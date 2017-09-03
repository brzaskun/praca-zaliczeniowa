/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Osito
 */
@Named
@Entity
@Cacheable
public class EVatwpis1 extends EVatwpisSuper implements Serializable {
    private static final long serialVersionUID = -3274961058594456484L;
    
    @JoinColumn(name = "dok", referencedColumnName = "id_dok")
    @ManyToOne(cascade = CascadeType.ALL)
    private Dok dok;
    
    

    public EVatwpis1(Evewidencja ewidencja, double netto, double vat, String estawka) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
    }
    
    public EVatwpis1(Evewidencja ewidencja, double netto, double vat, String estawka, Dok dok) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
        this.dok = dok;
    }

    public EVatwpis1() {
    }

    public Evewidencja getEwidencja() {
        return ewidencja;
    }

    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
    }

  
    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public String getEstawka() {
        return estawka;
    }

    public void setEstawka(String estawka) {
        this.estawka = estawka;
    }

    public Dok getDok() {
        return dok;
    }

    public void setDok(Dok dok) {
        this.dok = dok;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMcEw() {
        return mcEw;
    }

    public void setMcEw(String mcEw) {
        this.mcEw = mcEw;
    }

    public String getRokEw() {
        return rokEw;
    }

    public void setRokEw(String rokEw) {
        this.rokEw = rokEw;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.ewidencja);
        hash = 17 * hash + Objects.hashCode(this.dok);
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
        final EVatwpis1 other = (EVatwpis1) obj;
        if (!Objects.equals(this.ewidencja, other.ewidencja)) {
            return false;
        }
        if (!Objects.equals(this.dok, other.dok)) {
            return false;
        }
        return true;
    }

   

   
    
}


