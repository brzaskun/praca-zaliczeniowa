/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Embeddable
public class DtpropertiesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private int id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "property", nullable = false, length = 64)
    private String property;

    public DtpropertiesPK() {
    }

    public DtpropertiesPK(int id, String property) {
        this.id = id;
        this.property = property;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (property != null ? property.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DtpropertiesPK)) {
            return false;
        }
        DtpropertiesPK other = (DtpropertiesPK) object;
        if (this.id != other.id) {
            return false;
        }
        if ((this.property == null && other.property != null) || (this.property != null && !this.property.equals(other.property))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DtpropertiesPK[ id=" + id + ", property=" + property + " ]";
    }
    
}
