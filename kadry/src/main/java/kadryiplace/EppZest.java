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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "epp_zest", catalog = "kadryiplace", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"epz_poj_serial", "epz_rok_serial", "epz_lp"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EppZest.findAll", query = "SELECT e FROM EppZest e"),
    @NamedQuery(name = "EppZest.findByEpzSerial", query = "SELECT e FROM EppZest e WHERE e.epzSerial = :epzSerial"),
    @NamedQuery(name = "EppZest.findByEpzLp", query = "SELECT e FROM EppZest e WHERE e.epzLp = :epzLp"),
    @NamedQuery(name = "EppZest.findByEpzData", query = "SELECT e FROM EppZest e WHERE e.epzData = :epzData"),
    @NamedQuery(name = "EppZest.findByEpzDowod", query = "SELECT e FROM EppZest e WHERE e.epzDowod = :epzDowod"),
    @NamedQuery(name = "EppZest.findByEpzOpis", query = "SELECT e FROM EppZest e WHERE e.epzOpis = :epzOpis"),
    @NamedQuery(name = "EppZest.findByEpzKwota", query = "SELECT e FROM EppZest e WHERE e.epzKwota = :epzKwota"),
    @NamedQuery(name = "EppZest.findByEpzSkreslony", query = "SELECT e FROM EppZest e WHERE e.epzSkreslony = :epzSkreslony"),
    @NamedQuery(name = "EppZest.findByEpzChar1", query = "SELECT e FROM EppZest e WHERE e.epzChar1 = :epzChar1"),
    @NamedQuery(name = "EppZest.findByEpzChar2", query = "SELECT e FROM EppZest e WHERE e.epzChar2 = :epzChar2"),
    @NamedQuery(name = "EppZest.findByEpzVchar1", query = "SELECT e FROM EppZest e WHERE e.epzVchar1 = :epzVchar1"),
    @NamedQuery(name = "EppZest.findByEpzData1", query = "SELECT e FROM EppZest e WHERE e.epzData1 = :epzData1"),
    @NamedQuery(name = "EppZest.findByEpzData2", query = "SELECT e FROM EppZest e WHERE e.epzData2 = :epzData2"),
    @NamedQuery(name = "EppZest.findByEpzNum1", query = "SELECT e FROM EppZest e WHERE e.epzNum1 = :epzNum1"),
    @NamedQuery(name = "EppZest.findByEpzNum2", query = "SELECT e FROM EppZest e WHERE e.epzNum2 = :epzNum2"),
    @NamedQuery(name = "EppZest.findByEpzInt1", query = "SELECT e FROM EppZest e WHERE e.epzInt1 = :epzInt1")})
public class EppZest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "epz_serial", nullable = false)
    private Integer epzSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "epz_lp", nullable = false)
    private int epzLp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "epz_data", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date epzData;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "epz_dowod", nullable = false, length = 32)
    private String epzDowod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "epz_opis", nullable = false, length = 64)
    private String epzOpis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "epz_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal epzKwota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "epz_skreslony", nullable = false)
    private Character epzSkreslony;
    @Column(name = "epz_char_1")
    private Character epzChar1;
    @Column(name = "epz_char_2")
    private Character epzChar2;
    @Size(max = 64)
    @Column(name = "epz_vchar_1", length = 64)
    private String epzVchar1;
    @Column(name = "epz_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date epzData1;
    @Column(name = "epz_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date epzData2;
    @Column(name = "epz_num_1", precision = 17, scale = 6)
    private BigDecimal epzNum1;
    @Column(name = "epz_num_2", precision = 17, scale = 6)
    private BigDecimal epzNum2;
    @Column(name = "epz_int_1")
    private Integer epzInt1;
    @OneToMany(mappedBy = "dsrEpzSerial")
    private List<DaneStatR> daneStatRList;
    @JoinColumn(name = "epz_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres epzOkrSerial;
    @JoinColumn(name = "epz_poj_serial", referencedColumnName = "poj_serial", nullable = false)
    @ManyToOne(optional = false)
    private Pojazdy epzPojSerial;
    @JoinColumn(name = "epz_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok epzRokSerial;

    public EppZest() {
    }

    public EppZest(Integer epzSerial) {
        this.epzSerial = epzSerial;
    }

    public EppZest(Integer epzSerial, int epzLp, Date epzData, String epzDowod, String epzOpis, BigDecimal epzKwota, Character epzSkreslony) {
        this.epzSerial = epzSerial;
        this.epzLp = epzLp;
        this.epzData = epzData;
        this.epzDowod = epzDowod;
        this.epzOpis = epzOpis;
        this.epzKwota = epzKwota;
        this.epzSkreslony = epzSkreslony;
    }

    public Integer getEpzSerial() {
        return epzSerial;
    }

    public void setEpzSerial(Integer epzSerial) {
        this.epzSerial = epzSerial;
    }

    public int getEpzLp() {
        return epzLp;
    }

    public void setEpzLp(int epzLp) {
        this.epzLp = epzLp;
    }

    public Date getEpzData() {
        return epzData;
    }

    public void setEpzData(Date epzData) {
        this.epzData = epzData;
    }

    public String getEpzDowod() {
        return epzDowod;
    }

    public void setEpzDowod(String epzDowod) {
        this.epzDowod = epzDowod;
    }

    public String getEpzOpis() {
        return epzOpis;
    }

    public void setEpzOpis(String epzOpis) {
        this.epzOpis = epzOpis;
    }

    public BigDecimal getEpzKwota() {
        return epzKwota;
    }

    public void setEpzKwota(BigDecimal epzKwota) {
        this.epzKwota = epzKwota;
    }

    public Character getEpzSkreslony() {
        return epzSkreslony;
    }

    public void setEpzSkreslony(Character epzSkreslony) {
        this.epzSkreslony = epzSkreslony;
    }

    public Character getEpzChar1() {
        return epzChar1;
    }

    public void setEpzChar1(Character epzChar1) {
        this.epzChar1 = epzChar1;
    }

    public Character getEpzChar2() {
        return epzChar2;
    }

    public void setEpzChar2(Character epzChar2) {
        this.epzChar2 = epzChar2;
    }

    public String getEpzVchar1() {
        return epzVchar1;
    }

    public void setEpzVchar1(String epzVchar1) {
        this.epzVchar1 = epzVchar1;
    }

    public Date getEpzData1() {
        return epzData1;
    }

    public void setEpzData1(Date epzData1) {
        this.epzData1 = epzData1;
    }

    public Date getEpzData2() {
        return epzData2;
    }

    public void setEpzData2(Date epzData2) {
        this.epzData2 = epzData2;
    }

    public BigDecimal getEpzNum1() {
        return epzNum1;
    }

    public void setEpzNum1(BigDecimal epzNum1) {
        this.epzNum1 = epzNum1;
    }

    public BigDecimal getEpzNum2() {
        return epzNum2;
    }

    public void setEpzNum2(BigDecimal epzNum2) {
        this.epzNum2 = epzNum2;
    }

    public Integer getEpzInt1() {
        return epzInt1;
    }

    public void setEpzInt1(Integer epzInt1) {
        this.epzInt1 = epzInt1;
    }

    @XmlTransient
    public List<DaneStatR> getDaneStatRList() {
        return daneStatRList;
    }

    public void setDaneStatRList(List<DaneStatR> daneStatRList) {
        this.daneStatRList = daneStatRList;
    }

    public Okres getEpzOkrSerial() {
        return epzOkrSerial;
    }

    public void setEpzOkrSerial(Okres epzOkrSerial) {
        this.epzOkrSerial = epzOkrSerial;
    }

    public Pojazdy getEpzPojSerial() {
        return epzPojSerial;
    }

    public void setEpzPojSerial(Pojazdy epzPojSerial) {
        this.epzPojSerial = epzPojSerial;
    }

    public Rok getEpzRokSerial() {
        return epzRokSerial;
    }

    public void setEpzRokSerial(Rok epzRokSerial) {
        this.epzRokSerial = epzRokSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (epzSerial != null ? epzSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EppZest)) {
            return false;
        }
        EppZest other = (EppZest) object;
        if ((this.epzSerial == null && other.epzSerial != null) || (this.epzSerial != null && !this.epzSerial.equals(other.epzSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.EppZest[ epzSerial=" + epzSerial + " ]";
    }
    
}
