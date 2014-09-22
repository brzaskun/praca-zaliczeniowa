/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.*;
import java.io.Serializable;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Osito
 */
@Named
@Entity
public class EVatwpisFK implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    @JoinColumn(name = "ewidencja")
    @ManyToOne
    private Evewidencja ewidencja;
    @Column(name = "netto")
    private double netto;
    @Column(name = "vat")
    private double vat;
    @Column(name = "estawka")
    private String estawka;
    @ManyToOne(cascade = CascadeType.ALL)
    private Dokfk dokfk;
    
    

    public EVatwpisFK(Evewidencja ewidencja, double netto, double vat, String estawka) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
    }

    public EVatwpisFK() {
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

    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

       
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.ewidencja);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.netto) ^ (Double.doubleToLongBits(this.netto) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.vat) ^ (Double.doubleToLongBits(this.vat) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.estawka);
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
        final EVatwpisFK other = (EVatwpisFK) obj;
        if (!Objects.equals(this.ewidencja, other.ewidencja)) {
            return false;
        }
        if (!Objects.equals(this.dokfk, other.dokfk)) {
            return false;
        }
        return true;
    }

   

   
    
}


