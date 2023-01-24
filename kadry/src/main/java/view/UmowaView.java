/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.OsobaPropTypFacade;
import DAOsuperplace.WynKodTytFacade;
import beanstesty.NieobecnosciBean;
import beanstesty.PasekwynagrodzenBean;
import beanstesty.UmowaBean;
import comparator.UmowaDataBazacomparator;
import dao.AngazFacade;
import dao.DzienFacade;
import dao.EtatPracFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.KodyzawodowFacade;
import dao.KombinacjaubezpieczenFacade;
import dao.NieobecnoscFacade;
import dao.RodzajnieobecnosciFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.SMTPSettingsFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.StanowiskopracFacade;
import dao.SwiadczeniekodzusFacade;
import dao.UmowaFacade;
import dao.UmowakodzusFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import entity.Angaz;
import entity.Dzien;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kodyzawodow;
import entity.Nieobecnosc;
import entity.Rodzajwynagrodzenia;
import entity.SMTPSettings;
import entity.Skladnikwynagrodzenia;
import entity.Stanowiskoprac;
import entity.Umowa;
import entity.Umowakodzus;
import entity.Zmiennawynagrodzenia;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.WynKodTyt;
import msg.Msg;
import pdf.PdfUmowaoPrace;
import pdf.PdfUmowaoZlecenia;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class UmowaView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private Umowa selected;
    @Inject
    private Umowa selectedlista;
    private List<Umowa> wybraneumowy;
    @Inject
    private EtatPrac etat;
    private List<Umowa> listapraca;
    private List<Umowa> listazlecenia;
    private List<Umowa> listafunkcja;
    private List<Umowa> listawypowiedzenia;
    private List<Angaz> listaangaz;
    private List<Umowakodzus> listaumowakodzus;
    private List<Kodyzawodow> listakodyzawodow;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private DzienFacade dzienFacade;
    @Inject
    private KodyzawodowFacade kodyzawodowFacade;
    @Inject
    private EtatPracFacade etatFacade;
    @Inject
    private StanowiskopracFacade stanowiskopracFacade;
    @Inject
    private UmowakodzusFacade rodzajumowyFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private SwiadczeniekodzusFacade nieobecnosckodzusFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private KombinacjaubezpieczenFacade kombinacjaubezpieczenFacade;
    @Inject
    private OsobaPropTypFacade osobaPropTypFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private SkladnikWynagrodzeniaView skladnikWynagrodzeniaView;
    @Inject
    private ZmiennaWynagrodzeniaView zmiennaWynagrodzeniaView;
    @Inject
    private PasekwynagrodzenView pasekwynagrodzenView;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    @Inject
    private UpdateClassView updateClassView;
    private String datadzisiejsza;
    private String miejscowosc;
    private String rodzajumowy;
    private int tabView;

    @PostConstruct
    public void init() {
        Umowa umowa = umowaFacade.findPracownikAktywna(wpisView.getPracownik());
        if (umowa!=null) {
            wpisView.setUmowa(umowa);
        }
        if (wpisView.getUmowa() != null) {
            if (wpisView.getUmowa().getUmowakodzus()!=null&&wpisView.getUmowa().getUmowakodzus().isPraca()) {
                rodzajumowy = "1";
            } else if (wpisView.getUmowa().getUmowakodzus()!=null&&wpisView.getUmowa().getUmowakodzus().isZlecenie()) {
                rodzajumowy = "2";
            } else if (wpisView.getUmowa().getUmowakodzus()!=null) {
                rodzajumowy = "3";
            }
            if (rodzajumowy == null) {
                rodzajumowy = "1";
            }
            listapraca = umowaFacade.findByAngazPraca(wpisView.getAngaz());
            listawypowiedzenia = umowaFacade.findByAngazPraca(wpisView.getAngaz());
            listazlecenia = umowaFacade.findByAngazZlecenie(wpisView.getAngaz());
            listafunkcja = umowaFacade.findByAngazFunkcja(wpisView.getAngaz());
            if (rodzajumowy.equals("1")) {
                listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywnePraca();
            } else if (rodzajumowy.equals("2")) {
                listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywneZlecenie();
            } else {
                listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywneFunkcja();
            }
        } else {
            rodzajumowy = "1";
            listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywnePraca();
            listapraca = new ArrayList<>();
            listawypowiedzenia = new ArrayList<>();
        }
        //to psuje zmiane pracownika jak ma tyylko umowy zlecenia
//        if (rodzajumowy==null) {
//            rodzajumowy = "1";
//        }

        listaangaz = angazFacade.findByFirma(wpisView.getFirma());

        listakodyzawodow = kodyzawodowFacade.findAll();
        datadzisiejsza = Data.aktualnaData();
        miejscowosc = wpisView.getFirma()!=null?wpisView.getFirma().getMiasto():"brak firmy";
        selected = new Umowa();
        if (selectedlista==null||selectedlista.getId()==null) {
            if (rodzajumowy.equals("1")&&listapraca != null && listapraca.size() > 0 && wpisView.getUmowa() != null && !listapraca.contains(wpisView.getUmowa())) {
                wpisView.setUmowa(listapraca.get(listapraca.size() - 1));
            } else if (rodzajumowy.equals("2")&&listazlecenia != null && listazlecenia.size() > 0 && wpisView.getUmowa() != null && !listazlecenia.contains(wpisView.getUmowa())) {
                wpisView.setUmowa(listazlecenia.get(listazlecenia.size() - 1));
            } else if (rodzajumowy.equals("3")&&listafunkcja != null && listafunkcja.size() > 0 && wpisView.getUmowa() != null && !listafunkcja.contains(wpisView.getUmowa())) {
                wpisView.setUmowa(listafunkcja.get(listafunkcja.size() - 1));
            }
        } else {
            wpisView.setUmowa(selectedlista);
        }
        pasekwynagrodzenView.setSymulacjabrrutto(wpisView.getRokWpisuInt() < 2022 ? 2800 : 3010);
        pasekwynagrodzenView.symulacjaoblicz("1");
    }

    public void wyborinnejumowy() {
        if (rodzajumowy == null) {
            rodzajumowy = "1";
        }
        listaangaz = angazFacade.findByFirma(wpisView.getFirma());
        listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywne();
        listakodyzawodow = kodyzawodowFacade.findAll();
        datadzisiejsza = Data.aktualnaData();
        miejscowosc = wpisView.getFirma().getMiasto();
        //updateClassView.updateUmowa();
    }

    public void wyborinnejumowy2() {
        if (rodzajumowy == null) {
            rodzajumowy = "1";
        }
        if (rodzajumowy.equals("1")) {
            listawypowiedzenia = umowaFacade.findByAngazPraca(wpisView.getAngaz());
        } else if (rodzajumowy.equals("2")) {
            listawypowiedzenia = umowaFacade.findByAngazZlecenie(wpisView.getAngaz());
        } else {
            listawypowiedzenia = umowaFacade.findByAngazFunkcja(wpisView.getAngaz());
        }
    }

    public void create() {
        if (selected != null && wpisView.getAngaz() != null) {
            if (selected.getWynagrodzeniemiesieczne()==0.0 && selected.getWynagrodzeniegodzinowe()==0.0) {
                Msg.msg("e","Nie wprowadzono wynagrodzenia, nie można wygenerować umowy");
            } else {
            if (listapraca != null && listapraca.size()>0) {
                createkolejna(listapraca);
            } else {
               selected.setAngaz(wpisView.getAngaz());
                Umowa umowa = beanstesty.UmowaBean.createpierwsza(selected, umowaFacade, etatFacade, stanowiskopracFacade, rodzajwynagrodzeniaFacade, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                listapraca.add(umowa);
                wpisView.setUmowa(umowa);
            }
            //zrobic dopasowanie kalendarfza do etatu
                skladnikWynagrodzeniaView.init();
                zmiennaWynagrodzeniaView.init();
                Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcAngaz(selected.getAngaz(), selected.getRok(), selected.getMc());
                if (kalendarz == null) {
                    Msg.msg("e", "Brak kalendarza za miesiąc rozpoczęcia umowy");
                }
                List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = PasekwynagrodzenBean.rozpoczecieumowywtrakcieMiesiaca(wpisView.getAngaz(), selected.getDataod(), selected.getDatado(), rodzajnieobecnosciFacade, kalendarz.getRok(), kalendarz.getMc(), kalendarz, null);
                if (zatrudnieniewtrakciemiesiaca != null&&zatrudnieniewtrakciemiesiaca.size()>0) {
                    nieobecnoscFacade.createList(zatrudnieniewtrakciemiesiaca);
                    boolean czynaniesiono = NieobecnosciBean.nanies(zatrudnieniewtrakciemiesiaca.get(0), kalendarzmiesiacFacade, nieobecnoscFacade);
                    if (czynaniesiono == false) {
                        Msg.msg("e", "Wystąpił błąd podczas nanoszenia rozpoczęcia umowy");
                    }
                }
                selected = new Umowa();
                updateClassView.updateUmowa();
                Msg.msg("Dodano nową umowę");
            }
        }
    }
    
     public void createzlecenie() {
        if (selected != null && wpisView.getAngaz() != null) {
            if (listazlecenia != null && listazlecenia.size()>0) {
                createkolejna(listazlecenia);
            } else {
                selected.setAngaz(wpisView.getAngaz());
                Umowa umowa = beanstesty.UmowaBean.createpierwsza(selected, umowaFacade, etatFacade, stanowiskopracFacade, rodzajwynagrodzeniaFacade, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                listapraca.add(umowa);
                wpisView.setUmowa(umowa);
            }
            //zrobic dopasowanie kalendarfza do etatu
                skladnikWynagrodzeniaView.init();
                zmiennaWynagrodzeniaView.init();
                updateClassView.updateUmowa();
                Msg.msg("Dodano nową umowę");
                selected = new Umowa();
        }
    }
    
    
    
    
    public void createkolejna(List<Umowa> listaumowa) {
        if (selected != null && wpisView.getAngaz() != null) {
            try {
                Angaz angaz = wpisView.getAngaz();
                selected.setAngaz(angaz);
                if (selected.getUmowakodzus().isPraca()) {
                    try {
                        String dataodkiedywyplatazasilku = UmowaBean.obliczdatepierwszegozasilku(angaz.getUmowaList(), selected);
                        selected.setPierwszydzienzasilku(dataodkiedywyplatazasilku);
                    } catch (Exception e){}
                }
                selected.setAktywna(true);
                selected.setDatasystem(new Date());
                selected.setLiczdourlopu(true);
                Umowa ostatniaumowa = null;
                String dataostatniejumowy = null;
                String datazamknieciapoprzedniejumowy = Data.odejmijdzien(selected.getDataod(), 1);
                listaumowa = umowaFacade.findByAngaz(angaz);
                int kolejnosc = listaumowa.size() + 1;
                selected.setLicznikumow(kolejnosc);
                int numerpoprzedniejumowy = kolejnosc-1;
                for (Umowa p : listaumowa) {
                    if (p.getLicznikumow()==numerpoprzedniejumowy) {
                        ostatniaumowa = p;
                        if (p.getDatado()!=null&&!p.getDatado().equals("")) {
                            String datado = p.getDatado();
                            String rok = Data.getRok(datado);
                            String mc = Data.getMc(datado);
                            if (selected.getRok().equals(rok)&&selected.getMc().equals(mc)) {
                                dataostatniejumowy = p.getDatado();
                            }
                        } else {
                            p.setDatado(datazamknieciapoprzedniejumowy);
                            umowaFacade.edit(p);
                        }
                    }
                }
                
                umowaFacade.create(selected);
                for (Umowa p : listaumowa) {
                    p.setAktywna(false);
                }
                umowaFacade.editList(listaumowa);
                listaumowa.add(selected);
                wpisView.setUmowa(selected);
                if (selected.getUmowakodzus().isPraca() && selected.getEtat1() != null && selected.getEtat2() != null) {
                    List<EtatPrac> etaty = etatFacade.findByAngaz(angaz);
                    EtatPrac ostatnietat = null;
                    for (EtatPrac e : etaty) {
                        if (e.getDatado()==null||e.getDatado().equals("")) {
                            if (selected.getEtat1()!=e.getEtat1()||selected.getEtat2()!=e.getEtat2()) {
                                e.setDatado(datazamknieciapoprzedniejumowy);
                                etatFacade.edit(e);
                                EtatPrac etat = new EtatPrac(angaz, selected.getDataod(), selected.getDatado(), selected.getEtat1(), selected.getEtat2());
                                etatFacade.create(etat);
                            }
                        }
                    }
                }
                if (selected.getUmowakodzus().isPraca() && selected.getKodzawodu() != null) {
                    List<Stanowiskoprac> etaty = stanowiskopracFacade.findByAngaz(angaz);
                    Stanowiskoprac ostatnietat = null;
                    for (Stanowiskoprac e : etaty) {
                        if (e.getDatado()==null||e.getDatado().equals("")) {
                            if (!e.getOpis().equals(selected.getStanowisko())) {
                                e.setDatado(datazamknieciapoprzedniejumowy);
                                stanowiskopracFacade.edit(e);
                                Stanowiskoprac stanowisko = new Stanowiskoprac(angaz, selected.getDataod(), selected.getDatado(), selected.getStanowisko());
                                stanowiskopracFacade.create(stanowisko);
                            }
                        }
                    }
                    
                }
                if (selected.getWynagrodzeniemiesieczne() != 0.0) {
                    Rodzajwynagrodzenia rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findZasadniczePraca() : rodzajwynagrodzeniaFacade.findZasadniczeZlecenie();
                    Skladnikwynagrodzenia skladnikwynagrodzenia = skladnikWynagrodzeniaFacade.findByAngazRodzaj(selected.getAngaz(),rodzajwynagrodzenia);
                    List<Zmiennawynagrodzenia> etaty = zmiennaWynagrodzeniaFacade.findBySkladnik(skladnikwynagrodzenia);
                    Zmiennawynagrodzenia ostatnietat = null;
                    for (Zmiennawynagrodzenia e : etaty) {
                        if (e.getDatado()==null||e.getDatado().equals("")) {
                            if (e.getKwota()!=selected.getWynagrodzeniemiesieczne()) {
                                e.setDatado(datazamknieciapoprzedniejumowy);
                                zmiennaWynagrodzeniaFacade.edit(e);
                                Zmiennawynagrodzenia zmiennawynagrodzenie = beanstesty.UmowaBean.dodajzmiennawynagrodzenie(skladnikwynagrodzenia,"PLN", selected, 1, zmiennaWynagrodzeniaFacade);
                                if (skladnikwynagrodzenia.getId() != null && zmiennawynagrodzenie != null) {
                                    Msg.msg("Dodano składniki wynagrodzania");
                                }
                            }
                        }
                    }
                }
                if (selected.getWynagrodzeniegodzinowe() != 0.0) {
                    Rodzajwynagrodzenia rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findGodzinowePraca() : rodzajwynagrodzeniaFacade.findGodzinoweZlecenie();
                    Skladnikwynagrodzenia skladnikwynagrodzenia = skladnikWynagrodzeniaFacade.findByAngazRodzaj(selected.getAngaz(),rodzajwynagrodzenia);
                    List<Zmiennawynagrodzenia> etaty = zmiennaWynagrodzeniaFacade.findBySkladnik(skladnikwynagrodzenia);
                    Zmiennawynagrodzenia ostatnietat = null;
                    for (Zmiennawynagrodzenia e : etaty) {
                        if (e.getDatado()==null||e.getDatado().equals("")) {
                            if (e.getKwota()!=selected.getWynagrodzeniegodzinowe()) {
                                e.setDatado(datazamknieciapoprzedniejumowy);
                                zmiennaWynagrodzeniaFacade.edit(e);
                                Zmiennawynagrodzenia zmiennawynagrodzenie = beanstesty.UmowaBean.dodajzmiennawynagrodzenie(skladnikwynagrodzenia,"PLN", selected, 2, zmiennaWynagrodzeniaFacade);
                                if (skladnikwynagrodzenia.getId() != null && zmiennawynagrodzenie != null) {
                                    Msg.msg("Dodano składniki wynagrodzania");
                                }
                            }
                        }
                    }
                }
                if (selected.getWynagrodzenieoddelegowanie() != 0.0) {
                    Rodzajwynagrodzenia rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findGodzinoweOddelegowaniePraca() : rodzajwynagrodzeniaFacade.findGodzinoweOddelegowanieZlecenie();
                    Skladnikwynagrodzenia skladnikwynagrodzenia = skladnikWynagrodzeniaFacade.findByAngazRodzaj(selected.getAngaz(),rodzajwynagrodzenia);
                    List<Zmiennawynagrodzenia> etaty = zmiennaWynagrodzeniaFacade.findBySkladnik(skladnikwynagrodzenia);
                    Zmiennawynagrodzenia ostatnietat = null;
                    for (Zmiennawynagrodzenia e : etaty) {
                        if (e.getDatado()==null||e.getDatado().equals("")) {
                            if (e.getKwota()!=selected.getWynagrodzenieoddelegowanie()) {
                                e.setDatado(datazamknieciapoprzedniejumowy);
                                zmiennaWynagrodzeniaFacade.edit(e);
                                Zmiennawynagrodzenia zmiennawynagrodzenie = beanstesty.UmowaBean.dodajzmiennawynagrodzenie(skladnikwynagrodzenia,selected.getSymbolwalutyoddelegowanie(), selected, 3, zmiennaWynagrodzeniaFacade);
                                if (skladnikwynagrodzenia.getId() != null && zmiennawynagrodzenie != null) {
                                    Msg.msg("Dodano składniki wynagrodzania");
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano nowej umowy. Sprawdź angaż");
            }
        }
    }

    public void edytujumowe(Umowa umowa) {
        try {
            if (umowa.getId() != null) {
                umowaFacade.edit(umowa);
                Msg.msg("Naniesiono zmiany");
            }
        } catch (Exception e) {
        }
    }

    public void edit() {
        if (selected != null && selected.getId() != null) {
            try {
                if (selected.getDataod() != null) {
                    List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = nieobecnoscFacade.findByAngaz200(wpisView.getAngaz());
                    if (zatrudnieniewtrakciemiesiaca != null) {
                        for (Nieobecnosc p : zatrudnieniewtrakciemiesiaca) {
                            List<Dzien> dzienList = p.getDzienList();
                            List<Dzien> wzorcowe = dzienFacade.findByNrwrokuByData(p.getDataod(), p.getDatado(), wpisView.getFirma());
                            for (Dzien d : dzienList) {
                                d.setNieobecnosc(null);
                                dzienFacade.edit(d);
                                try {
                                    d.nanieswzorcowe(wzorcowe);
                                } catch (Exception e) {
                                }
                                dzienFacade.edit(d);
                            }
                            p.setDzienList(null);
                            nieobecnoscFacade.edit(p);
                        }
                        nieobecnoscFacade.removeList(zatrudnieniewtrakciemiesiaca);
                        zatrudnieniewtrakciemiesiaca = null;
                    }
                }
                if (selected.getUmowakodzus().isPraca()) {
                    try {
                        String dataodkiedywyplatazasilku = UmowaBean.obliczdatepierwszegozasilku(wpisView.getAngaz().getUmowaList(), selected);
                        selected.setPierwszydzienzasilku(dataodkiedywyplatazasilku);
                    } catch (Exception e){}
                }
                selected.setDatasystem(new Date());
                selected.setLiczdourlopu(true);
                umowaFacade.edit(selected);
                String dataostatniejumowy = null;
                if (listapraca != null && !listapraca.isEmpty()) {
                    int kolejnosc = listapraca.size() + 1;
                    selected.setLicznikumow(kolejnosc);
                    int numerpoprzedniejumowy = kolejnosc-1;
                    for (Umowa p : listapraca) {
                        if (p.getLicznikumow()==numerpoprzedniejumowy) {
                            if (p.getRok().equals(selected.getRok())&&p.getMc().equals(selected.getMc())) {
                                dataostatniejumowy = p.getDatado();
                            }
                        }
                    }
                }
                wpisView.setUmowa(selected);
                String rok = Data.getRok(selected.getDataod());
                String mc = Data.getMc(selected.getDataod());
                Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcAngaz(selected.getAngaz(), selected.getRok(), selected.getMc());
                List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = PasekwynagrodzenBean.rozpoczecieumowywtrakcieMiesiaca(selected.getAngaz(), selected.getDataod(), selected.getDatado(), rodzajnieobecnosciFacade, rok, mc, kalendarz, dataostatniejumowy);
                if (zatrudnieniewtrakciemiesiaca != null) {
                    nieobecnoscFacade.createList(zatrudnieniewtrakciemiesiaca);
                }
                selected = new Umowa();
                Msg.msg("Edycja umowy zakończona");
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie zmieniono danych nowej umowy. Sprawdź angaż.");
                Msg.msg("e", "Czy istnieją już listy płac? Usuń je.");
            }
        }
    }

    public void obliczwiek() {
        String zwrot = "";
        String dataurodzenia = wpisView.getAngaz().getPracownik().getDataurodzenia();
        if (dataurodzenia == null) {
            Msg.msg("e", "Brak daty urodzenia pracownika");
        } else if (selected != null && selected.getDataod() != null) {
            LocalDate dataur = LocalDate.parse(dataurodzenia);
            LocalDate dataumowy = LocalDate.parse(selected.getDataod());
            String rok = Data.getRok(selected.getDataod());
            String pierwszydzienroku = rok + "-01-01";
            LocalDate dataroku = LocalDate.parse(pierwszydzienroku);
            long lata = ChronoUnit.YEARS.between(dataur, dataumowy);
            long dni = ChronoUnit.DAYS.between(dataroku, dataumowy);
            selected.setLata((int) lata);
            selected.setDni((int) dni);
        }
    }

    public void sprawdzczyumowajestnaczas() {
        if (selected.getDatado() != null) {
            if (selected.getSlownikszkolazatrhistoria() != null && selected.getSlownikszkolazatrhistoria().getSymbol().equals("P")) {
                Msg.msg("e", "Wybrano umowę na czas nieokreślony a wprowadzono datę do!");
            }
        }
    }

    public void cancel() {
        try {
            wpisView.setUmowa(null);
            selected = new Umowa();
            Msg.msg("Reset umowy zakończony");
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie dodano resetu umowy.");
        }
    }

    public void aktywuj() {
        if (selectedlista != null) {
            wpisView.setUmowa(selectedlista);
            wpisView.setFirma(selectedlista.getAngaz().getFirma());
            wpisView.setPracownik(selectedlista.getAngaz().getPracownik());
            updateClassView.updateUmowa();
            Msg.msg("Aktywowano umowę");
        }
    }

    public void ustawumowe() {
        int numerkolejny = 1;
        List<Umowa> umowaList = umowaFacade.findByAngaz(wpisView.getAngaz());
        if (umowaList != null) {
            numerkolejny = numerkolejny + umowaList.size();
        }
        if (selected.getId() == null) {
            if (selected.getUmowakodzus().isPraca()) {
                selected.setNrkolejny("UP/" + numerkolejny + "/" + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu());
                selected.setChorobowe(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setNfz("16R");
            } else if (selected.getUmowakodzus().isZlecenie()) {
                selected.setNrkolejny("UC/" + numerkolejny + "/" + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu());
                selected.setChorobowe(false);
                selected.setChorobowedobrowolne(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setNfz("16R");
            }
        }
    }

    public void usun(Umowa umowa) {
        if (umowa != null) {
            wpisView.setUmowa(null);
            umowaFacade.remove(umowa);
            listapraca.remove(umowa);
            listapraca = umowaFacade.findByAngaz(wpisView.getAngaz());
            for (Umowa p : listapraca) {
                p.setAktywna(false);
            }
            if (listapraca!=null&&listapraca.size()>0) {
                Umowa get = listapraca.get(listapraca.size()-1);
                get.setAktywna(true);
            }
            umowaFacade.editList(listapraca);
            Msg.msg("Usunięto umowę o pracę");
        } else {
            Msg.msg("e", "Nie wybrano umowy");
        }
    }
    
    public void usunfunkcja(Umowa umowa) {
        if (umowa != null) {
            wpisView.setUmowa(null);
            umowaFacade.remove(umowa);
            listafunkcja.remove(umowa);
            listafunkcja = umowaFacade.findByAngazFunkcja(wpisView.getAngaz());
            for (Umowa p : listafunkcja) {
                p.setAktywna(false);
            }
            if (listafunkcja!=null&&listafunkcja.size()>0) {
                Umowa get = listafunkcja.get(listafunkcja.size()-1);
                get.setAktywna(true);
            }
            umowaFacade.editList(listafunkcja);
            Msg.msg("Usunięto umowę o pełnienie funkcji");
        } else {
            Msg.msg("e", "Nie wybrano umowy");
        }
    }
    
    public void usunzlecenie(Umowa umowa) {
        if (umowa != null) {
            wpisView.setUmowa(null);
            umowaFacade.remove(umowa);
            listazlecenia.remove(umowa);
            listazlecenia = umowaFacade.findByAngazZlecenie(wpisView.getAngaz());
            for (Umowa p : listazlecenia) {
                p.setAktywna(false);
            }
            if (listazlecenia!=null&&listazlecenia.size()>0) {
                Umowa get = listazlecenia.get(listazlecenia.size()-1);
                get.setAktywna(true);
            }
            umowaFacade.editList(listazlecenia);
            Msg.msg("Usunięto umowę zlecenia");
        } else {
            Msg.msg("e", "Nie wybrano umowy");
        }
    }

    public void edytuj(Umowa umowa) {
        if (umowa != null) {
            selected = umowa;
            Msg.msg("Wybrano umowę do edycji");
        } else {
            Msg.msg("e", "Nie wybrano umowy");
        }
    }
    
    public void edytujzapisz(Umowa umowa) {
        if (umowa != null) {
            umowaFacade.edit(umowa);
            Msg.msg("Naniesiono zmiany");
        } else {
            Msg.msg("e", "Nie wybrano umowy");
        }
    }

//    public void dodatetat() {
//        if (etat!=null&&etat.getDataod()!=null) {
//            etatFacade.create(etat);
//            selected.getEtatList().add(etat);
//            umowaFacade.edit(selected);
//        }
//    }
//    
    public void naniesdatynaumowe() {
        if (selected != null && selected.getDatazawarcia() != null) {
            String rok = selected.getDatazawarcia().substring(0,4);
            int rokI = Integer.parseInt(rok);
            if (rokI<2022) {
                selected.setDatazawarcia(null);
                Msg.msg("e","Umowa nie może być z wcześniejszego roku niż 2022");
            } else {
                selected.setDataspoleczne(selected.getDatazawarcia());
                selected.setDatazdrowotne(selected.getDatazawarcia());
                selected.setDataod(selected.getDatazawarcia());
                selected.setTerminrozpoczeciapracy(selected.getDatazawarcia());
                obliczwiek();
            }
        }
    }

    
    public void kopiujostatniaumowe() {
        List<Umowa> pobraneumowy = null;
        if (rodzajumowy.equals("1")) {
            pobraneumowy = umowaFacade.findByFirmaPraca(wpisView.getFirma(), true);
        } else {
            pobraneumowy = umowaFacade.findByFirmaZlecenie(wpisView.getFirma(), true);
        }
        if (pobraneumowy!=null&&pobraneumowy.size()>0) {
            try {
                Collections.sort(pobraneumowy, new UmowaDataBazacomparator());
                selected = new Umowa(pobraneumowy.get(0), false);
                selected.setAngaz(wpisView.getAngaz());
                ustawumowe();
                Msg.msg("Skopiowano umowę");
            } catch (Exception e){
                Msg.msg("e","Brak umowy sporządzonej w nowym programie");
            }
        } else {
            Msg.msg("e","Brak umowy do skopiowania");
        }
    }
    
    
    

    

   

    public void oznaczjakoaktywna() {
        if (selectedlista != null) {
            for (Umowa p : listapraca) {
                p.setAktywna(false);
            }
            umowaFacade.editList(listapraca);
            selectedlista.setAktywna(true);
            umowaFacade.edit(selectedlista);
            Msg.msg("Oznaczono umowę");
        }
    }

    public void przedluz() {
        if (selectedlista != null) {
            tabView = 1;
            selected = new Umowa(selectedlista);
            Msg.msg("Przygotowano dane");
        } else {
            Msg.msg("e", "Nie wybrano umowy");
        }
    }

    public void drukujumoweselected(Umowa praca) {
        if (praca != null) {
            String nazwa = praca.getAngaz().getPracownik().getPesel()+"umowaoprace.pdf";
            PdfUmowaoPrace.drukuj(praca, nazwa);
        } else {
            Msg.msg("e", "Nie wybrano umowy");
        }
    }
    
     public void mailUmowaPraca(Umowa praca) {
         if (praca==null) {
                Msg.msg("w", "Nie wybrano umowy");
            } else {
                FirmaKadry firmaKadry = wpisView.getFirma();
                String nazwa = praca.getAngaz().getPracownik().getPesel()+"umowaoprace.pdf";
                ByteArrayOutputStream drukujmail = PdfUmowaoPrace.drukuj(praca, nazwa);
                SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                mail.Mail.mailUmowyoPrace(wpisView.getFirma(), wpisView.getFirma().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa, wpisView.getUzer().getEmail());
                Msg.msg("Wysłano umowę o pracę do klienta");
         }
    }

    public void drukujzlecenie(Umowa zlecenie) {
        if (zlecenie != null) {
            String nazwa = zlecenie.getAngaz().getPracownik().getPesel()+"umowa.pdf";
            PdfUmowaoZlecenia.drukuj(zlecenie, nazwa);
        } else {
            Msg.msg("e", "Nie wybrano umowy");
        }
    }
    
     public void mailUmowaZlecenia(Umowa zlecenie) {
         if (zlecenie==null) {
                Msg.msg("w", "Nie wybrano umowy");
            } else {
                FirmaKadry firmaKadry = wpisView.getFirma();
                String nazwa = zlecenie.getAngaz().getPracownik().getPesel()+"umowazlecenia.pdf";
                ByteArrayOutputStream drukujmail = PdfUmowaoZlecenia.drukuj(zlecenie, nazwa);
                SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                mail.Mail.mailUmowyZlecenia(wpisView.getFirma(), wpisView.getFirma().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa, wpisView.getUzer().getEmail());
                Msg.msg("Wysłano umowę zlecenia do klienta");
         }
    }
    
    public Umowa getSelected() {
        return selected;
    }

    public void setSelected(Umowa selected) {
        this.selected = selected;
    }

    public List<Umowa> getListapraca() {
        return listapraca;
    }

    public void setListapraca(List<Umowa> listapraca) {
        this.listapraca = listapraca;
    }

    public Umowa getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Umowa selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Angaz> getListaangaz() {
        return listaangaz;
    }

    public void setListaangaz(List<Angaz> listaangaz) {
        this.listaangaz = listaangaz;
    }

    public List<Umowakodzus> getListaumowakodzus() {
        return listaumowakodzus;
    }

    public void setListaumowakodzus(List<Umowakodzus> listaumowakodzus) {
        this.listaumowakodzus = listaumowakodzus;
    }

    public EtatPrac getEtat() {
        return etat;
    }

    public void setEtat(EtatPrac etat) {
        this.etat = etat;
    }

    public List<Kodyzawodow> getListakodyzawodow() {
        return listakodyzawodow;
    }

    public void setListakodyzawodow(List<Kodyzawodow> listakodyzawodow) {
        this.listakodyzawodow = listakodyzawodow;
    }


    public String getDatadzisiejsza() {
        return datadzisiejsza;
    }

    public void setDatadzisiejsza(String datadzisiejsza) {
        this.datadzisiejsza = datadzisiejsza;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getRodzajumowy() {
        return rodzajumowy;
    }

    public void setRodzajumowy(String rodzajumowy) {
        this.rodzajumowy = rodzajumowy;
    }


    public List<Umowa> getWybraneumowy() {
        return wybraneumowy;
    }

    public void setWybraneumowy(List<Umowa> wybraneumowy) {
        this.wybraneumowy = wybraneumowy;
    }

    public int getTabView() {
        return tabView;
    }

    public void setTabView(int tabView) {
        this.tabView = tabView;
    }

    public List<Umowa> getListawypowiedzenia() {
        return listawypowiedzenia;
    }

    public void setListawypowiedzenia(List<Umowa> listawypowiedzenia) {
        this.listawypowiedzenia = listawypowiedzenia;
    }

    public List<Umowa> getListazlecenia() {
        return listazlecenia;
    }

    public void setListazlecenia(List<Umowa> listazlecenia) {
        this.listazlecenia = listazlecenia;
    }

    public List<Umowa> getListafunkcja() {
        return listafunkcja;
    }

    public void setListafunkcja(List<Umowa> listafunkcja) {
        this.listafunkcja = listafunkcja;
    }

    public static void main(String[] args) {
        String dataurodzenia = "1970-05-28";
        LocalDate dataur = LocalDate.parse(dataurodzenia);
        LocalDate dataumowy = LocalDate.parse("2021-11-14");
        String rok = Data.getRok("2021-11-14");
        String pierwszydzienroku = rok + "-01-01";
        LocalDate dataroku = LocalDate.parse(pierwszydzienroku);
        long lata = ChronoUnit.YEARS.between(dataur, dataumowy);
        long dni = ChronoUnit.DAYS.between(dataroku, dataumowy);
    }

    @Inject
    private WynKodTytFacade wynKodTytFacade;
    @Inject
    private UmowakodzusFacade umowakodzusFacade;

    public void generujtabele() {
        Msg.msg("Start");
        List<WynKodTyt> findAll = wynKodTytFacade.findAll();
        for (WynKodTyt p : findAll) {
            Umowakodzus s = new Umowakodzus();
            s.setKod(p.getWktKod());
            s.setOpis(p.getWktOpis());
            s.setWkt_serial(p.getWktSerial());
            s.setPraca(p.getWktUmZlec().equals('N'));
            s.setZlecenie(p.getWktUmZlec().equals('T'));
            umowakodzusFacade.create(s);
        }
        Msg.dP();
    }

//    public void rob() {
//        List<OsobaPropTyp> listapraca = osobaPropTypFacade.findAll();
//        for (OsobaPropTyp p : listapraca) {
//            Kombinacjaubezpieczen k = new Kombinacjaubezpieczen(p);
//            try {
//                kombinacjaubezpieczenFacade.create(k);
//            } catch (Exception e) {
//                E.e(e);
//            }
//        }
//    }
}
