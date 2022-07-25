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
public class WierszDokfk  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String datadok;
    private String dataoperacji;
    private String iddok;
    private String kontrahent;
    private String nrwlasny;
    private String opis;
    private double wartosc;
    private String waluta;

    public WierszDokfk() {
    }

    public WierszDokfk(int id, String datadok, String dataoperacji, String iddok, String kontrahent, String nrwlasny, String opis, double wartosc, String waluta) {
        this.id = id;
        this.datadok = datadok;
        this.dataoperacji = dataoperacji;
        this.iddok = iddok;
        this.kontrahent = kontrahent;
        this.nrwlasny = nrwlasny;
        this.opis = opis;
        this.wartosc = wartosc;
        this.waluta = waluta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatadok() {
        return datadok;
    }

    public void setDatadok(String datadok) {
        this.datadok = datadok;
    }

    public String getDataoperacji() {
        return dataoperacji;
    }

    public void setDataoperacji(String dataoperacji) {
        this.dataoperacji = dataoperacji;
    }

    public String getIddok() {
        return iddok;
    }

    public void setIddok(String iddok) {
        this.iddok = iddok;
    }

    public String getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(String kontrahent) {
        this.kontrahent = kontrahent;
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

    public double getWartosc() {
        return wartosc;
    }

    public void setWartosc(double wartosc) {
        this.wartosc = wartosc;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    @Override
    public String toString() {
        return "WierszDokfk{" + "id=" + id + ", datadok=" + datadok + ", dataoperacji=" + dataoperacji + ", iddok=" + iddok + ", kontrahent=" + kontrahent + ", nrwlasny=" + nrwlasny + ", opis=" + opis + ", wartosc=" + wartosc + ", waluta=" + waluta + '}';
    }

   
    
}
