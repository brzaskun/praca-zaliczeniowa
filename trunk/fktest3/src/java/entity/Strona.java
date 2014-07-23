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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    @ManyToOne
    private Wiersz _wiersz;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "_strona")
    private Rozrachunek rozrachunek;

    public Strona() {
    }

    
    public Strona(String nazwa, Wiersz wiersz) {
        this.nazwa = "Strona "+nazwa;
        this._wiersz = wiersz;
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
        return rozrachunek;
    }

    public void setRozrachunek(Rozrachunek rozrachunek) {
        this.rozrachunek = rozrachunek;
    }

    public Wiersz getWiersz() {
        return _wiersz;
    }

    public void setWiersz(Wiersz _wiersz) {
        this._wiersz = _wiersz;
    }
 
    

    @Override
    public String toString() {
        return "Strona{" + "id=" + id + ", nazwa=" + nazwa + '}';
    }
    
    
    
}
