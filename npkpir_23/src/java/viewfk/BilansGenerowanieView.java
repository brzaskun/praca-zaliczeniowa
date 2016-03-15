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
import embeddablefk.SaldoKonto;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import entityfk.Waluty;
import entityfk.WierszBO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
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
    private boolean sabledy;

    public BilansGenerowanieView() {
        this.komunikatyok = new ArrayList<>();
        this.komunikatyok.add("Nie rozpoczęto analizy");
        this.komunikatyerror = new ArrayList<>();
        this.listawalut = new HashMap<>();
    }

    @PostConstruct
    private void init() {
        List<Waluty> waluty = walutyDAOfk.findAll();
        for (Waluty p : waluty) {
            listawalut.put(p.getSymbolwaluty(), p);
        }
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
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

    public SaldoAnalitykaView getSaldoAnalitykaView() {
        return saldoAnalitykaView;
    }

    public void setSaldoAnalitykaView(SaldoAnalitykaView saldoAnalitykaView) {
        this.saldoAnalitykaView = saldoAnalitykaView;
    }

//</editor-fold>
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
        wierszBODAO.deletePodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        this.komunikatyok = new ArrayList<>();
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
            Waluty walpln = saldoAnalitykaView.initGenerowanieBO();
            List<SaldoKonto> listaSaldoKonto = saldoAnalitykaView.getListaSaldoKonto();
            for (SaldoKonto p : listaSaldoKonto) {
                if (p.getKonto().getPelnynumer().equals("201-2-19") && p.getSaldoWn() == 123.0) {
                    System.out.println("");
                }
            }
            List<Konto> kontaNowyRok = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            resetujBOnaKonto(kontaNowyRok);
            Konto kontowyniku = PlanKontFKBean.findKonto860(kontaNowyRok);
            obliczkontawynikowe(kontowyniku, listaSaldoKonto, walpln);
            //tutaj trzeba przerobic odpowiednio listaSaldo
            List<SaldoKonto> listaSaldoKontoPrzetworzone = przetwarzajSaldoKonto(listaSaldoKonto);
            List<WierszBO> wierszeBO = new ArrayList<>();
            zrobwierszeBO(wierszeBO, listaSaldoKontoPrzetworzone, kontaNowyRok);
            komunikatyok.add("Wygenerowano BO na rok " + wpisView.getRokWpisuSt());
            wierszBODAO.editList(wierszeBO);
            zapiszBOnaKontach(wierszeBO, kontaNowyRok);
            obliczsaldoBOkonta(kontaNowyRok);
            kontoDAO.editList(kontaNowyRok);
            bilansWprowadzanieView.init();
            bilansWprowadzanieView.zapiszBilansBOdoBazy();
            Msg.msg("Generuje bilans");
        }
    }

    private void obliczsaldoBOkonta(List<Konto> przygotowanalista) {
        for (Konto r : przygotowanalista) {
            if (r.getPelnynumer().equals("201-2-23")) {
                System.out.println("");
            }
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
            k.setBoWn(k.getBoWn() + p.getKwotaWnPLN());
            k.setBoMa(k.getBoMa() + p.getKwotaMaPLN());
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

    private void zrobwierszeBO(List<WierszBO> wierszeBO, List<SaldoKonto> listaSaldoKonto, List<Konto> kontaNowyRok) {
        if (!listaSaldoKonto.isEmpty()) {
            for (SaldoKonto p : listaSaldoKonto) {
                if (p.getKonto().getBilansowewynikowe().equals("bilansowe")) {
                    Konto k = nowekonto(p.getKonto(), kontaNowyRok);
                    wierszeBO.add(new WierszBO(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt(), k, p.getWalutadlabo()));
                }
            }
        }
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
            if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe") && p.getKonto().getPelnynumer().startsWith("20")) {
                nowalista.addAll(przetworzPojedyncze(p));
            } else {
                nowalista.add(p);
            }
        }
        return nowalista;
    }

    private Collection<? extends SaldoKonto> przetworzPojedyncze(SaldoKonto p) {
        List<SaldoKonto> nowalista_wierszy = new ArrayList<>();
        Set<Waluty> waluty = new HashSet<>();
        for (StronaWiersza t : p.getZapisy()) {
            waluty.add(listawalut.get(t.getSymbolWalut()));
        }
        for (Waluty wal : waluty) {
            nowalista_wierszy.addAll(sumujdlawaluty(wal, p.getZapisy()));
        }
        return nowalista_wierszy;
    }

    private Collection<? extends SaldoKonto> sumujdlawaluty(Waluty wal, List<StronaWiersza> zapisy) {
        List<SaldoKonto> zapisykonta = new ArrayList<>();
        for (StronaWiersza t : zapisy) {
            System.out.println("generowanie bo " + t);
            if (t.getPozostalo() != 0.0 && t.getSymbolWalut().equals(wal.getSymbolwaluty()) && t.getToNieJestRRK()) {
                if (t.getKwota() == 26.20) {
                    System.out.println("");
                }
                zapisykonta.add(new SaldoKonto(t, wal));
            }
        }
        return zapisykonta;
    }

}
