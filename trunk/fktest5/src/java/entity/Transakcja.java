/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Osito
 */
@Entity
public class Transakcja implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @ManyToOne
    private Rachunek rachunek;
    @ManyToOne
    private Platnosc platnosc;
    @Column
    private double kwota;

    public Transakcja(Rachunek rachunek, Platnosc platnosc, double kwota) {
        this.rachunek = rachunek;
        this.platnosc = platnosc;
        this.kwota = kwota;
    }

    public Transakcja() {
    }
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rachunek getRachunek() {
        return rachunek;
    }

    public void setRachunek(Rachunek rachunek) {
        this.rachunek = rachunek;
    }

    public Platnosc getPlatnosc() {
        return platnosc;
    }

    public void setPlatnosc(Platnosc platnosc) {
        this.platnosc = platnosc;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    @Override
    public String toString() {
        return "Transakcja{" + "id=" + id + ", rachunek=" + rachunek + ", platnosc=" + platnosc + ", kwota=" + kwota + '}';
    }
    
    
    
}
