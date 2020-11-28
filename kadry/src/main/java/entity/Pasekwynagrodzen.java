/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "pasekwynagrodzen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pasekwynagrodzen.findAll", query = "SELECT p FROM Pasekwynagrodzen p"),
    @NamedQuery(name = "Pasekwynagrodzen.findById", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.id = :id"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPracchorobowe", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.pracchorobowe = :pracchorobowe"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPracrentowe", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.pracrentowe = :pracrentowe"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPracemerytalne", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.pracemerytalne = :pracemerytalne"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPraczdrowotne", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.praczdrowotne = :praczdrowotne"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPraczdrowotnepomniejszone", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.praczdrowotnepomniejszone = :praczdrowotnepomniejszone"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPraczdrowotnedodoliczenia", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.praczdrowotnedodoliczenia = :praczdrowotnedodoliczenia"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPraczdrowotnedopotracenia", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.praczdrowotnedopotracenia = :praczdrowotnedopotracenia"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRentowe", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rentowe = :rentowe"),
    @NamedQuery(name = "Pasekwynagrodzen.findByWypadkowe", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.wypadkowe = :wypadkowe"),
    @NamedQuery(name = "Pasekwynagrodzen.findByFp", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.fp = :fp"),
    @NamedQuery(name = "Pasekwynagrodzen.findByFgsp", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.fgsp = :fgsp"),
    @NamedQuery(name = "Pasekwynagrodzen.findByKosztyuzyskania", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.kosztyuzyskania = :kosztyuzyskania"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPodstawaopodatkowania", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.podstawaopodatkowania = :podstawaopodatkowania"),
    @NamedQuery(name = "Pasekwynagrodzen.findByKwotawolna", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.kwotawolna = :kwotawolna"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPodatekdochodowy", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.podatekdochodowy = :podatekdochodowy"),
    @NamedQuery(name = "Pasekwynagrodzen.findByNetto", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.netto = :netto"),
    @NamedQuery(name = "Pasekwynagrodzen.findByBruttozus", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.bruttozus = :bruttozus"),
    @NamedQuery(name = "Pasekwynagrodzen.findByBruttobezzus", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.bruttobezzus = :bruttobezzus")})
public class Pasekwynagrodzen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pracchorobowe")
    private Double pracchorobowe;
    @Column(name = "pracrentowe")
    private Double pracrentowe;
    @Column(name = "pracemerytalne")
    private Double pracemerytalne;
    @Column(name = "praczdrowotne")
    private Double praczdrowotne;
    @Column(name = "praczdrowotnepomniejszone")
    private Double praczdrowotnepomniejszone;
    @Column(name = "praczdrowotnedodoliczenia")
    private Double praczdrowotnedodoliczenia;
    @Column(name = "praczdrowotnedopotracenia")
    private Double praczdrowotnedopotracenia;
    @Column(name = "rentowe")
    private Double rentowe;
    @Column(name = "wypadkowe")
    private Double wypadkowe;
    @Column(name = "fp")
    private Double fp;
    @Column(name = "fgsp")
    private Double fgsp;
    @Column(name = "kosztyuzyskania")
    private Double kosztyuzyskania;
    @Column(name = "podstawaopodatkowania")
    private Double podstawaopodatkowania;
    @Column(name = "kwotawolna")
    private Double kwotawolna;
    @Column(name = "podatekdochodowy")
    private Double podatekdochodowy;
    @Column(name = "netto")
    private Double netto;
    @Column(name = "bruttozus")
    private Double bruttozus;
    @Column(name = "bruttobezzus")
    private Double bruttobezzus;
    @JoinColumn(name = "definicjalistaplac", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Definicjalistaplac definicjalistaplac;

    public Pasekwynagrodzen() {
    }

    public Pasekwynagrodzen(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPracchorobowe() {
        return pracchorobowe;
    }

    public void setPracchorobowe(Double pracchorobowe) {
        this.pracchorobowe = pracchorobowe;
    }

    public Double getPracrentowe() {
        return pracrentowe;
    }

    public void setPracrentowe(Double pracrentowe) {
        this.pracrentowe = pracrentowe;
    }

    public Double getPracemerytalne() {
        return pracemerytalne;
    }

    public void setPracemerytalne(Double pracemerytalne) {
        this.pracemerytalne = pracemerytalne;
    }

    public Double getPraczdrowotne() {
        return praczdrowotne;
    }

    public void setPraczdrowotne(Double praczdrowotne) {
        this.praczdrowotne = praczdrowotne;
    }

    public Double getPraczdrowotnepomniejszone() {
        return praczdrowotnepomniejszone;
    }

    public void setPraczdrowotnepomniejszone(Double praczdrowotnepomniejszone) {
        this.praczdrowotnepomniejszone = praczdrowotnepomniejszone;
    }

    public Double getPraczdrowotnedodoliczenia() {
        return praczdrowotnedodoliczenia;
    }

    public void setPraczdrowotnedodoliczenia(Double praczdrowotnedodoliczenia) {
        this.praczdrowotnedodoliczenia = praczdrowotnedodoliczenia;
    }

    public Double getPraczdrowotnedopotracenia() {
        return praczdrowotnedopotracenia;
    }

    public void setPraczdrowotnedopotracenia(Double praczdrowotnedopotracenia) {
        this.praczdrowotnedopotracenia = praczdrowotnedopotracenia;
    }

    public Double getRentowe() {
        return rentowe;
    }

    public void setRentowe(Double rentowe) {
        this.rentowe = rentowe;
    }

    public Double getWypadkowe() {
        return wypadkowe;
    }

    public void setWypadkowe(Double wypadkowe) {
        this.wypadkowe = wypadkowe;
    }

    public Double getFp() {
        return fp;
    }

    public void setFp(Double fp) {
        this.fp = fp;
    }

    public Double getFgsp() {
        return fgsp;
    }

    public void setFgsp(Double fgsp) {
        this.fgsp = fgsp;
    }

    public Double getKosztyuzyskania() {
        return kosztyuzyskania;
    }

    public void setKosztyuzyskania(Double kosztyuzyskania) {
        this.kosztyuzyskania = kosztyuzyskania;
    }

    public Double getPodstawaopodatkowania() {
        return podstawaopodatkowania;
    }

    public void setPodstawaopodatkowania(Double podstawaopodatkowania) {
        this.podstawaopodatkowania = podstawaopodatkowania;
    }

    public Double getKwotawolna() {
        return kwotawolna;
    }

    public void setKwotawolna(Double kwotawolna) {
        this.kwotawolna = kwotawolna;
    }

    public Double getPodatekdochodowy() {
        return podatekdochodowy;
    }

    public void setPodatekdochodowy(Double podatekdochodowy) {
        this.podatekdochodowy = podatekdochodowy;
    }

    public Double getNetto() {
        return netto;
    }

    public void setNetto(Double netto) {
        this.netto = netto;
    }

    public Double getBruttozus() {
        return bruttozus;
    }

    public void setBruttozus(Double bruttozus) {
        this.bruttozus = bruttozus;
    }

    public Double getBruttobezzus() {
        return bruttobezzus;
    }

    public void setBruttobezzus(Double bruttobezzus) {
        this.bruttobezzus = bruttobezzus;
    }

    public Definicjalistaplac getDefinicjalistaplac() {
        return definicjalistaplac;
    }

    public void setDefinicjalistaplac(Definicjalistaplac definicjalistaplac) {
        this.definicjalistaplac = definicjalistaplac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pasekwynagrodzen)) {
            return false;
        }
        Pasekwynagrodzen other = (Pasekwynagrodzen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pasekwynagrodzen[ id=" + id + " ]";
    }
    
}
