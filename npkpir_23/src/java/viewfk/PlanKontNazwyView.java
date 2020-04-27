/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontocomparator;
import daoFK.KontoDAOfk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import view.WpisView;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PlanKontNazwyView implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    private List<Konto> wykazkont;
    private List<Konto> wykazkontselected;
    
    @PostConstruct
    private void init() { //E.m(this);
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<Konto> wykazkontwzorzec = kontoDAOfk.findWszystkieKontaPodatnikaPobierzRelacje(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
        for (Iterator<Konto> it = wykazkont.iterator();it.hasNext();) {
            Konto p = it.next();
            Konto wzorcowe = pobierzzewzorca(p.getPelnynumer(), wykazkontwzorzec);
            if (wzorcowe==null) {
                p.setNazwapelnawzorcowy("NIE MA TAKIEGO KONTA WZORCOWEGO");
            } else if (!p.getNazwapelna().equals(wzorcowe.getNazwapelna())) {
                p.setNazwapelnawzorcowy(wzorcowe.getNazwapelna());
                p.setNazwaskroconawzorcowy(wzorcowe.getNazwaskrocona());
            } else {
                it.remove();
            }
        }
        Collections.sort(wykazkont, new Kontocomparator());
        Msg.msg("Pobrnao konta");
    }

    private Konto pobierzzewzorca(String pelnynumer, List<Konto> wykazkontwzorzec) {
        Konto zwrot = null;
        List<Konto> result = wykazkontwzorzec.stream().filter(p -> p.getPelnynumer().equals(pelnynumer)).collect(Collectors.toList());
        if (!result.isEmpty() && result.size()==1) {
            zwrot = result.get(0);
        }
        return zwrot;
    }
    
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public List<Konto> getWykazkontselected() {
        return wykazkontselected;
    }

    public void setWykazkontselected(List<Konto> wykazkontselected) {
        this.wykazkontselected = wykazkontselected;
    }

    
    
    
    
}
