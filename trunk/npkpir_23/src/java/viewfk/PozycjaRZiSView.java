/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontocomparator;
import daoFK.KontoDAOfk;
import daoFK.KontoZapisyFKDAO;
import embeddablefk.PozycjaRZiS;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.Kontozapisy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
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
    private static TreeNode wybranynodekonta;
    private PozycjaRZiS selected;
    private ArrayList<TreeNodeExtended> finallNodes;
    private static ArrayList<PozycjaRZiS> pozycje;
    private static ArrayList<Konto> przyporzadkowanekonta;
    private List<Konto> wykazkont;
    @Inject
    private KontoDAOfk kontoDAO;
    private static String wybranapozycja;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;

    public PozycjaRZiSView() {
        this.wykazkont = new ArrayList<>();
        this.root = new TreeNodeExtended("root", null);
        this.przyporzadkowanekonta = new ArrayList<>();
        this.finallNodes = new ArrayList<TreeNodeExtended>();
        pozycje = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        List<Konto> pobranekonta = kontoDAO.findKontaPotomne("0", "wynikowe");
        zmodyfikujwykazkont(pobranekonta);
        Collections.sort(wykazkont, new Kontocomparator());
        //(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, double kwota)
        pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Przychody netto ze sprzedaży i zrównane z nimi, w tym:", false));
        pozycje.add(new PozycjaRZiS(2, "A.I", "I", 1, 1, "Przychody netto ze sprzedaży produktów", false, 0.0));
        pozycje.add(new PozycjaRZiS(3, "A.II", "II", 1, 1, "Zmiana stanu produktów", false, 0.0));
        pozycje.add(new PozycjaRZiS(4, "A.III", "III", 1, 1, "Koszt wytworzenia produktów na własne potrzeby jednostki", true, 0.0));
        pozycje.add(new PozycjaRZiS(5, "A.IV", "IV", 1, 1, "Przychody netto ze sprzedaży towarów i materiałów", false, 0.0));
        pozycje.add(new PozycjaRZiS(6, "B", "B", 0, 0, "Koszty działalności operacyjnej", true));
        pozycje.add(new PozycjaRZiS(7, "B.I", "I", 6, 1, "Amortyzacja", true));
        pozycje.add(new PozycjaRZiS(8, "B.II", "II", 6, 1, "Zużycie materiałów i energii", true, 0.0));
        pozycje.add(new PozycjaRZiS(9, "B.III", "III", 6, 1, "Usługi obce", true, 0.0));
        pozycje.add(new PozycjaRZiS(10, "B.IV", "IV", 6, 1, "Podatki i  opłaty", true, 0.0));
        pozycje.add(new PozycjaRZiS(11, "B.V", "V", 6, 1, "Wynagrodzenia", true, 0.0));
        pozycje.add(new PozycjaRZiS(12, "B.I.1", "1", 7, 2, "amortyzacja kup", true, 0.0));
        pozycje.add(new PozycjaRZiS(13, "B.I.2", "2", 7, 2, "amortyzacja nkup", true));
        pozycje.add(new PozycjaRZiS(14, "B.I.2.a)", "a)", 13, 3, "bobopo", true, 0.0));
        pozycje.add(new PozycjaRZiS(15, "C", "C", 0, 0, "Zysk (strata) ze sprzedaży (A-B)", false, "A-B"));
        pozycje.add(new PozycjaRZiS(16, "D", "D", 0, 0, "Pozostałe truey operacyjne", false));
        pozycje.add(new PozycjaRZiS(17, "D.I", "I", 16, 1, "Zysk z niefinansowych aktywów trwałych", false, 0.0));
        pozycje.add(new PozycjaRZiS(18, "D.II", "II", 16, 1, "Dotacje", false, 0.0));
        pozycje.add(new PozycjaRZiS(19, "D.III", "III", 16, 1, "Inne truey operacyjne", false, 0.0));
        pozycje.add(new PozycjaRZiS(20, "E", "E", 0, 0, "Pozostałe koszty operacyjne", true));
        pozycje.add(new PozycjaRZiS(21, "E.I", "I", 20, 1, "Strata z niefinansowych aktywów trwałych", true, 0.0));
        pozycje.add(new PozycjaRZiS(22, "E.II", "II", 20, 1, "Aktualizacja aktywów niefinansowych", true, 0.0));
        pozycje.add(new PozycjaRZiS(23, "E.III", "III", 20, 1, "Inne koszty operacyjne", true, 0.0));
        pozycje.add(new PozycjaRZiS(24, "F", "F", 0, 0, "Zysk (strata) ze działalności operacyjnej (C+D-E)", false, "C+D-E"));
        //tutaj dzieje sie magia :) tak funkcja przeksztalca baze danych w nody
        getNodes();
        List<Kontozapisy> zapisy = kontoZapisyFKDAO.findAll();
        List<Konto> plankont = kontoDAO.findAll();
        root.addNumbers(zapisy, plankont);
        root.sumNodes();
        root.resolveFormulas();
        root.expandAll();
        level = root.ustaldepthDT(pozycje)-1;
    }

    private void drugiinit() {
        wykazkont.clear();
        List<Konto> pobranekonta = kontoDAO.findKontaPotomne("0", "wynikowe");
        zmodyfikujwykazkont(pobranekonta);
        Collections.sort(wykazkont, new Kontocomparator());
    }
    
    private void zmodyfikujwykazkont(List<Konto> macierzyste) {
        for (Konto p: macierzyste) {
            if (p.getPozycja() == null) {
                if (!wykazkont.contains(p)) {
                    wykazkont.add(p);
                }
            } else if (p.getPozycja().equals("analit")) {
                List<Konto> potomki = kontoDAO.findKontaPotomne(p.getPelnynumer());
                for (Konto r : potomki) {
                    zmodyfikujwykazkont(potomki);
                }
            } 
        }
    }
    
     //tworzy nody z bazy danych dla tablicy nodow plan kont
    private void getNodes(){
        root.createTreeNodesForElement(pozycje);
    }
    
    public void rozwinwszystkie(){
        getNodes();
        level = root.ustaldepthDT(pozycje)-1;
        root.expandAll();
    }  
    
    private static int level = 0;
    public void rozwin(){
        int maxpoziom = root.ustaldepthDT(pozycje);
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
    public void rozwinrzadanalityki (Konto konto) {
        List<Konto> lista = kontoDAO.findKontaPotomne(konto.getPelnynumer());
        if (lista.size()>0) {
            wykazkont.addAll(kontoDAO.findKontaPotomne(konto.getPelnynumer()));
            wykazkont.remove(konto);
            Collections.sort(wykazkont,new Kontocomparator());
        } else {
            Msg.msg("e", "Konto nie posiada analityk");
        }
    }
    
    public void onKontoDrop(Konto konto) {
        if (wybranapozycja==null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            przyporzadkowanekonta.add(konto);
            Collections.sort(przyporzadkowanekonta,new Kontocomparator());
            wykazkont.remove(konto);
            konto.setPozycja(wybranapozycja);
            konto.setPozycjonowane(true);
            kontoDAO.edit(konto);
            if (konto.isMapotomkow()==true) {
                przyporzadkujpotkomkow(konto.getPelnynumer(), wybranapozycja);
            }
            if (konto.getMacierzysty()>0) {
                oznaczmacierzyste(konto.getMacierzyste());
            }
           
        }
        drugiinit();
    }
    
    public void onKontoRemove(Konto konto) {
        wykazkont.add(konto);
        Collections.sort(wykazkont,new Kontocomparator());
        przyporzadkowanekonta.remove(konto);
        konto.setPozycja(null);
        konto.setPozycjonowane(false);
        kontoDAO.edit(konto);
        //zerujemy potomkow
           if (konto.isMapotomkow()==true) {
               przyporzadkujpotkomkow(konto.getPelnynumer(), null);
           }
        //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
           if (konto.getMacierzysty()>0) {
                odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer());
            }
        drugiinit();
    }
    private void przyporzadkujpotkomkow(String konto, String pozycja) {
        List<Konto> lista = kontoDAO.findKontaPotomne(konto);
        for (Konto p : lista) {
            if (pozycja == null) {
                p.setPozycja(null);
            } else {
                p.setPozycja(pozycja);
            }
            kontoDAO.edit(p);
            if (p.isMapotomkow()==true) {
                    przyporzadkujpotkomkow(p.getPelnynumer(), pozycja);
            }
        }
    }
    
    private void oznaczmacierzyste (String macierzyste) {
        Konto konto = kontoDAO.findKonto(macierzyste);
        konto.setPozycja("analit");
        kontoDAO.edit(konto);
        if (konto.getMacierzysty()>0) {
            oznaczmacierzyste(konto.getMacierzyste());
        }
    }
    private void odznaczmacierzyste (String macierzyste, String kontoanalizowane) {
            List<Konto> siostry = kontoDAO.findKontaPotomne(macierzyste);
              if (siostry.size() > 1) {
                  boolean sainne = false;
                  for (Konto p : siostry) {
                      if (p.isPozycjonowane()==true && !p.getPelnynumer().equals(kontoanalizowane)) {
                          sainne = true;
                      }
                  }
                if (sainne==false) {
                    Konto konto = kontoDAO.findKonto(macierzyste);
                    konto.setPozycja(null);
                    kontoDAO.edit(konto);
                    if (konto.getMacierzysty()>0) {
                        odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer());
                    }
                }
              }
    }
    
    public void wybranopozycjeRZiS() {
        wybranapozycja = ((PozycjaRZiS) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(wyszukajprzyporzadkowane(wybranapozycja));
        Msg.msg("i", "Wybrano pozycję "+((PozycjaRZiS) wybranynodekonta.getData()).getNazwa());
    }
   
    private List<Konto> wyszukajprzyporzadkowane(String pozycja) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowane(pozycja, "wynikowe");
        List<Konto> returnlist = new ArrayList<>();
        int level = 0;
        for (Konto p : lista) {
            if (p.getPozycja().equals(pozycja)) {
                returnlist.add(p);
            }
        }
        return returnlist;
        
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

    public ArrayList<Konto> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

    public void setPrzyporzadkowanekonta(ArrayList<Konto> przyporzadkowanekonta) {
        PozycjaRZiSView.przyporzadkowanekonta = przyporzadkowanekonta;
    }

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public String getWybranapozycja() {
        return wybranapozycja;
    }

    public TreeNode getWybranynodekonta() {
        return wybranynodekonta;
    }

    public void setWybranynodekonta(TreeNode wybranynodekonta) {
        PozycjaRZiSView.wybranynodekonta = wybranynodekonta;
    }

  
    
    

    
    
    
    
    
}
