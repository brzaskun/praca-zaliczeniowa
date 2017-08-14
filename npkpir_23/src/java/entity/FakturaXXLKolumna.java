/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "fakturaxxlkolumna",uniqueConstraints = {@UniqueConstraint(columnNames={"podatnik"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FakturaXXLKolumna.findAll", query = "SELECT e FROM FakturaXXLKolumna e"),
    @NamedQuery(name = "FakturaXXLKolumna.findByPodatnik", query = "SELECT e FROM FakturaXXLKolumna e WHERE e.podatnik = :podatnik")
})
public class FakturaXXLKolumna implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "podatnik", referencedColumnName = "nip")
    private Podatnik podatnik;
    @Column(name = "pkwiu")
    private boolean pkwiu;
    @Column(name = "ilosc")
    private boolean ilosc;
    @Column(name = "jednostka")
    private boolean jednostka;
    @Column(name = "cena")
    private boolean cena;
    @Column(name = "nettoopis0")
    private String nettoopis0;
    @Column(name = "nettoopis1")
    private String nettoopis1;
    @Column(name = "nettoopis2")
    private String nettoopis2;
    @Column(name = "nettoopis3")
    private String nettoopis3;
    @Column(name = "nettoopis4")
    private String nettoopis4;
    @Column(name = "nettoopis5")
    private String nettoopis5;
 private int podid;

    public int getPodid() {
        return podid;
    }

    public void setPodid(int podid) {
        this.podid = podid;
    }
    
    public boolean isPkwiu() {
        return pkwiu;
    }

    public void setPkwiu(boolean pkwiu) {
        this.pkwiu = pkwiu;
    }

    public boolean isIlosc() {
        return ilosc;
    }

    public void setIlosc(boolean ilosc) {
        this.ilosc = ilosc;
    }

    public boolean isJednostka() {
        return jednostka;
    }

    public void setJednostka(boolean jednostka) {
        this.jednostka = jednostka;
    }

    public boolean isCena() {
        return cena;
    }

    public void setCena(boolean cena) {
        this.cena = cena;
    }
 
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getNettoopis0() {
        return nettoopis0;
    }

    public void setNettoopis0(String nettoopis0) {
        this.nettoopis0 = nettoopis0;
    }

    public String getNettoopis1() {
        return nettoopis1;
    }

    public void setNettoopis1(String nettoopis1) {
        this.nettoopis1 = nettoopis1;
    }

    public String getNettoopis2() {
        return nettoopis2;
    }

    public void setNettoopis2(String nettoopis2) {
        this.nettoopis2 = nettoopis2;
    }

    public String getNettoopis3() {
        return nettoopis3;
    }

    public void setNettoopis3(String nettoopis3) {
        this.nettoopis3 = nettoopis3;
    }

    public String getNettoopis4() {
        return nettoopis4;
    }

    public void setNettoopis4(String nettoopis4) {
        this.nettoopis4 = nettoopis4;
    }

    public String getNettoopis5() {
        return nettoopis5;
    }

    public void setNettoopis5(String nettoopis5) {
        this.nettoopis5 = nettoopis5;
    }

      
}
