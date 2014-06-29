/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import comparator.Tabelanbpcomparator;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import view.DokView;
import waluty.WalutyNBP;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class WalutyFKBean {

    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    
    

    public List<Tabelanbp> pobierzkursy(TabelanbpDAO tabelanbpDAO, WalutyDAOfk walutyDAOfk) throws ParseException {
        String datawstepna;
        Integer numertabeli;
        List<Tabelanbp> wierszejuzzapisane = tabelanbpDAO.findAll();
        Collections.sort(wierszejuzzapisane, new Tabelanbpcomparator());
        Tabelanbp wiersz = null;
        if (wierszejuzzapisane.size() > 0) {
            wiersz = wierszejuzzapisane.get(wierszejuzzapisane.size() - 1);
        }
        if (wiersz == null) {
            datawstepna = "2012-12-31";
            numertabeli = 1;
        } else {
            datawstepna = wiersz.getDatatabeli();
            numertabeli = Integer.parseInt(wiersz.getNrtabeli().split("/")[0]);
            numertabeli++;
        }
        List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
        List<Waluty> pobranewaluty = walutyDAOfk.findAll();
        FacesContext context = FacesContext.getCurrentInstance();
        WalutyNBP walutyNBP = (WalutyNBP) context.getApplication().evaluateExpressionGet(context, "#{walutyNBP}", WalutyNBP.class);
        for (Waluty w : pobranewaluty) {
            try {
                wierszepobranezNBP.addAll(walutyNBP.pobierzpliknbp(datawstepna, numertabeli, w.getSymbolwaluty()));
            } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                //Msg.msg("e", "nie udalo sie pobrac kursow walut z internetu");
            }
            //Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
        }
        for (Tabelanbp p : wierszepobranezNBP) {
            tabelanbpDAO.dodaj(p);
        }
        return wierszepobranezNBP;
    }
    
    @Schedule(hour = "14", persistent = false)
    public void pobierzkursy() {
        String datawstepna;
        Integer numertabeli;
        List<Tabelanbp> wierszejuzzapisane = tabelanbpDAO.findAll();
        Collections.sort(wierszejuzzapisane, new Tabelanbpcomparator());
        Tabelanbp wiersz = null;
        if (wierszejuzzapisane.size() > 0) {
            wiersz = wierszejuzzapisane.get(wierszejuzzapisane.size() - 1);
        }
        if (wiersz == null) {
            datawstepna = "2012-12-31";
            numertabeli = 1;
        } else {
            datawstepna = wiersz.getDatatabeli();
            numertabeli = Integer.parseInt(wiersz.getNrtabeli().split("/")[0]);
            numertabeli++;
        }
        List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
        List<Waluty> pobranewaluty = walutyDAOfk.findAll();
        FacesContext context = FacesContext.getCurrentInstance();
        WalutyNBP walutyNBP = (WalutyNBP) context.getELContext().getELResolver().getValue(context.getELContext(), null,"walutyNBP");
        for (Waluty w : pobranewaluty) {
            try {
                wierszepobranezNBP.addAll(walutyNBP.pobierzpliknbp(datawstepna, numertabeli, w.getSymbolwaluty()));
            } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                //Msg.msg("e", "nie udalo sie pobrac kursow walut z internetu");
            }
            //Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
        }
        for (Tabelanbp p : wierszepobranezNBP) {
            tabelanbpDAO.dodaj(p);
        }
    }

   
    
    
}
