/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.sun.org.apache.xpath.internal.operations.Variable;
import entityfk.SprawozdanieFinansowe;
import enumy.ElementySprawozdaniafin;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SprawozdanieFinansoweView implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String wybranyrok;
    @Inject
    private SprawozdanieFinansowe sprawozdanieFinansowe;
    private List elementy;

    public SprawozdanieFinansoweView() {
        
    }

    @PostConstruct
    private void init() {
        wybranyrok = wpisView.getRokWpisuSt();
        elementy = new ArrayList<>();
        Class c = ElementySprawozdaniafin.class;
        Field[] p = c.getFields();
        for (Field r : p) {
            try {
                elementy.add(r.getName());
            } catch (Exception ex) {
            }
        }
    }
    
    public int strtoint(String el) {
        int zwrot = 0;
        Class c = ElementySprawozdaniafin.class;
        Field[] p = c.getFields();
        for (Field r : p) {
            try {
                if (r.getName().equals(el)) {
                    zwrot = r.getInt(r);
                    break;
                }
            } catch (Exception ex) {
            }
        }
        return zwrot;
    }
    
    public void dodaj() {
        sprawozdanieFinansowe.setPodatnik(wpisView.getPodatnikObiekt());
        sprawozdanieFinansowe.setRok(wpisView.getRokWpisuSt());
        sprawozdanieFinansowe.setSporzadzajacy(wpisView.getWprowadzil().getLogin());
        Msg.dP();
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getWybranyrok() {
        return wybranyrok;
    }

    public void setWybranyrok(String wybranyrok) {
        this.wybranyrok = wybranyrok;
    }

    public SprawozdanieFinansowe getSprawozdanieFinansowe() {
        return sprawozdanieFinansowe;
    }

    public void setSprawozdanieFinansowe(SprawozdanieFinansowe sprawozdanieFinansowe) {
        this.sprawozdanieFinansowe = sprawozdanieFinansowe;
    }

    public List getElementy() {
        return elementy;
    }

    public void setElementy(List elementy) {
        this.elementy = elementy;
    }
    
    
    
    
}
