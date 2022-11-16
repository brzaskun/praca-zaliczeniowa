/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
@Table(name = "limitdochodudwaszesc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Limitdochodudwaszesc.findAll", query = "SELECT l FROM Limitdochodudwaszesc l"),
    @NamedQuery(name = "Limitdochodudwaszesc.findById", query = "SELECT l FROM Limitdochodudwaszesc l WHERE l.id = :id"),
    @NamedQuery(name = "Limitdochodudwaszesc.findByKwota", query = "SELECT l FROM Limitdochodudwaszesc l WHERE l.kwota = :kwota"),
    @NamedQuery(name = "Limitdochodudwaszesc.findByRok", query = "SELECT l FROM Limitdochodudwaszesc l WHERE l.rok = :rok")})
public class Limitdochodudwaszesc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private Double kwota;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;

    public Limitdochodudwaszesc() {
    }

    public Limitdochodudwaszesc(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getKwota() {
        return kwota;
    }

    public void setKwota(Double kwota) {
        this.kwota = kwota;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Limitdochodudwaszesc)) {
            return false;
        }
        Limitdochodudwaszesc other = (Limitdochodudwaszesc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Limitdochodudwaszesc[ id=" + id + " ]";
    }
    
}
