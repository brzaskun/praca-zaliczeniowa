/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class PlatnosciPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ROK")
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MIESIAC")
    private String miesiac;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PODATNIK")
    private String podatnik;

    public PlatnosciPK() {
        
    }

    public PlatnosciPK(String rok, String miesiac, String podatnik) {
        this.rok = rok;
        this.miesiac = miesiac;
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rok != null ? rok.hashCode() : 0);
        hash += (miesiac != null ? miesiac.hashCode() : 0);
        hash += (podatnik != null ? podatnik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlatnosciPK)) {
            return false;
        }
        PlatnosciPK other = (PlatnosciPK) object;
        if ((this.rok == null && other.rok != null) || (this.rok != null && !this.rok.equals(other.rok))) {
            return false;
        }
        if ((this.miesiac == null && other.miesiac != null) || (this.miesiac != null && !this.miesiac.equals(other.miesiac))) {
            return false;
        }
        if ((this.podatnik == null && other.podatnik != null) || (this.podatnik != null && !this.podatnik.equals(other.podatnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PlatnosciPK[ rok=" + rok + ", miesiac=" + miesiac + ", podatnik=" + podatnik + " ]";
    }
    
}
