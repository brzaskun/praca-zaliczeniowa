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
@Table(name = "kon_slow", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KonSlow.findAll", query = "SELECT k FROM KonSlow k"),
    @NamedQuery(name = "KonSlow.findByKslSerial", query = "SELECT k FROM KonSlow k WHERE k.kslSerial = :kslSerial"),
    @NamedQuery(name = "KonSlow.findByKslTyp", query = "SELECT k FROM KonSlow k WHERE k.kslTyp = :kslTyp"),
    @NamedQuery(name = "KonSlow.findByKslOpis", query = "SELECT k FROM KonSlow k WHERE k.kslOpis = :kslOpis"),
    @NamedQuery(name = "KonSlow.findByKslPorzadek", query = "SELECT k FROM KonSlow k WHERE k.kslPorzadek = :kslPorzadek")})
public class KonSlow implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ksl_serial", nullable = false)
    private Integer kslSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ksl_typ", nullable = false)
    private Character kslTyp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "ksl_opis", nullable = false, length = 128)
    private String kslOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ksl_porzadek", nullable = false)
    private short kslPorzadek;
    @OneToMany(mappedBy = "konTytulZaw")
    private List<Kontrahent> kontrahentList;
    @OneToMany(mappedBy = "konTypKontakt")
    private List<Kontrahent> kontrahentList1;
    @OneToMany(mappedBy = "konZrodlo")
    private List<Kontrahent> kontrahentList2;
    @OneToMany(mappedBy = "konZwrotPocz")
    private List<Kontrahent> kontrahentList3;
    @OneToMany(mappedBy = "konBranza")
    private List<Kontrahent> kontrahentList4;
    @JoinColumn(name = "ksl_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma kslFirSerial;

    public KonSlow() {
    }

    public KonSlow(Integer kslSerial) {
        this.kslSerial = kslSerial;
    }

    public KonSlow(Integer kslSerial, Character kslTyp, String kslOpis, short kslPorzadek) {
        this.kslSerial = kslSerial;
        this.kslTyp = kslTyp;
        this.kslOpis = kslOpis;
        this.kslPorzadek = kslPorzadek;
    }

    public Integer getKslSerial() {
        return kslSerial;
    }

    public void setKslSerial(Integer kslSerial) {
        this.kslSerial = kslSerial;
    }

    public Character getKslTyp() {
        return kslTyp;
    }

    public void setKslTyp(Character kslTyp) {
        this.kslTyp = kslTyp;
    }

    public String getKslOpis() {
        return kslOpis;
    }

    public void setKslOpis(String kslOpis) {
        this.kslOpis = kslOpis;
    }

    public short getKslPorzadek() {
        return kslPorzadek;
    }

    public void setKslPorzadek(short kslPorzadek) {
        this.kslPorzadek = kslPorzadek;
    }

    @XmlTransient
    public List<Kontrahent> getKontrahentList() {
        return kontrahentList;
    }

    public void setKontrahentList(List<Kontrahent> kontrahentList) {
        this.kontrahentList = kontrahentList;
    }

    @XmlTransient
    public List<Kontrahent> getKontrahentList1() {
        return kontrahentList1;
    }

    public void setKontrahentList1(List<Kontrahent> kontrahentList1) {
        this.kontrahentList1 = kontrahentList1;
    }

    @XmlTransient
    public List<Kontrahent> getKontrahentList2() {
        return kontrahentList2;
    }

    public void setKontrahentList2(List<Kontrahent> kontrahentList2) {
        this.kontrahentList2 = kontrahentList2;
    }

    @XmlTransient
    public List<Kontrahent> getKontrahentList3() {
        return kontrahentList3;
    }

    public void setKontrahentList3(List<Kontrahent> kontrahentList3) {
        this.kontrahentList3 = kontrahentList3;
    }

    @XmlTransient
    public List<Kontrahent> getKontrahentList4() {
        return kontrahentList4;
    }

    public void setKontrahentList4(List<Kontrahent> kontrahentList4) {
        this.kontrahentList4 = kontrahentList4;
    }

    public Firma getKslFirSerial() {
        return kslFirSerial;
    }

    public void setKslFirSerial(Firma kslFirSerial) {
        this.kslFirSerial = kslFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kslSerial != null ? kslSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KonSlow)) {
            return false;
        }
        KonSlow other = (KonSlow) object;
        if ((this.kslSerial == null && other.kslSerial != null) || (this.kslSerial != null && !this.kslSerial.equals(other.kslSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.KonSlow[ kslSerial=" + kslSerial + " ]";
    }
    
}
