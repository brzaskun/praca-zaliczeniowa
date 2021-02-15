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
@Table(name = "zakupy", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zakupy.findAll", query = "SELECT z FROM Zakupy z"),
    @NamedQuery(name = "Zakupy.findByZakSerial", query = "SELECT z FROM Zakupy z WHERE z.zakSerial = :zakSerial"),
    @NamedQuery(name = "Zakupy.findByZakLp", query = "SELECT z FROM Zakupy z WHERE z.zakLp = :zakLp"),
    @NamedQuery(name = "Zakupy.findByZakNumer", query = "SELECT z FROM Zakupy z WHERE z.zakNumer = :zakNumer"),
    @NamedQuery(name = "Zakupy.findByZakDataOtrz", query = "SELECT z FROM Zakupy z WHERE z.zakDataOtrz = :zakDataOtrz"),
    @NamedQuery(name = "Zakupy.findByZakDataWyst", query = "SELECT z FROM Zakupy z WHERE z.zakDataWyst = :zakDataWyst"),
    @NamedQuery(name = "Zakupy.findByZakPrzed", query = "SELECT z FROM Zakupy z WHERE z.zakPrzed = :zakPrzed"),
    @NamedQuery(name = "Zakupy.findByZakKonNazwaSkr", query = "SELECT z FROM Zakupy z WHERE z.zakKonNazwaSkr = :zakKonNazwaSkr"),
    @NamedQuery(name = "Zakupy.findByZakKonNazwa", query = "SELECT z FROM Zakupy z WHERE z.zakKonNazwa = :zakKonNazwa"),
    @NamedQuery(name = "Zakupy.findByZakKonAdres", query = "SELECT z FROM Zakupy z WHERE z.zakKonAdres = :zakKonAdres"),
    @NamedQuery(name = "Zakupy.findByZakKonNip", query = "SELECT z FROM Zakupy z WHERE z.zakKonNip = :zakKonNip"),
    @NamedQuery(name = "Zakupy.findByZakZakBezOdl", query = "SELECT z FROM Zakupy z WHERE z.zakZakBezOdl = :zakZakBezOdl"),
    @NamedQuery(name = "Zakupy.findByZakKwSt0", query = "SELECT z FROM Zakupy z WHERE z.zakKwSt0 = :zakKwSt0"),
    @NamedQuery(name = "Zakupy.findByZakOpodNet", query = "SELECT z FROM Zakupy z WHERE z.zakOpodNet = :zakOpodNet"),
    @NamedQuery(name = "Zakupy.findByZakOpodVat", query = "SELECT z FROM Zakupy z WHERE z.zakOpodVat = :zakOpodVat"),
    @NamedQuery(name = "Zakupy.findByZakOpodizwNet", query = "SELECT z FROM Zakupy z WHERE z.zakOpodizwNet = :zakOpodizwNet"),
    @NamedQuery(name = "Zakupy.findByZakOpodizwVat", query = "SELECT z FROM Zakupy z WHERE z.zakOpodizwVat = :zakOpodizwVat"),
    @NamedQuery(name = "Zakupy.findByZakVatOdl", query = "SELECT z FROM Zakupy z WHERE z.zakVatOdl = :zakVatOdl"),
    @NamedQuery(name = "Zakupy.findByZakKol10", query = "SELECT z FROM Zakupy z WHERE z.zakKol10 = :zakKol10"),
    @NamedQuery(name = "Zakupy.findByZakKol11", query = "SELECT z FROM Zakupy z WHERE z.zakKol11 = :zakKol11"),
    @NamedQuery(name = "Zakupy.findByZakKol12", query = "SELECT z FROM Zakupy z WHERE z.zakKol12 = :zakKol12"),
    @NamedQuery(name = "Zakupy.findByZakKol14", query = "SELECT z FROM Zakupy z WHERE z.zakKol14 = :zakKol14"),
    @NamedQuery(name = "Zakupy.findByZakKwBezVat", query = "SELECT z FROM Zakupy z WHERE z.zakKwBezVat = :zakKwBezVat"),
    @NamedQuery(name = "Zakupy.findByZakUwagi", query = "SELECT z FROM Zakupy z WHERE z.zakUwagi = :zakUwagi"),
    @NamedQuery(name = "Zakupy.findByZakSkreslony", query = "SELECT z FROM Zakupy z WHERE z.zakSkreslony = :zakSkreslony"),
    @NamedQuery(name = "Zakupy.findByZakCzas", query = "SELECT z FROM Zakupy z WHERE z.zakCzas = :zakCzas"),
    @NamedQuery(name = "Zakupy.findByZakUsrUserid", query = "SELECT z FROM Zakupy z WHERE z.zakUsrUserid = :zakUsrUserid"),
    @NamedQuery(name = "Zakupy.findByZakCzasMod", query = "SELECT z FROM Zakupy z WHERE z.zakCzasMod = :zakCzasMod"),
    @NamedQuery(name = "Zakupy.findByZakUsrUseridMod", query = "SELECT z FROM Zakupy z WHERE z.zakUsrUseridMod = :zakUsrUseridMod"),
    @NamedQuery(name = "Zakupy.findByZakRejTyp", query = "SELECT z FROM Zakupy z WHERE z.zakRejTyp = :zakRejTyp"),
    @NamedQuery(name = "Zakupy.findByZakKwVat", query = "SELECT z FROM Zakupy z WHERE z.zakKwVat = :zakKwVat"),
    @NamedQuery(name = "Zakupy.findByZakKorekty", query = "SELECT z FROM Zakupy z WHERE z.zakKorekty = :zakKorekty"),
    @NamedQuery(name = "Zakupy.findByZakKol16", query = "SELECT z FROM Zakupy z WHERE z.zakKol16 = :zakKol16"),
    @NamedQuery(name = "Zakupy.findByZakOpodStNet", query = "SELECT z FROM Zakupy z WHERE z.zakOpodStNet = :zakOpodStNet"),
    @NamedQuery(name = "Zakupy.findByZakOpodStVat", query = "SELECT z FROM Zakupy z WHERE z.zakOpodStVat = :zakOpodStVat"),
    @NamedQuery(name = "Zakupy.findByZakOpodizwStNet", query = "SELECT z FROM Zakupy z WHERE z.zakOpodizwStNet = :zakOpodizwStNet"),
    @NamedQuery(name = "Zakupy.findByZakOpodizwStVat", query = "SELECT z FROM Zakupy z WHERE z.zakOpodizwStVat = :zakOpodizwStVat"),
    @NamedQuery(name = "Zakupy.findByZakForOpis", query = "SELECT z FROM Zakupy z WHERE z.zakForOpis = :zakForOpis"),
    @NamedQuery(name = "Zakupy.findByZakDodNum1", query = "SELECT z FROM Zakupy z WHERE z.zakDodNum1 = :zakDodNum1"),
    @NamedQuery(name = "Zakupy.findByZakDodNum2", query = "SELECT z FROM Zakupy z WHERE z.zakDodNum2 = :zakDodNum2"),
    @NamedQuery(name = "Zakupy.findByZakDodNum3", query = "SELECT z FROM Zakupy z WHERE z.zakDodNum3 = :zakDodNum3"),
    @NamedQuery(name = "Zakupy.findByZakDodNum4", query = "SELECT z FROM Zakupy z WHERE z.zakDodNum4 = :zakDodNum4"),
    @NamedQuery(name = "Zakupy.findByZakDodChar1", query = "SELECT z FROM Zakupy z WHERE z.zakDodChar1 = :zakDodChar1"),
    @NamedQuery(name = "Zakupy.findByZakDodChar2", query = "SELECT z FROM Zakupy z WHERE z.zakDodChar2 = :zakDodChar2"),
    @NamedQuery(name = "Zakupy.findByZakDodVchar1", query = "SELECT z FROM Zakupy z WHERE z.zakDodVchar1 = :zakDodVchar1"),
    @NamedQuery(name = "Zakupy.findByZakDodVchar2", query = "SELECT z FROM Zakupy z WHERE z.zakDodVchar2 = :zakDodVchar2"),
    @NamedQuery(name = "Zakupy.findByZakDodChar3", query = "SELECT z FROM Zakupy z WHERE z.zakDodChar3 = :zakDodChar3"),
    @NamedQuery(name = "Zakupy.findByZakDodChar4", query = "SELECT z FROM Zakupy z WHERE z.zakDodChar4 = :zakDodChar4"),
    @NamedQuery(name = "Zakupy.findByZakDodNum5", query = "SELECT z FROM Zakupy z WHERE z.zakDodNum5 = :zakDodNum5"),
    @NamedQuery(name = "Zakupy.findByZakDodNum6", query = "SELECT z FROM Zakupy z WHERE z.zakDodNum6 = :zakDodNum6"),
    @NamedQuery(name = "Zakupy.findByZakDodNum7", query = "SELECT z FROM Zakupy z WHERE z.zakDodNum7 = :zakDodNum7"),
    @NamedQuery(name = "Zakupy.findByZakDodNum8", query = "SELECT z FROM Zakupy z WHERE z.zakDodNum8 = :zakDodNum8"),
    @NamedQuery(name = "Zakupy.findByZakDodNum9", query = "SELECT z FROM Zakupy z WHERE z.zakDodNum9 = :zakDodNum9"),
    @NamedQuery(name = "Zakupy.findByZakDodNum10", query = "SELECT z FROM Zakupy z WHERE z.zakDodNum10 = :zakDodNum10"),
    @NamedQuery(name = "Zakupy.findByZakDodData1", query = "SELECT z FROM Zakupy z WHERE z.zakDodData1 = :zakDodData1"),
    @NamedQuery(name = "Zakupy.findByZakDodData2", query = "SELECT z FROM Zakupy z WHERE z.zakDodData2 = :zakDodData2")})
public class Zakupy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "zak_serial", nullable = false)
    private Integer zakSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zak_lp", nullable = false)
    private int zakLp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "zak_numer", nullable = false, length = 32)
    private String zakNumer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zak_data_otrz", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date zakDataOtrz;
    @Column(name = "zak_data_wyst")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zakDataWyst;
    @Size(max = 64)
    @Column(name = "zak_przed", length = 64)
    private String zakPrzed;
    @Size(max = 32)
    @Column(name = "zak_kon_nazwa_skr", length = 32)
    private String zakKonNazwaSkr;
    @Size(max = 64)
    @Column(name = "zak_kon_nazwa", length = 64)
    private String zakKonNazwa;
    @Size(max = 128)
    @Column(name = "zak_kon_adres", length = 128)
    private String zakKonAdres;
    @Size(max = 14)
    @Column(name = "zak_kon_nip", length = 14)
    private String zakKonNip;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "zak_zak_bez_odl", precision = 13, scale = 2)
    private BigDecimal zakZakBezOdl;
    @Column(name = "zak_kw_st_0", precision = 13, scale = 2)
    private BigDecimal zakKwSt0;
    @Column(name = "zak_opod_net", precision = 13, scale = 2)
    private BigDecimal zakOpodNet;
    @Column(name = "zak_opod_vat", precision = 13, scale = 2)
    private BigDecimal zakOpodVat;
    @Column(name = "zak_opodizw_net", precision = 13, scale = 2)
    private BigDecimal zakOpodizwNet;
    @Column(name = "zak_opodizw_vat", precision = 13, scale = 2)
    private BigDecimal zakOpodizwVat;
    @Column(name = "zak_vat_odl", precision = 13, scale = 2)
    private BigDecimal zakVatOdl;
    @Column(name = "zak_kol_10", precision = 13, scale = 2)
    private BigDecimal zakKol10;
    @Column(name = "zak_kol_11", precision = 13, scale = 2)
    private BigDecimal zakKol11;
    @Column(name = "zak_kol_12", precision = 13, scale = 2)
    private BigDecimal zakKol12;
    @Column(name = "zak_kol_14", precision = 13, scale = 2)
    private BigDecimal zakKol14;
    @Column(name = "zak_kw_bez_vat", precision = 13, scale = 2)
    private BigDecimal zakKwBezVat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "zak_uwagi", nullable = false, length = 128)
    private String zakUwagi;
    @Column(name = "zak_skreslony")
    private Character zakSkreslony;
    @Column(name = "zak_czas")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zakCzas;
    @Size(max = 32)
    @Column(name = "zak_usr_userid", length = 32)
    private String zakUsrUserid;
    @Column(name = "zak_czas_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zakCzasMod;
    @Size(max = 32)
    @Column(name = "zak_usr_userid_mod", length = 32)
    private String zakUsrUseridMod;
    @Column(name = "zak_rej_typ")
    private Character zakRejTyp;
    @Column(name = "zak_kw_vat", precision = 13, scale = 2)
    private BigDecimal zakKwVat;
    @Column(name = "zak_korekty")
    private Character zakKorekty;
    @Column(name = "zak_kol_16", precision = 13, scale = 2)
    private BigDecimal zakKol16;
    @Column(name = "zak_opod_st_net", precision = 13, scale = 2)
    private BigDecimal zakOpodStNet;
    @Column(name = "zak_opod_st_vat", precision = 13, scale = 2)
    private BigDecimal zakOpodStVat;
    @Column(name = "zak_opodizw_st_net", precision = 13, scale = 2)
    private BigDecimal zakOpodizwStNet;
    @Column(name = "zak_opodizw_st_vat", precision = 13, scale = 2)
    private BigDecimal zakOpodizwStVat;
    @Size(max = 32)
    @Column(name = "zak_for_opis", length = 32)
    private String zakForOpis;
    @Column(name = "zak_dod_num_1", precision = 13, scale = 2)
    private BigDecimal zakDodNum1;
    @Column(name = "zak_dod_num_2", precision = 13, scale = 2)
    private BigDecimal zakDodNum2;
    @Column(name = "zak_dod_num_3", precision = 13, scale = 2)
    private BigDecimal zakDodNum3;
    @Column(name = "zak_dod_num_4", precision = 13, scale = 2)
    private BigDecimal zakDodNum4;
    @Column(name = "zak_dod_char_1")
    private Character zakDodChar1;
    @Column(name = "zak_dod_char_2")
    private Character zakDodChar2;
    @Size(max = 32)
    @Column(name = "zak_dod_vchar_1", length = 32)
    private String zakDodVchar1;
    @Size(max = 32)
    @Column(name = "zak_dod_vchar_2", length = 32)
    private String zakDodVchar2;
    @Column(name = "zak_dod_char_3")
    private Character zakDodChar3;
    @Column(name = "zak_dod_char_4")
    private Character zakDodChar4;
    @Column(name = "zak_dod_num_5", precision = 17, scale = 6)
    private BigDecimal zakDodNum5;
    @Column(name = "zak_dod_num_6", precision = 17, scale = 6)
    private BigDecimal zakDodNum6;
    @Column(name = "zak_dod_num_7", precision = 5, scale = 2)
    private BigDecimal zakDodNum7;
    @Column(name = "zak_dod_num_8", precision = 5, scale = 2)
    private BigDecimal zakDodNum8;
    @Column(name = "zak_dod_num_9", precision = 5, scale = 2)
    private BigDecimal zakDodNum9;
    @Column(name = "zak_dod_num_10", precision = 5, scale = 2)
    private BigDecimal zakDodNum10;
    @Column(name = "zak_dod_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zakDodData1;
    @Column(name = "zak_dod_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zakDodData2;
    @JoinColumn(name = "zak_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma zakFirSerial;
    @JoinColumn(name = "zak_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent zakKonSerial;
    @JoinColumn(name = "zak_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres zakOkrSerial;
    @JoinColumn(name = "zak_rej_serial", referencedColumnName = "rej_serial")
    @ManyToOne
    private Rejestr zakRejSerial;
    @JoinColumn(name = "zak_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok zakRokSerial;
    @OneToMany(mappedBy = "zazZakSerial")
    private List<Zakzap> zakzapList;
    @OneToMany(mappedBy = "dsrZakSerial")
    private List<DaneStatR> daneStatRList;

    public Zakupy() {
    }

    public Zakupy(Integer zakSerial) {
        this.zakSerial = zakSerial;
    }

    public Zakupy(Integer zakSerial, int zakLp, String zakNumer, Date zakDataOtrz, String zakUwagi) {
        this.zakSerial = zakSerial;
        this.zakLp = zakLp;
        this.zakNumer = zakNumer;
        this.zakDataOtrz = zakDataOtrz;
        this.zakUwagi = zakUwagi;
    }

    public Integer getZakSerial() {
        return zakSerial;
    }

    public void setZakSerial(Integer zakSerial) {
        this.zakSerial = zakSerial;
    }

    public int getZakLp() {
        return zakLp;
    }

    public void setZakLp(int zakLp) {
        this.zakLp = zakLp;
    }

    public String getZakNumer() {
        return zakNumer;
    }

    public void setZakNumer(String zakNumer) {
        this.zakNumer = zakNumer;
    }

    public Date getZakDataOtrz() {
        return zakDataOtrz;
    }

    public void setZakDataOtrz(Date zakDataOtrz) {
        this.zakDataOtrz = zakDataOtrz;
    }

    public Date getZakDataWyst() {
        return zakDataWyst;
    }

    public void setZakDataWyst(Date zakDataWyst) {
        this.zakDataWyst = zakDataWyst;
    }

    public String getZakPrzed() {
        return zakPrzed;
    }

    public void setZakPrzed(String zakPrzed) {
        this.zakPrzed = zakPrzed;
    }

    public String getZakKonNazwaSkr() {
        return zakKonNazwaSkr;
    }

    public void setZakKonNazwaSkr(String zakKonNazwaSkr) {
        this.zakKonNazwaSkr = zakKonNazwaSkr;
    }

    public String getZakKonNazwa() {
        return zakKonNazwa;
    }

    public void setZakKonNazwa(String zakKonNazwa) {
        this.zakKonNazwa = zakKonNazwa;
    }

    public String getZakKonAdres() {
        return zakKonAdres;
    }

    public void setZakKonAdres(String zakKonAdres) {
        this.zakKonAdres = zakKonAdres;
    }

    public String getZakKonNip() {
        return zakKonNip;
    }

    public void setZakKonNip(String zakKonNip) {
        this.zakKonNip = zakKonNip;
    }

    public BigDecimal getZakZakBezOdl() {
        return zakZakBezOdl;
    }

    public void setZakZakBezOdl(BigDecimal zakZakBezOdl) {
        this.zakZakBezOdl = zakZakBezOdl;
    }

    public BigDecimal getZakKwSt0() {
        return zakKwSt0;
    }

    public void setZakKwSt0(BigDecimal zakKwSt0) {
        this.zakKwSt0 = zakKwSt0;
    }

    public BigDecimal getZakOpodNet() {
        return zakOpodNet;
    }

    public void setZakOpodNet(BigDecimal zakOpodNet) {
        this.zakOpodNet = zakOpodNet;
    }

    public BigDecimal getZakOpodVat() {
        return zakOpodVat;
    }

    public void setZakOpodVat(BigDecimal zakOpodVat) {
        this.zakOpodVat = zakOpodVat;
    }

    public BigDecimal getZakOpodizwNet() {
        return zakOpodizwNet;
    }

    public void setZakOpodizwNet(BigDecimal zakOpodizwNet) {
        this.zakOpodizwNet = zakOpodizwNet;
    }

    public BigDecimal getZakOpodizwVat() {
        return zakOpodizwVat;
    }

    public void setZakOpodizwVat(BigDecimal zakOpodizwVat) {
        this.zakOpodizwVat = zakOpodizwVat;
    }

    public BigDecimal getZakVatOdl() {
        return zakVatOdl;
    }

    public void setZakVatOdl(BigDecimal zakVatOdl) {
        this.zakVatOdl = zakVatOdl;
    }

    public BigDecimal getZakKol10() {
        return zakKol10;
    }

    public void setZakKol10(BigDecimal zakKol10) {
        this.zakKol10 = zakKol10;
    }

    public BigDecimal getZakKol11() {
        return zakKol11;
    }

    public void setZakKol11(BigDecimal zakKol11) {
        this.zakKol11 = zakKol11;
    }

    public BigDecimal getZakKol12() {
        return zakKol12;
    }

    public void setZakKol12(BigDecimal zakKol12) {
        this.zakKol12 = zakKol12;
    }

    public BigDecimal getZakKol14() {
        return zakKol14;
    }

    public void setZakKol14(BigDecimal zakKol14) {
        this.zakKol14 = zakKol14;
    }

    public BigDecimal getZakKwBezVat() {
        return zakKwBezVat;
    }

    public void setZakKwBezVat(BigDecimal zakKwBezVat) {
        this.zakKwBezVat = zakKwBezVat;
    }

    public String getZakUwagi() {
        return zakUwagi;
    }

    public void setZakUwagi(String zakUwagi) {
        this.zakUwagi = zakUwagi;
    }

    public Character getZakSkreslony() {
        return zakSkreslony;
    }

    public void setZakSkreslony(Character zakSkreslony) {
        this.zakSkreslony = zakSkreslony;
    }

    public Date getZakCzas() {
        return zakCzas;
    }

    public void setZakCzas(Date zakCzas) {
        this.zakCzas = zakCzas;
    }

    public String getZakUsrUserid() {
        return zakUsrUserid;
    }

    public void setZakUsrUserid(String zakUsrUserid) {
        this.zakUsrUserid = zakUsrUserid;
    }

    public Date getZakCzasMod() {
        return zakCzasMod;
    }

    public void setZakCzasMod(Date zakCzasMod) {
        this.zakCzasMod = zakCzasMod;
    }

    public String getZakUsrUseridMod() {
        return zakUsrUseridMod;
    }

    public void setZakUsrUseridMod(String zakUsrUseridMod) {
        this.zakUsrUseridMod = zakUsrUseridMod;
    }

    public Character getZakRejTyp() {
        return zakRejTyp;
    }

    public void setZakRejTyp(Character zakRejTyp) {
        this.zakRejTyp = zakRejTyp;
    }

    public BigDecimal getZakKwVat() {
        return zakKwVat;
    }

    public void setZakKwVat(BigDecimal zakKwVat) {
        this.zakKwVat = zakKwVat;
    }

    public Character getZakKorekty() {
        return zakKorekty;
    }

    public void setZakKorekty(Character zakKorekty) {
        this.zakKorekty = zakKorekty;
    }

    public BigDecimal getZakKol16() {
        return zakKol16;
    }

    public void setZakKol16(BigDecimal zakKol16) {
        this.zakKol16 = zakKol16;
    }

    public BigDecimal getZakOpodStNet() {
        return zakOpodStNet;
    }

    public void setZakOpodStNet(BigDecimal zakOpodStNet) {
        this.zakOpodStNet = zakOpodStNet;
    }

    public BigDecimal getZakOpodStVat() {
        return zakOpodStVat;
    }

    public void setZakOpodStVat(BigDecimal zakOpodStVat) {
        this.zakOpodStVat = zakOpodStVat;
    }

    public BigDecimal getZakOpodizwStNet() {
        return zakOpodizwStNet;
    }

    public void setZakOpodizwStNet(BigDecimal zakOpodizwStNet) {
        this.zakOpodizwStNet = zakOpodizwStNet;
    }

    public BigDecimal getZakOpodizwStVat() {
        return zakOpodizwStVat;
    }

    public void setZakOpodizwStVat(BigDecimal zakOpodizwStVat) {
        this.zakOpodizwStVat = zakOpodizwStVat;
    }

    public String getZakForOpis() {
        return zakForOpis;
    }

    public void setZakForOpis(String zakForOpis) {
        this.zakForOpis = zakForOpis;
    }

    public BigDecimal getZakDodNum1() {
        return zakDodNum1;
    }

    public void setZakDodNum1(BigDecimal zakDodNum1) {
        this.zakDodNum1 = zakDodNum1;
    }

    public BigDecimal getZakDodNum2() {
        return zakDodNum2;
    }

    public void setZakDodNum2(BigDecimal zakDodNum2) {
        this.zakDodNum2 = zakDodNum2;
    }

    public BigDecimal getZakDodNum3() {
        return zakDodNum3;
    }

    public void setZakDodNum3(BigDecimal zakDodNum3) {
        this.zakDodNum3 = zakDodNum3;
    }

    public BigDecimal getZakDodNum4() {
        return zakDodNum4;
    }

    public void setZakDodNum4(BigDecimal zakDodNum4) {
        this.zakDodNum4 = zakDodNum4;
    }

    public Character getZakDodChar1() {
        return zakDodChar1;
    }

    public void setZakDodChar1(Character zakDodChar1) {
        this.zakDodChar1 = zakDodChar1;
    }

    public Character getZakDodChar2() {
        return zakDodChar2;
    }

    public void setZakDodChar2(Character zakDodChar2) {
        this.zakDodChar2 = zakDodChar2;
    }

    public String getZakDodVchar1() {
        return zakDodVchar1;
    }

    public void setZakDodVchar1(String zakDodVchar1) {
        this.zakDodVchar1 = zakDodVchar1;
    }

    public String getZakDodVchar2() {
        return zakDodVchar2;
    }

    public void setZakDodVchar2(String zakDodVchar2) {
        this.zakDodVchar2 = zakDodVchar2;
    }

    public Character getZakDodChar3() {
        return zakDodChar3;
    }

    public void setZakDodChar3(Character zakDodChar3) {
        this.zakDodChar3 = zakDodChar3;
    }

    public Character getZakDodChar4() {
        return zakDodChar4;
    }

    public void setZakDodChar4(Character zakDodChar4) {
        this.zakDodChar4 = zakDodChar4;
    }

    public BigDecimal getZakDodNum5() {
        return zakDodNum5;
    }

    public void setZakDodNum5(BigDecimal zakDodNum5) {
        this.zakDodNum5 = zakDodNum5;
    }

    public BigDecimal getZakDodNum6() {
        return zakDodNum6;
    }

    public void setZakDodNum6(BigDecimal zakDodNum6) {
        this.zakDodNum6 = zakDodNum6;
    }

    public BigDecimal getZakDodNum7() {
        return zakDodNum7;
    }

    public void setZakDodNum7(BigDecimal zakDodNum7) {
        this.zakDodNum7 = zakDodNum7;
    }

    public BigDecimal getZakDodNum8() {
        return zakDodNum8;
    }

    public void setZakDodNum8(BigDecimal zakDodNum8) {
        this.zakDodNum8 = zakDodNum8;
    }

    public BigDecimal getZakDodNum9() {
        return zakDodNum9;
    }

    public void setZakDodNum9(BigDecimal zakDodNum9) {
        this.zakDodNum9 = zakDodNum9;
    }

    public BigDecimal getZakDodNum10() {
        return zakDodNum10;
    }

    public void setZakDodNum10(BigDecimal zakDodNum10) {
        this.zakDodNum10 = zakDodNum10;
    }

    public Date getZakDodData1() {
        return zakDodData1;
    }

    public void setZakDodData1(Date zakDodData1) {
        this.zakDodData1 = zakDodData1;
    }

    public Date getZakDodData2() {
        return zakDodData2;
    }

    public void setZakDodData2(Date zakDodData2) {
        this.zakDodData2 = zakDodData2;
    }

    public Firma getZakFirSerial() {
        return zakFirSerial;
    }

    public void setZakFirSerial(Firma zakFirSerial) {
        this.zakFirSerial = zakFirSerial;
    }

    public Kontrahent getZakKonSerial() {
        return zakKonSerial;
    }

    public void setZakKonSerial(Kontrahent zakKonSerial) {
        this.zakKonSerial = zakKonSerial;
    }

    public Okres getZakOkrSerial() {
        return zakOkrSerial;
    }

    public void setZakOkrSerial(Okres zakOkrSerial) {
        this.zakOkrSerial = zakOkrSerial;
    }

    public Rejestr getZakRejSerial() {
        return zakRejSerial;
    }

    public void setZakRejSerial(Rejestr zakRejSerial) {
        this.zakRejSerial = zakRejSerial;
    }

    public Rok getZakRokSerial() {
        return zakRokSerial;
    }

    public void setZakRokSerial(Rok zakRokSerial) {
        this.zakRokSerial = zakRokSerial;
    }

    @XmlTransient
    public List<Zakzap> getZakzapList() {
        return zakzapList;
    }

    public void setZakzapList(List<Zakzap> zakzapList) {
        this.zakzapList = zakzapList;
    }

    @XmlTransient
    public List<DaneStatR> getDaneStatRList() {
        return daneStatRList;
    }

    public void setDaneStatRList(List<DaneStatR> daneStatRList) {
        this.daneStatRList = daneStatRList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zakSerial != null ? zakSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zakupy)) {
            return false;
        }
        Zakupy other = (Zakupy) object;
        if ((this.zakSerial == null && other.zakSerial != null) || (this.zakSerial != null && !this.zakSerial.equals(other.zakSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Zakupy[ zakSerial=" + zakSerial + " ]";
    }
    
}
