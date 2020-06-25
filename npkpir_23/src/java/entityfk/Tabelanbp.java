/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.Podatnik;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nrtabeli", "waluta"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tabelanbp.findAll", query = "SELECT t FROM Tabelanbp t"),
    @NamedQuery(name = "Tabelanbp.findAllRok", query = "SELECT t FROM Tabelanbp t WHERE t.datatabeli LIKE :rok AND t.recznie = 0"),
    @NamedQuery(name = "Tabelanbp.findAllRokRecznie", query = "SELECT t FROM Tabelanbp t WHERE t.datatabeli LIKE :rok AND t.recznie = 1"),
    @NamedQuery(name = "Tabelanbp.findByIdtabelanbp", query = "SELECT t FROM Tabelanbp t WHERE t.idtabelanbp = :idtabelanbp"),
    @NamedQuery(name = "Tabelanbp.findByDatatabeli", query = "SELECT t FROM Tabelanbp t WHERE t.datatabeli = :datatabeli"),
    @NamedQuery(name = "Tabelanbp.findByKurssredni", query = "SELECT t FROM Tabelanbp t WHERE t.kurssredni = :kurssredni"),
    @NamedQuery(name = "Tabelanbp.findByNrtabeli", query = "SELECT t FROM Tabelanbp t WHERE t.nrtabeli = :nrtabeli"),
    @NamedQuery(name = "Tabelanbp.findBySymbolWaluty", query = "SELECT t FROM Tabelanbp t WHERE t.waluta.symbolwaluty = :symbolwaluty"),
    @NamedQuery(name = "Tabelanbp.findBySymbolWalutyRokMc", query = "SELECT t FROM Tabelanbp t WHERE t.waluta.symbolwaluty = :symbolwaluty AND t.datatabeli LIKE :likedatatabeli "),
    @NamedQuery(name = "Tabelanbp.findByWaluta", query = "SELECT t FROM Tabelanbp t WHERE t.waluta = :waluta"),
    @NamedQuery(name = "Tabelanbp.findBySymbolWalutyOstatnia", query = "SELECT t FROM Tabelanbp t WHERE t.waluta.symbolwaluty = :symbolwaluty ORDER BY t.datatabeli DESC"),
    @NamedQuery(name = "Tabelanbp.findByDatatabeliSymbolwaluty", query = "SELECT t FROM Tabelanbp t WHERE t.datatabeli = :datatabeli AND t.waluta.symbolwaluty = :symbolwaluty AND t.nrtabeli LIKE '%/NBP/%'")
})

public class Tabelanbp implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtabelanbp", nullable = false)
    private Integer idtabelanbp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "nrtabeli", nullable = false, length = 25)
    private String nrtabeli;
    @JoinColumn(name = "waluta", referencedColumnName = "idwaluty")
    @ManyToOne
    private Waluty waluta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "datatabeli", nullable = false, length = 10)
    private String datatabeli;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kurssredni", nullable = false)
    private double kurssredni;
    @Column(name = "recznie")
    private boolean recznie;
    //to jest dlatego ze dla faktury typu FVZ caly dokument jest w jednym kursie
    @OneToMany(mappedBy = "tabelanbp", fetch = FetchType.LAZY)
    private List<Dokfk> Dokfk;
    //natomiast dla wyciagow bankowych jest inaczej tam liczy sie kazdy wiersz
    @OneToMany(mappedBy = "tabelanbp", fetch = FetchType.LAZY)
    private List<Wiersz> Wiersze;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;

    public Tabelanbp() {
    }

    public Tabelanbp(String nrtabeli, Waluty waluta, String datatabeli) {
        this.nrtabeli = nrtabeli;
        this.waluta = waluta;
        this.datatabeli = datatabeli;
    }
    
    public Tabelanbp(String nrtabeli, Waluty waluta, String datatabeli, double kurs) {
        this.nrtabeli = nrtabeli;
        this.waluta = waluta;
        this.datatabeli = datatabeli;
        this.kurssredni = kurs;
    }

    
    public Integer getIdtabelanbp() {
        return idtabelanbp;
    }

    public void setIdtabelanbp(Integer idtabelanbp) {
        this.idtabelanbp = idtabelanbp;
    }

    public String getNrtabeli() {
        return nrtabeli;
    }

    public void setNrtabeli(String nrtabeli) {
        this.nrtabeli = nrtabeli;
    }

    public Waluty getWaluta() {
        return waluta;
    }

    public void setWaluta(Waluty waluta) {
        this.waluta = waluta;
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
    
    public double getKurssredniPrzelicznik() {
        return kurssredni/waluta.getPrzelicznik();
    }

      public List<Dokfk> getDokfk() {
        return Dokfk;
    }

    public void setDokfk(List<Dokfk> Dokfk) {
        this.Dokfk = Dokfk;
    }

    public List<Wiersz> getWiersze() {
        return Wiersze;
    }

    public void setWiersze(List<Wiersz> Wiersze) {
        this.Wiersze = Wiersze;
    }

    public boolean isRecznie() {
        return recznie;
    }

    public void setRecznie(boolean recznie) {
        this.recznie = recznie;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.nrtabeli);
        hash = 97 * hash + Objects.hashCode(this.waluta);
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
        final Tabelanbp other = (Tabelanbp) obj;
        if (!Objects.equals(this.nrtabeli, other.nrtabeli)) {
            return false;
        }
        if (!Objects.equals(this.waluta, other.waluta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nrtabeli;
    }
    
     public String toString2() {
        return "Tabelanbp{" + "idtabelanbp=" + idtabelanbp + ", nrtabeli=" + nrtabeli + ", waluta=" + waluta + ", datatabeli=" + datatabeli + ", kurssredni=" + kurssredni + '}';
    }

   
    
    


    
}

