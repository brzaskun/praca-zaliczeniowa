/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Tabelanbpcomparator;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import msg.Msg;
import org.xml.sax.SAXException;
import waluty.WalutyNBP;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class WalutyViewFK implements Serializable {

    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private List<Waluty> pobranewaluty;
    private List<Tabelanbp> pobranekursy;
    private List<Tabelanbp> pobranekursyRok;
    @Inject
    private Waluty nowawaluta;

    public WalutyViewFK() {
        pobranewaluty = new ArrayList<>();
        pobranekursy = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        pobranewaluty = walutyDAOfk.findAll();
        pobranekursy = tabelanbpDAO.findAll();
        pobranekursyRok = tabelanbpDAO.findKursyRok();
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
    
    @Schedule(hour="14", persistent=false)
    public void pobierzkursy() throws ParseException {
        String datawstepna;
        Integer numertabeli;
        List<Tabelanbp> wierszejuzzapisane = tabelanbpDAO.findAll();
        Collections.sort(wierszejuzzapisane, new Tabelanbpcomparator());
        Tabelanbp wiersz = null;
        if (wierszejuzzapisane.size() > 0) {
            wiersz = wierszejuzzapisane.get(wierszejuzzapisane.size()-1);
        }
        if (wiersz == null) {
            datawstepna = "2012-12-31";
            numertabeli = 1;
        } else {
            datawstepna = wiersz.getDatatabeli();
            numertabeli = Integer.parseInt(wiersz.getTabelanbpPK().getNrtabeli().split("/")[0]);
            numertabeli++;
        }
        List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
        List<Waluty> pobranewaluty = walutyDAOfk.findAll();
        for (Waluty w : pobranewaluty) {
            try {
                wierszepobranezNBP.addAll(WalutyNBP.pobierzpliknbp(datawstepna, numertabeli, w.getSymbolwaluty()));
            } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                //Msg.msg("e", "nie udalo sie pobrac kursow walut z internetu");
            }
            //Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
        }
        for (Tabelanbp p : wierszepobranezNBP) {
            tabelanbpDAO.dodaj(p);
            pobranekursy.add(p);
            pobranekursyRok.add(p);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
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
