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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"podatnik"})
})
@NamedQueries({
    @NamedQuery(name = "Logofaktura.findAll", query = "SELECT d FROM Logofaktura d"),
    @NamedQuery(name = "Logofaktura.usunlogo", query = "DELETE FROM Logofaktura d WHERE d.podatnik = :podatnik"),
    @NamedQuery(name = "Logofaktura.findByPodatnik", query = "SELECT d FROM Logofaktura d WHERE d.podatnik = :podatnik"),
    })
public class Logofaktura  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @Lob
    private byte[] pliklogo;
    @Column(name = "nazwapliku")
    private String nazwapliku;
    @Column(name = "rozszerzenie")
    private String rozszerzenie;

    public Logofaktura() {
    }

    
    public Logofaktura(Podatnik podatnikObiekt, String nazwakrotka, String extension, byte[] contents) {
        this.podatnik = podatnikObiekt;
        this.nazwapliku = nazwakrotka;
        this.rozszerzenie = extension;
        this.pliklogo = contents;
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

    public byte[] getPliklogo() {
        return pliklogo;
    }

    public void setPliklogo(byte[] pliklogo) {
        this.pliklogo = pliklogo;
    }

    public String getNazwapliku() {
        return nazwapliku;
    }

    public void setNazwapliku(String nazwapliku) {
        this.nazwapliku = nazwapliku;
    }

    public String getRozszerzenie() {
        return rozszerzenie;
    }

    public void setRozszerzenie(String rozszerzenie) {
        this.rozszerzenie = rozszerzenie;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.podatnik);
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
        final Logofaktura other = (Logofaktura) obj;
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logofaktura{" + "podatnik=" + podatnik.getNazwapelna() + ", pliklogo=" + pliklogo + '}';
    }
    
    
}
