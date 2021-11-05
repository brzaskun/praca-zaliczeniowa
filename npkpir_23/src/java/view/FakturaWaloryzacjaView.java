/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.FakturaWaloryzacja;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FakturaWaloryzacjaView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private FakturaWaloryzacja selected;

    public FakturaWaloryzacja getSelected() {
        return selected;
    }

    public void setSelected(FakturaWaloryzacja selected) {
        this.selected = selected;
    }
    
    
    
}
