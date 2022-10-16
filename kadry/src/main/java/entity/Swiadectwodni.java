/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "swiadectwodni", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nieobecnoscswiadectwoschema"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Swiadectwodni.findAll", query = "SELECT s FROM Swiadectwodni s"),
    @NamedQuery(name = "Swiadectwodni.findById", query = "SELECT s FROM Swiadectwodni s WHERE s.id = :id"),
    @NamedQuery(name = "Swiadectwodni.findBySwiadectwo", query = "SELECT s FROM Swiadectwodni s WHERE s.swiadectwo = :swiadectwo"),
    @NamedQuery(name = "Swiadectwodni.findByNieobecnoscswiadectwoschema", query = "SELECT s FROM Swiadectwodni s WHERE s.nieobecnoscswiadectwoschema = :nieobecnoscswiadectwoschema"),
    @NamedQuery(name = "Swiadectwodni.findByDni", query = "SELECT s FROM Swiadectwodni s WHERE s.dni = :dni"),
    @NamedQuery(name = "Swiadectwodni.findByGodziny", query = "SELECT s FROM Swiadectwodni s WHERE s.godziny = :godziny")})
public class Swiadectwodni implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;
    @NotNull
    @JoinColumn(name = "swiadectwo", referencedColumnName = "id")
    private Swiadectwo swiadectwo;
    @NotNull
    @JoinColumn(name = "nieobecnoscswiadectwoschema", referencedColumnName = "id")
    private Nieobecnoscswiadectwoschema nieobecnoscswiadectwoschema;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dni", precision = 22, scale = 0)
    private double dni;
    @Column(name = "godziny", precision = 22, scale = 0)
    private double godziny;
    @Transient
    private List<Nieobecnosc> nieobecnoscinieskladkowe;

    public Swiadectwodni() {
    }

    public Swiadectwodni(Integer id) {
        this.id = id;
    }

    public Swiadectwodni(Integer id, Swiadectwo swiadectwo) {
        this.id = id;
        this.swiadectwo = swiadectwo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Swiadectwo getSwiadectwo() {
        return swiadectwo;
    }

    public void setSwiadectwo(Swiadectwo swiadectwo) {
        this.swiadectwo = swiadectwo;
    }

    public Nieobecnoscswiadectwoschema getNieobecnoscswiadectwoschema() {
        return nieobecnoscswiadectwoschema;
    }

    public void setNieobecnoscswiadectwoschema(Nieobecnoscswiadectwoschema nieobecnoscswiadectwoschema) {
        this.nieobecnoscswiadectwoschema = nieobecnoscswiadectwoschema;
    }

    public double getDni() {
        return dni;
    }

    public void setDni(double dni) {
        this.dni = dni;
    }

    public double getGodziny() {
        return godziny;
    }

    public void setGodziny(double godziny) {
        this.godziny = godziny;
    }

    public List<Nieobecnosc> getNieobecnoscinieskladkowe() {
        return nieobecnoscinieskladkowe;
    }

    public void setNieobecnoscinieskladkowe(List<Nieobecnosc> nieobecnoscinieskladkowe) {
        this.nieobecnoscinieskladkowe = nieobecnoscinieskladkowe;
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
        if (!(object instanceof Swiadectwodni)) {
            return false;
        }
        Swiadectwodni other = (Swiadectwodni) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Swiadectwodni[ id=" + id + " ]";
    }
    
}
