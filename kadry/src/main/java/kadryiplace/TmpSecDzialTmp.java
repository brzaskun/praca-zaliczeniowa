/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "tmp_sec_dzial_tmp", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpSecDzialTmp.findAll", query = "SELECT t FROM TmpSecDzialTmp t"),
    @NamedQuery(name = "TmpSecDzialTmp.findByTstSerial", query = "SELECT t FROM TmpSecDzialTmp t WHERE t.tstSerial = :tstSerial"),
    @NamedQuery(name = "TmpSecDzialTmp.findByTstUserid", query = "SELECT t FROM TmpSecDzialTmp t WHERE t.tstUserid = :tstUserid"),
    @NamedQuery(name = "TmpSecDzialTmp.findByTstDepSerial", query = "SELECT t FROM TmpSecDzialTmp t WHERE t.tstDepSerial = :tstDepSerial")})
public class TmpSecDzialTmp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tst_serial", nullable = false)
    private Integer tstSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "tst_userid", nullable = false, length = 32)
    private String tstUserid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tst_dep_serial", nullable = false)
    private int tstDepSerial;

    public TmpSecDzialTmp() {
    }

    public TmpSecDzialTmp(Integer tstSerial) {
        this.tstSerial = tstSerial;
    }

    public TmpSecDzialTmp(Integer tstSerial, String tstUserid, int tstDepSerial) {
        this.tstSerial = tstSerial;
        this.tstUserid = tstUserid;
        this.tstDepSerial = tstDepSerial;
    }

    public Integer getTstSerial() {
        return tstSerial;
    }

    public void setTstSerial(Integer tstSerial) {
        this.tstSerial = tstSerial;
    }

    public String getTstUserid() {
        return tstUserid;
    }

    public void setTstUserid(String tstUserid) {
        this.tstUserid = tstUserid;
    }

    public int getTstDepSerial() {
        return tstDepSerial;
    }

    public void setTstDepSerial(int tstDepSerial) {
        this.tstDepSerial = tstDepSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tstSerial != null ? tstSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmpSecDzialTmp)) {
            return false;
        }
        TmpSecDzialTmp other = (TmpSecDzialTmp) object;
        if ((this.tstSerial == null && other.tstSerial != null) || (this.tstSerial != null && !this.tstSerial.equals(other.tstSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.TmpSecDzialTmp[ tstSerial=" + tstSerial + " ]";
    }
    
}
