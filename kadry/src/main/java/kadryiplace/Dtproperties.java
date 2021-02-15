/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "dtproperties", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dtproperties.findAll", query = "SELECT d FROM Dtproperties d"),
    @NamedQuery(name = "Dtproperties.findById", query = "SELECT d FROM Dtproperties d WHERE d.dtpropertiesPK.id = :id"),
    @NamedQuery(name = "Dtproperties.findByObjectid", query = "SELECT d FROM Dtproperties d WHERE d.objectid = :objectid"),
    @NamedQuery(name = "Dtproperties.findByProperty", query = "SELECT d FROM Dtproperties d WHERE d.dtpropertiesPK.property = :property"),
    @NamedQuery(name = "Dtproperties.findByValue", query = "SELECT d FROM Dtproperties d WHERE d.value = :value"),
    @NamedQuery(name = "Dtproperties.findByUvalue", query = "SELECT d FROM Dtproperties d WHERE d.uvalue = :uvalue"),
    @NamedQuery(name = "Dtproperties.findByVersion", query = "SELECT d FROM Dtproperties d WHERE d.version = :version")})
public class Dtproperties implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DtpropertiesPK dtpropertiesPK;
    @Column(name = "objectid")
    private Integer objectid;
    @Size(max = 255)
    @Column(name = "value", length = 255)
    private String value;
    @Size(max = 255)
    @Column(name = "uvalue", length = 255)
    private String uvalue;
    @Lob
    @Column(name = "lvalue")
    private byte[] lvalue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version", nullable = false)
    private int version;

    public Dtproperties() {
    }

    public Dtproperties(DtpropertiesPK dtpropertiesPK) {
        this.dtpropertiesPK = dtpropertiesPK;
    }

    public Dtproperties(DtpropertiesPK dtpropertiesPK, int version) {
        this.dtpropertiesPK = dtpropertiesPK;
        this.version = version;
    }

    public Dtproperties(int id, String property) {
        this.dtpropertiesPK = new DtpropertiesPK(id, property);
    }

    public DtpropertiesPK getDtpropertiesPK() {
        return dtpropertiesPK;
    }

    public void setDtpropertiesPK(DtpropertiesPK dtpropertiesPK) {
        this.dtpropertiesPK = dtpropertiesPK;
    }

    public Integer getObjectid() {
        return objectid;
    }

    public void setObjectid(Integer objectid) {
        this.objectid = objectid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUvalue() {
        return uvalue;
    }

    public void setUvalue(String uvalue) {
        this.uvalue = uvalue;
    }

    public byte[] getLvalue() {
        return lvalue;
    }

    public void setLvalue(byte[] lvalue) {
        this.lvalue = lvalue;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dtpropertiesPK != null ? dtpropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dtproperties)) {
            return false;
        }
        Dtproperties other = (Dtproperties) object;
        if ((this.dtpropertiesPK == null && other.dtpropertiesPK != null) || (this.dtpropertiesPK != null && !this.dtpropertiesPK.equals(other.dtpropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Dtproperties[ dtpropertiesPK=" + dtpropertiesPK + " ]";
    }
    
}
