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

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podsumowanieamazonoss")
@NamedQueries({
        @NamedQuery(name = "PodsumowanieAmazonOSS.findByRok", query = "SELECT d FROM PodsumowanieAmazonOSS d WHERE d.rok = :rok"),
        @NamedQuery(name = "PodsumowanieAmazonOSS.findByPodatnikRokMc", query = "SELECT p FROM PodsumowanieAmazonOSS p WHERE p.podatnik = :podatnik AND  p.rok = :rok AND p.mc = :mc"),
        @NamedQuery(name = "PodsumowanieAmazonOSS.DeleteByPodatnikRokMc", query = "DELETE FROM PodsumowanieAmazonOSS p WHERE p.podatnik = :podatnik AND  p.rok = :rok AND p.mc = :mc")
    })
public class PodsumowanieAmazonOSS  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "jurysdykcja")
    private String jurysdykcja;
    @Column(name = "waluta")
    private String waluta;
    @Column(name = "nettowaluta")
    private double nettowaluta;
    @Column(name = "vatwaluta")
    private double vatwaluta;
    @Column(name = "netto")
    private double netto;
    @Column(name = "vat")
    private double vat;
    @Column(name = "vatstawka")
    private double vatstawka;
    @Column(name = "kurs")
    private double kurs;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @Column(name = "rok")
    private String rok;
    @Column(name = "mc")
    private String mc;
    @Column(name = "ujete")
    private boolean ujete;

    public PodsumowanieAmazonOSS() {
    }

    public PodsumowanieAmazonOSS(String jurysdykcja, String waluta, double nettowaluta, double vatwaluta, double netto, double vat, double vatstawka, double kurs, Podatnik podatnik, String rok, String mc) {
        this.jurysdykcja = jurysdykcja;
        this.waluta = waluta;
        this.nettowaluta = nettowaluta;
        this.vatwaluta = vatwaluta;
        this.netto = netto;
        this.vat = vat;
        this.vatstawka = vatstawka;
        this.kurs = kurs;
        this.podatnik = podatnik;
        this.rok = rok;
        this.mc = mc;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.jurysdykcja);
        hash = 29 * hash + Objects.hashCode(this.podatnik);
        hash = 29 * hash + Objects.hashCode(this.rok);
        hash = 29 * hash + Objects.hashCode(this.mc);
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
        final PodsumowanieAmazonOSS other = (PodsumowanieAmazonOSS) obj;
        if (!Objects.equals(this.jurysdykcja, other.jurysdykcja)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PodsumowanieAmazonOSS{" + "jurysdykcja=" + jurysdykcja + ", waluta=" + waluta + ", nettowaluta=" + nettowaluta + ", vatwaluta=" + vatwaluta + ", netto=" + netto + ", vat=" + vat + ", vatstawka=" + vatstawka + ", kurs=" + kurs + ", podatnik=" + podatnik.getPrintnazwa() + ", rok=" + rok + ", mc=" + mc + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJurysdykcja() {
        return jurysdykcja;
    }

    public void setJurysdykcja(String jurysdykcja) {
        this.jurysdykcja = jurysdykcja;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public double getNettowaluta() {
        return nettowaluta;
    }

    public void setNettowaluta(double nettowaluta) {
        this.nettowaluta = nettowaluta;
    }

    public double getVatwaluta() {
        return vatwaluta;
    }

    public void setVatwaluta(double vatwaluta) {
        this.vatwaluta = vatwaluta;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getVatstawka() {
        return vatstawka;
    }

    public void setVatstawka(double vatstawka) {
        this.vatstawka = vatstawka;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public boolean isUjete() {
        return ujete;
    }

    public void setUjete(boolean ujete) {
        this.ujete = ujete;
    }
    
    
    
    
}
