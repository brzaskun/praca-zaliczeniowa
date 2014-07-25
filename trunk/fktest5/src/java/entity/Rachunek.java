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
    private Integer idR;
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwaR;
//    @OneToOne
//    private Wiersz wierszR;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rachunekT")
    private List<Transakcja> transakcjeR;
    
    public Rachunek() {
        this.transakcjeR = new ArrayList<>();
    }

    
    public Rachunek(String nazwa) {
        this.transakcjeR = new ArrayList<>();
        this.nazwaR = "Rachunek "+nazwa;
    }

    
    public Integer getIdR() {
        return idR;
    }

    public void setIdR(Integer idR) {
        this.idR = idR;
    }

    public String getNazwaR() {
        return nazwaR;
    }

    public void setNazwaR(String nazwaR) {
        this.nazwaR = nazwaR;
    }

      
    public Transakcja getTransakcje(int i) {
        return this.transakcjeR.get(i);
    }

    public void setTransakcje(Transakcja transakcja) {
        this.transakcjeR.add(transakcja);
    }

    public List<Transakcja> getTransakcjeR() {
        return transakcjeR;
    }

    public void setTransakcjeR(List<Transakcja> transakcjeR) {
        this.transakcjeR = transakcjeR;
    }
    
    

    @Override
    public String toString() {
        try {
            return "Rachunek{" + "id=" + idR + ", nazwa=" + nazwaR + ", transakcjeR=" + transakcjeR.size() + '}';
        } catch (Exception e) {
            return "";
        }
    }

   

  
 
    
    
    
}
