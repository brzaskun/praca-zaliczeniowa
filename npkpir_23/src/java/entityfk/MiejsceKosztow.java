/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.Podatnik;
import java.io.Serializable;
import javax.persistence.Cacheable;
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
    @NamedQuery(name = "MiejsceKosztow.findAll", query = "SELECT m FROM MiejsceKosztow m"),
    @NamedQuery(name = "MiejsceKosztow.findById", query = "SELECT m FROM MiejsceKosztow m WHERE m.id = :id"),
    @NamedQuery(name = "MiejsceKosztow.findByAktywny", query = "SELECT m FROM MiejsceKosztow m WHERE m.aktywny = :aktywny"),
    @NamedQuery(name = "MiejsceKosztow.findByOpismiejsca", query = "SELECT m FROM MiejsceKosztow m WHERE m.opismiejsca = :opismiejsca"),
    @NamedQuery(name = "MiejsceKosztow.findByPodatnik", query = "SELECT m FROM MiejsceKosztow m WHERE m.podatnikObj = :podatnik AND m.pokaz0chowaj1 = 0 ORDER BY m.opismiejsca"),
    @NamedQuery(name = "MiejsceKosztow.findByPodatnikWszystkie", query = "SELECT m FROM MiejsceKosztow m WHERE m.podatnikObj = :podatnik ORDER BY m.opismiejsca"),
    @NamedQuery(name = "MiejsceKosztow.countByPodatnik", query = "SELECT COUNT(d) FROM MiejsceKosztow d WHERE d.podatnikObj = :podatnik AND d.pokaz0chowaj1 = 0"),
})
@Cacheable
public class MiejsceKosztow extends MiejsceSuper implements Serializable {
        
    public MiejsceKosztow() {
    }

    public MiejsceKosztow(int id) {
        this.id = id;
    }

    public MiejsceKosztow(int id, boolean aktywny, String opismiejsca) {
        this.id = id;
        this.aktywny = aktywny;
        this.opismiejsca = opismiejsca;
    }

    public void uzupelnij(Podatnik podatnik, String numer, int rok) {
        this.podatnikObj = podatnik;
        this.nrkonta = numer;
        this.rok = rok;
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
        final MiejsceKosztow other = (MiejsceKosztow) obj;
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
