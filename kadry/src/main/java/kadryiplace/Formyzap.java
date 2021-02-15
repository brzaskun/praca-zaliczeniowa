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
@Table(name = "formyzap", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formyzap.findAll", query = "SELECT f FROM Formyzap f"),
    @NamedQuery(name = "Formyzap.findByForSerial", query = "SELECT f FROM Formyzap f WHERE f.forSerial = :forSerial"),
    @NamedQuery(name = "Formyzap.findByForOpis", query = "SELECT f FROM Formyzap f WHERE f.forOpis = :forOpis"),
    @NamedQuery(name = "Formyzap.findByForBezgot", query = "SELECT f FROM Formyzap f WHERE f.forBezgot = :forBezgot"),
    @NamedQuery(name = "Formyzap.findByForSystem", query = "SELECT f FROM Formyzap f WHERE f.forSystem = :forSystem"),
    @NamedQuery(name = "Formyzap.findByForChar1", query = "SELECT f FROM Formyzap f WHERE f.forChar1 = :forChar1"),
    @NamedQuery(name = "Formyzap.findByForChar2", query = "SELECT f FROM Formyzap f WHERE f.forChar2 = :forChar2"),
    @NamedQuery(name = "Formyzap.findByForChar3", query = "SELECT f FROM Formyzap f WHERE f.forChar3 = :forChar3"),
    @NamedQuery(name = "Formyzap.findByForChar4", query = "SELECT f FROM Formyzap f WHERE f.forChar4 = :forChar4"),
    @NamedQuery(name = "Formyzap.findByForVchar1", query = "SELECT f FROM Formyzap f WHERE f.forVchar1 = :forVchar1"),
    @NamedQuery(name = "Formyzap.findByForRodzaj", query = "SELECT f FROM Formyzap f WHERE f.forRodzaj = :forRodzaj"),
    @NamedQuery(name = "Formyzap.findByForChar5", query = "SELECT f FROM Formyzap f WHERE f.forChar5 = :forChar5"),
    @NamedQuery(name = "Formyzap.findByForChar6", query = "SELECT f FROM Formyzap f WHERE f.forChar6 = :forChar6"),
    @NamedQuery(name = "Formyzap.findByForChar7", query = "SELECT f FROM Formyzap f WHERE f.forChar7 = :forChar7"),
    @NamedQuery(name = "Formyzap.findByForChar8", query = "SELECT f FROM Formyzap f WHERE f.forChar8 = :forChar8"),
    @NamedQuery(name = "Formyzap.findByForChar9", query = "SELECT f FROM Formyzap f WHERE f.forChar9 = :forChar9"),
    @NamedQuery(name = "Formyzap.findByForChar10", query = "SELECT f FROM Formyzap f WHERE f.forChar10 = :forChar10"),
    @NamedQuery(name = "Formyzap.findByForChar11", query = "SELECT f FROM Formyzap f WHERE f.forChar11 = :forChar11"),
    @NamedQuery(name = "Formyzap.findByForChar12", query = "SELECT f FROM Formyzap f WHERE f.forChar12 = :forChar12")})
public class Formyzap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "for_serial", nullable = false)
    private Integer forSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "for_opis", nullable = false, length = 32)
    private String forOpis;
    @Column(name = "for_bezgot")
    private Character forBezgot;
    @Column(name = "for_system")
    private Character forSystem;
    @Column(name = "for_char_1")
    private Character forChar1;
    @Column(name = "for_char_2")
    private Character forChar2;
    @Column(name = "for_char_3")
    private Character forChar3;
    @Column(name = "for_char_4")
    private Character forChar4;
    @Size(max = 128)
    @Column(name = "for_vchar_1", length = 128)
    private String forVchar1;
    @Column(name = "for_rodzaj")
    private Character forRodzaj;
    @Column(name = "for_char_5")
    private Character forChar5;
    @Column(name = "for_char_6")
    private Character forChar6;
    @Column(name = "for_char_7")
    private Character forChar7;
    @Column(name = "for_char_8")
    private Character forChar8;
    @Column(name = "for_char_9")
    private Character forChar9;
    @Column(name = "for_char_10")
    private Character forChar10;
    @Column(name = "for_char_11")
    private Character forChar11;
    @Column(name = "for_char_12")
    private Character forChar12;
    @OneToMany(mappedBy = "parForSerial")
    private List<Paragon> paragonList;
    @OneToMany(mappedBy = "zdoForSerial")
    private List<Zakdok> zakdokList;
    @OneToMany(mappedBy = "zazForSerial")
    private List<Zakzap> zakzapList;
    @OneToMany(mappedBy = "konForSerial")
    private List<Kontrahent> kontrahentList;
    @OneToMany(mappedBy = "fakForSerial")
    private List<Fakrach> fakrachList;
    @OneToMany(mappedBy = "spzForSerial")
    private List<Sprzap> sprzapList;

    public Formyzap() {
    }

    public Formyzap(Integer forSerial) {
        this.forSerial = forSerial;
    }

    public Formyzap(Integer forSerial, String forOpis) {
        this.forSerial = forSerial;
        this.forOpis = forOpis;
    }

    public Integer getForSerial() {
        return forSerial;
    }

    public void setForSerial(Integer forSerial) {
        this.forSerial = forSerial;
    }

    public String getForOpis() {
        return forOpis;
    }

    public void setForOpis(String forOpis) {
        this.forOpis = forOpis;
    }

    public Character getForBezgot() {
        return forBezgot;
    }

    public void setForBezgot(Character forBezgot) {
        this.forBezgot = forBezgot;
    }

    public Character getForSystem() {
        return forSystem;
    }

    public void setForSystem(Character forSystem) {
        this.forSystem = forSystem;
    }

    public Character getForChar1() {
        return forChar1;
    }

    public void setForChar1(Character forChar1) {
        this.forChar1 = forChar1;
    }

    public Character getForChar2() {
        return forChar2;
    }

    public void setForChar2(Character forChar2) {
        this.forChar2 = forChar2;
    }

    public Character getForChar3() {
        return forChar3;
    }

    public void setForChar3(Character forChar3) {
        this.forChar3 = forChar3;
    }

    public Character getForChar4() {
        return forChar4;
    }

    public void setForChar4(Character forChar4) {
        this.forChar4 = forChar4;
    }

    public String getForVchar1() {
        return forVchar1;
    }

    public void setForVchar1(String forVchar1) {
        this.forVchar1 = forVchar1;
    }

    public Character getForRodzaj() {
        return forRodzaj;
    }

    public void setForRodzaj(Character forRodzaj) {
        this.forRodzaj = forRodzaj;
    }

    public Character getForChar5() {
        return forChar5;
    }

    public void setForChar5(Character forChar5) {
        this.forChar5 = forChar5;
    }

    public Character getForChar6() {
        return forChar6;
    }

    public void setForChar6(Character forChar6) {
        this.forChar6 = forChar6;
    }

    public Character getForChar7() {
        return forChar7;
    }

    public void setForChar7(Character forChar7) {
        this.forChar7 = forChar7;
    }

    public Character getForChar8() {
        return forChar8;
    }

    public void setForChar8(Character forChar8) {
        this.forChar8 = forChar8;
    }

    public Character getForChar9() {
        return forChar9;
    }

    public void setForChar9(Character forChar9) {
        this.forChar9 = forChar9;
    }

    public Character getForChar10() {
        return forChar10;
    }

    public void setForChar10(Character forChar10) {
        this.forChar10 = forChar10;
    }

    public Character getForChar11() {
        return forChar11;
    }

    public void setForChar11(Character forChar11) {
        this.forChar11 = forChar11;
    }

    public Character getForChar12() {
        return forChar12;
    }

    public void setForChar12(Character forChar12) {
        this.forChar12 = forChar12;
    }

    @XmlTransient
    public List<Paragon> getParagonList() {
        return paragonList;
    }

    public void setParagonList(List<Paragon> paragonList) {
        this.paragonList = paragonList;
    }

    @XmlTransient
    public List<Zakdok> getZakdokList() {
        return zakdokList;
    }

    public void setZakdokList(List<Zakdok> zakdokList) {
        this.zakdokList = zakdokList;
    }

    @XmlTransient
    public List<Zakzap> getZakzapList() {
        return zakzapList;
    }

    public void setZakzapList(List<Zakzap> zakzapList) {
        this.zakzapList = zakzapList;
    }

    @XmlTransient
    public List<Kontrahent> getKontrahentList() {
        return kontrahentList;
    }

    public void setKontrahentList(List<Kontrahent> kontrahentList) {
        this.kontrahentList = kontrahentList;
    }

    @XmlTransient
    public List<Fakrach> getFakrachList() {
        return fakrachList;
    }

    public void setFakrachList(List<Fakrach> fakrachList) {
        this.fakrachList = fakrachList;
    }

    @XmlTransient
    public List<Sprzap> getSprzapList() {
        return sprzapList;
    }

    public void setSprzapList(List<Sprzap> sprzapList) {
        this.sprzapList = sprzapList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (forSerial != null ? forSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formyzap)) {
            return false;
        }
        Formyzap other = (Formyzap) object;
        if ((this.forSerial == null && other.forSerial != null) || (this.forSerial != null && !this.forSerial.equals(other.forSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Formyzap[ forSerial=" + forSerial + " ]";
    }
    
}
