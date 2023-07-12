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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "fakturaopisuslugi", schema = "kadry", uniqueConstraints = {
    @UniqueConstraint(columnNames={"opis"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fakturaopisuslugi.findAll", query = "SELECT f FROM Fakturaopisuslugi f"),
    @NamedQuery(name = "Fakturaopisuslugi.findById", query = "SELECT f FROM Fakturaopisuslugi f WHERE f.id = :id"),
    @NamedQuery(name = "Fakturaopisuslugi.findByOpis", query = "SELECT f FROM Fakturaopisuslugi f WHERE f.opis = :opis")})
public class Fakturaopisuslugi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "opis")
    private String opis;
    @Column(name = "listawz")
    private boolean listawz;
    @Column(name = "listauz")
    private boolean listauz;
    @Column(name = "listaud")
    private boolean listaud;
    @Column(name = "listaos")
    private boolean listaos;
    @Column(name = "listaza")
    private boolean listaza;
    @Column(name = "listazr")
    private boolean listazr;
    @Column(name = "umowa")
    private boolean umowa;
    @Column(name = "aneks")
    private boolean aneks;
    @Column(name = "rozwiazanie")
    private boolean rozwiazanie;
    @Column(name = "swiadectwo")
    private boolean swiadectwo;
    @Column(name = "pit11")
    private boolean pit11;
    @Column(name = "pit4")
    private boolean pit4;
    @Column(name = "angaz")
    private boolean angaz;
    @Column(name = "a1")
    private boolean a1;
    @Column(name = "komornik")
    private boolean komornik;
    

    public Fakturaopisuslugi() {
    }

    public Fakturaopisuslugi(Integer id) {
        this.id = id;
    }

    public Fakturaopisuslugi(Integer id, String opis) {
        this.id = id;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Fakturaopisuslugi)) {
            return false;
        }
        Fakturaopisuslugi other = (Fakturaopisuslugi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fakturaopisuslugi{" + "opis=" + opis + '}';
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public boolean isListawz() {
        return listawz;
    }

    public void setListawz(boolean listawz) {
        this.listawz = listawz;
    }

    public boolean isListauz() {
        return listauz;
    }

    public void setListauz(boolean listauz) {
        this.listauz = listauz;
    }

    public boolean isListaud() {
        return listaud;
    }

    public void setListaud(boolean listaud) {
        this.listaud = listaud;
    }

    public boolean isListaos() {
        return listaos;
    }

    public void setListaos(boolean listaos) {
        this.listaos = listaos;
    }

    public boolean isListaza() {
        return listaza;
    }

    public void setListaza(boolean listaza) {
        this.listaza = listaza;
    }

    public boolean isListazr() {
        return listazr;
    }

    public void setListazr(boolean listazr) {
        this.listazr = listazr;
    }

    public boolean isUmowa() {
        return umowa;
    }

    public void setUmowa(boolean umowa) {
        this.umowa = umowa;
    }

    public boolean isAneks() {
        return aneks;
    }

    public void setAneks(boolean aneks) {
        this.aneks = aneks;
    }

    public boolean isRozwiazanie() {
        return rozwiazanie;
    }

    public void setRozwiazanie(boolean rozwiazanie) {
        this.rozwiazanie = rozwiazanie;
    }

    public boolean isSwiadectwo() {
        return swiadectwo;
    }

    public void setSwiadectwo(boolean swiadectwo) {
        this.swiadectwo = swiadectwo;
    }

    public boolean isPit11() {
        return pit11;
    }

    public void setPit11(boolean pit11) {
        this.pit11 = pit11;
    }

    public boolean isPit4() {
        return pit4;
    }

    public void setPit4(boolean pit4) {
        this.pit4 = pit4;
    }

    public boolean isAngaz() {
        return angaz;
    }

    public void setAngaz(boolean angaz) {
        this.angaz = angaz;
    }

    public boolean isA1() {
        return a1;
    }

    public void setA1(boolean a1) {
        this.a1 = a1;
    }

    public boolean isKomornik() {
        return komornik;
    }

    public void setKomornik(boolean komornik) {
        this.komornik = komornik;
    }

  
    
    
}
