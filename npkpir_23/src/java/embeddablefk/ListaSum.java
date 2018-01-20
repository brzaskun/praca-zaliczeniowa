/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Tabelanbp;
import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class ListaSum  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Tabelanbp tabelanbp;
    private double kurswaluty;
    private String waluta;
    private double sumaWn;
    private double sumaMa;
    private double saldoWn;
    private double saldoMa;
    private double sumaWnPLN;
    private double sumaMaPLN;
    private double saldoWnPLN;
    private double saldoMaPLN;

    public ListaSum() {
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public double getSumaWn() {
        return sumaWn;
    }

    public void setSumaWn(double sumaWn) {
        this.sumaWn = sumaWn;
    }

    public Tabelanbp getTabelanbp() {
        return tabelanbp;
    }

    public void setTabelanbp(Tabelanbp tabelanbp) {
        this.tabelanbp = tabelanbp;
    }

    public double getSumaMa() {
        return sumaMa;
    }

    public void setSumaMa(double sumaMa) {
        this.sumaMa = sumaMa;
    }

    public double getSaldoWn() {
        return saldoWn;
    }

    public void setSaldoWn(double saldoWn) {
        this.saldoWn = saldoWn;
    }

    public double getSaldoMa() {
        return saldoMa;
    }

    public void setSaldoMa(double saldoMa) {
        this.saldoMa = saldoMa;
    }

    public double getSumaWnPLN() {
        return sumaWnPLN;
    }

    public void setSumaWnPLN(double sumaWnPLN) {
        this.sumaWnPLN = sumaWnPLN;
    }

    public double getSumaMaPLN() {
        return sumaMaPLN;
    }

    public void setSumaMaPLN(double sumaMaPLN) {
        this.sumaMaPLN = sumaMaPLN;
    }

    public double getSaldoWnPLN() {
        return saldoWnPLN;
    }

    public void setSaldoWnPLN(double saldoWnPLN) {
        this.saldoWnPLN = saldoWnPLN;
    }

    public double getSaldoMaPLN() {
        return saldoMaPLN;
    }

    public void setSaldoMaPLN(double saldoMaPLN) {
        this.saldoMaPLN = saldoMaPLN;
    }

    public double getKurswaluty() {
        return kurswaluty;
    }

    public void setKurswaluty(double kurswaluty) {
        this.kurswaluty = kurswaluty;
    }
    
    
    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }
    

//</editor-fold>

    @Override
    public String toString() {
        return "ListaSum{" + "kurswaluty=" + kurswaluty + ", waluta=" + waluta + ", sumaWn=" + sumaWn + ", sumaMa=" + sumaMa + ", saldoWn=" + saldoWn + ", saldoMa=" + saldoMa + ", sumaWnPLN=" + sumaWnPLN + ", sumaMaPLN=" + sumaMaPLN + ", saldoWnPLN=" + saldoWnPLN + ", saldoMaPLN=" + saldoMaPLN + '}';
    }


}
