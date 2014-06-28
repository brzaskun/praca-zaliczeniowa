
package entityfk;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "wiersze")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wiersze.findAll", query = "SELECT w FROM Wiersze w"),
    @NamedQuery(name = "Wiersze.findByDataksiegowania", query = "SELECT w FROM Wiersze w WHERE w.dataksiegowania = :dataksiegowania"),
    @NamedQuery(name = "Wiersze.findByIdwiersza", query = "SELECT w FROM Wiersze w WHERE w.idwiersza = :idwiersza"),
    @NamedQuery(name = "Wiersze.findByOpisWiersza", query = "SELECT w FROM Wiersze w WHERE w.opisWiersza = :opisWiersza"),
    @NamedQuery(name = "Wiersze.findByPodatnik", query = "SELECT w FROM Wiersze w WHERE w.dokfk.dokfkPK.podatnik = :podatnik")
})

public class Wiersze implements Serializable {
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
    @Column(name = "WnReadOnly")
    private boolean WnReadOnly;
    @Column(name = "MaReadOnly")
    private boolean MaReadOnly;
    @Size(max = 255)
    @Column(name = "opisWiersza", length = 255)
    private String opisWiersza;
    @Column(name = "typWiersza")
    private Integer typWiersza;
    @Column(name = "zaksiegowane")
    private Boolean zaksiegowane;
    @ManyToOne(optional = false)
    private Dokfk dokfk;
    @OneToMany(mappedBy = "wiersz", cascade = CascadeType.ALL, targetEntity = Kontozapisy.class,  orphanRemoval=true)
    private List<Kontozapisy> zapisynakontach;
    //to jest potrzebne do rapotow walutowych i wyciagow walutowych
    @Column(name = "dataWalutyWiersza")
    private String dataWalutyWiersza;
    @JoinColumns({
        @JoinColumn(name = "nrtabeli", referencedColumnName = "nrtabeli"),
        @JoinColumn(name = "symbolwaluty", referencedColumnName = "symbolwaluty")
    })
    @ManyToOne
    private Tabelanbp tabelanbp;
    @OneToOne(mappedBy = "wiersz", cascade = CascadeType.ALL, targetEntity = WierszStronafk.class,  orphanRemoval=true)
    private Rozrachunekfk rozrachunekfkWn;
    @OneToOne(mappedBy = "wiersz", cascade = CascadeType.ALL, targetEntity = WierszStronafk.class,  orphanRemoval=true)
    private Rozrachunekfk rozrachunekfkMa;
    @JoinColumn(name = "kontoWn", referencedColumnName = "id")
    @ManyToOne
    private Konto kontoWn;
    @JoinColumn(name = "kontoMa", referencedColumnName = "id")
    @ManyToOne
    private Konto kontoMa;
    @Column(name = "kwotaWn")
    private double kwotaWn;
    @Column(name = "kwotaPLNWn")
    private double kwotaPLNWn;
    @Column(name = "kwotaWalutaWn")
    private double kwotaWalutaWn;
    @Column(name = "kwotaMa")
    private double kwotaMa;
    @Column(name = "kwotaPLNMa")
    private double kwotaPLNMa;
    @Column(name = "kwotaWalutaMa")
    private double kwotaWalutaMa;        
        
   
    

    public Wiersze() {
    }
    
    //trzeba wstawiac numer porzadkowy dla celow funkcji javascript ktore odpowiednio obrabiaja wiersze w trakcie wprowadzania
    public Wiersze(int idporzadkowy, int typwiersza) {
        this.idporzadkowy = idporzadkowy;
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getDataksiegowania() {
        return dataksiegowania;
    }
    
    public void setDataksiegowania(String dataksiegowania) {
        this.dataksiegowania = dataksiegowania;
    }

    public Rozrachunekfk getRozrachunekfkWn() {
        return rozrachunekfkWn;
    }

    public void setRozrachunekfkWn(Rozrachunekfk rozrachunekfkWn) {
        this.rozrachunekfkWn = rozrachunekfkWn;
    }

    public Rozrachunekfk getRozrachunekfkMa() {
        return rozrachunekfkMa;
    }

    public void setRozrachunekfkMa(Rozrachunekfk rozrachunekfkMa) {
        this.rozrachunekfkMa = rozrachunekfkMa;
    }

    public double getKwotaWn() {
        return kwotaWn;
    }

    public void setKwotaWn(double kwotaWn) {
        this.kwotaWn = kwotaWn;
    }

    public double getKwotaPLNWn() {
        return kwotaPLNWn;
    }

    public void setKwotaPLNWn(double kwotaPLNWn) {
        this.kwotaPLNWn = kwotaPLNWn;
    }

    public double getKwotaWalutaWn() {
        return kwotaWalutaWn;
    }

    public void setKwotaWalutaWn(double kwotaWalutaWn) {
        this.kwotaWalutaWn = kwotaWalutaWn;
    }

    public double getKwotaMa() {
        return kwotaMa;
    }

    public void setKwotaMa(double kwotaMa) {
        this.kwotaMa = kwotaMa;
    }

    public double getKwotaPLNMa() {
        return kwotaPLNMa;
    }

    public void setKwotaPLNMa(double kwotaPLNMa) {
        this.kwotaPLNMa = kwotaPLNMa;
    }

    public double getKwotaWalutaMa() {
        return kwotaWalutaMa;
    }

    public void setKwotaWalutaMa(double kwotaWalutaMa) {
        this.kwotaWalutaMa = kwotaWalutaMa;
    }
    
    
    public List<Kontozapisy> getZapisynakontach() {
        return zapisynakontach;
    }

    public void setZapisynakontach(List<Kontozapisy> zapisynakontach) {
        this.zapisynakontach = zapisynakontach;
    }

    public Konto getKontoWn() {
        return kontoWn;
    }

    public void setKontoWn(Konto kontoWn) {
        this.kontoWn = kontoWn;
    }

    public Konto getKontoMa() {
        return kontoMa;
    }

    public void setKontoMa(Konto kontoMa) {
        this.kontoMa = kontoMa;
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
    
    public Boolean getZaksiegowane() {
        return zaksiegowane;
    }
    
    public void setZaksiegowane(Boolean zaksiegowane) {
        this.zaksiegowane = zaksiegowane;
    }
  
    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }

    public boolean isWnReadOnly() {
        return WnReadOnly;
    }

    public void setWnReadOnly(boolean WnReadOnly) {
        this.WnReadOnly = WnReadOnly;
    }

    public boolean isMaReadOnly() {
        return MaReadOnly;
    }

    public void setMaReadOnly(boolean MaReadOnly) {
        this.MaReadOnly = MaReadOnly;
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
        final Wiersze other = (Wiersze) obj;
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
