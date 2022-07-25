/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class Umorzenie implements Serializable{
    private static final long serialVersionUID = 3357210341387891311L;
    private String nazwaSrodka;
    private Integer nrUmorzenia;
    private Integer rokUmorzenia;
    private Integer mcUmorzenia;
    private BigDecimal kwota;
    private int srodekTrwID;
    private String kontonetto;
    private String kontoumorzenie;
    //chodzi o to czy środek trwały czy wnip
    private String rodzaj;
    

    public String getNazwaSrodka() {
        return nazwaSrodka;
    }

    public void setNazwaSrodka(String nazwaSrodka) {
        this.nazwaSrodka = nazwaSrodka;
    }
    
    public Integer getNrUmorzenia() {
        return nrUmorzenia;
    }

    public void setNrUmorzenia(Integer nrUmorzenia) {
        this.nrUmorzenia = nrUmorzenia;
    }

    public Integer getRokUmorzenia() {
        return rokUmorzenia;
    }

    public void setRokUmorzenia(Integer rokUmorzenia) {
        this.rokUmorzenia = rokUmorzenia;
    }

    public Integer getMcUmorzenia() {
        return mcUmorzenia;
    }

    public void setMcUmorzenia(Integer mcUmorzenia) {
        this.mcUmorzenia = mcUmorzenia;
    }

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }

    public int getSrodekTrwID() {
        return srodekTrwID;
    }

    public void setSrodekTrwID(int srodekTrwID) {
        this.srodekTrwID = srodekTrwID;
    }

    public String getKontonetto() {
        return kontonetto;
    }

    public void setKontonetto(String kontonetto) {
        this.kontonetto = kontonetto;
    }

    public String getKontoumorzenie() {
        return kontoumorzenie;
    }

    public void setKontoumorzenie(String kontoumorzenie) {
        this.kontoumorzenie = kontoumorzenie;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }
    
    

    @Override
    public String toString() {
        return "Umorzenie{" + "nazwaSrodka=" + nazwaSrodka + ", rokUmorzenia=" + rokUmorzenia + ", mcUmorzenia=" + mcUmorzenia + ", kwota=" + kwota + ", kontonetto=" + kontonetto + ", kontoumorzenie=" + kontoumorzenie + '}';
    }
    
    
    
}
