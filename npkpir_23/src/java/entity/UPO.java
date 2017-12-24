/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import deklaracje.upo.Potwierdzenie;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
public class UPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private int id;
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 4, max = 4)
    @Column(name = "rok", nullable = false, length = 4)
    private String rok;
    @Column(name = "miesiac")
    private String miesiac;
    @Lob
    @Column (name = "potwierdzenie")
    private Potwierdzenie potwierdzenie;
    @Lob
    @Column (name = "jpk")
    private JPKSuper jpk;
    @Lob
    @Column (name = "deklaracja")
    private DeklaracjaSuper deklaracja;
    @Column (name = "wersja")
    private String wersja;

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

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public Potwierdzenie getPotwierdzenie() {
        return potwierdzenie;
    }

    public void setPotwierdzenie(Potwierdzenie potwierdzenie) {
        this.potwierdzenie = potwierdzenie;
    }

    public JPKSuper getJpk() {
        return jpk;
    }

    public void setJpk(JPKSuper jpk) {
        this.jpk = jpk;
    }

    public DeklaracjaSuper getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(DeklaracjaSuper deklaracja) {
        this.deklaracja = deklaracja;
    }

    public String getWersja() {
        return wersja;
    }

    public void setWersja(String wersja) {
        this.wersja = wersja;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final UPO other = (UPO) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UPO{" + "podatnik=" + podatnik.getPrintnazwa() + ", rok=" + rok + ", miesiac=" + miesiac + ", potwierdzenie=" + potwierdzenie.getKodFormularza() + ", jpk=" + jpk + ", deklaracja=" + deklaracja + ", wersja=" + wersja + '}';
    }
    
    
    
    
}
