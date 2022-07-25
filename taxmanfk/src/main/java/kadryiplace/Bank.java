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
@Table(name = "bank", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bank.findAll", query = "SELECT b FROM Bank b"),
    @NamedQuery(name = "Bank.findByBanSerial", query = "SELECT b FROM Bank b WHERE b.banSerial = :banSerial"),
    @NamedQuery(name = "Bank.findByBanNazwa", query = "SELECT b FROM Bank b WHERE b.banNazwa = :banNazwa"),
    @NamedQuery(name = "Bank.findByBanMiaSerial", query = "SELECT b FROM Bank b WHERE b.banMiaSerial = :banMiaSerial"),
    @NamedQuery(name = "Bank.findByBanPanSerial", query = "SELECT b FROM Bank b WHERE b.banPanSerial = :banPanSerial"),
    @NamedQuery(name = "Bank.findByBanKod", query = "SELECT b FROM Bank b WHERE b.banKod = :banKod"),
    @NamedQuery(name = "Bank.findByBanUlica", query = "SELECT b FROM Bank b WHERE b.banUlica = :banUlica"),
    @NamedQuery(name = "Bank.findByBanDom", query = "SELECT b FROM Bank b WHERE b.banDom = :banDom"),
    @NamedQuery(name = "Bank.findByBanMieszkanie", query = "SELECT b FROM Bank b WHERE b.banMieszkanie = :banMieszkanie"),
    @NamedQuery(name = "Bank.findByBanVchar1", query = "SELECT b FROM Bank b WHERE b.banVchar1 = :banVchar1"),
    @NamedQuery(name = "Bank.findByBanVchar2", query = "SELECT b FROM Bank b WHERE b.banVchar2 = :banVchar2"),
    @NamedQuery(name = "Bank.findByBanInt1", query = "SELECT b FROM Bank b WHERE b.banInt1 = :banInt1"),
    @NamedQuery(name = "Bank.findByBanInt2", query = "SELECT b FROM Bank b WHERE b.banInt2 = :banInt2"),
    @NamedQuery(name = "Bank.findByBanChar1", query = "SELECT b FROM Bank b WHERE b.banChar1 = :banChar1"),
    @NamedQuery(name = "Bank.findByBanChar2", query = "SELECT b FROM Bank b WHERE b.banChar2 = :banChar2"),
    @NamedQuery(name = "Bank.findByBanTyp", query = "SELECT b FROM Bank b WHERE b.banTyp = :banTyp")})
public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ban_serial", nullable = false)
    private Integer banSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "ban_nazwa", nullable = false, length = 128)
    private String banNazwa;
    @Column(name = "ban_mia_serial")
    private Integer banMiaSerial;
    @Column(name = "ban_pan_serial")
    private Integer banPanSerial;
    @Size(max = 5)
    @Column(name = "ban_kod", length = 5)
    private String banKod;
    @Size(max = 64)
    @Column(name = "ban_ulica", length = 64)
    private String banUlica;
    @Size(max = 10)
    @Column(name = "ban_dom", length = 10)
    private String banDom;
    @Size(max = 10)
    @Column(name = "ban_mieszkanie", length = 10)
    private String banMieszkanie;
    @Size(max = 64)
    @Column(name = "ban_vchar_1", length = 64)
    private String banVchar1;
    @Size(max = 64)
    @Column(name = "ban_vchar_2", length = 64)
    private String banVchar2;
    @Column(name = "ban_int_1")
    private Integer banInt1;
    @Column(name = "ban_int_2")
    private Integer banInt2;
    @Column(name = "ban_char_1")
    private Character banChar1;
    @Column(name = "ban_char_2")
    private Character banChar2;
    @Column(name = "ban_typ")
    private Character banTyp;
    @OneToMany(mappedBy = "kchBanSerial")
    private List<KasaCh> kasaChList;
    @OneToMany(mappedBy = "firFakBanSerial")
    private List<Firma> firmaList;
    @OneToMany(mappedBy = "firZapBanSerial")
    private List<Firma> firmaList1;
    @OneToMany(mappedBy = "firBanSerial3")
    private List<Firma> firmaList2;
    @OneToMany(mappedBy = "firBanSerial4")
    private List<Firma> firmaList3;
    @OneToMany(mappedBy = "lplBanSerial")
    private List<Place> placeList;
    @OneToMany(mappedBy = "konBanSerial")
    private List<Kontrahent> kontrahentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kntBanSerial")
    private List<Konto> kontoList;
    @OneToMany(mappedBy = "urzBanSerial")
    private List<Urzad> urzadList;
    @OneToMany(mappedBy = "urzBanSerialV")
    private List<Urzad> urzadList1;
    @OneToMany(mappedBy = "osoBanSerial")
    private List<Osoba> osobaList;

    public Bank() {
    }

    public Bank(Integer banSerial) {
        this.banSerial = banSerial;
    }

    public Bank(Integer banSerial, String banNazwa) {
        this.banSerial = banSerial;
        this.banNazwa = banNazwa;
    }

    public Integer getBanSerial() {
        return banSerial;
    }

    public void setBanSerial(Integer banSerial) {
        this.banSerial = banSerial;
    }

    public String getBanNazwa() {
        return banNazwa;
    }

    public void setBanNazwa(String banNazwa) {
        this.banNazwa = banNazwa;
    }

    public Integer getBanMiaSerial() {
        return banMiaSerial;
    }

    public void setBanMiaSerial(Integer banMiaSerial) {
        this.banMiaSerial = banMiaSerial;
    }

    public Integer getBanPanSerial() {
        return banPanSerial;
    }

    public void setBanPanSerial(Integer banPanSerial) {
        this.banPanSerial = banPanSerial;
    }

    public String getBanKod() {
        return banKod;
    }

    public void setBanKod(String banKod) {
        this.banKod = banKod;
    }

    public String getBanUlica() {
        return banUlica;
    }

    public void setBanUlica(String banUlica) {
        this.banUlica = banUlica;
    }

    public String getBanDom() {
        return banDom;
    }

    public void setBanDom(String banDom) {
        this.banDom = banDom;
    }

    public String getBanMieszkanie() {
        return banMieszkanie;
    }

    public void setBanMieszkanie(String banMieszkanie) {
        this.banMieszkanie = banMieszkanie;
    }

    public String getBanVchar1() {
        return banVchar1;
    }

    public void setBanVchar1(String banVchar1) {
        this.banVchar1 = banVchar1;
    }

    public String getBanVchar2() {
        return banVchar2;
    }

    public void setBanVchar2(String banVchar2) {
        this.banVchar2 = banVchar2;
    }

    public Integer getBanInt1() {
        return banInt1;
    }

    public void setBanInt1(Integer banInt1) {
        this.banInt1 = banInt1;
    }

    public Integer getBanInt2() {
        return banInt2;
    }

    public void setBanInt2(Integer banInt2) {
        this.banInt2 = banInt2;
    }

    public Character getBanChar1() {
        return banChar1;
    }

    public void setBanChar1(Character banChar1) {
        this.banChar1 = banChar1;
    }

    public Character getBanChar2() {
        return banChar2;
    }

    public void setBanChar2(Character banChar2) {
        this.banChar2 = banChar2;
    }

    public Character getBanTyp() {
        return banTyp;
    }

    public void setBanTyp(Character banTyp) {
        this.banTyp = banTyp;
    }

    @XmlTransient
    public List<KasaCh> getKasaChList() {
        return kasaChList;
    }

    public void setKasaChList(List<KasaCh> kasaChList) {
        this.kasaChList = kasaChList;
    }

    @XmlTransient
    public List<Firma> getFirmaList() {
        return firmaList;
    }

    public void setFirmaList(List<Firma> firmaList) {
        this.firmaList = firmaList;
    }

    @XmlTransient
    public List<Firma> getFirmaList1() {
        return firmaList1;
    }

    public void setFirmaList1(List<Firma> firmaList1) {
        this.firmaList1 = firmaList1;
    }

    @XmlTransient
    public List<Firma> getFirmaList2() {
        return firmaList2;
    }

    public void setFirmaList2(List<Firma> firmaList2) {
        this.firmaList2 = firmaList2;
    }

    @XmlTransient
    public List<Firma> getFirmaList3() {
        return firmaList3;
    }

    public void setFirmaList3(List<Firma> firmaList3) {
        this.firmaList3 = firmaList3;
    }

    @XmlTransient
    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    @XmlTransient
    public List<Kontrahent> getKontrahentList() {
        return kontrahentList;
    }

    public void setKontrahentList(List<Kontrahent> kontrahentList) {
        this.kontrahentList = kontrahentList;
    }

    @XmlTransient
    public List<Konto> getKontoList() {
        return kontoList;
    }

    public void setKontoList(List<Konto> kontoList) {
        this.kontoList = kontoList;
    }

    @XmlTransient
    public List<Urzad> getUrzadList() {
        return urzadList;
    }

    public void setUrzadList(List<Urzad> urzadList) {
        this.urzadList = urzadList;
    }

    @XmlTransient
    public List<Urzad> getUrzadList1() {
        return urzadList1;
    }

    public void setUrzadList1(List<Urzad> urzadList1) {
        this.urzadList1 = urzadList1;
    }

    @XmlTransient
    public List<Osoba> getOsobaList() {
        return osobaList;
    }

    public void setOsobaList(List<Osoba> osobaList) {
        this.osobaList = osobaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (banSerial != null ? banSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bank)) {
            return false;
        }
        Bank other = (Bank) object;
        if ((this.banSerial == null && other.banSerial != null) || (this.banSerial != null && !this.banSerial.equals(other.banSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Bank[ banSerial=" + banSerial + " ]";
    }
    
}
