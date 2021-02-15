/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "place_prz_zus", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlacePrzZus.findAll", query = "SELECT p FROM PlacePrzZus p"),
    @NamedQuery(name = "PlacePrzZus.findByPzzSerial", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzSerial = :pzzSerial"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDataOd", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDataOd = :pzzDataOd"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDataDo", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDataDo = :pzzDataDo"),
    @NamedQuery(name = "PlacePrzZus.findByPzzLiczba", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzLiczba = :pzzLiczba"),
    @NamedQuery(name = "PlacePrzZus.findByPzzKodCh1", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzKodCh1 = :pzzKodCh1"),
    @NamedQuery(name = "PlacePrzZus.findByPzzKodCh2", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzKodCh2 = :pzzKodCh2"),
    @NamedQuery(name = "PlacePrzZus.findByPzzKwota", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzKwota = :pzzKwota"),
    @NamedQuery(name = "PlacePrzZus.findByPzzPodDoch", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzPodDoch = :pzzPodDoch"),
    @NamedQuery(name = "PlacePrzZus.findByPzzZus", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzZus = :pzzZus"),
    @NamedQuery(name = "PlacePrzZus.findByPzzZdrowotne", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzZdrowotne = :pzzZdrowotne"),
    @NamedQuery(name = "PlacePrzZus.findByPzzChorWyp", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzChorWyp = :pzzChorWyp"),
    @NamedQuery(name = "PlacePrzZus.findByPzzWyp", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzWyp = :pzzWyp"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod1", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod1 = :pzzDod1"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod2", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod2 = :pzzDod2"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod3", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod3 = :pzzDod3"),
    @NamedQuery(name = "PlacePrzZus.findByPzzPodstawa", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzPodstawa = :pzzPodstawa"),
    @NamedQuery(name = "PlacePrzZus.findByPzzKwota1", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzKwota1 = :pzzKwota1"),
    @NamedQuery(name = "PlacePrzZus.findByPzzKwota2", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzKwota2 = :pzzKwota2"),
    @NamedQuery(name = "PlacePrzZus.findByPzzWspolcz", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzWspolcz = :pzzWspolcz"),
    @NamedQuery(name = "PlacePrzZus.findByPzzWkzZrodloFin", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzWkzZrodloFin = :pzzWkzZrodloFin"),
    @NamedQuery(name = "PlacePrzZus.findByPzzAbsencja", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzAbsencja = :pzzAbsencja"),
    @NamedQuery(name = "PlacePrzZus.findByPzzPdstZasChor", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzPdstZasChor = :pzzPdstZasChor"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod4", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod4 = :pzzDod4"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod5", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod5 = :pzzDod5"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod6", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod6 = :pzzDod6"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod7", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod7 = :pzzDod7"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod8", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod8 = :pzzDod8"),
    @NamedQuery(name = "PlacePrzZus.findByPzzNum1", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzNum1 = :pzzNum1"),
    @NamedQuery(name = "PlacePrzZus.findByPzzNum2", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzNum2 = :pzzNum2"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDate1", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDate1 = :pzzDate1"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDate2", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDate2 = :pzzDate2"),
    @NamedQuery(name = "PlacePrzZus.findByPzzTyp", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzTyp = :pzzTyp"),
    @NamedQuery(name = "PlacePrzZus.findByPzzVchar1", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzVchar1 = :pzzVchar1"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod9", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod9 = :pzzDod9"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod10", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod10 = :pzzDod10"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod11", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod11 = :pzzDod11"),
    @NamedQuery(name = "PlacePrzZus.findByPzzDod12", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzDod12 = :pzzDod12"),
    @NamedQuery(name = "PlacePrzZus.findByPzzVchar2", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzVchar2 = :pzzVchar2"),
    @NamedQuery(name = "PlacePrzZus.findByPzzOkrSredniej", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzOkrSredniej = :pzzOkrSredniej"),
    @NamedQuery(name = "PlacePrzZus.findByPzzInt1", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzInt1 = :pzzInt1"),
    @NamedQuery(name = "PlacePrzZus.findByPzzInt2", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzInt2 = :pzzInt2"),
    @NamedQuery(name = "PlacePrzZus.findByPzzInt3", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzInt3 = :pzzInt3"),
    @NamedQuery(name = "PlacePrzZus.findByPzzInt4", query = "SELECT p FROM PlacePrzZus p WHERE p.pzzInt4 = :pzzInt4")})
public class PlacePrzZus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_serial", nullable = false)
    private Integer pzzSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_data_od", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pzzDataOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_data_do", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pzzDataDo;
    @Column(name = "pzz_liczba")
    private Short pzzLiczba;
    @Size(max = 1)
    @Column(name = "pzz_kod_ch_1", length = 1)
    private String pzzKodCh1;
    @Size(max = 1)
    @Column(name = "pzz_kod_ch_2", length = 1)
    private String pzzKodCh2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal pzzKwota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_pod_doch", nullable = false)
    private Character pzzPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_zus", nullable = false)
    private Character pzzZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_zdrowotne", nullable = false)
    private Character pzzZdrowotne;
    @Column(name = "pzz_chor_wyp")
    private Character pzzChorWyp;
    @Column(name = "pzz_wyp")
    private Character pzzWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_dod_1", nullable = false)
    private Character pzzDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_dod_2", nullable = false)
    private Character pzzDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_dod_3", nullable = false)
    private Character pzzDod3;
    @Column(name = "pzz_podstawa", precision = 13, scale = 2)
    private BigDecimal pzzPodstawa;
    @Column(name = "pzz_kwota_1", precision = 13, scale = 2)
    private BigDecimal pzzKwota1;
    @Column(name = "pzz_kwota_2", precision = 13, scale = 2)
    private BigDecimal pzzKwota2;
    @Column(name = "pzz_wspolcz", precision = 5, scale = 2)
    private BigDecimal pzzWspolcz;
    @Column(name = "pzz_wkz_zrodlo_fin")
    private Character pzzWkzZrodloFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_absencja", nullable = false)
    private Character pzzAbsencja;
    @Column(name = "pzz_pdst_zas_chor")
    private Character pzzPdstZasChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_dod_4", nullable = false)
    private Character pzzDod4;
    @Column(name = "pzz_dod_5")
    private Character pzzDod5;
    @Column(name = "pzz_dod_6")
    private Character pzzDod6;
    @Column(name = "pzz_dod_7")
    private Character pzzDod7;
    @Column(name = "pzz_dod_8")
    private Character pzzDod8;
    @Column(name = "pzz_num_1", precision = 17, scale = 6)
    private BigDecimal pzzNum1;
    @Column(name = "pzz_num_2", precision = 17, scale = 6)
    private BigDecimal pzzNum2;
    @Column(name = "pzz_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pzzDate1;
    @Column(name = "pzz_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pzzDate2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pzz_typ", nullable = false)
    private Character pzzTyp;
    @Size(max = 64)
    @Column(name = "pzz_vchar_1", length = 64)
    private String pzzVchar1;
    @Column(name = "pzz_dod_9")
    private Character pzzDod9;
    @Column(name = "pzz_dod_10")
    private Character pzzDod10;
    @Column(name = "pzz_dod_11")
    private Character pzzDod11;
    @Column(name = "pzz_dod_12")
    private Character pzzDod12;
    @Size(max = 64)
    @Column(name = "pzz_vchar_2", length = 64)
    private String pzzVchar2;
    @Size(max = 64)
    @Column(name = "pzz_okr_sredniej", length = 64)
    private String pzzOkrSredniej;
    @Column(name = "pzz_int_1")
    private Integer pzzInt1;
    @Column(name = "pzz_int_2")
    private Integer pzzInt2;
    @Column(name = "pzz_int_3")
    private Integer pzzInt3;
    @Column(name = "pzz_int_4")
    private Integer pzzInt4;
    @JoinColumn(name = "pzz_lis_serial", referencedColumnName = "lis_serial")
    @ManyToOne
    private Listy pzzLisSerial;
    @JoinColumn(name = "pzz_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres pzzOkrSerial;
    @JoinColumn(name = "pzz_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba pzzOsoSerial;
    @JoinColumn(name = "pzz_lpl_serial", referencedColumnName = "lpl_serial")
    @ManyToOne
    private Place pzzLplSerial;
    @JoinColumn(name = "pzz_wkz_serial", referencedColumnName = "wkz_serial", nullable = false)
    @ManyToOne(optional = false)
    private WynKodPrzZus pzzWkzSerial;

    public PlacePrzZus() {
    }

    public PlacePrzZus(Integer pzzSerial) {
        this.pzzSerial = pzzSerial;
    }

    public PlacePrzZus(Integer pzzSerial, Date pzzDataOd, Date pzzDataDo, BigDecimal pzzKwota, Character pzzPodDoch, Character pzzZus, Character pzzZdrowotne, Character pzzDod1, Character pzzDod2, Character pzzDod3, Character pzzAbsencja, Character pzzDod4, Character pzzTyp) {
        this.pzzSerial = pzzSerial;
        this.pzzDataOd = pzzDataOd;
        this.pzzDataDo = pzzDataDo;
        this.pzzKwota = pzzKwota;
        this.pzzPodDoch = pzzPodDoch;
        this.pzzZus = pzzZus;
        this.pzzZdrowotne = pzzZdrowotne;
        this.pzzDod1 = pzzDod1;
        this.pzzDod2 = pzzDod2;
        this.pzzDod3 = pzzDod3;
        this.pzzAbsencja = pzzAbsencja;
        this.pzzDod4 = pzzDod4;
        this.pzzTyp = pzzTyp;
    }

    public Integer getPzzSerial() {
        return pzzSerial;
    }

    public void setPzzSerial(Integer pzzSerial) {
        this.pzzSerial = pzzSerial;
    }

    public Date getPzzDataOd() {
        return pzzDataOd;
    }

    public void setPzzDataOd(Date pzzDataOd) {
        this.pzzDataOd = pzzDataOd;
    }

    public Date getPzzDataDo() {
        return pzzDataDo;
    }

    public void setPzzDataDo(Date pzzDataDo) {
        this.pzzDataDo = pzzDataDo;
    }

    public Short getPzzLiczba() {
        return pzzLiczba;
    }

    public void setPzzLiczba(Short pzzLiczba) {
        this.pzzLiczba = pzzLiczba;
    }

    public String getPzzKodCh1() {
        return pzzKodCh1;
    }

    public void setPzzKodCh1(String pzzKodCh1) {
        this.pzzKodCh1 = pzzKodCh1;
    }

    public String getPzzKodCh2() {
        return pzzKodCh2;
    }

    public void setPzzKodCh2(String pzzKodCh2) {
        this.pzzKodCh2 = pzzKodCh2;
    }

    public BigDecimal getPzzKwota() {
        return pzzKwota;
    }

    public void setPzzKwota(BigDecimal pzzKwota) {
        this.pzzKwota = pzzKwota;
    }

    public Character getPzzPodDoch() {
        return pzzPodDoch;
    }

    public void setPzzPodDoch(Character pzzPodDoch) {
        this.pzzPodDoch = pzzPodDoch;
    }

    public Character getPzzZus() {
        return pzzZus;
    }

    public void setPzzZus(Character pzzZus) {
        this.pzzZus = pzzZus;
    }

    public Character getPzzZdrowotne() {
        return pzzZdrowotne;
    }

    public void setPzzZdrowotne(Character pzzZdrowotne) {
        this.pzzZdrowotne = pzzZdrowotne;
    }

    public Character getPzzChorWyp() {
        return pzzChorWyp;
    }

    public void setPzzChorWyp(Character pzzChorWyp) {
        this.pzzChorWyp = pzzChorWyp;
    }

    public Character getPzzWyp() {
        return pzzWyp;
    }

    public void setPzzWyp(Character pzzWyp) {
        this.pzzWyp = pzzWyp;
    }

    public Character getPzzDod1() {
        return pzzDod1;
    }

    public void setPzzDod1(Character pzzDod1) {
        this.pzzDod1 = pzzDod1;
    }

    public Character getPzzDod2() {
        return pzzDod2;
    }

    public void setPzzDod2(Character pzzDod2) {
        this.pzzDod2 = pzzDod2;
    }

    public Character getPzzDod3() {
        return pzzDod3;
    }

    public void setPzzDod3(Character pzzDod3) {
        this.pzzDod3 = pzzDod3;
    }

    public BigDecimal getPzzPodstawa() {
        return pzzPodstawa;
    }

    public void setPzzPodstawa(BigDecimal pzzPodstawa) {
        this.pzzPodstawa = pzzPodstawa;
    }

    public BigDecimal getPzzKwota1() {
        return pzzKwota1;
    }

    public void setPzzKwota1(BigDecimal pzzKwota1) {
        this.pzzKwota1 = pzzKwota1;
    }

    public BigDecimal getPzzKwota2() {
        return pzzKwota2;
    }

    public void setPzzKwota2(BigDecimal pzzKwota2) {
        this.pzzKwota2 = pzzKwota2;
    }

    public BigDecimal getPzzWspolcz() {
        return pzzWspolcz;
    }

    public void setPzzWspolcz(BigDecimal pzzWspolcz) {
        this.pzzWspolcz = pzzWspolcz;
    }

    public Character getPzzWkzZrodloFin() {
        return pzzWkzZrodloFin;
    }

    public void setPzzWkzZrodloFin(Character pzzWkzZrodloFin) {
        this.pzzWkzZrodloFin = pzzWkzZrodloFin;
    }

    public Character getPzzAbsencja() {
        return pzzAbsencja;
    }

    public void setPzzAbsencja(Character pzzAbsencja) {
        this.pzzAbsencja = pzzAbsencja;
    }

    public Character getPzzPdstZasChor() {
        return pzzPdstZasChor;
    }

    public void setPzzPdstZasChor(Character pzzPdstZasChor) {
        this.pzzPdstZasChor = pzzPdstZasChor;
    }

    public Character getPzzDod4() {
        return pzzDod4;
    }

    public void setPzzDod4(Character pzzDod4) {
        this.pzzDod4 = pzzDod4;
    }

    public Character getPzzDod5() {
        return pzzDod5;
    }

    public void setPzzDod5(Character pzzDod5) {
        this.pzzDod5 = pzzDod5;
    }

    public Character getPzzDod6() {
        return pzzDod6;
    }

    public void setPzzDod6(Character pzzDod6) {
        this.pzzDod6 = pzzDod6;
    }

    public Character getPzzDod7() {
        return pzzDod7;
    }

    public void setPzzDod7(Character pzzDod7) {
        this.pzzDod7 = pzzDod7;
    }

    public Character getPzzDod8() {
        return pzzDod8;
    }

    public void setPzzDod8(Character pzzDod8) {
        this.pzzDod8 = pzzDod8;
    }

    public BigDecimal getPzzNum1() {
        return pzzNum1;
    }

    public void setPzzNum1(BigDecimal pzzNum1) {
        this.pzzNum1 = pzzNum1;
    }

    public BigDecimal getPzzNum2() {
        return pzzNum2;
    }

    public void setPzzNum2(BigDecimal pzzNum2) {
        this.pzzNum2 = pzzNum2;
    }

    public Date getPzzDate1() {
        return pzzDate1;
    }

    public void setPzzDate1(Date pzzDate1) {
        this.pzzDate1 = pzzDate1;
    }

    public Date getPzzDate2() {
        return pzzDate2;
    }

    public void setPzzDate2(Date pzzDate2) {
        this.pzzDate2 = pzzDate2;
    }

    public Character getPzzTyp() {
        return pzzTyp;
    }

    public void setPzzTyp(Character pzzTyp) {
        this.pzzTyp = pzzTyp;
    }

    public String getPzzVchar1() {
        return pzzVchar1;
    }

    public void setPzzVchar1(String pzzVchar1) {
        this.pzzVchar1 = pzzVchar1;
    }

    public Character getPzzDod9() {
        return pzzDod9;
    }

    public void setPzzDod9(Character pzzDod9) {
        this.pzzDod9 = pzzDod9;
    }

    public Character getPzzDod10() {
        return pzzDod10;
    }

    public void setPzzDod10(Character pzzDod10) {
        this.pzzDod10 = pzzDod10;
    }

    public Character getPzzDod11() {
        return pzzDod11;
    }

    public void setPzzDod11(Character pzzDod11) {
        this.pzzDod11 = pzzDod11;
    }

    public Character getPzzDod12() {
        return pzzDod12;
    }

    public void setPzzDod12(Character pzzDod12) {
        this.pzzDod12 = pzzDod12;
    }

    public String getPzzVchar2() {
        return pzzVchar2;
    }

    public void setPzzVchar2(String pzzVchar2) {
        this.pzzVchar2 = pzzVchar2;
    }

    public String getPzzOkrSredniej() {
        return pzzOkrSredniej;
    }

    public void setPzzOkrSredniej(String pzzOkrSredniej) {
        this.pzzOkrSredniej = pzzOkrSredniej;
    }

    public Integer getPzzInt1() {
        return pzzInt1;
    }

    public void setPzzInt1(Integer pzzInt1) {
        this.pzzInt1 = pzzInt1;
    }

    public Integer getPzzInt2() {
        return pzzInt2;
    }

    public void setPzzInt2(Integer pzzInt2) {
        this.pzzInt2 = pzzInt2;
    }

    public Integer getPzzInt3() {
        return pzzInt3;
    }

    public void setPzzInt3(Integer pzzInt3) {
        this.pzzInt3 = pzzInt3;
    }

    public Integer getPzzInt4() {
        return pzzInt4;
    }

    public void setPzzInt4(Integer pzzInt4) {
        this.pzzInt4 = pzzInt4;
    }

    public Listy getPzzLisSerial() {
        return pzzLisSerial;
    }

    public void setPzzLisSerial(Listy pzzLisSerial) {
        this.pzzLisSerial = pzzLisSerial;
    }

    public Okres getPzzOkrSerial() {
        return pzzOkrSerial;
    }

    public void setPzzOkrSerial(Okres pzzOkrSerial) {
        this.pzzOkrSerial = pzzOkrSerial;
    }

    public Osoba getPzzOsoSerial() {
        return pzzOsoSerial;
    }

    public void setPzzOsoSerial(Osoba pzzOsoSerial) {
        this.pzzOsoSerial = pzzOsoSerial;
    }

    public Place getPzzLplSerial() {
        return pzzLplSerial;
    }

    public void setPzzLplSerial(Place pzzLplSerial) {
        this.pzzLplSerial = pzzLplSerial;
    }

    public WynKodPrzZus getPzzWkzSerial() {
        return pzzWkzSerial;
    }

    public void setPzzWkzSerial(WynKodPrzZus pzzWkzSerial) {
        this.pzzWkzSerial = pzzWkzSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pzzSerial != null ? pzzSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlacePrzZus)) {
            return false;
        }
        PlacePrzZus other = (PlacePrzZus) object;
        if ((this.pzzSerial == null && other.pzzSerial != null) || (this.pzzSerial != null && !this.pzzSerial.equals(other.pzzSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.PlacePrzZus[ pzzSerial=" + pzzSerial + " ]";
    }
    
}
