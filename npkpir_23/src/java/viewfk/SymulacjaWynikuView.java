/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.CechazapisuBean;
import beansFK.KontaFKBean;
import beansFK.StronaWierszaBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.WierszBODAO;
import daoFK.WynikFKRokMcDAO;
import em.Em;
import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entityfk.Cechazapisu;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.WynikFKRokMc;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import msg.B;
import msg.Msg;
import pdf.PdfSymulacjaWyniku;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SymulacjaWynikuView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> listakontakoszty;
    private List<SaldoKonto> listakontaprzychody;
    private List<SaldoKonto> sumaSaldoKontoPrzychody;
    private List<SaldoKonto> sumaSaldoKontoKoszty;
    private List<PozycjeSymulacji> pozycjePodsumowaniaWyniku;
    private List<PozycjeSymulacji> pozycjeObliczeniaPodatku;
    private List<PozycjeSymulacji> pozycjeDoWyplaty;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{symulacjaWynikuNarastajacoView}")
    private SymulacjaWynikuNarastajacoView symulacjaWynikuNarastajacoView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private WynikFKRokMcDAO wynikFKRokMcDAO;
    private List<SaldoKonto>wybraneprzychody;
    private double sumaprzychody;
    private List<SaldoKonto>wybranekoszty;
    private double sumakoszty;
    private double nkup;
    private double kupmn_mc;
    private double kupmn_mc_pop;
    private double npup;
    private double pmn_mc;
    private double pmn_mc_pop;
    private List<CechyzapisuPrzegladView.CechaStronaWiersza> zapisyZCecha;
    private double wynikfinansowy;
    private boolean tylkokontasyntetyczne;


    public SymulacjaWynikuView() {
         E.m(this);
        sumaSaldoKontoPrzychody = new ArrayList<>();
    }

    public void init() {
        List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlitykaWynikowe(wpisView);
        List<Konto> kontaklientaprzychody = new ArrayList<>();
        List<Konto> kontaklientakoszty = new ArrayList<>();
        for (Konto p : kontaklienta) {
            if (p.getZwyklerozrachszczegolne().equals("szczególne")) {
                kontaklientakoszty.add(p);
                kontaklientaprzychody.add(p);
            } else if (p.isPrzychod0koszt1()) {
                kontaklientakoszty.add(p);
            } else {
                kontaklientaprzychody.add(p);
            }
        }
        listakontaprzychody = przygotowanalistasaldR(kontaklientaprzychody, 0);
        listakontakoszty = przygotowanalistasaldR(kontaklientakoszty, 1);
        pobierzzapisyzcechami();
        obliczsymulacje();
//        pozycjeDoWyplatyNarastajaco = symulacjaWynikuNarastajacoView.danedobiezacejsym();
//        obliczkwotydowyplaty();
        System.out.println("");
        tylkokontasyntetyczneoblicz();
    }

    public void odswiezsymulacjewynikuanalityczne() {
        wpisView.wpisAktualizuj();
        init();
    }

    
     private List<SaldoKonto> przygotowanalistasaldR(List<Konto> kontaklienta, int przychod0koszt1) {
        List<SaldoKonto> przygotowanalista = new ArrayList<>();
        List<StronaWiersza> zapisyRok = pobierzzapisyR();
        for (Konto p : kontaklienta) {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            naniesZapisyNaKontoR(saldoKonto, p, zapisyRok);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalista, przychod0koszt1);
        }
        for (int i = 1; i < przygotowanalista.size() + 1; i++) {
            przygotowanalista.get(i - 1).setId(i);
        }
        sumaSaldoKontoPrzychody = new ArrayList<>();
        sumaSaldoKontoPrzychody.add(KontaFKBean.sumujsaldakont(przygotowanalista));
        return przygotowanalista;
    }
    

    private void naniesZapisyNaKontoR(SaldoKonto saldoKonto, Konto p, List<StronaWiersza> zapisyRok) {
        double sumaWn = 0.0;
        double sumaMa = 0.0;
        for (StronaWiersza r : zapisyRok) {
            if (r.getKonto().equals(p)) {
                if (r.getWnma().equals("Wn")) {
                    sumaWn += r.getKwotaPLN();
                } else {
                    sumaMa += r.getKwotaPLN();
                }
                saldoKonto.getZapisy().add(r);
            }
        }
        saldoKonto.setObrotyWn(sumaWn);
        saldoKonto.setObrotyMa(sumaMa);
    }
    
    

    private void dodajdolisty(SaldoKonto saldoKonto, List<SaldoKonto> przygotowanalista, int przychod0koszt1) {
        boolean kontoszczegolne = saldoKonto.getKonto().getZwyklerozrachszczegolne().equals("szczególne");
        if (kontoszczegolne) {
            if (saldoKonto.getSaldoWn() != 0.0 && przychod0koszt1 == 1) {
                przygotowanalista.add(saldoKonto);
                return;
            }
            if (saldoKonto.getSaldoMa() != 0.0 && przychod0koszt1 == 0) {
                przygotowanalista.add(saldoKonto);
                return;
            }
        } else {
            if (saldoKonto.getObrotyBoWn() != 0.0 || saldoKonto.getBoWn() != 0.0) {
                przygotowanalista.add(saldoKonto);
                return;
            }
            if (saldoKonto.getObrotyBoMa() != 0.0 || saldoKonto.getBoMa() != 0.0) {
                przygotowanalista.add(saldoKonto);
                return;
            }
        }
    }

   
    private List<StronaWiersza> pobierzzapisyR() {
        List<StronaWiersza> zapisywynikrokmc = stronaWierszaDAO.findStronaByPodatnikRokMcWynik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        return zapisywynikrokmc;
    }

    private double sumuj(List<SaldoKonto> listakonta, String przychodykoszty) {
        double suma = 0.0;
        if (przychodykoszty.equals("przychody")) {
            for (SaldoKonto p : listakonta) {
                suma += (p.getSaldoMa() - p.getSaldoWn());
            }
        }
        if (przychodykoszty.equals("koszty")) {
            for (SaldoKonto p : listakonta) {
                suma += (p.getSaldoWn() - p.getSaldoMa());
            }
        }
        return suma;
    }

    private void obliczsymulacje() {
        pozycjePodsumowaniaWyniku = new ArrayList<>();
        double przychody = Z.z(sumuj(listakontaprzychody, B.b("przychody")));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("przychodyrazem"), przychody));
        double koszty = Z.z(sumuj(listakontakoszty, B.b("koszty")));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("kosztyrazem"), koszty));
        wynikfinansowy = Z.z(przychody - koszty);
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("wynikfinansowy"), wynikfinansowy));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("nkup"), Z.z(nkup)));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji("kupmn", Z.z(kupmn_mc)));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji("kupmn pop mce", Z.z(kupmn_mc_pop)));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("npup"),-Z.z(npup)));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji("pmn",Z.z(pmn_mc)));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji("pmn pop mce",Z.z(pmn_mc_pop)));
        double wynikpodatkowy = Z.z(wynikfinansowy + nkup + kupmn_mc + kupmn_mc_pop - npup - pmn_mc - pmn_mc_pop);
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("wynikpodatkowy"), wynikpodatkowy));
    }
    
    
    
    public void sumazapisowPrzychody() {
        sumaprzychody = 0.0;
        for (SaldoKonto p : sumaSaldoKontoPrzychody) {
            sumaprzychody += p.getSaldoMa();
            sumaprzychody -= p.getSaldoWn();
        }
    }
    
    public void sumazapisowKoszty() {
        sumakoszty = 0.0;
        for (SaldoKonto p : sumaSaldoKontoKoszty) {
            sumakoszty += p.getSaldoWn();
            sumakoszty -= p.getSaldoMa();
        }
    }
    
    public void drukuj(int i) {
        PdfSymulacjaWyniku.drukuj(listakontaprzychody, listakontakoszty, pozycjePodsumowaniaWyniku, pozycjeObliczeniaPodatku, wpisView, i, pozycjeDoWyplaty);
    }

    private void pobierzzapisyzcechami() {
        zapisyZCecha = new ArrayList<>();
        //pobieram wszystkie strony wiersza z roku
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikoweCecha(stronaWierszaDAO, wpisView);
        //pobieram strony wiersza z cecha i wyluskuje strony wiersza z dokumentu z cecha
        List<StronaWiersza> zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "NKUP", wpisView.getMiesiacWpisu());
        for (StronaWiersza stw : zapisycechakoszt) {
            for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCecha.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw));
                }
        }
        //sumuje
        nkup = CechazapisuBean.sumujcecha(zapisycechakoszt, "NKUP", wpisView.getMiesiacWpisu());
        zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "KUPMN", wpisView.getMiesiacWpisu());
        for (StronaWiersza stw : zapisycechakoszt) {
            for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCecha.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw));
                }
        }
        kupmn_mc = CechazapisuBean.sumujcecha(zapisycechakoszt, "KUPMN", wpisView.getMiesiacWpisu());
        kupmn_mc_pop = 0.0;
            zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "KUPMN", wpisView.getMiesiacUprzedni());
            kupmn_mc_pop = -CechazapisuBean.sumujcecha(zapisycechakoszt, "KUPMN", wpisView.getMiesiacUprzedni());
            for (StronaWiersza stw : zapisycechakoszt) {
                for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCecha.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw, "popmc"));
                }
            }
        List<StronaWiersza> zapisycechaprzychod = CechazapisuBean.pobierzwierszezcecha(zapisy, "NPUP", wpisView.getMiesiacWpisu());
        for (StronaWiersza stw : zapisycechaprzychod) {
            for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCecha.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw));
                }
        }
        npup = CechazapisuBean.sumujcecha(zapisycechaprzychod, "NPUP", wpisView.getMiesiacWpisu());
        zapisycechaprzychod = CechazapisuBean.pobierzwierszezcecha(zapisy, "PMN", wpisView.getMiesiacWpisu());
        for (StronaWiersza stw : zapisycechaprzychod) {
            for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCecha.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw));
                }
        }
        pmn_mc = -CechazapisuBean.sumujcecha(zapisycechaprzychod, "PMN", wpisView.getMiesiacWpisu());
        pmn_mc_pop = 0.0;
            zapisycechaprzychod = CechazapisuBean.pobierzwierszezcecha(zapisy, "PMN", wpisView.getMiesiacUprzedni());
            pmn_mc_pop = CechazapisuBean.sumujcecha(zapisycechaprzychod, "PMN", wpisView.getMiesiacUprzedni());
            for (StronaWiersza stw : zapisycechaprzychod) {
                for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCecha.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw, "popmc"));
                }
            }
    }
    
    public void zaksiegujwynik () {
        List<PozycjeSymulacji> pozycje = new ArrayList<>(pozycjePodsumowaniaWyniku);
        WynikFKRokMc wynikFKRokMc = new WynikFKRokMc();
        wynikFKRokMc.setPodatnikObj(wpisView.getPodatnikObiekt());
        wynikFKRokMc.setRok(wpisView.getRokWpisuSt());
        wynikFKRokMc.setMc(wpisView.getMiesiacWpisu());
        wynikFKRokMc.setPrzychody(pozycje.get(0).getWartosc());
        wynikFKRokMc.setKoszty(pozycje.get(1).getWartosc());
        wynikFKRokMc.setWynikfinansowy(pozycje.get(2).getWartosc());
        wynikFKRokMc.setNkup(pozycje.get(3).getWartosc()+pozycje.get(4).getWartosc()+pozycje.get(5).getWartosc());
        wynikFKRokMc.setNpup(pozycje.get(6).getWartosc()+pozycje.get(7).getWartosc()+pozycje.get(8).getWartosc());
        wynikFKRokMc.setWynikpodatkowy(pozycje.get(9).getWartosc());
        wynikFKRokMc.setUdzialowiec("firma");
//        if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_Z_O_O)) {
//            wynikFKRokMc.setPodatek(pozycje.get(6).getWartosc());
//            wynikFKRokMc.setWynikfinansowynetto(pozycje.get(7).getWartosc());
//        }
        wynikFKRokMc.setWprowadzil(wpisView.getWprowadzil().getLogin());
        wynikFKRokMc.setData(new Date());
        //wywalilem bo ozajmuje za duzo miejsca
//        wynikFKRokMc.setListaprzychody(listakontaprzychody);
//        wynikFKRokMc.setListakoszty(listakontakoszty);
        try {
            WynikFKRokMc pobrany = wynikFKRokMcDAO.findWynikFKRokMcFirma(wynikFKRokMc);
            wynikFKRokMcDAO.destroy(pobrany);
        } catch (Exception e) {  
            E.e(e);
        }
        try {
            wynikFKRokMcDAO.dodaj(wynikFKRokMc);
            symulacjaWynikuNarastajacoView.init();
            Msg.msg("Zachowano wynik");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie zachowano wyniku.");
        }
    }
   
    
    public void tylkokontasyntetyczneoblicz() {
        if (tylkokontasyntetyczne) {
            listakontaprzychody = zredukuj(listakontaprzychody);
            listakontakoszty = zredukuj(listakontakoszty);
        }
    }
    
    private List<SaldoKonto> zredukuj(List<SaldoKonto> lista) {
        List<SaldoKonto> macierzyste = new ArrayList<>();
        for (SaldoKonto p : lista) {
            SaldoKonto macierzystewiersz = jestmacierzyste(macierzyste,p.getKonto().getKontomacierzyste());
            if (macierzystewiersz != null) {
                naniesnamacierzyste(macierzystewiersz,p);
            } else {
                macierzystewiersz = stworzmacierzyste(p);
                macierzyste.add(macierzystewiersz);
            }
        }
        int i = 1;
        for (SaldoKonto p : macierzyste) {
            p.setId(i);
            p.wyliczSaldo();
        }
        return macierzyste;
    }
       
    private void naniesnamacierzyste(SaldoKonto macierzystewiersz, SaldoKonto p) {
        macierzystewiersz.setObrotyWn(macierzystewiersz.getObrotyWn()+p.getObrotyBoWn());
        macierzystewiersz.setObrotyMa(macierzystewiersz.getObrotyMa()+p.getObrotyBoMa());
        macierzystewiersz.setObrotyBoWn(macierzystewiersz.getObrotyBoWn()+p.getObrotyBoWn());
        macierzystewiersz.setObrotyBoMa(macierzystewiersz.getObrotyBoMa()+p.getObrotyBoMa());
    }

    private SaldoKonto stworzmacierzyste(SaldoKonto p) {
        SaldoKonto macierzystewiersz = new SaldoKonto();
        Konto mac = p.getKonto().getKontomacierzyste() != null ? p.getKonto().getKontomacierzyste() : p.getKonto();
        macierzystewiersz.setKonto(mac);
        macierzystewiersz.setObrotyWn(macierzystewiersz.getObrotyWn()+p.getObrotyBoWn());
        macierzystewiersz.setObrotyMa(macierzystewiersz.getObrotyMa()+p.getObrotyBoMa());
        macierzystewiersz.setObrotyBoWn(macierzystewiersz.getObrotyBoWn()+p.getObrotyBoWn());
        macierzystewiersz.setObrotyBoMa(macierzystewiersz.getObrotyBoMa()+p.getObrotyBoMa());
        return macierzystewiersz;
    }

    private SaldoKonto jestmacierzyste(List<SaldoKonto> macierzyste, Konto kontomacierzyste) {
        SaldoKonto zwrot = null;
        for (SaldoKonto p : macierzyste) {
            if (p.getKonto().equals(kontomacierzyste)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
    

    //<editor-fold defaultstate="collapsed" desc="comment">

    public List<SaldoKonto> getWybraneprzychody() {
        return wybraneprzychody;
    }

    public void setWybraneprzychody(List<SaldoKonto> wybraneprzychody) {
        this.wybraneprzychody = wybraneprzychody;
    }

    public boolean isTylkokontasyntetyczne() {
        return tylkokontasyntetyczne;
    }

    public void setTylkokontasyntetyczne(boolean tylkokontasyntetyczne) {
        this.tylkokontasyntetyczne = tylkokontasyntetyczne;
    }

    public List<CechyzapisuPrzegladView.CechaStronaWiersza> getZapisyZCecha() {
        return zapisyZCecha;
    }

    public void setZapisyZCecha(List<CechyzapisuPrzegladView.CechaStronaWiersza> zapisyZCecha) {
        this.zapisyZCecha = zapisyZCecha;
    }

    public SymulacjaWynikuNarastajacoView getSymulacjaWynikuNarastajacoView() {
        return symulacjaWynikuNarastajacoView;
    }

    public void setSymulacjaWynikuNarastajacoView(SymulacjaWynikuNarastajacoView symulacjaWynikuNarastajacoView) {
        this.symulacjaWynikuNarastajacoView = symulacjaWynikuNarastajacoView;
    }

    public List<PozycjeSymulacji> getPozycjeDoWyplaty() {
        return pozycjeDoWyplaty;
    }

    public void setPozycjeDoWyplaty(List<PozycjeSymulacji> pozycjeDoWyplaty) {
        this.pozycjeDoWyplaty = pozycjeDoWyplaty;
    }

    public double getSumaprzychody() {
        return Z.z(sumaprzychody);
    }

    public void setSumaprzychody(double sumaprzychody) {
        this.sumaprzychody = sumaprzychody;
    }

    public List<SaldoKonto> getWybranekoszty() {
        return wybranekoszty;
    }

    public void setWybranekoszty(List<SaldoKonto> wybranekoszty) {
        this.wybranekoszty = wybranekoszty;
    }

    public double getSumakoszty() {
        return Z.z(sumakoszty);
    }

    public void setSumakoszty(double sumakoszty) {
        this.sumakoszty = sumakoszty;
    }

    public List<PozycjeSymulacji> getPozycjePodsumowaniaWyniku() {
        return pozycjePodsumowaniaWyniku;
    }

    public void setPozycjePodsumowaniaWyniku(List<PozycjeSymulacji> pozycjePodsumowaniaWyniku) {
        this.pozycjePodsumowaniaWyniku = pozycjePodsumowaniaWyniku;
    }

  

    public List<SaldoKonto> getSumaSaldoKontoPrzychody() {
        return sumaSaldoKontoPrzychody;
    }

    public void setSumaSaldoKontoPrzychody(List<SaldoKonto> sumaSaldoKontoPrzychody) {
        this.sumaSaldoKontoPrzychody = sumaSaldoKontoPrzychody;
    }

    public List<SaldoKonto> getListakontakoszty() {
        return listakontakoszty;
    }

    public void setListakontakoszty(List<SaldoKonto> listakontakoszty) {
        this.listakontakoszty = listakontakoszty;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<SaldoKonto> getListakontaprzychody() {
        return listakontaprzychody;
    }

    public void setListakontaprzychody(List<SaldoKonto> listakontaprzychody) {
        this.listakontaprzychody = listakontaprzychody;
    }

    public List<SaldoKonto> getSumaSaldoKontoKoszty() {
        return sumaSaldoKontoKoszty;
    }

    public void setSumaSaldoKontoKoszty(List<SaldoKonto> sumaSaldoKontoKoszty) {
        this.sumaSaldoKontoKoszty = sumaSaldoKontoKoszty;
    }

    public List<PozycjeSymulacji> getPozycjeObliczeniaPodatku() {
        return pozycjeObliczeniaPodatku;
    }

    public void setPozycjeObliczeniaPodatku(List<PozycjeSymulacji> pozycjeObliczeniaPodatku) {
        this.pozycjeObliczeniaPodatku = pozycjeObliczeniaPodatku;
    }

    
    
    


//</editor-fold>
    public static class PozycjeSymulacji {

        private String nazwa;
        private double wartosc;

        public PozycjeSymulacji() {
        }

        public PozycjeSymulacji(String nazwa, double wartosc) {
            this.nazwa = nazwa;
            this.wartosc = wartosc;
        }

        @Override
        public String toString() {
            return "PozycjeSymulacji{" + "nazwa=" + nazwa + ", wartosc=" + wartosc + '}';
        }
        
        public String getNazwa() {
            return nazwa;
        }

        public void setNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        public double getWartosc() {
            return wartosc;
        }

        public void setWartosc(double wartosc) {
            this.wartosc = wartosc;
        }

    }

    public static void main(String[] args) {
        double wynikfinansowy = 49963.29;
        double udzial = Double.valueOf("1")/100;
        System.out.println(udzial);
        double podstawaopodatkowania = Z.z(udzial*wynikfinansowy);
        System.out.println(podstawaopodatkowania);
        System.out.println(Z.z0(podstawaopodatkowania*0.19));
    }

    public static class PozycjeSymulacjiTabela {

        private String udzialowiec;
        private double udział;
        private double wynikfinansowyudzial;
        private double podstawaopodatkowania;
        private double podatekdochodowy;
        private double zapłacono;
        private double dozapłaty;
        private double dowypłaty;
        
        public PozycjeSymulacjiTabela() {
        }

        @Override
        public String toString() {
            return "PozycjeSymulacjiTabela{" + "udzialowiec=" + udzialowiec + ", udzia\u0142=" + udział + ", wynikfinansowyudzial=" + wynikfinansowyudzial + ", podstawaopodatkowania=" + podstawaopodatkowania + ", podatekdochodowy=" + podatekdochodowy + ", zap\u0142acono=" + zapłacono + ", dozap\u0142aty=" + dozapłaty + ", dowyp\u0142aty=" + dowypłaty + '}';
        }

        
        
        public String getUdzialowiec() {
            return udzialowiec;
        }

        public void setUdzialowiec(String udzialowiec) {
            this.udzialowiec = udzialowiec;
        }

        public double getWynikfinansowyudzial() {
            return wynikfinansowyudzial;
        }

        public void setWynikfinansowyudzial(double wynikfinansowyudzial) {
            this.wynikfinansowyudzial = wynikfinansowyudzial;
        }

        public double getUdział() {
            return udział;
        }

        public void setUdział(double udział) {
            this.udział = udział;
        }

        public double getPodstawaopodatkowania() {
            return podstawaopodatkowania;
        }

        public void setPodstawaopodatkowania(double podstawaopodatkowania) {
            this.podstawaopodatkowania = podstawaopodatkowania;
        }

        public double getPodatekdochodowy() {
            return podatekdochodowy;
        }

        public void setPodatekdochodowy(double podatekdochodowy) {
            this.podatekdochodowy = podatekdochodowy;
        }

        public double getZapłacono() {
            return zapłacono;
        }

        public void setZapłacono(double zapłacono) {
            this.zapłacono = zapłacono;
        }

        public double getDozapłaty() {
            return dozapłaty;
        }

        public void setDozapłaty(double dozapłaty) {
            this.dozapłaty = dozapłaty;
        }

        public double getDowypłaty() {
            return dowypłaty;
        }

        public void setDowypłaty(double dowypłaty) {
            this.dowypłaty = dowypłaty;
        }
        
        
        public static void main(String[] args) throws Exception  {
            EntityManager em = Em.getEm();
            Podatnik podatnik = (Podatnik) em.createNamedQuery("Podatnik.findByNip").setParameter("nip", "9552340951").getSingleResult();
            //List<StronaWiersza> pobranezapisy = em.createQuery("SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND (SIZE(t.cechazapisuLista) > 0 OR SIZE(t.wiersz.dokfk.cechadokumentuLista) > 0)").setParameter("podatnikObj", podatnik).getResultList();
            List<StronaWiersza> pobranezapisy = em.createQuery("SELECT t FROM StronaWiersza  t JOIN t.wiersz.dokfk s WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND (SIZE(t.cechazapisuLista) > 0 OR SIZE(s.cechadokumentuLista) > 0)").setParameter("podatnikObj", podatnik).getResultList();
            System.out.println("");
        }
    }
}
