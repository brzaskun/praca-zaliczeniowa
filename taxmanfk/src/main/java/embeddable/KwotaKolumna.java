/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class KwotaKolumna implements Serializable{
    
    private static final long serialVersionUID = 8267815530007898066L;
    
    private Double netto;
    private Double vat;
    private Double brutto;
    private String nazwakolumny;
    private String dowykorzystania;

    public KwotaKolumna() {
        this.netto = 0.0;
        this.nazwakolumny = "";
    }

    public KwotaKolumna(double kwota, String nazwakolumny) {
        this.netto = kwota;
        this.nazwakolumny = nazwakolumny;
    }

    
    
    public Double getNetto() {
        return netto;
    }

    public void setNetto(Double netto) {
        this.netto = netto;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getBrutto() {
        return brutto;
    }

    public void setBrutto(Double brutto) {
        this.brutto = brutto;
    }
    
    public String getNazwakolumny() {
        return nazwakolumny;
    }

    public void setNazwakolumny(String nazwakolumny) {
        this.nazwakolumny = nazwakolumny;
    }

    public String getDowykorzystania() {
        return dowykorzystania;
    }

    public void setDowykorzystania(String dowykorzystania) {
        this.dowykorzystania = dowykorzystania;
    }

    
}
