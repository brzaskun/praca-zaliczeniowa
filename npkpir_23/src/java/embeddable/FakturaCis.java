/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

/**
 *
 * @author Osito
 */
public class FakturaCis {

    private int id;
    private String numer_faktury;
    private String data_wystawienia;
    private String seller_name;
    private String seller_addr;
    private String seller_zip;
    private String seller_city;
    private String seller_country;
    private String seller_nip;
    private String buyer_name;
    private String buyer_addr1;
    private String buyer_addr2;
    private String buyer_zip;
    private String buyer_city;
    private String buyer_country;
    private String buyer_iso_kod;
    private String buyer_nip;
    private double stawka_vat;
    private String waluta;
    private double kurs_waluty;
    private String data_kursu;
    private double cena_total_pln;
    private double cena_total_netto_pln;
    private double podatek_vat_pln;
    private double cena_total_waluta;
    private double cena_total_netto_waluta;
    private double podatek_vat_waluta;
    private double podatek_vat_waluta_kontrola;

    public FakturaCis() {
    }

    @Override
    public String toString() {
        return "FakturaCis{" + "numer_faktury=" + numer_faktury + ", data_wystawienia=" + data_wystawienia + ", buyer_name=" + buyer_name + ", buyer_country=" + buyer_country + ", buyer_nip=" + buyer_nip + ", stawka_vat=" + stawka_vat + ", waluta=" + waluta + ", cena_total_netto_waluta=" + cena_total_netto_waluta + ", podatek_vat_waluta=" + podatek_vat_waluta + '}';
    }
//<editor-fold defaultstate="collapsed" desc="comment">

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPodatek_vat_waluta_kontrola() {
        return podatek_vat_waluta_kontrola;
    }

    public void setPodatek_vat_waluta_kontrola(double podatek_vat_waluta_kontrola) {
        this.podatek_vat_waluta_kontrola = podatek_vat_waluta_kontrola;
    }

    public String getNumer_faktury() {
        return numer_faktury;
    }

    public void setNumer_faktury(String numer_faktury) {
        this.numer_faktury = numer_faktury;
    }

    public String getData_wystawienia() {
        return data_wystawienia;
    }

    public void setData_wystawienia(String data_wystawienia) {
        this.data_wystawienia = data_wystawienia;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getSeller_addr() {
        return seller_addr;
    }

    public void setSeller_addr(String seller_addr) {
        this.seller_addr = seller_addr;
    }

    public String getSeller_zip() {
        return seller_zip;
    }

    public void setSeller_zip(String seller_zip) {
        this.seller_zip = seller_zip;
    }

    public String getSeller_city() {
        return seller_city;
    }

    public void setSeller_city(String seller_city) {
        this.seller_city = seller_city;
    }

    public String getSeller_country() {
        return seller_country;
    }

    public void setSeller_country(String seller_country) {
        this.seller_country = seller_country;
    }

    public String getSeller_nip() {
        return seller_nip;
    }

    public void setSeller_nip(String seller_nip) {
        this.seller_nip = seller_nip;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public String getBuyer_addr1() {
        return buyer_addr1;
    }

    public void setBuyer_addr1(String buyer_addr1) {
        this.buyer_addr1 = buyer_addr1;
    }

    public String getBuyer_addr2() {
        return buyer_addr2;
    }

    public void setBuyer_addr2(String buyer_addr2) {
        this.buyer_addr2 = buyer_addr2;
    }

    public String getBuyer_zip() {
        return buyer_zip;
    }

    public void setBuyer_zip(String buyer_zip) {
        this.buyer_zip = buyer_zip;
    }

    public String getBuyer_city() {
        return buyer_city;
    }

    public void setBuyer_city(String buyer_city) {
        this.buyer_city = buyer_city;
    }

    public String getBuyer_country() {
        return buyer_country;
    }

    public void setBuyer_country(String buyer_country) {
        this.buyer_country = buyer_country;
    }

    public String getBuyer_iso_kod() {
        return buyer_iso_kod;
    }

    public void setBuyer_iso_kod(String buyer_iso_kod) {
        this.buyer_iso_kod = buyer_iso_kod;
    }

    public String getBuyer_nip() {
        return buyer_nip;
    }

    public void setBuyer_nip(String buyer_nip) {
        this.buyer_nip = buyer_nip;
    }

    public double getStawka_vat() {
        return stawka_vat;
    }

    public void setStawka_vat(double stawka_vat) {
        this.stawka_vat = stawka_vat;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public double getKurs_waluty() {
        return kurs_waluty;
    }

    public void setKurs_waluty(double kurs_waluty) {
        this.kurs_waluty = kurs_waluty;
    }

    public String getData_kursu() {
        return data_kursu;
    }

    public void setData_kursu(String data_kursu) {
        this.data_kursu = data_kursu;
    }

    public double getCena_total_pln() {
        return cena_total_pln;
    }

    public void setCena_total_pln(double cena_total_pln) {
        this.cena_total_pln = cena_total_pln;
    }

    public double getCena_total_netto_pln() {
        return cena_total_netto_pln;
    }

    public void setCena_total_netto_pln(double cena_total_netto_pln) {
        this.cena_total_netto_pln = cena_total_netto_pln;
    }

    public double getPodatek_vat_pln() {
        return podatek_vat_pln;
    }

    public void setPodatek_vat_pln(double podatek_vat_pln) {
        this.podatek_vat_pln = podatek_vat_pln;
    }

    public double getCena_total_waluta() {
        return cena_total_waluta;
    }

    public void setCena_total_waluta(double cena_total_waluta) {
        this.cena_total_waluta = cena_total_waluta;
    }

    public double getCena_total_netto_waluta() {
        return cena_total_netto_waluta;
    }

    public void setCena_total_netto_waluta(double cena_total_netto_waluta) {
        this.cena_total_netto_waluta = cena_total_netto_waluta;
    }

    public double getPodatek_vat_waluta() {
        return podatek_vat_waluta;
    }

    public void setPodatek_vat_waluta(double podatek_vat_waluta) {
        this.podatek_vat_waluta = podatek_vat_waluta;
    }
//</editor-fold>

}
