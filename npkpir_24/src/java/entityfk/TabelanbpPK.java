/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Embeddable
public class TabelanbpPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(nullable = false, length = 25)
    private String nrtabeli;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(nullable = false, length = 3)
    private String symbolwaluty;

    public TabelanbpPK() {
    }

    public TabelanbpPK(String nrtabeli, String symbolwaluty) {
        this.nrtabeli = nrtabeli;
        this.symbolwaluty = symbolwaluty;
    }

    public String getNrtabeli() {
        return nrtabeli;
    }

    public void setNrtabeli(String nrtabeli) {
        this.nrtabeli = nrtabeli;
    }

    public String getSymbolwaluty() {
        return symbolwaluty;
    }

    public void setSymbolwaluty(String symbolwaluty) {
        this.symbolwaluty = symbolwaluty;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nrtabeli != null ? nrtabeli.hashCode() : 0);
        hash += (symbolwaluty != null ? symbolwaluty.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TabelanbpPK)) {
            return false;
        }
        TabelanbpPK other = (TabelanbpPK) object;
        if ((this.nrtabeli == null && other.nrtabeli != null) || (this.nrtabeli != null && !this.nrtabeli.equals(other.nrtabeli))) {
            return false;
        }
        if ((this.symbolwaluty == null && other.symbolwaluty != null) || (this.symbolwaluty != null && !this.symbolwaluty.equals(other.symbolwaluty))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.TabelanbpPK[ nrtabeli=" + nrtabeli + ", symbolwaluty=" + symbolwaluty + " ]";
    }
    
}
