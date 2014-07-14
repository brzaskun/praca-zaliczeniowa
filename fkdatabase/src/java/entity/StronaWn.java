/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Osito
 */
@Cacheable(false)
@Entity
@Table(name = "stronawn", catalog = "fktest", schema = "")
@Inheritance(strategy = InheritanceType.JOINED)
public class StronaWn extends StronaWiersza implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @OneToMany(mappedBy="stronaWn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transakcja> transakcje;
   
    public StronaWn() {
    }
    
    public StronaWn(String nazwarozrachunku) {
        super.setNazwaStronyWiersza(nazwarozrachunku);
    }

    public List<Transakcja> getTransakcje() {
        return transakcje;
    }

    public void setTransakcje(List<Transakcja> transakcje) {
        this.transakcje = transakcje;
    }
    
    
    @Override
    public String toString() {
        return "StronaWiersza{" + "nazwarozrachunku=" + nazwaStronyWiersza + ", wiersz=" + wiersz.getWiersznazwa() + '}';
    }

    
}
