/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.inject.Named;
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
import javax.persistence.OneToMany;

/**
 *
 * @author Osito
 */
@Named
@Entity
public class KwotaKolumna1 implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    private double netto;
    private double nettowaluta;
    private double vat;
    private double vatodliczony;
    private double vatkoszt;
    private double vatwaluta;
    private double brutto;
    private String nazwakolumny;

    @JoinColumn(name = "dok", referencedColumnName = "id_dok")
    @ManyToOne(cascade = CascadeType.ALL)
    private Dok dok;
    @JoinColumn(name = "kwotakolumna1")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,  orphanRemoval=true)
    private List<Kolumna1Rozbicie> listaKolumna1Rozbicie;

    public KwotaKolumna1() {
        this.netto = 0.0;
        this.nettowaluta = 0.0;
        this.nazwakolumny = "";
        this.listaKolumna1Rozbicie = Collections.synchronizedList(new ArrayList<>());
    }

    public KwotaKolumna1(double kwota, String nazwakolumny) {
        this.netto = kwota;
        this.nettowaluta = 0.0;
        this.nazwakolumny = nazwakolumny;
        this.listaKolumna1Rozbicie = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.netto) ^ (Double.doubleToLongBits(this.netto) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.vat) ^ (Double.doubleToLongBits(this.vat) >>> 32));
        hash = 41 * hash + Objects.hashCode(this.dok);
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
        final KwotaKolumna1 other = (KwotaKolumna1) obj;
        if (Double.doubleToLongBits(this.netto) != Double.doubleToLongBits(other.netto)) {
            return false;
        }
        if (Double.doubleToLongBits(this.vat) != Double.doubleToLongBits(other.vat)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dok, other.dok)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "KwotaKolumna1{" + "netto=" + netto + ", vat=" + vat + ", brutto=" + brutto + ", nazwakolumny=" + nazwakolumny + ", dok=" + dok + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getNettowaluta() {
        return nettowaluta;
    }

    public void setNettowaluta(double nettowaluta) {
        this.nettowaluta = nettowaluta;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getVatodliczony() {
        return vatodliczony;
    }

    public void setVatodliczony(double vatodliczony) {
        this.vatodliczony = vatodliczony;
    }

    public double getVatkoszt() {
        return vatkoszt;
    }

    public void setVatkoszt(double vatkoszt) {
        this.vatkoszt = vatkoszt;
    }

    public double getVatwaluta() {
        return vatwaluta;
    }

    public void setVatwaluta(double vatwaluta) {
        this.vatwaluta = vatwaluta;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public String getNazwakolumny() {
        return nazwakolumny;
    }

    public void setNazwakolumny(String nazwakolumny) {
        this.nazwakolumny = nazwakolumny;
    }

    public Dok getDok() {
        return dok;
    }

    public void setDok(Dok dok) {
        this.dok = dok;
    }

    public List<Kolumna1Rozbicie> getListaKolumna1Rozbicie() {
        return listaKolumna1Rozbicie;
    }

    public void setListaKolumna1Rozbicie(List<Kolumna1Rozbicie> listaKolumna1Rozbicie) {
        this.listaKolumna1Rozbicie = listaKolumna1Rozbicie;
    }

    
  
    
    
    
}
