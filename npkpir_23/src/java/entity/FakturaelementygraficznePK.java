/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
public class FakturaelementygraficznePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "podatnik", nullable = false, length = 250)
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nazwaelementu", nullable = false, length = 150)
    private String nazwaelementu;

    public FakturaelementygraficznePK() {
    }

    public FakturaelementygraficznePK(String podatnik, String nazwaelementu) {
        this.podatnik = podatnik;
        this.nazwaelementu = nazwaelementu;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getNazwaelementu() {
        return nazwaelementu;
    }

    public void setNazwaelementu(String nazwaelementu) {
        this.nazwaelementu = nazwaelementu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (podatnik != null ? podatnik.hashCode() : 0);
        hash += (nazwaelementu != null ? nazwaelementu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FakturaelementygraficznePK)) {
            return false;
        }
        FakturaelementygraficznePK other = (FakturaelementygraficznePK) object;
        if ((this.podatnik == null && other.podatnik != null) || (this.podatnik != null && !this.podatnik.equals(other.podatnik))) {
            return false;
        }
        if ((this.nazwaelementu == null && other.nazwaelementu != null) || (this.nazwaelementu != null && !this.nazwaelementu.equals(other.nazwaelementu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FakturadodelementyPK[ podatnik=" + podatnik + ", nazwaelementu=" + nazwaelementu + " ]";
    }
    
}
