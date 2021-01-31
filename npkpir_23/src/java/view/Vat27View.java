/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.Deklaracjavat27DAO;
import dao.DokDAO;
import dao.PodatnikDAO;
import entity.Deklaracjavat27;
import entity.Dok;
import entity.Vat27;
import entityfk.Vatuepodatnik;
import entityfk.VatuepodatnikPK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import pdf.PdfVAT27dekl;
import pdf.PdfVat27;
import pdf.PdfVatUE;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Vat27View implements Serializable {

    //lista gdzie beda podsumowane wartosci
    private List<Vat27> klienciWDTWNT;
    private List<Vat27> listawybranych;
    private List<Dok> listaDok;
    private List<Deklaracjavat27> deklaracjevat27;
    @Inject
    private Deklaracjavat27 dekl27selected;
    private boolean niemoznadrukowac;
    @Inject
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private Deklaracjavat27DAO deklaracjavat27DAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    private String opisvatuepkpir;
    private String brakustawienUE;
    private boolean deklaracja0korekta1;

    public Vat27View() {
        klienciWDTWNT = Collections.synchronizedList(new ArrayList<>());
        listaDok = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        List<Dok> dokvatmc = Collections.synchronizedList(new ArrayList<>());
        String rok = wpisView.getRokWpisuSt();
        String podatnik = wpisView.getPodatnikWpisu();
        String mc = wpisView.getMiesiacWpisu();
        opisvatuepkpir = wpisView.getPodatnikWpisu() + " Zestawienie dokumentów do deklaracji VAT-27 na koniec " + rok + "/" + mc + " rozliczenie miesięczne";
        try {
            dokvatmc = dokDAO.zwrocBiezacegoKlientaRokMC(wpisView.getPodatnikObiekt(), rok, mc);
            Collections.sort(dokvatmc, new Dokcomparator());
            klienciWDTWNT.addAll(kontrahenciUE(dokvatmc));
            int numerkolejny = 1;
            for (Dok p : dokvatmc) {
                p.setNrWpkpir(numerkolejny++);
            }
            //a teraz podsumuj klientów
            double sumanettovatue = 0.0;
            for (Dok p : dokvatmc) {
                for (Vat27 s : klienciWDTWNT) {
                    if (p.getKontr().getNip().equals(s.getKontrahent().getNip()) && p.getRodzajedok().getSkrot().equals(s.getTransakcja())) {
                        s.setNetto(p.getNetto() + s.getNetto());
                        s.setLiczbadok(s.getLiczbadok() + 1);
                        s.getZawiera().add(p);
                        p.setVat27(s);
                        listaDok.add(p);
                        sumanettovatue += (double) Math.round(p.getNetto());
                        break;
                    }
                }
            }
            Vat27 rzadpodsumowanie = new Vat27("podsum.", null, sumanettovatue, 0, null);
            klienciWDTWNT.add(rzadpodsumowanie);
            //zachowajwbazie(String.valueOf(rok), mc, podatnik);
           try {
            pobierzdeklaracje27();
            Deklaracjavat27 d = deklaracjavat27DAO.findbyPodatnikRokMc(wpisView);
            if (d != null) {
                deklaracja0korekta1 = true;
            }
            } catch (Exception e) { E.e(e); 
            }
        } catch (Exception ex) {
            brakustawienUE = "Wystąpił błąd podczas pobierania dokumentów. Prawdopodobnie nie ma ustawień parametru dla VAT-27 dla bieżącego podatnika";
            E.e(ex);
        }

    }
    
    private void init2() {
        try {
            pobierzdeklaracje27();
            Deklaracjavat27 d = deklaracjavat27DAO.findbyPodatnikRokMc(wpisView);
            if (d != null) {
                deklaracja0korekta1 = true;
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void init3() {
        try {
            Deklaracjavat27 d = deklaracjavat27DAO.findbyPodatnikRokMc(wpisView);
            if (d != null) {
                deklaracja0korekta1 = true;
            } else {
                deklaracja0korekta1 = false;
            }
            for (Deklaracjavat27 dek : deklaracjevat27) {
                if (dek.getRok().equals(wpisView.getRokWpisuSt()) && dek.getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                    deklaracja0korekta1 = true;
                }
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }

    private void zachowajwbazie(String rok, String symbolokresu, String klient) {
        Vatuepodatnik vatuepodatnik = new Vatuepodatnik();
        VatuepodatnikPK vatuepodatnikPK = new VatuepodatnikPK();
        vatuepodatnikPK.setRok(rok);
        vatuepodatnikPK.setSymbolokresu(symbolokresu);
        vatuepodatnikPK.setKlient(klient);
        vatuepodatnik.setVatuepodatnikPK(vatuepodatnikPK);
        vatuepodatnik.setKlienciwdtwnt(klienciWDTWNT);
        vatuepodatnik.setMc0kw1(Boolean.TRUE);
        vatuepodatnik.setRozliczone(Boolean.FALSE);
    }

   
   public void pobierzdeklaracje27()  {
       deklaracjevat27 = deklaracjavat27DAO.findbyPodatnikRok(wpisView);
       if (deklaracjevat27 == null) {
           deklaracjevat27 = Collections.synchronizedList(new ArrayList<>());
       }
    }

    public void drukujewidencje() {
        try {
            PdfVatUE.drukujewidencje(klienciWDTWNT, wpisView, "VAT-27");
        } catch (Exception e) {
            E.e(e);

        }
    }

    public void drukujewidencjeTabela() {
        try {
            if (listawybranych != null && !listawybranych.isEmpty()) {
                PdfVat27.drukujewidencjeTabela(listawybranych, wpisView, "VAT-27");
            } else {
                PdfVat27.drukujewidencjeTabela(klienciWDTWNT, wpisView, "VAT-27");
            }
        } catch (Exception e) {
            E.e(e);

        }
    }
    
     private Collection<? extends Vat27> kontrahenciUE(List<Dok> dokvatmc) {
        Set<Vat27> klienty = new HashSet<>();
        for (Dok p : dokvatmc) {
            if (warunekkontrahenci(p)) {
                //wyszukujemy dokumenty WNT i WDT dodajemu do sumy
                Vat27 veu = new Vat27(p.getRodzajedok().getSkrot(), p.getKontr(), 0.0, 0);
                veu.setZawiera(new ArrayList<>());
                klienty.add(veu);
            }
        }
        return klienty;
    }

    private boolean warunekkontrahenci(Dok p) {
        boolean zwrot = false;
        zwrot = p.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie sprzedawca");
        return zwrot;
    }
    
    public void usundekl(Deklaracjavat27 d) {
        try {
            deklaracjavat27DAO.remove(d);
            deklaracjevat27.remove(d);
            for (Vat27 p : d.getPozycje()) {
                if (!p.getZawiera().isEmpty()) {
                    for (Dok dok:p.getZawiera()) {
                        dok.setVat27(null);
                    }
                    dokDAO.editList(p.getZawiera());
                }
            }
            Msg.dP();
            init2();
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void drukuj(Deklaracjavat27 d) {
        try {
            if (d == null) {
                Msg.msg("e", "Nie wybrano deklaracji");
            } else {
                PdfVAT27dekl.drukujVAT(podatnikDAO, d, wpisView);
                Msg.msg("Wydrukowano deklaracje");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wydrukowano ewidencji");
        }
    }
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    
    public String getOpisvatuepkpir() {
        return opisvatuepkpir;
    }
    
    public void setOpisvatuepkpir(String opisvatuepkpir) {
        this.opisvatuepkpir = opisvatuepkpir;
    }

    public List<Dok> getListaDok() {
        return listaDok;
    }

    public void setListaDok(List<Dok> listaDok) {
        this.listaDok = listaDok;
    }

    public List<Deklaracjavat27> getDeklaracjevat27() {
        return deklaracjevat27;
    }

    public void setDeklaracjevat27(List<Deklaracjavat27> deklaracjevat27) {
        this.deklaracjevat27 = deklaracjevat27;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public List<Vat27> getKlienciWDTWNT() {
        return klienciWDTWNT;
    }
    
    public void setKlienciWDTWNT(List<Vat27> klienciWDTWNT) {
        this.klienciWDTWNT = klienciWDTWNT;
    }
    
    public List<Vat27> getListawybranych() {
        return listawybranych;
    }
    
    public void setListawybranych(List<Vat27> listawybranych) {
        this.listawybranych = listawybranych;
    }

    public Deklaracjavat27 getDekl27selected() {
        return dekl27selected;
    }

    public void setDekl27selected(Deklaracjavat27 dekl27selected) {
        this.dekl27selected = dekl27selected;
    }

    public boolean isDeklaracja0korekta1() {
        return deklaracja0korekta1;
    }

    public void setDeklaracja0korekta1(boolean deklaracja0korekta1) {
        this.deklaracja0korekta1 = deklaracja0korekta1;
    }
    
    
    public boolean isNiemoznadrukowac() {
        return niemoznadrukowac;
    }
    
    public void setNiemoznadrukowac(boolean niemoznadrukowac) {
        this.niemoznadrukowac = niemoznadrukowac;
    }
    
    public String getBrakustawienUE() {
        return brakustawienUE;
    }
    
    public void setBrakustawienUE(String brakustawienUE) {
        this.brakustawienUE = brakustawienUE;
    }
//</editor-fold>
   

  
}
