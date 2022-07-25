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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "place_zlec", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaceZlec.findAll", query = "SELECT p FROM PlaceZlec p"),
    @NamedQuery(name = "PlaceZlec.findByPzlSerial", query = "SELECT p FROM PlaceZlec p WHERE p.pzlSerial = :pzlSerial"),
    @NamedQuery(name = "PlaceZlec.findByPzlDataOd", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDataOd = :pzlDataOd"),
    @NamedQuery(name = "PlaceZlec.findByPzlDataDo", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDataDo = :pzlDataDo"),
    @NamedQuery(name = "PlaceZlec.findByPzlKwota", query = "SELECT p FROM PlaceZlec p WHERE p.pzlKwota = :pzlKwota"),
    @NamedQuery(name = "PlaceZlec.findByPzlKoszt", query = "SELECT p FROM PlaceZlec p WHERE p.pzlKoszt = :pzlKoszt"),
    @NamedQuery(name = "PlaceZlec.findByPzlPodDoch", query = "SELECT p FROM PlaceZlec p WHERE p.pzlPodDoch = :pzlPodDoch"),
    @NamedQuery(name = "PlaceZlec.findByPzlZus", query = "SELECT p FROM PlaceZlec p WHERE p.pzlZus = :pzlZus"),
    @NamedQuery(name = "PlaceZlec.findByPzlZdrowotne", query = "SELECT p FROM PlaceZlec p WHERE p.pzlZdrowotne = :pzlZdrowotne"),
    @NamedQuery(name = "PlaceZlec.findByPzlChorWyp", query = "SELECT p FROM PlaceZlec p WHERE p.pzlChorWyp = :pzlChorWyp"),
    @NamedQuery(name = "PlaceZlec.findByPzlWyp", query = "SELECT p FROM PlaceZlec p WHERE p.pzlWyp = :pzlWyp"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod1", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod1 = :pzlDod1"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod2", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod2 = :pzlDod2"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod3", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod3 = :pzlDod3"),
    @NamedQuery(name = "PlaceZlec.findByPzlKosztR", query = "SELECT p FROM PlaceZlec p WHERE p.pzlKosztR = :pzlKosztR"),
    @NamedQuery(name = "PlaceZlec.findByPzlKosztProc", query = "SELECT p FROM PlaceZlec p WHERE p.pzlKosztProc = :pzlKosztProc"),
    @NamedQuery(name = "PlaceZlec.findByPzlPdstZasChor", query = "SELECT p FROM PlaceZlec p WHERE p.pzlPdstZasChor = :pzlPdstZasChor"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod4", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod4 = :pzlDod4"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod5", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod5 = :pzlDod5"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod6", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod6 = :pzlDod6"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod7", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod7 = :pzlDod7"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod8", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod8 = :pzlDod8"),
    @NamedQuery(name = "PlaceZlec.findByPzlNum1", query = "SELECT p FROM PlaceZlec p WHERE p.pzlNum1 = :pzlNum1"),
    @NamedQuery(name = "PlaceZlec.findByPzlNum2", query = "SELECT p FROM PlaceZlec p WHERE p.pzlNum2 = :pzlNum2"),
    @NamedQuery(name = "PlaceZlec.findByPzlNum3", query = "SELECT p FROM PlaceZlec p WHERE p.pzlNum3 = :pzlNum3"),
    @NamedQuery(name = "PlaceZlec.findByPzlNum4", query = "SELECT p FROM PlaceZlec p WHERE p.pzlNum4 = :pzlNum4"),
    @NamedQuery(name = "PlaceZlec.findByPzlVchar1", query = "SELECT p FROM PlaceZlec p WHERE p.pzlVchar1 = :pzlVchar1"),
    @NamedQuery(name = "PlaceZlec.findByPzlVchar2", query = "SELECT p FROM PlaceZlec p WHERE p.pzlVchar2 = :pzlVchar2"),
    @NamedQuery(name = "PlaceZlec.findByPzlDate1", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDate1 = :pzlDate1"),
    @NamedQuery(name = "PlaceZlec.findByPzlDate2", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDate2 = :pzlDate2"),
    @NamedQuery(name = "PlaceZlec.findByPzlTyp", query = "SELECT p FROM PlaceZlec p WHERE p.pzlTyp = :pzlTyp"),
    @NamedQuery(name = "PlaceZlec.findByPzlVchar3", query = "SELECT p FROM PlaceZlec p WHERE p.pzlVchar3 = :pzlVchar3"),
    @NamedQuery(name = "PlaceZlec.findByPzlVchar4", query = "SELECT p FROM PlaceZlec p WHERE p.pzlVchar4 = :pzlVchar4"),
    @NamedQuery(name = "PlaceZlec.findByPzlDate3", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDate3 = :pzlDate3"),
    @NamedQuery(name = "PlaceZlec.findByPzlDate4", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDate4 = :pzlDate4"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod9", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod9 = :pzlDod9"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod10", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod10 = :pzlDod10"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod11", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod11 = :pzlDod11"),
    @NamedQuery(name = "PlaceZlec.findByPzlDod12", query = "SELECT p FROM PlaceZlec p WHERE p.pzlDod12 = :pzlDod12"),
    @NamedQuery(name = "PlaceZlec.findByPzlInt1", query = "SELECT p FROM PlaceZlec p WHERE p.pzlInt1 = :pzlInt1"),
    @NamedQuery(name = "PlaceZlec.findByPzlInt2", query = "SELECT p FROM PlaceZlec p WHERE p.pzlInt2 = :pzlInt2"),
    @NamedQuery(name = "PlaceZlec.findByPzlInt3", query = "SELECT p FROM PlaceZlec p WHERE p.pzlInt3 = :pzlInt3"),
    @NamedQuery(name = "PlaceZlec.findByPzlInt4", query = "SELECT p FROM PlaceZlec p WHERE p.pzlInt4 = :pzlInt4")})
public class PlaceZlec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_serial", nullable = false)
    private Integer pzlSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_data_od", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pzlDataOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_data_do", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pzlDataDo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal pzlKwota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_koszt", nullable = false, precision = 13, scale = 2)
    private BigDecimal pzlKoszt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_pod_doch", nullable = false)
    private Character pzlPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_zus", nullable = false)
    private Character pzlZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_zdrowotne", nullable = false)
    private Character pzlZdrowotne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_chor_wyp", nullable = false)
    private Character pzlChorWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_wyp", nullable = false)
    private Character pzlWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_dod_1", nullable = false)
    private Character pzlDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_dod_2", nullable = false)
    private Character pzlDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_dod_3", nullable = false)
    private Character pzlDod3;
    @Column(name = "pzl_koszt_r")
    private Character pzlKosztR;
    @Column(name = "pzl_koszt_proc", precision = 5, scale = 2)
    private BigDecimal pzlKosztProc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_pdst_zas_chor", nullable = false)
    private Character pzlPdstZasChor;
    @Column(name = "pzl_dod_4")
    private Character pzlDod4;
    @Column(name = "pzl_dod_5")
    private Character pzlDod5;
    @Column(name = "pzl_dod_6")
    private Character pzlDod6;
    @Column(name = "pzl_dod_7")
    private Character pzlDod7;
    @Column(name = "pzl_dod_8")
    private Character pzlDod8;
    @Column(name = "pzl_num_1", precision = 17, scale = 6)
    private BigDecimal pzlNum1;
    @Column(name = "pzl_num_2", precision = 17, scale = 6)
    private BigDecimal pzlNum2;
    @Column(name = "pzl_num_3", precision = 17, scale = 6)
    private BigDecimal pzlNum3;
    @Column(name = "pzl_num_4", precision = 17, scale = 6)
    private BigDecimal pzlNum4;
    @Size(max = 64)
    @Column(name = "pzl_vchar_1", length = 64)
    private String pzlVchar1;
    @Size(max = 64)
    @Column(name = "pzl_vchar_2", length = 64)
    private String pzlVchar2;
    @Column(name = "pzl_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pzlDate1;
    @Column(name = "pzl_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pzlDate2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzl_typ", nullable = false)
    private Character pzlTyp;
    @Size(max = 64)
    @Column(name = "pzl_vchar_3", length = 64)
    private String pzlVchar3;
    @Size(max = 64)
    @Column(name = "pzl_vchar_4", length = 64)
    private String pzlVchar4;
    @Column(name = "pzl_date_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pzlDate3;
    @Column(name = "pzl_date_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pzlDate4;
    @Column(name = "pzl_dod_9")
    private Character pzlDod9;
    @Column(name = "pzl_dod_10")
    private Character pzlDod10;
    @Column(name = "pzl_dod_11")
    private Character pzlDod11;
    @Column(name = "pzl_dod_12")
    private Character pzlDod12;
    @Column(name = "pzl_int_1")
    private Integer pzlInt1;
    @Column(name = "pzl_int_2")
    private Integer pzlInt2;
    @Column(name = "pzl_int_3")
    private Integer pzlInt3;
    @Column(name = "pzl_int_4")
    private Integer pzlInt4;
    @OneToMany(mappedBy = "dswPzlSerial")
    private List<DaneStatW> daneStatWList;
    @JoinColumn(name = "pzl_lis_serial", referencedColumnName = "lis_serial")
    @ManyToOne
    private Listy pzlLisSerial;
    @JoinColumn(name = "pzl_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres pzlOkrSerial;
    @JoinColumn(name = "pzl_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba pzlOsoSerial;
    @JoinColumn(name = "pzl_ozl_serial", referencedColumnName = "ozl_serial")
    @ManyToOne
    private OsobaZlec pzlOzlSerial;
    @JoinColumn(name = "pzl_lpl_serial", referencedColumnName = "lpl_serial")
    @ManyToOne
    private Place pzlLplSerial;
    @JoinColumn(name = "pzl_wks_serial", referencedColumnName = "wks_serial", nullable = false)
    @ManyToOne(optional = false)
    private WynKodSkl pzlWksSerial;

    public PlaceZlec() {
    }

    public PlaceZlec(Integer pzlSerial) {
        this.pzlSerial = pzlSerial;
    }

    public PlaceZlec(Integer pzlSerial, Date pzlDataOd, Date pzlDataDo, BigDecimal pzlKwota, BigDecimal pzlKoszt, Character pzlPodDoch, Character pzlZus, Character pzlZdrowotne, Character pzlChorWyp, Character pzlWyp, Character pzlDod1, Character pzlDod2, Character pzlDod3, Character pzlPdstZasChor, Character pzlTyp) {
        this.pzlSerial = pzlSerial;
        this.pzlDataOd = pzlDataOd;
        this.pzlDataDo = pzlDataDo;
        this.pzlKwota = pzlKwota;
        this.pzlKoszt = pzlKoszt;
        this.pzlPodDoch = pzlPodDoch;
        this.pzlZus = pzlZus;
        this.pzlZdrowotne = pzlZdrowotne;
        this.pzlChorWyp = pzlChorWyp;
        this.pzlWyp = pzlWyp;
        this.pzlDod1 = pzlDod1;
        this.pzlDod2 = pzlDod2;
        this.pzlDod3 = pzlDod3;
        this.pzlPdstZasChor = pzlPdstZasChor;
        this.pzlTyp = pzlTyp;
    }

    public Integer getPzlSerial() {
        return pzlSerial;
    }

    public void setPzlSerial(Integer pzlSerial) {
        this.pzlSerial = pzlSerial;
    }

    public Date getPzlDataOd() {
        return pzlDataOd;
    }

    public void setPzlDataOd(Date pzlDataOd) {
        this.pzlDataOd = pzlDataOd;
    }

    public Date getPzlDataDo() {
        return pzlDataDo;
    }

    public void setPzlDataDo(Date pzlDataDo) {
        this.pzlDataDo = pzlDataDo;
    }

    public BigDecimal getPzlKwota() {
        return pzlKwota;
    }

    public void setPzlKwota(BigDecimal pzlKwota) {
        this.pzlKwota = pzlKwota;
    }

    public BigDecimal getPzlKoszt() {
        return pzlKoszt;
    }

    public void setPzlKoszt(BigDecimal pzlKoszt) {
        this.pzlKoszt = pzlKoszt;
    }

    public Character getPzlPodDoch() {
        return pzlPodDoch;
    }

    public void setPzlPodDoch(Character pzlPodDoch) {
        this.pzlPodDoch = pzlPodDoch;
    }

    public Character getPzlZus() {
        return pzlZus;
    }

    public void setPzlZus(Character pzlZus) {
        this.pzlZus = pzlZus;
    }

    public Character getPzlZdrowotne() {
        return pzlZdrowotne;
    }

    public void setPzlZdrowotne(Character pzlZdrowotne) {
        this.pzlZdrowotne = pzlZdrowotne;
    }

    public Character getPzlChorWyp() {
        return pzlChorWyp;
    }

    public void setPzlChorWyp(Character pzlChorWyp) {
        this.pzlChorWyp = pzlChorWyp;
    }

    public Character getPzlWyp() {
        return pzlWyp;
    }

    public void setPzlWyp(Character pzlWyp) {
        this.pzlWyp = pzlWyp;
    }

    public Character getPzlDod1() {
        return pzlDod1;
    }

    public void setPzlDod1(Character pzlDod1) {
        this.pzlDod1 = pzlDod1;
    }

    public Character getPzlDod2() {
        return pzlDod2;
    }

    public void setPzlDod2(Character pzlDod2) {
        this.pzlDod2 = pzlDod2;
    }

    public Character getPzlDod3() {
        return pzlDod3;
    }

    public void setPzlDod3(Character pzlDod3) {
        this.pzlDod3 = pzlDod3;
    }

    public Character getPzlKosztR() {
        return pzlKosztR;
    }

    public void setPzlKosztR(Character pzlKosztR) {
        this.pzlKosztR = pzlKosztR;
    }

    public BigDecimal getPzlKosztProc() {
        return pzlKosztProc;
    }

    public void setPzlKosztProc(BigDecimal pzlKosztProc) {
        this.pzlKosztProc = pzlKosztProc;
    }

    public Character getPzlPdstZasChor() {
        return pzlPdstZasChor;
    }

    public void setPzlPdstZasChor(Character pzlPdstZasChor) {
        this.pzlPdstZasChor = pzlPdstZasChor;
    }

    public Character getPzlDod4() {
        return pzlDod4;
    }

    public void setPzlDod4(Character pzlDod4) {
        this.pzlDod4 = pzlDod4;
    }

    public Character getPzlDod5() {
        return pzlDod5;
    }

    public void setPzlDod5(Character pzlDod5) {
        this.pzlDod5 = pzlDod5;
    }

    public Character getPzlDod6() {
        return pzlDod6;
    }

    public void setPzlDod6(Character pzlDod6) {
        this.pzlDod6 = pzlDod6;
    }

    public Character getPzlDod7() {
        return pzlDod7;
    }

    public void setPzlDod7(Character pzlDod7) {
        this.pzlDod7 = pzlDod7;
    }

    public Character getPzlDod8() {
        return pzlDod8;
    }

    public void setPzlDod8(Character pzlDod8) {
        this.pzlDod8 = pzlDod8;
    }

    public BigDecimal getPzlNum1() {
        return pzlNum1;
    }

    public void setPzlNum1(BigDecimal pzlNum1) {
        this.pzlNum1 = pzlNum1;
    }

    public BigDecimal getPzlNum2() {
        return pzlNum2;
    }

    public void setPzlNum2(BigDecimal pzlNum2) {
        this.pzlNum2 = pzlNum2;
    }

    public BigDecimal getPzlNum3() {
        return pzlNum3;
    }

    public void setPzlNum3(BigDecimal pzlNum3) {
        this.pzlNum3 = pzlNum3;
    }

    public BigDecimal getPzlNum4() {
        return pzlNum4;
    }

    public void setPzlNum4(BigDecimal pzlNum4) {
        this.pzlNum4 = pzlNum4;
    }

    public String getPzlVchar1() {
        return pzlVchar1;
    }

    public void setPzlVchar1(String pzlVchar1) {
        this.pzlVchar1 = pzlVchar1;
    }

    public String getPzlVchar2() {
        return pzlVchar2;
    }

    public void setPzlVchar2(String pzlVchar2) {
        this.pzlVchar2 = pzlVchar2;
    }

    public Date getPzlDate1() {
        return pzlDate1;
    }

    public void setPzlDate1(Date pzlDate1) {
        this.pzlDate1 = pzlDate1;
    }

    public Date getPzlDate2() {
        return pzlDate2;
    }

    public void setPzlDate2(Date pzlDate2) {
        this.pzlDate2 = pzlDate2;
    }

    public Character getPzlTyp() {
        return pzlTyp;
    }

    public void setPzlTyp(Character pzlTyp) {
        this.pzlTyp = pzlTyp;
    }

    public String getPzlVchar3() {
        return pzlVchar3;
    }

    public void setPzlVchar3(String pzlVchar3) {
        this.pzlVchar3 = pzlVchar3;
    }

    public String getPzlVchar4() {
        return pzlVchar4;
    }

    public void setPzlVchar4(String pzlVchar4) {
        this.pzlVchar4 = pzlVchar4;
    }

    public Date getPzlDate3() {
        return pzlDate3;
    }

    public void setPzlDate3(Date pzlDate3) {
        this.pzlDate3 = pzlDate3;
    }

    public Date getPzlDate4() {
        return pzlDate4;
    }

    public void setPzlDate4(Date pzlDate4) {
        this.pzlDate4 = pzlDate4;
    }

    public Character getPzlDod9() {
        return pzlDod9;
    }

    public void setPzlDod9(Character pzlDod9) {
        this.pzlDod9 = pzlDod9;
    }

    public Character getPzlDod10() {
        return pzlDod10;
    }

    public void setPzlDod10(Character pzlDod10) {
        this.pzlDod10 = pzlDod10;
    }

    public Character getPzlDod11() {
        return pzlDod11;
    }

    public void setPzlDod11(Character pzlDod11) {
        this.pzlDod11 = pzlDod11;
    }

    public Character getPzlDod12() {
        return pzlDod12;
    }

    public void setPzlDod12(Character pzlDod12) {
        this.pzlDod12 = pzlDod12;
    }

    public Integer getPzlInt1() {
        return pzlInt1;
    }

    public void setPzlInt1(Integer pzlInt1) {
        this.pzlInt1 = pzlInt1;
    }

    public Integer getPzlInt2() {
        return pzlInt2;
    }

    public void setPzlInt2(Integer pzlInt2) {
        this.pzlInt2 = pzlInt2;
    }

    public Integer getPzlInt3() {
        return pzlInt3;
    }

    public void setPzlInt3(Integer pzlInt3) {
        this.pzlInt3 = pzlInt3;
    }

    public Integer getPzlInt4() {
        return pzlInt4;
    }

    public void setPzlInt4(Integer pzlInt4) {
        this.pzlInt4 = pzlInt4;
    }

    @XmlTransient
    public List<DaneStatW> getDaneStatWList() {
        return daneStatWList;
    }

    public void setDaneStatWList(List<DaneStatW> daneStatWList) {
        this.daneStatWList = daneStatWList;
    }

    public Listy getPzlLisSerial() {
        return pzlLisSerial;
    }

    public void setPzlLisSerial(Listy pzlLisSerial) {
        this.pzlLisSerial = pzlLisSerial;
    }

    public Okres getPzlOkrSerial() {
        return pzlOkrSerial;
    }

    public void setPzlOkrSerial(Okres pzlOkrSerial) {
        this.pzlOkrSerial = pzlOkrSerial;
    }

    public Osoba getPzlOsoSerial() {
        return pzlOsoSerial;
    }

    public void setPzlOsoSerial(Osoba pzlOsoSerial) {
        this.pzlOsoSerial = pzlOsoSerial;
    }

    public OsobaZlec getPzlOzlSerial() {
        return pzlOzlSerial;
    }

    public void setPzlOzlSerial(OsobaZlec pzlOzlSerial) {
        this.pzlOzlSerial = pzlOzlSerial;
    }

    public Place getPzlLplSerial() {
        return pzlLplSerial;
    }

    public void setPzlLplSerial(Place pzlLplSerial) {
        this.pzlLplSerial = pzlLplSerial;
    }

    public WynKodSkl getPzlWksSerial() {
        return pzlWksSerial;
    }

    public void setPzlWksSerial(WynKodSkl pzlWksSerial) {
        this.pzlWksSerial = pzlWksSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pzlSerial != null ? pzlSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaceZlec)) {
            return false;
        }
        PlaceZlec other = (PlaceZlec) object;
        if ((this.pzlSerial == null && other.pzlSerial != null) || (this.pzlSerial != null && !this.pzlSerial.equals(other.pzlSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.PlaceZlec[ pzlSerial=" + pzlSerial + " ]";
    }
    
}
