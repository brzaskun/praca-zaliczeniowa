/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.CechazapisuBean;
import comparator.CechaStronaWierszacomparator;
import dao.DokDAOfk;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import pdffk.PdfCechyZapisow;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class CechyzapisuPrzegladView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<CechaStronaWiersza> zapisyZCecha;
    private List<CechaStronaWiersza> zapisyZCechafilter;
    private List<CechaStronaWiersza> wybraneZapisyZCecha;
    private Set<String> wykazcech;
    private double razem;
    @Inject
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    private boolean jakiecechy;
    private boolean cit8;

    public CechyzapisuPrzegladView() {
         ////E.m(this);
        this.zapisyZCecha = Collections.synchronizedList(new ArrayList<>());
        this.wykazcech = new HashSet<>();
    }
    
    
    public void init() { //E.m(this);
        List<Dokfk> wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        zapisyZCecha = Collections.synchronizedList(new ArrayList<>());
        zapisyZCecha = CechazapisuBean.pobierzstrony(wykazZaksiegowanychDokumentow);
        wykazcech = new HashSet<>();
        int i = 1;
        for (CechaStronaWiersza p : zapisyZCecha) {
            p.setId(i++);
            wykazcech.add(p.getCechazapisu().getNazwacechy());
        }
        if (jakiecechy) {
            for (Iterator<CechaStronaWiersza> it = zapisyZCecha.iterator(); it.hasNext();) {
                CechaStronaWiersza p = it.next();
                if (p.cechazapisu.getCharaktercechy() == 1) {
                    it.remove();
                }
            }
        }
        Collections.sort(zapisyZCecha, new CechaStronaWierszacomparator());
    }
    
    public void initCIT8() {
        zapisyZCecha = Collections.synchronizedList(new ArrayList<>());
        wykazcech = new HashSet<>();
        zapisyZCecha = Collections.synchronizedList(new ArrayList<>());
        List<Dokfk> wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRok(wpisView);
        this.zapisyZCecha = CechazapisuBean.pobierzstrony(wykazZaksiegowanychDokumentow);
        if (cit8) {
            for (Iterator<CechaStronaWiersza> it = zapisyZCecha.iterator(); it.hasNext();) {
                CechaStronaWiersza n = it.next();
                if (!n.getStronaWiersza().getDokfk().getMiesiac().equals("12")) {
                    if (n.getCechazapisu().getNazwacechy().toLowerCase().equals("pmn") || n.getCechazapisu().getNazwacechy().toLowerCase().equals("kupmn")) {
                        it.remove();
                    }
                }
            }
        }
        wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt(), "12");
        List<CechaStronaWiersza> zapisyZCecharokpop = CechazapisuBean.pobierzstrony(wykazZaksiegowanychDokumentow);
        for (Iterator<CechaStronaWiersza> it = zapisyZCecharokpop.iterator(); it.hasNext();) {
                CechaStronaWiersza p = it.next();
                if (!p.cechazapisu.getNazwacechy().equals("KUPMN")&& !p.cechazapisu.getNazwacechy().equals("PMN")) {
                    it.remove();
                }
        }
        if (!zapisyZCecharokpop.isEmpty()) {
            zapisyZCecha.addAll(zapisyZCecharokpop);
        }
        int i = 1;
        for (CechaStronaWiersza p : zapisyZCecha) {
            p.setId(i++);
            wykazcech.add(p.getCechazapisu().getNazwacechy());
        }
        if (jakiecechy) {
            for (Iterator<CechaStronaWiersza> it = zapisyZCecha.iterator(); it.hasNext();) {
                CechaStronaWiersza p = it.next();
                if (p.cechazapisu.getCharaktercechy() == 1) {
                    it.remove();
                }
            }
        }
        Collections.sort(zapisyZCecha, new CechaStronaWierszacomparator());
    }

    
    
    public void odswiez() {
        this.zapisyZCechafilter = null;
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            initCIT8();
        } else {
            wpisView.wpisAktualizuj();
            init();
        }
        sumujpobrane();
    }
    
    public void sumujpobrane() {
        razem = 0.0;
        if (zapisyZCecha!=null) {
            for (CechaStronaWiersza p : zapisyZCecha) {
                if (p.getCechazapisu().getNazwacechy().equals("KUPMN")) {
                    if (p.getStronaWiersza().getMc().equals("12") && p.getStronaWiersza().getRok().equals(wpisView.getRokUprzedniSt())) {
                        razem -= p.getStronaWiersza().getKwotaPLN();
                    } else {
                        razem += p.getStronaWiersza().getKwotaPLN();
                    }
                } else {
                    razem += p.getStronaWiersza().getKwotaPLN();
                }
            }
        }
    }

    public void sumujwybrane() {
        razem = 0.0;
        if (wybraneZapisyZCecha!=null) {
             for (CechaStronaWiersza p : wybraneZapisyZCecha) {
                razem += p.getStronaWiersza().getKwotaPLN();
            }
        }
    }
    
    public void sumujfiltrowane(){
        razem = 0.0;
        if (zapisyZCechafilter!=null) {
            for (CechaStronaWiersza p : zapisyZCechafilter) {
                razem += p.getStronaWiersza().getKwotaPLN();
            }
        }
    }
    
    public void drukujzaksiegowanydokument() {
        if (wybraneZapisyZCecha != null && wybraneZapisyZCecha.size()>0) {
            PdfCechyZapisow.drukujlistaCech(wpisView, wybraneZapisyZCecha);
        } else if (zapisyZCechafilter != null && zapisyZCechafilter.size()>0) {
            PdfCechyZapisow.drukujlistaCech(wpisView, zapisyZCechafilter);
        } else {
            PdfCechyZapisow.drukujlistaCech(wpisView, zapisyZCecha);
        }
    }
    
       //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public boolean isCit8() {
        return cit8;
    }

    public void setCit8(boolean cit8) {
        this.cit8 = cit8;
    }

    public boolean isJakiecechy() {
        return jakiecechy;
    }

    public void setJakiecechy(boolean jakiecechy) {
        this.jakiecechy = jakiecechy;
    }

    public Set<String> getWykazcech() {
        return wykazcech;
    }

    public void setWykazcech(Set<String> wykazcech) {
        this.wykazcech = wykazcech;
    }
    
    public List<CechaStronaWiersza> getZapisyZCecha() {
        return zapisyZCecha;
    }
    
    public void setZapisyZCecha(List<CechaStronaWiersza> zapisyZCecha) {
        this.zapisyZCecha = zapisyZCecha;
    }

    public List<CechaStronaWiersza> getWybraneZapisyZCecha() {
        return wybraneZapisyZCecha;
    }

    public void setWybraneZapisyZCecha(List<CechaStronaWiersza> wybraneZapisyZCecha) {
        this.wybraneZapisyZCecha = wybraneZapisyZCecha;
    }

    public double getRazem() {
        return razem;
    }

    public void setRazem(double razem) {
        this.razem = razem;
    }

    public List<CechaStronaWiersza> getZapisyZCechafilter() {
        return zapisyZCechafilter;
    }

    public void setZapisyZCechafilter(List<CechaStronaWiersza> zapisyZCechafilter) {
        this.zapisyZCechafilter = zapisyZCechafilter;
    }
    
    
    
//</editor-fold>

    public static class CechaStronaWiersza {
        private int id;
        private Cechazapisu cechazapisu;
        private StronaWiersza stronaWiersza;
        private boolean cechadok;
        private boolean cechazapis;
        
        public CechaStronaWiersza() {
        }

        public CechaStronaWiersza(Cechazapisu cechazapisu, StronaWiersza stronaWiersza, boolean cd, boolean cz) {
            this.cechazapisu = cechazapisu;
            this.stronaWiersza = stronaWiersza;
            this.cechadok = cd;
            this.cechazapis = cz;
        }
        
        public CechaStronaWiersza(Cechazapisu cechazapisu, StronaWiersza stronaWiersza, String popmc) {
            this.cechazapisu = cechazapisu;
            stronaWiersza.setKwotaPLN(-stronaWiersza.getKwotaPLN());
            this.stronaWiersza = stronaWiersza;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
        
        
        public Cechazapisu getCechazapisu() {
            return cechazapisu;
        }

        public void setCechazapisu(Cechazapisu cechazapisu) {
            this.cechazapisu = cechazapisu;
        }

        public StronaWiersza getStronaWiersza() {
            return stronaWiersza;
        }

        public void setStronaWiersza(StronaWiersza stronaWiersza) {
            this.stronaWiersza = stronaWiersza;
        }

        public boolean isCechadok() {
            return cechadok;
        }

        public void setCechadok(boolean cechadok) {
            this.cechadok = cechadok;
        }

        public boolean isCechazapis() {
            return cechazapis;
        }

        public void setCechazapis(boolean cechazapis) {
            this.cechazapis = cechazapis;
        }

        
        
        @Override
        public String toString() {
            return "CechaStronaWiersza{" + "cechazapisu=" + cechazapisu + ", stronaWiersza=" + stronaWiersza + '}';
        }
        
        
        
    }
    
    
    
}
