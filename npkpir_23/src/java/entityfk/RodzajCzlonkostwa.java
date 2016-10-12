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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nazwa", "skrotnazwy"})
})
public class RodzajCzlonkostwa  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "nazwa")
    private Integer nazwa;
    @Column(name = "skrotnazwy")
    private Integer skrotnazwy;
    @Column(name = "opis")
    private Integer opis;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.nazwa);
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
        final RodzajCzlonkostwa other = (RodzajCzlonkostwa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nazwa, other.nazwa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RodzajeCzlonkostwa{" + "nazwa=" + nazwa + ", skrotnazwy=" + skrotnazwy + ", opis=" + opis + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNazwa() {
        return nazwa;
    }

    public void setNazwa(Integer nazwa) {
        this.nazwa = nazwa;
    }

    public Integer getSkrotnazwy() {
        return skrotnazwy;
    }

    public void setSkrotnazwy(Integer skrotnazwy) {
        this.skrotnazwy = skrotnazwy;
    }

    public Integer getOpis() {
        return opis;
    }

    public void setOpis(Integer opis) {
        this.opis = opis;
    }
    
    
}
