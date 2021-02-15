/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "rapo_sec", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RapoSec.findAll", query = "SELECT r FROM RapoSec r"),
    @NamedQuery(name = "RapoSec.findByRpsSerial", query = "SELECT r FROM RapoSec r WHERE r.rpsSerial = :rpsSerial"),
    @NamedQuery(name = "RapoSec.findByRpsInt1", query = "SELECT r FROM RapoSec r WHERE r.rpsInt1 = :rpsInt1"),
    @NamedQuery(name = "RapoSec.findByRpsInt2", query = "SELECT r FROM RapoSec r WHERE r.rpsInt2 = :rpsInt2"),
    @NamedQuery(name = "RapoSec.findByRpsChar1", query = "SELECT r FROM RapoSec r WHERE r.rpsChar1 = :rpsChar1"),
    @NamedQuery(name = "RapoSec.findByRpsChar2", query = "SELECT r FROM RapoSec r WHERE r.rpsChar2 = :rpsChar2"),
    @NamedQuery(name = "RapoSec.findByRpsChar3", query = "SELECT r FROM RapoSec r WHERE r.rpsChar3 = :rpsChar3"),
    @NamedQuery(name = "RapoSec.findByRpsChar4", query = "SELECT r FROM RapoSec r WHERE r.rpsChar4 = :rpsChar4"),
    @NamedQuery(name = "RapoSec.findByRpsVchar1", query = "SELECT r FROM RapoSec r WHERE r.rpsVchar1 = :rpsVchar1"),
    @NamedQuery(name = "RapoSec.findByRpsVchar2", query = "SELECT r FROM RapoSec r WHERE r.rpsVchar2 = :rpsVchar2"),
    @NamedQuery(name = "RapoSec.findByRpsTyp", query = "SELECT r FROM RapoSec r WHERE r.rpsTyp = :rpsTyp"),
    @NamedQuery(name = "RapoSec.findByRpsUtwoUser", query = "SELECT r FROM RapoSec r WHERE r.rpsUtwoUser = :rpsUtwoUser"),
    @NamedQuery(name = "RapoSec.findByRpsUtwoData", query = "SELECT r FROM RapoSec r WHERE r.rpsUtwoData = :rpsUtwoData"),
    @NamedQuery(name = "RapoSec.findByRpsModyUser", query = "SELECT r FROM RapoSec r WHERE r.rpsModyUser = :rpsModyUser"),
    @NamedQuery(name = "RapoSec.findByRpsModyData", query = "SELECT r FROM RapoSec r WHERE r.rpsModyData = :rpsModyData")})
public class RapoSec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rps_serial", nullable = false)
    private Integer rpsSerial;
    @Column(name = "rps_int_1")
    private Integer rpsInt1;
    @Column(name = "rps_int_2")
    private Integer rpsInt2;
    @Column(name = "rps_char_1")
    private Character rpsChar1;
    @Column(name = "rps_char_2")
    private Character rpsChar2;
    @Column(name = "rps_char_3")
    private Character rpsChar3;
    @Column(name = "rps_char_4")
    private Character rpsChar4;
    @Size(max = 254)
    @Column(name = "rps_vchar_1", length = 254)
    private String rpsVchar1;
    @Size(max = 254)
    @Column(name = "rps_vchar_2", length = 254)
    private String rpsVchar2;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "rps_lvchar_1", length = 2147483647)
    private String rpsLvchar1;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "rps_lvchar_2", length = 2147483647)
    private String rpsLvchar2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rps_typ", nullable = false)
    private Character rpsTyp;
    @Size(max = 32)
    @Column(name = "rps_utwo_user", length = 32)
    private String rpsUtwoUser;
    @Column(name = "rps_utwo_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rpsUtwoData;
    @Size(max = 32)
    @Column(name = "rps_mody_user", length = 32)
    private String rpsModyUser;
    @Column(name = "rps_mody_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rpsModyData;
    @JoinColumn(name = "rps_rpt_serial", referencedColumnName = "rpt_serial", nullable = false)
    @ManyToOne(optional = false)
    private RapoDat rpsRptSerial;
    @JoinColumn(name = "rps_grp_serial", referencedColumnName = "grp_serial", nullable = false)
    @ManyToOne(optional = false)
    private SecGroup rpsGrpSerial;

    public RapoSec() {
    }

    public RapoSec(Integer rpsSerial) {
        this.rpsSerial = rpsSerial;
    }

    public RapoSec(Integer rpsSerial, Character rpsTyp) {
        this.rpsSerial = rpsSerial;
        this.rpsTyp = rpsTyp;
    }

    public Integer getRpsSerial() {
        return rpsSerial;
    }

    public void setRpsSerial(Integer rpsSerial) {
        this.rpsSerial = rpsSerial;
    }

    public Integer getRpsInt1() {
        return rpsInt1;
    }

    public void setRpsInt1(Integer rpsInt1) {
        this.rpsInt1 = rpsInt1;
    }

    public Integer getRpsInt2() {
        return rpsInt2;
    }

    public void setRpsInt2(Integer rpsInt2) {
        this.rpsInt2 = rpsInt2;
    }

    public Character getRpsChar1() {
        return rpsChar1;
    }

    public void setRpsChar1(Character rpsChar1) {
        this.rpsChar1 = rpsChar1;
    }

    public Character getRpsChar2() {
        return rpsChar2;
    }

    public void setRpsChar2(Character rpsChar2) {
        this.rpsChar2 = rpsChar2;
    }

    public Character getRpsChar3() {
        return rpsChar3;
    }

    public void setRpsChar3(Character rpsChar3) {
        this.rpsChar3 = rpsChar3;
    }

    public Character getRpsChar4() {
        return rpsChar4;
    }

    public void setRpsChar4(Character rpsChar4) {
        this.rpsChar4 = rpsChar4;
    }

    public String getRpsVchar1() {
        return rpsVchar1;
    }

    public void setRpsVchar1(String rpsVchar1) {
        this.rpsVchar1 = rpsVchar1;
    }

    public String getRpsVchar2() {
        return rpsVchar2;
    }

    public void setRpsVchar2(String rpsVchar2) {
        this.rpsVchar2 = rpsVchar2;
    }

    public String getRpsLvchar1() {
        return rpsLvchar1;
    }

    public void setRpsLvchar1(String rpsLvchar1) {
        this.rpsLvchar1 = rpsLvchar1;
    }

    public String getRpsLvchar2() {
        return rpsLvchar2;
    }

    public void setRpsLvchar2(String rpsLvchar2) {
        this.rpsLvchar2 = rpsLvchar2;
    }

    public Character getRpsTyp() {
        return rpsTyp;
    }

    public void setRpsTyp(Character rpsTyp) {
        this.rpsTyp = rpsTyp;
    }

    public String getRpsUtwoUser() {
        return rpsUtwoUser;
    }

    public void setRpsUtwoUser(String rpsUtwoUser) {
        this.rpsUtwoUser = rpsUtwoUser;
    }

    public Date getRpsUtwoData() {
        return rpsUtwoData;
    }

    public void setRpsUtwoData(Date rpsUtwoData) {
        this.rpsUtwoData = rpsUtwoData;
    }

    public String getRpsModyUser() {
        return rpsModyUser;
    }

    public void setRpsModyUser(String rpsModyUser) {
        this.rpsModyUser = rpsModyUser;
    }

    public Date getRpsModyData() {
        return rpsModyData;
    }

    public void setRpsModyData(Date rpsModyData) {
        this.rpsModyData = rpsModyData;
    }

    public RapoDat getRpsRptSerial() {
        return rpsRptSerial;
    }

    public void setRpsRptSerial(RapoDat rpsRptSerial) {
        this.rpsRptSerial = rpsRptSerial;
    }

    public SecGroup getRpsGrpSerial() {
        return rpsGrpSerial;
    }

    public void setRpsGrpSerial(SecGroup rpsGrpSerial) {
        this.rpsGrpSerial = rpsGrpSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rpsSerial != null ? rpsSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RapoSec)) {
            return false;
        }
        RapoSec other = (RapoSec) object;
        if ((this.rpsSerial == null && other.rpsSerial != null) || (this.rpsSerial != null && !this.rpsSerial.equals(other.rpsSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.RapoSec[ rpsSerial=" + rpsSerial + " ]";
    }
    
}
