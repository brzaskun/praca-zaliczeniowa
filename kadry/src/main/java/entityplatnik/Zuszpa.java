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
@Table(name = "ZUSZPA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zuszpa.findAll", query = "SELECT z FROM Zuszpa z"),
    @NamedQuery(name = "Zuszpa.findByIdDokument", query = "SELECT z FROM Zuszpa z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zuszpa.findByIdPlatnik", query = "SELECT z FROM Zuszpa z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zuszpa.findByI1Zglplsklade", query = "SELECT z FROM Zuszpa z WHERE z.i1Zglplsklade = :i1Zglplsklade"),
    @NamedQuery(name = "Zuszpa.findByI2Zglzmdaplatnik", query = "SELECT z FROM Zuszpa z WHERE z.i2Zglzmdaplatnik = :i2Zglzmdaplatnik"),
    @NamedQuery(name = "Zuszpa.findByI3Datanadania", query = "SELECT z FROM Zuszpa z WHERE z.i3Datanadania = :i3Datanadania"),
    @NamedQuery(name = "Zuszpa.findByI4Nalepkar", query = "SELECT z FROM Zuszpa z WHERE z.i4Nalepkar = :i4Nalepkar"),
    @NamedQuery(name = "Zuszpa.findByIi1Nip", query = "SELECT z FROM Zuszpa z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zuszpa.findByIi2Regon", query = "SELECT z FROM Zuszpa z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zuszpa.findByIi3Nazwaskr", query = "SELECT z FROM Zuszpa z WHERE z.ii3Nazwaskr = :ii3Nazwaskr"),
    @NamedQuery(name = "Zuszpa.findByIii1Nazwafirma", query = "SELECT z FROM Zuszpa z WHERE z.iii1Nazwafirma = :iii1Nazwafirma"),
    @NamedQuery(name = "Zuszpa.findByIii2Pljestjedbud", query = "SELECT z FROM Zuszpa z WHERE z.iii2Pljestjedbud = :iii2Pljestjedbud"),
    @NamedQuery(name = "Zuszpa.findByIii3Pljedpozabu", query = "SELECT z FROM Zuszpa z WHERE z.iii3Pljedpozabu = :iii3Pljedpozabu"),
    @NamedQuery(name = "Zuszpa.findByIii4Norganuzaloz", query = "SELECT z FROM Zuszpa z WHERE z.iii4Norganuzaloz = :iii4Norganuzaloz"),
    @NamedQuery(name = "Zuszpa.findByIii5Plpodlwpisew", query = "SELECT z FROM Zuszpa z WHERE z.iii5Plpodlwpisew = :iii5Plpodlwpisew"),
    @NamedQuery(name = "Zuszpa.findByIii6Datawprejew", query = "SELECT z FROM Zuszpa z WHERE z.iii6Datawprejew = :iii6Datawprejew"),
    @NamedQuery(name = "Zuszpa.findByIii7Nrwpisrejew", query = "SELECT z FROM Zuszpa z WHERE z.iii7Nrwpisrejew = :iii7Nrwpisrejew"),
    @NamedQuery(name = "Zuszpa.findByIii8Norganurejew", query = "SELECT z FROM Zuszpa z WHERE z.iii8Norganurejew = :iii8Norganurejew"),
    @NamedQuery(name = "Zuszpa.findByIii9Datapowobub", query = "SELECT z FROM Zuszpa z WHERE z.iii9Datapowobub = :iii9Datapowobub"),
    @NamedQuery(name = "Zuszpa.findByIii10Datardzial", query = "SELECT z FROM Zuszpa z WHERE z.iii10Datardzial = :iii10Datardzial"),
    @NamedQuery(name = "Zuszpa.findByIv1Nrrachunku", query = "SELECT z FROM Zuszpa z WHERE z.iv1Nrrachunku = :iv1Nrrachunku"),
    @NamedQuery(name = "Zuszpa.findByIv2Czyinnerach", query = "SELECT z FROM Zuszpa z WHERE z.iv2Czyinnerach = :iv2Czyinnerach"),
    @NamedQuery(name = "Zuszpa.findByV1Plmastprchr", query = "SELECT z FROM Zuszpa z WHERE z.v1Plmastprchr = :v1Plmastprchr"),
    @NamedQuery(name = "Zuszpa.findByV2Dataotstpchr", query = "SELECT z FROM Zuszpa z WHERE z.v2Dataotstpchr = :v2Dataotstpchr"),
    @NamedQuery(name = "Zuszpa.findByV3Datautrstprch", query = "SELECT z FROM Zuszpa z WHERE z.v3Datautrstprch = :v3Datautrstprch"),
    @NamedQuery(name = "Zuszpa.findByV4Adrdzialnsied", query = "SELECT z FROM Zuszpa z WHERE z.v4Adrdzialnsied = :v4Adrdzialnsied"),
    @NamedQuery(name = "Zuszpa.findByVi1Kodpocztowy", query = "SELECT z FROM Zuszpa z WHERE z.vi1Kodpocztowy = :vi1Kodpocztowy"),
    @NamedQuery(name = "Zuszpa.findByVi2Miejscowosc", query = "SELECT z FROM Zuszpa z WHERE z.vi2Miejscowosc = :vi2Miejscowosc"),
    @NamedQuery(name = "Zuszpa.findByVi3Gmina", query = "SELECT z FROM Zuszpa z WHERE z.vi3Gmina = :vi3Gmina"),
    @NamedQuery(name = "Zuszpa.findByVi4Ulica", query = "SELECT z FROM Zuszpa z WHERE z.vi4Ulica = :vi4Ulica"),
    @NamedQuery(name = "Zuszpa.findByVi5Numerdomu", query = "SELECT z FROM Zuszpa z WHERE z.vi5Numerdomu = :vi5Numerdomu"),
    @NamedQuery(name = "Zuszpa.findByVi6Numerlokalu", query = "SELECT z FROM Zuszpa z WHERE z.vi6Numerlokalu = :vi6Numerlokalu"),
    @NamedQuery(name = "Zuszpa.findByVi7Telefon", query = "SELECT z FROM Zuszpa z WHERE z.vi7Telefon = :vi7Telefon"),
    @NamedQuery(name = "Zuszpa.findByVi8Faks", query = "SELECT z FROM Zuszpa z WHERE z.vi8Faks = :vi8Faks"),
    @NamedQuery(name = "Zuszpa.findByVi9Adrpocztyel", query = "SELECT z FROM Zuszpa z WHERE z.vi9Adrpocztyel = :vi9Adrpocztyel"),
    @NamedQuery(name = "Zuszpa.findByVii1Kodpocztowy", query = "SELECT z FROM Zuszpa z WHERE z.vii1Kodpocztowy = :vii1Kodpocztowy"),
    @NamedQuery(name = "Zuszpa.findByVii2Miejscowosc", query = "SELECT z FROM Zuszpa z WHERE z.vii2Miejscowosc = :vii2Miejscowosc"),
    @NamedQuery(name = "Zuszpa.findByVii3Ulica", query = "SELECT z FROM Zuszpa z WHERE z.vii3Ulica = :vii3Ulica"),
    @NamedQuery(name = "Zuszpa.findByVii4Numerdomu", query = "SELECT z FROM Zuszpa z WHERE z.vii4Numerdomu = :vii4Numerdomu"),
    @NamedQuery(name = "Zuszpa.findByVii5Numerlokalu", query = "SELECT z FROM Zuszpa z WHERE z.vii5Numerlokalu = :vii5Numerlokalu"),
    @NamedQuery(name = "Zuszpa.findByVii6Teldoteletr", query = "SELECT z FROM Zuszpa z WHERE z.vii6Teldoteletr = :vii6Teldoteletr"),
    @NamedQuery(name = "Zuszpa.findByVii7Skrpocztowa", query = "SELECT z FROM Zuszpa z WHERE z.vii7Skrpocztowa = :vii7Skrpocztowa"),
    @NamedQuery(name = "Zuszpa.findByVii8Telefon", query = "SELECT z FROM Zuszpa z WHERE z.vii8Telefon = :vii8Telefon"),
    @NamedQuery(name = "Zuszpa.findByVii9Faks", query = "SELECT z FROM Zuszpa z WHERE z.vii9Faks = :vii9Faks"),
    @NamedQuery(name = "Zuszpa.findByVii10Adrpocztyel", query = "SELECT z FROM Zuszpa z WHERE z.vii10Adrpocztyel = :vii10Adrpocztyel"),
    @NamedQuery(name = "Zuszpa.findByViii1Nip", query = "SELECT z FROM Zuszpa z WHERE z.viii1Nip = :viii1Nip"),
    @NamedQuery(name = "Zuszpa.findByViii2Regon", query = "SELECT z FROM Zuszpa z WHERE z.viii2Regon = :viii2Regon"),
    @NamedQuery(name = "Zuszpa.findByViii3Nazwaskr", query = "SELECT z FROM Zuszpa z WHERE z.viii3Nazwaskr = :viii3Nazwaskr"),
    @NamedQuery(name = "Zuszpa.findByIx1Lzalzba", query = "SELECT z FROM Zuszpa z WHERE z.ix1Lzalzba = :ix1Lzalzba"),
    @NamedQuery(name = "Zuszpa.findByIx2Lzalzaa", query = "SELECT z FROM Zuszpa z WHERE z.ix2Lzalzaa = :ix2Lzalzaa"),
    @NamedQuery(name = "Zuszpa.findByIx3Datawypel", query = "SELECT z FROM Zuszpa z WHERE z.ix3Datawypel = :ix3Datawypel"),
    @NamedQuery(name = "Zuszpa.findByStatuswr", query = "SELECT z FROM Zuszpa z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zuszpa.findByStatuspt", query = "SELECT z FROM Zuszpa z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zuszpa.findByInserttmp", query = "SELECT z FROM Zuszpa z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zuszpa.findBySeria", query = "SELECT z FROM Zuszpa z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zuszpa.findByStatuszus", query = "SELECT z FROM Zuszpa z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zuszpa.findByStatusKontroli", query = "SELECT z FROM Zuszpa z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zuszpa.findByIdPlZusStatus", query = "SELECT z FROM Zuszpa z WHERE z.idPlZusStatus = :idPlZusStatus")})
public class Zuszpa implements Serializable {

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
    @Column(name = "I_1_ZGLPLSKLADE")
    private Character i1Zglplsklade;
    @Column(name = "I_2_ZGLZMDAPLATNIK")
    private Character i2Zglzmdaplatnik;
    @Column(name = "I_3_DATANADANIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i3Datanadania;
    @Size(max = 20)
    @Column(name = "I_4_NALEPKAR", length = 20)
    private String i4Nalepkar;
    @Size(max = 10)
    @Column(name = "II_1_NIP", length = 10)
    private String ii1Nip;
    @Size(max = 14)
    @Column(name = "II_2_REGON", length = 14)
    private String ii2Regon;
    @Size(max = 31)
    @Column(name = "II_3_NAZWASKR", length = 31)
    private String ii3Nazwaskr;
    @Size(max = 62)
    @Column(name = "III_1_NAZWAFIRMA", length = 62)
    private String iii1Nazwafirma;
    @Column(name = "III_2_PLJESTJEDBUD")
    private Character iii2Pljestjedbud;
    @Column(name = "III_3_PLJEDPOZABU")
    private Character iii3Pljedpozabu;
    @Size(max = 31)
    @Column(name = "III_4_NORGANUZALOZ", length = 31)
    private String iii4Norganuzaloz;
    @Column(name = "III_5_PLPODLWPISEW")
    private Character iii5Plpodlwpisew;
    @Column(name = "III_6_DATAWPREJEW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iii6Datawprejew;
    @Size(max = 15)
    @Column(name = "III_7_NRWPISREJEW", length = 15)
    private String iii7Nrwpisrejew;
    @Size(max = 72)
    @Column(name = "III_8_NORGANUREJEW", length = 72)
    private String iii8Norganurejew;
    @Column(name = "III_9_DATAPOWOBUB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iii9Datapowobub;
    @Column(name = "III_10_DATARDZIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iii10Datardzial;
    @Size(max = 36)
    @Column(name = "IV_1_NRRACHUNKU", length = 36)
    private String iv1Nrrachunku;
    @Column(name = "IV_2_CZYINNERACH")
    private Character iv2Czyinnerach;
    @Column(name = "V_1_PLMASTPRCHR")
    private Character v1Plmastprchr;
    @Column(name = "V_2_DATAOTSTPCHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date v2Dataotstpchr;
    @Column(name = "V_3_DATAUTRSTPRCH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date v3Datautrstprch;
    @Column(name = "V_4_ADRDZIALNSIED")
    private Character v4Adrdzialnsied;
    @Size(max = 5)
    @Column(name = "VI_1_KODPOCZTOWY", length = 5)
    private String vi1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "VI_2_MIEJSCOWOSC", length = 26)
    private String vi2Miejscowosc;
    @Size(max = 26)
    @Column(name = "VI_3_GMINA", length = 26)
    private String vi3Gmina;
    @Size(max = 30)
    @Column(name = "VI_4_ULICA", length = 30)
    private String vi4Ulica;
    @Size(max = 7)
    @Column(name = "VI_5_NUMERDOMU", length = 7)
    private String vi5Numerdomu;
    @Size(max = 7)
    @Column(name = "VI_6_NUMERLOKALU", length = 7)
    private String vi6Numerlokalu;
    @Size(max = 12)
    @Column(name = "VI_7_TELEFON", length = 12)
    private String vi7Telefon;
    @Size(max = 12)
    @Column(name = "VI_8_FAKS", length = 12)
    private String vi8Faks;
    @Size(max = 30)
    @Column(name = "VI_9_ADRPOCZTYEL", length = 30)
    private String vi9Adrpocztyel;
    @Size(max = 5)
    @Column(name = "VII_1_KODPOCZTOWY", length = 5)
    private String vii1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "VII_2_MIEJSCOWOSC", length = 26)
    private String vii2Miejscowosc;
    @Size(max = 30)
    @Column(name = "VII_3_ULICA", length = 30)
    private String vii3Ulica;
    @Size(max = 7)
    @Column(name = "VII_4_NUMERDOMU", length = 7)
    private String vii4Numerdomu;
    @Size(max = 7)
    @Column(name = "VII_5_NUMERLOKALU", length = 7)
    private String vii5Numerlokalu;
    @Size(max = 12)
    @Column(name = "VII_6_TELDOTELETR", length = 12)
    private String vii6Teldoteletr;
    @Size(max = 5)
    @Column(name = "VII_7_SKRPOCZTOWA", length = 5)
    private String vii7Skrpocztowa;
    @Size(max = 12)
    @Column(name = "VII_8_TELEFON", length = 12)
    private String vii8Telefon;
    @Size(max = 12)
    @Column(name = "VII_9_FAKS", length = 12)
    private String vii9Faks;
    @Size(max = 30)
    @Column(name = "VII_10_ADRPOCZTYEL", length = 30)
    private String vii10Adrpocztyel;
    @Size(max = 10)
    @Column(name = "VIII_1_NIP", length = 10)
    private String viii1Nip;
    @Size(max = 14)
    @Column(name = "VIII_2_REGON", length = 14)
    private String viii2Regon;
    @Size(max = 31)
    @Column(name = "VIII_3_NAZWASKR", length = 31)
    private String viii3Nazwaskr;
    @Size(max = 3)
    @Column(name = "IX_1_LZALZBA", length = 3)
    private String ix1Lzalzba;
    @Size(max = 3)
    @Column(name = "IX_2_LZALZAA", length = 3)
    private String ix2Lzalzaa;
    @Column(name = "IX_3_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ix3Datawypel;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "SERIA")
    private Integer seria;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_PL_ZUS_STATUS")
    private Character idPlZusStatus;

    public Zuszpa() {
    }

    public Zuszpa(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zuszpa(Integer idDokument, int idPlatnik) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
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

    public Character getI1Zglplsklade() {
        return i1Zglplsklade;
    }

    public void setI1Zglplsklade(Character i1Zglplsklade) {
        this.i1Zglplsklade = i1Zglplsklade;
    }

    public Character getI2Zglzmdaplatnik() {
        return i2Zglzmdaplatnik;
    }

    public void setI2Zglzmdaplatnik(Character i2Zglzmdaplatnik) {
        this.i2Zglzmdaplatnik = i2Zglzmdaplatnik;
    }

    public Date getI3Datanadania() {
        return i3Datanadania;
    }

    public void setI3Datanadania(Date i3Datanadania) {
        this.i3Datanadania = i3Datanadania;
    }

    public String getI4Nalepkar() {
        return i4Nalepkar;
    }

    public void setI4Nalepkar(String i4Nalepkar) {
        this.i4Nalepkar = i4Nalepkar;
    }

    public String getIi1Nip() {
        return ii1Nip;
    }

    public void setIi1Nip(String ii1Nip) {
        this.ii1Nip = ii1Nip;
    }

    public String getIi2Regon() {
        return ii2Regon;
    }

    public void setIi2Regon(String ii2Regon) {
        this.ii2Regon = ii2Regon;
    }

    public String getIi3Nazwaskr() {
        return ii3Nazwaskr;
    }

    public void setIi3Nazwaskr(String ii3Nazwaskr) {
        this.ii3Nazwaskr = ii3Nazwaskr;
    }

    public String getIii1Nazwafirma() {
        return iii1Nazwafirma;
    }

    public void setIii1Nazwafirma(String iii1Nazwafirma) {
        this.iii1Nazwafirma = iii1Nazwafirma;
    }

    public Character getIii2Pljestjedbud() {
        return iii2Pljestjedbud;
    }

    public void setIii2Pljestjedbud(Character iii2Pljestjedbud) {
        this.iii2Pljestjedbud = iii2Pljestjedbud;
    }

    public Character getIii3Pljedpozabu() {
        return iii3Pljedpozabu;
    }

    public void setIii3Pljedpozabu(Character iii3Pljedpozabu) {
        this.iii3Pljedpozabu = iii3Pljedpozabu;
    }

    public String getIii4Norganuzaloz() {
        return iii4Norganuzaloz;
    }

    public void setIii4Norganuzaloz(String iii4Norganuzaloz) {
        this.iii4Norganuzaloz = iii4Norganuzaloz;
    }

    public Character getIii5Plpodlwpisew() {
        return iii5Plpodlwpisew;
    }

    public void setIii5Plpodlwpisew(Character iii5Plpodlwpisew) {
        this.iii5Plpodlwpisew = iii5Plpodlwpisew;
    }

    public Date getIii6Datawprejew() {
        return iii6Datawprejew;
    }

    public void setIii6Datawprejew(Date iii6Datawprejew) {
        this.iii6Datawprejew = iii6Datawprejew;
    }

    public String getIii7Nrwpisrejew() {
        return iii7Nrwpisrejew;
    }

    public void setIii7Nrwpisrejew(String iii7Nrwpisrejew) {
        this.iii7Nrwpisrejew = iii7Nrwpisrejew;
    }

    public String getIii8Norganurejew() {
        return iii8Norganurejew;
    }

    public void setIii8Norganurejew(String iii8Norganurejew) {
        this.iii8Norganurejew = iii8Norganurejew;
    }

    public Date getIii9Datapowobub() {
        return iii9Datapowobub;
    }

    public void setIii9Datapowobub(Date iii9Datapowobub) {
        this.iii9Datapowobub = iii9Datapowobub;
    }

    public Date getIii10Datardzial() {
        return iii10Datardzial;
    }

    public void setIii10Datardzial(Date iii10Datardzial) {
        this.iii10Datardzial = iii10Datardzial;
    }

    public String getIv1Nrrachunku() {
        return iv1Nrrachunku;
    }

    public void setIv1Nrrachunku(String iv1Nrrachunku) {
        this.iv1Nrrachunku = iv1Nrrachunku;
    }

    public Character getIv2Czyinnerach() {
        return iv2Czyinnerach;
    }

    public void setIv2Czyinnerach(Character iv2Czyinnerach) {
        this.iv2Czyinnerach = iv2Czyinnerach;
    }

    public Character getV1Plmastprchr() {
        return v1Plmastprchr;
    }

    public void setV1Plmastprchr(Character v1Plmastprchr) {
        this.v1Plmastprchr = v1Plmastprchr;
    }

    public Date getV2Dataotstpchr() {
        return v2Dataotstpchr;
    }

    public void setV2Dataotstpchr(Date v2Dataotstpchr) {
        this.v2Dataotstpchr = v2Dataotstpchr;
    }

    public Date getV3Datautrstprch() {
        return v3Datautrstprch;
    }

    public void setV3Datautrstprch(Date v3Datautrstprch) {
        this.v3Datautrstprch = v3Datautrstprch;
    }

    public Character getV4Adrdzialnsied() {
        return v4Adrdzialnsied;
    }

    public void setV4Adrdzialnsied(Character v4Adrdzialnsied) {
        this.v4Adrdzialnsied = v4Adrdzialnsied;
    }

    public String getVi1Kodpocztowy() {
        return vi1Kodpocztowy;
    }

    public void setVi1Kodpocztowy(String vi1Kodpocztowy) {
        this.vi1Kodpocztowy = vi1Kodpocztowy;
    }

    public String getVi2Miejscowosc() {
        return vi2Miejscowosc;
    }

    public void setVi2Miejscowosc(String vi2Miejscowosc) {
        this.vi2Miejscowosc = vi2Miejscowosc;
    }

    public String getVi3Gmina() {
        return vi3Gmina;
    }

    public void setVi3Gmina(String vi3Gmina) {
        this.vi3Gmina = vi3Gmina;
    }

    public String getVi4Ulica() {
        return vi4Ulica;
    }

    public void setVi4Ulica(String vi4Ulica) {
        this.vi4Ulica = vi4Ulica;
    }

    public String getVi5Numerdomu() {
        return vi5Numerdomu;
    }

    public void setVi5Numerdomu(String vi5Numerdomu) {
        this.vi5Numerdomu = vi5Numerdomu;
    }

    public String getVi6Numerlokalu() {
        return vi6Numerlokalu;
    }

    public void setVi6Numerlokalu(String vi6Numerlokalu) {
        this.vi6Numerlokalu = vi6Numerlokalu;
    }

    public String getVi7Telefon() {
        return vi7Telefon;
    }

    public void setVi7Telefon(String vi7Telefon) {
        this.vi7Telefon = vi7Telefon;
    }

    public String getVi8Faks() {
        return vi8Faks;
    }

    public void setVi8Faks(String vi8Faks) {
        this.vi8Faks = vi8Faks;
    }

    public String getVi9Adrpocztyel() {
        return vi9Adrpocztyel;
    }

    public void setVi9Adrpocztyel(String vi9Adrpocztyel) {
        this.vi9Adrpocztyel = vi9Adrpocztyel;
    }

    public String getVii1Kodpocztowy() {
        return vii1Kodpocztowy;
    }

    public void setVii1Kodpocztowy(String vii1Kodpocztowy) {
        this.vii1Kodpocztowy = vii1Kodpocztowy;
    }

    public String getVii2Miejscowosc() {
        return vii2Miejscowosc;
    }

    public void setVii2Miejscowosc(String vii2Miejscowosc) {
        this.vii2Miejscowosc = vii2Miejscowosc;
    }

    public String getVii3Ulica() {
        return vii3Ulica;
    }

    public void setVii3Ulica(String vii3Ulica) {
        this.vii3Ulica = vii3Ulica;
    }

    public String getVii4Numerdomu() {
        return vii4Numerdomu;
    }

    public void setVii4Numerdomu(String vii4Numerdomu) {
        this.vii4Numerdomu = vii4Numerdomu;
    }

    public String getVii5Numerlokalu() {
        return vii5Numerlokalu;
    }

    public void setVii5Numerlokalu(String vii5Numerlokalu) {
        this.vii5Numerlokalu = vii5Numerlokalu;
    }

    public String getVii6Teldoteletr() {
        return vii6Teldoteletr;
    }

    public void setVii6Teldoteletr(String vii6Teldoteletr) {
        this.vii6Teldoteletr = vii6Teldoteletr;
    }

    public String getVii7Skrpocztowa() {
        return vii7Skrpocztowa;
    }

    public void setVii7Skrpocztowa(String vii7Skrpocztowa) {
        this.vii7Skrpocztowa = vii7Skrpocztowa;
    }

    public String getVii8Telefon() {
        return vii8Telefon;
    }

    public void setVii8Telefon(String vii8Telefon) {
        this.vii8Telefon = vii8Telefon;
    }

    public String getVii9Faks() {
        return vii9Faks;
    }

    public void setVii9Faks(String vii9Faks) {
        this.vii9Faks = vii9Faks;
    }

    public String getVii10Adrpocztyel() {
        return vii10Adrpocztyel;
    }

    public void setVii10Adrpocztyel(String vii10Adrpocztyel) {
        this.vii10Adrpocztyel = vii10Adrpocztyel;
    }

    public String getViii1Nip() {
        return viii1Nip;
    }

    public void setViii1Nip(String viii1Nip) {
        this.viii1Nip = viii1Nip;
    }

    public String getViii2Regon() {
        return viii2Regon;
    }

    public void setViii2Regon(String viii2Regon) {
        this.viii2Regon = viii2Regon;
    }

    public String getViii3Nazwaskr() {
        return viii3Nazwaskr;
    }

    public void setViii3Nazwaskr(String viii3Nazwaskr) {
        this.viii3Nazwaskr = viii3Nazwaskr;
    }

    public String getIx1Lzalzba() {
        return ix1Lzalzba;
    }

    public void setIx1Lzalzba(String ix1Lzalzba) {
        this.ix1Lzalzba = ix1Lzalzba;
    }

    public String getIx2Lzalzaa() {
        return ix2Lzalzaa;
    }

    public void setIx2Lzalzaa(String ix2Lzalzaa) {
        this.ix2Lzalzaa = ix2Lzalzaa;
    }

    public Date getIx3Datawypel() {
        return ix3Datawypel;
    }

    public void setIx3Datawypel(Date ix3Datawypel) {
        this.ix3Datawypel = ix3Datawypel;
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

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getSeria() {
        return seria;
    }

    public void setSeria(Integer seria) {
        this.seria = seria;
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

    public Character getIdPlZusStatus() {
        return idPlZusStatus;
    }

    public void setIdPlZusStatus(Character idPlZusStatus) {
        this.idPlZusStatus = idPlZusStatus;
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
        if (!(object instanceof Zuszpa)) {
            return false;
        }
        Zuszpa other = (Zuszpa) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zuszpa[ idDokument=" + idDokument + " ]";
    }
    
}
