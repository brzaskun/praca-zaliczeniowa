/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
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
@Cacheable
@NamedQueries({
    @NamedQuery(name = "EVatwpis1.findByRokKW", query = "SELECT d FROM EVatwpis1 d WHERE d.rokEw = :pkpirR AND d.dok.podatnik = :podatnik AND (d.mcEw = :mc1 OR d.mcEw = :mc2 OR d.mcEw = :mc3)"),
    @NamedQuery(name = "EVatwpis1.findByRokMc", query = "SELECT d FROM EVatwpis1 d WHERE d.rokEw = :pkpirR AND d.dok.podatnik = :podatnik AND d.mcEw = :mc"),
    @NamedQuery(name = "EVatwpis1.findByRok", query = "SELECT d FROM EVatwpis1 d WHERE d.rokEw = :rok"),
    @NamedQuery(name = "EVatwpis1.findByMcRok", query = "SELECT d FROM EVatwpis1 d WHERE d.rokEw = :rok AND d.mcEw = :mc"),
    @NamedQuery(name = "EVatwpis1.findByRokMcKasowe", query = "SELECT d FROM EVatwpis1 d WHERE d.rokEw = :pkpirR AND d.dok.podatnik = :podatnik AND d.mcEw = :mc AND d.dok.rozliczony = '1'"),
    @NamedQuery(name = "EVatwpis1.findByRokKWKasowe", query = "SELECT d FROM EVatwpis1 d WHERE d.rokEw = :pkpirR AND d.dok.podatnik = :podatnik AND (d.mcEw = :mc1 OR d.mcEw = :mc2 OR d.mcEw = :mc3) AND d.dok.rozliczony = '1'")
})
public class EVatwpis1 extends EVatwpisSuper implements Serializable {
    private static final long serialVersionUID = -3291613981355945492L;
    
    @JoinColumn(name = "dok", referencedColumnName = "id_dok")
    @ManyToOne(cascade = CascadeType.ALL)
    private Dok dok;

    
    public EVatwpis1(EVatwpis1 wiersz) {
        super(wiersz);
        this.dok = wiersz.dok;
    }
    
     
    

    public EVatwpis1(Evewidencja ewidencja, double netto, double vat, String estawka, String mcEw, String rokEw) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
        this.mcEw = mcEw;
        this.rokEw = rokEw;
    }
    
    public EVatwpis1(Evewidencja ewidencja, double netto, double vat, String estawka, Dok dok) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
        this.dok = dok;
    }

    public EVatwpis1() {
    }

    @Override
    public Evewidencja getEwidencja() {
        return ewidencja;
    }

    @Override
    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
    }

//    @Override
//    public Evewidencja getEwidencjaID() {
//        return ewidencjaID;
//    }
//
//    @Override
//    public void setEwidencjaID(Evewidencja ewidencjaID) {
//        this.ewidencjaID = ewidencjaID;
//    }

  
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

    public String getEstawka() {
        return estawka;
    }

    public void setEstawka(String estawka) {
        this.estawka = estawka;
    }

    public Dok getDok() {
        return dok;
    }

    public void setDok(Dok dok) {
        this.dok = dok;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.ewidencja);
        hash = 17 * hash + Objects.hashCode(this.dok);
        return hash;
    }

    @Override
    public String toString() {
        return "EVatwpis1{"+ super.toString() + "dok=" + dok.toString2() + '}';
    }

    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EVatwpis1 other = (EVatwpis1) obj;
        if (!Objects.equals(this.ewidencja, other.ewidencja)) {
            return false;
        }
        if (!Objects.equals(this.dok, other.dok)) {
            return false;
        }
        return true;
    }

    @Override
   public String getDataSprz() {
      String zwrot = this.getDok() != null ? this.getDok().getDataSprz() : "";
      return zwrot;
    }
    
    @Override
    public String getDataWyst() {
      String zwrot = this.getDok() != null ? this.getDok().getDataWyst() : "";
      return zwrot;
    }
    
    @Override
    public Klienci getKontr() {
      Klienci zwrot = this.getDok() != null ? this.getDok().getKontr() : new Klienci();
      return zwrot;
    }
    
    @Override
    public String getNrKolejny() {
      String zwrot = this.getDok() != null ? this.getDok().getRodzajedok().getSkrot()+"/"+this.getDok().getIdDok()+"/"+this.getDok().getPkpirR() :"";
      return zwrot;
    }
    
    @Override
    public String getNrWlDk() {
      return this.getDok() != null ? this.getDok().getNrWlDk() :"";
    }
    
    @Override
    public String getOpis() {
      String zwrot = this.getDok() != null ? this.getDok().getOpis() : "podsumowanie";
      return zwrot;
    }
    
    @Override
    public double getProcentvat() {
        return this.getDok().getRodzajedok().getProcentvat();
    }
    
    @Override
    public Evewidencja getNazwaewidencji() {
        return this.getEwidencja();
    }
    
    public String getNrpolanetto() {
        return this.getEwidencja().getNrpolanetto();
    }

    public String getNrpolavat() {
        return this.getEwidencja().getNrpolavat();
    }
    
    public String getOpiszw() {
        return this.getEstawka();
    }
    
    @Override
    public String getInnymc(){
        return this.getDok().getVatM();
    }
    
    @Override
    public String getInnyrok(){
        return this.getDok().getVatR();
    }
    
    @Override
    public String getDataoperacji() {
        return this.dok.getDataSprz();
    }
    
    @Override
    public void setNazwaewidencji(Evewidencja object) {
        super.ewidencja = object;
    }

   
    @Override
    public void setDuplikat(boolean duplikat) {
        super.duplikat = duplikat;
    }
    
    @Override
    public boolean isDuplikat() {
        return super.duplikat;
    }

    @Override
    public boolean isNieduplikuj() {
        return nieduplikuj;
    }

    @Override
    public void setNieduplikuj(boolean nieduplikuj) {
        this.nieduplikuj = nieduplikuj;
    }
    
    
}


