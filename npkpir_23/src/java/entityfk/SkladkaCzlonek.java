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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"czlonek","skladka"})
})
@NamedQueries({
    @NamedQuery(name = "SkladkaCzlonek.findByPodatnikRok", query = "SELECT m FROM SkladkaCzlonek m WHERE m.czlonek.podatnikObj = :podatnikObj AND m.skladka.rok = :rok")
})
public class SkladkaCzlonek  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private int id;
    @JoinColumn(name="czlonek", referencedColumnName = "id")
    private MiejscePrzychodow czlonek;
    @JoinColumn(name="skladka", referencedColumnName = "id")
    private SkladkaStowarzyszenie skladka;

    public SkladkaCzlonek() {
    }

    public SkladkaCzlonek(MiejscePrzychodow r) {
        this.czlonek = r;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MiejscePrzychodow getCzlonek() {
        return czlonek;
    }

    public void setCzlonek(MiejscePrzychodow czlonek) {
        this.czlonek = czlonek;
    }

    public SkladkaStowarzyszenie getSkladka() {
        return skladka;
    }

    public void setSkladka(SkladkaStowarzyszenie skladka) {
        this.skladka = skladka;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.czlonek);
        hash = 31 * hash + Objects.hashCode(this.skladka);
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
        final SkladkaCzlonek other = (SkladkaCzlonek) obj;
        if (!Objects.equals(this.czlonek, other.czlonek)) {
            return false;
        }
        if (!Objects.equals(this.skladka, other.skladka)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SkladkaCzlonek{" + "czlonek=" + czlonek.getPodatnikObj().getNazwisko() + ", skladka=" + skladka.getRodzajCzlonkostwa().getNazwa()+ ", kwota=" + skladka.getKwota() + '}';
    }
    
    
}
