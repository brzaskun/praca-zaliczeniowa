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
@Table(name = "ksiegapir", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ksiegapir.findAll", query = "SELECT k FROM Ksiegapir k"),
    @NamedQuery(name = "Ksiegapir.findByKsiSerial", query = "SELECT k FROM Ksiegapir k WHERE k.ksiSerial = :ksiSerial"),
    @NamedQuery(name = "Ksiegapir.findByKsiDataZdarz", query = "SELECT k FROM Ksiegapir k WHERE k.ksiDataZdarz = :ksiDataZdarz"),
    @NamedQuery(name = "Ksiegapir.findByKsiNrDowodu", query = "SELECT k FROM Ksiegapir k WHERE k.ksiNrDowodu = :ksiNrDowodu"),
    @NamedQuery(name = "Ksiegapir.findByKsiOpisZdarz", query = "SELECT k FROM Ksiegapir k WHERE k.ksiOpisZdarz = :ksiOpisZdarz"),
    @NamedQuery(name = "Ksiegapir.findByKsiLp", query = "SELECT k FROM Ksiegapir k WHERE k.ksiLp = :ksiLp"),
    @NamedQuery(name = "Ksiegapir.findByKsiKwota7", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKwota7 = :ksiKwota7"),
    @NamedQuery(name = "Ksiegapir.findByKsiKwota8", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKwota8 = :ksiKwota8"),
    @NamedQuery(name = "Ksiegapir.findByKsiKwota10", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKwota10 = :ksiKwota10"),
    @NamedQuery(name = "Ksiegapir.findByKsiKwota11", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKwota11 = :ksiKwota11"),
    @NamedQuery(name = "Ksiegapir.findByKsiKwota12", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKwota12 = :ksiKwota12"),
    @NamedQuery(name = "Ksiegapir.findByKsiKwota13", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKwota13 = :ksiKwota13"),
    @NamedQuery(name = "Ksiegapir.findByKsiKwota14", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKwota14 = :ksiKwota14"),
    @NamedQuery(name = "Ksiegapir.findByKsiKwota16", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKwota16 = :ksiKwota16"),
    @NamedQuery(name = "Ksiegapir.findByKsiUwagi", query = "SELECT k FROM Ksiegapir k WHERE k.ksiUwagi = :ksiUwagi"),
    @NamedQuery(name = "Ksiegapir.findByKsiKonNazwa", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKonNazwa = :ksiKonNazwa"),
    @NamedQuery(name = "Ksiegapir.findByKsiKonAdres", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKonAdres = :ksiKonAdres"),
    @NamedQuery(name = "Ksiegapir.findByKsiKonNazwaSkr", query = "SELECT k FROM Ksiegapir k WHERE k.ksiKonNazwaSkr = :ksiKonNazwaSkr"),
    @NamedQuery(name = "Ksiegapir.findByKsiSkreslony", query = "SELECT k FROM Ksiegapir k WHERE k.ksiSkreslony = :ksiSkreslony"),
    @NamedQuery(name = "Ksiegapir.findByKsiOrygTyp", query = "SELECT k FROM Ksiegapir k WHERE k.ksiOrygTyp = :ksiOrygTyp"),
    @NamedQuery(name = "Ksiegapir.findByKsiOrygSerial", query = "SELECT k FROM Ksiegapir k WHERE k.ksiOrygSerial = :ksiOrygSerial"),
    @NamedQuery(name = "Ksiegapir.findByKsiCzas", query = "SELECT k FROM Ksiegapir k WHERE k.ksiCzas = :ksiCzas"),
    @NamedQuery(name = "Ksiegapir.findByKsiUsrUserid", query = "SELECT k FROM Ksiegapir k WHERE k.ksiUsrUserid = :ksiUsrUserid"),
    @NamedQuery(name = "Ksiegapir.findByKsiCzasMod", query = "SELECT k FROM Ksiegapir k WHERE k.ksiCzasMod = :ksiCzasMod"),
    @NamedQuery(name = "Ksiegapir.findByKsiUsrUseridMod", query = "SELECT k FROM Ksiegapir k WHERE k.ksiUsrUseridMod = :ksiUsrUseridMod"),
    @NamedQuery(name = "Ksiegapir.findByKsiDodNum1", query = "SELECT k FROM Ksiegapir k WHERE k.ksiDodNum1 = :ksiDodNum1"),
    @NamedQuery(name = "Ksiegapir.findByKsiDodNum2", query = "SELECT k FROM Ksiegapir k WHERE k.ksiDodNum2 = :ksiDodNum2"),
    @NamedQuery(name = "Ksiegapir.findByKsiDodNum3", query = "SELECT k FROM Ksiegapir k WHERE k.ksiDodNum3 = :ksiDodNum3"),
    @NamedQuery(name = "Ksiegapir.findByKsiDodNum4", query = "SELECT k FROM Ksiegapir k WHERE k.ksiDodNum4 = :ksiDodNum4"),
    @NamedQuery(name = "Ksiegapir.findByKsiDodChar1", query = "SELECT k FROM Ksiegapir k WHERE k.ksiDodChar1 = :ksiDodChar1"),
    @NamedQuery(name = "Ksiegapir.findByKsiDodChar2", query = "SELECT k FROM Ksiegapir k WHERE k.ksiDodChar2 = :ksiDodChar2"),
    @NamedQuery(name = "Ksiegapir.findByKsiDodVchar1", query = "SELECT k FROM Ksiegapir k WHERE k.ksiDodVchar1 = :ksiDodVchar1"),
    @NamedQuery(name = "Ksiegapir.findByKsiDodVchar2", query = "SELECT k FROM Ksiegapir k WHERE k.ksiDodVchar2 = :ksiDodVchar2")})
public class Ksiegapir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ksi_serial", nullable = false)
    private Integer ksiSerial;
    @Column(name = "ksi_data_zdarz")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ksiDataZdarz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ksi_nr_dowodu", nullable = false, length = 32)
    private String ksiNrDowodu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "ksi_opis_zdarz", nullable = false, length = 128)
    private String ksiOpisZdarz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ksi_lp", nullable = false)
    private int ksiLp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ksi_kwota_7", precision = 13, scale = 2)
    private BigDecimal ksiKwota7;
    @Column(name = "ksi_kwota_8", precision = 13, scale = 2)
    private BigDecimal ksiKwota8;
    @Column(name = "ksi_kwota_10", precision = 13, scale = 2)
    private BigDecimal ksiKwota10;
    @Column(name = "ksi_kwota_11", precision = 13, scale = 2)
    private BigDecimal ksiKwota11;
    @Column(name = "ksi_kwota_12", precision = 13, scale = 2)
    private BigDecimal ksiKwota12;
    @Column(name = "ksi_kwota_13", precision = 13, scale = 2)
    private BigDecimal ksiKwota13;
    @Column(name = "ksi_kwota_14", precision = 13, scale = 2)
    private BigDecimal ksiKwota14;
    @Column(name = "ksi_kwota_16", precision = 13, scale = 2)
    private BigDecimal ksiKwota16;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ksi_uwagi", nullable = false, length = 64)
    private String ksiUwagi;
    @Size(max = 64)
    @Column(name = "ksi_kon_nazwa", length = 64)
    private String ksiKonNazwa;
    @Size(max = 128)
    @Column(name = "ksi_kon_adres", length = 128)
    private String ksiKonAdres;
    @Size(max = 32)
    @Column(name = "ksi_kon_nazwa_skr", length = 32)
    private String ksiKonNazwaSkr;
    @Column(name = "ksi_skreslony")
    private Character ksiSkreslony;
    @Column(name = "ksi_oryg_typ")
    private Character ksiOrygTyp;
    @Column(name = "ksi_oryg_serial")
    private Integer ksiOrygSerial;
    @Column(name = "ksi_czas")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ksiCzas;
    @Size(max = 32)
    @Column(name = "ksi_usr_userid", length = 32)
    private String ksiUsrUserid;
    @Column(name = "ksi_czas_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ksiCzasMod;
    @Size(max = 32)
    @Column(name = "ksi_usr_userid_mod", length = 32)
    private String ksiUsrUseridMod;
    @Column(name = "ksi_dod_num_1", precision = 13, scale = 2)
    private BigDecimal ksiDodNum1;
    @Column(name = "ksi_dod_num_2", precision = 13, scale = 2)
    private BigDecimal ksiDodNum2;
    @Column(name = "ksi_dod_num_3", precision = 13, scale = 2)
    private BigDecimal ksiDodNum3;
    @Column(name = "ksi_dod_num_4", precision = 13, scale = 2)
    private BigDecimal ksiDodNum4;
    @Column(name = "ksi_dod_char_1")
    private Character ksiDodChar1;
    @Column(name = "ksi_dod_char_2")
    private Character ksiDodChar2;
    @Size(max = 64)
    @Column(name = "ksi_dod_vchar_1", length = 64)
    private String ksiDodVchar1;
    @Size(max = 64)
    @Column(name = "ksi_dod_vchar_2", length = 64)
    private String ksiDodVchar2;
    @JoinColumn(name = "ksi_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma ksiFirSerial;
    @JoinColumn(name = "ksi_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent ksiKonSerial;
    @JoinColumn(name = "ksi_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres ksiOkrSerial;
    @JoinColumn(name = "ksi_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok ksiRokSerial;
    @OneToMany(mappedBy = "dsrKsiSerial")
    private List<DaneStatR> daneStatRList;

    public Ksiegapir() {
    }

    public Ksiegapir(Integer ksiSerial) {
        this.ksiSerial = ksiSerial;
    }

    public Ksiegapir(Integer ksiSerial, String ksiNrDowodu, String ksiOpisZdarz, int ksiLp, String ksiUwagi) {
        this.ksiSerial = ksiSerial;
        this.ksiNrDowodu = ksiNrDowodu;
        this.ksiOpisZdarz = ksiOpisZdarz;
        this.ksiLp = ksiLp;
        this.ksiUwagi = ksiUwagi;
    }

    public Integer getKsiSerial() {
        return ksiSerial;
    }

    public void setKsiSerial(Integer ksiSerial) {
        this.ksiSerial = ksiSerial;
    }

    public Date getKsiDataZdarz() {
        return ksiDataZdarz;
    }

    public void setKsiDataZdarz(Date ksiDataZdarz) {
        this.ksiDataZdarz = ksiDataZdarz;
    }

    public String getKsiNrDowodu() {
        return ksiNrDowodu;
    }

    public void setKsiNrDowodu(String ksiNrDowodu) {
        this.ksiNrDowodu = ksiNrDowodu;
    }

    public String getKsiOpisZdarz() {
        return ksiOpisZdarz;
    }

    public void setKsiOpisZdarz(String ksiOpisZdarz) {
        this.ksiOpisZdarz = ksiOpisZdarz;
    }

    public int getKsiLp() {
        return ksiLp;
    }

    public void setKsiLp(int ksiLp) {
        this.ksiLp = ksiLp;
    }

    public BigDecimal getKsiKwota7() {
        return ksiKwota7;
    }

    public void setKsiKwota7(BigDecimal ksiKwota7) {
        this.ksiKwota7 = ksiKwota7;
    }

    public BigDecimal getKsiKwota8() {
        return ksiKwota8;
    }

    public void setKsiKwota8(BigDecimal ksiKwota8) {
        this.ksiKwota8 = ksiKwota8;
    }

    public BigDecimal getKsiKwota10() {
        return ksiKwota10;
    }

    public void setKsiKwota10(BigDecimal ksiKwota10) {
        this.ksiKwota10 = ksiKwota10;
    }

    public BigDecimal getKsiKwota11() {
        return ksiKwota11;
    }

    public void setKsiKwota11(BigDecimal ksiKwota11) {
        this.ksiKwota11 = ksiKwota11;
    }

    public BigDecimal getKsiKwota12() {
        return ksiKwota12;
    }

    public void setKsiKwota12(BigDecimal ksiKwota12) {
        this.ksiKwota12 = ksiKwota12;
    }

    public BigDecimal getKsiKwota13() {
        return ksiKwota13;
    }

    public void setKsiKwota13(BigDecimal ksiKwota13) {
        this.ksiKwota13 = ksiKwota13;
    }

    public BigDecimal getKsiKwota14() {
        return ksiKwota14;
    }

    public void setKsiKwota14(BigDecimal ksiKwota14) {
        this.ksiKwota14 = ksiKwota14;
    }

    public BigDecimal getKsiKwota16() {
        return ksiKwota16;
    }

    public void setKsiKwota16(BigDecimal ksiKwota16) {
        this.ksiKwota16 = ksiKwota16;
    }

    public String getKsiUwagi() {
        return ksiUwagi;
    }

    public void setKsiUwagi(String ksiUwagi) {
        this.ksiUwagi = ksiUwagi;
    }

    public String getKsiKonNazwa() {
        return ksiKonNazwa;
    }

    public void setKsiKonNazwa(String ksiKonNazwa) {
        this.ksiKonNazwa = ksiKonNazwa;
    }

    public String getKsiKonAdres() {
        return ksiKonAdres;
    }

    public void setKsiKonAdres(String ksiKonAdres) {
        this.ksiKonAdres = ksiKonAdres;
    }

    public String getKsiKonNazwaSkr() {
        return ksiKonNazwaSkr;
    }

    public void setKsiKonNazwaSkr(String ksiKonNazwaSkr) {
        this.ksiKonNazwaSkr = ksiKonNazwaSkr;
    }

    public Character getKsiSkreslony() {
        return ksiSkreslony;
    }

    public void setKsiSkreslony(Character ksiSkreslony) {
        this.ksiSkreslony = ksiSkreslony;
    }

    public Character getKsiOrygTyp() {
        return ksiOrygTyp;
    }

    public void setKsiOrygTyp(Character ksiOrygTyp) {
        this.ksiOrygTyp = ksiOrygTyp;
    }

    public Integer getKsiOrygSerial() {
        return ksiOrygSerial;
    }

    public void setKsiOrygSerial(Integer ksiOrygSerial) {
        this.ksiOrygSerial = ksiOrygSerial;
    }

    public Date getKsiCzas() {
        return ksiCzas;
    }

    public void setKsiCzas(Date ksiCzas) {
        this.ksiCzas = ksiCzas;
    }

    public String getKsiUsrUserid() {
        return ksiUsrUserid;
    }

    public void setKsiUsrUserid(String ksiUsrUserid) {
        this.ksiUsrUserid = ksiUsrUserid;
    }

    public Date getKsiCzasMod() {
        return ksiCzasMod;
    }

    public void setKsiCzasMod(Date ksiCzasMod) {
        this.ksiCzasMod = ksiCzasMod;
    }

    public String getKsiUsrUseridMod() {
        return ksiUsrUseridMod;
    }

    public void setKsiUsrUseridMod(String ksiUsrUseridMod) {
        this.ksiUsrUseridMod = ksiUsrUseridMod;
    }

    public BigDecimal getKsiDodNum1() {
        return ksiDodNum1;
    }

    public void setKsiDodNum1(BigDecimal ksiDodNum1) {
        this.ksiDodNum1 = ksiDodNum1;
    }

    public BigDecimal getKsiDodNum2() {
        return ksiDodNum2;
    }

    public void setKsiDodNum2(BigDecimal ksiDodNum2) {
        this.ksiDodNum2 = ksiDodNum2;
    }

    public BigDecimal getKsiDodNum3() {
        return ksiDodNum3;
    }

    public void setKsiDodNum3(BigDecimal ksiDodNum3) {
        this.ksiDodNum3 = ksiDodNum3;
    }

    public BigDecimal getKsiDodNum4() {
        return ksiDodNum4;
    }

    public void setKsiDodNum4(BigDecimal ksiDodNum4) {
        this.ksiDodNum4 = ksiDodNum4;
    }

    public Character getKsiDodChar1() {
        return ksiDodChar1;
    }

    public void setKsiDodChar1(Character ksiDodChar1) {
        this.ksiDodChar1 = ksiDodChar1;
    }

    public Character getKsiDodChar2() {
        return ksiDodChar2;
    }

    public void setKsiDodChar2(Character ksiDodChar2) {
        this.ksiDodChar2 = ksiDodChar2;
    }

    public String getKsiDodVchar1() {
        return ksiDodVchar1;
    }

    public void setKsiDodVchar1(String ksiDodVchar1) {
        this.ksiDodVchar1 = ksiDodVchar1;
    }

    public String getKsiDodVchar2() {
        return ksiDodVchar2;
    }

    public void setKsiDodVchar2(String ksiDodVchar2) {
        this.ksiDodVchar2 = ksiDodVchar2;
    }

    public Firma getKsiFirSerial() {
        return ksiFirSerial;
    }

    public void setKsiFirSerial(Firma ksiFirSerial) {
        this.ksiFirSerial = ksiFirSerial;
    }

    public Kontrahent getKsiKonSerial() {
        return ksiKonSerial;
    }

    public void setKsiKonSerial(Kontrahent ksiKonSerial) {
        this.ksiKonSerial = ksiKonSerial;
    }

    public Okres getKsiOkrSerial() {
        return ksiOkrSerial;
    }

    public void setKsiOkrSerial(Okres ksiOkrSerial) {
        this.ksiOkrSerial = ksiOkrSerial;
    }

    public Rok getKsiRokSerial() {
        return ksiRokSerial;
    }

    public void setKsiRokSerial(Rok ksiRokSerial) {
        this.ksiRokSerial = ksiRokSerial;
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
        hash += (ksiSerial != null ? ksiSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ksiegapir)) {
            return false;
        }
        Ksiegapir other = (Ksiegapir) object;
        if ((this.ksiSerial == null && other.ksiSerial != null) || (this.ksiSerial != null && !this.ksiSerial.equals(other.ksiSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Ksiegapir[ ksiSerial=" + ksiSerial + " ]";
    }
    
}
