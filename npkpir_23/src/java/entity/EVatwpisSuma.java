/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "EVatwpisSuma", uniqueConstraints = {
    @UniqueConstraint(columnNames={"ewidencja,podid,rok,mc"})
})
@NamedQueries({
    @NamedQuery(name = "EVatwpisSuma.findAll", query = "SELECT d FROM EVatwpisSuma d"),
    @NamedQuery(name = "EVatwpisSuma.usunByMcRok", query = "DELETE FROM EVatwpisSuma a WHERE a.podatnik = :podatnik AND a.rok = :rok AND a.mc = :mc"),
    @NamedQuery(name = "EVatwpisSuma.findByPodatnikRok", query = "SELECT a FROM EVatwpisSuma a WHERE a.podatnik = :podatnik AND a.rok = :rok")
})
public class EVatwpisSuma implements Serializable {
    private static final long serialVersionUID = 609846542238933045L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Integer id;
    @OneToOne
    @JoinColumn(name = "ewidencja", referencedColumnName = "id")
    private Evewidencja ewidencja;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @Column(name = "netto")
    private BigDecimal netto;
    @Column(name = "vat")
    private BigDecimal vat;
    @Column(name = "estawka")
    private String estawka;
    @Column(name = "niesumuj")
    private boolean niesumuj;
    @Column(name = "rok")
    private String rok;
    @Column(name = "mc")
    private String mc;

    public EVatwpisSuma(Evewidencja ewidencja, BigDecimal netto, BigDecimal vat, String estawka, Podatnik podatnik, String rok, String mc) {
        this.ewidencja = ewidencja;
        this.netto = netto;
        this.vat = vat;
        this.estawka = estawka;
        this.rok = rok;
        this.mc = mc;
        this.podatnik = podatnik;
    }

    public EVatwpisSuma() {
    }

    public boolean isNiesumuj() {
        return niesumuj;
    }

    public void setNiesumuj(boolean niesumuj) {
        this.niesumuj = niesumuj;
    }

    

    public Evewidencja getEwidencja() {
        return ewidencja;
    }

    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
    }

  
    public BigDecimal getNetto() {
        return netto;
    }

    public void setNetto(BigDecimal netto) {
        this.netto = netto;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public String getEstawka() {
        return estawka;
    }

    public void setEstawka(String estawka) {
        this.estawka = estawka;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
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

    
    
    @Override
    public String toString() {
        return "EVatwpisSuma{" + "ewidencja=" + ewidencja.getNazwa() + ",pole netto "+ ewidencja.getNrpolanetto() + ", netto=" + netto + ", vat=" + vat + ", estawka=" + estawka + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.ewidencja);
        hash = 47 * hash + Objects.hashCode(this.podatnik);
        hash = 47 * hash + Objects.hashCode(this.rok);
        hash = 47 * hash + Objects.hashCode(this.mc);
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
        final EVatwpisSuma other = (EVatwpisSuma) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.ewidencja, other.ewidencja)) {
            return false;
        }
        return Objects.equals(this.podatnik, other.podatnik);
    }

    
    
   
    
}
