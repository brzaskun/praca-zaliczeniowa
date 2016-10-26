/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"rok", "rodzajCzlonkostwa", "okres"})
})
public class SkladkaStowarzyszenie implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "rok")
    private String rok;
    @JoinColumn(name = "rodzajCzlonkostwa", referencedColumnName = "id")
    private RodzajCzlonkostwa rodzajCzlonkostwa;
    @Column(name = "kwota")
    private double kwota;
    //mc, kw, polrok, rok
    @Column(name = "okres")
    private String okres;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.rok);
        hash = 19 * hash + Objects.hashCode(this.rodzajCzlonkostwa);
        hash = 19 * hash + Objects.hashCode(this.okres);
        return hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public String getOkres() {
        return okres;
    }

    public void setOkres(String okres) {
        this.okres = okres;
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
        final SkladkaStowarzyszenie other = (SkladkaStowarzyszenie) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.okres, other.okres)) {
            return false;
        }
        if (!Objects.equals(this.rodzajCzlonkostwa, other.rodzajCzlonkostwa)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "SkladkiStowarzyszenie{" + "rok=" + rok + ", rodzajCzlonkostwa=" + rodzajCzlonkostwa + ", skladka=" + kwota + ", okres=" + okres + '}';
    }
    
    
    
}
