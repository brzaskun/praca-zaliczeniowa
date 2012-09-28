/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class EVatwpis implements Serializable {
    @Inject
    private EVidencja ewidencja;
    private double netto;
    private double vat;
    @Inject
    private EStawka estawka;

    public EVidencja getEwidencja() {
        return ewidencja;
    }

    public void setEwidencja(EVidencja ewidencja) {
        this.ewidencja = ewidencja;
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

    public EStawka getEstawka() {
        return estawka;
    }

    public void setEstawka(EStawka estawka) {
        this.estawka = estawka;
    }
    
}
