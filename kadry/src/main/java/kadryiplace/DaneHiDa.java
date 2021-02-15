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
@Table(name = "dane_hi_da", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneHiDa.findAll", query = "SELECT d FROM DaneHiDa d"),
    @NamedQuery(name = "DaneHiDa.findByDhiSerial", query = "SELECT d FROM DaneHiDa d WHERE d.dhiSerial = :dhiSerial"),
    @NamedQuery(name = "DaneHiDa.findByDhiSysSerial", query = "SELECT d FROM DaneHiDa d WHERE d.dhiSysSerial = :dhiSysSerial"),
    @NamedQuery(name = "DaneHiDa.findByDhiDataOd", query = "SELECT d FROM DaneHiDa d WHERE d.dhiDataOd = :dhiDataOd"),
    @NamedQuery(name = "DaneHiDa.findByDhiDataDo", query = "SELECT d FROM DaneHiDa d WHERE d.dhiDataDo = :dhiDataDo"),
    @NamedQuery(name = "DaneHiDa.findByDhiChar1", query = "SELECT d FROM DaneHiDa d WHERE d.dhiChar1 = :dhiChar1"),
    @NamedQuery(name = "DaneHiDa.findByDhiChar2", query = "SELECT d FROM DaneHiDa d WHERE d.dhiChar2 = :dhiChar2"),
    @NamedQuery(name = "DaneHiDa.findByDhiChar3", query = "SELECT d FROM DaneHiDa d WHERE d.dhiChar3 = :dhiChar3"),
    @NamedQuery(name = "DaneHiDa.findByDhiChar4", query = "SELECT d FROM DaneHiDa d WHERE d.dhiChar4 = :dhiChar4"),
    @NamedQuery(name = "DaneHiDa.findByDhiVchar1", query = "SELECT d FROM DaneHiDa d WHERE d.dhiVchar1 = :dhiVchar1"),
    @NamedQuery(name = "DaneHiDa.findByDhiVchar2", query = "SELECT d FROM DaneHiDa d WHERE d.dhiVchar2 = :dhiVchar2"),
    @NamedQuery(name = "DaneHiDa.findByDhiVchar3", query = "SELECT d FROM DaneHiDa d WHERE d.dhiVchar3 = :dhiVchar3"),
    @NamedQuery(name = "DaneHiDa.findByDhiVchar4", query = "SELECT d FROM DaneHiDa d WHERE d.dhiVchar4 = :dhiVchar4"),
    @NamedQuery(name = "DaneHiDa.findByDhiDate1", query = "SELECT d FROM DaneHiDa d WHERE d.dhiDate1 = :dhiDate1"),
    @NamedQuery(name = "DaneHiDa.findByDhiDate2", query = "SELECT d FROM DaneHiDa d WHERE d.dhiDate2 = :dhiDate2"),
    @NamedQuery(name = "DaneHiDa.findByDhiDate3", query = "SELECT d FROM DaneHiDa d WHERE d.dhiDate3 = :dhiDate3"),
    @NamedQuery(name = "DaneHiDa.findByDhiDate4", query = "SELECT d FROM DaneHiDa d WHERE d.dhiDate4 = :dhiDate4"),
    @NamedQuery(name = "DaneHiDa.findByDhiInt1", query = "SELECT d FROM DaneHiDa d WHERE d.dhiInt1 = :dhiInt1"),
    @NamedQuery(name = "DaneHiDa.findByDhiInt2", query = "SELECT d FROM DaneHiDa d WHERE d.dhiInt2 = :dhiInt2"),
    @NamedQuery(name = "DaneHiDa.findByDhiInt3", query = "SELECT d FROM DaneHiDa d WHERE d.dhiInt3 = :dhiInt3"),
    @NamedQuery(name = "DaneHiDa.findByDhiInt4", query = "SELECT d FROM DaneHiDa d WHERE d.dhiInt4 = :dhiInt4"),
    @NamedQuery(name = "DaneHiDa.findByDhiNumeric1", query = "SELECT d FROM DaneHiDa d WHERE d.dhiNumeric1 = :dhiNumeric1"),
    @NamedQuery(name = "DaneHiDa.findByDhiNumeric2", query = "SELECT d FROM DaneHiDa d WHERE d.dhiNumeric2 = :dhiNumeric2"),
    @NamedQuery(name = "DaneHiDa.findByDhiNumeric3", query = "SELECT d FROM DaneHiDa d WHERE d.dhiNumeric3 = :dhiNumeric3"),
    @NamedQuery(name = "DaneHiDa.findByDhiNumeric4", query = "SELECT d FROM DaneHiDa d WHERE d.dhiNumeric4 = :dhiNumeric4"),
    @NamedQuery(name = "DaneHiDa.findByDhi2char1", query = "SELECT d FROM DaneHiDa d WHERE d.dhi2char1 = :dhi2char1"),
    @NamedQuery(name = "DaneHiDa.findByDhi2char2", query = "SELECT d FROM DaneHiDa d WHERE d.dhi2char2 = :dhi2char2"),
    @NamedQuery(name = "DaneHiDa.findByDhiRecord", query = "SELECT d FROM DaneHiDa d WHERE d.dhiRecord = :dhiRecord"),
    @NamedQuery(name = "DaneHiDa.findByDhiStatus", query = "SELECT d FROM DaneHiDa d WHERE d.dhiStatus = :dhiStatus"),
    @NamedQuery(name = "DaneHiDa.findByDhiTime1", query = "SELECT d FROM DaneHiDa d WHERE d.dhiTime1 = :dhiTime1"),
    @NamedQuery(name = "DaneHiDa.findByDhiTime2", query = "SELECT d FROM DaneHiDa d WHERE d.dhiTime2 = :dhiTime2")})
public class DaneHiDa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dhi_serial", nullable = false)
    private Integer dhiSerial;
    @Column(name = "dhi_sys_serial")
    private Integer dhiSysSerial;
    @Column(name = "dhi_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhiDataOd;
    @Column(name = "dhi_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhiDataDo;
    @Column(name = "dhi_char_1")
    private Character dhiChar1;
    @Column(name = "dhi_char_2")
    private Character dhiChar2;
    @Column(name = "dhi_char_3")
    private Character dhiChar3;
    @Column(name = "dhi_char_4")
    private Character dhiChar4;
    @Size(max = 254)
    @Column(name = "dhi_vchar_1", length = 254)
    private String dhiVchar1;
    @Size(max = 254)
    @Column(name = "dhi_vchar_2", length = 254)
    private String dhiVchar2;
    @Size(max = 254)
    @Column(name = "dhi_vchar_3", length = 254)
    private String dhiVchar3;
    @Size(max = 254)
    @Column(name = "dhi_vchar_4", length = 254)
    private String dhiVchar4;
    @Column(name = "dhi_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhiDate1;
    @Column(name = "dhi_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhiDate2;
    @Column(name = "dhi_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhiDate3;
    @Column(name = "dhi_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhiDate4;
    @Column(name = "dhi_int_1")
    private Integer dhiInt1;
    @Column(name = "dhi_int_2")
    private Integer dhiInt2;
    @Column(name = "dhi_int_3")
    private Integer dhiInt3;
    @Column(name = "dhi_int_4")
    private Integer dhiInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dhi_numeric_1", precision = 17, scale = 6)
    private BigDecimal dhiNumeric1;
    @Column(name = "dhi_numeric_2", precision = 17, scale = 6)
    private BigDecimal dhiNumeric2;
    @Column(name = "dhi_numeric_3", precision = 17, scale = 6)
    private BigDecimal dhiNumeric3;
    @Column(name = "dhi_numeric_4", precision = 17, scale = 6)
    private BigDecimal dhiNumeric4;
    @Size(max = 2)
    @Column(name = "dhi_2char_1", length = 2)
    private String dhi2char1;
    @Size(max = 2)
    @Column(name = "dhi_2char_2", length = 2)
    private String dhi2char2;
    @Size(max = 4)
    @Column(name = "dhi_record", length = 4)
    private String dhiRecord;
    @Column(name = "dhi_status")
    private Character dhiStatus;
    @Column(name = "dhi_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhiTime1;
    @Column(name = "dhi_time_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dhiTime2;
    @JoinColumn(name = "dhi_dls_serial", referencedColumnName = "dls_serial")
    @ManyToOne
    private DaneLiSl dhiDlsSerial;
    @JoinColumn(name = "dhi_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma dhiFirSerial;
    @JoinColumn(name = "dhi_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent dhiKonSerial;
    @JoinColumn(name = "dhi_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba dhiOsoSerial;
    @JoinColumn(name = "dhi_osr_serial", referencedColumnName = "osr_serial")
    @ManyToOne
    private OsobaRod dhiOsrSerial;

    public DaneHiDa() {
    }

    public DaneHiDa(Integer dhiSerial) {
        this.dhiSerial = dhiSerial;
    }

    public Integer getDhiSerial() {
        return dhiSerial;
    }

    public void setDhiSerial(Integer dhiSerial) {
        this.dhiSerial = dhiSerial;
    }

    public Integer getDhiSysSerial() {
        return dhiSysSerial;
    }

    public void setDhiSysSerial(Integer dhiSysSerial) {
        this.dhiSysSerial = dhiSysSerial;
    }

    public Date getDhiDataOd() {
        return dhiDataOd;
    }

    public void setDhiDataOd(Date dhiDataOd) {
        this.dhiDataOd = dhiDataOd;
    }

    public Date getDhiDataDo() {
        return dhiDataDo;
    }

    public void setDhiDataDo(Date dhiDataDo) {
        this.dhiDataDo = dhiDataDo;
    }

    public Character getDhiChar1() {
        return dhiChar1;
    }

    public void setDhiChar1(Character dhiChar1) {
        this.dhiChar1 = dhiChar1;
    }

    public Character getDhiChar2() {
        return dhiChar2;
    }

    public void setDhiChar2(Character dhiChar2) {
        this.dhiChar2 = dhiChar2;
    }

    public Character getDhiChar3() {
        return dhiChar3;
    }

    public void setDhiChar3(Character dhiChar3) {
        this.dhiChar3 = dhiChar3;
    }

    public Character getDhiChar4() {
        return dhiChar4;
    }

    public void setDhiChar4(Character dhiChar4) {
        this.dhiChar4 = dhiChar4;
    }

    public String getDhiVchar1() {
        return dhiVchar1;
    }

    public void setDhiVchar1(String dhiVchar1) {
        this.dhiVchar1 = dhiVchar1;
    }

    public String getDhiVchar2() {
        return dhiVchar2;
    }

    public void setDhiVchar2(String dhiVchar2) {
        this.dhiVchar2 = dhiVchar2;
    }

    public String getDhiVchar3() {
        return dhiVchar3;
    }

    public void setDhiVchar3(String dhiVchar3) {
        this.dhiVchar3 = dhiVchar3;
    }

    public String getDhiVchar4() {
        return dhiVchar4;
    }

    public void setDhiVchar4(String dhiVchar4) {
        this.dhiVchar4 = dhiVchar4;
    }

    public Date getDhiDate1() {
        return dhiDate1;
    }

    public void setDhiDate1(Date dhiDate1) {
        this.dhiDate1 = dhiDate1;
    }

    public Date getDhiDate2() {
        return dhiDate2;
    }

    public void setDhiDate2(Date dhiDate2) {
        this.dhiDate2 = dhiDate2;
    }

    public Date getDhiDate3() {
        return dhiDate3;
    }

    public void setDhiDate3(Date dhiDate3) {
        this.dhiDate3 = dhiDate3;
    }

    public Date getDhiDate4() {
        return dhiDate4;
    }

    public void setDhiDate4(Date dhiDate4) {
        this.dhiDate4 = dhiDate4;
    }

    public Integer getDhiInt1() {
        return dhiInt1;
    }

    public void setDhiInt1(Integer dhiInt1) {
        this.dhiInt1 = dhiInt1;
    }

    public Integer getDhiInt2() {
        return dhiInt2;
    }

    public void setDhiInt2(Integer dhiInt2) {
        this.dhiInt2 = dhiInt2;
    }

    public Integer getDhiInt3() {
        return dhiInt3;
    }

    public void setDhiInt3(Integer dhiInt3) {
        this.dhiInt3 = dhiInt3;
    }

    public Integer getDhiInt4() {
        return dhiInt4;
    }

    public void setDhiInt4(Integer dhiInt4) {
        this.dhiInt4 = dhiInt4;
    }

    public BigDecimal getDhiNumeric1() {
        return dhiNumeric1;
    }

    public void setDhiNumeric1(BigDecimal dhiNumeric1) {
        this.dhiNumeric1 = dhiNumeric1;
    }

    public BigDecimal getDhiNumeric2() {
        return dhiNumeric2;
    }

    public void setDhiNumeric2(BigDecimal dhiNumeric2) {
        this.dhiNumeric2 = dhiNumeric2;
    }

    public BigDecimal getDhiNumeric3() {
        return dhiNumeric3;
    }

    public void setDhiNumeric3(BigDecimal dhiNumeric3) {
        this.dhiNumeric3 = dhiNumeric3;
    }

    public BigDecimal getDhiNumeric4() {
        return dhiNumeric4;
    }

    public void setDhiNumeric4(BigDecimal dhiNumeric4) {
        this.dhiNumeric4 = dhiNumeric4;
    }

    public String getDhi2char1() {
        return dhi2char1;
    }

    public void setDhi2char1(String dhi2char1) {
        this.dhi2char1 = dhi2char1;
    }

    public String getDhi2char2() {
        return dhi2char2;
    }

    public void setDhi2char2(String dhi2char2) {
        this.dhi2char2 = dhi2char2;
    }

    public String getDhiRecord() {
        return dhiRecord;
    }

    public void setDhiRecord(String dhiRecord) {
        this.dhiRecord = dhiRecord;
    }

    public Character getDhiStatus() {
        return dhiStatus;
    }

    public void setDhiStatus(Character dhiStatus) {
        this.dhiStatus = dhiStatus;
    }

    public Date getDhiTime1() {
        return dhiTime1;
    }

    public void setDhiTime1(Date dhiTime1) {
        this.dhiTime1 = dhiTime1;
    }

    public Date getDhiTime2() {
        return dhiTime2;
    }

    public void setDhiTime2(Date dhiTime2) {
        this.dhiTime2 = dhiTime2;
    }

    public DaneLiSl getDhiDlsSerial() {
        return dhiDlsSerial;
    }

    public void setDhiDlsSerial(DaneLiSl dhiDlsSerial) {
        this.dhiDlsSerial = dhiDlsSerial;
    }

    public Firma getDhiFirSerial() {
        return dhiFirSerial;
    }

    public void setDhiFirSerial(Firma dhiFirSerial) {
        this.dhiFirSerial = dhiFirSerial;
    }

    public Kontrahent getDhiKonSerial() {
        return dhiKonSerial;
    }

    public void setDhiKonSerial(Kontrahent dhiKonSerial) {
        this.dhiKonSerial = dhiKonSerial;
    }

    public Osoba getDhiOsoSerial() {
        return dhiOsoSerial;
    }

    public void setDhiOsoSerial(Osoba dhiOsoSerial) {
        this.dhiOsoSerial = dhiOsoSerial;
    }

    public OsobaRod getDhiOsrSerial() {
        return dhiOsrSerial;
    }

    public void setDhiOsrSerial(OsobaRod dhiOsrSerial) {
        this.dhiOsrSerial = dhiOsrSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dhiSerial != null ? dhiSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneHiDa)) {
            return false;
        }
        DaneHiDa other = (DaneHiDa) object;
        if ((this.dhiSerial == null && other.dhiSerial != null) || (this.dhiSerial != null && !this.dhiSerial.equals(other.dhiSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneHiDa[ dhiSerial=" + dhiSerial + " ]";
    }
    
}
