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
@Table(name = "wyposaz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wyposaz.findAll", query = "SELECT w FROM Wyposaz w"),
    @NamedQuery(name = "Wyposaz.findByWypSerial", query = "SELECT w FROM Wyposaz w WHERE w.wypSerial = :wypSerial"),
    @NamedQuery(name = "Wyposaz.findByWypLp", query = "SELECT w FROM Wyposaz w WHERE w.wypLp = :wypLp"),
    @NamedQuery(name = "Wyposaz.findByWypDataNab", query = "SELECT w FROM Wyposaz w WHERE w.wypDataNab = :wypDataNab"),
    @NamedQuery(name = "Wyposaz.findByWypNrRach", query = "SELECT w FROM Wyposaz w WHERE w.wypNrRach = :wypNrRach"),
    @NamedQuery(name = "Wyposaz.findByWypNazwa", query = "SELECT w FROM Wyposaz w WHERE w.wypNazwa = :wypNazwa"),
    @NamedQuery(name = "Wyposaz.findByWypCena", query = "SELECT w FROM Wyposaz w WHERE w.wypCena = :wypCena"),
    @NamedQuery(name = "Wyposaz.findByWypNrPozKs", query = "SELECT w FROM Wyposaz w WHERE w.wypNrPozKs = :wypNrPozKs"),
    @NamedQuery(name = "Wyposaz.findByWypDataLik", query = "SELECT w FROM Wyposaz w WHERE w.wypDataLik = :wypDataLik"),
    @NamedQuery(name = "Wyposaz.findByWypPrzLik", query = "SELECT w FROM Wyposaz w WHERE w.wypPrzLik = :wypPrzLik"),
    @NamedQuery(name = "Wyposaz.findByWypCzas", query = "SELECT w FROM Wyposaz w WHERE w.wypCzas = :wypCzas"),
    @NamedQuery(name = "Wyposaz.findByWypUsrUserid", query = "SELECT w FROM Wyposaz w WHERE w.wypUsrUserid = :wypUsrUserid"),
    @NamedQuery(name = "Wyposaz.findByWypCzasMod", query = "SELECT w FROM Wyposaz w WHERE w.wypCzasMod = :wypCzasMod"),
    @NamedQuery(name = "Wyposaz.findByWypUsrUseridMod", query = "SELECT w FROM Wyposaz w WHERE w.wypUsrUseridMod = :wypUsrUseridMod"),
    @NamedQuery(name = "Wyposaz.findByWypSkreslony", query = "SELECT w FROM Wyposaz w WHERE w.wypSkreslony = :wypSkreslony"),
    @NamedQuery(name = "Wyposaz.findByWypKsiSerial", query = "SELECT w FROM Wyposaz w WHERE w.wypKsiSerial = :wypKsiSerial"),
    @NamedQuery(name = "Wyposaz.findByWypZlikwid", query = "SELECT w FROM Wyposaz w WHERE w.wypZlikwid = :wypZlikwid"),
    @NamedQuery(name = "Wyposaz.findByWypDod1", query = "SELECT w FROM Wyposaz w WHERE w.wypDod1 = :wypDod1"),
    @NamedQuery(name = "Wyposaz.findByWypChar1", query = "SELECT w FROM Wyposaz w WHERE w.wypChar1 = :wypChar1"),
    @NamedQuery(name = "Wyposaz.findByWypChar2", query = "SELECT w FROM Wyposaz w WHERE w.wypChar2 = :wypChar2"),
    @NamedQuery(name = "Wyposaz.findByWypChar3", query = "SELECT w FROM Wyposaz w WHERE w.wypChar3 = :wypChar3"),
    @NamedQuery(name = "Wyposaz.findByWypChar4", query = "SELECT w FROM Wyposaz w WHERE w.wypChar4 = :wypChar4"),
    @NamedQuery(name = "Wyposaz.findByWypNum1", query = "SELECT w FROM Wyposaz w WHERE w.wypNum1 = :wypNum1"),
    @NamedQuery(name = "Wyposaz.findByWypNum2", query = "SELECT w FROM Wyposaz w WHERE w.wypNum2 = :wypNum2"),
    @NamedQuery(name = "Wyposaz.findByWypDate1", query = "SELECT w FROM Wyposaz w WHERE w.wypDate1 = :wypDate1"),
    @NamedQuery(name = "Wyposaz.findByWypInt1", query = "SELECT w FROM Wyposaz w WHERE w.wypInt1 = :wypInt1"),
    @NamedQuery(name = "Wyposaz.findByWypInt2", query = "SELECT w FROM Wyposaz w WHERE w.wypInt2 = :wypInt2"),
    @NamedQuery(name = "Wyposaz.findByWypDate2", query = "SELECT w FROM Wyposaz w WHERE w.wypDate2 = :wypDate2"),
    @NamedQuery(name = "Wyposaz.findByWypDod2", query = "SELECT w FROM Wyposaz w WHERE w.wypDod2 = :wypDod2")})
public class Wyposaz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wyp_serial", nullable = false)
    private Integer wypSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wyp_lp", nullable = false)
    private int wypLp;
    @Column(name = "wyp_data_nab")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wypDataNab;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "wyp_nr_rach", nullable = false, length = 32)
    private String wypNrRach;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "wyp_nazwa", nullable = false, length = 64)
    private String wypNazwa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "wyp_cena", nullable = false, precision = 13, scale = 2)
    private BigDecimal wypCena;
    @Size(max = 16)
    @Column(name = "wyp_nr_poz_ks", length = 16)
    private String wypNrPozKs;
    @Column(name = "wyp_data_lik")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wypDataLik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "wyp_prz_lik", nullable = false, length = 64)
    private String wypPrzLik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wyp_czas", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date wypCzas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "wyp_usr_userid", nullable = false, length = 32)
    private String wypUsrUserid;
    @Column(name = "wyp_czas_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wypCzasMod;
    @Size(max = 32)
    @Column(name = "wyp_usr_userid_mod", length = 32)
    private String wypUsrUseridMod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wyp_skreslony", nullable = false)
    private Character wypSkreslony;
    @Column(name = "wyp_ksi_serial")
    private Integer wypKsiSerial;
    @Column(name = "wyp_zlikwid")
    private Character wypZlikwid;
    @Size(max = 64)
    @Column(name = "wyp_dod_1", length = 64)
    private String wypDod1;
    @Column(name = "wyp_char_1")
    private Character wypChar1;
    @Column(name = "wyp_char_2")
    private Character wypChar2;
    @Column(name = "wyp_char_3")
    private Character wypChar3;
    @Column(name = "wyp_char_4")
    private Character wypChar4;
    @Column(name = "wyp_num_1", precision = 17, scale = 6)
    private BigDecimal wypNum1;
    @Column(name = "wyp_num_2", precision = 17, scale = 6)
    private BigDecimal wypNum2;
    @Column(name = "wyp_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wypDate1;
    @Column(name = "wyp_int_1")
    private Integer wypInt1;
    @Column(name = "wyp_int_2")
    private Integer wypInt2;
    @Column(name = "wyp_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wypDate2;
    @Size(max = 64)
    @Column(name = "wyp_dod_2", length = 64)
    private String wypDod2;
    @JoinColumn(name = "wyp_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma wypFirSerial;
    @OneToMany(mappedBy = "dsrWypSerial")
    private List<DaneStatR> daneStatRList;

    public Wyposaz() {
    }

    public Wyposaz(Integer wypSerial) {
        this.wypSerial = wypSerial;
    }

    public Wyposaz(Integer wypSerial, int wypLp, String wypNrRach, String wypNazwa, BigDecimal wypCena, String wypPrzLik, Date wypCzas, String wypUsrUserid, Character wypSkreslony) {
        this.wypSerial = wypSerial;
        this.wypLp = wypLp;
        this.wypNrRach = wypNrRach;
        this.wypNazwa = wypNazwa;
        this.wypCena = wypCena;
        this.wypPrzLik = wypPrzLik;
        this.wypCzas = wypCzas;
        this.wypUsrUserid = wypUsrUserid;
        this.wypSkreslony = wypSkreslony;
    }

    public Integer getWypSerial() {
        return wypSerial;
    }

    public void setWypSerial(Integer wypSerial) {
        this.wypSerial = wypSerial;
    }

    public int getWypLp() {
        return wypLp;
    }

    public void setWypLp(int wypLp) {
        this.wypLp = wypLp;
    }

    public Date getWypDataNab() {
        return wypDataNab;
    }

    public void setWypDataNab(Date wypDataNab) {
        this.wypDataNab = wypDataNab;
    }

    public String getWypNrRach() {
        return wypNrRach;
    }

    public void setWypNrRach(String wypNrRach) {
        this.wypNrRach = wypNrRach;
    }

    public String getWypNazwa() {
        return wypNazwa;
    }

    public void setWypNazwa(String wypNazwa) {
        this.wypNazwa = wypNazwa;
    }

    public BigDecimal getWypCena() {
        return wypCena;
    }

    public void setWypCena(BigDecimal wypCena) {
        this.wypCena = wypCena;
    }

    public String getWypNrPozKs() {
        return wypNrPozKs;
    }

    public void setWypNrPozKs(String wypNrPozKs) {
        this.wypNrPozKs = wypNrPozKs;
    }

    public Date getWypDataLik() {
        return wypDataLik;
    }

    public void setWypDataLik(Date wypDataLik) {
        this.wypDataLik = wypDataLik;
    }

    public String getWypPrzLik() {
        return wypPrzLik;
    }

    public void setWypPrzLik(String wypPrzLik) {
        this.wypPrzLik = wypPrzLik;
    }

    public Date getWypCzas() {
        return wypCzas;
    }

    public void setWypCzas(Date wypCzas) {
        this.wypCzas = wypCzas;
    }

    public String getWypUsrUserid() {
        return wypUsrUserid;
    }

    public void setWypUsrUserid(String wypUsrUserid) {
        this.wypUsrUserid = wypUsrUserid;
    }

    public Date getWypCzasMod() {
        return wypCzasMod;
    }

    public void setWypCzasMod(Date wypCzasMod) {
        this.wypCzasMod = wypCzasMod;
    }

    public String getWypUsrUseridMod() {
        return wypUsrUseridMod;
    }

    public void setWypUsrUseridMod(String wypUsrUseridMod) {
        this.wypUsrUseridMod = wypUsrUseridMod;
    }

    public Character getWypSkreslony() {
        return wypSkreslony;
    }

    public void setWypSkreslony(Character wypSkreslony) {
        this.wypSkreslony = wypSkreslony;
    }

    public Integer getWypKsiSerial() {
        return wypKsiSerial;
    }

    public void setWypKsiSerial(Integer wypKsiSerial) {
        this.wypKsiSerial = wypKsiSerial;
    }

    public Character getWypZlikwid() {
        return wypZlikwid;
    }

    public void setWypZlikwid(Character wypZlikwid) {
        this.wypZlikwid = wypZlikwid;
    }

    public String getWypDod1() {
        return wypDod1;
    }

    public void setWypDod1(String wypDod1) {
        this.wypDod1 = wypDod1;
    }

    public Character getWypChar1() {
        return wypChar1;
    }

    public void setWypChar1(Character wypChar1) {
        this.wypChar1 = wypChar1;
    }

    public Character getWypChar2() {
        return wypChar2;
    }

    public void setWypChar2(Character wypChar2) {
        this.wypChar2 = wypChar2;
    }

    public Character getWypChar3() {
        return wypChar3;
    }

    public void setWypChar3(Character wypChar3) {
        this.wypChar3 = wypChar3;
    }

    public Character getWypChar4() {
        return wypChar4;
    }

    public void setWypChar4(Character wypChar4) {
        this.wypChar4 = wypChar4;
    }

    public BigDecimal getWypNum1() {
        return wypNum1;
    }

    public void setWypNum1(BigDecimal wypNum1) {
        this.wypNum1 = wypNum1;
    }

    public BigDecimal getWypNum2() {
        return wypNum2;
    }

    public void setWypNum2(BigDecimal wypNum2) {
        this.wypNum2 = wypNum2;
    }

    public Date getWypDate1() {
        return wypDate1;
    }

    public void setWypDate1(Date wypDate1) {
        this.wypDate1 = wypDate1;
    }

    public Integer getWypInt1() {
        return wypInt1;
    }

    public void setWypInt1(Integer wypInt1) {
        this.wypInt1 = wypInt1;
    }

    public Integer getWypInt2() {
        return wypInt2;
    }

    public void setWypInt2(Integer wypInt2) {
        this.wypInt2 = wypInt2;
    }

    public Date getWypDate2() {
        return wypDate2;
    }

    public void setWypDate2(Date wypDate2) {
        this.wypDate2 = wypDate2;
    }

    public String getWypDod2() {
        return wypDod2;
    }

    public void setWypDod2(String wypDod2) {
        this.wypDod2 = wypDod2;
    }

    public Firma getWypFirSerial() {
        return wypFirSerial;
    }

    public void setWypFirSerial(Firma wypFirSerial) {
        this.wypFirSerial = wypFirSerial;
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
        hash += (wypSerial != null ? wypSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wyposaz)) {
            return false;
        }
        Wyposaz other = (Wyposaz) object;
        if ((this.wypSerial == null && other.wypSerial != null) || (this.wypSerial != null && !this.wypSerial.equals(other.wypSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Wyposaz[ wypSerial=" + wypSerial + " ]";
    }
    
}
