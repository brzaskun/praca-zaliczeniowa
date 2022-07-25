/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "amo_okr", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AmoOkr.findAll", query = "SELECT a FROM AmoOkr a"),
    @NamedQuery(name = "AmoOkr.findByAooSerial", query = "SELECT a FROM AmoOkr a WHERE a.aooSerial = :aooSerial"),
    @NamedQuery(name = "AmoOkr.findByAooWartUlep", query = "SELECT a FROM AmoOkr a WHERE a.aooWartUlep = :aooWartUlep"),
    @NamedQuery(name = "AmoOkr.findByAooPodstawa", query = "SELECT a FROM AmoOkr a WHERE a.aooPodstawa = :aooPodstawa"),
    @NamedQuery(name = "AmoOkr.findByAmoStawka", query = "SELECT a FROM AmoOkr a WHERE a.amoStawka = :amoStawka"),
    @NamedQuery(name = "AmoOkr.findByAooOdpisMies", query = "SELECT a FROM AmoOkr a WHERE a.aooOdpisMies = :aooOdpisMies"),
    @NamedQuery(name = "AmoOkr.findByAooNum1", query = "SELECT a FROM AmoOkr a WHERE a.aooNum1 = :aooNum1"),
    @NamedQuery(name = "AmoOkr.findByAooNum2", query = "SELECT a FROM AmoOkr a WHERE a.aooNum2 = :aooNum2"),
    @NamedQuery(name = "AmoOkr.findByAooNum3", query = "SELECT a FROM AmoOkr a WHERE a.aooNum3 = :aooNum3"),
    @NamedQuery(name = "AmoOkr.findByAooNum4", query = "SELECT a FROM AmoOkr a WHERE a.aooNum4 = :aooNum4"),
    @NamedQuery(name = "AmoOkr.findByAooNum5", query = "SELECT a FROM AmoOkr a WHERE a.aooNum5 = :aooNum5"),
    @NamedQuery(name = "AmoOkr.findByAooNum6", query = "SELECT a FROM AmoOkr a WHERE a.aooNum6 = :aooNum6"),
    @NamedQuery(name = "AmoOkr.findByAooNum7", query = "SELECT a FROM AmoOkr a WHERE a.aooNum7 = :aooNum7"),
    @NamedQuery(name = "AmoOkr.findByAooNum8", query = "SELECT a FROM AmoOkr a WHERE a.aooNum8 = :aooNum8"),
    @NamedQuery(name = "AmoOkr.findByAooChar1", query = "SELECT a FROM AmoOkr a WHERE a.aooChar1 = :aooChar1"),
    @NamedQuery(name = "AmoOkr.findByAooChar2", query = "SELECT a FROM AmoOkr a WHERE a.aooChar2 = :aooChar2"),
    @NamedQuery(name = "AmoOkr.findByAooChar3", query = "SELECT a FROM AmoOkr a WHERE a.aooChar3 = :aooChar3"),
    @NamedQuery(name = "AmoOkr.findByAooTyp", query = "SELECT a FROM AmoOkr a WHERE a.aooTyp = :aooTyp"),
    @NamedQuery(name = "AmoOkr.findByAooVchar1", query = "SELECT a FROM AmoOkr a WHERE a.aooVchar1 = :aooVchar1"),
    @NamedQuery(name = "AmoOkr.findByAooVchar2", query = "SELECT a FROM AmoOkr a WHERE a.aooVchar2 = :aooVchar2"),
    @NamedQuery(name = "AmoOkr.findByAooData1", query = "SELECT a FROM AmoOkr a WHERE a.aooData1 = :aooData1"),
    @NamedQuery(name = "AmoOkr.findByAooData2", query = "SELECT a FROM AmoOkr a WHERE a.aooData2 = :aooData2")})
public class AmoOkr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "aoo_serial", nullable = false)
    private Integer aooSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "aoo_wart_ulep", nullable = false, precision = 13, scale = 2)
    private BigDecimal aooWartUlep;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aoo_podstawa", nullable = false, precision = 13, scale = 2)
    private BigDecimal aooPodstawa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amo_stawka", nullable = false, precision = 13, scale = 2)
    private BigDecimal amoStawka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aoo_odpis_mies", nullable = false, precision = 13, scale = 2)
    private BigDecimal aooOdpisMies;
    @Column(name = "aoo_num_1", precision = 17, scale = 6)
    private BigDecimal aooNum1;
    @Column(name = "aoo_num_2", precision = 17, scale = 6)
    private BigDecimal aooNum2;
    @Column(name = "aoo_num_3", precision = 17, scale = 6)
    private BigDecimal aooNum3;
    @Column(name = "aoo_num_4", precision = 17, scale = 6)
    private BigDecimal aooNum4;
    @Column(name = "aoo_num_5", precision = 17, scale = 6)
    private BigDecimal aooNum5;
    @Column(name = "aoo_num_6", precision = 17, scale = 6)
    private BigDecimal aooNum6;
    @Column(name = "aoo_num_7", precision = 17, scale = 6)
    private BigDecimal aooNum7;
    @Column(name = "aoo_num_8", precision = 17, scale = 6)
    private BigDecimal aooNum8;
    @Column(name = "aoo_char_1")
    private Character aooChar1;
    @Column(name = "aoo_char_2")
    private Character aooChar2;
    @Column(name = "aoo_char_3")
    private Character aooChar3;
    @Column(name = "aoo_typ")
    private Character aooTyp;
    @Size(max = 64)
    @Column(name = "aoo_vchar_1", length = 64)
    private String aooVchar1;
    @Size(max = 64)
    @Column(name = "aoo_vchar_2", length = 64)
    private String aooVchar2;
    @Column(name = "aoo_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aooData1;
    @Column(name = "aoo_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date aooData2;
    @JoinColumn(name = "aoo_amo_serial", referencedColumnName = "amo_serial", nullable = false)
    @ManyToOne(optional = false)
    private Amorty aooAmoSerial;
    @JoinColumn(name = "aoo_okr_serial", referencedColumnName = "okr_serial")
    @ManyToOne
    private Okres aooOkrSerial;

    public AmoOkr() {
    }

    public AmoOkr(Integer aooSerial) {
        this.aooSerial = aooSerial;
    }

    public AmoOkr(Integer aooSerial, BigDecimal aooWartUlep, BigDecimal aooPodstawa, BigDecimal amoStawka, BigDecimal aooOdpisMies) {
        this.aooSerial = aooSerial;
        this.aooWartUlep = aooWartUlep;
        this.aooPodstawa = aooPodstawa;
        this.amoStawka = amoStawka;
        this.aooOdpisMies = aooOdpisMies;
    }

    public Integer getAooSerial() {
        return aooSerial;
    }

    public void setAooSerial(Integer aooSerial) {
        this.aooSerial = aooSerial;
    }

    public BigDecimal getAooWartUlep() {
        return aooWartUlep;
    }

    public void setAooWartUlep(BigDecimal aooWartUlep) {
        this.aooWartUlep = aooWartUlep;
    }

    public BigDecimal getAooPodstawa() {
        return aooPodstawa;
    }

    public void setAooPodstawa(BigDecimal aooPodstawa) {
        this.aooPodstawa = aooPodstawa;
    }

    public BigDecimal getAmoStawka() {
        return amoStawka;
    }

    public void setAmoStawka(BigDecimal amoStawka) {
        this.amoStawka = amoStawka;
    }

    public BigDecimal getAooOdpisMies() {
        return aooOdpisMies;
    }

    public void setAooOdpisMies(BigDecimal aooOdpisMies) {
        this.aooOdpisMies = aooOdpisMies;
    }

    public BigDecimal getAooNum1() {
        return aooNum1;
    }

    public void setAooNum1(BigDecimal aooNum1) {
        this.aooNum1 = aooNum1;
    }

    public BigDecimal getAooNum2() {
        return aooNum2;
    }

    public void setAooNum2(BigDecimal aooNum2) {
        this.aooNum2 = aooNum2;
    }

    public BigDecimal getAooNum3() {
        return aooNum3;
    }

    public void setAooNum3(BigDecimal aooNum3) {
        this.aooNum3 = aooNum3;
    }

    public BigDecimal getAooNum4() {
        return aooNum4;
    }

    public void setAooNum4(BigDecimal aooNum4) {
        this.aooNum4 = aooNum4;
    }

    public BigDecimal getAooNum5() {
        return aooNum5;
    }

    public void setAooNum5(BigDecimal aooNum5) {
        this.aooNum5 = aooNum5;
    }

    public BigDecimal getAooNum6() {
        return aooNum6;
    }

    public void setAooNum6(BigDecimal aooNum6) {
        this.aooNum6 = aooNum6;
    }

    public BigDecimal getAooNum7() {
        return aooNum7;
    }

    public void setAooNum7(BigDecimal aooNum7) {
        this.aooNum7 = aooNum7;
    }

    public BigDecimal getAooNum8() {
        return aooNum8;
    }

    public void setAooNum8(BigDecimal aooNum8) {
        this.aooNum8 = aooNum8;
    }

    public Character getAooChar1() {
        return aooChar1;
    }

    public void setAooChar1(Character aooChar1) {
        this.aooChar1 = aooChar1;
    }

    public Character getAooChar2() {
        return aooChar2;
    }

    public void setAooChar2(Character aooChar2) {
        this.aooChar2 = aooChar2;
    }

    public Character getAooChar3() {
        return aooChar3;
    }

    public void setAooChar3(Character aooChar3) {
        this.aooChar3 = aooChar3;
    }

    public Character getAooTyp() {
        return aooTyp;
    }

    public void setAooTyp(Character aooTyp) {
        this.aooTyp = aooTyp;
    }

    public String getAooVchar1() {
        return aooVchar1;
    }

    public void setAooVchar1(String aooVchar1) {
        this.aooVchar1 = aooVchar1;
    }

    public String getAooVchar2() {
        return aooVchar2;
    }

    public void setAooVchar2(String aooVchar2) {
        this.aooVchar2 = aooVchar2;
    }

    public Date getAooData1() {
        return aooData1;
    }

    public void setAooData1(Date aooData1) {
        this.aooData1 = aooData1;
    }

    public Date getAooData2() {
        return aooData2;
    }

    public void setAooData2(Date aooData2) {
        this.aooData2 = aooData2;
    }

    public Amorty getAooAmoSerial() {
        return aooAmoSerial;
    }

    public void setAooAmoSerial(Amorty aooAmoSerial) {
        this.aooAmoSerial = aooAmoSerial;
    }

    public Okres getAooOkrSerial() {
        return aooOkrSerial;
    }

    public void setAooOkrSerial(Okres aooOkrSerial) {
        this.aooOkrSerial = aooOkrSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aooSerial != null ? aooSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmoOkr)) {
            return false;
        }
        AmoOkr other = (AmoOkr) object;
        if ((this.aooSerial == null && other.aooSerial != null) || (this.aooSerial != null && !this.aooSerial.equals(other.aooSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.AmoOkr[ aooSerial=" + aooSerial + " ]";
    }
    
}
