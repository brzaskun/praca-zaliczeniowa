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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "super_dep", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuperDep.findAll", query = "SELECT s FROM SuperDep s"),
    @NamedQuery(name = "SuperDep.findBySdpSerial", query = "SELECT s FROM SuperDep s WHERE s.sdpSerial = :sdpSerial"),
    @NamedQuery(name = "SuperDep.findBySdpNazwa", query = "SELECT s FROM SuperDep s WHERE s.sdpNazwa = :sdpNazwa"),
    @NamedQuery(name = "SuperDep.findBySdpTyp", query = "SELECT s FROM SuperDep s WHERE s.sdpTyp = :sdpTyp")})
public class SuperDep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sdp_serial", nullable = false)
    private Integer sdpSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "sdp_nazwa", nullable = false, length = 64)
    private String sdpNazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sdp_typ", nullable = false)
    private Character sdpTyp;
    @JoinColumn(name = "sdp_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma sdpFirSerial;

    public SuperDep() {
    }

    public SuperDep(Integer sdpSerial) {
        this.sdpSerial = sdpSerial;
    }

    public SuperDep(Integer sdpSerial, String sdpNazwa, Character sdpTyp) {
        this.sdpSerial = sdpSerial;
        this.sdpNazwa = sdpNazwa;
        this.sdpTyp = sdpTyp;
    }

    public Integer getSdpSerial() {
        return sdpSerial;
    }

    public void setSdpSerial(Integer sdpSerial) {
        this.sdpSerial = sdpSerial;
    }

    public String getSdpNazwa() {
        return sdpNazwa;
    }

    public void setSdpNazwa(String sdpNazwa) {
        this.sdpNazwa = sdpNazwa;
    }

    public Character getSdpTyp() {
        return sdpTyp;
    }

    public void setSdpTyp(Character sdpTyp) {
        this.sdpTyp = sdpTyp;
    }

    public Firma getSdpFirSerial() {
        return sdpFirSerial;
    }

    public void setSdpFirSerial(Firma sdpFirSerial) {
        this.sdpFirSerial = sdpFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sdpSerial != null ? sdpSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuperDep)) {
            return false;
        }
        SuperDep other = (SuperDep) object;
        if ((this.sdpSerial == null && other.sdpSerial != null) || (this.sdpSerial != null && !this.sdpSerial.equals(other.sdpSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SuperDep[ sdpSerial=" + sdpSerial + " ]";
    }
    
}
