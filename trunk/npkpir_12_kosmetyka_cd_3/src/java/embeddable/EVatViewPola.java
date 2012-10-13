/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named(value="EVatViewPola")
public class EVatViewPola {
    private String opis;
    private double netto;
    private double vat;

    public EVatViewPola() {
    }

    public EVatViewPola(String opis, double netto, double vat) {
        this.opis = opis;
        this.netto = netto;
        this.vat = vat;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }
    
    
    
}
