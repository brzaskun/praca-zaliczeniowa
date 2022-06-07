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
@Table(name = "PLATN_MDG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnMdg.findAll", query = "SELECT p FROM PlatnMdg p"),
    @NamedQuery(name = "PlatnMdg.findById", query = "SELECT p FROM PlatnMdg p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnMdg.findByIdPlatnik", query = "SELECT p FROM PlatnMdg p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnMdg.findByIdPlZus", query = "SELECT p FROM PlatnMdg p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnMdg.findByIdUbZus", query = "SELECT p FROM PlatnMdg p WHERE p.idUbZus = :idUbZus"),
    @NamedQuery(name = "PlatnMdg.findByLiczbaDniDzial", query = "SELECT p FROM PlatnMdg p WHERE p.liczbaDniDzial = :liczbaDniDzial"),
    @NamedQuery(name = "PlatnMdg.findByDataCzasRej", query = "SELECT p FROM PlatnMdg p WHERE p.dataCzasRej = :dataCzasRej"),
    @NamedQuery(name = "PlatnMdg.findByRokDniDzial", query = "SELECT p FROM PlatnMdg p WHERE p.rokDniDzial = :rokDniDzial"),
    @NamedQuery(name = "PlatnMdg.findByInserttmp", query = "SELECT p FROM PlatnMdg p WHERE p.inserttmp = :inserttmp")})
public class PlatnMdg implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Column(name = "LICZBA_DNI_DZIAL")
    private Integer liczbaDniDzial;
    @Column(name = "DATA_CZAS_REJ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCzasRej;
    @Column(name = "ROK_DNI_DZIAL")
    private Integer rokDniDzial;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnMdg() {
    }

    public PlatnMdg(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(Integer idUbZus) {
        this.idUbZus = idUbZus;
    }

    public Integer getLiczbaDniDzial() {
        return liczbaDniDzial;
    }

    public void setLiczbaDniDzial(Integer liczbaDniDzial) {
        this.liczbaDniDzial = liczbaDniDzial;
    }

    public Date getDataCzasRej() {
        return dataCzasRej;
    }

    public void setDataCzasRej(Date dataCzasRej) {
        this.dataCzasRej = dataCzasRej;
    }

    public Integer getRokDniDzial() {
        return rokDniDzial;
    }

    public void setRokDniDzial(Integer rokDniDzial) {
        this.rokDniDzial = rokDniDzial;
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
        if (!(object instanceof PlatnMdg)) {
            return false;
        }
        PlatnMdg other = (PlatnMdg) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnMdg[ id=" + id + " ]";
    }
    
}
