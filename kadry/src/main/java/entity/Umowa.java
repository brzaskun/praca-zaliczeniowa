/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
import embeddable.CzasTrwania;
import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "umowa", uniqueConstraints = {
    @UniqueConstraint(columnNames={"angaz","nrkolejny"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Umowa.findAll", query = "SELECT u FROM Umowa u"),
    @NamedQuery(name = "Umowa.findById", query = "SELECT u FROM Umowa u WHERE u.id = :id"),
    @NamedQuery(name = "Umowa.findByDatado", query = "SELECT u FROM Umowa u WHERE u.datado = :datado"),
    @NamedQuery(name = "Umowa.findByDataod", query = "SELECT u FROM Umowa u WHERE u.dataod = :dataod"),
    @NamedQuery(name = "Umowa.findByDatazawarcia", query = "SELECT u FROM Umowa u WHERE u.datazawarcia = :datazawarcia"),
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
    @NamedQuery(name = "Umowa.findByPracownikAktywna", query = "SELECT u FROM Umowa u WHERE u.angaz.pracownik = :pracownik AND u.aktywna=TRUE"),
    @NamedQuery(name = "Umowa.findByPracownikFirma", query = "SELECT u FROM Umowa u WHERE u.angaz.pracownik = :pracownik AND u.angaz.firma = :firma"),
    @NamedQuery(name = "Umowa.findByAngaz", query = "SELECT u FROM Umowa u WHERE u.angaz = :angaz ORDER BY u.dataod ASC"),
    @NamedQuery(name = "Umowa.findByFirma", query = "SELECT u FROM Umowa u WHERE u.angaz.firma = :firma"),
    @NamedQuery(name = "Umowa.findByAngazPraca", query = "SELECT u FROM Umowa u WHERE u.angaz = :angaz AND u.umowakodzus.praca = TRUE ORDER BY u.dataod ASC"),
    @NamedQuery(name = "Umowa.findByAngazZlecenie", query = "SELECT u FROM Umowa u WHERE u.angaz = :angaz AND u.umowakodzus.zlecenie = TRUE ORDER BY u.dataod ASC"),
    @NamedQuery(name = "Umowa.findByFirmaZlecenie", query = "SELECT u FROM Umowa u WHERE u.angaz.firma = :firma AND u.umowakodzus.zlecenie = TRUE ORDER BY u.dataod ASC"),
    @NamedQuery(name = "Umowa.findByFirmaZlecenieAktywne", query = "SELECT u FROM Umowa u WHERE u.angaz.firma = :firma AND u.umowakodzus.zlecenie = TRUE AND u.aktywna = TRUE ORDER BY u.dataod ASC"),
    @NamedQuery(name = "Umowa.findByFirmaPraca", query = "SELECT u FROM Umowa u WHERE u.angaz.firma = :firma AND u.umowakodzus.praca = TRUE ORDER BY u.dataod ASC"),
    @NamedQuery(name = "Umowa.findByFirmaPracaAktywne", query = "SELECT u FROM Umowa u WHERE u.angaz.firma = :firma AND u.umowakodzus.praca = TRUE AND u.aktywna = TRUE ORDER BY u.dataod ASC"),
    @NamedQuery(name = "Umowa.findByAngazFunkcja", query = "SELECT u FROM Umowa u WHERE u.angaz = :angaz AND u.umowakodzus.funkcja = TRUE ORDER BY u.dataod ASC")
        
})
public class Umowa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne()
    private Angaz angaz;
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
    @Column(name = "czastrwania")
    private Integer czastrwania;
    @JoinColumn(name = "umowakodzus", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @NotNull
    private Umowakodzus umowakodzus;
    @Column(name = "etat1")
    private Integer etat1;
    @Column(name = "etat2")
    private Integer etat2;
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
    private List<Rachunekdoumowyzlecenia> rachunekdoumowyzleceniaList;
    @Size(max = 1256)
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
    @Column(name = "lata")
    private int lata;
    @Column(name = "dni")
    private int dni;
    @Column(name = "licznikumow")
    private int licznikumow;
    @Column(name = "wynagrodzeniemiesieczne")
    private double wynagrodzeniemiesieczne;
    @Column(name = "wynagrodzeniegodzinowe")
    private double wynagrodzeniegodzinowe;
    @Column(name = "wynagrodzenieoddelegowanie")
    private double wynagrodzenieoddelegowanie;
    @Column(name = "symbolwalutyoddelegowanie")
    private String symbolwalutyoddelegowanie;
    @JoinColumn(name = "kombinacjaubezpieczen", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Kombinacjaubezpieczen kombinacjaubezpieczen;
    @JoinColumn(name = "rozwiazanieumowy", referencedColumnName = "id")
    @OneToOne
    private Rozwiazanieumowy rozwiazanieumowy;
     @JoinColumn(name = "grupakadry", referencedColumnName = "id")
    @OneToOne
    private Grupakadry grupakadry;
    @Transient
    private Zmiennawynagrodzenia zmiennawynagrodzenia;
    @Transient
    private  boolean netto0brutto1;
   

    public Umowa() {
        this.setEtat1(1);
        this.setEtat2(1);
    }

    public Umowa(int id) {
        this.id = id;
        this.setEtat1(1);
        this.setEtat2(1);
    }

    public Umowa(Umowa stara) {
        String nowadata = Data.dodajdzien(stara.datado, 1);;
        this.dataod = nowadata;
        this.datazawarcia = nowadata;
        this.nfz = stara.nfz;
        this.stanowisko = stara.stanowisko;
        this.miejscepracy = stara.miejscepracy;
        this.nieliczFGSP = stara.nieliczFGSP;
        this.nieliczFP = stara.nieliczFP;
        this.kodzawodu = stara.kodzawodu;
        this.angaz = stara.angaz;
        this.opiszawodu = stara.opiszawodu;
        this.miejscepracy = stara.miejscepracy;
        this.opiszawodu = stara.opiszawodu;
        this.innewarunkizatrudnienia = stara.innewarunkizatrudnienia;
        this.terminrozpoczeciapracy = nowadata;
        this.dopuszczalnailoscgodzin = stara.dopuszczalnailoscgodzin;
        this.etat1 = stara.etat1;
        this.etat2 = stara.etat2;
        this.umowakodzus = stara.umowakodzus;
        this.slownikszkolazatrhistoria = stara.slownikszkolazatrhistoria;
        
   }
    //sluzy do kopiowania umowy przy wystawiamniu umowy dla kilkupracownikow
    public Umowa(Umowa stara, boolean abyodroznickonstruktor) {
        this.dataod = stara.dataod;
        this.datado = stara.datado;
        this.datazawarcia = stara.datazawarcia;
        this.nfz = stara.nfz;
        this.stanowisko = stara.stanowisko;
        this.miejscepracy = stara.miejscepracy;
        this.nieliczFGSP = stara.nieliczFGSP;
        this.nieliczFP = stara.nieliczFP;
        this.kodzawodu = stara.kodzawodu;
        this.angaz = stara.angaz;
        this.opiszawodu = stara.opiszawodu;
        this.miejscepracy = stara.miejscepracy;
        this.opiszawodu = stara.opiszawodu;
        this.innewarunkizatrudnienia = stara.innewarunkizatrudnienia;
        this.terminrozpoczeciapracy = stara.terminrozpoczeciapracy;
        this.dopuszczalnailoscgodzin = stara.dopuszczalnailoscgodzin;
        this.etat1 = stara.etat1;
        this.etat2 = stara.etat2;
        this.umowakodzus = stara.umowakodzus;
        this.slownikszkolazatrhistoria = stara.slownikszkolazatrhistoria;
        this.wynagrodzeniemiesieczne = stara.wynagrodzeniemiesieczne;
        this.wynagrodzeniegodzinowe = stara.wynagrodzeniegodzinowe;
        this.wynagrodzenieoddelegowanie = stara.wynagrodzenieoddelegowanie;
        
   }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEtat1() {
        return etat1;
    }

    public void setEtat1(Integer etat1) {
        this.etat1 = etat1;
    }

    public Integer getEtat2() {
        return etat2;
    }

    public void setEtat2(Integer etat2) {
        this.etat2 = etat2;
    }



    public Angaz getAngaz() {
        return angaz;
    }
    
    public Pracownik getPracownik() {
        return this.angaz.getPracownik();
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
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

    public Grupakadry getGrupakadry() {
        return grupakadry;
    }

    public void setGrupakadry(Grupakadry grupakadry) {
        this.grupakadry = grupakadry;
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

    public int getLicznikumow() {
        return licznikumow;
    }

    public void setLicznikumow(int licznikumow) {
        this.licznikumow = licznikumow;
    }

    public Zmiennawynagrodzenia getZmiennawynagrodzenia() {
        return zmiennawynagrodzenia;
    }

    public void setZmiennawynagrodzenia(Zmiennawynagrodzenia zmiennawynagrodzenia) {
        this.zmiennawynagrodzenia = zmiennawynagrodzenia;
    }

    public boolean isNetto0brutto1() {
        return netto0brutto1;
    }

    public void setNetto0brutto1(boolean netto0brutto1) {
        this.netto0brutto1 = netto0brutto1;
    }

    public double getWynagrodzeniemiesieczne() {
        return wynagrodzeniemiesieczne;
    }

    public void setWynagrodzeniemiesieczne(double wynagrodzeniemiesieczne) {
        this.wynagrodzeniemiesieczne = wynagrodzeniemiesieczne;
    }

    public double getWynagrodzeniegodzinowe() {
        return wynagrodzeniegodzinowe;
    }

    public void setWynagrodzeniegodzinowe(double wynagrodzeniegodzinowe) {
        this.wynagrodzeniegodzinowe = wynagrodzeniegodzinowe;
    }

    public double getWynagrodzenieoddelegowanie() {
        return wynagrodzenieoddelegowanie;
    }

    public void setWynagrodzenieoddelegowanie(double wynagrodzenieoddelegowanie) {
        this.wynagrodzenieoddelegowanie = wynagrodzenieoddelegowanie;
    }
    
    

    @XmlTransient
    public List<Rachunekdoumowyzlecenia> getRachunekdoumowyzleceniaList() {
        return rachunekdoumowyzleceniaList;
    }

    public void setRachunekdoumowyzleceniaList(List<Rachunekdoumowyzlecenia> rachunekdoumowyzleceniaList) {
        this.rachunekdoumowyzleceniaList = rachunekdoumowyzleceniaList;
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
        if (angaz!=null) {
            return "Umowa{"+ ", dataod=" + dataod + "datado=" + datado  + ", angaz=" + angaz.getPracownik().getNazwiskoImie() + ", rodzajumowy="+ "}";
        } else {
            return "Umowa{"+ ", dataod=" + dataod  + "datado=" + datado + ", rodzajumowy="+ "}";
        }
    }

   public String umowanumernazwa() {
       return this.nrkolejny;
   }
   



    public Umowakodzus getUmowakodzus() {
        return umowakodzus;
    }
    public void setUmowakodzus(Umowakodzus umowakodzus) {
        this.umowakodzus = umowakodzus;
    }
    public String getCzastrwania() {
        String zwrot = "brak informacji";
        if (this.slownikszkolazatrhistoria!=null) {
            zwrot = this.slownikszkolazatrhistoria.getOpis();
            zwrot = zwrot.replace("biezacy", "");
        }
        return zwrot;
    }
    
    public String getLataDni() {
        String zwrot = "brak informacji";
        if (this.dataod!=null&&datado!=null) {
            zwrot = Data.obliczwiekString(this.dataod, this.datado);
        }
        return zwrot;
    }
    public void setCzastrwania(String czastrwania) {
        int zwrot = CzasTrwania.find(czastrwania);;
        this.czastrwania = zwrot;
    }
    
    public Kodyzawodow getKodzawodu() {
        return kodzawodu;
    }
    public void setKodzawodu(Kodyzawodow kodzawodu) {
        this.kodzawodu = kodzawodu;
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


    public String getInnewarunkizatrudnienia() {
        return innewarunkizatrudnienia;
    }

    public void setInnewarunkizatrudnienia(String innewarunkizatrudnienia) {
        String zwrot = innewarunkizatrudnienia.replace("<p>", "").replace("</p>", "");
        this.innewarunkizatrudnienia = zwrot;
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

    public Rozwiazanieumowy getRozwiazanieumowy() {
        return rozwiazanieumowy;
    }

    public void setRozwiazanieumowy(Rozwiazanieumowy rozwiazanieumowy) {
        this.rozwiazanieumowy = rozwiazanieumowy;
    }

    public String getSymbolwalutyoddelegowanie() {
        return symbolwalutyoddelegowanie;
    }

    public void setSymbolwalutyoddelegowanie(String symbolwalutyoddelegowanie) {
        this.symbolwalutyoddelegowanie = symbolwalutyoddelegowanie;
    }

  
    public String getWiekumowa() {
        String zwrot = "";
        if (this.dataod!=null) {
            zwrot = this.lata+" lat i "+this.dni+" dni";
        }
        return zwrot;
    }
    
  public boolean czynalezydookresu(String rok, String mc) {
        boolean zwrot = false;
        String dataod = this.dataod;
        String datado = this.datado;
        if (datado==null||datado.equals("")) {
            datado = "2050-12-31";
        }
        if (Data.czyjestpomiedzy(dataod, datado, rok, mc)) {
            zwrot = true;
        }
        //String dataod ()
        return zwrot;
    }
       public boolean nalezydomiesiaca(String rok, String mc) {
        boolean zaczynasiew = Data.czydatajestwmcu(this.dataod, rok, mc);
        boolean konczysiew = Data.czydatajestwmcu(this.dataod, rok, mc);
        boolean zwrot = zaczynasiew || konczysiew;
        return zwrot;
    }

       
     

//    private List<EtatPrac> noweetat(List<EtatPrac> etatList, Umowa aThis) {
//        List<EtatPrac> zwrot = new ArrayList<>();
//        if (etatList!=null) {
//            for (EtatPrac e : etatList) {
//                if (e.getDatado()==null || e.getDatado().equals("")) {
//                    EtatPrac nowy = new EtatPrac();
//                    nowy.setDataod(aThis.getDataod());
//                    nowy.setEtat1(e.getEtat1());
//                    nowy.setEtat2(e.getEtat2());
//                    nowy.setUmowa(aThis);
//                    zwrot.add(nowy);
//                }
//            }
//        }
//        return zwrot;
//    }
//
//    private List<Skladnikpotracenia> nowepotracenie(List<Skladnikpotracenia> skladnikpotraceniaList, Umowa aThis) {
//        List<Skladnikpotracenia> zwrot = new ArrayList<>();
//        if (skladnikpotraceniaList!=null) {
//            for (Skladnikpotracenia e : skladnikpotraceniaList) {
//                if (e.isRozliczony()==false) {
//                    Skladnikpotracenia nowy = new Skladnikpotracenia();
//                    nowy.setUmowa(aThis);
//                    nowy.setNazwa(e.getNazwa());
//                    nowy.setRodzajpotracenia(e.getRodzajpotracenia());
//                    nowy.setRozliczony(false);
//                    nowy.setZmiennapotraceniaList(nowezmienne(e.getZmiennapotraceniaList()));
//                    zwrot.add(nowy);
//                }
//            }
//        }
//        return zwrot;
//    }
//
//    private List<Zmiennapotracenia> nowezmienne(List<Zmiennapotracenia> zmiennapotraceniaList) {
//        List<Zmiennapotracenia> zwrot = new ArrayList<>();
//        if (zmiennapotraceniaList!=null) {
//            for (Zmiennapotracenia e : zmiennapotraceniaList) {
//                if (e.getDatado()==null || e.getDatado().equals("")) {
//                    Zmiennapotracenia nowy = new Zmiennapotracenia();
//                    nowy.setDataod(e.getDataod());
//                    nowy.setSkladnikpotracenia(e.getSkladnikpotracenia());
//                    nowy.setKwotakomornicza(e.getKwotakomornicza());
//                    nowy.setSkladnikpotracenia(e.getSkladnikpotracenia());
//                    nowy.setKwotakomornicza(e.getKwotakomornicza());
//                    nowy.setKwotakomorniczarozliczona(e.getKwotakomorniczarozliczona());
//                    nowy.setKwotastala(e.getKwotastala());
//                    nowy.setMaxustawowy(e.isMaxustawowy());
//                    zwrot.add(nowy);
//                }
//            }
//        }
//        return zwrot;
//    }
//
//    private List<Stanowiskoprac> nowestanowisko(List<Stanowiskoprac> stanowiskopracList, Umowa aThis) {
//        List<Stanowiskoprac> zwrot = new ArrayList<>();
//        if (stanowiskopracList!=null) {
//            for (Stanowiskoprac e : stanowiskopracList) {
//                if (e.getDatado()==null || e.getDatado().equals("")) {
//                    Stanowiskoprac nowy = new Stanowiskoprac();
//                    nowy.setDataod(aThis.getDataod());
//                    nowy.setOpis(e.getOpis());
//                    nowy.setUwagi(e.getUwagi());
//                    nowy.setUmowa(aThis);
//                    zwrot.add(nowy);
//                }
//            }
//        }
//        return zwrot;
//    }
//
//    private List<Skladnikwynagrodzenia> noweskladniki(List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList, Umowa aThis) {
//        List<Skladnikwynagrodzenia> zwrot = new ArrayList<>();
//        if (skladnikwynagrodzeniaList!=null) {
//            for (Skladnikwynagrodzenia e : skladnikwynagrodzeniaList) {
//                if (e.getZmiennawynagrodzeniaList()==null) {
//                    Skladnikwynagrodzenia nowy = new Skladnikwynagrodzenia();
//                    nowy.setUmowa(aThis);
//                    nowy.setOddelegowanie(e.isOddelegowanie());
//                    nowy.setRodzajwynagrodzenia(nowy.getRodzajwynagrodzenia());
//                    nowy.setUwagi(nowy.getUwagi());
//                    //nowy.setWks_serial(nowy.getWks_serial());
//                    nowy.setZmiennawynagrodzeniaList(nowezmiennewynagrodzenia(e.getZmiennawynagrodzeniaList()));
//                    if (nowy.getZmiennawynagrodzeniaList()!=null&&nowy.getZmiennawynagrodzeniaList().size()>0) {
//                        zwrot.add(nowy);
//                    }
//                }
//            }
//        }
//        return zwrot;
//    }
//
//    
//    private List<Zmiennawynagrodzenia> nowezmiennewynagrodzenia(List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList) {
//        List<Zmiennawynagrodzenia> zwrot = new ArrayList<>();
//        if (zmiennawynagrodzeniaList!=null) {
//            for (Zmiennawynagrodzenia e : zmiennawynagrodzeniaList) {
//                if (e.getDatado()==null || e.getDatado().equals("")) {
//                    Zmiennawynagrodzenia nowy = new Zmiennawynagrodzenia();
//                    nowy.setDataod(e.getDataod());
    
//                    nowy.setSkladnikwynagrodzenia(e.getSkladnikwynagrodzenia());
//                    nowy.setKwota(e.getKwota());
//                    nowy.setMinimalneustatowe(e.isMinimalneustatowe());
//                    nowy.setNazwa(e.getNazwa());
//                    nowy.setNetto0brutto1(e.isNetto0brutto1());
//                    nowy.setAktywna(e.isAktywna());
//                    nowy.setWaluta(e.getWaluta());
//                    zwrot.add(nowy);
//                }
//            }
//        }
//        return zwrot;
//    }

    public String getEtat() {
        String zwrot = "Zrobic pole etat w umowie!";
        if (this.etat1!=null&&this.etat2!=null) {
            zwrot = this.etat1+"/"+this.etat2;
        }
        return zwrot;
    }

    public String pobierzwynagrodzenieString() {
        return "Zrobić pole opisowe wynagrodzenie w umowie!";
    }

    public String pobierzwynagrodzenieString(double wynagrodzeniegodzinowe) {
        String zwrot = "brak stawki";
        if (wynagrodzeniegodzinowe>0.0) {
            String sl = String.valueOf(wynagrodzeniegodzinowe);
            zwrot = f.F.curr(wynagrodzeniegodzinowe)+" brutto "+" - słownie: "+slownie.Slownie.slownie(sl);
        }
        return zwrot;
    }

    public boolean czynalezydoroku(String rok) {
        boolean zwrot = false;
        String rokod = Data.getRok(this.dataod);
        String rokdo = Data.getRok(this.datado);
        if (rokdo==null||rokdo.equals("")) {
            rokdo = rok;
        }
        Integer rokodInt = Integer.parseInt(rokod);//2022
        Integer rokdoInt = Integer.parseInt(rokdo);//2023
        Integer rokInt = Integer.parseInt(rok);//2021
        if (rokodInt<=rokInt&&rokInt<=rokdoInt) {
            zwrot = true;
        }
        //String dataod ()
        return zwrot;
    }

    public static void main(String[] args) {
        boolean zwrot = false;
        String rok = "2023";
        String rokod = Data.getRok("2022-01-01");
        String rokdo = Data.getRok(null);
        if (rokdo==null||rokdo.equals("")) {
            rokdo = rok;
        }
        Integer rokodInt = Integer.parseInt(rokod);//2022
        Integer rokdoInt = Integer.parseInt(rokdo);//2023
        Integer rokInt = Integer.parseInt(rok);//2021
        if (rokodInt<=rokInt&&rokInt<=rokdoInt) {
            zwrot = true;
        }
        System.out.println("wynik "+zwrot);
    }
   
}
