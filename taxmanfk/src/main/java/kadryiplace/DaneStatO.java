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
@Table(name = "dane_stat_o", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatO.findAll", query = "SELECT d FROM DaneStatO d"),
    @NamedQuery(name = "DaneStatO.findByDsoSerial", query = "SELECT d FROM DaneStatO d WHERE d.dsoSerial = :dsoSerial"),
    @NamedQuery(name = "DaneStatO.findByDsoSysSerial", query = "SELECT d FROM DaneStatO d WHERE d.dsoSysSerial = :dsoSysSerial"),
    @NamedQuery(name = "DaneStatO.findByDsoChar1", query = "SELECT d FROM DaneStatO d WHERE d.dsoChar1 = :dsoChar1"),
    @NamedQuery(name = "DaneStatO.findByDsoChar2", query = "SELECT d FROM DaneStatO d WHERE d.dsoChar2 = :dsoChar2"),
    @NamedQuery(name = "DaneStatO.findByDso2char1", query = "SELECT d FROM DaneStatO d WHERE d.dso2char1 = :dso2char1"),
    @NamedQuery(name = "DaneStatO.findByDso2char2", query = "SELECT d FROM DaneStatO d WHERE d.dso2char2 = :dso2char2"),
    @NamedQuery(name = "DaneStatO.findByDsoVchar1", query = "SELECT d FROM DaneStatO d WHERE d.dsoVchar1 = :dsoVchar1"),
    @NamedQuery(name = "DaneStatO.findByDsoVchar2", query = "SELECT d FROM DaneStatO d WHERE d.dsoVchar2 = :dsoVchar2"),
    @NamedQuery(name = "DaneStatO.findByDsoInt1", query = "SELECT d FROM DaneStatO d WHERE d.dsoInt1 = :dsoInt1"),
    @NamedQuery(name = "DaneStatO.findByDsoInt2", query = "SELECT d FROM DaneStatO d WHERE d.dsoInt2 = :dsoInt2"),
    @NamedQuery(name = "DaneStatO.findByDsoInt3", query = "SELECT d FROM DaneStatO d WHERE d.dsoInt3 = :dsoInt3"),
    @NamedQuery(name = "DaneStatO.findByDsoInt4", query = "SELECT d FROM DaneStatO d WHERE d.dsoInt4 = :dsoInt4"),
    @NamedQuery(name = "DaneStatO.findByDsoNumeric1", query = "SELECT d FROM DaneStatO d WHERE d.dsoNumeric1 = :dsoNumeric1"),
    @NamedQuery(name = "DaneStatO.findByDsoNumeric2", query = "SELECT d FROM DaneStatO d WHERE d.dsoNumeric2 = :dsoNumeric2"),
    @NamedQuery(name = "DaneStatO.findByDsoNumeric3", query = "SELECT d FROM DaneStatO d WHERE d.dsoNumeric3 = :dsoNumeric3"),
    @NamedQuery(name = "DaneStatO.findByDsoNumeric4", query = "SELECT d FROM DaneStatO d WHERE d.dsoNumeric4 = :dsoNumeric4"),
    @NamedQuery(name = "DaneStatO.findByDsoDate1", query = "SELECT d FROM DaneStatO d WHERE d.dsoDate1 = :dsoDate1"),
    @NamedQuery(name = "DaneStatO.findByDsoDate2", query = "SELECT d FROM DaneStatO d WHERE d.dsoDate2 = :dsoDate2"),
    @NamedQuery(name = "DaneStatO.findByDsoTime1", query = "SELECT d FROM DaneStatO d WHERE d.dsoTime1 = :dsoTime1")})
public class DaneStatO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dso_serial", nullable = false)
    private Integer dsoSerial;
    @Column(name = "dso_sys_serial")
    private Integer dsoSysSerial;
    @Column(name = "dso_char_1")
    private Character dsoChar1;
    @Column(name = "dso_char_2")
    private Character dsoChar2;
    @Size(max = 2)
    @Column(name = "dso_2char_1", length = 2)
    private String dso2char1;
    @Size(max = 2)
    @Column(name = "dso_2char_2", length = 2)
    private String dso2char2;
    @Size(max = 128)
    @Column(name = "dso_vchar_1", length = 128)
    private String dsoVchar1;
    @Size(max = 128)
    @Column(name = "dso_vchar_2", length = 128)
    private String dsoVchar2;
    @Column(name = "dso_int_1")
    private Integer dsoInt1;
    @Column(name = "dso_int_2")
    private Integer dsoInt2;
    @Column(name = "dso_int_3")
    private Integer dsoInt3;
    @Column(name = "dso_int_4")
    private Integer dsoInt4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dso_numeric_1", precision = 17, scale = 6)
    private BigDecimal dsoNumeric1;
    @Column(name = "dso_numeric_2", precision = 17, scale = 6)
    private BigDecimal dsoNumeric2;
    @Column(name = "dso_numeric_3", precision = 17, scale = 6)
    private BigDecimal dsoNumeric3;
    @Column(name = "dso_numeric_4", precision = 17, scale = 6)
    private BigDecimal dsoNumeric4;
    @Column(name = "dso_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsoDate1;
    @Column(name = "dso_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsoDate2;
    @Column(name = "dso_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dsoTime1;
    @JoinColumn(name = "dso_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent dsoKonSerial;

    public DaneStatO() {
    }

    public DaneStatO(Integer dsoSerial) {
        this.dsoSerial = dsoSerial;
    }

    public Integer getDsoSerial() {
        return dsoSerial;
    }

    public void setDsoSerial(Integer dsoSerial) {
        this.dsoSerial = dsoSerial;
    }

    public Integer getDsoSysSerial() {
        return dsoSysSerial;
    }

    public void setDsoSysSerial(Integer dsoSysSerial) {
        this.dsoSysSerial = dsoSysSerial;
    }

    public Character getDsoChar1() {
        return dsoChar1;
    }

    public void setDsoChar1(Character dsoChar1) {
        this.dsoChar1 = dsoChar1;
    }

    public Character getDsoChar2() {
        return dsoChar2;
    }

    public void setDsoChar2(Character dsoChar2) {
        this.dsoChar2 = dsoChar2;
    }

    public String getDso2char1() {
        return dso2char1;
    }

    public void setDso2char1(String dso2char1) {
        this.dso2char1 = dso2char1;
    }

    public String getDso2char2() {
        return dso2char2;
    }

    public void setDso2char2(String dso2char2) {
        this.dso2char2 = dso2char2;
    }

    public String getDsoVchar1() {
        return dsoVchar1;
    }

    public void setDsoVchar1(String dsoVchar1) {
        this.dsoVchar1 = dsoVchar1;
    }

    public String getDsoVchar2() {
        return dsoVchar2;
    }

    public void setDsoVchar2(String dsoVchar2) {
        this.dsoVchar2 = dsoVchar2;
    }

    public Integer getDsoInt1() {
        return dsoInt1;
    }

    public void setDsoInt1(Integer dsoInt1) {
        this.dsoInt1 = dsoInt1;
    }

    public Integer getDsoInt2() {
        return dsoInt2;
    }

    public void setDsoInt2(Integer dsoInt2) {
        this.dsoInt2 = dsoInt2;
    }

    public Integer getDsoInt3() {
        return dsoInt3;
    }

    public void setDsoInt3(Integer dsoInt3) {
        this.dsoInt3 = dsoInt3;
    }

    public Integer getDsoInt4() {
        return dsoInt4;
    }

    public void setDsoInt4(Integer dsoInt4) {
        this.dsoInt4 = dsoInt4;
    }

    public BigDecimal getDsoNumeric1() {
        return dsoNumeric1;
    }

    public void setDsoNumeric1(BigDecimal dsoNumeric1) {
        this.dsoNumeric1 = dsoNumeric1;
    }

    public BigDecimal getDsoNumeric2() {
        return dsoNumeric2;
    }

    public void setDsoNumeric2(BigDecimal dsoNumeric2) {
        this.dsoNumeric2 = dsoNumeric2;
    }

    public BigDecimal getDsoNumeric3() {
        return dsoNumeric3;
    }

    public void setDsoNumeric3(BigDecimal dsoNumeric3) {
        this.dsoNumeric3 = dsoNumeric3;
    }

    public BigDecimal getDsoNumeric4() {
        return dsoNumeric4;
    }

    public void setDsoNumeric4(BigDecimal dsoNumeric4) {
        this.dsoNumeric4 = dsoNumeric4;
    }

    public Date getDsoDate1() {
        return dsoDate1;
    }

    public void setDsoDate1(Date dsoDate1) {
        this.dsoDate1 = dsoDate1;
    }

    public Date getDsoDate2() {
        return dsoDate2;
    }

    public void setDsoDate2(Date dsoDate2) {
        this.dsoDate2 = dsoDate2;
    }

    public Date getDsoTime1() {
        return dsoTime1;
    }

    public void setDsoTime1(Date dsoTime1) {
        this.dsoTime1 = dsoTime1;
    }

    public Kontrahent getDsoKonSerial() {
        return dsoKonSerial;
    }

    public void setDsoKonSerial(Kontrahent dsoKonSerial) {
        this.dsoKonSerial = dsoKonSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dsoSerial != null ? dsoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatO)) {
            return false;
        }
        DaneStatO other = (DaneStatO) object;
        if ((this.dsoSerial == null && other.dsoSerial != null) || (this.dsoSerial != null && !this.dsoSerial.equals(other.dsoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatO[ dsoSerial=" + dsoSerial + " ]";
    }
    
}
