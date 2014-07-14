/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class StronaWiersza implements Serializable{
     private static final long serialVersionUID = 1L;
     
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    protected String nazwaStronyWiersza;
    @OneToOne
    @JoinColumn(name = "wierszid", referencedColumnName = "idwiersza")
    protected Wiersz wiersz;
    

    public StronaWiersza() {
    }

    public StronaWiersza(String nazwarozrachunku) {
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

    
    

    @Override
    public String toString() {
        return "StronaWiersza{" + "nazwaStronyWiersza=" + nazwaStronyWiersza + ", wiersz=" + wiersz.getWiersznazwa() + '}';
    }

    

    
    
    
}
