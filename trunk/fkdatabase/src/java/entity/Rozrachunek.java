/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Osito
 */
@Entity
public class Rozrachunek implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    private double kwota;
    @ManyToOne
    private StronaWiersza stronaWiersza;
    @ManyToMany
    private List<Rozrachunek> sparowanerozrachunki;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StronaWiersza getStronaWiersza() {
        return stronaWiersza;
    }

    public void setStronaWiersza(StronaWiersza stronaWiersza) {
        this.stronaWiersza = stronaWiersza;
    }
    
    
            
            
}
