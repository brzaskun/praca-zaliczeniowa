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
@Table(name = "rodzajlistyplac_rodzajwynagrodzenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RodzajlistyplacRodzajwynagrodzenia.findAll", query = "SELECT d FROM RodzajlistyplacRodzajwynagrodzenia d"),
    @NamedQuery(name = "RodzajlistyplacRodzajwynagrodzenia.findById", query = "SELECT d FROM RodzajlistyplacRodzajwynagrodzenia d WHERE d.id = :id"),
    @NamedQuery(name = "RodzajlistyplacRodzajwynagrodzenia.findByRodzajwynagrodzenia", query = "SELECT d FROM RodzajlistyplacRodzajwynagrodzenia d WHERE d.rodzajlistyplac = :rodzajlistyplac"),
    @NamedQuery(name = "RodzajlistyplacRodzajwynagrodzenia.findByRodzajwynagrodzeniaNOT", query = "SELECT d FROM RodzajlistyplacRodzajwynagrodzenia d WHERE d.rodzajlistyplac != :rodzajlistyplac"),
    @NamedQuery(name = "RodzajlistyplacRodzajwynagrodzenia.deleteByRodzajwynagrodzenia", query = "DELETE FROM RodzajlistyplacRodzajwynagrodzenia d WHERE d.rodzajwynagrodzenia = :rodzajwynagrodzenia")
})
public class RodzajlistyplacRodzajwynagrodzenia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "rodzajlistyplac", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Rodzajlistyplac rodzajlistyplac;
    @JoinColumn(name = "rodzajwynagrodzenia", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Rodzajwynagrodzenia rodzajwynagrodzenia;

    public RodzajlistyplacRodzajwynagrodzenia() {
    }

    public RodzajlistyplacRodzajwynagrodzenia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rodzajlistyplac getRodzajlistyplac() {
        return rodzajlistyplac;
    }

    public void setRodzajlistyplac(Rodzajlistyplac rodzajlistyplac) {
        this.rodzajlistyplac = rodzajlistyplac;
    }



    public Rodzajwynagrodzenia getRodzajwynagrodzenia() {
        return rodzajwynagrodzenia;
    }

    public void setRodzajwynagrodzenia(Rodzajwynagrodzenia rodzajwynagrodzenia) {
        this.rodzajwynagrodzenia = rodzajwynagrodzenia;
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
        if (!(object instanceof RodzajlistyplacRodzajwynagrodzenia)) {
            return false;
        }
        RodzajlistyplacRodzajwynagrodzenia other = (RodzajlistyplacRodzajwynagrodzenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RodzajlistyplacRodzajwynagrodzenia{" + "rodzajlistyplac=" + rodzajlistyplac.getNazwa() + ", rodzajwynagrodzenia=" + rodzajwynagrodzenia.getOpisskrocony() + '}';
    }

    
    
}
