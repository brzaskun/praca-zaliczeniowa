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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import z.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kartawynagrodzen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kartawynagrodzen.findAll", query = "SELECT k FROM Kartawynagrodzen k"),
    @NamedQuery(name = "Kartawynagrodzen.findById", query = "SELECT k FROM Kartawynagrodzen k WHERE k.id = :id"),
    @NamedQuery(name = "Kartawynagrodzen.findByBruttobezzus", query = "SELECT k FROM Kartawynagrodzen k WHERE k.bruttobezzus = :bruttobezzus"),
    @NamedQuery(name = "Kartawynagrodzen.findByBruttozus", query = "SELECT k FROM Kartawynagrodzen k WHERE k.bruttozus = :bruttozus"),
    @NamedQuery(name = "Kartawynagrodzen.findByFgsp", query = "SELECT k FROM Kartawynagrodzen k WHERE k.fgsp = :fgsp"),
    @NamedQuery(name = "Kartawynagrodzen.findByFp", query = "SELECT k FROM Kartawynagrodzen k WHERE k.fp = :fp"),
    @NamedQuery(name = "Kartawynagrodzen.findByKosztyuzyskania", query = "SELECT k FROM Kartawynagrodzen k WHERE k.kosztyuzyskania = :kosztyuzyskania"),
    @NamedQuery(name = "Kartawynagrodzen.findByKwotawolna", query = "SELECT k FROM Kartawynagrodzen k WHERE k.kwotawolna = :kwotawolna"),
    @NamedQuery(name = "Kartawynagrodzen.findByNetto", query = "SELECT k FROM Kartawynagrodzen k WHERE k.netto = :netto"),
    @NamedQuery(name = "Kartawynagrodzen.findByPodatekdochodowy", query = "SELECT k FROM Kartawynagrodzen k WHERE k.podatekdochodowy = :podatekdochodowy"),
    @NamedQuery(name = "Kartawynagrodzen.findByPodstawaopodatkowania", query = "SELECT k FROM Kartawynagrodzen k WHERE k.podstawaopodatkowania = :podstawaopodatkowania"),
    @NamedQuery(name = "Kartawynagrodzen.findByPracchorobowe", query = "SELECT k FROM Kartawynagrodzen k WHERE k.pracchorobowe = :pracchorobowe"),
    @NamedQuery(name = "Kartawynagrodzen.findByPracemerytalne", query = "SELECT k FROM Kartawynagrodzen k WHERE k.pracemerytalne = :pracemerytalne"),
    @NamedQuery(name = "Kartawynagrodzen.findByPracrentowe", query = "SELECT k FROM Kartawynagrodzen k WHERE k.pracrentowe = :pracrentowe"),
    @NamedQuery(name = "Kartawynagrodzen.findByRazemspolecznepracownik", query = "SELECT k FROM Kartawynagrodzen k WHERE k.razemspolecznepracownik = :razemspolecznepracownik"),
    @NamedQuery(name = "Kartawynagrodzen.findByPraczdrowotne", query = "SELECT k FROM Kartawynagrodzen k WHERE k.praczdrowotne = :praczdrowotne"),
    @NamedQuery(name = "Kartawynagrodzen.findByPraczdrowotnedodoliczenia", query = "SELECT k FROM Kartawynagrodzen k WHERE k.praczdrowotnedodoliczenia = :praczdrowotnedodoliczenia"),
    @NamedQuery(name = "Kartawynagrodzen.findByPraczdrowotnedopotracenia", query = "SELECT k FROM Kartawynagrodzen k WHERE k.praczdrowotnedopotracenia = :praczdrowotnedopotracenia"),
    @NamedQuery(name = "Kartawynagrodzen.findByPraczdrowotnepomniejszone", query = "SELECT k FROM Kartawynagrodzen k WHERE k.praczdrowotnepomniejszone = :praczdrowotnepomniejszone"),
    @NamedQuery(name = "Kartawynagrodzen.findByEmerytalne", query = "SELECT k FROM Kartawynagrodzen k WHERE k.emerytalne = :emerytalne"),
    @NamedQuery(name = "Kartawynagrodzen.findByRentowe", query = "SELECT k FROM Kartawynagrodzen k WHERE k.rentowe = :rentowe"),
    @NamedQuery(name = "Kartawynagrodzen.findByWypadkowe", query = "SELECT k FROM Kartawynagrodzen k WHERE k.wypadkowe = :wypadkowe"),
    @NamedQuery(name = "Kartawynagrodzen.findByRazemspolecznefirma", query = "SELECT k FROM Kartawynagrodzen k WHERE k.razemspolecznefirma = :razemspolecznefirma"),
    @NamedQuery(name = "Kartawynagrodzen.findByPodatekwstepny", query = "SELECT k FROM Kartawynagrodzen k WHERE k.podatekwstepny = :podatekwstepny"),
    @NamedQuery(name = "Kartawynagrodzen.findByPodstawaubezpzdrowotne", query = "SELECT k FROM Kartawynagrodzen k WHERE k.podstawaubezpzdrowotne = :podstawaubezpzdrowotne"),
    @NamedQuery(name = "Kartawynagrodzen.findByPotracenia", query = "SELECT k FROM Kartawynagrodzen k WHERE k.potracenia = :potracenia"),
    @NamedQuery(name = "Kartawynagrodzen.findByRazem53", query = "SELECT k FROM Kartawynagrodzen k WHERE k.razem53 = :razem53"),
    @NamedQuery(name = "Kartawynagrodzen.findByKosztpracodawcy", query = "SELECT k FROM Kartawynagrodzen k WHERE k.kosztpracodawcy = :kosztpracodawcy"),
    @NamedQuery(name = "Kartawynagrodzen.findByRok", query = "SELECT k FROM Kartawynagrodzen k WHERE k.rok = :rok"),
    @NamedQuery(name = "Kartawynagrodzen.findByMc", query = "SELECT k FROM Kartawynagrodzen k WHERE k.mc = :mc")})
public class Kartawynagrodzen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "bruttobezzus")
    private double bruttobezzus;
    @Column(name = "bruttozus")
    private double bruttozus;
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
    @Column(name = "emerytalne")
    private double emerytalne;
    @Column(name = "rentowe")
    private double rentowe;
    @Column(name = "wypadkowe")
    private double wypadkowe;
    @Column(name = "razemspolecznefirma")
    private double razemspolecznefirma;
    @Column(name = "podatekwstepny")
    private double podatekwstepny;
    @Column(name = "podstawaubezpzdrowotne")
    private double podstawaubezpzdrowotne;
    @Column(name = "potracenia")
    private double potracenia;
    @Column(name = "razem53")
    private double razem53;
    @Column(name = "kosztpracodawcy")
    private double kosztpracodawcy;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Angaz angaz;

    public Kartawynagrodzen() {
    }

    public Kartawynagrodzen(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public double getEmerytalne() {
        return emerytalne;
    }

    public void setEmerytalne(double emerytalne) {
        this.emerytalne = emerytalne;
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

    public double getRazemspolecznefirma() {
        return razemspolecznefirma;
    }

    public void setRazemspolecznefirma(double razemspolecznefirma) {
        this.razemspolecznefirma = razemspolecznefirma;
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

    public double getRazem53() {
        return razem53;
    }

    public void setRazem53(double razem53) {
        this.razem53 = razem53;
    }

    public double getKosztpracodawcy() {
        return kosztpracodawcy;
    }

    public void setKosztpracodawcy(double kosztpracodawcy) {
        this.kosztpracodawcy = kosztpracodawcy;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    @XmlTransient
    public double getBrutto() {
        return Z.z(this.bruttozus+this.bruttobezzus);
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
        if (!(object instanceof Kartawynagrodzen)) {
            return false;
        }
        Kartawynagrodzen other = (Kartawynagrodzen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Kartawynagrodzen[ id=" + id + " ]";
    }

    public void zeruj() {
        this.bruttobezzus = 0.0;
        this.bruttozus = 0.0;
        this.fgsp = 0.0;
        this.fp = 0.0;
        this.kosztyuzyskania = 0.0;
        this.kwotawolna = 0.0;
        this.netto = 0.0;
        this.podatekdochodowy = 0.0;
        this.podstawaopodatkowania = 0.0;
        this.pracchorobowe = 0.0;
        this.pracemerytalne = 0.0;
        this.pracrentowe = 0.0;
        this.razemspolecznepracownik = 0.0;
        this.praczdrowotne = 0.0;
        this.praczdrowotnedodoliczenia = 0.0;
        this.praczdrowotnedopotracenia = 0.0;
        this.praczdrowotnepomniejszone = 0.0;
        this.emerytalne = 0.0;
        this.rentowe = 0.0;
        this.wypadkowe = 0.0;
        this.razemspolecznefirma = 0.0;
        this.podatekwstepny = 0.0;
        this.podstawaubezpzdrowotne = 0.0;
        this.potracenia = 0.0;
        this.razem53 = 0.0;
        this.kosztpracodawcy = 0.0;
    }


    public void dodaj(Pasekwynagrodzen pasek) {
        this.bruttobezzus += pasek.getBruttobezzus();
        this.bruttozus += pasek.getBruttozus();
        this.fgsp += pasek.getFgsp();
        this.fp += pasek.getFp();
        this.kosztyuzyskania += pasek.getKosztyuzyskania();
        this.kwotawolna += pasek.getKwotawolna();
        this.netto += pasek.getNetto();
        this.podatekdochodowy += pasek.getPodatekdochodowy();
        this.podstawaopodatkowania += pasek.getPodstawaopodatkowania();
        this.pracchorobowe += pasek.getPracchorobowe();
        this.pracemerytalne += pasek.getPracemerytalne();
        this.pracrentowe += pasek.getPracrentowe();
        this.razemspolecznepracownik += pasek.getRazemspolecznepracownik();
        this.praczdrowotne += pasek.getPraczdrowotne();
        this.praczdrowotnedodoliczenia += pasek.getPraczdrowotnedodoliczenia();
        this.praczdrowotnedopotracenia += pasek.getPraczdrowotnedopotracenia();
        this.praczdrowotnepomniejszone += pasek.getPraczdrowotnepomniejszone();
        this.emerytalne += pasek.getEmerytalne();
        this.rentowe += pasek.getRentowe();
        this.wypadkowe += pasek.getWypadkowe();
        this.razemspolecznefirma += pasek.getRazemspolecznefirma();
        this.podatekwstepny += pasek.getPodatekwstepny();
        this.podstawaubezpzdrowotne += pasek.getPodstawaubezpzdrowotne();
        this.potracenia += pasek.getPotracenia();
        this.razem53 += pasek.getRazem53();
        this.kosztpracodawcy += pasek.getKosztpracodawcy();
    }
    
}
