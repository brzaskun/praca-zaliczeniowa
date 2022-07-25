/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kurs_grup", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KursGrup.findAll", query = "SELECT k FROM KursGrup k"),
    @NamedQuery(name = "KursGrup.findByKugSerial", query = "SELECT k FROM KursGrup k WHERE k.kugSerial = :kugSerial"),
    @NamedQuery(name = "KursGrup.findByKugNazwa", query = "SELECT k FROM KursGrup k WHERE k.kugNazwa = :kugNazwa"),
    @NamedQuery(name = "KursGrup.findByKugKod", query = "SELECT k FROM KursGrup k WHERE k.kugKod = :kugKod"),
    @NamedQuery(name = "KursGrup.findByKugTyp", query = "SELECT k FROM KursGrup k WHERE k.kugTyp = :kugTyp")})
public class KursGrup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "kug_serial", nullable = false)
    private Integer kugSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "kug_nazwa", nullable = false, length = 64)
    private String kugNazwa;
    @Size(max = 2)
    @Column(name = "kug_kod", length = 2)
    private String kugKod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kug_typ", nullable = false)
    private Character kugTyp;
    @OneToMany(mappedBy = "kurKugSerial")
    private List<Kurs> kursList;

    public KursGrup() {
    }

    public KursGrup(Integer kugSerial) {
        this.kugSerial = kugSerial;
    }

    public KursGrup(Integer kugSerial, String kugNazwa, Character kugTyp) {
        this.kugSerial = kugSerial;
        this.kugNazwa = kugNazwa;
        this.kugTyp = kugTyp;
    }

    public Integer getKugSerial() {
        return kugSerial;
    }

    public void setKugSerial(Integer kugSerial) {
        this.kugSerial = kugSerial;
    }

    public String getKugNazwa() {
        return kugNazwa;
    }

    public void setKugNazwa(String kugNazwa) {
        this.kugNazwa = kugNazwa;
    }

    public String getKugKod() {
        return kugKod;
    }

    public void setKugKod(String kugKod) {
        this.kugKod = kugKod;
    }

    public Character getKugTyp() {
        return kugTyp;
    }

    public void setKugTyp(Character kugTyp) {
        this.kugTyp = kugTyp;
    }

    @XmlTransient
    public List<Kurs> getKursList() {
        return kursList;
    }

    public void setKursList(List<Kurs> kursList) {
        this.kursList = kursList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kugSerial != null ? kugSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KursGrup)) {
            return false;
        }
        KursGrup other = (KursGrup) object;
        if ((this.kugSerial == null && other.kugSerial != null) || (this.kugSerial != null && !this.kugSerial.equals(other.kugSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.KursGrup[ kugSerial=" + kugSerial + " ]";
    }
    
}
