/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "osoba_prop_typ", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OsobaPropTyp.findAll", query = "SELECT o FROM OsobaPropTyp o"),
    @NamedQuery(name = "OsobaPropTyp.findByOptSerial", query = "SELECT o FROM OsobaPropTyp o WHERE o.optSerial = :optSerial"),
    @NamedQuery(name = "OsobaPropTyp.findByOptOpis", query = "SELECT o FROM OsobaPropTyp o WHERE o.optOpis = :optOpis"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusEmer", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusEmer = :optZusEmer"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusRent", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusRent = :optZusRent"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusChor", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusChor = :optZusChor"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusWyp", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusWyp = :optZusWyp"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusZdro", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusZdro = :optZusZdro"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusFp", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusFp = :optZusFp"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusFgsp", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusFgsp = :optZusFgsp"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbEmUbez", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbEmUbez = :optUbEmUbez"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbEmPrac", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbEmPrac = :optUbEmPrac"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbEmBudz", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbEmBudz = :optUbEmBudz"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbEmPfron", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbEmPfron = :optUbEmPfron"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbReUbez", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbReUbez = :optUbReUbez"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbRePrac", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbRePrac = :optUbRePrac"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbReBudz", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbReBudz = :optUbReBudz"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbRePfron", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbRePfron = :optUbRePfron"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbChUbez", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbChUbez = :optUbChUbez"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbChPrac", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbChPrac = :optUbChPrac"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbChBudz", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbChBudz = :optUbChBudz"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbChPfron", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbChPfron = :optUbChPfron"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbWyUbez", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbWyUbez = :optUbWyUbez"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbWyPrac", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbWyPrac = :optUbWyPrac"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbWyBudz", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbWyBudz = :optUbWyBudz"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbWyPfron", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbWyPfron = :optUbWyPfron"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbZdUbez", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbZdUbez = :optUbZdUbez"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbZdPrac", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbZdPrac = :optUbZdPrac"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbZdBudz", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbZdBudz = :optUbZdBudz"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbZdPfron", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbZdPfron = :optUbZdPfron"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbFpUbez", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbFpUbez = :optUbFpUbez"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbFpPrac", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbFpPrac = :optUbFpPrac"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbFpBudz", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbFpBudz = :optUbFpBudz"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbFpPfron", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbFpPfron = :optUbFpPfron"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbFgUbez", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbFgUbez = :optUbFgUbez"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbFgPrac", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbFgPrac = :optUbFgPrac"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbFgBudz", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbFgBudz = :optUbFgBudz"),
    @NamedQuery(name = "OsobaPropTyp.findByOptUbFgPfron", query = "SELECT o FROM OsobaPropTyp o WHERE o.optUbFgPfron = :optUbFgPfron"),
    @NamedQuery(name = "OsobaPropTyp.findByOptKolejnosc", query = "SELECT o FROM OsobaPropTyp o WHERE o.optKolejnosc = :optKolejnosc"),
    @NamedQuery(name = "OsobaPropTyp.findByOptSystem", query = "SELECT o FROM OsobaPropTyp o WHERE o.optSystem = :optSystem"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod1", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod1 = :optDod1"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod2", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod2 = :optDod2"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod3", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod3 = :optDod3"),
    @NamedQuery(name = "OsobaPropTyp.findByOptOgrPdstChor", query = "SELECT o FROM OsobaPropTyp o WHERE o.optOgrPdstChor = :optOgrPdstChor"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod4", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod4 = :optDod4"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod5", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod5 = :optDod5"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod6", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod6 = :optDod6"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod7", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod7 = :optDod7"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod8", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod8 = :optDod8"),
    @NamedQuery(name = "OsobaPropTyp.findByOptEmerProc", query = "SELECT o FROM OsobaPropTyp o WHERE o.optEmerProc = :optEmerProc"),
    @NamedQuery(name = "OsobaPropTyp.findByOptRentProc", query = "SELECT o FROM OsobaPropTyp o WHERE o.optRentProc = :optRentProc"),
    @NamedQuery(name = "OsobaPropTyp.findByOptChorProc", query = "SELECT o FROM OsobaPropTyp o WHERE o.optChorProc = :optChorProc"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWypProc", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWypProc = :optWypProc"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZdroProc", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZdroProc = :optZdroProc"),
    @NamedQuery(name = "OsobaPropTyp.findByOptFpProc", query = "SELECT o FROM OsobaPropTyp o WHERE o.optFpProc = :optFpProc"),
    @NamedQuery(name = "OsobaPropTyp.findByOptFgspProc", query = "SELECT o FROM OsobaPropTyp o WHERE o.optFgspProc = :optFgspProc"),
    @NamedQuery(name = "OsobaPropTyp.findByOptEmerProcO", query = "SELECT o FROM OsobaPropTyp o WHERE o.optEmerProcO = :optEmerProcO"),
    @NamedQuery(name = "OsobaPropTyp.findByOptRentProcO", query = "SELECT o FROM OsobaPropTyp o WHERE o.optRentProcO = :optRentProcO"),
    @NamedQuery(name = "OsobaPropTyp.findByOptChorProcO", query = "SELECT o FROM OsobaPropTyp o WHERE o.optChorProcO = :optChorProcO"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWypProcO", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWypProcO = :optWypProcO"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZdroProcO", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZdroProcO = :optZdroProcO"),
    @NamedQuery(name = "OsobaPropTyp.findByOptFpProcO", query = "SELECT o FROM OsobaPropTyp o WHERE o.optFpProcO = :optFpProcO"),
    @NamedQuery(name = "OsobaPropTyp.findByOptFgspProcO", query = "SELECT o FROM OsobaPropTyp o WHERE o.optFgspProcO = :optFgspProcO"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusEmerDobr", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusEmerDobr = :optZusEmerDobr"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusRentDobr", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusRentDobr = :optZusRentDobr"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusZdroDobr", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusZdroDobr = :optZusZdroDobr"),
    @NamedQuery(name = "OsobaPropTyp.findByOptZusWypDobr", query = "SELECT o FROM OsobaPropTyp o WHERE o.optZusWypDobr = :optZusWypDobr"),
    @NamedQuery(name = "OsobaPropTyp.findByOptKodTytU12", query = "SELECT o FROM OsobaPropTyp o WHERE o.optKodTytU12 = :optKodTytU12"),
    @NamedQuery(name = "OsobaPropTyp.findByOptKodTytU3", query = "SELECT o FROM OsobaPropTyp o WHERE o.optKodTytU3 = :optKodTytU3"),
    @NamedQuery(name = "OsobaPropTyp.findByOptKodTytU4", query = "SELECT o FROM OsobaPropTyp o WHERE o.optKodTytU4 = :optKodTytU4"),
    @NamedQuery(name = "OsobaPropTyp.findByOptTyp", query = "SELECT o FROM OsobaPropTyp o WHERE o.optTyp = :optTyp"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDataOd", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDataOd = :optDataOd"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDataDo", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDataDo = :optDataDo"),
    @NamedQuery(name = "OsobaPropTyp.findByOptStatus", query = "SELECT o FROM OsobaPropTyp o WHERE o.optStatus = :optStatus"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWspDod1", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWspDod1 = :optWspDod1"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWspDod2", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWspDod2 = :optWspDod2"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWspDod3", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWspDod3 = :optWspDod3"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWspDod4", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWspDod4 = :optWspDod4"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWspDod5", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWspDod5 = :optWspDod5"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWspDod6", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWspDod6 = :optWspDod6"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWspDod7", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWspDod7 = :optWspDod7"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWspDod8", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWspDod8 = :optWspDod8"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWspDod9", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWspDod9 = :optWspDod9"),
    @NamedQuery(name = "OsobaPropTyp.findByOptWspDod10", query = "SELECT o FROM OsobaPropTyp o WHERE o.optWspDod10 = :optWspDod10"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod9", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod9 = :optDod9"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod10", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod10 = :optDod10"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod11", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod11 = :optDod11"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod12", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod12 = :optDod12"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod13", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod13 = :optDod13"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod14", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod14 = :optDod14"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod15", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod15 = :optDod15"),
    @NamedQuery(name = "OsobaPropTyp.findByOptDod16", query = "SELECT o FROM OsobaPropTyp o WHERE o.optDod16 = :optDod16")})
public class OsobaPropTyp implements Serializable {

    private static final long serialVersionUID = 1L;
    //1 pracownik
    //4 zleceniobiorca
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "opt_serial", nullable = false)
    private Integer optSerial;
    @Size(max = 254)
    @Column(name = "opt_opis", length = 254)
    private String optOpis;
    @Column(name = "opt_zus_emer")
    private Character optZusEmer;
    @Column(name = "opt_zus_rent")
    private Character optZusRent;
    @Column(name = "opt_zus_chor")
    private Character optZusChor;
    @Column(name = "opt_zus_wyp")
    private Character optZusWyp;
    @Column(name = "opt_zus_zdro")
    private Character optZusZdro;
    @Column(name = "opt_zus_fp")
    private Character optZusFp;
    @Column(name = "opt_zus_fgsp")
    private Character optZusFgsp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "opt_ub_em_ubez", precision = 5, scale = 2)
    private BigDecimal optUbEmUbez;
    @Column(name = "opt_ub_em_prac", precision = 5, scale = 2)
    private BigDecimal optUbEmPrac;
    @Column(name = "opt_ub_em_budz", precision = 5, scale = 2)
    private BigDecimal optUbEmBudz;
    @Column(name = "opt_ub_em_pfron", precision = 5, scale = 2)
    private BigDecimal optUbEmPfron;
    @Column(name = "opt_ub_re_ubez", precision = 5, scale = 2)
    private BigDecimal optUbReUbez;
    @Column(name = "opt_ub_re_prac", precision = 5, scale = 2)
    private BigDecimal optUbRePrac;
    @Column(name = "opt_ub_re_budz", precision = 5, scale = 2)
    private BigDecimal optUbReBudz;
    @Column(name = "opt_ub_re_pfron", precision = 5, scale = 2)
    private BigDecimal optUbRePfron;
    @Column(name = "opt_ub_ch_ubez", precision = 5, scale = 2)
    private BigDecimal optUbChUbez;
    @Column(name = "opt_ub_ch_prac", precision = 5, scale = 2)
    private BigDecimal optUbChPrac;
    @Column(name = "opt_ub_ch_budz", precision = 5, scale = 2)
    private BigDecimal optUbChBudz;
    @Column(name = "opt_ub_ch_pfron", precision = 5, scale = 2)
    private BigDecimal optUbChPfron;
    @Column(name = "opt_ub_wy_ubez", precision = 5, scale = 2)
    private BigDecimal optUbWyUbez;
    @Column(name = "opt_ub_wy_prac", precision = 5, scale = 2)
    private BigDecimal optUbWyPrac;
    @Column(name = "opt_ub_wy_budz", precision = 5, scale = 2)
    private BigDecimal optUbWyBudz;
    @Column(name = "opt_ub_wy_pfron", precision = 5, scale = 2)
    private BigDecimal optUbWyPfron;
    @Column(name = "opt_ub_zd_ubez", precision = 5, scale = 2)
    private BigDecimal optUbZdUbez;
    @Column(name = "opt_ub_zd_prac", precision = 5, scale = 2)
    private BigDecimal optUbZdPrac;
    @Column(name = "opt_ub_zd_budz", precision = 5, scale = 2)
    private BigDecimal optUbZdBudz;
    @Column(name = "opt_ub_zd_pfron", precision = 5, scale = 2)
    private BigDecimal optUbZdPfron;
    @Column(name = "opt_ub_fp_ubez", precision = 5, scale = 2)
    private BigDecimal optUbFpUbez;
    @Column(name = "opt_ub_fp_prac", precision = 5, scale = 2)
    private BigDecimal optUbFpPrac;
    @Column(name = "opt_ub_fp_budz", precision = 5, scale = 2)
    private BigDecimal optUbFpBudz;
    @Column(name = "opt_ub_fp_pfron", precision = 5, scale = 2)
    private BigDecimal optUbFpPfron;
    @Column(name = "opt_ub_fg_ubez", precision = 5, scale = 2)
    private BigDecimal optUbFgUbez;
    @Column(name = "opt_ub_fg_prac", precision = 5, scale = 2)
    private BigDecimal optUbFgPrac;
    @Column(name = "opt_ub_fg_budz", precision = 5, scale = 2)
    private BigDecimal optUbFgBudz;
    @Column(name = "opt_ub_fg_pfron", precision = 5, scale = 2)
    private BigDecimal optUbFgPfron;
    @Column(name = "opt_kolejnosc")
    private Short optKolejnosc;
    @Column(name = "opt_system")
    private Character optSystem;
    @Column(name = "opt_dod_1")
    private Character optDod1;
    @Column(name = "opt_dod_2")
    private Character optDod2;
    @Column(name = "opt_dod_3")
    private Character optDod3;
    @Column(name = "opt_ogr_pdst_chor")
    private Character optOgrPdstChor;
    @Column(name = "opt_dod_4")
    private Character optDod4;
    @Column(name = "opt_dod_5")
    private Character optDod5;
    @Column(name = "opt_dod_6")
    private Character optDod6;
    @Column(name = "opt_dod_7")
    private Character optDod7;
    @Column(name = "opt_dod_8")
    private Character optDod8;
    @Column(name = "opt_emer_proc", precision = 5, scale = 2)
    private BigDecimal optEmerProc;
    @Column(name = "opt_rent_proc", precision = 5, scale = 2)
    private BigDecimal optRentProc;
    @Column(name = "opt_chor_proc", precision = 5, scale = 2)
    private BigDecimal optChorProc;
    @Column(name = "opt_wyp_proc", precision = 5, scale = 2)
    private BigDecimal optWypProc;
    @Column(name = "opt_zdro_proc", precision = 5, scale = 2)
    private BigDecimal optZdroProc;
    @Column(name = "opt_fp_proc", precision = 5, scale = 2)
    private BigDecimal optFpProc;
    @Column(name = "opt_fgsp_proc", precision = 5, scale = 2)
    private BigDecimal optFgspProc;
    @Column(name = "opt_emer_proc_o")
    private Character optEmerProcO;
    @Column(name = "opt_rent_proc_o")
    private Character optRentProcO;
    @Column(name = "opt_chor_proc_o")
    private Character optChorProcO;
    @Column(name = "opt_wyp_proc_o")
    private Character optWypProcO;
    @Column(name = "opt_zdro_proc_o")
    private Character optZdroProcO;
    @Column(name = "opt_fp_proc_o")
    private Character optFpProcO;
    @Column(name = "opt_fgsp_proc_o")
    private Character optFgspProcO;
    @Column(name = "opt_zus_emer_dobr")
    private Character optZusEmerDobr;
    @Column(name = "opt_zus_rent_dobr")
    private Character optZusRentDobr;
    @Column(name = "opt_zus_zdro_dobr")
    private Character optZusZdroDobr;
    @Column(name = "opt_zus_wyp_dobr")
    private Character optZusWypDobr;
    @Size(max = 4)
    @Column(name = "opt_kod_tyt_u_1_2", length = 4)
    private String optKodTytU12;
    @Column(name = "opt_kod_tyt_u_3")
    private Character optKodTytU3;
    @Column(name = "opt_kod_tyt_u_4")
    private Character optKodTytU4;
    @Column(name = "opt_typ")
    private Character optTyp;
    @Column(name = "opt_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date optDataOd;
    @Column(name = "opt_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date optDataDo;
    @Column(name = "opt_status")
    private Character optStatus;
    @Column(name = "opt_wsp_dod_1", precision = 5, scale = 2)
    private BigDecimal optWspDod1;
    @Column(name = "opt_wsp_dod_2", precision = 5, scale = 2)
    private BigDecimal optWspDod2;
    @Column(name = "opt_wsp_dod_3", precision = 5, scale = 2)
    private BigDecimal optWspDod3;
    @Column(name = "opt_wsp_dod_4", precision = 5, scale = 2)
    private BigDecimal optWspDod4;
    @Column(name = "opt_wsp_dod_5", precision = 5, scale = 2)
    private BigDecimal optWspDod5;
    @Column(name = "opt_wsp_dod_6", precision = 5, scale = 2)
    private BigDecimal optWspDod6;
    @Column(name = "opt_wsp_dod_7", precision = 5, scale = 2)
    private BigDecimal optWspDod7;
    @Column(name = "opt_wsp_dod_8", precision = 5, scale = 2)
    private BigDecimal optWspDod8;
    @Column(name = "opt_wsp_dod_9", precision = 5, scale = 2)
    private BigDecimal optWspDod9;
    @Column(name = "opt_wsp_dod_10", precision = 5, scale = 2)
    private BigDecimal optWspDod10;
    @Column(name = "opt_dod_9")
    private Character optDod9;
    @Column(name = "opt_dod_10")
    private Character optDod10;
    @Column(name = "opt_dod_11")
    private Character optDod11;
    @Column(name = "opt_dod_12")
    private Character optDod12;
    @Column(name = "opt_dod_13")
    private Character optDod13;
    @Column(name = "opt_dod_14")
    private Character optDod14;
    @Column(name = "opt_dod_15")
    private Character optDod15;
    @Column(name = "opt_dod_16")
    private Character optDod16;
    @OneToMany(mappedBy = "dssOptSerial")
    private List<DaneStatS> daneStatSList;
    @OneToMany(mappedBy = "ozlOptSerial")
    private List<OsobaZlec> osobaZlecList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "osdOptSerial")
    private List<OsobaDet> osobaDetList;

    public OsobaPropTyp() {
    }

    public OsobaPropTyp(Integer optSerial) {
        this.optSerial = optSerial;
    }

    public Integer getOptSerial() {
        return optSerial;
    }

    public void setOptSerial(Integer optSerial) {
        this.optSerial = optSerial;
    }

    public String getOptOpis() {
        return optOpis;
    }

    public void setOptOpis(String optOpis) {
        this.optOpis = optOpis;
    }

    public Character getOptZusEmer() {
        return optZusEmer;
    }

    public void setOptZusEmer(Character optZusEmer) {
        this.optZusEmer = optZusEmer;
    }

    public Character getOptZusRent() {
        return optZusRent;
    }

    public void setOptZusRent(Character optZusRent) {
        this.optZusRent = optZusRent;
    }

    public Character getOptZusChor() {
        return optZusChor;
    }

    public void setOptZusChor(Character optZusChor) {
        this.optZusChor = optZusChor;
    }

    public Character getOptZusWyp() {
        return optZusWyp;
    }

    public void setOptZusWyp(Character optZusWyp) {
        this.optZusWyp = optZusWyp;
    }

    public Character getOptZusZdro() {
        return optZusZdro;
    }

    public void setOptZusZdro(Character optZusZdro) {
        this.optZusZdro = optZusZdro;
    }

    public Character getOptZusFp() {
        return optZusFp;
    }

    public void setOptZusFp(Character optZusFp) {
        this.optZusFp = optZusFp;
    }

    public Character getOptZusFgsp() {
        return optZusFgsp;
    }

    public void setOptZusFgsp(Character optZusFgsp) {
        this.optZusFgsp = optZusFgsp;
    }

    public BigDecimal getOptUbEmUbez() {
        return optUbEmUbez;
    }

    public void setOptUbEmUbez(BigDecimal optUbEmUbez) {
        this.optUbEmUbez = optUbEmUbez;
    }

    public BigDecimal getOptUbEmPrac() {
        return optUbEmPrac;
    }

    public void setOptUbEmPrac(BigDecimal optUbEmPrac) {
        this.optUbEmPrac = optUbEmPrac;
    }

    public BigDecimal getOptUbEmBudz() {
        return optUbEmBudz;
    }

    public void setOptUbEmBudz(BigDecimal optUbEmBudz) {
        this.optUbEmBudz = optUbEmBudz;
    }

    public BigDecimal getOptUbEmPfron() {
        return optUbEmPfron;
    }

    public void setOptUbEmPfron(BigDecimal optUbEmPfron) {
        this.optUbEmPfron = optUbEmPfron;
    }

    public BigDecimal getOptUbReUbez() {
        return optUbReUbez;
    }

    public void setOptUbReUbez(BigDecimal optUbReUbez) {
        this.optUbReUbez = optUbReUbez;
    }

    public BigDecimal getOptUbRePrac() {
        return optUbRePrac;
    }

    public void setOptUbRePrac(BigDecimal optUbRePrac) {
        this.optUbRePrac = optUbRePrac;
    }

    public BigDecimal getOptUbReBudz() {
        return optUbReBudz;
    }

    public void setOptUbReBudz(BigDecimal optUbReBudz) {
        this.optUbReBudz = optUbReBudz;
    }

    public BigDecimal getOptUbRePfron() {
        return optUbRePfron;
    }

    public void setOptUbRePfron(BigDecimal optUbRePfron) {
        this.optUbRePfron = optUbRePfron;
    }

    public BigDecimal getOptUbChUbez() {
        return optUbChUbez;
    }

    public void setOptUbChUbez(BigDecimal optUbChUbez) {
        this.optUbChUbez = optUbChUbez;
    }

    public BigDecimal getOptUbChPrac() {
        return optUbChPrac;
    }

    public void setOptUbChPrac(BigDecimal optUbChPrac) {
        this.optUbChPrac = optUbChPrac;
    }

    public BigDecimal getOptUbChBudz() {
        return optUbChBudz;
    }

    public void setOptUbChBudz(BigDecimal optUbChBudz) {
        this.optUbChBudz = optUbChBudz;
    }

    public BigDecimal getOptUbChPfron() {
        return optUbChPfron;
    }

    public void setOptUbChPfron(BigDecimal optUbChPfron) {
        this.optUbChPfron = optUbChPfron;
    }

    public BigDecimal getOptUbWyUbez() {
        return optUbWyUbez;
    }

    public void setOptUbWyUbez(BigDecimal optUbWyUbez) {
        this.optUbWyUbez = optUbWyUbez;
    }

    public BigDecimal getOptUbWyPrac() {
        return optUbWyPrac;
    }

    public void setOptUbWyPrac(BigDecimal optUbWyPrac) {
        this.optUbWyPrac = optUbWyPrac;
    }

    public BigDecimal getOptUbWyBudz() {
        return optUbWyBudz;
    }

    public void setOptUbWyBudz(BigDecimal optUbWyBudz) {
        this.optUbWyBudz = optUbWyBudz;
    }

    public BigDecimal getOptUbWyPfron() {
        return optUbWyPfron;
    }

    public void setOptUbWyPfron(BigDecimal optUbWyPfron) {
        this.optUbWyPfron = optUbWyPfron;
    }

    public BigDecimal getOptUbZdUbez() {
        return optUbZdUbez;
    }

    public void setOptUbZdUbez(BigDecimal optUbZdUbez) {
        this.optUbZdUbez = optUbZdUbez;
    }

    public BigDecimal getOptUbZdPrac() {
        return optUbZdPrac;
    }

    public void setOptUbZdPrac(BigDecimal optUbZdPrac) {
        this.optUbZdPrac = optUbZdPrac;
    }

    public BigDecimal getOptUbZdBudz() {
        return optUbZdBudz;
    }

    public void setOptUbZdBudz(BigDecimal optUbZdBudz) {
        this.optUbZdBudz = optUbZdBudz;
    }

    public BigDecimal getOptUbZdPfron() {
        return optUbZdPfron;
    }

    public void setOptUbZdPfron(BigDecimal optUbZdPfron) {
        this.optUbZdPfron = optUbZdPfron;
    }

    public BigDecimal getOptUbFpUbez() {
        return optUbFpUbez;
    }

    public void setOptUbFpUbez(BigDecimal optUbFpUbez) {
        this.optUbFpUbez = optUbFpUbez;
    }

    public BigDecimal getOptUbFpPrac() {
        return optUbFpPrac;
    }

    public void setOptUbFpPrac(BigDecimal optUbFpPrac) {
        this.optUbFpPrac = optUbFpPrac;
    }

    public BigDecimal getOptUbFpBudz() {
        return optUbFpBudz;
    }

    public void setOptUbFpBudz(BigDecimal optUbFpBudz) {
        this.optUbFpBudz = optUbFpBudz;
    }

    public BigDecimal getOptUbFpPfron() {
        return optUbFpPfron;
    }

    public void setOptUbFpPfron(BigDecimal optUbFpPfron) {
        this.optUbFpPfron = optUbFpPfron;
    }

    public BigDecimal getOptUbFgUbez() {
        return optUbFgUbez;
    }

    public void setOptUbFgUbez(BigDecimal optUbFgUbez) {
        this.optUbFgUbez = optUbFgUbez;
    }

    public BigDecimal getOptUbFgPrac() {
        return optUbFgPrac;
    }

    public void setOptUbFgPrac(BigDecimal optUbFgPrac) {
        this.optUbFgPrac = optUbFgPrac;
    }

    public BigDecimal getOptUbFgBudz() {
        return optUbFgBudz;
    }

    public void setOptUbFgBudz(BigDecimal optUbFgBudz) {
        this.optUbFgBudz = optUbFgBudz;
    }

    public BigDecimal getOptUbFgPfron() {
        return optUbFgPfron;
    }

    public void setOptUbFgPfron(BigDecimal optUbFgPfron) {
        this.optUbFgPfron = optUbFgPfron;
    }

    public Short getOptKolejnosc() {
        return optKolejnosc;
    }

    public void setOptKolejnosc(Short optKolejnosc) {
        this.optKolejnosc = optKolejnosc;
    }

    public Character getOptSystem() {
        return optSystem;
    }

    public void setOptSystem(Character optSystem) {
        this.optSystem = optSystem;
    }

    public Character getOptDod1() {
        return optDod1;
    }

    public void setOptDod1(Character optDod1) {
        this.optDod1 = optDod1;
    }

    public Character getOptDod2() {
        return optDod2;
    }

    public void setOptDod2(Character optDod2) {
        this.optDod2 = optDod2;
    }

    public Character getOptDod3() {
        return optDod3;
    }

    public void setOptDod3(Character optDod3) {
        this.optDod3 = optDod3;
    }

    public Character getOptOgrPdstChor() {
        return optOgrPdstChor;
    }

    public void setOptOgrPdstChor(Character optOgrPdstChor) {
        this.optOgrPdstChor = optOgrPdstChor;
    }

    public Character getOptDod4() {
        return optDod4;
    }

    public void setOptDod4(Character optDod4) {
        this.optDod4 = optDod4;
    }

    public Character getOptDod5() {
        return optDod5;
    }

    public void setOptDod5(Character optDod5) {
        this.optDod5 = optDod5;
    }

    public Character getOptDod6() {
        return optDod6;
    }

    public void setOptDod6(Character optDod6) {
        this.optDod6 = optDod6;
    }

    public Character getOptDod7() {
        return optDod7;
    }

    public void setOptDod7(Character optDod7) {
        this.optDod7 = optDod7;
    }

    public Character getOptDod8() {
        return optDod8;
    }

    public void setOptDod8(Character optDod8) {
        this.optDod8 = optDod8;
    }

    public BigDecimal getOptEmerProc() {
        return optEmerProc;
    }

    public void setOptEmerProc(BigDecimal optEmerProc) {
        this.optEmerProc = optEmerProc;
    }

    public BigDecimal getOptRentProc() {
        return optRentProc;
    }

    public void setOptRentProc(BigDecimal optRentProc) {
        this.optRentProc = optRentProc;
    }

    public BigDecimal getOptChorProc() {
        return optChorProc;
    }

    public void setOptChorProc(BigDecimal optChorProc) {
        this.optChorProc = optChorProc;
    }

    public BigDecimal getOptWypProc() {
        return optWypProc;
    }

    public void setOptWypProc(BigDecimal optWypProc) {
        this.optWypProc = optWypProc;
    }

    public BigDecimal getOptZdroProc() {
        return optZdroProc;
    }

    public void setOptZdroProc(BigDecimal optZdroProc) {
        this.optZdroProc = optZdroProc;
    }

    public BigDecimal getOptFpProc() {
        return optFpProc;
    }

    public void setOptFpProc(BigDecimal optFpProc) {
        this.optFpProc = optFpProc;
    }

    public BigDecimal getOptFgspProc() {
        return optFgspProc;
    }

    public void setOptFgspProc(BigDecimal optFgspProc) {
        this.optFgspProc = optFgspProc;
    }

    public Character getOptEmerProcO() {
        return optEmerProcO;
    }

    public void setOptEmerProcO(Character optEmerProcO) {
        this.optEmerProcO = optEmerProcO;
    }

    public Character getOptRentProcO() {
        return optRentProcO;
    }

    public void setOptRentProcO(Character optRentProcO) {
        this.optRentProcO = optRentProcO;
    }

    public Character getOptChorProcO() {
        return optChorProcO;
    }

    public void setOptChorProcO(Character optChorProcO) {
        this.optChorProcO = optChorProcO;
    }

    public Character getOptWypProcO() {
        return optWypProcO;
    }

    public void setOptWypProcO(Character optWypProcO) {
        this.optWypProcO = optWypProcO;
    }

    public Character getOptZdroProcO() {
        return optZdroProcO;
    }

    public void setOptZdroProcO(Character optZdroProcO) {
        this.optZdroProcO = optZdroProcO;
    }

    public Character getOptFpProcO() {
        return optFpProcO;
    }

    public void setOptFpProcO(Character optFpProcO) {
        this.optFpProcO = optFpProcO;
    }

    public Character getOptFgspProcO() {
        return optFgspProcO;
    }

    public void setOptFgspProcO(Character optFgspProcO) {
        this.optFgspProcO = optFgspProcO;
    }

    public Character getOptZusEmerDobr() {
        return optZusEmerDobr;
    }

    public void setOptZusEmerDobr(Character optZusEmerDobr) {
        this.optZusEmerDobr = optZusEmerDobr;
    }

    public Character getOptZusRentDobr() {
        return optZusRentDobr;
    }

    public void setOptZusRentDobr(Character optZusRentDobr) {
        this.optZusRentDobr = optZusRentDobr;
    }

    public Character getOptZusZdroDobr() {
        return optZusZdroDobr;
    }

    public void setOptZusZdroDobr(Character optZusZdroDobr) {
        this.optZusZdroDobr = optZusZdroDobr;
    }

    public Character getOptZusWypDobr() {
        return optZusWypDobr;
    }

    public void setOptZusWypDobr(Character optZusWypDobr) {
        this.optZusWypDobr = optZusWypDobr;
    }

    public String getOptKodTytU12() {
        return optKodTytU12;
    }

    public void setOptKodTytU12(String optKodTytU12) {
        this.optKodTytU12 = optKodTytU12;
    }

    public Character getOptKodTytU3() {
        return optKodTytU3;
    }

    public void setOptKodTytU3(Character optKodTytU3) {
        this.optKodTytU3 = optKodTytU3;
    }

    public Character getOptKodTytU4() {
        return optKodTytU4;
    }

    public void setOptKodTytU4(Character optKodTytU4) {
        this.optKodTytU4 = optKodTytU4;
    }

    public Character getOptTyp() {
        return optTyp;
    }

    public void setOptTyp(Character optTyp) {
        this.optTyp = optTyp;
    }

    public Date getOptDataOd() {
        return optDataOd;
    }

    public void setOptDataOd(Date optDataOd) {
        this.optDataOd = optDataOd;
    }

    public Date getOptDataDo() {
        return optDataDo;
    }

    public void setOptDataDo(Date optDataDo) {
        this.optDataDo = optDataDo;
    }

    public Character getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(Character optStatus) {
        this.optStatus = optStatus;
    }

    public BigDecimal getOptWspDod1() {
        return optWspDod1;
    }

    public void setOptWspDod1(BigDecimal optWspDod1) {
        this.optWspDod1 = optWspDod1;
    }

    public BigDecimal getOptWspDod2() {
        return optWspDod2;
    }

    public void setOptWspDod2(BigDecimal optWspDod2) {
        this.optWspDod2 = optWspDod2;
    }

    public BigDecimal getOptWspDod3() {
        return optWspDod3;
    }

    public void setOptWspDod3(BigDecimal optWspDod3) {
        this.optWspDod3 = optWspDod3;
    }

    public BigDecimal getOptWspDod4() {
        return optWspDod4;
    }

    public void setOptWspDod4(BigDecimal optWspDod4) {
        this.optWspDod4 = optWspDod4;
    }

    public BigDecimal getOptWspDod5() {
        return optWspDod5;
    }

    public void setOptWspDod5(BigDecimal optWspDod5) {
        this.optWspDod5 = optWspDod5;
    }

    public BigDecimal getOptWspDod6() {
        return optWspDod6;
    }

    public void setOptWspDod6(BigDecimal optWspDod6) {
        this.optWspDod6 = optWspDod6;
    }

    public BigDecimal getOptWspDod7() {
        return optWspDod7;
    }

    public void setOptWspDod7(BigDecimal optWspDod7) {
        this.optWspDod7 = optWspDod7;
    }

    public BigDecimal getOptWspDod8() {
        return optWspDod8;
    }

    public void setOptWspDod8(BigDecimal optWspDod8) {
        this.optWspDod8 = optWspDod8;
    }

    public BigDecimal getOptWspDod9() {
        return optWspDod9;
    }

    public void setOptWspDod9(BigDecimal optWspDod9) {
        this.optWspDod9 = optWspDod9;
    }

    public BigDecimal getOptWspDod10() {
        return optWspDod10;
    }

    public void setOptWspDod10(BigDecimal optWspDod10) {
        this.optWspDod10 = optWspDod10;
    }

    public Character getOptDod9() {
        return optDod9;
    }

    public void setOptDod9(Character optDod9) {
        this.optDod9 = optDod9;
    }

    public Character getOptDod10() {
        return optDod10;
    }

    public void setOptDod10(Character optDod10) {
        this.optDod10 = optDod10;
    }

    public Character getOptDod11() {
        return optDod11;
    }

    public void setOptDod11(Character optDod11) {
        this.optDod11 = optDod11;
    }

    public Character getOptDod12() {
        return optDod12;
    }

    public void setOptDod12(Character optDod12) {
        this.optDod12 = optDod12;
    }

    public Character getOptDod13() {
        return optDod13;
    }

    public void setOptDod13(Character optDod13) {
        this.optDod13 = optDod13;
    }

    public Character getOptDod14() {
        return optDod14;
    }

    public void setOptDod14(Character optDod14) {
        this.optDod14 = optDod14;
    }

    public Character getOptDod15() {
        return optDod15;
    }

    public void setOptDod15(Character optDod15) {
        this.optDod15 = optDod15;
    }

    public Character getOptDod16() {
        return optDod16;
    }

    public void setOptDod16(Character optDod16) {
        this.optDod16 = optDod16;
    }

    @XmlTransient
    public List<DaneStatS> getDaneStatSList() {
        return daneStatSList;
    }

    public void setDaneStatSList(List<DaneStatS> daneStatSList) {
        this.daneStatSList = daneStatSList;
    }

    @XmlTransient
    public List<OsobaZlec> getOsobaZlecList() {
        return osobaZlecList;
    }

    public void setOsobaZlecList(List<OsobaZlec> osobaZlecList) {
        this.osobaZlecList = osobaZlecList;
    }

    @XmlTransient
    public List<OsobaDet> getOsobaDetList() {
        return osobaDetList;
    }

    public void setOsobaDetList(List<OsobaDet> osobaDetList) {
        this.osobaDetList = osobaDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (optSerial != null ? optSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OsobaPropTyp)) {
            return false;
        }
        OsobaPropTyp other = (OsobaPropTyp) object;
        if ((this.optSerial == null && other.optSerial != null) || (this.optSerial != null && !this.optSerial.equals(other.optSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.OsobaPropTyp[ optSerial=" + optSerial + " ]";
    }
    
}
