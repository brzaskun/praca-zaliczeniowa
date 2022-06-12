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
@Table(name = "tmp_sec_firma", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpSecFirma.findAll", query = "SELECT t FROM TmpSecFirma t"),
    @NamedQuery(name = "TmpSecFirma.findByTsfSerial", query = "SELECT t FROM TmpSecFirma t WHERE t.tsfSerial = :tsfSerial"),
    @NamedQuery(name = "TmpSecFirma.findByTsfUserid", query = "SELECT t FROM TmpSecFirma t WHERE t.tsfUserid = :tsfUserid"),
    @NamedQuery(name = "TmpSecFirma.findByTsfFirSerial", query = "SELECT t FROM TmpSecFirma t WHERE t.tsfFirSerial = :tsfFirSerial")})
public class TmpSecFirma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tsf_serial", nullable = false)
    private Integer tsfSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "tsf_userid", nullable = false, length = 32)
    private String tsfUserid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tsf_fir_serial", nullable = false)
    private int tsfFirSerial;

    public TmpSecFirma() {
    }

    public TmpSecFirma(Integer tsfSerial) {
        this.tsfSerial = tsfSerial;
    }

    public TmpSecFirma(Integer tsfSerial, String tsfUserid, int tsfFirSerial) {
        this.tsfSerial = tsfSerial;
        this.tsfUserid = tsfUserid;
        this.tsfFirSerial = tsfFirSerial;
    }

    public Integer getTsfSerial() {
        return tsfSerial;
    }

    public void setTsfSerial(Integer tsfSerial) {
        this.tsfSerial = tsfSerial;
    }

    public String getTsfUserid() {
        return tsfUserid;
    }

    public void setTsfUserid(String tsfUserid) {
        this.tsfUserid = tsfUserid;
    }

    public int getTsfFirSerial() {
        return tsfFirSerial;
    }

    public void setTsfFirSerial(int tsfFirSerial) {
        this.tsfFirSerial = tsfFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tsfSerial != null ? tsfSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmpSecFirma)) {
            return false;
        }
        TmpSecFirma other = (TmpSecFirma) object;
        if ((this.tsfSerial == null && other.tsfSerial != null) || (this.tsfSerial != null && !this.tsfSerial.equals(other.tsfSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.TmpSecFirma[ tsfSerial=" + tsfSerial + " ]";
    }
    
}
