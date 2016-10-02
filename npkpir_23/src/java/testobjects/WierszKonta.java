/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testobjects;

import entityfk.Tabelanbp;
import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class WierszKonta implements Serializable {
    private static final long serialVersionUID = 1L;
    private int lp;
    private String opis;
    private double kwotaWn;
    private double kwotaWnPLN;
    private String opiskontaWn;
    private double kwotaMa;
    private double kwotaMaPLN;
    private String opiskontaMa;
    private double saldo;
    private String waluta;
    private Tabelanbp tabela;
    private double kurs;

    public WierszKonta() {
    }

    public WierszKonta(int lp, String opis, double kwotawn, String opiskontawn, double kwotama, String opiskontama, String waluta) {
        this.lp = lp;
        this.opis = opis;
        this.kwotaWn = kwotawn;
        this.opiskontaWn = opiskontawn;
        this.kwotaMa = kwotama;
        this.opiskontaMa = opiskontama;
        this.waluta = waluta;
    }
    
    public WierszKonta(int lp, String opis, double kwotawn, String opiskontawn, double kwotama, String opiskontama, double saldo, String waluta) {
        this.lp = lp;
        this.opis = opis;
        this.kwotaWn = kwotawn;
        this.opiskontaWn = opiskontawn;
        this.kwotaMa = kwotama;
        this.opiskontaMa = opiskontama;
        this.saldo = saldo;
        this.waluta = waluta;
    }

    public WierszKonta(int lp, String opis, String waluta) {
        this.lp = lp;
        this.opis = opis;
        this.waluta = waluta;
        
    }

    
    
    
    
    @Override
    public String toString() {
        return "WierszKonta{" + "lp=" + lp + ", opis=" + opis + ", kwotawn=" + kwotaWn + ", opiskontawn=" + opiskontaWn + ", kwotama=" + kwotaMa + ", opiskontama=" + opiskontaMa + '}';
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

    public double getKwotaWn() {
        return kwotaWn;
    }

    public void setKwotaWn(double kwotaWn) {
        this.kwotaWn = kwotaWn;
    }

    public String getOpiskontaWn() {
        return opiskontaWn;
    }

    public void setOpiskontaWn(String opiskontaWn) {
        this.opiskontaWn = opiskontaWn;
    }

    public double getKwotaMa() {
        return kwotaMa;
    }

    public void setKwotaMa(double kwotaMa) {
        this.kwotaMa = kwotaMa;
    }

    public String getOpiskontaMa() {
        return opiskontaMa;
    }

    public void setOpiskontaMa(String opiskontaMa) {
        this.opiskontaMa = opiskontaMa;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
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

    public Tabelanbp getTabela() {
        return tabela;
    }

    public void setTabela(Tabelanbp tabela) {
        this.tabela = tabela;
    }


    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }
    
    
    
}
