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

    public PozycjaRZiSView() {
        this.root = new TreeNodeExtended("root", null);
    }

    @PostConstruct
    private void init() {
        ArrayList<PozycjaRZiS> pozycje = new ArrayList<>();
        pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Przychody netto ze sprzedaży i zrównane z nimi, w tym:", true));
        pozycje.add(new PozycjaRZiS(2, "A.I", "I", 1, 1, "Przychody netto ze sprzedaży produktów", true));
        pozycje.add(new PozycjaRZiS(3, "A.I", "II", 1, 1, "Zmiana stanu produktów", true));
        pozycje.add(new PozycjaRZiS(4, "A.I", "III", 1, 1, "Koszt wytworzenia produktów na własne potrzeby jednostki", true));
        pozycje.add(new PozycjaRZiS(5, "A.I", "IV", 1, 1, "Przychody netto ze sprzeda􀄪y towarów i materiałów", true));
        pozycje.add(new PozycjaRZiS(6, "B", "B", 0, 0, "Koszty działalności operacyjnej", true));
        pozycje.add(new PozycjaRZiS(7, "B.I", "I", 6, 1, "Amortyzacja", true));
        pozycje.add(new PozycjaRZiS(8, "B.II", "II", 6, 1, "Zużycie materiałów i energii", true));
        pozycje.add(new PozycjaRZiS(9, "B.III", "III", 6, 1, "Usługi obce", true));
        pozycje.add(new PozycjaRZiS(10, "B.IV", "IV", 6, 1, "Podatki i  opłaty", true));
        pozycje.add(new PozycjaRZiS(11, "B.V", "V", 6, 1, "Wynagrodzenia", true));
        pozycje.add(new PozycjaRZiS(12, "B.I.1", "1", 7, 2, "amortyzacja kup", true));
        pozycje.add(new PozycjaRZiS(13, "B.I.2", "2", 7, 2, "amortyzacja nkup", true));
        createTreeNodesForElement(root, getElementTreeFromPlainList(pozycje));
        DefaultTreeNode node = new DefaultTreeNode();
        rozwin(root);
    }

    void createTreeNodesForElement(final TreeNode dmtn, final Map<String, ArrayList<PozycjaRZiS>> rzedy) {
       ArrayList<TreeNode> poprzednie = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ArrayList<TreeNode> nowe = new ArrayList<>();
            ArrayList<PozycjaRZiS> biezaca = rzedy.get(String.valueOf(i));
            for (PozycjaRZiS p : biezaca) {
                if (i == 0) {
                    TreeNode tmp = new DefaultTreeNode(p, root);
                     nowe.add(tmp);
                } else {
                    Iterator it = poprzednie.iterator();
                    while (it.hasNext()) {
                        TreeNode r = (TreeNode) it.next();
                        PozycjaRZiS parent = (PozycjaRZiS) r.getData();
                        if (parent.getLp() == p.getMacierzysty()) {
                            TreeNode tmp = new DefaultTreeNode(p, r);
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
    Map<String, ArrayList<PozycjaRZiS>> getElementTreeFromPlainList(ArrayList<PozycjaRZiS> pozycje) {
        Map<String, ArrayList<PozycjaRZiS>> rzedy = new LinkedHashMap<>(4);
        // builds a map of elements object returned from store
        for (int i = 0; i < 3; i++) {
            ArrayList<PozycjaRZiS> values = new ArrayList<>();
            for (PozycjaRZiS s : pozycje) {
                if (s.getLevel() == i) {
                    values.add(s);
                }
            }
            rzedy.put(String.valueOf(i), values);
        }
        return rzedy;
    }

    private void rozwin(TreeNode n) {
        boolean madzieci = n.getChildren().size() > 0;
        if (madzieci == true) {
            for (TreeNode o : n.getChildren()) {
                o.setExpanded(true);
                rozwin(o);
            }
        }
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
