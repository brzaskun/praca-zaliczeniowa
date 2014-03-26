/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import abstractClasses.ToBeATreeNodeObject;
import beans.PlanKontBean;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Osito
 */

@ViewScoped
@Named("planKontView")
public class PlanKontView implements Serializable {
    private static List<Konto> wykazkontS;
    private static int level = 0;

    public static List<Konto> getWykazkontS() {
        return wykazkontS;
    }

    private List<Konto> wykazkont;
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
    private TreeNodeExtended<Konto> selectednode;
    private String wewy;
    private String listajs;

    public PlanKontView() {
        this.wykazkont = new ArrayList<>();
        this.root = new TreeNodeExtended("root", null);
    }

    @PostConstruct
    private void init() {
        rozwinwszystkie();
        wykazkont = kontoDAO.findAll();
        wykazkontS = kontoDAO.findAll();
    }
    //tworzy nody z bazy danych dla tablicy nodow plan kont

    private void getNodes() {
        this.root = new TreeNodeExtended("root", null);
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findAll());
        root.createTreeNodesForElement(kontadlanodes);
    }

    public void rozwinwszystkie() {
        getNodes();
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findAll());
        level = root.ustaldepthDT(kontadlanodes) - 1;
        root.expandAll();
    }

    public void rozwin() {
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findAll());
        int maxpoziom = root.ustaldepthDT(kontadlanodes);
        if (level < --maxpoziom) {
            root.expandLevel(level++);
        }
    }

    public void zwinwszystkie() {
        getNodes();
        root.foldAll();
        level = 0;
    }

    public void zwin() {
        root.foldLevel(--level);
    }

    public void dodaj() {
        Konto kontomacierzyste = (Konto) selectednode.getData();
        if (nowe.getBilansowewynikowe() != null) {
            int wynikdodaniakonta = PlanKontBean.dodajsyntetyczne(nowe, kontomacierzyste, kontoDAO);
            if (wynikdodaniakonta == 0) {
                nowe = new Konto();
                odswiezroot();
                Msg.msg("i", "Dodano konto syntetyczne", "formX:messages");
            } else {
                nowe = new Konto();
                Msg.msg("e", "Konto syntetyczne o takim numerze juz istnieje!", "formX:messages");
                return;
            }
        } else {
            //jezeli to nie slownik to wyrzuca blad i dodaje analityke
            try {
                if (nowe.getNrkonta().equals("kontr")) {
                    if (kontomacierzyste.isMapotomkow()==true) {
                     Msg.msg("e","Konto już ma analitykę, nie można dodać słownika");
                     return;
                    } else {
                        int wynikdodaniakonta = PlanKontBean.dodajslownik(nowe, kontomacierzyste, kontoDAO);
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
                        wynikdodaniakonta = PlanKontBean.dodajelementyslownika(kontomacierzyste, kontoDAO, kliencifkDAO);
                        if (wynikdodaniakonta == 0) {
                            nowe = new Konto();
                            odswiezroot();
                            Msg.msg("Dodano elementy słownika");
                        } else {
                            Msg.msg("e", "Wystąpił błąd przy dodawani elementów słownika");
                        }
                    }
                }
            } catch (Exception e) {
                if (kontomacierzyste.isBlokada() == false) {
                    int wynikdodaniakonta = PlanKontBean.dodajanalityczne(nowe, kontomacierzyste, kontoDAO);
                    if (wynikdodaniakonta == 0) {
                        kontomacierzyste.setMapotomkow(true);
                        kontoDAO.edit(kontomacierzyste);
                        nowe = new Konto();
                        odswiezroot();
                        Msg.msg("i", "Dodaje konto analityczne", "formX:messages");
                    } else {
                        nowe = new Konto();
                        Msg.msg("e", "Konto analityczne o takim numerze juz istnieje!", "formX:messages");
                    }
                } else {
                    Msg.msg("w", "Nie można dodawać kont analitycznych. Istnieją zapisy z BO");
                    return;
                }
            }
        }
    }
    
    @RolesAllowed("BookkeeperFK")
    public void mesyd() {
        Msg.msg("lolololo");
    }

    private void odswiezroot() {
        ArrayList<Konto> kontadlanodes = new ArrayList<>();
        kontadlanodes.addAll(kontoDAO.findAll());
        root.reset();
        root.createTreeNodesForElement(kontadlanodes);
        root.expandAll();
    }

    private int znajdzduplikat() {
        if (wykazkont.contains(nowe)) {
            return 1;
        } else {
            return 0;
        }
    }

    private void obliczpelnynumerkonta() {
        if (nowe.getLevel() == 0) {
            nowe.setPelnynumer(nowe.getNrkonta());
        } else {
            nowe.setPelnynumer(nowe.getMacierzyste() + "-" + nowe.getNrkonta());
        }
    }

    public void usun() {
        if (selectednode != null) {
            Konto zawartosc = (Konto) selectednode.getData();
            if (zawartosc.isBlokada() == true) {
                Msg.msg("e", "Na koncie istnieją zapisy. Nie można go usunąć");
                return;
            } else if (zawartosc.isMapotomkow() == true) {
                Msg.msg("e", "Konto ma analitykę, nie można go usunąć.", "formX:messages");
            } else {
                kontoDAO.destroy(selectednode.getData());
                if (zawartosc.getNazwapelna().equals("Słownik kontrahenci")) {
                    int wynik = PlanKontBean.usunelementyslownika(zawartosc.getMacierzyste(), kontoDAO);
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
                    boolean sadzieci = sprawdzczymacierzystymapotomne(zawartosc);
                    if (!sadzieci) {
                        Konto kontomacierzyste = kontoDAO.findKonto(zawartosc.getMacierzysty());
                        kontomacierzyste.setBlokada(false);
                        kontomacierzyste.setMapotomkow(false);
                        kontoDAO.edit(kontomacierzyste);
                    }
                }
                odswiezroot();
                Msg.msg("i", "Usuwam konto", "formX:messages");
            }
        } else {
            Msg.msg("e", "Nie wybrano konta", "formX:messages");
        }
    }

    private boolean sprawdzczymacierzystymapotomne(Konto konto) {
        int macierzyste = konto.getMacierzysty();
        List<Object> finallChildrenData = new ArrayList<>();
        root.getFinallChildrenData(new ArrayList<TreeNodeExtended>(), finallChildrenData);
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
                return;
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
        List<Konto> listakont = kontoDAO.findKontaOstAlityka();
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
        } catch (Exception e) {
            for (Konto p : listakont) {
                if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                    results.add(p);
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


}
