/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import javax.persistence.Table;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podatekplatnosc")
@NamedQueries({
    @NamedQuery(name = "PodatekPlatnosc.findAll", query = "SELECT d FROM PodatekPlatnosc d")
})

public class PodatekPlatnosc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Integer id;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @Column(name = "rok")
    private String rok;
    @Column(name = "kwota")
    private BigDecimal kwota;
    @Column(name = "rodzajpodatku")
    private int rodzajpodatku;
    @Column(name = "terminzaplaty")
    private String terminzaplaty;

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

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }

    public int getRodzajpodatku() {
        return rodzajpodatku;
    }

    public void setRodzajpodatku(int rodzajpodatku) {
        this.rodzajpodatku = rodzajpodatku;
    }

    public String getTerminzaplaty() {
        return terminzaplaty;
    }

    public void setTerminzaplaty(String terminzaplaty) {
        this.terminzaplaty = terminzaplaty;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.podatnik);
        hash = 89 * hash + Objects.hashCode(this.rok);
        hash = 89 * hash + this.rodzajpodatku;
        hash = 89 * hash + Objects.hashCode(this.terminzaplaty);
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
        final PodatekPlatnosc other = (PodatekPlatnosc) obj;
        if (this.rodzajpodatku != other.rodzajpodatku) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.terminzaplaty, other.terminzaplaty)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.podatnik, other.podatnik);
    }
    
    
    
}
