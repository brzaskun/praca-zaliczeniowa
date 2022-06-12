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
@Table(name = "kedu", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kedu.findAll", query = "SELECT k FROM Kedu k"),
    @NamedQuery(name = "Kedu.findByKedSerial", query = "SELECT k FROM Kedu k WHERE k.kedSerial = :kedSerial"),
    @NamedQuery(name = "Kedu.findByKedKolejnosc", query = "SELECT k FROM Kedu k WHERE k.kedKolejnosc = :kedKolejnosc"),
    @NamedQuery(name = "Kedu.findByKedOpis", query = "SELECT k FROM Kedu k WHERE k.kedOpis = :kedOpis"),
    @NamedQuery(name = "Kedu.findByKedTypSegm", query = "SELECT k FROM Kedu k WHERE k.kedTypSegm = :kedTypSegm")})
public class Kedu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ked_serial", nullable = false)
    private Integer kedSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ked_kolejnosc", nullable = false)
    private int kedKolejnosc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "ked_opis", nullable = false, length = 254)
    private String kedOpis;
    @Size(max = 10)
    @Column(name = "ked_typ_segm", length = 10)
    private String kedTypSegm;

    public Kedu() {
    }

    public Kedu(Integer kedSerial) {
        this.kedSerial = kedSerial;
    }

    public Kedu(Integer kedSerial, int kedKolejnosc, String kedOpis) {
        this.kedSerial = kedSerial;
        this.kedKolejnosc = kedKolejnosc;
        this.kedOpis = kedOpis;
    }

    public Integer getKedSerial() {
        return kedSerial;
    }

    public void setKedSerial(Integer kedSerial) {
        this.kedSerial = kedSerial;
    }

    public int getKedKolejnosc() {
        return kedKolejnosc;
    }

    public void setKedKolejnosc(int kedKolejnosc) {
        this.kedKolejnosc = kedKolejnosc;
    }

    public String getKedOpis() {
        return kedOpis;
    }

    public void setKedOpis(String kedOpis) {
        this.kedOpis = kedOpis;
    }

    public String getKedTypSegm() {
        return kedTypSegm;
    }

    public void setKedTypSegm(String kedTypSegm) {
        this.kedTypSegm = kedTypSegm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kedSerial != null ? kedSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kedu)) {
            return false;
        }
        Kedu other = (Kedu) object;
        if ((this.kedSerial == null && other.kedSerial != null) || (this.kedSerial != null && !this.kedSerial.equals(other.kedSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Kedu[ kedSerial=" + kedSerial + " ]";
    }
    
}
