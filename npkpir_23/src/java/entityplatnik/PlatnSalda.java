/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PLATN_SALDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnSalda.findAll", query = "SELECT p FROM PlatnSalda p"),
    @NamedQuery(name = "PlatnSalda.findById", query = "SELECT p FROM PlatnSalda p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnSalda.findByIdPlatnik", query = "SELECT p FROM PlatnSalda p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnSalda.findByIdPlZus", query = "SELECT p FROM PlatnSalda p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnSalda.findBySaldoWn", query = "SELECT p FROM PlatnSalda p WHERE p.saldoWn = :saldoWn"),
    @NamedQuery(name = "PlatnSalda.findByDataOstObr", query = "SELECT p FROM PlatnSalda p WHERE p.dataOstObr = :dataOstObr"),
    @NamedQuery(name = "PlatnSalda.findByZakres", query = "SELECT p FROM PlatnSalda p WHERE p.zakres = :zakres"),
    @NamedQuery(name = "PlatnSalda.findByInserttmp", query = "SELECT p FROM PlatnSalda p WHERE p.inserttmp = :inserttmp")})
public class PlatnSalda implements Serializable {

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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALDO_WN", precision = 15, scale = 2)
    private BigDecimal saldoWn;
    @Column(name = "DATA_OST_OBR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOstObr;
    @Column(name = "ZAKRES")
    private Integer zakres;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnSalda() {
    }

    public PlatnSalda(Integer id) {
        this.id = id;
    }

    public PlatnSalda(Integer id, int idPlatnik) {
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

    public BigDecimal getSaldoWn() {
        return saldoWn;
    }

    public void setSaldoWn(BigDecimal saldoWn) {
        this.saldoWn = saldoWn;
    }

    public Date getDataOstObr() {
        return dataOstObr;
    }

    public void setDataOstObr(Date dataOstObr) {
        this.dataOstObr = dataOstObr;
    }

    public Integer getZakres() {
        return zakres;
    }

    public void setZakres(Integer zakres) {
        this.zakres = zakres;
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
        if (!(object instanceof PlatnSalda)) {
            return false;
        }
        PlatnSalda other = (PlatnSalda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnSalda[ id=" + id + " ]";
    }
    
}
