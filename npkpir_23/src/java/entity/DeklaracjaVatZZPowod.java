/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Osito
 */
@Entity
public class DeklaracjaVatZZPowod implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nr")
    private String nr;
    @Column(name = "tresc")
    private String tresc;
    @ManyToMany(mappedBy = "powody")
    private List<DeklaracjaVatZZ> vatzzty;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.nr);
        hash = 59 * hash + Objects.hashCode(this.tresc);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeklaracjaVatZZPowod other = (DeklaracjaVatZZPowod) obj;
        if (!Objects.equals(this.nr, other.nr)) {
            return false;
        }
        if (!Objects.equals(this.tresc, other.tresc)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeklaracjaVatZZPowod{" + "nr=" + nr + ", tresc=" + tresc + ", vatzzty=" + vatzzty + '}';
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public List<DeklaracjaVatZZ> getVatzzty() {
        return vatzzty;
    }

    public void setVatzzty(List<DeklaracjaVatZZ> vatzzty) {
        this.vatzzty = vatzzty;
    }
    
    
}
