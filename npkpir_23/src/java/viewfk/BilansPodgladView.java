/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.KontocomparatorByKwota;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.model.TreeNode;
import pdffk.PdfBilansPodgladKonta;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class BilansPodgladView  implements Serializable{
    private static final long serialVersionUID = 1L;
    private static int level = 0;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject private KontoDAOfk kontoDAO;
    private TreeNodeExtended<Konto> root;
    private TreeNodeExtended<Konto> selectednode;
    private TreeNode[] selectednodes;
    private double sumawn;
    private double sumama;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private boolean sortujwgwartosci;

    public BilansPodgladView() {
         E.m(this);
        this.root = new TreeNodeExtended("root", null);
    }

    
    public void init(){
        rozwinwszystkie();
    }
    
    
    //tworzy nody z bazy danych dla tablicy nodow plan kont
    private void getNodes(){
        this.root = new TreeNodeExtended("root", null);
        List<Konto> listakont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        level = root.ustaldepthDT(listakont)-1;
        //podsumujkonta(listakont, level);
        sumakont(listakont);
        usunzerowe(listakont);
        root.createTreeNodesForElement(listakont);
        
    }
       
        
    public void rozwinwszystkie(){
        try {
            getNodes();
            root.expandAll();
            Msg.msg("Rozwinięto maksymalnie");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Brak kont bilansowych u podatnika");
        }
    }
    
    private void podsumujkonta(List<Konto> listakont, int level) {
        for (int i = level; i > -1 ; i--) {
            for (Konto p : listakont) {
                if (p.getLevel()==i) {
                    Konto macierzyste = znajdzmacierzysty(p.getMacierzysty(), listakont);
                    if (macierzyste != null) {
                        macierzyste.setBoWn(macierzyste.getBoWn()+p.getBoWn());
                        macierzyste.setBoMa(macierzyste.getBoMa()+p.getBoMa());
                    }
                }
            }
        }
    }
    
    private void sumakont(List<Konto> listakont) {
         sumawn = 0.0;
            sumama = 0.0;
            for (Konto r : listakont) {
                if (r.getLevel()==0) {
                    sumawn += r.getBoWn();
                    sumama += r.getBoMa();
                }
            }
            sumawn = Z.z(sumawn);
            sumama = Z.z(sumama);
    }
    
    private void usunzerowe(List<Konto> listakont) {
        for (Iterator<Konto> it = listakont.iterator(); it.hasNext();) {
            Konto p = (Konto) it.next();
            if (p.getBoWn() == 0 && p.getBoMa() == 0) {
                it.remove();
            }
        }
    }
    
    private Konto znajdzmacierzysty(int macierzysty, List<Konto> listakont) {
        for (Konto p : listakont) {
            if (p.getId() == macierzysty) {
                return p;
            }
        }
        return null;
    }
    
    public void rozwin(){
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findWszystkieKontaBilansowePodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt()));
        int maxpoziom = root.ustaldepthDT(kontadlanodes);
        if (level < --maxpoziom) {
            root.expandLevel(level++);
        }
    }  
    
    public void zwinwszystkie(){
        getNodes();
        root.foldAll();
        level = 0;
        Msg.msg("Zwinięto maksymalnie");
    }  
    public void zwin(){
        root.foldLevel(--level);
    }  
    
    public void drukuj() {
        if (selectednodes != null && selectednodes.length > 0) {
            List<Konto> w = new ArrayList<Konto>();
            for (TreeNode p : selectednodes) {
                Konto k = (Konto) p.getData();
                if (!w.contains(k)) {
                    List<Konto> tmp = new ArrayList<Konto>();
                    ((TreeNodeExtended) p).getChildrenTree(new ArrayList<TreeNodeExtended>(), tmp);
                    w.add(k);
                    w.addAll(tmp);
                }
            }
            if (sortujwgwartosci) {
                sortujliste(w);
            }
            dodajwierszsumyAll(w);
            PdfBilansPodgladKonta.drukujBilansPodgladKonta(w, wpisView);
        } else {
            List<Konto> w = new ArrayList<Konto>();
            root.getChildrenTree(new ArrayList<TreeNodeExtended>(), w);
            if (sortujwgwartosci) {
                sortujliste(w);
            }
            dodajwierszsumyAll(w);
            PdfBilansPodgladKonta.drukujBilansPodgladKonta(w, wpisView);
        }
    }
    
   
    
    public void drukujAnal(boolean analityka) {
        if (selectednodes != null && selectednodes.length > 0) {
            List<Konto> w = new ArrayList<Konto>();
            for (TreeNode p : selectednodes) {
                Konto k = (Konto) p.getData();
                if (!w.contains(k)) {
                    List<Konto> tmp = new ArrayList<Konto>();
                    ((TreeNodeExtended) p).getChildrenTree(new ArrayList<TreeNodeExtended>(), tmp);
                    w.add(k);
                    w.addAll(tmp);
                }
            }
            modyfikujlistedowydruku(analityka, w);
            dodajwierszsumy(w);
            if (sortujwgwartosci) {
                sortujliste(w);
            }
            PdfBilansPodgladKonta.drukujBilansPodgladKonta(w, wpisView);
        } else {
            List<Konto> w = new ArrayList<Konto>();
            root.getChildrenTree(new ArrayList<TreeNodeExtended>(), w);
            modyfikujlistedowydruku(analityka, w);
            dodajwierszsumy(w);
            if (sortujwgwartosci) {
                sortujliste(w);
            }
            PdfBilansPodgladKonta.drukujBilansPodgladKonta(w, wpisView);
        }
    }
    
    private void dodajwierszsumyAll(List<Konto> w) {
        double wn = 0.0;
        double ma = 0.0;
        for (Konto p : w) {
            if (p.getMacierzysty()==0) {
                wn += p.getBoWn();
                ma += p.getBoMa();
            }
        }
        w.add(new Konto("podsumowanie", Z.z(wn), Z.z(ma)));
    }
     
    private void dodajwierszsumy(List<Konto> w) {
        double wn = 0.0;
        double ma = 0.0;
        for (Konto p : w) {
            wn += p.getBoWn();
            ma += p.getBoMa();
        }
        w.add(new Konto("podsumowanie", Z.z(wn), Z.z(ma)));
    }
    
    private void modyfikujlistedowydruku(boolean analityka, List<Konto> w) {
        if (analityka ==  true) {
                for (Iterator<Konto> it = w.iterator(); it.hasNext();) {
                    if (it.next().isMapotomkow() == true) {
                        it.remove();
                    }
                }
            } else {
                for (Iterator<Konto> it = w.iterator(); it.hasNext();) {
                    if (!it.next().getMacierzyste().equals("0")) {
                        it.remove();
                    }
                }
            }
    }
   
    private void sortujliste(List<Konto> w) {
        Collections.sort(w, new KontocomparatorByKwota());
    }
     
    public TreeNodeExtended<Konto> getSelectednode() {
        return selectednode;
    }

    public void setSelectednode(TreeNodeExtended<Konto> selectednode) {
        this.selectednode = selectednode;
    }

    public TreeNodeExtended getRoot() {
        return root;
    }

    public void setRoot(TreeNodeExtended root) {
        this.root = root;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public double getSumawn() {
        return sumawn;
    }

    public void setSumawn(double sumawn) {
        this.sumawn = sumawn;
    }

    public double getSumama() {
        return sumama;
    }

    public void setSumama(double sumama) {
        this.sumama = sumama;
    }

    public TreeNode[] getSelectednodes() {
        return selectednodes;
    }

    public void setSelectednodes(TreeNode[] selectednodes) {
        this.selectednodes = selectednodes;
    }

    public boolean isSortujwgwartosci() {
        return sortujwgwartosci;
    }

    public void setSortujwgwartosci(boolean sortujwgwartosci) {
        this.sortujwgwartosci = sortujwgwartosci;
    }

  

    
    

    
}
