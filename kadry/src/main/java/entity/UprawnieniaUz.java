/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
@Table(name = "uprawnieniauz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UprawnieniaUz.findAll", query = "SELECT u FROM UprawnieniaUz u"),
    @NamedQuery(name = "UprawnieniaUz.findById", query = "SELECT u FROM UprawnieniaUz u WHERE u.id = :id"),
    @NamedQuery(name = "UprawnieniaUz.findByNazwa", query = "SELECT u FROM UprawnieniaUz u WHERE u.nazwa = :nazwa")})
public class UprawnieniaUz implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nazwa")
    private String nazwa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uprawnienia")
    private List<Uz> uzList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public UprawnieniaUz() {
    }

    public UprawnieniaUz(Integer id) {
        this.id = id;
    }

    public UprawnieniaUz(Integer id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof UprawnieniaUz)) {
            return false;
        }
        UprawnieniaUz other = (UprawnieniaUz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Uprawnienia[ id=" + id + " ]";
    }


    @XmlTransient
    public List<Uz> getUzList() {
        return uzList;
    }

    public void setUzList(List<Uz> uzList) {
        this.uzList = uzList;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
}
