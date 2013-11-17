/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
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
@Table(name = "konto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Konto.findAll", query = "SELECT k FROM Konto k"),
    @NamedQuery(name = "Konto.findById", query = "SELECT k FROM Konto k WHERE k.id = :id"),
    @NamedQuery(name = "Konto.findByPodatnik", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik"),
    @NamedQuery(name = "Konto.findByNrkonta", query = "SELECT k FROM Konto k WHERE k.nrkonta = :nrkonta"),
    @NamedQuery(name = "Konto.findBySyntetyczne", query = "SELECT k FROM Konto k WHERE k.syntetyczne = :syntetyczne"),
    @NamedQuery(name = "Konto.findByAnalityka", query = "SELECT k FROM Konto k WHERE k.analityka = :analityka"),
    @NamedQuery(name = "Konto.findByNazwapelna", query = "SELECT k FROM Konto k WHERE k.nazwapelna = :nazwapelna"),
    @NamedQuery(name = "Konto.findByNazwaskrocona", query = "SELECT k FROM Konto k WHERE k.nazwaskrocona = :nazwaskrocona"),
    @NamedQuery(name = "Konto.findByBilansowewynikowe", query = "SELECT k FROM Konto k WHERE k.bilansowewynikowe = :bilansowewynikowe"),
    @NamedQuery(name = "Konto.findByZwyklerozrachszczegolne", query = "SELECT k FROM Konto k WHERE k.zwyklerozrachszczegolne = :zwyklerozrachszczegolne"),
    @NamedQuery(name = "Konto.findByMacierzyste", query = "SELECT k FROM Konto k WHERE k.macierzyste = :macierzyste"),
    @NamedQuery(name = "Konto.findByPelnynumer", query = "SELECT k FROM Konto k WHERE k.pelnynumer = :pelnynumer"),
    @NamedQuery(name = "Konto.findByMapotomkow", query = "SELECT k FROM Konto k WHERE k.mapotomkow = :mapotomkow"),
    @NamedQuery(name = "Konto.findByRozwin", query = "SELECT k FROM Konto k WHERE k.rozwin = :rozwin")})
public class Konto implements Serializable {
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
    @Size(min = 1, max = 4)
    @Column(name = "nrkonta")
    private String nrkonta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "syntetyczne")
    private String syntetyczne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "analityka")
    private int analityka;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 350)
    @Column(name = "nazwapelna")
    private String nazwapelna;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nazwaskrocona")
    private String nazwaskrocona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "bilansowewynikowe")
    private String bilansowewynikowe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "zwyklerozrachszczegolne")
    private String zwyklerozrachszczegolne;
    @Lob
    @Column(name = "pozycja")
    private byte[] pozycja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "macierzyste")
    private String macierzyste;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pelnynumer")
    private String pelnynumer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mapotomkow")
    private boolean mapotomkow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rozwin")
    private boolean rozwin;
    @OneToMany(mappedBy = "kontoid")
    private List<Rozrachunekfk> rozrachunekfkList;

    public Konto() {
    }

    public Konto(Integer id) {
        this.id = id;
    }

    public Konto(Integer id, String podatnik, String nrkonta, String syntetyczne, int analityka, String nazwapelna, String nazwaskrocona, String bilansowewynikowe, String zwyklerozrachszczegolne, String macierzyste, String pelnynumer, boolean rozwin) {
        this.id = id;
        this.podatnik = podatnik;
        this.nrkonta = nrkonta;
        this.syntetyczne = syntetyczne;
        this.analityka = analityka;
        this.nazwapelna = nazwapelna;
        this.nazwaskrocona = nazwaskrocona;
        this.bilansowewynikowe = bilansowewynikowe;
        this.zwyklerozrachszczegolne = zwyklerozrachszczegolne;
        this.macierzyste = macierzyste;
        this.pelnynumer = pelnynumer;
        this.rozwin = rozwin;
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

    public String getNrkonta() {
        return nrkonta;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public String getSyntetyczne() {
        return syntetyczne;
    }

    public void setSyntetyczne(String syntetyczne) {
        this.syntetyczne = syntetyczne;
    }

    public int getAnalityka() {
        return analityka;
    }

    public void setAnalityka(int analityka) {
        this.analityka = analityka;
    }

    public String getNazwapelna() {
        return nazwapelna;
    }

    public void setNazwapelna(String nazwapelna) {
        this.nazwapelna = nazwapelna;
    }

    public String getNazwaskrocona() {
        return nazwaskrocona;
    }

    public void setNazwaskrocona(String nazwaskrocona) {
        this.nazwaskrocona = nazwaskrocona;
    }

    public String getBilansowewynikowe() {
        return bilansowewynikowe;
    }

    public void setBilansowewynikowe(String bilansowewynikowe) {
        this.bilansowewynikowe = bilansowewynikowe;
    }

    public String getZwyklerozrachszczegolne() {
        return zwyklerozrachszczegolne;
    }

    public void setZwyklerozrachszczegolne(String zwyklerozrachszczegolne) {
        this.zwyklerozrachszczegolne = zwyklerozrachszczegolne;
    }

    public byte[] getPozycja() {
        return pozycja;
    }

    public void setPozycja(byte[] pozycja) {
        this.pozycja = pozycja;
    }

    public String getMacierzyste() {
        return macierzyste;
    }

    public void setMacierzyste(String macierzyste) {
        this.macierzyste = macierzyste;
    }

    public String getPelnynumer() {
        return pelnynumer;
    }

    public void setPelnynumer(String pelnynumer) {
        this.pelnynumer = pelnynumer;
    }

    public boolean isMapotomkow() {
        return mapotomkow;
    }

    public void setMapotomkow(boolean mapotomkow) {
        this.mapotomkow = mapotomkow;
    }
    
    public boolean getRozwin() {
        return rozwin;
    }

    public void setRozwin(boolean rozwin) {
        this.rozwin = rozwin;
    }
    
    @XmlTransient
    public List<Rozrachunekfk> getRozrachunekfkList() {
        return rozrachunekfkList;
    }

    public void setRozrachunekfkList(List<Rozrachunekfk> rozrachunekfkList) {
        this.rozrachunekfkList = rozrachunekfkList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

  @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Konto other = (Konto) obj;
        if (!Objects.equals(this.pelnynumer, other.pelnynumer)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Konto[ id=" + id + " ]";
    }
    
}
