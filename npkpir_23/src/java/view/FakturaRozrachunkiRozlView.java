/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaDAO;
import dao.FakturaRozrachunkiDAO;
import entity.FakturaRozrachunki;
import entity.Klienci;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class FakturaRozrachunkiRozlView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Klienci> klienci;
    private List<FakturaRozrachunki> wprowadzoneplatnosci;
    private List<FakturaRozrachunki> wprowadzoneplatnoscifiltered;
    @Inject
    private FakturaRozrachunki selected;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private FakturaRozrachunkiDAO fakturaRozrachunkiDAO;
    private double suma;

    public FakturaRozrachunkiRozlView() {
    }

    @PostConstruct
    public void init() { //E.m(this);
        klienci = Collections.synchronizedList(new ArrayList<>());
        wprowadzoneplatnosci = Collections.synchronizedList(new ArrayList<>());
        klienci.addAll(pobierzkontrahentow());
        if (klienci != null) {
            for (Iterator<Klienci> it = klienci.iterator(); it.hasNext();) {
                if (it.next() == null) {
                    it.remove();
                }
            }
        }
        pobierzplatnosci();
        sumuj();
    }
    
    public void pobierzplatnosci() {
        wprowadzoneplatnosci = fakturaRozrachunkiDAO.findByPodatnikrokMc(wpisView);
    }
   
    private Collection<? extends Klienci> pobierzkontrahentow() {
        return fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt());
    }
    
    public List<Klienci> completeKL(String query) {
        List<Klienci> results = Collections.synchronizedList(new ArrayList<>());
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
            results.add(new Klienci(-1, "nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1"));
        }
        return results;
    }
    
       
    public void usun(FakturaRozrachunki p) {
        try {
            fakturaRozrachunkiDAO.destroy(p);
            wprowadzoneplatnosci.remove(p);
            if (wprowadzoneplatnoscifiltered!=null) {
                wprowadzoneplatnoscifiltered.remove(p);
            }
            Msg.msg("Usunięto zapis");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Nie usunięto zapisu");
            E.e(e);
        }
    }
    
    public int sortujfaktrozrach(Object o1, Object o2) {
        int zwrot = 0;
        try {
            String[] a = ((String) o1).split("/");
            String[] b = ((String) o2).split("/");
            int nr1 = Integer.parseInt(a[a.length-1]);
            int nr2 = Integer.parseInt(b[b.length-1]);
            zwrot = porownaj(nr1, nr2);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    private static int porownaj(int o1, int o2) {
        if (o1 < o2) {
            return -1;
        } else if (o1 > o2) {
            return 1;
        } else {
            return 0;
        }
    }
    public void sumuj() {
        if (wprowadzoneplatnoscifiltered!=null) {
            suma = wprowadzoneplatnoscifiltered.stream().mapToDouble(FakturaRozrachunki::getKwotapln).sum();
        } else {
            suma = wprowadzoneplatnosci.stream().mapToDouble(FakturaRozrachunki::getKwotapln).sum();
        }
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    public List<Klienci> getKlienci() {
        return klienci;
    }
    
    public void setKlienci(List<Klienci> klienci) {
        this.klienci = klienci;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
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

    public List<FakturaRozrachunki> getWprowadzoneplatnoscifiltered() {
        return wprowadzoneplatnoscifiltered;
    }

    public void setWprowadzoneplatnoscifiltered(List<FakturaRozrachunki> wprowadzoneplatnoscifiltered) {
        this.wprowadzoneplatnoscifiltered = wprowadzoneplatnoscifiltered;
    }
    
    
    public List<FakturaRozrachunki> getWprowadzoneplatnosci() {
        return wprowadzoneplatnosci;
    }

    public void setWprowadzoneplatnosci(List<FakturaRozrachunki> wprowadzoneplatnosci) {
        this.wprowadzoneplatnosci = wprowadzoneplatnosci;
    }
   
//</editor-fold>

    
    
    
    
}
