/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Okresrozliczeniowy;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zamknietemiesiace")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zamknietemiesiace.findAll", query = "SELECT z FROM Zamknietemiesiace z"),
    @NamedQuery(name = "Zamknietemiesiace.findByPodatnik", query = "SELECT z FROM Zamknietemiesiace z WHERE z.podatnik = :podatnik")})
public class Zamknietemiesiace implements Serializable {
    private static final long serialVersionUID = 1L;
    @Lob
    @Column(name = "zamkniete")
    private List<Okresrozliczeniowy> zamkniete;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "podatnik")
    private String podatnik;

    public Zamknietemiesiace() {
    }

    public Zamknietemiesiace(String podatnik) {
        this.podatnik = podatnik;
    }
    
   
    public List<Okresrozliczeniowy> getZamkniete() {
        return zamkniete;
    }

    public void setZamkniete(List<Okresrozliczeniowy> zamkniete) {
        this.zamkniete = zamkniete;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (podatnik != null ? podatnik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zamknietemiesiace)) {
            return false;
        }
        Zamknietemiesiace other = (Zamknietemiesiace) object;
        if ((this.podatnik == null && other.podatnik != null) || (this.podatnik != null && !this.podatnik.equals(other.podatnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zamknietemiesiace[ podatnik=" + podatnik + " ]";
    }
    
}
