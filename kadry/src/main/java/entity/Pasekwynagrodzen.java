/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "pasekwynagrodzen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pasekwynagrodzen.findAll", query = "SELECT p FROM Pasekwynagrodzen p"),
    @NamedQuery(name = "Pasekwynagrodzen.findByDefKal", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.definicjalistaplac = :definicjalistaplac AND p.kalendarzmiesiac = :kalendarzmiesiac"),
    @NamedQuery(name = "Pasekwynagrodzen.findById", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.id = :id"),
    @NamedQuery(name = "Pasekwynagrodzen.findByBruttobezzus", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.bruttobezzus = :bruttobezzus"),
    @NamedQuery(name = "Pasekwynagrodzen.findByBruttozus", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.bruttozus = :bruttozus"),
    @NamedQuery(name = "Pasekwynagrodzen.findByFgsp", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.fgsp = :fgsp"),
    @NamedQuery(name = "Pasekwynagrodzen.findByFp", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.fp = :fp"),
    @NamedQuery(name = "Pasekwynagrodzen.findByKosztyuzyskania", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.kosztyuzyskania = :kosztyuzyskania"),
    @NamedQuery(name = "Pasekwynagrodzen.findByKwotawolna", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.kwotawolna = :kwotawolna"),
    @NamedQuery(name = "Pasekwynagrodzen.findByNetto", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.netto = :netto"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPodatekdochodowy", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.podatekdochodowy = :podatekdochodowy"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPodstawaopodatkowania", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.podstawaopodatkowania = :podstawaopodatkowania"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPracchorobowe", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.pracchorobowe = :pracchorobowe"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPracemerytalne", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.pracemerytalne = :pracemerytalne"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPracrentowe", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.pracrentowe = :pracrentowe"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPraczdrowotne", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.praczdrowotne = :praczdrowotne"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPraczdrowotnedodoliczenia", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.praczdrowotnedodoliczenia = :praczdrowotnedodoliczenia"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPraczdrowotnedopotracenia", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.praczdrowotnedopotracenia = :praczdrowotnedopotracenia"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPraczdrowotnepomniejszone", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.praczdrowotnepomniejszone = :praczdrowotnepomniejszone"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRentowe", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rentowe = :rentowe"),
    @NamedQuery(name = "Pasekwynagrodzen.findByWypadkowe", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.wypadkowe = :wypadkowe")})
public class Pasekwynagrodzen implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "bruttobezzus")
private Double bruttobezzus;
    @Column(name = "bruttozus")
    private Double bruttozus;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fgsp")
    private Double fgsp;
    @Column(name = "fp")
    private Double fp;
    @Column(name = "kosztyuzyskania")
    private Double kosztyuzyskania;
    @Column(name = "kwotawolna")
    private Double kwotawolna;
    @Column(name = "netto")
    private Double netto;
    @Column(name = "podatekdochodowy")
    private Double podatekdochodowy;
    @Column(name = "podstawaopodatkowania")
    private Double podstawaopodatkowania;
    @Column(name = "pracchorobowe")
    private Double pracchorobowe;
    @Column(name = "pracemerytalne")
    private Double pracemerytalne;
    @Column(name = "pracrentowe")
    private Double pracrentowe;
    @Column(name = "razemspolecznepracownik")
    private Double razemspolecznepracownik;
    @Column(name = "praczdrowotne")
    private Double praczdrowotne;
    @Column(name = "praczdrowotnedodoliczenia")
    private Double praczdrowotnedodoliczenia;
    @Column(name = "praczdrowotnedopotracenia")
    private Double praczdrowotnedopotracenia;
    @Column(name = "praczdrowotnepomniejszone")
    private Double praczdrowotnepomniejszone;
    @Column(name = "rentowe")
    private Double rentowe;
    @Column(name = "wypadkowe")
    private Double wypadkowe;
    @Column(name = "podatekwstepny")
    private Double podatekwstepny;
    @Column(name = "podstawaubezpzdrowotne")
    private Double podstawaubezpzdrowotne;
    @Column(name = "potracenia")
    private Double potracenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen", orphanRemoval = true)
    private List<Naliczeniepotracenie> naliczeniepotracenieList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen", orphanRemoval = true)
    private List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen", orphanRemoval = true)
    private List<Naliczenienieobecnosc> naliczenienieobecnoscList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "definicjalistaplac", referencedColumnName = "id")
    @ManyToOne
    private Definicjalistaplac definicjalistaplac;
    @JoinColumn(name = "kalendarzmiesiac", referencedColumnName = "id")
    @ManyToOne
    private Kalendarzmiesiac kalendarzmiesiac;

    public Pasekwynagrodzen() {
        this.naliczeniepotracenieList = new ArrayList<>();
        this.naliczenieskladnikawynagrodzeniaList = new ArrayList<>();
        this.naliczenienieobecnoscList = new ArrayList<>();
    }

    public Pasekwynagrodzen(int id) {
        this.id = id;
        this.naliczeniepotracenieList = new ArrayList<>();
        this.naliczenieskladnikawynagrodzeniaList = new ArrayList<>();
        this.naliczenienieobecnoscList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @XmlTransient
    public List<Naliczeniepotracenie> getNaliczeniepotracenieList() {
        return naliczeniepotracenieList;
    }
    public void setNaliczeniepotracenieList(List<Naliczeniepotracenie> naliczeniepotracenieList) {
        this.naliczeniepotracenieList = naliczeniepotracenieList;
    }
    @XmlTransient
    public List<Naliczenieskladnikawynagrodzenia> getNaliczenieskladnikawynagrodzeniaList() {
        return naliczenieskladnikawynagrodzeniaList;
    }
    public void setNaliczenieskladnikawynagrodzeniaList(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList) {
        this.naliczenieskladnikawynagrodzeniaList = naliczenieskladnikawynagrodzeniaList;
    }
    @XmlTransient
    public List<Naliczenienieobecnosc> getNaliczenienieobecnoscList() {
        return naliczenienieobecnoscList;
    }
    public void setNaliczenienieobecnoscList(List<Naliczenienieobecnosc> naliczenienieobecnoscList) {
        this.naliczenienieobecnoscList = naliczenienieobecnoscList;
    }
    public Kalendarzmiesiac getKalendarzmiesiac() {
        return kalendarzmiesiac;
    }
    public void setKalendarzmiesiac(Kalendarzmiesiac kalendarzmiesiac) {
        this.kalendarzmiesiac = kalendarzmiesiac;
    }

    public Double getBruttobezzus() {
        return bruttobezzus;
    }

    public void setBruttobezzus(Double bruttobezzus) {
        this.bruttobezzus = bruttobezzus;
    }

    public Double getBruttozus() {
        return bruttozus;
    }

    public void setBruttozus(Double bruttozus) {
        this.bruttozus = bruttozus;
    }

    public Double getFgsp() {
        return fgsp;
    }

    public void setFgsp(Double fgsp) {
        this.fgsp = fgsp;
    }

    public Double getFp() {
        return fp;
    }

    public void setFp(Double fp) {
        this.fp = fp;
    }

    public Double getKosztyuzyskania() {
        return kosztyuzyskania;
    }

    public void setKosztyuzyskania(Double kosztyuzyskania) {
        this.kosztyuzyskania = kosztyuzyskania;
    }

    public Double getKwotawolna() {
        return kwotawolna;
    }

    public void setKwotawolna(Double kwotawolna) {
        this.kwotawolna = kwotawolna;
    }

    public Double getNetto() {
        return netto;
    }

    public void setNetto(Double netto) {
        this.netto = netto;
    }

    public Double getPodatekdochodowy() {
        return podatekdochodowy;
    }

    public void setPodatekdochodowy(Double podatekdochodowy) {
        this.podatekdochodowy = podatekdochodowy;
    }

    public Double getPodstawaopodatkowania() {
        return podstawaopodatkowania;
    }

    public void setPodstawaopodatkowania(Double podstawaopodatkowania) {
        this.podstawaopodatkowania = podstawaopodatkowania;
    }

    public Double getPracchorobowe() {
        return pracchorobowe;
    }

    public void setPracchorobowe(Double pracchorobowe) {
        this.pracchorobowe = pracchorobowe;
    }

    public Double getPracemerytalne() {
        return pracemerytalne;
    }

    public void setPracemerytalne(Double pracemerytalne) {
        this.pracemerytalne = pracemerytalne;
    }

    public Double getPracrentowe() {
        return pracrentowe;
    }

    public void setPracrentowe(Double pracrentowe) {
        this.pracrentowe = pracrentowe;
    }

    public Double getRazemspolecznepracownik() {
        return razemspolecznepracownik;
    }

    public void setRazemspolecznepracownik(Double razemspolecznepracownik) {
        this.razemspolecznepracownik = razemspolecznepracownik;
    }

    public Double getPraczdrowotne() {
        return praczdrowotne;
    }

    public void setPraczdrowotne(Double praczdrowotne) {
        this.praczdrowotne = praczdrowotne;
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

    public Double getPraczdrowotnepomniejszone() {
        return praczdrowotnepomniejszone;
    }

    public void setPraczdrowotnepomniejszone(Double praczdrowotnepomniejszone) {
        this.praczdrowotnepomniejszone = praczdrowotnepomniejszone;
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

    public Double getPodatekwstepny() {
        return podatekwstepny;
    }

    public void setPodatekwstepny(Double podatekwstepny) {
        this.podatekwstepny = podatekwstepny;
    }

    public Double getPodstawaubezpzdrowotne() {
        return podstawaubezpzdrowotne;
    }

    public void setPodstawaubezpzdrowotne(Double podstawaubezpzdrowotne) {
        this.podstawaubezpzdrowotne = podstawaubezpzdrowotne;
    }

    public Double getPotracenia() {
        return potracenia;
    }

    public void setPotracenia(Double potracenia) {
        this.potracenia = potracenia;
    }

    
        
}
