/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.ObslugaPodpisuBean;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class CzyJestKartaView   implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean moznapodpisywac;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String innehaslo;
    
    
    public void init() {
         moznapodpisywac = ObslugaPodpisuBean.moznapodpisacjpk(innehaslo);
    }

    public boolean isMoznapodpisywac() {
        return moznapodpisywac;
    }

    public void setMoznapodpisywac(boolean moznapodpisywac) {
        this.moznapodpisywac = moznapodpisywac;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public String getInnehaslo() {
        return innehaslo;
    }

    public void setInnehaslo(String haslo) {
        this.innehaslo = haslo;
    }
    
    
}
