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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kadryfakturapozycjarokmc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kadryfakturapozycjarokmc.findAll", query = "SELECT k FROM Kadryfakturapozycjarokmc k"),
    @NamedQuery(name = "Kadryfakturapozycjarokmc.findById", query = "SELECT k FROM Kadryfakturapozycjarokmc k WHERE k.id = :id"),
    @NamedQuery(name = "Kadryfakturapozycjarokmc.findByRok", query = "SELECT k FROM Kadryfakturapozycjarokmc k WHERE k.rok = :rok"),
    @NamedQuery(name = "Kadryfakturapozycjarokmc.findByMc", query = "SELECT k FROM Kadryfakturapozycjarokmc k WHERE k.mc = :mc"),
    @NamedQuery(name = "Kadryfakturapozycjarokmc.findByIlosc", query = "SELECT k FROM Kadryfakturapozycjarokmc k WHERE k.ilosc = :ilosc")})
public class Kadryfakturapozycjarokmc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ilosc")
    private Double ilosc;
    @JoinColumn(name = "kadryfakturypozycja", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Kadryfakturapozycja kadryfakturypozycja;

    public Kadryfakturapozycjarokmc() {
    }

    public Kadryfakturapozycjarokmc(Integer id) {
        this.id = id;
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

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Double getIlosc() {
        return ilosc;
    }

    public void setIlosc(Double ilosc) {
        this.ilosc = ilosc;
    }

    public Kadryfakturapozycja getKadryfakturypozycja() {
        return kadryfakturypozycja;
    }

    public void setKadryfakturypozycja(Kadryfakturapozycja kadryfakturypozycja) {
        this.kadryfakturypozycja = kadryfakturypozycja;
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
        if (!(object instanceof Kadryfakturapozycjarokmc)) {
            return false;
        }
        Kadryfakturapozycjarokmc other = (Kadryfakturapozycjarokmc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Kadryfakturapozycjarokmc[ id=" + id + " ]";
    }
    
}
