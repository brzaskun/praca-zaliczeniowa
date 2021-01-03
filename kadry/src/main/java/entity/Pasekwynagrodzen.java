/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
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
    @Column(name = "bruttobezzus")
private double bruttobezzus;
    @Column(name = "bruttozus")
    private double bruttozus;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fgsp")
    private double fgsp;
    @Column(name = "fp")
    private double fp;
    @Column(name = "kosztyuzyskania")
    private double kosztyuzyskania;
    @Column(name = "kwotawolna")
    private double kwotawolna;
    @Column(name = "netto")
    private double netto;
    @Column(name = "podatekdochodowy")
    private double podatekdochodowy;
    @Column(name = "podstawaopodatkowania")
    private double podstawaopodatkowania;
    @Column(name = "pracchorobowe")
    private double pracchorobowe;
    @Column(name = "pracemerytalne")
    private double pracemerytalne;
    @Column(name = "pracrentowe")
    private double pracrentowe;
    @Column(name = "razemspolecznepracownik")
    private double razemspolecznepracownik;
    @Column(name = "praczdrowotne")
    private double praczdrowotne;
    @Column(name = "praczdrowotnedodoliczenia")
    private double praczdrowotnedodoliczenia;
    @Column(name = "praczdrowotnedopotracenia")
    private double praczdrowotnedopotracenia;
    @Column(name = "praczdrowotnepomniejszone")
    private double praczdrowotnepomniejszone;
    @Column(name = "rentowe")
    private double rentowe;
    @Column(name = "wypadkowe")
    private double wypadkowe;
    @Column(name = "podatekwstepny")
    private double podatekwstepny;
    @Column(name = "podstawaubezpzdrowotne")
    private double podstawaubezpzdrowotne;
    @Column(name = "potracenia")
    private double potracenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen")
    private List<Naliczeniepotracenie> naliczeniepotracenieList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen")
    private List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen")
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
    @OneToOne(mappedBy = "pasekwynagrodzen")
    private Kalendarzmiesiac kalendarzmiesiac;

    public Pasekwynagrodzen() {
    }

    public Pasekwynagrodzen(int id) {
        this.id = id;
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

    public double getBruttobezzus() {
        return bruttobezzus;
    }

    public void setBruttobezzus(double bruttobezzus) {
        this.bruttobezzus = bruttobezzus;
    }

    public double getBruttozus() {
        return bruttozus;
    }

    public void setBruttozus(double bruttozus) {
        this.bruttozus = bruttozus;
    }

    public double getFgsp() {
        return fgsp;
    }

    public void setFgsp(double fgsp) {
        this.fgsp = fgsp;
    }

    public double getFp() {
        return fp;
    }

    public void setFp(double fp) {
        this.fp = fp;
    }

    public double getKosztyuzyskania() {
        return kosztyuzyskania;
    }

    public void setKosztyuzyskania(double kosztyuzyskania) {
        this.kosztyuzyskania = kosztyuzyskania;
    }

    public double getKwotawolna() {
        return kwotawolna;
    }

    public void setKwotawolna(double kwotawolna) {
        this.kwotawolna = kwotawolna;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getPodatekdochodowy() {
        return podatekdochodowy;
    }

    public void setPodatekdochodowy(double podatekdochodowy) {
        this.podatekdochodowy = podatekdochodowy;
    }

    public double getPodstawaopodatkowania() {
        return podstawaopodatkowania;
    }

    public void setPodstawaopodatkowania(double podstawaopodatkowania) {
        this.podstawaopodatkowania = podstawaopodatkowania;
    }

    public double getPracchorobowe() {
        return pracchorobowe;
    }

    public void setPracchorobowe(double pracchorobowe) {
        this.pracchorobowe = pracchorobowe;
    }

    public double getPracemerytalne() {
        return pracemerytalne;
    }

    public void setPracemerytalne(double pracemerytalne) {
        this.pracemerytalne = pracemerytalne;
    }

    public double getPracrentowe() {
        return pracrentowe;
    }

    public void setPracrentowe(double pracrentowe) {
        this.pracrentowe = pracrentowe;
    }

    public double getRazemspolecznepracownik() {
        return razemspolecznepracownik;
    }

    public void setRazemspolecznepracownik(double razemspolecznepracownik) {
        this.razemspolecznepracownik = razemspolecznepracownik;
    }

    public double getPraczdrowotne() {
        return praczdrowotne;
    }

    public void setPraczdrowotne(double praczdrowotne) {
        this.praczdrowotne = praczdrowotne;
    }

    public double getPraczdrowotnedodoliczenia() {
        return praczdrowotnedodoliczenia;
    }

    public void setPraczdrowotnedodoliczenia(double praczdrowotnedodoliczenia) {
        this.praczdrowotnedodoliczenia = praczdrowotnedodoliczenia;
    }

    public double getPraczdrowotnedopotracenia() {
        return praczdrowotnedopotracenia;
    }

    public void setPraczdrowotnedopotracenia(double praczdrowotnedopotracenia) {
        this.praczdrowotnedopotracenia = praczdrowotnedopotracenia;
    }

    public double getPraczdrowotnepomniejszone() {
        return praczdrowotnepomniejszone;
    }

    public void setPraczdrowotnepomniejszone(double praczdrowotnepomniejszone) {
        this.praczdrowotnepomniejszone = praczdrowotnepomniejszone;
    }

    public double getRentowe() {
        return rentowe;
    }

    public void setRentowe(double rentowe) {
        this.rentowe = rentowe;
    }

    public double getWypadkowe() {
        return wypadkowe;
    }

    public void setWypadkowe(double wypadkowe) {
        this.wypadkowe = wypadkowe;
    }

    public double getPodatekwstepny() {
        return podatekwstepny;
    }

    public void setPodatekwstepny(double podatekwstepny) {
        this.podatekwstepny = podatekwstepny;
    }

    public double getPodstawaubezpzdrowotne() {
        return podstawaubezpzdrowotne;
    }

    public void setPodstawaubezpzdrowotne(double podstawaubezpzdrowotne) {
        this.podstawaubezpzdrowotne = podstawaubezpzdrowotne;
    }

    public double getPotracenia() {
        return potracenia;
    }

    public void setPotracenia(double potracenia) {
        this.potracenia = potracenia;
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

    
        
}
