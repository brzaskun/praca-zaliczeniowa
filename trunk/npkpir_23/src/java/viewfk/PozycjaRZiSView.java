/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontocomparator;
import daoFK.KontoDAOfk;
import embeddablefk.PozycjaRZiS;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

    public PozycjaRZiSView() {
        this.wykazkont = new ArrayList<>();
        this.root = new TreeNodeExtended("root", null);
        this.przyporzadkowanekonta = new ArrayList<>();
        this.finallNodes = new ArrayList<TreeNodeExtended>();
        pozycje = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        wykazkont = kontoDAO.findKontaPotomne("0");
        Collections.sort(wykazkont, new Kontocomparator());
        //(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, double kwota)
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
        pozycje.add(new PozycjaRZiS(15, "C", "C", 0, 0, "Zysk (strata) ze sprzedaży (A-B)", true, "A-B"));
        pozycje.add(new PozycjaRZiS(16, "D", "D", 0, 0, "Pozostałe przychody operacyjne", true));
        pozycje.add(new PozycjaRZiS(17, "D.I", "I", 16, 1, "Zysk z niefinansowych aktywów trwałych", true, 100.0));
        pozycje.add(new PozycjaRZiS(18, "D.II", "II", 16, 1, "Dotacje", true, 200.0));
        pozycje.add(new PozycjaRZiS(19, "D.III", "III", 16, 1, "Inne przychody operacyjne", true, 300.0));
        pozycje.add(new PozycjaRZiS(20, "E", "E", 0, 0, "Pozostałe koszty operacyjne", true));
        pozycje.add(new PozycjaRZiS(21, "E.I", "I", 20, 1, "Strata z niefinansowych aktywów trwałych", true, 100.0));
        pozycje.add(new PozycjaRZiS(22, "E.II", "II", 20, 1, "Aktualizacja aktywów niefinansowych", true, 200.0));
        pozycje.add(new PozycjaRZiS(23, "E.III", "III", 20, 1, "Inne koszty operacyjne", true, 250.0));
        pozycje.add(new PozycjaRZiS(24, "F", "F", 0, 0, "Zysk (strata) ze działalności operacyjnej (C+D-E)", true, "C+D-E"));
        //tutaj dzieje sie magia :) tak funkcja przeksztalca baze danych w nody
        getNodes();
        root.sumNodes();
        root.resolveFormulas();
        root.expandAll();
        level = root.ustaldepthDT(pozycje)-1;
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
    
    public void onKontoDrop(Konto konto) {
        przyporzadkowanekonta.add(konto);
        wykazkont.remove(konto);
    }
    public void onKontoRemove(Konto konto) {
        wykazkont.add(konto);
        przyporzadkowanekonta.remove(konto);
    }
    
    public void wybranopozycjeRZiS() {
        wybranapozycja = ((PozycjaRZiS) wybranynodekonta.getData()).getPozycjaString();
        Msg.msg("i", "Wybrano pozycję "+((PozycjaRZiS) wybranynodekonta.getData()).getNazwa());
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
