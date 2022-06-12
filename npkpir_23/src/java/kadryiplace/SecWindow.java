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
@Table(name = "sec_window", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecWindow.findAll", query = "SELECT s FROM SecWindow s"),
    @NamedQuery(name = "SecWindow.findByWinSerial", query = "SELECT s FROM SecWindow s WHERE s.winSerial = :winSerial"),
    @NamedQuery(name = "SecWindow.findByWinWindow", query = "SELECT s FROM SecWindow s WHERE s.winWindow = :winWindow"),
    @NamedQuery(name = "SecWindow.findByWinTitle", query = "SELECT s FROM SecWindow s WHERE s.winTitle = :winTitle")})
public class SecWindow implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "win_serial", nullable = false)
    private Integer winSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "win_window", nullable = false, length = 64)
    private String winWindow;
    @Size(max = 64)
    @Column(name = "win_title", length = 64)
    private String winTitle;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mnuWinSerial")
    private List<SecMenu> secMenuList;

    public SecWindow() {
    }

    public SecWindow(Integer winSerial) {
        this.winSerial = winSerial;
    }

    public SecWindow(Integer winSerial, String winWindow) {
        this.winSerial = winSerial;
        this.winWindow = winWindow;
    }

    public Integer getWinSerial() {
        return winSerial;
    }

    public void setWinSerial(Integer winSerial) {
        this.winSerial = winSerial;
    }

    public String getWinWindow() {
        return winWindow;
    }

    public void setWinWindow(String winWindow) {
        this.winWindow = winWindow;
    }

    public String getWinTitle() {
        return winTitle;
    }

    public void setWinTitle(String winTitle) {
        this.winTitle = winTitle;
    }

    @XmlTransient
    public List<SecMenu> getSecMenuList() {
        return secMenuList;
    }

    public void setSecMenuList(List<SecMenu> secMenuList) {
        this.secMenuList = secMenuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (winSerial != null ? winSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecWindow)) {
            return false;
        }
        SecWindow other = (SecWindow) object;
        if ((this.winSerial == null && other.winSerial != null) || (this.winSerial != null && !this.winSerial.equals(other.winSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SecWindow[ winSerial=" + winSerial + " ]";
    }
    
}
