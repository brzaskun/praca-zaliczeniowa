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
import daoFK.KontopozycjaDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.Kontopozycja;
import entityfk.KontopozycjaPK;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
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
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import org.primefaces.model.TreeNodeChildren;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PozycjaBRView implements Serializable {
    private static final long serialVersionUID = 1L;

    private static TreeNode wybranynodekonta;
    private static ArrayList<PozycjaRZiSBilans> pozycje;
    private static ArrayList<PozycjaRZiSBilans> pozycje_old;
    private static ArrayList<Konto> przyporzadkowanekonta;
    private static String wybranapozycja;
    private static int level = 0;

    private TreeNodeExtended root;
    private TreeNodeExtended rootUklad;
    private TreeNodeExtended rootProjektRZiS;
    private TreeNodeExtended rootBilansAktywa;
    private TreeNodeExtended rootBilansPasywa;
    private TreeNodeExtended rootProjektKonta;
    private TreeNode[] selectedNodes;
    private PozycjaRZiS nowyelementRZiS;
    private PozycjaBilans nowyelementBilans;
    private PozycjaRZiS selected;
    private ArrayList<TreeNodeExtended> finallNodes;
    private List<Konto> wykazkont;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject 
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    @Inject
    private UkladBR uklad;
    @Inject
    private KontopozycjaDAO kontopozycjarzisDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public PozycjaBRView() {
        this.wykazkont = new ArrayList<>();
        this.nowyelementRZiS = new PozycjaRZiS();
        this.nowyelementBilans = new PozycjaBilans();
        this.root = new TreeNodeExtended("root", null);
        this.rootUklad = new TreeNodeExtended("root", null);
        this.rootProjektRZiS = new TreeNodeExtended("root", null);
        this.rootBilansAktywa = new TreeNodeExtended("root", null);
        this.rootBilansPasywa = new TreeNodeExtended("root", null);
        this.rootProjektKonta = new TreeNodeExtended("root", null);
        PozycjaBRView.przyporzadkowanekonta = new ArrayList<>();
        this.finallNodes = new ArrayList<TreeNodeExtended>();
        pozycje = new ArrayList<>();
        pozycje_old = new ArrayList<>();
    }

    @PostConstruct
    private void init() {

        //(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, double kwota)
        pozycje_old.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Przychody netto ze sprzedaży i zrównane z nimi, w tym:", false));
        pozycje_old.add(new PozycjaRZiS(2, "A.I", "I", 1, 1, "Przychody netto ze sprzedaży produktów", false, 0.0));
        pozycje_old.add(new PozycjaRZiS(3, "A.II", "II", 1, 1, "Zmiana stanu produktów", false, 0.0));
        pozycje_old.add(new PozycjaRZiS(4, "A.III", "III", 1, 1, "Koszt wytworzenia produktów na własne potrzeby jednostki", true, 0.0));
        pozycje_old.add(new PozycjaRZiS(5, "A.IV", "IV", 1, 1, "Przychody netto ze sprzedaży towarów i materiałów", false, 0.0));
        pozycje_old.add(new PozycjaRZiS(6, "B", "B", 0, 0, "Koszty działalności operacyjnej", true));
        pozycje_old.add(new PozycjaRZiS(7, "B.I", "I", 6, 1, "Amortyzacja", true));
        pozycje_old.add(new PozycjaRZiS(8, "B.II", "II", 6, 1, "Zużycie materiałów i energii", true, 0.0));
        pozycje_old.add(new PozycjaRZiS(9, "B.III", "III", 6, 1, "Usługi obce", true, 0.0));
        pozycje_old.add(new PozycjaRZiS(10, "B.IV", "IV", 6, 1, "Podatki i  opłaty", true, 0.0));
        pozycje_old.add(new PozycjaRZiS(11, "B.V", "V", 6, 1, "Wynagrodzenia", true, 0.0));
        pozycje_old.add(new PozycjaRZiS(12, "B.I.1", "1", 7, 2, "amortyzacja kup", true, 0.0));
        pozycje_old.add(new PozycjaRZiS(13, "B.I.2", "2", 7, 2, "amortyzacja nkup", true));
        pozycje_old.add(new PozycjaRZiS(14, "B.I.2.a)", "a)", 13, 3, "bobopo", true, 0.0));
        pozycje_old.add(new PozycjaRZiS(15, "C", "C", 0, 0, "Zysk (strata) ze sprzedaży (A-B)", false, "A-B"));
        pozycje_old.add(new PozycjaRZiS(16, "D", "D", 0, 0, "Pozostałe truey operacyjne", false));
        pozycje_old.add(new PozycjaRZiS(17, "D.I", "I", 16, 1, "Zysk z niefinansowych aktywów trwałych", false, 0.0));
        pozycje_old.add(new PozycjaRZiS(18, "D.II", "II", 16, 1, "Dotacje", false, 0.0));
        pozycje_old.add(new PozycjaRZiS(19, "D.III", "III", 16, 1, "Inne truey operacyjne", false, 0.0));
        pozycje_old.add(new PozycjaRZiS(20, "E", "E", 0, 0, "Pozostałe koszty operacyjne", true));
        pozycje_old.add(new PozycjaRZiS(21, "E.I", "I", 20, 1, "Strata z niefinansowych aktywów trwałych", true, 0.0));
        pozycje_old.add(new PozycjaRZiS(22, "E.II", "II", 20, 1, "Aktualizacja aktywów niefinansowych", true, 0.0));
        pozycje_old.add(new PozycjaRZiS(23, "E.III", "III", 20, 1, "Inne koszty operacyjne", true, 0.0));
        pozycje_old.add(new PozycjaRZiS(24, "F", "F", 0, 0, "Zysk (strata) ze działalności operacyjnej (C+D-E)", false, "C+D-E"));
        //tutaj dzieje sie magia :) tak funkcja przeksztalca baze danych w nody
        pozycje.addAll(pozycjaRZiSDAO.findAll());
//        if (pozycje.size() == 0) {
//            pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
//            Msg.msg("i", "Dodaje pusta pozycje");
//        }
//        if (pozycje.size() > 0) {
//            List<Kontozapisy> zapisy = kontoZapisyFKDAO.findAll();
//            List<Konto> plankont = kontoDAO.findAll();
//            ustawRoota(rootProjektRZiS, pozycje, zapisy, plankont);
//        }

    }
    
// to jest uruchamiane po wyborze ukladu pierwsza funkcja
    public void pobierzuklad(String br, TreeNodeExtended root, String aktywapasywa) {
        pozycje = new ArrayList<>();
        try {
         if (br.equals("r")) {
                pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
                if (pozycje.isEmpty()) {
                   pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                    Msg.msg("i", "Dodaje pusta pozycje");
                }
            } else {
                if (aktywapasywa.equals("aktywa")) {
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladAktywa(uklad));
                } else {
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladPasywa(uklad));
                }
                if (pozycje.isEmpty()) {
                   pozycje.add(new PozycjaBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                    Msg.msg("i", "Dodaje pusta pozycje");
                }
            }
        } catch (Exception e) {
        }   
        root.getChildren().clear();
        PozycjaRZiSFKBean.ustawRootaprojekt(root, pozycje);
        level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
        Msg.msg("i", "Pobrano układ ");
    }

    public void pobierzukladprzeglad(String br, TreeNodeExtended root) {
        pozycje = new ArrayList<>();
       try {
         if (br.equals("r")) {
                pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
                if (pozycje.isEmpty()) {
                   pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                    Msg.msg("i", "Dodaje pusta pozycje");
                }
            } else {
                //pozycje.addAll(pozycjaBilansDAO.findBilansuklad(uklad));
                if (pozycje.isEmpty()) {
                   pozycje.add(new PozycjaBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                    Msg.msg("i", "Dodaje pusta pozycje");
                }
            }
        } catch (Exception e) {
        }
        root.getChildren().clear();
        List<StronaWiersza> zapisy = new ArrayList<>();
        if (br.equals("r")) {
            zapisy.addAll(stronaWierszaDAO.findStronaByPodatnikRokWalutaWynik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "PLN"));
        } else {
            zapisy.addAll(stronaWierszaDAO.findStronaByPodatnikRokWalutaBilans(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "PLN"));
        }
        List<Konto> plankont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu());
        PozycjaRZiSFKBean.ustawRoota(root, pozycje, zapisy, plankont);
        level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
        Msg.msg("i", "Pobrano układ ");
    }
    
    public void pobierzukladprzegladbilans() {
        ArrayList<PozycjaRZiSBilans> pozycjeaktywa = new ArrayList<>();
        ArrayList<PozycjaRZiSBilans> pozycjepasywa = new ArrayList<>();
       try {
                pozycjeaktywa.addAll(pozycjaBilansDAO.findBilansukladAktywa(uklad));
                pozycjepasywa.addAll(pozycjaBilansDAO.findBilansukladPasywa(uklad));
            if (pozycjeaktywa.isEmpty()) {
               pozycjeaktywa.add(new PozycjaBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            if (pozycjepasywa.isEmpty()) {
               pozycjepasywa.add(new PozycjaBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
        } catch (Exception e) {
        }
        rootBilansAktywa.getChildren().clear();
        rootBilansAktywa.getChildren().clear();
        List<StronaWiersza> zapisy = new ArrayList<>();
        zapisy.addAll(stronaWierszaDAO.findStronaByPodatnikRokWalutaBilans(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "PLN"));
        List<Konto> plankont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu());
        PozycjaRZiSFKBean.ustawRoota(rootBilansAktywa, pozycjeaktywa, zapisy, plankont);
        PozycjaRZiSFKBean.ustawRoota(rootBilansPasywa, pozycjepasywa, zapisy, plankont);
        level = PozycjaRZiSFKBean.ustawLevel(rootBilansAktywa, pozycje);
        Msg.msg("i", "Pobrano układ ");
    }

    public void pobierzukladkonto(String br, String aktywapasywa) {
        pozycje = new ArrayList<>();
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjarzisDAO, uklad);
        try {
         if (br.equals("r")) {
                pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
                if (pozycje.isEmpty()) {
                   pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                    Msg.msg("i", "Dodaje pusta pozycje");
                }
            } else {
                if (aktywapasywa.equals("aktywa")) {
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladAktywa(uklad));
                } else {
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladPasywa(uklad));
                }
                if (pozycje.isEmpty()) {
                   pozycje.add(new PozycjaBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                    Msg.msg("i", "Dodaje pusta pozycje");
                }
            }
        } catch (Exception e) {
        }
        if (br.equals("r")) {
            drugiinit();
        } else {
            drugiinitbilansowe();
        }
        rootProjektKonta.getChildren().clear();
        PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektKonta, pozycje);
        level = PozycjaRZiSFKBean.ustawLevel(rootProjektKonta, pozycje);
        Msg.msg("i", "Pobrano układ " );
    }

    private void drugiinit() {
        wykazkont.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView.getPodatnikWpisu(), "0", "wynikowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalityki(pobraneKontaSyntetyczne, wykazkont, kontoDAO, wpisView.getPodatnikWpisu());
        Collections.sort(wykazkont, new Kontocomparator());
    }
    
    private void drugiinitbilansowe() {
        wykazkont.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView.getPodatnikWpisu(), "0", "bilansowe");
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
    
    public void zwinrzadanalityki (Konto konto) {
        List<Konto> lista = kontoDAO.findKontaSiostrzanePodatnik(wpisView.getPodatnikWpisu(), konto.getMacierzyste());
        boolean jestprzypisane = false;
        List<String> analitykinazwy = new ArrayList<>();
        for (Konto p : lista) {
            if (p.isPozycjonowane()) {
                jestprzypisane = true;
                analitykinazwy.add(p.getPelnynumer());
            }
        }
        if (jestprzypisane) {
            String result = StringUtils.join(analitykinazwy, ", ");
            Msg.msg("e", "Nie można zwinąć analityk. Istnieją analityki przypisane do kont: "+result);
        } else {
            Konto macierzyste = kontoDAO.findKonto(konto.getMacierzyste(), wpisView.getPodatnikWpisu());
            for (Konto p : lista) {
                wykazkont.remove(p);
            }
            wykazkont.add(macierzyste);
            Collections.sort(wykazkont, new Kontocomparator());
        }
    }

    public void onKontoDrop(Konto konto, String br) {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                wykazkont.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                konto.setPozycjaWn(wybranapozycja);
                konto.setPozycjaMa(wybranapozycja);
                konto.setPozycjonowane(true);
                kontoDAO.edit(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), wybranapozycja, kontoDAO, wpisView.getPodatnikWpisu());
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto.getMacierzyste(), kontoDAO, wpisView.getPodatnikWpisu());
                }
            } else {
                RequestContext.getCurrentInstance().execute("PF('kontownmawybor').show();");
                Msg.msg("Konto niezwykle");
            }
        }
        if (br.equals("r")) {
            drugiinit();
        } else {
            drugiinitbilansowe();
        }
    }

    public void onKontoRemove(Konto konto, String br) {
        wykazkont.add(konto);
        Collections.sort(wykazkont, new Kontocomparator());
        if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setPozycjaWn(null);
            konto.setPozycjaMa(null);
            konto.setPozycjonowane(false);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), null, kontoDAO, wpisView.getPodatnikWpisu());
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikWpisu());
            }
        } else {
            Msg.msg("Konto niezwykle");
        }
        if (br.equals("r")) {
            drugiinit();
        } else {
            drugiinitbilansowe();
        }
    }

    public void wybranopozycjeRZiS() {
        wybranapozycja = ((PozycjaRZiS) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowane(kontoDAO, wybranapozycja));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaRZiS) wybranynodekonta.getData()).getNazwa());
    }
    public void wybranopozycjeBilans() {
        wybranapozycja = ((PozycjaBilans) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowaneB(kontoDAO, wybranapozycja));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaBilans) wybranynodekonta.getData()).getNazwa());
    }

    public void dodajnowapozycje(String syntetycznaanalityczna) {
        if (syntetycznaanalityczna.equals("syntetyczna")) {
            //dodaje nowa syntetyke
            if (pozycje.get(0).getNazwa().equals("Kliknij tutaj i dodaj pierwszą pozycję")) {
                pozycje.remove(0);
            }
            if (pozycje.isEmpty()) {
                Msg.msg("i", nowyelementRZiS.getNazwa() + "zachowam pod A");
                nowyelementRZiS.setPozycjaSymbol("A");
                nowyelementRZiS.setPozycjaString("A");
                nowyelementRZiS.setLevel(0);
                nowyelementRZiS.setMacierzysty(0);
            } else {
                String poprzednialitera = ((PozycjaRZiS) rootProjektRZiS.getChildren().get(rootProjektRZiS.getChildCount() - 1).getData()).getPozycjaSymbol();
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
            nowyelementRZiS.setUklad(uklad.getUklad());
            nowyelementRZiS.setPodatnik(uklad.getPodatnik());
            nowyelementRZiS.setRok(uklad.getRok());
            try {
                pozycjaRZiSDAO.dodaj(nowyelementRZiS);
                pozycje.add(nowyelementRZiS);
                rootProjektRZiS = new TreeNodeExtended("root", null);
                PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektRZiS, pozycje);
                level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
                Msg.msg("i", "Dodano nowa pozycję syntetyczną");
            } catch (Exception e) {
                Msg.msg("e", "Wystąpił błąd - nie dodano nowej pozycji syntetycznej");
            }
            nowyelementRZiS = new PozycjaRZiS();

        } else {
            if (pozycje.get(0).getNazwa().equals("Kliknij tutaj i dodaj pierwszą pozycję")) {
                Msg.msg("e", "Błąd. Najpierw dodaj pierwszą pozycje wyższego rzędu!");
                return;
            }
            int level = ((PozycjaRZiS) wybranynodekonta.getData()).getLevel();
            if (level == 4) {
                Msg.msg("e", "Nie można dodawać więcej poziomów");
                return;
            }
            PozycjaRZiS parent = (PozycjaRZiS) wybranynodekonta.getData();
            String nastepnysymbol;
            //sprawdzic trzeba czy sa dzieci juz jakies
            if (wybranynodekonta.getChildCount() == 0) {
                //w zaleznosci od levelu zwraca nastepny numer
                nastepnysymbol = PozycjaRZiSFKBean.zwrocNastepnySymbol(level + 1);
            } else {
                int index = wybranynodekonta.getChildCount() - 1;
                PozycjaRZiS lastchild = (PozycjaRZiS) wybranynodekonta.getChildren().get(index).getData();
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
            nowyelementRZiS.setUklad(uklad.getUklad());
            nowyelementRZiS.setPodatnik(uklad.getPodatnik());
            nowyelementRZiS.setRok(uklad.getRok());
            try {
                pozycjaRZiSDAO.dodaj(nowyelementRZiS);
                pozycje.add(nowyelementRZiS);
                rootProjektRZiS = new TreeNodeExtended("root", null);
                PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektRZiS, pozycje);
                level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
                Msg.msg("i", "Dodano nowa pozycję analityczną");
            } catch (Exception e) {
                Msg.msg("e", "Wystąpił błąd - nie dodano nowej pozycji analitycznej");
            }
            nowyelementRZiS = new PozycjaRZiS();
        }
    }
    
    public void dodajnowapozycjeBilans(String syntetycznaanalityczna) {
        if (syntetycznaanalityczna.equals("syntetyczna")) {
            //dodaje nowa syntetyke
            if (pozycje.get(0).getNazwa().equals("Kliknij tutaj i dodaj pierwszą pozycję")) {
                pozycje.remove(0);
            }
            if (pozycje.isEmpty()) {
                Msg.msg("i", nowyelementBilans.getNazwa() + "zachowam pod A");
                nowyelementBilans.setPozycjaSymbol("A");
                nowyelementBilans.setPozycjaString("A");
                nowyelementBilans.setLevel(0);
                nowyelementBilans.setMacierzysty(0);
            } else {
                String poprzednialitera = ((PozycjaBilans) rootProjektRZiS.getChildren().get(rootProjektRZiS.getChildCount() - 1).getData()).getPozycjaSymbol();
                String nowalitera = RomNumb.alfaInc(poprzednialitera);
                nowyelementBilans.setPozycjaSymbol(nowalitera);
                nowyelementBilans.setPozycjaString(nowalitera);
                nowyelementBilans.setLevel(0);
                nowyelementBilans.setMacierzysty(0);
                if (!(nowyelementBilans.getFormula() instanceof String)) {
                    nowyelementBilans.setFormula("");
                }
                Msg.msg("i", nowyelementBilans.getNazwa() + "zachowam pod " + nowalitera);
            }
            nowyelementBilans.setUklad(uklad.getUklad());
            nowyelementBilans.setPodatnik(uklad.getPodatnik());
            nowyelementBilans.setRok(uklad.getRok());
            try {
                pozycjaRZiSDAO.dodaj(nowyelementBilans);
                pozycje.add(nowyelementBilans);
                rootProjektRZiS = new TreeNodeExtended("root", null);
                PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektRZiS, pozycje);
                level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
                Msg.msg("i", "Dodano nowa pozycję syntetyczną");
            } catch (Exception e) {
                Msg.msg("e", "Wystąpił błąd - nie dodano nowej pozycji syntetycznej");
            }
            nowyelementBilans = new PozycjaBilans();

        } else {
            if (pozycje.get(0).getNazwa().equals("Kliknij tutaj i dodaj pierwszą pozycję")) {
                Msg.msg("e", "Błąd. Najpierw dodaj pierwszą pozycje wyższego rzędu!");
                return;
            }
            int level = ((PozycjaBilans) wybranynodekonta.getData()).getLevel();
            if (level == 4) {
                Msg.msg("e", "Nie można dodawać więcej poziomów");
                return;
            }
            PozycjaBilans parent = (PozycjaBilans) wybranynodekonta.getData();
            String nastepnysymbol;
            //sprawdzic trzeba czy sa dzieci juz jakies
            if (wybranynodekonta.getChildCount() == 0) {
                //w zaleznosci od levelu zwraca nastepny numer
                nastepnysymbol = PozycjaRZiSFKBean.zwrocNastepnySymbol(level + 1);
            } else {
                int index = wybranynodekonta.getChildCount() - 1;
                PozycjaBilans lastchild = (PozycjaBilans) wybranynodekonta.getChildren().get(index).getData();
                nastepnysymbol = PozycjaRZiSFKBean.zwrocNastepnySymbol(level + 1, lastchild.getPozycjaSymbol());
            }
            nowyelementBilans.setPozycjaSymbol(nastepnysymbol);
            nowyelementBilans.setPozycjaString(parent.getPozycjaString() + "." + nastepnysymbol);
            nowyelementBilans.setPrzychod0koszt1(parent.isPrzychod0koszt1());
            nowyelementBilans.setLevel(level + 1);
            nowyelementBilans.setMacierzysty(parent.getLp());
            if (!(nowyelementBilans.getFormula() instanceof String)) {
                nowyelementBilans.setFormula("");
            }
            nowyelementBilans.setUklad(uklad.getUklad());
            nowyelementBilans.setPodatnik(uklad.getPodatnik());
            nowyelementBilans.setRok(uklad.getRok());
            try {
                pozycjaRZiSDAO.dodaj(nowyelementBilans);
                pozycje.add(nowyelementBilans);
                rootProjektRZiS = new TreeNodeExtended("root", null);
                PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektRZiS, pozycje);
                level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
                Msg.msg("i", "Dodano nowa pozycję analityczną");
            } catch (Exception e) {
                Msg.msg("e", "Wystąpił błąd - nie dodano nowej pozycji analitycznej");
            }
            nowyelementBilans = new PozycjaBilans();
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
                pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            rootProjektRZiS = new TreeNodeExtended("root", null);
            PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektRZiS, pozycje);
            level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
            Msg.msg("i", "Usuwam w RZiS");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się usunąć pozycji w RZiS");
        }
    }

    public void zaksiegujzmianypozycji(String br) {
        List<Konto> plankont = kontoDAO.findAll();
        for (Konto p : plankont) {
            Kontopozycja kontopozycja = new Kontopozycja();
            if (p.getPozycjaWn() != null) {
                KontopozycjaPK kontopozycjaPK = new KontopozycjaPK();
                kontopozycja.setKonto(p);
                kontopozycja.setUkladBR(uklad);
                kontopozycja.setKontopozycjaPK(kontopozycjaPK);
                kontopozycja.setPozycjaWn(p.getPozycjaWn());
                kontopozycja.setPozycjonowane(p.isPozycjonowane());
                kontopozycjarzisDAO.edit(kontopozycja);
            } else {
                KontopozycjaPK kontopozycjaPK = new KontopozycjaPK();
                kontopozycja.setKonto(p);
                kontopozycja.setUkladBR(uklad);
                kontopozycja.setKontopozycjaPK(kontopozycjaPK);
                try {
                    kontopozycjarzisDAO.destroy(kontopozycja);
                } catch (Exception e) {
                }
            }
        }
        Msg.msg("i", "Zapamiętano przyporządkowanie kont dla układu: " );
    }

   

    //<editor-fold defaultstate="collapsed" desc="comment">
    
    
    public TreeNodeExtended getRootBilansAktywa() {
        return rootBilansAktywa;
    }

    public void setRootBilansAktywa(TreeNodeExtended rootBilansAktywa) {
        this.rootBilansAktywa = rootBilansAktywa;
    }

    public TreeNodeExtended getRootBilansPasywa() {
        return rootBilansPasywa;
    }

    public void setRootBilansPasywa(TreeNodeExtended rootBilansPasywa) {
        this.rootBilansPasywa = rootBilansPasywa;
    }

    public PozycjaBilans getNowyelementBilans() {
        return nowyelementBilans;
    }

    public void setNowyelementBilans(PozycjaBilans nowyelementBilans) {
        this.nowyelementBilans = nowyelementBilans;
    }

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

    public ArrayList<Konto> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

    public void setPrzyporzadkowanekonta(ArrayList<Konto> przyporzadkowanekonta) {
        PozycjaBRView.przyporzadkowanekonta = przyporzadkowanekonta;
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
        PozycjaBRView.wybranynodekonta = wybranynodekonta;
    }

    public TreeNodeExtended getRootProjektRZiS() {
        return rootProjektRZiS;
    }

    public void setRootProjektRZiS(TreeNodeExtended rootProjektRZiS) {
        this.rootProjektRZiS = rootProjektRZiS;
    }

    public PozycjaRZiS getNowyelementRZiS() {
        return nowyelementRZiS;
    }

    public void setNowyelementRZiS(PozycjaRZiS nowyelementRZiS) {
        this.nowyelementRZiS = nowyelementRZiS;
    }

    public UkladBR getUklad() {
        return uklad;
    }

    public void setUklad(UkladBR uklad) {
        this.uklad = uklad;
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

