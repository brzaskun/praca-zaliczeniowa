/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import abstractClasses.ToBeATreeNodeObject;
import comparator.Kontocomparator;
import entityfk.Konto;
import entityfk.Kontozapisy;
import entityfk.PozycjaRZiS;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Osito
 */
public class TreeNodeExtended<T> extends DefaultTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean display;

    public TreeNodeExtended() {
        super();
    }

    public TreeNodeExtended(Object object, TreeNode node) {
        super(object, node);
    }

    //robi drzewko z elementów bazy danych
    public void createTreeNodesForElement(ArrayList<T> pozycje) {
       int depth = ustaldepthDT(pozycje);
       Map<String, ArrayList<T>> rzedy = getElementTreeFromPlainList(pozycje, depth);
       ArrayList<TreeNodeExtended> poprzednie = new ArrayList<>();
        for (int i = 0; i < depth; i++) {
            ArrayList<TreeNodeExtended> nowe = new ArrayList<>();
            ArrayList<T> biezaca = rzedy.get(String.valueOf(i));
            uporzadkujbiezaca(biezaca);
            for (T p : biezaca) {
                if (i == 0) {
                    TreeNodeExtended tmp = new TreeNodeExtended(p, this);
                     nowe.add(tmp);
                } else {
                    Iterator it = poprzednie.iterator();
                    while (it.hasNext()) {
                        TreeNodeExtended r = (TreeNodeExtended) it.next();
                        T parent = (T) r.getData();
                        int lp = 0;
                        int macierzysty = 0;
                        try {
                            //tutaj wyszukujemy funkcje, a mozna bylo uzyc abstrakcji
                            Method method = parent.getClass().getMethod("getLp");
                            lp = (int) method.invoke(parent);
                            method = p.getClass().getMethod("getMacierzysty");
                            macierzysty =  (int) method.invoke(p);
                        } catch (Exception ex) {
                           Logger.getLogger(TreeNodeExtended.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (lp == macierzysty) {
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
    private Map<String, ArrayList<T>> getElementTreeFromPlainList(ArrayList<T> pozycje, int depth) {
        Map<String, ArrayList<T>> rzedy = new LinkedHashMap<>(depth);
        // builds a map of elements object returned from store
        for (int i = 0; i < depth; i++) {
            ArrayList<T> values = new ArrayList<>();
            for (T s : pozycje) {
                int level = 0;
                try {
                    Method method = s.getClass().getMethod("getLevel");
                    level = (int) method.invoke(s);
                } catch (Exception ex) {
                    Logger.getLogger(TreeNodeExtended.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (level == i) {
                    values.add(s);
                }
            }
            if (values.size()>0) {
                rzedy.put(String.valueOf(i),  values);
            }
        }
        return rzedy;
    }
    
       
    public int ustaldepthDT(ArrayList<T> pozycje) {
        int depth = 0;
        int pobranawartosc = 0;
        for (T  p : pozycje) {
            try {
               Method method = p.getClass().getMethod("getLevel");
               pobranawartosc = (int) method.invoke(p);
        } catch (Exception ex) {
           Logger.getLogger(TreeNodeExtended.class.getName()).log(Level.SEVERE, null, ex);
        }
            if (depth < pobranawartosc) {
                depth = pobranawartosc;
            }
        }
        return depth+1;
    }
    
    
    
    
    //to tak smiesznie ze przekazuje pusta liste i ona dopiero sie zapelnia zadanymi
    public void getFinallChildren(ArrayList<TreeNodeExtended> finallNodes) {
        List<TreeNode> children = this.getChildren();
        boolean madzieci = this.getChildCount() > 0;
        if (madzieci == true) {
            for (TreeNode o : children) {
                finallNodes.add((TreeNodeExtended) o);
                ((TreeNodeExtended) o).getFinallChildren(finallNodes);
            }
            finallNodes.remove(this);
        }
    }
    //ustawia paramentr display na true tylko dla ostatnich elementow
    public void displayOnlyFinallChildren() {
        ArrayList<TreeNodeExtended> finallNodes = new ArrayList<>();
        this.getFinallChildren(finallNodes);
        for (TreeNodeExtended p : finallNodes) {
            p.display = true;
        }
    }

    public void addNumbers(List<Kontozapisy> zapisynakontach, List<Konto> plankont) {
        ArrayList<TreeNodeExtended> finallNodes = new ArrayList<>();
        this.getFinallChildren(finallNodes);
        for (Kontozapisy p: zapisynakontach) {
            //pobiermay dane z poszczegolnego konta
            double kwotaWn = p.getKwotawn();
            double kwotaMa = p.getKwotama();
            Konto kontopobrane = plankont.get(plankont.indexOf(p.getKontoob()));
            String pozycjaRZiS = kontopobrane.getPozycja();
            for (TreeNodeExtended r : finallNodes) {
                //sprawdzamy czy dane konto nalezy do danego wezla
                PozycjaRZiS pozycja = (PozycjaRZiS) r.getData();
                if ((pozycja.getPozycjaString()).equals(pozycjaRZiS)) {
                    //pobieramy kwoty oraz to czy jest to przychod czy koszt
                    double kwotapierwotna = pozycja.getKwota();
                    double donaniesienia = 0.0;
                    boolean przychod0koszt1 = pozycja.isPrzychod0koszt1();
                    if (przychod0koszt1 == false) {
                        donaniesienia = kwotaMa - kwotaWn + kwotapierwotna;
                    } else {
                        donaniesienia = kwotaWn - kwotaMa + kwotapierwotna;
                    }
                    pozycja.setKwota(donaniesienia);
                }
            }
        }
    }
    
    public void sumNodes() {
        ArrayList<TreeNodeExtended> finallNodes = new ArrayList<>();
        this.getFinallChildren(finallNodes);
        ArrayList<TreeNodeExtended> parents = new ArrayList<>();
        do {
            int lowestlevel = ustaldepth(finallNodes);
            parents.clear();
            for (TreeNodeExtended p : finallNodes) {
                //ta fomula wyklyczamy roota i nody z formula do dodawania i odliczania kwot
                if ((p.getParent()) instanceof TreeNodeExtended && !(p.getParent().getData() instanceof String) && p.getFormula().equals("")) {
                    if (((PozycjaRZiS) p.getData()).getLevel() == lowestlevel) {
                        double kwotaparent = ((TreeNodeExtended) p.getParent()).getKwota();
                        double kwotanode = p.getKwota();
                        ((PozycjaRZiS) p.getParent().getData()).setKwota(kwotaparent + kwotanode);
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
        } while (parents.size() > 0);
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
        boolean madzieci = this.getChildCount() > 0;
        List<TreeNode> lista = this.getChildren();
        if (madzieci == true) {
            for (TreeNode o : lista) {
                o.setExpanded(true);
                ((TreeNodeExtended) o).expandAll();
            }
        }
    }
    
    public void expandLevel(int level) {
        boolean madzieci = this.getChildCount() > 0;
        List<TreeNode> lista = this.getChildren();
        if (madzieci == true) {
            for (TreeNode o : lista) {
                o.setExpanded(true);
                if (((ToBeATreeNodeObject) o.getData()).getLevel()<level){
                    ((TreeNodeExtended) o).expandLevel(level);
                }
            }
        }
    }
    
    
     public void foldAll() {
        boolean madzieci = this.getChildCount() > 0;
        List<TreeNode> lista = this.getChildren();
        if (madzieci == true) {
            for (TreeNode o : lista) {
                o.setExpanded(false);
                ((TreeNodeExtended) o).foldAll();
            }
        }
    }
     
      public void foldLevel(int level) {
        boolean madzieci = this.getChildCount() > 0;
        List<TreeNode> lista = this.getChildren();
        if (madzieci == true) {
            for (TreeNode o : lista) {
                if (((ToBeATreeNodeObject) o.getData()).getLevel()==level){
                     o.setExpanded(false);
                }
                ((TreeNodeExtended) o).foldLevel(level);
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

    public void reset() {
         this.getChildren().clear();
    }

    private void uporzadkujbiezaca(ArrayList<T> biezaca) {
        if (biezaca.size() > 0) {
            Object pobrany = biezaca.get(0);
            if (pobrany.getClass().getSimpleName().equals("Konto")) {
                Collections.sort((ArrayList<Konto>) biezaca, new Kontocomparator());
            }
        }
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
    
    
}
