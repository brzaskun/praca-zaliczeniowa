/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import embeddablefk.WierszStronafkPK;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@MappedSuperclass
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
public class Transakcja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double kwotatransakcji;
    @Column(precision = 22)
    private Double poprzedniakwota;
    @Column(precision = 22)
    private Double roznicekursowe;
    private Boolean zablokujnanoszenie;
    @JoinColumn(name = "rozliczany", referencedColumnName = "idrozrachunku")
    @ManyToOne
    private Rozrachunekfk rozliczany;
    @JoinColumn(name = "sparowany", referencedColumnName = "idrozrachunku")
    @ManyToOne
    private Rozrachunekfk sparowany;

    public Transakcja() {
        this.roznicekursowe = 0.0;
    }

    public WierszStronafkPK idSparowany() {
        return this.getSparowany().getWierszStronafk().getWierszStronafkPK();
    }
    
    public WierszStronafkPK idRozliczany() {
        return this.getRozliczany().getWierszStronafk().getWierszStronafkPK();
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.rozliczany);
        hash = 83 * hash + Objects.hashCode(this.sparowany);
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
