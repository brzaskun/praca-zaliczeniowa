    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PozycjaRZiSFKBean;
import beansFK.StronaWierszaBean;
import beansFK.UkladBRBean;
import converter.RomNumb;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.PozycjaBilansDAO;
import dao.PozycjaRZiSDAO;
import dao.StronaWierszaDAO;
import dao.UkladBRDAO;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import view.WpisView;
import wydajnosc.ConstructorInterceptor;
import xls.ReadCSVInterpaperFile;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class PozycjaBRWzorcowyView implements Serializable {
    private static final long serialVersionUID = 1L;

    private TreeNodeExtended wybranynodekonta;
    private List<PozycjaRZiSBilans> pozycje;
    private List<PozycjaRZiSBilans> pozycje_old;
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
    private List<TreeNodeExtended> finallNodes;
    private List<StronaWiersza> podpieteStronyWiersza;
    private List<Konto> sumaPodpietychKont;
    private boolean pokazaktywa;
    private boolean dodawanieformuly;
    
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject 
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private UkladBR uklad;
    private String nowanazwa;
    
    @Inject
    private WpisView wpisView;
    private String aktywapasywa;
    

    public PozycjaBRWzorcowyView() {
         ////E.m(this);
        this.wykazkont = Collections.synchronizedList(new ArrayList<>());
        this.nowyelementRZiS = new PozycjaRZiS();
        this.nowyelementBilans = new PozycjaBilans();
        this.root = new TreeNodeExtended("root", null);
        this.rootUklad = new TreeNodeExtended("root", null);
        this.rootProjektRZiS = new TreeNodeExtended("root", null);
        this.rootBilansAktywa = new TreeNodeExtended("root", null);
        this.rootBilansPasywa = new TreeNodeExtended("root", null);
        this.rootProjektKonta = new TreeNodeExtended("root", null);
        this.finallNodes = new ArrayList<TreeNodeExtended>();
        pozycje = Collections.synchronizedList(new ArrayList<>());
        pozycje_old = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    private void init() { //E.m(this);

        //(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, double kwota)
//        pozycje_old.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Przychody netto ze sprzedaży i zrównane z nimi, w tym:", false));
//        pozycje_old.add(new PozycjaRZiS(2, "A.I", "I", 1, 1, "Przychody netto ze sprzedaży produktów", false, 0.0));
//        pozycje_old.add(new PozycjaRZiS(3, "A.II", "II", 1, 1, "Zmiana stanu produktów", false, 0.0));
//        pozycje_old.add(new PozycjaRZiS(4, "A.III", "III", 1, 1, "Koszt wytworzenia produktów na własne potrzeby jednostki", true, 0.0));
//        pozycje_old.add(new PozycjaRZiS(5, "A.IV", "IV", 1, 1, "Przychody netto ze sprzedaży towarów i materiałów", false, 0.0));
//        pozycje_old.add(new PozycjaRZiS(6, "B", "B", 0, 0, "Koszty działalności operacyjnej", true));
//        pozycje_old.add(new PozycjaRZiS(7, "B.I", "I", 6, 1, "Amortyzacja", true));
//        pozycje_old.add(new PozycjaRZiS(8, "B.II", "II", 6, 1, "Zużycie materiałów i energii", true, 0.0));
//        pozycje_old.add(new PozycjaRZiS(9, "B.III", "III", 6, 1, "Usługi obce", true, 0.0));
//        pozycje_old.add(new PozycjaRZiS(10, "B.IV", "IV", 6, 1, "Podatki i  opłaty", true, 0.0));
//        pozycje_old.add(new PozycjaRZiS(11, "B.V", "V", 6, 1, "Wynagrodzenia", true, 0.0));
//        pozycje_old.add(new PozycjaRZiS(12, "B.I.1", "1", 7, 2, "amortyzacja kup", true, 0.0));
//        pozycje_old.add(new PozycjaRZiS(13, "B.I.2", "2", 7, 2, "amortyzacja nkup", true));
//        pozycje_old.add(new PozycjaRZiS(14, "B.I.2.a)", "a)", 13, 3, "bobopo", true, 0.0));
//        pozycje_old.add(new PozycjaRZiS(15, "C", "C", 0, 0, "Zysk (strata) ze sprzedaży (A-B)", false, "A-B"));
//        pozycje_old.add(new PozycjaRZiS(16, "D", "D", 0, 0, "Pozostałe truey operacyjne", false));
//        pozycje_old.add(new PozycjaRZiS(17, "D.I", "I", 16, 1, "Zysk z niefinansowych aktywów trwałych", false, 0.0));
//        pozycje_old.add(new PozycjaRZiS(18, "D.II", "II", 16, 1, "Dotacje", false, 0.0));
//        pozycje_old.add(new PozycjaRZiS(19, "D.III", "III", 16, 1, "Inne truey operacyjne", false, 0.0));
//        pozycje_old.add(new PozycjaRZiS(20, "E", "E", 0, 0, "Pozostałe koszty operacyjne", true));
//        pozycje_old.add(new PozycjaRZiS(21, "E.I", "I", 20, 1, "Strata z niefinansowych aktywów trwałych", true, 0.0));
//        pozycje_old.add(new PozycjaRZiS(22, "E.II", "II", 20, 1, "Aktualizacja aktywów niefinansowych", true, 0.0));
//        pozycje_old.add(new PozycjaRZiS(23, "E.III", "III", 20, 1, "Inne koszty operacyjne", true, 0.0));
//        pozycje_old.add(new PozycjaRZiS(24, "F", "F", 0, 0, "Zysk (strata) ze działalności operacyjnej (C+D-E)", false, "C+D-E"));
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
        this.aktywapasywa = aktywapasywa;
        pozycje = Collections.synchronizedList(new ArrayList<>());
        try {
         pozycje = UkladBRBean.pobierzpozycje(pozycjaRZiSDAO, pozycjaBilansDAO, uklad, aktywapasywa, br);
        } catch (Exception e) {  E.e(e);
        }   
        root.getChildren().clear();
        if (pozycje != null) {
            PozycjaRZiSFKBean.ustawRootaprojekt(root, pozycje);
            level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
            Msg.msg("i", "Pobrano układ ");
        } else {
            Msg.msg("e", "Pozycje sa puste");
        }
    }

    public void pobierzukladprzegladRZiS() {
        pozycje = Collections.synchronizedList(new ArrayList<>());
       try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
            if (pozycje.isEmpty()) {
               pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
        } catch (Exception e) {  E.e(e);
        }
        rootProjektRZiS.getChildren().clear();
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        try {
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                PozycjaRZiS p = (PozycjaRZiS) it.next();
                p.setPrzyporzadkowanestronywiersza(null);
            }
            PozycjaRZiSFKBean.ustawRoota(rootProjektRZiS, pozycje, zapisy);
            level = PozycjaRZiSFKBean.ustawLevel(rootProjektRZiS, pozycje);
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e){
            rootProjektRZiS.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
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
    
    public void pobierzukladprzegladBilans() {
        pobierzukladprzegladRZiS();
        List<Object> listazwrotnapozycji = Collections.synchronizedList(new ArrayList<>());
        rootProjektRZiS.getFinallChildrenData(new ArrayList<TreeNodeExtended>(), listazwrotnapozycji);
        PozycjaRZiS pozycjawynikfin = (PozycjaRZiS) listazwrotnapozycji.get(listazwrotnapozycji.size()-1);
        List<PozycjaRZiSBilans> pozycjeaktywa = Collections.synchronizedList(new ArrayList<>());
        List<PozycjaRZiSBilans> pozycjepasywa = Collections.synchronizedList(new ArrayList<>());
       try {
                pozycjeaktywa.addAll(pozycjaBilansDAO.findBilansukladAktywa(uklad));
                pozycjepasywa.addAll(pozycjaBilansDAO.findBilansukladPasywa(uklad));
            if (pozycjeaktywa.isEmpty()) {
               pozycjeaktywa.add(new PozycjaBilans(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            if (pozycjepasywa.isEmpty()) {
               pozycjepasywa.add(new PozycjaBilans(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
        } catch (Exception e) {  E.e(e);
        }
        rootBilansAktywa.getChildren().clear();
        rootBilansPasywa.getChildren().clear();
        List<StronaWiersza> zapisy = Collections.synchronizedList(new ArrayList<>());
        zapisy.addAll(stronaWierszaDAO.findStronaByPodatnikRokBilans(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
        try {
            List<Konto> plankont = kontoDAO.findKontaBilansowePodatnikaBezPotomkow(wpisView);
            Konto kontowyniku = null;
            for (Konto p : plankont) {
                if (p.getPelnynumer().equals("860")) {
                    kontowyniku = p;
                    break;
                }
            }
            double wynikfinansowy = pozycjawynikfin.getKwota();
            double wf = Math.abs(wynikfinansowy);
            if (wynikfinansowy > 0) {//zysk
                kontowyniku.setObrotyMa(wf);
                kontowyniku.setSaldoMa(wf);
            } else {//strata
                kontowyniku.setObrotyWn(wf);
                kontowyniku.setSaldoWn(wf);
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
            PozycjaRZiSFKBean.sumujObrotyNaKontach(zapisy, plankont);
            PozycjaRZiSFKBean.ustawRootaBilans(rootBilansAktywa, pozycjeaktywa, plankont,"aktywa");
            PozycjaRZiSFKBean.ustawRootaBilans(rootBilansPasywa, pozycjepasywa, plankont,"pasywa");
            level = PozycjaRZiSFKBean.ustawLevel(rootBilansAktywa, pozycje);
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e){
            rootBilansAktywa.getChildren().clear();
            rootBilansPasywa.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
        }
    }

   

    public void pobierzdanezplikur(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("xlsx")) {
                byte[] contents = uploadedFile.getContents();
                ReadCSVInterpaperFile.updateRZiSInter(pozycjaRZiSDAO, wpisView, contents);
                pobierzuklad("r", rootProjektRZiS, "");
            }
        }catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        
    }
    
    public void pobierzdanezpliku(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("xlsx")) {
                byte[] contents = uploadedFile.getContents();
                ReadCSVInterpaperFile.updateBilansInter(pozycjaBilansDAO, wpisView, contents);
                pobierzuklad("b", rootProjektRZiS, "aktywa");
            }
        }catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
    public void edytujpozycje(String bilansrzis) {
        try {
            if (bilansrzis.equals("bilans") && nowyelementBilans != null) {
                pozycjaBilansDAO.edit(nowyelementBilans);
                nowyelementBilans = new PozycjaBilans();
            } else if(nowyelementRZiS != null) {
                pozycjaRZiSDAO.edit(nowyelementRZiS);
                nowyelementRZiS = new PozycjaRZiS();
            }
            dodawanieformuly = false;
            Msg.msg("Zachowano zmiany w pozycji");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd. Niezachowano zmian w pozycji");
        }
    }

    public void dodajnowapozycje(String syntetycznaanalityczna) {
        if (syntetycznaanalityczna.equals("syntetyczna")) {
            //dodaje nowa syntetyke
            if (((PozycjaRZiS) rootProjektRZiS.getFirstChild()).getNazwa().equals("Kliknij tutaj i dodaj pierwszą pozycję")) {
                pozycje.remove(0);
                Msg.msg("i", nowyelementRZiS.getNazwa() + "zachowam pod A");
                nowyelementRZiS.setPozycjaSymbol("A");
                nowyelementRZiS.setPozycjaString("A");
                nowyelementRZiS.setLevel(0);
                nowyelementRZiS.setMacierzysta(null);
            } else {
                String poprzednialitera = ((PozycjaRZiS) rootProjektRZiS.getChildren().get(rootProjektRZiS.getChildCount() - 1).getData()).getPozycjaSymbol();
                String nowalitera = RomNumb.alfaInc(poprzednialitera);
                nowyelementRZiS.setPozycjaSymbol(nowalitera);
                nowyelementRZiS.setPozycjaString(nowalitera);
                nowyelementRZiS.setLevel(0);
                nowyelementRZiS.setMacierzysta(null);
                if (!(nowyelementRZiS.getFormula() instanceof String)) {
                    nowyelementRZiS.setFormula("");
                }
                Msg.msg("i", nowyelementRZiS.getNazwa() + "zachowam pod " + nowalitera);
            }
            nowyelementRZiS.setUklad(uklad.getUklad());
            nowyelementRZiS.setPodatnik(uklad.getPodatnik().getNazwapelna());
            nowyelementRZiS.setRok(uklad.getRok());
            try {
                pozycjaRZiSDAO.create(nowyelementRZiS);
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
            if (((PozycjaRZiS) rootProjektRZiS.getFirstChild()).getNazwa().equals("Kliknij tutaj i dodaj pierwszą pozycję")) {
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
                nastepnysymbol = zwrockolejnalitere();
            }
            nowyelementRZiS.setPozycjaSymbol(nastepnysymbol);
            nowyelementRZiS.setPozycjaString(parent.getPozycjaString() + "." + nastepnysymbol);
            nowyelementRZiS.setPrzychod0koszt1(parent.isPrzychod0koszt1());
            nowyelementRZiS.setLevel(level + 1);
            nowyelementRZiS.setMacierzysta(parent);
            if (!(nowyelementRZiS.getFormula() instanceof String)) {
                nowyelementRZiS.setFormula("");
            }
            nowyelementRZiS.setUklad(uklad.getUklad());
            nowyelementRZiS.setPodatnik(uklad.getPodatnik().getNazwapelna());
            nowyelementRZiS.setRok(uklad.getRok());
            try {
                pozycjaRZiSDAO.create(nowyelementRZiS);
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
    
    public void zachowajInt(TreeNodeExtended root) {
        List lista = new ArrayList();
        root.getChildrenTree(new ArrayList<TreeNodeExtended>(), lista);
        List<PozycjaRZiSBilans> pozycje = Collections.synchronizedList(new ArrayList<>());
        for (Object p : lista) {
            pozycje.add((PozycjaRZiSBilans) p);
        }
        pozycjaRZiSDAO.editList(pozycje);
        Msg.msg("Zachowano zmiany");
    }
    
    
    
    public void dodajnowapozycjeBilans(String syntetycznaanalityczna) {
        if (syntetycznaanalityczna.equals("syntetyczna")) {
            //dodaje nowa syntetyke
            if (((PozycjaBilans) rootProjektRZiS.getFirstChild()).getNazwa().equals("Kliknij tutaj i dodaj pierwszą pozycję")) {
                pozycje.remove(0);
                Msg.msg("i", nowyelementBilans.getNazwa() + "zachowam pod A");
                nowyelementBilans.setPozycjaSymbol("A");
                nowyelementBilans.setPozycjaString("A");
                nowyelementBilans.setLevel(0);
                nowyelementBilans.setMacierzysta(null);
            } else {
                String poprzednialitera = ((PozycjaBilans) rootProjektRZiS.getChildren().get(rootProjektRZiS.getChildCount() - 1).getData()).getPozycjaSymbol();
                String nowalitera = RomNumb.alfaInc(poprzednialitera);
                nowyelementBilans.setPozycjaSymbol(nowalitera);
                nowyelementBilans.setPozycjaString(nowalitera);
                nowyelementBilans.setLevel(0);
                nowyelementBilans.setMacierzysta(null);
                if (!(nowyelementBilans.getFormula() instanceof String)) {
                    nowyelementBilans.setFormula("");
                }
                Msg.msg("i", nowyelementBilans.getNazwa() + "zachowam pod " + nowalitera);
            }
            nowyelementBilans.setUklad(uklad.getUklad());
            nowyelementBilans.setPodatnik(uklad.getPodatnik().getNazwapelna());
            nowyelementBilans.setRok(uklad.getRok());
            try {
                pozycjaRZiSDAO.create(nowyelementBilans);
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
            if (((PozycjaBilans) rootProjektRZiS.getFirstChild()).getNazwa().equals("Kliknij tutaj i dodaj pierwszą pozycję")) {
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
                nastepnysymbol = zwrockolejnalitere();
            }
            nowyelementBilans.setPozycjaSymbol(nastepnysymbol);
            nowyelementBilans.setPozycjaString(parent.getPozycjaString() + "." + nastepnysymbol);
            nowyelementBilans.setPrzychod0koszt1(parent.isPrzychod0koszt1());
            nowyelementBilans.setLevel(level + 1);
            nowyelementBilans.setMacierzysta(parent);
            if (!(nowyelementBilans.getFormula() instanceof String)) {
                nowyelementBilans.setFormula("");
            }
            nowyelementBilans.setUklad(uklad.getUklad());
            nowyelementBilans.setPodatnik(uklad.getPodatnik().getNazwapelna());
            nowyelementBilans.setRok(uklad.getRok());
            try {
                pozycjaRZiSDAO.create(nowyelementBilans);
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
   
    private String zwrockolejnalitere() {
        int levelp = ((PozycjaRZiSBilans) wybranynodekonta.getData()).getLevel()+1;
        String pozycjaoczekiwana = PozycjaRZiSFKBean.zwrocPierwszySymbol(levelp);
        List<TreeNode> lista = wybranynodekonta.getChildren();
        for (TreeNode p : lista) {
            PozycjaRZiSBilans r = (PozycjaRZiSBilans)(p.getData());
            int level = r.getLevel();
            String pozycjazastana = r.getPozycjaSymbol();
            String nastepnysymbol = PozycjaRZiSFKBean.zwrocNastepnySymbol(level, pozycjaoczekiwana);
            if (pozycjaoczekiwana.equals(pozycjazastana)) {
                pozycjaoczekiwana = nastepnysymbol;
            } else {
                break;
            }
        }
        return pozycjaoczekiwana;
    }
    
            
    public void implementujdopochodnych() {
        try {
            if (wybranynodekonta.getChildCount() > 0) {
                Msg.msg("w", "Wybrana pozycja Bilansu/RZiS zawiera podpunkty!");
            } else {
                PozycjaRZiSBilans implementowanapozycja = (PozycjaRZiSBilans) wybranynodekonta.getData();
                if (implementowanapozycja.getDe()==null) {
                    Msg.msg("e", "Brak tłumaczenia na niemiecki, nie można implementować");
                } else {
                    List<UkladBR> uklady = ukladBRDAO.findRokUkladnazwa(implementowanapozycja.getRok(),implementowanapozycja.getUklad());
                    for (UkladBR u : uklady) {
                        if (!u.getPodatnik().getNip().equals("9999999999")) {
                            dodajpozycje(u, implementowanapozycja);
                        }
                    }
                    Msg.msg("i", "Zaimplementowano pozycje");
                }
            }
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie udało się zaimplementować pozycji");
        }
    }
    
    private void dodajpozycje(UkladBR u, PozycjaRZiSBilans implementowanapozycja) {
        try {
            PozycjaRZiSBilans nowa = serialclone.SerialClone.clone(implementowanapozycja);
            nowa.setPodatnik(u.getPodatnik().getNazwapelna());
            nowa.setLp(null);
            pozycjaBilansDAO.create(nowa);
        } catch (Exception e) {
            Msg.msg("e", "Bład nie dodano pozycji dla podatnika "+u.getPodatnik().getPrintnazwa());
        }
    }

       
    public boolean usunpozycje() {
        boolean zwrot = false;
        try {
            if (wybranynodekonta.getChildCount() > 0) {
                Msg.msg("w", "Wybrana pozycja RZiS zawiera podpunkty!");
                throw new Exception();
            }
            pozycje.remove(wybranynodekonta.getData());
            pozycjaRZiSDAO.remove(wybranynodekonta.getData());
            if (pozycje.isEmpty()) {
                pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            rootProjektRZiS = new TreeNodeExtended("root", null);
            PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektRZiS, pozycje);
            level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
            zwrot = true;
            Msg.msg("i", "Usuwam w RZiS");
        } catch (Exception e) {  
            E.e(e);
            Msg.msg("e", "Nie udało się usunąć pozycji w RZiS");
        }
        return zwrot;
    }
    
    public void usunpozycjeimplantacje() {
        boolean usunnajpierw = usunpozycje();
        PozycjaRZiSBilans p = (PozycjaRZiSBilans) wybranynodekonta.getData();
        List<KontopozycjaZapis> kontopozycjebilans = kontopozycjaZapisDAO.findKontoPozycjaByRokUkladBilans(wpisView.getRokWpisuSt(), p.getUklad());
        List<KontopozycjaZapis> kontopozycjerzis = kontopozycjaZapisDAO.findKontoPozycjaByRokUkladRzis(wpisView.getRokWpisuSt(), p.getUklad());
        List<KontopozycjaZapis> dousuniecia = new ArrayList<>();
        try {
            if (usunnajpierw) {
                List<PozycjaRZiSBilans> pozycjepodatnikow = null;
                String classname = p.getClass().getName();
                if (classname.equals("entityfk.PozycjaBilans")) {
                    pozycjepodatnikow = pozycjaBilansDAO.findBilansPozString(p.getPozycjaString(), wpisView.getRokWpisuSt(), p.getUklad());
                    for (PozycjaRZiSBilans s : pozycjepodatnikow) {
                        dousuniecia.addAll(znajdzdousuniecia(kontopozycjebilans,s));
                    }
                    for (KontopozycjaZapis s : dousuniecia) {
                        try {
                            kontopozycjaZapisDAO.remove(s);
                        } catch (Exception e) {}
                    }
                    pozycjaBilansDAO.remove(pozycjepodatnikow);
                    Msg.msg("Udane usuniecie pozycji Bilansu");
                } else {
                    pozycjepodatnikow = pozycjaRZiSDAO.findRZiSPozString(p.getPozycjaString(), wpisView.getRokWpisuSt(), p.getUklad());
                    for (PozycjaRZiSBilans s : pozycjepodatnikow) {
                        dousuniecia.addAll(znajdzdousunieciaRziS(kontopozycjerzis,s));
                    }
                    for (KontopozycjaZapis s : dousuniecia) {
                        try {
                            kontopozycjaZapisDAO.remove(s);
                        } catch (Exception e) {}
                    }
                    pozycjaRZiSDAO.remove(pozycjepodatnikow);
                    Msg.msg("Udane usuniecie pozycji RZiS");
                }
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie udało się usunąć pozycji w bilansie/RZiS implementacja");
        }
    }
    
    
    private List<KontopozycjaZapis> znajdzdousuniecia(List<KontopozycjaZapis> kontopozycjebilans, PozycjaRZiSBilans s) {
        List<KontopozycjaZapis> dousuniecia = new ArrayList<>();
        for (KontopozycjaZapis p : kontopozycjebilans) {
            if (s.isPrzychod0koszt1()== false && p.getStronaWn().equals("0") && (s.getPozycjaString().equals(p.getPozycjaMa())||s.getPozycjaString().equals(p.getPozycjaWn()))) {
                dousuniecia.add(p);
            } else if (s.isPrzychod0koszt1()== true && p.getStronaMa().equals("1") && (s.getPozycjaString().equals(p.getPozycjaMa())||s.getPozycjaString().equals(p.getPozycjaMa()))) {
                dousuniecia.add(p);
            }
        }
        return dousuniecia;
    }
    
    private List<KontopozycjaZapis> znajdzdousunieciaRziS(List<KontopozycjaZapis> kontopozycjebilans, PozycjaRZiSBilans s) {
        List<KontopozycjaZapis> dousuniecia = new ArrayList<>();
        for (KontopozycjaZapis p : kontopozycjebilans) {
            if (s.isPrzychod0koszt1()== false && p.getStronaWn().equals("99") && (s.getPozycjaString().equals(p.getPozycjaMa())||s.getPozycjaString().equals(p.getPozycjaWn()))) {
                dousuniecia.add(p);
            } else if (s.isPrzychod0koszt1()== true && p.getStronaMa().equals("99") && (s.getPozycjaString().equals(p.getPozycjaMa())||s.getPozycjaString().equals(p.getPozycjaMa()))) {
                dousuniecia.add(p);
            }
        }
        return dousuniecia;
    }
    
    
    public void usunwszystkie() {
        try {
            for (PozycjaRZiSBilans p : pozycje) {
                pozycjaRZiSDAO.remove(p);
            }
            pozycje = Collections.synchronizedList(new ArrayList<>());
            if (pozycje.isEmpty()) {
                pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            rootProjektRZiS = new TreeNodeExtended("root", null);
            PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektRZiS, pozycje);
            level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
            Msg.msg("i", "Usunąłem wyszstkie pozycje w bilansie wzorcowym");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie udało się usunąć wszystkich pozycji w bilansie wzorcowym");
        }
    }
    public void przesunwgore() {
        try {
            if (wybranynodekonta == null) {
                Msg.msg("e", "Nie wybrano pozycji");
            } else {
                PozycjaRZiSBilans p  = (PozycjaRZiSBilans) wybranynodekonta.getData();
                TreeNode parent = wybranynodekonta.getParent();
                int level = ((PozycjaRZiSBilans) wybranynodekonta.getData()).getLevel();
                String poprzednisymbol = PozycjaRZiSFKBean.zwrocPoprzedniSymbol(level, p.getPozycjaSymbol());
                if (poprzednisymbol==null) {
                    Msg.msg("e", "Nie można przesunąć wyżej. To pierwszy element");
                } else {
                    p.setPozycjaSymbol(poprzednisymbol);
                    String ps = !parent.getData().equals("root") ? ((PozycjaRZiSBilans) parent.getData()).getPozycjaString() + "." : "";
                    p.setPozycjaString(ps + poprzednisymbol);
                    pozycjaBilansDAO.edit(p);
                    if (wybranynodekonta.getChildCount() > 0) {
                        for (TreeNode child : parent.getChildren()) {
                            przenumerujdzieci(child);
                        }
                    }
                    rootProjektRZiS = new TreeNodeExtended("root", null);
                    PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektRZiS, pozycje);
                    Msg.msg("i", "Przenumerowano pozycje");
                }
            }
        } catch (Exception e) {  
            E.e(e);
            Msg.msg("e", "Nie udało się przenumerować pozycji");
        }
    } 
    public void przesunwdol() {
        try {
            if (wybranynodekonta == null) {
                Msg.msg("e", "Nie wybrano pozycji");
            } else {
                PozycjaRZiSBilans p  = (PozycjaRZiSBilans) wybranynodekonta.getData();
                TreeNode parent = wybranynodekonta.getParent();
                int level = ((PozycjaRZiSBilans) wybranynodekonta.getData()).getLevel();
                String nastepnysymbol = PozycjaRZiSFKBean.zwrocNastepnySymbol(level, p.getPozycjaSymbol());
                String ps = !parent.getData().equals("root") ? ((PozycjaRZiSBilans) parent.getData()).getPozycjaString() + "." : "";
                p.setPozycjaString(ps + nastepnysymbol);
                p.setPozycjaSymbol(nastepnysymbol);
                pozycjaBilansDAO.edit(p);
                if (wybranynodekonta.getChildCount() > 0) {
                    for (TreeNode child : parent.getChildren()) {
                        przenumerujdzieci(child);
                    }
                }
                rootProjektRZiS = new TreeNodeExtended("root", null);
                PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektRZiS, pozycje);
                Msg.msg("i", "Przenumerowano pozycje");
            }
        } catch (Exception e) {  
            E.e(e);
            Msg.msg("e", "Nie udało się przenumerować pozycji");
        }
    }
    
    private void przenumerujdzieci (TreeNode superchild) {
        PozycjaRZiSBilans p  = (PozycjaRZiSBilans) superchild.getData();
        TreeNode parent = superchild.getParent();
        p.setPozycjaString(((PozycjaRZiSBilans) parent.getData()).getPozycjaString() + "." + p.getPozycjaSymbol());
        pozycjaBilansDAO.edit(p);
        if (superchild.getChildren()!= null) {
           for (TreeNode r : superchild.getChildren()) {
               przenumerujdzieci(r);
           }
        }
    }
    
    public void usunpozycjeprzenumeruj() {
        try {
            if (wybranynodekonta.getChildCount() > 0) {
                Msg.msg("w", "Wybrana pozycja RZiS zawiera podpunkty!");
                throw new Exception();
            }
            int level = ((PozycjaRZiSBilans) wybranynodekonta.getData()).getLevel();
            pozycje.remove(wybranynodekonta.getData());
            pozycjaRZiSDAO.remove(wybranynodekonta.getData());
            if (pozycje.isEmpty()) {
                pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            } else {
                String pierwszysymbol = PozycjaRZiSFKBean.zwrocPierwszySymbol(level);
                TreeNode parent = wybranynodekonta.getParent();
                if (parent != null) {
                    for (TreeNode child : parent.getChildren()) {
                        if (!child.equals(wybranynodekonta)) {
                            PozycjaRZiSBilans chd = (PozycjaRZiSBilans) child.getData();
                            chd.setPozycjaSymbol(pierwszysymbol);
                            chd.setPozycjaString(((PozycjaRZiSBilans) parent.getData()).getPozycjaString() + "." + pierwszysymbol);
                            pierwszysymbol = PozycjaRZiSFKBean.zwrocNastepnySymbol(level, pierwszysymbol);
                            pozycjaBilansDAO.edit(chd);
                        }
                    }
                }
            }
            rootProjektRZiS = new TreeNodeExtended("root", null);
            PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektRZiS, pozycje);
            level = PozycjaRZiSFKBean.ustawLevel(root, pozycje);
            Msg.msg("i", "Usuwam pozycję w Bilansie/RZiS wzorcowym");
        } catch (Exception e) {  
            E.e(e);
            Msg.msg("e", "Nie udało się usunąć pozycji w Bilansie/RZiS wzorcowym");
        }
            
    }


//    public void zmien() {
//        List<Konto> lista = kontoDAO.findAll();
//        for (Konto p : lista) {
//            if(p.getPodatnik().equals("Testowy")) {
//                p.setPodatnik("Wzorcowy");
//                kontoDAO.edit(p);
//            }
//        }
//        List<PozycjaBilans> lista2 = pozycjaBilansDAO.findAll();
//        for (PozycjaRZiSBilans p : lista2) {
//            if(p.getPodatnik().equals("Tymczasowy")) {
//                p.setPodatnik("Wzorcowy");
//                kontoDAO.edit(p);
//            }
//        }
//        List<PozycjaRZiS> lista3 = pozycjaRZiSDAO.findAll();
//        for (PozycjaRZiSBilans p : lista3) {
//            if(p.getPodatnik().equals("Tymczasowy")) {
//                p.setPodatnik("Wzorcowy");
//                kontoDAO.edit(p);
//            }
//        }
//    }
    
    
    public void wyluskajStronyzPozycji() {
        sumaPodpietychKont = Collections.synchronizedList(new ArrayList<>());
        podpieteStronyWiersza = Collections.synchronizedList(new ArrayList<>());
        for (TreeNode p : selectedNodes) {
            PozycjaRZiSBilans r = (PozycjaRZiSBilans) p.getData();
            if (r.getPrzyporzadkowanestronywiersza() != null) {
                podpieteStronyWiersza.addAll(r.getPrzyporzadkowanestronywiersza());
            }
        }
        List<Konto> konta = Collections.synchronizedList(new ArrayList<>());
        for (StronaWiersza p : podpieteStronyWiersza) {
            Konto k = p.getKonto();
            if (!konta.contains(k)) {
                konta.add(k);
                k.setKwota(p.getKwota());
                sumaPodpietychKont.add(k);
            } else {
                for (Konto r : sumaPodpietychKont) {
                    if (r.equals(k)) {
                        r.setKwota(r.getKwota()+p.getKwota());
                    }
                }
            }
        }
    }
    
    public void wyluskajStronyzPozycjiBilans() {
        podpieteStronyWiersza = Collections.synchronizedList(new ArrayList<>());
        sumaPodpietychKont = Collections.synchronizedList(new ArrayList<>());
        List<Konto> podpieteKonta = Collections.synchronizedList(new ArrayList<>());
        for (TreeNode p : selectedNodes) {
            PozycjaRZiSBilans r = (PozycjaRZiSBilans) p.getData();
            if (r.getPrzyporzadkowanekonta() != null) {
                podpieteKonta.addAll(r.getPrzyporzadkowanekonta());
            }
        }
        List<Konto> konta = Collections.synchronizedList(new ArrayList<>());
        for (Konto p : podpieteKonta) {
            if (!konta.contains(p)) {
                konta.add(p);
                sumaPodpietychKont.add(p);
            } else {
                for (Konto r : sumaPodpietychKont) {
                    if (r.equals(p)) {
                        r.setKwota(r.getKwota()+p.getKwota());
                    }
                }
            }
        }
        for (Konto p : sumaPodpietychKont) {
            List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkie(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt());
            for (StronaWiersza r : stronywiersza) {
                podpieteStronyWiersza.add(r);
            }
        }
    }
    
    public void skopiujnazwe(String rb) {
        if (rb.equals("b")) {
            nowanazwa = ((PozycjaBilans) wybranynodekonta.getData()).getNazwa();
        } else {
            nowanazwa = ((PozycjaRZiS) wybranynodekonta.getData()).getNazwa();
        }
    }
    
    public void skopiujpole(String rb) {
        if (rb.equals("b")) {
            nowyelementBilans = (PozycjaBilans) wybranynodekonta.getData();
        } else {
            nowyelementRZiS = (PozycjaRZiS) wybranynodekonta.getData();
        }
        dodawanieformuly = true;
    }
    
    public void zmiennazwepozycji() {
        try {
            String classname = wybranynodekonta.getData().getClass().getName();
            if (classname.equals("entityfk.PozycjaBilans")) {
                PozycjaBilans p = (PozycjaBilans) wybranynodekonta.getData();
                p.setNazwa(nowanazwa);
                pozycjaBilansDAO.edit(p);
                implementujzmianenazwy(p);
                Msg.msg("Zmieniono nazwę pozycji bilansu");
            } else {
                PozycjaRZiS r = (PozycjaRZiS) wybranynodekonta.getData();
                r.setNazwa(nowanazwa);
                pozycjaBilansDAO.edit(r);
                implementujzmianenazwy(r);
                Msg.msg("Zmieniono nazwę pozycji RZiS");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd przy zmianai nazwy pozycji bilansu/RZiS");
        }
    }
    
    public void wybranopozycjeRZiS() {
        String nazwa = ((PozycjaRZiS) wybranynodekonta.getData()).getNazwa();
        Msg.msg("Wybrano pozycję "+nazwa);
    }
    
    public void wybranopozycjeBilans() {
        String nazwa = ((PozycjaBilans) wybranynodekonta.getData()).getNazwa();
        Msg.msg("Wybrano pozycję "+nazwa);
    }
       
    //<editor-fold defaultstate="collapsed" desc="comment">

    public String getNowanazwa() {
        return nowanazwa;
    }

    public void setNowanazwa(String nowanazwa) {
        this.nowanazwa = nowanazwa;
    }
    
    

    public List<StronaWiersza> getPodpieteStronyWiersza() {
        return podpieteStronyWiersza;
    }

    public void setPodpieteStronyWiersza(List<StronaWiersza> podpieteStronyWiersza) {
        this.podpieteStronyWiersza = podpieteStronyWiersza;
    }

    public List<Konto> getSumaPodpietychKont() {
        return sumaPodpietychKont;
    }

    public void setSumaPodpietychKont(List<Konto> sumaPodpietychKont) {
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

    public TreeNodeExtended getWybranynodekonta() {
        return wybranynodekonta;
    }

    public void setWybranynodekonta(TreeNodeExtended wybranynodekonta) {
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
    
    public boolean isDodawanieformuly() {
        return dodawanieformuly;
    }

    public void setDodawanieformuly(boolean dodawanieformuly) {
        this.dodawanieformuly = dodawanieformuly;
    }
   
    
    //</editor-fold>

    private void implementujzmianenazwy(PozycjaRZiSBilans p) {
        List<PozycjaRZiSBilans> pozycjepodatnikow = null;
        String classname = p.getClass().getName();
        if (classname.equals("entityfk.PozycjaBilans")) {
            pozycjepodatnikow = pozycjaBilansDAO.findBilansPozString(p.getPozycjaString(), wpisView.getRokWpisuSt(), p.getUklad());
            for (PozycjaRZiSBilans r : pozycjepodatnikow) {
                r.setNazwa(p.getNazwa());
                r.setDe(p.getDe());
            }
            //pozycjaBilansDAO.editList(pozycjepodatnikow);
            Msg.msg("Udana implementacja zmiany");
        } else {
            pozycjepodatnikow = pozycjaRZiSDAO.findRZiSPozString(p.getPozycjaString(), wpisView.getRokWpisuSt(), p.getUklad());
            for (PozycjaRZiSBilans r : pozycjepodatnikow) {
                r.setNazwa(p.getNazwa());
                r.setDe(p.getDe());
            }
            //pozycjaBilansDAO.editList(pozycjepodatnikow);
            Msg.msg("Udana implementacja zmiany");
        }
        error.E.s("");
    }

    
public String getAktywapasywa() {
    return this.aktywapasywa;
}

   

}


