/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import abstractClasses.ToBeATreeNodeObject;
import comparator.Kontocomparator;
import entityfk.Konto;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Osito
 * @param <T>
 */
@Named
@Stateless
public class TreeNodeExtended<T> extends DefaultTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private boolean display;
    private List<KontoKwota> listaKontKwot;

    public TreeNodeExtended() {
        super();
    }

    public TreeNodeExtended(Object object, TreeNode parentnode) {
        super(object, parentnode);
    }

    //robi drzewko z elementów bazy danych
    public void createTreeNodesForElement(List<T> pozycje) {
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
                    tmp.setExpanded(true);
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
                            lp = ((ToBeATreeNodeObject) parent).getLp();
                            macierzysty = ((ToBeATreeNodeObject) p).getMacierzysty();
                        } catch (Exception ex) {
                           
                        }
                        if (lp == macierzysty) {
                            TreeNodeExtended tmp = new TreeNodeExtended(p, r);
                            tmp.setExpanded(true);
                            nowe.add(tmp);
                        }
                    }
                }
            }
            poprzednie.clear();
            poprzednie.addAll(nowe);
        }
    }
    
    
    //przeksztalca tresc tabeli w elementy do drzewa uklada je rzedami
    private Map<String, ArrayList<T>> getElementTreeFromPlainList(List<T> pozycje, int depth) {
        Map<String, ArrayList<T>> rzedy = new LinkedHashMap<>(depth);
        // builds a map of elements object returned from store
        for (int i = 0; i < depth; i++) {
            ArrayList<T> values = new ArrayList<>();
            for (T s : pozycje) {
                int level = 0;
                try {
//                    Method method = s.getClass().getMethod("getLevel");
//                    level = (int) method.invoke(s);
                    level = ((ToBeATreeNodeObject) s).getLevel();
                } catch (Exception ex) {
                    
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
    
    
    
      public int ustaldepthDT(List<T> pozycje) {
        int depth = 0;
        int pobranawartosc = 0;
        try {
            for (T p : pozycje) {
                try {
//                    Method method = p.getClass().getMethod("getLevel");
//                    pobranawartosc = (int) method.invoke(p);
                    pobranawartosc = ((ToBeATreeNodeObject) p).getLevel();
                } catch (Exception ex) {
                    
                }
                if (depth < pobranawartosc) {
                    depth = pobranawartosc;
                }
            }
            return depth + 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }
      
      public int ustaldepthDT() {
        ArrayList<TreeNodeExtended> pozycje = new ArrayList<TreeNodeExtended>();
        this.getFinallChildren(pozycje);
        int depth = 0;
        int pobranawartosc = 0;
        try {
            for (TreeNode p : pozycje) {
                try {
//                    Method method = p.getClass().getMethod("getLevel");
//                    pobranawartosc = (int) method.invoke(p);
                    pobranawartosc = ((PozycjaRZiSBilans) p.getData()).getLevel();
                } catch (Exception ex) {
                    
                }
                if (depth < pobranawartosc) {
                    depth = pobranawartosc;
                }
            }
            return depth + 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }
    
//    
//    //to tak smiesznie ze przekazuje pusta liste i ona dopiero sie zapelnia zadanymi Nodami
//    public void getNumberNodes(int numerporzadkowy) {
//        List<TreeNode> children = this.getChildren();
//        boolean madzieci = this.getChildCount() > 0;
//        if (madzieci == true) {
//            for (TreeNode o : children) {
//                ((Konto) ((TreeNodeExtended) o).getData()).setLpid(++numerporzadkowy);
//                ((TreeNodeExtended) o).getNumberNodes(numerporzadkowy);
//            }
//        }
//    }
//    
    
    //to tak smiesznie ze przekazuje pusta liste i ona dopiero sie zapelnia zadanymi Nodami
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
    
    public void getChildrenTree(List<TreeNodeExtended> nodes, List<T> pozycje) {
        List<TreeNode> children = this.getChildren();
        boolean madzieci = this.getChildCount() > 0;
        if (madzieci == true) {
            for (TreeNode o : children) {
                nodes.add((TreeNodeExtended) o);
                Object ob = ((TreeNodeExtended) o).getData();
                pozycje.add((T) ob);
                ((TreeNodeExtended) o).getChildrenTree(nodes, pozycje);
            }
            nodes.remove(this);
        }
    }
    
    //to tak smiesznie ze przekazuje pusta liste i ona dopiero sie zapelnia zadanymi Kontami
    public void getFinallChildrenData(ArrayList<TreeNodeExtended> finallNodes, List<Object> listazwrotna) {
        List<TreeNode> children = this.getChildren();
        boolean madzieci = this.getChildCount() > 0;
        if (madzieci == true) {
            for (TreeNode o : children) {
                finallNodes.add((TreeNodeExtended) o);
                ((TreeNodeExtended) o).getFinallChildrenData(finallNodes, listazwrotna);
            }
            finallNodes.remove(this);
        } else {
          listazwrotna.add(this.getData());
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

    public void addNumbers(List<StronaWiersza> zapisynakontach, List<Konto> plankont) throws Exception {
        ArrayList<TreeNodeExtended> finallNodes = new ArrayList<>();
        this.getFinallChildren(finallNodes);
        for (StronaWiersza stronaWiersza : zapisynakontach) {
            //pobiermay dane z poszczegolnego konta
            double kwotaWn = stronaWiersza.getWnma().equals("Wn") ? stronaWiersza.getKwotaPLN() : 0.0;
            double kwotaMa = stronaWiersza.getWnma().equals("Ma") ? stronaWiersza.getKwotaPLN() : 0.0;
            try {
                Konto kontopobrane = plankont.get(plankont.indexOf(stronaWiersza.getKonto()));
                String pozycjaRZiS_wn = kontopobrane.getKontopozycjaID().getPozycjaWn();
                String pozycjaRZiS_ma = kontopobrane.getKontopozycjaID().getPozycjaMa();
                for (TreeNodeExtended wybranapozycja : finallNodes) {
                    //sprawdzamy czy dane konto nalezy do danego wezla
                    PozycjaRZiSBilans pozycja = (PozycjaRZiSBilans) wybranapozycja.getData();
                    if ((pozycja.getPozycjaString()).equals(pozycjaRZiS_wn)) {
                        //pobieramy kwoty oraz to czy jest to przychod czy koszt
                        double kwotapierwotna = pozycja.getKwota();
                        double donaniesienia = 0.0;
                        pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                        donaniesienia = kwotaWn + kwotapierwotna;
                        pozycja.setKwota(donaniesienia);
                    }
                    if ((pozycja.getPozycjaString()).equals(pozycjaRZiS_ma)) {
                        //pobieramy kwoty oraz to czy jest to przychod czy koszt
                        double kwotapierwotna = pozycja.getKwota();
                        double donaniesienia = 0.0;
                        pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                        donaniesienia = kwotaMa + kwotapierwotna;
                        pozycja.setKwota(donaniesienia);
                    }
                }
            } catch (Exception e) {
                //throw new Exception("Istnieją konta nieprzyporządkowane do RZiS. Nie można przetworzyć danych za okres.");
            }
        }
    }
    
    public void addNumbersBilans(List<Konto> plankont, String aktywapasywa) throws Exception {
        ArrayList<TreeNodeExtended> finallNodes = new ArrayList<>();
        this.getFinallChildren(finallNodes);
        for (Konto p: plankont) {
            Konto kontopobrane = p;
            try {
                String pozycjaBilansWn = null;
                String pozycjaBilansMa = null;
                boolean stronaWn;
                boolean stronaMa;
                if (kontopobrane.getKontopozycjaID() != null) {
                    pozycjaBilansWn = kontopobrane.getKontopozycjaID().getPozycjaWn();
                    pozycjaBilansMa = kontopobrane.getKontopozycjaID().getPozycjaMa();
                    stronaWn =  kontopobrane.getKontopozycjaID().getStronaWn().equals("1") ? true : false;
                    stronaMa = kontopobrane.getKontopozycjaID().getStronaMa().equals("1") ? true : false;
                    boolean zrobionoWn = false;
                    boolean zrobionoMa = false;
                    for (TreeNodeExtended r : finallNodes) {
                        if (zrobionoWn == true && zrobionoMa == true) {
                            break;
                        }
                        //sprawdzamy czy dane konto nalezy do danego wezla
                            PozycjaRZiSBilans pozycja = (PozycjaRZiSBilans) r.getData();
                            if (kontopobrane.getZwyklerozrachszczegolne().equals("zwykłe")) {
                                double kwotapierwotna = pozycja.getKwota();
                                if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                    //pobieramy kwoty oraz to czy jest to przychod czy koszt
                                        if (stronaWn == false) {//jesli konto zwykle jest przyporzadowane do aktywow
                                            if (p.getSaldoWn() > 0) {
                                                pozycja.setKwota(kwotapierwotna+p.getSaldoWn());
                                                pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoWn(), p);
                                            } else if (p.getSaldoMa() > 0) {
                                                pozycja.setKwota(kwotapierwotna-p.getSaldoMa());
                                                pozycja.obsluzPrzyporzadkowaneKonta(-p.getSaldoMa(), p);
                                            }
                                        } else {
                                            if (p.getSaldoMa() > 0) {
                                                pozycja.setKwota(kwotapierwotna+p.getSaldoMa());
                                                pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoMa(), p);
                                            } else if (p.getSaldoWn() > 0) {
                                                pozycja.setKwota(kwotapierwotna-p.getSaldoWn());
                                                pozycja.obsluzPrzyporzadkowaneKonta(-p.getSaldoWn(), p);
                                            }
                                        }
                                        break;//tu break ma sens bo konto zwykle jest tylko w jednym miejscu
                                }
                            } else if (kontopobrane.getZwyklerozrachszczegolne().equals("rozrachunkowe") || kontopobrane.getZwyklerozrachszczegolne().equals("vat")) {
                                double kwotapierwotna = pozycja.getKwota();
                                if (p.getSaldoWn() != 0.0 || p.getSaldoMa() != 0.0) {
                                    if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                        if (stronaWn==false && stronaMa==false) {
                                            pozycja.setKwota(kwotapierwotna+p.getSaldoWn()-p.getSaldoMa());
                                            pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoWn()-p.getSaldoMa(), p);
                                        } else {
                                            pozycja.setKwota(kwotapierwotna+p.getSaldoWn());
                                            pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoWn(), p);
                                        }
                                        zrobionoWn = true;
                                    } else if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                        if (stronaWn==true && stronaMa==true) {
                                            pozycja.setKwota(kwotapierwotna+p.getSaldoMa()-p.getSaldoWn());
                                            pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoMa()-p.getSaldoWn(), p);
                                        } else {
                                            pozycja.setKwota(kwotapierwotna+p.getSaldoMa());
                                            pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoMa(), p);
                                        }
                                        zrobionoMa = true;
                                    }
                                }
                            } else if (kontopobrane.getZwyklerozrachszczegolne().equals("szczególne")) {
                                double kwotapierwotna = pozycja.getKwota();
                                if (p.getSaldoWn() != 0.0 || p.getSaldoMa() != 0.0) {
                                    if (aktywapasywa.equals("aktywa")) {
                                        if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                            if (p.getSaldoWn() != 0) {
                                                pozycja.setKwota(kwotapierwotna+p.getSaldoWn());
                                                pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoWn(), p);
                                            }
                                            zrobionoWn = true;
                                        } //sa dwa idy zamiast else bo przy szczegolnych dwa salda moga byc przypisane do jednej pozycji
                                        if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                            if (p.getSaldoMa() != 0) {
                                                pozycja.setKwota(kwotapierwotna-p.getSaldoMa());
                                                pozycja.obsluzPrzyporzadkowaneKonta(-p.getSaldoMa(), p);
                                            }
                                            zrobionoMa = true;
                                        }
                                    } else {
                                        if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                            if (p.getSaldoWn() != 0) {
                                                pozycja.setKwota(kwotapierwotna-p.getSaldoWn());
                                                pozycja.obsluzPrzyporzadkowaneKonta(-p.getSaldoWn(), p);
                                            }
                                            zrobionoWn = true;
                                        } //sa dwa idy zamiast else bo przy szczegolnych dwa salda moga byc przypisane do jednej pozycji
                                        if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                            if (p.getSaldoMa() != 0) {
                                                pozycja.setKwota(kwotapierwotna+p.getSaldoMa());
                                                pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoMa(), p);
                                            }
                                            zrobionoMa = true;
                                        }
                                    }
                                }
                            }
                    }
                }
            } catch (Exception e) {
                if (e.getStackTrace() != null && e.getStackTrace().length >0) {
                    System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString());
                }
                throw new Exception("Istnieją konta nieprzyporządkowane do RZiS. Nie można przetworzyć danych za okres.");
            }
        }
    }
    
    public void addNumbersBilansNowy(List<StronaWiersza> zapisynakontach, List<Konto> plankont, String aktywapasywa) throws Exception {
        ArrayList<TreeNodeExtended> finallNodes = new ArrayList<>();
        this.getFinallChildren(finallNodes);
        for (StronaWiersza stronaWiersza : zapisynakontach) {
            double kwotaWn = stronaWiersza.getWnma().equals("Wn") ? stronaWiersza.getKwotaPLN() : 0.0;
            double kwotaMa = stronaWiersza.getWnma().equals("Ma") ? stronaWiersza.getKwotaPLN() : 0.0;
            try {
                Konto kontopobrane = plankont.get(plankont.indexOf(stronaWiersza.getKonto()));
                String pozycjaBilansWn = null;
                String pozycjaBilansMa = null;
                boolean stronaWn;
                boolean stronaMa;
                if (kontopobrane.getKontopozycjaID() != null) {
                    pozycjaBilansWn = kontopobrane.getKontopozycjaID().getPozycjaWn();
                    pozycjaBilansMa = kontopobrane.getKontopozycjaID().getPozycjaMa();
                    stronaWn =  kontopobrane.getKontopozycjaID().getStronaWn().equals("1") ? true : false;
                    stronaMa = kontopobrane.getKontopozycjaID().getStronaMa().equals("1") ? true : false;
                    for (TreeNodeExtended r : finallNodes) {
                        //sprawdzamy czy dane konto nalezy do danego wezla
                            PozycjaRZiSBilans pozycja = (PozycjaRZiSBilans) r.getData();
                            if (kontopobrane.getZwyklerozrachszczegolne().equals("zwykłe")) {
                                double kwotapierwotna = pozycja.getKwota();
                                if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                    //pobieramy kwoty oraz to czy jest to przychod czy koszt
                                        if (stronaWn == false) {//jesli konto zwykle jest przyporzadowane do aktywow
                                            if (kontopobrane.getSaldoWn() > 0) {
                                                pozycja.setKwota(kwotapierwotna+kontopobrane.getSaldoWn());
                                                pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                                            } else {
                                                pozycja.setKwota(kwotapierwotna-kontopobrane.getSaldoMa());
                                                pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                                            }
                                        } else {
                                            if (kontopobrane.getSaldoMa() > 0) {
                                                pozycja.setKwota(kwotapierwotna+kontopobrane.getSaldoMa());
                                                pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                                            } else {
                                                pozycja.setKwota(kwotapierwotna-kontopobrane.getSaldoWn());
                                                pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                                            }
                                        }
                                }
                            } else if (kontopobrane.getZwyklerozrachszczegolne().equals("rozrachunkowe") || kontopobrane.getZwyklerozrachszczegolne().equals("vat")) {
                                double kwotapierwotna = pozycja.getKwota();
                                if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                    if (stronaWn==false && stronaMa==false) {
                                        pozycja.setKwota(kwotapierwotna+kontopobrane.getSaldoWn()-kontopobrane.getSaldoMa());
                                    } else {
                                        pozycja.setKwota(kwotapierwotna+kontopobrane.getSaldoWn());
                                    }
                                    pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                                } else if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                    if (stronaWn==true && stronaMa==true) {
                                        pozycja.setKwota(kwotapierwotna+kontopobrane.getSaldoMa()-kontopobrane.getSaldoWn());
                                    } else {
                                        pozycja.setKwota(kwotapierwotna+kontopobrane.getSaldoMa());
                                    }
                                    pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                                }
                            } else if (kontopobrane.getZwyklerozrachszczegolne().equals("szczególne")) {
                                double kwotapierwotna = pozycja.getKwota();
                                if (aktywapasywa.equals("aktywa")) {
                                    if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                        if (kontopobrane.getSaldoWn() != 0) {
                                            pozycja.setKwota(kwotapierwotna+kontopobrane.getSaldoWn());
                                            pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                                        }
                                    } //sa dwa idy zamiast else bo przy szczegolnych dwa salda moga byc przypisane do jednej pozycji
                                    if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                        if (kontopobrane.getSaldoMa() != 0) {
                                            pozycja.setKwota(kwotapierwotna-kontopobrane.getSaldoMa());
                                            pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                                        }
                                    }
                                } else {
                                    if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                        if (kontopobrane.getSaldoWn() != 0) {
                                            pozycja.setKwota(kwotapierwotna-kontopobrane.getSaldoWn());
                                            pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                                        }
                                    } //sa dwa idy zamiast else bo przy szczegolnych dwa salda moga byc przypisane do jednej pozycji
                                    if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                        if (kontopobrane.getSaldoMa() != 0) {
                                            pozycja.setKwota(kwotapierwotna+kontopobrane.getSaldoMa());
                                            pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                                        }
                                    }
                                }
                                
                            }
                    }
                }
            } catch (Exception e) {
                
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
                if ((p.getParent()) instanceof TreeNodeExtended && !(p.getParent().getData() instanceof String) && p.getFormula().isEmpty()) {
                    if (((PozycjaRZiSBilans) p.getData()).getLevel() == lowestlevel) {
                        double kwotaparent = ((TreeNodeExtended) p.getParent()).getKwota();
                        double kwotanode = p.getKwota();
                        ((PozycjaRZiSBilans) p.getParent().getData()).setKwota(kwotaparent + kwotanode);
                        if (!parents.contains(p.getParent())) {
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
            PozycjaRZiSBilans pozycja = (PozycjaRZiSBilans) p.getData();
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
            try {
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
            } catch (Exception e) {
                
            }
        }
    }

    private String getFormula() {
        return ((PozycjaRZiSBilans) this.getData()).getFormula();
    }

    private String getSymbol() {
        return ((PozycjaRZiSBilans) this.getData()).getPozycjaSymbol();
    }

    private double getKwota() {
        return ((PozycjaRZiSBilans) this.getData()).getKwota();
    }

    private void setKwota(double kwota) {
        ((PozycjaRZiSBilans) this.getData()).setKwota(kwota);
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
                Collections.sort((List<Konto>) biezaca, new Kontocomparator());
            }
        }
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public List<KontoKwota> getListaKontKwot() {
        return listaKontKwot;
    }

    public void setListaKontKwot(List<KontoKwota> listaKontKwot) {
        this.listaKontKwot = listaKontKwot;
    }

      
    
    
}
