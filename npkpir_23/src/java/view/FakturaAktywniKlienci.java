/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Kliencicomparator;
import dao.FakturaDAO;
import dao.KlienciDAO;
import entity.Klienci;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaAktywniKlienci  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Klienci> klienciaktywowanie;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{fakturaRozrachunkiView}")
    private FakturaRozrachunkiView fakturaRozrachunkiView;
    @ManagedProperty(value = "#{fakturaRozrachunkiAnalizaView}")
    private FakturaRozrachunkiAnalizaView fakturaRozrachunkiAnalizaView;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private KlienciDAO klienciDAO;
    
    @PostConstruct
    public void init() {
        klienciaktywowanie = (List<Klienci>) pobierzkontrahentowaktywowanie();
        Collections.sort(klienciaktywowanie, new Kliencicomparator());
    }
    
     private Collection<? extends Klienci> pobierzkontrahentowaktywowanie() {
        Collection p = fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt());
        for (Iterator<Klienci> it = p.iterator(); it.hasNext();) {
            Klienci k = it.next();
            if (k == null) {
                it.remove();
            }
        }
        return p;
    }
    
    public void aktywacjakontrahenta(Klienci k) {
        if (k.isAktywnydlafaktrozrachunki()) {
            k.setAktywnydlafaktrozrachunki(false);
        } else {
            k.setAktywnydlafaktrozrachunki(true);
        }
        klienciDAO.edit(k);
        init();
        fakturaRozrachunkiView.init();
        fakturaRozrachunkiAnalizaView.init();
        Msg.msg("Zmieniono stan aktywacji klienta");
    }

    
    public List<Klienci> getKlienciaktywowanie() {
        return klienciaktywowanie;
    }

    public void setKlienciaktywowanie(List<Klienci> klienciaktywowanie) {
        this.klienciaktywowanie = klienciaktywowanie;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public FakturaRozrachunkiView getFakturaRozrachunkiView() {
        return fakturaRozrachunkiView;
    }

    public void setFakturaRozrachunkiView(FakturaRozrachunkiView fakturaRozrachunkiView) {
        this.fakturaRozrachunkiView = fakturaRozrachunkiView;
    }

    public FakturaRozrachunkiAnalizaView getFakturaRozrachunkiAnalizaView() {
        return fakturaRozrachunkiAnalizaView;
    }

    public void setFakturaRozrachunkiAnalizaView(FakturaRozrachunkiAnalizaView fakturaRozrachunkiAnalizaView) {
        this.fakturaRozrachunkiAnalizaView = fakturaRozrachunkiAnalizaView;
    }
    
    
    
}
