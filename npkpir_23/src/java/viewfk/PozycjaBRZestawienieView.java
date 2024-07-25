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
import dao.PodatnikOpodatkowanieDAO;
import dao.PozycjaBilansDAO;
import dao.PozycjaRZiSDAO;
import dao.StronaWierszaDAO;
import dao.UkladBRDAO;
import dao.WierszBODAO;
import data.Data;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import interceptor.ConstructorInterceptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import org.primefaces.model.TreeNode;
import pdffk.PdfBilans;
import pdffk.PdfRZiS;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class PozycjaBRZestawienieView implements Serializable {
    private static final long serialVersionUID = 1L;

    private TreeNode wybranynodekonta;
    private List<PozycjaRZiSBilans> pozycje;
    private List<PozycjaRZiSBilans> pozycje_old;
    private String wybranapozycja;
    
    private int level = 0;
    private List<Konto> wykazkont;
    private TreeNodeExtended root;
    private TreeNodeExtended rootUklad;
    private TreeNodeExtended rootProjektRZiS;
    private TreeNodeExtended rootBilans;
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
    private double sumabilansowaaktywa;
    private double sumabilansowapasywa;
    
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject 
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    @Inject
    private UkladBR uklad;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    private boolean laczlata;
    private String bilansnadzien;
    private String bilansoddnia;
    private String kontabilansowebezprzyporzadkowania;
    
    @Inject
    private WpisView wpisView;
    

    public PozycjaBRZestawienieView() {
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
    public void init() { //E.m(this);
        try {
            uklad = ukladBRDAO.findukladBRPodatnikRokAktywny(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            //(int lp, String pozycjaString, String pozycjaSymbol, int macierzysty, int level, String nazwa, boolean przychod0koszt1, double kwota)
//            pozycje_old.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Przychody netto ze sprzedaży i zrównane z nimi, w tym:", false));
//            pozycje_old.add(new PozycjaRZiS(2, "A.I", "I", 1, 1, "Przychody netto ze sprzedaży produktów", false, 0.0));
//            pozycje_old.add(new PozycjaRZiS(3, "A.II", "II", 1, 1, "Zmiana stanu produktów", false, 0.0));
//            pozycje_old.add(new PozycjaRZiS(4, "A.III", "III", 1, 1, "Koszt wytworzenia produktów na własne potrzeby jednostki", true, 0.0));
//            pozycje_old.add(new PozycjaRZiS(5, "A.IV", "IV", 1, 1, "Przychody netto ze sprzedaży towarów i materiałów", false, 0.0));
//            pozycje_old.add(new PozycjaRZiS(6, "B", "B", 0, 0, "Koszty działalności operacyjnej", true));
//            pozycje_old.add(new PozycjaRZiS(7, "B.I", "I", 6, 1, "Amortyzacja", true));
//            pozycje_old.add(new PozycjaRZiS(8, "B.II", "II", 6, 1, "Zużycie materiałów i energii", true, 0.0));
//            pozycje_old.add(new PozycjaRZiS(9, "B.III", "III", 6, 1, "Usługi obce", true, 0.0));
//            pozycje_old.add(new PozycjaRZiS(10, "B.IV", "IV", 6, 1, "Podatki i  opłaty", true, 0.0));
//            pozycje_old.add(new PozycjaRZiS(11, "B.V", "V", 6, 1, "Wynagrodzenia", true, 0.0));
//            pozycje_old.add(new PozycjaRZiS(12, "B.I.1", "1", 7, 2, "amortyzacja kup", true, 0.0));
//            pozycje_old.add(new PozycjaRZiS(13, "B.I.2", "2", 7, 2, "amortyzacja nkup", true));
//            pozycje_old.add(new PozycjaRZiS(14, "B.I.2.a)", "a)", 13, 3, "bobopo", true, 0.0));
//            pozycje_old.add(new PozycjaRZiS(15, "C", "C", 0, 0, "Zysk (strata) ze sprzedaży (A-B)", false, "A-B"));
//            pozycje_old.add(new PozycjaRZiS(16, "D", "D", 0, 0, "Pozostałe truey operacyjne", false));
//            pozycje_old.add(new PozycjaRZiS(17, "D.I", "I", 16, 1, "Zysk z niefinansowych aktywów trwałych", false, 0.0));
//            pozycje_old.add(new PozycjaRZiS(18, "D.II", "II", 16, 1, "Dotacje", false, 0.0));
//            pozycje_old.add(new PozycjaRZiS(19, "D.III", "III", 16, 1, "Inne truey operacyjne", false, 0.0));
//            pozycje_old.add(new PozycjaRZiS(20, "E", "E", 0, 0, "Pozostałe koszty operacyjne", true));
//            pozycje_old.add(new PozycjaRZiS(21, "E.I", "I", 20, 1, "Strata z niefinansowych aktywów trwałych", true, 0.0));
//            pozycje_old.add(new PozycjaRZiS(22, "E.II", "II", 20, 1, "Aktualizacja aktywów niefinansowych", true, 0.0));
//            pozycje_old.add(new PozycjaRZiS(23, "E.III", "III", 20, 1, "Inne koszty operacyjne", true, 0.0));
//            pozycje_old.add(new PozycjaRZiS(24, "F", "F", 0, 0, "Zysk (strata) ze działalności operacyjnej (C+D-E)", false, "C+D-E"));
            pozycje.addAll(pozycjaRZiSDAO.findAll());
            bilansnadzien = Data.ostatniDzien(wpisView);
            bilansoddnia = wpisView.getRokUprzedniSt()+"-12-31";
        } catch (Exception e){}
    }
    
// to jest uruchamiane po wyborze ukladu pierwsza funkcja
    public void pobierzuklad(String br, TreeNodeExtended root, String aktywapasywa) {
        pozycje = Collections.synchronizedList(new ArrayList<>());
        try {
         if (br.equals("r")) {
                pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
                if (pozycje.isEmpty()) {
                   pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                    Msg.msg("i", "Dodaje pusta pozycje");
                }
            } else {
                if (aktywapasywa.equals("aktywa")) {
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladAktywa(uklad));
                } else {
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladPasywa(uklad));
                }
                if (pozycje.isEmpty()) {
                   pozycje.add(new PozycjaBilans(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
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

    public List<PozycjaRZiSBilans> obliczRZiSOtwarciaRZiSDataXML() {
        obliczRZiSOtwarciaRZiSData();
        List<PozycjaRZiSBilans> poz =  new ArrayList<>();
        rootProjektRZiS.getChildrenTree( new ArrayList(), poz);
        return poz;
    }
    
    public void obliczRZiSOtwarciaRZiSData() {
        if (uklad == null || uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
        List<PozycjaRZiSBilans> pozycje = Collections.synchronizedList(new ArrayList<>());
        pobierzPozycje(pozycje);
        rootProjektRZiS.getChildren().clear();
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        List<StronaWiersza> zapisyRokPop = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt(), "12");
         List<Konto> pustekonta = kontoDAO.findKontaWynikowePodatnikaBezPotomkowPuste(wpisView);
        if (pustekonta.isEmpty()==false) {
            kontabilansowebezprzyporzadkowania = pustekonta.stream().map(Konto::getPelnynumer).collect(Collectors.toList()).toString();
        }
        try {
            PozycjaRZiSFKBean.ustawRootaRokPop(rootProjektRZiS, pozycje, zapisy, zapisyRokPop);
            level = PozycjaRZiSFKBean.ustawLevel(rootProjektRZiS, pozycje);
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e) {
            E.e(e);
            rootProjektRZiS.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
        }
        bilansnadzien = Data.ostatniDzien(wpisView);
    }
    public void zmianaukladprzegladRZiS() {
        UkladBRBean.ustawAktywny(uklad, ukladBRDAO);
        List<PozycjaRZiSBilans> pozycje = Collections.synchronizedList(new ArrayList<>());
        pobierzPozycje(pozycje);
        wyczyscKonta("wynikowe", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaZapisDAO, uklad, "wynikowe");
        pobierzukladprzegladRZiSWybierz();
        bilansnadzien = Data.ostatniDzien(wpisView);
    }
    
    public void zmianaukladprzegladRZiSBO() {
        UkladBRBean.ustawAktywny(uklad, ukladBRDAO);
        List<PozycjaRZiSBilans> pozycje = Collections.synchronizedList(new ArrayList<>());
        pobierzPozycje(pozycje);
        wyczyscKonta("wynikowe", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaZapisDAO, uklad, "wynikowe");
        obliczRZiSOtwarciaRZiSData();
        bilansnadzien = Data.ostatniDzien(wpisView);
    }
    
            
    public void zmianaukladprzegladBilans() {
        UkladBRBean.ustawAktywny(uklad, ukladBRDAO);
        List<PozycjaRZiSBilans> pozycje = Collections.synchronizedList(new ArrayList<>());
        pobierzPozycje(pozycje);
        wyczyscKonta("bilansowe", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaZapisDAO, uklad, "bilansowe");
        pobierzukladprzegladBilans();
        bilansnadzien = Data.ostatniDzien(wpisView);
    }
    
    public List<PozycjaRZiSBilans> pobierzukladprzegladRZiSWybierz() {
        if (laczlata) {
            pobierzukladprzegladRZiSDwaLata();
        } else {
            pobierzukladprzegladRZiS();
        }
        List<PozycjaRZiSBilans> poz =  new ArrayList<>();
        rootProjektRZiS.getChildrenTree( new ArrayList(), poz);
        return poz;
    }
    
    public void pobierzukladprzegladRZiSDwaLata() {
        if (uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
        PodatnikOpodatkowanieD opodatkowanie = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt());
         bilansoddnia = opodatkowanie.getDatarozpoczecia();
        List<PozycjaRZiSBilans> pozycje = Collections.synchronizedList(new ArrayList<>());
        pobierzPozycje(pozycje);
        rootProjektRZiS.getChildren().clear();
        List<StronaWiersza> zapisyrokpoprzedni = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, "12", wpisView.getRokUprzedniSt(), wpisView.getPodatnikObiekt());
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
        if (zapisyrokpoprzedni != null) {
            zapisy.addAll(zapisyrokpoprzedni);
        }
        try {
            PozycjaRZiSFKBean.ustawRoota(rootProjektRZiS, pozycje, zapisy);
            //to niepotrzebne bo usunalem przyciski zwijania, rozwijania
            //level = PozycjaRZiSFKBean.ustawLevel(rootProjektRZiS, pozycje);
            Msg.msg("i", "Obliczono rachunek zysków i strat");
        } catch (Exception e) {
            E.e(e);
            rootProjektRZiS.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
        }
    }
    
    
    public void pobierzukladprzegladRZiS() {
        if (uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
        List<PozycjaRZiSBilans> pozycje = Collections.synchronizedList(new ArrayList<>());
        pobierzPozycje(pozycje);
        rootProjektRZiS.getChildren().clear();
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
        try {
            PozycjaRZiSFKBean.ustawRoota(rootProjektRZiS, pozycje, zapisy);
            //to niepotrzebne bo usunalem przyciski zwijania, rozwijania
            //level = PozycjaRZiSFKBean.ustawLevel(rootProjektRZiS, pozycje);
            Msg.msg("i", "Obliczono rachunek zysków i strat");
        } catch (Exception e) {
            E.e(e);
            rootProjektRZiS.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
        }
    }
    
    private void wyczyscKonta(String rb, Podatnik podatnik, String rok) {
        if (rb.equals("wynikowe")) {
            List<Konto> listakont = kontoDAO.findWszystkieKontaWynikowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        } else {
            List<Konto> listakont = kontoDAO.findWszystkieKontaBilansowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        }
    }
    private void pobierzPozycje(List<PozycjaRZiSBilans> pozycje) {
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
            if (pozycje.isEmpty()) {
               pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
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
            rootBilans = rootBilansAktywa;
        } else {
            pokazaktywa = false;
            pobierzukladprzegladBilans();
            rootBilans = rootBilansPasywa;
        }
    }
    
    public void pokazukladprzegladBilans(String aktywapasywa) {
        if (aktywapasywa.equals("aktywa")) {
            pokazaktywa = true;
            rootBilans = rootBilansAktywa;
        } else {
            pokazaktywa = false;
            rootBilans = rootBilansPasywa;
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
        List<PozycjaRZiSBilans> pozycjeaktywa = Collections.synchronizedList(new ArrayList<>());
        List<PozycjaRZiSBilans> pozycjepasywa = Collections.synchronizedList(new ArrayList<>());
        pobierzPozycjeAktywaPasywa(pozycjeaktywa, pozycjepasywa);
        rootBilansAktywa.getChildren().clear();
        rootBilansPasywa.getChildren().clear();
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowbilansowe(stronaWierszaDAO, wpisView);
        try {
            List<Konto> plankont = kontoDAO.findKontaBilansowePodatnikaBezPotomkow(wpisView);
            Konto kontowyniku = findKonto860(plankont);
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

    private Konto findKonto860(List<Konto> plankont) {
        for (Konto p : plankont) {
            if (p.getPelnynumer().equals("860")) {
                return p;
            }
        }
        return null;
    }
    
    private void naniesKwoteWynikFinansowy(Konto kontowyniku) {
        pobierzukladprzegladRZiSWybierz();
        List<Object> listazwrotnapozycji = Collections.synchronizedList(new ArrayList<>());
        rootProjektRZiS.getFinallChildrenData(new ArrayList<TreeNodeExtended>(), listazwrotnapozycji);
        PozycjaRZiS pozycjawynikfin = (PozycjaRZiS) listazwrotnapozycji.get(listazwrotnapozycji.size() - 1);
        double wynikfinansowy = pozycjawynikfin.getKwota();
        double wf = Z.z(Math.abs(wynikfinansowy));
        if (wynikfinansowy > 0) {//zysk
            kontowyniku.setObrotyMa(kontowyniku.getObrotyMa()+wf);
        } else {//strata
            kontowyniku.setObrotyWn(kontowyniku.getObrotyWn()+wf);
        }
        double wynikkwota = kontowyniku.getObrotyWn()-kontowyniku.getObrotyMa();
        if ( wynikkwota > 0) {
            kontowyniku.setSaldoWn(wynikkwota);
        } else {
            kontowyniku.setSaldoMa(Math.abs(wynikkwota));
        }
    }
    
    private void pobierzPozycjeAktywaPasywa(List<PozycjaRZiSBilans> pozycjeaktywa, List<PozycjaRZiSBilans> pozycjepasywa) {
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

//    private void naniesBOnaKonto(Konto p) {
//        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(p, wierszBODAO, wpisView);
//        for (StronaWiersza r : zapisyBO) {
//            if (r.getWnma().equals("Wn")) {
//                p.setBoWn(Z.z(p.getBoWn() + r.getKwotaPLN()));
//            } else {
//                p.setBoMa(Z.z(p.getBoMa() + r.getKwotaPLN()));
//            }
//        }
//    }
//    
   
   

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
                nowyelementRZiS.setMacierzysta(null);
            } else {
                String poprzednialitera = ((PozycjaRZiS) ((TreeNode)rootProjektRZiS.getChildren().get(rootProjektRZiS.getChildCount() - 1)).getData()).getPozycjaSymbol();
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
                PozycjaRZiS lastchild = (PozycjaRZiS) ((TreeNode)wybranynodekonta.getChildren().get(index)).getData();
                nastepnysymbol = PozycjaRZiSFKBean.zwrocNastepnySymbol(level + 1, lastchild.getPozycjaSymbol());
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
                nowyelementBilans.setMacierzysta(null);
            } else {
                String poprzednialitera = ((PozycjaBilans) ((TreeNode)rootProjektRZiS.getChildren().get(rootProjektRZiS.getChildCount() - 1)).getData()).getPozycjaSymbol();
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
                PozycjaBilans lastchild = (PozycjaBilans) ((TreeNode)wybranynodekonta.getChildren().get(index)).getData();
                nastepnysymbol = PozycjaRZiSFKBean.zwrocNastepnySymbol(level + 1, lastchild.getPozycjaSymbol());
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
   
    public void usunpozycje() {
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
            Msg.msg("i", "Usuwam w RZiS");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie udało się usunąć pozycji w RZiS");
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
        if (selectedNodes != null && selectedNodes.length > 0) {
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
    }
    
    public void wyluskajStronyzPozycjiBilans() {
        podpieteStronyWiersza = Collections.synchronizedList(new ArrayList<>());
        sumaPodpietychKont = Collections.synchronizedList(new ArrayList<>());
        List<Konto> podpieteKonta = Collections.synchronizedList(new ArrayList<>());
        if (selectedNodes != null && selectedNodes.length > 0) {
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
            List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikRokBilans(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            int granicagorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacNastepny()) == 1 ? 13 : Mce.getMiesiacToNumber().get(wpisView.getMiesiacNastepny());
            for (Konto p : sumaPodpietychKont) {
                for (Iterator<StronaWiersza> it = stronywiersza.iterator(); it.hasNext(); ) {   
                    StronaWiersza r = (StronaWiersza) it.next();
                    if (Mce.getMiesiacToNumber().get(r.getDokfk().getMiesiac()) < granicagorna && r.getKonto().equals(p)) {
                        podpieteStronyWiersza.add(r);
                    }
                }
            }
        }
    }
    
    public void odswiezrzis() {
        wpisView.wpisAktualizuj();
        pobierzukladprzegladRZiSWybierz();
    }
    
    public void odswiezrzisBO() {
        wpisView.wpisAktualizuj();
        obliczRZiSOtwarciaRZiSData();
    }
    
    public void odswiezbilans() {
        wpisView.wpisAktualizuj();
        pobierzukladprzegladBilans("aktywa");
    }
    
    public void drukujRZiS() {
        PdfRZiS.drukujRZiS(rootProjektRZiS, wpisView, bilansoddnia, bilansnadzien, laczlata);
    }
    
    public void drukujRZiSBO() {
        PdfRZiS.drukujRZiSBO(rootProjektRZiS, wpisView, bilansnadzien, bilansoddnia);
    }

    public String getKontabilansowebezprzyporzadkowania() {
        return kontabilansowebezprzyporzadkowania;
    }

    public void setKontabilansowebezprzyporzadkowania(String kontabilansowebezprzyporzadkowania) {
        this.kontabilansowebezprzyporzadkowania = kontabilansowebezprzyporzadkowania;
    }

    public boolean isLaczlata() {
        return laczlata;
    }

    public void setLaczlata(boolean laczlata) {
        this.laczlata = laczlata;
    }
    
    
    public void drukujRZiSPozycje() {
        PdfRZiS.drukujRZiSPozycje(rootProjektRZiS, wpisView);
    }
    
    public void drukujBilans(String ap) {
        if (ap.equals("a")) {
            PdfBilans.drukujBilans(rootBilansAktywa, wpisView, ap, 0);
        } else {
            PdfBilans.drukujBilans(rootBilansPasywa, wpisView, ap, 0);
        }
    }
    public void drukujBilansPozycje(String ap) {
        if (ap.equals("a")) {
            PdfBilans.drukujBilansBOPozycje(rootBilansAktywa, wpisView, ap, 0, false);
        } else {
            PdfBilans.drukujBilansBOPozycje(rootBilansPasywa, wpisView, ap, 0, false);
        }
    }
    
    public void drukujBilansKonta(String ap) {
        if (ap.equals("a")) {
            PdfBilans.drukujBilansKonta(rootBilansAktywa, wpisView, ap, 0, false);
        } else {
            PdfBilans.drukujBilansKonta(rootBilansPasywa, wpisView, ap, 0, false);
        }
    }
   
       
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    

    public List<StronaWiersza> getPodpieteStronyWiersza() {
        return podpieteStronyWiersza;
    }

    public void setPodpieteStronyWiersza(List<StronaWiersza> podpieteStronyWiersza) {
        this.podpieteStronyWiersza = podpieteStronyWiersza;
    }

    public String getBilansnadzien() {
        return bilansnadzien;
    }

    public void setBilansnadzien(String bilansnadzien) {
        this.bilansnadzien = bilansnadzien;
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

    public String getBilansoddnia() {
        return bilansoddnia;
    }

    public void setBilansoddnia(String bilansoddnia) {
        this.bilansoddnia = bilansoddnia;
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

    public TreeNodeExtended getRootBilans() {
        return rootBilans;
    }

    public void setRootBilans(TreeNodeExtended rootBilans) {
        this.rootBilans = rootBilans;
    }

    public TreeNodeExtended getRootProjektKonta() {
        return rootProjektKonta;
    }

    public void setRootProjektKonta(TreeNodeExtended rootProjektKonta) {
        this.rootProjektKonta = rootProjektKonta;
    }
    

   
    
    //</editor-fold>

}

