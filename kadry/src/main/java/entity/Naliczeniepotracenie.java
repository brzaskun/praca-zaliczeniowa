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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "naliczeniepotracenie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Naliczeniepotracenie.findAll", query = "SELECT n FROM Naliczeniepotracenie n"),
    @NamedQuery(name = "Naliczeniepotracenie.findById", query = "SELECT n FROM Naliczeniepotracenie n WHERE n.id = :id"),
    @NamedQuery(name = "Naliczeniepotracenie.findByKwota", query = "SELECT n FROM Naliczeniepotracenie n WHERE n.kwota = :kwota")})
public class Naliczeniepotracenie implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private double kwota;
    @JoinColumn(name = "pasekwynagrodzen", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pasekwynagrodzen pasekwynagrodzen;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "skladnikpotracenia", referencedColumnName = "id")
    @ManyToOne
    private Skladnikpotracenia skladnikpotracenia;

    public Naliczeniepotracenie() {
    }

    public Naliczeniepotracenie(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Skladnikpotracenia getSkladnikpotracenia() {
        return skladnikpotracenia;
    }

    public void setSkladnikpotracenia(Skladnikpotracenia skladnikpotracenia) {
        this.skladnikpotracenia = skladnikpotracenia;
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
        if (!(object instanceof Naliczeniepotracenie)) {
            return false;
        }
        Naliczeniepotracenie other = (Naliczeniepotracenie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Naliczeniepotracenie[ id=" + id + " ]";
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public Pasekwynagrodzen getPasekwynagrodzen() {
        return pasekwynagrodzen;
    }

    public void setPasekwynagrodzen(Pasekwynagrodzen pasekwynagrodzen) {
        this.pasekwynagrodzen = pasekwynagrodzen;
    }
    
}
