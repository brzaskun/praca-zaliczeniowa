/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    
    @JoinColumn(name = "kontoWn", referencedColumnName = "id")
    @ManyToOne
    private Konto kontoWn;
    @OneToMany(mappedBy="stronaWn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transakcja> transakcje;
    @Column(name = "WnReadOnly")
    private boolean WnReadOnly;
    
   
    public StronaWn() {
        this.transakcje = new ArrayList<>();
    }
    
    public StronaWn(String nazwarozrachunku) {
        this.transakcje = new ArrayList<>();
        super.setNazwaStronyWiersza(nazwarozrachunku);
    }

    public List<Transakcja> getTransakcje() {
        return transakcje;
    }

    public void setTransakcje(List<Transakcja> transakcje) {
        this.transakcje = transakcje;
    }

    public Konto getKontoWn() {
        return kontoWn;
    }

    public void setKontoWn(Konto kontoWn) {
        this.kontoWn = kontoWn;
    }

    public boolean isWnReadOnly() {
        return WnReadOnly;
    }

    public void setWnReadOnly(boolean WnReadOnly) {
        this.WnReadOnly = WnReadOnly;
    }
    
    
    
   
    
    
}
