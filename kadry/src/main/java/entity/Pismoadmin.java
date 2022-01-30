/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pismoadmin.findAll", query = "SELECT p FROM Pismoadmin p"),
    @NamedQuery(name = "Pismoadmin.findByLp", query = "SELECT p FROM Pismoadmin p WHERE p.lp = :lp"),
    @NamedQuery(name = "Pismoadmin.findByDatawiadomosci", query = "SELECT p FROM Pismoadmin p WHERE p.datawiadomosci = :datawiadomosci"),
    @NamedQuery(name = "Pismoadmin.findByNadawca", query = "SELECT p FROM Pismoadmin p WHERE p.nadawca = :nadawca"),
    @NamedQuery(name = "Pismoadmin.findByStatus", query = "SELECT p FROM Pismoadmin p WHERE p.status = :status"),
    @NamedQuery(name = "Pismoadmin.findByNOTStatus", query = "SELECT p FROM Pismoadmin p WHERE p.status != :status"),
    @NamedQuery(name = "Pismoadmin.findByDatastatus", query = "SELECT p FROM Pismoadmin p WHERE p.datastatus = :datastatus")})
public class Pismoadmin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer lp;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datawiadomosci;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(nullable = false, length = 65535)
    private String tresc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 20, max = 150)
    @Column(nullable = false, length = 150)
    private String nadawca;
    @Size(max = 150)
    @Column(length = 150)
    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datastatus;

    public Pismoadmin() {
    }

    public Pismoadmin(Integer lp) {
        this.lp = lp;
    }

    public Pismoadmin(Integer lp, Date datawiadomosci, String tresc, String nadawca) {
        this.lp = lp;
        this.datawiadomosci = datawiadomosci;
        this.tresc = tresc;
        this.nadawca = nadawca;
    }

    public Integer getLp() {
        return lp;
    }

    public void setLp(Integer lp) {
        this.lp = lp;
    }

    public Date getDatawiadomosci() {
        return datawiadomosci;
    }

    public void setDatawiadomosci(Date datawiadomosci) {
        this.datawiadomosci = datawiadomosci;
    }

    
    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getNadawca() {
        return nadawca;
    }

    public void setNadawca(String nadawca) {
        this.nadawca = nadawca;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatastatus() {
        return datastatus;
    }

    public void setDatastatus(Date datastatus) {
        this.datastatus = datastatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lp != null ? lp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pismoadmin)) {
            return false;
        }
        Pismoadmin other = (Pismoadmin) object;
        if ((this.lp == null && other.lp != null) || (this.lp != null && !this.lp.equals(other.lp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pismoadmin[ lp=" + lp + " ]";
    }
    
}
