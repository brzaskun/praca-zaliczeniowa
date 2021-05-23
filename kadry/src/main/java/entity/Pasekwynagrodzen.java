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
import kadryiplace.Place;
import kadryiplace.PlaceSkl;
import z.Z;

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
    @NamedQuery(name = "Pasekwynagrodzen.findByDef", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.definicjalistaplac = :definicjalistaplac"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokAngaz", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.kalendarzmiesiac.rok = :rok AND p.kalendarzmiesiac.umowa.angaz = :angaz"),
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "brutto")
    private double brutto;
    @Column(name = "bruttobezzus")
    private double bruttobezzus;
    @Column(name = "bruttozus")
    private double bruttozus;
    @Column(name = "bruttobezzusbezpodatek")
    private double bruttobezzusbezpodatek;
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
     @Column(name = "dietastawka")
    private double dietastawka;
    @Column(name = "dietaodliczeniepodstawaop")
    private double dietaodliczeniepodstawaop;
    @Column(name = "dieta")
    private double dieta;
    @Column(name = "kurs")
    private double kurs;
    @Column(name = "limitzus")
    private double limitzus;
    @Column(name = "dniobowiazku")
    private Integer dniobowiazku;
    @Column(name = "dniprzepracowane")
    private Integer dniprzepracowane;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen", orphanRemoval = true)
    private List<Naliczeniepotracenie> naliczeniepotracenieList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen", orphanRemoval = true)
    private List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen", orphanRemoval = true)
    private List<Naliczenienieobecnosc> naliczenienieobecnoscList;
    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "definicjalistaplac", referencedColumnName = "id")
    @ManyToOne
    private Definicjalistaplac definicjalistaplac;
    @JoinColumn(name = "kalendarzmiesiac", referencedColumnName = "id")
    @ManyToOne
    private Kalendarzmiesiac kalendarzmiesiac;
    @Column(name="rok")
    private String rok;
    @Column(name="mc")
    private String mc;
    @Column(name="importowany")
    private boolean importowany;

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

    public Pasekwynagrodzen(Place r) {
        List<PlaceSkl> placeSklList = r.getPlaceSklList();
        if (placeSklList!=null) {
            for (PlaceSkl p: placeSklList) {
                String doch = String.valueOf(p.getSklWksSerial().getWksPodDoch());
                String zus = String.valueOf(p.getSklWksSerial().getWksZus());
                if (doch.equals("N")&&zus.equals("N")) {
                    this.bruttobezzusbezpodatek = Z.z(this.bruttobezzusbezpodatek+p.getSklKwota().doubleValue());
                } else if (zus.equals("N")) {
                    this.bruttobezzus = Z.z(this.bruttobezzus+p.getSklKwota().doubleValue());
                } else {
                    this.bruttozus = Z.z(this.bruttozus+p.getSklKwota().doubleValue());
                }
            }
        }
        this.brutto = this.bruttobezzus+this.bruttozus+this.bruttobezzusbezpodatek;
        this.fgsp = r.getLplFgspPrac().doubleValue();
        this.fp = r.getLplFpPrac().doubleValue();
        this.kosztyuzyskania = r.getLplKoszty().doubleValue();
        this.kwotawolna = r.getLplZalWolna().doubleValue();
        this.netto = r.getLplKwotaDod2().doubleValue();
        this.podatekdochodowy = r.getLplZalDoch().doubleValue();
        this.podstawaopodatkowania = r.getLplPdstPodDoch().doubleValue();
        this.pracchorobowe = r.getLplChorUbez().doubleValue();
        this.pracemerytalne = r.getLplEmerUbez().doubleValue();
        this.pracrentowe = r.getLplRentUbez().doubleValue();
        this.razemspolecznepracownik = r.getLplPodZusKw().doubleValue();
        this.praczdrowotne = r.getLplZdroUbez().doubleValue();
        this.praczdrowotnedodoliczenia = 0.0;
        this.praczdrowotnedopotracenia = r.getLplPodZdroKw().doubleValue();
        this.praczdrowotnepomniejszone = this.praczdrowotne-this.praczdrowotnedopotracenia;
        this.emerytalne = r.getLplEmerPrac().doubleValue();
        this.rentowe = r.getLplRentPrac().doubleValue();
        this.wypadkowe = r.getLplWypPrac().doubleValue();
        this.razemspolecznefirma = this.emerytalne+this.rentowe+this.wypadkowe;
        this.podatekwstepny = r.getLplKwotaDod6().doubleValue();
        this.podstawaubezpzdrowotne = r.getLplPdstZdrowotne().doubleValue();
        this.potracenia = r.getLplPotracenia().doubleValue();
        this.razem53 = this.fp+this.fgsp;
        this.kosztpracodawcy = this.bruttozus+this.bruttobezzus+this.razemspolecznefirma;
        this.dietastawka = 0.0;
        this.dietaodliczeniepodstawaop = 0.0;
        this.dieta = 0.0;
        this.kurs = 0.0;
        this.limitzus = 0.0;
        this.dniobowiazku = r.getLplDniObow().intValue();
        this.dniprzepracowane = r.getLplDniPrzepr().intValue();
    }
    
    public void dodajPasek(Pasekwynagrodzen p) {
        this.bruttobezzusbezpodatek = this.bruttobezzusbezpodatek +p.bruttobezzusbezpodatek;
        this.bruttozus = this.bruttozus + p.bruttozus;
        this.brutto = this.brutto + p.brutto;
        this.fgsp = this.fgsp + p.fgsp;
        this.fp = this.fp + p.fp;
        this.bruttobezzus = this.bruttobezzus + p.bruttobezzus;
        this.podatekdochodowy = this.podatekdochodowy + p.podatekdochodowy;
        this.pracchorobowe = this.pracchorobowe + p.pracchorobowe;
        this.pracemerytalne = this.pracemerytalne + p.pracemerytalne;
        this.pracrentowe = this.pracrentowe + p.pracrentowe;
        this.praczdrowotne = this.praczdrowotne + p.praczdrowotne;
        this.emerytalne = this.emerytalne + p.emerytalne;
        this.rentowe = this.rentowe + p.rentowe;
        this.wypadkowe = this.wypadkowe + p.wypadkowe;
        this.razemspolecznepracownik = this.razemspolecznepracownik + p.razemspolecznepracownik;
        this.razemspolecznefirma = this.razemspolecznefirma + p.razemspolecznefirma;
        this.podstawaubezpzdrowotne = this.podstawaubezpzdrowotne + p.podstawaubezpzdrowotne;
        this.razem53 = this.razem53 + p.razem53;
        this.kosztpracodawcy = this.kosztpracodawcy + p.kosztpracodawcy;
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
        if ((this.id == null && other.id == null) && (!this.kalendarzmiesiac.equals(other.kalendarzmiesiac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pasekwynagrodzen{" + "kalendarzmiesiac=" + kalendarzmiesiac.getNazwiskoImie() + '}';
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

    public String getNazwiskoImie() {
        return this.kalendarzmiesiac.getUmowa().getAngaz().getPracownik().getNazwiskoImie();
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

    public double getDietaodliczeniepodstawaop() {
        return dietaodliczeniepodstawaop;
    }

    public void setDietaodliczeniepodstawaop(double dietaodliczeniepodstawaop) {
        this.dietaodliczeniepodstawaop = dietaodliczeniepodstawaop;
    }

    public double getDietastawka() {
        return dietastawka;
    }

    public void setDietastawka(double dietastawka) {
        this.dietastawka = dietastawka;
    }

    public double getDieta() {
        return dieta;
    }

    public void setDieta(double dieta) {
        this.dieta = dieta;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public double getLimitzus() {
        return limitzus;
    }

    public void setLimitzus(double limitzus) {
        this.limitzus = limitzus;
    }

    public Integer getDniobowiazku() {
        return dniobowiazku;
    }

    public void setDniobowiazku(Integer dniobowiazku) {
        this.dniobowiazku = dniobowiazku;
    }

    public Integer getDniprzepracowane() {
        return dniprzepracowane;
    }

    public void setDniprzepracowane(Integer dniprzepracowane) {
        this.dniprzepracowane = dniprzepracowane;
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

    public boolean isImportowany() {
        return importowany;
    }

    public void setImportowany(boolean importowany) {
        this.importowany = importowany;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public double getBruttobezzusbezpodatek() {
        return bruttobezzusbezpodatek;
    }

    public void setBruttobezzusbezpodatek(double bruttobezzusbezpodatek) {
        this.bruttobezzusbezpodatek = bruttobezzusbezpodatek;
    }

   
    

    
    
    
}
