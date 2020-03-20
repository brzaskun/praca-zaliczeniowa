/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entity.Klienci;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class InterpaperXLS implements Serializable {
    private static final long serialVersionUID = 1L;
    private int nr;
    private String nrfaktury;
    private Date dataotrzymania;
    private Date datawystawienia;
    private Date datasprzedaży;
    private Date dataobvat;
    private String kontrahent;
    private Klienci klient;
    private String klientnazwa;
    private String klientpaństwo;
    private String klientkod;
    private String klientmiasto;
    private String klientulica;
    private String klientdom;
    private String klientlokal;
    private String nip;
    private String walutaplatnosci;
    private double bruttowaluta;
    private double saldofaktury;
    private Date terminplatnosci;
    private int przekroczenieterminu;
    private Date ostatniawplata;
    private String sposobzaplaty;
    private double nettowaluta;
    private double vatwaluta;
    private double nettoPLN;
    private double nettoPLNvat;
    private double vatPLN;
    private double bruttoPLN;
    private boolean juzzaksiegowany;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.nrfaktury);
        hash = 41 * hash + Objects.hashCode(this.kontrahent);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InterpaperXLS other = (InterpaperXLS) obj;
        if (!Objects.equals(this.nrfaktury, other.nrfaktury)) {
            return false;
        }
        if (!Objects.equals(this.kontrahent, other.kontrahent)) {
            return false;
        }
        return true;
    }

    
    public String toString2() {
        return "InterpaperXLS{" + "nr=" + nr + ", nrfaktury=" + nrfaktury + ", datawystawienia=" + datawystawienia + ", datasprzeda\u017cy=" + datasprzedaży + ", kontrahent=" + kontrahent + ", walutaplatnosci=" + walutaplatnosci + ", bruttowaluta=" + bruttowaluta + ", saldofaktury=" + saldofaktury + ", terminplatnosci=" + terminplatnosci + ", przekroczenieterminu=" + przekroczenieterminu + ", ostatniawplata=" + ostatniawplata + ", sposobzaplaty=" + sposobzaplaty + ", nettowaluta=" + nettowaluta + ", vatwaluta=" + vatwaluta + ", nettoPLN=" + nettoPLN + ", vatPLN=" + vatPLN + ", bruttoPLN=" + bruttoPLN + '}';
    }

    
    
    @Override
    public String toString() {
        return "InterpaperXLS{" + "nrfaktury=" + nrfaktury + ", datawystawienia=" + datawystawienia + ", datasprzeda\u017cy=" + datasprzedaży + ", kontrahent=" + kontrahent + ", walutaplatnosci=" + walutaplatnosci + ", bruttowaluta=" + bruttowaluta + ", nettowaluta=" + nettowaluta + ", vatwaluta=" + vatwaluta + '}';
    }

    
    public String getAdres() {
        return this.klientkod+" "+this.klientmiasto+" "+this.klientulica+" "+this.klientdom;
    }
    
    public String getVatstawka() {
        String zwrot = "błąd!";
        if (this.nettowaluta!=0.0) {
            double stawka = this.vatwaluta/this.nettowaluta;
            stawka = Z.z(stawka)*100;
            zwrot = stawka+"%";
        }
        return zwrot;
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public int getNr() {
        return nr;
    }
    
    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
    
    public String getNrfaktury() {
        return nrfaktury;
    }
    
    public void setNrfaktury(String nrfaktury) {
        this.nrfaktury = nrfaktury;
    }
    
    public Date getDatawystawienia() {
        return datawystawienia;
    }

    public Date getDataotrzymania() {
        return dataotrzymania;
    }

    public void setDataotrzymania(Date dataotrzymania) {
        this.dataotrzymania = dataotrzymania;
    }
    
    public void setDatawystawienia(Date datawystawienia) {
        this.datawystawienia = datawystawienia;
    }
    
    public Date getDatasprzedaży() {
        return datasprzedaży;
    }
    
    public void setDatasprzedaży(Date datasprzedaży) {
        this.datasprzedaży = datasprzedaży;
    }
    
    public String getKontrahent() {
        return kontrahent;
    }

    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
    }
    
    public void setKontrahent(String kontrahent) {
        this.kontrahent = kontrahent;
    }
    
    public String getWalutaplatnosci() {
        return walutaplatnosci;
    }
    
    public void setWalutaplatnosci(String walutaplatnosci) {
        this.walutaplatnosci = walutaplatnosci;
    }
    
    public double getBruttowaluta() {
        return bruttowaluta;
    }
    
    public void setBruttowaluta(double bruttowaluta) {
        this.bruttowaluta = bruttowaluta;
    }
    
    public double getSaldofaktury() {
        return saldofaktury;
    }
    
    public void setSaldofaktury(double saldofaktury) {
        this.saldofaktury = saldofaktury;
    }
    
    public Date getTerminplatnosci() {
        return terminplatnosci;
    }
    
    public void setTerminplatnosci(Date terminplatnosci) {
        this.terminplatnosci = terminplatnosci;
    }
    
    public int getPrzekroczenieterminu() {
        return przekroczenieterminu;
    }
    
    public void setPrzekroczenieterminu(int przekroczenieterminu) {
        this.przekroczenieterminu = przekroczenieterminu;
    }
    
    public Date getOstatniawplata() {
        return ostatniawplata;
    }
    
    public void setOstatniawplata(Date ostatniawplata) {
        this.ostatniawplata = ostatniawplata;
    }
    
    public String getSposobzaplaty() {
        return sposobzaplaty;
    }
    
    public void setSposobzaplaty(String sposobzaplaty) {
        this.sposobzaplaty = sposobzaplaty;
    }
    
    public double getNettowaluta() {
        return nettowaluta;
    }
    
    public void setNettowaluta(double nettowaluta) {
        this.nettowaluta = nettowaluta;
    }
    
    public double getVatwaluta() {
        return vatwaluta;
    }
    
    public void setVatwaluta(double vatwaluta) {
        this.vatwaluta = vatwaluta;
    }
    
    public double getNettoPLN() {
        return nettoPLN;
    }
    
    public void setNettoPLN(double nettoPLN) {
        this.nettoPLN = nettoPLN;
    }

    public double getNettoPLNvat() {
        return nettoPLNvat;
    }

    public void setNettoPLNvat(double nettoPLNvat) {
        this.nettoPLNvat = nettoPLNvat;
    }
    
    public double getVatPLN() {
        return vatPLN;
    }
    
    public void setVatPLN(double vatPLN) {
        this.vatPLN = vatPLN;
    }

    public Date getDataobvat() {
        return dataobvat;
    }

    public void setDataobvat(Date dataobvat) {
        this.dataobvat = dataobvat;
    }
    
    public double getBruttoPLN() {
        return bruttoPLN;
    }
    
    public void setBruttoPLN(double bruttoPLN) {
        this.bruttoPLN = bruttoPLN;
    }
    
    public boolean isJuzzaksiegowany() {
        return juzzaksiegowany;
    }

    public void setJuzzaksiegowany(boolean juzzaksiegowany) {
        this.juzzaksiegowany = juzzaksiegowany;
    }
    
//</editor-fold>

    public String getKlientnazwa() {
        return klientnazwa;
    }

    public void setKlientnazwa(String klientnazwa) {
        this.klientnazwa = klientnazwa;
    }

    public String getKlientpaństwo() {
        return klientpaństwo;
    }

    public void setKlientpaństwo(String klientpaństwo) {
        this.klientpaństwo = klientpaństwo;
    }

    public String getKlientkod() {
        return klientkod;
    }

    public void setKlientkod(String klientkod) {
        this.klientkod = klientkod;
    }

    public String getKlientmiasto() {
        return klientmiasto;
    }

    public void setKlientmiasto(String klientmiasto) {
        this.klientmiasto = klientmiasto;
    }

    public String getKlientulica() {
        return klientulica;
    }

    public void setKlientulica(String klientulica) {
        this.klientulica = klientulica;
    }

    public String getKlientdom() {
        return klientdom;
    }

    public void setKlientdom(String klientdom) {
        this.klientdom = klientdom;
    }

    public String getKlientlokal() {
        return klientlokal;
    }

    public void setKlientlokal(String klientlokal) {
        this.klientlokal = klientlokal;
    }

    

    
}
