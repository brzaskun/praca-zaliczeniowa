/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "etata_hist", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EtataHist.findAll", query = "SELECT e FROM EtataHist e"),
    @NamedQuery(name = "EtataHist.findByEahSerial", query = "SELECT e FROM EtataHist e WHERE e.eahSerial = :eahSerial"),
    @NamedQuery(name = "EtataHist.findByEahDataOd", query = "SELECT e FROM EtataHist e WHERE e.eahDataOd = :eahDataOd"),
    @NamedQuery(name = "EtataHist.findByEahDataDo", query = "SELECT e FROM EtataHist e WHERE e.eahDataDo = :eahDataDo"),
    @NamedQuery(name = "EtataHist.findByEahStatus", query = "SELECT e FROM EtataHist e WHERE e.eahStatus = :eahStatus"),
    @NamedQuery(name = "EtataHist.findByEahTyp", query = "SELECT e FROM EtataHist e WHERE e.eahTyp = :eahTyp")})
public class EtataHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "eah_serial", nullable = false)
    private Integer eahSerial;
    @Column(name = "eah_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eahDataOd;
    @Column(name = "eah_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eahDataDo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eah_status", nullable = false)
    private Character eahStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eah_typ", nullable = false)
    private Character eahTyp;
    @JoinColumn(name = "eah_eta_serial", referencedColumnName = "eta_serial")
    @ManyToOne
    private Etat eahEtaSerial;
    @JoinColumn(name = "eah_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba eahOsoSerial;

    public EtataHist() {
    }

    public EtataHist(Integer eahSerial) {
        this.eahSerial = eahSerial;
    }

    public EtataHist(Integer eahSerial, Character eahStatus, Character eahTyp) {
        this.eahSerial = eahSerial;
        this.eahStatus = eahStatus;
        this.eahTyp = eahTyp;
    }

    public Integer getEahSerial() {
        return eahSerial;
    }

    public void setEahSerial(Integer eahSerial) {
        this.eahSerial = eahSerial;
    }

    public Date getEahDataOd() {
        return eahDataOd;
    }

    public void setEahDataOd(Date eahDataOd) {
        this.eahDataOd = eahDataOd;
    }

    public Date getEahDataDo() {
        return eahDataDo;
    }

    public void setEahDataDo(Date eahDataDo) {
        this.eahDataDo = eahDataDo;
    }

    public Character getEahStatus() {
        return eahStatus;
    }

    public void setEahStatus(Character eahStatus) {
        this.eahStatus = eahStatus;
    }

    public Character getEahTyp() {
        return eahTyp;
    }

    public void setEahTyp(Character eahTyp) {
        this.eahTyp = eahTyp;
    }

    public Etat getEahEtaSerial() {
        return eahEtaSerial;
    }

    public void setEahEtaSerial(Etat eahEtaSerial) {
        this.eahEtaSerial = eahEtaSerial;
    }

    public Osoba getEahOsoSerial() {
        return eahOsoSerial;
    }

    public void setEahOsoSerial(Osoba eahOsoSerial) {
        this.eahOsoSerial = eahOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eahSerial != null ? eahSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtataHist)) {
            return false;
        }
        EtataHist other = (EtataHist) object;
        if ((this.eahSerial == null && other.eahSerial != null) || (this.eahSerial != null && !this.eahSerial.equals(other.eahSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.EtataHist[ eahSerial=" + eahSerial + " ]";
    }
    
}
