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
@Table(name = "dane_hi_da_m", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneHiDaM.findAll", query = "SELECT d FROM DaneHiDaM d"),
    @NamedQuery(name = "DaneHiDaM.findByDhmSerial", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmSerial = :dhmSerial"),
    @NamedQuery(name = "DaneHiDaM.findByDhmSysSerial", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmSysSerial = :dhmSysSerial"),
    @NamedQuery(name = "DaneHiDaM.findByDhmDataOd", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmDataOd = :dhmDataOd"),
    @NamedQuery(name = "DaneHiDaM.findByDhmDataDo", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmDataDo = :dhmDataDo"),
    @NamedQuery(name = "DaneHiDaM.findByDhmChar1", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmChar1 = :dhmChar1"),
    @NamedQuery(name = "DaneHiDaM.findByDhmChar2", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmChar2 = :dhmChar2"),
    @NamedQuery(name = "DaneHiDaM.findByDhmChar3", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmChar3 = :dhmChar3"),
    @NamedQuery(name = "DaneHiDaM.findByDhmChar4", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmChar4 = :dhmChar4"),
    @NamedQuery(name = "DaneHiDaM.findByDhmVchar1", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmVchar1 = :dhmVchar1"),
    @NamedQuery(name = "DaneHiDaM.findByDhmVchar2", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmVchar2 = :dhmVchar2"),
    @NamedQuery(name = "DaneHiDaM.findByDhmVchar3", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmVchar3 = :dhmVchar3"),
    @NamedQuery(name = "DaneHiDaM.findByDhmVchar4", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmVchar4 = :dhmVchar4"),
    @NamedQuery(name = "DaneHiDaM.findByDhmDate1", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmDate1 = :dhmDate1"),
    @NamedQuery(name = "DaneHiDaM.findByDhmDate2", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmDate2 = :dhmDate2"),
    @NamedQuery(name = "DaneHiDaM.findByDhmDate3", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmDate3 = :dhmDate3"),
    @NamedQuery(name = "DaneHiDaM.findByDhmDate4", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmDate4 = :dhmDate4"),
    @NamedQuery(name = "DaneHiDaM.findByDhmInt1", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmInt1 = :dhmInt1"),
    @NamedQuery(name = "DaneHiDaM.findByDhmInt2", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmInt2 = :dhmInt2"),
    @NamedQuery(name = "DaneHiDaM.findByDhmInt3", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmInt3 = :dhmInt3"),
    @NamedQuery(name = "DaneHiDaM.findByDhmInt4", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmInt4 = :dhmInt4"),
    @NamedQuery(name = "DaneHiDaM.findByDhmNumeric1", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmNumeric1 = :dhmNumeric1"),
    @NamedQuery(name = "DaneHiDaM.findByDhmNumeric2", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmNumeric2 = :dhmNumeric2"),
    @NamedQuery(name = "DaneHiDaM.findByDhmNumeric3", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmNumeric3 = :dhmNumeric3"),
    @NamedQuery(name = "DaneHiDaM.findByDhmNumeric4", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmNumeric4 = :dhmNumeric4"),
    @NamedQuery(name = "DaneHiDaM.findByDhm2char1", query = "SELECT d FROM DaneHiDaM d WHERE d.dhm2char1 = :dhm2char1"),
    @NamedQuery(name = "DaneHiDaM.findByDhm2char2", query = "SELECT d FROM DaneHiDaM d WHERE d.dhm2char2 = :dhm2char2"),
    @NamedQuery(name = "DaneHiDaM.findByDhmRecord", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmRecord = :dhmRecord"),
    @NamedQuery(name = "DaneHiDaM.findByDhmStatus", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmStatus = :dhmStatus"),
    @NamedQuery(name = "DaneHiDaM.findByDhmTime1", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmTime1 = :dhmTime1"),
    @NamedQuery(name = "DaneHiDaM.findByDhmTime2", query = "SELECT d FROM DaneHiDaM d WHERE d.dhmTime2 = :dhmTime2")})
public class DaneHiDaM implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dhm_serial", nullable = false)
    private Integer dhmSerial;
    @Column(name = "dhm_sys_serial")
    private Integer dhmSysSerial;
    @Column(name = "dhm_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhmDataOd;
    @Column(name = "dhm_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhmDataDo;
    @Column(name = "dhm_char_1")
    private Character dhmChar1;
    @Column(name = "dhm_char_2")
    private Character dhmChar2;
    @Column(name = "dhm_char_3")
    private Character dhmChar3;
    @Column(name = "dhm_char_4")
    private Character dhmChar4;
    @Size(max = 254)
    @Column(name = "dhm_vchar_1", length = 254)
    private String dhmVchar1;
    @Size(max = 254)
    @Column(name = "dhm_vchar_2", length = 254)
    private String dhmVchar2;
    @Size(max = 254)
    @Column(name = "dhm_vchar_3", length = 254)
    private String dhmVchar3;
    @Size(max = 254)
    @Column(name = "dhm_vchar_4", length = 254)
    private String dhmVchar4;
    @Column(name = "dhm_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhmDate1;
    @Column(name = "dhm_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhmDate2;
    @Column(name = "dhm_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhmDate3;
    @Column(name = "dhm_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhmDate4;
    @Column(name = "dhm_int_1")
    private Integer dhmInt1;
    @Column(name = "dhm_int_2")
    private Integer dhmInt2;
    @Column(name = "dhm_int_3")
    private Integer dhmInt3;
    @Column(name = "dhm_int_4")
    private Integer dhmInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dhm_numeric_1", precision = 17, scale = 6)
    private BigDecimal dhmNumeric1;
    @Column(name = "dhm_numeric_2", precision = 17, scale = 6)
    private BigDecimal dhmNumeric2;
    @Column(name = "dhm_numeric_3", precision = 17, scale = 6)
    private BigDecimal dhmNumeric3;
    @Column(name = "dhm_numeric_4", precision = 17, scale = 6)
    private BigDecimal dhmNumeric4;
    @Size(max = 2)
    @Column(name = "dhm_2char_1", length = 2)
    private String dhm2char1;
    @Size(max = 2)
    @Column(name = "dhm_2char_2", length = 2)
    private String dhm2char2;
    @Size(max = 4)
    @Column(name = "dhm_record", length = 4)
    private String dhmRecord;
    @Column(name = "dhm_status")
    private Character dhmStatus;
    @Column(name = "dhm_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhmTime1;
    @Column(name = "dhm_time_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhmTime2;
    @JoinColumn(name = "dhm_dls_serial", referencedColumnName = "dls_serial")
    @ManyToOne
    private DaneLiSl dhmDlsSerial;
    @JoinColumn(name = "dhm_mag_serial", referencedColumnName = "mag_serial")
    @ManyToOne
    private Magazyn dhmMagSerial;

    public DaneHiDaM() {
    }

    public DaneHiDaM(Integer dhmSerial) {
        this.dhmSerial = dhmSerial;
    }

    public Integer getDhmSerial() {
        return dhmSerial;
    }

    public void setDhmSerial(Integer dhmSerial) {
        this.dhmSerial = dhmSerial;
    }

    public Integer getDhmSysSerial() {
        return dhmSysSerial;
    }

    public void setDhmSysSerial(Integer dhmSysSerial) {
        this.dhmSysSerial = dhmSysSerial;
    }

    public Date getDhmDataOd() {
        return dhmDataOd;
    }

    public void setDhmDataOd(Date dhmDataOd) {
        this.dhmDataOd = dhmDataOd;
    }

    public Date getDhmDataDo() {
        return dhmDataDo;
    }

    public void setDhmDataDo(Date dhmDataDo) {
        this.dhmDataDo = dhmDataDo;
    }

    public Character getDhmChar1() {
        return dhmChar1;
    }

    public void setDhmChar1(Character dhmChar1) {
        this.dhmChar1 = dhmChar1;
    }

    public Character getDhmChar2() {
        return dhmChar2;
    }

    public void setDhmChar2(Character dhmChar2) {
        this.dhmChar2 = dhmChar2;
    }

    public Character getDhmChar3() {
        return dhmChar3;
    }

    public void setDhmChar3(Character dhmChar3) {
        this.dhmChar3 = dhmChar3;
    }

    public Character getDhmChar4() {
        return dhmChar4;
    }

    public void setDhmChar4(Character dhmChar4) {
        this.dhmChar4 = dhmChar4;
    }

    public String getDhmVchar1() {
        return dhmVchar1;
    }

    public void setDhmVchar1(String dhmVchar1) {
        this.dhmVchar1 = dhmVchar1;
    }

    public String getDhmVchar2() {
        return dhmVchar2;
    }

    public void setDhmVchar2(String dhmVchar2) {
        this.dhmVchar2 = dhmVchar2;
    }

    public String getDhmVchar3() {
        return dhmVchar3;
    }

    public void setDhmVchar3(String dhmVchar3) {
        this.dhmVchar3 = dhmVchar3;
    }

    public String getDhmVchar4() {
        return dhmVchar4;
    }

    public void setDhmVchar4(String dhmVchar4) {
        this.dhmVchar4 = dhmVchar4;
    }

    public Date getDhmDate1() {
        return dhmDate1;
    }

    public void setDhmDate1(Date dhmDate1) {
        this.dhmDate1 = dhmDate1;
    }

    public Date getDhmDate2() {
        return dhmDate2;
    }

    public void setDhmDate2(Date dhmDate2) {
        this.dhmDate2 = dhmDate2;
    }

    public Date getDhmDate3() {
        return dhmDate3;
    }

    public void setDhmDate3(Date dhmDate3) {
        this.dhmDate3 = dhmDate3;
    }

    public Date getDhmDate4() {
        return dhmDate4;
    }

    public void setDhmDate4(Date dhmDate4) {
        this.dhmDate4 = dhmDate4;
    }

    public Integer getDhmInt1() {
        return dhmInt1;
    }

    public void setDhmInt1(Integer dhmInt1) {
        this.dhmInt1 = dhmInt1;
    }

    public Integer getDhmInt2() {
        return dhmInt2;
    }

    public void setDhmInt2(Integer dhmInt2) {
        this.dhmInt2 = dhmInt2;
    }

    public Integer getDhmInt3() {
        return dhmInt3;
    }

    public void setDhmInt3(Integer dhmInt3) {
        this.dhmInt3 = dhmInt3;
    }

    public Integer getDhmInt4() {
        return dhmInt4;
    }

    public void setDhmInt4(Integer dhmInt4) {
        this.dhmInt4 = dhmInt4;
    }

    public BigDecimal getDhmNumeric1() {
        return dhmNumeric1;
    }

    public void setDhmNumeric1(BigDecimal dhmNumeric1) {
        this.dhmNumeric1 = dhmNumeric1;
    }

    public BigDecimal getDhmNumeric2() {
        return dhmNumeric2;
    }

    public void setDhmNumeric2(BigDecimal dhmNumeric2) {
        this.dhmNumeric2 = dhmNumeric2;
    }

    public BigDecimal getDhmNumeric3() {
        return dhmNumeric3;
    }

    public void setDhmNumeric3(BigDecimal dhmNumeric3) {
        this.dhmNumeric3 = dhmNumeric3;
    }

    public BigDecimal getDhmNumeric4() {
        return dhmNumeric4;
    }

    public void setDhmNumeric4(BigDecimal dhmNumeric4) {
        this.dhmNumeric4 = dhmNumeric4;
    }

    public String getDhm2char1() {
        return dhm2char1;
    }

    public void setDhm2char1(String dhm2char1) {
        this.dhm2char1 = dhm2char1;
    }

    public String getDhm2char2() {
        return dhm2char2;
    }

    public void setDhm2char2(String dhm2char2) {
        this.dhm2char2 = dhm2char2;
    }

    public String getDhmRecord() {
        return dhmRecord;
    }

    public void setDhmRecord(String dhmRecord) {
        this.dhmRecord = dhmRecord;
    }

    public Character getDhmStatus() {
        return dhmStatus;
    }

    public void setDhmStatus(Character dhmStatus) {
        this.dhmStatus = dhmStatus;
    }

    public Date getDhmTime1() {
        return dhmTime1;
    }

    public void setDhmTime1(Date dhmTime1) {
        this.dhmTime1 = dhmTime1;
    }

    public Date getDhmTime2() {
        return dhmTime2;
    }

    public void setDhmTime2(Date dhmTime2) {
        this.dhmTime2 = dhmTime2;
    }

    public DaneLiSl getDhmDlsSerial() {
        return dhmDlsSerial;
    }

    public void setDhmDlsSerial(DaneLiSl dhmDlsSerial) {
        this.dhmDlsSerial = dhmDlsSerial;
    }

    public Magazyn getDhmMagSerial() {
        return dhmMagSerial;
    }

    public void setDhmMagSerial(Magazyn dhmMagSerial) {
        this.dhmMagSerial = dhmMagSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dhmSerial != null ? dhmSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneHiDaM)) {
            return false;
        }
        DaneHiDaM other = (DaneHiDaM) object;
        if ((this.dhmSerial == null && other.dhmSerial != null) || (this.dhmSerial != null && !this.dhmSerial.equals(other.dhmSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneHiDaM[ dhmSerial=" + dhmSerial + " ]";
    }
    
}
