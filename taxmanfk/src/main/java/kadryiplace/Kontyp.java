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
@Table(name = "kontyp", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kontyp.findAll", query = "SELECT k FROM Kontyp k"),
    @NamedQuery(name = "Kontyp.findByKtpSerial", query = "SELECT k FROM Kontyp k WHERE k.ktpSerial = :ktpSerial"),
    @NamedQuery(name = "Kontyp.findByKtpOpis", query = "SELECT k FROM Kontyp k WHERE k.ktpOpis = :ktpOpis"),
    @NamedQuery(name = "Kontyp.findByKtpKolejnosc", query = "SELECT k FROM Kontyp k WHERE k.ktpKolejnosc = :ktpKolejnosc")})
public class Kontyp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ktp_serial", nullable = false)
    private Integer ktpSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "ktp_opis", nullable = false, length = 48)
    private String ktpOpis;
    @Column(name = "ktp_kolejnosc")
    private Short ktpKolejnosc;

    public Kontyp() {
    }

    public Kontyp(Integer ktpSerial) {
        this.ktpSerial = ktpSerial;
    }

    public Kontyp(Integer ktpSerial, String ktpOpis) {
        this.ktpSerial = ktpSerial;
        this.ktpOpis = ktpOpis;
    }

    public Integer getKtpSerial() {
        return ktpSerial;
    }

    public void setKtpSerial(Integer ktpSerial) {
        this.ktpSerial = ktpSerial;
    }

    public String getKtpOpis() {
        return ktpOpis;
    }

    public void setKtpOpis(String ktpOpis) {
        this.ktpOpis = ktpOpis;
    }

    public Short getKtpKolejnosc() {
        return ktpKolejnosc;
    }

    public void setKtpKolejnosc(Short ktpKolejnosc) {
        this.ktpKolejnosc = ktpKolejnosc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ktpSerial != null ? ktpSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kontyp)) {
            return false;
        }
        Kontyp other = (Kontyp) object;
        if ((this.ktpSerial == null && other.ktpSerial != null) || (this.ktpSerial != null && !this.ktpSerial.equals(other.ktpSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Kontyp[ ktpSerial=" + ktpSerial + " ]";
    }
    
}
