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
@Table(name = "ARCHIWUM_I")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArchiwumI.findAll", query = "SELECT a FROM ArchiwumI a"),
    @NamedQuery(name = "ArchiwumI.findById", query = "SELECT a FROM ArchiwumI a WHERE a.id = :id"),
    @NamedQuery(name = "ArchiwumI.findByIdArchORob", query = "SELECT a FROM ArchiwumI a WHERE a.idArchORob = :idArchORob"),
    @NamedQuery(name = "ArchiwumI.findByTypArchiwum", query = "SELECT a FROM ArchiwumI a WHERE a.typArchiwum = :typArchiwum"),
    @NamedQuery(name = "ArchiwumI.findByIdAoOdtw", query = "SELECT a FROM ArchiwumI a WHERE a.idAoOdtw = :idAoOdtw"),
    @NamedQuery(name = "ArchiwumI.findByIdPlatnik", query = "SELECT a FROM ArchiwumI a WHERE a.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "ArchiwumI.findByIdPlatnikRob", query = "SELECT a FROM ArchiwumI a WHERE a.idPlatnikRob = :idPlatnikRob"),
    @NamedQuery(name = "ArchiwumI.findByIdZestaw", query = "SELECT a FROM ArchiwumI a WHERE a.idZestaw = :idZestaw"),
    @NamedQuery(name = "ArchiwumI.findByIdZestawRob", query = "SELECT a FROM ArchiwumI a WHERE a.idZestawRob = :idZestawRob"),
    @NamedQuery(name = "ArchiwumI.findByDataArch", query = "SELECT a FROM ArchiwumI a WHERE a.dataArch = :dataArch"),
    @NamedQuery(name = "ArchiwumI.findByTrybarch", query = "SELECT a FROM ArchiwumI a WHERE a.trybarch = :trybarch"),
    @NamedQuery(name = "ArchiwumI.findByInserttmp", query = "SELECT a FROM ArchiwumI a WHERE a.inserttmp = :inserttmp"),
    @NamedQuery(name = "ArchiwumI.findByIdProgram", query = "SELECT a FROM ArchiwumI a WHERE a.idProgram = :idProgram")})
public class ArchiwumI implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_ARCH_O_ROB")
    private Integer idArchORob;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TYP_ARCHIWUM", nullable = false)
    private Character typArchiwum;
    @Column(name = "ID_AO_ODTW")
    private Integer idAoOdtw;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_PLATNIK_ROB")
    private Integer idPlatnikRob;
    @Column(name = "ID_ZESTAW")
    private Integer idZestaw;
    @Column(name = "ID_ZESTAW_ROB")
    private Integer idZestawRob;
    @Column(name = "DATA_ARCH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataArch;
    @Column(name = "TRYBARCH")
    private Character trybarch;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PROGRAM")
    private Integer idProgram;

    public ArchiwumI() {
    }

    public ArchiwumI(Integer id) {
        this.id = id;
    }

    public ArchiwumI(Integer id, Character typArchiwum) {
        this.id = id;
        this.typArchiwum = typArchiwum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdArchORob() {
        return idArchORob;
    }

    public void setIdArchORob(Integer idArchORob) {
        this.idArchORob = idArchORob;
    }

    public Character getTypArchiwum() {
        return typArchiwum;
    }

    public void setTypArchiwum(Character typArchiwum) {
        this.typArchiwum = typArchiwum;
    }

    public Integer getIdAoOdtw() {
        return idAoOdtw;
    }

    public void setIdAoOdtw(Integer idAoOdtw) {
        this.idAoOdtw = idAoOdtw;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdPlatnikRob() {
        return idPlatnikRob;
    }

    public void setIdPlatnikRob(Integer idPlatnikRob) {
        this.idPlatnikRob = idPlatnikRob;
    }

    public Integer getIdZestaw() {
        return idZestaw;
    }

    public void setIdZestaw(Integer idZestaw) {
        this.idZestaw = idZestaw;
    }

    public Integer getIdZestawRob() {
        return idZestawRob;
    }

    public void setIdZestawRob(Integer idZestawRob) {
        this.idZestawRob = idZestawRob;
    }

    public Date getDataArch() {
        return dataArch;
    }

    public void setDataArch(Date dataArch) {
        this.dataArch = dataArch;
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
        if (!(object instanceof ArchiwumI)) {
            return false;
        }
        ArchiwumI other = (ArchiwumI) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.ArchiwumI[ id=" + id + " ]";
    }
    
}
