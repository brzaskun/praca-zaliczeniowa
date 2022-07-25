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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tmp_dzial", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpDzial.findAll", query = "SELECT t FROM TmpDzial t"),
    @NamedQuery(name = "TmpDzial.findByTmpSerial", query = "SELECT t FROM TmpDzial t WHERE t.tmpSerial = :tmpSerial"),
    @NamedQuery(name = "TmpDzial.findByTmpUserid", query = "SELECT t FROM TmpDzial t WHERE t.tmpUserid = :tmpUserid"),
    @NamedQuery(name = "TmpDzial.findByTmpSelected", query = "SELECT t FROM TmpDzial t WHERE t.tmpSelected = :tmpSelected")})
public class TmpDzial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tmp_serial", nullable = false)
    private Integer tmpSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "tmp_userid", nullable = false, length = 32)
    private String tmpUserid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tmp_selected", nullable = false)
    private Character tmpSelected;
    @JoinColumn(name = "tmp_dep_serial", referencedColumnName = "dep_serial", nullable = false)
    @ManyToOne(optional = false)
    private Dep tmpDepSerial;

    public TmpDzial() {
    }

    public TmpDzial(Integer tmpSerial) {
        this.tmpSerial = tmpSerial;
    }

    public TmpDzial(Integer tmpSerial, String tmpUserid, Character tmpSelected) {
        this.tmpSerial = tmpSerial;
        this.tmpUserid = tmpUserid;
        this.tmpSelected = tmpSelected;
    }

    public Integer getTmpSerial() {
        return tmpSerial;
    }

    public void setTmpSerial(Integer tmpSerial) {
        this.tmpSerial = tmpSerial;
    }

    public String getTmpUserid() {
        return tmpUserid;
    }

    public void setTmpUserid(String tmpUserid) {
        this.tmpUserid = tmpUserid;
    }

    public Character getTmpSelected() {
        return tmpSelected;
    }

    public void setTmpSelected(Character tmpSelected) {
        this.tmpSelected = tmpSelected;
    }

    public Dep getTmpDepSerial() {
        return tmpDepSerial;
    }

    public void setTmpDepSerial(Dep tmpDepSerial) {
        this.tmpDepSerial = tmpDepSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tmpSerial != null ? tmpSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TmpDzial)) {
            return false;
        }
        TmpDzial other = (TmpDzial) object;
        if ((this.tmpSerial == null && other.tmpSerial != null) || (this.tmpSerial != null && !this.tmpSerial.equals(other.tmpSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.TmpDzial[ tmpSerial=" + tmpSerial + " ]";
    }
    
}
