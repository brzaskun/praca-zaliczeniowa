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
@Table(name = "zus", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zus.findAll", query = "SELECT z FROM Zus z"),
    @NamedQuery(name = "Zus.findByZusSerial", query = "SELECT z FROM Zus z WHERE z.zusSerial = :zusSerial"),
    @NamedQuery(name = "Zus.findByZusNazwa", query = "SELECT z FROM Zus z WHERE z.zusNazwa = :zusNazwa"),
    @NamedQuery(name = "Zus.findByZusMiaSerial", query = "SELECT z FROM Zus z WHERE z.zusMiaSerial = :zusMiaSerial"),
    @NamedQuery(name = "Zus.findByZusPanSerial", query = "SELECT z FROM Zus z WHERE z.zusPanSerial = :zusPanSerial"),
    @NamedQuery(name = "Zus.findByZusKod", query = "SELECT z FROM Zus z WHERE z.zusKod = :zusKod"),
    @NamedQuery(name = "Zus.findByZusUlica", query = "SELECT z FROM Zus z WHERE z.zusUlica = :zusUlica"),
    @NamedQuery(name = "Zus.findByZusDom", query = "SELECT z FROM Zus z WHERE z.zusDom = :zusDom"),
    @NamedQuery(name = "Zus.findByZusMieszkanie", query = "SELECT z FROM Zus z WHERE z.zusMieszkanie = :zusMieszkanie"),
    @NamedQuery(name = "Zus.findByZusVchar1", query = "SELECT z FROM Zus z WHERE z.zusVchar1 = :zusVchar1"),
    @NamedQuery(name = "Zus.findByZusVchar2", query = "SELECT z FROM Zus z WHERE z.zusVchar2 = :zusVchar2"),
    @NamedQuery(name = "Zus.findByZusInt1", query = "SELECT z FROM Zus z WHERE z.zusInt1 = :zusInt1"),
    @NamedQuery(name = "Zus.findByZusInt2", query = "SELECT z FROM Zus z WHERE z.zusInt2 = :zusInt2"),
    @NamedQuery(name = "Zus.findByZusChar1", query = "SELECT z FROM Zus z WHERE z.zusChar1 = :zusChar1"),
    @NamedQuery(name = "Zus.findByZusChar2", query = "SELECT z FROM Zus z WHERE z.zusChar2 = :zusChar2"),
    @NamedQuery(name = "Zus.findByZusTyp", query = "SELECT z FROM Zus z WHERE z.zusTyp = :zusTyp")})
public class Zus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "zus_serial", nullable = false)
    private Integer zusSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "zus_nazwa", nullable = false, length = 128)
    private String zusNazwa;
    @Column(name = "zus_mia_serial")
    private Integer zusMiaSerial;
    @Column(name = "zus_pan_serial")
    private Integer zusPanSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "zus_kod", nullable = false, length = 5)
    private String zusKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "zus_ulica", nullable = false, length = 64)
    private String zusUlica;
    @Size(max = 10)
    @Column(name = "zus_dom", length = 10)
    private String zusDom;
    @Size(max = 10)
    @Column(name = "zus_mieszkanie", length = 10)
    private String zusMieszkanie;
    @Size(max = 64)
    @Column(name = "zus_vchar_1", length = 64)
    private String zusVchar1;
    @Size(max = 64)
    @Column(name = "zus_vchar_2", length = 64)
    private String zusVchar2;
    @Column(name = "zus_int_1")
    private Integer zusInt1;
    @Column(name = "zus_int_2")
    private Integer zusInt2;
    @Column(name = "zus_char_1")
    private Character zusChar1;
    @Column(name = "zus_char_2")
    private Character zusChar2;
    @Column(name = "zus_typ")
    private Character zusTyp;

    public Zus() {
    }

    public Zus(Integer zusSerial) {
        this.zusSerial = zusSerial;
    }

    public Zus(Integer zusSerial, String zusNazwa, String zusKod, String zusUlica) {
        this.zusSerial = zusSerial;
        this.zusNazwa = zusNazwa;
        this.zusKod = zusKod;
        this.zusUlica = zusUlica;
    }

    public Integer getZusSerial() {
        return zusSerial;
    }

    public void setZusSerial(Integer zusSerial) {
        this.zusSerial = zusSerial;
    }

    public String getZusNazwa() {
        return zusNazwa;
    }

    public void setZusNazwa(String zusNazwa) {
        this.zusNazwa = zusNazwa;
    }

    public Integer getZusMiaSerial() {
        return zusMiaSerial;
    }

    public void setZusMiaSerial(Integer zusMiaSerial) {
        this.zusMiaSerial = zusMiaSerial;
    }

    public Integer getZusPanSerial() {
        return zusPanSerial;
    }

    public void setZusPanSerial(Integer zusPanSerial) {
        this.zusPanSerial = zusPanSerial;
    }

    public String getZusKod() {
        return zusKod;
    }

    public void setZusKod(String zusKod) {
        this.zusKod = zusKod;
    }

    public String getZusUlica() {
        return zusUlica;
    }

    public void setZusUlica(String zusUlica) {
        this.zusUlica = zusUlica;
    }

    public String getZusDom() {
        return zusDom;
    }

    public void setZusDom(String zusDom) {
        this.zusDom = zusDom;
    }

    public String getZusMieszkanie() {
        return zusMieszkanie;
    }

    public void setZusMieszkanie(String zusMieszkanie) {
        this.zusMieszkanie = zusMieszkanie;
    }

    public String getZusVchar1() {
        return zusVchar1;
    }

    public void setZusVchar1(String zusVchar1) {
        this.zusVchar1 = zusVchar1;
    }

    public String getZusVchar2() {
        return zusVchar2;
    }

    public void setZusVchar2(String zusVchar2) {
        this.zusVchar2 = zusVchar2;
    }

    public Integer getZusInt1() {
        return zusInt1;
    }

    public void setZusInt1(Integer zusInt1) {
        this.zusInt1 = zusInt1;
    }

    public Integer getZusInt2() {
        return zusInt2;
    }

    public void setZusInt2(Integer zusInt2) {
        this.zusInt2 = zusInt2;
    }

    public Character getZusChar1() {
        return zusChar1;
    }

    public void setZusChar1(Character zusChar1) {
        this.zusChar1 = zusChar1;
    }

    public Character getZusChar2() {
        return zusChar2;
    }

    public void setZusChar2(Character zusChar2) {
        this.zusChar2 = zusChar2;
    }

    public Character getZusTyp() {
        return zusTyp;
    }

    public void setZusTyp(Character zusTyp) {
        this.zusTyp = zusTyp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zusSerial != null ? zusSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zus)) {
            return false;
        }
        Zus other = (Zus) object;
        if ((this.zusSerial == null && other.zusSerial != null) || (this.zusSerial != null && !this.zusSerial.equals(other.zusSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Zus[ zusSerial=" + zusSerial + " ]";
    }
    
}
