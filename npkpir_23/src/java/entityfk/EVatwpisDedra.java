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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

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
    @NamedQuery(name = "EVatwpisDedra.findByPodatnik", query = "SELECT k FROM EVatwpisDedra k WHERE k.podatnikObj = :podatnik"),
    @NamedQuery(name = "EVatwpisDedra.findByPodatnikRok", query = "SELECT k FROM EVatwpisDedra k WHERE k.podatnikObj = :podatnik AND k.rokEw = :rok"),
    @NamedQuery(name = "EVatwpisDedra.findByPodatnikRokMc", query = "SELECT k FROM EVatwpisDedra k WHERE k.podatnikObj = :podatnik AND k.rokEw = :rok AND k.mcEw = :mc"),
    @NamedQuery(name = "EVatwpisDedra.findByPodatnikRokInnyOkres", query = "SELECT k FROM EVatwpisDedra k WHERE k.podatnikObj = :podatnik AND k.rokEw = :rok AND k.innyokres != 0")
})
public class EVatwpisDedra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    private int lp;
    @JoinColumn(name = "ewidencja", referencedColumnName = "nazwa")
    @ManyToOne
    private Evewidencja ewidencja;
    @JoinColumn(name = "podatnikObj", referencedColumnName = "nip")
    private Podatnik podatnikObj;
    @Column(name = "netto")
    private double netto;
    @Column(name = "nettowwalucie")
    private double nettowwalucie;
    @Column(name = "vat")
    private double vat;
    @Column(name = "vatwwalucie")
    private double vatwwalucie;
    @Column(name = "brutto")
    private double brutto;
    @Column(name = "estawka")
    private String estawka;
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
    @Size(max = 2)
    @Column(name = "mcEw")
    private String mcEw;
    @Size(max = 4)
    @Column(name = "rokEw")
    private String rokEw;

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
    
      
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
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

    public String getMcRok() {
        String zwrot = "";
        if (this.mcEw!=null) {
            zwrot = this.mcEw+"/"+this.rokEw;
        }
        return zwrot;
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


