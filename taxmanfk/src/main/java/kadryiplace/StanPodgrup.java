/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "stan_podgrup", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StanPodgrup.findAll", query = "SELECT s FROM StanPodgrup s"),
    @NamedQuery(name = "StanPodgrup.findByStpSerial", query = "SELECT s FROM StanPodgrup s WHERE s.stpSerial = :stpSerial"),
    @NamedQuery(name = "StanPodgrup.findByStpKod", query = "SELECT s FROM StanPodgrup s WHERE s.stpKod = :stpKod"),
    @NamedQuery(name = "StanPodgrup.findByStpNazwa", query = "SELECT s FROM StanPodgrup s WHERE s.stpNazwa = :stpNazwa")})
public class StanPodgrup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "stp_serial", nullable = false)
    private Integer stpSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stp_kod", nullable = false)
    private short stpKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "stp_nazwa", nullable = false, length = 64)
    private String stpNazwa;
    @JoinColumn(name = "stp_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma stpFirSerial;
    @JoinColumn(name = "stp_stg_serial", referencedColumnName = "stg_serial")
    @ManyToOne
    private StanGrup stpStgSerial;
    @OneToMany(mappedBy = "staStpSerial")
    private List<Stanowisko> stanowiskoList;

    public StanPodgrup() {
    }

    public StanPodgrup(Integer stpSerial) {
        this.stpSerial = stpSerial;
    }

    public StanPodgrup(Integer stpSerial, short stpKod, String stpNazwa) {
        this.stpSerial = stpSerial;
        this.stpKod = stpKod;
        this.stpNazwa = stpNazwa;
    }

    public Integer getStpSerial() {
        return stpSerial;
    }

    public void setStpSerial(Integer stpSerial) {
        this.stpSerial = stpSerial;
    }

    public short getStpKod() {
        return stpKod;
    }

    public void setStpKod(short stpKod) {
        this.stpKod = stpKod;
    }

    public String getStpNazwa() {
        return stpNazwa;
    }

    public void setStpNazwa(String stpNazwa) {
        this.stpNazwa = stpNazwa;
    }

    public Firma getStpFirSerial() {
        return stpFirSerial;
    }

    public void setStpFirSerial(Firma stpFirSerial) {
        this.stpFirSerial = stpFirSerial;
    }

    public StanGrup getStpStgSerial() {
        return stpStgSerial;
    }

    public void setStpStgSerial(StanGrup stpStgSerial) {
        this.stpStgSerial = stpStgSerial;
    }

    @XmlTransient
    public List<Stanowisko> getStanowiskoList() {
        return stanowiskoList;
    }

    public void setStanowiskoList(List<Stanowisko> stanowiskoList) {
        this.stanowiskoList = stanowiskoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stpSerial != null ? stpSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StanPodgrup)) {
            return false;
        }
        StanPodgrup other = (StanPodgrup) object;
        if ((this.stpSerial == null && other.stpSerial != null) || (this.stpSerial != null && !this.stpSerial.equals(other.stpSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.StanPodgrup[ stpSerial=" + stpSerial + " ]";
    }
    
}
