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
@Table(name = "PLATN_IWA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnIwa.findAll", query = "SELECT p FROM PlatnIwa p"),
    @NamedQuery(name = "PlatnIwa.findById", query = "SELECT p FROM PlatnIwa p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnIwa.findByIdPlatnik", query = "SELECT p FROM PlatnIwa p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnIwa.findByIdPlZus", query = "SELECT p FROM PlatnIwa p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnIwa.findByI11idinfor", query = "SELECT p FROM PlatnIwa p WHERE p.i11idinfor = :i11idinfor"),
    @NamedQuery(name = "PlatnIwa.findByI12okrinfor", query = "SELECT p FROM PlatnIwa p WHERE p.i12okrinfor = :i12okrinfor"),
    @NamedQuery(name = "PlatnIwa.findByVi1Datawypel", query = "SELECT p FROM PlatnIwa p WHERE p.vi1Datawypel = :vi1Datawypel"),
    @NamedQuery(name = "PlatnIwa.findByInserttmp", query = "SELECT p FROM PlatnIwa p WHERE p.inserttmp = :inserttmp")})
public class PlatnIwa implements Serializable {

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
    @Column(name = "I_1_1IDINFOR")
    private Integer i11idinfor;
    @Column(name = "I_1_2OKRINFOR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i12okrinfor;
    @Column(name = "VI_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vi1Datawypel;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnIwa() {
    }

    public PlatnIwa(Integer id) {
        this.id = id;
    }

    public PlatnIwa(Integer id, int idPlatnik) {
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

    public Integer getI11idinfor() {
        return i11idinfor;
    }

    public void setI11idinfor(Integer i11idinfor) {
        this.i11idinfor = i11idinfor;
    }

    public Date getI12okrinfor() {
        return i12okrinfor;
    }

    public void setI12okrinfor(Date i12okrinfor) {
        this.i12okrinfor = i12okrinfor;
    }

    public Date getVi1Datawypel() {
        return vi1Datawypel;
    }

    public void setVi1Datawypel(Date vi1Datawypel) {
        this.vi1Datawypel = vi1Datawypel;
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
        if (!(object instanceof PlatnIwa)) {
            return false;
        }
        PlatnIwa other = (PlatnIwa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnIwa[ id=" + id + " ]";
    }
    
}
