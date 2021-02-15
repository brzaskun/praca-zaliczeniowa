/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "magazyn", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Magazyn.findAll", query = "SELECT m FROM Magazyn m"),
    @NamedQuery(name = "Magazyn.findByMagSerial", query = "SELECT m FROM Magazyn m WHERE m.magSerial = :magSerial"),
    @NamedQuery(name = "Magazyn.findByMagTyp", query = "SELECT m FROM Magazyn m WHERE m.magTyp = :magTyp"),
    @NamedQuery(name = "Magazyn.findByMagSymbol", query = "SELECT m FROM Magazyn m WHERE m.magSymbol = :magSymbol"),
    @NamedQuery(name = "Magazyn.findByMagNazwa", query = "SELECT m FROM Magazyn m WHERE m.magNazwa = :magNazwa"),
    @NamedQuery(name = "Magazyn.findByMagSwwKu", query = "SELECT m FROM Magazyn m WHERE m.magSwwKu = :magSwwKu"),
    @NamedQuery(name = "Magazyn.findByMagCenaWart", query = "SELECT m FROM Magazyn m WHERE m.magCenaWart = :magCenaWart"),
    @NamedQuery(name = "Magazyn.findByMagStVat", query = "SELECT m FROM Magazyn m WHERE m.magStVat = :magStVat"),
    @NamedQuery(name = "Magazyn.findByMagCenaSpr", query = "SELECT m FROM Magazyn m WHERE m.magCenaSpr = :magCenaSpr"),
    @NamedQuery(name = "Magazyn.findByMagStanMag", query = "SELECT m FROM Magazyn m WHERE m.magStanMag = :magStanMag"),
    @NamedQuery(name = "Magazyn.findByMagStan", query = "SELECT m FROM Magazyn m WHERE m.magStan = :magStan"),
    @NamedQuery(name = "Magazyn.findByMagStanOstrz", query = "SELECT m FROM Magazyn m WHERE m.magStanOstrz = :magStanOstrz"),
    @NamedQuery(name = "Magazyn.findByMagVatSprzedaz", query = "SELECT m FROM Magazyn m WHERE m.magVatSprzedaz = :magVatSprzedaz"),
    @NamedQuery(name = "Magazyn.findByMagVatZakupy", query = "SELECT m FROM Magazyn m WHERE m.magVatZakupy = :magVatZakupy"),
    @NamedQuery(name = "Magazyn.findByMagStVatZ", query = "SELECT m FROM Magazyn m WHERE m.magStVatZ = :magStVatZ"),
    @NamedQuery(name = "Magazyn.findByMagOstrzegac", query = "SELECT m FROM Magazyn m WHERE m.magOstrzegac = :magOstrzegac"),
    @NamedQuery(name = "Magazyn.findByMagStanOstrzMax", query = "SELECT m FROM Magazyn m WHERE m.magStanOstrzMax = :magStanOstrzMax"),
    @NamedQuery(name = "Magazyn.findByMagKodKres", query = "SELECT m FROM Magazyn m WHERE m.magKodKres = :magKodKres"),
    @NamedQuery(name = "Magazyn.findByMagCenaSpr2", query = "SELECT m FROM Magazyn m WHERE m.magCenaSpr2 = :magCenaSpr2"),
    @NamedQuery(name = "Magazyn.findByMagCenaSpr3", query = "SELECT m FROM Magazyn m WHERE m.magCenaSpr3 = :magCenaSpr3"),
    @NamedQuery(name = "Magazyn.findByMagMarza1", query = "SELECT m FROM Magazyn m WHERE m.magMarza1 = :magMarza1"),
    @NamedQuery(name = "Magazyn.findByMagMarza2", query = "SELECT m FROM Magazyn m WHERE m.magMarza2 = :magMarza2"),
    @NamedQuery(name = "Magazyn.findByMagMarza3", query = "SELECT m FROM Magazyn m WHERE m.magMarza3 = :magMarza3"),
    @NamedQuery(name = "Magazyn.findByMagCenaSprBr1", query = "SELECT m FROM Magazyn m WHERE m.magCenaSprBr1 = :magCenaSprBr1"),
    @NamedQuery(name = "Magazyn.findByMagCenaSprBr2", query = "SELECT m FROM Magazyn m WHERE m.magCenaSprBr2 = :magCenaSprBr2"),
    @NamedQuery(name = "Magazyn.findByMagCenaSprBr3", query = "SELECT m FROM Magazyn m WHERE m.magCenaSprBr3 = :magCenaSprBr3"),
    @NamedQuery(name = "Magazyn.findByMagUwagi", query = "SELECT m FROM Magazyn m WHERE m.magUwagi = :magUwagi"),
    @NamedQuery(name = "Magazyn.findByMagCenaWartBr", query = "SELECT m FROM Magazyn m WHERE m.magCenaWartBr = :magCenaWartBr"),
    @NamedQuery(name = "Magazyn.findByMagDodChar1", query = "SELECT m FROM Magazyn m WHERE m.magDodChar1 = :magDodChar1"),
    @NamedQuery(name = "Magazyn.findByMagDodChar2", query = "SELECT m FROM Magazyn m WHERE m.magDodChar2 = :magDodChar2"),
    @NamedQuery(name = "Magazyn.findByMagDodNum1", query = "SELECT m FROM Magazyn m WHERE m.magDodNum1 = :magDodNum1"),
    @NamedQuery(name = "Magazyn.findByMagDodNum2", query = "SELECT m FROM Magazyn m WHERE m.magDodNum2 = :magDodNum2"),
    @NamedQuery(name = "Magazyn.findByMagDodVchar1", query = "SELECT m FROM Magazyn m WHERE m.magDodVchar1 = :magDodVchar1"),
    @NamedQuery(name = "Magazyn.findByMagDodVchar2", query = "SELECT m FROM Magazyn m WHERE m.magDodVchar2 = :magDodVchar2"),
    @NamedQuery(name = "Magazyn.findByMagDodNum3", query = "SELECT m FROM Magazyn m WHERE m.magDodNum3 = :magDodNum3"),
    @NamedQuery(name = "Magazyn.findByMagDodNum4", query = "SELECT m FROM Magazyn m WHERE m.magDodNum4 = :magDodNum4"),
    @NamedQuery(name = "Magazyn.findByMagDodNum5", query = "SELECT m FROM Magazyn m WHERE m.magDodNum5 = :magDodNum5"),
    @NamedQuery(name = "Magazyn.findByMagDodNum6", query = "SELECT m FROM Magazyn m WHERE m.magDodNum6 = :magDodNum6"),
    @NamedQuery(name = "Magazyn.findByMagDodNum7", query = "SELECT m FROM Magazyn m WHERE m.magDodNum7 = :magDodNum7"),
    @NamedQuery(name = "Magazyn.findByMagDodNum8", query = "SELECT m FROM Magazyn m WHERE m.magDodNum8 = :magDodNum8"),
    @NamedQuery(name = "Magazyn.findByMagDodNum9", query = "SELECT m FROM Magazyn m WHERE m.magDodNum9 = :magDodNum9"),
    @NamedQuery(name = "Magazyn.findByMagDodNum10", query = "SELECT m FROM Magazyn m WHERE m.magDodNum10 = :magDodNum10"),
    @NamedQuery(name = "Magazyn.findByMagDodNum11", query = "SELECT m FROM Magazyn m WHERE m.magDodNum11 = :magDodNum11"),
    @NamedQuery(name = "Magazyn.findByMagDodNum12", query = "SELECT m FROM Magazyn m WHERE m.magDodNum12 = :magDodNum12"),
    @NamedQuery(name = "Magazyn.findByMagDodNum13", query = "SELECT m FROM Magazyn m WHERE m.magDodNum13 = :magDodNum13"),
    @NamedQuery(name = "Magazyn.findByMagDodNum14", query = "SELECT m FROM Magazyn m WHERE m.magDodNum14 = :magDodNum14")})
public class Magazyn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mag_serial", nullable = false)
    private Integer magSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mag_typ", nullable = false)
    private Character magTyp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "mag_symbol", nullable = false, length = 16)
    private String magSymbol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 96)
    @Column(name = "mag_nazwa", nullable = false, length = 96)
    private String magNazwa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "mag_sww_ku", nullable = false, length = 32)
    private String magSwwKu;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "mag_cena_wart", nullable = false, precision = 13, scale = 4)
    private BigDecimal magCenaWart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mag_st_vat", nullable = false, precision = 5, scale = 2)
    private BigDecimal magStVat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mag_cena_spr", nullable = false, precision = 13, scale = 4)
    private BigDecimal magCenaSpr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mag_stan_mag", nullable = false)
    private Character magStanMag;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mag_stan", nullable = false, precision = 17, scale = 6)
    private BigDecimal magStan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mag_stan_ostrz", nullable = false, precision = 17, scale = 6)
    private BigDecimal magStanOstrz;
    @Column(name = "mag_vat_sprzedaz")
    private Character magVatSprzedaz;
    @Column(name = "mag_vat_zakupy")
    private Character magVatZakupy;
    @Column(name = "mag_st_vat_z", precision = 5, scale = 2)
    private BigDecimal magStVatZ;
    @Column(name = "mag_ostrzegac")
    private Character magOstrzegac;
    @Column(name = "mag_stan_ostrz_max", precision = 17, scale = 6)
    private BigDecimal magStanOstrzMax;
    @Size(max = 32)
    @Column(name = "mag_kod_kres", length = 32)
    private String magKodKres;
    @Column(name = "mag_cena_spr_2", precision = 13, scale = 4)
    private BigDecimal magCenaSpr2;
    @Column(name = "mag_cena_spr_3", precision = 13, scale = 4)
    private BigDecimal magCenaSpr3;
    @Column(name = "mag_marza_1", precision = 5, scale = 2)
    private BigDecimal magMarza1;
    @Column(name = "mag_marza_2", precision = 5, scale = 2)
    private BigDecimal magMarza2;
    @Column(name = "mag_marza_3", precision = 5, scale = 2)
    private BigDecimal magMarza3;
    @Column(name = "mag_cena_spr_br_1", precision = 13, scale = 4)
    private BigDecimal magCenaSprBr1;
    @Column(name = "mag_cena_spr_br_2", precision = 13, scale = 4)
    private BigDecimal magCenaSprBr2;
    @Column(name = "mag_cena_spr_br_3", precision = 13, scale = 4)
    private BigDecimal magCenaSprBr3;
    @Size(max = 64)
    @Column(name = "mag_uwagi", length = 64)
    private String magUwagi;
    @Column(name = "mag_cena_wart_br", precision = 13, scale = 4)
    private BigDecimal magCenaWartBr;
    @Column(name = "mag_dod_char_1")
    private Character magDodChar1;
    @Column(name = "mag_dod_char_2")
    private Character magDodChar2;
    @Column(name = "mag_dod_num_1", precision = 17, scale = 6)
    private BigDecimal magDodNum1;
    @Column(name = "mag_dod_num_2", precision = 17, scale = 6)
    private BigDecimal magDodNum2;
    @Size(max = 64)
    @Column(name = "mag_dod_vchar_1", length = 64)
    private String magDodVchar1;
    @Size(max = 64)
    @Column(name = "mag_dod_vchar_2", length = 64)
    private String magDodVchar2;
    @Column(name = "mag_dod_num_3", precision = 13, scale = 4)
    private BigDecimal magDodNum3;
    @Column(name = "mag_dod_num_4", precision = 13, scale = 4)
    private BigDecimal magDodNum4;
    @Column(name = "mag_dod_num_5", precision = 13, scale = 4)
    private BigDecimal magDodNum5;
    @Column(name = "mag_dod_num_6", precision = 13, scale = 4)
    private BigDecimal magDodNum6;
    @Column(name = "mag_dod_num_7", precision = 13, scale = 4)
    private BigDecimal magDodNum7;
    @Column(name = "mag_dod_num_8", precision = 13, scale = 4)
    private BigDecimal magDodNum8;
    @Column(name = "mag_dod_num_9", precision = 13, scale = 4)
    private BigDecimal magDodNum9;
    @Column(name = "mag_dod_num_10", precision = 13, scale = 4)
    private BigDecimal magDodNum10;
    @Column(name = "mag_dod_num_11", precision = 5, scale = 2)
    private BigDecimal magDodNum11;
    @Column(name = "mag_dod_num_12", precision = 5, scale = 2)
    private BigDecimal magDodNum12;
    @Column(name = "mag_dod_num_13", precision = 5, scale = 2)
    private BigDecimal magDodNum13;
    @Column(name = "mag_dod_num_14", precision = 5, scale = 2)
    private BigDecimal magDodNum14;
    @OneToMany(mappedBy = "damMagSerial")
    private List<DaneStDaM> daneStDaMList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zpzMagSerial")
    private List<Zakpoz> zakpozList;
    @OneToMany(mappedBy = "dhmMagSerial")
    private List<DaneHiDaM> daneHiDaMList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scmMagSerial")
    private List<ScMag> scMagList;
    @OneToMany(mappedBy = "dsmMagSerial")
    private List<DaneStatM> daneStatMList;
    @OneToMany(mappedBy = "dlmMagSerial")
    private List<DaneLiDaM> daneLiDaMList;
    @JoinColumn(name = "mag_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma magFirSerial;
    @JoinColumn(name = "mag_gru_serial", referencedColumnName = "gru_serial", nullable = false)
    @ManyToOne(optional = false)
    private Grupa magGruSerial;
    @JoinColumn(name = "mag_jed_serial", referencedColumnName = "jed_serial", nullable = false)
    @ManyToOne(optional = false)
    private Jednostka magJedSerial;
    @JoinColumn(name = "mag_lgr_serial", referencedColumnName = "lgr_serial")
    @ManyToOne
    private LokGrupa magLgrSerial;
    @JoinColumn(name = "mag_lok_serial", referencedColumnName = "lok_serial")
    @ManyToOne
    private Lokalizacja magLokSerial;
    @JoinColumn(name = "mag_pgr_serial", referencedColumnName = "pgr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Podgrupa magPgrSerial;
    @JoinColumn(name = "mag_zak_wal_serial", referencedColumnName = "wal_serial")
    @ManyToOne
    private Waluta magZakWalSerial;
    @JoinColumn(name = "mag_spr_wal_serial", referencedColumnName = "wal_serial")
    @ManyToOne
    private Waluta magSprWalSerial;
    @OneToMany(mappedBy = "pozMagSerial")
    private List<Pozycja> pozycjaList;
    @OneToMany(mappedBy = "mpzMagSerial")
    private List<Magpoz> magpozList;
    @OneToMany(mappedBy = "pazMagSerial")
    private List<Parpoz> parpozList;

    public Magazyn() {
    }

    public Magazyn(Integer magSerial) {
        this.magSerial = magSerial;
    }

    public Magazyn(Integer magSerial, Character magTyp, String magSymbol, String magNazwa, String magSwwKu, BigDecimal magCenaWart, BigDecimal magStVat, BigDecimal magCenaSpr, Character magStanMag, BigDecimal magStan, BigDecimal magStanOstrz) {
        this.magSerial = magSerial;
        this.magTyp = magTyp;
        this.magSymbol = magSymbol;
        this.magNazwa = magNazwa;
        this.magSwwKu = magSwwKu;
        this.magCenaWart = magCenaWart;
        this.magStVat = magStVat;
        this.magCenaSpr = magCenaSpr;
        this.magStanMag = magStanMag;
        this.magStan = magStan;
        this.magStanOstrz = magStanOstrz;
    }

    public Integer getMagSerial() {
        return magSerial;
    }

    public void setMagSerial(Integer magSerial) {
        this.magSerial = magSerial;
    }

    public Character getMagTyp() {
        return magTyp;
    }

    public void setMagTyp(Character magTyp) {
        this.magTyp = magTyp;
    }

    public String getMagSymbol() {
        return magSymbol;
    }

    public void setMagSymbol(String magSymbol) {
        this.magSymbol = magSymbol;
    }

    public String getMagNazwa() {
        return magNazwa;
    }

    public void setMagNazwa(String magNazwa) {
        this.magNazwa = magNazwa;
    }

    public String getMagSwwKu() {
        return magSwwKu;
    }

    public void setMagSwwKu(String magSwwKu) {
        this.magSwwKu = magSwwKu;
    }

    public BigDecimal getMagCenaWart() {
        return magCenaWart;
    }

    public void setMagCenaWart(BigDecimal magCenaWart) {
        this.magCenaWart = magCenaWart;
    }

    public BigDecimal getMagStVat() {
        return magStVat;
    }

    public void setMagStVat(BigDecimal magStVat) {
        this.magStVat = magStVat;
    }

    public BigDecimal getMagCenaSpr() {
        return magCenaSpr;
    }

    public void setMagCenaSpr(BigDecimal magCenaSpr) {
        this.magCenaSpr = magCenaSpr;
    }

    public Character getMagStanMag() {
        return magStanMag;
    }

    public void setMagStanMag(Character magStanMag) {
        this.magStanMag = magStanMag;
    }

    public BigDecimal getMagStan() {
        return magStan;
    }

    public void setMagStan(BigDecimal magStan) {
        this.magStan = magStan;
    }

    public BigDecimal getMagStanOstrz() {
        return magStanOstrz;
    }

    public void setMagStanOstrz(BigDecimal magStanOstrz) {
        this.magStanOstrz = magStanOstrz;
    }

    public Character getMagVatSprzedaz() {
        return magVatSprzedaz;
    }

    public void setMagVatSprzedaz(Character magVatSprzedaz) {
        this.magVatSprzedaz = magVatSprzedaz;
    }

    public Character getMagVatZakupy() {
        return magVatZakupy;
    }

    public void setMagVatZakupy(Character magVatZakupy) {
        this.magVatZakupy = magVatZakupy;
    }

    public BigDecimal getMagStVatZ() {
        return magStVatZ;
    }

    public void setMagStVatZ(BigDecimal magStVatZ) {
        this.magStVatZ = magStVatZ;
    }

    public Character getMagOstrzegac() {
        return magOstrzegac;
    }

    public void setMagOstrzegac(Character magOstrzegac) {
        this.magOstrzegac = magOstrzegac;
    }

    public BigDecimal getMagStanOstrzMax() {
        return magStanOstrzMax;
    }

    public void setMagStanOstrzMax(BigDecimal magStanOstrzMax) {
        this.magStanOstrzMax = magStanOstrzMax;
    }

    public String getMagKodKres() {
        return magKodKres;
    }

    public void setMagKodKres(String magKodKres) {
        this.magKodKres = magKodKres;
    }

    public BigDecimal getMagCenaSpr2() {
        return magCenaSpr2;
    }

    public void setMagCenaSpr2(BigDecimal magCenaSpr2) {
        this.magCenaSpr2 = magCenaSpr2;
    }

    public BigDecimal getMagCenaSpr3() {
        return magCenaSpr3;
    }

    public void setMagCenaSpr3(BigDecimal magCenaSpr3) {
        this.magCenaSpr3 = magCenaSpr3;
    }

    public BigDecimal getMagMarza1() {
        return magMarza1;
    }

    public void setMagMarza1(BigDecimal magMarza1) {
        this.magMarza1 = magMarza1;
    }

    public BigDecimal getMagMarza2() {
        return magMarza2;
    }

    public void setMagMarza2(BigDecimal magMarza2) {
        this.magMarza2 = magMarza2;
    }

    public BigDecimal getMagMarza3() {
        return magMarza3;
    }

    public void setMagMarza3(BigDecimal magMarza3) {
        this.magMarza3 = magMarza3;
    }

    public BigDecimal getMagCenaSprBr1() {
        return magCenaSprBr1;
    }

    public void setMagCenaSprBr1(BigDecimal magCenaSprBr1) {
        this.magCenaSprBr1 = magCenaSprBr1;
    }

    public BigDecimal getMagCenaSprBr2() {
        return magCenaSprBr2;
    }

    public void setMagCenaSprBr2(BigDecimal magCenaSprBr2) {
        this.magCenaSprBr2 = magCenaSprBr2;
    }

    public BigDecimal getMagCenaSprBr3() {
        return magCenaSprBr3;
    }

    public void setMagCenaSprBr3(BigDecimal magCenaSprBr3) {
        this.magCenaSprBr3 = magCenaSprBr3;
    }

    public String getMagUwagi() {
        return magUwagi;
    }

    public void setMagUwagi(String magUwagi) {
        this.magUwagi = magUwagi;
    }

    public BigDecimal getMagCenaWartBr() {
        return magCenaWartBr;
    }

    public void setMagCenaWartBr(BigDecimal magCenaWartBr) {
        this.magCenaWartBr = magCenaWartBr;
    }

    public Character getMagDodChar1() {
        return magDodChar1;
    }

    public void setMagDodChar1(Character magDodChar1) {
        this.magDodChar1 = magDodChar1;
    }

    public Character getMagDodChar2() {
        return magDodChar2;
    }

    public void setMagDodChar2(Character magDodChar2) {
        this.magDodChar2 = magDodChar2;
    }

    public BigDecimal getMagDodNum1() {
        return magDodNum1;
    }

    public void setMagDodNum1(BigDecimal magDodNum1) {
        this.magDodNum1 = magDodNum1;
    }

    public BigDecimal getMagDodNum2() {
        return magDodNum2;
    }

    public void setMagDodNum2(BigDecimal magDodNum2) {
        this.magDodNum2 = magDodNum2;
    }

    public String getMagDodVchar1() {
        return magDodVchar1;
    }

    public void setMagDodVchar1(String magDodVchar1) {
        this.magDodVchar1 = magDodVchar1;
    }

    public String getMagDodVchar2() {
        return magDodVchar2;
    }

    public void setMagDodVchar2(String magDodVchar2) {
        this.magDodVchar2 = magDodVchar2;
    }

    public BigDecimal getMagDodNum3() {
        return magDodNum3;
    }

    public void setMagDodNum3(BigDecimal magDodNum3) {
        this.magDodNum3 = magDodNum3;
    }

    public BigDecimal getMagDodNum4() {
        return magDodNum4;
    }

    public void setMagDodNum4(BigDecimal magDodNum4) {
        this.magDodNum4 = magDodNum4;
    }

    public BigDecimal getMagDodNum5() {
        return magDodNum5;
    }

    public void setMagDodNum5(BigDecimal magDodNum5) {
        this.magDodNum5 = magDodNum5;
    }

    public BigDecimal getMagDodNum6() {
        return magDodNum6;
    }

    public void setMagDodNum6(BigDecimal magDodNum6) {
        this.magDodNum6 = magDodNum6;
    }

    public BigDecimal getMagDodNum7() {
        return magDodNum7;
    }

    public void setMagDodNum7(BigDecimal magDodNum7) {
        this.magDodNum7 = magDodNum7;
    }

    public BigDecimal getMagDodNum8() {
        return magDodNum8;
    }

    public void setMagDodNum8(BigDecimal magDodNum8) {
        this.magDodNum8 = magDodNum8;
    }

    public BigDecimal getMagDodNum9() {
        return magDodNum9;
    }

    public void setMagDodNum9(BigDecimal magDodNum9) {
        this.magDodNum9 = magDodNum9;
    }

    public BigDecimal getMagDodNum10() {
        return magDodNum10;
    }

    public void setMagDodNum10(BigDecimal magDodNum10) {
        this.magDodNum10 = magDodNum10;
    }

    public BigDecimal getMagDodNum11() {
        return magDodNum11;
    }

    public void setMagDodNum11(BigDecimal magDodNum11) {
        this.magDodNum11 = magDodNum11;
    }

    public BigDecimal getMagDodNum12() {
        return magDodNum12;
    }

    public void setMagDodNum12(BigDecimal magDodNum12) {
        this.magDodNum12 = magDodNum12;
    }

    public BigDecimal getMagDodNum13() {
        return magDodNum13;
    }

    public void setMagDodNum13(BigDecimal magDodNum13) {
        this.magDodNum13 = magDodNum13;
    }

    public BigDecimal getMagDodNum14() {
        return magDodNum14;
    }

    public void setMagDodNum14(BigDecimal magDodNum14) {
        this.magDodNum14 = magDodNum14;
    }

    @XmlTransient
    public List<DaneStDaM> getDaneStDaMList() {
        return daneStDaMList;
    }

    public void setDaneStDaMList(List<DaneStDaM> daneStDaMList) {
        this.daneStDaMList = daneStDaMList;
    }

    @XmlTransient
    public List<Zakpoz> getZakpozList() {
        return zakpozList;
    }

    public void setZakpozList(List<Zakpoz> zakpozList) {
        this.zakpozList = zakpozList;
    }

    @XmlTransient
    public List<DaneHiDaM> getDaneHiDaMList() {
        return daneHiDaMList;
    }

    public void setDaneHiDaMList(List<DaneHiDaM> daneHiDaMList) {
        this.daneHiDaMList = daneHiDaMList;
    }

    @XmlTransient
    public List<ScMag> getScMagList() {
        return scMagList;
    }

    public void setScMagList(List<ScMag> scMagList) {
        this.scMagList = scMagList;
    }

    @XmlTransient
    public List<DaneStatM> getDaneStatMList() {
        return daneStatMList;
    }

    public void setDaneStatMList(List<DaneStatM> daneStatMList) {
        this.daneStatMList = daneStatMList;
    }

    @XmlTransient
    public List<DaneLiDaM> getDaneLiDaMList() {
        return daneLiDaMList;
    }

    public void setDaneLiDaMList(List<DaneLiDaM> daneLiDaMList) {
        this.daneLiDaMList = daneLiDaMList;
    }

    public Firma getMagFirSerial() {
        return magFirSerial;
    }

    public void setMagFirSerial(Firma magFirSerial) {
        this.magFirSerial = magFirSerial;
    }

    public Grupa getMagGruSerial() {
        return magGruSerial;
    }

    public void setMagGruSerial(Grupa magGruSerial) {
        this.magGruSerial = magGruSerial;
    }

    public Jednostka getMagJedSerial() {
        return magJedSerial;
    }

    public void setMagJedSerial(Jednostka magJedSerial) {
        this.magJedSerial = magJedSerial;
    }

    public LokGrupa getMagLgrSerial() {
        return magLgrSerial;
    }

    public void setMagLgrSerial(LokGrupa magLgrSerial) {
        this.magLgrSerial = magLgrSerial;
    }

    public Lokalizacja getMagLokSerial() {
        return magLokSerial;
    }

    public void setMagLokSerial(Lokalizacja magLokSerial) {
        this.magLokSerial = magLokSerial;
    }

    public Podgrupa getMagPgrSerial() {
        return magPgrSerial;
    }

    public void setMagPgrSerial(Podgrupa magPgrSerial) {
        this.magPgrSerial = magPgrSerial;
    }

    public Waluta getMagZakWalSerial() {
        return magZakWalSerial;
    }

    public void setMagZakWalSerial(Waluta magZakWalSerial) {
        this.magZakWalSerial = magZakWalSerial;
    }

    public Waluta getMagSprWalSerial() {
        return magSprWalSerial;
    }

    public void setMagSprWalSerial(Waluta magSprWalSerial) {
        this.magSprWalSerial = magSprWalSerial;
    }

    @XmlTransient
    public List<Pozycja> getPozycjaList() {
        return pozycjaList;
    }

    public void setPozycjaList(List<Pozycja> pozycjaList) {
        this.pozycjaList = pozycjaList;
    }

    @XmlTransient
    public List<Magpoz> getMagpozList() {
        return magpozList;
    }

    public void setMagpozList(List<Magpoz> magpozList) {
        this.magpozList = magpozList;
    }

    @XmlTransient
    public List<Parpoz> getParpozList() {
        return parpozList;
    }

    public void setParpozList(List<Parpoz> parpozList) {
        this.parpozList = parpozList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (magSerial != null ? magSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Magazyn)) {
            return false;
        }
        Magazyn other = (Magazyn) object;
        if ((this.magSerial == null && other.magSerial != null) || (this.magSerial != null && !this.magSerial.equals(other.magSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Magazyn[ magSerial=" + magSerial + " ]";
    }
    
}
