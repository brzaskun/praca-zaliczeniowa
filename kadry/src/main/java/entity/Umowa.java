/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
import embeddable.CzasTrwania;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Umowa.findByDatanfz", query = "SELECT u FROM Umowa u WHERE u.datanfz = :datanfz"),
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
    @NamedQuery(name = "Umowa.findByAngazZlecenie", query = "SELECT u FROM Umowa u WHERE u.angaz = :angaz AND u.umowakodzus.zlecenie = TRUE ORDER BY u.dataod ASC")
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
    @Column(name = "datanfz")
    private String datanfz;
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kosztyuzyskaniaprocent")
    private double kosztyuzyskaniaprocent;
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
    @Size(max = 10)
    @Column(name = "datasystem")
    private String datasystem;
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
    private List<Urlopprezentacja> urlopprezentacjaList;
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "umowa")
    private List<Rachunekdoumowyzlecenia> rachunekdoumowyzleceniaList;

    public Umowa() {
        this.etatList = new ArrayList<>();
    }

    public Umowa(int id) {
        this.id = id;
        this.etatList = new ArrayList<>();
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

    @XmlTransient
    public List<Skladnikwynagrodzenia> getSkladnikwynagrodzeniaList() {
        return skladnikwynagrodzeniaList;
    }

    public void setSkladnikwynagrodzeniaList(List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList) {
        this.skladnikwynagrodzeniaList = skladnikwynagrodzeniaList;
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

    public  boolean getOdliczaculgepodatkowa() {
        return odliczaculgepodatkowa;
    }

    public void setOdliczaculgepodatkowa( boolean odliczaculgepodatkowa) {
        this.odliczaculgepodatkowa = odliczaculgepodatkowa;
    }

    public boolean getChorobowe() {
        return chorobowe;
    }

    public void setChorobowe(boolean chorobowe) {
        this.chorobowe = chorobowe;
    }
    public boolean getChorobowedobrowolne() {
        return chorobowedobrowolne;
    }
    public void setChorobowedobrowolne(boolean chorobowedobrowolne) {
        this.chorobowedobrowolne = chorobowedobrowolne;
    }
   

    public boolean getEmerytalne() {
        return emerytalne;
    }

    public void setEmerytalne(boolean emerytalne) {
        this.emerytalne = emerytalne;
    }


    public boolean getNieliczFGSP() {
        return nieliczFGSP;
    }

    public void setNieliczFGSP(boolean nieliczFGSP) {
        this.nieliczFGSP = nieliczFGSP;
    }

    public boolean getNieliczFP() {
        return nieliczFP;
    }

    public void setNieliczFP(boolean nieliczFP) {
        this.nieliczFP = nieliczFP;
    }

      public boolean getRentowe() {
        return rentowe;
    }

    public void setRentowe(boolean rentowe) {
        this.rentowe = rentowe;
    }

    public boolean getWypadkowe() {
        return wypadkowe;
    }

    public void setWypadkowe(boolean wypadkowe) {
        this.wypadkowe = wypadkowe;
    }

    public boolean getZdrowotne() {
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

    public String getDatanfz() {
        return datanfz;
    }

    public void setDatanfz(String datanfz) {
        this.datanfz = datanfz;
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

    public String getDatasystem() {
        return datasystem;
    }

    public void setDatasystem(String datasystem) {
        this.datasystem = datasystem;
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
    
    @XmlTransient
    public List<Urlopprezentacja> getUrlopprezentacjaList() {
        return urlopprezentacjaList;
    }

    public void setUrlopprezentacjaList(List<Urlopprezentacja> urlopprezentacjaList) {
        this.urlopprezentacjaList = urlopprezentacjaList;
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
    
}
