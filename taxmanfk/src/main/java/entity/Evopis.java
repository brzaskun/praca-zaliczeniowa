/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "evopis",uniqueConstraints = {
    @UniqueConstraint(columnNames={"opis"})
})
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
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.opis);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evopis other = (Evopis) obj;
        if (!Objects.equals(this.opis, other.opis)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return opis ;
    }
    
}
