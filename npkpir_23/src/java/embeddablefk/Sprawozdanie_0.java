/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Konto;

/**
 *
 * @author Osito
 */
public class Sprawozdanie_0 {
    private int lp;
    private Konto konto;
    private double brutto;
    private double umorzenie;
    private double netto;

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public double getUmorzenie() {
        return umorzenie;
    }

    public void setUmorzenie(double umorzenie) {
        this.umorzenie = umorzenie;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

 
    
    
    
}
