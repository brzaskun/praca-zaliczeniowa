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
import javax.persistence.ManyToOne;
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
@Table(name = "skladnikpotracenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Skladnikpotracenia.findAll", query = "SELECT s FROM Skladnikpotracenia s"),
    @NamedQuery(name = "Skladnikpotracenia.findById", query = "SELECT s FROM Skladnikpotracenia s WHERE s.id = :id"),
    @NamedQuery(name = "Skladnikpotracenia.findByNazwa", query = "SELECT s FROM Skladnikpotracenia s WHERE s.nazwa = :nazwa")})
public class Skladnikpotracenia implements Serializable {

   
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "skladnikpotracenia")
    private List<Naliczeniepotracenie> naliczeniepotracenieList;
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne
    private Umowa umowa;
    @JoinColumn(name = "zmiennapotracenia", referencedColumnName = "id")
    @ManyToOne
    private Zmiennapotracenia zmiennapotracenia;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;


    public Skladnikpotracenia() {
    }

    public Skladnikpotracenia(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<Naliczeniepotracenie> getNaliczeniepotracenieList() {
        return naliczeniepotracenieList;
    }

    public void setNaliczeniepotracenieList(List<Naliczeniepotracenie> naliczeniepotracenieList) {
        this.naliczeniepotracenieList = naliczeniepotracenieList;
    }

    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }

    public Zmiennapotracenia getZmiennapotracenia() {
        return zmiennapotracenia;
    }

    public void setZmiennapotracenia(Zmiennapotracenia zmiennapotracenia) {
        this.zmiennapotracenia = zmiennapotracenia;
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
        if (!(object instanceof Skladnikpotracenia)) {
            return false;
        }
        Skladnikpotracenia other = (Skladnikpotracenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Skladnikpotracenia[ id=" + id + " ]";
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
}
