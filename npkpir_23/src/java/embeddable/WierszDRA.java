/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Podatnik;
import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class WierszDRA  implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String rok;
    private String mc;
    private String mcnazwa;
    private Podatnik podatnik;
    private String imienazwisko;
    private String opodatkowanie;
    private double udzial;
    private double dochod;
    private double przychod;
    private boolean brakdokumentow;
    private boolean jestpit;

    public WierszDRA() {
    }
    
    
    public WierszDRA(int id, Podatnik p, String rok, String mc, String get) {
        this.id = id;
        this.podatnik = p;
        this.rok = rok;
        this.mc = mc;
        this.mcnazwa = get;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMcnazwa() {
        return mcnazwa;
    }

    public void setMcnazwa(String mcnazwa) {
        this.mcnazwa = mcnazwa;
    }

    public String getImienazwisko() {
        return imienazwisko;
    }

    public void setImienazwisko(String imienazwisko) {
        this.imienazwisko = imienazwisko;
    }

    public boolean isBrakdokumentow() {
        return brakdokumentow;
    }

    public void setBrakdokumentow(boolean brakdokumentow) {
        this.brakdokumentow = brakdokumentow;
    }

    
    @Override
    public String toString() {
        return "WierszDRA{" + "rok=" + rok + ", mc=" + mc + ", mcnazwa=" + mcnazwa + ", podatnik=" + podatnik.getPrintnazwa() + ", imienazwisko=" + imienazwisko + ", opodatkowanie=" + opodatkowanie + ", udzial=" + udzial + ", dochod=" + dochod + ", przychod=" + przychod + '}';
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getOpodatkowanie() {
        return opodatkowanie;
    }

    public void setOpodatkowanie(String opodatkowanie) {
        this.opodatkowanie = opodatkowanie;
    }

    public double getUdzial() {
        return udzial;
    }

    public void setUdzial(double udzial) {
        this.udzial = udzial;
    }

    public double getDochod() {
        return dochod;
    }

    public void setDochod(double dochod) {
        this.dochod = dochod;
    }

    public double getPrzychod() {
        return przychod;
    }

    public void setPrzychod(double przychod) {
        this.przychod = przychod;
    }

    public boolean isJestpit() {
        return jestpit;
    }

    public void setJestpit(boolean jestpit) {
        this.jestpit = jestpit;
    }
    
    
    
    
    
}

