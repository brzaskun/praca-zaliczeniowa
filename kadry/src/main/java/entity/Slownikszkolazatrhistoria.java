/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
@Table(name = "slownikszkolazatrhistoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Slownikszkolazatrhistoria.findAll", query = "SELECT s FROM Slownikszkolazatrhistoria s"),
    @NamedQuery(name = "Slownikszkolazatrhistoria.findById", query = "SELECT s FROM Slownikszkolazatrhistoria s WHERE s.id = :id"),
    @NamedQuery(name = "Slownikszkolazatrhistoria.findBySymbol", query = "SELECT s FROM Slownikszkolazatrhistoria s WHERE s.symbol = :symbol"),
    @NamedQuery(name = "Slownikszkolazatrhistoria.findByOpis", query = "SELECT s FROM Slownikszkolazatrhistoria s WHERE s.opis = :opis"),
    @NamedQuery(name = "Slownikszkolazatrhistoria.findByPraca0nauka1", query = "SELECT s FROM Slownikszkolazatrhistoria s WHERE s.praca0nauka1 = :praca0nauka1")})
public class Slownikszkolazatrhistoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "symbol")
    private String symbol;
    @Size(max = 128)
    @Column(name = "opis")
    private String opis;
    @Column(name = "praca0nauka1")
    private boolean praca0nauka1;
    @Column(name = "dni")
    private int dni;

    public Slownikszkolazatrhistoria() {
    }

    public Slownikszkolazatrhistoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public boolean getPraca0nauka1() {
        return praca0nauka1;
    }

    public void setPraca0nauka1(boolean praca0nauka1) {
        this.praca0nauka1 = praca0nauka1;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slownikszkolazatrhistoria)) {
            return false;
        }
        Slownikszkolazatrhistoria other = (Slownikszkolazatrhistoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Slownikszkolazatrhistoria{" + "symbol=" + symbol + ", opis=" + opis + ", praca0nauka1=" + praca0nauka1 + '}';
    }

   
    
}
