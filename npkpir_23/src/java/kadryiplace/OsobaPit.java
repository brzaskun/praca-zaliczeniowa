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
@Table(name = "osoba_pit", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OsobaPit.findAll", query = "SELECT o FROM OsobaPit o"),
    @NamedQuery(name = "OsobaPit.findByOspSerial", query = "SELECT o FROM OsobaPit o WHERE o.ospSerial = :ospSerial"),
    @NamedQuery(name = "OsobaPit.findByOspTytul", query = "SELECT o FROM OsobaPit o WHERE o.ospTytul = :ospTytul"),
    @NamedQuery(name = "OsobaPit.findByOspK49", query = "SELECT o FROM OsobaPit o WHERE o.ospK49 = :ospK49"),
    @NamedQuery(name = "OsobaPit.findByOspK56", query = "SELECT o FROM OsobaPit o WHERE o.ospK56 = :ospK56"),
    @NamedQuery(name = "OsobaPit.findByOspK61", query = "SELECT o FROM OsobaPit o WHERE o.ospK61 = :ospK61"),
    @NamedQuery(name = "OsobaPit.findByOspK69", query = "SELECT o FROM OsobaPit o WHERE o.ospK69 = :ospK69"),
    @NamedQuery(name = "OsobaPit.findByOspK79", query = "SELECT o FROM OsobaPit o WHERE o.ospK79 = :ospK79"),
    @NamedQuery(name = "OsobaPit.findByOspK111", query = "SELECT o FROM OsobaPit o WHERE o.ospK111 = :ospK111"),
    @NamedQuery(name = "OsobaPit.findByOspK112", query = "SELECT o FROM OsobaPit o WHERE o.ospK112 = :ospK112"),
    @NamedQuery(name = "OsobaPit.findByOspK113", query = "SELECT o FROM OsobaPit o WHERE o.ospK113 = :ospK113"),
    @NamedQuery(name = "OsobaPit.findByOspK114", query = "SELECT o FROM OsobaPit o WHERE o.ospK114 = :ospK114"),
    @NamedQuery(name = "OsobaPit.findByOspK120", query = "SELECT o FROM OsobaPit o WHERE o.ospK120 = :ospK120"),
    @NamedQuery(name = "OsobaPit.findByOspK121", query = "SELECT o FROM OsobaPit o WHERE o.ospK121 = :ospK121"),
    @NamedQuery(name = "OsobaPit.findByOspK122", query = "SELECT o FROM OsobaPit o WHERE o.ospK122 = :ospK122"),
    @NamedQuery(name = "OsobaPit.findByOspK123", query = "SELECT o FROM OsobaPit o WHERE o.ospK123 = :ospK123"),
    @NamedQuery(name = "OsobaPit.findByOspK129", query = "SELECT o FROM OsobaPit o WHERE o.ospK129 = :ospK129"),
    @NamedQuery(name = "OsobaPit.findByOspK130", query = "SELECT o FROM OsobaPit o WHERE o.ospK130 = :ospK130"),
    @NamedQuery(name = "OsobaPit.findByOspK136", query = "SELECT o FROM OsobaPit o WHERE o.ospK136 = :ospK136"),
    @NamedQuery(name = "OsobaPit.findByOspK137", query = "SELECT o FROM OsobaPit o WHERE o.ospK137 = :ospK137"),
    @NamedQuery(name = "OsobaPit.findByOspK143", query = "SELECT o FROM OsobaPit o WHERE o.ospK143 = :ospK143"),
    @NamedQuery(name = "OsobaPit.findByOspK144", query = "SELECT o FROM OsobaPit o WHERE o.ospK144 = :ospK144"),
    @NamedQuery(name = "OsobaPit.findByOspK150", query = "SELECT o FROM OsobaPit o WHERE o.ospK150 = :ospK150"),
    @NamedQuery(name = "OsobaPit.findByOspK151", query = "SELECT o FROM OsobaPit o WHERE o.ospK151 = :ospK151"),
    @NamedQuery(name = "OsobaPit.findByOspK157", query = "SELECT o FROM OsobaPit o WHERE o.ospK157 = :ospK157"),
    @NamedQuery(name = "OsobaPit.findByOspK158", query = "SELECT o FROM OsobaPit o WHERE o.ospK158 = :ospK158"),
    @NamedQuery(name = "OsobaPit.findByOspK159", query = "SELECT o FROM OsobaPit o WHERE o.ospK159 = :ospK159"),
    @NamedQuery(name = "OsobaPit.findByOspK160", query = "SELECT o FROM OsobaPit o WHERE o.ospK160 = :ospK160"),
    @NamedQuery(name = "OsobaPit.findByOspK161", query = "SELECT o FROM OsobaPit o WHERE o.ospK161 = :ospK161"),
    @NamedQuery(name = "OsobaPit.findByOspK162", query = "SELECT o FROM OsobaPit o WHERE o.ospK162 = :ospK162"),
    @NamedQuery(name = "OsobaPit.findByOspK163", query = "SELECT o FROM OsobaPit o WHERE o.ospK163 = :ospK163"),
    @NamedQuery(name = "OsobaPit.findByOspK164", query = "SELECT o FROM OsobaPit o WHERE o.ospK164 = :ospK164"),
    @NamedQuery(name = "OsobaPit.findByOspK172", query = "SELECT o FROM OsobaPit o WHERE o.ospK172 = :ospK172"),
    @NamedQuery(name = "OsobaPit.findByOspK173", query = "SELECT o FROM OsobaPit o WHERE o.ospK173 = :ospK173"),
    @NamedQuery(name = "OsobaPit.findByOspK174", query = "SELECT o FROM OsobaPit o WHERE o.ospK174 = :ospK174"),
    @NamedQuery(name = "OsobaPit.findByOspK175", query = "SELECT o FROM OsobaPit o WHERE o.ospK175 = :ospK175"),
    @NamedQuery(name = "OsobaPit.findByOspK176", query = "SELECT o FROM OsobaPit o WHERE o.ospK176 = :ospK176"),
    @NamedQuery(name = "OsobaPit.findByOspK177", query = "SELECT o FROM OsobaPit o WHERE o.ospK177 = :ospK177"),
    @NamedQuery(name = "OsobaPit.findByOspK178", query = "SELECT o FROM OsobaPit o WHERE o.ospK178 = :ospK178"),
    @NamedQuery(name = "OsobaPit.findByOspK179", query = "SELECT o FROM OsobaPit o WHERE o.ospK179 = :ospK179"),
    @NamedQuery(name = "OsobaPit.findByOspK106", query = "SELECT o FROM OsobaPit o WHERE o.ospK106 = :ospK106"),
    @NamedQuery(name = "OsobaPit.findByOspK115", query = "SELECT o FROM OsobaPit o WHERE o.ospK115 = :ospK115"),
    @NamedQuery(name = "OsobaPit.findByOspK124", query = "SELECT o FROM OsobaPit o WHERE o.ospK124 = :ospK124"),
    @NamedQuery(name = "OsobaPit.findByOspK131", query = "SELECT o FROM OsobaPit o WHERE o.ospK131 = :ospK131"),
    @NamedQuery(name = "OsobaPit.findByOspK138", query = "SELECT o FROM OsobaPit o WHERE o.ospK138 = :ospK138"),
    @NamedQuery(name = "OsobaPit.findByOspK145", query = "SELECT o FROM OsobaPit o WHERE o.ospK145 = :ospK145"),
    @NamedQuery(name = "OsobaPit.findByOspK152", query = "SELECT o FROM OsobaPit o WHERE o.ospK152 = :ospK152"),
    @NamedQuery(name = "OsobaPit.findByOspDzialaln2", query = "SELECT o FROM OsobaPit o WHERE o.ospDzialaln2 = :ospDzialaln2"),
    @NamedQuery(name = "OsobaPit.findByOspDzialaln3", query = "SELECT o FROM OsobaPit o WHERE o.ospDzialaln3 = :ospDzialaln3"),
    @NamedQuery(name = "OsobaPit.findByOspNajem1", query = "SELECT o FROM OsobaPit o WHERE o.ospNajem1 = :ospNajem1"),
    @NamedQuery(name = "OsobaPit.findByOspNajem2", query = "SELECT o FROM OsobaPit o WHERE o.ospNajem2 = :ospNajem2"),
    @NamedQuery(name = "OsobaPit.findByOspNajem3", query = "SELECT o FROM OsobaPit o WHERE o.ospNajem3 = :ospNajem3"),
    @NamedQuery(name = "OsobaPit.findByOspNajem4", query = "SELECT o FROM OsobaPit o WHERE o.ospNajem4 = :ospNajem4"),
    @NamedQuery(name = "OsobaPit.findByOspDzialaln1", query = "SELECT o FROM OsobaPit o WHERE o.ospDzialaln1 = :ospDzialaln1"),
    @NamedQuery(name = "OsobaPit.findByOspDod1", query = "SELECT o FROM OsobaPit o WHERE o.ospDod1 = :ospDod1"),
    @NamedQuery(name = "OsobaPit.findByOspDod2", query = "SELECT o FROM OsobaPit o WHERE o.ospDod2 = :ospDod2"),
    @NamedQuery(name = "OsobaPit.findByOspDod3", query = "SELECT o FROM OsobaPit o WHERE o.ospDod3 = :ospDod3"),
    @NamedQuery(name = "OsobaPit.findByOspDec1Nr", query = "SELECT o FROM OsobaPit o WHERE o.ospDec1Nr = :ospDec1Nr"),
    @NamedQuery(name = "OsobaPit.findByOspDec1Data", query = "SELECT o FROM OsobaPit o WHERE o.ospDec1Data = :ospDec1Data"),
    @NamedQuery(name = "OsobaPit.findByOspDec2Nr", query = "SELECT o FROM OsobaPit o WHERE o.ospDec2Nr = :ospDec2Nr"),
    @NamedQuery(name = "OsobaPit.findByOspDec2Data", query = "SELECT o FROM OsobaPit o WHERE o.ospDec2Data = :ospDec2Data"),
    @NamedQuery(name = "OsobaPit.findByOspDzialaln4", query = "SELECT o FROM OsobaPit o WHERE o.ospDzialaln4 = :ospDzialaln4"),
    @NamedQuery(name = "OsobaPit.findByOspD4Nip", query = "SELECT o FROM OsobaPit o WHERE o.ospD4Nip = :ospD4Nip"),
    @NamedQuery(name = "OsobaPit.findByOspD4Regon", query = "SELECT o FROM OsobaPit o WHERE o.ospD4Regon = :ospD4Regon"),
    @NamedQuery(name = "OsobaPit.findByOspD4Nazwa", query = "SELECT o FROM OsobaPit o WHERE o.ospD4Nazwa = :ospD4Nazwa"),
    @NamedQuery(name = "OsobaPit.findByOspD4Miejsce", query = "SELECT o FROM OsobaPit o WHERE o.ospD4Miejsce = :ospD4Miejsce"),
    @NamedQuery(name = "OsobaPit.findByOspDataOd", query = "SELECT o FROM OsobaPit o WHERE o.ospDataOd = :ospDataOd"),
    @NamedQuery(name = "OsobaPit.findByOspDataDo", query = "SELECT o FROM OsobaPit o WHERE o.ospDataDo = :ospDataDo"),
    @NamedQuery(name = "OsobaPit.findByOspDate1", query = "SELECT o FROM OsobaPit o WHERE o.ospDate1 = :ospDate1"),
    @NamedQuery(name = "OsobaPit.findByOspDate2", query = "SELECT o FROM OsobaPit o WHERE o.ospDate2 = :ospDate2"),
    @NamedQuery(name = "OsobaPit.findByOspVchar1", query = "SELECT o FROM OsobaPit o WHERE o.ospVchar1 = :ospVchar1"),
    @NamedQuery(name = "OsobaPit.findByOspVchar2", query = "SELECT o FROM OsobaPit o WHERE o.ospVchar2 = :ospVchar2"),
    @NamedQuery(name = "OsobaPit.findByOspNum1", query = "SELECT o FROM OsobaPit o WHERE o.ospNum1 = :ospNum1"),
    @NamedQuery(name = "OsobaPit.findByOspNum2", query = "SELECT o FROM OsobaPit o WHERE o.ospNum2 = :ospNum2"),
    @NamedQuery(name = "OsobaPit.findByOspNum3", query = "SELECT o FROM OsobaPit o WHERE o.ospNum3 = :ospNum3"),
    @NamedQuery(name = "OsobaPit.findByOspNum4", query = "SELECT o FROM OsobaPit o WHERE o.ospNum4 = :ospNum4"),
    @NamedQuery(name = "OsobaPit.findByOspNum5", query = "SELECT o FROM OsobaPit o WHERE o.ospNum5 = :ospNum5"),
    @NamedQuery(name = "OsobaPit.findByOspNum6", query = "SELECT o FROM OsobaPit o WHERE o.ospNum6 = :ospNum6"),
    @NamedQuery(name = "OsobaPit.findByOspNum7", query = "SELECT o FROM OsobaPit o WHERE o.ospNum7 = :ospNum7"),
    @NamedQuery(name = "OsobaPit.findByOspNum8", query = "SELECT o FROM OsobaPit o WHERE o.ospNum8 = :ospNum8")})
public class OsobaPit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "osp_serial", nullable = false)
    private Integer ospSerial;
    @Column(name = "osp_tytul")
    private Character ospTytul;
    @Size(max = 64)
    @Column(name = "osp_k_49", length = 64)
    private String ospK49;
    @Size(max = 64)
    @Column(name = "osp_k_56", length = 64)
    private String ospK56;
    @Size(max = 64)
    @Column(name = "osp_k_61", length = 64)
    private String ospK61;
    @Size(max = 64)
    @Column(name = "osp_k_69", length = 64)
    private String ospK69;
    @Size(max = 64)
    @Column(name = "osp_k_79", length = 64)
    private String ospK79;
    @Size(max = 10)
    @Column(name = "osp_k_111", length = 10)
    private String ospK111;
    @Size(max = 14)
    @Column(name = "osp_k_112", length = 14)
    private String ospK112;
    @Size(max = 64)
    @Column(name = "osp_k_113", length = 64)
    private String ospK113;
    @Size(max = 64)
    @Column(name = "osp_k_114", length = 64)
    private String ospK114;
    @Size(max = 10)
    @Column(name = "osp_k_120", length = 10)
    private String ospK120;
    @Size(max = 14)
    @Column(name = "osp_k_121", length = 14)
    private String ospK121;
    @Size(max = 64)
    @Column(name = "osp_k_122", length = 64)
    private String ospK122;
    @Size(max = 64)
    @Column(name = "osp_k_123", length = 64)
    private String ospK123;
    @Size(max = 64)
    @Column(name = "osp_k_129", length = 64)
    private String ospK129;
    @Size(max = 64)
    @Column(name = "osp_k_130", length = 64)
    private String ospK130;
    @Size(max = 64)
    @Column(name = "osp_k_136", length = 64)
    private String ospK136;
    @Size(max = 64)
    @Column(name = "osp_k_137", length = 64)
    private String ospK137;
    @Size(max = 64)
    @Column(name = "osp_k_143", length = 64)
    private String ospK143;
    @Size(max = 64)
    @Column(name = "osp_k_144", length = 64)
    private String ospK144;
    @Size(max = 64)
    @Column(name = "osp_k_150", length = 64)
    private String ospK150;
    @Size(max = 64)
    @Column(name = "osp_k_151", length = 64)
    private String ospK151;
    @Size(max = 64)
    @Column(name = "osp_k_157", length = 64)
    private String ospK157;
    @Size(max = 10)
    @Column(name = "osp_k_158", length = 10)
    private String ospK158;
    @Size(max = 14)
    @Column(name = "osp_k_159", length = 14)
    private String ospK159;
    @Size(max = 64)
    @Column(name = "osp_k_160", length = 64)
    private String ospK160;
    @Size(max = 32)
    @Column(name = "osp_k_161", length = 32)
    private String ospK161;
    @Column(name = "osp_k_162")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospK162;
    @Column(name = "osp_k_163")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospK163;
    @Column(name = "osp_k_164")
    private Character ospK164;
    @Size(max = 64)
    @Column(name = "osp_k_172", length = 64)
    private String ospK172;
    @Size(max = 10)
    @Column(name = "osp_k_173", length = 10)
    private String ospK173;
    @Size(max = 14)
    @Column(name = "osp_k_174", length = 14)
    private String ospK174;
    @Size(max = 64)
    @Column(name = "osp_k_175", length = 64)
    private String ospK175;
    @Size(max = 32)
    @Column(name = "osp_k_176", length = 32)
    private String ospK176;
    @Column(name = "osp_k_177")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospK177;
    @Column(name = "osp_k_178")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospK178;
    @Column(name = "osp_k_179")
    private Character ospK179;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "osp_k_106", precision = 7, scale = 4)
    private BigDecimal ospK106;
    @Column(name = "osp_k_115", precision = 7, scale = 4)
    private BigDecimal ospK115;
    @Column(name = "osp_k_124", precision = 7, scale = 4)
    private BigDecimal ospK124;
    @Column(name = "osp_k_131", precision = 7, scale = 4)
    private BigDecimal ospK131;
    @Column(name = "osp_k_138", precision = 7, scale = 4)
    private BigDecimal ospK138;
    @Column(name = "osp_k_145", precision = 7, scale = 4)
    private BigDecimal ospK145;
    @Column(name = "osp_k_152", precision = 7, scale = 4)
    private BigDecimal ospK152;
    @Column(name = "osp_dzialaln_2")
    private Character ospDzialaln2;
    @Column(name = "osp_dzialaln_3")
    private Character ospDzialaln3;
    @Column(name = "osp_najem_1")
    private Character ospNajem1;
    @Column(name = "osp_najem_2")
    private Character ospNajem2;
    @Column(name = "osp_najem_3")
    private Character ospNajem3;
    @Column(name = "osp_najem_4")
    private Character ospNajem4;
    @Column(name = "osp_dzialaln_1")
    private Character ospDzialaln1;
    @Column(name = "osp_dod_1")
    private Character ospDod1;
    @Column(name = "osp_dod_2")
    private Character ospDod2;
    @Column(name = "osp_dod_3")
    private Character ospDod3;
    @Size(max = 32)
    @Column(name = "osp_dec1_nr", length = 32)
    private String ospDec1Nr;
    @Column(name = "osp_dec1_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospDec1Data;
    @Size(max = 32)
    @Column(name = "osp_dec2_nr", length = 32)
    private String ospDec2Nr;
    @Column(name = "osp_dec2_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospDec2Data;
    @Column(name = "osp_dzialaln_4")
    private Character ospDzialaln4;
    @Size(max = 10)
    @Column(name = "osp_d4_nip", length = 10)
    private String ospD4Nip;
    @Size(max = 14)
    @Column(name = "osp_d4_regon", length = 14)
    private String ospD4Regon;
    @Size(max = 64)
    @Column(name = "osp_d4_nazwa", length = 64)
    private String ospD4Nazwa;
    @Size(max = 64)
    @Column(name = "osp_d4_miejsce", length = 64)
    private String ospD4Miejsce;
    @Column(name = "osp_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospDataOd;
    @Column(name = "osp_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospDataDo;
    @Column(name = "osp_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospDate1;
    @Column(name = "osp_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ospDate2;
    @Size(max = 64)
    @Column(name = "osp_vchar_1", length = 64)
    private String ospVchar1;
    @Size(max = 64)
    @Column(name = "osp_vchar_2", length = 64)
    private String ospVchar2;
    @Column(name = "osp_num_1", precision = 13, scale = 2)
    private BigDecimal ospNum1;
    @Column(name = "osp_num_2", precision = 13, scale = 2)
    private BigDecimal ospNum2;
    @Column(name = "osp_num_3", precision = 13, scale = 2)
    private BigDecimal ospNum3;
    @Column(name = "osp_num_4", precision = 13, scale = 2)
    private BigDecimal ospNum4;
    @Column(name = "osp_num_5", precision = 13, scale = 2)
    private BigDecimal ospNum5;
    @Column(name = "osp_num_6", precision = 13, scale = 2)
    private BigDecimal ospNum6;
    @Column(name = "osp_num_7", precision = 17, scale = 6)
    private BigDecimal ospNum7;
    @Column(name = "osp_num_8", precision = 17, scale = 6)
    private BigDecimal ospNum8;
    @JoinColumn(name = "osp_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba ospOsoSerial;
    @OneToMany(mappedBy = "dssOspSerialPit")
    private List<DaneStatS> daneStatSList;

    public OsobaPit() {
    }

    public OsobaPit(Integer ospSerial) {
        this.ospSerial = ospSerial;
    }

    public Integer getOspSerial() {
        return ospSerial;
    }

    public void setOspSerial(Integer ospSerial) {
        this.ospSerial = ospSerial;
    }

    public Character getOspTytul() {
        return ospTytul;
    }

    public void setOspTytul(Character ospTytul) {
        this.ospTytul = ospTytul;
    }

    public String getOspK49() {
        return ospK49;
    }

    public void setOspK49(String ospK49) {
        this.ospK49 = ospK49;
    }

    public String getOspK56() {
        return ospK56;
    }

    public void setOspK56(String ospK56) {
        this.ospK56 = ospK56;
    }

    public String getOspK61() {
        return ospK61;
    }

    public void setOspK61(String ospK61) {
        this.ospK61 = ospK61;
    }

    public String getOspK69() {
        return ospK69;
    }

    public void setOspK69(String ospK69) {
        this.ospK69 = ospK69;
    }

    public String getOspK79() {
        return ospK79;
    }

    public void setOspK79(String ospK79) {
        this.ospK79 = ospK79;
    }

    public String getOspK111() {
        return ospK111;
    }

    public void setOspK111(String ospK111) {
        this.ospK111 = ospK111;
    }

    public String getOspK112() {
        return ospK112;
    }

    public void setOspK112(String ospK112) {
        this.ospK112 = ospK112;
    }

    public String getOspK113() {
        return ospK113;
    }

    public void setOspK113(String ospK113) {
        this.ospK113 = ospK113;
    }

    public String getOspK114() {
        return ospK114;
    }

    public void setOspK114(String ospK114) {
        this.ospK114 = ospK114;
    }

    public String getOspK120() {
        return ospK120;
    }

    public void setOspK120(String ospK120) {
        this.ospK120 = ospK120;
    }

    public String getOspK121() {
        return ospK121;
    }

    public void setOspK121(String ospK121) {
        this.ospK121 = ospK121;
    }

    public String getOspK122() {
        return ospK122;
    }

    public void setOspK122(String ospK122) {
        this.ospK122 = ospK122;
    }

    public String getOspK123() {
        return ospK123;
    }

    public void setOspK123(String ospK123) {
        this.ospK123 = ospK123;
    }

    public String getOspK129() {
        return ospK129;
    }

    public void setOspK129(String ospK129) {
        this.ospK129 = ospK129;
    }

    public String getOspK130() {
        return ospK130;
    }

    public void setOspK130(String ospK130) {
        this.ospK130 = ospK130;
    }

    public String getOspK136() {
        return ospK136;
    }

    public void setOspK136(String ospK136) {
        this.ospK136 = ospK136;
    }

    public String getOspK137() {
        return ospK137;
    }

    public void setOspK137(String ospK137) {
        this.ospK137 = ospK137;
    }

    public String getOspK143() {
        return ospK143;
    }

    public void setOspK143(String ospK143) {
        this.ospK143 = ospK143;
    }

    public String getOspK144() {
        return ospK144;
    }

    public void setOspK144(String ospK144) {
        this.ospK144 = ospK144;
    }

    public String getOspK150() {
        return ospK150;
    }

    public void setOspK150(String ospK150) {
        this.ospK150 = ospK150;
    }

    public String getOspK151() {
        return ospK151;
    }

    public void setOspK151(String ospK151) {
        this.ospK151 = ospK151;
    }

    public String getOspK157() {
        return ospK157;
    }

    public void setOspK157(String ospK157) {
        this.ospK157 = ospK157;
    }

    public String getOspK158() {
        return ospK158;
    }

    public void setOspK158(String ospK158) {
        this.ospK158 = ospK158;
    }

    public String getOspK159() {
        return ospK159;
    }

    public void setOspK159(String ospK159) {
        this.ospK159 = ospK159;
    }

    public String getOspK160() {
        return ospK160;
    }

    public void setOspK160(String ospK160) {
        this.ospK160 = ospK160;
    }

    public String getOspK161() {
        return ospK161;
    }

    public void setOspK161(String ospK161) {
        this.ospK161 = ospK161;
    }

    public Date getOspK162() {
        return ospK162;
    }

    public void setOspK162(Date ospK162) {
        this.ospK162 = ospK162;
    }

    public Date getOspK163() {
        return ospK163;
    }

    public void setOspK163(Date ospK163) {
        this.ospK163 = ospK163;
    }

    public Character getOspK164() {
        return ospK164;
    }

    public void setOspK164(Character ospK164) {
        this.ospK164 = ospK164;
    }

    public String getOspK172() {
        return ospK172;
    }

    public void setOspK172(String ospK172) {
        this.ospK172 = ospK172;
    }

    public String getOspK173() {
        return ospK173;
    }

    public void setOspK173(String ospK173) {
        this.ospK173 = ospK173;
    }

    public String getOspK174() {
        return ospK174;
    }

    public void setOspK174(String ospK174) {
        this.ospK174 = ospK174;
    }

    public String getOspK175() {
        return ospK175;
    }

    public void setOspK175(String ospK175) {
        this.ospK175 = ospK175;
    }

    public String getOspK176() {
        return ospK176;
    }

    public void setOspK176(String ospK176) {
        this.ospK176 = ospK176;
    }

    public Date getOspK177() {
        return ospK177;
    }

    public void setOspK177(Date ospK177) {
        this.ospK177 = ospK177;
    }

    public Date getOspK178() {
        return ospK178;
    }

    public void setOspK178(Date ospK178) {
        this.ospK178 = ospK178;
    }

    public Character getOspK179() {
        return ospK179;
    }

    public void setOspK179(Character ospK179) {
        this.ospK179 = ospK179;
    }

    public BigDecimal getOspK106() {
        return ospK106;
    }

    public void setOspK106(BigDecimal ospK106) {
        this.ospK106 = ospK106;
    }

    public BigDecimal getOspK115() {
        return ospK115;
    }

    public void setOspK115(BigDecimal ospK115) {
        this.ospK115 = ospK115;
    }

    public BigDecimal getOspK124() {
        return ospK124;
    }

    public void setOspK124(BigDecimal ospK124) {
        this.ospK124 = ospK124;
    }

    public BigDecimal getOspK131() {
        return ospK131;
    }

    public void setOspK131(BigDecimal ospK131) {
        this.ospK131 = ospK131;
    }

    public BigDecimal getOspK138() {
        return ospK138;
    }

    public void setOspK138(BigDecimal ospK138) {
        this.ospK138 = ospK138;
    }

    public BigDecimal getOspK145() {
        return ospK145;
    }

    public void setOspK145(BigDecimal ospK145) {
        this.ospK145 = ospK145;
    }

    public BigDecimal getOspK152() {
        return ospK152;
    }

    public void setOspK152(BigDecimal ospK152) {
        this.ospK152 = ospK152;
    }

    public Character getOspDzialaln2() {
        return ospDzialaln2;
    }

    public void setOspDzialaln2(Character ospDzialaln2) {
        this.ospDzialaln2 = ospDzialaln2;
    }

    public Character getOspDzialaln3() {
        return ospDzialaln3;
    }

    public void setOspDzialaln3(Character ospDzialaln3) {
        this.ospDzialaln3 = ospDzialaln3;
    }

    public Character getOspNajem1() {
        return ospNajem1;
    }

    public void setOspNajem1(Character ospNajem1) {
        this.ospNajem1 = ospNajem1;
    }

    public Character getOspNajem2() {
        return ospNajem2;
    }

    public void setOspNajem2(Character ospNajem2) {
        this.ospNajem2 = ospNajem2;
    }

    public Character getOspNajem3() {
        return ospNajem3;
    }

    public void setOspNajem3(Character ospNajem3) {
        this.ospNajem3 = ospNajem3;
    }

    public Character getOspNajem4() {
        return ospNajem4;
    }

    public void setOspNajem4(Character ospNajem4) {
        this.ospNajem4 = ospNajem4;
    }

    public Character getOspDzialaln1() {
        return ospDzialaln1;
    }

    public void setOspDzialaln1(Character ospDzialaln1) {
        this.ospDzialaln1 = ospDzialaln1;
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

    public String getOspDec1Nr() {
        return ospDec1Nr;
    }

    public void setOspDec1Nr(String ospDec1Nr) {
        this.ospDec1Nr = ospDec1Nr;
    }

    public Date getOspDec1Data() {
        return ospDec1Data;
    }

    public void setOspDec1Data(Date ospDec1Data) {
        this.ospDec1Data = ospDec1Data;
    }

    public String getOspDec2Nr() {
        return ospDec2Nr;
    }

    public void setOspDec2Nr(String ospDec2Nr) {
        this.ospDec2Nr = ospDec2Nr;
    }

    public Date getOspDec2Data() {
        return ospDec2Data;
    }

    public void setOspDec2Data(Date ospDec2Data) {
        this.ospDec2Data = ospDec2Data;
    }

    public Character getOspDzialaln4() {
        return ospDzialaln4;
    }

    public void setOspDzialaln4(Character ospDzialaln4) {
        this.ospDzialaln4 = ospDzialaln4;
    }

    public String getOspD4Nip() {
        return ospD4Nip;
    }

    public void setOspD4Nip(String ospD4Nip) {
        this.ospD4Nip = ospD4Nip;
    }

    public String getOspD4Regon() {
        return ospD4Regon;
    }

    public void setOspD4Regon(String ospD4Regon) {
        this.ospD4Regon = ospD4Regon;
    }

    public String getOspD4Nazwa() {
        return ospD4Nazwa;
    }

    public void setOspD4Nazwa(String ospD4Nazwa) {
        this.ospD4Nazwa = ospD4Nazwa;
    }

    public String getOspD4Miejsce() {
        return ospD4Miejsce;
    }

    public void setOspD4Miejsce(String ospD4Miejsce) {
        this.ospD4Miejsce = ospD4Miejsce;
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

    public Date getOspDate1() {
        return ospDate1;
    }

    public void setOspDate1(Date ospDate1) {
        this.ospDate1 = ospDate1;
    }

    public Date getOspDate2() {
        return ospDate2;
    }

    public void setOspDate2(Date ospDate2) {
        this.ospDate2 = ospDate2;
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

    public BigDecimal getOspNum5() {
        return ospNum5;
    }

    public void setOspNum5(BigDecimal ospNum5) {
        this.ospNum5 = ospNum5;
    }

    public BigDecimal getOspNum6() {
        return ospNum6;
    }

    public void setOspNum6(BigDecimal ospNum6) {
        this.ospNum6 = ospNum6;
    }

    public BigDecimal getOspNum7() {
        return ospNum7;
    }

    public void setOspNum7(BigDecimal ospNum7) {
        this.ospNum7 = ospNum7;
    }

    public BigDecimal getOspNum8() {
        return ospNum8;
    }

    public void setOspNum8(BigDecimal ospNum8) {
        this.ospNum8 = ospNum8;
    }

    public Osoba getOspOsoSerial() {
        return ospOsoSerial;
    }

    public void setOspOsoSerial(Osoba ospOsoSerial) {
        this.ospOsoSerial = ospOsoSerial;
    }

    @XmlTransient
    public List<DaneStatS> getDaneStatSList() {
        return daneStatSList;
    }

    public void setDaneStatSList(List<DaneStatS> daneStatSList) {
        this.daneStatSList = daneStatSList;
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
        if (!(object instanceof OsobaPit)) {
            return false;
        }
        OsobaPit other = (OsobaPit) object;
        if ((this.ospSerial == null && other.ospSerial != null) || (this.ospSerial != null && !this.ospSerial.equals(other.ospSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.OsobaPit[ ospSerial=" + ospSerial + " ]";
    }
    
}
