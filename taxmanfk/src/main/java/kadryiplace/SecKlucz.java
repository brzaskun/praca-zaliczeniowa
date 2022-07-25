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
@Table(name = "sec_klucz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecKlucz.findAll", query = "SELECT s FROM SecKlucz s"),
    @NamedQuery(name = "SecKlucz.findByKluSerial", query = "SELECT s FROM SecKlucz s WHERE s.kluSerial = :kluSerial"),
    @NamedQuery(name = "SecKlucz.findByKluWartosc", query = "SELECT s FROM SecKlucz s WHERE s.kluWartosc = :kluWartosc"),
    @NamedQuery(name = "SecKlucz.findByKluTyp", query = "SELECT s FROM SecKlucz s WHERE s.kluTyp = :kluTyp")})
public class SecKlucz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "klu_serial", nullable = false)
    private Integer kluSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "klu_wartosc", nullable = false, length = 254)
    private String kluWartosc;
    @Column(name = "klu_typ")
    private Character kluTyp;

    public SecKlucz() {
    }

    public SecKlucz(Integer kluSerial) {
        this.kluSerial = kluSerial;
    }

    public SecKlucz(Integer kluSerial, String kluWartosc) {
        this.kluSerial = kluSerial;
        this.kluWartosc = kluWartosc;
    }

    public Integer getKluSerial() {
        return kluSerial;
    }

    public void setKluSerial(Integer kluSerial) {
        this.kluSerial = kluSerial;
    }

    public String getKluWartosc() {
        return kluWartosc;
    }

    public void setKluWartosc(String kluWartosc) {
        this.kluWartosc = kluWartosc;
    }

    public Character getKluTyp() {
        return kluTyp;
    }

    public void setKluTyp(Character kluTyp) {
        this.kluTyp = kluTyp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kluSerial != null ? kluSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecKlucz)) {
            return false;
        }
        SecKlucz other = (SecKlucz) object;
        if ((this.kluSerial == null && other.kluSerial != null) || (this.kluSerial != null && !this.kluSerial.equals(other.kluSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SecKlucz[ kluSerial=" + kluSerial + " ]";
    }
    
}
