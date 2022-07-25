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
@Table(name = "dane_st_da_x", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStDaX.findAll", query = "SELECT d FROM DaneStDaX d"),
    @NamedQuery(name = "DaneStDaX.findByDaxSerial", query = "SELECT d FROM DaneStDaX d WHERE d.daxSerial = :daxSerial"),
    @NamedQuery(name = "DaneStDaX.findByDaxSysSerial", query = "SELECT d FROM DaneStDaX d WHERE d.daxSysSerial = :daxSysSerial"),
    @NamedQuery(name = "DaneStDaX.findByDaxIntSerial", query = "SELECT d FROM DaneStDaX d WHERE d.daxIntSerial = :daxIntSerial"),
    @NamedQuery(name = "DaneStDaX.findByDaxChar1", query = "SELECT d FROM DaneStDaX d WHERE d.daxChar1 = :daxChar1"),
    @NamedQuery(name = "DaneStDaX.findByDaxChar2", query = "SELECT d FROM DaneStDaX d WHERE d.daxChar2 = :daxChar2"),
    @NamedQuery(name = "DaneStDaX.findByDaxChar3", query = "SELECT d FROM DaneStDaX d WHERE d.daxChar3 = :daxChar3"),
    @NamedQuery(name = "DaneStDaX.findByDaxChar4", query = "SELECT d FROM DaneStDaX d WHERE d.daxChar4 = :daxChar4"),
    @NamedQuery(name = "DaneStDaX.findByDaxVchar1", query = "SELECT d FROM DaneStDaX d WHERE d.daxVchar1 = :daxVchar1"),
    @NamedQuery(name = "DaneStDaX.findByDaxVchar2", query = "SELECT d FROM DaneStDaX d WHERE d.daxVchar2 = :daxVchar2"),
    @NamedQuery(name = "DaneStDaX.findByDaxVchar3", query = "SELECT d FROM DaneStDaX d WHERE d.daxVchar3 = :daxVchar3"),
    @NamedQuery(name = "DaneStDaX.findByDaxVchar4", query = "SELECT d FROM DaneStDaX d WHERE d.daxVchar4 = :daxVchar4"),
    @NamedQuery(name = "DaneStDaX.findByDaxDate1", query = "SELECT d FROM DaneStDaX d WHERE d.daxDate1 = :daxDate1"),
    @NamedQuery(name = "DaneStDaX.findByDaxDate2", query = "SELECT d FROM DaneStDaX d WHERE d.daxDate2 = :daxDate2"),
    @NamedQuery(name = "DaneStDaX.findByDaxDate3", query = "SELECT d FROM DaneStDaX d WHERE d.daxDate3 = :daxDate3"),
    @NamedQuery(name = "DaneStDaX.findByDaxDate4", query = "SELECT d FROM DaneStDaX d WHERE d.daxDate4 = :daxDate4"),
    @NamedQuery(name = "DaneStDaX.findByDaxInt1", query = "SELECT d FROM DaneStDaX d WHERE d.daxInt1 = :daxInt1"),
    @NamedQuery(name = "DaneStDaX.findByDaxInt2", query = "SELECT d FROM DaneStDaX d WHERE d.daxInt2 = :daxInt2"),
    @NamedQuery(name = "DaneStDaX.findByDaxInt3", query = "SELECT d FROM DaneStDaX d WHERE d.daxInt3 = :daxInt3"),
    @NamedQuery(name = "DaneStDaX.findByDaxInt4", query = "SELECT d FROM DaneStDaX d WHERE d.daxInt4 = :daxInt4"),
    @NamedQuery(name = "DaneStDaX.findByDaxNumeric1", query = "SELECT d FROM DaneStDaX d WHERE d.daxNumeric1 = :daxNumeric1"),
    @NamedQuery(name = "DaneStDaX.findByDaxNumeric2", query = "SELECT d FROM DaneStDaX d WHERE d.daxNumeric2 = :daxNumeric2"),
    @NamedQuery(name = "DaneStDaX.findByDaxNumeric3", query = "SELECT d FROM DaneStDaX d WHERE d.daxNumeric3 = :daxNumeric3"),
    @NamedQuery(name = "DaneStDaX.findByDaxNumeric4", query = "SELECT d FROM DaneStDaX d WHERE d.daxNumeric4 = :daxNumeric4"),
    @NamedQuery(name = "DaneStDaX.findByDaxTime1", query = "SELECT d FROM DaneStDaX d WHERE d.daxTime1 = :daxTime1"),
    @NamedQuery(name = "DaneStDaX.findByDax2char1", query = "SELECT d FROM DaneStDaX d WHERE d.dax2char1 = :dax2char1"),
    @NamedQuery(name = "DaneStDaX.findByDax2char2", query = "SELECT d FROM DaneStDaX d WHERE d.dax2char2 = :dax2char2")})
public class DaneStDaX implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dax_serial", nullable = false)
    private Integer daxSerial;
    @Column(name = "dax_sys_serial")
    private Integer daxSysSerial;
    @Column(name = "dax_int_serial")
    private Integer daxIntSerial;
    @Column(name = "dax_char_1")
    private Character daxChar1;
    @Column(name = "dax_char_2")
    private Character daxChar2;
    @Column(name = "dax_char_3")
    private Character daxChar3;
    @Column(name = "dax_char_4")
    private Character daxChar4;
    @Size(max = 254)
    @Column(name = "dax_vchar_1", length = 254)
    private String daxVchar1;
    @Size(max = 254)
    @Column(name = "dax_vchar_2", length = 254)
    private String daxVchar2;
    @Size(max = 254)
    @Column(name = "dax_vchar_3", length = 254)
    private String daxVchar3;
    @Size(max = 254)
    @Column(name = "dax_vchar_4", length = 254)
    private String daxVchar4;
    @Column(name = "dax_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date daxDate1;
    @Column(name = "dax_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date daxDate2;
    @Column(name = "dax_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date daxDate3;
    @Column(name = "dax_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date daxDate4;
    @Column(name = "dax_int_1")
    private Integer daxInt1;
    @Column(name = "dax_int_2")
    private Integer daxInt2;
    @Column(name = "dax_int_3")
    private Integer daxInt3;
    @Column(name = "dax_int_4")
    private Integer daxInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dax_numeric_1", precision = 17, scale = 6)
    private BigDecimal daxNumeric1;
    @Column(name = "dax_numeric_2", precision = 17, scale = 6)
    private BigDecimal daxNumeric2;
    @Column(name = "dax_numeric_3", precision = 17, scale = 6)
    private BigDecimal daxNumeric3;
    @Column(name = "dax_numeric_4", precision = 17, scale = 6)
    private BigDecimal daxNumeric4;
    @Column(name = "dax_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date daxTime1;
    @Size(max = 2)
    @Column(name = "dax_2char_1", length = 2)
    private String dax2char1;
    @Size(max = 2)
    @Column(name = "dax_2char_2", length = 2)
    private String dax2char2;
    @JoinColumn(name = "dax_dls_serial", referencedColumnName = "dls_serial")
    @ManyToOne
    private DaneLiSl daxDlsSerial;

    public DaneStDaX() {
    }

    public DaneStDaX(Integer daxSerial) {
        this.daxSerial = daxSerial;
    }

    public Integer getDaxSerial() {
        return daxSerial;
    }

    public void setDaxSerial(Integer daxSerial) {
        this.daxSerial = daxSerial;
    }

    public Integer getDaxSysSerial() {
        return daxSysSerial;
    }

    public void setDaxSysSerial(Integer daxSysSerial) {
        this.daxSysSerial = daxSysSerial;
    }

    public Integer getDaxIntSerial() {
        return daxIntSerial;
    }

    public void setDaxIntSerial(Integer daxIntSerial) {
        this.daxIntSerial = daxIntSerial;
    }

    public Character getDaxChar1() {
        return daxChar1;
    }

    public void setDaxChar1(Character daxChar1) {
        this.daxChar1 = daxChar1;
    }

    public Character getDaxChar2() {
        return daxChar2;
    }

    public void setDaxChar2(Character daxChar2) {
        this.daxChar2 = daxChar2;
    }

    public Character getDaxChar3() {
        return daxChar3;
    }

    public void setDaxChar3(Character daxChar3) {
        this.daxChar3 = daxChar3;
    }

    public Character getDaxChar4() {
        return daxChar4;
    }

    public void setDaxChar4(Character daxChar4) {
        this.daxChar4 = daxChar4;
    }

    public String getDaxVchar1() {
        return daxVchar1;
    }

    public void setDaxVchar1(String daxVchar1) {
        this.daxVchar1 = daxVchar1;
    }

    public String getDaxVchar2() {
        return daxVchar2;
    }

    public void setDaxVchar2(String daxVchar2) {
        this.daxVchar2 = daxVchar2;
    }

    public String getDaxVchar3() {
        return daxVchar3;
    }

    public void setDaxVchar3(String daxVchar3) {
        this.daxVchar3 = daxVchar3;
    }

    public String getDaxVchar4() {
        return daxVchar4;
    }

    public void setDaxVchar4(String daxVchar4) {
        this.daxVchar4 = daxVchar4;
    }

    public Date getDaxDate1() {
        return daxDate1;
    }

    public void setDaxDate1(Date daxDate1) {
        this.daxDate1 = daxDate1;
    }

    public Date getDaxDate2() {
        return daxDate2;
    }

    public void setDaxDate2(Date daxDate2) {
        this.daxDate2 = daxDate2;
    }

    public Date getDaxDate3() {
        return daxDate3;
    }

    public void setDaxDate3(Date daxDate3) {
        this.daxDate3 = daxDate3;
    }

    public Date getDaxDate4() {
        return daxDate4;
    }

    public void setDaxDate4(Date daxDate4) {
        this.daxDate4 = daxDate4;
    }

    public Integer getDaxInt1() {
        return daxInt1;
    }

    public void setDaxInt1(Integer daxInt1) {
        this.daxInt1 = daxInt1;
    }

    public Integer getDaxInt2() {
        return daxInt2;
    }

    public void setDaxInt2(Integer daxInt2) {
        this.daxInt2 = daxInt2;
    }

    public Integer getDaxInt3() {
        return daxInt3;
    }

    public void setDaxInt3(Integer daxInt3) {
        this.daxInt3 = daxInt3;
    }

    public Integer getDaxInt4() {
        return daxInt4;
    }

    public void setDaxInt4(Integer daxInt4) {
        this.daxInt4 = daxInt4;
    }

    public BigDecimal getDaxNumeric1() {
        return daxNumeric1;
    }

    public void setDaxNumeric1(BigDecimal daxNumeric1) {
        this.daxNumeric1 = daxNumeric1;
    }

    public BigDecimal getDaxNumeric2() {
        return daxNumeric2;
    }

    public void setDaxNumeric2(BigDecimal daxNumeric2) {
        this.daxNumeric2 = daxNumeric2;
    }

    public BigDecimal getDaxNumeric3() {
        return daxNumeric3;
    }

    public void setDaxNumeric3(BigDecimal daxNumeric3) {
        this.daxNumeric3 = daxNumeric3;
    }

    public BigDecimal getDaxNumeric4() {
        return daxNumeric4;
    }

    public void setDaxNumeric4(BigDecimal daxNumeric4) {
        this.daxNumeric4 = daxNumeric4;
    }

    public Date getDaxTime1() {
        return daxTime1;
    }

    public void setDaxTime1(Date daxTime1) {
        this.daxTime1 = daxTime1;
    }

    public String getDax2char1() {
        return dax2char1;
    }

    public void setDax2char1(String dax2char1) {
        this.dax2char1 = dax2char1;
    }

    public String getDax2char2() {
        return dax2char2;
    }

    public void setDax2char2(String dax2char2) {
        this.dax2char2 = dax2char2;
    }

    public DaneLiSl getDaxDlsSerial() {
        return daxDlsSerial;
    }

    public void setDaxDlsSerial(DaneLiSl daxDlsSerial) {
        this.daxDlsSerial = daxDlsSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (daxSerial != null ? daxSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStDaX)) {
            return false;
        }
        DaneStDaX other = (DaneStDaX) object;
        if ((this.daxSerial == null && other.daxSerial != null) || (this.daxSerial != null && !this.daxSerial.equals(other.daxSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStDaX[ daxSerial=" + daxSerial + " ]";
    }
    
}
