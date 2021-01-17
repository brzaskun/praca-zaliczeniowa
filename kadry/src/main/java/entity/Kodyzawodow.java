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
@Table(name = "kodyzawodow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kodyzawodow.findAll", query = "SELECT k FROM Kodyzawodow k"),
    @NamedQuery(name = "Kodyzawodow.findById", query = "SELECT k FROM Kodyzawodow k WHERE k.id = :id"),
    @NamedQuery(name = "Kodyzawodow.findBySymbolkzis", query = "SELECT k FROM Kodyzawodow k WHERE k.symbolkzis = :symbolkzis"),
    @NamedQuery(name = "Kodyzawodow.findBySymbolus", query = "SELECT k FROM Kodyzawodow k WHERE k.symbolus = :symbolus"),
    @NamedQuery(name = "Kodyzawodow.findByNazwa", query = "SELECT k FROM Kodyzawodow k WHERE k.nazwa = :nazwa")})
public class Kodyzawodow implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "symbolkzis")
    private String symbolkzis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "symbolus")
    private String symbolus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "nazwa")
    private String nazwa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kodzawodu")
    private List<Umowa> umowaList;

    public Kodyzawodow() {
    }

    public Kodyzawodow(Integer id) {
        this.id = id;
    }

    public Kodyzawodow(Integer id, String symbolkzis, String symbolus, String nazwa) {
        this.id = id;
        this.symbolkzis = symbolkzis;
        this.symbolus = symbolus;
        this.nazwa = nazwa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbolkzis() {
        return symbolkzis;
    }

    public void setSymbolkzis(String symbolkzis) {
        this.symbolkzis = symbolkzis;
    }

    public String getSymbolus() {
        return symbolus;
    }

    public void setSymbolus(String symbolus) {
        this.symbolus = symbolus;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @XmlTransient
    public List<Umowa> getUmowaList() {
        return umowaList;
    }

    public void setUmowaList(List<Umowa> umowaList) {
        this.umowaList = umowaList;
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
        if (!(object instanceof Kodyzawodow)) {
            return false;
        }
        Kodyzawodow other = (Kodyzawodow) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Kodyzawodow[ id=" + id + " ]";
    }
    
}
