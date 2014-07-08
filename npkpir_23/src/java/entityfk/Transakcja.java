/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcja.findAll", query = "SELECT t FROM Transakcja t"),
    @NamedQuery(name = "Transakcja.findByRozliczonyID", query = "SELECT t FROM Transakcja t WHERE t.rozliczany.idrozrachunku = :rozliczany"),
    @NamedQuery(name = "Transakcja.findBySparowanyID", query = "SELECT t FROM Transakcja t WHERE t.sparowany.idrozrachunku = :sparowany"),
    @NamedQuery(name = "Transakcja.usunNiezaksiegowane", query = "DELETE FROM Transakcja t WHERE t.podatnik = :podatnik AND t.zaksiegowana = 0"),
    @NamedQuery(name = "Transakcja.findByRozliczanySparowany", query = "SELECT t FROM Transakcja t WHERE t.rozliczany.idrozrachunku = :rozliczany AND t.sparowany.idrozrachunku = :sparowany"),
    @NamedQuery(name = "Transakcja.findById", query = "SELECT t FROM Transakcja t WHERE t.id = :id"),
    @NamedQuery(name = "Transakcja.findByKwotatransakcji", query = "SELECT t FROM Transakcja t WHERE t.kwotatransakcji = :kwotatransakcji"),
    @NamedQuery(name = "Transakcja.findByPoprzedniakwota", query = "SELECT t FROM Transakcja t WHERE t.poprzedniakwota = :poprzedniakwota"),
    @NamedQuery(name = "Transakcja.findByRoznicekursowe", query = "SELECT t FROM Transakcja t WHERE t.roznicekursowe = :roznicekursowe"),
    @NamedQuery(name = "Transakcja.findByZablokujnanoszenie", query = "SELECT t FROM Transakcja t WHERE t.zablokujnanoszenie = :zablokujnanoszenie")})
public class Transakcja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false, name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwotatransakcji", precision = 22)
    private Double kwotatransakcji;
    @Column(name = "poprzedniakwota", precision = 22)
    private Double poprzedniakwota;
    @Column(name = "roznicekursowe", precision = 22)
    private Double roznicekursowe;
    @Column(name = "zablokujnanoszenie")
    private boolean zablokujnanoszenie;
    @Column(name = "zaksiegowana") 
    private boolean zaksiegowana;
    @JoinColumn(name = "rozliczany", referencedColumnName = "idrozrachunku")
    @ManyToOne
    private Rozrachunekfk rozliczany;
    @JoinColumn(name = "sparowany", referencedColumnName = "idrozrachunku")
    @ManyToOne
    private Rozrachunekfk sparowany;
    private String podatnik;
    private String symbolWaluty;

    public Transakcja() {
        this.kwotatransakcji = 0.0;
        this.poprzedniakwota = 0.0;
        this.roznicekursowe = 0.0;
        this.zablokujnanoszenie = false;
        this.zaksiegowana = false;
        this.roznicekursowe = 0.0;
    }

   
    public double GetSpRozl() {
        return this.getSparowany().getRozliczono();
    }

    public void SetSpRozl(double suma) {
        this.getSparowany().setRozliczono(suma);
    }

     public double GetSpKwotaPier() {
        return this.getSparowany().getKwotapierwotna();
    }

    public double GetSpPoz() {
        return this.getSparowany().getPozostalo();
    }

    public void SetSpPoz(double suma) {
        this.getSparowany().setPozostalo(suma);
    }

    public double GetRRozl() {
        return this.getRozliczany().getRozliczono();
    }

    public void SetRRozl(double suma) {
        this.getRozliczany().setRozliczono(suma);
    }

     public double GetRKwotaPier() {
        return this.getRozliczany().getKwotapierwotna();
    }
    
    public double GetRPoz() {
        return this.getRozliczany().getPozostalo();
    }
    
    public void SetRPoz(double suma) {
        this.getRozliczany().setPozostalo(suma);
    }

    public boolean isZablokujnanoszenie() {
        return zablokujnanoszenie;
    }


    public void setZablokujnanoszenie(Boolean zablokujnanoszenie) {
        this.zablokujnanoszenie = zablokujnanoszenie;
    }

    public Rozrachunekfk getRozliczany() {
        return rozliczany;
    }

    public void setRozliczany(Rozrachunekfk rozliczany) {
        this.rozliczany = rozliczany;
    }

    public Rozrachunekfk getSparowany() {
        return sparowany;
    }

    public void setSparowany(Rozrachunekfk sparowany) {
        this.sparowany = sparowany;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getKwotatransakcji() {
        return kwotatransakcji;
    }

    public void setKwotatransakcji(Double kwotatransakcji) {
        this.kwotatransakcji = kwotatransakcji;
    }

    public Double getPoprzedniakwota() {
        return poprzedniakwota;
    }

    public void setPoprzedniakwota(Double poprzedniakwota) {
        this.poprzedniakwota = poprzedniakwota;
    }

    public Double getRoznicekursowe() {
        return roznicekursowe;
    }

    public void setRoznicekursowe(Double roznicekursowe) {
        this.roznicekursowe = roznicekursowe;
    }

    public boolean isZaksiegowana() {
        return zaksiegowana;
    }

    public void setZaksiegowana(boolean zaksiegowana) {
        this.zaksiegowana = zaksiegowana;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getSymbolWaluty() {
        return symbolWaluty;
    }

    public void setSymbolWaluty(String symbolWaluty) {
        this.symbolWaluty = symbolWaluty;
    }
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.rozliczany);
        hash = 89 * hash + Objects.hashCode(this.sparowany);
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
        final Transakcja other = (Transakcja) obj;
        if (!Objects.equals(this.rozliczany, other.rozliczany)) {
            return false;
        }
        if (!Objects.equals(this.sparowany, other.sparowany)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transakcja{" + "id=" + id + ", kwotatransakcji=" + kwotatransakcji + ", poprzedniakwota=" + poprzedniakwota + ", roznicekursowe=" + roznicekursowe + ", zablokujnanoszenie=" + zablokujnanoszenie + ", rozliczany=" + rozliczany + ", sparowany=" + sparowany + '}';
    }

  
    
}
