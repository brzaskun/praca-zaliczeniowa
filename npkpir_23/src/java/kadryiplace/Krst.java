/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "krst", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Krst.findAll", query = "SELECT k FROM Krst k"),
    @NamedQuery(name = "Krst.findByKrsSerial", query = "SELECT k FROM Krst k WHERE k.krsSerial = :krsSerial"),
    @NamedQuery(name = "Krst.findByKrsPozycja", query = "SELECT k FROM Krst k WHERE k.krsPozycja = :krsPozycja"),
    @NamedQuery(name = "Krst.findByKrsGrupa", query = "SELECT k FROM Krst k WHERE k.krsGrupa = :krsGrupa"),
    @NamedQuery(name = "Krst.findByKrsDalsze", query = "SELECT k FROM Krst k WHERE k.krsDalsze = :krsDalsze"),
    @NamedQuery(name = "Krst.findByKrsNazwa", query = "SELECT k FROM Krst k WHERE k.krsNazwa = :krsNazwa"),
    @NamedQuery(name = "Krst.findByKrsStawkaOd", query = "SELECT k FROM Krst k WHERE k.krsStawkaOd = :krsStawkaOd"),
    @NamedQuery(name = "Krst.findByKrsStawkaDo", query = "SELECT k FROM Krst k WHERE k.krsStawkaDo = :krsStawkaDo"),
    @NamedQuery(name = "Krst.findByKrsOpis", query = "SELECT k FROM Krst k WHERE k.krsOpis = :krsOpis"),
    @NamedQuery(name = "Krst.findByKrsPgrRodz", query = "SELECT k FROM Krst k WHERE k.krsPgrRodz = :krsPgrRodz"),
    @NamedQuery(name = "Krst.findByKrsChar1", query = "SELECT k FROM Krst k WHERE k.krsChar1 = :krsChar1"),
    @NamedQuery(name = "Krst.findByKrsChar2", query = "SELECT k FROM Krst k WHERE k.krsChar2 = :krsChar2"),
    @NamedQuery(name = "Krst.findByKrsVchar1", query = "SELECT k FROM Krst k WHERE k.krsVchar1 = :krsVchar1"),
    @NamedQuery(name = "Krst.findByKrsNum1", query = "SELECT k FROM Krst k WHERE k.krsNum1 = :krsNum1"),
    @NamedQuery(name = "Krst.findByKrsNum2", query = "SELECT k FROM Krst k WHERE k.krsNum2 = :krsNum2")})
public class Krst implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "krs_serial", nullable = false)
    private Integer krsSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "krs_pozycja", nullable = false)
    private int krsPozycja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "krs_grupa", nullable = false, length = 16)
    private String krsGrupa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "krs_dalsze", nullable = false, length = 64)
    private String krsDalsze;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "krs_nazwa", nullable = false, length = 64)
    private String krsNazwa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "krs_stawka_od", nullable = false, precision = 5, scale = 2)
    private BigDecimal krsStawkaOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "krs_stawka_do", nullable = false, precision = 5, scale = 2)
    private BigDecimal krsStawkaDo;
    @Size(max = 254)
    @Column(name = "krs_opis", length = 254)
    private String krsOpis;
    @Size(max = 64)
    @Column(name = "krs_pgr_rodz", length = 64)
    private String krsPgrRodz;
    @Column(name = "krs_char_1")
    private Character krsChar1;
    @Column(name = "krs_char_2")
    private Character krsChar2;
    @Size(max = 254)
    @Column(name = "krs_vchar_1", length = 254)
    private String krsVchar1;
    @Column(name = "krs_num_1", precision = 17, scale = 6)
    private BigDecimal krsNum1;
    @Column(name = "krs_num_2", precision = 17, scale = 6)
    private BigDecimal krsNum2;
    @OneToMany(mappedBy = "sroKrsSerial")
    private List<Srodki> srodkiList;

    public Krst() {
    }

    public Krst(Integer krsSerial) {
        this.krsSerial = krsSerial;
    }

    public Krst(Integer krsSerial, int krsPozycja, String krsGrupa, String krsDalsze, String krsNazwa, BigDecimal krsStawkaOd, BigDecimal krsStawkaDo) {
        this.krsSerial = krsSerial;
        this.krsPozycja = krsPozycja;
        this.krsGrupa = krsGrupa;
        this.krsDalsze = krsDalsze;
        this.krsNazwa = krsNazwa;
        this.krsStawkaOd = krsStawkaOd;
        this.krsStawkaDo = krsStawkaDo;
    }

    public Integer getKrsSerial() {
        return krsSerial;
    }

    public void setKrsSerial(Integer krsSerial) {
        this.krsSerial = krsSerial;
    }

    public int getKrsPozycja() {
        return krsPozycja;
    }

    public void setKrsPozycja(int krsPozycja) {
        this.krsPozycja = krsPozycja;
    }

    public String getKrsGrupa() {
        return krsGrupa;
    }

    public void setKrsGrupa(String krsGrupa) {
        this.krsGrupa = krsGrupa;
    }

    public String getKrsDalsze() {
        return krsDalsze;
    }

    public void setKrsDalsze(String krsDalsze) {
        this.krsDalsze = krsDalsze;
    }

    public String getKrsNazwa() {
        return krsNazwa;
    }

    public void setKrsNazwa(String krsNazwa) {
        this.krsNazwa = krsNazwa;
    }

    public BigDecimal getKrsStawkaOd() {
        return krsStawkaOd;
    }

    public void setKrsStawkaOd(BigDecimal krsStawkaOd) {
        this.krsStawkaOd = krsStawkaOd;
    }

    public BigDecimal getKrsStawkaDo() {
        return krsStawkaDo;
    }

    public void setKrsStawkaDo(BigDecimal krsStawkaDo) {
        this.krsStawkaDo = krsStawkaDo;
    }

    public String getKrsOpis() {
        return krsOpis;
    }

    public void setKrsOpis(String krsOpis) {
        this.krsOpis = krsOpis;
    }

    public String getKrsPgrRodz() {
        return krsPgrRodz;
    }

    public void setKrsPgrRodz(String krsPgrRodz) {
        this.krsPgrRodz = krsPgrRodz;
    }

    public Character getKrsChar1() {
        return krsChar1;
    }

    public void setKrsChar1(Character krsChar1) {
        this.krsChar1 = krsChar1;
    }

    public Character getKrsChar2() {
        return krsChar2;
    }

    public void setKrsChar2(Character krsChar2) {
        this.krsChar2 = krsChar2;
    }

    public String getKrsVchar1() {
        return krsVchar1;
    }

    public void setKrsVchar1(String krsVchar1) {
        this.krsVchar1 = krsVchar1;
    }

    public BigDecimal getKrsNum1() {
        return krsNum1;
    }

    public void setKrsNum1(BigDecimal krsNum1) {
        this.krsNum1 = krsNum1;
    }

    public BigDecimal getKrsNum2() {
        return krsNum2;
    }

    public void setKrsNum2(BigDecimal krsNum2) {
        this.krsNum2 = krsNum2;
    }

    @XmlTransient
    public List<Srodki> getSrodkiList() {
        return srodkiList;
    }

    public void setSrodkiList(List<Srodki> srodkiList) {
        this.srodkiList = srodkiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (krsSerial != null ? krsSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Krst)) {
            return false;
        }
        Krst other = (Krst) object;
        if ((this.krsSerial == null && other.krsSerial != null) || (this.krsSerial != null && !this.krsSerial.equals(other.krsSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Krst[ krsSerial=" + krsSerial + " ]";
    }
    
}
