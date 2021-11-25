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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "swiadczeniekodzus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Swiadczeniekodzus.findAll", query = "SELECT n FROM Swiadczeniekodzus n"),
    @NamedQuery(name = "Swiadczeniekodzus.findById", query = "SELECT n FROM Swiadczeniekodzus n WHERE n.id = :id"),
    @NamedQuery(name = "Swiadczeniekodzus.findByKod", query = "SELECT n FROM Swiadczeniekodzus n WHERE n.kod = :kod"),
    @NamedQuery(name = "Swiadczeniekodzus.findByOpis", query = "SELECT n FROM Swiadczeniekodzus n WHERE n.opis = :opis"),
    @NamedQuery(name = "Swiadczeniekodzus.findByAktywne", query = "SELECT n FROM Swiadczeniekodzus n WHERE n.aktywne = TRUE"),
    @NamedQuery(name = "Swiadczeniekodzus.findByRodzajnieobecnosci", query = "SELECT n FROM Swiadczeniekodzus n WHERE n.rodzajnieobecnosci = :rodzajnieobecnosci"),
    @NamedQuery(name = "Swiadczeniekodzus.findByOpisskrocony", query = "SELECT n FROM Swiadczeniekodzus n WHERE n.opisskrocony = :opisskrocony"),
    @NamedQuery(name = "Swiadczeniekodzus.findByPodatek", query = "SELECT n FROM Swiadczeniekodzus n WHERE n.podatek = :podatek"),
    @NamedQuery(name = "Swiadczeniekodzus.findBySpoleczne", query = "SELECT n FROM Swiadczeniekodzus n WHERE n.spoleczne = :spoleczne"),
    @NamedQuery(name = "Swiadczeniekodzus.findByZdrowotne", query = "SELECT n FROM Swiadczeniekodzus n WHERE n.zdrowotne = :zdrowotne")})
public class Swiadczeniekodzus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "wkp_serial")
    private Integer wkp_serial;
    @Column(name = "wkp_abs_serial")
    private Integer wkp_abs_serial;
    @JoinColumn(name = "rodzajnieobecnosci", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rodzajnieobecnosci rodzajnieobecnosci;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "kod")
    private String kod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "opis")
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "opisskrocony")
    private String opisskrocony;
    @Column(name = "podatek")
    private boolean podatek;
    @Column(name = "spoleczne")
    private boolean spoleczne;
    @Column(name = "zrodlofinansowania")
    private Character zrodlofinansowania;
    @Column(name = "przerwa")
    private boolean przerwa;
    @Column(name = "aktywne")
    private  boolean aktywne;
    @Column(name = "zdrowotne")
    private boolean zdrowotne;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "swiadczeniekodzus")
    private List<Nieobecnosc> nieobecnoscList;

    public Swiadczeniekodzus() {
    }

    public Swiadczeniekodzus(Integer id) {
        this.id = id;
    }

    public Swiadczeniekodzus(Integer id, String opis, String opisskrocony) {
        this.id = id;
        this.opis = opis;
        this.opisskrocony = opisskrocony;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWkp_serial() {
        return wkp_serial;
    }

    public void setWkp_serial(Integer wkp_serial) {
        this.wkp_serial = wkp_serial;
    }


    public boolean getZdrowotne() {
        return zdrowotne;
    }

    public void setZdrowotne(boolean zdrowotne) {
        this.zdrowotne = zdrowotne;
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
        if (!(object instanceof Swiadczeniekodzus)) {
            return false;
        }
        Swiadczeniekodzus other = (Swiadczeniekodzus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Swiadczeniekodzus[ id=" + id + " ]";
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getOpisskrocony() {
        return opisskrocony;
    }

    public void setOpisskrocony(String opisskrocony) {
        this.opisskrocony = opisskrocony;
    }

    public boolean getPodatek() {
        return podatek;
    }

    public void setPodatek(boolean podatek) {
        this.podatek = podatek;
    }

    public boolean getSpoleczne() {
        return spoleczne;
    }

    public void setSpoleczne(boolean spoleczne) {
        this.spoleczne = spoleczne;
    }

    public Integer getWkp_abs_serial() {
        return wkp_abs_serial;
    }

    public void setWkp_abs_serial(Integer wkp_abs_serial) {
        this.wkp_abs_serial = wkp_abs_serial;
    }

    public Rodzajnieobecnosci getRodzajnieobecnosci() {
        return rodzajnieobecnosci;
    }

    public void setRodzajnieobecnosci(Rodzajnieobecnosci rodzajnieobecnosci) {
        this.rodzajnieobecnosci = rodzajnieobecnosci;
    }

    public Character getZrodlofinansowania() {
        return zrodlofinansowania;
    }

    public void setZrodlofinansowania(Character zrodlofinansowania) {
        this.zrodlofinansowania = zrodlofinansowania;
    }

    public boolean isPrzerwa() {
        return przerwa;
    }

    public void setPrzerwa(boolean przerwa) {
        this.przerwa = przerwa;
    }


    public boolean isAktywne() {
        return aktywne;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }
    
}
