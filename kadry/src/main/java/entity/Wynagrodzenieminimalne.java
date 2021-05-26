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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "wynagrodzenieminimalne")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wynagrodzenieminimalne.findAll", query = "SELECT w FROM Wynagrodzenieminimalne w"),
    @NamedQuery(name = "Wynagrodzenieminimalne.findById", query = "SELECT w FROM Wynagrodzenieminimalne w WHERE w.id = :id"),
    @NamedQuery(name = "Wynagrodzenieminimalne.findByRok", query = "SELECT w FROM Wynagrodzenieminimalne w WHERE w.rok = :rok"),
    @NamedQuery(name = "Wynagrodzenieminimalne.findByKwotabrutto", query = "SELECT w FROM Wynagrodzenieminimalne w WHERE w.kwotabrutto = :kwotabrutto")})
public class Wynagrodzenieminimalne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rok")
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kwotabrutto")
    private double kwotabrutto;

    public Wynagrodzenieminimalne() {
    }

    public Wynagrodzenieminimalne(Integer id) {
        this.id = id;
    }

    public Wynagrodzenieminimalne(Integer id, String rok, double kwotabrutto) {
        this.id = id;
        this.rok = rok;
        this.kwotabrutto = kwotabrutto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public double getKwotabrutto() {
        return kwotabrutto;
    }

    public void setKwotabrutto(double kwotabrutto) {
        this.kwotabrutto = kwotabrutto;
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
        if (!(object instanceof Wynagrodzenieminimalne)) {
            return false;
        }
        Wynagrodzenieminimalne other = (Wynagrodzenieminimalne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Wynagrodzenieminimalne[ id=" + id + " ]";
    }
    
}
