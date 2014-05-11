/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import abstractClasses.ToBeATreeNodeObject;
import beansFK.KontaFKBean;
import beansFK.PlanKontFKBean;
import dao.DokDAO;
import dao.PodatnikDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import msg.Msg;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import view.WpisView;

/**
 *
 * @author Osito
 */

@ViewScoped
@ManagedBean
public class PlanKontView implements Serializable {
    private static List<Konto> wykazkontS;
    private static int levelBiezacy = 0;
    private static int levelMaksymalny = 0;

    public static List<Konto> getWykazkontS() {
        return wykazkontS;
    }
    private List<Konto> wykazkont;
    private List<Konto> wykazkontwzor;
    private List<Konto> wykazkontanalityczne;
    @Inject
    private Konto selected;
    @Inject
    private Konto nowe;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private KliencifkDAO kliencifkDAO;
    private TreeNodeExtended<Konto> root;
    private TreeNodeExtended<Konto> rootwzorcowy;
    private TreeNodeExtended<Konto> selectednode;
    private String wewy;
    private String listajs;
    private boolean czyoddacdowzorca;
    @Inject
    private PodatnikDAO podatnikDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;


    public PlanKontView() {
    }

    @PostConstruct
    private void init() {
        wykazkont = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
        wykazkontS = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
        root = rootInit(wykazkont);
        rozwinwszystkie(root);
        wykazkontwzor = kontoDAO.findKontoPodatnik("Testowy");
        rootwzorcowy = rootInit(wykazkontwzor);
    }
    //tworzy nody z bazy danych dla tablicy nodow plan kont

    private TreeNodeExtended<Konto> rootInit(List<Konto> wykaz) {
        TreeNodeExtended<Konto> r = new TreeNodeExtended("root", null);
        if (!wykaz.isEmpty()) {
            r.createTreeNodesForElement(wykaz);
        }
        return r;
    }

    private int ustalLevel(TreeNodeExtended<Konto> r) {
        int level = 0;
        if (czywzorcowe(r.getChildren().get(0))) {
            level = r.ustaldepthDT(wykazkontwzor);
        } else {
            level = r.ustaldepthDT(wykazkont);
        }
        levelMaksymalny = level;
       return level;
    }
    
    private boolean czywzorcowe(TreeNode nodeR) {
        Konto konto = (Konto) nodeR.getData();
        return konto.getPodatnik().equals("Testowy");      
    }

    public void rozwinwszystkie(TreeNodeExtended<Konto> r) {
        if (r.getChildCount()>0) {
            levelBiezacy = ustalLevel(r);
            r.expandAll();
        }
    }

    public void rozwin(TreeNodeExtended<Konto> r) {
        int maxpoziom = ustalLevel(r);
        if (levelBiezacy < --maxpoziom) {
            r.expandLevel(levelBiezacy++);
        } else {
            Msg.msg("Osiągnięto maksymalny poziom szczegółowości analityki");
        }
    }

    public void zwinwszystkie(TreeNodeExtended<Konto> r) {
        r.foldAll();
        levelBiezacy = 0;
    }

    public void zwin(TreeNodeExtended<Konto> r) {
        if (levelBiezacy != 0) {
            r.foldLevel(--levelBiezacy);
        } else {
            Msg.msg("Wyświetlane są tylko konta syntetyczne");
        }
    }

    public void dodajdowzorca() {
        czyoddacdowzorca = true;
    }
    
    public void dodaj() {
        TreeNodeExtended<Konto> r;
        String podatnik;
        if (czyoddacdowzorca == true) {
            r = rootwzorcowy;
            podatnik = "Testowy";
        } else {
            r = root;
            podatnik = wpisView.getPodatnikWpisu();
        }
        Konto kontomacierzyste = (Konto) selectednode.getData();
        if (nowe.getBilansowewynikowe() != null) {
            int wynikdodaniakonta = PlanKontFKBean.dodajsyntetyczne(nowe, kontomacierzyste, kontoDAO, podatnik);
            if (wynikdodaniakonta == 0) {
                nowe = new Konto();
                odswiezroot(r);
                Msg.msg("i", "Dodano konto syntetyczne", "formX:messages");
            } else {
                nowe = new Konto();
                Msg.msg("e", "Konto syntetyczne o takim numerze juz istnieje!", "formX:messages");
            }
        } else {
            //jezeli to nie slownik to wyrzuca blad i dodaje analityke
            try {
                if (nowe.getNrkonta().equals("kontr")) {
                    if (kontomacierzyste.isMapotomkow()==true) {
                     Msg.msg("e","Konto już ma analitykę, nie można dodać słownika");
                    } else {
                        int wynikdodaniakonta = PlanKontFKBean.dodajslownik(nowe, kontomacierzyste, kontoDAO, podatnik);
                        if (wynikdodaniakonta == 0) {
                            kontomacierzyste.setBlokada(true);
                            kontomacierzyste.setMapotomkow(true);
                            kontomacierzyste.setMaslownik(true);
                            kontoDAO.edit(kontomacierzyste);
                            Msg.msg("i", "Dodaje słownik", "formX:messages");
                        } else {
                            nowe = new Konto();
                            Msg.msg("e", "Nie można dodać słownika!", "formX:messages");
                            return;
                        }
                        wynikdodaniakonta = PlanKontFKBean.dodajelementyslownika(kontomacierzyste, kontoDAO, kliencifkDAO, wpisView.getPodatnikObiekt());
                        if (wynikdodaniakonta == 0) {
                            nowe = new Konto();
                            odswiezroot(r);
                            Msg.msg("Dodano elementy słownika");
                        } else {
                            Msg.msg("e", "Wystąpił błąd przy dodawani elementów słownika");
                        }
                    }
                }
            } catch (Exception e) {
                if (kontomacierzyste.isBlokada() == false) {
                    int wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(nowe, kontomacierzyste, kontoDAO, podatnik);
                    if (wynikdodaniakonta == 0) {
                        kontomacierzyste.setMapotomkow(true);
                        kontoDAO.edit(kontomacierzyste);
                        nowe = new Konto();
                        odswiezroot(r);
                        Msg.msg("i", "Dodaje konto analityczne", "formX:messages");
                    } else {
                        nowe = new Konto();
                        Msg.msg("e", "Konto analityczne o takim numerze juz istnieje!", "formX:messages");
                    }
                } else {
                    Msg.msg("w", "Nie można dodawać kont analitycznych. Istnieją zapisy z BO");
                }
            }
        }
    }
    
    public void implementacjaKontWzorcowych() {
        if (!wykazkontwzor.isEmpty()) {
            for (Konto p : wykazkontwzor) {
                p.setPodatnik(wpisView.getPodatnikWpisu());
                try {
                    kontoDAO.dodaj(p);
                } catch (PersistenceException e) {
                    Msg.msg("e","Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: "+p.getPelnynumer());
                } catch (Exception ef) {
                    Msg.msg("e","Wystąpił błąd podczas dodawania konta. Nie dodano: "+p.getPelnynumer());
                }
            }
            List<Konto> wykazkonttmp = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
            //a teraz trzeba pozmieniac id macierzystych bo inaczej sie nie odnajda
            for (Konto p : wykazkonttmp) {
                if (!p.getMacierzyste().equals("0")) {
                    Konto macierzyste = kontoDAO.findKonto(p.getMacierzyste(), wpisView.getPodatnikWpisu());
                    p.setMacierzysty(macierzyste.getId());
                    kontoDAO.edit(p);
                }
            }
            wykazkont = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
            wykazkontS = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
            root = rootInit(wykazkont);
            rozwinwszystkie(root);
            Msg.msg("Zakonczono z sukcesem implementacje kont wzorcowych u bieżącego podatnika");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont wzorcowych jest pusta.");
        }
    }
    
    public void implementacjaJednegoKontaWzorcowego() {
        if (selectednode != null) {
            List<Podatnik> listapodatnikowfk = podatnikDAO.findPodatnikFK();
            for (Podatnik p : listapodatnikowfk) {
                Konto konto = ((Konto) selectednode.getData());
                konto.setPodatnik(p.getNazwapelna());
                if (!konto.getMacierzyste().equals("0")) {
                    Konto macierzyste = kontoDAO.findKonto(konto.getMacierzyste(), p.getNazwapelna());
                    konto.setMacierzysty(macierzyste.getId());
                }
                try {
                    kontoDAO.dodaj(konto);
                } catch (PersistenceException e) {
                    Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + konto.getPelnynumer());
                } catch (Exception ef) {
                    Msg.msg("e", "Wystąpił błąd podczas dodawania konta. "+ef.getMessage()+" Nie dodano: " + konto.getPelnynumer());
                }
            }
            wykazkont = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
            wykazkontS = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
            root = rootInit(wykazkont);
            rozwinwszystkie(root);
            Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta wzorcowego u wszystkich klientów FK");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont wzorcowych jest pusta.");
        }
    }
    
    public void usunieciewszystkichKontPodatnika() {
        if (!wykazkont.isEmpty()) {
            for (Konto p : wykazkont) {
                if (!p.getPodatnik().equals("Testowy")) {
                    try {
                        kontoDAO.destroy(p);
                    } catch (Exception e) {
                        Msg.msg("e","Wystąpił błąd przy usuwaniu wszytskich kont. Przerywam wykonywanie funkcji");
                    }
                } else {
                    Msg.msg("e","Próbujesz usunąć konta wzorcowe. Przerywam działanie.");
                    return; 
                }
            }
            wykazkont = new ArrayList<>();
            wykazkontS = new ArrayList<>();
            root =  new TreeNodeExtended("root", null);
            Msg.msg("Zakonczono z sukcesem usuwanie kont u bieżącego podatnika");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do usuniecia jest pusta.");
        }
    }
    
    public void porzadkowanieKontPodatnika(){
        wykazkont = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
        //resetuj kolumne macierzyste
        KontaFKBean.czyszczenieKont(wykazkont, kontoDAO, wpisView.getPodatnikWpisu());
        wykazkont = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
        wykazkontS = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
        root = rootInit(wykazkont);
        rozwinwszystkie(root);
    }
    
     public void porzadkowanieKontWzorcowych(){
        wykazkontwzor = kontoDAO.findKontoPodatnik("Testowy");
        //resetuj kolumne macierzyste
        KontaFKBean.czyszczenieKont(wykazkontwzor, kontoDAO, "Testowy");
        wykazkontwzor = kontoDAO.findKontoPodatnik("Testowy");
        rootwzorcowy = rootInit(wykazkontwzor);
        rozwinwszystkie(rootwzorcowy);
    }

    private void odswiezroot(TreeNodeExtended<Konto> r) {
        if (czywzorcowe(r.getChildren().get(0))) {
            r.reset();
            r.createTreeNodesForElement(kontoDAO.findKontoPodatnik("Testowy"));
        } else {
            r.reset();
            r.createTreeNodesForElement(kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu()));
        }
        r.expandAll();
    }

    
    private void obliczpelnynumerkonta() {
        if (nowe.getLevel() == 0) {
            nowe.setPelnynumer(nowe.getNrkonta());
        } else {
            nowe.setPelnynumer(nowe.getMacierzyste() + "-" + nowe.getNrkonta());
        }
    }

    public void usun(TreeNodeExtended<Konto> r) {
        String podatnik;
        if (czyoddacdowzorca == true) {
            podatnik = "Testowy";
        } else {
            podatnik = wpisView.getPodatnikWpisu();
        }
        if (selectednode != null) {
            Konto zawartosc = (Konto) selectednode.getData();
            if (zawartosc.isBlokada() == true) {
                Msg.msg("e", "Na koncie istnieją zapisy. Nie można go usunąć");
            } else if (zawartosc.isMapotomkow() == true) {
                Msg.msg("e", "Konto ma analitykę, nie można go usunąć.", "formX:messages");
            } else {
                kontoDAO.destroy(selectednode.getData());
                if (zawartosc.getNazwapelna().equals("Słownik kontrahenci")) {
                    int wynik = PlanKontFKBean.usunelementyslownika(zawartosc.getMacierzyste(), kontoDAO, podatnik);
                    if (wynik == 0) {
                        Konto kontomacierzyste = kontoDAO.findKonto(zawartosc.getMacierzysty());
                        kontomacierzyste.setBlokada(false);
                        kontomacierzyste.setMapotomkow(false);
                        kontomacierzyste.setMaslownik(false);
                        kontoDAO.edit(kontomacierzyste);
                        Msg.msg("Usunięto elementy słownika");
                    } else {
                        Msg.msg("e", "Wystapił błąd i nie usunięto elementów słownika");
                    }
                } else {
                    boolean sadzieci = sprawdzczymacierzystymapotomne(zawartosc, r);
                    if (!sadzieci) {
                        Konto kontomacierzyste = kontoDAO.findKonto(zawartosc.getMacierzysty());
                        kontomacierzyste.setBlokada(false);
                        kontomacierzyste.setMapotomkow(false);
                        kontoDAO.edit(kontomacierzyste);
                    }
                }
                odswiezroot(r);
                Msg.msg("i", "Usuwam konto", "formX:messages");
            }
        } else {
            Msg.msg("e", "Nie wybrano konta", "formX:messages");
        }
    }

    private boolean sprawdzczymacierzystymapotomne(Konto konto, TreeNodeExtended<Konto> r) {
        int macierzyste = konto.getMacierzysty();
        List<Object> finallChildrenData = new ArrayList<>();
        r.getFinallChildrenData(new ArrayList<TreeNodeExtended>(), finallChildrenData);
        finallChildrenData.remove(konto);
        for (Object p : finallChildrenData) {
            if (((ToBeATreeNodeObject) p).getMacierzysty() == macierzyste) {
                return true;
            }
        }
        return false;
    }

    public void zablokujkonto() {
        if (selectednode != null) {
            Konto konto = (Konto) selectednode.getData();
            if (konto.isBlokada() == false) {
                konto.setBlokada(true);
                kontoDAO.edit(konto);
                Msg.msg("w", "Zabezpieczono konto przed edycją.");
            } else if (konto.getBoWn() == 0.0 && konto.getBoMa() == 0.0 && konto.isBlokada() == true) {
                konto.setBlokada(false);
                kontoDAO.edit(konto);
                Msg.msg("w", "Odblokowano edycję konta.");
            }
        } else {
            Msg.msg("f", "Nie wybrano konta", "formX:messages");
        }
    }

    public List<Konto> complete(String qr) {
        String query = qr.split(" ")[0];
        List<Konto> results = new ArrayList<>();
        List<Konto> listakont = kontoDAO.findKontaOstAlityka(wpisView.getPodatnikWpisu());
        if (listakont != null) {
            try {
                String q = query.substring(0, 1);
                int i = Integer.parseInt(q);
                for (Konto p : listakont) {
                    if (query.length() == 4 && !query.contains("-")) {
                        //wstawia - do ciagu konta
                        query = query.substring(0, 3) + "-" + query.substring(3, 4);
                    }
                    if (p.getPelnynumer().startsWith(query)) {
                        results.add(p);
                    }
                }
            } catch (NumberFormatException e) {
                for (Konto p : listakont) {
                    if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                        results.add(p);
                    }
                }
            }
        }
        return results;
    }
    
    

    public void selrow(NodeSelectEvent e) {
        TreeNode p = e.getTreeNode();
        Konto zawartosc = (Konto) p.getData();
        Msg.msg("i", "Wybrano: " + zawartosc.getPelnynumer() + " " + zawartosc.getNazwapelna());
    }

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public Konto getSelected() {
        return selected;
    }

    public void setSelected(Konto selected) {
        this.selected = selected;
    }

    public Konto getNowe() {
        return nowe;
    }

    public void setNowe(Konto nowe) {
        this.nowe = nowe;
    }

    public String getWewy() {
        return wewy;
    }

    public void setWewy(String wewy) {
        this.wewy = wewy;
    }

    public TreeNodeExtended<Konto> getSelectednode() {
        return selectednode;
    }

    public void setSelectednode(TreeNodeExtended<Konto> selectednode) {
        this.selectednode = selectednode;
    }

//   static{
    public String getListajs() {
        return "jeden,dwa,trzy,cztery,piec,szesc,siedem,osiem,dziewiec,dziesiec";
    }
    public TreeNodeExtended getRoot() {
        return root;
    }

   
    public void setRoot(TreeNodeExtended root) {
        this.root = root;
    }

    public TreeNodeExtended<Konto> getRootwzorcowy() {
        return rootwzorcowy;
    }

    public void setRootwzorcowy(TreeNodeExtended<Konto> rootwzorcowy) {
        this.rootwzorcowy = rootwzorcowy;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
   


    
}
