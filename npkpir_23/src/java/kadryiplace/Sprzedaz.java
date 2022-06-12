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
@Table(name = "sprzedaz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sprzedaz.findAll", query = "SELECT s FROM Sprzedaz s"),
    @NamedQuery(name = "Sprzedaz.findBySprSerial", query = "SELECT s FROM Sprzedaz s WHERE s.sprSerial = :sprSerial"),
    @NamedQuery(name = "Sprzedaz.findBySprKolKsiegi", query = "SELECT s FROM Sprzedaz s WHERE s.sprKolKsiegi = :sprKolKsiegi"),
    @NamedQuery(name = "Sprzedaz.findBySprLp", query = "SELECT s FROM Sprzedaz s WHERE s.sprLp = :sprLp"),
    @NamedQuery(name = "Sprzedaz.findBySprNumer", query = "SELECT s FROM Sprzedaz s WHERE s.sprNumer = :sprNumer"),
    @NamedQuery(name = "Sprzedaz.findBySprDataWyst", query = "SELECT s FROM Sprzedaz s WHERE s.sprDataWyst = :sprDataWyst"),
    @NamedQuery(name = "Sprzedaz.findBySprDataSprz", query = "SELECT s FROM Sprzedaz s WHERE s.sprDataSprz = :sprDataSprz"),
    @NamedQuery(name = "Sprzedaz.findBySprPrzedSprz", query = "SELECT s FROM Sprzedaz s WHERE s.sprPrzedSprz = :sprPrzedSprz"),
    @NamedQuery(name = "Sprzedaz.findBySprOrygTyp", query = "SELECT s FROM Sprzedaz s WHERE s.sprOrygTyp = :sprOrygTyp"),
    @NamedQuery(name = "Sprzedaz.findBySprOrygSerial", query = "SELECT s FROM Sprzedaz s WHERE s.sprOrygSerial = :sprOrygSerial"),
    @NamedQuery(name = "Sprzedaz.findBySprKonNazwaSkr", query = "SELECT s FROM Sprzedaz s WHERE s.sprKonNazwaSkr = :sprKonNazwaSkr"),
    @NamedQuery(name = "Sprzedaz.findBySprKonNazwa", query = "SELECT s FROM Sprzedaz s WHERE s.sprKonNazwa = :sprKonNazwa"),
    @NamedQuery(name = "Sprzedaz.findBySprKonAdres", query = "SELECT s FROM Sprzedaz s WHERE s.sprKonAdres = :sprKonAdres"),
    @NamedQuery(name = "Sprzedaz.findBySprKonNip", query = "SELECT s FROM Sprzedaz s WHERE s.sprKonNip = :sprKonNip"),
    @NamedQuery(name = "Sprzedaz.findBySprKwZwol", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwZwol = :sprKwZwol"),
    @NamedQuery(name = "Sprzedaz.findBySprBezrKwBr", query = "SELECT s FROM Sprzedaz s WHERE s.sprBezrKwBr = :sprBezrKwBr"),
    @NamedQuery(name = "Sprzedaz.findBySprKwVat1", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwVat1 = :sprKwVat1"),
    @NamedQuery(name = "Sprzedaz.findBySprStVat1", query = "SELECT s FROM Sprzedaz s WHERE s.sprStVat1 = :sprStVat1"),
    @NamedQuery(name = "Sprzedaz.findBySprKwVat2", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwVat2 = :sprKwVat2"),
    @NamedQuery(name = "Sprzedaz.findBySprStVat2", query = "SELECT s FROM Sprzedaz s WHERE s.sprStVat2 = :sprStVat2"),
    @NamedQuery(name = "Sprzedaz.findBySprKwVat3", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwVat3 = :sprKwVat3"),
    @NamedQuery(name = "Sprzedaz.findBySprStVat3", query = "SELECT s FROM Sprzedaz s WHERE s.sprStVat3 = :sprStVat3"),
    @NamedQuery(name = "Sprzedaz.findBySprKwVat0Eksp", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwVat0Eksp = :sprKwVat0Eksp"),
    @NamedQuery(name = "Sprzedaz.findBySprKwVat0Kraj", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwVat0Kraj = :sprKwVat0Kraj"),
    @NamedQuery(name = "Sprzedaz.findBySprKwArt8", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwArt8 = :sprKwArt8"),
    @NamedQuery(name = "Sprzedaz.findBySprVatArt8", query = "SELECT s FROM Sprzedaz s WHERE s.sprVatArt8 = :sprVatArt8"),
    @NamedQuery(name = "Sprzedaz.findBySprKwBezVat", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwBezVat = :sprKwBezVat"),
    @NamedQuery(name = "Sprzedaz.findBySprUwagi", query = "SELECT s FROM Sprzedaz s WHERE s.sprUwagi = :sprUwagi"),
    @NamedQuery(name = "Sprzedaz.findBySprSkreslony", query = "SELECT s FROM Sprzedaz s WHERE s.sprSkreslony = :sprSkreslony"),
    @NamedQuery(name = "Sprzedaz.findBySprCzas", query = "SELECT s FROM Sprzedaz s WHERE s.sprCzas = :sprCzas"),
    @NamedQuery(name = "Sprzedaz.findBySprUsrUserid", query = "SELECT s FROM Sprzedaz s WHERE s.sprUsrUserid = :sprUsrUserid"),
    @NamedQuery(name = "Sprzedaz.findBySprCzasMod", query = "SELECT s FROM Sprzedaz s WHERE s.sprCzasMod = :sprCzasMod"),
    @NamedQuery(name = "Sprzedaz.findBySprUsrUseridMod", query = "SELECT s FROM Sprzedaz s WHERE s.sprUsrUseridMod = :sprUsrUseridMod"),
    @NamedQuery(name = "Sprzedaz.findBySprRejTyp", query = "SELECT s FROM Sprzedaz s WHERE s.sprRejTyp = :sprRejTyp"),
    @NamedQuery(name = "Sprzedaz.findBySprKol7", query = "SELECT s FROM Sprzedaz s WHERE s.sprKol7 = :sprKol7"),
    @NamedQuery(name = "Sprzedaz.findBySprKol8", query = "SELECT s FROM Sprzedaz s WHERE s.sprKol8 = :sprKol8"),
    @NamedQuery(name = "Sprzedaz.findBySprKorekty", query = "SELECT s FROM Sprzedaz s WHERE s.sprKorekty = :sprKorekty"),
    @NamedQuery(name = "Sprzedaz.findBySprKwVat4", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwVat4 = :sprKwVat4"),
    @NamedQuery(name = "Sprzedaz.findBySprStVat4", query = "SELECT s FROM Sprzedaz s WHERE s.sprStVat4 = :sprStVat4"),
    @NamedQuery(name = "Sprzedaz.findBySprKwVat", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwVat = :sprKwVat"),
    @NamedQuery(name = "Sprzedaz.findBySprKwVat0Exp2a", query = "SELECT s FROM Sprzedaz s WHERE s.sprKwVat0Exp2a = :sprKwVat0Exp2a"),
    @NamedQuery(name = "Sprzedaz.findBySprForOpis", query = "SELECT s FROM Sprzedaz s WHERE s.sprForOpis = :sprForOpis"),
    @NamedQuery(name = "Sprzedaz.findBySprDodNum1", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodNum1 = :sprDodNum1"),
    @NamedQuery(name = "Sprzedaz.findBySprDodNum2", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodNum2 = :sprDodNum2"),
    @NamedQuery(name = "Sprzedaz.findBySprDodNum3", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodNum3 = :sprDodNum3"),
    @NamedQuery(name = "Sprzedaz.findBySprDodNum4", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodNum4 = :sprDodNum4"),
    @NamedQuery(name = "Sprzedaz.findBySprDodChar1", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodChar1 = :sprDodChar1"),
    @NamedQuery(name = "Sprzedaz.findBySprDodChar2", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodChar2 = :sprDodChar2"),
    @NamedQuery(name = "Sprzedaz.findBySprDodVchar1", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodVchar1 = :sprDodVchar1"),
    @NamedQuery(name = "Sprzedaz.findBySprDodVchar2", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodVchar2 = :sprDodVchar2"),
    @NamedQuery(name = "Sprzedaz.findBySprDodNum5", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodNum5 = :sprDodNum5"),
    @NamedQuery(name = "Sprzedaz.findBySprDodNum6", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodNum6 = :sprDodNum6"),
    @NamedQuery(name = "Sprzedaz.findBySprDodNum7", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodNum7 = :sprDodNum7"),
    @NamedQuery(name = "Sprzedaz.findBySprDodNum8", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodNum8 = :sprDodNum8"),
    @NamedQuery(name = "Sprzedaz.findBySprDodNum9", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodNum9 = :sprDodNum9"),
    @NamedQuery(name = "Sprzedaz.findBySprDodNum10", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodNum10 = :sprDodNum10"),
    @NamedQuery(name = "Sprzedaz.findBySprDodChar3", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodChar3 = :sprDodChar3"),
    @NamedQuery(name = "Sprzedaz.findBySprDodChar4", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodChar4 = :sprDodChar4"),
    @NamedQuery(name = "Sprzedaz.findBySprDodData1", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodData1 = :sprDodData1"),
    @NamedQuery(name = "Sprzedaz.findBySprDodData2", query = "SELECT s FROM Sprzedaz s WHERE s.sprDodData2 = :sprDodData2")})
public class Sprzedaz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "spr_serial", nullable = false)
    private Integer sprSerial;
    @Column(name = "spr_kol_ksiegi")
    private Short sprKolKsiegi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "spr_lp", nullable = false)
    private int sprLp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "spr_numer", nullable = false, length = 32)
    private String sprNumer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "spr_data_wyst", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sprDataWyst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "spr_data_sprz", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sprDataSprz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spr_przed_sprz", nullable = false, length = 64)
    private String sprPrzedSprz;
    @Column(name = "spr_oryg_typ")
    private Character sprOrygTyp;
    @Column(name = "spr_oryg_serial")
    private Integer sprOrygSerial;
    @Size(max = 32)
    @Column(name = "spr_kon_nazwa_skr", length = 32)
    private String sprKonNazwaSkr;
    @Size(max = 64)
    @Column(name = "spr_kon_nazwa", length = 64)
    private String sprKonNazwa;
    @Size(max = 128)
    @Column(name = "spr_kon_adres", length = 128)
    private String sprKonAdres;
    @Size(max = 14)
    @Column(name = "spr_kon_nip", length = 14)
    private String sprKonNip;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "spr_kw_zwol", precision = 13, scale = 2)
    private BigDecimal sprKwZwol;
    @Column(name = "spr_bezr_kw_br", precision = 13, scale = 2)
    private BigDecimal sprBezrKwBr;
    @Column(name = "spr_kw_vat_1", precision = 13, scale = 2)
    private BigDecimal sprKwVat1;
    @Column(name = "spr_st_vat_1", precision = 5, scale = 2)
    private BigDecimal sprStVat1;
    @Column(name = "spr_kw_vat_2", precision = 13, scale = 2)
    private BigDecimal sprKwVat2;
    @Column(name = "spr_st_vat_2", precision = 5, scale = 2)
    private BigDecimal sprStVat2;
    @Column(name = "spr_kw_vat_3", precision = 13, scale = 2)
    private BigDecimal sprKwVat3;
    @Column(name = "spr_st_vat_3", precision = 5, scale = 2)
    private BigDecimal sprStVat3;
    @Column(name = "spr_kw_vat_0_eksp", precision = 13, scale = 2)
    private BigDecimal sprKwVat0Eksp;
    @Column(name = "spr_kw_vat_0_kraj", precision = 13, scale = 2)
    private BigDecimal sprKwVat0Kraj;
    @Column(name = "spr_kw_art8", precision = 13, scale = 2)
    private BigDecimal sprKwArt8;
    @Column(name = "spr_vat_art8", precision = 13, scale = 2)
    private BigDecimal sprVatArt8;
    @Column(name = "spr_kw_bez_vat", precision = 13, scale = 2)
    private BigDecimal sprKwBezVat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "spr_uwagi", nullable = false, length = 128)
    private String sprUwagi;
    @Column(name = "spr_skreslony")
    private Character sprSkreslony;
    @Column(name = "spr_czas")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sprCzas;
    @Size(max = 32)
    @Column(name = "spr_usr_userid", length = 32)
    private String sprUsrUserid;
    @Column(name = "spr_czas_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sprCzasMod;
    @Size(max = 32)
    @Column(name = "spr_usr_userid_mod", length = 32)
    private String sprUsrUseridMod;
    @Column(name = "spr_rej_typ")
    private Character sprRejTyp;
    @Column(name = "spr_kol_7", precision = 13, scale = 2)
    private BigDecimal sprKol7;
    @Column(name = "spr_kol_8", precision = 13, scale = 2)
    private BigDecimal sprKol8;
    @Column(name = "spr_korekty")
    private Character sprKorekty;
    @Column(name = "spr_kw_vat_4", precision = 13, scale = 2)
    private BigDecimal sprKwVat4;
    @Column(name = "spr_st_vat_4", precision = 5, scale = 2)
    private BigDecimal sprStVat4;
    @Column(name = "spr_kw_vat", precision = 13, scale = 2)
    private BigDecimal sprKwVat;
    @Column(name = "spr_kw_vat_0_exp2a", precision = 13, scale = 2)
    private BigDecimal sprKwVat0Exp2a;
    @Size(max = 32)
    @Column(name = "spr_for_opis", length = 32)
    private String sprForOpis;
    @Column(name = "spr_dod_num_1", precision = 13, scale = 2)
    private BigDecimal sprDodNum1;
    @Column(name = "spr_dod_num_2", precision = 13, scale = 2)
    private BigDecimal sprDodNum2;
    @Column(name = "spr_dod_num_3", precision = 13, scale = 2)
    private BigDecimal sprDodNum3;
    @Column(name = "spr_dod_num_4", precision = 13, scale = 2)
    private BigDecimal sprDodNum4;
    @Column(name = "spr_dod_char_1")
    private Character sprDodChar1;
    @Column(name = "spr_dod_char_2")
    private Character sprDodChar2;
    @Size(max = 32)
    @Column(name = "spr_dod_vchar_1", length = 32)
    private String sprDodVchar1;
    @Size(max = 32)
    @Column(name = "spr_dod_vchar_2", length = 32)
    private String sprDodVchar2;
    @Column(name = "spr_dod_num_5", precision = 17, scale = 6)
    private BigDecimal sprDodNum5;
    @Column(name = "spr_dod_num_6", precision = 17, scale = 6)
    private BigDecimal sprDodNum6;
    @Column(name = "spr_dod_num_7", precision = 5, scale = 2)
    private BigDecimal sprDodNum7;
    @Column(name = "spr_dod_num_8", precision = 5, scale = 2)
    private BigDecimal sprDodNum8;
    @Column(name = "spr_dod_num_9", precision = 5, scale = 2)
    private BigDecimal sprDodNum9;
    @Column(name = "spr_dod_num_10", precision = 5, scale = 2)
    private BigDecimal sprDodNum10;
    @Column(name = "spr_dod_char_3")
    private Character sprDodChar3;
    @Column(name = "spr_dod_char_4")
    private Character sprDodChar4;
    @Column(name = "spr_dod_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sprDodData1;
    @Column(name = "spr_dod_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sprDodData2;
    @OneToMany(mappedBy = "dsrSprSerial")
    private List<DaneStatR> daneStatRList;
    @JoinColumn(name = "spr_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma sprFirSerial;
    @JoinColumn(name = "spr_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent sprKonSerial;
    @JoinColumn(name = "spr_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres sprOkrSerial;
    @JoinColumn(name = "spr_rej_serial", referencedColumnName = "rej_serial")
    @ManyToOne
    private Rejestr sprRejSerial;
    @JoinColumn(name = "spr_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok sprRokSerial;
    @OneToMany(mappedBy = "spzSprSerial")
    private List<Sprzap> sprzapList;

    public Sprzedaz() {
    }

    public Sprzedaz(Integer sprSerial) {
        this.sprSerial = sprSerial;
    }

    public Sprzedaz(Integer sprSerial, int sprLp, String sprNumer, Date sprDataWyst, Date sprDataSprz, String sprPrzedSprz, String sprUwagi) {
        this.sprSerial = sprSerial;
        this.sprLp = sprLp;
        this.sprNumer = sprNumer;
        this.sprDataWyst = sprDataWyst;
        this.sprDataSprz = sprDataSprz;
        this.sprPrzedSprz = sprPrzedSprz;
        this.sprUwagi = sprUwagi;
    }

    public Integer getSprSerial() {
        return sprSerial;
    }

    public void setSprSerial(Integer sprSerial) {
        this.sprSerial = sprSerial;
    }

    public Short getSprKolKsiegi() {
        return sprKolKsiegi;
    }

    public void setSprKolKsiegi(Short sprKolKsiegi) {
        this.sprKolKsiegi = sprKolKsiegi;
    }

    public int getSprLp() {
        return sprLp;
    }

    public void setSprLp(int sprLp) {
        this.sprLp = sprLp;
    }

    public String getSprNumer() {
        return sprNumer;
    }

    public void setSprNumer(String sprNumer) {
        this.sprNumer = sprNumer;
    }

    public Date getSprDataWyst() {
        return sprDataWyst;
    }

    public void setSprDataWyst(Date sprDataWyst) {
        this.sprDataWyst = sprDataWyst;
    }

    public Date getSprDataSprz() {
        return sprDataSprz;
    }

    public void setSprDataSprz(Date sprDataSprz) {
        this.sprDataSprz = sprDataSprz;
    }

    public String getSprPrzedSprz() {
        return sprPrzedSprz;
    }

    public void setSprPrzedSprz(String sprPrzedSprz) {
        this.sprPrzedSprz = sprPrzedSprz;
    }

    public Character getSprOrygTyp() {
        return sprOrygTyp;
    }

    public void setSprOrygTyp(Character sprOrygTyp) {
        this.sprOrygTyp = sprOrygTyp;
    }

    public Integer getSprOrygSerial() {
        return sprOrygSerial;
    }

    public void setSprOrygSerial(Integer sprOrygSerial) {
        this.sprOrygSerial = sprOrygSerial;
    }

    public String getSprKonNazwaSkr() {
        return sprKonNazwaSkr;
    }

    public void setSprKonNazwaSkr(String sprKonNazwaSkr) {
        this.sprKonNazwaSkr = sprKonNazwaSkr;
    }

    public String getSprKonNazwa() {
        return sprKonNazwa;
    }

    public void setSprKonNazwa(String sprKonNazwa) {
        this.sprKonNazwa = sprKonNazwa;
    }

    public String getSprKonAdres() {
        return sprKonAdres;
    }

    public void setSprKonAdres(String sprKonAdres) {
        this.sprKonAdres = sprKonAdres;
    }

    public String getSprKonNip() {
        return sprKonNip;
    }

    public void setSprKonNip(String sprKonNip) {
        this.sprKonNip = sprKonNip;
    }

    public BigDecimal getSprKwZwol() {
        return sprKwZwol;
    }

    public void setSprKwZwol(BigDecimal sprKwZwol) {
        this.sprKwZwol = sprKwZwol;
    }

    public BigDecimal getSprBezrKwBr() {
        return sprBezrKwBr;
    }

    public void setSprBezrKwBr(BigDecimal sprBezrKwBr) {
        this.sprBezrKwBr = sprBezrKwBr;
    }

    public BigDecimal getSprKwVat1() {
        return sprKwVat1;
    }

    public void setSprKwVat1(BigDecimal sprKwVat1) {
        this.sprKwVat1 = sprKwVat1;
    }

    public BigDecimal getSprStVat1() {
        return sprStVat1;
    }

    public void setSprStVat1(BigDecimal sprStVat1) {
        this.sprStVat1 = sprStVat1;
    }

    public BigDecimal getSprKwVat2() {
        return sprKwVat2;
    }

    public void setSprKwVat2(BigDecimal sprKwVat2) {
        this.sprKwVat2 = sprKwVat2;
    }

    public BigDecimal getSprStVat2() {
        return sprStVat2;
    }

    public void setSprStVat2(BigDecimal sprStVat2) {
        this.sprStVat2 = sprStVat2;
    }

    public BigDecimal getSprKwVat3() {
        return sprKwVat3;
    }

    public void setSprKwVat3(BigDecimal sprKwVat3) {
        this.sprKwVat3 = sprKwVat3;
    }

    public BigDecimal getSprStVat3() {
        return sprStVat3;
    }

    public void setSprStVat3(BigDecimal sprStVat3) {
        this.sprStVat3 = sprStVat3;
    }

    public BigDecimal getSprKwVat0Eksp() {
        return sprKwVat0Eksp;
    }

    public void setSprKwVat0Eksp(BigDecimal sprKwVat0Eksp) {
        this.sprKwVat0Eksp = sprKwVat0Eksp;
    }

    public BigDecimal getSprKwVat0Kraj() {
        return sprKwVat0Kraj;
    }

    public void setSprKwVat0Kraj(BigDecimal sprKwVat0Kraj) {
        this.sprKwVat0Kraj = sprKwVat0Kraj;
    }

    public BigDecimal getSprKwArt8() {
        return sprKwArt8;
    }

    public void setSprKwArt8(BigDecimal sprKwArt8) {
        this.sprKwArt8 = sprKwArt8;
    }

    public BigDecimal getSprVatArt8() {
        return sprVatArt8;
    }

    public void setSprVatArt8(BigDecimal sprVatArt8) {
        this.sprVatArt8 = sprVatArt8;
    }

    public BigDecimal getSprKwBezVat() {
        return sprKwBezVat;
    }

    public void setSprKwBezVat(BigDecimal sprKwBezVat) {
        this.sprKwBezVat = sprKwBezVat;
    }

    public String getSprUwagi() {
        return sprUwagi;
    }

    public void setSprUwagi(String sprUwagi) {
        this.sprUwagi = sprUwagi;
    }

    public Character getSprSkreslony() {
        return sprSkreslony;
    }

    public void setSprSkreslony(Character sprSkreslony) {
        this.sprSkreslony = sprSkreslony;
    }

    public Date getSprCzas() {
        return sprCzas;
    }

    public void setSprCzas(Date sprCzas) {
        this.sprCzas = sprCzas;
    }

    public String getSprUsrUserid() {
        return sprUsrUserid;
    }

    public void setSprUsrUserid(String sprUsrUserid) {
        this.sprUsrUserid = sprUsrUserid;
    }

    public Date getSprCzasMod() {
        return sprCzasMod;
    }

    public void setSprCzasMod(Date sprCzasMod) {
        this.sprCzasMod = sprCzasMod;
    }

    public String getSprUsrUseridMod() {
        return sprUsrUseridMod;
    }

    public void setSprUsrUseridMod(String sprUsrUseridMod) {
        this.sprUsrUseridMod = sprUsrUseridMod;
    }

    public Character getSprRejTyp() {
        return sprRejTyp;
    }

    public void setSprRejTyp(Character sprRejTyp) {
        this.sprRejTyp = sprRejTyp;
    }

    public BigDecimal getSprKol7() {
        return sprKol7;
    }

    public void setSprKol7(BigDecimal sprKol7) {
        this.sprKol7 = sprKol7;
    }

    public BigDecimal getSprKol8() {
        return sprKol8;
    }

    public void setSprKol8(BigDecimal sprKol8) {
        this.sprKol8 = sprKol8;
    }

    public Character getSprKorekty() {
        return sprKorekty;
    }

    public void setSprKorekty(Character sprKorekty) {
        this.sprKorekty = sprKorekty;
    }

    public BigDecimal getSprKwVat4() {
        return sprKwVat4;
    }

    public void setSprKwVat4(BigDecimal sprKwVat4) {
        this.sprKwVat4 = sprKwVat4;
    }

    public BigDecimal getSprStVat4() {
        return sprStVat4;
    }

    public void setSprStVat4(BigDecimal sprStVat4) {
        this.sprStVat4 = sprStVat4;
    }

    public BigDecimal getSprKwVat() {
        return sprKwVat;
    }

    public void setSprKwVat(BigDecimal sprKwVat) {
        this.sprKwVat = sprKwVat;
    }

    public BigDecimal getSprKwVat0Exp2a() {
        return sprKwVat0Exp2a;
    }

    public void setSprKwVat0Exp2a(BigDecimal sprKwVat0Exp2a) {
        this.sprKwVat0Exp2a = sprKwVat0Exp2a;
    }

    public String getSprForOpis() {
        return sprForOpis;
    }

    public void setSprForOpis(String sprForOpis) {
        this.sprForOpis = sprForOpis;
    }

    public BigDecimal getSprDodNum1() {
        return sprDodNum1;
    }

    public void setSprDodNum1(BigDecimal sprDodNum1) {
        this.sprDodNum1 = sprDodNum1;
    }

    public BigDecimal getSprDodNum2() {
        return sprDodNum2;
    }

    public void setSprDodNum2(BigDecimal sprDodNum2) {
        this.sprDodNum2 = sprDodNum2;
    }

    public BigDecimal getSprDodNum3() {
        return sprDodNum3;
    }

    public void setSprDodNum3(BigDecimal sprDodNum3) {
        this.sprDodNum3 = sprDodNum3;
    }

    public BigDecimal getSprDodNum4() {
        return sprDodNum4;
    }

    public void setSprDodNum4(BigDecimal sprDodNum4) {
        this.sprDodNum4 = sprDodNum4;
    }

    public Character getSprDodChar1() {
        return sprDodChar1;
    }

    public void setSprDodChar1(Character sprDodChar1) {
        this.sprDodChar1 = sprDodChar1;
    }

    public Character getSprDodChar2() {
        return sprDodChar2;
    }

    public void setSprDodChar2(Character sprDodChar2) {
        this.sprDodChar2 = sprDodChar2;
    }

    public String getSprDodVchar1() {
        return sprDodVchar1;
    }

    public void setSprDodVchar1(String sprDodVchar1) {
        this.sprDodVchar1 = sprDodVchar1;
    }

    public String getSprDodVchar2() {
        return sprDodVchar2;
    }

    public void setSprDodVchar2(String sprDodVchar2) {
        this.sprDodVchar2 = sprDodVchar2;
    }

    public BigDecimal getSprDodNum5() {
        return sprDodNum5;
    }

    public void setSprDodNum5(BigDecimal sprDodNum5) {
        this.sprDodNum5 = sprDodNum5;
    }

    public BigDecimal getSprDodNum6() {
        return sprDodNum6;
    }

    public void setSprDodNum6(BigDecimal sprDodNum6) {
        this.sprDodNum6 = sprDodNum6;
    }

    public BigDecimal getSprDodNum7() {
        return sprDodNum7;
    }

    public void setSprDodNum7(BigDecimal sprDodNum7) {
        this.sprDodNum7 = sprDodNum7;
    }

    public BigDecimal getSprDodNum8() {
        return sprDodNum8;
    }

    public void setSprDodNum8(BigDecimal sprDodNum8) {
        this.sprDodNum8 = sprDodNum8;
    }

    public BigDecimal getSprDodNum9() {
        return sprDodNum9;
    }

    public void setSprDodNum9(BigDecimal sprDodNum9) {
        this.sprDodNum9 = sprDodNum9;
    }

    public BigDecimal getSprDodNum10() {
        return sprDodNum10;
    }

    public void setSprDodNum10(BigDecimal sprDodNum10) {
        this.sprDodNum10 = sprDodNum10;
    }

    public Character getSprDodChar3() {
        return sprDodChar3;
    }

    public void setSprDodChar3(Character sprDodChar3) {
        this.sprDodChar3 = sprDodChar3;
    }

    public Character getSprDodChar4() {
        return sprDodChar4;
    }

    public void setSprDodChar4(Character sprDodChar4) {
        this.sprDodChar4 = sprDodChar4;
    }

    public Date getSprDodData1() {
        return sprDodData1;
    }

    public void setSprDodData1(Date sprDodData1) {
        this.sprDodData1 = sprDodData1;
    }

    public Date getSprDodData2() {
        return sprDodData2;
    }

    public void setSprDodData2(Date sprDodData2) {
        this.sprDodData2 = sprDodData2;
    }

    @XmlTransient
    public List<DaneStatR> getDaneStatRList() {
        return daneStatRList;
    }

    public void setDaneStatRList(List<DaneStatR> daneStatRList) {
        this.daneStatRList = daneStatRList;
    }

    public Firma getSprFirSerial() {
        return sprFirSerial;
    }

    public void setSprFirSerial(Firma sprFirSerial) {
        this.sprFirSerial = sprFirSerial;
    }

    public Kontrahent getSprKonSerial() {
        return sprKonSerial;
    }

    public void setSprKonSerial(Kontrahent sprKonSerial) {
        this.sprKonSerial = sprKonSerial;
    }

    public Okres getSprOkrSerial() {
        return sprOkrSerial;
    }

    public void setSprOkrSerial(Okres sprOkrSerial) {
        this.sprOkrSerial = sprOkrSerial;
    }

    public Rejestr getSprRejSerial() {
        return sprRejSerial;
    }

    public void setSprRejSerial(Rejestr sprRejSerial) {
        this.sprRejSerial = sprRejSerial;
    }

    public Rok getSprRokSerial() {
        return sprRokSerial;
    }

    public void setSprRokSerial(Rok sprRokSerial) {
        this.sprRokSerial = sprRokSerial;
    }

    @XmlTransient
    public List<Sprzap> getSprzapList() {
        return sprzapList;
    }

    public void setSprzapList(List<Sprzap> sprzapList) {
        this.sprzapList = sprzapList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprSerial != null ? sprSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprzedaz)) {
            return false;
        }
        Sprzedaz other = (Sprzedaz) object;
        if ((this.sprSerial == null && other.sprSerial != null) || (this.sprSerial != null && !this.sprSerial.equals(other.sprSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Sprzedaz[ sprSerial=" + sprSerial + " ]";
    }
    
}
