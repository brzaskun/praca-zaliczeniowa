/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.UrlopBean;
import comparator.Rejestrurlopowcomparator;
import dao.AngazFacade;
import dao.KalendarzmiesiacFacade;
import dao.PracownikFacade;
import dao.RejestrurlopowFacade;
import dao.SMTPSettingsFacade;
import data.Data;
import entity.Angaz;
import entity.Dzien;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Nieobecnosc;
import entity.Nieobecnoscprezentacja;
import entity.Pracownik;
import entity.Rejestrurlopow;
import entity.SMTPSettings;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdf.PdfZestawienieUrlopow;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class UrlopyZestawienieView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    private List<Rejestrurlopow> listaurlopow;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private RejestrurlopowFacade rejestrurlopowFacade;

    
    @PostConstruct
    public void init() {
        List<Angaz> angaze = angazFacade.findByFirmaAktywni(wpisView.getFirma());
        listaurlopow = rejestrurlopowFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu());
        String rokpoprzedni = wpisView.getRokUprzedni();
        List<Rejestrurlopow> listaurlopowrokuprzedni = rejestrurlopowFacade.findByFirmaRok(wpisView.getFirma(), rokpoprzedni);
        for (Rejestrurlopow rejestr : listaurlopow) {
            Predicate<Angaz> isQualified = item -> item.equals(rejestr.getAngaz());
            angaze.removeIf(isQualified);
        }
        for (Angaz angaz : angaze) {
            listaurlopow.add(new Rejestrurlopow(angaz,wpisView.getRokWpisu()));
        }
        if (listaurlopow!=null) {
            for (Iterator<Rejestrurlopow> it = listaurlopow.iterator(); it.hasNext();) {
                Rejestrurlopow rejestrurlopowrok = it.next();
                boolean jestumowaAktywna = rejestrurlopowrok.getAngaz().jestumowaPracaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                if (jestumowaAktywna==false) {
                    it.remove();
                } else {
                    String stannadzien = data.Data.ostatniDzien(wpisView.getRokWpisu(),"12");
                    String dataDlaEtatu = data.Data.ostatniDzien(wpisView.getRokWpisu(),wpisView.getMiesiacWpisu());
                    Nieobecnoscprezentacja urlopprezentacja = UrlopBean.pobierzurlop(rejestrurlopowrok.getAngaz(), wpisView.getRokWpisu(), stannadzien, dataDlaEtatu);
                    Pracownik pracownik = rejestrurlopowrok.getAngaz().getPracownik();
                    if(rejestrurlopowrok.getRok().equals(wpisView.getRokWpisu())) {
                        //pracownik.setWymiarurlopu(urlopprezentacja.getWymiarokresbiezacydni());
                        pracownik.setWymiarurlopu(urlopprezentacja.getWymiargeneralnydni());
                        pracownikFacade.edit(pracownik);
                    }
                    boolean jestprzed2023 = wpisView.getRokWpisu().equals("2023")&&Integer.parseInt(rejestrurlopowrok.getAngaz().getRok())<2023?true:false;
                    if (rejestrurlopowrok.getAngaz().getRok().equals(wpisView.getRokWpisu())||jestprzed2023) {
                        rejestrurlopowrok.setUrlopzalegly(rejestrurlopowrok.getAngaz().getBourlopdni());
                    } else {
                        
                        if (listaurlopowrokuprzedni.isEmpty()==false) {
                            Predicate<Rejestrurlopow> pred = item -> item.getAngaz().equals(rejestrurlopowrok.getAngaz());
                            Rejestrurlopow rejestrpoprzednirok = listaurlopowrokuprzedni.stream().filter(pred).findAny().orElse(null);
                            if (rejestrpoprzednirok!=null) {
                                System.out.println("rejestr: "+rejestrpoprzednirok.getAngaz().getNazwiskoiImie()+" "+rejestrpoprzednirok.getDowykorzystanianastrok());
                                rejestrurlopowrok.setUrlopzalegly(rejestrpoprzednirok.getDowykorzystanianastrok());
                            }

                        }
                    }
                }
            }
            Collections.sort(listaurlopow, new Rejestrurlopowcomparator());
            //tutaj sa sejwy
            sumujUrlopy(listaurlopow);
        }
    }
    
    private void sumujUrlopy(List<Rejestrurlopow> angaze) {
        for (Rejestrurlopow rejestrurlopow : angaze) {
            List<Kalendarzmiesiac> kalendarzmiesiacs = rejestrurlopow.getAngaz().getKalendarzmiesiacList();
            if (kalendarzmiesiacs != null && kalendarzmiesiacs.size() > 0) {
                kalendarzmiesiacs = kalendarzmiesiacs.stream().filter(p -> p.getRok().equals(wpisView.getRokWpisu())).collect(Collectors.toList());
            }
            int sumadnirok = 0;
            if (rejestrurlopow.getAngaz().getRok().equals(wpisView.getRokWpisu())) {
                rejestrurlopow.setUrlopzalegly(rejestrurlopow.getAngaz().getBourlopdni());
            }
            String datadodatkowywymiar = rejestrurlopow.getAngaz().getPracownik().getDatadodwymiar();
            if (datadodatkowywymiar!=null&&datadodatkowywymiar.length()==10) {
                String rok = Data.getRok(datadodatkowywymiar);
                Integer rokI = Integer.parseInt(rok);
                if (wpisView.getRokWpisuInt()>=rokI) {
                    rejestrurlopow.setDodatkowywymiar(rejestrurlopow.getAngaz().getPracownik().getDodatkowywymiar());
                }
            }
            for (Kalendarzmiesiac kal : kalendarzmiesiacs) {
                int sumadni = 0;
                String mc = kal.getMc();
                Integer mcInt = Integer.parseInt(mc);
                for (Dzien dzien : kal.getDzienList()) {
                    Nieobecnosc dziennieob = dzien.getNieobecnosc();
                    if (dziennieob != null && (dziennieob.getRodzajnieobecnosci().getKod().equals("U") ||dziennieob.getRodzajnieobecnosci().getKod().equals("UD"))) {
                        if (dzien.getTypdnia()==0) {
                            sumadni = sumadni+1;
                            sumadnirok = sumadnirok+1;
                        }
                    }
                }
                if (sumadni>0) {
                    try {
                        String metoda = "setM"+mcInt;
                        Method met;
                        met = Rejestrurlopow.class.getDeclaredMethod(metoda,int.class);
                        met.invoke(rejestrurlopow, sumadni);
                    } catch (Exception ex) {
                        System.out.println("");
                    }
                }
            }
            rejestrurlopow.setWykorzystaniesuma(sumadnirok);
            int wymiar = rejestrurlopow.getAngaz().getPracownik().getWymiarurlopu();
            rejestrurlopow.setDowykorzystanianastrok(wymiar - sumadnirok + rejestrurlopow.getUrlopzalegly()+rejestrurlopow.getDodatkowywymiar() - rejestrurlopow.getEwiwalent());
            rejestrurlopowFacade.edit(rejestrurlopow);
        }
    }
    
    public void drukuj() {
        if (listaurlopow!=null&&listaurlopow.size()>0) {
            PdfZestawienieUrlopow.drukuj(listaurlopow, wpisView.getFirma(), wpisView.getRokWpisu());
        }
    }
    
     public void mail() {
         ByteArrayOutputStream dra =  PdfZestawienieUrlopow.drukujmail(listaurlopow, wpisView.getFirma(), wpisView.getRokWpisu());
        if (dra != null) {
            SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
            findSprawaByDef.setUseremail(wpisView.getUzer().getEmail());
            findSprawaByDef.setPassword(wpisView.getUzer().getEmailhaslo());
            String nazwa = wpisView.getFirma().getNip() + "urlopyZest" + wpisView.getRokWpisu() + ".pdf";
            mail.Mail.mailUrlopy(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), wpisView.getFirma().getEmail(), null, findSprawaByDef, dra.toByteArray(), nazwa, wpisView.getUzer().getEmail());
            Msg.msg("Wysłano listę płac do pracodawcy");
        } else {
            Msg.msg("e", "Błąd dwysyki DRA");
        }
    }
    
    
    public void aktywujPracAngaze(FirmaKadry firma) {
        if (firma!=null) {
           init();
        }
    }

    public List<Rejestrurlopow> getListaurlopow() {
        return listaurlopow;
    }

    public void setListaurlopow(List<Rejestrurlopow> listaurlopow) {
        this.listaurlopow = listaurlopow;
    }

    
    
    
    
}
