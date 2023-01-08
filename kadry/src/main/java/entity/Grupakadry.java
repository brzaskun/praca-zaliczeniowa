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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "grupakadry", uniqueConstraints = {
    @UniqueConstraint(columnNames={"firma","nazwa"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupakadry.findAll", query = "SELECT g FROM Grupakadry g"),
    @NamedQuery(name = "Grupakadry.findById", query = "SELECT g FROM Grupakadry g WHERE g.id = :id"),
    @NamedQuery(name = "Grupakadry.findByNazwa", query = "SELECT g FROM Grupakadry g WHERE g.nazwa = :nazwa"),
    @NamedQuery(name = "Grupakadry.findByFirma", query = "SELECT g FROM Grupakadry g WHERE g.firma = :firma")})
public class Grupakadry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nazwa")
    private String nazwa;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private FirmaKadry firma;
    @OneToMany(mappedBy = "grupakadry")
    private List<Umowa> umowylista;

    public Grupakadry() {
    }

    public Grupakadry(Integer id) {
        this.id = id;
    }

    public Grupakadry(Integer id, String nazwa, FirmaKadry firma) {
        this.id = id;
        this.nazwa = nazwa;
        this.firma = firma;
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

    public FirmaKadry getFirma() {
        return firma;
    }

    public void setFirma(FirmaKadry firma) {
        this.firma = firma;
    }

    public List<Umowa> getUmowylista() {
        return umowylista;
    }

    public void setUmowylista(List<Umowa> umowylista) {
        this.umowylista = umowylista;
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
        if (!(object instanceof Grupakadry)) {
            return false;
        }
        Grupakadry other = (Grupakadry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Grupakadry[ id=" + id + " ]";
    }
    
}
