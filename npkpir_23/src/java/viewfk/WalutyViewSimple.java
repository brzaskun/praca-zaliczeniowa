/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.WalutyFKBean;
import comparator.Tabelanbpcomparator;
import dao.TabelanbpDAO;
import dao.WalutyDAOfk;
import dao.WierszDAO;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import org.primefaces.event.RowEditEvent;
import view.WpisView; import org.primefaces.PrimeFaces;
import waluty.WalutyNBP;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class WalutyViewSimple implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private WalutyDAOfk walutyDAOfk;
    private List<Waluty> pobraneRodzajeWalut;
    private List<String> symboleWalut;
    public WalutyViewSimple() {
         ////E.m(this);
        pobraneRodzajeWalut = Collections.synchronizedList(new ArrayList<>());
        symboleWalut = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        pobraneRodzajeWalut = walutyDAOfk.findAll();
        for (Waluty w : pobraneRodzajeWalut) {
            symboleWalut.add(w.getSymbolwaluty());
        }
    }

    public List<String> getSymboleWalut() {
        return symboleWalut;
    }

    public void setSymboleWalut(List<String> symboleWalut) {
        this.symboleWalut = symboleWalut;
    }
    

    

   
}
