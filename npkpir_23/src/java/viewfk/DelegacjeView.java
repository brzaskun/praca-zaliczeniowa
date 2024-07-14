/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.DelegacjaBean;
import beansFK.PlanKontFKBean;
import beansFK.SlownikiBean;
import dao.DelegacjaDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.StronaWierszaDAO;
import dao.UkladBRDAO;
import embeddablefk.DelegacjaZest;
import entityfk.Delegacja;
import entityfk.Dokfk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import org.primefaces.PrimeFaces;
 import view.WpisView;
import interceptor.ConstructorInterceptor;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class DelegacjeView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private Delegacja selected;
    private List<Delegacja> delegacjekrajowe;
    private List<Delegacja> delegacjezagraniczne;
    @Inject
    private DelegacjaDAO delegacjaDAO;
    @Inject
    private WpisView wpisView;
    private boolean zapisz0edytuj1;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private Map<Delegacja, List<DelegacjaZest>> listadelegacja;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private PlanKontCompleteView planKontCompleteView;
    private int jest1niema0;
    private String rodzajdokpole;

    public DelegacjeView() {
        
   }
    
    
    public void init() { //E.m(this);
        try {
            delegacjekrajowe = delegacjaDAO.findDelegacjaPodatnik(wpisView, false);
            delegacjezagraniczne = delegacjaDAO.findDelegacjaPodatnik(wpisView,true);
            obliczsumymiejsc();
        } catch (Exception e) {  E.e(e);
            
        }
        listadelegacja = new ConcurrentHashMap<>();
    }
    
    public void obliczsumymiejsc() {
        List<Konto> kontaslownikowe = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), 2);
        for (Delegacja p : delegacjekrajowe) {
            DelegacjaBean.zsumujkwotyzkont(p, kontaslownikowe, wpisView, stronaWierszaDAO, listadelegacja);
        }
        for (Delegacja p : delegacjezagraniczne) {
            DelegacjaBean.zsumujkwotyzkont(p, kontaslownikowe, wpisView, stronaWierszaDAO, listadelegacja);
        }
    }
    
    public void stworz(boolean krajowa0zagraniczna1, Dokfk dokfk) {
        dodaj(krajowa0zagraniczna1);
        planKontCompleteView.init();
        jest1niema0 = 1;
        pobierzkontodladelegacji(dokfk);
    }
    
    public void zerujjest1niema0() {
        jest1niema0 = 1;
    }

    public void dodaj(boolean krajowa0zagraniczna1) {
        Delegacja duplikat = delegacjaDAO.findDelegacja(selected);
        if (duplikat == null) {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            selected.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumer(krajowa0zagraniczna1));
            selected.setKrajowa0zagraniczna1(krajowa0zagraniczna1);
            selected.setRok(wpisView.getRokWpisu());
            selected.setAktywny(true);
            delegacjaDAO.create(selected);
            if (krajowa0zagraniczna1) {
                delegacjezagraniczne = delegacjaDAO.findDelegacjaPodatnik(wpisView, krajowa0zagraniczna1);
                PlanKontFKBean.aktualizujslownikDelegacjeZagraniczne(wykazkont, delegacjaDAO, selected, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            } else {
                delegacjekrajowe = delegacjaDAO.findDelegacjaPodatnik(wpisView, krajowa0zagraniczna1);
                PlanKontFKBean.aktualizujslownikDelegacjeKrajowe(wykazkont, delegacjaDAO, selected, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            }
            selected = new Delegacja();
            Msg.msg("Dodaje delegację");
            PrimeFaces.current().ajax().update(":form_dialog_delegacje_stworz");
            PrimeFaces.current().ajax().update(":parametrydel");
        } else {
            Msg.msg("e", "Delegacja o takich parametrach już istnieje. Nie mogę dodać");
        }
    }
    
     private String pobierzkolejnynumer(boolean krajowa0zagraniczna1) {
        int liczba = delegacjaDAO.countDelegacja(wpisView, krajowa0zagraniczna1) + 1;
        return String.valueOf(liczba);
    }

    public void usun(Delegacja delegacja, boolean krajowa0zagraniczna1) {
        if (delegacja.getAktywny() == true) {
            Msg.msg("e", "Delegacja jest w użyciu, nie można usunąć opisu");
        } else {
            delegacjaDAO.remove(delegacja);
            if (krajowa0zagraniczna1) {
                this.delegacjezagraniczne.remove(delegacja);
            } else {
                this.delegacjekrajowe.remove(delegacja);
            }
            PlanKontFKBean.usunelementslownika(delegacja, kontoDAOfk, wpisView);
        }
    }
    
    public void edytuj(Delegacja delegacja) {
        selected = delegacja;
        zapisz0edytuj1 = true;
    }
    
    public void zapiszedycje(boolean krajowa0zagraniczna1) {
        delegacjaDAO.edit(selected);
        if (krajowa0zagraniczna1) {
            delegacjezagraniczne = delegacjaDAO.findDelegacjaPodatnik(wpisView,krajowa0zagraniczna1);
            SlownikiBean.aktualizujkontapoedycji(selected, 6, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDAOfk);
        } else {
            delegacjekrajowe = delegacjaDAO.findDelegacjaPodatnik(wpisView,krajowa0zagraniczna1);
            SlownikiBean.aktualizujkontapoedycji(selected, 5, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDAOfk);
        }
        zapisz0edytuj1 = false;
        selected = new Delegacja();
    }
    
   
    
    public int sortDelegacje(Object o1, Object o2) {
        int nr1 = Integer.parseInt(((Delegacja) o1).getNrkonta());
        int nr2 = Integer.parseInt(((Delegacja) o2).getNrkonta());
        if (nr1 > nr2) {
            return 1;
        } else if (nr1 < nr2) {
            return -1;
        }
        return 0;
    }
    
    public void sprawdzIstnienieDelegacji(Dokfk dokfk) {
        try {
            if (dokfk.getRodzajedok().getSkrot().contains("DEL")) {
                rodzajdokpole = dokfk.getRodzajedok().getSkrot();
                jest1niema0 = DelegacjaBean.sprawdzczyjestdelegacja(delegacjaDAO, dokfk.getNumerwlasnydokfk());
                Konto kontoRozrachunkowe = null;
                try {
                    kontoRozrachunkowe = kontoDAOfk.findKontoNazwaPelnaPodatnik(dokfk.getNumerwlasnydokfk(), wpisView);
                } catch (Exception e) {

                }
                if (kontoRozrachunkowe != null) {
                    dokfk.getRodzajedok().setKontorozrachunkowe(kontoRozrachunkowe);
                    PrimeFaces.current().ajax().update("formwpisdokument:przypkonto");
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
      public void pobierzkontodladelegacji(Dokfk dokfk) {
        if (dokfk.getRodzajedok().getSkrot().contains("DEL")) {
            Konto kontoRozrachunkowe = null;
            try {
                kontoRozrachunkowe = kontoDAOfk.findKontoNazwaPelnaPodatnik(dokfk.getNumerwlasnydokfk(), wpisView);
            } catch (Exception e) {

            }
            try {
                if (kontoRozrachunkowe != null) {
                    dokfk.getRodzajedok().setKontorozrachunkowe(kontoRozrachunkowe);
                    PrimeFaces.current().ajax().update("formwpisdokument:przypkonto");
                    dokfk.getListawierszy().get(0).getStronaMa().setKonto(kontoRozrachunkowe);
                    PrimeFaces.current().ajax().update("formwpisdokument:dataList");

                }
            } catch (Exception e){}
        }
    }
    
    public void dodajNowaDelegacje() {
        planKontCompleteView.init();
    }
    
    public Delegacja getSelected() {
        return selected;
    }

    public void setSelected(Delegacja selected) {
        this.selected = selected;
    }

    public List<Delegacja> getDelegacjekrajowe() {
        return delegacjekrajowe;
    }

    public void setDelegacjekrajowe(List<Delegacja> delegacjekrajowe) {
        this.delegacjekrajowe = delegacjekrajowe;
    }

    public List<Delegacja> getDelegacjezagraniczne() {
        return delegacjezagraniczne;
    }

    public void setDelegacjezagraniczne(List<Delegacja> delegacjezagraniczne) {
        this.delegacjezagraniczne = delegacjezagraniczne;
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

    public Map<Delegacja, List<DelegacjaZest>> getListadelegacja() {
        return listadelegacja;
    }

    public void setListadelegacja(Map<Delegacja, List<DelegacjaZest>> listadelegacja) {
        this.listadelegacja = listadelegacja;
    }

    public PlanKontCompleteView getPlanKontCompleteView() {
        return planKontCompleteView;
    }

    public void setPlanKontCompleteView(PlanKontCompleteView planKontCompleteView) {
        this.planKontCompleteView = planKontCompleteView;
    }

    public int getJest1niema0() {
        return jest1niema0;
    }

    public void setJest1niema0(int jest1niema0) {
        this.jest1niema0 = jest1niema0;
    }

    public String getRodzajdokpole() {
        return rodzajdokpole;
    }

    public void setRodzajdokpole(String rodzajdokpole) {
        this.rodzajdokpole = rodzajdokpole;
    }

    
}
