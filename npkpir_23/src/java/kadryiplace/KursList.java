/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kurs_list", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KursList.findAll", query = "SELECT k FROM KursList k"),
    @NamedQuery(name = "KursList.findByKulSerial", query = "SELECT k FROM KursList k WHERE k.kulSerial = :kulSerial"),
    @NamedQuery(name = "KursList.findByKulDofin", query = "SELECT k FROM KursList k WHERE k.kulDofin = :kulDofin"),
    @NamedQuery(name = "KursList.findByKulDataUkoncz", query = "SELECT k FROM KursList k WHERE k.kulDataUkoncz = :kulDataUkoncz"),
    @NamedQuery(name = "KursList.findByKulTyp", query = "SELECT k FROM KursList k WHERE k.kulTyp = :kulTyp")})
public class KursList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "kul_serial", nullable = false)
    private Integer kulSerial;
    @Column(name = "kul_dofin")
    private Character kulDofin;
    @Column(name = "kul_data_ukoncz")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kulDataUkoncz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kul_typ", nullable = false)
    private Character kulTyp;
    @JoinColumn(name = "kul_kur_serial", referencedColumnName = "kur_serial")
    @ManyToOne
    private Kurs kulKurSerial;
    @JoinColumn(name = "kul_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba kulOsoSerial;

    public KursList() {
    }

    public KursList(Integer kulSerial) {
        this.kulSerial = kulSerial;
    }

    public KursList(Integer kulSerial, Character kulTyp) {
        this.kulSerial = kulSerial;
        this.kulTyp = kulTyp;
    }

    public Integer getKulSerial() {
        return kulSerial;
    }

    public void setKulSerial(Integer kulSerial) {
        this.kulSerial = kulSerial;
    }

    public Character getKulDofin() {
        return kulDofin;
    }

    public void setKulDofin(Character kulDofin) {
        this.kulDofin = kulDofin;
    }

    public Date getKulDataUkoncz() {
        return kulDataUkoncz;
    }

    public void setKulDataUkoncz(Date kulDataUkoncz) {
        this.kulDataUkoncz = kulDataUkoncz;
    }

    public Character getKulTyp() {
        return kulTyp;
    }

    public void setKulTyp(Character kulTyp) {
        this.kulTyp = kulTyp;
    }

    public Kurs getKulKurSerial() {
        return kulKurSerial;
    }

    public void setKulKurSerial(Kurs kulKurSerial) {
        this.kulKurSerial = kulKurSerial;
    }

    public Osoba getKulOsoSerial() {
        return kulOsoSerial;
    }

    public void setKulOsoSerial(Osoba kulOsoSerial) {
        this.kulOsoSerial = kulOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kulSerial != null ? kulSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KursList)) {
            return false;
        }
        KursList other = (KursList) object;
        if ((this.kulSerial == null && other.kulSerial != null) || (this.kulSerial != null && !this.kulSerial.equals(other.kulSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.KursList[ kulSerial=" + kulSerial + " ]";
    }
    
}
