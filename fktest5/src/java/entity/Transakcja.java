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
public class Transakcja implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idT;
    @ManyToOne (cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "rachunekTid", referencedColumnName = "idR")
    private Rachunek rachunekT;
    @ManyToOne (cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "platnoscTid", referencedColumnName = "idP")
    private Platnosc platnoscT;
    @Column
    private double kwotaT;

    public Transakcja(Rachunek rachunek, Platnosc platnosc, double kwota) {
        this.rachunekT = rachunek;
        this.platnoscT = platnosc;
        this.kwotaT = kwota;
    }

    public Transakcja() {
    }
    
    
    
    public Integer getIdT() {
        return idT;
    }

    public void setIdT(Integer idT) {
        this.idT = idT;
    }

    public Rachunek getRachunekT() {
        return rachunekT;
    }

    public void setRachunekT(Rachunek rachunekT) {
        this.rachunekT = rachunekT;
    }

    public Platnosc getPlatnoscT() {
        return platnoscT;
    }

    public void setPlatnoscT(Platnosc platnoscT) {
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
        return "Transakcja{" + "id=" + idT + ", rachunek=" + rachunekT + ", platnosc=" + platnoscT + ", kwota=" + kwotaT + '}';
    }
    
    
    
}
