/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import entity.Podatnik;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podid", "opismiejsca", "rok"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MiejscePrzychodow.findAll", query = "SELECT m FROM MiejscePrzychodow m"),
    @NamedQuery(name = "MiejscePrzychodow.findById", query = "SELECT m FROM MiejscePrzychodow m WHERE m.id = :id"),
    @NamedQuery(name = "MiejscePrzychodow.findByAktywny", query = "SELECT m FROM MiejscePrzychodow m WHERE m.aktywny = :aktywny"),
    @NamedQuery(name = "MiejscePrzychodow.findCzlonekStowarzyszenia", query = "SELECT m FROM MiejscePrzychodow m WHERE  m.podatnikObj = :podatnik AND m.poczatek IS NOT NULL ORDER BY m.opismiejsca"),
    @NamedQuery(name = "MiejscePrzychodow.findByOpismiejsca", query = "SELECT m FROM MiejscePrzychodow m WHERE m.opismiejsca = :opismiejsca"),
    @NamedQuery(name = "MiejscePrzychodow.findByPodatnik", query = "SELECT m FROM MiejscePrzychodow m WHERE m.podatnikObj = :podatnik AND m.pokaz0chowaj1 = 0 ORDER BY m.opismiejsca"),
    @NamedQuery(name = "MiejscePrzychodow.findByPodatnikWszystkie", query = "SELECT m FROM MiejscePrzychodow m WHERE m.podatnikObj = :podatnik ORDER BY m.opismiejsca"),
    @NamedQuery(name = "MiejscePrzychodow.findByPodatnikRok", query = "SELECT m FROM MiejscePrzychodow m WHERE m.podatnikObj = :podatnik AND m.rok = :rok ORDER BY m.opismiejsca"),
})
@Cacheable
public class MiejscePrzychodow extends MiejsceSuper implements Serializable {
    protected static final long serialVersionUID = 1L;
    @Column(name = "poczatek")
    private String poczatek;
    @Column(name = "koniec")
    private String koniec;
    @Column(name = "email")
    private String email;
    @Column(name = "nrlegitymacji")
    private String nrlegitymacji;

    public MiejscePrzychodow() {
    }

    public MiejscePrzychodow(int id) {
        this.id = id;
    }

    public MiejscePrzychodow(int id, boolean aktywny, String opismiejsca, int rok) {
        this.id = id;
        this.aktywny = aktywny;
        this.opismiejsca = opismiejsca;
        this.rok = rok;
    }
    
    public void uzupelnij(Podatnik podatnik, String numer) {
        this.podatnikObj = podatnik;
        this.nrkonta = numer;
    }

    
    public String getPoczatek() {
        return poczatek;
    }

    public void setPoczatek(String poczatek) {
        this.poczatek = poczatek;
    }

    public String getKoniec() {
        return koniec;
    }

    public void setKoniec(String koniec) {
        this.koniec = koniec;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNrlegitymacji() {
        return nrlegitymacji;
    }

    public void setNrlegitymacji(String nrlegitymacji) {
        this.nrlegitymacji = nrlegitymacji;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
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
        final MiejscePrzychodow other = (MiejscePrzychodow) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return opismiejsca;
    }

  
    
}
