/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.*;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Named
@Entity
@Table(catalog = "pkpir", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ewidencja", "podatnikObj", "rok","nrkolejnywserii","seriadokfk", "WIERSZ_idwiersza", "KLIENT_id"})
})
@NamedQueries({
    @NamedQuery(name = "EVatwpisFK.findByWiersz", query = "SELECT k FROM EVatwpisFK k WHERE k.wiersz = :wiersz")
})
public class EVatwpisFK implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    private int lp;
    @JoinColumn(name = "ewidencja")
    @ManyToOne
    private Evewidencja ewidencja;
    @Column(name = "netto")
    private double netto;
    @Column(name = "vat")
    private double vat;
    @Column(name = "brutto")
    private double brutto;
    @Column(name = "estawka")
    private String estawka;
    @ManyToOne(cascade = CascadeType.ALL)
    private Dokfk dokfk;
    @OneToOne
    private Wiersz wiersz;
    private Klienci klient;
    @Column(name = "datadokumentu")
    private String datadokumentu;
    @Column(name = "dataoperacji")
    private String dataoperacji;
    
    

    public EVatwpisFK(Evewidencja ewidencja, double netto, double vat, String estawka) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
    }

    public EVatwpisFK() {
    }

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }
    
    
    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }
    
    
    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
    }

    public Evewidencja getEwidencja() {
        return ewidencja;
    }
    
    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
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
    
    public String getEstawka() {
        return estawka;
    }
    
    public void setEstawka(String estawka) {
        this.estawka = estawka;
    }
    
    public Dokfk getDokfk() {
        return dokfk;
    }
    
    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Wiersz getWiersz() {
        return wiersz;
    }
    
    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }
    
    public String getDatadokumentu() {
        return datadokumentu;
    }
    
    public void setDatadokumentu(String datadokumentu) {
        this.datadokumentu = datadokumentu;
    }
    
    public String getDataoperacji() {
        return dataoperacji;
    }
    
    public void setDataoperacji(String dataoperacji) {
        this.dataoperacji = dataoperacji;
    }
//</editor-fold>
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.ewidencja);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.netto) ^ (Double.doubleToLongBits(this.netto) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.vat) ^ (Double.doubleToLongBits(this.vat) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.estawka);
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
        final EVatwpisFK other = (EVatwpisFK) obj;
        if (!Objects.equals(this.ewidencja, other.ewidencja)) {
            return false;
        }
        if (!Objects.equals(this.dokfk, other.dokfk)) {
            return false;
        }
        if (!Objects.equals(this.wiersz, other.wiersz)) {
            return false;
        }
        if (!Objects.equals(this.klient, other.klient)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EVatwpisFK{" + "ewidencja=" + ewidencja + ", netto=" + netto + ", vat=" + vat + ", dokfk=" + dokfk + ", wiersz=" + wiersz + ", klient=" + klient + '}';
    }

    

    
   
    
}


