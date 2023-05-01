/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;

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
    private double dniobowiazku;
    private double dniprzepracowane;
    private boolean uzupelniane;

    public Z3dane(String rok, String mc) {
        this.rok = rok;
        this.mc = mc;
    }
    
    public void sumuj(double stale, double zmienne, double premie) {
        this.stale = this.stale+stale;
        this.zmienne = this.zmienne+zmienne;
        this.premie = this.premie+premie;
    }
    

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
    
    public String getOkres() {
        return this.rok+this.mc;
    }

    public double getDniobowiazku() {
        return dniobowiazku;
    }

    public void setDniobowiazku(double dniobowiazku) {
        this.dniobowiazku = dniobowiazku;
    }

    public double getDniprzepracowane() {
        return dniprzepracowane;
    }

    public void setDniprzepracowane(double dniprzepracowane) {
        this.dniprzepracowane = dniprzepracowane;
    }

    public boolean isUzupelniane() {
        return uzupelniane;
    }

    public void setUzupelniane(boolean uzupelniane) {
        this.uzupelniane = uzupelniane;
    }

    
    
    
}
