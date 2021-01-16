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
@Table(name = "skladnikwynagrodzenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Skladnikwynagrodzenia.findAll", query = "SELECT s FROM Skladnikwynagrodzenia s"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findById", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.id = :id"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findByPracownik", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.umowa.angaz.pracownik = :pracownik")
})
public class Skladnikwynagrodzenia implements Serializable {

    @Size(max = 255)
    @Column(name = "uwagi")
    private String uwagi;
    @JoinColumn(name = "rodzajwynagrodzenia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rodzajwynagrodzenia rodzajwynagrodzenia;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skladnikwynagrodzenia", orphanRemoval = true)
    private List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skladnikwynagrodzenia", orphanRemoval = true)
    private List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList;
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne
    private Umowa umowa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skladnikwynagrodzenia", orphanRemoval = true)
    private List<Naliczenienieobecnosc> naliczenienieobecnoscList;

    public Skladnikwynagrodzenia() {
        this.zmiennawynagrodzeniaList = new ArrayList<>();
    }

    public Skladnikwynagrodzenia(int id) {
        this.id = id;
        this.zmiennawynagrodzeniaList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    @XmlTransient
    public List<Naliczenieskladnikawynagrodzenia> getNaliczenieskladnikawynagrodzeniaList() {
        return naliczenieskladnikawynagrodzeniaList;
    }

    public void setNaliczenieskladnikawynagrodzeniaList(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList) {
        this.naliczenieskladnikawynagrodzeniaList = naliczenieskladnikawynagrodzeniaList;
    }

    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }

    @XmlTransient
    public List<Naliczenienieobecnosc> getNaliczenienieobecnoscList() {
        return naliczenienieobecnoscList;
    }

    public void setNaliczenienieobecnoscList(List<Naliczenienieobecnosc> naliczenienieobecnoscList) {
        this.naliczenienieobecnoscList = naliczenienieobecnoscList;
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
        if (!(object instanceof Skladnikwynagrodzenia)) {
            return false;
        }
        Skladnikwynagrodzenia other = (Skladnikwynagrodzenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Skladnikwynagrodzenia[ id=" + id + " ]";
    }
    @XmlTransient
    public List<Zmiennawynagrodzenia> getZmiennawynagrodzeniaList() {
        return zmiennawynagrodzeniaList;
    }
    public void setZmiennawynagrodzeniaList(List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList) {
        this.zmiennawynagrodzeniaList = zmiennawynagrodzeniaList;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public Rodzajwynagrodzenia getRodzajwynagrodzenia() {
        return rodzajwynagrodzenia;
    }

    public void setRodzajwynagrodzenia(Rodzajwynagrodzenia rodzajwynagrodzenia) {
        this.rodzajwynagrodzenia = rodzajwynagrodzenia;
    }


    
    
}
