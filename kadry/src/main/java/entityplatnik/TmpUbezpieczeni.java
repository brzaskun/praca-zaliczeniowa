/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "TMP_UBEZPIECZENI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpUbezpieczeni.findAll", query = "SELECT t FROM TmpUbezpieczeni t"),
    @NamedQuery(name = "TmpUbezpieczeni.findByIdUbZus", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.tmpUbezpieczeniPK.idUbZus = :idUbZus"),
    @NamedQuery(name = "TmpUbezpieczeni.findByIdProgram", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.tmpUbezpieczeniPK.idProgram = :idProgram"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokUbezpieczonyZus", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokUbezpieczonyZus = :krokUbezpieczonyZus"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokUbezp30krotnosc", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokUbezp30krotnosc = :krokUbezp30krotnosc"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokUbezpAdres", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokUbezpAdres = :krokUbezpAdres"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokUbezpCzlonekRodziny", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokUbezpCzlonekRodziny = :krokUbezpCzlonekRodziny"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokUbezpIdent", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokUbezpIdent = :krokUbezpIdent"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokUbezpIndschemat", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokUbezpIndschemat = :krokUbezpIndschemat"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokUbezpInne", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokUbezpInne = :krokUbezpInne"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokUbezpKodPracy", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokUbezpKodPracy = :krokUbezpKodPracy"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokUbezpPodleganie", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokUbezpPodleganie = :krokUbezpPodleganie"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokPlatnDra2rca2", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokPlatnDra2rca2 = :krokPlatnDra2rca2"),
    @NamedQuery(name = "TmpUbezpieczeni.findByKrokPlatnMdg", query = "SELECT t FROM TmpUbezpieczeni t WHERE t.krokPlatnMdg = :krokPlatnMdg")})
public class TmpUbezpieczeni implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TmpUbezpieczeniPK tmpUbezpieczeniPK;
    @Column(name = "KROK_UBEZPIECZONY_ZUS")
    private Short krokUbezpieczonyZus;
    @Column(name = "KROK_UBEZP_30KROTNOSC")
    private Short krokUbezp30krotnosc;
    @Column(name = "KROK_UBEZP_ADRES")
    private Short krokUbezpAdres;
    @Column(name = "KROK_UBEZP_CZLONEK_RODZINY")
    private Short krokUbezpCzlonekRodziny;
    @Column(name = "KROK_UBEZP_IDENT")
    private Short krokUbezpIdent;
    @Column(name = "KROK_UBEZP_INDSCHEMAT")
    private Short krokUbezpIndschemat;
    @Column(name = "KROK_UBEZP_INNE")
    private Short krokUbezpInne;
    @Column(name = "KROK_UBEZP_KOD_PRACY")
    private Short krokUbezpKodPracy;
    @Column(name = "KROK_UBEZP_PODLEGANIE")
    private Short krokUbezpPodleganie;
    @Column(name = "KROK_PLATN_DRA2RCA2")
    private Short krokPlatnDra2rca2;
    @Column(name = "KROK_PLATN_MDG")
    private Short krokPlatnMdg;

    public TmpUbezpieczeni() {
    }

    public TmpUbezpieczeni(TmpUbezpieczeniPK tmpUbezpieczeniPK) {
        this.tmpUbezpieczeniPK = tmpUbezpieczeniPK;
    }

    public TmpUbezpieczeni(int idUbZus, int idProgram) {
        this.tmpUbezpieczeniPK = new TmpUbezpieczeniPK(idUbZus, idProgram);
    }

    public TmpUbezpieczeniPK getTmpUbezpieczeniPK() {
        return tmpUbezpieczeniPK;
    }

    public void setTmpUbezpieczeniPK(TmpUbezpieczeniPK tmpUbezpieczeniPK) {
        this.tmpUbezpieczeniPK = tmpUbezpieczeniPK;
    }

    public Short getKrokUbezpieczonyZus() {
        return krokUbezpieczonyZus;
    }

    public void setKrokUbezpieczonyZus(Short krokUbezpieczonyZus) {
        this.krokUbezpieczonyZus = krokUbezpieczonyZus;
    }

    public Short getKrokUbezp30krotnosc() {
        return krokUbezp30krotnosc;
    }

    public void setKrokUbezp30krotnosc(Short krokUbezp30krotnosc) {
        this.krokUbezp30krotnosc = krokUbezp30krotnosc;
    }

    public Short getKrokUbezpAdres() {
        return krokUbezpAdres;
    }

    public void setKrokUbezpAdres(Short krokUbezpAdres) {
        this.krokUbezpAdres = krokUbezpAdres;
    }

    public Short getKrokUbezpCzlonekRodziny() {
        return krokUbezpCzlonekRodziny;
    }

    public void setKrokUbezpCzlonekRodziny(Short krokUbezpCzlonekRodziny) {
        this.krokUbezpCzlonekRodziny = krokUbezpCzlonekRodziny;
    }

    public Short getKrokUbezpIdent() {
        return krokUbezpIdent;
    }

    public void setKrokUbezpIdent(Short krokUbezpIdent) {
        this.krokUbezpIdent = krokUbezpIdent;
    }

    public Short getKrokUbezpIndschemat() {
        return krokUbezpIndschemat;
    }

    public void setKrokUbezpIndschemat(Short krokUbezpIndschemat) {
        this.krokUbezpIndschemat = krokUbezpIndschemat;
    }

    public Short getKrokUbezpInne() {
        return krokUbezpInne;
    }

    public void setKrokUbezpInne(Short krokUbezpInne) {
        this.krokUbezpInne = krokUbezpInne;
    }

    public Short getKrokUbezpKodPracy() {
        return krokUbezpKodPracy;
    }

    public void setKrokUbezpKodPracy(Short krokUbezpKodPracy) {
        this.krokUbezpKodPracy = krokUbezpKodPracy;
    }

    public Short getKrokUbezpPodleganie() {
        return krokUbezpPodleganie;
    }

    public void setKrokUbezpPodleganie(Short krokUbezpPodleganie) {
        this.krokUbezpPodleganie = krokUbezpPodleganie;
    }

    public Short getKrokPlatnDra2rca2() {
        return krokPlatnDra2rca2;
    }

    public void setKrokPlatnDra2rca2(Short krokPlatnDra2rca2) {
        this.krokPlatnDra2rca2 = krokPlatnDra2rca2;
    }

    public Short getKrokPlatnMdg() {
        return krokPlatnMdg;
    }

    public void setKrokPlatnMdg(Short krokPlatnMdg) {
        this.krokPlatnMdg = krokPlatnMdg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tmpUbezpieczeniPK != null ? tmpUbezpieczeniPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmpUbezpieczeni)) {
            return false;
        }
        TmpUbezpieczeni other = (TmpUbezpieczeni) object;
        if ((this.tmpUbezpieczeniPK == null && other.tmpUbezpieczeniPK != null) || (this.tmpUbezpieczeniPK != null && !this.tmpUbezpieczeniPK.equals(other.tmpUbezpieczeniPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.TmpUbezpieczeni[ tmpUbezpieczeniPK=" + tmpUbezpieczeniPK + " ]";
    }
    
}
