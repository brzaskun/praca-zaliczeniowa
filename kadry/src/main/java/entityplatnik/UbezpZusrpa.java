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
@Table(name = "UBEZP_ZUSRPA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpZusrpa.findAll", query = "SELECT u FROM UbezpZusrpa u"),
    @NamedQuery(name = "UbezpZusrpa.findByIdDokument", query = "SELECT u FROM UbezpZusrpa u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpZusrpa.findByIdPlatnik", query = "SELECT u FROM UbezpZusrpa u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpZusrpa.findByIdDokNad", query = "SELECT u FROM UbezpZusrpa u WHERE u.idDokNad = :idDokNad"),
    @NamedQuery(name = "UbezpZusrpa.findByIdUbezpieczony", query = "SELECT u FROM UbezpZusrpa u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiA1Nazwisko", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiA1Nazwisko = :iiiA1Nazwisko"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiA2Imiepierw", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiA2Imiepierw = :iiiA2Imiepierw"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiA3Typid", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiA3Typid = :iiiA3Typid"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiA4Identyfik", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiA4Identyfik = :iiiA4Identyfik"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiA51kodtytub", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiA51kodtytub = :iiiA51kodtytub"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiA52prdoem", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiA52prdoem = :iiiA52prdoem"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiA53stniep", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiA53stniep = :iiiA53stniep"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiB1Rok", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiB1Rok = :iiiB1Rok"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiB2Kwota", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiB2Kwota = :iiiB2Kwota"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiB3Rok", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiB3Rok = :iiiB3Rok"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiB4Kwota", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiB4Kwota = :iiiB4Kwota"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiB5Rok", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiB5Rok = :iiiB5Rok"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiB6Kwota", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiB6Kwota = :iiiB6Kwota"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiC1Rok", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiC1Rok = :iiiC1Rok"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiC2Kwota", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiC2Kwota = :iiiC2Kwota"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiC3Rok", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiC3Rok = :iiiC3Rok"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiC4Kwota", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiC4Kwota = :iiiC4Kwota"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiC5Rok", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiC5Rok = :iiiC5Rok"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiC6Kwota", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiC6Kwota = :iiiC6Kwota"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiD1Kwota", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiD1Kwota = :iiiD1Kwota"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiE1Rok", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiE1Rok = :iiiE1Rok"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiE2Kwota", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiE2Kwota = :iiiE2Kwota"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiE3Rok", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiE3Rok = :iiiE3Rok"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiE4Kwota", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiE4Kwota = :iiiE4Kwota"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiE5Rok", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiE5Rok = :iiiE5Rok"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiE6Kwota", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiE6Kwota = :iiiE6Kwota"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiF1Okresod", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiF1Okresod = :iiiF1Okresod"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiF2Okresdo", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiF2Okresdo = :iiiF2Okresdo"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiF31wymzajl", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiF31wymzajl = :iiiF31wymzajl"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiF32wymzajm", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiF32wymzajm = :iiiF32wymzajm"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiF4Okresod", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiF4Okresod = :iiiF4Okresod"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiF5Okresdo", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiF5Okresdo = :iiiF5Okresdo"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiF61wymzajl", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiF61wymzajl = :iiiF61wymzajl"),
    @NamedQuery(name = "UbezpZusrpa.findByIiiF62wymzajm", query = "SELECT u FROM UbezpZusrpa u WHERE u.iiiF62wymzajm = :iiiF62wymzajm"),
    @NamedQuery(name = "UbezpZusrpa.findByStatuswr", query = "SELECT u FROM UbezpZusrpa u WHERE u.statuswr = :statuswr"),
    @NamedQuery(name = "UbezpZusrpa.findByStatuspt", query = "SELECT u FROM UbezpZusrpa u WHERE u.statuspt = :statuspt"),
    @NamedQuery(name = "UbezpZusrpa.findByStatusws", query = "SELECT u FROM UbezpZusrpa u WHERE u.statusws = :statusws"),
    @NamedQuery(name = "UbezpZusrpa.findByInserttmp", query = "SELECT u FROM UbezpZusrpa u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpZusrpa.findByNrsciezwyl", query = "SELECT u FROM UbezpZusrpa u WHERE u.nrsciezwyl = :nrsciezwyl"),
    @NamedQuery(name = "UbezpZusrpa.findByStatuszus", query = "SELECT u FROM UbezpZusrpa u WHERE u.statuszus = :statuszus"),
    @NamedQuery(name = "UbezpZusrpa.findByStatusKontroli", query = "SELECT u FROM UbezpZusrpa u WHERE u.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "UbezpZusrpa.findByIdUbZusStatus", query = "SELECT u FROM UbezpZusrpa u WHERE u.idUbZusStatus = :idUbZusStatus"),
    @NamedQuery(name = "UbezpZusrpa.findByNrBlok", query = "SELECT u FROM UbezpZusrpa u WHERE u.nrBlok = :nrBlok")})
public class UbezpZusrpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOKUMENT", nullable = false)
    private Integer idDokument;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOK_NAD", nullable = false)
    private int idDokNad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
    @Size(max = 31)
    @Column(name = "III_A_1_NAZWISKO", length = 31)
    private String iiiA1Nazwisko;
    @Size(max = 22)
    @Column(name = "III_A_2_IMIEPIERW", length = 22)
    private String iiiA2Imiepierw;
    @Column(name = "III_A_3_TYPID")
    private Character iiiA3Typid;
    @Size(max = 11)
    @Column(name = "III_A_4_IDENTYFIK", length = 11)
    private String iiiA4Identyfik;
    @Size(max = 4)
    @Column(name = "III_A_5_1KODTYTUB", length = 4)
    private String iiiA51kodtytub;
    @Column(name = "III_A_5_2PRDOEM")
    private Character iiiA52prdoem;
    @Column(name = "III_A_5_3STNIEP")
    private Character iiiA53stniep;
    @Column(name = "III_B_1_ROK")
    private Short iiiB1Rok;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_B_2_KWOTA", precision = 8, scale = 2)
    private BigDecimal iiiB2Kwota;
    @Column(name = "III_B_3_ROK")
    private Short iiiB3Rok;
    @Column(name = "III_B_4_KWOTA", precision = 8, scale = 2)
    private BigDecimal iiiB4Kwota;
    @Column(name = "III_B_5_ROK")
    private Short iiiB5Rok;
    @Column(name = "III_B_6_KWOTA", precision = 8, scale = 2)
    private BigDecimal iiiB6Kwota;
    @Column(name = "III_C_1_ROK")
    private Short iiiC1Rok;
    @Column(name = "III_C_2_KWOTA", precision = 8, scale = 2)
    private BigDecimal iiiC2Kwota;
    @Column(name = "III_C_3_ROK")
    private Short iiiC3Rok;
    @Column(name = "III_C_4_KWOTA", precision = 8, scale = 2)
    private BigDecimal iiiC4Kwota;
    @Column(name = "III_C_5_ROK")
    private Short iiiC5Rok;
    @Column(name = "III_C_6_KWOTA", precision = 8, scale = 2)
    private BigDecimal iiiC6Kwota;
    @Column(name = "III_D_1_KWOTA", precision = 8, scale = 2)
    private BigDecimal iiiD1Kwota;
    @Column(name = "III_E_1_ROK")
    private Short iiiE1Rok;
    @Column(name = "III_E_2_KWOTA", precision = 8, scale = 2)
    private BigDecimal iiiE2Kwota;
    @Column(name = "III_E_3_ROK")
    private Short iiiE3Rok;
    @Column(name = "III_E_4_KWOTA", precision = 8, scale = 2)
    private BigDecimal iiiE4Kwota;
    @Column(name = "III_E_5_ROK")
    private Short iiiE5Rok;
    @Column(name = "III_E_6_KWOTA", precision = 8, scale = 2)
    private BigDecimal iiiE6Kwota;
    @Column(name = "III_F_1_OKRESOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiF1Okresod;
    @Column(name = "III_F_2_OKRESDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiF2Okresdo;
    @Size(max = 3)
    @Column(name = "III_F_3_1WYMZAJL", length = 3)
    private String iiiF31wymzajl;
    @Size(max = 3)
    @Column(name = "III_F_3_2WYMZAJM", length = 3)
    private String iiiF32wymzajm;
    @Column(name = "III_F_4_OKRESOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiF4Okresod;
    @Column(name = "III_F_5_OKRESDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiF5Okresdo;
    @Size(max = 3)
    @Column(name = "III_F_6_1WYMZAJL", length = 3)
    private String iiiF61wymzajl;
    @Size(max = 3)
    @Column(name = "III_F_6_2WYMZAJM", length = 3)
    private String iiiF62wymzajm;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUSWS", nullable = false)
    private int statusws;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "NRSCIEZWYL")
    private Integer nrsciezwyl;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_UB_ZUS_STATUS")
    private Character idUbZusStatus;
    @Column(name = "NR_BLOK")
    private Integer nrBlok;

    public UbezpZusrpa() {
    }

    public UbezpZusrpa(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public UbezpZusrpa(Integer idDokument, int idPlatnik, int idDokNad, int idUbezpieczony, int statusws) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
        this.idDokNad = idDokNad;
        this.idUbezpieczony = idUbezpieczony;
        this.statusws = statusws;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public int getIdDokNad() {
        return idDokNad;
    }

    public void setIdDokNad(int idDokNad) {
        this.idDokNad = idDokNad;
    }

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public String getIiiA1Nazwisko() {
        return iiiA1Nazwisko;
    }

    public void setIiiA1Nazwisko(String iiiA1Nazwisko) {
        this.iiiA1Nazwisko = iiiA1Nazwisko;
    }

    public String getIiiA2Imiepierw() {
        return iiiA2Imiepierw;
    }

    public void setIiiA2Imiepierw(String iiiA2Imiepierw) {
        this.iiiA2Imiepierw = iiiA2Imiepierw;
    }

    public Character getIiiA3Typid() {
        return iiiA3Typid;
    }

    public void setIiiA3Typid(Character iiiA3Typid) {
        this.iiiA3Typid = iiiA3Typid;
    }

    public String getIiiA4Identyfik() {
        return iiiA4Identyfik;
    }

    public void setIiiA4Identyfik(String iiiA4Identyfik) {
        this.iiiA4Identyfik = iiiA4Identyfik;
    }

    public String getIiiA51kodtytub() {
        return iiiA51kodtytub;
    }

    public void setIiiA51kodtytub(String iiiA51kodtytub) {
        this.iiiA51kodtytub = iiiA51kodtytub;
    }

    public Character getIiiA52prdoem() {
        return iiiA52prdoem;
    }

    public void setIiiA52prdoem(Character iiiA52prdoem) {
        this.iiiA52prdoem = iiiA52prdoem;
    }

    public Character getIiiA53stniep() {
        return iiiA53stniep;
    }

    public void setIiiA53stniep(Character iiiA53stniep) {
        this.iiiA53stniep = iiiA53stniep;
    }

    public Short getIiiB1Rok() {
        return iiiB1Rok;
    }

    public void setIiiB1Rok(Short iiiB1Rok) {
        this.iiiB1Rok = iiiB1Rok;
    }

    public BigDecimal getIiiB2Kwota() {
        return iiiB2Kwota;
    }

    public void setIiiB2Kwota(BigDecimal iiiB2Kwota) {
        this.iiiB2Kwota = iiiB2Kwota;
    }

    public Short getIiiB3Rok() {
        return iiiB3Rok;
    }

    public void setIiiB3Rok(Short iiiB3Rok) {
        this.iiiB3Rok = iiiB3Rok;
    }

    public BigDecimal getIiiB4Kwota() {
        return iiiB4Kwota;
    }

    public void setIiiB4Kwota(BigDecimal iiiB4Kwota) {
        this.iiiB4Kwota = iiiB4Kwota;
    }

    public Short getIiiB5Rok() {
        return iiiB5Rok;
    }

    public void setIiiB5Rok(Short iiiB5Rok) {
        this.iiiB5Rok = iiiB5Rok;
    }

    public BigDecimal getIiiB6Kwota() {
        return iiiB6Kwota;
    }

    public void setIiiB6Kwota(BigDecimal iiiB6Kwota) {
        this.iiiB6Kwota = iiiB6Kwota;
    }

    public Short getIiiC1Rok() {
        return iiiC1Rok;
    }

    public void setIiiC1Rok(Short iiiC1Rok) {
        this.iiiC1Rok = iiiC1Rok;
    }

    public BigDecimal getIiiC2Kwota() {
        return iiiC2Kwota;
    }

    public void setIiiC2Kwota(BigDecimal iiiC2Kwota) {
        this.iiiC2Kwota = iiiC2Kwota;
    }

    public Short getIiiC3Rok() {
        return iiiC3Rok;
    }

    public void setIiiC3Rok(Short iiiC3Rok) {
        this.iiiC3Rok = iiiC3Rok;
    }

    public BigDecimal getIiiC4Kwota() {
        return iiiC4Kwota;
    }

    public void setIiiC4Kwota(BigDecimal iiiC4Kwota) {
        this.iiiC4Kwota = iiiC4Kwota;
    }

    public Short getIiiC5Rok() {
        return iiiC5Rok;
    }

    public void setIiiC5Rok(Short iiiC5Rok) {
        this.iiiC5Rok = iiiC5Rok;
    }

    public BigDecimal getIiiC6Kwota() {
        return iiiC6Kwota;
    }

    public void setIiiC6Kwota(BigDecimal iiiC6Kwota) {
        this.iiiC6Kwota = iiiC6Kwota;
    }

    public BigDecimal getIiiD1Kwota() {
        return iiiD1Kwota;
    }

    public void setIiiD1Kwota(BigDecimal iiiD1Kwota) {
        this.iiiD1Kwota = iiiD1Kwota;
    }

    public Short getIiiE1Rok() {
        return iiiE1Rok;
    }

    public void setIiiE1Rok(Short iiiE1Rok) {
        this.iiiE1Rok = iiiE1Rok;
    }

    public BigDecimal getIiiE2Kwota() {
        return iiiE2Kwota;
    }

    public void setIiiE2Kwota(BigDecimal iiiE2Kwota) {
        this.iiiE2Kwota = iiiE2Kwota;
    }

    public Short getIiiE3Rok() {
        return iiiE3Rok;
    }

    public void setIiiE3Rok(Short iiiE3Rok) {
        this.iiiE3Rok = iiiE3Rok;
    }

    public BigDecimal getIiiE4Kwota() {
        return iiiE4Kwota;
    }

    public void setIiiE4Kwota(BigDecimal iiiE4Kwota) {
        this.iiiE4Kwota = iiiE4Kwota;
    }

    public Short getIiiE5Rok() {
        return iiiE5Rok;
    }

    public void setIiiE5Rok(Short iiiE5Rok) {
        this.iiiE5Rok = iiiE5Rok;
    }

    public BigDecimal getIiiE6Kwota() {
        return iiiE6Kwota;
    }

    public void setIiiE6Kwota(BigDecimal iiiE6Kwota) {
        this.iiiE6Kwota = iiiE6Kwota;
    }

    public Date getIiiF1Okresod() {
        return iiiF1Okresod;
    }

    public void setIiiF1Okresod(Date iiiF1Okresod) {
        this.iiiF1Okresod = iiiF1Okresod;
    }

    public Date getIiiF2Okresdo() {
        return iiiF2Okresdo;
    }

    public void setIiiF2Okresdo(Date iiiF2Okresdo) {
        this.iiiF2Okresdo = iiiF2Okresdo;
    }

    public String getIiiF31wymzajl() {
        return iiiF31wymzajl;
    }

    public void setIiiF31wymzajl(String iiiF31wymzajl) {
        this.iiiF31wymzajl = iiiF31wymzajl;
    }

    public String getIiiF32wymzajm() {
        return iiiF32wymzajm;
    }

    public void setIiiF32wymzajm(String iiiF32wymzajm) {
        this.iiiF32wymzajm = iiiF32wymzajm;
    }

    public Date getIiiF4Okresod() {
        return iiiF4Okresod;
    }

    public void setIiiF4Okresod(Date iiiF4Okresod) {
        this.iiiF4Okresod = iiiF4Okresod;
    }

    public Date getIiiF5Okresdo() {
        return iiiF5Okresdo;
    }

    public void setIiiF5Okresdo(Date iiiF5Okresdo) {
        this.iiiF5Okresdo = iiiF5Okresdo;
    }

    public String getIiiF61wymzajl() {
        return iiiF61wymzajl;
    }

    public void setIiiF61wymzajl(String iiiF61wymzajl) {
        this.iiiF61wymzajl = iiiF61wymzajl;
    }

    public String getIiiF62wymzajm() {
        return iiiF62wymzajm;
    }

    public void setIiiF62wymzajm(String iiiF62wymzajm) {
        this.iiiF62wymzajm = iiiF62wymzajm;
    }

    public Character getStatuswr() {
        return statuswr;
    }

    public void setStatuswr(Character statuswr) {
        this.statuswr = statuswr;
    }

    public Character getStatuspt() {
        return statuspt;
    }

    public void setStatuspt(Character statuspt) {
        this.statuspt = statuspt;
    }

    public int getStatusws() {
        return statusws;
    }

    public void setStatusws(int statusws) {
        this.statusws = statusws;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getNrsciezwyl() {
        return nrsciezwyl;
    }

    public void setNrsciezwyl(Integer nrsciezwyl) {
        this.nrsciezwyl = nrsciezwyl;
    }

    public Character getStatuszus() {
        return statuszus;
    }

    public void setStatuszus(Character statuszus) {
        this.statuszus = statuszus;
    }

    public Character getStatusKontroli() {
        return statusKontroli;
    }

    public void setStatusKontroli(Character statusKontroli) {
        this.statusKontroli = statusKontroli;
    }

    public Character getIdUbZusStatus() {
        return idUbZusStatus;
    }

    public void setIdUbZusStatus(Character idUbZusStatus) {
        this.idUbZusStatus = idUbZusStatus;
    }

    public Integer getNrBlok() {
        return nrBlok;
    }

    public void setNrBlok(Integer nrBlok) {
        this.nrBlok = nrBlok;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDokument != null ? idDokument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UbezpZusrpa)) {
            return false;
        }
        UbezpZusrpa other = (UbezpZusrpa) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpZusrpa[ idDokument=" + idDokument + " ]";
    }
    
}
