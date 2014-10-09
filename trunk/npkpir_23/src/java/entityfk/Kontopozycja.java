/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ukladBR, konto")
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kontopozycja.findAll", query = "SELECT k FROM Kontopozycja k"),
    @NamedQuery(name = "Kontopozycja.findByPozycjaWn", query = "SELECT k FROM Kontopozycja k WHERE k.pozycjaWn = :pozycjaWn"),
    @NamedQuery(name = "Kontopozycja.findByPozycjaMa", query = "SELECT k FROM Kontopozycja k WHERE k.pozycjaMa = :pozycjaMa"),
    @NamedQuery(name = "Kontopozycja.findByPodatnik", query = "SELECT k FROM Kontopozycja k WHERE k.ukladBR = :podatnik"),
    @NamedQuery(name = "Kontopozycja.findByUklad", query = "SELECT k FROM Kontopozycja k WHERE k.ukladBR = :uklad"),
    @NamedQuery(name = "Kontopozycja.findByKontoId", query = "SELECT k FROM Kontopozycja k WHERE k.kontoID.id = :kontoId"),
    @NamedQuery(name = "Kontopozycja.findByRok", query = "SELECT k FROM Kontopozycja k WHERE k.ukladBR.rok = :rok")})
public class Kontopozycja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKP", nullable = false)
    private Integer idKP;
    @Size(max = 255)
    @Column(length = 255, name = "pozycjaWn")
    private String pozycjaWn;
    @Column(length = 10, name = "stronaWn")
    private String stronaWn;
    @Size(max = 255)
    @Column(length = 255, name = "pozycjaMa")
    private String pozycjaMa;
    @Column(length = 10, name = "stronaMa")
    private String stronaMa;
    @Column(name = "pozycjonowane")
    private boolean pozycjonowane;
    @JoinColumn(name = "ukladBR", referencedColumnName = "lp")
    private UkladBR ukladBR;
    @OneToOne
    @JoinColumn(name = "kontoID", referencedColumnName = "id")
    private Konto kontoID;

    public Kontopozycja() {
    }

    public Integer getIdKP() {
        return idKP;
    }

    public void setIdKP(Integer idKP) {
        this.idKP = idKP;
    }

    public String getPozycjaWn() {
        return pozycjaWn;
    }

    public void setPozycjaWn(String pozycjaWn) {
        this.pozycjaWn = pozycjaWn;
    }

    public String getPozycjaMa() {
        return pozycjaMa;
    }

    public void setPozycjaMa(String pozycjaMa) {
        this.pozycjaMa = pozycjaMa;
    }

    public boolean isPozycjonowane() {
        return pozycjonowane;
    }

    public void setPozycjonowane(boolean pozycjonowane) {
        this.pozycjonowane = pozycjonowane;
    }

   
    public UkladBR getUkladBR() {
        return ukladBR;
    }

    public void setUkladBR(UkladBR ukladBR) {
        this.ukladBR = ukladBR;
    }

    public Konto getKontoID() {
        return kontoID;
    }

    public void setKontoID(Konto kontoID) {
        this.kontoID = kontoID;
    }

   

    public String getStronaWn() {
        return stronaWn;
    }

    public void setStronaWn(String stronaWn) {
        this.stronaWn = stronaWn;
    }

    public String getStronaMa() {
        return stronaMa;
    }

    public void setStronaMa(String stronaMa) {
        this.stronaMa = stronaMa;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.ukladBR);
        hash = 41 * hash + Objects.hashCode(this.kontoID);
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
        final Kontopozycja other = (Kontopozycja) obj;
        if (!Objects.equals(this.ukladBR, other.ukladBR)) {
            return false;
        }
        if (!Objects.equals(this.kontoID, other.kontoID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kontopozycja{" + "id=" + idKP + ", pozycjaWn=" + pozycjaWn + ", pozycjaMa=" + pozycjaMa + ", pozycjonowane=" + pozycjonowane + ", ukladBR=" + ukladBR + ", konto=" + kontoID + '}';
    }
    
    
    
    

   
    
}
