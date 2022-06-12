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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "sc_promo", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScPromo.findAll", query = "SELECT s FROM ScPromo s"),
    @NamedQuery(name = "ScPromo.findByScpSerial", query = "SELECT s FROM ScPromo s WHERE s.scpSerial = :scpSerial"),
    @NamedQuery(name = "ScPromo.findByScpTyp", query = "SELECT s FROM ScPromo s WHERE s.scpTyp = :scpTyp"),
    @NamedQuery(name = "ScPromo.findByScpRodzaj", query = "SELECT s FROM ScPromo s WHERE s.scpRodzaj = :scpRodzaj"),
    @NamedQuery(name = "ScPromo.findByScpOpis1", query = "SELECT s FROM ScPromo s WHERE s.scpOpis1 = :scpOpis1"),
    @NamedQuery(name = "ScPromo.findByScpOpis2", query = "SELECT s FROM ScPromo s WHERE s.scpOpis2 = :scpOpis2"),
    @NamedQuery(name = "ScPromo.findByScpOpis3", query = "SELECT s FROM ScPromo s WHERE s.scpOpis3 = :scpOpis3"),
    @NamedQuery(name = "ScPromo.findByScpOpis4", query = "SELECT s FROM ScPromo s WHERE s.scpOpis4 = :scpOpis4"),
    @NamedQuery(name = "ScPromo.findByScpDataOd", query = "SELECT s FROM ScPromo s WHERE s.scpDataOd = :scpDataOd"),
    @NamedQuery(name = "ScPromo.findByScpDataDo", query = "SELECT s FROM ScPromo s WHERE s.scpDataDo = :scpDataDo"),
    @NamedQuery(name = "ScPromo.findByScpChar1", query = "SELECT s FROM ScPromo s WHERE s.scpChar1 = :scpChar1"),
    @NamedQuery(name = "ScPromo.findByScpChar2", query = "SELECT s FROM ScPromo s WHERE s.scpChar2 = :scpChar2"),
    @NamedQuery(name = "ScPromo.findByScpVchar1", query = "SELECT s FROM ScPromo s WHERE s.scpVchar1 = :scpVchar1"),
    @NamedQuery(name = "ScPromo.findByScpVchar2", query = "SELECT s FROM ScPromo s WHERE s.scpVchar2 = :scpVchar2"),
    @NamedQuery(name = "ScPromo.findByScpNum1", query = "SELECT s FROM ScPromo s WHERE s.scpNum1 = :scpNum1"),
    @NamedQuery(name = "ScPromo.findByScpNum2", query = "SELECT s FROM ScPromo s WHERE s.scpNum2 = :scpNum2"),
    @NamedQuery(name = "ScPromo.findByScpNum3", query = "SELECT s FROM ScPromo s WHERE s.scpNum3 = :scpNum3"),
    @NamedQuery(name = "ScPromo.findByScpNum4", query = "SELECT s FROM ScPromo s WHERE s.scpNum4 = :scpNum4"),
    @NamedQuery(name = "ScPromo.findByScpInt1", query = "SELECT s FROM ScPromo s WHERE s.scpInt1 = :scpInt1"),
    @NamedQuery(name = "ScPromo.findByScpInt2", query = "SELECT s FROM ScPromo s WHERE s.scpInt2 = :scpInt2"),
    @NamedQuery(name = "ScPromo.findByScpInt3", query = "SELECT s FROM ScPromo s WHERE s.scpInt3 = :scpInt3"),
    @NamedQuery(name = "ScPromo.findByScpInt4", query = "SELECT s FROM ScPromo s WHERE s.scpInt4 = :scpInt4"),
    @NamedQuery(name = "ScPromo.findByScpData1", query = "SELECT s FROM ScPromo s WHERE s.scpData1 = :scpData1"),
    @NamedQuery(name = "ScPromo.findByScpData2", query = "SELECT s FROM ScPromo s WHERE s.scpData2 = :scpData2")})
public class ScPromo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "scp_serial", nullable = false)
    private Integer scpSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scp_typ", nullable = false)
    private Character scpTyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scp_rodzaj", nullable = false)
    private Character scpRodzaj;
    @Size(max = 128)
    @Column(name = "scp_opis_1", length = 128)
    private String scpOpis1;
    @Size(max = 128)
    @Column(name = "scp_opis_2", length = 128)
    private String scpOpis2;
    @Size(max = 128)
    @Column(name = "scp_opis_3", length = 128)
    private String scpOpis3;
    @Size(max = 128)
    @Column(name = "scp_opis_4", length = 128)
    private String scpOpis4;
    @Column(name = "scp_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scpDataOd;
    @Column(name = "scp_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scpDataDo;
    @Column(name = "scp_char_1")
    private Character scpChar1;
    @Column(name = "scp_char_2")
    private Character scpChar2;
    @Size(max = 32)
    @Column(name = "scp_vchar_1", length = 32)
    private String scpVchar1;
    @Size(max = 32)
    @Column(name = "scp_vchar_2", length = 32)
    private String scpVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "scp_num_1", precision = 17, scale = 6)
    private BigDecimal scpNum1;
    @Column(name = "scp_num_2", precision = 17, scale = 6)
    private BigDecimal scpNum2;
    @Column(name = "scp_num_3", precision = 17, scale = 6)
    private BigDecimal scpNum3;
    @Column(name = "scp_num_4", precision = 17, scale = 6)
    private BigDecimal scpNum4;
    @Column(name = "scp_int_1")
    private Integer scpInt1;
    @Column(name = "scp_int_2")
    private Integer scpInt2;
    @Column(name = "scp_int_3")
    private Integer scpInt3;
    @Column(name = "scp_int_4")
    private Integer scpInt4;
    @Column(name = "scp_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scpData1;
    @Column(name = "scp_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scpData2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scmScpSerial")
    private List<ScMag> scMagList;
    @OneToMany(mappedBy = "dscScpSerial")
    private List<DaneStatSc> daneStatScList;
    @JoinColumn(name = "scp_sck_serial", referencedColumnName = "sck_serial", nullable = false)
    @ManyToOne(optional = false)
    private ScKamp scpSckSerial;

    public ScPromo() {
    }

    public ScPromo(Integer scpSerial) {
        this.scpSerial = scpSerial;
    }

    public ScPromo(Integer scpSerial, Character scpTyp, Character scpRodzaj) {
        this.scpSerial = scpSerial;
        this.scpTyp = scpTyp;
        this.scpRodzaj = scpRodzaj;
    }

    public Integer getScpSerial() {
        return scpSerial;
    }

    public void setScpSerial(Integer scpSerial) {
        this.scpSerial = scpSerial;
    }

    public Character getScpTyp() {
        return scpTyp;
    }

    public void setScpTyp(Character scpTyp) {
        this.scpTyp = scpTyp;
    }

    public Character getScpRodzaj() {
        return scpRodzaj;
    }

    public void setScpRodzaj(Character scpRodzaj) {
        this.scpRodzaj = scpRodzaj;
    }

    public String getScpOpis1() {
        return scpOpis1;
    }

    public void setScpOpis1(String scpOpis1) {
        this.scpOpis1 = scpOpis1;
    }

    public String getScpOpis2() {
        return scpOpis2;
    }

    public void setScpOpis2(String scpOpis2) {
        this.scpOpis2 = scpOpis2;
    }

    public String getScpOpis3() {
        return scpOpis3;
    }

    public void setScpOpis3(String scpOpis3) {
        this.scpOpis3 = scpOpis3;
    }

    public String getScpOpis4() {
        return scpOpis4;
    }

    public void setScpOpis4(String scpOpis4) {
        this.scpOpis4 = scpOpis4;
    }

    public Date getScpDataOd() {
        return scpDataOd;
    }

    public void setScpDataOd(Date scpDataOd) {
        this.scpDataOd = scpDataOd;
    }

    public Date getScpDataDo() {
        return scpDataDo;
    }

    public void setScpDataDo(Date scpDataDo) {
        this.scpDataDo = scpDataDo;
    }

    public Character getScpChar1() {
        return scpChar1;
    }

    public void setScpChar1(Character scpChar1) {
        this.scpChar1 = scpChar1;
    }

    public Character getScpChar2() {
        return scpChar2;
    }

    public void setScpChar2(Character scpChar2) {
        this.scpChar2 = scpChar2;
    }

    public String getScpVchar1() {
        return scpVchar1;
    }

    public void setScpVchar1(String scpVchar1) {
        this.scpVchar1 = scpVchar1;
    }

    public String getScpVchar2() {
        return scpVchar2;
    }

    public void setScpVchar2(String scpVchar2) {
        this.scpVchar2 = scpVchar2;
    }

    public BigDecimal getScpNum1() {
        return scpNum1;
    }

    public void setScpNum1(BigDecimal scpNum1) {
        this.scpNum1 = scpNum1;
    }

    public BigDecimal getScpNum2() {
        return scpNum2;
    }

    public void setScpNum2(BigDecimal scpNum2) {
        this.scpNum2 = scpNum2;
    }

    public BigDecimal getScpNum3() {
        return scpNum3;
    }

    public void setScpNum3(BigDecimal scpNum3) {
        this.scpNum3 = scpNum3;
    }

    public BigDecimal getScpNum4() {
        return scpNum4;
    }

    public void setScpNum4(BigDecimal scpNum4) {
        this.scpNum4 = scpNum4;
    }

    public Integer getScpInt1() {
        return scpInt1;
    }

    public void setScpInt1(Integer scpInt1) {
        this.scpInt1 = scpInt1;
    }

    public Integer getScpInt2() {
        return scpInt2;
    }

    public void setScpInt2(Integer scpInt2) {
        this.scpInt2 = scpInt2;
    }

    public Integer getScpInt3() {
        return scpInt3;
    }

    public void setScpInt3(Integer scpInt3) {
        this.scpInt3 = scpInt3;
    }

    public Integer getScpInt4() {
        return scpInt4;
    }

    public void setScpInt4(Integer scpInt4) {
        this.scpInt4 = scpInt4;
    }

    public Date getScpData1() {
        return scpData1;
    }

    public void setScpData1(Date scpData1) {
        this.scpData1 = scpData1;
    }

    public Date getScpData2() {
        return scpData2;
    }

    public void setScpData2(Date scpData2) {
        this.scpData2 = scpData2;
    }

    @XmlTransient
    public List<ScMag> getScMagList() {
        return scMagList;
    }

    public void setScMagList(List<ScMag> scMagList) {
        this.scMagList = scMagList;
    }

    @XmlTransient
    public List<DaneStatSc> getDaneStatScList() {
        return daneStatScList;
    }

    public void setDaneStatScList(List<DaneStatSc> daneStatScList) {
        this.daneStatScList = daneStatScList;
    }

    public ScKamp getScpSckSerial() {
        return scpSckSerial;
    }

    public void setScpSckSerial(ScKamp scpSckSerial) {
        this.scpSckSerial = scpSckSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scpSerial != null ? scpSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScPromo)) {
            return false;
        }
        ScPromo other = (ScPromo) object;
        if ((this.scpSerial == null && other.scpSerial != null) || (this.scpSerial != null && !this.scpSerial.equals(other.scpSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.ScPromo[ scpSerial=" + scpSerial + " ]";
    }
    
}
