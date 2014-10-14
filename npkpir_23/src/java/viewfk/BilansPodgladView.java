/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

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
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public BilansPodgladView() {
        this.root = new TreeNodeExtended("root", null);
    }

    @PostConstruct
    private void init(){
        rozwinwszystkie();
    }
    
//    public void generujBO() {
//        BOFKBean.resetujBO(kontoDAO, wpisView.getPodatnikWpisu());
//        BOFKBean.generujBO(kontoDAO, stronaWierszaDAO, wpisView);
//        this.rozwinwszystkie();
//        Msg.msg("Wygenerowano BO");
//    }
    
    //tworzy nody z bazy danych dla tablicy nodow plan kont
    private void getNodes(){
        this.root = new TreeNodeExtended("root", null);
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findWszystkieKontaBilansowePodatnika(wpisView.getPodatnikWpisu()));
        root.createTreeNodesForElement(kontadlanodes);
        
    }
       
        
    public void rozwinwszystkie(){
        getNodes();
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findWszystkieKontaBilansowePodatnika(wpisView.getPodatnikWpisu()));
        level = root.ustaldepthDT(kontadlanodes)-1;
        root.expandAll();
        Msg.msg("Rozwinięto maksymalnie");
    }  
    
    public void rozwin(){
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findWszystkieKontaBilansowePodatnika(wpisView.getPodatnikWpisu()));
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
