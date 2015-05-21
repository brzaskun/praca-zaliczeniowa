    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.BOFKBean;
import beansFK.PozycjaRZiSFKBean;
import beansFK.StronaWierszaBean;
import converter.RomNumb;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.WierszBODAO;
import embeddable.Mce;
import embeddablefk.KontoKwota;
import embeddablefk.StronaWierszaKwota;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.model.TreeNode;
import pdffk.PdfBilans;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PozycjaBRBOView implements Serializable {
    private static final long serialVersionUID = 1L;

    private TreeNode wybranynodekonta;
    private ArrayList<PozycjaRZiSBilans> pozycje;
    private ArrayList<PozycjaRZiSBilans> pozycje_old;
    private String wybranapozycja;
    
    private int level = 0;
    private List<Konto> wykazkont;
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
    private List<StronaWierszaKwota> podpieteStronyWiersza;
    private List<KontoKwota> sumaPodpietychKont;
    private boolean pokazaktywa;
    private double sumabilansowaaktywa;
    private double sumabilansowapasywa;
    
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
    private WierszBODAO wierszBODAO;
    
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    

    public PozycjaBRBOView() {
        this.wykazkont = new ArrayList<>();
        this.nowyelementRZiS = new PozycjaRZiS();
        this.nowyelementBilans = new PozycjaBilans();
        this.root = new TreeNodeExtended("root", null);
        this.rootUklad = new TreeNodeExtended("root", null);
        this.rootProjektRZiS = new TreeNodeExtended("root", null);
        this.rootBilansAktywa = new TreeNodeExtended("root", null);
        this.rootBilansPasywa = new TreeNodeExtended("root", null);
        this.rootProjektKonta = new TreeNodeExtended("root", null);
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
        } catch (Exception e) {  E.e(e);
        }   
        root.getChildren().clear();
        PozycjaRZiSFKBean.ustawRootaprojekt(root, pozycje);
        level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
        Msg.msg("i", "Pobrano układ ");
    }

    public void pobierzukladprzegladRZiS() {
        ArrayList<PozycjaRZiSBilans> pozycje = new ArrayList<>();
        pobierzPozycje(pozycje);
        rootProjektRZiS.getChildren().clear();
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView);
        List<Konto> plankont = kontoDAO.findKontaWynikowePodatnikaBezPotomkow(wpisView);
        try {
            PozycjaRZiSFKBean.ustawRoota(rootProjektRZiS, pozycje, zapisy, plankont);
            level = PozycjaRZiSFKBean.ustawLevel(rootProjektRZiS, pozycje);
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e) {
            E.e(e);
            rootProjektRZiS.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
        }
    }
    
    private void pobierzPozycje(ArrayList<PozycjaRZiSBilans> pozycje) {
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
            if (pozycje.isEmpty()) {
               pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                PozycjaRZiS p = (PozycjaRZiS) it.next();
                p.setPrzyporzadkowanestronywiersza(null);
            }
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
    
    public void pobierzukladprzegladBilans(String aktywapasywa) {
        if (aktywapasywa.equals("aktywa")) {
            pokazaktywa = true;
            pobierzukladprzegladBilans();
        } else {
            pokazaktywa = false;
            pobierzukladprzegladBilans();
        }
    }
    
    private void sumaaktywapasywaoblicz(String aktywapasywa) {
        if (aktywapasywa.equals("aktywa")) {
            List<TreeNode> wezly = rootBilansAktywa.getChildren();
            double suma = 0.0;
            for (Iterator<TreeNode> it = wezly.iterator(); it.hasNext();) {
                PozycjaRZiSBilans p = (PozycjaRZiSBilans) it.next().getData();
                suma += p.getKwota();
            }
            sumabilansowaaktywa = Z.z(suma);
        } else {
            List<TreeNode> wezly = rootBilansPasywa.getChildren();
            double suma = 0.0;
            for (Iterator<TreeNode> it = wezly.iterator(); it.hasNext();) {
                PozycjaRZiSBilans p = (PozycjaRZiSBilans) it.next().getData();
                suma += p.getKwota();
            }
            sumabilansowapasywa = Z.z(suma);
        }
    }
    
    public void pobierzukladprzegladBilans() {
        ArrayList<PozycjaRZiSBilans> pozycjeaktywa = new ArrayList<>();
        ArrayList<PozycjaRZiSBilans> pozycjepasywa = new ArrayList<>();
        pobierzPozycjeAktywaPasywa(pozycjeaktywa, pozycjepasywa);
        rootBilansAktywa.getChildren().clear();
        rootBilansPasywa.getChildren().clear();
        List<StronaWiersza> zapisy = stronaWierszaDAO.findStronaByPodatnikRokBilansBO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        try {
            List<Konto> plankont = kontoDAO.findKontaBilansowePodatnikaBezPotomkow(wpisView);
            Konto kontowyniku = kontoDAO.findKonto860(wpisView);
            naniesKwoteWynikFinansowy(kontowyniku);
            PozycjaRZiSFKBean.sumujObrotyNaKontach(zapisy, plankont);
            PozycjaRZiSFKBean.ustawRootaBilans(rootBilansAktywa, pozycjeaktywa, plankont, "aktywa");
            PozycjaRZiSFKBean.ustawRootaBilans(rootBilansPasywa, pozycjepasywa, plankont, "pasywa");
            //nowy nie dziala - trzeba mocniej polowkowac. problem polea na tym ze pozycje zaleza od sald, czyli nie mozna isc po stronawiersza
            //trzeba najpierw podsumowac konta
            //PozycjaRZiSFKBean.ustawRootaBilansNowy(rootBilansAktywa, pozycjeaktywa, zapisy, plankont, "aktywa");
            //PozycjaRZiSFKBean.ustawRootaBilansNowy(rootBilansPasywa, pozycjepasywa, zapisy, plankont, "pasywa");
            level = PozycjaRZiSFKBean.ustawLevel(rootBilansAktywa, pozycje);
            Msg.msg("i", "Pobrano układ ");
            sumaaktywapasywaoblicz("aktywa");
            sumaaktywapasywaoblicz("pasywa");
        } catch (Exception e) {
            E.e(e);
            rootBilansAktywa.getChildren().clear();
            rootBilansPasywa.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
        }
    }

    private void naniesKwoteWynikFinansowy(Konto kontowyniku) {
        pobierzukladprzegladRZiS();
        List<Object> listazwrotnapozycji = new ArrayList<>();
        rootProjektRZiS.getFinallChildrenData(new ArrayList<TreeNodeExtended>(), listazwrotnapozycji);
        PozycjaRZiS pozycjawynikfin = (PozycjaRZiS) listazwrotnapozycji.get(listazwrotnapozycji.size() - 1);
        double wynikfinansowy = pozycjawynikfin.getKwota();
            double wf = Math.abs(wynikfinansowy);
            if (wynikfinansowy > 0) {//zysk
                kontowyniku.setObrotyMa(wf);
                kontowyniku.setSaldoMa(wf);
            } else {//strata
                kontowyniku.setObrotyWn(wf);
                kontowyniku.setSaldoWn(wf);
            }
    }
    
    private void pobierzPozycjeAktywaPasywa(ArrayList<PozycjaRZiSBilans> pozycjeaktywa, ArrayList<PozycjaRZiSBilans> pozycjepasywa) {
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
            for (Iterator<PozycjaRZiSBilans> it = pozycjeaktywa.iterator(); it.hasNext();) {
                PozycjaBilans p = (PozycjaBilans) it.next();
                p.setPrzyporzadkowanestronywiersza(null);
                p.setPrzyporzadkowanekonta(null);
            }
            for (Iterator<PozycjaRZiSBilans> it = pozycjepasywa.iterator(); it.hasNext();) {
                PozycjaBilans p = (PozycjaBilans) it.next();
                p.setPrzyporzadkowanestronywiersza(null);
                p.setPrzyporzadkowanekonta(null);
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    private void naniesBOnaKonto(Konto p) {
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(p, wierszBODAO, wpisView);
        for (StronaWiersza r : zapisyBO) {
            if (r.getWnma().equals("Wn")) {
                p.setBoWn(Z.z(p.getBoWn() + r.getKwotaPLN()));
            } else {
                p.setBoMa(Z.z(p.getBoMa() + r.getKwotaPLN()));
            }
        }
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

    public void zwinwszystkie(TreeNodeExtended root) {
        root.foldAll();
        level = 0;
    }

    public void zwin(TreeNodeExtended root) {
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
            } catch (Exception e) {  E.e(e);
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
            } catch (Exception e) {  E.e(e);
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
            } catch (Exception e) {  E.e(e);
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
            } catch (Exception e) {  E.e(e);
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
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie udało się usunąć pozycji w RZiS");
        }
    }

    public void zmien() {
        List<Konto> lista = kontoDAO.findAll();
        for (Konto p : lista) {
            if(p.getPodatnik().equals("Testowy")) {
                p.setPodatnik("Wzorcowy");
                kontoDAO.edit(p);
            }
        }
        List<PozycjaBilans> lista2 = pozycjaBilansDAO.findAll();
        for (PozycjaRZiSBilans p : lista2) {
            if(p.getPodatnik().equals("Tymczasowy")) {
                p.setPodatnik("Wzorcowy");
                kontoDAO.edit(p);
            }
        }
        List<PozycjaRZiS> lista3 = pozycjaRZiSDAO.findAll();
        for (PozycjaRZiSBilans p : lista3) {
            if(p.getPodatnik().equals("Tymczasowy")) {
                p.setPodatnik("Wzorcowy");
                kontoDAO.edit(p);
            }
        }
    }
    
    
    public void wyluskajStronyzPozycji() {
        sumaPodpietychKont = new ArrayList<>();
        podpieteStronyWiersza = new ArrayList<>();
        if (selectedNodes != null && selectedNodes.length > 0) {
            for (TreeNode p : selectedNodes) {
                PozycjaRZiSBilans r = (PozycjaRZiSBilans) p.getData();
                if (r.getPrzyporzadkowanestronywiersza() != null) {
                    podpieteStronyWiersza.addAll(r.getPrzyporzadkowanestronywiersza());
                }
            }
            List<Konto> konta = new ArrayList<>();
            for (StronaWierszaKwota p : podpieteStronyWiersza) {
                Konto k = p.getStronaWiersza().getKonto();
                if (!konta.contains(k)) {
                    konta.add(k);
                    sumaPodpietychKont.add(new KontoKwota(k, p.getKwota()));
                } else {
                    for (KontoKwota r : sumaPodpietychKont) {
                        if (r.getKonto().equals(k)) {
                            r.setKwota(r.getKwota()+p.getKwota());
                        }
                    }
                }
            }
        }
    }
    
    public void wyluskajStronyzPozycjiBilans() {
        podpieteStronyWiersza = new ArrayList<>();
        sumaPodpietychKont = new ArrayList<>();
        List<KontoKwota> podpieteKonta = new ArrayList<>();
        if (selectedNodes != null && selectedNodes.length > 0) {
            for (TreeNode p : selectedNodes) {
                PozycjaRZiSBilans r = (PozycjaRZiSBilans) p.getData();
                if (r.getPrzyporzadkowanekonta() != null) {
                    podpieteKonta.addAll(r.getPrzyporzadkowanekonta());
                }
            }
            List<Konto> konta = new ArrayList<>();
            for (KontoKwota p : podpieteKonta) {
                Konto k = p.getKonto();
                if (!konta.contains(k)) {
                    konta.add(k);
                    sumaPodpietychKont.add(new KontoKwota(k, p.getKwota()));
                } else {
                    for (KontoKwota r : sumaPodpietychKont) {
                        if (r.getKonto().equals(k)) {
                            r.setKwota(r.getKwota()+p.getKwota());
                        }
                    }
                }
            }
            List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikRokBilans(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (KontoKwota p : sumaPodpietychKont) {
                int granicagorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacNastepny());
                for (Iterator<StronaWiersza> it = stronywiersza.iterator(); it.hasNext(); ) {   
                    StronaWiersza r = (StronaWiersza) it.next();
                    if (Mce.getMiesiacToNumber().get(r.getDokfk().getMiesiac()) < granicagorna && r.getKonto().equals(p.getKonto())) {
                        podpieteStronyWiersza.add(new StronaWierszaKwota(r, r.getKwotaPLN()));
                    }
                }
            }
        }
    }
    
    public void odswiezrzis() {
        wpisView.wpisAktualizuj();
        pobierzukladprzegladRZiS();
    }
    
    public void odswiezbilans() {
        wpisView.wpisAktualizuj();
        pobierzukladprzegladBilans("aktywa");
    }
    
    public void drukujBilans(String ap) {
        if (ap.equals("a")) {
            PdfBilans.drukujBilansBO(rootBilansAktywa, wpisView, ap);
        } else {
            PdfBilans.drukujBilansBO(rootBilansPasywa, wpisView, ap);
        }
    }
    
    public void drukujBilansPozycje(String ap) {
        if (ap.equals("a")) {
            PdfBilans.drukujBilansBOPozycje(rootBilansAktywa, wpisView, ap);
        } else {
            PdfBilans.drukujBilansBOPozycje(rootBilansPasywa, wpisView, ap);
        }
    }
       
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    

    public List<StronaWierszaKwota> getPodpieteStronyWiersza() {
        return podpieteStronyWiersza;
    }

    public void setPodpieteStronyWiersza(List<StronaWierszaKwota> podpieteStronyWiersza) {
        this.podpieteStronyWiersza = podpieteStronyWiersza;
    }

    public double getSumabilansowaaktywa() {
        return sumabilansowaaktywa;
    }

    public void setSumabilansowaaktywa(double sumabilansowaaktywa) {
        this.sumabilansowaaktywa = sumabilansowaaktywa;
    }

    public double getSumabilansowapasywa() {
        return sumabilansowapasywa;
    }

    public void setSumabilansowapasywa(double sumabilansowapasywa) {
        this.sumabilansowapasywa = sumabilansowapasywa;
    }

    public List<KontoKwota> getSumaPodpietychKont() {
        return sumaPodpietychKont;
    }

    public void setSumaPodpietychKont(List<KontoKwota> sumaPodpietychKont) {
        this.sumaPodpietychKont = sumaPodpietychKont;
    }

    public boolean isPokazaktywa() {
        return pokazaktywa;
    }

    public void setPokazaktywa(boolean pokazaktywa) {
        this.pokazaktywa = pokazaktywa;
    }

   

    
  
    public String getWybranapozycja() {
        return wybranapozycja;
    }

    public void setWybranapozycja(String wybranapozycja) {
        this.wybranapozycja = wybranapozycja;
    }

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

  

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

   

    public TreeNode getWybranynodekonta() {
        return wybranynodekonta;
    }

    public void setWybranynodekonta(TreeNode wybranynodekonta) {
        this.wybranynodekonta = wybranynodekonta;
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

