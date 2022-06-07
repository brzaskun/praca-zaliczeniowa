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
@Table(name = "UBEZP_ZUSRNA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpZusrna.findAll", query = "SELECT u FROM UbezpZusrna u"),
    @NamedQuery(name = "UbezpZusrna.findByIdDokument", query = "SELECT u FROM UbezpZusrna u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpZusrna.findByIdPlatnik", query = "SELECT u FROM UbezpZusrna u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpZusrna.findByIdDokNad", query = "SELECT u FROM UbezpZusrna u WHERE u.idDokNad = :idDokNad"),
    @NamedQuery(name = "UbezpZusrna.findByIdUbezpieczony", query = "SELECT u FROM UbezpZusrna u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpZusrna.findByIiiA1Nazwisko", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiA1Nazwisko = :iiiA1Nazwisko"),
    @NamedQuery(name = "UbezpZusrna.findByIiiA2Imiepierw", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiA2Imiepierw = :iiiA2Imiepierw"),
    @NamedQuery(name = "UbezpZusrna.findByIiiA3Typid", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiA3Typid = :iiiA3Typid"),
    @NamedQuery(name = "UbezpZusrna.findByIiiA4Identyfik", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiA4Identyfik = :iiiA4Identyfik"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB11kodtytub", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB11kodtytub = :iiiB11kodtytub"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB12prdoem", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB12prdoem = :iiiB12prdoem"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB13stniep", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB13stniep = :iiiB13stniep"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB2Infoprrpod", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB2Infoprrpod = :iiiB2Infoprrpod"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB31wymczprl", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB31wymczprl = :iiiB31wymczprl"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB32wymczprm", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB32wymczprm = :iiiB32wymczprm"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB4Podwymer", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB4Podwymer = :iiiB4Podwymer"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB5Podwymciw", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB5Podwymciw = :iiiB5Podwymciw"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB6Podwymzdr", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB6Podwymzdr = :iiiB6Podwymzdr"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB7KwskleuR", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB7KwskleuR = :iiiB7KwskleuR"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB8KwsklruR", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB8KwsklruR = :iiiB8KwsklruR"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB9KwsklchR", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB9KwsklchR = :iiiB9KwsklchR"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB10Kwsklzdr", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB10Kwsklzdr = :iiiB10Kwsklzdr"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB11KwsklepR", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB11KwsklepR = :iiiB11KwsklepR"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB12KwsklrpR", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB12KwsklrpR = :iiiB12KwsklrpR"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB13Kwsklwyp", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB13Kwsklwyp = :iiiB13Kwsklwyp"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB14Kwobproge", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB14Kwobproge = :iiiB14Kwobproge"),
    @NamedQuery(name = "UbezpZusrna.findByIiiB15Lkwskl", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiB15Lkwskl = :iiiB15Lkwskl"),
    @NamedQuery(name = "UbezpZusrna.findByIiiC1Loszasrodz", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiC1Loszasrodz = :iiiC1Loszasrodz"),
    @NamedQuery(name = "UbezpZusrna.findByIiiC2Kwwypzrodo", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiC2Kwwypzrodo = :iiiC2Kwwypzrodo"),
    @NamedQuery(name = "UbezpZusrna.findByIiiC3Kwwypzwych", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiC3Kwwypzwych = :iiiC3Kwwypzwych"),
    @NamedQuery(name = "UbezpZusrna.findByIiiC4Loszaspiel", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiC4Loszaspiel = :iiiC4Loszaspiel"),
    @NamedQuery(name = "UbezpZusrna.findByIiiC5Kwwypzpieo", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiC5Kwwypzpieo = :iiiC5Kwwypzpieo"),
    @NamedQuery(name = "UbezpZusrna.findByIiiC6Lkwwypz", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiC6Lkwwypz = :iiiC6Lkwwypz"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD1Dniprzepr", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD1Dniprzepr = :iiiD1Dniprzepr"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD2Ldniobpr", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD2Ldniobpr = :iiiD2Ldniobpr"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD3Kodskl1", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD3Kodskl1 = :iiiD3Kodskl1"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD4Okrod1", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD4Okrod1 = :iiiD4Okrod1"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD5Okrdo1", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD5Okrdo1 = :iiiD5Okrdo1"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD6KwRna1", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD6KwRna1 = :iiiD6KwRna1"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD7Kodskl2", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD7Kodskl2 = :iiiD7Kodskl2"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD8Okrod2", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD8Okrod2 = :iiiD8Okrod2"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD9Okrdo2", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD9Okrdo2 = :iiiD9Okrdo2"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD10KwRna2", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD10KwRna2 = :iiiD10KwRna2"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD11Kodskl3", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD11Kodskl3 = :iiiD11Kodskl3"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD12Okrod3", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD12Okrod3 = :iiiD12Okrod3"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD13Okrdo3", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD13Okrdo3 = :iiiD13Okrdo3"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD14KwRna3", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD14KwRna3 = :iiiD14KwRna3"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD15Kodskl4", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD15Kodskl4 = :iiiD15Kodskl4"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD16Okrod4", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD16Okrod4 = :iiiD16Okrod4"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD17Okrdo4", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD17Okrdo4 = :iiiD17Okrdo4"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD18KwRna4", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD18KwRna4 = :iiiD18KwRna4"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD19Kodskl5", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD19Kodskl5 = :iiiD19Kodskl5"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD20Okrod5", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD20Okrod5 = :iiiD20Okrod5"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD21Okrdo5", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD21Okrdo5 = :iiiD21Okrdo5"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD22KwRna5", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD22KwRna5 = :iiiD22KwRna5"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD23Kodskl6", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD23Kodskl6 = :iiiD23Kodskl6"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD24Okrod6", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD24Okrod6 = :iiiD24Okrod6"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD25Okrdo6", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD25Okrdo6 = :iiiD25Okrdo6"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD26KwRna6", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD26KwRna6 = :iiiD26KwRna6"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD27Kodskl7", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD27Kodskl7 = :iiiD27Kodskl7"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD28Okrod7", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD28Okrod7 = :iiiD28Okrod7"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD29Okrdo7", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD29Okrdo7 = :iiiD29Okrdo7"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD30KwRna7", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD30KwRna7 = :iiiD30KwRna7"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD31Kodskl8", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD31Kodskl8 = :iiiD31Kodskl8"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD32Okrod8", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD32Okrod8 = :iiiD32Okrod8"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD33Okrdo8", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD33Okrdo8 = :iiiD33Okrdo8"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD34KwRna8", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD34KwRna8 = :iiiD34KwRna8"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD35Kodskl9", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD35Kodskl9 = :iiiD35Kodskl9"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD36Okrod9", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD36Okrod9 = :iiiD36Okrod9"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD37Okrdo9", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD37Okrdo9 = :iiiD37Okrdo9"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD38KwRna9", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD38KwRna9 = :iiiD38KwRna9"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD39Kodskl10", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD39Kodskl10 = :iiiD39Kodskl10"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD40Okrod10", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD40Okrod10 = :iiiD40Okrod10"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD41Okrdo10", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD41Okrdo10 = :iiiD41Okrdo10"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD42KwRna10", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD42KwRna10 = :iiiD42KwRna10"),
    @NamedQuery(name = "UbezpZusrna.findByIiiD43Sumakwot", query = "SELECT u FROM UbezpZusrna u WHERE u.iiiD43Sumakwot = :iiiD43Sumakwot"),
    @NamedQuery(name = "UbezpZusrna.findByStatuswr", query = "SELECT u FROM UbezpZusrna u WHERE u.statuswr = :statuswr"),
    @NamedQuery(name = "UbezpZusrna.findByStatuspt", query = "SELECT u FROM UbezpZusrna u WHERE u.statuspt = :statuspt"),
    @NamedQuery(name = "UbezpZusrna.findByStatusws", query = "SELECT u FROM UbezpZusrna u WHERE u.statusws = :statusws"),
    @NamedQuery(name = "UbezpZusrna.findByInserttmp", query = "SELECT u FROM UbezpZusrna u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpZusrna.findByNrsciezwyl", query = "SELECT u FROM UbezpZusrna u WHERE u.nrsciezwyl = :nrsciezwyl")})
public class UbezpZusrna implements Serializable {

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
    @Size(max = 2)
    @Column(name = "III_D_1_DNIPRZEPR", length = 2)
    private String iiiD1Dniprzepr;
    @Size(max = 2)
    @Column(name = "III_D_2_LDNIOBPR", length = 2)
    private String iiiD2Ldniobpr;
    @Size(max = 2)
    @Column(name = "III_D_3_KODSKL_1", length = 2)
    private String iiiD3Kodskl1;
    @Column(name = "III_D_4_OKROD_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD4Okrod1;
    @Column(name = "III_D_5_OKRDO_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD5Okrdo1;
    @Column(name = "III_D_6_KW_RNA_1", precision = 8, scale = 2)
    private BigDecimal iiiD6KwRna1;
    @Size(max = 2)
    @Column(name = "III_D_7_KODSKL_2", length = 2)
    private String iiiD7Kodskl2;
    @Column(name = "III_D_8_OKROD_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD8Okrod2;
    @Column(name = "III_D_9_OKRDO_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD9Okrdo2;
    @Column(name = "III_D_10_KW_RNA_2", precision = 8, scale = 2)
    private BigDecimal iiiD10KwRna2;
    @Size(max = 2)
    @Column(name = "III_D_11_KODSKL_3", length = 2)
    private String iiiD11Kodskl3;
    @Column(name = "III_D_12_OKROD_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD12Okrod3;
    @Column(name = "III_D_13_OKRDO_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD13Okrdo3;
    @Column(name = "III_D_14_KW_RNA_3", precision = 8, scale = 2)
    private BigDecimal iiiD14KwRna3;
    @Size(max = 2)
    @Column(name = "III_D_15_KODSKL_4", length = 2)
    private String iiiD15Kodskl4;
    @Column(name = "III_D_16_OKROD_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD16Okrod4;
    @Column(name = "III_D_17_OKRDO_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD17Okrdo4;
    @Column(name = "III_D_18_KW_RNA_4", precision = 8, scale = 2)
    private BigDecimal iiiD18KwRna4;
    @Size(max = 2)
    @Column(name = "III_D_19_KODSKL_5", length = 2)
    private String iiiD19Kodskl5;
    @Column(name = "III_D_20_OKROD_5")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD20Okrod5;
    @Column(name = "III_D_21_OKRDO_5")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD21Okrdo5;
    @Column(name = "III_D_22_KW_RNA_5", precision = 8, scale = 2)
    private BigDecimal iiiD22KwRna5;
    @Size(max = 2)
    @Column(name = "III_D_23_KODSKL_6", length = 2)
    private String iiiD23Kodskl6;
    @Column(name = "III_D_24_OKROD_6")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD24Okrod6;
    @Column(name = "III_D_25_OKRDO_6")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD25Okrdo6;
    @Column(name = "III_D_26_KW_RNA_6", precision = 8, scale = 2)
    private BigDecimal iiiD26KwRna6;
    @Size(max = 2)
    @Column(name = "III_D_27_KODSKL_7", length = 2)
    private String iiiD27Kodskl7;
    @Column(name = "III_D_28_OKROD_7")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD28Okrod7;
    @Column(name = "III_D_29_OKRDO_7")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD29Okrdo7;
    @Column(name = "III_D_30_KW_RNA_7", precision = 8, scale = 2)
    private BigDecimal iiiD30KwRna7;
    @Size(max = 2)
    @Column(name = "III_D_31_KODSKL_8", length = 2)
    private String iiiD31Kodskl8;
    @Column(name = "III_D_32_OKROD_8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD32Okrod8;
    @Column(name = "III_D_33_OKRDO_8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD33Okrdo8;
    @Column(name = "III_D_34_KW_RNA_8", precision = 8, scale = 2)
    private BigDecimal iiiD34KwRna8;
    @Size(max = 2)
    @Column(name = "III_D_35_KODSKL_9", length = 2)
    private String iiiD35Kodskl9;
    @Column(name = "III_D_36_OKROD_9")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD36Okrod9;
    @Column(name = "III_D_37_OKRDO_9")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD37Okrdo9;
    @Column(name = "III_D_38_KW_RNA_9", precision = 8, scale = 2)
    private BigDecimal iiiD38KwRna9;
    @Size(max = 2)
    @Column(name = "III_D_39_KODSKL_10", length = 2)
    private String iiiD39Kodskl10;
    @Column(name = "III_D_40_OKROD_10")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD40Okrod10;
    @Column(name = "III_D_41_OKRDO_10")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiD41Okrdo10;
    @Column(name = "III_D_42_KW_RNA_10", precision = 8, scale = 2)
    private BigDecimal iiiD42KwRna10;
    @Column(name = "III_D_43_SUMAKWOT", precision = 9, scale = 2)
    private BigDecimal iiiD43Sumakwot;
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

    public UbezpZusrna() {
    }

    public UbezpZusrna(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public UbezpZusrna(Integer idDokument, int idPlatnik, int idDokNad, int idUbezpieczony, int statusws) {
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

    public String getIiiD1Dniprzepr() {
        return iiiD1Dniprzepr;
    }

    public void setIiiD1Dniprzepr(String iiiD1Dniprzepr) {
        this.iiiD1Dniprzepr = iiiD1Dniprzepr;
    }

    public String getIiiD2Ldniobpr() {
        return iiiD2Ldniobpr;
    }

    public void setIiiD2Ldniobpr(String iiiD2Ldniobpr) {
        this.iiiD2Ldniobpr = iiiD2Ldniobpr;
    }

    public String getIiiD3Kodskl1() {
        return iiiD3Kodskl1;
    }

    public void setIiiD3Kodskl1(String iiiD3Kodskl1) {
        this.iiiD3Kodskl1 = iiiD3Kodskl1;
    }

    public Date getIiiD4Okrod1() {
        return iiiD4Okrod1;
    }

    public void setIiiD4Okrod1(Date iiiD4Okrod1) {
        this.iiiD4Okrod1 = iiiD4Okrod1;
    }

    public Date getIiiD5Okrdo1() {
        return iiiD5Okrdo1;
    }

    public void setIiiD5Okrdo1(Date iiiD5Okrdo1) {
        this.iiiD5Okrdo1 = iiiD5Okrdo1;
    }

    public BigDecimal getIiiD6KwRna1() {
        return iiiD6KwRna1;
    }

    public void setIiiD6KwRna1(BigDecimal iiiD6KwRna1) {
        this.iiiD6KwRna1 = iiiD6KwRna1;
    }

    public String getIiiD7Kodskl2() {
        return iiiD7Kodskl2;
    }

    public void setIiiD7Kodskl2(String iiiD7Kodskl2) {
        this.iiiD7Kodskl2 = iiiD7Kodskl2;
    }

    public Date getIiiD8Okrod2() {
        return iiiD8Okrod2;
    }

    public void setIiiD8Okrod2(Date iiiD8Okrod2) {
        this.iiiD8Okrod2 = iiiD8Okrod2;
    }

    public Date getIiiD9Okrdo2() {
        return iiiD9Okrdo2;
    }

    public void setIiiD9Okrdo2(Date iiiD9Okrdo2) {
        this.iiiD9Okrdo2 = iiiD9Okrdo2;
    }

    public BigDecimal getIiiD10KwRna2() {
        return iiiD10KwRna2;
    }

    public void setIiiD10KwRna2(BigDecimal iiiD10KwRna2) {
        this.iiiD10KwRna2 = iiiD10KwRna2;
    }

    public String getIiiD11Kodskl3() {
        return iiiD11Kodskl3;
    }

    public void setIiiD11Kodskl3(String iiiD11Kodskl3) {
        this.iiiD11Kodskl3 = iiiD11Kodskl3;
    }

    public Date getIiiD12Okrod3() {
        return iiiD12Okrod3;
    }

    public void setIiiD12Okrod3(Date iiiD12Okrod3) {
        this.iiiD12Okrod3 = iiiD12Okrod3;
    }

    public Date getIiiD13Okrdo3() {
        return iiiD13Okrdo3;
    }

    public void setIiiD13Okrdo3(Date iiiD13Okrdo3) {
        this.iiiD13Okrdo3 = iiiD13Okrdo3;
    }

    public BigDecimal getIiiD14KwRna3() {
        return iiiD14KwRna3;
    }

    public void setIiiD14KwRna3(BigDecimal iiiD14KwRna3) {
        this.iiiD14KwRna3 = iiiD14KwRna3;
    }

    public String getIiiD15Kodskl4() {
        return iiiD15Kodskl4;
    }

    public void setIiiD15Kodskl4(String iiiD15Kodskl4) {
        this.iiiD15Kodskl4 = iiiD15Kodskl4;
    }

    public Date getIiiD16Okrod4() {
        return iiiD16Okrod4;
    }

    public void setIiiD16Okrod4(Date iiiD16Okrod4) {
        this.iiiD16Okrod4 = iiiD16Okrod4;
    }

    public Date getIiiD17Okrdo4() {
        return iiiD17Okrdo4;
    }

    public void setIiiD17Okrdo4(Date iiiD17Okrdo4) {
        this.iiiD17Okrdo4 = iiiD17Okrdo4;
    }

    public BigDecimal getIiiD18KwRna4() {
        return iiiD18KwRna4;
    }

    public void setIiiD18KwRna4(BigDecimal iiiD18KwRna4) {
        this.iiiD18KwRna4 = iiiD18KwRna4;
    }

    public String getIiiD19Kodskl5() {
        return iiiD19Kodskl5;
    }

    public void setIiiD19Kodskl5(String iiiD19Kodskl5) {
        this.iiiD19Kodskl5 = iiiD19Kodskl5;
    }

    public Date getIiiD20Okrod5() {
        return iiiD20Okrod5;
    }

    public void setIiiD20Okrod5(Date iiiD20Okrod5) {
        this.iiiD20Okrod5 = iiiD20Okrod5;
    }

    public Date getIiiD21Okrdo5() {
        return iiiD21Okrdo5;
    }

    public void setIiiD21Okrdo5(Date iiiD21Okrdo5) {
        this.iiiD21Okrdo5 = iiiD21Okrdo5;
    }

    public BigDecimal getIiiD22KwRna5() {
        return iiiD22KwRna5;
    }

    public void setIiiD22KwRna5(BigDecimal iiiD22KwRna5) {
        this.iiiD22KwRna5 = iiiD22KwRna5;
    }

    public String getIiiD23Kodskl6() {
        return iiiD23Kodskl6;
    }

    public void setIiiD23Kodskl6(String iiiD23Kodskl6) {
        this.iiiD23Kodskl6 = iiiD23Kodskl6;
    }

    public Date getIiiD24Okrod6() {
        return iiiD24Okrod6;
    }

    public void setIiiD24Okrod6(Date iiiD24Okrod6) {
        this.iiiD24Okrod6 = iiiD24Okrod6;
    }

    public Date getIiiD25Okrdo6() {
        return iiiD25Okrdo6;
    }

    public void setIiiD25Okrdo6(Date iiiD25Okrdo6) {
        this.iiiD25Okrdo6 = iiiD25Okrdo6;
    }

    public BigDecimal getIiiD26KwRna6() {
        return iiiD26KwRna6;
    }

    public void setIiiD26KwRna6(BigDecimal iiiD26KwRna6) {
        this.iiiD26KwRna6 = iiiD26KwRna6;
    }

    public String getIiiD27Kodskl7() {
        return iiiD27Kodskl7;
    }

    public void setIiiD27Kodskl7(String iiiD27Kodskl7) {
        this.iiiD27Kodskl7 = iiiD27Kodskl7;
    }

    public Date getIiiD28Okrod7() {
        return iiiD28Okrod7;
    }

    public void setIiiD28Okrod7(Date iiiD28Okrod7) {
        this.iiiD28Okrod7 = iiiD28Okrod7;
    }

    public Date getIiiD29Okrdo7() {
        return iiiD29Okrdo7;
    }

    public void setIiiD29Okrdo7(Date iiiD29Okrdo7) {
        this.iiiD29Okrdo7 = iiiD29Okrdo7;
    }

    public BigDecimal getIiiD30KwRna7() {
        return iiiD30KwRna7;
    }

    public void setIiiD30KwRna7(BigDecimal iiiD30KwRna7) {
        this.iiiD30KwRna7 = iiiD30KwRna7;
    }

    public String getIiiD31Kodskl8() {
        return iiiD31Kodskl8;
    }

    public void setIiiD31Kodskl8(String iiiD31Kodskl8) {
        this.iiiD31Kodskl8 = iiiD31Kodskl8;
    }

    public Date getIiiD32Okrod8() {
        return iiiD32Okrod8;
    }

    public void setIiiD32Okrod8(Date iiiD32Okrod8) {
        this.iiiD32Okrod8 = iiiD32Okrod8;
    }

    public Date getIiiD33Okrdo8() {
        return iiiD33Okrdo8;
    }

    public void setIiiD33Okrdo8(Date iiiD33Okrdo8) {
        this.iiiD33Okrdo8 = iiiD33Okrdo8;
    }

    public BigDecimal getIiiD34KwRna8() {
        return iiiD34KwRna8;
    }

    public void setIiiD34KwRna8(BigDecimal iiiD34KwRna8) {
        this.iiiD34KwRna8 = iiiD34KwRna8;
    }

    public String getIiiD35Kodskl9() {
        return iiiD35Kodskl9;
    }

    public void setIiiD35Kodskl9(String iiiD35Kodskl9) {
        this.iiiD35Kodskl9 = iiiD35Kodskl9;
    }

    public Date getIiiD36Okrod9() {
        return iiiD36Okrod9;
    }

    public void setIiiD36Okrod9(Date iiiD36Okrod9) {
        this.iiiD36Okrod9 = iiiD36Okrod9;
    }

    public Date getIiiD37Okrdo9() {
        return iiiD37Okrdo9;
    }

    public void setIiiD37Okrdo9(Date iiiD37Okrdo9) {
        this.iiiD37Okrdo9 = iiiD37Okrdo9;
    }

    public BigDecimal getIiiD38KwRna9() {
        return iiiD38KwRna9;
    }

    public void setIiiD38KwRna9(BigDecimal iiiD38KwRna9) {
        this.iiiD38KwRna9 = iiiD38KwRna9;
    }

    public String getIiiD39Kodskl10() {
        return iiiD39Kodskl10;
    }

    public void setIiiD39Kodskl10(String iiiD39Kodskl10) {
        this.iiiD39Kodskl10 = iiiD39Kodskl10;
    }

    public Date getIiiD40Okrod10() {
        return iiiD40Okrod10;
    }

    public void setIiiD40Okrod10(Date iiiD40Okrod10) {
        this.iiiD40Okrod10 = iiiD40Okrod10;
    }

    public Date getIiiD41Okrdo10() {
        return iiiD41Okrdo10;
    }

    public void setIiiD41Okrdo10(Date iiiD41Okrdo10) {
        this.iiiD41Okrdo10 = iiiD41Okrdo10;
    }

    public BigDecimal getIiiD42KwRna10() {
        return iiiD42KwRna10;
    }

    public void setIiiD42KwRna10(BigDecimal iiiD42KwRna10) {
        this.iiiD42KwRna10 = iiiD42KwRna10;
    }

    public BigDecimal getIiiD43Sumakwot() {
        return iiiD43Sumakwot;
    }

    public void setIiiD43Sumakwot(BigDecimal iiiD43Sumakwot) {
        this.iiiD43Sumakwot = iiiD43Sumakwot;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDokument != null ? idDokument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UbezpZusrna)) {
            return false;
        }
        UbezpZusrna other = (UbezpZusrna) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpZusrna[ idDokument=" + idDokument + " ]";
    }
    
}
