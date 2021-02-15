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
@Table(name = "srodki", catalog = "kadryiplace", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"sro_fir_serial", "sro_nr_ewid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Srodki.findAll", query = "SELECT s FROM Srodki s"),
    @NamedQuery(name = "Srodki.findBySroSerial", query = "SELECT s FROM Srodki s WHERE s.sroSerial = :sroSerial"),
    @NamedQuery(name = "Srodki.findBySroLp", query = "SELECT s FROM Srodki s WHERE s.sroLp = :sroLp"),
    @NamedQuery(name = "Srodki.findBySroOpis", query = "SELECT s FROM Srodki s WHERE s.sroOpis = :sroOpis"),
    @NamedQuery(name = "Srodki.findBySroDataNab", query = "SELECT s FROM Srodki s WHERE s.sroDataNab = :sroDataNab"),
    @NamedQuery(name = "Srodki.findBySroDataPrzyj", query = "SELECT s FROM Srodki s WHERE s.sroDataPrzyj = :sroDataPrzyj"),
    @NamedQuery(name = "Srodki.findBySroDok", query = "SELECT s FROM Srodki s WHERE s.sroDok = :sroDok"),
    @NamedQuery(name = "Srodki.findBySroWartPocz", query = "SELECT s FROM Srodki s WHERE s.sroWartPocz = :sroWartPocz"),
    @NamedQuery(name = "Srodki.findBySroUwagi", query = "SELECT s FROM Srodki s WHERE s.sroUwagi = :sroUwagi"),
    @NamedQuery(name = "Srodki.findBySroSkreslony", query = "SELECT s FROM Srodki s WHERE s.sroSkreslony = :sroSkreslony"),
    @NamedQuery(name = "Srodki.findBySroCzas", query = "SELECT s FROM Srodki s WHERE s.sroCzas = :sroCzas"),
    @NamedQuery(name = "Srodki.findBySroUsrUserid", query = "SELECT s FROM Srodki s WHERE s.sroUsrUserid = :sroUsrUserid"),
    @NamedQuery(name = "Srodki.findBySroCzasMod", query = "SELECT s FROM Srodki s WHERE s.sroCzasMod = :sroCzasMod"),
    @NamedQuery(name = "Srodki.findBySroUsrUseridMod", query = "SELECT s FROM Srodki s WHERE s.sroUsrUseridMod = :sroUsrUseridMod"),
    @NamedQuery(name = "Srodki.findBySroAmortyJedno", query = "SELECT s FROM Srodki s WHERE s.sroAmortyJedno = :sroAmortyJedno"),
    @NamedQuery(name = "Srodki.findBySroOrygTyp", query = "SELECT s FROM Srodki s WHERE s.sroOrygTyp = :sroOrygTyp"),
    @NamedQuery(name = "Srodki.findBySroOrygSerial", query = "SELECT s FROM Srodki s WHERE s.sroOrygSerial = :sroOrygSerial"),
    @NamedQuery(name = "Srodki.findBySroNrEwid", query = "SELECT s FROM Srodki s WHERE s.sroNrEwid = :sroNrEwid"),
    @NamedQuery(name = "Srodki.findBySroVchar1", query = "SELECT s FROM Srodki s WHERE s.sroVchar1 = :sroVchar1"),
    @NamedQuery(name = "Srodki.findBySroTyp", query = "SELECT s FROM Srodki s WHERE s.sroTyp = :sroTyp"),
    @NamedQuery(name = "Srodki.findBySroSroSerial", query = "SELECT s FROM Srodki s WHERE s.sroSroSerial = :sroSroSerial"),
    @NamedQuery(name = "Srodki.findBySroChar1", query = "SELECT s FROM Srodki s WHERE s.sroChar1 = :sroChar1"),
    @NamedQuery(name = "Srodki.findBySroChar2", query = "SELECT s FROM Srodki s WHERE s.sroChar2 = :sroChar2"),
    @NamedQuery(name = "Srodki.findBySroChar3", query = "SELECT s FROM Srodki s WHERE s.sroChar3 = :sroChar3"),
    @NamedQuery(name = "Srodki.findBySroChar4", query = "SELECT s FROM Srodki s WHERE s.sroChar4 = :sroChar4"),
    @NamedQuery(name = "Srodki.findBySroVchar2", query = "SELECT s FROM Srodki s WHERE s.sroVchar2 = :sroVchar2"),
    @NamedQuery(name = "Srodki.findBySroDate1", query = "SELECT s FROM Srodki s WHERE s.sroDate1 = :sroDate1"),
    @NamedQuery(name = "Srodki.findBySroDate2", query = "SELECT s FROM Srodki s WHERE s.sroDate2 = :sroDate2"),
    @NamedQuery(name = "Srodki.findBySroNum1", query = "SELECT s FROM Srodki s WHERE s.sroNum1 = :sroNum1"),
    @NamedQuery(name = "Srodki.findBySroNum2", query = "SELECT s FROM Srodki s WHERE s.sroNum2 = :sroNum2"),
    @NamedQuery(name = "Srodki.findBySroInt1", query = "SELECT s FROM Srodki s WHERE s.sroInt1 = :sroInt1"),
    @NamedQuery(name = "Srodki.findBySroInt2", query = "SELECT s FROM Srodki s WHERE s.sroInt2 = :sroInt2")})
public class Srodki implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sro_serial", nullable = false)
    private Integer sroSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sro_lp", nullable = false)
    private int sroLp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "sro_opis", nullable = false, length = 64)
    private String sroOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sro_data_nab", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sroDataNab;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sro_data_przyj", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sroDataPrzyj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "sro_dok", nullable = false, length = 64)
    private String sroDok;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "sro_wart_pocz", nullable = false, precision = 13, scale = 2)
    private BigDecimal sroWartPocz;
    @Size(max = 128)
    @Column(name = "sro_uwagi", length = 128)
    private String sroUwagi;
    @Column(name = "sro_skreslony")
    private Character sroSkreslony;
    @Column(name = "sro_czas")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sroCzas;
    @Size(max = 32)
    @Column(name = "sro_usr_userid", length = 32)
    private String sroUsrUserid;
    @Column(name = "sro_czas_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sroCzasMod;
    @Size(max = 32)
    @Column(name = "sro_usr_userid_mod", length = 32)
    private String sroUsrUseridMod;
    @Column(name = "sro_amorty_jedno")
    private Character sroAmortyJedno;
    @Column(name = "sro_oryg_typ")
    private Character sroOrygTyp;
    @Column(name = "sro_oryg_serial")
    private Integer sroOrygSerial;
    @Size(max = 16)
    @Column(name = "sro_nr_ewid", length = 16)
    private String sroNrEwid;
    @Size(max = 64)
    @Column(name = "sro_vchar_1", length = 64)
    private String sroVchar1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sro_typ", nullable = false)
    private Character sroTyp;
    @Column(name = "sro_sro_serial")
    private Integer sroSroSerial;
    @Column(name = "sro_char_1")
    private Character sroChar1;
    @Column(name = "sro_char_2")
    private Character sroChar2;
    @Column(name = "sro_char_3")
    private Character sroChar3;
    @Column(name = "sro_char_4")
    private Character sroChar4;
    @Size(max = 64)
    @Column(name = "sro_vchar_2", length = 64)
    private String sroVchar2;
    @Column(name = "sro_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sroDate1;
    @Column(name = "sro_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sroDate2;
    @Column(name = "sro_num_1", precision = 17, scale = 6)
    private BigDecimal sroNum1;
    @Column(name = "sro_num_2", precision = 17, scale = 6)
    private BigDecimal sroNum2;
    @Column(name = "sro_int_1")
    private Integer sroInt1;
    @Column(name = "sro_int_2")
    private Integer sroInt2;
    @OneToMany(mappedBy = "dsrSroSerial")
    private List<DaneStatR> daneStatRList;
    @JoinColumn(name = "sro_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma sroFirSerial;
    @JoinColumn(name = "sro_krs_serial", referencedColumnName = "krs_serial")
    @ManyToOne
    private Krst sroKrsSerial;
    @OneToMany(mappedBy = "amoSroSerial")
    private List<Amorty> amortyList;

    public Srodki() {
    }

    public Srodki(Integer sroSerial) {
        this.sroSerial = sroSerial;
    }

    public Srodki(Integer sroSerial, int sroLp, String sroOpis, Date sroDataNab, Date sroDataPrzyj, String sroDok, BigDecimal sroWartPocz, Character sroTyp) {
        this.sroSerial = sroSerial;
        this.sroLp = sroLp;
        this.sroOpis = sroOpis;
        this.sroDataNab = sroDataNab;
        this.sroDataPrzyj = sroDataPrzyj;
        this.sroDok = sroDok;
        this.sroWartPocz = sroWartPocz;
        this.sroTyp = sroTyp;
    }

    public Integer getSroSerial() {
        return sroSerial;
    }

    public void setSroSerial(Integer sroSerial) {
        this.sroSerial = sroSerial;
    }

    public int getSroLp() {
        return sroLp;
    }

    public void setSroLp(int sroLp) {
        this.sroLp = sroLp;
    }

    public String getSroOpis() {
        return sroOpis;
    }

    public void setSroOpis(String sroOpis) {
        this.sroOpis = sroOpis;
    }

    public Date getSroDataNab() {
        return sroDataNab;
    }

    public void setSroDataNab(Date sroDataNab) {
        this.sroDataNab = sroDataNab;
    }

    public Date getSroDataPrzyj() {
        return sroDataPrzyj;
    }

    public void setSroDataPrzyj(Date sroDataPrzyj) {
        this.sroDataPrzyj = sroDataPrzyj;
    }

    public String getSroDok() {
        return sroDok;
    }

    public void setSroDok(String sroDok) {
        this.sroDok = sroDok;
    }

    public BigDecimal getSroWartPocz() {
        return sroWartPocz;
    }

    public void setSroWartPocz(BigDecimal sroWartPocz) {
        this.sroWartPocz = sroWartPocz;
    }

    public String getSroUwagi() {
        return sroUwagi;
    }

    public void setSroUwagi(String sroUwagi) {
        this.sroUwagi = sroUwagi;
    }

    public Character getSroSkreslony() {
        return sroSkreslony;
    }

    public void setSroSkreslony(Character sroSkreslony) {
        this.sroSkreslony = sroSkreslony;
    }

    public Date getSroCzas() {
        return sroCzas;
    }

    public void setSroCzas(Date sroCzas) {
        this.sroCzas = sroCzas;
    }

    public String getSroUsrUserid() {
        return sroUsrUserid;
    }

    public void setSroUsrUserid(String sroUsrUserid) {
        this.sroUsrUserid = sroUsrUserid;
    }

    public Date getSroCzasMod() {
        return sroCzasMod;
    }

    public void setSroCzasMod(Date sroCzasMod) {
        this.sroCzasMod = sroCzasMod;
    }

    public String getSroUsrUseridMod() {
        return sroUsrUseridMod;
    }

    public void setSroUsrUseridMod(String sroUsrUseridMod) {
        this.sroUsrUseridMod = sroUsrUseridMod;
    }

    public Character getSroAmortyJedno() {
        return sroAmortyJedno;
    }

    public void setSroAmortyJedno(Character sroAmortyJedno) {
        this.sroAmortyJedno = sroAmortyJedno;
    }

    public Character getSroOrygTyp() {
        return sroOrygTyp;
    }

    public void setSroOrygTyp(Character sroOrygTyp) {
        this.sroOrygTyp = sroOrygTyp;
    }

    public Integer getSroOrygSerial() {
        return sroOrygSerial;
    }

    public void setSroOrygSerial(Integer sroOrygSerial) {
        this.sroOrygSerial = sroOrygSerial;
    }

    public String getSroNrEwid() {
        return sroNrEwid;
    }

    public void setSroNrEwid(String sroNrEwid) {
        this.sroNrEwid = sroNrEwid;
    }

    public String getSroVchar1() {
        return sroVchar1;
    }

    public void setSroVchar1(String sroVchar1) {
        this.sroVchar1 = sroVchar1;
    }

    public Character getSroTyp() {
        return sroTyp;
    }

    public void setSroTyp(Character sroTyp) {
        this.sroTyp = sroTyp;
    }

    public Integer getSroSroSerial() {
        return sroSroSerial;
    }

    public void setSroSroSerial(Integer sroSroSerial) {
        this.sroSroSerial = sroSroSerial;
    }

    public Character getSroChar1() {
        return sroChar1;
    }

    public void setSroChar1(Character sroChar1) {
        this.sroChar1 = sroChar1;
    }

    public Character getSroChar2() {
        return sroChar2;
    }

    public void setSroChar2(Character sroChar2) {
        this.sroChar2 = sroChar2;
    }

    public Character getSroChar3() {
        return sroChar3;
    }

    public void setSroChar3(Character sroChar3) {
        this.sroChar3 = sroChar3;
    }

    public Character getSroChar4() {
        return sroChar4;
    }

    public void setSroChar4(Character sroChar4) {
        this.sroChar4 = sroChar4;
    }

    public String getSroVchar2() {
        return sroVchar2;
    }

    public void setSroVchar2(String sroVchar2) {
        this.sroVchar2 = sroVchar2;
    }

    public Date getSroDate1() {
        return sroDate1;
    }

    public void setSroDate1(Date sroDate1) {
        this.sroDate1 = sroDate1;
    }

    public Date getSroDate2() {
        return sroDate2;
    }

    public void setSroDate2(Date sroDate2) {
        this.sroDate2 = sroDate2;
    }

    public BigDecimal getSroNum1() {
        return sroNum1;
    }

    public void setSroNum1(BigDecimal sroNum1) {
        this.sroNum1 = sroNum1;
    }

    public BigDecimal getSroNum2() {
        return sroNum2;
    }

    public void setSroNum2(BigDecimal sroNum2) {
        this.sroNum2 = sroNum2;
    }

    public Integer getSroInt1() {
        return sroInt1;
    }

    public void setSroInt1(Integer sroInt1) {
        this.sroInt1 = sroInt1;
    }

    public Integer getSroInt2() {
        return sroInt2;
    }

    public void setSroInt2(Integer sroInt2) {
        this.sroInt2 = sroInt2;
    }

    @XmlTransient
    public List<DaneStatR> getDaneStatRList() {
        return daneStatRList;
    }

    public void setDaneStatRList(List<DaneStatR> daneStatRList) {
        this.daneStatRList = daneStatRList;
    }

    public Firma getSroFirSerial() {
        return sroFirSerial;
    }

    public void setSroFirSerial(Firma sroFirSerial) {
        this.sroFirSerial = sroFirSerial;
    }

    public Krst getSroKrsSerial() {
        return sroKrsSerial;
    }

    public void setSroKrsSerial(Krst sroKrsSerial) {
        this.sroKrsSerial = sroKrsSerial;
    }

    @XmlTransient
    public List<Amorty> getAmortyList() {
        return amortyList;
    }

    public void setAmortyList(List<Amorty> amortyList) {
        this.amortyList = amortyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sroSerial != null ? sroSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Srodki)) {
            return false;
        }
        Srodki other = (Srodki) object;
        if ((this.sroSerial == null && other.sroSerial != null) || (this.sroSerial != null && !this.sroSerial.equals(other.sroSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Srodki[ sroSerial=" + sroSerial + " ]";
    }
    
}
