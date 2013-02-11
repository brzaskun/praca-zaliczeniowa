/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "srodkikst")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Srodkikst.findAll", query = "SELECT s FROM Srodkikst s"),
    @NamedQuery(name = "Srodkikst.findById", query = "SELECT s FROM Srodkikst s WHERE s.id = :id"),
    @NamedQuery(name = "Srodkikst.findByStawka", query = "SELECT s FROM Srodkikst s WHERE s.stawka = :stawka"),
    @NamedQuery(name = "Srodkikst.findBySymbol", query = "SELECT s FROM Srodkikst s WHERE s.symbol = :symbol"),
    @NamedQuery(name = "Srodkikst.findByNazwa", query = "SELECT s FROM Srodkikst s WHERE s.nazwa = :nazwa")})
public class Srodkikst implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 7)
    @Column(name = "stawka")
    private String stawka;
    @Size(max = 7)
    @Column(name = "symbol")
    private String symbol;
    @Size(max = 262)
    @Column(name = "nazwa")
    private String nazwa;

    public Srodkikst() {
    }

    public Srodkikst(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStawka() {
        return stawka;
    }

    public void setStawka(String stawka) {
        this.stawka = stawka;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.nazwa);
        return hash;
    }

   

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Srodkikst other = (Srodkikst) obj;
        if (!Objects.equals(this.nazwa, other.nazwa)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return nazwa;
    }

   
    
}
