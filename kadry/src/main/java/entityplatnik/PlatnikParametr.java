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
@Table(name = "PLATNIK_PARAMETR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnikParametr.findAll", query = "SELECT p FROM PlatnikParametr p"),
    @NamedQuery(name = "PlatnikParametr.findById", query = "SELECT p FROM PlatnikParametr p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnikParametr.findByIdNadrzedny", query = "SELECT p FROM PlatnikParametr p WHERE p.idNadrzedny = :idNadrzedny"),
    @NamedQuery(name = "PlatnikParametr.findByIdPlatnik", query = "SELECT p FROM PlatnikParametr p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnikParametr.findByNazwa", query = "SELECT p FROM PlatnikParametr p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "PlatnikParametr.findByWartosc", query = "SELECT p FROM PlatnikParametr p WHERE p.wartosc = :wartosc"),
    @NamedQuery(name = "PlatnikParametr.findByDataod", query = "SELECT p FROM PlatnikParametr p WHERE p.dataod = :dataod"),
    @NamedQuery(name = "PlatnikParametr.findByDatado", query = "SELECT p FROM PlatnikParametr p WHERE p.datado = :datado"),
    @NamedQuery(name = "PlatnikParametr.findByInserttmp", query = "SELECT p FROM PlatnikParametr p WHERE p.inserttmp = :inserttmp")})
public class PlatnikParametr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_NADRZEDNY")
    private Integer idNadrzedny;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Size(max = 32)
    @Column(name = "NAZWA", length = 32)
    private String nazwa;
    @Size(max = 20)
    @Column(name = "WARTOSC", length = 20)
    private String wartosc;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "DATADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datado;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlatnikParametr() {
    }

    public PlatnikParametr(Integer id) {
        this.id = id;
    }

    public PlatnikParametr(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdNadrzedny() {
        return idNadrzedny;
    }

    public void setIdNadrzedny(Integer idNadrzedny) {
        this.idNadrzedny = idNadrzedny;
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
        if (!(object instanceof PlatnikParametr)) {
            return false;
        }
        PlatnikParametr other = (PlatnikParametr) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnikParametr[ id=" + id + " ]";
    }
    
}
