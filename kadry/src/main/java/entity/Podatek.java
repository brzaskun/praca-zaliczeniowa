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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podatek")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Podatek.findAll", query = "SELECT p FROM Podatek p"),
    @NamedQuery(name = "Podatek.findById", query = "SELECT p FROM Podatek p WHERE p.id = :id"),
    @NamedQuery(name = "Podatek.findByOdliczaculgepodatkowa", query = "SELECT p FROM Podatek p WHERE p.odliczaculgepodatkowa = :odliczaculgepodatkowa"),
    @NamedQuery(name = "Podatek.findByKosztyuzyskania", query = "SELECT p FROM Podatek p WHERE p.kosztyuzyskania = :kosztyuzyskania")})
public class Podatek implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "odliczaculgepodatkowa")
    private Boolean odliczaculgepodatkowa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kosztyuzyskania")
    private double kosztyuzyskania;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "podatek")
    private List<Angaz> angazList;

    public Podatek() {
        this.odliczaculgepodatkowa = true;
        this.kosztyuzyskania = 250.0;
    }

    public Podatek(Integer id) {
        this.id = id;
    }

    public Podatek(Integer id, int kosztyuzyskania) {
        this.id = id;
        this.kosztyuzyskania = kosztyuzyskania;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getOdliczaculgepodatkowa() {
        return odliczaculgepodatkowa;
    }

    public void setOdliczaculgepodatkowa(Boolean odliczaculgepodatkowa) {
        this.odliczaculgepodatkowa = odliczaculgepodatkowa;
    }

    public double getKosztyuzyskania() {
        return kosztyuzyskania;
    }

    public void setKosztyuzyskania(double kosztyuzyskania) {
        this.kosztyuzyskania = kosztyuzyskania;
    }

    @XmlTransient
    public List<Angaz> getAngazList() {
        return angazList;
    }

    public void setAngazList(List<Angaz> angazList) {
        this.angazList = angazList;
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
        if (!(object instanceof Podatek)) {
            return false;
        }
        Podatek other = (Podatek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Podatek[ id=" + id + " ]";
    }
    
}
