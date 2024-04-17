/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "remanent",uniqueConstraints = {
    @UniqueConstraint(
            columnNames={"podid, rok"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Remanent.findAll", query = "SELECT r FROM Remanent r"),
    @NamedQuery(name = "Remanent.findById", query = "SELECT r FROM Remanent r WHERE r.id = :id"),
    @NamedQuery(name = "Remanent.findByRok", query = "SELECT r FROM Remanent r WHERE r.rok = :rok"),
    @NamedQuery(name = "Remanent.findByPodatnik", query = "SELECT r FROM Remanent r WHERE r.podid = :podatnik"),
    @NamedQuery(name = "Remanent.findByPodatnikRok", query = "SELECT r FROM Remanent r WHERE r.podid = :podatnik AND r.rok = :rok"),
    @NamedQuery(name = "Remanent.findByKwota", query = "SELECT r FROM Remanent r WHERE r.kwota = :kwota")})
public class Remanent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "rok")
    private String rok;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private double kwota;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podid;

    public Remanent() {
    }

    public Remanent(Integer id) {
        this.id = id;
    }

    public Remanent(String rok, double remanentkwota, Podatnik pod) {
        this.rok = rok;
        this.kwota = remanentkwota;
        this.podid = pod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public Podatnik getPodid() {
        return podid;
    }

    public void setPodid(Podatnik podid) {
        this.podid = podid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.rok);
        hash = 17 * hash + Objects.hashCode(this.podid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Remanent other = (Remanent) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.podid, other.podid);
    }

    
    
    

    @Override
    public String toString() {
        return "javaapplication2.Remanent[ id=" + id + " ]";
    }
    
}
