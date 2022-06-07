/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "TMP_TAB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpTab.findAll", query = "SELECT t FROM TmpTab t"),
    @NamedQuery(name = "TmpTab.findById", query = "SELECT t FROM TmpTab t WHERE t.id = :id"),
    @NamedQuery(name = "TmpTab.findByIdPlatnik", query = "SELECT t FROM TmpTab t WHERE t.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "TmpTab.findById1", query = "SELECT t FROM TmpTab t WHERE t.id1 = :id1"),
    @NamedQuery(name = "TmpTab.findByBlok1", query = "SELECT t FROM TmpTab t WHERE t.blok1 = :blok1"),
    @NamedQuery(name = "TmpTab.findByTyp1", query = "SELECT t FROM TmpTab t WHERE t.typ1 = :typ1"),
    @NamedQuery(name = "TmpTab.findById2", query = "SELECT t FROM TmpTab t WHERE t.id2 = :id2"),
    @NamedQuery(name = "TmpTab.findByBlok2", query = "SELECT t FROM TmpTab t WHERE t.blok2 = :blok2"),
    @NamedQuery(name = "TmpTab.findByTyp2", query = "SELECT t FROM TmpTab t WHERE t.typ2 = :typ2"),
    @NamedQuery(name = "TmpTab.findByIdUbezpieczony", query = "SELECT t FROM TmpTab t WHERE t.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "TmpTab.findByIdraps", query = "SELECT t FROM TmpTab t WHERE t.idraps = :idraps"),
    @NamedQuery(name = "TmpTab.findByIdokrrol", query = "SELECT t FROM TmpTab t WHERE t.idokrrol = :idokrrol"),
    @NamedQuery(name = "TmpTab.findByKodtytub", query = "SELECT t FROM TmpTab t WHERE t.kodtytub = :kodtytub"),
    @NamedQuery(name = "TmpTab.findByPrdoem", query = "SELECT t FROM TmpTab t WHERE t.prdoem = :prdoem"),
    @NamedQuery(name = "TmpTab.findByStniep", query = "SELECT t FROM TmpTab t WHERE t.stniep = :stniep"),
    @NamedQuery(name = "TmpTab.findByPodwymer", query = "SELECT t FROM TmpTab t WHERE t.podwymer = :podwymer"),
    @NamedQuery(name = "TmpTab.findByPodwymciw", query = "SELECT t FROM TmpTab t WHERE t.podwymciw = :podwymciw"),
    @NamedQuery(name = "TmpTab.findByPodwymzdr", query = "SELECT t FROM TmpTab t WHERE t.podwymzdr = :podwymzdr"),
    @NamedQuery(name = "TmpTab.findByLiczba", query = "SELECT t FROM TmpTab t WHERE t.liczba = :liczba"),
    @NamedQuery(name = "TmpTab.findByNumer", query = "SELECT t FROM TmpTab t WHERE t.numer = :numer"),
    @NamedQuery(name = "TmpTab.findByNazwisko", query = "SELECT t FROM TmpTab t WHERE t.nazwisko = :nazwisko"),
    @NamedQuery(name = "TmpTab.findByImie", query = "SELECT t FROM TmpTab t WHERE t.imie = :imie"),
    @NamedQuery(name = "TmpTab.findByPlatnikPrzekaz", query = "SELECT t FROM TmpTab t WHERE t.platnikPrzekaz = :platnikPrzekaz"),
    @NamedQuery(name = "TmpTab.findByTypid", query = "SELECT t FROM TmpTab t WHERE t.typid = :typid"),
    @NamedQuery(name = "TmpTab.findByIdentyfik", query = "SELECT t FROM TmpTab t WHERE t.identyfik = :identyfik"),
    @NamedQuery(name = "TmpTab.findByData1", query = "SELECT t FROM TmpTab t WHERE t.data1 = :data1"),
    @NamedQuery(name = "TmpTab.findByData2", query = "SELECT t FROM TmpTab t WHERE t.data2 = :data2"),
    @NamedQuery(name = "TmpTab.findByIdProgram", query = "SELECT t FROM TmpTab t WHERE t.idProgram = :idProgram")})
public class TmpTab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_1")
    private Integer id1;
    @Column(name = "BLOK_1")
    private Integer blok1;
    @Column(name = "TYP_1")
    private Integer typ1;
    @Column(name = "ID_2")
    private Integer id2;
    @Column(name = "BLOK_2")
    private Integer blok2;
    @Column(name = "TYP_2")
    private Integer typ2;
    @Column(name = "ID_UBEZPIECZONY")
    private Integer idUbezpieczony;
    @Size(max = 2)
    @Column(name = "IDRAPS", length = 2)
    private String idraps;
    @Size(max = 6)
    @Column(name = "IDOKRROL", length = 6)
    private String idokrrol;
    @Size(max = 6)
    @Column(name = "KODTYTUB", length = 6)
    private String kodtytub;
    @Column(name = "PRDOEM")
    private Character prdoem;
    @Column(name = "STNIEP")
    private Character stniep;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PODWYMER", precision = 18, scale = 2)
    private BigDecimal podwymer;
    @Column(name = "PODWYMCIW", precision = 18, scale = 2)
    private BigDecimal podwymciw;
    @Column(name = "PODWYMZDR", precision = 18, scale = 2)
    private BigDecimal podwymzdr;
    @Column(name = "LICZBA")
    private Integer liczba;
    @Column(name = "NUMER")
    private Integer numer;
    @Size(max = 31)
    @Column(name = "NAZWISKO", length = 31)
    private String nazwisko;
    @Size(max = 22)
    @Column(name = "IMIE", length = 22)
    private String imie;
    @Size(max = 55)
    @Column(name = "PLATNIK_PRZEKAZ", length = 55)
    private String platnikPrzekaz;
    @Column(name = "TYPID")
    private Character typid;
    @Size(max = 11)
    @Column(name = "IDENTYFIK", length = 11)
    private String identyfik;
    @Column(name = "DATA1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data1;
    @Column(name = "DATA2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data2;
    @Column(name = "ID_PROGRAM")
    private Integer idProgram;

    public TmpTab() {
    }

    public TmpTab(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public Integer getBlok1() {
        return blok1;
    }

    public void setBlok1(Integer blok1) {
        this.blok1 = blok1;
    }

    public Integer getTyp1() {
        return typ1;
    }

    public void setTyp1(Integer typ1) {
        this.typ1 = typ1;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }

    public Integer getBlok2() {
        return blok2;
    }

    public void setBlok2(Integer blok2) {
        this.blok2 = blok2;
    }

    public Integer getTyp2() {
        return typ2;
    }

    public void setTyp2(Integer typ2) {
        this.typ2 = typ2;
    }

    public Integer getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(Integer idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public String getIdraps() {
        return idraps;
    }

    public void setIdraps(String idraps) {
        this.idraps = idraps;
    }

    public String getIdokrrol() {
        return idokrrol;
    }

    public void setIdokrrol(String idokrrol) {
        this.idokrrol = idokrrol;
    }

    public String getKodtytub() {
        return kodtytub;
    }

    public void setKodtytub(String kodtytub) {
        this.kodtytub = kodtytub;
    }

    public Character getPrdoem() {
        return prdoem;
    }

    public void setPrdoem(Character prdoem) {
        this.prdoem = prdoem;
    }

    public Character getStniep() {
        return stniep;
    }

    public void setStniep(Character stniep) {
        this.stniep = stniep;
    }

    public BigDecimal getPodwymer() {
        return podwymer;
    }

    public void setPodwymer(BigDecimal podwymer) {
        this.podwymer = podwymer;
    }

    public BigDecimal getPodwymciw() {
        return podwymciw;
    }

    public void setPodwymciw(BigDecimal podwymciw) {
        this.podwymciw = podwymciw;
    }

    public BigDecimal getPodwymzdr() {
        return podwymzdr;
    }

    public void setPodwymzdr(BigDecimal podwymzdr) {
        this.podwymzdr = podwymzdr;
    }

    public Integer getLiczba() {
        return liczba;
    }

    public void setLiczba(Integer liczba) {
        this.liczba = liczba;
    }

    public Integer getNumer() {
        return numer;
    }

    public void setNumer(Integer numer) {
        this.numer = numer;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getPlatnikPrzekaz() {
        return platnikPrzekaz;
    }

    public void setPlatnikPrzekaz(String platnikPrzekaz) {
        this.platnikPrzekaz = platnikPrzekaz;
    }

    public Character getTypid() {
        return typid;
    }

    public void setTypid(Character typid) {
        this.typid = typid;
    }

    public String getIdentyfik() {
        return identyfik;
    }

    public void setIdentyfik(String identyfik) {
        this.identyfik = identyfik;
    }

    public Date getData1() {
        return data1;
    }

    public void setData1(Date data1) {
        this.data1 = data1;
    }

    public Date getData2() {
        return data2;
    }

    public void setData2(Date data2) {
        this.data2 = data2;
    }

    public Integer getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(Integer idProgram) {
        this.idProgram = idProgram;
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
        if (!(object instanceof TmpTab)) {
            return false;
        }
        TmpTab other = (TmpTab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.TmpTab[ id=" + id + " ]";
    }
    
}
