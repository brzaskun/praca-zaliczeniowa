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
@Table(name = "stan_hist", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StanHist.findAll", query = "SELECT s FROM StanHist s"),
    @NamedQuery(name = "StanHist.findBySthSerial", query = "SELECT s FROM StanHist s WHERE s.sthSerial = :sthSerial"),
    @NamedQuery(name = "StanHist.findBySthDataOd", query = "SELECT s FROM StanHist s WHERE s.sthDataOd = :sthDataOd"),
    @NamedQuery(name = "StanHist.findBySthDataDo", query = "SELECT s FROM StanHist s WHERE s.sthDataDo = :sthDataDo"),
    @NamedQuery(name = "StanHist.findBySthStatus", query = "SELECT s FROM StanHist s WHERE s.sthStatus = :sthStatus"),
    @NamedQuery(name = "StanHist.findBySthDod1", query = "SELECT s FROM StanHist s WHERE s.sthDod1 = :sthDod1"),
    @NamedQuery(name = "StanHist.findBySthTyp", query = "SELECT s FROM StanHist s WHERE s.sthTyp = :sthTyp"),
    @NamedQuery(name = "StanHist.findBySthDod2", query = "SELECT s FROM StanHist s WHERE s.sthDod2 = :sthDod2"),
    @NamedQuery(name = "StanHist.findBySthDod3", query = "SELECT s FROM StanHist s WHERE s.sthDod3 = :sthDod3"),
    @NamedQuery(name = "StanHist.findBySthDod4", query = "SELECT s FROM StanHist s WHERE s.sthDod4 = :sthDod4"),
    @NamedQuery(name = "StanHist.findBySthVchar1", query = "SELECT s FROM StanHist s WHERE s.sthVchar1 = :sthVchar1"),
    @NamedQuery(name = "StanHist.findBySthVchar2", query = "SELECT s FROM StanHist s WHERE s.sthVchar2 = :sthVchar2"),
    @NamedQuery(name = "StanHist.findBySthNum1", query = "SELECT s FROM StanHist s WHERE s.sthNum1 = :sthNum1")})
public class StanHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sth_serial", nullable = false)
    private Integer sthSerial;
    @Column(name = "sth_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sthDataOd;
    @Column(name = "sth_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sthDataDo;
    @Column(name = "sth_status")
    private Character sthStatus;
    @Column(name = "sth_dod_1")
    private Character sthDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sth_typ", nullable = false)
    private Character sthTyp;
    @Column(name = "sth_dod_2")
    private Character sthDod2;
    @Column(name = "sth_dod_3")
    private Character sthDod3;
    @Column(name = "sth_dod_4")
    private Character sthDod4;
    @Size(max = 64)
    @Column(name = "sth_vchar_1", length = 64)
    private String sthVchar1;
    @Size(max = 64)
    @Column(name = "sth_vchar_2", length = 64)
    private String sthVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sth_num_1", precision = 17, scale = 6)
    private BigDecimal sthNum1;
    @JoinColumn(name = "sth_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba sthOsoSerial;
    @JoinColumn(name = "sth_sta_serial", referencedColumnName = "sta_serial", nullable = false)
    @ManyToOne(optional = false)
    private Stanowisko sthStaSerial;

    public StanHist() {
    }

    public StanHist(Integer sthSerial) {
        this.sthSerial = sthSerial;
    }

    public StanHist(Integer sthSerial, Character sthTyp) {
        this.sthSerial = sthSerial;
        this.sthTyp = sthTyp;
    }

    public Integer getSthSerial() {
        return sthSerial;
    }

    public void setSthSerial(Integer sthSerial) {
        this.sthSerial = sthSerial;
    }

    public Date getSthDataOd() {
        return sthDataOd;
    }

    public void setSthDataOd(Date sthDataOd) {
        this.sthDataOd = sthDataOd;
    }

    public Date getSthDataDo() {
        return sthDataDo;
    }

    public void setSthDataDo(Date sthDataDo) {
        this.sthDataDo = sthDataDo;
    }

    public Character getSthStatus() {
        return sthStatus;
    }

    public void setSthStatus(Character sthStatus) {
        this.sthStatus = sthStatus;
    }

    public Character getSthDod1() {
        return sthDod1;
    }

    public void setSthDod1(Character sthDod1) {
        this.sthDod1 = sthDod1;
    }

    public Character getSthTyp() {
        return sthTyp;
    }

    public void setSthTyp(Character sthTyp) {
        this.sthTyp = sthTyp;
    }

    public Character getSthDod2() {
        return sthDod2;
    }

    public void setSthDod2(Character sthDod2) {
        this.sthDod2 = sthDod2;
    }

    public Character getSthDod3() {
        return sthDod3;
    }

    public void setSthDod3(Character sthDod3) {
        this.sthDod3 = sthDod3;
    }

    public Character getSthDod4() {
        return sthDod4;
    }

    public void setSthDod4(Character sthDod4) {
        this.sthDod4 = sthDod4;
    }

    public String getSthVchar1() {
        return sthVchar1;
    }

    public void setSthVchar1(String sthVchar1) {
        this.sthVchar1 = sthVchar1;
    }

    public String getSthVchar2() {
        return sthVchar2;
    }

    public void setSthVchar2(String sthVchar2) {
        this.sthVchar2 = sthVchar2;
    }

    public BigDecimal getSthNum1() {
        return sthNum1;
    }

    public void setSthNum1(BigDecimal sthNum1) {
        this.sthNum1 = sthNum1;
    }

    public Osoba getSthOsoSerial() {
        return sthOsoSerial;
    }

    public void setSthOsoSerial(Osoba sthOsoSerial) {
        this.sthOsoSerial = sthOsoSerial;
    }

    public Stanowisko getSthStaSerial() {
        return sthStaSerial;
    }

    public void setSthStaSerial(Stanowisko sthStaSerial) {
        this.sthStaSerial = sthStaSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sthSerial != null ? sthSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StanHist)) {
            return false;
        }
        StanHist other = (StanHist) object;
        if ((this.sthSerial == null && other.sthSerial != null) || (this.sthSerial != null && !this.sthSerial.equals(other.sthSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.StanHist[ sthSerial=" + sthSerial + " ]";
    }
    
}
