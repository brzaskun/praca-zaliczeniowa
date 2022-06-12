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
@Table(name = "kongrupa", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kongrupa.findAll", query = "SELECT k FROM Kongrupa k"),
    @NamedQuery(name = "Kongrupa.findByKgrSerial", query = "SELECT k FROM Kongrupa k WHERE k.kgrSerial = :kgrSerial"),
    @NamedQuery(name = "Kongrupa.findByKgrNazwa", query = "SELECT k FROM Kongrupa k WHERE k.kgrNazwa = :kgrNazwa"),
    @NamedQuery(name = "Kongrupa.findByKgrPorzadek", query = "SELECT k FROM Kongrupa k WHERE k.kgrPorzadek = :kgrPorzadek"),
    @NamedQuery(name = "Kongrupa.findByKgrKtpSerial", query = "SELECT k FROM Kongrupa k WHERE k.kgrKtpSerial = :kgrKtpSerial")})
public class Kongrupa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "kgr_serial", nullable = false)
    private Integer kgrSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "kgr_nazwa", nullable = false, length = 64)
    private String kgrNazwa;
    @Column(name = "kgr_porzadek")
    private Short kgrPorzadek;
    @Column(name = "kgr_ktp_serial")
    private Integer kgrKtpSerial;
    @OneToMany(mappedBy = "konKgrSerial")
    private List<Kontrahent> kontrahentList;
    @JoinColumn(name = "kgr_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma kgrFirSerial;

    public Kongrupa() {
    }

    public Kongrupa(Integer kgrSerial) {
        this.kgrSerial = kgrSerial;
    }

    public Kongrupa(Integer kgrSerial, String kgrNazwa) {
        this.kgrSerial = kgrSerial;
        this.kgrNazwa = kgrNazwa;
    }

    public Integer getKgrSerial() {
        return kgrSerial;
    }

    public void setKgrSerial(Integer kgrSerial) {
        this.kgrSerial = kgrSerial;
    }

    public String getKgrNazwa() {
        return kgrNazwa;
    }

    public void setKgrNazwa(String kgrNazwa) {
        this.kgrNazwa = kgrNazwa;
    }

    public Short getKgrPorzadek() {
        return kgrPorzadek;
    }

    public void setKgrPorzadek(Short kgrPorzadek) {
        this.kgrPorzadek = kgrPorzadek;
    }

    public Integer getKgrKtpSerial() {
        return kgrKtpSerial;
    }

    public void setKgrKtpSerial(Integer kgrKtpSerial) {
        this.kgrKtpSerial = kgrKtpSerial;
    }

    @XmlTransient
    public List<Kontrahent> getKontrahentList() {
        return kontrahentList;
    }

    public void setKontrahentList(List<Kontrahent> kontrahentList) {
        this.kontrahentList = kontrahentList;
    }

    public Firma getKgrFirSerial() {
        return kgrFirSerial;
    }

    public void setKgrFirSerial(Firma kgrFirSerial) {
        this.kgrFirSerial = kgrFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kgrSerial != null ? kgrSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kongrupa)) {
            return false;
        }
        Kongrupa other = (Kongrupa) object;
        if ((this.kgrSerial == null && other.kgrSerial != null) || (this.kgrSerial != null && !this.kgrSerial.equals(other.kgrSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Kongrupa[ kgrSerial=" + kgrSerial + " ]";
    }
    
}
