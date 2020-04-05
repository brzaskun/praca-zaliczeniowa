/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Dokfk;
import entityfk.Wiersz;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class EVatwpisSuper implements Serializable {
    private static final long serialVersionUID = 7803477770796415103L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected long id;
    @JoinColumn(name = "ewidencja", referencedColumnName = "nazwa")
    @ManyToOne
    protected Evewidencja ewidencja;
    @Column(name = "netto")
    protected double netto;
    @Column(name = "vat")
    protected double vat;
    @Column(name = "estawka")
    protected String estawka;
    @Size(max = 2)
    @Column(name = "mcEw")
    protected String mcEw;
    @Size(max = 4)
    @Column(name = "rokEw")
    protected String rokEw;
    @Column(name="nieduplikuj")
    protected boolean nieduplikuj;
    @Transient
    protected boolean duplikat;
    @Transient
    protected int lp;
    @Transient
    protected double brutto;
    

    public EVatwpisSuper(EVatwpisSuper wiersz) {
        this.id = wiersz.id;
        this.ewidencja = wiersz.ewidencja;
        this.netto = wiersz.netto;
        this.vat = wiersz.vat;
        this.estawka = wiersz.estawka;
        this.mcEw = wiersz.mcEw;
        this.rokEw = wiersz.rokEw;
    }

    
    

    public EVatwpisSuper() {
        
    }

    public long getId() {
        return id;
    }

    public Evewidencja getEwidencja() {
        return ewidencja;
    }

    public String getEstawka() {
        return estawka;
    }

    public String getMcEw() {
        return mcEw;
    }

    public String getRokEw() {
        return rokEw;
    }

    public boolean isNieduplikuj() {
        return nieduplikuj;
    }

    public void setNieduplikuj(boolean nieduplikuj) {
        this.nieduplikuj = nieduplikuj;
    }

    public String getDataWyst() {
        return "";
    }

    public double getNetto() {
        return 0.0;
    }

    public double getVat() {
        return 0.0;
    }

    public String getOpizw() {
        return "";
    }

    public Evewidencja getNazwaewidencji() {
        return new Evewidencja();
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setDataSprz(String string) {
    }

    public void setDataWyst(String string) {
    }

    public void setKontr(Object object) {
    }

    public void setNrWlDk(String string) {
    }

    public void setOpis(String podsumowanie) {
    }

    public void setNazwaewidencji(Evewidencja object) {
    }
  
    public void setNetto(double doubleValue) {
    }

    public void setVat(double doubleValue) {
    }

    public void setOpizw(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setDuplikat(boolean b) {
        
    }

    public double getProcentvat() {
        return 0.0;
    }

    public Dokfk getDokfk() {
        return new Dokfk();
    }

    public String getDataSprz() {
        return "";
    }

    public String getNrKolejny() {
        return "";
    }

    public String getOpis() {
        return "";
    }

    public String getNumerwlasnydokfk() {
        return "";
    }

    public String getNrWlDk() {
        return "";
    }

    public Klienci getKontr() {
        return new Klienci();
    }

    public String getInnymc() {
        return "";
    }

    public String getInnyrok() {
        return "";
    }

    public boolean isDuplikat() {
        return false;
    }

    public Wiersz getWiersz() {
        return new Wiersz();
    }

    public String getDataoperacji() {
        return "";
    }
    
    public String getNrpozycji() {
        return "";
    }

    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }


    

    @Override
    public String toString() {
        return "EVatwpisSuper{" + "ewidencja=" + ewidencja.getNazwa() + ", netto=" + netto + ", vat=" + vat + ", mcEw=" + mcEw + ", rokEw=" + rokEw + ' ';
    }
    
    
   
}
