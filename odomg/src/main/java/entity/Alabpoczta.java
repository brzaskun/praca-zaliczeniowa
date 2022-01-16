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
@Table(name = "alabpoczta", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"symbol"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alabpoczta.findAll", query = "SELECT a FROM Alabpoczta a"),
    @NamedQuery(name = "Alabpoczta.findById", query = "SELECT a FROM Alabpoczta a WHERE a.id = :id"),
    @NamedQuery(name = "Alabpoczta.findBySymbol", query = "SELECT a FROM Alabpoczta a WHERE a.symbol = :symbol"),
    @NamedQuery(name = "Alabpoczta.findByAdres", query = "SELECT a FROM Alabpoczta a WHERE a.adres = :adres")})
public class Alabpoczta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "symbol", nullable = false, length = 45)
    private String symbol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "adres", nullable = false, length = 128)
    private String adres;

    public Alabpoczta() {
    }

    public Alabpoczta(Integer id) {
        this.id = id;
    }

    public Alabpoczta(Integer id, String symbol, String adres) {
        this.id = id;
        this.symbol = symbol;
        this.adres = adres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
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
        if (!(object instanceof Alabpoczta)) {
            return false;
        }
        Alabpoczta other = (Alabpoczta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Alabpoczta[ id=" + id + " ]";
    }
    
}
