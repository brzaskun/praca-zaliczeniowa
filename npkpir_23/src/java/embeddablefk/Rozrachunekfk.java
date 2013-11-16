/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import java.util.Objects;

/**
 *
 * @author Osito
 */
public class Rozrachunekfk {
    private WierszStronafk wierszStronafk;
    private double kwotapierwotna;
    private double rozliczono;
    private double pozostalo;


    public Rozrachunekfk() {
        this.kwotapierwotna = 0.0;
        this.rozliczono = 0.0;
        this.pozostalo = 0.0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.wierszStronafk);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.kwotapierwotna) ^ (Double.doubleToLongBits(this.kwotapierwotna) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.rozliczono) ^ (Double.doubleToLongBits(this.rozliczono) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.pozostalo) ^ (Double.doubleToLongBits(this.pozostalo) >>> 32));
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
        final Rozrachunekfk other = (Rozrachunekfk) obj;
        if (!Objects.equals(this.wierszStronafk, other.wierszStronafk)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Rozrachunek{" + "wierszStronafk=" + wierszStronafk + ", kwotapierwotna=" + kwotapierwotna + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo + '}';
    }

    public WierszStronafk getWierszStronafk() {
        return wierszStronafk;
    }

    public void setWierszStronafk(WierszStronafk wierszStronafk) {
        this.wierszStronafk = wierszStronafk;
    }

    public double getKwotapierwotna() {
        return kwotapierwotna;
    }

    public void setKwotapierwotna(double kwotapierwotna) {
        this.kwotapierwotna = kwotapierwotna;
    }

    public double getRozliczono() {
        return rozliczono;
    }

    public void setRozliczono(double rozliczono) {
        this.rozliczono = rozliczono;
    }

    public double getPozostalo() {
        return pozostalo;
    }

    public void setPozostalo(double pozostalo) {
        this.pozostalo = pozostalo;
    }
    
    
}
