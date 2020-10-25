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
    @UniqueConstraint(columnNames={"nazwa"})
})
@NamedQueries({
    @NamedQuery(name = "JPKvatwersjaEvewidencja.findyByJPKvatwersja", query = "SELECT i FROM JPKvatwersjaEvewidencja i WHERE i.jpkvatwersja = :jpkvatwersja")
})
public class JPKvatwersjaEvewidencja implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "evewidencja", referencedColumnName = "id")
    private Evewidencja evewidencja;
    @JoinColumn(name = "jpkvatwersja", referencedColumnName = "id")
    private JPKVATWersja jpkvatwersja;
    @Column(name = "polejpk_netto_sprzedaz")
    private String polejpk_netto_sprzedaz;
    @Column(name = "polejpk_vat_sprzedaz")
    private String polejpk_vat_sprzedaz;
    @Column(name = "polejpk_netto_sprzedaz_suma")
    private String polejpk_netto_sprzedaz_suma;
    @Column(name = "polejpk_vat_sprzedaz_suma")
    private String polejpk_vat_sprzedaz_suma;
    @Column(name = "polejpk_netto_zakup")
    private String polejpk_netto_zakup;
    @Column(name = "polejpk_vat_zakup")
    private String polejpk_vat_zakup;

    public JPKvatwersjaEvewidencja() {
    }

    public JPKvatwersjaEvewidencja(JPKVATWersja selected, Evewidencja s) {
        this.jpkvatwersja = selected;
        this.evewidencja = s;
    }

    public JPKvatwersjaEvewidencja(JPKvatwersjaEvewidencja p, JPKVATWersja jPKVATWersja) {
        this.evewidencja = p.getEvewidencja();
        this.jpkvatwersja = jPKVATWersja;
        this.polejpk_netto_sprzedaz = p.getPolejpk_netto_sprzedaz();
        this.polejpk_vat_sprzedaz = p.getPolejpk_vat_sprzedaz();
        this.polejpk_netto_sprzedaz_suma = p.getPolejpk_netto_sprzedaz_suma();
        this.polejpk_vat_sprzedaz_suma = p.getPolejpk_vat_sprzedaz_suma();
        this.polejpk_netto_zakup = p.getPolejpk_netto_zakup();
        this.polejpk_vat_zakup = p.getPolejpk_vat_zakup();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Evewidencja getEvewidencja() {
        return evewidencja;
    }

    public void setEvewidencja(Evewidencja evewidencja) {
        this.evewidencja = evewidencja;
    }

    public JPKVATWersja getJpkvatwersja() {
        return jpkvatwersja;
    }

    public void setJpkvatwersja(JPKVATWersja jpkvatwersja) {
        this.jpkvatwersja = jpkvatwersja;
    }

    public String getPolejpk_netto_sprzedaz() {
        return polejpk_netto_sprzedaz;
    }

    public void setPolejpk_netto_sprzedaz(String polejpk_netto_sprzedaz) {
        this.polejpk_netto_sprzedaz = polejpk_netto_sprzedaz;
    }

    public String getPolejpk_vat_sprzedaz() {
        return polejpk_vat_sprzedaz;
    }

    public void setPolejpk_vat_sprzedaz(String polejpk_vat_sprzedaz) {
        this.polejpk_vat_sprzedaz = polejpk_vat_sprzedaz;
    }

    public String getPolejpk_netto_sprzedaz_suma() {
        return polejpk_netto_sprzedaz_suma;
    }

    public void setPolejpk_netto_sprzedaz_suma(String polejpk_netto_sprzedaz_suma) {
        this.polejpk_netto_sprzedaz_suma = polejpk_netto_sprzedaz_suma;
    }

    public String getPolejpk_vat_sprzedaz_suma() {
        return polejpk_vat_sprzedaz_suma;
    }

    public void setPolejpk_vat_sprzedaz_suma(String polejpk_vat_sprzedaz_suma) {
        this.polejpk_vat_sprzedaz_suma = polejpk_vat_sprzedaz_suma;
    }

    public String getPolejpk_netto_zakup() {
        return polejpk_netto_zakup;
    }

    public void setPolejpk_netto_zakup(String polejpk_netto_zakup) {
        this.polejpk_netto_zakup = polejpk_netto_zakup;
    }

    public String getPolejpk_vat_zakup() {
        return polejpk_vat_zakup;
    }

    public void setPolejpk_vat_zakup(String polejpk_vat_zakup) {
        this.polejpk_vat_zakup = polejpk_vat_zakup;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.evewidencja);
        hash = 37 * hash + Objects.hashCode(this.jpkvatwersja);
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
        final JPKvatwersjaEvewidencja other = (JPKvatwersjaEvewidencja) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.evewidencja, other.evewidencja)) {
            return false;
        }
        if (!Objects.equals(this.jpkvatwersja, other.jpkvatwersja)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPKvatwersjaEvewidencja{" + "evewidencja=" + evewidencja.getNazwa() + ", jpkvatwersja=" + jpkvatwersja.getNazwa() + ", polejpk_netto_sprzedaz=" + polejpk_netto_sprzedaz + ", polejpk_vat_sprzedaz=" + polejpk_vat_sprzedaz + ", polejpk_netto_sprzedaz_suma=" + polejpk_netto_sprzedaz_suma + ", polejpk_vat_sprzedaz_suma=" + polejpk_vat_sprzedaz_suma + ", polejpk_netto_zakup=" + polejpk_netto_zakup + ", polejpk_vat_zakup=" + polejpk_vat_zakup + '}';
    }

    
    
    

    
}
