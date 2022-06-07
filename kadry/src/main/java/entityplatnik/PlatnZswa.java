/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "PLATN_ZSWA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnZswa.findAll", query = "SELECT p FROM PlatnZswa p"),
    @NamedQuery(name = "PlatnZswa.findById", query = "SELECT p FROM PlatnZswa p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnZswa.findByIdPlatnik", query = "SELECT p FROM PlatnZswa p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnZswa.findByIdPlZus", query = "SELECT p FROM PlatnZswa p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnZswa.findByI11idzglosz", query = "SELECT p FROM PlatnZswa p WHERE p.i11idzglosz = :i11idzglosz"),
    @NamedQuery(name = "PlatnZswa.findByI12okreszgl", query = "SELECT p FROM PlatnZswa p WHERE p.i12okreszgl = :i12okreszgl"),
    @NamedQuery(name = "PlatnZswa.findByInserttmp", query = "SELECT p FROM PlatnZswa p WHERE p.inserttmp = :inserttmp")})
public class PlatnZswa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "I_1_1IDZGLOSZ")
    private Integer i11idzglosz;
    @Column(name = "I_1_2OKRESZGL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i12okreszgl;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnZswa() {
    }

    public PlatnZswa(Integer id) {
        this.id = id;
    }

    public PlatnZswa(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getI11idzglosz() {
        return i11idzglosz;
    }

    public void setI11idzglosz(Integer i11idzglosz) {
        this.i11idzglosz = i11idzglosz;
    }

    public Date getI12okreszgl() {
        return i12okreszgl;
    }

    public void setI12okreszgl(Date i12okreszgl) {
        this.i12okreszgl = i12okreszgl;
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
        if (!(object instanceof PlatnZswa)) {
            return false;
        }
        PlatnZswa other = (PlatnZswa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnZswa[ id=" + id + " ]";
    }
    
}
