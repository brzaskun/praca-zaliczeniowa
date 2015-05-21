/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturadodelementyDAO;
import entity.Fakturadodelementy;
import entity.FakturadodelementyPK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class FakturadodelementyView implements Serializable {

    private static final Map<String, String> elementy;

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
    }
    private List<Fakturadodelementy> fakturadodelementy;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public FakturadodelementyView() {
    }

    @PostConstruct
    private void init() {
        try {
            fakturadodelementy = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            if (fakturadodelementy == null || fakturadodelementy.isEmpty()) {
                fakturadodelementy = new ArrayList<>();
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
        } catch (Exception e) { E.e(e); 
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

    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<Fakturadodelementy> getFakturadodelementy() {
        return fakturadodelementy;
    }

    public void setFakturadodelementy(List<Fakturadodelementy> fakturadodelementy) {
        this.fakturadodelementy = fakturadodelementy;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    //</editor-fold>

}
