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
@Table(name = "wyn_kod_skl", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WynKodSkl.findAll", query = "SELECT w FROM WynKodSkl w"),
    @NamedQuery(name = "WynKodSkl.findByWksSerial", query = "SELECT w FROM WynKodSkl w WHERE w.wksSerial = :wksSerial"),
    @NamedQuery(name = "WynKodSkl.findByWksKod", query = "SELECT w FROM WynKodSkl w WHERE w.wksKod = :wksKod"),
    @NamedQuery(name = "WynKodSkl.findByWksOpis", query = "SELECT w FROM WynKodSkl w WHERE w.wksOpis = :wksOpis"),
    @NamedQuery(name = "WynKodSkl.findByWksOpisSkr", query = "SELECT w FROM WynKodSkl w WHERE w.wksOpisSkr = :wksOpisSkr"),
    @NamedQuery(name = "WynKodSkl.findByWksPodDoch", query = "SELECT w FROM WynKodSkl w WHERE w.wksPodDoch = :wksPodDoch"),
    @NamedQuery(name = "WynKodSkl.findByWksZus", query = "SELECT w FROM WynKodSkl w WHERE w.wksZus = :wksZus"),
    @NamedQuery(name = "WynKodSkl.findByWksZdrowotne", query = "SELECT w FROM WynKodSkl w WHERE w.wksZdrowotne = :wksZdrowotne"),
    @NamedQuery(name = "WynKodSkl.findByWksChorWyp", query = "SELECT w FROM WynKodSkl w WHERE w.wksChorWyp = :wksChorWyp"),
    @NamedQuery(name = "WynKodSkl.findByWksWyp", query = "SELECT w FROM WynKodSkl w WHERE w.wksWyp = :wksWyp"),
    @NamedQuery(name = "WynKodSkl.findByWksDod1", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod1 = :wksDod1"),
    @NamedQuery(name = "WynKodSkl.findByWksDod2", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod2 = :wksDod2"),
    @NamedQuery(name = "WynKodSkl.findByWksDod3", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod3 = :wksDod3"),
    @NamedQuery(name = "WynKodSkl.findByWksSystem", query = "SELECT w FROM WynKodSkl w WHERE w.wksSystem = :wksSystem"),
    @NamedQuery(name = "WynKodSkl.findByWksOkres", query = "SELECT w FROM WynKodSkl w WHERE w.wksOkres = :wksOkres"),
    @NamedQuery(name = "WynKodSkl.findByWksChorRed", query = "SELECT w FROM WynKodSkl w WHERE w.wksChorRed = :wksChorRed"),
    @NamedQuery(name = "WynKodSkl.findByWksPdstZasChor", query = "SELECT w FROM WynKodSkl w WHERE w.wksPdstZasChor = :wksPdstZasChor"),
    @NamedQuery(name = "WynKodSkl.findByWksDod4", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod4 = :wksDod4"),
    @NamedQuery(name = "WynKodSkl.findByWksDod5", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod5 = :wksDod5"),
    @NamedQuery(name = "WynKodSkl.findByWksDod6", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod6 = :wksDod6"),
    @NamedQuery(name = "WynKodSkl.findByWksDod7", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod7 = :wksDod7"),
    @NamedQuery(name = "WynKodSkl.findByWksDod8", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod8 = :wksDod8"),
    @NamedQuery(name = "WynKodSkl.findByWksNum1", query = "SELECT w FROM WynKodSkl w WHERE w.wksNum1 = :wksNum1"),
    @NamedQuery(name = "WynKodSkl.findByWksNum2", query = "SELECT w FROM WynKodSkl w WHERE w.wksNum2 = :wksNum2"),
    @NamedQuery(name = "WynKodSkl.findByWksDate1", query = "SELECT w FROM WynKodSkl w WHERE w.wksDate1 = :wksDate1"),
    @NamedQuery(name = "WynKodSkl.findByWksDate2", query = "SELECT w FROM WynKodSkl w WHERE w.wksDate2 = :wksDate2"),
    @NamedQuery(name = "WynKodSkl.findByWksVchar1", query = "SELECT w FROM WynKodSkl w WHERE w.wksVchar1 = :wksVchar1"),
    @NamedQuery(name = "WynKodSkl.findByWksVchar2", query = "SELECT w FROM WynKodSkl w WHERE w.wksVchar2 = :wksVchar2"),
    @NamedQuery(name = "WynKodSkl.findByWksPdstUrlop", query = "SELECT w FROM WynKodSkl w WHERE w.wksPdstUrlop = :wksPdstUrlop"),
    @NamedQuery(name = "WynKodSkl.findByWksTyp", query = "SELECT w FROM WynKodSkl w WHERE w.wksTyp = :wksTyp"),
    @NamedQuery(name = "WynKodSkl.findByWksInt1", query = "SELECT w FROM WynKodSkl w WHERE w.wksInt1 = :wksInt1"),
    @NamedQuery(name = "WynKodSkl.findByWksInt2", query = "SELECT w FROM WynKodSkl w WHERE w.wksInt2 = :wksInt2"),
    @NamedQuery(name = "WynKodSkl.findByWksDod9", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod9 = :wksDod9"),
    @NamedQuery(name = "WynKodSkl.findByWksDod10", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod10 = :wksDod10"),
    @NamedQuery(name = "WynKodSkl.findByWksDod11", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod11 = :wksDod11"),
    @NamedQuery(name = "WynKodSkl.findByWksDod12", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod12 = :wksDod12"),
    @NamedQuery(name = "WynKodSkl.findByWksDod13", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod13 = :wksDod13"),
    @NamedQuery(name = "WynKodSkl.findByWksDod14", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod14 = :wksDod14"),
    @NamedQuery(name = "WynKodSkl.findByWksDod15", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod15 = :wksDod15"),
    @NamedQuery(name = "WynKodSkl.findByWksDod16", query = "SELECT w FROM WynKodSkl w WHERE w.wksDod16 = :wksDod16")})
public class WynKodSkl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_serial", nullable = false)
    private Integer wksSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "wks_kod", nullable = false, length = 2)
    private String wksKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 96)
    @Column(name = "wks_opis", nullable = false, length = 96)
    private String wksOpis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "wks_opis_skr", nullable = false, length = 32)
    private String wksOpisSkr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_pod_doch", nullable = false)
    private Character wksPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_zus", nullable = false)
    private Character wksZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_zdrowotne", nullable = false)
    private Character wksZdrowotne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_chor_wyp", nullable = false)
    private Character wksChorWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_wyp", nullable = false)
    private Character wksWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_dod_1", nullable = false)
    private Character wksDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_dod_2", nullable = false)
    private Character wksDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_dod_3", nullable = false)
    private Character wksDod3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_system", nullable = false)
    private Character wksSystem;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_okres", nullable = false)
    private Character wksOkres;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_chor_red", nullable = false)
    private Character wksChorRed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_pdst_zas_chor", nullable = false)
    private Character wksPdstZasChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_dod_4", nullable = false)
    private Character wksDod4;
    @Column(name = "wks_dod_5")
    private Character wksDod5;
    @Column(name = "wks_dod_6")
    private Character wksDod6;
    @Column(name = "wks_dod_7")
    private Character wksDod7;
    @Column(name = "wks_dod_8")
    private Character wksDod8;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wks_num_1", precision = 17, scale = 6)
    private BigDecimal wksNum1;
    @Column(name = "wks_num_2", precision = 17, scale = 6)
    private BigDecimal wksNum2;
    @Column(name = "wks_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wksDate1;
    @Column(name = "wks_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wksDate2;
    @Size(max = 64)
    @Column(name = "wks_vchar_1", length = 64)
    private String wksVchar1;
    @Size(max = 64)
    @Column(name = "wks_vchar_2", length = 64)
    private String wksVchar2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_pdst_urlop", nullable = false)
    private Character wksPdstUrlop;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wks_typ", nullable = false)
    private Character wksTyp;
    @Column(name = "wks_int_1")
    private Integer wksInt1;
    @Column(name = "wks_int_2")
    private Integer wksInt2;
    @Column(name = "wks_dod_9")
    private Character wksDod9;
    @Column(name = "wks_dod_10")
    private Character wksDod10;
    @Column(name = "wks_dod_11")
    private Character wksDod11;
    @Column(name = "wks_dod_12")
    private Character wksDod12;
    @Column(name = "wks_dod_13")
    private Character wksDod13;
    @Column(name = "wks_dod_14")
    private Character wksDod14;
    @Column(name = "wks_dod_15")
    private Character wksDod15;
    @Column(name = "wks_dod_16")
    private Character wksDod16;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sklWksSerial")
    private List<PlaceSkl> placeSklList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tksWksSerial")
    private List<TytulWks> tytulWksList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pzlWksSerial")
    private List<PlaceZlec> placeZlecList;
    @OneToMany(mappedBy = "ozlWksSerial")
    private List<OsobaZlec> osobaZlecList;
    @OneToMany(mappedBy = "ossWksSerial")
    private List<OsobaSkl> osobaSklList;

    public WynKodSkl() {
    }

    public WynKodSkl(Integer wksSerial) {
        this.wksSerial = wksSerial;
    }

    public WynKodSkl(Integer wksSerial, String wksKod, String wksOpis, String wksOpisSkr, Character wksPodDoch, Character wksZus, Character wksZdrowotne, Character wksChorWyp, Character wksWyp, Character wksDod1, Character wksDod2, Character wksDod3, Character wksSystem, Character wksOkres, Character wksChorRed, Character wksPdstZasChor, Character wksDod4, Character wksPdstUrlop, Character wksTyp) {
        this.wksSerial = wksSerial;
        this.wksKod = wksKod;
        this.wksOpis = wksOpis;
        this.wksOpisSkr = wksOpisSkr;
        this.wksPodDoch = wksPodDoch;
        this.wksZus = wksZus;
        this.wksZdrowotne = wksZdrowotne;
        this.wksChorWyp = wksChorWyp;
        this.wksWyp = wksWyp;
        this.wksDod1 = wksDod1;
        this.wksDod2 = wksDod2;
        this.wksDod3 = wksDod3;
        this.wksSystem = wksSystem;
        this.wksOkres = wksOkres;
        this.wksChorRed = wksChorRed;
        this.wksPdstZasChor = wksPdstZasChor;
        this.wksDod4 = wksDod4;
        this.wksPdstUrlop = wksPdstUrlop;
        this.wksTyp = wksTyp;
    }

    public Integer getWksSerial() {
        return wksSerial;
    }

    public void setWksSerial(Integer wksSerial) {
        this.wksSerial = wksSerial;
    }

    public String getWksKod() {
        return wksKod;
    }

    public void setWksKod(String wksKod) {
        this.wksKod = wksKod;
    }

    public String getWksOpis() {
        return wksOpis;
    }

    public void setWksOpis(String wksOpis) {
        this.wksOpis = wksOpis;
    }

    public String getWksOpisSkr() {
        return wksOpisSkr;
    }

    public void setWksOpisSkr(String wksOpisSkr) {
        this.wksOpisSkr = wksOpisSkr;
    }

    public Character getWksPodDoch() {
        return wksPodDoch;
    }

    public void setWksPodDoch(Character wksPodDoch) {
        this.wksPodDoch = wksPodDoch;
    }

    public Character getWksZus() {
        return wksZus;
    }

    public void setWksZus(Character wksZus) {
        this.wksZus = wksZus;
    }

    public Character getWksZdrowotne() {
        return wksZdrowotne;
    }

    public void setWksZdrowotne(Character wksZdrowotne) {
        this.wksZdrowotne = wksZdrowotne;
    }

    public Character getWksChorWyp() {
        return wksChorWyp;
    }

    public void setWksChorWyp(Character wksChorWyp) {
        this.wksChorWyp = wksChorWyp;
    }

    public Character getWksWyp() {
        return wksWyp;
    }

    public void setWksWyp(Character wksWyp) {
        this.wksWyp = wksWyp;
    }

    public Character getWksDod1() {
        return wksDod1;
    }

    public void setWksDod1(Character wksDod1) {
        this.wksDod1 = wksDod1;
    }

    public Character getWksDod2() {
        return wksDod2;
    }

    public void setWksDod2(Character wksDod2) {
        this.wksDod2 = wksDod2;
    }

    public Character getWksDod3() {
        return wksDod3;
    }

    public void setWksDod3(Character wksDod3) {
        this.wksDod3 = wksDod3;
    }

    public Character getWksSystem() {
        return wksSystem;
    }

    public void setWksSystem(Character wksSystem) {
        this.wksSystem = wksSystem;
    }

    public Character getWksOkres() {
        return wksOkres;
    }

    public void setWksOkres(Character wksOkres) {
        this.wksOkres = wksOkres;
    }

    public Character getWksChorRed() {
        return wksChorRed;
    }

    public void setWksChorRed(Character wksChorRed) {
        this.wksChorRed = wksChorRed;
    }

    public Character getWksPdstZasChor() {
        return wksPdstZasChor;
    }

    public void setWksPdstZasChor(Character wksPdstZasChor) {
        this.wksPdstZasChor = wksPdstZasChor;
    }

    public Character getWksDod4() {
        return wksDod4;
    }

    public void setWksDod4(Character wksDod4) {
        this.wksDod4 = wksDod4;
    }

    public Character getWksDod5() {
        return wksDod5;
    }

    public void setWksDod5(Character wksDod5) {
        this.wksDod5 = wksDod5;
    }

    public Character getWksDod6() {
        return wksDod6;
    }

    public void setWksDod6(Character wksDod6) {
        this.wksDod6 = wksDod6;
    }

    public Character getWksDod7() {
        return wksDod7;
    }

    public void setWksDod7(Character wksDod7) {
        this.wksDod7 = wksDod7;
    }

    public Character getWksDod8() {
        return wksDod8;
    }

    public void setWksDod8(Character wksDod8) {
        this.wksDod8 = wksDod8;
    }

    public BigDecimal getWksNum1() {
        return wksNum1;
    }

    public void setWksNum1(BigDecimal wksNum1) {
        this.wksNum1 = wksNum1;
    }

    public BigDecimal getWksNum2() {
        return wksNum2;
    }

    public void setWksNum2(BigDecimal wksNum2) {
        this.wksNum2 = wksNum2;
    }

    public Date getWksDate1() {
        return wksDate1;
    }

    public void setWksDate1(Date wksDate1) {
        this.wksDate1 = wksDate1;
    }

    public Date getWksDate2() {
        return wksDate2;
    }

    public void setWksDate2(Date wksDate2) {
        this.wksDate2 = wksDate2;
    }

    public String getWksVchar1() {
        return wksVchar1;
    }

    public void setWksVchar1(String wksVchar1) {
        this.wksVchar1 = wksVchar1;
    }

    public String getWksVchar2() {
        return wksVchar2;
    }

    public void setWksVchar2(String wksVchar2) {
        this.wksVchar2 = wksVchar2;
    }

    public Character getWksPdstUrlop() {
        return wksPdstUrlop;
    }

    public void setWksPdstUrlop(Character wksPdstUrlop) {
        this.wksPdstUrlop = wksPdstUrlop;
    }

    public Character getWksTyp() {
        return wksTyp;
    }

    public void setWksTyp(Character wksTyp) {
        this.wksTyp = wksTyp;
    }

    public Integer getWksInt1() {
        return wksInt1;
    }

    public void setWksInt1(Integer wksInt1) {
        this.wksInt1 = wksInt1;
    }

    public Integer getWksInt2() {
        return wksInt2;
    }

    public void setWksInt2(Integer wksInt2) {
        this.wksInt2 = wksInt2;
    }

    public Character getWksDod9() {
        return wksDod9;
    }

    public void setWksDod9(Character wksDod9) {
        this.wksDod9 = wksDod9;
    }

    public Character getWksDod10() {
        return wksDod10;
    }

    public void setWksDod10(Character wksDod10) {
        this.wksDod10 = wksDod10;
    }

    public Character getWksDod11() {
        return wksDod11;
    }

    public void setWksDod11(Character wksDod11) {
        this.wksDod11 = wksDod11;
    }

    public Character getWksDod12() {
        return wksDod12;
    }

    public void setWksDod12(Character wksDod12) {
        this.wksDod12 = wksDod12;
    }

    public Character getWksDod13() {
        return wksDod13;
    }

    public void setWksDod13(Character wksDod13) {
        this.wksDod13 = wksDod13;
    }

    public Character getWksDod14() {
        return wksDod14;
    }

    public void setWksDod14(Character wksDod14) {
        this.wksDod14 = wksDod14;
    }

    public Character getWksDod15() {
        return wksDod15;
    }

    public void setWksDod15(Character wksDod15) {
        this.wksDod15 = wksDod15;
    }

    public Character getWksDod16() {
        return wksDod16;
    }

    public void setWksDod16(Character wksDod16) {
        this.wksDod16 = wksDod16;
    }

    @XmlTransient
    public List<PlaceSkl> getPlaceSklList() {
        return placeSklList;
    }

    public void setPlaceSklList(List<PlaceSkl> placeSklList) {
        this.placeSklList = placeSklList;
    }

    @XmlTransient
    public List<TytulWks> getTytulWksList() {
        return tytulWksList;
    }

    public void setTytulWksList(List<TytulWks> tytulWksList) {
        this.tytulWksList = tytulWksList;
    }

    @XmlTransient
    public List<PlaceZlec> getPlaceZlecList() {
        return placeZlecList;
    }

    public void setPlaceZlecList(List<PlaceZlec> placeZlecList) {
        this.placeZlecList = placeZlecList;
    }

    @XmlTransient
    public List<OsobaZlec> getOsobaZlecList() {
        return osobaZlecList;
    }

    public void setOsobaZlecList(List<OsobaZlec> osobaZlecList) {
        this.osobaZlecList = osobaZlecList;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList() {
        return osobaSklList;
    }

    public void setOsobaSklList(List<OsobaSkl> osobaSklList) {
        this.osobaSklList = osobaSklList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wksSerial != null ? wksSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WynKodSkl)) {
            return false;
        }
        WynKodSkl other = (WynKodSkl) object;
        if ((this.wksSerial == null && other.wksSerial != null) || (this.wksSerial != null && !this.wksSerial.equals(other.wksSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.WynKodSkl[ wksSerial=" + wksSerial + " ]";
    }
    
}
