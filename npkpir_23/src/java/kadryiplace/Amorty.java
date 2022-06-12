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
@Table(name = "amorty", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Amorty.findAll", query = "SELECT a FROM Amorty a"),
    @NamedQuery(name = "Amorty.findByAmoSerial", query = "SELECT a FROM Amorty a WHERE a.amoSerial = :amoSerial"),
    @NamedQuery(name = "Amorty.findByAmoWspol", query = "SELECT a FROM Amorty a WHERE a.amoWspol = :amoWspol"),
    @NamedQuery(name = "Amorty.findByAmoUmorz", query = "SELECT a FROM Amorty a WHERE a.amoUmorz = :amoUmorz"),
    @NamedQuery(name = "Amorty.findByAmoPodst", query = "SELECT a FROM Amorty a WHERE a.amoPodst = :amoPodst"),
    @NamedQuery(name = "Amorty.findByAmoStawka", query = "SELECT a FROM Amorty a WHERE a.amoStawka = :amoStawka"),
    @NamedQuery(name = "Amorty.findByAmoPocz", query = "SELECT a FROM Amorty a WHERE a.amoPocz = :amoPocz"),
    @NamedQuery(name = "Amorty.findByAmoRok", query = "SELECT a FROM Amorty a WHERE a.amoRok = :amoRok"),
    @NamedQuery(name = "Amorty.findByAmoPrzyczLik", query = "SELECT a FROM Amorty a WHERE a.amoPrzyczLik = :amoPrzyczLik"),
    @NamedQuery(name = "Amorty.findByAmoDataLik", query = "SELECT a FROM Amorty a WHERE a.amoDataLik = :amoDataLik"),
    @NamedQuery(name = "Amorty.findByAmoChar1", query = "SELECT a FROM Amorty a WHERE a.amoChar1 = :amoChar1"),
    @NamedQuery(name = "Amorty.findByAmoChar2", query = "SELECT a FROM Amorty a WHERE a.amoChar2 = :amoChar2"),
    @NamedQuery(name = "Amorty.findByAmoNum1", query = "SELECT a FROM Amorty a WHERE a.amoNum1 = :amoNum1"),
    @NamedQuery(name = "Amorty.findByAmoNum2", query = "SELECT a FROM Amorty a WHERE a.amoNum2 = :amoNum2"),
    @NamedQuery(name = "Amorty.findByAmoNum3", query = "SELECT a FROM Amorty a WHERE a.amoNum3 = :amoNum3"),
    @NamedQuery(name = "Amorty.findByAmoNum4", query = "SELECT a FROM Amorty a WHERE a.amoNum4 = :amoNum4"),
    @NamedQuery(name = "Amorty.findByAmoData1", query = "SELECT a FROM Amorty a WHERE a.amoData1 = :amoData1"),
    @NamedQuery(name = "Amorty.findByAmoData2", query = "SELECT a FROM Amorty a WHERE a.amoData2 = :amoData2"),
    @NamedQuery(name = "Amorty.findByAmoTyp", query = "SELECT a FROM Amorty a WHERE a.amoTyp = :amoTyp")})
public class Amorty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "amo_serial", nullable = false)
    private Integer amoSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amo_wspol", precision = 5, scale = 2)
    private BigDecimal amoWspol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amo_umorz", nullable = false, precision = 13, scale = 2)
    private BigDecimal amoUmorz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amo_podst", nullable = false, precision = 13, scale = 2)
    private BigDecimal amoPodst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amo_stawka", nullable = false, precision = 5, scale = 2)
    private BigDecimal amoStawka;
    @Column(name = "amo_pocz", precision = 13, scale = 2)
    private BigDecimal amoPocz;
    @Column(name = "amo_rok")
    private Short amoRok;
    @Size(max = 64)
    @Column(name = "amo_przycz_lik", length = 64)
    private String amoPrzyczLik;
    @Column(name = "amo_data_lik")
    @Temporal(TemporalType.TIMESTAMP)
    private Date amoDataLik;
    @Column(name = "amo_char_1")
    private Character amoChar1;
    @Column(name = "amo_char_2")
    private Character amoChar2;
    @Column(name = "amo_num_1", precision = 17, scale = 6)
    private BigDecimal amoNum1;
    @Column(name = "amo_num_2", precision = 17, scale = 6)
    private BigDecimal amoNum2;
    @Column(name = "amo_num_3", precision = 17, scale = 6)
    private BigDecimal amoNum3;
    @Column(name = "amo_num_4", precision = 17, scale = 6)
    private BigDecimal amoNum4;
    @Column(name = "amo_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date amoData1;
    @Column(name = "amo_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date amoData2;
    @Column(name = "amo_typ")
    private Character amoTyp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aooAmoSerial")
    private List<AmoOkr> amoOkrList;
    @JoinColumn(name = "amo_rok_serial", referencedColumnName = "rok_serial")
    @ManyToOne
    private Rok amoRokSerial;
    @JoinColumn(name = "amo_sro_serial", referencedColumnName = "sro_serial")
    @ManyToOne
    private Srodki amoSroSerial;

    public Amorty() {
    }

    public Amorty(Integer amoSerial) {
        this.amoSerial = amoSerial;
    }

    public Amorty(Integer amoSerial, BigDecimal amoUmorz, BigDecimal amoPodst, BigDecimal amoStawka) {
        this.amoSerial = amoSerial;
        this.amoUmorz = amoUmorz;
        this.amoPodst = amoPodst;
        this.amoStawka = amoStawka;
    }

    public Integer getAmoSerial() {
        return amoSerial;
    }

    public void setAmoSerial(Integer amoSerial) {
        this.amoSerial = amoSerial;
    }

    public BigDecimal getAmoWspol() {
        return amoWspol;
    }

    public void setAmoWspol(BigDecimal amoWspol) {
        this.amoWspol = amoWspol;
    }

    public BigDecimal getAmoUmorz() {
        return amoUmorz;
    }

    public void setAmoUmorz(BigDecimal amoUmorz) {
        this.amoUmorz = amoUmorz;
    }

    public BigDecimal getAmoPodst() {
        return amoPodst;
    }

    public void setAmoPodst(BigDecimal amoPodst) {
        this.amoPodst = amoPodst;
    }

    public BigDecimal getAmoStawka() {
        return amoStawka;
    }

    public void setAmoStawka(BigDecimal amoStawka) {
        this.amoStawka = amoStawka;
    }

    public BigDecimal getAmoPocz() {
        return amoPocz;
    }

    public void setAmoPocz(BigDecimal amoPocz) {
        this.amoPocz = amoPocz;
    }

    public Short getAmoRok() {
        return amoRok;
    }

    public void setAmoRok(Short amoRok) {
        this.amoRok = amoRok;
    }

    public String getAmoPrzyczLik() {
        return amoPrzyczLik;
    }

    public void setAmoPrzyczLik(String amoPrzyczLik) {
        this.amoPrzyczLik = amoPrzyczLik;
    }

    public Date getAmoDataLik() {
        return amoDataLik;
    }

    public void setAmoDataLik(Date amoDataLik) {
        this.amoDataLik = amoDataLik;
    }

    public Character getAmoChar1() {
        return amoChar1;
    }

    public void setAmoChar1(Character amoChar1) {
        this.amoChar1 = amoChar1;
    }

    public Character getAmoChar2() {
        return amoChar2;
    }

    public void setAmoChar2(Character amoChar2) {
        this.amoChar2 = amoChar2;
    }

    public BigDecimal getAmoNum1() {
        return amoNum1;
    }

    public void setAmoNum1(BigDecimal amoNum1) {
        this.amoNum1 = amoNum1;
    }

    public BigDecimal getAmoNum2() {
        return amoNum2;
    }

    public void setAmoNum2(BigDecimal amoNum2) {
        this.amoNum2 = amoNum2;
    }

    public BigDecimal getAmoNum3() {
        return amoNum3;
    }

    public void setAmoNum3(BigDecimal amoNum3) {
        this.amoNum3 = amoNum3;
    }

    public BigDecimal getAmoNum4() {
        return amoNum4;
    }

    public void setAmoNum4(BigDecimal amoNum4) {
        this.amoNum4 = amoNum4;
    }

    public Date getAmoData1() {
        return amoData1;
    }

    public void setAmoData1(Date amoData1) {
        this.amoData1 = amoData1;
    }

    public Date getAmoData2() {
        return amoData2;
    }

    public void setAmoData2(Date amoData2) {
        this.amoData2 = amoData2;
    }

    public Character getAmoTyp() {
        return amoTyp;
    }

    public void setAmoTyp(Character amoTyp) {
        this.amoTyp = amoTyp;
    }

    @XmlTransient
    public List<AmoOkr> getAmoOkrList() {
        return amoOkrList;
    }

    public void setAmoOkrList(List<AmoOkr> amoOkrList) {
        this.amoOkrList = amoOkrList;
    }

    public Rok getAmoRokSerial() {
        return amoRokSerial;
    }

    public void setAmoRokSerial(Rok amoRokSerial) {
        this.amoRokSerial = amoRokSerial;
    }

    public Srodki getAmoSroSerial() {
        return amoSroSerial;
    }

    public void setAmoSroSerial(Srodki amoSroSerial) {
        this.amoSroSerial = amoSroSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (amoSerial != null ? amoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Amorty)) {
            return false;
        }
        Amorty other = (Amorty) object;
        if ((this.amoSerial == null && other.amoSerial != null) || (this.amoSerial != null && !this.amoSerial.equals(other.amoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Amorty[ amoSerial=" + amoSerial + " ]";
    }
    
}
