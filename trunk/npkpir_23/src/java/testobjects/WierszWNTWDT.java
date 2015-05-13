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
public class WierszWNTWDT implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String data;
    private String iddok;
    private String nrwlasny;
    private String opis;
    private double kg;
    private double szt;
    private double kwotaWn;
    private double kwotaWnPLN;
    private String opiskontaWn;
    private double kwotaMa;
    private double kwotaMaPLN;
    private String opiskontaMa;

    public WierszWNTWDT() {
    }

    public WierszWNTWDT(int id, String data, String iddok, String nrwlasny, int lpwiersza, String opis, double kg, double szt, double kwotaWn, String kontoWn, double kwotaMa, String kontoMa) {
        this.id = id;
        this.data = data;
        this.iddok = iddok;
        this.nrwlasny = nrwlasny;
        this.opis = opis;
        this.kg = kg;
        this.szt = szt;
        this.kwotaWn = kwotaWn;
        this.opiskontaWn = kontoWn;
        this.kwotaMa = kwotaMa;
        this.opiskontaMa = kontoMa;
    }

    public WierszWNTWDT(int id, String data, String iddok, String nrwlasny, int lpwiersza, String opis, double kg, double szt) {
        this.id = id;
        this.data = data;
        this.iddok = iddok;
        this.nrwlasny = nrwlasny;
        this.opis = opis;
        this.kg = kg;
        this.szt = szt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIddok() {
        return iddok;
    }

    public void setIddok(String iddok) {
        this.iddok = iddok;
    }

    public String getNrwlasny() {
        return nrwlasny;
    }

    public void setNrwlasny(String nrwlasny) {
        this.nrwlasny = nrwlasny;
    }

   
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getKg() {
        return kg;
    }

    public void setKg(double kg) {
        this.kg = kg;
    }

    public double getSzt() {
        return szt;
    }

    public void setSzt(double szt) {
        this.szt = szt;
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
        return "WierszWNTWDT{" + "id=" + id + ", data=" + data + ", iddok=" + iddok + ", nrwlasny=" + nrwlasny + ", opis=" + opis + ", kg=" + kg + ", szt=" + szt + ", kwotaWn=" + kwotaWn + ", kontoWn=" + opiskontaWn + ", kwotaMa=" + kwotaMa + ", kontoMa=" + opiskontaMa + '}';
    }
            
    
    
}
