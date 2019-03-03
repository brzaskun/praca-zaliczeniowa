/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.TransakcjaDAO;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
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
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RozrachunkiPrzegladKontrahenciView implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private List<Konto> listaKontRozrachunkowych;
    //private List<RozrachunkiTransakcje> listaRozrachunkow;
    private List<StronaWiersza> stronyWiersza;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private StronaWierszaDAO stronaWierszaDAO;
    @Inject private Konto wybranekonto;
    //@Inject private RozrachunekfkDAO rozrachunekfkDAO;
    @Inject private TransakcjaDAO transakcjaDAO;
    private TreeNodeExtended<Konto> root;
    private int levelBiezacy = 0;
    private int levelMaksymalny = 0;
    @Inject private TreeNodeExtended<Konto> wybranekontoNode;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String wybranaWalutaDlaKont;
    private String coWyswietlacRozrachunkiPrzeglad;

    public RozrachunkiPrzegladKontrahenciView() {
         E.m(this);
        listaKontRozrachunkowych = Collections.synchronizedList(new ArrayList<>());
        //listaRozrachunkow = Collections.synchronizedList(new ArrayList<>());
        stronyWiersza = Collections.synchronizedList(new ArrayList<>());
        wybranaWalutaDlaKont = "wszystkie";
    }
    
    @PostConstruct
    private void init() {E.m(this);
        listaKontRozrachunkowych.addAll(kontoDAOfk.findKontaRozrachunkoweWszystkie(wpisView));
        zweryfikujobecnosczapisow();
        if (listaKontRozrachunkowych != null && listaKontRozrachunkowych.isEmpty()==false) {
            wybranekonto = listaKontRozrachunkowych.get(0);
            root = rootInit(listaKontRozrachunkowych);
            //rozwinwszystkie(root);
        }
    }

    private void zweryfikujobecnosczapisow() {
        List<StronaWiersza> wierszezzapisami = stronaWierszaDAO.findStronaByPodatnikRokBilans(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Set<Konto> zawartekontawzapisach = new HashSet<>();
        for (StronaWiersza p : wierszezzapisami) {
            if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                zawartekontawzapisach.add(p.getKonto());
            }
        }
        for (Iterator<Konto> it = listaKontRozrachunkowych.iterator(); it.hasNext(); ) {
                Konto p = it.next();
                if (p.isMapotomkow() == false && !zawartekontawzapisach.contains(p)) {
                    it.remove();
                }
        }
    }
    
    private TreeNodeExtended<Konto> rootInit(List<Konto> wykazKont) {
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
        stronyWiersza = Collections.synchronizedList(new ArrayList<>());
        TreeNodeExtended<Konto> node = (TreeNodeExtended<Konto>) event.getTreeNode();
        wybranekonto = (Konto) node.getData();
        if (wybranaWalutaDlaKont.equals("wszystkie")) {
            stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
        } else {
            stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
        }
        filtrrozrachunkow();
    }
    
    public void pobierzZapisyZmianaWaluty() {
        if (wybranaWalutaDlaKont.equals("wszystkie")) {
            stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
        } else {
            stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
        }
        filtrrozrachunkow();
    }
    
    public void pobierzZapisyZmianaZakresu() {
        if (wybranaWalutaDlaKont.equals("wszystkie")) {
            stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
        } else {
            stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
        }
       filtrrozrachunkow();
    }
    
    private void filtrrozrachunkow() {
        if (coWyswietlacRozrachunkiPrzeglad != null) {
        for (Iterator<StronaWiersza> p = stronyWiersza.iterator(); p.hasNext();) {
             switch (coWyswietlacRozrachunkiPrzeglad) {
                 case "rozliczone":
                     if (p.next().getPozostalo() != 0) {
                         p.remove();
                     }
                     break;
                 case "częściowo":
                     StronaWiersza px = p.next();
                     if (px.getPozostalo() == 0 || px.getRozliczono() == 0) {
                         p.remove();
                     }
                     break;
                 case "nowe":
                     if (p.next().getRozliczono() != 0) {
                         p.remove();
                     }
                     break;
                 default:
                     p.next();
                     break;
             }
         } 
        }
    }
    
    public List<Konto> complete(String query) {  
         List<Konto> results = Collections.synchronizedList(new ArrayList<>());
         try{
             String q = query.substring(0,1);
             int i = Integer.parseInt(q);
             for(Konto p : listaKontRozrachunkowych) {
                 if(query.length()==4&&!query.contains("-")){
                     //wstawia - do ciagu konta
                     query = query.substring(0,3)+"-"+query.substring(3,4);
                 }
                 if(p.getPelnynumer().startsWith(query)) {
                     results.add(p);
                 }
             }
         } catch (Exception e){
             for(Konto p : listaKontRozrachunkowych) {
                 if(p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                     results.add(p);
                 }
             }
         }
         return results;
     }
    
    public void pobierzZapisyNaKoncieNodeUnselect(NodeUnselectEvent event) {
        stronyWiersza.clear();
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

    public List<StronaWiersza> getStronyWiersza() {
        return stronyWiersza;
    }

    public void setStronyWiersza(List<StronaWiersza> stronyWiersza) {
        this.stronyWiersza = stronyWiersza;
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
