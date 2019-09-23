/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Dokfkcomparator;
import comparator.Rodzajedokcomparator;
import daoFK.DokDAOfk;
import entity.Rodzajedok;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import view.WpisView;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkGuestView implements Serializable {

    private static final long serialVersionUID = 1L;
    private String wybranakategoriadok;
    private List<Dokfk> wykazZaksiegowanychDokumentow;
    private List dokumentypodatnikazestawienie;
    private List<Dokfk> filteredValue;
    private Dokfk selected;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    
    @PostConstruct
    public void init() {
        wybranakategoriadok = "wszystkie";
        wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        if (wykazZaksiegowanychDokumentow != null && wykazZaksiegowanychDokumentow.size() > 0) {
            for (Iterator<Dokfk> it = wykazZaksiegowanychDokumentow.iterator(); it.hasNext();) {
                Dokfk r = (Dokfk) it.next();
                if (r.isImportowany() == true) {
                    it.remove();
                }
            }
        }
        dokumentypodatnikazestawienie = znajdzrodzajedokaktualne(wykazZaksiegowanychDokumentow);
        Collections.sort(wykazZaksiegowanychDokumentow, new Dokfkcomparator());
        filteredValue = null;
    }
    
    
     public void odswiezzaksiegowane() {
        if (wybranakategoriadok == null) {
            wybranakategoriadok = "wszystkie";
        }
        if (wybranakategoriadok.equals("wszystkie")) {
            if (wpisView.getMiesiacWpisu().equals("CR")) {
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRok(wpisView);
            } else {
                wpisView.wpisAktualizuj();
                wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
            }
        } else if (wpisView.getMiesiacWpisu().equals("CR")) {
            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wybranakategoriadok);
        } else {
            wpisView.wpisAktualizuj();
            wykazZaksiegowanychDokumentow = dokDAOfk.findDokfkPodatnikRokMcKategoria(wpisView, wybranakategoriadok);
        }
        if (wykazZaksiegowanychDokumentow != null && wykazZaksiegowanychDokumentow.size() > 0) {
            for (Iterator<Dokfk> it = wykazZaksiegowanychDokumentow.iterator(); it.hasNext();) {
                Dokfk r = (Dokfk) it.next();
                if (r.isImportowany() == true) {
                    it.remove();
                }
            }
        }
        Collections.sort(wykazZaksiegowanychDokumentow, new Dokfkcomparator());
        filteredValue = null;
    }
     
      private List znajdzrodzajedokaktualne(List<Dokfk> wykazZaksiegowanychDokumentow) {
        Set<Rodzajedok> lista = new HashSet<>();
        for (Dokfk p : wykazZaksiegowanychDokumentow) {
            lista.add(p.getRodzajedok());
        }
        List<Rodzajedok> t = new ArrayList<>(lista);
        Collections.sort(t, new Rodzajedokcomparator());
        return t;
    }

    public String getWybranakategoriadok() {
        return wybranakategoriadok;
    }

    public void setWybranakategoriadok(String wybranakategoriadok) {
        this.wybranakategoriadok = wybranakategoriadok;
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<Dokfk> getWykazZaksiegowanychDokumentow() {
        return wykazZaksiegowanychDokumentow;
    }
    
    public void setWykazZaksiegowanychDokumentow(List<Dokfk> wykazZaksiegowanychDokumentow) {
        this.wykazZaksiegowanychDokumentow = wykazZaksiegowanychDokumentow;
    }

    public Dokfk getSelected() {
        return selected;
    }

    public void setSelected(Dokfk selected) {
        this.selected = selected;
    }
    
    public List getDokumentypodatnikazestawienie() {
        return dokumentypodatnikazestawienie;
    }
    
    public void setDokumentypodatnikazestawienie(List dokumentypodatnikazestawienie) {
        this.dokumentypodatnikazestawienie = dokumentypodatnikazestawienie;
    }
    
    public List<Dokfk> getFilteredValue() {
        return filteredValue;
    }
    
    public void setFilteredValue(List<Dokfk> filteredValue) {
        this.filteredValue = filteredValue;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
//</editor-fold>
     
}
