/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "PLIK_POTW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlikPotw.findAll", query = "SELECT p FROM PlikPotw p"),
    @NamedQuery(name = "PlikPotw.findById", query = "SELECT p FROM PlikPotw p WHERE p.id = :id"),
    @NamedQuery(name = "PlikPotw.findByIdPrzesylki", query = "SELECT p FROM PlikPotw p WHERE p.idPrzesylki = :idPrzesylki"),
    @NamedQuery(name = "PlikPotw.findByInserttmp", query = "SELECT p FROM PlikPotw p WHERE p.inserttmp = :inserttmp")})
public class PlikPotw implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "POTWIERDZENIE", length = 2147483647)
    private String potwierdzenie;
    @Size(max = 35)
    @Column(name = "ID_PRZESYLKI", length = 35)
    private String idPrzesylki;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlikPotw() {
    }

    public PlikPotw(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPotwierdzenie() {
        return potwierdzenie;
    }

    public void setPotwierdzenie(String potwierdzenie) {
        this.potwierdzenie = potwierdzenie;
    }

    public String getIdPrzesylki() {
        return idPrzesylki;
    }

    public void setIdPrzesylki(String idPrzesylki) {
        this.idPrzesylki = idPrzesylki;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
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
        if (!(object instanceof PlikPotw)) {
            return false;
        }
        PlikPotw other = (PlikPotw) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlikPotw[ id=" + id + " ]";
    }
    
}
