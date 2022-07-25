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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "ARCHIWUM_O")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArchiwumO.findAll", query = "SELECT a FROM ArchiwumO a"),
    @NamedQuery(name = "ArchiwumO.findById", query = "SELECT a FROM ArchiwumO a WHERE a.id = :id"),
    @NamedQuery(name = "ArchiwumO.findByTypArchiwum", query = "SELECT a FROM ArchiwumO a WHERE a.typArchiwum = :typArchiwum"),
    @NamedQuery(name = "ArchiwumO.findByStatusarch", query = "SELECT a FROM ArchiwumO a WHERE a.statusarch = :statusarch"),
    @NamedQuery(name = "ArchiwumO.findByIdPlatnik", query = "SELECT a FROM ArchiwumO a WHERE a.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "ArchiwumO.findByIdZestaw", query = "SELECT a FROM ArchiwumO a WHERE a.idZestaw = :idZestaw"),
    @NamedQuery(name = "ArchiwumO.findByNumer", query = "SELECT a FROM ArchiwumO a WHERE a.numer = :numer"),
    @NamedQuery(name = "ArchiwumO.findByNazwa", query = "SELECT a FROM ArchiwumO a WHERE a.nazwa = :nazwa"),
    @NamedQuery(name = "ArchiwumO.findByDataWys", query = "SELECT a FROM ArchiwumO a WHERE a.dataWys = :dataWys"),
    @NamedQuery(name = "ArchiwumO.findByDataPotw", query = "SELECT a FROM ArchiwumO a WHERE a.dataPotw = :dataPotw"),
    @NamedQuery(name = "ArchiwumO.findByDataArch", query = "SELECT a FROM ArchiwumO a WHERE a.dataArch = :dataArch"),
    @NamedQuery(name = "ArchiwumO.findByDataOdtw", query = "SELECT a FROM ArchiwumO a WHERE a.dataOdtw = :dataOdtw"),
    @NamedQuery(name = "ArchiwumO.findByTrybarch", query = "SELECT a FROM ArchiwumO a WHERE a.trybarch = :trybarch"),
    @NamedQuery(name = "ArchiwumO.findByInserttmp", query = "SELECT a FROM ArchiwumO a WHERE a.inserttmp = :inserttmp"),
    @NamedQuery(name = "ArchiwumO.findByIdProgram", query = "SELECT a FROM ArchiwumO a WHERE a.idProgram = :idProgram")})
public class ArchiwumO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TYP_ARCHIWUM", nullable = false)
    private Character typArchiwum;
    @Column(name = "STATUSARCH")
    private Character statusarch;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_ZESTAW")
    private Integer idZestaw;
    @Column(name = "NUMER")
    private Integer numer;
    @Size(max = 255)
    @Column(name = "NAZWA", length = 255)
    private String nazwa;
    @Column(name = "DATA_WYS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataWys;
    @Column(name = "DATA_POTW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPotw;
    @Column(name = "DATA_ARCH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataArch;
    @Column(name = "DATA_ODTW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOdtw;
    @Column(name = "TRYBARCH")
    private Character trybarch;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PROGRAM")
    private Integer idProgram;

    public ArchiwumO() {
    }

    public ArchiwumO(Integer id) {
        this.id = id;
    }

    public ArchiwumO(Integer id, Character typArchiwum) {
        this.id = id;
        this.typArchiwum = typArchiwum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getTypArchiwum() {
        return typArchiwum;
    }

    public void setTypArchiwum(Character typArchiwum) {
        this.typArchiwum = typArchiwum;
    }

    public Character getStatusarch() {
        return statusarch;
    }

    public void setStatusarch(Character statusarch) {
        this.statusarch = statusarch;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdZestaw() {
        return idZestaw;
    }

    public void setIdZestaw(Integer idZestaw) {
        this.idZestaw = idZestaw;
    }

    public Integer getNumer() {
        return numer;
    }

    public void setNumer(Integer numer) {
        this.numer = numer;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Date getDataWys() {
        return dataWys;
    }

    public void setDataWys(Date dataWys) {
        this.dataWys = dataWys;
    }

    public Date getDataPotw() {
        return dataPotw;
    }

    public void setDataPotw(Date dataPotw) {
        this.dataPotw = dataPotw;
    }

    public Date getDataArch() {
        return dataArch;
    }

    public void setDataArch(Date dataArch) {
        this.dataArch = dataArch;
    }

    public Date getDataOdtw() {
        return dataOdtw;
    }

    public void setDataOdtw(Date dataOdtw) {
        this.dataOdtw = dataOdtw;
    }

    public Character getTrybarch() {
        return trybarch;
    }

    public void setTrybarch(Character trybarch) {
        this.trybarch = trybarch;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(Integer idProgram) {
        this.idProgram = idProgram;
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
        if (!(object instanceof ArchiwumO)) {
            return false;
        }
        ArchiwumO other = (ArchiwumO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.ArchiwumO[ id=" + id + " ]";
    }
    
}
