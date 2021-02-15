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
@Table(name = "dane_stat_d", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatD.findAll", query = "SELECT d FROM DaneStatD d"),
    @NamedQuery(name = "DaneStatD.findByDsdSerial", query = "SELECT d FROM DaneStatD d WHERE d.dsdSerial = :dsdSerial"),
    @NamedQuery(name = "DaneStatD.findByDsdSysSerial", query = "SELECT d FROM DaneStatD d WHERE d.dsdSysSerial = :dsdSysSerial"),
    @NamedQuery(name = "DaneStatD.findByDsdChar1", query = "SELECT d FROM DaneStatD d WHERE d.dsdChar1 = :dsdChar1"),
    @NamedQuery(name = "DaneStatD.findByDsdChar2", query = "SELECT d FROM DaneStatD d WHERE d.dsdChar2 = :dsdChar2"),
    @NamedQuery(name = "DaneStatD.findByDsd2char1", query = "SELECT d FROM DaneStatD d WHERE d.dsd2char1 = :dsd2char1"),
    @NamedQuery(name = "DaneStatD.findByDsd2char2", query = "SELECT d FROM DaneStatD d WHERE d.dsd2char2 = :dsd2char2"),
    @NamedQuery(name = "DaneStatD.findByDsdVchar1", query = "SELECT d FROM DaneStatD d WHERE d.dsdVchar1 = :dsdVchar1"),
    @NamedQuery(name = "DaneStatD.findByDsdVchar2", query = "SELECT d FROM DaneStatD d WHERE d.dsdVchar2 = :dsdVchar2"),
    @NamedQuery(name = "DaneStatD.findByDsdNumeric1", query = "SELECT d FROM DaneStatD d WHERE d.dsdNumeric1 = :dsdNumeric1"),
    @NamedQuery(name = "DaneStatD.findByDsdNumeric2", query = "SELECT d FROM DaneStatD d WHERE d.dsdNumeric2 = :dsdNumeric2"),
    @NamedQuery(name = "DaneStatD.findByDsdNumeric3", query = "SELECT d FROM DaneStatD d WHERE d.dsdNumeric3 = :dsdNumeric3"),
    @NamedQuery(name = "DaneStatD.findByDsdNumeric4", query = "SELECT d FROM DaneStatD d WHERE d.dsdNumeric4 = :dsdNumeric4"),
    @NamedQuery(name = "DaneStatD.findByDsdDate1", query = "SELECT d FROM DaneStatD d WHERE d.dsdDate1 = :dsdDate1"),
    @NamedQuery(name = "DaneStatD.findByDsdDate2", query = "SELECT d FROM DaneStatD d WHERE d.dsdDate2 = :dsdDate2"),
    @NamedQuery(name = "DaneStatD.findByDsdInt1", query = "SELECT d FROM DaneStatD d WHERE d.dsdInt1 = :dsdInt1"),
    @NamedQuery(name = "DaneStatD.findByDsdInt2", query = "SELECT d FROM DaneStatD d WHERE d.dsdInt2 = :dsdInt2"),
    @NamedQuery(name = "DaneStatD.findByDsdInt3", query = "SELECT d FROM DaneStatD d WHERE d.dsdInt3 = :dsdInt3"),
    @NamedQuery(name = "DaneStatD.findByDsdInt4", query = "SELECT d FROM DaneStatD d WHERE d.dsdInt4 = :dsdInt4"),
    @NamedQuery(name = "DaneStatD.findByDsdTime1", query = "SELECT d FROM DaneStatD d WHERE d.dsdTime1 = :dsdTime1")})
public class DaneStatD implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsd_serial", nullable = false)
    private Integer dsdSerial;
    @Column(name = "dsd_sys_serial")
    private Integer dsdSysSerial;
    @Column(name = "dsd_char_1")
    private Character dsdChar1;
    @Column(name = "dsd_char_2")
    private Character dsdChar2;
    @Size(max = 2)
    @Column(name = "dsd_2char_1", length = 2)
    private String dsd2char1;
    @Size(max = 2)
    @Column(name = "dsd_2char_2", length = 2)
    private String dsd2char2;
    @Size(max = 128)
    @Column(name = "dsd_vchar_1", length = 128)
    private String dsdVchar1;
    @Size(max = 128)
    @Column(name = "dsd_vchar_2", length = 128)
    private String dsdVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsd_numeric_1", precision = 17, scale = 6)
    private BigDecimal dsdNumeric1;
    @Column(name = "dsd_numeric_2", precision = 17, scale = 6)
    private BigDecimal dsdNumeric2;
    @Column(name = "dsd_numeric_3", precision = 17, scale = 6)
    private BigDecimal dsdNumeric3;
    @Column(name = "dsd_numeric_4", precision = 17, scale = 6)
    private BigDecimal dsdNumeric4;
    @Column(name = "dsd_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsdDate1;
    @Column(name = "dsd_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsdDate2;
    @Column(name = "dsd_int_1")
    private Integer dsdInt1;
    @Column(name = "dsd_int_2")
    private Integer dsdInt2;
    @Column(name = "dsd_int_3")
    private Integer dsdInt3;
    @Column(name = "dsd_int_4")
    private Integer dsdInt4;
    @Column(name = "dsd_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsdTime1;
    @JoinColumn(name = "dsd_mdo_serial", referencedColumnName = "mdo_serial")
    @ManyToOne
    private Magdok dsdMdoSerial;
    @JoinColumn(name = "dsd_zdo_serial", referencedColumnName = "zdo_serial")
    @ManyToOne
    private Zakdok dsdZdoSerial;

    public DaneStatD() {
    }

    public DaneStatD(Integer dsdSerial) {
        this.dsdSerial = dsdSerial;
    }

    public Integer getDsdSerial() {
        return dsdSerial;
    }

    public void setDsdSerial(Integer dsdSerial) {
        this.dsdSerial = dsdSerial;
    }

    public Integer getDsdSysSerial() {
        return dsdSysSerial;
    }

    public void setDsdSysSerial(Integer dsdSysSerial) {
        this.dsdSysSerial = dsdSysSerial;
    }

    public Character getDsdChar1() {
        return dsdChar1;
    }

    public void setDsdChar1(Character dsdChar1) {
        this.dsdChar1 = dsdChar1;
    }

    public Character getDsdChar2() {
        return dsdChar2;
    }

    public void setDsdChar2(Character dsdChar2) {
        this.dsdChar2 = dsdChar2;
    }

    public String getDsd2char1() {
        return dsd2char1;
    }

    public void setDsd2char1(String dsd2char1) {
        this.dsd2char1 = dsd2char1;
    }

    public String getDsd2char2() {
        return dsd2char2;
    }

    public void setDsd2char2(String dsd2char2) {
        this.dsd2char2 = dsd2char2;
    }

    public String getDsdVchar1() {
        return dsdVchar1;
    }

    public void setDsdVchar1(String dsdVchar1) {
        this.dsdVchar1 = dsdVchar1;
    }

    public String getDsdVchar2() {
        return dsdVchar2;
    }

    public void setDsdVchar2(String dsdVchar2) {
        this.dsdVchar2 = dsdVchar2;
    }

    public BigDecimal getDsdNumeric1() {
        return dsdNumeric1;
    }

    public void setDsdNumeric1(BigDecimal dsdNumeric1) {
        this.dsdNumeric1 = dsdNumeric1;
    }

    public BigDecimal getDsdNumeric2() {
        return dsdNumeric2;
    }

    public void setDsdNumeric2(BigDecimal dsdNumeric2) {
        this.dsdNumeric2 = dsdNumeric2;
    }

    public BigDecimal getDsdNumeric3() {
        return dsdNumeric3;
    }

    public void setDsdNumeric3(BigDecimal dsdNumeric3) {
        this.dsdNumeric3 = dsdNumeric3;
    }

    public BigDecimal getDsdNumeric4() {
        return dsdNumeric4;
    }

    public void setDsdNumeric4(BigDecimal dsdNumeric4) {
        this.dsdNumeric4 = dsdNumeric4;
    }

    public Date getDsdDate1() {
        return dsdDate1;
    }

    public void setDsdDate1(Date dsdDate1) {
        this.dsdDate1 = dsdDate1;
    }

    public Date getDsdDate2() {
        return dsdDate2;
    }

    public void setDsdDate2(Date dsdDate2) {
        this.dsdDate2 = dsdDate2;
    }

    public Integer getDsdInt1() {
        return dsdInt1;
    }

    public void setDsdInt1(Integer dsdInt1) {
        this.dsdInt1 = dsdInt1;
    }

    public Integer getDsdInt2() {
        return dsdInt2;
    }

    public void setDsdInt2(Integer dsdInt2) {
        this.dsdInt2 = dsdInt2;
    }

    public Integer getDsdInt3() {
        return dsdInt3;
    }

    public void setDsdInt3(Integer dsdInt3) {
        this.dsdInt3 = dsdInt3;
    }

    public Integer getDsdInt4() {
        return dsdInt4;
    }

    public void setDsdInt4(Integer dsdInt4) {
        this.dsdInt4 = dsdInt4;
    }

    public Date getDsdTime1() {
        return dsdTime1;
    }

    public void setDsdTime1(Date dsdTime1) {
        this.dsdTime1 = dsdTime1;
    }

    public Magdok getDsdMdoSerial() {
        return dsdMdoSerial;
    }

    public void setDsdMdoSerial(Magdok dsdMdoSerial) {
        this.dsdMdoSerial = dsdMdoSerial;
    }

    public Zakdok getDsdZdoSerial() {
        return dsdZdoSerial;
    }

    public void setDsdZdoSerial(Zakdok dsdZdoSerial) {
        this.dsdZdoSerial = dsdZdoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dsdSerial != null ? dsdSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatD)) {
            return false;
        }
        DaneStatD other = (DaneStatD) object;
        if ((this.dsdSerial == null && other.dsdSerial != null) || (this.dsdSerial != null && !this.dsdSerial.equals(other.dsdSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatD[ dsdSerial=" + dsdSerial + " ]";
    }
    
}
