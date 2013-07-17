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
import javax.persistence.Lob;
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
@Table(name = "kontozapisy", catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kontozapisy.findAll", query = "SELECT k FROM Kontozapisy k"),
    @NamedQuery(name = "Kontozapisy.findByPodatnik", query = "SELECT k FROM Kontozapisy k WHERE k.kontozapisyPK.podatnik = :podatnik"),
    @NamedQuery(name = "Kontozapisy.findByKonto", query = "SELECT k FROM Kontozapisy k WHERE k.kontozapisyPK.konto = :konto"),
    @NamedQuery(name = "Kontozapisy.findByOpis", query = "SELECT k FROM Kontozapisy k WHERE k.opis = :opis"),
    @NamedQuery(name = "Kontozapisy.findByKwotawn", query = "SELECT k FROM Kontozapisy k WHERE k.kwotawn = :kwotawn"),
    @NamedQuery(name = "Kontozapisy.findByKontown", query = "SELECT k FROM Kontozapisy k WHERE k.kontown = :kontown"),
    @NamedQuery(name = "Kontozapisy.findByKwotama", query = "SELECT k FROM Kontozapisy k WHERE k.kwotama = :kwotama"),
    @NamedQuery(name = "Kontozapisy.findByKontoma", query = "SELECT k FROM Kontozapisy k WHERE k.kontoma = :kontoma")})
public class Kontozapisy implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KontozapisyPK kontozapisyPK;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "kontoob", nullable = false)
    private Konto kontoob;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "dokument", nullable = false)
    private Dokfk dokument;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "opis", nullable = false, length = 255)
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kwotawn", nullable = false)
    private double kwotawn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "kontown", nullable = false, length = 100)
    private String kontown;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kwotama", nullable = false)
    private double kwotama;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "kontoma", nullable = false, length = 100)
    private String kontoma;

    public Kontozapisy() {
    }

    public Kontozapisy(KontozapisyPK kontozapisyPK) {
        this.kontozapisyPK = kontozapisyPK;
    }

    public Kontozapisy(KontozapisyPK kontozapisyPK, Konto kontoob, Dokfk dokument, String opis, double kwotawn, String kontown, double kwotama, String kontoma) {
        this.kontozapisyPK = kontozapisyPK;
        this.kontoob = kontoob;
        this.dokument = dokument;
        this.opis = opis;
        this.kwotawn = kwotawn;
        this.kontown = kontown;
        this.kwotama = kwotama;
        this.kontoma = kontoma;
    }

    public Kontozapisy(String podatnik, String konto) {
        this.kontozapisyPK = new KontozapisyPK(podatnik, konto);
    }

    public KontozapisyPK getKontozapisyPK() {
        return kontozapisyPK;
    }

    public void setKontozapisyPK(KontozapisyPK kontozapisyPK) {
        this.kontozapisyPK = kontozapisyPK;
    }

    public Konto getKontoob() {
        return kontoob;
    }

    public void setKontoob(Konto kontoob) {
        this.kontoob = kontoob;
    }

    public Dokfk getDokument() {
        return dokument;
    }

    public void setDokument(Dokfk dokument) {
        this.dokument = dokument;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getKwotawn() {
        return kwotawn;
    }

    public void setKwotawn(double kwotawn) {
        this.kwotawn = kwotawn;
    }

    public String getKontown() {
        return kontown;
    }

    public void setKontown(String kontown) {
        this.kontown = kontown;
    }

    public double getKwotama() {
        return kwotama;
    }

    public void setKwotama(double kwotama) {
        this.kwotama = kwotama;
    }

    public String getKontoma() {
        return kontoma;
    }

    public void setKontoma(String kontoma) {
        this.kontoma = kontoma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kontozapisyPK != null ? kontozapisyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kontozapisy)) {
            return false;
        }
        Kontozapisy other = (Kontozapisy) object;
        if ((this.kontozapisyPK == null && other.kontozapisyPK != null) || (this.kontozapisyPK != null && !this.kontozapisyPK.equals(other.kontozapisyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Kontozapisy[ kontozapisyPK=" + kontozapisyPK + " ]";
    }
    
}
