/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Table;
import javax.xml.parsers.ParserConfigurationException;
import msg.Msg;
import org.primefaces.context.RequestContext;
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
        System.out.println("pobierzkursy()");
        String datawstepna;
        Integer numertabeli;
        List<Tabelanbp> wierszejuzzapisane = tabelanbpDAO.findLast();
        Tabelanbp wiersz = null;
        if (wierszejuzzapisane.size() > 0) {
            wiersz = wierszejuzzapisane.get(0);
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
    //</editor-fold>
}
