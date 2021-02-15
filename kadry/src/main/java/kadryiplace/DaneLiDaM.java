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
@Table(name = "dane_li_da_m", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneLiDaM.findAll", query = "SELECT d FROM DaneLiDaM d"),
    @NamedQuery(name = "DaneLiDaM.findByDlmSerial", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmSerial = :dlmSerial"),
    @NamedQuery(name = "DaneLiDaM.findByDlmSysSerial", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmSysSerial = :dlmSysSerial"),
    @NamedQuery(name = "DaneLiDaM.findByDlmChar1", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmChar1 = :dlmChar1"),
    @NamedQuery(name = "DaneLiDaM.findByDlmChar2", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmChar2 = :dlmChar2"),
    @NamedQuery(name = "DaneLiDaM.findByDlmChar3", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmChar3 = :dlmChar3"),
    @NamedQuery(name = "DaneLiDaM.findByDlmChar4", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmChar4 = :dlmChar4"),
    @NamedQuery(name = "DaneLiDaM.findByDlmVchar1", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmVchar1 = :dlmVchar1"),
    @NamedQuery(name = "DaneLiDaM.findByDlmVchar2", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmVchar2 = :dlmVchar2"),
    @NamedQuery(name = "DaneLiDaM.findByDlmVchar3", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmVchar3 = :dlmVchar3"),
    @NamedQuery(name = "DaneLiDaM.findByDlmVchar4", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmVchar4 = :dlmVchar4"),
    @NamedQuery(name = "DaneLiDaM.findByDlmDate1", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmDate1 = :dlmDate1"),
    @NamedQuery(name = "DaneLiDaM.findByDlmDate2", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmDate2 = :dlmDate2"),
    @NamedQuery(name = "DaneLiDaM.findByDlmDate3", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmDate3 = :dlmDate3"),
    @NamedQuery(name = "DaneLiDaM.findByDlmDate4", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmDate4 = :dlmDate4"),
    @NamedQuery(name = "DaneLiDaM.findByDlmInt1", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmInt1 = :dlmInt1"),
    @NamedQuery(name = "DaneLiDaM.findByDlmInt2", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmInt2 = :dlmInt2"),
    @NamedQuery(name = "DaneLiDaM.findByDlmInt3", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmInt3 = :dlmInt3"),
    @NamedQuery(name = "DaneLiDaM.findByDlmInt4", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmInt4 = :dlmInt4"),
    @NamedQuery(name = "DaneLiDaM.findByDlmNumeric1", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmNumeric1 = :dlmNumeric1"),
    @NamedQuery(name = "DaneLiDaM.findByDlmNumeric2", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmNumeric2 = :dlmNumeric2"),
    @NamedQuery(name = "DaneLiDaM.findByDlmNumeric3", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmNumeric3 = :dlmNumeric3"),
    @NamedQuery(name = "DaneLiDaM.findByDlmNumeric4", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmNumeric4 = :dlmNumeric4"),
    @NamedQuery(name = "DaneLiDaM.findByDlm2char1", query = "SELECT d FROM DaneLiDaM d WHERE d.dlm2char1 = :dlm2char1"),
    @NamedQuery(name = "DaneLiDaM.findByDlm2char2", query = "SELECT d FROM DaneLiDaM d WHERE d.dlm2char2 = :dlm2char2"),
    @NamedQuery(name = "DaneLiDaM.findByDlmRecord", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmRecord = :dlmRecord"),
    @NamedQuery(name = "DaneLiDaM.findByDlmStatus", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmStatus = :dlmStatus"),
    @NamedQuery(name = "DaneLiDaM.findByDlmTime1", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmTime1 = :dlmTime1"),
    @NamedQuery(name = "DaneLiDaM.findByDlmTime2", query = "SELECT d FROM DaneLiDaM d WHERE d.dlmTime2 = :dlmTime2")})
public class DaneLiDaM implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dlm_serial", nullable = false)
    private Integer dlmSerial;
    @Column(name = "dlm_sys_serial")
    private Integer dlmSysSerial;
    @Column(name = "dlm_char_1")
    private Character dlmChar1;
    @Column(name = "dlm_char_2")
    private Character dlmChar2;
    @Column(name = "dlm_char_3")
    private Character dlmChar3;
    @Column(name = "dlm_char_4")
    private Character dlmChar4;
    @Size(max = 254)
    @Column(name = "dlm_vchar_1", length = 254)
    private String dlmVchar1;
    @Size(max = 254)
    @Column(name = "dlm_vchar_2", length = 254)
    private String dlmVchar2;
    @Size(max = 254)
    @Column(name = "dlm_vchar_3", length = 254)
    private String dlmVchar3;
    @Size(max = 254)
    @Column(name = "dlm_vchar_4", length = 254)
    private String dlmVchar4;
    @Column(name = "dlm_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlmDate1;
    @Column(name = "dlm_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlmDate2;
    @Column(name = "dlm_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlmDate3;
    @Column(name = "dlm_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlmDate4;
    @Column(name = "dlm_int_1")
    private Integer dlmInt1;
    @Column(name = "dlm_int_2")
    private Integer dlmInt2;
    @Column(name = "dlm_int_3")
    private Integer dlmInt3;
    @Column(name = "dlm_int_4")
    private Integer dlmInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dlm_numeric_1", precision = 17, scale = 6)
    private BigDecimal dlmNumeric1;
    @Column(name = "dlm_numeric_2", precision = 17, scale = 6)
    private BigDecimal dlmNumeric2;
    @Column(name = "dlm_numeric_3", precision = 17, scale = 6)
    private BigDecimal dlmNumeric3;
    @Column(name = "dlm_numeric_4", precision = 17, scale = 6)
    private BigDecimal dlmNumeric4;
    @Size(max = 2)
    @Column(name = "dlm_2char_1", length = 2)
    private String dlm2char1;
    @Size(max = 2)
    @Column(name = "dlm_2char_2", length = 2)
    private String dlm2char2;
    @Size(max = 4)
    @Column(name = "dlm_record", length = 4)
    private String dlmRecord;
    @Column(name = "dlm_status")
    private Character dlmStatus;
    @Column(name = "dlm_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlmTime1;
    @Column(name = "dlm_time_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlmTime2;
    @JoinColumn(name = "dlm_dls_serial", referencedColumnName = "dls_serial")
    @ManyToOne
    private DaneLiSl dlmDlsSerial;
    @JoinColumn(name = "dlm_mag_serial", referencedColumnName = "mag_serial")
    @ManyToOne
    private Magazyn dlmMagSerial;

    public DaneLiDaM() {
    }

    public DaneLiDaM(Integer dlmSerial) {
        this.dlmSerial = dlmSerial;
    }

    public Integer getDlmSerial() {
        return dlmSerial;
    }

    public void setDlmSerial(Integer dlmSerial) {
        this.dlmSerial = dlmSerial;
    }

    public Integer getDlmSysSerial() {
        return dlmSysSerial;
    }

    public void setDlmSysSerial(Integer dlmSysSerial) {
        this.dlmSysSerial = dlmSysSerial;
    }

    public Character getDlmChar1() {
        return dlmChar1;
    }

    public void setDlmChar1(Character dlmChar1) {
        this.dlmChar1 = dlmChar1;
    }

    public Character getDlmChar2() {
        return dlmChar2;
    }

    public void setDlmChar2(Character dlmChar2) {
        this.dlmChar2 = dlmChar2;
    }

    public Character getDlmChar3() {
        return dlmChar3;
    }

    public void setDlmChar3(Character dlmChar3) {
        this.dlmChar3 = dlmChar3;
    }

    public Character getDlmChar4() {
        return dlmChar4;
    }

    public void setDlmChar4(Character dlmChar4) {
        this.dlmChar4 = dlmChar4;
    }

    public String getDlmVchar1() {
        return dlmVchar1;
    }

    public void setDlmVchar1(String dlmVchar1) {
        this.dlmVchar1 = dlmVchar1;
    }

    public String getDlmVchar2() {
        return dlmVchar2;
    }

    public void setDlmVchar2(String dlmVchar2) {
        this.dlmVchar2 = dlmVchar2;
    }

    public String getDlmVchar3() {
        return dlmVchar3;
    }

    public void setDlmVchar3(String dlmVchar3) {
        this.dlmVchar3 = dlmVchar3;
    }

    public String getDlmVchar4() {
        return dlmVchar4;
    }

    public void setDlmVchar4(String dlmVchar4) {
        this.dlmVchar4 = dlmVchar4;
    }

    public Date getDlmDate1() {
        return dlmDate1;
    }

    public void setDlmDate1(Date dlmDate1) {
        this.dlmDate1 = dlmDate1;
    }

    public Date getDlmDate2() {
        return dlmDate2;
    }

    public void setDlmDate2(Date dlmDate2) {
        this.dlmDate2 = dlmDate2;
    }

    public Date getDlmDate3() {
        return dlmDate3;
    }

    public void setDlmDate3(Date dlmDate3) {
        this.dlmDate3 = dlmDate3;
    }

    public Date getDlmDate4() {
        return dlmDate4;
    }

    public void setDlmDate4(Date dlmDate4) {
        this.dlmDate4 = dlmDate4;
    }

    public Integer getDlmInt1() {
        return dlmInt1;
    }

    public void setDlmInt1(Integer dlmInt1) {
        this.dlmInt1 = dlmInt1;
    }

    public Integer getDlmInt2() {
        return dlmInt2;
    }

    public void setDlmInt2(Integer dlmInt2) {
        this.dlmInt2 = dlmInt2;
    }

    public Integer getDlmInt3() {
        return dlmInt3;
    }

    public void setDlmInt3(Integer dlmInt3) {
        this.dlmInt3 = dlmInt3;
    }

    public Integer getDlmInt4() {
        return dlmInt4;
    }

    public void setDlmInt4(Integer dlmInt4) {
        this.dlmInt4 = dlmInt4;
    }

    public BigDecimal getDlmNumeric1() {
        return dlmNumeric1;
    }

    public void setDlmNumeric1(BigDecimal dlmNumeric1) {
        this.dlmNumeric1 = dlmNumeric1;
    }

    public BigDecimal getDlmNumeric2() {
        return dlmNumeric2;
    }

    public void setDlmNumeric2(BigDecimal dlmNumeric2) {
        this.dlmNumeric2 = dlmNumeric2;
    }

    public BigDecimal getDlmNumeric3() {
        return dlmNumeric3;
    }

    public void setDlmNumeric3(BigDecimal dlmNumeric3) {
        this.dlmNumeric3 = dlmNumeric3;
    }

    public BigDecimal getDlmNumeric4() {
        return dlmNumeric4;
    }

    public void setDlmNumeric4(BigDecimal dlmNumeric4) {
        this.dlmNumeric4 = dlmNumeric4;
    }

    public String getDlm2char1() {
        return dlm2char1;
    }

    public void setDlm2char1(String dlm2char1) {
        this.dlm2char1 = dlm2char1;
    }

    public String getDlm2char2() {
        return dlm2char2;
    }

    public void setDlm2char2(String dlm2char2) {
        this.dlm2char2 = dlm2char2;
    }

    public String getDlmRecord() {
        return dlmRecord;
    }

    public void setDlmRecord(String dlmRecord) {
        this.dlmRecord = dlmRecord;
    }

    public Character getDlmStatus() {
        return dlmStatus;
    }

    public void setDlmStatus(Character dlmStatus) {
        this.dlmStatus = dlmStatus;
    }

    public Date getDlmTime1() {
        return dlmTime1;
    }

    public void setDlmTime1(Date dlmTime1) {
        this.dlmTime1 = dlmTime1;
    }

    public Date getDlmTime2() {
        return dlmTime2;
    }

    public void setDlmTime2(Date dlmTime2) {
        this.dlmTime2 = dlmTime2;
    }

    public DaneLiSl getDlmDlsSerial() {
        return dlmDlsSerial;
    }

    public void setDlmDlsSerial(DaneLiSl dlmDlsSerial) {
        this.dlmDlsSerial = dlmDlsSerial;
    }

    public Magazyn getDlmMagSerial() {
        return dlmMagSerial;
    }

    public void setDlmMagSerial(Magazyn dlmMagSerial) {
        this.dlmMagSerial = dlmMagSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dlmSerial != null ? dlmSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneLiDaM)) {
            return false;
        }
        DaneLiDaM other = (DaneLiDaM) object;
        if ((this.dlmSerial == null && other.dlmSerial != null) || (this.dlmSerial != null && !this.dlmSerial.equals(other.dlmSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneLiDaM[ dlmSerial=" + dlmSerial + " ]";
    }
    
}
