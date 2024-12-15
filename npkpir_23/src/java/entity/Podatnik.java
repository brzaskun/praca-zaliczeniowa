/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Parametr;
import embeddable.Pozycjenafakturzebazadanych;
import embeddable.Straty1;
import embeddable.Udzialy;
import entityfk.MiejsceKosztow;
import entityfk.SprawozdanieFinansowe;
import enumy.FormaPrawna;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podatnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Podatnik.findAll", query = "SELECT p FROM Podatnik p"),
    @NamedQuery(name = "Podatnik.findByNip", query = "SELECT p FROM Podatnik p WHERE p.nip = :nip"),
    @NamedQuery(name = "Podatnik.findByPesel", query = "SELECT p FROM Podatnik p WHERE p.pesel = :pesel"),
    @NamedQuery(name = "Podatnik.findByRegon", query = "SELECT p FROM Podatnik p WHERE p.regon = :regon"),
    @NamedQuery(name = "Podatnik.findByDataurodzenia", query = "SELECT p FROM Podatnik p WHERE p.dataurodzenia = :dataurodzenia"),
    @NamedQuery(name = "Podatnik.findByEmail", query = "SELECT p FROM Podatnik p WHERE p.email = :email"),
    @NamedQuery(name = "Podatnik.findByFax", query = "SELECT p FROM Podatnik p WHERE p.fax = :fax"),
    @NamedQuery(name = "Podatnik.findByGmina", query = "SELECT p FROM Podatnik p WHERE p.gmina = :gmina"),
    @NamedQuery(name = "Podatnik.findByImie", query = "SELECT p FROM Podatnik p WHERE p.imie = :imie"),
    @NamedQuery(name = "Podatnik.findByKodpocztowy", query = "SELECT p FROM Podatnik p WHERE p.kodpocztowy = :kodpocztowy"),
    @NamedQuery(name = "Podatnik.findByMetodakasowa", query = "SELECT p FROM Podatnik p WHERE p.metodakasowa = :metodakasowa"),
    @NamedQuery(name = "Podatnik.findByMiejscowosc", query = "SELECT p FROM Podatnik p WHERE p.miejscowosc = :miejscowosc"),
    @NamedQuery(name = "Podatnik.findByNazwapelna", query = "SELECT p FROM Podatnik p WHERE p.nazwapelna = :nazwapelna"),
    @NamedQuery(name = "Podatnik.findByNazwisko", query = "SELECT p FROM Podatnik p WHERE p.nazwisko = :nazwisko"),
    @NamedQuery(name = "Podatnik.findByNrdomu", query = "SELECT p FROM Podatnik p WHERE p.nrdomu = :nrdomu"),
    @NamedQuery(name = "Podatnik.findByNrlokalu", query = "SELECT p FROM Podatnik p WHERE p.nrlokalu = :nrlokalu"),
    @NamedQuery(name = "Podatnik.findByPoczta", query = "SELECT p FROM Podatnik p WHERE p.poczta = :poczta"),
    @NamedQuery(name = "Podatnik.findByPowiat", query = "SELECT p FROM Podatnik p WHERE p.powiat = :powiat"),
    @NamedQuery(name = "Podatnik.findByTelefonkontaktowy", query = "SELECT p FROM Podatnik p WHERE p.telefonkontaktowy = :telefonkontaktowy"),
    @NamedQuery(name = "Podatnik.findByUlica", query = "SELECT p FROM Podatnik p WHERE p.ulica = :ulica"),
    @NamedQuery(name = "Podatnik.findByUrzadskarbowy", query = "SELECT p FROM Podatnik p WHERE p.urzadskarbowy = :urzadskarbowy"),
    @NamedQuery(name = "Podatnik.findByVatokres", query = "SELECT p FROM Podatnik p WHERE p.vatokres = :vatokres"),
    @NamedQuery(name = "Podatnik.findByFirmafk", query = "SELECT p FROM Podatnik p WHERE p.firmafk = :firmafk AND p.podmiotaktywny = true"),
    @NamedQuery(name = "Podatnik.findByZUS", query = "SELECT p FROM Podatnik p WHERE p.wysylkazusmail = true AND p.podmiotaktywny = true"),
    @NamedQuery(name = "Podatnik.findByPodmiotaktywny", query = "SELECT p FROM Podatnik p WHERE p.podmiotaktywny = true"),
    @NamedQuery(name = "Podatnik.findByPodmiotnowy", query = "SELECT p FROM Podatnik p WHERE p.nowypodmiot = TRUE"),
    @NamedQuery(name = "Podatnik.findByPodmiotaktywnyPrzyporzadkowany", query = "SELECT p FROM Podatnik p WHERE p.podmiotaktywny = :podmiotaktywny AND p.ksiegowa IS NOT NULL"),
    @NamedQuery(name = "Podatnik.findByKsiegowa", query = "SELECT p FROM Podatnik p WHERE p.ksiegowa = :ksiegowa"),
    @NamedQuery(name = "Podatnik.findByWojewodztwo", query = "SELECT p FROM Podatnik p WHERE p.wojewodztwo = :wojewodztwo")})
public class Podatnik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "nip", unique = true)
    private String nip;
    @Size(max = 255)
    @Column(name = "PESEL")
    @Pattern(regexp="\\d+",message="Pesel składa się wyłącznie z cyfr")
    private String pesel;
    @Column(name = "REGON")
    @Pattern(regexp="\\d+",message="Regon składa się wyłącznie z cyfr")
    private String regon;
    @Size(max = 255)
    @Column(name = "DATAURODZENIA")
    private String dataurodzenia;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Nieprawidłowy adres email")
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    //drugiemail
    @Column(name = "FAX")
    private String fax;
    @Size(max = 255)
    @Column(name = "GMINA")
    private String gmina;
    @Size(max = 255)
    @Column(name = "IMIE")
    private String imie;
    @Size(max = 255)
    @Column(name = "KODPOCZTOWY")
    private String kodpocztowy;
    @Size(max = 255)
    @Column(name = "METODAKASOWA")
    private String metodakasowa;
    @Column(name = "metodakasowapit")
    private boolean metodakasowapit;
    @Size(max = 255)
    @Column(name = "MIEJSCOWOSC")
    private String miejscowosc;
    @Size(max = 255)
    @Column(name = "NAZWAPELNA")
    private String nazwapelna;
    @Size(max = 255)
    @Column(name = "NAZWISKO")
    private String nazwisko;
    @Size(max = 255)
    @Column(name = "NRDOMU")
    private String nrdomu;
    @Size(max = 255)
    @Column(name = "NRLOKALU")
    private String nrlokalu;
    @Size(max = 255)
    @Column(name = "POCZTA")
    private String poczta;
    @Size(max = 255)
    @Column(name = "POWIAT")
    private String powiat;
//    @Pattern(regexp="[\\d]{3}[-]{1,1}[\\d]{3}[-]{1,1}[\\d]{3}", message="Nieprawidłowy numer telefonu, winien być: xxx-xxx-xxx")
    @Size(max = 255)
    @Column(name = "TELEFONKONTAKTOWY")
    private String telefonkontaktowy;
    @Size(max = 255)
    @Column(name = "ULICA")
    private String ulica;
    @Size(max = 255)
    @Column(name = "URZADSKARBOWY")
    private String urzadskarbowy;
    @Lob
    @Column(name = "VATOKRES")
    private List<Parametr> vatokres;
    @Size(max = 255)
    @Column(name = "WOJEWODZTWO")
    private String wojewodztwo;
//    @Lob
//    @Column(name = "zusparametr")
//    private List<Zusstawki> zusparametr;
    @Lob
    @Column(name = "remanent")
    private List<Parametr> remanent;
//    @Lob
//    @Column(name="dokumentyksiegowe")
//    private List<Rodzajedok> dokumentyksiegowe;
    @Lob
    @Column(name = "kwotaautoryzujaca")
    private List<Parametr> kwotaautoryzujaca;
    @Lob
    @Column(name = "zawieszeniedzialalnosci")
    private List<Parametr> zawieszeniedzialalnosci;
    @Column(name = "pole47")
    private String pole47;
    @Lob
    @Column(name = "opisypkpir")
    private List opisypkpir;
    @Lob
    @Column(name = "udzialy")
    private List<Udzialy> udzialy;
    @Lob
    @Column(name = "stratyzlatub1")
    private List<Straty1> stratyzlatub1;
    @Lob
    @Column(name = "numerpkpir")
    private List<Parametr> numerpkpir;
    @Size(max = 34)
    @Column(name = "nrkontabankowego")
    private String nrkontabankowego;
    @Size(max = 45)
    @Column(name = "swift")
    private String swift;
    @Size (max = 3)
    @Column(name = "platnoscwdni")
    private String platnoscwdni;
    @Size (max = 512)
    @Column(name = "nazwadlafaktury")
    private String nazwadlafaktury;
    @Size (max = 512)
    @Column(name = "nazwadlafaktury2")
    private String nazwadlafaktury2;
    @Column(name = "adresdlafaktury")
    private String adresdlafaktury;
    @Column(name = "nipdlafaktury")
    private String nipdlafaktury;
    @Lob
    @Column(name = "wierszwzorcowy")
    private Pozycjenafakturzebazadanych wierszwzorcowy;
    @Size (max = 512)
    @Column(name = "miejscewystawienia")
    private String miejscewystawienia;
    @Size (max = 512)
    @Column(name = "wystawcafaktury")
    private String wystawcafaktury;
    @Size (max = 512)
    @Column(name = "schematnumeracji")
    private String schematnumeracji;
    //nie fk 0
    //f 1
    //pkpir i fk 3
    @Column(name = "firmafk")
    private int firmafk;
    @Column(name = "podmiotaktywny")
    private boolean podmiotaktywny;
    @Column(name = "wysylkazusmail")
    private boolean wysylkazusmail;
    @Column(name = "odliczeniezus51")
    private boolean odliczeniezus51;
    @Column(name = "odliczeniezus52")
    private boolean odliczeniezus52;
    @Column(name = "datamalyzus")
    private String datamalyzus;
    @JoinColumn(name = "podatnik")
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<ParamCzworkiPiatki> paramCzworkiPiatki;
    @JoinColumn(name = "podid")
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<PodatnikUdzialy> wykazudzialow;
    @JoinColumn(name = "podatnik")
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<Zusstawkinew> zusstawkinowe;
    @OneToMany(mappedBy = "podatnikObj")
    private List<MiejsceKosztow> miejsceKosztowList;
    @Column(name = "formaprawna")
    @Enumerated(EnumType.STRING)
    private FormaPrawna formaPrawna;
    @JoinColumn(name = "wprowadzil", referencedColumnName = "login")
    @ManyToOne
    private Uz wprowadzil;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datawprowadzenia")
    private Date datawprowadzenia;
    @JoinColumn(name = "ksiegowa", referencedColumnName = "login")
    @ManyToOne
    private Uz ksiegowa;
    @JoinColumn(name = "kadrowa", referencedColumnName = "login")
    @ManyToOne
    private Uz kadrowa;
    //wzorcowe one to many unidirectional/ nazwa kolumny to nazwa tworzona w tabeli ParamVatUE, nie trzeba zmiennej w ParamVatUE
    @JoinColumn(name = "podid")
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<ParamVatUE> paramVatUE;
    @JoinColumn(name = "podid")
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<ParamProcentVat> paramProcentVat;
    @JoinColumn(name = "podid")
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<ParamDeklVatNadwyzka> paramDeklVatNadwyzka;
    @Column(name = "datarozpoczecia")
    @Size(max = 10)
    private String datarozpoczecia;
    @Column(name = "datazawieszenia")
    @Size(max = 10)
    private String datazawieszenia;
    @Column(name = "datawznowienia")
    @Size(max = 10)
    private String datawznowienia;
    @Column(name = "datalikwidacji")
    @Size(max = 10)
    private String datalikwidacji;
    @OneToMany(mappedBy = "podatnikObj")
    private List<PodatnikOpodatkowanieD> podatnikOpodatkowanieD;
    @OneToMany(mappedBy = "podatnik")
    private List<SprawozdanieFinansowe> elementysprawozdaniafin;
    @Column(name = "jestwlikiwdacji")
    private boolean jestwlikiwdacji;
    @Size(max = 512)
    @Column(name = "printnazwa")
    private String printnazwa;
    @Column(name = "podpiscertyfikowany")
    private boolean podpiscertyfikowany;
    @Size(max = 6)
    @Column(name = "kodubezpieczeniaZUS")
    private String kodubezpieczeniaZUS;
    @Column(name = "tylkodlaZUS")
    private boolean tylkodlaZUS;
    @Column(name = "zatrudniapracownikow")
    private boolean zatrudniapracownikow;
    @Size(max = 10)
    @Column(name = "dataotwarcialikwidacji")
    private String dataotwarcialikwidacji;
    @Size(max = 2)
    @Column(name = "jezykmaila")
    private String jezykmaila;
    @Column(name = "kartacert")
    private String kartacert;
    @Column(name = "nazwaRejestr")
    private String nazwaRejestr;
    @Size(min = 5)
    @Column(name = "kodPKD", nullable = true)
    private String kodPKD;
    @Column(name = "kartapesel")
    private String kartapesel;
    @Column(name = "starekadry")
    private boolean starekadry;
    @Column(name = "rozrachunkifk")
    private boolean rozrachunkifk;
    @Column(name = "gussymbol")
    private String gussymbol;
    @Column(name = "polecajacy")
    private String polecajacy;
    @Column(name = "nowypodmiot")
    private boolean nowypodmiot;
    
    @Column(name = "umowalokal")
    private boolean umowalokal;
    @Column(name = "vatr")
    private boolean vatr;
    @Column(name = "vatue")
    private boolean vatue;
    @Column(name = "opisdzialalnosci")
    private boolean opisdzialalnosci;
    @Column(name = "kontobankowe")
    private boolean kontobankowe;
    @Column(name = "ppo")
    private boolean ppo;
    @Column(name = "pel")
    private boolean pel;
    @Column(name = "umowa")
    private boolean umowa;
    @Column(name = "faktura")
    private boolean faktura;
    @Column(name = "zua")
    private boolean zua;
    @Column(name = "modulfaktur")
    private boolean modulfaktur;
    @Column(name = "upl")
    private boolean upl;
    @Size (max = 10)
    @Column(name = "koniecumowy")
    private String koniecumowy;
    @Size (max = 10)
    @Column(name = "wydaniedokumentow")
    private String wydaniedokumentow;
    @Column(name = "wysylaczestdok")
    private boolean wysylaczestdok;
    @Column(name = "niesprawdzajfaktury")
    private boolean niesprawdzajfaktury;
    @Column(name = "domyslnametodaplatnosci")
    private String domyslnametodaplatnosci;
    @Column(name = "zwolnienievat43")
    private boolean zwolnienievat43;
    @Column(name = "kasafiskalna")
    private boolean kasafiskalna;
    
    @JoinColumn(name = "finanzamt", referencedColumnName = "id")
    @ManyToOne
    private Finanzamt finanzamt;
    @Column(name = "steuernummer")
    private String steuernummer;
    @Transient
    private double cena;
    @Transient
    private double cenakadry;
    @Transient
    private double liczbadok;
    @Transient
    private double liczbawierszy;
    @Transient
    private double liczbadokimport;
    @Transient
    private double liczbawierszyimport;

    
    public Podatnik() {
        this.podmiotaktywny = true;
        this.jezykmaila = "pl";
        this.domyslnametodaplatnosci = "przelew";
    }

    public Podatnik(String nip) {
        this.nip = nip;
        this.podmiotaktywny = true;
        this.jezykmaila = "pl";
        this.domyslnametodaplatnosci = "przelew";
    }
    
    public String podatnikDaneWydruk() {
        StringBuilder s = new StringBuilder();
        s.append(this.printnazwa);
        s.append(" ");
        if (adresdlafaktury != null && adresdlafaktury.length() > 1)  {
            s.append(this.adresdlafaktury);
            s.append(" ");
        } else {
            s.append(this.kodpocztowy);
            s.append(" ");
            s.append(this.miejscowosc);
            s.append(" ");
            s.append(this.ulica);
            s.append(" ");
            s.append(this.nrdomu);
            s.append("/");
            s.append(this.nrlokalu);
            s.append(" ");
        }
        s.append("NIP ");
        s.append(this.nip);
        return s.toString();
    }
    
    public boolean sadwienazwydlafaktury() {
        boolean zwrot = false;
        if (nazwadlafaktury2!=null && !nazwadlafaktury2.equals("")) {
            zwrot = true;
        }
        return zwrot;
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getAdres() {
        return kodpocztowy+" "+miejscowosc+" "+ulica+" "+nrdomu+"/"+nrlokalu;
    }
    public String getSchematnumeracji() {
        return schematnumeracji;
    }

    public boolean isStarekadry() {
        return starekadry;
    }

    public void setStarekadry(boolean starekadry) {
        this.starekadry = starekadry;
    }

    public List<PodatnikUdzialy> getWykazudzialow() {
        return wykazudzialow;
    }

    public void setWykazudzialow(List<PodatnikUdzialy> wykazudzialow) {
        this.wykazudzialow = wykazudzialow;
    }

    
    public List<Zusstawkinew> getZusstawkinowe() {
        return zusstawkinowe;
    }

    public void setZusstawkinowe(List<Zusstawkinew> zusstawkinowe) {
        this.zusstawkinowe = zusstawkinowe;
    }

    public String getSteuernummer() {
        return steuernummer;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public void setSteuernummer(String steuernummer) {
        this.steuernummer = steuernummer;
    }

    public Finanzamt getFinanzamt() {
        return finanzamt;
    }

    public void setFinanzamt(Finanzamt finanzamt) {
        this.finanzamt = finanzamt;
    }

   
    
    public String getKodPKD() {
        return kodPKD;
    }

    public void setKodPKD(String kodPKD) {
        this.kodPKD = kodPKD;
    }

    public String getNazwaRejestr() {
        return nazwaRejestr;
    }

    public void setNazwaRejestr(String nazwaRejestr) {
        this.nazwaRejestr = nazwaRejestr;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getCenakadry() {
        return cenakadry;
    }

    public void setCenakadry(double cenakadry) {
        this.cenakadry = cenakadry;
    }

    
    public String getKartacert() {
        return kartacert;
    }

    public void setKartacert(String kartacert) {
        this.kartacert = kartacert;
    }

    public String getKartapesel() {
        return kartapesel;
    }

    public void setKartapesel(String kartapesel) {
        this.kartapesel = kartapesel;
    }

    public String getJezykmaila() {
        return jezykmaila;
    }

    public void setJezykmaila(String jezykmaila) {
        this.jezykmaila = jezykmaila;
    }

    public String getDataotwarcialikwidacji() {
        return dataotwarcialikwidacji;
    }

    public void setDataotwarcialikwidacji(String dataotwarcialikwidacji) {
        this.dataotwarcialikwidacji = dataotwarcialikwidacji;
    }

    public boolean isPodpiscertyfikowany() {
        return podpiscertyfikowany;
    }

    public void setPodpiscertyfikowany(boolean podpiscertyfikowany) {
        this.podpiscertyfikowany = podpiscertyfikowany;
    }

    public String getPrintnazwa() {
        return printnazwa;
    }
    
    public String getPrintnazwaShort() {
        String zwrot = printnazwa;
        if (printnazwa != null && printnazwa.length() > 15) {
            zwrot = printnazwa.substring(0, 15);
        }
        return zwrot;
    }

    public List<ParamProcentVat> getParamProcentVat() {
        return paramProcentVat;
    }

    public void setParamProcentVat(List<ParamProcentVat> paramProcentVat) {
        this.paramProcentVat = paramProcentVat;
    }

    public void setPrintnazwa(String printnazwa) {
        this.printnazwa = printnazwa;
    }

    public boolean isJestwlikiwdacji() {
        return jestwlikiwdacji;
    }

    public void setJestwlikiwdacji(boolean jestwlikiwdacji) {
        this.jestwlikiwdacji = jestwlikiwdacji;
    }

    public List<SprawozdanieFinansowe> getElementysprawozdaniafin() {
        return elementysprawozdaniafin;
    }

    public void setElementysprawozdaniafin(List<SprawozdanieFinansowe> elementysprawozdaniafin) {
        this.elementysprawozdaniafin = elementysprawozdaniafin;
    }

    public void setSchematnumeracji(String schematnumeracji) {
        this.schematnumeracji = schematnumeracji;
    }

    public List<PodatnikOpodatkowanieD> getPodatnikOpodatkowanieD() {
        return podatnikOpodatkowanieD;
    }

    public void setPodatnikOpodatkowanieD(List<PodatnikOpodatkowanieD> podatnikOpodatkowanieD) {
        this.podatnikOpodatkowanieD = podatnikOpodatkowanieD;
    }

    public String getDatarozpoczecia() {
        return datarozpoczecia;
    }

    public void setDatarozpoczecia(String datarozpoczecia) {
        this.datarozpoczecia = datarozpoczecia;
    }

    public Uz getKsiegowa() {
        return ksiegowa;
    }

    public void setKsiegowa(Uz ksiegowa) {
        this.ksiegowa = ksiegowa;
    }

    public List<ParamVatUE> getParamVatUE() {
        return paramVatUE;
    }

    public void setParamVatUE(List<ParamVatUE> paramVatUE) {
        this.paramVatUE = paramVatUE;
    }

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public Date getDatawprowadzenia() {
        return datawprowadzenia;
    }

    public void setDatawprowadzenia(Date datawprowadzenia) {
        this.datawprowadzenia = datawprowadzenia;
    }

    public boolean isOdliczeniezus51() {
        return odliczeniezus51;
    }

    public void setOdliczeniezus51(boolean odliczeniezus51) {
        this.odliczeniezus51 = odliczeniezus51;
    }

    public boolean isOdliczeniezus52() {
        return odliczeniezus52;
    }

    public void setOdliczeniezus52(boolean odliczeniezus52) {
        this.odliczeniezus52 = odliczeniezus52;
    }

    public FormaPrawna getFormaPrawna() {
        return formaPrawna;
    }

    public String getDatamalyzus() {
        return datamalyzus;
    }

    public void setDatamalyzus(String datamalyzus) {
        this.datamalyzus = datamalyzus;
    }

    public void setFormaPrawna(FormaPrawna formaPrawna) {
        this.formaPrawna = formaPrawna;
    }

    public String getWystawcafaktury() {
        return wystawcafaktury;
    }

    public void setWystawcafaktury(String wystawcafaktury) {
        this.wystawcafaktury = wystawcafaktury;
    }

 
    public boolean isWysylkazusmail() {
        return wysylkazusmail;
    }

    public List<ParamCzworkiPiatki> getParamCzworkiPiatki() {
        return paramCzworkiPiatki;
    }

    public void setParamCzworkiPiatki(List<ParamCzworkiPiatki> paramCzworkiPiatki) {
        this.paramCzworkiPiatki = paramCzworkiPiatki;
    }

    public void setWysylkazusmail(boolean wysylkazusmail) {
        this.wysylkazusmail = wysylkazusmail;
    }

    public boolean isPodmiotaktywny() {
        return podmiotaktywny;
    }

    public void setPodmiotaktywny(boolean podmiotaktywny) {
        this.podmiotaktywny = podmiotaktywny;
    }

    public int getFirmafk() {
        return firmafk;
    }

    public void setFirmafk(int firmafk) {
        this.firmafk = firmafk;
    }

    public double getLiczbadokimport() {
        return liczbadokimport;
    }

    public void setLiczbadokimport(double liczbadokimport) {
        this.liczbadokimport = liczbadokimport;
    }

    public double getLiczbawierszyimport() {
        return liczbawierszyimport;
    }

    public void setLiczbawierszyimport(double liczbawierszyimport) {
        this.liczbawierszyimport = liczbawierszyimport;
    }

   
    
    public String getNip() {
        return nip;
    }
    
    public void setNip(String nip) {
        this.nip = nip;
    }
    
    public String getPesel() {
        return pesel;
    }
    
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
    
    public String getRegon() {
        return regon;
    }
    
    public void setRegon(String regon) {
        this.regon = regon;
    }
    
    public String getDataurodzenia() {
        return dataurodzenia;
    }
    
    public void setDataurodzenia(String dataurodzenia) {
        this.dataurodzenia = dataurodzenia;
    }
    
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String getGmina() {
        return gmina;
    }
    
    public void setGmina(String gmina) {
        this.gmina = gmina;
    }
    
    public String getImie() {
        return imie;
    }
    
    public void setImie(String imie) {
        this.imie = imie;
    }
    
    public String getKodpocztowy() {
        return kodpocztowy;
    }
    
    public void setKodpocztowy(String kodpocztowy) {
        this.kodpocztowy = kodpocztowy;
    }
    
    public String getMetodakasowa() {
        return metodakasowa;
    }
    
    public void setMetodakasowa(String metodakasowa) {
        this.metodakasowa = metodakasowa;
    }
    
    public String getMiejscowosc() {
        return miejscowosc;
    }
    
    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }
    
     public String getNazwapelnaPDF() {
        if (this.isJestwlikiwdacji()) {
            return printnazwa +" W LIKWIDACJI";
        }
        return printnazwa;
    }
    
    public String getNazwapelna() {
        return nazwapelna;
    }
    
    public void setNazwapelna(String nazwapelna) {
        this.nazwapelna = nazwapelna;
    }
    
    public String getNazwisko() {
        return nazwisko;
    }
    
    public String getNazwiskoImie() {
        return nazwisko+" "+imie;
    }
    
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    
    public String getNrdomu() {
        return nrdomu;
    }
    
    public void setNrdomu(String nrdomu) {
        this.nrdomu = nrdomu;
    }
    
    public String getNrlokalu() {
        return nrlokalu;
    }
    
    public void setNrlokalu(String nrlokalu) {
        this.nrlokalu = nrlokalu;
    }
    
    public String getPoczta() {
        return poczta;
    }
    
    public void setPoczta(String poczta) {
        this.poczta = poczta;
    }
    
     public String getPowiat() {
        return powiat;
    }
    
    public void setPowiat(String powiat) {
        this.powiat = powiat;
    }
    
    public String getTelefonkontaktowy() {
        return telefonkontaktowy;
    }
    
    public void setTelefonkontaktowy(String telefonkontaktowy) {
        this.telefonkontaktowy = telefonkontaktowy;
    }
    
    public String getUlica() {
        return ulica;
    }
    
    public void setUlica(String ulica) {
        this.ulica = ulica;
    }
    
    public String getUrzadskarbowy() {
        return urzadskarbowy;
    }
    
    public void setUrzadskarbowy(String urzadskarbowy) {
        this.urzadskarbowy = urzadskarbowy;
    }
    
    public List<Parametr> getVatokres() {
        return vatokres;
    }
    
    public void setVatokres(List<Parametr> vatokres) {
        this.vatokres = vatokres;
    }

    public List<Parametr> getZawieszeniedzialalnosci() {
        return zawieszeniedzialalnosci;
    }

    public void setZawieszeniedzialalnosci(List<Parametr> zawieszeniedzialalnosci) {
        this.zawieszeniedzialalnosci = zawieszeniedzialalnosci;
    }

    public boolean isRozrachunkifk() {
        return rozrachunkifk;
    }

    public void setRozrachunkifk(boolean rozrachunkifk) {
        this.rozrachunkifk = rozrachunkifk;
    }
    
    
    
    public String getWojewodztwo() {
        return wojewodztwo;
    }
    
    public void setWojewodztwo(String wojewodztwo) {
        this.wojewodztwo = wojewodztwo;
    }
    
//    public List<Zusstawki> getZusparametr() {
//        return null;
//    }
    
//    public void setZusparametr(List<Zusstawki> zusparametr) {
//        this.zusparametr = zusparametr;
//    }
    
    public List<Parametr> getRemanent() {
        return remanent;
    }
    
    public void setRemanent(List<Parametr> remanent) {
        this.remanent = remanent;
    }

    public List<ParamDeklVatNadwyzka> getParamDeklVatNadwyzka() {
        return paramDeklVatNadwyzka;
    }

    public void setParamDeklVatNadwyzka(List<ParamDeklVatNadwyzka> paramDeklVatNadwyzka) {
        this.paramDeklVatNadwyzka = paramDeklVatNadwyzka;
    }
    
 
    
    public List<Parametr> getKwotaautoryzujaca() {
            return kwotaautoryzujaca;
    }
    
    public void setKwotaautoryzujaca(List<Parametr> kwotaautoryzujaca) {
        this.kwotaautoryzujaca = kwotaautoryzujaca;
    }
    
    public String getPole47() {
        return pole47;
    }
    
        
    public void setPole47(String pole47) {
        if (pole47!=null) {
            pole47 = pole47.replace(",", ".");
        }
        this.pole47 = pole47;
    }
    
    public List getOpisypkpir() {
        return opisypkpir;
    }
    
    public void setOpisypkpir(List opisypkpir) {
        this.opisypkpir = opisypkpir;
    }

    public List<Udzialy> getUdzialy() {
        return udzialy;
    }
    
    
    public void setUdzialy(List<Udzialy> udzialy) {
        this.udzialy = udzialy;
    }
    
   
    public List<Parametr> getNumerpkpir() {
        return numerpkpir;
    }
    
    public void setNumerpkpir(List<Parametr> numerpkpir) {
        this.numerpkpir = numerpkpir;
    }
    
    public String getNrkontabankowego() {
        return nrkontabankowego;
    }
    
    public void setNrkontabankowego(String nrkontabankowego) {
        this.nrkontabankowego = nrkontabankowego;
    }

    public String getPlatnoscwdni() {
        return platnoscwdni;
    }

    public void setPlatnoscwdni(String platnoscwdni) {
        this.platnoscwdni = platnoscwdni;
    }

    public String getNazwadlafaktury() {
        return nazwadlafaktury;
    }

    public void setNazwadlafaktury(String nazwadlafaktury) {
        this.nazwadlafaktury = nazwadlafaktury;
    }

    public String getAdresdlafaktury() {
        return adresdlafaktury;
    }

    public void setAdresdlafaktury(String adresdlafaktury) {
        this.adresdlafaktury = adresdlafaktury;
    }
    
    

    public Pozycjenafakturzebazadanych getWierszwzorcowy() {
        return wierszwzorcowy;
    }

    public void setWierszwzorcowy(Pozycjenafakturzebazadanych wierszwzorcowy) {
        this.wierszwzorcowy = wierszwzorcowy;
    }

    public String getMiejscewystawienia() {
        return miejscewystawienia;
    }

    public void setMiejscewystawienia(String miejscewystawienia) {
        this.miejscewystawienia = miejscewystawienia;
    }

    public String getNipdlafaktury() {
        return nipdlafaktury;
    }

    public void setNipdlafaktury(String nipdlafaktury) {
        this.nipdlafaktury = nipdlafaktury;
    }

    public List<Straty1> getStratyzlatub1() {
        return stratyzlatub1;
    }

    public void setStratyzlatub1(List<Straty1> stratyzlatub1) {
        this.stratyzlatub1 = stratyzlatub1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodubezpieczeniaZUS() {
        return kodubezpieczeniaZUS;
    }

    public void setKodubezpieczeniaZUS(String kodubezpieczeniaZUS) {
        this.kodubezpieczeniaZUS = kodubezpieczeniaZUS;
    }

    public boolean isTylkodlaZUS() {
        return tylkodlaZUS;
    }

    public void setTylkodlaZUS(boolean tylkodlaZUS) {
        this.tylkodlaZUS = tylkodlaZUS;
    }

    public boolean isZatrudniapracownikow() {
        return zatrudniapracownikow;
    }

    public void setZatrudniapracownikow(boolean zatrudniapracownikow) {
        this.zatrudniapracownikow = zatrudniapracownikow;
    }

    public double getLiczbadok() {
        return liczbadok;
    }

    public void setLiczbadok(double liczbadok) {
        this.liczbadok = liczbadok;
    }

    public double getLiczbawierszy() {
        return liczbawierszy;
    }

    public void setLiczbawierszy(double liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }

    
    
    @XmlTransient
    public List<MiejsceKosztow> getMiejsceKosztowList() {
        return miejsceKosztowList;
    }

    public void setMiejsceKosztowList(List<MiejsceKosztow> miejsceKosztowList) {
        this.miejsceKosztowList = miejsceKosztowList;
    }

    public String getNazwadlafaktury2() {
        return nazwadlafaktury2;
    }

    public void setNazwadlafaktury2(String nazwadlafaktury2) {
        this.nazwadlafaktury2 = nazwadlafaktury2;
    }

    public boolean isMetodakasowapit() {
        return metodakasowapit;
    }

    public void setMetodakasowapit(boolean metodakasowapit) {
        this.metodakasowapit = metodakasowapit;
    }

    public String getGussymbol() {
        return gussymbol;
    }

    public void setGussymbol(String gussymbol) {
        this.gussymbol = gussymbol;
    }

    public String getPolecajacy() {
        return polecajacy;
    }

    public void setPolecajacy(String polecajacy) {
        this.polecajacy = polecajacy;
    }

    public Uz getKadrowa() {
        return kadrowa;
    }

    public void setKadrowa(Uz kadrowa) {
        this.kadrowa = kadrowa;
    }

    public boolean isNowypodmiot() {
        return nowypodmiot;
    }

    public void setNowypodmiot(boolean nowypodmiot) {
        this.nowypodmiot = nowypodmiot;
    }

    public boolean isUmowalokal() {
        return umowalokal;
    }

    public void setUmowalokal(boolean umowalokal) {
        this.umowalokal = umowalokal;
    }

    public boolean isVatr() {
        return vatr;
    }

    public void setVatr(boolean vatr) {
        this.vatr = vatr;
    }

    public boolean isVatue() {
        return vatue;
    }

    public void setVatue(boolean vatue) {
        this.vatue = vatue;
    }

    public boolean isOpisdzialalnosci() {
        return opisdzialalnosci;
    }

    public void setOpisdzialalnosci(boolean opisdzialalnosci) {
        this.opisdzialalnosci = opisdzialalnosci;
    }

    public boolean isKontobankowe() {
        return kontobankowe;
    }

    public void setKontobankowe(boolean kontobankowe) {
        this.kontobankowe = kontobankowe;
    }

    public boolean isPpo() {
        return ppo;
    }

    public void setPpo(boolean ppo) {
        this.ppo = ppo;
    }

    public boolean isPel() {
        return pel;
    }

    public void setPel(boolean pel) {
        this.pel = pel;
    }

    public boolean isUmowa() {
        return umowa;
    }

    public void setUmowa(boolean umowa) {
        this.umowa = umowa;
    }

    public boolean isFaktura() {
        return faktura;
    }

    public void setFaktura(boolean faktura) {
        this.faktura = faktura;
    }

    public boolean isZua() {
        return zua;
    }

    public void setZua(boolean zua) {
        this.zua = zua;
    }

    public boolean isModulfaktur() {
        return modulfaktur;
    }

    public void setModulfaktur(boolean modulfaktur) {
        this.modulfaktur = modulfaktur;
    }

    public boolean isUpl() {
        return upl;
    }

    public void setUpl(boolean upl) {
        this.upl = upl;
    }

    public String getDatawznowienia() {
        return datawznowienia;
    }

    public void setDatawznowienia(String datawznowienia) {
        this.datawznowienia = datawznowienia;
    }

    public String getDatalikwidacji() {
        return datalikwidacji;
    }

    public void setDatalikwidacji(String datalikwidacji) {
        this.datalikwidacji = datalikwidacji;
    }

    public String getDatazawieszenia() {
        return datazawieszenia;
    }

    public void setDatazawieszenia(String datazawieszenia) {
        this.datazawieszenia = datazawieszenia;
    }

    public String getKoniecumowy() {
        return koniecumowy;
    }

    public void setKoniecumowy(String koniecumowy) {
        this.koniecumowy = koniecumowy;
    }

    public String getWydaniedokumentow() {
        return wydaniedokumentow;
    }

    public void setWydaniedokumentow(String wydaniedokumentow) {
        this.wydaniedokumentow = wydaniedokumentow;
    }

    public boolean isWysylaczestdok() {
        return wysylaczestdok;
    }

    public void setWysylaczestdok(boolean wysylaczestdok) {
        this.wysylaczestdok = wysylaczestdok;
    }

    public boolean isNiesprawdzajfaktury() {
        return niesprawdzajfaktury;
    }
    
    public boolean isNiemcy() {
        boolean zwrot = false;
        if (this.getSteuernummer()!=null&&this.getSteuernummer().isBlank()==false) {
            zwrot = true;
        }
        return zwrot;
    }

    public void setNiesprawdzajfaktury(boolean niesprawdzajfaktury) {
        this.niesprawdzajfaktury = niesprawdzajfaktury;
    }

    public String getDomyslnametodaplatnosci() {
        return domyslnametodaplatnosci;
    }

    public void setDomyslnametodaplatnosci(String domyslnametodaplatnosci) {
        this.domyslnametodaplatnosci = domyslnametodaplatnosci;
    }

    public boolean isZwolnienievat43() {
        return zwolnienievat43;
    }

    public void setZwolnienievat43(boolean zwolnienievat43) {
        this.zwolnienievat43 = zwolnienievat43;
    }

    public boolean isKasafiskalna() {
        return kasafiskalna;
    }

    public void setKasafiskalna(boolean kasafiskalna) {
        this.kasafiskalna = kasafiskalna;
    }

    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nip != null ? nip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Podatnik)) {
            return false;
        }
        Podatnik other = (Podatnik) object;
        if ((this.nip == null && other.nip != null) || (this.nip != null && !this.nip.equals(other.nip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Podatnik{" + "nip=" + nip + ", imie=" + imie + ", nazwapelna=" + nazwapelna + ", nazwisko=" + nazwisko + '}';
    }

   public String getFirmaForma() {
       String zwrot = "Fima";
       if (this.formaPrawna != null) {
           switch (this.formaPrawna.toString()) {
               case "STOWARZYSZENIE":
                   zwrot = "STOWARZYSZENIE";
                   break;
               case "FEDERACJA":
                   zwrot = "";
                   break;
               case "FUNDACJA":
                   zwrot = "";
                   break;
               default:
                   zwrot = "FIRMA";
           }
       }
       return zwrot;
   }


//   Szczególna forma prawna służy do określania, czy podmiot jest zaklasyfikowany do jednej z niżej wymienionych grup:
//
//spółki cywilne prowadzące działalność na podstawie umowy zawartej zgodnie z Kodeksem cywilnym (kod 019),
//spółki przewidziane w przepisach innych ustaw niż Kodeks spółek handlowych i Kodeks cywilny lub formy prawne, do których stosuje się przepisy o spółkach (kod 023),
//uczelnie (kod 044),
//fundusze (kod 049),
//Kościół Katolicki (kod 050),
//inne kościoły i związki wyznaniowe (kod 051),
//europejskie ugrupowanie współpracy terytorialnej (kod 053),
//stowarzyszenia niewpisane do KRS (kod 055),
//organizacje społeczne oddzielnie niewymienione niewpisane do KRS (kod 060),
//partie polityczne (kod 070),
//samorządy gospodarcze i zawodowe niewpisane do KRS (kod 076),
//przedstawicielstwa zagraniczne (kod 080),
//wspólnoty mieszkaniowe (kod 085),
//osoby fizyczne prowadzące działalność gospodarczą (kod 099),
//europejskie zgrupowania interesów gospodarczych (kod 114),
//spółki akcyjne (kod 116),
//spółki z ograniczoną odpowiedzialnością (kod 117),
//spółki jawne (kod 118),
//spółki partnerskie (kod 115),
//spółki komandytowe (kod 120),
//spółki komandytowo-akcyjne (kod 121),
//spółki europejskie (kod 122),
//przedsiębiorstwa państwowe (kod 124),
//towarzystwa ubezpieczeń wzajemnych (kod 126),
//instytucje gospodarki budżetowej (kod 132),
//związki zawodowe rolników indywidualnych (kod 133),
//towarzystwa reasekuracji wzajemnej (kod 134),
//główne oddziały zagranicznych zakładów reasekuracji (kod 135),
//główne oddziały zagranicznych zakładów ubezpieczeń (kod 136),
//ogólnokrajowe zrzeszenia międzybranżowe (kod 137),
//ogólnokrajowe związki międzybranżowe (kod 138),
//spółdzielnie (kod 140),
//spółdzielnie europejskie (kod 142),
//związki rolników, kółek i organizacji rolniczych (kod 143),
//związki rolniczych zrzeszeń branżowych (kod 145),
//samodzielne publiczne zakłady opieki zdrowotnej (kod 146),
//cechy rzemieślnicze (kod 147),
//fundacje (kod 148),
//izby rzemieślnicze (kod 152),
//Związek Rzemiosła Polskiego (kod 154),
//stowarzyszenia (kod 155),
//związki stowarzyszeń (kod 156),
//stowarzyszenia kultury fizycznej (kod 157),
//związki sportowe (kod 158),
//polskie związki sportowe (kod 159),
//inne organizacje społeczne lub zawodowe (kod 160),
//kolumny transportu sanitarnego (kod 161),
//stowarzyszenia kultury fizycznej o zasięgu ogólnokrajowym (kod 162),
//zrzeszenia handlu i usług (kod 163),
//zrzeszenia transportu (kod 164),
//instytuty badawcze (kod 165),
//jednostki badawczo-rozwojowe (kod 141),
//ogólnokrajowe reprezentacje zrzeszeń handlu i usług (kod 166),
//ogólnokrajowe reprezentacje zrzeszeń transportu (kod 167),
//inne organizacje podmiotów gospodarczych (kod 168),
//izby gospodarcze (kod 169),
//przedsiębiorstwa zagraniczne (kod 171),
//związki zawodowe (kod 172),
//związki pracodawców (kod 174),
//federacje/konfederacje związków pracodawców (kod 175),
//kółka rolnicze (kod 177),
//rolnicze zrzeszenia branżowe (kod 178),
//oddziały zagranicznych przedsiębiorców (kod 179),
//spółdzielcze kasy oszczędnościowo-kredytowe (kod 180),
//stowarzyszenia ogrodowe (kod 181),
//związki stowarzyszeń ogrodowych (kod 182),
//jednostki terenowe stowarzyszeń posiadające osobowość prawną (kod 183),
//jednostki organizacyjne związków zawodowych posiadające osobowość prawną (kod 184)
//przedszkola publiczne (kod 381),
//przedszkola niepubliczne (kod 382),
//publiczne szkoły podstawowe (kod 383),
//publiczne gimnazja (kod 384),
//publiczne szkoły ponadpodstawowe (kod 385),
//publiczne szkoły ponadgimnazjalne (kod 386),
//publiczne szkoły artystyczne (kod 387),
//niepubliczne szkoły podstawowe (kod 388),
//niepubliczne gimnazja (kod 389),
//niepubliczne szkoły ponadpodstawowe (kod 390),
//niepubliczne szkoły ponadgimnazjalne (kod 391),
//niepubliczne szkoły artystyczne (kod 392),
//publiczne placówki systemu oświaty (kod 393),
//niepubliczne placówki systemu oświaty (kod 394),
//inne publiczne jednostki organizacyjne systemu oświaty (kod 395),
//inne niepubliczne jednostki organizacyjne systemu oświaty (kod 396),
//publiczne zespoły szkół i placówek systemu oświaty (kod 397),
//niepubliczne zespoły szkół i placówek systemu oświaty (kod 398),
//organy władzy, administracji rządowej (kod 401),
//organy kontroli państwowej i ochrony prawa (kod 402),
//wspólnoty samorządowe (kod 403),
//sądy i trybunały (kod 406),
//Skarb Państwa (kod 409),
//państwowe jednostki organizacyjne (kod 428),
//gminne samorządowe jednostki organizacyjne (kod 429),
//powiatowe samorządowe jednostki organizacyjne (kod 430),
//wojewódzkie samorządowe jednostki organizacyjne (kod 431),
//inne państwowe lub samorządowe osoby prawne w rozumieniu art. 9 pkt 14 ustawy z dnia 27 sierpnia 2009 r. o finansach publicznych (Dz. U. z 2013 r. poz. 885, z późn. zm.3)) (kod 439),
//bez szczególnej formy prawnej (kod 999).
   
}
