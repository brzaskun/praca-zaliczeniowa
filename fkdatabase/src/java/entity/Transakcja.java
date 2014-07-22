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
import javax.persistence.CascadeType;
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
    @MapsId("rozliczajacy")
    @JoinColumn(name="rozliczajacy_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private StronaWiersza rozliczajacy;
    @MapsId("nowaTransakcja")
    @JoinColumn(name="nowaTransakcja_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private StronaWiersza nowaTransakcja;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double kwota;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double kwotapoprzednia;

    public Transakcja() {
    }

    public Transakcja(StronaWiersza rozliczajacy, StronaWiersza rozliczany) {
        this.rozliczajacy = rozliczajacy;
        this.nowaTransakcja = rozliczany;
    }

    public StronaWiersza getRozliczajacy() {
        return rozliczajacy;
    }

    public void setRozliczajacy(StronaWiersza rozliczajacy) {
        this.rozliczajacy = rozliczajacy;
    }

    public StronaWiersza getNowaTransakcja() {
        return nowaTransakcja;
    }

    public void setNowaTransakcja(StronaWiersza nowaTransakcja) {
        this.nowaTransakcja = nowaTransakcja;
    }

  
    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getKwotapoprzednia() {
        return kwotapoprzednia;
    }

    public void setKwotapoprzednia(double kwotapoprzednia) {
        this.kwotapoprzednia = kwotapoprzednia;
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
        if (!Objects.equals(this.rozliczajacy, other.rozliczajacy)) {
            return false;
        }
        if (!Objects.equals(this.nowaTransakcja, other.nowaTransakcja)) {
            return false;
        }
        return true;
    }

  

    @Override
    public String toString() {
        return "Transakcja{" + "stronaWn=" + rozliczajacy.getNazwaStronyWiersza() + ", stronaMa=" + nowaTransakcja.getNazwaStronyWiersza() + ", kwota=" + kwota + '}';
    }

    
}
