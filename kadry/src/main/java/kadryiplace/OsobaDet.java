/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "osoba_det", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OsobaDet.findAll", query = "SELECT o FROM OsobaDet o"),
    @NamedQuery(name = "OsobaDet.findByOsdSerial", query = "SELECT o FROM OsobaDet o WHERE o.osdSerial = :osdSerial"),
    @NamedQuery(name = "OsobaDet.findByOsdTytul", query = "SELECT o FROM OsobaDet o WHERE o.osdTytul = :osdTytul"),
    @NamedQuery(name = "OsobaDet.findByOsdZusEmer", query = "SELECT o FROM OsobaDet o WHERE o.osdZusEmer = :osdZusEmer"),
    @NamedQuery(name = "OsobaDet.findByOsdZusRent", query = "SELECT o FROM OsobaDet o WHERE o.osdZusRent = :osdZusRent"),
    @NamedQuery(name = "OsobaDet.findByOsdZusChor", query = "SELECT o FROM OsobaDet o WHERE o.osdZusChor = :osdZusChor"),
    @NamedQuery(name = "OsobaDet.findByOsdZusWyp", query = "SELECT o FROM OsobaDet o WHERE o.osdZusWyp = :osdZusWyp"),
    @NamedQuery(name = "OsobaDet.findByOsdZusZdro", query = "SELECT o FROM OsobaDet o WHERE o.osdZusZdro = :osdZusZdro"),
    @NamedQuery(name = "OsobaDet.findByOsdZusFp", query = "SELECT o FROM OsobaDet o WHERE o.osdZusFp = :osdZusFp"),
    @NamedQuery(name = "OsobaDet.findByOsdZusFgsp", query = "SELECT o FROM OsobaDet o WHERE o.osdZusFgsp = :osdZusFgsp"),
    @NamedQuery(name = "OsobaDet.findByOsdUbEmUbez", query = "SELECT o FROM OsobaDet o WHERE o.osdUbEmUbez = :osdUbEmUbez"),
    @NamedQuery(name = "OsobaDet.findByOsdUbEmPrac", query = "SELECT o FROM OsobaDet o WHERE o.osdUbEmPrac = :osdUbEmPrac"),
    @NamedQuery(name = "OsobaDet.findByOsdUbEmBudz", query = "SELECT o FROM OsobaDet o WHERE o.osdUbEmBudz = :osdUbEmBudz"),
    @NamedQuery(name = "OsobaDet.findByOsdUbEmPfron", query = "SELECT o FROM OsobaDet o WHERE o.osdUbEmPfron = :osdUbEmPfron"),
    @NamedQuery(name = "OsobaDet.findByOsdUbReUbez", query = "SELECT o FROM OsobaDet o WHERE o.osdUbReUbez = :osdUbReUbez"),
    @NamedQuery(name = "OsobaDet.findByOsdUbRePrac", query = "SELECT o FROM OsobaDet o WHERE o.osdUbRePrac = :osdUbRePrac"),
    @NamedQuery(name = "OsobaDet.findByOsdUbReBudz", query = "SELECT o FROM OsobaDet o WHERE o.osdUbReBudz = :osdUbReBudz"),
    @NamedQuery(name = "OsobaDet.findByOsdUbRePfron", query = "SELECT o FROM OsobaDet o WHERE o.osdUbRePfron = :osdUbRePfron"),
    @NamedQuery(name = "OsobaDet.findByOsdUbChUbez", query = "SELECT o FROM OsobaDet o WHERE o.osdUbChUbez = :osdUbChUbez"),
    @NamedQuery(name = "OsobaDet.findByOsdUbChPrac", query = "SELECT o FROM OsobaDet o WHERE o.osdUbChPrac = :osdUbChPrac"),
    @NamedQuery(name = "OsobaDet.findByOsdUbChBudz", query = "SELECT o FROM OsobaDet o WHERE o.osdUbChBudz = :osdUbChBudz"),
    @NamedQuery(name = "OsobaDet.findByOsdUbChPfron", query = "SELECT o FROM OsobaDet o WHERE o.osdUbChPfron = :osdUbChPfron"),
    @NamedQuery(name = "OsobaDet.findByOsdUbWyUbez", query = "SELECT o FROM OsobaDet o WHERE o.osdUbWyUbez = :osdUbWyUbez"),
    @NamedQuery(name = "OsobaDet.findByOsdUbWyPrac", query = "SELECT o FROM OsobaDet o WHERE o.osdUbWyPrac = :osdUbWyPrac"),
    @NamedQuery(name = "OsobaDet.findByOsdUbWyBudz", query = "SELECT o FROM OsobaDet o WHERE o.osdUbWyBudz = :osdUbWyBudz"),
    @NamedQuery(name = "OsobaDet.findByOsdUbWyPfron", query = "SELECT o FROM OsobaDet o WHERE o.osdUbWyPfron = :osdUbWyPfron"),
    @NamedQuery(name = "OsobaDet.findByOsdUbZdUbez", query = "SELECT o FROM OsobaDet o WHERE o.osdUbZdUbez = :osdUbZdUbez"),
    @NamedQuery(name = "OsobaDet.findByOsdUbZdPrac", query = "SELECT o FROM OsobaDet o WHERE o.osdUbZdPrac = :osdUbZdPrac"),
    @NamedQuery(name = "OsobaDet.findByOsdUbZdBudz", query = "SELECT o FROM OsobaDet o WHERE o.osdUbZdBudz = :osdUbZdBudz"),
    @NamedQuery(name = "OsobaDet.findByOsdUbZdPfron", query = "SELECT o FROM OsobaDet o WHERE o.osdUbZdPfron = :osdUbZdPfron"),
    @NamedQuery(name = "OsobaDet.findByOsdUbFpUbez", query = "SELECT o FROM OsobaDet o WHERE o.osdUbFpUbez = :osdUbFpUbez"),
    @NamedQuery(name = "OsobaDet.findByOsdUbFpPrac", query = "SELECT o FROM OsobaDet o WHERE o.osdUbFpPrac = :osdUbFpPrac"),
    @NamedQuery(name = "OsobaDet.findByOsdUbFpBudz", query = "SELECT o FROM OsobaDet o WHERE o.osdUbFpBudz = :osdUbFpBudz"),
    @NamedQuery(name = "OsobaDet.findByOsdUbFpPfron", query = "SELECT o FROM OsobaDet o WHERE o.osdUbFpPfron = :osdUbFpPfron"),
    @NamedQuery(name = "OsobaDet.findByOsdUbFgUbez", query = "SELECT o FROM OsobaDet o WHERE o.osdUbFgUbez = :osdUbFgUbez"),
    @NamedQuery(name = "OsobaDet.findByOsdUbFgPrac", query = "SELECT o FROM OsobaDet o WHERE o.osdUbFgPrac = :osdUbFgPrac"),
    @NamedQuery(name = "OsobaDet.findByOsdUbFgBudz", query = "SELECT o FROM OsobaDet o WHERE o.osdUbFgBudz = :osdUbFgBudz"),
    @NamedQuery(name = "OsobaDet.findByOsdUbFgPfron", query = "SELECT o FROM OsobaDet o WHERE o.osdUbFgPfron = :osdUbFgPfron"),
    @NamedQuery(name = "OsobaDet.findByOsdKodTytU12", query = "SELECT o FROM OsobaDet o WHERE o.osdKodTytU12 = :osdKodTytU12"),
    @NamedQuery(name = "OsobaDet.findByOsdDod1", query = "SELECT o FROM OsobaDet o WHERE o.osdDod1 = :osdDod1"),
    @NamedQuery(name = "OsobaDet.findByOsdDod2", query = "SELECT o FROM OsobaDet o WHERE o.osdDod2 = :osdDod2"),
    @NamedQuery(name = "OsobaDet.findByOsdDod3", query = "SELECT o FROM OsobaDet o WHERE o.osdDod3 = :osdDod3"),
    @NamedQuery(name = "OsobaDet.findByOsdOgrPdstChor", query = "SELECT o FROM OsobaDet o WHERE o.osdOgrPdstChor = :osdOgrPdstChor"),
    @NamedQuery(name = "OsobaDet.findByOsdDod4", query = "SELECT o FROM OsobaDet o WHERE o.osdDod4 = :osdDod4"),
    @NamedQuery(name = "OsobaDet.findByOsdDod5", query = "SELECT o FROM OsobaDet o WHERE o.osdDod5 = :osdDod5"),
    @NamedQuery(name = "OsobaDet.findByOsdDod6", query = "SELECT o FROM OsobaDet o WHERE o.osdDod6 = :osdDod6"),
    @NamedQuery(name = "OsobaDet.findByOsdDod7", query = "SELECT o FROM OsobaDet o WHERE o.osdDod7 = :osdDod7"),
    @NamedQuery(name = "OsobaDet.findByOsdDod8", query = "SELECT o FROM OsobaDet o WHERE o.osdDod8 = :osdDod8"),
    @NamedQuery(name = "OsobaDet.findByOsdEmerProc", query = "SELECT o FROM OsobaDet o WHERE o.osdEmerProc = :osdEmerProc"),
    @NamedQuery(name = "OsobaDet.findByOsdRentProc", query = "SELECT o FROM OsobaDet o WHERE o.osdRentProc = :osdRentProc"),
    @NamedQuery(name = "OsobaDet.findByOsdChorProc", query = "SELECT o FROM OsobaDet o WHERE o.osdChorProc = :osdChorProc"),
    @NamedQuery(name = "OsobaDet.findByOsdWypProc", query = "SELECT o FROM OsobaDet o WHERE o.osdWypProc = :osdWypProc"),
    @NamedQuery(name = "OsobaDet.findByOsdZdroProc", query = "SELECT o FROM OsobaDet o WHERE o.osdZdroProc = :osdZdroProc"),
    @NamedQuery(name = "OsobaDet.findByOsdFpProc", query = "SELECT o FROM OsobaDet o WHERE o.osdFpProc = :osdFpProc"),
    @NamedQuery(name = "OsobaDet.findByOsdFgspProc", query = "SELECT o FROM OsobaDet o WHERE o.osdFgspProc = :osdFgspProc"),
    @NamedQuery(name = "OsobaDet.findByOsdEmerProcO", query = "SELECT o FROM OsobaDet o WHERE o.osdEmerProcO = :osdEmerProcO"),
    @NamedQuery(name = "OsobaDet.findByOsdRentProcO", query = "SELECT o FROM OsobaDet o WHERE o.osdRentProcO = :osdRentProcO"),
    @NamedQuery(name = "OsobaDet.findByOsdChorProcO", query = "SELECT o FROM OsobaDet o WHERE o.osdChorProcO = :osdChorProcO"),
    @NamedQuery(name = "OsobaDet.findByOsdWypProcO", query = "SELECT o FROM OsobaDet o WHERE o.osdWypProcO = :osdWypProcO"),
    @NamedQuery(name = "OsobaDet.findByOsdZdroProcO", query = "SELECT o FROM OsobaDet o WHERE o.osdZdroProcO = :osdZdroProcO"),
    @NamedQuery(name = "OsobaDet.findByOsdFpProcO", query = "SELECT o FROM OsobaDet o WHERE o.osdFpProcO = :osdFpProcO"),
    @NamedQuery(name = "OsobaDet.findByOsdFgspProcO", query = "SELECT o FROM OsobaDet o WHERE o.osdFgspProcO = :osdFgspProcO"),
    @NamedQuery(name = "OsobaDet.findByOsdZusEmerDobr", query = "SELECT o FROM OsobaDet o WHERE o.osdZusEmerDobr = :osdZusEmerDobr"),
    @NamedQuery(name = "OsobaDet.findByOsdZusRentDobr", query = "SELECT o FROM OsobaDet o WHERE o.osdZusRentDobr = :osdZusRentDobr"),
    @NamedQuery(name = "OsobaDet.findByOsdZusZdroDobr", query = "SELECT o FROM OsobaDet o WHERE o.osdZusZdroDobr = :osdZusZdroDobr"),
    @NamedQuery(name = "OsobaDet.findByOsdZusWypDobr", query = "SELECT o FROM OsobaDet o WHERE o.osdZusWypDobr = :osdZusWypDobr"),
    @NamedQuery(name = "OsobaDet.findByOsdInt1", query = "SELECT o FROM OsobaDet o WHERE o.osdInt1 = :osdInt1"),
    @NamedQuery(name = "OsobaDet.findByOsdInt2", query = "SELECT o FROM OsobaDet o WHERE o.osdInt2 = :osdInt2"),
    @NamedQuery(name = "OsobaDet.findByOsdNum1", query = "SELECT o FROM OsobaDet o WHERE o.osdNum1 = :osdNum1"),
    @NamedQuery(name = "OsobaDet.findByOsdNum2", query = "SELECT o FROM OsobaDet o WHERE o.osdNum2 = :osdNum2"),
    @NamedQuery(name = "OsobaDet.findByOsdNum3", query = "SELECT o FROM OsobaDet o WHERE o.osdNum3 = :osdNum3"),
    @NamedQuery(name = "OsobaDet.findByOsdNum4", query = "SELECT o FROM OsobaDet o WHERE o.osdNum4 = :osdNum4"),
    @NamedQuery(name = "OsobaDet.findByOsdWspDod1", query = "SELECT o FROM OsobaDet o WHERE o.osdWspDod1 = :osdWspDod1"),
    @NamedQuery(name = "OsobaDet.findByOsdWspDod2", query = "SELECT o FROM OsobaDet o WHERE o.osdWspDod2 = :osdWspDod2"),
    @NamedQuery(name = "OsobaDet.findByOsdWspDod3", query = "SELECT o FROM OsobaDet o WHERE o.osdWspDod3 = :osdWspDod3"),
    @NamedQuery(name = "OsobaDet.findByOsdWspDod4", query = "SELECT o FROM OsobaDet o WHERE o.osdWspDod4 = :osdWspDod4"),
    @NamedQuery(name = "OsobaDet.findByOsdWspDod5", query = "SELECT o FROM OsobaDet o WHERE o.osdWspDod5 = :osdWspDod5"),
    @NamedQuery(name = "OsobaDet.findByOsdWspDod6", query = "SELECT o FROM OsobaDet o WHERE o.osdWspDod6 = :osdWspDod6"),
    @NamedQuery(name = "OsobaDet.findByOsdWspDod7", query = "SELECT o FROM OsobaDet o WHERE o.osdWspDod7 = :osdWspDod7"),
    @NamedQuery(name = "OsobaDet.findByOsdWspDod8", query = "SELECT o FROM OsobaDet o WHERE o.osdWspDod8 = :osdWspDod8"),
    @NamedQuery(name = "OsobaDet.findByOsdWspDod9", query = "SELECT o FROM OsobaDet o WHERE o.osdWspDod9 = :osdWspDod9"),
    @NamedQuery(name = "OsobaDet.findByOsdWspDod10", query = "SELECT o FROM OsobaDet o WHERE o.osdWspDod10 = :osdWspDod10"),
    @NamedQuery(name = "OsobaDet.findByOsdDod9", query = "SELECT o FROM OsobaDet o WHERE o.osdDod9 = :osdDod9"),
    @NamedQuery(name = "OsobaDet.findByOsdDod10", query = "SELECT o FROM OsobaDet o WHERE o.osdDod10 = :osdDod10"),
    @NamedQuery(name = "OsobaDet.findByOsdDod11", query = "SELECT o FROM OsobaDet o WHERE o.osdDod11 = :osdDod11"),
    @NamedQuery(name = "OsobaDet.findByOsdDod12", query = "SELECT o FROM OsobaDet o WHERE o.osdDod12 = :osdDod12"),
    @NamedQuery(name = "OsobaDet.findByOsdDod13", query = "SELECT o FROM OsobaDet o WHERE o.osdDod13 = :osdDod13"),
    @NamedQuery(name = "OsobaDet.findByOsdDod14", query = "SELECT o FROM OsobaDet o WHERE o.osdDod14 = :osdDod14"),
    @NamedQuery(name = "OsobaDet.findByOsdDod15", query = "SELECT o FROM OsobaDet o WHERE o.osdDod15 = :osdDod15"),
    @NamedQuery(name = "OsobaDet.findByOsdDod16", query = "SELECT o FROM OsobaDet o WHERE o.osdDod16 = :osdDod16")})
public class OsobaDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "osd_serial", nullable = false)
    private Integer osdSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "osd_tytul", nullable = false)
    private Character osdTytul;
    @Column(name = "osd_zus_emer")
    private Character osdZusEmer;
    @Column(name = "osd_zus_rent")
    private Character osdZusRent;
    @Column(name = "osd_zus_chor")
    private Character osdZusChor;
    @Column(name = "osd_zus_wyp")
    private Character osdZusWyp;
    @Column(name = "osd_zus_zdro")
    private Character osdZusZdro;
    @Column(name = "osd_zus_fp")
    private Character osdZusFp;
    @Column(name = "osd_zus_fgsp")
    private Character osdZusFgsp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "osd_ub_em_ubez", precision = 5, scale = 2)
    private BigDecimal osdUbEmUbez;
    @Column(name = "osd_ub_em_prac", precision = 5, scale = 2)
    private BigDecimal osdUbEmPrac;
    @Column(name = "osd_ub_em_budz", precision = 5, scale = 2)
    private BigDecimal osdUbEmBudz;
    @Column(name = "osd_ub_em_pfron", precision = 5, scale = 2)
    private BigDecimal osdUbEmPfron;
    @Column(name = "osd_ub_re_ubez", precision = 5, scale = 2)
    private BigDecimal osdUbReUbez;
    @Column(name = "osd_ub_re_prac", precision = 5, scale = 2)
    private BigDecimal osdUbRePrac;
    @Column(name = "osd_ub_re_budz", precision = 5, scale = 2)
    private BigDecimal osdUbReBudz;
    @Column(name = "osd_ub_re_pfron", precision = 5, scale = 2)
    private BigDecimal osdUbRePfron;
    @Column(name = "osd_ub_ch_ubez", precision = 5, scale = 2)
    private BigDecimal osdUbChUbez;
    @Column(name = "osd_ub_ch_prac", precision = 5, scale = 2)
    private BigDecimal osdUbChPrac;
    @Column(name = "osd_ub_ch_budz", precision = 5, scale = 2)
    private BigDecimal osdUbChBudz;
    @Column(name = "osd_ub_ch_pfron", precision = 5, scale = 2)
    private BigDecimal osdUbChPfron;
    @Column(name = "osd_ub_wy_ubez", precision = 5, scale = 2)
    private BigDecimal osdUbWyUbez;
    @Column(name = "osd_ub_wy_prac", precision = 5, scale = 2)
    private BigDecimal osdUbWyPrac;
    @Column(name = "osd_ub_wy_budz", precision = 5, scale = 2)
    private BigDecimal osdUbWyBudz;
    @Column(name = "osd_ub_wy_pfron", precision = 5, scale = 2)
    private BigDecimal osdUbWyPfron;
    @Column(name = "osd_ub_zd_ubez", precision = 5, scale = 2)
    private BigDecimal osdUbZdUbez;
    @Column(name = "osd_ub_zd_prac", precision = 5, scale = 2)
    private BigDecimal osdUbZdPrac;
    @Column(name = "osd_ub_zd_budz", precision = 5, scale = 2)
    private BigDecimal osdUbZdBudz;
    @Column(name = "osd_ub_zd_pfron", precision = 5, scale = 2)
    private BigDecimal osdUbZdPfron;
    @Column(name = "osd_ub_fp_ubez", precision = 5, scale = 2)
    private BigDecimal osdUbFpUbez;
    @Column(name = "osd_ub_fp_prac", precision = 5, scale = 2)
    private BigDecimal osdUbFpPrac;
    @Column(name = "osd_ub_fp_budz", precision = 5, scale = 2)
    private BigDecimal osdUbFpBudz;
    @Column(name = "osd_ub_fp_pfron", precision = 5, scale = 2)
    private BigDecimal osdUbFpPfron;
    @Column(name = "osd_ub_fg_ubez", precision = 5, scale = 2)
    private BigDecimal osdUbFgUbez;
    @Column(name = "osd_ub_fg_prac", precision = 5, scale = 2)
    private BigDecimal osdUbFgPrac;
    @Column(name = "osd_ub_fg_budz", precision = 5, scale = 2)
    private BigDecimal osdUbFgBudz;
    @Column(name = "osd_ub_fg_pfron", precision = 5, scale = 2)
    private BigDecimal osdUbFgPfron;
    @Size(max = 8)
    @Column(name = "osd_kod_tyt_u_1_2", length = 8)
    private String osdKodTytU12;
    @Column(name = "osd_dod_1")
    private Character osdDod1;
    @Column(name = "osd_dod_2")
    private Character osdDod2;
    @Column(name = "osd_dod_3")
    private Character osdDod3;
    @Column(name = "osd_ogr_pdst_chor")
    private Character osdOgrPdstChor;
    @Column(name = "osd_dod_4")
    private Character osdDod4;
    @Column(name = "osd_dod_5")
    private Character osdDod5;
    @Column(name = "osd_dod_6")
    private Character osdDod6;
    @Column(name = "osd_dod_7")
    private Character osdDod7;
    @Column(name = "osd_dod_8")
    private Character osdDod8;
    @Column(name = "osd_emer_proc", precision = 5, scale = 2)
    private BigDecimal osdEmerProc;
    @Column(name = "osd_rent_proc", precision = 5, scale = 2)
    private BigDecimal osdRentProc;
    @Column(name = "osd_chor_proc", precision = 5, scale = 2)
    private BigDecimal osdChorProc;
    @Column(name = "osd_wyp_proc", precision = 5, scale = 2)
    private BigDecimal osdWypProc;
    @Column(name = "osd_zdro_proc", precision = 5, scale = 2)
    private BigDecimal osdZdroProc;
    @Column(name = "osd_fp_proc", precision = 5, scale = 2)
    private BigDecimal osdFpProc;
    @Column(name = "osd_fgsp_proc", precision = 5, scale = 2)
    private BigDecimal osdFgspProc;
    @Column(name = "osd_emer_proc_o")
    private Character osdEmerProcO;
    @Column(name = "osd_rent_proc_o")
    private Character osdRentProcO;
    @Column(name = "osd_chor_proc_o")
    private Character osdChorProcO;
    @Column(name = "osd_wyp_proc_o")
    private Character osdWypProcO;
    @Column(name = "osd_zdro_proc_o")
    private Character osdZdroProcO;
    @Column(name = "osd_fp_proc_o")
    private Character osdFpProcO;
    @Column(name = "osd_fgsp_proc_o")
    private Character osdFgspProcO;
    @Column(name = "osd_zus_emer_dobr")
    private Character osdZusEmerDobr;
    @Column(name = "osd_zus_rent_dobr")
    private Character osdZusRentDobr;
    @Column(name = "osd_zus_zdro_dobr")
    private Character osdZusZdroDobr;
    @Column(name = "osd_zus_wyp_dobr")
    private Character osdZusWypDobr;
    @Column(name = "osd_int_1")
    private Integer osdInt1;
    @Column(name = "osd_int_2")
    private Integer osdInt2;
    @Column(name = "osd_num_1", precision = 17, scale = 6)
    private BigDecimal osdNum1;
    @Column(name = "osd_num_2", precision = 17, scale = 6)
    private BigDecimal osdNum2;
    @Column(name = "osd_num_3", precision = 17, scale = 6)
    private BigDecimal osdNum3;
    @Column(name = "osd_num_4", precision = 17, scale = 6)
    private BigDecimal osdNum4;
    @Column(name = "osd_wsp_dod_1", precision = 5, scale = 2)
    private BigDecimal osdWspDod1;
    @Column(name = "osd_wsp_dod_2", precision = 5, scale = 2)
    private BigDecimal osdWspDod2;
    @Column(name = "osd_wsp_dod_3", precision = 5, scale = 2)
    private BigDecimal osdWspDod3;
    @Column(name = "osd_wsp_dod_4", precision = 5, scale = 2)
    private BigDecimal osdWspDod4;
    @Column(name = "osd_wsp_dod_5", precision = 5, scale = 2)
    private BigDecimal osdWspDod5;
    @Column(name = "osd_wsp_dod_6", precision = 5, scale = 2)
    private BigDecimal osdWspDod6;
    @Column(name = "osd_wsp_dod_7", precision = 5, scale = 2)
    private BigDecimal osdWspDod7;
    @Column(name = "osd_wsp_dod_8", precision = 5, scale = 2)
    private BigDecimal osdWspDod8;
    @Column(name = "osd_wsp_dod_9", precision = 5, scale = 2)
    private BigDecimal osdWspDod9;
    @Column(name = "osd_wsp_dod_10", precision = 5, scale = 2)
    private BigDecimal osdWspDod10;
    @Column(name = "osd_dod_9")
    private Character osdDod9;
    @Column(name = "osd_dod_10")
    private Character osdDod10;
    @Column(name = "osd_dod_11")
    private Character osdDod11;
    @Column(name = "osd_dod_12")
    private Character osdDod12;
    @Column(name = "osd_dod_13")
    private Character osdDod13;
    @Column(name = "osd_dod_14")
    private Character osdDod14;
    @Column(name = "osd_dod_15")
    private Character osdDod15;
    @Column(name = "osd_dod_16")
    private Character osdDod16;
    @JoinColumn(name = "osd_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba osdOsoSerial;
    @JoinColumn(name = "osd_opt_serial", referencedColumnName = "opt_serial", nullable = false)
    @ManyToOne(optional = false)
    private OsobaPropTyp osdOptSerial;
    @JoinColumn(name = "osd_wkt_serial", referencedColumnName = "wkt_serial")
    @ManyToOne
    private WynKodTyt osdWktSerial;

    public OsobaDet() {
    }

    public OsobaDet(Integer osdSerial) {
        this.osdSerial = osdSerial;
    }

    public OsobaDet(Integer osdSerial, Character osdTytul) {
        this.osdSerial = osdSerial;
        this.osdTytul = osdTytul;
    }

    public Integer getOsdSerial() {
        return osdSerial;
    }

    public void setOsdSerial(Integer osdSerial) {
        this.osdSerial = osdSerial;
    }

    public Character getOsdTytul() {
        return osdTytul;
    }

    public void setOsdTytul(Character osdTytul) {
        this.osdTytul = osdTytul;
    }

    public Character getOsdZusEmer() {
        return osdZusEmer;
    }

    public void setOsdZusEmer(Character osdZusEmer) {
        this.osdZusEmer = osdZusEmer;
    }

    public Character getOsdZusRent() {
        return osdZusRent;
    }

    public void setOsdZusRent(Character osdZusRent) {
        this.osdZusRent = osdZusRent;
    }

    public Character getOsdZusChor() {
        return osdZusChor;
    }

    public void setOsdZusChor(Character osdZusChor) {
        this.osdZusChor = osdZusChor;
    }

    public Character getOsdZusWyp() {
        return osdZusWyp;
    }

    public void setOsdZusWyp(Character osdZusWyp) {
        this.osdZusWyp = osdZusWyp;
    }

    public Character getOsdZusZdro() {
        return osdZusZdro;
    }

    public void setOsdZusZdro(Character osdZusZdro) {
        this.osdZusZdro = osdZusZdro;
    }

    public Character getOsdZusFp() {
        return osdZusFp;
    }

    public void setOsdZusFp(Character osdZusFp) {
        this.osdZusFp = osdZusFp;
    }

    public Character getOsdZusFgsp() {
        return osdZusFgsp;
    }

    public void setOsdZusFgsp(Character osdZusFgsp) {
        this.osdZusFgsp = osdZusFgsp;
    }

    public BigDecimal getOsdUbEmUbez() {
        return osdUbEmUbez;
    }

    public void setOsdUbEmUbez(BigDecimal osdUbEmUbez) {
        this.osdUbEmUbez = osdUbEmUbez;
    }

    public BigDecimal getOsdUbEmPrac() {
        return osdUbEmPrac;
    }

    public void setOsdUbEmPrac(BigDecimal osdUbEmPrac) {
        this.osdUbEmPrac = osdUbEmPrac;
    }

    public BigDecimal getOsdUbEmBudz() {
        return osdUbEmBudz;
    }

    public void setOsdUbEmBudz(BigDecimal osdUbEmBudz) {
        this.osdUbEmBudz = osdUbEmBudz;
    }

    public BigDecimal getOsdUbEmPfron() {
        return osdUbEmPfron;
    }

    public void setOsdUbEmPfron(BigDecimal osdUbEmPfron) {
        this.osdUbEmPfron = osdUbEmPfron;
    }

    public BigDecimal getOsdUbReUbez() {
        return osdUbReUbez;
    }

    public void setOsdUbReUbez(BigDecimal osdUbReUbez) {
        this.osdUbReUbez = osdUbReUbez;
    }

    public BigDecimal getOsdUbRePrac() {
        return osdUbRePrac;
    }

    public void setOsdUbRePrac(BigDecimal osdUbRePrac) {
        this.osdUbRePrac = osdUbRePrac;
    }

    public BigDecimal getOsdUbReBudz() {
        return osdUbReBudz;
    }

    public void setOsdUbReBudz(BigDecimal osdUbReBudz) {
        this.osdUbReBudz = osdUbReBudz;
    }

    public BigDecimal getOsdUbRePfron() {
        return osdUbRePfron;
    }

    public void setOsdUbRePfron(BigDecimal osdUbRePfron) {
        this.osdUbRePfron = osdUbRePfron;
    }

    public BigDecimal getOsdUbChUbez() {
        return osdUbChUbez;
    }

    public void setOsdUbChUbez(BigDecimal osdUbChUbez) {
        this.osdUbChUbez = osdUbChUbez;
    }

    public BigDecimal getOsdUbChPrac() {
        return osdUbChPrac;
    }

    public void setOsdUbChPrac(BigDecimal osdUbChPrac) {
        this.osdUbChPrac = osdUbChPrac;
    }

    public BigDecimal getOsdUbChBudz() {
        return osdUbChBudz;
    }

    public void setOsdUbChBudz(BigDecimal osdUbChBudz) {
        this.osdUbChBudz = osdUbChBudz;
    }

    public BigDecimal getOsdUbChPfron() {
        return osdUbChPfron;
    }

    public void setOsdUbChPfron(BigDecimal osdUbChPfron) {
        this.osdUbChPfron = osdUbChPfron;
    }

    public BigDecimal getOsdUbWyUbez() {
        return osdUbWyUbez;
    }

    public void setOsdUbWyUbez(BigDecimal osdUbWyUbez) {
        this.osdUbWyUbez = osdUbWyUbez;
    }

    public BigDecimal getOsdUbWyPrac() {
        return osdUbWyPrac;
    }

    public void setOsdUbWyPrac(BigDecimal osdUbWyPrac) {
        this.osdUbWyPrac = osdUbWyPrac;
    }

    public BigDecimal getOsdUbWyBudz() {
        return osdUbWyBudz;
    }

    public void setOsdUbWyBudz(BigDecimal osdUbWyBudz) {
        this.osdUbWyBudz = osdUbWyBudz;
    }

    public BigDecimal getOsdUbWyPfron() {
        return osdUbWyPfron;
    }

    public void setOsdUbWyPfron(BigDecimal osdUbWyPfron) {
        this.osdUbWyPfron = osdUbWyPfron;
    }

    public BigDecimal getOsdUbZdUbez() {
        return osdUbZdUbez;
    }

    public void setOsdUbZdUbez(BigDecimal osdUbZdUbez) {
        this.osdUbZdUbez = osdUbZdUbez;
    }

    public BigDecimal getOsdUbZdPrac() {
        return osdUbZdPrac;
    }

    public void setOsdUbZdPrac(BigDecimal osdUbZdPrac) {
        this.osdUbZdPrac = osdUbZdPrac;
    }

    public BigDecimal getOsdUbZdBudz() {
        return osdUbZdBudz;
    }

    public void setOsdUbZdBudz(BigDecimal osdUbZdBudz) {
        this.osdUbZdBudz = osdUbZdBudz;
    }

    public BigDecimal getOsdUbZdPfron() {
        return osdUbZdPfron;
    }

    public void setOsdUbZdPfron(BigDecimal osdUbZdPfron) {
        this.osdUbZdPfron = osdUbZdPfron;
    }

    public BigDecimal getOsdUbFpUbez() {
        return osdUbFpUbez;
    }

    public void setOsdUbFpUbez(BigDecimal osdUbFpUbez) {
        this.osdUbFpUbez = osdUbFpUbez;
    }

    public BigDecimal getOsdUbFpPrac() {
        return osdUbFpPrac;
    }

    public void setOsdUbFpPrac(BigDecimal osdUbFpPrac) {
        this.osdUbFpPrac = osdUbFpPrac;
    }

    public BigDecimal getOsdUbFpBudz() {
        return osdUbFpBudz;
    }

    public void setOsdUbFpBudz(BigDecimal osdUbFpBudz) {
        this.osdUbFpBudz = osdUbFpBudz;
    }

    public BigDecimal getOsdUbFpPfron() {
        return osdUbFpPfron;
    }

    public void setOsdUbFpPfron(BigDecimal osdUbFpPfron) {
        this.osdUbFpPfron = osdUbFpPfron;
    }

    public BigDecimal getOsdUbFgUbez() {
        return osdUbFgUbez;
    }

    public void setOsdUbFgUbez(BigDecimal osdUbFgUbez) {
        this.osdUbFgUbez = osdUbFgUbez;
    }

    public BigDecimal getOsdUbFgPrac() {
        return osdUbFgPrac;
    }

    public void setOsdUbFgPrac(BigDecimal osdUbFgPrac) {
        this.osdUbFgPrac = osdUbFgPrac;
    }

    public BigDecimal getOsdUbFgBudz() {
        return osdUbFgBudz;
    }

    public void setOsdUbFgBudz(BigDecimal osdUbFgBudz) {
        this.osdUbFgBudz = osdUbFgBudz;
    }

    public BigDecimal getOsdUbFgPfron() {
        return osdUbFgPfron;
    }

    public void setOsdUbFgPfron(BigDecimal osdUbFgPfron) {
        this.osdUbFgPfron = osdUbFgPfron;
    }

    public String getOsdKodTytU12() {
        return osdKodTytU12;
    }

    public void setOsdKodTytU12(String osdKodTytU12) {
        this.osdKodTytU12 = osdKodTytU12;
    }

    public Character getOsdDod1() {
        return osdDod1;
    }

    public void setOsdDod1(Character osdDod1) {
        this.osdDod1 = osdDod1;
    }

    public Character getOsdDod2() {
        return osdDod2;
    }

    public void setOsdDod2(Character osdDod2) {
        this.osdDod2 = osdDod2;
    }

    public Character getOsdDod3() {
        return osdDod3;
    }

    public void setOsdDod3(Character osdDod3) {
        this.osdDod3 = osdDod3;
    }

    public Character getOsdOgrPdstChor() {
        return osdOgrPdstChor;
    }

    public void setOsdOgrPdstChor(Character osdOgrPdstChor) {
        this.osdOgrPdstChor = osdOgrPdstChor;
    }

    public Character getOsdDod4() {
        return osdDod4;
    }

    public void setOsdDod4(Character osdDod4) {
        this.osdDod4 = osdDod4;
    }

    public Character getOsdDod5() {
        return osdDod5;
    }

    public void setOsdDod5(Character osdDod5) {
        this.osdDod5 = osdDod5;
    }

    public Character getOsdDod6() {
        return osdDod6;
    }

    public void setOsdDod6(Character osdDod6) {
        this.osdDod6 = osdDod6;
    }

    public Character getOsdDod7() {
        return osdDod7;
    }

    public void setOsdDod7(Character osdDod7) {
        this.osdDod7 = osdDod7;
    }

    public Character getOsdDod8() {
        return osdDod8;
    }

    public void setOsdDod8(Character osdDod8) {
        this.osdDod8 = osdDod8;
    }

    public BigDecimal getOsdEmerProc() {
        return osdEmerProc;
    }

    public void setOsdEmerProc(BigDecimal osdEmerProc) {
        this.osdEmerProc = osdEmerProc;
    }

    public BigDecimal getOsdRentProc() {
        return osdRentProc;
    }

    public void setOsdRentProc(BigDecimal osdRentProc) {
        this.osdRentProc = osdRentProc;
    }

    public BigDecimal getOsdChorProc() {
        return osdChorProc;
    }

    public void setOsdChorProc(BigDecimal osdChorProc) {
        this.osdChorProc = osdChorProc;
    }

    public BigDecimal getOsdWypProc() {
        return osdWypProc;
    }

    public void setOsdWypProc(BigDecimal osdWypProc) {
        this.osdWypProc = osdWypProc;
    }

    public BigDecimal getOsdZdroProc() {
        return osdZdroProc;
    }

    public void setOsdZdroProc(BigDecimal osdZdroProc) {
        this.osdZdroProc = osdZdroProc;
    }

    public BigDecimal getOsdFpProc() {
        return osdFpProc;
    }

    public void setOsdFpProc(BigDecimal osdFpProc) {
        this.osdFpProc = osdFpProc;
    }

    public BigDecimal getOsdFgspProc() {
        return osdFgspProc;
    }

    public void setOsdFgspProc(BigDecimal osdFgspProc) {
        this.osdFgspProc = osdFgspProc;
    }

    public Character getOsdEmerProcO() {
        return osdEmerProcO;
    }

    public void setOsdEmerProcO(Character osdEmerProcO) {
        this.osdEmerProcO = osdEmerProcO;
    }

    public Character getOsdRentProcO() {
        return osdRentProcO;
    }

    public void setOsdRentProcO(Character osdRentProcO) {
        this.osdRentProcO = osdRentProcO;
    }

    public Character getOsdChorProcO() {
        return osdChorProcO;
    }

    public void setOsdChorProcO(Character osdChorProcO) {
        this.osdChorProcO = osdChorProcO;
    }

    public Character getOsdWypProcO() {
        return osdWypProcO;
    }

    public void setOsdWypProcO(Character osdWypProcO) {
        this.osdWypProcO = osdWypProcO;
    }

    public Character getOsdZdroProcO() {
        return osdZdroProcO;
    }

    public void setOsdZdroProcO(Character osdZdroProcO) {
        this.osdZdroProcO = osdZdroProcO;
    }

    public Character getOsdFpProcO() {
        return osdFpProcO;
    }

    public void setOsdFpProcO(Character osdFpProcO) {
        this.osdFpProcO = osdFpProcO;
    }

    public Character getOsdFgspProcO() {
        return osdFgspProcO;
    }

    public void setOsdFgspProcO(Character osdFgspProcO) {
        this.osdFgspProcO = osdFgspProcO;
    }

    public Character getOsdZusEmerDobr() {
        return osdZusEmerDobr;
    }

    public void setOsdZusEmerDobr(Character osdZusEmerDobr) {
        this.osdZusEmerDobr = osdZusEmerDobr;
    }

    public Character getOsdZusRentDobr() {
        return osdZusRentDobr;
    }

    public void setOsdZusRentDobr(Character osdZusRentDobr) {
        this.osdZusRentDobr = osdZusRentDobr;
    }

    public Character getOsdZusZdroDobr() {
        return osdZusZdroDobr;
    }

    public void setOsdZusZdroDobr(Character osdZusZdroDobr) {
        this.osdZusZdroDobr = osdZusZdroDobr;
    }

    public Character getOsdZusWypDobr() {
        return osdZusWypDobr;
    }

    public void setOsdZusWypDobr(Character osdZusWypDobr) {
        this.osdZusWypDobr = osdZusWypDobr;
    }

    public Integer getOsdInt1() {
        return osdInt1;
    }

    public void setOsdInt1(Integer osdInt1) {
        this.osdInt1 = osdInt1;
    }

    public Integer getOsdInt2() {
        return osdInt2;
    }

    public void setOsdInt2(Integer osdInt2) {
        this.osdInt2 = osdInt2;
    }

    public BigDecimal getOsdNum1() {
        return osdNum1;
    }

    public void setOsdNum1(BigDecimal osdNum1) {
        this.osdNum1 = osdNum1;
    }

    public BigDecimal getOsdNum2() {
        return osdNum2;
    }

    public void setOsdNum2(BigDecimal osdNum2) {
        this.osdNum2 = osdNum2;
    }

    public BigDecimal getOsdNum3() {
        return osdNum3;
    }

    public void setOsdNum3(BigDecimal osdNum3) {
        this.osdNum3 = osdNum3;
    }

    public BigDecimal getOsdNum4() {
        return osdNum4;
    }

    public void setOsdNum4(BigDecimal osdNum4) {
        this.osdNum4 = osdNum4;
    }

    public BigDecimal getOsdWspDod1() {
        return osdWspDod1;
    }

    public void setOsdWspDod1(BigDecimal osdWspDod1) {
        this.osdWspDod1 = osdWspDod1;
    }

    public BigDecimal getOsdWspDod2() {
        return osdWspDod2;
    }

    public void setOsdWspDod2(BigDecimal osdWspDod2) {
        this.osdWspDod2 = osdWspDod2;
    }

    public BigDecimal getOsdWspDod3() {
        return osdWspDod3;
    }

    public void setOsdWspDod3(BigDecimal osdWspDod3) {
        this.osdWspDod3 = osdWspDod3;
    }

    public BigDecimal getOsdWspDod4() {
        return osdWspDod4;
    }

    public void setOsdWspDod4(BigDecimal osdWspDod4) {
        this.osdWspDod4 = osdWspDod4;
    }

    public BigDecimal getOsdWspDod5() {
        return osdWspDod5;
    }

    public void setOsdWspDod5(BigDecimal osdWspDod5) {
        this.osdWspDod5 = osdWspDod5;
    }

    public BigDecimal getOsdWspDod6() {
        return osdWspDod6;
    }

    public void setOsdWspDod6(BigDecimal osdWspDod6) {
        this.osdWspDod6 = osdWspDod6;
    }

    public BigDecimal getOsdWspDod7() {
        return osdWspDod7;
    }

    public void setOsdWspDod7(BigDecimal osdWspDod7) {
        this.osdWspDod7 = osdWspDod7;
    }

    public BigDecimal getOsdWspDod8() {
        return osdWspDod8;
    }

    public void setOsdWspDod8(BigDecimal osdWspDod8) {
        this.osdWspDod8 = osdWspDod8;
    }

    public BigDecimal getOsdWspDod9() {
        return osdWspDod9;
    }

    public void setOsdWspDod9(BigDecimal osdWspDod9) {
        this.osdWspDod9 = osdWspDod9;
    }

    public BigDecimal getOsdWspDod10() {
        return osdWspDod10;
    }

    public void setOsdWspDod10(BigDecimal osdWspDod10) {
        this.osdWspDod10 = osdWspDod10;
    }

    public Character getOsdDod9() {
        return osdDod9;
    }

    public void setOsdDod9(Character osdDod9) {
        this.osdDod9 = osdDod9;
    }

    public Character getOsdDod10() {
        return osdDod10;
    }

    public void setOsdDod10(Character osdDod10) {
        this.osdDod10 = osdDod10;
    }

    public Character getOsdDod11() {
        return osdDod11;
    }

    public void setOsdDod11(Character osdDod11) {
        this.osdDod11 = osdDod11;
    }

    public Character getOsdDod12() {
        return osdDod12;
    }

    public void setOsdDod12(Character osdDod12) {
        this.osdDod12 = osdDod12;
    }

    public Character getOsdDod13() {
        return osdDod13;
    }

    public void setOsdDod13(Character osdDod13) {
        this.osdDod13 = osdDod13;
    }

    public Character getOsdDod14() {
        return osdDod14;
    }

    public void setOsdDod14(Character osdDod14) {
        this.osdDod14 = osdDod14;
    }

    public Character getOsdDod15() {
        return osdDod15;
    }

    public void setOsdDod15(Character osdDod15) {
        this.osdDod15 = osdDod15;
    }

    public Character getOsdDod16() {
        return osdDod16;
    }

    public void setOsdDod16(Character osdDod16) {
        this.osdDod16 = osdDod16;
    }

    public Osoba getOsdOsoSerial() {
        return osdOsoSerial;
    }

    public void setOsdOsoSerial(Osoba osdOsoSerial) {
        this.osdOsoSerial = osdOsoSerial;
    }

    public OsobaPropTyp getOsdOptSerial() {
        return osdOptSerial;
    }

    public void setOsdOptSerial(OsobaPropTyp osdOptSerial) {
        this.osdOptSerial = osdOptSerial;
    }

    public WynKodTyt getOsdWktSerial() {
        return osdWktSerial;
    }

    public void setOsdWktSerial(WynKodTyt osdWktSerial) {
        this.osdWktSerial = osdWktSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (osdSerial != null ? osdSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OsobaDet)) {
            return false;
        }
        OsobaDet other = (OsobaDet) object;
        if ((this.osdSerial == null && other.osdSerial != null) || (this.osdSerial != null && !this.osdSerial.equals(other.osdSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.OsobaDet[ osdSerial=" + osdSerial + " ]";
    }
    
}
