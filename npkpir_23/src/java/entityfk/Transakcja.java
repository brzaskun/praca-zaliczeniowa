/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(catalog = "pkpir", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"rozliczany", "sparowany"})
})
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
    @ManyToMany
    private HashMap<String,Rozrachunekfk> rozrachunki;
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

    public Rozrachunekfk getRozliczany() {
        Iterator it = this.rozrachunki.entrySet().iterator();
        while (it.hasNext()) {
            String klucz = (String) it.next();
            if (klucz.equals("Rozliczany")) {
                return this.rozrachunki.get(klucz);
            }
        }
        return null;
    }
    
    public Rozrachunekfk getSparowany() {
        Iterator it = this.rozrachunki.entrySet().iterator();
        while (it.hasNext()) {
            String klucz = (String) it.next();
            if (klucz.equals("Sparowany")) {
                return this.rozrachunki.get(klucz);
            }
        }
        return null;
    }
    
    public void setRozliczany(Rozrachunekfk rozrachunekfk) {
        this.rozrachunki.put("Rozliczany", rozrachunekfk);
    }
    
    public void setSparowany(Rozrachunekfk rozrachunekfk) {
        this.rozrachunki.put("Sparowany", rozrachunekfk);
    }
    
    public HashMap<String, Rozrachunekfk> getRozrachunki() {
        return rozrachunki;
    }

    public void setRozrachunki(HashMap<String, Rozrachunekfk> rozrachunki) {
        this.rozrachunki = rozrachunki;
    }

    public void setZablokujnanoszenie(Boolean zablokujnanoszenie) {
        this.zablokujnanoszenie = zablokujnanoszenie;
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
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.rozrachunki);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.rozrachunki, other.rozrachunki)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transakcja{" + "id=" + id + ", kwotatransakcji=" + kwotatransakcji + ", rozrachunki=" + rozrachunki + ", podatnik=" + podatnik + '}';
    }
    
   

   
    
}
