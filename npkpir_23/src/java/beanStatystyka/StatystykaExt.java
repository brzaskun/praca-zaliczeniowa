/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanStatystyka;

import entity.Statystyka;
import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class StatystykaExt extends Statystyka implements Serializable {
    private double wspolczynnik;
    private double fakturaobecnie;
    private double fakturanowa;

    public StatystykaExt(double wspolczynnik, double fakturaobecnie, double fakturanowa, Statystyka o) {
        super(o);
        this.wspolczynnik = wspolczynnik;
        this.fakturaobecnie = fakturaobecnie;
        this.fakturanowa = fakturanowa;
    }

    public StatystykaExt() {
    }

    public double getWspolczynnik() {
        return wspolczynnik;
    }

    public void setWspolczynnik(double wspolczynnik) {
        this.wspolczynnik = wspolczynnik;
    }

    public double getFakturaobecnie() {
        return fakturaobecnie;
    }

    public void setFakturaobecnie(double fakturaobecnie) {
        this.fakturaobecnie = fakturaobecnie;
    }

    public double getFakturanowa() {
        return fakturanowa;
    }

    public void setFakturanowa(double fakturanowa) {
        this.fakturanowa = fakturanowa;
    }

    
    
    
    
    
}
