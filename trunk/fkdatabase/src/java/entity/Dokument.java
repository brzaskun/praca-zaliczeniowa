/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "fktest", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokument.findAll", query = "SELECT d FROM Dokument d"),
    @NamedQuery(name = "Dokument.findById", query = "SELECT d FROM Dokument d WHERE d.id = :id"),
    @NamedQuery(name = "Dokument.findByNazwa", query = "SELECT d FROM Dokument d WHERE d.nazwa = :nazwa")})
public class Dokument implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dokument", orphanRemoval = true)
    private List<Wiersz> wierszelista;
    
    
    public Dokument() {
        this.wierszelista = new ArrayList<>();
    }

    public Dokument(Integer id) {
        this.id = id;
    }

    public Dokument(String nazwa) {
        this.wierszelista = new ArrayList<>();
        this.nazwa = nazwa;
    }
    

    public Dokument(Integer id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
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

    public List<Wiersz> getWierszelista() {
        return wierszelista;
    }

    public void setWierszelista(List<Wiersz> wierszelista) {
        this.wierszelista = wierszelista;
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
        if (!(object instanceof Dokument)) {
            return false;
        }
        Dokument other = (Dokument) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dokument{" + "id=" + id + ", nazwa=" + nazwa + ", wierszelista=" + wierszelista + '}';
    }

   
    
}
