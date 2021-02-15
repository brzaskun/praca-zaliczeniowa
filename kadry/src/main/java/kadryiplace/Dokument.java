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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "dokument", catalog = "kadryiplace", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"dok_typ"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokument.findAll", query = "SELECT d FROM Dokument d"),
    @NamedQuery(name = "Dokument.findByDokSerial", query = "SELECT d FROM Dokument d WHERE d.dokSerial = :dokSerial"),
    @NamedQuery(name = "Dokument.findByDokOpis", query = "SELECT d FROM Dokument d WHERE d.dokOpis = :dokOpis"),
    @NamedQuery(name = "Dokument.findByDokKolejnosc", query = "SELECT d FROM Dokument d WHERE d.dokKolejnosc = :dokKolejnosc"),
    @NamedQuery(name = "Dokument.findByDokTyp", query = "SELECT d FROM Dokument d WHERE d.dokTyp = :dokTyp"),
    @NamedQuery(name = "Dokument.findByDokTypWspNum", query = "SELECT d FROM Dokument d WHERE d.dokTypWspNum = :dokTypWspNum")})
public class Dokument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dok_serial", nullable = false)
    private int dokSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "dok_opis", nullable = false, length = 64)
    private String dokOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dok_kolejnosc", nullable = false)
    private short dokKolejnosc;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "dok_typ", nullable = false, length = 1)
    private String dokTyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dok_typ_wsp_num", nullable = false)
    private Character dokTypWspNum;
    @JoinColumn(name = "dok_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma dokFirSerial;

    public Dokument() {
    }

    public Dokument(String dokTyp) {
        this.dokTyp = dokTyp;
    }

    public Dokument(String dokTyp, int dokSerial, String dokOpis, short dokKolejnosc, Character dokTypWspNum) {
        this.dokTyp = dokTyp;
        this.dokSerial = dokSerial;
        this.dokOpis = dokOpis;
        this.dokKolejnosc = dokKolejnosc;
        this.dokTypWspNum = dokTypWspNum;
    }

    public int getDokSerial() {
        return dokSerial;
    }

    public void setDokSerial(int dokSerial) {
        this.dokSerial = dokSerial;
    }

    public String getDokOpis() {
        return dokOpis;
    }

    public void setDokOpis(String dokOpis) {
        this.dokOpis = dokOpis;
    }

    public short getDokKolejnosc() {
        return dokKolejnosc;
    }

    public void setDokKolejnosc(short dokKolejnosc) {
        this.dokKolejnosc = dokKolejnosc;
    }

    public String getDokTyp() {
        return dokTyp;
    }

    public void setDokTyp(String dokTyp) {
        this.dokTyp = dokTyp;
    }

    public Character getDokTypWspNum() {
        return dokTypWspNum;
    }

    public void setDokTypWspNum(Character dokTypWspNum) {
        this.dokTypWspNum = dokTypWspNum;
    }

    public Firma getDokFirSerial() {
        return dokFirSerial;
    }

    public void setDokFirSerial(Firma dokFirSerial) {
        this.dokFirSerial = dokFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dokTyp != null ? dokTyp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dokument)) {
            return false;
        }
        Dokument other = (Dokument) object;
        if ((this.dokTyp == null && other.dokTyp != null) || (this.dokTyp != null && !this.dokTyp.equals(other.dokTyp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Dokument[ dokTyp=" + dokTyp + " ]";
    }
    
}
