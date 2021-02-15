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
@Table(name = "dane_li_da_x", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneLiDaX.findAll", query = "SELECT d FROM DaneLiDaX d"),
    @NamedQuery(name = "DaneLiDaX.findByDlxSerial", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxSerial = :dlxSerial"),
    @NamedQuery(name = "DaneLiDaX.findByDlxSysSerial", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxSysSerial = :dlxSysSerial"),
    @NamedQuery(name = "DaneLiDaX.findByDlxIntSerial", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxIntSerial = :dlxIntSerial"),
    @NamedQuery(name = "DaneLiDaX.findByDlxChar1", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxChar1 = :dlxChar1"),
    @NamedQuery(name = "DaneLiDaX.findByDlxChar2", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxChar2 = :dlxChar2"),
    @NamedQuery(name = "DaneLiDaX.findByDlxChar3", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxChar3 = :dlxChar3"),
    @NamedQuery(name = "DaneLiDaX.findByDlxChar4", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxChar4 = :dlxChar4"),
    @NamedQuery(name = "DaneLiDaX.findByDlxVchar1", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxVchar1 = :dlxVchar1"),
    @NamedQuery(name = "DaneLiDaX.findByDlxVchar2", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxVchar2 = :dlxVchar2"),
    @NamedQuery(name = "DaneLiDaX.findByDlxVchar3", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxVchar3 = :dlxVchar3"),
    @NamedQuery(name = "DaneLiDaX.findByDlxVchar4", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxVchar4 = :dlxVchar4"),
    @NamedQuery(name = "DaneLiDaX.findByDlxDate1", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxDate1 = :dlxDate1"),
    @NamedQuery(name = "DaneLiDaX.findByDlxDate2", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxDate2 = :dlxDate2"),
    @NamedQuery(name = "DaneLiDaX.findByDlxDate3", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxDate3 = :dlxDate3"),
    @NamedQuery(name = "DaneLiDaX.findByDlxDate4", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxDate4 = :dlxDate4"),
    @NamedQuery(name = "DaneLiDaX.findByDlxInt1", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxInt1 = :dlxInt1"),
    @NamedQuery(name = "DaneLiDaX.findByDlxInt2", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxInt2 = :dlxInt2"),
    @NamedQuery(name = "DaneLiDaX.findByDlxInt3", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxInt3 = :dlxInt3"),
    @NamedQuery(name = "DaneLiDaX.findByDlxInt4", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxInt4 = :dlxInt4"),
    @NamedQuery(name = "DaneLiDaX.findByDlxNumeric1", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxNumeric1 = :dlxNumeric1"),
    @NamedQuery(name = "DaneLiDaX.findByDlxNumeric2", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxNumeric2 = :dlxNumeric2"),
    @NamedQuery(name = "DaneLiDaX.findByDlxNumeric3", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxNumeric3 = :dlxNumeric3"),
    @NamedQuery(name = "DaneLiDaX.findByDlxNumeric4", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxNumeric4 = :dlxNumeric4"),
    @NamedQuery(name = "DaneLiDaX.findByDlx2char1", query = "SELECT d FROM DaneLiDaX d WHERE d.dlx2char1 = :dlx2char1"),
    @NamedQuery(name = "DaneLiDaX.findByDlx2char2", query = "SELECT d FROM DaneLiDaX d WHERE d.dlx2char2 = :dlx2char2"),
    @NamedQuery(name = "DaneLiDaX.findByDlxRecord", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxRecord = :dlxRecord"),
    @NamedQuery(name = "DaneLiDaX.findByDlxStatus", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxStatus = :dlxStatus"),
    @NamedQuery(name = "DaneLiDaX.findByDlxTime1", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxTime1 = :dlxTime1"),
    @NamedQuery(name = "DaneLiDaX.findByDlxTime2", query = "SELECT d FROM DaneLiDaX d WHERE d.dlxTime2 = :dlxTime2")})
public class DaneLiDaX implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dlx_serial", nullable = false)
    private Integer dlxSerial;
    @Column(name = "dlx_sys_serial")
    private Integer dlxSysSerial;
    @Column(name = "dlx_int_serial")
    private Integer dlxIntSerial;
    @Column(name = "dlx_char_1")
    private Character dlxChar1;
    @Column(name = "dlx_char_2")
    private Character dlxChar2;
    @Column(name = "dlx_char_3")
    private Character dlxChar3;
    @Column(name = "dlx_char_4")
    private Character dlxChar4;
    @Size(max = 254)
    @Column(name = "dlx_vchar_1", length = 254)
    private String dlxVchar1;
    @Size(max = 254)
    @Column(name = "dlx_vchar_2", length = 254)
    private String dlxVchar2;
    @Size(max = 254)
    @Column(name = "dlx_vchar_3", length = 254)
    private String dlxVchar3;
    @Size(max = 254)
    @Column(name = "dlx_vchar_4", length = 254)
    private String dlxVchar4;
    @Column(name = "dlx_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlxDate1;
    @Column(name = "dlx_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlxDate2;
    @Column(name = "dlx_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlxDate3;
    @Column(name = "dlx_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlxDate4;
    @Column(name = "dlx_int_1")
    private Integer dlxInt1;
    @Column(name = "dlx_int_2")
    private Integer dlxInt2;
    @Column(name = "dlx_int_3")
    private Integer dlxInt3;
    @Column(name = "dlx_int_4")
    private Integer dlxInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dlx_numeric_1", precision = 17, scale = 6)
    private BigDecimal dlxNumeric1;
    @Column(name = "dlx_numeric_2", precision = 17, scale = 6)
    private BigDecimal dlxNumeric2;
    @Column(name = "dlx_numeric_3", precision = 17, scale = 6)
    private BigDecimal dlxNumeric3;
    @Column(name = "dlx_numeric_4", precision = 17, scale = 6)
    private BigDecimal dlxNumeric4;
    @Size(max = 2)
    @Column(name = "dlx_2char_1", length = 2)
    private String dlx2char1;
    @Size(max = 2)
    @Column(name = "dlx_2char_2", length = 2)
    private String dlx2char2;
    @Size(max = 4)
    @Column(name = "dlx_record", length = 4)
    private String dlxRecord;
    @Column(name = "dlx_status")
    private Character dlxStatus;
    @Column(name = "dlx_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlxTime1;
    @Column(name = "dlx_time_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlxTime2;
    @JoinColumn(name = "dlx_dls_serial", referencedColumnName = "dls_serial")
    @ManyToOne
    private DaneLiSl dlxDlsSerial;

    public DaneLiDaX() {
    }

    public DaneLiDaX(Integer dlxSerial) {
        this.dlxSerial = dlxSerial;
    }

    public Integer getDlxSerial() {
        return dlxSerial;
    }

    public void setDlxSerial(Integer dlxSerial) {
        this.dlxSerial = dlxSerial;
    }

    public Integer getDlxSysSerial() {
        return dlxSysSerial;
    }

    public void setDlxSysSerial(Integer dlxSysSerial) {
        this.dlxSysSerial = dlxSysSerial;
    }

    public Integer getDlxIntSerial() {
        return dlxIntSerial;
    }

    public void setDlxIntSerial(Integer dlxIntSerial) {
        this.dlxIntSerial = dlxIntSerial;
    }

    public Character getDlxChar1() {
        return dlxChar1;
    }

    public void setDlxChar1(Character dlxChar1) {
        this.dlxChar1 = dlxChar1;
    }

    public Character getDlxChar2() {
        return dlxChar2;
    }

    public void setDlxChar2(Character dlxChar2) {
        this.dlxChar2 = dlxChar2;
    }

    public Character getDlxChar3() {
        return dlxChar3;
    }

    public void setDlxChar3(Character dlxChar3) {
        this.dlxChar3 = dlxChar3;
    }

    public Character getDlxChar4() {
        return dlxChar4;
    }

    public void setDlxChar4(Character dlxChar4) {
        this.dlxChar4 = dlxChar4;
    }

    public String getDlxVchar1() {
        return dlxVchar1;
    }

    public void setDlxVchar1(String dlxVchar1) {
        this.dlxVchar1 = dlxVchar1;
    }

    public String getDlxVchar2() {
        return dlxVchar2;
    }

    public void setDlxVchar2(String dlxVchar2) {
        this.dlxVchar2 = dlxVchar2;
    }

    public String getDlxVchar3() {
        return dlxVchar3;
    }

    public void setDlxVchar3(String dlxVchar3) {
        this.dlxVchar3 = dlxVchar3;
    }

    public String getDlxVchar4() {
        return dlxVchar4;
    }

    public void setDlxVchar4(String dlxVchar4) {
        this.dlxVchar4 = dlxVchar4;
    }

    public Date getDlxDate1() {
        return dlxDate1;
    }

    public void setDlxDate1(Date dlxDate1) {
        this.dlxDate1 = dlxDate1;
    }

    public Date getDlxDate2() {
        return dlxDate2;
    }

    public void setDlxDate2(Date dlxDate2) {
        this.dlxDate2 = dlxDate2;
    }

    public Date getDlxDate3() {
        return dlxDate3;
    }

    public void setDlxDate3(Date dlxDate3) {
        this.dlxDate3 = dlxDate3;
    }

    public Date getDlxDate4() {
        return dlxDate4;
    }

    public void setDlxDate4(Date dlxDate4) {
        this.dlxDate4 = dlxDate4;
    }

    public Integer getDlxInt1() {
        return dlxInt1;
    }

    public void setDlxInt1(Integer dlxInt1) {
        this.dlxInt1 = dlxInt1;
    }

    public Integer getDlxInt2() {
        return dlxInt2;
    }

    public void setDlxInt2(Integer dlxInt2) {
        this.dlxInt2 = dlxInt2;
    }

    public Integer getDlxInt3() {
        return dlxInt3;
    }

    public void setDlxInt3(Integer dlxInt3) {
        this.dlxInt3 = dlxInt3;
    }

    public Integer getDlxInt4() {
        return dlxInt4;
    }

    public void setDlxInt4(Integer dlxInt4) {
        this.dlxInt4 = dlxInt4;
    }

    public BigDecimal getDlxNumeric1() {
        return dlxNumeric1;
    }

    public void setDlxNumeric1(BigDecimal dlxNumeric1) {
        this.dlxNumeric1 = dlxNumeric1;
    }

    public BigDecimal getDlxNumeric2() {
        return dlxNumeric2;
    }

    public void setDlxNumeric2(BigDecimal dlxNumeric2) {
        this.dlxNumeric2 = dlxNumeric2;
    }

    public BigDecimal getDlxNumeric3() {
        return dlxNumeric3;
    }

    public void setDlxNumeric3(BigDecimal dlxNumeric3) {
        this.dlxNumeric3 = dlxNumeric3;
    }

    public BigDecimal getDlxNumeric4() {
        return dlxNumeric4;
    }

    public void setDlxNumeric4(BigDecimal dlxNumeric4) {
        this.dlxNumeric4 = dlxNumeric4;
    }

    public String getDlx2char1() {
        return dlx2char1;
    }

    public void setDlx2char1(String dlx2char1) {
        this.dlx2char1 = dlx2char1;
    }

    public String getDlx2char2() {
        return dlx2char2;
    }

    public void setDlx2char2(String dlx2char2) {
        this.dlx2char2 = dlx2char2;
    }

    public String getDlxRecord() {
        return dlxRecord;
    }

    public void setDlxRecord(String dlxRecord) {
        this.dlxRecord = dlxRecord;
    }

    public Character getDlxStatus() {
        return dlxStatus;
    }

    public void setDlxStatus(Character dlxStatus) {
        this.dlxStatus = dlxStatus;
    }

    public Date getDlxTime1() {
        return dlxTime1;
    }

    public void setDlxTime1(Date dlxTime1) {
        this.dlxTime1 = dlxTime1;
    }

    public Date getDlxTime2() {
        return dlxTime2;
    }

    public void setDlxTime2(Date dlxTime2) {
        this.dlxTime2 = dlxTime2;
    }

    public DaneLiSl getDlxDlsSerial() {
        return dlxDlsSerial;
    }

    public void setDlxDlsSerial(DaneLiSl dlxDlsSerial) {
        this.dlxDlsSerial = dlxDlsSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dlxSerial != null ? dlxSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneLiDaX)) {
            return false;
        }
        DaneLiDaX other = (DaneLiDaX) object;
        if ((this.dlxSerial == null && other.dlxSerial != null) || (this.dlxSerial != null && !this.dlxSerial.equals(other.dlxSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneLiDaX[ dlxSerial=" + dlxSerial + " ]";
    }
    
}
