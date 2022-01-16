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
@Table(name = "szkolenieust", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"firma", "nazwaszkolenia"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Szkolenieust.findAll", query = "SELECT s FROM Szkolenieust s"),
    @NamedQuery(name = "Szkolenieust.findById", query = "SELECT s FROM Szkolenieust s WHERE s.id = :id"),
    @NamedQuery(name = "Szkolenieust.findByFirma", query = "SELECT s FROM Szkolenieust s WHERE s.firma = :firma"),
    @NamedQuery(name = "Szkolenieust.findByNazwaszkolenia", query = "SELECT s FROM Szkolenieust s WHERE s.nazwaszkolenia = :nazwaszkolenia"),
    @NamedQuery(name = "Szkolenieust.findByIloscpytan", query = "SELECT s FROM Szkolenieust s WHERE s.iloscpytan = :iloscpytan"),
    @NamedQuery(name = "Szkolenieust.findByEmail", query = "SELECT s FROM Szkolenieust s WHERE s.email = :email"),
    @NamedQuery(name = "Szkolenieust.findByProgzdawalnosci", query = "SELECT s FROM Szkolenieust s WHERE s.progzdawalnosci = :progzdawalnosci"),
    @NamedQuery(name = "Szkolenieust.findByUpowaznienie", query = "SELECT s FROM Szkolenieust s WHERE s.upowaznienie = :upowaznienie"),
    @NamedQuery(name = "Szkolenieust.findByFirmaId", query = "SELECT s FROM Szkolenieust s WHERE s.firmaId = :firmaId")})
public class Szkolenieust implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "firma", nullable = false, length = 255)
    private String firma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nazwaszkolenia", nullable = false, length = 100)
    private String nazwaszkolenia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iloscpytan", nullable = false)
    private int iloscpytan;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 256)
    @Column(name = "email", length = 256)
    private String email;
    @Column(name = "progzdawalnosci")
    private Integer progzdawalnosci;
    @Size(max = 45)
    @Column(name = "upowaznienie", length = 45)
    private String upowaznienie;
    @Column(name = "firma_id")
    private Integer firmaId;

    public Szkolenieust() {
    }

    public Szkolenieust(Integer id) {
        this.id = id;
    }

    public Szkolenieust(Integer id, String firma, String nazwaszkolenia, int iloscpytan) {
        this.id = id;
        this.firma = firma;
        this.nazwaszkolenia = nazwaszkolenia;
        this.iloscpytan = iloscpytan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getNazwaszkolenia() {
        return nazwaszkolenia;
    }

    public void setNazwaszkolenia(String nazwaszkolenia) {
        this.nazwaszkolenia = nazwaszkolenia;
    }

    public int getIloscpytan() {
        return iloscpytan;
    }

    public void setIloscpytan(int iloscpytan) {
        this.iloscpytan = iloscpytan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getProgzdawalnosci() {
        return progzdawalnosci;
    }

    public void setProgzdawalnosci(Integer progzdawalnosci) {
        this.progzdawalnosci = progzdawalnosci;
    }

    public String getUpowaznienie() {
        return upowaznienie;
    }

    public void setUpowaznienie(String upowaznienie) {
        this.upowaznienie = upowaznienie;
    }

    public Integer getFirmaId() {
        return firmaId;
    }

    public void setFirmaId(Integer firmaId) {
        this.firmaId = firmaId;
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
        if (!(object instanceof Szkolenieust)) {
            return false;
        }
        Szkolenieust other = (Szkolenieust) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Szkolenieust[ id=" + id + " ]";
    }
    
}
