/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.DokFKTransakcjeBean;
import daoFK.KontoDAOfk;
import daoFK.RozrachunekfkDAO;
import daoFK.TransakcjaDAO;
import daoFK.ZestawienielisttransakcjiDAO;
import embeddablefk.RozrachunkiTransakcje;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.Rozrachunekfk;
import entityfk.Transakcja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
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
    @Inject private Konto wybranekonto;
    @Inject private RozrachunekfkDAO rozrachunekfkDAO;
    @Inject private ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO;
    @Inject private TransakcjaDAO transakcjaDAO;
    private TreeNodeExtended<Konto> root;
    private int levelBiezacy = 0;
    private int levelMaksymalny = 0;
    @Inject private TreeNodeExtended<Konto> wybranekontoNode;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String wybranaWalutaDlaKont;
    private String coWyswietlacRozrachunkiPrzeglad;

    public RozrachunkiPrzegladView() {
        listaKontRozrachunkowych = new ArrayList<>();
        listaRozrachunkow = new ArrayList<>();
        listaTransakcji = new ArrayList<>();
        wybranaWalutaDlaKont = "PLN";
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
        wybranekonto = (Konto) node.getData();
        List<Rozrachunekfk> listarozrachunkowkonto = rozrachunekfkDAO.findRozrachunkifkByPodatnikKontoWaluta(wpisView.getPodatnikWpisu(), wybranekonto.getPelnynumer(), wybranaWalutaDlaKont);
        if (!listarozrachunkowkonto.isEmpty()) {
            for (Rozrachunekfk p : listarozrachunkowkonto) {
                List<Transakcja> listatransakcjikonto = new ArrayList<>();
                listatransakcjikonto.addAll(DokFKTransakcjeBean.pobierzbiezaceTransakcjePrzegladRozrachunkow(transakcjaDAO, p));
                RozrachunkiTransakcje rozrachunkiTransakcje = new RozrachunkiTransakcje(p, listatransakcjikonto);
                listaRozrachunkow.add(rozrachunkiTransakcje);
            }
        }
    }
    
    public void pobierzZapisyZmianaWaluty() {
        Konto wybraneKontoNode = serialclone.SerialClone.clone(wybranekonto);
        listaRozrachunkow = new ArrayList<>();
        List<Rozrachunekfk> listarozrachunkowkonto = rozrachunekfkDAO.findRozrachunkifkByPodatnikKontoWaluta(wpisView.getPodatnikWpisu(), wybraneKontoNode.getPelnynumer(), wybranaWalutaDlaKont);
        if (!listarozrachunkowkonto.isEmpty()) {
            for (Rozrachunekfk p : listarozrachunkowkonto) {
                List<Transakcja> listatransakcjikonto = new ArrayList<>();
                listatransakcjikonto.addAll(DokFKTransakcjeBean.pobierzbiezaceTransakcjePrzegladRozrachunkow(transakcjaDAO, p));
                RozrachunkiTransakcje rozrachunkiTransakcje = new RozrachunkiTransakcje(p, listatransakcjikonto);
                listaRozrachunkow.add(rozrachunkiTransakcje);
            }
        }
    }
    
    public void pobierzZapisyZmianaZakresu() {
        Konto wybraneKontoNode = serialclone.SerialClone.clone(wybranekonto);
        listaRozrachunkow = new ArrayList<>();
        List<Rozrachunekfk> listarozrachunkowkonto = rozrachunekfkDAO.findRozrachunkifkByPodatnikKontoWalutaSelekcja(wpisView.getPodatnikWpisu(), wybraneKontoNode.getPelnynumer(), wybranaWalutaDlaKont, coWyswietlacRozrachunkiPrzeglad);
        if (!listarozrachunkowkonto.isEmpty()) {
            for (Rozrachunekfk p : listarozrachunkowkonto) {
                List<Transakcja> listatransakcjikonto = new ArrayList<>();
                listatransakcjikonto.addAll(DokFKTransakcjeBean.pobierzbiezaceTransakcjePrzegladRozrachunkow(transakcjaDAO, p));
                RozrachunkiTransakcje rozrachunkiTransakcje = new RozrachunkiTransakcje(p, listatransakcjikonto);
                listaRozrachunkow.add(rozrachunkiTransakcje);
            }
        }
    }
    
    
    
    public void pobierzZapisyNaKoncieNodeUnselect(NodeUnselectEvent event) {
        listaRozrachunkow.clear();
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

    public Konto getWybranekonto() {
        return wybranekonto;
    }

    public void setWybranekonto(Konto wybranekonto) {
        this.wybranekonto = wybranekonto;
    }

    public String getWybranaWalutaDlaKont() {
        return wybranaWalutaDlaKont;
    }

    public void setWybranaWalutaDlaKont(String wybranaWalutaDlaKont) {
        this.wybranaWalutaDlaKont = wybranaWalutaDlaKont;
    }

    public String getCoWyswietlacRozrachunkiPrzeglad() {
        return coWyswietlacRozrachunkiPrzeglad;
    }

    public void setCoWyswietlacRozrachunkiPrzeglad(String coWyswietlacRozrachunkiPrzeglad) {
        this.coWyswietlacRozrachunkiPrzeglad = coWyswietlacRozrachunkiPrzeglad;
    }
    
    
    
    
    
}
