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
@Table(name = "UBEZP_IDENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpIdent.findAll", query = "SELECT u FROM UbezpIdent u"),
    @NamedQuery(name = "UbezpIdent.findById", query = "SELECT u FROM UbezpIdent u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpIdent.findByIdUbezpieczony", query = "SELECT u FROM UbezpIdent u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpIdent.findByIdUbezpieczonyScal", query = "SELECT u FROM UbezpIdent u WHERE u.idUbezpieczonyScal = :idUbezpieczonyScal"),
    @NamedQuery(name = "UbezpIdent.findByIdSdwiubezpieczony", query = "SELECT u FROM UbezpIdent u WHERE u.idSdwiubezpieczony = :idSdwiubezpieczony"),
    @NamedQuery(name = "UbezpIdent.findByIdDokument", query = "SELECT u FROM UbezpIdent u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpIdent.findByZrodloDanych", query = "SELECT u FROM UbezpIdent u WHERE u.zrodloDanych = :zrodloDanych"),
    @NamedQuery(name = "UbezpIdent.findByPesel", query = "SELECT u FROM UbezpIdent u WHERE u.pesel = :pesel"),
    @NamedQuery(name = "UbezpIdent.findByNip", query = "SELECT u FROM UbezpIdent u WHERE u.nip = :nip"),
    @NamedQuery(name = "UbezpIdent.findByRodzdok", query = "SELECT u FROM UbezpIdent u WHERE u.rodzdok = :rodzdok"),
    @NamedQuery(name = "UbezpIdent.findBySerianrdok", query = "SELECT u FROM UbezpIdent u WHERE u.serianrdok = :serianrdok"),
    @NamedQuery(name = "UbezpIdent.findByNazwisko", query = "SELECT u FROM UbezpIdent u WHERE u.nazwisko = :nazwisko"),
    @NamedQuery(name = "UbezpIdent.findByImiepierw", query = "SELECT u FROM UbezpIdent u WHERE u.imiepierw = :imiepierw"),
    @NamedQuery(name = "UbezpIdent.findByDataurodz", query = "SELECT u FROM UbezpIdent u WHERE u.dataurodz = :dataurodz"),
    @NamedQuery(name = "UbezpIdent.findByStatusDane", query = "SELECT u FROM UbezpIdent u WHERE u.statusDane = :statusDane"),
    @NamedQuery(name = "UbezpIdent.findByDataod", query = "SELECT u FROM UbezpIdent u WHERE u.dataod = :dataod"),
    @NamedQuery(name = "UbezpIdent.findByTypBlok", query = "SELECT u FROM UbezpIdent u WHERE u.typBlok = :typBlok"),
    @NamedQuery(name = "UbezpIdent.findByInserttmp", query = "SELECT u FROM UbezpIdent u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpIdent.findByIdPlZus", query = "SELECT u FROM UbezpIdent u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpIdent.findByIdUbZus", query = "SELECT u FROM UbezpIdent u WHERE u.idUbZus = :idUbZus")})
public class UbezpIdent implements Serializable {

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
    @Column(name = "ID_UBEZPIECZONY_SCAL")
    private Integer idUbezpieczonyScal;
    @Column(name = "ID_SDWIUBEZPIECZONY")
    private Integer idSdwiubezpieczony;
    @Column(name = "ID_DOKUMENT")
    private Integer idDokument;
    @Size(max = 50)
    @Column(name = "ZRODLO_DANYCH", length = 50)
    private String zrodloDanych;
    @Size(max = 11)
    @Column(name = "PESEL", length = 11)
    private String pesel;
    @Size(max = 10)
    @Column(name = "NIP", length = 10)
    private String nip;
    @Column(name = "RODZDOK")
    private Character rodzdok;
    @Size(max = 9)
    @Column(name = "SERIANRDOK", length = 9)
    private String serianrdok;
    @Size(max = 31)
    @Column(name = "NAZWISKO", length = 31)
    private String nazwisko;
    @Size(max = 22)
    @Column(name = "IMIEPIERW", length = 22)
    private String imiepierw;
    @Column(name = "DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataurodz;
    @Column(name = "STATUS_DANE")
    private Character statusDane;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "TYP_BLOK")
    private Character typBlok;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;

    public UbezpIdent() {
    }

    public UbezpIdent(Integer id) {
        this.id = id;
    }

    public UbezpIdent(Integer id, int idUbezpieczony) {
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

    public Integer getIdUbezpieczonyScal() {
        return idUbezpieczonyScal;
    }

    public void setIdUbezpieczonyScal(Integer idUbezpieczonyScal) {
        this.idUbezpieczonyScal = idUbezpieczonyScal;
    }

    public Integer getIdSdwiubezpieczony() {
        return idSdwiubezpieczony;
    }

    public void setIdSdwiubezpieczony(Integer idSdwiubezpieczony) {
        this.idSdwiubezpieczony = idSdwiubezpieczony;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public String getZrodloDanych() {
        return zrodloDanych;
    }

    public void setZrodloDanych(String zrodloDanych) {
        this.zrodloDanych = zrodloDanych;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Character getRodzdok() {
        return rodzdok;
    }

    public void setRodzdok(Character rodzdok) {
        this.rodzdok = rodzdok;
    }

    public String getSerianrdok() {
        return serianrdok;
    }

    public void setSerianrdok(String serianrdok) {
        this.serianrdok = serianrdok;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImiepierw() {
        return imiepierw;
    }

    public void setImiepierw(String imiepierw) {
        this.imiepierw = imiepierw;
    }

    public Date getDataurodz() {
        return dataurodz;
    }

    public void setDataurodz(Date dataurodz) {
        this.dataurodz = dataurodz;
    }

    public Character getStatusDane() {
        return statusDane;
    }

    public void setStatusDane(Character statusDane) {
        this.statusDane = statusDane;
    }

    public Date getDataod() {
        return dataod;
    }

    public void setDataod(Date dataod) {
        this.dataod = dataod;
    }

    public Character getTypBlok() {
        return typBlok;
    }

    public void setTypBlok(Character typBlok) {
        this.typBlok = typBlok;
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
        if (!(object instanceof UbezpIdent)) {
            return false;
        }
        UbezpIdent other = (UbezpIdent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpIdent[ id=" + id + " ]";
    }
    
}
