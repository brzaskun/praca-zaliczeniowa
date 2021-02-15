/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "st_syst_opis", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StSystOpis.findAll", query = "SELECT s FROM StSystOpis s"),
    @NamedQuery(name = "StSystOpis.findBySsdSerial", query = "SELECT s FROM StSystOpis s WHERE s.ssdSerial = :ssdSerial"),
    @NamedQuery(name = "StSystOpis.findBySsdNazwa", query = "SELECT s FROM StSystOpis s WHERE s.ssdNazwa = :ssdNazwa"),
    @NamedQuery(name = "StSystOpis.findBySsdTyp", query = "SELECT s FROM StSystOpis s WHERE s.ssdTyp = :ssdTyp"),
    @NamedQuery(name = "StSystOpis.findBySsdDod1", query = "SELECT s FROM StSystOpis s WHERE s.ssdDod1 = :ssdDod1"),
    @NamedQuery(name = "StSystOpis.findBySsdDod2", query = "SELECT s FROM StSystOpis s WHERE s.ssdDod2 = :ssdDod2"),
    @NamedQuery(name = "StSystOpis.findBySsdDod3", query = "SELECT s FROM StSystOpis s WHERE s.ssdDod3 = :ssdDod3"),
    @NamedQuery(name = "StSystOpis.findBySsdDod4", query = "SELECT s FROM StSystOpis s WHERE s.ssdDod4 = :ssdDod4"),
    @NamedQuery(name = "StSystOpis.findBySsdDod5", query = "SELECT s FROM StSystOpis s WHERE s.ssdDod5 = :ssdDod5"),
    @NamedQuery(name = "StSystOpis.findBySsdDod6", query = "SELECT s FROM StSystOpis s WHERE s.ssdDod6 = :ssdDod6"),
    @NamedQuery(name = "StSystOpis.findBySsdDod7", query = "SELECT s FROM StSystOpis s WHERE s.ssdDod7 = :ssdDod7"),
    @NamedQuery(name = "StSystOpis.findBySsdDod8", query = "SELECT s FROM StSystOpis s WHERE s.ssdDod8 = :ssdDod8"),
    @NamedQuery(name = "StSystOpis.findBySsdDod9", query = "SELECT s FROM StSystOpis s WHERE s.ssdDod9 = :ssdDod9"),
    @NamedQuery(name = "StSystOpis.findBySsdDod10", query = "SELECT s FROM StSystOpis s WHERE s.ssdDod10 = :ssdDod10"),
    @NamedQuery(name = "StSystOpis.findBySsdDotyczy", query = "SELECT s FROM StSystOpis s WHERE s.ssdDotyczy = :ssdDotyczy"),
    @NamedQuery(name = "StSystOpis.findBySsdSposob", query = "SELECT s FROM StSystOpis s WHERE s.ssdSposob = :ssdSposob"),
    @NamedQuery(name = "StSystOpis.findBySsdTypSyst", query = "SELECT s FROM StSystOpis s WHERE s.ssdTypSyst = :ssdTypSyst"),
    @NamedQuery(name = "StSystOpis.findBySsdSystem", query = "SELECT s FROM StSystOpis s WHERE s.ssdSystem = :ssdSystem")})
public class StSystOpis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ssd_serial", nullable = false)
    private Integer ssdSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ssd_nazwa", nullable = false, length = 64)
    private String ssdNazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ssd_typ", nullable = false)
    private Character ssdTyp;
    @Column(name = "ssd_dod_1")
    private Character ssdDod1;
    @Column(name = "ssd_dod_2")
    private Character ssdDod2;
    @Column(name = "ssd_dod_3")
    private Character ssdDod3;
    @Column(name = "ssd_dod_4")
    private Character ssdDod4;
    @Column(name = "ssd_dod_5")
    private Character ssdDod5;
    @Column(name = "ssd_dod_6")
    private Character ssdDod6;
    @Column(name = "ssd_dod_7")
    private Character ssdDod7;
    @Column(name = "ssd_dod_8")
    private Character ssdDod8;
    @Column(name = "ssd_dod_9")
    private Character ssdDod9;
    @Column(name = "ssd_dod_10")
    private Character ssdDod10;
    @Column(name = "ssd_dotyczy")
    private Character ssdDotyczy;
    @Column(name = "ssd_sposob")
    private Character ssdSposob;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ssd_typ_syst", nullable = false)
    private Character ssdTypSyst;
    @Column(name = "ssd_system")
    private Character ssdSystem;
    @OneToMany(mappedBy = "wkzSsdSerial")
    private List<WynKodPrzZus> wynKodPrzZusList;
    @OneToMany(mappedBy = "opoSsdSerial1")
    private List<OsobaPot> osobaPotList;
    @OneToMany(mappedBy = "opoSsdSerialMin3")
    private List<OsobaPot> osobaPotList1;
    @OneToMany(mappedBy = "opoSsdSerialMax3")
    private List<OsobaPot> osobaPotList2;
    @OneToMany(mappedBy = "opoSsdSerial3")
    private List<OsobaPot> osobaPotList3;
    @OneToMany(mappedBy = "opoSsdSerial2")
    private List<OsobaPot> osobaPotList4;
    @OneToMany(mappedBy = "opoSsdSerialMin")
    private List<OsobaPot> osobaPotList5;
    @OneToMany(mappedBy = "opoSsdSerialMax")
    private List<OsobaPot> osobaPotList6;
    @OneToMany(mappedBy = "opoSsdSerialMin2")
    private List<OsobaPot> osobaPotList7;
    @OneToMany(mappedBy = "opoSsdSerialMax2")
    private List<OsobaPot> osobaPotList8;
    @OneToMany(mappedBy = "wkpSsdSerial")
    private List<WynKodPrz> wynKodPrzList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ssoSsdSerial")
    private List<StSystWart> stSystWartList;
    @OneToMany(mappedBy = "ozlSsdSerial1")
    private List<OsobaZlec> osobaZlecList;
    @OneToMany(mappedBy = "ozlSsdSerialK")
    private List<OsobaZlec> osobaZlecList1;
    @OneToMany(mappedBy = "ozlSsdSerial2")
    private List<OsobaZlec> osobaZlecList2;
    @OneToMany(mappedBy = "ozlSsdSerialK2")
    private List<OsobaZlec> osobaZlecList3;
    @OneToMany(mappedBy = "ozlSsdSerialMin")
    private List<OsobaZlec> osobaZlecList4;
    @OneToMany(mappedBy = "ossSsdSerialMax3")
    private List<OsobaSkl> osobaSklList;
    @OneToMany(mappedBy = "ossSsdSerialMin3")
    private List<OsobaSkl> osobaSklList1;
    @OneToMany(mappedBy = "ossSsdSerial1")
    private List<OsobaSkl> osobaSklList2;
    @OneToMany(mappedBy = "ossSsdSerial2")
    private List<OsobaSkl> osobaSklList3;
    @OneToMany(mappedBy = "ossSsdSerialMin")
    private List<OsobaSkl> osobaSklList4;
    @OneToMany(mappedBy = "ossSsdSerialMax")
    private List<OsobaSkl> osobaSklList5;
    @OneToMany(mappedBy = "ossSsdSerialMin2")
    private List<OsobaSkl> osobaSklList6;
    @OneToMany(mappedBy = "ossSsdSerialMax2")
    private List<OsobaSkl> osobaSklList7;
    @OneToMany(mappedBy = "ossSsdSerial3")
    private List<OsobaSkl> osobaSklList8;
    @JoinColumn(name = "ssd_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma ssdFirSerial;
    @JoinColumn(name = "ssd_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba ssdOsoSerial;

    public StSystOpis() {
    }

    public StSystOpis(Integer ssdSerial) {
        this.ssdSerial = ssdSerial;
    }

    public StSystOpis(Integer ssdSerial, String ssdNazwa, Character ssdTyp, Character ssdTypSyst) {
        this.ssdSerial = ssdSerial;
        this.ssdNazwa = ssdNazwa;
        this.ssdTyp = ssdTyp;
        this.ssdTypSyst = ssdTypSyst;
    }

    public Integer getSsdSerial() {
        return ssdSerial;
    }

    public void setSsdSerial(Integer ssdSerial) {
        this.ssdSerial = ssdSerial;
    }

    public String getSsdNazwa() {
        return ssdNazwa;
    }

    public void setSsdNazwa(String ssdNazwa) {
        this.ssdNazwa = ssdNazwa;
    }

    public Character getSsdTyp() {
        return ssdTyp;
    }

    public void setSsdTyp(Character ssdTyp) {
        this.ssdTyp = ssdTyp;
    }

    public Character getSsdDod1() {
        return ssdDod1;
    }

    public void setSsdDod1(Character ssdDod1) {
        this.ssdDod1 = ssdDod1;
    }

    public Character getSsdDod2() {
        return ssdDod2;
    }

    public void setSsdDod2(Character ssdDod2) {
        this.ssdDod2 = ssdDod2;
    }

    public Character getSsdDod3() {
        return ssdDod3;
    }

    public void setSsdDod3(Character ssdDod3) {
        this.ssdDod3 = ssdDod3;
    }

    public Character getSsdDod4() {
        return ssdDod4;
    }

    public void setSsdDod4(Character ssdDod4) {
        this.ssdDod4 = ssdDod4;
    }

    public Character getSsdDod5() {
        return ssdDod5;
    }

    public void setSsdDod5(Character ssdDod5) {
        this.ssdDod5 = ssdDod5;
    }

    public Character getSsdDod6() {
        return ssdDod6;
    }

    public void setSsdDod6(Character ssdDod6) {
        this.ssdDod6 = ssdDod6;
    }

    public Character getSsdDod7() {
        return ssdDod7;
    }

    public void setSsdDod7(Character ssdDod7) {
        this.ssdDod7 = ssdDod7;
    }

    public Character getSsdDod8() {
        return ssdDod8;
    }

    public void setSsdDod8(Character ssdDod8) {
        this.ssdDod8 = ssdDod8;
    }

    public Character getSsdDod9() {
        return ssdDod9;
    }

    public void setSsdDod9(Character ssdDod9) {
        this.ssdDod9 = ssdDod9;
    }

    public Character getSsdDod10() {
        return ssdDod10;
    }

    public void setSsdDod10(Character ssdDod10) {
        this.ssdDod10 = ssdDod10;
    }

    public Character getSsdDotyczy() {
        return ssdDotyczy;
    }

    public void setSsdDotyczy(Character ssdDotyczy) {
        this.ssdDotyczy = ssdDotyczy;
    }

    public Character getSsdSposob() {
        return ssdSposob;
    }

    public void setSsdSposob(Character ssdSposob) {
        this.ssdSposob = ssdSposob;
    }

    public Character getSsdTypSyst() {
        return ssdTypSyst;
    }

    public void setSsdTypSyst(Character ssdTypSyst) {
        this.ssdTypSyst = ssdTypSyst;
    }

    public Character getSsdSystem() {
        return ssdSystem;
    }

    public void setSsdSystem(Character ssdSystem) {
        this.ssdSystem = ssdSystem;
    }

    @XmlTransient
    public List<WynKodPrzZus> getWynKodPrzZusList() {
        return wynKodPrzZusList;
    }

    public void setWynKodPrzZusList(List<WynKodPrzZus> wynKodPrzZusList) {
        this.wynKodPrzZusList = wynKodPrzZusList;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList() {
        return osobaPotList;
    }

    public void setOsobaPotList(List<OsobaPot> osobaPotList) {
        this.osobaPotList = osobaPotList;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList1() {
        return osobaPotList1;
    }

    public void setOsobaPotList1(List<OsobaPot> osobaPotList1) {
        this.osobaPotList1 = osobaPotList1;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList2() {
        return osobaPotList2;
    }

    public void setOsobaPotList2(List<OsobaPot> osobaPotList2) {
        this.osobaPotList2 = osobaPotList2;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList3() {
        return osobaPotList3;
    }

    public void setOsobaPotList3(List<OsobaPot> osobaPotList3) {
        this.osobaPotList3 = osobaPotList3;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList4() {
        return osobaPotList4;
    }

    public void setOsobaPotList4(List<OsobaPot> osobaPotList4) {
        this.osobaPotList4 = osobaPotList4;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList5() {
        return osobaPotList5;
    }

    public void setOsobaPotList5(List<OsobaPot> osobaPotList5) {
        this.osobaPotList5 = osobaPotList5;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList6() {
        return osobaPotList6;
    }

    public void setOsobaPotList6(List<OsobaPot> osobaPotList6) {
        this.osobaPotList6 = osobaPotList6;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList7() {
        return osobaPotList7;
    }

    public void setOsobaPotList7(List<OsobaPot> osobaPotList7) {
        this.osobaPotList7 = osobaPotList7;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList8() {
        return osobaPotList8;
    }

    public void setOsobaPotList8(List<OsobaPot> osobaPotList8) {
        this.osobaPotList8 = osobaPotList8;
    }

    @XmlTransient
    public List<WynKodPrz> getWynKodPrzList() {
        return wynKodPrzList;
    }

    public void setWynKodPrzList(List<WynKodPrz> wynKodPrzList) {
        this.wynKodPrzList = wynKodPrzList;
    }

    @XmlTransient
    public List<StSystWart> getStSystWartList() {
        return stSystWartList;
    }

    public void setStSystWartList(List<StSystWart> stSystWartList) {
        this.stSystWartList = stSystWartList;
    }

    @XmlTransient
    public List<OsobaZlec> getOsobaZlecList() {
        return osobaZlecList;
    }

    public void setOsobaZlecList(List<OsobaZlec> osobaZlecList) {
        this.osobaZlecList = osobaZlecList;
    }

    @XmlTransient
    public List<OsobaZlec> getOsobaZlecList1() {
        return osobaZlecList1;
    }

    public void setOsobaZlecList1(List<OsobaZlec> osobaZlecList1) {
        this.osobaZlecList1 = osobaZlecList1;
    }

    @XmlTransient
    public List<OsobaZlec> getOsobaZlecList2() {
        return osobaZlecList2;
    }

    public void setOsobaZlecList2(List<OsobaZlec> osobaZlecList2) {
        this.osobaZlecList2 = osobaZlecList2;
    }

    @XmlTransient
    public List<OsobaZlec> getOsobaZlecList3() {
        return osobaZlecList3;
    }

    public void setOsobaZlecList3(List<OsobaZlec> osobaZlecList3) {
        this.osobaZlecList3 = osobaZlecList3;
    }

    @XmlTransient
    public List<OsobaZlec> getOsobaZlecList4() {
        return osobaZlecList4;
    }

    public void setOsobaZlecList4(List<OsobaZlec> osobaZlecList4) {
        this.osobaZlecList4 = osobaZlecList4;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList() {
        return osobaSklList;
    }

    public void setOsobaSklList(List<OsobaSkl> osobaSklList) {
        this.osobaSklList = osobaSklList;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList1() {
        return osobaSklList1;
    }

    public void setOsobaSklList1(List<OsobaSkl> osobaSklList1) {
        this.osobaSklList1 = osobaSklList1;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList2() {
        return osobaSklList2;
    }

    public void setOsobaSklList2(List<OsobaSkl> osobaSklList2) {
        this.osobaSklList2 = osobaSklList2;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList3() {
        return osobaSklList3;
    }

    public void setOsobaSklList3(List<OsobaSkl> osobaSklList3) {
        this.osobaSklList3 = osobaSklList3;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList4() {
        return osobaSklList4;
    }

    public void setOsobaSklList4(List<OsobaSkl> osobaSklList4) {
        this.osobaSklList4 = osobaSklList4;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList5() {
        return osobaSklList5;
    }

    public void setOsobaSklList5(List<OsobaSkl> osobaSklList5) {
        this.osobaSklList5 = osobaSklList5;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList6() {
        return osobaSklList6;
    }

    public void setOsobaSklList6(List<OsobaSkl> osobaSklList6) {
        this.osobaSklList6 = osobaSklList6;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList7() {
        return osobaSklList7;
    }

    public void setOsobaSklList7(List<OsobaSkl> osobaSklList7) {
        this.osobaSklList7 = osobaSklList7;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList8() {
        return osobaSklList8;
    }

    public void setOsobaSklList8(List<OsobaSkl> osobaSklList8) {
        this.osobaSklList8 = osobaSklList8;
    }

    public Firma getSsdFirSerial() {
        return ssdFirSerial;
    }

    public void setSsdFirSerial(Firma ssdFirSerial) {
        this.ssdFirSerial = ssdFirSerial;
    }

    public Osoba getSsdOsoSerial() {
        return ssdOsoSerial;
    }

    public void setSsdOsoSerial(Osoba ssdOsoSerial) {
        this.ssdOsoSerial = ssdOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ssdSerial != null ? ssdSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StSystOpis)) {
            return false;
        }
        StSystOpis other = (StSystOpis) object;
        if ((this.ssdSerial == null && other.ssdSerial != null) || (this.ssdSerial != null && !this.ssdSerial.equals(other.ssdSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.StSystOpis[ ssdSerial=" + ssdSerial + " ]";
    }
    
}
