/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
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
    @NamedQuery(name = "UkladBR.findAll", query = "SELECT r FROM UkladBR r"),
    @NamedQuery(name = "UkladBR.findByUklad", query = "SELECT r FROM UkladBR r WHERE r.ukladBRPK.uklad = :uklad"),
    @NamedQuery(name = "UkladBR.findByPodatnik", query = "SELECT r FROM UkladBR r WHERE r.ukladBRPK.podatnik = :podatnik"),
    @NamedQuery(name = "UkladBR.findByRok", query = "SELECT r FROM UkladBR r WHERE r.ukladBRPK.rok = :rok"),
    @NamedQuery(name = "UkladBR.findByUkladPodRok", query = "SELECT r FROM UkladBR r WHERE r.ukladBRPK.uklad = :uklad AND r.ukladBRPK.podatnik = :podatnik AND r.ukladBRPK.rok = :rok"),
    @NamedQuery(name = "UkladBR.findByBlokada", query = "SELECT r FROM UkladBR r WHERE r.blokada = :blokada")})
public class UkladBR implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private UkladBRPK ukladBRPK;
    @GeneratedValue
    private int lp;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, name = "blokada")
    private boolean blokada;


    public UkladBR() {
    }

    public UkladBR(UkladBRPK ukladBRPK) {
        this.ukladBRPK = ukladBRPK;
    }

    public UkladBR(UkladBRPK ukladBRPK, boolean blokada) {
        this.ukladBRPK = ukladBRPK;
        this.blokada = blokada;
    }

    public UkladBR(String uklad, String podatnik, String rok) {
        this.ukladBRPK = new UkladBRPK(uklad, podatnik, rok);
    }
   
    public boolean getBlokada() {
        return blokada;
    }

    public void setBlokada(boolean blokada) {
        this.blokada = blokada;
    }

    public UkladBRPK getUkladBRPK() {
        return ukladBRPK;
    }

    public void setUkladBRPK(UkladBRPK ukladBRPK) {
        this.ukladBRPK = ukladBRPK;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    
       

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ukladBRPK != null ? ukladBRPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UkladBR)) {
            return false;
        }
        UkladBR other = (UkladBR) object;
        if ((this.ukladBRPK == null && other.ukladBRPK != null) || (this.ukladBRPK != null && !this.ukladBRPK.equals(other.ukladBRPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UkladBR{" + "ukladBRPK=" + ukladBRPK + ", blokada=" + blokada + '}';
    }

   
       
    
}
