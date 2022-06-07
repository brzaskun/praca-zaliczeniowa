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
@Table(name = "ZUSZUA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zuszua.findAll", query = "SELECT z FROM Zuszua z"),
    @NamedQuery(name = "Zuszua.findByIdDokument", query = "SELECT z FROM Zuszua z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zuszua.findByIdPlatnik", query = "SELECT z FROM Zuszua z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zuszua.findByIdUbezpieczony", query = "SELECT z FROM Zuszua z WHERE z.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "Zuszua.findByI1Zgldoubspolzdr", query = "SELECT z FROM Zuszua z WHERE z.i1Zgldoubspolzdr = :i1Zgldoubspolzdr"),
    @NamedQuery(name = "Zuszua.findByI2Zgldoubspol", query = "SELECT z FROM Zuszua z WHERE z.i2Zgldoubspol = :i2Zgldoubspol"),
    @NamedQuery(name = "Zuszua.findByI3Zgzmkorosub", query = "SELECT z FROM Zuszua z WHERE z.i3Zgzmkorosub = :i3Zgzmkorosub"),
    @NamedQuery(name = "Zuszua.findByI4Datanadania", query = "SELECT z FROM Zuszua z WHERE z.i4Datanadania = :i4Datanadania"),
    @NamedQuery(name = "Zuszua.findByI5Nalepkar", query = "SELECT z FROM Zuszua z WHERE z.i5Nalepkar = :i5Nalepkar"),
    @NamedQuery(name = "Zuszua.findByIi1Nip", query = "SELECT z FROM Zuszua z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zuszua.findByIi2Regon", query = "SELECT z FROM Zuszua z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zuszua.findByIi3Pesel", query = "SELECT z FROM Zuszua z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zuszua.findByIi4Rodzdok", query = "SELECT z FROM Zuszua z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zuszua.findByIi5Serianrdok", query = "SELECT z FROM Zuszua z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zuszua.findByIi6Nazwaskr", query = "SELECT z FROM Zuszua z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zuszua.findByIi7Nazwisko", query = "SELECT z FROM Zuszua z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zuszua.findByIi8Imiepierw", query = "SELECT z FROM Zuszua z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zuszua.findByIi9Dataurodz", query = "SELECT z FROM Zuszua z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zuszua.findByIii1Pesel", query = "SELECT z FROM Zuszua z WHERE z.iii1Pesel = :iii1Pesel"),
    @NamedQuery(name = "Zuszua.findByIii2Nip", query = "SELECT z FROM Zuszua z WHERE z.iii2Nip = :iii2Nip"),
    @NamedQuery(name = "Zuszua.findByIii3Rodzdok", query = "SELECT z FROM Zuszua z WHERE z.iii3Rodzdok = :iii3Rodzdok"),
    @NamedQuery(name = "Zuszua.findByIii4Serianrdok", query = "SELECT z FROM Zuszua z WHERE z.iii4Serianrdok = :iii4Serianrdok"),
    @NamedQuery(name = "Zuszua.findByIii5Nazwisko", query = "SELECT z FROM Zuszua z WHERE z.iii5Nazwisko = :iii5Nazwisko"),
    @NamedQuery(name = "Zuszua.findByIii6Imiepierw", query = "SELECT z FROM Zuszua z WHERE z.iii6Imiepierw = :iii6Imiepierw"),
    @NamedQuery(name = "Zuszua.findByIii7Dataurodz", query = "SELECT z FROM Zuszua z WHERE z.iii7Dataurodz = :iii7Dataurodz"),
    @NamedQuery(name = "Zuszua.findByIv1Imiedrugie", query = "SELECT z FROM Zuszua z WHERE z.iv1Imiedrugie = :iv1Imiedrugie"),
    @NamedQuery(name = "Zuszua.findByIv2Nazwiskorod", query = "SELECT z FROM Zuszua z WHERE z.iv2Nazwiskorod = :iv2Nazwiskorod"),
    @NamedQuery(name = "Zuszua.findByIv3Obywatelstwo", query = "SELECT z FROM Zuszua z WHERE z.iv3Obywatelstwo = :iv3Obywatelstwo"),
    @NamedQuery(name = "Zuszua.findByIv4Plec", query = "SELECT z FROM Zuszua z WHERE z.iv4Plec = :iv4Plec"),
    @NamedQuery(name = "Zuszua.findByIv5Kartstalpob", query = "SELECT z FROM Zuszua z WHERE z.iv5Kartstalpob = :iv5Kartstalpob"),
    @NamedQuery(name = "Zuszua.findByIv6Kartaczasopob", query = "SELECT z FROM Zuszua z WHERE z.iv6Kartaczasopob = :iv6Kartaczasopob"),
    @NamedQuery(name = "Zuszua.findByV11kodtytub", query = "SELECT z FROM Zuszua z WHERE z.v11kodtytub = :v11kodtytub"),
    @NamedQuery(name = "Zuszua.findByV12prdoem", query = "SELECT z FROM Zuszua z WHERE z.v12prdoem = :v12prdoem"),
    @NamedQuery(name = "Zuszua.findByV13stniep", query = "SELECT z FROM Zuszua z WHERE z.v13stniep = :v13stniep"),
    @NamedQuery(name = "Zuszua.findByV21okresniepod", query = "SELECT z FROM Zuszua z WHERE z.v21okresniepod = :v21okresniepod"),
    @NamedQuery(name = "Zuszua.findByV22okresniepdo", query = "SELECT z FROM Zuszua z WHERE z.v22okresniepdo = :v22okresniepdo"),
    @NamedQuery(name = "Zuszua.findByVi11wymczprl", query = "SELECT z FROM Zuszua z WHERE z.vi11wymczprl = :vi11wymczprl"),
    @NamedQuery(name = "Zuszua.findByVi12wymczprm", query = "SELECT z FROM Zuszua z WHERE z.vi12wymczprm = :vi12wymczprm"),
    @NamedQuery(name = "Zuszua.findByVi2Datapowsobub", query = "SELECT z FROM Zuszua z WHERE z.vi2Datapowsobub = :vi2Datapowsobub"),
    @NamedQuery(name = "Zuszua.findByVi3Oszgpodlem", query = "SELECT z FROM Zuszua z WHERE z.vi3Oszgpodlem = :vi3Oszgpodlem"),
    @NamedQuery(name = "Zuszua.findByVi4Oszgpodren", query = "SELECT z FROM Zuszua z WHERE z.vi4Oszgpodren = :vi4Oszgpodren"),
    @NamedQuery(name = "Zuszua.findByVi5Oszgpodlch", query = "SELECT z FROM Zuszua z WHERE z.vi5Oszgpodlch = :vi5Oszgpodlch"),
    @NamedQuery(name = "Zuszua.findByVi6Oszgpodlwyp", query = "SELECT z FROM Zuszua z WHERE z.vi6Oszgpodlwyp = :vi6Oszgpodlwyp"),
    @NamedQuery(name = "Zuszua.findByVii1Datapowsobub", query = "SELECT z FROM Zuszua z WHERE z.vii1Datapowsobub = :vii1Datapowsobub"),
    @NamedQuery(name = "Zuszua.findByViii1Wnoobjem", query = "SELECT z FROM Zuszua z WHERE z.viii1Wnoobjem = :viii1Wnoobjem"),
    @NamedQuery(name = "Zuszua.findByViii2Oddniaubem", query = "SELECT z FROM Zuszua z WHERE z.viii2Oddniaubem = :viii2Oddniaubem"),
    @NamedQuery(name = "Zuszua.findByViii3Wnoobjr", query = "SELECT z FROM Zuszua z WHERE z.viii3Wnoobjr = :viii3Wnoobjr"),
    @NamedQuery(name = "Zuszua.findByViii4Oddniaubr", query = "SELECT z FROM Zuszua z WHERE z.viii4Oddniaubr = :viii4Oddniaubr"),
    @NamedQuery(name = "Zuszua.findByViii5Wnoobjch", query = "SELECT z FROM Zuszua z WHERE z.viii5Wnoobjch = :viii5Wnoobjch"),
    @NamedQuery(name = "Zuszua.findByViii6Oddniaubch", query = "SELECT z FROM Zuszua z WHERE z.viii6Oddniaubch = :viii6Oddniaubch"),
    @NamedQuery(name = "Zuszua.findByIx1Datapowsobub", query = "SELECT z FROM Zuszua z WHERE z.ix1Datapowsobub = :ix1Datapowsobub"),
    @NamedQuery(name = "Zuszua.findByIx2Kwpiersk", query = "SELECT z FROM Zuszua z WHERE z.ix2Kwpiersk = :ix2Kwpiersk"),
    @NamedQuery(name = "Zuszua.findByX1Kodpokzprac", query = "SELECT z FROM Zuszua z WHERE z.x1Kodpokzprac = :x1Kodpokzprac"),
    @NamedQuery(name = "Zuszua.findByX2Wspgospzpr", query = "SELECT z FROM Zuszua z WHERE z.x2Wspgospzpr = :x2Wspgospzpr"),
    @NamedQuery(name = "Zuszua.findByX3Kodstnzdpra", query = "SELECT z FROM Zuszua z WHERE z.x3Kodstnzdpra = :x3Kodstnzdpra"),
    @NamedQuery(name = "Zuszua.findByX41okrnzdoprod", query = "SELECT z FROM Zuszua z WHERE z.x41okrnzdoprod = :x41okrnzdoprod"),
    @NamedQuery(name = "Zuszua.findByX42okrnzdoprdo", query = "SELECT z FROM Zuszua z WHERE z.x42okrnzdoprdo = :x42okrnzdoprdo"),
    @NamedQuery(name = "Zuszua.findByX5Kodzawodu", query = "SELECT z FROM Zuszua z WHERE z.x5Kodzawodu = :x5Kodzawodu"),
    @NamedQuery(name = "Zuszua.findByX6Kodprgorn", query = "SELECT z FROM Zuszua z WHERE z.x6Kodprgorn = :x6Kodprgorn"),
    @NamedQuery(name = "Zuszua.findByX71okrprgorod", query = "SELECT z FROM Zuszua z WHERE z.x71okrprgorod = :x71okrprgorod"),
    @NamedQuery(name = "Zuszua.findByX72okrprgordo", query = "SELECT z FROM Zuszua z WHERE z.x72okrprgordo = :x72okrprgordo"),
    @NamedQuery(name = "Zuszua.findByX8Kodwyk", query = "SELECT z FROM Zuszua z WHERE z.x8Kodwyk = :x8Kodwyk"),
    @NamedQuery(name = "Zuszua.findByX9Kodprszw", query = "SELECT z FROM Zuszua z WHERE z.x9Kodprszw = :x9Kodprszw"),
    @NamedQuery(name = "Zuszua.findByX101okrprszwod", query = "SELECT z FROM Zuszua z WHERE z.x101okrprszwod = :x101okrprszwod"),
    @NamedQuery(name = "Zuszua.findByX102okrprszwdo", query = "SELECT z FROM Zuszua z WHERE z.x102okrprszwdo = :x102okrprszwdo"),
    @NamedQuery(name = "Zuszua.findByXi1Kodkasy", query = "SELECT z FROM Zuszua z WHERE z.xi1Kodkasy = :xi1Kodkasy"),
    @NamedQuery(name = "Zuszua.findByXi2Nazwakch", query = "SELECT z FROM Zuszua z WHERE z.xi2Nazwakch = :xi2Nazwakch"),
    @NamedQuery(name = "Zuszua.findByXi3Dataumzkasa", query = "SELECT z FROM Zuszua z WHERE z.xi3Dataumzkasa = :xi3Dataumzkasa"),
    @NamedQuery(name = "Zuszua.findByXii1Kodpocztowy", query = "SELECT z FROM Zuszua z WHERE z.xii1Kodpocztowy = :xii1Kodpocztowy"),
    @NamedQuery(name = "Zuszua.findByXii2Miejscowosc", query = "SELECT z FROM Zuszua z WHERE z.xii2Miejscowosc = :xii2Miejscowosc"),
    @NamedQuery(name = "Zuszua.findByXii3Gmina", query = "SELECT z FROM Zuszua z WHERE z.xii3Gmina = :xii3Gmina"),
    @NamedQuery(name = "Zuszua.findByXii4Ulica", query = "SELECT z FROM Zuszua z WHERE z.xii4Ulica = :xii4Ulica"),
    @NamedQuery(name = "Zuszua.findByXii5Numerdomu", query = "SELECT z FROM Zuszua z WHERE z.xii5Numerdomu = :xii5Numerdomu"),
    @NamedQuery(name = "Zuszua.findByXii6Numerlokalu", query = "SELECT z FROM Zuszua z WHERE z.xii6Numerlokalu = :xii6Numerlokalu"),
    @NamedQuery(name = "Zuszua.findByXii7Telefon", query = "SELECT z FROM Zuszua z WHERE z.xii7Telefon = :xii7Telefon"),
    @NamedQuery(name = "Zuszua.findByXii8Faks", query = "SELECT z FROM Zuszua z WHERE z.xii8Faks = :xii8Faks"),
    @NamedQuery(name = "Zuszua.findByXiii1Kodpocztowy", query = "SELECT z FROM Zuszua z WHERE z.xiii1Kodpocztowy = :xiii1Kodpocztowy"),
    @NamedQuery(name = "Zuszua.findByXiii2Miejscowosc", query = "SELECT z FROM Zuszua z WHERE z.xiii2Miejscowosc = :xiii2Miejscowosc"),
    @NamedQuery(name = "Zuszua.findByXiii3Gmina", query = "SELECT z FROM Zuszua z WHERE z.xiii3Gmina = :xiii3Gmina"),
    @NamedQuery(name = "Zuszua.findByXiii4Ulica", query = "SELECT z FROM Zuszua z WHERE z.xiii4Ulica = :xiii4Ulica"),
    @NamedQuery(name = "Zuszua.findByXiii5Numerdomu", query = "SELECT z FROM Zuszua z WHERE z.xiii5Numerdomu = :xiii5Numerdomu"),
    @NamedQuery(name = "Zuszua.findByXiii6Numerlokalu", query = "SELECT z FROM Zuszua z WHERE z.xiii6Numerlokalu = :xiii6Numerlokalu"),
    @NamedQuery(name = "Zuszua.findByXiii7Telefon", query = "SELECT z FROM Zuszua z WHERE z.xiii7Telefon = :xiii7Telefon"),
    @NamedQuery(name = "Zuszua.findByXiii8Faks", query = "SELECT z FROM Zuszua z WHERE z.xiii8Faks = :xiii8Faks"),
    @NamedQuery(name = "Zuszua.findByXiv1Kodpocztowy", query = "SELECT z FROM Zuszua z WHERE z.xiv1Kodpocztowy = :xiv1Kodpocztowy"),
    @NamedQuery(name = "Zuszua.findByXiv2Miejscowosc", query = "SELECT z FROM Zuszua z WHERE z.xiv2Miejscowosc = :xiv2Miejscowosc"),
    @NamedQuery(name = "Zuszua.findByXiv3Ulica", query = "SELECT z FROM Zuszua z WHERE z.xiv3Ulica = :xiv3Ulica"),
    @NamedQuery(name = "Zuszua.findByXiv4Numerdomu", query = "SELECT z FROM Zuszua z WHERE z.xiv4Numerdomu = :xiv4Numerdomu"),
    @NamedQuery(name = "Zuszua.findByXiv5Numerlokalu", query = "SELECT z FROM Zuszua z WHERE z.xiv5Numerlokalu = :xiv5Numerlokalu"),
    @NamedQuery(name = "Zuszua.findByXiv6Skrpocztowa", query = "SELECT z FROM Zuszua z WHERE z.xiv6Skrpocztowa = :xiv6Skrpocztowa"),
    @NamedQuery(name = "Zuszua.findByXiv7Telefon", query = "SELECT z FROM Zuszua z WHERE z.xiv7Telefon = :xiv7Telefon"),
    @NamedQuery(name = "Zuszua.findByXiv8Faks", query = "SELECT z FROM Zuszua z WHERE z.xiv8Faks = :xiv8Faks"),
    @NamedQuery(name = "Zuszua.findByXiv9Adrpocztyel", query = "SELECT z FROM Zuszua z WHERE z.xiv9Adrpocztyel = :xiv9Adrpocztyel"),
    @NamedQuery(name = "Zuszua.findByXv1Datawypel", query = "SELECT z FROM Zuszua z WHERE z.xv1Datawypel = :xv1Datawypel"),
    @NamedQuery(name = "Zuszua.findByStatuswr", query = "SELECT z FROM Zuszua z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zuszua.findByStatuspt", query = "SELECT z FROM Zuszua z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zuszua.findByInserttmp", query = "SELECT z FROM Zuszua z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zuszua.findBySeria", query = "SELECT z FROM Zuszua z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zuszua.findByIx2Kodnfz", query = "SELECT z FROM Zuszua z WHERE z.ix2Kodnfz = :ix2Kodnfz"),
    @NamedQuery(name = "Zuszua.findByStatuszus", query = "SELECT z FROM Zuszua z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zuszua.findByStatusKontroli", query = "SELECT z FROM Zuszua z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zuszua.findByIdPlZusStatus", query = "SELECT z FROM Zuszua z WHERE z.idPlZusStatus = :idPlZusStatus"),
    @NamedQuery(name = "Zuszua.findByIdUbZusStatus", query = "SELECT z FROM Zuszua z WHERE z.idUbZusStatus = :idUbZusStatus")})
public class Zuszua implements Serializable {

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
    @Column(name = "I_1_ZGLDOUBSPOLZDR")
    private Character i1Zgldoubspolzdr;
    @Column(name = "I_2_ZGLDOUBSPOL")
    private Character i2Zgldoubspol;
    @Column(name = "I_3_ZGZMKOROSUB")
    private Character i3Zgzmkorosub;
    @Column(name = "I_4_DATANADANIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i4Datanadania;
    @Size(max = 20)
    @Column(name = "I_5_NALEPKAR", length = 20)
    private String i5Nalepkar;
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
    @Column(name = "V_2_1OKRESNIEPOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date v21okresniepod;
    @Column(name = "V_2_2OKRESNIEPDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date v22okresniepdo;
    @Size(max = 3)
    @Column(name = "VI_1_1WYMCZPRL", length = 3)
    private String vi11wymczprl;
    @Size(max = 3)
    @Column(name = "VI_1_2WYMCZPRM", length = 3)
    private String vi12wymczprm;
    @Column(name = "VI_2_DATAPOWSOBUB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vi2Datapowsobub;
    @Column(name = "VI_3_OSZGPODLEM")
    private Character vi3Oszgpodlem;
    @Column(name = "VI_4_OSZGPODREN")
    private Character vi4Oszgpodren;
    @Column(name = "VI_5_OSZGPODLCH")
    private Character vi5Oszgpodlch;
    @Column(name = "VI_6_OSZGPODLWYP")
    private Character vi6Oszgpodlwyp;
    @Column(name = "VII_1_DATAPOWSOBUB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vii1Datapowsobub;
    @Column(name = "VIII_1_WNOOBJEM")
    private Character viii1Wnoobjem;
    @Column(name = "VIII_2_ODDNIAUBEM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date viii2Oddniaubem;
    @Column(name = "VIII_3_WNOOBJR")
    private Character viii3Wnoobjr;
    @Column(name = "VIII_4_ODDNIAUBR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date viii4Oddniaubr;
    @Column(name = "VIII_5_WNOOBJCH")
    private Character viii5Wnoobjch;
    @Column(name = "VIII_6_ODDNIAUBCH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date viii6Oddniaubch;
    @Column(name = "IX_1_DATAPOWSOBUB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ix1Datapowsobub;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "IX_2_KWPIERSK", precision = 9, scale = 2)
    private BigDecimal ix2Kwpiersk;
    @Size(max = 2)
    @Column(name = "X_1_KODPOKZPRAC", length = 2)
    private String x1Kodpokzprac;
    @Column(name = "X_2_WSPGOSPZPR")
    private Character x2Wspgospzpr;
    @Size(max = 2)
    @Column(name = "X_3_KODSTNZDPRA", length = 2)
    private String x3Kodstnzdpra;
    @Column(name = "X_4_1OKRNZDOPROD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date x41okrnzdoprod;
    @Column(name = "X_4_2OKRNZDOPRDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date x42okrnzdoprdo;
    @Size(max = 7)
    @Column(name = "X_5_KODZAWODU", length = 7)
    private String x5Kodzawodu;
    @Size(max = 6)
    @Column(name = "X_6_KODPRGORN", length = 6)
    private String x6Kodprgorn;
    @Column(name = "X_7_1OKRPRGOROD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date x71okrprgorod;
    @Column(name = "X_7_2OKRPRGORDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date x72okrprgordo;
    @Size(max = 2)
    @Column(name = "X_8_KODWYK", length = 2)
    private String x8Kodwyk;
    @Size(max = 9)
    @Column(name = "X_9_KODPRSZW", length = 9)
    private String x9Kodprszw;
    @Column(name = "X_10_1OKRPRSZWOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date x101okrprszwod;
    @Column(name = "X_10_2OKRPRSZWDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date x102okrprszwdo;
    @Size(max = 3)
    @Column(name = "XI_1_KODKASY", length = 3)
    private String xi1Kodkasy;
    @Size(max = 23)
    @Column(name = "XI_2_NAZWAKCH", length = 23)
    private String xi2Nazwakch;
    @Column(name = "XI_3_DATAUMZKASA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date xi3Dataumzkasa;
    @Size(max = 5)
    @Column(name = "XII_1_KODPOCZTOWY", length = 5)
    private String xii1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "XII_2_MIEJSCOWOSC", length = 26)
    private String xii2Miejscowosc;
    @Size(max = 26)
    @Column(name = "XII_3_GMINA", length = 26)
    private String xii3Gmina;
    @Size(max = 30)
    @Column(name = "XII_4_ULICA", length = 30)
    private String xii4Ulica;
    @Size(max = 7)
    @Column(name = "XII_5_NUMERDOMU", length = 7)
    private String xii5Numerdomu;
    @Size(max = 7)
    @Column(name = "XII_6_NUMERLOKALU", length = 7)
    private String xii6Numerlokalu;
    @Size(max = 12)
    @Column(name = "XII_7_TELEFON", length = 12)
    private String xii7Telefon;
    @Size(max = 12)
    @Column(name = "XII_8_FAKS", length = 12)
    private String xii8Faks;
    @Size(max = 5)
    @Column(name = "XIII_1_KODPOCZTOWY", length = 5)
    private String xiii1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "XIII_2_MIEJSCOWOSC", length = 26)
    private String xiii2Miejscowosc;
    @Size(max = 26)
    @Column(name = "XIII_3_GMINA", length = 26)
    private String xiii3Gmina;
    @Size(max = 30)
    @Column(name = "XIII_4_ULICA", length = 30)
    private String xiii4Ulica;
    @Size(max = 7)
    @Column(name = "XIII_5_NUMERDOMU", length = 7)
    private String xiii5Numerdomu;
    @Size(max = 7)
    @Column(name = "XIII_6_NUMERLOKALU", length = 7)
    private String xiii6Numerlokalu;
    @Size(max = 12)
    @Column(name = "XIII_7_TELEFON", length = 12)
    private String xiii7Telefon;
    @Size(max = 12)
    @Column(name = "XIII_8_FAKS", length = 12)
    private String xiii8Faks;
    @Size(max = 5)
    @Column(name = "XIV_1_KODPOCZTOWY", length = 5)
    private String xiv1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "XIV_2_MIEJSCOWOSC", length = 26)
    private String xiv2Miejscowosc;
    @Size(max = 30)
    @Column(name = "XIV_3_ULICA", length = 30)
    private String xiv3Ulica;
    @Size(max = 7)
    @Column(name = "XIV_4_NUMERDOMU", length = 7)
    private String xiv4Numerdomu;
    @Size(max = 7)
    @Column(name = "XIV_5_NUMERLOKALU", length = 7)
    private String xiv5Numerlokalu;
    @Size(max = 5)
    @Column(name = "XIV_6_SKRPOCZTOWA", length = 5)
    private String xiv6Skrpocztowa;
    @Size(max = 12)
    @Column(name = "XIV_7_TELEFON", length = 12)
    private String xiv7Telefon;
    @Size(max = 12)
    @Column(name = "XIV_8_FAKS", length = 12)
    private String xiv8Faks;
    @Size(max = 30)
    @Column(name = "XIV_9_ADRPOCZTYEL", length = 30)
    private String xiv9Adrpocztyel;
    @Column(name = "XV_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date xv1Datawypel;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "SERIA")
    private Integer seria;
    @Size(max = 3)
    @Column(name = "IX_2_KODNFZ", length = 3)
    private String ix2Kodnfz;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_PL_ZUS_STATUS")
    private Character idPlZusStatus;
    @Column(name = "ID_UB_ZUS_STATUS")
    private Character idUbZusStatus;

    public Zuszua() {
    }

    public Zuszua(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zuszua(Integer idDokument, int idPlatnik, int idUbezpieczony) {
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

    public Character getI1Zgldoubspolzdr() {
        return i1Zgldoubspolzdr;
    }

    public void setI1Zgldoubspolzdr(Character i1Zgldoubspolzdr) {
        this.i1Zgldoubspolzdr = i1Zgldoubspolzdr;
    }

    public Character getI2Zgldoubspol() {
        return i2Zgldoubspol;
    }

    public void setI2Zgldoubspol(Character i2Zgldoubspol) {
        this.i2Zgldoubspol = i2Zgldoubspol;
    }

    public Character getI3Zgzmkorosub() {
        return i3Zgzmkorosub;
    }

    public void setI3Zgzmkorosub(Character i3Zgzmkorosub) {
        this.i3Zgzmkorosub = i3Zgzmkorosub;
    }

    public Date getI4Datanadania() {
        return i4Datanadania;
    }

    public void setI4Datanadania(Date i4Datanadania) {
        this.i4Datanadania = i4Datanadania;
    }

    public String getI5Nalepkar() {
        return i5Nalepkar;
    }

    public void setI5Nalepkar(String i5Nalepkar) {
        this.i5Nalepkar = i5Nalepkar;
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

    public Date getV21okresniepod() {
        return v21okresniepod;
    }

    public void setV21okresniepod(Date v21okresniepod) {
        this.v21okresniepod = v21okresniepod;
    }

    public Date getV22okresniepdo() {
        return v22okresniepdo;
    }

    public void setV22okresniepdo(Date v22okresniepdo) {
        this.v22okresniepdo = v22okresniepdo;
    }

    public String getVi11wymczprl() {
        return vi11wymczprl;
    }

    public void setVi11wymczprl(String vi11wymczprl) {
        this.vi11wymczprl = vi11wymczprl;
    }

    public String getVi12wymczprm() {
        return vi12wymczprm;
    }

    public void setVi12wymczprm(String vi12wymczprm) {
        this.vi12wymczprm = vi12wymczprm;
    }

    public Date getVi2Datapowsobub() {
        return vi2Datapowsobub;
    }

    public void setVi2Datapowsobub(Date vi2Datapowsobub) {
        this.vi2Datapowsobub = vi2Datapowsobub;
    }

    public Character getVi3Oszgpodlem() {
        return vi3Oszgpodlem;
    }

    public void setVi3Oszgpodlem(Character vi3Oszgpodlem) {
        this.vi3Oszgpodlem = vi3Oszgpodlem;
    }

    public Character getVi4Oszgpodren() {
        return vi4Oszgpodren;
    }

    public void setVi4Oszgpodren(Character vi4Oszgpodren) {
        this.vi4Oszgpodren = vi4Oszgpodren;
    }

    public Character getVi5Oszgpodlch() {
        return vi5Oszgpodlch;
    }

    public void setVi5Oszgpodlch(Character vi5Oszgpodlch) {
        this.vi5Oszgpodlch = vi5Oszgpodlch;
    }

    public Character getVi6Oszgpodlwyp() {
        return vi6Oszgpodlwyp;
    }

    public void setVi6Oszgpodlwyp(Character vi6Oszgpodlwyp) {
        this.vi6Oszgpodlwyp = vi6Oszgpodlwyp;
    }

    public Date getVii1Datapowsobub() {
        return vii1Datapowsobub;
    }

    public void setVii1Datapowsobub(Date vii1Datapowsobub) {
        this.vii1Datapowsobub = vii1Datapowsobub;
    }

    public Character getViii1Wnoobjem() {
        return viii1Wnoobjem;
    }

    public void setViii1Wnoobjem(Character viii1Wnoobjem) {
        this.viii1Wnoobjem = viii1Wnoobjem;
    }

    public Date getViii2Oddniaubem() {
        return viii2Oddniaubem;
    }

    public void setViii2Oddniaubem(Date viii2Oddniaubem) {
        this.viii2Oddniaubem = viii2Oddniaubem;
    }

    public Character getViii3Wnoobjr() {
        return viii3Wnoobjr;
    }

    public void setViii3Wnoobjr(Character viii3Wnoobjr) {
        this.viii3Wnoobjr = viii3Wnoobjr;
    }

    public Date getViii4Oddniaubr() {
        return viii4Oddniaubr;
    }

    public void setViii4Oddniaubr(Date viii4Oddniaubr) {
        this.viii4Oddniaubr = viii4Oddniaubr;
    }

    public Character getViii5Wnoobjch() {
        return viii5Wnoobjch;
    }

    public void setViii5Wnoobjch(Character viii5Wnoobjch) {
        this.viii5Wnoobjch = viii5Wnoobjch;
    }

    public Date getViii6Oddniaubch() {
        return viii6Oddniaubch;
    }

    public void setViii6Oddniaubch(Date viii6Oddniaubch) {
        this.viii6Oddniaubch = viii6Oddniaubch;
    }

    public Date getIx1Datapowsobub() {
        return ix1Datapowsobub;
    }

    public void setIx1Datapowsobub(Date ix1Datapowsobub) {
        this.ix1Datapowsobub = ix1Datapowsobub;
    }

    public BigDecimal getIx2Kwpiersk() {
        return ix2Kwpiersk;
    }

    public void setIx2Kwpiersk(BigDecimal ix2Kwpiersk) {
        this.ix2Kwpiersk = ix2Kwpiersk;
    }

    public String getX1Kodpokzprac() {
        return x1Kodpokzprac;
    }

    public void setX1Kodpokzprac(String x1Kodpokzprac) {
        this.x1Kodpokzprac = x1Kodpokzprac;
    }

    public Character getX2Wspgospzpr() {
        return x2Wspgospzpr;
    }

    public void setX2Wspgospzpr(Character x2Wspgospzpr) {
        this.x2Wspgospzpr = x2Wspgospzpr;
    }

    public String getX3Kodstnzdpra() {
        return x3Kodstnzdpra;
    }

    public void setX3Kodstnzdpra(String x3Kodstnzdpra) {
        this.x3Kodstnzdpra = x3Kodstnzdpra;
    }

    public Date getX41okrnzdoprod() {
        return x41okrnzdoprod;
    }

    public void setX41okrnzdoprod(Date x41okrnzdoprod) {
        this.x41okrnzdoprod = x41okrnzdoprod;
    }

    public Date getX42okrnzdoprdo() {
        return x42okrnzdoprdo;
    }

    public void setX42okrnzdoprdo(Date x42okrnzdoprdo) {
        this.x42okrnzdoprdo = x42okrnzdoprdo;
    }

    public String getX5Kodzawodu() {
        return x5Kodzawodu;
    }

    public void setX5Kodzawodu(String x5Kodzawodu) {
        this.x5Kodzawodu = x5Kodzawodu;
    }

    public String getX6Kodprgorn() {
        return x6Kodprgorn;
    }

    public void setX6Kodprgorn(String x6Kodprgorn) {
        this.x6Kodprgorn = x6Kodprgorn;
    }

    public Date getX71okrprgorod() {
        return x71okrprgorod;
    }

    public void setX71okrprgorod(Date x71okrprgorod) {
        this.x71okrprgorod = x71okrprgorod;
    }

    public Date getX72okrprgordo() {
        return x72okrprgordo;
    }

    public void setX72okrprgordo(Date x72okrprgordo) {
        this.x72okrprgordo = x72okrprgordo;
    }

    public String getX8Kodwyk() {
        return x8Kodwyk;
    }

    public void setX8Kodwyk(String x8Kodwyk) {
        this.x8Kodwyk = x8Kodwyk;
    }

    public String getX9Kodprszw() {
        return x9Kodprszw;
    }

    public void setX9Kodprszw(String x9Kodprszw) {
        this.x9Kodprszw = x9Kodprszw;
    }

    public Date getX101okrprszwod() {
        return x101okrprszwod;
    }

    public void setX101okrprszwod(Date x101okrprszwod) {
        this.x101okrprszwod = x101okrprszwod;
    }

    public Date getX102okrprszwdo() {
        return x102okrprszwdo;
    }

    public void setX102okrprszwdo(Date x102okrprszwdo) {
        this.x102okrprszwdo = x102okrprszwdo;
    }

    public String getXi1Kodkasy() {
        return xi1Kodkasy;
    }

    public void setXi1Kodkasy(String xi1Kodkasy) {
        this.xi1Kodkasy = xi1Kodkasy;
    }

    public String getXi2Nazwakch() {
        return xi2Nazwakch;
    }

    public void setXi2Nazwakch(String xi2Nazwakch) {
        this.xi2Nazwakch = xi2Nazwakch;
    }

    public Date getXi3Dataumzkasa() {
        return xi3Dataumzkasa;
    }

    public void setXi3Dataumzkasa(Date xi3Dataumzkasa) {
        this.xi3Dataumzkasa = xi3Dataumzkasa;
    }

    public String getXii1Kodpocztowy() {
        return xii1Kodpocztowy;
    }

    public void setXii1Kodpocztowy(String xii1Kodpocztowy) {
        this.xii1Kodpocztowy = xii1Kodpocztowy;
    }

    public String getXii2Miejscowosc() {
        return xii2Miejscowosc;
    }

    public void setXii2Miejscowosc(String xii2Miejscowosc) {
        this.xii2Miejscowosc = xii2Miejscowosc;
    }

    public String getXii3Gmina() {
        return xii3Gmina;
    }

    public void setXii3Gmina(String xii3Gmina) {
        this.xii3Gmina = xii3Gmina;
    }

    public String getXii4Ulica() {
        return xii4Ulica;
    }

    public void setXii4Ulica(String xii4Ulica) {
        this.xii4Ulica = xii4Ulica;
    }

    public String getXii5Numerdomu() {
        return xii5Numerdomu;
    }

    public void setXii5Numerdomu(String xii5Numerdomu) {
        this.xii5Numerdomu = xii5Numerdomu;
    }

    public String getXii6Numerlokalu() {
        return xii6Numerlokalu;
    }

    public void setXii6Numerlokalu(String xii6Numerlokalu) {
        this.xii6Numerlokalu = xii6Numerlokalu;
    }

    public String getXii7Telefon() {
        return xii7Telefon;
    }

    public void setXii7Telefon(String xii7Telefon) {
        this.xii7Telefon = xii7Telefon;
    }

    public String getXii8Faks() {
        return xii8Faks;
    }

    public void setXii8Faks(String xii8Faks) {
        this.xii8Faks = xii8Faks;
    }

    public String getXiii1Kodpocztowy() {
        return xiii1Kodpocztowy;
    }

    public void setXiii1Kodpocztowy(String xiii1Kodpocztowy) {
        this.xiii1Kodpocztowy = xiii1Kodpocztowy;
    }

    public String getXiii2Miejscowosc() {
        return xiii2Miejscowosc;
    }

    public void setXiii2Miejscowosc(String xiii2Miejscowosc) {
        this.xiii2Miejscowosc = xiii2Miejscowosc;
    }

    public String getXiii3Gmina() {
        return xiii3Gmina;
    }

    public void setXiii3Gmina(String xiii3Gmina) {
        this.xiii3Gmina = xiii3Gmina;
    }

    public String getXiii4Ulica() {
        return xiii4Ulica;
    }

    public void setXiii4Ulica(String xiii4Ulica) {
        this.xiii4Ulica = xiii4Ulica;
    }

    public String getXiii5Numerdomu() {
        return xiii5Numerdomu;
    }

    public void setXiii5Numerdomu(String xiii5Numerdomu) {
        this.xiii5Numerdomu = xiii5Numerdomu;
    }

    public String getXiii6Numerlokalu() {
        return xiii6Numerlokalu;
    }

    public void setXiii6Numerlokalu(String xiii6Numerlokalu) {
        this.xiii6Numerlokalu = xiii6Numerlokalu;
    }

    public String getXiii7Telefon() {
        return xiii7Telefon;
    }

    public void setXiii7Telefon(String xiii7Telefon) {
        this.xiii7Telefon = xiii7Telefon;
    }

    public String getXiii8Faks() {
        return xiii8Faks;
    }

    public void setXiii8Faks(String xiii8Faks) {
        this.xiii8Faks = xiii8Faks;
    }

    public String getXiv1Kodpocztowy() {
        return xiv1Kodpocztowy;
    }

    public void setXiv1Kodpocztowy(String xiv1Kodpocztowy) {
        this.xiv1Kodpocztowy = xiv1Kodpocztowy;
    }

    public String getXiv2Miejscowosc() {
        return xiv2Miejscowosc;
    }

    public void setXiv2Miejscowosc(String xiv2Miejscowosc) {
        this.xiv2Miejscowosc = xiv2Miejscowosc;
    }

    public String getXiv3Ulica() {
        return xiv3Ulica;
    }

    public void setXiv3Ulica(String xiv3Ulica) {
        this.xiv3Ulica = xiv3Ulica;
    }

    public String getXiv4Numerdomu() {
        return xiv4Numerdomu;
    }

    public void setXiv4Numerdomu(String xiv4Numerdomu) {
        this.xiv4Numerdomu = xiv4Numerdomu;
    }

    public String getXiv5Numerlokalu() {
        return xiv5Numerlokalu;
    }

    public void setXiv5Numerlokalu(String xiv5Numerlokalu) {
        this.xiv5Numerlokalu = xiv5Numerlokalu;
    }

    public String getXiv6Skrpocztowa() {
        return xiv6Skrpocztowa;
    }

    public void setXiv6Skrpocztowa(String xiv6Skrpocztowa) {
        this.xiv6Skrpocztowa = xiv6Skrpocztowa;
    }

    public String getXiv7Telefon() {
        return xiv7Telefon;
    }

    public void setXiv7Telefon(String xiv7Telefon) {
        this.xiv7Telefon = xiv7Telefon;
    }

    public String getXiv8Faks() {
        return xiv8Faks;
    }

    public void setXiv8Faks(String xiv8Faks) {
        this.xiv8Faks = xiv8Faks;
    }

    public String getXiv9Adrpocztyel() {
        return xiv9Adrpocztyel;
    }

    public void setXiv9Adrpocztyel(String xiv9Adrpocztyel) {
        this.xiv9Adrpocztyel = xiv9Adrpocztyel;
    }

    public Date getXv1Datawypel() {
        return xv1Datawypel;
    }

    public void setXv1Datawypel(Date xv1Datawypel) {
        this.xv1Datawypel = xv1Datawypel;
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

    public String getIx2Kodnfz() {
        return ix2Kodnfz;
    }

    public void setIx2Kodnfz(String ix2Kodnfz) {
        this.ix2Kodnfz = ix2Kodnfz;
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
        if (!(object instanceof Zuszua)) {
            return false;
        }
        Zuszua other = (Zuszua) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zuszua[ idDokument=" + idDokument + " ]";
    }
    
}
