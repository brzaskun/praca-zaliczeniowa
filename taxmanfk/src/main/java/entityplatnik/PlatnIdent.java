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
@Table(name = "PLATN_IDENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnIdent.findAll", query = "SELECT p FROM PlatnIdent p"),
    @NamedQuery(name = "PlatnIdent.findById", query = "SELECT p FROM PlatnIdent p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnIdent.findByIdPlatnik", query = "SELECT p FROM PlatnIdent p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnIdent.findByIdDokZgl", query = "SELECT p FROM PlatnIdent p WHERE p.idDokZgl = :idDokZgl"),
    @NamedQuery(name = "PlatnIdent.findByNip", query = "SELECT p FROM PlatnIdent p WHERE p.nip = :nip"),
    @NamedQuery(name = "PlatnIdent.findByRegon", query = "SELECT p FROM PlatnIdent p WHERE p.regon = :regon"),
    @NamedQuery(name = "PlatnIdent.findByPesel", query = "SELECT p FROM PlatnIdent p WHERE p.pesel = :pesel"),
    @NamedQuery(name = "PlatnIdent.findByRodzdok", query = "SELECT p FROM PlatnIdent p WHERE p.rodzdok = :rodzdok"),
    @NamedQuery(name = "PlatnIdent.findBySerianrdok", query = "SELECT p FROM PlatnIdent p WHERE p.serianrdok = :serianrdok"),
    @NamedQuery(name = "PlatnIdent.findByNazwaskr", query = "SELECT p FROM PlatnIdent p WHERE p.nazwaskr = :nazwaskr"),
    @NamedQuery(name = "PlatnIdent.findByNazwisko", query = "SELECT p FROM PlatnIdent p WHERE p.nazwisko = :nazwisko"),
    @NamedQuery(name = "PlatnIdent.findByImiepierw", query = "SELECT p FROM PlatnIdent p WHERE p.imiepierw = :imiepierw"),
    @NamedQuery(name = "PlatnIdent.findByDataurodz", query = "SELECT p FROM PlatnIdent p WHERE p.dataurodz = :dataurodz"),
    @NamedQuery(name = "PlatnIdent.findByNazwafirma", query = "SELECT p FROM PlatnIdent p WHERE p.nazwafirma = :nazwafirma"),
    @NamedQuery(name = "PlatnIdent.findByTypPlatnika", query = "SELECT p FROM PlatnIdent p WHERE p.typPlatnika = :typPlatnika"),
    @NamedQuery(name = "PlatnIdent.findByStatusDane", query = "SELECT p FROM PlatnIdent p WHERE p.statusDane = :statusDane"),
    @NamedQuery(name = "PlatnIdent.findByDataod", query = "SELECT p FROM PlatnIdent p WHERE p.dataod = :dataod"),
    @NamedQuery(name = "PlatnIdent.findByTypBlok", query = "SELECT p FROM PlatnIdent p WHERE p.typBlok = :typBlok"),
    @NamedQuery(name = "PlatnIdent.findByInserttmp", query = "SELECT p FROM PlatnIdent p WHERE p.inserttmp = :inserttmp"),
    @NamedQuery(name = "PlatnIdent.findByIdPlZus", query = "SELECT p FROM PlatnIdent p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnIdent.findByZrodloDanych", query = "SELECT p FROM PlatnIdent p WHERE p.zrodloDanych = :zrodloDanych")})
public class PlatnIdent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Column(name = "ID_DOK_ZGL")
    private Integer idDokZgl;
    @Size(max = 10)
    @Column(name = "NIP", length = 10)
    private String nip;
    @Size(max = 14)
    @Column(name = "REGON", length = 14)
    private String regon;
    @Size(max = 11)
    @Column(name = "PESEL", length = 11)
    private String pesel;
    @Column(name = "RODZDOK")
    private Character rodzdok;
    @Size(max = 9)
    @Column(name = "SERIANRDOK", length = 9)
    private String serianrdok;
    @Size(max = 31)
    @Column(name = "NAZWASKR", length = 31)
    private String nazwaskr;
    @Size(max = 31)
    @Column(name = "NAZWISKO", length = 31)
    private String nazwisko;
    @Size(max = 22)
    @Column(name = "IMIEPIERW", length = 22)
    private String imiepierw;
    @Column(name = "DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataurodz;
    @Size(max = 62)
    @Column(name = "NAZWAFIRMA", length = 62)
    private String nazwafirma;
    @Column(name = "TYP_PLATNIKA")
    private Character typPlatnika;
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
    @Size(max = 50)
    @Column(name = "ZRODLO_DANYCH", length = 50)
    private String zrodloDanych;

    public PlatnIdent() {
    }

    public PlatnIdent(Integer id) {
        this.id = id;
    }

    public PlatnIdent(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdDokZgl() {
        return idDokZgl;
    }

    public void setIdDokZgl(Integer idDokZgl) {
        this.idDokZgl = idDokZgl;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
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

    public String getNazwaskr() {
        return nazwaskr;
    }

    public void setNazwaskr(String nazwaskr) {
        this.nazwaskr = nazwaskr;
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

    public String getNazwafirma() {
        return nazwafirma;
    }

    public void setNazwafirma(String nazwafirma) {
        this.nazwafirma = nazwafirma;
    }

    public Character getTypPlatnika() {
        return typPlatnika;
    }

    public void setTypPlatnika(Character typPlatnika) {
        this.typPlatnika = typPlatnika;
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

    public String getZrodloDanych() {
        return zrodloDanych;
    }

    public void setZrodloDanych(String zrodloDanych) {
        this.zrodloDanych = zrodloDanych;
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
        if (!(object instanceof PlatnIdent)) {
            return false;
        }
        PlatnIdent other = (PlatnIdent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnIdent[ id=" + id + " ]";
    }
    
}
