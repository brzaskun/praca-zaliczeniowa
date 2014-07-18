
package entityfk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Cacheable(false)
@Entity
@Table(name = "wiersz", uniqueConstraints = {
    @UniqueConstraint(columnNames = "idporzadkowy, nrkolejnywserii, rok, podatnik, seriadokfk")
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wiersz.findAll", query = "SELECT w FROM Wiersz w"),
    @NamedQuery(name = "Wiersz.findByDataksiegowania", query = "SELECT w FROM Wiersz w WHERE w.dataksiegowania = :dataksiegowania"),
    @NamedQuery(name = "Wiersz.findByIdwiersza", query = "SELECT w FROM Wiersz w WHERE w.idwiersza = :idwiersza"),
    @NamedQuery(name = "Wiersz.findByOpisWiersza", query = "SELECT w FROM Wiersz w WHERE w.opisWiersza = :opisWiersza"),
    @NamedQuery(name = "Wiersz.findByPodatnik", query = "SELECT w FROM Wiersz w WHERE w.dokfk.dokfkPK.podatnik = :podatnik")
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
    @Column(name = "typWiersza")
    private Integer typWiersza;
    @ManyToOne
    private Dokfk dokfk;
    @OneToMany(mappedBy = "wiersz", cascade = CascadeType.ALL, targetEntity = Kontozapisy.class,  orphanRemoval=true)
    private List<Kontozapisy> zapisynakontach;
    //to jest potrzebne do rapotow walutowych i wyciagow walutowych, chodzi o wprowadzenie daty przez użytkownika
    @Column(name = "dataWalutyWiersza")
    private String dataWalutyWiersza;
    @ManyToOne
    private Tabelanbp tabelanbp;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "wiersz", orphanRemoval = true)
    private StronaWn stronaWn;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "wiersz", orphanRemoval = true)
    private StronaMa stronaMa;
  
    

    public Wiersz() {
        this.zapisynakontach = new ArrayList<>();
    }
    
    //trzeba wstawiac numer porzadkowy dla celow funkcji javascript ktore odpowiednio obrabiaja wiersze w trakcie wprowadzania
    public Wiersz(int idporzadkowy, int typwiersza) {
        this.zapisynakontach = new ArrayList<>();
        this.idporzadkowy = idporzadkowy;
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public String getDataksiegowania() {
        return dataksiegowania;
    }
    
    public void setDataksiegowania(String dataksiegowania) {
        this.dataksiegowania = dataksiegowania;
    }
   
    public List<Kontozapisy> getZapisynakontach() {
        return zapisynakontach;
    }

    public void setZapisynakontach(List<Kontozapisy> zapisynakontach) {
        this.zapisynakontach = zapisynakontach;
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

    public String getDataWalutyWiersza() {
        return dataWalutyWiersza;
    }

    public void setDataWalutyWiersza(String dataWalutyWiersza) {
        this.dataWalutyWiersza = dataWalutyWiersza;
    }

    public StronaWn getStronaWn() {
        return stronaWn;
    }

    public void setStronaWn(StronaWn stronaWn) {
        this.stronaWn = stronaWn;
    }

    public StronaMa getStronaMa() {
        return stronaMa;
    }

    public void setStronaMa(StronaMa stronaMa) {
        this.stronaMa = stronaMa;
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
        return "Wiersze{" + "idwiersza=" + idwiersza + ", idporzadkowy=" + idporzadkowy + ", dokfk=" + dokfk + '}';
    }


    
    
}
