/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @OneToOne
    private Dok _dok;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "_wiersz")
    private Strona strona;

    public Wiersz() {
    }

    
    public Wiersz(String nazwa, Dok dok) {
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
        return strona;
    }

    public void setStrona(Strona strona) {
        this.strona = strona;
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
