
package entityfk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "wiersz")
//@Table(name = "wiersz", uniqueConstraints = {
//    @UniqueConstraint(columnNames = "idporzadkowy, nrkolejnywserii, rok, podatnik, seriadokfk")
//})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wiersz.findAll", query = "SELECT w FROM Wiersz w"),
    @NamedQuery(name = "Wiersz.findByDataksiegowania", query = "SELECT w FROM Wiersz w WHERE w.dataksiegowania = :dataksiegowania"),
    @NamedQuery(name = "Wiersz.findByIdwiersza", query = "SELECT w FROM Wiersz w WHERE w.idwiersza = :idwiersza"),
    @NamedQuery(name = "Wiersz.findByOpisWiersza", query = "SELECT w FROM Wiersz w WHERE w.opisWiersza = :opisWiersza"),
    @NamedQuery(name = "Wiersz.findByPodatnik", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik"),
    @NamedQuery(name = "Wiersz.findByPodatnikMcRok", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik AND w.dokfk.dokfkPK.rok = :rok AND w.dokfk.miesiac = :mc"),
    @NamedQuery(name = "Wiersz.findByPodatnikMcRokWNTWDT", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik AND w.dokfk.dokfkPK.rok = :rok AND w.dokfk.miesiac = :mc AND w.dokfk.dokfkPK.seriadokfk = :wntwdt"),
    @NamedQuery(name = "Wiersz.findByPodatnikRok", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik AND w.dokfk.dokfkPK.rok = :rok")
})
public class Wiersz implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idwiersza", nullable = false)
    //to jest id generowany przez serwer
    private Integer idwiersza;
    @Column(name="idporzadkowy")
    //to jest numer nadawany kazdorazowo od 1 dla numerowania wewnatrz dokumentu
    private Integer idporzadkowy;
    @Size(max = 255)
    @Column(name = "dataksiegowania", length = 255)
    private String dataksiegowania;
    @Size(max = 255)
    @Column(name = "opisWiersza", length = 255)
    private String opisWiersza;
    @Column(name = "ilosc_kg")
    private double ilosc_kg;
    @Column(name = "ilosc_szt")
    private double ilosc_szt;
    @Column(name = "typWiersza")
    private Integer typWiersza;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private Dokfk dokfk;
    //NIE USUWAĆ!!! to jest potrzebne do rapotow walutowych i wyciagow walutowych, chodzi o wprowadzenie daty przez użytkownika
    @Column(name = "dataWalutyWiersza")
    private String dataWalutyWiersza;
    @ManyToOne(fetch = FetchType.EAGER)
    private Tabelanbp tabelanbp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wiersz", orphanRemoval = true, fetch = FetchType.EAGER)
    @MapKeyColumn(name="strona_key")
    private Map<String, StronaWiersza> strona;
    @Column(name="lpmacierzystego")
    private Integer lpmacierzystego;
    @ManyToOne
    private Wiersz czworka;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "czworka", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Wiersz> piatki;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "wiersz", orphanRemoval = true, fetch = FetchType.EAGER)
    private EVatwpisFK eVatwpisFK;
    @Column(name = "saldoWBRK")
    private double saldoWBRK;

  
    

    public Wiersz() {
        this.strona = new HashMap<>();
        this.piatki = new HashSet<>();
    }
       
    
    //trzeba wstawiac numer porzadkowy dla celow funkcji javascript ktore odpowiednio obrabiaja wiersze w trakcie wprowadzania
    public Wiersz(int idporzadkowy, int typwiersza) {
        this.strona = new HashMap<>();
        this.idporzadkowy = idporzadkowy;
        this.typWiersza = typwiersza;
        this.piatki = new HashSet<>();
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public EVatwpisFK geteVatwpisFK() {
        return eVatwpisFK;
    }

    public void seteVatwpisFK(EVatwpisFK eVatwpisFK) {
        this.eVatwpisFK = eVatwpisFK;
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

    public void setStronaWn(StronaWiersza stronaWiersza) {
        this.strona.put("Wn", stronaWiersza);
    }
    
    public void setStronaMa(StronaWiersza stronaWiersza) {
        this.strona.put("Ma", stronaWiersza);
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
    
    public StronaWiersza getStronaWn() {
        try {
            return this.strona.get("Wn");
        } catch (Exception e) {
            return null;
        }
    }
    
    public StronaWiersza getStronaMa() {
        try {
            return this.strona.get("Ma");
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<StronaWiersza> getStronyWiersza() {
        List<StronaWiersza> strony = new ArrayList<>();
        if (this.strona.get("Wn") != null) {
            strony.add(this.strona.get("Wn"));
        }
        if (this.strona.get("Ma") != null) {
            strony.add(this.strona.get("Ma"));
        }
        return strony;
    }
    
    public List<StronaWiersza> getStronyWierszaKonto() {
        List<StronaWiersza> strony = new ArrayList<>();
        if (this.strona.get("Wn") != null && this.strona.get("Wn").getKonto() != null) {
            strony.add(this.strona.get("Wn"));
        }
        if (this.strona.get("Ma") != null && this.strona.get("Ma").getKonto() != null) {
            strony.add(this.strona.get("Ma"));
        }
        return strony;
    }

    public Map<String, StronaWiersza> getStrona() {
        return strona;
    }
    
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

    public Tabelanbp getTabelanbp() {
        return tabelanbp;
    }

    public void setTabelanbp(Tabelanbp tabelanbp) {
        this.tabelanbp = tabelanbp;
    }

   
       
    
    //</editor-fold>
  
   
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idwiersza != null ? idwiersza.hashCode() : 0);
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
        final Wiersz other = (Wiersz) obj;
        if (!Objects.equals(this.idporzadkowy, other.idporzadkowy)) {
            return false;
        }
        if (!Objects.equals(this.dokfk, other.dokfk)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (getStronaWn() == null) {
            return "idwiersza=" + idwiersza + ", idporz.= " + idporzadkowy + "typ : "+typWiersza + " Wn: null, Ma: "+ getStronaMa().getKwota()+ " Ma:"+getStronaMa().getKonto().getPelnynumer() + '}';
        } else if (getStronaMa() == null) {
            return "idwiersza=" + idwiersza + ", idporz.= " + idporzadkowy + "typ : "+typWiersza + " Wn: "+ getStronaWn().getKwota() + " Ma: null}"+ " Wn:"+getStronaWn().getKonto().getPelnynumer();
        } else if (idwiersza != null) {
            return "idwiersza=" + idwiersza + ", idporz.=" + idporzadkowy + "typ : "+typWiersza+" Wn: "+ getStronaWn().getKwota() + " Ma: "+ getStronaMa().getKwota() + " Wn:"+getStronaWn().getKonto().getPelnynumer()+ " Ma: "+getStronaMa().getKonto().getPelnynumer()+'}';
        } else {
            return "idwiersza= null, idporz.=" + idporzadkowy + "typ : "+typWiersza+" Wn: "+ getStronaWn().getKwota() + " Ma: "+ getStronaMa().getKwota() + " Wn:"+getStronaWn().getKonto().getPelnynumer()+ " Ma: "+getStronaMa().getKonto().getPelnynumer()+'}';
        }
    }
    
    public String tostring2() {
        if (getStronaWn() != null && getStronaMa() != null) {
            String wn = getStronaWn().getKonto() == null ? "brak konta Wn" : getStronaWn().getKonto().getPelnynumer();
            String ma = getStronaMa().getKonto() == null ? "brak konta Ma" : getStronaMa().getKonto().getPelnynumer();
            return "lpwiersza "+idporzadkowy+" opis "+opisWiersza + "konto Wn "+ wn + "konto Ma " + ma;
        } else if (getStronaWn() != null) {
            String wn = getStronaWn().getKonto() == null ? "brak konta Wn" : getStronaWn().getKonto().getPelnynumer();
            return "lpwiersza "+idporzadkowy+" opis "+opisWiersza + "konto Wn "+wn;
        } else if (getStronaMa() != null) {
            String ma = getStronaMa().getKonto() == null ? "brak konta Ma" : getStronaMa().getKonto().getPelnynumer();
            return "lpwiersza "+idporzadkowy+" opis "+opisWiersza + "konto Ma "+ma;
        } else {
            return "lpwiersza "+idporzadkowy;
        }
    }
    
    public String getDokfkS() {
        return this.getDokfk().getDokfkPK().toString2();
    }
    
}
