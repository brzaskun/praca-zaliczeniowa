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
@Table(name = "dane_stat_s", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatS.findAll", query = "SELECT d FROM DaneStatS d"),
    @NamedQuery(name = "DaneStatS.findByDssSerial", query = "SELECT d FROM DaneStatS d WHERE d.dssSerial = :dssSerial"),
    @NamedQuery(name = "DaneStatS.findByDssSysSerial", query = "SELECT d FROM DaneStatS d WHERE d.dssSysSerial = :dssSysSerial"),
    @NamedQuery(name = "DaneStatS.findByDssChar1", query = "SELECT d FROM DaneStatS d WHERE d.dssChar1 = :dssChar1"),
    @NamedQuery(name = "DaneStatS.findByDssChar2", query = "SELECT d FROM DaneStatS d WHERE d.dssChar2 = :dssChar2"),
    @NamedQuery(name = "DaneStatS.findByDss2char1", query = "SELECT d FROM DaneStatS d WHERE d.dss2char1 = :dss2char1"),
    @NamedQuery(name = "DaneStatS.findByDss2char2", query = "SELECT d FROM DaneStatS d WHERE d.dss2char2 = :dss2char2"),
    @NamedQuery(name = "DaneStatS.findByDssVchar1", query = "SELECT d FROM DaneStatS d WHERE d.dssVchar1 = :dssVchar1"),
    @NamedQuery(name = "DaneStatS.findByDssVchar2", query = "SELECT d FROM DaneStatS d WHERE d.dssVchar2 = :dssVchar2"),
    @NamedQuery(name = "DaneStatS.findByDssNumeric1", query = "SELECT d FROM DaneStatS d WHERE d.dssNumeric1 = :dssNumeric1"),
    @NamedQuery(name = "DaneStatS.findByDssNumeric2", query = "SELECT d FROM DaneStatS d WHERE d.dssNumeric2 = :dssNumeric2"),
    @NamedQuery(name = "DaneStatS.findByDssNumeric3", query = "SELECT d FROM DaneStatS d WHERE d.dssNumeric3 = :dssNumeric3"),
    @NamedQuery(name = "DaneStatS.findByDssNumeric4", query = "SELECT d FROM DaneStatS d WHERE d.dssNumeric4 = :dssNumeric4"),
    @NamedQuery(name = "DaneStatS.findByDssInt1", query = "SELECT d FROM DaneStatS d WHERE d.dssInt1 = :dssInt1"),
    @NamedQuery(name = "DaneStatS.findByDssInt2", query = "SELECT d FROM DaneStatS d WHERE d.dssInt2 = :dssInt2"),
    @NamedQuery(name = "DaneStatS.findByDssInt3", query = "SELECT d FROM DaneStatS d WHERE d.dssInt3 = :dssInt3"),
    @NamedQuery(name = "DaneStatS.findByDssInt4", query = "SELECT d FROM DaneStatS d WHERE d.dssInt4 = :dssInt4"),
    @NamedQuery(name = "DaneStatS.findByDssDate1", query = "SELECT d FROM DaneStatS d WHERE d.dssDate1 = :dssDate1"),
    @NamedQuery(name = "DaneStatS.findByDssDate2", query = "SELECT d FROM DaneStatS d WHERE d.dssDate2 = :dssDate2"),
    @NamedQuery(name = "DaneStatS.findByDssTime1", query = "SELECT d FROM DaneStatS d WHERE d.dssTime1 = :dssTime1"),
    @NamedQuery(name = "DaneStatS.findByDssOspSerial", query = "SELECT d FROM DaneStatS d WHERE d.dssOspSerial = :dssOspSerial")})
public class DaneStatS implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dss_serial", nullable = false)
    private Integer dssSerial;
    @Column(name = "dss_sys_serial")
    private Integer dssSysSerial;
    @Column(name = "dss_char_1")
    private Character dssChar1;
    @Column(name = "dss_char_2")
    private Character dssChar2;
    @Size(max = 2)
    @Column(name = "dss_2char_1", length = 2)
    private String dss2char1;
    @Size(max = 2)
    @Column(name = "dss_2char_2", length = 2)
    private String dss2char2;
    @Size(max = 128)
    @Column(name = "dss_vchar_1", length = 128)
    private String dssVchar1;
    @Size(max = 128)
    @Column(name = "dss_vchar_2", length = 128)
    private String dssVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dss_numeric_1", precision = 17, scale = 6)
    private BigDecimal dssNumeric1;
    @Column(name = "dss_numeric_2", precision = 17, scale = 6)
    private BigDecimal dssNumeric2;
    @Column(name = "dss_numeric_3", precision = 17, scale = 6)
    private BigDecimal dssNumeric3;
    @Column(name = "dss_numeric_4", precision = 17, scale = 6)
    private BigDecimal dssNumeric4;
    @Column(name = "dss_int_1")
    private Integer dssInt1;
    @Column(name = "dss_int_2")
    private Integer dssInt2;
    @Column(name = "dss_int_3")
    private Integer dssInt3;
    @Column(name = "dss_int_4")
    private Integer dssInt4;
    @Column(name = "dss_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dssDate1;
    @Column(name = "dss_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dssDate2;
    @Column(name = "dss_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dssTime1;
    @Column(name = "dss_osp_serial")
    private Integer dssOspSerial;
    @JoinColumn(name = "dss_osp_serial_pit", referencedColumnName = "osp_serial")
    @ManyToOne
    private OsobaPit dssOspSerialPit;
    @JoinColumn(name = "dss_opo_serial", referencedColumnName = "opo_serial")
    @ManyToOne
    private OsobaPot dssOpoSerial;
    @JoinColumn(name = "dss_opt_serial", referencedColumnName = "opt_serial")
    @ManyToOne
    private OsobaPropTyp dssOptSerial;
    @JoinColumn(name = "dss_osp_serial_prz", referencedColumnName = "osp_serial")
    @ManyToOne
    private OsobaPrz dssOspSerialPrz;
    @JoinColumn(name = "dss_oss_serial", referencedColumnName = "oss_serial")
    @ManyToOne
    private OsobaSkl dssOssSerial;
    @JoinColumn(name = "dss_ozl_serial", referencedColumnName = "ozl_serial")
    @ManyToOne
    private OsobaZlec dssOzlSerial;

    public DaneStatS() {
    }

    public DaneStatS(Integer dssSerial) {
        this.dssSerial = dssSerial;
    }

    public Integer getDssSerial() {
        return dssSerial;
    }

    public void setDssSerial(Integer dssSerial) {
        this.dssSerial = dssSerial;
    }

    public Integer getDssSysSerial() {
        return dssSysSerial;
    }

    public void setDssSysSerial(Integer dssSysSerial) {
        this.dssSysSerial = dssSysSerial;
    }

    public Character getDssChar1() {
        return dssChar1;
    }

    public void setDssChar1(Character dssChar1) {
        this.dssChar1 = dssChar1;
    }

    public Character getDssChar2() {
        return dssChar2;
    }

    public void setDssChar2(Character dssChar2) {
        this.dssChar2 = dssChar2;
    }

    public String getDss2char1() {
        return dss2char1;
    }

    public void setDss2char1(String dss2char1) {
        this.dss2char1 = dss2char1;
    }

    public String getDss2char2() {
        return dss2char2;
    }

    public void setDss2char2(String dss2char2) {
        this.dss2char2 = dss2char2;
    }

    public String getDssVchar1() {
        return dssVchar1;
    }

    public void setDssVchar1(String dssVchar1) {
        this.dssVchar1 = dssVchar1;
    }

    public String getDssVchar2() {
        return dssVchar2;
    }

    public void setDssVchar2(String dssVchar2) {
        this.dssVchar2 = dssVchar2;
    }

    public BigDecimal getDssNumeric1() {
        return dssNumeric1;
    }

    public void setDssNumeric1(BigDecimal dssNumeric1) {
        this.dssNumeric1 = dssNumeric1;
    }

    public BigDecimal getDssNumeric2() {
        return dssNumeric2;
    }

    public void setDssNumeric2(BigDecimal dssNumeric2) {
        this.dssNumeric2 = dssNumeric2;
    }

    public BigDecimal getDssNumeric3() {
        return dssNumeric3;
    }

    public void setDssNumeric3(BigDecimal dssNumeric3) {
        this.dssNumeric3 = dssNumeric3;
    }

    public BigDecimal getDssNumeric4() {
        return dssNumeric4;
    }

    public void setDssNumeric4(BigDecimal dssNumeric4) {
        this.dssNumeric4 = dssNumeric4;
    }

    public Integer getDssInt1() {
        return dssInt1;
    }

    public void setDssInt1(Integer dssInt1) {
        this.dssInt1 = dssInt1;
    }

    public Integer getDssInt2() {
        return dssInt2;
    }

    public void setDssInt2(Integer dssInt2) {
        this.dssInt2 = dssInt2;
    }

    public Integer getDssInt3() {
        return dssInt3;
    }

    public void setDssInt3(Integer dssInt3) {
        this.dssInt3 = dssInt3;
    }

    public Integer getDssInt4() {
        return dssInt4;
    }

    public void setDssInt4(Integer dssInt4) {
        this.dssInt4 = dssInt4;
    }

    public Date getDssDate1() {
        return dssDate1;
    }

    public void setDssDate1(Date dssDate1) {
        this.dssDate1 = dssDate1;
    }

    public Date getDssDate2() {
        return dssDate2;
    }

    public void setDssDate2(Date dssDate2) {
        this.dssDate2 = dssDate2;
    }

    public Date getDssTime1() {
        return dssTime1;
    }

    public void setDssTime1(Date dssTime1) {
        this.dssTime1 = dssTime1;
    }

    public Integer getDssOspSerial() {
        return dssOspSerial;
    }

    public void setDssOspSerial(Integer dssOspSerial) {
        this.dssOspSerial = dssOspSerial;
    }

    public OsobaPit getDssOspSerialPit() {
        return dssOspSerialPit;
    }

    public void setDssOspSerialPit(OsobaPit dssOspSerialPit) {
        this.dssOspSerialPit = dssOspSerialPit;
    }

    public OsobaPot getDssOpoSerial() {
        return dssOpoSerial;
    }

    public void setDssOpoSerial(OsobaPot dssOpoSerial) {
        this.dssOpoSerial = dssOpoSerial;
    }

    public OsobaPropTyp getDssOptSerial() {
        return dssOptSerial;
    }

    public void setDssOptSerial(OsobaPropTyp dssOptSerial) {
        this.dssOptSerial = dssOptSerial;
    }

    public OsobaPrz getDssOspSerialPrz() {
        return dssOspSerialPrz;
    }

    public void setDssOspSerialPrz(OsobaPrz dssOspSerialPrz) {
        this.dssOspSerialPrz = dssOspSerialPrz;
    }

    public OsobaSkl getDssOssSerial() {
        return dssOssSerial;
    }

    public void setDssOssSerial(OsobaSkl dssOssSerial) {
        this.dssOssSerial = dssOssSerial;
    }

    public OsobaZlec getDssOzlSerial() {
        return dssOzlSerial;
    }

    public void setDssOzlSerial(OsobaZlec dssOzlSerial) {
        this.dssOzlSerial = dssOzlSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dssSerial != null ? dssSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatS)) {
            return false;
        }
        DaneStatS other = (DaneStatS) object;
        if ((this.dssSerial == null && other.dssSerial != null) || (this.dssSerial != null && !this.dssSerial.equals(other.dssSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatS[ dssSerial=" + dssSerial + " ]";
    }
    
}
