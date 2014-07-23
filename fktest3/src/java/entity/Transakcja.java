/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
public class Transakcja implements Serializable{
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
    private Rozrachunek _rozrachunek;

    public Transakcja() {
    }

    
    public Transakcja(String nazwa, Rozrachunek rozrachunek) {
        this.nazwa = "Transakcja "+nazwa;
        this._rozrachunek = rozrachunek;
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

    public Rozrachunek getRozrachunek() {
        return _rozrachunek;
    }

    public void setRozrachunek(Rozrachunek _rozrachunek) {
        this._rozrachunek = _rozrachunek;
    }

    @Override
    public String toString() {
        return "Transakcja{" + "id=" + id + ", nazwa=" + nazwa + ", _rozrachunek=" + _rozrachunek + '}';
    }
    
    

    
    
}
