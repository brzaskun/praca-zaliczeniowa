/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "skladnikpotracenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Skladnikpotracenia.findAll", query = "SELECT s FROM Skladnikpotracenia s"),
    @NamedQuery(name = "Skladnikpotracenia.findById", query = "SELECT s FROM Skladnikpotracenia s WHERE s.id = :id"),
    @NamedQuery(name = "Skladnikpotracenia.findByNazwa", query = "SELECT s FROM Skladnikpotracenia s WHERE s.nazwa = :nazwa"),
    @NamedQuery(name = "Skladnikpotracenia.findByPracownik", query = "SELECT s FROM Skladnikpotracenia s WHERE s.umowa.angaz.pracownik = :pracownik"),
    @NamedQuery(name = "Skladnikpotracenia.findByUmowa", query = "SELECT s FROM Skladnikpotracenia s WHERE s.umowa = :umowa")
})
public class Skladnikpotracenia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @JoinColumn(name = "slownikpotracenia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Slownikpotracenia slownikpotracenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skladnikpotracenia")
    private List<Zmiennapotracenia> zmiennapotraceniaList;
    @OneToMany(mappedBy = "skladnikpotracenia")
    private List<Naliczeniepotracenie> naliczeniepotracenieList;
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne
    private Umowa umowa;


    public Skladnikpotracenia() {
        this.zmiennapotraceniaList = new ArrayList<>();
    }

    public Skladnikpotracenia(int id) {
        this.id = id;
        this.zmiennapotraceniaList = new ArrayList<>();
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


    @XmlTransient
    public List<Zmiennapotracenia> getZmiennapotraceniaList() {
        return zmiennapotraceniaList;
    }

    public void setZmiennapotraceniaList(List<Zmiennapotracenia> zmiennapotraceniaList) {
        this.zmiennapotraceniaList = zmiennapotraceniaList;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Slownikpotracenia getSlownikpotracenia() {
        return slownikpotracenia;
    }

    public void setSlownikpotracenia(Slownikpotracenia slownikpotracenia) {
        this.slownikpotracenia = slownikpotracenia;
    }
    
    public String getTytul() {
        String zwrot = "blad";
        if (this.zmiennapotraceniaList!=null && this.zmiennapotraceniaList.size()>0) {
            zwrot = zmiennapotraceniaList.get(0).getNazwa();
        }
        return zwrot;
    }
    
    public String getDataOd() {
        String zwrot = "blad";
        if (this.zmiennapotraceniaList!=null && this.zmiennapotraceniaList.size()>0) {
            zwrot = zmiennapotraceniaList.get(0).getDataod();
        }
        return zwrot;
    }
    
    public String getDataDo() {
        String zwrot = "blad";
        if (this.zmiennapotraceniaList!=null && this.zmiennapotraceniaList.size()>0) {
            zwrot = zmiennapotraceniaList.get(0).getDatado();
        }
        return zwrot;
    }
    
    public double getKwotastala() {
        double zwrot = 0.0;
        if (this.zmiennapotraceniaList!=null && this.zmiennapotraceniaList.size()>0) {
            zwrot = zmiennapotraceniaList.get(0).getKwotastala();
        }
        return zwrot;
    }
    
    public double getKwotakomornicza() {
        double zwrot = 0.0;
        if (this.zmiennapotraceniaList!=null && this.zmiennapotraceniaList.size()>0) {
            zwrot = zmiennapotraceniaList.get(0).getKwotakomornicza();
        }
        return zwrot;
    }
}
