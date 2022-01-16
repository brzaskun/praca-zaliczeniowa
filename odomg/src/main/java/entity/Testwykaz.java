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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "testwykaz", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nazwa"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Testwykaz.findAll", query = "SELECT t FROM Testwykaz t"),
    @NamedQuery(name = "Testwykaz.findById", query = "SELECT t FROM Testwykaz t WHERE t.id = :id"),
    @NamedQuery(name = "Testwykaz.findByNazwa", query = "SELECT t FROM Testwykaz t WHERE t.nazwa = :nazwa"),
    @NamedQuery(name = "Testwykaz.findBySkrot", query = "SELECT t FROM Testwykaz t WHERE t.skrot = :skrot")})
public class Testwykaz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "nazwa", nullable = false, length = 256)
    private String nazwa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "skrot", nullable = false, length = 32)
    private String skrot;
    @Lob
    @Size(max = 65535)
    @Column(name = "opis", length = 65535)
    private String opis;

    public Testwykaz() {
    }

    public Testwykaz(Integer id) {
        this.id = id;
    }

    public Testwykaz(Integer id, String nazwa, String skrot) {
        this.id = id;
        this.nazwa = nazwa;
        this.skrot = skrot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Testwykaz)) {
            return false;
        }
        Testwykaz other = (Testwykaz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Testwykaz[ id=" + id + " ]";
    }
    
}
