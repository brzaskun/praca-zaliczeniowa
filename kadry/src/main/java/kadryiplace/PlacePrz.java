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
@Table(name = "place_prz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlacePrz.findAll", query = "SELECT p FROM PlacePrz p"),
    @NamedQuery(name = "PlacePrz.findByPrzSerial", query = "SELECT p FROM PlacePrz p WHERE p.przSerial = :przSerial"),
    @NamedQuery(name = "PlacePrz.findByPrzDataOd", query = "SELECT p FROM PlacePrz p WHERE p.przDataOd = :przDataOd"),
    @NamedQuery(name = "PlacePrz.findByPrzDataDo", query = "SELECT p FROM PlacePrz p WHERE p.przDataDo = :przDataDo"),
    @NamedQuery(name = "PlacePrz.findByPrzLiczba", query = "SELECT p FROM PlacePrz p WHERE p.przLiczba = :przLiczba"),
    @NamedQuery(name = "PlacePrz.findByPrzKodCh1", query = "SELECT p FROM PlacePrz p WHERE p.przKodCh1 = :przKodCh1"),
    @NamedQuery(name = "PlacePrz.findByPrzKodCh2", query = "SELECT p FROM PlacePrz p WHERE p.przKodCh2 = :przKodCh2"),
    @NamedQuery(name = "PlacePrz.findByPrzKwota", query = "SELECT p FROM PlacePrz p WHERE p.przKwota = :przKwota"),
    @NamedQuery(name = "PlacePrz.findByPrzPodDoch", query = "SELECT p FROM PlacePrz p WHERE p.przPodDoch = :przPodDoch"),
    @NamedQuery(name = "PlacePrz.findByPrzZus", query = "SELECT p FROM PlacePrz p WHERE p.przZus = :przZus"),
    @NamedQuery(name = "PlacePrz.findByPrzZdrowotne", query = "SELECT p FROM PlacePrz p WHERE p.przZdrowotne = :przZdrowotne"),
    @NamedQuery(name = "PlacePrz.findByPrzChorWyp", query = "SELECT p FROM PlacePrz p WHERE p.przChorWyp = :przChorWyp"),
    @NamedQuery(name = "PlacePrz.findByPrzWyp", query = "SELECT p FROM PlacePrz p WHERE p.przWyp = :przWyp"),
    @NamedQuery(name = "PlacePrz.findByPrzDod1", query = "SELECT p FROM PlacePrz p WHERE p.przDod1 = :przDod1"),
    @NamedQuery(name = "PlacePrz.findByPrzDod2", query = "SELECT p FROM PlacePrz p WHERE p.przDod2 = :przDod2"),
    @NamedQuery(name = "PlacePrz.findByPrzDod3", query = "SELECT p FROM PlacePrz p WHERE p.przDod3 = :przDod3"),
    @NamedQuery(name = "PlacePrz.findByPrzPodstawa", query = "SELECT p FROM PlacePrz p WHERE p.przPodstawa = :przPodstawa"),
    @NamedQuery(name = "PlacePrz.findByPrzKwota1", query = "SELECT p FROM PlacePrz p WHERE p.przKwota1 = :przKwota1"),
    @NamedQuery(name = "PlacePrz.findByPrzKwota2", query = "SELECT p FROM PlacePrz p WHERE p.przKwota2 = :przKwota2"),
    @NamedQuery(name = "PlacePrz.findByPrzWspolcz", query = "SELECT p FROM PlacePrz p WHERE p.przWspolcz = :przWspolcz"),
    @NamedQuery(name = "PlacePrz.findByPrzWkpZrodloFin", query = "SELECT p FROM PlacePrz p WHERE p.przWkpZrodloFin = :przWkpZrodloFin"),
    @NamedQuery(name = "PlacePrz.findByPrzPrzekrRpw", query = "SELECT p FROM PlacePrz p WHERE p.przPrzekrRpw = :przPrzekrRpw"),
    @NamedQuery(name = "PlacePrz.findByPrzAbsencja", query = "SELECT p FROM PlacePrz p WHERE p.przAbsencja = :przAbsencja"),
    @NamedQuery(name = "PlacePrz.findByPrzPdstZasChor", query = "SELECT p FROM PlacePrz p WHERE p.przPdstZasChor = :przPdstZasChor"),
    @NamedQuery(name = "PlacePrz.findByPrzDod4", query = "SELECT p FROM PlacePrz p WHERE p.przDod4 = :przDod4"),
    @NamedQuery(name = "PlacePrz.findByPrzDod5", query = "SELECT p FROM PlacePrz p WHERE p.przDod5 = :przDod5"),
    @NamedQuery(name = "PlacePrz.findByPrzDod6", query = "SELECT p FROM PlacePrz p WHERE p.przDod6 = :przDod6"),
    @NamedQuery(name = "PlacePrz.findByPrzDod7", query = "SELECT p FROM PlacePrz p WHERE p.przDod7 = :przDod7"),
    @NamedQuery(name = "PlacePrz.findByPrzDod8", query = "SELECT p FROM PlacePrz p WHERE p.przDod8 = :przDod8"),
    @NamedQuery(name = "PlacePrz.findByPrzNum1", query = "SELECT p FROM PlacePrz p WHERE p.przNum1 = :przNum1"),
    @NamedQuery(name = "PlacePrz.findByPrzNum2", query = "SELECT p FROM PlacePrz p WHERE p.przNum2 = :przNum2"),
    @NamedQuery(name = "PlacePrz.findByPrzDate1", query = "SELECT p FROM PlacePrz p WHERE p.przDate1 = :przDate1"),
    @NamedQuery(name = "PlacePrz.findByPrzDate2", query = "SELECT p FROM PlacePrz p WHERE p.przDate2 = :przDate2"),
    @NamedQuery(name = "PlacePrz.findByPrzTyp", query = "SELECT p FROM PlacePrz p WHERE p.przTyp = :przTyp"),
    @NamedQuery(name = "PlacePrz.findByPrzVchar1", query = "SELECT p FROM PlacePrz p WHERE p.przVchar1 = :przVchar1"),
    @NamedQuery(name = "PlacePrz.findByPrzDod9", query = "SELECT p FROM PlacePrz p WHERE p.przDod9 = :przDod9"),
    @NamedQuery(name = "PlacePrz.findByPrzDod10", query = "SELECT p FROM PlacePrz p WHERE p.przDod10 = :przDod10"),
    @NamedQuery(name = "PlacePrz.findByPrzDod11", query = "SELECT p FROM PlacePrz p WHERE p.przDod11 = :przDod11"),
    @NamedQuery(name = "PlacePrz.findByPrzDod12", query = "SELECT p FROM PlacePrz p WHERE p.przDod12 = :przDod12"),
    @NamedQuery(name = "PlacePrz.findByPrzVchar2", query = "SELECT p FROM PlacePrz p WHERE p.przVchar2 = :przVchar2"),
    @NamedQuery(name = "PlacePrz.findByPrzOkrSredniej", query = "SELECT p FROM PlacePrz p WHERE p.przOkrSredniej = :przOkrSredniej"),
    @NamedQuery(name = "PlacePrz.findByPrzInt1", query = "SELECT p FROM PlacePrz p WHERE p.przInt1 = :przInt1"),
    @NamedQuery(name = "PlacePrz.findByPrzInt2", query = "SELECT p FROM PlacePrz p WHERE p.przInt2 = :przInt2"),
    @NamedQuery(name = "PlacePrz.findByPrzInt3", query = "SELECT p FROM PlacePrz p WHERE p.przInt3 = :przInt3"),
    @NamedQuery(name = "PlacePrz.findByPrzInt4", query = "SELECT p FROM PlacePrz p WHERE p.przInt4 = :przInt4")})
public class PlacePrz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_serial", nullable = false)
    private Integer przSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_data_od", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date przDataOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_data_do", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date przDataDo;
    @Column(name = "prz_liczba")
    private Short przLiczba;
    @Size(max = 1)
    @Column(name = "prz_kod_ch_1", length = 1)
    private String przKodCh1;
    @Size(max = 1)
    @Column(name = "prz_kod_ch_2", length = 1)
    private String przKodCh2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal przKwota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_pod_doch", nullable = false)
    private Character przPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_zus", nullable = false)
    private Character przZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_zdrowotne", nullable = false)
    private Character przZdrowotne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_chor_wyp", nullable = false)
    private Character przChorWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_wyp", nullable = false)
    private Character przWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_dod_1", nullable = false)
    private Character przDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_dod_2", nullable = false)
    private Character przDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_dod_3", nullable = false)
    private Character przDod3;
    @Column(name = "prz_podstawa", precision = 13, scale = 2)
    private BigDecimal przPodstawa;
    @Column(name = "prz_kwota_1", precision = 13, scale = 2)
    private BigDecimal przKwota1;
    @Column(name = "prz_kwota_2", precision = 13, scale = 2)
    private BigDecimal przKwota2;
    @Column(name = "prz_wspolcz", precision = 5, scale = 2)
    private BigDecimal przWspolcz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_wkp_zrodlo_fin", nullable = false)
    private Character przWkpZrodloFin;
    @Column(name = "prz_przekr_rpw")
    private Character przPrzekrRpw;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_absencja", nullable = false)
    private Character przAbsencja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_pdst_zas_chor", nullable = false)
    private Character przPdstZasChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_dod_4", nullable = false)
    private Character przDod4;
    @Column(name = "prz_dod_5")
    private Character przDod5;
    @Column(name = "prz_dod_6")
    private Character przDod6;
    @Column(name = "prz_dod_7")
    private Character przDod7;
    @Column(name = "prz_dod_8")
    private Character przDod8;
    @Column(name = "prz_num_1", precision = 17, scale = 6)
    private BigDecimal przNum1;
    @Column(name = "prz_num_2", precision = 17, scale = 6)
    private BigDecimal przNum2;
    @Column(name = "prz_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date przDate1;
    @Column(name = "prz_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date przDate2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_typ", nullable = false)
    private Character przTyp;
    @Size(max = 64)
    @Column(name = "prz_vchar_1", length = 64)
    private String przVchar1;
    @Column(name = "prz_dod_9")
    private Character przDod9;
    @Column(name = "prz_dod_10")
    private Character przDod10;
    @Column(name = "prz_dod_11")
    private Character przDod11;
    @Column(name = "prz_dod_12")
    private Character przDod12;
    @Size(max = 64)
    @Column(name = "prz_vchar_2", length = 64)
    private String przVchar2;
    @Size(max = 64)
    @Column(name = "prz_okr_sredniej", length = 64)
    private String przOkrSredniej;
    @Column(name = "prz_int_1")
    private Integer przInt1;
    @Column(name = "prz_int_2")
    private Integer przInt2;
    @Column(name = "prz_int_3")
    private Integer przInt3;
    @Column(name = "prz_int_4")
    private Integer przInt4;
    @OneToMany(mappedBy = "dswPrzSerial")
    private List<DaneStatW> daneStatWList;
    @JoinColumn(name = "prz_lis_serial", referencedColumnName = "lis_serial")
    @ManyToOne
    private Listy przLisSerial;
    @JoinColumn(name = "prz_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres przOkrSerial;
    @JoinColumn(name = "prz_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba przOsoSerial;
    @JoinColumn(name = "prz_lpl_serial", referencedColumnName = "lpl_serial")
    @ManyToOne
    private Place przLplSerial;
    @JoinColumn(name = "prz_wkp_serial", referencedColumnName = "wkp_serial", nullable = false)
    @ManyToOne(optional = false)
    private WynKodPrz przWkpSerial;

    public PlacePrz() {
    }

    public PlacePrz(Integer przSerial) {
        this.przSerial = przSerial;
    }

    public PlacePrz(Integer przSerial, Date przDataOd, Date przDataDo, BigDecimal przKwota, Character przPodDoch, Character przZus, Character przZdrowotne, Character przChorWyp, Character przWyp, Character przDod1, Character przDod2, Character przDod3, Character przWkpZrodloFin, Character przAbsencja, Character przPdstZasChor, Character przDod4, Character przTyp) {
        this.przSerial = przSerial;
        this.przDataOd = przDataOd;
        this.przDataDo = przDataDo;
        this.przKwota = przKwota;
        this.przPodDoch = przPodDoch;
        this.przZus = przZus;
        this.przZdrowotne = przZdrowotne;
        this.przChorWyp = przChorWyp;
        this.przWyp = przWyp;
        this.przDod1 = przDod1;
        this.przDod2 = przDod2;
        this.przDod3 = przDod3;
        this.przWkpZrodloFin = przWkpZrodloFin;
        this.przAbsencja = przAbsencja;
        this.przPdstZasChor = przPdstZasChor;
        this.przDod4 = przDod4;
        this.przTyp = przTyp;
    }

    public Integer getPrzSerial() {
        return przSerial;
    }

    public void setPrzSerial(Integer przSerial) {
        this.przSerial = przSerial;
    }

    public Date getPrzDataOd() {
        return przDataOd;
    }

    public void setPrzDataOd(Date przDataOd) {
        this.przDataOd = przDataOd;
    }

    public Date getPrzDataDo() {
        return przDataDo;
    }

    public void setPrzDataDo(Date przDataDo) {
        this.przDataDo = przDataDo;
    }

    public Short getPrzLiczba() {
        return przLiczba;
    }

    public void setPrzLiczba(Short przLiczba) {
        this.przLiczba = przLiczba;
    }

    public String getPrzKodCh1() {
        return przKodCh1;
    }

    public void setPrzKodCh1(String przKodCh1) {
        this.przKodCh1 = przKodCh1;
    }

    public String getPrzKodCh2() {
        return przKodCh2;
    }

    public void setPrzKodCh2(String przKodCh2) {
        this.przKodCh2 = przKodCh2;
    }

    public BigDecimal getPrzKwota() {
        return przKwota;
    }

    public void setPrzKwota(BigDecimal przKwota) {
        this.przKwota = przKwota;
    }

    public Character getPrzPodDoch() {
        return przPodDoch;
    }

    public void setPrzPodDoch(Character przPodDoch) {
        this.przPodDoch = przPodDoch;
    }

    public Character getPrzZus() {
        return przZus;
    }

    public void setPrzZus(Character przZus) {
        this.przZus = przZus;
    }

    public Character getPrzZdrowotne() {
        return przZdrowotne;
    }

    public void setPrzZdrowotne(Character przZdrowotne) {
        this.przZdrowotne = przZdrowotne;
    }

    public Character getPrzChorWyp() {
        return przChorWyp;
    }

    public void setPrzChorWyp(Character przChorWyp) {
        this.przChorWyp = przChorWyp;
    }

    public Character getPrzWyp() {
        return przWyp;
    }

    public void setPrzWyp(Character przWyp) {
        this.przWyp = przWyp;
    }

    public Character getPrzDod1() {
        return przDod1;
    }

    public void setPrzDod1(Character przDod1) {
        this.przDod1 = przDod1;
    }

    public Character getPrzDod2() {
        return przDod2;
    }

    public void setPrzDod2(Character przDod2) {
        this.przDod2 = przDod2;
    }

    public Character getPrzDod3() {
        return przDod3;
    }

    public void setPrzDod3(Character przDod3) {
        this.przDod3 = przDod3;
    }

    public BigDecimal getPrzPodstawa() {
        return przPodstawa;
    }

    public void setPrzPodstawa(BigDecimal przPodstawa) {
        this.przPodstawa = przPodstawa;
    }

    public BigDecimal getPrzKwota1() {
        return przKwota1;
    }

    public void setPrzKwota1(BigDecimal przKwota1) {
        this.przKwota1 = przKwota1;
    }

    public BigDecimal getPrzKwota2() {
        return przKwota2;
    }

    public void setPrzKwota2(BigDecimal przKwota2) {
        this.przKwota2 = przKwota2;
    }

    public BigDecimal getPrzWspolcz() {
        return przWspolcz;
    }

    public void setPrzWspolcz(BigDecimal przWspolcz) {
        this.przWspolcz = przWspolcz;
    }

    public Character getPrzWkpZrodloFin() {
        return przWkpZrodloFin;
    }

    public void setPrzWkpZrodloFin(Character przWkpZrodloFin) {
        this.przWkpZrodloFin = przWkpZrodloFin;
    }

    public Character getPrzPrzekrRpw() {
        return przPrzekrRpw;
    }

    public void setPrzPrzekrRpw(Character przPrzekrRpw) {
        this.przPrzekrRpw = przPrzekrRpw;
    }

    public Character getPrzAbsencja() {
        return przAbsencja;
    }

    public void setPrzAbsencja(Character przAbsencja) {
        this.przAbsencja = przAbsencja;
    }

    public Character getPrzPdstZasChor() {
        return przPdstZasChor;
    }

    public void setPrzPdstZasChor(Character przPdstZasChor) {
        this.przPdstZasChor = przPdstZasChor;
    }

    public Character getPrzDod4() {
        return przDod4;
    }

    public void setPrzDod4(Character przDod4) {
        this.przDod4 = przDod4;
    }

    public Character getPrzDod5() {
        return przDod5;
    }

    public void setPrzDod5(Character przDod5) {
        this.przDod5 = przDod5;
    }

    public Character getPrzDod6() {
        return przDod6;
    }

    public void setPrzDod6(Character przDod6) {
        this.przDod6 = przDod6;
    }

    public Character getPrzDod7() {
        return przDod7;
    }

    public void setPrzDod7(Character przDod7) {
        this.przDod7 = przDod7;
    }

    public Character getPrzDod8() {
        return przDod8;
    }

    public void setPrzDod8(Character przDod8) {
        this.przDod8 = przDod8;
    }

    public BigDecimal getPrzNum1() {
        return przNum1;
    }

    public void setPrzNum1(BigDecimal przNum1) {
        this.przNum1 = przNum1;
    }

    public BigDecimal getPrzNum2() {
        return przNum2;
    }

    public void setPrzNum2(BigDecimal przNum2) {
        this.przNum2 = przNum2;
    }

    public Date getPrzDate1() {
        return przDate1;
    }

    public void setPrzDate1(Date przDate1) {
        this.przDate1 = przDate1;
    }

    public Date getPrzDate2() {
        return przDate2;
    }

    public void setPrzDate2(Date przDate2) {
        this.przDate2 = przDate2;
    }

    public Character getPrzTyp() {
        return przTyp;
    }

    public void setPrzTyp(Character przTyp) {
        this.przTyp = przTyp;
    }

    public String getPrzVchar1() {
        return przVchar1;
    }

    public void setPrzVchar1(String przVchar1) {
        this.przVchar1 = przVchar1;
    }

    public Character getPrzDod9() {
        return przDod9;
    }

    public void setPrzDod9(Character przDod9) {
        this.przDod9 = przDod9;
    }

    public Character getPrzDod10() {
        return przDod10;
    }

    public void setPrzDod10(Character przDod10) {
        this.przDod10 = przDod10;
    }

    public Character getPrzDod11() {
        return przDod11;
    }

    public void setPrzDod11(Character przDod11) {
        this.przDod11 = przDod11;
    }

    public Character getPrzDod12() {
        return przDod12;
    }

    public void setPrzDod12(Character przDod12) {
        this.przDod12 = przDod12;
    }

    public String getPrzVchar2() {
        return przVchar2;
    }

    public void setPrzVchar2(String przVchar2) {
        this.przVchar2 = przVchar2;
    }

    public String getPrzOkrSredniej() {
        return przOkrSredniej;
    }

    public void setPrzOkrSredniej(String przOkrSredniej) {
        this.przOkrSredniej = przOkrSredniej;
    }

    public Integer getPrzInt1() {
        return przInt1;
    }

    public void setPrzInt1(Integer przInt1) {
        this.przInt1 = przInt1;
    }

    public Integer getPrzInt2() {
        return przInt2;
    }

    public void setPrzInt2(Integer przInt2) {
        this.przInt2 = przInt2;
    }

    public Integer getPrzInt3() {
        return przInt3;
    }

    public void setPrzInt3(Integer przInt3) {
        this.przInt3 = przInt3;
    }

    public Integer getPrzInt4() {
        return przInt4;
    }

    public void setPrzInt4(Integer przInt4) {
        this.przInt4 = przInt4;
    }

    @XmlTransient
    public List<DaneStatW> getDaneStatWList() {
        return daneStatWList;
    }

    public void setDaneStatWList(List<DaneStatW> daneStatWList) {
        this.daneStatWList = daneStatWList;
    }

    public Listy getPrzLisSerial() {
        return przLisSerial;
    }

    public void setPrzLisSerial(Listy przLisSerial) {
        this.przLisSerial = przLisSerial;
    }

    public Okres getPrzOkrSerial() {
        return przOkrSerial;
    }

    public void setPrzOkrSerial(Okres przOkrSerial) {
        this.przOkrSerial = przOkrSerial;
    }

    public Osoba getPrzOsoSerial() {
        return przOsoSerial;
    }

    public void setPrzOsoSerial(Osoba przOsoSerial) {
        this.przOsoSerial = przOsoSerial;
    }

    public Place getPrzLplSerial() {
        return przLplSerial;
    }

    public void setPrzLplSerial(Place przLplSerial) {
        this.przLplSerial = przLplSerial;
    }

    public WynKodPrz getPrzWkpSerial() {
        return przWkpSerial;
    }

    public void setPrzWkpSerial(WynKodPrz przWkpSerial) {
        this.przWkpSerial = przWkpSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (przSerial != null ? przSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlacePrz)) {
            return false;
        }
        PlacePrz other = (PlacePrz) object;
        if ((this.przSerial == null && other.przSerial != null) || (this.przSerial != null && !this.przSerial.equals(other.przSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.PlacePrz[ przSerial=" + przSerial + " ]";
    }
    
}
