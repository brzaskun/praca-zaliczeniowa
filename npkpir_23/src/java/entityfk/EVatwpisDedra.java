/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.*;
import java.io.Serializable;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Osito
 */
@Named
@Entity
@NamedQueries({
    @NamedQuery(name = "EVatwpisDedra.findByPodatnik", query = "SELECT k FROM EVatwpisDedra k WHERE k.podatnikObj = :podatnik"),
    @NamedQuery(name = "EVatwpisDedra.findByPodatnikRok", query = "SELECT k FROM EVatwpisDedra k WHERE k.podatnikObj = :podatnik AND k.rokEw = :rok"),
    @NamedQuery(name = "EVatwpisDedra.findByPodatnikRokMc", query = "SELECT k FROM EVatwpisDedra k WHERE k.podatnikObj = :podatnik AND k.rokEw = :rok AND k.mcEw = :mc"),
    @NamedQuery(name = "EVatwpisDedra.findByMcRok", query = "SELECT k FROM EVatwpisDedra k WHERE k.rokEw = :rok AND k.mcEw = :mc"),
    @NamedQuery(name = "EVatwpisDedra.findByPodatnikRokMcSprzedaz", query = "SELECT k FROM EVatwpisDedra k WHERE k.podatnikObj = :podatnik AND k.rokEw = :rok AND k.mcEw = :mc AND k.ewidencja.nazwa !=:nazwa"),
    @NamedQuery(name = "EVatwpisDedra.findByPodatnikRokMcZakup", query = "SELECT k FROM EVatwpisDedra k WHERE k.podatnikObj = :podatnik AND k.rokEw = :rok AND k.mcEw = :mc AND k.ewidencja.nazwa=:nazwa"),
    @NamedQuery(name = "EVatwpisDedra.findByPodatnikRokInnyOkres", query = "SELECT k FROM EVatwpisDedra k WHERE k.podatnikObj = :podatnik AND k.rokEw = :rok AND k.innyokres != 0")
})
public class EVatwpisDedra extends EVatwpisSuper implements Serializable {
    private static final long serialVersionUID = 1L;
    private int lp;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnikObj;
    @Column(name = "nettowwalucie")
    private double nettowwalucie;
    @Column(name = "vatwwalucie")
    private double vatwwalucie;
    @Column(name = "brutto")
    private double brutto;
    @Column(name = "imienazwisko")
    private String imienazwisko;
    @Column(name = "faktura")
    private String faktura;
    @Column(name = "ulica")
    private String ulica;
    @Column(name = "miasto")
    private String miasto;
    @Column(name = "datadokumentu")
    private String datadokumentu;
    @Column(name = "dataoperacji")
    private String dataoperacji;
    @Column(name = "innyokres")
    private int innyokres;
    @JoinColumn(name = "klient", referencedColumnName = "id")
    @ManyToOne
    private Klienci klient;


    public EVatwpisDedra(double netto, double vat) {
        this.ulica = "podsumowanie";
        this.netto = netto;
        this.vat = vat;
    }
    
    

    public EVatwpisDedra(Evewidencja ewidencja, double netto, double vat, String estawka) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.brutto = netto + vat;
        this.estawka = estawka;
        this.innyokres = 0;
    }

    public EVatwpisDedra() {
        this.innyokres = 0;
    }
    
    //<editor-fold defaultstate="collapsed" desc="getters & setters">\

    public double getNettowwalucie() {
        return nettowwalucie;
    }

    public void setNettowwalucie(double nettowwalucie) {    
        this.nettowwalucie = nettowwalucie;
    }

    public double getVatwwalucie() {
        return vatwwalucie;
    }

    public void setVatwwalucie(double vatwwalucie) {
        this.vatwwalucie = vatwwalucie;
    }

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
    
    @Override
    public Evewidencja getEwidencja() {
        return ewidencja;
    }
    
    @Override
    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
    }
    
    @Override
    public String getEstawka() {
        return estawka;
    }
    
    public void setEstawka(String estawka) {
        this.estawka = estawka;
    }
    
      
    @Override
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public double getNetto() {
        return netto;
    }
    
    @Override
    public void setNetto(double netto) {
        this.netto = netto;
    }
    @Override
    public double getVat() {
        return vat;
    }
    @Override
    public void setVat(double vat) {
        this.vat = vat;
    }
    
    
    public String getDatadokumentu() {
        return datadokumentu;
    }
    
    public void setDatadokumentu(String datadokumentu) {
        this.datadokumentu = datadokumentu;
    }

    public int getInnyokres() {
        return innyokres;
    }

    public void setInnyokres(int innyokres) {
        this.innyokres = innyokres;
    }

    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
    }

    public String getMcEw() {
        return mcEw;
    }

    public void setMcEw(String mcEw) {
        this.mcEw = mcEw;
    }

    public String getRokEw() {
        return rokEw;
    }

    public void setRokEw(String rokEw) {
        this.rokEw = rokEw;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }
  
    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }

    public String getImienazwisko() {
        return imienazwisko;
    }

    public void setImienazwisko(String imienazwisko) {
        this.imienazwisko = imienazwisko;
    }

    public String getFaktura() {
        return faktura;
    }

    public void setFaktura(String faktura) {
        this.faktura = faktura;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }
 
    public String getDataoperacji() {
        return dataoperacji;
    }
    
    public void setDataoperacji(String dataoperacji) {
        this.dataoperacji = dataoperacji;
    }
//</editor-fold>

    public String getMcRok() {
        String zwrot = "";
        if (this.mcEw!=null) {
            zwrot = this.mcEw+"/"+this.rokEw;
        }
        return zwrot;
    }
    
    public String getAdres() {
        String zwrot = "";
        if (this.ulica != null && this.miasto != null) {
            zwrot = this.ulica+" "+this.miasto;
        } else {
            zwrot = this.ulica;
        }
        return zwrot;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 23 * hash + Objects.hashCode(this.ewidencja);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.netto) ^ (Double.doubleToLongBits(this.netto) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.vat) ^ (Double.doubleToLongBits(this.vat) >>> 32));
        hash = 23 * hash + Objects.hashCode(this.estawka);
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
        final EVatwpisDedra other = (EVatwpisDedra) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.netto) != Double.doubleToLongBits(other.netto)) {
            return false;
        }
        if (Double.doubleToLongBits(this.vat) != Double.doubleToLongBits(other.vat)) {
            return false;
        }
        if (!Objects.equals(this.estawka, other.estawka)) {
            return false;
        }
        if (!Objects.equals(this.ewidencja, other.ewidencja)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EVatwpisDedra{" + "lp=" + lp + ", ewidencja=" + ewidencja + ", netto=" + netto + ", vat=" + vat + ", brutto=" + brutto + ", estawka=" + estawka + ", imienazwisko=" + imienazwisko + ", faktura=" + faktura + ", ulica=" + ulica + ", miasto=" + miasto + ", datadokumentu=" + datadokumentu + ", dataoperacji=" + dataoperacji + ", innyokres=" + innyokres + ", mcEw=" + mcEw + ", rokEw=" + rokEw + '}';
    }

   


     
    
   
    
}


