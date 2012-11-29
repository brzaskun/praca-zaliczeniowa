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
@Table(name = "evewidencja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evewidencja.findAll", query = "SELECT e FROM Evewidencja e"),
    @NamedQuery(name = "Evewidencja.findByNazwa", query = "SELECT e FROM Evewidencja e WHERE e.nazwa = :nazwa"),
    @NamedQuery(name = "Evewidencja.findByPole", query = "SELECT e FROM Evewidencja e WHERE e.pole = :pole"),
    @NamedQuery(name = "Evewidencja.findByRodzajzakupu", query = "SELECT e FROM Evewidencja e WHERE e.rodzajzakupu = :rodzajzakupu"),
    @NamedQuery(name = "Evewidencja.findByTransakcja", query = "SELECT e FROM Evewidencja e WHERE e.transakcja = :transakcja")})
public class Evewidencja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 255)
    @Column(name = "pole")
    private String pole;
    @Size(max = 255)
    @Column(name = "rodzajzakupu")
    private String rodzajzakupu;
    @Size(max = 255)
    @Column(name = "transakcja")
    private String transakcja;
    @Column(name = "tylkoNetto")
    private Boolean tylkoNetto;

    public Evewidencja() {
    }

    public Evewidencja(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getPole() {
        return pole;
    }

    public void setPole(String pole) {
        this.pole = pole;
    }

    public String getRodzajzakupu() {
        return rodzajzakupu;
    }

    public void setRodzajzakupu(String rodzajzakupu) {
        this.rodzajzakupu = rodzajzakupu;
    }

    public String getTransakcja() {
        return transakcja;
    }

    public void setTransakcja(String transakcja) {
        this.transakcja = transakcja;
    }

    public Boolean getTylkoNetto() {
        return tylkoNetto;
    }

    public void setTylkoNetto(Boolean tylkoNetto) {
        this.tylkoNetto = tylkoNetto;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nazwa != null ? nazwa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evewidencja)) {
            return false;
        }
        Evewidencja other = (Evewidencja) object;
        if ((this.nazwa == null && other.nazwa != null) || (this.nazwa != null && !this.nazwa.equals(other.nazwa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Evewidencja[ nazwa=" + nazwa + " ]";
    }
    
}
