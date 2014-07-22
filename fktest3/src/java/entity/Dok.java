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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
public class Dok implements Serializable{
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "_dok")
    private List<Wiersz> wiersz;

    public Dok() {
    }

    public Dok(String nazwa) {
        this.wiersz = new ArrayList<>();
        this.nazwa = "Dok "+nazwa;
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
        return this.wiersz.get(0);
    }
    
    public Wiersz getWiersz(int i) {
        return this.wiersz.get(i);
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz.add(wiersz);
    }

    @Override
    public String toString() {
        return "Dok{" + "id=" + id + ", nazwa=" + nazwa + ", wiersz=" + wiersz.size() + '}';
    }

    
    

    
    
    
}
