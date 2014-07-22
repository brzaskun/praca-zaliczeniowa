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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rozrachunek")
public class Rozrachunek implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column
    private double kwota;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stronaWiersza", referencedColumnName = "nazwaStronyWiersza") 
    private StronaWiersza stronaWiersza;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "rozliczajacy_id", referencedColumnName = "id")
//    private Rozrachunek rozliczajacy;
//    @OneToMany(mappedBy = "rozliczajacy", cascade = CascadeType.ALL)
//    private List<Rozrachunek> listarozliczanych;

    public Rozrachunek() {
       // this.listarozliczanych = new ArrayList<>();
    }

    public Rozrachunek(double kwota, StronaWiersza stronaWiersza) {
        //this.listarozliczanych = new ArrayList<>();
        this.kwota = kwota;
        this.stronaWiersza = stronaWiersza;
    }
    

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StronaWiersza getStronaWiersza() {
        return stronaWiersza;
    }

    public void setStronaWiersza(StronaWiersza stronaWiersza) {
        this.stronaWiersza = stronaWiersza;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

//    public List<Rozrachunek> getListarozliczanych() {
//        return listarozliczanych;
//    }
//
//    public void setListarozliczanych(List<Rozrachunek> listarozliczanych) {
//        this.listarozliczanych = listarozliczanych;
//    }
//    
    
//    public List<Rozrachunek> getSparowanerozrachunki() {
//        return sparowanerozrachunki;
//    }
//
//    public void setSparowanerozrachunki(List<Rozrachunek> sparowanerozrachunki) {
//        this.sparowanerozrachunki = sparowanerozrachunki;
//    }

//    @Override
//    public String toString() {
//        return "Rozrachunek{" + "id=" + id + ", kwota=" + kwota + ", stronaWiersza=" + stronaWiersza.getNazwaStronyWiersza() + ", sparowanerozrachunki=" + sparowanerozrachunki.size() + '}';
//    }
//
//    

//    public Rozrachunek getRozliczajacy() {
//        return rozliczajacy;
//    }
//
//    public void setRozliczajacy(Rozrachunek rozliczajacy) {
//        this.rozliczajacy = rozliczajacy;
//    }

    @Override
    public String toString() {
        return "Rozrachunek{" + "id=" + id + ", kwota=" + kwota + ", stronaWiersza=" + stronaWiersza.getNazwaStronyWiersza() +'}';
    }
            
    
            
}
