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
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
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
    private Map<Pojazdy, List<PojazdyZest>> listapojazdy;

    public PojazdyView() {
    }
    
    @PostConstruct
    private void init() {
        try {
            pojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
             obliczsumymiejsc();
        } catch (Exception e) {
            
        }
        listapojazdy = new HashMap<>();
    }
    
    public void obliczsumymiejsc() {
        List<Konto> kontaslownikowe = kontoDAOfk.findKontaMaSlownik(wpisView, 2);
        for (Pojazdy p : pojazdy) {
            PojazdyBean.zsumujkwotyzkont(p, kontaslownikowe, wpisView, stronaWierszaDAO, listapojazdy);
        }
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

    public Map<Pojazdy, List<PojazdyZest>> getListapojazdy() {
        return listapojazdy;
    }

    public void setListapojazdy(Map<Pojazdy, List<PojazdyZest>> listapojazdy) {
        this.listapojazdy = listapojazdy;
    }

    
}
