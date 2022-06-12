/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "sec_menu_item", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecMenuItem.findAll", query = "SELECT s FROM SecMenuItem s"),
    @NamedQuery(name = "SecMenuItem.findByItmItem", query = "SELECT s FROM SecMenuItem s WHERE s.itmItem = :itmItem"),
    @NamedQuery(name = "SecMenuItem.findByItmName", query = "SELECT s FROM SecMenuItem s WHERE s.itmName = :itmName")})
public class SecMenuItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "itm_item", nullable = false, length = 64)
    private String itmItem;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "itm_name", nullable = false, length = 64)
    private String itmName;

    public SecMenuItem() {
    }

    public SecMenuItem(String itmItem) {
        this.itmItem = itmItem;
    }

    public SecMenuItem(String itmItem, String itmName) {
        this.itmItem = itmItem;
        this.itmName = itmName;
    }

    public String getItmItem() {
        return itmItem;
    }

    public void setItmItem(String itmItem) {
        this.itmItem = itmItem;
    }

    public String getItmName() {
        return itmName;
    }

    public void setItmName(String itmName) {
        this.itmName = itmName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itmItem != null ? itmItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecMenuItem)) {
            return false;
        }
        SecMenuItem other = (SecMenuItem) object;
        if ((this.itmItem == null && other.itmItem != null) || (this.itmItem != null && !this.itmItem.equals(other.itmItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SecMenuItem[ itmItem=" + itmItem + " ]";
    }
    
}
