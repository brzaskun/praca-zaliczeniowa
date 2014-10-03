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
    @NamedQuery(name = "Bilansuklad.findAll", query = "SELECT r FROM Bilansuklad r"),
    @NamedQuery(name = "Bilansuklad.findByUklad", query = "SELECT r FROM Bilansuklad r WHERE r.bilansukladPK.uklad = :uklad"),
    @NamedQuery(name = "Bilansuklad.findByPodatnik", query = "SELECT r FROM Bilansuklad r WHERE r.bilansukladPK.podatnik = :podatnik"),
    @NamedQuery(name = "Bilansuklad.findByRok", query = "SELECT r FROM Bilansuklad r WHERE r.bilansukladPK.rok = :rok"),
    @NamedQuery(name = "Bilansuklad.findByUkladPodRok", query = "SELECT r FROM Bilansuklad r WHERE r.bilansukladPK.uklad = :uklad AND r.bilansukladPK.podatnik = :podatnik AND r.bilansukladPK.rok = :rok"),
    @NamedQuery(name = "Bilansuklad.findByBlokada", query = "SELECT r FROM Bilansuklad r WHERE r.blokada = :blokada")})
public class Bilansuklad implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BilansukladPK bilansukladPK;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean blokada;

    public Bilansuklad() {
    }

    public Bilansuklad(BilansukladPK bilansukladPK) {
        this.bilansukladPK = bilansukladPK;
    }

    public Bilansuklad(BilansukladPK bilansukladPK, boolean blokada) {
        this.bilansukladPK = bilansukladPK;
        this.blokada = blokada;
    }

    public Bilansuklad(String uklad, String podatnik, String rok) {
        this.bilansukladPK = new BilansukladPK(uklad, podatnik, rok);
    }

    public BilansukladPK getBilansukladPK() {
        return bilansukladPK;
    }

    public void setBilansukladPK(BilansukladPK bilansukladPK) {
        this.bilansukladPK = bilansukladPK;
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
        hash += (bilansukladPK != null ? bilansukladPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bilansuklad)) {
            return false;
        }
        Bilansuklad other = (Bilansuklad) object;
        if ((this.bilansukladPK == null && other.bilansukladPK != null) || (this.bilansukladPK != null && !this.bilansukladPK.equals(other.bilansukladPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Bilansuklad[ bilansukladPK=" + bilansukladPK + " ]";
    }
    
}
