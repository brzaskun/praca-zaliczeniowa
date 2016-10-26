/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.RodzajCzlonkostwacomparator;
import daoFK.RodzajCzlonkostwaDAO;
import daoFK.SkladkaStowarzyszenieDAO;
import entityfk.RodzajCzlonkostwa;
import entityfk.SkladkaStowarzyszenie;
import error.E;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
public class SkladkaStowarzyszenieView implements Serializable {
    @Inject
    private SkladkaStowarzyszenie skladkaStowarzyszenie;
    private List<SkladkaStowarzyszenie> skladkaStowarzyszenieLista;
    private List<RodzajCzlonkostwa> rodzajCzlonkostwaLista;
    @Inject
    private SkladkaStowarzyszenieDAO skladkaStowarzyszenieDAO;
    @Inject
    private RodzajCzlonkostwaDAO rodzajCzlonkostwaDAO;
    boolean zapisz0edytuj1;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        skladkaStowarzyszenieLista = skladkaStowarzyszenieDAO.findByPodatnikRok(wpisView);
        rodzajCzlonkostwaLista = rodzajCzlonkostwaDAO.findAll();
        Collections.sort(rodzajCzlonkostwaLista, new RodzajCzlonkostwacomparator());
    }
    
    public void dodaj() {
        try {
            skladkaStowarzyszenie.setPodatnik(wpisView.getPodatnikObiekt());
            skladkaStowarzyszenie.setRok(wpisView.getRokWpisuSt());
            skladkaStowarzyszenieDAO.dodaj(skladkaStowarzyszenie);
            skladkaStowarzyszenieLista.add(skladkaStowarzyszenie);
            skladkaStowarzyszenie = new SkladkaStowarzyszenie();
            Msg.msg("Nanieniono nowy rodzaj składki");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił bład. Sprawdź czy nazwy się nie powtarzają");
        }
    }
    public void edytuj() {
        try {
            skladkaStowarzyszenieDAO.edit(skladkaStowarzyszenie);
            skladkaStowarzyszenie = new SkladkaStowarzyszenie();
            zapisz0edytuj1= false;
            Msg.msg("Zmieniono pozycję");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił bład. Sprawdź czy nazwy sie nie powtarzają");
        }
    }
    
    public void edytuj(SkladkaStowarzyszenie p) {        
        skladkaStowarzyszenie = p;
        zapisz0edytuj1 = true;
        Msg.msg("Wybrano pozycję");
    }
    
    public void usun(SkladkaStowarzyszenie p) {
        try {
            skladkaStowarzyszenieDAO.destroy(p);
            skladkaStowarzyszenieLista.remove(p);
            Msg.msg("Usunięto pozycję");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił bład podczas usuwania pozycji");
        }
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public SkladkaStowarzyszenie getSkladkaStowarzyszenie() {
        return skladkaStowarzyszenie;
    }
    
    public void setSkladkaStowarzyszenie(SkladkaStowarzyszenie skladkaStowarzyszenie) {
        this.skladkaStowarzyszenie = skladkaStowarzyszenie;
    }

    public boolean isZapisz0edytuj1() {
        return zapisz0edytuj1;
    }

    public void setZapisz0edytuj1(boolean zapisz0edytuj1) {
        this.zapisz0edytuj1 = zapisz0edytuj1;
    }

    public List<RodzajCzlonkostwa> getRodzajCzlonkostwaLista() {
        return rodzajCzlonkostwaLista;
    }

    public void setRodzajCzlonkostwaLista(List<RodzajCzlonkostwa> rodzajCzlonkostwaLista) {
        this.rodzajCzlonkostwaLista = rodzajCzlonkostwaLista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public List<SkladkaStowarzyszenie> getSkladkaStowarzyszenieLista() {
        return skladkaStowarzyszenieLista;
    }
    
    public void setSkladkaStowarzyszenieLista(List<SkladkaStowarzyszenie> skladkaStowarzyszenieLista) {
        this.skladkaStowarzyszenieLista = skladkaStowarzyszenieLista;
    }
    
//</editor-fold>
    
}
