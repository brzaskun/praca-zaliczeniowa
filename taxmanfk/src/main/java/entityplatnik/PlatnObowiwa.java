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
@Table(name = "PLATN_OBOWIWA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnObowiwa.findAll", query = "SELECT p FROM PlatnObowiwa p"),
    @NamedQuery(name = "PlatnObowiwa.findById", query = "SELECT p FROM PlatnObowiwa p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnObowiwa.findByIdPlatnik", query = "SELECT p FROM PlatnObowiwa p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnObowiwa.findByIdPlZus", query = "SELECT p FROM PlatnObowiwa p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnObowiwa.findByRok", query = "SELECT p FROM PlatnObowiwa p WHERE p.rok = :rok"),
    @NamedQuery(name = "PlatnObowiwa.findByStatusZapisu", query = "SELECT p FROM PlatnObowiwa p WHERE p.statusZapisu = :statusZapisu"),
    @NamedQuery(name = "PlatnObowiwa.findByDataUstaleniaZapisu", query = "SELECT p FROM PlatnObowiwa p WHERE p.dataUstaleniaZapisu = :dataUstaleniaZapisu"),
    @NamedQuery(name = "PlatnObowiwa.findByStatusDane", query = "SELECT p FROM PlatnObowiwa p WHERE p.statusDane = :statusDane"),
    @NamedQuery(name = "PlatnObowiwa.findByInserttmp", query = "SELECT p FROM PlatnObowiwa p WHERE p.inserttmp = :inserttmp")})
public class PlatnObowiwa implements Serializable {

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
    @Column(name = "ROK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rok;
    @Column(name = "STATUS_ZAPISU")
    private Character statusZapisu;
    @Column(name = "DATA_USTALENIA_ZAPISU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUstaleniaZapisu;
    @Column(name = "STATUS_DANE")
    private Character statusDane;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnObowiwa() {
    }

    public PlatnObowiwa(Integer id) {
        this.id = id;
    }

    public PlatnObowiwa(Integer id, int idPlatnik) {
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

    public Date getRok() {
        return rok;
    }

    public void setRok(Date rok) {
        this.rok = rok;
    }

    public Character getStatusZapisu() {
        return statusZapisu;
    }

    public void setStatusZapisu(Character statusZapisu) {
        this.statusZapisu = statusZapisu;
    }

    public Date getDataUstaleniaZapisu() {
        return dataUstaleniaZapisu;
    }

    public void setDataUstaleniaZapisu(Date dataUstaleniaZapisu) {
        this.dataUstaleniaZapisu = dataUstaleniaZapisu;
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
        if (!(object instanceof PlatnObowiwa)) {
            return false;
        }
        PlatnObowiwa other = (PlatnObowiwa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnObowiwa[ id=" + id + " ]";
    }
    
}
