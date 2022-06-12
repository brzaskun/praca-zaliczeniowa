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
@Table(name = "cele", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cele.findAll", query = "SELECT c FROM Cele c"),
    @NamedQuery(name = "Cele.findByCelSerial", query = "SELECT c FROM Cele c WHERE c.celSerial = :celSerial"),
    @NamedQuery(name = "Cele.findByCelOpis", query = "SELECT c FROM Cele c WHERE c.celOpis = :celOpis"),
    @NamedQuery(name = "Cele.findByCelKolejnosc", query = "SELECT c FROM Cele c WHERE c.celKolejnosc = :celKolejnosc")})
public class Cele implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cel_serial", nullable = false)
    private Integer celSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "cel_opis", nullable = false, length = 64)
    private String celOpis;
    @Column(name = "cel_kolejnosc")
    private Short celKolejnosc;
    @JoinColumn(name = "cel_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma celFirSerial;

    public Cele() {
    }

    public Cele(Integer celSerial) {
        this.celSerial = celSerial;
    }

    public Cele(Integer celSerial, String celOpis) {
        this.celSerial = celSerial;
        this.celOpis = celOpis;
    }

    public Integer getCelSerial() {
        return celSerial;
    }

    public void setCelSerial(Integer celSerial) {
        this.celSerial = celSerial;
    }

    public String getCelOpis() {
        return celOpis;
    }

    public void setCelOpis(String celOpis) {
        this.celOpis = celOpis;
    }

    public Short getCelKolejnosc() {
        return celKolejnosc;
    }

    public void setCelKolejnosc(Short celKolejnosc) {
        this.celKolejnosc = celKolejnosc;
    }

    public Firma getCelFirSerial() {
        return celFirSerial;
    }

    public void setCelFirSerial(Firma celFirSerial) {
        this.celFirSerial = celFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (celSerial != null ? celSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cele)) {
            return false;
        }
        Cele other = (Cele) object;
        if ((this.celSerial == null && other.celSerial != null) || (this.celSerial != null && !this.celSerial.equals(other.celSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Cele[ celSerial=" + celSerial + " ]";
    }
    
}
