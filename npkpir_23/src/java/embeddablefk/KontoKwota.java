/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Konto;
import entityfk.PozycjaRZiSBilans;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Osito
 */
public class KontoKwota implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Konto konto;
    private double Kwota;
    private PozycjaRZiSBilans pozycjaRZiS;

    public KontoKwota(Konto konto, double Kwota) {
        this.konto = konto;
        this.Kwota = Kwota;
    }

    public KontoKwota() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.konto);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.Kwota) ^ (Double.doubleToLongBits(this.Kwota) >>> 32));
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
        final KontoKwota other = (KontoKwota) obj;
        if (!Objects.equals(this.konto, other.konto)) {
            return false;
        }
        if (Double.doubleToLongBits(this.Kwota) != Double.doubleToLongBits(other.Kwota)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KontoKwota{" + "konto=" + konto + ", Kwota=" + Kwota + '}';
    }
    
    
    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getKwota() {
        return Kwota;
    }

    public void setKwota(double Kwota) {
        this.Kwota = Kwota;
    }
    
    
    
}
