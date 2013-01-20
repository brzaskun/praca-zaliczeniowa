/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Pokoj.findByCena", query = "SELECT p FROM Pokoj p WHERE p.cena = :cena")})
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
    @Column(name = "cena")
    private float cena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpokoju")
    private List<Rezerwacja> rezerwacjaList;
    @JoinColumn(name = "idhotelu", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Hotel idhotelu;
    @JoinColumn(name = "standard", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Standard standard;

    public Pokoj() {
    }

    public Pokoj(Integer id) {
        this.id = id;
    }

    public Pokoj(Integer id, int iloscosob, float cena) {
        this.id = id;
        this.iloscosob = iloscosob;
        this.cena = cena;
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

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    @XmlTransient
    public List<Rezerwacja> getRezerwacjaList() {
        return rezerwacjaList;
    }

    public void setRezerwacjaList(List<Rezerwacja> rezerwacjaList) {
        this.rezerwacjaList = rezerwacjaList;
    }

    public Hotel getIdhotelu() {
        return idhotelu;
    }

    public void setIdhotelu(Hotel idhotelu) {
        this.idhotelu = idhotelu;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
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
