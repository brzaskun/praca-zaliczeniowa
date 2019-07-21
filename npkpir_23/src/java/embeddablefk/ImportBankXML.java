/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class ImportBankXML implements Serializable {
    private static final long serialVersionUID = 1L;

        private int nr;
        private String IBAN;
        private double kwota;
        private String waluta;
        private String wnma;
        private String datatransakcji;
        private String datawaluty;
        private String opistransakcji;
        private String kontrahent;
        private String kontrahentakraj;
        private String kontrahentaadres;
        private String nrtransakji;
        //typ transakcji
        //1 wpływ faktura
        //2 zapłata faktura
        //3 prowizja
        //4 wypłata karta
        //5 płatnośc karta
        //6 podatki
        private int typtransakcji;

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public String getWnma() {
        return wnma;
    }

    public void setWnma(String wnma) {
        this.wnma = wnma;
    }

    public String getDatatransakcji() {
        return datatransakcji;
    }

    public void setDatatransakcji(String datatransakcji) {
        this.datatransakcji = datatransakcji;
    }

    public String getDatawaluty() {
        return datawaluty;
    }

    public void setDatawaluty(String datawaluty) {
        this.datawaluty = datawaluty;
    }

    public String getOpistransakcji() {
        return opistransakcji;
    }

    public void setOpistransakcji(String opistransakcji) {
        this.opistransakcji = opistransakcji;
    }

    public String getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(String kontrahent) {
        this.kontrahent = kontrahent;
    }

    public String getKontrahentakraj() {
        return kontrahentakraj;
    }

    public void setKontrahentakraj(String kontrahentakraj) {
        this.kontrahentakraj = kontrahentakraj;
    }

    public String getKontrahentaadres() {
        return kontrahentaadres;
    }

    public void setKontrahentaadres(String kontrahentaadres) {
        this.kontrahentaadres = kontrahentaadres;
    }

    public String getNrtransakji() {
        return nrtransakji;
    }

    public void setNrtransakji(String nrtransakji) {
        this.nrtransakji = nrtransakji;
    }

    public int getTyptransakcji() {
        return typtransakcji;
    }

    public void setTyptransakcji(int typtransakcji) {
        this.typtransakcji = typtransakcji;
    }

        
        
}
