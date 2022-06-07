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
@Table(name = "ZUSDRA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ZUSDRA.findAll", query = "SELECT z FROM ZUSDRA z"),
    @NamedQuery(name = "ZUSDRA.findByIdDokument", query = "SELECT z FROM ZUSDRA z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "ZUSDRA.findByIdPlatnik", query = "SELECT z FROM ZUSDRA z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "ZUSDRA.findByI1Termindekirap", query = "SELECT z FROM ZUSDRA z WHERE z.i1Termindekirap = :i1Termindekirap"),
    @NamedQuery(name = "ZUSDRA.findByI21iddekls", query = "SELECT z FROM ZUSDRA z WHERE z.i21iddekls = :i21iddekls"),
    @NamedQuery(name = "ZUSDRA.findByI22okresdeklar", query = "SELECT z FROM ZUSDRA z WHERE z.i22okresdeklar = :i22okresdeklar"),
    @NamedQuery(name = "ZUSDRA.findByI3Datanadania", query = "SELECT z FROM ZUSDRA z WHERE z.i3Datanadania = :i3Datanadania"),
    @NamedQuery(name = "ZUSDRA.findByI4Nalepkar", query = "SELECT z FROM ZUSDRA z WHERE z.i4Nalepkar = :i4Nalepkar"),
    @NamedQuery(name = "ZUSDRA.findByI5Znakinrdecpok", query = "SELECT z FROM ZUSDRA z WHERE z.i5Znakinrdecpok = :i5Znakinrdecpok"),
    @NamedQuery(name = "ZUSDRA.findByIi1Nip", query = "SELECT z FROM ZUSDRA z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "ZUSDRA.findByIi2Regon", query = "SELECT z FROM ZUSDRA z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "ZUSDRA.findByIi3Pesel", query = "SELECT z FROM ZUSDRA z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "ZUSDRA.findByIi4Rodzdok", query = "SELECT z FROM ZUSDRA z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "ZUSDRA.findByIi5Serianrdok", query = "SELECT z FROM ZUSDRA z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "ZUSDRA.findByIi6Nazwaskr", query = "SELECT z FROM ZUSDRA z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "ZUSDRA.findByIi7Nazwisko", query = "SELECT z FROM ZUSDRA z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "ZUSDRA.findByIi8Imiepierw", query = "SELECT z FROM ZUSDRA z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "ZUSDRA.findByIi9Dataurodz", query = "SELECT z FROM ZUSDRA z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "ZUSDRA.findByIii1Lubezp", query = "SELECT z FROM ZUSDRA z WHERE z.iii1Lubezp = :iii1Lubezp"),
    @NamedQuery(name = "ZUSDRA.findByIii2Lprnapelnyw", query = "SELECT z FROM ZUSDRA z WHERE z.iii2Lprnapelnyw = :iii2Lprnapelnyw"),
    @NamedQuery(name = "ZUSDRA.findByIii3Plmastprchr", query = "SELECT z FROM ZUSDRA z WHERE z.iii3Plmastprchr = :iii3Plmastprchr"),
    @NamedQuery(name = "ZUSDRA.findByIii4Stopasklwyp", query = "SELECT z FROM ZUSDRA z WHERE z.iii4Stopasklwyp = :iii4Stopasklwyp"),
    @NamedQuery(name = "ZUSDRA.findByIv1Ssklubem", query = "SELECT z FROM ZUSDRA z WHERE z.iv1Ssklubem = :iv1Ssklubem"),
    @NamedQuery(name = "ZUSDRA.findByIv2Ssklubr", query = "SELECT z FROM ZUSDRA z WHERE z.iv2Ssklubr = :iv2Ssklubr"),
    @NamedQuery(name = "ZUSDRA.findByIv3Ssklubemr", query = "SELECT z FROM ZUSDRA z WHERE z.iv3Ssklubemr = :iv3Ssklubemr"),
    @NamedQuery(name = "ZUSDRA.findByIv4Kwskemfubd", query = "SELECT z FROM ZUSDRA z WHERE z.iv4Kwskemfubd = :iv4Kwskemfubd"),
    @NamedQuery(name = "ZUSDRA.findByIv5Kwskrenfub", query = "SELECT z FROM ZUSDRA z WHERE z.iv5Kwskrenfub = :iv5Kwskrenfub"),
    @NamedQuery(name = "ZUSDRA.findByIv6Sumakwemrfpub", query = "SELECT z FROM ZUSDRA z WHERE z.iv6Sumakwemrfpub = :iv6Sumakwemrfpub"),
    @NamedQuery(name = "ZUSDRA.findByIv7Kwskemfpl", query = "SELECT z FROM ZUSDRA z WHERE z.iv7Kwskemfpl = :iv7Kwskemfpl"),
    @NamedQuery(name = "ZUSDRA.findByIv8Kwskrenfpl", query = "SELECT z FROM ZUSDRA z WHERE z.iv8Kwskrenfpl = :iv8Kwskrenfpl"),
    @NamedQuery(name = "ZUSDRA.findByIv9Skwemirenppl", query = "SELECT z FROM ZUSDRA z WHERE z.iv9Skwemirenppl = :iv9Skwemirenppl"),
    @NamedQuery(name = "ZUSDRA.findByIv10Kwskembpd", query = "SELECT z FROM ZUSDRA z WHERE z.iv10Kwskembpd = :iv10Kwskembpd"),
    @NamedQuery(name = "ZUSDRA.findByIv11Kwskrenfbp", query = "SELECT z FROM ZUSDRA z WHERE z.iv11Kwskrenfbp = :iv11Kwskrenfbp"),
    @NamedQuery(name = "ZUSDRA.findByIv12Sumakwemrpbp", query = "SELECT z FROM ZUSDRA z WHERE z.iv12Sumakwemrpbp = :iv12Sumakwemrpbp"),
    @NamedQuery(name = "ZUSDRA.findByIv13Kwskemfpf", query = "SELECT z FROM ZUSDRA z WHERE z.iv13Kwskemfpf = :iv13Kwskemfpf"),
    @NamedQuery(name = "ZUSDRA.findByIv14Kwskrenfpf", query = "SELECT z FROM ZUSDRA z WHERE z.iv14Kwskrenfpf = :iv14Kwskrenfpf"),
    @NamedQuery(name = "ZUSDRA.findByIv15Skwemirenppf", query = "SELECT z FROM ZUSDRA z WHERE z.iv15Skwemirenppf = :iv15Skwemirenppf"),
    @NamedQuery(name = "ZUSDRA.findByIv16Kwskemfkd", query = "SELECT z FROM ZUSDRA z WHERE z.iv16Kwskemfkd = :iv16Kwskemfkd"),
    @NamedQuery(name = "ZUSDRA.findByIv17Kwskrenffk", query = "SELECT z FROM ZUSDRA z WHERE z.iv17Kwskrenffk = :iv17Kwskrenffk"),
    @NamedQuery(name = "ZUSDRA.findByIv18Skwemirenpfk", query = "SELECT z FROM ZUSDRA z WHERE z.iv18Skwemirenpfk = :iv18Skwemirenpfk"),
    @NamedQuery(name = "ZUSDRA.findByIv19Ssklubch", query = "SELECT z FROM ZUSDRA z WHERE z.iv19Ssklubch = :iv19Ssklubch"),
    @NamedQuery(name = "ZUSDRA.findByIv20Ssklubwyp", query = "SELECT z FROM ZUSDRA z WHERE z.iv20Ssklubwyp = :iv20Ssklubwyp"),
    @NamedQuery(name = "ZUSDRA.findByIv21Ssklubchwyp", query = "SELECT z FROM ZUSDRA z WHERE z.iv21Ssklubchwyp = :iv21Ssklubchwyp"),
    @NamedQuery(name = "ZUSDRA.findByIv22Kwskchubd", query = "SELECT z FROM ZUSDRA z WHERE z.iv22Kwskchubd = :iv22Kwskchubd"),
    @NamedQuery(name = "ZUSDRA.findByIv23Kwskwfub", query = "SELECT z FROM ZUSDRA z WHERE z.iv23Kwskwfub = :iv23Kwskwfub"),
    @NamedQuery(name = "ZUSDRA.findByIv24Skwchiwyppub", query = "SELECT z FROM ZUSDRA z WHERE z.iv24Skwchiwyppub = :iv24Skwchiwyppub"),
    @NamedQuery(name = "ZUSDRA.findByIv25Kwskwfpl", query = "SELECT z FROM ZUSDRA z WHERE z.iv25Kwskwfpl = :iv25Kwskwfpl"),
    @NamedQuery(name = "ZUSDRA.findByIv26Skwchiwypppl", query = "SELECT z FROM ZUSDRA z WHERE z.iv26Skwchiwypppl = :iv26Skwchiwypppl"),
    @NamedQuery(name = "ZUSDRA.findByIv27Kwskchfpf", query = "SELECT z FROM ZUSDRA z WHERE z.iv27Kwskchfpf = :iv27Kwskchfpf"),
    @NamedQuery(name = "ZUSDRA.findByIv28Kwskwfpf", query = "SELECT z FROM ZUSDRA z WHERE z.iv28Kwskwfpf = :iv28Kwskwfpf"),
    @NamedQuery(name = "ZUSDRA.findByIv29Skwchiwypppf", query = "SELECT z FROM ZUSDRA z WHERE z.iv29Skwchiwypppf = :iv29Skwchiwypppf"),
    @NamedQuery(name = "ZUSDRA.findByIv30Kwskwffk", query = "SELECT z FROM ZUSDRA z WHERE z.iv30Kwskwffk = :iv30Kwskwffk"),
    @NamedQuery(name = "ZUSDRA.findByIv31Skwchiwyppfk", query = "SELECT z FROM ZUSDRA z WHERE z.iv31Skwchiwyppfk = :iv31Skwchiwyppfk"),
    @NamedQuery(name = "ZUSDRA.findByIv32Kwskspol", query = "SELECT z FROM ZUSDRA z WHERE z.iv32Kwskspol = :iv32Kwskspol"),
    @NamedQuery(name = "ZUSDRA.findByV1Kwwypswzubch", query = "SELECT z FROM ZUSDRA z WHERE z.v1Kwwypswzubch = :v1Kwwypswzubch"),
    @NamedQuery(name = "ZUSDRA.findByV2Kwnalwynch", query = "SELECT z FROM ZUSDRA z WHERE z.v2Kwnalwynch = :v2Kwnalwynch"),
    @NamedQuery(name = "ZUSDRA.findByV3Kwwypswzubwyp", query = "SELECT z FROM ZUSDRA z WHERE z.v3Kwwypswzubwyp = :v3Kwwypswzubwyp"),
    @NamedQuery(name = "ZUSDRA.findByV4Kwwypswfinpbp", query = "SELECT z FROM ZUSDRA z WHERE z.v4Kwwypswfinpbp = :v4Kwwypswfinpbp"),
    @NamedQuery(name = "ZUSDRA.findByV5Lkwdopotrdra", query = "SELECT z FROM ZUSDRA z WHERE z.v5Lkwdopotrdra = :v5Lkwdopotrdra"),
    @NamedQuery(name = "ZUSDRA.findByVi1KwdozwrotuVi", query = "SELECT z FROM ZUSDRA z WHERE z.vi1KwdozwrotuVi = :vi1KwdozwrotuVi"),
    @NamedQuery(name = "ZUSDRA.findByVi2KwdozappVi", query = "SELECT z FROM ZUSDRA z WHERE z.vi2KwdozappVi = :vi2KwdozappVi"),
    @NamedQuery(name = "ZUSDRA.findByVii1Kwskdprzpl", query = "SELECT z FROM ZUSDRA z WHERE z.vii1Kwskdprzpl = :vii1Kwskdprzpl"),
    @NamedQuery(name = "ZUSDRA.findByVii2Kwskladfpfk", query = "SELECT z FROM ZUSDRA z WHERE z.vii2Kwskladfpfk = :vii2Kwskladfpfk"),
    @NamedQuery(name = "ZUSDRA.findByVii3Kwnalezwy", query = "SELECT z FROM ZUSDRA z WHERE z.vii3Kwnalezwy = :vii3Kwnalezwy"),
    @NamedQuery(name = "ZUSDRA.findByVii4Kwzap", query = "SELECT z FROM ZUSDRA z WHERE z.vii4Kwzap = :vii4Kwzap"),
    @NamedQuery(name = "ZUSDRA.findByViii1Kwnalsklfp", query = "SELECT z FROM ZUSDRA z WHERE z.viii1Kwnalsklfp = :viii1Kwnalsklfp"),
    @NamedQuery(name = "ZUSDRA.findByViii2Kwnalskfgsp", query = "SELECT z FROM ZUSDRA z WHERE z.viii2Kwnalskfgsp = :viii2Kwnalskfgsp"),
    @NamedQuery(name = "ZUSDRA.findByViii3KwzaplViii", query = "SELECT z FROM ZUSDRA z WHERE z.viii3KwzaplViii = :viii3KwzaplViii"),
    @NamedQuery(name = "ZUSDRA.findByIx1Lsumakwdozapl", query = "SELECT z FROM ZUSDRA z WHERE z.ix1Lsumakwdozapl = :ix1Lsumakwdozapl"),
    @NamedQuery(name = "ZUSDRA.findByIx1Kwdozwrotu", query = "SELECT z FROM ZUSDRA z WHERE z.ix1Kwdozwrotu = :ix1Kwdozwrotu"),
    @NamedQuery(name = "ZUSDRA.findByIx2Kwdozaplaty", query = "SELECT z FROM ZUSDRA z WHERE z.ix2Kwdozaplaty = :ix2Kwdozaplaty"),
    @NamedQuery(name = "ZUSDRA.findByX1Kwdoplnaubspol", query = "SELECT z FROM ZUSDRA z WHERE z.x1Kwdoplnaubspol = :x1Kwdoplnaubspol"),
    @NamedQuery(name = "ZUSDRA.findByX2Kwdoplnaubzdr", query = "SELECT z FROM ZUSDRA z WHERE z.x2Kwdoplnaubzdr = :x2Kwdoplnaubzdr"),
    @NamedQuery(name = "ZUSDRA.findByX3Kwdopfpifgsp", query = "SELECT z FROM ZUSDRA z WHERE z.x3Kwdopfpifgsp = :x3Kwdopfpifgsp"),
    @NamedQuery(name = "ZUSDRA.findByX4Lkwdoplat", query = "SELECT z FROM ZUSDRA z WHERE z.x4Lkwdoplat = :x4Lkwdoplat"),
    @NamedQuery(name = "ZUSDRA.findByXi11kodtytub", query = "SELECT z FROM ZUSDRA z WHERE z.xi11kodtytub = :xi11kodtytub"),
    @NamedQuery(name = "ZUSDRA.findByXi12prdoem", query = "SELECT z FROM ZUSDRA z WHERE z.xi12prdoem = :xi12prdoem"),
    @NamedQuery(name = "ZUSDRA.findByXi13stniep", query = "SELECT z FROM ZUSDRA z WHERE z.xi13stniep = :xi13stniep"),
    @NamedQuery(name = "ZUSDRA.findByXi2Podstwymeir", query = "SELECT z FROM ZUSDRA z WHERE z.xi2Podstwymeir = :xi2Podstwymeir"),
    @NamedQuery(name = "ZUSDRA.findByXi3Podstwymchiw", query = "SELECT z FROM ZUSDRA z WHERE z.xi3Podstwymchiw = :xi3Podstwymchiw"),
    @NamedQuery(name = "ZUSDRA.findByXi4Podstwymzdr", query = "SELECT z FROM ZUSDRA z WHERE z.xi4Podstwymzdr = :xi4Podstwymzdr"),
    @NamedQuery(name = "ZUSDRA.findByXi5Infoprrpod", query = "SELECT z FROM ZUSDRA z WHERE z.xi5Infoprrpod = :xi5Infoprrpod"),
    @NamedQuery(name = "ZUSDRA.findByXii1LkartekRca", query = "SELECT z FROM ZUSDRA z WHERE z.xii1LkartekRca = :xii1LkartekRca"),
    @NamedQuery(name = "ZUSDRA.findByXii2LkartekRna", query = "SELECT z FROM ZUSDRA z WHERE z.xii2LkartekRna = :xii2LkartekRna"),
    @NamedQuery(name = "ZUSDRA.findByXii3LkartekRza", query = "SELECT z FROM ZUSDRA z WHERE z.xii3LkartekRza = :xii3LkartekRza"),
    @NamedQuery(name = "ZUSDRA.findByXii4LkartekRsa", query = "SELECT z FROM ZUSDRA z WHERE z.xii4LkartekRsa = :xii4LkartekRsa"),
    @NamedQuery(name = "ZUSDRA.findByXii5LkartekRga", query = "SELECT z FROM ZUSDRA z WHERE z.xii5LkartekRga = :xii5LkartekRga"),
    @NamedQuery(name = "ZUSDRA.findByXii6Lliczkartrap", query = "SELECT z FROM ZUSDRA z WHERE z.xii6Lliczkartrap = :xii6Lliczkartrap"),
    @NamedQuery(name = "ZUSDRA.findByXii71iddekls", query = "SELECT z FROM ZUSDRA z WHERE z.xii71iddekls = :xii71iddekls"),
    @NamedQuery(name = "ZUSDRA.findByXii72okresdeklar", query = "SELECT z FROM ZUSDRA z WHERE z.xii72okresdeklar = :xii72okresdeklar"),
    @NamedQuery(name = "ZUSDRA.findByXii8Datawypel", query = "SELECT z FROM ZUSDRA z WHERE z.xii8Datawypel = :xii8Datawypel"),
    @NamedQuery(name = "ZUSDRA.findByStatuswr", query = "SELECT z FROM ZUSDRA z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "ZUSDRA.findByStatuspt", query = "SELECT z FROM ZUSDRA z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "ZUSDRA.findByInserttmp", query = "SELECT z FROM ZUSDRA z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "ZUSDRA.findByZnKor", query = "SELECT z FROM ZUSDRA z WHERE z.znKor = :znKor"),
    @NamedQuery(name = "ZUSDRA.findByNrsciezwyl", query = "SELECT z FROM ZUSDRA z WHERE z.nrsciezwyl = :nrsciezwyl"),
    @NamedQuery(name = "ZUSDRA.findBySeria", query = "SELECT z FROM ZUSDRA z WHERE z.seria = :seria"),
    @NamedQuery(name = "ZUSDRA.findByVii3Kwskladfzbp", query = "SELECT z FROM ZUSDRA z WHERE z.vii3Kwskladfzbp = :vii3Kwskladfzbp"),
    @NamedQuery(name = "ZUSDRA.findByIx1Lprskladfep", query = "SELECT z FROM ZUSDRA z WHERE z.ix1Lprskladfep = :ix1Lprskladfep"),
    @NamedQuery(name = "ZUSDRA.findByIx2Lstanprszw", query = "SELECT z FROM ZUSDRA z WHERE z.ix2Lstanprszw = :ix2Lstanprszw"),
    @NamedQuery(name = "ZUSDRA.findByIx3Sskladfep", query = "SELECT z FROM ZUSDRA z WHERE z.ix3Sskladfep = :ix3Sskladfep"),
    @NamedQuery(name = "ZUSDRA.findByIv25Kwskchpd", query = "SELECT z FROM ZUSDRA z WHERE z.iv25Kwskchpd = :iv25Kwskchpd"),
    @NamedQuery(name = "ZUSDRA.findByIv28Kwskchbpd", query = "SELECT z FROM ZUSDRA z WHERE z.iv28Kwskchbpd = :iv28Kwskchbpd"),
    @NamedQuery(name = "ZUSDRA.findByIv29Kwskwfbpd", query = "SELECT z FROM ZUSDRA z WHERE z.iv29Kwskwfbpd = :iv29Kwskwfbpd"),
    @NamedQuery(name = "ZUSDRA.findByIv30Skwchiwyppbp", query = "SELECT z FROM ZUSDRA z WHERE z.iv30Skwchiwyppbp = :iv30Skwchiwyppbp"),
    @NamedQuery(name = "ZUSDRA.findByIv34Kwskchfkd", query = "SELECT z FROM ZUSDRA z WHERE z.iv34Kwskchfkd = :iv34Kwskchfkd"),
    @NamedQuery(name = "ZUSDRA.findByVii1Kwskladfpp", query = "SELECT z FROM ZUSDRA z WHERE z.vii1Kwskladfpp = :vii1Kwskladfpp"),
    @NamedQuery(name = "ZUSDRA.findByVii2Kwskladfpub", query = "SELECT z FROM ZUSDRA z WHERE z.vii2Kwskladfpub = :vii2Kwskladfpub"),
    @NamedQuery(name = "ZUSDRA.findByVii5Kwskdprzpl", query = "SELECT z FROM ZUSDRA z WHERE z.vii5Kwskdprzpl = :vii5Kwskdprzpl"),
    @NamedQuery(name = "ZUSDRA.findByX3Podstwymch", query = "SELECT z FROM ZUSDRA z WHERE z.x3Podstwymch = :x3Podstwymch"),
    @NamedQuery(name = "ZUSDRA.findByX4Podstwymwyp", query = "SELECT z FROM ZUSDRA z WHERE z.x4Podstwymwyp = :x4Podstwymwyp"),
    @NamedQuery(name = "ZUSDRA.findByStatuszus", query = "SELECT z FROM ZUSDRA z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "ZUSDRA.findByStatusKontroli", query = "SELECT z FROM ZUSDRA z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "ZUSDRA.findByIdPlZusStatus", query = "SELECT z FROM ZUSDRA z WHERE z.idPlZusStatus = :idPlZusStatus"),
    @NamedQuery(name = "ZUSDRA.findByXiCzyopodatkSkala", query = "SELECT z FROM ZUSDRA z WHERE z.xiCzyopodatkSkala = :xiCzyopodatkSkala"),
    @NamedQuery(name = "ZUSDRA.findByXiKwotadochodumpSkala", query = "SELECT z FROM ZUSDRA z WHERE z.xiKwotadochodumpSkala = :xiKwotadochodumpSkala"),
    @NamedQuery(name = "ZUSDRA.findByXiPodstwymsklzdrSkala", query = "SELECT z FROM ZUSDRA z WHERE z.xiPodstwymsklzdrSkala = :xiPodstwymsklzdrSkala"),
    @NamedQuery(name = "ZUSDRA.findByXiKwotaskladkiSkala", query = "SELECT z FROM ZUSDRA z WHERE z.xiKwotaskladkiSkala = :xiKwotaskladkiSkala"),
    @NamedQuery(name = "ZUSDRA.findByXiCzyopodatkLiniowy", query = "SELECT z FROM ZUSDRA z WHERE z.xiCzyopodatkLiniowy = :xiCzyopodatkLiniowy"),
    @NamedQuery(name = "ZUSDRA.findByXiKwotadochodumpLiniowy", query = "SELECT z FROM ZUSDRA z WHERE z.xiKwotadochodumpLiniowy = :xiKwotadochodumpLiniowy"),
    @NamedQuery(name = "ZUSDRA.findByXiPodstwymsklzdrLiniowy", query = "SELECT z FROM ZUSDRA z WHERE z.xiPodstwymsklzdrLiniowy = :xiPodstwymsklzdrLiniowy"),
    @NamedQuery(name = "ZUSDRA.findByXiKwotaskladkiLiniowy", query = "SELECT z FROM ZUSDRA z WHERE z.xiKwotaskladkiLiniowy = :xiKwotaskladkiLiniowy"),
    @NamedQuery(name = "ZUSDRA.findByXiCzyopodatkKarta", query = "SELECT z FROM ZUSDRA z WHERE z.xiCzyopodatkKarta = :xiCzyopodatkKarta"),
    @NamedQuery(name = "ZUSDRA.findByXiPodstwymsklzdrKarta", query = "SELECT z FROM ZUSDRA z WHERE z.xiPodstwymsklzdrKarta = :xiPodstwymsklzdrKarta"),
    @NamedQuery(name = "ZUSDRA.findByXiKwotaskladkiKarta", query = "SELECT z FROM ZUSDRA z WHERE z.xiKwotaskladkiKarta = :xiKwotaskladkiKarta"),
    @NamedQuery(name = "ZUSDRA.findByXiCzyopodatkRyczalt", query = "SELECT z FROM ZUSDRA z WHERE z.xiCzyopodatkRyczalt = :xiCzyopodatkRyczalt"),
    @NamedQuery(name = "ZUSDRA.findByXiSumaprzychodowbrk", query = "SELECT z FROM ZUSDRA z WHERE z.xiSumaprzychodowbrk = :xiSumaprzychodowbrk"),
    @NamedQuery(name = "ZUSDRA.findByXiCzyoplacaniesklprk", query = "SELECT z FROM ZUSDRA z WHERE z.xiCzyoplacaniesklprk = :xiCzyoplacaniesklprk"),
    @NamedQuery(name = "ZUSDRA.findByXiKwotaprzychodowurk", query = "SELECT z FROM ZUSDRA z WHERE z.xiKwotaprzychodowurk = :xiKwotaprzychodowurk"),
    @NamedQuery(name = "ZUSDRA.findByXiPodstwymsklzdrRyczalt", query = "SELECT z FROM ZUSDRA z WHERE z.xiPodstwymsklzdrRyczalt = :xiPodstwymsklzdrRyczalt"),
    @NamedQuery(name = "ZUSDRA.findByXiKwotaskladkiRyczalt", query = "SELECT z FROM ZUSDRA z WHERE z.xiKwotaskladkiRyczalt = :xiKwotaskladkiRyczalt"),
    @NamedQuery(name = "ZUSDRA.findByXiCzyBezOpodatk", query = "SELECT z FROM ZUSDRA z WHERE z.xiCzyBezOpodatk = :xiCzyBezOpodatk"),
    @NamedQuery(name = "ZUSDRA.findByXiPodstwymsklzdrBezop", query = "SELECT z FROM ZUSDRA z WHERE z.xiPodstwymsklzdrBezop = :xiPodstwymsklzdrBezop"),
    @NamedQuery(name = "ZUSDRA.findByXiKwotaskladkiBezop", query = "SELECT z FROM ZUSDRA z WHERE z.xiKwotaskladkiBezop = :xiKwotaskladkiBezop"),
    @NamedQuery(name = "ZUSDRA.findByXiiRokrozliczeniasklzd", query = "SELECT z FROM ZUSDRA z WHERE z.xiiRokrozliczeniasklzd = :xiiRokrozliczeniasklzd"),
    @NamedQuery(name = "ZUSDRA.findByXiiKwotaprzychodowrr", query = "SELECT z FROM ZUSDRA z WHERE z.xiiKwotaprzychodowrr = :xiiKwotaprzychodowrr"),
    @NamedQuery(name = "ZUSDRA.findByXiiRocznaskladka", query = "SELECT z FROM ZUSDRA z WHERE z.xiiRocznaskladka = :xiiRocznaskladka"),
    @NamedQuery(name = "ZUSDRA.findByXiiSumamiesnalezskladek", query = "SELECT z FROM ZUSDRA z WHERE z.xiiSumamiesnalezskladek = :xiiSumamiesnalezskladek"),
    @NamedQuery(name = "ZUSDRA.findByXiiKwotadodoplaty", query = "SELECT z FROM ZUSDRA z WHERE z.xiiKwotadodoplaty = :xiiKwotadodoplaty")})
public class ZUSDRA implements Serializable {

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
    @Column(name = "I_1_TERMINDEKIRAP")
    private Character i1Termindekirap;
    @Size(max = 2)
    @Column(name = "I_2_1IDDEKLS", length = 2)
    private String i21iddekls;
    @Size(max = 6)
    @Column(name = "I_2_2OKRESDEKLAR", length = 6)
    private String i22okresdeklar;
    @Column(name = "I_3_DATANADANIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i3Datanadania;
    @Size(max = 20)
    @Column(name = "I_4_NALEPKAR", length = 20)
    private String i4Nalepkar;
    @Size(max = 12)
    @Column(name = "I_5_ZNAKINRDECPOK", length = 12)
    private String i5Znakinrdecpok;
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
    @Column(name = "III_1_LUBEZP")
    private Integer iii1Lubezp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_2_LPRNAPELNYW", precision = 8, scale = 2)
    private BigDecimal iii2Lprnapelnyw;
    @Column(name = "III_3_PLMASTPRCHR")
    private Character iii3Plmastprchr;
    @Column(name = "III_4_STOPASKLWYP", precision = 4, scale = 2)
    private BigDecimal iii4Stopasklwyp;
    @Column(name = "IV_1_SSKLUBEM", precision = 11, scale = 2)
    private BigDecimal iv1Ssklubem;
    @Column(name = "IV_2_SSKLUBR", precision = 11, scale = 2)
    private BigDecimal iv2Ssklubr;
    @Column(name = "IV_3_SSKLUBEMR", precision = 11, scale = 2)
    private BigDecimal iv3Ssklubemr;
    @Column(name = "IV_4_KWSKEMFUBD", precision = 11, scale = 2)
    private BigDecimal iv4Kwskemfubd;
    @Column(name = "IV_5_KWSKRENFUB", precision = 11, scale = 2)
    private BigDecimal iv5Kwskrenfub;
    @Column(name = "IV_6_SUMAKWEMRFPUB", precision = 11, scale = 2)
    private BigDecimal iv6Sumakwemrfpub;
    @Column(name = "IV_7_KWSKEMFPL", precision = 11, scale = 2)
    private BigDecimal iv7Kwskemfpl;
    @Column(name = "IV_8_KWSKRENFPL", precision = 11, scale = 2)
    private BigDecimal iv8Kwskrenfpl;
    @Column(name = "IV_9_SKWEMIRENPPL", precision = 11, scale = 2)
    private BigDecimal iv9Skwemirenppl;
    @Column(name = "IV_10_KWSKEMBPD", precision = 11, scale = 2)
    private BigDecimal iv10Kwskembpd;
    @Column(name = "IV_11_KWSKRENFBP", precision = 11, scale = 2)
    private BigDecimal iv11Kwskrenfbp;
    @Column(name = "IV_12_SUMAKWEMRPBP", precision = 11, scale = 2)
    private BigDecimal iv12Sumakwemrpbp;
    @Column(name = "IV_13_KWSKEMFPF", precision = 11, scale = 2)
    private BigDecimal iv13Kwskemfpf;
    @Column(name = "IV_14_KWSKRENFPF", precision = 11, scale = 2)
    private BigDecimal iv14Kwskrenfpf;
    @Column(name = "IV_15_SKWEMIRENPPF", precision = 11, scale = 2)
    private BigDecimal iv15Skwemirenppf;
    @Column(name = "IV_16_KWSKEMFKD", precision = 11, scale = 2)
    private BigDecimal iv16Kwskemfkd;
    @Column(name = "IV_17_KWSKRENFFK", precision = 11, scale = 2)
    private BigDecimal iv17Kwskrenffk;
    @Column(name = "IV_18_SKWEMIRENPFK", precision = 11, scale = 2)
    private BigDecimal iv18Skwemirenpfk;
    @Column(name = "IV_19_SSKLUBCH", precision = 11, scale = 2)
    private BigDecimal iv19Ssklubch;
    @Column(name = "IV_20_SSKLUBWYP", precision = 11, scale = 2)
    private BigDecimal iv20Ssklubwyp;
    @Column(name = "IV_21_SSKLUBCHWYP", precision = 11, scale = 2)
    private BigDecimal iv21Ssklubchwyp;
    @Column(name = "IV_22_KWSKCHUBD", precision = 11, scale = 2)
    private BigDecimal iv22Kwskchubd;
    @Column(name = "IV_23_KWSKWFUB", precision = 11, scale = 2)
    private BigDecimal iv23Kwskwfub;
    @Column(name = "IV_24_SKWCHIWYPPUB", precision = 11, scale = 2)
    private BigDecimal iv24Skwchiwyppub;
    @Column(name = "IV_25_KWSKWFPL", precision = 11, scale = 2)
    private BigDecimal iv25Kwskwfpl;
    @Column(name = "IV_26_SKWCHIWYPPPL", precision = 11, scale = 2)
    private BigDecimal iv26Skwchiwypppl;
    @Column(name = "IV_27_KWSKCHFPF", precision = 11, scale = 2)
    private BigDecimal iv27Kwskchfpf;
    @Column(name = "IV_28_KWSKWFPF", precision = 11, scale = 2)
    private BigDecimal iv28Kwskwfpf;
    @Column(name = "IV_29_SKWCHIWYPPPF", precision = 11, scale = 2)
    private BigDecimal iv29Skwchiwypppf;
    @Column(name = "IV_30_KWSKWFFK", precision = 11, scale = 2)
    private BigDecimal iv30Kwskwffk;
    @Column(name = "IV_31_SKWCHIWYPPFK", precision = 11, scale = 2)
    private BigDecimal iv31Skwchiwyppfk;
    @Column(name = "IV_32_KWSKSPOL", precision = 12, scale = 2)
    private BigDecimal iv32Kwskspol;
    @Column(name = "V_1_KWWYPSWZUBCH", precision = 11, scale = 2)
    private BigDecimal v1Kwwypswzubch;
    @Column(name = "V_2_KWNALWYNCH", precision = 11, scale = 2)
    private BigDecimal v2Kwnalwynch;
    @Column(name = "V_3_KWWYPSWZUBWYP", precision = 11, scale = 2)
    private BigDecimal v3Kwwypswzubwyp;
    @Column(name = "V_4_KWWYPSWFINPBP", precision = 11, scale = 2)
    private BigDecimal v4Kwwypswfinpbp;
    @Column(name = "V_5_LKWDOPOTRDRA", precision = 12, scale = 2)
    private BigDecimal v5Lkwdopotrdra;
    @Column(name = "VI_1_KWDOZWROTU_VI", precision = 12, scale = 2)
    private BigDecimal vi1KwdozwrotuVi;
    @Column(name = "VI_2_KWDOZAPP_VI", precision = 12, scale = 2)
    private BigDecimal vi2KwdozappVi;
    @Column(name = "VII_1_KWSKDPRZPL", precision = 10, scale = 2)
    private BigDecimal vii1Kwskdprzpl;
    @Column(name = "VII_2_KWSKLADFPFK", precision = 11, scale = 2)
    private BigDecimal vii2Kwskladfpfk;
    @Column(name = "VII_3_KWNALEZWY", precision = 10, scale = 2)
    private BigDecimal vii3Kwnalezwy;
    @Column(name = "VII_4_KWZAP", precision = 12, scale = 2)
    private BigDecimal vii4Kwzap;
    @Column(name = "VIII_1_KWNALSKLFP", precision = 11, scale = 2)
    private BigDecimal viii1Kwnalsklfp;
    @Column(name = "VIII_2_KWNALSKFGSP", precision = 11, scale = 2)
    private BigDecimal viii2Kwnalskfgsp;
    @Column(name = "VIII_3_KWZAPL_VIII", precision = 12, scale = 2)
    private BigDecimal viii3KwzaplViii;
    @Column(name = "IX_1_LSUMAKWDOZAPL", precision = 12, scale = 2)
    private BigDecimal ix1Lsumakwdozapl;
    @Column(name = "IX_1_KWDOZWROTU", precision = 13, scale = 2)
    private BigDecimal ix1Kwdozwrotu;
    @Column(name = "IX_2_KWDOZAPLATY", precision = 13, scale = 2)
    private BigDecimal ix2Kwdozaplaty;
    @Column(name = "X_1_KWDOPLNAUBSPOL", precision = 10, scale = 2)
    private BigDecimal x1Kwdoplnaubspol;
    @Column(name = "X_2_KWDOPLNAUBZDR", precision = 10, scale = 2)
    private BigDecimal x2Kwdoplnaubzdr;
    @Column(name = "X_3_KWDOPFPIFGSP", precision = 10, scale = 2)
    private BigDecimal x3Kwdopfpifgsp;
    @Column(name = "X_4_LKWDOPLAT", precision = 11, scale = 2)
    private BigDecimal x4Lkwdoplat;
    @Size(max = 4)
    @Column(name = "XI_1_1KODTYTUB", length = 4)
    private String xi11kodtytub;
    @Column(name = "XI_1_2PRDOEM")
    private Character xi12prdoem;
    @Column(name = "XI_1_3STNIEP")
    private Character xi13stniep;
    @Column(name = "XI_2_PODSTWYMEIR", precision = 10, scale = 2)
    private BigDecimal xi2Podstwymeir;
    @Column(name = "XI_3_PODSTWYMCHIW", precision = 10, scale = 2)
    private BigDecimal xi3Podstwymchiw;
    @Column(name = "XI_4_PODSTWYMZDR", precision = 10, scale = 2)
    private BigDecimal xi4Podstwymzdr;
    @Column(name = "XI_5_INFOPRRPOD")
    private Character xi5Infoprrpod;
    @Size(max = 5)
    @Column(name = "XII_1_LKARTEK_RCA", length = 5)
    private String xii1LkartekRca;
    @Size(max = 5)
    @Column(name = "XII_2_LKARTEK_RNA", length = 5)
    private String xii2LkartekRna;
    @Size(max = 5)
    @Column(name = "XII_3_LKARTEK_RZA", length = 5)
    private String xii3LkartekRza;
    @Size(max = 5)
    @Column(name = "XII_4_LKARTEK_RSA", length = 5)
    private String xii4LkartekRsa;
    @Size(max = 5)
    @Column(name = "XII_5_LKARTEK_RGA", length = 5)
    private String xii5LkartekRga;
    @Size(max = 5)
    @Column(name = "XII_6_LLICZKARTRAP", length = 5)
    private String xii6Lliczkartrap;
    @Size(max = 2)
    @Column(name = "XII_7_1IDDEKLS", length = 2)
    private String xii71iddekls;
    @Size(max = 6)
    @Column(name = "XII_7_2OKRESDEKLAR", length = 6)
    private String xii72okresdeklar;
    @Column(name = "XII_8_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date xii8Datawypel;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ZN_KOR")
    private Character znKor;
    @Column(name = "NRSCIEZWYL")
    private Integer nrsciezwyl;
    @Column(name = "SERIA")
    private Integer seria;
    @Column(name = "VII_3_KWSKLADFZBP", precision = 11, scale = 2)
    private BigDecimal vii3Kwskladfzbp;
    @Column(name = "IX_1_LPRSKLADFEP")
    private Integer ix1Lprskladfep;
    @Column(name = "IX_2_LSTANPRSZW")
    private Integer ix2Lstanprszw;
    @Column(name = "IX_3_SSKLADFEP", precision = 12, scale = 2)
    private BigDecimal ix3Sskladfep;
    @Column(name = "IV_25_KWSKCHPD", precision = 11, scale = 2)
    private BigDecimal iv25Kwskchpd;
    @Column(name = "IV_28_KWSKCHBPD", precision = 11, scale = 2)
    private BigDecimal iv28Kwskchbpd;
    @Column(name = "IV_29_KWSKWFBPD", precision = 11, scale = 2)
    private BigDecimal iv29Kwskwfbpd;
    @Column(name = "IV_30_SKWCHIWYPPBP", precision = 11, scale = 2)
    private BigDecimal iv30Skwchiwyppbp;
    @Column(name = "IV_34_KWSKCHFKD", precision = 11, scale = 2)
    private BigDecimal iv34Kwskchfkd;
    @Column(name = "VII_1_KWSKLADFPP", precision = 11, scale = 2)
    private BigDecimal vii1Kwskladfpp;
    @Column(name = "VII_2_KWSKLADFPUB", precision = 11, scale = 2)
    private BigDecimal vii2Kwskladfpub;
    @Column(name = "VII_5_KWSKDPRZPL", precision = 11, scale = 2)
    private BigDecimal vii5Kwskdprzpl;
    @Column(name = "X_3_PODSTWYMCH", precision = 10, scale = 2)
    private BigDecimal x3Podstwymch;
    @Column(name = "X_4_PODSTWYMWYP", precision = 10, scale = 2)
    private BigDecimal x4Podstwymwyp;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_PL_ZUS_STATUS")
    private Character idPlZusStatus;
    @Column(name = "XI_CZYOPODATK_SKALA")
    private Character xiCzyopodatkSkala;
    @Column(name = "XI_KWOTADOCHODUMP_SKALA", precision = 10, scale = 2)
    private BigDecimal xiKwotadochodumpSkala;
    @Column(name = "XI_PODSTWYMSKLZDR_SKALA", precision = 10, scale = 2)
    private BigDecimal xiPodstwymsklzdrSkala;
    @Column(name = "XI_KWOTASKLADKI_SKALA", precision = 10, scale = 2)
    private BigDecimal xiKwotaskladkiSkala;
    @Column(name = "XI_CZYOPODATK_LINIOWY")
    private Character xiCzyopodatkLiniowy;
    @Column(name = "XI_KWOTADOCHODUMP_LINIOWY", precision = 10, scale = 2)
    private BigDecimal xiKwotadochodumpLiniowy;
    @Column(name = "XI_PODSTWYMSKLZDR_LINIOWY", precision = 10, scale = 2)
    private BigDecimal xiPodstwymsklzdrLiniowy;
    @Column(name = "XI_KWOTASKLADKI_LINIOWY", precision = 10, scale = 2)
    private BigDecimal xiKwotaskladkiLiniowy;
    @Column(name = "XI_CZYOPODATK_KARTA")
    private Character xiCzyopodatkKarta;
    @Column(name = "XI_PODSTWYMSKLZDR_KARTA", precision = 10, scale = 2)
    private BigDecimal xiPodstwymsklzdrKarta;
    @Column(name = "XI_KWOTASKLADKI_KARTA", precision = 10, scale = 2)
    private BigDecimal xiKwotaskladkiKarta;
    @Column(name = "XI_CZYOPODATK_RYCZALT")
    private Character xiCzyopodatkRyczalt;
    @Column(name = "XI_SUMAPRZYCHODOWBRK", precision = 10, scale = 2)
    private BigDecimal xiSumaprzychodowbrk;
    @Column(name = "XI_CZYOPLACANIESKLPRK")
    private Character xiCzyoplacaniesklprk;
    @Column(name = "XI_KWOTAPRZYCHODOWURK", precision = 10, scale = 2)
    private BigDecimal xiKwotaprzychodowurk;
    @Column(name = "XI_PODSTWYMSKLZDR_RYCZALT", precision = 10, scale = 2)
    private BigDecimal xiPodstwymsklzdrRyczalt;
    @Column(name = "XI_KWOTASKLADKI_RYCZALT", precision = 10, scale = 2)
    private BigDecimal xiKwotaskladkiRyczalt;
    @Column(name = "XI_CZY_BEZ_OPODATK")
    private Character xiCzyBezOpodatk;
    @Column(name = "XI_PODSTWYMSKLZDR_BEZOP", precision = 10, scale = 2)
    private BigDecimal xiPodstwymsklzdrBezop;
    @Column(name = "XI_KWOTASKLADKI_BEZOP", precision = 10, scale = 2)
    private BigDecimal xiKwotaskladkiBezop;
    @Column(name = "XII_ROKROZLICZENIASKLZD")
    private Short xiiRokrozliczeniasklzd;
    @Column(name = "XII_KWOTAPRZYCHODOWRR", precision = 11, scale = 2)
    private BigDecimal xiiKwotaprzychodowrr;
    @Column(name = "XII_ROCZNASKLADKA", precision = 11, scale = 2)
    private BigDecimal xiiRocznaskladka;
    @Column(name = "XII_SUMAMIESNALEZSKLADEK", precision = 11, scale = 2)
    private BigDecimal xiiSumamiesnalezskladek;
    @Column(name = "XII_KWOTADODOPLATY", precision = 11, scale = 2)
    private BigDecimal xiiKwotadodoplaty;

    public ZUSDRA() {
    }

    public ZUSDRA(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public ZUSDRA(Integer idDokument, int idPlatnik) {
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

    public Character getI1Termindekirap() {
        return i1Termindekirap;
    }

    public void setI1Termindekirap(Character i1Termindekirap) {
        this.i1Termindekirap = i1Termindekirap;
    }

    public String getI21iddekls() {
        return i21iddekls;
    }

    public void setI21iddekls(String i21iddekls) {
        this.i21iddekls = i21iddekls;
    }

    public String getI22okresdeklar() {
        return i22okresdeklar;
    }

    public void setI22okresdeklar(String i22okresdeklar) {
        this.i22okresdeklar = i22okresdeklar;
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

    public String getI5Znakinrdecpok() {
        return i5Znakinrdecpok;
    }

    public void setI5Znakinrdecpok(String i5Znakinrdecpok) {
        this.i5Znakinrdecpok = i5Znakinrdecpok;
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

    public Integer getIii1Lubezp() {
        return iii1Lubezp;
    }

    public void setIii1Lubezp(Integer iii1Lubezp) {
        this.iii1Lubezp = iii1Lubezp;
    }

    public BigDecimal getIii2Lprnapelnyw() {
        return iii2Lprnapelnyw;
    }

    public void setIii2Lprnapelnyw(BigDecimal iii2Lprnapelnyw) {
        this.iii2Lprnapelnyw = iii2Lprnapelnyw;
    }

    public Character getIii3Plmastprchr() {
        return iii3Plmastprchr;
    }

    public void setIii3Plmastprchr(Character iii3Plmastprchr) {
        this.iii3Plmastprchr = iii3Plmastprchr;
    }

    public BigDecimal getIii4Stopasklwyp() {
        return iii4Stopasklwyp;
    }

    public void setIii4Stopasklwyp(BigDecimal iii4Stopasklwyp) {
        this.iii4Stopasklwyp = iii4Stopasklwyp;
    }

    public BigDecimal getIv1Ssklubem() {
        return iv1Ssklubem;
    }

    public void setIv1Ssklubem(BigDecimal iv1Ssklubem) {
        this.iv1Ssklubem = iv1Ssklubem;
    }

    public BigDecimal getIv2Ssklubr() {
        return iv2Ssklubr;
    }

    public void setIv2Ssklubr(BigDecimal iv2Ssklubr) {
        this.iv2Ssklubr = iv2Ssklubr;
    }

    public BigDecimal getIv3Ssklubemr() {
        return iv3Ssklubemr;
    }

    public void setIv3Ssklubemr(BigDecimal iv3Ssklubemr) {
        this.iv3Ssklubemr = iv3Ssklubemr;
    }

    public BigDecimal getIv4Kwskemfubd() {
        return iv4Kwskemfubd;
    }

    public void setIv4Kwskemfubd(BigDecimal iv4Kwskemfubd) {
        this.iv4Kwskemfubd = iv4Kwskemfubd;
    }

    public BigDecimal getIv5Kwskrenfub() {
        return iv5Kwskrenfub;
    }

    public void setIv5Kwskrenfub(BigDecimal iv5Kwskrenfub) {
        this.iv5Kwskrenfub = iv5Kwskrenfub;
    }

    public BigDecimal getIv6Sumakwemrfpub() {
        return iv6Sumakwemrfpub;
    }

    public void setIv6Sumakwemrfpub(BigDecimal iv6Sumakwemrfpub) {
        this.iv6Sumakwemrfpub = iv6Sumakwemrfpub;
    }

    public BigDecimal getIv7Kwskemfpl() {
        return iv7Kwskemfpl;
    }

    public void setIv7Kwskemfpl(BigDecimal iv7Kwskemfpl) {
        this.iv7Kwskemfpl = iv7Kwskemfpl;
    }

    public BigDecimal getIv8Kwskrenfpl() {
        return iv8Kwskrenfpl;
    }

    public void setIv8Kwskrenfpl(BigDecimal iv8Kwskrenfpl) {
        this.iv8Kwskrenfpl = iv8Kwskrenfpl;
    }

    public BigDecimal getIv9Skwemirenppl() {
        return iv9Skwemirenppl;
    }

    public void setIv9Skwemirenppl(BigDecimal iv9Skwemirenppl) {
        this.iv9Skwemirenppl = iv9Skwemirenppl;
    }

    public BigDecimal getIv10Kwskembpd() {
        return iv10Kwskembpd;
    }

    public void setIv10Kwskembpd(BigDecimal iv10Kwskembpd) {
        this.iv10Kwskembpd = iv10Kwskembpd;
    }

    public BigDecimal getIv11Kwskrenfbp() {
        return iv11Kwskrenfbp;
    }

    public void setIv11Kwskrenfbp(BigDecimal iv11Kwskrenfbp) {
        this.iv11Kwskrenfbp = iv11Kwskrenfbp;
    }

    public BigDecimal getIv12Sumakwemrpbp() {
        return iv12Sumakwemrpbp;
    }

    public void setIv12Sumakwemrpbp(BigDecimal iv12Sumakwemrpbp) {
        this.iv12Sumakwemrpbp = iv12Sumakwemrpbp;
    }

    public BigDecimal getIv13Kwskemfpf() {
        return iv13Kwskemfpf;
    }

    public void setIv13Kwskemfpf(BigDecimal iv13Kwskemfpf) {
        this.iv13Kwskemfpf = iv13Kwskemfpf;
    }

    public BigDecimal getIv14Kwskrenfpf() {
        return iv14Kwskrenfpf;
    }

    public void setIv14Kwskrenfpf(BigDecimal iv14Kwskrenfpf) {
        this.iv14Kwskrenfpf = iv14Kwskrenfpf;
    }

    public BigDecimal getIv15Skwemirenppf() {
        return iv15Skwemirenppf;
    }

    public void setIv15Skwemirenppf(BigDecimal iv15Skwemirenppf) {
        this.iv15Skwemirenppf = iv15Skwemirenppf;
    }

    public BigDecimal getIv16Kwskemfkd() {
        return iv16Kwskemfkd;
    }

    public void setIv16Kwskemfkd(BigDecimal iv16Kwskemfkd) {
        this.iv16Kwskemfkd = iv16Kwskemfkd;
    }

    public BigDecimal getIv17Kwskrenffk() {
        return iv17Kwskrenffk;
    }

    public void setIv17Kwskrenffk(BigDecimal iv17Kwskrenffk) {
        this.iv17Kwskrenffk = iv17Kwskrenffk;
    }

    public BigDecimal getIv18Skwemirenpfk() {
        return iv18Skwemirenpfk;
    }

    public void setIv18Skwemirenpfk(BigDecimal iv18Skwemirenpfk) {
        this.iv18Skwemirenpfk = iv18Skwemirenpfk;
    }

    public BigDecimal getIv19Ssklubch() {
        return iv19Ssklubch;
    }

    public void setIv19Ssklubch(BigDecimal iv19Ssklubch) {
        this.iv19Ssklubch = iv19Ssklubch;
    }

    public BigDecimal getIv20Ssklubwyp() {
        return iv20Ssklubwyp;
    }

    public void setIv20Ssklubwyp(BigDecimal iv20Ssklubwyp) {
        this.iv20Ssklubwyp = iv20Ssklubwyp;
    }

    public BigDecimal getIv21Ssklubchwyp() {
        return iv21Ssklubchwyp;
    }

    public void setIv21Ssklubchwyp(BigDecimal iv21Ssklubchwyp) {
        this.iv21Ssklubchwyp = iv21Ssklubchwyp;
    }

    public BigDecimal getIv22Kwskchubd() {
        return iv22Kwskchubd;
    }

    public void setIv22Kwskchubd(BigDecimal iv22Kwskchubd) {
        this.iv22Kwskchubd = iv22Kwskchubd;
    }

    public BigDecimal getIv23Kwskwfub() {
        return iv23Kwskwfub;
    }

    public void setIv23Kwskwfub(BigDecimal iv23Kwskwfub) {
        this.iv23Kwskwfub = iv23Kwskwfub;
    }

    public BigDecimal getIv24Skwchiwyppub() {
        return iv24Skwchiwyppub;
    }

    public void setIv24Skwchiwyppub(BigDecimal iv24Skwchiwyppub) {
        this.iv24Skwchiwyppub = iv24Skwchiwyppub;
    }

    public BigDecimal getIv25Kwskwfpl() {
        return iv25Kwskwfpl;
    }

    public void setIv25Kwskwfpl(BigDecimal iv25Kwskwfpl) {
        this.iv25Kwskwfpl = iv25Kwskwfpl;
    }

    public BigDecimal getIv26Skwchiwypppl() {
        return iv26Skwchiwypppl;
    }

    public void setIv26Skwchiwypppl(BigDecimal iv26Skwchiwypppl) {
        this.iv26Skwchiwypppl = iv26Skwchiwypppl;
    }

    public BigDecimal getIv27Kwskchfpf() {
        return iv27Kwskchfpf;
    }

    public void setIv27Kwskchfpf(BigDecimal iv27Kwskchfpf) {
        this.iv27Kwskchfpf = iv27Kwskchfpf;
    }

    public BigDecimal getIv28Kwskwfpf() {
        return iv28Kwskwfpf;
    }

    public void setIv28Kwskwfpf(BigDecimal iv28Kwskwfpf) {
        this.iv28Kwskwfpf = iv28Kwskwfpf;
    }

    public BigDecimal getIv29Skwchiwypppf() {
        return iv29Skwchiwypppf;
    }

    public void setIv29Skwchiwypppf(BigDecimal iv29Skwchiwypppf) {
        this.iv29Skwchiwypppf = iv29Skwchiwypppf;
    }

    public BigDecimal getIv30Kwskwffk() {
        return iv30Kwskwffk;
    }

    public void setIv30Kwskwffk(BigDecimal iv30Kwskwffk) {
        this.iv30Kwskwffk = iv30Kwskwffk;
    }

    public BigDecimal getIv31Skwchiwyppfk() {
        return iv31Skwchiwyppfk;
    }

    public void setIv31Skwchiwyppfk(BigDecimal iv31Skwchiwyppfk) {
        this.iv31Skwchiwyppfk = iv31Skwchiwyppfk;
    }

    public BigDecimal getIv32Kwskspol() {
        return iv32Kwskspol;
    }

    public void setIv32Kwskspol(BigDecimal iv32Kwskspol) {
        this.iv32Kwskspol = iv32Kwskspol;
    }

    public BigDecimal getV1Kwwypswzubch() {
        return v1Kwwypswzubch;
    }

    public void setV1Kwwypswzubch(BigDecimal v1Kwwypswzubch) {
        this.v1Kwwypswzubch = v1Kwwypswzubch;
    }

    public BigDecimal getV2Kwnalwynch() {
        return v2Kwnalwynch;
    }

    public void setV2Kwnalwynch(BigDecimal v2Kwnalwynch) {
        this.v2Kwnalwynch = v2Kwnalwynch;
    }

    public BigDecimal getV3Kwwypswzubwyp() {
        return v3Kwwypswzubwyp;
    }

    public void setV3Kwwypswzubwyp(BigDecimal v3Kwwypswzubwyp) {
        this.v3Kwwypswzubwyp = v3Kwwypswzubwyp;
    }

    public BigDecimal getV4Kwwypswfinpbp() {
        return v4Kwwypswfinpbp;
    }

    public void setV4Kwwypswfinpbp(BigDecimal v4Kwwypswfinpbp) {
        this.v4Kwwypswfinpbp = v4Kwwypswfinpbp;
    }

    public BigDecimal getV5Lkwdopotrdra() {
        return v5Lkwdopotrdra;
    }

    public void setV5Lkwdopotrdra(BigDecimal v5Lkwdopotrdra) {
        this.v5Lkwdopotrdra = v5Lkwdopotrdra;
    }

    public BigDecimal getVi1KwdozwrotuVi() {
        return vi1KwdozwrotuVi;
    }

    public void setVi1KwdozwrotuVi(BigDecimal vi1KwdozwrotuVi) {
        this.vi1KwdozwrotuVi = vi1KwdozwrotuVi;
    }

    public BigDecimal getVi2KwdozappVi() {
        return vi2KwdozappVi;
    }

    public void setVi2KwdozappVi(BigDecimal vi2KwdozappVi) {
        this.vi2KwdozappVi = vi2KwdozappVi;
    }

    public BigDecimal getVii1Kwskdprzpl() {
        return vii1Kwskdprzpl;
    }

    public void setVii1Kwskdprzpl(BigDecimal vii1Kwskdprzpl) {
        this.vii1Kwskdprzpl = vii1Kwskdprzpl;
    }

    public BigDecimal getVii2Kwskladfpfk() {
        return vii2Kwskladfpfk;
    }

    public void setVii2Kwskladfpfk(BigDecimal vii2Kwskladfpfk) {
        this.vii2Kwskladfpfk = vii2Kwskladfpfk;
    }

    public BigDecimal getVii3Kwnalezwy() {
        return vii3Kwnalezwy;
    }

    public void setVii3Kwnalezwy(BigDecimal vii3Kwnalezwy) {
        this.vii3Kwnalezwy = vii3Kwnalezwy;
    }

    public BigDecimal getVii4Kwzap() {
        return vii4Kwzap;
    }

    public void setVii4Kwzap(BigDecimal vii4Kwzap) {
        this.vii4Kwzap = vii4Kwzap;
    }

    public BigDecimal getViii1Kwnalsklfp() {
        return viii1Kwnalsklfp;
    }

    public void setViii1Kwnalsklfp(BigDecimal viii1Kwnalsklfp) {
        this.viii1Kwnalsklfp = viii1Kwnalsklfp;
    }

    public BigDecimal getViii2Kwnalskfgsp() {
        return viii2Kwnalskfgsp;
    }

    public void setViii2Kwnalskfgsp(BigDecimal viii2Kwnalskfgsp) {
        this.viii2Kwnalskfgsp = viii2Kwnalskfgsp;
    }

    public BigDecimal getViii3KwzaplViii() {
        return viii3KwzaplViii;
    }

    public void setViii3KwzaplViii(BigDecimal viii3KwzaplViii) {
        this.viii3KwzaplViii = viii3KwzaplViii;
    }

    public BigDecimal getIx1Lsumakwdozapl() {
        return ix1Lsumakwdozapl;
    }

    public void setIx1Lsumakwdozapl(BigDecimal ix1Lsumakwdozapl) {
        this.ix1Lsumakwdozapl = ix1Lsumakwdozapl;
    }

    public BigDecimal getIx1Kwdozwrotu() {
        return ix1Kwdozwrotu;
    }

    public void setIx1Kwdozwrotu(BigDecimal ix1Kwdozwrotu) {
        this.ix1Kwdozwrotu = ix1Kwdozwrotu;
    }

    public BigDecimal getIx2Kwdozaplaty() {
        return ix2Kwdozaplaty;
    }

    public void setIx2Kwdozaplaty(BigDecimal ix2Kwdozaplaty) {
        this.ix2Kwdozaplaty = ix2Kwdozaplaty;
    }

    public BigDecimal getX1Kwdoplnaubspol() {
        return x1Kwdoplnaubspol;
    }

    public void setX1Kwdoplnaubspol(BigDecimal x1Kwdoplnaubspol) {
        this.x1Kwdoplnaubspol = x1Kwdoplnaubspol;
    }

    public BigDecimal getX2Kwdoplnaubzdr() {
        return x2Kwdoplnaubzdr;
    }

    public void setX2Kwdoplnaubzdr(BigDecimal x2Kwdoplnaubzdr) {
        this.x2Kwdoplnaubzdr = x2Kwdoplnaubzdr;
    }

    public BigDecimal getX3Kwdopfpifgsp() {
        return x3Kwdopfpifgsp;
    }

    public void setX3Kwdopfpifgsp(BigDecimal x3Kwdopfpifgsp) {
        this.x3Kwdopfpifgsp = x3Kwdopfpifgsp;
    }

    public BigDecimal getX4Lkwdoplat() {
        return x4Lkwdoplat;
    }

    public void setX4Lkwdoplat(BigDecimal x4Lkwdoplat) {
        this.x4Lkwdoplat = x4Lkwdoplat;
    }

    public String getXi11kodtytub() {
        return xi11kodtytub;
    }

    public void setXi11kodtytub(String xi11kodtytub) {
        this.xi11kodtytub = xi11kodtytub;
    }

    public Character getXi12prdoem() {
        return xi12prdoem;
    }

    public void setXi12prdoem(Character xi12prdoem) {
        this.xi12prdoem = xi12prdoem;
    }

    public Character getXi13stniep() {
        return xi13stniep;
    }

    public void setXi13stniep(Character xi13stniep) {
        this.xi13stniep = xi13stniep;
    }

    public BigDecimal getXi2Podstwymeir() {
        return xi2Podstwymeir;
    }

    public void setXi2Podstwymeir(BigDecimal xi2Podstwymeir) {
        this.xi2Podstwymeir = xi2Podstwymeir;
    }

    public BigDecimal getXi3Podstwymchiw() {
        return xi3Podstwymchiw;
    }

    public void setXi3Podstwymchiw(BigDecimal xi3Podstwymchiw) {
        this.xi3Podstwymchiw = xi3Podstwymchiw;
    }

    public BigDecimal getXi4Podstwymzdr() {
        return xi4Podstwymzdr;
    }

    public void setXi4Podstwymzdr(BigDecimal xi4Podstwymzdr) {
        this.xi4Podstwymzdr = xi4Podstwymzdr;
    }

    public Character getXi5Infoprrpod() {
        return xi5Infoprrpod;
    }

    public void setXi5Infoprrpod(Character xi5Infoprrpod) {
        this.xi5Infoprrpod = xi5Infoprrpod;
    }

    public String getXii1LkartekRca() {
        return xii1LkartekRca;
    }

    public void setXii1LkartekRca(String xii1LkartekRca) {
        this.xii1LkartekRca = xii1LkartekRca;
    }

    public String getXii2LkartekRna() {
        return xii2LkartekRna;
    }

    public void setXii2LkartekRna(String xii2LkartekRna) {
        this.xii2LkartekRna = xii2LkartekRna;
    }

    public String getXii3LkartekRza() {
        return xii3LkartekRza;
    }

    public void setXii3LkartekRza(String xii3LkartekRza) {
        this.xii3LkartekRza = xii3LkartekRza;
    }

    public String getXii4LkartekRsa() {
        return xii4LkartekRsa;
    }

    public void setXii4LkartekRsa(String xii4LkartekRsa) {
        this.xii4LkartekRsa = xii4LkartekRsa;
    }

    public String getXii5LkartekRga() {
        return xii5LkartekRga;
    }

    public void setXii5LkartekRga(String xii5LkartekRga) {
        this.xii5LkartekRga = xii5LkartekRga;
    }

    public String getXii6Lliczkartrap() {
        return xii6Lliczkartrap;
    }

    public void setXii6Lliczkartrap(String xii6Lliczkartrap) {
        this.xii6Lliczkartrap = xii6Lliczkartrap;
    }

    public String getXii71iddekls() {
        return xii71iddekls;
    }

    public void setXii71iddekls(String xii71iddekls) {
        this.xii71iddekls = xii71iddekls;
    }

    public String getXii72okresdeklar() {
        return xii72okresdeklar;
    }

    public void setXii72okresdeklar(String xii72okresdeklar) {
        this.xii72okresdeklar = xii72okresdeklar;
    }

    public Date getXii8Datawypel() {
        return xii8Datawypel;
    }

    public void setXii8Datawypel(Date xii8Datawypel) {
        this.xii8Datawypel = xii8Datawypel;
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

    public Character getZnKor() {
        return znKor;
    }

    public void setZnKor(Character znKor) {
        this.znKor = znKor;
    }

    public Integer getNrsciezwyl() {
        return nrsciezwyl;
    }

    public void setNrsciezwyl(Integer nrsciezwyl) {
        this.nrsciezwyl = nrsciezwyl;
    }

    public Integer getSeria() {
        return seria;
    }

    public void setSeria(Integer seria) {
        this.seria = seria;
    }

    public BigDecimal getVii3Kwskladfzbp() {
        return vii3Kwskladfzbp;
    }

    public void setVii3Kwskladfzbp(BigDecimal vii3Kwskladfzbp) {
        this.vii3Kwskladfzbp = vii3Kwskladfzbp;
    }

    public Integer getIx1Lprskladfep() {
        return ix1Lprskladfep;
    }

    public void setIx1Lprskladfep(Integer ix1Lprskladfep) {
        this.ix1Lprskladfep = ix1Lprskladfep;
    }

    public Integer getIx2Lstanprszw() {
        return ix2Lstanprszw;
    }

    public void setIx2Lstanprszw(Integer ix2Lstanprszw) {
        this.ix2Lstanprszw = ix2Lstanprszw;
    }

    public BigDecimal getIx3Sskladfep() {
        return ix3Sskladfep;
    }

    public void setIx3Sskladfep(BigDecimal ix3Sskladfep) {
        this.ix3Sskladfep = ix3Sskladfep;
    }

    public BigDecimal getIv25Kwskchpd() {
        return iv25Kwskchpd;
    }

    public void setIv25Kwskchpd(BigDecimal iv25Kwskchpd) {
        this.iv25Kwskchpd = iv25Kwskchpd;
    }

    public BigDecimal getIv28Kwskchbpd() {
        return iv28Kwskchbpd;
    }

    public void setIv28Kwskchbpd(BigDecimal iv28Kwskchbpd) {
        this.iv28Kwskchbpd = iv28Kwskchbpd;
    }

    public BigDecimal getIv29Kwskwfbpd() {
        return iv29Kwskwfbpd;
    }

    public void setIv29Kwskwfbpd(BigDecimal iv29Kwskwfbpd) {
        this.iv29Kwskwfbpd = iv29Kwskwfbpd;
    }

    public BigDecimal getIv30Skwchiwyppbp() {
        return iv30Skwchiwyppbp;
    }

    public void setIv30Skwchiwyppbp(BigDecimal iv30Skwchiwyppbp) {
        this.iv30Skwchiwyppbp = iv30Skwchiwyppbp;
    }

    public BigDecimal getIv34Kwskchfkd() {
        return iv34Kwskchfkd;
    }

    public void setIv34Kwskchfkd(BigDecimal iv34Kwskchfkd) {
        this.iv34Kwskchfkd = iv34Kwskchfkd;
    }

    public BigDecimal getVii1Kwskladfpp() {
        return vii1Kwskladfpp;
    }

    public void setVii1Kwskladfpp(BigDecimal vii1Kwskladfpp) {
        this.vii1Kwskladfpp = vii1Kwskladfpp;
    }

    public BigDecimal getVii2Kwskladfpub() {
        return vii2Kwskladfpub;
    }

    public void setVii2Kwskladfpub(BigDecimal vii2Kwskladfpub) {
        this.vii2Kwskladfpub = vii2Kwskladfpub;
    }

    public BigDecimal getVii5Kwskdprzpl() {
        return vii5Kwskdprzpl;
    }

    public void setVii5Kwskdprzpl(BigDecimal vii5Kwskdprzpl) {
        this.vii5Kwskdprzpl = vii5Kwskdprzpl;
    }

    public BigDecimal getX3Podstwymch() {
        return x3Podstwymch;
    }

    public void setX3Podstwymch(BigDecimal x3Podstwymch) {
        this.x3Podstwymch = x3Podstwymch;
    }

    public BigDecimal getX4Podstwymwyp() {
        return x4Podstwymwyp;
    }

    public void setX4Podstwymwyp(BigDecimal x4Podstwymwyp) {
        this.x4Podstwymwyp = x4Podstwymwyp;
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

    public Character getXiCzyopodatkSkala() {
        return xiCzyopodatkSkala;
    }

    public void setXiCzyopodatkSkala(Character xiCzyopodatkSkala) {
        this.xiCzyopodatkSkala = xiCzyopodatkSkala;
    }

    public BigDecimal getXiKwotadochodumpSkala() {
        return xiKwotadochodumpSkala;
    }

    public void setXiKwotadochodumpSkala(BigDecimal xiKwotadochodumpSkala) {
        this.xiKwotadochodumpSkala = xiKwotadochodumpSkala;
    }

    public BigDecimal getXiPodstwymsklzdrSkala() {
        return xiPodstwymsklzdrSkala;
    }

    public void setXiPodstwymsklzdrSkala(BigDecimal xiPodstwymsklzdrSkala) {
        this.xiPodstwymsklzdrSkala = xiPodstwymsklzdrSkala;
    }

    public BigDecimal getXiKwotaskladkiSkala() {
        return xiKwotaskladkiSkala;
    }

    public void setXiKwotaskladkiSkala(BigDecimal xiKwotaskladkiSkala) {
        this.xiKwotaskladkiSkala = xiKwotaskladkiSkala;
    }

    public Character getXiCzyopodatkLiniowy() {
        return xiCzyopodatkLiniowy;
    }

    public void setXiCzyopodatkLiniowy(Character xiCzyopodatkLiniowy) {
        this.xiCzyopodatkLiniowy = xiCzyopodatkLiniowy;
    }

    public BigDecimal getXiKwotadochodumpLiniowy() {
        return xiKwotadochodumpLiniowy;
    }

    public void setXiKwotadochodumpLiniowy(BigDecimal xiKwotadochodumpLiniowy) {
        this.xiKwotadochodumpLiniowy = xiKwotadochodumpLiniowy;
    }

    public BigDecimal getXiPodstwymsklzdrLiniowy() {
        return xiPodstwymsklzdrLiniowy;
    }

    public void setXiPodstwymsklzdrLiniowy(BigDecimal xiPodstwymsklzdrLiniowy) {
        this.xiPodstwymsklzdrLiniowy = xiPodstwymsklzdrLiniowy;
    }

    public BigDecimal getXiKwotaskladkiLiniowy() {
        return xiKwotaskladkiLiniowy;
    }

    public void setXiKwotaskladkiLiniowy(BigDecimal xiKwotaskladkiLiniowy) {
        this.xiKwotaskladkiLiniowy = xiKwotaskladkiLiniowy;
    }

    public Character getXiCzyopodatkKarta() {
        return xiCzyopodatkKarta;
    }

    public void setXiCzyopodatkKarta(Character xiCzyopodatkKarta) {
        this.xiCzyopodatkKarta = xiCzyopodatkKarta;
    }

    public BigDecimal getXiPodstwymsklzdrKarta() {
        return xiPodstwymsklzdrKarta;
    }

    public void setXiPodstwymsklzdrKarta(BigDecimal xiPodstwymsklzdrKarta) {
        this.xiPodstwymsklzdrKarta = xiPodstwymsklzdrKarta;
    }

    public BigDecimal getXiKwotaskladkiKarta() {
        return xiKwotaskladkiKarta;
    }

    public void setXiKwotaskladkiKarta(BigDecimal xiKwotaskladkiKarta) {
        this.xiKwotaskladkiKarta = xiKwotaskladkiKarta;
    }

    public Character getXiCzyopodatkRyczalt() {
        return xiCzyopodatkRyczalt;
    }

    public void setXiCzyopodatkRyczalt(Character xiCzyopodatkRyczalt) {
        this.xiCzyopodatkRyczalt = xiCzyopodatkRyczalt;
    }

    public BigDecimal getXiSumaprzychodowbrk() {
        return xiSumaprzychodowbrk;
    }

    public void setXiSumaprzychodowbrk(BigDecimal xiSumaprzychodowbrk) {
        this.xiSumaprzychodowbrk = xiSumaprzychodowbrk;
    }

    public Character getXiCzyoplacaniesklprk() {
        return xiCzyoplacaniesklprk;
    }

    public void setXiCzyoplacaniesklprk(Character xiCzyoplacaniesklprk) {
        this.xiCzyoplacaniesklprk = xiCzyoplacaniesklprk;
    }

    public BigDecimal getXiKwotaprzychodowurk() {
        return xiKwotaprzychodowurk;
    }

    public void setXiKwotaprzychodowurk(BigDecimal xiKwotaprzychodowurk) {
        this.xiKwotaprzychodowurk = xiKwotaprzychodowurk;
    }

    public BigDecimal getXiPodstwymsklzdrRyczalt() {
        return xiPodstwymsklzdrRyczalt;
    }

    public void setXiPodstwymsklzdrRyczalt(BigDecimal xiPodstwymsklzdrRyczalt) {
        this.xiPodstwymsklzdrRyczalt = xiPodstwymsklzdrRyczalt;
    }

    public BigDecimal getXiKwotaskladkiRyczalt() {
        return xiKwotaskladkiRyczalt;
    }

    public void setXiKwotaskladkiRyczalt(BigDecimal xiKwotaskladkiRyczalt) {
        this.xiKwotaskladkiRyczalt = xiKwotaskladkiRyczalt;
    }

    public Character getXiCzyBezOpodatk() {
        return xiCzyBezOpodatk;
    }

    public void setXiCzyBezOpodatk(Character xiCzyBezOpodatk) {
        this.xiCzyBezOpodatk = xiCzyBezOpodatk;
    }

    public BigDecimal getXiPodstwymsklzdrBezop() {
        return xiPodstwymsklzdrBezop;
    }

    public void setXiPodstwymsklzdrBezop(BigDecimal xiPodstwymsklzdrBezop) {
        this.xiPodstwymsklzdrBezop = xiPodstwymsklzdrBezop;
    }

    public BigDecimal getXiKwotaskladkiBezop() {
        return xiKwotaskladkiBezop;
    }

    public void setXiKwotaskladkiBezop(BigDecimal xiKwotaskladkiBezop) {
        this.xiKwotaskladkiBezop = xiKwotaskladkiBezop;
    }

    public Short getXiiRokrozliczeniasklzd() {
        return xiiRokrozliczeniasklzd;
    }

    public void setXiiRokrozliczeniasklzd(Short xiiRokrozliczeniasklzd) {
        this.xiiRokrozliczeniasklzd = xiiRokrozliczeniasklzd;
    }

    public BigDecimal getXiiKwotaprzychodowrr() {
        return xiiKwotaprzychodowrr;
    }

    public void setXiiKwotaprzychodowrr(BigDecimal xiiKwotaprzychodowrr) {
        this.xiiKwotaprzychodowrr = xiiKwotaprzychodowrr;
    }

    public BigDecimal getXiiRocznaskladka() {
        return xiiRocznaskladka;
    }

    public void setXiiRocznaskladka(BigDecimal xiiRocznaskladka) {
        this.xiiRocznaskladka = xiiRocznaskladka;
    }

    public BigDecimal getXiiSumamiesnalezskladek() {
        return xiiSumamiesnalezskladek;
    }

    public void setXiiSumamiesnalezskladek(BigDecimal xiiSumamiesnalezskladek) {
        this.xiiSumamiesnalezskladek = xiiSumamiesnalezskladek;
    }

    public BigDecimal getXiiKwotadodoplaty() {
        return xiiKwotadodoplaty;
    }

    public void setXiiKwotadodoplaty(BigDecimal xiiKwotadodoplaty) {
        this.xiiKwotadodoplaty = xiiKwotadodoplaty;
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
        if (!(object instanceof ZUSDRA)) {
            return false;
        }
        ZUSDRA other = (ZUSDRA) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ZUSDRA{" + "i22okresdeklar=" + i22okresdeklar + ",idPlatnik=" + idPlatnik + ", i1Termindekirap=" + i1Termindekirap + ", i3Datanadania=" + i3Datanadania + ", ii1Nip=" + ii1Nip + ", ii2Regon=" + ii2Regon + ", ii6Nazwaskr=" + ii6Nazwaskr + ", ii7Nazwisko=" + ii7Nazwisko + ", ii8Imiepierw=" + ii8Imiepierw + ", vi2KwdozappVi=" + vi2KwdozappVi + ", ix2Kwdozaplaty=" + ix2Kwdozaplaty + '}';
    }

  
    
}
