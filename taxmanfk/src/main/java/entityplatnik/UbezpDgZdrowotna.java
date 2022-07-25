/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "UBEZP_DG_ZDROWOTNA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpDgZdrowotna.findAll", query = "SELECT u FROM UbezpDgZdrowotna u"),
    @NamedQuery(name = "UbezpDgZdrowotna.findById", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByIdPlatnik", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByIdUbezpieczony", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByIdZapis", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.idZapis = :idZapis"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByIdUbZus", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByIdPlZus", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByOkres", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.okres = :okres"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByFormaPodatkowa", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.formaPodatkowa = :formaPodatkowa"),
    @NamedQuery(name = "UbezpDgZdrowotna.findBySumaPrzychBiez", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.sumaPrzychBiez = :sumaPrzychBiez"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByDeklRokpoprz", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.deklRokpoprz = :deklRokpoprz"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByPrzychRokpoprz", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.przychRokpoprz = :przychRokpoprz"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByDochod", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.dochod = :dochod"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByPodstawa", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.podstawa = :podstawa"),
    @NamedQuery(name = "UbezpDgZdrowotna.findBySkladka", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.skladka = :skladka"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByTypDok", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.typDok = :typDok"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByIdWewDok", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.idWewDok = :idWewDok"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByNrPoddok", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.nrPoddok = :nrPoddok"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByKodTytulu", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.kodTytulu = :kodTytulu"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByAktualny", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.aktualny = :aktualny"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByInserttmp", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByDataCzasUtworz", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.dataCzasUtworz = :dataCzasUtworz"),
    @NamedQuery(name = "UbezpDgZdrowotna.findByDataCzasZmiany", query = "SELECT u FROM UbezpDgZdrowotna u WHERE u.dataCzasZmiany = :dataCzasZmiany")})
public class UbezpDgZdrowotna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_UBEZPIECZONY")
    private Integer idUbezpieczony;
    @Column(name = "ID_ZAPIS")
    private Integer idZapis;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "OKRES")
    @Temporal(TemporalType.TIMESTAMP)
    private Date okres;
    @Column(name = "FORMA_PODATKOWA")
    private Character formaPodatkowa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SUMA_PRZYCH_BIEZ", precision = 10, scale = 2)
    private BigDecimal sumaPrzychBiez;
    @Column(name = "DEKL_ROKPOPRZ")
    private Character deklRokpoprz;
    @Column(name = "PRZYCH_ROKPOPRZ", precision = 10, scale = 2)
    private BigDecimal przychRokpoprz;
    @Column(name = "DOCHOD", precision = 10, scale = 2)
    private BigDecimal dochod;
    @Column(name = "PODSTAWA", precision = 10, scale = 2)
    private BigDecimal podstawa;
    @Column(name = "SKLADKA", precision = 10, scale = 2)
    private BigDecimal skladka;
    @Size(max = 5)
    @Column(name = "TYP_DOK", length = 5)
    private String typDok;
    @Column(name = "ID_WEW_DOK")
    private Integer idWewDok;
    @Column(name = "NR_PODDOK")
    private Integer nrPoddok;
    @Size(max = 4)
    @Column(name = "KOD_TYTULU", length = 4)
    private String kodTytulu;
    @Column(name = "AKTUALNY")
    private Character aktualny;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "DATA_CZAS_UTWORZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCzasUtworz;
    @Column(name = "DATA_CZAS_ZMIANY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCzasZmiany;

    public UbezpDgZdrowotna() {
    }

    public UbezpDgZdrowotna(Integer id) {
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

    public Integer getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(Integer idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getIdZapis() {
        return idZapis;
    }

    public void setIdZapis(Integer idZapis) {
        this.idZapis = idZapis;
    }

    public Integer getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(Integer idUbZus) {
        this.idUbZus = idUbZus;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Date getOkres() {
        return okres;
    }

    public void setOkres(Date okres) {
        this.okres = okres;
    }

    public Character getFormaPodatkowa() {
        return formaPodatkowa;
    }

    public void setFormaPodatkowa(Character formaPodatkowa) {
        this.formaPodatkowa = formaPodatkowa;
    }

    public BigDecimal getSumaPrzychBiez() {
        return sumaPrzychBiez;
    }

    public void setSumaPrzychBiez(BigDecimal sumaPrzychBiez) {
        this.sumaPrzychBiez = sumaPrzychBiez;
    }

    public Character getDeklRokpoprz() {
        return deklRokpoprz;
    }

    public void setDeklRokpoprz(Character deklRokpoprz) {
        this.deklRokpoprz = deklRokpoprz;
    }

    public BigDecimal getPrzychRokpoprz() {
        return przychRokpoprz;
    }

    public void setPrzychRokpoprz(BigDecimal przychRokpoprz) {
        this.przychRokpoprz = przychRokpoprz;
    }

    public BigDecimal getDochod() {
        return dochod;
    }

    public void setDochod(BigDecimal dochod) {
        this.dochod = dochod;
    }

    public BigDecimal getPodstawa() {
        return podstawa;
    }

    public void setPodstawa(BigDecimal podstawa) {
        this.podstawa = podstawa;
    }

    public BigDecimal getSkladka() {
        return skladka;
    }

    public void setSkladka(BigDecimal skladka) {
        this.skladka = skladka;
    }

    public String getTypDok() {
        return typDok;
    }

    public void setTypDok(String typDok) {
        this.typDok = typDok;
    }

    public Integer getIdWewDok() {
        return idWewDok;
    }

    public void setIdWewDok(Integer idWewDok) {
        this.idWewDok = idWewDok;
    }

    public Integer getNrPoddok() {
        return nrPoddok;
    }

    public void setNrPoddok(Integer nrPoddok) {
        this.nrPoddok = nrPoddok;
    }

    public String getKodTytulu() {
        return kodTytulu;
    }

    public void setKodTytulu(String kodTytulu) {
        this.kodTytulu = kodTytulu;
    }

    public Character getAktualny() {
        return aktualny;
    }

    public void setAktualny(Character aktualny) {
        this.aktualny = aktualny;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Date getDataCzasUtworz() {
        return dataCzasUtworz;
    }

    public void setDataCzasUtworz(Date dataCzasUtworz) {
        this.dataCzasUtworz = dataCzasUtworz;
    }

    public Date getDataCzasZmiany() {
        return dataCzasZmiany;
    }

    public void setDataCzasZmiany(Date dataCzasZmiany) {
        this.dataCzasZmiany = dataCzasZmiany;
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
        if (!(object instanceof UbezpDgZdrowotna)) {
            return false;
        }
        UbezpDgZdrowotna other = (UbezpDgZdrowotna) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpDgZdrowotna[ id=" + id + " ]";
    }
    
}
