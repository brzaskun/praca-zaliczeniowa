/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Dok;
import entity.Klienci;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import vies.Vies;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class VatUe implements Serializable{
    private static final long serialVersionUID = -8660608026514979599L;
    private String transakcja;
    private Klienci kontrahent;
    private Double netto;
    private Double nettoprzedkorekta;
    private double nettowaluta;
    private int liczbadok;
    private String nazwawaluty;
    @Lob
    @OneToMany(fetch = FetchType.EAGER)
    private List<Dok> zawiera;
    @Lob
    @OneToMany(fetch = FetchType.EAGER)
    private List<Dokfk> zawierafk;
    private Vies vies;
    private boolean korekta;

    public VatUe() {
        this.zawiera = new ArrayList<>();
        this.zawierafk = new ArrayList<>();
    }


    public VatUe(String transakcja, Klienci kontrahent, Double netto, int liczbadok, List<Dok> zawiera) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.liczbadok = liczbadok;
        this.zawiera = zawiera;
        this.zawiera = new ArrayList<>();
        this.zawierafk = new ArrayList<>();
    }
    
    public VatUe(String transakcja, Klienci kontrahent, double netto, double nettowal) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.nettowaluta = nettowal;
        this.zawiera = new ArrayList<>();
        this.zawierafk = new ArrayList<>();
    }
    
    public VatUe(String transakcja, Klienci kontrahent, Double netto, int liczbadok) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.liczbadok = liczbadok;
        this.zawiera = new ArrayList<>();
        this.zawierafk = new ArrayList<>();
    }


     public String getTransakcja() {
        return transakcja;
    }
    
    public void setTransakcja(String transakcja) {
        this.transakcja = transakcja;
    }

    public Klienci getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(Klienci kontrahent) {
        this.kontrahent = kontrahent;
    }

    public Double getNettoprzedkorekta() {
        return nettoprzedkorekta;
    }

    public void setNettoprzedkorekta(Double nettoprzedkorekta) {
        this.nettoprzedkorekta = nettoprzedkorekta;
    }

    public Double getNetto() {
        return netto;
    }

    public void setNetto(Double netto) {
        this.netto = netto;
    }

  
    public int getLiczbadok() {
        return liczbadok;
    }

    public void setLiczbadok(int liczbadok) {
        this.liczbadok = liczbadok;
    }

    public List<Dok> getZawiera() {
        return zawiera;
    }

    public void setZawiera(List<Dok> zawiera) {
        this.zawiera = zawiera;
    }

    public List<Dokfk> getZawierafk() {
        return zawierafk;
    }

    public void setZawierafk(List<Dokfk> zawierafk) {
        this.zawierafk = zawierafk;
    }

    public double getNettowaluta() {
        return nettowaluta;
    }

    public void setNettowaluta(double nettowaluta) {
        this.nettowaluta = nettowaluta;
    }

    public boolean isKorekta() {
        return korekta;
    }

    public void setKorekta(boolean korekta) {
        this.korekta = korekta;
    }

    public String getNazwawaluty() {
        return nazwawaluty;
    }

    public void setNazwawaluty(String nazwawaluty) {
        this.nazwawaluty = nazwawaluty;
    }

    public Vies getVies() {
        return vies;
    }

    public void setVies(Vies vies) {
        this.vies = vies;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.transakcja);
        hash = 59 * hash + Objects.hashCode(this.kontrahent);
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
        final VatUe other = (VatUe) obj;
        if (!Objects.equals(this.transakcja, other.transakcja)) {
            return false;
        }
        if (!Objects.equals(this.kontrahent, other.kontrahent)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VatUe{" + "transakcja=" + transakcja + ", kontrahent=" + kontrahent.getNpelna() + ", netto=" + netto + ", nettowaluta=" + nettowaluta + ", liczbadok=" + liczbadok + ", nazwawaluty=" + nazwawaluty + ", zawiera=" + zawiera + ", zawierafk=" + zawierafk + '}';
    }

  
    
}
