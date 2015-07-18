/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
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
public class PlanKontInterView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Konto> wykazkont;
    private List<Konto> wykazkontwzor;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    
    @PostConstruct
    public void init() {
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
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
         for (Konto r : wykazkont) {
                 r.setDe(null);
                 kontoDAOfk.edit(r);
         }
         for (Konto p : wykazkontwzor) {
             for (Konto r : wykazkont) {
                 if (r.getPelnynumer().equals(p.getPelnynumer())) {
                     r.setDe(p.getDe());
                     kontoDAOfk.edit(r);
                 } 
             }
         }
         wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
