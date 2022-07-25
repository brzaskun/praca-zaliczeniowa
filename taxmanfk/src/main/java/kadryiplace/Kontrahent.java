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
@Table(name = "kontrahent", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kontrahent.findAll", query = "SELECT k FROM Kontrahent k"),
    @NamedQuery(name = "Kontrahent.findByKonSerial", query = "SELECT k FROM Kontrahent k WHERE k.konSerial = :konSerial"),
    @NamedQuery(name = "Kontrahent.findByKonNazwa", query = "SELECT k FROM Kontrahent k WHERE k.konNazwa = :konNazwa"),
    @NamedQuery(name = "Kontrahent.findByKonUlica", query = "SELECT k FROM Kontrahent k WHERE k.konUlica = :konUlica"),
    @NamedQuery(name = "Kontrahent.findByKonKod", query = "SELECT k FROM Kontrahent k WHERE k.konKod = :konKod"),
    @NamedQuery(name = "Kontrahent.findByKonNip", query = "SELECT k FROM Kontrahent k WHERE k.konNip = :konNip"),
    @NamedQuery(name = "Kontrahent.findByKonTel", query = "SELECT k FROM Kontrahent k WHERE k.konTel = :konTel"),
    @NamedQuery(name = "Kontrahent.findByKonPorzadek", query = "SELECT k FROM Kontrahent k WHERE k.konPorzadek = :konPorzadek"),
    @NamedQuery(name = "Kontrahent.findByKonRegon", query = "SELECT k FROM Kontrahent k WHERE k.konRegon = :konRegon"),
    @NamedQuery(name = "Kontrahent.findByKonNumerKonta", query = "SELECT k FROM Kontrahent k WHERE k.konNumerKonta = :konNumerKonta"),
    @NamedQuery(name = "Kontrahent.findByKonCzesty", query = "SELECT k FROM Kontrahent k WHERE k.konCzesty = :konCzesty"),
    @NamedQuery(name = "Kontrahent.findByKonNazwaSkr", query = "SELECT k FROM Kontrahent k WHERE k.konNazwaSkr = :konNazwaSkr"),
    @NamedQuery(name = "Kontrahent.findByKonDom", query = "SELECT k FROM Kontrahent k WHERE k.konDom = :konDom"),
    @NamedQuery(name = "Kontrahent.findByKonMieszkanie", query = "SELECT k FROM Kontrahent k WHERE k.konMieszkanie = :konMieszkanie"),
    @NamedQuery(name = "Kontrahent.findByKonEmail", query = "SELECT k FROM Kontrahent k WHERE k.konEmail = :konEmail"),
    @NamedQuery(name = "Kontrahent.findByKonCenaSpr", query = "SELECT k FROM Kontrahent k WHERE k.konCenaSpr = :konCenaSpr"),
    @NamedQuery(name = "Kontrahent.findByKonRabat", query = "SELECT k FROM Kontrahent k WHERE k.konRabat = :konRabat"),
    @NamedQuery(name = "Kontrahent.findByKonFirSerial", query = "SELECT k FROM Kontrahent k WHERE k.konFirSerial = :konFirSerial"),
    @NamedQuery(name = "Kontrahent.findByKonNrId", query = "SELECT k FROM Kontrahent k WHERE k.konNrId = :konNrId"),
    @NamedQuery(name = "Kontrahent.findByKonFaks", query = "SELECT k FROM Kontrahent k WHERE k.konFaks = :konFaks"),
    @NamedQuery(name = "Kontrahent.findByKonVchar1", query = "SELECT k FROM Kontrahent k WHERE k.konVchar1 = :konVchar1"),
    @NamedQuery(name = "Kontrahent.findByKonVchar2", query = "SELECT k FROM Kontrahent k WHERE k.konVchar2 = :konVchar2"),
    @NamedQuery(name = "Kontrahent.findByKonVchar3", query = "SELECT k FROM Kontrahent k WHERE k.konVchar3 = :konVchar3"),
    @NamedQuery(name = "Kontrahent.findByKonVchar4", query = "SELECT k FROM Kontrahent k WHERE k.konVchar4 = :konVchar4"),
    @NamedQuery(name = "Kontrahent.findByKonGmina", query = "SELECT k FROM Kontrahent k WHERE k.konGmina = :konGmina"),
    @NamedQuery(name = "Kontrahent.findByKonPowiat", query = "SELECT k FROM Kontrahent k WHERE k.konPowiat = :konPowiat"),
    @NamedQuery(name = "Kontrahent.findByKonPoczta", query = "SELECT k FROM Kontrahent k WHERE k.konPoczta = :konPoczta"),
    @NamedQuery(name = "Kontrahent.findByKonWojewodztwo", query = "SELECT k FROM Kontrahent k WHERE k.konWojewodztwo = :konWojewodztwo"),
    @NamedQuery(name = "Kontrahent.findByKonTyp", query = "SELECT k FROM Kontrahent k WHERE k.konTyp = :konTyp"),
    @NamedQuery(name = "Kontrahent.findByKonNazwisko", query = "SELECT k FROM Kontrahent k WHERE k.konNazwisko = :konNazwisko"),
    @NamedQuery(name = "Kontrahent.findByKonImie1", query = "SELECT k FROM Kontrahent k WHERE k.konImie1 = :konImie1"),
    @NamedQuery(name = "Kontrahent.findByKonImie2", query = "SELECT k FROM Kontrahent k WHERE k.konImie2 = :konImie2"),
    @NamedQuery(name = "Kontrahent.findByKonStanowisko", query = "SELECT k FROM Kontrahent k WHERE k.konStanowisko = :konStanowisko"),
    @NamedQuery(name = "Kontrahent.findByKonPlec", query = "SELECT k FROM Kontrahent k WHERE k.konPlec = :konPlec"),
    @NamedQuery(name = "Kontrahent.findByKonDataWpro", query = "SELECT k FROM Kontrahent k WHERE k.konDataWpro = :konDataWpro"),
    @NamedQuery(name = "Kontrahent.findByKonDataZmia", query = "SELECT k FROM Kontrahent k WHERE k.konDataZmia = :konDataZmia"),
    @NamedQuery(name = "Kontrahent.findByKonDataUrodz", query = "SELECT k FROM Kontrahent k WHERE k.konDataUrodz = :konDataUrodz"),
    @NamedQuery(name = "Kontrahent.findByKonNum1", query = "SELECT k FROM Kontrahent k WHERE k.konNum1 = :konNum1"),
    @NamedQuery(name = "Kontrahent.findByKonNum2", query = "SELECT k FROM Kontrahent k WHERE k.konNum2 = :konNum2"),
    @NamedQuery(name = "Kontrahent.findByKonInt1", query = "SELECT k FROM Kontrahent k WHERE k.konInt1 = :konInt1"),
    @NamedQuery(name = "Kontrahent.findByKonInt2", query = "SELECT k FROM Kontrahent k WHERE k.konInt2 = :konInt2"),
    @NamedQuery(name = "Kontrahent.findByKonInt3", query = "SELECT k FROM Kontrahent k WHERE k.konInt3 = :konInt3"),
    @NamedQuery(name = "Kontrahent.findByKonInt4", query = "SELECT k FROM Kontrahent k WHERE k.konInt4 = :konInt4"),
    @NamedQuery(name = "Kontrahent.findByKonChar1", query = "SELECT k FROM Kontrahent k WHERE k.konChar1 = :konChar1"),
    @NamedQuery(name = "Kontrahent.findByKonChar2", query = "SELECT k FROM Kontrahent k WHERE k.konChar2 = :konChar2"),
    @NamedQuery(name = "Kontrahent.findByKonChar3", query = "SELECT k FROM Kontrahent k WHERE k.konChar3 = :konChar3"),
    @NamedQuery(name = "Kontrahent.findByKonChar4", query = "SELECT k FROM Kontrahent k WHERE k.konChar4 = :konChar4"),
    @NamedQuery(name = "Kontrahent.findByKonDate1", query = "SELECT k FROM Kontrahent k WHERE k.konDate1 = :konDate1"),
    @NamedQuery(name = "Kontrahent.findByKonDate2", query = "SELECT k FROM Kontrahent k WHERE k.konDate2 = :konDate2"),
    @NamedQuery(name = "Kontrahent.findByKonPesel", query = "SELECT k FROM Kontrahent k WHERE k.konPesel = :konPesel"),
    @NamedQuery(name = "Kontrahent.findByKonDokRodzaj", query = "SELECT k FROM Kontrahent k WHERE k.konDokRodzaj = :konDokRodzaj"),
    @NamedQuery(name = "Kontrahent.findByKonDokNumer", query = "SELECT k FROM Kontrahent k WHERE k.konDokNumer = :konDokNumer"),
    @NamedQuery(name = "Kontrahent.findByKonDokData", query = "SELECT k FROM Kontrahent k WHERE k.konDokData = :konDokData"),
    @NamedQuery(name = "Kontrahent.findByKonDokOrgan", query = "SELECT k FROM Kontrahent k WHERE k.konDokOrgan = :konDokOrgan")})
public class Kontrahent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "kon_serial", nullable = false)
    private Integer konSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "kon_nazwa", nullable = false, length = 64)
    private String konNazwa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "kon_ulica", nullable = false, length = 64)
    private String konUlica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "kon_kod", nullable = false, length = 5)
    private String konKod;
    @Size(max = 16)
    @Column(name = "kon_nip", length = 16)
    private String konNip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "kon_tel", nullable = false, length = 32)
    private String konTel;
    @Column(name = "kon_porzadek")
    private Short konPorzadek;
    @Size(max = 16)
    @Column(name = "kon_regon", length = 16)
    private String konRegon;
    @Size(max = 64)
    @Column(name = "kon_numer_konta", length = 64)
    private String konNumerKonta;
    @Column(name = "kon_czesty")
    private Character konCzesty;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 31)
    @Column(name = "kon_nazwa_skr", nullable = false, length = 31)
    private String konNazwaSkr;
    @Size(max = 10)
    @Column(name = "kon_dom", length = 10)
    private String konDom;
    @Size(max = 10)
    @Column(name = "kon_mieszkanie", length = 10)
    private String konMieszkanie;
    @Size(max = 96)
    @Column(name = "kon_email", length = 96)
    private String konEmail;
    @Column(name = "kon_cena_spr")
    private Character konCenaSpr;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kon_rabat", precision = 5, scale = 2)
    private BigDecimal konRabat;
    @Column(name = "kon_fir_serial")
    private Integer konFirSerial;
    @Column(name = "kon_nr_id")
    private Integer konNrId;
    @Size(max = 16)
    @Column(name = "kon_faks", length = 16)
    private String konFaks;
    @Size(max = 64)
    @Column(name = "kon_vchar_1", length = 64)
    private String konVchar1;
    @Size(max = 64)
    @Column(name = "kon_vchar_2", length = 64)
    private String konVchar2;
    @Size(max = 64)
    @Column(name = "kon_vchar_3", length = 64)
    private String konVchar3;
    @Size(max = 64)
    @Column(name = "kon_vchar_4", length = 64)
    private String konVchar4;
    @Size(max = 26)
    @Column(name = "kon_gmina", length = 26)
    private String konGmina;
    @Size(max = 26)
    @Column(name = "kon_powiat", length = 26)
    private String konPowiat;
    @Size(max = 26)
    @Column(name = "kon_poczta", length = 26)
    private String konPoczta;
    @Size(max = 26)
    @Column(name = "kon_wojewodztwo", length = 26)
    private String konWojewodztwo;
    @Column(name = "kon_typ")
    private Character konTyp;
    @Size(max = 31)
    @Column(name = "kon_nazwisko", length = 31)
    private String konNazwisko;
    @Size(max = 22)
    @Column(name = "kon_imie1", length = 22)
    private String konImie1;
    @Size(max = 22)
    @Column(name = "kon_imie2", length = 22)
    private String konImie2;
    @Size(max = 64)
    @Column(name = "kon_stanowisko", length = 64)
    private String konStanowisko;
    @Column(name = "kon_plec")
    private Character konPlec;
    @Column(name = "kon_data_wpro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date konDataWpro;
    @Column(name = "kon_data_zmia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date konDataZmia;
    @Column(name = "kon_data_urodz")
    @Temporal(TemporalType.TIMESTAMP)
    private Date konDataUrodz;
    @Column(name = "kon_num_1", precision = 16, scale = 7)
    private BigDecimal konNum1;
    @Column(name = "kon_num_2", precision = 16, scale = 7)
    private BigDecimal konNum2;
    @Column(name = "kon_int_1")
    private Integer konInt1;
    @Column(name = "kon_int_2")
    private Integer konInt2;
    @Column(name = "kon_int_3")
    private Integer konInt3;
    @Column(name = "kon_int_4")
    private Integer konInt4;
    @Column(name = "kon_char_1")
    private Character konChar1;
    @Column(name = "kon_char_2")
    private Character konChar2;
    @Column(name = "kon_char_3")
    private Character konChar3;
    @Column(name = "kon_char_4")
    private Character konChar4;
    @Column(name = "kon_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date konDate1;
    @Column(name = "kon_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date konDate2;
    @Size(max = 16)
    @Column(name = "kon_pesel", length = 16)
    private String konPesel;
    @Column(name = "kon_dok_rodzaj")
    private Character konDokRodzaj;
    @Size(max = 16)
    @Column(name = "kon_dok_numer", length = 16)
    private String konDokNumer;
    @Column(name = "kon_dok_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date konDokData;
    @Size(max = 64)
    @Column(name = "kon_dok_organ", length = 64)
    private String konDokOrgan;
    @OneToMany(mappedBy = "dliKonSerial")
    private List<DaneLiDa> daneLiDaList;
    @OneToMany(mappedBy = "ksiKonSerial")
    private List<Ksiegapir> ksiegapirList;
    @OneToMany(mappedBy = "zakKonSerial")
    private List<Zakupy> zakupyList;
    @OneToMany(mappedBy = "zdoKonSerial")
    private List<Zakdok> zakdokList;
    @OneToMany(mappedBy = "pakKonSerial")
    private List<ParagonK> paragonKList;
    @OneToMany(mappedBy = "dsoKonSerial")
    private List<DaneStatO> daneStatOList;
    @OneToMany(mappedBy = "dhiKonSerial")
    private List<DaneHiDa> daneHiDaList;
    @JoinColumn(name = "kon_ban_serial", referencedColumnName = "ban_serial")
    @ManyToOne
    private Bank konBanSerial;
    @JoinColumn(name = "kon_for_serial", referencedColumnName = "for_serial")
    @ManyToOne
    private Formyzap konForSerial;
    @JoinColumn(name = "kon_tytul_zaw", referencedColumnName = "ksl_serial")
    @ManyToOne
    private KonSlow konTytulZaw;
    @JoinColumn(name = "kon_typ_kontakt", referencedColumnName = "ksl_serial")
    @ManyToOne
    private KonSlow konTypKontakt;
    @JoinColumn(name = "kon_zrodlo", referencedColumnName = "ksl_serial")
    @ManyToOne
    private KonSlow konZrodlo;
    @JoinColumn(name = "kon_zwrot_pocz", referencedColumnName = "ksl_serial")
    @ManyToOne
    private KonSlow konZwrotPocz;
    @JoinColumn(name = "kon_branza", referencedColumnName = "ksl_serial")
    @ManyToOne
    private KonSlow konBranza;
    @JoinColumn(name = "kon_kgr_serial", referencedColumnName = "kgr_serial")
    @ManyToOne
    private Kongrupa konKgrSerial;
    @JoinColumn(name = "kon_mia_serial", referencedColumnName = "mia_serial")
    @ManyToOne
    private Miasto konMiaSerial;
    @JoinColumn(name = "kon_pan_serial", referencedColumnName = "pan_serial")
    @ManyToOne
    private Panstwo konPanSerial;
    @OneToMany(mappedBy = "mdoKonSerial")
    private List<Magdok> magdokList;
    @OneToMany(mappedBy = "sprKonSerial")
    private List<Sprzedaz> sprzedazList;
    @OneToMany(mappedBy = "fakKonSerial")
    private List<Fakrach> fakrachList;
    @OneToMany(mappedBy = "dasKonSerial")
    private List<DaneStDa> daneStDaList;
    @OneToMany(mappedBy = "adhKonSerial")
    private List<AdresHist> adresHistList;

    public Kontrahent() {
    }

    public Kontrahent(Integer konSerial) {
        this.konSerial = konSerial;
    }

    public Kontrahent(Integer konSerial, String konNazwa, String konUlica, String konKod, String konTel, String konNazwaSkr) {
        this.konSerial = konSerial;
        this.konNazwa = konNazwa;
        this.konUlica = konUlica;
        this.konKod = konKod;
        this.konTel = konTel;
        this.konNazwaSkr = konNazwaSkr;
    }

    public Integer getKonSerial() {
        return konSerial;
    }

    public void setKonSerial(Integer konSerial) {
        this.konSerial = konSerial;
    }

    public String getKonNazwa() {
        return konNazwa;
    }

    public void setKonNazwa(String konNazwa) {
        this.konNazwa = konNazwa;
    }

    public String getKonUlica() {
        return konUlica;
    }

    public void setKonUlica(String konUlica) {
        this.konUlica = konUlica;
    }

    public String getKonKod() {
        return konKod;
    }

    public void setKonKod(String konKod) {
        this.konKod = konKod;
    }

    public String getKonNip() {
        return konNip;
    }

    public void setKonNip(String konNip) {
        this.konNip = konNip;
    }

    public String getKonTel() {
        return konTel;
    }

    public void setKonTel(String konTel) {
        this.konTel = konTel;
    }

    public Short getKonPorzadek() {
        return konPorzadek;
    }

    public void setKonPorzadek(Short konPorzadek) {
        this.konPorzadek = konPorzadek;
    }

    public String getKonRegon() {
        return konRegon;
    }

    public void setKonRegon(String konRegon) {
        this.konRegon = konRegon;
    }

    public String getKonNumerKonta() {
        return konNumerKonta;
    }

    public void setKonNumerKonta(String konNumerKonta) {
        this.konNumerKonta = konNumerKonta;
    }

    public Character getKonCzesty() {
        return konCzesty;
    }

    public void setKonCzesty(Character konCzesty) {
        this.konCzesty = konCzesty;
    }

    public String getKonNazwaSkr() {
        return konNazwaSkr;
    }

    public void setKonNazwaSkr(String konNazwaSkr) {
        this.konNazwaSkr = konNazwaSkr;
    }

    public String getKonDom() {
        return konDom;
    }

    public void setKonDom(String konDom) {
        this.konDom = konDom;
    }

    public String getKonMieszkanie() {
        return konMieszkanie;
    }

    public void setKonMieszkanie(String konMieszkanie) {
        this.konMieszkanie = konMieszkanie;
    }

    public String getKonEmail() {
        return konEmail;
    }

    public void setKonEmail(String konEmail) {
        this.konEmail = konEmail;
    }

    public Character getKonCenaSpr() {
        return konCenaSpr;
    }

    public void setKonCenaSpr(Character konCenaSpr) {
        this.konCenaSpr = konCenaSpr;
    }

    public BigDecimal getKonRabat() {
        return konRabat;
    }

    public void setKonRabat(BigDecimal konRabat) {
        this.konRabat = konRabat;
    }

    public Integer getKonFirSerial() {
        return konFirSerial;
    }

    public void setKonFirSerial(Integer konFirSerial) {
        this.konFirSerial = konFirSerial;
    }

    public Integer getKonNrId() {
        return konNrId;
    }

    public void setKonNrId(Integer konNrId) {
        this.konNrId = konNrId;
    }

    public String getKonFaks() {
        return konFaks;
    }

    public void setKonFaks(String konFaks) {
        this.konFaks = konFaks;
    }

    public String getKonVchar1() {
        return konVchar1;
    }

    public void setKonVchar1(String konVchar1) {
        this.konVchar1 = konVchar1;
    }

    public String getKonVchar2() {
        return konVchar2;
    }

    public void setKonVchar2(String konVchar2) {
        this.konVchar2 = konVchar2;
    }

    public String getKonVchar3() {
        return konVchar3;
    }

    public void setKonVchar3(String konVchar3) {
        this.konVchar3 = konVchar3;
    }

    public String getKonVchar4() {
        return konVchar4;
    }

    public void setKonVchar4(String konVchar4) {
        this.konVchar4 = konVchar4;
    }

    public String getKonGmina() {
        return konGmina;
    }

    public void setKonGmina(String konGmina) {
        this.konGmina = konGmina;
    }

    public String getKonPowiat() {
        return konPowiat;
    }

    public void setKonPowiat(String konPowiat) {
        this.konPowiat = konPowiat;
    }

    public String getKonPoczta() {
        return konPoczta;
    }

    public void setKonPoczta(String konPoczta) {
        this.konPoczta = konPoczta;
    }

    public String getKonWojewodztwo() {
        return konWojewodztwo;
    }

    public void setKonWojewodztwo(String konWojewodztwo) {
        this.konWojewodztwo = konWojewodztwo;
    }

    public Character getKonTyp() {
        return konTyp;
    }

    public void setKonTyp(Character konTyp) {
        this.konTyp = konTyp;
    }

    public String getKonNazwisko() {
        return konNazwisko;
    }

    public void setKonNazwisko(String konNazwisko) {
        this.konNazwisko = konNazwisko;
    }

    public String getKonImie1() {
        return konImie1;
    }

    public void setKonImie1(String konImie1) {
        this.konImie1 = konImie1;
    }

    public String getKonImie2() {
        return konImie2;
    }

    public void setKonImie2(String konImie2) {
        this.konImie2 = konImie2;
    }

    public String getKonStanowisko() {
        return konStanowisko;
    }

    public void setKonStanowisko(String konStanowisko) {
        this.konStanowisko = konStanowisko;
    }

    public Character getKonPlec() {
        return konPlec;
    }

    public void setKonPlec(Character konPlec) {
        this.konPlec = konPlec;
    }

    public Date getKonDataWpro() {
        return konDataWpro;
    }

    public void setKonDataWpro(Date konDataWpro) {
        this.konDataWpro = konDataWpro;
    }

    public Date getKonDataZmia() {
        return konDataZmia;
    }

    public void setKonDataZmia(Date konDataZmia) {
        this.konDataZmia = konDataZmia;
    }

    public Date getKonDataUrodz() {
        return konDataUrodz;
    }

    public void setKonDataUrodz(Date konDataUrodz) {
        this.konDataUrodz = konDataUrodz;
    }

    public BigDecimal getKonNum1() {
        return konNum1;
    }

    public void setKonNum1(BigDecimal konNum1) {
        this.konNum1 = konNum1;
    }

    public BigDecimal getKonNum2() {
        return konNum2;
    }

    public void setKonNum2(BigDecimal konNum2) {
        this.konNum2 = konNum2;
    }

    public Integer getKonInt1() {
        return konInt1;
    }

    public void setKonInt1(Integer konInt1) {
        this.konInt1 = konInt1;
    }

    public Integer getKonInt2() {
        return konInt2;
    }

    public void setKonInt2(Integer konInt2) {
        this.konInt2 = konInt2;
    }

    public Integer getKonInt3() {
        return konInt3;
    }

    public void setKonInt3(Integer konInt3) {
        this.konInt3 = konInt3;
    }

    public Integer getKonInt4() {
        return konInt4;
    }

    public void setKonInt4(Integer konInt4) {
        this.konInt4 = konInt4;
    }

    public Character getKonChar1() {
        return konChar1;
    }

    public void setKonChar1(Character konChar1) {
        this.konChar1 = konChar1;
    }

    public Character getKonChar2() {
        return konChar2;
    }

    public void setKonChar2(Character konChar2) {
        this.konChar2 = konChar2;
    }

    public Character getKonChar3() {
        return konChar3;
    }

    public void setKonChar3(Character konChar3) {
        this.konChar3 = konChar3;
    }

    public Character getKonChar4() {
        return konChar4;
    }

    public void setKonChar4(Character konChar4) {
        this.konChar4 = konChar4;
    }

    public Date getKonDate1() {
        return konDate1;
    }

    public void setKonDate1(Date konDate1) {
        this.konDate1 = konDate1;
    }

    public Date getKonDate2() {
        return konDate2;
    }

    public void setKonDate2(Date konDate2) {
        this.konDate2 = konDate2;
    }

    public String getKonPesel() {
        return konPesel;
    }

    public void setKonPesel(String konPesel) {
        this.konPesel = konPesel;
    }

    public Character getKonDokRodzaj() {
        return konDokRodzaj;
    }

    public void setKonDokRodzaj(Character konDokRodzaj) {
        this.konDokRodzaj = konDokRodzaj;
    }

    public String getKonDokNumer() {
        return konDokNumer;
    }

    public void setKonDokNumer(String konDokNumer) {
        this.konDokNumer = konDokNumer;
    }

    public Date getKonDokData() {
        return konDokData;
    }

    public void setKonDokData(Date konDokData) {
        this.konDokData = konDokData;
    }

    public String getKonDokOrgan() {
        return konDokOrgan;
    }

    public void setKonDokOrgan(String konDokOrgan) {
        this.konDokOrgan = konDokOrgan;
    }

    @XmlTransient
    public List<DaneLiDa> getDaneLiDaList() {
        return daneLiDaList;
    }

    public void setDaneLiDaList(List<DaneLiDa> daneLiDaList) {
        this.daneLiDaList = daneLiDaList;
    }

    @XmlTransient
    public List<Ksiegapir> getKsiegapirList() {
        return ksiegapirList;
    }

    public void setKsiegapirList(List<Ksiegapir> ksiegapirList) {
        this.ksiegapirList = ksiegapirList;
    }

    @XmlTransient
    public List<Zakupy> getZakupyList() {
        return zakupyList;
    }

    public void setZakupyList(List<Zakupy> zakupyList) {
        this.zakupyList = zakupyList;
    }

    @XmlTransient
    public List<Zakdok> getZakdokList() {
        return zakdokList;
    }

    public void setZakdokList(List<Zakdok> zakdokList) {
        this.zakdokList = zakdokList;
    }

    @XmlTransient
    public List<ParagonK> getParagonKList() {
        return paragonKList;
    }

    public void setParagonKList(List<ParagonK> paragonKList) {
        this.paragonKList = paragonKList;
    }

    @XmlTransient
    public List<DaneStatO> getDaneStatOList() {
        return daneStatOList;
    }

    public void setDaneStatOList(List<DaneStatO> daneStatOList) {
        this.daneStatOList = daneStatOList;
    }

    @XmlTransient
    public List<DaneHiDa> getDaneHiDaList() {
        return daneHiDaList;
    }

    public void setDaneHiDaList(List<DaneHiDa> daneHiDaList) {
        this.daneHiDaList = daneHiDaList;
    }

    public Bank getKonBanSerial() {
        return konBanSerial;
    }

    public void setKonBanSerial(Bank konBanSerial) {
        this.konBanSerial = konBanSerial;
    }

    public Formyzap getKonForSerial() {
        return konForSerial;
    }

    public void setKonForSerial(Formyzap konForSerial) {
        this.konForSerial = konForSerial;
    }

    public KonSlow getKonTytulZaw() {
        return konTytulZaw;
    }

    public void setKonTytulZaw(KonSlow konTytulZaw) {
        this.konTytulZaw = konTytulZaw;
    }

    public KonSlow getKonTypKontakt() {
        return konTypKontakt;
    }

    public void setKonTypKontakt(KonSlow konTypKontakt) {
        this.konTypKontakt = konTypKontakt;
    }

    public KonSlow getKonZrodlo() {
        return konZrodlo;
    }

    public void setKonZrodlo(KonSlow konZrodlo) {
        this.konZrodlo = konZrodlo;
    }

    public KonSlow getKonZwrotPocz() {
        return konZwrotPocz;
    }

    public void setKonZwrotPocz(KonSlow konZwrotPocz) {
        this.konZwrotPocz = konZwrotPocz;
    }

    public KonSlow getKonBranza() {
        return konBranza;
    }

    public void setKonBranza(KonSlow konBranza) {
        this.konBranza = konBranza;
    }

    public Kongrupa getKonKgrSerial() {
        return konKgrSerial;
    }

    public void setKonKgrSerial(Kongrupa konKgrSerial) {
        this.konKgrSerial = konKgrSerial;
    }

    public Miasto getKonMiaSerial() {
        return konMiaSerial;
    }

    public void setKonMiaSerial(Miasto konMiaSerial) {
        this.konMiaSerial = konMiaSerial;
    }

    public Panstwo getKonPanSerial() {
        return konPanSerial;
    }

    public void setKonPanSerial(Panstwo konPanSerial) {
        this.konPanSerial = konPanSerial;
    }

    @XmlTransient
    public List<Magdok> getMagdokList() {
        return magdokList;
    }

    public void setMagdokList(List<Magdok> magdokList) {
        this.magdokList = magdokList;
    }

    @XmlTransient
    public List<Sprzedaz> getSprzedazList() {
        return sprzedazList;
    }

    public void setSprzedazList(List<Sprzedaz> sprzedazList) {
        this.sprzedazList = sprzedazList;
    }

    @XmlTransient
    public List<Fakrach> getFakrachList() {
        return fakrachList;
    }

    public void setFakrachList(List<Fakrach> fakrachList) {
        this.fakrachList = fakrachList;
    }

    @XmlTransient
    public List<DaneStDa> getDaneStDaList() {
        return daneStDaList;
    }

    public void setDaneStDaList(List<DaneStDa> daneStDaList) {
        this.daneStDaList = daneStDaList;
    }

    @XmlTransient
    public List<AdresHist> getAdresHistList() {
        return adresHistList;
    }

    public void setAdresHistList(List<AdresHist> adresHistList) {
        this.adresHistList = adresHistList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (konSerial != null ? konSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kontrahent)) {
            return false;
        }
        Kontrahent other = (Kontrahent) object;
        if ((this.konSerial == null && other.konSerial != null) || (this.konSerial != null && !this.konSerial.equals(other.konSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Kontrahent[ konSerial=" + konSerial + " ]";
    }
    
}
