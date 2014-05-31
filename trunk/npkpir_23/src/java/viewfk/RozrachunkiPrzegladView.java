/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.PlanKontFKBean;
import comparator.Kontozapisycomparator;
import daoFK.KontoDAOfk;
import embeddablefk.Transakcja;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.Rozrachunekfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.NodeSelectEvent;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RozrachunkiPrzegladView implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private List<Konto> listaKontRozrachunkowych;
    private List<Rozrachunekfk> listaRozrachunkow;
    private List<Transakcja> listaTransakcji;
    @Inject private KontoDAOfk kontoDAOfk;
    private TreeNodeExtended<Konto> root;
    private int levelBiezacy = 0;
    private int levelMaksymalny = 0;
    @Inject private TreeNodeExtended<Konto> wybranekontoNode;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public RozrachunkiPrzegladView() {
        listaKontRozrachunkowych = new ArrayList<>();
        listaRozrachunkow = new ArrayList<>();
        listaTransakcji = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
        listaKontRozrachunkowych.addAll(kontoDAOfk.findKontaRozrachunkowe(wpisView.getPodatnikWpisu()));
        root = rootInit(listaKontRozrachunkowych);
        rozwinwszystkie(root);
    }

    private TreeNodeExtended<Konto> rootInit(List<Konto> wykazKont) {
        Iterator it = wykazKont.iterator();
        while (it.hasNext()) {
            Konto konto = (Konto) it.next();
            if (konto.getNrkonta().equals("0")) {
                it.remove();
            }
        }
        TreeNodeExtended<Konto> r = new TreeNodeExtended("root", null);
        if (!wykazKont.isEmpty()) {
            r.createTreeNodesForElement(wykazKont);
        }
        return r;
    }
    //zostawilem bo duzo zmiennych malo linijek
    private int ustalLevel(TreeNodeExtended<Konto> r) {
        int level = 0;
        levelMaksymalny = r.ustaldepthDT(listaKontRozrachunkowych);
        return level;
    }
    
    public void rozwinwszystkie(TreeNodeExtended<Konto> r) {
        if (r.getChildCount() > 0) {
            levelBiezacy = ustalLevel(r);
            r.expandAll();
        }
    }
    
    public void zwinwszystkie(TreeNodeExtended<Konto> r) {
        r.foldAll();
        levelBiezacy = 0;
    }
    
    public void pobierzZapisyNaKoncieNode(NodeSelectEvent event) {
        TreeNodeExtended<Konto> node = (TreeNodeExtended<Konto>) event.getTreeNode();
        Konto wybraneKontoNode = (Konto) node.getData();
        wybranekonto = serialclone.SerialClone.clone(wybraneKontoNode);
        kontozapisy = new ArrayList<>();
            List<Konto> kontapotomne = new ArrayList<>();
            if (wybraneKontoNode.isMapotomkow() == true) {
                List<Konto> kontamacierzyste = new ArrayList<>();
                kontamacierzyste.addAll(pobierzpotomkow(wybraneKontoNode));
                //tu jest ten loop ala TreeeNode schodzi w dol potomnych i wyszukuje ich potomnych
                while (kontamacierzyste.size() > 0) {
                    znajdzkontazpotomkami(kontapotomne, kontamacierzyste);
                }
                for (Konto p : kontapotomne) {
                    kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKontoPodatnik(wpisView.getPodatnikWpisu(), p.getPelnynumer(), wybranaWalutaDlaKont));
                }
                Collections.sort(kontozapisy, new Kontozapisycomparator());

            } else {
                kontozapisy = kontoZapisyFKDAO.findZapisyKontoPodatnik(wpisView.getPodatnikWpisu(), wybraneKontoNode.getPelnynumer(), wybranaWalutaDlaKont);
            }
            sumazapisow();
            //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
            System.out.println("odnalazlem");
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public List<Konto> getListaKontRozrachunkowych() {
        return listaKontRozrachunkowych;
    }

    public void setListaKontRozrachunkowych(List<Konto> listaKontRozrachunkowych) {
        this.listaKontRozrachunkowych = listaKontRozrachunkowych;
    }
    
    public List<Rozrachunekfk> getListaRozrachunkow() {
        return listaRozrachunkow;
    }

    public void setListaRozrachunkow(List<Rozrachunekfk> listaRozrachunkow) {
        this.listaRozrachunkow = listaRozrachunkow;
    }

    public List<Transakcja> getListaTransakcji() {
        return listaTransakcji;
    }

    public void setListaTransakcji(List<Transakcja> listaTransakcji) {
        this.listaTransakcji = listaTransakcji;
    }

    public TreeNodeExtended<Konto> getRoot() {
        return root;
    }

    public void setRoot(TreeNodeExtended<Konto> root) {
        this.root = root;
    }

    public TreeNodeExtended<Konto> getWybranekontoNode() {
        return wybranekontoNode;
    }

    public void setWybranekontoNode(TreeNodeExtended<Konto> wybranekontoNode) {
        this.wybranekontoNode = wybranekontoNode;
    }
    
    
    
    
}
