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
@Table(name = "zakdok", catalog = "kadryiplace", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"zdo_numer", "zdo_dok_typ", "zdo_fir_serial"}),
    @UniqueConstraint(columnNames = {"zdo_rok_serial", "zdo_typ_wsp_lp", "zdo_lp"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zakdok.findAll", query = "SELECT z FROM Zakdok z"),
    @NamedQuery(name = "Zakdok.findByZdoSerial", query = "SELECT z FROM Zakdok z WHERE z.zdoSerial = :zdoSerial"),
    @NamedQuery(name = "Zakdok.findByZdoOkrSerial", query = "SELECT z FROM Zakdok z WHERE z.zdoOkrSerial = :zdoOkrSerial"),
    @NamedQuery(name = "Zakdok.findByZdoLp", query = "SELECT z FROM Zakdok z WHERE z.zdoLp = :zdoLp"),
    @NamedQuery(name = "Zakdok.findByZdoNumer", query = "SELECT z FROM Zakdok z WHERE z.zdoNumer = :zdoNumer"),
    @NamedQuery(name = "Zakdok.findByZdoDataWyst", query = "SELECT z FROM Zakdok z WHERE z.zdoDataWyst = :zdoDataWyst"),
    @NamedQuery(name = "Zakdok.findByZdoMiaSerial", query = "SELECT z FROM Zakdok z WHERE z.zdoMiaSerial = :zdoMiaSerial"),
    @NamedQuery(name = "Zakdok.findByZdoDataZaku", query = "SELECT z FROM Zakdok z WHERE z.zdoDataZaku = :zdoDataZaku"),
    @NamedQuery(name = "Zakdok.findByZdoDataPlat", query = "SELECT z FROM Zakdok z WHERE z.zdoDataPlat = :zdoDataPlat"),
    @NamedQuery(name = "Zakdok.findByZdoUwagi", query = "SELECT z FROM Zakdok z WHERE z.zdoUwagi = :zdoUwagi"),
    @NamedQuery(name = "Zakdok.findByZdoCzas", query = "SELECT z FROM Zakdok z WHERE z.zdoCzas = :zdoCzas"),
    @NamedQuery(name = "Zakdok.findByZdoUsrUserid", query = "SELECT z FROM Zakdok z WHERE z.zdoUsrUserid = :zdoUsrUserid"),
    @NamedQuery(name = "Zakdok.findByZdoDokTyp", query = "SELECT z FROM Zakdok z WHERE z.zdoDokTyp = :zdoDokTyp"),
    @NamedQuery(name = "Zakdok.findByZdoKorygujaca", query = "SELECT z FROM Zakdok z WHERE z.zdoKorygujaca = :zdoKorygujaca"),
    @NamedQuery(name = "Zakdok.findByZdoKorygowana", query = "SELECT z FROM Zakdok z WHERE z.zdoKorygowana = :zdoKorygowana"),
    @NamedQuery(name = "Zakdok.findByZdoKorygowanaS", query = "SELECT z FROM Zakdok z WHERE z.zdoKorygowanaS = :zdoKorygowanaS"),
    @NamedQuery(name = "Zakdok.findByZdoTypWspLp", query = "SELECT z FROM Zakdok z WHERE z.zdoTypWspLp = :zdoTypWspLp"),
    @NamedQuery(name = "Zakdok.findByZdoNumerMag", query = "SELECT z FROM Zakdok z WHERE z.zdoNumerMag = :zdoNumerMag"),
    @NamedQuery(name = "Zakdok.findByZdoKorygujacaS", query = "SELECT z FROM Zakdok z WHERE z.zdoKorygujacaS = :zdoKorygujacaS"),
    @NamedQuery(name = "Zakdok.findByZdoChar1", query = "SELECT z FROM Zakdok z WHERE z.zdoChar1 = :zdoChar1"),
    @NamedQuery(name = "Zakdok.findByZdoChar2", query = "SELECT z FROM Zakdok z WHERE z.zdoChar2 = :zdoChar2"),
    @NamedQuery(name = "Zakdok.findByZdoVchar1", query = "SELECT z FROM Zakdok z WHERE z.zdoVchar1 = :zdoVchar1"),
    @NamedQuery(name = "Zakdok.findByZdoInt1", query = "SELECT z FROM Zakdok z WHERE z.zdoInt1 = :zdoInt1"),
    @NamedQuery(name = "Zakdok.findByZdoNum1", query = "SELECT z FROM Zakdok z WHERE z.zdoNum1 = :zdoNum1"),
    @NamedQuery(name = "Zakdok.findByZdoNum2", query = "SELECT z FROM Zakdok z WHERE z.zdoNum2 = :zdoNum2"),
    @NamedQuery(name = "Zakdok.findByZdoDate1", query = "SELECT z FROM Zakdok z WHERE z.zdoDate1 = :zdoDate1"),
    @NamedQuery(name = "Zakdok.findByZdoNum3", query = "SELECT z FROM Zakdok z WHERE z.zdoNum3 = :zdoNum3"),
    @NamedQuery(name = "Zakdok.findByZdoNum4", query = "SELECT z FROM Zakdok z WHERE z.zdoNum4 = :zdoNum4"),
    @NamedQuery(name = "Zakdok.findByZdoSposobRej", query = "SELECT z FROM Zakdok z WHERE z.zdoSposobRej = :zdoSposobRej"),
    @NamedQuery(name = "Zakdok.findByZdoDokMag", query = "SELECT z FROM Zakdok z WHERE z.zdoDokMag = :zdoDokMag"),
    @NamedQuery(name = "Zakdok.findByZdoKonNazwa", query = "SELECT z FROM Zakdok z WHERE z.zdoKonNazwa = :zdoKonNazwa"),
    @NamedQuery(name = "Zakdok.findByZdoKonAdres", query = "SELECT z FROM Zakdok z WHERE z.zdoKonAdres = :zdoKonAdres"),
    @NamedQuery(name = "Zakdok.findByZdoKonRegon", query = "SELECT z FROM Zakdok z WHERE z.zdoKonRegon = :zdoKonRegon"),
    @NamedQuery(name = "Zakdok.findByZdoKonNip", query = "SELECT z FROM Zakdok z WHERE z.zdoKonNip = :zdoKonNip"),
    @NamedQuery(name = "Zakdok.findByZdoForOpis", query = "SELECT z FROM Zakdok z WHERE z.zdoForOpis = :zdoForOpis"),
    @NamedQuery(name = "Zakdok.findByZdoKonNazwaSkr", query = "SELECT z FROM Zakdok z WHERE z.zdoKonNazwaSkr = :zdoKonNazwaSkr"),
    @NamedQuery(name = "Zakdok.findByZdoMiaNazwa", query = "SELECT z FROM Zakdok z WHERE z.zdoMiaNazwa = :zdoMiaNazwa"),
    @NamedQuery(name = "Zakdok.findByZdoKwota", query = "SELECT z FROM Zakdok z WHERE z.zdoKwota = :zdoKwota"),
    @NamedQuery(name = "Zakdok.findByZdoKonNrId", query = "SELECT z FROM Zakdok z WHERE z.zdoKonNrId = :zdoKonNrId"),
    @NamedQuery(name = "Zakdok.findByZdoVchar2", query = "SELECT z FROM Zakdok z WHERE z.zdoVchar2 = :zdoVchar2"),
    @NamedQuery(name = "Zakdok.findByZdoKonPesel", query = "SELECT z FROM Zakdok z WHERE z.zdoKonPesel = :zdoKonPesel")})
public class Zakdok implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "zdo_serial", nullable = false)
    private Integer zdoSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zdo_okr_serial", nullable = false)
    private int zdoOkrSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zdo_lp", nullable = false)
    private int zdoLp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "zdo_numer", nullable = false, length = 32)
    private String zdoNumer;
    @Column(name = "zdo_data_wyst")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zdoDataWyst;
    @Column(name = "zdo_mia_serial")
    private Integer zdoMiaSerial;
    @Column(name = "zdo_data_zaku")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zdoDataZaku;
    @Column(name = "zdo_data_plat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zdoDataPlat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "zdo_uwagi", nullable = false, length = 128)
    private String zdoUwagi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zdo_czas", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date zdoCzas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "zdo_usr_userid", nullable = false, length = 32)
    private String zdoUsrUserid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zdo_dok_typ", nullable = false)
    private Character zdoDokTyp;
    @Size(max = 32)
    @Column(name = "zdo_korygujaca", length = 32)
    private String zdoKorygujaca;
    @Size(max = 32)
    @Column(name = "zdo_korygowana", length = 32)
    private String zdoKorygowana;
    @Column(name = "zdo_korygowana_s")
    private Integer zdoKorygowanaS;
    @Column(name = "zdo_typ_wsp_lp")
    private Character zdoTypWspLp;
    @Size(max = 32)
    @Column(name = "zdo_numer_mag", length = 32)
    private String zdoNumerMag;
    @Column(name = "zdo_korygujaca_s")
    private Integer zdoKorygujacaS;
    @Column(name = "zdo_char_1")
    private Character zdoChar1;
    @Column(name = "zdo_char_2")
    private Character zdoChar2;
    @Size(max = 64)
    @Column(name = "zdo_vchar_1", length = 64)
    private String zdoVchar1;
    @Column(name = "zdo_int_1")
    private Integer zdoInt1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "zdo_num_1", precision = 17, scale = 6)
    private BigDecimal zdoNum1;
    @Column(name = "zdo_num_2", precision = 17, scale = 6)
    private BigDecimal zdoNum2;
    @Column(name = "zdo_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zdoDate1;
    @Column(name = "zdo_num_3", precision = 5, scale = 2)
    private BigDecimal zdoNum3;
    @Column(name = "zdo_num_4", precision = 13, scale = 2)
    private BigDecimal zdoNum4;
    @Column(name = "zdo_sposob_rej")
    private Character zdoSposobRej;
    @Column(name = "zdo_dok_mag")
    private Character zdoDokMag;
    @Size(max = 64)
    @Column(name = "zdo_kon_nazwa", length = 64)
    private String zdoKonNazwa;
    @Size(max = 128)
    @Column(name = "zdo_kon_adres", length = 128)
    private String zdoKonAdres;
    @Size(max = 16)
    @Column(name = "zdo_kon_regon", length = 16)
    private String zdoKonRegon;
    @Size(max = 16)
    @Column(name = "zdo_kon_nip", length = 16)
    private String zdoKonNip;
    @Size(max = 32)
    @Column(name = "zdo_for_opis", length = 32)
    private String zdoForOpis;
    @Size(max = 32)
    @Column(name = "zdo_kon_nazwa_skr", length = 32)
    private String zdoKonNazwaSkr;
    @Size(max = 48)
    @Column(name = "zdo_mia_nazwa", length = 48)
    private String zdoMiaNazwa;
    @Column(name = "zdo_kwota", precision = 13, scale = 2)
    private BigDecimal zdoKwota;
    @Column(name = "zdo_kon_nr_id")
    private Integer zdoKonNrId;
    @Size(max = 64)
    @Column(name = "zdo_vchar_2", length = 64)
    private String zdoVchar2;
    @Size(max = 16)
    @Column(name = "zdo_kon_pesel", length = 16)
    private String zdoKonPesel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zpzZdoSerial")
    private List<Zakpoz> zakpozList;
    @JoinColumn(name = "zdo_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma zdoFirSerial;
    @JoinColumn(name = "zdo_for_serial", referencedColumnName = "for_serial")
    @ManyToOne
    private Formyzap zdoForSerial;
    @JoinColumn(name = "zdo_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent zdoKonSerial;
    @JoinColumn(name = "zdo_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok zdoRokSerial;
    @OneToMany(mappedBy = "dsdZdoSerial")
    private List<DaneStatD> daneStatDList;

    public Zakdok() {
    }

    public Zakdok(Integer zdoSerial) {
        this.zdoSerial = zdoSerial;
    }

    public Zakdok(Integer zdoSerial, int zdoOkrSerial, int zdoLp, String zdoNumer, String zdoUwagi, Date zdoCzas, String zdoUsrUserid, Character zdoDokTyp) {
        this.zdoSerial = zdoSerial;
        this.zdoOkrSerial = zdoOkrSerial;
        this.zdoLp = zdoLp;
        this.zdoNumer = zdoNumer;
        this.zdoUwagi = zdoUwagi;
        this.zdoCzas = zdoCzas;
        this.zdoUsrUserid = zdoUsrUserid;
        this.zdoDokTyp = zdoDokTyp;
    }

    public Integer getZdoSerial() {
        return zdoSerial;
    }

    public void setZdoSerial(Integer zdoSerial) {
        this.zdoSerial = zdoSerial;
    }

    public int getZdoOkrSerial() {
        return zdoOkrSerial;
    }

    public void setZdoOkrSerial(int zdoOkrSerial) {
        this.zdoOkrSerial = zdoOkrSerial;
    }

    public int getZdoLp() {
        return zdoLp;
    }

    public void setZdoLp(int zdoLp) {
        this.zdoLp = zdoLp;
    }

    public String getZdoNumer() {
        return zdoNumer;
    }

    public void setZdoNumer(String zdoNumer) {
        this.zdoNumer = zdoNumer;
    }

    public Date getZdoDataWyst() {
        return zdoDataWyst;
    }

    public void setZdoDataWyst(Date zdoDataWyst) {
        this.zdoDataWyst = zdoDataWyst;
    }

    public Integer getZdoMiaSerial() {
        return zdoMiaSerial;
    }

    public void setZdoMiaSerial(Integer zdoMiaSerial) {
        this.zdoMiaSerial = zdoMiaSerial;
    }

    public Date getZdoDataZaku() {
        return zdoDataZaku;
    }

    public void setZdoDataZaku(Date zdoDataZaku) {
        this.zdoDataZaku = zdoDataZaku;
    }

    public Date getZdoDataPlat() {
        return zdoDataPlat;
    }

    public void setZdoDataPlat(Date zdoDataPlat) {
        this.zdoDataPlat = zdoDataPlat;
    }

    public String getZdoUwagi() {
        return zdoUwagi;
    }

    public void setZdoUwagi(String zdoUwagi) {
        this.zdoUwagi = zdoUwagi;
    }

    public Date getZdoCzas() {
        return zdoCzas;
    }

    public void setZdoCzas(Date zdoCzas) {
        this.zdoCzas = zdoCzas;
    }

    public String getZdoUsrUserid() {
        return zdoUsrUserid;
    }

    public void setZdoUsrUserid(String zdoUsrUserid) {
        this.zdoUsrUserid = zdoUsrUserid;
    }

    public Character getZdoDokTyp() {
        return zdoDokTyp;
    }

    public void setZdoDokTyp(Character zdoDokTyp) {
        this.zdoDokTyp = zdoDokTyp;
    }

    public String getZdoKorygujaca() {
        return zdoKorygujaca;
    }

    public void setZdoKorygujaca(String zdoKorygujaca) {
        this.zdoKorygujaca = zdoKorygujaca;
    }

    public String getZdoKorygowana() {
        return zdoKorygowana;
    }

    public void setZdoKorygowana(String zdoKorygowana) {
        this.zdoKorygowana = zdoKorygowana;
    }

    public Integer getZdoKorygowanaS() {
        return zdoKorygowanaS;
    }

    public void setZdoKorygowanaS(Integer zdoKorygowanaS) {
        this.zdoKorygowanaS = zdoKorygowanaS;
    }

    public Character getZdoTypWspLp() {
        return zdoTypWspLp;
    }

    public void setZdoTypWspLp(Character zdoTypWspLp) {
        this.zdoTypWspLp = zdoTypWspLp;
    }

    public String getZdoNumerMag() {
        return zdoNumerMag;
    }

    public void setZdoNumerMag(String zdoNumerMag) {
        this.zdoNumerMag = zdoNumerMag;
    }

    public Integer getZdoKorygujacaS() {
        return zdoKorygujacaS;
    }

    public void setZdoKorygujacaS(Integer zdoKorygujacaS) {
        this.zdoKorygujacaS = zdoKorygujacaS;
    }

    public Character getZdoChar1() {
        return zdoChar1;
    }

    public void setZdoChar1(Character zdoChar1) {
        this.zdoChar1 = zdoChar1;
    }

    public Character getZdoChar2() {
        return zdoChar2;
    }

    public void setZdoChar2(Character zdoChar2) {
        this.zdoChar2 = zdoChar2;
    }

    public String getZdoVchar1() {
        return zdoVchar1;
    }

    public void setZdoVchar1(String zdoVchar1) {
        this.zdoVchar1 = zdoVchar1;
    }

    public Integer getZdoInt1() {
        return zdoInt1;
    }

    public void setZdoInt1(Integer zdoInt1) {
        this.zdoInt1 = zdoInt1;
    }

    public BigDecimal getZdoNum1() {
        return zdoNum1;
    }

    public void setZdoNum1(BigDecimal zdoNum1) {
        this.zdoNum1 = zdoNum1;
    }

    public BigDecimal getZdoNum2() {
        return zdoNum2;
    }

    public void setZdoNum2(BigDecimal zdoNum2) {
        this.zdoNum2 = zdoNum2;
    }

    public Date getZdoDate1() {
        return zdoDate1;
    }

    public void setZdoDate1(Date zdoDate1) {
        this.zdoDate1 = zdoDate1;
    }

    public BigDecimal getZdoNum3() {
        return zdoNum3;
    }

    public void setZdoNum3(BigDecimal zdoNum3) {
        this.zdoNum3 = zdoNum3;
    }

    public BigDecimal getZdoNum4() {
        return zdoNum4;
    }

    public void setZdoNum4(BigDecimal zdoNum4) {
        this.zdoNum4 = zdoNum4;
    }

    public Character getZdoSposobRej() {
        return zdoSposobRej;
    }

    public void setZdoSposobRej(Character zdoSposobRej) {
        this.zdoSposobRej = zdoSposobRej;
    }

    public Character getZdoDokMag() {
        return zdoDokMag;
    }

    public void setZdoDokMag(Character zdoDokMag) {
        this.zdoDokMag = zdoDokMag;
    }

    public String getZdoKonNazwa() {
        return zdoKonNazwa;
    }

    public void setZdoKonNazwa(String zdoKonNazwa) {
        this.zdoKonNazwa = zdoKonNazwa;
    }

    public String getZdoKonAdres() {
        return zdoKonAdres;
    }

    public void setZdoKonAdres(String zdoKonAdres) {
        this.zdoKonAdres = zdoKonAdres;
    }

    public String getZdoKonRegon() {
        return zdoKonRegon;
    }

    public void setZdoKonRegon(String zdoKonRegon) {
        this.zdoKonRegon = zdoKonRegon;
    }

    public String getZdoKonNip() {
        return zdoKonNip;
    }

    public void setZdoKonNip(String zdoKonNip) {
        this.zdoKonNip = zdoKonNip;
    }

    public String getZdoForOpis() {
        return zdoForOpis;
    }

    public void setZdoForOpis(String zdoForOpis) {
        this.zdoForOpis = zdoForOpis;
    }

    public String getZdoKonNazwaSkr() {
        return zdoKonNazwaSkr;
    }

    public void setZdoKonNazwaSkr(String zdoKonNazwaSkr) {
        this.zdoKonNazwaSkr = zdoKonNazwaSkr;
    }

    public String getZdoMiaNazwa() {
        return zdoMiaNazwa;
    }

    public void setZdoMiaNazwa(String zdoMiaNazwa) {
        this.zdoMiaNazwa = zdoMiaNazwa;
    }

    public BigDecimal getZdoKwota() {
        return zdoKwota;
    }

    public void setZdoKwota(BigDecimal zdoKwota) {
        this.zdoKwota = zdoKwota;
    }

    public Integer getZdoKonNrId() {
        return zdoKonNrId;
    }

    public void setZdoKonNrId(Integer zdoKonNrId) {
        this.zdoKonNrId = zdoKonNrId;
    }

    public String getZdoVchar2() {
        return zdoVchar2;
    }

    public void setZdoVchar2(String zdoVchar2) {
        this.zdoVchar2 = zdoVchar2;
    }

    public String getZdoKonPesel() {
        return zdoKonPesel;
    }

    public void setZdoKonPesel(String zdoKonPesel) {
        this.zdoKonPesel = zdoKonPesel;
    }

    @XmlTransient
    public List<Zakpoz> getZakpozList() {
        return zakpozList;
    }

    public void setZakpozList(List<Zakpoz> zakpozList) {
        this.zakpozList = zakpozList;
    }

    public Firma getZdoFirSerial() {
        return zdoFirSerial;
    }

    public void setZdoFirSerial(Firma zdoFirSerial) {
        this.zdoFirSerial = zdoFirSerial;
    }

    public Formyzap getZdoForSerial() {
        return zdoForSerial;
    }

    public void setZdoForSerial(Formyzap zdoForSerial) {
        this.zdoForSerial = zdoForSerial;
    }

    public Kontrahent getZdoKonSerial() {
        return zdoKonSerial;
    }

    public void setZdoKonSerial(Kontrahent zdoKonSerial) {
        this.zdoKonSerial = zdoKonSerial;
    }

    public Rok getZdoRokSerial() {
        return zdoRokSerial;
    }

    public void setZdoRokSerial(Rok zdoRokSerial) {
        this.zdoRokSerial = zdoRokSerial;
    }

    @XmlTransient
    public List<DaneStatD> getDaneStatDList() {
        return daneStatDList;
    }

    public void setDaneStatDList(List<DaneStatD> daneStatDList) {
        this.daneStatDList = daneStatDList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zdoSerial != null ? zdoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zakdok)) {
            return false;
        }
        Zakdok other = (Zakdok) object;
        if ((this.zdoSerial == null && other.zdoSerial != null) || (this.zdoSerial != null && !this.zdoSerial.equals(other.zdoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Zakdok[ zdoSerial=" + zdoSerial + " ]";
    }
    
}
