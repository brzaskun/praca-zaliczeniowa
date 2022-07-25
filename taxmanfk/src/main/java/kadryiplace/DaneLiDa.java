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
@Table(name = "dane_li_da", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneLiDa.findAll", query = "SELECT d FROM DaneLiDa d"),
    @NamedQuery(name = "DaneLiDa.findByDliSerial", query = "SELECT d FROM DaneLiDa d WHERE d.dliSerial = :dliSerial"),
    @NamedQuery(name = "DaneLiDa.findByDliSysSerial", query = "SELECT d FROM DaneLiDa d WHERE d.dliSysSerial = :dliSysSerial"),
    @NamedQuery(name = "DaneLiDa.findByDliChar1", query = "SELECT d FROM DaneLiDa d WHERE d.dliChar1 = :dliChar1"),
    @NamedQuery(name = "DaneLiDa.findByDliChar2", query = "SELECT d FROM DaneLiDa d WHERE d.dliChar2 = :dliChar2"),
    @NamedQuery(name = "DaneLiDa.findByDliChar3", query = "SELECT d FROM DaneLiDa d WHERE d.dliChar3 = :dliChar3"),
    @NamedQuery(name = "DaneLiDa.findByDliChar4", query = "SELECT d FROM DaneLiDa d WHERE d.dliChar4 = :dliChar4"),
    @NamedQuery(name = "DaneLiDa.findByDliVchar1", query = "SELECT d FROM DaneLiDa d WHERE d.dliVchar1 = :dliVchar1"),
    @NamedQuery(name = "DaneLiDa.findByDliVchar2", query = "SELECT d FROM DaneLiDa d WHERE d.dliVchar2 = :dliVchar2"),
    @NamedQuery(name = "DaneLiDa.findByDliVchar3", query = "SELECT d FROM DaneLiDa d WHERE d.dliVchar3 = :dliVchar3"),
    @NamedQuery(name = "DaneLiDa.findByDliVchar4", query = "SELECT d FROM DaneLiDa d WHERE d.dliVchar4 = :dliVchar4"),
    @NamedQuery(name = "DaneLiDa.findByDliDate1", query = "SELECT d FROM DaneLiDa d WHERE d.dliDate1 = :dliDate1"),
    @NamedQuery(name = "DaneLiDa.findByDliDate2", query = "SELECT d FROM DaneLiDa d WHERE d.dliDate2 = :dliDate2"),
    @NamedQuery(name = "DaneLiDa.findByDliDate3", query = "SELECT d FROM DaneLiDa d WHERE d.dliDate3 = :dliDate3"),
    @NamedQuery(name = "DaneLiDa.findByDliDate4", query = "SELECT d FROM DaneLiDa d WHERE d.dliDate4 = :dliDate4"),
    @NamedQuery(name = "DaneLiDa.findByDliInt1", query = "SELECT d FROM DaneLiDa d WHERE d.dliInt1 = :dliInt1"),
    @NamedQuery(name = "DaneLiDa.findByDliInt2", query = "SELECT d FROM DaneLiDa d WHERE d.dliInt2 = :dliInt2"),
    @NamedQuery(name = "DaneLiDa.findByDliInt3", query = "SELECT d FROM DaneLiDa d WHERE d.dliInt3 = :dliInt3"),
    @NamedQuery(name = "DaneLiDa.findByDliInt4", query = "SELECT d FROM DaneLiDa d WHERE d.dliInt4 = :dliInt4"),
    @NamedQuery(name = "DaneLiDa.findByDliNumeric1", query = "SELECT d FROM DaneLiDa d WHERE d.dliNumeric1 = :dliNumeric1"),
    @NamedQuery(name = "DaneLiDa.findByDliNumeric2", query = "SELECT d FROM DaneLiDa d WHERE d.dliNumeric2 = :dliNumeric2"),
    @NamedQuery(name = "DaneLiDa.findByDliNumeric3", query = "SELECT d FROM DaneLiDa d WHERE d.dliNumeric3 = :dliNumeric3"),
    @NamedQuery(name = "DaneLiDa.findByDliNumeric4", query = "SELECT d FROM DaneLiDa d WHERE d.dliNumeric4 = :dliNumeric4"),
    @NamedQuery(name = "DaneLiDa.findByDli2char1", query = "SELECT d FROM DaneLiDa d WHERE d.dli2char1 = :dli2char1"),
    @NamedQuery(name = "DaneLiDa.findByDli2char2", query = "SELECT d FROM DaneLiDa d WHERE d.dli2char2 = :dli2char2"),
    @NamedQuery(name = "DaneLiDa.findByDliRecord", query = "SELECT d FROM DaneLiDa d WHERE d.dliRecord = :dliRecord"),
    @NamedQuery(name = "DaneLiDa.findByDliStatus", query = "SELECT d FROM DaneLiDa d WHERE d.dliStatus = :dliStatus"),
    @NamedQuery(name = "DaneLiDa.findByDliTime1", query = "SELECT d FROM DaneLiDa d WHERE d.dliTime1 = :dliTime1"),
    @NamedQuery(name = "DaneLiDa.findByDliTime2", query = "SELECT d FROM DaneLiDa d WHERE d.dliTime2 = :dliTime2")})
public class DaneLiDa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dli_serial", nullable = false)
    private Integer dliSerial;
    @Column(name = "dli_sys_serial")
    private Integer dliSysSerial;
    @Column(name = "dli_char_1")
    private Character dliChar1;
    @Column(name = "dli_char_2")
    private Character dliChar2;
    @Column(name = "dli_char_3")
    private Character dliChar3;
    @Column(name = "dli_char_4")
    private Character dliChar4;
    @Size(max = 254)
    @Column(name = "dli_vchar_1", length = 254)
    private String dliVchar1;
    @Size(max = 254)
    @Column(name = "dli_vchar_2", length = 254)
    private String dliVchar2;
    @Size(max = 254)
    @Column(name = "dli_vchar_3", length = 254)
    private String dliVchar3;
    @Size(max = 254)
    @Column(name = "dli_vchar_4", length = 254)
    private String dliVchar4;
    @Column(name = "dli_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dliDate1;
    @Column(name = "dli_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dliDate2;
    @Column(name = "dli_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dliDate3;
    @Column(name = "dli_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dliDate4;
    @Column(name = "dli_int_1")
    private Integer dliInt1;
    @Column(name = "dli_int_2")
    private Integer dliInt2;
    @Column(name = "dli_int_3")
    private Integer dliInt3;
    @Column(name = "dli_int_4")
    private Integer dliInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dli_numeric_1", precision = 17, scale = 6)
    private BigDecimal dliNumeric1;
    @Column(name = "dli_numeric_2", precision = 17, scale = 6)
    private BigDecimal dliNumeric2;
    @Column(name = "dli_numeric_3", precision = 17, scale = 6)
    private BigDecimal dliNumeric3;
    @Column(name = "dli_numeric_4", precision = 17, scale = 6)
    private BigDecimal dliNumeric4;
    @Size(max = 2)
    @Column(name = "dli_2char_1", length = 2)
    private String dli2char1;
    @Size(max = 2)
    @Column(name = "dli_2char_2", length = 2)
    private String dli2char2;
    @Size(max = 4)
    @Column(name = "dli_record", length = 4)
    private String dliRecord;
    @Column(name = "dli_status")
    private Character dliStatus;
    @Column(name = "dli_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dliTime1;
    @Column(name = "dli_time_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dliTime2;
    @JoinColumn(name = "dli_dls_serial", referencedColumnName = "dls_serial")
    @ManyToOne
    private DaneLiSl dliDlsSerial;
    @JoinColumn(name = "dli_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma dliFirSerial;
    @JoinColumn(name = "dli_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent dliKonSerial;
    @JoinColumn(name = "dli_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba dliOsoSerial;
    @JoinColumn(name = "dli_osr_serial", referencedColumnName = "osr_serial")
    @ManyToOne
    private OsobaRod dliOsrSerial;

    public DaneLiDa() {
    }

    public DaneLiDa(Integer dliSerial) {
        this.dliSerial = dliSerial;
    }

    public Integer getDliSerial() {
        return dliSerial;
    }

    public void setDliSerial(Integer dliSerial) {
        this.dliSerial = dliSerial;
    }

    public Integer getDliSysSerial() {
        return dliSysSerial;
    }

    public void setDliSysSerial(Integer dliSysSerial) {
        this.dliSysSerial = dliSysSerial;
    }

    public Character getDliChar1() {
        return dliChar1;
    }

    public void setDliChar1(Character dliChar1) {
        this.dliChar1 = dliChar1;
    }

    public Character getDliChar2() {
        return dliChar2;
    }

    public void setDliChar2(Character dliChar2) {
        this.dliChar2 = dliChar2;
    }

    public Character getDliChar3() {
        return dliChar3;
    }

    public void setDliChar3(Character dliChar3) {
        this.dliChar3 = dliChar3;
    }

    public Character getDliChar4() {
        return dliChar4;
    }

    public void setDliChar4(Character dliChar4) {
        this.dliChar4 = dliChar4;
    }

    public String getDliVchar1() {
        return dliVchar1;
    }

    public void setDliVchar1(String dliVchar1) {
        this.dliVchar1 = dliVchar1;
    }

    public String getDliVchar2() {
        return dliVchar2;
    }

    public void setDliVchar2(String dliVchar2) {
        this.dliVchar2 = dliVchar2;
    }

    public String getDliVchar3() {
        return dliVchar3;
    }

    public void setDliVchar3(String dliVchar3) {
        this.dliVchar3 = dliVchar3;
    }

    public String getDliVchar4() {
        return dliVchar4;
    }

    public void setDliVchar4(String dliVchar4) {
        this.dliVchar4 = dliVchar4;
    }

    public Date getDliDate1() {
        return dliDate1;
    }

    public void setDliDate1(Date dliDate1) {
        this.dliDate1 = dliDate1;
    }

    public Date getDliDate2() {
        return dliDate2;
    }

    public void setDliDate2(Date dliDate2) {
        this.dliDate2 = dliDate2;
    }

    public Date getDliDate3() {
        return dliDate3;
    }

    public void setDliDate3(Date dliDate3) {
        this.dliDate3 = dliDate3;
    }

    public Date getDliDate4() {
        return dliDate4;
    }

    public void setDliDate4(Date dliDate4) {
        this.dliDate4 = dliDate4;
    }

    public Integer getDliInt1() {
        return dliInt1;
    }

    public void setDliInt1(Integer dliInt1) {
        this.dliInt1 = dliInt1;
    }

    public Integer getDliInt2() {
        return dliInt2;
    }

    public void setDliInt2(Integer dliInt2) {
        this.dliInt2 = dliInt2;
    }

    public Integer getDliInt3() {
        return dliInt3;
    }

    public void setDliInt3(Integer dliInt3) {
        this.dliInt3 = dliInt3;
    }

    public Integer getDliInt4() {
        return dliInt4;
    }

    public void setDliInt4(Integer dliInt4) {
        this.dliInt4 = dliInt4;
    }

    public BigDecimal getDliNumeric1() {
        return dliNumeric1;
    }

    public void setDliNumeric1(BigDecimal dliNumeric1) {
        this.dliNumeric1 = dliNumeric1;
    }

    public BigDecimal getDliNumeric2() {
        return dliNumeric2;
    }

    public void setDliNumeric2(BigDecimal dliNumeric2) {
        this.dliNumeric2 = dliNumeric2;
    }

    public BigDecimal getDliNumeric3() {
        return dliNumeric3;
    }

    public void setDliNumeric3(BigDecimal dliNumeric3) {
        this.dliNumeric3 = dliNumeric3;
    }

    public BigDecimal getDliNumeric4() {
        return dliNumeric4;
    }

    public void setDliNumeric4(BigDecimal dliNumeric4) {
        this.dliNumeric4 = dliNumeric4;
    }

    public String getDli2char1() {
        return dli2char1;
    }

    public void setDli2char1(String dli2char1) {
        this.dli2char1 = dli2char1;
    }

    public String getDli2char2() {
        return dli2char2;
    }

    public void setDli2char2(String dli2char2) {
        this.dli2char2 = dli2char2;
    }

    public String getDliRecord() {
        return dliRecord;
    }

    public void setDliRecord(String dliRecord) {
        this.dliRecord = dliRecord;
    }

    public Character getDliStatus() {
        return dliStatus;
    }

    public void setDliStatus(Character dliStatus) {
        this.dliStatus = dliStatus;
    }

    public Date getDliTime1() {
        return dliTime1;
    }

    public void setDliTime1(Date dliTime1) {
        this.dliTime1 = dliTime1;
    }

    public Date getDliTime2() {
        return dliTime2;
    }

    public void setDliTime2(Date dliTime2) {
        this.dliTime2 = dliTime2;
    }

    public DaneLiSl getDliDlsSerial() {
        return dliDlsSerial;
    }

    public void setDliDlsSerial(DaneLiSl dliDlsSerial) {
        this.dliDlsSerial = dliDlsSerial;
    }

    public Firma getDliFirSerial() {
        return dliFirSerial;
    }

    public void setDliFirSerial(Firma dliFirSerial) {
        this.dliFirSerial = dliFirSerial;
    }

    public Kontrahent getDliKonSerial() {
        return dliKonSerial;
    }

    public void setDliKonSerial(Kontrahent dliKonSerial) {
        this.dliKonSerial = dliKonSerial;
    }

    public Osoba getDliOsoSerial() {
        return dliOsoSerial;
    }

    public void setDliOsoSerial(Osoba dliOsoSerial) {
        this.dliOsoSerial = dliOsoSerial;
    }

    public OsobaRod getDliOsrSerial() {
        return dliOsrSerial;
    }

    public void setDliOsrSerial(OsobaRod dliOsrSerial) {
        this.dliOsrSerial = dliOsrSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dliSerial != null ? dliSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneLiDa)) {
            return false;
        }
        DaneLiDa other = (DaneLiDa) object;
        if ((this.dliSerial == null && other.dliSerial != null) || (this.dliSerial != null && !this.dliSerial.equals(other.dliSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneLiDa[ dliSerial=" + dliSerial + " ]";
    }
    
}
