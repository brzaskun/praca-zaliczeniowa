/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
    private long id;
    private Double netto;
    private Double vat;
    private Double brutto;
    private String nazwakolumny;
    private String dowykorzystania;
    @JoinColumn(name = "dok", referencedColumnName = "id_dok")
    @ManyToOne(cascade = CascadeType.ALL)
    private Dok dok;

    public KwotaKolumna1() {
        this.netto = 0.0;
        this.nazwakolumny = "";
    }

    public KwotaKolumna1(double kwota, String nazwakolumny) {
        this.netto = kwota;
        this.nazwakolumny = nazwakolumny;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 29 * hash + Objects.hashCode(this.dok);
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
        final KwotaKolumna1 other = (KwotaKolumna1) obj;
        if (this.id != other.id) {
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

    
    
    
    public Double getNetto() {
        return netto;
    }

    public void setNetto(Double netto) {
        this.netto = netto;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getBrutto() {
        return brutto;
    }

    public void setBrutto(Double brutto) {
        this.brutto = brutto;
    }
    
    public String getNazwakolumny() {
        return nazwakolumny;
    }

    public void setNazwakolumny(String nazwakolumny) {
        this.nazwakolumny = nazwakolumny;
    }

    public String getDowykorzystania() {
        return dowykorzystania;
    }

    public void setDowykorzystania(String dowykorzystania) {
        this.dowykorzystania = dowykorzystania;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dok getDok() {
        return dok;
    }

    public void setDok(Dok dok) {
        this.dok = dok;
    }

    
    
}
