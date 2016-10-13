/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.Podatnik;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"podatnik","rok","rodzajCzlonkostwa"})
})
public class StowarzyszenieSkladki implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "podatnik")
    private Podatnik podatnik;
    @Column(name="rok")
    private String rok;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "rodzajCzlonkostwa")
    private RodzajCzlonkostwa rodzajCzlonkostwa;
    @Column(name="kwotarok")
    private double kwotarok;
    @Column(name="kwotapolrok")
    private double kwotapolrok;
    @Column(name="kwotakw")
    private double kwotakw;
    @Column(name="kwotamc")
    private double kwotamc;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.podatnik);
        hash = 37 * hash + Objects.hashCode(this.rok);
        hash = 37 * hash + Objects.hashCode(this.rodzajCzlonkostwa);
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
        final StowarzyszenieSkladki other = (StowarzyszenieSkladki) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        if (!Objects.equals(this.rodzajCzlonkostwa, other.rodzajCzlonkostwa)) {
            return false;
        }
        return true;
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

    public RodzajCzlonkostwa getRodzajCzlonkostwa() {
        return rodzajCzlonkostwa;
    }

    public void setRodzajCzlonkostwa(RodzajCzlonkostwa rodzajCzlonkostwa) {
        this.rodzajCzlonkostwa = rodzajCzlonkostwa;
    }

    public double getKwotarok() {
        return kwotarok;
    }

    public void setKwotarok(double kwotarok) {
        this.kwotarok = kwotarok;
    }

    public double getKwotapolrok() {
        return kwotapolrok;
    }

    public void setKwotapolrok(double kwotapolrok) {
        this.kwotapolrok = kwotapolrok;
    }

    public double getKwotakw() {
        return kwotakw;
    }

    public void setKwotakw(double kwotakw) {
        this.kwotakw = kwotakw;
    }

    public double getKwotamc() {
        return kwotamc;
    }

    public void setKwotamc(double kwotamc) {
        this.kwotamc = kwotamc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
