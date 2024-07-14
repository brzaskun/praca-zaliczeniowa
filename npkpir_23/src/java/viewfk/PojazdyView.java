/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.PlanKontFKBean;
import beansFK.PojazdyBean;
import beansFK.SlownikiBean;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.PojazdyDAO;
import dao.StronaWierszaDAO;
import dao.UkladBRDAO;
import embeddablefk.PojazdyZest;
import entityfk.Konto;
import entityfk.Pojazdy;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import pdf.PdfPojazdy;
import view.WpisView;
import interceptor.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class PojazdyView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private Pojazdy selected;
    private List<Pojazdy> pojazdy;
    @Inject
    private PojazdyDAO pojazdyDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private WpisView wpisView;
    private boolean zapisz0edytuj1;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private LinkedHashSet<TabelaPojazdy> listasumpojazdy;
    private List<TabelaPojazdy> listawybranychpojazdow;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    private boolean rozwinwszystkie;

    public PojazdyView() {
         ////E.m(this);
    }
    
    public void init() { //E.m(this);
        try {
            pojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
        } catch (Exception e) {  E.e(e);
            
        }
    }
    
     public void sumymiesiecy() {
        try {
            pojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
            usunukryte();
            listasumpojazdy = new LinkedHashSet<>();
            obliczsumy();
        } catch (Exception e) { 
            E.e(e);
        }
    }
    
    
    public void obliczsumy() {
        List<Konto> kontaslownikowe = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), 3);
        List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikRokMcWynikSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        PojazdyBean.zsumujkwotyzkont(pojazdy, kontaslownikowe, wpisView, stronaWierszaDAO, listasumpojazdy, stronywiersza);
    }

    public void dodaj() {
        List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        selected.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumer(), wpisView.getRokWpisu());
        pojazdyDAO.create(selected);
        PlanKontFKBean.aktualizujslownikPojazdy(wykazkont, pojazdyDAO, selected, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
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
            pojazdyDAO.remove(pojazdy);
            this.pojazdy.remove(pojazdy);
        }
    }
    
     public void ukryjpojazd(Pojazdy pojazd) {
        pojazdyDAO.edit(pojazd);
        SlownikiBean.ukryjkontapodeycji(pojazd, 3, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDAOfk, pojazd.isPokaz0chowaj1());
        zapisz0edytuj1 = false;
        Msg.msg("Naniesiono zmiany");
    }
    
    public void edytuj(Pojazdy pojazdy) {
        selected = pojazdy;
        zapisz0edytuj1 = true;
    }
    
    public void zapiszedycje() {
        pojazdyDAO.edit(selected);
        SlownikiBean.aktualizujkontapoedycji(selected, 3,wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDAOfk);
        selected.setNrrejestracyjny(null);
        selected.setNazwapojazdu(null);
        pojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
        zapisz0edytuj1 = false;
        Msg.msg("Naniesiono zmiany");
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

    public boolean isRozwinwszystkie() {
        return rozwinwszystkie;
    }

    public void setRozwinwszystkie(boolean rozwinwszystkie) {
        this.rozwinwszystkie = rozwinwszystkie;
    }

    private void usunukryte() {
        for (Iterator<Pojazdy> it = pojazdy.iterator(); it.hasNext();) {
            if (it.next().isPokaz0chowaj1()) {
                it.remove();
            }
        }
    }

     public void canceledycje() {
        zapisz0edytuj1 = false;
        selected = new Pojazdy();
        Msg.msg("Rezygnazja z edycji");
    }
    
   
     public static class TabelaPojazdy {
        private int id;
        private Pojazdy pojazd;
        private List<PojazdyZest> pojazdyZest;
        
        public TabelaPojazdy() {
            this.pojazdyZest = Collections.synchronizedList(new ArrayList<>());
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
