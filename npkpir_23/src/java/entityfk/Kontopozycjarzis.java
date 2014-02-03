/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Kontopozycjarzis.findByLp", query = "SELECT k FROM Kontopozycjarzis k WHERE k.lp = :lp"),
    @NamedQuery(name = "Kontopozycjarzis.findByPozycjaString", query = "SELECT k FROM Kontopozycjarzis k WHERE k.pozycjaString = :pozycjaString")
   })
public class Kontopozycjarzis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer lp;
    @Size(max = 255)
    @Column(length = 255)
    private String pozycjaString;
    private Rzisuklad uklad;
    private Konto konto;

    public Kontopozycjarzis() {
    }

    public Kontopozycjarzis(Integer lp) {
        this.lp = lp;
    }

    public Integer getLp() {
        return lp;
    }

    public void setLp(Integer lp) {
        this.lp = lp;
    }

    public String getPozycjaString() {
        return pozycjaString;
    }

    public void setPozycjaString(String pozycjaString) {
        this.pozycjaString = pozycjaString;
    }

    public Rzisuklad getUklad() {
        return uklad;
    }

    public void setUklad(Rzisuklad uklad) {
        this.uklad = uklad;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lp != null ? lp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kontopozycjarzis)) {
            return false;
        }
        Kontopozycjarzis other = (Kontopozycjarzis) object;
        if ((this.lp == null && other.lp != null) || (this.lp != null && !this.lp.equals(other.lp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Kontopozycjarzis[ lp=" + lp + " ]";
    }
    
}
