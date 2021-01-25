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
    @NamedQuery(name = "Angaz.findByRodzajwynagrodzenia", query = "SELECT a FROM Angaz a WHERE a.rodzajwynagrodzenia = :rodzajwynagrodzenia"),
    @NamedQuery(name = "Angaz.findByFirma", query = "SELECT a FROM Angaz a WHERE a.firma = :firma"),
    @NamedQuery(name = "Angaz.findPracownikByFirma", query = "SELECT a.pracownik FROM Angaz a WHERE a.firma = :firma")
})

public class Angaz implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz")
    private List<Wynagrodzeniahistoryczne> wynagrodzeniahistoryczneList;

    @OneToMany(mappedBy = "angaz")
    private List<Memory> memoryList;

  
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL)
    private List<Umowa> umowaList;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private Firma firma;
    @JoinColumn(name = "pracownik", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private Pracownik pracownik;
    @Column(name = "rodzajwynagrodzenia")
    private Integer rodzajwynagrodzenia;


    public Angaz() {
    }

    public Angaz(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
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

    public Integer getRodzajwynagrodzenia() {
        return rodzajwynagrodzenia;
    }

    public void setRodzajwynagrodzenia(Integer rodzajwynagrodzenia) {
        this.rodzajwynagrodzenia = rodzajwynagrodzenia;
    }
     public String getAngazString() {
        return this.getFirma().getNazwa()+" "+this.getPracownik().getNazwiskoImie();
    }
    public String getAngazStringPlik() {
        return this.getFirma().getNazwa();
    }

    @XmlTransient
    public List<Memory> getMemoryList() {
        return memoryList;
    }

    public void setMemoryList(List<Memory> memoryList) {
        this.memoryList = memoryList;
    }

    @XmlTransient
    public List<Wynagrodzeniahistoryczne> getWynagrodzeniahistoryczneList() {
        return wynagrodzeniahistoryczneList;
    }

    public void setWynagrodzeniahistoryczneList(List<Wynagrodzeniahistoryczne> wynagrodzeniahistoryczneList) {
        this.wynagrodzeniahistoryczneList = wynagrodzeniahistoryczneList;
    }
}
