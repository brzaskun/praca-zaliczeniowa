/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

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
@Table(name = "KODTYTUB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kodtytub.findAll", query = "SELECT k FROM Kodtytub k"),
    @NamedQuery(name = "Kodtytub.findByKodtytub", query = "SELECT k FROM Kodtytub k WHERE k.kodtytub = :kodtytub")})
public class Kodtytub implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "KODTYTUB", nullable = false, length = 6)
    private String kodtytub;

    public Kodtytub() {
    }

    public Kodtytub(String kodtytub) {
        this.kodtytub = kodtytub;
    }

    public String getKodtytub() {
        return kodtytub;
    }

    public void setKodtytub(String kodtytub) {
        this.kodtytub = kodtytub;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodtytub != null ? kodtytub.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kodtytub)) {
            return false;
        }
        Kodtytub other = (Kodtytub) object;
        if ((this.kodtytub == null && other.kodtytub != null) || (this.kodtytub != null && !this.kodtytub.equals(other.kodtytub))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Kodtytub[ kodtytub=" + kodtytub + " ]";
    }
    
}
