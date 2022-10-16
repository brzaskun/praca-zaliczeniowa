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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "nieobecnoscswiadectwoschema", uniqueConstraints = {
    @UniqueConstraint(columnNames={"swiadectwoschema","rodzajnieobecnosci"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nieobecnoscswiadectwoschema.findAll", query = "SELECT n FROM Nieobecnoscswiadectwoschema n"),
    @NamedQuery(name = "Nieobecnoscswiadectwoschema.findById", query = "SELECT n FROM Nieobecnoscswiadectwoschema n WHERE n.id = :id"),
    @NamedQuery(name = "Nieobecnoscswiadectwoschema.findBySwiadectwoschema", query = "SELECT n FROM Nieobecnoscswiadectwoschema n WHERE n.swiadectwoschema = :swiadectwoschema"),
    @NamedQuery(name = "Nieobecnoscswiadectwoschema.findByRodzajnieobecnosci", query = "SELECT n FROM Nieobecnoscswiadectwoschema n WHERE n.rodzajnieobecnosci = :rodzajnieobecnosci"),
    @NamedQuery(name = "Nieobecnoscswiadectwoschema.findByZdanie", query = "SELECT n FROM Nieobecnoscswiadectwoschema n WHERE n.zdanie = :zdanie"),
    @NamedQuery(name = "Nieobecnoscswiadectwoschema.findByPodzdanie", query = "SELECT n FROM Nieobecnoscswiadectwoschema n WHERE n.podzdanie = :podzdanie")})
public class Nieobecnoscswiadectwoschema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "swiadectwoschema", referencedColumnName = "id")
    private Swiadectwoschema swiadectwoschema;
    @JoinColumn(name = "rodzajnieobecnosci", referencedColumnName = "id")
    private Rodzajnieobecnosci rodzajnieobecnosci;
    @Size(max = 45)
    @Column(name = "zdanie")
    private String zdanie;
    @Size(max = 45)
    @Column(name = "podzdanie")
    private String podzdanie;

    public Nieobecnoscswiadectwoschema() {
    }

    public Nieobecnoscswiadectwoschema(Integer id) {
        this.id = id;
    }

    public Nieobecnoscswiadectwoschema(Integer id, Swiadectwoschema swiadectwoschema, Rodzajnieobecnosci rodzajnieobecnosci) {
        this.id = id;
        this.swiadectwoschema = swiadectwoschema;
        this.rodzajnieobecnosci = rodzajnieobecnosci;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Swiadectwoschema getSwiadectwoschema() {
        return swiadectwoschema;
    }

    public void setSwiadectwoschema(Swiadectwoschema swiadectwoschema) {
        this.swiadectwoschema = swiadectwoschema;
    }

    public Rodzajnieobecnosci getRodzajnieobecnosci() {
        return rodzajnieobecnosci;
    }

    public void setRodzajnieobecnosci(Rodzajnieobecnosci rodzajnieobecnosci) {
        this.rodzajnieobecnosci = rodzajnieobecnosci;
    }

    

    public String getZdanie() {
        return zdanie;
    }

    public void setZdanie(String zdanie) {
        this.zdanie = zdanie;
    }

    public String getPodzdanie() {
        return podzdanie;
    }

    public void setPodzdanie(String podzdanie) {
        this.podzdanie = podzdanie;
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
        if (!(object instanceof Nieobecnoscswiadectwoschema)) {
            return false;
        }
        Nieobecnoscswiadectwoschema other = (Nieobecnoscswiadectwoschema) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Nieobecnoscswiadectwoschema[ id=" + id + " ]";
    }
    
}
