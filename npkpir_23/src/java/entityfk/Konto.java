/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import abstractClasses.ToBeATreeNodeObject;
import entity.Podatnik;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import language.LocaleInfo;
import org.eclipse.persistence.annotations.FetchAttribute;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "konto", uniqueConstraints = {@UniqueConstraint(columnNames={"podid","pelnynumer","rok"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Konto.findAll", query = "SELECT k FROM Konto k"),
    @NamedQuery(name = "Konto.findById", query = "SELECT k FROM Konto k WHERE k.id = :id"),
    @NamedQuery(name = "Konto.findByPodatnikRok", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPodatnikRokBezPrzyporzadkowania", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.rok = :rok AND (k.pozycjaWn IS NULL OR k.pozycjaMa IS NULL)"),
    @NamedQuery(name = "Konto.findByPodatnikKsiegi", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.rok = :rok AND k.zaksiegowane = '1'"),
    @NamedQuery(name = "Konto.findByPodatnikBezSlownik", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.rok = :rok AND k.slownikowe = '0'"),
    @NamedQuery(name = "Konto.findByPodatnikBezSlownikKsiegi", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.rok = :rok AND k.slownikowe = '0' AND k.zaksiegowane = '1'"),
    @NamedQuery(name = "Konto.findByPodatnikTylkoSlownik", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.rok = :rok AND k.slownikowe != '0'"),
    @NamedQuery(name = "Konto.findByPodatnikTylkoSlownikZero", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.rok = :rok AND k.slownikowe != '0' AND k.nrkonta = '0'"),
    @NamedQuery(name = "Konto.findByPodatnikKliencifk", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.rok = :rok AND k.nazwapelna = :nazwa AND k.nazwaskrocona = :nip"),
    @NamedQuery(name = "Konto.findByMaxLevelPodatnik", query = "SELECT MAX(k.level) FROM Konto k WHERE k.podatnik = :podatnik  AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPodatnikBez0", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.nrkonta != '0' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByKontaPodatnikaBO", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE :wzorzec AND k.mapotomkow = 0 AND k.nrkonta != 0  AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPodatnikBilansowe", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.bilansowewynikowe = 'bilansowe' AND k.nrkonta != 0  AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPodatnikWynikowe", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.bilansowewynikowe = 'wynikowe' AND k.nrkonta != 0  AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPodatnikBilansoweBezPotomkow", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.bilansowewynikowe = 'bilansowe' AND (k.pozycjaWn IS NOT NULL OR k.pozycjaMa IS NOT NULL) AND k.mapotomkow = 0  AND k.nrkonta != 0 AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPodatnikBilansoweKwotaBezPotomkow", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.bilansowewynikowe = 'bilansowe' AND (k.pozycjaWn IS NOT NULL OR k.pozycjaMa IS NOT NULL) AND k.mapotomkow = 0  AND k.nrkonta != 0 AND k.rok = :rok AND (k.boWn != 0 OR k.boMa != 0)"),
    @NamedQuery(name = "Konto.findByPodatnikWynikoweBezPotomkow", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.bilansowewynikowe = 'wynikowe' AND (k.pozycjaWn IS NOT NULL OR k.pozycjaMa IS NOT NULL) AND k.mapotomkow = 0  AND k.nrkonta != 0  AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPodatnikZPotomkami", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik  AND k.mapotomkow = 1  AND k.nrkonta != 0  AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPodatnik490", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.nrkonta = '490' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByKonto860", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer = '860' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByNrkonta", query = "SELECT k FROM Konto k WHERE k.nrkonta = :nrkonta"),
    @NamedQuery(name = "Konto.findBySyntetyczne", query = "SELECT k FROM Konto k WHERE k.syntetyczne = :syntetyczne"),
    @NamedQuery(name = "Konto.findByLevel", query = "SELECT k FROM Konto k WHERE k.level = :level"),
    @NamedQuery(name = "Konto.findByNazwapelna", query = "SELECT k FROM Konto k WHERE k.nazwapelna = :nazwapelna"),
    @NamedQuery(name = "Konto.findByNazwaskrocona", query = "SELECT k FROM Konto k WHERE k.nazwaskrocona = :nazwaskrocona"),
    @NamedQuery(name = "Konto.findByBilansowewynikowe", query = "SELECT k FROM Konto k WHERE k.bilansowewynikowe = :bilansowewynikowe"),
    @NamedQuery(name = "Konto.findByBilansowewynikowePodatnik", query = "SELECT k FROM Konto k WHERE k.bilansowewynikowe = :bilansowewynikowe AND k.podatnik = :podatnik AND k.mapotomkow = false AND k.nrkonta != 0 AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByZwyklerozrachszczegolne", query = "SELECT k FROM Konto k WHERE k.zwyklerozrachszczegolne = :zwyklerozrachszczegolne"),
    @NamedQuery(name = "Konto.findByRozrachunkowePodatnik", query = "SELECT k FROM Konto k WHERE k.zwyklerozrachszczegolne = :zwyklerozrachszczegolne AND k.podatnik = :podatnik  AND k.nrkonta != 0 AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByRozrachunkowePodatnikZpotomkami", query = "SELECT k FROM Konto k WHERE k.zwyklerozrachszczegolne = :zwyklerozrachszczegolne AND k.podatnik = :podatnik  AND k.nrkonta != 0 AND k.rok = :rok AND k.mapotomkow = true"),
    @NamedQuery(name = "Konto.findByRozrachunkiPodatnikWszystkie", query = "SELECT k FROM Konto k WHERE k.zwyklerozrachszczegolne = 'rozrachunkowe' AND k.podatnik = :podatnik  AND k.nrkonta != 0 AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByVATPodatnik", query = "SELECT k FROM Konto k WHERE k.zwyklerozrachszczegolne = :zwyklerozrachszczegolne AND k.podatnik = :podatnik AND k.mapotomkow = false AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findBySrodkiTrwPodatnik", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.rok = :rok AND  k.mapotomkow = false AND k.pelnynumer LIKE '010%'"),
    @NamedQuery(name = "Konto.findByRMKPodatnik", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.rok = :rok AND  k.mapotomkow = false AND k.pelnynumer LIKE '64%'"),
    @NamedQuery(name = "Konto.findByMacierzysteBOPodatnik", query = "SELECT k FROM Konto k WHERE k.kontomacierzyste = :macierzyste AND k.podatnik = :podatnik AND NOT k.pelnynumer = '000' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findBySiostrzaneBOPodatnik", query = "SELECT k FROM Konto k WHERE k.kontomacierzyste = :macierzyste AND k.podatnik = :podatnik AND NOT k.pelnynumer = '000' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByMacierzystePodatnikCOUNT", query = "SELECT MAX(CAST (k.nrkonta AS DECIMAL)) FROM Konto k WHERE k.kontomacierzyste = :macierzyste AND k.podatnik = :podatnik AND NOT k.pelnynumer = '000' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPozycjaWynikowe", query = "SELECT k FROM Konto k WHERE k.bilansowewynikowe = 'wynikowe'  AND (k.pozycjaWn = :pozycja OR k.pozycjaMa = :pozycja)  AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPozycjaWynikoweAll", query = "SELECT k FROM Konto k WHERE k.bilansowewynikowe = 'wynikowe'  AND (k.pozycjaWn IS NOT NULL OR k.pozycjaMa IS NOT NULL) AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPozycjaBilansowe", query = "SELECT k FROM Konto k WHERE k.bilansowewynikowe = 'bilansowe' AND k.podatnik = :podatnik  AND k.rok = :rok AND ((k.pozycjaWn = :pozycja AND k.stronaWn = :aktywa0pasywa1) OR (k.pozycjaMa = :pozycja AND k.stronaMa = :aktywa0pasywa1))"),
    @NamedQuery(name = "Konto.findByPozycjaBilansoweAll", query = "SELECT k FROM Konto k WHERE k.bilansowewynikowe = 'bilansowe' AND k.podatnik = :podatnik  AND k.rok = :rok AND (k.pozycjaWn IS NOT NULL OR k.pozycjaMa IS NOT NULL)"),
    @NamedQuery(name = "Konto.findByMacierzysteWynikowe", query = "SELECT k FROM Konto k WHERE k.kontomacierzyste = :macierzyste AND NOT k.pelnynumer = '000' AND k.bilansowewynikowe = 'wynikowe' AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByMacierzysteWynikoweNull", query = "SELECT k FROM Konto k WHERE k.kontomacierzyste IS NULL AND NOT k.pelnynumer = '000' AND k.bilansowewynikowe = 'wynikowe' AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByMacierzysteBilansowe", query = "SELECT k FROM Konto k WHERE k.kontomacierzyste = :macierzyste AND NOT k.pelnynumer = '000' AND k.bilansowewynikowe = 'bilansowe' AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByMacierzysteBilansoweNull", query = "SELECT k FROM Konto k WHERE k.kontomacierzyste IS NULL AND NOT k.pelnynumer = '000' AND k.bilansowewynikowe = 'bilansowe' AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByPelnynumer", query = "SELECT k FROM Konto k WHERE k.pelnynumer = :pelnynumer"),
    @NamedQuery(name = "Konto.findByPelnynumerPodatnik", query = "SELECT k FROM Konto k WHERE k.pelnynumer = :pelnynumer AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByLevelPodatnik", query = "SELECT k FROM Konto k WHERE k.level = :level AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByLevelRok", query = "SELECT k FROM Konto k WHERE k.level != :level AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByNazwaPodatnik", query = "SELECT k FROM Konto k WHERE k.nazwaskrocona = :nazwaskrocona AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByNazwaPelnaPodatnik", query = "SELECT k FROM Konto k WHERE k.nazwapelna = :nazwapelna AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByMapotomkow", query = "SELECT k FROM Konto k WHERE k.mapotomkow = :mapotomkow"),
    @NamedQuery(name = "Konto.findByMapotomkowMaSlownik", query = "SELECT k FROM Konto k WHERE k.mapotomkow = :mapotomkow AND k.nrkonta != '0'"),
    @NamedQuery(name = "Konto.findByMapotomkowMaSlownikPodatnik", query = "SELECT k FROM Konto k WHERE k.mapotomkow = :mapotomkow AND k.nrkonta != '0' AND k.podatnik = :podatnik  AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByMapotomkowMaSlownikPodatnikWynikowe", query = "SELECT k FROM Konto k WHERE k.mapotomkow = :mapotomkow AND k.nrkonta != '0' AND k.podatnik = :podatnik  AND k.rok = :rok AND k.bilansowewynikowe = 'wynikowe'"),
    @NamedQuery(name = "Konto.findByMapotomkowMaSlownikPodatnik5", query = "SELECT k FROM Konto k WHERE k.mapotomkow = :mapotomkow AND k.nrkonta != '0' AND k.podatnik = :podatnik AND k.pelnynumer LIKE '5%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findByMaSlownik", query = "SELECT k FROM Konto k WHERE k.idslownika = :idslownika  AND k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findKontoMacierzystyNrkonta", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.rok = :rok AND k.kontomacierzyste = :kontomacierzyste AND k.nrkonta = :nrkonta"),
    @NamedQuery(name = "Konto.findByRozwin", query = "SELECT k FROM Konto k WHERE k.rozwin = :rozwin"),
    @NamedQuery(name = "Konto.updateMapotomkow", query = "UPDATE Konto k SET k.mapotomkow = '0' WHERE k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.wyzerujBowKontach", query = "UPDATE Konto k SET k.boWn = 0.0, k.boMa = 0.0 WHERE k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.wyzerujBoWnwKontach", query = "UPDATE Konto k SET k.boWn = 0.0 WHERE k.podatnik = :podatnik AND k.rok = :rok AND k.bilansowewynikowe = :bilansowewynikowe"),
    @NamedQuery(name = "Konto.wyzerujBoMawKontach", query = "UPDATE Konto k SET k.boMa = 0.0 WHERE k.podatnik = :podatnik AND k.rok = :rok AND k.bilansowewynikowe = :bilansowewynikowe"),
    @NamedQuery(name = "Konto.wyzerujSaldaZaksiegowanewKontach", query = "UPDATE Konto k SET k.saldoWnksiegi = 0.0, k.saldoMaksiegi = 0.0, k.zaksiegowane = 0 WHERE k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.NullBoWnLevel", query = "UPDATE Konto k SET k.boWn = 0.0 WHERE k.podatnik = :podatnik AND k.rok = :rok AND k.level = :level"),
    @NamedQuery(name = "Konto.NullBoMaLevel", query = "UPDATE Konto k SET k.boMa = 0.0 WHERE k.podatnik = :podatnik AND k.rok = :rok AND k.level = :level"),
//    @NamedQuery(name = "Konto.NullObrotyWnLevel", query = "UPDATE Konto k SET k.obrotyWn = 0.0 WHERE k.podatnik = :podatnik AND k.rok = :rok AND k.level = :level"),
//    @NamedQuery(name = "Konto.NullObrotyMaLevel", query = "UPDATE Konto k SET k.obrotyMa = 0.0 WHERE k.podatnik = :podatnik AND k.rok = :rok AND k.level = :level"),
    @NamedQuery(name = "Konto.findlistaKontKasaBank", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '1%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findlistaKontGrupa0", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '0%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findlistaKontGrupa0Analityka", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '0%' AND k.rok = :rok AND k.mapotomkow = '0'"),
    @NamedQuery(name = "Konto.findlistaKontGrupa1", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '1%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findlistaKontGrupa2", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '2%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findlistaKontGrupa3", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '3%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findlistaKontGrupa4", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '4%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findlistaKontGrupa5", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '5%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findlistaKontGrupa6", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '6%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findlistaKontGrupa7", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '7%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findlistaKontGrupa8", query = "SELECT k FROM Konto k WHERE k.podatnik = :podatnik AND k.pelnynumer LIKE '8%' AND k.rok = :rok"),
    @NamedQuery(name = "Konto.updateZablokowane", query = "UPDATE Konto k SET k.blokada = '0' WHERE k.podatnik = :podatnik AND k.rok = :rok"),
    @NamedQuery(name = "Konto.findBySlownikoweMacierzyste", query = "SELECT k FROM Konto k WHERE k.kontomacierzyste = :kontomacierzyste AND k.nrkonta = :nrkonta  AND k.podatnik = :podatnik AND k.rok = :rok")
})
@Cacheable
public class Konto extends ToBeATreeNodeObject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "nrkonta")
    private String nrkonta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "syntetyczne")
    private String syntetyczne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "analityka")
    private int level;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 350)
    @Column(name = "nazwapelna")
    private String nazwapelna;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nazwaskrocona")
    private String nazwaskrocona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "bilansowewynikowe")
    private String bilansowewynikowe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "zwyklerozrachszczegolne")
    private String zwyklerozrachszczegolne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "macierzysty")
    private int macierzysty;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kontomacierzyste",referencedColumnName = "id")
    private Konto kontomacierzyste;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pelnynumer")
    private String pelnynumer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mapotomkow")
    private boolean mapotomkow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rozwin")
    private boolean rozwin;
    @Basic(optional = false)
    @Column(name = "rok")
    private int rok;
    @Basic(optional = false)
    @Column(name = "boWn")
    private double boWn;
    @Basic(optional = false)
    @Column(name = "boMa")
    private double boMa;
    @Transient
    private double obrotyWn;
    @Transient
    private double obrotyMa;
    @Transient
    private double saldoWn;
    @Transient
    private double saldoMa;
    @Basic(optional = false)
    @Column(name = "blokada")
    private boolean blokada;
    @Basic(optional = false)
    @Column(name = "slownikowe")
    private boolean slownikowe;
    /*
    KONTR = 1
    MIEJS = 2
    SAMOC = 3
    MIESI = 4
    DELEK = 5
    DELEZ = 6
    MIEJP = 7
    */
    @Basic(optional = false)
    @Column(name = "idslownika")
    private int idslownika;
    @Column(name="przychod0koszt1")
    private boolean przychod0koszt1;
    @Size(min = 1, max = 255)
    @Column(name = "syntetycznenumer")
    private String syntetycznenumer;
    @Column(name = "de")
    private String de;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kontokategoria",referencedColumnName = "id")
    private Kontokategoria kontokategoria;
    @Column(name = "wnma0wm1ma2")
    private int wnma0wm1ma2;
    @Column(name = "saldoWnksiegi")
    private double saldoWnksiegi;
    @Column(name = "saldoMaksiegi")
    private double saldoMaksiegi;
    @Column(name = "zaksiegowane")
    private boolean zaksiegowane;
    @Column(name = "niewidoczne")
    private boolean niewidoczne;
    @Column(name = "sprawdzono")
    private String sprawdzono;
    @Transient
    private double kwota;
    @Transient
    private String nazwapelnawzorcowy;
    @Transient
    private String nazwaskroconawzorcowy;
    @Size(max = 255)
    @Column(length = 255, name = "pozycjaWn")
    protected String pozycjaWn;
    //0 aktywa
    //1 pasywa
    //99 rzis
    @Column(length = 10, name = "stronaWn")
    protected String stronaWn;
    @Size(max = 255)
    @Column(length = 255, name = "pozycjaMa")
    protected String pozycjaMa;
    //0 aktywa
    //1 pasywa
    //99 rzis
    @Column(length = 10, name = "stronaMa")
    protected String stronaMa;
    @Column(name = "syntetykaanalityka")
    protected String syntetykaanalityka;
    @Column(name = "wynik0bilans1")
    protected boolean wynik0bilans1;
    
    
    
//    @OneToMany(mappedBy = "konto")
//    private List<StronaWiersza> stronaWiersza;


    public Konto() {
        this.slownikowe = false;
        this.idslownika = 0;
    }

    public Konto(Integer id) {
        this.id = id;
    }

    public Konto(Konto old) {
        this.podatnik = old.podatnik;
        this.nrkonta = old.nrkonta;
        this.syntetyczne = old.syntetyczne;
        this.level = old.level;
        this.nazwapelna = old.nazwapelna;
        this.nazwaskrocona = old.nazwaskrocona;
        this.bilansowewynikowe = old.bilansowewynikowe;
        this.zwyklerozrachszczegolne = old.zwyklerozrachszczegolne;
        this.macierzysty = old.macierzysty;
        this.kontomacierzyste = old.kontomacierzyste;
        this.pelnynumer = old.pelnynumer;
        this.mapotomkow = old.mapotomkow;
        this.rozwin = old.rozwin;
        this.rok = old.rok;
        this.boWn = old.boWn;
        this.boMa = old.boMa;
        this.obrotyWn = old.obrotyWn;
        this.obrotyMa = old.obrotyMa;
        this.saldoWn = old.saldoWn;
        this.saldoMa = old.saldoMa;
        this.blokada = old.blokada;
        this.slownikowe = old.slownikowe;
        this.idslownika = old.idslownika;
        this.przychod0koszt1 = old.przychod0koszt1;
        this.syntetycznenumer = old.syntetycznenumer;
        this.de = old.de;
        this.kontokategoria = old.kontokategoria;
        this.wnma0wm1ma2 = old.wnma0wm1ma2;
        this.saldoWnksiegi = old.saldoWnksiegi;
        this.saldoMaksiegi = old.saldoMaksiegi;
        this.zaksiegowane = old.zaksiegowane;
        this.niewidoczne = old.niewidoczne;
    }

    
    

    public Konto(Integer id, Podatnik podatnik, String nrkonta, String syntetyczne, int analityka, String nazwapelna, String nazwaskrocona, 
            String bilansowewynikowe, String zwyklerozrachszczegolne, String macierzyste, String pelnynumer, boolean rozwin, int rok,
            String syntetycznenumer) {
        this.id = id;
        this.podatnik = podatnik;
        this.nrkonta = nrkonta;
        this.syntetyczne = syntetyczne;
        this.level = analityka;
        this.nazwapelna = nazwapelna;
        this.nazwaskrocona = nazwaskrocona;
        this.bilansowewynikowe = bilansowewynikowe;
        this.zwyklerozrachszczegolne = zwyklerozrachszczegolne;
        this.pelnynumer = pelnynumer;
        this.rozwin = rozwin;
        this.rok = rok;
        this.boWn = 0.0;
        this.boMa = 0.0;
        this.slownikowe = false;
        this.idslownika = 0;
        this.syntetycznenumer = syntetycznenumer;
    }   

    public Konto(String podsumowanie, double wn, double ma) {
        this.nazwapelna = podsumowanie;
        this.boWn = wn;
        this.boMa = ma;
    }
    
    public void getAllChildren(List<Konto> listakontwszystkie, Podatnik podatnik, Integer rok, SessionFacade kontoFacade) {
        List<Konto> children = kontoFacade.findKontaPotomnePodatnik(podatnik, rok, this);
        if (!children.isEmpty()) {
            for (Konto o : children) {
                listakontwszystkie.add(o);
                o.getAllChildren(listakontwszystkie,podatnik, rok, kontoFacade);
            }
        }
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getSaldoWnksiegi() {
        return saldoWnksiegi;
    }

    public void setSaldoWnksiegi(double saldoWnksiegi) {
        this.saldoWnksiegi = saldoWnksiegi;
    }

    public double getSaldoMaksiegi() {
        return saldoMaksiegi;
    }

    public void setSaldoMaksiegi(double saldoMaksiegi) {
        this.saldoMaksiegi = saldoMaksiegi;
    }

    public boolean isZaksiegowane() {
        return zaksiegowane;
    }

    public void setZaksiegowane(boolean zaksiegowane) {
        this.zaksiegowane = zaksiegowane;
    }

    public boolean isNiewidoczne() {
        return niewidoczne;
    }

    public void setNiewidoczne(boolean niewidoczne) {
        this.niewidoczne = niewidoczne;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

   
    public String getNrkonta() {
        return nrkonta;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public String getSyntetyczne() {
        return syntetyczne;
    }

    public void setSyntetyczne(String syntetyczne) {
        this.syntetyczne = syntetyczne;
    }

    public String getSyntetycznenumer() {
        return syntetycznenumer;
    }

    public void setSyntetycznenumer(String syntetycznenumer) {
        this.syntetycznenumer = syntetycznenumer;
    }

    public int getWnma0wm1ma2() {
        return wnma0wm1ma2;
    }

    public void setWnma0wm1ma2(int wnma0wm1ma2) {
        this.wnma0wm1ma2 = wnma0wm1ma2;
    }
    
    public String getWnma0wm1ma2S() {
        String zwrot = "wnma";
        if (wnma0wm1ma2 == 1) {
            zwrot = "wn";
        }
        if (wnma0wm1ma2 == 2) {
            zwrot = "ma";
        }
        return zwrot;
    }
    

    @Override
    public int getLevel() {
        return level;
    }
    
  
    @Override
    public void setLevel(int level) {
        this.level = level;
    }


    public String getNazwapelna() {
        return nazwapelna;
    }

    public void setNazwapelna(String nazwapelna) {
        this.nazwapelna = nazwapelna;
    }

    public String getNazwaskrocona() {
        return nazwaskrocona;
    }

    public void setNazwaskrocona(String nazwaskrocona) {
        this.nazwaskrocona = nazwaskrocona;
    }

    public String getBilansowewynikowe() {
        return bilansowewynikowe;
    }

    public void setBilansowewynikowe(String bilansowewynikowe) {
        this.bilansowewynikowe = bilansowewynikowe;
    }

    public String getZwyklerozrachszczegolne() {
        return zwyklerozrachszczegolne;
    }
    
    public String getZwyklerozrachszczegolneS() {
        String zwrot = null;
        switch (zwyklerozrachszczegolne) {
            case "zwykle":
                zwrot = "zw";
                break;
            case "rozrachunkowe":
                zwrot = "roz.";
                break;
            case "szczególne":
                zwrot = "szcz.";
                break;
                
        }
        return zwrot;
    }

    public void setZwyklerozrachszczegolne(String zwyklerozrachszczegolne) {
        this.zwyklerozrachszczegolne = zwyklerozrachszczegolne;
    }

    @Override
    public int getMacierzysty() {
        return macierzysty;
    }

    @Override
    public void setMacierzysty(int macierzysty) {
        this.macierzysty = macierzysty;
    }

    public String getPelnynumer() {
        return pelnynumer;
    }

    public void setPelnynumer(String pelnynumer) {
        this.pelnynumer = pelnynumer;
    }

    public boolean isMapotomkow() {
        return mapotomkow;
    }

    public void setMapotomkow(boolean mapotomkow) {
        this.mapotomkow = mapotomkow;
    }
    
    public boolean getRozwin() {
        return rozwin;
    }

    public void setRozwin(boolean rozwin) {
        this.rozwin = rozwin;
    }
    
    @Override
    public int getLp() {
        return this.id;
    }
    
    @Override
   public void setLp(int lp) {
        
    }
    public int getRok() {
        return rok;
    }
    
    public String getRokSt() {
        return String.valueOf(rok);
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public int getIdslownika() {
        return idslownika;
    }

    public void setIdslownika(int idslownika) {
        this.idslownika = idslownika;
    }


    public double getBoWn() {
        return boWn;
    }

    public void setBoWn(double boWn) {
        this.boWn = boWn;
    }

    public double getBoMa() {
        return boMa;
    }

    public void setBoMa(double boMa) {
        this.boMa = boMa;
    }

    public Kontokategoria getKontokategoria() {
        return kontokategoria;
    }

    public void setKontokategoria(Kontokategoria kontokategoria) {
        this.kontokategoria = kontokategoria;
    }

    public String getSprawdzono() {
        return sprawdzono;
    }

    public void setSprawdzono(String sprawdzono) {
        this.sprawdzono = sprawdzono;
    }

   

    public boolean isBlokada() {
        return blokada;
    }

    public void setBlokada(boolean blokada) {
        this.blokada = blokada;
    }

    public boolean isSlownikowe() {
        return slownikowe;
    }

    public void setSlownikowe(boolean slownikowe) {
        this.slownikowe = slownikowe;
    }
  
//    public List<StronaWiersza> getStronaWiersza() {
//        return stronaWiersza;
//    }
//
//    public void setStronaWiersza(List<StronaWiersza> stronaWiersza) {
//        this.stronaWiersza = stronaWiersza;
//    }

    public double getObrotyWn() {
        return obrotyWn;
    }

    public void setObrotyWn(double obrotyWn) {
        this.obrotyWn = obrotyWn;
    }

    public double getObrotyMa() {
        return obrotyMa;
    }

    public void setObrotyMa(double obrotyMa) {
        this.obrotyMa = obrotyMa;
    }

    public double getSaldoWn() {
        return saldoWn;
    }

    public String getNazwapelnawzorcowy() {
        return nazwapelnawzorcowy;
    }

    public void setNazwapelnawzorcowy(String nazwapelnawzorcowy) {
        this.nazwapelnawzorcowy = nazwapelnawzorcowy;
    }

    public String getNazwaskroconawzorcowy() {
        return nazwaskroconawzorcowy;
    }

    public void setNazwaskroconawzorcowy(String nazwaskroconawzorcowy) {
        this.nazwaskroconawzorcowy = nazwaskroconawzorcowy;
    }

    public void setSaldoWn(double saldoWn) {
        this.saldoWn = saldoWn;
    }

    public double getSaldoMa() {
        return saldoMa;
    }

    public void setSaldoMa(double saldoMa) {
        this.saldoMa = saldoMa;
    }

    public boolean isPrzychod0koszt1() {
        return przychod0koszt1;
    }

    public void setPrzychod0koszt1(boolean przychod0koszt1) {
        this.przychod0koszt1 = przychod0koszt1;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public Konto getKontomacierzyste() {
        return kontomacierzyste;
    }

    public void setKontomacierzyste(Konto kontomacierzyste) {
        this.kontomacierzyste = kontomacierzyste;
    }

    public String getPozycjaWn() {
        return pozycjaWn;
    }

    public void setPozycjaWn(String pozycjaWn) {
        this.pozycjaWn = pozycjaWn;
    }

    public String getStronaWn() {
        return stronaWn;
    }

    public void setStronaWn(String stronaWn) {
        this.stronaWn = stronaWn;
    }

    public String getPozycjaMa() {
        return pozycjaMa;
    }

    public void setPozycjaMa(String pozycjaMa) {
        this.pozycjaMa = pozycjaMa;
    }

    public String getStronaMa() {
        return stronaMa;
    }

    public void setStronaMa(String stronaMa) {
        this.stronaMa = stronaMa;
    }

    public String getSyntetykaanalityka() {
        return syntetykaanalityka;
    }

    public void setSyntetykaanalityka(String syntetykaanalityka) {
        this.syntetykaanalityka = syntetykaanalityka;
    }

    public boolean isWynik0bilans1() {
        return wynik0bilans1;
    }

    public void setWynik0bilans1(boolean wynik0bilans1) {
        this.wynik0bilans1 = wynik0bilans1;
    }
   
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.podatnik);
        hash = 97 * hash + Objects.hashCode(this.pelnynumer);
        return hash;
    }
    
   

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Konto other = (Konto) obj;
        if (Objects.equals(this.podatnik, other.podatnik) && !Objects.equals(this.pelnynumer, other.pelnynumer)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Konto{" + "id=" + id + ", rok=" + rok +", podatnik=" + podatnik.getPrintnazwa() + ", nrkonta=" + pelnynumer + ", nazwa=" + nazwapelna + ", syntetyczne=" + syntetyczne + ", nazwaskrocona=" + nazwaskrocona + ", bilansowewynikowe=" + bilansowewynikowe + ", zwyklerozrachszczegolne=" + zwyklerozrachszczegolne + '}';
    }

        
    public String getNazwaKontaInt() {
        String zwrot = this.getNazwaskrocona();
        String lokale = LocaleInfo.getLocale();
        if (lokale.equals("de")) {
            zwrot = this.getDe();
        }
        return zwrot;
    }
    
    public String getNumerNazwaInt() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getPelnynumer());
        sb.append(" ");
        sb.append(this.getNazwaKontaInt());
        return sb.toString();
    }

    public Konto getSyntetyczne(List<Konto> syntetyczne) {
        for (Konto p : syntetyczne) {
            if (p.getPelnynumer().equals(this.syntetycznenumer)) {
                return p;
            }
        }
        return null;
    }
    
    public Konto getSyntetyczne0(List<Konto> syntetyczne) {
        for (Konto p : syntetyczne) {
            if (p.getPelnynumer().equals(this.pelnynumer)) {
                return p;
            }
        }
        return null;
    }
    
    public String getNumerNazwa() {
        StringBuilder s = new StringBuilder();
        s.append(this.pelnynumer);
        s.append("-");
        s.append(this.nazwapelna);
        return s.toString();
    }
    
     public String getNumerNazwaMacierzyste() {
        StringBuilder s = new StringBuilder();
        if (this.kontomacierzyste != null) {
            s.append("macierzyste ");
            s.append(this.kontomacierzyste.pelnynumer);
            s.append("-");
            s.append(this.kontomacierzyste.nazwapelna);
        }
        return s.toString();
    }
    
    public String getNazwaObcieta(int ilepokazac) {
        String zwrot = this.getNazwapelna();
        if (zwrot.length() > ilepokazac) {
            zwrot = zwrot.substring(0,ilepokazac);
        }
        return zwrot;
    }
//  public static void main(String[] args) throws Exception  {
//        EntityManager em = Em.getEm();
//        List<Object> konta = em.createNamedQuery("Konto.findAll").getResultList();
//        for (Iterator<Object> it = konta.iterator(); it.hasNext();) {
//            Konto p = (Konto) it.next();
//            int macnr = p.getMacierzysty();
//            if (macnr != 0) {
//                String macnrs = p.getMacierzyste();
//                //System.out.println("nr mac "+macnrs+" podatnik "+p.getPodatnik());
//                Konto mac = (Konto) em.createNamedQuery("Konto.findById").setParameter("id", macnr).getSingleResult();
//                p.setKontomacierzyste(mac);
//                //System.out.println("nr mac2 "+mac.getPelnynumer() +" podatnik "+mac.podatnik);
//                if (!mac.getPelnynumer().equals(macnrs)) {
//                    throw new Exception();
//                }
//            }
//        }
//        System.out.println("zachowanie");
//        Em.saveList(em, konta);
//        System.out.println("koniec");
//    }

    public void dodajpotomkaBO(Konto k) {
        this.setBoWn(this.getBoWn() + k.getBoWn());
        this.setBoMa(this.getBoMa() + k.getBoMa());
    }

    public Konto getTopKonto() {
        Konto zwrot = this;
       if (this.getKontomacierzyste()!=null) {
           zwrot = this.getKontomacierzyste().getTopKonto();
       }
       return zwrot;
    }
    
    public void czyscPozycje() {
        this.setPozycjaWn(null);
        this.setPozycjaMa(null);
        this.setStronaWn(null);
        this.setStronaMa(null);
        this.setSyntetykaanalityka(null);
    }
    
    public void kopiujPozycje(Konto p) {
        this.setPozycjaWn(p.getPozycjaWn());
        this.setPozycjaMa(p.getPozycjaMa());
        this.setStronaWn(p.getStronaWn());
        this.setStronaMa(p.getStronaMa());
        this.setSyntetykaanalityka(p.getSyntetykaanalityka());
        this.setWynik0bilans1(p.isWynik0bilans1());
    }
    
    public void kopiujPozycje(Konto p, String wnma) {
        if (wnma.equals("wn")) {
            this.setPozycjaWn(p.getPozycjaWn());
            this.setStronaWn(p.getStronaWn());
        } else {
            this.setPozycjaMa(p.getPozycjaMa());
            this.setStronaMa(p.getStronaMa());
        }
        this.setSyntetykaanalityka(p.getSyntetykaanalityka());
        this.setWynik0bilans1(p.isWynik0bilans1());
    }
    
    public void naniesPozycje(KontopozycjaZapis p) {
        this.setPozycjaWn(p.getPozycjaWn());
        this.setPozycjaMa(p.getPozycjaMa());
        this.setStronaWn(p.getStronaWn());
        this.setStronaMa(p.getStronaMa());
        this.setSyntetykaanalityka(p.getSyntetykaanalityka());
        this.setWynik0bilans1(p.isWynik0bilans1());
    }
   
    public void kontopozycjaBiezacaWn (String wybranapozycja,String numer, String wynikoweszczegolne) {
        this.setPozycjaWn(wybranapozycja);
        this.setStronaWn(numer);
        this.setSyntetykaanalityka(wynikoweszczegolne);
    }
    
    public void kontopozycjaBiezacaMa (String wybranapozycja, String numer, String wynikoweszczegolne) {
        this.setPozycjaMa(wybranapozycja);
        this.setStronaMa(numer);
        this.setSyntetykaanalityka(wynikoweszczegolne);
    }
    
}
