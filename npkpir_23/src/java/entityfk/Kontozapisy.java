/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kontozapisy.findAll", query = "SELECT k FROM Kontozapisy k"),
    @NamedQuery(name = "Kontozapisy.findById", query = "SELECT k FROM Kontozapisy k WHERE k.id = :id"),
    @NamedQuery(name = "Kontozapisy.findByPodatnik", query = "SELECT k FROM Kontozapisy k WHERE k.podatnik = :podatnik"),
    @NamedQuery(name = "Kontozapisy.findByKonto", query = "SELECT k FROM Kontozapisy k WHERE k.konto = :konto"),
    @NamedQuery(name = "Kontozapisy.findByKontoPodatnik", query = "SELECT k FROM Kontozapisy k WHERE k.podatnik = :podatnik AND k.konto = :konto"),
    @NamedQuery(name = "Kontozapisy.findByOpis", query = "SELECT k FROM Kontozapisy k WHERE k.opis = :opis"),
    @NamedQuery(name = "Kontozapisy.findByKwotawn", query = "SELECT k FROM Kontozapisy k WHERE k.kwotawn = :kwotawn"),
    @NamedQuery(name = "Kontozapisy.findByKontown", query = "SELECT k FROM Kontozapisy k WHERE k.kontown = :kontown"),
    @NamedQuery(name = "Kontozapisy.findByKwotama", query = "SELECT k FROM Kontozapisy k WHERE k.kwotama = :kwotama"),
    @NamedQuery(name = "Kontozapisy.findByKontoma", query = "SELECT k FROM Kontozapisy k WHERE k.kontoma = :kontoma")})
public class Kontozapisy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String konto;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private Konto kontoob;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String kontoprzeciwstawne;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private Dokfk dokument;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double kwotawn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String kontown;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double kwotama;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String kontoma;
    @ManyToOne(optional = false)
    private Wiersze wiersz;

    public Kontozapisy() {
    }

    public Kontozapisy(Integer id) {
        this.id = id;
    }

    public Kontozapisy(Integer id, String podatnik, String konto, Konto kontoob, Dokfk dokument, String opis, double kwotawn, String kontown, double kwotama, String kontoma) {
        this.id = id;
        this.podatnik = podatnik;
        this.konto = konto;
        this.kontoob = kontoob;
        this.dokument = dokument;
        this.opis = opis;
        this.kwotawn = kwotawn;
        this.kontown = kontown;
        this.kwotama = kwotama;
        this.kontoma = kontoma;
    }
    //<editor-fold defaultstate="collapsed" desc="comment">
    
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
    
    public String getKonto() {
        return konto;
    }
    
    public void setKonto(String konto) {
        this.konto = konto;
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
    
       
    public String getKontoprzeciwstawne() {
        return kontoprzeciwstawne;
    }
    
    public void setKontoprzeciwstawne(String kontoprzeciwstawne) {
        this.kontoprzeciwstawne = kontoprzeciwstawne;
    }
    
    public Wiersze getWiersz() {
        return wiersz;
    }
    
    public void setWiersz(Wiersze wiersz) {
        this.wiersz = wiersz;
    }
    
    
    //</editor-fold>
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kontozapisy)) {
            return false;
        }
        Kontozapisy other = (Kontozapisy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kontozapisy{" + "podatnik=" + podatnik + ", konto=" + konto + ", kontoprzeciwstawne=" + kontoprzeciwstawne + ", dokument=" + dokument +  ", opis=" + opis + '}';
    }

    
    
}
