/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.PodatnikUdzialyDAO;
import daoFK.WynikFKRokMcDAO;
import embeddable.Mce;
import entity.PodatnikUdzialy;
import entityfk.WynikFKRokMc;
import enumy.FormaPrawna;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.B;
import msg.Msg;
import pdf.PdfSymulacjaWynikuNarastajaco;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SymulacjaWynikuNarastajacoView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<WynikFKRokMc> listamiesiecy;
    private List<WynikFKRokMc> listamiesiecypoprzednich;
    private WynikFKRokMc sumamiesiecy;
    private WynikFKRokMc sumapoprzednichmiesiecy;
    private List<SymulacjaWynikuView.PozycjeSymulacji> dozaplaty;
    private Map<String, Double> podatnikkwota;
    private Map<String, Double> podatnikkwotarazem;
    private List<SymulacjaWynikuView.PozycjeSymulacji> pozycjePodsumowaniaWyniku;
    private List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku;
    private List<SymulacjaWynikuView.PozycjeSymulacjiTabela> pozycjeObliczeniaPodatkuTabela;
    private List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatkuPoprzedniemiesiace;
    private List<SymulacjaWynikuView.PozycjeSymulacjiTabela> pozycjeObliczeniaPodatkuPoprzedniemiesiaceTabela;
    private List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty;
    private List<SymulacjaWynikuView.PozycjeSymulacjiTabela> pozycjeDoWyplatyTablica;
    @Inject
    private WynikFKRokMcDAO wynikFKRokMcDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private double wynikfinansowy;
    private double wynikfinansowynetto;
    private double wynikfinansowynettoPopMce;
    private double pdop;
    private double zaplacono;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;

    public SymulacjaWynikuNarastajacoView() {
        this.listamiesiecy = new ArrayList<>();
        this.listamiesiecypoprzednich = new ArrayList<>();
        this.dozaplaty = new ArrayList<>();
    }
    
    
    
    public void init() {
        this.listamiesiecy = new ArrayList<>();
        this.listamiesiecypoprzednich = new ArrayList<>();
        this.dozaplaty = new ArrayList<>();
        List<WynikFKRokMc> listapobrana = wynikFKRokMcDAO.findWynikFKPodatnikRok(wpisView);
        int biezacymc = Integer.parseInt(wpisView.getMiesiacWpisu());
        for (Iterator<WynikFKRokMc> p = listapobrana.iterator(); p.hasNext(); ) {
            WynikFKRokMc r = (WynikFKRokMc) p.next();
            int mc = Integer.parseInt(r.getMc());
            if (mc < biezacymc) {
                listamiesiecy.add(r);
                listamiesiecypoprzednich.add(r);
            } else if (mc == biezacymc) {
                listamiesiecy.add(r);
            }
        }
        sumapoprzednichmiesiecy = sumujpoprzedniemiesiace();
        sumamiesiecy = sumujmiesiace();
        obliczsymulacjepoprzedniemce();
        obliczsymulacje();
        obliczkwotydowyplaty();
    }
    
    public Map<String, Double> danedobiezacejsym() {
        this.listamiesiecy = new ArrayList<>();
        this.listamiesiecypoprzednich = new ArrayList<>();
        this.dozaplaty = new ArrayList<>();
        List<WynikFKRokMc> listapobrana = wynikFKRokMcDAO.findWynikFKPodatnikRok(wpisView);
        int biezacymc = Integer.parseInt(wpisView.getMiesiacUprzedni());
        for (Iterator<WynikFKRokMc> p = listapobrana.iterator(); p.hasNext(); ) {
            WynikFKRokMc r = (WynikFKRokMc) p.next();
            int mc = Integer.parseInt(r.getMc());
            if (mc < biezacymc) {
                listamiesiecy.add(r);
                listamiesiecypoprzednich.add(r);
            } else if (mc == biezacymc) {
                listamiesiecy.add(r);
            }
        }
        sumapoprzednichmiesiecy = sumujpoprzedniemiesiace();
        sumamiesiecy = sumujmiesiace();
        obliczsymulacjepoprzedniemce();
        obliczsymulacje();
        Map<String, Double> pozycjeDoWyplatyExport = new HashMap<>();
        obliczkwotydowyplaty(pozycjeDoWyplatyExport);
        return pozycjeDoWyplatyExport;
    }
    
    private WynikFKRokMc sumujmiesiace() {
        WynikFKRokMc w = new WynikFKRokMc();
        double przychod = 0;
        double koszt = 0;
        double npup = 0;
        double nkup = 0;
        for (WynikFKRokMc p : listamiesiecy) {
            przychod += p.getPrzychody();
            koszt += p.getKoszty();
            npup += p.getNpup();
            nkup += p.getNkup();
        }
        w.setPrzychody(Z.z(przychod));
        w.setKoszty(Z.z(koszt));
        w.setWynikfinansowy(Z.z(przychod-koszt));
        w.setNpup(npup);
        w.setNkup(nkup);
        w.setWynikpodatkowy(Z.z(przychod-koszt+npup-nkup));
        return w;
    }
    
    private WynikFKRokMc sumujpoprzedniemiesiace() {
        WynikFKRokMc w = new WynikFKRokMc();
        double przychod = 0;
        double koszt = 0;
        double npup = 0;
        double nkup = 0;
        zaplacono = 0.0;
        for (WynikFKRokMc p : listamiesiecypoprzednich) {
            przychod += p.getPrzychody();
            koszt += p.getKoszty();
            npup += p.getNpup();
            nkup += p.getNkup();
            zaplacono += p.getPodatek();
        }
        w.setPrzychody(Z.z(przychod));
        w.setKoszty(Z.z(koszt));
        w.setWynikfinansowy(Z.z(przychod-koszt));
        w.setNpup(npup);
        w.setNkup(nkup);
        w.setWynikpodatkowy(Z.z(przychod-koszt+npup-nkup));
        return w;
    }
    
    private void obliczsymulacje() {
        podatnikkwotarazem = new HashMap<>();
        pozycjePodsumowaniaWyniku = new ArrayList<>();
        double przychody = sumamiesiecy.getPrzychody();
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("przychody"), przychody));
        double koszty = sumamiesiecy.getKoszty();
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("koszty"), koszty));
        wynikfinansowy = Z.z(przychody - koszty);
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wynikfinansowy"), wynikfinansowy));
        double npup = sumamiesiecy.getNpup();
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("npup"), npup));
        double nkup = sumamiesiecy.getNkup();
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("nkup"), nkup));
        double wynikpodatkowy = Z.z(wynikfinansowy + npup - nkup);
        pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wynikpodatkowy"), wynikpodatkowy));
        wynikfinansowynetto = wynikpodatkowy;
        if (wpisView.getPodatnikObiekt().getFormaPrawna().equals(FormaPrawna.SPOLKA_Z_O_O)) {
            double podstawaopodatkowania = Z.z0(wynikpodatkowy);
            pdop = 0.0;
            if (podstawaopodatkowania > 0) {
                pdop = Z.z0(podstawaopodatkowania*0.19);
            }
            pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("pdop"), pdop));
            pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("zapłacono"), zaplacono));
            wynikfinansowynetto = wynikpodatkowy - pdop; 
            pdop = Z.z(pdop-zaplacono) > 0.0 ? Z.z(pdop-zaplacono) : 0.0;
            pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dozapłaty"), pdop));
            pozycjePodsumowaniaWyniku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wynikfinansowynetto"), wynikfinansowynetto));
        }
        pozycjeObliczeniaPodatku = new ArrayList<>();
        try {
            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
            int i = 1;
            for (PodatnikUdzialy p : udzialy) {
                double udział = Z.z(Double.parseDouble(p.getUdzial())/100);
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(p.getNazwiskoimie(), udział));
                double podstawaopodatkowania = Z.z0(udział*wynikfinansowynetto);
                double wynikfinansowyudzial = Z.z0(udział*wynikfinansowy);
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wynikfinansowy")+" #"+String.valueOf(i), wynikfinansowyudzial));
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("podstawaopodatkowania")+" #"+String.valueOf(i), podstawaopodatkowania));
                double podatek = Z.z0(podstawaopodatkowania*0.19);
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("podatekdochodowy")+" #"+String.valueOf(i), podatek));
                double zaplacono = Z.z0(podatnikkwota.get(p.getNazwiskoimie())) >= 0 ? Z.z0(podatnikkwota.get(p.getNazwiskoimie())) : 0.0;
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("zapłacono")+" #"+String.valueOf(i), -zaplacono));
                pozycjeObliczeniaPodatku.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dozapłaty")+" #"+String.valueOf(i++), Z.z0(podatek-zaplacono)));
                podatnikkwotarazem.put(p.getNazwiskoimie(),Z.z0(podatek));
            }
            pozycjeObliczeniaPodatkuTabela = przekonwertujdotabeliPodatek(pozycjeObliczeniaPodatku);
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie określono udziałów w ustawieniach podatnika. Nie można obliczyć podatku");
        }
    }
    
     private void obliczsymulacjepoprzedniemce() {
        wynikfinansowynettoPopMce = 0.0;
        double przychody = sumapoprzednichmiesiecy.getPrzychody();
        double koszty = sumapoprzednichmiesiecy.getKoszty();
        double wynikfinansowy = Z.z(przychody - koszty);
        double npup = sumapoprzednichmiesiecy.getNpup();
        double nkup = sumapoprzednichmiesiecy.getNkup();
        double wynikpodatkowy = Z.z(wynikfinansowy + npup - nkup);
        pozycjeObliczeniaPodatkuPoprzedniemiesiace = new ArrayList<>();
        podatnikkwota = new HashMap<>();
        try {
            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
            int i = 1;
            for (PodatnikUdzialy p : udzialy) {
                double udział = Z.z(Double.parseDouble(p.getUdzial())/100);
                pozycjeObliczeniaPodatkuPoprzedniemiesiace.add(new SymulacjaWynikuView.PozycjeSymulacji(p.getNazwiskoimie(), udział));
                double wynikfinansowyudzial = Z.z(udział*wynikfinansowy);
                double podstawaopodatkowania = Z.z0(udział*wynikpodatkowy);
                pozycjeObliczeniaPodatkuPoprzedniemiesiace.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wynikfinansowy")+" #pm"+String.valueOf(i), wynikfinansowyudzial));
                pozycjeObliczeniaPodatkuPoprzedniemiesiace.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("podstawaopodatkowania")+" #pm"+String.valueOf(i), podstawaopodatkowania));
                pozycjeObliczeniaPodatkuPoprzedniemiesiace.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("podatekdochodowypopmc")+" #pm"+String.valueOf(i++), Z.z0(podstawaopodatkowania*0.19)));
                podatnikkwota.put(p.getNazwiskoimie(),Z.z0(podstawaopodatkowania*0.19));
                wynikfinansowynettoPopMce += Z.z((wynikfinansowyudzial - Z.z0(podstawaopodatkowania*0.19)));
            }
            pozycjeObliczeniaPodatkuPoprzedniemiesiaceTabela = przekonwertujdotabeli(pozycjeObliczeniaPodatkuPoprzedniemiesiace);
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie określono udziałów w ustawieniach podatnika. Nie można obliczyć podatku");
        }
    }
    
    private void obliczkwotydowyplaty() {
        pozycjeDoWyplaty = new ArrayList<>();
        try {
            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
            for (PodatnikUdzialy p : udzialy) {
                double udział = Z.z(Double.parseDouble(p.getUdzial())/100);
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(p.getNazwiskoimie(), udział));
                double dowyplaty = Z.z(udział*wynikfinansowy);
                double podatek = Z.z(podatnikkwotarazem.get(p.getNazwiskoimie()));
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dowypłatyodpocz.rok"), Z.z(dowyplaty-podatek)));
                double dowyplatypopmce = Z.z(udział*wynikfinansowynettoPopMce);
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("wypłaconopopmce"), dowyplatypopmce));
                double dowyplmc = Z.z(dowyplaty-podatek-dowyplatypopmce);
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dowypłaty"), dowyplmc));
            }
            pozycjeDoWyplatyTablica = przekonwertujdotabeliWyplata(pozycjeDoWyplaty);
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie określono udziałów w ustawieniach podatnika. Nie można obliczyć podatku");
        }
    }
    
    private void obliczkwotydowyplaty(Map<String, Double> pozycjeDoWyplatyExport) {
        pozycjeDoWyplaty = new ArrayList<>();
        try {
            List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
            for (PodatnikUdzialy p : udzialy) {
                double udział = Z.z(Double.parseDouble(p.getUdzial())/100);
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(p.getNazwiskoimie(), udział));
                double dowyplaty = Z.z(udział*wynikfinansowy);
                double zaplacono = Z.z(podatnikkwotarazem.get(p.getNazwiskoimie()));
                pozycjeDoWyplaty.add(new SymulacjaWynikuView.PozycjeSymulacji(B.b("dowypłaty"), Z.z(dowyplaty-zaplacono)));
                int nrmca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu())-1;
                if (listamiesiecy.size() >= nrmca) {
                    pozycjeDoWyplatyExport.put(p.getNazwiskoimie(), Z.z(dowyplaty-zaplacono));
                }
            }
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie określono udziałów w ustawieniach podatnika. Nie można obliczyć podatku");
        }
    }
    
    public void drukuj() {
        PdfSymulacjaWynikuNarastajaco.drukuj(listamiesiecy, pozycjePodsumowaniaWyniku, pozycjeObliczeniaPodatkuPoprzedniemiesiace, pozycjeObliczeniaPodatku, pozycjeDoWyplaty, wpisView);
    }
    
    public void usun(WynikFKRokMc wynikFKRokMc) {
        try {
            wynikFKRokMcDAO.destroy(wynikFKRokMc);
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
        List<SymulacjaWynikuView.PozycjeSymulacjiTabela> tabela = new ArrayList<>();
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
        List<SymulacjaWynikuView.PozycjeSymulacjiTabela> tabela = new ArrayList<>();
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
    
    private List<SymulacjaWynikuView.PozycjeSymulacjiTabela> przekonwertujdotabeliPodatek(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatkuPoprzedniemiesiace) {
        List<SymulacjaWynikuView.PozycjeSymulacjiTabela> tabela = new ArrayList<>();
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
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setZapłacono(pobrane.getWartosc());
            pobrane = pozycjeObliczeniaPodatkuPoprzedniemiesiace.get(i++);
            s.setDozapłaty(pobrane.getWartosc());
            tabela.add(s);
        }
        return tabela;
    }
    
    public void zapiszpodatki() {
        for (WynikFKRokMc p : listamiesiecy) {
            if (p.getMc().equals(wpisView.getMiesiacWpisu())) {
                p.setPodatek(pdop);
                wynikFKRokMcDAO.edit(p);
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
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
    
    public List<SymulacjaWynikuView.PozycjeSymulacji> getPozycjeObliczeniaPodatkuPoprzedniemiesiace() {
        return pozycjeObliczeniaPodatkuPoprzedniemiesiace;
    }
    
    public void setPozycjeObliczeniaPodatkuPoprzedniemiesiace(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatkuPoprzedniemiesiace) {
        this.pozycjeObliczeniaPodatkuPoprzedniemiesiace = pozycjeObliczeniaPodatkuPoprzedniemiesiace;
    }
    
    public List<SymulacjaWynikuView.PozycjeSymulacji> getPozycjeDoWyplaty() {
        return pozycjeDoWyplaty;
    }
    
    public void setPozycjeDoWyplaty(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty) {
        this.pozycjeDoWyplaty = pozycjeDoWyplaty;
    }
    
    public List<SymulacjaWynikuView.PozycjeSymulacjiTabela> getPozycjeObliczeniaPodatkuPoprzedniemiesiaceTabela() {
        return pozycjeObliczeniaPodatkuPoprzedniemiesiaceTabela;
    }
    
    public void setPozycjeObliczeniaPodatkuPoprzedniemiesiaceTabela(List<SymulacjaWynikuView.PozycjeSymulacjiTabela> pozycjeObliczeniaPodatkuPoprzedniemiesiaceTabela) {
        this.pozycjeObliczeniaPodatkuPoprzedniemiesiaceTabela = pozycjeObliczeniaPodatkuPoprzedniemiesiaceTabela;
    }
    
    public List<SymulacjaWynikuView.PozycjeSymulacjiTabela> getPozycjeObliczeniaPodatkuTabela() {
        return pozycjeObliczeniaPodatkuTabela;
    }
    
    public void setPozycjeObliczeniaPodatkuTabela(List<SymulacjaWynikuView.PozycjeSymulacjiTabela> pozycjeObliczeniaPodatkuTabela) {
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
