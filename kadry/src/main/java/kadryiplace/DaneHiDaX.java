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
@Table(name = "dane_hi_da_x", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneHiDaX.findAll", query = "SELECT d FROM DaneHiDaX d"),
    @NamedQuery(name = "DaneHiDaX.findByDhxSerial", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxSerial = :dhxSerial"),
    @NamedQuery(name = "DaneHiDaX.findByDhxSysSerial", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxSysSerial = :dhxSysSerial"),
    @NamedQuery(name = "DaneHiDaX.findByDhxIntSerial", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxIntSerial = :dhxIntSerial"),
    @NamedQuery(name = "DaneHiDaX.findByDhxDataOd", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxDataOd = :dhxDataOd"),
    @NamedQuery(name = "DaneHiDaX.findByDhxDataDo", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxDataDo = :dhxDataDo"),
    @NamedQuery(name = "DaneHiDaX.findByDhxChar1", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxChar1 = :dhxChar1"),
    @NamedQuery(name = "DaneHiDaX.findByDhxChar2", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxChar2 = :dhxChar2"),
    @NamedQuery(name = "DaneHiDaX.findByDhxChar3", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxChar3 = :dhxChar3"),
    @NamedQuery(name = "DaneHiDaX.findByDhxChar4", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxChar4 = :dhxChar4"),
    @NamedQuery(name = "DaneHiDaX.findByDhxVchar1", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxVchar1 = :dhxVchar1"),
    @NamedQuery(name = "DaneHiDaX.findByDhxVchar2", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxVchar2 = :dhxVchar2"),
    @NamedQuery(name = "DaneHiDaX.findByDhxVchar3", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxVchar3 = :dhxVchar3"),
    @NamedQuery(name = "DaneHiDaX.findByDhxVchar4", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxVchar4 = :dhxVchar4"),
    @NamedQuery(name = "DaneHiDaX.findByDhxDate1", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxDate1 = :dhxDate1"),
    @NamedQuery(name = "DaneHiDaX.findByDhxDate2", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxDate2 = :dhxDate2"),
    @NamedQuery(name = "DaneHiDaX.findByDhxDate3", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxDate3 = :dhxDate3"),
    @NamedQuery(name = "DaneHiDaX.findByDhxDate4", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxDate4 = :dhxDate4"),
    @NamedQuery(name = "DaneHiDaX.findByDhxInt1", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxInt1 = :dhxInt1"),
    @NamedQuery(name = "DaneHiDaX.findByDhxInt2", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxInt2 = :dhxInt2"),
    @NamedQuery(name = "DaneHiDaX.findByDhxInt3", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxInt3 = :dhxInt3"),
    @NamedQuery(name = "DaneHiDaX.findByDhxInt4", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxInt4 = :dhxInt4"),
    @NamedQuery(name = "DaneHiDaX.findByDhxNumeric1", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxNumeric1 = :dhxNumeric1"),
    @NamedQuery(name = "DaneHiDaX.findByDhxNumeric2", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxNumeric2 = :dhxNumeric2"),
    @NamedQuery(name = "DaneHiDaX.findByDhxNumeric3", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxNumeric3 = :dhxNumeric3"),
    @NamedQuery(name = "DaneHiDaX.findByDhxNumeric4", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxNumeric4 = :dhxNumeric4"),
    @NamedQuery(name = "DaneHiDaX.findByDhx2char1", query = "SELECT d FROM DaneHiDaX d WHERE d.dhx2char1 = :dhx2char1"),
    @NamedQuery(name = "DaneHiDaX.findByDhx2char2", query = "SELECT d FROM DaneHiDaX d WHERE d.dhx2char2 = :dhx2char2"),
    @NamedQuery(name = "DaneHiDaX.findByDhxRecord", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxRecord = :dhxRecord"),
    @NamedQuery(name = "DaneHiDaX.findByDhxStatus", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxStatus = :dhxStatus"),
    @NamedQuery(name = "DaneHiDaX.findByDhxTime1", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxTime1 = :dhxTime1"),
    @NamedQuery(name = "DaneHiDaX.findByDhxTime2", query = "SELECT d FROM DaneHiDaX d WHERE d.dhxTime2 = :dhxTime2")})
public class DaneHiDaX implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dhx_serial", nullable = false)
    private Integer dhxSerial;
    @Column(name = "dhx_sys_serial")
    private Integer dhxSysSerial;
    @Column(name = "dhx_int_serial")
    private Integer dhxIntSerial;
    @Column(name = "dhx_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhxDataOd;
    @Column(name = "dhx_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhxDataDo;
    @Column(name = "dhx_char_1")
    private Character dhxChar1;
    @Column(name = "dhx_char_2")
    private Character dhxChar2;
    @Column(name = "dhx_char_3")
    private Character dhxChar3;
    @Column(name = "dhx_char_4")
    private Character dhxChar4;
    @Size(max = 254)
    @Column(name = "dhx_vchar_1", length = 254)
    private String dhxVchar1;
    @Size(max = 254)
    @Column(name = "dhx_vchar_2", length = 254)
    private String dhxVchar2;
    @Size(max = 254)
    @Column(name = "dhx_vchar_3", length = 254)
    private String dhxVchar3;
    @Size(max = 254)
    @Column(name = "dhx_vchar_4", length = 254)
    private String dhxVchar4;
    @Column(name = "dhx_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhxDate1;
    @Column(name = "dhx_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhxDate2;
    @Column(name = "dhx_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhxDate3;
    @Column(name = "dhx_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhxDate4;
    @Column(name = "dhx_int_1")
    private Integer dhxInt1;
    @Column(name = "dhx_int_2")
    private Integer dhxInt2;
    @Column(name = "dhx_int_3")
    private Integer dhxInt3;
    @Column(name = "dhx_int_4")
    private Integer dhxInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dhx_numeric_1", precision = 17, scale = 6)
    private BigDecimal dhxNumeric1;
    @Column(name = "dhx_numeric_2", precision = 17, scale = 6)
    private BigDecimal dhxNumeric2;
    @Column(name = "dhx_numeric_3", precision = 17, scale = 6)
    private BigDecimal dhxNumeric3;
    @Column(name = "dhx_numeric_4", precision = 17, scale = 6)
    private BigDecimal dhxNumeric4;
    @Size(max = 2)
    @Column(name = "dhx_2char_1", length = 2)
    private String dhx2char1;
    @Size(max = 2)
    @Column(name = "dhx_2char_2", length = 2)
    private String dhx2char2;
    @Size(max = 4)
    @Column(name = "dhx_record", length = 4)
    private String dhxRecord;
    @Column(name = "dhx_status")
    private Character dhxStatus;
    @Column(name = "dhx_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhxTime1;
    @Column(name = "dhx_time_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhxTime2;
    @JoinColumn(name = "dhx_dls_serial", referencedColumnName = "dls_serial")
    @ManyToOne
    private DaneLiSl dhxDlsSerial;

    public DaneHiDaX() {
    }

    public DaneHiDaX(Integer dhxSerial) {
        this.dhxSerial = dhxSerial;
    }

    public Integer getDhxSerial() {
        return dhxSerial;
    }

    public void setDhxSerial(Integer dhxSerial) {
        this.dhxSerial = dhxSerial;
    }

    public Integer getDhxSysSerial() {
        return dhxSysSerial;
    }

    public void setDhxSysSerial(Integer dhxSysSerial) {
        this.dhxSysSerial = dhxSysSerial;
    }

    public Integer getDhxIntSerial() {
        return dhxIntSerial;
    }

    public void setDhxIntSerial(Integer dhxIntSerial) {
        this.dhxIntSerial = dhxIntSerial;
    }

    public Date getDhxDataOd() {
        return dhxDataOd;
    }

    public void setDhxDataOd(Date dhxDataOd) {
        this.dhxDataOd = dhxDataOd;
    }

    public Date getDhxDataDo() {
        return dhxDataDo;
    }

    public void setDhxDataDo(Date dhxDataDo) {
        this.dhxDataDo = dhxDataDo;
    }

    public Character getDhxChar1() {
        return dhxChar1;
    }

    public void setDhxChar1(Character dhxChar1) {
        this.dhxChar1 = dhxChar1;
    }

    public Character getDhxChar2() {
        return dhxChar2;
    }

    public void setDhxChar2(Character dhxChar2) {
        this.dhxChar2 = dhxChar2;
    }

    public Character getDhxChar3() {
        return dhxChar3;
    }

    public void setDhxChar3(Character dhxChar3) {
        this.dhxChar3 = dhxChar3;
    }

    public Character getDhxChar4() {
        return dhxChar4;
    }

    public void setDhxChar4(Character dhxChar4) {
        this.dhxChar4 = dhxChar4;
    }

    public String getDhxVchar1() {
        return dhxVchar1;
    }

    public void setDhxVchar1(String dhxVchar1) {
        this.dhxVchar1 = dhxVchar1;
    }

    public String getDhxVchar2() {
        return dhxVchar2;
    }

    public void setDhxVchar2(String dhxVchar2) {
        this.dhxVchar2 = dhxVchar2;
    }

    public String getDhxVchar3() {
        return dhxVchar3;
    }

    public void setDhxVchar3(String dhxVchar3) {
        this.dhxVchar3 = dhxVchar3;
    }

    public String getDhxVchar4() {
        return dhxVchar4;
    }

    public void setDhxVchar4(String dhxVchar4) {
        this.dhxVchar4 = dhxVchar4;
    }

    public Date getDhxDate1() {
        return dhxDate1;
    }

    public void setDhxDate1(Date dhxDate1) {
        this.dhxDate1 = dhxDate1;
    }

    public Date getDhxDate2() {
        return dhxDate2;
    }

    public void setDhxDate2(Date dhxDate2) {
        this.dhxDate2 = dhxDate2;
    }

    public Date getDhxDate3() {
        return dhxDate3;
    }

    public void setDhxDate3(Date dhxDate3) {
        this.dhxDate3 = dhxDate3;
    }

    public Date getDhxDate4() {
        return dhxDate4;
    }

    public void setDhxDate4(Date dhxDate4) {
        this.dhxDate4 = dhxDate4;
    }

    public Integer getDhxInt1() {
        return dhxInt1;
    }

    public void setDhxInt1(Integer dhxInt1) {
        this.dhxInt1 = dhxInt1;
    }

    public Integer getDhxInt2() {
        return dhxInt2;
    }

    public void setDhxInt2(Integer dhxInt2) {
        this.dhxInt2 = dhxInt2;
    }

    public Integer getDhxInt3() {
        return dhxInt3;
    }

    public void setDhxInt3(Integer dhxInt3) {
        this.dhxInt3 = dhxInt3;
    }

    public Integer getDhxInt4() {
        return dhxInt4;
    }

    public void setDhxInt4(Integer dhxInt4) {
        this.dhxInt4 = dhxInt4;
    }

    public BigDecimal getDhxNumeric1() {
        return dhxNumeric1;
    }

    public void setDhxNumeric1(BigDecimal dhxNumeric1) {
        this.dhxNumeric1 = dhxNumeric1;
    }

    public BigDecimal getDhxNumeric2() {
        return dhxNumeric2;
    }

    public void setDhxNumeric2(BigDecimal dhxNumeric2) {
        this.dhxNumeric2 = dhxNumeric2;
    }

    public BigDecimal getDhxNumeric3() {
        return dhxNumeric3;
    }

    public void setDhxNumeric3(BigDecimal dhxNumeric3) {
        this.dhxNumeric3 = dhxNumeric3;
    }

    public BigDecimal getDhxNumeric4() {
        return dhxNumeric4;
    }

    public void setDhxNumeric4(BigDecimal dhxNumeric4) {
        this.dhxNumeric4 = dhxNumeric4;
    }

    public String getDhx2char1() {
        return dhx2char1;
    }

    public void setDhx2char1(String dhx2char1) {
        this.dhx2char1 = dhx2char1;
    }

    public String getDhx2char2() {
        return dhx2char2;
    }

    public void setDhx2char2(String dhx2char2) {
        this.dhx2char2 = dhx2char2;
    }

    public String getDhxRecord() {
        return dhxRecord;
    }

    public void setDhxRecord(String dhxRecord) {
        this.dhxRecord = dhxRecord;
    }

    public Character getDhxStatus() {
        return dhxStatus;
    }

    public void setDhxStatus(Character dhxStatus) {
        this.dhxStatus = dhxStatus;
    }

    public Date getDhxTime1() {
        return dhxTime1;
    }

    public void setDhxTime1(Date dhxTime1) {
        this.dhxTime1 = dhxTime1;
    }

    public Date getDhxTime2() {
        return dhxTime2;
    }

    public void setDhxTime2(Date dhxTime2) {
        this.dhxTime2 = dhxTime2;
    }

    public DaneLiSl getDhxDlsSerial() {
        return dhxDlsSerial;
    }

    public void setDhxDlsSerial(DaneLiSl dhxDlsSerial) {
        this.dhxDlsSerial = dhxDlsSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dhxSerial != null ? dhxSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneHiDaX)) {
            return false;
        }
        DaneHiDaX other = (DaneHiDaX) object;
        if ((this.dhxSerial == null && other.dhxSerial != null) || (this.dhxSerial != null && !this.dhxSerial.equals(other.dhxSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneHiDaX[ dhxSerial=" + dhxSerial + " ]";
    }
    
}
