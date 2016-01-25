/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaDAO;
import dao.FakturaRozrachunkiDAO;
import dao.WpisDAO;
import entity.FakturaRozrachunki;
import entity.Klienci;
import entity.Wpis;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaRozrachunkiView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Klienci> klienci;
    private List<FakturaRozrachunki> wprowadzoneplatnosci;
    @Inject
    private FakturaRozrachunki selected;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{fakturaRozrachunkiRozlView}")
    private FakturaRozrachunkiRozlView fakturaRozrachunkiRozlView;
    @ManagedProperty(value = "#{fakturaRozrachunkiAnalizaView}")
    private FakturaRozrachunkiAnalizaView fakturaRozrachunkiAnalizaView;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private FakturaRozrachunkiDAO fakturaRozrachunkiDAO;
    @Inject 
    private WpisDAO wpisDAO;

    public FakturaRozrachunkiView() {
        klienci = new ArrayList<>();
        wprowadzoneplatnosci = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        klienci.addAll(pobierzkontrahentow());
        if (klienci != null) {
            for (Iterator<Klienci> it = klienci.iterator(); it.hasNext();) {
                if (it.next() == null) {
                    it.remove();
                }
            }
        }
        wprowadzoneplatnosci = fakturaRozrachunkiDAO.rozrachunkiZDnia(wpisView);
        System.out.println("d");
    }
   
    private Collection<? extends Klienci> pobierzkontrahentow() {
        return fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt());
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
    
    public void zaksiegujplatnosc() {
        try {
            selected.setRok(wpisView.getRokWpisuSt());
            selected.setMc(wpisView.getMiesiacWpisu());
            selected.setWystawca(wpisView.getPodatnikObiekt());
            selected.setWprowadzil(wpisView.getWprowadzil());
            fakturaRozrachunkiDAO.dodaj(selected);
            wprowadzoneplatnosci.add(selected);
            boolean zaplata0korekta1 = selected.isZaplata0korekta1();
            String rodzajdokumentu = selected.getRodzajdokumentu();
            String starynumer = selected.getNrdokumentu();
            String nowynumer = zrobnowynumer(starynumer);
            selected = new FakturaRozrachunki();
            selected.setZaplata0korekta1(zaplata0korekta1);
            selected.setRodzajdokumentu(rodzajdokumentu);
            selected.setNrdokumentu(nowynumer);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private String zrobnowynumer(String starynumer) {
        String nowynumer = starynumer;
        if (starynumer.contains("/")) {
            String[] numertablica = starynumer.split("/");
            if (numertablica.length == 2) {
                String koncowka = numertablica[numertablica.length-1];
                int nowakoncowka = Integer.parseInt(koncowka)+1;
                String nowakoncowka2 = String.valueOf(nowakoncowka);
                nowynumer = numertablica[0]+"/"+nowakoncowka2;
            }
        }
        return nowynumer;
    }
    
    public void usun(FakturaRozrachunki p) {
        try {
            fakturaRozrachunkiDAO.destroy(p);
            wprowadzoneplatnosci.remove(p);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        fakturaRozrachunkiDAO.edit((FakturaRozrachunki) event.getObject());
    }
    
    public void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.naniesDaneDoWpis();
        init();
        fakturaRozrachunkiRozlView.init();
        fakturaRozrachunkiAnalizaView.init();
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    public List<Klienci> getKlienci() {
        return klienci;
    }
    
    public void setKlienci(List<Klienci> klienci) {
        this.klienci = klienci;
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

    public FakturaRozrachunkiRozlView getFakturaRozrachunkiRozlView() {
        return fakturaRozrachunkiRozlView;
    }

    public void setFakturaRozrachunkiRozlView(FakturaRozrachunkiRozlView fakturaRozrachunkiRozlView) {
        this.fakturaRozrachunkiRozlView = fakturaRozrachunkiRozlView;
    }

    public FakturaRozrachunkiAnalizaView getFakturaRozrachunkiAnalizaView() {
        return fakturaRozrachunkiAnalizaView;
    }

    public void setFakturaRozrachunkiAnalizaView(FakturaRozrachunkiAnalizaView fakturaRozrachunkiAnalizaView) {
        this.fakturaRozrachunkiAnalizaView = fakturaRozrachunkiAnalizaView;
    }
    
    
    public List<FakturaRozrachunki> getWprowadzoneplatnosci() {
        return wprowadzoneplatnosci;
    }

    public void setWprowadzoneplatnosci(List<FakturaRozrachunki> wprowadzoneplatnosci) {
        this.wprowadzoneplatnosci = wprowadzoneplatnosci;
    }
   
//</editor-fold>

    

    
    
    
    
}
