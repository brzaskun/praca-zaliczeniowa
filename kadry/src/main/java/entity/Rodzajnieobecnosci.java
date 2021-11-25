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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rodzajnieobecnosci", uniqueConstraints = {
    @UniqueConstraint(columnNames={"opis"})
})
@NamedQueries({
    @NamedQuery(name = "Rodzajnieobecnosci.findAll", query = "SELECT r FROM Rodzajnieobecnosci r"),
    @NamedQuery(name = "Rodzajnieobecnosci.findById", query = "SELECT r FROM Rodzajnieobecnosci r WHERE r.id = :id"),
    @NamedQuery(name = "Rodzajnieobecnosci.findByOpis", query = "SELECT r FROM Rodzajnieobecnosci r WHERE r.opis = :opis"),
    @NamedQuery(name = "Rodzajnieobecnosci.findByKod", query = "SELECT r FROM Rodzajnieobecnosci r WHERE r.kod = :kod"),
    @NamedQuery(name = "Rodzajnieobecnosci.findByRedukcjawyn", query = "SELECT r FROM Rodzajnieobecnosci r WHERE r.redukcjawyn = :redukcjawyn")})
public class Rodzajnieobecnosci implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "absSerial")
    private Integer absSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "opis")
    private String opis;
    @Size(max = 1)
    @Column(name = "kod")
    private Character kod;
    @Size(max = 1)
    @Column(name = "redukcjawyn")
    private Character redukcjawyn;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajnieobecnosci")
    private List<Swiadczeniekodzus> swiadczeniekodzusList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajnieobecnosci")
    private List<Nieobecnosc> nieobecnoscList;

    public Rodzajnieobecnosci() {
    }

    public Rodzajnieobecnosci(Integer id) {
        this.id = id;
    }

    public Rodzajnieobecnosci(Integer id, String opis) {
        this.id = id;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAbsSerial() {
        return absSerial;
    }

    public void setAbsSerial(Integer absSerial) {
        this.absSerial = absSerial;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Character getKod() {
        return kod;
    }

    public void setKod(Character kod) {
        this.kod = kod;
    }

    public Character getRedukcjawyn() {
        return redukcjawyn;
    }

    public void setRedukcjawyn(Character redukcjawyn) {
        this.redukcjawyn = redukcjawyn;
    }

   
    @XmlTransient
    public List<Swiadczeniekodzus> getSwiadczeniekodzusList() {
        return swiadczeniekodzusList;
    }

    public void setSwiadczeniekodzusList(List<Swiadczeniekodzus> nieobecnoscList) {
        this.swiadczeniekodzusList = nieobecnoscList;
    }

    @XmlTransient
    public List<Nieobecnosc> getNieobecnoscList() {
        return nieobecnoscList;
    }

    public void setNieobecnoscList(List<Nieobecnosc> nieobecnoscList) {
        this.nieobecnoscList = nieobecnoscList;
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
        if (!(object instanceof Rodzajnieobecnosci)) {
            return false;
        }
        Rodzajnieobecnosci other = (Rodzajnieobecnosci) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rodzajnieobecnosci[ id=" + id + " ]";
    }
    
}
