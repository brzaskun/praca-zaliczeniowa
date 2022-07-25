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
@Table(name = "PLATN_RACHBANK_NRS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnRachbankNrs.findAll", query = "SELECT p FROM PlatnRachbankNrs p"),
    @NamedQuery(name = "PlatnRachbankNrs.findById", query = "SELECT p FROM PlatnRachbankNrs p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnRachbankNrs.findByIdPlatnik", query = "SELECT p FROM PlatnRachbankNrs p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnRachbankNrs.findByNrRachBank", query = "SELECT p FROM PlatnRachbankNrs p WHERE p.nrRachBank = :nrRachBank"),
    @NamedQuery(name = "PlatnRachbankNrs.findByDataod", query = "SELECT p FROM PlatnRachbankNrs p WHERE p.dataod = :dataod"),
    @NamedQuery(name = "PlatnRachbankNrs.findByStatusDane", query = "SELECT p FROM PlatnRachbankNrs p WHERE p.statusDane = :statusDane"),
    @NamedQuery(name = "PlatnRachbankNrs.findByInserttmp", query = "SELECT p FROM PlatnRachbankNrs p WHERE p.inserttmp = :inserttmp"),
    @NamedQuery(name = "PlatnRachbankNrs.findByIdPlZus", query = "SELECT p FROM PlatnRachbankNrs p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnRachbankNrs.findByRola", query = "SELECT p FROM PlatnRachbankNrs p WHERE p.rola = :rola"),
    @NamedQuery(name = "PlatnRachbankNrs.findByZakres", query = "SELECT p FROM PlatnRachbankNrs p WHERE p.zakres = :zakres")})
public class PlatnRachbankNrs implements Serializable {

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
    @Size(max = 36)
    @Column(name = "NR_RACH_BANK", length = 36)
    private String nrRachBank;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "STATUS_DANE")
    private Character statusDane;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ROLA")
    private Character rola;
    @Column(name = "ZAKRES")
    private Integer zakres;

    public PlatnRachbankNrs() {
    }

    public PlatnRachbankNrs(Integer id) {
        this.id = id;
    }

    public PlatnRachbankNrs(Integer id, int idPlatnik) {
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

    public String getNrRachBank() {
        return nrRachBank;
    }

    public void setNrRachBank(String nrRachBank) {
        this.nrRachBank = nrRachBank;
    }

    public Date getDataod() {
        return dataod;
    }

    public void setDataod(Date dataod) {
        this.dataod = dataod;
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

    public Character getRola() {
        return rola;
    }

    public void setRola(Character rola) {
        this.rola = rola;
    }

    public Integer getZakres() {
        return zakres;
    }

    public void setZakres(Integer zakres) {
        this.zakres = zakres;
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
        if (!(object instanceof PlatnRachbankNrs)) {
            return false;
        }
        PlatnRachbankNrs other = (PlatnRachbankNrs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnRachbankNrs[ id=" + id + " ]";
    }
    
}
