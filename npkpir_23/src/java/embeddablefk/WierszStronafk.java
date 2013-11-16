/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Konto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

/**
 *
 * @author Osito
 */
@Embeddable
public class WierszStronafk implements Serializable {
    
    @EmbeddedId
    protected WierszStronafkPK wierszStronafkPK = new WierszStronafkPK();
    private double kwota;
    private Konto konto;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.wierszStronafkPK);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.kwota) ^ (Double.doubleToLongBits(this.kwota) >>> 32));
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
        final WierszStronafk other = (WierszStronafk) obj;
        if (!Objects.equals(this.wierszStronafkPK, other.wierszStronafkPK)) {
            return false;
        }
        if (Double.doubleToLongBits(this.kwota) != Double.doubleToLongBits(other.kwota)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WierszStronafk{" + "wierszStronafkPK=" + wierszStronafkPK + ", kwota=" + kwota + '}';
    }

    public WierszStronafkPK getWierszStronafkPK() {
        return wierszStronafkPK;
    }

    public void setWierszStronafkPK(WierszStronafkPK wierszStronafkPK) {
        this.wierszStronafkPK = wierszStronafkPK;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }
    
    
    
    
}
