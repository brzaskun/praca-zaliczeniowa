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
@Table(name = "sec_menu", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecMenu.findAll", query = "SELECT s FROM SecMenu s"),
    @NamedQuery(name = "SecMenu.findByMnuSerial", query = "SELECT s FROM SecMenu s WHERE s.mnuSerial = :mnuSerial"),
    @NamedQuery(name = "SecMenu.findByMnuLevel1", query = "SELECT s FROM SecMenu s WHERE s.mnuLevel1 = :mnuLevel1"),
    @NamedQuery(name = "SecMenu.findByMnuLevel2", query = "SELECT s FROM SecMenu s WHERE s.mnuLevel2 = :mnuLevel2"),
    @NamedQuery(name = "SecMenu.findByMnuLevel3", query = "SELECT s FROM SecMenu s WHERE s.mnuLevel3 = :mnuLevel3"),
    @NamedQuery(name = "SecMenu.findByMnuLevel4", query = "SELECT s FROM SecMenu s WHERE s.mnuLevel4 = :mnuLevel4"),
    @NamedQuery(name = "SecMenu.findByMnuLevel5", query = "SELECT s FROM SecMenu s WHERE s.mnuLevel5 = :mnuLevel5"),
    @NamedQuery(name = "SecMenu.findByMnuLevel6", query = "SELECT s FROM SecMenu s WHERE s.mnuLevel6 = :mnuLevel6")})
public class SecMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mnu_serial", nullable = false)
    private Integer mnuSerial;
    @Size(max = 64)
    @Column(name = "mnu_level_1", length = 64)
    private String mnuLevel1;
    @Size(max = 64)
    @Column(name = "mnu_level_2", length = 64)
    private String mnuLevel2;
    @Size(max = 64)
    @Column(name = "mnu_level_3", length = 64)
    private String mnuLevel3;
    @Size(max = 64)
    @Column(name = "mnu_level_4", length = 64)
    private String mnuLevel4;
    @Size(max = 64)
    @Column(name = "mnu_level_5", length = 64)
    private String mnuLevel5;
    @Size(max = 64)
    @Column(name = "mnu_level_6", length = 64)
    private String mnuLevel6;
    @JoinColumn(name = "mnu_grp_serial", referencedColumnName = "grp_serial", nullable = false)
    @ManyToOne(optional = false)
    private SecGroup mnuGrpSerial;
    @JoinColumn(name = "mnu_win_serial", referencedColumnName = "win_serial", nullable = false)
    @ManyToOne(optional = false)
    private SecWindow mnuWinSerial;

    public SecMenu() {
    }

    public SecMenu(Integer mnuSerial) {
        this.mnuSerial = mnuSerial;
    }

    public Integer getMnuSerial() {
        return mnuSerial;
    }

    public void setMnuSerial(Integer mnuSerial) {
        this.mnuSerial = mnuSerial;
    }

    public String getMnuLevel1() {
        return mnuLevel1;
    }

    public void setMnuLevel1(String mnuLevel1) {
        this.mnuLevel1 = mnuLevel1;
    }

    public String getMnuLevel2() {
        return mnuLevel2;
    }

    public void setMnuLevel2(String mnuLevel2) {
        this.mnuLevel2 = mnuLevel2;
    }

    public String getMnuLevel3() {
        return mnuLevel3;
    }

    public void setMnuLevel3(String mnuLevel3) {
        this.mnuLevel3 = mnuLevel3;
    }

    public String getMnuLevel4() {
        return mnuLevel4;
    }

    public void setMnuLevel4(String mnuLevel4) {
        this.mnuLevel4 = mnuLevel4;
    }

    public String getMnuLevel5() {
        return mnuLevel5;
    }

    public void setMnuLevel5(String mnuLevel5) {
        this.mnuLevel5 = mnuLevel5;
    }

    public String getMnuLevel6() {
        return mnuLevel6;
    }

    public void setMnuLevel6(String mnuLevel6) {
        this.mnuLevel6 = mnuLevel6;
    }

    public SecGroup getMnuGrpSerial() {
        return mnuGrpSerial;
    }

    public void setMnuGrpSerial(SecGroup mnuGrpSerial) {
        this.mnuGrpSerial = mnuGrpSerial;
    }

    public SecWindow getMnuWinSerial() {
        return mnuWinSerial;
    }

    public void setMnuWinSerial(SecWindow mnuWinSerial) {
        this.mnuWinSerial = mnuWinSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mnuSerial != null ? mnuSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecMenu)) {
            return false;
        }
        SecMenu other = (SecMenu) object;
        if ((this.mnuSerial == null && other.mnuSerial != null) || (this.mnuSerial != null && !this.mnuSerial.equals(other.mnuSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SecMenu[ mnuSerial=" + mnuSerial + " ]";
    }
    
}
