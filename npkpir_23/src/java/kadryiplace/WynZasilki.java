/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "wyn_zasilki", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WynZasilki.findAll", query = "SELECT w FROM WynZasilki w"),
    @NamedQuery(name = "WynZasilki.findByWzaSerial", query = "SELECT w FROM WynZasilki w WHERE w.wzaSerial = :wzaSerial"),
    @NamedQuery(name = "WynZasilki.findByWzaOpis", query = "SELECT w FROM WynZasilki w WHERE w.wzaOpis = :wzaOpis"),
    @NamedQuery(name = "WynZasilki.findByWzaPodDoch", query = "SELECT w FROM WynZasilki w WHERE w.wzaPodDoch = :wzaPodDoch"),
    @NamedQuery(name = "WynZasilki.findByWzaZus", query = "SELECT w FROM WynZasilki w WHERE w.wzaZus = :wzaZus"),
    @NamedQuery(name = "WynZasilki.findByWzaZdrowotne", query = "SELECT w FROM WynZasilki w WHERE w.wzaZdrowotne = :wzaZdrowotne"),
    @NamedQuery(name = "WynZasilki.findByWzaChorWyp", query = "SELECT w FROM WynZasilki w WHERE w.wzaChorWyp = :wzaChorWyp"),
    @NamedQuery(name = "WynZasilki.findByWzaWyp", query = "SELECT w FROM WynZasilki w WHERE w.wzaWyp = :wzaWyp"),
    @NamedQuery(name = "WynZasilki.findByWzaDod1", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod1 = :wzaDod1"),
    @NamedQuery(name = "WynZasilki.findByWzaDod2", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod2 = :wzaDod2"),
    @NamedQuery(name = "WynZasilki.findByWzaDod3", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod3 = :wzaDod3"),
    @NamedQuery(name = "WynZasilki.findByWzaSystem", query = "SELECT w FROM WynZasilki w WHERE w.wzaSystem = :wzaSystem"),
    @NamedQuery(name = "WynZasilki.findByWzaPdstZasChor", query = "SELECT w FROM WynZasilki w WHERE w.wzaPdstZasChor = :wzaPdstZasChor"),
    @NamedQuery(name = "WynZasilki.findByWzaDod4", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod4 = :wzaDod4"),
    @NamedQuery(name = "WynZasilki.findByWzaDod5", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod5 = :wzaDod5"),
    @NamedQuery(name = "WynZasilki.findByWzaDod6", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod6 = :wzaDod6"),
    @NamedQuery(name = "WynZasilki.findByWzaDod7", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod7 = :wzaDod7"),
    @NamedQuery(name = "WynZasilki.findByWzaDod8", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod8 = :wzaDod8"),
    @NamedQuery(name = "WynZasilki.findByWzaNum1", query = "SELECT w FROM WynZasilki w WHERE w.wzaNum1 = :wzaNum1"),
    @NamedQuery(name = "WynZasilki.findByWzaNum2", query = "SELECT w FROM WynZasilki w WHERE w.wzaNum2 = :wzaNum2"),
    @NamedQuery(name = "WynZasilki.findByWzaDate1", query = "SELECT w FROM WynZasilki w WHERE w.wzaDate1 = :wzaDate1"),
    @NamedQuery(name = "WynZasilki.findByWzaDate2", query = "SELECT w FROM WynZasilki w WHERE w.wzaDate2 = :wzaDate2"),
    @NamedQuery(name = "WynZasilki.findByWzaVchar1", query = "SELECT w FROM WynZasilki w WHERE w.wzaVchar1 = :wzaVchar1"),
    @NamedQuery(name = "WynZasilki.findByWzaVchar2", query = "SELECT w FROM WynZasilki w WHERE w.wzaVchar2 = :wzaVchar2"),
    @NamedQuery(name = "WynZasilki.findByWzaInt1", query = "SELECT w FROM WynZasilki w WHERE w.wzaInt1 = :wzaInt1"),
    @NamedQuery(name = "WynZasilki.findByWzaInt2", query = "SELECT w FROM WynZasilki w WHERE w.wzaInt2 = :wzaInt2"),
    @NamedQuery(name = "WynZasilki.findByWzaPdstUrlop", query = "SELECT w FROM WynZasilki w WHERE w.wzaPdstUrlop = :wzaPdstUrlop"),
    @NamedQuery(name = "WynZasilki.findByWzaTyp", query = "SELECT w FROM WynZasilki w WHERE w.wzaTyp = :wzaTyp"),
    @NamedQuery(name = "WynZasilki.findByWzaDod9", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod9 = :wzaDod9"),
    @NamedQuery(name = "WynZasilki.findByWzaDod10", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod10 = :wzaDod10"),
    @NamedQuery(name = "WynZasilki.findByWzaDod11", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod11 = :wzaDod11"),
    @NamedQuery(name = "WynZasilki.findByWzaDod12", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod12 = :wzaDod12"),
    @NamedQuery(name = "WynZasilki.findByWzaDod13", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod13 = :wzaDod13"),
    @NamedQuery(name = "WynZasilki.findByWzaDod14", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod14 = :wzaDod14"),
    @NamedQuery(name = "WynZasilki.findByWzaDod15", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod15 = :wzaDod15"),
    @NamedQuery(name = "WynZasilki.findByWzaDod16", query = "SELECT w FROM WynZasilki w WHERE w.wzaDod16 = :wzaDod16")})
public class WynZasilki implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_serial", nullable = false)
    private Integer wzaSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "wza_opis", nullable = false, length = 32)
    private String wzaOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_pod_doch", nullable = false)
    private Character wzaPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_zus", nullable = false)
    private Character wzaZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_zdrowotne", nullable = false)
    private Character wzaZdrowotne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_chor_wyp", nullable = false)
    private Character wzaChorWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_wyp", nullable = false)
    private Character wzaWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_dod_1", nullable = false)
    private Character wzaDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_dod_2", nullable = false)
    private Character wzaDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_dod_3", nullable = false)
    private Character wzaDod3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_system", nullable = false)
    private Character wzaSystem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_pdst_zas_chor", nullable = false)
    private Character wzaPdstZasChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_dod_4", nullable = false)
    private Character wzaDod4;
    @Column(name = "wza_dod_5")
    private Character wzaDod5;
    @Column(name = "wza_dod_6")
    private Character wzaDod6;
    @Column(name = "wza_dod_7")
    private Character wzaDod7;
    @Column(name = "wza_dod_8")
    private Character wzaDod8;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wza_num_1", precision = 17, scale = 6)
    private BigDecimal wzaNum1;
    @Column(name = "wza_num_2", precision = 17, scale = 6)
    private BigDecimal wzaNum2;
    @Column(name = "wza_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wzaDate1;
    @Column(name = "wza_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wzaDate2;
    @Size(max = 64)
    @Column(name = "wza_vchar_1", length = 64)
    private String wzaVchar1;
    @Size(max = 64)
    @Column(name = "wza_vchar_2", length = 64)
    private String wzaVchar2;
    @Column(name = "wza_int_1")
    private Integer wzaInt1;
    @Column(name = "wza_int_2")
    private Integer wzaInt2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_pdst_urlop", nullable = false)
    private Character wzaPdstUrlop;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wza_typ", nullable = false)
    private Character wzaTyp;
    @Column(name = "wza_dod_9")
    private Character wzaDod9;
    @Column(name = "wza_dod_10")
    private Character wzaDod10;
    @Column(name = "wza_dod_11")
    private Character wzaDod11;
    @Column(name = "wza_dod_12")
    private Character wzaDod12;
    @Column(name = "wza_dod_13")
    private Character wzaDod13;
    @Column(name = "wza_dod_14")
    private Character wzaDod14;
    @Column(name = "wza_dod_15")
    private Character wzaDod15;
    @Column(name = "wza_dod_16")
    private Character wzaDod16;

    public WynZasilki() {
    }

    public WynZasilki(Integer wzaSerial) {
        this.wzaSerial = wzaSerial;
    }

    public WynZasilki(Integer wzaSerial, String wzaOpis, Character wzaPodDoch, Character wzaZus, Character wzaZdrowotne, Character wzaChorWyp, Character wzaWyp, Character wzaDod1, Character wzaDod2, Character wzaDod3, Character wzaSystem, Character wzaPdstZasChor, Character wzaDod4, Character wzaPdstUrlop, Character wzaTyp) {
        this.wzaSerial = wzaSerial;
        this.wzaOpis = wzaOpis;
        this.wzaPodDoch = wzaPodDoch;
        this.wzaZus = wzaZus;
        this.wzaZdrowotne = wzaZdrowotne;
        this.wzaChorWyp = wzaChorWyp;
        this.wzaWyp = wzaWyp;
        this.wzaDod1 = wzaDod1;
        this.wzaDod2 = wzaDod2;
        this.wzaDod3 = wzaDod3;
        this.wzaSystem = wzaSystem;
        this.wzaPdstZasChor = wzaPdstZasChor;
        this.wzaDod4 = wzaDod4;
        this.wzaPdstUrlop = wzaPdstUrlop;
        this.wzaTyp = wzaTyp;
    }

    public Integer getWzaSerial() {
        return wzaSerial;
    }

    public void setWzaSerial(Integer wzaSerial) {
        this.wzaSerial = wzaSerial;
    }

    public String getWzaOpis() {
        return wzaOpis;
    }

    public void setWzaOpis(String wzaOpis) {
        this.wzaOpis = wzaOpis;
    }

    public Character getWzaPodDoch() {
        return wzaPodDoch;
    }

    public void setWzaPodDoch(Character wzaPodDoch) {
        this.wzaPodDoch = wzaPodDoch;
    }

    public Character getWzaZus() {
        return wzaZus;
    }

    public void setWzaZus(Character wzaZus) {
        this.wzaZus = wzaZus;
    }

    public Character getWzaZdrowotne() {
        return wzaZdrowotne;
    }

    public void setWzaZdrowotne(Character wzaZdrowotne) {
        this.wzaZdrowotne = wzaZdrowotne;
    }

    public Character getWzaChorWyp() {
        return wzaChorWyp;
    }

    public void setWzaChorWyp(Character wzaChorWyp) {
        this.wzaChorWyp = wzaChorWyp;
    }

    public Character getWzaWyp() {
        return wzaWyp;
    }

    public void setWzaWyp(Character wzaWyp) {
        this.wzaWyp = wzaWyp;
    }

    public Character getWzaDod1() {
        return wzaDod1;
    }

    public void setWzaDod1(Character wzaDod1) {
        this.wzaDod1 = wzaDod1;
    }

    public Character getWzaDod2() {
        return wzaDod2;
    }

    public void setWzaDod2(Character wzaDod2) {
        this.wzaDod2 = wzaDod2;
    }

    public Character getWzaDod3() {
        return wzaDod3;
    }

    public void setWzaDod3(Character wzaDod3) {
        this.wzaDod3 = wzaDod3;
    }

    public Character getWzaSystem() {
        return wzaSystem;
    }

    public void setWzaSystem(Character wzaSystem) {
        this.wzaSystem = wzaSystem;
    }

    public Character getWzaPdstZasChor() {
        return wzaPdstZasChor;
    }

    public void setWzaPdstZasChor(Character wzaPdstZasChor) {
        this.wzaPdstZasChor = wzaPdstZasChor;
    }

    public Character getWzaDod4() {
        return wzaDod4;
    }

    public void setWzaDod4(Character wzaDod4) {
        this.wzaDod4 = wzaDod4;
    }

    public Character getWzaDod5() {
        return wzaDod5;
    }

    public void setWzaDod5(Character wzaDod5) {
        this.wzaDod5 = wzaDod5;
    }

    public Character getWzaDod6() {
        return wzaDod6;
    }

    public void setWzaDod6(Character wzaDod6) {
        this.wzaDod6 = wzaDod6;
    }

    public Character getWzaDod7() {
        return wzaDod7;
    }

    public void setWzaDod7(Character wzaDod7) {
        this.wzaDod7 = wzaDod7;
    }

    public Character getWzaDod8() {
        return wzaDod8;
    }

    public void setWzaDod8(Character wzaDod8) {
        this.wzaDod8 = wzaDod8;
    }

    public BigDecimal getWzaNum1() {
        return wzaNum1;
    }

    public void setWzaNum1(BigDecimal wzaNum1) {
        this.wzaNum1 = wzaNum1;
    }

    public BigDecimal getWzaNum2() {
        return wzaNum2;
    }

    public void setWzaNum2(BigDecimal wzaNum2) {
        this.wzaNum2 = wzaNum2;
    }

    public Date getWzaDate1() {
        return wzaDate1;
    }

    public void setWzaDate1(Date wzaDate1) {
        this.wzaDate1 = wzaDate1;
    }

    public Date getWzaDate2() {
        return wzaDate2;
    }

    public void setWzaDate2(Date wzaDate2) {
        this.wzaDate2 = wzaDate2;
    }

    public String getWzaVchar1() {
        return wzaVchar1;
    }

    public void setWzaVchar1(String wzaVchar1) {
        this.wzaVchar1 = wzaVchar1;
    }

    public String getWzaVchar2() {
        return wzaVchar2;
    }

    public void setWzaVchar2(String wzaVchar2) {
        this.wzaVchar2 = wzaVchar2;
    }

    public Integer getWzaInt1() {
        return wzaInt1;
    }

    public void setWzaInt1(Integer wzaInt1) {
        this.wzaInt1 = wzaInt1;
    }

    public Integer getWzaInt2() {
        return wzaInt2;
    }

    public void setWzaInt2(Integer wzaInt2) {
        this.wzaInt2 = wzaInt2;
    }

    public Character getWzaPdstUrlop() {
        return wzaPdstUrlop;
    }

    public void setWzaPdstUrlop(Character wzaPdstUrlop) {
        this.wzaPdstUrlop = wzaPdstUrlop;
    }

    public Character getWzaTyp() {
        return wzaTyp;
    }

    public void setWzaTyp(Character wzaTyp) {
        this.wzaTyp = wzaTyp;
    }

    public Character getWzaDod9() {
        return wzaDod9;
    }

    public void setWzaDod9(Character wzaDod9) {
        this.wzaDod9 = wzaDod9;
    }

    public Character getWzaDod10() {
        return wzaDod10;
    }

    public void setWzaDod10(Character wzaDod10) {
        this.wzaDod10 = wzaDod10;
    }

    public Character getWzaDod11() {
        return wzaDod11;
    }

    public void setWzaDod11(Character wzaDod11) {
        this.wzaDod11 = wzaDod11;
    }

    public Character getWzaDod12() {
        return wzaDod12;
    }

    public void setWzaDod12(Character wzaDod12) {
        this.wzaDod12 = wzaDod12;
    }

    public Character getWzaDod13() {
        return wzaDod13;
    }

    public void setWzaDod13(Character wzaDod13) {
        this.wzaDod13 = wzaDod13;
    }

    public Character getWzaDod14() {
        return wzaDod14;
    }

    public void setWzaDod14(Character wzaDod14) {
        this.wzaDod14 = wzaDod14;
    }

    public Character getWzaDod15() {
        return wzaDod15;
    }

    public void setWzaDod15(Character wzaDod15) {
        this.wzaDod15 = wzaDod15;
    }

    public Character getWzaDod16() {
        return wzaDod16;
    }

    public void setWzaDod16(Character wzaDod16) {
        this.wzaDod16 = wzaDod16;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wzaSerial != null ? wzaSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WynZasilki)) {
            return false;
        }
        WynZasilki other = (WynZasilki) object;
        if ((this.wzaSerial == null && other.wzaSerial != null) || (this.wzaSerial != null && !this.wzaSerial.equals(other.wzaSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.WynZasilki[ wzaSerial=" + wzaSerial + " ]";
    }
    
}
