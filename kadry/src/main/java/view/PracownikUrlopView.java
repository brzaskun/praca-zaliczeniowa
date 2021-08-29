/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.IPaddress;
import dao.KalendarzmiesiacFacade;
import dao.PracownikFacade;
import dao.UmowaFacade;
import dao.UrlopprezentacjaFacade;
import data.Data;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Umowa;
import entity.Urlopprezentacja;
import entity.Urlopwykorzystanie;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PracownikUrlopView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private UrlopprezentacjaFacade urlopprezentacjaFacade;
    @Inject
    private WpisView wpisView;
    private Urlopprezentacja urlopprezentacja;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    private String stannadzien;
    
    
    public void init() {
        try {
            if (wpisView.getPracownik()!=null) {
                stannadzien = data.Data.ostatniDzien(wpisView);
                pobierz();
            }
        } catch (Exception e){}
    }

        
    public void edit() {
      if (wpisView.getPracownik()!=null) {
          try {
            wpisView.getPracownik().setIpusera(IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()));
            Calendar calendar = Calendar.getInstance();
            wpisView.getPracownik().setDatalogowania(Data.aktualnaDataCzas());
            wpisView.getPracownik().setModyfikowal(wpisView.getUzer().getSecname());
            pracownikFacade.edit(wpisView.getPracownik());
            Msg.msg("Uaktualniono dane");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie zmieniono danych");
          }
      }
    }
    
    public void pobierz() {
        if (wpisView.getPracownik()!=null) {
            urlopprezentacja = new Urlopprezentacja(wpisView.getUmowa(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(wpisView.getUmowa(), wpisView.getRokWpisu());
            urlopprezentacja.setUrlopwykorzystanieList(naniesdnizkodem(kalendarze, urlopprezentacja, "100"));
            List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
            urlopprezentacja.setWymiarokresbiezacy(obliczwymiarwgodzinach(umowy, wpisView.getUmowa().pobierzetat(stannadzien)));
            urlopprezentacja.setDoprzeniesienia(urlopprezentacja.getWymiarokresbiezacy()-urlopprezentacja.getWykorzystanierokbiezacy()-urlopprezentacja.getWykorzystanierokbiezacyekwiwalent());
            Msg.msg("Pobrano dane");
        }
    }
    
    private List<Urlopwykorzystanie> naniesdnizkodem(List<Kalendarzmiesiac> kalendarze, Urlopprezentacja urlopprezentacja, String kod) {
        List<Urlopwykorzystanie> lista = new ArrayList<>();
        Urlopwykorzystanie wykorzystaniesuma = new Urlopwykorzystanie("podsumowanie",0);
        for (Kalendarzmiesiac p : kalendarze) {
            for (Dzien r : p.getDzienList()) {
                if (r.getNieobecnosc()!=null) {
                    if (r.getNieobecnosc().getNieobecnosckodzus().getKod().equals("100")) {
                        Urlopwykorzystanie wykorzystanie = new Urlopwykorzystanie();
                        wykorzystanie.setMc(p.getMc());
                        wykorzystanie.setData(Data.zrobdate(r.getNrdnia(), p.getMc(), p.getRok()));
                        wykorzystanie.setDni(1);
                        wykorzystanie.setGodziny((int) r.getUrlopPlatny());
                        wykorzystanie.setUrlopprezentacja(urlopprezentacja);
                        EtatPrac pobierzetat = p.getUmowa().pobierzetat(wykorzystanie.getData());
                        wykorzystanie.setEtat1(pobierzetat.getEtat1());
                        wykorzystanie.setEtat2(pobierzetat.getEtat2());
                        if (r.getUrlopPlatny()>0) {
                            wykorzystaniesuma.setGodziny(wykorzystaniesuma.getGodziny()+wykorzystanie.getGodziny());
                            lista.add(wykorzystanie);
                        }
                    }
                }
            }
        }
        urlopprezentacja.setWykorzystanierokbiezacy(wykorzystaniesuma.getGodziny());
        lista.add(wykorzystaniesuma);
        return lista;
    }

    private int obliczwymiarwgodzinach(List<Umowa> umowy, EtatPrac etat) {
        int zwrot = 20;
        double liczbadni = 0;
        for (Umowa p : umowy) {
            if (p.isLiczdourlopu()) {
                if (p.getSlownikszkolazatrhistoria()!=null) {
                    if (p.getSlownikszkolazatrhistoria().getPraca0nauka1()) {
                        liczbadni = liczbadni+p.getSlownikszkolazatrhistoria().getDni();
                    } else {
                        LocalDate dateBefore =  LocalDate.parse(p.getDataod());
                        LocalDate dateAfter = LocalDate.parse(stannadzien);
                        if (p.getDatado()!=null && Data.czyjestpo(p.getDatado(), stannadzien)) {
                            dateAfter = LocalDate.parse(p.getDatado());
                        }
                        long daysBetween =  ChronoUnit.DAYS.between(dateBefore, dateAfter);
                        liczbadni = liczbadni+daysBetween;
                        if (liczbadni>=3650) {
                            break;
                        }
                    }
                } 
            }
        }
        if (liczbadni>=3650) {
            zwrot = 26;
        }
        zwrot = (zwrot*8*etat.getEtat1())/etat.getEtat2();
        return zwrot;
    }


      public Urlopprezentacja getUrlopprezentacja() {
        return urlopprezentacja;
    }

    public void setUrlopprezentacja(Urlopprezentacja urlopprezentacja) {
        this.urlopprezentacja = urlopprezentacja;
    }

    public String getStannadzien() {
        return stannadzien;
    }

    public void setStannadzien(String stannadzien) {
        this.stannadzien = stannadzien;
    }

   
    
    
    
}
