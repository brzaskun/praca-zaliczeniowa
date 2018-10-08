/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Dokfk;
import entityfk.Waluty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */

@MappedSuperclass
public class VatSuper implements Serializable{
    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected long id;
    @Column(name = "transakcja")
    protected String transakcja;
    @JoinColumn(name = "kontrahent", referencedColumnName = "id")
    @OneToOne
    protected Klienci kontrahent;
    @Column(name = "netto")
    protected Double netto;
    @Column(name = "nettoprzedkorekta")
    protected Double nettoprzedkorekta;
    @Column(name = "nettowaluta")
    protected double nettowaluta;
    @Column(name = "liczbadok")
    protected int liczbadok;
    @JoinColumn(name = "waluta", referencedColumnName = "idwaluty")
    @ManyToOne
    protected Waluty nazwawaluty;
    @Size(max = 2)
    @Column(name = "mc")
    protected String mc;
    @Size(max = 4)
    @Column(name = "rok")
    protected String rok;
    
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vatUe")
    protected List<Dok> zawiera;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vatUe")
    protected List<Dokfk> zawierafk;
    
    @Column(name = "korekta")
    protected boolean korekta;

    public VatSuper() {
        this.zawiera = Collections.synchronizedList(new ArrayList<>());
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }


    public VatSuper(String transakcja, Klienci kontrahent, Double netto, int liczbadok, List<Dok> zawiera) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.liczbadok = liczbadok;
        this.zawiera = zawiera;
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }
    
    public VatSuper(String transakcja, Klienci kontrahent, double netto, double nettowal) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.nettowaluta = nettowal;
        this.zawiera = Collections.synchronizedList(new ArrayList<>());
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }
    
    public VatSuper(String transakcja, Klienci kontrahent, Double netto, int liczbadok) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.liczbadok = liczbadok;
        this.zawiera = Collections.synchronizedList(new ArrayList<>());
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }


     public String getTransakcja() {
        return transakcja;
    }
    
    public void setTransakcja(String transakcja) {
        this.transakcja = transakcja;
    }

    public Klienci getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(Klienci kontrahent) {
        this.kontrahent = kontrahent;
    }

    public Double getNettoprzedkorekta() {
        return nettoprzedkorekta;
    }

    public void setNettoprzedkorekta(Double nettoprzedkorekta) {
        this.nettoprzedkorekta = nettoprzedkorekta;
    }

    public Double getNetto() {
        return netto;
    }

    public void setNetto(Double netto) {
        this.netto = netto;
    }

  
    public int getLiczbadok() {
        return liczbadok;
    }

    public void setLiczbadok(int liczbadok) {
        this.liczbadok = liczbadok;
    }

    public List<Dok> getZawiera() {
        if (this instanceof entity.VatUe)  {
            return ((entity.VatUe)this).getZawiera();
        } else {
            return null;
        }
    }

    public List<Dokfk> getZawierafk() {
        if (this instanceof entity.VatUe)  {
            return ((entity.VatUe)this).getZawierafk();
        } else {
            return null;
        }
    }


    public double getNettowaluta() {
        return nettowaluta;
    }

    public void setNettowaluta(double nettowaluta) {
        this.nettowaluta = nettowaluta;
    }

    public boolean isKorekta() {
        return korekta;
    }

    public void setKorekta(boolean korekta) {
        this.korekta = korekta;
    }

    public Waluty getNazwawaluty() {
        return nazwawaluty;
    }

    public void setNazwawaluty(Waluty nazwawaluty) {
        this.nazwawaluty = nazwawaluty;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 83 * hash + Objects.hashCode(this.transakcja);
        hash = 83 * hash + Objects.hashCode(this.kontrahent);
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
        final VatSuper other = (VatSuper) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.transakcja, other.transakcja)) {
            return false;
        }
        if (!Objects.equals(this.kontrahent, other.kontrahent)) {
            return false;
        }
        return true;
    }

    
    
    

    @Override
    public String toString() {
        return "VatUe{" + "transakcja=" + transakcja + ", kontrahent=" + kontrahent.getNpelna() + ", netto=" + netto + ", nettowaluta=" + nettowaluta + ", liczbadok=" + liczbadok + ", nazwawaluty=" + nazwawaluty + ", zawiera=" + zawiera + ", zawierafk=" + zawierafk + '}';
    }

  
    
}
