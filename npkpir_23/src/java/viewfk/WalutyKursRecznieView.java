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
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import msg.Msg;import org.xml.sax.SAXException;
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
    private String nrodtabela;
    
    
      public void pobierzkursyNowaWaluta(Waluty w) {
        try {
            ////E.m(this);
            String datawstepna;
            Integer numertabeli;
            datawstepna = "2019-12-18";
            numertabeli = 244;
            datawstepna = wpisView.getRokUprzedniSt()+"-12-31";
            numertabeli = 252;
            List<Tabelanbp> wierszepobranezNBP = Collections.synchronizedList(new ArrayList<>());
            boolean korygujdate = false;
            try {
                if (dataodtabela!=null && nrodtabela!=null) {
                    datawstepna = dataodtabela;
                    numertabeli = Integer.parseInt(nrodtabela);
                } else {
                    Tabelanbp ostatniatabela = tabelanbpDAO.findOstatniaTabela(w.getSymbolwaluty());
                    if (ostatniatabela != null) {
                        datawstepna = ostatniatabela.getDatatabeli();
                        numertabeli = Integer.parseInt(ostatniatabela.getNrtabeli().substring(0, 3));
                    }
                    korygujdate = true;
                }
                wierszepobranezNBP.addAll(walutyNBP.pobierzpliknbp(datawstepna, numertabeli, w.getSymbolwaluty(), korygujdate));
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

    public String getNrodtabela() {
        return nrodtabela;
    }

    public void setNrodtabela(String nrodtabela) {
        this.nrodtabela = nrodtabela;
    }
    
}
