/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Bean")
@SessionScoped
public class Bean {

   private boolean pokaz;
   private String pole;

    public boolean isPokaz() {
        return pokaz;
    }

    public void setPokaz(boolean pokaz) {
        this.pokaz = pokaz;
    }

    public String getPole() {
        return pole;
    }

    public void setPole(String pole) {
        this.pole = pole;
    }
   
    
    public Bean() {
    }
}
