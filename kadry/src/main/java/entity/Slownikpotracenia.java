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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "slownikpotracenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Slownikpotracenia.findAll", query = "SELECT s FROM Slownikpotracenia s"),
    @NamedQuery(name = "Slownikpotracenia.findById", query = "SELECT s FROM Slownikpotracenia s WHERE s.id = :id"),
    @NamedQuery(name = "Slownikpotracenia.findByNazwa", query = "SELECT s FROM Slownikpotracenia s WHERE s.nazwa = :nazwa"),
    @NamedQuery(name = "Slownikpotracenia.findByLimitumowaoprace", query = "SELECT s FROM Slownikpotracenia s WHERE s.limitumowaoprace = :limitumowaoprace"),
    @NamedQuery(name = "Slownikpotracenia.findByLimitumowazlecenia", query = "SELECT s FROM Slownikpotracenia s WHERE s.limitumowazlecenia = :limitumowazlecenia")})
public class Slownikpotracenia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 256)
    @Column(name = "nazwa")
    private String nazwa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "limitumowaoprace")
    private double limitumowaoprace;
    @Column(name = "limitumowazlecenia")
    private double limitumowazlecenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "slownikpotracenia")
    private List<Skladnikpotracenia> skladnikpotraceniaList;

    public Slownikpotracenia() {
    }

    public Slownikpotracenia(Integer id) {
        this.id = id;
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

    public double getLimitumowaoprace() {
        return limitumowaoprace;
    }

    public void setLimitumowaoprace(double limitumowaoprace) {
        this.limitumowaoprace = limitumowaoprace;
    }

    public double getLimitumowazlecenia() {
        return limitumowazlecenia;
    }

    public void setLimitumowazlecenia(double limitumowazlecenia) {
        this.limitumowazlecenia = limitumowazlecenia;
    }

    @XmlTransient
    public List<Skladnikpotracenia> getSkladnikpotraceniaList() {
        return skladnikpotraceniaList;
    }

    public void setSkladnikpotraceniaList(List<Skladnikpotracenia> skladnikpotraceniaList) {
        this.skladnikpotraceniaList = skladnikpotraceniaList;
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
        if (!(object instanceof Slownikpotracenia)) {
            return false;
        }
        Slownikpotracenia other = (Slownikpotracenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Slownikpotracenia[ id=" + id + " ]";
    }
    
}
