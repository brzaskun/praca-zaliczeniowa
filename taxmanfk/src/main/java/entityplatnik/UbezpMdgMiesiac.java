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
@Table(name = "UBEZP_MDG_MIESIAC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpMdgMiesiac.findAll", query = "SELECT u FROM UbezpMdgMiesiac u"),
    @NamedQuery(name = "UbezpMdgMiesiac.findById", query = "SELECT u FROM UbezpMdgMiesiac u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpMdgMiesiac.findByIdPlatnik", query = "SELECT u FROM UbezpMdgMiesiac u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpMdgMiesiac.findByIdZapis", query = "SELECT u FROM UbezpMdgMiesiac u WHERE u.idZapis = :idZapis"),
    @NamedQuery(name = "UbezpMdgMiesiac.findByIdZapisDgRok", query = "SELECT u FROM UbezpMdgMiesiac u WHERE u.idZapisDgRok = :idZapisDgRok"),
    @NamedQuery(name = "UbezpMdgMiesiac.findByIdUbezpieczony", query = "SELECT u FROM UbezpMdgMiesiac u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpMdgMiesiac.findByIdPlZus", query = "SELECT u FROM UbezpMdgMiesiac u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpMdgMiesiac.findByIdUbZus", query = "SELECT u FROM UbezpMdgMiesiac u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "UbezpMdgMiesiac.findByOkres", query = "SELECT u FROM UbezpMdgMiesiac u WHERE u.okres = :okres"),
    @NamedQuery(name = "UbezpMdgMiesiac.findByKodMdg", query = "SELECT u FROM UbezpMdgMiesiac u WHERE u.kodMdg = :kodMdg"),
    @NamedQuery(name = "UbezpMdgMiesiac.findByInserttmp", query = "SELECT u FROM UbezpMdgMiesiac u WHERE u.inserttmp = :inserttmp")})
public class UbezpMdgMiesiac implements Serializable {

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
    @Column(name = "ID_ZAPIS_DG_ROK")
    private Integer idZapisDgRok;
    @Column(name = "ID_UBEZPIECZONY")
    private Integer idUbezpieczony;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Column(name = "OKRES")
    @Temporal(TemporalType.TIMESTAMP)
    private Date okres;
    @Column(name = "KOD_MDG")
    private Character kodMdg;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public UbezpMdgMiesiac() {
    }

    public UbezpMdgMiesiac(Integer id) {
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

    public Integer getIdZapisDgRok() {
        return idZapisDgRok;
    }

    public void setIdZapisDgRok(Integer idZapisDgRok) {
        this.idZapisDgRok = idZapisDgRok;
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

    public Date getOkres() {
        return okres;
    }

    public void setOkres(Date okres) {
        this.okres = okres;
    }

    public Character getKodMdg() {
        return kodMdg;
    }

    public void setKodMdg(Character kodMdg) {
        this.kodMdg = kodMdg;
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
        if (!(object instanceof UbezpMdgMiesiac)) {
            return false;
        }
        UbezpMdgMiesiac other = (UbezpMdgMiesiac) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpMdgMiesiac[ id=" + id + " ]";
    }
    
}
