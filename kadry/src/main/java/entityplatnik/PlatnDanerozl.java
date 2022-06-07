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
@Table(name = "PLATN_DANEROZL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnDanerozl.findAll", query = "SELECT p FROM PlatnDanerozl p"),
    @NamedQuery(name = "PlatnDanerozl.findById", query = "SELECT p FROM PlatnDanerozl p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnDanerozl.findByIdPlatnik", query = "SELECT p FROM PlatnDanerozl p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnDanerozl.findByNazwa", query = "SELECT p FROM PlatnDanerozl p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "PlatnDanerozl.findByWartosc", query = "SELECT p FROM PlatnDanerozl p WHERE p.wartosc = :wartosc"),
    @NamedQuery(name = "PlatnDanerozl.findByDataod", query = "SELECT p FROM PlatnDanerozl p WHERE p.dataod = :dataod"),
    @NamedQuery(name = "PlatnDanerozl.findByDatado", query = "SELECT p FROM PlatnDanerozl p WHERE p.datado = :datado"),
    @NamedQuery(name = "PlatnDanerozl.findByStatusDane", query = "SELECT p FROM PlatnDanerozl p WHERE p.statusDane = :statusDane"),
    @NamedQuery(name = "PlatnDanerozl.findByInserttmp", query = "SELECT p FROM PlatnDanerozl p WHERE p.inserttmp = :inserttmp"),
    @NamedQuery(name = "PlatnDanerozl.findByIdPlZus", query = "SELECT p FROM PlatnDanerozl p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnDanerozl.findByZakresNumerowDeklaracji", query = "SELECT p FROM PlatnDanerozl p WHERE p.zakresNumerowDeklaracji = :zakresNumerowDeklaracji"),
    @NamedQuery(name = "PlatnDanerozl.findByZrodlo", query = "SELECT p FROM PlatnDanerozl p WHERE p.zrodlo = :zrodlo")})
public class PlatnDanerozl implements Serializable {

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
    @Size(max = 16)
    @Column(name = "NAZWA", length = 16)
    private String nazwa;
    @Size(max = 8)
    @Column(name = "WARTOSC", length = 8)
    private String wartosc;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "DATADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datado;
    @Column(name = "STATUS_DANE")
    private Character statusDane;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ZAKRES_NUMEROW_DEKLARACJI")
    private Short zakresNumerowDeklaracji;
    @Column(name = "ZRODLO")
    private Character zrodlo;

    public PlatnDanerozl() {
    }

    public PlatnDanerozl(Integer id) {
        this.id = id;
    }

    public PlatnDanerozl(Integer id, int idPlatnik) {
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

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getWartosc() {
        return wartosc;
    }

    public void setWartosc(String wartosc) {
        this.wartosc = wartosc;
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

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Short getZakresNumerowDeklaracji() {
        return zakresNumerowDeklaracji;
    }

    public void setZakresNumerowDeklaracji(Short zakresNumerowDeklaracji) {
        this.zakresNumerowDeklaracji = zakresNumerowDeklaracji;
    }

    public Character getZrodlo() {
        return zrodlo;
    }

    public void setZrodlo(Character zrodlo) {
        this.zrodlo = zrodlo;
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
        if (!(object instanceof PlatnDanerozl)) {
            return false;
        }
        PlatnDanerozl other = (PlatnDanerozl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnDanerozl[ id=" + id + " ]";
    }
    
}
