/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class Pozycjenafakturzebazadanych implements Serializable{
     int lp;
        String nazwa;
        String PKWiU;
        String jednostka;
        double ilosc;
        double cena;
        double netto;
        String podatek;
        double podatekkwota;
        double brutto;

    public Pozycjenafakturzebazadanych() {
    }
        
         public Pozycjenafakturzebazadanych(int lp, String nazwa, String PKWiU, String jednostka, double ilosc, double cena, double netto, String podatek, double podatekkwota, double brutto) {
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

        public String getPodatek() {
            return podatek;
        }

        public void setPodatek(String podatek) {
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
    
}
