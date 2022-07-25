/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.BilansBean;
import beansFK.PlanKontFKBean;
import comparator.Kontocomparator;
import comparator.RoznicaSaldBOcomparator;
import comparator.SaldoKontocomparator;
import comparator.StronaWierszacomparatorDesc;
import dao.DokDAOfk;
import dao.KontoDAOfk;
import dao.UkladBRDAO;
import dao.WalutyDAOfk;
import dao.WierszBODAO;
import embeddablefk.RoznicaSaldBO;
import embeddablefk.SaldoKonto;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import entityfk.Waluty;
import entityfk.WierszBO;
import error.E;
import extclass.ReverseIterator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdffk.PdfBilansGen;
import testobjects.WierszBO_T;
import static testobjects.testobjects.getKlienci;
import static testobjects.testobjects.getPodatnik;
import static testobjects.testobjects.getRodzajedok;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class BilansGenerowanieView implements Serializable {

    @Inject
    private WpisView wpisView;
    @Inject
    private SaldoAnalitykaView saldoAnalitykaView;
    @Inject
    private BilansWprowadzanieView bilansWprowadzanieView;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private DokDAOfk dokDAOfk;
    private Map<String, Waluty> listawalut;
    private List<String> komunikatyok;
    private List<String> komunikatyerror;
    private List<String> komunikatyerror2;
    private List<String> komunikatyerror3;
    private boolean sabledy;
    private boolean sabledy2;
    private boolean przeniestylkosalda;
    private boolean tojestbilanslikwidacyjny;
    private List<RoznicaSaldBO> kontainnesaldo;
    private boolean tylkoroznicowy;

    public BilansGenerowanieView() {
        ////E.m(this);
        this.komunikatyok = Collections.synchronizedList(new ArrayList<>());
        this.komunikatyok.add("Nie rozpoczęto analizy");
        this.komunikatyok.add("Nie rozpoczęto analizy");
        this.komunikatyerror = Collections.synchronizedList(new ArrayList<>());
        this.komunikatyerror2 = Collections.synchronizedList(new ArrayList<>());
        this.komunikatyerror3 = Collections.synchronizedList(new ArrayList<>());
        this.listawalut = new ConcurrentHashMap<>();
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        List<Waluty> waluty = walutyDAOfk.findAll();
        for (Waluty p : waluty) {
            listawalut.put(p.getSymbolwaluty(), p);
        }
        Dokfk dokbo = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (dokbo!=null) {
            this.komunikatyok.add("Istnieje już bilans BO, można wygenerować jedynie BO różnicowy");
            tylkoroznicowy = true;
            przeniestylkosalda = true;
        } else {
            tylkoroznicowy = false;
            przeniestylkosalda = false;
        }
    }
    
    public void resetlikiwdacja() {
        this.tojestbilanslikwidacyjny = true;
        reset();
    }

    public void reset() {
        this.komunikatyok = Collections.synchronizedList(new ArrayList<>());
        this.komunikatyok.add("Nie rozpoczęto analizy");
        this.komunikatyerror = Collections.synchronizedList(new ArrayList<>());
        this.komunikatyerror2 = Collections.synchronizedList(new ArrayList<>());
        this.komunikatyerror3 = Collections.synchronizedList(new ArrayList<>());
        this.listawalut = new ConcurrentHashMap<>();
         List<Waluty> waluty = walutyDAOfk.findAll();
        for (Waluty p : waluty) {
            listawalut.put(p.getSymbolwaluty(), p);
        }
        Dokfk dokbo = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (dokbo!=null) {
            this.komunikatyok.add("Istnieje już bilans BO, można wygenerować jedynie BO różnicowy");
            tylkoroznicowy = true;
            przeniestylkosalda = true;
        } else {
            tylkoroznicowy = false;
            przeniestylkosalda = false;
        }
    }
    
   


    public static void generujbilans() {
        String podatnik = "Lolo";
        List<testobjects.Konto> saldakont = pobierzkonta(podatnik);
        List<testobjects.WierszBO_T> wierszeBO = generujwierszeBO(saldakont);
        assert wierszeBO.size() == 10 : "za malo wierszy " + wierszeBO.size();
        assert wierszeBO.get(9).getKwotaWn() == 150.0 : "nieprawidlowa kwota " + wierszeBO.get(9).getKwotaWn();
        boolean dokumentusuniety = usunpoprzednidokumentBO(podatnik);
        assert dokumentusuniety == true : "blad nie usunieto dokumentu";
        if (dokumentusuniety) {
            Dokfk nowydokBO = generujNowyDokBO(wierszeBO, podatnik);
            assert nowydokBO.getNumerwlasnydokfk().equals("1/23/345/z") : "nie wygenerowano poprawnie";
        }
    }

    private static List<testobjects.Konto> pobierzkonta(String podatnik) {
        int i = 1;
        List<testobjects.Konto> konta = Collections.synchronizedList(new ArrayList<>());
        konta.add(new testobjects.Konto(i++, podatnik, "Konto010", "010", 2016, 150, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto020", "020", 2016, 450, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto100", "100", 2016, 1250, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto201", "201", 2016, 1750, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto202", "202", 2016, 0, 1950, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto300", "300", 2016, 400, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto645", "645", 2016, 0, 550, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto821", "821", 2016, 0, 2000, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto860", "860", 2016, 150, 0, "bilansowe"));
        konta.add(new testobjects.Konto(i++, podatnik, "Konto871", "871", 2016, 150, 0, "bilansowe"));
        return konta;
    }

    private static List<testobjects.WierszBO_T> generujwierszeBO(List<testobjects.Konto> saldakont) {
        List<testobjects.WierszBO_T> wierszeBO = Collections.synchronizedList(new ArrayList<>());
        if (!saldakont.isEmpty()) {
            for (testobjects.Konto p : saldakont) {
                wierszeBO.add(new testobjects.WierszBO_T(p.getPodatnik(), p, p.getSaldoWn(), p.getSaldoMa()));
            }
        }
        return wierszeBO;
    }

    public static void main(String[] args) {
        generujbilans();
    }

    private static boolean usunpoprzednidokumentBO(String podatnik) {
        return true;
    }

    private static Dokfk generujNowyDokBO(List<WierszBO_T> wierszeBO, String podatnik) {
        Dokfk nowydok = getDokfk("BO", podatnik);
        return nowydok;
    }

    public static Dokfk getDokfk(String rodzaj, String podatnik) {
        Dokfk d = new Dokfk(12, "2015");
        d.setDatadokumentu("2015-03-01");
        d.setDataoperacji("2015-03-02");
        d.setDatawplywu("2015-03-05");
        d.setDatawystawienia("2015-03-06");
        d.setRodzajedok(getRodzajedok(rodzaj));
        d.setPodatnikObj(getPodatnik());
        d.setKontr(getKlienci());
        d.setNumerwlasnydokfk("1/23/345/z");
        d.setMiesiac("02");
        return d;
    }

    public void generuj() {
        try {
            this.komunikatyok = Collections.synchronizedList(new ArrayList<>());
            this.komunikatyerror = Collections.synchronizedList(new ArrayList<>());
            this.komunikatyerror2 = Collections.synchronizedList(new ArrayList<>());
            this.komunikatyerror3 = Collections.synchronizedList(new ArrayList<>());
            this.sabledy = false;
            this.sabledy2 = false;
            boolean stop = false;
            List<Konto> konta = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            if (konta.isEmpty()) {
                stop = true;
                String error = "Brak kont w roku " + wpisView.getRokWpisuSt() + " nie można generować BO";
                Msg.msg("e", error);
                komunikatyerror.add(error);
            } else {
                komunikatyok.add("Sprawdzono obecnosc planu kont. Liczba kont: " + konta.size());
            }
            List<UkladBR> uklad = ukladBRDAO.findukladBRPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            if (uklad.isEmpty()) {
                stop = true;
                String error = "Brak ukladu w roku " + wpisView.getRokWpisuSt() + " nie można generować BO";
                Msg.msg("e", error);
                komunikatyerror.add(error);
            } else {
                komunikatyok.add("Sprawdzono obecnosc układu. Liczba ukladów: " + uklad.size());
            }
            if (stop == true) {
                sabledy = true;
            } else {
                Dokfk dokbo = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                if (dokbo != null) {
                    dokDAOfk.remove(dokbo);
                } else {
                    Msg.msg("w","Nie znaleziono dokumentu BO. Albo go nie ma, albo jesteś w niewłaściwym miesiącu");
                }
                wierszBODAO.deletePodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                Waluty walpln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
                saldoAnalitykaView.initGenerowanieBO();
                List<SaldoKonto> listaSaldoKontoRokPop = saldoAnalitykaView.getListaSaldoKonto();
                Collections.sort(listaSaldoKontoRokPop, new SaldoKontocomparator());
                List<Konto> kontaNowyRok = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                resetujBOnaKonto(kontaNowyRok);
                Konto kontowyniku = PlanKontFKBean.findKonto860(kontaNowyRok);
                obliczkontawynikowe(kontowyniku, listaSaldoKontoRokPop, walpln);
                //tutaj trzeba przerobic odpowiednio listaSaldo
                List<SaldoKonto> listaSaldoKontoPrzetworzone = przetwarzajSaldoKonto(listaSaldoKontoRokPop);
                List<WierszBO> wierszeBO = Collections.synchronizedList(new ArrayList<>());
                List<Konto> kontazdziecmi = Collections.synchronizedList(new ArrayList<>());
                List<Konto> brakujacekontanowyrok = zrobwierszeBO(wierszeBO, listaSaldoKontoPrzetworzone, kontaNowyRok, kontazdziecmi);
                if (!brakujacekontanowyrok.isEmpty()) {
                    komunikatyerror.add("W nowym roku nie ma następujących kont w planie kont: ");
                    for (Konto p : brakujacekontanowyrok) {
                        komunikatyerror.add(p.getPelnynumer() + " " + p.getNazwapelna());
                        sabledy = true;
                    }
                }
                if (!kontazdziecmi.isEmpty()) {
                    komunikatyerror2.add("W nowym roku następujące konta mają subkonta, trzeba przeksięgować kwoty na subkonto dla kwot z następujacych kont poprzedniego roku: ");
                    for (Konto p : kontazdziecmi) {
                        komunikatyerror2.add(p.getPelnynumer() + " " + p.getNazwapelna());
                        sabledy2 = true;
                    }
                }
                komunikatyok.add("Wygenerowano BO na rok " + wpisView.getRokWpisuSt());
                wierszBODAO.editList(wierszeBO);
                zapiszBOnaKontach(wierszeBO, kontaNowyRok);
                obliczsaldoBOkonta(kontaNowyRok);
                kontoDAO.editList(kontaNowyRok);
                this.komunikatyerror3 = Collections.synchronizedList(new ArrayList<>());
                kontainnesaldo = znajdzroznicesald(listaSaldoKontoRokPop, kontaNowyRok);
                if (!kontainnesaldo.isEmpty()) {
                    komunikatyerror3.add("Po generacji jest różnica sald z BO. Sprawdź w poprzednim roku wiersze BO z dokumentem BO: ");
                    for (RoznicaSaldBO p : kontainnesaldo) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(p.getKonto().getPelnynumer() + " winno być ");
                        sb.append(format.F.curr(p.getWinnobyc()) + " strona ");
                        sb.append(p.getWinnobycStrona() + " jest ");
                        sb.append(format.F.curr(p.getJest()) + " strona ");
                        sb.append(p.getJestStrona() + " roznica ");
                        sb.append(p.getKwotaroznicy());
                        komunikatyerror3.add(sb.toString());
                        if (p.getKonto().getPelnynumer().equals("860")) {
                            sb = new StringBuilder();
                            sb.append("Chyba w pop. roku nie przeksięgowano wyniku finansowego z 860 na 820");
                            komunikatyerror3.add(sb.toString());
                        }
                    }
                }
                bilansWprowadzanieView.init();
                bilansWprowadzanieView.zapiszBilansBOdoBazy();
                Msg.msg("Generuje bilans");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd podczas generowania bilansu otwarcia. Czy  nie naniesiono już jakiś rozrachunków? Trzeba usunąć dokumenty źródłowe "+e);
        }
    }
    
    public void generujBoRoznicowy() {
        try {
                this.komunikatyok = Collections.synchronizedList(new ArrayList<>());
                this.komunikatyerror = Collections.synchronizedList(new ArrayList<>());
                this.komunikatyerror2 = Collections.synchronizedList(new ArrayList<>());
                this.komunikatyerror3 = Collections.synchronizedList(new ArrayList<>());
                Dokfk dokbo = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                if (dokbo == null) {
                    Msg.msg("w","Nie znaleziono dokumentu BO. Nie ma sensu generować BO różnicowego");
                    return;
                }
                
                Waluty walpln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
                saldoAnalitykaView.initGenerowanieBO();
                List<SaldoKonto> listaSaldoKontoRokPop = saldoAnalitykaView.getListaSaldoKonto();
                Collections.sort(listaSaldoKontoRokPop, new SaldoKontocomparator());
                List<Konto> kontaNowyRok = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                resetujBOnaKonto(kontaNowyRok);
                Konto kontowyniku = PlanKontFKBean.findKonto860(kontaNowyRok);
                obliczkontawynikowe(kontowyniku, listaSaldoKontoRokPop, walpln);
                //tutaj trzeba przerobic odpowiednio listaSaldo
                List<SaldoKonto> listaSaldoKontoPrzetworzone = przetwarzajSaldoKontoBoRoznicowe(listaSaldoKontoRokPop);
                List<WierszBO> wierszeBO = wierszBODAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                List<WierszBO> wierszeBOroznicowe = zrobwierszeBORoznicowy(wierszeBO, listaSaldoKontoPrzetworzone, kontaNowyRok);
                if (wierszeBOroznicowe.isEmpty()) {
                    komunikatyok.add("Brak roznic miedzy wygenerowanym a obecnym BO " + wpisView.getRokWpisuSt());
                } else {
                    komunikatyok.add("Wygenerowano BO różnicowy na rok " + wpisView.getRokWpisuSt());
                    wierszBODAO.editList(wierszeBOroznicowe);
                    zapiszBOnaKontach(wierszeBOroznicowe, kontaNowyRok);
                    zapiszBOnaKontach(wierszeBO, kontaNowyRok);
                    obliczsaldoBOkonta(kontaNowyRok);
                    kontoDAO.editList(kontaNowyRok);
                    kontainnesaldo = znajdzroznicesald(listaSaldoKontoRokPop, kontaNowyRok);
                    if (!kontainnesaldo.isEmpty()) {
                        komunikatyerror3.add("Po generacji jest różnica sald z BO. Sprawdź w poprzednim roku wiersze BO z dokumentem BO: ");
                        for (RoznicaSaldBO p : kontainnesaldo) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(p.getKonto().getPelnynumer() + " winno być ");
                            sb.append(format.F.curr(p.getWinnobyc()) + " strona ");
                            sb.append(p.getWinnobycStrona() + " jest ");
                            sb.append(format.F.curr(p.getJest()) + " strona ");
                            sb.append(p.getJestStrona() + " roznica ");
                            sb.append(p.getKwotaroznicy());
                            komunikatyerror3.add(sb.toString());
                            if (p.getKonto().getPelnynumer().equals("860")) {
                                sb = new StringBuilder();
                                sb.append("Chyba w pop. roku nie przeksięgowano wyniku finansowego z 860 na 820");
                                komunikatyerror3.add(sb.toString());
                            }
                        }
                    }
                    bilansWprowadzanieView.init();
                    bilansWprowadzanieView.zapiszBilansBOdoBazy();
                    Msg.msg("Generuje bilans");
                }
                
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd podczas generowania bilansu otwarcia. Czy  nie naniesiono już jakiś rozrachunków? Trzeba usunąć dokumenty źródłowe "+e);
        }
    }
    
    public void naniesroznice() {
        try {
            Waluty walpln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
            List<WierszBO> wierszeBO = Collections.synchronizedList(new ArrayList<>());
            for (RoznicaSaldBO p : kontainnesaldo) {
                if (Math.abs(p.getKwotaroznicy()) < 1) {
                    SaldoKonto sk = new SaldoKonto(p, walpln);
                    wierszeBO.add(new WierszBO(wpisView.getPodatnikObiekt(), sk, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), p.getKonto(), walpln, wpisView.getUzer()));
                }
            }
            wierszBODAO.createList(wierszeBO);
            Msg.msg("Naniesiono wiersze różnicowe");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd podczas nanoszenia wierszy różnicowych "+e);
        }
    }

    public void drukuj() {
        try {
            String nazwa = "bogenerr"+wpisView.getPodatnikObiekt().getNip();
            PdfBilansGen.drukujbilansgen(nazwa, komunikatyerror, komunikatyerror2, komunikatyerror3, wpisView.getUzer());
            Msg.dP();
        } catch (Exception e){
            Msg.dPe();
        }
    }
    
    private List<RoznicaSaldBO> znajdzroznicesald(List<SaldoKonto> listaSaldoKontoRokPop, List<Konto> kontaNowyRok) {
        List<RoznicaSaldBO> listaroznic = Collections.synchronizedList(new ArrayList<>());
        for (SaldoKonto p : listaSaldoKontoRokPop) {
            try {
                if (p.getKonto().getBilansowewynikowe().equals("bilansowe")) {
                    Konto nowe = kontaNowyRok.get(kontaNowyRok.indexOf(p.getKonto()));
                    if (Z.zAbs(p.getSaldoWn()) != Z.zAbs(nowe.getBoWn())) {
                       listaroznic.add(new RoznicaSaldBO(nowe, Z.z(nowe.getBoWn()-p.getSaldoWn()), nowe.getBoWn(), "Wn", p.getSaldoWn(), "Wn"));
                    } 
                    if (Z.zAbs(p.getSaldoMa()) != Z.zAbs(nowe.getBoMa())) {
                        listaroznic.add(new RoznicaSaldBO(nowe, Z.z(nowe.getBoMa()-p.getSaldoMa()), nowe.getBoMa(), "Ma", p.getSaldoMa(), "Ma"));
                    }
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
        Collections.sort(listaroznic, new RoznicaSaldBOcomparator());
        return listaroznic;
    }
    
    private void obliczsaldoBOkonta(List<Konto> przygotowanalista) {
        for (Konto r : przygotowanalista) {
            if (r.getBoWn() > r.getBoMa()) {
                r.setBoWn(r.getBoWn() - r.getBoMa());
                r.setBoMa(0.0);
            } else if (r.getBoWn() < r.getBoMa()) {
                r.setBoMa(r.getBoMa() - r.getBoWn());
                r.setBoWn(0.0);
            } else {
                r.setBoWn(0.0);
                r.setBoMa(0.0);
            }
        }
    }

    private void resetujBOnaKonto(List<Konto> przygotowanalista) {
        for (Konto r : przygotowanalista) {
            if (r != null) {
                r.setBoWn(0.0);
                r.setBoMa(0.0);
            }
        }
    }

    private void zapiszBOnaKontach(List<WierszBO> wierszeBO, List<Konto> kontaNowyRok) {
        for (WierszBO p : wierszeBO) {
            Konto k = kontaNowyRok.get(kontaNowyRok.indexOf(p.getKonto()));
            if (p.getWaluta().getSymbolwaluty().equals("PLN")) {
                k.setBoWn(k.getBoWn() + p.getKwotaWn());
                k.setBoMa(k.getBoMa() + p.getKwotaMa());
            } else {
                k.setBoWn(k.getBoWn() + p.getKwotaWnPLN());
                k.setBoMa(k.getBoMa() + p.getKwotaMaPLN());
            }
            if (k.getBoWn() > 0.0 || k.getBoMa() > 0.0) {
                k.setBlokada(true);
            }
        }
    }

    private void obliczkontawynikowe(Konto kontowyniku, List<SaldoKonto> listaSaldoKonto, Waluty walpln) {
        double przychody = 0.0;
        double koszty = 0.0;
        for (SaldoKonto p : listaSaldoKonto) {
            if (p.getKonto().getBilansowewynikowe().equals("wynikowe")) {
                if (p.getKonto().isPrzychod0koszt1()) {
                    koszty += p.getSaldoWn() - p.getSaldoMa();
                } else {
                    przychody += p.getSaldoMa() - p.getSaldoWn();
                }
            }
        }
        double wynik = Z.z(Z.z(przychody) - Z.z(koszty));
        SaldoKonto p = pobierzSaldoKonto(listaSaldoKonto, kontowyniku);
        p.setWalutadlabo(walpln);
        if (wynik > 0) {
            p.setSaldoMa(Z.z(p.getSaldoMa() + wynik));
        } else {
            p.setSaldoWn(Z.z(p.getSaldoWn()) - wynik);
        }
    }

    private SaldoKonto pobierzSaldoKonto(List<SaldoKonto> listaSaldoKonto, Konto kontowyniku) {
        SaldoKonto saldoKonto = null;
        for (SaldoKonto p : listaSaldoKonto) {
            if (p.getKonto().equals(kontowyniku)) {
                saldoKonto = p;
            }
        }
        if (saldoKonto == null) {
            saldoKonto = new SaldoKonto(kontowyniku, 0, 0);
            listaSaldoKonto.add(saldoKonto);
        }
        return saldoKonto;
    }

    private List<Konto> zrobwierszeBO(List<WierszBO> wierszeBO, List<SaldoKonto> listaSaldoKonto, List<Konto> kontaNowyRok, List<Konto> kontazdziecmi) {
        Set<Konto> brakujacekontanowyrok = new HashSet<>();
        if (!listaSaldoKonto.isEmpty()) {
            for (SaldoKonto p : listaSaldoKonto) {
                if (p.getKonto().getBilansowewynikowe().equals("bilansowe")) {
                    Konto k = nowekonto(p.getKonto(), kontaNowyRok);
                    if (k != null) {
                        if (k.isMapotomkow()) {
                            kontazdziecmi.add(k);
                        }
                        wierszeBO.add(new WierszBO(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), k, p.getWalutadlabo(), wpisView.getUzer()));
                    } else {
                        brakujacekontanowyrok.add(p.getKonto());
                    }
                }
            }
        }
        List<Konto> zwrot = new ArrayList<>(brakujacekontanowyrok);
        Collections.sort(zwrot, new Kontocomparator());
        return zwrot;
    }
    
    private List<WierszBO> zrobwierszeBORoznicowy(List<WierszBO> wierszeBO, List<SaldoKonto> listaSaldoKonto, List<Konto> kontaNowyRok) {
        List<WierszBO> roznicowe = new ArrayList<>();
        if (!listaSaldoKonto.isEmpty()) {
            //agreguj kwoty
            List<Konto> rejestrkont = new ArrayList<>();
            for (Iterator<SaldoKonto> ita = listaSaldoKonto.iterator();ita.hasNext();) {
                SaldoKonto t = ita.next();
                if (!rejestrkont.contains(t.getKonto())) {
                    rejestrkont.add(t.getKonto());
                    for (SaldoKonto s :listaSaldoKonto){
                        if (t.getKonto().getPelnynumer().equals(s.getKonto().getPelnynumer()) && t.getId()!=s.getId()) {
                            t.setSaldoWn(t.getSaldoWn()+s.getSaldoWn());
                            t.setSaldoMa(t.getSaldoMa()+s.getSaldoMa());
                            t.setSaldoWnPLN(t.getSaldoWnPLN()+s.getSaldoWnPLN());
                            t.setSaldoMaPLN(t.getSaldoMaPLN()+s.getSaldoMaPLN());
                        }
                    }
                } else {
                    ita.remove();
                }
            }
            for (SaldoKonto p : listaSaldoKonto) {
                if (p.getKonto().getBilansowewynikowe().equals("bilansowe")) {
                    List<WierszBO> wierszecojuzsa = wierszeBO.stream().filter(r->r.getKonto().getPelnynumer().equals(p.getKonto().getPelnynumer())).collect(Collectors.toList());
                    double sumaStarawn = wierszecojuzsa.stream().map(x->x.getKwotaWn()).reduce(0.0, Double::sum);
                    double sumaStarama = wierszecojuzsa.stream().map(x->x.getKwotaMa()).reduce(0.0, Double::sum);
                    double sumaStarawnPLN = wierszecojuzsa.stream().map(x->x.getKwotaWnPLN()).reduce(0.0, Double::sum);
                    double sumaStaramaPLN = wierszecojuzsa.stream().map(x->x.getKwotaMaPLN()).reduce(0.0, Double::sum);
                    double roznicaSaldoWnPLN=Z.z(p.getSaldoWnPLN()-sumaStarawnPLN);
                    double roznicaSaldoMaPLN=Z.z(p.getSaldoMaPLN()-sumaStaramaPLN);
                    double wynikPLN = Z.z(roznicaSaldoWnPLN+roznicaSaldoMaPLN);
                    double roznicaSaldoWn=Z.z(p.getSaldoWn()-sumaStarawn);
                    double roznicaSaldoMa=Z.z(p.getSaldoMa()-sumaStarama);
                    double wynik = Z.z(roznicaSaldoWn+roznicaSaldoMa);
                    Konto k = nowekonto(p.getKonto(), kontaNowyRok);
                    boolean przetwarzaj = false;
                    boolean przetwarzaj2 = false;
                    if (p.getSaldoWnPLN()!=0.0||p.getSaldoMaPLN()!=0.0) {
                        if (roznicaSaldoWnPLN!=0.0 || roznicaSaldoMaPLN!=0.0) {
                            przetwarzaj = true;
                            przetwarzaj2 = false;
                        }
                    } else {
                        if (roznicaSaldoWn!=0.0 || roznicaSaldoMa!=0.0) {
                            przetwarzaj = false;
                            przetwarzaj2 = true;
                        }
                    }
                    if (k != null) {
                        double roznicaWn = Z.z(p.getSaldoWn()-sumaStarawn);
                        double roznicaMa = Z.z(p.getSaldoMa()-sumaStarama);
                        double roznicaWnPLN = Z.z(p.getSaldoWnPLN()-sumaStarawnPLN);
                        double roznicaMaPLN = Z.z(p.getSaldoMaPLN()-sumaStaramaPLN);
                        if (przetwarzaj) {
                            WierszBO wierszBO = new WierszBO(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), k, p.getWalutadlabo(), wpisView.getUzer(), roznicaWnPLN, roznicaMaPLN, roznicaWnPLN, roznicaMaPLN);
                            wierszBO.setRoznicowy(true);
                            roznicowe.add(wierszBO);
                        } else if (przetwarzaj2) {
                            WierszBO wierszBO = new WierszBO(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), k, p.getWalutadlabo(), wpisView.getUzer(), roznicaWn, roznicaMa, roznicaWn, roznicaMa);
                            wierszBO.setRoznicowy(true);
                            roznicowe.add(wierszBO);
                        }
                    }
                }
            }
            List<WierszBO> wierszekorekty = new ArrayList<>();
            for (WierszBO t : wierszeBO) {
                if (!rejestrkont.contains(t.getKonto())) {
                    WierszBO wierszBO = new WierszBO(t);
                    wierszBO.setNowy0edycja1usun2(0);
                    wierszBO.setRoznicowy(true);
                    wierszekorekty.add(wierszBO);
                }
            }
            wierszBODAO.createList(wierszekorekty);
            roznicowe.addAll(wierszekorekty);
        }
        return roznicowe;
    }

    private Konto nowekonto(Konto konto, List<Konto> kontaNowyRok) {
        Konto zwrot = null;
        for (Konto p : kontaNowyRok) {
            if (p.getPelnynumer().equals(konto.getPelnynumer())) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    private List<SaldoKonto> przetwarzajSaldoKonto(List<SaldoKonto> listaSaldoKonto) {
        List<SaldoKonto> nowalista = Collections.synchronizedList(new ArrayList<>());
        for (SaldoKonto p : listaSaldoKonto) {
             if (p.getKonto().getPelnynumer().startsWith("149")) {
                nowalista.addAll(przetworzPojedyncze(p));
            } else if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe") && p.getKonto().getPelnynumer().startsWith("20") && przeniestylkosalda==false) {
                nowalista.addAll(przetworzPojedyncze(p));
            } else if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe") && p.getKonto().getPelnynumer().startsWith("20") && przeniestylkosalda==true) {
                Set<Waluty> waluty = pobierzWalutySaldoKonto(p, false);
                waluty.add(listawalut.get("PLN"));
                List<SaldoKonto> wierszeSumWalutaPLN = Collections.synchronizedList(new ArrayList<>());
                for (Waluty wal : waluty) {
                    SaldoKonto sal = sumujkwotyWalutaPln(p, wal); 
                    if (sal != null) {
                        wierszeSumWalutaPLN.add(sal);
                    }
                }
                nowalista.addAll(wierszeSumWalutaPLN);
                if (wierszeSumWalutaPLN.size() > 1 || (wierszeSumWalutaPLN.size() == 1 && !wierszeSumWalutaPLN.get(0).getWalutadlabo().getSymbolwaluty().equals("PLN"))) {
                    SaldoKonto roznicekursowe = rozliczroznicekursowedwojki(p,wierszeSumWalutaPLN);
                    if (roznicekursowe != null) {
                        roznicekursowe.setRoznicakursowastatystyczna(true);
                        nowalista.add(roznicekursowe);
                    }
                }
            } else if (p.getKonto().getPelnynumer().startsWith("1")) {
                List<Waluty> waluty = Collections.synchronizedList(new ArrayList<>());
                waluty.addAll(pobierzWalutySaldoKonto(p, true));
                if (waluty.isEmpty()) {
                    waluty.add(listawalut.get("PLN"));
                }
                for (Waluty r : waluty) {
                    nowalista.addAll(przetworzWBRK(p,r));
                }
            } else {
                nowalista.add(p);
            }
        }
        return nowalista;
    }
    
    private List<SaldoKonto> przetwarzajSaldoKontoBoRoznicowe(List<SaldoKonto> listaSaldoKonto) {
        List<SaldoKonto> nowalista = Collections.synchronizedList(new ArrayList<>());
        for (SaldoKonto p : listaSaldoKonto) {
             if (p.getKonto().getPelnynumer().startsWith("149")) {
                nowalista.addAll(przetworzPojedyncze(p));
            } else if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe") && p.getKonto().getPelnynumer().startsWith("20") && przeniestylkosalda==true) {
                Set<Waluty> waluty = pobierzWalutySaldoKonto(p, false);
                waluty.add(listawalut.get("PLN"));
                List<SaldoKonto> wierszeSumWalutaPLN = Collections.synchronizedList(new ArrayList<>());
                for (Waluty wal : waluty) {
                    SaldoKonto sal = sumujkwotyWalutaPln(p, wal); 
                    if (sal != null) {
                        wierszeSumWalutaPLN.add(sal);
                    }
                }
                nowalista.addAll(wierszeSumWalutaPLN);
                if (wierszeSumWalutaPLN.size() > 1 || (wierszeSumWalutaPLN.size() == 1 && !wierszeSumWalutaPLN.get(0).getWalutadlabo().getSymbolwaluty().equals("PLN"))) {
                    SaldoKonto roznicekursowe = rozliczroznicekursowedwojki(p,wierszeSumWalutaPLN);
                    if (roznicekursowe != null) {
                        roznicekursowe.setRoznicakursowastatystyczna(true);
                        nowalista.add(roznicekursowe);
                    }
                }
            } else if (p.getKonto().getPelnynumer().startsWith("1")) {
                List<Waluty> waluty = Collections.synchronizedList(new ArrayList<>());
                waluty.addAll(pobierzWalutySaldoKonto(p, true));
                if (waluty.isEmpty()) {
                    waluty.add(listawalut.get("PLN"));
                }
                for (Waluty r : waluty) {
                    nowalista.addAll(przetworzWBRK(p,r));
                }
            } else {
                nowalista.add(p);
            }
        }
        int i = 1;
        for (SaldoKonto t : nowalista) {
            t.setId(i++);
        }
        return nowalista;
    }
    
    private SaldoKonto rozliczroznicekursowedwojki(SaldoKonto p, List<SaldoKonto> nowewiersze) {
        if (p.getKonto().getPelnynumer().equals("203-2-5")) {
        }
        double wnpln = p.getSaldoWn();
        double mapln = p.getSaldoMa();
        double sumawnpln = 0.0;
        double sumamapln = 0.0;
        for (SaldoKonto r : nowewiersze) {
            sumawnpln += r.getSaldoWnPLN();
            sumamapln += r.getSaldoMaPLN();
        }
        double roznicakursowe = 0.0;
        boolean wn = false;
        if (wnpln == 0.0 && mapln == 0.0) {
            if (sumawnpln > 0.0) {
                roznicakursowe = sumawnpln;
                wn = true;
            } else if (sumamapln > 0.0) {
                roznicakursowe = sumamapln;
                wn = false;
            }
        } else if (wnpln > 0.0) {
            if (sumawnpln > 0.0) {
                roznicakursowe = wnpln - sumawnpln;
                wn = true;
            } else if (sumamapln > 0.0) {
                if (wnpln > sumamapln) {
                    roznicakursowe = wnpln - sumamapln;
                    wn = false;
                } else if (wnpln < sumamapln){
                    roznicakursowe = sumamapln - wnpln;
                    wn = true;
                }   
            }
        } else if (mapln > 0.0) {
            if (sumamapln > 0.0) {
                roznicakursowe = mapln - sumamapln;
                wn = false;
            } else {
                if (mapln > sumawnpln) {
                    roznicakursowe = mapln - sumawnpln;
                    wn = true;
                } else if (mapln < sumawnpln){
                    roznicakursowe = sumawnpln - mapln;
                    wn = false;
                }  
            }
        }
        SaldoKonto zwrot = null;
        if (Z.z(roznicakursowe) > 0.0) {
            zwrot = new SaldoKonto(p, listawalut.get("PLN"), Z.z(roznicakursowe), Z.z(roznicakursowe), wn);
        }
        return zwrot;
    }
    
    private SaldoKonto sumujkwotyWalutaPln(SaldoKonto p, Waluty w){
        double sumawaluta = 0.0;
        double sumapln = 0.0;
        for (StronaWiersza r : p.getZapisy()) {
            if (r.getSymbolWalutBOiSW().equals(w.getSymbolwaluty())) {
                 if (r.isWn()) {
                    sumawaluta += r.getKwota();
                    sumapln += r.getKwotaPLN();
                 } else {
                    sumawaluta -= r.getKwota();
                    sumapln -= r.getKwotaPLN();
                 }
            }
        }
        SaldoKonto zwrot = null;
        if (Z.z(sumawaluta) != 0.0 || Z.z(sumapln) != 0.0) {
            zwrot = new SaldoKonto(p, w, Z.z(sumawaluta), Z.z(sumapln));
        }
        return zwrot;
    }
    
    private Set<Waluty> pobierzWalutySaldoKonto(SaldoKonto p, boolean jedna) {
        Set<Waluty> waluty = new HashSet<>();
        for (StronaWiersza t : p.getZapisy()) {
            if (!t.getSymbolWalutBOiSW().equals("PLN")) {
                waluty.add(listawalut.get(t.getSymbolWalutBOiSW()));
                if (jedna) {
                    break;
                }
            }
        }
        return waluty;
    }

//    private Collection<? extends SaldoKonto> przetworzWBRK(SaldoKonto p, Waluty waluta) {
//        List<SaldoKonto> nowalista_wierszy = Collections.synchronizedList(new ArrayList<>());
//        Waluty walutapln = listawalut.get("PLN");
//        //dodatnie oznacza saldo Wn ujemne saldo Ma
//        List<StronaWiersza> zapisyfilter = p.getZapisy();
//        double saldopln = Z.z(obliczsaldoPLNWBK(zapisyfilter));
//        //dodatnie oznacza saldo Wn ujemne saldo Ma
//        double saldowal = Z.z(obliczsaldo(zapisyfilter, waluta));
//        double roznica = Z.z(Z.z(saldowal) - Z.z(saldopln));
//        List<Double> kwotywpln = Collections.synchronizedList(new ArrayList<>());
//        //tzn ze na koncie sa tylko zlotowki
//        if (roznica == 0.0) {
//            nowalista_wierszy.add(p);
//        } else {
//            nowalista_wierszy.addAll(tworzwierszewaluty(waluta, zapisyfilter, saldowal, kwotywpln));
//            double sumasaldozapisywaluta = obliczsaldoplnsaldokont(kwotywpln);
//            double roznicekursowe = 0.0;
//            double rozniceatr = przejrzyjATR(p.getZapisy());
//            if (saldopln>0 && rozniceatr > 0 || saldopln<0 && rozniceatr < 0) {
//                saldopln = Z.z(saldopln + rozniceatr);
//            } else if (saldopln>0 && rozniceatr < 0) {
//                saldopln = Z.z(saldopln + rozniceatr);
//            } else if (saldopln<0 && rozniceatr > 0) {
//                saldopln = Z.z(saldopln + rozniceatr);
//            }
//            if (saldopln > 0 && sumasaldozapisywaluta > 0)  {
//                if (saldopln>sumasaldozapisywaluta) {
//                    roznicekursowe = Z.z(saldopln-sumasaldozapisywaluta);
//                    if (roznicekursowe != 0.0) {
//                        nowalista_wierszy.add(new SaldoKonto(p, roznicekursowe, walutapln, "Wn"));
//                    }
//                } else {
//                    roznicekursowe = Z.z(sumasaldozapisywaluta-saldopln);
//                    if (roznicekursowe != 0.0) {
//                        nowalista_wierszy.add(new SaldoKonto(p, roznicekursowe, walutapln, "Ma"));
//                    }
//                }
//                
//            } else if (saldopln < 0 && sumasaldozapisywaluta < 0) {
//                if (Math.abs(saldopln)>Math.abs(sumasaldozapisywaluta)) {
//                    roznicekursowe = -Z.z(saldopln-sumasaldozapisywaluta);
//                    if (roznicekursowe != 0.0) {
//                        nowalista_wierszy.add(new SaldoKonto(p, roznicekursowe, walutapln, "Ma"));
//                    }
//                } else {
//                    roznicekursowe = -Z.z(sumasaldozapisywaluta-saldopln);
//                    if (roznicekursowe != 0.0) {
//                        nowalista_wierszy.add(new SaldoKonto(p, roznicekursowe, walutapln, "Wn"));
//                    }
//                }
//                
//            } else if (saldopln > 0 && sumasaldozapisywaluta < 0)  {
//                if (Math.abs(saldopln)>Math.abs(sumasaldozapisywaluta)) {
//                    roznicekursowe = Z.z(saldopln-sumasaldozapisywaluta);
//                } else {
//                    roznicekursowe = Z.z(sumasaldozapisywaluta-saldopln);
//                }
//                if (roznicekursowe>0) {
//                    nowalista_wierszy.add(new SaldoKonto(p, roznicekursowe, walutapln, "Wn"));    
//                } else {
//                    nowalista_wierszy.add(new SaldoKonto(p, roznicekursowe, walutapln, "Ma"));    
//                }
//            } else if (saldopln < 0 && sumasaldozapisywaluta > 0) {
//                if (Math.abs(saldopln)>Math.abs(sumasaldozapisywaluta)) {
//                    roznicekursowe = Z.z(saldopln-sumasaldozapisywaluta);
//                } else {
//                    roznicekursowe = Z.z(sumasaldozapisywaluta-saldopln);
//                }
//                if (roznicekursowe<0) {
//                    nowalista_wierszy.add(new SaldoKonto(p, -roznicekursowe, walutapln, "Ma"));    
//                } else {
//                    nowalista_wierszy.add(new SaldoKonto(p, -roznicekursowe, walutapln, "Wn"));    
//                }
//            }
//        }
//        return nowalista_wierszy;
//    }
    
    private Collection<? extends SaldoKonto> przetworzWBRK(SaldoKonto p, Waluty waluta) {
        List<SaldoKonto> nowalista_wierszy = Collections.synchronizedList(new ArrayList<>());
        Waluty walutapln = listawalut.get("PLN");
        //dodatnie oznacza saldo Wn ujemne saldo Ma
        List<StronaWiersza> zapisyfilter = p.getZapisy();
        double[] salda = BilansBean.rozliczzapisy(zapisyfilter);
        double saldopln = salda[0];
        //dodatnie oznacza saldo Wn ujemne saldo Ma
        double saldowal = salda[1];
        if (waluta.getSymbolwaluty().equals("PLN")) {
            saldowal = salda[0];
        }
        double roznica = Z.z(Z.z(saldowal) - Z.z(saldopln));
        List<Double> kwotywpln = Collections.synchronizedList(new ArrayList<>());
        //tzn ze na koncie sa tylko zlotowki
        if (roznica == 0.0) {
            nowalista_wierszy.add(p);
        } else {
            nowalista_wierszy.add(new SaldoKonto(p, waluta, saldowal, saldopln));
//            double rozniceatr = przejrzyjATR(p.getZapisy());
//            if (rozniceatr<0.0) {
//                nowalista_wierszy.add(new SaldoKonto(p, rozniceatr, walutapln, "Ma"));
//            } else if (rozniceatr>0.0){
//                nowalista_wierszy.add(new SaldoKonto(p, rozniceatr, walutapln, "Wn"));
//            }
        }
        return nowalista_wierszy;
    }

    private List<StronaWiersza> przejrzyj(List<StronaWiersza> zapisy) {
        List<StronaWiersza> zwrot = new ArrayList<>();
        for (StronaWiersza p : zapisy) {
            if (p.getDokfk().getRodzajedok()!=null && !p.getDokfk().getRodzajedok().getSkrot().equals("ATR")) {
                zwrot.add(p);
            }
        }
        return zwrot;
    }
    
    private double przejrzyjATR(List<StronaWiersza> zapisy) {
        double suma = 0.0;
        for (StronaWiersza p : zapisy) {
            if (p.getDokfk().getRodzajedok()!=null && p.getDokfk().getRodzajedok().getSkrot().equals("ATR")) {
                if (p.isWn()) {
                    suma += p.getKwotaPLN();
                } else {
                    suma -= p.getKwotaPLN();
                }
            }
        }
        return suma;
    }
    
    private double obliczsaldoplnsaldokont(List<Double> kwotywpln) {
        double zwrot = 0.0;
        for (Double p : kwotywpln) {
            zwrot += p;
        }
        return Z.z(zwrot);
    }

    private double obliczsaldo(List<StronaWiersza> zapisy, Waluty waluta) {
        double saldo = 0.0;
        for (StronaWiersza p : zapisy) {
            if (waluta.getSymbolwaluty().equals(p.getSymbolWalutBOiSW()) && !p.getWiersz().getDokfk().getSeriadokfk().equals("RRK")) {
                if (p.isWn()) {
                    saldo += p.getKwota();
                } else {
                    saldo -= p.getKwota();
                }
            }
        }
        return Z.z(saldo);
    }

    private double obliczsaldoPLN(List<StronaWiersza> zapisy, Waluty waluta) {
        double saldo = 0.0;
        for (StronaWiersza p : zapisy) {
            if (waluta.getSymbolwaluty().equals(p.getSymbolWalutBOiSW()) && !p.getWiersz().getDokfk().getSeriadokfk().equals("RRK")) {
                if (p.isWn()) {
                    saldo += p.getKwotaPLN();
                } else {
                    saldo -= p.getKwotaPLN();
                }
            }
        }
        return Z.z(saldo);
    }

    private double obliczsaldoPLNWBK(List<StronaWiersza> zapisy) {
        double saldo = 0.0;
        for (StronaWiersza p : zapisy) {
            if (!p.getDokfk().getRodzajedok().getSkrot().equals("ATR")) {
                if (p.isWn()) {
                    saldo += p.getKwotaPLN();
                } else {
                    saldo -= p.getKwotaPLN();
                }
            }
        }
        return Z.z(saldo);
    }
    
//    private Collection<? extends SaldoKonto> tworzwierszewaluty(Waluty wal, List<StronaWiersza> zapisy, double saldowal, List<Double> kwotywpln) {
//        int zapisydl = zapisy.size() - 1;
//        double limit = saldowal;
//        Collections.sort(zapisy, new StronaWierszacomparatorBO());
//        List<SaldoKonto> nowalista_wierszy = Collections.synchronizedList(new ArrayList<>());
//        if (limit > 0.0) {
//            for (int i = zapisydl; i >= 0; i--) {
//                StronaWiersza p = zapisy.get(i);
//                double kurs = p.getKursWalutyBOSW();
//                if (p.getSymbolWalutBOiSW().equals(wal.getSymbolwaluty()) && p.isWn() && kurs != 1.0) {
//                    limit -= p.getKwota();
//                    if (limit >= 0.0) {
//                        nowalista_wierszy.add(new SaldoKonto(p, wal, p.getKwota(), p.getKwotaPLN()));
//                        kwotywpln.add(p.getKwotaPLN());
//                    } else {
//                        double kwotapomniejszona = Z.z(p.getKwota() + limit);
//                        double kwotapomniejszonapln = Z.z(kwotapomniejszona * kurs);
//                        if (kwotapomniejszona != 0.0) {
//                            nowalista_wierszy.add(new SaldoKonto(p, wal, kwotapomniejszona, kwotapomniejszonapln));
//                            kwotywpln.add(kwotapomniejszonapln);
//                        }
//                        break;
//                    }
//                }
//            }
//        } else {
//            for (int i = zapisydl; i >= 0; i--) {
//                StronaWiersza p = zapisy.get(i);
//                double kurs = p.getKursWalutyBOSW();
//                if (p.getSymbolWalutBOiSW().equals(wal.getSymbolwaluty()) && !p.isWn() && kurs != 1.0) {
//                    limit += p.getKwota();
//                    if (limit <= 0.0) {
//                        nowalista_wierszy.add(new SaldoKonto(p, wal, p.getKwota(), p.getKwotaPLN()));
//                        kwotywpln.add(-p.getKwotaPLN());
//                    } else {
//                        double kwotapomniejszona = Z.z(limit - p.getKwota());
//                        double kwotapomniejszonapln = Z.z(kwotapomniejszona * kurs);
//                        if (kwotapomniejszona != 0.0) {
//                            nowalista_wierszy.add(new SaldoKonto(p, wal, kwotapomniejszona, kwotapomniejszonapln));
//                            kwotywpln.add(kwotapomniejszonapln);
//                        }
//                        break;
//                    }
//                }
//            }
//        }
//        return nowalista_wierszy;
//    }

    private Collection<? extends SaldoKonto> przetworzPojedyncze(SaldoKonto p) {
        if (p.getKonto().getPelnynumer().equals("203-2-247")) {
            System.out.println("");
        }
        List<SaldoKonto> nowalista_wierszy = Collections.synchronizedList(new ArrayList<>());
        Set<Waluty> waluty = new HashSet<>();
        double saldodocelowe = 0.0;
        for (StronaWiersza t : p.getZapisy()) {
            waluty.add(listawalut.get(t.getSymbolWalutBOiSW()));
            if (t.isWn()) {
                saldodocelowe = Z.z(saldodocelowe+t.getKwotaPLN());
            } else {
                saldodocelowe = Z.z(saldodocelowe-t.getKwotaPLN());
            }
        }
        if (waluty.size()>1) {
            saldodocelowe = Z.z(saldodocelowe);
            for (Waluty wal : waluty) {
                nowalista_wierszy.addAll(sumujdlawaluty(wal, p.getZapisy()));
            }

            double saldoaktualne = 0.0;
            for (SaldoKonto s: nowalista_wierszy) {
                if (s.getSaldoWnPLN()!=0.0) {
                    saldoaktualne = Z.z(saldoaktualne+s.getSaldoWnPLN());
                } else {
                    saldoaktualne = Z.z(saldoaktualne-s.getSaldoMaPLN());
                }
            }
            saldoaktualne = Z.z(saldoaktualne);
            double roznica = Z.z(saldodocelowe-saldoaktualne);
            if (roznica!=0.0) {
                nowalista_wierszy.add(new SaldoKonto(p.getKonto(), listawalut.get("PLN"), roznica, roznica));
            }
        } else {
            for (Waluty wal : waluty) {
                nowalista_wierszy.addAll(sumujdlawaluty(wal, p.getZapisy()));
            }
        }
        return nowalista_wierszy;
    }

        private Collection<? extends SaldoKonto> sumujdlawaluty(Waluty wal, List<StronaWiersza> zapisy) {
        //na poczatku usuniemy te co pokazuja ze sa rozliczone
        double saldopln = Z.z(obliczsaldoPLN(zapisy, wal));
        //dodatnie oznacza saldo Wn ujemne saldo Ma
        double saldowal = Z.z(obliczsaldo(zapisy, wal));
        List<StronaWiersza> zerowe = Collections.synchronizedList(new ArrayList<>());
        List<StronaWiersza> zapisydopor = Collections.synchronizedList(new ArrayList<>());
        for (StronaWiersza s : zapisy) {
            if (s.getSymbolWalutBOiSW().equals(wal.getSymbolwaluty())&&Z.z(s.getPozostalo())!=0.0 && !s.getWiersz().getDokfk().getSeriadokfk().equals("RRK")) {
                zapisydopor.add(s);
            }
        }
        List<SaldoKonto> zapisykonta = Collections.synchronizedList(new ArrayList<>());
//        for (ListIterator<StronaWiersza> it = zapisy.listIterator(); it.hasNext();) {
//            StronaWiersza w = it.next();
//            double kwota = w.getKwota();
//            boolean usun = false;
//            //plus to wn minus to ma
//            double roznicakursowaWn = 0.0;
//            double roznicakursowaMa = 0.0;
//            //ten loop 'rozlicza' pozycje o takich samych kwotach a przeciwnych stronach
//            for (ListIterator<StronaWiersza> it1 = zapisydopor.listIterator(); it1.hasNext();) {
//                StronaWiersza w1 = it1.next();
//                double kwota1 = w1.getKwota();
//                if (kwota != 0.0 && kwota1 != 0.0 && Z.z(kwota) == Z.z(kwota1) && w.isWn() != w1.isWn()) {
//                    it1.remove();
//                    usun = true;
//                    if (w.isWn()) {
//                        roznicakursowaWn = Z.z(w.getKwotaPLN()-w1.getKwotaPLN());
//                    } else {
//                        roznicakursowaMa = Z.z(w1.getKwotaPLN()-w.getKwotaPLN());
//                    }
//                    break;
//                }
//            }
//            if (usun) {
//                it.remove();
//            }
//            if (roznicakursowaWn!=0.0) {
//                zapisykonta.add(new SaldoKonto(w.getKonto(), roznicakursowaWn, 0.0, listawalut.get("PLN")));
//            } else if (roznicakursowaMa!=0.0) {
//                zapisykonta.add(new SaldoKonto(w.getKonto(), 0.0, roznicakursowaMa, listawalut.get("PLN")));
//            }
//        }
        Collections.sort(zapisydopor, new StronaWierszacomparatorDesc());
        //to musi byc bo na koncie moga byz zapisy w walucie i nie
        if (saldowal!=0) {
            for (Iterator<StronaWiersza> it = new ReverseIterator<>(zapisydopor).iterator(); it.hasNext();) {
                StronaWiersza t = it.next();
                if (saldowal>0.0) {
                    if (t.getSymbolWalutBOiSW().equals(wal.getSymbolwaluty()) && t.isWn() && t.getKwota()>0.0) {
                        double pozostalo = t.getPozostalo();
                        double pozostalopln = t.getPozostaloPLN();
                        if (Math.abs(saldowal)<pozostalo) {
                            pozostalopln = Z.z((Math.abs(saldowal)/pozostalo)*pozostalopln);
                            pozostalo = Math.abs(saldowal);
                            saldowal = saldowal-pozostalo;
                            zapisykonta.add(new SaldoKonto(t, wal, pozostalo, pozostalopln));
                        } else if (pozostalo > 0.0 ) { 
                            zapisykonta.add(new SaldoKonto(t, wal));
                            saldowal = saldowal-pozostalo;
                        }
                        if (saldowal <=0.0) {
                            break;
                        }
                    }
                } else {
                    if (t.getSymbolWalutBOiSW().equals(wal.getSymbolwaluty()) && t.isWn()==false && t.getKwota()>0.0) {
                        double pozostalo = t.getPozostalo();
                        double pozostalopln = t.getPozostaloPLN();
                        if (Math.abs(saldowal)<pozostalo) {
                            pozostalopln = Z.z((Math.abs(saldowal)/pozostalo)*pozostalopln);
                            pozostalo = Math.abs(saldowal);
                            saldowal = saldowal+pozostalo;
                            zapisykonta.add(new SaldoKonto(t, wal, pozostalo, pozostalopln));
                        } else if (pozostalo > 0.0 ) { 
                            zapisykonta.add(new SaldoKonto(t, wal));
                            saldowal = saldowal+pozostalo;
                        }
                        if (saldowal >=0.0) {
                            break;
                        }
                    }
                }
                    
            }
        }
//        } else {
//            for (Iterator<StronaWiersza> it = new ReverseIterator<>(zapisy).iterator(); it.hasNext();) {
//                StronaWiersza t = it.next();
//                if (t.getKonto().getPelnynumer().equals("201-2-2")){
//                    error.E.s("");
//                }
//                if (saldopln>0.0) {
//                    if (t.getSymbolWalutBOiSW().equals("PLN") && t.isWn() && t.getKwota()>0.0) {
//                        double pozostalo = t.getPozostalo();
//                        double pozostalopln = t.getPozostaloPLN();
//                        if (Math.abs(saldopln)<pozostalo) {
//                            pozostalopln = Z.z((Math.abs(saldopln)/pozostalo)*pozostalopln);
//                            pozostalo = Math.abs(saldopln);
//                            saldopln = saldopln-pozostalo;
//                            zapisykonta.add(new SaldoKonto(t, wal, pozostalo, pozostalopln));
//                        } else if (pozostalo > 0.0 ) { 
//                            zapisykonta.add(new SaldoKonto(t, wal));
//                            saldopln = saldopln-pozostalo;
//                        }
//                        if (saldopln <=0.0) {
//                            break;
//                        }
//                    }
//                } else {
//                    if (t.getSymbolWalutBOiSW().equals("PLN") && t.isWn()==false && t.getKwota()>0.0) {
//                        double pozostalo = t.getPozostalo();
//                        double pozostalopln = t.getPozostaloPLN();
//                        if (Math.abs(saldopln)<pozostalo) {
//                            pozostalopln = Z.z((Math.abs(saldopln)/pozostalo)*pozostalopln);
//                            pozostalo = Math.abs(saldopln);
//                            saldopln = saldopln+pozostalo;
//                            zapisykonta.add(new SaldoKonto(t, wal, pozostalo, pozostalopln));
//                        } else if (pozostalo > 0.0 ) { 
//                            zapisykonta.add(new SaldoKonto(t, wal));
//                            saldopln = saldopln+pozostalo;
//                        }
//                        if (saldopln >=0.0) {
//                            break;
//                        }
//                    }
//                }
//                    
//            }
//        }
      
        return zapisykonta;
    }

    

    //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public boolean isTylkoroznicowy() {
        return tylkoroznicowy;
    }

    public void setTylkoroznicowy(boolean tylkoroznicowy) {
        this.tylkoroznicowy = tylkoroznicowy;
    }

    public boolean isTojestbilanslikwidacyjny() {
        return tojestbilanslikwidacyjny;
    }

    public void setTojestbilanslikwidacyjny(boolean tojestbilanslikwidacyjny) {
        this.tojestbilanslikwidacyjny = tojestbilanslikwidacyjny;
    }

    public boolean isPrzeniestylkosalda() {
        return przeniestylkosalda;
    }

    public void setPrzeniestylkosalda(boolean przeniestylkosalda) {
        this.przeniestylkosalda = przeniestylkosalda;
    }

    public BilansWprowadzanieView getBilansWprowadzanieView() {
        return bilansWprowadzanieView;
    }

    public void setBilansWprowadzanieView(BilansWprowadzanieView bilansWprowadzanieView) {
        this.bilansWprowadzanieView = bilansWprowadzanieView;
    }

    public boolean isSabledy() {
        return sabledy;
    }

    public void setSabledy(boolean sabledy) {
        this.sabledy = sabledy;
    }

    public boolean isSabledy2() {
        return sabledy2;
    }

    public void setSabledy2(boolean sabledy2) {
        this.sabledy2 = sabledy2;
    }

    public List<String> getKomunikatyok() {
        return komunikatyok;
    }

    public void setKomunikatyok(List<String> komunikatyok) {
        this.komunikatyok = komunikatyok;
    }

    public List<String> getKomunikatyerror() {
        return komunikatyerror;
    }

    public void setKomunikatyerror(List<String> komunikatyerror) {
        this.komunikatyerror = komunikatyerror;
    }

    public List<String> getKomunikatyerror2() {
        return komunikatyerror2;
    }

    public void setKomunikatyerror2(List<String> komunikatyerror2) {
        this.komunikatyerror2 = komunikatyerror2;
    }

    public List<String> getKomunikatyerror3() {
        return komunikatyerror3;
    }

    public void setKomunikatyerror3(List<String> komunikatyerror3) {
        this.komunikatyerror3 = komunikatyerror3;
    }

    public SaldoAnalitykaView getSaldoAnalitykaView() {
        return saldoAnalitykaView;
    }

    public void setSaldoAnalitykaView(SaldoAnalitykaView saldoAnalitykaView) {
        this.saldoAnalitykaView = saldoAnalitykaView;
    }

//</editor-fold>

    

}

