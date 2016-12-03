/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Pozycjenafakturze.findByAktywny", query = "SELECT p FROM Pozycjenafakturze p WHERE p.aktywny = :aktywny"),
    @NamedQuery(name = "Pozycjenafakturze.findByGora", query = "SELECT p FROM Pozycjenafakturze p WHERE p.gora = :gora"),
    @NamedQuery(name = "Pozycjenafakturze.findByLewy", query = "SELECT p FROM Pozycjenafakturze p WHERE p.lewy = :lewy"),
    @NamedQuery(name = "Pozycjenafakturze.findByNazwa", query = "SELECT p FROM Pozycjenafakturze p WHERE p.pozycjenafakturzePK.nazwa = :nazwa"),
    @NamedQuery(name = "Pozycjenafakturze.findByPodatnik", query = "SELECT p FROM Pozycjenafakturze p WHERE p.pozycjenafakturzePK.podatnik = :podatnik")})
public class Pozycjenafakturze implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PozycjenafakturzePK pozycjenafakturzePK;
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

    public Pozycjenafakturze() {
    }

    public Pozycjenafakturze(PozycjenafakturzePK pozycjenafakturzePK) {
        this.pozycjenafakturzePK = pozycjenafakturzePK;
    }

    public Pozycjenafakturze(PozycjenafakturzePK pozycjenafakturzePK, boolean aktywny, int gora, int lewy) {
        this.pozycjenafakturzePK = pozycjenafakturzePK;
        this.aktywny = aktywny;
        this.gora = gora;
        this.lewy = lewy;
    }

    public Pozycjenafakturze(String nazwa, String podatnik) {
        this.pozycjenafakturzePK = new PozycjenafakturzePK(nazwa, podatnik);
    }

    public PozycjenafakturzePK getPozycjenafakturzePK() {
        return pozycjenafakturzePK;
    }

    public void setPozycjenafakturzePK(PozycjenafakturzePK pozycjenafakturzePK) {
        this.pozycjenafakturzePK = pozycjenafakturzePK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pozycjenafakturzePK != null ? pozycjenafakturzePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pozycjenafakturze)) {
            return false;
        }
        Pozycjenafakturze other = (Pozycjenafakturze) object;
        if ((this.pozycjenafakturzePK == null && other.pozycjenafakturzePK != null) || (this.pozycjenafakturzePK != null && !this.pozycjenafakturzePK.equals(other.pozycjenafakturzePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pozycjenafakturze " + pozycjenafakturzePK.getNazwa() + ", aktywny=" + aktywny + ", gora=" + gora + ", lewy=" + lewy + '}';
    }

    
    
}
