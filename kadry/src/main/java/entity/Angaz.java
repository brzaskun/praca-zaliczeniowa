/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import beanstesty.DataBean;
import data.Data;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
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
@Table(name = "angaz", uniqueConstraints = {
    @UniqueConstraint(columnNames={"firma","pracownik"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Angaz.findAll", query = "SELECT a FROM Angaz a"),
    @NamedQuery(name = "Angaz.findById", query = "SELECT a FROM Angaz a WHERE a.id = :id"),
    @NamedQuery(name = "Angaz.findByFirma", query = "SELECT a FROM Angaz a WHERE a.firma = :firma ORDER BY a.pracownik.nazwisko"),
    @NamedQuery(name = "Angaz.findByFirmaPracownik", query = "SELECT a FROM Angaz a WHERE a.firma = :firma AND a.pracownik = :pracownik"),
    @NamedQuery(name = "Angaz.findByFirmaAktywni", query = "SELECT a FROM Angaz a WHERE a.firma = :firma AND a.ukryj = FALSE ORDER BY a.pracownik.nazwisko"),
    @NamedQuery(name = "Angaz.findByPeselFirma", query = "SELECT a FROM Angaz a WHERE a.pracownik.pesel = :pesel AND a.firma = :firma"),
    @NamedQuery(name = "Angaz.findPracownikByFirma", query = "SELECT a.pracownik FROM Angaz a WHERE a.firma = :firma")
})

public class Angaz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL)
    private List<Umowa> umowaList;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private FirmaKadry firma;
    @JoinColumn(name = "pracownik", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private Pracownik pracownik;
    @Column(name = "serialsp")
    private String serialsp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz", orphanRemoval = true)
    private List<Kartawynagrodzen> kartawynagrodzenList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz", orphanRemoval = true)
    private List<Wynagrodzeniahistoryczne> wynagrodzeniahistoryczneList;
    @OneToMany(mappedBy = "angaz")
    private List<Memory> memoryList;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datadodania")
    private Date datadodania;
    @Column(name = "utworzyl")
    private String utworzyl;
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rok")
    private String rok;
    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "mc")
    private String mc;
    @Column(name = "kosztyuzyskaniaprocent")
    private double kosztyuzyskaniaprocent;
    @Column(name = "kosztyuzyskania0podwyzszone")
    private boolean kosztyuzyskania0podwyzszone;
    @Column(name = "kwotawolnaprocent")
    private double kwotawolnaprocent;
    @Column(name = "odliczaculgepodatkowa")
    private boolean odliczaculgepodatkowa;
    @Column(name = "dataprzyjazdudopolski")
    private String dataprzyjazdudopolski;
    @Column(name = "nierezydent")
    private boolean nierezydent;
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
    @Column(name = "ukryj")
    private boolean ukryj;
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Staz> stazList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz", orphanRemoval = true)
    private List<EtatPrac> etatList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz", orphanRemoval = true)
    private List<Nieobecnosc> nieobecnoscList;
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kalendarzmiesiac> kalendarzmiesiacList;
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skladnikpotracenia> skladnikpotraceniaList;
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz", orphanRemoval = true)
    private List<Stanowiskoprac> stanowiskopracList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz", orphanRemoval = true)
    private List<Nieobecnoscprezentacja> urlopprezentacjaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz", orphanRemoval = true)
    private List<Dokumenty> dokumentyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz", orphanRemoval = true)
    private List<Rejestrurlopow> rejestrurlopowList;
    @Column(name = "student")
    private  boolean student;
    @Column(name = "pierwszyprogwniosek")
    private  boolean pierwszyprogwniosek;
    
    @Column(name = "przekroczenierok")
    private String przekroczenierok;
    @Column(name = "przekroczeniemc")
    private String przekroczeniemc;
    @Column(name = "bourloprok")
    private String bourloprok;
    @Column(name = "bourlopdni")
    private int bourlopdni;
    @Column(name = "bourlopgodziny")
    private int bourlopgodziny;
    @Column(name = "databezrobotnyskierowanie")
    private String  databezrobotnyskierowanie;
    @Column(name = "dataa1")
    private String  dataa1;
    @Column(name = "dataa1mail")
    private String  dataa1mail;
    @Column(name = "databadanielekarskie")
    private String  databadanielekarskie;
    @Column(name = "databadanielekarskiemail")
    private String  databadanielekarskiemail;
//    @Transient
//    private int m0;
//    @Transient
//    private int m1;
//    @Transient
//    private int m2;
//    @Transient
//    private int m3;
//    @Transient
//    private int m4;
//    @Transient
//    private int m5;
//    @Transient
//    private int m6;
//    @Transient
//    private int m7;
//    @Transient
//    private int m8;
//    @Transient
//    private int m9;
//    @Transient
//    private int m10;
//    @Transient
//    private int m11;
//    @Transient
//    private int m12;
//    @Transient
//    private int m13;
//    @Transient
//    private int m14;

     

    public Angaz() {
    }

    public Angaz(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUtworzyl() {
        return utworzyl;
    }

    public void setUtworzyl(String utworzyl) {
        this.utworzyl = utworzyl;
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

    public String getPrzekroczenierok() {
        return przekroczenierok;
    }

    public void setPrzekroczenierok(String przekroczenierok) {
        this.przekroczenierok = przekroczenierok;
    }

    public String getPrzekroczeniemc() {
        return przekroczeniemc;
    }

    public void setPrzekroczeniemc(String przekroczeniemc) {
        this.przekroczeniemc = przekroczeniemc;
    }

    public String getBourloprok() {
        return bourloprok;
    }

    public void setBourloprok(String bourloprok) {
        this.bourloprok = bourloprok;
    }

    public boolean isPierwszyprogwniosek() {
        return pierwszyprogwniosek;
    }

    public void setPierwszyprogwniosek(boolean pierwszyprogwniosek) {
        this.pierwszyprogwniosek = pierwszyprogwniosek;
    }


    
    public int getBourlopdni() {
        return bourlopdni;
    }

    public void setBourlopdni(int bourlopdni) {
        this.bourlopdni = bourlopdni;
    }

    public int getBourlopgodziny() {
        return bourlopgodziny;
    }

    public void setBourlopgodziny(int bourlopgodziny) {
        this.bourlopgodziny = bourlopgodziny;
    }

    public List<Staz> getStazList() {
        return stazList;
    }

    public void setStazList(List<Staz> stazList) {
        this.stazList = stazList;
    }

   public String getStudentsymbol(){
        String zwrot = "";
        if (this.student) {
            zwrot = "✔";
        }
        return zwrot;
    }
    
    public String getUlgasymbol(){
        String zwrot = "";
        if (this.odliczaculgepodatkowa) {
            zwrot = "✔";
        }
        return zwrot;
    }
    

    @XmlTransient
    public List<Umowa> getUmowaList() {
        return umowaList;
    }

    public void setUmowaList(List<Umowa> umowaList) {
        this.umowaList = umowaList;
    }

    public FirmaKadry getFirma() {
        return firma;
    }

    public void setFirma(FirmaKadry firma) {
        this.firma = firma;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public Date getDatadodania() {
        return datadodania;
    }

    public void setDatadodania(Date datadodania) {
        this.datadodania = datadodania;
    }

    public double getKosztyuzyskaniaprocent() {
        return kosztyuzyskaniaprocent;
    }

    public void setKosztyuzyskaniaprocent(double kosztyuzyskaniaprocent) {
        this.kosztyuzyskaniaprocent = kosztyuzyskaniaprocent;
    }

    public boolean isKosztyuzyskania0podwyzszone() {
        return kosztyuzyskania0podwyzszone;
    }

    public void setKosztyuzyskania0podwyzszone(boolean kosztyuzyskania0podwyzszone) {
        this.kosztyuzyskania0podwyzszone = kosztyuzyskania0podwyzszone;
    }

    public double getKwotawolnaprocent() {
        return kwotawolnaprocent;
    }

    public void setKwotawolnaprocent(double kwotawolnaprocent) {
        this.kwotawolnaprocent = kwotawolnaprocent;
    }

    public boolean isOdliczaculgepodatkowa() {
        return odliczaculgepodatkowa;
    }

    public void setOdliczaculgepodatkowa(boolean odliczaculgepodatkowa) {
        this.odliczaculgepodatkowa = odliczaculgepodatkowa;
    }

    public String getDataprzyjazdudopolski() {
        return dataprzyjazdudopolski;
    }

    public void setDataprzyjazdudopolski(String dataprzyjazdudopolski) {
        this.dataprzyjazdudopolski = dataprzyjazdudopolski;
    }

    public boolean isNierezydent() {
        return nierezydent;
    }

    public void setNierezydent(boolean nierezydent) {
        this.nierezydent = nierezydent;
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

    public boolean isUkryj() {
        return ukryj;
    }

    public void setUkryj(boolean ukryj) {
        this.ukryj = ukryj;
    }

    public String getDatabezrobotnyskierowanie() {
        return databezrobotnyskierowanie;
    }

    public void setDatabezrobotnyskierowanie(String databezrobotnyskierowanie) {
        this.databezrobotnyskierowanie = databezrobotnyskierowanie;
    }

    public String getDataa1mail() {
        return dataa1mail;
    }

    public void setDataa1mail(String dataa1mail) {
        this.dataa1mail = dataa1mail;
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
    
    @XmlTransient   
   public List<Nieobecnosc> getNieobecnoscList() {
       return nieobecnoscList;
   }
   public void setNieobecnoscList(List<Nieobecnosc> nieobecnoscList) {
       this.nieobecnoscList = nieobecnoscList;
   }
   
   @XmlTransient
    public List<EtatPrac> getEtatList() {
        return etatList;
    }
    public void setEtatList(List<EtatPrac> etatList) {
        this.etatList = etatList;
    }
    
    public List<Stanowiskoprac> getStanowiskopracList() {
        return stanowiskopracList;
    }

    public void setStanowiskopracList(List<Stanowiskoprac> stanowiskopracList) {
        this.stanowiskopracList = stanowiskopracList;
    }

    public List<Rejestrurlopow> getRejestrurlopowList() {
        return rejestrurlopowList;
    }

    public void setRejestrurlopowList(List<Rejestrurlopow> rejestrurlopowList) {
        this.rejestrurlopowList = rejestrurlopowList;
    }

    
    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public String getDataa1() {
        return dataa1;
    }

    public void setDataa1(String dataa1) {
        this.dataa1 = dataa1;
    }

    public String getDatabadanielekarskie() {
        return databadanielekarskie;
    }

    public void setDatabadanielekarskie(String databadanielekarskie) {
        this.databadanielekarskie = databadanielekarskie;
    }

    public String getDatabadanielekarskiemail() {
        return databadanielekarskiemail;
    }

    public void setDatabadanielekarskiemail(String databadanielekarskiemail) {
        this.databadanielekarskiemail = databadanielekarskiemail;
    }

    
    
//    public int getM0() {
//        return m0;
//    }
//
//    public void setM0(int m0) {
//        this.m0 = m0;
//    }
//
//    public int getM1() {
//        return m1;
//    }
//
//    public void setM1(int m1) {
//        this.m1 = m1;
//    }
//
//    public int getM2() {
//        return m2;
//    }
//
//    public void setM2(int m2) {
//        this.m2 = m2;
//    }
//
//    public int getM3() {
//        return m3;
//    }
//
//    public void setM3(int m3) {
//        this.m3 = m3;
//    }
//
//    public int getM4() {
//        return m4;
//    }
//
//    public void setM4(int m4) {
//        this.m4 = m4;
//    }
//
//    public int getM5() {
//        return m5;
//    }
//
//    public void setM5(int m5) {
//        this.m5 = m5;
//    }
//
//    public int getM6() {
//        return m6;
//    }
//
//    public void setM6(int m6) {
//        this.m6 = m6;
//    }
//
//    public int getM7() {
//        return m7;
//    }
//
//    public void setM7(int m7) {
//        this.m7 = m7;
//    }
//
//    public int getM8() {
//        return m8;
//    }
//
//    public void setM8(int m8) {
//        this.m8 = m8;
//    }
//
//    public int getM9() {
//        return m9;
//    }
//
//    public void setM9(int m9) {
//        this.m9 = m9;
//    }
//
//    public int getM10() {
//        return m10;
//    }
//
//    public void setM10(int m10) {
//        this.m10 = m10;
//    }
//
//    public int getM11() {
//        return m11;
//    }
//
//    public void setM11(int m11) {
//        this.m11 = m11;
//    }
//
//    public int getM12() {
//        return m12;
//    }
//
//    public void setM12(int m12) {
//        this.m12 = m12;
//    }
//
//    public int getM13() {
//        return m13;
//    }
//
//    public void setM13(int m13) {
//        this.m13 = m13;
//    }
//
//    public int getM14() {
//        return m14;
//    }
//
//    public void setM14(int m14) {
//        this.m14 = m14;
//    }
//    
    
    public String getStazurlop() {
        return this.pracownik.getStazlata()+"l. "+this.pracownik.getStazdni()+"dn.";
    }
    
     @XmlTransient
    public List<Nieobecnoscprezentacja> getUrlopprezentacjaList() {
        return urlopprezentacjaList;
    }

    public void setUrlopprezentacjaList(List<Nieobecnoscprezentacja> urlopprezentacjaList) {
        this.urlopprezentacjaList = urlopprezentacjaList;
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
        if (!(object instanceof Angaz)) {
            return false;
        }
        Angaz other = (Angaz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Angaz{, firma=" + firma.getNazwa() + ", pracownik=" + pracownik.getNazwiskoImie() + ", serialsp=" + serialsp + '}';
    }

   public String getNazwiskoiImie() {
        return this.getPracownik().getNazwiskoImie();
   }

    public String getAngazString() {
        return this.getFirma().getNazwa()+" "+this.getPracownik().getNazwiskoImie();
    }
    public String getAngazStringPlik() {
        return this.getFirma().getNazwa();
    }
    
    public String getAngazFirmaNip() {
        return this.getFirma().getNip();
    }

    @XmlTransient
    public List<Memory> getMemoryList() {
        return memoryList;
    }

    public void setMemoryList(List<Memory> memoryList) {
        this.memoryList = memoryList;
    }

    @XmlTransient
    public List<Wynagrodzeniahistoryczne> getWynagrodzeniahistoryczneList() {
        return wynagrodzeniahistoryczneList;
    }

    public void setWynagrodzeniahistoryczneList(List<Wynagrodzeniahistoryczne> wynagrodzeniahistoryczneList) {
        this.wynagrodzeniahistoryczneList = wynagrodzeniahistoryczneList;
    }

    public String getSerialsp() {
        return serialsp;
    }

    public void setSerialsp(String serialsp) {
        this.serialsp = serialsp;
    }

    public List<Dokumenty> getDokumentyList() {
        return dokumentyList;
    }

    public void setDokumentyList(List<Dokumenty> dokumentyList) {
        this.dokumentyList = dokumentyList;
    }

    public String getRokMc() {
        return this.rok+"/"+this.mc;
    }
    
    @XmlTransient
    public List<Kartawynagrodzen> getKartawynagrodzenList() {
        return kartawynagrodzenList;
    }

    public void setKartawynagrodzenList(List<Kartawynagrodzen> kartawynagrodzenList) {
        this.kartawynagrodzenList = kartawynagrodzenList;
    }

     public String pobierzwynagrodzenieString(WpisView wpisView) {
        String zwrot = "";
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                if (p.getRodzajwynagrodzenia().getKod().equals("40")||p.getRodzajwynagrodzenia().getId()==91) {
                    zwrot = zwrot+p.getRodzajwynagrodzenia().getOpispelny()+" ";
                    zwrot = zwrot+pobierzkwoteString(p.getZmiennawynagrodzeniaList(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                }
            }
        }
        return zwrot;
    }
    
    public String pobierzwynagrodzenieString() {
        String zwrot = "";
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                if (p.getRodzajwynagrodzenia().getKod().equals("40")) {
                    zwrot = zwrot+p.getRodzajwynagrodzenia().getOpispelny()+" ";
                    zwrot = zwrot+pobierzkwoteString(p.getZmiennawynagrodzeniaList());
                }
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
    
    private String pobierzkwoteString(List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList) {
        String zwrot = "";
        if (zmiennawynagrodzeniaList!=null) {
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                String dataod1 = p.getDataod();
                String datado1 = p.getDatado();
                zwrot = zwrot+p.getNazwa()+" ";
                zwrot = zwrot+f.F.curr(p.getKwota());
                break;
            }
        }
        return zwrot;
    }
    
     public double pobierzwynagrodzenieKwota(String rok, String mc, Kalendarzmiesiac kalendarz) {
        double zwrot = 0.0;
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                if (p.getRodzajwynagrodzenia().getKod().equals("40")||p.getRodzajwynagrodzenia().getKod().equals("NZ")||p.getRodzajwynagrodzenia().getId()==91) {
                    zwrot = zwrot + pobierzkwoteKwota(p.getZmiennawynagrodzeniaList(), rok, mc, kalendarz);
                }
            }
        }
        return zwrot;
    }   
     
     public double pobierzwynagrodzenieKwotaWaluta(String rok, String mc, Kalendarzmiesiac kalendarz) {
        double zwrot = 0.0;
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                zwrot = zwrot + pobierzkwoteKwotaWaluta(p.getZmiennawynagrodzeniaList(), rok, mc, kalendarz);
            }
        }
        return zwrot;
    }   
     
    

    private double pobierzkwoteKwota(List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList, String rok, String mc, Kalendarzmiesiac kalendarz) {
        double zwrot = 0.0;
        if (zmiennawynagrodzeniaList!=null) {
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                if (p.getWaluta()==null||p.getWaluta().equals("PLN")) {
                    String dataod1 = p.getDataod();
                    String datado1 = p.getDatado();
                    if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), dataod1, datado1)) {
                        zwrot = p.getKwota();
                        break;
                    }
                }
            }
        }
        return zwrot;
    }
    
    private double pobierzkwoteKwotaWaluta(List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList, String rok, String mc, Kalendarzmiesiac kalendarz) {
        double zwrot = 0.0;
        if (zmiennawynagrodzeniaList!=null) {
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                if (p.getWaluta()!=null&&!p.getWaluta().equals("PLN")) {
                    String dataod1 = p.getDataod();
                    String datado1 = p.getDatado();
                    if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), dataod1, datado1)) {
                        zwrot = p.getKwota();
                        break;
                    }
                }
            }
        }
        return zwrot;
    }
    
    public boolean czywynagrodzeniegodzinowe() {
        boolean zwrot = false;
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                if (p.getRodzajwynagrodzenia().getKod().equals("11")||p.getRodzajwynagrodzenia().getKod().equals("40")) {
                    zwrot = !p.getRodzajwynagrodzenia().getGodzinowe0miesieczne1();
                }
            }
        }
        return zwrot;
    }
    
    public boolean czyrusztowania() {
        boolean zwrot = false;
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                if (p.getRodzajwynagrodzenia().getWks_serial()==1078) {
                    zwrot = true;
                }
            }
        }
        return zwrot;
    }
    
    public boolean czywynagrodzeniegodzinoweRachunek() {
        boolean zwrot = false;
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                if (p.getRodzajwynagrodzenia().getKod().equals("40")||p.getRodzajwynagrodzenia().getKod().equals("NZ")) {
                    zwrot = !p.getRodzajwynagrodzenia().getGodzinowe0miesieczne1();
                }
            }
        }
        return zwrot;
    }
    
    

   

    public String getEtat() {
        String zwrot = "pełny etat";
        if (this.etatList!=null&&this.etatList.size()>0) {
            EtatPrac e = etatList.get(0);
            switch (e.getEtat()){
                case "1/1":
                    zwrot = "pełny etat";
                    break;
                default:
                    zwrot = e.getEtat()+" etatu";
                    break;
            }
        }
        return zwrot;
    }

    public Skladnikwynagrodzenia pobierzskladnikzlecenieMiesieczne() {
        Skladnikwynagrodzenia zwrot = null;
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                if (p.getRodzajwynagrodzenia().getStale0zmienne1()==false) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }
    
    public EtatPrac pobierzetat(String data) {
       EtatPrac zwrot = null;
        List<EtatPrac> etatList1 = this.etatList;
        if (etatList1!=null) {
            for (EtatPrac p : etatList1) {
                String datagraniczna = p.getDataod();
                if (Data.czyjestpoTerminData(datagraniczna, data)) {
                    zwrot = p;
                }
            }
        }
        return zwrot;
    }
    
//    public List<Kalendarzmiesiac> getKalendarze() {
//        List<Kalendarzmiesiac> lista = new ArrayList<>();
//        List<Umowa> umowy = this.getUmowaList();
//        if (umowy != null) {
//            for (Umowa u : umowy) {
//                lista.addAll(u.getKalendarzmiesiacList());
//            }
//        }
//        Collections.sort(lista, new Kalendarzmiesiaccomparator());
//        return lista;
//    }

    public Umowa pobierzumowaZlecenia(String rok, String mc) {
        Umowa zwrot = null;
        List<Umowa> umowaList1 = this.umowaList;
        for (Umowa z : umowaList1) {
            if (z.getUmowakodzus().isZlecenie()) {
                if (z.czynalezydookresu(rok,mc)) {
                    zwrot = z;
                    break;
                }
            }
        }
        return zwrot;
    }
    
    public Umowa pobierzumowaDzielo(String rok, String mc) {
        Umowa zwrot = null;
        List<Umowa> umowaList1 = this.umowaList;
        for (Umowa z : umowaList1) {
            if (z.getUmowakodzus().isDzielo()) {
                if (z.czynalezydookresu(rok,mc)) {
                    zwrot = z;
                    break;
                }
            }
        }
        return zwrot;
    }
    
     public boolean jestumowaPracaAktywna(String rok, String mc) {
        boolean zwrot = false;
        List<Umowa> umowaList1 = this.umowaList;
        for (Umowa z : umowaList1) {
            if (z.isPraca()&&z.czynalezydookresu(rok,mc)) {
                zwrot = true;
                break;
            }
        }
        return zwrot;
    }
     
     public boolean jestumowaAktywna(String rok, String mc) {
        boolean zwrot = false;
        List<Umowa> umowaList1 = this.umowaList;
        for (Umowa z : umowaList1) {
            if (z.czynalezydookresu(rok,mc)) {
                zwrot = true;
                break;
            }
        }
        return zwrot;
    }
    
    public Umowa pobierzumowaAktywna(String rok, String mc) {
        Umowa zwrot = null;
        List<Umowa> umowaList1 = this.umowaList;
        for (Umowa z : umowaList1) {
            if (z.czynalezydookresu(rok,mc)) {
                zwrot = z;
                break;
            }
        }
        return zwrot;
    }

    public Skladnikwynagrodzenia pobierzSkladnikwynagrodzenia(Rodzajwynagrodzenia rodzajwynagrodzenia) {
        Skladnikwynagrodzenia zwrot = null;
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia s : this.skladnikwynagrodzeniaList) {
                if (s.getRodzajwynagrodzenia().equals(rodzajwynagrodzenia)) {
                    zwrot = s;
                }
            }
        }
        return zwrot;
    }

    public Umowa getAktywnaUmowa() {
        Umowa zwrot = null;
        if (this.umowaList!=null) {
            if (this.umowaList.size()==1) {
                zwrot = this.umowaList.get(0);
            } else {
                for (Umowa s : this.umowaList) {
                    if (s.isAktywna()) {
                        zwrot = s;
                    }
                }
            }
        }
        return zwrot;
    }

    

    
    
    
}
