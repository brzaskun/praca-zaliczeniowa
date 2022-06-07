/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "PLATN_OSWRIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnOswria.findAll", query = "SELECT p FROM PlatnOswria p"),
    @NamedQuery(name = "PlatnOswria.findById", query = "SELECT p FROM PlatnOswria p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnOswria.findByIdPlZus", query = "SELECT p FROM PlatnOswria p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnOswria.findByIdPlatnik", query = "SELECT p FROM PlatnOswria p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnOswria.findByStatusOsw", query = "SELECT p FROM PlatnOswria p WHERE p.statusOsw = :statusOsw"),
    @NamedQuery(name = "PlatnOswria.findByDataWypelZglOsw", query = "SELECT p FROM PlatnOswria p WHERE p.dataWypelZglOsw = :dataWypelZglOsw"),
    @NamedQuery(name = "PlatnOswria.findByDataWypelOdwOsw", query = "SELECT p FROM PlatnOswria p WHERE p.dataWypelOdwOsw = :dataWypelOdwOsw"),
    @NamedQuery(name = "PlatnOswria.findByCzyRiaPlatnik", query = "SELECT p FROM PlatnOswria p WHERE p.czyRiaPlatnik = :czyRiaPlatnik"),
    @NamedQuery(name = "PlatnOswria.findByInserttmp", query = "SELECT p FROM PlatnOswria p WHERE p.inserttmp = :inserttmp")})
public class PlatnOswria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_PL_ZUS")
    private BigInteger idPlZus;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Size(max = 1)
    @Column(name = "STATUS_OSW", length = 1)
    private String statusOsw;
    @Column(name = "DATA_WYPEL_ZGL_OSW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataWypelZglOsw;
    @Column(name = "DATA_WYPEL_ODW_OSW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataWypelOdwOsw;
    @Size(max = 1)
    @Column(name = "CZY_RIA_PLATNIK", length = 1)
    private String czyRiaPlatnik;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnOswria() {
    }

    public PlatnOswria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(BigInteger idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public String getStatusOsw() {
        return statusOsw;
    }

    public void setStatusOsw(String statusOsw) {
        this.statusOsw = statusOsw;
    }

    public Date getDataWypelZglOsw() {
        return dataWypelZglOsw;
    }

    public void setDataWypelZglOsw(Date dataWypelZglOsw) {
        this.dataWypelZglOsw = dataWypelZglOsw;
    }

    public Date getDataWypelOdwOsw() {
        return dataWypelOdwOsw;
    }

    public void setDataWypelOdwOsw(Date dataWypelOdwOsw) {
        this.dataWypelOdwOsw = dataWypelOdwOsw;
    }

    public String getCzyRiaPlatnik() {
        return czyRiaPlatnik;
    }

    public void setCzyRiaPlatnik(String czyRiaPlatnik) {
        this.czyRiaPlatnik = czyRiaPlatnik;
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
        if (!(object instanceof PlatnOswria)) {
            return false;
        }
        PlatnOswria other = (PlatnOswria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnOswria[ id=" + id + " ]";
    }
    
}
