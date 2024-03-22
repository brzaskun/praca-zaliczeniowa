/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.DataBean;
import beanstesty.EtatBean;
import beanstesty.IPaddress;
import static beanstesty.KalendarzmiesiacBean.pobierzpaski;
import beanstesty.UrlopBean;
import dao.EkwiwalentUrlopFacade;
import dao.EtatPracFacade;
import dao.KalendarzmiesiacFacade;
import dao.NieobecnoscprezentacjaFacade;
import dao.PasekwynagrodzenFacade;
import dao.PracownikFacade;
import dao.RejestrurlopowFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.UmowaFacade;
import dao.WspolczynnikEkwiwalentFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import entity.Angaz;
import entity.EkwiwalentUrlop;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Naliczenienieobecnosc;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnoscprezentacja;
import entity.Nieobecnoscwykorzystanie;
import entity.Pracownik;
import entity.Rejestrurlopow;
import entity.Rodzajwynagrodzenia;
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
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class PracownikEkwiwalentView  implements Serializable {
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
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private EtatPracFacade etatPracFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private EkwiwalentUrlopFacade ekwiwalentSkladnikiFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private RejestrurlopowFacade rejestrurlopowFacade;
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
    private EkwiwalentUrlop ekwiwalent;
    List<Naliczenienieobecnosc> skladnikistale;
    List<Naliczenienieobecnosc> skladnikizmienne;
    private EtatPrac wybranyetat;
    private String wiekdlachoroby;
    
    @PostConstruct
    public void init() {
        try {
            if (wpisView.getPracownik()!=null) {
                stannadzien = data.Data.ostatniDzien(wpisView);
                wspolczynnikEkwiwalent = wspolczynnikEkwiwalentFacade.findbyRok(wpisView.getRokWpisu());
                EtatPrac pobierzetat = EtatBean.pobierzetat(wpisView.getAngaz(),stannadzien);
                double etatulamek = pobierzetat.getEtat1()/pobierzetat.getEtat2();
                double wspolczynnikEkwiwalentkwota = wspolczynnikEkwiwalent.getKwota()*etatulamek;
                EkwiwalentUrlop znaleziony = ekwiwalentSkladnikiFacade.findbyUmowa(wpisView.getUmowa());
                if (znaleziony!=null) {
                    ekwiwalent=znaleziony;
                } else {
                    ustawekwiwalent(ekwiwalent, wspolczynnikEkwiwalentkwota, stannadzien);
                }
                pobierzurlop();
                pobierzchoroba();
                pobierzzasilek();
                pobierzoddelegowanie();
                obliczekwiwalent(ekwiwalent);
            }
        } catch (Exception e){
            System.out.println("");
        }
    }
    
    public void przeliczekwiwalent() {
        pobierzurlop();
        obliczekwiwalent(ekwiwalent);
    }

    private void ustawekwiwalent (EkwiwalentUrlop ekw, double kwotawspolczynnika, String stannadzien) {
        if (ekw!=null) {
            ekw.setDziennaliczenia(stannadzien);
            ekw.setAngaz(wpisView.getAngaz());
            ekw.setUmowa(wpisView.getUmowa());
            ekw.setWspolczynnik(kwotawspolczynnika);
        }
    }
        
    public void edit() {
      if (wpisView.getPracownik()!=null) {
          try {
            wpisView.getPracownik().setIpusera(IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()));
            Calendar calendar = Calendar.getInstance();
            wpisView.getPracownik().setDatalogowania(Data.aktualnaDataCzas());
            wpisView.getPracownik().setModyfikowal(wpisView.getUzer().getImieNazwisko());
            pracownikFacade.edit(wpisView.getPracownik());
            Msg.msg("Uaktualniono dane");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie zmieniono danych");
          }
      }
    }
    
    public void pobierzurlop() {
        if (wpisView.getPracownik()!=null) {
            Rejestrurlopow rejestrurlopow = rejestrurlopowFacade.findByAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
            urlopprezentacja = UrlopBean.pobierzurlopSwiadectwo(wpisView.getAngaz(), wpisView.getRokWpisu(), stannadzien, stannadzien, rejestrurlopow, null);
        }
    }
    public void pobierzoddelegowanie() {
        if (wpisView.getPracownik()!=null) {
            oddelegowanieprezentacja = new Nieobecnoscprezentacja(wpisView.getAngaz(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
            oddelegowanieprezentacja.setNieobecnoscwykorzystanieList(UrlopBean.naniesdnizkodem(kalendarze, oddelegowanieprezentacja, "Z"));
            List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
            EtatPrac pobierzetat = EtatBean.pobierzetat(wpisView.getAngaz(),stannadzien);
            Object[] obliczwymiarwgodzinach = UrlopBean.obliczwymiarwgodzinach(umowy, pobierzetat, wpisView.getRokWpisu(), stannadzien, wpisView.getAngaz(), kalendarze);
            oddelegowanieprezentacja.setWymiarokresbiezacydni((int) obliczwymiarwgodzinach[0]);
            oddelegowanieprezentacja.setWymiarokresbiezacygodziny((int) obliczwymiarwgodzinach[1]);
            oddelegowanieprezentacja.setDoprzeniesienia(oddelegowanieprezentacja.getWymiarokresbiezacygodziny()-oddelegowanieprezentacja.getWykorzystanierokbiezacy()-oddelegowanieprezentacja.getWykorzystanierokbiezacyekwiwalent());
            //Msg.msg("Pobrano oddelegowania");
        }
    }
    
    public void pobierzchoroba() {
        if (wpisView.getPracownik()!=null) {
            chorobaprezentacja = new Nieobecnoscprezentacja(wpisView.getAngaz(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
            chorobaprezentacja.setNieobecnoscwykorzystanieList(UrlopBean.naniesdnizkodem(kalendarze, chorobaprezentacja, "CH"));
            List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
            chorobaprezentacja.setWymiarokresbiezacygodziny(obliczwymiarwgodzinachchoroba(umowy));
            chorobaprezentacja.setDoprzeniesienia(chorobaprezentacja.getWymiarokresbiezacygodziny()-chorobaprezentacja.getWykorzystanierokbiezacy()-chorobaprezentacja.getWykorzystanierokbiezacyekwiwalent());
            wiekdlachoroby = obliczwiek(wpisView.getAngaz().getPracownik());
            //Msg.msg("Pobrano dane chorobowe");
        }
    }
    
    
    
     public void pobierzzasilek() {
        if (wpisView.getPracownik()!=null) {
            zasilekprezentacja = new Nieobecnoscprezentacja(wpisView.getAngaz(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
            zasilekprezentacja.setNieobecnoscwykorzystanieList(UrlopBean.naniesdnizkodem(kalendarze, zasilekprezentacja, "ZC"));
            zasilekprezentacja.getNieobecnoscwykorzystanieList().addAll(UrlopBean.naniesdnizkodem(kalendarze, zasilekprezentacja, "W"));
            List<Umowa> umowy = umowaFacade.findByAngaz(wpisView.getAngaz());
            zasilekprezentacja.setWymiarokresbiezacygodziny(obliczwymiarwgodzinachzasilek(umowy));
            zasilekprezentacja.setDoprzeniesienia(zasilekprezentacja.getWymiarokresbiezacygodziny()-zasilekprezentacja.getWykorzystanierokbiezacy()-zasilekprezentacja.getWykorzystanierokbiezacyekwiwalent());
            //Msg.msg("Pobrano dni zasiłkowe");
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
    
    public void zapiszekwiwalent() {
        if (ekwiwalent!=null) {
            EkwiwalentUrlop znaleziony = ekwiwalentSkladnikiFacade.findbyUmowa(ekwiwalent.getUmowa());
            if (znaleziony!=null) {
                ekwiwalentSkladnikiFacade.remove(znaleziony);
            }
            ekwiwalentSkladnikiFacade.create(ekwiwalent);
            tworzzmiennaekwiwalent(ekwiwalent);
            Msg.msg("Zachowano wyliczenie ekwiwalentu");
        } else {
            Msg.dPe();
        }
    }
    
    private void tworzzmiennaekwiwalent(EkwiwalentUrlop ekwiwalent) {
        Angaz angaz = ekwiwalent.getAngaz();
        List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList = angaz.getSkladnikwynagrodzeniaList();
        Skladnikwynagrodzenia ekwiwalentskladnik = null;
        for (Skladnikwynagrodzenia skl : skladnikwynagrodzeniaList) {
            if (skl.getRodzajwynagrodzenia().getId()==110) {
                ekwiwalentskladnik = skladnikWynagrodzeniaFacade.findById(skl.getId());
            }
        }
        String nazwa = "ekwiwalent umowa "+ekwiwalent.getUmowa().getNrkolejny();
        if (ekwiwalentskladnik==null) {
            ekwiwalentskladnik = new Skladnikwynagrodzenia();
            ekwiwalentskladnik.setAngaz(angaz);
            Rodzajwynagrodzenia findEkwiwalentUrlop = rodzajwynagrodzeniaFacade.findEkwiwalentUrlop();
            ekwiwalentskladnik.setRodzajwynagrodzenia(findEkwiwalentUrlop);
            ekwiwalentSkladnikiFacade.create(ekwiwalentskladnik);
        } else {
            Zmiennawynagrodzenia dousuniecia = null;
            for (Zmiennawynagrodzenia zm : ekwiwalentskladnik.getZmiennawynagrodzeniaList()) {
                if (zm.getNazwa().equals(nazwa)) {
                    zmiennaWynagrodzeniaFacade.remove(zm);
                    dousuniecia = zm;
                }
            }
            if (dousuniecia!=null) {
                ekwiwalentskladnik.getZmiennawynagrodzeniaList().remove(dousuniecia);
                ekwiwalentSkladnikiFacade.edit(ekwiwalentskladnik);
            }
        }
        try {
            Zmiennawynagrodzenia zmienna = new Zmiennawynagrodzenia(ekwiwalentskladnik);
            zmienna.setAktywna(true);
            zmienna.setNazwa(nazwa);
            zmienna.setDataod(ekwiwalent.getDzienwyplaty());
            zmienna.setDatado(ekwiwalent.getDzienwyplaty());
            zmienna.setNrkolejnyzmiennej(1);
            zmienna.setKwota(ekwiwalent.getKwota());
            zmienna.setNetto0brutto1(true);
            zmienna.setDatadodania(new Date());
            zmienna.setUtworzyl(wpisView.getUzer().getImieNazwisko());
            zmiennaWynagrodzeniaFacade.create(zmienna);
            Msg.msg("Utworzono zmienna Ekwiwalent za urlop");
        } catch (Exception e ) {
            Msg.msg("e","Zapisano już zmienna wypłaty ekwiwalentu. Usuń ją najpierw, aby zapisać nową");
        }
    }


    public void usunekwiwalent() {
        if (ekwiwalent!=null) {
            ekwiwalentSkladnikiFacade.remove(ekwiwalent);
            ekwiwalent = new EkwiwalentUrlop();
            ustawekwiwalent(ekwiwalent, wspolczynnikEkwiwalent.getKwota(), stannadzien);
            obliczekwiwalent(ekwiwalent);
            Msg.msg("Usunięto ekwiwalent");
        } else {
            Msg.dPe();
        }
    }
    
    
    
    private int obliczwymiarwgodzinachchoroba(List<Umowa> umowy) {
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
    
    private int obliczwymiarwgodzinachzasilek(List<Umowa> umowy) {
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
    
    public void obliczekwiwalent(EkwiwalentUrlop ekw) {
        if (ekw.getDziennaliczenia() != null && wpisView.getAngaz().getEtatList().isEmpty() == false) {
            ekw.setKwota(0.0);
            ekw.setAngaz(wpisView.getAngaz());
            ekw.setUmowa(wpisView.getUmowa());
            skladnikistale = new ArrayList<>();
            skladnikizmienne = new ArrayList<>();
            String data = ekw.getDziennaliczenia();
            String pierwszydzienmiesiaca = Data.pierwszyDzien(ekw.getDziennaliczenia());
            String ostatnidzienmiesiaca = Data.ostatniDzien(ekw.getDziennaliczenia());
            stannadzien = ekw.getDziennaliczenia();
            String rok = Data.getRok(data);
            String mc = Data.getMc(data);
            List<Skladnikwynagrodzenia> skladniki = skladnikWynagrodzeniaFacade.findByAngaz(wpisView.getAngaz());
            pobierzurlop();
            double dnipoprzednirok = urlopprezentacja.getBilansotwarciadni();
            double godzinyekw = urlopprezentacja.getDoprzeniesienia();
            ekw.setZalegly((int) dnipoprzednirok);
            ekw.setBiezacy((int) urlopprezentacja.getWymiarokresbiezacydni());
            ekw.setGodziny((int) godzinyekw);
            ekw.setDni(urlopprezentacja.getDoprzeniesieniadni());
            ekw.setRok(rok);
            ekw.setMc(mc);
            ekw.setData(new Date());
            List<EtatPrac> etatlist = etatPracFacade.findByAngaz(wpisView.getAngaz());
            wybranyetat = etatlist.get(0);
            ekw.setEtat1(wybranyetat.getEtat1());
            ekw.setEtat2(wybranyetat.getEtat2());
            if (etatlist.size() > 1) {
                for (EtatPrac e : etatlist) {
                    if (DataBean.czysiemiesci(data, data, e.getDataod(), e.getDatado())) {
                        wybranyetat = e;
                        ekw.setEtat1(e.getEtat1());
                        ekw.setEtat2(e.getEtat2());
                        break;
                    }
                }
            }
            if (godzinyekw > 0) {
                
                double dobowanormaczasupracy = (8.0*(((double)wybranyetat.getEtat1())/((double)wybranyetat.getEtat2())));
                double dniekw = Z.z(godzinyekw/dobowanormaczasupracy);
                //kiedys to bylo tam gdzie zmienne, ale wtedy cofao sie z okrfesem o kilka miesiecy, bylo kilka skladnikow i dla kazdego robil te funkcje co jest bez sensu 08082023
                String dzien = wpisView.getAngaz().getFirma().getDzienlp();
                if (dzien!=null) {
                    String[] popokres = Data.poprzedniOkres(mc, rok);
                    rok = popokres[1];
                    mc = popokres[0];
                }
                for (Skladnikwynagrodzenia p : skladniki) {
                      if (p.getRodzajwynagrodzenia().getStale0zmienne1()==false&&p.getRodzajwynagrodzenia().getWks_serial()!=1014) {
                            for (Zmiennawynagrodzenia r : p.getZmiennawynagrodzeniaList()) {
                                if (DataBean.czysiemiesci(pierwszydzienmiesiaca, ostatnidzienmiesiaca, r.getDataod(), r.getDatado())) {
                                    double kwotastala = r.getKwota();
                                    Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                                    naliczenienieobecnosc.setDatado(data);
                                    naliczenienieobecnosc.setDataod(data);
                                    naliczenienieobecnosc.setSkladnikwynagrodzenia(p);
                                    naliczenienieobecnosc.setSredniazailemcy(1);
                                    naliczenienieobecnosc.setLiczbadniNieobecnosci(dniekw);
                                    naliczenienieobecnosc.setLiczbagodzinNieobecnosci(godzinyekw);
                                double stawkadzienna = kwotastala/wspolczynnikEkwiwalent.getKwota();
                                    naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                                double stawkagodzinowa = stawkadzienna/dobowanormaczasupracy;
                                double kwotaekwu = Z.z(stawkagodzinowa*godzinyekw);
                                    naliczenienieobecnosc.setStawkagodzinowa(stawkagodzinowa);
                                    naliczenienieobecnosc.setKwota(kwotaekwu);
                                    ekw.setKwotastale(kwotaekwu);
                                ekw.setKwota(ekw.getKwota()+kwotaekwu);
                                    skladnikistale.add(naliczenienieobecnosc);
                             }
                        }
                      } else if (p.getRodzajwynagrodzenia().getWks_serial()!=1014) {
                                    List<Kalendarzmiesiac> kalendarzlista = kalendarzmiesiacFacade.findByAngaz(wpisView.getAngaz());
                                   
                                    //jak to bylo to cofal sie o jeden miesiac za daleko. robilem ek2wiwaetn w maju a on zaczylal od marca w dol 02.06.2023
                                    //                            if (dzien!=null) {
                                    //                                String[] popokres = Data.poprzedniOkres(mc, rok);
                                    //                                rok = popokres[1];
                                    //                                mc = popokres[0];
                                    //                            }
                                    //                          przywroilem to 05.06.2023 bo tak mialo byc. Natalia namieszala. nie analizuje
                                    //List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokAngaz(rok, wpisView.getAngaz());
                                    List<Naliczenieskladnikawynagrodzenia> naliczonyskladnikdosredniej = pobierzpaski(rok, mc, p, kalendarzlista);
                                    double godzinyfaktyczne = 0.0;
                                    double dninalezne = 0.0;
                                    double stawkazagodzine = 0.0;
                                    double kwotywyplacone = 0.0;
                                    int liczba = 0;
                                    int dzielnik = 0;
                                    double sredniadopodstawy = 0.0;
                                    Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                                    naliczenienieobecnosc.setSkladnikwynagrodzenia(p);
                                    naliczenienieobecnosc.setDatado(data);
                                    naliczenienieobecnosc.setDataod(data);
                                    naliczenienieobecnosc.setSredniazailemcy(3);
                                    naliczenienieobecnosc.setLiczbadniNieobecnosci(dniekw);
                                    naliczenienieobecnosc.setLiczbagodzinNieobecnosci(godzinyekw);
                                    for (Naliczenieskladnikawynagrodzenia pa : naliczonyskladnikdosredniej) {
                                godzinyfaktyczne = godzinyfaktyczne+pa.getGodzinyfaktyczne();
                                dninalezne = dninalezne+pa.getDninalezne();
                                        kwotywyplacone = kwotywyplacone + pa.getKwotadolistyplac();
                                        liczba++;
                                        boolean skladnikstaly = false;
                                double stawkazagodzinezm = pa.getKwotadolistyplac()/pa.getGodzinynalezne();
                                if (kwotywyplacone>0.0) {
                                            Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(pa.getPasekwynagrodzen().getRok(), pa.getPasekwynagrodzen().getMc(), pa.getKwotadolistyplac(),
                                                    skladnikstaly, naliczenienieobecnosc, godzinyekw, pa.getGodzinyfaktyczne(), pa.getDnifaktyczne(), pa.getGodzinynalezne(), pa.getDninalezne(), stawkazagodzinezm);
                                            naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
                                            naliczenienieobecnosc.setSredniazailemcy(liczba);
                                    dzielnik = dzielnik+1;
                                        }
                                if(liczba>3) {
                                            break;
                                        }
                                    }
                            if (godzinyfaktyczne!=0.0&&dninalezne!=0.0) {
                                dzielnik = dzielnik==0?1:dzielnik;
                                sredniadopodstawy = Z.z(kwotywyplacone/dzielnik);
                                        naliczenienieobecnosc.setSumakwotdosredniej(kwotywyplacone);
                                        naliczenienieobecnosc.setSumagodzindosredniej(godzinyfaktyczne);
                                        naliczenienieobecnosc.setSkladnikizmiennesrednia(sredniadopodstawy);
                                double stawkadzienna = sredniadopodstawy/wspolczynnikEkwiwalent.getKwota();
                                        naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                                stawkazagodzine = Z.z6(stawkadzienna/dobowanormaczasupracy);
                                        naliczenienieobecnosc.setStawkagodzinowa(stawkazagodzine);
                                double kwotaekwu = stawkazagodzine*godzinyekw;
                                        naliczenienieobecnosc.setKwota(kwotaekwu);
                                        ekw.setKwotazmienne(kwotaekwu);
                                ekw.setKwota(ekw.getKwota()+kwotaekwu);
                                        skladnikizmienne.add(naliczenienieobecnosc);
                                    }
                                }
                            }
                Msg.msg("Pobrano ekwiwalent");
                        }
        } else {
            skladnikistale = new ArrayList<>();
            skladnikizmienne = new ArrayList<>();
            ekwiwalent = new EkwiwalentUrlop();
            Msg.msg("w","Brak etatu. nie można obliczyć ekwiwalentu");
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

    public EkwiwalentUrlop getEkwiwalent() {
        return ekwiwalent;
    }

    public void setEkwiwalent(EkwiwalentUrlop ekwiwalent) {
        this.ekwiwalent = ekwiwalent;
    }


    public String getWiekdlachoroby() {
        return wiekdlachoroby;
    }

    public void setWiekdlachoroby(String wiekdlachoroby) {
        this.wiekdlachoroby = wiekdlachoroby;
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
