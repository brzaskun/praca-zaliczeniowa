/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import daoFK.KontoZapisyFKDAO;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.Kontozapisy;
import java.io.Serializable;
import java.util.ArrayList;
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
public class BilansPodgladView  implements Serializable{
    private static int level = 0;

    @Inject private KontoDAOfk kontoDAO;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    private TreeNodeExtended<Konto> root;
    private TreeNodeExtended<Konto> selectednode;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public BilansPodgladView() {
        this.root = new TreeNodeExtended("root", null);
    }

    @PostConstruct
    private void init(){
        rozwinwszystkie();
    }
    
    public void generujBO() {
        ArrayList<Kontozapisy> kontozapisy = new ArrayList<>();
        kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKontoPodatnik(wpisView.getPodatnikWpisu(), "000"));
        resetujBO();
        ArrayList<Konto> konta = new ArrayList<>();
        konta.addAll(kontoDAO.findAll());
        for (Kontozapisy p : kontozapisy) {
            for (Konto r : konta) {
                if (p.getKontoprzeciwstawne().equals(r.getPelnynumer())&&p.getKwotawn()>0) {
                    r.setBoMa(r.getBoMa()+p.getKwotawn());
                    r.setBlokada(true);
                    kontoDAO.edit(r);
                    break;
                } else if (p.getKontoprzeciwstawne().equals(r.getPelnynumer())&&p.getKwotama()>0) {
                    r.setBoWn(r.getBoWn()+p.getKwotama());
                    r.setBlokada(true);
                    kontoDAO.edit(r);
                    break;
                }
            }
            
        }
        rozwinwszystkie();
    }
    
    private void resetujBO() {
        ArrayList<Konto> konta = new ArrayList<>();
        konta.addAll(kontoDAO.findAll());
        for (Konto p: konta) {
            p.setBoWn(0.0);
            p.setBoMa(0.0);
            p.setBlokada(false);
            kontoDAO.edit(p);
        }
    }
    
    
    //tworzy nody z bazy danych dla tablicy nodow plan kont
    private void getNodes(){
        this.root = new TreeNodeExtended("root", null);
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findAll());
        root.createTreeNodesForElement(kontadlanodes);
        
    }
       
        
    public void rozwinwszystkie(){
        getNodes();
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findAll());
        level = root.ustaldepthDT(kontadlanodes)-1;
        root.expandAll();
    }  
    
    public void rozwin(){
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findAll());
        int maxpoziom = root.ustaldepthDT(kontadlanodes);
        if (level < --maxpoziom) {
            root.expandLevel(level++);
        }
    }  
    
    public void zwinwszystkie(){
        getNodes();
        root.foldAll();
        level = 0;
    }  
    public void zwin(){
        root.foldLevel(--level);
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
    

    
}
