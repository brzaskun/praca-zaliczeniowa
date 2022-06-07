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
@Table(name = "KODSWPRZ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kodswprz.findAll", query = "SELECT k FROM Kodswprz k"),
    @NamedQuery(name = "Kodswprz.findByKodswprz", query = "SELECT k FROM Kodswprz k WHERE k.kodswprz = :kodswprz")})
public class Kodswprz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "KODSWPRZ", nullable = false, length = 3)
    private String kodswprz;

    public Kodswprz() {
    }

    public Kodswprz(String kodswprz) {
        this.kodswprz = kodswprz;
    }

    public String getKodswprz() {
        return kodswprz;
    }

    public void setKodswprz(String kodswprz) {
        this.kodswprz = kodswprz;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodswprz != null ? kodswprz.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kodswprz)) {
            return false;
        }
        Kodswprz other = (Kodswprz) object;
        if ((this.kodswprz == null && other.kodswprz != null) || (this.kodswprz != null && !this.kodswprz.equals(other.kodswprz))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Kodswprz[ kodswprz=" + kodswprz + " ]";
    }
    
}
