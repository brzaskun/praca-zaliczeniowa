/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "stronawiersza")
public class StronaWiersza implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwaStronyWiersza;
    @ManyToOne
    @JoinColumn(name = "idwiersza", referencedColumnName = "idwiersza")
    private Wiersz wiersz;
    @Column(name = "wnma")
    private String wnma;
//    @JoinTable(name = "StronaWiersza_StronaWiersza",
//            joinColumns = {
//                @JoinColumn(table = "StronaWiersza", name = "stronawiersza_id", referencedColumnName = "nazwaStronyWiersza"),},
//            inverseJoinColumns = {
//                @JoinColumn(table = "Transakcja", name = "transakcja_rozliczajacy_id", referencedColumnName = "rozliczajacy_id"),
//                @JoinColumn(table = "Transakcja", name = "transakcja_nowaTransakcja_id", referencedColumnName = "nowaTransakcja_id")
//            }
//    )
//    @OneToMany(mappedBy = "rozliczajacy", cascade = CascadeType.ALL)
//    private List<Transakcja> transakcjeR;
//    @OneToMany(mappedBy = "nowaTransakcja", cascade = CascadeType.ALL)
//    private List<Transakcja> transakcjeN;
    @OneToMany(mappedBy = "stronaWiersza")
    private List<Rozrachunek> Rozrachunek;
    @Column(name = "kwotapierwotna")
    private double kwotapierwotna;
    @Column(name = "rozliczono")
    private double rozliczono;
    @Column(name = "pozostalo")
    private double pozostalo;

    public StronaWiersza() {
        this.kwotapierwotna = 0.0;
        this.rozliczono = 0.0;
        //this.transakcje = new ArrayList<>();
    }

    public StronaWiersza(String nazwarozrachunku, double kwotapierwotna) {
        this.kwotapierwotna = kwotapierwotna;
        this.rozliczono = 0.0;
        //this.transakcje = new ArrayList<>();
        this.nazwaStronyWiersza = nazwarozrachunku;
    }

    public String getNazwaStronyWiersza() {
        return nazwaStronyWiersza;
    }

    public void setNazwaStronyWiersza(String nazwaStronyWiersza) {
        this.nazwaStronyWiersza = nazwaStronyWiersza;
    }

    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }

//    public List<Transakcja> getTransakcjeR() {
//        return transakcjeR;
//    }
//
//    public void setTransakcjeR(List<Transakcja> transakcjeR) {
//        this.transakcjeR = transakcjeR;
//    }
//
//    public List<Transakcja> getTransakcjeN() {
//        return transakcjeN;
//    }
//
//    public void setTransakcjeN(List<Transakcja> transakcjeN) {
//        this.transakcjeN = transakcjeN;
//    }


    public String getWnma() {
        return wnma;
    }

    public void setWnma(String wnma) {
        this.wnma = wnma;
    }

    public double getKwotapierwotna() {
        return kwotapierwotna;
    }

    public void setKwotapierwotna(double kwotapierwotna) {
        this.kwotapierwotna = kwotapierwotna;
    }

    public double getRozliczono() {
//        this.rozliczono = 0.0;
//        for (Transakcja p : this.transakcje) {
//            this.rozliczono += p.getKwota();
//        }
//        this.pozostalo = this.kwotapierwotna - this.rozliczono;
        return rozliczono;
    }

    public double getPozostalo() {
        return pozostalo;
    }

    public void setPozostalo(double pozostalo) {
        this.pozostalo = pozostalo;
    }

//    public void dodajTransakcjeNowe(Transakcja transakcja) {
//        if (this.transakcjeN.contains(transakcja)) {
//            this.rozliczono = this.rozliczono - transakcja.getKwotapoprzednia() + transakcja.getKwota();
//        } else {
//            this.rozliczono = this.rozliczono + transakcja.getKwota();
//            this.transakcjeN.add(transakcja);
//        }
//        this.pozostalo = this.kwotapierwotna - this.rozliczono;
//    }
//    
//    public void dodajTransakcjeRozliczajacy(Transakcja transakcja) {
//        if (this.transakcjeR.contains(transakcja)) {
//            this.rozliczono = this.rozliczono - transakcja.getKwotapoprzednia() + transakcja.getKwota();
//        } else {
//            this.rozliczono = this.rozliczono + transakcja.getKwota();
//            this.transakcjeR.add(transakcja);
//        }
//        this.pozostalo = this.kwotapierwotna - this.rozliczono;
//    }
    @Override
    public String toString() {
        return "StronaWiersza{" + "nazwaStronyWiersza=" + nazwaStronyWiersza + ", wiersz=" + wiersz + ", wnma=" + wnma + ", kwotapierwotna=" + kwotapierwotna + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo + '}';
    }

  

   
   
}
