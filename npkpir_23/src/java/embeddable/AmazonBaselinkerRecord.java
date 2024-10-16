/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package embeddable;

import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class AmazonBaselinkerRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String typ;
    private String pelnyNumer;
    private String nabywca;
    private String adres;
    private String kodPocztowy;
    private String miasto;
    private String nip;
    private String dataWystawienia;
    private String dataSprzedazy;
    private double netto;
    private double vat;
    private double nettopln;
    private double vatpln;
    private double kwotaVat;
    private double brutto;
    private String sposobPlatnosci;
    private String waluta;
    private String kursWaluty;
    private String kraj;
    private String dokumentPowiazany;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getPelnyNumer() {
        return pelnyNumer;
    }

    public void setPelnyNumer(String pelnyNumer) {
        this.pelnyNumer = pelnyNumer;
    }

    public String getNabywca() {
        return nabywca;
    }

    public void setNabywca(String nabywca) {
        this.nabywca = nabywca;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getDataWystawienia() {
        return dataWystawienia;
    }

    public void setDataWystawienia(String dataWystawienia) {
        this.dataWystawienia = dataWystawienia;
    }

    public String getDataSprzedazy() {
        return dataSprzedazy;
    }

    public void setDataSprzedazy(String dataSprzedazy) {
        this.dataSprzedazy = dataSprzedazy;
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

    public double getNettopln() {
        return nettopln;
    }

    public void setNettopln(double nettopln) {
        this.nettopln = nettopln;
    }

    public double getVatpln() {
        return vatpln;
    }

    public void setVatpln(double vatpln) {
        this.vatpln = vatpln;
    }

    public double getKwotaVat() {
        return kwotaVat;
    }

    public void setKwotaVat(double kwotaVat) {
        this.kwotaVat = kwotaVat;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public String getSposobPlatnosci() {
        return sposobPlatnosci;
    }

    public void setSposobPlatnosci(String sposobPlatnosci) {
        this.sposobPlatnosci = sposobPlatnosci;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public String getKursWaluty() {
        return kursWaluty;
    }

    public void setKursWaluty(String kursWaluty) {
        this.kursWaluty = kursWaluty;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getDokumentPowiazany() {
        return dokumentPowiazany;
    }

    public void setDokumentPowiazany(String dokumentPowiazany) {
        this.dokumentPowiazany = dokumentPowiazany;
    }

 
}
