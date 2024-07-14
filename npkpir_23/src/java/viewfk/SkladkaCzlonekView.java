/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.MiejscePrzychodowDAO;
import dao.SkladkaCzlonekDAO;
import dao.SkladkaStowarzyszenieDAO;
import entityfk.MiejscePrzychodow;
import entityfk.SkladkaCzlonek;
import entityfk.SkladkaStowarzyszenie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import view.WpisView;
import interceptor.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@RequestScoped
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
    @Inject
    private WpisView wpisView;
    private SkladkaStowarzyszenie skladkadomyslna;
    
    @PostConstruct
    private void init() { //E.m(this);
        skladkaStowarzyszenieLista = skladkaStowarzyszenieDAO.findByPodatnikRok(wpisView);
        List<MiejscePrzychodow> czlonkowiestowarzyszenia = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
        skladkaCzlonekLista = skladkaCzlonekDAO.findPodatnikRok(wpisView);
        if (skladkaCzlonekLista == null) {
            skladkaCzlonekLista = Collections.synchronizedList(new ArrayList<>());
        }
        uzupelnijliste(czlonkowiestowarzyszenia);
    }
    
    public void pobierz() {
        init();
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
    
    
    public void zachowajzmiany(SkladkaCzlonek s) {
        skladkaCzlonekDAO.edit(s);
        Msg.msg("Zachowano zmiany");
    }
    
    public void wartoscdomyslna() {
        if (skladkadomyslna != null) {
            for(SkladkaCzlonek t : skladkaCzlonekLista) {
                t.setSkladka(skladkadomyslna);
            }
            skladkaCzlonekDAO.editList(skladkaCzlonekLista);
            Msg.msg("Pobrano wskazaną wartość domyślną");
        }
    }
    
    public void zeruj() {
        if (!skladkaCzlonekLista.isEmpty()) {
            for(SkladkaCzlonek t : skladkaCzlonekLista) {
                t.setSkladka(null);
            }
            skladkaCzlonekDAO.editList(skladkaCzlonekLista);
            Msg.msg("Wyzerowano pozycje");
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

    public SkladkaStowarzyszenie getSkladkadomyslna() {
        return skladkadomyslna;
    }

    public void setSkladkadomyslna(SkladkaStowarzyszenie skladkadomyslna) {
        this.skladkadomyslna = skladkadomyslna;
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
