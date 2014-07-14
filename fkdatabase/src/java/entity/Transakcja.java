/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Cacheable(false)
@Entity
@Table(catalog = "fktest", schema = "")
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
    @Column(nullable = false)
    private double kwota;

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
  
    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public TransakcjaPK getTransakcjaPK() {
        return transakcjaPK;
    }

    public void setTransakcjaPK(TransakcjaPK transakcjaPK) {
        this.transakcjaPK = transakcjaPK;
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
        return "Transakcja{" + "stronaWn=" + stronaWn + ", stronaMa=" + stronaMa + ", kwota=" + kwota + '}';
    }

    
}
