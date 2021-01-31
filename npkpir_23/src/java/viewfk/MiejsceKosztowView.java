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
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.MiejsceKosztowDAO;
import dao.UkladBRDAO;
import embeddablefk.MiejsceZest;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import pdf.PdfMiejsceKosztow;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class MiejsceKosztowView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private MiejsceKosztow selected;
    private List<MiejsceKosztow> miejscakosztow;
    @Inject
    private MiejsceKosztowDAO miejsceKosztowDAO;
    @Inject
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
    private boolean rozwinwszystkie;

    public MiejsceKosztowView() {
         ////E.m(this);
        
    }

    public void init() { //E.m(this);
        try {
            miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnikWszystkie(wpisView.getPodatnikObiekt());
        } catch (Exception e) { 
            E.e(e);
        }
    }
    
    public void sumymiesiecy() {
        try {
            miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
            usunukryte();
            listasummiejsckosztow = new LinkedHashSet<>();
            obliczsumymiejsc();
        } catch (Exception e) {  E.e(e);
            
        }
    }
    
    public void obliczsumymiejsc() {
        List<Konto> kontaslownikowe = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), 2);
        List<StronaWiersza> stronywiersza = stronaWierszaDAO.findStronaByPodatnikRokMcWynikSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        MiejsceKosztowBean.zsumujkwotyzkont(miejscakosztow, kontaslownikowe, wpisView, stronaWierszaDAO, listasummiejsckosztow, stronywiersza);
    }
    

    public void dodaj() {
        try {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            selected.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumer(), wpisView.getRokWpisu());
            miejsceKosztowDAO.create(selected);
            PlanKontFKBean.aktualizujslownikMiejscaKosztow(wykazkont, miejsceKosztowDAO, selected, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
            selected.setOpismiejsca(null);
            selected.setOpisskrocony(null);
            Msg.msg("Dodaje miejsce");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie dodano miejsca");
        }
    }
    
     private String pobierzkolejnynumer() {
        List miejsca = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        int max = 0;
        for (Iterator<MiejsceKosztow> it = miejsca.iterator(); it.hasNext();) {
            MiejsceKosztow m = it.next();
            int nr = Integer.parseInt(m.getNrkonta());
            if (max < nr) {
                max = nr;
            }
        }
        String zwrot = String.valueOf(max+1);
        return zwrot;
    }

    public void usun(MiejsceKosztow miejsceKosztow) {
        try {
            //idslownika 2
            List<Konto> wszystkieslonikowe = kontoDAOfk.findWszystkieKontaPodatnikaTylkoSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            List<Konto> dosprawdzenia = Collections.synchronizedList(new ArrayList<>());
            for (Konto p : wszystkieslonikowe) {
                if (p.getNrkonta().equals(miejsceKosztow.getNrkonta()) && p.getKontomacierzyste().getIdslownika()==2) {
                    dosprawdzenia.add(p);
                }
            }
            boolean nieusuwac = false;
            Konto boxnakonto = null;
            for (Konto r : dosprawdzenia) {
                List<StronaWiersza> wynik = stronaWierszaDAO.findStronaByKontoOnly(r);
                if (wynik != null && wynik.size() > 0) {
                    boxnakonto = r;
                    nieusuwac = true;
                    break;
                }
            }
            if (nieusuwac) {
                Msg.msg("e", "Są zapisy w roku bieżącym na koncie "+boxnakonto.getPelnynumer()+" z użyciem tego miejsca kosztów. Nie można go usunąć!");
            } else {
                for (Konto r : dosprawdzenia) {
                    kontoDAOfk.remove(r);
                }
                miejsceKosztowDAO.remove(miejsceKosztow);
                miejscakosztow.remove(miejsceKosztow);
                Msg.msg("Usunięto miejsce kosztów "+miejsceKosztow.getOpismiejsca()+" wraz z kontami");
                
            }
            
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void edytuj(MiejsceKosztow miejsceKosztow) {
        selected = miejsceKosztow;
        zapisz0edytuj1 = true;
    }
    
    public void zapiszedycje() {
        miejsceKosztowDAO.edit(selected);
        SlownikiBean.aktualizujkontapoedycji(selected, 2, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDAOfk);
        selected.setOpismiejsca(null);
        selected.setOpisskrocony(null);
        miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        zapisz0edytuj1 = false;
        Msg.msg("Naniesiono zmiany");
    }
    
    public void ukryjmiejscekosztow(MiejsceKosztow miejsceKosztow) {
        miejsceKosztowDAO.edit(miejsceKosztow);
        SlownikiBean.ukryjkontapodeycji(miejsceKosztow, 2, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDAOfk, miejsceKosztow.isPokaz0chowaj1());
        zapisz0edytuj1 = false;
        Msg.msg("Naniesiono zmiany");
    }
    
    public void canceledycje() {
        zapisz0edytuj1 = false;
        selected = new MiejsceKosztow();
        Msg.msg("Rezygnazja z edycji");
    }
    
    public void zapisykontmiesiace() {
         wpisView.wpisAktualizuj();
         sumymiesiecy();
    }
    
    public int sortMiejsceKosztow(Object o1, Object o2) {
        int nr1 = Integer.parseInt(((MiejsceKosztow) o1).getNrkonta());
        int nr2 = Integer.parseInt(((MiejsceKosztow) o2).getNrkonta());
        if (nr1 > nr2) {
            return -1;
        } else if (nr1 < nr2) {
            return 1;
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
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public MiejsceKosztow getSelected() {
        return selected;
    }
    
    public void setSelected(MiejsceKosztow selected) {
        this.selected = selected;
    }

    public boolean isRozwinwszystkie() {
        return rozwinwszystkie;
    }

    public void setRozwinwszystkie(boolean rozwinwszystkie) {
        this.rozwinwszystkie = rozwinwszystkie;
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
//</editor-fold>

     private void usunukryte() {
        for (Iterator<MiejsceKosztow> it = miejscakosztow.iterator(); it.hasNext();) {
            if (it.next().isPokaz0chowaj1()) {
                it.remove();
            }
        }
    }

   
    

    public static class TabelaMiejsceKosztow {
        private int id;
        private MiejsceKosztow miejsceKosztow;
        private List<MiejsceZest> miejsceKosztowZest;
        
        public TabelaMiejsceKosztow() {
            this.miejsceKosztowZest = Collections.synchronizedList(new ArrayList<>());
        }

        public TabelaMiejsceKosztow(int id, MiejsceKosztow miejsceKosztow, List<MiejsceZest> miejsceKosztowZest) {
            this.id = id;
            this.miejsceKosztow = miejsceKosztow;
            this.miejsceKosztowZest = miejsceKosztowZest;
        }
        
        

        //<editor-fold defaultstate="collapsed" desc="comment">
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
        
        public List<MiejsceZest> getMiejsceKosztowZest() {
            return miejsceKosztowZest;
        }
        
        public void setMiejsceKosztowZest(List<MiejsceZest> miejsceKosztowZest) {
            this.miejsceKosztowZest = miejsceKosztowZest;
        }
//</editor-fold>
        
        
    }

    
}
