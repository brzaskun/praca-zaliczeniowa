/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import embeddablefk.WierszStronafk;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rozrachunekfk")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rozrachunekfk.findAll", query = "SELECT w FROM Rozrachunekfk w"),
    @NamedQuery(name = "Rozrachunekfk.findByWierszStronafk", query = "SELECT w FROM Rozrachunekfk w WHERE w.wierszStronafk.wierszStronafkPK = :wierszStronafkPK"),
    @NamedQuery(name = "Rozrachunekfk.findRozrachunkifkByKonto", query = "SELECT w FROM Rozrachunekfk w WHERE w.kontoid.pelnynumer = :nrkonta AND w.wierszStronafk.wierszStronafkPK.stronaWnlubMa =:wnmaNew")
})
public class Rozrachunekfk  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrozrachunku", nullable = false)
    private Integer idrozrachunku;
    @JoinColumns({
        @JoinColumn(name="typdokumentu", referencedColumnName="typdokumentu"),
        @JoinColumn(name="nrkolejnydokumentu", referencedColumnName="nrkolejnydokumentu"),
        @JoinColumn(name="nrPorzadkowyWiersza", referencedColumnName="nrPorzadkowyWiersza"),
        @JoinColumn(name="stronaWnlubMa", referencedColumnName="stronaWnlubMa")
    })
    @ManyToOne
    private WierszStronafk wierszStronafk;
    @Column(name="kwotapierwotna")
    private double kwotapierwotna;
    @Column(name="rozliczono")
    private double rozliczono;
    @Column(name="pozostalo")
    private double pozostalo;
    @Column(name="nowatransakcja")
    private boolean nowatransakcja;
    @JoinColumn(name = "konto_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Konto kontoid;
    


    public Rozrachunekfk() {
        this.kwotapierwotna = 0.0;
        this.rozliczono = 0.0;
        this.pozostalo = 0.0;
        this.nowatransakcja = false;
    }

    public Rozrachunekfk(WierszStronafk wierszStronafk) {
        this.wierszStronafk = wierszStronafk;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.wierszStronafk);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.kwotapierwotna) ^ (Double.doubleToLongBits(this.kwotapierwotna) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.rozliczono) ^ (Double.doubleToLongBits(this.rozliczono) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.pozostalo) ^ (Double.doubleToLongBits(this.pozostalo) >>> 32));
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
        final Rozrachunekfk other = (Rozrachunekfk) obj;
        if (!Objects.equals(this.wierszStronafk, other.wierszStronafk)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Rozrachunek{" + "wierszStronafk=" + wierszStronafk + ", kwotapierwotna=" + kwotapierwotna + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo + '}';
    }

    public WierszStronafk getWierszStronafk() {
        return wierszStronafk;
    }

    public void setWierszStronafk(WierszStronafk wierszStronafk) {
        this.wierszStronafk = wierszStronafk;
    }

    public double getKwotapierwotna() {
        return kwotapierwotna;
    }

    public void setKwotapierwotna(double kwotapierwotna) {
        this.kwotapierwotna = kwotapierwotna;
    }

    public double getRozliczono() {
        return rozliczono;
    }

    public void setRozliczono(double rozliczono) {
        this.rozliczono = rozliczono;
    }

    public double getPozostalo() {
        return pozostalo;
    }

    public void setPozostalo(double pozostalo) {
        this.pozostalo = pozostalo;
    }

    public boolean isNowatransakcja() {
        return nowatransakcja;
    }

    public void setNowatransakcja(boolean nowatransakcja) {
        this.nowatransakcja = nowatransakcja;
    }
    
    public Konto getKontoid() {
        return kontoid;
    }

    public void setKontoid(Konto kontoid) {
        this.kontoid = kontoid;
    }

    public Integer getIdrozrachunku() {
        return idrozrachunku;
    }

    public void setIdrozrachunku(Integer idrozrachunku) {
        this.idrozrachunku = idrozrachunku;
    }
   
    
    
}
