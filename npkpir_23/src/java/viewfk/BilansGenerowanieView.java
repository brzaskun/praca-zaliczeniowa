/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKBean;
import daoFK.KontoDAOfk;
import daoFK.UkladBRDAO;
import daoFK.WalutyDAOfk;
import daoFK.WierszBODAO;
import embeddablefk.RoznicaSaldBO;
import embeddablefk.SaldoKonto;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import entityfk.Waluty;
import entityfk.WierszBO;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
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
@ManagedBean
@ViewScoped
public class BilansGenerowanieView implements Serializable {

    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{saldoAnalitykaView}")
    private SaldoAnalitykaView saldoAnalitykaView;
    @ManagedProperty(value = "#{bilansWprowadzanieView}")
    private BilansWprowadzanieView bilansWprowadzanieView;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    private Map<String, Waluty> listawalut;
    private List<String> komunikatyok;
    private List<String> komunikatyerror;
    private List<String> komunikatyerror2;
    private List<String> komunikatyerror3;
    private boolean sabledy;
    private boolean sabledy2;
    private boolean przeniestylkosalda;

    public BilansGenerowanieView() {
        E.m(this);
        this.komunikatyok = new ArrayList<>();
        this.komunikatyok.add("Nie rozpoczęto analizy");
        this.komunikatyerror = new ArrayList<>();
        this.komunikatyerror2 = new ArrayList<>();
        this.komunikatyerror3 = new ArrayList<>();
        this.listawalut = new HashMap<>();
    }

    @PostConstruct
    private void init() {
        List<Waluty> waluty = walutyDAOfk.findAll();
        for (Waluty p : waluty) {
            listawalut.put(p.getSymbolwaluty(), p);
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
        System.out.println("koniec");
    }

    private static List<testobjects.Konto> pobierzkonta(String podatnik) {
        int i = 1;
        List<testobjects.Konto> konta = new ArrayList<>();
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
        List<testobjects.WierszBO_T> wierszeBO = new ArrayList<>();
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
        DokfkPK dp = new DokfkPK(rodzaj, 12, podatnik, "2015");
        Dokfk d = new Dokfk(dp);
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
        this.komunikatyok = new ArrayList<>();
        this.komunikatyerror = new ArrayList<>();
        this.komunikatyerror2 = new ArrayList<>();
        this.komunikatyerror3 = new ArrayList<>();
        this.sabledy = false;
        this.sabledy2 = false;
        boolean stop = false;
        List<Konto> konta = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        if (konta.isEmpty()) {
            stop = true;
            String error = "Brak kont w roku " + wpisView.getRokWpisuSt() + " nie można generować BO";
            Msg.msg("e", error);
            komunikatyerror.add(error);
        } else {
            komunikatyok.add("Sprawdzono obecnosc planu kont. Liczba kont: " + konta.size());
        }
        List<UkladBR> uklad = ukladBRDAO.findukladBRPodatnikRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
            wierszBODAO.deletePodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Waluty walpln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
            saldoAnalitykaView.initGenerowanieBO();
            List<SaldoKonto> listaSaldoKontoRokPop = saldoAnalitykaView.getListaSaldoKonto();
            List<Konto> kontaNowyRok = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            resetujBOnaKonto(kontaNowyRok);
            Konto kontowyniku = PlanKontFKBean.findKonto860(kontaNowyRok);
            obliczkontawynikowe(kontowyniku, listaSaldoKontoRokPop, walpln);
            //tutaj trzeba przerobic odpowiednio listaSaldo
            List<SaldoKonto> listaSaldoKontoPrzetworzone = przetwarzajSaldoKonto(listaSaldoKontoRokPop);
            List<WierszBO> wierszeBO = new ArrayList<>();
            List<Konto> kontazdziecmi = new ArrayList<>();
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
            List<RoznicaSaldBO> kontainnesaldo = znajdzroznicesald(listaSaldoKontoRokPop, kontaNowyRok);
            if (!kontainnesaldo.isEmpty()) {
                komunikatyerror3.add("Po generacji jest różnica sald z BO. Sprawdź w poprzednim roku wiersze BO z dokumentem BO: ");
                for (RoznicaSaldBO p : kontainnesaldo) {
                    komunikatyerror3.add(p.getKonto().getPelnynumer() + " " + p.getKwotaroznicy());
                }
            }
            bilansWprowadzanieView.init();
            bilansWprowadzanieView.zapiszBilansBOdoBazy();
            Msg.msg("Generuje bilans");
        }
    }

    private List<RoznicaSaldBO> znajdzroznicesald(List<SaldoKonto> listaSaldoKontoRokPop, List<Konto> kontaNowyRok) {
        List<RoznicaSaldBO> listaroznic = new ArrayList<>();
        for (SaldoKonto p : listaSaldoKontoRokPop) {
            if (p.getKonto().getBilansowewynikowe().equals("bilansowe")) {
                Konto nowe = kontaNowyRok.get(kontaNowyRok.indexOf(p.getKonto()));
                if (Z.zAbs(p.getSaldoWn()) != Z.zAbs(nowe.getBoWn())) {
                   listaroznic.add(new RoznicaSaldBO(nowe, Z.zAbs(nowe.getBoWn()-p.getSaldoWn())));
                } else if (Z.zAbs(p.getSaldoMa()) != Z.zAbs(nowe.getBoMa())) {
                   listaroznic.add(new RoznicaSaldBO(nowe, Z.zAbs(nowe.getBoMa()-p.getSaldoMa())));
                }
            }
        }
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
        List<Konto> brakujacekontanowyrok = new ArrayList<>();
        if (!listaSaldoKonto.isEmpty()) {
            for (SaldoKonto p : listaSaldoKonto) {
                if (p.getKonto().getBilansowewynikowe().equals("bilansowe")) {
                    Konto k = nowekonto(p.getKonto(), kontaNowyRok);
                    if (k != null) {
                        if (k.isMapotomkow()) {
                            kontazdziecmi.add(k);
                        }
                        wierszeBO.add(new WierszBO(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt(), k, p.getWalutadlabo()));
                    } else {
                        brakujacekontanowyrok.add(p.getKonto());
                    }
                }
            }
        }
        return brakujacekontanowyrok;
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
        List<SaldoKonto> nowalista = new ArrayList<>();
        for (SaldoKonto p : listaSaldoKonto) {
            if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe") && p.getKonto().getPelnynumer().startsWith("20") && przeniestylkosalda==false) {
                nowalista.addAll(przetworzPojedyncze(p));
            } else if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe") && p.getKonto().getPelnynumer().startsWith("20") && przeniestylkosalda==true) {
                if (p.getKonto().getPelnynumer().equals("203-2-81")) {
                    System.out.println("");
                }
                System.out.println("saldokonto "+p.getKonto().getPelnynumer());
                Set<Waluty> waluty = pobierzWalutySaldoKonto(p, false);
                waluty.add(listawalut.get("PLN"));
                List<SaldoKonto> nowewiersze = new ArrayList<>();
                for (Waluty wal : waluty) {
                    SaldoKonto sal = sumujkwotywalutapln(p, wal); 
                    if (sal != null) {
                        nowewiersze.add(sal);
                    }
                }
                nowalista.addAll(nowewiersze);
                if (nowewiersze.size() > 1 || (nowewiersze.size() == 1 && !nowewiersze.get(0).getWalutadlabo().getSymbolwaluty().equals("PLN"))) {
                    SaldoKonto roznicekursowe = rozliczroznicekursowedwojki(p,nowewiersze);
                    if (roznicekursowe != null) {
                        nowalista.add(roznicekursowe);
                    }
                }
            } else if (p.getKonto().getPelnynumer().startsWith("1")) {
                List<Waluty> waluty = new ArrayList<>();
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
    
    private SaldoKonto rozliczroznicekursowedwojki(SaldoKonto p, List<SaldoKonto> nowewiersze) {
        if (p.getKonto().getPelnynumer().equals("203-2-81")) {
            System.out.println("");
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
    
    private SaldoKonto sumujkwotywalutapln(SaldoKonto p, Waluty w){
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

    private Collection<? extends SaldoKonto> przetworzWBRK(SaldoKonto p, Waluty waluta) {
        if (p.getKonto().getPelnynumer().equals("132-1")) {
            System.out.println("");
        }
        List<SaldoKonto> nowalista_wierszy = new ArrayList<>();
        Waluty walutapln = listawalut.get("PLN");
        //dodatnie oznacza saldo Wn ujemne saldo Ma
        double saldopln = obliczsaldoPLN(p.getZapisy());
        //dodatnie oznacza saldo Wn ujemne saldo Ma
        double saldowal = obliczsaldo(p.getZapisy(), waluta);
        double roznica = Z.z(Z.z(saldowal) - Z.z(saldopln));
        List<Double> kwotywpln = new ArrayList<>();
        if (roznica == 0.0) {
            nowalista_wierszy.add(p);
        } else {
            nowalista_wierszy.addAll(tworzwierszewaluty(waluta, p.getZapisy(), saldowal, kwotywpln));
            double sumasaldokont = obliczsaldoplnsaldokont(kwotywpln);
            double roznicekursowe = 0.0;
            if ((saldopln > 0 && sumasaldokont > 0) || (saldopln < 0 && sumasaldokont < 0)) {
                roznicekursowe = Z.z(Math.abs(saldopln) - Math.abs(sumasaldokont));
            } else {
                roznicekursowe = Z.z(Math.abs(saldopln) + Math.abs(sumasaldokont));
            }
            if (roznicekursowe != 0.0) {
                if (p.getSaldoMa() > 0.0) {
                    nowalista_wierszy.add(new SaldoKonto(p, -roznicekursowe, walutapln));
                } else {
                    nowalista_wierszy.add(new SaldoKonto(p, roznicekursowe, walutapln));
                }
            }
        }
        return nowalista_wierszy;
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
            if (waluta.getSymbolwaluty().equals(p.getSymbolWalutBOiSW())) {
                if (p.isWn()) {
                    saldo += p.getKwota();
                } else {
                    saldo -= p.getKwota();
                }
            }
        }
        return Z.z(saldo);
    }

    private double obliczsaldoPLN(List<StronaWiersza> zapisy) {
        double saldo = 0.0;
        for (StronaWiersza p : zapisy) {
            if (p.isWn()) {
                saldo += p.getKwotaPLN();
            } else {
                saldo -= p.getKwotaPLN();
            }
        }
        return Z.z(saldo);
    }

    private Collection<? extends SaldoKonto> tworzwierszewaluty(Waluty wal, List<StronaWiersza> zapisy, double saldowal, List<Double> kwotywpln) {
        int zapisydl = zapisy.size() - 1;
        double limit = saldowal;
        List<SaldoKonto> nowalista_wierszy = new ArrayList<>();
        if (limit > 0.0) {
            for (int i = zapisydl; i >= 0; i--) {
                StronaWiersza p = zapisy.get(i);
                double kurs = p.getKursWalutyBOSW();
                if (p.isWn() && kurs > 0.0) {
                    limit -= p.getKwota();
                    if (limit >= 0.0) {
                        nowalista_wierszy.add(new SaldoKonto(p, wal, p.getKwota(), p.getKwotaPLN()));
                        kwotywpln.add(p.getKwotaPLN());
                    } else {
                        double kwotapomniejszona = Z.z(p.getKwota() + limit);
                        double kwotapomniejszonapln = 0.0;
                        if (wal.getSymbolwaluty().equals("PLN")) {
                            kwotapomniejszonapln = kwotapomniejszona;
                        } else {
                            kwotapomniejszonapln = Z.z(kwotapomniejszona * kurs);
                        }
                        nowalista_wierszy.add(new SaldoKonto(p, wal, kwotapomniejszona, kwotapomniejszonapln));
                        kwotywpln.add(kwotapomniejszonapln);
                        break;
                    }
                }
            }
        } else {
            for (int i = zapisydl; i >= 0; i--) {
                StronaWiersza p = zapisy.get(i);
                double kurs = p.getKursWalutyBOSW();
                if (!p.isWn() && kurs > 0.0) {
                    limit += p.getKwota();
                    if (limit <= 0.0) {
                        nowalista_wierszy.add(new SaldoKonto(p, wal, p.getKwota(), p.getKwotaPLN()));
                        kwotywpln.add(-p.getKwotaPLN());
                    } else {
                        double kwotapomniejszona = Z.z(limit - p.getKwota());
                        double kwotapomniejszonapln = 0.0;
                        if (wal.getSymbolwaluty().equals("PLN")) {
                            kwotapomniejszonapln = kwotapomniejszona;
                        } else {
                            kwotapomniejszonapln = Z.z(kwotapomniejszona * kurs);
                        }
                        nowalista_wierszy.add(new SaldoKonto(p, wal, kwotapomniejszona, kwotapomniejszonapln));
                        kwotywpln.add(-kwotapomniejszonapln);
                        break;
                    }
                }
            }
        }
        return nowalista_wierszy;
    }

    private Collection<? extends SaldoKonto> przetworzPojedyncze(SaldoKonto p) {
        List<SaldoKonto> nowalista_wierszy = new ArrayList<>();
        Set<Waluty> waluty = new HashSet<>();
        for (StronaWiersza t : p.getZapisy()) {
            waluty.add(listawalut.get(t.getSymbolWalutBOiSW()));
        }
        for (Waluty wal : waluty) {
            nowalista_wierszy.addAll(sumujdlawaluty(wal, p.getZapisy()));
        }
        return nowalista_wierszy;
    }

    private Collection<? extends SaldoKonto> sumujdlawaluty(Waluty wal, List<StronaWiersza> zapisy) {
        List<StronaWiersza> zapisydopor = new ArrayList<>();
        for (StronaWiersza l : zapisy) {
            zapisydopor.add(l);
        }
        for (ListIterator<StronaWiersza> it = zapisy.listIterator(); it.hasNext();) {
            StronaWiersza w = it.next();
            double kwota = w.getKwota();
            boolean usun = false;
            for (ListIterator<StronaWiersza> it1 = zapisydopor.listIterator(); it1.hasNext();) {
                StronaWiersza w1 = it1.next();
                double kwota1 = w1.getKwota();
                if (kwota != 0.0 && kwota1 != 0.0 && Z.z(kwota) == Z.z(kwota1) && w.isWn() != w1.isWn()) {
                    it1.remove();
                    usun = true;
                    break;
                }
            }
            if (usun) {
                it.remove();
            }
        }
        List<SaldoKonto> zapisykonta = new ArrayList<>();
        for (StronaWiersza t : zapisy) {
            if (t.getPozostalo() != 0.0 && t.getSymbolWalutBOiSW().equals(wal.getSymbolwaluty()) && t.getToNieJestRRK()) {
                zapisykonta.add(new SaldoKonto(t, wal));
            }
        }
        return zapisykonta;
    }

    

    //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
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
