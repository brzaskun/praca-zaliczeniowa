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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "fakrach", catalog = "kadryiplace", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fak_fir_serial", "fak_dok_typ", "fak_numer"}),
    @UniqueConstraint(columnNames = {"fak_typ_wsp_num", "fak_rok_serial", "fak_lp"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fakrach.findAll", query = "SELECT f FROM Fakrach f"),
    @NamedQuery(name = "Fakrach.findByFakSerial", query = "SELECT f FROM Fakrach f WHERE f.fakSerial = :fakSerial"),
    @NamedQuery(name = "Fakrach.findByFakLp", query = "SELECT f FROM Fakrach f WHERE f.fakLp = :fakLp"),
    @NamedQuery(name = "Fakrach.findByFakNumer", query = "SELECT f FROM Fakrach f WHERE f.fakNumer = :fakNumer"),
    @NamedQuery(name = "Fakrach.findByFakDataWyst", query = "SELECT f FROM Fakrach f WHERE f.fakDataWyst = :fakDataWyst"),
    @NamedQuery(name = "Fakrach.findByFakMiaSerial", query = "SELECT f FROM Fakrach f WHERE f.fakMiaSerial = :fakMiaSerial"),
    @NamedQuery(name = "Fakrach.findByFakDataSprz", query = "SELECT f FROM Fakrach f WHERE f.fakDataSprz = :fakDataSprz"),
    @NamedQuery(name = "Fakrach.findByFakDataPlat", query = "SELECT f FROM Fakrach f WHERE f.fakDataPlat = :fakDataPlat"),
    @NamedQuery(name = "Fakrach.findByFakUwagi", query = "SELECT f FROM Fakrach f WHERE f.fakUwagi = :fakUwagi"),
    @NamedQuery(name = "Fakrach.findByFakCzas", query = "SELECT f FROM Fakrach f WHERE f.fakCzas = :fakCzas"),
    @NamedQuery(name = "Fakrach.findByFakUsrUserid", query = "SELECT f FROM Fakrach f WHERE f.fakUsrUserid = :fakUsrUserid"),
    @NamedQuery(name = "Fakrach.findByFakRokSerial", query = "SELECT f FROM Fakrach f WHERE f.fakRokSerial = :fakRokSerial"),
    @NamedQuery(name = "Fakrach.findByFakDokTyp", query = "SELECT f FROM Fakrach f WHERE f.fakDokTyp = :fakDokTyp"),
    @NamedQuery(name = "Fakrach.findByFakKorygujaca", query = "SELECT f FROM Fakrach f WHERE f.fakKorygujaca = :fakKorygujaca"),
    @NamedQuery(name = "Fakrach.findByFakKorygowana", query = "SELECT f FROM Fakrach f WHERE f.fakKorygowana = :fakKorygowana"),
    @NamedQuery(name = "Fakrach.findByFakTypWspNum", query = "SELECT f FROM Fakrach f WHERE f.fakTypWspNum = :fakTypWspNum"),
    @NamedQuery(name = "Fakrach.findByFakKorygowanaS", query = "SELECT f FROM Fakrach f WHERE f.fakKorygowanaS = :fakKorygowanaS"),
    @NamedQuery(name = "Fakrach.findByFakNumerMag", query = "SELECT f FROM Fakrach f WHERE f.fakNumerMag = :fakNumerMag"),
    @NamedQuery(name = "Fakrach.findByFakKorygujacaS", query = "SELECT f FROM Fakrach f WHERE f.fakKorygujacaS = :fakKorygujacaS"),
    @NamedQuery(name = "Fakrach.findByFakDokMag", query = "SELECT f FROM Fakrach f WHERE f.fakDokMag = :fakDokMag"),
    @NamedQuery(name = "Fakrach.findByFakKonNazwa", query = "SELECT f FROM Fakrach f WHERE f.fakKonNazwa = :fakKonNazwa"),
    @NamedQuery(name = "Fakrach.findByFakKonAdres", query = "SELECT f FROM Fakrach f WHERE f.fakKonAdres = :fakKonAdres"),
    @NamedQuery(name = "Fakrach.findByFakKonRegon", query = "SELECT f FROM Fakrach f WHERE f.fakKonRegon = :fakKonRegon"),
    @NamedQuery(name = "Fakrach.findByFakKonNip", query = "SELECT f FROM Fakrach f WHERE f.fakKonNip = :fakKonNip"),
    @NamedQuery(name = "Fakrach.findByFakForOpis", query = "SELECT f FROM Fakrach f WHERE f.fakForOpis = :fakForOpis"),
    @NamedQuery(name = "Fakrach.findByFakKonNazwaSkr", query = "SELECT f FROM Fakrach f WHERE f.fakKonNazwaSkr = :fakKonNazwaSkr"),
    @NamedQuery(name = "Fakrach.findByFakMiaNazwa", query = "SELECT f FROM Fakrach f WHERE f.fakMiaNazwa = :fakMiaNazwa"),
    @NamedQuery(name = "Fakrach.findByFakKwota", query = "SELECT f FROM Fakrach f WHERE f.fakKwota = :fakKwota"),
    @NamedQuery(name = "Fakrach.findByFakUserName", query = "SELECT f FROM Fakrach f WHERE f.fakUserName = :fakUserName"),
    @NamedQuery(name = "Fakrach.findByFakUsrKasjer", query = "SELECT f FROM Fakrach f WHERE f.fakUsrKasjer = :fakUsrKasjer"),
    @NamedQuery(name = "Fakrach.findByFakKonNrId", query = "SELECT f FROM Fakrach f WHERE f.fakKonNrId = :fakKonNrId"),
    @NamedQuery(name = "Fakrach.findByFakChar1", query = "SELECT f FROM Fakrach f WHERE f.fakChar1 = :fakChar1"),
    @NamedQuery(name = "Fakrach.findByFakVchar1", query = "SELECT f FROM Fakrach f WHERE f.fakVchar1 = :fakVchar1"),
    @NamedQuery(name = "Fakrach.findByFakDate1", query = "SELECT f FROM Fakrach f WHERE f.fakDate1 = :fakDate1"),
    @NamedQuery(name = "Fakrach.findByFakInt1", query = "SELECT f FROM Fakrach f WHERE f.fakInt1 = :fakInt1"),
    @NamedQuery(name = "Fakrach.findByFakNum1", query = "SELECT f FROM Fakrach f WHERE f.fakNum1 = :fakNum1"),
    @NamedQuery(name = "Fakrach.findByFakNum2", query = "SELECT f FROM Fakrach f WHERE f.fakNum2 = :fakNum2"),
    @NamedQuery(name = "Fakrach.findByFakNum3", query = "SELECT f FROM Fakrach f WHERE f.fakNum3 = :fakNum3"),
    @NamedQuery(name = "Fakrach.findByFakNum4", query = "SELECT f FROM Fakrach f WHERE f.fakNum4 = :fakNum4"),
    @NamedQuery(name = "Fakrach.findByFakChar2", query = "SELECT f FROM Fakrach f WHERE f.fakChar2 = :fakChar2"),
    @NamedQuery(name = "Fakrach.findByFakSposobRej", query = "SELECT f FROM Fakrach f WHERE f.fakSposobRej = :fakSposobRej"),
    @NamedQuery(name = "Fakrach.findByFakKonPesel", query = "SELECT f FROM Fakrach f WHERE f.fakKonPesel = :fakKonPesel")})
public class Fakrach implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "fak_serial", nullable = false)
    private Integer fakSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fak_lp", nullable = false)
    private int fakLp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "fak_numer", nullable = false, length = 32)
    private String fakNumer;
    @Column(name = "fak_data_wyst")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fakDataWyst;
    @Column(name = "fak_mia_serial")
    private Integer fakMiaSerial;
    @Column(name = "fak_data_sprz")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fakDataSprz;
    @Column(name = "fak_data_plat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fakDataPlat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "fak_uwagi", nullable = false, length = 128)
    private String fakUwagi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fak_czas", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fakCzas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "fak_usr_userid", nullable = false, length = 32)
    private String fakUsrUserid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fak_rok_serial", nullable = false)
    private int fakRokSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fak_dok_typ", nullable = false)
    private Character fakDokTyp;
    @Size(max = 32)
    @Column(name = "fak_korygujaca", length = 32)
    private String fakKorygujaca;
    @Size(max = 32)
    @Column(name = "fak_korygowana", length = 32)
    private String fakKorygowana;
    @Column(name = "fak_typ_wsp_num")
    private Character fakTypWspNum;
    @Column(name = "fak_korygowana_s")
    private Integer fakKorygowanaS;
    @Size(max = 32)
    @Column(name = "fak_numer_mag", length = 32)
    private String fakNumerMag;
    @Column(name = "fak_korygujaca_s")
    private Integer fakKorygujacaS;
    @Column(name = "fak_dok_mag")
    private Character fakDokMag;
    @Size(max = 64)
    @Column(name = "fak_kon_nazwa", length = 64)
    private String fakKonNazwa;
    @Size(max = 128)
    @Column(name = "fak_kon_adres", length = 128)
    private String fakKonAdres;
    @Size(max = 16)
    @Column(name = "fak_kon_regon", length = 16)
    private String fakKonRegon;
    @Size(max = 16)
    @Column(name = "fak_kon_nip", length = 16)
    private String fakKonNip;
    @Size(max = 32)
    @Column(name = "fak_for_opis", length = 32)
    private String fakForOpis;
    @Size(max = 32)
    @Column(name = "fak_kon_nazwa_skr", length = 32)
    private String fakKonNazwaSkr;
    @Size(max = 48)
    @Column(name = "fak_mia_nazwa", length = 48)
    private String fakMiaNazwa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fak_kwota", precision = 13, scale = 2)
    private BigDecimal fakKwota;
    @Size(max = 65)
    @Column(name = "fak_user_name", length = 65)
    private String fakUserName;
    @Size(max = 16)
    @Column(name = "fak_usr_kasjer", length = 16)
    private String fakUsrKasjer;
    @Column(name = "fak_kon_nr_id")
    private Integer fakKonNrId;
    @Column(name = "fak_char_1")
    private Character fakChar1;
    @Size(max = 64)
    @Column(name = "fak_vchar_1", length = 64)
    private String fakVchar1;
    @Column(name = "fak_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fakDate1;
    @Column(name = "fak_int_1")
    private Integer fakInt1;
    @Column(name = "fak_num_1", precision = 17, scale = 6)
    private BigDecimal fakNum1;
    @Column(name = "fak_num_2", precision = 17, scale = 6)
    private BigDecimal fakNum2;
    @Column(name = "fak_num_3", precision = 5, scale = 2)
    private BigDecimal fakNum3;
    @Column(name = "fak_num_4", precision = 13, scale = 2)
    private BigDecimal fakNum4;
    @Column(name = "fak_char_2")
    private Character fakChar2;
    @Column(name = "fak_sposob_rej")
    private Character fakSposobRej;
    @Size(max = 16)
    @Column(name = "fak_kon_pesel", length = 16)
    private String fakKonPesel;
    @OneToMany(mappedBy = "freFakSerial")
    private List<FakrachE> fakrachEList;
    @OneToMany(mappedBy = "dsfFakSerial")
    private List<DaneStatF> daneStatFList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pozFakSerial")
    private List<Pozycja> pozycjaList;
    @JoinColumn(name = "fak_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma fakFirSerial;
    @JoinColumn(name = "fak_for_serial", referencedColumnName = "for_serial")
    @ManyToOne
    private Formyzap fakForSerial;
    @JoinColumn(name = "fak_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent fakKonSerial;
    @JoinColumn(name = "fak_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres fakOkrSerial;
    @JoinColumn(name = "fak_par_serial", referencedColumnName = "par_serial")
    @ManyToOne
    private Paragon fakParSerial;
    @OneToMany(mappedBy = "spzFakSerial")
    private List<Sprzap> sprzapList;

    public Fakrach() {
    }

    public Fakrach(Integer fakSerial) {
        this.fakSerial = fakSerial;
    }

    public Fakrach(Integer fakSerial, int fakLp, String fakNumer, String fakUwagi, Date fakCzas, String fakUsrUserid, int fakRokSerial, Character fakDokTyp) {
        this.fakSerial = fakSerial;
        this.fakLp = fakLp;
        this.fakNumer = fakNumer;
        this.fakUwagi = fakUwagi;
        this.fakCzas = fakCzas;
        this.fakUsrUserid = fakUsrUserid;
        this.fakRokSerial = fakRokSerial;
        this.fakDokTyp = fakDokTyp;
    }

    public Integer getFakSerial() {
        return fakSerial;
    }

    public void setFakSerial(Integer fakSerial) {
        this.fakSerial = fakSerial;
    }

    public int getFakLp() {
        return fakLp;
    }

    public void setFakLp(int fakLp) {
        this.fakLp = fakLp;
    }

    public String getFakNumer() {
        return fakNumer;
    }

    public void setFakNumer(String fakNumer) {
        this.fakNumer = fakNumer;
    }

    public Date getFakDataWyst() {
        return fakDataWyst;
    }

    public void setFakDataWyst(Date fakDataWyst) {
        this.fakDataWyst = fakDataWyst;
    }

    public Integer getFakMiaSerial() {
        return fakMiaSerial;
    }

    public void setFakMiaSerial(Integer fakMiaSerial) {
        this.fakMiaSerial = fakMiaSerial;
    }

    public Date getFakDataSprz() {
        return fakDataSprz;
    }

    public void setFakDataSprz(Date fakDataSprz) {
        this.fakDataSprz = fakDataSprz;
    }

    public Date getFakDataPlat() {
        return fakDataPlat;
    }

    public void setFakDataPlat(Date fakDataPlat) {
        this.fakDataPlat = fakDataPlat;
    }

    public String getFakUwagi() {
        return fakUwagi;
    }

    public void setFakUwagi(String fakUwagi) {
        this.fakUwagi = fakUwagi;
    }

    public Date getFakCzas() {
        return fakCzas;
    }

    public void setFakCzas(Date fakCzas) {
        this.fakCzas = fakCzas;
    }

    public String getFakUsrUserid() {
        return fakUsrUserid;
    }

    public void setFakUsrUserid(String fakUsrUserid) {
        this.fakUsrUserid = fakUsrUserid;
    }

    public int getFakRokSerial() {
        return fakRokSerial;
    }

    public void setFakRokSerial(int fakRokSerial) {
        this.fakRokSerial = fakRokSerial;
    }

    public Character getFakDokTyp() {
        return fakDokTyp;
    }

    public void setFakDokTyp(Character fakDokTyp) {
        this.fakDokTyp = fakDokTyp;
    }

    public String getFakKorygujaca() {
        return fakKorygujaca;
    }

    public void setFakKorygujaca(String fakKorygujaca) {
        this.fakKorygujaca = fakKorygujaca;
    }

    public String getFakKorygowana() {
        return fakKorygowana;
    }

    public void setFakKorygowana(String fakKorygowana) {
        this.fakKorygowana = fakKorygowana;
    }

    public Character getFakTypWspNum() {
        return fakTypWspNum;
    }

    public void setFakTypWspNum(Character fakTypWspNum) {
        this.fakTypWspNum = fakTypWspNum;
    }

    public Integer getFakKorygowanaS() {
        return fakKorygowanaS;
    }

    public void setFakKorygowanaS(Integer fakKorygowanaS) {
        this.fakKorygowanaS = fakKorygowanaS;
    }

    public String getFakNumerMag() {
        return fakNumerMag;
    }

    public void setFakNumerMag(String fakNumerMag) {
        this.fakNumerMag = fakNumerMag;
    }

    public Integer getFakKorygujacaS() {
        return fakKorygujacaS;
    }

    public void setFakKorygujacaS(Integer fakKorygujacaS) {
        this.fakKorygujacaS = fakKorygujacaS;
    }

    public Character getFakDokMag() {
        return fakDokMag;
    }

    public void setFakDokMag(Character fakDokMag) {
        this.fakDokMag = fakDokMag;
    }

    public String getFakKonNazwa() {
        return fakKonNazwa;
    }

    public void setFakKonNazwa(String fakKonNazwa) {
        this.fakKonNazwa = fakKonNazwa;
    }

    public String getFakKonAdres() {
        return fakKonAdres;
    }

    public void setFakKonAdres(String fakKonAdres) {
        this.fakKonAdres = fakKonAdres;
    }

    public String getFakKonRegon() {
        return fakKonRegon;
    }

    public void setFakKonRegon(String fakKonRegon) {
        this.fakKonRegon = fakKonRegon;
    }

    public String getFakKonNip() {
        return fakKonNip;
    }

    public void setFakKonNip(String fakKonNip) {
        this.fakKonNip = fakKonNip;
    }

    public String getFakForOpis() {
        return fakForOpis;
    }

    public void setFakForOpis(String fakForOpis) {
        this.fakForOpis = fakForOpis;
    }

    public String getFakKonNazwaSkr() {
        return fakKonNazwaSkr;
    }

    public void setFakKonNazwaSkr(String fakKonNazwaSkr) {
        this.fakKonNazwaSkr = fakKonNazwaSkr;
    }

    public String getFakMiaNazwa() {
        return fakMiaNazwa;
    }

    public void setFakMiaNazwa(String fakMiaNazwa) {
        this.fakMiaNazwa = fakMiaNazwa;
    }

    public BigDecimal getFakKwota() {
        return fakKwota;
    }

    public void setFakKwota(BigDecimal fakKwota) {
        this.fakKwota = fakKwota;
    }

    public String getFakUserName() {
        return fakUserName;
    }

    public void setFakUserName(String fakUserName) {
        this.fakUserName = fakUserName;
    }

    public String getFakUsrKasjer() {
        return fakUsrKasjer;
    }

    public void setFakUsrKasjer(String fakUsrKasjer) {
        this.fakUsrKasjer = fakUsrKasjer;
    }

    public Integer getFakKonNrId() {
        return fakKonNrId;
    }

    public void setFakKonNrId(Integer fakKonNrId) {
        this.fakKonNrId = fakKonNrId;
    }

    public Character getFakChar1() {
        return fakChar1;
    }

    public void setFakChar1(Character fakChar1) {
        this.fakChar1 = fakChar1;
    }

    public String getFakVchar1() {
        return fakVchar1;
    }

    public void setFakVchar1(String fakVchar1) {
        this.fakVchar1 = fakVchar1;
    }

    public Date getFakDate1() {
        return fakDate1;
    }

    public void setFakDate1(Date fakDate1) {
        this.fakDate1 = fakDate1;
    }

    public Integer getFakInt1() {
        return fakInt1;
    }

    public void setFakInt1(Integer fakInt1) {
        this.fakInt1 = fakInt1;
    }

    public BigDecimal getFakNum1() {
        return fakNum1;
    }

    public void setFakNum1(BigDecimal fakNum1) {
        this.fakNum1 = fakNum1;
    }

    public BigDecimal getFakNum2() {
        return fakNum2;
    }

    public void setFakNum2(BigDecimal fakNum2) {
        this.fakNum2 = fakNum2;
    }

    public BigDecimal getFakNum3() {
        return fakNum3;
    }

    public void setFakNum3(BigDecimal fakNum3) {
        this.fakNum3 = fakNum3;
    }

    public BigDecimal getFakNum4() {
        return fakNum4;
    }

    public void setFakNum4(BigDecimal fakNum4) {
        this.fakNum4 = fakNum4;
    }

    public Character getFakChar2() {
        return fakChar2;
    }

    public void setFakChar2(Character fakChar2) {
        this.fakChar2 = fakChar2;
    }

    public Character getFakSposobRej() {
        return fakSposobRej;
    }

    public void setFakSposobRej(Character fakSposobRej) {
        this.fakSposobRej = fakSposobRej;
    }

    public String getFakKonPesel() {
        return fakKonPesel;
    }

    public void setFakKonPesel(String fakKonPesel) {
        this.fakKonPesel = fakKonPesel;
    }

    @XmlTransient
    public List<FakrachE> getFakrachEList() {
        return fakrachEList;
    }

    public void setFakrachEList(List<FakrachE> fakrachEList) {
        this.fakrachEList = fakrachEList;
    }

    @XmlTransient
    public List<DaneStatF> getDaneStatFList() {
        return daneStatFList;
    }

    public void setDaneStatFList(List<DaneStatF> daneStatFList) {
        this.daneStatFList = daneStatFList;
    }

    @XmlTransient
    public List<Pozycja> getPozycjaList() {
        return pozycjaList;
    }

    public void setPozycjaList(List<Pozycja> pozycjaList) {
        this.pozycjaList = pozycjaList;
    }

    public Firma getFakFirSerial() {
        return fakFirSerial;
    }

    public void setFakFirSerial(Firma fakFirSerial) {
        this.fakFirSerial = fakFirSerial;
    }

    public Formyzap getFakForSerial() {
        return fakForSerial;
    }

    public void setFakForSerial(Formyzap fakForSerial) {
        this.fakForSerial = fakForSerial;
    }

    public Kontrahent getFakKonSerial() {
        return fakKonSerial;
    }

    public void setFakKonSerial(Kontrahent fakKonSerial) {
        this.fakKonSerial = fakKonSerial;
    }

    public Okres getFakOkrSerial() {
        return fakOkrSerial;
    }

    public void setFakOkrSerial(Okres fakOkrSerial) {
        this.fakOkrSerial = fakOkrSerial;
    }

    public Paragon getFakParSerial() {
        return fakParSerial;
    }

    public void setFakParSerial(Paragon fakParSerial) {
        this.fakParSerial = fakParSerial;
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
        hash += (fakSerial != null ? fakSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fakrach)) {
            return false;
        }
        Fakrach other = (Fakrach) object;
        if ((this.fakSerial == null && other.fakSerial != null) || (this.fakSerial != null && !this.fakSerial.equals(other.fakSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Fakrach[ fakSerial=" + fakSerial + " ]";
    }
    
}
