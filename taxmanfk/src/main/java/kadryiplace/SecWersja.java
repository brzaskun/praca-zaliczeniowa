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
@Table(name = "sec_wersja", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecWersja.findAll", query = "SELECT s FROM SecWersja s"),
    @NamedQuery(name = "SecWersja.findByWerSerial", query = "SELECT s FROM SecWersja s WHERE s.werSerial = :werSerial"),
    @NamedQuery(name = "SecWersja.findByWerOpis", query = "SELECT s FROM SecWersja s WHERE s.werOpis = :werOpis")})
public class SecWersja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wer_serial", nullable = false)
    private Integer werSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "wer_opis", nullable = false, length = 16)
    private String werOpis;

    public SecWersja() {
    }

    public SecWersja(Integer werSerial) {
        this.werSerial = werSerial;
    }

    public SecWersja(Integer werSerial, String werOpis) {
        this.werSerial = werSerial;
        this.werOpis = werOpis;
    }

    public Integer getWerSerial() {
        return werSerial;
    }

    public void setWerSerial(Integer werSerial) {
        this.werSerial = werSerial;
    }

    public String getWerOpis() {
        return werOpis;
    }

    public void setWerOpis(String werOpis) {
        this.werOpis = werOpis;
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
        if (!(object instanceof SecWersja)) {
            return false;
        }
        SecWersja other = (SecWersja) object;
        if ((this.werSerial == null && other.werSerial != null) || (this.werSerial != null && !this.werSerial.equals(other.werSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SecWersja[ werSerial=" + werSerial + " ]";
    }
    
}
