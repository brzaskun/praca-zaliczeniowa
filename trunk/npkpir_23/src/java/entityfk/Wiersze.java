
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
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Wiersze.findByKwotaMa", query = "SELECT w FROM Wiersze w WHERE w.kwotaMa = :kwotaMa"),
    @NamedQuery(name = "Wiersze.findByKwotaWn", query = "SELECT w FROM Wiersze w WHERE w.kwotaWn = :kwotaWn"),
    @NamedQuery(name = "Wiersze.findByOpis", query = "SELECT w FROM Wiersze w WHERE w.opis = :opis"),
    @NamedQuery(name = "Wiersze.findByKontoMa", query = "SELECT w FROM Wiersze w WHERE w.kontoMa = :kontoMa"),
    @NamedQuery(name = "Wiersze.findByKontoWn", query = "SELECT w FROM Wiersze w WHERE w.kontoWn = :kontoWn"),
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
    @Column(name = "kontoMa")
    @OneToOne
    private Konto kontoMa;
    @Lob
    @Column(name = "kontoWn")
    @OneToOne
    private Konto kontoWn;
    @ManyToOne(optional = false)
    private Dokfk dokfk;
    @OneToMany(mappedBy = "wiersz", cascade = CascadeType.ALL, targetEntity = Kontozapisy.class,  orphanRemoval=true)
    private List<Kontozapisy> zapisynakontach;
   
    

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
