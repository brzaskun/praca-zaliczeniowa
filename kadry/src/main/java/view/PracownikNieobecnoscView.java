/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.DataBean;
import beanstesty.IPaddress;
import static beanstesty.KalendarzmiesiacBean.pobierzpaski;
import dao.EtatPracFacade;
import dao.KalendarzmiesiacFacade;
import dao.NieobecnoscprezentacjaFacade;
import dao.PasekwynagrodzenFacade;
import dao.PracownikFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.UmowaFacade;
import dao.WspolczynnikEkwiwalentFacade;
import data.Data;
import embeddable.Mce;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Naliczenienieobecnosc;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Nieobecnoscprezentacja;
import entity.Nieobecnoscwykorzystanie;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import entity.Skladnikwynagrodzenia;
import entity.Sredniadlanieobecnosci;
import entity.Umowa;
import entity.WspolczynnikEkwiwalent;
import entity.Zmiennawynagrodzenia;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;
import z.Z;

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
    private WspolczynnikEkwiwalentFacade wspolczynnikEkwiwalentFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private EtatPracFacade etatPracFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private WpisView wpisView;
    private Nieobecnoscprezentacja urlopprezentacja;
    private Nieobecnoscprezentacja chorobaprezentacja;
    private Nieobecnoscprezentacja zasilekprezentacja;
    private Nieobecnoscprezentacja oddelegowanieprezentacja;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    private String stannadzien;
    private WspolczynnikEkwiwalent wspolczynnikEkwiwalent;
    @Inject
    private Nieobecnosc ekwiwalent;
    double ewiewalentskladnikizmienne;
    double ewiewalentskladnikistale;
    double ekwiwalentrazem;
    List<Naliczenienieobecnosc> skladnikistale;
    List<Naliczenienieobecnosc> skladnikizmienne;
    private EtatPrac wybranyetat;
    private String wiekdlachoroby;
    
    public void init() {
        try {
            if (wpisView.getPracownik()!=null) {
                stannadzien = data.Data.ostatniDzien(wpisView);
                pobierzurlop();
                pobierzchoroba();
                pobierzzasilek();
                pobierzoddelegowanie();
                wspolczynnikEkwiwalent = wspolczynnikEkwiwalentFacade.findbyRok(wpisView.getRokWpisu());
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
              Msg.msg("e", "Błąd - nie zmieniono danych");
          }
      }
    }
    
    public void pobierzurlop() {
        if (wpisView.getPracownik()!=null) {
            urlopprezentacja = new Nieobecnoscprezentacja(wpisView.getAngaz(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
            urlopprezentacja.setNieobecnoscwykorzystanieList(naniesdnizkodem(kalendarze, urlopprezentacja, "U"));
            List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
            urlopprezentacja.setWymiarokresbiezacy(obliczwymiarwgodzinach(umowy, wpisView.getAngaz().pobierzetat(stannadzien)));
            urlopprezentacja.setDoprzeniesienia(urlopprezentacja.getWymiarokresbiezacy()-urlopprezentacja.getWykorzystanierokbiezacy()-urlopprezentacja.getWykorzystanierokbiezacyekwiwalent());
            Msg.msg("Pobrano dane urlopowe");
        }
    }
    public void pobierzoddelegowanie() {
        if (wpisView.getPracownik()!=null) {
            oddelegowanieprezentacja = new Nieobecnoscprezentacja(wpisView.getAngaz(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
            oddelegowanieprezentacja.setNieobecnoscwykorzystanieList(naniesdnizkodem(kalendarze, oddelegowanieprezentacja, "Z"));
            List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
            oddelegowanieprezentacja.setWymiarokresbiezacy(obliczwymiarwgodzinach(umowy, wpisView.getAngaz().pobierzetat(stannadzien)));
            oddelegowanieprezentacja.setDoprzeniesienia(oddelegowanieprezentacja.getWymiarokresbiezacy()-oddelegowanieprezentacja.getWykorzystanierokbiezacy()-oddelegowanieprezentacja.getWykorzystanierokbiezacyekwiwalent());
            Msg.msg("Pobrano oddelegowania");
        }
    }
    
    public void pobierzchoroba() {
        if (wpisView.getPracownik()!=null) {
            chorobaprezentacja = new Nieobecnoscprezentacja(wpisView.getAngaz(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
            chorobaprezentacja.setNieobecnoscwykorzystanieList(naniesdnizkodem(kalendarze, chorobaprezentacja, "CH"));
            List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
            chorobaprezentacja.setWymiarokresbiezacy(obliczwymiarwgodzinachchoroba(umowy, wpisView.getAngaz().pobierzetat(stannadzien)));
            chorobaprezentacja.setDoprzeniesienia(chorobaprezentacja.getWymiarokresbiezacy()-chorobaprezentacja.getWykorzystanierokbiezacy()-chorobaprezentacja.getWykorzystanierokbiezacyekwiwalent());
            wiekdlachoroby = obliczwiek(wpisView.getAngaz().getPracownik());
            Msg.msg("Pobrano dane chorobowe");
        }
    }
    
    
    
     public void pobierzzasilek() {
        if (wpisView.getPracownik()!=null) {
            zasilekprezentacja = new Nieobecnoscprezentacja(wpisView.getAngaz(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
            zasilekprezentacja.setNieobecnoscwykorzystanieList(naniesdnizkodem(kalendarze, zasilekprezentacja, "ZC"));
            List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
            zasilekprezentacja.setWymiarokresbiezacy(obliczwymiarwgodzinachzasilek(umowy, wpisView.getAngaz().pobierzetat(stannadzien)));
            zasilekprezentacja.setDoprzeniesienia(zasilekprezentacja.getWymiarokresbiezacy()-zasilekprezentacja.getWykorzystanierokbiezacy()-zasilekprezentacja.getWykorzystanierokbiezacyekwiwalent());
            Msg.msg("Pobrano dni zasiłkowe");
        }
    }
     
    public String sumujdni(String ob, Nieobecnoscprezentacja n) {
        String zwrot = "";
        if (ob!=null) {
            int razem = 0;
            for (Nieobecnoscwykorzystanie p : n.getNieobecnoscwykorzystanieList()) {
                if (p.getMc()!=null&&p.getMc().equals(ob)) {
                    if (p.getKod().equals("Z")) {
                        razem = razem+(int)p.getDni();
                    } else {
                        razem = razem+(int)p.getGodziny();
                    }
                }
            }
            zwrot = String.valueOf(razem);
        } 
        return zwrot;
    }
    
    private List<Nieobecnoscwykorzystanie> naniesdnizkodem(List<Kalendarzmiesiac> kalendarze, Nieobecnoscprezentacja urlopprezentacja, String kod) {
        List<Nieobecnoscwykorzystanie> lista = new ArrayList<>();
        Nieobecnoscwykorzystanie wykorzystaniesuma = new Nieobecnoscwykorzystanie("podsum.",0);
        for (Kalendarzmiesiac p : kalendarze) {
            for (Dzien r : p.getDzienList()) {
                if (r.getNieobecnosc()!=null) {
                    if (r.getNieobecnosc().getKod().equals(kod)) {
                        Nieobecnoscwykorzystanie wykorzystanie = new Nieobecnoscwykorzystanie();
                        wykorzystanie.setMc(p.getMc());
                        wykorzystanie.setData(Data.zrobdate(r.getNrdnia(), p.getMc(), p.getRok()));
                        wykorzystanie.setDni(1);
                        wykorzystanie.setOpis(r.getNieobecnosc().getOpisRodzajSwiadczenie());
                        wykorzystanie.setKod(r.getNieobecnosc().getKod());
                        if (kod.equals("U")) {
                            wykorzystanie.setGodziny((int) r.getUrlopPlatny());
                        }
                        if (kod.equals("CH")) {
                            wykorzystanie.setGodziny((int) r.getWynagrodzeniezachorobe());
                        }
                        if (kod.equals("ZC")) {
                            wykorzystanie.setGodziny((int) r.getZasilek());
                        }
                        if (kod.equals("Z")) {
                            wykorzystanie.setGodziny((int) r.getPrzepracowano());
                        }
                        wykorzystanie.setUrlopprezentacja(urlopprezentacja);
                        EtatPrac pobierzetat = p.getAngaz().pobierzetat(wykorzystanie.getData());
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
        if (kod.equals("U")) {
            urlopprezentacja.setWykorzystanierokbiezacy(wykorzystaniesuma.getGodziny());
        }
        if (kod.equals("CH")) {
            urlopprezentacja.setWykorzystanierokbiezacy((int) wykorzystaniesuma.getDni());
        }
        if (kod.equals("ZC")) {
            urlopprezentacja.setWykorzystanierokbiezacy((int) wykorzystaniesuma.getDni());
        }
        if (kod.equals("Z")) {
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
        double zwrotdouble = (double)zwrot;
        //a teraz sprawdzamy czy nie sa umowy tylko z tego roku i trzeba proporckjonalnie
            Set<String> napoczetemiesiace = new HashSet<>();
            for (Umowa p : umowy) {
                if (p.isLiczdourlopu()) {
                    if (p.getSlownikszkolazatrhistoria() != null) {
                        if (p.getSlownikszkolazatrhistoria().getPraca0nauka1() == false) {
                            String rokumowy = Data.getRok(p.getDataod());
                            boolean czyumowaztegoroku = wpisView.getRokWpisu().equals(rokumowy);
                            if (czyumowaztegoroku == false) {
                                break;
                            } else {
                                String dataod = p.getDataod();
                                String datado = stannadzien;
                                if (p.getDatado() != null && Data.czyjestpo(p.getDatado(), stannadzien)) {
                                    datado = p.getDatado();
                                }
                                napoczetemiesiace.addAll(Mce.zakresmiesiecy(Data.getMc(dataod), Data.getMc(datado)));
                            }
                        }
                    }
                }
            }
            if (napoczetemiesiace.size()>0) {
                zwrot = (int) (Math.ceil(zwrotdouble/12.0)*napoczetemiesiace.size());
            }

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
    
    public  String obliczwiek(Pracownik pracownik) {
        String zwrota = "";
        if (pracownik!=null) {
            String dataurodzenia = pracownik.getDataurodzenia();
            LocalDate dataur = LocalDate.parse(dataurodzenia);
            LocalDate dataumowy = LocalDate.parse(stannadzien);
            String rok = Data.getRok(stannadzien);
            String pierwszydzienroku = rok+"-01-01";
            LocalDate dataroku = LocalDate.parse(pierwszydzienroku);
            long lata = ChronoUnit.YEARS.between(dataur, dataumowy);
            long dni = ChronoUnit.DAYS.between(dataroku, dataumowy);
            int[] zwrot = new int[2];
            zwrot[0] = (int) lata;
            zwrot[1] = (int) dni;
            zwrota = "lat: "+zwrot[0]+" dni: "+zwrot[1];
        }
        return zwrota;
    }
    
    private int obliczwymiarwgodzinachzasilek(List<Umowa> umowy, EtatPrac etat) {
        int zwrot = 182;
        double liczbadni = 0;
//        for (Umowa p : umowy) {
//            Pracownik pracownik = p.getAngaz().getPracownik();
//            String dataUrodzenia = pracownik.getDataurodzenia();
//            LocalDate dateBefore =  LocalDate.parse(dataUrodzenia);
//            LocalDate dateAfter = LocalDate.parse(stannadzien);
//            long daysBetween =  ChronoUnit.DAYS.between(dateBefore, dateAfter);
//            liczbadni = daysBetween;
//        }
//        if (liczbadni>=18260) {
//            zwrot = 15;
//        }
        return zwrot;
    }
    
    public void obliczekwiwalent() {
        if (ekwiwalent.getDataod() != null) {
            skladnikistale = new ArrayList<>();
            skladnikizmienne = new ArrayList<>();
            String data = ekwiwalent.getDataod();
            ekwiwalent.setDatado(data);
            String rok = Data.getRok(data);
            String mc = Data.getMc(data);
            ewiewalentskladnikistale = 0.0;
            List<Skladnikwynagrodzenia> skladniki = skladnikWynagrodzeniaFacade.findByAngaz(wpisView.getAngaz());
            double godzinyekwiwalent = urlopprezentacja.getDoprzeniesienia();
            ekwiwalentrazem = 0.0;
            if (godzinyekwiwalent>0) {
                List<EtatPrac> etatlist = etatPracFacade.findByAngaz(wpisView.getAngaz());
                wybranyetat = etatlist.get(0);
                if (etatlist.size()>1) {
                    for (EtatPrac e : etatlist) {
                        if (DataBean.czysiemiesci(data, data, e.getDataod(), e.getDatado())) {
                            wybranyetat = e;
                            break;
                        }
                    }
                }
                double dobowanormaczasupracy = (8/(wybranyetat.getEtat1()/wybranyetat.getEtat2()));
                double dniekwiwalent = Z.z(godzinyekwiwalent/dobowanormaczasupracy);
                for (Skladnikwynagrodzenia p : skladniki) {
                      if (p.getRodzajwynagrodzenia().getStale0zmienne1()==false) {
                        for (Zmiennawynagrodzenia r : p.getZmiennawynagrodzeniaList()) {
                             if (DataBean.czysiemiesci(data, data, r.getDataod(), r.getDatado())) {
                                double kwotastala = r.getKwota();
                                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                                naliczenienieobecnosc.setDatado(data);
                                naliczenienieobecnosc.setDataod(data);
                                naliczenienieobecnosc.setSkladnikwynagrodzenia(p);
                                naliczenienieobecnosc.setSredniazailemcy(1);
                                naliczenienieobecnosc.setLiczbadniurlopu(dniekwiwalent);
                                naliczenienieobecnosc.setLiczbagodzinurlopu(godzinyekwiwalent);
                                double stawkadzienna = Z.z(kwotastala/wspolczynnikEkwiwalent.getKwota());
                                naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                                double stawkagodzinowa = stawkadzienna/dobowanormaczasupracy;
                                double kwotaekwiwalentu = Z.z(stawkagodzinowa*godzinyekwiwalent);
                                naliczenienieobecnosc.setStawkagodzinowa(stawkagodzinowa);
                                naliczenienieobecnosc.setKwota(kwotaekwiwalentu);
                                ewiewalentskladnikistale = ewiewalentskladnikistale+kwotaekwiwalentu;
                                ekwiwalentrazem = ekwiwalentrazem+kwotaekwiwalentu;
                                skladnikistale.add(naliczenienieobecnosc);
                             }
                        }
                      } else {
                          List<Kalendarzmiesiac> kalendarzlista = kalendarzmiesiacFacade.findByAngaz(wpisView.getAngaz());
                            String dzien = wpisView.getAngaz().getFirma().getDzienlp();
                            if (dzien!=null) {
                                String[] popokres = Data.poprzedniOkres(mc, rok);
                                rok = popokres[1];
                                mc = popokres[0];
                            }
                            List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokAngaz(rok, wpisView.getAngaz());
                            List<Naliczenieskladnikawynagrodzenia> naliczonyskladnikdosredniej = pobierzpaski(rok, mc, p, kalendarzlista);
                            double godzinyfaktyczne = 0.0;
                            double dninalezne = 0.0;
                            double stawkazagodzine = 0.0;
                            double kwotywyplacone = 0.0;
                            int liczba = 0;
                            double sredniadopodstawy = 0.0;
                            Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                            naliczenienieobecnosc.setSkladnikwynagrodzenia(p);
                            naliczenienieobecnosc.setDatado(data);
                            naliczenienieobecnosc.setDataod(data);
                            naliczenienieobecnosc.setSredniazailemcy(3);
                            naliczenienieobecnosc.setLiczbadniurlopu(dniekwiwalent);
                            naliczenienieobecnosc.setLiczbagodzinurlopu(godzinyekwiwalent);
                            for (Naliczenieskladnikawynagrodzenia pa : naliczonyskladnikdosredniej) {
                                godzinyfaktyczne = godzinyfaktyczne+pa.getGodzinyfaktyczne();
                                dninalezne = dninalezne+pa.getDninalezne();
                                kwotywyplacone = kwotywyplacone + pa.getKwotadolistyplac();
                                liczba++;
                                boolean skladnikstaly = false;
                                double stawkazagodzinezm = pa.getKwotadolistyplac()/pa.getGodzinynalezne();

                                Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(pa.getPasekwynagrodzen().getRok(), pa.getPasekwynagrodzen().getMc(), pa.getKwotadolistyplac(), 
                                        skladnikstaly, naliczenienieobecnosc, godzinyekwiwalent, pa.getGodzinyfaktyczne(), pa.getDnifaktyczne(), pa.getGodzinynalezne(), pa.getDninalezne(), stawkazagodzinezm);
                                naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
                                naliczenienieobecnosc.setSredniazailemcy(liczba);
                                if(liczba>3) {
                                    break;
                                }
                            }
                            if (godzinyfaktyczne!=0.0&&dninalezne!=0.0) {
                                sredniadopodstawy = Z.z(kwotywyplacone/3);
                                naliczenienieobecnosc.setSumakwotdosredniej(kwotywyplacone);
                                naliczenienieobecnosc.setSumagodzindosredniej(godzinyfaktyczne);
                                naliczenienieobecnosc.setSkladnikizmiennesrednia(sredniadopodstawy);
                                double stawkadzienna = Z.z(sredniadopodstawy/wspolczynnikEkwiwalent.getKwota());
                                naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                                stawkazagodzine = Z.z6(stawkadzienna/dobowanormaczasupracy);
                                naliczenienieobecnosc.setStawkagodzinowa(stawkazagodzine);
                                double kwotaekwiwalentu = stawkazagodzine*godzinyekwiwalent;
                                naliczenienieobecnosc.setKwota(kwotaekwiwalentu);
                                ewiewalentskladnikizmienne = ewiewalentskladnikizmienne+kwotaekwiwalentu;
                                ekwiwalentrazem = ekwiwalentrazem+kwotaekwiwalentu;
                                skladnikizmienne.add(naliczenienieobecnosc);
                            }
                      }
                }
            } else {
                Msg.msg("w","Wykorzystano urlop w całości. NIe ma ekwiwalentu");
            }
        }
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

    public Nieobecnoscprezentacja getZasilekprezentacja() {
        return zasilekprezentacja;
    }

    public void setZasilekprezentacja(Nieobecnoscprezentacja zasilekprezentacja) {
        this.zasilekprezentacja = zasilekprezentacja;
    }

    public WspolczynnikEkwiwalent getWspolczynnikEkwiwalent() {
        return wspolczynnikEkwiwalent;
    }

    public void setWspolczynnikEkwiwalent(WspolczynnikEkwiwalent wspolczynnikEkwiwalent) {
        this.wspolczynnikEkwiwalent = wspolczynnikEkwiwalent;
    }

    public Nieobecnosc getEkwiwalent() {
        return ekwiwalent;
    }

    public void setEkwiwalent(Nieobecnosc ekwiwalent) {
        this.ekwiwalent = ekwiwalent;
    }

    public String getWiekdlachoroby() {
        return wiekdlachoroby;
    }

    public void setWiekdlachoroby(String wiekdlachoroby) {
        this.wiekdlachoroby = wiekdlachoroby;
    }

    public double getEwiewalentskladnikizmienne() {
        return ewiewalentskladnikizmienne;
    }

    public void setEwiewalentskladnikizmienne(double ewiewalentskladnikizmienne) {
        this.ewiewalentskladnikizmienne = ewiewalentskladnikizmienne;
    }

    public double getEwiewalentskladnikistale() {
        return ewiewalentskladnikistale;
    }

    public void setEwiewalentskladnikistale(double ewiewalentskladnikistale) {
        this.ewiewalentskladnikistale = ewiewalentskladnikistale;
    }

    public double getEkwiwalentrazem() {
        return ekwiwalentrazem;
    }

    public void setEkwiwalentrazem(double ekwiwalentrazem) {
        this.ekwiwalentrazem = ekwiwalentrazem;
    }

    public List<Naliczenienieobecnosc> getSkladnikistale() {
        return skladnikistale;
    }

    public void setSkladnikistale(List<Naliczenienieobecnosc> skladnikistale) {
        this.skladnikistale = skladnikistale;
    }

    public List<Naliczenienieobecnosc> getSkladnikizmienne() {
        return skladnikizmienne;
    }

    public void setSkladnikizmienne(List<Naliczenienieobecnosc> skladnikizmienne) {
        this.skladnikizmienne = skladnikizmienne;
    }

    public EtatPrac getWybranyetat() {
        return wybranyetat;
    }

    public void setWybranyetat(EtatPrac wybranyetat) {
        this.wybranyetat = wybranyetat;
    }

    public Nieobecnoscprezentacja getOddelegowanieprezentacja() {
        return oddelegowanieprezentacja;
    }

    public void setOddelegowanieprezentacja(Nieobecnoscprezentacja oddelegowanieprezentacja) {
        this.oddelegowanieprezentacja = oddelegowanieprezentacja;
    }

    
   
    
    
    
}
