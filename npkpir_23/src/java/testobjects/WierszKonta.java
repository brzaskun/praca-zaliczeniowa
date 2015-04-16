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
public class WierszKonta implements Serializable {
    private static final long serialVersionUID = 1L;
    private int lp;
    private String opis;
    private double kwotawn;
    private String opiskontawn;
    private double kwotama;
    private String opiskontama;

    public WierszKonta() {
    }

    public WierszKonta(int lp, String opis, double kwotawn, String opiskontawn, double kwotama, String opiskontama) {
        this.lp = lp;
        this.opis = opis;
        this.kwotawn = kwotawn;
        this.opiskontawn = opiskontawn;
        this.kwotama = kwotama;
        this.opiskontama = opiskontama;
    }

    public WierszKonta(int lp, String opis) {
        this.lp = lp;
        this.opis = opis;
    }
    
    
    
    @Override
    public String toString() {
        return "WierszKonta{" + "lp=" + lp + ", opis=" + opis + ", kwotawn=" + kwotawn + ", opiskontawn=" + opiskontawn + ", kwotama=" + kwotama + ", opiskontama=" + opiskontama + '}';
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

    public double getKwotawn() {
        return kwotawn;
    }

    public void setKwotawn(double kwotawn) {
        this.kwotawn = kwotawn;
    }

    public String getOpiskontawn() {
        return opiskontawn;
    }

    public void setOpiskontawn(String opiskontawn) {
        this.opiskontawn = opiskontawn;
    }

    public double getKwotama() {
        return kwotama;
    }

    public void setKwotama(double kwotama) {
        this.kwotama = kwotama;
    }

    public String getOpiskontama() {
        return opiskontama;
    }

    public void setOpiskontama(String opiskontama) {
        this.opiskontama = opiskontama;
    }
    
    
    
}
