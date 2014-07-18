/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import entityfk.StronaMa;
import entityfk.StronaWn;
import entityfk.TransakcjaPK;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Cacheable(false)
@Entity
@Table(name = "transakcja", catalog = "pkpir", schema = "")
@XmlRootElement
public class Transakcja  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId 
    private TransakcjaPK transakcjaPK;
    @MapsId("stronaWnId")
    @JoinColumn(name="stronaWn_id")
    @ManyToOne
    private StronaWn stronaWn;
    @MapsId("stronaMaId")
    @JoinColumn(name="stronaMa_id")
    @ManyToOne
    private StronaMa stronaMa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kwotatransakcji")
    private double kwotatransakcji;
    @Column(name = "poprzedniakwota")
    private double poprzedniakwota;
    @Column(name = "roznicekursowe")
    private double roznicekursowe;
    

   
    
    public Transakcja() {
    }

    
    public Transakcja(StronaWn rozliczajacy, StronaMa rozliczany) {
        this.stronaWn = rozliczajacy;
        this.stronaMa = rozliczany;
    }

    public StronaWn getStronaWn() {
        return stronaWn;
    }

    public void setStronaWn(StronaWn stronaWn) {
        this.stronaWn = stronaWn;
    }

    public StronaMa getStronaMa() {
        return stronaMa;
    }

    public void setStronaMa(StronaMa stronaMa) {
        this.stronaMa = stronaMa;
    }

    public double getKwotatransakcji() {
        return kwotatransakcji;
    }

    public void setKwotatransakcji(double kwotatransakcji) {
        this.kwotatransakcji = kwotatransakcji;
    }

    public double getPoprzedniakwota() {
        return poprzedniakwota;
    }

    public void setPoprzedniakwota(double poprzedniakwota) {
        this.poprzedniakwota = poprzedniakwota;
    }
  

    public TransakcjaPK getTransakcjaPK() {
        return transakcjaPK;
    }

    public void setTransakcjaPK(TransakcjaPK transakcjaPK) {
        this.transakcjaPK = transakcjaPK;
    }

    public double getRoznicekursowe() {
        return roznicekursowe;
    }

    public void setRoznicekursowe(double roznicekursowe) {
        this.roznicekursowe = roznicekursowe;
    }

   
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.transakcjaPK);
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
        final Transakcja other = (Transakcja) obj;
        if (!Objects.equals(this.transakcjaPK, other.transakcjaPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transakcja{" + "stronaWn=" + stronaWn + ", stronaMa=" + stronaMa + ", kwota=" + kwotatransakcji + '}';
    }

    
}

