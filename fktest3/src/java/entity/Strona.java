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
    @ManyToOne
    private Wiersz _wiersz;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "_strona")
    private List<Rozrachunek> rozrachunek;

    public Strona() {
        this.rozrachunek = new ArrayList<>();
    }

    
    public Strona(String nazwa, Wiersz wiersz) {
        this.nazwa = "Strona "+nazwa;
        this._wiersz = wiersz;
        this.rozrachunek = new ArrayList<>();
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
        return this.rozrachunek.get(0);
    }
    
    public Rozrachunek getRozrachunek(int i) {
        return this.rozrachunek.get(i);
    }

    public void setRozrachunek(Rozrachunek rozrachunek) {
        this.rozrachunek.add(rozrachunek);
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
