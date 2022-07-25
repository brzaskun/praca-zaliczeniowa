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
@Table(name = "dane_stat_w", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneStatW.findAll", query = "SELECT d FROM DaneStatW d"),
    @NamedQuery(name = "DaneStatW.findByDswSerial", query = "SELECT d FROM DaneStatW d WHERE d.dswSerial = :dswSerial"),
    @NamedQuery(name = "DaneStatW.findByDswSysSerial", query = "SELECT d FROM DaneStatW d WHERE d.dswSysSerial = :dswSysSerial"),
    @NamedQuery(name = "DaneStatW.findByDswChar1", query = "SELECT d FROM DaneStatW d WHERE d.dswChar1 = :dswChar1"),
    @NamedQuery(name = "DaneStatW.findByDswChar2", query = "SELECT d FROM DaneStatW d WHERE d.dswChar2 = :dswChar2"),
    @NamedQuery(name = "DaneStatW.findByDsw2char1", query = "SELECT d FROM DaneStatW d WHERE d.dsw2char1 = :dsw2char1"),
    @NamedQuery(name = "DaneStatW.findByDsw2char2", query = "SELECT d FROM DaneStatW d WHERE d.dsw2char2 = :dsw2char2"),
    @NamedQuery(name = "DaneStatW.findByDswVchar1", query = "SELECT d FROM DaneStatW d WHERE d.dswVchar1 = :dswVchar1"),
    @NamedQuery(name = "DaneStatW.findByDswVchar2", query = "SELECT d FROM DaneStatW d WHERE d.dswVchar2 = :dswVchar2"),
    @NamedQuery(name = "DaneStatW.findByDswNumeric1", query = "SELECT d FROM DaneStatW d WHERE d.dswNumeric1 = :dswNumeric1"),
    @NamedQuery(name = "DaneStatW.findByDswNumeric2", query = "SELECT d FROM DaneStatW d WHERE d.dswNumeric2 = :dswNumeric2"),
    @NamedQuery(name = "DaneStatW.findByDswNumeric3", query = "SELECT d FROM DaneStatW d WHERE d.dswNumeric3 = :dswNumeric3"),
    @NamedQuery(name = "DaneStatW.findByDswNumeric4", query = "SELECT d FROM DaneStatW d WHERE d.dswNumeric4 = :dswNumeric4"),
    @NamedQuery(name = "DaneStatW.findByDswInt1", query = "SELECT d FROM DaneStatW d WHERE d.dswInt1 = :dswInt1"),
    @NamedQuery(name = "DaneStatW.findByDswInt2", query = "SELECT d FROM DaneStatW d WHERE d.dswInt2 = :dswInt2"),
    @NamedQuery(name = "DaneStatW.findByDswInt3", query = "SELECT d FROM DaneStatW d WHERE d.dswInt3 = :dswInt3"),
    @NamedQuery(name = "DaneStatW.findByDswInt4", query = "SELECT d FROM DaneStatW d WHERE d.dswInt4 = :dswInt4"),
    @NamedQuery(name = "DaneStatW.findByDswDate1", query = "SELECT d FROM DaneStatW d WHERE d.dswDate1 = :dswDate1"),
    @NamedQuery(name = "DaneStatW.findByDswDate2", query = "SELECT d FROM DaneStatW d WHERE d.dswDate2 = :dswDate2"),
    @NamedQuery(name = "DaneStatW.findByDswTime1", query = "SELECT d FROM DaneStatW d WHERE d.dswTime1 = :dswTime1")})
public class DaneStatW implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dsw_serial", nullable = false)
    private Integer dswSerial;
    @Column(name = "dsw_sys_serial")
    private Integer dswSysSerial;
    @Column(name = "dsw_char_1")
    private Character dswChar1;
    @Column(name = "dsw_char_2")
    private Character dswChar2;
    @Size(max = 2)
    @Column(name = "dsw_2char_1", length = 2)
    private String dsw2char1;
    @Size(max = 2)
    @Column(name = "dsw_2char_2", length = 2)
    private String dsw2char2;
    @Size(max = 128)
    @Column(name = "dsw_vchar_1", length = 128)
    private String dswVchar1;
    @Size(max = 128)
    @Column(name = "dsw_vchar_2", length = 128)
    private String dswVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dsw_numeric_1", precision = 17, scale = 6)
    private BigDecimal dswNumeric1;
    @Column(name = "dsw_numeric_2", precision = 17, scale = 6)
    private BigDecimal dswNumeric2;
    @Column(name = "dsw_numeric_3", precision = 17, scale = 6)
    private BigDecimal dswNumeric3;
    @Column(name = "dsw_numeric_4", precision = 17, scale = 6)
    private BigDecimal dswNumeric4;
    @Column(name = "dsw_int_1")
    private Integer dswInt1;
    @Column(name = "dsw_int_2")
    private Integer dswInt2;
    @Column(name = "dsw_int_3")
    private Integer dswInt3;
    @Column(name = "dsw_int_4")
    private Integer dswInt4;
    @Column(name = "dsw_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dswDate1;
    @Column(name = "dsw_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dswDate2;
    @Column(name = "dsw_time_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dswTime1;
    @JoinColumn(name = "dsw_ppo_serial", referencedColumnName = "opo_serial")
    @ManyToOne
    private OsobaPot dswPpoSerial;
    @JoinColumn(name = "dsw_prz_serial", referencedColumnName = "prz_serial")
    @ManyToOne
    private PlacePrz dswPrzSerial;
    @JoinColumn(name = "dsw_skl_serial", referencedColumnName = "skl_serial")
    @ManyToOne
    private PlaceSkl dswSklSerial;
    @JoinColumn(name = "dsw_pzl_serial", referencedColumnName = "pzl_serial")
    @ManyToOne
    private PlaceZlec dswPzlSerial;

    public DaneStatW() {
    }

    public DaneStatW(Integer dswSerial) {
        this.dswSerial = dswSerial;
    }

    public Integer getDswSerial() {
        return dswSerial;
    }

    public void setDswSerial(Integer dswSerial) {
        this.dswSerial = dswSerial;
    }

    public Integer getDswSysSerial() {
        return dswSysSerial;
    }

    public void setDswSysSerial(Integer dswSysSerial) {
        this.dswSysSerial = dswSysSerial;
    }

    public Character getDswChar1() {
        return dswChar1;
    }

    public void setDswChar1(Character dswChar1) {
        this.dswChar1 = dswChar1;
    }

    public Character getDswChar2() {
        return dswChar2;
    }

    public void setDswChar2(Character dswChar2) {
        this.dswChar2 = dswChar2;
    }

    public String getDsw2char1() {
        return dsw2char1;
    }

    public void setDsw2char1(String dsw2char1) {
        this.dsw2char1 = dsw2char1;
    }

    public String getDsw2char2() {
        return dsw2char2;
    }

    public void setDsw2char2(String dsw2char2) {
        this.dsw2char2 = dsw2char2;
    }

    public String getDswVchar1() {
        return dswVchar1;
    }

    public void setDswVchar1(String dswVchar1) {
        this.dswVchar1 = dswVchar1;
    }

    public String getDswVchar2() {
        return dswVchar2;
    }

    public void setDswVchar2(String dswVchar2) {
        this.dswVchar2 = dswVchar2;
    }

    public BigDecimal getDswNumeric1() {
        return dswNumeric1;
    }

    public void setDswNumeric1(BigDecimal dswNumeric1) {
        this.dswNumeric1 = dswNumeric1;
    }

    public BigDecimal getDswNumeric2() {
        return dswNumeric2;
    }

    public void setDswNumeric2(BigDecimal dswNumeric2) {
        this.dswNumeric2 = dswNumeric2;
    }

    public BigDecimal getDswNumeric3() {
        return dswNumeric3;
    }

    public void setDswNumeric3(BigDecimal dswNumeric3) {
        this.dswNumeric3 = dswNumeric3;
    }

    public BigDecimal getDswNumeric4() {
        return dswNumeric4;
    }

    public void setDswNumeric4(BigDecimal dswNumeric4) {
        this.dswNumeric4 = dswNumeric4;
    }

    public Integer getDswInt1() {
        return dswInt1;
    }

    public void setDswInt1(Integer dswInt1) {
        this.dswInt1 = dswInt1;
    }

    public Integer getDswInt2() {
        return dswInt2;
    }

    public void setDswInt2(Integer dswInt2) {
        this.dswInt2 = dswInt2;
    }

    public Integer getDswInt3() {
        return dswInt3;
    }

    public void setDswInt3(Integer dswInt3) {
        this.dswInt3 = dswInt3;
    }

    public Integer getDswInt4() {
        return dswInt4;
    }

    public void setDswInt4(Integer dswInt4) {
        this.dswInt4 = dswInt4;
    }

    public Date getDswDate1() {
        return dswDate1;
    }

    public void setDswDate1(Date dswDate1) {
        this.dswDate1 = dswDate1;
    }

    public Date getDswDate2() {
        return dswDate2;
    }

    public void setDswDate2(Date dswDate2) {
        this.dswDate2 = dswDate2;
    }

    public Date getDswTime1() {
        return dswTime1;
    }

    public void setDswTime1(Date dswTime1) {
        this.dswTime1 = dswTime1;
    }

    public OsobaPot getDswPpoSerial() {
        return dswPpoSerial;
    }

    public void setDswPpoSerial(OsobaPot dswPpoSerial) {
        this.dswPpoSerial = dswPpoSerial;
    }

    public PlacePrz getDswPrzSerial() {
        return dswPrzSerial;
    }

    public void setDswPrzSerial(PlacePrz dswPrzSerial) {
        this.dswPrzSerial = dswPrzSerial;
    }

    public PlaceSkl getDswSklSerial() {
        return dswSklSerial;
    }

    public void setDswSklSerial(PlaceSkl dswSklSerial) {
        this.dswSklSerial = dswSklSerial;
    }

    public PlaceZlec getDswPzlSerial() {
        return dswPzlSerial;
    }

    public void setDswPzlSerial(PlaceZlec dswPzlSerial) {
        this.dswPzlSerial = dswPzlSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dswSerial != null ? dswSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneStatW)) {
            return false;
        }
        DaneStatW other = (DaneStatW) object;
        if ((this.dswSerial == null && other.dswSerial != null) || (this.dswSerial != null && !this.dswSerial.equals(other.dswSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneStatW[ dswSerial=" + dswSerial + " ]";
    }
    
}
