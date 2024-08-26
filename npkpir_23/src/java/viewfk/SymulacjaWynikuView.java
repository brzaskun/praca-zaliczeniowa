/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.CechazapisuBean;
import beansFK.KontaFKBean;
import beansFK.StronaWierszaBean;
import comparator.SaldoKontocomparator;
import dao.CechazapisuDAOfk;
import dao.KontoDAOfk;
import dao.PodatnikUdzialyDAO;
import dao.StronaWierszaDAO;
import dao.TransakcjaDAO;
import dao.WierszBODAO;
import dao.WynikFKRokMcDAO;
import data.Data;
import embeddable.Mce;
import embeddablefk.PozycjeSymulacjiNowe;
import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entity.PodatnikUdzialy;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import entityfk.WynikFKRokMc;
import error.E;
import interceptor.ConstructorInterceptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.B;
import msg.Msg;
import pdf.PdfSymulacjaWyniku;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class SymulacjaWynikuView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> listakontakoszty;
    private List<SaldoKonto> listakontaprzychody;
    private List<SaldoKonto> sumaSaldoKontoPrzychody;
    private List<SaldoKonto> sumaSaldoKontoKoszty;
    private List<PozycjeSymulacji> pozycjePodsumowaniaWyniku;
    private List<PozycjeSymulacjiNowe> pozycjePodsumowaniaWynikuNowe;
    private List<PozycjeSymulacji> pozycjeObliczeniaPodatku;
    private List<PozycjeSymulacji> pozycjeDoWyplaty;
    @Inject
    private WpisView wpisView;
    @Inject
    private SymulacjaWynikuNarastajacoView symulacjaWynikuNarastajacoView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private WynikFKRokMcDAO wynikFKRokMcDAO;
    @Inject
    private TransakcjaDAO transakcjaDAO;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
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
    private List<CechyzapisuPrzegladView.CechaStronaWiersza> zapisyZCechafiltered;
    private List<CechyzapisuPrzegladView.CechaStronaWiersza> zapisyZCechaP;
    private List<CechyzapisuPrzegladView.CechaStronaWiersza> zapisyZCechafilteredP;
    private double wynikfinansowy;
    private boolean tylkokontasyntetyczne;
    private String wybranacechadok;
    private String wybranawaluta;
    private List<Cechazapisu> pobranecechypodatnik;
    private List<String> pobranewalutypodatnik;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    private String mcod;
    private String mcdo;
    private List<Dokfk> dokumentyzodliczonymvat;
    private double sumavatprzychody;
    private double sumavatkoszty;
    private String bilansnadzien;
    private String bilansoddnia;
    private String bilansoddniaRokPop;
    private String bilansnadzienRokPop;
    

    public SymulacjaWynikuView() {
        this.sumaSaldoKontoPrzychody = Collections.synchronizedList(new ArrayList<>());
        this.pozycjePodsumowaniaWynikuNowe = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void inita() {
        mcod = wpisView.getMiesiacWpisu();
        mcdo = wpisView.getMiesiacWpisu();
        bilansoddniaRokPop = wpisView.getOpodatkowanieRokUprzedni()!=null?wpisView.getOpodatkowanieRokUprzedni().getDatarozpoczecia():null;
        bilansnadzienRokPop = wpisView.getOpodatkowanieRokUprzedni()!=null?wpisView.getOpodatkowanieRokUprzedni().getDatazakonczenia():null;
        bilansnadzien = Data.ostatniDzien(wpisView);
        String bilod = bilansoddnia = wpisView.getOpodatkowanieRokBiezacy().getDatarozpoczecia();
        String pierwszydzien = Data.pierwszyDzien(wpisView);
        bilansoddnia = pierwszydzien;
        if (Data.getRok(bilod).equals(Data.getRok(pierwszydzien))&&Data.getMc(bilod).equals(Data.getMc(pierwszydzien))) {
            bilansoddnia = bilod;
        }
        
    }
    
    public void init() { //E.m(this);
        List<StronaWiersza> zapisyRok = pobierzzapisyRokMc(mcod, mcdo);
        initcd(zapisyRok);
    }
    
    public void initksiegowa() { //E.m(this);
        List<StronaWiersza> zapisyRok = pobierzzapisyRokMcKsiegowa(bilansoddnia, bilansnadzien);
        initcd(zapisyRok);
    }
    
    public void initcd(List<StronaWiersza> zapisyRok) { //E.m(this);
        List<Konto> kontaklienta = kontoDAOfk.findKontaOstAlitykaWynikowe(wpisView);
        List<Konto> kontaklientaprzychody = Collections.synchronizedList(new ArrayList<>());
        List<Konto> kontaklientakoszty = Collections.synchronizedList(new ArrayList<>());
        for (Konto p : kontaklienta) {
            if (p.getPelnynumer().equals("490")||p.getPelnynumer().equals("791")) {
                kontaklientakoszty.add(p);
            } else if (p.getZwyklerozrachszczegolne().equals("szczególne")) {
                kontaklientakoszty.add(p);
                kontaklientaprzychody.add(p);
            } else if (p.isPrzychod0koszt1()) {
                kontaklientakoszty.add(p);
            } else {
                kontaklientaprzychody.add(p);
            }
        }
        pobranewalutypodatnik = pobierzswaluty(zapisyRok);
        dokumentyzodliczonymvat = new ArrayList<>();
        sumaprzychody = 0.0;
        sumavatkoszty = 0.0;
        listakontaprzychody = przygotowanalistasaldR(zapisyRok, kontaklientaprzychody, 0);
        listakontakoszty = przygotowanalistasaldR(zapisyRok, kontaklientakoszty, 1);
        pobranecechypodatnik = cechazapisuDAOfk.findPodatnik(wpisView.getPodatnikObiekt());
        pobierzzapisyzcechami();
        obliczsymulacje();
        obliczsymulacjeNowa();
//        pozycjeDoWyplatyNarastajaco = symulacjaWynikuNarastajacoView.danedobiezacejsym();
//        obliczkwotydowyplaty();
        tylkokontasyntetyczneoblicz();
        sumazapisowPrzychody1();
        sumazapisowKoszty1();
        Collections.sort(listakontaprzychody, new SaldoKontocomparator());
        Collections.sort(listakontakoszty, new SaldoKontocomparator());
    }

    public void odswiezsymulacjewynikuanalityczne() {
        Integer mcod = Integer.parseInt(this.mcod);
        Integer mcdo = Integer.parseInt(this.mcdo);
        if (mcdo<mcod) {
            Msg.msg("e","Miesiąc do jest późniejszy od miesiąca od");
        } else {
            wpisView.setMiesiacWpisu(this.mcdo);
            wpisView.wpisAktualizuj();
            init();
        }
    }
    
     public void odswiezsymulacjewynikuanalityczneksiegowa() {
        Integer mcod = Integer.parseInt(this.mcod);
        Integer mcdo = Integer.parseInt(this.mcdo);
        if (mcdo<mcod) {
            Msg.msg("e","Miesiąc do jest późniejszy od miesiąca od");
        } else {
            wpisView.setMiesiacWpisu(this.mcdo);
            wpisView.wpisAktualizuj();
            initksiegowa();
        }
    }

    
     private List<SaldoKonto> przygotowanalistasaldR(List<StronaWiersza> zapisyRok, List<Konto> kontaklienta, int przychod0koszt1) {
        List<SaldoKonto> przygotowanalista = Collections.synchronizedList(new ArrayList<>());
        CechazapisuBean.luskaniezapisowZCechami(wybranacechadok, zapisyRok);
        for (Konto p : kontaklienta) {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            naniesZapisyNaKontoR(saldoKonto, p, zapisyRok, przychod0koszt1);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalista, przychod0koszt1);
        }
        for (int i = 1; i < przygotowanalista.size() + 1; i++) {
            przygotowanalista.get(i - 1).setId(i);
        }
        sumaSaldoKontoPrzychody = Collections.synchronizedList(new ArrayList<>());
        sumaSaldoKontoPrzychody.add(KontaFKBean.sumujsaldakont(przygotowanalista));
        return przygotowanalista;
    }
    

    private void naniesZapisyNaKontoR(SaldoKonto saldoKonto, Konto p, List<StronaWiersza> zapisyRok, int przychod0koszt1) {
        double sumaWn = 0.0;
        double sumaMa = 0.0;
        double sumaWnPodatki = 0.0;
        double sumaMaPodatki = 0.0;
        double vat = 0.0;
        for (StronaWiersza r : zapisyRok) {
            if (r.getKonto().equals(p)) {
                double[] wyniksumowania = nanieszapisysuma(wybranawaluta, r, przychod0koszt1);
                if (r.isTylkopodatkowo()) {
                    sumaWnPodatki = sumaWnPodatki+wyniksumowania[0];
                    sumaMaPodatki = sumaMaPodatki+wyniksumowania[1];
                } else {
                    sumaWn = sumaWn+wyniksumowania[0];
                    sumaMa = sumaMa+wyniksumowania[1];
                }
                saldoKonto.getZapisy().add(r);
                Dokfk dok = r.getDokfk();
                if (!saldoKonto.getKonto().getPelnynumer().equals("404-2")&&!dokumentyzodliczonymvat.contains(dok)) {
                    vat +=  dok.getVATVAT();
                    dokumentyzodliczonymvat.add(dok);
                }
            }
        }
        saldoKonto.setVat(vat);
        saldoKonto.setObrotyWn(sumaWn);
        saldoKonto.setObrotyMa(sumaMa);
        saldoKonto.setObrotyWnPodatki(sumaWnPodatki);
        saldoKonto.setObrotyMaPodatki(sumaMaPodatki);
    }
    
    private double[] nanieszapisysuma(String wybranawaluta, StronaWiersza r, int przychod0koszt1) {
        double[] zwrot = new double[2];
        double sumaWn = 0.0;
        double sumaMa = 0.0;
        if (wybranawaluta == null) {
            if (r.getKonto().getZwyklerozrachszczegolne().equals("szczególne")) {
                if (r.getWnma().equals("Wn") && przychod0koszt1 == 1) {
                    sumaWn += r.getKwotaPLN();
                } else if (r.getWnma().equals("Wn") && przychod0koszt1 == 0 && r.getKwotaPLN() < 0.0) {
                    sumaMa -= r.getKwotaPLN();
                } else if (r.getWnma().equals("Ma") && przychod0koszt1 == 0) {
                    sumaMa += r.getKwotaPLN();
                } else if (r.getWnma().equals("Ma") && przychod0koszt1 == 1 && r.getKwotaPLN() < 0.0) {
                    sumaWn -= r.getKwotaPLN();
                }
            } else {
                if (r.getWnma().equals("Wn")) {
                    sumaWn += r.getKwotaPLN();
                } else {
                    sumaMa += r.getKwotaPLN();
                }
            }

        } else {
            if (r.getSymbolWalutBOiSW().equals(wybranawaluta)) {
                if (r.getKonto().getZwyklerozrachszczegolne().equals("szczególne")) {
                    if (r.getWnma().equals("Wn") && przychod0koszt1 == 1) {
                        sumaWn += r.getKwota();
                    } else if (r.getWnma().equals("Wn") && przychod0koszt1 == 0 && r.getKwota() < 0.0) {
                        sumaMa -= r.getKwota();
                    } else if (r.getWnma().equals("Ma") && przychod0koszt1 == 0) {
                        sumaMa += r.getKwota();
                    } else if (r.getWnma().equals("Ma") && przychod0koszt1 == 1 && r.getKwota() < 0.0) {
                        sumaWn -= r.getKwota();
                    }
                } else {
                    if (r.getWnma().equals("Wn")) {
                        sumaWn += r.getKwota();
                    } else {
                        sumaMa += r.getKwota();
                    }
                }
            }
        }
        zwrot[0] = sumaWn;
        zwrot[1] = sumaMa;
        return zwrot;
    }
    
    

    private void dodajdolisty(SaldoKonto saldoKonto, List<SaldoKonto> przygotowanalista, int przychod0koszt1) {
        boolean kontoszczegolne = saldoKonto.getKonto().getZwyklerozrachszczegolne().equals("szczególne");
        if (kontoszczegolne) {
            if ((saldoKonto.getSaldoWn() != 0.0 || saldoKonto.getSaldoWnPodatki() !=0.0) && przychod0koszt1 == 1) {
                przygotowanalista.add(saldoKonto);
                return;
            }
            if ((saldoKonto.getSaldoMa() != 0.0 || saldoKonto.getSaldoMaPodatki() !=0.0) && przychod0koszt1 == 0) {
                przygotowanalista.add(saldoKonto);
                return;
            }
        } else {
            if (saldoKonto.getObrotyBoWn() != 0.0 || saldoKonto.getObrotyWnPodatki() !=0.0 || saldoKonto.getBoWn() != 0.0) {
                przygotowanalista.add(saldoKonto);
                return;
            }
            if (saldoKonto.getObrotyBoMa() != 0.0 || saldoKonto.getObrotyMaPodatki() !=0.0  || saldoKonto.getBoMa() != 0.0) {
                przygotowanalista.add(saldoKonto);
                return;
            }
        }
    }

   
    private List<StronaWiersza> pobierzzapisyRokMc(String mcod, String mcdo) {
        List<StronaWiersza> zapisywynikrokmc = new ArrayList<>();
        if (wpisView.getPodatnikObiekt().isMetodakasowapit()) {
            zapisywynikrokmc.addAll(przetworzRozliczenia(wpisView.getPodatnikObiekt(), "", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
            zapisywynikrokmc.addAll(stronaWierszaDAO.findStronaByPodatnikRokMcWynikCIT(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()).stream().filter(p->p.getDokfk().getRodzajedok().getKategoriadokumentu()==0||p.getDokfk().getRodzajedok().getKategoriadokumentu()==5).collect(Collectors.toList()));
        } else {
            zapisywynikrokmc = stronaWierszaDAO.findStronaByPodatnikRokMcodMcdoWynikCIT(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), mcod, mcdo);
        }
        return zapisywynikrokmc;
    }
    
    private List<StronaWiersza> pobierzzapisyRokMcKsiegowa(String bilansoddnia, String bilansnadzien) {
        List<StronaWiersza> zapisywynikrokmc = new ArrayList<>();
        if (wpisView.getPodatnikObiekt().isMetodakasowapit()) {
            zapisywynikrokmc.addAll(przetworzRozliczenia(wpisView.getPodatnikObiekt(), "", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
            zapisywynikrokmc.addAll(stronaWierszaDAO.findStronaByPodatnikRokMcWynikCIT(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()).stream().filter(p->p.getDokfk().getRodzajedok().getKategoriadokumentu()==0||p.getDokfk().getRodzajedok().getKategoriadokumentu()==5).collect(Collectors.toList()));
        } else {
            zapisywynikrokmc = stronaWierszaDAO.findStronaByPodatnikRokDzienOdDziendoWynikCIT(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), bilansoddnia, bilansnadzien);
        }
        return zapisywynikrokmc;
    }
    
    private List<StronaWiersza> przetworzRozliczenia(Podatnik podatnik, String vatokres, String rokWpisuSt, String miesiacWpisu) {
        List<StronaWiersza> zwrot = new ArrayList<>();
        List<Transakcja> transakcje = transakcjaDAO.findPodatnikRokMcRozliczajacy(podatnik, rokWpisuSt, miesiacWpisu);
        zwrot.addAll(stworzevatwpisRozl(transakcje));
        error.E.s("");
        return zwrot;
    }
    
     private Collection<? extends StronaWiersza> stworzevatwpisRozl(List<Transakcja> lista) {
        List<StronaWiersza> zwrot = new ArrayList<>();
        for (Transakcja p : lista) {
            if (p.getNowaTransakcja().getDokfk().getEwidencjaVAT()!=null&&p.getNowaTransakcja().getDokfk().getEwidencjaVAT().size()>0) {
                List<StronaWiersza> zwrotw = naniesPlatnoscRozl(p);
                if (zwrotw != null) {
                    for (StronaWiersza t : zwrotw) {
                        if (!zwrot.contains(t)) {
                            zwrot.add(t);
                        }
                    }
                }
            }
        }
        for (StronaWiersza r : zwrot) {
            rozliczPlatnoscRozl(r);
        }
        return zwrot;
    }
    
    private List<StronaWiersza> naniesPlatnoscRozl(Transakcja p) {
        Dokfk dok = p.getNowaTransakcja().getDokfk();
        List<StronaWiersza> zwrot = dok.getStronyWierszy();
        for (StronaWiersza s : zwrot) {
            s.setSumatransakcji(Z.z(s.getSumatransakcji()+p.getKwotatransakcji()));
        }
        return zwrot;
    }
    
    private void rozliczPlatnoscRozl(StronaWiersza p) {
        Dokfk dok = p.getDokfk();
        double rozliczono =0.0;
        rozliczono = Z.z(p.getSumatransakcji());
        double zostalo = Z.z(dok.getWartoscdokumentu()-rozliczono);
        if (zostalo>=0.0) {
            double procent = Z.z6(p.getSumatransakcji()/dok.getWartoscdokumentu());
            p.setKwota(Z.z(p.getKwota()*procent));
            p.setKwotaPLN(Z.z(p.getKwotaPLN()*procent));
        }
    }

    private double sumuj(List<SaldoKonto> listakonta, String przychodykoszty, int podatki1finanse0razem3) {
        double suma = 0.0;
        if (przychodykoszty.equals("przychody")) {
            for (SaldoKonto p : listakonta) {
                if (podatki1finanse0razem3==0) {
                    suma += (p.getSaldoMa() - p.getSaldoWn());
                } else {
                    suma += (p.getSaldoMaPodatki() - p.getSaldoWnPodatki());
                }
            }
        }
        if (przychodykoszty.equals("koszty")) {
            for (SaldoKonto p : listakonta) {
                if (podatki1finanse0razem3==0) {
                    suma += (p.getSaldoWn() - p.getSaldoMa());
                } else {
                    suma += (p.getSaldoWnPodatki() - p.getSaldoMaPodatki());
                }
            }
        }
        return suma;
    }

    private void obliczsymulacje() {
        pozycjePodsumowaniaWyniku = Collections.synchronizedList(new ArrayList<>());
        double przychody = Z.z(sumuj(listakontaprzychody, "przychody",0));
        przychody += Z.z(sumuj(listakontaprzychody, "przychody",1));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("przychodyrazem"), przychody));
        double koszty = Z.z(sumuj(listakontakoszty, "koszty",0));
        koszty += Z.z(sumuj(listakontakoszty, "koszty",1));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("kosztyrazem"), koszty));
        wynikfinansowy = Z.z(przychody - koszty);
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("wynikfinansowy"), wynikfinansowy));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("nkup"), Z.z(nkup)));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji("kupmn", Z.z(kupmn_mc)));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji("kupmn pop mce", Z.z(kupmn_mc_pop)));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("npup"),Z.z(npup)));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji("pmn",Z.z(pmn_mc)));
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji("pmn pop mce",Z.z(pmn_mc_pop)));
        double wynikpodatkowy = Z.z(wynikfinansowy + nkup + kupmn_mc + kupmn_mc_pop + npup + pmn_mc + pmn_mc_pop);
        pozycjePodsumowaniaWyniku.add(new PozycjeSymulacji(B.b("wynikpodatkowy"), wynikpodatkowy));
    }
    
    private void obliczsymulacjeNowa() {
        pozycjePodsumowaniaWynikuNowe = Collections.synchronizedList(new ArrayList<>());
        double przychody = Z.z(sumuj(listakontaprzychody, "przychody",0));
        double koszty = Z.z(sumuj(listakontakoszty, "koszty",0));
        double wynik = Z.z(przychody - koszty);
        double przychodyPodatkowe = Z.z(sumuj(listakontaprzychody, "przychody",1));
        double kosztyPodatkowe = Z.z(sumuj(listakontakoszty, "koszty",1));
        double wynikPodatkowyWstepny = Z.z(przychodyPodatkowe - kosztyPodatkowe);
        //takbylo i dublowal przychody miesiac nastepny
        //double wynikpodatkowy = Z.z(wynikfinansowy +wynikPodatkowyWstepny + nkup + kupmn_mc + kupmn_mc_pop + npup + pmn_mc + pmn_mc_pop);
        double wynikpodatkowy = Z.z(wynikfinansowy +wynikPodatkowyWstepny + nkup + kupmn_mc + kupmn_mc_pop + npup + pmn_mc + pmn_mc_pop);
        double udzial = 1;
        String kto = B.b("wszyscy");
        int id = 1;
        pozycjePodsumowaniaWynikuNowe.add(obliczpojedyncza(id++, przychody, koszty, wynik, przychodyPodatkowe, kosztyPodatkowe, wynikPodatkowyWstepny, wynikpodatkowy, udzial, kto));
        List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnikBiezace(wpisView);
        for (PodatnikUdzialy p : udzialy) {
            double udział = Z.z4(Double.parseDouble(p.getUdzial())/100);
            pozycjePodsumowaniaWynikuNowe.add(obliczpojedyncza(id++, przychody, koszty, wynik, przychodyPodatkowe, kosztyPodatkowe, wynikPodatkowyWstepny,wynikpodatkowy, udział, p.getNazwiskoimie()));
        }
    }
    
    private PozycjeSymulacjiNowe obliczpojedyncza(int id, double przychody, double koszty, double wynik, double przychodyPodatkowe, double kosztyPodatkowe, double wynikPodatkowyWstepny, double wynikpodatkowy, double udzial, String kto) {
        PozycjeSymulacjiNowe p = new PozycjeSymulacjiNowe();
        p.setId(id);
        p.setPrzychody(Z.z(przychody*udzial));
        p.setKoszty(Z.z(koszty*udzial));
        p.setWynikfinansowy(Z.z(wynik*udzial));
        p.setPrzychodyPodatkowe(Z.z(przychodyPodatkowe*udzial));
        p.setKosztyPodatkowe(Z.z(kosztyPodatkowe*udzial));
        p.setWynikPodatkowyWstępny(Z.z(wynikPodatkowyWstepny*udzial));
        p.setNkup(Z.z(nkup*udzial));
        p.setKupmn(Z.z(kupmn_mc*udzial));
        p.setKupmn_poprzedniemce(Z.z(kupmn_mc_pop*udzial));
        p.setNpup(Z.z(npup*udzial));
        p.setPmn(Z.z(pmn_mc*udzial));
        p.setPmn_poprzedniemce(Z.z(pmn_mc_pop*udzial));
        p.setWynikpodatkowy(Z.z(wynikpodatkowy*udzial));
        p.setPodatnik(wpisView.getPodatnikObiekt());
        p.setRok(wpisView.getRokWpisuSt());
        p.setMc(wpisView.getMiesiacWpisu());
        p.setUdzialowiec(kto);
        p.setUdzial(udzial);
        return p;
    }
    
    
    
    public void sumazapisowPrzychody() {
        sumaprzychody = 0.0;
        sumavatprzychody = 0.0;
        for (SaldoKonto p : sumaSaldoKontoPrzychody) {
            sumaprzychody += p.getSaldoMa();
            sumaprzychody -= p.getSaldoWn();
            sumavatprzychody += p.getVat();
        }
    }
    
     public void sumazapisowPrzychody1() {
        sumaprzychody = 0.0;
        sumavatprzychody = 0.0;
        for (SaldoKonto p : listakontaprzychody) {
            sumaprzychody += p.getSaldoMa();
            sumaprzychody -= p.getSaldoWn();
            sumavatprzychody += p.getVat();
        }
    }
    
    public void sumazapisowKoszty() {
        sumakoszty = 0.0;
        sumavatkoszty = 0.0;
        for (SaldoKonto p : sumaSaldoKontoKoszty) {
            sumakoszty += p.getSaldoWn();
            sumakoszty -= p.getSaldoMa();
            sumavatkoszty += p.getVat();
        }
    }
    
    public void sumazapisowKoszty1() {
        sumakoszty = 0.0;
        sumavatkoszty = 0.0;
        for (SaldoKonto p : listakontakoszty) {
            sumakoszty += p.getSaldoWn();
            sumakoszty -= p.getSaldoMa();
            sumavatkoszty += p.getVat();
        }
    }
    
    public void drukuj(int i) {
        PdfSymulacjaWyniku.drukuj(listakontaprzychody, listakontakoszty, pozycjePodsumowaniaWynikuNowe, pozycjeObliczeniaPodatku, wpisView, i, pozycjeDoWyplaty, sumaprzychody, sumavatprzychody, sumakoszty, sumavatkoszty, mcod, mcdo);
    }

    private void pobierzzapisyzcechami() {
        zapisyZCecha = Collections.synchronizedList(new ArrayList<>());
        zapisyZCechaP = Collections.synchronizedList(new ArrayList<>());
        //pobieram wszystkie strony wiersza z roku
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikoweCecha(stronaWierszaDAO, wpisView);
        //pobieram strony wiersza z cecha i wyluskuje strony wiersza z dokumentu z cecha
        List<StronaWiersza> zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "NKUP", wpisView.getMiesiacWpisu());
        for (StronaWiersza stw : zapisycechakoszt) {
            for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCecha.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw, false, false));
                }
        }
        //sumuje
        nkup = CechazapisuBean.sumujcecha(zapisycechakoszt, "NKUP", wpisView.getMiesiacWpisu());
        zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "KUPMN", wpisView.getMiesiacWpisu());
        for (StronaWiersza stw : zapisycechakoszt) {
            for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCecha.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw, false, false));
                }
        }
        kupmn_mc = CechazapisuBean.sumujcecha(zapisycechakoszt, "KUPMN", wpisView.getMiesiacWpisu());
        kupmn_mc_pop = 0.0;
            zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "KUPMN", wpisView.getMiesiacUprzedni());
            //;UWAGA TU JEST TEN MINUS :)
            kupmn_mc_pop = -CechazapisuBean.sumujcecha(zapisycechakoszt, "KUPMN", wpisView.getMiesiacUprzedni());
            for (StronaWiersza stw : zapisycechakoszt) {
                for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCecha.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw, "popmc"));
                }
            }
        List<StronaWiersza> zapisycechaprzychod = CechazapisuBean.pobierzwierszezcecha(zapisy, "NPUP", wpisView.getMiesiacWpisu());
        for (StronaWiersza stw : zapisycechaprzychod) {
            for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCechaP.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw, false, false));
                }
        }
        npup = CechazapisuBean.sumujcecha(zapisycechaprzychod, "NPUP", wpisView.getMiesiacWpisu());
        zapisycechaprzychod = CechazapisuBean.pobierzwierszezcecha(zapisy, "PMN", wpisView.getMiesiacWpisu());
        for (StronaWiersza stw : zapisycechaprzychod) {
            for (Cechazapisu s : stw.getCechazapisuLista()) {
                    zapisyZCechaP.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw, false, false));
                }
        }
        pmn_mc = CechazapisuBean.sumujcecha(zapisycechaprzychod, "PMN", wpisView.getMiesiacWpisu());
        pmn_mc_pop = 0.0;
        zapisycechaprzychod = CechazapisuBean.pobierzwierszezcecha(zapisy, "PMN", wpisView.getMiesiacUprzedni());
        //tu tez wstawilem ten minus 19.06.2023
        pmn_mc_pop = -CechazapisuBean.sumujcecha(zapisycechaprzychod, "PMN", wpisView.getMiesiacUprzedni());
        for (StronaWiersza stw : zapisycechaprzychod) {
            for (Cechazapisu s : stw.getCechazapisuLista()) {
                zapisyZCechaP.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, stw, "popmc"));
            }
        }
    }
    
    public void zaksiegujwynik () {
        if (this.mcod.equals(this.mcdo)) {
            double wynikfinnarastajaco = 0.0;
            double wynikpodatkowynarastajaco = 0.0;
            List<WynikFKRokMc> wynikpoprzedniemce = wynikFKRokMcDAO.findWynikFKPodatnikRokFirma(wpisView);
            double podatekzaplacony = 0.0;
            for (WynikFKRokMc p : wynikpoprzedniemce) {
                if (Mce.getMiesiacToNumber().get(p.getMc()) < Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu())) {
                    wynikfinnarastajaco += p.getWynikfinansowy();
                    podatekzaplacony += p.getPodatekdowplaty();
                }
            }
            PozycjeSymulacjiNowe wszyscy = pozycjePodsumowaniaWynikuNowe.get(0);
            WynikFKRokMc wynikFKRokMc = new WynikFKRokMc();
            wynikFKRokMc.setPodatnikObj(wpisView.getPodatnikObiekt());
            wynikFKRokMc.setRok(wpisView.getRokWpisuSt());
            wynikFKRokMc.setMc(wpisView.getMiesiacWpisu());
            wynikFKRokMc.setDataod(bilansoddnia);
            wynikFKRokMc.setDatado(bilansnadzien);
            wynikFKRokMc.setPrzychody(wszyscy.getPrzychody());
            wynikFKRokMc.setKoszty(wszyscy.getKoszty());
            wynikFKRokMc.setWynikfinansowy(wszyscy.getWynikfinansowy());
            wynikFKRokMc.setPrzychodyPodatkoweKonta(wszyscy.getPrzychodyPodatkowe());
            wynikFKRokMc.setKosztyPodatkoweKonta(wszyscy.getKosztyPodatkowe());
            wynikFKRokMc.setWynikPodatkowyWstepny(wszyscy.getWynikPodatkowyWstępny());
            wynikfinnarastajaco += wynikFKRokMc.getWynikfinansowy();
            wynikFKRokMc.setWynikfinansowynarastajaco(wynikfinnarastajaco);
            wynikFKRokMc.setNkup(wszyscy.getNkup()+wszyscy.getKupmn()+wszyscy.getKupmn_poprzedniemce());
            wynikFKRokMc.setNpup(wszyscy.getNpup()+wszyscy.getPmn()+wszyscy.getPmn_poprzedniemce());
            wynikFKRokMc.setWynikpodatkowy(wynikFKRokMc.getWynikfinansowy()+wynikFKRokMc.getWynikPodatkowyWstepny()+wynikFKRokMc.getNkup()+wynikFKRokMc.getNpup());
            wynikpodatkowynarastajaco += wynikFKRokMc.getWynikPodatkowyWstepny();
            wynikFKRokMc.setWynikPodatkowyWstepnyNarastajaco(wynikpodatkowynarastajaco);
            wynikFKRokMc.setUdzialowiec("firma");
    //        if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_Z_O_O)) {
                wynikFKRokMc.setPodatek(wszyscy.getPmn());
                wynikFKRokMc.setWynikfinansowynetto(Z.z(wszyscy.getWynikfinansowy()-wszyscy.getPmn()));
                wynikFKRokMc.setPodatekzaplacono(podatekzaplacony);
                double dowplaty = wszyscy.getPmn()-podatekzaplacony >0.0?wszyscy.getPmn()-podatekzaplacony:0.0;
                wynikFKRokMc.setPodatekdowplaty(dowplaty);
    //        }
            wynikFKRokMc.setWprowadzil(wpisView.getUzer().getLogin());
            wynikFKRokMc.setData(new Date());
            //wywalilem bo ozajmuje za duzo miejsca
    //        wynikFKRokMc.setListaprzychody(listakontaprzychody);
    //        wynikFKRokMc.setListakoszty(listakontakoszty);
            try {
                WynikFKRokMc pobrany = wynikFKRokMcDAO.findWynikFKRokMcFirma(wynikFKRokMc);
                if (pobrany!=null) {
                    wynikFKRokMcDAO.remove(pobrany);
                }
            } catch (Exception e) {  
                E.e(e);
            }
            try {
                wynikFKRokMcDAO.create(wynikFKRokMc);
                symulacjaWynikuNarastajacoView.init();
                Msg.msg("Zachowano wynik");
            } catch (Exception e) {  E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie zachowano wyniku.");
            }
        } else {
            Msg.msg("e", "Miesiąc od jest inny o miesiąca do. Nie można zaksięgować");
        }
    }
    
    public void zaksiegujwynikKsiegowa () {
        if (Data.getMc(bilansoddnia).equals(Data.getMc(bilansnadzien))) {
            double wynikfinnarastajaco = 0.0;
            double wynikpodatkowynarastajaco = 0.0;
            List<WynikFKRokMc> wynikpoprzedniemce = wynikFKRokMcDAO.findWynikFKPodatnikRokFirma(wpisView);
            double podatekzaplacony = 0.0;
            for (WynikFKRokMc p : wynikpoprzedniemce) {
                if (Mce.getMiesiacToNumber().get(p.getMc()) < Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu())) {
                    wynikfinnarastajaco += p.getWynikfinansowy();
                    podatekzaplacony += p.getPodatekdowplaty();
                }
            }
            PozycjeSymulacjiNowe wszyscy = pozycjePodsumowaniaWynikuNowe.get(0);
            WynikFKRokMc wynikFKRokMc = new WynikFKRokMc();
            wynikFKRokMc.setPodatnikObj(wpisView.getPodatnikObiekt());
            wynikFKRokMc.setRok(Data.getRok(bilansnadzien));
            wynikFKRokMc.setMc(Data.getMc(bilansnadzien));
            wynikFKRokMc.setDataod(bilansoddnia);
            wynikFKRokMc.setDatado(bilansnadzien);
            wynikFKRokMc.setPrzychody(wszyscy.getPrzychody());
            wynikFKRokMc.setKoszty(wszyscy.getKoszty());
            wynikFKRokMc.setWynikfinansowy(wszyscy.getWynikfinansowy());
            wynikFKRokMc.setPrzychodyPodatkoweKonta(wszyscy.getPrzychodyPodatkowe());
            wynikFKRokMc.setKosztyPodatkoweKonta(wszyscy.getKosztyPodatkowe());
            wynikFKRokMc.setWynikPodatkowyWstepny(wszyscy.getWynikPodatkowyWstępny());
            wynikfinnarastajaco += wynikFKRokMc.getWynikfinansowy();
            wynikFKRokMc.setWynikfinansowynarastajaco(wynikfinnarastajaco);
            wynikFKRokMc.setNkup(wszyscy.getNkup()+wszyscy.getKupmn()+wszyscy.getKupmn_poprzedniemce());
            wynikFKRokMc.setNpup(wszyscy.getNpup()+wszyscy.getPmn()+wszyscy.getPmn_poprzedniemce());
            wynikFKRokMc.setWynikpodatkowy(wynikFKRokMc.getWynikfinansowy()+wynikFKRokMc.getWynikPodatkowyWstepny()+wynikFKRokMc.getNkup()+wynikFKRokMc.getNpup());
            wynikpodatkowynarastajaco += wynikFKRokMc.getWynikPodatkowyWstepny();
            wynikFKRokMc.setWynikPodatkowyWstepnyNarastajaco(wynikpodatkowynarastajaco);
            wynikFKRokMc.setUdzialowiec("firma");
    //        if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_Z_O_O)) {
                wynikFKRokMc.setPodatek(wszyscy.getPmn());
                wynikFKRokMc.setWynikfinansowynetto(Z.z(wszyscy.getWynikfinansowy()-wszyscy.getPmn()));
                wynikFKRokMc.setPodatekzaplacono(podatekzaplacony);
                double dowplaty = wszyscy.getPmn()-podatekzaplacony >0.0?wszyscy.getPmn()-podatekzaplacony:0.0;
                wynikFKRokMc.setPodatekdowplaty(dowplaty);
    //        }
            wynikFKRokMc.setWprowadzil(wpisView.getUzer().getLogin());
            wynikFKRokMc.setData(new Date());
            //wywalilem bo ozajmuje za duzo miejsca
    //        wynikFKRokMc.setListaprzychody(listakontaprzychody);
    //        wynikFKRokMc.setListakoszty(listakontakoszty);
            try {
                WynikFKRokMc pobrany = wynikFKRokMcDAO.findWynikFKRokMcFirma(wynikFKRokMc);
                if (pobrany!=null) {
                    wynikFKRokMcDAO.remove(pobrany);
                }
            } catch (Exception e) {  
                E.e(e);
            }
            try {
                wynikFKRokMcDAO.create(wynikFKRokMc);
                symulacjaWynikuNarastajacoView.init();
                Msg.msg("Zachowano wynik");
            } catch (Exception e) {  E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie zachowano wyniku.");
            }
        } else {
            Msg.msg("e", "Miesiąc od jest inny o miesiąca do. Nie można zaksięgować");
        }
    }
   
    
    public void tylkokontasyntetyczneoblicz() {
        if (tylkokontasyntetyczne) {
            listakontaprzychody = zredukuj(listakontaprzychody);
            listakontakoszty = zredukuj(listakontakoszty);
        }
    }
    
    private List<SaldoKonto> zredukuj(List<SaldoKonto> lista) {
        List<SaldoKonto> macierzyste = Collections.synchronizedList(new ArrayList<>());
        for (SaldoKonto p : lista) {
            SaldoKonto macierzystewiersz = jestmacierzyste(macierzyste,p.getTopKonto());
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
        macierzystewiersz.getZapisy().addAll(p.getZapisy());
    }

    private SaldoKonto stworzmacierzyste(SaldoKonto p) {
        SaldoKonto macierzystewiersz = new SaldoKonto();
        Konto mac = p.getTopKonto();
        macierzystewiersz.setKonto(mac);
        macierzystewiersz.setObrotyWn(macierzystewiersz.getObrotyWn()+p.getObrotyBoWn());
        macierzystewiersz.setObrotyMa(macierzystewiersz.getObrotyMa()+p.getObrotyBoMa());
        macierzystewiersz.setObrotyBoWn(macierzystewiersz.getObrotyBoWn()+p.getObrotyBoWn());
        macierzystewiersz.setObrotyBoMa(macierzystewiersz.getObrotyBoMa()+p.getObrotyBoMa());
        macierzystewiersz.setZapisy(p.getZapisy());
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

    public String getBilansnadzien() {
        return bilansnadzien;
    }

    public void setBilansnadzien(String bilansnadzien) {
        this.bilansnadzien = bilansnadzien;
    }

    public String getBilansoddnia() {
        return bilansoddnia;
    }

    public void setBilansoddnia(String bilansoddnia) {
        this.bilansoddnia = bilansoddnia;
    }

    public String getBilansoddniaRokPop() {
        return bilansoddniaRokPop;
    }

    public void setBilansoddniaRokPop(String bilansoddniaRokPop) {
        this.bilansoddniaRokPop = bilansoddniaRokPop;
    }

    public String getBilansnadzienRokPop() {
        return bilansnadzienRokPop;
    }

    public void setBilansnadzienRokPop(String bilansnadzienRokPop) {
        this.bilansnadzienRokPop = bilansnadzienRokPop;
    }
    

    //<editor-fold defaultstate="collapsed" desc="comment">

    public List<SaldoKonto> getWybraneprzychody() {
        return wybraneprzychody;
    }

    public void setWybraneprzychody(List<SaldoKonto> wybraneprzychody) {
        this.wybraneprzychody = wybraneprzychody;
    }

    public String getMcod() {
        return mcod;
    }

    public void setMcod(String mcod) {
        this.mcod = mcod;
    }

    public String getMcdo() {
        return mcdo;
    }

    public void setMcdo(String mcdo) {
        this.mcdo = mcdo;
    }

    public String getWybranawaluta() {
        return wybranawaluta;
    }

    public void setWybranawaluta(String wybranawaluta) {
        this.wybranawaluta = wybranawaluta;
    }

    public List<String> getPobranewalutypodatnik() {
        return pobranewalutypodatnik;
    }

    public void setPobranewalutypodatnik(List<String> pobranewalutypodatnik) {
        this.pobranewalutypodatnik = pobranewalutypodatnik;
    }

    public List<CechyzapisuPrzegladView.CechaStronaWiersza> getZapisyZCechaP() {
        return zapisyZCechaP;
    }

    public void setZapisyZCechaP(List<CechyzapisuPrzegladView.CechaStronaWiersza> zapisyZCechaP) {
        this.zapisyZCechaP = zapisyZCechaP;
    }

    public List<CechyzapisuPrzegladView.CechaStronaWiersza> getZapisyZCechafilteredP() {
        return zapisyZCechafilteredP;
    }

    public void setZapisyZCechafilteredP(List<CechyzapisuPrzegladView.CechaStronaWiersza> zapisyZCechafilteredP) {
        this.zapisyZCechafilteredP = zapisyZCechafilteredP;
    }

    public double getNkup() {
        return nkup;
    }

    public void setNkup(double nkup) {
        this.nkup = nkup;
    }

    public double getNpup() {
        return npup;
    }

    public void setNpup(double npup) {
        this.npup = npup;
    }

    public List<PozycjeSymulacjiNowe> getPozycjePodsumowaniaWynikuNowe() {
        return pozycjePodsumowaniaWynikuNowe;
    }

    public void setPozycjePodsumowaniaWynikuNowe(List<PozycjeSymulacjiNowe> pozycjePodsumowaniaWynikuNowe) {
        this.pozycjePodsumowaniaWynikuNowe = pozycjePodsumowaniaWynikuNowe;
    }

    public String getWybranacechadok() {
        return wybranacechadok;
    }

    public void setWybranacechadok(String wybranacechadok) {
        this.wybranacechadok = wybranacechadok;
    }

    public List<Cechazapisu> getPobranecechypodatnik() {
        return pobranecechypodatnik;
    }

    public void setPobranecechypodatnik(List<Cechazapisu> pobranecechypodatnik) {
        this.pobranecechypodatnik = pobranecechypodatnik;
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

    public double getKupmn_mc() {
        return kupmn_mc;
    }

    public void setKupmn_mc(double kupmn_mc) {
        this.kupmn_mc = kupmn_mc;
    }

    public double getPmn_mc() {
        return pmn_mc;
    }

    public void setPmn_mc(double pmn_mc) {
        this.pmn_mc = pmn_mc;
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

    public List<CechyzapisuPrzegladView.CechaStronaWiersza> getZapisyZCechafiltered() {
        return zapisyZCechafiltered;
    }

    public void setZapisyZCechafiltered(List<CechyzapisuPrzegladView.CechaStronaWiersza> zapisyZCechafiltered) {
        this.zapisyZCechafiltered = zapisyZCechafiltered;
    }

    private List<String> pobierzswaluty(List<StronaWiersza> zapisyRok) {
        Set<String> walutylista = new HashSet<>();
        if (zapisyRok!=null) {
            for (StronaWiersza p : zapisyRok) {
                walutylista.add(p.getSymbolWalutBOiSW());
            }
        }
        List<String> zwrot = new ArrayList<>(walutylista);
        Collections.sort(zwrot);
        return zwrot;
    }

    public double getKupmn_mc_pop() {
        return kupmn_mc_pop;
    }

    public void setKupmn_mc_pop(double kupmn_mc_pop) {
        this.kupmn_mc_pop = kupmn_mc_pop;
    }

    public double getPmn_mc_pop() {
        return pmn_mc_pop;
    }

    public void setPmn_mc_pop(double pmn_mc_pop) {
        this.pmn_mc_pop = pmn_mc_pop;
    }

    public double getSumavatprzychody() {
        return sumavatprzychody;
    }

    public void setSumavatprzychody(double sumavatprzychody) {
        this.sumavatprzychody = sumavatprzychody;
    }

    public double getSumavatkoszty() {
        return sumavatkoszty;
    }

    public void setSumavatkoszty(double sumavatkoszty) {
        this.sumavatkoszty = sumavatkoszty;
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
        error.E.s(udzial);
        double podstawaopodatkowania = Z.z(udzial*wynikfinansowy);
        error.E.s(podstawaopodatkowania);
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
//            EntityManager em = Em.getEm();
//            Podatnik podatnik = (Podatnik) em.createNamedQuery("Podatnik.findByNip").setParameter("nip", "9552340951").getSingleResult();
//            error.E.s(podatnik.toString());
//            //List<StronaWiersza> pobranezapisy = em.createQuery("SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND (SIZE(t.cechazapisuLista) > 0 OR SIZE(t.wiersz.dokfk.cechadokumentuLista) > 0)").setParameter("podatnikObj", podatnik).getResultList();
//            List<StronaWiersza> pobranezapisy = em.createQuery("SELECT t FROM StronaWiersza  t JOIN t.wiersz.dokfk s WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND (SIZE(t.cechazapisuLista) > 0 OR SIZE(s.cechadokumentuLista) > 0)").setParameter("podatnikObj", podatnik).getResultList();
//            error.E.s("");
              double udział = Z.z4(Double.parseDouble("33.33")/100);
        }
    }
}
