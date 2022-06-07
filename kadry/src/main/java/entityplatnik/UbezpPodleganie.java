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
@Table(name = "UBEZP_PODLEGANIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpPodleganie.findAll", query = "SELECT u FROM UbezpPodleganie u"),
    @NamedQuery(name = "UbezpPodleganie.findById", query = "SELECT u FROM UbezpPodleganie u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpPodleganie.findByIdUbezpieczony", query = "SELECT u FROM UbezpPodleganie u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpPodleganie.findByIdPlZus", query = "SELECT u FROM UbezpPodleganie u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpPodleganie.findByIdUbZus", query = "SELECT u FROM UbezpPodleganie u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "UbezpPodleganie.findByKodtytub", query = "SELECT u FROM UbezpPodleganie u WHERE u.kodtytub = :kodtytub"),
    @NamedQuery(name = "UbezpPodleganie.findByPrdoem", query = "SELECT u FROM UbezpPodleganie u WHERE u.prdoem = :prdoem"),
    @NamedQuery(name = "UbezpPodleganie.findByStniep", query = "SELECT u FROM UbezpPodleganie u WHERE u.stniep = :stniep"),
    @NamedQuery(name = "UbezpPodleganie.findByDataod", query = "SELECT u FROM UbezpPodleganie u WHERE u.dataod = :dataod"),
    @NamedQuery(name = "UbezpPodleganie.findByDatado", query = "SELECT u FROM UbezpPodleganie u WHERE u.datado = :datado"),
    @NamedQuery(name = "UbezpPodleganie.findByEmerPodleganie", query = "SELECT u FROM UbezpPodleganie u WHERE u.emerPodleganie = :emerPodleganie"),
    @NamedQuery(name = "UbezpPodleganie.findByRentPodleganie", query = "SELECT u FROM UbezpPodleganie u WHERE u.rentPodleganie = :rentPodleganie"),
    @NamedQuery(name = "UbezpPodleganie.findByChorPodleganie", query = "SELECT u FROM UbezpPodleganie u WHERE u.chorPodleganie = :chorPodleganie"),
    @NamedQuery(name = "UbezpPodleganie.findByWypPodleganie", query = "SELECT u FROM UbezpPodleganie u WHERE u.wypPodleganie = :wypPodleganie"),
    @NamedQuery(name = "UbezpPodleganie.findByZdrowPodleganie", query = "SELECT u FROM UbezpPodleganie u WHERE u.zdrowPodleganie = :zdrowPodleganie"),
    @NamedQuery(name = "UbezpPodleganie.findByNumerSchematu", query = "SELECT u FROM UbezpPodleganie u WHERE u.numerSchematu = :numerSchematu"),
    @NamedQuery(name = "UbezpPodleganie.findByTypDokWyrej", query = "SELECT u FROM UbezpPodleganie u WHERE u.typDokWyrej = :typDokWyrej"),
    @NamedQuery(name = "UbezpPodleganie.findByDataWypeldokwyrej", query = "SELECT u FROM UbezpPodleganie u WHERE u.dataWypeldokwyrej = :dataWypeldokwyrej"),
    @NamedQuery(name = "UbezpPodleganie.findByZwuaVDatarozwygstps", query = "SELECT u FROM UbezpPodleganie u WHERE u.zwuaVDatarozwygstps = :zwuaVDatarozwygstps"),
    @NamedQuery(name = "UbezpPodleganie.findByInserttmp", query = "SELECT u FROM UbezpPodleganie u WHERE u.inserttmp = :inserttmp")})
public class UbezpPodleganie implements Serializable {

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
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Size(max = 4)
    @Column(name = "KODTYTUB", length = 4)
    private String kodtytub;
    @Column(name = "PRDOEM")
    private Character prdoem;
    @Column(name = "STNIEP")
    private Character stniep;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "DATADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datado;
    @Column(name = "EMER_PODLEGANIE")
    private Character emerPodleganie;
    @Column(name = "RENT_PODLEGANIE")
    private Character rentPodleganie;
    @Column(name = "CHOR_PODLEGANIE")
    private Character chorPodleganie;
    @Column(name = "WYP_PODLEGANIE")
    private Character wypPodleganie;
    @Column(name = "ZDROW_PODLEGANIE")
    private Character zdrowPodleganie;
    @Column(name = "NUMER_SCHEMATU")
    private Integer numerSchematu;
    @Column(name = "TYP_DOK_WYREJ")
    private Integer typDokWyrej;
    @Column(name = "DATA_WYPELDOKWYREJ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataWypeldokwyrej;
    @Column(name = "ZWUA_V_DATAROZWYGSTPS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zwuaVDatarozwygstps;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public UbezpPodleganie() {
    }

    public UbezpPodleganie(Integer id) {
        this.id = id;
    }

    public UbezpPodleganie(Integer id, int idUbezpieczony) {
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

    public Character getEmerPodleganie() {
        return emerPodleganie;
    }

    public void setEmerPodleganie(Character emerPodleganie) {
        this.emerPodleganie = emerPodleganie;
    }

    public Character getRentPodleganie() {
        return rentPodleganie;
    }

    public void setRentPodleganie(Character rentPodleganie) {
        this.rentPodleganie = rentPodleganie;
    }

    public Character getChorPodleganie() {
        return chorPodleganie;
    }

    public void setChorPodleganie(Character chorPodleganie) {
        this.chorPodleganie = chorPodleganie;
    }

    public Character getWypPodleganie() {
        return wypPodleganie;
    }

    public void setWypPodleganie(Character wypPodleganie) {
        this.wypPodleganie = wypPodleganie;
    }

    public Character getZdrowPodleganie() {
        return zdrowPodleganie;
    }

    public void setZdrowPodleganie(Character zdrowPodleganie) {
        this.zdrowPodleganie = zdrowPodleganie;
    }

    public Integer getNumerSchematu() {
        return numerSchematu;
    }

    public void setNumerSchematu(Integer numerSchematu) {
        this.numerSchematu = numerSchematu;
    }

    public Integer getTypDokWyrej() {
        return typDokWyrej;
    }

    public void setTypDokWyrej(Integer typDokWyrej) {
        this.typDokWyrej = typDokWyrej;
    }

    public Date getDataWypeldokwyrej() {
        return dataWypeldokwyrej;
    }

    public void setDataWypeldokwyrej(Date dataWypeldokwyrej) {
        this.dataWypeldokwyrej = dataWypeldokwyrej;
    }

    public Date getZwuaVDatarozwygstps() {
        return zwuaVDatarozwygstps;
    }

    public void setZwuaVDatarozwygstps(Date zwuaVDatarozwygstps) {
        this.zwuaVDatarozwygstps = zwuaVDatarozwygstps;
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
        if (!(object instanceof UbezpPodleganie)) {
            return false;
        }
        UbezpPodleganie other = (UbezpPodleganie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpPodleganie[ id=" + id + " ]";
    }
    
}
