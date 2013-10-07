/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zmianatablicy.findAll", query = "SELECT z FROM Zmianatablicy z"),
    @NamedQuery(name = "Zmianatablicy.findByNazwatablicy", query = "SELECT z FROM Zmianatablicy z WHERE z.nazwatablicy = :nazwatablicy"),
    @NamedQuery(name = "Zmianatablicy.findByZmiana", query = "SELECT z FROM Zmianatablicy z WHERE z.zmiana = :zmiana")})
public class Zmianatablicy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwatablicy;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean zmiana;

    public Zmianatablicy() {
    }

    public Zmianatablicy(String nazwatablicy) {
        this.nazwatablicy = nazwatablicy;
    }

    public Zmianatablicy(String nazwatablicy, boolean zmiana) {
        this.nazwatablicy = nazwatablicy;
        this.zmiana = zmiana;
    }

    public String getNazwatablicy() {
        return nazwatablicy;
    }

    public void setNazwatablicy(String nazwatablicy) {
        this.nazwatablicy = nazwatablicy;
    }

    public boolean getZmiana() {
        return zmiana;
    }

    public void setZmiana(boolean zmiana) {
        this.zmiana = zmiana;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nazwatablicy != null ? nazwatablicy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zmianatablicy)) {
            return false;
        }
        Zmianatablicy other = (Zmianatablicy) object;
        if ((this.nazwatablicy == null && other.nazwatablicy != null) || (this.nazwatablicy != null && !this.nazwatablicy.equals(other.nazwatablicy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zmianatablicy[ nazwatablicy=" + nazwatablicy + " ]";
    }
    
}
