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
@Table(name = "zatrud", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zatrud.findAll", query = "SELECT z FROM Zatrud z"),
    @NamedQuery(name = "Zatrud.findByZatSerial", query = "SELECT z FROM Zatrud z WHERE z.zatSerial = :zatSerial"),
    @NamedQuery(name = "Zatrud.findByZatNazwa", query = "SELECT z FROM Zatrud z WHERE z.zatNazwa = :zatNazwa"),
    @NamedQuery(name = "Zatrud.findByZatTyp", query = "SELECT z FROM Zatrud z WHERE z.zatTyp = :zatTyp")})
public class Zatrud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "zat_serial", nullable = false)
    private Integer zatSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "zat_nazwa", nullable = false, length = 64)
    private String zatNazwa;
    @Column(name = "zat_typ")
    private Character zatTyp;

    public Zatrud() {
    }

    public Zatrud(Integer zatSerial) {
        this.zatSerial = zatSerial;
    }

    public Zatrud(Integer zatSerial, String zatNazwa) {
        this.zatSerial = zatSerial;
        this.zatNazwa = zatNazwa;
    }

    public Integer getZatSerial() {
        return zatSerial;
    }

    public void setZatSerial(Integer zatSerial) {
        this.zatSerial = zatSerial;
    }

    public String getZatNazwa() {
        return zatNazwa;
    }

    public void setZatNazwa(String zatNazwa) {
        this.zatNazwa = zatNazwa;
    }

    public Character getZatTyp() {
        return zatTyp;
    }

    public void setZatTyp(Character zatTyp) {
        this.zatTyp = zatTyp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zatSerial != null ? zatSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zatrud)) {
            return false;
        }
        Zatrud other = (Zatrud) object;
        if ((this.zatSerial == null && other.zatSerial != null) || (this.zatSerial != null && !this.zatSerial.equals(other.zatSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Zatrud[ zatSerial=" + zatSerial + " ]";
    }
    
}
