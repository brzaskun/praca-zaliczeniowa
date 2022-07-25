/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "osoba_rod", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OsobaRod.findAll", query = "SELECT o FROM OsobaRod o"),
    @NamedQuery(name = "OsobaRod.findByOsrSerial", query = "SELECT o FROM OsobaRod o WHERE o.osrSerial = :osrSerial"),
    @NamedQuery(name = "OsobaRod.findByOsrPesel", query = "SELECT o FROM OsobaRod o WHERE o.osrPesel = :osrPesel"),
    @NamedQuery(name = "OsobaRod.findByOsrNip", query = "SELECT o FROM OsobaRod o WHERE o.osrNip = :osrNip"),
    @NamedQuery(name = "OsobaRod.findByOsrZusDok", query = "SELECT o FROM OsobaRod o WHERE o.osrZusDok = :osrZusDok"),
    @NamedQuery(name = "OsobaRod.findByOsrZusNumer", query = "SELECT o FROM OsobaRod o WHERE o.osrZusNumer = :osrZusNumer"),
    @NamedQuery(name = "OsobaRod.findByOsrNazwisko", query = "SELECT o FROM OsobaRod o WHERE o.osrNazwisko = :osrNazwisko"),
    @NamedQuery(name = "OsobaRod.findByOsrImie1", query = "SELECT o FROM OsobaRod o WHERE o.osrImie1 = :osrImie1"),
    @NamedQuery(name = "OsobaRod.findByOsrImie2", query = "SELECT o FROM OsobaRod o WHERE o.osrImie2 = :osrImie2"),
    @NamedQuery(name = "OsobaRod.findByOsrUrodzData", query = "SELECT o FROM OsobaRod o WHERE o.osrUrodzData = :osrUrodzData"),
    @NamedQuery(name = "OsobaRod.findByOsrMiejsceUr", query = "SELECT o FROM OsobaRod o WHERE o.osrMiejsceUr = :osrMiejsceUr"),
    @NamedQuery(name = "OsobaRod.findByOsrKodPokr", query = "SELECT o FROM OsobaRod o WHERE o.osrKodPokr = :osrKodPokr"),
    @NamedQuery(name = "OsobaRod.findByOsrWylUtrz", query = "SELECT o FROM OsobaRod o WHERE o.osrWylUtrz = :osrWylUtrz"),
    @NamedQuery(name = "OsobaRod.findByOsrWspGosp", query = "SELECT o FROM OsobaRod o WHERE o.osrWspGosp = :osrWspGosp"),
    @NamedQuery(name = "OsobaRod.findByOsrKodNiep", query = "SELECT o FROM OsobaRod o WHERE o.osrKodNiep = :osrKodNiep"),
    @NamedQuery(name = "OsobaRod.findByOsrKod", query = "SELECT o FROM OsobaRod o WHERE o.osrKod = :osrKod"),
    @NamedQuery(name = "OsobaRod.findByOsrGmina", query = "SELECT o FROM OsobaRod o WHERE o.osrGmina = :osrGmina"),
    @NamedQuery(name = "OsobaRod.findByOsrUlica", query = "SELECT o FROM OsobaRod o WHERE o.osrUlica = :osrUlica"),
    @NamedQuery(name = "OsobaRod.findByOsrDom", query = "SELECT o FROM OsobaRod o WHERE o.osrDom = :osrDom"),
    @NamedQuery(name = "OsobaRod.findByOsrMieszkanie", query = "SELECT o FROM OsobaRod o WHERE o.osrMieszkanie = :osrMieszkanie"),
    @NamedQuery(name = "OsobaRod.findByOsrTel", query = "SELECT o FROM OsobaRod o WHERE o.osrTel = :osrTel"),
    @NamedQuery(name = "OsobaRod.findByOsrFaks", query = "SELECT o FROM OsobaRod o WHERE o.osrFaks = :osrFaks"),
    @NamedQuery(name = "OsobaRod.findByOsrAdrTsam", query = "SELECT o FROM OsobaRod o WHERE o.osrAdrTsam = :osrAdrTsam"),
    @NamedQuery(name = "OsobaRod.findByOsrDataDod1", query = "SELECT o FROM OsobaRod o WHERE o.osrDataDod1 = :osrDataDod1"),
    @NamedQuery(name = "OsobaRod.findByOsrDataDod2", query = "SELECT o FROM OsobaRod o WHERE o.osrDataDod2 = :osrDataDod2"),
    @NamedQuery(name = "OsobaRod.findByOsrCharDod1", query = "SELECT o FROM OsobaRod o WHERE o.osrCharDod1 = :osrCharDod1"),
    @NamedQuery(name = "OsobaRod.findByOsrCharDod2", query = "SELECT o FROM OsobaRod o WHERE o.osrCharDod2 = :osrCharDod2"),
    @NamedQuery(name = "OsobaRod.findByOsrZgloszony", query = "SELECT o FROM OsobaRod o WHERE o.osrZgloszony = :osrZgloszony"),
    @NamedQuery(name = "OsobaRod.findByOsrDataUpra", query = "SELECT o FROM OsobaRod o WHERE o.osrDataUpra = :osrDataUpra"),
    @NamedQuery(name = "OsobaRod.findByOsrPodlZdro", query = "SELECT o FROM OsobaRod o WHERE o.osrPodlZdro = :osrPodlZdro"),
    @NamedQuery(name = "OsobaRod.findByOsrTyp", query = "SELECT o FROM OsobaRod o WHERE o.osrTyp = :osrTyp"),
    @NamedQuery(name = "OsobaRod.findByOsrDataDod3", query = "SELECT o FROM OsobaRod o WHERE o.osrDataDod3 = :osrDataDod3"),
    @NamedQuery(name = "OsobaRod.findByOsrDataDod4", query = "SELECT o FROM OsobaRod o WHERE o.osrDataDod4 = :osrDataDod4"),
    @NamedQuery(name = "OsobaRod.findByOsrCharDod3", query = "SELECT o FROM OsobaRod o WHERE o.osrCharDod3 = :osrCharDod3"),
    @NamedQuery(name = "OsobaRod.findByOsrCharDod4", query = "SELECT o FROM OsobaRod o WHERE o.osrCharDod4 = :osrCharDod4"),
    @NamedQuery(name = "OsobaRod.findByOsrVcharDod1", query = "SELECT o FROM OsobaRod o WHERE o.osrVcharDod1 = :osrVcharDod1"),
    @NamedQuery(name = "OsobaRod.findByOsrVcharDod2", query = "SELECT o FROM OsobaRod o WHERE o.osrVcharDod2 = :osrVcharDod2"),
    @NamedQuery(name = "OsobaRod.findByOsrNumDod1", query = "SELECT o FROM OsobaRod o WHERE o.osrNumDod1 = :osrNumDod1"),
    @NamedQuery(name = "OsobaRod.findByOsrNumDod2", query = "SELECT o FROM OsobaRod o WHERE o.osrNumDod2 = :osrNumDod2"),
    @NamedQuery(name = "OsobaRod.findByOsrNumDod3", query = "SELECT o FROM OsobaRod o WHERE o.osrNumDod3 = :osrNumDod3"),
    @NamedQuery(name = "OsobaRod.findByOsrNumDod4", query = "SELECT o FROM OsobaRod o WHERE o.osrNumDod4 = :osrNumDod4"),
    @NamedQuery(name = "OsobaRod.findByOsrIntDod1", query = "SELECT o FROM OsobaRod o WHERE o.osrIntDod1 = :osrIntDod1"),
    @NamedQuery(name = "OsobaRod.findByOsrIntDod2", query = "SELECT o FROM OsobaRod o WHERE o.osrIntDod2 = :osrIntDod2")})
public class OsobaRod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "osr_serial", nullable = false)
    private Integer osrSerial;
    @Size(max = 11)
    @Column(name = "osr_pesel", length = 11)
    private String osrPesel;
    @Size(max = 10)
    @Column(name = "osr_nip", length = 10)
    private String osrNip;
    @Column(name = "osr_zus_dok")
    private Character osrZusDok;
    @Size(max = 14)
    @Column(name = "osr_zus_numer", length = 14)
    private String osrZusNumer;
    @Size(max = 31)
    @Column(name = "osr_nazwisko", length = 31)
    private String osrNazwisko;
    @Size(max = 22)
    @Column(name = "osr_imie1", length = 22)
    private String osrImie1;
    @Size(max = 22)
    @Column(name = "osr_imie2", length = 22)
    private String osrImie2;
    @Column(name = "osr_urodz_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osrUrodzData;
    @Size(max = 26)
    @Column(name = "osr_miejsce_ur", length = 26)
    private String osrMiejsceUr;
    @Size(max = 2)
    @Column(name = "osr_kod_pokr", length = 2)
    private String osrKodPokr;
    @Column(name = "osr_wyl_utrz")
    private Character osrWylUtrz;
    @Column(name = "osr_wsp_gosp")
    private Character osrWspGosp;
    @Column(name = "osr_kod_niep")
    private Character osrKodNiep;
    @Size(max = 5)
    @Column(name = "osr_kod", length = 5)
    private String osrKod;
    @Size(max = 26)
    @Column(name = "osr_gmina", length = 26)
    private String osrGmina;
    @Size(max = 30)
    @Column(name = "osr_ulica", length = 30)
    private String osrUlica;
    @Size(max = 7)
    @Column(name = "osr_dom", length = 7)
    private String osrDom;
    @Size(max = 7)
    @Column(name = "osr_mieszkanie", length = 7)
    private String osrMieszkanie;
    @Size(max = 16)
    @Column(name = "osr_tel", length = 16)
    private String osrTel;
    @Size(max = 16)
    @Column(name = "osr_faks", length = 16)
    private String osrFaks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "osr_adr_tsam", nullable = false)
    private Character osrAdrTsam;
    @Column(name = "osr_data_dod_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osrDataDod1;
    @Column(name = "osr_data_dod_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osrDataDod2;
    @Column(name = "osr_char_dod_1")
    private Character osrCharDod1;
    @Column(name = "osr_char_dod_2")
    private Character osrCharDod2;
    @Column(name = "osr_zgloszony")
    private Character osrZgloszony;
    @Column(name = "osr_data_upra")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osrDataUpra;
    @Column(name = "osr_podl_zdro")
    private Character osrPodlZdro;
    @Column(name = "osr_typ")
    private Character osrTyp;
    @Column(name = "osr_data_dod_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osrDataDod3;
    @Column(name = "osr_data_dod_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osrDataDod4;
    @Column(name = "osr_char_dod_3")
    private Character osrCharDod3;
    @Column(name = "osr_char_dod_4")
    private Character osrCharDod4;
    @Size(max = 64)
    @Column(name = "osr_vchar_dod_1", length = 64)
    private String osrVcharDod1;
    @Size(max = 64)
    @Column(name = "osr_vchar_dod_2", length = 64)
    private String osrVcharDod2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "osr_num_dod_1", precision = 17, scale = 6)
    private BigDecimal osrNumDod1;
    @Column(name = "osr_num_dod_2", precision = 17, scale = 6)
    private BigDecimal osrNumDod2;
    @Column(name = "osr_num_dod_3", precision = 17, scale = 6)
    private BigDecimal osrNumDod3;
    @Column(name = "osr_num_dod_4", precision = 17, scale = 6)
    private BigDecimal osrNumDod4;
    @Column(name = "osr_int_dod_1")
    private Integer osrIntDod1;
    @Column(name = "osr_int_dod_2")
    private Integer osrIntDod2;
    @OneToMany(mappedBy = "dliOsrSerial")
    private List<DaneLiDa> daneLiDaList;
    @OneToMany(mappedBy = "dhiOsrSerial")
    private List<DaneHiDa> daneHiDaList;
    @OneToMany(mappedBy = "dstOsrSerial")
    private List<DaneStat> daneStatList;
    @OneToMany(mappedBy = "dasOsrSerial")
    private List<DaneStDa> daneStDaList;
    @OneToMany(mappedBy = "adhOsrSerial")
    private List<AdresHist> adresHistList;
    @JoinColumn(name = "osr_mia_serial", referencedColumnName = "mia_serial")
    @ManyToOne
    private Miasto osrMiaSerial;
    @JoinColumn(name = "osr_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba osrOsoSerial;

    public OsobaRod() {
    }

    public OsobaRod(Integer osrSerial) {
        this.osrSerial = osrSerial;
    }

    public OsobaRod(Integer osrSerial, Character osrAdrTsam) {
        this.osrSerial = osrSerial;
        this.osrAdrTsam = osrAdrTsam;
    }

    public Integer getOsrSerial() {
        return osrSerial;
    }

    public void setOsrSerial(Integer osrSerial) {
        this.osrSerial = osrSerial;
    }

    public String getOsrPesel() {
        return osrPesel;
    }

    public void setOsrPesel(String osrPesel) {
        this.osrPesel = osrPesel;
    }

    public String getOsrNip() {
        return osrNip;
    }

    public void setOsrNip(String osrNip) {
        this.osrNip = osrNip;
    }

    public Character getOsrZusDok() {
        return osrZusDok;
    }

    public void setOsrZusDok(Character osrZusDok) {
        this.osrZusDok = osrZusDok;
    }

    public String getOsrZusNumer() {
        return osrZusNumer;
    }

    public void setOsrZusNumer(String osrZusNumer) {
        this.osrZusNumer = osrZusNumer;
    }

    public String getOsrNazwisko() {
        return osrNazwisko;
    }

    public void setOsrNazwisko(String osrNazwisko) {
        this.osrNazwisko = osrNazwisko;
    }

    public String getOsrImie1() {
        return osrImie1;
    }

    public void setOsrImie1(String osrImie1) {
        this.osrImie1 = osrImie1;
    }

    public String getOsrImie2() {
        return osrImie2;
    }

    public void setOsrImie2(String osrImie2) {
        this.osrImie2 = osrImie2;
    }

    public Date getOsrUrodzData() {
        return osrUrodzData;
    }

    public void setOsrUrodzData(Date osrUrodzData) {
        this.osrUrodzData = osrUrodzData;
    }

    public String getOsrMiejsceUr() {
        return osrMiejsceUr;
    }

    public void setOsrMiejsceUr(String osrMiejsceUr) {
        this.osrMiejsceUr = osrMiejsceUr;
    }

    public String getOsrKodPokr() {
        return osrKodPokr;
    }

    public void setOsrKodPokr(String osrKodPokr) {
        this.osrKodPokr = osrKodPokr;
    }

    public Character getOsrWylUtrz() {
        return osrWylUtrz;
    }

    public void setOsrWylUtrz(Character osrWylUtrz) {
        this.osrWylUtrz = osrWylUtrz;
    }

    public Character getOsrWspGosp() {
        return osrWspGosp;
    }

    public void setOsrWspGosp(Character osrWspGosp) {
        this.osrWspGosp = osrWspGosp;
    }

    public Character getOsrKodNiep() {
        return osrKodNiep;
    }

    public void setOsrKodNiep(Character osrKodNiep) {
        this.osrKodNiep = osrKodNiep;
    }

    public String getOsrKod() {
        return osrKod;
    }

    public void setOsrKod(String osrKod) {
        this.osrKod = osrKod;
    }

    public String getOsrGmina() {
        return osrGmina;
    }

    public void setOsrGmina(String osrGmina) {
        this.osrGmina = osrGmina;
    }

    public String getOsrUlica() {
        return osrUlica;
    }

    public void setOsrUlica(String osrUlica) {
        this.osrUlica = osrUlica;
    }

    public String getOsrDom() {
        return osrDom;
    }

    public void setOsrDom(String osrDom) {
        this.osrDom = osrDom;
    }

    public String getOsrMieszkanie() {
        return osrMieszkanie;
    }

    public void setOsrMieszkanie(String osrMieszkanie) {
        this.osrMieszkanie = osrMieszkanie;
    }

    public String getOsrTel() {
        return osrTel;
    }

    public void setOsrTel(String osrTel) {
        this.osrTel = osrTel;
    }

    public String getOsrFaks() {
        return osrFaks;
    }

    public void setOsrFaks(String osrFaks) {
        this.osrFaks = osrFaks;
    }

    public Character getOsrAdrTsam() {
        return osrAdrTsam;
    }

    public void setOsrAdrTsam(Character osrAdrTsam) {
        this.osrAdrTsam = osrAdrTsam;
    }

    public Date getOsrDataDod1() {
        return osrDataDod1;
    }

    public void setOsrDataDod1(Date osrDataDod1) {
        this.osrDataDod1 = osrDataDod1;
    }

    public Date getOsrDataDod2() {
        return osrDataDod2;
    }

    public void setOsrDataDod2(Date osrDataDod2) {
        this.osrDataDod2 = osrDataDod2;
    }

    public Character getOsrCharDod1() {
        return osrCharDod1;
    }

    public void setOsrCharDod1(Character osrCharDod1) {
        this.osrCharDod1 = osrCharDod1;
    }

    public Character getOsrCharDod2() {
        return osrCharDod2;
    }

    public void setOsrCharDod2(Character osrCharDod2) {
        this.osrCharDod2 = osrCharDod2;
    }

    public Character getOsrZgloszony() {
        return osrZgloszony;
    }

    public void setOsrZgloszony(Character osrZgloszony) {
        this.osrZgloszony = osrZgloszony;
    }

    public Date getOsrDataUpra() {
        return osrDataUpra;
    }

    public void setOsrDataUpra(Date osrDataUpra) {
        this.osrDataUpra = osrDataUpra;
    }

    public Character getOsrPodlZdro() {
        return osrPodlZdro;
    }

    public void setOsrPodlZdro(Character osrPodlZdro) {
        this.osrPodlZdro = osrPodlZdro;
    }

    public Character getOsrTyp() {
        return osrTyp;
    }

    public void setOsrTyp(Character osrTyp) {
        this.osrTyp = osrTyp;
    }

    public Date getOsrDataDod3() {
        return osrDataDod3;
    }

    public void setOsrDataDod3(Date osrDataDod3) {
        this.osrDataDod3 = osrDataDod3;
    }

    public Date getOsrDataDod4() {
        return osrDataDod4;
    }

    public void setOsrDataDod4(Date osrDataDod4) {
        this.osrDataDod4 = osrDataDod4;
    }

    public Character getOsrCharDod3() {
        return osrCharDod3;
    }

    public void setOsrCharDod3(Character osrCharDod3) {
        this.osrCharDod3 = osrCharDod3;
    }

    public Character getOsrCharDod4() {
        return osrCharDod4;
    }

    public void setOsrCharDod4(Character osrCharDod4) {
        this.osrCharDod4 = osrCharDod4;
    }

    public String getOsrVcharDod1() {
        return osrVcharDod1;
    }

    public void setOsrVcharDod1(String osrVcharDod1) {
        this.osrVcharDod1 = osrVcharDod1;
    }

    public String getOsrVcharDod2() {
        return osrVcharDod2;
    }

    public void setOsrVcharDod2(String osrVcharDod2) {
        this.osrVcharDod2 = osrVcharDod2;
    }

    public BigDecimal getOsrNumDod1() {
        return osrNumDod1;
    }

    public void setOsrNumDod1(BigDecimal osrNumDod1) {
        this.osrNumDod1 = osrNumDod1;
    }

    public BigDecimal getOsrNumDod2() {
        return osrNumDod2;
    }

    public void setOsrNumDod2(BigDecimal osrNumDod2) {
        this.osrNumDod2 = osrNumDod2;
    }

    public BigDecimal getOsrNumDod3() {
        return osrNumDod3;
    }

    public void setOsrNumDod3(BigDecimal osrNumDod3) {
        this.osrNumDod3 = osrNumDod3;
    }

    public BigDecimal getOsrNumDod4() {
        return osrNumDod4;
    }

    public void setOsrNumDod4(BigDecimal osrNumDod4) {
        this.osrNumDod4 = osrNumDod4;
    }

    public Integer getOsrIntDod1() {
        return osrIntDod1;
    }

    public void setOsrIntDod1(Integer osrIntDod1) {
        this.osrIntDod1 = osrIntDod1;
    }

    public Integer getOsrIntDod2() {
        return osrIntDod2;
    }

    public void setOsrIntDod2(Integer osrIntDod2) {
        this.osrIntDod2 = osrIntDod2;
    }

    @XmlTransient
    public List<DaneLiDa> getDaneLiDaList() {
        return daneLiDaList;
    }

    public void setDaneLiDaList(List<DaneLiDa> daneLiDaList) {
        this.daneLiDaList = daneLiDaList;
    }

    @XmlTransient
    public List<DaneHiDa> getDaneHiDaList() {
        return daneHiDaList;
    }

    public void setDaneHiDaList(List<DaneHiDa> daneHiDaList) {
        this.daneHiDaList = daneHiDaList;
    }

    @XmlTransient
    public List<DaneStat> getDaneStatList() {
        return daneStatList;
    }

    public void setDaneStatList(List<DaneStat> daneStatList) {
        this.daneStatList = daneStatList;
    }

    @XmlTransient
    public List<DaneStDa> getDaneStDaList() {
        return daneStDaList;
    }

    public void setDaneStDaList(List<DaneStDa> daneStDaList) {
        this.daneStDaList = daneStDaList;
    }

    @XmlTransient
    public List<AdresHist> getAdresHistList() {
        return adresHistList;
    }

    public void setAdresHistList(List<AdresHist> adresHistList) {
        this.adresHistList = adresHistList;
    }

    public Miasto getOsrMiaSerial() {
        return osrMiaSerial;
    }

    public void setOsrMiaSerial(Miasto osrMiaSerial) {
        this.osrMiaSerial = osrMiaSerial;
    }

    public Osoba getOsrOsoSerial() {
        return osrOsoSerial;
    }

    public void setOsrOsoSerial(Osoba osrOsoSerial) {
        this.osrOsoSerial = osrOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (osrSerial != null ? osrSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OsobaRod)) {
            return false;
        }
        OsobaRod other = (OsobaRod) object;
        if ((this.osrSerial == null && other.osrSerial != null) || (this.osrSerial != null && !this.osrSerial.equals(other.osrSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.OsobaRod[ osrSerial=" + osrSerial + " ]";
    }
    
}
