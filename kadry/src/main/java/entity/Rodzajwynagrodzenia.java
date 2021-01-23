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
@Table(name = "rodzajwynagrodzenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rodzajwynagrodzenia.findAll", query = "SELECT r FROM Rodzajwynagrodzenia r"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findById", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.id = :id"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findByKod", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.kod = :kod"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findByOpispelny", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.opispelny = :opispelny"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findByOpisskrocony", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.opisskrocony = :opisskrocony")})
public class Rodzajwynagrodzenia implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "kod")
    private String kod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "opispelny")
    private String opispelny;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "opisskrocony")
    private String opisskrocony;
    @Column(name = "stale0zmienne1")
    private  boolean stale0zmienne1;
    @Column(name = "godzinowe0miesieczne1")
    private  boolean godzinowe0miesieczne1;
    @Column(name = "redukowany")
    private  boolean redukowany;
    @Column(name = "zus0bezzus1")
    private  boolean zus0bezzus1;
  

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajwynagrodzenia")
    private List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList;

    public Rodzajwynagrodzenia() {
        System.out.println("");
    }

    public Rodzajwynagrodzenia(Integer id) {
        this.id = id;
    }

    public Rodzajwynagrodzenia(Integer id, String kod, String opispelny, String opisskrocony) {
        this.id = id;
        this.kod = kod;
        this.opispelny = opispelny;
        this.opisskrocony = opisskrocony;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<Skladnikwynagrodzenia> getSkladnikwynagrodzeniaList() {
        return skladnikwynagrodzeniaList;
    }

    public void setSkladnikwynagrodzeniaList(List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList) {
        this.skladnikwynagrodzeniaList = skladnikwynagrodzeniaList;
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
        if (!(object instanceof Rodzajwynagrodzenia)) {
            return false;
        }
        Rodzajwynagrodzenia other = (Rodzajwynagrodzenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rodzajwynagrodzenia[ id=" + id + " ]";
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getOpispelny() {
        return opispelny;
    }

    public void setOpispelny(String opispelny) {
        this.opispelny = opispelny;
    }

    public String getOpisskrocony() {
        return opisskrocony;
    }

    public void setOpisskrocony(String opisskrocony) {
        this.opisskrocony = opisskrocony;
    }

    public  boolean getStale0zmienne1() {
        return stale0zmienne1;
    }

    public void setStale0zmienne1( boolean stale0zmienne1) {
        this.stale0zmienne1 = stale0zmienne1;
    }

    public  boolean getGodzinowe0miesieczne1() {
        return godzinowe0miesieczne1;
    }

    public void setGodzinowe0miesieczne1( boolean godzinowe0miesieczne1) {
        this.godzinowe0miesieczne1 = godzinowe0miesieczne1;
    }

    public  boolean getRedukowany() {
        return redukowany;
    }

    public void setRedukowany( boolean redukowany) {
        this.redukowany = redukowany;
    }

    public  boolean getZus0bezzus1() {
        return zus0bezzus1;
    }

    public void setZus0bezzus1( boolean zus0bezzus1) {
        this.zus0bezzus1 = zus0bezzus1;
    }

      
}
