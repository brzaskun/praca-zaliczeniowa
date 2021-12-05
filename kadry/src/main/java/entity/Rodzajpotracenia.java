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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rodzajpotracenia", uniqueConstraints = {
    @UniqueConstraint(columnNames={"opis"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rodzajpotracenia.findAll", query = "SELECT s FROM Rodzajpotracenia s"),
    @NamedQuery(name = "Rodzajpotracenia.findById", query = "SELECT s FROM Rodzajpotracenia s WHERE s.id = :id"),
    @NamedQuery(name = "Rodzajpotracenia.findByOpis", query = "SELECT s FROM Rodzajpotracenia s WHERE s.opis = :opis"),
    @NamedQuery(name = "Rodzajpotracenia.findByLimitumowaoprace", query = "SELECT s FROM Rodzajpotracenia s WHERE s.limitumowaoprace = :limitumowaoprace"),
    @NamedQuery(name = "Rodzajpotracenia.findByLimitumowazlecenia", query = "SELECT s FROM Rodzajpotracenia s WHERE s.limitumowazlecenia = :limitumowazlecenia")})
public class Rodzajpotracenia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 256)
    @Column(name = "opis")
    private String opis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "limitumowaoprace")
    private double limitumowaoprace;
    @Column(name = "limitumowazlecenia")
    private double limitumowazlecenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajpotracenia")
    private List<Skladnikpotracenia> skladnikpotraceniaList;
    @Column(name = "wpo_serial")
    private Integer wpo_serial;
    @Column(name = "pod_doch")
    private boolean pod_doch;
    @Column(name = "zus")
    private boolean zus;
    @Column(name = "zdrowotna")
    private boolean zdrowotne;
    @Column(name = "numer")
    private int numer;
    
    public Rodzajpotracenia() {
    }

    public Rodzajpotracenia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
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

    public Integer getWpo_serial() {
        return wpo_serial;
    }

    public void setWpo_serial(Integer wpo_serial) {
        this.wpo_serial = wpo_serial;
    }

 

    public boolean isPod_doch() {
        return pod_doch;
    }

    public void setPod_doch(boolean pod_doch) {
        this.pod_doch = pod_doch;
    }

    public boolean isZus() {
        return zus;
    }

    public void setZus(boolean zus) {
        this.zus = zus;
    }

    public boolean isZdrowotne() {
        return zdrowotne;
    }

    public void setZdrowotne(boolean zdrowotne) {
        this.zdrowotne = zdrowotne;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
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
        if (!(object instanceof Rodzajpotracenia)) {
            return false;
        }
        Rodzajpotracenia other = (Rodzajpotracenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rodzajpotracenia[ id=" + id + " ]";
    }
    
}
