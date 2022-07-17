    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podstawki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Podstawki.findAll", query = "SELECT p FROM Podstawki p"),
    @NamedQuery(name = "Podstawki.findByRok", query = "SELECT p FROM Podstawki p WHERE p.rok = :rok"),
    @NamedQuery(name = "Podstawki.findByRok2022", query = "SELECT p FROM Podstawki p WHERE p.rok = :rok AND p.mc IS NULL"),
    @NamedQuery(name = "Podstawki.findByRok202207", query = "SELECT p FROM Podstawki p WHERE p.rok = :rok AND p.mc IS NOT NULL")
})

   
public class Podstawki implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ROK")
    private Integer rok;
    @Column(name = "mc")
    private String mc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwotawolna")
    private Double kwotawolna;
    @Column(name = "prog")
    private Double prog;
    @Column(name = "stawka1")
    private Double stawka1;
    @Column(name = "nadwyzka")
    private Double nadwyzka;
    @Column(name = "stawka2")
    private Double stawka2;
    @Column(name = "stawkaliniowy")
    private Double stawkaliniowy;
    @Column(name = "stawkanajem1")
    private Double stawkanajem1;
    @Column(name = "stawkanajem2")
    private Double stawkanajem2;
    @Column(name = "stawkaryczalt1")
    private Double stawkaryczalt1;
    @Column(name = "stawkaryczalt2")
    private Double stawkaryczalt2;
    @Column(name = "stawkaryczalt3")
    private Double stawkaryczalt3;
    @Column(name = "stawkaryczalt4")
    private Double stawkaryczalt4;
    @Column(name = "limitryczalt")
    private Double limitryczalt;
    @Column(name = "amortjednor")
    private Double amortjednor;
    @Column(name = "ksiegihandlowe")
    private Double ksiegihandlowe;

    public Podstawki() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.rok);
        hash = 43 * hash + Objects.hashCode(this.mc);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Podstawki other = (Podstawki) obj;
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        return true;
    }

    

    
    @Override
    public String toString() {
        return "entity.Podstawki[ rok=" + rok + " ]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getRok() {
        return rok;
    }

    public void setRok(Integer rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Double getKwotawolna() {
        return kwotawolna;
    }

    public void setKwotawolna(Double kwotawolna) {
        this.kwotawolna = kwotawolna;
    }

    public Double getProg() {
        return prog;
    }

    public void setProg(Double prog) {
        this.prog = prog;
    }

    public Double getStawka1() {
        return stawka1;
    }

    public void setStawka1(Double stawka1) {
        this.stawka1 = stawka1;
    }

    public Double getStawka2() {
        return stawka2;
    }

    public void setStawka2(Double stawka2) {
        this.stawka2 = stawka2;
    }

    public Double getStawkaliniowy() {
        return stawkaliniowy;
    }

    public void setStawkaliniowy(Double stawkaliniowy) {
        this.stawkaliniowy = stawkaliniowy;
    }

    public Double getStawkanajem1() {
        return stawkanajem1;
    }

    public void setStawkanajem1(Double stawkanajem1) {
        this.stawkanajem1 = stawkanajem1;
    }

    public Double getStawkanajem2() {
        return stawkanajem2;
    }

    public void setStawkanajem2(Double stawkanajem2) {
        this.stawkanajem2 = stawkanajem2;
    }

    public Double getStawkaryczalt1() {
        return stawkaryczalt1;
    }

    public void setStawkaryczalt1(Double stawkaryczalt1) {
        this.stawkaryczalt1 = stawkaryczalt1;
    }

    public Double getStawkaryczalt2() {
        return stawkaryczalt2;
    }

    public void setStawkaryczalt2(Double stawkaryczalt2) {
        this.stawkaryczalt2 = stawkaryczalt2;
    }

    public Double getStawkaryczalt3() {
        return stawkaryczalt3;
    }

    public void setStawkaryczalt3(Double stawkaryczalt3) {
        this.stawkaryczalt3 = stawkaryczalt3;
    }

    public Double getStawkaryczalt4() {
        return stawkaryczalt4;
    }

    public void setStawkaryczalt4(Double stawkaryczalt4) {
        this.stawkaryczalt4 = stawkaryczalt4;
    }

    public Double getLimitryczalt() {
        return limitryczalt;
    }

    public void setLimitryczalt(Double limitryczalt) {
        this.limitryczalt = limitryczalt;
    }

    public Double getAmortjednor() {
        return amortjednor;
    }

    public void setAmortjednor(Double amortjednor) {
        this.amortjednor = amortjednor;
    }

    public Double getKsiegihandlowe() {
        return ksiegihandlowe;
    }

    public void setKsiegihandlowe(Double ksiegihandlowe) {
        this.ksiegihandlowe = ksiegihandlowe;
    }

    public Double getNadwyzka() {
        return nadwyzka;
    }

    public void setNadwyzka(Double nadwyzka) {
        this.nadwyzka = nadwyzka;
    }
    
    
}
