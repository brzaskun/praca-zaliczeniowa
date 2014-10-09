/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.PozycjaRZiSFKBean;
import comparator.Kontocomparator;
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
import entityfk.UkladBR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PozycjaBRKontaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<PozycjaRZiSBilans> pozycje;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private KontopozycjaDAO kontopozycjarzisDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    @Inject
    private UkladBR uklad;
    private String wnmaPrzypisywanieKont;
    private Konto boxNaKonto;
    private boolean aktywa0pasywa1;
    private List<Konto> wykazkont;
    private ArrayList<Konto> przyporzadkowanekonta;
    private TreeNodeExtended rootProjektKonta;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private int level = 0;
    private String wybranapozycja;
    private TreeNode wybranynodekonta;

    public PozycjaBRKontaView() {
        this.wykazkont = new ArrayList<>();
        this.rootProjektKonta = new TreeNodeExtended("root", null);
        this.pozycje = new ArrayList<>();
        this.przyporzadkowanekonta = new ArrayList<>();
    }
    
    
    
    public void pobierzukladkonto(String br, String aktywapasywa) {
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
                    aktywa0pasywa1 = false;
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladAktywa(uklad));
                } else {
                    aktywa0pasywa1 = true;
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
            uzupelnijpozycjeOKontaR(pozycje);
        } else {
            drugiinitbilansowe();
            uzupelnijpozycjeOKonta(pozycje);
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
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalityki(pobraneKontaSyntetyczne, wykazkont, kontoDAO, wpisView.getPodatnikWpisu(), aktywa0pasywa1);
        Collections.sort(wykazkont, new Kontocomparator());
    }
    
     private void uzupelnijpozycjeOKonta(List<PozycjaRZiSBilans> pozycje) {
        for (PozycjaRZiSBilans p : pozycje) {
            PozycjaRZiSFKBean.wyszukajprzyporzadkowaneBLista(kontoDAO, p, pozycjaBilansDAO, wpisView.getPodatnikWpisu());
        }
    }
    
    private void uzupelnijpozycjeOKontaR(List<PozycjaRZiSBilans> pozycje) {
        for (PozycjaRZiSBilans p : pozycje) {
            PozycjaRZiSFKBean.wyszukajprzyporzadkowaneRLista(kontoDAO, p, pozycjaBilansDAO, wpisView.getPodatnikWpisu());
        }
    }

    public void zaksiegujzmianypozycji(String br) {
        List<Konto> plankont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu());
        for (Konto p : plankont) {
            Kontopozycja kontopozycja = new Kontopozycja();
            if (p.getPozycjaWn() != null || p.getPozycjaMa() != null) {
                KontopozycjaPK kontopozycjaPK = new KontopozycjaPK();
                kontopozycja.setKonto(p);
                kontopozycja.setUkladBR(uklad);
                kontopozycja.setKontopozycjaPK(kontopozycjaPK);
                kontopozycja.setPozycjaWn(p.getPozycjaWn());
                kontopozycja.setPozycjaMa(p.getPozycjaMa());
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
                boxNaKonto = konto;
                if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                    if (konto.getPozycjaWn() == null && konto.getPozycjaMa() == null) {
                        RequestContext.getCurrentInstance().update("kontownmawybor");
                        RequestContext.getCurrentInstance().execute("PF('kontownmawybor').show();");
                        Msg.msg("Konto niezwykle");
                    } else {
                        if (konto.getPozycjaWn() != null) {
                            wnmaPrzypisywanieKont = "ma";
                            onKontoDropKontaSpecjalne();
                        } else {
                            wnmaPrzypisywanieKont = "wn";
                            onKontoDropKontaSpecjalne();
                        }
                    }
                }
                
            }
        }
        if (br.equals("r")) {
            drugiinit();
        } else {
            drugiinitbilansowe();
        }
    }
    
        public void onKontoDropKontaSpecjalne() {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            Konto konto = boxNaKonto;
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                wykazkont.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                if (wnmaPrzypisywanieKont.equals("wn")) {
                    konto.setPozycjaWn(wybranapozycja);
                } else {
                    konto.setPozycjaMa(wybranapozycja);
                }
                konto.setPozycjonowane(true);
                kontoDAO.edit(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto.getPelnynumer(), wybranapozycja, kontoDAO, wpisView.getPodatnikWpisu(), wnmaPrzypisywanieKont);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto.getMacierzyste(), kontoDAO, wpisView.getPodatnikWpisu());
                }
                RequestContext.getCurrentInstance().update("formbilansuklad:dostepnekonta");
                RequestContext.getCurrentInstance().update("formbilansuklad:selected");
            } else if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                if (wnmaPrzypisywanieKont.equals("wn")) {
                    konto.setPozycjaWn(wybranapozycja);
                } else {
                    konto.setPozycjaMa(wybranapozycja);
                }
                konto.setPozycjonowane(true);
                kontoDAO.edit(konto);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                wykazkont.remove(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto.getPelnynumer(), wybranapozycja, kontoDAO, wpisView.getPodatnikWpisu(), wnmaPrzypisywanieKont);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto.getMacierzyste(), kontoDAO, wpisView.getPodatnikWpisu());
                }
                RequestContext.getCurrentInstance().update("formbilansuklad:dostepnekonta");
                RequestContext.getCurrentInstance().update("formbilansuklad:selected");
            }
        }
        drugiinitbilansowe();
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
        } else if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("szczególne")) {
            przyporzadkowanekonta.remove(konto);
            String wnma = "";
            if (konto.getPozycjaWn()!=null && konto.getPozycjaWn().equals(wybranapozycja)) {
                wnma = "wn";
                konto.setPozycjaWn(null);
            } else if (konto.getPozycjaMa()!=null && konto.getPozycjaMa().equals(wybranapozycja)) {
                wnma = "ma";
                konto.setPozycjaMa(null);
            }
            if (konto.getPozycjaWn() == null && konto.getPozycjaMa() == null) {
                konto.setPozycjonowane(false);
            }
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto.getPelnynumer(), null, kontoDAO, wpisView.getPodatnikWpisu(),wnma);
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
            pobierzukladkonto("b", aktywa0pasywa1 == false ? "aktywa" : "pasywa");
        }
    }

    public void wybranopozycjeRZiS() {
        wybranapozycja = ((PozycjaRZiS) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowane(kontoDAO, wybranapozycja, wpisView.getPodatnikWpisu()));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaRZiS) wybranynodekonta.getData()).getNazwa());
    }
    public void wybranopozycjeBilans() {
        wybranapozycja = ((PozycjaBilans) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowaneB(kontoDAO, wybranapozycja, wpisView.getPodatnikWpisu()));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaBilans) wybranynodekonta.getData()).getNazwa());
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

    public ArrayList<PozycjaRZiSBilans> getPozycje() {
        return pozycje;
    }

    public void setPozycje(ArrayList<PozycjaRZiSBilans> pozycje) {
        this.pozycje = pozycje;
    }

    public UkladBR getUklad() {
        return uklad;
    }

    public void setUklad(UkladBR uklad) {
        this.uklad = uklad;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public boolean isAktywa0pasywa1() {
        return aktywa0pasywa1;
    }

    public void setAktywa0pasywa1(boolean aktywa0pasywa1) {
        this.aktywa0pasywa1 = aktywa0pasywa1;
    }

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public ArrayList<Konto> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

    public void setPrzyporzadkowanekonta(ArrayList<Konto> przyporzadkowanekonta) {
        this.przyporzadkowanekonta = przyporzadkowanekonta;
    }

    public TreeNodeExtended getRootProjektKonta() {
        return rootProjektKonta;
    }

    public void setRootProjektKonta(TreeNodeExtended rootProjektKonta) {
        this.rootProjektKonta = rootProjektKonta;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getWybranapozycja() {
        return wybranapozycja;
    }

    public void setWybranapozycja(String wybranapozycja) {
        this.wybranapozycja = wybranapozycja;
    }

    public TreeNode getWybranynodekonta() {
        return wybranynodekonta;
    }

    public void setWybranynodekonta(TreeNode wybranynodekonta) {
        this.wybranynodekonta = wybranynodekonta;
    }

    public Konto getBoxNaKonto() {
        return boxNaKonto;
    }

    public void setBoxNaKonto(Konto boxNaKonto) {
        this.boxNaKonto = boxNaKonto;
    }

    public String getWnmaPrzypisywanieKont() {
        return wnmaPrzypisywanieKont;
    }

    public void setWnmaPrzypisywanieKont(String wnmaPrzypisywanieKont) {
        this.wnmaPrzypisywanieKont = wnmaPrzypisywanieKont;
    }
    
    
    
}
