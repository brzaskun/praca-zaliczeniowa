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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "wyn_potracenia", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WynPotracenia.findAll", query = "SELECT w FROM WynPotracenia w"),
    @NamedQuery(name = "WynPotracenia.findByWpoSerial", query = "SELECT w FROM WynPotracenia w WHERE w.wpoSerial = :wpoSerial"),
    @NamedQuery(name = "WynPotracenia.findByWpoOpis", query = "SELECT w FROM WynPotracenia w WHERE w.wpoOpis = :wpoOpis"),
    @NamedQuery(name = "WynPotracenia.findByWpoPodDoch", query = "SELECT w FROM WynPotracenia w WHERE w.wpoPodDoch = :wpoPodDoch"),
    @NamedQuery(name = "WynPotracenia.findByWpoZus", query = "SELECT w FROM WynPotracenia w WHERE w.wpoZus = :wpoZus"),
    @NamedQuery(name = "WynPotracenia.findByWpoZdrowotne", query = "SELECT w FROM WynPotracenia w WHERE w.wpoZdrowotne = :wpoZdrowotne"),
    @NamedQuery(name = "WynPotracenia.findByWpoChorWyp", query = "SELECT w FROM WynPotracenia w WHERE w.wpoChorWyp = :wpoChorWyp"),
    @NamedQuery(name = "WynPotracenia.findByWpoNumer", query = "SELECT w FROM WynPotracenia w WHERE w.wpoNumer = :wpoNumer"),
    @NamedQuery(name = "WynPotracenia.findByWpoWyp", query = "SELECT w FROM WynPotracenia w WHERE w.wpoWyp = :wpoWyp"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod1", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod1 = :wpoDod1"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod2", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod2 = :wpoDod2"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod3", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod3 = :wpoDod3"),
    @NamedQuery(name = "WynPotracenia.findByWpoSystem", query = "SELECT w FROM WynPotracenia w WHERE w.wpoSystem = :wpoSystem"),
    @NamedQuery(name = "WynPotracenia.findByWpoPpe", query = "SELECT w FROM WynPotracenia w WHERE w.wpoPpe = :wpoPpe"),
    @NamedQuery(name = "WynPotracenia.findByWpoPdstZasChor", query = "SELECT w FROM WynPotracenia w WHERE w.wpoPdstZasChor = :wpoPdstZasChor"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod4", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod4 = :wpoDod4"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod5", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod5 = :wpoDod5"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod6", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod6 = :wpoDod6"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod7", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod7 = :wpoDod7"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod8", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod8 = :wpoDod8"),
    @NamedQuery(name = "WynPotracenia.findByWpoNum1", query = "SELECT w FROM WynPotracenia w WHERE w.wpoNum1 = :wpoNum1"),
    @NamedQuery(name = "WynPotracenia.findByWpoNum2", query = "SELECT w FROM WynPotracenia w WHERE w.wpoNum2 = :wpoNum2"),
    @NamedQuery(name = "WynPotracenia.findByWpoDate1", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDate1 = :wpoDate1"),
    @NamedQuery(name = "WynPotracenia.findByWpoDate2", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDate2 = :wpoDate2"),
    @NamedQuery(name = "WynPotracenia.findByWpoVchar1", query = "SELECT w FROM WynPotracenia w WHERE w.wpoVchar1 = :wpoVchar1"),
    @NamedQuery(name = "WynPotracenia.findByWpoVchar2", query = "SELECT w FROM WynPotracenia w WHERE w.wpoVchar2 = :wpoVchar2"),
    @NamedQuery(name = "WynPotracenia.findByWpoInt1", query = "SELECT w FROM WynPotracenia w WHERE w.wpoInt1 = :wpoInt1"),
    @NamedQuery(name = "WynPotracenia.findByWpoInt2", query = "SELECT w FROM WynPotracenia w WHERE w.wpoInt2 = :wpoInt2"),
    @NamedQuery(name = "WynPotracenia.findByWpoPdstUrlop", query = "SELECT w FROM WynPotracenia w WHERE w.wpoPdstUrlop = :wpoPdstUrlop"),
    @NamedQuery(name = "WynPotracenia.findByWpoTyp", query = "SELECT w FROM WynPotracenia w WHERE w.wpoTyp = :wpoTyp"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod9", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod9 = :wpoDod9"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod10", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod10 = :wpoDod10"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod11", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod11 = :wpoDod11"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod12", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod12 = :wpoDod12"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod13", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod13 = :wpoDod13"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod14", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod14 = :wpoDod14"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod15", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod15 = :wpoDod15"),
    @NamedQuery(name = "WynPotracenia.findByWpoDod16", query = "SELECT w FROM WynPotracenia w WHERE w.wpoDod16 = :wpoDod16")})
public class WynPotracenia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_serial", nullable = false)
    private Integer wpoSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "wpo_opis", nullable = false, length = 64)
    private String wpoOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_pod_doch", nullable = false)
    private Character wpoPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_zus", nullable = false)
    private Character wpoZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_zdrowotne", nullable = false)
    private Character wpoZdrowotne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_chor_wyp", nullable = false)
    private Character wpoChorWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_numer", nullable = false)
    private short wpoNumer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_wyp", nullable = false)
    private Character wpoWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_dod_1", nullable = false)
    private Character wpoDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_dod_2", nullable = false)
    private Character wpoDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_dod_3", nullable = false)
    private Character wpoDod3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_system", nullable = false)
    private Character wpoSystem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_ppe", nullable = false)
    private Character wpoPpe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_pdst_zas_chor", nullable = false)
    private Character wpoPdstZasChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_dod_4", nullable = false)
    private Character wpoDod4;
    @Column(name = "wpo_dod_5")
    private Character wpoDod5;
    @Column(name = "wpo_dod_6")
    private Character wpoDod6;
    @Column(name = "wpo_dod_7")
    private Character wpoDod7;
    @Column(name = "wpo_dod_8")
    private Character wpoDod8;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wpo_num_1", precision = 17, scale = 6)
    private BigDecimal wpoNum1;
    @Column(name = "wpo_num_2", precision = 17, scale = 6)
    private BigDecimal wpoNum2;
    @Column(name = "wpo_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wpoDate1;
    @Column(name = "wpo_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wpoDate2;
    @Size(max = 64)
    @Column(name = "wpo_vchar_1", length = 64)
    private String wpoVchar1;
    @Size(max = 64)
    @Column(name = "wpo_vchar_2", length = 64)
    private String wpoVchar2;
    @Column(name = "wpo_int_1")
    private Integer wpoInt1;
    @Column(name = "wpo_int_2")
    private Integer wpoInt2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_pdst_urlop", nullable = false)
    private Character wpoPdstUrlop;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpo_typ", nullable = false)
    private Character wpoTyp;
    @Column(name = "wpo_dod_9")
    private Character wpoDod9;
    @Column(name = "wpo_dod_10")
    private Character wpoDod10;
    @Column(name = "wpo_dod_11")
    private Character wpoDod11;
    @Column(name = "wpo_dod_12")
    private Character wpoDod12;
    @Column(name = "wpo_dod_13")
    private Character wpoDod13;
    @Column(name = "wpo_dod_14")
    private Character wpoDod14;
    @Column(name = "wpo_dod_15")
    private Character wpoDod15;
    @Column(name = "wpo_dod_16")
    private Character wpoDod16;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "opoWpoSerial")
    private List<OsobaPot> osobaPotList;
    @JoinColumn(name = "wpo_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma wpoFirSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ppoWpoSerial")
    private List<PlacePot> placePotList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tpoWpoSerial")
    private List<TytulWpo> tytulWpoList;

    public WynPotracenia() {
    }

    public WynPotracenia(Integer wpoSerial) {
        this.wpoSerial = wpoSerial;
    }

    public WynPotracenia(Integer wpoSerial, String wpoOpis, Character wpoPodDoch, Character wpoZus, Character wpoZdrowotne, Character wpoChorWyp, short wpoNumer, Character wpoWyp, Character wpoDod1, Character wpoDod2, Character wpoDod3, Character wpoSystem, Character wpoPpe, Character wpoPdstZasChor, Character wpoDod4, Character wpoPdstUrlop, Character wpoTyp) {
        this.wpoSerial = wpoSerial;
        this.wpoOpis = wpoOpis;
        this.wpoPodDoch = wpoPodDoch;
        this.wpoZus = wpoZus;
        this.wpoZdrowotne = wpoZdrowotne;
        this.wpoChorWyp = wpoChorWyp;
        this.wpoNumer = wpoNumer;
        this.wpoWyp = wpoWyp;
        this.wpoDod1 = wpoDod1;
        this.wpoDod2 = wpoDod2;
        this.wpoDod3 = wpoDod3;
        this.wpoSystem = wpoSystem;
        this.wpoPpe = wpoPpe;
        this.wpoPdstZasChor = wpoPdstZasChor;
        this.wpoDod4 = wpoDod4;
        this.wpoPdstUrlop = wpoPdstUrlop;
        this.wpoTyp = wpoTyp;
    }

    public Integer getWpoSerial() {
        return wpoSerial;
    }

    public void setWpoSerial(Integer wpoSerial) {
        this.wpoSerial = wpoSerial;
    }

    public String getWpoOpis() {
        return wpoOpis;
    }

    public void setWpoOpis(String wpoOpis) {
        this.wpoOpis = wpoOpis;
    }

    public Character getWpoPodDoch() {
        return wpoPodDoch;
    }

    public void setWpoPodDoch(Character wpoPodDoch) {
        this.wpoPodDoch = wpoPodDoch;
    }

    public Character getWpoZus() {
        return wpoZus;
    }

    public void setWpoZus(Character wpoZus) {
        this.wpoZus = wpoZus;
    }

    public Character getWpoZdrowotne() {
        return wpoZdrowotne;
    }

    public void setWpoZdrowotne(Character wpoZdrowotne) {
        this.wpoZdrowotne = wpoZdrowotne;
    }

    public Character getWpoChorWyp() {
        return wpoChorWyp;
    }

    public void setWpoChorWyp(Character wpoChorWyp) {
        this.wpoChorWyp = wpoChorWyp;
    }

    public short getWpoNumer() {
        return wpoNumer;
    }

    public void setWpoNumer(short wpoNumer) {
        this.wpoNumer = wpoNumer;
    }

    public Character getWpoWyp() {
        return wpoWyp;
    }

    public void setWpoWyp(Character wpoWyp) {
        this.wpoWyp = wpoWyp;
    }

    public Character getWpoDod1() {
        return wpoDod1;
    }

    public void setWpoDod1(Character wpoDod1) {
        this.wpoDod1 = wpoDod1;
    }

    public Character getWpoDod2() {
        return wpoDod2;
    }

    public void setWpoDod2(Character wpoDod2) {
        this.wpoDod2 = wpoDod2;
    }

    public Character getWpoDod3() {
        return wpoDod3;
    }

    public void setWpoDod3(Character wpoDod3) {
        this.wpoDod3 = wpoDod3;
    }

    public Character getWpoSystem() {
        return wpoSystem;
    }

    public void setWpoSystem(Character wpoSystem) {
        this.wpoSystem = wpoSystem;
    }

    public Character getWpoPpe() {
        return wpoPpe;
    }

    public void setWpoPpe(Character wpoPpe) {
        this.wpoPpe = wpoPpe;
    }

    public Character getWpoPdstZasChor() {
        return wpoPdstZasChor;
    }

    public void setWpoPdstZasChor(Character wpoPdstZasChor) {
        this.wpoPdstZasChor = wpoPdstZasChor;
    }

    public Character getWpoDod4() {
        return wpoDod4;
    }

    public void setWpoDod4(Character wpoDod4) {
        this.wpoDod4 = wpoDod4;
    }

    public Character getWpoDod5() {
        return wpoDod5;
    }

    public void setWpoDod5(Character wpoDod5) {
        this.wpoDod5 = wpoDod5;
    }

    public Character getWpoDod6() {
        return wpoDod6;
    }

    public void setWpoDod6(Character wpoDod6) {
        this.wpoDod6 = wpoDod6;
    }

    public Character getWpoDod7() {
        return wpoDod7;
    }

    public void setWpoDod7(Character wpoDod7) {
        this.wpoDod7 = wpoDod7;
    }

    public Character getWpoDod8() {
        return wpoDod8;
    }

    public void setWpoDod8(Character wpoDod8) {
        this.wpoDod8 = wpoDod8;
    }

    public BigDecimal getWpoNum1() {
        return wpoNum1;
    }

    public void setWpoNum1(BigDecimal wpoNum1) {
        this.wpoNum1 = wpoNum1;
    }

    public BigDecimal getWpoNum2() {
        return wpoNum2;
    }

    public void setWpoNum2(BigDecimal wpoNum2) {
        this.wpoNum2 = wpoNum2;
    }

    public Date getWpoDate1() {
        return wpoDate1;
    }

    public void setWpoDate1(Date wpoDate1) {
        this.wpoDate1 = wpoDate1;
    }

    public Date getWpoDate2() {
        return wpoDate2;
    }

    public void setWpoDate2(Date wpoDate2) {
        this.wpoDate2 = wpoDate2;
    }

    public String getWpoVchar1() {
        return wpoVchar1;
    }

    public void setWpoVchar1(String wpoVchar1) {
        this.wpoVchar1 = wpoVchar1;
    }

    public String getWpoVchar2() {
        return wpoVchar2;
    }

    public void setWpoVchar2(String wpoVchar2) {
        this.wpoVchar2 = wpoVchar2;
    }

    public Integer getWpoInt1() {
        return wpoInt1;
    }

    public void setWpoInt1(Integer wpoInt1) {
        this.wpoInt1 = wpoInt1;
    }

    public Integer getWpoInt2() {
        return wpoInt2;
    }

    public void setWpoInt2(Integer wpoInt2) {
        this.wpoInt2 = wpoInt2;
    }

    public Character getWpoPdstUrlop() {
        return wpoPdstUrlop;
    }

    public void setWpoPdstUrlop(Character wpoPdstUrlop) {
        this.wpoPdstUrlop = wpoPdstUrlop;
    }

    public Character getWpoTyp() {
        return wpoTyp;
    }

    public void setWpoTyp(Character wpoTyp) {
        this.wpoTyp = wpoTyp;
    }

    public Character getWpoDod9() {
        return wpoDod9;
    }

    public void setWpoDod9(Character wpoDod9) {
        this.wpoDod9 = wpoDod9;
    }

    public Character getWpoDod10() {
        return wpoDod10;
    }

    public void setWpoDod10(Character wpoDod10) {
        this.wpoDod10 = wpoDod10;
    }

    public Character getWpoDod11() {
        return wpoDod11;
    }

    public void setWpoDod11(Character wpoDod11) {
        this.wpoDod11 = wpoDod11;
    }

    public Character getWpoDod12() {
        return wpoDod12;
    }

    public void setWpoDod12(Character wpoDod12) {
        this.wpoDod12 = wpoDod12;
    }

    public Character getWpoDod13() {
        return wpoDod13;
    }

    public void setWpoDod13(Character wpoDod13) {
        this.wpoDod13 = wpoDod13;
    }

    public Character getWpoDod14() {
        return wpoDod14;
    }

    public void setWpoDod14(Character wpoDod14) {
        this.wpoDod14 = wpoDod14;
    }

    public Character getWpoDod15() {
        return wpoDod15;
    }

    public void setWpoDod15(Character wpoDod15) {
        this.wpoDod15 = wpoDod15;
    }

    public Character getWpoDod16() {
        return wpoDod16;
    }

    public void setWpoDod16(Character wpoDod16) {
        this.wpoDod16 = wpoDod16;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList() {
        return osobaPotList;
    }

    public void setOsobaPotList(List<OsobaPot> osobaPotList) {
        this.osobaPotList = osobaPotList;
    }

    public Firma getWpoFirSerial() {
        return wpoFirSerial;
    }

    public void setWpoFirSerial(Firma wpoFirSerial) {
        this.wpoFirSerial = wpoFirSerial;
    }

    @XmlTransient
    public List<PlacePot> getPlacePotList() {
        return placePotList;
    }

    public void setPlacePotList(List<PlacePot> placePotList) {
        this.placePotList = placePotList;
    }

    @XmlTransient
    public List<TytulWpo> getTytulWpoList() {
        return tytulWpoList;
    }

    public void setTytulWpoList(List<TytulWpo> tytulWpoList) {
        this.tytulWpoList = tytulWpoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wpoSerial != null ? wpoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WynPotracenia)) {
            return false;
        }
        WynPotracenia other = (WynPotracenia) object;
        if ((this.wpoSerial == null && other.wpoSerial != null) || (this.wpoSerial != null && !this.wpoSerial.equals(other.wpoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.WynPotracenia[ wpoSerial=" + wpoSerial + " ]";
    }
    
}
