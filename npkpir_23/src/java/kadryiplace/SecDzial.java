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
@Table(name = "sec_dzial", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecDzial.findAll", query = "SELECT s FROM SecDzial s"),
    @NamedQuery(name = "SecDzial.findBySedSerial", query = "SELECT s FROM SecDzial s WHERE s.sedSerial = :sedSerial"),
    @NamedQuery(name = "SecDzial.findBySedUserid", query = "SELECT s FROM SecDzial s WHERE s.sedUserid = :sedUserid"),
    @NamedQuery(name = "SecDzial.findBySedFirSerial", query = "SELECT s FROM SecDzial s WHERE s.sedFirSerial = :sedFirSerial"),
    @NamedQuery(name = "SecDzial.findBySedPioSerial", query = "SELECT s FROM SecDzial s WHERE s.sedPioSerial = :sedPioSerial"),
    @NamedQuery(name = "SecDzial.findBySedDepSerial", query = "SELECT s FROM SecDzial s WHERE s.sedDepSerial = :sedDepSerial")})
public class SecDzial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sed_serial", nullable = false)
    private Integer sedSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "sed_userid", nullable = false, length = 32)
    private String sedUserid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sed_fir_serial", nullable = false)
    private int sedFirSerial;
    @Column(name = "sed_pio_serial")
    private Integer sedPioSerial;
    @Column(name = "sed_dep_serial")
    private Integer sedDepSerial;

    public SecDzial() {
    }

    public SecDzial(Integer sedSerial) {
        this.sedSerial = sedSerial;
    }

    public SecDzial(Integer sedSerial, String sedUserid, int sedFirSerial) {
        this.sedSerial = sedSerial;
        this.sedUserid = sedUserid;
        this.sedFirSerial = sedFirSerial;
    }

    public Integer getSedSerial() {
        return sedSerial;
    }

    public void setSedSerial(Integer sedSerial) {
        this.sedSerial = sedSerial;
    }

    public String getSedUserid() {
        return sedUserid;
    }

    public void setSedUserid(String sedUserid) {
        this.sedUserid = sedUserid;
    }

    public int getSedFirSerial() {
        return sedFirSerial;
    }

    public void setSedFirSerial(int sedFirSerial) {
        this.sedFirSerial = sedFirSerial;
    }

    public Integer getSedPioSerial() {
        return sedPioSerial;
    }

    public void setSedPioSerial(Integer sedPioSerial) {
        this.sedPioSerial = sedPioSerial;
    }

    public Integer getSedDepSerial() {
        return sedDepSerial;
    }

    public void setSedDepSerial(Integer sedDepSerial) {
        this.sedDepSerial = sedDepSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sedSerial != null ? sedSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecDzial)) {
            return false;
        }
        SecDzial other = (SecDzial) object;
        if ((this.sedSerial == null && other.sedSerial != null) || (this.sedSerial != null && !this.sedSerial.equals(other.sedSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SecDzial[ sedSerial=" + sedSerial + " ]";
    }
    
}
