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
    @NamedQuery(name = "EVatwpisKJPK.findByRokKW", query = "SELECT d FROM EVatwpisKJPK d WHERE d.rokEw = :pkpirR AND d.klientJPK.podatnik = :podatnik AND (d.mcEw = :mc1 OR d.mcEw = :mc2 OR d.mcEw = :mc3)"),
    @NamedQuery(name = "EVatwpisKJPK.findByRokMc", query = "SELECT d FROM EVatwpisKJPK d WHERE d.rokEw = :pkpirR AND d.klientJPK.podatnik = :podatnik AND d.mcEw = :mc"),
    @NamedQuery(name = "EVatwpisKJPK.findByRok", query = "SELECT d FROM EVatwpisKJPK d WHERE d.rokEw = :rok"),
    @NamedQuery(name = "EVatwpisKJPK.findByMcRok", query = "SELECT d FROM EVatwpisKJPK d WHERE d.rokEw = :rok AND d.mcEw = :mc"),
    @NamedQuery(name = "EVatwpisKJPK.findByNULL", query = "SELECT d FROM EVatwpisKJPK d WHERE d.ewidencja IS NULL")
})
public class EVatwpisKJPK extends EVatwpisSuper implements Serializable {
    private static final long serialVersionUID = 1l;
    
    @JoinColumn(name = "klientJPK", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private KlientJPK klientJPK;

    
    public EVatwpisKJPK(EVatwpisKJPK wiersz) {
        super(wiersz);
        this.klientJPK = wiersz.klientJPK;
    }
    
     
    

    public EVatwpisKJPK(Evewidencja ewidencja, double netto, double vat, String estawka, String mcEw, String rokEw) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
        this.mcEw = mcEw;
        this.rokEw = rokEw;
    }
    
    public EVatwpisKJPK(Evewidencja ewidencja, double netto, double vat, String estawka, KlientJPK klientJPK) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
        this.klientJPK = klientJPK;
    }

    public EVatwpisKJPK() {
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

    public KlientJPK getKlientJPK() {
        return klientJPK;
    }

    public void setKlientJPK(KlientJPK klientJPK) {
        this.klientJPK = klientJPK;
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
        hash = 17 * hash + Objects.hashCode(this.klientJPK);
        return hash;
    }

    @Override
    public String toString() {
        return "EVatwpisKJPK{"+ super.toString() + "klientJPK=" + klientJPK.toString() + '}';
    }

    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EVatwpisKJPK other = (EVatwpisKJPK) obj;
        if (!Objects.equals(this.ewidencja, other.ewidencja)) {
            return false;
        }
        if (!Objects.equals(this.klientJPK, other.klientJPK)) {
            return false;
        }
        return true;
    }

    @Override
   public String getDataSprz() {
      String zwrot = this.getKlientJPK() != null ? this.getKlientJPK().getDataSprzedazy() : "";
      return zwrot;
    }
    
    @Override
    public String getDataWyst() {
      String zwrot = this.getKlientJPK() != null ? this.getKlientJPK().getDataWystawienia() : "";
      return zwrot;
    }
    
    @Override
    public Klienci getKontr() {
      Klienci zwrot = new Klienci();
      if (this.getKlientJPK()!=null) {
          zwrot.setNpelna("klient");
          zwrot.setNip(this.getKlientJPK().getNrKontrahenta());
      }
      return zwrot;
    }
    
    @Override
    public String getNrKolejny() {
      String zwrot = this.getKlientJPK() != null ? String.valueOf(this.getKlientJPK().getId()) :"";
      return zwrot;
    }
    
    @Override
    public String getNrWlDk() {
      return this.getKlientJPK() != null ? this.getKlientJPK().getSerial() :"";
    }
    
    @Override
    public String getOpis() {
      String zwrot = this.getKlientJPK() != null ? "ama" : "podsumowanie";
      return zwrot;
    }
    
    @Override
    public double getProcentvat() {
        return 100.0;
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
        return this.getKlientJPK().getMc();
    }
    
    @Override
    public String getInnyrok(){
        return this.getKlientJPK().getRok();
    }
    
    @Override
    public String getDataoperacji() {
        return this.getKlientJPK().getDataSprzedazy();
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
    @Override
    public boolean isTylkodlajpk() {
        return super.tylkodlajpk;
    }

    @Override
    public void setTylkodlajpk(boolean tylkodlajpk) {
        this.tylkodlajpk = tylkodlajpk;
    }
    
}


