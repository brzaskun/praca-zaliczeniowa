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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"podatnik, ewidencja"})
})
@NamedQueries({
    @NamedQuery(name = "PodatnikEwidencjaDok.findAll", query = "SELECT f FROM PodatnikEwidencjaDok f"),
    @NamedQuery(name = "PodatnikEwidencjaDok.findByPodatnik", query = "SELECT f FROM PodatnikEwidencjaDok f WHERE f.podatnik = :podatnik")
})
public class PodatnikEwidencjaDok  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Podatnik podatnik;
    @JoinColumn(name = "ewidencja", referencedColumnName = "nazwa")
    @ManyToOne(optional = false)
    protected Evewidencja ewidencja;
    @JoinColumn(name = "evewidencjaID", referencedColumnName = "id")
    private Evewidencja evewidencjaID;
    @Column(name = "kolejnosc")
    private int kolejnosc;

    public PodatnikEwidencjaDok() {
    }

    
    public PodatnikEwidencjaDok(Podatnik podatnik, Evewidencja p) {
        this.podatnik = podatnik;
        this.ewidencja = p;
        this.kolejnosc = 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.podatnik);
        hash = 11 * hash + Objects.hashCode(this.ewidencja);
        hash = 11 * hash + this.kolejnosc;
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
        final PodatnikEwidencjaDok other = (PodatnikEwidencjaDok) obj;
        if (this.kolejnosc != other.kolejnosc) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        if (!Objects.equals(this.ewidencja, other.ewidencja)) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public Evewidencja getEwidencja() {
        return ewidencja;
    }

    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
    }

    public Evewidencja getEvewidencjaID() {
        return evewidencjaID;
    }

    public void setEvewidencjaID(Evewidencja evewidencjaID) {
        this.evewidencjaID = evewidencjaID;
    }

    public int getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(int kolejnosc) {
        this.kolejnosc = kolejnosc;
    }
    
    
    
}
