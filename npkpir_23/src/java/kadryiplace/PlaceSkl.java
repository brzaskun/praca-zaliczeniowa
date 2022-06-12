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
@Table(name = "place_skl", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaceSkl.findAll", query = "SELECT p FROM PlaceSkl p"),
    @NamedQuery(name = "PlaceSkl.findBySklSerial", query = "SELECT p FROM PlaceSkl p WHERE p.sklSerial = :sklSerial"),
    @NamedQuery(name = "PlaceSkl.findBySklDataOd", query = "SELECT p FROM PlaceSkl p WHERE p.sklDataOd = :sklDataOd"),
    @NamedQuery(name = "PlaceSkl.findBySklDataDo", query = "SELECT p FROM PlaceSkl p WHERE p.sklDataDo = :sklDataDo"),
    @NamedQuery(name = "PlaceSkl.findBySklKwota", query = "SELECT p FROM PlaceSkl p WHERE p.sklKwota = :sklKwota"),
    @NamedQuery(name = "PlaceSkl.findBySklPodDoch", query = "SELECT p FROM PlaceSkl p WHERE p.sklPodDoch = :sklPodDoch"),
    @NamedQuery(name = "PlaceSkl.findBySklZus", query = "SELECT p FROM PlaceSkl p WHERE p.sklZus = :sklZus"),
    @NamedQuery(name = "PlaceSkl.findBySklZdrowotne", query = "SELECT p FROM PlaceSkl p WHERE p.sklZdrowotne = :sklZdrowotne"),
    @NamedQuery(name = "PlaceSkl.findBySklChorWyp", query = "SELECT p FROM PlaceSkl p WHERE p.sklChorWyp = :sklChorWyp"),
    @NamedQuery(name = "PlaceSkl.findBySklWyp", query = "SELECT p FROM PlaceSkl p WHERE p.sklWyp = :sklWyp"),
    @NamedQuery(name = "PlaceSkl.findBySklDod1", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod1 = :sklDod1"),
    @NamedQuery(name = "PlaceSkl.findBySklDod2", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod2 = :sklDod2"),
    @NamedQuery(name = "PlaceSkl.findBySklDod3", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod3 = :sklDod3"),
    @NamedQuery(name = "PlaceSkl.findBySklChorRed", query = "SELECT p FROM PlaceSkl p WHERE p.sklChorRed = :sklChorRed"),
    @NamedQuery(name = "PlaceSkl.findBySklPdstZasChor", query = "SELECT p FROM PlaceSkl p WHERE p.sklPdstZasChor = :sklPdstZasChor"),
    @NamedQuery(name = "PlaceSkl.findBySklRodzaj", query = "SELECT p FROM PlaceSkl p WHERE p.sklRodzaj = :sklRodzaj"),
    @NamedQuery(name = "PlaceSkl.findBySklDod4", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod4 = :sklDod4"),
    @NamedQuery(name = "PlaceSkl.findBySklDod5", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod5 = :sklDod5"),
    @NamedQuery(name = "PlaceSkl.findBySklDod6", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod6 = :sklDod6"),
    @NamedQuery(name = "PlaceSkl.findBySklDod7", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod7 = :sklDod7"),
    @NamedQuery(name = "PlaceSkl.findBySklDod8", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod8 = :sklDod8"),
    @NamedQuery(name = "PlaceSkl.findBySklNum1", query = "SELECT p FROM PlaceSkl p WHERE p.sklNum1 = :sklNum1"),
    @NamedQuery(name = "PlaceSkl.findBySklNum2", query = "SELECT p FROM PlaceSkl p WHERE p.sklNum2 = :sklNum2"),
    @NamedQuery(name = "PlaceSkl.findBySklDate1", query = "SELECT p FROM PlaceSkl p WHERE p.sklDate1 = :sklDate1"),
    @NamedQuery(name = "PlaceSkl.findBySklDate2", query = "SELECT p FROM PlaceSkl p WHERE p.sklDate2 = :sklDate2"),
    @NamedQuery(name = "PlaceSkl.findBySklTyp", query = "SELECT p FROM PlaceSkl p WHERE p.sklTyp = :sklTyp"),
    @NamedQuery(name = "PlaceSkl.findBySklVchar1", query = "SELECT p FROM PlaceSkl p WHERE p.sklVchar1 = :sklVchar1"),
    @NamedQuery(name = "PlaceSkl.findBySklDod9", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod9 = :sklDod9"),
    @NamedQuery(name = "PlaceSkl.findBySklDod10", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod10 = :sklDod10"),
    @NamedQuery(name = "PlaceSkl.findBySklDod11", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod11 = :sklDod11"),
    @NamedQuery(name = "PlaceSkl.findBySklDod12", query = "SELECT p FROM PlaceSkl p WHERE p.sklDod12 = :sklDod12"),
    @NamedQuery(name = "PlaceSkl.findBySklVchar2", query = "SELECT p FROM PlaceSkl p WHERE p.sklVchar2 = :sklVchar2"),
    @NamedQuery(name = "PlaceSkl.findBySklInt1", query = "SELECT p FROM PlaceSkl p WHERE p.sklInt1 = :sklInt1"),
    @NamedQuery(name = "PlaceSkl.findBySklInt2", query = "SELECT p FROM PlaceSkl p WHERE p.sklInt2 = :sklInt2"),
    @NamedQuery(name = "PlaceSkl.findBySklInt3", query = "SELECT p FROM PlaceSkl p WHERE p.sklInt3 = :sklInt3"),
    @NamedQuery(name = "PlaceSkl.findBySklInt4", query = "SELECT p FROM PlaceSkl p WHERE p.sklInt4 = :sklInt4")})
public class PlaceSkl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_serial", nullable = false)
    private Integer sklSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_data_od", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sklDataOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_data_do", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sklDataDo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal sklKwota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_pod_doch", nullable = false)
    private Character sklPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_zus", nullable = false)
    private Character sklZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_zdrowotne", nullable = false)
    private Character sklZdrowotne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_chor_wyp", nullable = false)
    private Character sklChorWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_wyp", nullable = false)
    private Character sklWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_dod_1", nullable = false)
    private Character sklDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_dod_2", nullable = false)
    private Character sklDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_dod_3", nullable = false)
    private Character sklDod3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_chor_red", nullable = false)
    private Character sklChorRed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_pdst_zas_chor", nullable = false)
    private Character sklPdstZasChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_rodzaj", nullable = false)
    private Character sklRodzaj;
    @Column(name = "skl_dod_4")
    private Character sklDod4;
    @Column(name = "skl_dod_5")
    private Character sklDod5;
    @Column(name = "skl_dod_6")
    private Character sklDod6;
    @Column(name = "skl_dod_7")
    private Character sklDod7;
    @Column(name = "skl_dod_8")
    private Character sklDod8;
    @Column(name = "skl_num_1", precision = 17, scale = 6)
    private BigDecimal sklNum1;
    @Column(name = "skl_num_2", precision = 17, scale = 6)
    private BigDecimal sklNum2;
    @Column(name = "skl_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sklDate1;
    @Column(name = "skl_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sklDate2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "skl_typ", nullable = false)
    private Character sklTyp;
    @Size(max = 64)
    @Column(name = "skl_vchar_1", length = 64)
    private String sklVchar1;
    @Column(name = "skl_dod_9")
    private Character sklDod9;
    @Column(name = "skl_dod_10")
    private Character sklDod10;
    @Column(name = "skl_dod_11")
    private Character sklDod11;
    @Column(name = "skl_dod_12")
    private Character sklDod12;
    @Size(max = 64)
    @Column(name = "skl_vchar_2", length = 64)
    private String sklVchar2;
    @Column(name = "skl_int_1")
    private Integer sklInt1;
    @Column(name = "skl_int_2")
    private Integer sklInt2;
    @Column(name = "skl_int_3")
    private Integer sklInt3;
    @Column(name = "skl_int_4")
    private Integer sklInt4;
    @JoinColumn(name = "skl_lis_serial", referencedColumnName = "lis_serial")
    @ManyToOne
    private Listy sklLisSerial;
    @JoinColumn(name = "skl_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres sklOkrSerial;
    @JoinColumn(name = "skl_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba sklOsoSerial;
    @JoinColumn(name = "skl_lpl_serial", referencedColumnName = "lpl_serial")
    @ManyToOne
    private Place sklLplSerial;
    @JoinColumn(name = "skl_wks_serial", referencedColumnName = "wks_serial", nullable = false)
    @ManyToOne(optional = false)
    private WynKodSkl sklWksSerial;
    @OneToMany(mappedBy = "dswSklSerial")
    private List<DaneStatW> daneStatWList;

    public PlaceSkl() {
    }

    public PlaceSkl(Integer sklSerial) {
        this.sklSerial = sklSerial;
    }

    public PlaceSkl(Integer sklSerial, Date sklDataOd, Date sklDataDo, BigDecimal sklKwota, Character sklPodDoch, Character sklZus, Character sklZdrowotne, Character sklChorWyp, Character sklWyp, Character sklDod1, Character sklDod2, Character sklDod3, Character sklChorRed, Character sklPdstZasChor, Character sklRodzaj, Character sklTyp) {
        this.sklSerial = sklSerial;
        this.sklDataOd = sklDataOd;
        this.sklDataDo = sklDataDo;
        this.sklKwota = sklKwota;
        this.sklPodDoch = sklPodDoch;
        this.sklZus = sklZus;
        this.sklZdrowotne = sklZdrowotne;
        this.sklChorWyp = sklChorWyp;
        this.sklWyp = sklWyp;
        this.sklDod1 = sklDod1;
        this.sklDod2 = sklDod2;
        this.sklDod3 = sklDod3;
        this.sklChorRed = sklChorRed;
        this.sklPdstZasChor = sklPdstZasChor;
        this.sklRodzaj = sklRodzaj;
        this.sklTyp = sklTyp;
    }

    public Integer getSklSerial() {
        return sklSerial;
    }

    public void setSklSerial(Integer sklSerial) {
        this.sklSerial = sklSerial;
    }

    public Date getSklDataOd() {
        return sklDataOd;
    }

    public void setSklDataOd(Date sklDataOd) {
        this.sklDataOd = sklDataOd;
    }

    public Date getSklDataDo() {
        return sklDataDo;
    }

    public void setSklDataDo(Date sklDataDo) {
        this.sklDataDo = sklDataDo;
    }

    public BigDecimal getSklKwota() {
        return sklKwota;
    }

    public void setSklKwota(BigDecimal sklKwota) {
        this.sklKwota = sklKwota;
    }

    public Character getSklPodDoch() {
        return sklPodDoch;
    }

    public void setSklPodDoch(Character sklPodDoch) {
        this.sklPodDoch = sklPodDoch;
    }

    public Character getSklZus() {
        return sklZus;
    }

    public void setSklZus(Character sklZus) {
        this.sklZus = sklZus;
    }

    public Character getSklZdrowotne() {
        return sklZdrowotne;
    }

    public void setSklZdrowotne(Character sklZdrowotne) {
        this.sklZdrowotne = sklZdrowotne;
    }

    public Character getSklChorWyp() {
        return sklChorWyp;
    }

    public void setSklChorWyp(Character sklChorWyp) {
        this.sklChorWyp = sklChorWyp;
    }

    public Character getSklWyp() {
        return sklWyp;
    }

    public void setSklWyp(Character sklWyp) {
        this.sklWyp = sklWyp;
    }

    public Character getSklDod1() {
        return sklDod1;
    }

    public void setSklDod1(Character sklDod1) {
        this.sklDod1 = sklDod1;
    }

    public Character getSklDod2() {
        return sklDod2;
    }

    public void setSklDod2(Character sklDod2) {
        this.sklDod2 = sklDod2;
    }

    public Character getSklDod3() {
        return sklDod3;
    }

    public void setSklDod3(Character sklDod3) {
        this.sklDod3 = sklDod3;
    }

    public Character getSklChorRed() {
        return sklChorRed;
    }

    public void setSklChorRed(Character sklChorRed) {
        this.sklChorRed = sklChorRed;
    }

    public Character getSklPdstZasChor() {
        return sklPdstZasChor;
    }

    public void setSklPdstZasChor(Character sklPdstZasChor) {
        this.sklPdstZasChor = sklPdstZasChor;
    }

    public Character getSklRodzaj() {
        return sklRodzaj;
    }

    public void setSklRodzaj(Character sklRodzaj) {
        this.sklRodzaj = sklRodzaj;
    }

    public Character getSklDod4() {
        return sklDod4;
    }

    public void setSklDod4(Character sklDod4) {
        this.sklDod4 = sklDod4;
    }

    public Character getSklDod5() {
        return sklDod5;
    }

    public void setSklDod5(Character sklDod5) {
        this.sklDod5 = sklDod5;
    }

    public Character getSklDod6() {
        return sklDod6;
    }

    public void setSklDod6(Character sklDod6) {
        this.sklDod6 = sklDod6;
    }

    public Character getSklDod7() {
        return sklDod7;
    }

    public void setSklDod7(Character sklDod7) {
        this.sklDod7 = sklDod7;
    }

    public Character getSklDod8() {
        return sklDod8;
    }

    public void setSklDod8(Character sklDod8) {
        this.sklDod8 = sklDod8;
    }

    public BigDecimal getSklNum1() {
        return sklNum1;
    }

    public void setSklNum1(BigDecimal sklNum1) {
        this.sklNum1 = sklNum1;
    }

    public BigDecimal getSklNum2() {
        return sklNum2;
    }

    public void setSklNum2(BigDecimal sklNum2) {
        this.sklNum2 = sklNum2;
    }

    public Date getSklDate1() {
        return sklDate1;
    }

    public void setSklDate1(Date sklDate1) {
        this.sklDate1 = sklDate1;
    }

    public Date getSklDate2() {
        return sklDate2;
    }

    public void setSklDate2(Date sklDate2) {
        this.sklDate2 = sklDate2;
    }

    public Character getSklTyp() {
        return sklTyp;
    }

    public void setSklTyp(Character sklTyp) {
        this.sklTyp = sklTyp;
    }

    public String getSklVchar1() {
        return sklVchar1;
    }

    public void setSklVchar1(String sklVchar1) {
        this.sklVchar1 = sklVchar1;
    }

    public Character getSklDod9() {
        return sklDod9;
    }

    public void setSklDod9(Character sklDod9) {
        this.sklDod9 = sklDod9;
    }

    public Character getSklDod10() {
        return sklDod10;
    }

    public void setSklDod10(Character sklDod10) {
        this.sklDod10 = sklDod10;
    }

    public Character getSklDod11() {
        return sklDod11;
    }

    public void setSklDod11(Character sklDod11) {
        this.sklDod11 = sklDod11;
    }

    public Character getSklDod12() {
        return sklDod12;
    }

    public void setSklDod12(Character sklDod12) {
        this.sklDod12 = sklDod12;
    }

    public String getSklVchar2() {
        return sklVchar2;
    }

    public void setSklVchar2(String sklVchar2) {
        this.sklVchar2 = sklVchar2;
    }

    public Integer getSklInt1() {
        return sklInt1;
    }

    public void setSklInt1(Integer sklInt1) {
        this.sklInt1 = sklInt1;
    }

    public Integer getSklInt2() {
        return sklInt2;
    }

    public void setSklInt2(Integer sklInt2) {
        this.sklInt2 = sklInt2;
    }

    public Integer getSklInt3() {
        return sklInt3;
    }

    public void setSklInt3(Integer sklInt3) {
        this.sklInt3 = sklInt3;
    }

    public Integer getSklInt4() {
        return sklInt4;
    }

    public void setSklInt4(Integer sklInt4) {
        this.sklInt4 = sklInt4;
    }

    public Listy getSklLisSerial() {
        return sklLisSerial;
    }

    public void setSklLisSerial(Listy sklLisSerial) {
        this.sklLisSerial = sklLisSerial;
    }

    public Okres getSklOkrSerial() {
        return sklOkrSerial;
    }

    public void setSklOkrSerial(Okres sklOkrSerial) {
        this.sklOkrSerial = sklOkrSerial;
    }

    public Osoba getSklOsoSerial() {
        return sklOsoSerial;
    }

    public void setSklOsoSerial(Osoba sklOsoSerial) {
        this.sklOsoSerial = sklOsoSerial;
    }

    public Place getSklLplSerial() {
        return sklLplSerial;
    }

    public void setSklLplSerial(Place sklLplSerial) {
        this.sklLplSerial = sklLplSerial;
    }

    public WynKodSkl getSklWksSerial() {
        return sklWksSerial;
    }

    public void setSklWksSerial(WynKodSkl sklWksSerial) {
        this.sklWksSerial = sklWksSerial;
    }

    @XmlTransient
    public List<DaneStatW> getDaneStatWList() {
        return daneStatWList;
    }

    public void setDaneStatWList(List<DaneStatW> daneStatWList) {
        this.daneStatWList = daneStatWList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sklSerial != null ? sklSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaceSkl)) {
            return false;
        }
        PlaceSkl other = (PlaceSkl) object;
        if ((this.sklSerial == null && other.sklSerial != null) || (this.sklSerial != null && !this.sklSerial.equals(other.sklSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.PlaceSkl[ sklSerial=" + sklSerial + " ]";
    }
    
}
