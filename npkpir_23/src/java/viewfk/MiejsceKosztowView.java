/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import daoFK.MiejsceKosztowDAO;
import entityfk.MiejsceKosztow;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.security.auth.message.callback.PrivateKeyCallback;
import msg.Msg;
import org.primefaces.context.RequestContext;
import serialclone.SerialClone;
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

    public MiejsceKosztowView() {
    }
    
    @PostConstruct
    private void init() {
        miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
    }

    public void dodaj() {
        selected.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumer());
        miejsceKosztowDAO.dodaj(selected);
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

}
