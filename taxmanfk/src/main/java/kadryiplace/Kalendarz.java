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
@Table(name = "kalendarz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kalendarz.findAll", query = "SELECT k FROM Kalendarz k"),
    @NamedQuery(name = "Kalendarz.findByKldSerial", query = "SELECT k FROM Kalendarz k WHERE k.kldSerial = :kldSerial"),
    @NamedQuery(name = "Kalendarz.findByKldOpis", query = "SELECT k FROM Kalendarz k WHERE k.kldOpis = :kldOpis"),
    @NamedQuery(name = "Kalendarz.findByKldDniPracy", query = "SELECT k FROM Kalendarz k WHERE k.kldDniPracy = :kldDniPracy"),
    @NamedQuery(name = "Kalendarz.findByKld1", query = "SELECT k FROM Kalendarz k WHERE k.kld1 = :kld1"),
    @NamedQuery(name = "Kalendarz.findByKld2", query = "SELECT k FROM Kalendarz k WHERE k.kld2 = :kld2"),
    @NamedQuery(name = "Kalendarz.findByKld3", query = "SELECT k FROM Kalendarz k WHERE k.kld3 = :kld3"),
    @NamedQuery(name = "Kalendarz.findByKld4", query = "SELECT k FROM Kalendarz k WHERE k.kld4 = :kld4"),
    @NamedQuery(name = "Kalendarz.findByKld5", query = "SELECT k FROM Kalendarz k WHERE k.kld5 = :kld5"),
    @NamedQuery(name = "Kalendarz.findByKld6", query = "SELECT k FROM Kalendarz k WHERE k.kld6 = :kld6"),
    @NamedQuery(name = "Kalendarz.findByKld7", query = "SELECT k FROM Kalendarz k WHERE k.kld7 = :kld7"),
    @NamedQuery(name = "Kalendarz.findByKld8", query = "SELECT k FROM Kalendarz k WHERE k.kld8 = :kld8"),
    @NamedQuery(name = "Kalendarz.findByKld9", query = "SELECT k FROM Kalendarz k WHERE k.kld9 = :kld9"),
    @NamedQuery(name = "Kalendarz.findByKld10", query = "SELECT k FROM Kalendarz k WHERE k.kld10 = :kld10"),
    @NamedQuery(name = "Kalendarz.findByKld11", query = "SELECT k FROM Kalendarz k WHERE k.kld11 = :kld11"),
    @NamedQuery(name = "Kalendarz.findByKld12", query = "SELECT k FROM Kalendarz k WHERE k.kld12 = :kld12"),
    @NamedQuery(name = "Kalendarz.findByKld13", query = "SELECT k FROM Kalendarz k WHERE k.kld13 = :kld13"),
    @NamedQuery(name = "Kalendarz.findByKld14", query = "SELECT k FROM Kalendarz k WHERE k.kld14 = :kld14"),
    @NamedQuery(name = "Kalendarz.findByKld15", query = "SELECT k FROM Kalendarz k WHERE k.kld15 = :kld15"),
    @NamedQuery(name = "Kalendarz.findByKld16", query = "SELECT k FROM Kalendarz k WHERE k.kld16 = :kld16"),
    @NamedQuery(name = "Kalendarz.findByKld17", query = "SELECT k FROM Kalendarz k WHERE k.kld17 = :kld17"),
    @NamedQuery(name = "Kalendarz.findByKld18", query = "SELECT k FROM Kalendarz k WHERE k.kld18 = :kld18"),
    @NamedQuery(name = "Kalendarz.findByKld19", query = "SELECT k FROM Kalendarz k WHERE k.kld19 = :kld19"),
    @NamedQuery(name = "Kalendarz.findByKld20", query = "SELECT k FROM Kalendarz k WHERE k.kld20 = :kld20"),
    @NamedQuery(name = "Kalendarz.findByKld21", query = "SELECT k FROM Kalendarz k WHERE k.kld21 = :kld21"),
    @NamedQuery(name = "Kalendarz.findByKld22", query = "SELECT k FROM Kalendarz k WHERE k.kld22 = :kld22"),
    @NamedQuery(name = "Kalendarz.findByKld23", query = "SELECT k FROM Kalendarz k WHERE k.kld23 = :kld23"),
    @NamedQuery(name = "Kalendarz.findByKld24", query = "SELECT k FROM Kalendarz k WHERE k.kld24 = :kld24"),
    @NamedQuery(name = "Kalendarz.findByKld25", query = "SELECT k FROM Kalendarz k WHERE k.kld25 = :kld25"),
    @NamedQuery(name = "Kalendarz.findByKld26", query = "SELECT k FROM Kalendarz k WHERE k.kld26 = :kld26"),
    @NamedQuery(name = "Kalendarz.findByKld27", query = "SELECT k FROM Kalendarz k WHERE k.kld27 = :kld27"),
    @NamedQuery(name = "Kalendarz.findByKld28", query = "SELECT k FROM Kalendarz k WHERE k.kld28 = :kld28"),
    @NamedQuery(name = "Kalendarz.findByKld29", query = "SELECT k FROM Kalendarz k WHERE k.kld29 = :kld29"),
    @NamedQuery(name = "Kalendarz.findByKld30", query = "SELECT k FROM Kalendarz k WHERE k.kld30 = :kld30"),
    @NamedQuery(name = "Kalendarz.findByKld31", query = "SELECT k FROM Kalendarz k WHERE k.kld31 = :kld31"),
    @NamedQuery(name = "Kalendarz.findByKldChar1", query = "SELECT k FROM Kalendarz k WHERE k.kldChar1 = :kldChar1"),
    @NamedQuery(name = "Kalendarz.findByKldChar2", query = "SELECT k FROM Kalendarz k WHERE k.kldChar2 = :kldChar2"),
    @NamedQuery(name = "Kalendarz.findByKldChar3", query = "SELECT k FROM Kalendarz k WHERE k.kldChar3 = :kldChar3"),
    @NamedQuery(name = "Kalendarz.findByKldChar4", query = "SELECT k FROM Kalendarz k WHERE k.kldChar4 = :kldChar4"),
    @NamedQuery(name = "Kalendarz.findByKldNum1", query = "SELECT k FROM Kalendarz k WHERE k.kldNum1 = :kldNum1"),
    @NamedQuery(name = "Kalendarz.findByKldNum2", query = "SELECT k FROM Kalendarz k WHERE k.kldNum2 = :kldNum2"),
    @NamedQuery(name = "Kalendarz.findByKldVchar1", query = "SELECT k FROM Kalendarz k WHERE k.kldVchar1 = :kldVchar1"),
    @NamedQuery(name = "Kalendarz.findByKldTyp", query = "SELECT k FROM Kalendarz k WHERE k.kldTyp = :kldTyp")})
public class Kalendarz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "kld_serial", nullable = false)
    private Integer kldSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "kld_opis", nullable = false, length = 64)
    private String kldOpis;
    @Column(name = "kld_dni_pracy")
    private Short kldDniPracy;
    @Column(name = "kld_1")
    private Character kld1;
    @Column(name = "kld_2")
    private Character kld2;
    @Column(name = "kld_3")
    private Character kld3;
    @Column(name = "kld_4")
    private Character kld4;
    @Column(name = "kld_5")
    private Character kld5;
    @Column(name = "kld_6")
    private Character kld6;
    @Column(name = "kld_7")
    private Character kld7;
    @Column(name = "kld_8")
    private Character kld8;
    @Column(name = "kld_9")
    private Character kld9;
    @Column(name = "kld_10")
    private Character kld10;
    @Column(name = "kld_11")
    private Character kld11;
    @Column(name = "kld_12")
    private Character kld12;
    @Column(name = "kld_13")
    private Character kld13;
    @Column(name = "kld_14")
    private Character kld14;
    @Column(name = "kld_15")
    private Character kld15;
    @Column(name = "kld_16")
    private Character kld16;
    @Column(name = "kld_17")
    private Character kld17;
    @Column(name = "kld_18")
    private Character kld18;
    @Column(name = "kld_19")
    private Character kld19;
    @Column(name = "kld_20")
    private Character kld20;
    @Column(name = "kld_21")
    private Character kld21;
    @Column(name = "kld_22")
    private Character kld22;
    @Column(name = "kld_23")
    private Character kld23;
    @Column(name = "kld_24")
    private Character kld24;
    @Column(name = "kld_25")
    private Character kld25;
    @Column(name = "kld_26")
    private Character kld26;
    @Column(name = "kld_27")
    private Character kld27;
    @Column(name = "kld_28")
    private Character kld28;
    @Column(name = "kld_29")
    private Character kld29;
    @Column(name = "kld_30")
    private Character kld30;
    @Column(name = "kld_31")
    private Character kld31;
    @Column(name = "kld_char_1")
    private Character kldChar1;
    @Column(name = "kld_char_2")
    private Character kldChar2;
    @Column(name = "kld_char_3")
    private Character kldChar3;
    @Column(name = "kld_char_4")
    private Character kldChar4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kld_num_1", precision = 17, scale = 6)
    private BigDecimal kldNum1;
    @Column(name = "kld_num_2", precision = 17, scale = 6)
    private BigDecimal kldNum2;
    @Size(max = 64)
    @Column(name = "kld_vchar_1", length = 64)
    private String kldVchar1;
    @Column(name = "kld_typ")
    private Character kldTyp;
    @OneToMany(mappedBy = "kagKldSerial")
    private List<KalGodz> kalGodzList;
    @JoinColumn(name = "kld_dep_serial", referencedColumnName = "dep_serial")
    @ManyToOne
    private Dep kldDepSerial;
    @JoinColumn(name = "kld_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres kldOkrSerial;
    @JoinColumn(name = "kld_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba kldOsoSerial;
    @JoinColumn(name = "kld_pio_serial", referencedColumnName = "pio_serial")
    @ManyToOne
    private Pion kldPioSerial;

    public Kalendarz() {
    }

    public Kalendarz(Integer kldSerial) {
        this.kldSerial = kldSerial;
    }

    public Kalendarz(Integer kldSerial, String kldOpis) {
        this.kldSerial = kldSerial;
        this.kldOpis = kldOpis;
    }

    public Integer getKldSerial() {
        return kldSerial;
    }

    public void setKldSerial(Integer kldSerial) {
        this.kldSerial = kldSerial;
    }

    public String getKldOpis() {
        return kldOpis;
    }

    public void setKldOpis(String kldOpis) {
        this.kldOpis = kldOpis;
    }

    public Short getKldDniPracy() {
        return kldDniPracy;
    }

    public void setKldDniPracy(Short kldDniPracy) {
        this.kldDniPracy = kldDniPracy;
    }

    public Character getKld1() {
        return kld1;
    }

    public void setKld1(Character kld1) {
        this.kld1 = kld1;
    }

    public Character getKld2() {
        return kld2;
    }

    public void setKld2(Character kld2) {
        this.kld2 = kld2;
    }

    public Character getKld3() {
        return kld3;
    }

    public void setKld3(Character kld3) {
        this.kld3 = kld3;
    }

    public Character getKld4() {
        return kld4;
    }

    public void setKld4(Character kld4) {
        this.kld4 = kld4;
    }

    public Character getKld5() {
        return kld5;
    }

    public void setKld5(Character kld5) {
        this.kld5 = kld5;
    }

    public Character getKld6() {
        return kld6;
    }

    public void setKld6(Character kld6) {
        this.kld6 = kld6;
    }

    public Character getKld7() {
        return kld7;
    }

    public void setKld7(Character kld7) {
        this.kld7 = kld7;
    }

    public Character getKld8() {
        return kld8;
    }

    public void setKld8(Character kld8) {
        this.kld8 = kld8;
    }

    public Character getKld9() {
        return kld9;
    }

    public void setKld9(Character kld9) {
        this.kld9 = kld9;
    }

    public Character getKld10() {
        return kld10;
    }

    public void setKld10(Character kld10) {
        this.kld10 = kld10;
    }

    public Character getKld11() {
        return kld11;
    }

    public void setKld11(Character kld11) {
        this.kld11 = kld11;
    }

    public Character getKld12() {
        return kld12;
    }

    public void setKld12(Character kld12) {
        this.kld12 = kld12;
    }

    public Character getKld13() {
        return kld13;
    }

    public void setKld13(Character kld13) {
        this.kld13 = kld13;
    }

    public Character getKld14() {
        return kld14;
    }

    public void setKld14(Character kld14) {
        this.kld14 = kld14;
    }

    public Character getKld15() {
        return kld15;
    }

    public void setKld15(Character kld15) {
        this.kld15 = kld15;
    }

    public Character getKld16() {
        return kld16;
    }

    public void setKld16(Character kld16) {
        this.kld16 = kld16;
    }

    public Character getKld17() {
        return kld17;
    }

    public void setKld17(Character kld17) {
        this.kld17 = kld17;
    }

    public Character getKld18() {
        return kld18;
    }

    public void setKld18(Character kld18) {
        this.kld18 = kld18;
    }

    public Character getKld19() {
        return kld19;
    }

    public void setKld19(Character kld19) {
        this.kld19 = kld19;
    }

    public Character getKld20() {
        return kld20;
    }

    public void setKld20(Character kld20) {
        this.kld20 = kld20;
    }

    public Character getKld21() {
        return kld21;
    }

    public void setKld21(Character kld21) {
        this.kld21 = kld21;
    }

    public Character getKld22() {
        return kld22;
    }

    public void setKld22(Character kld22) {
        this.kld22 = kld22;
    }

    public Character getKld23() {
        return kld23;
    }

    public void setKld23(Character kld23) {
        this.kld23 = kld23;
    }

    public Character getKld24() {
        return kld24;
    }

    public void setKld24(Character kld24) {
        this.kld24 = kld24;
    }

    public Character getKld25() {
        return kld25;
    }

    public void setKld25(Character kld25) {
        this.kld25 = kld25;
    }

    public Character getKld26() {
        return kld26;
    }

    public void setKld26(Character kld26) {
        this.kld26 = kld26;
    }

    public Character getKld27() {
        return kld27;
    }

    public void setKld27(Character kld27) {
        this.kld27 = kld27;
    }

    public Character getKld28() {
        return kld28;
    }

    public void setKld28(Character kld28) {
        this.kld28 = kld28;
    }

    public Character getKld29() {
        return kld29;
    }

    public void setKld29(Character kld29) {
        this.kld29 = kld29;
    }

    public Character getKld30() {
        return kld30;
    }

    public void setKld30(Character kld30) {
        this.kld30 = kld30;
    }

    public Character getKld31() {
        return kld31;
    }

    public void setKld31(Character kld31) {
        this.kld31 = kld31;
    }

    public Character getKldChar1() {
        return kldChar1;
    }

    public void setKldChar1(Character kldChar1) {
        this.kldChar1 = kldChar1;
    }

    public Character getKldChar2() {
        return kldChar2;
    }

    public void setKldChar2(Character kldChar2) {
        this.kldChar2 = kldChar2;
    }

    public Character getKldChar3() {
        return kldChar3;
    }

    public void setKldChar3(Character kldChar3) {
        this.kldChar3 = kldChar3;
    }

    public Character getKldChar4() {
        return kldChar4;
    }

    public void setKldChar4(Character kldChar4) {
        this.kldChar4 = kldChar4;
    }

    public BigDecimal getKldNum1() {
        return kldNum1;
    }

    public void setKldNum1(BigDecimal kldNum1) {
        this.kldNum1 = kldNum1;
    }

    public BigDecimal getKldNum2() {
        return kldNum2;
    }

    public void setKldNum2(BigDecimal kldNum2) {
        this.kldNum2 = kldNum2;
    }

    public String getKldVchar1() {
        return kldVchar1;
    }

    public void setKldVchar1(String kldVchar1) {
        this.kldVchar1 = kldVchar1;
    }

    public Character getKldTyp() {
        return kldTyp;
    }

    public void setKldTyp(Character kldTyp) {
        this.kldTyp = kldTyp;
    }

    @XmlTransient
    public List<KalGodz> getKalGodzList() {
        return kalGodzList;
    }

    public void setKalGodzList(List<KalGodz> kalGodzList) {
        this.kalGodzList = kalGodzList;
    }

    public Dep getKldDepSerial() {
        return kldDepSerial;
    }

    public void setKldDepSerial(Dep kldDepSerial) {
        this.kldDepSerial = kldDepSerial;
    }

    public Okres getKldOkrSerial() {
        return kldOkrSerial;
    }

    public void setKldOkrSerial(Okres kldOkrSerial) {
        this.kldOkrSerial = kldOkrSerial;
    }

    public Osoba getKldOsoSerial() {
        return kldOsoSerial;
    }

    public void setKldOsoSerial(Osoba kldOsoSerial) {
        this.kldOsoSerial = kldOsoSerial;
    }

    public Pion getKldPioSerial() {
        return kldPioSerial;
    }

    public void setKldPioSerial(Pion kldPioSerial) {
        this.kldPioSerial = kldPioSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kldSerial != null ? kldSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kalendarz)) {
            return false;
        }
        Kalendarz other = (Kalendarz) object;
        if ((this.kldSerial == null && other.kldSerial != null) || (this.kldSerial != null && !this.kldSerial.equals(other.kldSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Kalendarz[ kldSerial=" + kldSerial + " ]";
    }
    
}
