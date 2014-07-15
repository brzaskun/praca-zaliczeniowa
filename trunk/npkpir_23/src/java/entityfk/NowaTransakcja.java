/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "nowatransakcja", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"wiersz", "stronaWnlubMa"})
})
@XmlRootElement
public class NowaTransakcja  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnowatransakcja", nullable = false)
    private Integer idnowatransakcja;
    @JoinColumn(name = "wiersz", referencedColumnName = "idwiersza")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Wiersze wiersz;
    @Column(name = "stronaWnlubMa")
    private String stronaWnlubMa;
    @Column(name="kwotapierwotna")
    private double kwotapierwotna;
    @Column(name="rozliczono")
    private double rozliczono;
    @Column(name="pozostalo")
    private double pozostalo;
    @Column(name = "roznicekursowe")
    private double roznicekursowe;
    @JoinColumn(name = "konto_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Konto kontoid;
    @Column(name = "rok")
    private String rok;
    @Column(name = "mc")
    private String mc;
    @Column(name = "datarozrachunku")
    private String datarozrachunku;
    
    
    


    public NowaTransakcja() {
        this.kwotapierwotna = 0.0;
        this.rozliczono = 0.0;
        this.pozostalo = 0.0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idnowatransakcja);
        hash = 79 * hash + Objects.hashCode(this.wiersz);
        hash = 79 * hash + Objects.hashCode(this.stronaWnlubMa);
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
        final NowaTransakcja other = (NowaTransakcja) obj;
        if (!Objects.equals(this.wiersz, other.wiersz)) {
            return false;
        }
        if (!Objects.equals(this.stronaWnlubMa, other.stronaWnlubMa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rozliczajacy{" + "idrozrachunku=" + idnowatransakcja + ", wiersz=" + wiersz + ", stronaWnlubMa=" + stronaWnlubMa + ", kwotapierwotna=" + kwotapierwotna + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo + '}';
    }

    public double getRoznicekursowe() {
        return roznicekursowe;
    }

    public void setRoznicekursowe(double roznicekursowe) {
        this.roznicekursowe = roznicekursowe;
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

   
    public Konto getKontoid() {
        return kontoid;
    }

    public void setKontoid(Konto kontoid) {
        this.kontoid = kontoid;
    }

    public Integer getIdnowatransakcja() {
        return idnowatransakcja;
    }

    public void setIdnowatransakcja(Integer idnowatransakcja) {
        this.idnowatransakcja = idnowatransakcja;
    }


    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getDatarozrachunku() {
        return datarozrachunku;
    }

    public void setDatarozrachunku(String datarozrachunku) {
        this.datarozrachunku = datarozrachunku;
    }

    public Wiersze getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersze wiersz) {
        this.wiersz = wiersz;
    }

   
    public String getStronaWnlubMa() {
        return stronaWnlubMa;
    }

    public void setStronaWnlubMa(String stronaWnlubMa) {
        this.stronaWnlubMa = stronaWnlubMa;
    }

     
    
}
