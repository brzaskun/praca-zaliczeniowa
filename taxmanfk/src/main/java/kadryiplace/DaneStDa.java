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
@Table(name = "dane_st_da", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStDa.findAll", query = "SELECT d FROM DaneStDa d"),
    @NamedQuery(name = "DaneStDa.findByDasSerial", query = "SELECT d FROM DaneStDa d WHERE d.dasSerial = :dasSerial"),
    @NamedQuery(name = "DaneStDa.findByDasSysSerial", query = "SELECT d FROM DaneStDa d WHERE d.dasSysSerial = :dasSysSerial"),
    @NamedQuery(name = "DaneStDa.findByDasChar1", query = "SELECT d FROM DaneStDa d WHERE d.dasChar1 = :dasChar1"),
    @NamedQuery(name = "DaneStDa.findByDasChar2", query = "SELECT d FROM DaneStDa d WHERE d.dasChar2 = :dasChar2"),
    @NamedQuery(name = "DaneStDa.findByDasChar3", query = "SELECT d FROM DaneStDa d WHERE d.dasChar3 = :dasChar3"),
    @NamedQuery(name = "DaneStDa.findByDasChar4", query = "SELECT d FROM DaneStDa d WHERE d.dasChar4 = :dasChar4"),
    @NamedQuery(name = "DaneStDa.findByDasVchar1", query = "SELECT d FROM DaneStDa d WHERE d.dasVchar1 = :dasVchar1"),
    @NamedQuery(name = "DaneStDa.findByDasVchar2", query = "SELECT d FROM DaneStDa d WHERE d.dasVchar2 = :dasVchar2"),
    @NamedQuery(name = "DaneStDa.findByDasVchar3", query = "SELECT d FROM DaneStDa d WHERE d.dasVchar3 = :dasVchar3"),
    @NamedQuery(name = "DaneStDa.findByDasVchar4", query = "SELECT d FROM DaneStDa d WHERE d.dasVchar4 = :dasVchar4"),
    @NamedQuery(name = "DaneStDa.findByDasDate1", query = "SELECT d FROM DaneStDa d WHERE d.dasDate1 = :dasDate1"),
    @NamedQuery(name = "DaneStDa.findByDasDate2", query = "SELECT d FROM DaneStDa d WHERE d.dasDate2 = :dasDate2"),
    @NamedQuery(name = "DaneStDa.findByDasDate3", query = "SELECT d FROM DaneStDa d WHERE d.dasDate3 = :dasDate3"),
    @NamedQuery(name = "DaneStDa.findByDasDate4", query = "SELECT d FROM DaneStDa d WHERE d.dasDate4 = :dasDate4"),
    @NamedQuery(name = "DaneStDa.findByDasInt1", query = "SELECT d FROM DaneStDa d WHERE d.dasInt1 = :dasInt1"),
    @NamedQuery(name = "DaneStDa.findByDasInt2", query = "SELECT d FROM DaneStDa d WHERE d.dasInt2 = :dasInt2"),
    @NamedQuery(name = "DaneStDa.findByDasInt3", query = "SELECT d FROM DaneStDa d WHERE d.dasInt3 = :dasInt3"),
    @NamedQuery(name = "DaneStDa.findByDasInt4", query = "SELECT d FROM DaneStDa d WHERE d.dasInt4 = :dasInt4"),
    @NamedQuery(name = "DaneStDa.findByDasNumeric1", query = "SELECT d FROM DaneStDa d WHERE d.dasNumeric1 = :dasNumeric1"),
    @NamedQuery(name = "DaneStDa.findByDasNumeric2", query = "SELECT d FROM DaneStDa d WHERE d.dasNumeric2 = :dasNumeric2"),
    @NamedQuery(name = "DaneStDa.findByDasNumeric3", query = "SELECT d FROM DaneStDa d WHERE d.dasNumeric3 = :dasNumeric3"),
    @NamedQuery(name = "DaneStDa.findByDasNumeric4", query = "SELECT d FROM DaneStDa d WHERE d.dasNumeric4 = :dasNumeric4"),
    @NamedQuery(name = "DaneStDa.findByDasTime1", query = "SELECT d FROM DaneStDa d WHERE d.dasTime1 = :dasTime1"),
    @NamedQuery(name = "DaneStDa.findByDas2char1", query = "SELECT d FROM DaneStDa d WHERE d.das2char1 = :das2char1"),
    @NamedQuery(name = "DaneStDa.findByDas2char2", query = "SELECT d FROM DaneStDa d WHERE d.das2char2 = :das2char2")})
public class DaneStDa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "das_serial", nullable = false)
    private Integer dasSerial;
    @Column(name = "das_sys_serial")
    private Integer dasSysSerial;
    @Column(name = "das_char_1")
    private Character dasChar1;
    @Column(name = "das_char_2")
    private Character dasChar2;
    @Column(name = "das_char_3")
    private Character dasChar3;
    @Column(name = "das_char_4")
    private Character dasChar4;
    @Size(max = 254)
    @Column(name = "das_vchar_1", length = 254)
    private String dasVchar1;
    @Size(max = 254)
    @Column(name = "das_vchar_2", length = 254)
    private String dasVchar2;
    @Size(max = 254)
    @Column(name = "das_vchar_3", length = 254)
    private String dasVchar3;
    @Size(max = 254)
    @Column(name = "das_vchar_4", length = 254)
    private String dasVchar4;
    @Column(name = "das_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dasDate1;
    @Column(name = "das_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dasDate2;
    @Column(name = "das_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dasDate3;
    @Column(name = "das_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dasDate4;
    @Column(name = "das_int_1")
    private Integer dasInt1;
    @Column(name = "das_int_2")
    private Integer dasInt2;
    @Column(name = "das_int_3")
    private Integer dasInt3;
    @Column(name = "das_int_4")
    private Integer dasInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "das_numeric_1", precision = 17, scale = 6)
    private BigDecimal dasNumeric1;
    @Column(name = "das_numeric_2", precision = 17, scale = 6)
    private BigDecimal dasNumeric2;
    @Column(name = "das_numeric_3", precision = 17, scale = 6)
    private BigDecimal dasNumeric3;
    @Column(name = "das_numeric_4", precision = 17, scale = 6)
    private BigDecimal dasNumeric4;
    @Column(name = "das_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dasTime1;
    @Size(max = 2)
    @Column(name = "das_2char_1", length = 2)
    private String das2char1;
    @Size(max = 2)
    @Column(name = "das_2char_2", length = 2)
    private String das2char2;
    @JoinColumn(name = "das_dls_serial", referencedColumnName = "dls_serial")
    @ManyToOne
    private DaneLiSl dasDlsSerial;
    @JoinColumn(name = "das_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma dasFirSerial;
    @JoinColumn(name = "das_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent dasKonSerial;
    @JoinColumn(name = "das_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba dasOsoSerial;
    @JoinColumn(name = "das_osr_serial", referencedColumnName = "osr_serial")
    @ManyToOne
    private OsobaRod dasOsrSerial;

    public DaneStDa() {
    }

    public DaneStDa(Integer dasSerial) {
        this.dasSerial = dasSerial;
    }

    public Integer getDasSerial() {
        return dasSerial;
    }

    public void setDasSerial(Integer dasSerial) {
        this.dasSerial = dasSerial;
    }

    public Integer getDasSysSerial() {
        return dasSysSerial;
    }

    public void setDasSysSerial(Integer dasSysSerial) {
        this.dasSysSerial = dasSysSerial;
    }

    public Character getDasChar1() {
        return dasChar1;
    }

    public void setDasChar1(Character dasChar1) {
        this.dasChar1 = dasChar1;
    }

    public Character getDasChar2() {
        return dasChar2;
    }

    public void setDasChar2(Character dasChar2) {
        this.dasChar2 = dasChar2;
    }

    public Character getDasChar3() {
        return dasChar3;
    }

    public void setDasChar3(Character dasChar3) {
        this.dasChar3 = dasChar3;
    }

    public Character getDasChar4() {
        return dasChar4;
    }

    public void setDasChar4(Character dasChar4) {
        this.dasChar4 = dasChar4;
    }

    public String getDasVchar1() {
        return dasVchar1;
    }

    public void setDasVchar1(String dasVchar1) {
        this.dasVchar1 = dasVchar1;
    }

    public String getDasVchar2() {
        return dasVchar2;
    }

    public void setDasVchar2(String dasVchar2) {
        this.dasVchar2 = dasVchar2;
    }

    public String getDasVchar3() {
        return dasVchar3;
    }

    public void setDasVchar3(String dasVchar3) {
        this.dasVchar3 = dasVchar3;
    }

    public String getDasVchar4() {
        return dasVchar4;
    }

    public void setDasVchar4(String dasVchar4) {
        this.dasVchar4 = dasVchar4;
    }

    public Date getDasDate1() {
        return dasDate1;
    }

    public void setDasDate1(Date dasDate1) {
        this.dasDate1 = dasDate1;
    }

    public Date getDasDate2() {
        return dasDate2;
    }

    public void setDasDate2(Date dasDate2) {
        this.dasDate2 = dasDate2;
    }

    public Date getDasDate3() {
        return dasDate3;
    }

    public void setDasDate3(Date dasDate3) {
        this.dasDate3 = dasDate3;
    }

    public Date getDasDate4() {
        return dasDate4;
    }

    public void setDasDate4(Date dasDate4) {
        this.dasDate4 = dasDate4;
    }

    public Integer getDasInt1() {
        return dasInt1;
    }

    public void setDasInt1(Integer dasInt1) {
        this.dasInt1 = dasInt1;
    }

    public Integer getDasInt2() {
        return dasInt2;
    }

    public void setDasInt2(Integer dasInt2) {
        this.dasInt2 = dasInt2;
    }

    public Integer getDasInt3() {
        return dasInt3;
    }

    public void setDasInt3(Integer dasInt3) {
        this.dasInt3 = dasInt3;
    }

    public Integer getDasInt4() {
        return dasInt4;
    }

    public void setDasInt4(Integer dasInt4) {
        this.dasInt4 = dasInt4;
    }

    public BigDecimal getDasNumeric1() {
        return dasNumeric1;
    }

    public void setDasNumeric1(BigDecimal dasNumeric1) {
        this.dasNumeric1 = dasNumeric1;
    }

    public BigDecimal getDasNumeric2() {
        return dasNumeric2;
    }

    public void setDasNumeric2(BigDecimal dasNumeric2) {
        this.dasNumeric2 = dasNumeric2;
    }

    public BigDecimal getDasNumeric3() {
        return dasNumeric3;
    }

    public void setDasNumeric3(BigDecimal dasNumeric3) {
        this.dasNumeric3 = dasNumeric3;
    }

    public BigDecimal getDasNumeric4() {
        return dasNumeric4;
    }

    public void setDasNumeric4(BigDecimal dasNumeric4) {
        this.dasNumeric4 = dasNumeric4;
    }

    public Date getDasTime1() {
        return dasTime1;
    }

    public void setDasTime1(Date dasTime1) {
        this.dasTime1 = dasTime1;
    }

    public String getDas2char1() {
        return das2char1;
    }

    public void setDas2char1(String das2char1) {
        this.das2char1 = das2char1;
    }

    public String getDas2char2() {
        return das2char2;
    }

    public void setDas2char2(String das2char2) {
        this.das2char2 = das2char2;
    }

    public DaneLiSl getDasDlsSerial() {
        return dasDlsSerial;
    }

    public void setDasDlsSerial(DaneLiSl dasDlsSerial) {
        this.dasDlsSerial = dasDlsSerial;
    }

    public Firma getDasFirSerial() {
        return dasFirSerial;
    }

    public void setDasFirSerial(Firma dasFirSerial) {
        this.dasFirSerial = dasFirSerial;
    }

    public Kontrahent getDasKonSerial() {
        return dasKonSerial;
    }

    public void setDasKonSerial(Kontrahent dasKonSerial) {
        this.dasKonSerial = dasKonSerial;
    }

    public Osoba getDasOsoSerial() {
        return dasOsoSerial;
    }

    public void setDasOsoSerial(Osoba dasOsoSerial) {
        this.dasOsoSerial = dasOsoSerial;
    }

    public OsobaRod getDasOsrSerial() {
        return dasOsrSerial;
    }

    public void setDasOsrSerial(OsobaRod dasOsrSerial) {
        this.dasOsrSerial = dasOsrSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dasSerial != null ? dasSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStDa)) {
            return false;
        }
        DaneStDa other = (DaneStDa) object;
        if ((this.dasSerial == null && other.dasSerial != null) || (this.dasSerial != null && !this.dasSerial.equals(other.dasSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStDa[ dasSerial=" + dasSerial + " ]";
    }
    
}
