/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "DokFP.findAll", query = "SELECT f FROM DokFP f")
})
public class DokFP  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 4, max = 4)
    @Column(name = "rok", nullable = false, length = 4)
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "mc", nullable = false, length = 2)
    private String mc;
    @Column(name = "unikalnymuer")
    private String unikalnynumer;
    @Column(name = "kodKrajuNadaniaTIN")
    private String kodKrajuNadaniaTIN;
    @Column(name = "nazwaKontrahenta")
    private String nazwaKontrahenta;
    @Column(name = "dowodSprzedazy")
    private String dowodSprzedazy;
    @Column(name = "dataWystawienia")
    private String dataWystawienia;
    @Column(name = "dataSprzedazy")
    private String dataSprzedazy;
    @Column(name = "oznaczenie1")
    private String oznaczenie1;
    @Column(name = "oznaczenie2")
    private String oznaczenie2;
    @Column(name = "oznaczenie3")
    private String oznaczenie3;
    @Column(name = "oznaczenie4")
    private String oznaczenie4;

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

    public String getUnikalnynumer() {
        return unikalnynumer;
    }

    public void setUnikalnynumer(String unikalnynumer) {
        this.unikalnynumer = unikalnynumer;
    }

    public String getKodKrajuNadaniaTIN() {
        return kodKrajuNadaniaTIN;
    }

    public void setKodKrajuNadaniaTIN(String kodKrajuNadaniaTIN) {
        this.kodKrajuNadaniaTIN = kodKrajuNadaniaTIN;
    }

    public String getNazwaKontrahenta() {
        return nazwaKontrahenta;
    }

    public void setNazwaKontrahenta(String nazwaKontrahenta) {
        this.nazwaKontrahenta = nazwaKontrahenta;
    }

    public String getDowodSprzedazy() {
        return dowodSprzedazy;
    }

    public void setDowodSprzedazy(String dowodSprzedazy) {
        this.dowodSprzedazy = dowodSprzedazy;
    }

    public String getDataWystawienia() {
        return dataWystawienia;
    }

    public void setDataWystawienia(String dataWystawienia) {
        this.dataWystawienia = dataWystawienia;
    }

    public String getDataSprzedazy() {
        return dataSprzedazy;
    }

    public void setDataSprzedazy(String dataSprzedazy) {
        this.dataSprzedazy = dataSprzedazy;
    }

    public String getOznaczenie1() {
        return oznaczenie1;
    }

    public void setOznaczenie1(String oznaczenie1) {
        this.oznaczenie1 = oznaczenie1;
    }

    public String getOznaczenie2() {
        return oznaczenie2;
    }

    public void setOznaczenie2(String oznaczenie2) {
        this.oznaczenie2 = oznaczenie2;
    }

    public String getOznaczenie3() {
        return oznaczenie3;
    }

    public void setOznaczenie3(String oznaczenie3) {
        this.oznaczenie3 = oznaczenie3;
    }

    public String getOznaczenie4() {
        return oznaczenie4;
    }

    public void setOznaczenie4(String oznaczenie4) {
        this.oznaczenie4 = oznaczenie4;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final DokFP other = (DokFP) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DokFP{" + "podatnik=" + podatnik.getPrintnazwa() + ", rok=" + rok + ", mc=" + mc + ", unikalnynumer=" + unikalnynumer + ", kodKrajuNadaniaTIN=" + kodKrajuNadaniaTIN + ", nazwaKontrahenta=" + nazwaKontrahenta + ", dowodSprzedazy=" + dowodSprzedazy + ", dataWystawienia=" + dataWystawienia + ", dataSprzedazy=" + dataSprzedazy + ", oznaczenie1=" + oznaczenie1 + ", oznaczenie2=" + oznaczenie2 + ", oznaczenie3=" + oznaczenie3 + ", oznaczenie4=" + oznaczenie4 + '}';
    }

    
    
}
