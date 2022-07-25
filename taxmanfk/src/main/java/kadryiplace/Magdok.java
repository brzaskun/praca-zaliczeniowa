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
@Table(name = "magdok", catalog = "kadryiplace", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"mdo_fir_serial", "mdo_mdt_serial", "mdo_numer"}),
    @UniqueConstraint(columnNames = {"mdo_fir_serial", "mdo_mdt_serial", "mdo_lp"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Magdok.findAll", query = "SELECT m FROM Magdok m"),
    @NamedQuery(name = "Magdok.findByMdoSerial", query = "SELECT m FROM Magdok m WHERE m.mdoSerial = :mdoSerial"),
    @NamedQuery(name = "Magdok.findByMdoLp", query = "SELECT m FROM Magdok m WHERE m.mdoLp = :mdoLp"),
    @NamedQuery(name = "Magdok.findByMdoNumer", query = "SELECT m FROM Magdok m WHERE m.mdoNumer = :mdoNumer"),
    @NamedQuery(name = "Magdok.findByMdoDataWyst", query = "SELECT m FROM Magdok m WHERE m.mdoDataWyst = :mdoDataWyst"),
    @NamedQuery(name = "Magdok.findByMdoDataOper", query = "SELECT m FROM Magdok m WHERE m.mdoDataOper = :mdoDataOper"),
    @NamedQuery(name = "Magdok.findByMdoUwagi", query = "SELECT m FROM Magdok m WHERE m.mdoUwagi = :mdoUwagi"),
    @NamedQuery(name = "Magdok.findByMdoCzas", query = "SELECT m FROM Magdok m WHERE m.mdoCzas = :mdoCzas"),
    @NamedQuery(name = "Magdok.findByMdoUsrUserid", query = "SELECT m FROM Magdok m WHERE m.mdoUsrUserid = :mdoUsrUserid"),
    @NamedQuery(name = "Magdok.findByMdoMiaSerial", query = "SELECT m FROM Magdok m WHERE m.mdoMiaSerial = :mdoMiaSerial"),
    @NamedQuery(name = "Magdok.findByMdoKonNazwa", query = "SELECT m FROM Magdok m WHERE m.mdoKonNazwa = :mdoKonNazwa"),
    @NamedQuery(name = "Magdok.findByMdoKonAdres", query = "SELECT m FROM Magdok m WHERE m.mdoKonAdres = :mdoKonAdres"),
    @NamedQuery(name = "Magdok.findByMdoKonNip", query = "SELECT m FROM Magdok m WHERE m.mdoKonNip = :mdoKonNip"),
    @NamedQuery(name = "Magdok.findByMdoKonRegon", query = "SELECT m FROM Magdok m WHERE m.mdoKonRegon = :mdoKonRegon"),
    @NamedQuery(name = "Magdok.findByMdoKonNazwaSkr", query = "SELECT m FROM Magdok m WHERE m.mdoKonNazwaSkr = :mdoKonNazwaSkr"),
    @NamedQuery(name = "Magdok.findByMdoMiaNazwa", query = "SELECT m FROM Magdok m WHERE m.mdoMiaNazwa = :mdoMiaNazwa"),
    @NamedQuery(name = "Magdok.findByMdoCzasMod", query = "SELECT m FROM Magdok m WHERE m.mdoCzasMod = :mdoCzasMod"),
    @NamedQuery(name = "Magdok.findByMdoUsrUseridMod", query = "SELECT m FROM Magdok m WHERE m.mdoUsrUseridMod = :mdoUsrUseridMod"),
    @NamedQuery(name = "Magdok.findByMdoUserName", query = "SELECT m FROM Magdok m WHERE m.mdoUserName = :mdoUserName"),
    @NamedQuery(name = "Magdok.findByMdoKwota", query = "SELECT m FROM Magdok m WHERE m.mdoKwota = :mdoKwota"),
    @NamedQuery(name = "Magdok.findByMdoForSerial", query = "SELECT m FROM Magdok m WHERE m.mdoForSerial = :mdoForSerial"),
    @NamedQuery(name = "Magdok.findByMdoDataPlat", query = "SELECT m FROM Magdok m WHERE m.mdoDataPlat = :mdoDataPlat"),
    @NamedQuery(name = "Magdok.findByMdoForOpis", query = "SELECT m FROM Magdok m WHERE m.mdoForOpis = :mdoForOpis"),
    @NamedQuery(name = "Magdok.findByMdoKonNrId", query = "SELECT m FROM Magdok m WHERE m.mdoKonNrId = :mdoKonNrId"),
    @NamedQuery(name = "Magdok.findByMdoChar1", query = "SELECT m FROM Magdok m WHERE m.mdoChar1 = :mdoChar1"),
    @NamedQuery(name = "Magdok.findByMdoChar2", query = "SELECT m FROM Magdok m WHERE m.mdoChar2 = :mdoChar2"),
    @NamedQuery(name = "Magdok.findByMdoVchar1", query = "SELECT m FROM Magdok m WHERE m.mdoVchar1 = :mdoVchar1"),
    @NamedQuery(name = "Magdok.findByMdoNum1", query = "SELECT m FROM Magdok m WHERE m.mdoNum1 = :mdoNum1"),
    @NamedQuery(name = "Magdok.findByMdoNum2", query = "SELECT m FROM Magdok m WHERE m.mdoNum2 = :mdoNum2"),
    @NamedQuery(name = "Magdok.findByMdoInt1", query = "SELECT m FROM Magdok m WHERE m.mdoInt1 = :mdoInt1"),
    @NamedQuery(name = "Magdok.findByMdoDate1", query = "SELECT m FROM Magdok m WHERE m.mdoDate1 = :mdoDate1"),
    @NamedQuery(name = "Magdok.findByMdoNum3", query = "SELECT m FROM Magdok m WHERE m.mdoNum3 = :mdoNum3"),
    @NamedQuery(name = "Magdok.findByMdoNum4", query = "SELECT m FROM Magdok m WHERE m.mdoNum4 = :mdoNum4"),
    @NamedQuery(name = "Magdok.findByMdoSposobRej", query = "SELECT m FROM Magdok m WHERE m.mdoSposobRej = :mdoSposobRej"),
    @NamedQuery(name = "Magdok.findByMdoVchar2", query = "SELECT m FROM Magdok m WHERE m.mdoVchar2 = :mdoVchar2"),
    @NamedQuery(name = "Magdok.findByMdoTyp", query = "SELECT m FROM Magdok m WHERE m.mdoTyp = :mdoTyp")})
public class Magdok implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mdo_serial", nullable = false)
    private Integer mdoSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mdo_lp", nullable = false)
    private int mdoLp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "mdo_numer", nullable = false, length = 32)
    private String mdoNumer;
    @Column(name = "mdo_data_wyst")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdoDataWyst;
    @Column(name = "mdo_data_oper")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdoDataOper;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "mdo_uwagi", nullable = false, length = 128)
    private String mdoUwagi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mdo_czas", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdoCzas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 68)
    @Column(name = "mdo_usr_userid", nullable = false, length = 68)
    private String mdoUsrUserid;
    @Column(name = "mdo_mia_serial")
    private Integer mdoMiaSerial;
    @Size(max = 64)
    @Column(name = "mdo_kon_nazwa", length = 64)
    private String mdoKonNazwa;
    @Size(max = 128)
    @Column(name = "mdo_kon_adres", length = 128)
    private String mdoKonAdres;
    @Size(max = 16)
    @Column(name = "mdo_kon_nip", length = 16)
    private String mdoKonNip;
    @Size(max = 16)
    @Column(name = "mdo_kon_regon", length = 16)
    private String mdoKonRegon;
    @Size(max = 32)
    @Column(name = "mdo_kon_nazwa_skr", length = 32)
    private String mdoKonNazwaSkr;
    @Size(max = 48)
    @Column(name = "mdo_mia_nazwa", length = 48)
    private String mdoMiaNazwa;
    @Column(name = "mdo_czas_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdoCzasMod;
    @Size(max = 32)
    @Column(name = "mdo_usr_userid_mod", length = 32)
    private String mdoUsrUseridMod;
    @Size(max = 65)
    @Column(name = "mdo_user_name", length = 65)
    private String mdoUserName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mdo_kwota", precision = 13, scale = 2)
    private BigDecimal mdoKwota;
    @Column(name = "mdo_for_serial")
    private Integer mdoForSerial;
    @Column(name = "mdo_data_plat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdoDataPlat;
    @Size(max = 32)
    @Column(name = "mdo_for_opis", length = 32)
    private String mdoForOpis;
    @Column(name = "mdo_kon_nr_id")
    private Integer mdoKonNrId;
    @Column(name = "mdo_char_1")
    private Character mdoChar1;
    @Column(name = "mdo_char_2")
    private Character mdoChar2;
    @Size(max = 64)
    @Column(name = "mdo_vchar_1", length = 64)
    private String mdoVchar1;
    @Column(name = "mdo_num_1", precision = 17, scale = 6)
    private BigDecimal mdoNum1;
    @Column(name = "mdo_num_2", precision = 17, scale = 6)
    private BigDecimal mdoNum2;
    @Column(name = "mdo_int_1")
    private Integer mdoInt1;
    @Column(name = "mdo_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdoDate1;
    @Column(name = "mdo_num_3", precision = 5, scale = 2)
    private BigDecimal mdoNum3;
    @Column(name = "mdo_num_4", precision = 13, scale = 2)
    private BigDecimal mdoNum4;
    @Column(name = "mdo_sposob_rej")
    private Character mdoSposobRej;
    @Size(max = 16)
    @Column(name = "mdo_vchar_2", length = 16)
    private String mdoVchar2;
    @Column(name = "mdo_typ")
    private Character mdoTyp;
    @OneToMany(mappedBy = "zazMdoSerial")
    private List<Zakzap> zakzapList;
    @OneToMany(mappedBy = "dsdMdoSerial")
    private List<DaneStatD> daneStatDList;
    @JoinColumn(name = "mdo_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma mdoFirSerial;
    @JoinColumn(name = "mdo_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent mdoKonSerial;
    @JoinColumn(name = "mdo_mdt_serial", referencedColumnName = "mdt_serial")
    @ManyToOne
    private Magdoktyp mdoMdtSerial;
    @JoinColumn(name = "mdo_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres mdoOkrSerial;
    @JoinColumn(name = "mdo_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok mdoRokSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mpzMdoSerial")
    private List<Magpoz> magpozList;

    public Magdok() {
    }

    public Magdok(Integer mdoSerial) {
        this.mdoSerial = mdoSerial;
    }

    public Magdok(Integer mdoSerial, int mdoLp, String mdoNumer, String mdoUwagi, Date mdoCzas, String mdoUsrUserid) {
        this.mdoSerial = mdoSerial;
        this.mdoLp = mdoLp;
        this.mdoNumer = mdoNumer;
        this.mdoUwagi = mdoUwagi;
        this.mdoCzas = mdoCzas;
        this.mdoUsrUserid = mdoUsrUserid;
    }

    public Integer getMdoSerial() {
        return mdoSerial;
    }

    public void setMdoSerial(Integer mdoSerial) {
        this.mdoSerial = mdoSerial;
    }

    public int getMdoLp() {
        return mdoLp;
    }

    public void setMdoLp(int mdoLp) {
        this.mdoLp = mdoLp;
    }

    public String getMdoNumer() {
        return mdoNumer;
    }

    public void setMdoNumer(String mdoNumer) {
        this.mdoNumer = mdoNumer;
    }

    public Date getMdoDataWyst() {
        return mdoDataWyst;
    }

    public void setMdoDataWyst(Date mdoDataWyst) {
        this.mdoDataWyst = mdoDataWyst;
    }

    public Date getMdoDataOper() {
        return mdoDataOper;
    }

    public void setMdoDataOper(Date mdoDataOper) {
        this.mdoDataOper = mdoDataOper;
    }

    public String getMdoUwagi() {
        return mdoUwagi;
    }

    public void setMdoUwagi(String mdoUwagi) {
        this.mdoUwagi = mdoUwagi;
    }

    public Date getMdoCzas() {
        return mdoCzas;
    }

    public void setMdoCzas(Date mdoCzas) {
        this.mdoCzas = mdoCzas;
    }

    public String getMdoUsrUserid() {
        return mdoUsrUserid;
    }

    public void setMdoUsrUserid(String mdoUsrUserid) {
        this.mdoUsrUserid = mdoUsrUserid;
    }

    public Integer getMdoMiaSerial() {
        return mdoMiaSerial;
    }

    public void setMdoMiaSerial(Integer mdoMiaSerial) {
        this.mdoMiaSerial = mdoMiaSerial;
    }

    public String getMdoKonNazwa() {
        return mdoKonNazwa;
    }

    public void setMdoKonNazwa(String mdoKonNazwa) {
        this.mdoKonNazwa = mdoKonNazwa;
    }

    public String getMdoKonAdres() {
        return mdoKonAdres;
    }

    public void setMdoKonAdres(String mdoKonAdres) {
        this.mdoKonAdres = mdoKonAdres;
    }

    public String getMdoKonNip() {
        return mdoKonNip;
    }

    public void setMdoKonNip(String mdoKonNip) {
        this.mdoKonNip = mdoKonNip;
    }

    public String getMdoKonRegon() {
        return mdoKonRegon;
    }

    public void setMdoKonRegon(String mdoKonRegon) {
        this.mdoKonRegon = mdoKonRegon;
    }

    public String getMdoKonNazwaSkr() {
        return mdoKonNazwaSkr;
    }

    public void setMdoKonNazwaSkr(String mdoKonNazwaSkr) {
        this.mdoKonNazwaSkr = mdoKonNazwaSkr;
    }

    public String getMdoMiaNazwa() {
        return mdoMiaNazwa;
    }

    public void setMdoMiaNazwa(String mdoMiaNazwa) {
        this.mdoMiaNazwa = mdoMiaNazwa;
    }

    public Date getMdoCzasMod() {
        return mdoCzasMod;
    }

    public void setMdoCzasMod(Date mdoCzasMod) {
        this.mdoCzasMod = mdoCzasMod;
    }

    public String getMdoUsrUseridMod() {
        return mdoUsrUseridMod;
    }

    public void setMdoUsrUseridMod(String mdoUsrUseridMod) {
        this.mdoUsrUseridMod = mdoUsrUseridMod;
    }

    public String getMdoUserName() {
        return mdoUserName;
    }

    public void setMdoUserName(String mdoUserName) {
        this.mdoUserName = mdoUserName;
    }

    public BigDecimal getMdoKwota() {
        return mdoKwota;
    }

    public void setMdoKwota(BigDecimal mdoKwota) {
        this.mdoKwota = mdoKwota;
    }

    public Integer getMdoForSerial() {
        return mdoForSerial;
    }

    public void setMdoForSerial(Integer mdoForSerial) {
        this.mdoForSerial = mdoForSerial;
    }

    public Date getMdoDataPlat() {
        return mdoDataPlat;
    }

    public void setMdoDataPlat(Date mdoDataPlat) {
        this.mdoDataPlat = mdoDataPlat;
    }

    public String getMdoForOpis() {
        return mdoForOpis;
    }

    public void setMdoForOpis(String mdoForOpis) {
        this.mdoForOpis = mdoForOpis;
    }

    public Integer getMdoKonNrId() {
        return mdoKonNrId;
    }

    public void setMdoKonNrId(Integer mdoKonNrId) {
        this.mdoKonNrId = mdoKonNrId;
    }

    public Character getMdoChar1() {
        return mdoChar1;
    }

    public void setMdoChar1(Character mdoChar1) {
        this.mdoChar1 = mdoChar1;
    }

    public Character getMdoChar2() {
        return mdoChar2;
    }

    public void setMdoChar2(Character mdoChar2) {
        this.mdoChar2 = mdoChar2;
    }

    public String getMdoVchar1() {
        return mdoVchar1;
    }

    public void setMdoVchar1(String mdoVchar1) {
        this.mdoVchar1 = mdoVchar1;
    }

    public BigDecimal getMdoNum1() {
        return mdoNum1;
    }

    public void setMdoNum1(BigDecimal mdoNum1) {
        this.mdoNum1 = mdoNum1;
    }

    public BigDecimal getMdoNum2() {
        return mdoNum2;
    }

    public void setMdoNum2(BigDecimal mdoNum2) {
        this.mdoNum2 = mdoNum2;
    }

    public Integer getMdoInt1() {
        return mdoInt1;
    }

    public void setMdoInt1(Integer mdoInt1) {
        this.mdoInt1 = mdoInt1;
    }

    public Date getMdoDate1() {
        return mdoDate1;
    }

    public void setMdoDate1(Date mdoDate1) {
        this.mdoDate1 = mdoDate1;
    }

    public BigDecimal getMdoNum3() {
        return mdoNum3;
    }

    public void setMdoNum3(BigDecimal mdoNum3) {
        this.mdoNum3 = mdoNum3;
    }

    public BigDecimal getMdoNum4() {
        return mdoNum4;
    }

    public void setMdoNum4(BigDecimal mdoNum4) {
        this.mdoNum4 = mdoNum4;
    }

    public Character getMdoSposobRej() {
        return mdoSposobRej;
    }

    public void setMdoSposobRej(Character mdoSposobRej) {
        this.mdoSposobRej = mdoSposobRej;
    }

    public String getMdoVchar2() {
        return mdoVchar2;
    }

    public void setMdoVchar2(String mdoVchar2) {
        this.mdoVchar2 = mdoVchar2;
    }

    public Character getMdoTyp() {
        return mdoTyp;
    }

    public void setMdoTyp(Character mdoTyp) {
        this.mdoTyp = mdoTyp;
    }

    @XmlTransient
    public List<Zakzap> getZakzapList() {
        return zakzapList;
    }

    public void setZakzapList(List<Zakzap> zakzapList) {
        this.zakzapList = zakzapList;
    }

    @XmlTransient
    public List<DaneStatD> getDaneStatDList() {
        return daneStatDList;
    }

    public void setDaneStatDList(List<DaneStatD> daneStatDList) {
        this.daneStatDList = daneStatDList;
    }

    public Firma getMdoFirSerial() {
        return mdoFirSerial;
    }

    public void setMdoFirSerial(Firma mdoFirSerial) {
        this.mdoFirSerial = mdoFirSerial;
    }

    public Kontrahent getMdoKonSerial() {
        return mdoKonSerial;
    }

    public void setMdoKonSerial(Kontrahent mdoKonSerial) {
        this.mdoKonSerial = mdoKonSerial;
    }

    public Magdoktyp getMdoMdtSerial() {
        return mdoMdtSerial;
    }

    public void setMdoMdtSerial(Magdoktyp mdoMdtSerial) {
        this.mdoMdtSerial = mdoMdtSerial;
    }

    public Okres getMdoOkrSerial() {
        return mdoOkrSerial;
    }

    public void setMdoOkrSerial(Okres mdoOkrSerial) {
        this.mdoOkrSerial = mdoOkrSerial;
    }

    public Rok getMdoRokSerial() {
        return mdoRokSerial;
    }

    public void setMdoRokSerial(Rok mdoRokSerial) {
        this.mdoRokSerial = mdoRokSerial;
    }

    @XmlTransient
    public List<Magpoz> getMagpozList() {
        return magpozList;
    }

    public void setMagpozList(List<Magpoz> magpozList) {
        this.magpozList = magpozList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mdoSerial != null ? mdoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Magdok)) {
            return false;
        }
        Magdok other = (Magdok) object;
        if ((this.mdoSerial == null && other.mdoSerial != null) || (this.mdoSerial != null && !this.mdoSerial.equals(other.mdoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Magdok[ mdoSerial=" + mdoSerial + " ]";
    }
    
}
