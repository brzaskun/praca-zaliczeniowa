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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
public class Wiersz implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idWiersz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwaWiersz;
//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
//    private Dok _dok;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "wierszR")
    @JoinColumn(name = "rachunek", referencedColumnName = "nazwaR")
    private Rachunek rachunek;


    public Wiersz() {
    }

    
    public Wiersz(String nazwa, Dok dok) {
        this.nazwaWiersz = "Wiersz "+nazwa;
        //this._dok = dok;
       //this.rachunek = new Rachunek();
        //this.platnosc = new Platnosc();
    }
    
    public Wiersz(String nazwa, String nazwarozrachunek) {
        this.nazwaWiersz = "Wiersz "+nazwa;
        this.rachunek = new Rachunek("Rachunek "+nazwarozrachunek);
        (this.rachunek).setWierszR(this);
    }

    
    public Integer getIdWiersz() {
        return idWiersz;
    }

    public void setIdWiersz(Integer idWiersz) {
        this.idWiersz = idWiersz;
    }

    public String getNazwaWiersz() {
        return nazwaWiersz;
    }

    public void setNazwaWiersz(String nazwaWiersz) {
        this.nazwaWiersz = nazwaWiersz;
    }

    public Rachunek getRachunek() {
        return rachunek;
    }

    public void setRachunek(Rachunek rachunek) {
        this.rachunek = rachunek;
    }

   
   
//    public Dok getDok() {
//        return _dok;
//    }
//
//    public void setDok(Dok _dok) {
//        this._dok = _dok;
//    }

    @Override
    public String toString() {
        try {
            return "Wiersz{" + "id=" + idWiersz + ", nazwa=" + nazwaWiersz + ", _dok=" + ", rachunek=" + rachunek.getNazwaR() +'}';
        } catch (Exception e) {
            return "Wiersz{" + "id=" + idWiersz + ", nazwa=" + nazwaWiersz + ", _dok=" + '}';
        }
    }

   
    
}
