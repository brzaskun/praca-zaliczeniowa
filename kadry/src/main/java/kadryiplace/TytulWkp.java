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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "tytul_wkp", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TytulWkp.findAll", query = "SELECT t FROM TytulWkp t"),
    @NamedQuery(name = "TytulWkp.findByTkpSerial", query = "SELECT t FROM TytulWkp t WHERE t.tkpSerial = :tkpSerial")})
public class TytulWkp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tkp_serial", nullable = false)
    private Integer tkpSerial;
    @JoinColumn(name = "tkp_tyt_serial", referencedColumnName = "tyt_serial", nullable = false)
    @ManyToOne(optional = false)
    private Tytul tkpTytSerial;
    @JoinColumn(name = "tkp_wkp_serial", referencedColumnName = "wkp_serial", nullable = false)
    @ManyToOne(optional = false)
    private WynKodPrz tkpWkpSerial;

    public TytulWkp() {
    }

    public TytulWkp(Integer tkpSerial) {
        this.tkpSerial = tkpSerial;
    }

    public Integer getTkpSerial() {
        return tkpSerial;
    }

    public void setTkpSerial(Integer tkpSerial) {
        this.tkpSerial = tkpSerial;
    }

    public Tytul getTkpTytSerial() {
        return tkpTytSerial;
    }

    public void setTkpTytSerial(Tytul tkpTytSerial) {
        this.tkpTytSerial = tkpTytSerial;
    }

    public WynKodPrz getTkpWkpSerial() {
        return tkpWkpSerial;
    }

    public void setTkpWkpSerial(WynKodPrz tkpWkpSerial) {
        this.tkpWkpSerial = tkpWkpSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tkpSerial != null ? tkpSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TytulWkp)) {
            return false;
        }
        TytulWkp other = (TytulWkp) object;
        if ((this.tkpSerial == null && other.tkpSerial != null) || (this.tkpSerial != null && !this.tkpSerial.equals(other.tkpSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.TytulWkp[ tkpSerial=" + tkpSerial + " ]";
    }
    
}
