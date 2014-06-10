/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tabelanbp.findAll", query = "SELECT t FROM Tabelanbp t"),
    @NamedQuery(name = "Tabelanbp.findByNrtabeli", query = "SELECT t FROM Tabelanbp t WHERE t.tabelanbpPK.nrtabeli = :nrtabeli"),
    @NamedQuery(name = "Tabelanbp.findBySymbolwaluty", query = "SELECT t FROM Tabelanbp t WHERE t.tabelanbpPK.symbolwaluty = :symbolwaluty"),
    @NamedQuery(name = "Tabelanbp.findByDatatabeli", query = "SELECT t FROM Tabelanbp t WHERE t.datatabeli = :datatabeli"),
    @NamedQuery(name = "Tabelanbp.findByDatatabeliSymbolwaluty", query = "SELECT t FROM Tabelanbp t WHERE t.datatabeli = :datatabeli AND t.tabelanbpPK.symbolwaluty = :symbolwaluty"),
    @NamedQuery(name = "Tabelanbp.findByKurssredni", query = "SELECT t FROM Tabelanbp t WHERE t.kurssredni = :kurssredni")})
public class Tabelanbp implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TabelanbpPK tabelanbpPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String datatabeli;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double kurssredni;
    //to jest dlatego ze dla faktury typu FVZ caly dokument jest w jednym kursie
    @OneToMany(mappedBy = "tabelanbp")
    private List<Dokfk> Dokfk;
    //natomiast dla wyciagow bankowych jest inaczej tam liczy sie kazdy wiersz
    @OneToMany(mappedBy = "tabelanbp")
    private List<Wiersze> Wiersze;

    public Tabelanbp() {
    }

    public Tabelanbp(TabelanbpPK tabelanbpPK) {
        this.tabelanbpPK = tabelanbpPK;
    }

    public Tabelanbp(TabelanbpPK tabelanbpPK, String datatabeli, double kurssredni) {
        this.tabelanbpPK = tabelanbpPK;
        this.datatabeli = datatabeli;
        this.kurssredni = kurssredni;
    }

    public Tabelanbp(String nrtabeli, String symbolwaluty) {
        this.tabelanbpPK = new TabelanbpPK(nrtabeli, symbolwaluty);
    }

    public TabelanbpPK getTabelanbpPK() {
        return tabelanbpPK;
    }

    public void setTabelanbpPK(TabelanbpPK tabelanbpPK) {
        this.tabelanbpPK = tabelanbpPK;
    }

    public String getDatatabeli() {
        return datatabeli;
    }

    public void setDatatabeli(String datatabeli) {
        this.datatabeli = datatabeli;
    }

    public double getKurssredni() {
        return kurssredni;
    }

    public void setKurssredni(double kurssredni) {
        this.kurssredni = kurssredni;
    }

    public List<Dokfk> getDokfk() {
        return Dokfk;
    }

    public void setDokfk(List<Dokfk> Dokfk) {
        this.Dokfk = Dokfk;
    }

    public List<Wiersze> getWiersze() {
        return Wiersze;
    }

    public void setWiersze(List<Wiersze> Wiersze) {
        this.Wiersze = Wiersze;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tabelanbpPK != null ? tabelanbpPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tabelanbp)) {
            return false;
        }
        Tabelanbp other = (Tabelanbp) object;
        if ((this.tabelanbpPK == null && other.tabelanbpPK != null) || (this.tabelanbpPK != null && !this.tabelanbpPK.equals(other.tabelanbpPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Tabelanbp[ tabelanbpPK=" + tabelanbpPK + " ]";
    }
    
}
