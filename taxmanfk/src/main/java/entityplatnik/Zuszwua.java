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
@Table(name = "ZUSZWUA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zuszwua.findAll", query = "SELECT z FROM Zuszwua z"),
    @NamedQuery(name = "Zuszwua.findByIdDokument", query = "SELECT z FROM Zuszwua z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zuszwua.findByIdPlatnik", query = "SELECT z FROM Zuszwua z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zuszwua.findByIdUbezpieczony", query = "SELECT z FROM Zuszwua z WHERE z.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "Zuszwua.findByI1Wyrspoizdr", query = "SELECT z FROM Zuszwua z WHERE z.i1Wyrspoizdr = :i1Wyrspoizdr"),
    @NamedQuery(name = "Zuszwua.findByI2Wyrspol", query = "SELECT z FROM Zuszwua z WHERE z.i2Wyrspol = :i2Wyrspol"),
    @NamedQuery(name = "Zuszwua.findByI3Wyrubzdr", query = "SELECT z FROM Zuszwua z WHERE z.i3Wyrubzdr = :i3Wyrubzdr"),
    @NamedQuery(name = "Zuszwua.findByI4Zglkordawyrzub", query = "SELECT z FROM Zuszwua z WHERE z.i4Zglkordawyrzub = :i4Zglkordawyrzub"),
    @NamedQuery(name = "Zuszwua.findByI5Datanadania", query = "SELECT z FROM Zuszwua z WHERE z.i5Datanadania = :i5Datanadania"),
    @NamedQuery(name = "Zuszwua.findByI6Nalepkar", query = "SELECT z FROM Zuszwua z WHERE z.i6Nalepkar = :i6Nalepkar"),
    @NamedQuery(name = "Zuszwua.findByIi1Nip", query = "SELECT z FROM Zuszwua z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zuszwua.findByIi2Regon", query = "SELECT z FROM Zuszwua z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zuszwua.findByIi3Pesel", query = "SELECT z FROM Zuszwua z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zuszwua.findByIi4Rodzdok", query = "SELECT z FROM Zuszwua z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zuszwua.findByIi5Serianrdok", query = "SELECT z FROM Zuszwua z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zuszwua.findByIi6Nazwaskr", query = "SELECT z FROM Zuszwua z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zuszwua.findByIi7Nazwisko", query = "SELECT z FROM Zuszwua z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zuszwua.findByIi8Imiepierw", query = "SELECT z FROM Zuszwua z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zuszwua.findByIi9Dataurodz", query = "SELECT z FROM Zuszwua z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zuszwua.findByIii1Pesel", query = "SELECT z FROM Zuszwua z WHERE z.iii1Pesel = :iii1Pesel"),
    @NamedQuery(name = "Zuszwua.findByIii2Nip", query = "SELECT z FROM Zuszwua z WHERE z.iii2Nip = :iii2Nip"),
    @NamedQuery(name = "Zuszwua.findByIii3Rodzdok", query = "SELECT z FROM Zuszwua z WHERE z.iii3Rodzdok = :iii3Rodzdok"),
    @NamedQuery(name = "Zuszwua.findByIii4Serianrdok", query = "SELECT z FROM Zuszwua z WHERE z.iii4Serianrdok = :iii4Serianrdok"),
    @NamedQuery(name = "Zuszwua.findByIii5Nazwisko", query = "SELECT z FROM Zuszwua z WHERE z.iii5Nazwisko = :iii5Nazwisko"),
    @NamedQuery(name = "Zuszwua.findByIii6Imiepierw", query = "SELECT z FROM Zuszwua z WHERE z.iii6Imiepierw = :iii6Imiepierw"),
    @NamedQuery(name = "Zuszwua.findByIii7Dataurodz", query = "SELECT z FROM Zuszwua z WHERE z.iii7Dataurodz = :iii7Dataurodz"),
    @NamedQuery(name = "Zuszwua.findByIv11kodtytub", query = "SELECT z FROM Zuszwua z WHERE z.iv11kodtytub = :iv11kodtytub"),
    @NamedQuery(name = "Zuszwua.findByIv12prdoem", query = "SELECT z FROM Zuszwua z WHERE z.iv12prdoem = :iv12prdoem"),
    @NamedQuery(name = "Zuszwua.findByIv13stniep", query = "SELECT z FROM Zuszwua z WHERE z.iv13stniep = :iv13stniep"),
    @NamedQuery(name = "Zuszwua.findByIv2Rodzajubem", query = "SELECT z FROM Zuszwua z WHERE z.iv2Rodzajubem = :iv2Rodzajubem"),
    @NamedQuery(name = "Zuszwua.findByIv3Oddniaubem", query = "SELECT z FROM Zuszwua z WHERE z.iv3Oddniaubem = :iv3Oddniaubem"),
    @NamedQuery(name = "Zuszwua.findByIv4Kodtytwyrub", query = "SELECT z FROM Zuszwua z WHERE z.iv4Kodtytwyrub = :iv4Kodtytwyrub"),
    @NamedQuery(name = "Zuszwua.findByIv5Rodzajubr", query = "SELECT z FROM Zuszwua z WHERE z.iv5Rodzajubr = :iv5Rodzajubr"),
    @NamedQuery(name = "Zuszwua.findByIv6Oddniaubr", query = "SELECT z FROM Zuszwua z WHERE z.iv6Oddniaubr = :iv6Oddniaubr"),
    @NamedQuery(name = "Zuszwua.findByIv7Kodtytwyrub", query = "SELECT z FROM Zuszwua z WHERE z.iv7Kodtytwyrub = :iv7Kodtytwyrub"),
    @NamedQuery(name = "Zuszwua.findByIv8Rodzajubch", query = "SELECT z FROM Zuszwua z WHERE z.iv8Rodzajubch = :iv8Rodzajubch"),
    @NamedQuery(name = "Zuszwua.findByIv9Oddniaubch", query = "SELECT z FROM Zuszwua z WHERE z.iv9Oddniaubch = :iv9Oddniaubch"),
    @NamedQuery(name = "Zuszwua.findByIv10Kodtytwyrub", query = "SELECT z FROM Zuszwua z WHERE z.iv10Kodtytwyrub = :iv10Kodtytwyrub"),
    @NamedQuery(name = "Zuszwua.findByIv11Rodzajubwyp", query = "SELECT z FROM Zuszwua z WHERE z.iv11Rodzajubwyp = :iv11Rodzajubwyp"),
    @NamedQuery(name = "Zuszwua.findByIv12Oddniawyp", query = "SELECT z FROM Zuszwua z WHERE z.iv12Oddniawyp = :iv12Oddniawyp"),
    @NamedQuery(name = "Zuszwua.findByIv13Kodtytwyrub", query = "SELECT z FROM Zuszwua z WHERE z.iv13Kodtytwyrub = :iv13Kodtytwyrub"),
    @NamedQuery(name = "Zuszwua.findByIv14Rodzajubzdr", query = "SELECT z FROM Zuszwua z WHERE z.iv14Rodzajubzdr = :iv14Rodzajubzdr"),
    @NamedQuery(name = "Zuszwua.findByIv15Oddniazubzdr", query = "SELECT z FROM Zuszwua z WHERE z.iv15Oddniazubzdr = :iv15Oddniazubzdr"),
    @NamedQuery(name = "Zuszwua.findByIv16Kodtytwyrub", query = "SELECT z FROM Zuszwua z WHERE z.iv16Kodtytwyrub = :iv16Kodtytwyrub"),
    @NamedQuery(name = "Zuszwua.findByV11kodtytub", query = "SELECT z FROM Zuszwua z WHERE z.v11kodtytub = :v11kodtytub"),
    @NamedQuery(name = "Zuszwua.findByV12prdoem", query = "SELECT z FROM Zuszwua z WHERE z.v12prdoem = :v12prdoem"),
    @NamedQuery(name = "Zuszwua.findByV13stniep", query = "SELECT z FROM Zuszwua z WHERE z.v13stniep = :v13stniep"),
    @NamedQuery(name = "Zuszwua.findByV2Datakontubemr", query = "SELECT z FROM Zuszwua z WHERE z.v2Datakontubemr = :v2Datakontubemr"),
    @NamedQuery(name = "Zuszwua.findByV1Datawygas", query = "SELECT z FROM Zuszwua z WHERE z.v1Datawygas = :v1Datawygas"),
    @NamedQuery(name = "Zuszwua.findByV2Kodwygas", query = "SELECT z FROM Zuszwua z WHERE z.v2Kodwygas = :v2Kodwygas"),
    @NamedQuery(name = "Zuszwua.findByV3Kodpodstpraw", query = "SELECT z FROM Zuszwua z WHERE z.v3Kodpodstpraw = :v3Kodpodstpraw"),
    @NamedQuery(name = "Zuszwua.findByV4Podstpraw", query = "SELECT z FROM Zuszwua z WHERE z.v4Podstpraw = :v4Podstpraw"),
    @NamedQuery(name = "Zuszwua.findByV5Stronaini", query = "SELECT z FROM Zuszwua z WHERE z.v5Stronaini = :v5Stronaini"),
    @NamedQuery(name = "Zuszwua.findByVi1Datawypel", query = "SELECT z FROM Zuszwua z WHERE z.vi1Datawypel = :vi1Datawypel"),
    @NamedQuery(name = "Zuszwua.findByStatuswr", query = "SELECT z FROM Zuszwua z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zuszwua.findByStatuspt", query = "SELECT z FROM Zuszwua z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zuszwua.findByInserttmp", query = "SELECT z FROM Zuszwua z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zuszwua.findBySeria", query = "SELECT z FROM Zuszwua z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zuszwua.findByStatuszus", query = "SELECT z FROM Zuszwua z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zuszwua.findByStatusKontroli", query = "SELECT z FROM Zuszwua z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zuszwua.findByIdPlZusStatus", query = "SELECT z FROM Zuszwua z WHERE z.idPlZusStatus = :idPlZusStatus"),
    @NamedQuery(name = "Zuszwua.findByIdUbZusStatus", query = "SELECT z FROM Zuszwua z WHERE z.idUbZusStatus = :idUbZusStatus")})
public class Zuszwua implements Serializable {

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
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
    @Column(name = "I_1_WYRSPOIZDR")
    private Character i1Wyrspoizdr;
    @Column(name = "I_2_WYRSPOL")
    private Character i2Wyrspol;
    @Column(name = "I_3_WYRUBZDR")
    private Character i3Wyrubzdr;
    @Column(name = "I_4_ZGLKORDAWYRZUB")
    private Character i4Zglkordawyrzub;
    @Column(name = "I_5_DATANADANIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i5Datanadania;
    @Size(max = 20)
    @Column(name = "I_6_NALEPKAR", length = 20)
    private String i6Nalepkar;
    @Size(max = 10)
    @Column(name = "II_1_NIP", length = 10)
    private String ii1Nip;
    @Size(max = 14)
    @Column(name = "II_2_REGON", length = 14)
    private String ii2Regon;
    @Size(max = 11)
    @Column(name = "II_3_PESEL", length = 11)
    private String ii3Pesel;
    @Column(name = "II_4_RODZDOK")
    private Character ii4Rodzdok;
    @Size(max = 9)
    @Column(name = "II_5_SERIANRDOK", length = 9)
    private String ii5Serianrdok;
    @Size(max = 31)
    @Column(name = "II_6_NAZWASKR", length = 31)
    private String ii6Nazwaskr;
    @Size(max = 31)
    @Column(name = "II_7_NAZWISKO", length = 31)
    private String ii7Nazwisko;
    @Size(max = 22)
    @Column(name = "II_8_IMIEPIERW", length = 22)
    private String ii8Imiepierw;
    @Column(name = "II_9_DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ii9Dataurodz;
    @Size(max = 11)
    @Column(name = "III_1_PESEL", length = 11)
    private String iii1Pesel;
    @Size(max = 10)
    @Column(name = "III_2_NIP", length = 10)
    private String iii2Nip;
    @Column(name = "III_3_RODZDOK")
    private Character iii3Rodzdok;
    @Size(max = 9)
    @Column(name = "III_4_SERIANRDOK", length = 9)
    private String iii4Serianrdok;
    @Size(max = 31)
    @Column(name = "III_5_NAZWISKO", length = 31)
    private String iii5Nazwisko;
    @Size(max = 22)
    @Column(name = "III_6_IMIEPIERW", length = 22)
    private String iii6Imiepierw;
    @Column(name = "III_7_DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iii7Dataurodz;
    @Size(max = 4)
    @Column(name = "IV_1_1KODTYTUB", length = 4)
    private String iv11kodtytub;
    @Column(name = "IV_1_2PRDOEM")
    private Character iv12prdoem;
    @Column(name = "IV_1_3STNIEP")
    private Character iv13stniep;
    @Column(name = "IV_2_RODZAJUBEM")
    private Character iv2Rodzajubem;
    @Column(name = "IV_3_ODDNIAUBEM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv3Oddniaubem;
    @Size(max = 3)
    @Column(name = "IV_4_KODTYTWYRUB", length = 3)
    private String iv4Kodtytwyrub;
    @Column(name = "IV_5_RODZAJUBR")
    private Character iv5Rodzajubr;
    @Column(name = "IV_6_ODDNIAUBR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv6Oddniaubr;
    @Size(max = 3)
    @Column(name = "IV_7_KODTYTWYRUB", length = 3)
    private String iv7Kodtytwyrub;
    @Column(name = "IV_8_RODZAJUBCH")
    private Character iv8Rodzajubch;
    @Column(name = "IV_9_ODDNIAUBCH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv9Oddniaubch;
    @Size(max = 3)
    @Column(name = "IV_10_KODTYTWYRUB", length = 3)
    private String iv10Kodtytwyrub;
    @Column(name = "IV_11_RODZAJUBWYP")
    private Character iv11Rodzajubwyp;
    @Column(name = "IV_12_ODDNIAWYP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv12Oddniawyp;
    @Size(max = 3)
    @Column(name = "IV_13_KODTYTWYRUB", length = 3)
    private String iv13Kodtytwyrub;
    @Column(name = "IV_14_RODZAJUBZDR")
    private Character iv14Rodzajubzdr;
    @Column(name = "IV_15_ODDNIAZUBZDR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv15Oddniazubzdr;
    @Size(max = 3)
    @Column(name = "IV_16_KODTYTWYRUB", length = 3)
    private String iv16Kodtytwyrub;
    @Size(max = 4)
    @Column(name = "V_1_1KODTYTUB", length = 4)
    private String v11kodtytub;
    @Column(name = "V_1_2PRDOEM")
    private Character v12prdoem;
    @Column(name = "V_1_3STNIEP")
    private Character v13stniep;
    @Column(name = "V_2_DATAKONTUBEMR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date v2Datakontubemr;
    @Column(name = "V_1_DATAWYGAS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date v1Datawygas;
    @Size(max = 3)
    @Column(name = "V_2_KODWYGAS", length = 3)
    private String v2Kodwygas;
    @Size(max = 3)
    @Column(name = "V_3_KODPODSTPRAW", length = 3)
    private String v3Kodpodstpraw;
    @Size(max = 72)
    @Column(name = "V_4_PODSTPRAW", length = 72)
    private String v4Podstpraw;
    @Column(name = "V_5_STRONAINI")
    private Character v5Stronaini;
    @Column(name = "VI_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vi1Datawypel;
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
    @Column(name = "ID_UB_ZUS_STATUS")
    private Character idUbZusStatus;

    public Zuszwua() {
    }

    public Zuszwua(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zuszwua(Integer idDokument, int idPlatnik, int idUbezpieczony) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
        this.idUbezpieczony = idUbezpieczony;
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

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public Character getI1Wyrspoizdr() {
        return i1Wyrspoizdr;
    }

    public void setI1Wyrspoizdr(Character i1Wyrspoizdr) {
        this.i1Wyrspoizdr = i1Wyrspoizdr;
    }

    public Character getI2Wyrspol() {
        return i2Wyrspol;
    }

    public void setI2Wyrspol(Character i2Wyrspol) {
        this.i2Wyrspol = i2Wyrspol;
    }

    public Character getI3Wyrubzdr() {
        return i3Wyrubzdr;
    }

    public void setI3Wyrubzdr(Character i3Wyrubzdr) {
        this.i3Wyrubzdr = i3Wyrubzdr;
    }

    public Character getI4Zglkordawyrzub() {
        return i4Zglkordawyrzub;
    }

    public void setI4Zglkordawyrzub(Character i4Zglkordawyrzub) {
        this.i4Zglkordawyrzub = i4Zglkordawyrzub;
    }

    public Date getI5Datanadania() {
        return i5Datanadania;
    }

    public void setI5Datanadania(Date i5Datanadania) {
        this.i5Datanadania = i5Datanadania;
    }

    public String getI6Nalepkar() {
        return i6Nalepkar;
    }

    public void setI6Nalepkar(String i6Nalepkar) {
        this.i6Nalepkar = i6Nalepkar;
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

    public String getIi3Pesel() {
        return ii3Pesel;
    }

    public void setIi3Pesel(String ii3Pesel) {
        this.ii3Pesel = ii3Pesel;
    }

    public Character getIi4Rodzdok() {
        return ii4Rodzdok;
    }

    public void setIi4Rodzdok(Character ii4Rodzdok) {
        this.ii4Rodzdok = ii4Rodzdok;
    }

    public String getIi5Serianrdok() {
        return ii5Serianrdok;
    }

    public void setIi5Serianrdok(String ii5Serianrdok) {
        this.ii5Serianrdok = ii5Serianrdok;
    }

    public String getIi6Nazwaskr() {
        return ii6Nazwaskr;
    }

    public void setIi6Nazwaskr(String ii6Nazwaskr) {
        this.ii6Nazwaskr = ii6Nazwaskr;
    }

    public String getIi7Nazwisko() {
        return ii7Nazwisko;
    }

    public void setIi7Nazwisko(String ii7Nazwisko) {
        this.ii7Nazwisko = ii7Nazwisko;
    }

    public String getIi8Imiepierw() {
        return ii8Imiepierw;
    }

    public void setIi8Imiepierw(String ii8Imiepierw) {
        this.ii8Imiepierw = ii8Imiepierw;
    }

    public Date getIi9Dataurodz() {
        return ii9Dataurodz;
    }

    public void setIi9Dataurodz(Date ii9Dataurodz) {
        this.ii9Dataurodz = ii9Dataurodz;
    }

    public String getIii1Pesel() {
        return iii1Pesel;
    }

    public void setIii1Pesel(String iii1Pesel) {
        this.iii1Pesel = iii1Pesel;
    }

    public String getIii2Nip() {
        return iii2Nip;
    }

    public void setIii2Nip(String iii2Nip) {
        this.iii2Nip = iii2Nip;
    }

    public Character getIii3Rodzdok() {
        return iii3Rodzdok;
    }

    public void setIii3Rodzdok(Character iii3Rodzdok) {
        this.iii3Rodzdok = iii3Rodzdok;
    }

    public String getIii4Serianrdok() {
        return iii4Serianrdok;
    }

    public void setIii4Serianrdok(String iii4Serianrdok) {
        this.iii4Serianrdok = iii4Serianrdok;
    }

    public String getIii5Nazwisko() {
        return iii5Nazwisko;
    }

    public void setIii5Nazwisko(String iii5Nazwisko) {
        this.iii5Nazwisko = iii5Nazwisko;
    }

    public String getIii6Imiepierw() {
        return iii6Imiepierw;
    }

    public void setIii6Imiepierw(String iii6Imiepierw) {
        this.iii6Imiepierw = iii6Imiepierw;
    }

    public Date getIii7Dataurodz() {
        return iii7Dataurodz;
    }

    public void setIii7Dataurodz(Date iii7Dataurodz) {
        this.iii7Dataurodz = iii7Dataurodz;
    }

    public String getIv11kodtytub() {
        return iv11kodtytub;
    }

    public void setIv11kodtytub(String iv11kodtytub) {
        this.iv11kodtytub = iv11kodtytub;
    }

    public Character getIv12prdoem() {
        return iv12prdoem;
    }

    public void setIv12prdoem(Character iv12prdoem) {
        this.iv12prdoem = iv12prdoem;
    }

    public Character getIv13stniep() {
        return iv13stniep;
    }

    public void setIv13stniep(Character iv13stniep) {
        this.iv13stniep = iv13stniep;
    }

    public Character getIv2Rodzajubem() {
        return iv2Rodzajubem;
    }

    public void setIv2Rodzajubem(Character iv2Rodzajubem) {
        this.iv2Rodzajubem = iv2Rodzajubem;
    }

    public Date getIv3Oddniaubem() {
        return iv3Oddniaubem;
    }

    public void setIv3Oddniaubem(Date iv3Oddniaubem) {
        this.iv3Oddniaubem = iv3Oddniaubem;
    }

    public String getIv4Kodtytwyrub() {
        return iv4Kodtytwyrub;
    }

    public void setIv4Kodtytwyrub(String iv4Kodtytwyrub) {
        this.iv4Kodtytwyrub = iv4Kodtytwyrub;
    }

    public Character getIv5Rodzajubr() {
        return iv5Rodzajubr;
    }

    public void setIv5Rodzajubr(Character iv5Rodzajubr) {
        this.iv5Rodzajubr = iv5Rodzajubr;
    }

    public Date getIv6Oddniaubr() {
        return iv6Oddniaubr;
    }

    public void setIv6Oddniaubr(Date iv6Oddniaubr) {
        this.iv6Oddniaubr = iv6Oddniaubr;
    }

    public String getIv7Kodtytwyrub() {
        return iv7Kodtytwyrub;
    }

    public void setIv7Kodtytwyrub(String iv7Kodtytwyrub) {
        this.iv7Kodtytwyrub = iv7Kodtytwyrub;
    }

    public Character getIv8Rodzajubch() {
        return iv8Rodzajubch;
    }

    public void setIv8Rodzajubch(Character iv8Rodzajubch) {
        this.iv8Rodzajubch = iv8Rodzajubch;
    }

    public Date getIv9Oddniaubch() {
        return iv9Oddniaubch;
    }

    public void setIv9Oddniaubch(Date iv9Oddniaubch) {
        this.iv9Oddniaubch = iv9Oddniaubch;
    }

    public String getIv10Kodtytwyrub() {
        return iv10Kodtytwyrub;
    }

    public void setIv10Kodtytwyrub(String iv10Kodtytwyrub) {
        this.iv10Kodtytwyrub = iv10Kodtytwyrub;
    }

    public Character getIv11Rodzajubwyp() {
        return iv11Rodzajubwyp;
    }

    public void setIv11Rodzajubwyp(Character iv11Rodzajubwyp) {
        this.iv11Rodzajubwyp = iv11Rodzajubwyp;
    }

    public Date getIv12Oddniawyp() {
        return iv12Oddniawyp;
    }

    public void setIv12Oddniawyp(Date iv12Oddniawyp) {
        this.iv12Oddniawyp = iv12Oddniawyp;
    }

    public String getIv13Kodtytwyrub() {
        return iv13Kodtytwyrub;
    }

    public void setIv13Kodtytwyrub(String iv13Kodtytwyrub) {
        this.iv13Kodtytwyrub = iv13Kodtytwyrub;
    }

    public Character getIv14Rodzajubzdr() {
        return iv14Rodzajubzdr;
    }

    public void setIv14Rodzajubzdr(Character iv14Rodzajubzdr) {
        this.iv14Rodzajubzdr = iv14Rodzajubzdr;
    }

    public Date getIv15Oddniazubzdr() {
        return iv15Oddniazubzdr;
    }

    public void setIv15Oddniazubzdr(Date iv15Oddniazubzdr) {
        this.iv15Oddniazubzdr = iv15Oddniazubzdr;
    }

    public String getIv16Kodtytwyrub() {
        return iv16Kodtytwyrub;
    }

    public void setIv16Kodtytwyrub(String iv16Kodtytwyrub) {
        this.iv16Kodtytwyrub = iv16Kodtytwyrub;
    }

    public String getV11kodtytub() {
        return v11kodtytub;
    }

    public void setV11kodtytub(String v11kodtytub) {
        this.v11kodtytub = v11kodtytub;
    }

    public Character getV12prdoem() {
        return v12prdoem;
    }

    public void setV12prdoem(Character v12prdoem) {
        this.v12prdoem = v12prdoem;
    }

    public Character getV13stniep() {
        return v13stniep;
    }

    public void setV13stniep(Character v13stniep) {
        this.v13stniep = v13stniep;
    }

    public Date getV2Datakontubemr() {
        return v2Datakontubemr;
    }

    public void setV2Datakontubemr(Date v2Datakontubemr) {
        this.v2Datakontubemr = v2Datakontubemr;
    }

    public Date getV1Datawygas() {
        return v1Datawygas;
    }

    public void setV1Datawygas(Date v1Datawygas) {
        this.v1Datawygas = v1Datawygas;
    }

    public String getV2Kodwygas() {
        return v2Kodwygas;
    }

    public void setV2Kodwygas(String v2Kodwygas) {
        this.v2Kodwygas = v2Kodwygas;
    }

    public String getV3Kodpodstpraw() {
        return v3Kodpodstpraw;
    }

    public void setV3Kodpodstpraw(String v3Kodpodstpraw) {
        this.v3Kodpodstpraw = v3Kodpodstpraw;
    }

    public String getV4Podstpraw() {
        return v4Podstpraw;
    }

    public void setV4Podstpraw(String v4Podstpraw) {
        this.v4Podstpraw = v4Podstpraw;
    }

    public Character getV5Stronaini() {
        return v5Stronaini;
    }

    public void setV5Stronaini(Character v5Stronaini) {
        this.v5Stronaini = v5Stronaini;
    }

    public Date getVi1Datawypel() {
        return vi1Datawypel;
    }

    public void setVi1Datawypel(Date vi1Datawypel) {
        this.vi1Datawypel = vi1Datawypel;
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

    public Character getIdUbZusStatus() {
        return idUbZusStatus;
    }

    public void setIdUbZusStatus(Character idUbZusStatus) {
        this.idUbZusStatus = idUbZusStatus;
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
        if (!(object instanceof Zuszwua)) {
            return false;
        }
        Zuszwua other = (Zuszwua) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zuszwua[ idDokument=" + idDokument + " ]";
    }
    
}
