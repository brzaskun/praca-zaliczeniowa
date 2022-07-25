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
@Table(name = "wyn_kod_prz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WynKodPrz.findAll", query = "SELECT w FROM WynKodPrz w"),
    @NamedQuery(name = "WynKodPrz.findByWkpSerial", query = "SELECT w FROM WynKodPrz w WHERE w.wkpSerial = :wkpSerial"),
    @NamedQuery(name = "WynKodPrz.findByWkpKod", query = "SELECT w FROM WynKodPrz w WHERE w.wkpKod = :wkpKod"),
    @NamedQuery(name = "WynKodPrz.findByWkpOpis", query = "SELECT w FROM WynKodPrz w WHERE w.wkpOpis = :wkpOpis"),
    @NamedQuery(name = "WynKodPrz.findByWkpOpisSkr", query = "SELECT w FROM WynKodPrz w WHERE w.wkpOpisSkr = :wkpOpisSkr"),
    @NamedQuery(name = "WynKodPrz.findByWkpPodDoch", query = "SELECT w FROM WynKodPrz w WHERE w.wkpPodDoch = :wkpPodDoch"),
    @NamedQuery(name = "WynKodPrz.findByWkpZus", query = "SELECT w FROM WynKodPrz w WHERE w.wkpZus = :wkpZus"),
    @NamedQuery(name = "WynKodPrz.findByWkpZdrowotne", query = "SELECT w FROM WynKodPrz w WHERE w.wkpZdrowotne = :wkpZdrowotne"),
    @NamedQuery(name = "WynKodPrz.findByWkpChorWyp", query = "SELECT w FROM WynKodPrz w WHERE w.wkpChorWyp = :wkpChorWyp"),
    @NamedQuery(name = "WynKodPrz.findByWkpZrodloFin", query = "SELECT w FROM WynKodPrz w WHERE w.wkpZrodloFin = :wkpZrodloFin"),
    @NamedQuery(name = "WynKodPrz.findByWkpWyp", query = "SELECT w FROM WynKodPrz w WHERE w.wkpWyp = :wkpWyp"),
    @NamedQuery(name = "WynKodPrz.findByWkpPrzerwa", query = "SELECT w FROM WynKodPrz w WHERE w.wkpPrzerwa = :wkpPrzerwa"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod1", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod1 = :wkpDod1"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod2", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod2 = :wkpDod2"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod3", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod3 = :wkpDod3"),
    @NamedQuery(name = "WynKodPrz.findByWkpSystem", query = "SELECT w FROM WynKodPrz w WHERE w.wkpSystem = :wkpSystem"),
    @NamedQuery(name = "WynKodPrz.findByWkpAbsencja", query = "SELECT w FROM WynKodPrz w WHERE w.wkpAbsencja = :wkpAbsencja"),
    @NamedQuery(name = "WynKodPrz.findByWkpPdstZasChor", query = "SELECT w FROM WynKodPrz w WHERE w.wkpPdstZasChor = :wkpPdstZasChor"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod4", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod4 = :wkpDod4"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod5", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod5 = :wkpDod5"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod6", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod6 = :wkpDod6"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod7", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod7 = :wkpDod7"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod8", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod8 = :wkpDod8"),
    @NamedQuery(name = "WynKodPrz.findByWkpNum1", query = "SELECT w FROM WynKodPrz w WHERE w.wkpNum1 = :wkpNum1"),
    @NamedQuery(name = "WynKodPrz.findByWkpNum2", query = "SELECT w FROM WynKodPrz w WHERE w.wkpNum2 = :wkpNum2"),
    @NamedQuery(name = "WynKodPrz.findByWkpDate1", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDate1 = :wkpDate1"),
    @NamedQuery(name = "WynKodPrz.findByWkpDate2", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDate2 = :wkpDate2"),
    @NamedQuery(name = "WynKodPrz.findByWkpVchar1", query = "SELECT w FROM WynKodPrz w WHERE w.wkpVchar1 = :wkpVchar1"),
    @NamedQuery(name = "WynKodPrz.findByWkpVchar2", query = "SELECT w FROM WynKodPrz w WHERE w.wkpVchar2 = :wkpVchar2"),
    @NamedQuery(name = "WynKodPrz.findByWkpAbsSerial", query = "SELECT w FROM WynKodPrz w WHERE w.wkpAbsSerial = :wkpAbsSerial"),
    @NamedQuery(name = "WynKodPrz.findByWkpPdstUrlop", query = "SELECT w FROM WynKodPrz w WHERE w.wkpPdstUrlop = :wkpPdstUrlop"),
    @NamedQuery(name = "WynKodPrz.findByWkpTyp", query = "SELECT w FROM WynKodPrz w WHERE w.wkpTyp = :wkpTyp"),
    @NamedQuery(name = "WynKodPrz.findByWkpInt1", query = "SELECT w FROM WynKodPrz w WHERE w.wkpInt1 = :wkpInt1"),
    @NamedQuery(name = "WynKodPrz.findByWkpInt2", query = "SELECT w FROM WynKodPrz w WHERE w.wkpInt2 = :wkpInt2"),
    @NamedQuery(name = "WynKodPrz.findByWkpSwChorMaci", query = "SELECT w FROM WynKodPrz w WHERE w.wkpSwChorMaci = :wkpSwChorMaci"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod9", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod9 = :wkpDod9"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod10", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod10 = :wkpDod10"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod11", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod11 = :wkpDod11"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod12", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod12 = :wkpDod12"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod13", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod13 = :wkpDod13"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod14", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod14 = :wkpDod14"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod15", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod15 = :wkpDod15"),
    @NamedQuery(name = "WynKodPrz.findByWkpDod16", query = "SELECT w FROM WynKodPrz w WHERE w.wkpDod16 = :wkpDod16")})
public class WynKodPrz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_serial", nullable = false)
    private Integer wkpSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "wkp_kod", nullable = false, length = 3)
    private String wkpKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "wkp_opis", nullable = false, length = 254)
    private String wkpOpis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "wkp_opis_skr", nullable = false, length = 48)
    private String wkpOpisSkr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_pod_doch", nullable = false)
    private Character wkpPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_zus", nullable = false)
    private Character wkpZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_zdrowotne", nullable = false)
    private Character wkpZdrowotne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_chor_wyp", nullable = false)
    private Character wkpChorWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_zrodlo_fin", nullable = false)
    private Character wkpZrodloFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_wyp", nullable = false)
    private Character wkpWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_przerwa", nullable = false)
    private Character wkpPrzerwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_dod_1", nullable = false)
    private Character wkpDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_dod_2", nullable = false)
    private Character wkpDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_dod_3", nullable = false)
    private Character wkpDod3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_system", nullable = false)
    private Character wkpSystem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_absencja", nullable = false)
    private Character wkpAbsencja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_pdst_zas_chor", nullable = false)
    private Character wkpPdstZasChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_dod_4", nullable = false)
    private Character wkpDod4;
    @Column(name = "wkp_dod_5")
    private Character wkpDod5;
    @Column(name = "wkp_dod_6")
    private Character wkpDod6;
    @Column(name = "wkp_dod_7")
    private Character wkpDod7;
    @Column(name = "wkp_dod_8")
    private Character wkpDod8;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wkp_num_1", precision = 17, scale = 6)
    private BigDecimal wkpNum1;
    @Column(name = "wkp_num_2", precision = 17, scale = 6)
    private BigDecimal wkpNum2;
    @Column(name = "wkp_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wkpDate1;
    @Column(name = "wkp_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wkpDate2;
    @Size(max = 64)
    @Column(name = "wkp_vchar_1", length = 64)
    private String wkpVchar1;
    @Size(max = 64)
    @Column(name = "wkp_vchar_2", length = 64)
    private String wkpVchar2;
    @Column(name = "wkp_abs_serial")
    private Integer wkpAbsSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_pdst_urlop", nullable = false)
    private Character wkpPdstUrlop;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_typ", nullable = false)
    private Character wkpTyp;
    @Column(name = "wkp_int_1")
    private Integer wkpInt1;
    @Column(name = "wkp_int_2")
    private Integer wkpInt2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkp_sw_chor_maci", nullable = false)
    private Character wkpSwChorMaci;
    @Column(name = "wkp_dod_9")
    private Character wkpDod9;
    @Column(name = "wkp_dod_10")
    private Character wkpDod10;
    @Column(name = "wkp_dod_11")
    private Character wkpDod11;
    @Column(name = "wkp_dod_12")
    private Character wkpDod12;
    @Column(name = "wkp_dod_13")
    private Character wkpDod13;
    @Column(name = "wkp_dod_14")
    private Character wkpDod14;
    @Column(name = "wkp_dod_15")
    private Character wkpDod15;
    @Column(name = "wkp_dod_16")
    private Character wkpDod16;
    @JoinColumn(name = "wkp_ssd_serial", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis wkpSsdSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tkpWkpSerial")
    private List<TytulWkp> tytulWkpList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "przWkpSerial")
    private List<PlacePrz> placePrzList;
    @OneToMany(mappedBy = "ossWkpSerial")
    private List<OsobaSkl> osobaSklList;
    @OneToMany(mappedBy = "ospWkpSerial")
    private List<OsobaPrz> osobaPrzList;

    public WynKodPrz() {
    }

    public WynKodPrz(Integer wkpSerial) {
        this.wkpSerial = wkpSerial;
    }

    public WynKodPrz(Integer wkpSerial, String wkpKod, String wkpOpis, String wkpOpisSkr, Character wkpPodDoch, Character wkpZus, Character wkpZdrowotne, Character wkpChorWyp, Character wkpZrodloFin, Character wkpWyp, Character wkpPrzerwa, Character wkpDod1, Character wkpDod2, Character wkpDod3, Character wkpSystem, Character wkpAbsencja, Character wkpPdstZasChor, Character wkpDod4, Character wkpPdstUrlop, Character wkpTyp, Character wkpSwChorMaci) {
        this.wkpSerial = wkpSerial;
        this.wkpKod = wkpKod;
        this.wkpOpis = wkpOpis;
        this.wkpOpisSkr = wkpOpisSkr;
        this.wkpPodDoch = wkpPodDoch;
        this.wkpZus = wkpZus;
        this.wkpZdrowotne = wkpZdrowotne;
        this.wkpChorWyp = wkpChorWyp;
        this.wkpZrodloFin = wkpZrodloFin;
        this.wkpWyp = wkpWyp;
        this.wkpPrzerwa = wkpPrzerwa;
        this.wkpDod1 = wkpDod1;
        this.wkpDod2 = wkpDod2;
        this.wkpDod3 = wkpDod3;
        this.wkpSystem = wkpSystem;
        this.wkpAbsencja = wkpAbsencja;
        this.wkpPdstZasChor = wkpPdstZasChor;
        this.wkpDod4 = wkpDod4;
        this.wkpPdstUrlop = wkpPdstUrlop;
        this.wkpTyp = wkpTyp;
        this.wkpSwChorMaci = wkpSwChorMaci;
    }

    public Integer getWkpSerial() {
        return wkpSerial;
    }

    public void setWkpSerial(Integer wkpSerial) {
        this.wkpSerial = wkpSerial;
    }

    public String getWkpKod() {
        return wkpKod;
    }

    public void setWkpKod(String wkpKod) {
        this.wkpKod = wkpKod;
    }

    public String getWkpOpis() {
        return wkpOpis;
    }

    public void setWkpOpis(String wkpOpis) {
        this.wkpOpis = wkpOpis;
    }

    public String getWkpOpisSkr() {
        return wkpOpisSkr;
    }

    public void setWkpOpisSkr(String wkpOpisSkr) {
        this.wkpOpisSkr = wkpOpisSkr;
    }

    public Character getWkpPodDoch() {
        return wkpPodDoch;
    }

    public void setWkpPodDoch(Character wkpPodDoch) {
        this.wkpPodDoch = wkpPodDoch;
    }

    public Character getWkpZus() {
        return wkpZus;
    }

    public void setWkpZus(Character wkpZus) {
        this.wkpZus = wkpZus;
    }

    public Character getWkpZdrowotne() {
        return wkpZdrowotne;
    }

    public void setWkpZdrowotne(Character wkpZdrowotne) {
        this.wkpZdrowotne = wkpZdrowotne;
    }

    public Character getWkpChorWyp() {
        return wkpChorWyp;
    }

    public void setWkpChorWyp(Character wkpChorWyp) {
        this.wkpChorWyp = wkpChorWyp;
    }

    public Character getWkpZrodloFin() {
        return wkpZrodloFin;
    }

    public void setWkpZrodloFin(Character wkpZrodloFin) {
        this.wkpZrodloFin = wkpZrodloFin;
    }

    public Character getWkpWyp() {
        return wkpWyp;
    }

    public void setWkpWyp(Character wkpWyp) {
        this.wkpWyp = wkpWyp;
    }

    public Character getWkpPrzerwa() {
        return wkpPrzerwa;
    }

    public void setWkpPrzerwa(Character wkpPrzerwa) {
        this.wkpPrzerwa = wkpPrzerwa;
    }

    public Character getWkpDod1() {
        return wkpDod1;
    }

    public void setWkpDod1(Character wkpDod1) {
        this.wkpDod1 = wkpDod1;
    }

    public Character getWkpDod2() {
        return wkpDod2;
    }

    public void setWkpDod2(Character wkpDod2) {
        this.wkpDod2 = wkpDod2;
    }

    public Character getWkpDod3() {
        return wkpDod3;
    }

    public void setWkpDod3(Character wkpDod3) {
        this.wkpDod3 = wkpDod3;
    }

    public Character getWkpSystem() {
        return wkpSystem;
    }

    public void setWkpSystem(Character wkpSystem) {
        this.wkpSystem = wkpSystem;
    }

    public Character getWkpAbsencja() {
        return wkpAbsencja;
    }

    public void setWkpAbsencja(Character wkpAbsencja) {
        this.wkpAbsencja = wkpAbsencja;
    }

    public Character getWkpPdstZasChor() {
        return wkpPdstZasChor;
    }

    public void setWkpPdstZasChor(Character wkpPdstZasChor) {
        this.wkpPdstZasChor = wkpPdstZasChor;
    }

    public Character getWkpDod4() {
        return wkpDod4;
    }

    public void setWkpDod4(Character wkpDod4) {
        this.wkpDod4 = wkpDod4;
    }

    public Character getWkpDod5() {
        return wkpDod5;
    }

    public void setWkpDod5(Character wkpDod5) {
        this.wkpDod5 = wkpDod5;
    }

    public Character getWkpDod6() {
        return wkpDod6;
    }

    public void setWkpDod6(Character wkpDod6) {
        this.wkpDod6 = wkpDod6;
    }

    public Character getWkpDod7() {
        return wkpDod7;
    }

    public void setWkpDod7(Character wkpDod7) {
        this.wkpDod7 = wkpDod7;
    }

    public Character getWkpDod8() {
        return wkpDod8;
    }

    public void setWkpDod8(Character wkpDod8) {
        this.wkpDod8 = wkpDod8;
    }

    public BigDecimal getWkpNum1() {
        return wkpNum1;
    }

    public void setWkpNum1(BigDecimal wkpNum1) {
        this.wkpNum1 = wkpNum1;
    }

    public BigDecimal getWkpNum2() {
        return wkpNum2;
    }

    public void setWkpNum2(BigDecimal wkpNum2) {
        this.wkpNum2 = wkpNum2;
    }

    public Date getWkpDate1() {
        return wkpDate1;
    }

    public void setWkpDate1(Date wkpDate1) {
        this.wkpDate1 = wkpDate1;
    }

    public Date getWkpDate2() {
        return wkpDate2;
    }

    public void setWkpDate2(Date wkpDate2) {
        this.wkpDate2 = wkpDate2;
    }

    public String getWkpVchar1() {
        return wkpVchar1;
    }

    public void setWkpVchar1(String wkpVchar1) {
        this.wkpVchar1 = wkpVchar1;
    }

    public String getWkpVchar2() {
        return wkpVchar2;
    }

    public void setWkpVchar2(String wkpVchar2) {
        this.wkpVchar2 = wkpVchar2;
    }

    public Integer getWkpAbsSerial() {
        return wkpAbsSerial;
    }

    public void setWkpAbsSerial(Integer wkpAbsSerial) {
        this.wkpAbsSerial = wkpAbsSerial;
    }

    public Character getWkpPdstUrlop() {
        return wkpPdstUrlop;
    }

    public void setWkpPdstUrlop(Character wkpPdstUrlop) {
        this.wkpPdstUrlop = wkpPdstUrlop;
    }

    public Character getWkpTyp() {
        return wkpTyp;
    }

    public void setWkpTyp(Character wkpTyp) {
        this.wkpTyp = wkpTyp;
    }

    public Integer getWkpInt1() {
        return wkpInt1;
    }

    public void setWkpInt1(Integer wkpInt1) {
        this.wkpInt1 = wkpInt1;
    }

    public Integer getWkpInt2() {
        return wkpInt2;
    }

    public void setWkpInt2(Integer wkpInt2) {
        this.wkpInt2 = wkpInt2;
    }

    public Character getWkpSwChorMaci() {
        return wkpSwChorMaci;
    }

    public void setWkpSwChorMaci(Character wkpSwChorMaci) {
        this.wkpSwChorMaci = wkpSwChorMaci;
    }

    public Character getWkpDod9() {
        return wkpDod9;
    }

    public void setWkpDod9(Character wkpDod9) {
        this.wkpDod9 = wkpDod9;
    }

    public Character getWkpDod10() {
        return wkpDod10;
    }

    public void setWkpDod10(Character wkpDod10) {
        this.wkpDod10 = wkpDod10;
    }

    public Character getWkpDod11() {
        return wkpDod11;
    }

    public void setWkpDod11(Character wkpDod11) {
        this.wkpDod11 = wkpDod11;
    }

    public Character getWkpDod12() {
        return wkpDod12;
    }

    public void setWkpDod12(Character wkpDod12) {
        this.wkpDod12 = wkpDod12;
    }

    public Character getWkpDod13() {
        return wkpDod13;
    }

    public void setWkpDod13(Character wkpDod13) {
        this.wkpDod13 = wkpDod13;
    }

    public Character getWkpDod14() {
        return wkpDod14;
    }

    public void setWkpDod14(Character wkpDod14) {
        this.wkpDod14 = wkpDod14;
    }

    public Character getWkpDod15() {
        return wkpDod15;
    }

    public void setWkpDod15(Character wkpDod15) {
        this.wkpDod15 = wkpDod15;
    }

    public Character getWkpDod16() {
        return wkpDod16;
    }

    public void setWkpDod16(Character wkpDod16) {
        this.wkpDod16 = wkpDod16;
    }

    public StSystOpis getWkpSsdSerial() {
        return wkpSsdSerial;
    }

    public void setWkpSsdSerial(StSystOpis wkpSsdSerial) {
        this.wkpSsdSerial = wkpSsdSerial;
    }

    @XmlTransient
    public List<TytulWkp> getTytulWkpList() {
        return tytulWkpList;
    }

    public void setTytulWkpList(List<TytulWkp> tytulWkpList) {
        this.tytulWkpList = tytulWkpList;
    }

    @XmlTransient
    public List<PlacePrz> getPlacePrzList() {
        return placePrzList;
    }

    public void setPlacePrzList(List<PlacePrz> placePrzList) {
        this.placePrzList = placePrzList;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList() {
        return osobaSklList;
    }

    public void setOsobaSklList(List<OsobaSkl> osobaSklList) {
        this.osobaSklList = osobaSklList;
    }

    @XmlTransient
    public List<OsobaPrz> getOsobaPrzList() {
        return osobaPrzList;
    }

    public void setOsobaPrzList(List<OsobaPrz> osobaPrzList) {
        this.osobaPrzList = osobaPrzList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wkpSerial != null ? wkpSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WynKodPrz)) {
            return false;
        }
        WynKodPrz other = (WynKodPrz) object;
        if ((this.wkpSerial == null && other.wkpSerial != null) || (this.wkpSerial != null && !this.wkpSerial.equals(other.wkpSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.WynKodPrz[ wkpSerial=" + wkpSerial + " ]";
    }
    
}
