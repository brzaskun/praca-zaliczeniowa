/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "sec_group", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecGroup.findAll", query = "SELECT s FROM SecGroup s"),
    @NamedQuery(name = "SecGroup.findByGrpSerial", query = "SELECT s FROM SecGroup s WHERE s.grpSerial = :grpSerial"),
    @NamedQuery(name = "SecGroup.findByGrpGroup", query = "SELECT s FROM SecGroup s WHERE s.grpGroup = :grpGroup")})
public class SecGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "grp_serial", nullable = false)
    private Integer grpSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "grp_group", nullable = false, length = 64)
    private String grpGroup;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrGrpSerial")
    private List<SecUser> secUserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mnuGrpSerial")
    private List<SecMenu> secMenuList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rpsGrpSerial")
    private List<RapoSec> rapoSecList;

    public SecGroup() {
    }

    public SecGroup(Integer grpSerial) {
        this.grpSerial = grpSerial;
    }

    public SecGroup(Integer grpSerial, String grpGroup) {
        this.grpSerial = grpSerial;
        this.grpGroup = grpGroup;
    }

    public Integer getGrpSerial() {
        return grpSerial;
    }

    public void setGrpSerial(Integer grpSerial) {
        this.grpSerial = grpSerial;
    }

    public String getGrpGroup() {
        return grpGroup;
    }

    public void setGrpGroup(String grpGroup) {
        this.grpGroup = grpGroup;
    }

    @XmlTransient
    public List<SecUser> getSecUserList() {
        return secUserList;
    }

    public void setSecUserList(List<SecUser> secUserList) {
        this.secUserList = secUserList;
    }

    @XmlTransient
    public List<SecMenu> getSecMenuList() {
        return secMenuList;
    }

    public void setSecMenuList(List<SecMenu> secMenuList) {
        this.secMenuList = secMenuList;
    }

    @XmlTransient
    public List<RapoSec> getRapoSecList() {
        return rapoSecList;
    }

    public void setRapoSecList(List<RapoSec> rapoSecList) {
        this.rapoSecList = rapoSecList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grpSerial != null ? grpSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecGroup)) {
            return false;
        }
        SecGroup other = (SecGroup) object;
        if ((this.grpSerial == null && other.grpSerial != null) || (this.grpSerial != null && !this.grpSerial.equals(other.grpSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SecGroup[ grpSerial=" + grpSerial + " ]";
    }
    
}
