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
@Table(name = "sec_user", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecUser.findAll", query = "SELECT s FROM SecUser s"),
    @NamedQuery(name = "SecUser.findByUsrUserid", query = "SELECT s FROM SecUser s WHERE s.usrUserid = :usrUserid"),
    @NamedQuery(name = "SecUser.findByUsrFname", query = "SELECT s FROM SecUser s WHERE s.usrFname = :usrFname"),
    @NamedQuery(name = "SecUser.findByUsrMi", query = "SELECT s FROM SecUser s WHERE s.usrMi = :usrMi"),
    @NamedQuery(name = "SecUser.findByUsrLname", query = "SELECT s FROM SecUser s WHERE s.usrLname = :usrLname"),
    @NamedQuery(name = "SecUser.findByUsrNote", query = "SELECT s FROM SecUser s WHERE s.usrNote = :usrNote"),
    @NamedQuery(name = "SecUser.findByUsrHaslo", query = "SELECT s FROM SecUser s WHERE s.usrHaslo = :usrHaslo"),
    @NamedQuery(name = "SecUser.findByUsrKasjer", query = "SELECT s FROM SecUser s WHERE s.usrKasjer = :usrKasjer"),
    @NamedQuery(name = "SecUser.findByUsrSname", query = "SELECT s FROM SecUser s WHERE s.usrSname = :usrSname"),
    @NamedQuery(name = "SecUser.findByUsrHasloTmp", query = "SELECT s FROM SecUser s WHERE s.usrHasloTmp = :usrHasloTmp"),
    @NamedQuery(name = "SecUser.findByUsrDodVchar1", query = "SELECT s FROM SecUser s WHERE s.usrDodVchar1 = :usrDodVchar1"),
    @NamedQuery(name = "SecUser.findByUsrDodNum1", query = "SELECT s FROM SecUser s WHERE s.usrDodNum1 = :usrDodNum1"),
    @NamedQuery(name = "SecUser.findByUsrDodVchar2", query = "SELECT s FROM SecUser s WHERE s.usrDodVchar2 = :usrDodVchar2"),
    @NamedQuery(name = "SecUser.findByUsrDodData1", query = "SELECT s FROM SecUser s WHERE s.usrDodData1 = :usrDodData1"),
    @NamedQuery(name = "SecUser.findByUsrDodData2", query = "SELECT s FROM SecUser s WHERE s.usrDodData2 = :usrDodData2"),
    @NamedQuery(name = "SecUser.findByUsrDodInt1", query = "SELECT s FROM SecUser s WHERE s.usrDodInt1 = :usrDodInt1")})
public class SecUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "usr_userid", nullable = false, length = 32)
    private String usrUserid;
    @Size(max = 32)
    @Column(name = "usr_fname", length = 32)
    private String usrFname;
    @Column(name = "usr_mi")
    private Character usrMi;
    @Size(max = 32)
    @Column(name = "usr_lname", length = 32)
    private String usrLname;
    @Size(max = 64)
    @Column(name = "usr_note", length = 64)
    private String usrNote;
    @Size(max = 128)
    @Column(name = "usr_haslo", length = 128)
    private String usrHaslo;
    @Size(max = 16)
    @Column(name = "usr_kasjer", length = 16)
    private String usrKasjer;
    @Size(max = 32)
    @Column(name = "usr_sname", length = 32)
    private String usrSname;
    @Size(max = 128)
    @Column(name = "usr_haslo_tmp", length = 128)
    private String usrHasloTmp;
    @Size(max = 128)
    @Column(name = "usr_dod_vchar_1", length = 128)
    private String usrDodVchar1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "usr_dod_num_1", precision = 17, scale = 6)
    private BigDecimal usrDodNum1;
    @Size(max = 128)
    @Column(name = "usr_dod_vchar_2", length = 128)
    private String usrDodVchar2;
    @Column(name = "usr_dod_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usrDodData1;
    @Column(name = "usr_dod_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usrDodData2;
    @Column(name = "usr_dod_int_1")
    private Integer usrDodInt1;
    @JoinColumn(name = "usr_grp_serial", referencedColumnName = "grp_serial", nullable = false)
    @ManyToOne(optional = false)
    private SecGroup usrGrpSerial;

    public SecUser() {
    }

    public SecUser(String usrUserid) {
        this.usrUserid = usrUserid;
    }

    public String getUsrUserid() {
        return usrUserid;
    }

    public void setUsrUserid(String usrUserid) {
        this.usrUserid = usrUserid;
    }

    public String getUsrFname() {
        return usrFname;
    }

    public void setUsrFname(String usrFname) {
        this.usrFname = usrFname;
    }

    public Character getUsrMi() {
        return usrMi;
    }

    public void setUsrMi(Character usrMi) {
        this.usrMi = usrMi;
    }

    public String getUsrLname() {
        return usrLname;
    }

    public void setUsrLname(String usrLname) {
        this.usrLname = usrLname;
    }

    public String getUsrNote() {
        return usrNote;
    }

    public void setUsrNote(String usrNote) {
        this.usrNote = usrNote;
    }

    public String getUsrHaslo() {
        return usrHaslo;
    }

    public void setUsrHaslo(String usrHaslo) {
        this.usrHaslo = usrHaslo;
    }

    public String getUsrKasjer() {
        return usrKasjer;
    }

    public void setUsrKasjer(String usrKasjer) {
        this.usrKasjer = usrKasjer;
    }

    public String getUsrSname() {
        return usrSname;
    }

    public void setUsrSname(String usrSname) {
        this.usrSname = usrSname;
    }

    public String getUsrHasloTmp() {
        return usrHasloTmp;
    }

    public void setUsrHasloTmp(String usrHasloTmp) {
        this.usrHasloTmp = usrHasloTmp;
    }

    public String getUsrDodVchar1() {
        return usrDodVchar1;
    }

    public void setUsrDodVchar1(String usrDodVchar1) {
        this.usrDodVchar1 = usrDodVchar1;
    }

    public BigDecimal getUsrDodNum1() {
        return usrDodNum1;
    }

    public void setUsrDodNum1(BigDecimal usrDodNum1) {
        this.usrDodNum1 = usrDodNum1;
    }

    public String getUsrDodVchar2() {
        return usrDodVchar2;
    }

    public void setUsrDodVchar2(String usrDodVchar2) {
        this.usrDodVchar2 = usrDodVchar2;
    }

    public Date getUsrDodData1() {
        return usrDodData1;
    }

    public void setUsrDodData1(Date usrDodData1) {
        this.usrDodData1 = usrDodData1;
    }

    public Date getUsrDodData2() {
        return usrDodData2;
    }

    public void setUsrDodData2(Date usrDodData2) {
        this.usrDodData2 = usrDodData2;
    }

    public Integer getUsrDodInt1() {
        return usrDodInt1;
    }

    public void setUsrDodInt1(Integer usrDodInt1) {
        this.usrDodInt1 = usrDodInt1;
    }

    public SecGroup getUsrGrpSerial() {
        return usrGrpSerial;
    }

    public void setUsrGrpSerial(SecGroup usrGrpSerial) {
        this.usrGrpSerial = usrGrpSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrUserid != null ? usrUserid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecUser)) {
            return false;
        }
        SecUser other = (SecUser) object;
        if ((this.usrUserid == null && other.usrUserid != null) || (this.usrUserid != null && !this.usrUserid.equals(other.usrUserid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SecUser[ usrUserid=" + usrUserid + " ]";
    }
    
}
