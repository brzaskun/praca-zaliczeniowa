/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.MiejscePrzychodowDAO;
import daoFK.SkladkaCzlonekDAO;
import daoFK.SkladkaStowarzyszenieDAO;
import entityfk.MiejscePrzychodow;
import entityfk.SkladkaCzlonek;
import entityfk.SkladkaStowarzyszenie;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class SkladkaCzlonekView implements Serializable {
    @Inject
    private SkladkaCzlonek skladkaCzlonek;
    private List<SkladkaCzlonek> skladkaCzlonekLista;
    private List<SkladkaStowarzyszenie> skladkaStowarzyszenieLista;
    @Inject
    private SkladkaCzlonekDAO skladkaCzlonekDAO;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @Inject
    private SkladkaStowarzyszenieDAO skladkaStowarzyszenieDAO;
    boolean zapisz0edytuj1;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        skladkaStowarzyszenieLista = skladkaStowarzyszenieDAO.findByPodatnikRok(wpisView);
        List<MiejscePrzychodow> czlonkowiestowarzyszenia = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
        skladkaCzlonekLista = skladkaCzlonekDAO.findPodatnikRok(wpisView);
        if (skladkaCzlonekLista == null) {
            skladkaCzlonekLista = new ArrayList<>();
        }
        uzupelnijliste(czlonkowiestowarzyszenia);
    }
    
    private void uzupelnijliste(List<MiejscePrzychodow> czlonkowiestowarzyszenia) {
        Set<MiejscePrzychodow> czlonkowie = new HashSet<>();
        for (SkladkaCzlonek p : skladkaCzlonekLista) {
            czlonkowie.add(p.getCzlonek());
        }
        for (MiejscePrzychodow r : czlonkowiestowarzyszenia) {
            if (!czlonkowie.contains(r)) {
                skladkaCzlonekLista.add(new SkladkaCzlonek(r));
            }
        }
    }
    
    
    public void dodaj() {
        try {
            skladkaCzlonekDAO.dodaj(skladkaCzlonek);
            skladkaCzlonekLista.add(skladkaCzlonek);
            skladkaCzlonek = new SkladkaCzlonek();
            Msg.msg("Nanieniono nowy rodzaj członkostwa-składki");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił bład. Sprawdź czy nazwa rodzaju członkostwa/skrót nazwy sie nie powtarza");
        }
    }
    public void edytuj() {
        try {
            skladkaCzlonekDAO.edit(skladkaCzlonek);
            skladkaCzlonek = new SkladkaCzlonek();
            zapisz0edytuj1= false;
            Msg.msg("Zmieniono pozycję");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił bład. Sprawdź czy nazwa rodzaju członkostwa/skrót nazwy sie nie powtarza");
        }
    }
    
    public void edytuj(SkladkaCzlonek p) {        
        skladkaCzlonek = p;
        zapisz0edytuj1 = true;
        Msg.msg("Wybrano pozycję");
    }
    
    public void usun(SkladkaCzlonek p) {
        try {
            skladkaCzlonekDAO.destroy(p);
            skladkaCzlonekLista.remove(p);
            Msg.msg("Usunięto pozycję");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił bład podczas usuwania pozycji");
        }
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public SkladkaCzlonek getSkladkaCzlonek() {
        return skladkaCzlonek;
    }
    
    public void setSkladkaCzlonek(SkladkaCzlonek skladkaCzlonek) {
        this.skladkaCzlonek = skladkaCzlonek;
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

    public List<SkladkaStowarzyszenie> getSkladkaStowarzyszenieLista() {
        return skladkaStowarzyszenieLista;
    }

    public void setSkladkaStowarzyszenieLista(List<SkladkaStowarzyszenie> skladkaStowarzyszenieLista) {
        this.skladkaStowarzyszenieLista = skladkaStowarzyszenieLista;
    }
    
    public List<SkladkaCzlonek> getSkladkaCzlonekLista() {
        return skladkaCzlonekLista;
    }
    
    public void setSkladkaCzlonekLista(List<SkladkaCzlonek> skladkaCzlonekLista) {
        this.skladkaCzlonekLista = skladkaCzlonekLista;
    }
    
//</editor-fold>

   
    
}
