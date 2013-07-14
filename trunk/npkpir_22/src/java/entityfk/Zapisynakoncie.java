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
@Table(name = "zapisynakoncie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zapisynakoncie.findAll", query = "SELECT z FROM Zapisynakoncie z"),
    @NamedQuery(name = "Zapisynakoncie.findById", query = "SELECT z FROM Zapisynakoncie z WHERE z.id = :id"),
    @NamedQuery(name = "Zapisynakoncie.findByNumerpelnykonta", query = "SELECT z FROM Zapisynakoncie z WHERE z.numerpelnykonta = :numerpelnykonta"),
    @NamedQuery(name = "Zapisynakoncie.findByNazwakonta", query = "SELECT z FROM Zapisynakoncie z WHERE z.nazwakonta = :nazwakonta"),
    @NamedQuery(name = "Zapisynakoncie.findByPodatnik", query = "SELECT z FROM Zapisynakoncie z WHERE z.podatnik = :podatnik"),
    @NamedQuery(name = "Zapisynakoncie.findByKontrahent", query = "SELECT z FROM Zapisynakoncie z WHERE z.kontrahent = :kontrahent"),
    @NamedQuery(name = "Zapisynakoncie.findByKontown", query = "SELECT z FROM Zapisynakoncie z WHERE z.kontown = :kontown"),
    @NamedQuery(name = "Zapisynakoncie.findByKwotawn", query = "SELECT z FROM Zapisynakoncie z WHERE z.kwotawn = :kwotawn"),
    @NamedQuery(name = "Zapisynakoncie.findByKontoma", query = "SELECT z FROM Zapisynakoncie z WHERE z.kontoma = :kontoma"),
    @NamedQuery(name = "Zapisynakoncie.findByKwotama", query = "SELECT z FROM Zapisynakoncie z WHERE z.kwotama = :kwotama"),
    @NamedQuery(name = "Zapisynakoncie.findByMiesiac", query = "SELECT z FROM Zapisynakoncie z WHERE z.miesiac = :miesiac"),
    @NamedQuery(name = "Zapisynakoncie.findByRok", query = "SELECT z FROM Zapisynakoncie z WHERE z.rok = :rok"),
    @NamedQuery(name = "Zapisynakoncie.findByOpis", query = "SELECT z FROM Zapisynakoncie z WHERE z.opis = :opis"),
    @NamedQuery(name = "Zapisynakoncie.findByNrwldokumentu", query = "SELECT z FROM Zapisynakoncie z WHERE z.nrwldokumentu = :nrwldokumentu"),
    @NamedQuery(name = "Zapisynakoncie.findByIddok", query = "SELECT z FROM Zapisynakoncie z WHERE z.iddok = :iddok"),
    @NamedQuery(name = "Zapisynakoncie.findByDatadokumentu", query = "SELECT z FROM Zapisynakoncie z WHERE z.datadokumentu = :datadokumentu"),
    @NamedQuery(name = "Zapisynakoncie.findByZaksiegowany", query = "SELECT z FROM Zapisynakoncie z WHERE z.zaksiegowany = :zaksiegowany")})
public class Zapisynakoncie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "numerpelnykonta")
    private String numerpelnykonta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwakonta")
    private String nazwakonta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "podatnik")
    private String podatnik;
    @Size(max = 255)
    @Column(name = "kontrahent")
    private String kontrahent;
    @Size(max = 100)
    @Column(name = "kontown")
    private String kontown;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwotawn")
    private Double kwotawn;
    @Size(max = 100)
    @Column(name = "kontoma")
    private String kontoma;
    @Column(name = "kwotama")
    private Double kwotama;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "miesiac")
    private String miesiac;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rok")
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "opis")
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nrwldokumentu")
    private String nrwldokumentu;
    @Column(name = "iddok")
    private Integer iddok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "datadokumentu")
    private String datadokumentu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zaksiegowany")
    private boolean zaksiegowany;

    public Zapisynakoncie() {
    }

    public Zapisynakoncie(Integer id) {
        this.id = id;
    }

    public Zapisynakoncie(Integer id, String numerpelnykonta, String nazwakonta, String podatnik, String miesiac, String rok, String opis, String nrwldokumentu, String datadokumentu, boolean zaksiegowany) {
        this.id = id;
        this.numerpelnykonta = numerpelnykonta;
        this.nazwakonta = nazwakonta;
        this.podatnik = podatnik;
        this.miesiac = miesiac;
        this.rok = rok;
        this.opis = opis;
        this.nrwldokumentu = nrwldokumentu;
        this.datadokumentu = datadokumentu;
        this.zaksiegowany = zaksiegowany;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumerpelnykonta() {
        return numerpelnykonta;
    }

    public void setNumerpelnykonta(String numerpelnykonta) {
        this.numerpelnykonta = numerpelnykonta;
    }

    public String getNazwakonta() {
        return nazwakonta;
    }

    public void setNazwakonta(String nazwakonta) {
        this.nazwakonta = nazwakonta;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(String kontrahent) {
        this.kontrahent = kontrahent;
    }

    public String getKontown() {
        return kontown;
    }

    public void setKontown(String kontown) {
        this.kontown = kontown;
    }

    public Double getKwotawn() {
        return kwotawn;
    }

    public void setKwotawn(Double kwotawn) {
        this.kwotawn = kwotawn;
    }

    public String getKontoma() {
        return kontoma;
    }

    public void setKontoma(String kontoma) {
        this.kontoma = kontoma;
    }

    public Double getKwotama() {
        return kwotama;
    }

    public void setKwotama(Double kwotama) {
        this.kwotama = kwotama;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getNrwldokumentu() {
        return nrwldokumentu;
    }

    public void setNrwldokumentu(String nrwldokumentu) {
        this.nrwldokumentu = nrwldokumentu;
    }

    public Integer getIddok() {
        return iddok;
    }

    public void setIddok(Integer iddok) {
        this.iddok = iddok;
    }

    public String getDatadokumentu() {
        return datadokumentu;
    }

    public void setDatadokumentu(String datadokumentu) {
        this.datadokumentu = datadokumentu;
    }

    public boolean getZaksiegowany() {
        return zaksiegowany;
    }

    public void setZaksiegowany(boolean zaksiegowany) {
        this.zaksiegowany = zaksiegowany;
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
        if (!(object instanceof Zapisynakoncie)) {
            return false;
        }
        Zapisynakoncie other = (Zapisynakoncie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Zapisynakoncie[ id=" + id + " ]";
    }
    
}
