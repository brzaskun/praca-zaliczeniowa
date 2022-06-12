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
@Table(name = "dane_stat_sc", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatSc.findAll", query = "SELECT d FROM DaneStatSc d"),
    @NamedQuery(name = "DaneStatSc.findByDscSerial", query = "SELECT d FROM DaneStatSc d WHERE d.dscSerial = :dscSerial"),
    @NamedQuery(name = "DaneStatSc.findByDscSysSerial", query = "SELECT d FROM DaneStatSc d WHERE d.dscSysSerial = :dscSysSerial"),
    @NamedQuery(name = "DaneStatSc.findByDscChar1", query = "SELECT d FROM DaneStatSc d WHERE d.dscChar1 = :dscChar1"),
    @NamedQuery(name = "DaneStatSc.findByDscChar2", query = "SELECT d FROM DaneStatSc d WHERE d.dscChar2 = :dscChar2"),
    @NamedQuery(name = "DaneStatSc.findByDsc2char1", query = "SELECT d FROM DaneStatSc d WHERE d.dsc2char1 = :dsc2char1"),
    @NamedQuery(name = "DaneStatSc.findByDsc2char2", query = "SELECT d FROM DaneStatSc d WHERE d.dsc2char2 = :dsc2char2"),
    @NamedQuery(name = "DaneStatSc.findByDscVchar1", query = "SELECT d FROM DaneStatSc d WHERE d.dscVchar1 = :dscVchar1"),
    @NamedQuery(name = "DaneStatSc.findByDscVchar2", query = "SELECT d FROM DaneStatSc d WHERE d.dscVchar2 = :dscVchar2"),
    @NamedQuery(name = "DaneStatSc.findByDscNumeric1", query = "SELECT d FROM DaneStatSc d WHERE d.dscNumeric1 = :dscNumeric1"),
    @NamedQuery(name = "DaneStatSc.findByDscNumeric2", query = "SELECT d FROM DaneStatSc d WHERE d.dscNumeric2 = :dscNumeric2"),
    @NamedQuery(name = "DaneStatSc.findByDscNumeric3", query = "SELECT d FROM DaneStatSc d WHERE d.dscNumeric3 = :dscNumeric3"),
    @NamedQuery(name = "DaneStatSc.findByDscNumeric4", query = "SELECT d FROM DaneStatSc d WHERE d.dscNumeric4 = :dscNumeric4"),
    @NamedQuery(name = "DaneStatSc.findByDscDate1", query = "SELECT d FROM DaneStatSc d WHERE d.dscDate1 = :dscDate1"),
    @NamedQuery(name = "DaneStatSc.findByDscDate2", query = "SELECT d FROM DaneStatSc d WHERE d.dscDate2 = :dscDate2"),
    @NamedQuery(name = "DaneStatSc.findByDscInt1", query = "SELECT d FROM DaneStatSc d WHERE d.dscInt1 = :dscInt1"),
    @NamedQuery(name = "DaneStatSc.findByDscInt2", query = "SELECT d FROM DaneStatSc d WHERE d.dscInt2 = :dscInt2"),
    @NamedQuery(name = "DaneStatSc.findByDscInt3", query = "SELECT d FROM DaneStatSc d WHERE d.dscInt3 = :dscInt3"),
    @NamedQuery(name = "DaneStatSc.findByDscInt4", query = "SELECT d FROM DaneStatSc d WHERE d.dscInt4 = :dscInt4"),
    @NamedQuery(name = "DaneStatSc.findByDscTime1", query = "SELECT d FROM DaneStatSc d WHERE d.dscTime1 = :dscTime1")})
public class DaneStatSc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsc_serial", nullable = false)
    private Integer dscSerial;
    @Column(name = "dsc_sys_serial")
    private Integer dscSysSerial;
    @Column(name = "dsc_char_1")
    private Character dscChar1;
    @Column(name = "dsc_char_2")
    private Character dscChar2;
    @Size(max = 2)
    @Column(name = "dsc_2char_1", length = 2)
    private String dsc2char1;
    @Size(max = 2)
    @Column(name = "dsc_2char_2", length = 2)
    private String dsc2char2;
    @Size(max = 128)
    @Column(name = "dsc_vchar_1", length = 128)
    private String dscVchar1;
    @Size(max = 128)
    @Column(name = "dsc_vchar_2", length = 128)
    private String dscVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsc_numeric_1", precision = 17, scale = 6)
    private BigDecimal dscNumeric1;
    @Column(name = "dsc_numeric_2", precision = 17, scale = 6)
    private BigDecimal dscNumeric2;
    @Column(name = "dsc_numeric_3", precision = 17, scale = 6)
    private BigDecimal dscNumeric3;
    @Column(name = "dsc_numeric_4", precision = 17, scale = 6)
    private BigDecimal dscNumeric4;
    @Column(name = "dsc_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dscDate1;
    @Column(name = "dsc_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dscDate2;
    @Column(name = "dsc_int_1")
    private Integer dscInt1;
    @Column(name = "dsc_int_2")
    private Integer dscInt2;
    @Column(name = "dsc_int_3")
    private Integer dscInt3;
    @Column(name = "dsc_int_4")
    private Integer dscInt4;
    @Column(name = "dsc_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dscTime1;
    @JoinColumn(name = "dsc_sck_serial", referencedColumnName = "sck_serial")
    @ManyToOne
    private ScKamp dscSckSerial;
    @JoinColumn(name = "dsc_scm_serial", referencedColumnName = "scm_serial")
    @ManyToOne
    private ScMag dscScmSerial;
    @JoinColumn(name = "dsc_scp_serial", referencedColumnName = "scp_serial")
    @ManyToOne
    private ScPromo dscScpSerial;

    public DaneStatSc() {
    }

    public DaneStatSc(Integer dscSerial) {
        this.dscSerial = dscSerial;
    }

    public Integer getDscSerial() {
        return dscSerial;
    }

    public void setDscSerial(Integer dscSerial) {
        this.dscSerial = dscSerial;
    }

    public Integer getDscSysSerial() {
        return dscSysSerial;
    }

    public void setDscSysSerial(Integer dscSysSerial) {
        this.dscSysSerial = dscSysSerial;
    }

    public Character getDscChar1() {
        return dscChar1;
    }

    public void setDscChar1(Character dscChar1) {
        this.dscChar1 = dscChar1;
    }

    public Character getDscChar2() {
        return dscChar2;
    }

    public void setDscChar2(Character dscChar2) {
        this.dscChar2 = dscChar2;
    }

    public String getDsc2char1() {
        return dsc2char1;
    }

    public void setDsc2char1(String dsc2char1) {
        this.dsc2char1 = dsc2char1;
    }

    public String getDsc2char2() {
        return dsc2char2;
    }

    public void setDsc2char2(String dsc2char2) {
        this.dsc2char2 = dsc2char2;
    }

    public String getDscVchar1() {
        return dscVchar1;
    }

    public void setDscVchar1(String dscVchar1) {
        this.dscVchar1 = dscVchar1;
    }

    public String getDscVchar2() {
        return dscVchar2;
    }

    public void setDscVchar2(String dscVchar2) {
        this.dscVchar2 = dscVchar2;
    }

    public BigDecimal getDscNumeric1() {
        return dscNumeric1;
    }

    public void setDscNumeric1(BigDecimal dscNumeric1) {
        this.dscNumeric1 = dscNumeric1;
    }

    public BigDecimal getDscNumeric2() {
        return dscNumeric2;
    }

    public void setDscNumeric2(BigDecimal dscNumeric2) {
        this.dscNumeric2 = dscNumeric2;
    }

    public BigDecimal getDscNumeric3() {
        return dscNumeric3;
    }

    public void setDscNumeric3(BigDecimal dscNumeric3) {
        this.dscNumeric3 = dscNumeric3;
    }

    public BigDecimal getDscNumeric4() {
        return dscNumeric4;
    }

    public void setDscNumeric4(BigDecimal dscNumeric4) {
        this.dscNumeric4 = dscNumeric4;
    }

    public Date getDscDate1() {
        return dscDate1;
    }

    public void setDscDate1(Date dscDate1) {
        this.dscDate1 = dscDate1;
    }

    public Date getDscDate2() {
        return dscDate2;
    }

    public void setDscDate2(Date dscDate2) {
        this.dscDate2 = dscDate2;
    }

    public Integer getDscInt1() {
        return dscInt1;
    }

    public void setDscInt1(Integer dscInt1) {
        this.dscInt1 = dscInt1;
    }

    public Integer getDscInt2() {
        return dscInt2;
    }

    public void setDscInt2(Integer dscInt2) {
        this.dscInt2 = dscInt2;
    }

    public Integer getDscInt3() {
        return dscInt3;
    }

    public void setDscInt3(Integer dscInt3) {
        this.dscInt3 = dscInt3;
    }

    public Integer getDscInt4() {
        return dscInt4;
    }

    public void setDscInt4(Integer dscInt4) {
        this.dscInt4 = dscInt4;
    }

    public Date getDscTime1() {
        return dscTime1;
    }

    public void setDscTime1(Date dscTime1) {
        this.dscTime1 = dscTime1;
    }

    public ScKamp getDscSckSerial() {
        return dscSckSerial;
    }

    public void setDscSckSerial(ScKamp dscSckSerial) {
        this.dscSckSerial = dscSckSerial;
    }

    public ScMag getDscScmSerial() {
        return dscScmSerial;
    }

    public void setDscScmSerial(ScMag dscScmSerial) {
        this.dscScmSerial = dscScmSerial;
    }

    public ScPromo getDscScpSerial() {
        return dscScpSerial;
    }

    public void setDscScpSerial(ScPromo dscScpSerial) {
        this.dscScpSerial = dscScpSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dscSerial != null ? dscSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatSc)) {
            return false;
        }
        DaneStatSc other = (DaneStatSc) object;
        if ((this.dscSerial == null && other.dscSerial != null) || (this.dscSerial != null && !this.dscSerial.equals(other.dscSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatSc[ dscSerial=" + dscSerial + " ]";
    }
    
}
