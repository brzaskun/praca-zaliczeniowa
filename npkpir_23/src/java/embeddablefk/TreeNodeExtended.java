/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import abstractClasses.ToBeATreeNodeObject;
import comparator.KontoBOcomparator;
import comparator.Kontocomparator;
import comparator.PozycjaBilanscomparator;
import comparator.PozycjaRZiScomparator;
import embeddable.Mce;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import waluty.Z;

/**
 *
 * @author Osito
 * @param <T>
 */
@Named
public class TreeNodeExtended<T> extends DefaultTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private boolean display;

    public TreeNodeExtended() {
        super();
    }

    public TreeNodeExtended(Object object, TreeNode parentnode) {
        super(object, parentnode);
    }

    //robi drzewko z elementów bazy danych
    public void createTreeNodesForElement(List<T> pozycje) {
       int depth = ustaldepthDT(pozycje);
       Map<String, List<T>> rzedy = getElementTreeFromPlainList(pozycje, depth);
       List<TreeNodeExtended> poprzednie = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < depth; i++) {
            List<TreeNodeExtended> nowe = Collections.synchronizedList(new ArrayList<>());
            List<T> biezaca = rzedy.get(String.valueOf(i));
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
                        try {
                            //tutaj wyszukujemy funkcje, a mozna bylo uzyc abstrakcji
                            if (p.getClass().getSimpleName().equals("KontoBO")) {
                            String lps = ((Konto) p).getMacierzyste();
                            String macs = ((Konto) parent).getPelnynumer();
                            if (lps.equals(macs)) {
                                 TreeNodeExtended tmp = new TreeNodeExtended(p, r);
                                 tmp.setExpanded(true);
                                 nowe.add(tmp);
                             }
                           } else {
                                int lp = 0;
                                int macierzysty = 0;
                                lp = ((ToBeATreeNodeObject) parent).getLp();
                                macierzysty = ((ToBeATreeNodeObject) p).getMacierzysty();
                                if (lp == macierzysty) {
                                    TreeNodeExtended tmp = new TreeNodeExtended(p, r);
                                    tmp.setExpanded(true);
                                    nowe.add(tmp);
                                }
                            }
                        } catch (Exception ex) {
                           
                        }
                    }
                }
            }
            poprzednie.clear();
            poprzednie.addAll(nowe);
        }
    }
    
    
    //przeksztalca tresc tabeli w elementy do drzewa uklada je rzedami
    private Map<String, List<T>> getElementTreeFromPlainList(List<T> pozycje, int depth) {
        Map<String, List<T>> rzedy = new LinkedHashMap<>(depth);
        // builds a map of elements object returned from store
        for (int i = 0; i < depth; i++) {
            List<T> values = Collections.synchronizedList(new ArrayList<>());
            for (Iterator<T> it = pozycje.iterator(); it.hasNext(); ) {
                T s = it.next();
                int level = 0;
                try {
//                    Method method = s.getClass().getMethod("getLevel");
//                    level = (int) method.invoke(s);
                    level = ((ToBeATreeNodeObject) s).getLevel();
                } catch (Exception ex) {
                    
                }
                if (level == i) {
                    values.add(s);
                    it.remove();
                }
            }
            if (values.size()>0) {
                rzedy.put(String.valueOf(i),  values);
            }
        }
        for (String p : rzedy.keySet()) {
            List<T> values = rzedy.get(p);
            if (values.get(0) != null && values.get(0).getClass().getSimpleName().equals("PozycjaBilans")) {
                Collections.sort((List<PozycjaBilans>)values, new PozycjaBilanscomparator());
            } else if (values.get(0) != null && values.get(0).getClass().getSimpleName().equals("PozycjaRZiS")) {
                Collections.sort((List<PozycjaRZiS>)values, new PozycjaRZiScomparator());
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
        List<TreeNodeExtended> pozycje = new ArrayList<TreeNodeExtended>();
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
    public void getFinallChildren(List<TreeNodeExtended> finallNodes) {
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
    
    public void getRemoveZeroBOChildrenTree(List<TreeNodeExtended> oldnodes, List<TreeNodeExtended> newnodes) {
        List<TreeNode> children = this.getChildren();
        boolean madzieci = this.getChildCount() > 0;
        if (madzieci == true) {
            for (TreeNode o : children) {
                oldnodes.add((TreeNodeExtended) o);
                Object ob = ((TreeNodeExtended) o).getData();
                KontoBO konto = (KontoBO) ob;
                if (konto.getRoznicaWn() != 0.0 || konto.getRoznicaMa() != 0.0) {
                    newnodes.add((TreeNodeExtended) o);
                }
                ((TreeNodeExtended) o).getRemoveZeroBOChildrenTree(oldnodes, newnodes);
            }
            oldnodes.remove(this);
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
    
    public Object getFirstChild() {
        Object zwrot = null;
        List<TreeNode> children = this.getChildren();
        boolean madzieci = this.getChildCount() > 0;
        if (madzieci == true) {
            for (TreeNode o : children) {
                zwrot = ((TreeNodeExtended) o).getData();
                break;
            }
        }
        return zwrot;
    }
    
    //to tak smiesznie ze przekazuje pusta liste i ona dopiero sie zapelnia zadanymi Kontami
    public void getFinallChildrenData(List<TreeNodeExtended> finallNodes, List<Object> listazwrotna) {
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
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        for (TreeNodeExtended p : finallNodes) {
            p.display = true;
        }
    }

    public void addNumbers(List<StronaWiersza> zapisynakontach) throws Exception {
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        for (StronaWiersza stronaWiersza : zapisynakontach) {
            addNumbersloop(stronaWiersza, finallNodes);
        }
    }
    
    public void addNumbersSlot(List<StronaWiersza> zapisynakontach, String kolumna) throws Exception {
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        for (StronaWiersza stronaWiersza : zapisynakontach) {
            addNumbersloopNar(stronaWiersza, finallNodes, kolumna);
        }
    }
    
    
     public void addNumbersNar(List<StronaWiersza> zapisynakontach, String mckoncowy) throws Exception {
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        for (StronaWiersza stronaWiersza : zapisynakontach) {
            String mc = stronaWiersza.getDokfk().getMiesiac();
            if (Mce.getMiesiacToNumber().get(mc) <= Mce.getMiesiacToNumber().get(mckoncowy)) {
                addNumbersloopNar(stronaWiersza, finallNodes, mc);
            }
        }
    }
    
    private void addNumbersloop(StronaWiersza stronaWiersza, List<TreeNodeExtended> finallNodes) {
        double kwotaWn = stronaWiersza.getWnma().equals("Wn") ? stronaWiersza.getKwotaPLN() : 0.0;
        double kwotaMa = stronaWiersza.getWnma().equals("Ma") ? stronaWiersza.getKwotaPLN() : 0.0;
            try {
                Konto kontopobrane = stronaWiersza.getKonto();
                if (kontopobrane.getPelnynumer().equals("755")) {
                }
                String pozycjaRZiS_wn = kontopobrane.getKontopozycjaID().getPozycjaWn();
                String pozycjaRZiS_ma = kontopobrane.getKontopozycjaID().getPozycjaMa();
                boolean wn = false;
                boolean ma = false;
                for (TreeNodeExtended wybranapozycja : finallNodes) {
                    if (wn==true && ma==true) {
                        break;
                    }
                    //sprawdzamy czy dane konto nalezy do danego wezla
                    PozycjaRZiSBilans pozycja = (PozycjaRZiSBilans) wybranapozycja.getData();
                    if ((pozycja.getPozycjaString()).equals(pozycjaRZiS_wn)) {
                        //pobieramy kwoty oraz to czy jest to przychod czy koszt
                        double kwotapierwotna = Z.z(pozycja.getKwota());
                        double donaniesienia = 0.0;
                        pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                        pozycja.obsluzPrzyporzadkowaneKontaRZiS(kwotaWn, stronaWiersza.getKonto());
                        if (kontopobrane.getZwyklerozrachszczegolne().equals("szczególne")) {
                            donaniesienia = kwotapierwotna+kwotaWn;
                        } else {
                            if (kontopobrane.isPrzychod0koszt1() == true) {
                                donaniesienia = kwotapierwotna+kwotaWn;
                            } else {
                                donaniesienia = kwotapierwotna-kwotaWn;
                            }
                        }
                        pozycja.setKwota(donaniesienia);
                        wn = true;
                    }
                    if ((pozycja.getPozycjaString()).equals(pozycjaRZiS_ma)) {
                        //pobieramy kwoty oraz to czy jest to przychod czy koszt
                        double kwotapierwotna = Z.z(pozycja.getKwota());
                        double donaniesienia = 0.0;
                        pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                        pozycja.obsluzPrzyporzadkowaneKontaRZiS(kwotaMa, stronaWiersza.getKonto());
                        if (kontopobrane.getZwyklerozrachszczegolne().equals("szczególne")) {
                            donaniesienia = kwotapierwotna+kwotaMa;
                        } else {
                            if (kontopobrane.isPrzychod0koszt1() == true) {
                                donaniesienia = kwotapierwotna-kwotaMa;
                            } else {
                                donaniesienia = kwotapierwotna+kwotaMa;
                            }
                        }
                        pozycja.setKwota(donaniesienia);
                        ma = true;
                    }
                }
                
            //pobiermay dane z poszczegolnego konta
            
            } catch (Exception e) {
                //throw new Exception("Istnieją konta nieprzyporządkowane do RZiS. Nie można przetworzyć danych za okres.");
            }
    }
    
    private void addNumbersloopNar(StronaWiersza stronaWiersza, List<TreeNodeExtended> finallNodes, String mc) {
        double kwotaWn = stronaWiersza.getWnma().equals("Wn") ? stronaWiersza.getKwotaPLN() : 0.0;
        double kwotaMa = stronaWiersza.getWnma().equals("Ma") ? stronaWiersza.getKwotaPLN() : 0.0;
            try {
                Konto kontopobrane = stronaWiersza.getKonto();
                String pozycjaRZiS_wn = kontopobrane.getKontopozycjaID().getPozycjaWn();
                String pozycjaRZiS_ma = kontopobrane.getKontopozycjaID().getPozycjaMa();
                boolean wn = false;
                boolean ma = false;
                for (TreeNodeExtended wybranapozycja : finallNodes) {
                    if (wn==true && ma==true) {
                        break;
                    }
                    //sprawdzamy czy dane konto nalezy do danego wezla
                    PozycjaRZiS pozycja = (PozycjaRZiS) wybranapozycja.getData();
                    if ((pozycja.getPozycjaString()).equals(pozycjaRZiS_wn)) {
                        //pobieramy kwoty oraz to czy jest to przychod czy koszt
                        double kwotapierwotna = Z.z(pozycja.getMce().get(mc));
                        double donaniesienia = 0.0;
                        pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                        if (kontopobrane.getZwyklerozrachszczegolne().equals("szczególne")) {
                            donaniesienia = kwotapierwotna+kwotaWn;
                        } else {
                            if (kontopobrane.isPrzychod0koszt1() == true) {
                                donaniesienia = kwotapierwotna+kwotaWn;
                            } else {
                                donaniesienia = kwotapierwotna-kwotaWn;
                            }
                        }
                        pozycja.getMce().put(mc, donaniesienia);
                        wn = true;
                    }
                    if ((pozycja.getPozycjaString()).equals(pozycjaRZiS_ma)) {
                        //pobieramy kwoty oraz to czy jest to przychod czy koszt
                        double kwotapierwotna = Z.z(pozycja.getMce().get(mc));
                        double donaniesienia = 0.0;
                        pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                        if (kontopobrane.getZwyklerozrachszczegolne().equals("szczególne")) {
                            donaniesienia = kwotapierwotna+kwotaMa;
                        } else {
                            if (kontopobrane.isPrzychod0koszt1() == true) {
                                donaniesienia = kwotapierwotna-kwotaMa;
                            } else {
                                donaniesienia = kwotapierwotna+kwotaMa;
                            }
                        }
                        pozycja.getMce().put(mc, donaniesienia);
                        ma = true;
                    }
                }
                
            //pobiermay dane z poszczegolnego konta
            
            } catch (Exception e) {
                //throw new Exception("Istnieją konta nieprzyporządkowane do RZiS. Nie można przetworzyć danych za okres.");
            }
    }
    
    public void addNumbersBO(List<StronaWiersza> zapisynakontach) throws Exception {
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        for (StronaWiersza stronaWiersza : zapisynakontach) {
            //pobiermay dane z poszczegolnego konta
            double kwotaWn = stronaWiersza.getWnma().equals("Wn") ? stronaWiersza.getKwotaPLN() : 0.0;
            double kwotaMa = stronaWiersza.getWnma().equals("Ma") ? stronaWiersza.getKwotaPLN() : 0.0;
            try {
                Konto kontopobrane = stronaWiersza.getKonto();
                String pozycjaRZiS_wn = kontopobrane.getKontopozycjaID().getPozycjaWn();
                String pozycjaRZiS_ma = kontopobrane.getKontopozycjaID().getPozycjaMa();
                boolean wn = false;
                boolean ma = false;
                for (TreeNodeExtended wybranapozycja : finallNodes) {
                    if (wn==true && ma==true) {
                        break;
                    }
                    //sprawdzamy czy dane konto nalezy do danego wezla
                    PozycjaRZiSBilans pozycja = (PozycjaRZiSBilans) wybranapozycja.getData();
                    if ((pozycja.getPozycjaString()).equals(pozycjaRZiS_wn)) {
                        //pobieramy kwoty oraz to czy jest to przychod czy koszt
                        double kwotapierwotna = pozycja.getKwotabo();
                        double donaniesienia = 0.0;
                        pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                        if (kontopobrane.getZwyklerozrachszczegolne().equals("szczególne")) {
                            donaniesienia = kwotapierwotna+kwotaWn;
                        } else {
                            if (kontopobrane.isPrzychod0koszt1() == true) {
                                donaniesienia = kwotapierwotna+kwotaWn;
                            } else {
                                donaniesienia = kwotapierwotna-kwotaWn;
                            }
                        }
                        pozycja.setKwotabo(donaniesienia);
                        wn = true;
                    }
                    if ((pozycja.getPozycjaString()).equals(pozycjaRZiS_ma)) {
                        //pobieramy kwoty oraz to czy jest to przychod czy koszt
                        double kwotapierwotna = pozycja.getKwotabo();
                        double donaniesienia = 0.0;
                        pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                        if (kontopobrane.getZwyklerozrachszczegolne().equals("szczególne")) {
                            donaniesienia = kwotapierwotna+kwotaMa;
                        } else {
                            if (kontopobrane.isPrzychod0koszt1() == true) {
                                donaniesienia = kwotapierwotna-kwotaMa;
                            } else {
                                donaniesienia = kwotapierwotna+kwotaMa;
                            }
                        }
                        pozycja.setKwotabo(donaniesienia);
                        ma = true;
                    }
                }
            } catch (Exception e) {
                //throw new Exception("Istnieją konta nieprzyporządkowane do RZiS. Nie można przetworzyć danych za okres.");
            }
        }
    }
    
    public void addNumbersBilans(List<Konto> plankont, String aktywapasywa) throws Exception {
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        for (Konto p: plankont) {
            if (p.getPelnynumer().equals("234-4-2")) {
            }
            Konto kontopobrane = p;
            if (p.getSaldoWn() != 0.0 || p.getSaldoMa() != 0.0) {
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
                                if (pozycja.getPozycjaString().equals(pozycjaBilansWn) || pozycja.getPozycjaString().equals(pozycjaBilansMa)) {
                                    if (kontopobrane.getZwyklerozrachszczegolne().equals("zwykłe")) {
                                        double kwotapierwotna = Z.z(pozycja.getKwota());
                                        if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                            //pobieramy kwoty oraz to czy jest to przychod czy koszt
                                                if (stronaWn == false) {//jesli konto zwykle jest przyporzadowane do aktywow
                                                    if (p.getSaldoWn() != 0.0) {
                                                        pozycja.setKwota(kwotapierwotna+p.getSaldoWn());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoWn(), p);
                                                    } else if (p.getSaldoMa() != 0.0) {
                                                        pozycja.setKwota(kwotapierwotna-p.getSaldoMa());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(-p.getSaldoMa(), p);
                                                    }
                                                } else {
                                                    if (p.getSaldoMa() != 0.0) {
                                                        pozycja.setKwota(kwotapierwotna+p.getSaldoMa());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoMa(), p);
                                                    } else if (p.getSaldoWn() != 0.0) {
                                                        pozycja.setKwota(kwotapierwotna-p.getSaldoWn());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(-p.getSaldoWn(), p);
                                                    }
                                                }
                                                break;//tu break ma sens bo konto zwykle jest tylko w jednym miejscu
                                        }
                                    } else if (kontopobrane.getZwyklerozrachszczegolne().equals("rozrachunkowe") || kontopobrane.getZwyklerozrachszczegolne().equals("vat")) {
                                        double kwotapierwotna = Z.z(pozycja.getKwota());
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
                                        double kwotapierwotna = Z.z(pozycja.getKwota());
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
                    }
                } catch (Exception e) {
                    E.e(e);
                    throw new Exception("Istnieją konta nieprzyporządkowane do RZiS. Nie można przetworzyć danych za okres.");
                }
            }
        }
    }
    
    public void addNumbersBilansBO(List<Konto> plankont, String aktywapasywa) throws Exception {
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        for (Konto p: plankont) {
            if (p.getPelnynumer().equals("201-1-5")) {
            }
            Konto kontopobrane = p;
            if (p.getSaldoWn() != 0.0 || p.getSaldoMa() != 0.0) {
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
                                if (pozycja.getPozycjaString().equals(pozycjaBilansWn) || pozycja.getPozycjaString().equals(pozycjaBilansMa)) {
                                    if (kontopobrane.getZwyklerozrachszczegolne().equals("zwykłe")) {
                                        double kwotapierwotna = pozycja.getKwotabo();
                                        if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                            //pobieramy kwoty oraz to czy jest to przychod czy koszt
                                                if (stronaWn == false) {//jesli konto zwykle jest przyporzadowane do aktywow
                                                    if (p.getSaldoWn() != 0.0) {
                                                        pozycja.setKwotabo(kwotapierwotna+p.getSaldoWn());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoWn(), p);
                                                    } else if (p.getSaldoMa() != 0.0) {
                                                        pozycja.setKwotabo(kwotapierwotna-p.getSaldoMa());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(-p.getSaldoMa(), p);
                                                    }
                                                } else {
                                                    if (p.getSaldoMa() != 0.0) {
                                                        pozycja.setKwotabo(kwotapierwotna+p.getSaldoMa());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoMa(), p);
                                                    } else if (p.getSaldoWn() != 0.0) {
                                                        pozycja.setKwotabo(kwotapierwotna-p.getSaldoWn());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(-p.getSaldoWn(), p);
                                                    }
                                                }
                                                break;//tu break ma sens bo konto zwykle jest tylko w jednym miejscu
                                        }
                                    } else if (kontopobrane.getZwyklerozrachszczegolne().equals("rozrachunkowe") || kontopobrane.getZwyklerozrachszczegolne().equals("vat")) {
                                        double kwotapierwotna = pozycja.getKwotabo();
                                        if (p.getSaldoWn() != 0.0 || p.getSaldoMa() != 0.0) {
                                            if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                                if (stronaWn==false && stronaMa==false) {
                                                    pozycja.setKwotabo(kwotapierwotna+p.getSaldoWn()-p.getSaldoMa());
                                                    pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoWn()-p.getSaldoMa(), p);
                                                } else {
                                                    pozycja.setKwotabo(kwotapierwotna+p.getSaldoWn());
                                                    pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoWn(), p);
                                                }
                                                zrobionoWn = true;
                                            } else if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                                if (stronaWn==true && stronaMa==true) {
                                                    pozycja.setKwotabo(kwotapierwotna+p.getSaldoMa()-p.getSaldoWn());
                                                    pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoMa()-p.getSaldoWn(), p);
                                                } else {
                                                    pozycja.setKwotabo(kwotapierwotna+p.getSaldoMa());
                                                    pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoMa(), p);
                                                }
                                                zrobionoMa = true;
                                            }
                                        }
                                    } else if (kontopobrane.getZwyklerozrachszczegolne().equals("szczególne")) {
                                        double kwotapierwotna = pozycja.getKwotabo();
                                        if (p.getSaldoWn() != 0.0 || p.getSaldoMa() != 0.0) {
                                            if (aktywapasywa.equals("aktywa")) {
                                                if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                                    if (p.getSaldoWn() != 0) {
                                                        pozycja.setKwotabo(kwotapierwotna+p.getSaldoWn());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoWn(), p);
                                                    }
                                                    zrobionoWn = true;
                                                } //sa dwa idy zamiast else bo przy szczegolnych dwa salda moga byc przypisane do jednej pozycji
                                                if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                                    if (p.getSaldoMa() != 0) {
                                                        pozycja.setKwotabo(kwotapierwotna-p.getSaldoMa());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(-p.getSaldoMa(), p);
                                                    }
                                                    zrobionoMa = true;
                                                }
                                            } else {
                                                if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                                    if (p.getSaldoWn() != 0) {
                                                        pozycja.setKwotabo(kwotapierwotna-p.getSaldoWn());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(-p.getSaldoWn(), p);
                                                    }
                                                    zrobionoWn = true;
                                                } //sa dwa idy zamiast else bo przy szczegolnych dwa salda moga byc przypisane do jednej pozycji
                                                if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                                    if (p.getSaldoMa() != 0) {
                                                        pozycja.setKwotabo(kwotapierwotna+p.getSaldoMa());
                                                        pozycja.obsluzPrzyporzadkowaneKonta(p.getSaldoMa(), p);
                                                    }
                                                    zrobionoMa = true;
                                                }
                                            }
                                        }
                                    }
                                }
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                    throw new Exception("Istnieją konta nieprzyporządkowane do RZiS. Nie można przetworzyć danych za okres.");
                }
            }
        }
    }
    
    public void addNumbersBilansNowy(List<StronaWiersza> zapisynakontach, List<Konto> plankont, String aktywapasywa) throws Exception {
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
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
                                double kwotapierwotna = Z.z(pozycja.getKwota());
                                if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                    //pobieramy kwoty oraz to czy jest to przychod czy koszt
                                        if (stronaWn == false) {//jesli konto zwykle jest przyporzadowane do aktywow
                                            if (kontopobrane.getSaldoWn() > 0) {
                                                pozycja.setKwota(Z.z(kwotapierwotna+kontopobrane.getSaldoWn()));
                                                pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                                            } else {
                                                pozycja.setKwota(Z.z(kwotapierwotna-kontopobrane.getSaldoMa()));
                                                pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                                            }
                                        } else {
                                            if (kontopobrane.getSaldoMa() > 0) {
                                                pozycja.setKwota(Z.z(kwotapierwotna+kontopobrane.getSaldoMa()));
                                                pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                                            } else {
                                                pozycja.setKwota(Z.z(kwotapierwotna-kontopobrane.getSaldoWn()));
                                                pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                                            }
                                        }
                                }
                            } else if (kontopobrane.getZwyklerozrachszczegolne().equals("rozrachunkowe") || kontopobrane.getZwyklerozrachszczegolne().equals("vat")) {
                                double kwotapierwotna = Z.z(pozycja.getKwota());
                                if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                    if (stronaWn==false && stronaMa==false) {
                                        pozycja.setKwota(Z.z(kwotapierwotna+kontopobrane.getSaldoWn()-kontopobrane.getSaldoMa()));
                                    } else {
                                        pozycja.setKwota(Z.z(kwotapierwotna+kontopobrane.getSaldoWn()));
                                    }
                                    pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                                } else if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                    if (stronaWn==true && stronaMa==true) {
                                        pozycja.setKwota(Z.z(kwotapierwotna+kontopobrane.getSaldoMa()-kontopobrane.getSaldoWn()));
                                    } else {
                                        pozycja.setKwota(Z.z(kwotapierwotna+kontopobrane.getSaldoMa()));
                                    }
                                    pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                                }
                            } else if (kontopobrane.getZwyklerozrachszczegolne().equals("szczególne")) {
                                double kwotapierwotna = Z.z(pozycja.getKwota());
                                if (aktywapasywa.equals("aktywa")) {
                                    if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                        if (kontopobrane.getSaldoWn() != 0) {
                                            pozycja.setKwota(Z.z(kwotapierwotna+kontopobrane.getSaldoWn()));
                                            pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                                        }
                                    } //sa dwa idy zamiast else bo przy szczegolnych dwa salda moga byc przypisane do jednej pozycji
                                    if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                        if (kontopobrane.getSaldoMa() != 0) {
                                            pozycja.setKwota(Z.z(kwotapierwotna-kontopobrane.getSaldoMa()));
                                            pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaMa, stronaWiersza);
                                        }
                                    }
                                } else {
                                    if ((pozycja.getPozycjaString()).equals(pozycjaBilansWn) && pozycja.isPrzychod0koszt1() == stronaWn) {
                                        if (kontopobrane.getSaldoWn() != 0) {
                                            pozycja.setKwota(Z.z(kwotapierwotna-kontopobrane.getSaldoWn()));
                                            pozycja.obsluzPrzyporzadkowaneStronaWiersza(kwotaWn, stronaWiersza);
                                        }
                                    } //sa dwa idy zamiast else bo przy szczegolnych dwa salda moga byc przypisane do jednej pozycji
                                    if ((pozycja.getPozycjaString()).equals(pozycjaBilansMa) && pozycja.isPrzychod0koszt1() == stronaMa) {
                                        if (kontopobrane.getSaldoMa() != 0) {
                                            pozycja.setKwota(Z.z(kwotapierwotna+kontopobrane.getSaldoMa()));
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
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        List<TreeNodeExtended> parents = Collections.synchronizedList(new ArrayList<>());
        do {
            int lowestlevel = ustaldepth(finallNodes);
            parents.clear();
            for (TreeNodeExtended p : finallNodes) {
                //ta fomula wyklyczamy roota i nody z formula do dodawania i odliczania kwot
                if ((p.getParent()) instanceof TreeNodeExtended && !(p.getParent().getData() instanceof String) && p.getFormula().isEmpty()) {
                    if (((PozycjaRZiSBilans) p.getData()).getLevel() == lowestlevel) {
                        double kwotaparent = ((TreeNodeExtended) p.getParent()).getKwota();
                        double kwotanode = p.getKwota();
                        ((PozycjaRZiSBilans) p.getParent().getData()).setKwota(Z.z(kwotaparent + kwotanode));
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
    
    public void sumNodesNar(String mckoncowy) {
        List<String> mce = Mce.getMiesiaceGranica(mckoncowy);
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        List<TreeNodeExtended> parents = Collections.synchronizedList(new ArrayList<>());
        do {
            int lowestlevel = ustaldepth(finallNodes);
            parents.clear();
            for (TreeNodeExtended p : finallNodes) {
                //ta fomula wyklyczamy roota i nody z formula do dodawania i odliczania kwot
                if ((p.getParent()) instanceof TreeNodeExtended && !(p.getParent().getData() instanceof String) && p.getFormula().isEmpty()) {
                    if (((PozycjaRZiSBilans) p.getData()).getLevel() == lowestlevel) {
                        for (String mc : mce) {
                            double kwotaparent = ((PozycjaRZiS) p.getParent().getData()).getMce().get(mc);
                            double kwotanode = ((PozycjaRZiS) p.getData()).getMce().get(mc);
                            ((PozycjaRZiS) p.getParent().getData()).getMce().put(mc,(Z.z(kwotaparent + kwotanode)));
                        }
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
    
    public void sumNodesSlot(String kolumna) {
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        List<TreeNodeExtended> parents = Collections.synchronizedList(new ArrayList<>());
        do {
            int lowestlevel = ustaldepth(finallNodes);
            parents.clear();
            for (TreeNodeExtended p : finallNodes) {
                //ta fomula wyklyczamy roota i nody z formula do dodawania i odliczania kwot
                if ((p.getParent()) instanceof TreeNodeExtended && !(p.getParent().getData() instanceof String) && p.getFormula().isEmpty()) {
                    if (((PozycjaRZiSBilans) p.getData()).getLevel() == lowestlevel) {
                            double kwotaparent = ((PozycjaRZiS) p.getParent().getData()).getMce().get(kolumna);
                            double kwotanode = ((PozycjaRZiS) p.getData()).getMce().get(kolumna);
                            ((PozycjaRZiS) p.getParent().getData()).getMce().put(kolumna,(Z.z(kwotaparent + kwotanode)));
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
    
    public void sumNodesBO() {
        List<TreeNodeExtended> finallNodes = Collections.synchronizedList(new ArrayList<>());
        this.getFinallChildren(finallNodes);
        List<TreeNodeExtended> parents = Collections.synchronizedList(new ArrayList<>());
        do {
            int lowestlevel = ustaldepth(finallNodes);
            parents.clear();
            for (TreeNodeExtended p : finallNodes) {
                //ta fomula wyklyczamy roota i nody z formula do dodawania i odliczania kwot
                if ((p.getParent()) instanceof TreeNodeExtended && !(p.getParent().getData() instanceof String) && p.getFormula().isEmpty()) {
                    if (((PozycjaRZiSBilans) p.getData()).getLevel() == lowestlevel) {
                        double kwotaparent = ((TreeNodeExtended) p.getParent()).getKwotabo();
                        double kwotanode = p.getKwotabo();
                        ((PozycjaRZiSBilans) p.getParent().getData()).setKwotabo(Z.z(kwotaparent + kwotanode));
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

    private int ustaldepth(List<TreeNodeExtended> nodes) {
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
        List<TreeNode> finallNodes = (List<TreeNode>) this.getChildren();
        for (TreeNode p : finallNodes) {
            try {
                if (!((TreeNodeExtended) p).getFormula().isEmpty()) {
                    String formula = ((TreeNodeExtended) p).getFormula();
                    //musze zrobic formule z parserem
                    int formulalength = formula.length();
                    String[] formulaParse = parseall(formula);
                    double wynik = dotheMath(finallNodes, formulaParse, formulalength);
                    ((TreeNodeExtended) p).setKwota(wynik);
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
    }
    
    public void resolveFormulasNar(String mckoncowy) {
        List<String> mce = Mce.getMiesiaceGranica(mckoncowy);
        List<TreeNode> finallNodes = (List<TreeNode>) this.getChildren();
        for (TreeNode p : finallNodes) {
            try {
                if (!((TreeNodeExtended) p).getFormula().isEmpty()) {
                    String formula = ((TreeNodeExtended) p).getFormula();
                    int formulalength = formula.length();
                    String[] formulaParse = parseall(formula);
                    for (String r : mce) {
                        double wynik = dotheMathNar(finallNodes, formulaParse, formulalength,r);
                        ((PozycjaRZiS) p.getData()).getMce().put(r,wynik);
                    }
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
    }
    
    public void resolveFormulasSlot(String kolumna) {
        List<TreeNode> finallNodes = (List<TreeNode>) this.getChildren();
        for (TreeNode p : finallNodes) {
            try {
                if (!((TreeNodeExtended) p).getFormula().isEmpty()) {
                    String formula = ((TreeNodeExtended) p).getFormula();
                    int formulalength = formula.length();
                    String[] formulaParse = parseall(formula);
                    double wynik = dotheMathNar(finallNodes, formulaParse, formulalength,kolumna);
                    ((PozycjaRZiS) p.getData()).getMce().put(kolumna,wynik);
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
    }
    
    public void resolveFormulasBO() {
        List<TreeNode> finallNodes = (List<TreeNode>) this.getChildren();
        for (TreeNode p : finallNodes) {
            try {
                if (!((TreeNodeExtended) p).getFormula().isEmpty()) {
                    String formula = ((TreeNodeExtended) p).getFormula();
                    int formulalength = formula.length();
                    String[] formulaParse = parseall(formula);
                    ((TreeNodeExtended) p).getChildren();
                    double wynik = dotheMathBO(finallNodes, formulaParse, formulalength);
                    ((TreeNodeExtended) p).setKwotabo(wynik);
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
    
    private double getKwotabo() {
        return ((PozycjaRZiSBilans) this.getData()).getKwotabo();
    }

    private void setKwotabo(double kwotabo) {
        ((PozycjaRZiSBilans) this.getData()).setKwotabo(kwotabo);
    }

    private double dotheMath(List<TreeNode> finallNodes, String[] formulaParse, int formulalength) {
        double wynik = findBypozycjaSymbol(finallNodes, formulaParse[0]).getKwota();
        for (int i = 1; i < formulalength; i++) {
            String znak = formulaParse[i++];
            if (znak.equals(">") && formulaParse.length == 3) {
                if (wynik < 0.0) {
                    wynik = 0.0;
                }
            } else if (znak.equals("<") && formulaParse.length == 3) {
                if (wynik > 0.0) {
                    wynik = 0.0;
                }
            } else {
                TreeNodeExtended drugi = findBypozycjaSymbol(finallNodes, formulaParse[i]);
                if (znak.equals("-")) {
                    wynik -= drugi.getKwota();
                } else {
                    wynik += drugi.getKwota();
                }
            }
        }
        return wynik;
    }
    
    private double dotheMathNar(List<TreeNode> finallNodes, String[] formulaParse, int formulalength, String mc) {
        double wynik = ((PozycjaRZiS) findBypozycjaSymbol(finallNodes, formulaParse[0]).getData()).getMce().get(mc);
        for (int i = 1; i < formulalength; i++) {
            String znak = formulaParse[i++];
            if (znak.equals(">") && formulaParse.length == 3) {
                if (wynik < 0.0) {
                    wynik = 0.0;
                }
            } else if (znak.equals("<") && formulaParse.length == 3) {
                if (wynik > 0.0) {
                    wynik = 0.0;
                }
            } else {
                TreeNodeExtended drugi = findBypozycjaSymbol(finallNodes, formulaParse[i]);
                double kwota = ((PozycjaRZiS) drugi.getData()).getMce().get(mc);
                if (znak.equals("-")) {
                    wynik -= kwota;
                } else {
                    wynik += kwota;
                }
            }
        }
        return wynik;
    }
    
    private double dotheMathBO(List<TreeNode> finallNodes, String[] formulaParse, int formulalength) {
        double wynik = findBypozycjaSymbol(finallNodes, formulaParse[0]).getKwotabo();
        for (int i = 1; i < formulalength; i++) {
            String znak = formulaParse[i++];
            if (znak.equals(">") && formulaParse.length == 3) {
                if (wynik < 0.0) {
                    wynik = 0.0;
                }
            } else if (znak.equals("<") && formulaParse.length == 3) {
                if (wynik > 0.0) {
                    wynik = 0.0;
                }
            } else {
                TreeNodeExtended drugi = findBypozycjaSymbol(finallNodes, formulaParse[i]);
                if (znak.equals("-")) {
                    wynik -= drugi.getKwotabo();
                } else {
                    wynik += drugi.getKwotabo();
                }
            }
        }
        return wynik;
    }

    private TreeNodeExtended findBypozycjaSymbol(List<TreeNode> finallNodes, String pozycjastring) {
        for (TreeNode p : finallNodes) {
            if (((TreeNodeExtended) p).getSymbol().equals(pozycjastring)) {
                return (TreeNodeExtended) p;
            }
        }
        return null;
    }

    public void reset() {
         this.getChildren().clear();
    }

    private void uporzadkujbiezaca(List<T> biezaca) {
        if (biezaca != null && biezaca.size() > 0) {
            Object pobrany = biezaca.get(0);
            if (pobrany.getClass().getSimpleName().equals("Konto")) {
                Collections.sort((List<Konto>) biezaca, new Kontocomparator());
            }
            if (pobrany.getClass().getSimpleName().equals("KontoBO")) {
                Collections.sort((List<KontoBO>) biezaca, new KontoBOcomparator());
            }
        }
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

 
    
    public static void main(String[] args) {
        String formula = "A.I+A.II-A.III";
        String[] s = parseall(formula);
    }
    
    private static String[] parseall(String f) {
        String[] suma = null;
        if (f != null) {
            String[] formula = formulaparser(f);
            String[] znak = znakparser(formula, f);
            int len = formula.length+znak.length;
            suma = new String[len];
            int j = 0;
            for (int i = 0; i < formula.length; i++) {
                suma[j++] = formula[i];
                if (j < suma.length) {
                    suma[j++] = znak[i];
                }
            }
        }
        return suma;
    }
    
    private static String[] formulaparser(String formula) {
        return formula != null ? formula.split("[\\+|\\-|\\<|\\>]") : null;
    }
    
    private static String[] znakparser(String[] pola, String formula) {
        String[] znaki = new String[pola.length-1];
        for (int i = 0; i < pola.length-1; i++) {
            int ileobciac = pola[i].length();
            formula = formula.substring(ileobciac);
            String znak = formula.substring(0,1);
            znaki[i] = znak;
            formula = formula.substring(1);
        }
        return znaki;
    }

        
    
}
