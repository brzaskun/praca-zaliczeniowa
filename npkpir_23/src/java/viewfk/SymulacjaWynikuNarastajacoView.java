/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Stratacomparator;
import dao.PodatnikUdzialyDAO;
import dao.StrataDAO;
import dao.WynikFKRokMcDAO;
import data.Data;
import embeddable.Mce;
import entity.PodatnikUdzialy;
import entity.Strata;
import entity.StrataWykorzystanie;
import entityfk.WynikFKRokMc;
import enumy.FormaPrawna;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.B;
import msg.Msg;
import pdf.PdfSymulacjaWynikuNarastajaco;
import view.StrataView;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class SymulacjaWynikuNarastajacoView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<WynikFKRokMc> listamiesiecy;
    private List<WynikFKRokMc> listamiesiecypoprzednich;
    private WynikFKRokMc sumamiesiecy;
    private WynikFKRokMc sumapoprzednichmiesiecy;
    private List<SymulacjaWynikuView.PozycjeSymulacji> dozaplaty;
    private Map<String, Double> podatnikkwotarazem;
    private List<SymulacjaWynikuView.PozycjeSymulacji> pozycjePodsumowaniaWyniku;
    private List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku;
    private List<WynikFKRokMc> pozycjeObliczeniaPodatkuTabela;
    private List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty;
    private List<SymulacjaWynikuView.PozycjeSymulacjiTabela> pozycjeDoWyplatyTablica;
    private List<Strata> straty;
    private Map<String, Double> zaplaconepodatki;
    private Map<String, Double> wyplaconedywidendy;
    @Inject
    private WynikFKRokMcDAO wynikFKRokMcDAO;
    @Inject
    private StrataDAO strataDAO;
    @Inject
    private WpisView wpisView;
    private double wynikfinansowy;
    private double wynikfinansowynetto;
    private double wynikpodatkowy;
    private double wynikfinansowynettoPopMce;
    private double pdop;
    private double podatekdozaplaty;
    private double zaplacono;
    private double strata;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    private String dodatkoweinfo;

    public SymulacjaWynikuNarastajacoView() {
         ////E.m(this);
        this.listamiesiecy = Collections.synchronizedList(new ArrayList<>());
        this.listamiesiecypoprzednich = Collections.synchronizedList(new ArrayList<>());
        this.dozaplaty = Collections.synchronizedList(new ArrayList<>());
    }
    
    
    @PostConstruct
    public void init() { //E.m(this);
        this.listamiesiecy = Collections.synchronizedList(new ArrayList<>());
        this.listamiesiecypoprzednich = Collections.synchronizedList(new ArrayList<>());
        this.dozaplaty = Collections.synchronizedList(new ArrayList<>());
        List<WynikFKRokMc> listapobrana = wynikFKRokMcDAO.findWynikFKPodatnikRok(wpisView);
        int biezacymc = Integer.parseInt(wpisView.getMiesiacWpisu().equals("CR")?Data.aktualnyMc():wpisView.getMiesiacWpisu());
        for (Iterator<WynikFKRokMc> p = listapobrana.iterator(); p.hasNext(); ) {
            WynikFKRokMc r = (WynikFKRokMc) p.next();
            if (r.getUdzialowiec() == null || r.getUdzialowiec().equals("firma")) {
                int mc = Integer.parseInt(r.getMc());
                if (mc < biezacymc) {
                    listamiesiecy.add(r);
                    listamiesiecypoprzednich.add(r);
                } else if (mc == biezacymc) {
                    listamiesiecy.add(r);
                }
            }
        }
        sumapoprzednichmiesiecy = sumujmiesiace(listamiesiecypoprzednich);
        sumamiesiecy = sumujmiesiace(listamiesiecy);
        obliczsymulacjepoprzedniemce();
        obliczsymulacje();
        obliczkwotydowyplaty();
        sumamiesiecy.setId(11111);
        sumamiesiecy.setMc("R");
        listamiesiecy.add(sumamiesiecy);
    }
    
//    public Map<String, Double> danedobiezacejsym() {
//        this.listamiesiecy = Collections.synchronizedList(new ArrayList<>());
//        this.listamiesiecypoprzednich = Collections.synchronizedList(new ArrayList<>());
//        this.dozaplaty = Collections.synchronizedList(new ArrayList<>());
//        List<WynikFKRokMc> listapobrana = wynikFKRokMcDAO.findWynikFKPodatnikRok(wpisView);
//        int biezacymc = Integer.parseInt(wpisView.getMiesiacUprzedni());
//        for (Iterator<WynikFKRokMc> p = listapobrana.iterator(); p.hasNext(); ) {
//            WynikFKRokMc r = (WynikFKRokMc) p.next();
//            int mc = Integer.parseInt(r.getMc());
//            if (mc < biezacymc) {
//                listamiesiecy.add(r);
//                listamiesiecypoprzednich.add(r);
//            } else if (mc == biezacymc) {
//                listamiesiecy.add(r);
//            }
//        }
//        sumapoprzednichmiesiecy = sumujpoprzedniemiesiace();
//        sumamiesiecy = sumujmiesiace();
//        obliczsymulacjepoprzedniemce();
//        obliczsymulacje();
//        Map<String, Double> pozycjeDoWyplatyExport = new ConcurrentHashMap<>();
//        obliczkwotydowyplaty(pozycjeDoWyplatyExport);
//        return pozycjeDoWyplatyExport;
//    }
    
    private WynikFKRokMc sumujmiesiace(List<WynikFKRokMc> lista) {
        WynikFKRokMc w = new WynikFKRokMc();
        double przychod = 0;
        double koszt = 0;
        double przychodPodatkowy = 0;
        double kosztPodatkowy = 0;
        double npup = 0;
        double nkup = 0;
        double podatekzaplacono = 0;
        for (WynikFKRokMc p : lista) {
            przychod += p.getPrzychody();
            koszt += p.getKoszty();
            przychodPodatkowy += p.getPrzychodyPodatkoweKonta();
            kosztPodatkowy += p.getKosztyPodatkoweKonta();
            npup += p.getNpup();
            nkup += p.getNkup();
            podatekzaplacono += p.getPodatekdowplaty();
        }
        w.setPrzychody(Z.z(przychod));
        w.setKoszty(Z.z(koszt));
        w.setWynikfinansowy(Z.z(przychod-koszt));
        w.setWynikfinansowynetto(Z.z(przychod-koszt-podatekzaplacono));
        w.setPrzychodyPodatkoweKonta(przychodPodatkowy);
        w.setKosztyPodatkoweKonta(kosztPodatkowy);
        w.setWynikPodatkowyWstepny(przychod-koszt+przychodPodatkowy-kosztPodatkowy);
        w.setNpup(npup);
        w.setNkup(nkup);
        w.setPodatekzaplacono(podatekzaplacono);
        w.setWynikpodatkowy(Z.z(przychod-koszt+przychodPodatkowy-kosztPodatkowy+npup+nkup));
        return w;
    }
    
       
    private void obliczsymulacje() {
        dodatkoweinfo = "";
        podatnikkwotarazem = new ConcurrentHashMap<>();
        pozycjePodsumowaniaWyniku = Collections.synchronizedList(new ArrayList<>());
        double przychodynarastajaco = sumamiesiecy.getPrzychody();
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("przychody"), przychodynarastajaco));
        double kosztynarastajaco = sumamiesiecy.getKoszty();
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("koszty"), kosztynarastajaco));
        wynikfinansowy = Z.z(przychodynarastajaco - kosztynarastajaco);
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wynikfinansowy"), wynikfinansowy));
        wynikfinansowy = Z.z(przychodynarastajaco - kosztynarastajaco);
        double przychodypodatkowenarastajaco = sumamiesiecy.getPrzychodyPodatkoweKonta();
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji("przych. pod. konta", przychodypodatkowenarastajaco));
        double npup = sumamiesiecy.getNpup();
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("npup"), npup));
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji("przychód podatkowy po kor.", Z.z(przychodynarastajaco+npup+przychodypodatkowenarastajaco)));
        double kosztypodatkowenarastajaco = sumamiesiecy.getKosztyPodatkoweKonta();
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji("koszty pod. konta", kosztypodatkowenarastajaco));
        double nkup = sumamiesiecy.getNkup();
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("nkup"), nkup));
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji("koszt podatkowy po kor.", Z.z(kosztynarastajaco-nkup+kosztypodatkowenarastajaco)));
        double wynikpodatkowywstepny = Z.z(przychodypodatkowenarastajaco-kosztypodatkowenarastajaco);
        wynikpodatkowy = Z.z(wynikfinansowy + wynikpodatkowywstepny + npup + nkup);
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wynikpodatkowy"), wynikpodatkowy));
        wynikfinansowynetto = wynikpodatkowy;
        straty = strataDAO.findPodatnik(wpisView.getPodatnikObiekt());
        double pozostalodoodliczenia = sumujpozostale(straty, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        strata = pozostalodoodliczenia;
        if (pozostalodoodliczenia>=wynikfinansowynetto) {
            strata = wynikfinansowynetto;
        }
        if (strata>0) {
            strata = naniesrozliczenia(straty,strata);
            strataDAO.editList(straty);
        } else {
            List<Strata> stratypodatnika = strataDAO.findPodatnik(wpisView.getPodatnikObiekt());
            if (stratypodatnika!=null) {
                for (Strata s : stratypodatnika) {
                    for (Iterator<StrataWykorzystanie> it = s.getListawykorzystanie().iterator();it.hasNext();) {
                        StrataWykorzystanie st = it.next();
                        if (st.getRok().equals(wpisView.getRokWpisuSt())&&st.getMc().equals(wpisView.getMiesiacWpisu())) {
                            it.remove();
                            strataDAO.edit(s);
                        }
                    }
                    
                }
            }
        }
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji("strata z lat ub. do rozliczenia", strata));
        boolean formaprawna = true;
        //Usunalem rozroznienie
//        if (wpisView.getPodatnikObiekt().getFormaPrawna() == null) {
//            formaprawna = true;
//        } else if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_KOMANDYTOWA)) {
//            formaprawna = false;
//        }
        if (formaprawna) {
            double wynikdokwietnia = 0.0;
            double podstawaopodatkowania = Z.z0(wynikpodatkowy-strata);
            pdop = 0.0;
            boolean robic = true;
            if (wpisView.getRokWpisu()==2021&&Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu())<5) {
                robic = false;
            } else if (wpisView.getRokWpisu()==2021) {
                WynikFKRokMc styczen = wynikFKRokMcDAO.findWynikFKPodatnikRokUdzialowiec(wpisView.getPodatnikObiekt(), "2021", "01","firma"); 
                WynikFKRokMc luty = wynikFKRokMcDAO.findWynikFKPodatnikRokUdzialowiec(wpisView.getPodatnikObiekt(), "2021", "02","firma"); 
                WynikFKRokMc marzec = wynikFKRokMcDAO.findWynikFKPodatnikRokUdzialowiec(wpisView.getPodatnikObiekt(), "2021", "03","firma"); 
                WynikFKRokMc kwiecien = wynikFKRokMcDAO.findWynikFKPodatnikRokUdzialowiec(wpisView.getPodatnikObiekt(), "2021", "04","firma"); 
                double styczend  = styczen!=null?styczen.getWynikpodatkowy():0.0;
                double lutyd  = luty!=null?luty.getWynikpodatkowy():0.0;
                double marzecd  = marzec!=null?marzec.getWynikpodatkowy():0.0;
                double kwieciend  = kwiecien!=null?kwiecien.getWynikpodatkowy():0.0;
                wynikdokwietnia = styczend+lutyd+marzecd+kwieciend;
            } else if (wpisView.isMc0kw1()) {
                robic = false;
                int nrmca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
                int reszta = nrmca % 3;
                if (reszta==0) {
                    robic = true;
                }
            } 
            if (podstawaopodatkowania > 0 && robic) {
                if (wpisView.getRokWpisu()==2021 && wynikdokwietnia!=0.0) {
                    pdop = Z.z0((podstawaopodatkowania-wynikdokwietnia)*wpisView.getStawkapodatkuospr());
                    dodatkoweinfo = "Podstawę podatku obniżono o dochód podatkowy do 04/2021";
                    if (pdop<0.0) {
                        pdop = 0.0;
                    }
                } else {
                    pdop = Z.z0(podstawaopodatkowania*wpisView.getStawkapodatkuospr());
                }
            }
            pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji("podstawa opododatkowania", podstawaopodatkowania));
            pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("pdop"), pdop));
            zaplacono = sumapoprzednichmiesiecy.getPodatekzaplacono();
            pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("zapłacono"), zaplacono));
            wynikfinansowynetto = wynikfinansowy - pdop; 
            podatekdozaplaty = Z.z(pdop-zaplacono) > 0.0 ? Z.z(pdop-zaplacono) : 0.0;
            pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dozapłaty"), podatekdozaplaty));
            pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wynikfinansowynetto"), wynikfinansowynetto));
        }
        pozycjeObliczeniaPodatku = Collections.synchronizedList(new ArrayList<>());
        try {
            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView.getPodatnikObiekt());
            int i = 1;
            for (PodatnikUdzialy p : udzialy) {
                double udział = Z.z(Double.parseDouble(p.getUdzial())/100);
                double przychodnaudzial = 0.0;
                double kosztnaudzial = 0.0;
                double roznicadlankup = 0.0;
                double roznicadlanpup = 0.0;
                double podstawaopodatkowania = 0.0;
                double wynikfinansowyudzial = 0.0;
                if (p.getDatazakonczenia()==null || (p.getDatazakonczenia()!=null && Data.czyjestpomiedzy(p.getDatarozpoczecia(), p.getDatazakonczenia(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()))) {
                    udział = Z.z(Double.parseDouble(p.getUdzial())/100);
                    pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(p.getNazwiskoimie(), udział));
                    przychodnaudzial = Z.z(udział*przychodynarastajaco);
                    kosztnaudzial = Z.z(udział*kosztynarastajaco);
                    roznicadlankup = Z.z(udział*nkup);
                    roznicadlanpup = Z.z(udział*npup);
                    podstawaopodatkowania = Z.z0(udział*wynikpodatkowy - udział*pdop);
                    wynikfinansowyudzial = Z.z(udział*wynikfinansowy);
                    if (formaprawna) {
                        wynikfinansowyudzial = Z.z(udział*wynikfinansowynetto);
                        podstawaopodatkowania = Z.z(udział*wynikfinansowynetto);
                    }
                } else {
                    pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(p.getNazwiskoimie(), udział));
                }
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wynikfinansowy")+" #"+String.valueOf(i), wynikfinansowyudzial));
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("nkup")+" #"+String.valueOf(i), Z.z(roznicadlankup)));
                error.E.s("roznicadlankup "+roznicadlankup);
                error.E.s("podstawa "+podstawaopodatkowania);
                podstawaopodatkowania = podstawaopodatkowania < 0.0 ? 0.0 : Z.z0(podstawaopodatkowania);
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("podstawaopodatkowania")+" #"+String.valueOf(i), podstawaopodatkowania));
                double podatek = Z.z0(podstawaopodatkowania*0.19);
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("podatekdochodowy")+" #"+String.valueOf(i), podatek));
                double zaplacono = Z.z0(zaplaconepodatki.get(p.getNazwiskoimie())) >= 0 ? Z.z0(zaplaconepodatki.get(p.getNazwiskoimie())) : 0.0;
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("zapłacono")+" #"+String.valueOf(i), -zaplacono));
                double dozaplatypodatek = Z.z0(podatek-zaplacono) > 0.0 ? Z.z0(podatek-zaplacono) : 0.0;
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dozapłaty")+" #"+String.valueOf(i), dozaplatypodatek));
                double dywidendanetto = wynikfinansowyudzial - dozaplatypodatek > 0.0 ? wynikfinansowyudzial - dozaplatypodatek : 0.0;
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dywidendanarastajaco")+" #"+String.valueOf(i++), dywidendanetto));
                podatnikkwotarazem.put(p.getNazwiskoimie(),Z.z0(podatek));
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji("przychod #"+String.valueOf(i++), przychodnaudzial));
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji("koszt #"+String.valueOf(i++), kosztnaudzial));
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("npup")+" #"+String.valueOf(i), Z.z(roznicadlanpup)));
            }
            pozycjeObliczeniaPodatkuTabela = przekonwertujdotabeliPodatek(pozycjeObliczeniaPodatku);
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie określono udziałów w ustawieniach podatnika. Nie można obliczyć podatku");
        }
    }
    
    private double naniesrozliczenia(List<Strata> straty, double rozliczyc) {
        double naniesiono = 0.0;
        Collections.sort(straty, new Stratacomparator());
        for (Strata p : straty) {
            double zostalo = Z.z(p.getKwota()-p.getWykorzystano(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
            if (naniesiono<rozliczyc) {
                StrataWykorzystanie wykorzystanie = p.pobierzWykorzystanie(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                if (zostalo>0.0) {
                    if (zostalo>=rozliczyc) {
                        wykorzystanie.setKwotawykorzystania(rozliczyc);
                        naniesiono = Z.z(naniesiono+rozliczyc);
                    } else {
                        wykorzystanie.setKwotawykorzystania(zostalo);
                        naniesiono = Z.z(naniesiono+zostalo);
                    }
                } else {
                    p.getListawykorzystanie().remove(wykorzystanie);
                    break;
                }
            }
        }
        return naniesiono;
    }

    
    private double sumujpozostale(List<Strata> straty, String rok, String mc) {
        double zwrot = 0.0;
        for (Strata p : straty) {
            double pozostaloglobalnie = p.getWykorzystano(rok, mc);
            double wykorzystaniewroku = p.getSumabiezace(rok);
            if (pozostaloglobalnie>=wykorzystaniewroku) {
                double moznajeszcze = p.getPolowakwoty()-wykorzystaniewroku;
                zwrot = zwrot+moznajeszcze;
            }
        }
        return zwrot;
    }
    
     private void obliczsymulacjepoprzedniemce() {
        zaplaconepodatki = new ConcurrentHashMap<>();
        wyplaconedywidendy = new ConcurrentHashMap<>();
        List<WynikFKRokMc> poprzedniemce = wynikFKRokMcDAO.findWynikFKPodatnikRokUdzialowiec(wpisView);
        List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView.getPodatnikObiekt());
        for (PodatnikUdzialy p : udzialy) {
            double sumazaplaconegopodatku = 0.0;
            double sumawyplaconedywidendy = 0.0;
            for (WynikFKRokMc r : poprzedniemce){
                if (r.getUdzialowiec().equals(p.getNazwiskoimie())) {
                    if (Mce.getMiesiacToNumber().get(r.getMc()) < Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu())) {
                        sumazaplaconegopodatku += r.getPodatekdowplaty();
                        sumawyplaconedywidendy += r.getDywidendadowyplaty();
                    }
                }
            }
            zaplaconepodatki.put(p.getNazwiskoimie(), sumazaplaconegopodatku);
            wyplaconedywidendy.put(p.getNazwiskoimie(), sumawyplaconedywidendy);
        }
    }
    
    private void obliczkwotydowyplaty() {
        pozycjeDoWyplaty = Collections.synchronizedList(new ArrayList<>());
        try {
            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView.getPodatnikObiekt());
            for (PodatnikUdzialy p : udzialy) {
                double udział = Z.z(Double.parseDouble(p.getUdzial())/100);
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(p.getNazwiskoimie(), udział));
                double udzialpln = Z.z(udział*wynikfinansowy);
                if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_Z_O_O)) {
                    udzialpln = Z.z(udział*wynikfinansowynetto);
                }
                double podatek = Z.z(podatnikkwotarazem.get(p.getNazwiskoimie()));
                double dowyplnarast = Z.z(udzialpln-podatek) > 0.0? Z.z(udzialpln-podatek) : 0.0;
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dowypłatyodpocz.rok"), dowyplnarast));
                double dowyplatypopmce = wyplaconedywidendy.get(p.getNazwiskoimie());
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wypłaconopopmce"), dowyplatypopmce));
                double dowyplmc = Z.z(udzialpln-podatek-dowyplatypopmce) > 0.0 ? Z.z(udzialpln-podatek-dowyplatypopmce) : 0.0;
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dowypłaty"), dowyplmc));
            }
            pozycjeDoWyplatyTablica = przekonwertujdotabeliWyplata(pozycjeDoWyplaty);
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie wybrano formy prawnej firmy. Nie można obliczyć podatku");
            return;
        }
    }
    
//    private void obliczkwotydowyplaty(Map<String, Double> pozycjeDoWyplatyExport) {
//        pozycjeDoWyplaty = Collections.synchronizedList(new ArrayList<>());
//        try {
//            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
//            for (PodatnikUdzialy p : udzialy) {
//                double udział = Z.z(Double.parseDouble(p.getUdzial())/100);
//                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(p.getNazwiskoimie(), udział));
//                double dowyplaty = Z.z(udział*wynikfinansowy);
//                double zaplacono = Z.z(podatnikkwotarazem.get(p.getNazwiskoimie()));
//                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dowypłaty"), Z.z(dowyplaty-zaplacono)));
//                int nrmca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu())-1;
//                if (listamiesiecy.size() >= nrmca) {
//                    pozycjeDoWyplatyExport.put(p.getNazwiskoimie(), Z.z(dowyplaty-zaplacono));
//                }
//            }
//        } catch (Exception e) {  E.e(e);
//            Msg.msg("e", "Nie określono udziałów w ustawieniach podatnika. Nie można obliczyć podatku");
//        }
//    }
    
    public void drukuj() {
        PdfSymulacjaWynikuNarastajaco.drukuj(listamiesiecy, pozycjePodsumowaniaWyniku, pozycjeObliczeniaPodatku, pozycjeDoWyplaty, wpisView);
    }
    
    @Inject
    private StrataView strataView;
    
    public void usun(WynikFKRokMc wynikFKRokMc) {
        try {
            List<Strata> stratypodatnika = strataView.getStratypodatnika();
            if (stratypodatnika!=null) {
                for (Strata s : stratypodatnika) {
                    for (Iterator<StrataWykorzystanie> it = s.getListawykorzystanie().iterator();it.hasNext();) {
                        StrataWykorzystanie st = it.next();
                        if (st.getRok().equals(wynikFKRokMc.getRok())&&st.getMc().equals(wynikFKRokMc.getMc())) {
                            it.remove();
                            strataDAO.edit(s);
                        }
                    }
                    
                }
            }
            wynikFKRokMcDAO.remove(wynikFKRokMc);
            listamiesiecy.remove(wynikFKRokMc);
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Wystąpił bład. Nie usunięto wyniku za mc "+wynikFKRokMc.getMc());
        }
    }
    
        

    public void odswiezsymulacjewynikunar() {
        wpisView.wpisAktualizuj();
        init();
    }
    
    private List<SymulacjaWynikuView.PozycjeSymulacjiTabela> przekonwertujdotabeliWyplata(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatkuPoprzedniemiesiace) {
        List<SymulacjaWynikuView.PozycjeSymulacjiTabela> tabela = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < pozycjeObliczeniaPodatkuPoprzedniemiesiace.size(); ) {
            SymulacjaWynikuView.PozycjeSymulacjiTabela s = new SymulacjaWynikuView.PozycjeSymulacjiTabela();
            SymulacjaWynikuView.PozycjeSymulacji pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setUdzialowiec(pobrane.getNazwa().split("#")[0].trim());
            s.setUdział(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setWynikfinansowyudzial(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setPodstawaopodatkowania(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setPodatekdochodowy(pobrane.getWartosc());
            tabela.add(s);
        }
        return tabela;
    }
    
    private List<SymulacjaWynikuView.PozycjeSymulacjiTabela> przekonwertujdotabeli(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatkuPoprzedniemiesiace) {
        List<SymulacjaWynikuView.PozycjeSymulacjiTabela> tabela = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < pozycjeObliczeniaPodatkuPoprzedniemiesiace.size(); ) {
            SymulacjaWynikuView.PozycjeSymulacjiTabela s = new SymulacjaWynikuView.PozycjeSymulacjiTabela();
            SymulacjaWynikuView.PozycjeSymulacji pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setUdzialowiec(pobrane.getNazwa().split("#")[0].trim());
            s.setUdział(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setWynikfinansowyudzial(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setPodstawaopodatkowania(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setPodatekdochodowy(pobrane.getWartosc());
            tabela.add(s);
        }
        return tabela;
    }
    
    private List<WynikFKRokMc> przekonwertujdotabeliPodatek(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatkuPoprzedniemiesiace) {
        List<WynikFKRokMc> tabela = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < pozycjeObliczeniaPodatkuPoprzedniemiesiace.size(); ) {
            WynikFKRokMc s = new WynikFKRokMc();
            s.setPodatnikObj(wpisView.getPodatnikObiekt());
            s.setRok(wpisView.getRokWpisuSt());
            s.setMc(wpisView.getMiesiacWpisu());
            s.setWprowadzil(wpisView.getUzer().getLogin());
            s.setData(new Date());
            SymulacjaWynikuView.PozycjeSymulacji pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setUdzialowiec(pobrane.getNazwa().split("#")[0].trim());
            s.setUdzial(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setWynikfinansowy(pobrane.getWartosc());
            //i += 1;
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            error.E.s("nazwa "+pobrane.getNazwa());
            s.setNkup(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setWynikpodatkowy(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setPodatek(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setPodatekzaplacono(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setPodatekdowplaty(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setDywidendadowyplaty(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setPrzychody(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setKoszty(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            error.E.s("nazwa "+pobrane.getNazwa());
            s.setNpup(pobrane.getWartosc());
            tabela.add(s);
        }
        
        return tabela;
    }
    
    public void zapiszpodatki() {
        for (WynikFKRokMc p : listamiesiecy) {
            if (p.getMc().equals(wpisView.getMiesiacWpisu())) {
                p.setPodatek(pdop);
                p.setPodatekdowplaty(podatekdozaplaty);
                p.setStrata(strata);
                p.setWynikfinansowynetto(Z.z(p.getWynikfinansowy()-pdop));
                wynikFKRokMcDAO.edit(p);
            }
        }
        for (WynikFKRokMc r : pozycjeObliczeniaPodatkuTabela) {
            for (SymulacjaWynikuView.PozycjeSymulacjiTabela s : pozycjeDoWyplatyTablica) {
                if (r.getUdzialowiec().equals(s.getUdzialowiec())) {
                    r.setDywidendadowyplaty(s.getPodatekdochodowy());
                }
                try {
                    wynikFKRokMcDAO.edit(r);
                } catch (Exception e) {
                    WynikFKRokMc znalezione = wynikFKRokMcDAO.findWynikFKPodatnikRokUdzialowiec(r);
                    wynikFKRokMcDAO.remove(znalezione);
                    wynikFKRokMcDAO.edit(r);
                }
            }
        }
        Msg.msg("Zapisano podatki");
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getDodatkoweinfo() {
        return dodatkoweinfo;
    }

    public void setDodatkoweinfo(String dodatkoweinfo) {
        this.dodatkoweinfo = dodatkoweinfo;
    }
    
    public List<WynikFKRokMc> getListamiesiecy() {
        return listamiesiecy;
    }
    
    public void setListamiesiecy(List<WynikFKRokMc> listamiesiecy) {
        this.listamiesiecy = listamiesiecy;
    }
    
    public WynikFKRokMc getSumamiesiecy() {
        return sumamiesiecy;
    }
    
    public void setSumamiesiecy(WynikFKRokMc sumamiesiecy) {
        this.sumamiesiecy = sumamiesiecy;
    }
    
    public List<SymulacjaWynikuView.PozycjeSymulacji> getPozycjePodsumowaniaWyniku() {
        return pozycjePodsumowaniaWyniku;
    }
    
    public void setPozycjePodsumowaniaWyniku(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjePodsumowaniaWyniku) {
        this.pozycjePodsumowaniaWyniku = pozycjePodsumowaniaWyniku;
    }
    
    
    
    public List<SymulacjaWynikuView.PozycjeSymulacji> getPozycjeObliczeniaPodatku() {
        return pozycjeObliczeniaPodatku;
    }
    
    public void setPozycjeObliczeniaPodatku(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku) {
        this.pozycjeObliczeniaPodatku = pozycjeObliczeniaPodatku;
    }
    
    public List<SymulacjaWynikuView.PozycjeSymulacji> getDozaplaty() {
        return dozaplaty;
    }
    
    public void setDozaplaty(List<SymulacjaWynikuView.PozycjeSymulacji> dozaplaty) {
        this.dozaplaty = dozaplaty;
    }
    
   
    
    public List<SymulacjaWynikuView.PozycjeSymulacji> getPozycjeDoWyplaty() {
        return pozycjeDoWyplaty;
    }
    
    public void setPozycjeDoWyplaty(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty) {
        this.pozycjeDoWyplaty = pozycjeDoWyplaty;
    }
    
   
    public List<WynikFKRokMc> getPozycjeObliczeniaPodatkuTabela() {
        return pozycjeObliczeniaPodatkuTabela;
    }

    public void setPozycjeObliczeniaPodatkuTabela(List<WynikFKRokMc> pozycjeObliczeniaPodatkuTabela) {
        this.pozycjeObliczeniaPodatkuTabela = pozycjeObliczeniaPodatkuTabela;
    }
    
    
    
    public List<SymulacjaWynikuView.PozycjeSymulacjiTabela> getPozycjeDoWyplatyTablica() {
        return pozycjeDoWyplatyTablica;
    }
    
    public void setPozycjeDoWyplatyTablica(List<SymulacjaWynikuView.PozycjeSymulacjiTabela> pozycjeDoWyplatyTablica) {
        this.pozycjeDoWyplatyTablica = pozycjeDoWyplatyTablica;
    }

    public WynikFKRokMc getSumapoprzednichmiesiecy() {
        return sumapoprzednichmiesiecy;
    }
    
    public void setSumapoprzednichmiesiecy(WynikFKRokMc sumapoprzednichmiesiecy) {
        this.sumapoprzednichmiesiecy = sumapoprzednichmiesiecy;
    }
//</editor-fold>

    
    

   
    

        
    
}
