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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "evpozycja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evpozycja.findAll", query = "SELECT e FROM Evpozycja e"),
    @NamedQuery(name = "Evpozycja.findByNazwapola", query = "SELECT e FROM Evpozycja e WHERE e.nazwapola = :nazwapola"),
    @NamedQuery(name = "Evpozycja.findByNrpolanetto", query = "SELECT e FROM Evpozycja e WHERE e.nrpolanetto = :nrpolanetto"),
    @NamedQuery(name = "Evpozycja.findByNrpolavat", query = "SELECT e FROM Evpozycja e WHERE e.nrpolavat = :nrpolavat")})
public class Evpozycja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwapola")
    private String nazwapola;
    @Size(max = 255)
    @Column(name = "nrpolanetto")
    private String nrpolanetto;
    @Size(max = 255)
    @Column(name = "nrpolavat")
    private String nrpolavat;
    @JoinColumn(name = "macierzysty", referencedColumnName = "nazwapola",nullable = true)
    @ManyToOne
    private Evpozycja macierzysty;

    public Evpozycja() {
    }

    public Evpozycja(String nazwapola) {
        this.nazwapola = nazwapola;
    }

    public String getNazwapola() {
        return nazwapola;
    }

    public void setNazwapola(String nazwapola) {
        this.nazwapola = nazwapola;
    }

    public String getNrpolanetto() {
        return nrpolanetto;
    }

    public void setNrpolanetto(String nrpolanetto) {
        this.nrpolanetto = nrpolanetto;
    }

    public String getNrpolavat() {
        return nrpolavat;
    }

    public void setNrpolavat(String nrpolavat) {
        this.nrpolavat = nrpolavat;
    }

    public Evpozycja getMacierzysty() {
        return macierzysty;
    }

    public void setMacierzysty(Evpozycja macierzysty) {
        this.macierzysty = macierzysty;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nazwapola != null ? nazwapola.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evpozycja)) {
            return false;
        }
        Evpozycja other = (Evpozycja) object;
        if ((this.nazwapola == null && other.nazwapola != null) || (this.nazwapola != null && !this.nazwapola.equals(other.nazwapola))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazwapola;
    }
    
}
