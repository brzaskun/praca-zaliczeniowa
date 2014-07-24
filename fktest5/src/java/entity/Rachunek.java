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
@Table (name = "rachunek")
public class Rachunek implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwa;
    @OneToOne(mappedBy = "rachunek")
    private Wiersz wiersz;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transakcja> transakcje;
    
    public Rachunek() {
        this.transakcje = new ArrayList<>();
    }

    
    public Rachunek(String nazwa) {
        this.transakcje = new ArrayList<>();
        this.nazwa = "Rachunek "+nazwa;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }
    
    public Transakcja getTransakcje(int i) {
        return this.transakcje.get(i);
    }

    public void setTransakcje(Transakcja transakcja) {
        this.transakcje.add(transakcja);
    }

    public List<Transakcja> getTransakcje() {
        return transakcje;
    }

    public void setTransakcje(List<Transakcja> transakcje) {
        this.transakcje = transakcje;
    }
    
    

    @Override
    public String toString() {
        return "Rachunek{" + "id=" + id + ", nazwa=" + nazwa + ", wiersz=" + wiersz.getNazwa() + '}';
    }

   

  
 
    
    
    
}
