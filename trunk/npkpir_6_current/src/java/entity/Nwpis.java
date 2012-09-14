/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "nwpis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nwpis.findAll", query = "SELECT n FROM Nwpis n"),
    @NamedQuery(name = "Nwpis.findById", query = "SELECT n FROM Nwpis n WHERE n.id = :id"),
    @NamedQuery(name = "Nwpis.findByKwa", query = "SELECT n FROM Nwpis n WHERE n.kwa = :kwa"),
    @NamedQuery(name = "Nwpis.findByDataK", query = "SELECT n FROM Nwpis n WHERE n.dataK = :dataK"),
    @NamedQuery(name = "Nwpis.findByStatus", query = "SELECT n FROM Nwpis n WHERE n.status = :status"),
    @NamedQuery(name = "Nwpis.findByNrDok", query = "SELECT n FROM Nwpis n WHERE n.nrDok = :nrDok"),
    @NamedQuery(name = "Nwpis.findByKlt", query = "SELECT n FROM Nwpis n WHERE n.klt = :klt")})
public class Nwpis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UZ_LOGI")
    @OneToOne
        private Uz kwa;
    @Column(name = "data_k", insertable=false, updatable=false, columnDefinition="timestamp default current_timestamp")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataK;
    @Size(max = 45)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DOK_ID_DOK")
    @OneToOne
        private Dok nrDok;
    @Size(max = 45)
    @Column(name = "klt")
    private String klt;

    public Nwpis() {
    }

    public Nwpis(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uz getKwa() {
        return kwa;
    }

    public void setKwa(Uz kwa) {
        this.kwa = kwa;
    }

    public Date getDataK() {
        return dataK;
    }

    public void setDataK(Date dataK) {
        this.dataK = dataK;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Dok getNrDok() {
        return nrDok;
    }

    public void setNrDok(Dok nrDok) {
        this.nrDok = nrDok;
    }

    public String getKlt() {
        return klt;
    }

    public void setKlt(String klt) {
        this.klt = klt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nwpis)) {
            return false;
        }
        Nwpis other = (Nwpis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nwpis{" + "id=" + id + ", kwa=" + kwa + ", dataK=" + dataK + ", status=" + status + ", nrDok=" + nrDok + ", klt=" + klt + '}';
    }

   
    
}
