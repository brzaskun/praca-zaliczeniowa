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
@Table(name = "tmp_sec_pion_tmp", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpSecPionTmp.findAll", query = "SELECT t FROM TmpSecPionTmp t"),
    @NamedQuery(name = "TmpSecPionTmp.findByTstSerial", query = "SELECT t FROM TmpSecPionTmp t WHERE t.tstSerial = :tstSerial"),
    @NamedQuery(name = "TmpSecPionTmp.findByTstUserid", query = "SELECT t FROM TmpSecPionTmp t WHERE t.tstUserid = :tstUserid"),
    @NamedQuery(name = "TmpSecPionTmp.findByTstPioSerial", query = "SELECT t FROM TmpSecPionTmp t WHERE t.tstPioSerial = :tstPioSerial")})
public class TmpSecPionTmp implements Serializable {

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
    @Column(name = "tst_pio_serial", nullable = false)
    private int tstPioSerial;

    public TmpSecPionTmp() {
    }

    public TmpSecPionTmp(Integer tstSerial) {
        this.tstSerial = tstSerial;
    }

    public TmpSecPionTmp(Integer tstSerial, String tstUserid, int tstPioSerial) {
        this.tstSerial = tstSerial;
        this.tstUserid = tstUserid;
        this.tstPioSerial = tstPioSerial;
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

    public int getTstPioSerial() {
        return tstPioSerial;
    }

    public void setTstPioSerial(int tstPioSerial) {
        this.tstPioSerial = tstPioSerial;
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
        if (!(object instanceof TmpSecPionTmp)) {
            return false;
        }
        TmpSecPionTmp other = (TmpSecPionTmp) object;
        if ((this.tstSerial == null && other.tstSerial != null) || (this.tstSerial != null && !this.tstSerial.equals(other.tstSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.TmpSecPionTmp[ tstSerial=" + tstSerial + " ]";
    }
    
}
