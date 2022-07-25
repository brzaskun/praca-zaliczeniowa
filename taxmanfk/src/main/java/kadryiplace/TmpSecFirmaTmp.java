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
@Table(name = "tmp_sec_firma_tmp", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpSecFirmaTmp.findAll", query = "SELECT t FROM TmpSecFirmaTmp t"),
    @NamedQuery(name = "TmpSecFirmaTmp.findByTstSerial", query = "SELECT t FROM TmpSecFirmaTmp t WHERE t.tstSerial = :tstSerial"),
    @NamedQuery(name = "TmpSecFirmaTmp.findByTstUserid", query = "SELECT t FROM TmpSecFirmaTmp t WHERE t.tstUserid = :tstUserid"),
    @NamedQuery(name = "TmpSecFirmaTmp.findByTstFirSerial", query = "SELECT t FROM TmpSecFirmaTmp t WHERE t.tstFirSerial = :tstFirSerial")})
public class TmpSecFirmaTmp implements Serializable {

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
    @Column(name = "tst_fir_serial", nullable = false)
    private int tstFirSerial;

    public TmpSecFirmaTmp() {
    }

    public TmpSecFirmaTmp(Integer tstSerial) {
        this.tstSerial = tstSerial;
    }

    public TmpSecFirmaTmp(Integer tstSerial, String tstUserid, int tstFirSerial) {
        this.tstSerial = tstSerial;
        this.tstUserid = tstUserid;
        this.tstFirSerial = tstFirSerial;
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

    public int getTstFirSerial() {
        return tstFirSerial;
    }

    public void setTstFirSerial(int tstFirSerial) {
        this.tstFirSerial = tstFirSerial;
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
        if (!(object instanceof TmpSecFirmaTmp)) {
            return false;
        }
        TmpSecFirmaTmp other = (TmpSecFirmaTmp) object;
        if ((this.tstSerial == null && other.tstSerial != null) || (this.tstSerial != null && !this.tstSerial.equals(other.tstSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.TmpSecFirmaTmp[ tstSerial=" + tstSerial + " ]";
    }
    
}
