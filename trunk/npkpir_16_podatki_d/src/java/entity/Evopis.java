/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "evopis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evopis.findAll", query = "SELECT e FROM Evopis e"),
    @NamedQuery(name = "Evopis.findByLp", query = "SELECT e FROM Evopis e WHERE e.lp = :lp"),
    @NamedQuery(name = "Evopis.findByOpis", query = "SELECT e FROM Evopis e WHERE e.opis = :opis")})
public class Evopis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lp")
    private Integer lp;
    @Size(max = 255)
    @Column(name = "opis")
    private String opis;

    public Evopis() {
    }

    public Evopis(Integer lp) {
        this.lp = lp;
    }

    public Integer getLp() {
        return lp;
    }

    public void setLp(Integer lp) {
        this.lp = lp;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lp != null ? lp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evopis)) {
            return false;
        }
        Evopis other = (Evopis) object;
        if ((this.lp == null && other.lp != null) || (this.lp != null && !this.lp.equals(other.lp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Evopis[ lp=" + lp + " ]";
    }
    
}
