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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kolumna", catalog = "kadryiplace", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"kol_kol_ksiegi"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kolumna.findAll", query = "SELECT k FROM Kolumna k"),
    @NamedQuery(name = "Kolumna.findByKolSerial", query = "SELECT k FROM Kolumna k WHERE k.kolSerial = :kolSerial"),
    @NamedQuery(name = "Kolumna.findByKolOpis", query = "SELECT k FROM Kolumna k WHERE k.kolOpis = :kolOpis"),
    @NamedQuery(name = "Kolumna.findByKolKolKsiegi", query = "SELECT k FROM Kolumna k WHERE k.kolKolKsiegi = :kolKolKsiegi")})
public class Kolumna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "kol_serial", nullable = false)
    private Integer kolSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "kol_opis", nullable = false, length = 64)
    private String kolOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kol_kol_ksiegi", nullable = false)
    private short kolKolKsiegi;

    public Kolumna() {
    }

    public Kolumna(Integer kolSerial) {
        this.kolSerial = kolSerial;
    }

    public Kolumna(Integer kolSerial, String kolOpis, short kolKolKsiegi) {
        this.kolSerial = kolSerial;
        this.kolOpis = kolOpis;
        this.kolKolKsiegi = kolKolKsiegi;
    }

    public Integer getKolSerial() {
        return kolSerial;
    }

    public void setKolSerial(Integer kolSerial) {
        this.kolSerial = kolSerial;
    }

    public String getKolOpis() {
        return kolOpis;
    }

    public void setKolOpis(String kolOpis) {
        this.kolOpis = kolOpis;
    }

    public short getKolKolKsiegi() {
        return kolKolKsiegi;
    }

    public void setKolKolKsiegi(short kolKolKsiegi) {
        this.kolKolKsiegi = kolKolKsiegi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kolSerial != null ? kolSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kolumna)) {
            return false;
        }
        Kolumna other = (Kolumna) object;
        if ((this.kolSerial == null && other.kolSerial != null) || (this.kolSerial != null && !this.kolSerial.equals(other.kolSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Kolumna[ kolSerial=" + kolSerial + " ]";
    }
    
}
