/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import msg.Msg;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PlanKontView implements Serializable {

    private List<Konto> wykazkont;
    private List<Konto> wykazkontanalityczne;
    private static List<Konto> wykazkontS;
    private static String opiskonta;
    private static String pelnynumerkonta;
    @Inject
    private Konto selected;
    @Inject
    private Konto nowe;
    @Inject
    private KontoDAOfk kontoDAO;
    private TreeNodeExtended<Konto> root;
    private TreeNodeExtended<Konto> selectednode;

    public PlanKontView() {
        this.wykazkont = new ArrayList<>();
        this.root = new TreeNodeExtended("root", null);
    }

    @PostConstruct
    private void init() {
        rozwinwszystkie();
        wykazkont = kontoDAO.findAll();
        wykazkontS = kontoDAO.findAll();
        opiskonta = "";
        pelnynumerkonta = "";
        for (Konto t : wykazkontS) {
            opiskonta = opiskonta + t.getNazwaskrocona() + ",";
            pelnynumerkonta = pelnynumerkonta + t.getPelnynumer() + ",";
        }
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
    private static int level = 0;

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
        Konto selected = (Konto) selectednode.getData();
        if (nowe.getBilansowewynikowe() != null) {
            nowe.setSyntetyczne("syntetyczne");
        } else {
            if (selected.isBlokada() == false) {
                ArrayList<Konto> lista = new ArrayList<>();
                wykazkont = kontoDAO.findAll();
                for (Konto p : wykazkont) {
                    if (p.getMacierzyste().equals(selected.getPelnynumer())) {
                        lista.add(p);
                    }
                }
                if (lista.size() > 0) {
                    nowe.setNrkonta(String.valueOf(Integer.parseInt(lista.get(lista.size() - 1).getNrkonta()) + 1));
                } else {
                    nowe.setNrkonta("1");
                }
                nowe.setSyntetyczne("analityczne");
                nowe.setBilansowewynikowe(selected.getBilansowewynikowe());
                nowe.setZwyklerozrachszczegolne(selected.getZwyklerozrachszczegolne());
            } else {
                Msg.msg("w", "Nie można dodawać kont analitycznych. Istnieją zapisy z BO");
                return;
            }
        }
        System.out.println("Dodaje konto");
        nowe.setPodatnik("Testowy");
        nowe.setRok(2014);
        //dla syntetycznego informacja jest pusta a dla analitycznego bierzekonto
        if (nowe.getSyntetyczne().equals("syntetyczne")) {
            nowe.setMacierzyste("0");
        } else {
            nowe.setMacierzyste(selected.getPelnynumer());
            nowe.setMacierzysty(selected.getLp());
            //zaznacza w macierzystym ze sa potomkowie
            selected.setMapotomkow(true);
            kontoDAO.edit(selected);
        }
        if (nowe.getMacierzyste().equals("0")) {
            nowe.setLevel(0);
            nowe.setMacierzysty(0);
        } else {
            int i = 1;
            i += StringUtils.countMatches(nowe.getMacierzyste(), "-");
            nowe.setLevel(i);
        }
        nowe.setMapotomkow(false);
        obliczpelnynumerkonta();
        if (znajdzduplikat() == 0) {
            kontoDAO.dodaj(nowe);
            nowe = new Konto();
            odswiezroot();
            //tu trzeba zrobic zeby dodawac do istniejacych
            Msg.msg("i", "Dodaje konto", "formX:messages");
        } else {
            Msg.msg("e", "Konto o takim numerze juz istnieje!", "formX:messages");
            nowe = new Konto();
        }

    }

    ;
    
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
            if (((Konto) selectednode.getData()).isBlokada()==true) {
                Msg.msg("e", "Na koncie istnieją zapisy. Nie można go usunąć");
                return;
            } else {
                kontoDAO.destroy(selectednode.getData());
                odswiezroot();
                Msg.msg("i", "Usuwam konto", "formX:messages");
            }
        } else {
            Msg.msg("e", "Nie wybrano konta", "formX:messages");
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
        Msg.msg("i", "Wybrano: " + ((Konto) p.getData()).getPelnynumer() + " " + ((Konto) p.getData()).getNazwapelna());
    }

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public static List<Konto> getWykazkontS() {
        return wykazkontS;
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
    private String wewy;

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
    private String listajs;

//   static{
//       listajs = new String[2];
//       listajs[0] = "jeden";
//       listajs[1] = "dwa";
//   }
    public String getListajs() {
        return "jeden,dwa,trzy,cztery,piec,szesc,siedem,osiem,dziewiec,dziesiec";
    }

    public String getOpiskonta() {
        return opiskonta;
    }

    public String getPelnynumerkonta() {
        return pelnynumerkonta;
    }

    public TreeNodeExtended getRoot() {
        return root;
    }

    public void setRoot(TreeNodeExtended root) {
        this.root = root;
    }
}
