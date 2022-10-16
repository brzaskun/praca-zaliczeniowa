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
@Table(name = "swiadectwoschema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Swiadectwoschema.findAll", query = "SELECT s FROM Swiadectwoschema s"),
    @NamedQuery(name = "Swiadectwoschema.findById", query = "SELECT s FROM Swiadectwoschema s WHERE s.id = :id"),
    @NamedQuery(name = "Swiadectwoschema.findByRok", query = "SELECT s FROM Swiadectwoschema s WHERE s.rok = :rok"),
    @NamedQuery(name = "Swiadectwoschema.findByMc", query = "SELECT s FROM Swiadectwoschema s WHERE s.mc = :mc"),
    @NamedQuery(name = "Swiadectwoschema.findByNazwaschemy", query = "SELECT s FROM Swiadectwoschema s WHERE s.nazwaschemy = :nazwaschemy")})
public class Swiadectwoschema implements Serializable {

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
    @Size(min = 1, max = 2)
    @Column(name = "mc")
    private String mc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nazwaschemy")
    private String nazwaschemy;

    public Swiadectwoschema() {
    }

    public Swiadectwoschema(Integer id) {
        this.id = id;
    }

    public Swiadectwoschema(Integer id, String rok, String mc, String nazwaschemy) {
        this.id = id;
        this.rok = rok;
        this.mc = mc;
        this.nazwaschemy = nazwaschemy;
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

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getNazwaschemy() {
        return nazwaschemy;
    }

    public void setNazwaschemy(String nazwaschemy) {
        this.nazwaschemy = nazwaschemy;
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
        if (!(object instanceof Swiadectwoschema)) {
            return false;
        }
        Swiadectwoschema other = (Swiadectwoschema) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Swiadectwoschema[ id=" + id + " ]";
    }
    
}
