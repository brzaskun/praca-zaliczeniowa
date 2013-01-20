/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "standard")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Standard.findAll", query = "SELECT s FROM Standard s"),
    @NamedQuery(name = "Standard.findById", query = "SELECT s FROM Standard s WHERE s.id = :id"),
    @NamedQuery(name = "Standard.findByStandard", query = "SELECT s FROM Standard s WHERE s.standard = :standard")})
public class Standard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "standard")
    private String standard;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "standard")
    private List<Pokoj> pokojList;

    public Standard() {
    }

    public Standard(Integer id) {
        this.id = id;
    }

    public Standard(Integer id, String standard) {
        this.id = id;
        this.standard = standard;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @XmlTransient
    public List<Pokoj> getPokojList() {
        return pokojList;
    }

    public void setPokojList(List<Pokoj> pokojList) {
        this.pokojList = pokojList;
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
        if (!(object instanceof Standard)) {
            return false;
        }
        Standard other = (Standard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Standard[ id=" + id + " ]";
    }
    
}
