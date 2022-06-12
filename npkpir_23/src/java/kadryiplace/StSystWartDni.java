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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "st_syst_wart_dni", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StSystWartDni.findAll", query = "SELECT s FROM StSystWartDni s"),
    @NamedQuery(name = "StSystWartDni.findBySswSerial", query = "SELECT s FROM StSystWartDni s WHERE s.sswSerial = :sswSerial"),
    @NamedQuery(name = "StSystWartDni.findBySswData", query = "SELECT s FROM StSystWartDni s WHERE s.sswData = :sswData"),
    @NamedQuery(name = "StSystWartDni.findBySswNumeric", query = "SELECT s FROM StSystWartDni s WHERE s.sswNumeric = :sswNumeric"),
    @NamedQuery(name = "StSystWartDni.findBySswDod1", query = "SELECT s FROM StSystWartDni s WHERE s.sswDod1 = :sswDod1"),
    @NamedQuery(name = "StSystWartDni.findBySswDod2", query = "SELECT s FROM StSystWartDni s WHERE s.sswDod2 = :sswDod2"),
    @NamedQuery(name = "StSystWartDni.findBySswDod3", query = "SELECT s FROM StSystWartDni s WHERE s.sswDod3 = :sswDod3"),
    @NamedQuery(name = "StSystWartDni.findBySswNumeric2", query = "SELECT s FROM StSystWartDni s WHERE s.sswNumeric2 = :sswNumeric2"),
    @NamedQuery(name = "StSystWartDni.findBySswCzas1", query = "SELECT s FROM StSystWartDni s WHERE s.sswCzas1 = :sswCzas1"),
    @NamedQuery(name = "StSystWartDni.findBySswCzas2", query = "SELECT s FROM StSystWartDni s WHERE s.sswCzas2 = :sswCzas2"),
    @NamedQuery(name = "StSystWartDni.findBySswDod4", query = "SELECT s FROM StSystWartDni s WHERE s.sswDod4 = :sswDod4"),
    @NamedQuery(name = "StSystWartDni.findBySswTyp", query = "SELECT s FROM StSystWartDni s WHERE s.sswTyp = :sswTyp")})
public class StSystWartDni implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ssw_serial", nullable = false)
    private Integer sswSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ssw_data", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sswData;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ssw_numeric", nullable = false, precision = 17, scale = 6)
    private BigDecimal sswNumeric;
    @Column(name = "ssw_dod_1")
    private Character sswDod1;
    @Column(name = "ssw_dod_2")
    private Character sswDod2;
    @Column(name = "ssw_dod_3")
    private Character sswDod3;
    @Column(name = "ssw_numeric_2", precision = 17, scale = 6)
    private BigDecimal sswNumeric2;
    @Column(name = "ssw_czas_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sswCzas1;
    @Column(name = "ssw_czas_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sswCzas2;
    @Column(name = "ssw_dod_4")
    private Character sswDod4;
    @Column(name = "ssw_typ")
    private Character sswTyp;
    @JoinColumn(name = "ssw_sso_serial", referencedColumnName = "sso_serial", nullable = false)
    @ManyToOne(optional = false)
    private StSystWart sswSsoSerial;

    public StSystWartDni() {
    }

    public StSystWartDni(Integer sswSerial) {
        this.sswSerial = sswSerial;
    }

    public StSystWartDni(Integer sswSerial, Date sswData, BigDecimal sswNumeric) {
        this.sswSerial = sswSerial;
        this.sswData = sswData;
        this.sswNumeric = sswNumeric;
    }

    public Integer getSswSerial() {
        return sswSerial;
    }

    public void setSswSerial(Integer sswSerial) {
        this.sswSerial = sswSerial;
    }

    public Date getSswData() {
        return sswData;
    }

    public void setSswData(Date sswData) {
        this.sswData = sswData;
    }

    public BigDecimal getSswNumeric() {
        return sswNumeric;
    }

    public void setSswNumeric(BigDecimal sswNumeric) {
        this.sswNumeric = sswNumeric;
    }

    public Character getSswDod1() {
        return sswDod1;
    }

    public void setSswDod1(Character sswDod1) {
        this.sswDod1 = sswDod1;
    }

    public Character getSswDod2() {
        return sswDod2;
    }

    public void setSswDod2(Character sswDod2) {
        this.sswDod2 = sswDod2;
    }

    public Character getSswDod3() {
        return sswDod3;
    }

    public void setSswDod3(Character sswDod3) {
        this.sswDod3 = sswDod3;
    }

    public BigDecimal getSswNumeric2() {
        return sswNumeric2;
    }

    public void setSswNumeric2(BigDecimal sswNumeric2) {
        this.sswNumeric2 = sswNumeric2;
    }

    public Date getSswCzas1() {
        return sswCzas1;
    }

    public void setSswCzas1(Date sswCzas1) {
        this.sswCzas1 = sswCzas1;
    }

    public Date getSswCzas2() {
        return sswCzas2;
    }

    public void setSswCzas2(Date sswCzas2) {
        this.sswCzas2 = sswCzas2;
    }

    public Character getSswDod4() {
        return sswDod4;
    }

    public void setSswDod4(Character sswDod4) {
        this.sswDod4 = sswDod4;
    }

    public Character getSswTyp() {
        return sswTyp;
    }

    public void setSswTyp(Character sswTyp) {
        this.sswTyp = sswTyp;
    }

    public StSystWart getSswSsoSerial() {
        return sswSsoSerial;
    }

    public void setSswSsoSerial(StSystWart sswSsoSerial) {
        this.sswSsoSerial = sswSsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sswSerial != null ? sswSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StSystWartDni)) {
            return false;
        }
        StSystWartDni other = (StSystWartDni) object;
        if ((this.sswSerial == null && other.sswSerial != null) || (this.sswSerial != null && !this.sswSerial.equals(other.sswSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.StSystWartDni[ sswSerial=" + sswSerial + " ]";
    }
    
}
