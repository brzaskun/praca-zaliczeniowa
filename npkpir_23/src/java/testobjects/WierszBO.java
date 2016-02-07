/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testobjects;


/**
 *
 * @author Osito
 */
public class WierszBO {
    private String podatnik;;
    private Konto konto;
    private double kwotaWn;
    private double kwotaMa;
    private boolean rozrachunek;
    private String waluta;
    private double kurs;
    private double kwotaWnPLN;
    private double kwotaMaPLN;

    public WierszBO() {
    }

    public WierszBO(String podatnik, Konto konto, double kwotaWn, double kwotaMa) {
        this.podatnik = podatnik;
        this.konto = konto;
        this.kwotaWn = kwotaWn;
        this.kwotaMa = kwotaMa;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getKwotaWn() {
        return kwotaWn;
    }

    public void setKwotaWn(double kwotaWn) {
        this.kwotaWn = kwotaWn;
    }

    public double getKwotaMa() {
        return kwotaMa;
    }

    public void setKwotaMa(double kwotaMa) {
        this.kwotaMa = kwotaMa;
    }

    public boolean isRozrachunek() {
        return rozrachunek;
    }

    public void setRozrachunek(boolean rozrachunek) {
        this.rozrachunek = rozrachunek;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public double getKwotaWnPLN() {
        return kwotaWnPLN;
    }

    public void setKwotaWnPLN(double kwotaWnPLN) {
        this.kwotaWnPLN = kwotaWnPLN;
    }

    public double getKwotaMaPLN() {
        return kwotaMaPLN;
    }

    public void setKwotaMaPLN(double kwotaMaPLN) {
        this.kwotaMaPLN = kwotaMaPLN;
    }

    @Override
    public String toString() {
        return "WierszBO{" + "podatnik=" + podatnik + ", konto=" + konto + ", kwotaWn=" + kwotaWn + ", kwotaMa=" + kwotaMa + ", rozrachunek=" + rozrachunek + ", waluta=" + waluta + ", kurs=" + kurs + ", kwotaWnPLN=" + kwotaWnPLN + ", kwotaMaPLN=" + kwotaMaPLN + '}';
    }
    
    
}
