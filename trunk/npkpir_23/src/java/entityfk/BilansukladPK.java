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
public class BilansukladPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String uklad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(nullable = false, length = 4)
    private String rok;

    public BilansukladPK() {
    }

    public BilansukladPK(String uklad, String podatnik, String rok) {
        this.uklad = uklad;
        this.podatnik = podatnik;
        this.rok = rok;
    }

    public String getUklad() {
        return uklad;
    }

    public void setUklad(String uklad) {
        this.uklad = uklad;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uklad != null ? uklad.hashCode() : 0);
        hash += (podatnik != null ? podatnik.hashCode() : 0);
        hash += (rok != null ? rok.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BilansukladPK)) {
            return false;
        }
        BilansukladPK other = (BilansukladPK) object;
        if ((this.uklad == null && other.uklad != null) || (this.uklad != null && !this.uklad.equals(other.uklad))) {
            return false;
        }
        if ((this.podatnik == null && other.podatnik != null) || (this.podatnik != null && !this.podatnik.equals(other.podatnik))) {
            return false;
        }
        if ((this.rok == null && other.rok != null) || (this.rok != null && !this.rok.equals(other.rok))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BilansukladPK{" + "uklad=" + uklad + ", podatnik=" + podatnik + ", rok=" + rok + '}';
    }

  
}
