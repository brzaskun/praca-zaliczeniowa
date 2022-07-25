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
@Table(name = "osoba_prz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OsobaPrz.findAll", query = "SELECT o FROM OsobaPrz o"),
    @NamedQuery(name = "OsobaPrz.findByOspSerial", query = "SELECT o FROM OsobaPrz o WHERE o.ospSerial = :ospSerial"),
    @NamedQuery(name = "OsobaPrz.findByOspDataOd", query = "SELECT o FROM OsobaPrz o WHERE o.ospDataOd = :ospDataOd"),
    @NamedQuery(name = "OsobaPrz.findByOspDataDo", query = "SELECT o FROM OsobaPrz o WHERE o.ospDataDo = :ospDataDo"),
    @NamedQuery(name = "OsobaPrz.findByOspLiczba", query = "SELECT o FROM OsobaPrz o WHERE o.ospLiczba = :ospLiczba"),
    @NamedQuery(name = "OsobaPrz.findByOspKodCh1", query = "SELECT o FROM OsobaPrz o WHERE o.ospKodCh1 = :ospKodCh1"),
    @NamedQuery(name = "OsobaPrz.findByOspKodCh2", query = "SELECT o FROM OsobaPrz o WHERE o.ospKodCh2 = :ospKodCh2"),
    @NamedQuery(name = "OsobaPrz.findByOspKwota", query = "SELECT o FROM OsobaPrz o WHERE o.ospKwota = :ospKwota"),
    @NamedQuery(name = "OsobaPrz.findByOspStatus", query = "SELECT o FROM OsobaPrz o WHERE o.ospStatus = :ospStatus"),
    @NamedQuery(name = "OsobaPrz.findByOspKodDod1", query = "SELECT o FROM OsobaPrz o WHERE o.ospKodDod1 = :ospKodDod1"),
    @NamedQuery(name = "OsobaPrz.findByOspPrzekrRpw", query = "SELECT o FROM OsobaPrz o WHERE o.ospPrzekrRpw = :ospPrzekrRpw"),
    @NamedQuery(name = "OsobaPrz.findByOspDod1", query = "SELECT o FROM OsobaPrz o WHERE o.ospDod1 = :ospDod1"),
    @NamedQuery(name = "OsobaPrz.findByOspDod2", query = "SELECT o FROM OsobaPrz o WHERE o.ospDod2 = :ospDod2"),
    @NamedQuery(name = "OsobaPrz.findByOspDod3", query = "SELECT o FROM OsobaPrz o WHERE o.ospDod3 = :ospDod3"),
    @NamedQuery(name = "OsobaPrz.findByOspDod4", query = "SELECT o FROM OsobaPrz o WHERE o.ospDod4 = :ospDod4"),
    @NamedQuery(name = "OsobaPrz.findByOspDod5", query = "SELECT o FROM OsobaPrz o WHERE o.ospDod5 = :ospDod5"),
    @NamedQuery(name = "OsobaPrz.findByOspDod6", query = "SELECT o FROM OsobaPrz o WHERE o.ospDod6 = :ospDod6"),
    @NamedQuery(name = "OsobaPrz.findByOspDod7", query = "SELECT o FROM OsobaPrz o WHERE o.ospDod7 = :ospDod7"),
    @NamedQuery(name = "OsobaPrz.findByOspDod8", query = "SELECT o FROM OsobaPrz o WHERE o.ospDod8 = :ospDod8"),
    @NamedQuery(name = "OsobaPrz.findByOspNum1", query = "SELECT o FROM OsobaPrz o WHERE o.ospNum1 = :ospNum1"),
    @NamedQuery(name = "OsobaPrz.findByOspNum2", query = "SELECT o FROM OsobaPrz o WHERE o.ospNum2 = :ospNum2"),
    @NamedQuery(name = "OsobaPrz.findByOspNum3", query = "SELECT o FROM OsobaPrz o WHERE o.ospNum3 = :ospNum3"),
    @NamedQuery(name = "OsobaPrz.findByOspNum4", query = "SELECT o FROM OsobaPrz o WHERE o.ospNum4 = :ospNum4"),
    @NamedQuery(name = "OsobaPrz.findByOspInt1", query = "SELECT o FROM OsobaPrz o WHERE o.ospInt1 = :ospInt1"),
    @NamedQuery(name = "OsobaPrz.findByOspInt2", query = "SELECT o FROM OsobaPrz o WHERE o.ospInt2 = :ospInt2"),
    @NamedQuery(name = "OsobaPrz.findByOspData1", query = "SELECT o FROM OsobaPrz o WHERE o.ospData1 = :ospData1"),
    @NamedQuery(name = "OsobaPrz.findByOspData2", query = "SELECT o FROM OsobaPrz o WHERE o.ospData2 = :ospData2"),
    @NamedQuery(name = "OsobaPrz.findByOspTyp", query = "SELECT o FROM OsobaPrz o WHERE o.ospTyp = :ospTyp"),
    @NamedQuery(name = "OsobaPrz.findByOspVchar1", query = "SELECT o FROM OsobaPrz o WHERE o.ospVchar1 = :ospVchar1"),
    @NamedQuery(name = "OsobaPrz.findByOspVchar2", query = "SELECT o FROM OsobaPrz o WHERE o.ospVchar2 = :ospVchar2"),
    @NamedQuery(name = "OsobaPrz.findByOspInt3", query = "SELECT o FROM OsobaPrz o WHERE o.ospInt3 = :ospInt3"),
    @NamedQuery(name = "OsobaPrz.findByOspInt4", query = "SELECT o FROM OsobaPrz o WHERE o.ospInt4 = :ospInt4")})
public class OsobaPrz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "osp_serial", nullable = false)
    private Integer ospSerial;
    @Column(name = "osp_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospDataOd;
    @Column(name = "osp_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospDataDo;
    @Column(name = "osp_liczba")
    private Short ospLiczba;
    @Size(max = 1)
    @Column(name = "osp_kod_ch_1", length = 1)
    private String ospKodCh1;
    @Size(max = 1)
    @Column(name = "osp_kod_ch_2", length = 1)
    private String ospKodCh2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "osp_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal ospKwota;
    @Column(name = "osp_status")
    private Character ospStatus;
    @Column(name = "osp_kod_dod_1")
    private Character ospKodDod1;
    @Column(name = "osp_przekr_rpw")
    private Character ospPrzekrRpw;
    @Column(name = "osp_dod_1")
    private Character ospDod1;
    @Column(name = "osp_dod_2")
    private Character ospDod2;
    @Column(name = "osp_dod_3")
    private Character ospDod3;
    @Column(name = "osp_dod_4")
    private Character ospDod4;
    @Column(name = "osp_dod_5")
    private Character ospDod5;
    @Column(name = "osp_dod_6")
    private Character ospDod6;
    @Column(name = "osp_dod_7")
    private Character ospDod7;
    @Column(name = "osp_dod_8")
    private Character ospDod8;
    @Column(name = "osp_num_1", precision = 17, scale = 6)
    private BigDecimal ospNum1;
    @Column(name = "osp_num_2", precision = 17, scale = 6)
    private BigDecimal ospNum2;
    @Column(name = "osp_num_3", precision = 17, scale = 6)
    private BigDecimal ospNum3;
    @Column(name = "osp_num_4", precision = 17, scale = 6)
    private BigDecimal ospNum4;
    @Column(name = "osp_int_1")
    private Integer ospInt1;
    @Column(name = "osp_int_2")
    private Integer ospInt2;
    @Column(name = "osp_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospData1;
    @Column(name = "osp_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospData2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "osp_typ", nullable = false)
    private Character ospTyp;
    @Size(max = 64)
    @Column(name = "osp_vchar_1", length = 64)
    private String ospVchar1;
    @Size(max = 64)
    @Column(name = "osp_vchar_2", length = 64)
    private String ospVchar2;
    @Column(name = "osp_int_3")
    private Integer ospInt3;
    @Column(name = "osp_int_4")
    private Integer ospInt4;
    @OneToMany(mappedBy = "dssOspSerialPrz")
    private List<DaneStatS> daneStatSList;
    @JoinColumn(name = "osp_abs_serial", referencedColumnName = "abs_serial")
    @ManyToOne
    private Absencja ospAbsSerial;
    @JoinColumn(name = "osp_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba ospOsoSerial;
    @JoinColumn(name = "osp_tyt_serial", referencedColumnName = "tyt_serial")
    @ManyToOne
    private Tytul ospTytSerial;
    @JoinColumn(name = "osp_wkp_serial", referencedColumnName = "wkp_serial")
    @ManyToOne
    private WynKodPrz ospWkpSerial;

    public OsobaPrz() {
    }

    public OsobaPrz(Integer ospSerial) {
        this.ospSerial = ospSerial;
    }

    public OsobaPrz(Integer ospSerial, BigDecimal ospKwota, Character ospTyp) {
        this.ospSerial = ospSerial;
        this.ospKwota = ospKwota;
        this.ospTyp = ospTyp;
    }

    public Integer getOspSerial() {
        return ospSerial;
    }

    public void setOspSerial(Integer ospSerial) {
        this.ospSerial = ospSerial;
    }

    public Date getOspDataOd() {
        return ospDataOd;
    }

    public void setOspDataOd(Date ospDataOd) {
        this.ospDataOd = ospDataOd;
    }

    public Date getOspDataDo() {
        return ospDataDo;
    }

    public void setOspDataDo(Date ospDataDo) {
        this.ospDataDo = ospDataDo;
    }

    public Short getOspLiczba() {
        return ospLiczba;
    }

    public void setOspLiczba(Short ospLiczba) {
        this.ospLiczba = ospLiczba;
    }

    public String getOspKodCh1() {
        return ospKodCh1;
    }

    public void setOspKodCh1(String ospKodCh1) {
        this.ospKodCh1 = ospKodCh1;
    }

    public String getOspKodCh2() {
        return ospKodCh2;
    }

    public void setOspKodCh2(String ospKodCh2) {
        this.ospKodCh2 = ospKodCh2;
    }

    public BigDecimal getOspKwota() {
        return ospKwota;
    }

    public void setOspKwota(BigDecimal ospKwota) {
        this.ospKwota = ospKwota;
    }

    public Character getOspStatus() {
        return ospStatus;
    }

    public void setOspStatus(Character ospStatus) {
        this.ospStatus = ospStatus;
    }

    public Character getOspKodDod1() {
        return ospKodDod1;
    }

    public void setOspKodDod1(Character ospKodDod1) {
        this.ospKodDod1 = ospKodDod1;
    }

    public Character getOspPrzekrRpw() {
        return ospPrzekrRpw;
    }

    public void setOspPrzekrRpw(Character ospPrzekrRpw) {
        this.ospPrzekrRpw = ospPrzekrRpw;
    }

    public Character getOspDod1() {
        return ospDod1;
    }

    public void setOspDod1(Character ospDod1) {
        this.ospDod1 = ospDod1;
    }

    public Character getOspDod2() {
        return ospDod2;
    }

    public void setOspDod2(Character ospDod2) {
        this.ospDod2 = ospDod2;
    }

    public Character getOspDod3() {
        return ospDod3;
    }

    public void setOspDod3(Character ospDod3) {
        this.ospDod3 = ospDod3;
    }

    public Character getOspDod4() {
        return ospDod4;
    }

    public void setOspDod4(Character ospDod4) {
        this.ospDod4 = ospDod4;
    }

    public Character getOspDod5() {
        return ospDod5;
    }

    public void setOspDod5(Character ospDod5) {
        this.ospDod5 = ospDod5;
    }

    public Character getOspDod6() {
        return ospDod6;
    }

    public void setOspDod6(Character ospDod6) {
        this.ospDod6 = ospDod6;
    }

    public Character getOspDod7() {
        return ospDod7;
    }

    public void setOspDod7(Character ospDod7) {
        this.ospDod7 = ospDod7;
    }

    public Character getOspDod8() {
        return ospDod8;
    }

    public void setOspDod8(Character ospDod8) {
        this.ospDod8 = ospDod8;
    }

    public BigDecimal getOspNum1() {
        return ospNum1;
    }

    public void setOspNum1(BigDecimal ospNum1) {
        this.ospNum1 = ospNum1;
    }

    public BigDecimal getOspNum2() {
        return ospNum2;
    }

    public void setOspNum2(BigDecimal ospNum2) {
        this.ospNum2 = ospNum2;
    }

    public BigDecimal getOspNum3() {
        return ospNum3;
    }

    public void setOspNum3(BigDecimal ospNum3) {
        this.ospNum3 = ospNum3;
    }

    public BigDecimal getOspNum4() {
        return ospNum4;
    }

    public void setOspNum4(BigDecimal ospNum4) {
        this.ospNum4 = ospNum4;
    }

    public Integer getOspInt1() {
        return ospInt1;
    }

    public void setOspInt1(Integer ospInt1) {
        this.ospInt1 = ospInt1;
    }

    public Integer getOspInt2() {
        return ospInt2;
    }

    public void setOspInt2(Integer ospInt2) {
        this.ospInt2 = ospInt2;
    }

    public Date getOspData1() {
        return ospData1;
    }

    public void setOspData1(Date ospData1) {
        this.ospData1 = ospData1;
    }

    public Date getOspData2() {
        return ospData2;
    }

    public void setOspData2(Date ospData2) {
        this.ospData2 = ospData2;
    }

    public Character getOspTyp() {
        return ospTyp;
    }

    public void setOspTyp(Character ospTyp) {
        this.ospTyp = ospTyp;
    }

    public String getOspVchar1() {
        return ospVchar1;
    }

    public void setOspVchar1(String ospVchar1) {
        this.ospVchar1 = ospVchar1;
    }

    public String getOspVchar2() {
        return ospVchar2;
    }

    public void setOspVchar2(String ospVchar2) {
        this.ospVchar2 = ospVchar2;
    }

    public Integer getOspInt3() {
        return ospInt3;
    }

    public void setOspInt3(Integer ospInt3) {
        this.ospInt3 = ospInt3;
    }

    public Integer getOspInt4() {
        return ospInt4;
    }

    public void setOspInt4(Integer ospInt4) {
        this.ospInt4 = ospInt4;
    }

    @XmlTransient
    public List<DaneStatS> getDaneStatSList() {
        return daneStatSList;
    }

    public void setDaneStatSList(List<DaneStatS> daneStatSList) {
        this.daneStatSList = daneStatSList;
    }

    public Absencja getOspAbsSerial() {
        return ospAbsSerial;
    }

    public void setOspAbsSerial(Absencja ospAbsSerial) {
        this.ospAbsSerial = ospAbsSerial;
    }

    public Osoba getOspOsoSerial() {
        return ospOsoSerial;
    }

    public void setOspOsoSerial(Osoba ospOsoSerial) {
        this.ospOsoSerial = ospOsoSerial;
    }

    public Tytul getOspTytSerial() {
        return ospTytSerial;
    }

    public void setOspTytSerial(Tytul ospTytSerial) {
        this.ospTytSerial = ospTytSerial;
    }

    public WynKodPrz getOspWkpSerial() {
        return ospWkpSerial;
    }

    public void setOspWkpSerial(WynKodPrz ospWkpSerial) {
        this.ospWkpSerial = ospWkpSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ospSerial != null ? ospSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OsobaPrz)) {
            return false;
        }
        OsobaPrz other = (OsobaPrz) object;
        if ((this.ospSerial == null && other.ospSerial != null) || (this.ospSerial != null && !this.ospSerial.equals(other.ospSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.OsobaPrz[ ospSerial=" + ospSerial + " ]";
    }
    
}
