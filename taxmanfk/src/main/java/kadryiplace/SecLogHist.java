/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "sec_log_hist", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SecLogHist.findAll", query = "SELECT s FROM SecLogHist s"),
    @NamedQuery(name = "SecLogHist.findByActSerial", query = "SELECT s FROM SecLogHist s WHERE s.actSerial = :actSerial"),
    @NamedQuery(name = "SecLogHist.findByActDatetime", query = "SELECT s FROM SecLogHist s WHERE s.actDatetime = :actDatetime"),
    @NamedQuery(name = "SecLogHist.findByActUserid", query = "SELECT s FROM SecLogHist s WHERE s.actUserid = :actUserid"),
    @NamedQuery(name = "SecLogHist.findByActTable", query = "SELECT s FROM SecLogHist s WHERE s.actTable = :actTable"),
    @NamedQuery(name = "SecLogHist.findByActObject", query = "SELECT s FROM SecLogHist s WHERE s.actObject = :actObject"),
    @NamedQuery(name = "SecLogHist.findByActKeyCols", query = "SELECT s FROM SecLogHist s WHERE s.actKeyCols = :actKeyCols"),
    @NamedQuery(name = "SecLogHist.findByActKeyValues", query = "SELECT s FROM SecLogHist s WHERE s.actKeyValues = :actKeyValues"),
    @NamedQuery(name = "SecLogHist.findByActUpdateCols", query = "SELECT s FROM SecLogHist s WHERE s.actUpdateCols = :actUpdateCols"),
    @NamedQuery(name = "SecLogHist.findByActOldValues", query = "SELECT s FROM SecLogHist s WHERE s.actOldValues = :actOldValues"),
    @NamedQuery(name = "SecLogHist.findByActNewValues", query = "SELECT s FROM SecLogHist s WHERE s.actNewValues = :actNewValues")})
public class SecLogHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "act_serial", nullable = false)
    private Integer actSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "act_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date actDatetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "act_userid", nullable = false, length = 32)
    private String actUserid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "act_table", nullable = false, length = 32)
    private String actTable;
    @Size(max = 64)
    @Column(name = "act_object", length = 64)
    private String actObject;
    @Size(max = 254)
    @Column(name = "act_key_cols", length = 254)
    private String actKeyCols;
    @Size(max = 254)
    @Column(name = "act_key_values", length = 254)
    private String actKeyValues;
    @Size(max = 254)
    @Column(name = "act_update_cols", length = 254)
    private String actUpdateCols;
    @Size(max = 254)
    @Column(name = "act_old_values", length = 254)
    private String actOldValues;
    @Size(max = 254)
    @Column(name = "act_new_values", length = 254)
    private String actNewValues;

    public SecLogHist() {
    }

    public SecLogHist(Integer actSerial) {
        this.actSerial = actSerial;
    }

    public SecLogHist(Integer actSerial, Date actDatetime, String actUserid, String actTable) {
        this.actSerial = actSerial;
        this.actDatetime = actDatetime;
        this.actUserid = actUserid;
        this.actTable = actTable;
    }

    public Integer getActSerial() {
        return actSerial;
    }

    public void setActSerial(Integer actSerial) {
        this.actSerial = actSerial;
    }

    public Date getActDatetime() {
        return actDatetime;
    }

    public void setActDatetime(Date actDatetime) {
        this.actDatetime = actDatetime;
    }

    public String getActUserid() {
        return actUserid;
    }

    public void setActUserid(String actUserid) {
        this.actUserid = actUserid;
    }

    public String getActTable() {
        return actTable;
    }

    public void setActTable(String actTable) {
        this.actTable = actTable;
    }

    public String getActObject() {
        return actObject;
    }

    public void setActObject(String actObject) {
        this.actObject = actObject;
    }

    public String getActKeyCols() {
        return actKeyCols;
    }

    public void setActKeyCols(String actKeyCols) {
        this.actKeyCols = actKeyCols;
    }

    public String getActKeyValues() {
        return actKeyValues;
    }

    public void setActKeyValues(String actKeyValues) {
        this.actKeyValues = actKeyValues;
    }

    public String getActUpdateCols() {
        return actUpdateCols;
    }

    public void setActUpdateCols(String actUpdateCols) {
        this.actUpdateCols = actUpdateCols;
    }

    public String getActOldValues() {
        return actOldValues;
    }

    public void setActOldValues(String actOldValues) {
        this.actOldValues = actOldValues;
    }

    public String getActNewValues() {
        return actNewValues;
    }

    public void setActNewValues(String actNewValues) {
        this.actNewValues = actNewValues;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actSerial != null ? actSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecLogHist)) {
            return false;
        }
        SecLogHist other = (SecLogHist) object;
        if ((this.actSerial == null && other.actSerial != null) || (this.actSerial != null && !this.actSerial.equals(other.actSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.SecLogHist[ actSerial=" + actSerial + " ]";
    }
    
}
