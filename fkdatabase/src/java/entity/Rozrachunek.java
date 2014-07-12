/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "fktest", schema = "")
@XmlRootElement
public class Rozrachunek implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idroz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwarozrachunku;
    @ManyToOne
    @JoinColumn(name = "wierszid", referencedColumnName = "idwiersza")
    private Wiersz wiersz;

    public Rozrachunek() {
    }

    public Rozrachunek(String nazwarozrachunku) {
        this.nazwarozrachunku = nazwarozrachunku;
    }
    
    

    public Integer getIdroz() {
        return idroz;
    }

    public void setIdroz(Integer idroz) {
        this.idroz = idroz;
    }

    public String getNazwarozrachunku() {
        return nazwarozrachunku;
    }

    public void setNazwarozrachunku(String nazwarozrachunku) {
        this.nazwarozrachunku = nazwarozrachunku;
    }

    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }

    @Override
    public String toString() {
        return "Rozrachunek{" + "idroz=" + idroz + ", nazwarozrachunku=" + nazwarozrachunku + '}';
    }
    
    
    
}
