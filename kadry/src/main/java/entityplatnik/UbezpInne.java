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
@Table(name = "UBEZP_INNE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpInne.findAll", query = "SELECT u FROM UbezpInne u"),
    @NamedQuery(name = "UbezpInne.findById", query = "SELECT u FROM UbezpInne u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpInne.findByIdUbezpieczony", query = "SELECT u FROM UbezpInne u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpInne.findByIdDokument", query = "SELECT u FROM UbezpInne u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpInne.findByKodkasy", query = "SELECT u FROM UbezpInne u WHERE u.kodkasy = :kodkasy"),
    @NamedQuery(name = "UbezpInne.findByDataDok", query = "SELECT u FROM UbezpInne u WHERE u.dataDok = :dataDok"),
    @NamedQuery(name = "UbezpInne.findByIdDokumentObyw", query = "SELECT u FROM UbezpInne u WHERE u.idDokumentObyw = :idDokumentObyw"),
    @NamedQuery(name = "UbezpInne.findByObywatelstwo", query = "SELECT u FROM UbezpInne u WHERE u.obywatelstwo = :obywatelstwo"),
    @NamedQuery(name = "UbezpInne.findByDataDokObyw", query = "SELECT u FROM UbezpInne u WHERE u.dataDokObyw = :dataDokObyw"),
    @NamedQuery(name = "UbezpInne.findByStatusDane", query = "SELECT u FROM UbezpInne u WHERE u.statusDane = :statusDane"),
    @NamedQuery(name = "UbezpInne.findByInserttmp", query = "SELECT u FROM UbezpInne u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpInne.findByIdPlZus", query = "SELECT u FROM UbezpInne u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpInne.findByIdUbZus", query = "SELECT u FROM UbezpInne u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "UbezpInne.findByDataZgonu", query = "SELECT u FROM UbezpInne u WHERE u.dataZgonu = :dataZgonu"),
    @NamedQuery(name = "UbezpInne.findByDataAktDatyZgonu", query = "SELECT u FROM UbezpInne u WHERE u.dataAktDatyZgonu = :dataAktDatyZgonu"),
    @NamedQuery(name = "UbezpInne.findByZrodloZgonu", query = "SELECT u FROM UbezpInne u WHERE u.zrodloZgonu = :zrodloZgonu"),
    @NamedQuery(name = "UbezpInne.findByImiedrugie", query = "SELECT u FROM UbezpInne u WHERE u.imiedrugie = :imiedrugie"),
    @NamedQuery(name = "UbezpInne.findByNazwiskorod", query = "SELECT u FROM UbezpInne u WHERE u.nazwiskorod = :nazwiskorod"),
    @NamedQuery(name = "UbezpInne.findByPlec", query = "SELECT u FROM UbezpInne u WHERE u.plec = :plec"),
    @NamedQuery(name = "UbezpInne.findByCzyRiaUbezp", query = "SELECT u FROM UbezpInne u WHERE u.czyRiaUbezp = :czyRiaUbezp")})
public class UbezpInne implements Serializable {

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
    @Column(name = "ID_DOKUMENT")
    private Integer idDokument;
    @Size(max = 3)
    @Column(name = "KODKASY", length = 3)
    private String kodkasy;
    @Column(name = "DATA_DOK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDok;
    @Column(name = "ID_DOKUMENT_OBYW")
    private Integer idDokumentObyw;
    @Size(max = 22)
    @Column(name = "OBYWATELSTWO", length = 22)
    private String obywatelstwo;
    @Column(name = "DATA_DOK_OBYW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDokObyw;
    @Column(name = "STATUS_DANE")
    private Character statusDane;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Column(name = "DATA_ZGONU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataZgonu;
    @Column(name = "DATA_AKT_DATY_ZGONU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAktDatyZgonu;
    @Column(name = "ZRODLO_ZGONU")
    private Character zrodloZgonu;
    @Size(max = 22)
    @Column(name = "IMIEDRUGIE", length = 22)
    private String imiedrugie;
    @Size(max = 31)
    @Column(name = "NAZWISKOROD", length = 31)
    private String nazwiskorod;
    @Column(name = "PLEC")
    private Character plec;
    @Column(name = "CZY_RIA_UBEZP")
    private Character czyRiaUbezp;

    public UbezpInne() {
    }

    public UbezpInne(Integer id) {
        this.id = id;
    }

    public UbezpInne(Integer id, int idUbezpieczony) {
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

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public String getKodkasy() {
        return kodkasy;
    }

    public void setKodkasy(String kodkasy) {
        this.kodkasy = kodkasy;
    }

    public Date getDataDok() {
        return dataDok;
    }

    public void setDataDok(Date dataDok) {
        this.dataDok = dataDok;
    }

    public Integer getIdDokumentObyw() {
        return idDokumentObyw;
    }

    public void setIdDokumentObyw(Integer idDokumentObyw) {
        this.idDokumentObyw = idDokumentObyw;
    }

    public String getObywatelstwo() {
        return obywatelstwo;
    }

    public void setObywatelstwo(String obywatelstwo) {
        this.obywatelstwo = obywatelstwo;
    }

    public Date getDataDokObyw() {
        return dataDokObyw;
    }

    public void setDataDokObyw(Date dataDokObyw) {
        this.dataDokObyw = dataDokObyw;
    }

    public Character getStatusDane() {
        return statusDane;
    }

    public void setStatusDane(Character statusDane) {
        this.statusDane = statusDane;
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

    public Date getDataZgonu() {
        return dataZgonu;
    }

    public void setDataZgonu(Date dataZgonu) {
        this.dataZgonu = dataZgonu;
    }

    public Date getDataAktDatyZgonu() {
        return dataAktDatyZgonu;
    }

    public void setDataAktDatyZgonu(Date dataAktDatyZgonu) {
        this.dataAktDatyZgonu = dataAktDatyZgonu;
    }

    public Character getZrodloZgonu() {
        return zrodloZgonu;
    }

    public void setZrodloZgonu(Character zrodloZgonu) {
        this.zrodloZgonu = zrodloZgonu;
    }

    public String getImiedrugie() {
        return imiedrugie;
    }

    public void setImiedrugie(String imiedrugie) {
        this.imiedrugie = imiedrugie;
    }

    public String getNazwiskorod() {
        return nazwiskorod;
    }

    public void setNazwiskorod(String nazwiskorod) {
        this.nazwiskorod = nazwiskorod;
    }

    public Character getPlec() {
        return plec;
    }

    public void setPlec(Character plec) {
        this.plec = plec;
    }

    public Character getCzyRiaUbezp() {
        return czyRiaUbezp;
    }

    public void setCzyRiaUbezp(Character czyRiaUbezp) {
        this.czyRiaUbezp = czyRiaUbezp;
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
        if (!(object instanceof UbezpInne)) {
            return false;
        }
        UbezpInne other = (UbezpInne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpInne[ id=" + id + " ]";
    }
    
}
