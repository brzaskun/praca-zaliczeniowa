/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.persistence.Column;

/**
 *
 * @author Osito
 */

public class Z3dane implements Serializable {
    private static final long serialVersionUID = 1L;
    private String rok;
    private String mc;
    private double stale;
    private double zmienne;
    private double premie;
    private double godzinyobowiazku;
    private double godzinyprzepracowane;

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public double getStale() {
        return stale;
    }

    public void setStale(double stale) {
        this.stale = stale;
    }

    public double getZmienne() {
        return zmienne;
    }

    public void setZmienne(double zmienne) {
        this.zmienne = zmienne;
    }

    public double getPremie() {
        return premie;
    }

    public void setPremie(double premie) {
        this.premie = premie;
    }

    public double getGodzinyobowiazku() {
        return godzinyobowiazku;
    }

    public void setGodzinyobowiazku(double godzinyobowiazku) {
        this.godzinyobowiazku = godzinyobowiazku;
    }

    public double getGodzinyprzepracowane() {
        return godzinyprzepracowane;
    }

    public void setGodzinyprzepracowane(double godzinyprzepracowane) {
        this.godzinyprzepracowane = godzinyprzepracowane;
    }
    
    
    
}
