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
@Table(name = "okres", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Okres.findAll", query = "SELECT o FROM Okres o"),
    @NamedQuery(name = "Okres.findByOkrSerial", query = "SELECT o FROM Okres o WHERE o.okrSerial = :okrSerial"),
    @NamedQuery(name = "Okres.findByOkrDataOd", query = "SELECT o FROM Okres o WHERE o.okrDataOd = :okrDataOd"),
    @NamedQuery(name = "Okres.findByOkrDataDo", query = "SELECT o FROM Okres o WHERE o.okrDataDo = :okrDataDo"),
    @NamedQuery(name = "Okres.findByOkrOpis", query = "SELECT o FROM Okres o WHERE o.okrOpis = :okrOpis"),
    @NamedQuery(name = "Okres.findByOkrMieNumer", query = "SELECT o FROM Okres o WHERE o.okrMieNumer = :okrMieNumer"),
    @NamedQuery(name = "Okres.findByOkrZamkniety", query = "SELECT o FROM Okres o WHERE o.okrZamkniety = :okrZamkniety"),
    @NamedQuery(name = "Okres.findByOkrKryteria", query = "SELECT o FROM Okres o WHERE o.okrKryteria = :okrKryteria"),
    @NamedQuery(name = "Okres.findByOkrDniPracy", query = "SELECT o FROM Okres o WHERE o.okrDniPracy = :okrDniPracy"),
    @NamedQuery(name = "Okres.findByOkr1", query = "SELECT o FROM Okres o WHERE o.okr1 = :okr1"),
    @NamedQuery(name = "Okres.findByOkr2", query = "SELECT o FROM Okres o WHERE o.okr2 = :okr2"),
    @NamedQuery(name = "Okres.findByOkr3", query = "SELECT o FROM Okres o WHERE o.okr3 = :okr3"),
    @NamedQuery(name = "Okres.findByOkr4", query = "SELECT o FROM Okres o WHERE o.okr4 = :okr4"),
    @NamedQuery(name = "Okres.findByOkr5", query = "SELECT o FROM Okres o WHERE o.okr5 = :okr5"),
    @NamedQuery(name = "Okres.findByOkr6", query = "SELECT o FROM Okres o WHERE o.okr6 = :okr6"),
    @NamedQuery(name = "Okres.findByOkr7", query = "SELECT o FROM Okres o WHERE o.okr7 = :okr7"),
    @NamedQuery(name = "Okres.findByOkr8", query = "SELECT o FROM Okres o WHERE o.okr8 = :okr8"),
    @NamedQuery(name = "Okres.findByOkr9", query = "SELECT o FROM Okres o WHERE o.okr9 = :okr9"),
    @NamedQuery(name = "Okres.findByOkr10", query = "SELECT o FROM Okres o WHERE o.okr10 = :okr10"),
    @NamedQuery(name = "Okres.findByOkr11", query = "SELECT o FROM Okres o WHERE o.okr11 = :okr11"),
    @NamedQuery(name = "Okres.findByOkr12", query = "SELECT o FROM Okres o WHERE o.okr12 = :okr12"),
    @NamedQuery(name = "Okres.findByOkr13", query = "SELECT o FROM Okres o WHERE o.okr13 = :okr13"),
    @NamedQuery(name = "Okres.findByOkr14", query = "SELECT o FROM Okres o WHERE o.okr14 = :okr14"),
    @NamedQuery(name = "Okres.findByOkr15", query = "SELECT o FROM Okres o WHERE o.okr15 = :okr15"),
    @NamedQuery(name = "Okres.findByOkr16", query = "SELECT o FROM Okres o WHERE o.okr16 = :okr16"),
    @NamedQuery(name = "Okres.findByOkr17", query = "SELECT o FROM Okres o WHERE o.okr17 = :okr17"),
    @NamedQuery(name = "Okres.findByOkr18", query = "SELECT o FROM Okres o WHERE o.okr18 = :okr18"),
    @NamedQuery(name = "Okres.findByOkr19", query = "SELECT o FROM Okres o WHERE o.okr19 = :okr19"),
    @NamedQuery(name = "Okres.findByOkr20", query = "SELECT o FROM Okres o WHERE o.okr20 = :okr20"),
    @NamedQuery(name = "Okres.findByOkr21", query = "SELECT o FROM Okres o WHERE o.okr21 = :okr21"),
    @NamedQuery(name = "Okres.findByOkr22", query = "SELECT o FROM Okres o WHERE o.okr22 = :okr22"),
    @NamedQuery(name = "Okres.findByOkr23", query = "SELECT o FROM Okres o WHERE o.okr23 = :okr23"),
    @NamedQuery(name = "Okres.findByOkr24", query = "SELECT o FROM Okres o WHERE o.okr24 = :okr24"),
    @NamedQuery(name = "Okres.findByOkr25", query = "SELECT o FROM Okres o WHERE o.okr25 = :okr25"),
    @NamedQuery(name = "Okres.findByOkr26", query = "SELECT o FROM Okres o WHERE o.okr26 = :okr26"),
    @NamedQuery(name = "Okres.findByOkr27", query = "SELECT o FROM Okres o WHERE o.okr27 = :okr27"),
    @NamedQuery(name = "Okres.findByOkr28", query = "SELECT o FROM Okres o WHERE o.okr28 = :okr28"),
    @NamedQuery(name = "Okres.findByOkr29", query = "SELECT o FROM Okres o WHERE o.okr29 = :okr29"),
    @NamedQuery(name = "Okres.findByOkr30", query = "SELECT o FROM Okres o WHERE o.okr30 = :okr30"),
    @NamedQuery(name = "Okres.findByOkr31", query = "SELECT o FROM Okres o WHERE o.okr31 = :okr31"),
    @NamedQuery(name = "Okres.findByOkrChar1", query = "SELECT o FROM Okres o WHERE o.okrChar1 = :okrChar1"),
    @NamedQuery(name = "Okres.findByOkrNum1", query = "SELECT o FROM Okres o WHERE o.okrNum1 = :okrNum1"),
    @NamedQuery(name = "Okres.findByOkrChar2", query = "SELECT o FROM Okres o WHERE o.okrChar2 = :okrChar2"),
    @NamedQuery(name = "Okres.findByOkrChar3", query = "SELECT o FROM Okres o WHERE o.okrChar3 = :okrChar3"),
    @NamedQuery(name = "Okres.findByOkrChar4", query = "SELECT o FROM Okres o WHERE o.okrChar4 = :okrChar4"),
    @NamedQuery(name = "Okres.findByOkrNum2", query = "SELECT o FROM Okres o WHERE o.okrNum2 = :okrNum2"),
    @NamedQuery(name = "Okres.findByOkrVchar1", query = "SELECT o FROM Okres o WHERE o.okrVchar1 = :okrVchar1")})
public class Okres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "okr_serial", nullable = false)
    private Integer okrSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "okr_data_od", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date okrDataOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "okr_data_do", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date okrDataDo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "okr_opis", nullable = false, length = 64)
    private String okrOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "okr_mie_numer", nullable = false)
    private short okrMieNumer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "okr_zamkniety", nullable = false)
    private Character okrZamkniety;
    @Column(name = "okr_kryteria")
    private Character okrKryteria;
    @Column(name = "okr_dni_pracy")
    private Short okrDniPracy;
    @Column(name = "okr_1")
    private Character okr1;
    @Column(name = "okr_2")
    private Character okr2;
    @Column(name = "okr_3")
    private Character okr3;
    @Column(name = "okr_4")
    private Character okr4;
    @Column(name = "okr_5")
    private Character okr5;
    @Column(name = "okr_6")
    private Character okr6;
    @Column(name = "okr_7")
    private Character okr7;
    @Column(name = "okr_8")
    private Character okr8;
    @Column(name = "okr_9")
    private Character okr9;
    @Column(name = "okr_10")
    private Character okr10;
    @Column(name = "okr_11")
    private Character okr11;
    @Column(name = "okr_12")
    private Character okr12;
    @Column(name = "okr_13")
    private Character okr13;
    @Column(name = "okr_14")
    private Character okr14;
    @Column(name = "okr_15")
    private Character okr15;
    @Column(name = "okr_16")
    private Character okr16;
    @Column(name = "okr_17")
    private Character okr17;
    @Column(name = "okr_18")
    private Character okr18;
    @Column(name = "okr_19")
    private Character okr19;
    @Column(name = "okr_20")
    private Character okr20;
    @Column(name = "okr_21")
    private Character okr21;
    @Column(name = "okr_22")
    private Character okr22;
    @Column(name = "okr_23")
    private Character okr23;
    @Column(name = "okr_24")
    private Character okr24;
    @Column(name = "okr_25")
    private Character okr25;
    @Column(name = "okr_26")
    private Character okr26;
    @Column(name = "okr_27")
    private Character okr27;
    @Column(name = "okr_28")
    private Character okr28;
    @Column(name = "okr_29")
    private Character okr29;
    @Column(name = "okr_30")
    private Character okr30;
    @Column(name = "okr_31")
    private Character okr31;
    @Column(name = "okr_char_1")
    private Character okrChar1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "okr_num_1", precision = 17, scale = 6)
    private BigDecimal okrNum1;
    @Column(name = "okr_char_2")
    private Character okrChar2;
    @Column(name = "okr_char_3")
    private Character okrChar3;
    @Column(name = "okr_char_4")
    private Character okrChar4;
    @Column(name = "okr_num_2", precision = 17, scale = 6)
    private BigDecimal okrNum2;
    @Size(max = 64)
    @Column(name = "okr_vchar_1", length = 64)
    private String okrVchar1;
    @JoinColumn(name = "okr_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok okrRokSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sklOkrSerial")
    private List<PlaceSkl> placeSklList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parOkrSerial")
    private List<Paragon> paragonList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ksiOkrSerial")
    private List<Ksiegapir> ksiegapirList;
    @OneToMany(mappedBy = "aooOkrSerial")
    private List<AmoOkr> amoOkrList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zakOkrSerial")
    private List<Zakupy> zakupyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pzzOkrSerial")
    private List<PlacePrzZus> placePrzZusList;
    @OneToMany(mappedBy = "kagOkrSerial")
    private List<KalGodz> kalGodzList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "przOkrSerial")
    private List<PlacePrz> placePrzList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lplOkrSerial")
    private List<Place> placeList;
    @OneToMany(mappedBy = "rzuOkrSerial")
    private List<Rozliczus> rozliczusList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "epzOkrSerial")
    private List<EppZest> eppZestList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mdoOkrSerial")
    private List<Magdok> magdokList;
    @OneToMany(mappedBy = "ssoOkrSerial")
    private List<StSystWart> stSystWartList;
    @OneToMany(mappedBy = "spiOkrSerial")
    private List<Spisy> spisyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pzlOkrSerial")
    private List<PlaceZlec> placeZlecList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eppOkrSerial")
    private List<Przebieg> przebiegList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kldOkrSerial")
    private List<Kalendarz> kalendarzList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodOkrSerial")
    private List<RozlOdli> rozlOdliList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sprOkrSerial")
    private List<Sprzedaz> sprzedazList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lisOkrSerial")
    private List<Listy> listyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fakOkrSerial")
    private List<Fakrach> fakrachList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ppoOkrSerial")
    private List<PlacePot> placePotList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rzrOkrSerial")
    private List<RozlZrodel> rozlZrodelList;

    public Okres() {
    }

    public Okres(Integer okrSerial) {
        this.okrSerial = okrSerial;
    }

    public Okres(Integer okrSerial, Date okrDataOd, Date okrDataDo, String okrOpis, short okrMieNumer, Character okrZamkniety) {
        this.okrSerial = okrSerial;
        this.okrDataOd = okrDataOd;
        this.okrDataDo = okrDataDo;
        this.okrOpis = okrOpis;
        this.okrMieNumer = okrMieNumer;
        this.okrZamkniety = okrZamkniety;
    }

    public Integer getOkrSerial() {
        return okrSerial;
    }

    public void setOkrSerial(Integer okrSerial) {
        this.okrSerial = okrSerial;
    }

    public Date getOkrDataOd() {
        return okrDataOd;
    }

    public void setOkrDataOd(Date okrDataOd) {
        this.okrDataOd = okrDataOd;
    }

    public Date getOkrDataDo() {
        return okrDataDo;
    }

    public void setOkrDataDo(Date okrDataDo) {
        this.okrDataDo = okrDataDo;
    }

    public String getOkrOpis() {
        return okrOpis;
    }

    public void setOkrOpis(String okrOpis) {
        this.okrOpis = okrOpis;
    }

    public short getOkrMieNumer() {
        return okrMieNumer;
    }

    public void setOkrMieNumer(short okrMieNumer) {
        this.okrMieNumer = okrMieNumer;
    }

    public Character getOkrZamkniety() {
        return okrZamkniety;
    }

    public void setOkrZamkniety(Character okrZamkniety) {
        this.okrZamkniety = okrZamkniety;
    }

    public Character getOkrKryteria() {
        return okrKryteria;
    }

    public void setOkrKryteria(Character okrKryteria) {
        this.okrKryteria = okrKryteria;
    }

    public Short getOkrDniPracy() {
        return okrDniPracy;
    }

    public void setOkrDniPracy(Short okrDniPracy) {
        this.okrDniPracy = okrDniPracy;
    }

    public Character getOkr1() {
        return okr1;
    }

    public void setOkr1(Character okr1) {
        this.okr1 = okr1;
    }

    public Character getOkr2() {
        return okr2;
    }

    public void setOkr2(Character okr2) {
        this.okr2 = okr2;
    }

    public Character getOkr3() {
        return okr3;
    }

    public void setOkr3(Character okr3) {
        this.okr3 = okr3;
    }

    public Character getOkr4() {
        return okr4;
    }

    public void setOkr4(Character okr4) {
        this.okr4 = okr4;
    }

    public Character getOkr5() {
        return okr5;
    }

    public void setOkr5(Character okr5) {
        this.okr5 = okr5;
    }

    public Character getOkr6() {
        return okr6;
    }

    public void setOkr6(Character okr6) {
        this.okr6 = okr6;
    }

    public Character getOkr7() {
        return okr7;
    }

    public void setOkr7(Character okr7) {
        this.okr7 = okr7;
    }

    public Character getOkr8() {
        return okr8;
    }

    public void setOkr8(Character okr8) {
        this.okr8 = okr8;
    }

    public Character getOkr9() {
        return okr9;
    }

    public void setOkr9(Character okr9) {
        this.okr9 = okr9;
    }

    public Character getOkr10() {
        return okr10;
    }

    public void setOkr10(Character okr10) {
        this.okr10 = okr10;
    }

    public Character getOkr11() {
        return okr11;
    }

    public void setOkr11(Character okr11) {
        this.okr11 = okr11;
    }

    public Character getOkr12() {
        return okr12;
    }

    public void setOkr12(Character okr12) {
        this.okr12 = okr12;
    }

    public Character getOkr13() {
        return okr13;
    }

    public void setOkr13(Character okr13) {
        this.okr13 = okr13;
    }

    public Character getOkr14() {
        return okr14;
    }

    public void setOkr14(Character okr14) {
        this.okr14 = okr14;
    }

    public Character getOkr15() {
        return okr15;
    }

    public void setOkr15(Character okr15) {
        this.okr15 = okr15;
    }

    public Character getOkr16() {
        return okr16;
    }

    public void setOkr16(Character okr16) {
        this.okr16 = okr16;
    }

    public Character getOkr17() {
        return okr17;
    }

    public void setOkr17(Character okr17) {
        this.okr17 = okr17;
    }

    public Character getOkr18() {
        return okr18;
    }

    public void setOkr18(Character okr18) {
        this.okr18 = okr18;
    }

    public Character getOkr19() {
        return okr19;
    }

    public void setOkr19(Character okr19) {
        this.okr19 = okr19;
    }

    public Character getOkr20() {
        return okr20;
    }

    public void setOkr20(Character okr20) {
        this.okr20 = okr20;
    }

    public Character getOkr21() {
        return okr21;
    }

    public void setOkr21(Character okr21) {
        this.okr21 = okr21;
    }

    public Character getOkr22() {
        return okr22;
    }

    public void setOkr22(Character okr22) {
        this.okr22 = okr22;
    }

    public Character getOkr23() {
        return okr23;
    }

    public void setOkr23(Character okr23) {
        this.okr23 = okr23;
    }

    public Character getOkr24() {
        return okr24;
    }

    public void setOkr24(Character okr24) {
        this.okr24 = okr24;
    }

    public Character getOkr25() {
        return okr25;
    }

    public void setOkr25(Character okr25) {
        this.okr25 = okr25;
    }

    public Character getOkr26() {
        return okr26;
    }

    public void setOkr26(Character okr26) {
        this.okr26 = okr26;
    }

    public Character getOkr27() {
        return okr27;
    }

    public void setOkr27(Character okr27) {
        this.okr27 = okr27;
    }

    public Character getOkr28() {
        return okr28;
    }

    public void setOkr28(Character okr28) {
        this.okr28 = okr28;
    }

    public Character getOkr29() {
        return okr29;
    }

    public void setOkr29(Character okr29) {
        this.okr29 = okr29;
    }

    public Character getOkr30() {
        return okr30;
    }

    public void setOkr30(Character okr30) {
        this.okr30 = okr30;
    }

    public Character getOkr31() {
        return okr31;
    }

    public void setOkr31(Character okr31) {
        this.okr31 = okr31;
    }

    public Character getOkrChar1() {
        return okrChar1;
    }

    public void setOkrChar1(Character okrChar1) {
        this.okrChar1 = okrChar1;
    }

    public BigDecimal getOkrNum1() {
        return okrNum1;
    }

    public void setOkrNum1(BigDecimal okrNum1) {
        this.okrNum1 = okrNum1;
    }

    public Character getOkrChar2() {
        return okrChar2;
    }

    public void setOkrChar2(Character okrChar2) {
        this.okrChar2 = okrChar2;
    }

    public Character getOkrChar3() {
        return okrChar3;
    }

    public void setOkrChar3(Character okrChar3) {
        this.okrChar3 = okrChar3;
    }

    public Character getOkrChar4() {
        return okrChar4;
    }

    public void setOkrChar4(Character okrChar4) {
        this.okrChar4 = okrChar4;
    }

    public BigDecimal getOkrNum2() {
        return okrNum2;
    }

    public void setOkrNum2(BigDecimal okrNum2) {
        this.okrNum2 = okrNum2;
    }

    public String getOkrVchar1() {
        return okrVchar1;
    }

    public void setOkrVchar1(String okrVchar1) {
        this.okrVchar1 = okrVchar1;
    }

    public Rok getOkrRokSerial() {
        return okrRokSerial;
    }

    public void setOkrRokSerial(Rok okrRokSerial) {
        this.okrRokSerial = okrRokSerial;
    }

    @XmlTransient
    public List<PlaceSkl> getPlaceSklList() {
        return placeSklList;
    }

    public void setPlaceSklList(List<PlaceSkl> placeSklList) {
        this.placeSklList = placeSklList;
    }

    @XmlTransient
    public List<Paragon> getParagonList() {
        return paragonList;
    }

    public void setParagonList(List<Paragon> paragonList) {
        this.paragonList = paragonList;
    }

    @XmlTransient
    public List<Ksiegapir> getKsiegapirList() {
        return ksiegapirList;
    }

    public void setKsiegapirList(List<Ksiegapir> ksiegapirList) {
        this.ksiegapirList = ksiegapirList;
    }

    @XmlTransient
    public List<AmoOkr> getAmoOkrList() {
        return amoOkrList;
    }

    public void setAmoOkrList(List<AmoOkr> amoOkrList) {
        this.amoOkrList = amoOkrList;
    }

    @XmlTransient
    public List<Zakupy> getZakupyList() {
        return zakupyList;
    }

    public void setZakupyList(List<Zakupy> zakupyList) {
        this.zakupyList = zakupyList;
    }

    @XmlTransient
    public List<PlacePrzZus> getPlacePrzZusList() {
        return placePrzZusList;
    }

    public void setPlacePrzZusList(List<PlacePrzZus> placePrzZusList) {
        this.placePrzZusList = placePrzZusList;
    }

    @XmlTransient
    public List<KalGodz> getKalGodzList() {
        return kalGodzList;
    }

    public void setKalGodzList(List<KalGodz> kalGodzList) {
        this.kalGodzList = kalGodzList;
    }

    @XmlTransient
    public List<PlacePrz> getPlacePrzList() {
        return placePrzList;
    }

    public void setPlacePrzList(List<PlacePrz> placePrzList) {
        this.placePrzList = placePrzList;
    }

    @XmlTransient
    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    @XmlTransient
    public List<Rozliczus> getRozliczusList() {
        return rozliczusList;
    }

    public void setRozliczusList(List<Rozliczus> rozliczusList) {
        this.rozliczusList = rozliczusList;
    }

    @XmlTransient
    public List<EppZest> getEppZestList() {
        return eppZestList;
    }

    public void setEppZestList(List<EppZest> eppZestList) {
        this.eppZestList = eppZestList;
    }

    @XmlTransient
    public List<Magdok> getMagdokList() {
        return magdokList;
    }

    public void setMagdokList(List<Magdok> magdokList) {
        this.magdokList = magdokList;
    }

    @XmlTransient
    public List<StSystWart> getStSystWartList() {
        return stSystWartList;
    }

    public void setStSystWartList(List<StSystWart> stSystWartList) {
        this.stSystWartList = stSystWartList;
    }

    @XmlTransient
    public List<Spisy> getSpisyList() {
        return spisyList;
    }

    public void setSpisyList(List<Spisy> spisyList) {
        this.spisyList = spisyList;
    }

    @XmlTransient
    public List<PlaceZlec> getPlaceZlecList() {
        return placeZlecList;
    }

    public void setPlaceZlecList(List<PlaceZlec> placeZlecList) {
        this.placeZlecList = placeZlecList;
    }

    @XmlTransient
    public List<Przebieg> getPrzebiegList() {
        return przebiegList;
    }

    public void setPrzebiegList(List<Przebieg> przebiegList) {
        this.przebiegList = przebiegList;
    }

    @XmlTransient
    public List<Kalendarz> getKalendarzList() {
        return kalendarzList;
    }

    public void setKalendarzList(List<Kalendarz> kalendarzList) {
        this.kalendarzList = kalendarzList;
    }

    @XmlTransient
    public List<RozlOdli> getRozlOdliList() {
        return rozlOdliList;
    }

    public void setRozlOdliList(List<RozlOdli> rozlOdliList) {
        this.rozlOdliList = rozlOdliList;
    }

    @XmlTransient
    public List<Sprzedaz> getSprzedazList() {
        return sprzedazList;
    }

    public void setSprzedazList(List<Sprzedaz> sprzedazList) {
        this.sprzedazList = sprzedazList;
    }

    @XmlTransient
    public List<Listy> getListyList() {
        return listyList;
    }

    public void setListyList(List<Listy> listyList) {
        this.listyList = listyList;
    }

    @XmlTransient
    public List<Fakrach> getFakrachList() {
        return fakrachList;
    }

    public void setFakrachList(List<Fakrach> fakrachList) {
        this.fakrachList = fakrachList;
    }

    @XmlTransient
    public List<PlacePot> getPlacePotList() {
        return placePotList;
    }

    public void setPlacePotList(List<PlacePot> placePotList) {
        this.placePotList = placePotList;
    }

    @XmlTransient
    public List<RozlZrodel> getRozlZrodelList() {
        return rozlZrodelList;
    }

    public void setRozlZrodelList(List<RozlZrodel> rozlZrodelList) {
        this.rozlZrodelList = rozlZrodelList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (okrSerial != null ? okrSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Okres)) {
            return false;
        }
        Okres other = (Okres) object;
        if ((this.okrSerial == null && other.okrSerial != null) || (this.okrSerial != null && !this.okrSerial.equals(other.okrSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Okres[ okrSerial=" + okrSerial + " ]";
    }
    
}
