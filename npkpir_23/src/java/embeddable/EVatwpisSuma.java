/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Evewidencja;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class EVatwpisSuma implements Serializable {
    @Inject private Evewidencja ewidencja;
    private BigDecimal netto;
    private BigDecimal vat;
    private String estawka;
    private boolean niesumuj;

    public EVatwpisSuma(Evewidencja ewidencja, BigDecimal netto, BigDecimal vat, String estawka) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
    }

    public EVatwpisSuma() {
    }

    public boolean isNiesumuj() {
        return niesumuj;
    }

    public void setNiesumuj(boolean niesumuj) {
        this.niesumuj = niesumuj;
    }

    

    public Evewidencja getEwidencja() {
        return ewidencja;
    }

    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
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
        return "EVatwpisSuma{" + "ewidencja=" + ewidencja.getNazwa() + ",pole netto "+ ewidencja.getNrpolanetto() + ", netto=" + netto + ", vat=" + vat + ", estawka=" + estawka + '}';
    }

    
   
    
}
