/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.PlanKontFKBean;
import beansFK.PojazdyBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.PojazdyDAO;
import embeddablefk.PojazdyZest;
import entityfk.Konto;
import entityfk.Pojazdy;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdf.PdfMiejsceKosztow;
import pdf.PdfPojazdy;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PojazdyView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private Pojazdy selected;
    private List<Pojazdy> pojazdy;
    @Inject
    private PojazdyDAO pojazdyDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private boolean zapisz0edytuj1;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private LinkedHashSet<TabelaPojazdy> listasumpojazdy;
    private List<TabelaPojazdy> listawybranychpojazdow;

    public PojazdyView() {
    }
    
    public void init() {
        try {
            pojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
        } catch (Exception e) {  E.e(e);
            
        }
    }
    
     public void sumymiesiecy() {
        try {
            pojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
            listasumpojazdy = new LinkedHashSet<>();
            obliczsumy();
        } catch (Exception e) {  E.e(e);
            
        }
    }
    
    
    public void obliczsumy() {
        List<Konto> kontaslownikowe = kontoDAOfk.findKontaMaSlownik(wpisView, 3);
        List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikRokMcWynikSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        PojazdyBean.zsumujkwotyzkont(pojazdy, kontaslownikowe, wpisView, stronaWierszaDAO, listasumpojazdy, stronywiersza);
    }

    public void dodaj() {
        selected.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumer());
        pojazdyDAO.dodaj(selected);
        PlanKontFKBean.aktualizujslownikPojazdy(selected, kontoDAOfk, wpisView);
        pojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
        selected.setNrrejestracyjny(null);
        selected.setNazwapojazdu(null);
        Msg.msg("Dodaje miejsce");
    }
    
     private String pobierzkolejnynumer() {
        int liczba = pojazdyDAO.countPojazdy(wpisView.getPodatnikObiekt()) + 1;
        return String.valueOf(liczba);
    }

    public void usun(Pojazdy pojazdy) {
        if (pojazdy.getAktywny() == true) {
            Msg.msg("e", "Pojazd jest w użyciu, nie można usunąć opisu");
        } else {
            pojazdyDAO.destroy(pojazdy);
            this.pojazdy.remove(pojazdy);
        }
    }
    
    public void edytuj(Pojazdy pojazdy) {
        selected = pojazdy;
        zapisz0edytuj1 = true;
    }
    
    public void zapiszedycje() {
        pojazdyDAO.edit(selected);
        selected.setNrrejestracyjny(null);
        selected.setNazwapojazdu(null);
        pojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
        zapisz0edytuj1 = false;
    }
    
    public int sortPojazdy(Object o1, Object o2) {
        int nr1 = Integer.parseInt(((Pojazdy) o1).getNrkonta());
        int nr2 = Integer.parseInt(((Pojazdy) o2).getNrkonta());
        if (nr1 > nr2) {
            return 1;
        } else if (nr1 < nr2) {
            return -1;
        }
        return 0;
    }
    
     public void zapisykontmiesiace() {
         wpisView.wpisAktualizuj();
         sumymiesiecy();
    }
    

    
    public void drukuj(int i) {
        if (listawybranychpojazdow != null && listawybranychpojazdow.size() > 0) {
            PdfPojazdy.drukuj(listawybranychpojazdow, wpisView, i);
        } else {
            List tabela = new LinkedList(listasumpojazdy);
            PdfPojazdy.drukuj(tabela, wpisView, i);
        }
    }
     
    public Pojazdy getSelected() {
        return selected;
    }

    public void setSelected(Pojazdy selected) {
        this.selected = selected;
    }

    public List<Pojazdy> getPojazdy() {
        return pojazdy;
    }

    public void setPojazdy(List<Pojazdy> pojazdy) {
        this.pojazdy = pojazdy;
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

    public LinkedHashSet<TabelaPojazdy> getListasumpojazdy() {
        return listasumpojazdy;
    }

    public void setListasumpojazdy(LinkedHashSet<TabelaPojazdy> listasumpojazdy) {
        this.listasumpojazdy = listasumpojazdy;
    }

    public List<TabelaPojazdy> getListawybranychpojazdow() {
        return listawybranychpojazdow;
    }

    public void setListawybranychpojazdow(List<TabelaPojazdy> listawybranychpojazdow) {
        this.listawybranychpojazdow = listawybranychpojazdow;
    }

    

   
     public static class TabelaPojazdy {
        private int id;
        private Pojazdy pojazd;
        private List<PojazdyZest> pojazdyZest;
        
        public TabelaPojazdy() {
            this.pojazdyZest = new ArrayList<>();
        }

        public TabelaPojazdy(int id, Pojazdy pojazd, List<PojazdyZest> pojazdyZest) {
            this.id = id;
            this.pojazd = pojazd;
            this.pojazdyZest = pojazdyZest;
        }
        
        

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Pojazdy getPojazd() {
            return pojazd;
        }

        public void setPojazd(Pojazdy pojazd) {
            this.pojazd = pojazd;
        }

        public List<PojazdyZest> getPojazdyZest() {
            return pojazdyZest;
        }

        public void setPojazdyZest(List<PojazdyZest> pojazdyZest) {
            this.pojazdyZest = pojazdyZest;
        }

       
        
    }
    
}
