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
@Table(name = "ZUSZFA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zuszfa.findAll", query = "SELECT z FROM Zuszfa z"),
    @NamedQuery(name = "Zuszfa.findByIdDokument", query = "SELECT z FROM Zuszfa z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zuszfa.findByIdPlatnik", query = "SELECT z FROM Zuszfa z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zuszfa.findByI1Zglplsklade", query = "SELECT z FROM Zuszfa z WHERE z.i1Zglplsklade = :i1Zglplsklade"),
    @NamedQuery(name = "Zuszfa.findByI2Zglzmdaplatnik", query = "SELECT z FROM Zuszfa z WHERE z.i2Zglzmdaplatnik = :i2Zglzmdaplatnik"),
    @NamedQuery(name = "Zuszfa.findByI3Datanadania", query = "SELECT z FROM Zuszfa z WHERE z.i3Datanadania = :i3Datanadania"),
    @NamedQuery(name = "Zuszfa.findByI4Nalepkar", query = "SELECT z FROM Zuszfa z WHERE z.i4Nalepkar = :i4Nalepkar"),
    @NamedQuery(name = "Zuszfa.findByIi1Nip", query = "SELECT z FROM Zuszfa z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zuszfa.findByIi2Regon", query = "SELECT z FROM Zuszfa z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zuszfa.findByIi3Pesel", query = "SELECT z FROM Zuszfa z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zuszfa.findByIi4Rodzdok", query = "SELECT z FROM Zuszfa z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zuszfa.findByIi5Serianrdok", query = "SELECT z FROM Zuszfa z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zuszfa.findByIi6Nazwaskr", query = "SELECT z FROM Zuszfa z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zuszfa.findByIi7Nazwisko", query = "SELECT z FROM Zuszfa z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zuszfa.findByIi8Imiepierw", query = "SELECT z FROM Zuszfa z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zuszfa.findByIi9Dataurodz", query = "SELECT z FROM Zuszfa z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zuszfa.findByIii1Imiedrugie", query = "SELECT z FROM Zuszfa z WHERE z.iii1Imiedrugie = :iii1Imiedrugie"),
    @NamedQuery(name = "Zuszfa.findByIii2Miejsceur", query = "SELECT z FROM Zuszfa z WHERE z.iii2Miejsceur = :iii2Miejsceur"),
    @NamedQuery(name = "Zuszfa.findByIii3Obywatelstwo", query = "SELECT z FROM Zuszfa z WHERE z.iii3Obywatelstwo = :iii3Obywatelstwo"),
    @NamedQuery(name = "Zuszfa.findByIv1Podpozroldzia", query = "SELECT z FROM Zuszfa z WHERE z.iv1Podpozroldzia = :iv1Podpozroldzia"),
    @NamedQuery(name = "Zuszfa.findByIv2Nruprawnienia", query = "SELECT z FROM Zuszfa z WHERE z.iv2Nruprawnienia = :iv2Nruprawnienia"),
    @NamedQuery(name = "Zuszfa.findByIv3Norganuwydupr", query = "SELECT z FROM Zuszfa z WHERE z.iv3Norganuwydupr = :iv3Norganuwydupr"),
    @NamedQuery(name = "Zuszfa.findByIv4Datawydupr", query = "SELECT z FROM Zuszfa z WHERE z.iv4Datawydupr = :iv4Datawydupr"),
    @NamedQuery(name = "Zuszfa.findByIv5Datardzial", query = "SELECT z FROM Zuszfa z WHERE z.iv5Datardzial = :iv5Datardzial"),
    @NamedQuery(name = "Zuszfa.findByV1Nrrachunku", query = "SELECT z FROM Zuszfa z WHERE z.v1Nrrachunku = :v1Nrrachunku"),
    @NamedQuery(name = "Zuszfa.findByV2Czyinnerach", query = "SELECT z FROM Zuszfa z WHERE z.v2Czyinnerach = :v2Czyinnerach"),
    @NamedQuery(name = "Zuszfa.findByVi1Plmastprchr", query = "SELECT z FROM Zuszfa z WHERE z.vi1Plmastprchr = :vi1Plmastprchr"),
    @NamedQuery(name = "Zuszfa.findByVi2Dataotstpchr", query = "SELECT z FROM Zuszfa z WHERE z.vi2Dataotstpchr = :vi2Dataotstpchr"),
    @NamedQuery(name = "Zuszfa.findByVi3Datautrstprch", query = "SELECT z FROM Zuszfa z WHERE z.vi3Datautrstprch = :vi3Datautrstprch"),
    @NamedQuery(name = "Zuszfa.findByVi4Datapowobub", query = "SELECT z FROM Zuszfa z WHERE z.vi4Datapowobub = :vi4Datapowobub"),
    @NamedQuery(name = "Zuszfa.findByVi5Adrdzialnsied", query = "SELECT z FROM Zuszfa z WHERE z.vi5Adrdzialnsied = :vi5Adrdzialnsied"),
    @NamedQuery(name = "Zuszfa.findByVii1Kodpocztowy", query = "SELECT z FROM Zuszfa z WHERE z.vii1Kodpocztowy = :vii1Kodpocztowy"),
    @NamedQuery(name = "Zuszfa.findByVii2Miejscowosc", query = "SELECT z FROM Zuszfa z WHERE z.vii2Miejscowosc = :vii2Miejscowosc"),
    @NamedQuery(name = "Zuszfa.findByVii3Gmina", query = "SELECT z FROM Zuszfa z WHERE z.vii3Gmina = :vii3Gmina"),
    @NamedQuery(name = "Zuszfa.findByVii4Ulica", query = "SELECT z FROM Zuszfa z WHERE z.vii4Ulica = :vii4Ulica"),
    @NamedQuery(name = "Zuszfa.findByVii5Numerdomu", query = "SELECT z FROM Zuszfa z WHERE z.vii5Numerdomu = :vii5Numerdomu"),
    @NamedQuery(name = "Zuszfa.findByVii6Numerlokalu", query = "SELECT z FROM Zuszfa z WHERE z.vii6Numerlokalu = :vii6Numerlokalu"),
    @NamedQuery(name = "Zuszfa.findByVii7Telefon", query = "SELECT z FROM Zuszfa z WHERE z.vii7Telefon = :vii7Telefon"),
    @NamedQuery(name = "Zuszfa.findByVii8Faks", query = "SELECT z FROM Zuszfa z WHERE z.vii8Faks = :vii8Faks"),
    @NamedQuery(name = "Zuszfa.findByVii9Adrpocztyel", query = "SELECT z FROM Zuszfa z WHERE z.vii9Adrpocztyel = :vii9Adrpocztyel"),
    @NamedQuery(name = "Zuszfa.findByViii1Kodpocztowy", query = "SELECT z FROM Zuszfa z WHERE z.viii1Kodpocztowy = :viii1Kodpocztowy"),
    @NamedQuery(name = "Zuszfa.findByViii2Miejscowosc", query = "SELECT z FROM Zuszfa z WHERE z.viii2Miejscowosc = :viii2Miejscowosc"),
    @NamedQuery(name = "Zuszfa.findByViii3Gmina", query = "SELECT z FROM Zuszfa z WHERE z.viii3Gmina = :viii3Gmina"),
    @NamedQuery(name = "Zuszfa.findByViii4Ulica", query = "SELECT z FROM Zuszfa z WHERE z.viii4Ulica = :viii4Ulica"),
    @NamedQuery(name = "Zuszfa.findByViii5Numerdomu", query = "SELECT z FROM Zuszfa z WHERE z.viii5Numerdomu = :viii5Numerdomu"),
    @NamedQuery(name = "Zuszfa.findByViii6Numerlokalu", query = "SELECT z FROM Zuszfa z WHERE z.viii6Numerlokalu = :viii6Numerlokalu"),
    @NamedQuery(name = "Zuszfa.findByViii7Telefon", query = "SELECT z FROM Zuszfa z WHERE z.viii7Telefon = :viii7Telefon"),
    @NamedQuery(name = "Zuszfa.findByViii8Faks", query = "SELECT z FROM Zuszfa z WHERE z.viii8Faks = :viii8Faks"),
    @NamedQuery(name = "Zuszfa.findByViii9Adrpocztyel", query = "SELECT z FROM Zuszfa z WHERE z.viii9Adrpocztyel = :viii9Adrpocztyel"),
    @NamedQuery(name = "Zuszfa.findByIx1Kodpocztowy", query = "SELECT z FROM Zuszfa z WHERE z.ix1Kodpocztowy = :ix1Kodpocztowy"),
    @NamedQuery(name = "Zuszfa.findByIx2Miejscowosc", query = "SELECT z FROM Zuszfa z WHERE z.ix2Miejscowosc = :ix2Miejscowosc"),
    @NamedQuery(name = "Zuszfa.findByIx3Ulica", query = "SELECT z FROM Zuszfa z WHERE z.ix3Ulica = :ix3Ulica"),
    @NamedQuery(name = "Zuszfa.findByIx4Numerdomu", query = "SELECT z FROM Zuszfa z WHERE z.ix4Numerdomu = :ix4Numerdomu"),
    @NamedQuery(name = "Zuszfa.findByIx5Numerlokalu", query = "SELECT z FROM Zuszfa z WHERE z.ix5Numerlokalu = :ix5Numerlokalu"),
    @NamedQuery(name = "Zuszfa.findByIx6Teldoteletr", query = "SELECT z FROM Zuszfa z WHERE z.ix6Teldoteletr = :ix6Teldoteletr"),
    @NamedQuery(name = "Zuszfa.findByIx7Skrpocztowa", query = "SELECT z FROM Zuszfa z WHERE z.ix7Skrpocztowa = :ix7Skrpocztowa"),
    @NamedQuery(name = "Zuszfa.findByIx8Telefon", query = "SELECT z FROM Zuszfa z WHERE z.ix8Telefon = :ix8Telefon"),
    @NamedQuery(name = "Zuszfa.findByIx9Faks", query = "SELECT z FROM Zuszfa z WHERE z.ix9Faks = :ix9Faks"),
    @NamedQuery(name = "Zuszfa.findByIx10Adrpocztyel", query = "SELECT z FROM Zuszfa z WHERE z.ix10Adrpocztyel = :ix10Adrpocztyel"),
    @NamedQuery(name = "Zuszfa.findByX1Nip", query = "SELECT z FROM Zuszfa z WHERE z.x1Nip = :x1Nip"),
    @NamedQuery(name = "Zuszfa.findByX2Regon", query = "SELECT z FROM Zuszfa z WHERE z.x2Regon = :x2Regon"),
    @NamedQuery(name = "Zuszfa.findByX3Nazwaskr", query = "SELECT z FROM Zuszfa z WHERE z.x3Nazwaskr = :x3Nazwaskr"),
    @NamedQuery(name = "Zuszfa.findByXi1Lzalzba", query = "SELECT z FROM Zuszfa z WHERE z.xi1Lzalzba = :xi1Lzalzba"),
    @NamedQuery(name = "Zuszfa.findByXi2Lzalzaa", query = "SELECT z FROM Zuszfa z WHERE z.xi2Lzalzaa = :xi2Lzalzaa"),
    @NamedQuery(name = "Zuszfa.findByXi3Datawypel", query = "SELECT z FROM Zuszfa z WHERE z.xi3Datawypel = :xi3Datawypel"),
    @NamedQuery(name = "Zuszfa.findByStatuswr", query = "SELECT z FROM Zuszfa z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zuszfa.findByStatuspt", query = "SELECT z FROM Zuszfa z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zuszfa.findByInserttmp", query = "SELECT z FROM Zuszfa z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zuszfa.findBySeria", query = "SELECT z FROM Zuszfa z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zuszfa.findByStatuszus", query = "SELECT z FROM Zuszfa z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zuszfa.findByStatusKontroli", query = "SELECT z FROM Zuszfa z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zuszfa.findByIdPlZusStatus", query = "SELECT z FROM Zuszfa z WHERE z.idPlZusStatus = :idPlZusStatus")})
public class Zuszfa implements Serializable {

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
    @Size(max = 22)
    @Column(name = "III_1_IMIEDRUGIE", length = 22)
    private String iii1Imiedrugie;
    @Size(max = 26)
    @Column(name = "III_2_MIEJSCEUR", length = 26)
    private String iii2Miejsceur;
    @Size(max = 22)
    @Column(name = "III_3_OBYWATELSTWO", length = 22)
    private String iii3Obywatelstwo;
    @Size(max = 2)
    @Column(name = "IV_1_PODPOZROLDZIA", length = 2)
    private String iv1Podpozroldzia;
    @Size(max = 15)
    @Column(name = "IV_2_NRUPRAWNIENIA", length = 15)
    private String iv2Nruprawnienia;
    @Size(max = 72)
    @Column(name = "IV_3_NORGANUWYDUPR", length = 72)
    private String iv3Norganuwydupr;
    @Column(name = "IV_4_DATAWYDUPR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv4Datawydupr;
    @Column(name = "IV_5_DATARDZIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv5Datardzial;
    @Size(max = 36)
    @Column(name = "V_1_NRRACHUNKU", length = 36)
    private String v1Nrrachunku;
    @Column(name = "V_2_CZYINNERACH")
    private Character v2Czyinnerach;
    @Column(name = "VI_1_PLMASTPRCHR")
    private Character vi1Plmastprchr;
    @Column(name = "VI_2_DATAOTSTPCHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vi2Dataotstpchr;
    @Column(name = "VI_3_DATAUTRSTPRCH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vi3Datautrstprch;
    @Column(name = "VI_4_DATAPOWOBUB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vi4Datapowobub;
    @Column(name = "VI_5_ADRDZIALNSIED")
    private Character vi5Adrdzialnsied;
    @Size(max = 5)
    @Column(name = "VII_1_KODPOCZTOWY", length = 5)
    private String vii1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "VII_2_MIEJSCOWOSC", length = 26)
    private String vii2Miejscowosc;
    @Size(max = 26)
    @Column(name = "VII_3_GMINA", length = 26)
    private String vii3Gmina;
    @Size(max = 30)
    @Column(name = "VII_4_ULICA", length = 30)
    private String vii4Ulica;
    @Size(max = 7)
    @Column(name = "VII_5_NUMERDOMU", length = 7)
    private String vii5Numerdomu;
    @Size(max = 7)
    @Column(name = "VII_6_NUMERLOKALU", length = 7)
    private String vii6Numerlokalu;
    @Size(max = 12)
    @Column(name = "VII_7_TELEFON", length = 12)
    private String vii7Telefon;
    @Size(max = 12)
    @Column(name = "VII_8_FAKS", length = 12)
    private String vii8Faks;
    @Size(max = 30)
    @Column(name = "VII_9_ADRPOCZTYEL", length = 30)
    private String vii9Adrpocztyel;
    @Size(max = 5)
    @Column(name = "VIII_1_KODPOCZTOWY", length = 5)
    private String viii1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "VIII_2_MIEJSCOWOSC", length = 26)
    private String viii2Miejscowosc;
    @Size(max = 26)
    @Column(name = "VIII_3_GMINA", length = 26)
    private String viii3Gmina;
    @Size(max = 30)
    @Column(name = "VIII_4_ULICA", length = 30)
    private String viii4Ulica;
    @Size(max = 7)
    @Column(name = "VIII_5_NUMERDOMU", length = 7)
    private String viii5Numerdomu;
    @Size(max = 7)
    @Column(name = "VIII_6_NUMERLOKALU", length = 7)
    private String viii6Numerlokalu;
    @Size(max = 12)
    @Column(name = "VIII_7_TELEFON", length = 12)
    private String viii7Telefon;
    @Size(max = 12)
    @Column(name = "VIII_8_FAKS", length = 12)
    private String viii8Faks;
    @Size(max = 30)
    @Column(name = "VIII_9_ADRPOCZTYEL", length = 30)
    private String viii9Adrpocztyel;
    @Size(max = 5)
    @Column(name = "IX_1_KODPOCZTOWY", length = 5)
    private String ix1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "IX_2_MIEJSCOWOSC", length = 26)
    private String ix2Miejscowosc;
    @Size(max = 30)
    @Column(name = "IX_3_ULICA", length = 30)
    private String ix3Ulica;
    @Size(max = 7)
    @Column(name = "IX_4_NUMERDOMU", length = 7)
    private String ix4Numerdomu;
    @Size(max = 7)
    @Column(name = "IX_5_NUMERLOKALU", length = 7)
    private String ix5Numerlokalu;
    @Size(max = 12)
    @Column(name = "IX_6_TELDOTELETR", length = 12)
    private String ix6Teldoteletr;
    @Size(max = 5)
    @Column(name = "IX_7_SKRPOCZTOWA", length = 5)
    private String ix7Skrpocztowa;
    @Size(max = 12)
    @Column(name = "IX_8_TELEFON", length = 12)
    private String ix8Telefon;
    @Size(max = 12)
    @Column(name = "IX_9_FAKS", length = 12)
    private String ix9Faks;
    @Size(max = 30)
    @Column(name = "IX_10_ADRPOCZTYEL", length = 30)
    private String ix10Adrpocztyel;
    @Size(max = 10)
    @Column(name = "X_1_NIP", length = 10)
    private String x1Nip;
    @Size(max = 14)
    @Column(name = "X_2_REGON", length = 14)
    private String x2Regon;
    @Size(max = 31)
    @Column(name = "X_3_NAZWASKR", length = 31)
    private String x3Nazwaskr;
    @Size(max = 3)
    @Column(name = "XI_1_LZALZBA", length = 3)
    private String xi1Lzalzba;
    @Size(max = 3)
    @Column(name = "XI_2_LZALZAA", length = 3)
    private String xi2Lzalzaa;
    @Column(name = "XI_3_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date xi3Datawypel;
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

    public Zuszfa() {
    }

    public Zuszfa(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zuszfa(Integer idDokument, int idPlatnik) {
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

    public String getIii1Imiedrugie() {
        return iii1Imiedrugie;
    }

    public void setIii1Imiedrugie(String iii1Imiedrugie) {
        this.iii1Imiedrugie = iii1Imiedrugie;
    }

    public String getIii2Miejsceur() {
        return iii2Miejsceur;
    }

    public void setIii2Miejsceur(String iii2Miejsceur) {
        this.iii2Miejsceur = iii2Miejsceur;
    }

    public String getIii3Obywatelstwo() {
        return iii3Obywatelstwo;
    }

    public void setIii3Obywatelstwo(String iii3Obywatelstwo) {
        this.iii3Obywatelstwo = iii3Obywatelstwo;
    }

    public String getIv1Podpozroldzia() {
        return iv1Podpozroldzia;
    }

    public void setIv1Podpozroldzia(String iv1Podpozroldzia) {
        this.iv1Podpozroldzia = iv1Podpozroldzia;
    }

    public String getIv2Nruprawnienia() {
        return iv2Nruprawnienia;
    }

    public void setIv2Nruprawnienia(String iv2Nruprawnienia) {
        this.iv2Nruprawnienia = iv2Nruprawnienia;
    }

    public String getIv3Norganuwydupr() {
        return iv3Norganuwydupr;
    }

    public void setIv3Norganuwydupr(String iv3Norganuwydupr) {
        this.iv3Norganuwydupr = iv3Norganuwydupr;
    }

    public Date getIv4Datawydupr() {
        return iv4Datawydupr;
    }

    public void setIv4Datawydupr(Date iv4Datawydupr) {
        this.iv4Datawydupr = iv4Datawydupr;
    }

    public Date getIv5Datardzial() {
        return iv5Datardzial;
    }

    public void setIv5Datardzial(Date iv5Datardzial) {
        this.iv5Datardzial = iv5Datardzial;
    }

    public String getV1Nrrachunku() {
        return v1Nrrachunku;
    }

    public void setV1Nrrachunku(String v1Nrrachunku) {
        this.v1Nrrachunku = v1Nrrachunku;
    }

    public Character getV2Czyinnerach() {
        return v2Czyinnerach;
    }

    public void setV2Czyinnerach(Character v2Czyinnerach) {
        this.v2Czyinnerach = v2Czyinnerach;
    }

    public Character getVi1Plmastprchr() {
        return vi1Plmastprchr;
    }

    public void setVi1Plmastprchr(Character vi1Plmastprchr) {
        this.vi1Plmastprchr = vi1Plmastprchr;
    }

    public Date getVi2Dataotstpchr() {
        return vi2Dataotstpchr;
    }

    public void setVi2Dataotstpchr(Date vi2Dataotstpchr) {
        this.vi2Dataotstpchr = vi2Dataotstpchr;
    }

    public Date getVi3Datautrstprch() {
        return vi3Datautrstprch;
    }

    public void setVi3Datautrstprch(Date vi3Datautrstprch) {
        this.vi3Datautrstprch = vi3Datautrstprch;
    }

    public Date getVi4Datapowobub() {
        return vi4Datapowobub;
    }

    public void setVi4Datapowobub(Date vi4Datapowobub) {
        this.vi4Datapowobub = vi4Datapowobub;
    }

    public Character getVi5Adrdzialnsied() {
        return vi5Adrdzialnsied;
    }

    public void setVi5Adrdzialnsied(Character vi5Adrdzialnsied) {
        this.vi5Adrdzialnsied = vi5Adrdzialnsied;
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

    public String getVii3Gmina() {
        return vii3Gmina;
    }

    public void setVii3Gmina(String vii3Gmina) {
        this.vii3Gmina = vii3Gmina;
    }

    public String getVii4Ulica() {
        return vii4Ulica;
    }

    public void setVii4Ulica(String vii4Ulica) {
        this.vii4Ulica = vii4Ulica;
    }

    public String getVii5Numerdomu() {
        return vii5Numerdomu;
    }

    public void setVii5Numerdomu(String vii5Numerdomu) {
        this.vii5Numerdomu = vii5Numerdomu;
    }

    public String getVii6Numerlokalu() {
        return vii6Numerlokalu;
    }

    public void setVii6Numerlokalu(String vii6Numerlokalu) {
        this.vii6Numerlokalu = vii6Numerlokalu;
    }

    public String getVii7Telefon() {
        return vii7Telefon;
    }

    public void setVii7Telefon(String vii7Telefon) {
        this.vii7Telefon = vii7Telefon;
    }

    public String getVii8Faks() {
        return vii8Faks;
    }

    public void setVii8Faks(String vii8Faks) {
        this.vii8Faks = vii8Faks;
    }

    public String getVii9Adrpocztyel() {
        return vii9Adrpocztyel;
    }

    public void setVii9Adrpocztyel(String vii9Adrpocztyel) {
        this.vii9Adrpocztyel = vii9Adrpocztyel;
    }

    public String getViii1Kodpocztowy() {
        return viii1Kodpocztowy;
    }

    public void setViii1Kodpocztowy(String viii1Kodpocztowy) {
        this.viii1Kodpocztowy = viii1Kodpocztowy;
    }

    public String getViii2Miejscowosc() {
        return viii2Miejscowosc;
    }

    public void setViii2Miejscowosc(String viii2Miejscowosc) {
        this.viii2Miejscowosc = viii2Miejscowosc;
    }

    public String getViii3Gmina() {
        return viii3Gmina;
    }

    public void setViii3Gmina(String viii3Gmina) {
        this.viii3Gmina = viii3Gmina;
    }

    public String getViii4Ulica() {
        return viii4Ulica;
    }

    public void setViii4Ulica(String viii4Ulica) {
        this.viii4Ulica = viii4Ulica;
    }

    public String getViii5Numerdomu() {
        return viii5Numerdomu;
    }

    public void setViii5Numerdomu(String viii5Numerdomu) {
        this.viii5Numerdomu = viii5Numerdomu;
    }

    public String getViii6Numerlokalu() {
        return viii6Numerlokalu;
    }

    public void setViii6Numerlokalu(String viii6Numerlokalu) {
        this.viii6Numerlokalu = viii6Numerlokalu;
    }

    public String getViii7Telefon() {
        return viii7Telefon;
    }

    public void setViii7Telefon(String viii7Telefon) {
        this.viii7Telefon = viii7Telefon;
    }

    public String getViii8Faks() {
        return viii8Faks;
    }

    public void setViii8Faks(String viii8Faks) {
        this.viii8Faks = viii8Faks;
    }

    public String getViii9Adrpocztyel() {
        return viii9Adrpocztyel;
    }

    public void setViii9Adrpocztyel(String viii9Adrpocztyel) {
        this.viii9Adrpocztyel = viii9Adrpocztyel;
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

    public String getIx3Ulica() {
        return ix3Ulica;
    }

    public void setIx3Ulica(String ix3Ulica) {
        this.ix3Ulica = ix3Ulica;
    }

    public String getIx4Numerdomu() {
        return ix4Numerdomu;
    }

    public void setIx4Numerdomu(String ix4Numerdomu) {
        this.ix4Numerdomu = ix4Numerdomu;
    }

    public String getIx5Numerlokalu() {
        return ix5Numerlokalu;
    }

    public void setIx5Numerlokalu(String ix5Numerlokalu) {
        this.ix5Numerlokalu = ix5Numerlokalu;
    }

    public String getIx6Teldoteletr() {
        return ix6Teldoteletr;
    }

    public void setIx6Teldoteletr(String ix6Teldoteletr) {
        this.ix6Teldoteletr = ix6Teldoteletr;
    }

    public String getIx7Skrpocztowa() {
        return ix7Skrpocztowa;
    }

    public void setIx7Skrpocztowa(String ix7Skrpocztowa) {
        this.ix7Skrpocztowa = ix7Skrpocztowa;
    }

    public String getIx8Telefon() {
        return ix8Telefon;
    }

    public void setIx8Telefon(String ix8Telefon) {
        this.ix8Telefon = ix8Telefon;
    }

    public String getIx9Faks() {
        return ix9Faks;
    }

    public void setIx9Faks(String ix9Faks) {
        this.ix9Faks = ix9Faks;
    }

    public String getIx10Adrpocztyel() {
        return ix10Adrpocztyel;
    }

    public void setIx10Adrpocztyel(String ix10Adrpocztyel) {
        this.ix10Adrpocztyel = ix10Adrpocztyel;
    }

    public String getX1Nip() {
        return x1Nip;
    }

    public void setX1Nip(String x1Nip) {
        this.x1Nip = x1Nip;
    }

    public String getX2Regon() {
        return x2Regon;
    }

    public void setX2Regon(String x2Regon) {
        this.x2Regon = x2Regon;
    }

    public String getX3Nazwaskr() {
        return x3Nazwaskr;
    }

    public void setX3Nazwaskr(String x3Nazwaskr) {
        this.x3Nazwaskr = x3Nazwaskr;
    }

    public String getXi1Lzalzba() {
        return xi1Lzalzba;
    }

    public void setXi1Lzalzba(String xi1Lzalzba) {
        this.xi1Lzalzba = xi1Lzalzba;
    }

    public String getXi2Lzalzaa() {
        return xi2Lzalzaa;
    }

    public void setXi2Lzalzaa(String xi2Lzalzaa) {
        this.xi2Lzalzaa = xi2Lzalzaa;
    }

    public Date getXi3Datawypel() {
        return xi3Datawypel;
    }

    public void setXi3Datawypel(Date xi3Datawypel) {
        this.xi3Datawypel = xi3Datawypel;
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
        if (!(object instanceof Zuszfa)) {
            return false;
        }
        Zuszfa other = (Zuszfa) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zuszfa[ idDokument=" + idDokument + " ]";
    }
    
}
