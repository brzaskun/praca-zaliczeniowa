/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "pozycjenafakturze", uniqueConstraints = {
    @UniqueConstraint(columnNames={"podid","nazwa"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pozycjenafakturze.findAll", query = "SELECT p FROM Pozycjenafakturze p"),
    @NamedQuery(name = "Pozycjenafakturze.findByAktywny", query = "SELECT p FROM Pozycjenafakturze p WHERE p.aktywny = :aktywny"),
    @NamedQuery(name = "Pozycjenafakturze.findByGora", query = "SELECT p FROM Pozycjenafakturze p WHERE p.gora = :gora"),
    @NamedQuery(name = "Pozycjenafakturze.findByLewy", query = "SELECT p FROM Pozycjenafakturze p WHERE p.lewy = :lewy"),
    @NamedQuery(name = "Pozycjenafakturze.findByNazwa", query = "SELECT p FROM Pozycjenafakturze p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "Pozycjenafakturze.findByNazwaCo", query = "SELECT p FROM Pozycjenafakturze p WHERE p.nazwa = :nazwa AND  p.podid = :podatnik"),
    @NamedQuery(name = "Pozycjenafakturze.findByPodatnik", query = "SELECT p FROM Pozycjenafakturze p WHERE p.podid = :podatnik")})
public class Pozycjenafakturze implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aktywny")
    private boolean aktywny;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gora")
    private int gora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lewy")
    private int lewy;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nazwa")
    private String nazwa;

    public Pozycjenafakturze() {
    }

    public Pozycjenafakturze(String co, Podatnik podatnikObiekt, int gora, int lewy) {
        this.nazwa = co;
        this.podid = podatnikObiekt;
        this.aktywny = true;
        this.gora = gora;
        this.lewy = lewy;
    }

    

    public boolean getAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    public int getGora() {
        return gora;
    }

    public void setGora(int gora) {
        this.gora = gora;
    }

    public int getLewy() {
        return lewy;
    }

    public void setLewy(int lewy) {
        this.lewy = lewy;
    }

    public Podatnik getPodid() {
        return podid;
    }

    public void setPodid(Podatnik podid) {
        this.podid = podid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.podid);
        hash = 67 * hash + Objects.hashCode(this.nazwa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pozycjenafakturze other = (Pozycjenafakturze) obj;
        if (!Objects.equals(this.nazwa, other.nazwa)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.podid, other.podid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pozycjenafakturze{" + "aktywny=" + aktywny + ", gora=" + gora + ", lewy=" + lewy + ", podid=" + podid.getNazwapelna() + ", nazwa=" + nazwa + '}';
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

  

    
    
}
