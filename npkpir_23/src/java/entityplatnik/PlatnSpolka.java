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
@Table(name = "PLATN_SPOLKA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnSpolka.findAll", query = "SELECT p FROM PlatnSpolka p"),
    @NamedQuery(name = "PlatnSpolka.findById", query = "SELECT p FROM PlatnSpolka p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnSpolka.findByIdPlatnik", query = "SELECT p FROM PlatnSpolka p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnSpolka.findByIdPlZus", query = "SELECT p FROM PlatnSpolka p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnSpolka.findByIdUbZus", query = "SELECT p FROM PlatnSpolka p WHERE p.idUbZus = :idUbZus"),
    @NamedQuery(name = "PlatnSpolka.findByNazwaskr", query = "SELECT p FROM PlatnSpolka p WHERE p.nazwaskr = :nazwaskr"),
    @NamedQuery(name = "PlatnSpolka.findByNip", query = "SELECT p FROM PlatnSpolka p WHERE p.nip = :nip"),
    @NamedQuery(name = "PlatnSpolka.findByRegon", query = "SELECT p FROM PlatnSpolka p WHERE p.regon = :regon"),
    @NamedQuery(name = "PlatnSpolka.findByDatawpisu", query = "SELECT p FROM PlatnSpolka p WHERE p.datawpisu = :datawpisu"),
    @NamedQuery(name = "PlatnSpolka.findByDataod", query = "SELECT p FROM PlatnSpolka p WHERE p.dataod = :dataod"),
    @NamedQuery(name = "PlatnSpolka.findByDatado", query = "SELECT p FROM PlatnSpolka p WHERE p.datado = :datado"),
    @NamedQuery(name = "PlatnSpolka.findByInserttmp", query = "SELECT p FROM PlatnSpolka p WHERE p.inserttmp = :inserttmp")})
public class PlatnSpolka implements Serializable {

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
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Size(max = 70)
    @Column(name = "NAZWASKR", length = 70)
    private String nazwaskr;
    @Size(max = 10)
    @Column(name = "NIP", length = 10)
    private String nip;
    @Size(max = 14)
    @Column(name = "REGON", length = 14)
    private String regon;
    @Column(name = "DATAWPISU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datawpisu;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "DATADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datado;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnSpolka() {
    }

    public PlatnSpolka(Integer id) {
        this.id = id;
    }

    public PlatnSpolka(Integer id, int idPlatnik) {
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

    public Integer getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(Integer idUbZus) {
        this.idUbZus = idUbZus;
    }

    public String getNazwaskr() {
        return nazwaskr;
    }

    public void setNazwaskr(String nazwaskr) {
        this.nazwaskr = nazwaskr;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public Date getDatawpisu() {
        return datawpisu;
    }

    public void setDatawpisu(Date datawpisu) {
        this.datawpisu = datawpisu;
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
        if (!(object instanceof PlatnSpolka)) {
            return false;
        }
        PlatnSpolka other = (PlatnSpolka) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnSpolka[ id=" + id + " ]";
    }
    
}
