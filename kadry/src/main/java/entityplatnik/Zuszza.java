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
@Table(name = "ZUSZZA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zuszza.findAll", query = "SELECT z FROM Zuszza z"),
    @NamedQuery(name = "Zuszza.findByIdDokument", query = "SELECT z FROM Zuszza z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zuszza.findByIdPlatnik", query = "SELECT z FROM Zuszza z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zuszza.findByIdUbezpieczony", query = "SELECT z FROM Zuszza z WHERE z.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "Zuszza.findByI1Zgldoubezpzdr", query = "SELECT z FROM Zuszza z WHERE z.i1Zgldoubezpzdr = :i1Zgldoubezpzdr"),
    @NamedQuery(name = "Zuszza.findByI2Zglzmdanych", query = "SELECT z FROM Zuszza z WHERE z.i2Zglzmdanych = :i2Zglzmdanych"),
    @NamedQuery(name = "Zuszza.findByI3Datanadania", query = "SELECT z FROM Zuszza z WHERE z.i3Datanadania = :i3Datanadania"),
    @NamedQuery(name = "Zuszza.findByI4Nalepkar", query = "SELECT z FROM Zuszza z WHERE z.i4Nalepkar = :i4Nalepkar"),
    @NamedQuery(name = "Zuszza.findByIi1Nip", query = "SELECT z FROM Zuszza z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zuszza.findByIi2Regon", query = "SELECT z FROM Zuszza z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zuszza.findByIi3Pesel", query = "SELECT z FROM Zuszza z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zuszza.findByIi4Rodzdok", query = "SELECT z FROM Zuszza z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zuszza.findByIi5Serianrdok", query = "SELECT z FROM Zuszza z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zuszza.findByIi6Nazwaskr", query = "SELECT z FROM Zuszza z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zuszza.findByIi7Nazwisko", query = "SELECT z FROM Zuszza z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zuszza.findByIi8Imiepierw", query = "SELECT z FROM Zuszza z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zuszza.findByIi9Dataurodz", query = "SELECT z FROM Zuszza z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zuszza.findByIii1Pesel", query = "SELECT z FROM Zuszza z WHERE z.iii1Pesel = :iii1Pesel"),
    @NamedQuery(name = "Zuszza.findByIii2Nip", query = "SELECT z FROM Zuszza z WHERE z.iii2Nip = :iii2Nip"),
    @NamedQuery(name = "Zuszza.findByIii3Rodzdok", query = "SELECT z FROM Zuszza z WHERE z.iii3Rodzdok = :iii3Rodzdok"),
    @NamedQuery(name = "Zuszza.findByIii4Serianrdok", query = "SELECT z FROM Zuszza z WHERE z.iii4Serianrdok = :iii4Serianrdok"),
    @NamedQuery(name = "Zuszza.findByIii5Nazwisko", query = "SELECT z FROM Zuszza z WHERE z.iii5Nazwisko = :iii5Nazwisko"),
    @NamedQuery(name = "Zuszza.findByIii6Imiepierw", query = "SELECT z FROM Zuszza z WHERE z.iii6Imiepierw = :iii6Imiepierw"),
    @NamedQuery(name = "Zuszza.findByIii7Dataurodz", query = "SELECT z FROM Zuszza z WHERE z.iii7Dataurodz = :iii7Dataurodz"),
    @NamedQuery(name = "Zuszza.findByIv1Imiedrugie", query = "SELECT z FROM Zuszza z WHERE z.iv1Imiedrugie = :iv1Imiedrugie"),
    @NamedQuery(name = "Zuszza.findByIv2Nazwiskorod", query = "SELECT z FROM Zuszza z WHERE z.iv2Nazwiskorod = :iv2Nazwiskorod"),
    @NamedQuery(name = "Zuszza.findByIv3Obywatelstwo", query = "SELECT z FROM Zuszza z WHERE z.iv3Obywatelstwo = :iv3Obywatelstwo"),
    @NamedQuery(name = "Zuszza.findByIv4Plec", query = "SELECT z FROM Zuszza z WHERE z.iv4Plec = :iv4Plec"),
    @NamedQuery(name = "Zuszza.findByIv5Kartstalpob", query = "SELECT z FROM Zuszza z WHERE z.iv5Kartstalpob = :iv5Kartstalpob"),
    @NamedQuery(name = "Zuszza.findByIv6Kartaczasopob", query = "SELECT z FROM Zuszza z WHERE z.iv6Kartaczasopob = :iv6Kartaczasopob"),
    @NamedQuery(name = "Zuszza.findByV11kodtytub", query = "SELECT z FROM Zuszza z WHERE z.v11kodtytub = :v11kodtytub"),
    @NamedQuery(name = "Zuszza.findByV12prdoem", query = "SELECT z FROM Zuszza z WHERE z.v12prdoem = :v12prdoem"),
    @NamedQuery(name = "Zuszza.findByV13stniep", query = "SELECT z FROM Zuszza z WHERE z.v13stniep = :v13stniep"),
    @NamedQuery(name = "Zuszza.findByVi1Datapowsobub", query = "SELECT z FROM Zuszza z WHERE z.vi1Datapowsobub = :vi1Datapowsobub"),
    @NamedQuery(name = "Zuszza.findByVii1Datapowsobub", query = "SELECT z FROM Zuszza z WHERE z.vii1Datapowsobub = :vii1Datapowsobub"),
    @NamedQuery(name = "Zuszza.findByVii2Kwpiersk", query = "SELECT z FROM Zuszza z WHERE z.vii2Kwpiersk = :vii2Kwpiersk"),
    @NamedQuery(name = "Zuszza.findByViii1Kodkasy", query = "SELECT z FROM Zuszza z WHERE z.viii1Kodkasy = :viii1Kodkasy"),
    @NamedQuery(name = "Zuszza.findByViii2Nazwakch", query = "SELECT z FROM Zuszza z WHERE z.viii2Nazwakch = :viii2Nazwakch"),
    @NamedQuery(name = "Zuszza.findByViii3Dataumzkasa", query = "SELECT z FROM Zuszza z WHERE z.viii3Dataumzkasa = :viii3Dataumzkasa"),
    @NamedQuery(name = "Zuszza.findByIx1Kodpocztowy", query = "SELECT z FROM Zuszza z WHERE z.ix1Kodpocztowy = :ix1Kodpocztowy"),
    @NamedQuery(name = "Zuszza.findByIx2Miejscowosc", query = "SELECT z FROM Zuszza z WHERE z.ix2Miejscowosc = :ix2Miejscowosc"),
    @NamedQuery(name = "Zuszza.findByIx3Gmina", query = "SELECT z FROM Zuszza z WHERE z.ix3Gmina = :ix3Gmina"),
    @NamedQuery(name = "Zuszza.findByIx4Ulica", query = "SELECT z FROM Zuszza z WHERE z.ix4Ulica = :ix4Ulica"),
    @NamedQuery(name = "Zuszza.findByIx5Numerdomu", query = "SELECT z FROM Zuszza z WHERE z.ix5Numerdomu = :ix5Numerdomu"),
    @NamedQuery(name = "Zuszza.findByIx6Numerlokalu", query = "SELECT z FROM Zuszza z WHERE z.ix6Numerlokalu = :ix6Numerlokalu"),
    @NamedQuery(name = "Zuszza.findByIx7Telefon", query = "SELECT z FROM Zuszza z WHERE z.ix7Telefon = :ix7Telefon"),
    @NamedQuery(name = "Zuszza.findByIx8Faks", query = "SELECT z FROM Zuszza z WHERE z.ix8Faks = :ix8Faks"),
    @NamedQuery(name = "Zuszza.findByX1Kodpocztowy", query = "SELECT z FROM Zuszza z WHERE z.x1Kodpocztowy = :x1Kodpocztowy"),
    @NamedQuery(name = "Zuszza.findByX2Miejscowosc", query = "SELECT z FROM Zuszza z WHERE z.x2Miejscowosc = :x2Miejscowosc"),
    @NamedQuery(name = "Zuszza.findByX3Gmina", query = "SELECT z FROM Zuszza z WHERE z.x3Gmina = :x3Gmina"),
    @NamedQuery(name = "Zuszza.findByX4Ulica", query = "SELECT z FROM Zuszza z WHERE z.x4Ulica = :x4Ulica"),
    @NamedQuery(name = "Zuszza.findByX5Numerdomu", query = "SELECT z FROM Zuszza z WHERE z.x5Numerdomu = :x5Numerdomu"),
    @NamedQuery(name = "Zuszza.findByX6Numerlokalu", query = "SELECT z FROM Zuszza z WHERE z.x6Numerlokalu = :x6Numerlokalu"),
    @NamedQuery(name = "Zuszza.findByX7Telefon", query = "SELECT z FROM Zuszza z WHERE z.x7Telefon = :x7Telefon"),
    @NamedQuery(name = "Zuszza.findByX8Faks", query = "SELECT z FROM Zuszza z WHERE z.x8Faks = :x8Faks"),
    @NamedQuery(name = "Zuszza.findByXi1Kodpocztowy", query = "SELECT z FROM Zuszza z WHERE z.xi1Kodpocztowy = :xi1Kodpocztowy"),
    @NamedQuery(name = "Zuszza.findByXi2Miejscowosc", query = "SELECT z FROM Zuszza z WHERE z.xi2Miejscowosc = :xi2Miejscowosc"),
    @NamedQuery(name = "Zuszza.findByXi3Ulica", query = "SELECT z FROM Zuszza z WHERE z.xi3Ulica = :xi3Ulica"),
    @NamedQuery(name = "Zuszza.findByXi4Numerdomu", query = "SELECT z FROM Zuszza z WHERE z.xi4Numerdomu = :xi4Numerdomu"),
    @NamedQuery(name = "Zuszza.findByXi5Numerlokalu", query = "SELECT z FROM Zuszza z WHERE z.xi5Numerlokalu = :xi5Numerlokalu"),
    @NamedQuery(name = "Zuszza.findByXi6Skrpocztowa", query = "SELECT z FROM Zuszza z WHERE z.xi6Skrpocztowa = :xi6Skrpocztowa"),
    @NamedQuery(name = "Zuszza.findByXi7Telefon", query = "SELECT z FROM Zuszza z WHERE z.xi7Telefon = :xi7Telefon"),
    @NamedQuery(name = "Zuszza.findByXi8Faks", query = "SELECT z FROM Zuszza z WHERE z.xi8Faks = :xi8Faks"),
    @NamedQuery(name = "Zuszza.findByXi9Adrpocztyel", query = "SELECT z FROM Zuszza z WHERE z.xi9Adrpocztyel = :xi9Adrpocztyel"),
    @NamedQuery(name = "Zuszza.findByXii1Datawypel", query = "SELECT z FROM Zuszza z WHERE z.xii1Datawypel = :xii1Datawypel"),
    @NamedQuery(name = "Zuszza.findByStatuswr", query = "SELECT z FROM Zuszza z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zuszza.findByStatuspt", query = "SELECT z FROM Zuszza z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zuszza.findByInserttmp", query = "SELECT z FROM Zuszza z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zuszza.findBySeria", query = "SELECT z FROM Zuszza z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zuszza.findByVii2Kodnfz", query = "SELECT z FROM Zuszza z WHERE z.vii2Kodnfz = :vii2Kodnfz"),
    @NamedQuery(name = "Zuszza.findByStatuszus", query = "SELECT z FROM Zuszza z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zuszza.findByStatusKontroli", query = "SELECT z FROM Zuszza z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zuszza.findByIdPlZusStatus", query = "SELECT z FROM Zuszza z WHERE z.idPlZusStatus = :idPlZusStatus"),
    @NamedQuery(name = "Zuszza.findByIdUbZusStatus", query = "SELECT z FROM Zuszza z WHERE z.idUbZusStatus = :idUbZusStatus"),
    @NamedQuery(name = "Zuszza.findByVKodzawodu", query = "SELECT z FROM Zuszza z WHERE z.vKodzawodu = :vKodzawodu")})
public class Zuszza implements Serializable {

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
    @Column(name = "I_1_ZGLDOUBEZPZDR")
    private Character i1Zgldoubezpzdr;
    @Column(name = "I_2_ZGLZMDANYCH")
    private Character i2Zglzmdanych;
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
    @Size(max = 22)
    @Column(name = "IV_1_IMIEDRUGIE", length = 22)
    private String iv1Imiedrugie;
    @Size(max = 31)
    @Column(name = "IV_2_NAZWISKOROD", length = 31)
    private String iv2Nazwiskorod;
    @Size(max = 22)
    @Column(name = "IV_3_OBYWATELSTWO", length = 22)
    private String iv3Obywatelstwo;
    @Column(name = "IV_4_PLEC")
    private Character iv4Plec;
    @Column(name = "IV_5_KARTSTALPOB")
    private Character iv5Kartstalpob;
    @Column(name = "IV_6_KARTACZASOPOB")
    private Character iv6Kartaczasopob;
    @Size(max = 4)
    @Column(name = "V_1_1KODTYTUB", length = 4)
    private String v11kodtytub;
    @Column(name = "V_1_2PRDOEM")
    private Character v12prdoem;
    @Column(name = "V_1_3STNIEP")
    private Character v13stniep;
    @Column(name = "VI_1_DATAPOWSOBUB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vi1Datapowsobub;
    @Column(name = "VII_1_DATAPOWSOBUB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vii1Datapowsobub;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VII_2_KWPIERSK", precision = 9, scale = 2)
    private BigDecimal vii2Kwpiersk;
    @Size(max = 3)
    @Column(name = "VIII_1_KODKASY", length = 3)
    private String viii1Kodkasy;
    @Size(max = 23)
    @Column(name = "VIII_2_NAZWAKCH", length = 23)
    private String viii2Nazwakch;
    @Column(name = "VIII_3_DATAUMZKASA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date viii3Dataumzkasa;
    @Size(max = 5)
    @Column(name = "IX_1_KODPOCZTOWY", length = 5)
    private String ix1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "IX_2_MIEJSCOWOSC", length = 26)
    private String ix2Miejscowosc;
    @Size(max = 26)
    @Column(name = "IX_3_GMINA", length = 26)
    private String ix3Gmina;
    @Size(max = 30)
    @Column(name = "IX_4_ULICA", length = 30)
    private String ix4Ulica;
    @Size(max = 7)
    @Column(name = "IX_5_NUMERDOMU", length = 7)
    private String ix5Numerdomu;
    @Size(max = 7)
    @Column(name = "IX_6_NUMERLOKALU", length = 7)
    private String ix6Numerlokalu;
    @Size(max = 12)
    @Column(name = "IX_7_TELEFON", length = 12)
    private String ix7Telefon;
    @Size(max = 12)
    @Column(name = "IX_8_FAKS", length = 12)
    private String ix8Faks;
    @Size(max = 5)
    @Column(name = "X_1_KODPOCZTOWY", length = 5)
    private String x1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "X_2_MIEJSCOWOSC", length = 26)
    private String x2Miejscowosc;
    @Size(max = 26)
    @Column(name = "X_3_GMINA", length = 26)
    private String x3Gmina;
    @Size(max = 30)
    @Column(name = "X_4_ULICA", length = 30)
    private String x4Ulica;
    @Size(max = 7)
    @Column(name = "X_5_NUMERDOMU", length = 7)
    private String x5Numerdomu;
    @Size(max = 7)
    @Column(name = "X_6_NUMERLOKALU", length = 7)
    private String x6Numerlokalu;
    @Size(max = 12)
    @Column(name = "X_7_TELEFON", length = 12)
    private String x7Telefon;
    @Size(max = 12)
    @Column(name = "X_8_FAKS", length = 12)
    private String x8Faks;
    @Size(max = 5)
    @Column(name = "XI_1_KODPOCZTOWY", length = 5)
    private String xi1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "XI_2_MIEJSCOWOSC", length = 26)
    private String xi2Miejscowosc;
    @Size(max = 30)
    @Column(name = "XI_3_ULICA", length = 30)
    private String xi3Ulica;
    @Size(max = 7)
    @Column(name = "XI_4_NUMERDOMU", length = 7)
    private String xi4Numerdomu;
    @Size(max = 7)
    @Column(name = "XI_5_NUMERLOKALU", length = 7)
    private String xi5Numerlokalu;
    @Size(max = 5)
    @Column(name = "XI_6_SKRPOCZTOWA", length = 5)
    private String xi6Skrpocztowa;
    @Size(max = 12)
    @Column(name = "XI_7_TELEFON", length = 12)
    private String xi7Telefon;
    @Size(max = 12)
    @Column(name = "XI_8_FAKS", length = 12)
    private String xi8Faks;
    @Size(max = 30)
    @Column(name = "XI_9_ADRPOCZTYEL", length = 30)
    private String xi9Adrpocztyel;
    @Column(name = "XII_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date xii1Datawypel;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "SERIA")
    private Integer seria;
    @Size(max = 3)
    @Column(name = "VII_2_KODNFZ", length = 3)
    private String vii2Kodnfz;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_PL_ZUS_STATUS")
    private Character idPlZusStatus;
    @Column(name = "ID_UB_ZUS_STATUS")
    private Character idUbZusStatus;
    @Size(max = 6)
    @Column(name = "V_KODZAWODU", length = 6)
    private String vKodzawodu;

    public Zuszza() {
    }

    public Zuszza(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zuszza(Integer idDokument, int idPlatnik, int idUbezpieczony) {
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

    public Character getI1Zgldoubezpzdr() {
        return i1Zgldoubezpzdr;
    }

    public void setI1Zgldoubezpzdr(Character i1Zgldoubezpzdr) {
        this.i1Zgldoubezpzdr = i1Zgldoubezpzdr;
    }

    public Character getI2Zglzmdanych() {
        return i2Zglzmdanych;
    }

    public void setI2Zglzmdanych(Character i2Zglzmdanych) {
        this.i2Zglzmdanych = i2Zglzmdanych;
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

    public String getIv1Imiedrugie() {
        return iv1Imiedrugie;
    }

    public void setIv1Imiedrugie(String iv1Imiedrugie) {
        this.iv1Imiedrugie = iv1Imiedrugie;
    }

    public String getIv2Nazwiskorod() {
        return iv2Nazwiskorod;
    }

    public void setIv2Nazwiskorod(String iv2Nazwiskorod) {
        this.iv2Nazwiskorod = iv2Nazwiskorod;
    }

    public String getIv3Obywatelstwo() {
        return iv3Obywatelstwo;
    }

    public void setIv3Obywatelstwo(String iv3Obywatelstwo) {
        this.iv3Obywatelstwo = iv3Obywatelstwo;
    }

    public Character getIv4Plec() {
        return iv4Plec;
    }

    public void setIv4Plec(Character iv4Plec) {
        this.iv4Plec = iv4Plec;
    }

    public Character getIv5Kartstalpob() {
        return iv5Kartstalpob;
    }

    public void setIv5Kartstalpob(Character iv5Kartstalpob) {
        this.iv5Kartstalpob = iv5Kartstalpob;
    }

    public Character getIv6Kartaczasopob() {
        return iv6Kartaczasopob;
    }

    public void setIv6Kartaczasopob(Character iv6Kartaczasopob) {
        this.iv6Kartaczasopob = iv6Kartaczasopob;
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

    public Date getVi1Datapowsobub() {
        return vi1Datapowsobub;
    }

    public void setVi1Datapowsobub(Date vi1Datapowsobub) {
        this.vi1Datapowsobub = vi1Datapowsobub;
    }

    public Date getVii1Datapowsobub() {
        return vii1Datapowsobub;
    }

    public void setVii1Datapowsobub(Date vii1Datapowsobub) {
        this.vii1Datapowsobub = vii1Datapowsobub;
    }

    public BigDecimal getVii2Kwpiersk() {
        return vii2Kwpiersk;
    }

    public void setVii2Kwpiersk(BigDecimal vii2Kwpiersk) {
        this.vii2Kwpiersk = vii2Kwpiersk;
    }

    public String getViii1Kodkasy() {
        return viii1Kodkasy;
    }

    public void setViii1Kodkasy(String viii1Kodkasy) {
        this.viii1Kodkasy = viii1Kodkasy;
    }

    public String getViii2Nazwakch() {
        return viii2Nazwakch;
    }

    public void setViii2Nazwakch(String viii2Nazwakch) {
        this.viii2Nazwakch = viii2Nazwakch;
    }

    public Date getViii3Dataumzkasa() {
        return viii3Dataumzkasa;
    }

    public void setViii3Dataumzkasa(Date viii3Dataumzkasa) {
        this.viii3Dataumzkasa = viii3Dataumzkasa;
    }

    public String getIx1Kodpocztowy() {
        return ix1Kodpocztowy;
    }

    public void setIx1Kodpocztowy(String ix1Kodpocztowy) {
        this.ix1Kodpocztowy = ix1Kodpocztowy;
    }

    public String getIx2Miejscowosc() {
        return ix2Miejscowosc;
    }

    public void setIx2Miejscowosc(String ix2Miejscowosc) {
        this.ix2Miejscowosc = ix2Miejscowosc;
    }

    public String getIx3Gmina() {
        return ix3Gmina;
    }

    public void setIx3Gmina(String ix3Gmina) {
        this.ix3Gmina = ix3Gmina;
    }

    public String getIx4Ulica() {
        return ix4Ulica;
    }

    public void setIx4Ulica(String ix4Ulica) {
        this.ix4Ulica = ix4Ulica;
    }

    public String getIx5Numerdomu() {
        return ix5Numerdomu;
    }

    public void setIx5Numerdomu(String ix5Numerdomu) {
        this.ix5Numerdomu = ix5Numerdomu;
    }

    public String getIx6Numerlokalu() {
        return ix6Numerlokalu;
    }

    public void setIx6Numerlokalu(String ix6Numerlokalu) {
        this.ix6Numerlokalu = ix6Numerlokalu;
    }

    public String getIx7Telefon() {
        return ix7Telefon;
    }

    public void setIx7Telefon(String ix7Telefon) {
        this.ix7Telefon = ix7Telefon;
    }

    public String getIx8Faks() {
        return ix8Faks;
    }

    public void setIx8Faks(String ix8Faks) {
        this.ix8Faks = ix8Faks;
    }

    public String getX1Kodpocztowy() {
        return x1Kodpocztowy;
    }

    public void setX1Kodpocztowy(String x1Kodpocztowy) {
        this.x1Kodpocztowy = x1Kodpocztowy;
    }

    public String getX2Miejscowosc() {
        return x2Miejscowosc;
    }

    public void setX2Miejscowosc(String x2Miejscowosc) {
        this.x2Miejscowosc = x2Miejscowosc;
    }

    public String getX3Gmina() {
        return x3Gmina;
    }

    public void setX3Gmina(String x3Gmina) {
        this.x3Gmina = x3Gmina;
    }

    public String getX4Ulica() {
        return x4Ulica;
    }

    public void setX4Ulica(String x4Ulica) {
        this.x4Ulica = x4Ulica;
    }

    public String getX5Numerdomu() {
        return x5Numerdomu;
    }

    public void setX5Numerdomu(String x5Numerdomu) {
        this.x5Numerdomu = x5Numerdomu;
    }

    public String getX6Numerlokalu() {
        return x6Numerlokalu;
    }

    public void setX6Numerlokalu(String x6Numerlokalu) {
        this.x6Numerlokalu = x6Numerlokalu;
    }

    public String getX7Telefon() {
        return x7Telefon;
    }

    public void setX7Telefon(String x7Telefon) {
        this.x7Telefon = x7Telefon;
    }

    public String getX8Faks() {
        return x8Faks;
    }

    public void setX8Faks(String x8Faks) {
        this.x8Faks = x8Faks;
    }

    public String getXi1Kodpocztowy() {
        return xi1Kodpocztowy;
    }

    public void setXi1Kodpocztowy(String xi1Kodpocztowy) {
        this.xi1Kodpocztowy = xi1Kodpocztowy;
    }

    public String getXi2Miejscowosc() {
        return xi2Miejscowosc;
    }

    public void setXi2Miejscowosc(String xi2Miejscowosc) {
        this.xi2Miejscowosc = xi2Miejscowosc;
    }

    public String getXi3Ulica() {
        return xi3Ulica;
    }

    public void setXi3Ulica(String xi3Ulica) {
        this.xi3Ulica = xi3Ulica;
    }

    public String getXi4Numerdomu() {
        return xi4Numerdomu;
    }

    public void setXi4Numerdomu(String xi4Numerdomu) {
        this.xi4Numerdomu = xi4Numerdomu;
    }

    public String getXi5Numerlokalu() {
        return xi5Numerlokalu;
    }

    public void setXi5Numerlokalu(String xi5Numerlokalu) {
        this.xi5Numerlokalu = xi5Numerlokalu;
    }

    public String getXi6Skrpocztowa() {
        return xi6Skrpocztowa;
    }

    public void setXi6Skrpocztowa(String xi6Skrpocztowa) {
        this.xi6Skrpocztowa = xi6Skrpocztowa;
    }

    public String getXi7Telefon() {
        return xi7Telefon;
    }

    public void setXi7Telefon(String xi7Telefon) {
        this.xi7Telefon = xi7Telefon;
    }

    public String getXi8Faks() {
        return xi8Faks;
    }

    public void setXi8Faks(String xi8Faks) {
        this.xi8Faks = xi8Faks;
    }

    public String getXi9Adrpocztyel() {
        return xi9Adrpocztyel;
    }

    public void setXi9Adrpocztyel(String xi9Adrpocztyel) {
        this.xi9Adrpocztyel = xi9Adrpocztyel;
    }

    public Date getXii1Datawypel() {
        return xii1Datawypel;
    }

    public void setXii1Datawypel(Date xii1Datawypel) {
        this.xii1Datawypel = xii1Datawypel;
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

    public String getVii2Kodnfz() {
        return vii2Kodnfz;
    }

    public void setVii2Kodnfz(String vii2Kodnfz) {
        this.vii2Kodnfz = vii2Kodnfz;
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

    public String getVKodzawodu() {
        return vKodzawodu;
    }

    public void setVKodzawodu(String vKodzawodu) {
        this.vKodzawodu = vKodzawodu;
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
        if (!(object instanceof Zuszza)) {
            return false;
        }
        Zuszza other = (Zuszza) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zuszza[ idDokument=" + idDokument + " ]";
    }
    
}
