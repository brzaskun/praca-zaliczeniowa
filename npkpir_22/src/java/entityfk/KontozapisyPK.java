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
public class KontozapisyPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "podatnik", nullable = false, length = 255)
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "konto", nullable = false, length = 100)
    private String konto;

    public KontozapisyPK() {
    }

    public KontozapisyPK(String podatnik, String konto) {
        this.podatnik = podatnik;
        this.konto = konto;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getKonto() {
        return konto;
    }

    public void setKonto(String konto) {
        this.konto = konto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (podatnik != null ? podatnik.hashCode() : 0);
        hash += (konto != null ? konto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KontozapisyPK)) {
            return false;
        }
        KontozapisyPK other = (KontozapisyPK) object;
        if ((this.podatnik == null && other.podatnik != null) || (this.podatnik != null && !this.podatnik.equals(other.podatnik))) {
            return false;
        }
        if ((this.konto == null && other.konto != null) || (this.konto != null && !this.konto.equals(other.konto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.KontozapisyPK[ podatnik=" + podatnik + ", konto=" + konto + " ]";
    }
    
}
