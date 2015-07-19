/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
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

    public BilansPodgladView() {
        this.root = new TreeNodeExtended("root", null);
    }

    
    public void init(){
        rozwinwszystkie();
    }
    
    
    //tworzy nody z bazy danych dla tablicy nodow plan kont
    private void getNodes(){
        this.root = new TreeNodeExtended("root", null);
        List<Konto> listakont = kontoDAO.findWszystkieKontaBilansowePodatnika(wpisView);
        level = root.ustaldepthDT(listakont)-1;
        podsumujkonta(listakont, level);
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
        kontadlanodes.addAll(kontoDAO.findWszystkieKontaBilansowePodatnika(wpisView));
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
        System.out.println("");
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
            System.out.println("1");
            PdfBilansPodgladKonta.drukujBilansPodgladKonta(w, wpisView);
        } else {
            List<Konto> w = new ArrayList<Konto>();
            root.getChildrenTree(new ArrayList<TreeNodeExtended>(), w);
            System.out.println("2");
            PdfBilansPodgladKonta.drukujBilansPodgladKonta(w, wpisView);
        }
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

    
    

    
}
