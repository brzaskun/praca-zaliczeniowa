/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.FakturaRozrachunkicomparator;
import comparator.Kliencicomparator;
import dao.FakturaDAO;
import dao.FakturaRozrachunkiDAO;
import dao.WpisDAO;
import embeddable.FakturaPodatnikRozliczenie;
import embeddable.Mce;
import entity.FakturaRozrachunki;
import entity.Klienci;
import entity.Wpis;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import msg.Msg;
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
    private List<FakturaRozrachunki> wprowadzoneplatnoscifiltered;
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
    private String west;
    private double suma;

    public FakturaRozrachunkiView() {
    }

    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.isUserInRole("Guest")) {
            west = "sub/layoutFakturaRozrachunki/west.xhtml";
        } else if (request.isUserInRole("Bookkeeper")) {
            west = "sub/layoutFakturaRozrachunki/west.xhtml";
        } else if (request.isUserInRole("Manager")) {
            west = "sub/layoutFakturaRozrachunkiManager/west.xhtml";
        } else if (request.isUserInRole("GuestFK")) {
            west = "../wspolny/sub/layoutFakturaRozrachunki/west.xhtml";
        } else if (request.isUserInRole("GuestFaktura")) {
            west = "../wspolny/sub/layoutFakturaRozrachunki/west.xhtml";
        } else if (request.isUserInRole("Multiuser")) {
            west = "../wspolny/sub/layoutFakturaRozrachunki/west.xhtml";
        }
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
        wprowadzoneplatnosci = fakturaRozrachunkiDAO.rozrachunkiZDnia(wpisView);
        suma = wprowadzoneplatnosci.stream().mapToDouble(FakturaRozrachunki::getKwota).sum();
        selected.setRodzajdokumentu("rk");
        pobierzostatninumer();
        Collections.sort(klienci, new Kliencicomparator());
    }
   
    public void sumuj() {
        if (wprowadzoneplatnoscifiltered!=null) {
            suma = wprowadzoneplatnoscifiltered.stream().mapToDouble(FakturaRozrachunki::getKwota).sum();
        } else {
            suma = wprowadzoneplatnosci.stream().mapToDouble(FakturaRozrachunki::getKwota).sum();
        }
    }
    
    private Collection<? extends Klienci> pobierzkontrahentow() {
        Collection p =  fakturaDAO.findKontrahentFakturyRO(wpisView.getPodatnikObiekt());
        for (Iterator<Klienci> it = p.iterator(); it.hasNext();) {
            Klienci k = it.next();
            if (k == null) {
                it.remove();
            } else if (k.isAktywnydlafaktrozrachunki() == false) {
                it.remove();
            }
        }
        return p;
    }
    
    public List<Klienci> completeKL(String query) {
        List<Klienci> results = Collections.synchronizedList(new ArrayList<>());
        if (query.length() > 1) {
            Pattern pattern = Pattern.compile("[A-Z]{2}\\d+");
            Matcher m = pattern.matcher(query.toUpperCase());
            boolean czynipzagraniczny = m.matches();
            if (czynipzagraniczny) {
                for (Klienci p : klienci) {
                    if (p.getNip() !=null && p.getNip().startsWith(query.toUpperCase())) {
                            results.add(p);
                    }
                }
            } else {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Klienci p : klienci) {
                        if (p.getNip() !=null && p.getNip().startsWith(query)) {
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
            sumuj();
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
            } else if (numertablica.length == 3) {
                String poczatek = numertablica[0];
                int nowypoczatek = Integer.parseInt(poczatek)+1;
                String nowypoczatek2 = String.valueOf(nowypoczatek);
                nowynumer = nowypoczatek2+"/"+numertablica[1]+"/"+numertablica[2];
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
    
    public void onRowEditAnaliza(RowEditEvent event) {
        FakturaPodatnikRozliczenie fr = (FakturaPodatnikRozliczenie) event.getObject();
        if (fr !=null && fr.getRozliczenie()!=null) {
            fr.getRozliczenie().setKwota(fr.getKwota());
            fakturaRozrachunkiDAO.edit(fr.getRozliczenie());
            Msg.msg("Naniesiono zmiany");
        } else {
            Msg.msg("e","Zmienić można jedynie płatności");
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
        //fakturaRozrachunkiAnalizaView.zestawieniezbiorcze();
    }
    
    private List<FakturaRozrachunki> pobierzplatnosci() {
        List<FakturaRozrachunki> wprowadzoneplatnosci = fakturaRozrachunkiDAO.findByPodatnikrokMc(wpisView);
        return wprowadzoneplatnosci;
    }
    
    public void pobierzostatninumer() {
        selected.setNrdokumentu(null);
        List<FakturaRozrachunki> wprowadzoneplatnosci = pobierzplatnosci();
        for (Iterator<FakturaRozrachunki> it = wprowadzoneplatnosci.iterator(); it.hasNext();) {
            if (!it.next().getRodzajdokumentu().equals(selected.getRodzajdokumentu())) {
                it.remove();
            }
        }
        if (wprowadzoneplatnosci.size() > 0) {
            Collections.sort(wprowadzoneplatnosci, new FakturaRozrachunkicomparator());
            String ostatninumer = wprowadzoneplatnosci.get(wprowadzoneplatnosci.size()-1).getNrdokumentu();
            selected.setNrdokumentu(zrobnowynumer(ostatninumer));
        } else {
            String nr = String.valueOf(Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu()))+"/1";
            selected.setNrdokumentu(nr);
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

    public List<FakturaRozrachunki> getWprowadzoneplatnoscifiltered() {
        return wprowadzoneplatnoscifiltered;
    }

    public void setWprowadzoneplatnoscifiltered(List<FakturaRozrachunki> wprowadzoneplatnoscifiltered) {
        this.wprowadzoneplatnoscifiltered = wprowadzoneplatnoscifiltered;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
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
