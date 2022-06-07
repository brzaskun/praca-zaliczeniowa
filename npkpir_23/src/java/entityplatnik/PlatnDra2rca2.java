/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "PLATN_DRA2RCA2")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnDra2rca2.findAll", query = "SELECT p FROM PlatnDra2rca2 p"),
    @NamedQuery(name = "PlatnDra2rca2.findById", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnDra2rca2.findByIdPlatnik", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnDra2rca2.findByIdDokumentu", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.idDokumentu = :idDokumentu"),
    @NamedQuery(name = "PlatnDra2rca2.findByTypDok", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.typDok = :typDok"),
    @NamedQuery(name = "PlatnDra2rca2.findByIIdDeklaracji", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.iIdDeklaracji = :iIdDeklaracji"),
    @NamedQuery(name = "PlatnDra2rca2.findByIRokPodstSkl", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.iRokPodstSkl = :iRokPodstSkl"),
    @NamedQuery(name = "PlatnDra2rca2.findByIiiPrzychodDzial", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.iiiPrzychodDzial = :iiiPrzychodDzial"),
    @NamedQuery(name = "PlatnDra2rca2.findByIiiNajnPodstawa", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.iiiNajnPodstawa = :iiiNajnPodstawa"),
    @NamedQuery(name = "PlatnDra2rca2.findByIvDataWypel", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.ivDataWypel = :ivDataWypel"),
    @NamedQuery(name = "PlatnDra2rca2.findByIdPlZus", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "PlatnDra2rca2.findByIdUbZus", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.idUbZus = :idUbZus"),
    @NamedQuery(name = "PlatnDra2rca2.findByNrDzialPlat", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.nrDzialPlat = :nrDzialPlat"),
    @NamedQuery(name = "PlatnDra2rca2.findByInserttmp", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.inserttmp = :inserttmp"),
    @NamedQuery(name = "PlatnDra2rca2.findByIiiDochod", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.iiiDochod = :iiiDochod"),
    @NamedQuery(name = "PlatnDra2rca2.findByIvPrzychodKarta", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.ivPrzychodKarta = :ivPrzychodKarta"),
    @NamedQuery(name = "PlatnDra2rca2.findByIvDochodKarta", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.ivDochodKarta = :ivDochodKarta"),
    @NamedQuery(name = "PlatnDra2rca2.findByIvPrzychodRyczalt", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.ivPrzychodRyczalt = :ivPrzychodRyczalt"),
    @NamedQuery(name = "PlatnDra2rca2.findByIvDochodRyczalt", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.ivDochodRyczalt = :ivDochodRyczalt"),
    @NamedQuery(name = "PlatnDra2rca2.findByIvPrzychodOgolne", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.ivPrzychodOgolne = :ivPrzychodOgolne"),
    @NamedQuery(name = "PlatnDra2rca2.findByIvDochodOgolne", query = "SELECT p FROM PlatnDra2rca2 p WHERE p.ivDochodOgolne = :ivDochodOgolne")})
public class PlatnDra2rca2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_DOKUMENTU")
    private Integer idDokumentu;
    @Size(max = 3)
    @Column(name = "TYP_DOK", length = 3)
    private String typDok;
    @Column(name = "I_ID_DEKLARACJI")
    private Short iIdDeklaracji;
    @Column(name = "I_ROK_PODST_SKL")
    private Short iRokPodstSkl;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_PRZYCHOD_DZIAL", precision = 10, scale = 2)
    private BigDecimal iiiPrzychodDzial;
    @Column(name = "III_NAJN_PODSTAWA", precision = 10, scale = 2)
    private BigDecimal iiiNajnPodstawa;
    @Column(name = "IV_DATA_WYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ivDataWypel;
    @Column(name = "ID_PL_ZUS")
    private BigInteger idPlZus;
    @Column(name = "ID_UB_ZUS")
    private BigInteger idUbZus;
    @Column(name = "NR_DZIAL_PLAT")
    private Short nrDzialPlat;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "III_DOCHOD", precision = 10, scale = 2)
    private BigDecimal iiiDochod;
    @Column(name = "IV_PRZYCHOD_KARTA", precision = 10, scale = 2)
    private BigDecimal ivPrzychodKarta;
    @Column(name = "IV_DOCHOD_KARTA", precision = 10, scale = 2)
    private BigDecimal ivDochodKarta;
    @Column(name = "IV_PRZYCHOD_RYCZALT", precision = 10, scale = 2)
    private BigDecimal ivPrzychodRyczalt;
    @Column(name = "IV_DOCHOD_RYCZALT", precision = 10, scale = 2)
    private BigDecimal ivDochodRyczalt;
    @Column(name = "IV_PRZYCHOD_OGOLNE", precision = 10, scale = 2)
    private BigDecimal ivPrzychodOgolne;
    @Column(name = "IV_DOCHOD_OGOLNE", precision = 10, scale = 2)
    private BigDecimal ivDochodOgolne;

    public PlatnDra2rca2() {
    }

    public PlatnDra2rca2(Integer id) {
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

    public Integer getIdDokumentu() {
        return idDokumentu;
    }

    public void setIdDokumentu(Integer idDokumentu) {
        this.idDokumentu = idDokumentu;
    }

    public String getTypDok() {
        return typDok;
    }

    public void setTypDok(String typDok) {
        this.typDok = typDok;
    }

    public Short getIIdDeklaracji() {
        return iIdDeklaracji;
    }

    public void setIIdDeklaracji(Short iIdDeklaracji) {
        this.iIdDeklaracji = iIdDeklaracji;
    }

    public Short getIRokPodstSkl() {
        return iRokPodstSkl;
    }

    public void setIRokPodstSkl(Short iRokPodstSkl) {
        this.iRokPodstSkl = iRokPodstSkl;
    }

    public BigDecimal getIiiPrzychodDzial() {
        return iiiPrzychodDzial;
    }

    public void setIiiPrzychodDzial(BigDecimal iiiPrzychodDzial) {
        this.iiiPrzychodDzial = iiiPrzychodDzial;
    }

    public BigDecimal getIiiNajnPodstawa() {
        return iiiNajnPodstawa;
    }

    public void setIiiNajnPodstawa(BigDecimal iiiNajnPodstawa) {
        this.iiiNajnPodstawa = iiiNajnPodstawa;
    }

    public Date getIvDataWypel() {
        return ivDataWypel;
    }

    public void setIvDataWypel(Date ivDataWypel) {
        this.ivDataWypel = ivDataWypel;
    }

    public BigInteger getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(BigInteger idPlZus) {
        this.idPlZus = idPlZus;
    }

    public BigInteger getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(BigInteger idUbZus) {
        this.idUbZus = idUbZus;
    }

    public Short getNrDzialPlat() {
        return nrDzialPlat;
    }

    public void setNrDzialPlat(Short nrDzialPlat) {
        this.nrDzialPlat = nrDzialPlat;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public BigDecimal getIiiDochod() {
        return iiiDochod;
    }

    public void setIiiDochod(BigDecimal iiiDochod) {
        this.iiiDochod = iiiDochod;
    }

    public BigDecimal getIvPrzychodKarta() {
        return ivPrzychodKarta;
    }

    public void setIvPrzychodKarta(BigDecimal ivPrzychodKarta) {
        this.ivPrzychodKarta = ivPrzychodKarta;
    }

    public BigDecimal getIvDochodKarta() {
        return ivDochodKarta;
    }

    public void setIvDochodKarta(BigDecimal ivDochodKarta) {
        this.ivDochodKarta = ivDochodKarta;
    }

    public BigDecimal getIvPrzychodRyczalt() {
        return ivPrzychodRyczalt;
    }

    public void setIvPrzychodRyczalt(BigDecimal ivPrzychodRyczalt) {
        this.ivPrzychodRyczalt = ivPrzychodRyczalt;
    }

    public BigDecimal getIvDochodRyczalt() {
        return ivDochodRyczalt;
    }

    public void setIvDochodRyczalt(BigDecimal ivDochodRyczalt) {
        this.ivDochodRyczalt = ivDochodRyczalt;
    }

    public BigDecimal getIvPrzychodOgolne() {
        return ivPrzychodOgolne;
    }

    public void setIvPrzychodOgolne(BigDecimal ivPrzychodOgolne) {
        this.ivPrzychodOgolne = ivPrzychodOgolne;
    }

    public BigDecimal getIvDochodOgolne() {
        return ivDochodOgolne;
    }

    public void setIvDochodOgolne(BigDecimal ivDochodOgolne) {
        this.ivDochodOgolne = ivDochodOgolne;
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
        if (!(object instanceof PlatnDra2rca2)) {
            return false;
        }
        PlatnDra2rca2 other = (PlatnDra2rca2) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnDra2rca2[ id=" + id + " ]";
    }
    
}
