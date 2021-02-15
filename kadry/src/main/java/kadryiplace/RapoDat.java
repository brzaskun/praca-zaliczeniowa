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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "rapo_dat", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RapoDat.findAll", query = "SELECT r FROM RapoDat r"),
    @NamedQuery(name = "RapoDat.findByRptSerial", query = "SELECT r FROM RapoDat r WHERE r.rptSerial = :rptSerial"),
    @NamedQuery(name = "RapoDat.findByRptNazwa", query = "SELECT r FROM RapoDat r WHERE r.rptNazwa = :rptNazwa"),
    @NamedQuery(name = "RapoDat.findByRptMicrohelp", query = "SELECT r FROM RapoDat r WHERE r.rptMicrohelp = :rptMicrohelp"),
    @NamedQuery(name = "RapoDat.findByRptAktywnyRapo", query = "SELECT r FROM RapoDat r WHERE r.rptAktywnyRapo = :rptAktywnyRapo"),
    @NamedQuery(name = "RapoDat.findByRptAktywnyKart", query = "SELECT r FROM RapoDat r WHERE r.rptAktywnyKart = :rptAktywnyKart"),
    @NamedQuery(name = "RapoDat.findByRptKryteria", query = "SELECT r FROM RapoDat r WHERE r.rptKryteria = :rptKryteria"),
    @NamedQuery(name = "RapoDat.findByRptKartoteka", query = "SELECT r FROM RapoDat r WHERE r.rptKartoteka = :rptKartoteka"),
    @NamedQuery(name = "RapoDat.findByRptGenerator", query = "SELECT r FROM RapoDat r WHERE r.rptGenerator = :rptGenerator"),
    @NamedQuery(name = "RapoDat.findByRptRodzaj", query = "SELECT r FROM RapoDat r WHERE r.rptRodzaj = :rptRodzaj"),
    @NamedQuery(name = "RapoDat.findByRptTyp", query = "SELECT r FROM RapoDat r WHERE r.rptTyp = :rptTyp"),
    @NamedQuery(name = "RapoDat.findByRptUtwoUser", query = "SELECT r FROM RapoDat r WHERE r.rptUtwoUser = :rptUtwoUser"),
    @NamedQuery(name = "RapoDat.findByRptUtwoData", query = "SELECT r FROM RapoDat r WHERE r.rptUtwoData = :rptUtwoData"),
    @NamedQuery(name = "RapoDat.findByRptModyUser", query = "SELECT r FROM RapoDat r WHERE r.rptModyUser = :rptModyUser"),
    @NamedQuery(name = "RapoDat.findByRptModyData", query = "SELECT r FROM RapoDat r WHERE r.rptModyData = :rptModyData"),
    @NamedQuery(name = "RapoDat.findByRptChar1", query = "SELECT r FROM RapoDat r WHERE r.rptChar1 = :rptChar1"),
    @NamedQuery(name = "RapoDat.findByRptChar2", query = "SELECT r FROM RapoDat r WHERE r.rptChar2 = :rptChar2"),
    @NamedQuery(name = "RapoDat.findByRptChar3", query = "SELECT r FROM RapoDat r WHERE r.rptChar3 = :rptChar3"),
    @NamedQuery(name = "RapoDat.findByRptChar4", query = "SELECT r FROM RapoDat r WHERE r.rptChar4 = :rptChar4"),
    @NamedQuery(name = "RapoDat.findByRptChar5", query = "SELECT r FROM RapoDat r WHERE r.rptChar5 = :rptChar5"),
    @NamedQuery(name = "RapoDat.findByRptChar6", query = "SELECT r FROM RapoDat r WHERE r.rptChar6 = :rptChar6"),
    @NamedQuery(name = "RapoDat.findByRptChar7", query = "SELECT r FROM RapoDat r WHERE r.rptChar7 = :rptChar7"),
    @NamedQuery(name = "RapoDat.findByRptChar8", query = "SELECT r FROM RapoDat r WHERE r.rptChar8 = :rptChar8"),
    @NamedQuery(name = "RapoDat.findByRptChar9", query = "SELECT r FROM RapoDat r WHERE r.rptChar9 = :rptChar9"),
    @NamedQuery(name = "RapoDat.findByRptChar10", query = "SELECT r FROM RapoDat r WHERE r.rptChar10 = :rptChar10"),
    @NamedQuery(name = "RapoDat.findByRptChar11", query = "SELECT r FROM RapoDat r WHERE r.rptChar11 = :rptChar11"),
    @NamedQuery(name = "RapoDat.findByRptChar12", query = "SELECT r FROM RapoDat r WHERE r.rptChar12 = :rptChar12"),
    @NamedQuery(name = "RapoDat.findByRptInt1", query = "SELECT r FROM RapoDat r WHERE r.rptInt1 = :rptInt1"),
    @NamedQuery(name = "RapoDat.findByRptInt2", query = "SELECT r FROM RapoDat r WHERE r.rptInt2 = :rptInt2"),
    @NamedQuery(name = "RapoDat.findByRptInt3", query = "SELECT r FROM RapoDat r WHERE r.rptInt3 = :rptInt3"),
    @NamedQuery(name = "RapoDat.findByRptInt4", query = "SELECT r FROM RapoDat r WHERE r.rptInt4 = :rptInt4"),
    @NamedQuery(name = "RapoDat.findByRptNum1", query = "SELECT r FROM RapoDat r WHERE r.rptNum1 = :rptNum1"),
    @NamedQuery(name = "RapoDat.findByRptNum2", query = "SELECT r FROM RapoDat r WHERE r.rptNum2 = :rptNum2"),
    @NamedQuery(name = "RapoDat.findByRptNum3", query = "SELECT r FROM RapoDat r WHERE r.rptNum3 = :rptNum3"),
    @NamedQuery(name = "RapoDat.findByRptNum4", query = "SELECT r FROM RapoDat r WHERE r.rptNum4 = :rptNum4"),
    @NamedQuery(name = "RapoDat.findByRptVchar1", query = "SELECT r FROM RapoDat r WHERE r.rptVchar1 = :rptVchar1"),
    @NamedQuery(name = "RapoDat.findByRptVchar2", query = "SELECT r FROM RapoDat r WHERE r.rptVchar2 = :rptVchar2"),
    @NamedQuery(name = "RapoDat.findByRptVchar3", query = "SELECT r FROM RapoDat r WHERE r.rptVchar3 = :rptVchar3"),
    @NamedQuery(name = "RapoDat.findByRptVchar4", query = "SELECT r FROM RapoDat r WHERE r.rptVchar4 = :rptVchar4"),
    @NamedQuery(name = "RapoDat.findByRptDate1", query = "SELECT r FROM RapoDat r WHERE r.rptDate1 = :rptDate1"),
    @NamedQuery(name = "RapoDat.findByRptDate2", query = "SELECT r FROM RapoDat r WHERE r.rptDate2 = :rptDate2"),
    @NamedQuery(name = "RapoDat.findByRptDate3", query = "SELECT r FROM RapoDat r WHERE r.rptDate3 = :rptDate3"),
    @NamedQuery(name = "RapoDat.findByRptDate4", query = "SELECT r FROM RapoDat r WHERE r.rptDate4 = :rptDate4")})
public class RapoDat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rpt_serial", nullable = false)
    private Integer rptSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "rpt_nazwa", nullable = false, length = 254)
    private String rptNazwa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "rpt_microhelp", nullable = false, length = 254)
    private String rptMicrohelp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rpt_aktywny_rapo", nullable = false)
    private Character rptAktywnyRapo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rpt_aktywny_kart", nullable = false)
    private Character rptAktywnyKart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rpt_kryteria", nullable = false)
    private Character rptKryteria;
    @Column(name = "rpt_kartoteka")
    private Character rptKartoteka;
    @Column(name = "rpt_generator")
    private Character rptGenerator;
    @Column(name = "rpt_rodzaj")
    private Character rptRodzaj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rpt_typ", nullable = false)
    private Character rptTyp;
    @Size(max = 32)
    @Column(name = "rpt_utwo_user", length = 32)
    private String rptUtwoUser;
    @Column(name = "rpt_utwo_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rptUtwoData;
    @Size(max = 32)
    @Column(name = "rpt_mody_user", length = 32)
    private String rptModyUser;
    @Column(name = "rpt_mody_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rptModyData;
    @Column(name = "rpt_char_1")
    private Character rptChar1;
    @Column(name = "rpt_char_2")
    private Character rptChar2;
    @Column(name = "rpt_char_3")
    private Character rptChar3;
    @Column(name = "rpt_char_4")
    private Character rptChar4;
    @Column(name = "rpt_char_5")
    private Character rptChar5;
    @Column(name = "rpt_char_6")
    private Character rptChar6;
    @Column(name = "rpt_char_7")
    private Character rptChar7;
    @Column(name = "rpt_char_8")
    private Character rptChar8;
    @Column(name = "rpt_char_9")
    private Character rptChar9;
    @Column(name = "rpt_char_10")
    private Character rptChar10;
    @Column(name = "rpt_char_11")
    private Character rptChar11;
    @Column(name = "rpt_char_12")
    private Character rptChar12;
    @Column(name = "rpt_int_1")
    private Integer rptInt1;
    @Column(name = "rpt_int_2")
    private Integer rptInt2;
    @Column(name = "rpt_int_3")
    private Integer rptInt3;
    @Column(name = "rpt_int_4")
    private Integer rptInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rpt_num_1", precision = 17, scale = 6)
    private BigDecimal rptNum1;
    @Column(name = "rpt_num_2", precision = 17, scale = 6)
    private BigDecimal rptNum2;
    @Column(name = "rpt_num_3", precision = 17, scale = 6)
    private BigDecimal rptNum3;
    @Column(name = "rpt_num_4", precision = 17, scale = 6)
    private BigDecimal rptNum4;
    @Size(max = 254)
    @Column(name = "rpt_vchar_1", length = 254)
    private String rptVchar1;
    @Size(max = 254)
    @Column(name = "rpt_vchar_2", length = 254)
    private String rptVchar2;
    @Size(max = 254)
    @Column(name = "rpt_vchar_3", length = 254)
    private String rptVchar3;
    @Size(max = 254)
    @Column(name = "rpt_vchar_4", length = 254)
    private String rptVchar4;
    @Column(name = "rpt_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rptDate1;
    @Column(name = "rpt_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rptDate2;
    @Column(name = "rpt_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rptDate3;
    @Column(name = "rpt_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rptDate4;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "rpt_lvchar_1", length = 2147483647)
    private String rptLvchar1;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "rpt_lvchar_2", length = 2147483647)
    private String rptLvchar2;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "rpt_lvchar_3", length = 2147483647)
    private String rptLvchar3;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "rpt_lvchar_4", length = 2147483647)
    private String rptLvchar4;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "rpt_lvchar_5", length = 2147483647)
    private String rptLvchar5;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "rpt_lvchar_6", length = 2147483647)
    private String rptLvchar6;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "rpt_lvchar_7", length = 2147483647)
    private String rptLvchar7;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "rpt_lvchar_8", length = 2147483647)
    private String rptLvchar8;
    @Lob
    @Column(name = "rpt_dw_1")
    private byte[] rptDw1;
    @Lob
    @Column(name = "rpt_dw_2")
    private byte[] rptDw2;
    @Lob
    @Column(name = "rpt_dw_3")
    private byte[] rptDw3;
    @Lob
    @Column(name = "rpt_dw_4")
    private byte[] rptDw4;
    @Lob
    @Column(name = "rpt_dw_5")
    private byte[] rptDw5;
    @Lob
    @Column(name = "rpt_dw_6")
    private byte[] rptDw6;
    @Lob
    @Column(name = "rpt_dw_7")
    private byte[] rptDw7;
    @Lob
    @Column(name = "rpt_dw_8")
    private byte[] rptDw8;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rpsRptSerial")
    private List<RapoSec> rapoSecList;

    public RapoDat() {
    }

    public RapoDat(Integer rptSerial) {
        this.rptSerial = rptSerial;
    }

    public RapoDat(Integer rptSerial, String rptNazwa, String rptMicrohelp, Character rptAktywnyRapo, Character rptAktywnyKart, Character rptKryteria, Character rptTyp) {
        this.rptSerial = rptSerial;
        this.rptNazwa = rptNazwa;
        this.rptMicrohelp = rptMicrohelp;
        this.rptAktywnyRapo = rptAktywnyRapo;
        this.rptAktywnyKart = rptAktywnyKart;
        this.rptKryteria = rptKryteria;
        this.rptTyp = rptTyp;
    }

    public Integer getRptSerial() {
        return rptSerial;
    }

    public void setRptSerial(Integer rptSerial) {
        this.rptSerial = rptSerial;
    }

    public String getRptNazwa() {
        return rptNazwa;
    }

    public void setRptNazwa(String rptNazwa) {
        this.rptNazwa = rptNazwa;
    }

    public String getRptMicrohelp() {
        return rptMicrohelp;
    }

    public void setRptMicrohelp(String rptMicrohelp) {
        this.rptMicrohelp = rptMicrohelp;
    }

    public Character getRptAktywnyRapo() {
        return rptAktywnyRapo;
    }

    public void setRptAktywnyRapo(Character rptAktywnyRapo) {
        this.rptAktywnyRapo = rptAktywnyRapo;
    }

    public Character getRptAktywnyKart() {
        return rptAktywnyKart;
    }

    public void setRptAktywnyKart(Character rptAktywnyKart) {
        this.rptAktywnyKart = rptAktywnyKart;
    }

    public Character getRptKryteria() {
        return rptKryteria;
    }

    public void setRptKryteria(Character rptKryteria) {
        this.rptKryteria = rptKryteria;
    }

    public Character getRptKartoteka() {
        return rptKartoteka;
    }

    public void setRptKartoteka(Character rptKartoteka) {
        this.rptKartoteka = rptKartoteka;
    }

    public Character getRptGenerator() {
        return rptGenerator;
    }

    public void setRptGenerator(Character rptGenerator) {
        this.rptGenerator = rptGenerator;
    }

    public Character getRptRodzaj() {
        return rptRodzaj;
    }

    public void setRptRodzaj(Character rptRodzaj) {
        this.rptRodzaj = rptRodzaj;
    }

    public Character getRptTyp() {
        return rptTyp;
    }

    public void setRptTyp(Character rptTyp) {
        this.rptTyp = rptTyp;
    }

    public String getRptUtwoUser() {
        return rptUtwoUser;
    }

    public void setRptUtwoUser(String rptUtwoUser) {
        this.rptUtwoUser = rptUtwoUser;
    }

    public Date getRptUtwoData() {
        return rptUtwoData;
    }

    public void setRptUtwoData(Date rptUtwoData) {
        this.rptUtwoData = rptUtwoData;
    }

    public String getRptModyUser() {
        return rptModyUser;
    }

    public void setRptModyUser(String rptModyUser) {
        this.rptModyUser = rptModyUser;
    }

    public Date getRptModyData() {
        return rptModyData;
    }

    public void setRptModyData(Date rptModyData) {
        this.rptModyData = rptModyData;
    }

    public Character getRptChar1() {
        return rptChar1;
    }

    public void setRptChar1(Character rptChar1) {
        this.rptChar1 = rptChar1;
    }

    public Character getRptChar2() {
        return rptChar2;
    }

    public void setRptChar2(Character rptChar2) {
        this.rptChar2 = rptChar2;
    }

    public Character getRptChar3() {
        return rptChar3;
    }

    public void setRptChar3(Character rptChar3) {
        this.rptChar3 = rptChar3;
    }

    public Character getRptChar4() {
        return rptChar4;
    }

    public void setRptChar4(Character rptChar4) {
        this.rptChar4 = rptChar4;
    }

    public Character getRptChar5() {
        return rptChar5;
    }

    public void setRptChar5(Character rptChar5) {
        this.rptChar5 = rptChar5;
    }

    public Character getRptChar6() {
        return rptChar6;
    }

    public void setRptChar6(Character rptChar6) {
        this.rptChar6 = rptChar6;
    }

    public Character getRptChar7() {
        return rptChar7;
    }

    public void setRptChar7(Character rptChar7) {
        this.rptChar7 = rptChar7;
    }

    public Character getRptChar8() {
        return rptChar8;
    }

    public void setRptChar8(Character rptChar8) {
        this.rptChar8 = rptChar8;
    }

    public Character getRptChar9() {
        return rptChar9;
    }

    public void setRptChar9(Character rptChar9) {
        this.rptChar9 = rptChar9;
    }

    public Character getRptChar10() {
        return rptChar10;
    }

    public void setRptChar10(Character rptChar10) {
        this.rptChar10 = rptChar10;
    }

    public Character getRptChar11() {
        return rptChar11;
    }

    public void setRptChar11(Character rptChar11) {
        this.rptChar11 = rptChar11;
    }

    public Character getRptChar12() {
        return rptChar12;
    }

    public void setRptChar12(Character rptChar12) {
        this.rptChar12 = rptChar12;
    }

    public Integer getRptInt1() {
        return rptInt1;
    }

    public void setRptInt1(Integer rptInt1) {
        this.rptInt1 = rptInt1;
    }

    public Integer getRptInt2() {
        return rptInt2;
    }

    public void setRptInt2(Integer rptInt2) {
        this.rptInt2 = rptInt2;
    }

    public Integer getRptInt3() {
        return rptInt3;
    }

    public void setRptInt3(Integer rptInt3) {
        this.rptInt3 = rptInt3;
    }

    public Integer getRptInt4() {
        return rptInt4;
    }

    public void setRptInt4(Integer rptInt4) {
        this.rptInt4 = rptInt4;
    }

    public BigDecimal getRptNum1() {
        return rptNum1;
    }

    public void setRptNum1(BigDecimal rptNum1) {
        this.rptNum1 = rptNum1;
    }

    public BigDecimal getRptNum2() {
        return rptNum2;
    }

    public void setRptNum2(BigDecimal rptNum2) {
        this.rptNum2 = rptNum2;
    }

    public BigDecimal getRptNum3() {
        return rptNum3;
    }

    public void setRptNum3(BigDecimal rptNum3) {
        this.rptNum3 = rptNum3;
    }

    public BigDecimal getRptNum4() {
        return rptNum4;
    }

    public void setRptNum4(BigDecimal rptNum4) {
        this.rptNum4 = rptNum4;
    }

    public String getRptVchar1() {
        return rptVchar1;
    }

    public void setRptVchar1(String rptVchar1) {
        this.rptVchar1 = rptVchar1;
    }

    public String getRptVchar2() {
        return rptVchar2;
    }

    public void setRptVchar2(String rptVchar2) {
        this.rptVchar2 = rptVchar2;
    }

    public String getRptVchar3() {
        return rptVchar3;
    }

    public void setRptVchar3(String rptVchar3) {
        this.rptVchar3 = rptVchar3;
    }

    public String getRptVchar4() {
        return rptVchar4;
    }

    public void setRptVchar4(String rptVchar4) {
        this.rptVchar4 = rptVchar4;
    }

    public Date getRptDate1() {
        return rptDate1;
    }

    public void setRptDate1(Date rptDate1) {
        this.rptDate1 = rptDate1;
    }

    public Date getRptDate2() {
        return rptDate2;
    }

    public void setRptDate2(Date rptDate2) {
        this.rptDate2 = rptDate2;
    }

    public Date getRptDate3() {
        return rptDate3;
    }

    public void setRptDate3(Date rptDate3) {
        this.rptDate3 = rptDate3;
    }

    public Date getRptDate4() {
        return rptDate4;
    }

    public void setRptDate4(Date rptDate4) {
        this.rptDate4 = rptDate4;
    }

    public String getRptLvchar1() {
        return rptLvchar1;
    }

    public void setRptLvchar1(String rptLvchar1) {
        this.rptLvchar1 = rptLvchar1;
    }

    public String getRptLvchar2() {
        return rptLvchar2;
    }

    public void setRptLvchar2(String rptLvchar2) {
        this.rptLvchar2 = rptLvchar2;
    }

    public String getRptLvchar3() {
        return rptLvchar3;
    }

    public void setRptLvchar3(String rptLvchar3) {
        this.rptLvchar3 = rptLvchar3;
    }

    public String getRptLvchar4() {
        return rptLvchar4;
    }

    public void setRptLvchar4(String rptLvchar4) {
        this.rptLvchar4 = rptLvchar4;
    }

    public String getRptLvchar5() {
        return rptLvchar5;
    }

    public void setRptLvchar5(String rptLvchar5) {
        this.rptLvchar5 = rptLvchar5;
    }

    public String getRptLvchar6() {
        return rptLvchar6;
    }

    public void setRptLvchar6(String rptLvchar6) {
        this.rptLvchar6 = rptLvchar6;
    }

    public String getRptLvchar7() {
        return rptLvchar7;
    }

    public void setRptLvchar7(String rptLvchar7) {
        this.rptLvchar7 = rptLvchar7;
    }

    public String getRptLvchar8() {
        return rptLvchar8;
    }

    public void setRptLvchar8(String rptLvchar8) {
        this.rptLvchar8 = rptLvchar8;
    }

    public byte[] getRptDw1() {
        return rptDw1;
    }

    public void setRptDw1(byte[] rptDw1) {
        this.rptDw1 = rptDw1;
    }

    public byte[] getRptDw2() {
        return rptDw2;
    }

    public void setRptDw2(byte[] rptDw2) {
        this.rptDw2 = rptDw2;
    }

    public byte[] getRptDw3() {
        return rptDw3;
    }

    public void setRptDw3(byte[] rptDw3) {
        this.rptDw3 = rptDw3;
    }

    public byte[] getRptDw4() {
        return rptDw4;
    }

    public void setRptDw4(byte[] rptDw4) {
        this.rptDw4 = rptDw4;
    }

    public byte[] getRptDw5() {
        return rptDw5;
    }

    public void setRptDw5(byte[] rptDw5) {
        this.rptDw5 = rptDw5;
    }

    public byte[] getRptDw6() {
        return rptDw6;
    }

    public void setRptDw6(byte[] rptDw6) {
        this.rptDw6 = rptDw6;
    }

    public byte[] getRptDw7() {
        return rptDw7;
    }

    public void setRptDw7(byte[] rptDw7) {
        this.rptDw7 = rptDw7;
    }

    public byte[] getRptDw8() {
        return rptDw8;
    }

    public void setRptDw8(byte[] rptDw8) {
        this.rptDw8 = rptDw8;
    }

    @XmlTransient
    public List<RapoSec> getRapoSecList() {
        return rapoSecList;
    }

    public void setRapoSecList(List<RapoSec> rapoSecList) {
        this.rapoSecList = rapoSecList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rptSerial != null ? rptSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RapoDat)) {
            return false;
        }
        RapoDat other = (RapoDat) object;
        if ((this.rptSerial == null && other.rptSerial != null) || (this.rptSerial != null && !this.rptSerial.equals(other.rptSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.RapoDat[ rptSerial=" + rptSerial + " ]";
    }
    
}
