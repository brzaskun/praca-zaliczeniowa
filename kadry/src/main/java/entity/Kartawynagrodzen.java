/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import z.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kartawynagrodzen", uniqueConstraints = {
    @UniqueConstraint(columnNames={"angaz","rok", "mc"})
})
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
    @NamedQuery(name = "Kartawynagrodzen.findByPraczdrowotnedodoliczenia", query = "SELECT k FROM Kartawynagrodzen k WHERE k.praczdrowotnedoodliczenia = :praczdrowotnedoodliczenia"),
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
    @NamedQuery(name = "Kartawynagrodzen.findByAngazRok", query = "SELECT k FROM Kartawynagrodzen k WHERE k.rok = :rok AND k.angaz = :angaz"),
    @NamedQuery(name = "Kartawynagrodzen.findByMc", query = "SELECT k FROM Kartawynagrodzen k WHERE k.mc = :mc")})
public class Kartawynagrodzen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "brutto")
    private double brutto;
    @Column(name = "bruttobezzus")
    private double bruttobezzus;
    @Column(name = "bruttobezpodatku")
    private double bruttobezpodatku;
    @Column(name = "bruttozus")
    private double bruttozus;
    @Column(name = "bruttodo26lat")
    private double bruttodo26lat;
    @Column(name = "bruttopowyzej26lat")
    private double bruttopowyzej26lat;
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
    @Column(name = "podstawaopodatkowanianarast")
    private double podstawaopodatkowanianarast;
    @Column(name = "podstawaubezpieczenspolecznych")
    private double podstawaubezpieczenspolecznych;
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
    @Column(name = "praczdrowotnedoodliczenia")
    private double praczdrowotnedoodliczenia;
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
    @Column(name = "komornik")
    private double komornik;
    @Column(name = "potraceniainne")
    private double potraceniainne;
    @Column(name = "razem53")
    private double razem53;
    @Column(name = "kosztpracodawcy")
    private double kosztpracodawcy;
    @Column(name = "dochodzagranica")
    private double dochodzagranica;
    @Column(name = "dochodzagranicado26")
    private double dochodzagranicado26;
    @Column(name = "dochodzagranicapo26")
    private double dochodzagranicapo26;
    @Column(name = "dochodpolska")
    private double dochodpolska;
    @Column(name = "okresprzekroczenie26")
    private String okresprzekroczenie26;
    @Column(name = "podstawaopodatkowaniazagranica")
    private double podstawaopodatkowaniazagranica;
    @Column(name = "podatekdochodowyzagranica")
    private double podatekdochodowyzagranica;
    @Column(name = "dietaodliczeniepodstawaop")
    private double dietaodliczeniepodstawaop;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Angaz angaz;
    @Column(name = "nrlisty")
    private String nrlisty;
    @Column(name = "kosztypodwyzszone")
    private boolean kosztypodwyzszone;
    @Column(name = "kosztywieleumow")
    private boolean kosztywieleumow;
    @Column(name = "przekroczeniedni")
    private boolean przekroczeniedni;
    @Column
    private int wieklata;
    @Column(name="przekroczeniekorektapodstawypolska")
    private double przekroczeniekorektapodstawypolska;
    @Column(name="przekroczenienowypodatek")
    private double przekroczenienowypodatek;
    @Column(name="przekroczeniepodstawaniemiecka")
    private double przekroczeniepodstawaniemiecka;
    @Column(name="przekroczeniepodatekniemiecki")
    private double przekroczeniepodatekniemiecki;
    @Column(name = "przekroczeniekosztyuzyskania")
    private double przekroczeniekosztyuzyskania;
    @Column(name = "spoleczneudzialpolska")
    private double spoleczneudzialpolska;
    @Transient
    private String nazwiskoiimie;
    @Transient
    private Map<String,Kartawynagrodzen> sumy;
    @Transient
    private List<Pasekwynagrodzen> paski;
    @Transient
    private Set<String> pesele;
    @Transient
    private boolean jestPIT11;
    @Transient
    private boolean wyslano;
    
    public Kartawynagrodzen() {
        this.nrlisty = "";
        this.paski = new ArrayList<>();
        this.pesele = new HashSet<>();
    }

    public Kartawynagrodzen(Integer id) {
        this.id = id;
        this.nrlisty = "";
        this.paski = new ArrayList<>();
        this.pesele = new HashSet<>();
    }

    
    public boolean isEmeryt() {
        boolean zwrot = false;
        if (this.angaz!=null&&this.angaz.getPracownik().getPlec()!=null) {
            if (this.angaz.getPracownik().getPlec().equals("M")) {
                if (this.wieklata>64) {
                    zwrot = true;
                }
            } else if (this.angaz.getPracownik().getPlec().equals("K")) {
                if (this.wieklata>59) {
                    zwrot = true;
                }
            }
        }
        return zwrot;
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

    public double getDietaodliczeniepodstawaop() {
        return dietaodliczeniepodstawaop;
    }

    public void setDietaodliczeniepodstawaop(double dietaodliczeniepodstawaop) {
        this.dietaodliczeniepodstawaop = dietaodliczeniepodstawaop;
    }

    public double getPodstawaopodatkowanianarast() {
        return podstawaopodatkowanianarast;
    }

    public void setPodstawaopodatkowanianarast(double podstawaopodatkowanianarast) {
        this.podstawaopodatkowanianarast = podstawaopodatkowanianarast;
    }

    public double getBruttobezpodatku() {
        return bruttobezpodatku;
    }

    public void setBruttobezpodatku(double bruttobezpodatku) {
        this.bruttobezpodatku = bruttobezpodatku;
    }

    public double getPrzekroczeniekorektapodstawypolska() {
        return przekroczeniekorektapodstawypolska;
    }

    public void setPrzekroczeniekorektapodstawypolska(double przekroczeniekorektapodstawypolska) {
        this.przekroczeniekorektapodstawypolska = przekroczeniekorektapodstawypolska;
    }

    public double getPrzekroczenienowypodatek() {
        return przekroczenienowypodatek;
    }

    public void setPrzekroczenienowypodatek(double przekroczenienowypodatek) {
        this.przekroczenienowypodatek = przekroczenienowypodatek;
    }

    public double getPrzekroczeniepodstawaniemiecka() {
        return przekroczeniepodstawaniemiecka;
    }

    public void setPrzekroczeniepodstawaniemiecka(double przekroczeniepodstawaniemiecka) {
        this.przekroczeniepodstawaniemiecka = przekroczeniepodstawaniemiecka;
    }

    public double getPrzekroczeniepodatekniemiecki() {
        return przekroczeniepodatekniemiecki;
    }

    public void setPrzekroczeniepodatekniemiecki(double przekroczeniepodatekniemiecki) {
        this.przekroczeniepodatekniemiecki = przekroczeniepodatekniemiecki;
    }

    public double getPrzekroczeniekosztyuzyskania() {
        return przekroczeniekosztyuzyskania;
    }

    public void setPrzekroczeniekosztyuzyskania(double przekroczeniekosztyuzyskania) {
        this.przekroczeniekosztyuzyskania = przekroczeniekosztyuzyskania;
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

    
    public double getDochod() {
        return this.getBrutto()-this.kosztyuzyskania;
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

    public double getPraczdrowotnedoodliczenia() {
        return praczdrowotnedoodliczenia;
    }

    public void setPraczdrowotnedoodliczenia(double praczdrowotnedoodliczenia) {
        this.praczdrowotnedoodliczenia = praczdrowotnedoodliczenia;
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

    public double getBrutto() {
        double zwrot = brutto-bruttobezpodatku;
        if (przekroczeniedni) {
            zwrot = dochodpolska-bruttobezpodatku;
        }
        return Z.z(zwrot);
    }
    
    public double getBruttoMinusDieta() {
        double zwrot = getBrutto();
        if (przekroczeniedni==false) {
            zwrot = Z.z(zwrot-dietaodliczeniepodstawaop);
        }
        return zwrot;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    

    public String getNrlisty() {
        return nrlisty;
    }

    public void setNrlisty(String nrlisty) {
        this.nrlisty = nrlisty;
    }

    public boolean isKosztypodwyzszone() {
        return kosztypodwyzszone;
    }

    public void setKosztypodwyzszone(boolean kosztypodwyzszone) {
        this.kosztypodwyzszone = kosztypodwyzszone;
    }

    public boolean isKosztywieleumow() {
        return kosztywieleumow;
    }

    public void setKosztywieleumow(boolean kosztywieleumow) {
        this.kosztywieleumow = kosztywieleumow;
    }

    public String getNazwiskoiimie() {
        return nazwiskoiimie;
    }

    public void setNazwiskoiimie(String nazwiskoiimie) {
        this.nazwiskoiimie = nazwiskoiimie;
    }

    public Map<String, Kartawynagrodzen> getSumy() {
        return sumy;
    }

    public void setSumy(Map<String, Kartawynagrodzen> sumy) {
        this.sumy = sumy;
    }

    public List<Pasekwynagrodzen> getPaski() {
        return paski;
    }

    public void setPaski(List<Pasekwynagrodzen> paski) {
        this.paski = paski;
    }

    public Set<String> getPesele() {
        return pesele;
    }

    public void setPesele(Set<String> pesele) {
        this.pesele = pesele;
    }

    public boolean isJestPIT11() {
        return jestPIT11;
    }

    public void setJestPIT11(boolean jestPIT11) {
        this.jestPIT11 = jestPIT11;
    }

    public int getWieklata() {
        return wieklata;
    }

    public void setWieklata(int wieklata) {
        this.wieklata = wieklata;
    }

    public boolean isWyslano() {
        return wyslano;
    }

    public void setWyslano(boolean wyslano) {
        this.wyslano = wyslano;
    }

    public boolean isPrzekroczeniedni() {
        return przekroczeniedni;
    }

    public void setPrzekroczeniedni(boolean przekroczeniedni) {
        this.przekroczeniedni = przekroczeniedni;
    }

    public double getDochodzagranica() {
        return dochodzagranica;
    }

    public void setDochodzagranica(double dochodzagranica) {
        this.dochodzagranica = dochodzagranica;
    }

    public double getDochodzagranicado26() {
        return dochodzagranicado26;
    }

    public void setDochodzagranicado26(double dochodzagranicado26) {
        this.dochodzagranicado26 = dochodzagranicado26;
    }

    public double getDochodzagranicapo26() {
        return dochodzagranicapo26;
    }

    public void setDochodzagranicapo26(double dochodzagranicapo26) {
        this.dochodzagranicapo26 = dochodzagranicapo26;
    }
    

    public double getPodstawaubezpieczenspolecznych() {
        return podstawaubezpieczenspolecznych;
    }

    public void setPodstawaubezpieczenspolecznych(double podstawaubezpieczenspolecznych) {
        this.podstawaubezpieczenspolecznych = podstawaubezpieczenspolecznych;
    }

    public double getKomornik() {
        return komornik;
    }

    public void setKomornik(double komornik) {
        this.komornik = komornik;
    }

    public double getPotraceniainne() {
        return potraceniainne;
    }

    public void setPotraceniainne(double potraceniainne) {
        this.potraceniainne = potraceniainne;
    }

    public double getBruttodo26lat() {
        return bruttodo26lat;
    }

    public void setBruttodo26lat(double bruttodo26lat) {
        this.bruttodo26lat = bruttodo26lat;
    }

    public double getBruttopowyzej26lat() {
        return bruttopowyzej26lat;
    }

    public void setBruttopowyzej26lat(double bruttopowyzej26lat) {
        this.bruttopowyzej26lat = bruttopowyzej26lat;
    }

    public String getOkresprzekroczenie26() {
        return okresprzekroczenie26;
    }

    public void setOkresprzekroczenie26(String okresprzekroczenie26) {
        this.okresprzekroczenie26 = okresprzekroczenie26;
    }

    public double getPodstawaopodatkowaniazagranica() {
        return podstawaopodatkowaniazagranica;
    }

    public void setPodstawaopodatkowaniazagranica(double podstawaopodatkowaniazagranica) {
        this.podstawaopodatkowaniazagranica = podstawaopodatkowaniazagranica;
    }

    public double getPodatekdochodowyzagranica() {
        return podatekdochodowyzagranica;
    }

    public void setPodatekdochodowyzagranica(double podatekdochodowyzagranica) {
        this.podatekdochodowyzagranica = podatekdochodowyzagranica;
    }

    public double getDochodpolska() {
        return dochodpolska;
    }

    public void setDochodpolska(double dochodpolska) {
        this.dochodpolska = dochodpolska;
    }

    public double getDochodpolskaMinusdieta() {
        return Z.z(this.dochodpolska-this.dietaodliczeniepodstawaop);
    }

    public double getSpoleczneudzialpolska() {
        return spoleczneudzialpolska;
    }

    public void setSpoleczneudzialpolska(double spoleczneudzialpolska) {
        this.spoleczneudzialpolska = spoleczneudzialpolska;
    }
    
    public double getDochodpitpolska() {
        return Z.z(getDochodpolskaMinusdieta()-this.kosztyuzyskania);
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
        if (angaz!=null) {
            return "Kartawynagrodzen{prac=" + angaz.getPracownik().getNazwiskoImie() + " rok  "+rok +", mc=" + mc  +"brutto=" + brutto +"bruttobezzus=" + bruttobezzus + 
                    ", bruttozus=" + bruttozus + ", kosztyuzyskania=" + kosztyuzyskania + ", kwotawolna=" + kwotawolna + ", netto=" + netto + ", podatekdochodowy=" + podatekdochodowy + ", razemspolecznepracownik=" + razemspolecznepracownik + ", praczdrowotne=" + praczdrowotne + ", kosztypodwyzszone=" + kosztypodwyzszone + ", kosztywieleumow=" + kosztywieleumow + '}';
        } else {
            return "Kartawynagrodzen{prac= rok  "+rok +", mc=" + mc  + "brutto=" + brutto + "bruttobezzus=" + bruttobezzus + ", bruttozus=" + bruttozus + ", kosztyuzyskania=" + kosztyuzyskania +
                    ", kwotawolna=" + kwotawolna + ", netto=" + netto + ", podatekdochodowy=" + podatekdochodowy + ", razemspolecznepracownik=" + razemspolecznepracownik + ", praczdrowotne=" + praczdrowotne + ", kosztypodwyzszone=" + kosztypodwyzszone + ", kosztywieleumow=" + kosztywieleumow + '}';
        }
    }

    

    public void zeruj() {
        this.brutto = 0.0;
        this.bruttobezzus = 0.0;
        this.bruttobezpodatku = 0.0;
        this.bruttozus = 0.0;
        this.bruttodo26lat = 0.0;
        this.bruttopowyzej26lat = 0.0;
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
        this.praczdrowotnedoodliczenia = 0.0;
        this.praczdrowotnedopotracenia = 0.0;
        this.praczdrowotnepomniejszone = 0.0;
        this.emerytalne = 0.0;
        this.rentowe = 0.0;
        this.wypadkowe = 0.0;
        this.razemspolecznefirma = 0.0;
        this.podatekwstepny = 0.0;
        this.podstawaubezpzdrowotne = 0.0;
        this.podstawaubezpieczenspolecznych = 0.0;
        this.potracenia = 0.0;
        this.razem53 = 0.0;
        this.kosztpracodawcy = 0.0;
        this.dochodzagranica = 0.0;
        this.dochodzagranicado26 = 0.0;
        this.dochodzagranicapo26 = 0.0;
        this.nrlisty = null;
        this.kosztypodwyzszone = false;
        this.kosztywieleumow = false;
        this.dochodpolska = 0.0;
        this.dietaodliczeniepodstawaop =0.0;
        this.spoleczneudzialpolska = 0.0;
    }
    
    //tutaj
    public void dodaj(Pasekwynagrodzen pasek) {
        String mc = pasek.getMc();
        int mcI = Integer.parseInt(mc);
//        double nieopodatkowane = 0.0;
//        for (Naliczenieskladnikawynagrodzenia skl : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
//            if (skl.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isPodatek0bezpodatek1()) {
//                nieopodatkowane = nieopodatkowane+skl.getKwotadolistyplac();
//            }
//        }
//        this.brutto = this.brutto-nieopodatkowane;
        //łatka bo byla rewolucja od 112023 w sumowaniu skladnikow 05.01.2024 Broniwoja 5 :) a oni graja
        if (pasek.getRok().equals("2023")&&mcI<11) {
            this.brutto = Z.z(this.brutto+pasek.getBrutto());
            this.bruttobezzus = Z.z(this.bruttobezzus+pasek.getBruttobezzus()+pasek.getBruttobezspolecznych());
            this.bruttobezpodatku = Z.z(this.bruttobezpodatku+pasek.getBruttobezzusbezpodatek());
            this.bruttozus = Z.z(this.bruttozus+pasek.getBruttozus()+pasek.getBruttozusbezpodatek());
        } else {
            this.brutto = Z.z(this.brutto+pasek.getBrutto());
            this.bruttobezzus = Z.z(this.bruttobezzus+pasek.getBrutto()-pasek.getPrzychodyzus51());
            this.bruttobezpodatku = Z.z(this.bruttobezpodatku+pasek.getBruttobezzusbezpodatek());
            this.bruttozus = Z.z(this.bruttozus+pasek.getPrzychodyzus51());
        }
//        this.bruttodo26lat = Z.z(this.bruttodo26lat+pasek.getBruttobezzusbezpodatek()+pasek.getBruttozusbezpodatek()+pasek.getBruttobezspolecznych());
//        this.bruttopowyzej26lat = Z.z(this.bruttopowyzej26lat+(pasek.getBrutto()-(pasek.getBruttobezzusbezpodatek()+pasek.getBruttozusbezpodatek()+pasek.getBruttobezspolecznych())));
        this.fgsp += pasek.getFgsp();
        this.fp += pasek.getFp();
        this.kosztyuzyskania = Z.z(this.kosztyuzyskania+pasek.getKosztyuzyskania());
        this.kwotawolna += pasek.getKwotawolna();
        this.netto += pasek.getNetto();
        if (pasek.isPrzekroczenieoddelegowanie()) {
            this.podatekdochodowy = Z.z(this.podatekdochodowy+pasek.getPrzekroczenienowypodatek());
        } else {
            this.podatekdochodowy = Z.z(this.podatekdochodowy+pasek.getPodatekdochodowy());
        }
        this.podstawaopodatkowania += pasek.getPodstawaopodatkowania();
        this.pracchorobowe += pasek.getPracchorobowe();
        this.pracemerytalne += pasek.getPracemerytalne();
        this.pracrentowe += pasek.getPracrentowe();
        this.razemspolecznepracownik = Z.z(this.razemspolecznepracownik+pasek.getRazemspolecznepracownik());
        this.praczdrowotne += pasek.getPraczdrowotne();
        this.praczdrowotnedoodliczenia = Z.z(this.praczdrowotnedoodliczenia+pasek.getPraczdrowotnedoodliczenia());
        this.praczdrowotnedopotracenia = Z.z(this.praczdrowotnedopotracenia+pasek.getPraczdrowotnedopotracenia());
        this.przekroczeniekorektapodstawypolska = Z.z(this.przekroczeniekorektapodstawypolska+pasek.getPrzekroczeniekorektapodstawypolska());
        this.przekroczenienowypodatek = Z.z(this.przekroczenienowypodatek+pasek.getPrzekroczenienowypodatek());
        this.przekroczeniepodstawaniemiecka = Z.z(this.przekroczeniepodstawaniemiecka+pasek.getPrzekroczeniepodstawaniemiecka());
        this.przekroczeniepodatekniemiecki = Z.z(this.przekroczeniepodatekniemiecki+pasek.getPrzekroczeniepodatekniemiecki());
        this.przekroczeniekosztyuzyskania = Z.z(this.przekroczeniekosztyuzyskania+pasek.getPrzekroczeniekosztyuzyskania());
        this.emerytalne += pasek.getEmerytalne();
        this.rentowe += pasek.getRentowe();
        this.wypadkowe += pasek.getWypadkowe();
        this.razemspolecznefirma += pasek.getRazemspolecznefirma();
        this.podatekwstepny += pasek.getPodatekwstepny();
        this.podstawaubezpzdrowotne += pasek.getPodstawaubezpzdrowotne();
        this.podstawaubezpieczenspolecznych += pasek.getPodstawaskladkizus();
        this.potracenia += pasek.getPotracenia();
        this.komornik += pasek.getPotraceniaKomornik();
        this.potraceniainne += pasek.getPotraceniaInne();
        this.razem53 += pasek.getRazem53();
        this.kosztpracodawcy += pasek.getKosztpracodawcy();
        this.podstawaopodatkowaniazagranica += pasek.getPodstawaopodatkowaniazagranica();
        this.podatekdochodowyzagranica += pasek.getPodatekdochodowyzagranica();
        if (pasek.isDo26lat()==false) {
            this.okresprzekroczenie26 = pasek.getOkresWypl();
        }
        //this.mc!=null musi byc bo uzywamy tego tez do pit-11
        if (pasek.getDefinicjalistaplac().getNrkolejny()!=null && this.mc!=null && !this.mc.equals("razem")) {
            if (this.nrlisty==null) {
                this.nrlisty = pasek.getDefinicjalistaplac().getNrkolejny()+";";
            } else {
                this.nrlisty = this.nrlisty+pasek.getDefinicjalistaplac().getNrkolejny()+";";
            }
        }
        if (this.mc!=null&&!this.mc.equals("razem")) {
            this.paski.add(pasek);
        }
        this.pesele.add(pasek.getPesel());
        this.przekroczeniedni = pasek.isPrzekroczenieoddelegowanie();
        //to jest po staremu jak bralismy dane z superplac
        //teraz o tym czy jest rpzekroczenie jest info w pasku 1.12.2023 Sitges niedziela poranek przy kawie :)
//        List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList = pasek.getNaliczenieskladnikawynagrodzeniaList();
//        double niemcy = 0.0;
//        if (this.angaz!=null&&this.angaz.getPrzekroczenierok()!=null) {
//            for (Naliczenieskladnikawynagrodzenia p : naliczenieskladnikawynagrodzeniaList) {
//                //double zus = p.getKwotadolistyplac()<5922.0?p.getKwotadolistyplac()*.1371:5922.0*.1371;
//                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("13")) {
//                    niemcy = Z.z(niemcy + (p.getKwotadolistyplac()));
//                    this.setPrzekroczeniedni(true);
//                }
//            }
//        }
        this.dochodzagranica = this.dochodzagranica + Z.z(pasek.getPrzychodypodatekzagranica());
        this.dochodzagranicado26 = this.dochodzagranicado26 + Z.z(pasek.getPrzychodypodatekzagranicado26());
        this.dochodzagranicapo26 = this.dochodzagranicapo26 + Z.z(pasek.getPrzychodypodatekzagranicapo26());
        this.dochodpolska = this.dochodpolska + Z.z(pasek.getPrzychodypodatekpolska());
        this.dietaodliczeniepodstawaop = this.dietaodliczeniepodstawaop + Z.z(pasek.getDietaodliczeniepodstawaop());
        this.spoleczneudzialpolska = this.spoleczneudzialpolska + Z.z(pasek.getSpoleczneudzialpolska());
        
    }
    
     public void dodajkarta(Kartawynagrodzen kartawynagrodzen) {
        this.bruttobezzus = Z.z(this.bruttobezzus+kartawynagrodzen.getBruttobezzus());
        this.bruttobezpodatku = Z.z(this.bruttobezpodatku+kartawynagrodzen.getBruttobezpodatku());
        this.bruttozus = Z.z(this.bruttozus+kartawynagrodzen.getBruttozus());
        this.bruttodo26lat = Z.z(this.bruttodo26lat+kartawynagrodzen.getBruttodo26lat());
        this.bruttopowyzej26lat = Z.z(this.bruttopowyzej26lat+kartawynagrodzen.getBruttopowyzej26lat());
        this.kosztyuzyskania = Z.z(this.kosztyuzyskania+kartawynagrodzen.getKosztyuzyskania());
        this.kwotawolna += kartawynagrodzen.getKwotawolna();
        this.netto += kartawynagrodzen.getNetto();
        this.podatekdochodowy = Z.z(this.podatekdochodowy+kartawynagrodzen.getPodatekdochodowy());
        this.podstawaopodatkowania += kartawynagrodzen.getPodstawaopodatkowania();
        this.podstawaubezpieczenspolecznych += kartawynagrodzen.getPodstawaubezpieczenspolecznych();
        this.pracchorobowe += kartawynagrodzen.getPracchorobowe();
        this.pracemerytalne += kartawynagrodzen.getPracemerytalne();
        this.pracrentowe += kartawynagrodzen.getPracrentowe();
        this.razemspolecznepracownik = Z.z(this.razemspolecznepracownik+kartawynagrodzen.getRazemspolecznepracownik());
        this.praczdrowotne += kartawynagrodzen.getPraczdrowotne();
        this.przekroczeniekorektapodstawypolska = Z.z(this.przekroczeniekorektapodstawypolska+kartawynagrodzen.getPrzekroczeniekorektapodstawypolska());
        this.przekroczenienowypodatek = Z.z(this.przekroczenienowypodatek+kartawynagrodzen.getPrzekroczenienowypodatek());
        this.przekroczeniepodstawaniemiecka = Z.z(this.przekroczeniepodstawaniemiecka+kartawynagrodzen.getPrzekroczeniepodstawaniemiecka());
        this.przekroczeniepodatekniemiecki = Z.z(this.przekroczeniepodatekniemiecki+kartawynagrodzen.getPrzekroczeniepodatekniemiecki());
        this.przekroczeniekosztyuzyskania = Z.z(this.przekroczeniekosztyuzyskania+kartawynagrodzen.getPrzekroczeniekosztyuzyskania());
        this.praczdrowotnedoodliczenia += kartawynagrodzen.getPraczdrowotnedoodliczenia();
        this.praczdrowotnedopotracenia = Z.z(this.praczdrowotnedopotracenia+kartawynagrodzen.getPraczdrowotnedopotracenia());
        this.praczdrowotnepomniejszone += kartawynagrodzen.getPraczdrowotnepomniejszone();
        this.emerytalne += kartawynagrodzen.getEmerytalne();
        this.rentowe += kartawynagrodzen.getRentowe();
        this.wypadkowe += kartawynagrodzen.getWypadkowe();
        this.razemspolecznefirma += kartawynagrodzen.getRazemspolecznefirma();
        this.podatekwstepny += kartawynagrodzen.getPodatekwstepny();
        this.podstawaubezpzdrowotne += kartawynagrodzen.getPodstawaubezpzdrowotne();
        this.potracenia += kartawynagrodzen.getPotracenia();
        this.komornik += kartawynagrodzen.getKomornik();
        this.potraceniainne += kartawynagrodzen.getPotraceniainne();
        this.razem53 += kartawynagrodzen.getRazem53();
        this.dochodzagranica += kartawynagrodzen.getDochodzagranica();
        this.dochodzagranicado26 += kartawynagrodzen.getDochodzagranicado26();
        this.dochodzagranicapo26 += kartawynagrodzen.getDochodzagranicapo26();
        this.dochodpolska += kartawynagrodzen.getDochodpolska();
        this.dietaodliczeniepodstawaop = this.dietaodliczeniepodstawaop + Z.z(kartawynagrodzen.getDietaodliczeniepodstawaop());
        this.spoleczneudzialpolska = this.spoleczneudzialpolska + Z.z(kartawynagrodzen.getSpoleczneudzialpolska());
        this.przekroczeniedni = kartawynagrodzen.przekroczeniedni;
             //this.mc!=null musi byc bo uzywamy tego tez do pit-11
     
     }

    
    
}
