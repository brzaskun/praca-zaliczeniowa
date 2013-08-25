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

/**
 *
 * @author Osito
 */
@Embeddable
public class RozrachunkiPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int zapisrozliczany;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int zapissparowany;

    public RozrachunkiPK() {
    }

    public RozrachunkiPK(int zapisrozliczany, int zapissparowany) {
        this.zapisrozliczany = zapisrozliczany;
        this.zapissparowany = zapissparowany;
    }

    public int getZapisrozliczany() {
        return zapisrozliczany;
    }

    public void setZapisrozliczany(int zapisrozliczany) {
        this.zapisrozliczany = zapisrozliczany;
    }

    public int getZapissparowany() {
        return zapissparowany;
    }

    public void setZapissparowany(int zapissparowany) {
        this.zapissparowany = zapissparowany;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) zapisrozliczany;
        hash += (int) zapissparowany;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RozrachunkiPK)) {
            return false;
        }
        RozrachunkiPK other = (RozrachunkiPK) object;
        if (this.zapisrozliczany != other.zapisrozliczany) {
            return false;
        }
        if (this.zapissparowany != other.zapissparowany) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RozrachunkiPK[ zapisrozliczany=" + zapisrozliczany + ", zapissparowany=" + zapissparowany + " ]";
    }
    
}
