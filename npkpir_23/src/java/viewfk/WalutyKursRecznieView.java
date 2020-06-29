/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.TabelanbpDAO;
import data.Data;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import msg.Msg;import org.joda.time.DateTime;
import org.xml.sax.SAXException;
import view.WpisView;import waluty.WalutyNBP;

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
    private String dataodtabela;
    private String datadotabela;
        
    
      public void pobierzkursyNowaWaluta(Waluty w) {
        try {
            ////E.m(this);
            List<Tabelanbp> wierszepobranezNBP = Collections.synchronizedList(new ArrayList<>());
            if (datadotabela==null || datadotabela==null) {
                Msg.msg("e","Nie wpisano dat od do");
            } else {
                try {
                    wierszepobranezNBP.addAll(walutyNBP.pobierzpliknbp(dataodtabela, datadotabela, w));
                } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                    //Msg.msg("e", "nie udalo sie pobrac kursow walut z internetu");
                }
                Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
                zachowajwiersze(wierszepobranezNBP);
                walutyViewFK.init();
            }
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
     private static String zmiendate(String przekazanadata) {
        DateTime staradata = new DateTime(przekazanadata);
        DateTime nowadata = staradata.plusDays(1);
        String nowydzienformat = nowadata.toString("yyyy-MM-dd");
        return nowydzienformat;
    }
     
    private void zachowajwiersze (List<Tabelanbp> wierszepobranezNBP) {
        for (Tabelanbp p : wierszepobranezNBP) {
            try {
                tabelanbpDAO.dodaj(p);
            } catch (Exception e) { 
                E.e(e);
            }
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

     public String getDataodtabela() {
        return dataodtabela;
    }

    public void setDataodtabela(String dataodtabela) {
        this.dataodtabela = dataodtabela;
    }

    public String getDatadotabela() {
        return datadotabela;
    }

    public void setDatadotabela(String datadotabela) {
        this.datadotabela = datadotabela;
    }

  }
