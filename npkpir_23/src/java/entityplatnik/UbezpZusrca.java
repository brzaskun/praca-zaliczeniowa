/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "UBEZP_ZUSRCA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpZusrca.findAll", query = "SELECT u FROM UbezpZusrca u"),
    @NamedQuery(name = "UbezpZusrca.findByIdDokument", query = "SELECT u FROM UbezpZusrca u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpZusrca.findByIdPlatnik", query = "SELECT u FROM UbezpZusrca u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpZusrca.findByIdDokNad", query = "SELECT u FROM UbezpZusrca u WHERE u.idDokNad = :idDokNad"),
    @NamedQuery(name = "UbezpZusrca.findByIdUbezpieczony", query = "SELECT u FROM UbezpZusrca u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpZusrca.findByIiiA1Nazwisko", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiA1Nazwisko = :iiiA1Nazwisko"),
    @NamedQuery(name = "UbezpZusrca.findByIiiA2Imiepierw", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiA2Imiepierw = :iiiA2Imiepierw"),
    @NamedQuery(name = "UbezpZusrca.findByIiiA3Typid", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiA3Typid = :iiiA3Typid"),
    @NamedQuery(name = "UbezpZusrca.findByIiiA4Identyfik", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiA4Identyfik = :iiiA4Identyfik"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB11kodtytub", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB11kodtytub = :iiiB11kodtytub"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB12prdoem", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB12prdoem = :iiiB12prdoem"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB13stniep", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB13stniep = :iiiB13stniep"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB2Infoprrpod", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB2Infoprrpod = :iiiB2Infoprrpod"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB31wymczprl", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB31wymczprl = :iiiB31wymczprl"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB32wymczprm", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB32wymczprm = :iiiB32wymczprm"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB4Podwymer", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB4Podwymer = :iiiB4Podwymer"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB5Podwymciw", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB5Podwymciw = :iiiB5Podwymciw"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB6Podwymzdr", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB6Podwymzdr = :iiiB6Podwymzdr"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB7KwskleuR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB7KwskleuR = :iiiB7KwskleuR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB8KwsklruR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB8KwsklruR = :iiiB8KwsklruR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB9KwsklchR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB9KwsklchR = :iiiB9KwsklchR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB10Kwsklzdr", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB10Kwsklzdr = :iiiB10Kwsklzdr"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB11KwsklepR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB11KwsklepR = :iiiB11KwsklepR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB12KwsklrpR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB12KwsklrpR = :iiiB12KwsklrpR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB13Kwsklwyp", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB13Kwsklwyp = :iiiB13Kwsklwyp"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB14Kwobproge", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB14Kwobproge = :iiiB14Kwobproge"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB15Lkwskl", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB15Lkwskl = :iiiB15Lkwskl"),
    @NamedQuery(name = "UbezpZusrca.findByIiiC1Loszasrodz", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiC1Loszasrodz = :iiiC1Loszasrodz"),
    @NamedQuery(name = "UbezpZusrca.findByIiiC2Kwwypzrodo", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiC2Kwwypzrodo = :iiiC2Kwwypzrodo"),
    @NamedQuery(name = "UbezpZusrca.findByIiiC3Kwwypzwych", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiC3Kwwypzwych = :iiiC3Kwwypzwych"),
    @NamedQuery(name = "UbezpZusrca.findByIiiC4Loszaspiel", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiC4Loszaspiel = :iiiC4Loszaspiel"),
    @NamedQuery(name = "UbezpZusrca.findByIiiC5Kwwypzpieo", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiC5Kwwypzpieo = :iiiC5Kwwypzpieo"),
    @NamedQuery(name = "UbezpZusrca.findByIiiC6Lkwwypz", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiC6Lkwwypz = :iiiC6Lkwwypz"),
    @NamedQuery(name = "UbezpZusrca.findByStatuswr", query = "SELECT u FROM UbezpZusrca u WHERE u.statuswr = :statuswr"),
    @NamedQuery(name = "UbezpZusrca.findByStatuspt", query = "SELECT u FROM UbezpZusrca u WHERE u.statuspt = :statuspt"),
    @NamedQuery(name = "UbezpZusrca.findByStatusws", query = "SELECT u FROM UbezpZusrca u WHERE u.statusws = :statusws"),
    @NamedQuery(name = "UbezpZusrca.findByInserttmp", query = "SELECT u FROM UbezpZusrca u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpZusrca.findByNrsciezwyl", query = "SELECT u FROM UbezpZusrca u WHERE u.nrsciezwyl = :nrsciezwyl"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB5Podwymch", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB5Podwymch = :iiiB5Podwymch"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB6Podwymwy", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB6Podwymwy = :iiiB6Podwymwy"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB7KwskleuRN", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB7KwskleuRN = :iiiB7KwskleuRN"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB8KwsklruRN", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB8KwsklruRN = :iiiB8KwsklruRN"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB9KwsklchuRN", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB9KwsklchuRN = :iiiB9KwsklchuRN"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB10KwsklwyuR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB10KwsklwyuR = :iiiB10KwsklwyuR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB11KwsklepRN", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB11KwsklepRN = :iiiB11KwsklepRN"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB12KwsklrpRN", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB12KwsklrpRN = :iiiB12KwsklrpRN"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB13KwsklchpR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB13KwsklchpR = :iiiB13KwsklchpR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB14KwsklwypR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB14KwsklwypR = :iiiB14KwsklwypR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB15KwsklebpR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB15KwsklebpR = :iiiB15KwsklebpR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB16KwsklrbpR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB16KwsklrbpR = :iiiB16KwsklrbpR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB17KwsklchbpR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB17KwsklchbpR = :iiiB17KwsklchbpR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB18KwsklwybpR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB18KwsklwybpR = :iiiB18KwsklwybpR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB19KwsklepfronR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB19KwsklepfronR = :iiiB19KwsklepfronR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB20KwsklrpfronR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB20KwsklrpfronR = :iiiB20KwsklrpfronR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB21KwsklchpfronR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB21KwsklchpfronR = :iiiB21KwsklchpfronR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB22KwsklwypfronR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB22KwsklwypfronR = :iiiB22KwsklwypfronR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB23KwsklefkR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB23KwsklefkR = :iiiB23KwsklefkR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB24KwsklrfkR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB24KwsklrfkR = :iiiB24KwsklrfkR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB25KwsklchfkR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB25KwsklchfkR = :iiiB25KwsklchfkR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiB26KwsklwyfkR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiB26KwsklwyfkR = :iiiB26KwsklwyfkR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiC2KwsklzpR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiC2KwsklzpR = :iiiC2KwsklzpR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiC3KwsklzbpR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiC3KwsklzbpR = :iiiC3KwsklzbpR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiC4KwsklzuR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiC4KwsklzuR = :iiiC4KwsklzuR"),
    @NamedQuery(name = "UbezpZusrca.findByIiiC5KwsklzfkR", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiC5KwsklzfkR = :iiiC5KwsklzfkR"),
    @NamedQuery(name = "UbezpZusrca.findByStatuszus", query = "SELECT u FROM UbezpZusrca u WHERE u.statuszus = :statuszus"),
    @NamedQuery(name = "UbezpZusrca.findByStatusKontroli", query = "SELECT u FROM UbezpZusrca u WHERE u.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "UbezpZusrca.findByIdUbZusStatus", query = "SELECT u FROM UbezpZusrca u WHERE u.idUbZusStatus = :idUbZusStatus"),
    @NamedQuery(name = "UbezpZusrca.findByNrBlok", query = "SELECT u FROM UbezpZusrca u WHERE u.nrBlok = :nrBlok"),
    @NamedQuery(name = "UbezpZusrca.findByIiiPpk", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiPpk = :iiiPpk"),
    @NamedQuery(name = "UbezpZusrca.findByIiiCzyopodatkSkala", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiCzyopodatkSkala = :iiiCzyopodatkSkala"),
    @NamedQuery(name = "UbezpZusrca.findByIiiKwotadochodumpSkala", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiKwotadochodumpSkala = :iiiKwotadochodumpSkala"),
    @NamedQuery(name = "UbezpZusrca.findByIiiPodstwymsklzdrSkala", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiPodstwymsklzdrSkala = :iiiPodstwymsklzdrSkala"),
    @NamedQuery(name = "UbezpZusrca.findByIiiKwotaskladkiSkala", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiKwotaskladkiSkala = :iiiKwotaskladkiSkala"),
    @NamedQuery(name = "UbezpZusrca.findByIiiCzyopodatkLiniowy", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiCzyopodatkLiniowy = :iiiCzyopodatkLiniowy"),
    @NamedQuery(name = "UbezpZusrca.findByIiiKwotadochodumpLiniowy", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiKwotadochodumpLiniowy = :iiiKwotadochodumpLiniowy"),
    @NamedQuery(name = "UbezpZusrca.findByIiiPodstwymsklzdrLiniowy", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiPodstwymsklzdrLiniowy = :iiiPodstwymsklzdrLiniowy"),
    @NamedQuery(name = "UbezpZusrca.findByIiiKwotaskladkiLiniowy", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiKwotaskladkiLiniowy = :iiiKwotaskladkiLiniowy"),
    @NamedQuery(name = "UbezpZusrca.findByIiiCzyopodatkKarta", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiCzyopodatkKarta = :iiiCzyopodatkKarta"),
    @NamedQuery(name = "UbezpZusrca.findByIiiPodstwymsklzdrKarta", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiPodstwymsklzdrKarta = :iiiPodstwymsklzdrKarta"),
    @NamedQuery(name = "UbezpZusrca.findByIiiKwotaskladkiKarta", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiKwotaskladkiKarta = :iiiKwotaskladkiKarta"),
    @NamedQuery(name = "UbezpZusrca.findByIiiCzyopodatkRyczalt", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiCzyopodatkRyczalt = :iiiCzyopodatkRyczalt"),
    @NamedQuery(name = "UbezpZusrca.findByIiiSumaprzychodowbrk", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiSumaprzychodowbrk = :iiiSumaprzychodowbrk"),
    @NamedQuery(name = "UbezpZusrca.findByIiiCzyoplacaniesklprk", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiCzyoplacaniesklprk = :iiiCzyoplacaniesklprk"),
    @NamedQuery(name = "UbezpZusrca.findByIiiKwotaprzychodowurk", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiKwotaprzychodowurk = :iiiKwotaprzychodowurk"),
    @NamedQuery(name = "UbezpZusrca.findByIiiPodstwymsklzdrRyczalt", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiPodstwymsklzdrRyczalt = :iiiPodstwymsklzdrRyczalt"),
    @NamedQuery(name = "UbezpZusrca.findByIiiKwotaskladkiRyczalt", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiKwotaskladkiRyczalt = :iiiKwotaskladkiRyczalt"),
    @NamedQuery(name = "UbezpZusrca.findByIiiCzyBezOpodatk", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiCzyBezOpodatk = :iiiCzyBezOpodatk"),
    @NamedQuery(name = "UbezpZusrca.findByIiiPodstwymsklzdrBezop", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiPodstwymsklzdrBezop = :iiiPodstwymsklzdrBezop"),
    @NamedQuery(name = "UbezpZusrca.findByIiiKwotaskladkiBezop", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiKwotaskladkiBezop = :iiiKwotaskladkiBezop"),
    @NamedQuery(name = "UbezpZusrca.findByIiiRokrozliczeniasklzd", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiRokrozliczeniasklzd = :iiiRokrozliczeniasklzd"),
    @NamedQuery(name = "UbezpZusrca.findByIiiKwotaprzychodowrr", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiKwotaprzychodowrr = :iiiKwotaprzychodowrr"),
    @NamedQuery(name = "UbezpZusrca.findByIiiRocznaskladka", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiRocznaskladka = :iiiRocznaskladka"),
    @NamedQuery(name = "UbezpZusrca.findByIiiSumamiesnalezskladek", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiSumamiesnalezskladek = :iiiSumamiesnalezskladek"),
    @NamedQuery(name = "UbezpZusrca.findByIiiKwotadodoplaty", query = "SELECT u FROM UbezpZusrca u WHERE u.iiiKwotadodoplaty = :iiiKwotadodoplaty")})
public class UbezpZusrca implements Serializable {

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
    @Column(name = "III_B_1_1KODTYTUB", length = 4)
    private String iiiB11kodtytub;
    @Column(name = "III_B_1_2PRDOEM")
    private Character iiiB12prdoem;
    @Column(name = "III_B_1_3STNIEP")
    private Character iiiB13stniep;
    @Column(name = "III_B_2_INFOPRRPOD")
    private Character iiiB2Infoprrpod;
    @Size(max = 3)
    @Column(name = "III_B_3_1WYMCZPRL", length = 3)
    private String iiiB31wymczprl;
    @Size(max = 3)
    @Column(name = "III_B_3_2WYMCZPRM", length = 3)
    private String iiiB32wymczprm;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_B_4_PODWYMER", precision = 8, scale = 2)
    private BigDecimal iiiB4Podwymer;
    @Column(name = "III_B_5_PODWYMCIW", precision = 8, scale = 2)
    private BigDecimal iiiB5Podwymciw;
    @Column(name = "III_B_6_PODWYMZDR", precision = 8, scale = 2)
    private BigDecimal iiiB6Podwymzdr;
    @Column(name = "III_B_7_KWSKLEU_R", precision = 7, scale = 2)
    private BigDecimal iiiB7KwskleuR;
    @Column(name = "III_B_8_KWSKLRU_R", precision = 7, scale = 2)
    private BigDecimal iiiB8KwsklruR;
    @Column(name = "III_B_9_KWSKLCH_R", precision = 7, scale = 2)
    private BigDecimal iiiB9KwsklchR;
    @Column(name = "III_B_10_KWSKLZDR", precision = 7, scale = 2)
    private BigDecimal iiiB10Kwsklzdr;
    @Column(name = "III_B_11_KWSKLEP_R", precision = 7, scale = 2)
    private BigDecimal iiiB11KwsklepR;
    @Column(name = "III_B_12_KWSKLRP_R", precision = 7, scale = 2)
    private BigDecimal iiiB12KwsklrpR;
    @Column(name = "III_B_13_KWSKLWYP", precision = 7, scale = 2)
    private BigDecimal iiiB13Kwsklwyp;
    @Column(name = "III_B_14_KWOBPROGE", precision = 7, scale = 2)
    private BigDecimal iiiB14Kwobproge;
    @Column(name = "III_B_15_LKWSKL", precision = 8, scale = 2)
    private BigDecimal iiiB15Lkwskl;
    @Size(max = 2)
    @Column(name = "III_C_1_LOSZASRODZ", length = 2)
    private String iiiC1Loszasrodz;
    @Column(name = "III_C_2_KWWYPZRODO", precision = 7, scale = 2)
    private BigDecimal iiiC2Kwwypzrodo;
    @Column(name = "III_C_3_KWWYPZWYCH", precision = 7, scale = 2)
    private BigDecimal iiiC3Kwwypzwych;
    @Size(max = 2)
    @Column(name = "III_C_4_LOSZASPIEL", length = 2)
    private String iiiC4Loszaspiel;
    @Column(name = "III_C_5_KWWYPZPIEO", precision = 7, scale = 2)
    private BigDecimal iiiC5Kwwypzpieo;
    @Column(name = "III_C_6_LKWWYPZ", precision = 8, scale = 2)
    private BigDecimal iiiC6Lkwwypz;
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
    @Column(name = "III_B_5_PODWYMCH", precision = 8, scale = 2)
    private BigDecimal iiiB5Podwymch;
    @Column(name = "III_B_6_PODWYMWY", precision = 8, scale = 2)
    private BigDecimal iiiB6Podwymwy;
    @Column(name = "III_B_7_KWSKLEU_R_N", precision = 7, scale = 2)
    private BigDecimal iiiB7KwskleuRN;
    @Column(name = "III_B_8_KWSKLRU_R_N", precision = 7, scale = 2)
    private BigDecimal iiiB8KwsklruRN;
    @Column(name = "III_B_9_KWSKLCHU_R_N", precision = 7, scale = 2)
    private BigDecimal iiiB9KwsklchuRN;
    @Column(name = "III_B_10_KWSKLWYU_R", precision = 7, scale = 2)
    private BigDecimal iiiB10KwsklwyuR;
    @Column(name = "III_B_11_KWSKLEP_R_N", precision = 7, scale = 2)
    private BigDecimal iiiB11KwsklepRN;
    @Column(name = "III_B_12_KWSKLRP_R_N", precision = 7, scale = 2)
    private BigDecimal iiiB12KwsklrpRN;
    @Column(name = "III_B_13_KWSKLCHP_R", precision = 7, scale = 2)
    private BigDecimal iiiB13KwsklchpR;
    @Column(name = "III_B_14_KWSKLWYP_R", precision = 7, scale = 2)
    private BigDecimal iiiB14KwsklwypR;
    @Column(name = "III_B_15_KWSKLEBP_R", precision = 7, scale = 2)
    private BigDecimal iiiB15KwsklebpR;
    @Column(name = "III_B_16_KWSKLRBP_R", precision = 7, scale = 2)
    private BigDecimal iiiB16KwsklrbpR;
    @Column(name = "III_B_17_KWSKLCHBP_R", precision = 7, scale = 2)
    private BigDecimal iiiB17KwsklchbpR;
    @Column(name = "III_B_18_KWSKLWYBP_R", precision = 7, scale = 2)
    private BigDecimal iiiB18KwsklwybpR;
    @Column(name = "III_B_19_KWSKLEPFRON_R", precision = 7, scale = 2)
    private BigDecimal iiiB19KwsklepfronR;
    @Column(name = "III_B_20_KWSKLRPFRON_R", precision = 7, scale = 2)
    private BigDecimal iiiB20KwsklrpfronR;
    @Column(name = "III_B_21_KWSKLCHPFRON_R", precision = 7, scale = 2)
    private BigDecimal iiiB21KwsklchpfronR;
    @Column(name = "III_B_22_KWSKLWYPFRON_R", precision = 7, scale = 2)
    private BigDecimal iiiB22KwsklwypfronR;
    @Column(name = "III_B_23_KWSKLEFK_R", precision = 7, scale = 2)
    private BigDecimal iiiB23KwsklefkR;
    @Column(name = "III_B_24_KWSKLRFK_R", precision = 7, scale = 2)
    private BigDecimal iiiB24KwsklrfkR;
    @Column(name = "III_B_25_KWSKLCHFK_R", precision = 7, scale = 2)
    private BigDecimal iiiB25KwsklchfkR;
    @Column(name = "III_B_26_KWSKLWYFK_R", precision = 7, scale = 2)
    private BigDecimal iiiB26KwsklwyfkR;
    @Column(name = "III_C_2_KWSKLZP_R", precision = 7, scale = 2)
    private BigDecimal iiiC2KwsklzpR;
    @Column(name = "III_C_3_KWSKLZBP_R", precision = 7, scale = 2)
    private BigDecimal iiiC3KwsklzbpR;
    @Column(name = "III_C_4_KWSKLZU_R", precision = 7, scale = 2)
    private BigDecimal iiiC4KwsklzuR;
    @Column(name = "III_C_5_KWSKLZFK_R", precision = 7, scale = 2)
    private BigDecimal iiiC5KwsklzfkR;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_UB_ZUS_STATUS")
    private Character idUbZusStatus;
    @Column(name = "NR_BLOK")
    private Integer nrBlok;
    @Column(name = "III_PPK", precision = 7, scale = 2)
    private BigDecimal iiiPpk;
    @Column(name = "III_CZYOPODATK_SKALA")
    private Character iiiCzyopodatkSkala;
    @Column(name = "III_KWOTADOCHODUMP_SKALA", precision = 10, scale = 2)
    private BigDecimal iiiKwotadochodumpSkala;
    @Column(name = "III_PODSTWYMSKLZDR_SKALA", precision = 10, scale = 2)
    private BigDecimal iiiPodstwymsklzdrSkala;
    @Column(name = "III_KWOTASKLADKI_SKALA", precision = 10, scale = 2)
    private BigDecimal iiiKwotaskladkiSkala;
    @Column(name = "III_CZYOPODATK_LINIOWY")
    private Character iiiCzyopodatkLiniowy;
    @Column(name = "III_KWOTADOCHODUMP_LINIOWY", precision = 10, scale = 2)
    private BigDecimal iiiKwotadochodumpLiniowy;
    @Column(name = "III_PODSTWYMSKLZDR_LINIOWY", precision = 10, scale = 2)
    private BigDecimal iiiPodstwymsklzdrLiniowy;
    @Column(name = "III_KWOTASKLADKI_LINIOWY", precision = 10, scale = 2)
    private BigDecimal iiiKwotaskladkiLiniowy;
    @Column(name = "III_CZYOPODATK_KARTA")
    private Character iiiCzyopodatkKarta;
    @Column(name = "III_PODSTWYMSKLZDR_KARTA", precision = 10, scale = 2)
    private BigDecimal iiiPodstwymsklzdrKarta;
    @Column(name = "III_KWOTASKLADKI_KARTA", precision = 10, scale = 2)
    private BigDecimal iiiKwotaskladkiKarta;
    @Column(name = "III_CZYOPODATK_RYCZALT")
    private Character iiiCzyopodatkRyczalt;
    @Column(name = "III_SUMAPRZYCHODOWBRK", precision = 10, scale = 2)
    private BigDecimal iiiSumaprzychodowbrk;
    @Column(name = "III_CZYOPLACANIESKLPRK")
    private Character iiiCzyoplacaniesklprk;
    @Column(name = "III_KWOTAPRZYCHODOWURK", precision = 10, scale = 2)
    private BigDecimal iiiKwotaprzychodowurk;
    @Column(name = "III_PODSTWYMSKLZDR_RYCZALT", precision = 10, scale = 2)
    private BigDecimal iiiPodstwymsklzdrRyczalt;
    @Column(name = "III_KWOTASKLADKI_RYCZALT", precision = 10, scale = 2)
    private BigDecimal iiiKwotaskladkiRyczalt;
    @Column(name = "III_CZY_BEZ_OPODATK")
    private Character iiiCzyBezOpodatk;
    @Column(name = "III_PODSTWYMSKLZDR_BEZOP", precision = 10, scale = 2)
    private BigDecimal iiiPodstwymsklzdrBezop;
    @Column(name = "III_KWOTASKLADKI_BEZOP", precision = 10, scale = 2)
    private BigDecimal iiiKwotaskladkiBezop;
    @Column(name = "III_ROKROZLICZENIASKLZD")
    private Short iiiRokrozliczeniasklzd;
    @Column(name = "III_KWOTAPRZYCHODOWRR", precision = 11, scale = 2)
    private BigDecimal iiiKwotaprzychodowrr;
    @Column(name = "III_ROCZNASKLADKA", precision = 11, scale = 2)
    private BigDecimal iiiRocznaskladka;
    @Column(name = "III_SUMAMIESNALEZSKLADEK", precision = 11, scale = 2)
    private BigDecimal iiiSumamiesnalezskladek;
    @Column(name = "III_KWOTADODOPLATY", precision = 11, scale = 2)
    private BigDecimal iiiKwotadodoplaty;

    public UbezpZusrca() {
    }

    public UbezpZusrca(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public UbezpZusrca(Integer idDokument, int idPlatnik, int idDokNad, int idUbezpieczony, int statusws) {
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

    public String getIiiB11kodtytub() {
        return iiiB11kodtytub;
    }

    public void setIiiB11kodtytub(String iiiB11kodtytub) {
        this.iiiB11kodtytub = iiiB11kodtytub;
    }

    public Character getIiiB12prdoem() {
        return iiiB12prdoem;
    }

    public void setIiiB12prdoem(Character iiiB12prdoem) {
        this.iiiB12prdoem = iiiB12prdoem;
    }

    public Character getIiiB13stniep() {
        return iiiB13stniep;
    }

    public void setIiiB13stniep(Character iiiB13stniep) {
        this.iiiB13stniep = iiiB13stniep;
    }

    public Character getIiiB2Infoprrpod() {
        return iiiB2Infoprrpod;
    }

    public void setIiiB2Infoprrpod(Character iiiB2Infoprrpod) {
        this.iiiB2Infoprrpod = iiiB2Infoprrpod;
    }

    public String getIiiB31wymczprl() {
        return iiiB31wymczprl;
    }

    public void setIiiB31wymczprl(String iiiB31wymczprl) {
        this.iiiB31wymczprl = iiiB31wymczprl;
    }

    public String getIiiB32wymczprm() {
        return iiiB32wymczprm;
    }

    public void setIiiB32wymczprm(String iiiB32wymczprm) {
        this.iiiB32wymczprm = iiiB32wymczprm;
    }

    public BigDecimal getIiiB4Podwymer() {
        return iiiB4Podwymer;
    }

    public void setIiiB4Podwymer(BigDecimal iiiB4Podwymer) {
        this.iiiB4Podwymer = iiiB4Podwymer;
    }

    public BigDecimal getIiiB5Podwymciw() {
        return iiiB5Podwymciw;
    }

    public void setIiiB5Podwymciw(BigDecimal iiiB5Podwymciw) {
        this.iiiB5Podwymciw = iiiB5Podwymciw;
    }

    public BigDecimal getIiiB6Podwymzdr() {
        return iiiB6Podwymzdr;
    }

    public void setIiiB6Podwymzdr(BigDecimal iiiB6Podwymzdr) {
        this.iiiB6Podwymzdr = iiiB6Podwymzdr;
    }

    public BigDecimal getIiiB7KwskleuR() {
        return iiiB7KwskleuR;
    }

    public void setIiiB7KwskleuR(BigDecimal iiiB7KwskleuR) {
        this.iiiB7KwskleuR = iiiB7KwskleuR;
    }

    public BigDecimal getIiiB8KwsklruR() {
        return iiiB8KwsklruR;
    }

    public void setIiiB8KwsklruR(BigDecimal iiiB8KwsklruR) {
        this.iiiB8KwsklruR = iiiB8KwsklruR;
    }

    public BigDecimal getIiiB9KwsklchR() {
        return iiiB9KwsklchR;
    }

    public void setIiiB9KwsklchR(BigDecimal iiiB9KwsklchR) {
        this.iiiB9KwsklchR = iiiB9KwsklchR;
    }

    public BigDecimal getIiiB10Kwsklzdr() {
        return iiiB10Kwsklzdr;
    }

    public void setIiiB10Kwsklzdr(BigDecimal iiiB10Kwsklzdr) {
        this.iiiB10Kwsklzdr = iiiB10Kwsklzdr;
    }

    public BigDecimal getIiiB11KwsklepR() {
        return iiiB11KwsklepR;
    }

    public void setIiiB11KwsklepR(BigDecimal iiiB11KwsklepR) {
        this.iiiB11KwsklepR = iiiB11KwsklepR;
    }

    public BigDecimal getIiiB12KwsklrpR() {
        return iiiB12KwsklrpR;
    }

    public void setIiiB12KwsklrpR(BigDecimal iiiB12KwsklrpR) {
        this.iiiB12KwsklrpR = iiiB12KwsklrpR;
    }

    public BigDecimal getIiiB13Kwsklwyp() {
        return iiiB13Kwsklwyp;
    }

    public void setIiiB13Kwsklwyp(BigDecimal iiiB13Kwsklwyp) {
        this.iiiB13Kwsklwyp = iiiB13Kwsklwyp;
    }

    public BigDecimal getIiiB14Kwobproge() {
        return iiiB14Kwobproge;
    }

    public void setIiiB14Kwobproge(BigDecimal iiiB14Kwobproge) {
        this.iiiB14Kwobproge = iiiB14Kwobproge;
    }

    public BigDecimal getIiiB15Lkwskl() {
        return iiiB15Lkwskl;
    }

    public void setIiiB15Lkwskl(BigDecimal iiiB15Lkwskl) {
        this.iiiB15Lkwskl = iiiB15Lkwskl;
    }

    public String getIiiC1Loszasrodz() {
        return iiiC1Loszasrodz;
    }

    public void setIiiC1Loszasrodz(String iiiC1Loszasrodz) {
        this.iiiC1Loszasrodz = iiiC1Loszasrodz;
    }

    public BigDecimal getIiiC2Kwwypzrodo() {
        return iiiC2Kwwypzrodo;
    }

    public void setIiiC2Kwwypzrodo(BigDecimal iiiC2Kwwypzrodo) {
        this.iiiC2Kwwypzrodo = iiiC2Kwwypzrodo;
    }

    public BigDecimal getIiiC3Kwwypzwych() {
        return iiiC3Kwwypzwych;
    }

    public void setIiiC3Kwwypzwych(BigDecimal iiiC3Kwwypzwych) {
        this.iiiC3Kwwypzwych = iiiC3Kwwypzwych;
    }

    public String getIiiC4Loszaspiel() {
        return iiiC4Loszaspiel;
    }

    public void setIiiC4Loszaspiel(String iiiC4Loszaspiel) {
        this.iiiC4Loszaspiel = iiiC4Loszaspiel;
    }

    public BigDecimal getIiiC5Kwwypzpieo() {
        return iiiC5Kwwypzpieo;
    }

    public void setIiiC5Kwwypzpieo(BigDecimal iiiC5Kwwypzpieo) {
        this.iiiC5Kwwypzpieo = iiiC5Kwwypzpieo;
    }

    public BigDecimal getIiiC6Lkwwypz() {
        return iiiC6Lkwwypz;
    }

    public void setIiiC6Lkwwypz(BigDecimal iiiC6Lkwwypz) {
        this.iiiC6Lkwwypz = iiiC6Lkwwypz;
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

    public BigDecimal getIiiB5Podwymch() {
        return iiiB5Podwymch;
    }

    public void setIiiB5Podwymch(BigDecimal iiiB5Podwymch) {
        this.iiiB5Podwymch = iiiB5Podwymch;
    }

    public BigDecimal getIiiB6Podwymwy() {
        return iiiB6Podwymwy;
    }

    public void setIiiB6Podwymwy(BigDecimal iiiB6Podwymwy) {
        this.iiiB6Podwymwy = iiiB6Podwymwy;
    }

    public BigDecimal getIiiB7KwskleuRN() {
        return iiiB7KwskleuRN;
    }

    public void setIiiB7KwskleuRN(BigDecimal iiiB7KwskleuRN) {
        this.iiiB7KwskleuRN = iiiB7KwskleuRN;
    }

    public BigDecimal getIiiB8KwsklruRN() {
        return iiiB8KwsklruRN;
    }

    public void setIiiB8KwsklruRN(BigDecimal iiiB8KwsklruRN) {
        this.iiiB8KwsklruRN = iiiB8KwsklruRN;
    }

    public BigDecimal getIiiB9KwsklchuRN() {
        return iiiB9KwsklchuRN;
    }

    public void setIiiB9KwsklchuRN(BigDecimal iiiB9KwsklchuRN) {
        this.iiiB9KwsklchuRN = iiiB9KwsklchuRN;
    }

    public BigDecimal getIiiB10KwsklwyuR() {
        return iiiB10KwsklwyuR;
    }

    public void setIiiB10KwsklwyuR(BigDecimal iiiB10KwsklwyuR) {
        this.iiiB10KwsklwyuR = iiiB10KwsklwyuR;
    }

    public BigDecimal getIiiB11KwsklepRN() {
        return iiiB11KwsklepRN;
    }

    public void setIiiB11KwsklepRN(BigDecimal iiiB11KwsklepRN) {
        this.iiiB11KwsklepRN = iiiB11KwsklepRN;
    }

    public BigDecimal getIiiB12KwsklrpRN() {
        return iiiB12KwsklrpRN;
    }

    public void setIiiB12KwsklrpRN(BigDecimal iiiB12KwsklrpRN) {
        this.iiiB12KwsklrpRN = iiiB12KwsklrpRN;
    }

    public BigDecimal getIiiB13KwsklchpR() {
        return iiiB13KwsklchpR;
    }

    public void setIiiB13KwsklchpR(BigDecimal iiiB13KwsklchpR) {
        this.iiiB13KwsklchpR = iiiB13KwsklchpR;
    }

    public BigDecimal getIiiB14KwsklwypR() {
        return iiiB14KwsklwypR;
    }

    public void setIiiB14KwsklwypR(BigDecimal iiiB14KwsklwypR) {
        this.iiiB14KwsklwypR = iiiB14KwsklwypR;
    }

    public BigDecimal getIiiB15KwsklebpR() {
        return iiiB15KwsklebpR;
    }

    public void setIiiB15KwsklebpR(BigDecimal iiiB15KwsklebpR) {
        this.iiiB15KwsklebpR = iiiB15KwsklebpR;
    }

    public BigDecimal getIiiB16KwsklrbpR() {
        return iiiB16KwsklrbpR;
    }

    public void setIiiB16KwsklrbpR(BigDecimal iiiB16KwsklrbpR) {
        this.iiiB16KwsklrbpR = iiiB16KwsklrbpR;
    }

    public BigDecimal getIiiB17KwsklchbpR() {
        return iiiB17KwsklchbpR;
    }

    public void setIiiB17KwsklchbpR(BigDecimal iiiB17KwsklchbpR) {
        this.iiiB17KwsklchbpR = iiiB17KwsklchbpR;
    }

    public BigDecimal getIiiB18KwsklwybpR() {
        return iiiB18KwsklwybpR;
    }

    public void setIiiB18KwsklwybpR(BigDecimal iiiB18KwsklwybpR) {
        this.iiiB18KwsklwybpR = iiiB18KwsklwybpR;
    }

    public BigDecimal getIiiB19KwsklepfronR() {
        return iiiB19KwsklepfronR;
    }

    public void setIiiB19KwsklepfronR(BigDecimal iiiB19KwsklepfronR) {
        this.iiiB19KwsklepfronR = iiiB19KwsklepfronR;
    }

    public BigDecimal getIiiB20KwsklrpfronR() {
        return iiiB20KwsklrpfronR;
    }

    public void setIiiB20KwsklrpfronR(BigDecimal iiiB20KwsklrpfronR) {
        this.iiiB20KwsklrpfronR = iiiB20KwsklrpfronR;
    }

    public BigDecimal getIiiB21KwsklchpfronR() {
        return iiiB21KwsklchpfronR;
    }

    public void setIiiB21KwsklchpfronR(BigDecimal iiiB21KwsklchpfronR) {
        this.iiiB21KwsklchpfronR = iiiB21KwsklchpfronR;
    }

    public BigDecimal getIiiB22KwsklwypfronR() {
        return iiiB22KwsklwypfronR;
    }

    public void setIiiB22KwsklwypfronR(BigDecimal iiiB22KwsklwypfronR) {
        this.iiiB22KwsklwypfronR = iiiB22KwsklwypfronR;
    }

    public BigDecimal getIiiB23KwsklefkR() {
        return iiiB23KwsklefkR;
    }

    public void setIiiB23KwsklefkR(BigDecimal iiiB23KwsklefkR) {
        this.iiiB23KwsklefkR = iiiB23KwsklefkR;
    }

    public BigDecimal getIiiB24KwsklrfkR() {
        return iiiB24KwsklrfkR;
    }

    public void setIiiB24KwsklrfkR(BigDecimal iiiB24KwsklrfkR) {
        this.iiiB24KwsklrfkR = iiiB24KwsklrfkR;
    }

    public BigDecimal getIiiB25KwsklchfkR() {
        return iiiB25KwsklchfkR;
    }

    public void setIiiB25KwsklchfkR(BigDecimal iiiB25KwsklchfkR) {
        this.iiiB25KwsklchfkR = iiiB25KwsklchfkR;
    }

    public BigDecimal getIiiB26KwsklwyfkR() {
        return iiiB26KwsklwyfkR;
    }

    public void setIiiB26KwsklwyfkR(BigDecimal iiiB26KwsklwyfkR) {
        this.iiiB26KwsklwyfkR = iiiB26KwsklwyfkR;
    }

    public BigDecimal getIiiC2KwsklzpR() {
        return iiiC2KwsklzpR;
    }

    public void setIiiC2KwsklzpR(BigDecimal iiiC2KwsklzpR) {
        this.iiiC2KwsklzpR = iiiC2KwsklzpR;
    }

    public BigDecimal getIiiC3KwsklzbpR() {
        return iiiC3KwsklzbpR;
    }

    public void setIiiC3KwsklzbpR(BigDecimal iiiC3KwsklzbpR) {
        this.iiiC3KwsklzbpR = iiiC3KwsklzbpR;
    }

    public BigDecimal getIiiC4KwsklzuR() {
        return iiiC4KwsklzuR;
    }

    public void setIiiC4KwsklzuR(BigDecimal iiiC4KwsklzuR) {
        this.iiiC4KwsklzuR = iiiC4KwsklzuR;
    }

    public BigDecimal getIiiC5KwsklzfkR() {
        return iiiC5KwsklzfkR;
    }

    public void setIiiC5KwsklzfkR(BigDecimal iiiC5KwsklzfkR) {
        this.iiiC5KwsklzfkR = iiiC5KwsklzfkR;
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

    public BigDecimal getIiiPpk() {
        return iiiPpk;
    }

    public void setIiiPpk(BigDecimal iiiPpk) {
        this.iiiPpk = iiiPpk;
    }

    public Character getIiiCzyopodatkSkala() {
        return iiiCzyopodatkSkala;
    }

    public void setIiiCzyopodatkSkala(Character iiiCzyopodatkSkala) {
        this.iiiCzyopodatkSkala = iiiCzyopodatkSkala;
    }

    public BigDecimal getIiiKwotadochodumpSkala() {
        return iiiKwotadochodumpSkala;
    }

    public void setIiiKwotadochodumpSkala(BigDecimal iiiKwotadochodumpSkala) {
        this.iiiKwotadochodumpSkala = iiiKwotadochodumpSkala;
    }

    public BigDecimal getIiiPodstwymsklzdrSkala() {
        return iiiPodstwymsklzdrSkala;
    }

    public void setIiiPodstwymsklzdrSkala(BigDecimal iiiPodstwymsklzdrSkala) {
        this.iiiPodstwymsklzdrSkala = iiiPodstwymsklzdrSkala;
    }

    public BigDecimal getIiiKwotaskladkiSkala() {
        return iiiKwotaskladkiSkala;
    }

    public void setIiiKwotaskladkiSkala(BigDecimal iiiKwotaskladkiSkala) {
        this.iiiKwotaskladkiSkala = iiiKwotaskladkiSkala;
    }

    public Character getIiiCzyopodatkLiniowy() {
        return iiiCzyopodatkLiniowy;
    }

    public void setIiiCzyopodatkLiniowy(Character iiiCzyopodatkLiniowy) {
        this.iiiCzyopodatkLiniowy = iiiCzyopodatkLiniowy;
    }

    public BigDecimal getIiiKwotadochodumpLiniowy() {
        return iiiKwotadochodumpLiniowy;
    }

    public void setIiiKwotadochodumpLiniowy(BigDecimal iiiKwotadochodumpLiniowy) {
        this.iiiKwotadochodumpLiniowy = iiiKwotadochodumpLiniowy;
    }

    public BigDecimal getIiiPodstwymsklzdrLiniowy() {
        return iiiPodstwymsklzdrLiniowy;
    }

    public void setIiiPodstwymsklzdrLiniowy(BigDecimal iiiPodstwymsklzdrLiniowy) {
        this.iiiPodstwymsklzdrLiniowy = iiiPodstwymsklzdrLiniowy;
    }

    public BigDecimal getIiiKwotaskladkiLiniowy() {
        return iiiKwotaskladkiLiniowy;
    }

    public void setIiiKwotaskladkiLiniowy(BigDecimal iiiKwotaskladkiLiniowy) {
        this.iiiKwotaskladkiLiniowy = iiiKwotaskladkiLiniowy;
    }

    public Character getIiiCzyopodatkKarta() {
        return iiiCzyopodatkKarta;
    }

    public void setIiiCzyopodatkKarta(Character iiiCzyopodatkKarta) {
        this.iiiCzyopodatkKarta = iiiCzyopodatkKarta;
    }

    public BigDecimal getIiiPodstwymsklzdrKarta() {
        return iiiPodstwymsklzdrKarta;
    }

    public void setIiiPodstwymsklzdrKarta(BigDecimal iiiPodstwymsklzdrKarta) {
        this.iiiPodstwymsklzdrKarta = iiiPodstwymsklzdrKarta;
    }

    public BigDecimal getIiiKwotaskladkiKarta() {
        return iiiKwotaskladkiKarta;
    }

    public void setIiiKwotaskladkiKarta(BigDecimal iiiKwotaskladkiKarta) {
        this.iiiKwotaskladkiKarta = iiiKwotaskladkiKarta;
    }

    public Character getIiiCzyopodatkRyczalt() {
        return iiiCzyopodatkRyczalt;
    }

    public void setIiiCzyopodatkRyczalt(Character iiiCzyopodatkRyczalt) {
        this.iiiCzyopodatkRyczalt = iiiCzyopodatkRyczalt;
    }

    public BigDecimal getIiiSumaprzychodowbrk() {
        return iiiSumaprzychodowbrk;
    }

    public void setIiiSumaprzychodowbrk(BigDecimal iiiSumaprzychodowbrk) {
        this.iiiSumaprzychodowbrk = iiiSumaprzychodowbrk;
    }

    public Character getIiiCzyoplacaniesklprk() {
        return iiiCzyoplacaniesklprk;
    }

    public void setIiiCzyoplacaniesklprk(Character iiiCzyoplacaniesklprk) {
        this.iiiCzyoplacaniesklprk = iiiCzyoplacaniesklprk;
    }

    public BigDecimal getIiiKwotaprzychodowurk() {
        return iiiKwotaprzychodowurk;
    }

    public void setIiiKwotaprzychodowurk(BigDecimal iiiKwotaprzychodowurk) {
        this.iiiKwotaprzychodowurk = iiiKwotaprzychodowurk;
    }

    public BigDecimal getIiiPodstwymsklzdrRyczalt() {
        return iiiPodstwymsklzdrRyczalt;
    }

    public void setIiiPodstwymsklzdrRyczalt(BigDecimal iiiPodstwymsklzdrRyczalt) {
        this.iiiPodstwymsklzdrRyczalt = iiiPodstwymsklzdrRyczalt;
    }

    public BigDecimal getIiiKwotaskladkiRyczalt() {
        return iiiKwotaskladkiRyczalt;
    }

    public void setIiiKwotaskladkiRyczalt(BigDecimal iiiKwotaskladkiRyczalt) {
        this.iiiKwotaskladkiRyczalt = iiiKwotaskladkiRyczalt;
    }

    public Character getIiiCzyBezOpodatk() {
        return iiiCzyBezOpodatk;
    }

    public void setIiiCzyBezOpodatk(Character iiiCzyBezOpodatk) {
        this.iiiCzyBezOpodatk = iiiCzyBezOpodatk;
    }

    public BigDecimal getIiiPodstwymsklzdrBezop() {
        return iiiPodstwymsklzdrBezop;
    }

    public void setIiiPodstwymsklzdrBezop(BigDecimal iiiPodstwymsklzdrBezop) {
        this.iiiPodstwymsklzdrBezop = iiiPodstwymsklzdrBezop;
    }

    public BigDecimal getIiiKwotaskladkiBezop() {
        return iiiKwotaskladkiBezop;
    }

    public void setIiiKwotaskladkiBezop(BigDecimal iiiKwotaskladkiBezop) {
        this.iiiKwotaskladkiBezop = iiiKwotaskladkiBezop;
    }

    public Short getIiiRokrozliczeniasklzd() {
        return iiiRokrozliczeniasklzd;
    }

    public void setIiiRokrozliczeniasklzd(Short iiiRokrozliczeniasklzd) {
        this.iiiRokrozliczeniasklzd = iiiRokrozliczeniasklzd;
    }

    public BigDecimal getIiiKwotaprzychodowrr() {
        return iiiKwotaprzychodowrr;
    }

    public void setIiiKwotaprzychodowrr(BigDecimal iiiKwotaprzychodowrr) {
        this.iiiKwotaprzychodowrr = iiiKwotaprzychodowrr;
    }

    public BigDecimal getIiiRocznaskladka() {
        return iiiRocznaskladka;
    }

    public void setIiiRocznaskladka(BigDecimal iiiRocznaskladka) {
        this.iiiRocznaskladka = iiiRocznaskladka;
    }

    public BigDecimal getIiiSumamiesnalezskladek() {
        return iiiSumamiesnalezskladek;
    }

    public void setIiiSumamiesnalezskladek(BigDecimal iiiSumamiesnalezskladek) {
        this.iiiSumamiesnalezskladek = iiiSumamiesnalezskladek;
    }

    public BigDecimal getIiiKwotadodoplaty() {
        return iiiKwotadodoplaty;
    }

    public void setIiiKwotadodoplaty(BigDecimal iiiKwotadodoplaty) {
        this.iiiKwotadodoplaty = iiiKwotadodoplaty;
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
        if (!(object instanceof UbezpZusrca)) {
            return false;
        }
        UbezpZusrca other = (UbezpZusrca) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UbezpZusrca{" + "idPlatnik=" + idPlatnik + ", idDokNad=" + idDokNad + ", iiiA1Nazwisko=" + iiiA1Nazwisko + ", iiiA2Imiepierw=" + iiiA2Imiepierw + ", iiiA4Identyfik=" + iiiA4Identyfik + ", iiiB11kodtytub=" + iiiB11kodtytub + ", iiiB31wymczprl=" + iiiB31wymczprl + ", iiiB32wymczprm=" + iiiB32wymczprm + ", iiiB15Lkwskl=" + iiiB15Lkwskl + '}';
    }

    
    
}
