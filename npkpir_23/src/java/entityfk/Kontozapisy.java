/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kontozapisy.findAll", query = "SELECT k FROM Kontozapisy k"),
    @NamedQuery(name = "Kontozapisy.findById", query = "SELECT k FROM Kontozapisy k WHERE k.id = :id"),
    @NamedQuery(name = "Kontozapisy.findByIdDokfk", query = "SELECT k FROM Kontozapisy k WHERE k.idDokfk = :idDokfk"),
    @NamedQuery(name = "Kontozapisy.findByPodatnik", query = "SELECT k FROM Kontozapisy k WHERE k.podatnik = :podatnik"),
    @NamedQuery(name = "Kontozapisy.findByKonto", query = "SELECT k FROM Kontozapisy k WHERE k.konto = :konto"),
    @NamedQuery(name = "Kontozapisy.findByKontoprzeciwstawne", query = "SELECT k FROM Kontozapisy k WHERE k.kontoprzeciwstawne = :kontoprzeciwstawne"),
    @NamedQuery(name = "Kontozapisy.findByNumer", query = "SELECT k FROM Kontozapisy k WHERE k.numer = :numer"),
    @NamedQuery(name = "Kontozapisy.findByOpis", query = "SELECT k FROM Kontozapisy k WHERE k.opis = :opis"),
    @NamedQuery(name = "Kontozapisy.findByKwotawn", query = "SELECT k FROM Kontozapisy k WHERE k.kwotawn = :kwotawn"),
    @NamedQuery(name = "Kontozapisy.findByKontown", query = "SELECT k FROM Kontozapisy k WHERE k.kontown = :kontown"),
    @NamedQuery(name = "Kontozapisy.findByKwotama", query = "SELECT k FROM Kontozapisy k WHERE k.kwotama = :kwotama"),
    @NamedQuery(name = "Kontozapisy.findByKontoma", query = "SELECT k FROM Kontozapisy k WHERE k.kontoma = :kontoma"),
    @NamedQuery(name = "Kontozapisy.findByWartoscpierwotna", query = "SELECT k FROM Kontozapisy k WHERE k.wartoscpierwotna = :wartoscpierwotna"),
    @NamedQuery(name = "Kontozapisy.findByDorozliczenia", query = "SELECT k FROM Kontozapisy k WHERE k.dorozliczenia = :dorozliczenia")})
public class Kontozapisy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int idDokfk;
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
    private byte[] kontoob;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String kontoprzeciwstawne;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private byte[] dokument;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String numer;
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
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double wartoscpierwotna;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double dorozliczenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kontozapisy")
    private List<Rozrachunki> rozrachunkiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kontozapisy1")
    private List<Rozrachunki> rozrachunkiList1;

    public Kontozapisy() {
    }

    public Kontozapisy(Integer id) {
        this.id = id;
    }

    public Kontozapisy(Integer id, int idDokfk, String podatnik, String konto, byte[] kontoob, String kontoprzeciwstawne, byte[] dokument, String numer, String opis, double kwotawn, String kontown, double kwotama, String kontoma, double wartoscpierwotna, double dorozliczenia) {
        this.id = id;
        this.idDokfk = idDokfk;
        this.podatnik = podatnik;
        this.konto = konto;
        this.kontoob = kontoob;
        this.kontoprzeciwstawne = kontoprzeciwstawne;
        this.dokument = dokument;
        this.numer = numer;
        this.opis = opis;
        this.kwotawn = kwotawn;
        this.kontown = kontown;
        this.kwotama = kwotama;
        this.kontoma = kontoma;
        this.wartoscpierwotna = wartoscpierwotna;
        this.dorozliczenia = dorozliczenia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdDokfk() {
        return idDokfk;
    }

    public void setIdDokfk(int idDokfk) {
        this.idDokfk = idDokfk;
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

    public byte[] getKontoob() {
        return kontoob;
    }

    public void setKontoob(byte[] kontoob) {
        this.kontoob = kontoob;
    }

    public String getKontoprzeciwstawne() {
        return kontoprzeciwstawne;
    }

    public void setKontoprzeciwstawne(String kontoprzeciwstawne) {
        this.kontoprzeciwstawne = kontoprzeciwstawne;
    }

    public byte[] getDokument() {
        return dokument;
    }

    public void setDokument(byte[] dokument) {
        this.dokument = dokument;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
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

    public double getWartoscpierwotna() {
        return wartoscpierwotna;
    }

    public void setWartoscpierwotna(double wartoscpierwotna) {
        this.wartoscpierwotna = wartoscpierwotna;
    }

    public double getDorozliczenia() {
        return dorozliczenia;
    }

    public void setDorozliczenia(double dorozliczenia) {
        this.dorozliczenia = dorozliczenia;
    }

    @XmlTransient
    public List<Rozrachunki> getRozrachunkiList() {
        return rozrachunkiList;
    }

    public void setRozrachunkiList(List<Rozrachunki> rozrachunkiList) {
        this.rozrachunkiList = rozrachunkiList;
    }

    @XmlTransient
    public List<Rozrachunki> getRozrachunkiList1() {
        return rozrachunkiList1;
    }

    public void setRozrachunkiList1(List<Rozrachunki> rozrachunkiList1) {
        this.rozrachunkiList1 = rozrachunkiList1;
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
        return "entityfk.Kontozapisy[ id=" + id + " ]";
    }
    
}
