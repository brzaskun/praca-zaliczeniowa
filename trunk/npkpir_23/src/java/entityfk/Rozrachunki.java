/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rozrachunki.findAll", query = "SELECT r FROM Rozrachunki r"),
    @NamedQuery(name = "Rozrachunki.findByZapisrozliczany", query = "SELECT r FROM Rozrachunki r WHERE r.rozrachunkiPK.zapisrozliczany = :zapisrozliczany"),
    @NamedQuery(name = "Rozrachunki.findByZapissparowany", query = "SELECT r FROM Rozrachunki r WHERE r.rozrachunkiPK.zapissparowany = :zapissparowany"),
    @NamedQuery(name = "Rozrachunki.findByKwotarozrachunku", query = "SELECT r FROM Rozrachunki r WHERE r.kwotarozrachunku = :kwotarozrachunku")})
public class Rozrachunki implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RozrachunkiPK rozrachunkiPK;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double kwotarozrachunku;
    @JoinColumn(name = "zapissparowany", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kontozapisy kontozapisy;
    @JoinColumn(name = "zapisrozliczany", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kontozapisy kontozapisy1;

    public Rozrachunki() {
    }

    public Rozrachunki(RozrachunkiPK rozrachunkiPK) {
        this.rozrachunkiPK = rozrachunkiPK;
    }

    public Rozrachunki(RozrachunkiPK rozrachunkiPK, double kwotarozrachunku) {
        this.rozrachunkiPK = rozrachunkiPK;
        this.kwotarozrachunku = kwotarozrachunku;
    }

    public Rozrachunki(int zapisrozliczany, int zapissparowany) {
        this.rozrachunkiPK = new RozrachunkiPK(zapisrozliczany, zapissparowany);
    }

    public RozrachunkiPK getRozrachunkiPK() {
        return rozrachunkiPK;
    }

    public void setRozrachunkiPK(RozrachunkiPK rozrachunkiPK) {
        this.rozrachunkiPK = rozrachunkiPK;
    }

    public double getKwotarozrachunku() {
        return kwotarozrachunku;
    }

    public void setKwotarozrachunku(double kwotarozrachunku) {
        this.kwotarozrachunku = kwotarozrachunku;
    }

    public Kontozapisy getKontozapisy() {
        return kontozapisy;
    }

    public void setKontozapisy(Kontozapisy kontozapisy) {
        this.kontozapisy = kontozapisy;
    }

    public Kontozapisy getKontozapisy1() {
        return kontozapisy1;
    }

    public void setKontozapisy1(Kontozapisy kontozapisy1) {
        this.kontozapisy1 = kontozapisy1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rozrachunkiPK != null ? rozrachunkiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rozrachunki)) {
            return false;
        }
        Rozrachunki other = (Rozrachunki) object;
        if ((this.rozrachunkiPK == null && other.rozrachunkiPK != null) || (this.rozrachunkiPK != null && !this.rozrachunkiPK.equals(other.rozrachunkiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Rozrachunki[ rozrachunkiPK=" + rozrachunkiPK + " ]";
    }
    
}
