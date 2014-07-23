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
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwa;
    @ManyToOne
    private Dok _dok;
    @OneToMany(cascade = CascadeType.MERGE,  mappedBy = "_wierszn", fetch = FetchType.EAGER)
    private List<Strona> stronan;
    @OneToMany(cascade = CascadeType.MERGE,  mappedBy = "_wierszr", fetch = FetchType.EAGER)
    private List<Strona> stronar;

    public Wiersz() {
        this.stronan = new ArrayList<>();
        this.stronar = new ArrayList<>();
    }

    
    public Wiersz(String nazwa, Dok dok) {
        this.stronan = new ArrayList<>();
        this.stronar = new ArrayList<>();
        this.nazwa = "Wiersz "+nazwa;
        this._dok = dok;
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

    
    public Strona getStronaN(int i) {
        try {
            return this.stronan.get(i);
        } catch (Exception e) {
            return null;
        }
    }

    public void setStronaN(Strona strona) {
        this.stronan.add(strona);
    }
    
    public Strona getStronaR(int i) {
        try {
            return this.stronar.get(i);
        } catch (Exception e) {
            return null;
        }
    }

    public void setStronaR(Strona strona) {
        this.stronar.add(strona);
    }

    public List<Strona> getStronan() {
        return stronan;
    }

    public void setStronan(List<Strona> stronan) {
        this.stronan = stronan;
    }

    public List<Strona> getStronar() {
        return stronar;
    }

    public void setStronar(List<Strona> stronar) {
        this.stronar = stronar;
    }

    
    public Dok getDok() {
        return _dok;
    }

    public void setDok(Dok _dok) {
        this._dok = _dok;
    }

    @Override
    public String toString() {
        return "Wiersz{" + "id=" + id + ", nazwa=" + nazwa + ", _dok=" + _dok + ", stronan=" + stronan.size() + ", stronar=" + stronar.size() + '}';
    }

   

   
    
}
