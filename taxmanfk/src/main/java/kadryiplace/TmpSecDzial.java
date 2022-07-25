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
@Table(name = "tmp_sec_dzial", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpSecDzial.findAll", query = "SELECT t FROM TmpSecDzial t"),
    @NamedQuery(name = "TmpSecDzial.findByTsdSerial", query = "SELECT t FROM TmpSecDzial t WHERE t.tsdSerial = :tsdSerial"),
    @NamedQuery(name = "TmpSecDzial.findByTsdUserid", query = "SELECT t FROM TmpSecDzial t WHERE t.tsdUserid = :tsdUserid"),
    @NamedQuery(name = "TmpSecDzial.findByTsdDepSerial", query = "SELECT t FROM TmpSecDzial t WHERE t.tsdDepSerial = :tsdDepSerial")})
public class TmpSecDzial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tsd_serial", nullable = false)
    private Integer tsdSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "tsd_userid", nullable = false, length = 32)
    private String tsdUserid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tsd_dep_serial", nullable = false)
    private int tsdDepSerial;

    public TmpSecDzial() {
    }

    public TmpSecDzial(Integer tsdSerial) {
        this.tsdSerial = tsdSerial;
    }

    public TmpSecDzial(Integer tsdSerial, String tsdUserid, int tsdDepSerial) {
        this.tsdSerial = tsdSerial;
        this.tsdUserid = tsdUserid;
        this.tsdDepSerial = tsdDepSerial;
    }

    public Integer getTsdSerial() {
        return tsdSerial;
    }

    public void setTsdSerial(Integer tsdSerial) {
        this.tsdSerial = tsdSerial;
    }

    public String getTsdUserid() {
        return tsdUserid;
    }

    public void setTsdUserid(String tsdUserid) {
        this.tsdUserid = tsdUserid;
    }

    public int getTsdDepSerial() {
        return tsdDepSerial;
    }

    public void setTsdDepSerial(int tsdDepSerial) {
        this.tsdDepSerial = tsdDepSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tsdSerial != null ? tsdSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmpSecDzial)) {
            return false;
        }
        TmpSecDzial other = (TmpSecDzial) object;
        if ((this.tsdSerial == null && other.tsdSerial != null) || (this.tsdSerial != null && !this.tsdSerial.equals(other.tsdSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.TmpSecDzial[ tsdSerial=" + tsdSerial + " ]";
    }
    
}
