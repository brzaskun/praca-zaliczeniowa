/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;



import java.io.IOException;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import view.WpisView;
import wydajnosc.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class GuestFKView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    

    public GuestFKView() {
         ////E.m(this);
    }
    
    public void aktualizujGuestFK() throws IOException {
        aktualizujGuest();
        aktualizuj();
    }
    
    public void aktualizujGuest(){
        wpisView.naniesDaneDoWpis();
    }
    
     private void aktualizuj(){
        wpisView.naniesDaneDoWpis();
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}

