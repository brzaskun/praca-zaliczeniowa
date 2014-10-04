    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PozycjaRZiSFKBean;
import comparator.Kontocomparator;
import converter.RomNumb;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjarzisDAO;
import daoFK.PozycjaRZiSDAO;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.Kontopozycjarzis;
import entityfk.KontopozycjarzisPK;
import entityfk.PozycjaRZiSBilans;
import entityfk.Rzisuklad;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.model.TreeNode;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PozycjaRZiSView implements Serializable {
    private static final long serialVersionUID = 1L;

    private static TreeNode wybranynodekonta;
    private static ArrayList<PozycjaRZiSBilans> pozycje;
    private static ArrayList<PozycjaRZiSBilans> pozycje_old;
    private static ArrayList<Konto> przyporzadkowanekonta;
    private static String wybranapozycja;
    private static int level = 0;

    private TreeNodeExtended root;
    private TreeNodeExtended rootUklad;
    private TreeNodeExtended rootProjekt;
    private TreeNodeExtended rootProjektKonta;
    private TreeNode[] selectedNodes;
    private PozycjaRZiSBilans nowyelementRZiS;
    private PozycjaRZiSBilans selected;
    private ArrayList<TreeNodeExtended> finallNodes;
    private List<Konto> wykazkont;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject 
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private Rzisuklad rzisuklad;
    @Inject
    private KontopozycjarzisDAO kontopozycjarzisDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public PozycjaRZiSView() {
        this.wykazkont = new ArrayList<>();
        this.nowyelementRZiS = new PozycjaRZiSBilans();
        this.root = new TreeNodeExtended("root", null);
        this.rootUklad = new TreeNodeExtended("root", null);
        this.rootProjekt = new TreeNodeExtended("root", null);
        this.rootProjektKonta = new TreeNodeExtended("root", null);
        PozycjaRZiSView.przyporzadkowanekonta = new ArrayList<>();
        this.finallNodes = new ArrayList<TreeNodeExtended>();
        pozycje = new ArrayList<>();
        pozycje_old = new ArrayList<>();
    }

    @PostConstruct
    private void init() {

        //(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, double kwota)
        pozycje_old.add(new PozycjaRZiSBilans(1, "A", "A", 0, 0, "Przychody netto ze sprzedaży i zrównane z nimi, w tym:", false));
        pozycje_old.add(new PozycjaRZiSBilans(2, "A.I", "I", 1, 1, "Przychody netto ze sprzedaży produktów", false, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(3, "A.II", "II", 1, 1, "Zmiana stanu produktów", false, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(4, "A.III", "III", 1, 1, "Koszt wytworzenia produktów na własne potrzeby jednostki", true, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(5, "A.IV", "IV", 1, 1, "Przychody netto ze sprzedaży towarów i materiałów", false, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(6, "B", "B", 0, 0, "Koszty działalności operacyjnej", true));
        pozycje_old.add(new PozycjaRZiSBilans(7, "B.I", "I", 6, 1, "Amortyzacja", true));
        pozycje_old.add(new PozycjaRZiSBilans(8, "B.II", "II", 6, 1, "Zużycie materiałów i energii", true, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(9, "B.III", "III", 6, 1, "Usługi obce", true, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(10, "B.IV", "IV", 6, 1, "Podatki i  opłaty", true, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(11, "B.V", "V", 6, 1, "Wynagrodzenia", true, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(12, "B.I.1", "1", 7, 2, "amortyzacja kup", true, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(13, "B.I.2", "2", 7, 2, "amortyzacja nkup", true));
        pozycje_old.add(new PozycjaRZiSBilans(14, "B.I.2.a)", "a)", 13, 3, "bobopo", true, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(15, "C", "C", 0, 0, "Zysk (strata) ze sprzedaży (A-B)", false, "A-B"));
        pozycje_old.add(new PozycjaRZiSBilans(16, "D", "D", 0, 0, "Pozostałe truey operacyjne", false));
        pozycje_old.add(new PozycjaRZiSBilans(17, "D.I", "I", 16, 1, "Zysk z niefinansowych aktywów trwałych", false, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(18, "D.II", "II", 16, 1, "Dotacje", false, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(19, "D.III", "III", 16, 1, "Inne truey operacyjne", false, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(20, "E", "E", 0, 0, "Pozostałe koszty operacyjne", true));
        pozycje_old.add(new PozycjaRZiSBilans(21, "E.I", "I", 20, 1, "Strata z niefinansowych aktywów trwałych", true, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(22, "E.II", "II", 20, 1, "Aktualizacja aktywów niefinansowych", true, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(23, "E.III", "III", 20, 1, "Inne koszty operacyjne", true, 0.0));
        pozycje_old.add(new PozycjaRZiSBilans(24, "F", "F", 0, 0, "Zysk (strata) ze działalności operacyjnej (C+D-E)", false, "C+D-E"));
        //tutaj dzieje sie magia :) tak funkcja przeksztalca baze danych w nody
        pozycje.addAll(pozycjaRZiSDAO.findAll());
//        if (pozycje.size() == 0) {
//            pozycje.add(new PozycjaRZiSBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję RZiS", false));
//            Msg.msg("i", "Dodaje pusta pozycje");
//        }
//        if (pozycje.size() > 0) {
//            List<Kontozapisy> zapisy = kontoZapisyFKDAO.findAll();
//            List<Konto> plankont = kontoDAO.findAll();
//            ustawRoota(rootProjekt, pozycje, zapisy, plankont);
//        }

    }

    public void pobierzuklad() {
        pozycje = new ArrayList<>();
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(rzisuklad));
        } catch (Exception e) {
        }
        if (pozycje.isEmpty()) {
            pozycje.add(new PozycjaRZiSBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję RZiS", false));
            Msg.msg("i", "Dodaje pusta pozycje");
        }
        rootProjekt = new TreeNodeExtended("root", null);
        PozycjaRZiSFKBean.ustawRootaprojekt(rootProjekt, pozycje);
        level = PozycjaRZiSFKBean.ustawLevel(rootProjekt, pozycje);
        Msg.msg("i", "Pobrano układ " + rzisuklad.getRzisukladPK().getUklad());
    }

    public void pobierzukladprzeglad() {
        pozycje = new ArrayList<>();
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(rzisuklad));
        } catch (Exception e) {
        }
        if (pozycje.isEmpty()) {
            pozycje.add(new PozycjaRZiSBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję RZiS", false));
            Msg.msg("i", "Dodaje pusta pozycje");
        }
        root = new TreeNodeExtended("root", null);
        List<StronaWiersza> zapisy = stronaWierszaDAO.findStronaByPodatnikRokWalutaWynik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "PLN");
        List<Konto> plankont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu());
        PozycjaRZiSFKBean.ustawRoota(root, pozycje, zapisy, plankont);
        level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
        Msg.msg("i", "Pobrano układ " + rzisuklad.getRzisukladPK().getUklad());
    }

    public void pobierzukladkonto() {
        PozycjaRZiSFKBean.pobierzzachowanepozycjedlakont(kontoDAO, kontopozycjarzisDAO, rzisuklad);
        drugiinit();
        pozycje = new ArrayList<>();
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(rzisuklad));
        } catch (Exception e) {
        }
        if (pozycje.isEmpty()) {
            pozycje.add(new PozycjaRZiSBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję RZiS", false));
            Msg.msg("i", "Dodaje pusta pozycje");
        }
        rootProjektKonta = new TreeNodeExtended("root", null);
        PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektKonta, pozycje);
        level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
        Msg.msg("i", "Pobrano układ " + rzisuklad.getRzisukladPK().getUklad());
    }

    private void drugiinit() {
        wykazkont.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView.getPodatnikWpisu(), "0", "wynikowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalityki(pobraneKontaSyntetyczne, wykazkont, kontoDAO, wpisView.getPodatnikWpisu());
        Collections.sort(wykazkont, new Kontocomparator());
    }

    public void rozwinwszystkie() {
        root.createTreeNodesForElement(pozycje);
        level = root.ustaldepthDT(pozycje) - 1;
        root.expandAll();
    }

    public void rozwin() {
        int maxpoziom = root.ustaldepthDT(pozycje);
        if (level < --maxpoziom) {
            root.expandLevel(level++);
        }
    }

    public void zwinwszystkie() {
        root.createTreeNodesForElement(pozycje);
        root.foldAll();
        level = 0;
    }

    public void zwin() {
        root.foldLevel(--level);
    }
    
    public void rozwinwszystkie(TreeNodeExtended root) {
        root.createTreeNodesForElement(pozycje);
        level = root.ustaldepthDT(pozycje) - 1;
        root.expandAll();
    }

    public void rozwin(TreeNodeExtended root) {
        int maxpoziom = root.ustaldepthDT(pozycje);
        if (level < --maxpoziom) {
            root.expandLevel(level++);
        }
    }

    public void zwinwszystkie(TreeNodeExtended root) {
        root.createTreeNodesForElement(pozycje);
        root.foldAll();
        level = 0;
    }

    public void zwin(TreeNodeExtended root) {
        root.foldLevel(--level);
    }

    public void rozwinrzadanalityki(Konto konto) {
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikWpisu(), konto.getPelnynumer());
        if (lista.size() > 0) {
            wykazkont.addAll(kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikWpisu(), konto.getPelnynumer()));
            wykazkont.remove(konto);
            Collections.sort(wykazkont, new Kontocomparator());
        } else {
            Msg.msg("e", "Konto nie posiada analityk");
        }
    }

    public void onKontoDrop(Konto konto) {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            //to duperele porzadkujace sytuacje w okienkach
            przyporzadkowanekonta.add(konto);
            Collections.sort(przyporzadkowanekonta, new Kontocomparator());
            wykazkont.remove(konto);
            //czesc przekazujaca przyporzadkowanie do konta do wymiany
            konto.setPozycja(wybranapozycja);
            konto.setPozycjonowane(true);
            kontoDAO.edit(konto);
            //czesc nanoszaca informacje na potomku
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkow(konto.getPelnynumer(), wybranapozycja, kontoDAO, wpisView.getPodatnikWpisu());
            }
            //czesc nanoszaca informacje na macierzyste
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.oznaczmacierzyste(konto.getMacierzyste(), kontoDAO, wpisView.getPodatnikWpisu());
            }

        }
        drugiinit();
    }

    public void onKontoRemove(Konto konto) {
        wykazkont.add(konto);
        Collections.sort(wykazkont, new Kontocomparator());
        przyporzadkowanekonta.remove(konto);
        konto.setPozycja(null);
        konto.setPozycjonowane(false);
        kontoDAO.edit(konto);
        //zerujemy potomkow
        if (konto.isMapotomkow() == true) {
            PozycjaRZiSFKBean.przyporzadkujpotkomkow(konto.getPelnynumer(), null, kontoDAO, wpisView.getPodatnikWpisu());
        }
        //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
        if (konto.getMacierzysty() > 0) {
            PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikWpisu());
        }
        drugiinit();
    }

    public void wybranopozycjeRZiS() {
        wybranapozycja = ((PozycjaRZiSBilans) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowane(kontoDAO, wybranapozycja));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaRZiSBilans) wybranynodekonta.getData()).getNazwa());
    }

    public void dodajnowapozycje(String syntetycznaanalityczna) {
        if (syntetycznaanalityczna.equals("syntetyczna")) {
            //dodaje nowa syntetyke
            if (pozycje.get(0).getNazwa().equals("Kliknij tutaj i dodaj pierwszą pozycję RZiS")) {
                pozycje.remove(0);
            }
            if (pozycje.isEmpty()) {
                Msg.msg("i", nowyelementRZiS.getNazwa() + "zachowam pod A");
                nowyelementRZiS.setPozycjaSymbol("A");
                nowyelementRZiS.setPozycjaString("A");
                nowyelementRZiS.setLevel(0);
                nowyelementRZiS.setMacierzysty(0);
            } else {
                String poprzednialitera = ((PozycjaRZiSBilans) rootProjekt.getChildren().get(rootProjekt.getChildCount() - 1).getData()).getPozycjaSymbol();
                String nowalitera = RomNumb.alfaInc(poprzednialitera);
                nowyelementRZiS.setPozycjaSymbol(nowalitera);
                nowyelementRZiS.setPozycjaString(nowalitera);
                nowyelementRZiS.setLevel(0);
                nowyelementRZiS.setMacierzysty(0);
                if (!(nowyelementRZiS.getFormula() instanceof String)) {
                    nowyelementRZiS.setFormula("");
                }
                Msg.msg("i", nowyelementRZiS.getNazwa() + "zachowam pod " + nowalitera);
            }
            nowyelementRZiS.setUklad(rzisuklad.getRzisukladPK().getUklad());
            nowyelementRZiS.setPodatnik(rzisuklad.getRzisukladPK().getPodatnik());
            nowyelementRZiS.setRok(rzisuklad.getRzisukladPK().getRok());
            try {
                pozycjaRZiSDAO.dodaj(nowyelementRZiS);
                pozycje.add(nowyelementRZiS);
                rootProjekt = new TreeNodeExtended("root", null);
                PozycjaRZiSFKBean.ustawRootaprojekt(rootProjekt, pozycje);
                level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
                Msg.msg("i", "Dodano nowa pozycję syntetyczną");
            } catch (Exception e) {
                Msg.msg("e", "Wystąpił błąd - nie dodano nowej pozycji syntetycznej");
            }
            nowyelementRZiS = new PozycjaRZiSBilans();

        } else {
            if (pozycje.get(0).getNazwa().equals("Kliknij tutaj i dodaj pierwszą pozycję RZiS")) {
                Msg.msg("e", "Błąd. Najpierw dodaj pierwszą pozycje wyższego rzędu!");
                return;
            }
            int level = ((PozycjaRZiSBilans) wybranynodekonta.getData()).getLevel();
            if (level == 4) {
                Msg.msg("e", "Nie można dodawać więcej poziomów");
                return;
            }
            PozycjaRZiSBilans parent = (PozycjaRZiSBilans) wybranynodekonta.getData();
            String nastepnysymbol;
            //sprawdzic trzeba czy sa dzieci juz jakies
            if (wybranynodekonta.getChildCount() == 0) {
                //w zaleznosci od levelu zwraca nastepny numer
                nastepnysymbol = PozycjaRZiSFKBean.zwrocNastepnySymbol(level + 1);
            } else {
                int index = wybranynodekonta.getChildCount() - 1;
                PozycjaRZiSBilans lastchild = (PozycjaRZiSBilans) wybranynodekonta.getChildren().get(index).getData();
                nastepnysymbol = PozycjaRZiSFKBean.zwrocNastepnySymbol(level + 1, lastchild.getPozycjaSymbol());
            }
            nowyelementRZiS.setPozycjaSymbol(nastepnysymbol);
            nowyelementRZiS.setPozycjaString(parent.getPozycjaString() + "." + nastepnysymbol);
            nowyelementRZiS.setPrzychod0koszt1(parent.isPrzychod0koszt1());
            nowyelementRZiS.setLevel(level + 1);
            nowyelementRZiS.setMacierzysty(parent.getLp());
            if (!(nowyelementRZiS.getFormula() instanceof String)) {
                nowyelementRZiS.setFormula("");
            }
            nowyelementRZiS.setUklad(rzisuklad.getRzisukladPK().getUklad());
            nowyelementRZiS.setPodatnik(rzisuklad.getRzisukladPK().getPodatnik());
            nowyelementRZiS.setRok(rzisuklad.getRzisukladPK().getRok());
            try {
                pozycjaRZiSDAO.dodaj(nowyelementRZiS);
                pozycje.add(nowyelementRZiS);
                rootProjekt = new TreeNodeExtended("root", null);
                PozycjaRZiSFKBean.ustawRootaprojekt(rootProjekt, pozycje);
                level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
                Msg.msg("i", "Dodano nowa pozycję analityczną");
            } catch (Exception e) {
                Msg.msg("e", "Wystąpił błąd - nie dodano nowej pozycji analitycznej");
            }
            nowyelementRZiS = new PozycjaRZiSBilans();
        }
    }
   
    public void usunpozycje() {
        try {
            if (wybranynodekonta.getChildCount() > 0) {
                Msg.msg("w", "Wybrana pozycja RZiS zawiera podpunkty!");
                throw new Exception();
            }
            pozycje.remove(wybranynodekonta.getData());
            pozycjaRZiSDAO.destroy(wybranynodekonta.getData());
            if (pozycje.isEmpty()) {
                pozycje.add(new PozycjaRZiSBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję RZiS", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            rootProjekt = new TreeNodeExtended("root", null);
            PozycjaRZiSFKBean.ustawRootaprojekt(rootProjekt, pozycje);
            level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
            Msg.msg("i", "Usuwam w RZiS");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się usunąć pozycji w RZiS");
        }
    }

    public void zaksiegujzmianypozycji() {
        List<Konto> plankont = kontoDAO.findAll();
        for (Konto p : plankont) {
            Kontopozycjarzis kontopozycjarzis = new Kontopozycjarzis();
            if (p.getPozycja() != null) {
                KontopozycjarzisPK kontopozycjarzisPK = new KontopozycjarzisPK();
                kontopozycjarzisPK.setKontoId(p.getId());
                kontopozycjarzisPK.setUklad(rzisuklad.getRzisukladPK().getUklad());
                kontopozycjarzisPK.setPodatnik(rzisuklad.getRzisukladPK().getPodatnik());
                kontopozycjarzisPK.setRok(rzisuklad.getRzisukladPK().getRok());
                kontopozycjarzis.setKontopozycjarzisPK(kontopozycjarzisPK);
                kontopozycjarzis.setPozycjastring(p.getPozycja());
                kontopozycjarzis.setPozycjonowane(p.isPozycjonowane());
                kontopozycjarzisDAO.edit(kontopozycjarzis);
            } else {
                KontopozycjarzisPK kontopozycjarzisPK = new KontopozycjarzisPK();
                kontopozycjarzisPK.setKontoId(p.getId());
                kontopozycjarzisPK.setUklad(rzisuklad.getRzisukladPK().getUklad());
                kontopozycjarzisPK.setPodatnik(rzisuklad.getRzisukladPK().getPodatnik());
                kontopozycjarzisPK.setRok(rzisuklad.getRzisukladPK().getRok());
                kontopozycjarzis.setKontopozycjarzisPK(kontopozycjarzisPK);
                try {
                    kontopozycjarzisDAO.destroy(kontopozycjarzis);
                } catch (Exception e) {
                }
            }
        }
        Msg.msg("i", "Zapamiętano przyporządkowanie kont dla układu: " + rzisuklad.getRzisukladPK().getUklad());
    }

   

    //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public TreeNodeExtended getRoot() {
        return root;
    }

    public void setRoot(TreeNodeExtended root) {
        this.root = root;
    }

    public PozycjaRZiSBilans getSelected() {
        return selected;
    }

    public void setSelected(PozycjaRZiSBilans selected) {
        this.selected = selected;
    }

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

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

    public TreeNodeExtended getRootProjekt() {
        return rootProjekt;
    }

    public void setRootProjekt(TreeNodeExtended rootProjekt) {
        this.rootProjekt = rootProjekt;
    }

    public PozycjaRZiSBilans getNowyelementRZiS() {
        return nowyelementRZiS;
    }

    public void setNowyelementRZiS(PozycjaRZiSBilans nowyelementRZiS) {
        this.nowyelementRZiS = nowyelementRZiS;
    }

    public Rzisuklad getRzisuklad() {
        return rzisuklad;
    }

    public void setRzisuklad(Rzisuklad rzisuklad) {
        this.rzisuklad = rzisuklad;
    }

    public TreeNodeExtended getRootUklad() {
        return rootUklad;
    }

    public void setRootUklad(TreeNodeExtended rootUklad) {
        this.rootUklad = rootUklad;
    }

    public TreeNodeExtended getRootProjektKonta() {
        return rootProjektKonta;
    }

    public void setRootProjektKonta(TreeNodeExtended rootProjektKonta) {
        this.rootProjektKonta = rootProjektKonta;
    }

    //</editor-fold>
}
