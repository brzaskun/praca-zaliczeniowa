/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.TabelanbpDAO;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import msg.Msg;
import org.xml.sax.SAXException;
import view.WpisView;
import waluty.WalutyNBP;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class WalutyKursRecznieView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyNBP walutyNBP;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{walutyViewFK}")
    private WalutyViewFK walutyViewFK;
    
    
      public void pobierzkursyNowaWaluta(Waluty w) {
        try {
            E.m(this);
            String datawstepna;
            Integer numertabeli;
    //        datawstepna = "2013-12-30";
    //        numertabeli = 250;
            datawstepna = wpisView.getRokUprzedniSt()+"-12-31";
            numertabeli = 252;
            List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
            try {
                Tabelanbp ostatniatabela = tabelanbpDAO.findOstatniaTabela(w.getSymbolwaluty());
                if (ostatniatabela != null) {
                    datawstepna = ostatniatabela.getDatatabeli();
                    numertabeli = Integer.parseInt(ostatniatabela.getNrtabeli().substring(0, 3));
                }
                wierszepobranezNBP.addAll(walutyNBP.pobierzpliknbp(datawstepna, numertabeli, w.getSymbolwaluty()));
            } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                //Msg.msg("e", "nie udalo sie pobrac kursow walut z internetu");
            }
            Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
            zachowajwiersze(wierszepobranezNBP);
            walutyViewFK.init();
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    private void zachowajwiersze (List<Tabelanbp> wierszepobranezNBP) {
        try {
            tabelanbpDAO.dodaj(wierszepobranezNBP);
        } catch (Exception e) { 
            E.e(e);
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public WalutyViewFK getWalutyViewFK() {
        return walutyViewFK;
    }

    public void setWalutyViewFK(WalutyViewFK walutyViewFK) {
        this.walutyViewFK = walutyViewFK;
    }

    
    
}
