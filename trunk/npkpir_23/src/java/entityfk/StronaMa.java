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
@Table(name = "stronama", catalog = "fktest", schema = "")
@Inheritance(strategy = InheritanceType.JOINED)
public class StronaMa extends StronaWiersza implements Serializable {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "kontoMa", referencedColumnName = "id")
    @ManyToOne
    private Konto kontoMa;
    @OneToMany(mappedBy = "stronaMa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transakcja> transakcje;
    @Column(name = "MaReadOnly")
    private boolean MaReadOnly;

      

    public StronaMa() {
        this.transakcje = new ArrayList<>();
    }

    public StronaMa(String nazwarozrachunku) {
        this.transakcje = new ArrayList<>();
        super.setNazwaStronyWiersza(nazwarozrachunku);
    }

    public List<Transakcja> getTransakcje() {
        return transakcje;
    }

    public void setTransakcje(List<Transakcja> transakcje) {
        this.transakcje = transakcje;
    }

    public Konto getKontoMa() {
        return kontoMa;
    }

    public void setKontoMa(Konto kontoMa) {
        this.kontoMa = kontoMa;
    }

    public boolean isMaReadOnly() {
        return MaReadOnly;
    }

    public void setMaReadOnly(boolean MaReadOnly) {
        this.MaReadOnly = MaReadOnly;
    }

    
    

}
