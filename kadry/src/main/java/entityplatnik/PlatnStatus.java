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
@Table(name = "PLATN_STATUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnStatus.findAll", query = "SELECT p FROM PlatnStatus p"),
    @NamedQuery(name = "PlatnStatus.findById", query = "SELECT p FROM PlatnStatus p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnStatus.findByIdPlatnik", query = "SELECT p FROM PlatnStatus p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnStatus.findByIdPlZus", query = "SELECT p FROM PlatnStatus p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnStatus.findByDataod", query = "SELECT p FROM PlatnStatus p WHERE p.dataod = :dataod"),
    @NamedQuery(name = "PlatnStatus.findByDatado", query = "SELECT p FROM PlatnStatus p WHERE p.datado = :datado"),
    @NamedQuery(name = "PlatnStatus.findByWartosc", query = "SELECT p FROM PlatnStatus p WHERE p.wartosc = :wartosc"),
    @NamedQuery(name = "PlatnStatus.findByStatusDane", query = "SELECT p FROM PlatnStatus p WHERE p.statusDane = :statusDane"),
    @NamedQuery(name = "PlatnStatus.findByInserttmp", query = "SELECT p FROM PlatnStatus p WHERE p.inserttmp = :inserttmp")})
public class PlatnStatus implements Serializable {

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
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "DATADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datado;
    @Column(name = "WARTOSC")
    private Character wartosc;
    @Column(name = "STATUS_DANE")
    private Character statusDane;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnStatus() {
    }

    public PlatnStatus(Integer id) {
        this.id = id;
    }

    public PlatnStatus(Integer id, int idPlatnik) {
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

    public Date getDataod() {
        return dataod;
    }

    public void setDataod(Date dataod) {
        this.dataod = dataod;
    }

    public Date getDatado() {
        return datado;
    }

    public void setDatado(Date datado) {
        this.datado = datado;
    }

    public Character getWartosc() {
        return wartosc;
    }

    public void setWartosc(Character wartosc) {
        this.wartosc = wartosc;
    }

    public Character getStatusDane() {
        return statusDane;
    }

    public void setStatusDane(Character statusDane) {
        this.statusDane = statusDane;
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
        if (!(object instanceof PlatnStatus)) {
            return false;
        }
        PlatnStatus other = (PlatnStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnStatus[ id=" + id + " ]";
    }
    
}
