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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "SDWI_KODTYTUB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SdwiKodtytub.findAll", query = "SELECT s FROM SdwiKodtytub s"),
    @NamedQuery(name = "SdwiKodtytub.findById", query = "SELECT s FROM SdwiKodtytub s WHERE s.id = :id"),
    @NamedQuery(name = "SdwiKodtytub.findByIdSdwiubezpieczony", query = "SELECT s FROM SdwiKodtytub s WHERE s.idSdwiubezpieczony = :idSdwiubezpieczony"),
    @NamedQuery(name = "SdwiKodtytub.findByIdSdwinaglowek", query = "SELECT s FROM SdwiKodtytub s WHERE s.idSdwinaglowek = :idSdwinaglowek"),
    @NamedQuery(name = "SdwiKodtytub.findByKodtytub", query = "SELECT s FROM SdwiKodtytub s WHERE s.kodtytub = :kodtytub"),
    @NamedQuery(name = "SdwiKodtytub.findByInserttmp", query = "SELECT s FROM SdwiKodtytub s WHERE s.inserttmp = :inserttmp")})
public class SdwiKodtytub implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SDWIUBEZPIECZONY", nullable = false)
    private int idSdwiubezpieczony;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SDWINAGLOWEK", nullable = false)
    private int idSdwinaglowek;
    @Size(max = 6)
    @Column(name = "KODTYTUB", length = 6)
    private String kodtytub;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public SdwiKodtytub() {
    }

    public SdwiKodtytub(Integer id) {
        this.id = id;
    }

    public SdwiKodtytub(Integer id, int idSdwiubezpieczony, int idSdwinaglowek) {
        this.id = id;
        this.idSdwiubezpieczony = idSdwiubezpieczony;
        this.idSdwinaglowek = idSdwinaglowek;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdSdwiubezpieczony() {
        return idSdwiubezpieczony;
    }

    public void setIdSdwiubezpieczony(int idSdwiubezpieczony) {
        this.idSdwiubezpieczony = idSdwiubezpieczony;
    }

    public int getIdSdwinaglowek() {
        return idSdwinaglowek;
    }

    public void setIdSdwinaglowek(int idSdwinaglowek) {
        this.idSdwinaglowek = idSdwinaglowek;
    }

    public String getKodtytub() {
        return kodtytub;
    }

    public void setKodtytub(String kodtytub) {
        this.kodtytub = kodtytub;
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
        if (!(object instanceof SdwiKodtytub)) {
            return false;
        }
        SdwiKodtytub other = (SdwiKodtytub) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.SdwiKodtytub[ id=" + id + " ]";
    }
    
}
