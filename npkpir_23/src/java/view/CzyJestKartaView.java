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
public class CzyJestKartaView   implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean moznapodpisywac;
    
    @PostConstruct
    private void init() {
         moznapodpisywac = ObslugaPodpisuBean.moznapodpisacjpk(null);
    }

    public boolean isMoznapodpisywac() {
        return moznapodpisywac;
    }

    public void setMoznapodpisywac(boolean moznapodpisywac) {
        this.moznapodpisywac = moznapodpisywac;
    }
    
    
}
