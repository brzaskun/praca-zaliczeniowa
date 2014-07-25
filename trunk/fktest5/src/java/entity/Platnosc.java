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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@Table (name = "platnosc")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue(value = "Platnosc")
public class Platnosc extends Rozrachunek implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idP;
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwaP;
    @OneToOne
    private Wiersz wierszP;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "platnoscT")
    private List<Transakcja> transakcjeP;
    
    public Platnosc() {
        super();
        this.DTYPE = "Platnosc";
        this.transakcjeP = new ArrayList<>();
    }

    
    public Platnosc(String nazwa) {
        super();
        this.DTYPE = "Platnosc";
        this.transakcjeP = new ArrayList<>();
        this.nazwaP = "Platnosc "+nazwa;
    }

    
    public Integer getIdP() {
        return idP;
    }

    public void setIdP(Integer idP) {
        this.idP = idP;
    }

    public String getNazwaP() {
        return nazwaP;
    }

    public void setNazwaP(String nazwaP) {
        this.nazwaP = nazwaP;
    }

   
    public Transakcja getTransakcje(int i) {
        return this.transakcjeP.get(i);
    }

    public void setTransakcje(Transakcja transakcja) {
        this.transakcjeP.add(transakcja);
    }

    public List<Transakcja> getTransakcjeP() {
        return transakcjeP;
    }

    public void setTransakcjeP(List<Transakcja> transakcjeP) {
        this.transakcjeP = transakcjeP;
    }

    public Wiersz getWierszP() {
        return wierszP;
    }

    public void setWierszP(Wiersz wierszP) {
        this.wierszP = wierszP;
    }
    

    @Override
    public String toString() {
        try {
            return "Platnosc{" + "idP=" + idP + ", nazwaP=" + nazwaP + ", transakcjeP=" + transakcjeP.size() + '}';
        } catch (Exception e) {
            return "";
        }
    }
    
    
    
    
}
