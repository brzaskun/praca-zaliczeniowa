/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

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
@Table(name = "ZESTAW_DOKUM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ZestawDokum.findAll", query = "SELECT z FROM ZestawDokum z"),
    @NamedQuery(name = "ZestawDokum.findById", query = "SELECT z FROM ZestawDokum z WHERE z.id = :id"),
    @NamedQuery(name = "ZestawDokum.findByIdZestaw", query = "SELECT z FROM ZestawDokum z WHERE z.idZestaw = :idZestaw"),
    @NamedQuery(name = "ZestawDokum.findByIdDokument", query = "SELECT z FROM ZestawDokum z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "ZestawDokum.findByStatus", query = "SELECT z FROM ZestawDokum z WHERE z.status = :status"),
    @NamedQuery(name = "ZestawDokum.findByInserttmp", query = "SELECT z FROM ZestawDokum z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "ZestawDokum.findByNrDokument", query = "SELECT z FROM ZestawDokum z WHERE z.nrDokument = :nrDokument"),
    @NamedQuery(name = "ZestawDokum.findByKolejnosc", query = "SELECT z FROM ZestawDokum z WHERE z.kolejnosc = :kolejnosc")})
public class ZestawDokum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ZESTAW", nullable = false)
    private int idZestaw;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOKUMENT", nullable = false)
    private int idDokument;
    @Column(name = "STATUS")
    private Character status;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "NR_DOKUMENT")
    private Integer nrDokument;
    @Column(name = "KOLEJNOSC")
    private Integer kolejnosc;

    public ZestawDokum() {
    }

    public ZestawDokum(Integer id) {
        this.id = id;
    }

    public ZestawDokum(Integer id, int idZestaw, int idDokument) {
        this.id = id;
        this.idZestaw = idZestaw;
        this.idDokument = idDokument;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdZestaw() {
        return idZestaw;
    }

    public void setIdZestaw(int idZestaw) {
        this.idZestaw = idZestaw;
    }

    public int getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(int idDokument) {
        this.idDokument = idDokument;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getNrDokument() {
        return nrDokument;
    }

    public void setNrDokument(Integer nrDokument) {
        this.nrDokument = nrDokument;
    }

    public Integer getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(Integer kolejnosc) {
        this.kolejnosc = kolejnosc;
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
        if (!(object instanceof ZestawDokum)) {
            return false;
        }
        ZestawDokum other = (ZestawDokum) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.ZestawDokum[ id=" + id + " ]";
    }
    
}
