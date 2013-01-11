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
@Table(name = "rezerwacja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rezerwacja.findAll", query = "SELECT r FROM Rezerwacja r"),
    @NamedQuery(name = "Rezerwacja.findById", query = "SELECT r FROM Rezerwacja r WHERE r.id = :id"),
    @NamedQuery(name = "Rezerwacja.findByDataod", query = "SELECT r FROM Rezerwacja r WHERE r.dataod = :dataod"),
    @NamedQuery(name = "Rezerwacja.findByDatado", query = "SELECT r FROM Rezerwacja r WHERE r.datado = :datado"),
    @NamedQuery(name = "Rezerwacja.findByIdpokoju", query = "SELECT r FROM Rezerwacja r WHERE r.idpokoju = :idpokoju"),
    @NamedQuery(name = "Rezerwacja.findByKoszt", query = "SELECT r FROM Rezerwacja r WHERE r.koszt = :koszt"),
    @NamedQuery(name = "Rezerwacja.findByIdklienta", query = "SELECT r FROM Rezerwacja r WHERE r.idklienta = :idklienta")})
public class Rezerwacja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataod")
    @Temporal(TemporalType.DATE)
    private Date dataod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datado")
    @Temporal(TemporalType.DATE)
    private Date datado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idpokoju")
    private int idpokoju;
    @Basic(optional = false)
    @NotNull
    @Column(name = "koszt")
    private float koszt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idklienta")
    private int idklienta;

    public Rezerwacja() {
    }

    public Rezerwacja(Integer id) {
        this.id = id;
    }

    public Rezerwacja(Integer id, Date dataod, Date datado, int idpokoju, float koszt, int idklienta) {
        this.id = id;
        this.dataod = dataod;
        this.datado = datado;
        this.idpokoju = idpokoju;
        this.koszt = koszt;
        this.idklienta = idklienta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getIdpokoju() {
        return idpokoju;
    }

    public void setIdpokoju(int idpokoju) {
        this.idpokoju = idpokoju;
    }

    public float getKoszt() {
        return koszt;
    }

    public void setKoszt(float koszt) {
        this.koszt = koszt;
    }

    public int getIdklienta() {
        return idklienta;
    }

    public void setIdklienta(int idklienta) {
        this.idklienta = idklienta;
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
        if (!(object instanceof Rezerwacja)) {
            return false;
        }
        Rezerwacja other = (Rezerwacja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rezerwacja[ id=" + id + " ]";
    }
    
}
