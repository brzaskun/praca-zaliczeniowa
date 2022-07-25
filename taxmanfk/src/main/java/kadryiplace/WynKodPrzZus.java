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
@Table(name = "wyn_kod_prz_zus", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WynKodPrzZus.findAll", query = "SELECT w FROM WynKodPrzZus w"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzSerial", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzSerial = :wkzSerial"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzKod", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzKod = :wkzKod"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzOpis", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzOpis = :wkzOpis"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzOpisSkr", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzOpisSkr = :wkzOpisSkr"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzPodDoch", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzPodDoch = :wkzPodDoch"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzZus", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzZus = :wkzZus"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzZdrowotne", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzZdrowotne = :wkzZdrowotne"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzChorWyp", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzChorWyp = :wkzChorWyp"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzZrodloFin", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzZrodloFin = :wkzZrodloFin"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzWyp", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzWyp = :wkzWyp"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzPrzerwa", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzPrzerwa = :wkzPrzerwa"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod1", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod1 = :wkzDod1"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod2", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod2 = :wkzDod2"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod3", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod3 = :wkzDod3"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzSystem", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzSystem = :wkzSystem"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzAbsencja", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzAbsencja = :wkzAbsencja"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzPdstZasChor", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzPdstZasChor = :wkzPdstZasChor"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod4", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod4 = :wkzDod4"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod5", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod5 = :wkzDod5"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod6", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod6 = :wkzDod6"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod7", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod7 = :wkzDod7"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod8", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod8 = :wkzDod8"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzNum1", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzNum1 = :wkzNum1"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzNum2", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzNum2 = :wkzNum2"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDate1", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDate1 = :wkzDate1"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDate2", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDate2 = :wkzDate2"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzVchar1", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzVchar1 = :wkzVchar1"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzVchar2", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzVchar2 = :wkzVchar2"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzAbsSerial", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzAbsSerial = :wkzAbsSerial"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzPdstUrlop", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzPdstUrlop = :wkzPdstUrlop"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzTyp", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzTyp = :wkzTyp"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzInt1", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzInt1 = :wkzInt1"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzInt2", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzInt2 = :wkzInt2"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzSwChorMaci", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzSwChorMaci = :wkzSwChorMaci"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod9", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod9 = :wkzDod9"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod10", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod10 = :wkzDod10"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod11", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod11 = :wkzDod11"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod12", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod12 = :wkzDod12"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod13", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod13 = :wkzDod13"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod14", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod14 = :wkzDod14"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod15", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod15 = :wkzDod15"),
    @NamedQuery(name = "WynKodPrzZus.findByWkzDod16", query = "SELECT w FROM WynKodPrzZus w WHERE w.wkzDod16 = :wkzDod16")})
public class WynKodPrzZus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_serial", nullable = false)
    private Integer wkzSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "wkz_kod", nullable = false, length = 3)
    private String wkzKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "wkz_opis", nullable = false, length = 254)
    private String wkzOpis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "wkz_opis_skr", nullable = false, length = 48)
    private String wkzOpisSkr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_pod_doch", nullable = false)
    private Character wkzPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_zus", nullable = false)
    private Character wkzZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_zdrowotne", nullable = false)
    private Character wkzZdrowotne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_chor_wyp", nullable = false)
    private Character wkzChorWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_zrodlo_fin", nullable = false)
    private Character wkzZrodloFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_wyp", nullable = false)
    private Character wkzWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_przerwa", nullable = false)
    private Character wkzPrzerwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_dod_1", nullable = false)
    private Character wkzDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_dod_2", nullable = false)
    private Character wkzDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_dod_3", nullable = false)
    private Character wkzDod3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_system", nullable = false)
    private Character wkzSystem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_absencja", nullable = false)
    private Character wkzAbsencja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_pdst_zas_chor", nullable = false)
    private Character wkzPdstZasChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_dod_4", nullable = false)
    private Character wkzDod4;
    @Column(name = "wkz_dod_5")
    private Character wkzDod5;
    @Column(name = "wkz_dod_6")
    private Character wkzDod6;
    @Column(name = "wkz_dod_7")
    private Character wkzDod7;
    @Column(name = "wkz_dod_8")
    private Character wkzDod8;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wkz_num_1", precision = 17, scale = 6)
    private BigDecimal wkzNum1;
    @Column(name = "wkz_num_2", precision = 17, scale = 6)
    private BigDecimal wkzNum2;
    @Column(name = "wkz_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wkzDate1;
    @Column(name = "wkz_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wkzDate2;
    @Size(max = 64)
    @Column(name = "wkz_vchar_1", length = 64)
    private String wkzVchar1;
    @Size(max = 64)
    @Column(name = "wkz_vchar_2", length = 64)
    private String wkzVchar2;
    @Column(name = "wkz_abs_serial")
    private Integer wkzAbsSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_pdst_urlop", nullable = false)
    private Character wkzPdstUrlop;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_typ", nullable = false)
    private Character wkzTyp;
    @Column(name = "wkz_int_1")
    private Integer wkzInt1;
    @Column(name = "wkz_int_2")
    private Integer wkzInt2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkz_sw_chor_maci", nullable = false)
    private Character wkzSwChorMaci;
    @Column(name = "wkz_dod_9")
    private Character wkzDod9;
    @Column(name = "wkz_dod_10")
    private Character wkzDod10;
    @Column(name = "wkz_dod_11")
    private Character wkzDod11;
    @Column(name = "wkz_dod_12")
    private Character wkzDod12;
    @Column(name = "wkz_dod_13")
    private Character wkzDod13;
    @Column(name = "wkz_dod_14")
    private Character wkzDod14;
    @Column(name = "wkz_dod_15")
    private Character wkzDod15;
    @Column(name = "wkz_dod_16")
    private Character wkzDod16;
    @JoinColumn(name = "wkz_ssd_serial", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis wkzSsdSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pzzWkzSerial")
    private List<PlacePrzZus> placePrzZusList;

    public WynKodPrzZus() {
    }

    public WynKodPrzZus(Integer wkzSerial) {
        this.wkzSerial = wkzSerial;
    }

    public WynKodPrzZus(Integer wkzSerial, String wkzKod, String wkzOpis, String wkzOpisSkr, Character wkzPodDoch, Character wkzZus, Character wkzZdrowotne, Character wkzChorWyp, Character wkzZrodloFin, Character wkzWyp, Character wkzPrzerwa, Character wkzDod1, Character wkzDod2, Character wkzDod3, Character wkzSystem, Character wkzAbsencja, Character wkzPdstZasChor, Character wkzDod4, Character wkzPdstUrlop, Character wkzTyp, Character wkzSwChorMaci) {
        this.wkzSerial = wkzSerial;
        this.wkzKod = wkzKod;
        this.wkzOpis = wkzOpis;
        this.wkzOpisSkr = wkzOpisSkr;
        this.wkzPodDoch = wkzPodDoch;
        this.wkzZus = wkzZus;
        this.wkzZdrowotne = wkzZdrowotne;
        this.wkzChorWyp = wkzChorWyp;
        this.wkzZrodloFin = wkzZrodloFin;
        this.wkzWyp = wkzWyp;
        this.wkzPrzerwa = wkzPrzerwa;
        this.wkzDod1 = wkzDod1;
        this.wkzDod2 = wkzDod2;
        this.wkzDod3 = wkzDod3;
        this.wkzSystem = wkzSystem;
        this.wkzAbsencja = wkzAbsencja;
        this.wkzPdstZasChor = wkzPdstZasChor;
        this.wkzDod4 = wkzDod4;
        this.wkzPdstUrlop = wkzPdstUrlop;
        this.wkzTyp = wkzTyp;
        this.wkzSwChorMaci = wkzSwChorMaci;
    }

    public Integer getWkzSerial() {
        return wkzSerial;
    }

    public void setWkzSerial(Integer wkzSerial) {
        this.wkzSerial = wkzSerial;
    }

    public String getWkzKod() {
        return wkzKod;
    }

    public void setWkzKod(String wkzKod) {
        this.wkzKod = wkzKod;
    }

    public String getWkzOpis() {
        return wkzOpis;
    }

    public void setWkzOpis(String wkzOpis) {
        this.wkzOpis = wkzOpis;
    }

    public String getWkzOpisSkr() {
        return wkzOpisSkr;
    }

    public void setWkzOpisSkr(String wkzOpisSkr) {
        this.wkzOpisSkr = wkzOpisSkr;
    }

    public Character getWkzPodDoch() {
        return wkzPodDoch;
    }

    public void setWkzPodDoch(Character wkzPodDoch) {
        this.wkzPodDoch = wkzPodDoch;
    }

    public Character getWkzZus() {
        return wkzZus;
    }

    public void setWkzZus(Character wkzZus) {
        this.wkzZus = wkzZus;
    }

    public Character getWkzZdrowotne() {
        return wkzZdrowotne;
    }

    public void setWkzZdrowotne(Character wkzZdrowotne) {
        this.wkzZdrowotne = wkzZdrowotne;
    }

    public Character getWkzChorWyp() {
        return wkzChorWyp;
    }

    public void setWkzChorWyp(Character wkzChorWyp) {
        this.wkzChorWyp = wkzChorWyp;
    }

    public Character getWkzZrodloFin() {
        return wkzZrodloFin;
    }

    public void setWkzZrodloFin(Character wkzZrodloFin) {
        this.wkzZrodloFin = wkzZrodloFin;
    }

    public Character getWkzWyp() {
        return wkzWyp;
    }

    public void setWkzWyp(Character wkzWyp) {
        this.wkzWyp = wkzWyp;
    }

    public Character getWkzPrzerwa() {
        return wkzPrzerwa;
    }

    public void setWkzPrzerwa(Character wkzPrzerwa) {
        this.wkzPrzerwa = wkzPrzerwa;
    }

    public Character getWkzDod1() {
        return wkzDod1;
    }

    public void setWkzDod1(Character wkzDod1) {
        this.wkzDod1 = wkzDod1;
    }

    public Character getWkzDod2() {
        return wkzDod2;
    }

    public void setWkzDod2(Character wkzDod2) {
        this.wkzDod2 = wkzDod2;
    }

    public Character getWkzDod3() {
        return wkzDod3;
    }

    public void setWkzDod3(Character wkzDod3) {
        this.wkzDod3 = wkzDod3;
    }

    public Character getWkzSystem() {
        return wkzSystem;
    }

    public void setWkzSystem(Character wkzSystem) {
        this.wkzSystem = wkzSystem;
    }

    public Character getWkzAbsencja() {
        return wkzAbsencja;
    }

    public void setWkzAbsencja(Character wkzAbsencja) {
        this.wkzAbsencja = wkzAbsencja;
    }

    public Character getWkzPdstZasChor() {
        return wkzPdstZasChor;
    }

    public void setWkzPdstZasChor(Character wkzPdstZasChor) {
        this.wkzPdstZasChor = wkzPdstZasChor;
    }

    public Character getWkzDod4() {
        return wkzDod4;
    }

    public void setWkzDod4(Character wkzDod4) {
        this.wkzDod4 = wkzDod4;
    }

    public Character getWkzDod5() {
        return wkzDod5;
    }

    public void setWkzDod5(Character wkzDod5) {
        this.wkzDod5 = wkzDod5;
    }

    public Character getWkzDod6() {
        return wkzDod6;
    }

    public void setWkzDod6(Character wkzDod6) {
        this.wkzDod6 = wkzDod6;
    }

    public Character getWkzDod7() {
        return wkzDod7;
    }

    public void setWkzDod7(Character wkzDod7) {
        this.wkzDod7 = wkzDod7;
    }

    public Character getWkzDod8() {
        return wkzDod8;
    }

    public void setWkzDod8(Character wkzDod8) {
        this.wkzDod8 = wkzDod8;
    }

    public BigDecimal getWkzNum1() {
        return wkzNum1;
    }

    public void setWkzNum1(BigDecimal wkzNum1) {
        this.wkzNum1 = wkzNum1;
    }

    public BigDecimal getWkzNum2() {
        return wkzNum2;
    }

    public void setWkzNum2(BigDecimal wkzNum2) {
        this.wkzNum2 = wkzNum2;
    }

    public Date getWkzDate1() {
        return wkzDate1;
    }

    public void setWkzDate1(Date wkzDate1) {
        this.wkzDate1 = wkzDate1;
    }

    public Date getWkzDate2() {
        return wkzDate2;
    }

    public void setWkzDate2(Date wkzDate2) {
        this.wkzDate2 = wkzDate2;
    }

    public String getWkzVchar1() {
        return wkzVchar1;
    }

    public void setWkzVchar1(String wkzVchar1) {
        this.wkzVchar1 = wkzVchar1;
    }

    public String getWkzVchar2() {
        return wkzVchar2;
    }

    public void setWkzVchar2(String wkzVchar2) {
        this.wkzVchar2 = wkzVchar2;
    }

    public Integer getWkzAbsSerial() {
        return wkzAbsSerial;
    }

    public void setWkzAbsSerial(Integer wkzAbsSerial) {
        this.wkzAbsSerial = wkzAbsSerial;
    }

    public Character getWkzPdstUrlop() {
        return wkzPdstUrlop;
    }

    public void setWkzPdstUrlop(Character wkzPdstUrlop) {
        this.wkzPdstUrlop = wkzPdstUrlop;
    }

    public Character getWkzTyp() {
        return wkzTyp;
    }

    public void setWkzTyp(Character wkzTyp) {
        this.wkzTyp = wkzTyp;
    }

    public Integer getWkzInt1() {
        return wkzInt1;
    }

    public void setWkzInt1(Integer wkzInt1) {
        this.wkzInt1 = wkzInt1;
    }

    public Integer getWkzInt2() {
        return wkzInt2;
    }

    public void setWkzInt2(Integer wkzInt2) {
        this.wkzInt2 = wkzInt2;
    }

    public Character getWkzSwChorMaci() {
        return wkzSwChorMaci;
    }

    public void setWkzSwChorMaci(Character wkzSwChorMaci) {
        this.wkzSwChorMaci = wkzSwChorMaci;
    }

    public Character getWkzDod9() {
        return wkzDod9;
    }

    public void setWkzDod9(Character wkzDod9) {
        this.wkzDod9 = wkzDod9;
    }

    public Character getWkzDod10() {
        return wkzDod10;
    }

    public void setWkzDod10(Character wkzDod10) {
        this.wkzDod10 = wkzDod10;
    }

    public Character getWkzDod11() {
        return wkzDod11;
    }

    public void setWkzDod11(Character wkzDod11) {
        this.wkzDod11 = wkzDod11;
    }

    public Character getWkzDod12() {
        return wkzDod12;
    }

    public void setWkzDod12(Character wkzDod12) {
        this.wkzDod12 = wkzDod12;
    }

    public Character getWkzDod13() {
        return wkzDod13;
    }

    public void setWkzDod13(Character wkzDod13) {
        this.wkzDod13 = wkzDod13;
    }

    public Character getWkzDod14() {
        return wkzDod14;
    }

    public void setWkzDod14(Character wkzDod14) {
        this.wkzDod14 = wkzDod14;
    }

    public Character getWkzDod15() {
        return wkzDod15;
    }

    public void setWkzDod15(Character wkzDod15) {
        this.wkzDod15 = wkzDod15;
    }

    public Character getWkzDod16() {
        return wkzDod16;
    }

    public void setWkzDod16(Character wkzDod16) {
        this.wkzDod16 = wkzDod16;
    }

    public StSystOpis getWkzSsdSerial() {
        return wkzSsdSerial;
    }

    public void setWkzSsdSerial(StSystOpis wkzSsdSerial) {
        this.wkzSsdSerial = wkzSsdSerial;
    }

    @XmlTransient
    public List<PlacePrzZus> getPlacePrzZusList() {
        return placePrzZusList;
    }

    public void setPlacePrzZusList(List<PlacePrzZus> placePrzZusList) {
        this.placePrzZusList = placePrzZusList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wkzSerial != null ? wkzSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WynKodPrzZus)) {
            return false;
        }
        WynKodPrzZus other = (WynKodPrzZus) object;
        if ((this.wkzSerial == null && other.wkzSerial != null) || (this.wkzSerial != null && !this.wkzSerial.equals(other.wkzSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.WynKodPrzZus[ wkzSerial=" + wkzSerial + " ]";
    }
    
}
