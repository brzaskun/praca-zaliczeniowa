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
    @NamedQuery(name = "Skladnikwynagrodzenia.findByGodzinowe0miesieczne1", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.godzinowe0miesieczne1 = :godzinowe0miesieczne1"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findByNazwa", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.nazwa = :nazwa")})
public class Skladnikwynagrodzenia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "godzinowe0miesieczne1")
    private boolean godzinowe0miesieczne1;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Column(name = "redukowanyzaczasnieobecnosci")
    private boolean redukowanyzaczasnieobecnosci;
    @Column(name = "stala0zmienna1")
    private boolean stala0zmienna1;
    @Size(max = 4)
    @Column(name = "kod")
    private String kod;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skladnikwynagrodzenia")
    private List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList;
    @OneToMany(mappedBy = "skladnikwynagrodzenia")
    private List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList;
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne
    private Umowa umowa;
    @OneToMany(mappedBy = "skladnikwynagrodzenia")
    private List<Naliczenienieobecnosc> naliczenienieobecnoscList;

    public Skladnikwynagrodzenia() {
    }

    public Skladnikwynagrodzenia(int id) {
        this.id = id;
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

    public boolean getGodzinowe0miesieczne1() {
        return godzinowe0miesieczne1;
    }

    public void setGodzinowe0miesieczne1(boolean godzinowe0miesieczne1) {
        this.godzinowe0miesieczne1 = godzinowe0miesieczne1;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public boolean getRedukowanyzaczasnieobecnosci() {
        return redukowanyzaczasnieobecnosci;
    }

    public void setRedukowanyzaczasnieobecnosci(boolean redukowanyzaczasnieobecnosci) {
        this.redukowanyzaczasnieobecnosci = redukowanyzaczasnieobecnosci;
    }

    public boolean getStala0zmienna1() {
        return stala0zmienna1;
    }

    public void setStala0zmienna1(boolean stala0zmienna1) {
        this.stala0zmienna1 = stala0zmienna1;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    @XmlTransient
    public List<Zmiennawynagrodzenia> getZmiennawynagrodzeniaList() {
        return zmiennawynagrodzeniaList;
    }

    public void setZmiennawynagrodzeniaList(List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList) {
        this.zmiennawynagrodzeniaList = zmiennawynagrodzeniaList;
    }


    
    
}
