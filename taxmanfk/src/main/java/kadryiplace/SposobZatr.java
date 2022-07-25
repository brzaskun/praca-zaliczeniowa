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
@Table(name = "sposob_zatr", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SposobZatr.findAll", query = "SELECT s FROM SposobZatr s"),
    @NamedQuery(name = "SposobZatr.findBySzaSerial", query = "SELECT s FROM SposobZatr s WHERE s.szaSerial = :szaSerial"),
    @NamedQuery(name = "SposobZatr.findBySzaOpis", query = "SELECT s FROM SposobZatr s WHERE s.szaOpis = :szaOpis")})
public class SposobZatr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sza_serial", nullable = false)
    private Integer szaSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "sza_opis", nullable = false, length = 32)
    private String szaOpis;

    public SposobZatr() {
    }

    public SposobZatr(Integer szaSerial) {
        this.szaSerial = szaSerial;
    }

    public SposobZatr(Integer szaSerial, String szaOpis) {
        this.szaSerial = szaSerial;
        this.szaOpis = szaOpis;
    }

    public Integer getSzaSerial() {
        return szaSerial;
    }

    public void setSzaSerial(Integer szaSerial) {
        this.szaSerial = szaSerial;
    }

    public String getSzaOpis() {
        return szaOpis;
    }

    public void setSzaOpis(String szaOpis) {
        this.szaOpis = szaOpis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (szaSerial != null ? szaSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SposobZatr)) {
            return false;
        }
        SposobZatr other = (SposobZatr) object;
        if ((this.szaSerial == null && other.szaSerial != null) || (this.szaSerial != null && !this.szaSerial.equals(other.szaSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SposobZatr[ szaSerial=" + szaSerial + " ]";
    }
    
}
