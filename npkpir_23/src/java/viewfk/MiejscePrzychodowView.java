/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;


import beansFK.MiejscePrzychodowBean;
import beansFK.PlanKontFKBean;
import beansFK.SlownikiBean;
import comparator.MiejscePrzychodowcomparator;
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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import pdf.PdfMiejscePrzychodow;
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
    private List<MiejscePrzychodow> miejscaprzychodowfiltered;
    private List<MiejscePrzychodow> czlonkowiestowarzyszenia;
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
    private boolean rozwinwszystkie;

    public MiejscePrzychodowView() {
         ////E.m(this);
        
    }

    @PostConstruct
    public void init() { //E.m(this);
        try {
            miejscaprzychodow = miejscePrzychodowDAO.findMiejscaPodatnikWszystkie(wpisView.getPodatnikObiekt());
            czlonkowiestowarzyszenia = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
    public void sumymiesiecy() {
        try {
            miejscaprzychodow = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
            usunukryte();
            listasummiejscprzychodow = new LinkedHashSet<>();
            obliczsumymiejsc();
        } catch (Exception e) {  E.e(e);
            
        }
    }
    
    public void obliczsumymiejsc() {
        List<Konto> kontaslownikowe = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), 7);
        if (!wpisView.getMiesiacWpisu().equals("CR")) {
            List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikRokMcWynikSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            MiejscePrzychodowBean.zsumujkwotyzkont(miejscaprzychodow, kontaslownikowe, wpisView, stronaWierszaDAO, listasummiejscprzychodow, stronywiersza);
        } else {
            List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikRokWynikSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            MiejscePrzychodowBean.zsumujkwotyzkont(miejscaprzychodow, kontaslownikowe, wpisView, stronaWierszaDAO, listasummiejscprzychodow, stronywiersza);
        }
    }
    

    public void dodaj() {
        try {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            selected.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumer());
            selected.setRok(wpisView.getRokWpisu());
            miejscePrzychodowDAO.dodaj(selected);
            PlanKontFKBean.aktualizujslownikMiejscaPrzychodow(wykazkont, miejscePrzychodowDAO, selected, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            miejscaprzychodow = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
            czlonkowiestowarzyszenia.add(selected);
            Msg.msg("Dodaje miejsce");
            selected = new MiejscePrzychodow();
            Collections.sort(czlonkowiestowarzyszenia, new MiejscePrzychodowcomparator());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie dodano miejsca");
        }
    }
    
    private String pobierzkolejnynumer() {
        List miejsca = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        int max = 0;
        for (Iterator<MiejscePrzychodow> it = miejsca.iterator(); it.hasNext();) {
            MiejscePrzychodow m = it.next();
            int nr = Integer.parseInt(m.getNrkonta());
            if (max < nr) {
                max = nr;
            }
        }
        String zwrot = String.valueOf(max+1);
        return zwrot;
    }

    public void usun(MiejscePrzychodow miejscePrzychodow) {
        miejscePrzychodowDAO.destroy(miejscePrzychodow);
        miejscaprzychodow.remove(miejscePrzychodow);
        if (miejscaprzychodowfiltered != null) {
            miejscaprzychodowfiltered.remove(miejscePrzychodow);
        }
    }
    
    public void edytuj(MiejscePrzychodow miejscePrzychodow) {
        selected = miejscePrzychodow;
        zapisz0edytuj1 = true;
    }
    
    public void zapiszedycje() {
        miejscePrzychodowDAO.edit(selected);
        SlownikiBean.aktualizujkontapoedycji(selected, 7, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDAOfk);
        selected.setOpismiejsca(null);
        selected.setOpisskrocony(null);
        miejscaprzychodow = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        zapisz0edytuj1 = false;
        Msg.msg("Naniesiono zmiany");
    }
    
    public void ukryjmiejsceprzychodow(MiejscePrzychodow miejscePrzychodow) {
        miejscePrzychodowDAO.edit(miejscePrzychodow);
        zapisz0edytuj1 = false;
        Msg.msg("Naniesiono zmiany");
    }
    
    public void canceledycje() {
        zapisz0edytuj1 = false;
        selected = new MiejscePrzychodow();
        Msg.msg("Rezygnazja z edycji");
    }
    public void edytujpozycje(MiejscePrzychodow miejscePrzychodow, String dane, int dataemail) {
        boolean czyok = false;
        if (dataemail == 0) {
            czyok = sprawdzdata(dane);
        } else if (dataemail == 1) {
            czyok = sprawdzemail(dane);
        } else {
            czyok = true;
        }
        if (czyok) {
            miejscePrzychodowDAO.edit(miejscePrzychodow);
            Msg.msg("Naniesiono zmiany");
        } else {
            if (dane.equals("____-__-__") && dataemail == 0) {
               if (miejscePrzychodow.getPoczatek().equals("____-__-__")) {
                   miejscePrzychodow.setPoczatek(null);
               }
               if (miejscePrzychodow.getKoniec().equals("____-__-__")) {
                   miejscePrzychodow.setKoniec(null);
               }
               miejscePrzychodowDAO.edit(miejscePrzychodow);
               Msg.msg("Wyzerowano datę");
            } else {
                Msg.msg("Nieprawidłowy format danych. Nie dokonano zmian");
            }
        }
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
    public void drukujczlonkowie() {
        try {
            if (czlonkowiestowarzyszenia != null) {
                List tabela = new LinkedList(czlonkowiestowarzyszenia);
                PdfMiejscePrzychodow.drukujczlonkowie(tabela, wpisView);
                Msg.dP();
            } else {
                Msg.msg("e", "Lista jest pusta");
            }
        } catch(Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    public void drukuj(int i) {
        if (listawybranychprzychodow != null && listawybranychprzychodow.size() > 0) {
            PdfMiejscePrzychodow.drukuj(listawybranychprzychodow, wpisView, i);
        } else {
            List tabela = new LinkedList(listasummiejscprzychodow);
            PdfMiejscePrzychodow.drukuj(tabela, wpisView, i);
        }
    }
    
    public int compare(Object o1, Object o2) {
        int zwrot = 0;
        int nr1 = Integer.parseInt((String) o1);
        int nr2 = Integer.parseInt((String) o2);
        if (nr1 < nr2) {
            zwrot = -1;
        } else if (nr1 > nr2) {
            zwrot = 1;
        }
        return zwrot;
    }
    
    public void message() {
        Msg.msg("Wybrano wiersz");
    }
    
    private boolean sprawdzdata(String dane) {
        String regex = "^\\d{4}[-/\\s]?((((0[13578])|(1[02]))[-/\\s]?(([0-2][0-9])|(3[01])))|(((0[469])|(11))[-/\\s]?(([0-2][0-9])|(30)))|(02[-/\\s]?[0-2][0-9]))$";
        return dane.matches(regex);
    }

    private boolean sprawdzemail(String dane) {
        String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        return dane.matches(regex);
    }

    public void skopiujnazwy() {
        if (selected!=null) {
            selected.setOpisskrocony(selected.getOpismiejsca());
        }
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public MiejscePrzychodow getSelected() {
        return selected;
    }
    
    public void setSelected(MiejscePrzychodow selected) {
        this.selected = selected;
    }

    public boolean isRozwinwszystkie() {
        return rozwinwszystkie;
    }

    public void setRozwinwszystkie(boolean rozwinwszystkie) {
        this.rozwinwszystkie = rozwinwszystkie;
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

    public List<MiejscePrzychodow> getCzlonkowiestowarzyszenia() {
        return czlonkowiestowarzyszenia;
    }

    public void setCzlonkowiestowarzyszenia(List<MiejscePrzychodow> czlonkowiestowarzyszenia) {
        this.czlonkowiestowarzyszenia = czlonkowiestowarzyszenia;
    }

    public List<MiejscePrzychodow> getMiejscaprzychodowfiltered() {
        return miejscaprzychodowfiltered;
    }

    public void setMiejscaprzychodowfiltered(List<MiejscePrzychodow> miejscaprzychodowfiltered) {
        this.miejscaprzychodowfiltered = miejscaprzychodowfiltered;
    }
    
    public List<TabelaMiejscePrzychodow> getListawybranychprzychodow() {
        return listawybranychprzychodow;
    }
    
    public void setListawybranychprzychodow(List<TabelaMiejscePrzychodow> listawybranychprzychodow) {
        this.listawybranychprzychodow = listawybranychprzychodow;
    }
//</editor-fold>

     private void usunukryte() {
        for (Iterator<MiejscePrzychodow> it = miejscaprzychodow.iterator(); it.hasNext();) {
            if (it.next().isPokaz0chowaj1()) {
                it.remove();
            }
        }
    }

    
   
    

    public static class TabelaMiejscePrzychodow {
        private int id;
        private MiejscePrzychodow miejscePrzychodow;
        private List<MiejsceZest> miejscePrzychodowZest;
        
        public TabelaMiejscePrzychodow() {
            this.miejscePrzychodowZest = Collections.synchronizedList(new ArrayList<>());
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

    public static void main(String[] args) {
         String data = "brzaskun@o2.pl";
         String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
         error.E.s(data.matches(regex));
    }
    
}
