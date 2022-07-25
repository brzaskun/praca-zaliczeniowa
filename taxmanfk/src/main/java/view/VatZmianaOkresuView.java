/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Podatnik;
import error.E;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import viewfk.KontaVatFKView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class VatZmianaOkresuView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private EwidencjaVatView ewidencjaVatView;
    @Inject
    private KontaVatFKView kontaVatFKView;
    
    public void aktualizujpozmianiedaty(Podatnik podatnik) {
        try {
            ewidencjaVatView.stworzenieEwidencjiZDokumentowFK(podatnik, null);
            kontaVatFKView.init();
        } catch (Exception e) {
            E.e(e);
        }
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
