/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import embeddablefk.PozycjaRZiS;
import embeddablefk.TreeNodeExtended;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.swing.tree.DefaultMutableTreeNode;
import org.primefaces.component.tree.Tree;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PozycjaRZiSView implements Serializable {

    private TreeNodeExtended root;
    private TreeNode[] selectedNodes;
    private PozycjaRZiS selected;
    private ArrayList<TreeNodeExtended> finallNodes;

    public PozycjaRZiSView() {
        this.root = new TreeNodeExtended("root", null);
        this.finallNodes = new ArrayList<TreeNodeExtended>();
    }

    @PostConstruct
    private void init() {
        ArrayList<PozycjaRZiS> pozycje = new ArrayList<>();
        pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Przychody netto ze sprzedaży i zrównane z nimi, w tym:", true));
        pozycje.add(new PozycjaRZiS(2, "A.I", "I", 1, 1, "Przychody netto ze sprzedaży produktów", true, 100.0));
        pozycje.add(new PozycjaRZiS(3, "A.I", "II", 1, 1, "Zmiana stanu produktów", true, 200.0));
        pozycje.add(new PozycjaRZiS(4, "A.I", "III", 1, 1, "Koszt wytworzenia produktów na własne potrzeby jednostki", true, 300.0));
        pozycje.add(new PozycjaRZiS(5, "A.I", "IV", 1, 1, "Przychody netto ze sprzedaży towarów i materiałów", true, 400.0));
        pozycje.add(new PozycjaRZiS(6, "B", "B", 0, 0, "Koszty działalności operacyjnej", true));
        pozycje.add(new PozycjaRZiS(7, "B.I", "I", 6, 1, "Amortyzacja", true));
        pozycje.add(new PozycjaRZiS(8, "B.II", "II", 6, 1, "Zużycie materiałów i energii", true, 600.0));
        pozycje.add(new PozycjaRZiS(9, "B.III", "III", 6, 1, "Usługi obce", true, 500.0));
        pozycje.add(new PozycjaRZiS(10, "B.IV", "IV", 6, 1, "Podatki i  opłaty", true, 400.0));
        pozycje.add(new PozycjaRZiS(11, "B.V", "V", 6, 1, "Wynagrodzenia", true, 300.0));
        pozycje.add(new PozycjaRZiS(12, "B.I.1", "1", 7, 2, "amortyzacja kup", true, 150.0));
        pozycje.add(new PozycjaRZiS(13, "B.I.2", "2", 7, 2, "amortyzacja nkup", true));
        pozycje.add(new PozycjaRZiS(14, "B.I.2.a)", "a)", 13, 3, "bobopo", true, 33.0));
        int depth = ustaldepth(pozycje);
        createTreeNodesForElement(root, getElementTreeFromPlainList(pozycje, depth), depth);
        root.returnFinallChildren(finallNodes);
        root.sumNodes(finallNodes);
        root.expandAll();
    }

    void createTreeNodesForElement(final TreeNode dmtn, final Map<String, ArrayList<PozycjaRZiS>> rzedy, int depth) {
       ArrayList<TreeNodeExtended> poprzednie = new ArrayList<>();
        for (int i = 0; i < depth; i++) {
            ArrayList<TreeNodeExtended> nowe = new ArrayList<>();
            ArrayList<PozycjaRZiS> biezaca = rzedy.get(String.valueOf(i));
            for (PozycjaRZiS p : biezaca) {
                if (i == 0) {
                    TreeNodeExtended tmp = new TreeNodeExtended(p, root);
                     nowe.add(tmp);
                } else {
                    Iterator it = poprzednie.iterator();
                    while (it.hasNext()) {
                        TreeNodeExtended r = (TreeNodeExtended) it.next();
                        PozycjaRZiS parent = (PozycjaRZiS) r.getData();
                        if (parent.getLp() == p.getMacierzysty()) {
                            TreeNodeExtended tmp = new TreeNodeExtended(p, r);
                            nowe.add(tmp);
                        }
                    }
                }
            }
            poprzednie.clear();
            poprzednie.addAll(nowe);
        }
    }
    
    
    //przeksztalca tresc tabeli w elementy do drzewa
    Map<String, ArrayList<PozycjaRZiS>> getElementTreeFromPlainList(ArrayList<PozycjaRZiS> pozycje, int depth) {
        Map<String, ArrayList<PozycjaRZiS>> rzedy = new LinkedHashMap<>(depth);
        // builds a map of elements object returned from store
        for (int i = 0; i < depth; i++) {
            ArrayList<PozycjaRZiS> values = new ArrayList<>();
            for (PozycjaRZiS s : pozycje) {
                if (s.getLevel() == i) {
                    values.add(s);
                }
            }
            if (values.size()>0) {
                rzedy.put(String.valueOf(i), values);
            }
        }
        return rzedy;
    }
    
    private int ustaldepth(ArrayList<PozycjaRZiS> pozycje) {
        int depth = 0;
        for (PozycjaRZiS p : pozycje) {
            if (depth < p.getLevel()) {
                depth = p.getLevel();
            }
        }
        return depth+1;
    }

        
    private void sumujNodes() {
        
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public TreeNodeExtended getRoot() {
        return root;
    }

    public void setRoot(TreeNodeExtended root) {
        this.root = root;
    }
   
    public PozycjaRZiS getSelected() {
        return selected;
    }

    public void setSelected(PozycjaRZiS selected) {
        this.selected = selected;
    }

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }
    //</editor-fold>
}
