/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "stanowisko", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stanowisko.findAll", query = "SELECT s FROM Stanowisko s"),
    @NamedQuery(name = "Stanowisko.findByStaSerial", query = "SELECT s FROM Stanowisko s WHERE s.staSerial = :staSerial"),
    @NamedQuery(name = "Stanowisko.findByStaNazwa", query = "SELECT s FROM Stanowisko s WHERE s.staNazwa = :staNazwa"),
    @NamedQuery(name = "Stanowisko.findByStaKolejnosc", query = "SELECT s FROM Stanowisko s WHERE s.staKolejnosc = :staKolejnosc")})
public class Stanowisko implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sta_serial", nullable = false)
    private Integer staSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "sta_nazwa", nullable = false, length = 32)
    private String staNazwa;
    @Column(name = "sta_kolejnosc")
    private Short staKolejnosc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sthStaSerial")
    private List<StanHist> stanHistList;
    @JoinColumn(name = "sta_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma staFirSerial;
    @JoinColumn(name = "sta_stp_serial", referencedColumnName = "stp_serial")
    @ManyToOne
    private StanPodgrup staStpSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tmpStaSerial")
    private List<TmpStanowisko> tmpStanowiskoList;

    public Stanowisko() {
    }

    public Stanowisko(Integer staSerial) {
        this.staSerial = staSerial;
    }

    public Stanowisko(Integer staSerial, String staNazwa) {
        this.staSerial = staSerial;
        this.staNazwa = staNazwa;
    }

    public Integer getStaSerial() {
        return staSerial;
    }

    public void setStaSerial(Integer staSerial) {
        this.staSerial = staSerial;
    }

    public String getStaNazwa() {
        return staNazwa;
    }

    public void setStaNazwa(String staNazwa) {
        this.staNazwa = staNazwa;
    }

    public Short getStaKolejnosc() {
        return staKolejnosc;
    }

    public void setStaKolejnosc(Short staKolejnosc) {
        this.staKolejnosc = staKolejnosc;
    }

    @XmlTransient
    public List<StanHist> getStanHistList() {
        return stanHistList;
    }

    public void setStanHistList(List<StanHist> stanHistList) {
        this.stanHistList = stanHistList;
    }

    public Firma getStaFirSerial() {
        return staFirSerial;
    }

    public void setStaFirSerial(Firma staFirSerial) {
        this.staFirSerial = staFirSerial;
    }

    public StanPodgrup getStaStpSerial() {
        return staStpSerial;
    }

    public void setStaStpSerial(StanPodgrup staStpSerial) {
        this.staStpSerial = staStpSerial;
    }

    @XmlTransient
    public List<TmpStanowisko> getTmpStanowiskoList() {
        return tmpStanowiskoList;
    }

    public void setTmpStanowiskoList(List<TmpStanowisko> tmpStanowiskoList) {
        this.tmpStanowiskoList = tmpStanowiskoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (staSerial != null ? staSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stanowisko)) {
            return false;
        }
        Stanowisko other = (Stanowisko) object;
        if ((this.staSerial == null && other.staSerial != null) || (this.staSerial != null && !this.staSerial.equals(other.staSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Stanowisko[ staSerial=" + staSerial + " ]";
    }
    
}
