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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "trasy", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trasy.findAll", query = "SELECT t FROM Trasy t"),
    @NamedQuery(name = "Trasy.findByTraSerial", query = "SELECT t FROM Trasy t WHERE t.traSerial = :traSerial"),
    @NamedQuery(name = "Trasy.findByTraOpis", query = "SELECT t FROM Trasy t WHERE t.traOpis = :traOpis"),
    @NamedQuery(name = "Trasy.findByTraKolejnosc", query = "SELECT t FROM Trasy t WHERE t.traKolejnosc = :traKolejnosc")})
public class Trasy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tra_serial", nullable = false)
    private Integer traSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "tra_opis", nullable = false, length = 64)
    private String traOpis;
    @Column(name = "tra_kolejnosc")
    private Short traKolejnosc;
    @JoinColumn(name = "tra_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma traFirSerial;

    public Trasy() {
    }

    public Trasy(Integer traSerial) {
        this.traSerial = traSerial;
    }

    public Trasy(Integer traSerial, String traOpis) {
        this.traSerial = traSerial;
        this.traOpis = traOpis;
    }

    public Integer getTraSerial() {
        return traSerial;
    }

    public void setTraSerial(Integer traSerial) {
        this.traSerial = traSerial;
    }

    public String getTraOpis() {
        return traOpis;
    }

    public void setTraOpis(String traOpis) {
        this.traOpis = traOpis;
    }

    public Short getTraKolejnosc() {
        return traKolejnosc;
    }

    public void setTraKolejnosc(Short traKolejnosc) {
        this.traKolejnosc = traKolejnosc;
    }

    public Firma getTraFirSerial() {
        return traFirSerial;
    }

    public void setTraFirSerial(Firma traFirSerial) {
        this.traFirSerial = traFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (traSerial != null ? traSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trasy)) {
            return false;
        }
        Trasy other = (Trasy) object;
        if ((this.traSerial == null && other.traSerial != null) || (this.traSerial != null && !this.traSerial.equals(other.traSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Trasy[ traSerial=" + traSerial + " ]";
    }
    
}
