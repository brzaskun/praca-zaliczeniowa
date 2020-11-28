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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "angaz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Angaz.findAll", query = "SELECT a FROM Angaz a"),
    @NamedQuery(name = "Angaz.findById", query = "SELECT a FROM Angaz a WHERE a.id = :id"),
    @NamedQuery(name = "Angaz.findByRodzajwynagrodzenia", query = "SELECT a FROM Angaz a WHERE a.rodzajwynagrodzenia = :rodzajwynagrodzenia")})
public class Angaz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rodzajwynagrodzenia")
    private int rodzajwynagrodzenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz")
    private List<Umowa> umowaList;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Firma firma;
    @JoinColumn(name = "podatek", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Podatek podatek;
    @JoinColumn(name = "pracownik", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pracownik pracownik;
    @JoinColumn(name = "ubezpieczenie", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Ubezpieczenie ubezpieczenie;

    
    public Angaz() {
        this.podatek = new Podatek();
        this.ubezpieczenie = new Ubezpieczenie();
    }

    public Angaz(Integer id) {
        this.id = id;
        this.podatek = new Podatek();
        this.ubezpieczenie = new Ubezpieczenie();
    }

    public Angaz(Integer id, int rodzajwynagrodzenia) {
        this.id = id;
        this.rodzajwynagrodzenia = rodzajwynagrodzenia;
        this.podatek = new Podatek();
        this.ubezpieczenie = new Ubezpieczenie();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRodzajwynagrodzenia() {
        return rodzajwynagrodzenia;
    }

    public void setRodzajwynagrodzenia(int rodzajwynagrodzenia) {
        this.rodzajwynagrodzenia = rodzajwynagrodzenia;
    }

    @XmlTransient
    public List<Umowa> getUmowaList() {
        return umowaList;
    }

    public void setUmowaList(List<Umowa> umowaList) {
        this.umowaList = umowaList;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    public Podatek getPodatek() {
        return podatek;
    }

    public void setPodatek(Podatek podatek) {
        this.podatek = podatek;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public Ubezpieczenie getUbezpieczenie() {
        return ubezpieczenie;
    }

    public void setUbezpieczenie(Ubezpieczenie ubezpieczenie) {
        this.ubezpieczenie = ubezpieczenie;
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
        if (!(object instanceof Angaz)) {
            return false;
        }
        Angaz other = (Angaz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Angaz[ id=" + id + " ]";
    }
    
}
