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
@Table(name = "wersja", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wersja.findAll", query = "SELECT w FROM Wersja w"),
    @NamedQuery(name = "Wersja.findByWerSerial", query = "SELECT w FROM Wersja w WHERE w.werSerial = :werSerial"),
    @NamedQuery(name = "Wersja.findByWerWersja", query = "SELECT w FROM Wersja w WHERE w.werWersja = :werWersja"),
    @NamedQuery(name = "Wersja.findByWerApp", query = "SELECT w FROM Wersja w WHERE w.werApp = :werApp")})
public class Wersja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wer_serial", nullable = false)
    private Integer werSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "wer_wersja", nullable = false, length = 32)
    private String werWersja;
    @Size(max = 32)
    @Column(name = "wer_app", length = 32)
    private String werApp;

    public Wersja() {
    }

    public Wersja(Integer werSerial) {
        this.werSerial = werSerial;
    }

    public Wersja(Integer werSerial, String werWersja) {
        this.werSerial = werSerial;
        this.werWersja = werWersja;
    }

    public Integer getWerSerial() {
        return werSerial;
    }

    public void setWerSerial(Integer werSerial) {
        this.werSerial = werSerial;
    }

    public String getWerWersja() {
        return werWersja;
    }

    public void setWerWersja(String werWersja) {
        this.werWersja = werWersja;
    }

    public String getWerApp() {
        return werApp;
    }

    public void setWerApp(String werApp) {
        this.werApp = werApp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (werSerial != null ? werSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wersja)) {
            return false;
        }
        Wersja other = (Wersja) object;
        if ((this.werSerial == null && other.werSerial != null) || (this.werSerial != null && !this.werSerial.equals(other.werSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Wersja[ werSerial=" + werSerial + " ]";
    }
    
}
