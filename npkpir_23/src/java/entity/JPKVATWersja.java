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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "JPKVATWersja.usunliste", query = "DELETE FROM JPKVATWersja p WHERE p.nazwa = :nazwa")
})
public class JPKVATWersja implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "rokOd")
    private String rokOd;
    @Column(name = "mcOd")
    private String mcOd;
    @Column(name = "nazwa")
    private String nazwa;
    @Column(name = "mc0kw1")
    private boolean mc0kw1;
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
    @JoinColumn(name = "evewidencja",referencedColumnName = "nazwa")
    @ManyToOne
    private Evewidencja evewidencja;

    public JPKVATWersja() {
    }

    public JPKVATWersja(JPKVATWersja dk) {
        this.rokOd = dk.rokOd;
        this.mcOd = dk.mcOd;
        this.nazwa = dk.nazwa;
        this.mc0kw1 = dk.mc0kw1;
    }

        
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.nazwa);
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
        final JPKVATWersja other = (JPKVATWersja) obj;
        if (!Objects.equals(this.nazwa, other.nazwa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeklaracjaVatSchema{" + "rokOd=" + rokOd + ", mcOd=" + mcOd + ", nazwa=" + nazwa + ", mc0kw1=" + mc0kw1 + '}';
    }
  
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getRokOd() {
        return rokOd;
    }
    
    public void setRokOd(String rokOd) {
        this.rokOd = rokOd;
    }
    
    
    public String getMcOd() {
        return mcOd;
    }
    
    public void setMcOd(String mcOd) {
        this.mcOd = mcOd;
    }
    
    
    public String getNazwa() {
        return nazwa;
    }
    
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
       
    public boolean isMc0kw1() {
        return mc0kw1;
    }
    
    public void setMc0kw1(boolean mc0kw1) {
        this.mc0kw1 = mc0kw1;
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
    
  
    public Evewidencja getEvewidencja() {
        return evewidencja;
    }

    public void setEvewidencja(Evewidencja evewidencja) {
        this.evewidencja = evewidencja;
    }
    
    //</editor-fold>

    
}
