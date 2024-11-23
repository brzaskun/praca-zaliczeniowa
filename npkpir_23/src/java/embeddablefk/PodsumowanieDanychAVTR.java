/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package embeddablefk;

/**
 *
 * @author Osito
 */
import java.util.Objects;

public class PodsumowanieDanychAVTR {
    private int id; // Nowe pole
    private String jurysdykcja;
    private String waluta;
    private double nettoWaluta;
    private double vatWaluta;
    private double netto;
    private double vat;
    private double stawkaVat;
    private double kurs;

    // Konstruktor
    public PodsumowanieDanychAVTR(int id, String jurysdykcja, String waluta, double nettoWaluta, double vatWaluta, double netto, double vat, double stawkaVat, double kurs) {
        this.id = id;
        this.jurysdykcja = jurysdykcja;
        this.waluta = waluta;
        this.nettoWaluta = nettoWaluta;
        this.vatWaluta = vatWaluta;
        this.netto = netto;
        this.vat = vat;
        this.stawkaVat = stawkaVat;
        this.kurs = kurs;
    }

    // Gettery i Settery
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJurysdykcja() {
        return jurysdykcja;
    }

    public void setJurysdykcja(String jurysdykcja) {
        this.jurysdykcja = jurysdykcja;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public double getNettoWaluta() {
        return nettoWaluta;
    }

    public void setNettoWaluta(double nettoWaluta) {
        this.nettoWaluta = nettoWaluta;
    }

    public double getVatWaluta() {
        return vatWaluta;
    }

    public void setVatWaluta(double vatWaluta) {
        this.vatWaluta = vatWaluta;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getStawkaVat() {
        return stawkaVat;
    }

    public void setStawkaVat(double stawkaVat) {
        this.stawkaVat = stawkaVat;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    // Metody pomocnicze
    @Override
    public String toString() {
        return "PodsumowanieDanych{" +
                "id=" + id +
                ", jurysdykcja='" + jurysdykcja + '\'' +
                ", waluta='" + waluta + '\'' +
                ", nettoWaluta=" + nettoWaluta +
                ", vatWaluta=" + vatWaluta +
                ", netto=" + netto +
                ", vat=" + vat +
                ", stawkaVat=" + stawkaVat +
                ", kurs=" + kurs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PodsumowanieDanychAVTR that = (PodsumowanieDanychAVTR) o;
        return id == that.id &&
                Double.compare(that.nettoWaluta, nettoWaluta) == 0 &&
                Double.compare(that.vatWaluta, vatWaluta) == 0 &&
                Double.compare(that.netto, netto) == 0 &&
                Double.compare(that.vat, vat) == 0 &&
                Double.compare(that.stawkaVat, stawkaVat) == 0 &&
                Double.compare(that.kurs, kurs) == 0 &&
                Objects.equals(jurysdykcja, that.jurysdykcja) &&
                Objects.equals(waluta, that.waluta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jurysdykcja, waluta, nettoWaluta, vatWaluta, netto, vat, stawkaVat, kurs);
    }
}

