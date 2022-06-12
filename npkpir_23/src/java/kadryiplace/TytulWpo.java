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
@Table(name = "tytul_wpo", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TytulWpo.findAll", query = "SELECT t FROM TytulWpo t"),
    @NamedQuery(name = "TytulWpo.findByTpoSerial", query = "SELECT t FROM TytulWpo t WHERE t.tpoSerial = :tpoSerial")})
public class TytulWpo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tpo_serial", nullable = false)
    private Integer tpoSerial;
    @JoinColumn(name = "tpo_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma tpoFirSerial;
    @JoinColumn(name = "tpo_tyt_serial", referencedColumnName = "tyt_serial", nullable = false)
    @ManyToOne(optional = false)
    private Tytul tpoTytSerial;
    @JoinColumn(name = "tpo_wpo_serial", referencedColumnName = "wpo_serial", nullable = false)
    @ManyToOne(optional = false)
    private WynPotracenia tpoWpoSerial;

    public TytulWpo() {
    }

    public TytulWpo(Integer tpoSerial) {
        this.tpoSerial = tpoSerial;
    }

    public Integer getTpoSerial() {
        return tpoSerial;
    }

    public void setTpoSerial(Integer tpoSerial) {
        this.tpoSerial = tpoSerial;
    }

    public Firma getTpoFirSerial() {
        return tpoFirSerial;
    }

    public void setTpoFirSerial(Firma tpoFirSerial) {
        this.tpoFirSerial = tpoFirSerial;
    }

    public Tytul getTpoTytSerial() {
        return tpoTytSerial;
    }

    public void setTpoTytSerial(Tytul tpoTytSerial) {
        this.tpoTytSerial = tpoTytSerial;
    }

    public WynPotracenia getTpoWpoSerial() {
        return tpoWpoSerial;
    }

    public void setTpoWpoSerial(WynPotracenia tpoWpoSerial) {
        this.tpoWpoSerial = tpoWpoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpoSerial != null ? tpoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TytulWpo)) {
            return false;
        }
        TytulWpo other = (TytulWpo) object;
        if ((this.tpoSerial == null && other.tpoSerial != null) || (this.tpoSerial != null && !this.tpoSerial.equals(other.tpoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.TytulWpo[ tpoSerial=" + tpoSerial + " ]";
    }
    
}
