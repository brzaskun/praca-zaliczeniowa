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
@Table(name = "skladnikwynagrodzenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Skladnikwynagrodzenia.findAll", query = "SELECT s FROM Skladnikwynagrodzenia s"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findById", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.id = :id"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findByNazwa", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.nazwa = :nazwa"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findByGodzinowe0miesieczne1", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.godzinowe0miesieczne1 = :godzinowe0miesieczne1"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findByDefinicjalistaplac", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.definicjalistaplac = :definicjalistaplac")})
public class Skladnikwynagrodzenia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nazwa")
    private String nazwa;
    @Column(name = "godzinowe0miesieczne1")
    private Boolean godzinowe0miesieczne1;
    @Column(name = "definicjalistaplac")
    private Integer definicjalistaplac;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skladnikwynagrodzenia")
    private List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList;
    @JoinColumn(name = "rodzajumowy", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rodzajumowy rodzajumowy;
    @JoinColumn(name = "zmiennawynagrodzenia", referencedColumnName = "id")
    @ManyToOne
    private Zmiennawynagrodzenia zmiennawynagrodzenia;

    public Skladnikwynagrodzenia() {
    }

    public Skladnikwynagrodzenia(Integer id) {
        this.id = id;
    }

    public Skladnikwynagrodzenia(Integer id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
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

    public Boolean getGodzinowe0miesieczne1() {
        return godzinowe0miesieczne1;
    }

    public void setGodzinowe0miesieczne1(Boolean godzinowe0miesieczne1) {
        this.godzinowe0miesieczne1 = godzinowe0miesieczne1;
    }

    public Integer getDefinicjalistaplac() {
        return definicjalistaplac;
    }

    public void setDefinicjalistaplac(Integer definicjalistaplac) {
        this.definicjalistaplac = definicjalistaplac;
    }

    @XmlTransient
    public List<Naliczenieskladnikawynagrodzenia> getNaliczenieskladnikawynagrodzeniaList() {
        return naliczenieskladnikawynagrodzeniaList;
    }

    public void setNaliczenieskladnikawynagrodzeniaList(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList) {
        this.naliczenieskladnikawynagrodzeniaList = naliczenieskladnikawynagrodzeniaList;
    }

    public Rodzajumowy getRodzajumowy() {
        return rodzajumowy;
    }

    public void setRodzajumowy(Rodzajumowy rodzajumowy) {
        this.rodzajumowy = rodzajumowy;
    }

    public Zmiennawynagrodzenia getZmiennawynagrodzenia() {
        return zmiennawynagrodzenia;
    }

    public void setZmiennawynagrodzenia(Zmiennawynagrodzenia zmiennawynagrodzenia) {
        this.zmiennawynagrodzenia = zmiennawynagrodzenia;
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
    
}
