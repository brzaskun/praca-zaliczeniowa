/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vies;

import entity.Podatnik;
import entity.Uz;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kliencifk.findAll", query = "SELECT k FROM Kliencifk k")
})
@Cacheable
public class Vies implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lp", nullable = false)
    private int id;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data")
    private Date data;
    @Column(name = "wynik")
    private boolean wynik;
    @Column(name = "kraj")
    private String kraj;
    @Column(name = "NIP")
    private String NIP;
    @Column(name = "nazwafirmy")
    private String nazwafirmy;
    @Column(name = "adresfirmy")
    private String adresfirmy;
    @Column(name = "identyfikatorsprawdzenia")
    private String identyfikatorsprawdzenia;
    @JoinColumn(name = "wprowadzil", referencedColumnName = "login")
    @ManyToOne
    private Uz wprowadzil;
    @Transient
    private String uwagi;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
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
        final Vies other = (Vies) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
       return "Vies{" + "podatnik=" + podatnik.getNazwapelna() + ", data=" + data + ", wynik=" + wynik + ", kraj=" + kraj + ", NIP=" + NIP + ", nazwafirmy=" + nazwafirmy + ", adresfirmy=" + adresfirmy + ", identyfikatorsprawdzenia=" + identyfikatorsprawdzenia + '}';
    }
    
    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isWynik() {
        return wynik;
    }

    public void setWynik(boolean wynik) {
        this.wynik = wynik;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getNazwafirmy() {
        return nazwafirmy;
    }

    public void setNazwafirmy(String nazwafirmy) {
        this.nazwafirmy = nazwafirmy;
    }

    public String getAdresfirmy() {
        return adresfirmy;
    }

    public void setAdresfirmy(String adresfirmy) {
        this.adresfirmy = adresfirmy;
    }

    public String getIdentyfikatorsprawdzenia() {
        return identyfikatorsprawdzenia;
    }

    public void setIdentyfikatorsprawdzenia(String identyfikatorsprawdzenia) {
        this.identyfikatorsprawdzenia = identyfikatorsprawdzenia;
    }

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }
    
    public String getWynikVies() {
        String zwrot = "nieaktywny";
        if (this.identyfikatorsprawdzenia != null && this.uwagi != null) {
            zwrot = "awaria serw.";
        } else if (this.identyfikatorsprawdzenia != null) {
            zwrot = "ok";
        }
        return zwrot;
    }
    
}
