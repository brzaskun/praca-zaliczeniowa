/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.StronaWiersza;
import entityfk.PozycjaRZiS;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class StronaWierszaKwota implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private StronaWiersza stronawiersza;
    private double kwota;
    private PozycjaRZiS pozycjaRZiS;

    public StronaWierszaKwota(StronaWiersza stronawiersza, double kwota) {
        this.stronawiersza = stronawiersza;
        this.kwota = kwota;
    }

    public StronaWierszaKwota() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.stronawiersza);
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
        final StronaWierszaKwota other = (StronaWierszaKwota) obj;
        if (!Objects.equals(this.stronawiersza, other.stronawiersza)) {
            return false;
        }
        if (Double.doubleToLongBits(this.kwota) != Double.doubleToLongBits(other.kwota)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StronaWierszaKwota{" + "stronawiersza=" + stronawiersza + ", kwota=" + kwota + '}';
    }
    
    
    public StronaWiersza getStronaWiersza() {
        return stronawiersza;
    }

    public void setStronaWiersza(StronaWiersza stronawiersza) {
        this.stronawiersza = stronawiersza;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double Kwota) {
        this.kwota = Kwota;
    }
    
    
    
}
