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
@Table(name = "dane_stat_m", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatM.findAll", query = "SELECT d FROM DaneStatM d"),
    @NamedQuery(name = "DaneStatM.findByDsmSerial", query = "SELECT d FROM DaneStatM d WHERE d.dsmSerial = :dsmSerial"),
    @NamedQuery(name = "DaneStatM.findByDsmSysSerial", query = "SELECT d FROM DaneStatM d WHERE d.dsmSysSerial = :dsmSysSerial"),
    @NamedQuery(name = "DaneStatM.findByDsmChar1", query = "SELECT d FROM DaneStatM d WHERE d.dsmChar1 = :dsmChar1"),
    @NamedQuery(name = "DaneStatM.findByDsmChar2", query = "SELECT d FROM DaneStatM d WHERE d.dsmChar2 = :dsmChar2"),
    @NamedQuery(name = "DaneStatM.findByDsm2char1", query = "SELECT d FROM DaneStatM d WHERE d.dsm2char1 = :dsm2char1"),
    @NamedQuery(name = "DaneStatM.findByDsm2char2", query = "SELECT d FROM DaneStatM d WHERE d.dsm2char2 = :dsm2char2"),
    @NamedQuery(name = "DaneStatM.findByDsmVchar1", query = "SELECT d FROM DaneStatM d WHERE d.dsmVchar1 = :dsmVchar1"),
    @NamedQuery(name = "DaneStatM.findByDsmVchar2", query = "SELECT d FROM DaneStatM d WHERE d.dsmVchar2 = :dsmVchar2"),
    @NamedQuery(name = "DaneStatM.findByDsmInt1", query = "SELECT d FROM DaneStatM d WHERE d.dsmInt1 = :dsmInt1"),
    @NamedQuery(name = "DaneStatM.findByDsmInt2", query = "SELECT d FROM DaneStatM d WHERE d.dsmInt2 = :dsmInt2"),
    @NamedQuery(name = "DaneStatM.findByDsmInt3", query = "SELECT d FROM DaneStatM d WHERE d.dsmInt3 = :dsmInt3"),
    @NamedQuery(name = "DaneStatM.findByDsmInt4", query = "SELECT d FROM DaneStatM d WHERE d.dsmInt4 = :dsmInt4"),
    @NamedQuery(name = "DaneStatM.findByDsmNumeric1", query = "SELECT d FROM DaneStatM d WHERE d.dsmNumeric1 = :dsmNumeric1"),
    @NamedQuery(name = "DaneStatM.findByDsmNumeric2", query = "SELECT d FROM DaneStatM d WHERE d.dsmNumeric2 = :dsmNumeric2"),
    @NamedQuery(name = "DaneStatM.findByDsmNumeric3", query = "SELECT d FROM DaneStatM d WHERE d.dsmNumeric3 = :dsmNumeric3"),
    @NamedQuery(name = "DaneStatM.findByDsmNumeric4", query = "SELECT d FROM DaneStatM d WHERE d.dsmNumeric4 = :dsmNumeric4"),
    @NamedQuery(name = "DaneStatM.findByDsmDate1", query = "SELECT d FROM DaneStatM d WHERE d.dsmDate1 = :dsmDate1"),
    @NamedQuery(name = "DaneStatM.findByDsmDate2", query = "SELECT d FROM DaneStatM d WHERE d.dsmDate2 = :dsmDate2"),
    @NamedQuery(name = "DaneStatM.findByDsmTime1", query = "SELECT d FROM DaneStatM d WHERE d.dsmTime1 = :dsmTime1")})
public class DaneStatM implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsm_serial", nullable = false)
    private Integer dsmSerial;
    @Column(name = "dsm_sys_serial")
    private Integer dsmSysSerial;
    @Column(name = "dsm_char_1")
    private Character dsmChar1;
    @Column(name = "dsm_char_2")
    private Character dsmChar2;
    @Size(max = 2)
    @Column(name = "dsm_2char_1", length = 2)
    private String dsm2char1;
    @Size(max = 2)
    @Column(name = "dsm_2char_2", length = 2)
    private String dsm2char2;
    @Size(max = 128)
    @Column(name = "dsm_vchar_1", length = 128)
    private String dsmVchar1;
    @Size(max = 128)
    @Column(name = "dsm_vchar_2", length = 128)
    private String dsmVchar2;
    @Column(name = "dsm_int_1")
    private Integer dsmInt1;
    @Column(name = "dsm_int_2")
    private Integer dsmInt2;
    @Column(name = "dsm_int_3")
    private Integer dsmInt3;
    @Column(name = "dsm_int_4")
    private Integer dsmInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsm_numeric_1", precision = 17, scale = 6)
    private BigDecimal dsmNumeric1;
    @Column(name = "dsm_numeric_2", precision = 17, scale = 6)
    private BigDecimal dsmNumeric2;
    @Column(name = "dsm_numeric_3", precision = 17, scale = 6)
    private BigDecimal dsmNumeric3;
    @Column(name = "dsm_numeric_4", precision = 17, scale = 6)
    private BigDecimal dsmNumeric4;
    @Column(name = "dsm_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsmDate1;
    @Column(name = "dsm_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsmDate2;
    @Column(name = "dsm_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsmTime1;
    @JoinColumn(name = "dsm_mag_serial", referencedColumnName = "mag_serial")
    @ManyToOne
    private Magazyn dsmMagSerial;

    public DaneStatM() {
    }

    public DaneStatM(Integer dsmSerial) {
        this.dsmSerial = dsmSerial;
    }

    public Integer getDsmSerial() {
        return dsmSerial;
    }

    public void setDsmSerial(Integer dsmSerial) {
        this.dsmSerial = dsmSerial;
    }

    public Integer getDsmSysSerial() {
        return dsmSysSerial;
    }

    public void setDsmSysSerial(Integer dsmSysSerial) {
        this.dsmSysSerial = dsmSysSerial;
    }

    public Character getDsmChar1() {
        return dsmChar1;
    }

    public void setDsmChar1(Character dsmChar1) {
        this.dsmChar1 = dsmChar1;
    }

    public Character getDsmChar2() {
        return dsmChar2;
    }

    public void setDsmChar2(Character dsmChar2) {
        this.dsmChar2 = dsmChar2;
    }

    public String getDsm2char1() {
        return dsm2char1;
    }

    public void setDsm2char1(String dsm2char1) {
        this.dsm2char1 = dsm2char1;
    }

    public String getDsm2char2() {
        return dsm2char2;
    }

    public void setDsm2char2(String dsm2char2) {
        this.dsm2char2 = dsm2char2;
    }

    public String getDsmVchar1() {
        return dsmVchar1;
    }

    public void setDsmVchar1(String dsmVchar1) {
        this.dsmVchar1 = dsmVchar1;
    }

    public String getDsmVchar2() {
        return dsmVchar2;
    }

    public void setDsmVchar2(String dsmVchar2) {
        this.dsmVchar2 = dsmVchar2;
    }

    public Integer getDsmInt1() {
        return dsmInt1;
    }

    public void setDsmInt1(Integer dsmInt1) {
        this.dsmInt1 = dsmInt1;
    }

    public Integer getDsmInt2() {
        return dsmInt2;
    }

    public void setDsmInt2(Integer dsmInt2) {
        this.dsmInt2 = dsmInt2;
    }

    public Integer getDsmInt3() {
        return dsmInt3;
    }

    public void setDsmInt3(Integer dsmInt3) {
        this.dsmInt3 = dsmInt3;
    }

    public Integer getDsmInt4() {
        return dsmInt4;
    }

    public void setDsmInt4(Integer dsmInt4) {
        this.dsmInt4 = dsmInt4;
    }

    public BigDecimal getDsmNumeric1() {
        return dsmNumeric1;
    }

    public void setDsmNumeric1(BigDecimal dsmNumeric1) {
        this.dsmNumeric1 = dsmNumeric1;
    }

    public BigDecimal getDsmNumeric2() {
        return dsmNumeric2;
    }

    public void setDsmNumeric2(BigDecimal dsmNumeric2) {
        this.dsmNumeric2 = dsmNumeric2;
    }

    public BigDecimal getDsmNumeric3() {
        return dsmNumeric3;
    }

    public void setDsmNumeric3(BigDecimal dsmNumeric3) {
        this.dsmNumeric3 = dsmNumeric3;
    }

    public BigDecimal getDsmNumeric4() {
        return dsmNumeric4;
    }

    public void setDsmNumeric4(BigDecimal dsmNumeric4) {
        this.dsmNumeric4 = dsmNumeric4;
    }

    public Date getDsmDate1() {
        return dsmDate1;
    }

    public void setDsmDate1(Date dsmDate1) {
        this.dsmDate1 = dsmDate1;
    }

    public Date getDsmDate2() {
        return dsmDate2;
    }

    public void setDsmDate2(Date dsmDate2) {
        this.dsmDate2 = dsmDate2;
    }

    public Date getDsmTime1() {
        return dsmTime1;
    }

    public void setDsmTime1(Date dsmTime1) {
        this.dsmTime1 = dsmTime1;
    }

    public Magazyn getDsmMagSerial() {
        return dsmMagSerial;
    }

    public void setDsmMagSerial(Magazyn dsmMagSerial) {
        this.dsmMagSerial = dsmMagSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dsmSerial != null ? dsmSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatM)) {
            return false;
        }
        DaneStatM other = (DaneStatM) object;
        if ((this.dsmSerial == null && other.dsmSerial != null) || (this.dsmSerial != null && !this.dsmSerial.equals(other.dsmSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatM[ dsmSerial=" + dsmSerial + " ]";
    }
    
}
