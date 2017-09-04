/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.ObslugaPodpisuBean;
import java.io.Serializable;
import javax.annotation.PostConstruct;
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
    
    @PostConstruct
    private void init() {
        sprawdzczymozna();
    }

    public void sprawdzczymozna() {
        jestkarta  = ObslugaPodpisuBean.moznaPodpisac();
    }
    
    
    public boolean isJestkarta() {
        return jestkarta;
    }

    public void setJestkarta(boolean jestkarta) {
        this.jestkarta = jestkarta;
    }
    
    
}
