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
@Table(name = "dane_stat_i", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatI.findAll", query = "SELECT d FROM DaneStatI d"),
    @NamedQuery(name = "DaneStatI.findByDsiSerial", query = "SELECT d FROM DaneStatI d WHERE d.dsiSerial = :dsiSerial"),
    @NamedQuery(name = "DaneStatI.findByDsiSysSerial", query = "SELECT d FROM DaneStatI d WHERE d.dsiSysSerial = :dsiSysSerial"),
    @NamedQuery(name = "DaneStatI.findByDsiIntSerial", query = "SELECT d FROM DaneStatI d WHERE d.dsiIntSerial = :dsiIntSerial"),
    @NamedQuery(name = "DaneStatI.findByDsiChar1", query = "SELECT d FROM DaneStatI d WHERE d.dsiChar1 = :dsiChar1"),
    @NamedQuery(name = "DaneStatI.findByDsiChar2", query = "SELECT d FROM DaneStatI d WHERE d.dsiChar2 = :dsiChar2"),
    @NamedQuery(name = "DaneStatI.findByDsi2char1", query = "SELECT d FROM DaneStatI d WHERE d.dsi2char1 = :dsi2char1"),
    @NamedQuery(name = "DaneStatI.findByDsi2char2", query = "SELECT d FROM DaneStatI d WHERE d.dsi2char2 = :dsi2char2"),
    @NamedQuery(name = "DaneStatI.findByDsiVchar1", query = "SELECT d FROM DaneStatI d WHERE d.dsiVchar1 = :dsiVchar1"),
    @NamedQuery(name = "DaneStatI.findByDsiVchar2", query = "SELECT d FROM DaneStatI d WHERE d.dsiVchar2 = :dsiVchar2"),
    @NamedQuery(name = "DaneStatI.findByDsiNumeric1", query = "SELECT d FROM DaneStatI d WHERE d.dsiNumeric1 = :dsiNumeric1"),
    @NamedQuery(name = "DaneStatI.findByDsiNumeric2", query = "SELECT d FROM DaneStatI d WHERE d.dsiNumeric2 = :dsiNumeric2"),
    @NamedQuery(name = "DaneStatI.findByDsiNumeric3", query = "SELECT d FROM DaneStatI d WHERE d.dsiNumeric3 = :dsiNumeric3"),
    @NamedQuery(name = "DaneStatI.findByDsiNumeric4", query = "SELECT d FROM DaneStatI d WHERE d.dsiNumeric4 = :dsiNumeric4"),
    @NamedQuery(name = "DaneStatI.findByDsiInt1", query = "SELECT d FROM DaneStatI d WHERE d.dsiInt1 = :dsiInt1"),
    @NamedQuery(name = "DaneStatI.findByDsiInt2", query = "SELECT d FROM DaneStatI d WHERE d.dsiInt2 = :dsiInt2"),
    @NamedQuery(name = "DaneStatI.findByDsiInt3", query = "SELECT d FROM DaneStatI d WHERE d.dsiInt3 = :dsiInt3"),
    @NamedQuery(name = "DaneStatI.findByDsiInt4", query = "SELECT d FROM DaneStatI d WHERE d.dsiInt4 = :dsiInt4"),
    @NamedQuery(name = "DaneStatI.findByDsiDate1", query = "SELECT d FROM DaneStatI d WHERE d.dsiDate1 = :dsiDate1"),
    @NamedQuery(name = "DaneStatI.findByDsiDate2", query = "SELECT d FROM DaneStatI d WHERE d.dsiDate2 = :dsiDate2"),
    @NamedQuery(name = "DaneStatI.findByDsiTime1", query = "SELECT d FROM DaneStatI d WHERE d.dsiTime1 = :dsiTime1")})
public class DaneStatI implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsi_serial", nullable = false)
    private Integer dsiSerial;
    @Column(name = "dsi_sys_serial")
    private Integer dsiSysSerial;
    @Column(name = "dsi_int_serial")
    private Integer dsiIntSerial;
    @Column(name = "dsi_char_1")
    private Character dsiChar1;
    @Column(name = "dsi_char_2")
    private Character dsiChar2;
    @Size(max = 2)
    @Column(name = "dsi_2char_1", length = 2)
    private String dsi2char1;
    @Size(max = 2)
    @Column(name = "dsi_2char_2", length = 2)
    private String dsi2char2;
    @Size(max = 128)
    @Column(name = "dsi_vchar_1", length = 128)
    private String dsiVchar1;
    @Size(max = 128)
    @Column(name = "dsi_vchar_2", length = 128)
    private String dsiVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsi_numeric_1", precision = 17, scale = 6)
    private BigDecimal dsiNumeric1;
    @Column(name = "dsi_numeric_2", precision = 17, scale = 6)
    private BigDecimal dsiNumeric2;
    @Column(name = "dsi_numeric_3", precision = 17, scale = 6)
    private BigDecimal dsiNumeric3;
    @Column(name = "dsi_numeric_4", precision = 17, scale = 6)
    private BigDecimal dsiNumeric4;
    @Column(name = "dsi_int_1")
    private Integer dsiInt1;
    @Column(name = "dsi_int_2")
    private Integer dsiInt2;
    @Column(name = "dsi_int_3")
    private Integer dsiInt3;
    @Column(name = "dsi_int_4")
    private Integer dsiInt4;
    @Column(name = "dsi_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsiDate1;
    @Column(name = "dsi_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsiDate2;
    @Column(name = "dsi_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsiTime1;

    public DaneStatI() {
    }

    public DaneStatI(Integer dsiSerial) {
        this.dsiSerial = dsiSerial;
    }

    public Integer getDsiSerial() {
        return dsiSerial;
    }

    public void setDsiSerial(Integer dsiSerial) {
        this.dsiSerial = dsiSerial;
    }

    public Integer getDsiSysSerial() {
        return dsiSysSerial;
    }

    public void setDsiSysSerial(Integer dsiSysSerial) {
        this.dsiSysSerial = dsiSysSerial;
    }

    public Integer getDsiIntSerial() {
        return dsiIntSerial;
    }

    public void setDsiIntSerial(Integer dsiIntSerial) {
        this.dsiIntSerial = dsiIntSerial;
    }

    public Character getDsiChar1() {
        return dsiChar1;
    }

    public void setDsiChar1(Character dsiChar1) {
        this.dsiChar1 = dsiChar1;
    }

    public Character getDsiChar2() {
        return dsiChar2;
    }

    public void setDsiChar2(Character dsiChar2) {
        this.dsiChar2 = dsiChar2;
    }

    public String getDsi2char1() {
        return dsi2char1;
    }

    public void setDsi2char1(String dsi2char1) {
        this.dsi2char1 = dsi2char1;
    }

    public String getDsi2char2() {
        return dsi2char2;
    }

    public void setDsi2char2(String dsi2char2) {
        this.dsi2char2 = dsi2char2;
    }

    public String getDsiVchar1() {
        return dsiVchar1;
    }

    public void setDsiVchar1(String dsiVchar1) {
        this.dsiVchar1 = dsiVchar1;
    }

    public String getDsiVchar2() {
        return dsiVchar2;
    }

    public void setDsiVchar2(String dsiVchar2) {
        this.dsiVchar2 = dsiVchar2;
    }

    public BigDecimal getDsiNumeric1() {
        return dsiNumeric1;
    }

    public void setDsiNumeric1(BigDecimal dsiNumeric1) {
        this.dsiNumeric1 = dsiNumeric1;
    }

    public BigDecimal getDsiNumeric2() {
        return dsiNumeric2;
    }

    public void setDsiNumeric2(BigDecimal dsiNumeric2) {
        this.dsiNumeric2 = dsiNumeric2;
    }

    public BigDecimal getDsiNumeric3() {
        return dsiNumeric3;
    }

    public void setDsiNumeric3(BigDecimal dsiNumeric3) {
        this.dsiNumeric3 = dsiNumeric3;
    }

    public BigDecimal getDsiNumeric4() {
        return dsiNumeric4;
    }

    public void setDsiNumeric4(BigDecimal dsiNumeric4) {
        this.dsiNumeric4 = dsiNumeric4;
    }

    public Integer getDsiInt1() {
        return dsiInt1;
    }

    public void setDsiInt1(Integer dsiInt1) {
        this.dsiInt1 = dsiInt1;
    }

    public Integer getDsiInt2() {
        return dsiInt2;
    }

    public void setDsiInt2(Integer dsiInt2) {
        this.dsiInt2 = dsiInt2;
    }

    public Integer getDsiInt3() {
        return dsiInt3;
    }

    public void setDsiInt3(Integer dsiInt3) {
        this.dsiInt3 = dsiInt3;
    }

    public Integer getDsiInt4() {
        return dsiInt4;
    }

    public void setDsiInt4(Integer dsiInt4) {
        this.dsiInt4 = dsiInt4;
    }

    public Date getDsiDate1() {
        return dsiDate1;
    }

    public void setDsiDate1(Date dsiDate1) {
        this.dsiDate1 = dsiDate1;
    }

    public Date getDsiDate2() {
        return dsiDate2;
    }

    public void setDsiDate2(Date dsiDate2) {
        this.dsiDate2 = dsiDate2;
    }

    public Date getDsiTime1() {
        return dsiTime1;
    }

    public void setDsiTime1(Date dsiTime1) {
        this.dsiTime1 = dsiTime1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dsiSerial != null ? dsiSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatI)) {
            return false;
        }
        DaneStatI other = (DaneStatI) object;
        if ((this.dsiSerial == null && other.dsiSerial != null) || (this.dsiSerial != null && !this.dsiSerial.equals(other.dsiSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatI[ dsiSerial=" + dsiSerial + " ]";
    }
    
}
