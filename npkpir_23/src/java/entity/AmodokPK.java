/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Mce;
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
public class AmodokPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "mc")
    private int mc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "podatnik")
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rok")
    private int rok;

    public AmodokPK() {
    }

    public AmodokPK(int mc, String podatnik, int rok) {
        this.mc = mc;
        this.podatnik = podatnik;
        this.rok = rok;
    }

    public String getMcString() {
        return Mce.getNumberToMiesiac().get(this.mc);
    }
    
    public int getMc() {
        return mc;
    }

    public void setMc(int mc) {
        this.mc = mc;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) mc;
        hash += (int) rok;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmodokPK)) {
            return false;
        }
        AmodokPK other = (AmodokPK) object;
        if (this.mc != other.mc) {
            return false;
        }
        if ((this.podatnik == null && other.podatnik != null) || (this.podatnik != null && !this.podatnik.equals(other.podatnik))) {
            return false;
        }
        if (this.rok != other.rok) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AmodokPK[ mc=" + mc + ", podatnik=" + podatnik + ", rok=" + rok + " ]";
    }
    
}
