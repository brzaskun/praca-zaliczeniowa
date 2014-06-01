/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.PlanKontFKBean;
import comparator.Kontozapisycomparator;
import daoFK.KontoDAOfk;
import daoFK.RozrachunekfkDAO;
import daoFK.ZestawienielisttransakcjiDAO;
import embeddablefk.RozrachunkiTransakcje;
import embeddablefk.Transakcja;
import embeddablefk.TreeNodeExtended;
import embeddablefk.WierszStronafkPK;
import entityfk.Konto;
import entityfk.Rozrachunekfk;
import entityfk.Zestawienielisttransakcji;
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
    private List<RozrachunkiTransakcje> listaRozrachunkow;
    private List<Transakcja> listaTransakcji;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private RozrachunekfkDAO rozrachunekfkDAO;
    @Inject private ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO;
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
        listaRozrachunkow = new ArrayList<>();
        TreeNodeExtended<Konto> node = (TreeNodeExtended<Konto>) event.getTreeNode();
        Konto wybraneKontoNode = (Konto) node.getData();
        List<Rozrachunekfk> listarozrachunkowkonto = rozrachunekfkDAO.findRozrachunkifkByPodatnikKonto(wpisView.getPodatnikWpisu(), wybraneKontoNode.getPelnynumer());
        if (!listarozrachunkowkonto.isEmpty()) {
            List<Transakcja> listatransakcjikonto = new ArrayList<>();
            for (Rozrachunekfk p : listarozrachunkowkonto) {
                WierszStronafkPK wierszStronafkPK = new WierszStronafkPK(p.getWierszStronafk().getWierszStronafkPK());
                Zestawienielisttransakcji zestawienielisttransakcji = zestawienielisttransakcjiDAO.findByKlucz(wierszStronafkPK);
                listatransakcjikonto.addAll(zestawienielisttransakcji.getListatransakcji());
                RozrachunkiTransakcje rozrachunkiTransakcje = new RozrachunkiTransakcje(p, listaTransakcji);
                listaRozrachunkow.add(rozrachunkiTransakcje);
            }
        }
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

    public List<RozrachunkiTransakcje> getListaRozrachunkow() {
        return listaRozrachunkow;
    }

    public void setListaRozrachunkow(List<RozrachunkiTransakcje> listaRozrachunkow) {
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
