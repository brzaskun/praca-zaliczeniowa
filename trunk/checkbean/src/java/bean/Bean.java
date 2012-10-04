/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
    
    public void metoda(){
         FacesMessage msg = new FacesMessage("Nowy srodek zachowany");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
