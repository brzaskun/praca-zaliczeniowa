/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testobjects;

import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class WierszTabeli implements Serializable{
    private static final long serialVersionUID = 1L;
    private int lp;
    private String opis;
    private double wartosc;

    @Override
    public String toString() {
        return "WierszTabeli{" + "lp=" + lp + ", opis=" + opis + ", wartosc=" + wartosc + '}';
    }

    public WierszTabeli() {
    }

    public WierszTabeli(int lp, String opis, double wartosc) {
        this.lp = lp;
        this.opis = opis;
        this.wartosc = wartosc;
    }

    
    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getWartosc() {
        return wartosc;
    }

    public void setWartosc(double wartosc) {
        this.wartosc = wartosc;
    }
    
    
}
