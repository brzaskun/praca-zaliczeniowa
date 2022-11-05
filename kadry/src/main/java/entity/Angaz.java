/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import beanstesty.DataBean;
import comparator.KalendarzmiesiacLastcomparator;
import data.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
@Table(name = "angaz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Angaz.findAll", query = "SELECT a FROM Angaz a"),
    @NamedQuery(name = "Angaz.findById", query = "SELECT a FROM Angaz a WHERE a.id = :id"),
    @NamedQuery(name = "Angaz.findByFirma", query = "SELECT a FROM Angaz a WHERE a.firma = :firma ORDER BY a.pracownik.nazwisko"),
    @NamedQuery(name = "Angaz.findByFirmaAktywni", query = "SELECT a FROM Angaz a WHERE a.firma = :firma AND a.pracownik.aktywny = TRUE ORDER BY a.pracownik.nazwisko"),
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
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz")
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz", orphanRemoval = true)
    private List<EtatPrac> etatList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz")
    private List<Nieobecnosc> nieobecnoscList;
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kalendarzmiesiac> kalendarzmiesiacList;
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skladnikpotracenia> skladnikpotraceniaList;
    @OneToMany(mappedBy = "angaz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz")
    private List<Stanowiskoprac> stanowiskopracList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "angaz")
    private List<Nieobecnoscprezentacja> urlopprezentacjaList;


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
                zwrot = zwrot+p.getRodzajwynagrodzenia().getOpispelny()+" ";
                zwrot = zwrot+pobierzkwoteString(p.getZmiennawynagrodzeniaList(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
            }
        }
        return zwrot;
    }
    
    public String pobierzwynagrodzenieString() {
        String zwrot = "";
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                zwrot = zwrot+p.getRodzajwynagrodzenia().getOpispelny()+" ";
                zwrot = zwrot+pobierzkwoteString(p.getZmiennawynagrodzeniaList());
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

    public Skladnikwynagrodzenia pobierzskladnikzlecenie() {
        Skladnikwynagrodzenia zwrot = null;
        if (this.skladnikwynagrodzeniaList!=null) {
            for (Skladnikwynagrodzenia p : this.skladnikwynagrodzeniaList) {
                zwrot = p;
                break;
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
                if (Data.czyjestpo(datagraniczna, data)) {
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

    public Umowa pobierzumowa(String rok, String mc) {
        Umowa zwrot = null;
        List<Umowa> umowaList1 = this.umowaList;
        for (Umowa z : umowaList1) {
            if (z.getRodzajumowy().equals("umowa zlecenia")) {
                if (z.czynalezydookresu(rok,mc)) {
                    
                }
            }
        }
        return null;
    }
    
    
}
