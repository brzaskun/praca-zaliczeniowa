
package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
//    @NamedQuery(name = "Wiersze.findByIdwierszarozliczenia", query = "SELECT w FROM Wiersze w WHERE w.idwierszarozliczenia = :idwierszarozliczenia"),
    @NamedQuery(name = "Wiersze.findByKontonumer", query = "SELECT w FROM Wiersze w WHERE w.kontonumer = :kontonumer"),
    @NamedQuery(name = "Wiersze.findByKontoprzeciwstawne", query = "SELECT w FROM Wiersze w WHERE w.kontoprzeciwstawne = :kontoprzeciwstawne"),
    @NamedQuery(name = "Wiersze.findByKwotaMa", query = "SELECT w FROM Wiersze w WHERE w.kwotaMa = :kwotaMa"),
    @NamedQuery(name = "Wiersze.findByKwotaWn", query = "SELECT w FROM Wiersze w WHERE w.kwotaWn = :kwotaWn"),
//    @NamedQuery(name = "Wiersze.findByKwotapierwotna", query = "SELECT w FROM Wiersze w WHERE w.kwotapierwotna = :kwotapierwotna"),
    @NamedQuery(name = "Wiersze.findByOpis", query = "SELECT w FROM Wiersze w WHERE w.opis = :opis"),
//    @NamedQuery(name = "Wiersze.findByPozostalodorozliczenia", query = "SELECT w FROM Wiersze w WHERE w.pozostalodorozliczenia = :pozostalodorozliczenia"),
//    @NamedQuery(name = "Wiersze.findByRozliczono", query = "SELECT w FROM Wiersze w WHERE w.rozliczono = :rozliczono"),
    @NamedQuery(name = "Wiersze.findByTypwiersza", query = "SELECT w FROM Wiersze w WHERE w.typwiersza = :typwiersza"),
    @NamedQuery(name = "Wiersze.findByZaksiegowane", query = "SELECT w FROM Wiersze w WHERE w.zaksiegowane = :zaksiegowane"),
    @NamedQuery(name = "Wiersze.findByKonto", query = "SELECT w FROM Wiersze w WHERE w.konto = :konto"),
    @NamedQuery(name = "Wiersze.findByKontoMa", query = "SELECT w FROM Wiersze w WHERE w.kontoMa = :kontoMa"),
    @NamedQuery(name = "Wiersze.findByKontoWn", query = "SELECT w FROM Wiersze w WHERE w.kontoWn = :kontoWn")})

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
   
    @Size(max = 255)
    @Column(name = "kontonumer", length = 255)
    private String kontonumer;
    @Size(max = 255)
    @Column(name = "kontoprzeciwstawne", length = 255)
    private String kontoprzeciwstawne;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwotaMa", precision = 22)
    private Double kwotaMa;
    @Column(name = "kwotaWn", precision = 22)
    private Double kwotaWn;
    @Size(max = 255)
    @Column(name = "opis", length = 255)
    private String opis;
    @Column(name = "typwiersza")
    private Integer typwiersza;
    @Column(name = "zaksiegowane")
    private Boolean zaksiegowane;
    @Lob
    @Column(name = "konto")
    @OneToOne
    private Konto konto;
    @Lob
    @Column(name = "kontoMa")
    @OneToOne
    private Konto kontoMa;
    @Lob
    @Column(name = "kontoWn")
    @OneToOne
    private Konto kontoWn;
    
//    //pola niezbedne do prowadzenia rozrachunków
//    @Column(name = "idwierszarozliczenia")
//    private Integer idwierszarozliczenia;
    @Column(name = "kwotapierwotna", precision = 22)
    private Double kwotapierwotna;
//    @Column(name = "rozliczono", precision = 22)
//    private Double rozliczono;
//    @Column(name = "pozostalodorozliczenia", precision = 22)
//    private Double pozostalodorozliczenia;
    @ManyToOne(optional = false)
    private Dokfk dokfk;
   
    

    public Wiersze() {
    }
    
    //trzeba wstawiac numer porzadkowy dla celow funkcji javascript ktore odpowiednio obrabiaja wiersze w trakcie wprowadzania
    public Wiersze(int idporzadkowy, int typwiersza) {
        this.idporzadkowy = idporzadkowy;
        this.typwiersza = typwiersza;
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
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
            
     
    public String getKontonumer() {
        return kontonumer;
    }
    
    public void setKontonumer(String kontonumer) {
        this.kontonumer = kontonumer;
    }
    
    public String getKontoprzeciwstawne() {
        return kontoprzeciwstawne;
    }
    
    public void setKontoprzeciwstawne(String kontoprzeciwstawne) {
        this.kontoprzeciwstawne = kontoprzeciwstawne;
    }
    
    public Double getKwotaMa() {
        return kwotaMa;
    }
    
    public void setKwotaMa(Double kwotaMa) {
        this.kwotaMa = kwotaMa;
    }
    
    public Double getKwotaWn() {
        return kwotaWn;
    }
    
    public void setKwotaWn(Double kwotaWn) {
        this.kwotaWn = kwotaWn;
    }
     
    public String getOpis() {
        return opis;
    }
    
    public void setOpis(String opis) {
        this.opis = opis;
    }
    
        
    public Integer getTypwiersza() {
        return typwiersza;
    }
    
    public void setTypwiersza(Integer typwiersza) {
        this.typwiersza = typwiersza;
    }
    
    public Boolean getZaksiegowane() {
        return zaksiegowane;
    }
    
    public void setZaksiegowane(Boolean zaksiegowane) {
        this.zaksiegowane = zaksiegowane;
    }
    
    public Konto getKonto() {
        return konto;
    }
    
    public void setKonto(Konto konto) {
        this.konto = konto;
    }
    
    public Konto getKontoMa() {
        return kontoMa;
    }
    
    public void setKontoMa(Konto kontoMa) {
        this.kontoMa = kontoMa;
    }
    
    public Konto getKontoWn() {
        return kontoWn;
    }
    
    public void setKontoWn(Konto kontoWn) {
        this.kontoWn = kontoWn;
    }

    public Double getKwotapierwotna() {
        return kwotapierwotna;
    }

    public void setKwotapierwotna(Double kwotapierwotna) {
        this.kwotapierwotna = kwotapierwotna;
    }

    
    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }

        
    
    //</editor-fold>
  
   
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idwiersza != null ? idwiersza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wiersze)) {
            return false;
        }
        Wiersze other = (Wiersze) object;
        if ((this.idwiersza == null && other.idwiersza != null) || (this.idwiersza != null && !this.idwiersza.equals(other.idwiersza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Wiersze[ idwiersza=" + idwiersza + " ]";
    }
    
}
