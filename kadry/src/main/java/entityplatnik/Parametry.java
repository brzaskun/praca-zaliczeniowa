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
@Table(name = "PARAMETRY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametry.findAll", query = "SELECT p FROM Parametry p"),
    @NamedQuery(name = "Parametry.findById", query = "SELECT p FROM Parametry p WHERE p.id = :id"),
    @NamedQuery(name = "Parametry.findByNazwa", query = "SELECT p FROM Parametry p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "Parametry.findByWartosc", query = "SELECT p FROM Parametry p WHERE p.wartosc = :wartosc"),
    @NamedQuery(name = "Parametry.findByDataod", query = "SELECT p FROM Parametry p WHERE p.dataod = :dataod"),
    @NamedQuery(name = "Parametry.findByDatado", query = "SELECT p FROM Parametry p WHERE p.datado = :datado")})
public class Parametry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Size(max = 25)
    @Column(name = "NAZWA", length = 25)
    private String nazwa;
    @Size(max = 255)
    @Column(name = "WARTOSC", length = 255)
    private String wartosc;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "DATADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datado;

    public Parametry() {
    }

    public Parametry(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametry)) {
            return false;
        }
        Parametry other = (Parametry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Parametry[ id=" + id + " ]";
    }
    
}
