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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "tytul", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tytul.findAll", query = "SELECT t FROM Tytul t"),
    @NamedQuery(name = "Tytul.findByTytSerial", query = "SELECT t FROM Tytul t WHERE t.tytSerial = :tytSerial"),
    @NamedQuery(name = "Tytul.findByTytOpis", query = "SELECT t FROM Tytul t WHERE t.tytOpis = :tytOpis"),
    @NamedQuery(name = "Tytul.findByTytDomyslne", query = "SELECT t FROM Tytul t WHERE t.tytDomyslne = :tytDomyslne"),
    @NamedQuery(name = "Tytul.findByTytZrodlo", query = "SELECT t FROM Tytul t WHERE t.tytZrodlo = :tytZrodlo"),
    @NamedQuery(name = "Tytul.findByTytKolejnosc", query = "SELECT t FROM Tytul t WHERE t.tytKolejnosc = :tytKolejnosc"),
    @NamedQuery(name = "Tytul.findByTytZasSwiDni", query = "SELECT t FROM Tytul t WHERE t.tytZasSwiDni = :tytZasSwiDni"),
    @NamedQuery(name = "Tytul.findByTytWNaturze", query = "SELECT t FROM Tytul t WHERE t.tytWNaturze = :tytWNaturze"),
    @NamedQuery(name = "Tytul.findByTytTyp", query = "SELECT t FROM Tytul t WHERE t.tytTyp = :tytTyp"),
    @NamedQuery(name = "Tytul.findByTytSystem", query = "SELECT t FROM Tytul t WHERE t.tytSystem = :tytSystem"),
    @NamedQuery(name = "Tytul.findByTytOkres", query = "SELECT t FROM Tytul t WHERE t.tytOkres = :tytOkres"),
    @NamedQuery(name = "Tytul.findByTytStatus", query = "SELECT t FROM Tytul t WHERE t.tytStatus = :tytStatus"),
    @NamedQuery(name = "Tytul.findByTytDod1", query = "SELECT t FROM Tytul t WHERE t.tytDod1 = :tytDod1"),
    @NamedQuery(name = "Tytul.findByTytDod2", query = "SELECT t FROM Tytul t WHERE t.tytDod2 = :tytDod2"),
    @NamedQuery(name = "Tytul.findByTytDod3", query = "SELECT t FROM Tytul t WHERE t.tytDod3 = :tytDod3"),
    @NamedQuery(name = "Tytul.findByTytDod4", query = "SELECT t FROM Tytul t WHERE t.tytDod4 = :tytDod4"),
    @NamedQuery(name = "Tytul.findByTytDod5", query = "SELECT t FROM Tytul t WHERE t.tytDod5 = :tytDod5"),
    @NamedQuery(name = "Tytul.findByTytDod6", query = "SELECT t FROM Tytul t WHERE t.tytDod6 = :tytDod6"),
    @NamedQuery(name = "Tytul.findByTytDod7", query = "SELECT t FROM Tytul t WHERE t.tytDod7 = :tytDod7"),
    @NamedQuery(name = "Tytul.findByTytDod8", query = "SELECT t FROM Tytul t WHERE t.tytDod8 = :tytDod8"),
    @NamedQuery(name = "Tytul.findByTytInt1", query = "SELECT t FROM Tytul t WHERE t.tytInt1 = :tytInt1"),
    @NamedQuery(name = "Tytul.findByTytInt2", query = "SELECT t FROM Tytul t WHERE t.tytInt2 = :tytInt2"),
    @NamedQuery(name = "Tytul.findByTytVchar1", query = "SELECT t FROM Tytul t WHERE t.tytVchar1 = :tytVchar1"),
    @NamedQuery(name = "Tytul.findByTytVchar2", query = "SELECT t FROM Tytul t WHERE t.tytVchar2 = :tytVchar2"),
    @NamedQuery(name = "Tytul.findByTytNum1", query = "SELECT t FROM Tytul t WHERE t.tytNum1 = :tytNum1"),
    @NamedQuery(name = "Tytul.findByTytNum2", query = "SELECT t FROM Tytul t WHERE t.tytNum2 = :tytNum2"),
    @NamedQuery(name = "Tytul.findByTytNum3", query = "SELECT t FROM Tytul t WHERE t.tytNum3 = :tytNum3"),
    @NamedQuery(name = "Tytul.findByTytNum4", query = "SELECT t FROM Tytul t WHERE t.tytNum4 = :tytNum4"),
    @NamedQuery(name = "Tytul.findByTytDod9", query = "SELECT t FROM Tytul t WHERE t.tytDod9 = :tytDod9"),
    @NamedQuery(name = "Tytul.findByTytDod10", query = "SELECT t FROM Tytul t WHERE t.tytDod10 = :tytDod10"),
    @NamedQuery(name = "Tytul.findByTytDod11", query = "SELECT t FROM Tytul t WHERE t.tytDod11 = :tytDod11"),
    @NamedQuery(name = "Tytul.findByTytDod12", query = "SELECT t FROM Tytul t WHERE t.tytDod12 = :tytDod12")})
public class Tytul implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tyt_serial", nullable = false)
    private Integer tytSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "tyt_opis", nullable = false, length = 64)
    private String tytOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tyt_domyslne", nullable = false)
    private Character tytDomyslne;
    @Size(max = 2)
    @Column(name = "tyt_zrodlo", length = 2)
    private String tytZrodlo;
    @Column(name = "tyt_kolejnosc")
    private Short tytKolejnosc;
    @Column(name = "tyt_zas_swi_dni")
    private Character tytZasSwiDni;
    @Column(name = "tyt_w_naturze")
    private Character tytWNaturze;
    @Column(name = "tyt_typ")
    private Character tytTyp;
    @Column(name = "tyt_system")
    private Character tytSystem;
    @Column(name = "tyt_okres")
    private Character tytOkres;
    @Column(name = "tyt_status")
    private Character tytStatus;
    @Column(name = "tyt_dod_1")
    private Character tytDod1;
    @Column(name = "tyt_dod_2")
    private Character tytDod2;
    @Column(name = "tyt_dod_3")
    private Character tytDod3;
    @Column(name = "tyt_dod_4")
    private Character tytDod4;
    @Column(name = "tyt_dod_5")
    private Character tytDod5;
    @Column(name = "tyt_dod_6")
    private Character tytDod6;
    @Column(name = "tyt_dod_7")
    private Character tytDod7;
    @Column(name = "tyt_dod_8")
    private Character tytDod8;
    @Column(name = "tyt_int_1")
    private Integer tytInt1;
    @Column(name = "tyt_int_2")
    private Integer tytInt2;
    @Size(max = 64)
    @Column(name = "tyt_vchar_1", length = 64)
    private String tytVchar1;
    @Size(max = 64)
    @Column(name = "tyt_vchar_2", length = 64)
    private String tytVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tyt_num_1", precision = 17, scale = 6)
    private BigDecimal tytNum1;
    @Column(name = "tyt_num_2", precision = 17, scale = 6)
    private BigDecimal tytNum2;
    @Column(name = "tyt_num_3", precision = 17, scale = 6)
    private BigDecimal tytNum3;
    @Column(name = "tyt_num_4", precision = 17, scale = 6)
    private BigDecimal tytNum4;
    @Column(name = "tyt_dod_9")
    private Character tytDod9;
    @Column(name = "tyt_dod_10")
    private Character tytDod10;
    @Column(name = "tyt_dod_11")
    private Character tytDod11;
    @Column(name = "tyt_dod_12")
    private Character tytDod12;
    @OneToMany(mappedBy = "opoTytSerial")
    private List<OsobaPot> osobaPotList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tkpTytSerial")
    private List<TytulWkp> tytulWkpList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tksTytSerial")
    private List<TytulWks> tytulWksList;
    @OneToMany(mappedBy = "lisTytSerial")
    private List<Listy> listyList;
    @OneToMany(mappedBy = "ossTytSerial")
    private List<OsobaSkl> osobaSklList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tpoTytSerial")
    private List<TytulWpo> tytulWpoList;
    @OneToMany(mappedBy = "ospTytSerial")
    private List<OsobaPrz> osobaPrzList;

    public Tytul() {
    }

    public Tytul(Integer tytSerial) {
        this.tytSerial = tytSerial;
    }

    public Tytul(Integer tytSerial, String tytOpis, Character tytDomyslne) {
        this.tytSerial = tytSerial;
        this.tytOpis = tytOpis;
        this.tytDomyslne = tytDomyslne;
    }

    public Integer getTytSerial() {
        return tytSerial;
    }

    public void setTytSerial(Integer tytSerial) {
        this.tytSerial = tytSerial;
    }

    public String getTytOpis() {
        return tytOpis;
    }

    public void setTytOpis(String tytOpis) {
        this.tytOpis = tytOpis;
    }

    public Character getTytDomyslne() {
        return tytDomyslne;
    }

    public void setTytDomyslne(Character tytDomyslne) {
        this.tytDomyslne = tytDomyslne;
    }

    public String getTytZrodlo() {
        return tytZrodlo;
    }

    public void setTytZrodlo(String tytZrodlo) {
        this.tytZrodlo = tytZrodlo;
    }

    public Short getTytKolejnosc() {
        return tytKolejnosc;
    }

    public void setTytKolejnosc(Short tytKolejnosc) {
        this.tytKolejnosc = tytKolejnosc;
    }

    public Character getTytZasSwiDni() {
        return tytZasSwiDni;
    }

    public void setTytZasSwiDni(Character tytZasSwiDni) {
        this.tytZasSwiDni = tytZasSwiDni;
    }

    public Character getTytWNaturze() {
        return tytWNaturze;
    }

    public void setTytWNaturze(Character tytWNaturze) {
        this.tytWNaturze = tytWNaturze;
    }

    public Character getTytTyp() {
        return tytTyp;
    }

    public void setTytTyp(Character tytTyp) {
        this.tytTyp = tytTyp;
    }

    public Character getTytSystem() {
        return tytSystem;
    }

    public void setTytSystem(Character tytSystem) {
        this.tytSystem = tytSystem;
    }

    public Character getTytOkres() {
        return tytOkres;
    }

    public void setTytOkres(Character tytOkres) {
        this.tytOkres = tytOkres;
    }

    public Character getTytStatus() {
        return tytStatus;
    }

    public void setTytStatus(Character tytStatus) {
        this.tytStatus = tytStatus;
    }

    public Character getTytDod1() {
        return tytDod1;
    }

    public void setTytDod1(Character tytDod1) {
        this.tytDod1 = tytDod1;
    }

    public Character getTytDod2() {
        return tytDod2;
    }

    public void setTytDod2(Character tytDod2) {
        this.tytDod2 = tytDod2;
    }

    public Character getTytDod3() {
        return tytDod3;
    }

    public void setTytDod3(Character tytDod3) {
        this.tytDod3 = tytDod3;
    }

    public Character getTytDod4() {
        return tytDod4;
    }

    public void setTytDod4(Character tytDod4) {
        this.tytDod4 = tytDod4;
    }

    public Character getTytDod5() {
        return tytDod5;
    }

    public void setTytDod5(Character tytDod5) {
        this.tytDod5 = tytDod5;
    }

    public Character getTytDod6() {
        return tytDod6;
    }

    public void setTytDod6(Character tytDod6) {
        this.tytDod6 = tytDod6;
    }

    public Character getTytDod7() {
        return tytDod7;
    }

    public void setTytDod7(Character tytDod7) {
        this.tytDod7 = tytDod7;
    }

    public Character getTytDod8() {
        return tytDod8;
    }

    public void setTytDod8(Character tytDod8) {
        this.tytDod8 = tytDod8;
    }

    public Integer getTytInt1() {
        return tytInt1;
    }

    public void setTytInt1(Integer tytInt1) {
        this.tytInt1 = tytInt1;
    }

    public Integer getTytInt2() {
        return tytInt2;
    }

    public void setTytInt2(Integer tytInt2) {
        this.tytInt2 = tytInt2;
    }

    public String getTytVchar1() {
        return tytVchar1;
    }

    public void setTytVchar1(String tytVchar1) {
        this.tytVchar1 = tytVchar1;
    }

    public String getTytVchar2() {
        return tytVchar2;
    }

    public void setTytVchar2(String tytVchar2) {
        this.tytVchar2 = tytVchar2;
    }

    public BigDecimal getTytNum1() {
        return tytNum1;
    }

    public void setTytNum1(BigDecimal tytNum1) {
        this.tytNum1 = tytNum1;
    }

    public BigDecimal getTytNum2() {
        return tytNum2;
    }

    public void setTytNum2(BigDecimal tytNum2) {
        this.tytNum2 = tytNum2;
    }

    public BigDecimal getTytNum3() {
        return tytNum3;
    }

    public void setTytNum3(BigDecimal tytNum3) {
        this.tytNum3 = tytNum3;
    }

    public BigDecimal getTytNum4() {
        return tytNum4;
    }

    public void setTytNum4(BigDecimal tytNum4) {
        this.tytNum4 = tytNum4;
    }

    public Character getTytDod9() {
        return tytDod9;
    }

    public void setTytDod9(Character tytDod9) {
        this.tytDod9 = tytDod9;
    }

    public Character getTytDod10() {
        return tytDod10;
    }

    public void setTytDod10(Character tytDod10) {
        this.tytDod10 = tytDod10;
    }

    public Character getTytDod11() {
        return tytDod11;
    }

    public void setTytDod11(Character tytDod11) {
        this.tytDod11 = tytDod11;
    }

    public Character getTytDod12() {
        return tytDod12;
    }

    public void setTytDod12(Character tytDod12) {
        this.tytDod12 = tytDod12;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList() {
        return osobaPotList;
    }

    public void setOsobaPotList(List<OsobaPot> osobaPotList) {
        this.osobaPotList = osobaPotList;
    }

    @XmlTransient
    public List<TytulWkp> getTytulWkpList() {
        return tytulWkpList;
    }

    public void setTytulWkpList(List<TytulWkp> tytulWkpList) {
        this.tytulWkpList = tytulWkpList;
    }

    @XmlTransient
    public List<TytulWks> getTytulWksList() {
        return tytulWksList;
    }

    public void setTytulWksList(List<TytulWks> tytulWksList) {
        this.tytulWksList = tytulWksList;
    }

    @XmlTransient
    public List<Listy> getListyList() {
        return listyList;
    }

    public void setListyList(List<Listy> listyList) {
        this.listyList = listyList;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList() {
        return osobaSklList;
    }

    public void setOsobaSklList(List<OsobaSkl> osobaSklList) {
        this.osobaSklList = osobaSklList;
    }

    @XmlTransient
    public List<TytulWpo> getTytulWpoList() {
        return tytulWpoList;
    }

    public void setTytulWpoList(List<TytulWpo> tytulWpoList) {
        this.tytulWpoList = tytulWpoList;
    }

    @XmlTransient
    public List<OsobaPrz> getOsobaPrzList() {
        return osobaPrzList;
    }

    public void setOsobaPrzList(List<OsobaPrz> osobaPrzList) {
        this.osobaPrzList = osobaPrzList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tytSerial != null ? tytSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tytul)) {
            return false;
        }
        Tytul other = (Tytul) object;
        if ((this.tytSerial == null && other.tytSerial != null) || (this.tytSerial != null && !this.tytSerial.equals(other.tytSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Tytul[ tytSerial=" + tytSerial + " ]";
    }
    
}
