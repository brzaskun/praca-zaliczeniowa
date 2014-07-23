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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "_wiersz")
    private List<Strona> strona;

    public Wiersz() {
        this.strona = new ArrayList<>();
    }

    
    public Wiersz(String nazwa, Dok dok) {
        this.strona = new ArrayList<>();
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

    public Strona getStrona() {
        return this.strona.get(0);
    }

    public void setStrona(Strona strona) {
        this.strona.add(strona);
    }

    public Dok getDok() {
        return _dok;
    }

    public void setDok(Dok _dok) {
        this._dok = _dok;
    }
    
    

    @Override
    public String toString() {
        return "Wiersz{" + "id=" + id + ", nazwa=" + nazwa + '}';
    }
    
    
    
}
