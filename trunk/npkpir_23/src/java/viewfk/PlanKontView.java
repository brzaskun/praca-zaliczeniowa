/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.KontaFKBean;
import beansFK.PlanKontFKBean;
import dao.PodatnikDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    private static final long serialVersionUID = 1L;

    private int levelBiezacy = 0;
    private int levelMaksymalny = 0;
    private List<Konto> wykazkont;
    private List<Konto> wykazkontwzor;
    private List<Konto> wykazkontanalityczne;
    private List<Konto> listakontOstatniaAnalitykaklienta;
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
        wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu());
        root = rootInit(wykazkont);
        rozwinwszystkie(root);
        wykazkontwzor = kontoDAO.findWszystkieKontaPodatnika("Wzorcowy");
        rootwzorcowy = rootInit(wykazkontwzor);
        listakontOstatniaAnalitykaklienta = kontoDAO.findKontaOstAlityka(wpisView.getPodatnikWpisu());
    }
    //tworzy nody z bazy danych dla tablicy nodow plan kont

    private TreeNodeExtended<Konto> rootInit(List<Konto> wykazKont) {
        Iterator it = wykazKont.iterator();
        while (it.hasNext()) {
            Konto konto = (Konto) it.next();
            if (konto.getNrkonta().equals("0")) {
                it.remove();
            }
        }
        TreeNodeExtended<Konto> r = new TreeNodeExtended("root", null);
        if (!wykazKont.isEmpty()) {
            r.createTreeNodesForElement(wykazKont);
        }
        return r;
    }

    //zostawilem bo duzo zmiennych malo linijek
    private int ustalLevel(TreeNodeExtended<Konto> r) {
        int level = 0;
        if (PlanKontFKBean.czywzorcowe(r.getChildren().get(0))) {
            level = r.ustaldepthDT(wykazkontwzor);
        } else {
            level = r.ustaldepthDT(wykazkont);
        }
        levelMaksymalny = level;
        return level;
    }

    public void rozwinwszystkie(TreeNodeExtended<Konto> r) {
        if (r.getChildCount() > 0) {
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
                PlanKontFKBean.odswiezroot(r, kontoDAO, podatnik);
                Msg.msg("i", "Dodano konto syntetyczne", "formX:messages");
            } else {
                nowe = new Konto();
                Msg.msg("e", "Konto syntetyczne o takim numerze juz istnieje!", "formX:messages");
            }
        } else {
            //jezeli to nie slownik to wyrzuca blad i dodaje analityke
            try {
                if (nowe.getNrkonta().equals("kontr")) {
                    if (kontomacierzyste.isMapotomkow() == true) {
                        Msg.msg("e", "Konto już ma analitykę, nie można dodać słownika");
                    } else {
                        int wynikdodaniakonta = PlanKontFKBean.dodajslownik(nowe, kontomacierzyste, kontoDAO, podatnik);
                        if (wynikdodaniakonta == 0) {
                            PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAO);
                            Msg.msg("i", "Dodaje słownik", "formX:messages");
                        } else {
                            nowe = new Konto();
                            Msg.msg("e", "Nie można dodać słownika!", "formX:messages");
                            return;
                        }
                        wynikdodaniakonta = PlanKontFKBean.dodajelementyslownika(kontomacierzyste, kontoDAO, kliencifkDAO, wpisView.getPodatnikObiekt());
                        if (wynikdodaniakonta == 0) {
                            nowe = new Konto();
                            PlanKontFKBean.odswiezroot(r, kontoDAO, podatnik);
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
                        PlanKontFKBean.zablokujKontoMacierzysteNieSlownik(kontomacierzyste, kontoDAO);
                        nowe = new Konto();
                        PlanKontFKBean.odswiezroot(r, kontoDAO, podatnik);
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
                    Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + p.getPelnynumer());
                } catch (Exception ef) {
                    Msg.msg("e", "Wystąpił błąd podczas dodawania konta. Nie dodano: " + p.getPelnynumer());
                }
            }
            List<Konto> wykazkonttmp = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu());
            //a teraz trzeba pozmieniac id macierzystych bo inaczej sie nie odnajda
            for (Konto p : wykazkonttmp) {
                if (!p.getMacierzyste().equals("0")) {
                    Konto macierzyste = kontoDAO.findKonto(p.getMacierzyste(), wpisView.getPodatnikWpisu());
                    p.setMacierzysty(macierzyste.getId());
                    kontoDAO.edit(p);
                }
            }
            wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu());
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
                    Msg.msg("e", "Wystąpił błąd podczas dodawania konta. " + ef.getMessage() + " Nie dodano: " + konto.getPelnynumer());
                }
            }
            wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu());
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
                        Msg.msg("e", "Wystąpił błąd przy usuwaniu wszytskich kont. Przerywam wykonywanie funkcji");
                    }
                } else {
                    Msg.msg("e", "Próbujesz usunąć konta wzorcowe. Przerywam działanie.");
                    return;
                }
            }
            wykazkont = new ArrayList<>();
            root = new TreeNodeExtended("root", null);
            Msg.msg("Zakonczono z sukcesem usuwanie kont u bieżącego podatnika");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do usuniecia jest pusta.");
        }
    }

    public void porzadkowanieKontPodatnika() {
        wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu());
        //resetuj kolumne macierzyste
        KontaFKBean.czyszczenieKont(wykazkont, kontoDAO, wpisView.getPodatnikWpisu());
        wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu());
        root = rootInit(wykazkont);
        rozwinwszystkie(root);
    }

    public void porzadkowanieKontWzorcowych() {
        wykazkontwzor = kontoDAO.findWszystkieKontaPodatnika("Testowy");
        //resetuj kolumne macierzyste
        KontaFKBean.czyszczenieKont(wykazkontwzor, kontoDAO, "Testowy");
        wykazkontwzor = kontoDAO.findWszystkieKontaPodatnika("Testowy");
        rootwzorcowy = rootInit(wykazkontwzor);
        rozwinwszystkie(rootwzorcowy);
    }

    public void usun(TreeNodeExtended<Konto> rootZNodem) {
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
                try {
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
                        boolean sadzieci = PlanKontFKBean.sprawdzczymacierzystymapotomne(podatnik, zawartosc, kontoDAO);
                        if (!sadzieci) {
                            Konto kontomacierzyste = kontoDAO.findKonto(zawartosc.getMacierzysty());
                            kontomacierzyste.setBlokada(false);
                            kontomacierzyste.setMapotomkow(false);
                            kontoDAO.edit(kontomacierzyste);
                        }
                    }
                    PlanKontFKBean.odswiezroot(rootZNodem, kontoDAO, podatnik);
                    Msg.msg("i", "Usuwam konto", "formX:messages");
                } catch (Exception e) {
                    Msg.msg("e", "Istnieją zapisy na koncie, nie można go usunąć.");
                }
            }
        } else {
            Msg.msg("e", "Nie wybrano konta", "formX:messages");
        }
    }

    public void obslugaBlokadyKonta() {
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
        if (qr != null) {
            String query = qr.split(" ")[0];
            List<Konto> results = new ArrayList<>();
            if (listakontOstatniaAnalitykaklienta != null) {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Konto p : listakontOstatniaAnalitykaklienta) {
                        if (query.length() == 4 && !query.contains("-")) {
                            //wstawia - do ciagu konta
                            query = query.substring(0, 3) + "-" + query.substring(3, 4);
                        }
                        if (p.getPelnynumer().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Konto p : listakontOstatniaAnalitykaklienta) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
            }
            return results;
        }
        return null;
    }

    public void selrow(NodeSelectEvent e) {
        TreeNode p = e.getTreeNode();
        Konto zawartosc = (Konto) p.getData();
        Msg.msg("i", "Wybrano: " + zawartosc.getPelnynumer() + " " + zawartosc.getNazwapelna());
    }
    
    public void zachowajZmianyWKoncieWzorcowy(Konto konto) {
        kontoDAO.edit(konto);
        List<Konto> kontapotomne = kontoDAO.findKontaWszystkiePotomnePodatnik("Wzorcowy", konto);
        for (Konto p : kontapotomne) {
            p.setZwyklerozrachszczegolne(konto.getZwyklerozrachszczegolne());
            kontoDAO.edit(p);
        }
        wykazkontwzor = kontoDAO.findWszystkieKontaPodatnika("Wzorcowy");
        rootwzorcowy = rootInit(wykazkontwzor);
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
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
//</editor-fold>

}
