/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class WartosciVAT  implements Serializable {
    private static final long serialVersionUID = 1L;

    double netto;
    double vat;
    double nettowWalucie;
    double vatwWalucie;
    double vatPlndodoliczenia;
    double vatPlnkup;
    double vatWalutadodoliczenia;
    double vatWalutakup;

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

    public double getNettowWalucie() {
        return nettowWalucie;
    }

    public void setNettowWalucie(double nettowWalucie) {
        this.nettowWalucie = nettowWalucie;
    }

    public double getVatwWalucie() {
        return vatwWalucie;
    }

    public void setVatwWalucie(double vatwWalucie) {
        this.vatwWalucie = vatwWalucie;
    }

    public double getVatPlndodoliczenia() {
        return vatPlndodoliczenia;
    }

    public void setVatPlndodoliczenia(double vatPlndodoliczenia) {
        this.vatPlndodoliczenia = vatPlndodoliczenia;
    }

    public double getVatPlnkup() {
        return vatPlnkup;
    }

    public void setVatPlnkup(double vatPlnkup) {
        this.vatPlnkup = vatPlnkup;
    }

    public double getVatWalutadodoliczenia() {
        return vatWalutadodoliczenia;
    }

    public void setVatWalutadodoliczenia(double vatWalutadodoliczenia) {
        this.vatWalutadodoliczenia = vatWalutadodoliczenia;
    }

    public double getVatWalutakup() {
        return vatWalutakup;
    }

    public void setVatWalutakup(double vatWalutakup) {
        this.vatWalutakup = vatWalutakup;
    }
    
    
    
}
