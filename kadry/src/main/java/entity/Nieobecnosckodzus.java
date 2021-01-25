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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "nieobecnosckodzus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nieobecnosckodzus.findAll", query = "SELECT n FROM Nieobecnosckodzus n"),
    @NamedQuery(name = "Nieobecnosckodzus.findById", query = "SELECT n FROM Nieobecnosckodzus n WHERE n.id = :id"),
    @NamedQuery(name = "Nieobecnosckodzus.findByKod", query = "SELECT n FROM Nieobecnosckodzus n WHERE n.kod = :kod"),
    @NamedQuery(name = "Nieobecnosckodzus.findByOpis", query = "SELECT n FROM Nieobecnosckodzus n WHERE n.opis = :opis"),
    @NamedQuery(name = "Nieobecnosckodzus.findByOpisskrocony", query = "SELECT n FROM Nieobecnosckodzus n WHERE n.opisskrocony = :opisskrocony"),
    @NamedQuery(name = "Nieobecnosckodzus.findByPodatek", query = "SELECT n FROM Nieobecnosckodzus n WHERE n.podatek = :podatek"),
    @NamedQuery(name = "Nieobecnosckodzus.findBySpoleczne", query = "SELECT n FROM Nieobecnosckodzus n WHERE n.spoleczne = :spoleczne"),
    @NamedQuery(name = "Nieobecnosckodzus.findByZdrowotne", query = "SELECT n FROM Nieobecnosckodzus n WHERE n.zdrowotne = :zdrowotne")})
public class Nieobecnosckodzus implements Serializable {

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
    @Column(name = "swiadczeniezus")
    private boolean swiadczeniezus;
    @Column(name = "urlop")
    private boolean urlop;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "zdrowotne")
    private Integer zdrowotne;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nieobecnosckodzus")
    private List<Nieobecnosc> nieobecnoscList;

    public Nieobecnosckodzus() {
    }

    public Nieobecnosckodzus(Integer id) {
        this.id = id;
    }

    public Nieobecnosckodzus(Integer id, String opis, String opisskrocony) {
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


    public Integer getZdrowotne() {
        return zdrowotne;
    }

    public void setZdrowotne(Integer zdrowotne) {
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
        if (!(object instanceof Nieobecnosckodzus)) {
            return false;
        }
        Nieobecnosckodzus other = (Nieobecnosckodzus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Nieobecnosckodzus[ id=" + id + " ]";
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

    public boolean getSwiadczeniezus() {
        return swiadczeniezus;
    }

    public void setSwiadczeniezus(boolean swiadczeniezus) {
        this.swiadczeniezus = swiadczeniezus;
    }

    public boolean getUrlop() {
        return urlop;
    }

    public void setUrlop(boolean urlop) {
        this.urlop = urlop;
    }
    
}
