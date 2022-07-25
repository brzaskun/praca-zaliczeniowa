/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Tabelanbp;
import java.io.Serializable;
import javax.persistence.Basic;
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

@Entity
public class Kolumna1Rozbicie implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    @Column(name = "netto")
    private double netto;
    @Column(name = "nettowaluta")
    private double nettowaluta;
    @Column(name = "vat")
    private double vat;
    @Column(name = "brutto")
    private double brutto;
    @Column(name = "data")
    private String data;
    @ManyToOne
    @JoinColumn(name = "idtabelanbp")
    private Tabelanbp tabelanbp;

    public Kolumna1Rozbicie() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kolumna1Rozbicie other = (Kolumna1Rozbicie) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kolumna1Rozbicie{" + "netto=" + netto + ", nettowaluta=" + nettowaluta + ", vat=" + vat + ", brutto=" + brutto + ", data=" + data + ", tabelanbp=" + tabelanbp.getNrtabeli() + '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getNettowaluta() {
        return nettowaluta;
    }

    public void setNettowaluta(double nettowaluta) {
        this.nettowaluta = nettowaluta;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Tabelanbp getTabelanbp() {
        return tabelanbp;
    }

    public void setTabelanbp(Tabelanbp tabelanbp) {
        this.tabelanbp = tabelanbp;
    }

    
  

   
    
}
