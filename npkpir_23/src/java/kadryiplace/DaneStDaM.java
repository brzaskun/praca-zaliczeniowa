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
@Table(name = "dane_st_da_m", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStDaM.findAll", query = "SELECT d FROM DaneStDaM d"),
    @NamedQuery(name = "DaneStDaM.findByDamSerial", query = "SELECT d FROM DaneStDaM d WHERE d.damSerial = :damSerial"),
    @NamedQuery(name = "DaneStDaM.findByDamSysSerial", query = "SELECT d FROM DaneStDaM d WHERE d.damSysSerial = :damSysSerial"),
    @NamedQuery(name = "DaneStDaM.findByDamChar1", query = "SELECT d FROM DaneStDaM d WHERE d.damChar1 = :damChar1"),
    @NamedQuery(name = "DaneStDaM.findByDamChar2", query = "SELECT d FROM DaneStDaM d WHERE d.damChar2 = :damChar2"),
    @NamedQuery(name = "DaneStDaM.findByDamChar3", query = "SELECT d FROM DaneStDaM d WHERE d.damChar3 = :damChar3"),
    @NamedQuery(name = "DaneStDaM.findByDamChar4", query = "SELECT d FROM DaneStDaM d WHERE d.damChar4 = :damChar4"),
    @NamedQuery(name = "DaneStDaM.findByDamVchar1", query = "SELECT d FROM DaneStDaM d WHERE d.damVchar1 = :damVchar1"),
    @NamedQuery(name = "DaneStDaM.findByDamVchar2", query = "SELECT d FROM DaneStDaM d WHERE d.damVchar2 = :damVchar2"),
    @NamedQuery(name = "DaneStDaM.findByDamVchar3", query = "SELECT d FROM DaneStDaM d WHERE d.damVchar3 = :damVchar3"),
    @NamedQuery(name = "DaneStDaM.findByDamVchar4", query = "SELECT d FROM DaneStDaM d WHERE d.damVchar4 = :damVchar4"),
    @NamedQuery(name = "DaneStDaM.findByDamDate1", query = "SELECT d FROM DaneStDaM d WHERE d.damDate1 = :damDate1"),
    @NamedQuery(name = "DaneStDaM.findByDamDate2", query = "SELECT d FROM DaneStDaM d WHERE d.damDate2 = :damDate2"),
    @NamedQuery(name = "DaneStDaM.findByDamDate3", query = "SELECT d FROM DaneStDaM d WHERE d.damDate3 = :damDate3"),
    @NamedQuery(name = "DaneStDaM.findByDamDate4", query = "SELECT d FROM DaneStDaM d WHERE d.damDate4 = :damDate4"),
    @NamedQuery(name = "DaneStDaM.findByDamInt1", query = "SELECT d FROM DaneStDaM d WHERE d.damInt1 = :damInt1"),
    @NamedQuery(name = "DaneStDaM.findByDamInt2", query = "SELECT d FROM DaneStDaM d WHERE d.damInt2 = :damInt2"),
    @NamedQuery(name = "DaneStDaM.findByDamInt3", query = "SELECT d FROM DaneStDaM d WHERE d.damInt3 = :damInt3"),
    @NamedQuery(name = "DaneStDaM.findByDamInt4", query = "SELECT d FROM DaneStDaM d WHERE d.damInt4 = :damInt4"),
    @NamedQuery(name = "DaneStDaM.findByDamNumeric1", query = "SELECT d FROM DaneStDaM d WHERE d.damNumeric1 = :damNumeric1"),
    @NamedQuery(name = "DaneStDaM.findByDamNumeric2", query = "SELECT d FROM DaneStDaM d WHERE d.damNumeric2 = :damNumeric2"),
    @NamedQuery(name = "DaneStDaM.findByDamNumeric3", query = "SELECT d FROM DaneStDaM d WHERE d.damNumeric3 = :damNumeric3"),
    @NamedQuery(name = "DaneStDaM.findByDamNumeric4", query = "SELECT d FROM DaneStDaM d WHERE d.damNumeric4 = :damNumeric4"),
    @NamedQuery(name = "DaneStDaM.findByDamTime1", query = "SELECT d FROM DaneStDaM d WHERE d.damTime1 = :damTime1"),
    @NamedQuery(name = "DaneStDaM.findByDam2char1", query = "SELECT d FROM DaneStDaM d WHERE d.dam2char1 = :dam2char1"),
    @NamedQuery(name = "DaneStDaM.findByDam2char2", query = "SELECT d FROM DaneStDaM d WHERE d.dam2char2 = :dam2char2")})
public class DaneStDaM implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dam_serial", nullable = false)
    private Integer damSerial;
    @Column(name = "dam_sys_serial")
    private Integer damSysSerial;
    @Column(name = "dam_char_1")
    private Character damChar1;
    @Column(name = "dam_char_2")
    private Character damChar2;
    @Column(name = "dam_char_3")
    private Character damChar3;
    @Column(name = "dam_char_4")
    private Character damChar4;
    @Size(max = 254)
    @Column(name = "dam_vchar_1", length = 254)
    private String damVchar1;
    @Size(max = 254)
    @Column(name = "dam_vchar_2", length = 254)
    private String damVchar2;
    @Size(max = 254)
    @Column(name = "dam_vchar_3", length = 254)
    private String damVchar3;
    @Size(max = 254)
    @Column(name = "dam_vchar_4", length = 254)
    private String damVchar4;
    @Column(name = "dam_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date damDate1;
    @Column(name = "dam_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date damDate2;
    @Column(name = "dam_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date damDate3;
    @Column(name = "dam_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date damDate4;
    @Column(name = "dam_int_1")
    private Integer damInt1;
    @Column(name = "dam_int_2")
    private Integer damInt2;
    @Column(name = "dam_int_3")
    private Integer damInt3;
    @Column(name = "dam_int_4")
    private Integer damInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dam_numeric_1", precision = 17, scale = 6)
    private BigDecimal damNumeric1;
    @Column(name = "dam_numeric_2", precision = 17, scale = 6)
    private BigDecimal damNumeric2;
    @Column(name = "dam_numeric_3", precision = 17, scale = 6)
    private BigDecimal damNumeric3;
    @Column(name = "dam_numeric_4", precision = 17, scale = 6)
    private BigDecimal damNumeric4;
    @Column(name = "dam_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date damTime1;
    @Size(max = 2)
    @Column(name = "dam_2char_1", length = 2)
    private String dam2char1;
    @Size(max = 2)
    @Column(name = "dam_2char_2", length = 2)
    private String dam2char2;
    @JoinColumn(name = "dam_dls_serial", referencedColumnName = "dls_serial")
    @ManyToOne
    private DaneLiSl damDlsSerial;
    @JoinColumn(name = "dam_mag_serial", referencedColumnName = "mag_serial")
    @ManyToOne
    private Magazyn damMagSerial;

    public DaneStDaM() {
    }

    public DaneStDaM(Integer damSerial) {
        this.damSerial = damSerial;
    }

    public Integer getDamSerial() {
        return damSerial;
    }

    public void setDamSerial(Integer damSerial) {
        this.damSerial = damSerial;
    }

    public Integer getDamSysSerial() {
        return damSysSerial;
    }

    public void setDamSysSerial(Integer damSysSerial) {
        this.damSysSerial = damSysSerial;
    }

    public Character getDamChar1() {
        return damChar1;
    }

    public void setDamChar1(Character damChar1) {
        this.damChar1 = damChar1;
    }

    public Character getDamChar2() {
        return damChar2;
    }

    public void setDamChar2(Character damChar2) {
        this.damChar2 = damChar2;
    }

    public Character getDamChar3() {
        return damChar3;
    }

    public void setDamChar3(Character damChar3) {
        this.damChar3 = damChar3;
    }

    public Character getDamChar4() {
        return damChar4;
    }

    public void setDamChar4(Character damChar4) {
        this.damChar4 = damChar4;
    }

    public String getDamVchar1() {
        return damVchar1;
    }

    public void setDamVchar1(String damVchar1) {
        this.damVchar1 = damVchar1;
    }

    public String getDamVchar2() {
        return damVchar2;
    }

    public void setDamVchar2(String damVchar2) {
        this.damVchar2 = damVchar2;
    }

    public String getDamVchar3() {
        return damVchar3;
    }

    public void setDamVchar3(String damVchar3) {
        this.damVchar3 = damVchar3;
    }

    public String getDamVchar4() {
        return damVchar4;
    }

    public void setDamVchar4(String damVchar4) {
        this.damVchar4 = damVchar4;
    }

    public Date getDamDate1() {
        return damDate1;
    }

    public void setDamDate1(Date damDate1) {
        this.damDate1 = damDate1;
    }

    public Date getDamDate2() {
        return damDate2;
    }

    public void setDamDate2(Date damDate2) {
        this.damDate2 = damDate2;
    }

    public Date getDamDate3() {
        return damDate3;
    }

    public void setDamDate3(Date damDate3) {
        this.damDate3 = damDate3;
    }

    public Date getDamDate4() {
        return damDate4;
    }

    public void setDamDate4(Date damDate4) {
        this.damDate4 = damDate4;
    }

    public Integer getDamInt1() {
        return damInt1;
    }

    public void setDamInt1(Integer damInt1) {
        this.damInt1 = damInt1;
    }

    public Integer getDamInt2() {
        return damInt2;
    }

    public void setDamInt2(Integer damInt2) {
        this.damInt2 = damInt2;
    }

    public Integer getDamInt3() {
        return damInt3;
    }

    public void setDamInt3(Integer damInt3) {
        this.damInt3 = damInt3;
    }

    public Integer getDamInt4() {
        return damInt4;
    }

    public void setDamInt4(Integer damInt4) {
        this.damInt4 = damInt4;
    }

    public BigDecimal getDamNumeric1() {
        return damNumeric1;
    }

    public void setDamNumeric1(BigDecimal damNumeric1) {
        this.damNumeric1 = damNumeric1;
    }

    public BigDecimal getDamNumeric2() {
        return damNumeric2;
    }

    public void setDamNumeric2(BigDecimal damNumeric2) {
        this.damNumeric2 = damNumeric2;
    }

    public BigDecimal getDamNumeric3() {
        return damNumeric3;
    }

    public void setDamNumeric3(BigDecimal damNumeric3) {
        this.damNumeric3 = damNumeric3;
    }

    public BigDecimal getDamNumeric4() {
        return damNumeric4;
    }

    public void setDamNumeric4(BigDecimal damNumeric4) {
        this.damNumeric4 = damNumeric4;
    }

    public Date getDamTime1() {
        return damTime1;
    }

    public void setDamTime1(Date damTime1) {
        this.damTime1 = damTime1;
    }

    public String getDam2char1() {
        return dam2char1;
    }

    public void setDam2char1(String dam2char1) {
        this.dam2char1 = dam2char1;
    }

    public String getDam2char2() {
        return dam2char2;
    }

    public void setDam2char2(String dam2char2) {
        this.dam2char2 = dam2char2;
    }

    public DaneLiSl getDamDlsSerial() {
        return damDlsSerial;
    }

    public void setDamDlsSerial(DaneLiSl damDlsSerial) {
        this.damDlsSerial = damDlsSerial;
    }

    public Magazyn getDamMagSerial() {
        return damMagSerial;
    }

    public void setDamMagSerial(Magazyn damMagSerial) {
        this.damMagSerial = damMagSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (damSerial != null ? damSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStDaM)) {
            return false;
        }
        DaneStDaM other = (DaneStDaM) object;
        if ((this.damSerial == null && other.damSerial != null) || (this.damSerial != null && !this.damSerial.equals(other.damSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStDaM[ damSerial=" + damSerial + " ]";
    }
    
}
