/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.ObslugaPodpisuBean;
import beansPodpis.Xad;
import error.E;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PodpisView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private boolean jestkarta;

    public void sprawdzczymozna(WpisView wpisView) {
        if (wpisView.getPodatnikObiekt().isPodpiscertyfikowany()) {
            jestkarta  = ObslugaPodpisuBean.moznaPodpisac(wpisView.getPodatnikObiekt().getKartacert(), wpisView.getPodatnikObiekt().getKartapesel());
        } else {
            jestkarta = false;
        }
    }
    
    public Object[] podpiszDeklaracje(String xml, WpisView wpisView) {
        Object[] deklaracjapodpisana = null;
        try {
            deklaracjapodpisana = Xad.podpisz(xml,wpisView.getPodatnikObiekt().getKartacert(), wpisView.getPodatnikObiekt().getKartapesel());
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
