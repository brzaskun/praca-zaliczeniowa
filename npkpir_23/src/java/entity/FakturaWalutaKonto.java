/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Waluty;
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

/**
 *
 * @author Osito
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "FakturaWalutaKonto.findAll", query = "SELECT e FROM FakturaWalutaKonto e"),
    @NamedQuery(name = "FakturaWalutaKonto.findByPodatnik", query = "SELECT e FROM FakturaWalutaKonto e WHERE e.podatnik = :podatnik"),
    @NamedQuery(name = "FakturaWalutaKonto.findByPodatnikAktywne", query = "SELECT e FROM FakturaWalutaKonto e WHERE e.podatnik = :podatnik AND e.nieaktywny = '0'")
})
public class FakturaWalutaKonto  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @JoinColumn(name = "podatnik", referencedColumnName = "nip")
    @ManyToOne
    private Podatnik podatnik;
    @JoinColumn(name = "waluta", referencedColumnName = "idwaluty")
    @ManyToOne
    private Waluty waluta;
    @Column(name="nazwabanku")
    private  String nazwabanku;
    @Column(name="blz")
    private  String blz;
    @Column(name="swift")
    private  String swift;
    @Column(name="nrkonta")
    private  String nrkonta;
    @Column(name="iban")
    private  String iban;
    @Column(name="nieaktywny")
    private  boolean nieaktywny;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.podatnik);
        hash = 83 * hash + Objects.hashCode(this.waluta);
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
        final FakturaWalutaKonto other = (FakturaWalutaKonto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nrkonta, other.nrkonta)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        if (!Objects.equals(this.waluta, other.waluta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FakturaWalutaKonto{" + "podatnik=" + podatnik.getNazwapelna() + ", waluta=" + waluta.getSymbolwaluty() + ", nazwabanku=" + nazwabanku + ", blz=" + blz + ", swift=" + swift + ", nrkonta=" + nrkonta + ", nieaktywny=" + nieaktywny + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public Waluty getWaluta() {
        return waluta;
    }

    public void setWaluta(Waluty waluta) {
        this.waluta = waluta;
    }

    public String getNazwabanku() {
        return nazwabanku;
    }

    public void setNazwabanku(String nazwabanku) {
        this.nazwabanku = nazwabanku;
    }

    public String getBlz() {
        return blz;
    }

    public void setBlz(String blz) {
        this.blz = blz;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getNrkonta() {
        return nrkonta;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public boolean isNieaktywny() {
        return nieaktywny;
    }

    public void setNieaktywny(boolean nieaktywny) {
        this.nieaktywny = nieaktywny;
    }
    
    
    
}
