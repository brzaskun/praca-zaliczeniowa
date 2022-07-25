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
@Table(name = "dane_stat_p", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatP.findAll", query = "SELECT d FROM DaneStatP d"),
    @NamedQuery(name = "DaneStatP.findByDspSerial", query = "SELECT d FROM DaneStatP d WHERE d.dspSerial = :dspSerial"),
    @NamedQuery(name = "DaneStatP.findByDspSysSerial", query = "SELECT d FROM DaneStatP d WHERE d.dspSysSerial = :dspSysSerial"),
    @NamedQuery(name = "DaneStatP.findByDspChar1", query = "SELECT d FROM DaneStatP d WHERE d.dspChar1 = :dspChar1"),
    @NamedQuery(name = "DaneStatP.findByDspChar2", query = "SELECT d FROM DaneStatP d WHERE d.dspChar2 = :dspChar2"),
    @NamedQuery(name = "DaneStatP.findByDsp2char1", query = "SELECT d FROM DaneStatP d WHERE d.dsp2char1 = :dsp2char1"),
    @NamedQuery(name = "DaneStatP.findByDsp2char2", query = "SELECT d FROM DaneStatP d WHERE d.dsp2char2 = :dsp2char2"),
    @NamedQuery(name = "DaneStatP.findByDspVchar1", query = "SELECT d FROM DaneStatP d WHERE d.dspVchar1 = :dspVchar1"),
    @NamedQuery(name = "DaneStatP.findByDspVchar2", query = "SELECT d FROM DaneStatP d WHERE d.dspVchar2 = :dspVchar2"),
    @NamedQuery(name = "DaneStatP.findByDspNumeric1", query = "SELECT d FROM DaneStatP d WHERE d.dspNumeric1 = :dspNumeric1"),
    @NamedQuery(name = "DaneStatP.findByDspNumeric2", query = "SELECT d FROM DaneStatP d WHERE d.dspNumeric2 = :dspNumeric2"),
    @NamedQuery(name = "DaneStatP.findByDspNumeric3", query = "SELECT d FROM DaneStatP d WHERE d.dspNumeric3 = :dspNumeric3"),
    @NamedQuery(name = "DaneStatP.findByDspNumeric4", query = "SELECT d FROM DaneStatP d WHERE d.dspNumeric4 = :dspNumeric4"),
    @NamedQuery(name = "DaneStatP.findByDspDate1", query = "SELECT d FROM DaneStatP d WHERE d.dspDate1 = :dspDate1"),
    @NamedQuery(name = "DaneStatP.findByDspDate2", query = "SELECT d FROM DaneStatP d WHERE d.dspDate2 = :dspDate2"),
    @NamedQuery(name = "DaneStatP.findByDspInt1", query = "SELECT d FROM DaneStatP d WHERE d.dspInt1 = :dspInt1"),
    @NamedQuery(name = "DaneStatP.findByDspInt2", query = "SELECT d FROM DaneStatP d WHERE d.dspInt2 = :dspInt2"),
    @NamedQuery(name = "DaneStatP.findByDspInt3", query = "SELECT d FROM DaneStatP d WHERE d.dspInt3 = :dspInt3"),
    @NamedQuery(name = "DaneStatP.findByDspInt4", query = "SELECT d FROM DaneStatP d WHERE d.dspInt4 = :dspInt4"),
    @NamedQuery(name = "DaneStatP.findByDspTime1", query = "SELECT d FROM DaneStatP d WHERE d.dspTime1 = :dspTime1")})
public class DaneStatP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsp_serial", nullable = false)
    private Integer dspSerial;
    @Column(name = "dsp_sys_serial")
    private Integer dspSysSerial;
    @Column(name = "dsp_char_1")
    private Character dspChar1;
    @Column(name = "dsp_char_2")
    private Character dspChar2;
    @Size(max = 2)
    @Column(name = "dsp_2char_1", length = 2)
    private String dsp2char1;
    @Size(max = 2)
    @Column(name = "dsp_2char_2", length = 2)
    private String dsp2char2;
    @Size(max = 128)
    @Column(name = "dsp_vchar_1", length = 128)
    private String dspVchar1;
    @Size(max = 128)
    @Column(name = "dsp_vchar_2", length = 128)
    private String dspVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsp_numeric_1", precision = 17, scale = 6)
    private BigDecimal dspNumeric1;
    @Column(name = "dsp_numeric_2", precision = 17, scale = 6)
    private BigDecimal dspNumeric2;
    @Column(name = "dsp_numeric_3", precision = 17, scale = 6)
    private BigDecimal dspNumeric3;
    @Column(name = "dsp_numeric_4", precision = 17, scale = 6)
    private BigDecimal dspNumeric4;
    @Column(name = "dsp_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dspDate1;
    @Column(name = "dsp_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dspDate2;
    @Column(name = "dsp_int_1")
    private Integer dspInt1;
    @Column(name = "dsp_int_2")
    private Integer dspInt2;
    @Column(name = "dsp_int_3")
    private Integer dspInt3;
    @Column(name = "dsp_int_4")
    private Integer dspInt4;
    @Column(name = "dsp_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dspTime1;
    @JoinColumn(name = "dsp_mpz_serial", referencedColumnName = "mpz_serial")
    @ManyToOne
    private Magpoz dspMpzSerial;
    @JoinColumn(name = "dsp_paz_serial", referencedColumnName = "paz_serial")
    @ManyToOne
    private Parpoz dspPazSerial;
    @JoinColumn(name = "dsp_poz_serial", referencedColumnName = "poz_serial")
    @ManyToOne
    private Pozycja dspPozSerial;
    @JoinColumn(name = "dsp_zpz_serial", referencedColumnName = "zpz_serial")
    @ManyToOne
    private Zakpoz dspZpzSerial;

    public DaneStatP() {
    }

    public DaneStatP(Integer dspSerial) {
        this.dspSerial = dspSerial;
    }

    public Integer getDspSerial() {
        return dspSerial;
    }

    public void setDspSerial(Integer dspSerial) {
        this.dspSerial = dspSerial;
    }

    public Integer getDspSysSerial() {
        return dspSysSerial;
    }

    public void setDspSysSerial(Integer dspSysSerial) {
        this.dspSysSerial = dspSysSerial;
    }

    public Character getDspChar1() {
        return dspChar1;
    }

    public void setDspChar1(Character dspChar1) {
        this.dspChar1 = dspChar1;
    }

    public Character getDspChar2() {
        return dspChar2;
    }

    public void setDspChar2(Character dspChar2) {
        this.dspChar2 = dspChar2;
    }

    public String getDsp2char1() {
        return dsp2char1;
    }

    public void setDsp2char1(String dsp2char1) {
        this.dsp2char1 = dsp2char1;
    }

    public String getDsp2char2() {
        return dsp2char2;
    }

    public void setDsp2char2(String dsp2char2) {
        this.dsp2char2 = dsp2char2;
    }

    public String getDspVchar1() {
        return dspVchar1;
    }

    public void setDspVchar1(String dspVchar1) {
        this.dspVchar1 = dspVchar1;
    }

    public String getDspVchar2() {
        return dspVchar2;
    }

    public void setDspVchar2(String dspVchar2) {
        this.dspVchar2 = dspVchar2;
    }

    public BigDecimal getDspNumeric1() {
        return dspNumeric1;
    }

    public void setDspNumeric1(BigDecimal dspNumeric1) {
        this.dspNumeric1 = dspNumeric1;
    }

    public BigDecimal getDspNumeric2() {
        return dspNumeric2;
    }

    public void setDspNumeric2(BigDecimal dspNumeric2) {
        this.dspNumeric2 = dspNumeric2;
    }

    public BigDecimal getDspNumeric3() {
        return dspNumeric3;
    }

    public void setDspNumeric3(BigDecimal dspNumeric3) {
        this.dspNumeric3 = dspNumeric3;
    }

    public BigDecimal getDspNumeric4() {
        return dspNumeric4;
    }

    public void setDspNumeric4(BigDecimal dspNumeric4) {
        this.dspNumeric4 = dspNumeric4;
    }

    public Date getDspDate1() {
        return dspDate1;
    }

    public void setDspDate1(Date dspDate1) {
        this.dspDate1 = dspDate1;
    }

    public Date getDspDate2() {
        return dspDate2;
    }

    public void setDspDate2(Date dspDate2) {
        this.dspDate2 = dspDate2;
    }

    public Integer getDspInt1() {
        return dspInt1;
    }

    public void setDspInt1(Integer dspInt1) {
        this.dspInt1 = dspInt1;
    }

    public Integer getDspInt2() {
        return dspInt2;
    }

    public void setDspInt2(Integer dspInt2) {
        this.dspInt2 = dspInt2;
    }

    public Integer getDspInt3() {
        return dspInt3;
    }

    public void setDspInt3(Integer dspInt3) {
        this.dspInt3 = dspInt3;
    }

    public Integer getDspInt4() {
        return dspInt4;
    }

    public void setDspInt4(Integer dspInt4) {
        this.dspInt4 = dspInt4;
    }

    public Date getDspTime1() {
        return dspTime1;
    }

    public void setDspTime1(Date dspTime1) {
        this.dspTime1 = dspTime1;
    }

    public Magpoz getDspMpzSerial() {
        return dspMpzSerial;
    }

    public void setDspMpzSerial(Magpoz dspMpzSerial) {
        this.dspMpzSerial = dspMpzSerial;
    }

    public Parpoz getDspPazSerial() {
        return dspPazSerial;
    }

    public void setDspPazSerial(Parpoz dspPazSerial) {
        this.dspPazSerial = dspPazSerial;
    }

    public Pozycja getDspPozSerial() {
        return dspPozSerial;
    }

    public void setDspPozSerial(Pozycja dspPozSerial) {
        this.dspPozSerial = dspPozSerial;
    }

    public Zakpoz getDspZpzSerial() {
        return dspZpzSerial;
    }

    public void setDspZpzSerial(Zakpoz dspZpzSerial) {
        this.dspZpzSerial = dspZpzSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dspSerial != null ? dspSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatP)) {
            return false;
        }
        DaneStatP other = (DaneStatP) object;
        if ((this.dspSerial == null && other.dspSerial != null) || (this.dspSerial != null && !this.dspSerial.equals(other.dspSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatP[ dspSerial=" + dspSerial + " ]";
    }
    
}
