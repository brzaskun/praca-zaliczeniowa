/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

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
@Table(name = "UBEZP_KOD_PRACY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpKodPracy.findAll", query = "SELECT u FROM UbezpKodPracy u"),
    @NamedQuery(name = "UbezpKodPracy.findById", query = "SELECT u FROM UbezpKodPracy u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpKodPracy.findByIdUbezpieczony", query = "SELECT u FROM UbezpKodPracy u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpKodPracy.findByKodtytub", query = "SELECT u FROM UbezpKodPracy u WHERE u.kodtytub = :kodtytub"),
    @NamedQuery(name = "UbezpKodPracy.findByPrdoem", query = "SELECT u FROM UbezpKodPracy u WHERE u.prdoem = :prdoem"),
    @NamedQuery(name = "UbezpKodPracy.findByStniep", query = "SELECT u FROM UbezpKodPracy u WHERE u.stniep = :stniep"),
    @NamedQuery(name = "UbezpKodPracy.findByKodprszw", query = "SELECT u FROM UbezpKodPracy u WHERE u.kodprszw = :kodprszw"),
    @NamedQuery(name = "UbezpKodPracy.findByDataod", query = "SELECT u FROM UbezpKodPracy u WHERE u.dataod = :dataod"),
    @NamedQuery(name = "UbezpKodPracy.findByDatado", query = "SELECT u FROM UbezpKodPracy u WHERE u.datado = :datado"),
    @NamedQuery(name = "UbezpKodPracy.findByWymczprl", query = "SELECT u FROM UbezpKodPracy u WHERE u.wymczprl = :wymczprl"),
    @NamedQuery(name = "UbezpKodPracy.findByWymczprm", query = "SELECT u FROM UbezpKodPracy u WHERE u.wymczprm = :wymczprm"),
    @NamedQuery(name = "UbezpKodPracy.findByInserttmp", query = "SELECT u FROM UbezpKodPracy u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpKodPracy.findByIdPlZus", query = "SELECT u FROM UbezpKodPracy u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpKodPracy.findByIdUbZus", query = "SELECT u FROM UbezpKodPracy u WHERE u.idUbZus = :idUbZus")})
public class UbezpKodPracy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
    @Size(max = 4)
    @Column(name = "KODTYTUB", length = 4)
    private String kodtytub;
    @Column(name = "PRDOEM")
    private Character prdoem;
    @Column(name = "STNIEP")
    private Character stniep;
    @Size(max = 3)
    @Column(name = "KODPRSZW", length = 3)
    private String kodprszw;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "DATADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datado;
    @Size(max = 3)
    @Column(name = "WYMCZPRL", length = 3)
    private String wymczprl;
    @Size(max = 3)
    @Column(name = "WYMCZPRM", length = 3)
    private String wymczprm;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;

    public UbezpKodPracy() {
    }

    public UbezpKodPracy(Integer id) {
        this.id = id;
    }

    public UbezpKodPracy(Integer id, int idUbezpieczony) {
        this.id = id;
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public String getKodtytub() {
        return kodtytub;
    }

    public void setKodtytub(String kodtytub) {
        this.kodtytub = kodtytub;
    }

    public Character getPrdoem() {
        return prdoem;
    }

    public void setPrdoem(Character prdoem) {
        this.prdoem = prdoem;
    }

    public Character getStniep() {
        return stniep;
    }

    public void setStniep(Character stniep) {
        this.stniep = stniep;
    }

    public String getKodprszw() {
        return kodprszw;
    }

    public void setKodprszw(String kodprszw) {
        this.kodprszw = kodprszw;
    }

    public Date getDataod() {
        return dataod;
    }

    public void setDataod(Date dataod) {
        this.dataod = dataod;
    }

    public Date getDatado() {
        return datado;
    }

    public void setDatado(Date datado) {
        this.datado = datado;
    }

    public String getWymczprl() {
        return wymczprl;
    }

    public void setWymczprl(String wymczprl) {
        this.wymczprl = wymczprl;
    }

    public String getWymczprm() {
        return wymczprm;
    }

    public void setWymczprm(String wymczprm) {
        this.wymczprm = wymczprm;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(Integer idUbZus) {
        this.idUbZus = idUbZus;
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
        if (!(object instanceof UbezpKodPracy)) {
            return false;
        }
        UbezpKodPracy other = (UbezpKodPracy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpKodPracy[ id=" + id + " ]";
    }
    
}
