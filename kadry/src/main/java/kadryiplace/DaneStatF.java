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
@Table(name = "dane_stat_f", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatF.findAll", query = "SELECT d FROM DaneStatF d"),
    @NamedQuery(name = "DaneStatF.findByDsfSerial", query = "SELECT d FROM DaneStatF d WHERE d.dsfSerial = :dsfSerial"),
    @NamedQuery(name = "DaneStatF.findByDsfSysSerial", query = "SELECT d FROM DaneStatF d WHERE d.dsfSysSerial = :dsfSysSerial"),
    @NamedQuery(name = "DaneStatF.findByDsfChar1", query = "SELECT d FROM DaneStatF d WHERE d.dsfChar1 = :dsfChar1"),
    @NamedQuery(name = "DaneStatF.findByDsfChar2", query = "SELECT d FROM DaneStatF d WHERE d.dsfChar2 = :dsfChar2"),
    @NamedQuery(name = "DaneStatF.findByDsf2char1", query = "SELECT d FROM DaneStatF d WHERE d.dsf2char1 = :dsf2char1"),
    @NamedQuery(name = "DaneStatF.findByDsf2char2", query = "SELECT d FROM DaneStatF d WHERE d.dsf2char2 = :dsf2char2"),
    @NamedQuery(name = "DaneStatF.findByDsfVchar1", query = "SELECT d FROM DaneStatF d WHERE d.dsfVchar1 = :dsfVchar1"),
    @NamedQuery(name = "DaneStatF.findByDsfVchar2", query = "SELECT d FROM DaneStatF d WHERE d.dsfVchar2 = :dsfVchar2"),
    @NamedQuery(name = "DaneStatF.findByDsfNumeric1", query = "SELECT d FROM DaneStatF d WHERE d.dsfNumeric1 = :dsfNumeric1"),
    @NamedQuery(name = "DaneStatF.findByDsfNumeric2", query = "SELECT d FROM DaneStatF d WHERE d.dsfNumeric2 = :dsfNumeric2"),
    @NamedQuery(name = "DaneStatF.findByDsfNumeric3", query = "SELECT d FROM DaneStatF d WHERE d.dsfNumeric3 = :dsfNumeric3"),
    @NamedQuery(name = "DaneStatF.findByDsfNumeric4", query = "SELECT d FROM DaneStatF d WHERE d.dsfNumeric4 = :dsfNumeric4"),
    @NamedQuery(name = "DaneStatF.findByDsfDate1", query = "SELECT d FROM DaneStatF d WHERE d.dsfDate1 = :dsfDate1"),
    @NamedQuery(name = "DaneStatF.findByDsfDate2", query = "SELECT d FROM DaneStatF d WHERE d.dsfDate2 = :dsfDate2"),
    @NamedQuery(name = "DaneStatF.findByDsfInt1", query = "SELECT d FROM DaneStatF d WHERE d.dsfInt1 = :dsfInt1"),
    @NamedQuery(name = "DaneStatF.findByDsfInt2", query = "SELECT d FROM DaneStatF d WHERE d.dsfInt2 = :dsfInt2"),
    @NamedQuery(name = "DaneStatF.findByDsfInt3", query = "SELECT d FROM DaneStatF d WHERE d.dsfInt3 = :dsfInt3"),
    @NamedQuery(name = "DaneStatF.findByDsfInt4", query = "SELECT d FROM DaneStatF d WHERE d.dsfInt4 = :dsfInt4"),
    @NamedQuery(name = "DaneStatF.findByDsfTime1", query = "SELECT d FROM DaneStatF d WHERE d.dsfTime1 = :dsfTime1")})
public class DaneStatF implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsf_serial", nullable = false)
    private Integer dsfSerial;
    @Column(name = "dsf_sys_serial")
    private Integer dsfSysSerial;
    @Column(name = "dsf_char_1")
    private Character dsfChar1;
    @Column(name = "dsf_char_2")
    private Character dsfChar2;
    @Size(max = 2)
    @Column(name = "dsf_2char_1", length = 2)
    private String dsf2char1;
    @Size(max = 2)
    @Column(name = "dsf_2char_2", length = 2)
    private String dsf2char2;
    @Size(max = 128)
    @Column(name = "dsf_vchar_1", length = 128)
    private String dsfVchar1;
    @Size(max = 128)
    @Column(name = "dsf_vchar_2", length = 128)
    private String dsfVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsf_numeric_1", precision = 17, scale = 6)
    private BigDecimal dsfNumeric1;
    @Column(name = "dsf_numeric_2", precision = 17, scale = 6)
    private BigDecimal dsfNumeric2;
    @Column(name = "dsf_numeric_3", precision = 17, scale = 6)
    private BigDecimal dsfNumeric3;
    @Column(name = "dsf_numeric_4", precision = 17, scale = 6)
    private BigDecimal dsfNumeric4;
    @Column(name = "dsf_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsfDate1;
    @Column(name = "dsf_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsfDate2;
    @Column(name = "dsf_int_1")
    private Integer dsfInt1;
    @Column(name = "dsf_int_2")
    private Integer dsfInt2;
    @Column(name = "dsf_int_3")
    private Integer dsfInt3;
    @Column(name = "dsf_int_4")
    private Integer dsfInt4;
    @Column(name = "dsf_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsfTime1;
    @JoinColumn(name = "dsf_fak_serial", referencedColumnName = "fak_serial")
    @ManyToOne
    private Fakrach dsfFakSerial;

    public DaneStatF() {
    }

    public DaneStatF(Integer dsfSerial) {
        this.dsfSerial = dsfSerial;
    }

    public Integer getDsfSerial() {
        return dsfSerial;
    }

    public void setDsfSerial(Integer dsfSerial) {
        this.dsfSerial = dsfSerial;
    }

    public Integer getDsfSysSerial() {
        return dsfSysSerial;
    }

    public void setDsfSysSerial(Integer dsfSysSerial) {
        this.dsfSysSerial = dsfSysSerial;
    }

    public Character getDsfChar1() {
        return dsfChar1;
    }

    public void setDsfChar1(Character dsfChar1) {
        this.dsfChar1 = dsfChar1;
    }

    public Character getDsfChar2() {
        return dsfChar2;
    }

    public void setDsfChar2(Character dsfChar2) {
        this.dsfChar2 = dsfChar2;
    }

    public String getDsf2char1() {
        return dsf2char1;
    }

    public void setDsf2char1(String dsf2char1) {
        this.dsf2char1 = dsf2char1;
    }

    public String getDsf2char2() {
        return dsf2char2;
    }

    public void setDsf2char2(String dsf2char2) {
        this.dsf2char2 = dsf2char2;
    }

    public String getDsfVchar1() {
        return dsfVchar1;
    }

    public void setDsfVchar1(String dsfVchar1) {
        this.dsfVchar1 = dsfVchar1;
    }

    public String getDsfVchar2() {
        return dsfVchar2;
    }

    public void setDsfVchar2(String dsfVchar2) {
        this.dsfVchar2 = dsfVchar2;
    }

    public BigDecimal getDsfNumeric1() {
        return dsfNumeric1;
    }

    public void setDsfNumeric1(BigDecimal dsfNumeric1) {
        this.dsfNumeric1 = dsfNumeric1;
    }

    public BigDecimal getDsfNumeric2() {
        return dsfNumeric2;
    }

    public void setDsfNumeric2(BigDecimal dsfNumeric2) {
        this.dsfNumeric2 = dsfNumeric2;
    }

    public BigDecimal getDsfNumeric3() {
        return dsfNumeric3;
    }

    public void setDsfNumeric3(BigDecimal dsfNumeric3) {
        this.dsfNumeric3 = dsfNumeric3;
    }

    public BigDecimal getDsfNumeric4() {
        return dsfNumeric4;
    }

    public void setDsfNumeric4(BigDecimal dsfNumeric4) {
        this.dsfNumeric4 = dsfNumeric4;
    }

    public Date getDsfDate1() {
        return dsfDate1;
    }

    public void setDsfDate1(Date dsfDate1) {
        this.dsfDate1 = dsfDate1;
    }

    public Date getDsfDate2() {
        return dsfDate2;
    }

    public void setDsfDate2(Date dsfDate2) {
        this.dsfDate2 = dsfDate2;
    }

    public Integer getDsfInt1() {
        return dsfInt1;
    }

    public void setDsfInt1(Integer dsfInt1) {
        this.dsfInt1 = dsfInt1;
    }

    public Integer getDsfInt2() {
        return dsfInt2;
    }

    public void setDsfInt2(Integer dsfInt2) {
        this.dsfInt2 = dsfInt2;
    }

    public Integer getDsfInt3() {
        return dsfInt3;
    }

    public void setDsfInt3(Integer dsfInt3) {
        this.dsfInt3 = dsfInt3;
    }

    public Integer getDsfInt4() {
        return dsfInt4;
    }

    public void setDsfInt4(Integer dsfInt4) {
        this.dsfInt4 = dsfInt4;
    }

    public Date getDsfTime1() {
        return dsfTime1;
    }

    public void setDsfTime1(Date dsfTime1) {
        this.dsfTime1 = dsfTime1;
    }

    public Fakrach getDsfFakSerial() {
        return dsfFakSerial;
    }

    public void setDsfFakSerial(Fakrach dsfFakSerial) {
        this.dsfFakSerial = dsfFakSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dsfSerial != null ? dsfSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatF)) {
            return false;
        }
        DaneStatF other = (DaneStatF) object;
        if ((this.dsfSerial == null && other.dsfSerial != null) || (this.dsfSerial != null && !this.dsfSerial.equals(other.dsfSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatF[ dsfSerial=" + dsfSerial + " ]";
    }
    
}
