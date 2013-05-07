/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "pozycjenafakturze")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pozycjenafakturze.findAll", query = "SELECT p FROM Pozycjenafakturze p"),
    @NamedQuery(name = "Pozycjenafakturze.findById", query = "SELECT p FROM Pozycjenafakturze p WHERE p.id = :id"),
    @NamedQuery(name = "Pozycjenafakturze.findByPodatnik", query = "SELECT p FROM Pozycjenafakturze p WHERE p.podatnik = :podatnik"),
    @NamedQuery(name = "Pozycjenafakturze.findByNazwa", query = "SELECT p FROM Pozycjenafakturze p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "Pozycjenafakturze.findByLewy", query = "SELECT p FROM Pozycjenafakturze p WHERE p.lewy = :lewy"),
    @NamedQuery(name = "Pozycjenafakturze.findByGora", query = "SELECT p FROM Pozycjenafakturze p WHERE p.gora = :gora"),
    @NamedQuery(name = "Pozycjenafakturze.findByAktywny", query = "SELECT p FROM Pozycjenafakturze p WHERE p.aktywny = :aktywny")})
public class Pozycjenafakturze implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "podatnik")
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lewy")
    private int lewy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gora")
    private int gora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aktywny")
    private boolean aktywny;

    public Pozycjenafakturze() {
    }

    public Pozycjenafakturze(Integer id) {
        this.id = id;
    }

    public Pozycjenafakturze(Integer id, String podatnik, String nazwa, int lewy, int gora, boolean aktywny) {
        this.id = id;
        this.podatnik = podatnik;
        this.nazwa = nazwa;
        this.lewy = lewy;
        this.gora = gora;
        this.aktywny = aktywny;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getLewy() {
        return lewy;
    }

    public void setLewy(int lewy) {
        this.lewy = lewy;
    }

    public int getGora() {
        return gora;
    }

    public void setGora(int gora) {
        this.gora = gora;
    }

    public boolean getAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
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
        if (!(object instanceof Pozycjenafakturze)) {
            return false;
        }
        Pozycjenafakturze other = (Pozycjenafakturze) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pozycjenafakturze[ id=" + id + " ]";
    }
    
}
