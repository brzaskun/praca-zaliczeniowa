/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class WierszStronafkPK implements Serializable{
    private String typdokumentu;
    private int nrkolejnydokumentu;
    private int nrPorzadkowyWiersza;
    private String stronaWnlubMa;

    public WierszStronafkPK() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.typdokumentu);
        hash = 11 * hash + this.nrkolejnydokumentu;
        hash = 11 * hash + this.nrPorzadkowyWiersza;
        hash = 11 * hash + Objects.hashCode(this.stronaWnlubMa);
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
        final WierszStronafkPK other = (WierszStronafkPK) obj;
        if (!Objects.equals(this.typdokumentu, other.typdokumentu)) {
            return false;
        }
        if (this.nrkolejnydokumentu != other.nrkolejnydokumentu) {
            return false;
        }
        if (this.nrPorzadkowyWiersza != other.nrPorzadkowyWiersza) {
            return false;
        }
        if (!Objects.equals(this.stronaWnlubMa, other.stronaWnlubMa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WierszStronafkPK{" + "typdokumentu=" + typdokumentu + ", nrkolejnydokumentu=" + nrkolejnydokumentu + ", nrPorzadkowyWiersza=" + nrPorzadkowyWiersza + ", stronaWnlubMa=" + stronaWnlubMa + '}';
    }

    public String getTypdokumentu() {
        return typdokumentu;
    }

    public void setTypdokumentu(String typdokumentu) {
        this.typdokumentu = typdokumentu;
    }

    public int getNrkolejnydokumentu() {
        return nrkolejnydokumentu;
    }

    public void setNrkolejnydokumentu(int nrkolejnydokumentu) {
        this.nrkolejnydokumentu = nrkolejnydokumentu;
    }

    public int getNrPorzadkowyWiersza() {
        return nrPorzadkowyWiersza;
    }

    public void setNrPorzadkowyWiersza(int nrPorzadkowyWiersza) {
        this.nrPorzadkowyWiersza = nrPorzadkowyWiersza;
    }

    public String getStronaWnlubMa() {
        return stronaWnlubMa;
    }

    public void setStronaWnlubMa(String stronaWnlubMa) {
        this.stronaWnlubMa = stronaWnlubMa;
    }
    
    
    
}
