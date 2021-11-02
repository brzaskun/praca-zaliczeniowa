/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Faktura;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class FZTresc implements Serializable {

    private static final long serialVersionUID = 1L;
    private String mc;
    private String nrfakt;
    private String data;
    private double netto;
    private double vat;
    private double brutto;
    private int iloscwierszy;
    private String opis;
    private String waluta;
    private Faktura faktura;

    public FZTresc() {
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getNrfakt() {
        return nrfakt;
    }

    public void setNrfakt(String nrfakt) {
        this.nrfakt = nrfakt;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getVat() {
        return vat;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Faktura getFaktura() {
        return faktura;
    }

    public void setFaktura(Faktura faktura) {
        this.faktura = faktura;
    }

    public int getIloscwierszy() {
        return iloscwierszy;
    }

    public void setIloscwierszy(int iloscwierszy) {
        this.iloscwierszy = iloscwierszy;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nrfakt);
        hash = 97 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FZTresc other = (FZTresc) obj;
        if (!Objects.equals(this.nrfakt, other.nrfakt)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FZTresc{" + "mc=" + mc + ", nrfakt=" + nrfakt + ", data=" + data + ", netto=" + netto + ", vat=" + vat + ", brutto=" + brutto + ", opis=" + opis + ", faktura=" + faktura + '}';
    }

}
