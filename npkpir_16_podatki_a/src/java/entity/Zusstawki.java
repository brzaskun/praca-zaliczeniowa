/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "zusstawki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusstawki.findAll", query = "SELECT z FROM Zusstawki z"),
    @NamedQuery(name = "Zusstawki.findById", query = "SELECT z FROM Zusstawki z WHERE z.id = :id"),
    @NamedQuery(name = "Zusstawki.findByMiesiac", query = "SELECT z FROM Zusstawki z WHERE z.miesiac = :miesiac"),
    @NamedQuery(name = "Zusstawki.findByRok", query = "SELECT z FROM Zusstawki z WHERE z.rok = :rok"),
    @NamedQuery(name = "Zusstawki.findByZus51", query = "SELECT z FROM Zusstawki z WHERE z.zus51 = :zus51"),
    @NamedQuery(name = "Zusstawki.findByZus52", query = "SELECT z FROM Zusstawki z WHERE z.zus52 = :zus52"),
    @NamedQuery(name = "Zusstawki.findByZus53", query = "SELECT z FROM Zusstawki z WHERE z.zus53 = :zus53")})
public class Zusstawki implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "miesiac")
    private String miesiac;
    @Size(max = 255)
    @Column(name = "rok")
    private String rok;
    @Column(name = "zus51")
    private Double zus51;
    @Column(name = "zus52")
    private Double zus52;
    @Column(name = "zus53")
    private Double zus53;

    public Zusstawki() {
    }

    public Zusstawki(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public Double getZus51() {
        return zus51;
    }

    public void setZus51(Double zus51) {
        this.zus51 = zus51;
    }

    public Double getZus52() {
        return zus52;
    }

    public void setZus52(Double zus52) {
        this.zus52 = zus52;
    }

    public Double getZus53() {
        return zus53;
    }

    public void setZus53(Double zus53) {
        this.zus53 = zus53;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zusstawki)) {
            return false;
        }
        Zusstawki other = (Zusstawki) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zusstawki[ id=" + id + " ]";
    }
    
}
