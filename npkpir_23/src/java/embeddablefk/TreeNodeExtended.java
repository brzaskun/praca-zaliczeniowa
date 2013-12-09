/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import java.io.Serializable;
import java.util.ArrayList;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Osito
 */
public class TreeNodeExtended extends DefaultTreeNode implements Serializable{
    private static final long serialVersionUID = 1L;

    public TreeNodeExtended() {
        super();
    }

    public TreeNodeExtended(Object object, TreeNode node) {
        super(object,node);
    }
    
    public void returnFinallChildren(ArrayList<TreeNodeExtended> finallNodes) {
        boolean madzieci = this.getChildren().size() > 0;
        if (madzieci == true) {
            for (TreeNode o : this.getChildren()) {
                finallNodes.add((TreeNodeExtended) o);
                ((TreeNodeExtended) o).returnFinallChildren(finallNodes);
            }
            finallNodes.remove(this);
        }
    }
    
    public void sumNodes(ArrayList<TreeNodeExtended> finallNodes) {
        int lowestlevel = ustaldepth(finallNodes);
        ArrayList<TreeNodeExtended> parents = new ArrayList<>();
        for (TreeNodeExtended p : finallNodes) {
            if ((p.getParent()) instanceof TreeNodeExtended && !(p.getParent().getData() instanceof String)) {
                if (((PozycjaRZiS) p.getData()).getLevel()==lowestlevel) {
                    double kwotaparent = ((PozycjaRZiS) p.getParent().getData()).getKwota();
                    double kwotanode = ((PozycjaRZiS) p.getData()).getKwota();
                    ((PozycjaRZiS) p.getParent().getData()).setKwota(kwotaparent+kwotanode);
                    if (!parents.contains((TreeNodeExtended) p.getParent())) {
                        parents.add((TreeNodeExtended) p.getParent());
                    }
                } else {
                    parents.add(p);
                }
            }
        }
        finallNodes.clear();
        finallNodes.addAll(parents);
        if (parents.size() > 0) {
            sumNodes(finallNodes);
        }
    }
    
    private int ustaldepth(ArrayList<TreeNodeExtended> nodes) {
        int depth = 0;
        for (TreeNodeExtended p : nodes) {
            PozycjaRZiS pozycja = (PozycjaRZiS) p.getData();
            if (depth < pozycja.getLevel()) {
                depth = pozycja.getLevel();
            }
        }
        return depth;
    }
    
    public void expandAll() {
        boolean madzieci = this.getChildren().size() > 0;
        if (madzieci == true) {
            for (TreeNode o : this.getChildren()) {
                o.setExpanded(true);
                ((TreeNodeExtended) o).expandAll();
            }
        }
    }
}
