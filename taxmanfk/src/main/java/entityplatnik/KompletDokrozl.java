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
@Table(name = "KOMPLET_DOKROZL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KompletDokrozl.findAll", query = "SELECT k FROM KompletDokrozl k"),
    @NamedQuery(name = "KompletDokrozl.findById", query = "SELECT k FROM KompletDokrozl k WHERE k.id = :id"),
    @NamedQuery(name = "KompletDokrozl.findByIdPlatnik", query = "SELECT k FROM KompletDokrozl k WHERE k.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "KompletDokrozl.findByIdPlZus", query = "SELECT k FROM KompletDokrozl k WHERE k.idPlZus = :idPlZus"),
    @NamedQuery(name = "KompletDokrozl.findByIdKomplZus", query = "SELECT k FROM KompletDokrozl k WHERE k.idKomplZus = :idKomplZus"),
    @NamedQuery(name = "KompletDokrozl.findByNrKompletu", query = "SELECT k FROM KompletDokrozl k WHERE k.nrKompletu = :nrKompletu"),
    @NamedQuery(name = "KompletDokrozl.findByOkresRozlicz", query = "SELECT k FROM KompletDokrozl k WHERE k.okresRozlicz = :okresRozlicz"),
    @NamedQuery(name = "KompletDokrozl.findByZakres", query = "SELECT k FROM KompletDokrozl k WHERE k.zakres = :zakres"),
    @NamedQuery(name = "KompletDokrozl.findByPodnrKompletu", query = "SELECT k FROM KompletDokrozl k WHERE k.podnrKompletu = :podnrKompletu"),
    @NamedQuery(name = "KompletDokrozl.findByZnacznikCzasu", query = "SELECT k FROM KompletDokrozl k WHERE k.znacznikCzasu = :znacznikCzasu"),
    @NamedQuery(name = "KompletDokrozl.findByDataPotwWZus", query = "SELECT k FROM KompletDokrozl k WHERE k.dataPotwWZus = :dataPotwWZus"),
    @NamedQuery(name = "KompletDokrozl.findBySkrotDanych", query = "SELECT k FROM KompletDokrozl k WHERE k.skrotDanych = :skrotDanych"),
    @NamedQuery(name = "KompletDokrozl.findByInserttmp", query = "SELECT k FROM KompletDokrozl k WHERE k.inserttmp = :inserttmp")})
public class KompletDokrozl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PL_ZUS", nullable = false)
    private int idPlZus;
    @Column(name = "ID_KOMPL_ZUS")
    private Integer idKomplZus;
    @Column(name = "NR_KOMPLETU")
    private Integer nrKompletu;
    @Column(name = "OKRES_ROZLICZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date okresRozlicz;
    @Column(name = "ZAKRES")
    private Short zakres;
    @Column(name = "PODNR_KOMPLETU")
    private Integer podnrKompletu;
    @Column(name = "ZNACZNIK_CZASU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date znacznikCzasu;
    @Column(name = "DATA_POTW_W_ZUS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPotwWZus;
    @Size(max = 40)
    @Column(name = "SKROT_DANYCH", length = 40)
    private String skrotDanych;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public KompletDokrozl() {
    }

    public KompletDokrozl(Integer id) {
        this.id = id;
    }

    public KompletDokrozl(Integer id, int idPlZus) {
        this.id = id;
        this.idPlZus = idPlZus;
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

    public int getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(int idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getIdKomplZus() {
        return idKomplZus;
    }

    public void setIdKomplZus(Integer idKomplZus) {
        this.idKomplZus = idKomplZus;
    }

    public Integer getNrKompletu() {
        return nrKompletu;
    }

    public void setNrKompletu(Integer nrKompletu) {
        this.nrKompletu = nrKompletu;
    }

    public Date getOkresRozlicz() {
        return okresRozlicz;
    }

    public void setOkresRozlicz(Date okresRozlicz) {
        this.okresRozlicz = okresRozlicz;
    }

    public Short getZakres() {
        return zakres;
    }

    public void setZakres(Short zakres) {
        this.zakres = zakres;
    }

    public Integer getPodnrKompletu() {
        return podnrKompletu;
    }

    public void setPodnrKompletu(Integer podnrKompletu) {
        this.podnrKompletu = podnrKompletu;
    }

    public Date getZnacznikCzasu() {
        return znacznikCzasu;
    }

    public void setZnacznikCzasu(Date znacznikCzasu) {
        this.znacznikCzasu = znacznikCzasu;
    }

    public Date getDataPotwWZus() {
        return dataPotwWZus;
    }

    public void setDataPotwWZus(Date dataPotwWZus) {
        this.dataPotwWZus = dataPotwWZus;
    }

    public String getSkrotDanych() {
        return skrotDanych;
    }

    public void setSkrotDanych(String skrotDanych) {
        this.skrotDanych = skrotDanych;
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
        if (!(object instanceof KompletDokrozl)) {
            return false;
        }
        KompletDokrozl other = (KompletDokrozl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.KompletDokrozl[ id=" + id + " ]";
    }
    
}
