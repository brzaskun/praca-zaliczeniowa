/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"nazwalimitu","rok"})
})
@NamedQueries({
//    @NamedQuery(name = "EVatOpis.findAll", query = "SELECT d FROM EVatOpis d"),
//    @NamedQuery(name = "EVatOpis.findByLogin", query = "SELECT d FROM EVatOpis d WHERE d.login = :login"),
    })
public class Granica implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "nazwalimitu")
    private String nazwalimitu;
    @Column(name = "rok")
    private String rok;
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "proporcja")
    private boolean proporcja;

    public Granica() {
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.id;
        hash = 61 * hash + Objects.hashCode(this.nazwalimitu);
        hash = 61 * hash + Objects.hashCode(this.rok);
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
        final Granica other = (Granica) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nazwalimitu, other.nazwalimitu)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Limity{" + "nazwalimitu=" + nazwalimitu + ", rok=" + rok + ", kwota=" + kwota + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwalimitu() {
        return nazwalimitu;
    }

    public void setNazwalimitu(String nazwalimitu) {
        this.nazwalimitu = nazwalimitu;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public boolean isProporcja() {
        return proporcja;
    }

    public void setProporcja(boolean proporcja) {
        this.proporcja = proporcja;
    }
    
    
    
}
