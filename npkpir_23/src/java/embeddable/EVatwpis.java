/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Evewidencja;
import java.io.Serializable;
import java.util.Objects;
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
    private static final long serialVersionUID = -3177617915718651903L;
    @Inject
    private Evewidencja ewidencja;
    private double netto;
    private double vat;
    private String estawka;

    public EVatwpis(Evewidencja ewidencja, double netto, double vat, String estawka) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
    }

    public EVatwpis() {
    }

    public Evewidencja getEwidencja() {
        return ewidencja;
    }

    public void setEwidencja(Evewidencja ewidencja) {
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

    public String getEstawka() {
        return estawka;
    }

    public void setEstawka(String estawka) {
        this.estawka = estawka;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.ewidencja);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.netto) ^ (Double.doubleToLongBits(this.netto) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.vat) ^ (Double.doubleToLongBits(this.vat) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.estawka);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EVatwpis other = (EVatwpis) obj;
        if (!Objects.equals(this.ewidencja, other.ewidencja)) {
            return false;
        }
        if (Double.doubleToLongBits(this.netto) != Double.doubleToLongBits(other.netto)) {
            return false;
        }
        if (Double.doubleToLongBits(this.vat) != Double.doubleToLongBits(other.vat)) {
            return false;
        }
        if (!Objects.equals(this.estawka, other.estawka)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EVatwpis{" + "ewidencja=" + ewidencja.getNazwa() + ", netto=" + netto + ", vat=" + vat + ", estawka=" + estawka + '}';
    }

   
    
}
