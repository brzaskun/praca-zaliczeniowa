/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "odsetki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Odsetki.findAll", query = "SELECT o FROM Odsetki o"),
    @NamedQuery(name = "Odsetki.findById", query = "SELECT o FROM Odsetki o WHERE o.id = :id"),
    @NamedQuery(name = "Odsetki.findByDatado", query = "SELECT o FROM Odsetki o WHERE o.datado = :datado"),
    @NamedQuery(name = "Odsetki.findByDatadoD", query = "SELECT o FROM Odsetki o WHERE o.datadoD = :datadoD"),
    @NamedQuery(name = "Odsetki.findByDataod", query = "SELECT o FROM Odsetki o WHERE o.dataod = :dataod"),
    @NamedQuery(name = "Odsetki.findByDataodD", query = "SELECT o FROM Odsetki o WHERE o.dataodD = :dataodD"),
    @NamedQuery(name = "Odsetki.findByStopaodsetek", query = "SELECT o FROM Odsetki o WHERE o.stopaodsetek = :stopaodsetek")})
public class Odsetki implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "datado")
    private String datado;
    @Column(name = "datadoD")
    @Temporal(TemporalType.DATE)
    private Date datadoD;
    @Size(max = 255)
    @Column(name = "dataod")
    private String dataod;
    @Column(name = "dataodD")
    @Temporal(TemporalType.DATE)
    private Date dataodD;
    @Size(max = 255)
    @Column(name = "stopaodsetek")
    private String stopaodsetek;

    public Odsetki() {
    }

    public Odsetki(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public Date getDatadoD() {
        return datadoD;
    }

    public void setDatadoD(Date datadoD) {
        this.datadoD = datadoD;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public Date getDataodD() {
        return dataodD;
    }

    public void setDataodD(Date dataodD) {
        this.dataodD = dataodD;
    }

    public String getStopaodsetek() {
        return stopaodsetek;
    }

    public void setStopaodsetek(String stopaodsetek) {
        this.stopaodsetek = stopaodsetek;
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
        if (!(object instanceof Odsetki)) {
            return false;
        }
        Odsetki other = (Odsetki) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Odsetki[ id=" + id + " ]";
    }
    
}
