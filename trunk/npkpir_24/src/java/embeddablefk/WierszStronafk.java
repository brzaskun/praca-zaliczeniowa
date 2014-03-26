/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Konto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

/**
 *
 * @author Osito
 */
@Embeddable
public class WierszStronafk implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * to jest klucz ale nie moze byc EmbeddedId bo sie kloci z idwiersza. To pole jest osadzone w Wiersze
     * zostawilem to jestak zeby latow wyszukiwac i porownywac przez PK
     */
    protected WierszStronafkPK wierszStronafkPK;
    private double kwota;
    private double kwotaPLN;
    private double kwotaWaluta;
    private double kurswaluty;
    private String symbolwaluty;
    private String grafikawaluty;
    private String nrtabelinbp;
    private String datawaluty;
    private Konto konto;
    private String nrwlasnydokumentu;
    private String opisdokumentu;
    private String opiswiersza;

    public WierszStronafk() {
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = 0.0;
        this.kurswaluty = 0.0;
        this.kwota = 0.0;
        this.wierszStronafkPK = new WierszStronafkPK();
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.wierszStronafkPK);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.kwota) ^ (Double.doubleToLongBits(this.kwota) >>> 32));
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
        final WierszStronafk other = (WierszStronafk) obj;
        if (!Objects.equals(this.wierszStronafkPK, other.wierszStronafkPK)) {
            return false;
        }
        if (Double.doubleToLongBits(this.kwota) != Double.doubleToLongBits(other.kwota)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WierszStronafk{" + "wierszStronafkPK=" + wierszStronafkPK + ", kwota=" + kwota + '}';
    }

    public WierszStronafkPK getWierszStronafkPK() {
        return wierszStronafkPK;
    }

    public void setWierszStronafkPK(WierszStronafkPK wierszStronafkPK) {
        this.wierszStronafkPK = wierszStronafkPK;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public String getNrwlasnydokumentu() {
        return nrwlasnydokumentu;
    }

    public void setNrwlasnydokumentu(String nrwlasnydokumentu) {
        this.nrwlasnydokumentu = nrwlasnydokumentu;
    }

    public String getOpisdokumentu() {
        return opisdokumentu;
    }

    public void setOpisdokumentu(String opisdokumentu) {
        this.opisdokumentu = opisdokumentu;
    }

    public String getOpiswiersza() {
        return opiswiersza;
    }

    public void setOpiswiersza(String opiswiersza) {
        this.opiswiersza = opiswiersza;
    }

    public double getKwotaPLN() {
        return kwotaPLN;
    }

    public void setKwotaPLN(double kwotaPLN) {
        this.kwotaPLN = kwotaPLN;
    }

    public double getKwotaWaluta() {
        return kwotaWaluta;
    }

    public void setKwotaWaluta(double kwotaWaluta) {
        this.kwotaWaluta = kwotaWaluta;
    }

    public double getKurswaluty() {
        return kurswaluty;
    }

    public void setKurswaluty(double kurswaluty) {
        this.kurswaluty = kurswaluty;
    }

    public String getSymbolwaluty() {
        return symbolwaluty;
    }

    public void setSymbolwaluty(String symbolwaluty) {
        this.symbolwaluty = symbolwaluty;
    }

    public String getNrtabelinbp() {
        return nrtabelinbp;
    }

    public void setNrtabelinbp(String nrtabelinbp) {
        this.nrtabelinbp = nrtabelinbp;
    }

    public String getDatawaluty() {
        return datawaluty;
    }

    public void setDatawaluty(String datawaluty) {
        this.datawaluty = datawaluty;
    }

    public String getGrafikawaluty() {
        return grafikawaluty;
    }

    public void setGrafikawaluty(String grafikawaluty) {
        this.grafikawaluty = grafikawaluty;
    }

    
       
    
    
}
