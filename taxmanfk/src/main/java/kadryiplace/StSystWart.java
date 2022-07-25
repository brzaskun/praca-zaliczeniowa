/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "st_syst_wart", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StSystWart.findAll", query = "SELECT s FROM StSystWart s"),
    @NamedQuery(name = "StSystWart.findBySsoSerial", query = "SELECT s FROM StSystWart s WHERE s.ssoSerial = :ssoSerial"),
    @NamedQuery(name = "StSystWart.findBySsoMiesiacOd", query = "SELECT s FROM StSystWart s WHERE s.ssoMiesiacOd = :ssoMiesiacOd"),
    @NamedQuery(name = "StSystWart.findBySsoRokOd", query = "SELECT s FROM StSystWart s WHERE s.ssoRokOd = :ssoRokOd"),
    @NamedQuery(name = "StSystWart.findBySsoMiesiacDo", query = "SELECT s FROM StSystWart s WHERE s.ssoMiesiacDo = :ssoMiesiacDo"),
    @NamedQuery(name = "StSystWart.findBySsoRokDo", query = "SELECT s FROM StSystWart s WHERE s.ssoRokDo = :ssoRokDo"),
    @NamedQuery(name = "StSystWart.findBySsoNumeric", query = "SELECT s FROM StSystWart s WHERE s.ssoNumeric = :ssoNumeric"),
    @NamedQuery(name = "StSystWart.findBySsoDod1", query = "SELECT s FROM StSystWart s WHERE s.ssoDod1 = :ssoDod1"),
    @NamedQuery(name = "StSystWart.findBySsoDod2", query = "SELECT s FROM StSystWart s WHERE s.ssoDod2 = :ssoDod2"),
    @NamedQuery(name = "StSystWart.findBySsoDod3", query = "SELECT s FROM StSystWart s WHERE s.ssoDod3 = :ssoDod3"),
    @NamedQuery(name = "StSystWart.findBySsoNumeric2", query = "SELECT s FROM StSystWart s WHERE s.ssoNumeric2 = :ssoNumeric2"),
    @NamedQuery(name = "StSystWart.findBySsoStatus", query = "SELECT s FROM StSystWart s WHERE s.ssoStatus = :ssoStatus"),
    @NamedQuery(name = "StSystWart.findBySsoDataOd", query = "SELECT s FROM StSystWart s WHERE s.ssoDataOd = :ssoDataOd"),
    @NamedQuery(name = "StSystWart.findBySsoDataDo", query = "SELECT s FROM StSystWart s WHERE s.ssoDataDo = :ssoDataDo")})
public class StSystWart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sso_serial", nullable = false)
    private Integer ssoSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sso_miesiac_od", nullable = false)
    private int ssoMiesiacOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sso_rok_od", nullable = false)
    private int ssoRokOd;
    @Column(name = "sso_miesiac_do")
    private Integer ssoMiesiacDo;
    @Column(name = "sso_rok_do")
    private Integer ssoRokDo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "sso_numeric", nullable = false, precision = 17, scale = 6)
    private BigDecimal ssoNumeric;
    @Column(name = "sso_dod_1")
    private Character ssoDod1;
    @Column(name = "sso_dod_2")
    private Character ssoDod2;
    @Column(name = "sso_dod_3")
    private Character ssoDod3;
    @Column(name = "sso_numeric_2", precision = 17, scale = 6)
    private BigDecimal ssoNumeric2;
    @Column(name = "sso_status")
    private Character ssoStatus;
    @Column(name = "sso_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ssoDataOd;
    @Column(name = "sso_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ssoDataDo;
    @JoinColumn(name = "sso_okr_serial", referencedColumnName = "okr_serial")
    @ManyToOne
    private Okres ssoOkrSerial;
    @JoinColumn(name = "sso_ssd_serial", referencedColumnName = "ssd_serial", nullable = false)
    @ManyToOne(optional = false)
    private StSystOpis ssoSsdSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sswSsoSerial")
    private List<StSystWartDni> stSystWartDniList;

    public StSystWart() {
    }

    public StSystWart(Integer ssoSerial) {
        this.ssoSerial = ssoSerial;
    }

    public StSystWart(Integer ssoSerial, int ssoMiesiacOd, int ssoRokOd, BigDecimal ssoNumeric) {
        this.ssoSerial = ssoSerial;
        this.ssoMiesiacOd = ssoMiesiacOd;
        this.ssoRokOd = ssoRokOd;
        this.ssoNumeric = ssoNumeric;
    }

    public Integer getSsoSerial() {
        return ssoSerial;
    }

    public void setSsoSerial(Integer ssoSerial) {
        this.ssoSerial = ssoSerial;
    }

    public int getSsoMiesiacOd() {
        return ssoMiesiacOd;
    }

    public void setSsoMiesiacOd(int ssoMiesiacOd) {
        this.ssoMiesiacOd = ssoMiesiacOd;
    }

    public int getSsoRokOd() {
        return ssoRokOd;
    }

    public void setSsoRokOd(int ssoRokOd) {
        this.ssoRokOd = ssoRokOd;
    }

    public Integer getSsoMiesiacDo() {
        return ssoMiesiacDo;
    }

    public void setSsoMiesiacDo(Integer ssoMiesiacDo) {
        this.ssoMiesiacDo = ssoMiesiacDo;
    }

    public Integer getSsoRokDo() {
        return ssoRokDo;
    }

    public void setSsoRokDo(Integer ssoRokDo) {
        this.ssoRokDo = ssoRokDo;
    }

    public BigDecimal getSsoNumeric() {
        return ssoNumeric;
    }

    public void setSsoNumeric(BigDecimal ssoNumeric) {
        this.ssoNumeric = ssoNumeric;
    }

    public Character getSsoDod1() {
        return ssoDod1;
    }

    public void setSsoDod1(Character ssoDod1) {
        this.ssoDod1 = ssoDod1;
    }

    public Character getSsoDod2() {
        return ssoDod2;
    }

    public void setSsoDod2(Character ssoDod2) {
        this.ssoDod2 = ssoDod2;
    }

    public Character getSsoDod3() {
        return ssoDod3;
    }

    public void setSsoDod3(Character ssoDod3) {
        this.ssoDod3 = ssoDod3;
    }

    public BigDecimal getSsoNumeric2() {
        return ssoNumeric2;
    }

    public void setSsoNumeric2(BigDecimal ssoNumeric2) {
        this.ssoNumeric2 = ssoNumeric2;
    }

    public Character getSsoStatus() {
        return ssoStatus;
    }

    public void setSsoStatus(Character ssoStatus) {
        this.ssoStatus = ssoStatus;
    }

    public Date getSsoDataOd() {
        return ssoDataOd;
    }

    public void setSsoDataOd(Date ssoDataOd) {
        this.ssoDataOd = ssoDataOd;
    }

    public Date getSsoDataDo() {
        return ssoDataDo;
    }

    public void setSsoDataDo(Date ssoDataDo) {
        this.ssoDataDo = ssoDataDo;
    }

    public Okres getSsoOkrSerial() {
        return ssoOkrSerial;
    }

    public void setSsoOkrSerial(Okres ssoOkrSerial) {
        this.ssoOkrSerial = ssoOkrSerial;
    }

    public StSystOpis getSsoSsdSerial() {
        return ssoSsdSerial;
    }

    public void setSsoSsdSerial(StSystOpis ssoSsdSerial) {
        this.ssoSsdSerial = ssoSsdSerial;
    }

    @XmlTransient
    public List<StSystWartDni> getStSystWartDniList() {
        return stSystWartDniList;
    }

    public void setStSystWartDniList(List<StSystWartDni> stSystWartDniList) {
        this.stSystWartDniList = stSystWartDniList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ssoSerial != null ? ssoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StSystWart)) {
            return false;
        }
        StSystWart other = (StSystWart) object;
        if ((this.ssoSerial == null && other.ssoSerial != null) || (this.ssoSerial != null && !this.ssoSerial.equals(other.ssoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.StSystWart[ ssoSerial=" + ssoSerial + " ]";
    }
    
}
