/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import beanstesty.Pasekpomocnik;
import data.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import kadryiplace.Place;
import z.Z;

/**
 *
 * @author Osito
 */@Entity
@Table(name = "pasekwynagrodzen", uniqueConstraints = {
    @UniqueConstraint(columnNames={"kalendarzmiesiac", "definicjalistaplac", "lpl_serial"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pasekwynagrodzen.findAll", query = "SELECT p FROM Pasekwynagrodzen p"),
    @NamedQuery(name = "Pasekwynagrodzen.findByDefKal", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.definicjalistaplac = :definicjalistaplac AND p.kalendarzmiesiac = :kalendarzmiesiac"),
    @NamedQuery(name = "Pasekwynagrodzen.findByDef", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.definicjalistaplac = :definicjalistaplac"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRok", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rok = :rok"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokWypl", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rokwypl = :rok"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokMc", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rok = :rok and  p.mc = :mc"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokAngaz", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rok = :rok AND p.kalendarzmiesiac.angaz = :angaz"),
    @NamedQuery(name = "Pasekwynagrodzen.findByAngaz", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.kalendarzmiesiac.angaz = :angaz"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokWyplAngaz", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rokwypl = :rok AND p.kalendarzmiesiac.angaz = :angaz"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokMcAngaz", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rok = :rok AND p.mc = :mc AND p.kalendarzmiesiac.angaz = :angaz"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokMcFirma", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rok = :rok AND p.mc = :mc AND p.kalendarzmiesiac.angaz.firma = :firma"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokFirma", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rok = :rok AND p.kalendarzmiesiac.angaz.firma = :firma"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokWyplFirma", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rokwypl = :rok AND p.kalendarzmiesiac.angaz.firma = :firma"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokMcNip", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rok = :rok AND p.mc = :mc AND p.kalendarzmiesiac.angaz.firma.nip = :nip"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRokMcWyplAngaz", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rokwypl = :rok AND p.mcwypl = :mc AND p.kalendarzmiesiac.angaz = :angaz"),
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
    @NamedQuery(name = "Pasekwynagrodzen.findByPraczdrowotnedoodliczenia", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.praczdrowotnedoodliczenia = :praczdrowotnedoodliczenia"),
    @NamedQuery(name = "Pasekwynagrodzen.findByPraczdrowotnedopotracenia", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.praczdrowotnedopotracenia = :praczdrowotnedopotracenia"),
    @NamedQuery(name = "Pasekwynagrodzen.findByRentowe", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.rentowe = :rentowe"),
    @NamedQuery(name = "Pasekwynagrodzen.findByWypadkowe", query = "SELECT p FROM Pasekwynagrodzen p WHERE p.wypadkowe = :wypadkowe")})
public class Pasekwynagrodzen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "brutto")
    private double brutto;
    @Column(name = "bruttobezzus")
    private double bruttobezzus;
    @Column(name = "bruttobezspolecznych")
    private double bruttobezspolecznych;
    @Column(name = "bruttozus")
    private double bruttozus;
    @Column(name = "bruttozusbezpodatek")
    private double bruttozusbezpodatek;
    //informacyjne, kwota jest w brutto
    @Column(name = "bruttozuskraj")
    private double bruttozuskraj;
    @Column(name = "oddelegowaniewaluta")
    private double oddelegowaniewaluta;
    //informacyjne, kwota jest w brutto
    @Column(name = "oddelegowaniepln")
    private double oddelegowaniepln;
    @Column(name = "oddelegowaniewalutasymbol")
    private String oddelegowaniewalutasymbol;
    @Column(name = "bruttobezzusbezpodatek")
    private double bruttobezzusbezpodatek;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fgsp")
    private double fgsp;
    @Column(name = "fp")
    private double fp;
    @Column(name = "kosztyuzyskania")
    private double kosztyuzyskania;
    @Column(name = "kosztyuzyskaniahipotetyczne")
    private double kosztyuzyskaniahipotetyczne;
    @Column(name = "procentkosztow")
    private double procentkosztow;
    @Column(name = "kwotawolna")
    private double kwotawolna;
    @Column(name = "kwotawolnanalezna")
    private double kwotawolnanalezna;
    @Column(name = "kwotawolnahipotetyczna")
    private double kwotawolnahipotetyczna;
    @Column(name = "kwotawolnadlazdrowotnej")
    private double kwotawolnadlazdrowotnej;
    @Column(name = "nettoprzedpotraceniami")
    private double nettoprzedpotraceniami;
    @Column(name = "nettoprzedpotraceniamisafe")
    private double nettoprzedpotraceniamisafe;
    @Column(name = "netto")
    private double netto;
    @Column(name = "nettowaluta")
    private double nettowaluta;
    @Column(name = "symbolwaluty")
    private String symbolwaluty;
    @Column(name = "podatekdochodowy")
    private double podatekdochodowy;
    @Column(name = "podatekdochodowyzagranica")
    private double podatekdochodowyzagranica;
    @Column(name = "podstawaopodatkowaniazagranica")
    private double podstawaopodatkowaniazagranica;
    @Column(name = "podatekdochodowyzagranicawaluta")
    private double podatekdochodowyzagranicawaluta;
    @Column(name = "podstawaopodatkowaniazagranicawaluta")
    private double podstawaopodatkowaniazagranicawaluta;
    @Column(name = "podstawaopodatkowania")
    private double podstawaopodatkowania;
    @Column(name = "podstawaopodatkowaniahipotetyczna")
    private double podstawaopodatkowaniahipotetyczna;
    @Column(name = "podstawaskladkizus")
    private double podstawaskladkizus;
    @Column(name = "podstawaskladkizusemerytalna")
    private double podstawaskladkizusemerytalna;
    @Column(name = "podstawaskladkizusemerytalnaograniczona")
    private double podstawaskladkizusemerytalnaograniczona;
    @Column(name = "podstawaskladkizusrentowa")
    private double podstawaskladkizusrentowa;
    @Column(name = "podstawaskladkizusrentowaograniczona")
    private double podstawaskladkizusrentowaograniczona;
    @Column(name = "korektapodstawaskladkizus")
    private boolean korektapodstawaskladkizus;
    @Column(name = "podstawaskladkizuschorobowa")
    private double podstawaskladkizuschorobowa;
    @Column(name = "podstawaskladkizuswypadkowa")
    private double podstawaskladkizuswypadkowa;
    @Column(name = "pracchorobowe")
    private double pracchorobowe;
    @Column(name = "pracemerytalne")
    private double pracemerytalne;
    @Column(name = "pracrentowe")
    private double pracrentowe;
    @Column(name = "razemspolecznepracownik")
    private double razemspolecznepracownik;
    @Column(name = "spolecznedoodliczeniakierowca")
    private double spolecznedoodliczeniakierowca;
    @Column(name = "bruttominusspoleczne")
    private double bruttominusspoleczne;
    @Column(name = "bruttominusspolecznehipotetyczne")
    private double bruttominusspolecznehipotetyczne;
    @Column(name = "praczdrowotne")
    private double praczdrowotne;
    @Column(name = "praczdrowotnedoodliczenia")
    private double praczdrowotnedoodliczenia;
    @Column(name = "praczdrowotnedopotracenia")
    private double praczdrowotnedopotracenia;
//    @Column(name = "praczdrowotnepomniejszone")
//    private double praczdrowotnepomniejszone;
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
    @Column(name = "podatekwstepnyhipotetyczny")
    private double podatekwstepnyhipotetyczny;
    @Column(name = "podstawaubezpzdrowotne")
    private double podstawaubezpzdrowotne;
    @Column(name = "potracenia")
    private double potracenia;
    @Column(name = "komornik")
    private double komornik;
    @Column(name = "razem53")
    private double razem53;
    @Column(name = "kosztpracodawcy")
    private double kosztpracodawcy;
    @Column(name = "dietaodliczeniepodstawaop")
    private double dietaodliczeniepodstawaop;
    @Column(name = "dieta")
    private double dieta;
    @Column(name = "dietawaluta")
    private double dietawaluta;
    @Column(name = "kurs")
    private double kurs;
    @Column(name = "limitzus")
    private double limitzus;
    @Column(name = "limitzuspoza")
    private double limitzuspoza;
    @Column(name = "dniobowiazku")
    private Integer dniobowiazku;
    @Column(name = "dniprzepracowane")
    private Integer dniprzepracowane;
    @Column(name = "godzinyobowiazku")
    private Integer godzinyobowiazku;
    @Column(name = "godzinyprzepracowane")
    private Integer godzinyprzepracowane;
    @JoinColumn(name = "definicjalistaplac", referencedColumnName = "id")
    @ManyToOne
    private Definicjalistaplac definicjalistaplac;
    @JoinColumn(name = "kalendarzmiesiac", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Kalendarzmiesiac kalendarzmiesiac;
    @Column(name="rok")
    private String rok;
    @Column(name="mc")
    private String mc;
    @Column(name="rokwypl")
    private String rokwypl;
    @Column(name="mcwypl")
    private String mcwypl;
    @Size(max = 10)
    @Column(name="datawyplaty")
    private String datawyplaty;
    @Size(max = 10)
    @Column(name="datawysylki")
    private String datawysylki;
    @Column(name="importowany")
    private boolean importowany;
    @Column(name="przekroczenieoddelegowanie")
    private boolean przekroczenieoddelegowanie;
    @Column(name="emeryt")
    private boolean emeryt;
    @Column(name="sporzadzil")
    private String sporzadzil;
    @Column(name = "lata")
    private int lata;
    @Column(name = "miesiace")
    private int miesiace;
    @Column(name = "dni")
    private int dni;
    @Column(name="do26lat")
    private boolean do26lat;
    @Column(name="drugiprog")
    private boolean drugiprog;
    @Column(name="wynagrodzenieminimalne")
    private double wynagrodzenieminimalne;
    @Column(name = "nierezydent")
    private boolean nierezydent;
    @Column(name = "student")
    private boolean student;
    @Column(name = "lis_tyt_serial")
    private Integer lis_tyt_serial;
    @Column(name = "lpl_serial")
    private Integer lpl_serial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Naliczeniepotracenie> naliczeniepotracenieList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasekwynagrodzen", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Naliczenienieobecnosc> naliczenienieobecnoscList;
    @Transient
    private int numerator;
    @Transient
    private boolean zmieniony;
    @Column(name = "ulgadlaklasysredniejI")
    private double ulgadlaklasysredniejI;
    @Column(name = "ulgadlaklasysredniejII")
    private double ulgadlaklasysredniejII;
    @Column(name = "przychodzagranicasuperplace")
    private double przychodzagranicasuperplace;
    @Column(name = "wolneodzajecia")
    private double wolneodzajecia;
    @Column(name = "wolneodzajeciazasilek")
    private double wolneodzajeciazasilek;
    @Column(name = "fpprzekroczeniewiek")
    private boolean fpprzekroczeniewiek;
    @Column(name = "fppowrotmacierzynski")
    private boolean fppowrotmacierzynski;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data")
    private Date data;
    @Column(name = "podstawaprzedkorektaozagranice")
    private double podstawaprzedkorektaozagranice;
    @Column(name = "spoleczneudzialpolska")
    private double spoleczneudzialpolska;
    @Column(name = "spoleczneudzialoddelegowanie")
    private double spoleczneudzialoddelegowanie;
    @Column(name = "przychodypodatekpolska")
    private double przychodypodatekpolska;
    @Column(name = "przychodypodatekzagranica")
    private double przychodypodatekzagranica;
    @Column(name = "przychodyzus51")
    private double przychodyzus51;
    @Column(name = "przychodyzus51Polska")
    private double przychodyzus51Polska;
    @Column(name = "przychodyzus52")
    private double przychodyzus52;
    @Column(name="przekroczenie26lat")
    private double przekroczenie26lat;
    @Column(name="przekroczeniekorektapodstawypolska")
    private double przekroczeniekorektapodstawypolska;
    @Column(name="przekroczenienowypodatek")
    private double przekroczenienowypodatek;
    @Column(name="przekroczenienowazdrowotnadoodliczenia")
    private double przekroczenienowazdrowotnadoodliczenia;
    @Column(name="przekroczeniepodstawaniemiecka")
    private double przekroczeniepodstawaniemiecka;
    @Column(name="przekroczeniepodatekniemiecki")
    private double przekroczeniepodatekniemiecki;
    @Column(name = "przekroczeniekosztyuzyskania")
    private double przekroczeniekosztyuzyskania;
    @Transient
    private double pracchoroboweOddelegowanie;
    @Transient
    private double pracemerytalneOddelegowanie;
    @Transient
    private double pracrentoweOddelegowanie;
    @Transient
    private double praczdrowotneOddelegowanie;
    @Transient
    private double emerytalneOddelegowanie;
    @Transient
    private double rentoweOddelegowanie;
    @Transient
    private double pracchoroboweOddelegowanieEuro;
    @Transient
    private double pracemerytalneOddelegowanieEuro;
    @Transient
    private double pracrentoweOddelegowanieEuro;
    @Transient
    private double praczdrowotneOddelegowanieEuro;
    @Transient
    private double emerytalneOddelegowanieEuro;
    @Transient
    private double rentoweOddelegowanieEuro;
    @Transient
    private double liczbapaskow;
    
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "ekwiwalentskladniki", referencedColumnName = "id")
//    private EkwiwalentUrlop ekwiwalentSkladniki;

    
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

   
    public static Pasekwynagrodzen pasekuzupelnianie(Pasekwynagrodzen nowy, Place r, String datakonca26lat) {
        nowy.podstawaskladkizus = Z.z(r.getLplPdstChor().doubleValue());
        nowy.fgsp = Z.z(r.getLplFgspPrac().doubleValue());
        nowy.fp = Z.z(r.getLplFpPrac().doubleValue());
        nowy.kosztyuzyskania = Z.z(r.getLplKoszty().doubleValue());
        nowy.kwotawolna = Z.z(r.getLplZalWolna().doubleValue());
        nowy.podatekdochodowy = Z.z(r.getLplZalDoch().doubleValue());
        nowy.podstawaopodatkowania = Z.z(r.getLplPdstPodDoch().doubleValue());
        nowy.pracchorobowe = Z.z(r.getLplChorUbez().doubleValue());
        nowy.pracemerytalne = Z.z(r.getLplEmerUbez().doubleValue());
        nowy.pracrentowe = Z.z(r.getLplRentUbez().doubleValue());
        nowy.razemspolecznepracownik = Z.z(nowy.pracemerytalne+nowy.pracrentowe+nowy.pracchorobowe);
        nowy.praczdrowotne = Z.z(r.getLplZdroUbez().doubleValue());
        nowy.praczdrowotnedoodliczenia = Z.z(r.getLplPodZdroKw().doubleValue());
        nowy.praczdrowotnedopotracenia = Z.z(nowy.praczdrowotne-nowy.praczdrowotnedoodliczenia);
        nowy.emerytalne = Z.z(r.getLplEmerPrac().doubleValue());
        nowy.rentowe = Z.z(r.getLplRentPrac().doubleValue());
        nowy.wypadkowe = Z.z(r.getLplWypPrac().doubleValue());
        nowy.razemspolecznefirma = Z.z(nowy.emerytalne+nowy.rentowe+nowy.wypadkowe);
        nowy.podatekwstepny = Z.z(r.getLplKwotaDod6().doubleValue());
        nowy.podstawaubezpzdrowotne = Z.z(r.getLplPdstZdrowotne().doubleValue());
        nowy.potracenia = Z.z(r.getLplPotracenia().doubleValue());
        nowy.razem53 = Z.z(nowy.fp+nowy.fgsp);
        nowy.brutto = nowy.brutto+nowy.bruttobezzus + nowy.bruttozus + nowy.bruttobezzusbezpodatek;
        nowy.bruttominusspoleczne = Z.z(nowy.brutto-nowy.razemspolecznepracownik);
        nowy.nettoprzedpotraceniami = Z.z(nowy.brutto-nowy.razemspolecznepracownik-nowy.praczdrowotnedoodliczenia-nowy.praczdrowotnedopotracenia-nowy.podatekdochodowy);
        nowy.netto = Z.z(nowy.nettoprzedpotraceniami-r.getLplPotracenia().doubleValue());
        nowy.kosztpracodawcy = Z.z(nowy.brutto+nowy.razemspolecznefirma);
        nowy.dietaodliczeniepodstawaop = 0.0;
        nowy.dieta = 0.0;
        nowy.kurs = 0.0;
        nowy.limitzus = 0.0;
        nowy.setDatawyplaty(Data.data_yyyyMMddNull(r.getLplDataWyplaty()));
        nowy.rokwypl = Data.getRok(nowy.datawyplaty);
        nowy.mcwypl = Data.getMc(nowy.datawyplaty);
        if (nowy.getRok().equals("2020")||nowy.getRok().equals("2021")||nowy.getRok().equals("2022")) {
            boolean po26roku = Data.czyjestpo(datakonca26lat, nowy.getRokwypl(), nowy.getMcwypl());
            if (po26roku==false) {
                nowy.do26lat = true;
            } else {
                nowy.do26lat = false;
            }
        } else {
            if (r.getLplChar10()!=null) {
                if (r.getLplChar10().equals('Y')) {
                    nowy.do26lat = true;
                } else {
                    nowy.do26lat = false;
                }
            }
        }
        nowy.dniobowiazku = r.getLplDniObow().intValue();
        nowy.dniprzepracowane = r.getLplDniPrzepr().intValue();
        nowy.lis_tyt_serial = r.getLplLisSerial().getLisTytSerial().getTytSerial();
        nowy.lpl_serial = r.getLplSerial();
        return nowy;
    }

    public Pasekwynagrodzen(String rok, String mc) {
        this.rok = rok;
        this.mc = mc;
        this.rokwypl = rok;
        this.mcwypl = mc;
    }
    
    public void dodajPasek(Pasekwynagrodzen p) {
        this.rok = p.getRok();
        this.mc = p.getMc();
        this.rokwypl = p.getRokwypl();
        this.mcwypl = p.getMcwypl();
        this.bruttobezzusbezpodatek = Z.z(this.bruttobezzusbezpodatek +p.bruttobezzusbezpodatek);
        this.bruttobezspolecznych = Z.z(this.bruttobezspolecznych +p.bruttobezspolecznych);
        this.bruttozus = Z.z(this.bruttozus + p.bruttozus);
        this.bruttozuskraj = Z.z(this.bruttozuskraj + p.bruttozuskraj);
        this.bruttobezzus = Z.z(this.bruttobezzus + p.bruttobezzus);
        this.oddelegowaniewaluta = Z.z(this.oddelegowaniewaluta + p.oddelegowaniewaluta);
        this.oddelegowaniepln = Z.z(this.oddelegowaniepln + p.oddelegowaniepln);
        this.oddelegowaniewalutasymbol = p.oddelegowaniewalutasymbol;
        this.brutto = Z.z(this.brutto + p.brutto);
        this.fgsp = Z.z(this.fgsp + p.fgsp);
        this.fp = Z.z(this.fp + p.fp);
        this.bruttominusspoleczne = Z.z(this.bruttominusspoleczne + p.bruttominusspoleczne);
        this.dieta = Z.z(this.dieta + p.dieta);
        this.dietaodliczeniepodstawaop = Z.z(this.dietaodliczeniepodstawaop + p.dietaodliczeniepodstawaop);
        this.podstawaskladkizus = Z.z(this.podstawaskladkizus + p.podstawaskladkizus);
        this.podstawaskladkizusemerytalna = Z.z(this.podstawaskladkizusemerytalna + p.podstawaskladkizusemerytalna);
        this.podstawaskladkizusrentowa = Z.z(this.podstawaskladkizusrentowa + p.podstawaskladkizusrentowa);
        this.podstawaskladkizuschorobowa = Z.z(this.podstawaskladkizuschorobowa + p.podstawaskladkizuschorobowa);
        this.podstawaskladkizuswypadkowa = Z.z(this.podstawaskladkizuswypadkowa + p.podstawaskladkizuswypadkowa);
        this.pracchorobowe = Z.z(this.pracchorobowe + p.pracchorobowe);
        this.pracemerytalne = Z.z(this.pracemerytalne + p.pracemerytalne);
        this.pracrentowe = Z.z(this.pracrentowe + p.pracrentowe);
        this.praczdrowotne = Z.z(this.praczdrowotne + p.praczdrowotne);
        this.emerytalne = Z.z(this.emerytalne + p.emerytalne);
        this.rentowe = Z.z(this.rentowe + p.rentowe);
        this.wypadkowe = Z.z(this.wypadkowe + p.wypadkowe);
        this.razemspolecznepracownik = Z.z(this.razemspolecznepracownik + p.razemspolecznepracownik);
        this.razemspolecznefirma = Z.z(this.razemspolecznefirma + p.razemspolecznefirma);
        this.podstawaubezpzdrowotne = Z.z(this.podstawaubezpzdrowotne + p.podstawaubezpzdrowotne);
        this.razem53 = Z.z(this.razem53 + p.razem53);
        this.kosztpracodawcy = Z.z(this.kosztpracodawcy + p.kosztpracodawcy);
        this.kosztyuzyskania = Z.z(this.kosztyuzyskania+p.getKosztyuzyskania());
        this.kwotawolna = Z.z(this.kwotawolna+p.getKwotawolna());
        this.kwotawolnanalezna = Z.z(this.kwotawolnanalezna+p.getKwotawolnanalezna());
        this.kwotawolnadlazdrowotnej = Z.z(this.kwotawolnadlazdrowotnej+p.getKwotawolnadlazdrowotnej());
        this.kwotawolnahipotetyczna = Z.z(this.kwotawolnahipotetyczna+p.getKwotawolnahipotetyczna());
        this.podatekdochodowy = Z.z(this.podatekdochodowy+p.getPodatekdochodowy());
        this.podstawaopodatkowania = Z.z(this.podstawaopodatkowania+p.getPodstawaopodatkowania());
        this.praczdrowotnedoodliczenia = Z.z(this.praczdrowotnedoodliczenia+p.getPraczdrowotnedoodliczenia());
        this.praczdrowotnedopotracenia = Z.z(this.praczdrowotnedopotracenia+p.getPraczdrowotnedopotracenia());
        this.podatekwstepny = Z.z(this.podatekwstepny+p.getPodatekwstepny());
        this.potracenia = Z.z(this.potracenia+p.getPotracenia());
        this.nettoprzedpotraceniami = Z.z(this.nettoprzedpotraceniami+p.getNettoprzedpotraceniami());
        this.netto = Z.z(this.netto+p.getNetto());
        this.limitzus = Z.z(this.limitzus+p.getLimitzus());
        this.ulgadlaklasysredniejI = Z.z(this.ulgadlaklasysredniejI+p.getUlgadlaklasysredniejI());
        this.ulgadlaklasysredniejII = Z.z(this.ulgadlaklasysredniejII+p.getUlgadlaklasysredniejII());
        if (this.naliczenieskladnikawynagrodzeniaList == null) {
            this.naliczenieskladnikawynagrodzeniaList = new ArrayList<>();
        } else {
            this.naliczenieskladnikawynagrodzeniaList.addAll(p.getNaliczenieskladnikawynagrodzeniaList());
        }
        this.podatekdochodowyzagranicawaluta = Z.z(this.podatekdochodowyzagranicawaluta+p.getPodatekdochodowyzagranicawaluta());
        this.podatekdochodowyzagranica = Z.z(this.podatekdochodowyzagranica+p.getPodatekdochodowyzagranica());
        this.podstawaopodatkowaniazagranicawaluta = Z.z(this.podstawaopodatkowaniazagranicawaluta+p.getPodstawaopodatkowaniazagranicawaluta());
        this.podstawaopodatkowaniazagranica = Z.z(this.podstawaopodatkowaniazagranica+p.getPodstawaopodatkowaniazagranica());
        this.przekroczeniekorektapodstawypolska = Z.z(this.przekroczeniekorektapodstawypolska+p.getPrzekroczeniekorektapodstawypolska());
        this.przekroczenienowypodatek = Z.z(this.przekroczenienowypodatek)+p.getPrzekroczenienowypodatek();
        this.przekroczeniepodstawaniemiecka = Z.z(this.przekroczeniepodstawaniemiecka+p.getPrzekroczeniepodstawaniemiecka());
        this.przekroczeniepodatekniemiecki = Z.z(this.przekroczeniepodatekniemiecki+p.getPrzekroczeniepodatekniemiecki());
        this.przekroczeniekosztyuzyskania = Z.z(this.przekroczeniekosztyuzyskania+p.getPrzekroczeniekosztyuzyskania());
        this.przychodypodatekpolska = Z.z(this.przychodypodatekpolska)+p.getPrzychodypodatekpolska();
        this.przychodypodatekzagranica  = Z.z(this.przychodypodatekzagranica)+p.getPrzychodypodatekzagranica();
        this.przychodyzus51 = Z.z(this.przychodyzus51+p.getPrzychodyzus51());
        this.przychodyzus51Polska = Z.z(this.przychodyzus51Polska+p.getPrzychodyzus51Polska());
        this.przychodyzus52 = Z.z(this.przychodyzus51+p.getPrzychodyzus52());
        this.spoleczneudzialpolska = Z.z(this.spoleczneudzialpolska+p.getSpoleczneudzialpolska());
        this.spoleczneudzialoddelegowanie = Z.z(this.spoleczneudzialoddelegowanie+p.getSpoleczneudzialoddelegowanie());
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

    public double getPodstawaskladkizusemerytalna() {
        return podstawaskladkizusemerytalna;
    }

    public void setPodstawaskladkizusemerytalna(double podstawaskladkizusemerytalna) {
        this.podstawaskladkizusemerytalna = podstawaskladkizusemerytalna;
    }

    public double getPodstawaskladkizusrentowa() {
        return podstawaskladkizusrentowa;
    }

    public void setPodstawaskladkizusrentowa(double podstawaskladkizusrentowa) {
        this.podstawaskladkizusrentowa = podstawaskladkizusrentowa;
    }

    public double getPodstawaskladkizuschorobowa() {
        return podstawaskladkizuschorobowa;
    }

    public void setPodstawaskladkizuschorobowa(double podstawaskladkizuschorobowa) {
        this.podstawaskladkizuschorobowa = podstawaskladkizuschorobowa;
    }

    public double getPodstawaskladkizuswypadkowa() {
        return podstawaskladkizuswypadkowa;
    }

    public void setPodstawaskladkizuswypadkowa(double podstawaskladkizuswypadkowa) {
        this.podstawaskladkizuswypadkowa = podstawaskladkizuswypadkowa;
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

    public double getPrzekroczenienowazdrowotnadoodliczenia() {
        return przekroczenienowazdrowotnadoodliczenia;
    }

    public void setPrzekroczenienowazdrowotnadoodliczenia(double przekroczenienowazdrowotnadoodliczenia) {
        this.przekroczenienowazdrowotnadoodliczenia = przekroczenienowazdrowotnadoodliczenia;
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

    public double getSpoleczneudzialoddelegowanie() {
        return spoleczneudzialoddelegowanie;
    }

    public void setSpoleczneudzialoddelegowanie(double spoleczneudzialoddelegowanie) {
        this.spoleczneudzialoddelegowanie = spoleczneudzialoddelegowanie;
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

    public boolean isPrzekroczenieoddelegowanie() {
        return przekroczenieoddelegowanie;
    }

    public void setPrzekroczenieoddelegowanie(boolean przekroczenieoddelegowanie) {
        this.przekroczenieoddelegowanie = przekroczenieoddelegowanie;
    }

    public double getWolneodzajecia() {
        return wolneodzajecia;
    }

    public void setWolneodzajecia(double wolneodzajecia) {
        this.wolneodzajecia = wolneodzajecia;
    }

    public double getKomornik() {
        return komornik;
    }

    public void setKomornik(double komornik) {
        this.komornik = komornik;
    }

    public double getBruttominusspolecznehipotetyczne() {
        return bruttominusspolecznehipotetyczne;
    }

    public void setBruttominusspolecznehipotetyczne(double bruttominusspolecznehipotetyczne) {
        this.bruttominusspolecznehipotetyczne = bruttominusspolecznehipotetyczne;
    }

    public double getKwotawolnanalezna() {
        return kwotawolnanalezna;
    }

    public void setKwotawolnanalezna(double kwotawolnanalezna) {
        this.kwotawolnanalezna = kwotawolnanalezna;
    }

    
    public boolean isEmeryt() {
        return emeryt;
    }

    public void setEmeryt(boolean emeryt) {
        this.emeryt = emeryt;
    }
    

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public double getNettoprzedpotraceniamisafe() {
        return nettoprzedpotraceniamisafe;
    }

    public void setNettoprzedpotraceniamisafe(double nettoprzedpotraceniamisafe) {
        this.nettoprzedpotraceniamisafe = nettoprzedpotraceniamisafe;
    }

    public double getPodstawaopodatkowaniazagranica() {
        return podstawaopodatkowaniazagranica;
    }

    public void setPodstawaopodatkowaniazagranica(double podstawaopodatkowaniazagranica) {
        this.podstawaopodatkowaniazagranica = podstawaopodatkowaniazagranica;
    }

    public double getPodatekdochodowyzagranicawaluta() {
        return podatekdochodowyzagranicawaluta;
    }

    public void setPodatekdochodowyzagranicawaluta(double podatekdochodowyzagranicawaluta) {
        this.podatekdochodowyzagranicawaluta = podatekdochodowyzagranicawaluta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getPrzychodyzus51Polska() {
        return przychodyzus51Polska;
    }

    public void setPrzychodyzus51Polska(double przychodyzus51Polska) {
        this.przychodyzus51Polska = przychodyzus51Polska;
    }

    public double getPodstawaopodatkowaniazagranicawaluta() {
        return podstawaopodatkowaniazagranicawaluta;
    }

    public void setPodstawaopodatkowaniazagranicawaluta(double podstawaopodatkowaniazagranicawaluta) {
        this.podstawaopodatkowaniazagranicawaluta = podstawaopodatkowaniazagranicawaluta;
    }

    public double getWolneodzajeciazasilek() {
        return wolneodzajeciazasilek;
    }

    public void setWolneodzajeciazasilek(double wolneodzajeciazasilek) {
        this.wolneodzajeciazasilek = wolneodzajeciazasilek;
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
        if (kalendarzmiesiac==null) {
            return "Pasekwynagrodzen brutto=" + brutto + ", kosztyuzyskania=" + kosztyuzyskania + ", netto=" + netto + ", definicjalistaplac=" 
                    + ", rok=" + rok + ", mc=" + mc + ", rokwypl=" + rokwypl + ", mcwypl=" + mcwypl + '}';
        } else if (definicjalistaplac!=null) {
            return "Pasekwynagrodzen{"+ "nazwisko "+kalendarzmiesiac.getNazwiskoImie() + "brutto=" + brutto + ", kosztyuzyskania=" + kosztyuzyskania + ", netto=" + netto + ", definicjalistaplac=" 
                    + definicjalistaplac.getRodzajlistyplac().getNazwa() + ", kalendarzmiesiac=" + kalendarzmiesiac.getRokMc() + ", rok=" + rok + ", mc=" + mc + ", rokwypl=" + rokwypl + ", mcwypl=" + mcwypl + '}';
        } else {
            return "Pasekwynagrodzen{"+ "nazwisko "+kalendarzmiesiac.getNazwiskoImie() + "brutto=" + brutto + ", kosztyuzyskania=" + kosztyuzyskania + ", netto=" + netto +", rok=" + rok + ", mc=" + mc + ", rokwypl=" + rokwypl + ", mcwypl=" + mcwypl + '}';
        }
    }

    

//    id,nazwa,typ
//    1,"umowa o pracę",1
//    2,"umowa zlecenia i o dzieło",2
//    3,"pełnienie obowiązków",3
//    4,zasiłki,4

    public int getRodzajWynagrodzenia() {
        return this.definicjalistaplac.getRodzajlistyplac().getTyt_serial();
    }

    public double getBruttozuskraj() {
        return bruttozuskraj;
    }

    public void setBruttozuskraj(double bruttozuskraj) {
        this.bruttozuskraj = bruttozuskraj;
    }

    public Integer getLis_tyt_serial() {
        return lis_tyt_serial;
    }

    public double getBruttobezspolecznych() {
        return bruttobezspolecznych;
    }

    public void setBruttobezspolecznych(double bruttobezspolecznych) {
        this.bruttobezspolecznych = bruttobezspolecznych;
    }

    
    public void setLis_tyt_serial(Integer lis_tyt_serial) {
        this.lis_tyt_serial = lis_tyt_serial;
    }

    public Integer getLpl_serial() {
        return lpl_serial;
    }

    public double getNettowaluta() {
        return nettowaluta;
    }

    public void setNettowaluta(double nettowaluta) {
        this.nettowaluta = nettowaluta;
    }

    public String getSymbolwaluty() {
        return symbolwaluty;
    }

    public void setSymbolwaluty(String symbolwaluty) {
        this.symbolwaluty = symbolwaluty;
    }


    public double getUlgadlaklasysredniejI() {
        return ulgadlaklasysredniejI;
    }

    public void setUlgadlaklasysredniejI(double ulgadlaklasysredniejI) {
        this.ulgadlaklasysredniejI = ulgadlaklasysredniejI;
    }

    public double getUlgadlaklasysredniejII() {
        return ulgadlaklasysredniejII;
    }

   
    public void setUlgadlaklasysredniejII(double ulgadlaklasysredniejII) {
        this.ulgadlaklasysredniejII = ulgadlaklasysredniejII;
    }

    public double getUlgadlaklasysredniejSuma() {
        return this.ulgadlaklasysredniejI+this.ulgadlaklasysredniejII;
    }
    
    
    public void setLpl_serial(Integer lpl_serial) {
        this.lpl_serial = lpl_serial;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public boolean isZmieniony() {
        return zmieniony;
    }

    public void setZmieniony(boolean zmieniony) {
        this.zmieniony = zmieniony;
    }
   
    public String getOkresWypl() {
        return this.getRokwypl()+this.getMcwypl();
    }
    
    public String getOkresNalezny() {
        return this.getRok()+this.getMc();
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

    public double getKosztyuzyskaniahipotetyczne() {
        return kosztyuzyskaniahipotetyczne;
    }

    public void setKosztyuzyskaniahipotetyczne(double kosztyuzyskaniahipotetyczne) {
        this.kosztyuzyskaniahipotetyczne = kosztyuzyskaniahipotetyczne;
    }

    public double getKwotawolnadlazdrowotnej() {
        return kwotawolnadlazdrowotnej;
    }

    public void setKwotawolnadlazdrowotnej(double kwotawolnadlazdrowotnej) {
        this.kwotawolnadlazdrowotnej = kwotawolnadlazdrowotnej;
    }

    public double getKwotawolnahipotetyczna() {
        return kwotawolnahipotetyczna;
    }

    public void setKwotawolnahipotetyczna(double kwotawolnahipotetyczna) {
        this.kwotawolnahipotetyczna = kwotawolnahipotetyczna;
    }

    public boolean isNierezydent() {
        return nierezydent;
    }

    public void setNierezydent(boolean nierezydent) {
        this.nierezydent = nierezydent;
    }

    public double getPodstawaopodatkowaniahipotetyczna() {
        return podstawaopodatkowaniahipotetyczna;
    }

    public void setPodstawaopodatkowaniahipotetyczna(double podstawaopodatkowaniahipotetyczna) {
        this.podstawaopodatkowaniahipotetyczna = podstawaopodatkowaniahipotetyczna;
    }

    public double getPodatekwstepnyhipotetyczny() {
        return podatekwstepnyhipotetyczny;
    }

    public void setPodatekwstepnyhipotetyczny(double podatekwstepnyhipotetyczny) {
        this.podatekwstepnyhipotetyczny = podatekwstepnyhipotetyczny;
    }

    public String getNazwiskoImie() {
        return this.kalendarzmiesiac.getAngaz().getPracownik().getNazwiskoImie();
    }
    
    public String getPesel() {
        return this.kalendarzmiesiac.getAngaz().getPracownik().getPesel();
    }

    public String getNrkonta() {
        return this.kalendarzmiesiac.getAngaz().getPracownik().getBankkonto();
    }

    
    public double getBruttobezzus() {
        return bruttobezzus;
    }

    public void setBruttobezzus(double bruttobezzus) {
        this.bruttobezzus = bruttobezzus;
    }

    public double getPodstawaskladkizus() {
        return Z.z(podstawaskladkizus);
    }

    public void setPodstawaskladkizus(double podstawaskladkizus) {
        this.podstawaskladkizus = podstawaskladkizus;
    }

    public String getDatawyplaty() {
        return datawyplaty;
    }

    public void setDatawyplaty(String datawyplaty) {
        this.datawyplaty = datawyplaty;
        if (datawyplaty!=null&&datawyplaty.length()==10) {
            this.rokwypl = Data.getRok(datawyplaty);
            this.mcwypl = Data.getMc(datawyplaty);
        }
    }

    public double getBruttozus() {
        return bruttozus;
    }
    
    public void setBruttozus(double bruttozus) {
        this.bruttozus = bruttozus;
    }

    public double getNettoprzedpotraceniami() {
        return nettoprzedpotraceniami;
    }

    public void setNettoprzedpotraceniami(double nettoprzedpotraceniami) {
        this.nettoprzedpotraceniami = nettoprzedpotraceniami;
    }

    public double getDietawaluta() {
        return dietawaluta;
    }

    public void setDietawaluta(double dietawaluta) {
        this.dietawaluta = dietawaluta;
    }

    public double getOddelegowaniewaluta() {
        return oddelegowaniewaluta;
    }

    public double getOddelegowaniepln() {
        return oddelegowaniepln;
    }
    

    public double getOddelegowaniewalutaToczek() {
        double zwrot = oddelegowaniewaluta;
        if (this.isImportowany()) {
            List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList1 = this.naliczenieskladnikawynagrodzeniaList;
            if (naliczenieskladnikawynagrodzeniaList1!=null) {
                zwrot = naliczenieskladnikawynagrodzeniaList1.stream().filter(nala->nala.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isOddelegowanie()).mapToDouble(nal->nal.getKwotadolistyplacwaluta()).sum();
            }
        }
        return zwrot;
    }

    public void setOddelegowaniewaluta(double oddelegowaniewaluta) {
        this.oddelegowaniewaluta = oddelegowaniewaluta;
    }

    public double getOddelegowanieplnToczek() {
         double zwrot = oddelegowaniepln;
        if (this.isImportowany()) {
            List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList1 = this.naliczenieskladnikawynagrodzeniaList;
            if (naliczenieskladnikawynagrodzeniaList1!=null) {
                zwrot = naliczenieskladnikawynagrodzeniaList1.stream().filter(nala->nala.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isOddelegowanie()
                        ||(nala.getSkl_dod_1()!=null&&nala.getSkl_dod_1().equals('T'))).mapToDouble(nal->nal.getKwotadolistyplac()).sum();
            }
        }
        return zwrot;
    }

    public double getPodstawaskladkizusemerytalnaograniczona() {
        return podstawaskladkizusemerytalnaograniczona;
    }

    public void setPodstawaskladkizusemerytalnaograniczona(double podstawaskladkizusemerytalnaograniczona) {
        this.podstawaskladkizusemerytalnaograniczona = podstawaskladkizusemerytalnaograniczona;
    }

    public double getPodstawaskladkizusrentowaograniczona() {
        return podstawaskladkizusrentowaograniczona;
    }

    public void setPodstawaskladkizusrentowaograniczona(double podstawaskladkizusrentowaograniczona) {
        this.podstawaskladkizusrentowaograniczona = podstawaskladkizusrentowaograniczona;
    }
    
    

    public void setOddelegowaniepln(double oddelegowaniepln) {
        this.oddelegowaniepln = oddelegowaniepln;
    }

    public int getLata() {
        return lata;
    }

    public void setLata(int lata) {
        this.lata = lata;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getOddelegowaniewalutasymbol() {
        return oddelegowaniewalutasymbol;
    }

    public void setOddelegowaniewalutasymbol(String oddelegowaniewalutasymbol) {
        this.oddelegowaniewalutasymbol = oddelegowaniewalutasymbol;
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

    public double getBruttominusspoleczne() {
        return bruttominusspoleczne;
    }

    public void setBruttominusspoleczne(double bruttominusspoleczne) {
        this.bruttominusspoleczne = bruttominusspoleczne;
    }

    public boolean isDo26lat() {
        return do26lat;
    }

    public void setDo26lat(boolean do26lat) {
        this.do26lat = do26lat;
    }

    public double getKosztyuzyskania() {
        return kosztyuzyskania;
    }

    public void setKosztyuzyskania(double kosztyuzyskania) {
        this.kosztyuzyskania = kosztyuzyskania;
    }

    public double getProcentkosztow() {
        return procentkosztow;
    }

    public void setProcentkosztow(double procentkosztow) {
        this.procentkosztow = procentkosztow;
    }
    
    public void setKwotawolna(double kwotawolna) {
        this.kwotawolna = kwotawolna;
    }

    public double getKwotawolna() {
        return kwotawolna;
    }

    public Integer getGodzinyobowiazku() {
        return godzinyobowiazku;
    }

    public void setGodzinyobowiazku(Integer godzinyobowiazku) {
        this.godzinyobowiazku = godzinyobowiazku;
    }

    public Integer getGodzinyprzepracowane() {
        return godzinyprzepracowane;
    }

    public void setGodzinyprzepracowane(Integer godzinyprzepracowane) {
        this.godzinyprzepracowane = godzinyprzepracowane;
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

    public double getPodstawaprzedkorektaozagranice() {
        return podstawaprzedkorektaozagranice;
    }

    public void setPodstawaprzedkorektaozagranice(double podstawaprzedkorektaozagranice) {
        this.podstawaprzedkorektaozagranice = podstawaprzedkorektaozagranice;
    }

    public double getSpoleczneudzialpolska() {
        return spoleczneudzialpolska;
    }

    public void setSpoleczneudzialpolska(double spoleczneudzialpolska) {
        this.spoleczneudzialpolska = spoleczneudzialpolska;
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

    public double getLimitzuspoza() {
        return limitzuspoza;
    }

    public void setLimitzuspoza(double limitzuspoza) {
        this.limitzuspoza = limitzuspoza;
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
    
    public int getRokI() {
        int zwrot = 0;
        if (this.rok!=null) {
            zwrot = Integer.parseInt(this.rok);
        }
        return zwrot;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    
    public Integer getMcI() {
        Integer zwrot = -1;
        if (this.mc!=null) {
            zwrot = Integer.parseInt(this.mc);
        }
        return zwrot;
    }
    
    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getRokwypl() {
        return rokwypl;
    }
    
    
    public int getRokwyplI() {
        int zwrot = 0;
        if (this.rokwypl!=null) {
            zwrot = Integer.parseInt(this.rokwypl);
        }
        return zwrot;
    }

    public String getMcwypl() {
        return mcwypl;
    }
    
    public Integer getMcwyplI() {
        Integer zwrot = -1;
        if (this.mcwypl!=null) {
            zwrot = Integer.parseInt(this.mcwypl);
        }
        return zwrot;
    }

    public boolean isUlgadlaKlasySredniej () {
        boolean zwrot = false;
        if (this.kalendarzmiesiac!=null && this.kalendarzmiesiac.getAngaz()!=null) {
            zwrot = this.kalendarzmiesiac.getAngaz().getPracownik().isUlgadlaklasysredniej();
        }
        return zwrot;
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

    public int getMiesiace() {
        return miesiace;
    }

    public void setMiesiace(int miesiace) {
        this.miesiace = miesiace;
    }
    
//delete
//    public double getBruttobezzusbezpodatek() {
//        return bruttobezzusbezpodatek;
//    }
//
//    public void setBruttobezzusbezpodatek(double bruttobezzusbezpodatek) {
//        this.bruttobezzusbezpodatek = bruttobezzusbezpodatek;
//    }

    public double getWynagrodzenieminimalne() {
        return wynagrodzenieminimalne;
    }

    public void setWynagrodzenieminimalne(double wynagrodzenieminimalne) {
        this.wynagrodzenieminimalne = wynagrodzenieminimalne;
    }

    public boolean isDrugiprog() {
        return drugiprog;
    }

    public void setDrugiprog(boolean drugiprog) {
        this.drugiprog = drugiprog;
    }

    public String getSporzadzil() {
        return sporzadzil;
    }

    public void setSporzadzil(String sporzadzil) {
        this.sporzadzil = sporzadzil;
    }

    public double getPodatekdochodowyzagranica() {
        return podatekdochodowyzagranica;
    }

    public void setPodatekdochodowyzagranica(double podatekdochodowyzagranica) {
        this.podatekdochodowyzagranica = podatekdochodowyzagranica;
    }

    public double getSpolecznedoodliczeniakierowca() {
        return spolecznedoodliczeniakierowca;
    }

    public void setSpolecznedoodliczeniakierowca(double spolecznedoodliczeniakierowca) {
        this.spolecznedoodliczeniakierowca = spolecznedoodliczeniakierowca;
    }

    
    
//
//    public EkwiwalentUrlop getEkwiwalentUrlop() {
//        return ekwiwalentSkladniki;
//    }
//
//    public void setEkwiwalentUrlop(EkwiwalentUrlop ekwiwalentSkladniki) {
//        this.ekwiwalentSkladniki = ekwiwalentSkladniki;
//    }

    public String getDatawysylki() {
        return datawysylki;
    }

    public void setDatawysylki(String datawysylki) {
        this.datawysylki = datawysylki;
    }

    public double getPrzychodypodatekpolska() {
        return przychodypodatekpolska;
    }
    
    public double getPrzychodypodatekpolskaMinusDieta() {
        return Z.z(przychodypodatekpolska - dietaodliczeniepodstawaop);
    }

    public void setPrzychodypodatekpolska(double przychodypodatekpolska) {
        this.przychodypodatekpolska = przychodypodatekpolska;
    }

    public double getPrzychodypodatekzagranica() {
        return przychodypodatekzagranica;
    }

    public void setPrzychodypodatekzagranica(double przychodypodatekzagranica) {
        this.przychodypodatekzagranica = przychodypodatekzagranica;
    }

    public double getPrzychodyzus51() {
        return przychodyzus51;
    }

    public void setPrzychodyzus51(double przychodyzus51) {
        this.przychodyzus51 = przychodyzus51;
    }

    public double getPrzychodyzus52() {
        return przychodyzus52;
    }

    public void setPrzychodyzus52(double przychodyzus52) {
        this.przychodyzus52 = przychodyzus52;
    }

    public double getBruttobezzusbezpodatek() {
        return bruttobezzusbezpodatek;
    }

    public void setBruttobezzusbezpodatek(double bruttobezzusbezpodatek) {
        this.bruttobezzusbezpodatek = bruttobezzusbezpodatek;
    }

    
    public double getBruttozusbezpodatek() {
        return bruttozusbezpodatek;
    }

    public void setBruttozusbezpodatek(double bruttozusbezpodatek) {
        this.bruttozusbezpodatek = bruttozusbezpodatek;
    }

    public double getPrzychodzagranicasuperplace() {
        return przychodzagranicasuperplace;
    }

    public void setPrzychodzagranicasuperplace(double przychodzagranicasuperplace) {
        this.przychodzagranicasuperplace = przychodzagranicasuperplace;
    }

    public double getPrzekroczenie26lat() {
        return przekroczenie26lat;
    }

    public void setPrzekroczenie26lat(double przekroczenie26lat) {
        this.przekroczenie26lat = przekroczenie26lat;
    }

    public double getPracchoroboweOddelegowanie() {
        return pracchoroboweOddelegowanie;
    }

    public void setPracchoroboweOddelegowanie(double pracchoroboweOddelegowanie) {
        this.pracchoroboweOddelegowanie = pracchoroboweOddelegowanie;
    }

    public double getPracemerytalneOddelegowanie() {
        return pracemerytalneOddelegowanie;
    }

    public void setPracemerytalneOddelegowanie(double pracemerytalneOddelegowanie) {
        this.pracemerytalneOddelegowanie = pracemerytalneOddelegowanie;
    }

    public double getPracrentoweOddelegowanie() {
        return pracrentoweOddelegowanie;
    }

    public void setPracrentoweOddelegowanie(double pracrentoweOddelegowanie) {
        this.pracrentoweOddelegowanie = pracrentoweOddelegowanie;
    }

    public double getPraczdrowotneOddelegowanie() {
        return praczdrowotneOddelegowanie;
    }

    public void setPraczdrowotneOddelegowanie(double praczdrowotneOddelegowanie) {
        this.praczdrowotneOddelegowanie = praczdrowotneOddelegowanie;
    }

    public double getEmerytalneOddelegowanie() {
        return emerytalneOddelegowanie;
    }

    public void setEmerytalneOddelegowanie(double emerytalneOddelegowanie) {
        this.emerytalneOddelegowanie = emerytalneOddelegowanie;
    }

    public double getRentoweOddelegowanie() {
        return rentoweOddelegowanie;
    }

    public void setRentoweOddelegowanie(double rentoweOddelegowanie) {
        this.rentoweOddelegowanie = rentoweOddelegowanie;
    }

    public double getLiczbapaskow() {
        return liczbapaskow;
    }

    public void setLiczbapaskow(double liczbapaskow) {
        this.liczbapaskow = liczbapaskow;
    }

    public double getPracchoroboweOddelegowanieEuro() {
        return pracchoroboweOddelegowanieEuro;
    }

    public void setPracchoroboweOddelegowanieEuro(double pracchoroboweOddelegowanieEuro) {
        this.pracchoroboweOddelegowanieEuro = pracchoroboweOddelegowanieEuro;
    }

    public double getPracemerytalneOddelegowanieEuro() {
        return pracemerytalneOddelegowanieEuro;
    }

    public void setPracemerytalneOddelegowanieEuro(double pracemerytalneOddelegowanieEuro) {
        this.pracemerytalneOddelegowanieEuro = pracemerytalneOddelegowanieEuro;
    }

    public double getPracrentoweOddelegowanieEuro() {
        return pracrentoweOddelegowanieEuro;
    }

    public void setPracrentoweOddelegowanieEuro(double pracrentoweOddelegowanieEuro) {
        this.pracrentoweOddelegowanieEuro = pracrentoweOddelegowanieEuro;
    }

    public double getPraczdrowotneOddelegowanieEuro() {
        return praczdrowotneOddelegowanieEuro;
    }

    public void setPraczdrowotneOddelegowanieEuro(double praczdrowotneOddelegowanieEuro) {
        this.praczdrowotneOddelegowanieEuro = praczdrowotneOddelegowanieEuro;
    }

    public double getEmerytalneOddelegowanieEuro() {
        return emerytalneOddelegowanieEuro;
    }

    public void setEmerytalneOddelegowanieEuro(double emerytalneOddelegowanieEuro) {
        this.emerytalneOddelegowanieEuro = emerytalneOddelegowanieEuro;
    }

    public double getRentoweOddelegowanieEuro() {
        return rentoweOddelegowanieEuro;
    }

    public void setRentoweOddelegowanieEuro(double rentoweOddelegowanieEuro) {
        this.rentoweOddelegowanieEuro = rentoweOddelegowanieEuro;
    }

    public boolean isKorektapodstawaskladkizus() {
        return korektapodstawaskladkizus;
    }

    public void setKorektapodstawaskladkizus(boolean korektapodstawaskladkizus) {
        this.korektapodstawaskladkizus = korektapodstawaskladkizus;
    }

    


    public String getWiekpasek() {
        String zwrot = "";
        if (this.datawyplaty!=null) {
            zwrot = "lat: "+this.lata+" mc: "+this.miesiace+" dni: "+this.dni;
        }
        return zwrot;
    }
    
    public List<Pasekwynagrodzen.Skladnikwynlista> getPobierzskladniki(Definicjalistaplac definicjalistaplac){
        List<Pasekwynagrodzen.Skladnikwynlista> zwrot = new ArrayList<>();
        int i = 1;
        if (this.naliczenieskladnikawynagrodzeniaList!=null) {
            for (Naliczenieskladnikawynagrodzenia p : this.naliczenieskladnikawynagrodzeniaList) {
                boolean czyschowac =false;
//                List<RodzajlistyplacRodzajwynagrodzenia> definicjalistaplacRodzajwynagrodzeniaList = definicjalistaplac.getRodzajlistyplac().getDefinicjalistaplacRodzajwynagrodzeniaList();
//                for (RodzajlistyplacRodzajwynagrodzenia rodz : definicjalistaplacRodzajwynagrodzeniaList) {
//                    if (rodz.getRodzajwynagrodzenia().equals(p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia())) {
//                        czyschowac = false;
//                    }
//                }
                if (czyschowac==false) {
                    Skladnikwynlista wiersz = new Skladnikwynlista();
                    wiersz.lp = i++;
                    wiersz.kod = p.getSkladnikwynagrodzenia()!=null?p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod():"";
                    wiersz.nazwa = p.getSkladnikwynagrodzenia()!=null?p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpisskrocony():"";
                    wiersz.kwota = p.getKwotadolistyplac();
                    wiersz.kwotazmiennejporedukcji11 = p.getKwotaumownaminred11();
                    wiersz.redukcja = p.getKwotyredukujacesuma();
                    wiersz.dataod = p.getDataod();
                    wiersz.datado = p.getDatado();
                    wiersz.dniobowiazku = p.getDninalezne();
                    wiersz.dniprzepracowane = p.getDnifaktyczne();
                    wiersz.godzinyobowiazku = p.getGodzinynalezne();
                    wiersz.godzinyprzepracowane = p.getGodzinyfaktyczne();
                    wiersz.godzinypoza11 = p.getGodzinypoza11();
                    wiersz.stawkanagodzine = p.getStawkagodzinowa();
                    wiersz.stawkanagodzinewaluta = p.getStawkagodzinowawaluta();
                    wiersz.stawkadzienna = p.getStawkadzienna();
                    wiersz.wynagrodzeniezmienna = p.getKwotaumownazacalymc();
                    wiersz.wynagrodzeniezmiennawaluta = p.getKwotadolistyplacwaluta();
                    wiersz.uwagi = p.getUwagi();
                    wiersz.waluta = p.getWaluta();
                    try {
                        wiersz.setUrlopSP(p.getSkl_dod_1()!=null&&p.getSkl_dod_1().equals('T'));
                        wiersz.setRedukcjaSP(p.getSkl_rodzaj()!=null&&p.getSkl_rodzaj().equals('U'));
                    } catch (Exception e){}
                    zwrot.add(wiersz);
                }
            }
        }
        if (this.naliczenienieobecnoscList!=null) {
            for (Naliczenienieobecnosc p : this.naliczenienieobecnoscList) {
                boolean czyschowac =false;
//                List<RodzajlistyplacRodzajwynagrodzenia> definicjalistaplacRodzajwynagrodzeniaList = definicjalistaplac.getRodzajlistyplac().getDefinicjalistaplacRodzajwynagrodzeniaList();
//                for (RodzajlistyplacRodzajwynagrodzenia rodz : definicjalistaplacRodzajwynagrodzeniaList) {
//                    if (rodz.getRodzajlistyplac().equals(definicjalistaplac.getRodzajlistyplac())) {
//                        czyschowac = false;
//                    }
//                }
                if (czyschowac==false) {
                    Skladnikwynlista wiersz = new Skladnikwynlista();
                    wiersz.lp = i++;
                    wiersz.kod = p.getNieobecnosc().getKod();
                    if (p.getSkladnikwynagrodzenia()!=null) {
                        wiersz.nazwa = p.getNieobecnosc().getOpisRodzajSwiadczenie()+"/"+p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpisskrocony();
                    } else {
                        wiersz.nazwa = p.getNieobecnosc().getOpisRodzajSwiadczenie();
                    }
                    wiersz.kodzus = p.getNieobecnosc().getSwiadczeniekodzus()!=null?p.getNieobecnosc().getSwiadczeniekodzus().getKod():"";
                    wiersz.kwota = p.getKwota();
                    wiersz.redukcja = p.getKwotaredukcji()+p.getKwotastatystyczna();
                    wiersz.dataod = p.getDataod();
                    wiersz.datado = p.getDatado();
                    wiersz.dniobowiazku = p.getLiczbadniobowiazku();
                    wiersz.dniprzepracowane = p.getLiczbadniNieobecnosci();
                    wiersz.godzinyobowiazku = p.getLiczbagodzinobowiazku();
                    wiersz.godzinyprzepracowane = p.getLiczbagodzinNieobecnosci();
                    wiersz.stawkanagodzine = p.getStawkagodzinowa();
                    wiersz.stawkadzienna = p.getStawkadzienna();
                    wiersz.waluta = p.getWaluta();
                    wiersz.setWyrownanie(p.isWyrownaniepodstawy());
                    wiersz.procentzwolnienia = p.getNieobecnosc().getZwolnienieprocent();
                    wiersz.wynagrodzeniezmienna = p.getSkladnikistale();
                    wiersz.wynagrodzeniezmiennawaluta = p.getKwotawaluta();
                    wiersz.procentredukcja = p.getRedukcjaprocent();
                    zwrot.add(wiersz);
                }
            }
        }
        if (this.naliczeniepotracenieList!=null) {
            for (Naliczeniepotracenie p : this.naliczeniepotracenieList) {
                Skladnikwynlista wiersz = new Skladnikwynlista();
                wiersz.lp = i++;
                wiersz.kod = String.valueOf(p.getSkladnikpotracenia().getRodzajpotracenia().getNumer());
                wiersz.nazwa = p.getSkladnikpotracenia().getRodzajpotracenia().getOpis();
                wiersz.kwota = p.getKwota();
                wiersz.dataod = p.getDataOd();
                wiersz.datado = p.getDataDo();
                wiersz.setPotracenie(true);
                zwrot.add(wiersz);
            }
        }
        return zwrot;
    }
    
    public double getRedukcjeSuma() {
        double zwrot = 0.0;
        if (this.getNaliczenieskladnikawynagrodzeniaList()!=null) {
            for (Naliczenieskladnikawynagrodzenia p : this.getNaliczenieskladnikawynagrodzeniaList()) {
                zwrot = zwrot + p.getKwotyredukujacesuma();
            }
        }
        return zwrot;
    }

    public Skladnikwynagrodzenia pobierzzasadnicze() {
        Skladnikwynagrodzenia zwrot = null;
        if (this.naliczenieskladnikawynagrodzeniaList!=null) {
            if (this.naliczenieskladnikawynagrodzeniaList.size()==1) {
                zwrot = this.naliczenieskladnikawynagrodzeniaList.get(0).getSkladnikwynagrodzenia();
            } else {
                for(Naliczenieskladnikawynagrodzenia p : this.naliczenieskladnikawynagrodzeniaList) {
                    Skladnikwynagrodzenia skladnikwynagrodzenia = p.getSkladnikwynagrodzenia();
                    if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getOpispelny().equals("Wynagrodzenie zasadnicze")) {
                        zwrot = skladnikwynagrodzenia;
                        break;
                    }
                }
            }
        }
        return zwrot;
    }
    
    public Umowa getUmowa() {
        return this.kalendarzmiesiac.getAngaz().getAktywnaUmowa();
    } 

    public boolean isPraca() {
        boolean zwrot = false;
        if (this.definicjalistaplac!=null) {
            zwrot = this.definicjalistaplac.getRodzajlistyplac().getTyp()==1;
        }
        return zwrot;
    }
    
    public boolean isZasilek() {
        boolean zwrot = false;
        if (this.definicjalistaplac!=null) {
            zwrot = this.definicjalistaplac.getRodzajlistyplac().getTyp()==4;
        }
        return zwrot;
    }
    
    public boolean isFunkcja() {
        boolean zwrot = false;
        if (this.definicjalistaplac!=null) {
            zwrot = this.definicjalistaplac.getRodzajlistyplac().getTyp()==3;
        }
        return zwrot;
    }
    
    public boolean isSwiadczeniarzeczowe() {
        boolean zwrot = false;
        if (this.definicjalistaplac!=null) {
            zwrot = this.definicjalistaplac.getRodzajlistyplac().getTyp()==6;
        }
        return zwrot;
    }
    
    public boolean isZlecenie() {
        boolean zwrot = false;
        if (this.definicjalistaplac!=null) {
            zwrot = this.definicjalistaplac.getRodzajlistyplac().getSymbol().equals("UZ");
        }
        return zwrot;
    }
    
    public boolean isDzielo() {
        boolean zwrot = false;
        if (this.definicjalistaplac!=null) {
            zwrot = this.definicjalistaplac.getRodzajlistyplac().getSymbol().equals("UD");
        }
        return zwrot;
    }
    
    public String getKodZus() {
        String zwrot = "brak aktywnej umowy";
        Umowa aktywnaUmowa = this.kalendarzmiesiac.getAngaz().getAktywnaUmowa();
        if (aktywnaUmowa!=null) {
            zwrot = this.kalendarzmiesiac.getAngaz().getAktywnaUmowa().getUmowakodzus().getKod();
        }
        return zwrot;
    }

    public Angaz getAngaz() {
        return this.kalendarzmiesiac.getAngaz();
    }

    public Pracownik getPracownik() {
        return this.kalendarzmiesiac.getPracownik();
    }

    public boolean czyjestkomornik() {
        boolean zwrot = false;
        if (this.getNaliczeniepotracenieList()!=null && this.getNaliczeniepotracenieList().size()>0) {
            for (Naliczeniepotracenie nal  : this.getNaliczeniepotracenieList()) {
                if (nal.getSkladnikpotracenia().getRodzajpotracenia().isPotraceniekomornicze()) {
                    zwrot = true;
                } else {
                    zwrot = false;
                }
            }
            
        }
        return zwrot;
    }

    public boolean isFpprzekroczeniewiek() {
        return fpprzekroczeniewiek;
    }

    public void setFpprzekroczeniewiek(boolean fpprzekroczeniewiek) {
        this.fpprzekroczeniewiek = fpprzekroczeniewiek;
    }

    public boolean isFppowrotmacierzynski() {
        return fppowrotmacierzynski;
    }

    public void setFppowrotmacierzynski(boolean fppowrotmacierzynski) {
        this.fppowrotmacierzynski = fppowrotmacierzynski;
    }

    public double obliczproporcjeZusOddelegowani() {
        double zwrot = 1;
        double brutto = this.przychodyzus51;
        // dodalem ponownie stara wersje bo nie liczylo dobrze podstawy dla starych przychodow ze starym przyporzadkowaniem 04.01.2024
        if (this.przychodyzus51==0.0) {
            brutto = this.bruttozus;
            if (this.do26lat) {
                brutto = brutto+this.bruttozusbezpodatek;
            }
        }
        //zmiana 03.01.2024 po rewolucji w porzadkowaniu skladnikow nie pobieralo wlasciwie
//        if (this.do26lat) {
//            brutto = brutto+this.bruttozusbezpodatek;
//        }
        if (brutto>0.0&&brutto!=this.podstawaskladkizus&&this.oddelegowaniewaluta>0.0) {
            zwrot = this.podstawaskladkizus/brutto;
        } else if (this.bruttozusbezpodatek>0.0&&this.bruttozusbezpodatek!=this.podstawaskladkizus&&this.oddelegowaniewaluta>0.0) {
            zwrot = this.podstawaskladkizus/this.bruttozusbezpodatek;
        }
        //usunalem bo wisniewski i byl przyapdek graniczny z 5922 i zadzialalo zle, podwyzszalo podstawe o ten procent, a i tak bniore z 2022 tylko to co jest w skladniku zus
//        if (this.rokwypl.equals("2022")&&brutto>5922.0&&this.podstawaskladkizus==5922.0) {
//            double dzielnik = Z.z(this.brutto-this.bruttozus);
//            if (dzielnik==0.0) {
//                zwrot = 1.0;
//            } else {
//                zwrot = this.bruttozus/dzielnik;
//            }
//        }
        return zwrot;
    }

     public double getPotraceniaInne() {
        double zwrot = 0.0;
        if (this.naliczeniepotracenieList!=null) {
            for (Naliczeniepotracenie pot : this.naliczeniepotracenieList) {
                if (pot.getSkladnikpotracenia()!=null&&pot.getSkladnikpotracenia().getRodzajpotracenia().isPotraceniekomornicze()==false) {
                    zwrot = zwrot +pot.getKwota();
                }
            }
        }
        return zwrot;
    }
    
    public double getPotraceniaKomornik() {
        double zwrot = 0.0;
        if (this.naliczeniepotracenieList!=null) {
            for (Naliczeniepotracenie pot : this.naliczeniepotracenieList) {
                if (pot.getSkladnikpotracenia()!=null&&pot.getSkladnikpotracenia().getRodzajpotracenia().isPotraceniekomornicze()) {
                    zwrot = zwrot +pot.getKwota();
                }
            }
        }
        return zwrot;
    }

    public double getPotraceniaPPK() {
        double zwrot = 0.0;
        if (this.naliczeniepotracenieList!=null) {
            for (Naliczeniepotracenie pot : this.naliczeniepotracenieList) {
                if (pot.getSkladnikpotracenia()!=null&&pot.getSkladnikpotracenia().getRodzajpotracenia().isPotracenieppk()) {
                    zwrot = zwrot +pot.getKwota();
                }
            }
        }
        return zwrot;
    }
    
     public double getNaliczeniaPPKPracodawca() {
        double zwrot = 0.0;
        if (this.naliczenieskladnikawynagrodzeniaList!=null) {
            for (Naliczenieskladnikawynagrodzenia pot : this.naliczenieskladnikawynagrodzeniaList) {
                if (pot.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("98")||pot.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("99")) {
                    zwrot = zwrot +pot.getKwotadolistyplac();
                }
            }
        }
        return zwrot;
    }

    public double getPotraceniaZaliczki() {
        double zwrot = 0.0;
        if (this.naliczeniepotracenieList!=null) {
            for (Naliczeniepotracenie pot : this.naliczeniepotracenieList) {
                if (pot.getSkladnikpotracenia()!=null&&pot.getSkladnikpotracenia().getRodzajpotracenia().isPotraceniezaliczka()) {
                    zwrot = zwrot +pot.getKwota();
                }
            }
        }
        return zwrot;
    }

    public double getStale(boolean uzupelniac) {
        double zwrot = 0.0;
        for (Naliczenieskladnikawynagrodzenia nal : this.naliczenieskladnikawynagrodzeniaList) {
            if (nal.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isZ31()) {
                if (uzupelniac) {
                    if (nal.getKwotaumownazacalymc()>0.0) {
                        zwrot = zwrot+nal.getKwotaumownazacalymc();
                    } else {
                        zwrot = zwrot+nal.getKwotadolistyplac()+nal.getKwotyredukujacesuma();
                    }
                } else {
                    zwrot = zwrot+nal.getKwotadolistyplac();
                }
                
            }
        }
         for (Naliczenienieobecnosc nal : this.naliczenienieobecnoscList) {
            if (nal.getNieobecnosc().getRodzajnieobecnosci().isZ31()) {
                zwrot = zwrot+nal.getKwota();
            }
        }
        return zwrot;
    }

    public double getZmienne() {
        double zwrot = 0.0;
        for (Naliczenieskladnikawynagrodzenia nal : this.naliczenieskladnikawynagrodzeniaList) {
            if (nal.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isZ32()) {
                zwrot = zwrot+nal.getKwotadolistyplac();
            }
        }
        for (Naliczenienieobecnosc nal : this.naliczenienieobecnoscList) {
            if (nal.getNieobecnosc().getRodzajnieobecnosci().isZ32()) {
                zwrot = zwrot+nal.getKwota();
            }
        }
        return zwrot;
    }

    public double getPremie(boolean uzupelniac) {
        double zwrot = 0.0;
        for (Naliczenieskladnikawynagrodzenia nal : this.naliczenieskladnikawynagrodzeniaList) {
            if (nal.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isZ33()) {
                if (uzupelniac) {
                    if (nal.getKwotaumownazacalymc()>0.0) {
                        zwrot = zwrot+nal.getKwotaumownazacalymc();
                    } else {
                        zwrot = zwrot+nal.getKwotadolistyplac()+nal.getKwotyredukujacesuma();
                    }
                } else {
                    zwrot = zwrot+nal.getKwotadolistyplac();
                }
            }
        }
        for (Naliczenienieobecnosc nal : this.naliczenienieobecnoscList) {
            if (nal.getNieobecnosc().getRodzajnieobecnosci().isZ33()) {
                zwrot = zwrot+nal.getKwota();
            }
        }
        return zwrot;
    }

    public Skladnikwynagrodzenia getSkladnikZUS51() {
        Skladnikwynagrodzenia zwrot = null;
        List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList = this.kalendarzmiesiac.getAngaz().getSkladnikwynagrodzeniaList();
        skladnikwynagrodzeniaList = skladnikwynagrodzeniaList.stream().filter(p->p.getRodzajwynagrodzenia().getKod().equals("ZUS1")).collect(Collectors.toList());
        if (skladnikwynagrodzeniaList!=null&&skladnikwynagrodzeniaList.size()==1) {
            zwrot = skladnikwynagrodzeniaList.get(0);
        }
        return zwrot;
    }

    
    public void naniespomocnika(Pasekpomocnik sumujprzychodyzlisty, boolean jestprzekroczenie) {
        if (jestprzekroczenie) {
            this.przychodypodatekpolska = sumujprzychodyzlisty.getBruttokraj();
            this.przychodypodatekzagranica = sumujprzychodyzlisty.getBruttooddelegowanie();
            this.podstawaopodatkowaniazagranica = sumujprzychodyzlisty.getBruttooddelegowanie();
            this.podstawaopodatkowaniazagranicawaluta = sumujprzychodyzlisty.getBruttooddelegowaniewaluta();
        } else {
            this.przychodypodatekpolska = sumujprzychodyzlisty.getBruttokraj()+sumujprzychodyzlisty.getBruttooddelegowanie();
        }
         this.bruttobezzusbezpodatek = sumujprzychodyzlisty.getBezzusbezpodatek();
         this.oddelegowaniepln = sumujprzychodyzlisty.getBruttooddelegowanie();
         this.oddelegowaniewaluta = sumujprzychodyzlisty.getBruttooddelegowaniewaluta();
         this.przychodyzus51 = sumujprzychodyzlisty.getPrzychodyzus51();
         this.przychodyzus51Polska = sumujprzychodyzlisty.getPrzychodyzus51Polska();
         this.przychodyzus52 = sumujprzychodyzlisty.getPrzychodyzus52();
         this.brutto = sumujprzychodyzlisty.getBrutto();
    }

    public double getPrzychodypodatekzagranicado26() {
        double zwrot = 0.0;
        if (this.do26lat) {
            zwrot = this.przychodypodatekzagranica;
        }
        return zwrot;
    }

    public double getPrzychodypodatekzagranicapo26() {
        double zwrot = 0.0;
        if (this.do26lat==false) {
            zwrot = this.przychodypodatekzagranica;
        }
        return zwrot;
    }

    public double getNieopodatkowane() {
        double zwrot = 0.0;
        for (Naliczenieskladnikawynagrodzenia nal : this.naliczenieskladnikawynagrodzeniaList) {
            if (nal.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isPodatek0bezpodatek1()) {
                zwrot = zwrot+nal.getKwotadolistyplac();
            }
        }
        return zwrot;

    }
   
    

    public class Skladnikwynlista {
        int lp;
        String nazwa;
        String dataod;
        String datado;
        String kod;
        String kodzus;
        String uwagi;
        boolean urlopSP;
        boolean redukcjaSP;
        boolean potracenie;
        double kwota;
        double kwotazmiennejporedukcji11;
        double wynagrodzeniezmienna;
        double wynagrodzeniezmiennawaluta;
        double redukcja;
        double procentredukcja;
        double dniobowiazku;
        double dniprzepracowane;
        double godzinyobowiazku;
        double godzinyprzepracowane;
        double godzinypoza11;
        double stawkanagodzine;
        double stawkanagodzinewaluta;
        double stawkadzienna;
        double procentzwolnienia;
        boolean wyrownanie;
        String waluta;
        
        public Skladnikwynlista() {
        }

        public int getLp() {
            return lp;
        }

        public void setLp(int lp) {
            this.lp = lp;
        }

        public String getNazwa() {
            return nazwa;
        }

        public void setNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        public String getDataod() {
            return dataod;
        }
        public String getDataodShort() {
           return Data.getDzien(dataod);
        }
        
        public void setDataod(String dataod) {
            this.dataod = dataod;
        }

        public String getDatado() {
            return datado;
        }
        public String getDatadoShort() {
            return Data.getDzien(datado);
        }
        public void setDatado(String datado) {
            this.datado = datado;
        }

        public double getGodzinypoza11() {
            return godzinypoza11;
        }

        public boolean isWyrownanie() {
            return wyrownanie;
        }

        public void setWyrownanie(boolean wyrownanie) {
            this.wyrownanie = wyrownanie;
        }

        public void setGodzinypoza11(double godzinypoza11) {
            this.godzinypoza11 = godzinypoza11;
        }

        public double getProcentzwolnienia() {
            return procentzwolnienia;
        }

        public void setProcentzwolnienia(double procentzwolnienia) {
            this.procentzwolnienia = procentzwolnienia;
        }

        public boolean isUrlopSP() {
            return urlopSP;
        }

        public void setUrlopSP(boolean urlopSP) {
            this.urlopSP = urlopSP;
        }

        public boolean isRedukcjaSP() {
            return redukcjaSP;
        }

        public void setRedukcjaSP(boolean redukcjaSP) {
            this.redukcjaSP = redukcjaSP;
        }

        public boolean isPotracenie() {
            return potracenie;
        }

        public void setPotracenie(boolean potracenie) {
            this.potracenie = potracenie;
        }

        public double getKwota() {
            return kwota;
        }

        public void setKwota(double kwota) {
            this.kwota = kwota;
        }

        public String getKod() {
            return kod;
        }

        public void setKod(String kod) {
            this.kod = kod;
        }

        public double getRedukcja() {
            return redukcja;
        }

        public void setRedukcja(double redukcja) {
            this.redukcja = redukcja;
        }

        public double getDniobowiazku() {
            return dniobowiazku;
        }

        public void setDniobowiazku(double dniobowiazku) {
            this.dniobowiazku = dniobowiazku;
        }

        public double getDniprzepracowane() {
            return dniprzepracowane;
        }

        public void setDniprzepracowane(double dniprzepracowane) {
            this.dniprzepracowane = dniprzepracowane;
        }

        public double getGodzinyobowiazku() {
            return godzinyobowiazku;
        }

        public void setGodzinyobowiazku(double godzinyobowiazku) {
            this.godzinyobowiazku = godzinyobowiazku;
        }

        public double getGodzinyprzepracowane() {
            return godzinyprzepracowane;
        }

        public void setGodzinyprzepracowane(double godzinyprzepracowane) {
            this.godzinyprzepracowane = godzinyprzepracowane;
        }

        public double getStawkanagodzine() {
            return stawkanagodzine;
        }

        public void setStawkanagodzine(double stawkanagodzine) {
            this.stawkanagodzine = stawkanagodzine;
        }

        public double getStawkanagodzinewaluta() {
            return stawkanagodzinewaluta;
        }

        public void setStawkanagodzinewaluta(double stawkanagodzinewaluta) {
            this.stawkanagodzinewaluta = stawkanagodzinewaluta;
        }

        public double getWynagrodzeniezmienna() {
            return wynagrodzeniezmienna;
        }

        public void setWynagrodzeniezmienna(double wynagrodzeniezmienna) {
            this.wynagrodzeniezmienna = wynagrodzeniezmienna;
        }

        public double getWynagrodzeniezmiennawaluta() {
            return wynagrodzeniezmiennawaluta;
        }

        public void setWynagrodzeniezmiennawaluta(double wynagrodzeniezmiennawaluta) {
            this.wynagrodzeniezmiennawaluta = wynagrodzeniezmiennawaluta;
        }
        

        public double getStawkadzienna() {
            return stawkadzienna;
        }

        public void setStawkadzienna(double stawkadzienna) {
            this.stawkadzienna = stawkadzienna;
        }

        public String getUwagi() {
            return uwagi;
        }

        public void setUwagi(String uwagi) {
            this.uwagi = uwagi;
        }

        public String getWaluta() {
            return waluta;
        }

        public void setWaluta(String waluta) {
            this.waluta = waluta;
        }

        public String getKodzus() {
            return kodzus;
        }

        public void setKodzus(String kodzus) {
            this.kodzus = kodzus;
        }

        public double getKwotazmiennejporedukcji11() {
            return kwotazmiennejporedukcji11;
        }

        public void setKwotazmiennejporedukcji11(double kwotazmiennejporedukcji11) {
            this.kwotazmiennejporedukcji11 = kwotazmiennejporedukcji11;
        }

        public double getProcentredukcja() {
            return procentredukcja;
        }

        public void setProcentredukcja(double procentredukcja) {
            this.procentredukcja = procentredukcja;
        }
    
        

        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 67 * hash + this.lp;
            hash = 67 * hash + Objects.hashCode(this.nazwa);
            hash = 67 * hash + (int) (Double.doubleToLongBits(this.kwota) ^ (Double.doubleToLongBits(this.kwota) >>> 32));
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Skladnikwynlista other = (Skladnikwynlista) obj;
            if (this.lp != other.lp) {
                return false;
            }
            if (Double.doubleToLongBits(this.kwota) != Double.doubleToLongBits(other.kwota)) {
                return false;
            }
            if (!Objects.equals(this.nazwa, other.nazwa)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Skladnikwynlista{" + "nazwa=" + nazwa + ", dataod=" + dataod + ", datado=" + datado + ", kod=" + kod + ", kwota=" + kwota + ", redukcja=" + redukcja + '}';
        }
        
        
    }
    

    
    
    
}
