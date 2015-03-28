/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import java.util.Objects;

/**
 *
 * @author Osito
 */
public class PozycjaPrzychodKoszt {
    private int lp;
    private String nrkonta;
    private String kontoNazwapelna;
    private String kontoNazwaskrocona;
    private double kwota;

    public PozycjaPrzychodKoszt(int lp, String nrkonta, String kontoNazwapelna, String kontoNazwaskrocona, double kwota) {
        this.lp = lp;
        this.nrkonta = nrkonta;
        this.kontoNazwapelna = kontoNazwapelna;
        this.kontoNazwaskrocona = kontoNazwaskrocona;
        this.kwota = kwota;
    }

    public PozycjaPrzychodKoszt() {
    }

    @Override
    public String toString() {
        return "PozycjaPrzychodKoszt{" + "lp=" + lp + ", nrkonta=" + nrkonta + ", kontoNazwapelna=" + kontoNazwapelna + ", kontoNazwaskrocona=" + kontoNazwaskrocona + ", kwota=" + kwota + '}';
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.nrkonta);
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
        final PozycjaPrzychodKoszt other = (PozycjaPrzychodKoszt) obj;
        if (!Objects.equals(this.nrkonta, other.nrkonta)) {
            return false;
        }
        return true;
    }

    
    
    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public String getNrkonta() {
        return nrkonta;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public String getKontoNazwapelna() {
        return kontoNazwapelna;
    }

    public void setKontoNazwapelna(String kontoNazwapelna) {
        this.kontoNazwapelna = kontoNazwapelna;
    }

    public String getKontoNazwaskrocona() {
        return kontoNazwaskrocona;
    }

    public void setKontoNazwaskrocona(String kontoNazwaskrocona) {
        this.kontoNazwaskrocona = kontoNazwaskrocona;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }
    
    
}
