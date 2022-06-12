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
@Table(name = "dane_stat_a", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatA.findAll", query = "SELECT d FROM DaneStatA d"),
    @NamedQuery(name = "DaneStatA.findByDsaSerial", query = "SELECT d FROM DaneStatA d WHERE d.dsaSerial = :dsaSerial"),
    @NamedQuery(name = "DaneStatA.findByDsaSysSerial", query = "SELECT d FROM DaneStatA d WHERE d.dsaSysSerial = :dsaSysSerial"),
    @NamedQuery(name = "DaneStatA.findByDsaChar1", query = "SELECT d FROM DaneStatA d WHERE d.dsaChar1 = :dsaChar1"),
    @NamedQuery(name = "DaneStatA.findByDsaChar2", query = "SELECT d FROM DaneStatA d WHERE d.dsaChar2 = :dsaChar2"),
    @NamedQuery(name = "DaneStatA.findByDsa2char1", query = "SELECT d FROM DaneStatA d WHERE d.dsa2char1 = :dsa2char1"),
    @NamedQuery(name = "DaneStatA.findByDsa2char2", query = "SELECT d FROM DaneStatA d WHERE d.dsa2char2 = :dsa2char2"),
    @NamedQuery(name = "DaneStatA.findByDsaVchar1", query = "SELECT d FROM DaneStatA d WHERE d.dsaVchar1 = :dsaVchar1"),
    @NamedQuery(name = "DaneStatA.findByDsaVchar2", query = "SELECT d FROM DaneStatA d WHERE d.dsaVchar2 = :dsaVchar2"),
    @NamedQuery(name = "DaneStatA.findByDsaNumeric1", query = "SELECT d FROM DaneStatA d WHERE d.dsaNumeric1 = :dsaNumeric1"),
    @NamedQuery(name = "DaneStatA.findByDsaNumeric2", query = "SELECT d FROM DaneStatA d WHERE d.dsaNumeric2 = :dsaNumeric2"),
    @NamedQuery(name = "DaneStatA.findByDsaNumeric3", query = "SELECT d FROM DaneStatA d WHERE d.dsaNumeric3 = :dsaNumeric3"),
    @NamedQuery(name = "DaneStatA.findByDsaNumeric4", query = "SELECT d FROM DaneStatA d WHERE d.dsaNumeric4 = :dsaNumeric4"),
    @NamedQuery(name = "DaneStatA.findByDsaDate1", query = "SELECT d FROM DaneStatA d WHERE d.dsaDate1 = :dsaDate1"),
    @NamedQuery(name = "DaneStatA.findByDsaDate2", query = "SELECT d FROM DaneStatA d WHERE d.dsaDate2 = :dsaDate2"),
    @NamedQuery(name = "DaneStatA.findByDsaInt1", query = "SELECT d FROM DaneStatA d WHERE d.dsaInt1 = :dsaInt1"),
    @NamedQuery(name = "DaneStatA.findByDsaInt2", query = "SELECT d FROM DaneStatA d WHERE d.dsaInt2 = :dsaInt2"),
    @NamedQuery(name = "DaneStatA.findByDsaInt3", query = "SELECT d FROM DaneStatA d WHERE d.dsaInt3 = :dsaInt3"),
    @NamedQuery(name = "DaneStatA.findByDsaInt4", query = "SELECT d FROM DaneStatA d WHERE d.dsaInt4 = :dsaInt4"),
    @NamedQuery(name = "DaneStatA.findByDsaTime1", query = "SELECT d FROM DaneStatA d WHERE d.dsaTime1 = :dsaTime1")})
public class DaneStatA implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsa_serial", nullable = false)
    private Integer dsaSerial;
    @Column(name = "dsa_sys_serial")
    private Integer dsaSysSerial;
    @Column(name = "dsa_char_1")
    private Character dsaChar1;
    @Column(name = "dsa_char_2")
    private Character dsaChar2;
    @Size(max = 2)
    @Column(name = "dsa_2char_1", length = 2)
    private String dsa2char1;
    @Size(max = 2)
    @Column(name = "dsa_2char_2", length = 2)
    private String dsa2char2;
    @Size(max = 128)
    @Column(name = "dsa_vchar_1", length = 128)
    private String dsaVchar1;
    @Size(max = 128)
    @Column(name = "dsa_vchar_2", length = 128)
    private String dsaVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsa_numeric_1", precision = 17, scale = 6)
    private BigDecimal dsaNumeric1;
    @Column(name = "dsa_numeric_2", precision = 17, scale = 6)
    private BigDecimal dsaNumeric2;
    @Column(name = "dsa_numeric_3", precision = 17, scale = 6)
    private BigDecimal dsaNumeric3;
    @Column(name = "dsa_numeric_4", precision = 17, scale = 6)
    private BigDecimal dsaNumeric4;
    @Column(name = "dsa_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsaDate1;
    @Column(name = "dsa_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsaDate2;
    @Column(name = "dsa_int_1")
    private Integer dsaInt1;
    @Column(name = "dsa_int_2")
    private Integer dsaInt2;
    @Column(name = "dsa_int_3")
    private Integer dsaInt3;
    @Column(name = "dsa_int_4")
    private Integer dsaInt4;
    @Column(name = "dsa_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsaTime1;
    @JoinColumn(name = "dsa_par_serial", referencedColumnName = "par_serial")
    @ManyToOne
    private Paragon dsaParSerial;

    public DaneStatA() {
    }

    public DaneStatA(Integer dsaSerial) {
        this.dsaSerial = dsaSerial;
    }

    public Integer getDsaSerial() {
        return dsaSerial;
    }

    public void setDsaSerial(Integer dsaSerial) {
        this.dsaSerial = dsaSerial;
    }

    public Integer getDsaSysSerial() {
        return dsaSysSerial;
    }

    public void setDsaSysSerial(Integer dsaSysSerial) {
        this.dsaSysSerial = dsaSysSerial;
    }

    public Character getDsaChar1() {
        return dsaChar1;
    }

    public void setDsaChar1(Character dsaChar1) {
        this.dsaChar1 = dsaChar1;
    }

    public Character getDsaChar2() {
        return dsaChar2;
    }

    public void setDsaChar2(Character dsaChar2) {
        this.dsaChar2 = dsaChar2;
    }

    public String getDsa2char1() {
        return dsa2char1;
    }

    public void setDsa2char1(String dsa2char1) {
        this.dsa2char1 = dsa2char1;
    }

    public String getDsa2char2() {
        return dsa2char2;
    }

    public void setDsa2char2(String dsa2char2) {
        this.dsa2char2 = dsa2char2;
    }

    public String getDsaVchar1() {
        return dsaVchar1;
    }

    public void setDsaVchar1(String dsaVchar1) {
        this.dsaVchar1 = dsaVchar1;
    }

    public String getDsaVchar2() {
        return dsaVchar2;
    }

    public void setDsaVchar2(String dsaVchar2) {
        this.dsaVchar2 = dsaVchar2;
    }

    public BigDecimal getDsaNumeric1() {
        return dsaNumeric1;
    }

    public void setDsaNumeric1(BigDecimal dsaNumeric1) {
        this.dsaNumeric1 = dsaNumeric1;
    }

    public BigDecimal getDsaNumeric2() {
        return dsaNumeric2;
    }

    public void setDsaNumeric2(BigDecimal dsaNumeric2) {
        this.dsaNumeric2 = dsaNumeric2;
    }

    public BigDecimal getDsaNumeric3() {
        return dsaNumeric3;
    }

    public void setDsaNumeric3(BigDecimal dsaNumeric3) {
        this.dsaNumeric3 = dsaNumeric3;
    }

    public BigDecimal getDsaNumeric4() {
        return dsaNumeric4;
    }

    public void setDsaNumeric4(BigDecimal dsaNumeric4) {
        this.dsaNumeric4 = dsaNumeric4;
    }

    public Date getDsaDate1() {
        return dsaDate1;
    }

    public void setDsaDate1(Date dsaDate1) {
        this.dsaDate1 = dsaDate1;
    }

    public Date getDsaDate2() {
        return dsaDate2;
    }

    public void setDsaDate2(Date dsaDate2) {
        this.dsaDate2 = dsaDate2;
    }

    public Integer getDsaInt1() {
        return dsaInt1;
    }

    public void setDsaInt1(Integer dsaInt1) {
        this.dsaInt1 = dsaInt1;
    }

    public Integer getDsaInt2() {
        return dsaInt2;
    }

    public void setDsaInt2(Integer dsaInt2) {
        this.dsaInt2 = dsaInt2;
    }

    public Integer getDsaInt3() {
        return dsaInt3;
    }

    public void setDsaInt3(Integer dsaInt3) {
        this.dsaInt3 = dsaInt3;
    }

    public Integer getDsaInt4() {
        return dsaInt4;
    }

    public void setDsaInt4(Integer dsaInt4) {
        this.dsaInt4 = dsaInt4;
    }

    public Date getDsaTime1() {
        return dsaTime1;
    }

    public void setDsaTime1(Date dsaTime1) {
        this.dsaTime1 = dsaTime1;
    }

    public Paragon getDsaParSerial() {
        return dsaParSerial;
    }

    public void setDsaParSerial(Paragon dsaParSerial) {
        this.dsaParSerial = dsaParSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dsaSerial != null ? dsaSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatA)) {
            return false;
        }
        DaneStatA other = (DaneStatA) object;
        if ((this.dsaSerial == null && other.dsaSerial != null) || (this.dsaSerial != null && !this.dsaSerial.equals(other.dsaSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatA[ dsaSerial=" + dsaSerial + " ]";
    }
    
}
