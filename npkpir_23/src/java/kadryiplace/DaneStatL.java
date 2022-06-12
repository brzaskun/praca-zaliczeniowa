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
@Table(name = "dane_stat_l", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatL.findAll", query = "SELECT d FROM DaneStatL d"),
    @NamedQuery(name = "DaneStatL.findByDslSerial", query = "SELECT d FROM DaneStatL d WHERE d.dslSerial = :dslSerial"),
    @NamedQuery(name = "DaneStatL.findByDslSysSerial", query = "SELECT d FROM DaneStatL d WHERE d.dslSysSerial = :dslSysSerial"),
    @NamedQuery(name = "DaneStatL.findByDslChar1", query = "SELECT d FROM DaneStatL d WHERE d.dslChar1 = :dslChar1"),
    @NamedQuery(name = "DaneStatL.findByDslChar2", query = "SELECT d FROM DaneStatL d WHERE d.dslChar2 = :dslChar2"),
    @NamedQuery(name = "DaneStatL.findByDsl2char1", query = "SELECT d FROM DaneStatL d WHERE d.dsl2char1 = :dsl2char1"),
    @NamedQuery(name = "DaneStatL.findByDsl2char2", query = "SELECT d FROM DaneStatL d WHERE d.dsl2char2 = :dsl2char2"),
    @NamedQuery(name = "DaneStatL.findByDslVchar1", query = "SELECT d FROM DaneStatL d WHERE d.dslVchar1 = :dslVchar1"),
    @NamedQuery(name = "DaneStatL.findByDslVchar2", query = "SELECT d FROM DaneStatL d WHERE d.dslVchar2 = :dslVchar2"),
    @NamedQuery(name = "DaneStatL.findByDslInt1", query = "SELECT d FROM DaneStatL d WHERE d.dslInt1 = :dslInt1"),
    @NamedQuery(name = "DaneStatL.findByDslInt2", query = "SELECT d FROM DaneStatL d WHERE d.dslInt2 = :dslInt2"),
    @NamedQuery(name = "DaneStatL.findByDslInt3", query = "SELECT d FROM DaneStatL d WHERE d.dslInt3 = :dslInt3"),
    @NamedQuery(name = "DaneStatL.findByDslInt4", query = "SELECT d FROM DaneStatL d WHERE d.dslInt4 = :dslInt4"),
    @NamedQuery(name = "DaneStatL.findByDslNumeric1", query = "SELECT d FROM DaneStatL d WHERE d.dslNumeric1 = :dslNumeric1"),
    @NamedQuery(name = "DaneStatL.findByDslNumeric2", query = "SELECT d FROM DaneStatL d WHERE d.dslNumeric2 = :dslNumeric2"),
    @NamedQuery(name = "DaneStatL.findByDslNumeric3", query = "SELECT d FROM DaneStatL d WHERE d.dslNumeric3 = :dslNumeric3"),
    @NamedQuery(name = "DaneStatL.findByDslNumeric4", query = "SELECT d FROM DaneStatL d WHERE d.dslNumeric4 = :dslNumeric4"),
    @NamedQuery(name = "DaneStatL.findByDslDate1", query = "SELECT d FROM DaneStatL d WHERE d.dslDate1 = :dslDate1"),
    @NamedQuery(name = "DaneStatL.findByDslDate2", query = "SELECT d FROM DaneStatL d WHERE d.dslDate2 = :dslDate2"),
    @NamedQuery(name = "DaneStatL.findByDslTime1", query = "SELECT d FROM DaneStatL d WHERE d.dslTime1 = :dslTime1")})
public class DaneStatL implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsl_serial", nullable = false)
    private Integer dslSerial;
    @Column(name = "dsl_sys_serial")
    private Integer dslSysSerial;
    @Column(name = "dsl_char_1")
    private Character dslChar1;
    @Column(name = "dsl_char_2")
    private Character dslChar2;
    @Size(max = 2)
    @Column(name = "dsl_2char_1", length = 2)
    private String dsl2char1;
    @Size(max = 2)
    @Column(name = "dsl_2char_2", length = 2)
    private String dsl2char2;
    @Size(max = 128)
    @Column(name = "dsl_vchar_1", length = 128)
    private String dslVchar1;
    @Size(max = 128)
    @Column(name = "dsl_vchar_2", length = 128)
    private String dslVchar2;
    @Column(name = "dsl_int_1")
    private Integer dslInt1;
    @Column(name = "dsl_int_2")
    private Integer dslInt2;
    @Column(name = "dsl_int_3")
    private Integer dslInt3;
    @Column(name = "dsl_int_4")
    private Integer dslInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsl_numeric_1", precision = 17, scale = 6)
    private BigDecimal dslNumeric1;
    @Column(name = "dsl_numeric_2", precision = 17, scale = 6)
    private BigDecimal dslNumeric2;
    @Column(name = "dsl_numeric_3", precision = 17, scale = 6)
    private BigDecimal dslNumeric3;
    @Column(name = "dsl_numeric_4", precision = 17, scale = 6)
    private BigDecimal dslNumeric4;
    @Column(name = "dsl_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dslDate1;
    @Column(name = "dsl_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dslDate2;
    @Column(name = "dsl_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dslTime1;
    @JoinColumn(name = "dsl_lpl_serial", referencedColumnName = "lpl_serial")
    @ManyToOne
    private Place dslLplSerial;

    public DaneStatL() {
    }

    public DaneStatL(Integer dslSerial) {
        this.dslSerial = dslSerial;
    }

    public Integer getDslSerial() {
        return dslSerial;
    }

    public void setDslSerial(Integer dslSerial) {
        this.dslSerial = dslSerial;
    }

    public Integer getDslSysSerial() {
        return dslSysSerial;
    }

    public void setDslSysSerial(Integer dslSysSerial) {
        this.dslSysSerial = dslSysSerial;
    }

    public Character getDslChar1() {
        return dslChar1;
    }

    public void setDslChar1(Character dslChar1) {
        this.dslChar1 = dslChar1;
    }

    public Character getDslChar2() {
        return dslChar2;
    }

    public void setDslChar2(Character dslChar2) {
        this.dslChar2 = dslChar2;
    }

    public String getDsl2char1() {
        return dsl2char1;
    }

    public void setDsl2char1(String dsl2char1) {
        this.dsl2char1 = dsl2char1;
    }

    public String getDsl2char2() {
        return dsl2char2;
    }

    public void setDsl2char2(String dsl2char2) {
        this.dsl2char2 = dsl2char2;
    }

    public String getDslVchar1() {
        return dslVchar1;
    }

    public void setDslVchar1(String dslVchar1) {
        this.dslVchar1 = dslVchar1;
    }

    public String getDslVchar2() {
        return dslVchar2;
    }

    public void setDslVchar2(String dslVchar2) {
        this.dslVchar2 = dslVchar2;
    }

    public Integer getDslInt1() {
        return dslInt1;
    }

    public void setDslInt1(Integer dslInt1) {
        this.dslInt1 = dslInt1;
    }

    public Integer getDslInt2() {
        return dslInt2;
    }

    public void setDslInt2(Integer dslInt2) {
        this.dslInt2 = dslInt2;
    }

    public Integer getDslInt3() {
        return dslInt3;
    }

    public void setDslInt3(Integer dslInt3) {
        this.dslInt3 = dslInt3;
    }

    public Integer getDslInt4() {
        return dslInt4;
    }

    public void setDslInt4(Integer dslInt4) {
        this.dslInt4 = dslInt4;
    }

    public BigDecimal getDslNumeric1() {
        return dslNumeric1;
    }

    public void setDslNumeric1(BigDecimal dslNumeric1) {
        this.dslNumeric1 = dslNumeric1;
    }

    public BigDecimal getDslNumeric2() {
        return dslNumeric2;
    }

    public void setDslNumeric2(BigDecimal dslNumeric2) {
        this.dslNumeric2 = dslNumeric2;
    }

    public BigDecimal getDslNumeric3() {
        return dslNumeric3;
    }

    public void setDslNumeric3(BigDecimal dslNumeric3) {
        this.dslNumeric3 = dslNumeric3;
    }

    public BigDecimal getDslNumeric4() {
        return dslNumeric4;
    }

    public void setDslNumeric4(BigDecimal dslNumeric4) {
        this.dslNumeric4 = dslNumeric4;
    }

    public Date getDslDate1() {
        return dslDate1;
    }

    public void setDslDate1(Date dslDate1) {
        this.dslDate1 = dslDate1;
    }

    public Date getDslDate2() {
        return dslDate2;
    }

    public void setDslDate2(Date dslDate2) {
        this.dslDate2 = dslDate2;
    }

    public Date getDslTime1() {
        return dslTime1;
    }

    public void setDslTime1(Date dslTime1) {
        this.dslTime1 = dslTime1;
    }

    public Place getDslLplSerial() {
        return dslLplSerial;
    }

    public void setDslLplSerial(Place dslLplSerial) {
        this.dslLplSerial = dslLplSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dslSerial != null ? dslSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatL)) {
            return false;
        }
        DaneStatL other = (DaneStatL) object;
        if ((this.dslSerial == null && other.dslSerial != null) || (this.dslSerial != null && !this.dslSerial.equals(other.dslSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatL[ dslSerial=" + dslSerial + " ]";
    }
    
}
