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
@Table(name = "osoba_zlec", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OsobaZlec.findAll", query = "SELECT o FROM OsobaZlec o"),
    @NamedQuery(name = "OsobaZlec.findByOzlSerial", query = "SELECT o FROM OsobaZlec o WHERE o.ozlSerial = :ozlSerial"),
    @NamedQuery(name = "OsobaZlec.findByOzlDataOd", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDataOd = :ozlDataOd"),
    @NamedQuery(name = "OsobaZlec.findByOzlDataDo", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDataDo = :ozlDataDo"),
    @NamedQuery(name = "OsobaZlec.findByOzlKwota", query = "SELECT o FROM OsobaZlec o WHERE o.ozlKwota = :ozlKwota"),
    @NamedQuery(name = "OsobaZlec.findByOzlKoszt", query = "SELECT o FROM OsobaZlec o WHERE o.ozlKoszt = :ozlKoszt"),
    @NamedQuery(name = "OsobaZlec.findByOzlStatus", query = "SELECT o FROM OsobaZlec o WHERE o.ozlStatus = :ozlStatus"),
    @NamedQuery(name = "OsobaZlec.findByOzlKosztR", query = "SELECT o FROM OsobaZlec o WHERE o.ozlKosztR = :ozlKosztR"),
    @NamedQuery(name = "OsobaZlec.findByOzlKosztProc", query = "SELECT o FROM OsobaZlec o WHERE o.ozlKosztProc = :ozlKosztProc"),
    @NamedQuery(name = "OsobaZlec.findByOzlDod1", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDod1 = :ozlDod1"),
    @NamedQuery(name = "OsobaZlec.findByOzlDod2", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDod2 = :ozlDod2"),
    @NamedQuery(name = "OsobaZlec.findByOzlDod3", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDod3 = :ozlDod3"),
    @NamedQuery(name = "OsobaZlec.findByOzlKodTytU12", query = "SELECT o FROM OsobaZlec o WHERE o.ozlKodTytU12 = :ozlKodTytU12"),
    @NamedQuery(name = "OsobaZlec.findByOzlKodTytU3", query = "SELECT o FROM OsobaZlec o WHERE o.ozlKodTytU3 = :ozlKodTytU3"),
    @NamedQuery(name = "OsobaZlec.findByOzlKodTytU4", query = "SELECT o FROM OsobaZlec o WHERE o.ozlKodTytU4 = :ozlKodTytU4"),
    @NamedQuery(name = "OsobaZlec.findByOzlNrUmowy", query = "SELECT o FROM OsobaZlec o WHERE o.ozlNrUmowy = :ozlNrUmowy"),
    @NamedQuery(name = "OsobaZlec.findByOzlDataWyplaty", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDataWyplaty = :ozlDataWyplaty"),
    @NamedQuery(name = "OsobaZlec.findByOzlPraca1", query = "SELECT o FROM OsobaZlec o WHERE o.ozlPraca1 = :ozlPraca1"),
    @NamedQuery(name = "OsobaZlec.findByOzlPraca2", query = "SELECT o FROM OsobaZlec o WHERE o.ozlPraca2 = :ozlPraca2"),
    @NamedQuery(name = "OsobaZlec.findByOzlPraca3", query = "SELECT o FROM OsobaZlec o WHERE o.ozlPraca3 = :ozlPraca3"),
    @NamedQuery(name = "OsobaZlec.findByOzlPraca4", query = "SELECT o FROM OsobaZlec o WHERE o.ozlPraca4 = :ozlPraca4"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodVchar1", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodVchar1 = :ozlDodVchar1"),
    @NamedQuery(name = "OsobaZlec.findByOzlRodzaj", query = "SELECT o FROM OsobaZlec o WHERE o.ozlRodzaj = :ozlRodzaj"),
    @NamedQuery(name = "OsobaZlec.findByOzlRozliczenie", query = "SELECT o FROM OsobaZlec o WHERE o.ozlRozliczenie = :ozlRozliczenie"),
    @NamedQuery(name = "OsobaZlec.findByOzlDataZawarcia", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDataZawarcia = :ozlDataZawarcia"),
    @NamedQuery(name = "OsobaZlec.findByOzlLp", query = "SELECT o FROM OsobaZlec o WHERE o.ozlLp = :ozlLp"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodVchar2", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodVchar2 = :ozlDodVchar2"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodVchar3", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodVchar3 = :ozlDodVchar3"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodVchar4", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodVchar4 = :ozlDodVchar4"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodData1", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodData1 = :ozlDodData1"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodData2", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodData2 = :ozlDodData2"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodNum1", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodNum1 = :ozlDodNum1"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodNum2", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodNum2 = :ozlDodNum2"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodChar1", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodChar1 = :ozlDodChar1"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodChar2", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodChar2 = :ozlDodChar2"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodChar3", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodChar3 = :ozlDodChar3"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodChar4", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodChar4 = :ozlDodChar4"),
    @NamedQuery(name = "OsobaZlec.findByOzlSkreslony", query = "SELECT o FROM OsobaZlec o WHERE o.ozlSkreslony = :ozlSkreslony"),
    @NamedQuery(name = "OsobaZlec.findByOzlRokSerial", query = "SELECT o FROM OsobaZlec o WHERE o.ozlRokSerial = :ozlRokSerial"),
    @NamedQuery(name = "OsobaZlec.findByOzlOkrSerial", query = "SELECT o FROM OsobaZlec o WHERE o.ozlOkrSerial = :ozlOkrSerial"),
    @NamedQuery(name = "OsobaZlec.findByOzlWspolcz1", query = "SELECT o FROM OsobaZlec o WHERE o.ozlWspolcz1 = :ozlWspolcz1"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodChar5", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodChar5 = :ozlDodChar5"),
    @NamedQuery(name = "OsobaZlec.findByOzlWspolczK", query = "SELECT o FROM OsobaZlec o WHERE o.ozlWspolczK = :ozlWspolczK"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodData3", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodData3 = :ozlDodData3"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodData4", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodData4 = :ozlDodData4"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodNum3", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodNum3 = :ozlDodNum3"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodNum4", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodNum4 = :ozlDodNum4"),
    @NamedQuery(name = "OsobaZlec.findByOzlRyczalt", query = "SELECT o FROM OsobaZlec o WHERE o.ozlRyczalt = :ozlRyczalt"),
    @NamedQuery(name = "OsobaZlec.findByOzlTyp", query = "SELECT o FROM OsobaZlec o WHERE o.ozlTyp = :ozlTyp"),
    @NamedQuery(name = "OsobaZlec.findByOzlMin", query = "SELECT o FROM OsobaZlec o WHERE o.ozlMin = :ozlMin"),
    @NamedQuery(name = "OsobaZlec.findByOzlTypMin", query = "SELECT o FROM OsobaZlec o WHERE o.ozlTypMin = :ozlTypMin"),
    @NamedQuery(name = "OsobaZlec.findByOzlMax", query = "SELECT o FROM OsobaZlec o WHERE o.ozlMax = :ozlMax"),
    @NamedQuery(name = "OsobaZlec.findByOzlTypMax", query = "SELECT o FROM OsobaZlec o WHERE o.ozlTypMax = :ozlTypMax"),
    @NamedQuery(name = "OsobaZlec.findByOzlKwotaMin", query = "SELECT o FROM OsobaZlec o WHERE o.ozlKwotaMin = :ozlKwotaMin"),
    @NamedQuery(name = "OsobaZlec.findByOzlKwotaMax", query = "SELECT o FROM OsobaZlec o WHERE o.ozlKwotaMax = :ozlKwotaMax"),
    @NamedQuery(name = "OsobaZlec.findByOzlWspolczMin", query = "SELECT o FROM OsobaZlec o WHERE o.ozlWspolczMin = :ozlWspolczMin"),
    @NamedQuery(name = "OsobaZlec.findByOzlWspolczMax", query = "SELECT o FROM OsobaZlec o WHERE o.ozlWspolczMax = :ozlWspolczMax"),
    @NamedQuery(name = "OsobaZlec.findByOzlSsdSerialMax", query = "SELECT o FROM OsobaZlec o WHERE o.ozlSsdSerialMax = :ozlSsdSerialMax"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodInt1", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodInt1 = :ozlDodInt1"),
    @NamedQuery(name = "OsobaZlec.findByOzlDodInt2", query = "SELECT o FROM OsobaZlec o WHERE o.ozlDodInt2 = :ozlDodInt2")})
public class OsobaZlec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ozl_serial", nullable = false)
    private Integer ozlSerial;
    @Column(name = "ozl_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ozlDataOd;
    @Column(name = "ozl_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ozlDataDo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ozl_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal ozlKwota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ozl_koszt", nullable = false, precision = 13, scale = 2)
    private BigDecimal ozlKoszt;
    @Column(name = "ozl_status")
    private Character ozlStatus;
    @Column(name = "ozl_koszt_r")
    private Character ozlKosztR;
    @Column(name = "ozl_koszt_proc", precision = 5, scale = 2)
    private BigDecimal ozlKosztProc;
    @Column(name = "ozl_dod_1")
    private Character ozlDod1;
    @Column(name = "ozl_dod_2")
    private Character ozlDod2;
    @Column(name = "ozl_dod_3")
    private Character ozlDod3;
    @Size(max = 8)
    @Column(name = "ozl_kod_tyt_u_1_2", length = 8)
    private String ozlKodTytU12;
    @Column(name = "ozl_kod_tyt_u_3")
    private Character ozlKodTytU3;
    @Column(name = "ozl_kod_tyt_u_4")
    private Character ozlKodTytU4;
    @Size(max = 32)
    @Column(name = "ozl_nr_umowy", length = 32)
    private String ozlNrUmowy;
    @Column(name = "ozl_data_wyplaty")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ozlDataWyplaty;
    @Size(max = 64)
    @Column(name = "ozl_praca_1", length = 64)
    private String ozlPraca1;
    @Size(max = 64)
    @Column(name = "ozl_praca_2", length = 64)
    private String ozlPraca2;
    @Size(max = 64)
    @Column(name = "ozl_praca_3", length = 64)
    private String ozlPraca3;
    @Size(max = 64)
    @Column(name = "ozl_praca_4", length = 64)
    private String ozlPraca4;
    @Size(max = 64)
    @Column(name = "ozl_dod_vchar_1", length = 64)
    private String ozlDodVchar1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ozl_rodzaj", nullable = false)
    private Character ozlRodzaj;
    @Column(name = "ozl_rozliczenie")
    private Character ozlRozliczenie;
    @Column(name = "ozl_data_zawarcia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ozlDataZawarcia;
    @Column(name = "ozl_lp")
    private Integer ozlLp;
    @Size(max = 64)
    @Column(name = "ozl_dod_vchar_2", length = 64)
    private String ozlDodVchar2;
    @Size(max = 64)
    @Column(name = "ozl_dod_vchar_3", length = 64)
    private String ozlDodVchar3;
    @Size(max = 64)
    @Column(name = "ozl_dod_vchar_4", length = 64)
    private String ozlDodVchar4;
    @Column(name = "ozl_dod_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ozlDodData1;
    @Column(name = "ozl_dod_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ozlDodData2;
    @Column(name = "ozl_dod_num_1", precision = 17, scale = 6)
    private BigDecimal ozlDodNum1;
    @Column(name = "ozl_dod_num_2", precision = 17, scale = 6)
    private BigDecimal ozlDodNum2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ozl_dod_char_1", nullable = false)
    private Character ozlDodChar1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ozl_dod_char_2", nullable = false)
    private Character ozlDodChar2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ozl_dod_char_3", nullable = false)
    private Character ozlDodChar3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ozl_dod_char_4", nullable = false)
    private Character ozlDodChar4;
    @Column(name = "ozl_skreslony")
    private Character ozlSkreslony;
    @Column(name = "ozl_rok_serial")
    private Integer ozlRokSerial;
    @Column(name = "ozl_okr_serial")
    private Integer ozlOkrSerial;
    @Column(name = "ozl_wspolcz_1", precision = 17, scale = 6)
    private BigDecimal ozlWspolcz1;
    @Column(name = "ozl_dod_char_5")
    private Character ozlDodChar5;
    @Column(name = "ozl_wspolcz_k", precision = 17, scale = 6)
    private BigDecimal ozlWspolczK;
    @Column(name = "ozl_dod_data_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ozlDodData3;
    @Column(name = "ozl_dod_data_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ozlDodData4;
    @Column(name = "ozl_dod_num_3", precision = 17, scale = 6)
    private BigDecimal ozlDodNum3;
    @Column(name = "ozl_dod_num_4", precision = 17, scale = 6)
    private BigDecimal ozlDodNum4;
    @Column(name = "ozl_ryczalt")
    private Character ozlRyczalt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ozl_typ", nullable = false)
    private Character ozlTyp;
    @Column(name = "ozl_min")
    private Character ozlMin;
    @Column(name = "ozl_typ_min")
    private Character ozlTypMin;
    @Column(name = "ozl_max")
    private Character ozlMax;
    @Column(name = "ozl_typ_max")
    private Character ozlTypMax;
    @Column(name = "ozl_kwota_min", precision = 13, scale = 2)
    private BigDecimal ozlKwotaMin;
    @Column(name = "ozl_kwota_max", precision = 13, scale = 2)
    private BigDecimal ozlKwotaMax;
    @Column(name = "ozl_wspolcz_min", precision = 17, scale = 6)
    private BigDecimal ozlWspolczMin;
    @Column(name = "ozl_wspolcz_max", precision = 17, scale = 6)
    private BigDecimal ozlWspolczMax;
    @Column(name = "ozl_ssd_serial_max")
    private Integer ozlSsdSerialMax;
    @Column(name = "ozl_dod_int_1")
    private Integer ozlDodInt1;
    @Column(name = "ozl_dod_int_2")
    private Integer ozlDodInt2;
    @OneToMany(mappedBy = "dssOzlSerial")
    private List<DaneStatS> daneStatSList;
    @OneToMany(mappedBy = "pzlOzlSerial")
    private List<PlaceZlec> placeZlecList;
    @JoinColumn(name = "ozl_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma ozlFirSerial;
    @JoinColumn(name = "ozl_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba ozlOsoSerial;
    @JoinColumn(name = "ozl_opt_serial", referencedColumnName = "opt_serial")
    @ManyToOne
    private OsobaPropTyp ozlOptSerial;
    @JoinColumn(name = "ozl_ssd_serial_1", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ozlSsdSerial1;
    @JoinColumn(name = "ozl_ssd_serial_k", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ozlSsdSerialK;
    @JoinColumn(name = "ozl_ssd_serial_2", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ozlSsdSerial2;
    @JoinColumn(name = "ozl_ssd_serial_k2", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ozlSsdSerialK2;
    @JoinColumn(name = "ozl_ssd_serial_min", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ozlSsdSerialMin;
    @JoinColumn(name = "ozl_wks_serial", referencedColumnName = "wks_serial")
    @ManyToOne
    private WynKodSkl ozlWksSerial;
    @JoinColumn(name = "ozl_wkt_serial", referencedColumnName = "wkt_serial")
    @ManyToOne
    private WynKodTyt ozlWktSerial;

    public OsobaZlec() {
    }

    public OsobaZlec(Integer ozlSerial) {
        this.ozlSerial = ozlSerial;
    }

    public OsobaZlec(Integer ozlSerial, BigDecimal ozlKwota, BigDecimal ozlKoszt, Character ozlRodzaj, Character ozlDodChar1, Character ozlDodChar2, Character ozlDodChar3, Character ozlDodChar4, Character ozlTyp) {
        this.ozlSerial = ozlSerial;
        this.ozlKwota = ozlKwota;
        this.ozlKoszt = ozlKoszt;
        this.ozlRodzaj = ozlRodzaj;
        this.ozlDodChar1 = ozlDodChar1;
        this.ozlDodChar2 = ozlDodChar2;
        this.ozlDodChar3 = ozlDodChar3;
        this.ozlDodChar4 = ozlDodChar4;
        this.ozlTyp = ozlTyp;
    }

    public Integer getOzlSerial() {
        return ozlSerial;
    }

    public void setOzlSerial(Integer ozlSerial) {
        this.ozlSerial = ozlSerial;
    }

    public Date getOzlDataOd() {
        return ozlDataOd;
    }

    public void setOzlDataOd(Date ozlDataOd) {
        this.ozlDataOd = ozlDataOd;
    }

    public Date getOzlDataDo() {
        return ozlDataDo;
    }

    public void setOzlDataDo(Date ozlDataDo) {
        this.ozlDataDo = ozlDataDo;
    }

    public BigDecimal getOzlKwota() {
        return ozlKwota;
    }

    public void setOzlKwota(BigDecimal ozlKwota) {
        this.ozlKwota = ozlKwota;
    }

    public BigDecimal getOzlKoszt() {
        return ozlKoszt;
    }

    public void setOzlKoszt(BigDecimal ozlKoszt) {
        this.ozlKoszt = ozlKoszt;
    }

    public Character getOzlStatus() {
        return ozlStatus;
    }

    public void setOzlStatus(Character ozlStatus) {
        this.ozlStatus = ozlStatus;
    }

    public Character getOzlKosztR() {
        return ozlKosztR;
    }

    public void setOzlKosztR(Character ozlKosztR) {
        this.ozlKosztR = ozlKosztR;
    }

    public BigDecimal getOzlKosztProc() {
        return ozlKosztProc;
    }

    public void setOzlKosztProc(BigDecimal ozlKosztProc) {
        this.ozlKosztProc = ozlKosztProc;
    }

    public Character getOzlDod1() {
        return ozlDod1;
    }

    public void setOzlDod1(Character ozlDod1) {
        this.ozlDod1 = ozlDod1;
    }

    public Character getOzlDod2() {
        return ozlDod2;
    }

    public void setOzlDod2(Character ozlDod2) {
        this.ozlDod2 = ozlDod2;
    }

    public Character getOzlDod3() {
        return ozlDod3;
    }

    public void setOzlDod3(Character ozlDod3) {
        this.ozlDod3 = ozlDod3;
    }

    public String getOzlKodTytU12() {
        return ozlKodTytU12;
    }

    public void setOzlKodTytU12(String ozlKodTytU12) {
        this.ozlKodTytU12 = ozlKodTytU12;
    }

    public Character getOzlKodTytU3() {
        return ozlKodTytU3;
    }

    public void setOzlKodTytU3(Character ozlKodTytU3) {
        this.ozlKodTytU3 = ozlKodTytU3;
    }

    public Character getOzlKodTytU4() {
        return ozlKodTytU4;
    }

    public void setOzlKodTytU4(Character ozlKodTytU4) {
        this.ozlKodTytU4 = ozlKodTytU4;
    }

    public String getOzlNrUmowy() {
        return ozlNrUmowy;
    }

    public void setOzlNrUmowy(String ozlNrUmowy) {
        this.ozlNrUmowy = ozlNrUmowy;
    }

    public Date getOzlDataWyplaty() {
        return ozlDataWyplaty;
    }

    public void setOzlDataWyplaty(Date ozlDataWyplaty) {
        this.ozlDataWyplaty = ozlDataWyplaty;
    }

    public String getOzlPraca1() {
        return ozlPraca1;
    }

    public void setOzlPraca1(String ozlPraca1) {
        this.ozlPraca1 = ozlPraca1;
    }

    public String getOzlPraca2() {
        return ozlPraca2;
    }

    public void setOzlPraca2(String ozlPraca2) {
        this.ozlPraca2 = ozlPraca2;
    }

    public String getOzlPraca3() {
        return ozlPraca3;
    }

    public void setOzlPraca3(String ozlPraca3) {
        this.ozlPraca3 = ozlPraca3;
    }

    public String getOzlPraca4() {
        return ozlPraca4;
    }

    public void setOzlPraca4(String ozlPraca4) {
        this.ozlPraca4 = ozlPraca4;
    }

    public String getOzlDodVchar1() {
        return ozlDodVchar1;
    }

    public void setOzlDodVchar1(String ozlDodVchar1) {
        this.ozlDodVchar1 = ozlDodVchar1;
    }

    public Character getOzlRodzaj() {
        return ozlRodzaj;
    }

    public void setOzlRodzaj(Character ozlRodzaj) {
        this.ozlRodzaj = ozlRodzaj;
    }

    public Character getOzlRozliczenie() {
        return ozlRozliczenie;
    }

    public void setOzlRozliczenie(Character ozlRozliczenie) {
        this.ozlRozliczenie = ozlRozliczenie;
    }

    public Date getOzlDataZawarcia() {
        return ozlDataZawarcia;
    }

    public void setOzlDataZawarcia(Date ozlDataZawarcia) {
        this.ozlDataZawarcia = ozlDataZawarcia;
    }

    public Integer getOzlLp() {
        return ozlLp;
    }

    public void setOzlLp(Integer ozlLp) {
        this.ozlLp = ozlLp;
    }

    public String getOzlDodVchar2() {
        return ozlDodVchar2;
    }

    public void setOzlDodVchar2(String ozlDodVchar2) {
        this.ozlDodVchar2 = ozlDodVchar2;
    }

    public String getOzlDodVchar3() {
        return ozlDodVchar3;
    }

    public void setOzlDodVchar3(String ozlDodVchar3) {
        this.ozlDodVchar3 = ozlDodVchar3;
    }

    public String getOzlDodVchar4() {
        return ozlDodVchar4;
    }

    public void setOzlDodVchar4(String ozlDodVchar4) {
        this.ozlDodVchar4 = ozlDodVchar4;
    }

    public Date getOzlDodData1() {
        return ozlDodData1;
    }

    public void setOzlDodData1(Date ozlDodData1) {
        this.ozlDodData1 = ozlDodData1;
    }

    public Date getOzlDodData2() {
        return ozlDodData2;
    }

    public void setOzlDodData2(Date ozlDodData2) {
        this.ozlDodData2 = ozlDodData2;
    }

    public BigDecimal getOzlDodNum1() {
        return ozlDodNum1;
    }

    public void setOzlDodNum1(BigDecimal ozlDodNum1) {
        this.ozlDodNum1 = ozlDodNum1;
    }

    public BigDecimal getOzlDodNum2() {
        return ozlDodNum2;
    }

    public void setOzlDodNum2(BigDecimal ozlDodNum2) {
        this.ozlDodNum2 = ozlDodNum2;
    }

    public Character getOzlDodChar1() {
        return ozlDodChar1;
    }

    public void setOzlDodChar1(Character ozlDodChar1) {
        this.ozlDodChar1 = ozlDodChar1;
    }

    public Character getOzlDodChar2() {
        return ozlDodChar2;
    }

    public void setOzlDodChar2(Character ozlDodChar2) {
        this.ozlDodChar2 = ozlDodChar2;
    }

    public Character getOzlDodChar3() {
        return ozlDodChar3;
    }

    public void setOzlDodChar3(Character ozlDodChar3) {
        this.ozlDodChar3 = ozlDodChar3;
    }

    public Character getOzlDodChar4() {
        return ozlDodChar4;
    }

    public void setOzlDodChar4(Character ozlDodChar4) {
        this.ozlDodChar4 = ozlDodChar4;
    }

    public Character getOzlSkreslony() {
        return ozlSkreslony;
    }

    public void setOzlSkreslony(Character ozlSkreslony) {
        this.ozlSkreslony = ozlSkreslony;
    }

    public Integer getOzlRokSerial() {
        return ozlRokSerial;
    }

    public void setOzlRokSerial(Integer ozlRokSerial) {
        this.ozlRokSerial = ozlRokSerial;
    }

    public Integer getOzlOkrSerial() {
        return ozlOkrSerial;
    }

    public void setOzlOkrSerial(Integer ozlOkrSerial) {
        this.ozlOkrSerial = ozlOkrSerial;
    }

    public BigDecimal getOzlWspolcz1() {
        return ozlWspolcz1;
    }

    public void setOzlWspolcz1(BigDecimal ozlWspolcz1) {
        this.ozlWspolcz1 = ozlWspolcz1;
    }

    public Character getOzlDodChar5() {
        return ozlDodChar5;
    }

    public void setOzlDodChar5(Character ozlDodChar5) {
        this.ozlDodChar5 = ozlDodChar5;
    }

    public BigDecimal getOzlWspolczK() {
        return ozlWspolczK;
    }

    public void setOzlWspolczK(BigDecimal ozlWspolczK) {
        this.ozlWspolczK = ozlWspolczK;
    }

    public Date getOzlDodData3() {
        return ozlDodData3;
    }

    public void setOzlDodData3(Date ozlDodData3) {
        this.ozlDodData3 = ozlDodData3;
    }

    public Date getOzlDodData4() {
        return ozlDodData4;
    }

    public void setOzlDodData4(Date ozlDodData4) {
        this.ozlDodData4 = ozlDodData4;
    }

    public BigDecimal getOzlDodNum3() {
        return ozlDodNum3;
    }

    public void setOzlDodNum3(BigDecimal ozlDodNum3) {
        this.ozlDodNum3 = ozlDodNum3;
    }

    public BigDecimal getOzlDodNum4() {
        return ozlDodNum4;
    }

    public void setOzlDodNum4(BigDecimal ozlDodNum4) {
        this.ozlDodNum4 = ozlDodNum4;
    }

    public Character getOzlRyczalt() {
        return ozlRyczalt;
    }

    public void setOzlRyczalt(Character ozlRyczalt) {
        this.ozlRyczalt = ozlRyczalt;
    }

    public Character getOzlTyp() {
        return ozlTyp;
    }

    public void setOzlTyp(Character ozlTyp) {
        this.ozlTyp = ozlTyp;
    }

    public Character getOzlMin() {
        return ozlMin;
    }

    public void setOzlMin(Character ozlMin) {
        this.ozlMin = ozlMin;
    }

    public Character getOzlTypMin() {
        return ozlTypMin;
    }

    public void setOzlTypMin(Character ozlTypMin) {
        this.ozlTypMin = ozlTypMin;
    }

    public Character getOzlMax() {
        return ozlMax;
    }

    public void setOzlMax(Character ozlMax) {
        this.ozlMax = ozlMax;
    }

    public Character getOzlTypMax() {
        return ozlTypMax;
    }

    public void setOzlTypMax(Character ozlTypMax) {
        this.ozlTypMax = ozlTypMax;
    }

    public BigDecimal getOzlKwotaMin() {
        return ozlKwotaMin;
    }

    public void setOzlKwotaMin(BigDecimal ozlKwotaMin) {
        this.ozlKwotaMin = ozlKwotaMin;
    }

    public BigDecimal getOzlKwotaMax() {
        return ozlKwotaMax;
    }

    public void setOzlKwotaMax(BigDecimal ozlKwotaMax) {
        this.ozlKwotaMax = ozlKwotaMax;
    }

    public BigDecimal getOzlWspolczMin() {
        return ozlWspolczMin;
    }

    public void setOzlWspolczMin(BigDecimal ozlWspolczMin) {
        this.ozlWspolczMin = ozlWspolczMin;
    }

    public BigDecimal getOzlWspolczMax() {
        return ozlWspolczMax;
    }

    public void setOzlWspolczMax(BigDecimal ozlWspolczMax) {
        this.ozlWspolczMax = ozlWspolczMax;
    }

    public Integer getOzlSsdSerialMax() {
        return ozlSsdSerialMax;
    }

    public void setOzlSsdSerialMax(Integer ozlSsdSerialMax) {
        this.ozlSsdSerialMax = ozlSsdSerialMax;
    }

    public Integer getOzlDodInt1() {
        return ozlDodInt1;
    }

    public void setOzlDodInt1(Integer ozlDodInt1) {
        this.ozlDodInt1 = ozlDodInt1;
    }

    public Integer getOzlDodInt2() {
        return ozlDodInt2;
    }

    public void setOzlDodInt2(Integer ozlDodInt2) {
        this.ozlDodInt2 = ozlDodInt2;
    }

    @XmlTransient
    public List<DaneStatS> getDaneStatSList() {
        return daneStatSList;
    }

    public void setDaneStatSList(List<DaneStatS> daneStatSList) {
        this.daneStatSList = daneStatSList;
    }

    @XmlTransient
    public List<PlaceZlec> getPlaceZlecList() {
        return placeZlecList;
    }

    public void setPlaceZlecList(List<PlaceZlec> placeZlecList) {
        this.placeZlecList = placeZlecList;
    }

    public Firma getOzlFirSerial() {
        return ozlFirSerial;
    }

    public void setOzlFirSerial(Firma ozlFirSerial) {
        this.ozlFirSerial = ozlFirSerial;
    }

    public Osoba getOzlOsoSerial() {
        return ozlOsoSerial;
    }

    public void setOzlOsoSerial(Osoba ozlOsoSerial) {
        this.ozlOsoSerial = ozlOsoSerial;
    }

    public OsobaPropTyp getOzlOptSerial() {
        return ozlOptSerial;
    }

    public void setOzlOptSerial(OsobaPropTyp ozlOptSerial) {
        this.ozlOptSerial = ozlOptSerial;
    }

    public StSystOpis getOzlSsdSerial1() {
        return ozlSsdSerial1;
    }

    public void setOzlSsdSerial1(StSystOpis ozlSsdSerial1) {
        this.ozlSsdSerial1 = ozlSsdSerial1;
    }

    public StSystOpis getOzlSsdSerialK() {
        return ozlSsdSerialK;
    }

    public void setOzlSsdSerialK(StSystOpis ozlSsdSerialK) {
        this.ozlSsdSerialK = ozlSsdSerialK;
    }

    public StSystOpis getOzlSsdSerial2() {
        return ozlSsdSerial2;
    }

    public void setOzlSsdSerial2(StSystOpis ozlSsdSerial2) {
        this.ozlSsdSerial2 = ozlSsdSerial2;
    }

    public StSystOpis getOzlSsdSerialK2() {
        return ozlSsdSerialK2;
    }

    public void setOzlSsdSerialK2(StSystOpis ozlSsdSerialK2) {
        this.ozlSsdSerialK2 = ozlSsdSerialK2;
    }

    public StSystOpis getOzlSsdSerialMin() {
        return ozlSsdSerialMin;
    }

    public void setOzlSsdSerialMin(StSystOpis ozlSsdSerialMin) {
        this.ozlSsdSerialMin = ozlSsdSerialMin;
    }

    public WynKodSkl getOzlWksSerial() {
        return ozlWksSerial;
    }

    public void setOzlWksSerial(WynKodSkl ozlWksSerial) {
        this.ozlWksSerial = ozlWksSerial;
    }

    public WynKodTyt getOzlWktSerial() {
        return ozlWktSerial;
    }

    public void setOzlWktSerial(WynKodTyt ozlWktSerial) {
        this.ozlWktSerial = ozlWktSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ozlSerial != null ? ozlSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OsobaZlec)) {
            return false;
        }
        OsobaZlec other = (OsobaZlec) object;
        if ((this.ozlSerial == null && other.ozlSerial != null) || (this.ozlSerial != null && !this.ozlSerial.equals(other.ozlSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.OsobaZlec[ ozlSerial=" + ozlSerial + " ]";
    }
    
}
