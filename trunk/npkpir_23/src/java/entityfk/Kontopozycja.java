/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kontopozycjarzis.findAll", query = "SELECT k FROM Kontopozycjarzis k"),
    @NamedQuery(name = "Kontopozycjarzis.findByPozycjastring", query = "SELECT k FROM Kontopozycjarzis k WHERE k.pozycjastring = :pozycjastring"),
    @NamedQuery(name = "Kontopozycjarzis.findByPodatnik", query = "SELECT k FROM Kontopozycjarzis k WHERE k.kontopozycjarzisPK.podatnik = :podatnik"),
    @NamedQuery(name = "Kontopozycjarzis.findByPodatnikRokUklad", query = "SELECT k FROM Kontopozycjarzis k WHERE k.kontopozycjarzisPK.podatnik = :podatnik AND k.kontopozycjarzisPK.rok = :rok AND k.kontopozycjarzisPK.uklad = :uklad"),
    @NamedQuery(name = "Kontopozycjarzis.findByUklad", query = "SELECT k FROM Kontopozycjarzis k WHERE k.kontopozycjarzisPK.uklad = :uklad"),
    @NamedQuery(name = "Kontopozycjarzis.findByKontoId", query = "SELECT k FROM Kontopozycjarzis k WHERE k.kontopozycjarzisPK.kontoId = :kontoId"),
    @NamedQuery(name = "Kontopozycjarzis.findByRok", query = "SELECT k FROM Kontopozycjarzis k WHERE k.kontopozycjarzisPK.rok = :rok")})
public class Kontopozycja implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KontopozycjaPK kontopozycjarzisPK;
    @Size(max = 255)
    @Column(length = 255, name = "pozycjastring")
    private String pozycjastring;
    @Column(name = "pozycjonowane")
    private boolean pozycjonowane;

    public Kontopozycja() {
    }

    public Kontopozycja(KontopozycjaPK kontopozycjarzisPK) {
        this.kontopozycjarzisPK = kontopozycjarzisPK;
    }

    public Kontopozycja(String podatnik, String uklad, int kontoId, String rok) {
        this.kontopozycjarzisPK = new KontopozycjaPK(podatnik, uklad, kontoId, rok);
    }

    public KontopozycjaPK getKontopozycjarzisPK() {
        return kontopozycjarzisPK;
    }

    public void setKontopozycjarzisPK(KontopozycjaPK kontopozycjarzisPK) {
        this.kontopozycjarzisPK = kontopozycjarzisPK;
    }

    public String getPozycjastring() {
        return pozycjastring;
    }

    public void setPozycjastring(String pozycjastring) {
        this.pozycjastring = pozycjastring;
    }

    public boolean isPozycjonowane() {
        return pozycjonowane;
    }

    public void setPozycjonowane(boolean pozycjonowane) {
        this.pozycjonowane = pozycjonowane;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kontopozycjarzisPK != null ? kontopozycjarzisPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kontopozycja)) {
            return false;
        }
        Kontopozycja other = (Kontopozycja) object;
        if ((this.kontopozycjarzisPK == null && other.kontopozycjarzisPK != null) || (this.kontopozycjarzisPK != null && !this.kontopozycjarzisPK.equals(other.kontopozycjarzisPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Kontopozycjarzis[ kontopozycjarzisPK=" + kontopozycjarzisPK + " ]";
    }
    
}
