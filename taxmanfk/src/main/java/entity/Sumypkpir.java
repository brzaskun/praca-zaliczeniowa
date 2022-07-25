/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.DokKsiega;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
@Table(name = "sumypkpir")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sumypkpir.findAll", query = "SELECT s FROM Sumypkpir s"),
    @NamedQuery(name = "Sumypkpir.findByPodatnik", query = "SELECT s FROM Sumypkpir s WHERE s.sumypkpirPK.podatnik = :podatnik"),
    @NamedQuery(name = "Sumypkpir.findByRok", query = "SELECT s FROM Sumypkpir s WHERE s.sumypkpirPK.rok = :rok"),
    @NamedQuery(name = "Sumypkpir.findByMc", query = "SELECT s FROM Sumypkpir s WHERE s.sumypkpirPK.mc = :mc"),
    @NamedQuery(name = "Sumypkpir.findByPodatnikRok", query = "SELECT s FROM Sumypkpir s WHERE s.sumypkpirPK.podatnik = :podatnik AND s.sumypkpirPK.rok = :rok"),
    @NamedQuery(name = "Sumypkpir.deleteByPodatnikRokMc", query = "DELETE FROM Sumypkpir s WHERE s.sumypkpirPK.podatnik = :podatnik AND s.sumypkpirPK.rok = :rok AND s.sumypkpirPK.mc = :mc")
})
public class Sumypkpir implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SumypkpirPK sumypkpirPK;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "sumy")
    private DokKsiega sumy;

    public Sumypkpir() {
    }

    public Sumypkpir(SumypkpirPK sumypkpirPK) {
        this.sumypkpirPK = sumypkpirPK;
    }

    public Sumypkpir(SumypkpirPK sumypkpirPK, DokKsiega sumy) {
        this.sumypkpirPK = sumypkpirPK;
        this.sumy = sumy;
    }

    public Sumypkpir(String podatnik, String rok, String mc) {
        this.sumypkpirPK = new SumypkpirPK(podatnik, rok, mc);
    }

    public SumypkpirPK getSumypkpirPK() {
        return sumypkpirPK;
    }

    public void setSumypkpirPK(SumypkpirPK sumypkpirPK) {
        this.sumypkpirPK = sumypkpirPK;
    }

    public DokKsiega getSumy() {
        return sumy;
    }

    public void setSumy(DokKsiega sumy) {
        this.sumy = sumy;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sumypkpirPK != null ? sumypkpirPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sumypkpir)) {
            return false;
        }
        Sumypkpir other = (Sumypkpir) object;
        if ((this.sumypkpirPK == null && other.sumypkpirPK != null) || (this.sumypkpirPK != null && !this.sumypkpirPK.equals(other.sumypkpirPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Sumypkpir[ sumypkpirPK=" + sumypkpirPK + " ]";
    }
    
}
