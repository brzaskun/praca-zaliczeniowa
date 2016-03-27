/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.MiejsceKosztowBean;
import beansFK.PlanKontFKBean;
import beansFK.SlownikiBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.MiejsceKosztowDAO;
import daoFK.UkladBRDAO;
import embeddablefk.MiejsceKosztowZest;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdf.PdfMiejsceKosztow;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class MiejsceKosztowView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private MiejsceKosztow selected;
    private List<MiejsceKosztow> miejscakosztow;
    @Inject
    private MiejsceKosztowDAO miejsceKosztowDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private boolean zapisz0edytuj1;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private LinkedHashSet<TabelaMiejsceKosztow> listasummiejsckosztow;
    private List<TabelaMiejsceKosztow> listawybranychmiejsckosztow;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;

    public MiejsceKosztowView() {
         E.m(this);
        
    }

    @PostConstruct
    public void init() {
        try {
            miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        } catch (Exception e) {  E.e(e);
            
        }
    }
    
    public void sumymiesiecy() {
        try {
            miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
            listasummiejsckosztow = new LinkedHashSet<>();
            obliczsumymiejsc();
        } catch (Exception e) {  E.e(e);
            
        }
    }
    
    public void obliczsumymiejsc() {
        List<Konto> kontaslownikowe = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), 2);
        List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikRokMcWynikSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        MiejsceKosztowBean.zsumujkwotyzkont(miejscakosztow, kontaslownikowe, wpisView, stronaWierszaDAO, listasummiejsckosztow, stronywiersza);
    }
    

    public void dodaj() {
        List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        selected.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumer());
        miejsceKosztowDAO.dodaj(selected);
        PlanKontFKBean.aktualizujslownikMiejscaKosztow(wykazkont, miejsceKosztowDAO, selected, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
        miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        selected.setOpismiejsca(null);
        selected.setOpisskrocony(null);
        Msg.msg("Dodaje miejsce");
    }
    
     private String pobierzkolejnynumer() {
        int liczba = miejsceKosztowDAO.countMiejscaKosztow(wpisView.getPodatnikObiekt()) + 1;
        return String.valueOf(liczba);
    }

    public void usun(MiejsceKosztow miejsceKosztow) {
        if (miejsceKosztow.getAktywny() == true) {
            Msg.msg("e", "Miejsce kosztów jest w użyciu, nie można usunąć opisu");
        } else {
            miejsceKosztowDAO.destroy(miejsceKosztow);
            miejscakosztow.remove(miejsceKosztow);
        }
    }
    
    public void edytuj(MiejsceKosztow miejsceKosztow) {
        selected = miejsceKosztow;
        zapisz0edytuj1 = true;
    }
    
    public void zapiszedycje() {
        miejsceKosztowDAO.edit(selected);
        SlownikiBean.aktualizujkontapoedycji(selected, 3, wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), kontoDAOfk);
        selected.setOpismiejsca(null);
        selected.setOpisskrocony(null);
        miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        zapisz0edytuj1 = false;
    }
    
    public void zapisykontmiesiace() {
         wpisView.wpisAktualizuj();
         sumymiesiecy();
    }
    
    public int sortMiejsceKosztow(Object o1, Object o2) {
        int nr1 = Integer.parseInt(((MiejsceKosztow) o1).getNrkonta());
        int nr2 = Integer.parseInt(((MiejsceKosztow) o2).getNrkonta());
        if (nr1 > nr2) {
            return 1;
        } else if (nr1 < nr2) {
            return -1;
        }
        return 0;
    }
    
    public void drukuj(int i) {
        if (listawybranychmiejsckosztow != null && listawybranychmiejsckosztow.size() > 0) {
            PdfMiejsceKosztow.drukuj(listawybranychmiejsckosztow, wpisView, i);
        } else {
            List tabela = new LinkedList(listasummiejsckosztow);
            PdfMiejsceKosztow.drukuj(tabela, wpisView, i);
        }
    }
    
    public void message() {
        Msg.msg("Wybrano wiersz");
    }
    
    public MiejsceKosztow getSelected() {
        return selected;
    }

    public void setSelected(MiejsceKosztow selected) {
        this.selected = selected;
    }

    public List<MiejsceKosztow> getMiejscakosztow() {
        return miejscakosztow;
    }

    public void setMiejscakosztow(List<MiejsceKosztow> miejscakosztow) {
        this.miejscakosztow = miejscakosztow;
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

    public LinkedHashSet<TabelaMiejsceKosztow> getListasummiejsckosztow() {
        return listasummiejsckosztow;
    }

    public void setListasummiejsckosztow(LinkedHashSet<TabelaMiejsceKosztow> listasummiejsckosztow) {
        this.listasummiejsckosztow = listasummiejsckosztow;
    }

    public List<TabelaMiejsceKosztow> getListawybranychmiejsckosztow() {
        return listawybranychmiejsckosztow;
    }

    public void setListawybranychmiejsckosztow(List<TabelaMiejsceKosztow> listawybranychmiejsckosztow) {
        this.listawybranychmiejsckosztow = listawybranychmiejsckosztow;
    }

   
    

    public static class TabelaMiejsceKosztow {
        private int id;
        private MiejsceKosztow miejsceKosztow;
        private List<MiejsceKosztowZest> miejsceKosztowZest;
        
        public TabelaMiejsceKosztow() {
            this.miejsceKosztowZest = new ArrayList<>();
        }

        public TabelaMiejsceKosztow(int id, MiejsceKosztow miejsceKosztow, List<MiejsceKosztowZest> miejsceKosztowZest) {
            this.id = id;
            this.miejsceKosztow = miejsceKosztow;
            this.miejsceKosztowZest = miejsceKosztowZest;
        }
        
        

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public MiejsceKosztow getMiejsceKosztow() {
            return miejsceKosztow;
        }

        public void setMiejsceKosztow(MiejsceKosztow miejsceKosztow) {
            this.miejsceKosztow = miejsceKosztow;
        }

        public List<MiejsceKosztowZest> getMiejsceKosztowZest() {
            return miejsceKosztowZest;
        }

        public void setMiejsceKosztowZest(List<MiejsceKosztowZest> miejsceKosztowZest) {
            this.miejsceKosztowZest = miejsceKosztowZest;
        }
        
        
    }

    
}
