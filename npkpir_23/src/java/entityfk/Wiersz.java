package entityfk;

import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
//@Table(name = "wiersz")
@Table(name = "wiersz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wiersz.findAll", query = "SELECT w FROM Wiersz w"),
    @NamedQuery(name = "Wiersz.findByDataksiegowania", query = "SELECT w FROM Wiersz w WHERE w.dataksiegowania = :dataksiegowania"),
    @NamedQuery(name = "Wiersz.findByIdwiersza", query = "SELECT w FROM Wiersz w WHERE w.idwiersza = :idwiersza"),
    @NamedQuery(name = "Wiersz.findByOpisWiersza", query = "SELECT w FROM Wiersz w WHERE w.opisWiersza = :opisWiersza"),
    @NamedQuery(name = "Wiersz.findByPodatnik", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik"),
    @NamedQuery(name = "Wiersz.findByPodatnikMcRok", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik AND w.dokfk.rok = :rok AND w.dokfk.miesiac = :mc"),
    @NamedQuery(name = "Wiersz.findByPodatnikRokMcDo", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik AND w.dokfk.rok = :rok AND w.dokfk.miesiac <= :mcdo"),
    @NamedQuery(name = "Wiersz.findByPodatnikMcRokWNTWDT", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik AND w.dokfk.rok = :rok AND w.dokfk.miesiac = :mc AND w.dokfk.seriadokfk = :wntwdt"),
    @NamedQuery(name = "Wiersz.findByPodatnikRok", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik AND w.dokfk.rok = :rok"),
    @NamedQuery(name = "Wiersz.findByRok", query = "SELECT w FROM Wiersz w WHERE w.dokfk.rok = :rok"),
    @NamedQuery(name = "Wiersz.findByRokMc", query = "SELECT w FROM Wiersz w WHERE w.dokfk.rok = :rok AND w.dokfk.miesiac = :mc"),
    @NamedQuery(name = "Wiersz.findByPodatnikRokTabela", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik AND w.dokfk.rok = :rok AND w.tabelanbp = :tabelanbp"),
    @NamedQuery(name = "Wiersz.findByPodatnikRokMcSeria", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik AND w.dokfk.rok = :rok AND w.dokfk.miesiac = :mc AND w.dokfk.rodzajedok.skrotNazwyDok = :rodzajdok"),
    @NamedQuery(name = "Wiersz.findByPodatnikRokMcImport", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik AND w.dokfk.rok = :rok AND w.iban IS NOT NULL")
})
@Cacheable
public class Wiersz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idwiersza", nullable = false)
    //to jest id generowany przez serwer
    private Integer idwiersza;
    @Column(name = "idporzadkowy")
    //to jest numer nadawany kazdorazowo od 1 dla numerowania wewnatrz dokumentu
    private Integer idporzadkowy;
    @Size(max = 255)
    @Column(name = "dataksiegowania", length = 255)
    private String dataksiegowania;
    @Size(max = 768)
    @Column(name = "opisWiersza", length = 768)
    private String opisWiersza;
    @Column(name = "ilosc_kg")
    private double ilosc_kg;
    @Column(name = "ilosc_szt")
    private double ilosc_szt;
    //WnMa 0
    //Wn 1
    //Ma 2
    @Column(name = "typWiersza")
    private Integer typWiersza;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dokid", referencedColumnName = "id")
    private Dokfk dokfk;
    //NIE USUWAĆ!!! to jest potrzebne do rapotow walutowych i wyciagow walutowych, chodzi o wprowadzenie daty przez użytkownika
    @Column(name = "dataWalutyWiersza")
    private String dataWalutyWiersza;
    @JoinColumn(name = "TABELANBP_idtabelanbp", referencedColumnName = "idtabelanbp")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tabelanbp tabelanbp;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wiersz", orphanRemoval = true, fetch = FetchType.EAGER)
//    @MapKeyColumn(name = "strona_key", nullable = false)
//    private Map<String, StronaWiersza> strona;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "strWn", referencedColumnName = "id")
    private StronaWiersza strWn;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "strMa", referencedColumnName = "id")
    private StronaWiersza strMa;
    @Column(name = "lpmacierzystego")
    private Integer lpmacierzystego;
    @JoinColumn(name = "CZWORKA_idwiersza", referencedColumnName = "idwiersza")
    @ManyToOne
    private Wiersz czworka;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "czworka", orphanRemoval = true)
    private Set<Wiersz> piatki;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "wiersz", orphanRemoval = true, fetch = FetchType.LAZY)
    private EVatwpisFK eVatwpisFK;
    @Column(name = "saldoWBRK")
    private double saldoWBRK;
    @Column(name = "zawierarozkurs")
    private boolean zawierarozkurs;
    @JoinColumn(name = "stronanowatransakcja", referencedColumnName = "id", updatable = true, nullable = true)
    @OneToOne
    private StronaWiersza stronanowatransakcja;
    @JoinColumn(name = "stronarozliczajacy", referencedColumnName = "id", updatable = true, nullable = true)
    @OneToOne
    private StronaWiersza stronarozliczajacy;
    private String iban;
    @Transient
    private boolean sierota;

 
    

    public Wiersz() {
        this.idporzadkowy = 1;
        this.typWiersza = 0;
        this.dokfk = new Dokfk();
        this.setStronaMa(strMa);
        this.piatki = new HashSet<>();
    }

    //trzeba wstawiac numer porzadkowy dla celow funkcji javascript ktore odpowiednio obrabiaja wiersze w trakcie wprowadzania
    public Wiersz(int idporzadkowy, Dokfk dokfk, int typwiersza) {
        //this.strona = new HashMap<>();
        this.idporzadkowy = idporzadkowy;
        this.typWiersza = typwiersza;
        this.dokfk = dokfk;
        this.piatki = new HashSet<>();
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public EVatwpisFK geteVatwpisFK() {
        return eVatwpisFK;
    }

    public void seteVatwpisFK(EVatwpisFK eVatwpisFK) {
        this.eVatwpisFK = eVatwpisFK;
    }

    public boolean isSierota() {
        return sierota;
    }

    public void setSierota(boolean sierota) {
        this.sierota = sierota;
    }


    public StronaWiersza getStronaWn() {
        return strWn;
    }

    public void setStronaWn(StronaWiersza strWn) {
        this.strWn = strWn;
    }

    public StronaWiersza getStronaMa() {
        return strMa;
    }

    public void setStronaMa(StronaWiersza strMa) {
        this.strMa = strMa;
    }

   
    public StronaWiersza getStronanowatransakcja() {
        return stronanowatransakcja;
    }

    public void setStronanowatransakcja(StronaWiersza stronanowatransakcja) {
        this.stronanowatransakcja = stronanowatransakcja;
    }

    public StronaWiersza getStronarozliczajacy() {
        return stronarozliczajacy;
    }

    public void setStronarozliczajacy(StronaWiersza stronarozliczajacy) {
        this.stronarozliczajacy = stronarozliczajacy;
    }

   
    public boolean isZawierarozkurs() {
        return zawierarozkurs;
    }

    public void setZawierarozkurs(boolean zawierarozkurs) {
        this.zawierarozkurs = zawierarozkurs;
    }

    public double getSaldoWBRK() {
        return saldoWBRK;
    }

    public void setSaldoWBRK(double saldoWBRK) {
        this.saldoWBRK = saldoWBRK;
    }

    public double getIlosc_kg() {
        return ilosc_kg;
    }

    public void setIlosc_kg(double ilosc_kg) {
        this.ilosc_kg = ilosc_kg;
    }

    public double getIlosc_szt() {
        return ilosc_szt;
    }

    public void setIlosc_szt(double ilosc_szt) {
        this.ilosc_szt = ilosc_szt;
    }

    public Wiersz getCzworka() {
        return czworka;
    }

    public void setCzworka(Wiersz czworka) {
        this.czworka = czworka;
    }

    public Set<Wiersz> getPiatki() {
        return piatki;
    }

    public void setPiatki(Set<Wiersz> piatki) {
        this.piatki = piatki;
    }

    public Integer getLpmacierzystego() {
        return lpmacierzystego;
    }

    public void setLpmacierzystego(Integer lpmacierzystego) {
        this.lpmacierzystego = lpmacierzystego;
    }

    public String getDataWalutyWiersza() {
        return dataWalutyWiersza;
    }

    public void setDataWalutyWiersza(String dataWalutyWiersza) {
        this.dataWalutyWiersza = dataWalutyWiersza;
    }

//    public void setStronaWn(StronaWiersza stronaWiersza) {
//        this.strona.put("Wn", stronaWiersza);
//    }
//
//    public void setStronaMa(StronaWiersza stronaWiersza) {
//        this.strona.put("Ma", stronaWiersza);
//    }
    
     public void removeStrona(String WnMa) {
        if (WnMa.equals("Wn")) {
            this.strWn=null;
        } else {
            this.strMa=null; 
        }
                    
    }

    public double getKwotaWn() {
        if (this.getStronaWn() != null) {
            return this.getStronaWn().getKwota();
        } else {
            return 0.0;
        }
    }
    

    public double getKwotaWnPLN() {
        if (this.getStronaWn() != null) {
            return this.getStronaWn().getKwotaPLN();
        } else {
            return 0.0;
        }
    }

    public double getKwotaMa() {
        if (this.getStronaMa() != null) {
            return this.getStronaMa().getKwota();
        } else {
            return 0.0;
        }
    }

    public double getKwotaMaPLN() {
        if (this.getStronaMa() != null) {
            return this.getStronaMa().getKwotaPLN();
        } else {
            return 0.0;
        }
    }
    
    public Konto getKontoDlaBO() {
        Konto zwrot = null;
        if (this.getStronaWn() != null) {
            zwrot = this.getStronaWn().getKonto();
        } else if (this.getStronaMa() != null) {
            zwrot = this.getStronaMa().getKonto();
        }
        return zwrot;
    }

//    public StronaWiersza getStronaWn() {
//        try {
//            return this.strona.get("Wn");
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public StronaWiersza getStronaMa() {
//        try {
//            return this.strona.get("Ma");
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public List<StronaWiersza> getStronyWiersza() {
        List<StronaWiersza> strony = new ArrayList<>();
        if (this.strWn != null) {
            strony.add(this.strWn);
        }
        if (this.strMa != null) {
            strony.add(this.strMa);
        }
        return strony;
    }

    public List<StronaWiersza> getStronyWierszaKonto() {
        List<StronaWiersza> strony = new ArrayList<>();
        if (this.strWn != null && this.strWn.getKonto() != null) {
            strony.add(this.strWn);
        }
        if (this.strMa != null && this.strMa.getKonto() != null) {
            strony.add(this.strMa);
        }
        return strony;
    }

    public boolean zawieraRRK() {
        boolean zwrot = false;
        for (StronaWiersza p : this.getStronyWierszaKonto()) {
            for (Transakcja r : p.getNowetransakcje()) {
                if (r.getRoznicekursowe() != 0.0) {
                    this.zawierarozkurs = true;
                    zwrot = true;
                    return zwrot;
                }
            }
        }
        return zwrot;
    }
    
    public String getDataDokumentu() {
        return this.dokfk.getDatadokumentu();
    }

//    public Map<String, StronaWiersza> getStrona() {
//        return strona;
//    }

    public String getDataksiegowania() {
        return dataksiegowania;
    }

    public void setDataksiegowania(String dataksiegowania) {
        this.dataksiegowania = dataksiegowania;
    }

    public Integer getIdwiersza() {
        return idwiersza;
    }

    public void setIdwiersza(Integer idwiersza) {
        this.idwiersza = idwiersza;
    }

    public Integer getIdporzadkowy() {
        return idporzadkowy;
    }

    public void setIdporzadkowy(Integer idporzadkowy) {
        this.idporzadkowy = idporzadkowy;
    }

    public String getOpisWiersza() {
        return opisWiersza;
    }

    public void setOpisWiersza(String opisWiersza) {
        this.opisWiersza = opisWiersza;
    }

    public Integer getTypWiersza() {
        return typWiersza;
    }

    public void setTypWiersza(Integer typWiersza) {
        this.typWiersza = typWiersza;
    }

    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Tabelanbp getTabelanbp() {
        return tabelanbp;
    }

    public void setTabelanbp(Tabelanbp tabelanbp) {
        this.tabelanbp = tabelanbp;
    }

    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.idwiersza);
        hash = 47 * hash + Objects.hashCode(this.idporzadkowy);
        hash = 47 * hash + Objects.hashCode(this.dokfk);
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
        final Wiersz other = (Wiersz) obj;
        if (this.idwiersza==null&&other.idwiersza==null) {
           if (this.idporzadkowy!=other.idporzadkowy) {
               return false;
           }   
        } else {
            if (!Objects.equals(this.idwiersza, other.idwiersza)) {
                return false;
            }
        }
        if (!Objects.equals(this.dokfk, other.dokfk)) {
            return false;
        }
        return true;
    }

    

    
    
    
    
    public static void main(String[] args) {
//        Wiersz a = new Wiersz();
//        a.setIdporzadkowy(4);
//        Wiersz b = new Wiersz();
//        b.setIdporzadkowy(4);
//        if (a==b) {
//            error.E.s("pasuje ==");
//        } else 
//        if (a.equals(b)) {
//            error.E.s("pasuje equals");
//        } else {
//            error.E.s("rozne");
//        }
    }
    
    
    @Override
    public String toString() {
        try {
            String idwiersza = this.idwiersza==null?"brakid":String.valueOf(this.idwiersza);
            if (getStronaWn() == null && getStronaMa().getKonto() != null) {
                return "idwiersza=" + idwiersza + ", idporz.= " + idporzadkowy + "typ : " + typWiersza + " Wn: null, Ma: " + getStronaMa().getKwota() + " Ma:" + getStronaMa().getKonto().getPelnynumer() + " " + opisWiersza;
            } else if (getStronaMa() == null && getStronaWn().getKonto() != null) {
                return "idwiersza=" + idwiersza + ", idporz.= " + idporzadkowy + "typ : " + typWiersza + " Wn: " + getStronaWn().getKwota() + " Ma: null}" + " Wn:" + getStronaWn().getKonto().getPelnynumer() + " " + opisWiersza;
            } else if (idwiersza != null && getStronaWn().getKonto() != null && getStronaMa().getKonto() != null) {
                return "idwiersza=" + idwiersza + ", idporz.=" + idporzadkowy + "typ : " + typWiersza + " Wn: " + getStronaWn().getKwota() + " Ma: " + getStronaMa().getKwota() + " Wn:" + getStronaWn().getKonto().getPelnynumer() + " Ma: " + getStronaMa().getKonto().getPelnynumer() + " " + opisWiersza;
            } else if (getStronaWn().getKonto() != null && getStronaMa().getKonto() != null) {
                return "idwiersza= null, idporz.=" + idporzadkowy + "typ : " + typWiersza + " Wn: " + getStronaWn().getKwota() + " Ma: " + getStronaMa().getKwota() + " Wn:" + getStronaWn().getKonto().getPelnynumer() + " Ma: " + getStronaMa().getKonto().getPelnynumer() + " " + opisWiersza;
            } else if (getStronaWn() != null && getStronaMa() != null) {
                return "idwiersza= null, idporz.=" + idporzadkowy + "typ : " + typWiersza + " Wn: " + getStronaWn().getKwota() + " Ma: " + getStronaMa().getKwota() + " " + opisWiersza;
            } else {
                return "idwiersza= null, idporz.=" + idporzadkowy + "typ : " + typWiersza + " " + opisWiersza;
            }
        } catch (Exception e) {
            E.e(e);
            return "Wiersz toString() NullPointerException";
        }
    }

    public String tostring2() {
        if (getStronaWn() != null && getStronaMa() != null) {
            String wn = getStronaWn().getKonto() == null ? "brak konta Wn" : getStronaWn().getKonto().getPelnynumer();
            String ma = getStronaMa().getKonto() == null ? "brak konta Ma" : getStronaMa().getKonto().getPelnynumer();
            return "lpwiersza " + idporzadkowy + " opis " + opisWiersza + "konto Wn " + wn + "konto Ma " + ma;
        } else if (getStronaWn() != null) {
            String wn = getStronaWn().getKonto() == null ? "brak konta Wn" : getStronaWn().getKonto().getPelnynumer();
            return "lpwiersza " + idporzadkowy + " opis " + opisWiersza + "konto Wn " + wn;
        } else if (getStronaMa() != null) {
            String ma = getStronaMa().getKonto() == null ? "brak konta Ma" : getStronaMa().getKonto().getPelnynumer();
            return "lpwiersza " + idporzadkowy + " opis " + opisWiersza + "konto Ma " + ma;
        } else {
            return "lpwiersza " + idporzadkowy;
        }
    }

    public String getDokfkS() {
        return this.getDokfk().toString2();
    }

    public boolean jest0niejest1(WierszBO w, String mc) {
        if (w.getKonto().getPelnynumer().equals("407-2")) {
        }
        boolean jest0niejest1 = true;
        String opiswierszaBO = "zapis BO: " + w.getOpis();
        if (!mc.equals("01")) {
            opiswierszaBO = "kwota obrotów: " + w.getOpis();
        }
        if (this.getStronaWn() != null) {
            if (this.getStronaWn() != null && this.getStronaWn().getWierszbo() != null && this.getStronaWn().getWierszbo().equals(w)) {
                jest0niejest1 = false;
            } else if (this.getStronaWn().getKonto().equals(w.getKonto()) && this.getOpisWiersza().equals(opiswierszaBO) && this.getStronaWn().getWierszbo() == null) {
                jest0niejest1 = false;
            }
        }
        if (this.getStronaMa() != null) {
            if (this.getStronaMa() != null && this.getStronaMa().getWierszbo() != null && this.getStronaMa().getWierszbo().equals(w)) {
                jest0niejest1 = false;
            } else if (this.getStronaMa().getKonto().equals(w.getKonto()) && this.getOpisWiersza().equals(opiswierszaBO) && this.getStronaMa().getWierszbo() == null) {
                jest0niejest1 = false;
            }
        }
        return jest0niejest1;
    }

    public String getOpisWiersza(int ile) {
        String opis = this.opisWiersza;
        if (this.opisWiersza != null && this.opisWiersza.length() > 10) {
            if (this.opisWiersza.length() + 1 > ile) {
                opis = this.opisWiersza.substring(0, ile);
            }
        }
        return opis;
    }
    
    public int getWierszBOId() {
        int id = 0;
        if (this.getStronaWn() != null && this.getStronaWn().getWierszbo() != null) {
            id = this.getStronaWn().getWierszbo().getId();
        } else if (this.getStronaMa() != null && this.getStronaMa().getWierszbo() != null) {
            id = this.getStronaMa().getWierszbo().getId();
        }
        return id;
    }

    public double getKursWiersz() {
        double kurs = this.tabelanbp != null ? this.tabelanbp.getKurssredni() : 0;
        int wiersz = wierszbo();
        if (wiersz == 1) {
            kurs = this.getStronaWn().getKursBO();
        } else if (wiersz == 2) {
            kurs = this.getStronaMa().getKursBO();
        }
        return kurs;
    }

    public String getWalutaWiersz() {
        String waluta = this.tabelanbp != null ? this.tabelanbp.getWaluta().getSymbolwaluty() : "";
        int wiersz = wierszbo();
        if (wiersz == 1) {
            waluta = this.getStronaWn().getSymbolWalutBOiSW();
        } else if (wiersz == 2) {
            waluta = this.getStronaMa().getSymbolWalutBOiSW();
        }
        return waluta;
    }

    private int wierszbo() {
        int wiersbo = 0;
        if (this.getStronaWn() != null && this.getStronaWn().getTypStronaWiersza() == 9) {
            wiersbo = 1;
        } else if (this.getStronaMa() != null && this.getStronaMa().getTypStronaWiersza() == 9) {
            wiersbo = 2;
        }
        return wiersbo;
    }

    public Konto getKontoWn() {
        return this.getStronaWn() != null ? this.getStronaWn().getKonto()  : null;
    }
    public Konto getKontoMa() {
        return this.getStronaMa() != null ? this.getStronaMa().getKonto()  : null;
    }

    public boolean isWypelniony() {
        boolean zwrot = true;
        if (this.getStronaWn() != null) {
            if (Z.z(this.getStronaWn().getKwota())==0.0 || this.getStronaWn().getKonto()==null) {
                zwrot = false;
            }
        }
        if (this.getStronaMa() != null) {
            if (Z.z(this.getStronaMa().getKwota())==0.0 || this.getStronaMa().getKonto()==null) {
                zwrot = false;
            }
        }
        return zwrot;
    }
    
    public Wiersz getWiersznastepny() {
        Wiersz zwrot = null;
        if (this!=null) {
            List<Wiersz> wiersze = this.dokfk.getListawierszy();
            boolean pobrac = false;
            for (Wiersz w : wiersze) {
                if (pobrac && w.getLpmacierzystego()!=0) {
                    zwrot = w;
                    break;
                } else if (pobrac && w.getLpmacierzystego()==0) {
                    break;
                }
                if (w==this) {
                    pobrac = true;
                }
            }
        }
        return zwrot;
    }
    
    public String getCechy() {
        String zwrot = null;
        List<String> nazwy = new ArrayList<>();
        List<StronaWiersza> stronyWiersza = this.getStronyWiersza();
        List<Cechazapisu> cechy = new ArrayList<>();
        for (StronaWiersza s : stronyWiersza) {
            if (s.getCechazapisuLista()!=null&&s.getCechazapisuLista().size()>0) {
                cechy.addAll(s.getCechazapisuLista());
            }
        }
        if (cechy!=null&&cechy.size()>0) {
            for (Cechazapisu c : cechy) {
                nazwy.add(c.getNazwacechy());
            }
            zwrot = nazwy.toString();
        }
        return zwrot;
    }
}
