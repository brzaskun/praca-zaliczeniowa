/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.CechazapisuBean;
import beansFK.KontaFKBean;
import beansFK.StronaWierszaBean;
import dao.PodatnikUdzialyDAO;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.WierszBODAO;
import daoFK.WynikFKRokMcDAO;
import embeddablefk.SaldoKonto;
import entity.PodatnikUdzialy;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.WynikFKRokMc;
import enumy.FormaPrawna;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
    private double razemzapisycechakoszt;
    private double korektazapisycechakoszt;
    private double razemzapisycechaprzychod;
    private double korektazapisycechaprzychod;
    private Map<String, Double> podatnikkwotarazem;
    private double wynikfinansowy;
    private double wynikfinansowynetto;
    private Map<String, Double> pozycjeDoWyplatyNarastajaco;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;

    public SymulacjaWynikuView() {
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
        pozycjeDoWyplatyNarastajaco = symulacjaWynikuNarastajacoView.danedobiezacejsym();
        obliczkwotydowyplaty();
        System.out.println("");
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
        podatnikkwotarazem = new HashMap<>();
        pozycjePodsumowaniaWyniku = new ArrayList<>();
        double przychody = Z.z(sumuj(listakontaprzychody, B.b("przychody")));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("przychodyrazem"), przychody));
        double koszty = Z.z(sumuj(listakontakoszty, B.b("koszty")));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("kosztyrazem"), koszty));
        wynikfinansowy = Z.z(przychody - koszty);
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("wynikfinansowy"), wynikfinansowy));
        double npup = Z.z(razemzapisycechaprzychod);
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("npup"), npup));
        double nkup = Z.z(razemzapisycechakoszt);
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("nkup"), nkup));
        double wynikpodatkowy = Z.z(wynikfinansowy - npup - nkup);
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("wynikpodatkowy"), wynikpodatkowy));
        wynikfinansowynetto = wynikpodatkowy;
        if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_Z_O_O)) {
            double podstawaopodatkowania = Z.z0(wynikpodatkowy);
            double podatek = Z.z0(podstawaopodatkowania*0.19);
            pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("pdop"), podatek));
            wynikfinansowynetto = wynikpodatkowy - podatek; 
            pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("wynikfinansowynetto"), wynikfinansowynetto));
        }
        pozycjeObliczeniaPodatku = new ArrayList<>();
        try {
            int i = 1;
            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
            for (PodatnikUdzialy p : udzialy) {
                double udział = Z.z(Double.parseDouble(p.getUdzial())/100);
                pozycjeObliczeniaPodatku.add(new PozycjeSymulacji(p.getNazwiskoimie(), udział));
                double podstawaopodatkowania = Z.z0(udział*wynikfinansowynetto);
                pozycjeObliczeniaPodatku.add(new PozycjeSymulacji(B.b("podstawaopodatkowania")+" #"+String.valueOf(i), podstawaopodatkowania));
                double podatek = Z.z0(podstawaopodatkowania*0.19);
                pozycjeObliczeniaPodatku.add(new PozycjeSymulacji(B.b("podatekdochodowy")+" #"+String.valueOf(i++), podatek));
                podatnikkwotarazem.put(p.getNazwiskoimie(),Z.z0(podatek));
            }
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie określono udziałów w ustawieniach podatnika. Nie można obliczyć podatku");
        }
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
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView);
        List<StronaWiersza> zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "NKUP", wpisView.getMiesiacWpisu());
        double sumankup = CechazapisuBean.sumujcecha(zapisycechakoszt, "NKUP", wpisView.getMiesiacWpisu());
        zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "KUPMN", wpisView.getMiesiacWpisu());
        double sumankupmn = CechazapisuBean.sumujcecha(zapisycechakoszt, "KUPMN", wpisView.getMiesiacWpisu());
        double sumakupmnPoprzedniMc = 0.0;
        if (!wpisView.getMiesiacWpisu().equals("01")) {
            zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "KUPMN", wpisView.getMiesiacUprzedni());
            sumakupmnPoprzedniMc = CechazapisuBean.sumujcecha(zapisycechakoszt, "KUPMN", wpisView.getMiesiacUprzedni());
        }
        razemzapisycechakoszt = Z.z(sumankup - sumankupmn + sumakupmnPoprzedniMc);
        List<StronaWiersza> zapisycechaprzychod = CechazapisuBean.pobierzwierszezcecha(zapisy, "NPUP", wpisView.getMiesiacWpisu());
        razemzapisycechaprzychod = Z.z(CechazapisuBean.sumujcecha(zapisycechaprzychod, "NPUP", wpisView.getMiesiacWpisu()));
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
        wynikFKRokMc.setNpup(pozycje.get(3).getWartosc());
        wynikFKRokMc.setNkup(pozycje.get(4).getWartosc());
        wynikFKRokMc.setWynikpodatkowy(pozycje.get(5).getWartosc());
        if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_Z_O_O)) {
            wynikFKRokMc.setPodatek(pozycje.get(6).getWartosc());
            wynikFKRokMc.setWynikfinansowynetto(pozycje.get(7).getWartosc());
        }
        wynikFKRokMc.setWprowadzil(wpisView.getWprowadzil().getLogin());
        wynikFKRokMc.setData(new Date());
        //wywalilem bo ozajmuje za duzo miejsca
//        wynikFKRokMc.setListaprzychody(listakontaprzychody);
//        wynikFKRokMc.setListakoszty(listakontakoszty);
        try {
            WynikFKRokMc pobrany = wynikFKRokMcDAO.findWynikFKRokMc(wynikFKRokMc);
            wynikFKRokMcDAO.destroy(pobrany);
        } catch (Exception e) {  E.e(e);
        }
        try {
            wynikFKRokMcDAO.edit(wynikFKRokMc);
            Msg.msg("Zachowano wynik");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie zachowano wyniku.");
        }
    }
    
    private void obliczkwotydowyplaty() {
        pozycjeDoWyplaty = new ArrayList<>();
        try {
            int i = 1;
            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
            for (PodatnikUdzialy p : udzialy) {
                double udział = Z.z(Double.parseDouble(p.getUdzial())/100);
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(p.getNazwiskoimie()+" "+B.b("udział"), udział));
                double dowyplaty = Z.z(udział*wynikfinansowynetto);
                double zaplacono = Z.z(podatnikkwotarazem.get(p.getNazwiskoimie()));
                double zamc = Z.z(dowyplaty-zaplacono);
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("należnazamc")+" #"+String.valueOf(i), zamc));
                double wyplaconopopmce = 0.0;
                if (pozycjeDoWyplatyNarastajaco != null && pozycjeDoWyplatyNarastajaco.size() > 0) {
                wyplaconopopmce = pozycjeDoWyplatyNarastajaco.get(p.getNazwiskoimie());
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wypłaconopopmce")+" #"+String.valueOf(i), Z.z(wyplaconopopmce)));
                double roznica = Z.z(wyplaconopopmce+zamc);
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dowypłatyodpocz.rok")+" #"+String.valueOf(i), roznica));
                } else {
                    if (wpisView.getMiesiacWpisu().equals("01")) {
                        pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("pierwszymc")+" #"+String.valueOf(i), 0.0));
                        pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wroku")+" #"+String.valueOf(i), 0.0));
                    } else {
                        pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("niezachowanopop.mcy")+" #"+String.valueOf(i), 0.0));
                        pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("niemożnaobliczyć")+" #"+String.valueOf(i), 0.0));
                    }
                }
                i++;
            }
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie określono udziałów w ustawieniach podatnika. Nie można obliczyć podatku");
        }
    }
    //<editor-fold defaultstate="collapsed" desc="comment">

    public List<SaldoKonto> getWybraneprzychody() {
        return wybraneprzychody;
    }

    public void setWybraneprzychody(List<SaldoKonto> wybraneprzychody) {
        this.wybraneprzychody = wybraneprzychody;
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
}
