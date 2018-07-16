/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.*;
import java.io.Serializable;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ewidencja", "dokid", "WIERSZ_idwiersza", "KLIENT_id"})
})
@NamedQueries({
    @NamedQuery(name = "EVatwpisFK.findByWiersz", query = "SELECT k FROM EVatwpisFK k WHERE k.wiersz = :wiersz"),
    @NamedQuery(name = "EVatwpisFK.findByPodatnik", query = "SELECT k FROM EVatwpisFK k WHERE k.dokfk.podatnikObj = :podatnik"),
    @NamedQuery(name = "EVatwpisFK.findByPodatnikRok", query = "SELECT k FROM EVatwpisFK k WHERE k.dokfk.podatnikObj = :podatnik AND k.rokEw = :rok"),
    @NamedQuery(name = "EVatwpisFK.findByPodatnikRokInnyOkres", query = "SELECT k FROM EVatwpisFK k WHERE k.dokfk.podatnikObj = :podatnik AND k.rokEw = :rok AND k.innyokres != 0"),
    @NamedQuery(name = "EVatwpisFK.findEVatwpisFKPodatnikKlient", query = "SELECT k FROM EVatwpisFK k WHERE k.dokfk.podatnikObj = :podatnik AND k.klient = :klient AND k.dokfk.rok = :rok ORDER BY k.dokfk.nrkolejnywserii DESC")
})
@Cacheable
public class EVatwpisFK extends EVatwpisSuper implements Serializable {
    private static final long serialVersionUID = 1L;
    private int lp;
    @Column(name = "nettowwalucie")
    private double nettowwalucie;
    @Column(name = "vatwwalucie")
    private double vatwwalucie;
    @Column(name = "brutto")
    private double brutto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dokid", referencedColumnName = "id")
    private Dokfk dokfk;
    @OneToOne(fetch = FetchType.LAZY)
    private Wiersz wiersz;
    @OneToOne(fetch = FetchType.LAZY)
    private Klienci klient;
    @Column(name = "datadokumentu")
    private String datadokumentu;
    @Column(name = "dataoperacji")
    private String dataoperacji;
    @Column(name = "paliwo")
    private boolean paliwo;
    @Column(name = "innyokres")
    private int innyokres;
    @Column(name = "numerwlasnydokfk", nullable = false, length = 120)
    private String numerwlasnydokfk;
    @Column(name = "opisvat", nullable = false, length = 120)
    private String opisvat;
    @Column(name = "sprawdzony")
    private int sprawdzony;

   
    
    

    public EVatwpisFK(Evewidencja ewidencja, double netto, double vat, String estawka) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.brutto = netto + vat;
        this.estawka = estawka;
        this.innyokres = 0;
    }

    public EVatwpisFK() {
        this.innyokres = 0;
    }

    public EVatwpisFK(EVatwpisFK eVatwpisFK) {
        super(eVatwpisFK);
        this.lp = eVatwpisFK.lp;
        this.nettowwalucie = eVatwpisFK.nettowwalucie;
        this.vatwwalucie = eVatwpisFK.vatwwalucie;
        this.brutto = eVatwpisFK.brutto;
        this.dokfk = eVatwpisFK.dokfk;
        this.wiersz = eVatwpisFK.wiersz;
        this.klient = eVatwpisFK.klient;
        this.datadokumentu = eVatwpisFK.datadokumentu;
        this.dataoperacji = eVatwpisFK.dataoperacji;
        this.paliwo = eVatwpisFK.paliwo;
        this.innyokres = eVatwpisFK.innyokres;
        this.numerwlasnydokfk = eVatwpisFK.numerwlasnydokfk;
    }

    public EVatwpisFK(int k, Evewidencja p, Dokfk selected) {
        this.setLp(k);
        this.setEwidencja(p);
        this.setNetto(0.0);
        this.setVat(0.0);
        this.setDokfk(selected);
        this.setEstawka("op");
        this.setMcEw(selected.getVatM());
        this.setRokEw(selected.getVatR());
        this.setInnyokres(0);
    }

        
    

    
    //<editor-fold defaultstate="collapsed" desc="getters & setters">\
    @Override
    public String getNumerwlasnydokfk() {
        return numerwlasnydokfk;
    }

    public void setNumerwlasnydokfk(String numerwlasnydokfk) {
        this.numerwlasnydokfk = numerwlasnydokfk;
    }

    public int getSprawdzony() {
        return sprawdzony;
    }

    public void setSprawdzony(int sprawdzony) {
        this.sprawdzony = sprawdzony;
    }
    
    
     public String getOpisvat() {
        return opisvat;
    }

    public void setOpisvat(String opisvat) {
        this.opisvat = opisvat;
    }
     public boolean isPaliwo() {   
        return paliwo;
    }


    public void setPaliwo(boolean paliwo) {
        this.paliwo = paliwo;
    }

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
    
    
    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
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
    
    public void setVat(double vat) {
        this.vat = vat;
    }
    
    public String getEstawka() {
        return estawka;
    }
    
    public void setEstawka(String estawka) {
        this.estawka = estawka;
    }
    
    @Override
    public Dokfk getDokfk() {
        return dokfk;
    }
    
    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }
    
    @Override
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Override
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

    public int getInnyokres() {
        return innyokres;
    }

    public void setInnyokres(int innyokres) {
        this.innyokres = innyokres;
    }

    

    @Override
    public String getMcEw() {
        return mcEw;
    }

    public void setMcEw(String mcEw) {
        this.mcEw = mcEw;
    }

    @Override
    public String getRokEw() {
        return rokEw;
    }

    public void setRokEw(String rokEw) {
        this.rokEw = rokEw;
    }

    
    @Override
    public void setDuplikat(boolean duplikat) {
        super.duplikat = duplikat;
    }
   
    
    
    @Override
    public String getDataoperacji() {
        return dataoperacji;
    }
    
    public void setDataoperacji(String dataoperacji) {
        this.dataoperacji = dataoperacji;
    }
    
    
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.ewidencja);
        hash = 97 * hash + Objects.hashCode(this.dokfk);
        hash = 97 * hash + Objects.hashCode(this.wiersz);
        hash = 97 * hash + Objects.hashCode(this.klient);
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
        final EVatwpisFK other = (EVatwpisFK) obj;
        if (this.id != other.id) {
            return false;
        }
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
        if (wiersz != null) {
            return "EVatwpisFK{" + "ewidencja=" + ewidencja + ", netto=" + netto + ", vat=" + vat + ", dokfk=" + dokfk + ", wiersz=" + wiersz.tostring2() + ", klient=" + klient + '}';
        } else {
            return "EVatwpisFK{" + "ewidencja=" + ewidencja + ", netto=" + netto + ", vat=" + vat + ", dokfk=" + dokfk + ", klient=" + klient + '}';
        }
    }

     public String getWalutaWiersz() {
        Wiersz w = this.wiersz;
        String waluta = null;
        if (w != null) {
            waluta = w.getTabelanbp() != null ? w.getTabelanbp().getWaluta().getSymbolwaluty() : "";
        } else {
            waluta = this.dokfk.getTabelanbp().getWaluta().getSymbolwaluty();
        }
        return waluta;
    }

    @Override
    public String getDataSprz() {
      String zwrot = this.getDokfk() != null ? this.getDokfk().getDataoperacji():"";
      if (this.getWiersz() != null) {
          zwrot = this.getDataoperacji();
      }
      return zwrot;
    }
    
    @Override
    public String getDataWyst() {
      String zwrot = this.getDokfk() != null ? this.getDokfk().getDatadokumentu() :"";
      if (this.getWiersz() != null) {
          zwrot = this.getDatadokumentu();
      }
      return zwrot;
    }
    
    @Override
    public Klienci getKontr() {
      Klienci zwrot = this.getDokfk() != null ? this.getDokfk().getKontr(): new Klienci();
      if (this.getWiersz() != null) {
          zwrot = this.getKlient();
      }
      return zwrot;
    }
    
    @Override
    public String getNrKolejny() {
      String zwrot = this.getDokfk() != null ? this.getDokfk().toString2():"";
      if (this.getWiersz() != null) {
          zwrot = this.getDokfk().toString2() + ", poz: " + this.getWiersz().getIdporzadkowy();
      }
      return zwrot;
    }
    
    @Override
    public String getNrWlDk() {
      String zwrot = this.getDokfk() != null ? this.getDokfk().getNumerwlasnydokfk() :"";
      if (this.getWiersz() != null) {
          zwrot = this.getNumerwlasnydokfk();
      }
      return zwrot;
    }
    
    @Override
    public String getOpis() {
      String zwrot = this.getDokfk() != null ? this.getDokfk().getOpisdokfk() :"podsumowanie";
      if (this.getWiersz() != null) {
          zwrot = this.getWiersz().getOpisWiersza();
      }
      return zwrot;
    }
    
    @Override
    public double getProcentvat() {
        double zwrot = this.getDokfk() != null ? this.getDokfk().getRodzajedok().getProcentvat():100.0;
        if (this.isPaliwo()) {
            zwrot = (50.0);
        }
        return zwrot;
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
        return this.getDokfk() != null ? this.getDokfk().getVatM():"";
    }
    
    @Override
    public String getInnyrok(){
        return this.getDokfk() != null ? this.getDokfk().getVatR():"";
    }
    
    @Override
   public String getNrpozycji() {
        if (!this.getOpis().equals("podsumowanie") && this.getDokfk().getRodzajedok().getKategoriadokumentu()==0 && this.getNumerwlasnydokfk() != null) {
            return this.numerwlasnydokfk;
        } else if (!this.getOpis().equals("podsumowanie")){
            return this.dokfk.getNumerwlasnydokfk();
        } else {
            return "";
        }
    }
   @Override
    public void setNazwaewidencji(Evewidencja object) {
        super.ewidencja = object;
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


