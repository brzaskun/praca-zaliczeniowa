/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturadodelementyDAO;
import dao.FakturaelementygraficzneDAO;
import entity.Fakturadodelementy;
import entity.FakturadodelementyPK;
import entity.Fakturaelementygraficzne;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import mail.Mail;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturadodelementyView implements Serializable {

    private static final Map<String, String> elementy;
    private static Map<String,String[]> pozycjecss;
    @Inject
    private FakturaelementygraficzneDAO fakturaelementygraficzneDAO;
    private List<Fakturadodelementy> fakturadodelementy;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String mailfakturastopka;

    static {
        elementy = new HashMap<String, String>();
        elementy.put("wezwanie do zapłaty","Niniejsza faktura jest jednocześnie wezwaniem do zapłaty");
        elementy.put("warunki dostawy","Dostawa na warunkach exworks");
        elementy.put("przewłaszczenie","Do momentu zapłaty towar jest własnością sprzedawcy");
        elementy.put("nr zamówienia","Podaj numer zamowienia");
        elementy.put("logo","Plik graficzny");
        elementy.put("nagłówek", "Biuro Rachunkowe Taxman - program księgowy online");
        elementy.put("stopka", "Fakturę wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman. "
                + "Dokument nie wymaga podpisu. Odbiorca dokumentu wyraził zgode na otrzymanie go w formie elektronicznej.");
        elementy.put("mailstopka", Mail.getStopka());
        elementy.put("stopka niemiecka","Nie wpisywać. Dane pobierane z zakładki 'Stopka niemiecka'");
    }
    

    public FakturadodelementyView() {
    }

    @PostConstruct
    public void init() {
        try {
            fakturadodelementy = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            mailfakturastopka = Mail.getStopka();
            if (fakturadodelementy == null || fakturadodelementy.isEmpty()) {
                fakturadodelementy = Collections.synchronizedList(new ArrayList<>());
            }
            for (String p : elementy.keySet()) {
                String podatnik = wpisView.getPodatnikWpisu();
                FakturadodelementyPK fPK = new FakturadodelementyPK(podatnik, p);
                Fakturadodelementy f = new Fakturadodelementy(fPK, elementy.get(p), false);
                if (!fakturadodelementy.contains(f)) {
                    fakturadodelementyDAO.dodaj(f);
                    fakturadodelementy.add(f);
                }
            }
            for (Iterator<Fakturadodelementy> it = fakturadodelementy.iterator(); it.hasNext();) {
                Fakturadodelementy p = it.next();
                if (p.getFakturadodelementyPK().getNazwaelementu().equals("mailstopka")) {
                    if (p.getTrescelementu() != null || !p.getTrescelementu().equals("")) {
                        mailfakturastopka = p.getTrescelementu();
                        it.remove();
                    }
                }
            }
            Fakturaelementygraficzne elementgraficzny = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
            if (elementgraficzny != null) {
                pozycjecss = new ConcurrentHashMap<>();
                pozycjecss.put("logo", new String[]{elementgraficzny.getSzerokosc(),elementgraficzny.getWysokosc()});
            }
        } catch (Exception e) { 
                E.e(e); 
        }
    }

    public void zachowajzmiany() {
        try {
            for (Fakturadodelementy p : fakturadodelementy) {
                fakturadodelementyDAO.dodaj(p);
            }
            Msg.msg("i", "Zachowano dodatkowe elementy faktury.");
        } catch (Exception e) { E.e(e); 
            for (Fakturadodelementy p : fakturadodelementy) {
                fakturadodelementyDAO.edit(p);
            }
            Msg.msg("i", "Wyedytowano dodatkowe elementy faktury.");
        }
    }
    
    public boolean czydodatkowyelementjestAktywny (String element) {
        for (Fakturadodelementy p : fakturadodelementy) {
            if (p.getFakturadodelementyPK().getNazwaelementu().equals(element)) {
                return p.getAktywny();
            }
        }
        return false;
    }
    
    public String pobierzelementdodatkowy (String element) {
        for (Fakturadodelementy p : fakturadodelementy) {
            if (p.getFakturadodelementyPK().getNazwaelementu().equals(element)) {
                return p.getTrescelementu();
            }
        }
        return "nie odnaleziono";
    }
    
    public void zachowajstopke() {
        Fakturadodelementy f = fakturadodelementyDAO.findFaktStopkaPodatnik(wpisView.getPodatnikWpisu());
        if (f != null) {
            f.setTrescelementu(mailfakturastopka);
            fakturadodelementyDAO.edit(f);
        } else {
            f = new Fakturadodelementy(wpisView.getPodatnikWpisu(), "mailstopka");
            f.setTrescelementu(mailfakturastopka);
            fakturadodelementyDAO.dodaj(f);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<Fakturadodelementy> getFakturadodelementy() {
        return fakturadodelementy;
    }

    public void setFakturadodelementy(List<Fakturadodelementy> fakturadodelementy) {
        this.fakturadodelementy = fakturadodelementy;
    }

    public Map<String, String[]> getPozycjecss() {
        return pozycjecss;
    }
    
    public String getMailfakturastopka() {
        return mailfakturastopka;
    }

    public void setMailfakturastopka(String mailfakturastopka) {
        this.mailfakturastopka = mailfakturastopka;
    }

    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    //</editor-fold>

}
