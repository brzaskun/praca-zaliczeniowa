/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import beanstesty.DataBean;
import comparator.KalendarzmiesiacLastcomparator;
import data.Data;
import embeddable.CzasTrwania;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "umowa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Umowa.findAll", query = "SELECT u FROM Umowa u"),
    @NamedQuery(name = "Umowa.findById", query = "SELECT u FROM Umowa u WHERE u.id = :id"),
    @NamedQuery(name = "Umowa.findByDatado", query = "SELECT u FROM Umowa u WHERE u.datado = :datado"),
    @NamedQuery(name = "Umowa.findByDataod", query = "SELECT u FROM Umowa u WHERE u.dataod = :dataod"),
    @NamedQuery(name = "Umowa.findByDatazawarcia", query = "SELECT u FROM Umowa u WHERE u.datazawarcia = :datazawarcia"),
    @NamedQuery(name = "Umowa.findByKosztyuzyskania", query = "SELECT u FROM Umowa u WHERE u.kosztyuzyskaniaprocent = :kosztyuzyskaniaprocent"),
    @NamedQuery(name = "Umowa.findByOdliczaculgepodatkowa", query = "SELECT u FROM Umowa u WHERE u.odliczaculgepodatkowa = :odliczaculgepodatkowa"),
    @NamedQuery(name = "Umowa.findByChorobowe", query = "SELECT u FROM Umowa u WHERE u.chorobowe = :chorobowe"),
    @NamedQuery(name = "Umowa.findByChorobowedobrowolne", query = "SELECT u FROM Umowa u WHERE u.chorobowedobrowolne = :chorobowedobrowolne"),
    @NamedQuery(name = "Umowa.findByDataspoleczne", query = "SELECT u FROM Umowa u WHERE u.dataspoleczne = :dataspoleczne"),
    @NamedQuery(name = "Umowa.findByDatazdrowotne", query = "SELECT u FROM Umowa u WHERE u.datazdrowotne = :datazdrowotne"),
    @NamedQuery(name = "Umowa.findByEmerytalne", query = "SELECT u FROM Umowa u WHERE u.emerytalne = :emerytalne"),
    @NamedQuery(name = "Umowa.findByKodzawodu", query = "SELECT u FROM Umowa u WHERE u.kodzawodu = :kodzawodu"),
    @NamedQuery(name = "Umowa.findByNfz", query = "SELECT u FROM Umowa u WHERE u.nfz = :nfz"),
    @NamedQuery(name = "Umowa.findByNieliczFGSP", query = "SELECT u FROM Umowa u WHERE u.nieliczFGSP = :nieliczFGSP"),
    @NamedQuery(name = "Umowa.findByNieliczFP", query = "SELECT u FROM Umowa u WHERE u.nieliczFP = :nieliczFP"),
    @NamedQuery(name = "Umowa.findByRentowe", query = "SELECT u FROM Umowa u WHERE u.rentowe = :rentowe"),
    @NamedQuery(name = "Umowa.findByWypadkowe", query = "SELECT u FROM Umowa u WHERE u.wypadkowe = :wypadkowe"),
    @NamedQuery(name = "Umowa.findByZdrowotne", query = "SELECT u FROM Umowa u WHERE u.zdrowotne = :zdrowotne"),
    @NamedQuery(name = "Umowa.findByPracownik", query = "SELECT u FROM Umowa u WHERE u.angaz.pracownik = :pracownik"),
    @NamedQuery(name = "Umowa.findByPracownikFirma", query = "SELECT u FROM Umowa u WHERE u.angaz.pracownik = :pracownik AND u.angaz.firma = :firma"),
    @NamedQuery(name = "Umowa.findByAngaz", query = "SELECT u FROM Umowa u WHERE u.angaz = :angaz ORDER BY u.dataod ASC"),
    @NamedQuery(name = "Umowa.findByAngazPraca", query = "SELECT u FROM Umowa u WHERE u.angaz = :angaz AND u.umowakodzus.praca = TRUE ORDER BY u.dataod ASC"),
    @NamedQuery(name = "Umowa.findByAngazZlecenie", query = "SELECT u FROM Umowa u WHERE u.angaz = :angaz AND u.umowakodzus.zlecenie = TRUE ORDER BY u.dataod ASC"),
    @NamedQuery(name = "Umowa.findByAngazFunkcja", query = "SELECT u FROM Umowa u WHERE u.angaz = :angaz AND u.umowakodzus.funkcja = TRUE ORDER BY u.dataod ASC")
        
})
public class Umowa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "datado")
    private String datado;
    @Size(max = 10)
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 10)
    @Column(name = "datazawarcia")
    private String datazawarcia;
    @Size(max = 10)
    @Column(name = "dataspoleczne")
    private String dataspoleczne;
    @Size(max = 10)
    @Column(name = "datazdrowotne")
    private String datazdrowotne;
    @Size(max = 3)
    @Column(name = "nfz")
    private String nfz;
    @Size(max = 45)
    @Column(name = "nrkolejny")
    private String nrkolejny;
    @Size(max = 128)
    @Column(name = "stanowisko")
    private String stanowisko;
    @Size(max = 128)
    @Column(name = "miejscepracy")
    private String miejscepracy;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kosztyuzyskaniaprocent")
    private double kosztyuzyskaniaprocent;
    @Column(name = "kosztyuzyskania0podwyzszone1")
    private boolean kosztyuzyskania0podwyzszone1;
    @Column(name = "nierezydent")
    private boolean nierezydent;
    @Size(max = 10)
    @Column(name = "dataprzyjazdudopolski")
    private String dataprzyjazdudopolski;
    @Column(name = "kwotawolnaprocent")
    private double kwotawolnaprocent;
    @Column(name = "odliczaculgepodatkowa")
    private boolean odliczaculgepodatkowa;
    @Column(name = "chorobowe")
    private boolean chorobowe;
    @Column(name = "chorobowedobrowolne")
    private boolean chorobowedobrowolne;
    @Column(name = "emerytalne")
    private boolean emerytalne;
    @Column(name = "nieliczFGSP")
    private  boolean nieliczFGSP;
    @Column(name = "nieliczFP")
    private  boolean nieliczFP;
    @Column(name = "rentowe")
    private  boolean rentowe;
    @Column(name = "wypadkowe")
    private  boolean wypadkowe;
    @Column(name = "zdrowotne")
    private  boolean zdrowotne;
    @JoinColumn(name = "kodzawodu", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Kodyzawodow kodzawodu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "umowa", orphanRemoval = true)
    private List<EtatPrac> etatList;
    @Column(name = "czastrwania")
    private Integer czastrwania;
    @JoinColumn(name = "umowakodzus", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Umowakodzus umowakodzus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "umowa")
    private List<Nieobecnosc> nieobecnoscList;
    @NotNull
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne()
    private Angaz angaz;
    @OneToMany(mappedBy = "umowa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kalendarzmiesiac> kalendarzmiesiacList;
    @OneToMany(mappedBy = "umowa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skladnikpotracenia> skladnikpotraceniaList;
    @OneToMany(mappedBy = "umowa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "umowa")
    private List<Stanowiskoprac> stanowiskopracList;
    @Column(name = "aktywna")
    private  boolean aktywna;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datasystem")
    private Date datasystem;
    @JoinColumn(name = "slownikszkolazatrhistoria", referencedColumnName = "id")
    @ManyToOne
    private  Slownikszkolazatrhistoria slownikszkolazatrhistoria;
    @Column(name = "liczdourlopu")
    private  boolean liczdourlopu;
    @Column(name = "liczdostazowego")
    private  boolean liczdostazowego;
    @Column(name = "liczdonagrody")
    private  boolean liczdonagrody;
    @Column(name = "liczdoemerytury")
    private  boolean liczdoemerytury;
    @JoinColumn(name = "slownikwypowiedzenieumowy", referencedColumnName = "id")
    @ManyToOne
    private  Slownikwypowiedzenieumowy slownikwypowiedzenieumowy;
    @Column(name = "przyczynawypowiedzenia")
    private String przyczynawypowiedzenia;
    @Column(name = "opiszawodu")
    private String opiszawodu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "umowa")
    private List<Nieobecnoscprezentacja> urlopprezentacjaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "umowa")
    private List<Rachunekdoumowyzlecenia> rachunekdoumowyzleceniaList;
    @Size(max = 256)
    @Column(name = "innewarunkizatrudnienia")
    private String innewarunkizatrudnienia;
    @Column(name = "terminrozpoczeciapracy")
    private String terminrozpoczeciapracy;
    @Column(name = "dopuszczalnailoscgodzin")
    private String dopuszczalnailoscgodzin;
    @Size(max = 256)
    @Column(name = "przyczynaumowaokreslony")
    private String przyczynaumowaokreslony;
    @Column(name = "importowana")
    private boolean importowana;
    @Column(name = "pierwszydzienzasilku")
    private String pierwszydzienzasilku;
    @Column(name = "iloscdnitrwaniaumowy")
    private int iloscdnitrwaniaumowy;
    @Column(name = "dataprzypomnienia")
    private String  dataprzypomnienia;
    @Column(name = "dataprzypomnieniamail")
    private String  dataprzypomnieniamail;
    @Column(name = "dataszkolenierok")
    private String  dataszkolenierok;
    @Column(name = "dataszkolenie3lata")
    private String  dataszkolenie3lata;
    @Column(name = "dataszkolenie5lat")
    private String  dataszkolenie5lat;
    @Column(name = "dataprzypomnieniaszkolenie")
    private String  dataprzypomnieniaszkolenie;
    @Column(name = "dataprzypomnieniamailszkolenie")
    private String  dataprzypomnieniamailszkolenie;
    @Column(name = "lata")
    private int lata;
    @Column(name = "dni")
    private int dni;
    

    public Umowa() {
        this.etatList = new ArrayList<>();
        this.stanowiskopracList = new ArrayList<>();
    }

    public Umowa(int id) {
        this.id = id;
        this.etatList = new ArrayList<>();
        this.stanowiskopracList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    @XmlTransient
    public List<Kalendarzmiesiac> getKalendarzmiesiacList() {
        return kalendarzmiesiacList;
    }

    public void setKalendarzmiesiacList(List<Kalendarzmiesiac> kalendarzmiesiacList) {
        this.kalendarzmiesiacList = kalendarzmiesiacList;
    }

    @XmlTransient
    public List<Skladnikpotracenia> getSkladnikpotraceniaList() {
        return skladnikpotraceniaList;
    }

    public void setSkladnikpotraceniaList(List<Skladnikpotracenia> skladnikpotraceniaList) {
        this.skladnikpotraceniaList = skladnikpotraceniaList;
    }

    public String getDataprzypomnienia() {
        return dataprzypomnienia;
    }

    public void setDataprzypomnienia(String dataprzypomnienia) {
        this.dataprzypomnienia = dataprzypomnienia;
    }

    public String getMiejscepracy() {
        return miejscepracy;
    }

    public void setMiejscepracy(String miejscepracy) {
        this.miejscepracy = miejscepracy;
    }

    public boolean isKosztyuzyskania0podwyzszone1() {
        return kosztyuzyskania0podwyzszone1;
    }

    public void setKosztyuzyskania0podwyzszone1(boolean kosztyuzyskania0podwyzszone1) {
        this.kosztyuzyskania0podwyzszone1 = kosztyuzyskania0podwyzszone1;
    }

    public String getDataprzypomnieniamail() {
        return dataprzypomnieniamail;
    }

    public void setDataprzypomnieniamail(String dataprzypomnieniamail) {
        this.dataprzypomnieniamail = dataprzypomnieniamail;
    }

    public Date getDatasystem() {
        return datasystem;
    }

    public void setDatasystem(Date datasystem) {
        this.datasystem = datasystem;
    }

    public boolean isNierezydent() {
        return nierezydent;
    }

    public void setNierezydent(boolean nierezydent) {
        this.nierezydent = nierezydent;
    }

    public String getDataprzyjazdudopolski() {
        return dataprzyjazdudopolski;
    }

    public void setDataprzyjazdudopolski(String dataprzyjazdudopolski) {
        this.dataprzyjazdudopolski = dataprzyjazdudopolski;
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
    

    @XmlTransient
    public List<Skladnikwynagrodzenia> getSkladnikwynagrodzeniaList() {
        return skladnikwynagrodzeniaList;
    }

    public void setSkladnikwynagrodzeniaList(List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList) {
        this.skladnikwynagrodzeniaList = skladnikwynagrodzeniaList;
    }

    @XmlTransient
    public List<Rachunekdoumowyzlecenia> getRachunekdoumowyzleceniaList() {
        return rachunekdoumowyzleceniaList;
    }

    public void setRachunekdoumowyzleceniaList(List<Rachunekdoumowyzlecenia> rachunekdoumowyzleceniaList) {
        this.rachunekdoumowyzleceniaList = rachunekdoumowyzleceniaList;
    }

    public String getDataszkolenierok() {
        return dataszkolenierok;
    }

    public void setDataszkolenierok(String dataszkolenierok) {
        this.dataszkolenierok = dataszkolenierok;
    }

    public String getDataszkolenie3lata() {
        return dataszkolenie3lata;
    }

    public void setDataszkolenie3lata(String dataszkolenie3lata) {
        this.dataszkolenie3lata = dataszkolenie3lata;
    }

    public String getDataszkolenie5lat() {
        return dataszkolenie5lat;
    }

    public void setDataszkolenie5lat(String dataszkolenie5lat) {
        this.dataszkolenie5lat = dataszkolenie5lat;
    }

    public String getDataprzypomnieniaszkolenie() {
        return dataprzypomnieniaszkolenie;
    }

    public void setDataprzypomnieniaszkolenie(String dataprzypomnieniaszkolenie) {
        this.dataprzypomnieniaszkolenie = dataprzypomnieniaszkolenie;
    }

    public String getDataprzypomnieniamailszkolenie() {
        return dataprzypomnieniamailszkolenie;
    }

    public void setDataprzypomnieniamailszkolenie(String dataprzypomnieniamailszkolenie) {
        this.dataprzypomnieniamailszkolenie = dataprzypomnieniamailszkolenie;
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
        if (!(object instanceof Umowa)) {
            return false;
        }
        Umowa other = (Umowa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Umowa{" + "datado=" + datado + ", dataod=" + dataod + ", angaz=" + angaz.getPracownik().getNazwiskoImie() + ", rodzajumowy="+ "}";
    }

   public String umowanumernazwa() {
       return this.nrkolejny;
   }
   @XmlTransient   
   public List<Nieobecnosc> getNieobecnoscList() {
       return nieobecnoscList;
   }
   public void setNieobecnoscList(List<Nieobecnosc> nieobecnoscList) {
       this.nieobecnoscList = nieobecnoscList;
   }



    public Umowakodzus getUmowakodzus() {
        return umowakodzus;
    }
    public void setUmowakodzus(Umowakodzus umowakodzus) {
        this.umowakodzus = umowakodzus;
    }
    public String getCzastrwania() {
        String zwrot = CzasTrwania.getListaczastrwania().get(this.czastrwania);
        return zwrot;
    }
    public void setCzastrwania(String czastrwania) {
        int zwrot = CzasTrwania.find(czastrwania);;
        this.czastrwania = zwrot;
    }
    @XmlTransient
    public List<EtatPrac> getEtatList() {
        return etatList;
    }
    public void setEtatList(List<EtatPrac> etatList) {
        this.etatList = etatList;
    }
    public Kodyzawodow getKodzawodu() {
        return kodzawodu;
    }
    public void setKodzawodu(Kodyzawodow kodzawodu) {
        this.kodzawodu = kodzawodu;
    }



    public  double getKosztyuzyskaniaprocent() {
        return kosztyuzyskaniaprocent;
    }

    public void setKosztyuzyskaniaprocent( double kosztyuzyskaniaprocent) {
        this.kosztyuzyskaniaprocent = kosztyuzyskaniaprocent;
    }

    public boolean isOdliczaculgepodatkowa() {
        return odliczaculgepodatkowa;
    }

    public void setOdliczaculgepodatkowa(boolean odliczaculgepodatkowa) {
        this.odliczaculgepodatkowa = odliczaculgepodatkowa;
    }

    public boolean isChorobowe() {
        return chorobowe;
    }

    public void setChorobowe(boolean chorobowe) {
        this.chorobowe = chorobowe;
    }

    public boolean isChorobowedobrowolne() {
        return chorobowedobrowolne;
    }

    public void setChorobowedobrowolne(boolean chorobowedobrowolne) {
        this.chorobowedobrowolne = chorobowedobrowolne;
    }

    public boolean isEmerytalne() {
        return emerytalne;
    }

    public void setEmerytalne(boolean emerytalne) {
        this.emerytalne = emerytalne;
    }

    public boolean isNieliczFGSP() {
        return nieliczFGSP;
    }

    public void setNieliczFGSP(boolean nieliczFGSP) {
        this.nieliczFGSP = nieliczFGSP;
    }

    public boolean isNieliczFP() {
        return nieliczFP;
    }

    public void setNieliczFP(boolean nieliczFP) {
        this.nieliczFP = nieliczFP;
    }

    public boolean isRentowe() {
        return rentowe;
    }

    public void setRentowe(boolean rentowe) {
        this.rentowe = rentowe;
    }

    public boolean isWypadkowe() {
        return wypadkowe;
    }

    public void setWypadkowe(boolean wypadkowe) {
        this.wypadkowe = wypadkowe;
    }

    public boolean isZdrowotne() {
        return zdrowotne;
    }

    public void setZdrowotne(boolean zdrowotne) {
        this.zdrowotne = zdrowotne;
    }


    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatazawarcia() {
        return datazawarcia;
    }

    public void setDatazawarcia(String datazawarcia) {
        this.datazawarcia = datazawarcia;
    }

    public String getDataspoleczne() {
        return dataspoleczne;
    }

    public void setDataspoleczne(String dataspoleczne) {
        this.dataspoleczne = dataspoleczne;
    }

    public String getDatazdrowotne() {
        return datazdrowotne;
    }

    public void setDatazdrowotne(String datazdrowotne) {
        this.datazdrowotne = datazdrowotne;
    }

    public String getNfz() {
        return nfz;
    }

    public void setNfz(String nfz) {
        this.nfz = nfz;
    }

    public String getNrkolejny() {
        return nrkolejny;
    }

    public void setNrkolejny(String nrkolejny) {
        this.nrkolejny = nrkolejny;
    }

    public String getMc() {
        return Data.getMc(this.dataod);
    }
    public String getRok() {
        return Data.getRok(this.dataod);
    }

    public boolean isAktywna() {
        return aktywna;
    }

    public void setAktywna(boolean aktywna) {
        this.aktywna = aktywna;
    }

    public String getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(String stanowisko) {
        this.stanowisko = stanowisko;
    }

    public boolean nalezydomiesiaca(String rok, String mc) {
        boolean zaczynasiew = Data.czydatajestwmcu(this.dataod, rok, mc);
        boolean konczysiew = Data.czydatajestwmcu(this.dataod, rok, mc);
        boolean zwrot = zaczynasiew || konczysiew;
        return zwrot;
    }



    public Slownikszkolazatrhistoria getSlownikszkolazatrhistoria() {
        return slownikszkolazatrhistoria;
    }

    public void setSlownikszkolazatrhistoria(Slownikszkolazatrhistoria slownikszkolazatrhistoria) {
        this.slownikszkolazatrhistoria = slownikszkolazatrhistoria;
    }

    public boolean isLiczdourlopu() {
        return liczdourlopu;
    }

    public void setLiczdourlopu(boolean liczdourlopu) {
        this.liczdourlopu = liczdourlopu;
    }

    public boolean isLiczdostazowego() {
        return liczdostazowego;
    }

    public void setLiczdostazowego(boolean liczdostazowego) {
        this.liczdostazowego = liczdostazowego;
    }

    public boolean isLiczdonagrody() {
        return liczdonagrody;
    }

    public void setLiczdonagrody(boolean liczdonagrody) {
        this.liczdonagrody = liczdonagrody;
    }

    public boolean isLiczdoemerytury() {
        return liczdoemerytury;
    }

    public void setLiczdoemerytury(boolean liczdoemerytury) {
        this.liczdoemerytury = liczdoemerytury;
    }

    public Slownikwypowiedzenieumowy getSlownikwypowiedzenieumowy() {
        return slownikwypowiedzenieumowy;
    }

    public void setSlownikwypowiedzenieumowy(Slownikwypowiedzenieumowy slownikwypowiedzenieumowy) {
        this.slownikwypowiedzenieumowy = slownikwypowiedzenieumowy;
    }

    public String getPrzyczynawypowiedzenia() {
        return przyczynawypowiedzenia;
    }

    public void setPrzyczynawypowiedzenia(String przyczynawypowiedzenia) {
        this.przyczynawypowiedzenia = przyczynawypowiedzenia;
    }

    public String getOpiszawodu() {
        return opiszawodu;
    }

    public void setOpiszawodu(String opiszawodu) {
        this.opiszawodu = opiszawodu;
    }

    public double getKwotawolnaprocent() {
        return kwotawolnaprocent;
    }

    public void setKwotawolnaprocent(double kwotawolnaprocent) {
        this.kwotawolnaprocent = kwotawolnaprocent;
    }

    public List<Stanowiskoprac> getStanowiskopracList() {
        return stanowiskopracList;
    }

    public void setStanowiskopracList(List<Stanowiskoprac> stanowiskopracList) {
        this.stanowiskopracList = stanowiskopracList;
    }

    public String getInnewarunkizatrudnienia() {
        return innewarunkizatrudnienia;
    }

    public void setInnewarunkizatrudnienia(String innewarunkizatrudnienia) {
        this.innewarunkizatrudnienia = innewarunkizatrudnienia;
    }

    public String getTerminrozpoczeciapracy() {
        return terminrozpoczeciapracy;
    }

    public void setTerminrozpoczeciapracy(String terminrozpoczeciapracy) {
        this.terminrozpoczeciapracy = terminrozpoczeciapracy;
    }

    public String getDopuszczalnailoscgodzin() {
        return dopuszczalnailoscgodzin;
    }

    public void setDopuszczalnailoscgodzin(String dopuszczalnailoscgodzin) {
        this.dopuszczalnailoscgodzin = dopuszczalnailoscgodzin;
    }

    public String getPrzyczynaumowaokreslony() {
        return przyczynaumowaokreslony;
    }

    public void setPrzyczynaumowaokreslony(String przyczynaumowaokreslony) {
        this.przyczynaumowaokreslony = przyczynaumowaokreslony;
    }

    public boolean isImportowana() {
        return importowana;
    }

    public void setImportowana(boolean importowana) {
        this.importowana = importowana;
    }
    
    public String getImieNazwisko() {
        return this.angaz.getPracownik().getImie()+" "+this.angaz.getPracownik().getNazwisko();
    }
    
    public String getNazwiskoImie() {
        return this.angaz.getPracownik().getNazwisko()+" "+this.angaz.getPracownik().getImie();
    }
    
    public String getRodzajumowy() {
        String zwrot = "umowa o pracę";
        if (this.umowakodzus!=null && this.umowakodzus.isZlecenie()) {
            zwrot = "umowa zlecenia";
        } else if (this.umowakodzus!=null && this.umowakodzus.isFunkcja()) {
            zwrot = "pełnienie funkcji";
        }
        return zwrot;
    }

    public String getPierwszydzienzasilku() {
        return pierwszydzienzasilku;
    }

    public void setPierwszydzienzasilku(String pierwszydzienzasilku) {
        this.pierwszydzienzasilku = pierwszydzienzasilku;
    }

    public int getIloscdnitrwaniaumowy() {
        return iloscdnitrwaniaumowy;
    }

    public void setIloscdnitrwaniaumowy(int iloscdnitrwaniaumowy) {
        this.iloscdnitrwaniaumowy = iloscdnitrwaniaumowy;
    }

    
    
    
    @XmlTransient
    public List<Nieobecnoscprezentacja> getUrlopprezentacjaList() {
        return urlopprezentacjaList;
    }

    public void setUrlopprezentacjaList(List<Nieobecnoscprezentacja> urlopprezentacjaList) {
        this.urlopprezentacjaList = urlopprezentacjaList;
    }

    public String getWiekumowa() {
        String zwrot = "";
        if (this.dataod!=null) {
            zwrot = "lat: "+this.lata+" dni: "+this.dni;
        }
        return zwrot;
    }
    
    public EtatPrac pobierzetat(String data) {
       EtatPrac zwrot = null;
        List<EtatPrac> etatList1 = this.etatList;
        if (etatList1!=null) {
            for (EtatPrac p : etatList1) {
                String datagraniczna = p.getDataod();
                if (Data.czyjestpo(datagraniczna, data)) {
                    zwrot = p;
                }
            }
        }
        return zwrot;
    }
    
    public String pobierzwynagrodzenieString(WpisView wpisView) {
        String zwrot = "";
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                zwrot = zwrot+p.getRodzajwynagrodzenia().getOpispelny()+" ";
                zwrot = zwrot+pobierzkwoteString(p.getZmiennawynagrodzeniaList(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
            }
        }
        return zwrot;
    }

    private String pobierzkwoteString(List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList, String rok, String mc) {
        String zwrot = "";
        if (zmiennawynagrodzeniaList!=null) {
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                String dataod1 = p.getDataod();
                String datado1 = p.getDatado();
                boolean czydataodjestwmcu = Data.czydatajestwmcu(dataod1, rok, mc);
                boolean czydatadojestwmcu = Data.czydatajestwmcu(datado1, rok, mc);
                if (czydatadojestwmcu&&czydataodjestwmcu || datado1==null) {
                    zwrot = zwrot+p.getNazwa()+" ";
                    zwrot = zwrot+f.F.curr(p.getKwota());
                    break;
                }
            }
        }
        return zwrot;
    }
    
     public double pobierzwynagrodzenieKwota(String rok, String mc, Kalendarzmiesiac kalendarz) {
        double zwrot = 0.0;
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                zwrot = pobierzkwoteKwota(p.getZmiennawynagrodzeniaList(), rok, mc, kalendarz);
            }
        }
        return zwrot;
    }   
     
    

    private double pobierzkwoteKwota(List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList, String rok, String mc, Kalendarzmiesiac kalendarz) {
        double zwrot = 0.0;
        if (zmiennawynagrodzeniaList!=null) {
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                String dataod1 = p.getDataod();
                String datado1 = p.getDatado();
                if (DataBean.czysiemiesci(kalendarz, dataod1, datado1)) {
                    zwrot = p.getKwota();
                    break;
                }
            }
        }
        return zwrot;
    }
    
    public boolean czywynagrodzeniegodzinowe() {
        boolean zwrot = false;
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                if (p.getRodzajwynagrodzenia().getKod().equals("11")||p.getRodzajwynagrodzenia().getKod().equals("50")) {
                    zwrot = !p.getRodzajwynagrodzenia().getGodzinowe0miesieczne1();
                }
            }
        }
        return zwrot;
    }
    
    public Rachunekdoumowyzlecenia pobierzRachunekzlecenie(String rok, String mc) {
        Rachunekdoumowyzlecenia zwrot = null;
        try {
            List<Rachunekdoumowyzlecenia> rachunekdoumowyzleceniaList = this.getRachunekdoumowyzleceniaList();
            if (rachunekdoumowyzleceniaList!=null) {
                zwrot = rachunekdoumowyzleceniaList.stream().filter(pa->pa.getMc().equals(mc)&&pa.getRok().equals(rok)).findAny().get();
            }
        } catch (Exception e){}
        return zwrot;
    }

    public List<Naliczenieskladnikawynagrodzenia> pobierzpaski(String rok, String mc, Skladnikwynagrodzenia s) {
        List<Naliczenieskladnikawynagrodzenia> zwrot = new ArrayList<>();
        List<Kalendarzmiesiac> kalendarzList = this.getKalendarzmiesiacList();
        Collections.sort(kalendarzList, new KalendarzmiesiacLastcomparator());
        int ilemamy = 0;
        for (Kalendarzmiesiac  r : kalendarzList) {
            if (r.getRokI()<=Integer.parseInt(rok)) {
                if (Data.czyjestpomcnaprawdepo(r.getMc(), r.getRok(), mc, rok)) {
                    Naliczenieskladnikawynagrodzenia naliczonewynagrodzenie = r.getNaliczonewynagrodzenie(s);
                    if (naliczonewynagrodzenie!=null) {
                        zwrot.add(naliczonewynagrodzenie);
                    }
                    ilemamy++;
                }
                if (ilemamy==3) {
                    break;
                }
            }
        }
        
        return zwrot;
    }
}
