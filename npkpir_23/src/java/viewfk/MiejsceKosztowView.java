/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.MiejsceKosztowBean;
import beansFK.PlanKontFKBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.MiejsceKosztowDAO;
import embeddablefk.MiejsceKosztowZest;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
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
    private StronaWierszaDAO stronaWierszaDAO;
    private Map<MiejsceKosztow, List<MiejsceKosztowZest>> listasummiejsckosztow;

    public MiejsceKosztowView() {
    }
    
    @PostConstruct
    private void init() {
        try {
            miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
             obliczsumymiejsc();
        } catch (Exception e) {
            
        }
        listasummiejsckosztow = new HashMap<>();
    }
    
    public void obliczsumymiejsc() {
        List<Konto> kontaslownikowe = kontoDAOfk.findKontaMaSlownik(wpisView, 2);
        for (MiejsceKosztow p : miejscakosztow) {
            MiejsceKosztowBean.zsumujkwotyzkont(p, kontaslownikowe, wpisView, stronaWierszaDAO, listasummiejsckosztow);
        }
    }

    public void dodaj() {
        selected.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumer());
        miejsceKosztowDAO.dodaj(selected);
        PlanKontFKBean.aktualizujslownikMiejscaKosztow(selected, kontoDAOfk, wpisView);
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
        selected.setOpismiejsca(null);
        selected.setOpisskrocony(null);
        miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        zapisz0edytuj1 = false;
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

    public Map<MiejsceKosztow, List<MiejsceKosztowZest>> getListasummiejsckosztow() {
        return listasummiejsckosztow;
    }

    public void setListasummiejsckosztow(Map<MiejsceKosztow, List<MiejsceKosztowZest>> listasummiejsckosztow) {
        this.listasummiejsckosztow = listasummiejsckosztow;
    }

    
}
