/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.WalutyFKBean;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class WalutyViewFK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private List<Waluty> pobranewaluty;
    private List<String> symboleWalut;
    private List<Tabelanbp> pobranekursy;
    private List<Tabelanbp> pobranekursyRok;
    @Inject
    private Waluty nowawaluta;

    public WalutyViewFK() {
        pobranewaluty = new ArrayList<>();
        pobranekursy = new ArrayList<>();
        symboleWalut = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        pobranewaluty = walutyDAOfk.findAll();
        pobranekursy = tabelanbpDAO.findAll();
        pobranekursyRok = tabelanbpDAO.findKursyRok();
        for (Waluty w : pobranewaluty) {
            symboleWalut.add(w.getSymbolwaluty());
        }
    }

    public void dodajnowawalute() {
        try {
            nowawaluta.setSymbolwaluty(nowawaluta.getSymbolwaluty().toUpperCase(new Locale("pl")));
            nowawaluta.setNazwawaluty(nowawaluta.getNazwawaluty().toLowerCase(new Locale("pl")));
            walutyDAOfk.dodaj(nowawaluta);
            pobranewaluty.add(nowawaluta);
            Msg.msg("i", "Dodano nową walute");
        } catch (Exception e) {
            Msg.msg("e", "Nie dodano nowej waluty");
        }
    }
    
    public void pobierzkursy() throws ParseException {
        List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
        wierszepobranezNBP.addAll(WalutyFKBean.pobierzkursy(tabelanbpDAO, walutyDAOfk));
        for (Tabelanbp p : wierszepobranezNBP) {
            pobranekursy.add(p);
            pobranekursyRok.add(p);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<String> getSymboleWalut() {
        return symboleWalut;
    }

    public void setSymboleWalut(List<String> symboleWalut) {
        this.symboleWalut = symboleWalut;
    }

    public List<Waluty> getPobranewaluty() {
        return pobranewaluty;
    }

    public void setPobranewaluty(List<Waluty> pobranewaluty) {
        this.pobranewaluty = pobranewaluty;
    }

    public List<Tabelanbp> getPobranekursy() {
        return pobranekursy;
    }

    public void setPobranekursy(List<Tabelanbp> pobranekursy) {
        this.pobranekursy = pobranekursy;
    }

    public Waluty getNowawaluta() {
        return nowawaluta;
    }

    public void setNowawaluta(Waluty nowawaluta) {
        this.nowawaluta = nowawaluta;
    }
    
    
    public List<Tabelanbp> getPobranekursyRok() {
        return pobranekursyRok;
    }

    public void setPobranekursyRok(List<Tabelanbp> pobranekursyRok) {
        this.pobranekursyRok = pobranekursyRok;
    }
    
    
    //</editor-fold>

}
