/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rzisuklad.findAll", query = "SELECT r FROM Rzisuklad r"),
    @NamedQuery(name = "Rzisuklad.findByUklad", query = "SELECT r FROM Rzisuklad r WHERE r.rzisukladPK.uklad = :uklad"),
    @NamedQuery(name = "Rzisuklad.findByPodatnik", query = "SELECT r FROM Rzisuklad r WHERE r.rzisukladPK.podatnik = :podatnik"),
    @NamedQuery(name = "Rzisuklad.findByRok", query = "SELECT r FROM Rzisuklad r WHERE r.rzisukladPK.rok = :rok"),
    @NamedQuery(name = "Rzisuklad.findByUkladPodRok", query = "SELECT r FROM Rzisuklad r WHERE r.rzisukladPK.uklad = :uklad AND r.rzisukladPK.podatnik = :podatnik AND r.rzisukladPK.rok = :rok"),
    @NamedQuery(name = "Rzisuklad.findByBlokada", query = "SELECT r FROM Rzisuklad r WHERE r.blokada = :blokada")})
public class Rzisuklad implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RzisukladPK rzisukladPK;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean blokada;

    public Rzisuklad() {
    }

    public Rzisuklad(RzisukladPK rzisukladPK) {
        this.rzisukladPK = rzisukladPK;
    }

    public Rzisuklad(RzisukladPK rzisukladPK, boolean blokada) {
        this.rzisukladPK = rzisukladPK;
        this.blokada = blokada;
    }

    public Rzisuklad(String uklad, String podatnik, String rok) {
        this.rzisukladPK = new RzisukladPK(uklad, podatnik, rok);
    }

    public RzisukladPK getRzisukladPK() {
        return rzisukladPK;
    }

    public void setRzisukladPK(RzisukladPK rzisukladPK) {
        this.rzisukladPK = rzisukladPK;
    }

    public boolean getBlokada() {
        return blokada;
    }

    public void setBlokada(boolean blokada) {
        this.blokada = blokada;
    }

 
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rzisukladPK != null ? rzisukladPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rzisuklad)) {
            return false;
        }
        Rzisuklad other = (Rzisuklad) object;
        if ((this.rzisukladPK == null && other.rzisukladPK != null) || (this.rzisukladPK != null && !this.rzisukladPK.equals(other.rzisukladPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Rzisuklad[ rzisukladPK=" + rzisukladPK + " ]";
    }
    
}
