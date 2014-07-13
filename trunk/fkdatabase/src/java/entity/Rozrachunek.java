/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Cacheable(false)
@Entity
@Table(catalog = "fktest", schema = "")
@XmlRootElement
public class Rozrachunek implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nazwarozrachunku;
    @ManyToOne
    @JoinColumn(name = "wierszid", referencedColumnName = "idwiersza")
    private Wiersz wiersz;
    @OneToMany(mappedBy="rozliczajacy", cascade = CascadeType.ALL)
    private List<Transakcja> transakcje;
    

    public Rozrachunek() {
        this.transakcje = new ArrayList<>();
    }

    public Rozrachunek(String nazwarozrachunku) {
        this.transakcje = new ArrayList<>();
        this.nazwarozrachunku = nazwarozrachunku;
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

    public List<Transakcja> getTransakcje() {
        return transakcje;
    }

    public void setTransakcje(List<Transakcja> transakcje) {
        this.transakcje = transakcje;
    }

  
    
    @Override
    public String toString() {
        return "Rozrachunek{" + ", nazwarozrachunku=" + nazwarozrachunku + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nazwarozrachunku);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rozrachunek other = (Rozrachunek) obj;
        if (!Objects.equals(this.nazwarozrachunku, other.nazwarozrachunku)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
