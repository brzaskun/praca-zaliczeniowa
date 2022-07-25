/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "miesiac", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Miesiac.findAll", query = "SELECT m FROM Miesiac m"),
    @NamedQuery(name = "Miesiac.findByMieNumer", query = "SELECT m FROM Miesiac m WHERE m.mieNumer = :mieNumer"),
    @NamedQuery(name = "Miesiac.findByMieNazwa", query = "SELECT m FROM Miesiac m WHERE m.mieNazwa = :mieNazwa")})
public class Miesiac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mie_numer", nullable = false)
    private Short mieNumer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "mie_nazwa", nullable = false, length = 16)
    private String mieNazwa;

    public Miesiac() {
    }

    public Miesiac(Short mieNumer) {
        this.mieNumer = mieNumer;
    }

    public Miesiac(Short mieNumer, String mieNazwa) {
        this.mieNumer = mieNumer;
        this.mieNazwa = mieNazwa;
    }

    public Short getMieNumer() {
        return mieNumer;
    }

    public void setMieNumer(Short mieNumer) {
        this.mieNumer = mieNumer;
    }

    public String getMieNazwa() {
        return mieNazwa;
    }

    public void setMieNazwa(String mieNazwa) {
        this.mieNazwa = mieNazwa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mieNumer != null ? mieNumer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Miesiac)) {
            return false;
        }
        Miesiac other = (Miesiac) object;
        if ((this.mieNumer == null && other.mieNumer != null) || (this.mieNumer != null && !this.mieNumer.equals(other.mieNumer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Miesiac[ mieNumer=" + mieNumer + " ]";
    }
    
}
