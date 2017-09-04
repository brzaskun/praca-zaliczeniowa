/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.ObslugaPodpisuBean;
import error.E;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PodpisView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private boolean jestkarta;

    public void sprawdzczymozna() {
        jestkarta  = ObslugaPodpisuBean.moznaPodpisac();
    }
    
    public String podpiszDeklaracje(String xml) {
        String deklaracjapodpisana = xml;
        try {
            deklaracjapodpisana = ObslugaPodpisuBean.podpiszDeklaracje(xml);
        } catch (Exception e) {
            E.e(e);
        }
        return deklaracjapodpisana;
    }
    
    
    public boolean isJestkarta() {
        return jestkarta;
    }

    public void setJestkarta(boolean jestkarta) {
        this.jestkarta = jestkarta;
    }
    
    
}
