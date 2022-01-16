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
import javax.persistence.Lob;
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
@Table(name = "szkoleniepodglad", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nazwaszkolenia", "naglowek"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Szkoleniepodglad.findAll", query = "SELECT s FROM Szkoleniepodglad s"),
    @NamedQuery(name = "Szkoleniepodglad.findById", query = "SELECT s FROM Szkoleniepodglad s WHERE s.id = :id"),
    @NamedQuery(name = "Szkoleniepodglad.findByNazwaszkolenia", query = "SELECT s FROM Szkoleniepodglad s WHERE s.nazwaszkolenia = :nazwaszkolenia"),
    @NamedQuery(name = "Szkoleniepodglad.findByNaglowek", query = "SELECT s FROM Szkoleniepodglad s WHERE s.naglowek = :naglowek"),
    @NamedQuery(name = "Szkoleniepodglad.findByTemat", query = "SELECT s FROM Szkoleniepodglad s WHERE s.temat = :temat")})
public class Szkoleniepodglad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(name = "nazwaszkolenia", length = 255)
    private String nazwaszkolenia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "naglowek", nullable = false, length = 255)
    private String naglowek;
    @Size(max = 255)
    @Column(name = "temat", length = 255)
    private String temat;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "tresc", nullable = false, length = 16777215)
    private String tresc;

    public Szkoleniepodglad() {
    }

    public Szkoleniepodglad(Integer id) {
        this.id = id;
    }

    public Szkoleniepodglad(Integer id, String naglowek, String tresc) {
        this.id = id;
        this.naglowek = naglowek;
        this.tresc = tresc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwaszkolenia() {
        return nazwaszkolenia;
    }

    public void setNazwaszkolenia(String nazwaszkolenia) {
        this.nazwaszkolenia = nazwaszkolenia;
    }

    public String getNaglowek() {
        return naglowek;
    }

    public void setNaglowek(String naglowek) {
        this.naglowek = naglowek;
    }

    public String getTemat() {
        return temat;
    }

    public void setTemat(String temat) {
        this.temat = temat;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
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
        if (!(object instanceof Szkoleniepodglad)) {
            return false;
        }
        Szkoleniepodglad other = (Szkoleniepodglad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Szkoleniepodglad[ id=" + id + " ]";
    }
    
}
