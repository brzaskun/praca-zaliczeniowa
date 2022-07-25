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
@Table(name = "tmp_sec_pion", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpSecPion.findAll", query = "SELECT t FROM TmpSecPion t"),
    @NamedQuery(name = "TmpSecPion.findByTspSerial", query = "SELECT t FROM TmpSecPion t WHERE t.tspSerial = :tspSerial"),
    @NamedQuery(name = "TmpSecPion.findByTspUserid", query = "SELECT t FROM TmpSecPion t WHERE t.tspUserid = :tspUserid"),
    @NamedQuery(name = "TmpSecPion.findByTspPioSerial", query = "SELECT t FROM TmpSecPion t WHERE t.tspPioSerial = :tspPioSerial")})
public class TmpSecPion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tsp_serial", nullable = false)
    private Integer tspSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "tsp_userid", nullable = false, length = 32)
    private String tspUserid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tsp_pio_serial", nullable = false)
    private int tspPioSerial;

    public TmpSecPion() {
    }

    public TmpSecPion(Integer tspSerial) {
        this.tspSerial = tspSerial;
    }

    public TmpSecPion(Integer tspSerial, String tspUserid, int tspPioSerial) {
        this.tspSerial = tspSerial;
        this.tspUserid = tspUserid;
        this.tspPioSerial = tspPioSerial;
    }

    public Integer getTspSerial() {
        return tspSerial;
    }

    public void setTspSerial(Integer tspSerial) {
        this.tspSerial = tspSerial;
    }

    public String getTspUserid() {
        return tspUserid;
    }

    public void setTspUserid(String tspUserid) {
        this.tspUserid = tspUserid;
    }

    public int getTspPioSerial() {
        return tspPioSerial;
    }

    public void setTspPioSerial(int tspPioSerial) {
        this.tspPioSerial = tspPioSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tspSerial != null ? tspSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmpSecPion)) {
            return false;
        }
        TmpSecPion other = (TmpSecPion) object;
        if ((this.tspSerial == null && other.tspSerial != null) || (this.tspSerial != null && !this.tspSerial.equals(other.tspSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.TmpSecPion[ tspSerial=" + tspSerial + " ]";
    }
    
}
