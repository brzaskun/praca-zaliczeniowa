/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "jednostka", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jednostka.findAll", query = "SELECT j FROM Jednostka j"),
    @NamedQuery(name = "Jednostka.findByJedSerial", query = "SELECT j FROM Jednostka j WHERE j.jedSerial = :jedSerial"),
    @NamedQuery(name = "Jednostka.findByJedOpis", query = "SELECT j FROM Jednostka j WHERE j.jedOpis = :jedOpis"),
    @NamedQuery(name = "Jednostka.findByJedSkrot", query = "SELECT j FROM Jednostka j WHERE j.jedSkrot = :jedSkrot"),
    @NamedQuery(name = "Jednostka.findByJedTowar", query = "SELECT j FROM Jednostka j WHERE j.jedTowar = :jedTowar"),
    @NamedQuery(name = "Jednostka.findByJedUsluga", query = "SELECT j FROM Jednostka j WHERE j.jedUsluga = :jedUsluga"),
    @NamedQuery(name = "Jednostka.findByJedWyrob", query = "SELECT j FROM Jednostka j WHERE j.jedWyrob = :jedWyrob"),
    @NamedQuery(name = "Jednostka.findByJedMiePoPrz", query = "SELECT j FROM Jednostka j WHERE j.jedMiePoPrz = :jedMiePoPrz"),
    @NamedQuery(name = "Jednostka.findByJedChar1", query = "SELECT j FROM Jednostka j WHERE j.jedChar1 = :jedChar1"),
    @NamedQuery(name = "Jednostka.findByJedChar2", query = "SELECT j FROM Jednostka j WHERE j.jedChar2 = :jedChar2"),
    @NamedQuery(name = "Jednostka.findByJedVchar1", query = "SELECT j FROM Jednostka j WHERE j.jedVchar1 = :jedVchar1")})
public class Jednostka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "jed_serial", nullable = false)
    private Integer jedSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "jed_opis", nullable = false, length = 32)
    private String jedOpis;
    @Size(max = 16)
    @Column(name = "jed_skrot", length = 16)
    private String jedSkrot;
    @Column(name = "jed_towar")
    private Character jedTowar;
    @Column(name = "jed_usluga")
    private Character jedUsluga;
    @Column(name = "jed_wyrob")
    private Character jedWyrob;
    @Column(name = "jed_mie_po_prz")
    private Short jedMiePoPrz;
    @Column(name = "jed_char_1")
    private Character jedChar1;
    @Column(name = "jed_char_2")
    private Character jedChar2;
    @Size(max = 64)
    @Column(name = "jed_vchar_1", length = 64)
    private String jedVchar1;
    @OneToMany(mappedBy = "zpzMagJedSerial")
    private List<Zakpoz> zakpozList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "magJedSerial")
    private List<Magazyn> magazynList;
    @OneToMany(mappedBy = "pozMagJedSerial")
    private List<Pozycja> pozycjaList;
    @OneToMany(mappedBy = "mpzMagJedSerial")
    private List<Magpoz> magpozList;
    @OneToMany(mappedBy = "pazMagJedSerial")
    private List<Parpoz> parpozList;

    public Jednostka() {
    }

    public Jednostka(Integer jedSerial) {
        this.jedSerial = jedSerial;
    }

    public Jednostka(Integer jedSerial, String jedOpis) {
        this.jedSerial = jedSerial;
        this.jedOpis = jedOpis;
    }

    public Integer getJedSerial() {
        return jedSerial;
    }

    public void setJedSerial(Integer jedSerial) {
        this.jedSerial = jedSerial;
    }

    public String getJedOpis() {
        return jedOpis;
    }

    public void setJedOpis(String jedOpis) {
        this.jedOpis = jedOpis;
    }

    public String getJedSkrot() {
        return jedSkrot;
    }

    public void setJedSkrot(String jedSkrot) {
        this.jedSkrot = jedSkrot;
    }

    public Character getJedTowar() {
        return jedTowar;
    }

    public void setJedTowar(Character jedTowar) {
        this.jedTowar = jedTowar;
    }

    public Character getJedUsluga() {
        return jedUsluga;
    }

    public void setJedUsluga(Character jedUsluga) {
        this.jedUsluga = jedUsluga;
    }

    public Character getJedWyrob() {
        return jedWyrob;
    }

    public void setJedWyrob(Character jedWyrob) {
        this.jedWyrob = jedWyrob;
    }

    public Short getJedMiePoPrz() {
        return jedMiePoPrz;
    }

    public void setJedMiePoPrz(Short jedMiePoPrz) {
        this.jedMiePoPrz = jedMiePoPrz;
    }

    public Character getJedChar1() {
        return jedChar1;
    }

    public void setJedChar1(Character jedChar1) {
        this.jedChar1 = jedChar1;
    }

    public Character getJedChar2() {
        return jedChar2;
    }

    public void setJedChar2(Character jedChar2) {
        this.jedChar2 = jedChar2;
    }

    public String getJedVchar1() {
        return jedVchar1;
    }

    public void setJedVchar1(String jedVchar1) {
        this.jedVchar1 = jedVchar1;
    }

    @XmlTransient
    public List<Zakpoz> getZakpozList() {
        return zakpozList;
    }

    public void setZakpozList(List<Zakpoz> zakpozList) {
        this.zakpozList = zakpozList;
    }

    @XmlTransient
    public List<Magazyn> getMagazynList() {
        return magazynList;
    }

    public void setMagazynList(List<Magazyn> magazynList) {
        this.magazynList = magazynList;
    }

    @XmlTransient
    public List<Pozycja> getPozycjaList() {
        return pozycjaList;
    }

    public void setPozycjaList(List<Pozycja> pozycjaList) {
        this.pozycjaList = pozycjaList;
    }

    @XmlTransient
    public List<Magpoz> getMagpozList() {
        return magpozList;
    }

    public void setMagpozList(List<Magpoz> magpozList) {
        this.magpozList = magpozList;
    }

    @XmlTransient
    public List<Parpoz> getParpozList() {
        return parpozList;
    }

    public void setParpozList(List<Parpoz> parpozList) {
        this.parpozList = parpozList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jedSerial != null ? jedSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jednostka)) {
            return false;
        }
        Jednostka other = (Jednostka) object;
        if ((this.jedSerial == null && other.jedSerial != null) || (this.jedSerial != null && !this.jedSerial.equals(other.jedSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Jednostka[ jedSerial=" + jedSerial + " ]";
    }
    
}
