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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "UBEZP_MDG_ROK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpMdgRok.findAll", query = "SELECT u FROM UbezpMdgRok u"),
    @NamedQuery(name = "UbezpMdgRok.findById", query = "SELECT u FROM UbezpMdgRok u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpMdgRok.findByIdPlatnik", query = "SELECT u FROM UbezpMdgRok u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpMdgRok.findByIdZapis", query = "SELECT u FROM UbezpMdgRok u WHERE u.idZapis = :idZapis"),
    @NamedQuery(name = "UbezpMdgRok.findByRok", query = "SELECT u FROM UbezpMdgRok u WHERE u.rok = :rok"),
    @NamedQuery(name = "UbezpMdgRok.findByIdUbezpieczony", query = "SELECT u FROM UbezpMdgRok u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpMdgRok.findByIdPlZus", query = "SELECT u FROM UbezpMdgRok u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpMdgRok.findByIdUbZus", query = "SELECT u FROM UbezpMdgRok u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "UbezpMdgRok.findByDgMcStart", query = "SELECT u FROM UbezpMdgRok u WHERE u.dgMcStart = :dgMcStart"),
    @NamedQuery(name = "UbezpMdgRok.findByDgMcEnd", query = "SELECT u FROM UbezpMdgRok u WHERE u.dgMcEnd = :dgMcEnd"),
    @NamedQuery(name = "UbezpMdgRok.findByMdgMcStart", query = "SELECT u FROM UbezpMdgRok u WHERE u.mdgMcStart = :mdgMcStart"),
    @NamedQuery(name = "UbezpMdgRok.findByMdgMcEnd", query = "SELECT u FROM UbezpMdgRok u WHERE u.mdgMcEnd = :mdgMcEnd"),
    @NamedQuery(name = "UbezpMdgRok.findByLiczbaMcDg", query = "SELECT u FROM UbezpMdgRok u WHERE u.liczbaMcDg = :liczbaMcDg"),
    @NamedQuery(name = "UbezpMdgRok.findByLiczbaMcMdg", query = "SELECT u FROM UbezpMdgRok u WHERE u.liczbaMcMdg = :liczbaMcMdg"),
    @NamedQuery(name = "UbezpMdgRok.findByInserttmp", query = "SELECT u FROM UbezpMdgRok u WHERE u.inserttmp = :inserttmp")})
public class UbezpMdgRok implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_ZAPIS")
    private Integer idZapis;
    @Column(name = "ROK")
    private Integer rok;
    @Column(name = "ID_UBEZPIECZONY")
    private Integer idUbezpieczony;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Column(name = "DG_MC_START")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dgMcStart;
    @Column(name = "DG_MC_END")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dgMcEnd;
    @Column(name = "MDG_MC_START")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdgMcStart;
    @Column(name = "MDG_MC_END")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdgMcEnd;
    @Column(name = "LICZBA_MC_DG")
    private Integer liczbaMcDg;
    @Column(name = "LICZBA_MC_MDG")
    private Integer liczbaMcMdg;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public UbezpMdgRok() {
    }

    public UbezpMdgRok(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdZapis() {
        return idZapis;
    }

    public void setIdZapis(Integer idZapis) {
        this.idZapis = idZapis;
    }

    public Integer getRok() {
        return rok;
    }

    public void setRok(Integer rok) {
        this.rok = rok;
    }

    public Integer getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(Integer idUbezpieczony) {
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

    public Date getDgMcStart() {
        return dgMcStart;
    }

    public void setDgMcStart(Date dgMcStart) {
        this.dgMcStart = dgMcStart;
    }

    public Date getDgMcEnd() {
        return dgMcEnd;
    }

    public void setDgMcEnd(Date dgMcEnd) {
        this.dgMcEnd = dgMcEnd;
    }

    public Date getMdgMcStart() {
        return mdgMcStart;
    }

    public void setMdgMcStart(Date mdgMcStart) {
        this.mdgMcStart = mdgMcStart;
    }

    public Date getMdgMcEnd() {
        return mdgMcEnd;
    }

    public void setMdgMcEnd(Date mdgMcEnd) {
        this.mdgMcEnd = mdgMcEnd;
    }

    public Integer getLiczbaMcDg() {
        return liczbaMcDg;
    }

    public void setLiczbaMcDg(Integer liczbaMcDg) {
        this.liczbaMcDg = liczbaMcDg;
    }

    public Integer getLiczbaMcMdg() {
        return liczbaMcMdg;
    }

    public void setLiczbaMcMdg(Integer liczbaMcMdg) {
        this.liczbaMcMdg = liczbaMcMdg;
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
        if (!(object instanceof UbezpMdgRok)) {
            return false;
        }
        UbezpMdgRok other = (UbezpMdgRok) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpMdgRok[ id=" + id + " ]";
    }
    
}
