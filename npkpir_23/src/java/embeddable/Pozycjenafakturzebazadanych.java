/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entityfk.Tabelanbp;
import java.io.Serializable;
import javax.persistence.Embeddable;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Embeddable
public class Pozycjenafakturzebazadanych implements Serializable{
        private static final long serialVersionUID = 6017591291599259063L;
        private int lp;
        private String nazwa = "";
        private String nowakolumna = "";
        private double cenajedn0 = 0;
        private double cenajedn1 = 0;
        private double cenajedn2 = 0;
        private double cenajedn3 = 0;
        private double cenajedn4 = 0;
        private double cenajedn5 = 0;
        private String PKWiU = "";
        private String jednostka = "";
        private double ilosc = 0;
        private double cena = 0;
        private double netto = 0;
        private double podatek = 0;
        private double podatekkwota = 0;
        private double brutto = 0;
        private int dodatkowapozycja = 0;

    public Pozycjenafakturzebazadanych() {
    }

         public Pozycjenafakturzebazadanych(int lp, String nazwa, String PKWiU, String jednostka, double ilosc, double cena, double netto, double podatek, double podatekkwota, double brutto) {
            this.lp = lp;
            this.nazwa = nazwa;
            this.PKWiU = PKWiU;
            this.jednostka = jednostka;
            this.ilosc = ilosc;
            this.cena = cena;
            this.netto = netto;
            this.podatek = podatek;
            this.podatekkwota = podatekkwota;
            this.brutto = brutto;
        }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getCenajedn0() {
        return cenajedn0;
    }

    public void setCenajedn0(double cenajedn0) {
        this.cenajedn0 = cenajedn0;
    }

    
    public double getCenajedn1() {
        return cenajedn1;
    }

    public void setCenajedn1(double cenajedn1) {
        this.cenajedn1 = cenajedn1;
    }

    public double getCenajedn2() {
        return cenajedn2;
    }

    public void setCenajedn2(double cenajedn2) {
        this.cenajedn2 = cenajedn2;
    }

    public double getCenajedn3() {
        return cenajedn3;
    }

    public void setCenajedn3(double cenajedn3) {
        this.cenajedn3 = cenajedn3;
    }

    public double getCenajedn4() {
        return cenajedn4;
    }

    public void setCenajedn4(double cenajedn4) {
        this.cenajedn4 = cenajedn4;
    }

    public double getCenajedn5() {
        return cenajedn5;
    }

    public void setCenajedn5(double cenajedn5) {
        this.cenajedn5 = cenajedn5;
    }

    public String getPKWiU() {
        return PKWiU;
    }

    public void setPKWiU(String PKWiU) {
        this.PKWiU = PKWiU;
    }

    public String getJednostka() {
        return jednostka;
    }

    public void setJednostka(String jednostka) {
        this.jednostka = jednostka;
    }

    public double getIlosc() {
        return ilosc;
    }

    public void setIlosc(double ilosc) {
        this.ilosc = ilosc;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getPodatek() {
        return podatek;
    }

    public void setPodatek(double podatek) {
        this.podatek = podatek;
    }

    public double getPodatekkwota() {
        return podatekkwota;
    }

    public void setPodatekkwota(double podatekkwota) {
        this.podatekkwota = podatekkwota;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public String getNowakolumna() {
        return nowakolumna;
    }

    public void setNowakolumna(String nowakolumna) {
        this.nowakolumna = nowakolumna;
    }

    public int getDodatkowapozycja() {
        return dodatkowapozycja;
    }

    public void setDodatkowapozycja(int dodatkowapozycja) {
        this.dodatkowapozycja = dodatkowapozycja;
    }
     
    
    


    @Override
    public String toString() {
        return "Pozycjenafakturzebazadanych{" + "lp=" + lp + ", nazwa=" + nazwa + ", PKWiU=" + PKWiU + ", jednostka=" + jednostka + ", ilosc=" + ilosc + ", cena=" + cena + ", netto=" + netto + ", podatek=" + podatek + ", podatekkwota=" + podatekkwota + ", brutto=" + brutto + '}';
    }


    public Object getCena(boolean liczodwartoscibrutto, double podatek) {
        double zwrot = this.cena;
        if (liczodwartoscibrutto && Z.z(podatek) != 0.0) {
            zwrot = Z.z(this.cena-Z.z(this.cena*podatek/(100+podatek)));
        }
        return zwrot;
    }

    public double getNetto(Tabelanbp tabelanbp) {
        double zwrot = this.netto;
        if (tabelanbp!=null) {
            zwrot = Z.z(this.netto*tabelanbp.getKurssredniPrzelicznik());
        }
        return zwrot;
    }

    public double getPodatekkwota(Tabelanbp tabelanbp) {
        double zwrot = this.podatekkwota;
        if (tabelanbp!=null) {
            zwrot = Z.z(this.podatekkwota*tabelanbp.getKurssredniPrzelicznik());
        }
        return zwrot;
    }
 
        
}
