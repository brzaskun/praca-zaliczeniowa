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
    //to tak smiesznie ze przekazuje pusta liste i ona dopiero sie zapelnia zadanymi
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
            //ta fomula wyklyczamy roota i nody z formula do dodawania i odliczania kwot
            if ((p.getParent()) instanceof TreeNodeExtended && !(p.getParent().getData() instanceof String) && p.getFormula().equals("")) {
                if (((PozycjaRZiS) p.getData()).getLevel()==lowestlevel) {
                    double kwotaparent = ((TreeNodeExtended) p.getParent()).getKwota();
                    double kwotanode = p.getKwota();
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

    public void resolveFormulas() {
        ArrayList<TreeNode> finallNodes = (ArrayList<TreeNode>) this.getChildren();
        for (TreeNode p : finallNodes) {
            if (!((TreeNodeExtended) p).getFormula().isEmpty()) {
                String formula = ((TreeNodeExtended) p).getFormula();
                int formulalength = formula.length();
                Character[] formulaParse = new Character[formulalength];
                for (int i = 0; i < formulalength; i++) {
                    formulaParse[i] = formula.charAt(i);
                }
                double wynik = dotheMath(finallNodes, formulaParse, formulalength);
                ((TreeNodeExtended) p).setKwota(wynik);
            }
        }
    }
    
    private String getFormula() {
        return ((PozycjaRZiS) this.getData()).getFormula();
    }
    
    private String getSymbol() {
        return ((PozycjaRZiS) this.getData()).getPozycjaSymbol();
    }
    
    private double getKwota() {
        return ((PozycjaRZiS) this.getData()).getKwota();
    }
    
    private void setKwota(double kwota) {
        ((PozycjaRZiS) this.getData()).setKwota(kwota);
    }

    private double dotheMath(ArrayList<TreeNode> finallNodes, Character[] formulaParse, int formulalength) {
        double wynik = findBypozycjaSymbol(finallNodes, formulaParse[0]).getKwota();
        for (int i = 1; i < formulalength; i++) {
            Character znak = formulaParse[i++];
            TreeNodeExtended drugi = findBypozycjaSymbol(finallNodes, formulaParse[i]);
            if (znak == '-') {
                wynik -= drugi.getKwota();
            } else {
                wynik += drugi.getKwota();
            }
        }
        return wynik;
    }

    private TreeNodeExtended findBypozycjaSymbol(ArrayList<TreeNode> finallNodes, Character character) {
        for (TreeNode p : finallNodes) {
            if (((TreeNodeExtended) p).getSymbol().charAt(0) == character) {
                return (TreeNodeExtended) p;
            }
        }
        return null;
    }

    
}

