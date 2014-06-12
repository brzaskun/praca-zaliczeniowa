/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entityfk.Konto;
import entityfk.Rozrachunekfk;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Osito
 */
@Entity
public class WierszStronafk implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * to jest klucz ale nie moze byc EmbeddedId bo sie kloci z idwiersza. To pole jest osadzone w Wiersze
     * zostawilem to jestak zeby latow wyszukiwac i porownywac przez PK
     */
    @EmbeddedId
    public WierszStronafkPK wierszStronafkPK;
    @JoinColumn(name = "wiersz", referencedColumnName = "idwiersza")
    @OneToOne
    private Wiersze wiersz;
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "kwotaPLN")
    private double kwotaPLN;
    @Column(name = "kwotaWaluta")
    private double kwotaWaluta;
    @Column(name = "kurswaluty")
    private double kurswaluty;
    @Column(name = "symbolwaluty")
    private String symbolwaluty;
    @Column(name = "grafikawaluty")
    private String grafikawaluty;
    @Column(name = "nrtabelinbp")
    private String nrtabelinbp;
    @Column(name = "datawaluty")
    private String datawaluty;
    @JoinColumn(name = "konto", referencedColumnName = "id")
    @ManyToOne
    private Konto konto;
    @Column(name = "opiswiersza")
    private String opiswiersza;
    @OneToOne(mappedBy = "wierszStronafk", cascade = CascadeType.ALL, targetEntity = Rozrachunekfk.class,  orphanRemoval=true)
    private Rozrachunekfk rozrachunekfk;

    public WierszStronafk() {
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = 0.0;
        this.kurswaluty = 0.0;
        this.kwota = 0.0;
        this.wierszStronafkPK = new WierszStronafkPK();
    }

    public WierszStronafk(WierszStronafkPK wierszStronafkPK) {
        this.wierszStronafkPK = wierszStronafkPK;
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

    public Rozrachunekfk getRozrachunekfk() {
        return rozrachunekfk;
    }

    public void setRozrachunekfk(Rozrachunekfk rozrachunekfk) {
        this.rozrachunekfk = rozrachunekfk;
    }

   

    public Wiersze getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersze wiersz) {
        this.wiersz = wiersz;
    }

    
    
    
       
    
    
}
