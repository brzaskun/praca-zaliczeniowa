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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "kurs", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kurs.findAll", query = "SELECT k FROM Kurs k"),
    @NamedQuery(name = "Kurs.findByKurSerial", query = "SELECT k FROM Kurs k WHERE k.kurSerial = :kurSerial"),
    @NamedQuery(name = "Kurs.findByKurNazwa", query = "SELECT k FROM Kurs k WHERE k.kurNazwa = :kurNazwa"),
    @NamedQuery(name = "Kurs.findByKurTyp", query = "SELECT k FROM Kurs k WHERE k.kurTyp = :kurTyp")})
public class Kurs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "kur_serial", nullable = false)
    private Integer kurSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 96)
    @Column(name = "kur_nazwa", nullable = false, length = 96)
    private String kurNazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kur_typ", nullable = false)
    private Character kurTyp;
    @JoinColumn(name = "kur_kug_serial", referencedColumnName = "kug_serial")
    @ManyToOne
    private KursGrup kurKugSerial;
    @OneToMany(mappedBy = "kulKurSerial")
    private List<KursList> kursListList;

    public Kurs() {
    }

    public Kurs(Integer kurSerial) {
        this.kurSerial = kurSerial;
    }

    public Kurs(Integer kurSerial, String kurNazwa, Character kurTyp) {
        this.kurSerial = kurSerial;
        this.kurNazwa = kurNazwa;
        this.kurTyp = kurTyp;
    }

    public Integer getKurSerial() {
        return kurSerial;
    }

    public void setKurSerial(Integer kurSerial) {
        this.kurSerial = kurSerial;
    }

    public String getKurNazwa() {
        return kurNazwa;
    }

    public void setKurNazwa(String kurNazwa) {
        this.kurNazwa = kurNazwa;
    }

    public Character getKurTyp() {
        return kurTyp;
    }

    public void setKurTyp(Character kurTyp) {
        this.kurTyp = kurTyp;
    }

    public KursGrup getKurKugSerial() {
        return kurKugSerial;
    }

    public void setKurKugSerial(KursGrup kurKugSerial) {
        this.kurKugSerial = kurKugSerial;
    }

    @XmlTransient
    public List<KursList> getKursListList() {
        return kursListList;
    }

    public void setKursListList(List<KursList> kursListList) {
        this.kursListList = kursListList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kurSerial != null ? kurSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kurs)) {
            return false;
        }
        Kurs other = (Kurs) object;
        if ((this.kurSerial == null && other.kurSerial != null) || (this.kurSerial != null && !this.kurSerial.equals(other.kurSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Kurs[ kurSerial=" + kurSerial + " ]";
    }
    
}
