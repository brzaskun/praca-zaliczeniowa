/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
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
    private List<CechaStronaWiersza> wybraneZapisy;
    private double razem;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;

    public CechyzapisuPrzegladView() {
        this.wykazZaksiegowanychDokumentow = new ArrayList<>();
        this.zapisyZCecha = new ArrayList<>();
    }
    
    
    public void init() {
        this.zapisyZCecha = new ArrayList<>();
        wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        for (Dokfk p : wykazZaksiegowanychDokumentow) {
            if (p.getCechadokumentuLista() != null && p.getCechadokumentuLista().size() > 0) {
                for (Cechazapisu r: p.getCechadokumentuLista()) {
                    zapisyZCecha.addAll(pobierzstrony(r, p.getListawierszy()));
                }
            } else {
                for (Wiersz r : p.getListawierszy()) {
                    zapisyZCecha.addAll(pobierzpojedynczo(r));
                }
            }
        }
        int i = 1;
        for (CechaStronaWiersza p : zapisyZCecha) {
            p.setId(i++);
        }
        System.out.println("liczba "+zapisyZCecha.size());
        RequestContext.getCurrentInstance().update("formcechyzapisow");
    }

    private Collection<? extends CechaStronaWiersza> pobierzstrony(Cechazapisu r, List<Wiersz> listawierszy) {
        List<CechaStronaWiersza> lista = new ArrayList<>();
        for (Wiersz p : listawierszy) {
            if (p.getStronaWn() != null) {
                if (p.getStronaWn().getKonto().getBilansowewynikowe().equals("wynikowe")) {
                    lista.add(new CechaStronaWiersza(r, p.getStronaWn()));
                }
            }
            if (p.getStronaMa() != null) {
                if (p.getStronaMa().getKonto().getBilansowewynikowe().equals("wynikowe")) {
                    lista.add(new CechaStronaWiersza(r, p.getStronaMa()));
                }
            }
        }
        return lista;
    }

    private Collection<? extends CechaStronaWiersza> pobierzpojedynczo(Wiersz r) {
        List<CechaStronaWiersza> lista = new ArrayList<>();
        if (r.getStronaWn() != null) {
            if (r.getStronaWn().getKonto().getBilansowewynikowe().equals("wynikowe") && r.getStronaWn().getCechazapisuLista() != null) {
                for (Cechazapisu s : r.getStronaWn().getCechazapisuLista()) {
                    lista.add(new CechaStronaWiersza(s, r.getStronaWn()));
                }
            }
        }
        if (r.getStronaMa() != null) {
            if (r.getStronaMa().getKonto().getBilansowewynikowe().equals("wynikowe") && r.getStronaMa().getCechazapisuLista() != null) {
                for (Cechazapisu s : r.getStronaMa().getCechazapisuLista()) {
                    lista.add(new CechaStronaWiersza(s, r.getStronaMa()));
                }
            }
        }
        return lista;
    }
    
    public void odswiez() {
        wpisView.wpisAktualizuj();
        init();
    }

    public void sumujwybrane() {
        razem = 0.0;
        for (CechaStronaWiersza p : wybraneZapisy) {
            razem += p.getStronaWiersza().getKwotaPLN();
        }
    }
    //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public List<CechaStronaWiersza> getZapisyZCecha() {
        return zapisyZCecha;
    }
    
    public void setZapisyZCecha(List<CechaStronaWiersza> zapisyZCecha) {
        this.zapisyZCecha = zapisyZCecha;
    }

    public List<CechaStronaWiersza> getWybraneZapisy() {
        return wybraneZapisy;
    }

    public void setWybraneZapisy(List<CechaStronaWiersza> wybraneZapisy) {
        this.wybraneZapisy = wybraneZapisy;
    }

    public double getRazem() {
        return razem;
    }

    public void setRazem(double razem) {
        this.razem = razem;
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
