/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.TabelanbpDAO;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.xml.parsers.ParserConfigurationException;
import msg.Msg;
import org.joda.time.DateTime;
import org.xml.sax.SAXException;
import view.WpisView;
import waluty.WalutyNBP;
import interceptor.ConstructorInterceptor;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class WalutyKursRecznieView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyNBP walutyNBP;
    @Inject
    private WpisView wpisView;
    @Inject
    private WalutyViewFK walutyViewFK;
    private String dataodtabela;
    private String datadotabela;
        
    
      public void pobierzkursyNowaWaluta(Waluty w) {
        try {
            ////E.m(this);
            List<Tabelanbp> wierszepobranezNBP = Collections.synchronizedList(new ArrayList<>());
            if (dataodtabela!=null && dataodtabela.length()==10 && datadotabela!=null && datadotabela.length()==10) { 
                try {
                    wierszepobranezNBP.addAll(walutyNBP.pobierzpliknbp(dataodtabela, datadotabela, w));
                } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                    //Msg.msg("e", "nie udalo sie pobrac kursow walut z internetu");
                }
                Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
                zachowajwiersze(wierszepobranezNBP);
                walutyViewFK.init();
            } else {
                Msg.msg("e","Nie wpisano dat od do");
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
                tabelanbpDAO.create(p);
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
