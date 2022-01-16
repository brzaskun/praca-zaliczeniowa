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
@Table(name = "uczestnikgrupy", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"grupa", "id_uczestnik"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uczestnikgrupy.findAll", query = "SELECT u FROM Uczestnikgrupy u"),
    @NamedQuery(name = "Uczestnikgrupy.findById", query = "SELECT u FROM Uczestnikgrupy u WHERE u.id = :id"),
    @NamedQuery(name = "Uczestnikgrupy.findByEmail", query = "SELECT u FROM Uczestnikgrupy u WHERE u.email = :email"),
    @NamedQuery(name = "Uczestnikgrupy.findByNazwiskoiimie", query = "SELECT u FROM Uczestnikgrupy u WHERE u.nazwiskoiimie = :nazwiskoiimie"),
    @NamedQuery(name = "Uczestnikgrupy.findByGrupa", query = "SELECT u FROM Uczestnikgrupy u WHERE u.grupa = :grupa"),
    @NamedQuery(name = "Uczestnikgrupy.findByIdUczestnik", query = "SELECT u FROM Uczestnikgrupy u WHERE u.idUczestnik = :idUczestnik")})
public class Uczestnikgrupy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "email", nullable = false, length = 128)
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "nazwiskoiimie", nullable = false, length = 256)
    private String nazwiskoiimie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "grupa", nullable = false, length = 250)
    private String grupa;
    @Column(name = "id_uczestnik")
    private Integer idUczestnik;

    public Uczestnikgrupy() {
    }

    public Uczestnikgrupy(Integer id) {
        this.id = id;
    }

    public Uczestnikgrupy(Integer id, String email, String nazwiskoiimie, String grupa) {
        this.id = id;
        this.email = email;
        this.nazwiskoiimie = nazwiskoiimie;
        this.grupa = grupa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNazwiskoiimie() {
        return nazwiskoiimie;
    }

    public void setNazwiskoiimie(String nazwiskoiimie) {
        this.nazwiskoiimie = nazwiskoiimie;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public Integer getIdUczestnik() {
        return idUczestnik;
    }

    public void setIdUczestnik(Integer idUczestnik) {
        this.idUczestnik = idUczestnik;
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
        if (!(object instanceof Uczestnikgrupy)) {
            return false;
        }
        Uczestnikgrupy other = (Uczestnikgrupy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Uczestnikgrupy[ id=" + id + " ]";
    }
    
}
