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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "dzial_list", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DzialList.findAll", query = "SELECT d FROM DzialList d"),
    @NamedQuery(name = "DzialList.findByDzlSerial", query = "SELECT d FROM DzialList d WHERE d.dzlSerial = :dzlSerial"),
    @NamedQuery(name = "DzialList.findByDzlOsoSerial", query = "SELECT d FROM DzialList d WHERE d.dzlOsoSerial = :dzlOsoSerial"),
    @NamedQuery(name = "DzialList.findByDzlDepSerial", query = "SELECT d FROM DzialList d WHERE d.dzlDepSerial = :dzlDepSerial")})
public class DzialList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dzl_serial", nullable = false)
    private Integer dzlSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dzl_oso_serial", nullable = false)
    private int dzlOsoSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dzl_dep_serial", nullable = false)
    private int dzlDepSerial;

    public DzialList() {
    }

    public DzialList(Integer dzlSerial) {
        this.dzlSerial = dzlSerial;
    }

    public DzialList(Integer dzlSerial, int dzlOsoSerial, int dzlDepSerial) {
        this.dzlSerial = dzlSerial;
        this.dzlOsoSerial = dzlOsoSerial;
        this.dzlDepSerial = dzlDepSerial;
    }

    public Integer getDzlSerial() {
        return dzlSerial;
    }

    public void setDzlSerial(Integer dzlSerial) {
        this.dzlSerial = dzlSerial;
    }

    public int getDzlOsoSerial() {
        return dzlOsoSerial;
    }

    public void setDzlOsoSerial(int dzlOsoSerial) {
        this.dzlOsoSerial = dzlOsoSerial;
    }

    public int getDzlDepSerial() {
        return dzlDepSerial;
    }

    public void setDzlDepSerial(int dzlDepSerial) {
        this.dzlDepSerial = dzlDepSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dzlSerial != null ? dzlSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DzialList)) {
            return false;
        }
        DzialList other = (DzialList) object;
        if ((this.dzlSerial == null && other.dzlSerial != null) || (this.dzlSerial != null && !this.dzlSerial.equals(other.dzlSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DzialList[ dzlSerial=" + dzlSerial + " ]";
    }
    
}
