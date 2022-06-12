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
@Table(name = "dane_stat_ix", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatIx.findAll", query = "SELECT d FROM DaneStatIx d"),
    @NamedQuery(name = "DaneStatIx.findByDsxSerial", query = "SELECT d FROM DaneStatIx d WHERE d.dsxSerial = :dsxSerial"),
    @NamedQuery(name = "DaneStatIx.findByDsxSysSerial", query = "SELECT d FROM DaneStatIx d WHERE d.dsxSysSerial = :dsxSysSerial"),
    @NamedQuery(name = "DaneStatIx.findByDsxIntSerial", query = "SELECT d FROM DaneStatIx d WHERE d.dsxIntSerial = :dsxIntSerial"),
    @NamedQuery(name = "DaneStatIx.findByDsxChar1", query = "SELECT d FROM DaneStatIx d WHERE d.dsxChar1 = :dsxChar1"),
    @NamedQuery(name = "DaneStatIx.findByDsxChar2", query = "SELECT d FROM DaneStatIx d WHERE d.dsxChar2 = :dsxChar2"),
    @NamedQuery(name = "DaneStatIx.findByDsxVchar1", query = "SELECT d FROM DaneStatIx d WHERE d.dsxVchar1 = :dsxVchar1"),
    @NamedQuery(name = "DaneStatIx.findByDsxNumeric1", query = "SELECT d FROM DaneStatIx d WHERE d.dsxNumeric1 = :dsxNumeric1"),
    @NamedQuery(name = "DaneStatIx.findByDsxInt1", query = "SELECT d FROM DaneStatIx d WHERE d.dsxInt1 = :dsxInt1"),
    @NamedQuery(name = "DaneStatIx.findByDsxDate1", query = "SELECT d FROM DaneStatIx d WHERE d.dsxDate1 = :dsxDate1"),
    @NamedQuery(name = "DaneStatIx.findByDsxTime1", query = "SELECT d FROM DaneStatIx d WHERE d.dsxTime1 = :dsxTime1")})
public class DaneStatIx implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsx_serial", nullable = false)
    private Integer dsxSerial;
    @Column(name = "dsx_sys_serial")
    private Integer dsxSysSerial;
    @Column(name = "dsx_int_serial")
    private Integer dsxIntSerial;
    @Column(name = "dsx_char_1")
    private Character dsxChar1;
    @Column(name = "dsx_char_2")
    private Character dsxChar2;
    @Size(max = 128)
    @Column(name = "dsx_vchar_1", length = 128)
    private String dsxVchar1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsx_numeric_1", precision = 17, scale = 6)
    private BigDecimal dsxNumeric1;
    @Column(name = "dsx_int_1")
    private Integer dsxInt1;
    @Column(name = "dsx_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsxDate1;
    @Column(name = "dsx_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsxTime1;

    public DaneStatIx() {
    }

    public DaneStatIx(Integer dsxSerial) {
        this.dsxSerial = dsxSerial;
    }

    public Integer getDsxSerial() {
        return dsxSerial;
    }

    public void setDsxSerial(Integer dsxSerial) {
        this.dsxSerial = dsxSerial;
    }

    public Integer getDsxSysSerial() {
        return dsxSysSerial;
    }

    public void setDsxSysSerial(Integer dsxSysSerial) {
        this.dsxSysSerial = dsxSysSerial;
    }

    public Integer getDsxIntSerial() {
        return dsxIntSerial;
    }

    public void setDsxIntSerial(Integer dsxIntSerial) {
        this.dsxIntSerial = dsxIntSerial;
    }

    public Character getDsxChar1() {
        return dsxChar1;
    }

    public void setDsxChar1(Character dsxChar1) {
        this.dsxChar1 = dsxChar1;
    }

    public Character getDsxChar2() {
        return dsxChar2;
    }

    public void setDsxChar2(Character dsxChar2) {
        this.dsxChar2 = dsxChar2;
    }

    public String getDsxVchar1() {
        return dsxVchar1;
    }

    public void setDsxVchar1(String dsxVchar1) {
        this.dsxVchar1 = dsxVchar1;
    }

    public BigDecimal getDsxNumeric1() {
        return dsxNumeric1;
    }

    public void setDsxNumeric1(BigDecimal dsxNumeric1) {
        this.dsxNumeric1 = dsxNumeric1;
    }

    public Integer getDsxInt1() {
        return dsxInt1;
    }

    public void setDsxInt1(Integer dsxInt1) {
        this.dsxInt1 = dsxInt1;
    }

    public Date getDsxDate1() {
        return dsxDate1;
    }

    public void setDsxDate1(Date dsxDate1) {
        this.dsxDate1 = dsxDate1;
    }

    public Date getDsxTime1() {
        return dsxTime1;
    }

    public void setDsxTime1(Date dsxTime1) {
        this.dsxTime1 = dsxTime1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dsxSerial != null ? dsxSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatIx)) {
            return false;
        }
        DaneStatIx other = (DaneStatIx) object;
        if ((this.dsxSerial == null && other.dsxSerial != null) || (this.dsxSerial != null && !this.dsxSerial.equals(other.dsxSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatIx[ dsxSerial=" + dsxSerial + " ]";
    }
    
}
