/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Kliencicomparator;
import dao.FakturaDAO;
import dao.FakturaRozrachunkiDAO;
import entity.FakturaRozrachunki;
import entity.Klienci;
import error.E;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaRozrachunkiAnalizaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Klienci> klienci;
    private Klienci szukanyklient;
    private List<FakturaRozrachunki> wprowadzoneplatnosci;
    @Inject
    private FakturaRozrachunki selected;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private FakturaRozrachunkiDAO fakturaRozrachunkiDAO;

    public FakturaRozrachunkiAnalizaView() {
        klienci = new ArrayList<>();
        wprowadzoneplatnosci = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        klienci.addAll(pobierzkontrahentow());
        Collections.sort(klienci, new Kliencicomparator());
        if (klienci != null) {
            for (Iterator<Klienci> it = klienci.iterator(); it.hasNext();) {
                if (it.next() == null) {
                    it.remove();
                }
            }
        }
        pobierzfakturyrozliczenia(wpisView.getMiesiacWpisu());
        System.out.println("d");
    }
    
    public void pobierzfakturyrozliczenia(String mc) {
        wprowadzoneplatnosci = fakturaRozrachunkiDAO.findByPodatnik(wpisView);
        for (Iterator<FakturaRozrachunki> it = wprowadzoneplatnosci.iterator(); it.hasNext();) {
            FakturaRozrachunki p = it.next();
            if (!p.getMc().equals(mc) || !p.getKontrahent().equals(szukanyklient)) {
                it.remove();
            }
        }
    }
   
    private Collection<? extends Klienci> pobierzkontrahentow() {
        Collection p = fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt());
        for (Iterator<Klienci> it = p.iterator(); it.hasNext();) {
            if (it.next() == null) {
                it.remove();
            }
        }
        return p;
    }
    
    public List<Klienci> completeKL(String query) {
        List<Klienci> results = new ArrayList<>();
        if (query.length() > 1) {
            Pattern pattern = Pattern.compile("[A-Z]{2}\\d+");
            Matcher m = pattern.matcher(query.toUpperCase());
            boolean czynipzagraniczny = m.matches();
            if (czynipzagraniczny) {
                for (Klienci p : klienci) {
                    if (p.getNip().startsWith(query.toUpperCase())) {
                            results.add(p);
                    }
                }
            } else {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Klienci p : klienci) {
                        if (p.getNip().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Klienci p : klienci) {
                        if (p.getNpelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
            }
            results.add(new Klienci("nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1", "ewidencja", "kolumna"));
        }
        return results;
    }
    
       
    public void usun(FakturaRozrachunki p) {
        try {
            fakturaRozrachunkiDAO.destroy(p);
            wprowadzoneplatnosci.remove(p);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    public List<Klienci> getKlienci() {
        return klienci;
    }
    
    public void setKlienci(List<Klienci> klienci) {
        this.klienci = klienci;
    }

    public Klienci getSzukanyklient() {
        return szukanyklient;
    }

    public void setSzukanyklient(Klienci szukanyklient) {
        this.szukanyklient = szukanyklient;
    }

    public FakturaRozrachunki getSelected() {
        return selected;
    }

    public void setSelected(FakturaRozrachunki selected) {
        this.selected = selected;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    public List<FakturaRozrachunki> getWprowadzoneplatnosci() {
        return wprowadzoneplatnosci;
    }

    public void setWprowadzoneplatnosci(List<FakturaRozrachunki> wprowadzoneplatnosci) {
        this.wprowadzoneplatnosci = wprowadzoneplatnosci;
    }
   
//</editor-fold>

    
    
    
    
}
