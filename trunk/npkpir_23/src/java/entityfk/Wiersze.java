
package entityfk;

import embeddablefk.WierszStronafk;
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
    @NamedQuery(name = "Wiersze.findByOpis", query = "SELECT w FROM Wiersze w WHERE w.opis = :opis"),
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
    @Lob
    @Column(name = "wierszStronaWn")
    private WierszStronafk wierszStronaWn;
    @Lob
    @Column(name = "wierszStronaMa")
    private WierszStronafk wierszStronaMa;
    @Size(max = 255)
    @Column(name = "opis", length = 255)
    private String opis;
    @Column(name = "typwiersza")
    private Integer typwiersza;
    @Column(name = "zaksiegowane")
    private Boolean zaksiegowane;
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

    public WierszStronafk getWierszStronaWn() {
        return wierszStronaWn;
    }

    public void setWierszStronaWn(WierszStronafk wierszStronaWn) {
        this.wierszStronaWn = wierszStronaWn;
    }

    public WierszStronafk getWierszStronaMa() {
        return wierszStronaMa;
    }

    public void setWierszStronaMa(WierszStronafk wierszStronaMa) {
        this.wierszStronaMa = wierszStronaMa;
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
