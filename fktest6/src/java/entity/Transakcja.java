/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 *
 * @author Osito
 */
@Entity
public class Transakcja implements Serializable{
    private static final long serialVersionUID = 1L;

    @EmbeddedId 
    private TransakcjaPK transakcjaPK;
    @MapsId("rachunekT_PK")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "rachunekTid", referencedColumnName = "idR")
    private Rachunek rachunekT;
    @MapsId("platnoscT_PK")
    @ManyToOne (cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "platnoscTid", referencedColumnName = "idR")
    private Rachunek platnoscT;
    @Column
    private double kwotaT;

    public Transakcja(Rachunek rachunek, Rachunek platnosc, double kwota) {
        this.rachunekT = rachunek;
        this.platnoscT = platnosc;
        this.kwotaT = kwota;
    }

    public Transakcja() {
    }

    public TransakcjaPK getTransakcjaPK() {
        return transakcjaPK;
    }

    public void setTransakcjaPK(TransakcjaPK transakcjaPK) {
        this.transakcjaPK = transakcjaPK;
    }
    
   
    public Rachunek getRachunekT() {
        return rachunekT;
    }

    public void setRachunekT(Rachunek rachunekT) {
        this.rachunekT = rachunekT;
    }

    public Rachunek getPlatnoscT() {
        return platnoscT;
    }

    public void setPlatnoscT(Rachunek platnoscT) {
        this.platnoscT = platnoscT;
    }

   

    public double getKwotaT() {
        return kwotaT;
    }

    public void setKwotaT(double kwotaT) {
        this.kwotaT = kwotaT;
    }

    @Override
    public String toString() {
        return "Transakcja{" + "transakcjaPK=" + transakcjaPK + ", rachunekT=" + rachunekT.getNazwaR() + ", platnoscT=" + platnoscT.getNazwaR() + ", kwotaT=" + kwotaT + '}';
    }

    
    
    
}
