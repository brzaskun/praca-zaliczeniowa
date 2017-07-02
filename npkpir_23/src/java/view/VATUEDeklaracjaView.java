/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import deklaracje.vatue.m4.Deklaracja;
import deklaracje.vatue.m4.VATUEM4Bean;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VATUEDeklaracjaView implements Serializable {
    private static final long serialVersionUID = 1L;
    private deklaracje.vatue.m4.Deklaracja deklaracja;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    
    public void tworzdeklarajce() {
        deklaracja = new Deklaracja();
        deklaracja.setNaglowek(VATUEM4Bean.tworznaglowek("03","2017","22"));
    }

   
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Deklaracja getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(Deklaracja deklaracja) {
        this.deklaracja = deklaracja;
    }

    
    
    
}
