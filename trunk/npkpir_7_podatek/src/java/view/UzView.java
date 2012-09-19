/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Uz;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Osito
 */
@ManagedBean(name="UzytkownikView")
@SessionScoped
public class UzView implements Serializable{
    
    private Uz selUzytkownik;
    private boolean  dodajUz;
    private boolean edytujUz;
    

    public UzView() {
        selUzytkownik = new Uz();
        setDodajUz(false);
        setEdytujUz(false);
    }

    public UzView(Uz selUzytkownik) {
        this.selUzytkownik = selUzytkownik;
    }

   
    public Uz getSelUzytkownik() {
        return selUzytkownik;
    }

    public void setSelUzytkownik(Uz selUzytkownik) {
        this.selUzytkownik = selUzytkownik;
    }

    public boolean isDodajUz() {
        return dodajUz;
    }

    public void setDodajUz(boolean dodajUz) {
        this.dodajUz = dodajUz;
    }

    public boolean isEdytujUz() {
        return edytujUz;
    }

    public void setEdytujUz(boolean edytujUz) {
        this.edytujUz = edytujUz;
    }

   

  
   
}
