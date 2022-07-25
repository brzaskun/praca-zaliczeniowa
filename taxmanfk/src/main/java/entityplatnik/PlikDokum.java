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
@Table(name = "PLIK_DOKUM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlikDokum.findAll", query = "SELECT p FROM PlikDokum p"),
    @NamedQuery(name = "PlikDokum.findById", query = "SELECT p FROM PlikDokum p WHERE p.id = :id"),
    @NamedQuery(name = "PlikDokum.findByIdDokument", query = "SELECT p FROM PlikDokum p WHERE p.idDokument = :idDokument"),
    @NamedQuery(name = "PlikDokum.findByIdPlik", query = "SELECT p FROM PlikDokum p WHERE p.idPlik = :idPlik"),
    @NamedQuery(name = "PlikDokum.findByIdImport", query = "SELECT p FROM PlikDokum p WHERE p.idImport = :idImport"),
    @NamedQuery(name = "PlikDokum.findByInserttmp", query = "SELECT p FROM PlikDokum p WHERE p.inserttmp = :inserttmp")})
public class PlikDokum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_DOKUMENT")
    private Integer idDokument;
    @Column(name = "ID_PLIK")
    private Integer idPlik;
    @Column(name = "ID_IMPORT")
    private Integer idImport;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public PlikDokum() {
    }

    public PlikDokum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Integer getIdPlik() {
        return idPlik;
    }

    public void setIdPlik(Integer idPlik) {
        this.idPlik = idPlik;
    }

    public Integer getIdImport() {
        return idImport;
    }

    public void setIdImport(Integer idImport) {
        this.idImport = idImport;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
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
        if (!(object instanceof PlikDokum)) {
            return false;
        }
        PlikDokum other = (PlikDokum) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlikDokum[ id=" + id + " ]";
    }
    
}
