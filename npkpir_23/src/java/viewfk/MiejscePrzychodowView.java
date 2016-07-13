/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;


import beansFK.MiejscePrzychodowBean;
import beansFK.PlanKontFKBean;
import beansFK.SlownikiBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.MiejscePrzychodowDAO;
import daoFK.UkladBRDAO;
import embeddablefk.MiejsceZest;
import entityfk.Konto;
import entityfk.MiejscePrzychodow;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdf.PdfMiejscePrzychodow;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class MiejscePrzychodowView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private MiejscePrzychodow selected;
    private List<MiejscePrzychodow> miejscaprzychodow;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private boolean zapisz0edytuj1;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private LinkedHashSet<TabelaMiejscePrzychodow> listasummiejscprzychodow;
    private List<TabelaMiejscePrzychodow> listawybranychprzychodow;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;

    public MiejscePrzychodowView() {
         E.m(this);
        
    }

    @PostConstruct
    public void init() {
        try {
            miejscaprzychodow = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
    public void sumymiesiecy() {
        try {
            miejscaprzychodow = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
            listasummiejscprzychodow = new LinkedHashSet<>();
            obliczsumymiejsc();
        } catch (Exception e) {  E.e(e);
            
        }
    }
    
    public void obliczsumymiejsc() {
        List<Konto> kontaslownikowe = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), 7);
        if (!wpisView.getMiesiacWpisu().equals("CR")) {
            List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikRokMcWynikSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            MiejscePrzychodowBean.zsumujkwotyzkont(miejscaprzychodow, kontaslownikowe, wpisView, stronaWierszaDAO, listasummiejscprzychodow, stronywiersza);
        } else {
            List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikRokWynikSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            MiejscePrzychodowBean.zsumujkwotyzkont(miejscaprzychodow, kontaslownikowe, wpisView, stronaWierszaDAO, listasummiejscprzychodow, stronywiersza);
        }
    }
    

    public void dodaj() {
        List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        selected.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumer());
        selected.setRok(wpisView.getRokWpisu());
        miejscePrzychodowDAO.dodaj(selected);
        PlanKontFKBean.aktualizujslownikMiejscaPrzychodow(wykazkont, miejscePrzychodowDAO, selected, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
        miejscaprzychodow = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        selected.setOpismiejsca(null);
        selected.setOpisskrocony(null);
        Msg.msg("Dodaje miejsce");
    }
    
     private String pobierzkolejnynumer() {
        int liczba = miejscePrzychodowDAO.countMiejscaPrzychodow(wpisView.getPodatnikObiekt()) + 1;
        return String.valueOf(liczba);
    }

    public void usun(MiejscePrzychodow miejscePrzychodow) {
        if (miejscePrzychodow.getAktywny() == true) {
            Msg.msg("e", "Miejsce przychodó jest w użyciu, nie można usunąć opisu");
        } else {
            miejscePrzychodowDAO.destroy(miejscePrzychodow);
            miejscaprzychodow.remove(miejscePrzychodow);
        }
    }
    
    public void edytuj(MiejscePrzychodow miejscePrzychodow) {
        selected = miejscePrzychodow;
        zapisz0edytuj1 = true;
    }
    
    public void zapiszedycje() {
        miejscePrzychodowDAO.edit(selected);
        SlownikiBean.aktualizujkontapoedycji(selected, 7, wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), kontoDAOfk);
        selected.setOpismiejsca(null);
        selected.setOpisskrocony(null);
        miejscaprzychodow = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        zapisz0edytuj1 = false;
        Msg.msg("Naniesiono zmiany");
    }
    
    public void zapisykontmiesiace() {
         if (!wpisView.getMiesiacWpisu().equals("CR")) {
            wpisView.wpisAktualizuj();
         }
         sumymiesiecy();
    }
    
    public int sortMiejscePrzychodow(Object o1, Object o2) {
        int nr1 = Integer.parseInt(((MiejscePrzychodow) o1).getNrkonta());
        int nr2 = Integer.parseInt(((MiejscePrzychodow) o2).getNrkonta());
        if (nr1 > nr2) {
            return 1;
        } else if (nr1 < nr2) {
            return -1;
        }
        return 0;
    }
    
    public void drukuj(int i) {
        if (listawybranychprzychodow != null && listawybranychprzychodow.size() > 0) {
            PdfMiejscePrzychodow.drukuj(listawybranychprzychodow, wpisView, i);
        } else {
            List tabela = new LinkedList(listasummiejscprzychodow);
            PdfMiejscePrzychodow.drukuj(tabela, wpisView, i);
        }
    }
    
    public void message() {
        Msg.msg("Wybrano wiersz");
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public MiejscePrzychodow getSelected() {
        return selected;
    }
    
    public void setSelected(MiejscePrzychodow selected) {
        this.selected = selected;
    }
    
    public List<MiejscePrzychodow> getMiejscaprzychodow() {
        return miejscaprzychodow;
    }
    
    public void setMiejscaprzychodow(List<MiejscePrzychodow> miejscaprzychodow) {
        this.miejscaprzychodow = miejscaprzychodow;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public boolean isZapisz0edytuj1() {
        return zapisz0edytuj1;
    }
    
    public void setZapisz0edytuj1(boolean zapisz0edytuj1) {
        this.zapisz0edytuj1 = zapisz0edytuj1;
    }
    
    public LinkedHashSet<TabelaMiejscePrzychodow> getListasummiejscprzychodow() {
        return listasummiejscprzychodow;
    }
    
    public void setListasummiejscprzychodow(LinkedHashSet<TabelaMiejscePrzychodow> listasummiejscprzychodow) {
        this.listasummiejscprzychodow = listasummiejscprzychodow;
    }
    
    public List<TabelaMiejscePrzychodow> getListawybranychprzychodow() {
        return listawybranychprzychodow;
    }
    
    public void setListawybranychprzychodow(List<TabelaMiejscePrzychodow> listawybranychprzychodow) {
        this.listawybranychprzychodow = listawybranychprzychodow;
    }
//</editor-fold>

   
    

    public static class TabelaMiejscePrzychodow {
        private int id;
        private MiejscePrzychodow miejscePrzychodow;
        private List<MiejsceZest> miejscePrzychodowZest;
        
        public TabelaMiejscePrzychodow() {
            this.miejscePrzychodowZest = new ArrayList<>();
        }

        public TabelaMiejscePrzychodow(int id, MiejscePrzychodow miejscePrzychodow, List<MiejsceZest> miejscePrzychodowZest) {
            this.id = id;
            this.miejscePrzychodow = miejscePrzychodow;
            this.miejscePrzychodowZest = miejscePrzychodowZest;
        }

        @Override
        public String toString() {
            return "TabelaMiejscePrzychodow{" + "id=" + id + ", miejscePrzychodow=" + miejscePrzychodow.getOpismiejsca() + ", miejscePrzychodowZest=" + miejscePrzychodowZest.size() + '}';
        }
        
        

        //<editor-fold defaultstate="collapsed" desc="comment">
        public int getId() {
            return id;
        }
        
        public void setId(int id) {
            this.id = id;
        }
        
        public MiejscePrzychodow getMiejscePrzychodow() {
            return miejscePrzychodow;
        }
        
        public void setMiejscePrzychodow(MiejscePrzychodow miejscePrzychodow) {
            this.miejscePrzychodow = miejscePrzychodow;
        }
        
        public List<MiejsceZest> getMiejscePrzychodowZest() {
            return miejscePrzychodowZest;
        }
        
        public void setMiejscePrzychodowZest(List<MiejsceZest> miejscePrzychodowZest) {
            this.miejscePrzychodowZest = miejscePrzychodowZest;
        }
//</editor-fold>
        
        
    }

    
}
