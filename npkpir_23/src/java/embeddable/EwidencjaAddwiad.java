/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Klienci;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class EwidencjaAddwiad implements Serializable{
    private static final long serialVersionUID = -595126647192102178L;
    
    private int lp;
    private String opis;
    private double netto;
    private double vat;
    private double brutto;
    private String opzw;
    private String datadokumentu;
    private String dataoperacji;
    private Klienci klient;
    private Wiersz wiersz;

    public EwidencjaAddwiad() {
    }

        
    public EwidencjaAddwiad(String opis, double netto, double vat, double brutto, String opzw) {
        this.opis = opis;
        this.netto = netto;
        this.vat = vat;
        this.brutto = brutto;
        this.opzw = opzw;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.netto) ^ (Double.doubleToLongBits(this.netto) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.wiersz);
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
        final EwidencjaAddwiad other = (EwidencjaAddwiad) obj;
        if (!Objects.equals(this.opis, other.opis)) {
            return false;
        }
        if (!Objects.equals(this.klient, other.klient)) {
            return false;
        }
        if (!Objects.equals(this.wiersz, other.wiersz)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EwidencjaAddwiad{" + "opis=" + opis + ", netto=" + netto + ", vat=" + vat + ", brutto=" + brutto + ", opzw=" + opzw + ", datadokumentu=" + datadokumentu + ", dataoperacji=" + dataoperacji + ", klient=" + klient + ", wiersz=" + wiersz + '}';
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

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public String getOpzw() {
        return opzw;
    }

    public void setOpzw(String opzw) {
        this.opzw = opzw;
    }

    public String getDatadokumentu() {
        return datadokumentu;
    }

    public void setDatadokumentu(String datadokumentu) {
        this.datadokumentu = datadokumentu;
    }

    public String getDataoperacji() {
        return dataoperacji;
    }

    public void setDataoperacji(String dataoperacji) {
        this.dataoperacji = dataoperacji;
    }

    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
    }

    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }
    
    
    
}
