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
@Table(name = "dane_stat_r", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatR.findAll", query = "SELECT d FROM DaneStatR d"),
    @NamedQuery(name = "DaneStatR.findByDsrSerial", query = "SELECT d FROM DaneStatR d WHERE d.dsrSerial = :dsrSerial"),
    @NamedQuery(name = "DaneStatR.findByDsrSysSerial", query = "SELECT d FROM DaneStatR d WHERE d.dsrSysSerial = :dsrSysSerial"),
    @NamedQuery(name = "DaneStatR.findByDsrChar1", query = "SELECT d FROM DaneStatR d WHERE d.dsrChar1 = :dsrChar1"),
    @NamedQuery(name = "DaneStatR.findByDsrChar2", query = "SELECT d FROM DaneStatR d WHERE d.dsrChar2 = :dsrChar2"),
    @NamedQuery(name = "DaneStatR.findByDsr2char1", query = "SELECT d FROM DaneStatR d WHERE d.dsr2char1 = :dsr2char1"),
    @NamedQuery(name = "DaneStatR.findByDsr2char2", query = "SELECT d FROM DaneStatR d WHERE d.dsr2char2 = :dsr2char2"),
    @NamedQuery(name = "DaneStatR.findByDsrVchar1", query = "SELECT d FROM DaneStatR d WHERE d.dsrVchar1 = :dsrVchar1"),
    @NamedQuery(name = "DaneStatR.findByDsrVchar2", query = "SELECT d FROM DaneStatR d WHERE d.dsrVchar2 = :dsrVchar2"),
    @NamedQuery(name = "DaneStatR.findByDsrNumeric1", query = "SELECT d FROM DaneStatR d WHERE d.dsrNumeric1 = :dsrNumeric1"),
    @NamedQuery(name = "DaneStatR.findByDsrNumeric2", query = "SELECT d FROM DaneStatR d WHERE d.dsrNumeric2 = :dsrNumeric2"),
    @NamedQuery(name = "DaneStatR.findByDsrNumeric3", query = "SELECT d FROM DaneStatR d WHERE d.dsrNumeric3 = :dsrNumeric3"),
    @NamedQuery(name = "DaneStatR.findByDsrNumeric4", query = "SELECT d FROM DaneStatR d WHERE d.dsrNumeric4 = :dsrNumeric4"),
    @NamedQuery(name = "DaneStatR.findByDsrDate1", query = "SELECT d FROM DaneStatR d WHERE d.dsrDate1 = :dsrDate1"),
    @NamedQuery(name = "DaneStatR.findByDsrDate2", query = "SELECT d FROM DaneStatR d WHERE d.dsrDate2 = :dsrDate2"),
    @NamedQuery(name = "DaneStatR.findByDsrInt1", query = "SELECT d FROM DaneStatR d WHERE d.dsrInt1 = :dsrInt1"),
    @NamedQuery(name = "DaneStatR.findByDsrInt2", query = "SELECT d FROM DaneStatR d WHERE d.dsrInt2 = :dsrInt2"),
    @NamedQuery(name = "DaneStatR.findByDsrInt3", query = "SELECT d FROM DaneStatR d WHERE d.dsrInt3 = :dsrInt3"),
    @NamedQuery(name = "DaneStatR.findByDsrInt4", query = "SELECT d FROM DaneStatR d WHERE d.dsrInt4 = :dsrInt4"),
    @NamedQuery(name = "DaneStatR.findByDsrTime1", query = "SELECT d FROM DaneStatR d WHERE d.dsrTime1 = :dsrTime1")})
public class DaneStatR implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsr_serial", nullable = false)
    private Integer dsrSerial;
    @Column(name = "dsr_sys_serial")
    private Integer dsrSysSerial;
    @Column(name = "dsr_char_1")
    private Character dsrChar1;
    @Column(name = "dsr_char_2")
    private Character dsrChar2;
    @Size(max = 2)
    @Column(name = "dsr_2char_1", length = 2)
    private String dsr2char1;
    @Size(max = 2)
    @Column(name = "dsr_2char_2", length = 2)
    private String dsr2char2;
    @Size(max = 128)
    @Column(name = "dsr_vchar_1", length = 128)
    private String dsrVchar1;
    @Size(max = 128)
    @Column(name = "dsr_vchar_2", length = 128)
    private String dsrVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsr_numeric_1", precision = 17, scale = 6)
    private BigDecimal dsrNumeric1;
    @Column(name = "dsr_numeric_2", precision = 17, scale = 6)
    private BigDecimal dsrNumeric2;
    @Column(name = "dsr_numeric_3", precision = 17, scale = 6)
    private BigDecimal dsrNumeric3;
    @Column(name = "dsr_numeric_4", precision = 17, scale = 6)
    private BigDecimal dsrNumeric4;
    @Column(name = "dsr_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsrDate1;
    @Column(name = "dsr_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsrDate2;
    @Column(name = "dsr_int_1")
    private Integer dsrInt1;
    @Column(name = "dsr_int_2")
    private Integer dsrInt2;
    @Column(name = "dsr_int_3")
    private Integer dsrInt3;
    @Column(name = "dsr_int_4")
    private Integer dsrInt4;
    @Column(name = "dsr_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsrTime1;
    @JoinColumn(name = "dsr_epz_serial", referencedColumnName = "epz_serial")
    @ManyToOne
    private EppZest dsrEpzSerial;
    @JoinColumn(name = "dsr_ksi_serial", referencedColumnName = "ksi_serial")
    @ManyToOne
    private Ksiegapir dsrKsiSerial;
    @JoinColumn(name = "dsr_epp_serial", referencedColumnName = "epp_serial")
    @ManyToOne
    private Przebieg dsrEppSerial;
    @JoinColumn(name = "dsr_spr_serial", referencedColumnName = "spr_serial")
    @ManyToOne
    private Sprzedaz dsrSprSerial;
    @JoinColumn(name = "dsr_sro_serial", referencedColumnName = "sro_serial")
    @ManyToOne
    private Srodki dsrSroSerial;
    @JoinColumn(name = "dsr_wyp_serial", referencedColumnName = "wyp_serial")
    @ManyToOne
    private Wyposaz dsrWypSerial;
    @JoinColumn(name = "dsr_zak_serial", referencedColumnName = "zak_serial")
    @ManyToOne
    private Zakupy dsrZakSerial;

    public DaneStatR() {
    }

    public DaneStatR(Integer dsrSerial) {
        this.dsrSerial = dsrSerial;
    }

    public Integer getDsrSerial() {
        return dsrSerial;
    }

    public void setDsrSerial(Integer dsrSerial) {
        this.dsrSerial = dsrSerial;
    }

    public Integer getDsrSysSerial() {
        return dsrSysSerial;
    }

    public void setDsrSysSerial(Integer dsrSysSerial) {
        this.dsrSysSerial = dsrSysSerial;
    }

    public Character getDsrChar1() {
        return dsrChar1;
    }

    public void setDsrChar1(Character dsrChar1) {
        this.dsrChar1 = dsrChar1;
    }

    public Character getDsrChar2() {
        return dsrChar2;
    }

    public void setDsrChar2(Character dsrChar2) {
        this.dsrChar2 = dsrChar2;
    }

    public String getDsr2char1() {
        return dsr2char1;
    }

    public void setDsr2char1(String dsr2char1) {
        this.dsr2char1 = dsr2char1;
    }

    public String getDsr2char2() {
        return dsr2char2;
    }

    public void setDsr2char2(String dsr2char2) {
        this.dsr2char2 = dsr2char2;
    }

    public String getDsrVchar1() {
        return dsrVchar1;
    }

    public void setDsrVchar1(String dsrVchar1) {
        this.dsrVchar1 = dsrVchar1;
    }

    public String getDsrVchar2() {
        return dsrVchar2;
    }

    public void setDsrVchar2(String dsrVchar2) {
        this.dsrVchar2 = dsrVchar2;
    }

    public BigDecimal getDsrNumeric1() {
        return dsrNumeric1;
    }

    public void setDsrNumeric1(BigDecimal dsrNumeric1) {
        this.dsrNumeric1 = dsrNumeric1;
    }

    public BigDecimal getDsrNumeric2() {
        return dsrNumeric2;
    }

    public void setDsrNumeric2(BigDecimal dsrNumeric2) {
        this.dsrNumeric2 = dsrNumeric2;
    }

    public BigDecimal getDsrNumeric3() {
        return dsrNumeric3;
    }

    public void setDsrNumeric3(BigDecimal dsrNumeric3) {
        this.dsrNumeric3 = dsrNumeric3;
    }

    public BigDecimal getDsrNumeric4() {
        return dsrNumeric4;
    }

    public void setDsrNumeric4(BigDecimal dsrNumeric4) {
        this.dsrNumeric4 = dsrNumeric4;
    }

    public Date getDsrDate1() {
        return dsrDate1;
    }

    public void setDsrDate1(Date dsrDate1) {
        this.dsrDate1 = dsrDate1;
    }

    public Date getDsrDate2() {
        return dsrDate2;
    }

    public void setDsrDate2(Date dsrDate2) {
        this.dsrDate2 = dsrDate2;
    }

    public Integer getDsrInt1() {
        return dsrInt1;
    }

    public void setDsrInt1(Integer dsrInt1) {
        this.dsrInt1 = dsrInt1;
    }

    public Integer getDsrInt2() {
        return dsrInt2;
    }

    public void setDsrInt2(Integer dsrInt2) {
        this.dsrInt2 = dsrInt2;
    }

    public Integer getDsrInt3() {
        return dsrInt3;
    }

    public void setDsrInt3(Integer dsrInt3) {
        this.dsrInt3 = dsrInt3;
    }

    public Integer getDsrInt4() {
        return dsrInt4;
    }

    public void setDsrInt4(Integer dsrInt4) {
        this.dsrInt4 = dsrInt4;
    }

    public Date getDsrTime1() {
        return dsrTime1;
    }

    public void setDsrTime1(Date dsrTime1) {
        this.dsrTime1 = dsrTime1;
    }

    public EppZest getDsrEpzSerial() {
        return dsrEpzSerial;
    }

    public void setDsrEpzSerial(EppZest dsrEpzSerial) {
        this.dsrEpzSerial = dsrEpzSerial;
    }

    public Ksiegapir getDsrKsiSerial() {
        return dsrKsiSerial;
    }

    public void setDsrKsiSerial(Ksiegapir dsrKsiSerial) {
        this.dsrKsiSerial = dsrKsiSerial;
    }

    public Przebieg getDsrEppSerial() {
        return dsrEppSerial;
    }

    public void setDsrEppSerial(Przebieg dsrEppSerial) {
        this.dsrEppSerial = dsrEppSerial;
    }

    public Sprzedaz getDsrSprSerial() {
        return dsrSprSerial;
    }

    public void setDsrSprSerial(Sprzedaz dsrSprSerial) {
        this.dsrSprSerial = dsrSprSerial;
    }

    public Srodki getDsrSroSerial() {
        return dsrSroSerial;
    }

    public void setDsrSroSerial(Srodki dsrSroSerial) {
        this.dsrSroSerial = dsrSroSerial;
    }

    public Wyposaz getDsrWypSerial() {
        return dsrWypSerial;
    }

    public void setDsrWypSerial(Wyposaz dsrWypSerial) {
        this.dsrWypSerial = dsrWypSerial;
    }

    public Zakupy getDsrZakSerial() {
        return dsrZakSerial;
    }

    public void setDsrZakSerial(Zakupy dsrZakSerial) {
        this.dsrZakSerial = dsrZakSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dsrSerial != null ? dsrSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatR)) {
            return false;
        }
        DaneStatR other = (DaneStatR) object;
        if ((this.dsrSerial == null && other.dsrSerial != null) || (this.dsrSerial != null && !this.dsrSerial.equals(other.dsrSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatR[ dsrSerial=" + dsrSerial + " ]";
    }
    
}
