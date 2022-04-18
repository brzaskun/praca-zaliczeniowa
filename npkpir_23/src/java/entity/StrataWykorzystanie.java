/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "stratawykorzystanie", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"strata", "rok", "mc"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StrataWykorzystanie.findAll", query = "SELECT a FROM StrataWykorzystanie a"),
    @NamedQuery(name = "StrataWykorzystanie.findById", query = "SELECT a FROM StrataWykorzystanie a WHERE a.id = :id"),
    @NamedQuery(name = "StrataWykorzystanie.findByStrata", query = "SELECT a FROM StrataWykorzystanie a WHERE a.strata = :strata"),
    @NamedQuery(name = "StrataWykorzystanie.findByRok", query = "SELECT a FROM StrataWykorzystanie a WHERE a.rok = :rok"),
    })
public class StrataWykorzystanie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "strata", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Strata strata;
    @Column(name = "rok")
    private String rok;
    @Column(name = "mc")
    private String mc;
    @Column(name = "kwotawykorzystania")
    private double kwotawykorzystania;

    public StrataWykorzystanie() {
    }

    public StrataWykorzystanie(Strata strata, String rokwykorzystania, String mc) {
        this.strata = strata;
        this.rok = rokwykorzystania;
        this.mc = mc;
    }
    
    public StrataWykorzystanie(Strata strata, String rokwykorzystania, String mc, double kwotawykorzystania) {
        this.strata = strata;
        this.rok = rokwykorzystania;
        this.mc = mc;
        this.kwotawykorzystania = kwotawykorzystania;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.strata);
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
        final StrataWykorzystanie other = (StrataWykorzystanie) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.strata, other.strata)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StrataWykorzystanie{" + "strata=" + strata.getPodatnikObj().getNazwapelna() + ", rokwykorzystania=" + rok + ", kwotawykorzystania=" + kwotawykorzystania + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Strata getStrata() {
        return strata;
    }

    public void setStrata(Strata strata) {
        this.strata = strata;
    }

    public String getRok() {
        return rok;
    }
    
    public int getRokInt() {
        return Integer.parseInt(rok);
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public double getKwotawykorzystania() {
        return kwotawykorzystania;
    }

    public void setKwotawykorzystania(double kwotawykorzystania) {
        this.kwotawykorzystania = kwotawykorzystania;
    }

    public String getMc() {
        return mc;
    }
    
    public int getMcInt() {
        return Integer.parseInt(mc);
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
    
    
    
    
}
