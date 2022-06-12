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
@Table(name = "etatb_hist", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EtatbHist.findAll", query = "SELECT e FROM EtatbHist e"),
    @NamedQuery(name = "EtatbHist.findByEbhSerial", query = "SELECT e FROM EtatbHist e WHERE e.ebhSerial = :ebhSerial"),
    @NamedQuery(name = "EtatbHist.findByEbhDataOd", query = "SELECT e FROM EtatbHist e WHERE e.ebhDataOd = :ebhDataOd"),
    @NamedQuery(name = "EtatbHist.findByEbhDataDo", query = "SELECT e FROM EtatbHist e WHERE e.ebhDataDo = :ebhDataDo"),
    @NamedQuery(name = "EtatbHist.findByEbhStatus", query = "SELECT e FROM EtatbHist e WHERE e.ebhStatus = :ebhStatus"),
    @NamedQuery(name = "EtatbHist.findByEbhTyp", query = "SELECT e FROM EtatbHist e WHERE e.ebhTyp = :ebhTyp")})
public class EtatbHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ebh_serial", nullable = false)
    private Integer ebhSerial;
    @Column(name = "ebh_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ebhDataOd;
    @Column(name = "ebh_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ebhDataDo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ebh_status", nullable = false)
    private Character ebhStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ebh_typ", nullable = false)
    private Character ebhTyp;
    @JoinColumn(name = "ebh_eta_serial", referencedColumnName = "eta_serial")
    @ManyToOne
    private Etat ebhEtaSerial;
    @JoinColumn(name = "ebh_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba ebhOsoSerial;

    public EtatbHist() {
    }

    public EtatbHist(Integer ebhSerial) {
        this.ebhSerial = ebhSerial;
    }

    public EtatbHist(Integer ebhSerial, Character ebhStatus, Character ebhTyp) {
        this.ebhSerial = ebhSerial;
        this.ebhStatus = ebhStatus;
        this.ebhTyp = ebhTyp;
    }

    public Integer getEbhSerial() {
        return ebhSerial;
    }

    public void setEbhSerial(Integer ebhSerial) {
        this.ebhSerial = ebhSerial;
    }

    public Date getEbhDataOd() {
        return ebhDataOd;
    }

    public void setEbhDataOd(Date ebhDataOd) {
        this.ebhDataOd = ebhDataOd;
    }

    public Date getEbhDataDo() {
        return ebhDataDo;
    }

    public void setEbhDataDo(Date ebhDataDo) {
        this.ebhDataDo = ebhDataDo;
    }

    public Character getEbhStatus() {
        return ebhStatus;
    }

    public void setEbhStatus(Character ebhStatus) {
        this.ebhStatus = ebhStatus;
    }

    public Character getEbhTyp() {
        return ebhTyp;
    }

    public void setEbhTyp(Character ebhTyp) {
        this.ebhTyp = ebhTyp;
    }

    public Etat getEbhEtaSerial() {
        return ebhEtaSerial;
    }

    public void setEbhEtaSerial(Etat ebhEtaSerial) {
        this.ebhEtaSerial = ebhEtaSerial;
    }

    public Osoba getEbhOsoSerial() {
        return ebhOsoSerial;
    }

    public void setEbhOsoSerial(Osoba ebhOsoSerial) {
        this.ebhOsoSerial = ebhOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ebhSerial != null ? ebhSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtatbHist)) {
            return false;
        }
        EtatbHist other = (EtatbHist) object;
        if ((this.ebhSerial == null && other.ebhSerial != null) || (this.ebhSerial != null && !this.ebhSerial.equals(other.ebhSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.EtatbHist[ ebhSerial=" + ebhSerial + " ]";
    }
    
}
