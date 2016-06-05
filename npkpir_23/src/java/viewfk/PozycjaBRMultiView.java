    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKBean;
import beansFK.PozycjaRZiSFKBean;
import beansFK.StronaWierszaBean;
import beansFK.UkladBRBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
@ManagedBean
@ViewScoped
public class PozycjaBRMultiView implements Serializable {
    private static final long serialVersionUID = 1L;

    private TreeNode wybranynodekonta;
    private ArrayList<PozycjaRZiSBilans> pozycje;
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
    private UkladBRDAO ukladBRDAO;
    @Inject
    private UkladBR uklad;
    @Inject
    private WierszBODAO wierszBODAO;
    
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    

    public PozycjaBRMultiView() {
         E.m(this);
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
    }

    @PostConstruct
    private void init() {
        pozycje.addAll(pozycjaRZiSDAO.findAll());
    }
    


    public void obliczRZiS() {
        if (uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        }
        ArrayList<PozycjaRZiSBilans> pozycje = UkladBRBean.pobierzpozycje(pozycjaRZiSDAO, pozycjaBilansDAO, uklad, "", "r");
        UkladBRBean.czyscPozycje(pozycje);
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView);
        List<Konto> plankont = kontoDAO.findKontaWynikowePodatnikaBezPotomkow(wpisView);
        try {
            rootProjektRZiS.getChildren().clear();
            PozycjaRZiSFKBean.ustawRoota(rootProjektRZiS, pozycje, zapisy, plankont);
            level = PozycjaRZiSFKBean.ustawLevel(rootProjektRZiS, pozycje);
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e) {
            E.e(e);
            rootProjektRZiS.getChildren().clear();
            Msg.msg("e", "Wystąpił problem, nie pobrano układu");
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
        if (uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        }
        ArrayList<PozycjaRZiSBilans> pozycjeaktywa = new ArrayList<>();
        ArrayList<PozycjaRZiSBilans> pozycjepasywa = new ArrayList<>();
        pobierzPozycjeAktywaPasywa(pozycjeaktywa, pozycjepasywa);
        rootBilansAktywa.getChildren().clear();
        rootBilansPasywa.getChildren().clear();
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowbilansowe(stronaWierszaDAO, wpisView);
        try {
            List<Konto> kontabilansoweanalityczne = kontoDAO.findKontaBilansowePodatnikaBezPotomkow(wpisView);
            Konto kontowyniku = PlanKontFKBean.findKonto860(kontabilansoweanalityczne);
            naniesKwoteWynikFinansowy(kontowyniku);
            PozycjaRZiSFKBean.sumujObrotyNaKontach(zapisy, kontabilansoweanalityczne);
            PozycjaRZiSFKBean.ustawRootaBilans(rootBilansAktywa, pozycjeaktywa, kontabilansoweanalityczne, "aktywa");
            PozycjaRZiSFKBean.ustawRootaBilans(rootBilansPasywa, pozycjepasywa, kontabilansoweanalityczne, "pasywa");
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
        obliczRZiS();
        List<Object> listazwrotnapozycji = new ArrayList<>();
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
    
    private void pobierzPozycjeAktywaPasywa(ArrayList<PozycjaRZiSBilans> pozycjeaktywa, ArrayList<PozycjaRZiSBilans> pozycjepasywa) {
        try {
            if (uklad == null) {
                uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            }
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

    public void zachowajInt(TreeNodeExtended root) {
        List lista = new ArrayList();
        root.getChildrenTree(new ArrayList<TreeNodeExtended>(), lista);
        List<PozycjaRZiSBilans> pozycje = new ArrayList<>();
        for (Object p : lista) {
            pozycje.add((PozycjaRZiSBilans) p);
        }
        pozycjaRZiSDAO.editList(pozycje);
        Msg.msg("Zachowano zmiany");
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
                if (p.getKwota() != 0.0) {
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
            for (Iterator<KontoKwota> it = sumaPodpietychKont.iterator(); it.hasNext();) {
                if (Z.z(it.next().getKwota()) == 0.0) {
                    it.remove();
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
                if (p.getKwota() != 0.0) {
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
            for (Iterator<KontoKwota> it = sumaPodpietychKont.iterator(); it.hasNext();) {
                if (Z.z(it.next().getKwota()) == 0.0) {
                    it.remove();
                }
            }
        }
    }
    
    public void odswiezrzis() {
        wpisView.wpisAktualizuj();
        obliczRZiS();
    }
    
    public void odswiezbilans() {
        wpisView.wpisAktualizuj();
        pobierzukladprzegladBilans("aktywa");
    }
    
    public void odswiezbilansdwiestrony() {
        wpisView.wpisAktualizuj();
        pobierzukladprzegladBilans();
    }
    
    public void drukujRZiS() {
        PdfRZiS.drukujRZiS(rootProjektRZiS, wpisView);
    }
    
    public void drukujRZiSPozycje() {
        PdfRZiS.drukujRZiSPozycje(rootProjektRZiS, wpisView);
    }
    
    public void drukujBilans(String ap, double sumabilansowa) {
        if (ap.equals("a")) {
            PdfBilans.drukujBilans(rootBilansAktywa, wpisView, ap, sumabilansowa);
        } else {
            PdfBilans.drukujBilans(rootBilansPasywa, wpisView, ap, sumabilansowa);
        }
    }
    public void drukujBilansPozycje(String ap, double sumabilansowa) {
        if (ap.equals("a")) {
            PdfBilans.drukujBilansBOPozycje(rootBilansAktywa, wpisView, ap, sumabilansowa, false);
        } else {
            PdfBilans.drukujBilansBOPozycje(rootBilansPasywa, wpisView, ap, sumabilansowa, false);
        }
    }
    
    public void drukujBilansKonta(String ap, double sumabilansowa) {
        if (ap.equals("a")) {
            PdfBilans.drukujBilansKonta(rootBilansAktywa, wpisView, ap, sumabilansowa, false);
        } else {
            PdfBilans.drukujBilansKonta(rootBilansPasywa, wpisView, ap, sumabilansowa, false);
        }
    }
    
    public void drukujBilansKontaBez0(String ap, double sumabilansowa) {
        if (ap.equals("a")) {
            PdfBilans.drukujBilansKonta(rootBilansAktywa, wpisView, ap, sumabilansowa, true);
        } else {
            PdfBilans.drukujBilansKonta(rootBilansPasywa, wpisView, ap, sumabilansowa, true);
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

