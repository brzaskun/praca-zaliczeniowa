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
@Table(name = "tytul_wks", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TytulWks.findAll", query = "SELECT t FROM TytulWks t"),
    @NamedQuery(name = "TytulWks.findByTksSerial", query = "SELECT t FROM TytulWks t WHERE t.tksSerial = :tksSerial")})
public class TytulWks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tks_serial", nullable = false)
    private Integer tksSerial;
    @JoinColumn(name = "tks_tyt_serial", referencedColumnName = "tyt_serial", nullable = false)
    @ManyToOne(optional = false)
    private Tytul tksTytSerial;
    @JoinColumn(name = "tks_wks_serial", referencedColumnName = "wks_serial", nullable = false)
    @ManyToOne(optional = false)
    private WynKodSkl tksWksSerial;

    public TytulWks() {
    }

    public TytulWks(Integer tksSerial) {
        this.tksSerial = tksSerial;
    }

    public Integer getTksSerial() {
        return tksSerial;
    }

    public void setTksSerial(Integer tksSerial) {
        this.tksSerial = tksSerial;
    }

    public Tytul getTksTytSerial() {
        return tksTytSerial;
    }

    public void setTksTytSerial(Tytul tksTytSerial) {
        this.tksTytSerial = tksTytSerial;
    }

    public WynKodSkl getTksWksSerial() {
        return tksWksSerial;
    }

    public void setTksWksSerial(WynKodSkl tksWksSerial) {
        this.tksWksSerial = tksWksSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tksSerial != null ? tksSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TytulWks)) {
            return false;
        }
        TytulWks other = (TytulWks) object;
        if ((this.tksSerial == null && other.tksSerial != null) || (this.tksSerial != null && !this.tksSerial.equals(other.tksSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.TytulWks[ tksSerial=" + tksSerial + " ]";
    }
    
}
