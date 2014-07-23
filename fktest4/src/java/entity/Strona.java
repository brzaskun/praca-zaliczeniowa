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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
public class Strona implements Serializable{
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
    @ManyToOne(cascade = CascadeType.ALL)
    private Wiersz _wierszr;
    @ManyToOne(cascade = CascadeType.ALL)
    private Wiersz _wierszn;
    
    public Strona() {
    }

    
    public Strona(String nazwa, Wiersz wierszr, Wiersz wierszn) {
        this.nazwa = "Strona "+nazwa;
        this._wierszn = wierszn;
        this._wierszr = wierszr;
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

    public Wiersz getWierszr() {
        return _wierszr;
    }

    public void setWierszr(Wiersz _wierszr) {
        this._wierszr = _wierszr;
    }

    public Wiersz getWierszn() {
        return _wierszn;
    }

    public void setWierszn(Wiersz _wierszn) {
        this._wierszn = _wierszn;
    }

    @Override
    public String toString() {
        return "Strona{" + "id=" + id + ", nazwa=" + nazwa + ", _wierszr=" + _wierszr.getNazwa() + ", _wierszn=" + _wierszn.getNazwa() + '}';
    }

       
   
    
    
    
}
