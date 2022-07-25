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
@Table(name = "osoba_skl", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OsobaSkl.findAll", query = "SELECT o FROM OsobaSkl o"),
    @NamedQuery(name = "OsobaSkl.findByOssSerial", query = "SELECT o FROM OsobaSkl o WHERE o.ossSerial = :ossSerial"),
    @NamedQuery(name = "OsobaSkl.findByOssDataOd", query = "SELECT o FROM OsobaSkl o WHERE o.ossDataOd = :ossDataOd"),
    @NamedQuery(name = "OsobaSkl.findByOssDataDo", query = "SELECT o FROM OsobaSkl o WHERE o.ossDataDo = :ossDataDo"),
    @NamedQuery(name = "OsobaSkl.findByOssKwota", query = "SELECT o FROM OsobaSkl o WHERE o.ossKwota = :ossKwota"),
    @NamedQuery(name = "OsobaSkl.findByOssStatus", query = "SELECT o FROM OsobaSkl o WHERE o.ossStatus = :ossStatus"),
    @NamedQuery(name = "OsobaSkl.findByOssTyp", query = "SELECT o FROM OsobaSkl o WHERE o.ossTyp = :ossTyp"),
    @NamedQuery(name = "OsobaSkl.findByOssWspolcz", query = "SELECT o FROM OsobaSkl o WHERE o.ossWspolcz = :ossWspolcz"),
    @NamedQuery(name = "OsobaSkl.findByOssWspolcz2", query = "SELECT o FROM OsobaSkl o WHERE o.ossWspolcz2 = :ossWspolcz2"),
    @NamedQuery(name = "OsobaSkl.findByOssWspolczMin", query = "SELECT o FROM OsobaSkl o WHERE o.ossWspolczMin = :ossWspolczMin"),
    @NamedQuery(name = "OsobaSkl.findByOssWspolczMax", query = "SELECT o FROM OsobaSkl o WHERE o.ossWspolczMax = :ossWspolczMax"),
    @NamedQuery(name = "OsobaSkl.findByOssAktywna", query = "SELECT o FROM OsobaSkl o WHERE o.ossAktywna = :ossAktywna"),
    @NamedQuery(name = "OsobaSkl.findByOssDod1", query = "SELECT o FROM OsobaSkl o WHERE o.ossDod1 = :ossDod1"),
    @NamedQuery(name = "OsobaSkl.findByOssDod2", query = "SELECT o FROM OsobaSkl o WHERE o.ossDod2 = :ossDod2"),
    @NamedQuery(name = "OsobaSkl.findByOssDod3", query = "SELECT o FROM OsobaSkl o WHERE o.ossDod3 = :ossDod3"),
    @NamedQuery(name = "OsobaSkl.findByOssMin", query = "SELECT o FROM OsobaSkl o WHERE o.ossMin = :ossMin"),
    @NamedQuery(name = "OsobaSkl.findByOssMax", query = "SELECT o FROM OsobaSkl o WHERE o.ossMax = :ossMax"),
    @NamedQuery(name = "OsobaSkl.findByOssKwota2", query = "SELECT o FROM OsobaSkl o WHERE o.ossKwota2 = :ossKwota2"),
    @NamedQuery(name = "OsobaSkl.findByOssKwotaMin", query = "SELECT o FROM OsobaSkl o WHERE o.ossKwotaMin = :ossKwotaMin"),
    @NamedQuery(name = "OsobaSkl.findByOssKwotaMax", query = "SELECT o FROM OsobaSkl o WHERE o.ossKwotaMax = :ossKwotaMax"),
    @NamedQuery(name = "OsobaSkl.findByOssTyp2", query = "SELECT o FROM OsobaSkl o WHERE o.ossTyp2 = :ossTyp2"),
    @NamedQuery(name = "OsobaSkl.findByOssTypMin", query = "SELECT o FROM OsobaSkl o WHERE o.ossTypMin = :ossTypMin"),
    @NamedQuery(name = "OsobaSkl.findByOssTypMax", query = "SELECT o FROM OsobaSkl o WHERE o.ossTypMax = :ossTypMax"),
    @NamedQuery(name = "OsobaSkl.findByOssWspolczMin2", query = "SELECT o FROM OsobaSkl o WHERE o.ossWspolczMin2 = :ossWspolczMin2"),
    @NamedQuery(name = "OsobaSkl.findByOssWspolczMax2", query = "SELECT o FROM OsobaSkl o WHERE o.ossWspolczMax2 = :ossWspolczMax2"),
    @NamedQuery(name = "OsobaSkl.findByOssRodzaj", query = "SELECT o FROM OsobaSkl o WHERE o.ossRodzaj = :ossRodzaj"),
    @NamedQuery(name = "OsobaSkl.findByOssWspolczMin3", query = "SELECT o FROM OsobaSkl o WHERE o.ossWspolczMin3 = :ossWspolczMin3"),
    @NamedQuery(name = "OsobaSkl.findByOssWspolczMax3", query = "SELECT o FROM OsobaSkl o WHERE o.ossWspolczMax3 = :ossWspolczMax3"),
    @NamedQuery(name = "OsobaSkl.findByOssWspolcz3", query = "SELECT o FROM OsobaSkl o WHERE o.ossWspolcz3 = :ossWspolcz3"),
    @NamedQuery(name = "OsobaSkl.findByOssKwota3", query = "SELECT o FROM OsobaSkl o WHERE o.ossKwota3 = :ossKwota3"),
    @NamedQuery(name = "OsobaSkl.findByOssTyp3", query = "SELECT o FROM OsobaSkl o WHERE o.ossTyp3 = :ossTyp3"),
    @NamedQuery(name = "OsobaSkl.findByOssInt1", query = "SELECT o FROM OsobaSkl o WHERE o.ossInt1 = :ossInt1"),
    @NamedQuery(name = "OsobaSkl.findByOssInt2", query = "SELECT o FROM OsobaSkl o WHERE o.ossInt2 = :ossInt2"),
    @NamedQuery(name = "OsobaSkl.findByOssNum1", query = "SELECT o FROM OsobaSkl o WHERE o.ossNum1 = :ossNum1"),
    @NamedQuery(name = "OsobaSkl.findByOssNum2", query = "SELECT o FROM OsobaSkl o WHERE o.ossNum2 = :ossNum2"),
    @NamedQuery(name = "OsobaSkl.findByOssNum3", query = "SELECT o FROM OsobaSkl o WHERE o.ossNum3 = :ossNum3"),
    @NamedQuery(name = "OsobaSkl.findByOssNum4", query = "SELECT o FROM OsobaSkl o WHERE o.ossNum4 = :ossNum4"),
    @NamedQuery(name = "OsobaSkl.findByOssDod4", query = "SELECT o FROM OsobaSkl o WHERE o.ossDod4 = :ossDod4"),
    @NamedQuery(name = "OsobaSkl.findByOssDod5", query = "SELECT o FROM OsobaSkl o WHERE o.ossDod5 = :ossDod5"),
    @NamedQuery(name = "OsobaSkl.findByOssDod6", query = "SELECT o FROM OsobaSkl o WHERE o.ossDod6 = :ossDod6"),
    @NamedQuery(name = "OsobaSkl.findByOssData1", query = "SELECT o FROM OsobaSkl o WHERE o.ossData1 = :ossData1"),
    @NamedQuery(name = "OsobaSkl.findByOssData2", query = "SELECT o FROM OsobaSkl o WHERE o.ossData2 = :ossData2"),
    @NamedQuery(name = "OsobaSkl.findByOssNazwa", query = "SELECT o FROM OsobaSkl o WHERE o.ossNazwa = :ossNazwa"),
    @NamedQuery(name = "OsobaSkl.findByOssWyrazenie", query = "SELECT o FROM OsobaSkl o WHERE o.ossWyrazenie = :ossWyrazenie"),
    @NamedQuery(name = "OsobaSkl.findByOssOssSerial1", query = "SELECT o FROM OsobaSkl o WHERE o.ossOssSerial1 = :ossOssSerial1"),
    @NamedQuery(name = "OsobaSkl.findByOssOssSerial2", query = "SELECT o FROM OsobaSkl o WHERE o.ossOssSerial2 = :ossOssSerial2"),
    @NamedQuery(name = "OsobaSkl.findByOssOssSerial3", query = "SELECT o FROM OsobaSkl o WHERE o.ossOssSerial3 = :ossOssSerial3")})
public class OsobaSkl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "oss_serial", nullable = false)
    private Integer ossSerial;
    @Column(name = "oss_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ossDataOd;
    @Column(name = "oss_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ossDataDo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "oss_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal ossKwota;
    @Column(name = "oss_status")
    private Character ossStatus;
    @Column(name = "oss_typ")
    private Character ossTyp;
    @Column(name = "oss_wspolcz", precision = 17, scale = 6)
    private BigDecimal ossWspolcz;
    @Column(name = "oss_wspolcz_2", precision = 17, scale = 6)
    private BigDecimal ossWspolcz2;
    @Column(name = "oss_wspolcz_min", precision = 17, scale = 6)
    private BigDecimal ossWspolczMin;
    @Column(name = "oss_wspolcz_max", precision = 17, scale = 6)
    private BigDecimal ossWspolczMax;
    @Column(name = "oss_aktywna")
    private Character ossAktywna;
    @Column(name = "oss_dod_1")
    private Character ossDod1;
    @Column(name = "oss_dod_2")
    private Character ossDod2;
    @Column(name = "oss_dod_3")
    private Character ossDod3;
    @Column(name = "oss_min")
    private Character ossMin;
    @Column(name = "oss_max")
    private Character ossMax;
    @Column(name = "oss_kwota_2", precision = 13, scale = 2)
    private BigDecimal ossKwota2;
    @Column(name = "oss_kwota_min", precision = 13, scale = 2)
    private BigDecimal ossKwotaMin;
    @Column(name = "oss_kwota_max", precision = 13, scale = 2)
    private BigDecimal ossKwotaMax;
    @Column(name = "oss_typ_2")
    private Character ossTyp2;
    @Column(name = "oss_typ_min")
    private Character ossTypMin;
    @Column(name = "oss_typ_max")
    private Character ossTypMax;
    @Column(name = "oss_wspolcz_min2", precision = 17, scale = 6)
    private BigDecimal ossWspolczMin2;
    @Column(name = "oss_wspolcz_max2", precision = 17, scale = 6)
    private BigDecimal ossWspolczMax2;
    @Column(name = "oss_rodzaj")
    private Character ossRodzaj;
    @Column(name = "oss_wspolcz_min3", precision = 17, scale = 6)
    private BigDecimal ossWspolczMin3;
    @Column(name = "oss_wspolcz_max3", precision = 17, scale = 6)
    private BigDecimal ossWspolczMax3;
    @Column(name = "oss_wspolcz_3", precision = 17, scale = 6)
    private BigDecimal ossWspolcz3;
    @Column(name = "oss_kwota_3", precision = 13, scale = 2)
    private BigDecimal ossKwota3;
    @Column(name = "oss_typ_3")
    private Character ossTyp3;
    @Column(name = "oss_int_1")
    private Integer ossInt1;
    @Column(name = "oss_int_2")
    private Integer ossInt2;
    @Column(name = "oss_num_1", precision = 17, scale = 6)
    private BigDecimal ossNum1;
    @Column(name = "oss_num_2", precision = 17, scale = 6)
    private BigDecimal ossNum2;
    @Column(name = "oss_num_3", precision = 17, scale = 6)
    private BigDecimal ossNum3;
    @Column(name = "oss_num_4", precision = 17, scale = 6)
    private BigDecimal ossNum4;
    @Column(name = "oss_dod_4")
    private Character ossDod4;
    @Column(name = "oss_dod_5")
    private Character ossDod5;
    @Column(name = "oss_dod_6")
    private Character ossDod6;
    @Column(name = "oss_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ossData1;
    @Column(name = "oss_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ossData2;
    @Size(max = 48)
    @Column(name = "oss_nazwa", length = 48)
    private String ossNazwa;
    @Column(name = "oss_wyrazenie")
    private Character ossWyrazenie;
    @Column(name = "oss_oss_serial_1")
    private Integer ossOssSerial1;
    @Column(name = "oss_oss_serial_2")
    private Integer ossOssSerial2;
    @Column(name = "oss_oss_serial_3")
    private Integer ossOssSerial3;
    @OneToMany(mappedBy = "dssOssSerial")
    private List<DaneStatS> daneStatSList;
    @JoinColumn(name = "oss_ssd_serial_max3", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ossSsdSerialMax3;
    @JoinColumn(name = "oss_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba ossOsoSerial;
    @JoinColumn(name = "oss_ssd_serial_min3", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ossSsdSerialMin3;
    @JoinColumn(name = "oss_ssd_serial_1", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ossSsdSerial1;
    @JoinColumn(name = "oss_ssd_serial_2", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ossSsdSerial2;
    @JoinColumn(name = "oss_wkp_serial", referencedColumnName = "wkp_serial")
    @ManyToOne
    private WynKodPrz ossWkpSerial;
    @JoinColumn(name = "oss_ssd_serial_min", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ossSsdSerialMin;
    @JoinColumn(name = "oss_wks_serial", referencedColumnName = "wks_serial")
    @ManyToOne
    private WynKodSkl ossWksSerial;
    @JoinColumn(name = "oss_ssd_serial_max", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ossSsdSerialMax;
    @JoinColumn(name = "oss_ssd_serial_min2", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ossSsdSerialMin2;
    @JoinColumn(name = "oss_ssd_serial_max2", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ossSsdSerialMax2;
    @JoinColumn(name = "oss_ssd_serial_3", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis ossSsdSerial3;
    @JoinColumn(name = "oss_tyt_serial", referencedColumnName = "tyt_serial")
    @ManyToOne
    private Tytul ossTytSerial;

    public OsobaSkl() {
    }

    public OsobaSkl(Integer ossSerial) {
        this.ossSerial = ossSerial;
    }

    public OsobaSkl(Integer ossSerial, BigDecimal ossKwota) {
        this.ossSerial = ossSerial;
        this.ossKwota = ossKwota;
    }

    public Integer getOssSerial() {
        return ossSerial;
    }

    public void setOssSerial(Integer ossSerial) {
        this.ossSerial = ossSerial;
    }

    public Date getOssDataOd() {
        return ossDataOd;
    }

    public void setOssDataOd(Date ossDataOd) {
        this.ossDataOd = ossDataOd;
    }

    public Date getOssDataDo() {
        return ossDataDo;
    }

    public void setOssDataDo(Date ossDataDo) {
        this.ossDataDo = ossDataDo;
    }

    public BigDecimal getOssKwota() {
        return ossKwota;
    }

    public void setOssKwota(BigDecimal ossKwota) {
        this.ossKwota = ossKwota;
    }

    public Character getOssStatus() {
        return ossStatus;
    }

    public void setOssStatus(Character ossStatus) {
        this.ossStatus = ossStatus;
    }

    public Character getOssTyp() {
        return ossTyp;
    }

    public void setOssTyp(Character ossTyp) {
        this.ossTyp = ossTyp;
    }

    public BigDecimal getOssWspolcz() {
        return ossWspolcz;
    }

    public void setOssWspolcz(BigDecimal ossWspolcz) {
        this.ossWspolcz = ossWspolcz;
    }

    public BigDecimal getOssWspolcz2() {
        return ossWspolcz2;
    }

    public void setOssWspolcz2(BigDecimal ossWspolcz2) {
        this.ossWspolcz2 = ossWspolcz2;
    }

    public BigDecimal getOssWspolczMin() {
        return ossWspolczMin;
    }

    public void setOssWspolczMin(BigDecimal ossWspolczMin) {
        this.ossWspolczMin = ossWspolczMin;
    }

    public BigDecimal getOssWspolczMax() {
        return ossWspolczMax;
    }

    public void setOssWspolczMax(BigDecimal ossWspolczMax) {
        this.ossWspolczMax = ossWspolczMax;
    }

    public Character getOssAktywna() {
        return ossAktywna;
    }

    public void setOssAktywna(Character ossAktywna) {
        this.ossAktywna = ossAktywna;
    }

    public Character getOssDod1() {
        return ossDod1;
    }

    public void setOssDod1(Character ossDod1) {
        this.ossDod1 = ossDod1;
    }

    public Character getOssDod2() {
        return ossDod2;
    }

    public void setOssDod2(Character ossDod2) {
        this.ossDod2 = ossDod2;
    }

    public Character getOssDod3() {
        return ossDod3;
    }

    public void setOssDod3(Character ossDod3) {
        this.ossDod3 = ossDod3;
    }

    public Character getOssMin() {
        return ossMin;
    }

    public void setOssMin(Character ossMin) {
        this.ossMin = ossMin;
    }

    public Character getOssMax() {
        return ossMax;
    }

    public void setOssMax(Character ossMax) {
        this.ossMax = ossMax;
    }

    public BigDecimal getOssKwota2() {
        return ossKwota2;
    }

    public void setOssKwota2(BigDecimal ossKwota2) {
        this.ossKwota2 = ossKwota2;
    }

    public BigDecimal getOssKwotaMin() {
        return ossKwotaMin;
    }

    public void setOssKwotaMin(BigDecimal ossKwotaMin) {
        this.ossKwotaMin = ossKwotaMin;
    }

    public BigDecimal getOssKwotaMax() {
        return ossKwotaMax;
    }

    public void setOssKwotaMax(BigDecimal ossKwotaMax) {
        this.ossKwotaMax = ossKwotaMax;
    }

    public Character getOssTyp2() {
        return ossTyp2;
    }

    public void setOssTyp2(Character ossTyp2) {
        this.ossTyp2 = ossTyp2;
    }

    public Character getOssTypMin() {
        return ossTypMin;
    }

    public void setOssTypMin(Character ossTypMin) {
        this.ossTypMin = ossTypMin;
    }

    public Character getOssTypMax() {
        return ossTypMax;
    }

    public void setOssTypMax(Character ossTypMax) {
        this.ossTypMax = ossTypMax;
    }

    public BigDecimal getOssWspolczMin2() {
        return ossWspolczMin2;
    }

    public void setOssWspolczMin2(BigDecimal ossWspolczMin2) {
        this.ossWspolczMin2 = ossWspolczMin2;
    }

    public BigDecimal getOssWspolczMax2() {
        return ossWspolczMax2;
    }

    public void setOssWspolczMax2(BigDecimal ossWspolczMax2) {
        this.ossWspolczMax2 = ossWspolczMax2;
    }

    public Character getOssRodzaj() {
        return ossRodzaj;
    }

    public void setOssRodzaj(Character ossRodzaj) {
        this.ossRodzaj = ossRodzaj;
    }

    public BigDecimal getOssWspolczMin3() {
        return ossWspolczMin3;
    }

    public void setOssWspolczMin3(BigDecimal ossWspolczMin3) {
        this.ossWspolczMin3 = ossWspolczMin3;
    }

    public BigDecimal getOssWspolczMax3() {
        return ossWspolczMax3;
    }

    public void setOssWspolczMax3(BigDecimal ossWspolczMax3) {
        this.ossWspolczMax3 = ossWspolczMax3;
    }

    public BigDecimal getOssWspolcz3() {
        return ossWspolcz3;
    }

    public void setOssWspolcz3(BigDecimal ossWspolcz3) {
        this.ossWspolcz3 = ossWspolcz3;
    }

    public BigDecimal getOssKwota3() {
        return ossKwota3;
    }

    public void setOssKwota3(BigDecimal ossKwota3) {
        this.ossKwota3 = ossKwota3;
    }

    public Character getOssTyp3() {
        return ossTyp3;
    }

    public void setOssTyp3(Character ossTyp3) {
        this.ossTyp3 = ossTyp3;
    }

    public Integer getOssInt1() {
        return ossInt1;
    }

    public void setOssInt1(Integer ossInt1) {
        this.ossInt1 = ossInt1;
    }

    public Integer getOssInt2() {
        return ossInt2;
    }

    public void setOssInt2(Integer ossInt2) {
        this.ossInt2 = ossInt2;
    }

    public BigDecimal getOssNum1() {
        return ossNum1;
    }

    public void setOssNum1(BigDecimal ossNum1) {
        this.ossNum1 = ossNum1;
    }

    public BigDecimal getOssNum2() {
        return ossNum2;
    }

    public void setOssNum2(BigDecimal ossNum2) {
        this.ossNum2 = ossNum2;
    }

    public BigDecimal getOssNum3() {
        return ossNum3;
    }

    public void setOssNum3(BigDecimal ossNum3) {
        this.ossNum3 = ossNum3;
    }

    public BigDecimal getOssNum4() {
        return ossNum4;
    }

    public void setOssNum4(BigDecimal ossNum4) {
        this.ossNum4 = ossNum4;
    }

    public Character getOssDod4() {
        return ossDod4;
    }

    public void setOssDod4(Character ossDod4) {
        this.ossDod4 = ossDod4;
    }

    public Character getOssDod5() {
        return ossDod5;
    }

    public void setOssDod5(Character ossDod5) {
        this.ossDod5 = ossDod5;
    }

    public Character getOssDod6() {
        return ossDod6;
    }

    public void setOssDod6(Character ossDod6) {
        this.ossDod6 = ossDod6;
    }

    public Date getOssData1() {
        return ossData1;
    }

    public void setOssData1(Date ossData1) {
        this.ossData1 = ossData1;
    }

    public Date getOssData2() {
        return ossData2;
    }

    public void setOssData2(Date ossData2) {
        this.ossData2 = ossData2;
    }

    public String getOssNazwa() {
        return ossNazwa;
    }

    public void setOssNazwa(String ossNazwa) {
        this.ossNazwa = ossNazwa;
    }

    public Character getOssWyrazenie() {
        return ossWyrazenie;
    }

    public void setOssWyrazenie(Character ossWyrazenie) {
        this.ossWyrazenie = ossWyrazenie;
    }

    public Integer getOssOssSerial1() {
        return ossOssSerial1;
    }

    public void setOssOssSerial1(Integer ossOssSerial1) {
        this.ossOssSerial1 = ossOssSerial1;
    }

    public Integer getOssOssSerial2() {
        return ossOssSerial2;
    }

    public void setOssOssSerial2(Integer ossOssSerial2) {
        this.ossOssSerial2 = ossOssSerial2;
    }

    public Integer getOssOssSerial3() {
        return ossOssSerial3;
    }

    public void setOssOssSerial3(Integer ossOssSerial3) {
        this.ossOssSerial3 = ossOssSerial3;
    }

    @XmlTransient
    public List<DaneStatS> getDaneStatSList() {
        return daneStatSList;
    }

    public void setDaneStatSList(List<DaneStatS> daneStatSList) {
        this.daneStatSList = daneStatSList;
    }

    public StSystOpis getOssSsdSerialMax3() {
        return ossSsdSerialMax3;
    }

    public void setOssSsdSerialMax3(StSystOpis ossSsdSerialMax3) {
        this.ossSsdSerialMax3 = ossSsdSerialMax3;
    }

    public Osoba getOssOsoSerial() {
        return ossOsoSerial;
    }

    public void setOssOsoSerial(Osoba ossOsoSerial) {
        this.ossOsoSerial = ossOsoSerial;
    }

    public StSystOpis getOssSsdSerialMin3() {
        return ossSsdSerialMin3;
    }

    public void setOssSsdSerialMin3(StSystOpis ossSsdSerialMin3) {
        this.ossSsdSerialMin3 = ossSsdSerialMin3;
    }

    public StSystOpis getOssSsdSerial1() {
        return ossSsdSerial1;
    }

    public void setOssSsdSerial1(StSystOpis ossSsdSerial1) {
        this.ossSsdSerial1 = ossSsdSerial1;
    }

    public StSystOpis getOssSsdSerial2() {
        return ossSsdSerial2;
    }

    public void setOssSsdSerial2(StSystOpis ossSsdSerial2) {
        this.ossSsdSerial2 = ossSsdSerial2;
    }

    public WynKodPrz getOssWkpSerial() {
        return ossWkpSerial;
    }

    public void setOssWkpSerial(WynKodPrz ossWkpSerial) {
        this.ossWkpSerial = ossWkpSerial;
    }

    public StSystOpis getOssSsdSerialMin() {
        return ossSsdSerialMin;
    }

    public void setOssSsdSerialMin(StSystOpis ossSsdSerialMin) {
        this.ossSsdSerialMin = ossSsdSerialMin;
    }

    public WynKodSkl getOssWksSerial() {
        return ossWksSerial;
    }

    public void setOssWksSerial(WynKodSkl ossWksSerial) {
        this.ossWksSerial = ossWksSerial;
    }

    public StSystOpis getOssSsdSerialMax() {
        return ossSsdSerialMax;
    }

    public void setOssSsdSerialMax(StSystOpis ossSsdSerialMax) {
        this.ossSsdSerialMax = ossSsdSerialMax;
    }

    public StSystOpis getOssSsdSerialMin2() {
        return ossSsdSerialMin2;
    }

    public void setOssSsdSerialMin2(StSystOpis ossSsdSerialMin2) {
        this.ossSsdSerialMin2 = ossSsdSerialMin2;
    }

    public StSystOpis getOssSsdSerialMax2() {
        return ossSsdSerialMax2;
    }

    public void setOssSsdSerialMax2(StSystOpis ossSsdSerialMax2) {
        this.ossSsdSerialMax2 = ossSsdSerialMax2;
    }

    public StSystOpis getOssSsdSerial3() {
        return ossSsdSerial3;
    }

    public void setOssSsdSerial3(StSystOpis ossSsdSerial3) {
        this.ossSsdSerial3 = ossSsdSerial3;
    }

    public Tytul getOssTytSerial() {
        return ossTytSerial;
    }

    public void setOssTytSerial(Tytul ossTytSerial) {
        this.ossTytSerial = ossTytSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ossSerial != null ? ossSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OsobaSkl)) {
            return false;
        }
        OsobaSkl other = (OsobaSkl) object;
        if ((this.ossSerial == null && other.ossSerial != null) || (this.ossSerial != null && !this.ossSerial.equals(other.ossSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OsobaSkl{" + "ossSerial=" + ossSerial + ", ossDataOd=" + ossDataOd + ", ossDataDo=" + ossDataDo + ", ossKwota=" + ossKwota + ", ossStatus=" + ossStatus + ", ossTyp=" + ossTyp + ", ossAktywna=" + ossAktywna + ", ossDod1=" + ossDod1 + ", ossRodzaj=" + ossRodzaj + ", ossTyp3=" + ossTyp3 + '}';
    }

   
    
}
