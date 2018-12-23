/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import entity.Podatnik;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;
import xls.ReadXLSFile;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PlanKontInterView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Konto> wykazkont;
    private List<Konto> wykazkontwzor;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;

    public PlanKontInterView() {
         E.m(this);
    }
    
    
    @PostConstruct
    public void init() {
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
    }
    
    public void zachowajzmiany_wzor() {
        try {
            kontoDAOfk.editList(wykazkontwzor);
            Msg.msg("Zachowano nazwy kont wzorcowych.");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd podczas zachowywania nazw kont wzorcowych");
        }
    }
    
     public void zachowajzmiany() {
        try {
            kontoDAOfk.editList(wykazkont);
            Msg.msg("Zachowano nazwy kont.");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd podczas zachowywania nazw kont");
        }
    }
     
     public void implementuj() {
         List<String> numery = Collections.synchronizedList(new ArrayList<>());
         numery.add("201");
         numery.add("202");
         numery.add("203");
         numery.add("204");
         List<Konto> slowniki = kontoDAOfk.findWszystkieKontaPodatnikaTylkoSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
         for (Konto r : slowniki) {
            if (numery.contains(r.getSyntetycznenumer())) {
                r.setDe(r.getNazwapelna());
                kontoDAOfk.edit(r);
            }
         }
         for (Konto p : wykazkontwzor) {
             for (Konto r : wykazkont) {
                 if (r.getPelnynumer().equals(p.getPelnynumer())) {
                     r.setDe(p.getDe());
                     kontoDAOfk.edit(r);
                 } 
             }
         }
         wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
     }
     
    public void pobierzdanezpliku(Podatnik podatnik, String rok) {
        ReadXLSFile.updateKonta(kontoDAOfk, podatnik, Integer.parseInt(rok), "c://temp//plankont.xlsx");
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(podatnik, rok);
    }

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public List<Konto> getWykazkontwzor() {
        return wykazkontwzor;
    }

    public void setWykazkontwzor(List<Konto> wykazkontwzor) {
        this.wykazkontwzor = wykazkontwzor;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public KontoDAOfk getKontoDAOfk() {
        return kontoDAOfk;
    }

    public void setKontoDAOfk(KontoDAOfk kontoDAOfk) {
        this.kontoDAOfk = kontoDAOfk;
    }
    
    
    
}
