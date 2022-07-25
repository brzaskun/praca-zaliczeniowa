/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class EVatwpisSuma1 implements Serializable {
    private BigDecimal netto;
    private BigDecimal vat;
    private String estawka;

    public EVatwpisSuma1(BigDecimal netto, BigDecimal vat, String estawka) {
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
    }

    public EVatwpisSuma1() {
    }

    
 
    public BigDecimal getNetto() {
        return netto;
    }

    public void setNetto(BigDecimal netto) {
        this.netto = netto;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public String getEstawka() {
        return estawka;
    }

    public void setEstawka(String estawka) {
        this.estawka = estawka;
    }

    
    
    @Override
    public String toString() {
        return "EVatwpisSuma{" + ",pole netto " + ", netto=" + netto + ", vat=" + vat + ", estawka=" + estawka + '}';
    }

    
   
    
}
