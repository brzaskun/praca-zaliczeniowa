/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "pokoj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pokoj.findAll", query = "SELECT p FROM Pokoj p"),
    @NamedQuery(name = "Pokoj.findById", query = "SELECT p FROM Pokoj p WHERE p.id = :id"),
    @NamedQuery(name = "Pokoj.findByIloscosob", query = "SELECT p FROM Pokoj p WHERE p.iloscosob = :iloscosob"),
    @NamedQuery(name = "Pokoj.findByStandard", query = "SELECT p FROM Pokoj p WHERE p.standard = :standard"),
    @NamedQuery(name = "Pokoj.findByCena", query = "SELECT p FROM Pokoj p WHERE p.cena = :cena"),
    @NamedQuery(name = "Pokoj.findByIdhotelu", query = "SELECT p FROM Pokoj p WHERE p.idhotelu = :idhotelu")})
public class Pokoj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iloscosob")
    private int iloscosob;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "standard")
    private String standard;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private float cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idhotelu")
    private int idhotelu;

    public Pokoj() {
    }

    public Pokoj(Integer id) {
        this.id = id;
    }

    public Pokoj(Integer id, int iloscosob, String standard, float cena, int idhotelu) {
        this.id = id;
        this.iloscosob = iloscosob;
        this.standard = standard;
        this.cena = cena;
        this.idhotelu = idhotelu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIloscosob() {
        return iloscosob;
    }

    public void setIloscosob(int iloscosob) {
        this.iloscosob = iloscosob;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public int getIdhotelu() {
        return idhotelu;
    }

    public void setIdhotelu(int idhotelu) {
        this.idhotelu = idhotelu;
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
        if (!(object instanceof Pokoj)) {
            return false;
        }
        Pokoj other = (Pokoj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pokoj[ id=" + id + " ]";
    }
    
}
