/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "statystyka", uniqueConstraints = {
    @UniqueConstraint(columnNames={"podatnik", "rok"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Statystyka.findAll", query = "SELECT e FROM Statystyka e"),
    @NamedQuery(name = "Statystyka.findByPodatnik", query = "SELECT e FROM Statystyka e WHERE e.podatnik = :podatnik"),
    @NamedQuery(name = "Statystyka.findByRok", query = "SELECT e FROM Statystyka e WHERE e.rok = :rok"),
    @NamedQuery(name = "Statystyka.findUsunRok", query = "DELETE FROM Statystyka e WHERE e.rok = :rok")
})
public class Statystyka  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lp")
    private int lp;
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @Column(name = "rok")
    private String rok;
    @Column(name = "iloscdokumentow")
    private int iloscdokumentow;
    @Column(name = "obroty")
    private double obroty;
    @Column(name = "iloscfaktur")
    private int iloscfaktur;
    @Column(name = "kwotafaktur")
    private double kwotafaktur;
    @Column(name = "fakturaNaObroty")
    private double fakturaNaObroty;
    @Column(name = "fakturaNaDokumenty")
    private double fakturaNaDokumenty;
    @Column(name = "ranking")
    private double ranking;
    @Column(name = "liczbapracownikow")
    private int liczbapracownikow;
     private int podid;

    public int getPodid() {
        return podid;
    }

    public void setPodid(int podid) {
        this.podid = podid;
    }

    public Statystyka() {
    }

    public Statystyka(Statystyka o) {
        this.lp = o.lp;
        this.podatnik = o.podatnik;
        this.rok = o.rok;
        this.iloscdokumentow = o.iloscdokumentow;
        this.obroty = o.obroty;
        this.iloscfaktur = o.iloscfaktur;
        this.kwotafaktur = o.kwotafaktur;
        this.fakturaNaObroty = o.fakturaNaObroty;
        this.fakturaNaDokumenty = o.fakturaNaDokumenty;
        this.ranking = o.ranking;
        this.liczbapracownikow = o.liczbapracownikow;
    }
    
    
    public Statystyka(int lp, Podatnik podatnik, String rok, int iloscdokumentow, double obroty, int iloscfaktur, double kwotafaktur) {
        this.lp = lp;
        this.podatnik = podatnik;
        this.rok = rok;
        this.iloscdokumentow = iloscdokumentow;
        this.obroty = obroty;
        this.iloscfaktur = iloscfaktur;
        this.kwotafaktur = kwotafaktur;
        this.fakturaNaObroty = Z.z4(this.kwotafaktur/this.obroty)*100 > 12 ? 12 : Z.z4(this.kwotafaktur/this.obroty)*100;
        this.fakturaNaDokumenty = Z.z4(this.kwotafaktur/this.iloscdokumentow)/10 > 12 ? 12 : Z.z4(this.kwotafaktur/this.iloscdokumentow)/10;
        this.ranking = this.fakturaNaDokumenty+this.fakturaNaObroty;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
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

    public int getIloscdokumentow() {
        return iloscdokumentow;
    }

    public void setIloscdokumentow(int iloscdokumentow) {
        this.iloscdokumentow = iloscdokumentow;
    }

    public double getObroty() {
        return obroty;
    }

    public void setObroty(double obroty) {
        this.obroty = obroty;
    }

    public int getIloscfaktur() {
        return iloscfaktur;
    }

    public void setIloscfaktur(int iloscfaktur) {
        this.iloscfaktur = iloscfaktur;
    }

    public double getKwotafaktur() {
        return kwotafaktur;
    }

    public void setKwotafaktur(double kwotafaktur) {
        this.kwotafaktur = kwotafaktur;
    }

    public double getFakturaNaObroty() {
        return fakturaNaObroty;
    }

    public void setFakturaNaObroty(double fakturaNaObroty) {
        this.fakturaNaObroty = fakturaNaObroty;
    }

    public double getFakturaNaDokumenty() {
        return fakturaNaDokumenty;
    }

    public void setFakturaNaDokumenty(double fakturaNaDokumenty) {
        this.fakturaNaDokumenty = fakturaNaDokumenty;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public int getLiczbapracownikow() {
        return liczbapracownikow;
    }

    public void setLiczbapracownikow(int liczbapracownikow) {
        this.liczbapracownikow = liczbapracownikow;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.podatnik);
        hash = 29 * hash + Objects.hashCode(this.rok);
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
        final Statystyka other = (Statystyka) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StatystykaBean{" + "podatnik=" + podatnik + ", rok=" + rok + ", iloscdokumentow=" + iloscdokumentow + ", obroty=" + obroty + ", iloscfaktur=" + iloscfaktur + ", kwotafaktur=" + kwotafaktur + '}';
    }
    
    
    
}
