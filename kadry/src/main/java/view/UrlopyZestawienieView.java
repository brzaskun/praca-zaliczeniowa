/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.UrlopBean;
import comparator.Angazcomparator;
import dao.AngazFacade;
import dao.KalendarzmiesiacFacade;
import dao.SMTPSettingsFacade;
import entity.Angaz;
import entity.Dzien;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Nieobecnosc;
import entity.Nieobecnoscprezentacja;
import entity.Pracownik;
import entity.SMTPSettings;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
    private List<Angaz> listaurlopow;
    private List<Angaz> listaeast2;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    @Inject
    private AngazFacade angazFacade;

    
    @PostConstruct
    private void init() {
        listaurlopow = angazFacade.findByFirmaAktywni(wpisView.getFirma());
        if (listaurlopow!=null) {
            for (Iterator<Angaz> it = listaurlopow.iterator(); it.hasNext();) {
                Angaz angaz = it.next();
                boolean jestumowaAktywna = angaz.jestumowaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                if (jestumowaAktywna==false) {
                    it.remove();
                } else {
                    String stannadzien = data.Data.ostatniDzien(wpisView.getRokWpisu(),"12");
                    String dataDlaEtatu = data.Data.ostatniDzien(wpisView.getRokWpisu(),wpisView.getMiesiacWpisu());
                    Nieobecnoscprezentacja urlopprezentacja = UrlopBean.pobierzurlop(angaz, wpisView.getRokWpisu(), stannadzien, dataDlaEtatu);
                    Pracownik pracownik = angaz.getPracownik();
                    pracownik.setWymiarurlopu(urlopprezentacja.getWymiargeneralnydni());
                    angazFacade.edit(angaz);
                }
            }
            Collections.sort(listaurlopow, new Angazcomparator());
            sumujUrlopy(listaurlopow);
        }
    }
    
    private void sumujUrlopy(List<Angaz> angaze) {
        for (Angaz angaz : angaze) {
            List<Kalendarzmiesiac> kalendarzmiesiacs = angaz.getKalendarzmiesiacList();
            if (kalendarzmiesiacs != null && kalendarzmiesiacs.size() > 0) {
                kalendarzmiesiacs = kalendarzmiesiacs.stream().filter(p -> p.getRok().equals(wpisView.getRokWpisu())).collect(Collectors.toList());
            }
            int sumadnirok = 0;
            angaz.setM0(angaz.getBourlopdni());
            for (Kalendarzmiesiac kal : kalendarzmiesiacs) {
                int sumadni = 0;
                String mc = kal.getMc();
                Integer mcInt = Integer.parseInt(mc);
                for (Dzien dzien : kal.getDzienList()) {
                    Nieobecnosc dziennieob = dzien.getNieobecnosc();
                    if (dziennieob != null && (dziennieob.getRodzajnieobecnosci().getKod().equals("U") ||dziennieob.getRodzajnieobecnosci().getKod().equals("UD"))) {
                        sumadni = sumadni+1;
                        sumadnirok = sumadnirok+1;
                    }
                }
                if (sumadni>0) {
                    try {
                        String metoda = "setM"+mcInt;
                        Method met;
                        met = Angaz.class.getDeclaredMethod(metoda,int.class);
                        met.invoke(angaz, new Integer(sumadni));
                    } catch (Exception ex) {
                        System.out.println("");
                    }
                }
            }
            angaz.setM13(sumadnirok);
            int wymiar = angaz.getPracownik().getWymiarurlopu();
            angaz.setM14(wymiar - sumadnirok + angaz.getM0());
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
            String nazwa = wpisView.getFirma().getNip() + "urlopyZest" + wpisView.getRokWpisu() + ".pdf";
            mail.Mail.mailUrlopy(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), "info@taxman.biz.pl", null, findSprawaByDef, dra.toByteArray(), nazwa, wpisView.getUzer().getEmail());
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

    public List<Angaz> getListaurlopow() {
        return listaurlopow;
    }

    public void setListaurlopow(List<Angaz> listaurlopow) {
        this.listaurlopow = listaurlopow;
    }

    
    
    
    
}
