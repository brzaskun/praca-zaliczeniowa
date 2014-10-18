
package entityfk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Cacheable(false)
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
    @NamedQuery(name = "Wiersz.findByPodatnik", query = "SELECT w FROM Wiersz w WHERE w.dokfk.podatnikObj = :podatnik")
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
    
    public StronaWiersza getStronaWn() {
        return this.strona.get("Wn");
    }
    
    public StronaWiersza getStronaMa() {
        return this.strona.get("Ma");
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
        } else {
            return "idwiersza=" + idwiersza + ", idporz.=" + idporzadkowy + "typ : "+typWiersza+" Wn: "+ getStronaWn().getKwota() + " Ma: "+ getStronaMa().getKwota() + " Wn:"+getStronaWn().getKonto().getPelnynumer()+ " Ma: "+getStronaMa().getKonto().getPelnynumer()+'}';
        }
    }
    
    
}
