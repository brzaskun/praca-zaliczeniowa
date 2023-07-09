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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "fakturaopisuslugi", schema = "kadry", uniqueConstraints = {
    @UniqueConstraint(columnNames={"opis"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fakturaopisuslugi.findAll", query = "SELECT f FROM Fakturaopisuslugi f"),
    @NamedQuery(name = "Fakturaopisuslugi.findById", query = "SELECT f FROM Fakturaopisuslugi f WHERE f.id = :id"),
    @NamedQuery(name = "Fakturaopisuslugi.findByOpis", query = "SELECT f FROM Fakturaopisuslugi f WHERE f.opis = :opis")})
public class Fakturaopisuslugi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "opis")
    private String opis;

    public Fakturaopisuslugi() {
    }

    public Fakturaopisuslugi(Integer id) {
        this.id = id;
    }

    public Fakturaopisuslugi(Integer id, String opis) {
        this.id = id;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Fakturaopisuslugi)) {
            return false;
        }
        Fakturaopisuslugi other = (Fakturaopisuslugi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fakturaopisuslugi{" + "opis=" + opis + '}';
    }

    
    
}
