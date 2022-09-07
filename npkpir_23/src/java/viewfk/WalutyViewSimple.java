/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.WalutyDAOfk;
import entityfk.Waluty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

    public List<Waluty> getPobraneRodzajeWalut() {
        return pobraneRodzajeWalut;
    }

    public void setPobraneRodzajeWalut(List<Waluty> pobraneRodzajeWalut) {
        this.pobraneRodzajeWalut = pobraneRodzajeWalut;
    }
    

    

   
}
