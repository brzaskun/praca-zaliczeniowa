/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Konto;
import entityfk.PozycjaRZiS;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class KontoKwota implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Konto konto;
    private double kwota;
    private double kwota2;
    private PozycjaRZiS pozycjaRZiS;

    public KontoKwota(Konto konto, double kwotakonto) {
        this.konto = konto;
        this.kwota = kwotakonto;
    }

    public KontoKwota() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.konto);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.kwota) ^ (Double.doubleToLongBits(this.kwota) >>> 32));
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
        return true;
    }

    
    @Override
    public String toString() {
        return konto.getPelnynumer() + " ";
    }
    
    
    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double Kwota) {
        this.kwota = Kwota;
    }

    public double getKwota2() {
        return kwota2;
    }

    public void setKwota2(double kwota2) {
        this.kwota2 = kwota2;
    }
    
    
    
}
