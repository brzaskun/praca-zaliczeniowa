/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Bean")
@ViewScoped
public class Bean {

   private boolean warunek;
   private String pole;

    public boolean isWarunek() {
        return warunek;
    }

    public void setWarunek(boolean warunek) {
        this.warunek = warunek;
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
