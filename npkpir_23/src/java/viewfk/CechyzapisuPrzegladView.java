/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.CechazapisuBean;
import daoFK.DokDAOfk;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import pdffk.PdfCechyZapisow;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class CechyzapisuPrzegladView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Dokfk> wykazZaksiegowanychDokumentow;
    private List<CechaStronaWiersza> zapisyZCecha;
    private List<CechaStronaWiersza> zapisyZCechafilter;
    private List<CechaStronaWiersza> wybraneZapisyZCecha;
    private Set<String> wykazcech;
    private double razem;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    private boolean jakiecechy;
    private boolean cit8;

    public CechyzapisuPrzegladView() {
         E.m(this);
        this.wykazZaksiegowanychDokumentow = new ArrayList<>();
        this.zapisyZCecha = new ArrayList<>();
        this.wykazcech = new HashSet<>();
    }
    
    
    public void init() {
        wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        zapisyZCecha = new ArrayList<>();
        zapisyZCecha = CechazapisuBean.pobierzstrony(wykazZaksiegowanychDokumentow);
        wykazcech = new HashSet<>();
        int i = 1;
        for (CechaStronaWiersza p : zapisyZCecha) {
            p.setId(i++);
            wykazcech.add(p.getCechazapisu().getCechazapisuPK().getNazwacechy());
        }
        if (jakiecechy) {
            for (Iterator<CechaStronaWiersza> it = zapisyZCecha.iterator(); it.hasNext();) {
                CechaStronaWiersza p = it.next();
                if (p.cechazapisu.getCharaktercechy() == 1) {
                    it.remove();
                }
            }
        }
    }
    
    public void initCIT8() {
        zapisyZCecha = new ArrayList<>();
        wykazcech = new HashSet<>();
        zapisyZCecha = new ArrayList<>();
        wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRok(wpisView);
        this.zapisyZCecha = CechazapisuBean.pobierzstrony(wykazZaksiegowanychDokumentow);
        if (cit8) {
            for (Iterator<CechaStronaWiersza> it = zapisyZCecha.iterator(); it.hasNext();) {
                CechaStronaWiersza n = it.next();
                if (!n.getStronaWiersza().getDokfk().getMiesiac().equals("12")) {
                    if (n.getCechazapisu().getCechazapisuPK().getNazwacechy().toLowerCase().equals("pmn") || n.getCechazapisu().getCechazapisuPK().getNazwacechy().toLowerCase().equals("kupmn")) {
                        it.remove();
                    }
                }
            }
        }
        int i = 1;
        for (CechaStronaWiersza p : zapisyZCecha) {
            p.setId(i++);
            wykazcech.add(p.getCechazapisu().getCechazapisuPK().getNazwacechy());
        }
        if (jakiecechy) {
            for (Iterator<CechaStronaWiersza> it = zapisyZCecha.iterator(); it.hasNext();) {
                CechaStronaWiersza p = it.next();
                if (p.cechazapisu.getCharaktercechy() == 1) {
                    it.remove();
                }
            }
        }
        RequestContext.getCurrentInstance().update("formcechyzapisow");
    }

    
    
    public void odswiez() {
        this.zapisyZCechafilter = null;
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            initCIT8();
        } else {
            wpisView.wpisAktualizuj();
            init();
        }
    }

    public void sumujwybrane() {
        razem = 0.0;
        for (CechaStronaWiersza p : wybraneZapisyZCecha) {
            razem += p.getStronaWiersza().getKwotaPLN();
        }
    }
    
    public void resetujsuma(){
        razem = 0.0;
    }
    
    public void drukujzaksiegowanydokument() {
        if (wybraneZapisyZCecha != null && !wybraneZapisyZCecha.isEmpty()) {
            PdfCechyZapisow.drukujzaksiegowanydokument(wpisView, wybraneZapisyZCecha);
        } else {
            PdfCechyZapisow.drukujzaksiegowanydokument(wpisView, zapisyZCecha);
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
        
        public CechaStronaWiersza() {
        }

        public CechaStronaWiersza(Cechazapisu cechazapisu, StronaWiersza stronaWiersza) {
            this.cechazapisu = cechazapisu;
            this.stronaWiersza = stronaWiersza;
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

        
        
        @Override
        public String toString() {
            return "CechaStronaWiersza{" + "cechazapisu=" + cechazapisu + ", stronaWiersza=" + stronaWiersza + '}';
        }
        
        
        
    }
    
    
    
}
