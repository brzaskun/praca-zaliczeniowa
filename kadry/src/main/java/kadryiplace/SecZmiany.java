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
@Table(name = "sec_zmiany", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecZmiany.findAll", query = "SELECT s FROM SecZmiany s"),
    @NamedQuery(name = "SecZmiany.findBySzmSerial", query = "SELECT s FROM SecZmiany s WHERE s.szmSerial = :szmSerial"),
    @NamedQuery(name = "SecZmiany.findBySzmWersja", query = "SELECT s FROM SecZmiany s WHERE s.szmWersja = :szmWersja"),
    @NamedQuery(name = "SecZmiany.findBySzmOpis", query = "SELECT s FROM SecZmiany s WHERE s.szmOpis = :szmOpis"),
    @NamedQuery(name = "SecZmiany.findBySzmKolejnosc", query = "SELECT s FROM SecZmiany s WHERE s.szmKolejnosc = :szmKolejnosc")})
public class SecZmiany implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "szm_serial", nullable = false)
    private Integer szmSerial;
    @Size(max = 32)
    @Column(name = "szm_wersja", length = 32)
    private String szmWersja;
    @Size(max = 254)
    @Column(name = "szm_opis", length = 254)
    private String szmOpis;
    @Column(name = "szm_kolejnosc")
    private Integer szmKolejnosc;

    public SecZmiany() {
    }

    public SecZmiany(Integer szmSerial) {
        this.szmSerial = szmSerial;
    }

    public Integer getSzmSerial() {
        return szmSerial;
    }

    public void setSzmSerial(Integer szmSerial) {
        this.szmSerial = szmSerial;
    }

    public String getSzmWersja() {
        return szmWersja;
    }

    public void setSzmWersja(String szmWersja) {
        this.szmWersja = szmWersja;
    }

    public String getSzmOpis() {
        return szmOpis;
    }

    public void setSzmOpis(String szmOpis) {
        this.szmOpis = szmOpis;
    }

    public Integer getSzmKolejnosc() {
        return szmKolejnosc;
    }

    public void setSzmKolejnosc(Integer szmKolejnosc) {
        this.szmKolejnosc = szmKolejnosc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (szmSerial != null ? szmSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecZmiany)) {
            return false;
        }
        SecZmiany other = (SecZmiany) object;
        if ((this.szmSerial == null && other.szmSerial != null) || (this.szmSerial != null && !this.szmSerial.equals(other.szmSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SecZmiany[ szmSerial=" + szmSerial + " ]";
    }
    
}
