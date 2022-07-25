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
@Table(name = "etat", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etat.findAll", query = "SELECT e FROM Etat e"),
    @NamedQuery(name = "Etat.findByEtaSerial", query = "SELECT e FROM Etat e WHERE e.etaSerial = :etaSerial"),
    @NamedQuery(name = "Etat.findByEtaKod1", query = "SELECT e FROM Etat e WHERE e.etaKod1 = :etaKod1"),
    @NamedQuery(name = "Etat.findByEtaKod2", query = "SELECT e FROM Etat e WHERE e.etaKod2 = :etaKod2"),
    @NamedQuery(name = "Etat.findByEtaNazwa", query = "SELECT e FROM Etat e WHERE e.etaNazwa = :etaNazwa"),
    @NamedQuery(name = "Etat.findByEtaTyp", query = "SELECT e FROM Etat e WHERE e.etaTyp = :etaTyp")})
public class Etat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "eta_serial", nullable = false)
    private Integer etaSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eta_kod_1", nullable = false)
    private Character etaKod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eta_kod_2", nullable = false)
    private Character etaKod2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "eta_nazwa", nullable = false, length = 64)
    private String etaNazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eta_typ", nullable = false)
    private Character etaTyp;
    @OneToMany(mappedBy = "eahEtaSerial")
    private List<EtataHist> etataHistList;
    @OneToMany(mappedBy = "ebhEtaSerial")
    private List<EtatbHist> etatbHistList;

    public Etat() {
    }

    public Etat(Integer etaSerial) {
        this.etaSerial = etaSerial;
    }

    public Etat(Integer etaSerial, Character etaKod1, Character etaKod2, String etaNazwa, Character etaTyp) {
        this.etaSerial = etaSerial;
        this.etaKod1 = etaKod1;
        this.etaKod2 = etaKod2;
        this.etaNazwa = etaNazwa;
        this.etaTyp = etaTyp;
    }

    public Integer getEtaSerial() {
        return etaSerial;
    }

    public void setEtaSerial(Integer etaSerial) {
        this.etaSerial = etaSerial;
    }

    public Character getEtaKod1() {
        return etaKod1;
    }

    public void setEtaKod1(Character etaKod1) {
        this.etaKod1 = etaKod1;
    }

    public Character getEtaKod2() {
        return etaKod2;
    }

    public void setEtaKod2(Character etaKod2) {
        this.etaKod2 = etaKod2;
    }

    public String getEtaNazwa() {
        return etaNazwa;
    }

    public void setEtaNazwa(String etaNazwa) {
        this.etaNazwa = etaNazwa;
    }

    public Character getEtaTyp() {
        return etaTyp;
    }

    public void setEtaTyp(Character etaTyp) {
        this.etaTyp = etaTyp;
    }

    @XmlTransient
    public List<EtataHist> getEtataHistList() {
        return etataHistList;
    }

    public void setEtataHistList(List<EtataHist> etataHistList) {
        this.etataHistList = etataHistList;
    }

    @XmlTransient
    public List<EtatbHist> getEtatbHistList() {
        return etatbHistList;
    }

    public void setEtatbHistList(List<EtatbHist> etatbHistList) {
        this.etatbHistList = etatbHistList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (etaSerial != null ? etaSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etat)) {
            return false;
        }
        Etat other = (Etat) object;
        if ((this.etaSerial == null && other.etaSerial != null) || (this.etaSerial != null && !this.etaSerial.equals(other.etaSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Etat[ etaSerial=" + etaSerial + " ]";
    }
    
}
