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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "FakturaDodatkowaPozycja",uniqueConstraints = {
    @UniqueConstraint(
            columnNames={"nazwa"})
})
@NamedQueries({
    @NamedQuery(name = "FakturaDodatkowaPozycja.findAll", query = "SELECT d FROM FakturaDodatkowaPozycja d"),
    @NamedQuery(name = "FakturaDodatkowaPozycja.findByNazwa", query = "SELECT d FROM FakturaDodatkowaPozycja d WHERE d.nazwa = :nazwa"),
    })
public class FakturaDodatkowaPozycja  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "nazwa")
    private String nazwa;
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "aktywny")
    private boolean aktywny;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public boolean isAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.nazwa);
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
        final FakturaDodatkowaPozycja other = (FakturaDodatkowaPozycja) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nazwa, other.nazwa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FakturaDodatkowaPozycja{" + "nazwa=" + nazwa + ", kwota=" + kwota + ", aktywny=" + aktywny + '}';
    }
    
    
}
