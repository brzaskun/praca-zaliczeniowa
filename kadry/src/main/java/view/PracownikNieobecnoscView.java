/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.IPaddress;
import dao.KalendarzmiesiacFacade;
import dao.NieobecnoscprezentacjaFacade;
import dao.PracownikFacade;
import dao.UmowaFacade;
import data.Data;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Nieobecnoscprezentacja;
import entity.Nieobecnoscwykorzystanie;
import entity.Pracownik;
import entity.Umowa;
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
public class PracownikNieobecnoscView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private NieobecnoscprezentacjaFacade nieobecnoscprezentacjaFacade;
    @Inject
    private WpisView wpisView;
    private Nieobecnoscprezentacja urlopprezentacja;
    private Nieobecnoscprezentacja chorobaprezentacja;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    private String stannadzien;
    
    
    public void init() {
        try {
            if (wpisView.getPracownik()!=null) {
                stannadzien = data.Data.ostatniDzien(wpisView);
                pobierzurlop();
                pobierzchoroba();
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
    
    public void pobierzurlop() {
        if (wpisView.getPracownik()!=null) {
            urlopprezentacja = new Nieobecnoscprezentacja(wpisView.getUmowa(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(wpisView.getUmowa(), wpisView.getRokWpisu());
            urlopprezentacja.setNieobecnoscwykorzystanieList(naniesdnizkodem(kalendarze, urlopprezentacja, "100"));
            List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
            urlopprezentacja.setWymiarokresbiezacy(obliczwymiarwgodzinach(umowy, wpisView.getUmowa().pobierzetat(stannadzien)));
            urlopprezentacja.setDoprzeniesienia(urlopprezentacja.getWymiarokresbiezacy()-urlopprezentacja.getWykorzystanierokbiezacy()-urlopprezentacja.getWykorzystanierokbiezacyekwiwalent());
            Msg.msg("Pobrano dane urlopowe");
        }
    }
    
    public void pobierzchoroba() {
        if (wpisView.getPracownik()!=null) {
            chorobaprezentacja = new Nieobecnoscprezentacja(wpisView.getUmowa(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(wpisView.getUmowa(), wpisView.getRokWpisu());
            chorobaprezentacja.setNieobecnoscwykorzystanieList(naniesdnizkodem(kalendarze, chorobaprezentacja, "331"));
            List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
            chorobaprezentacja.setWymiarokresbiezacy(obliczwymiarwgodzinachchoroba(umowy, wpisView.getUmowa().pobierzetat(stannadzien)));
            chorobaprezentacja.setDoprzeniesienia(chorobaprezentacja.getWymiarokresbiezacy()-chorobaprezentacja.getWykorzystanierokbiezacy()-chorobaprezentacja.getWykorzystanierokbiezacyekwiwalent());
            Msg.msg("Pobrano dane chorobowe");
        }
    }
    
    private List<Nieobecnoscwykorzystanie> naniesdnizkodem(List<Kalendarzmiesiac> kalendarze, Nieobecnoscprezentacja urlopprezentacja, String kod) {
        List<Nieobecnoscwykorzystanie> lista = new ArrayList<>();
        Nieobecnoscwykorzystanie wykorzystaniesuma = new Nieobecnoscwykorzystanie("podsumowanie",0);
        for (Kalendarzmiesiac p : kalendarze) {
            for (Dzien r : p.getDzienList()) {
                if (r.getNieobecnosc()!=null) {
                    if (r.getNieobecnosc().getSwiadczeniekodzus().getKod().equals(kod)) {
                        Nieobecnoscwykorzystanie wykorzystanie = new Nieobecnoscwykorzystanie();
                        wykorzystanie.setMc(p.getMc());
                        wykorzystanie.setData(Data.zrobdate(r.getNrdnia(), p.getMc(), p.getRok()));
                        wykorzystanie.setDni(1);
                        wykorzystanie.setOpis(r.getNieobecnosc().getSwiadczeniekodzus().getOpisskrocony());
                        wykorzystanie.setKod(r.getNieobecnosc().getSwiadczeniekodzus().getKod());
                        if (kod.equals("100")) {
                            wykorzystanie.setGodziny((int) r.getUrlopPlatny());
                        }
                        if (kod.equals("331")) {
                            wykorzystanie.setGodziny((int) r.getWynagrodzeniezachorobe());
                        }
                        wykorzystanie.setUrlopprezentacja(urlopprezentacja);
                        EtatPrac pobierzetat = p.getUmowa().pobierzetat(wykorzystanie.getData());
                        wykorzystanie.setEtat1(pobierzetat.getEtat1());
                        wykorzystanie.setEtat2(pobierzetat.getEtat2());
                        if (wykorzystanie.getGodziny()>0) {
                            wykorzystaniesuma.setGodziny(wykorzystaniesuma.getGodziny()+wykorzystanie.getGodziny());
                            wykorzystaniesuma.setDni(wykorzystaniesuma.getDni()+wykorzystanie.getDni());
                            lista.add(wykorzystanie);
                        }
                    }
                }
            }
        }
        if (kod.equals("100")) {
            urlopprezentacja.setWykorzystanierokbiezacy(wykorzystaniesuma.getGodziny());
        }
        if (kod.equals("331")) {
            urlopprezentacja.setWykorzystanierokbiezacy((int) wykorzystaniesuma.getDni());
        }
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
    
    private int obliczwymiarwgodzinachchoroba(List<Umowa> umowy, EtatPrac etat) {
        int zwrot = 33;
        double liczbadni = 0;
        for (Umowa p : umowy) {
            Pracownik pracownik = p.getAngaz().getPracownik();
            String dataUrodzenia = pracownik.getDataurodzenia();
            LocalDate dateBefore =  LocalDate.parse(dataUrodzenia);
            LocalDate dateAfter = LocalDate.parse(stannadzien);
            long daysBetween =  ChronoUnit.DAYS.between(dateBefore, dateAfter);
            liczbadni = daysBetween;
        }
        if (liczbadni>=18260) {
            zwrot = 15;
        }
        return zwrot;
    }


      public Nieobecnoscprezentacja getUrlopprezentacja() {
        return urlopprezentacja;
    }

    public void setUrlopprezentacja(Nieobecnoscprezentacja urlopprezentacja) {
        this.urlopprezentacja = urlopprezentacja;
    }

    public String getStannadzien() {
        return stannadzien;
    }

    public void setStannadzien(String stannadzien) {
        this.stannadzien = stannadzien;
    }

    public Nieobecnoscprezentacja getChorobaprezentacja() {
        return chorobaprezentacja;
    }

    public void setChorobaprezentacja(Nieobecnoscprezentacja chorobaprezentacja) {
        this.chorobaprezentacja = chorobaprezentacja;
    }

   
    
    
    
}
