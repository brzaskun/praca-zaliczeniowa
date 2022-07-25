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
@Table(name = "dane_stat", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStat.findAll", query = "SELECT d FROM DaneStat d"),
    @NamedQuery(name = "DaneStat.findByDstSerial", query = "SELECT d FROM DaneStat d WHERE d.dstSerial = :dstSerial"),
    @NamedQuery(name = "DaneStat.findByDstVchar1", query = "SELECT d FROM DaneStat d WHERE d.dstVchar1 = :dstVchar1"),
    @NamedQuery(name = "DaneStat.findByDstNumeric1", query = "SELECT d FROM DaneStat d WHERE d.dstNumeric1 = :dstNumeric1"),
    @NamedQuery(name = "DaneStat.findByDstNumeric2", query = "SELECT d FROM DaneStat d WHERE d.dstNumeric2 = :dstNumeric2"),
    @NamedQuery(name = "DaneStat.findByDstChar1", query = "SELECT d FROM DaneStat d WHERE d.dstChar1 = :dstChar1"),
    @NamedQuery(name = "DaneStat.findByDstInt1", query = "SELECT d FROM DaneStat d WHERE d.dstInt1 = :dstInt1"),
    @NamedQuery(name = "DaneStat.findByDstInt2", query = "SELECT d FROM DaneStat d WHERE d.dstInt2 = :dstInt2"),
    @NamedQuery(name = "DaneStat.findByDstChar2", query = "SELECT d FROM DaneStat d WHERE d.dstChar2 = :dstChar2"),
    @NamedQuery(name = "DaneStat.findByDstVchar2", query = "SELECT d FROM DaneStat d WHERE d.dstVchar2 = :dstVchar2"),
    @NamedQuery(name = "DaneStat.findByDstSysSerial", query = "SELECT d FROM DaneStat d WHERE d.dstSysSerial = :dstSysSerial"),
    @NamedQuery(name = "DaneStat.findByDst2char1", query = "SELECT d FROM DaneStat d WHERE d.dst2char1 = :dst2char1"),
    @NamedQuery(name = "DaneStat.findByDst2char2", query = "SELECT d FROM DaneStat d WHERE d.dst2char2 = :dst2char2"),
    @NamedQuery(name = "DaneStat.findByDstInt3", query = "SELECT d FROM DaneStat d WHERE d.dstInt3 = :dstInt3"),
    @NamedQuery(name = "DaneStat.findByDstInt4", query = "SELECT d FROM DaneStat d WHERE d.dstInt4 = :dstInt4"),
    @NamedQuery(name = "DaneStat.findByDstNumeric3", query = "SELECT d FROM DaneStat d WHERE d.dstNumeric3 = :dstNumeric3"),
    @NamedQuery(name = "DaneStat.findByDstNumeric4", query = "SELECT d FROM DaneStat d WHERE d.dstNumeric4 = :dstNumeric4"),
    @NamedQuery(name = "DaneStat.findByDstDate1", query = "SELECT d FROM DaneStat d WHERE d.dstDate1 = :dstDate1"),
    @NamedQuery(name = "DaneStat.findByDstDate2", query = "SELECT d FROM DaneStat d WHERE d.dstDate2 = :dstDate2"),
    @NamedQuery(name = "DaneStat.findByDstTime1", query = "SELECT d FROM DaneStat d WHERE d.dstTime1 = :dstTime1")})
public class DaneStat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dst_serial", nullable = false)
    private Integer dstSerial;
    @Size(max = 128)
    @Column(name = "dst_vchar_1", length = 128)
    private String dstVchar1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dst_numeric_1", precision = 17, scale = 6)
    private BigDecimal dstNumeric1;
    @Column(name = "dst_numeric_2", precision = 17, scale = 6)
    private BigDecimal dstNumeric2;
    @Column(name = "dst_char_1")
    private Character dstChar1;
    @Column(name = "dst_int_1")
    private Integer dstInt1;
    @Column(name = "dst_int_2")
    private Integer dstInt2;
    @Column(name = "dst_char_2")
    private Character dstChar2;
    @Size(max = 128)
    @Column(name = "dst_vchar_2", length = 128)
    private String dstVchar2;
    @Column(name = "dst_sys_serial")
    private Integer dstSysSerial;
    @Size(max = 2)
    @Column(name = "dst_2char_1", length = 2)
    private String dst2char1;
    @Size(max = 2)
    @Column(name = "dst_2char_2", length = 2)
    private String dst2char2;
    @Column(name = "dst_int_3")
    private Integer dstInt3;
    @Column(name = "dst_int_4")
    private Integer dstInt4;
    @Column(name = "dst_numeric_3", precision = 17, scale = 6)
    private BigDecimal dstNumeric3;
    @Column(name = "dst_numeric_4", precision = 17, scale = 6)
    private BigDecimal dstNumeric4;
    @Column(name = "dst_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dstDate1;
    @Column(name = "dst_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dstDate2;
    @Column(name = "dst_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dstTime1;
    @JoinColumn(name = "dst_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma dstFirSerial;
    @JoinColumn(name = "dst_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba dstOsoSerial;
    @JoinColumn(name = "dst_osr_serial", referencedColumnName = "osr_serial")
    @ManyToOne
    private OsobaRod dstOsrSerial;

    public DaneStat() {
    }

    public DaneStat(Integer dstSerial) {
        this.dstSerial = dstSerial;
    }

    public Integer getDstSerial() {
        return dstSerial;
    }

    public void setDstSerial(Integer dstSerial) {
        this.dstSerial = dstSerial;
    }

    public String getDstVchar1() {
        return dstVchar1;
    }

    public void setDstVchar1(String dstVchar1) {
        this.dstVchar1 = dstVchar1;
    }

    public BigDecimal getDstNumeric1() {
        return dstNumeric1;
    }

    public void setDstNumeric1(BigDecimal dstNumeric1) {
        this.dstNumeric1 = dstNumeric1;
    }

    public BigDecimal getDstNumeric2() {
        return dstNumeric2;
    }

    public void setDstNumeric2(BigDecimal dstNumeric2) {
        this.dstNumeric2 = dstNumeric2;
    }

    public Character getDstChar1() {
        return dstChar1;
    }

    public void setDstChar1(Character dstChar1) {
        this.dstChar1 = dstChar1;
    }

    public Integer getDstInt1() {
        return dstInt1;
    }

    public void setDstInt1(Integer dstInt1) {
        this.dstInt1 = dstInt1;
    }

    public Integer getDstInt2() {
        return dstInt2;
    }

    public void setDstInt2(Integer dstInt2) {
        this.dstInt2 = dstInt2;
    }

    public Character getDstChar2() {
        return dstChar2;
    }

    public void setDstChar2(Character dstChar2) {
        this.dstChar2 = dstChar2;
    }

    public String getDstVchar2() {
        return dstVchar2;
    }

    public void setDstVchar2(String dstVchar2) {
        this.dstVchar2 = dstVchar2;
    }

    public Integer getDstSysSerial() {
        return dstSysSerial;
    }

    public void setDstSysSerial(Integer dstSysSerial) {
        this.dstSysSerial = dstSysSerial;
    }

    public String getDst2char1() {
        return dst2char1;
    }

    public void setDst2char1(String dst2char1) {
        this.dst2char1 = dst2char1;
    }

    public String getDst2char2() {
        return dst2char2;
    }

    public void setDst2char2(String dst2char2) {
        this.dst2char2 = dst2char2;
    }

    public Integer getDstInt3() {
        return dstInt3;
    }

    public void setDstInt3(Integer dstInt3) {
        this.dstInt3 = dstInt3;
    }

    public Integer getDstInt4() {
        return dstInt4;
    }

    public void setDstInt4(Integer dstInt4) {
        this.dstInt4 = dstInt4;
    }

    public BigDecimal getDstNumeric3() {
        return dstNumeric3;
    }

    public void setDstNumeric3(BigDecimal dstNumeric3) {
        this.dstNumeric3 = dstNumeric3;
    }

    public BigDecimal getDstNumeric4() {
        return dstNumeric4;
    }

    public void setDstNumeric4(BigDecimal dstNumeric4) {
        this.dstNumeric4 = dstNumeric4;
    }

    public Date getDstDate1() {
        return dstDate1;
    }

    public void setDstDate1(Date dstDate1) {
        this.dstDate1 = dstDate1;
    }

    public Date getDstDate2() {
        return dstDate2;
    }

    public void setDstDate2(Date dstDate2) {
        this.dstDate2 = dstDate2;
    }

    public Date getDstTime1() {
        return dstTime1;
    }

    public void setDstTime1(Date dstTime1) {
        this.dstTime1 = dstTime1;
    }

    public Firma getDstFirSerial() {
        return dstFirSerial;
    }

    public void setDstFirSerial(Firma dstFirSerial) {
        this.dstFirSerial = dstFirSerial;
    }

    public Osoba getDstOsoSerial() {
        return dstOsoSerial;
    }

    public void setDstOsoSerial(Osoba dstOsoSerial) {
        this.dstOsoSerial = dstOsoSerial;
    }

    public OsobaRod getDstOsrSerial() {
        return dstOsrSerial;
    }

    public void setDstOsrSerial(OsobaRod dstOsrSerial) {
        this.dstOsrSerial = dstOsrSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dstSerial != null ? dstSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStat)) {
            return false;
        }
        DaneStat other = (DaneStat) object;
        if ((this.dstSerial == null && other.dstSerial != null) || (this.dstSerial != null && !this.dstSerial.equals(other.dstSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStat[ dstSerial=" + dstSerial + " ]";
    }
    
}
