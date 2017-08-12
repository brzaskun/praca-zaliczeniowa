/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import viewfk.KontaVatFKView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VatZmianaOkresuView implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{ewidencjaVatView}")
    private EwidencjaVatView ewidencjaVatView;
    @ManagedProperty(value = "#{kontaVatFKView}")
    private KontaVatFKView kontaVatFKView;
    
    public void aktualizujpozmianiedaty() {
        ewidencjaVatView.stworzenieEwidencjiZDokumentowFK();
        kontaVatFKView.init();
    }

    public EwidencjaVatView getEwidencjaVatView() {
        return ewidencjaVatView;
    }

    public void setEwidencjaVatView(EwidencjaVatView ewidencjaVatView) {
        this.ewidencjaVatView = ewidencjaVatView;
    }

    public KontaVatFKView getKontaVatFKView() {
        return kontaVatFKView;
    }

    public void setKontaVatFKView(KontaVatFKView kontaVatFKView) {
        this.kontaVatFKView = kontaVatFKView;
    }
    
    
}
