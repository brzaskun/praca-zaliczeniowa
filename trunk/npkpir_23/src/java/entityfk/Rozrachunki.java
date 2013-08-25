/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entityfk.Zapisynakoncie;
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
    @NamedQuery(name = "Rozrachunki.findByKwota", query = "SELECT r FROM Rozrachunki r WHERE r.kwota = :kwota")})
public class Rozrachunki implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RozrachunkiPK rozrachunkiPK;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double kwota;
    @JoinColumn(name = "zapisrozliczany", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Zapisynakoncie zapisrozliczany;
    @JoinColumn(name = "zapissparowany", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Zapisynakoncie zapissparowany;

    public Rozrachunki() {
    }

    public Rozrachunki(RozrachunkiPK rozrachunkiPK) {
        this.rozrachunkiPK = rozrachunkiPK;
    }

    public Rozrachunki(RozrachunkiPK rozrachunkiPK, double kwota) {
        this.rozrachunkiPK = rozrachunkiPK;
        this.kwota = kwota;
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

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public Zapisynakoncie getZapisrozliczany() {
        return zapisrozliczany;
    }

    public void setZapisrozliczany(Zapisynakoncie zapisrozliczany) {
        this.zapisrozliczany = zapisrozliczany;
    }

    public Zapisynakoncie getZapissparowany() {
        return zapissparowany;
    }

    public void setZapissparowany(Zapisynakoncie zapissparowany) {
        this.zapissparowany = zapissparowany;
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
        return "entity.Rozrachunki[ rozrachunkiPK=" + rozrachunkiPK + " ]";
    }
    
}
