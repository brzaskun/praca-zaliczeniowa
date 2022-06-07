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
@Table(name = "PLATN_HIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnHist.findAll", query = "SELECT p FROM PlatnHist p"),
    @NamedQuery(name = "PlatnHist.findById", query = "SELECT p FROM PlatnHist p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnHist.findByIdPlatnik", query = "SELECT p FROM PlatnHist p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnHist.findByIdPlatnikHist", query = "SELECT p FROM PlatnHist p WHERE p.idPlatnikHist = :idPlatnikHist"),
    @NamedQuery(name = "PlatnHist.findByDatado", query = "SELECT p FROM PlatnHist p WHERE p.datado = :datado"),
    @NamedQuery(name = "PlatnHist.findByInserttmp", query = "SELECT p FROM PlatnHist p WHERE p.inserttmp = :inserttmp")})
public class PlatnHist implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK_HIST", nullable = false)
    private int idPlatnikHist;
    @Column(name = "DATADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datado;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnHist() {
    }

    public PlatnHist(Integer id) {
        this.id = id;
    }

    public PlatnHist(Integer id, int idPlatnik, int idPlatnikHist) {
        this.id = id;
        this.idPlatnik = idPlatnik;
        this.idPlatnikHist = idPlatnikHist;
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

    public int getIdPlatnikHist() {
        return idPlatnikHist;
    }

    public void setIdPlatnikHist(int idPlatnikHist) {
        this.idPlatnikHist = idPlatnikHist;
    }

    public Date getDatado() {
        return datado;
    }

    public void setDatado(Date datado) {
        this.datado = datado;
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
        if (!(object instanceof PlatnHist)) {
            return false;
        }
        PlatnHist other = (PlatnHist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnHist[ id=" + id + " ]";
    }
    
}
