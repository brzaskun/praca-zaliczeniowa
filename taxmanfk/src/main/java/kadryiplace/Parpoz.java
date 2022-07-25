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
@Table(name = "parpoz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parpoz.findAll", query = "SELECT p FROM Parpoz p"),
    @NamedQuery(name = "Parpoz.findByPazSerial", query = "SELECT p FROM Parpoz p WHERE p.pazSerial = :pazSerial"),
    @NamedQuery(name = "Parpoz.findByPazIlosc", query = "SELECT p FROM Parpoz p WHERE p.pazIlosc = :pazIlosc"),
    @NamedQuery(name = "Parpoz.findByPazMagSymbol", query = "SELECT p FROM Parpoz p WHERE p.pazMagSymbol = :pazMagSymbol"),
    @NamedQuery(name = "Parpoz.findByPazMagNazwa", query = "SELECT p FROM Parpoz p WHERE p.pazMagNazwa = :pazMagNazwa"),
    @NamedQuery(name = "Parpoz.findByPazMagSwwKu", query = "SELECT p FROM Parpoz p WHERE p.pazMagSwwKu = :pazMagSwwKu"),
    @NamedQuery(name = "Parpoz.findByPazMagStVat", query = "SELECT p FROM Parpoz p WHERE p.pazMagStVat = :pazMagStVat"),
    @NamedQuery(name = "Parpoz.findByPazMagCenaSpr", query = "SELECT p FROM Parpoz p WHERE p.pazMagCenaSpr = :pazMagCenaSpr"),
    @NamedQuery(name = "Parpoz.findByPazMagVatSprzedaz", query = "SELECT p FROM Parpoz p WHERE p.pazMagVatSprzedaz = :pazMagVatSprzedaz"),
    @NamedQuery(name = "Parpoz.findByPazWalSkrot", query = "SELECT p FROM Parpoz p WHERE p.pazWalSkrot = :pazWalSkrot"),
    @NamedQuery(name = "Parpoz.findByPazWalIlosc", query = "SELECT p FROM Parpoz p WHERE p.pazWalIlosc = :pazWalIlosc"),
    @NamedQuery(name = "Parpoz.findByPazWalKurs", query = "SELECT p FROM Parpoz p WHERE p.pazWalKurs = :pazWalKurs"),
    @NamedQuery(name = "Parpoz.findByPazWalDzien", query = "SELECT p FROM Parpoz p WHERE p.pazWalDzien = :pazWalDzien"),
    @NamedQuery(name = "Parpoz.findByPazMagCenaSprO", query = "SELECT p FROM Parpoz p WHERE p.pazMagCenaSprO = :pazMagCenaSprO"),
    @NamedQuery(name = "Parpoz.findByPazMagTyp", query = "SELECT p FROM Parpoz p WHERE p.pazMagTyp = :pazMagTyp"),
    @NamedQuery(name = "Parpoz.findByPazRabat", query = "SELECT p FROM Parpoz p WHERE p.pazRabat = :pazRabat"),
    @NamedQuery(name = "Parpoz.findByPazKodKres", query = "SELECT p FROM Parpoz p WHERE p.pazKodKres = :pazKodKres"),
    @NamedQuery(name = "Parpoz.findByPazVchar1", query = "SELECT p FROM Parpoz p WHERE p.pazVchar1 = :pazVchar1"),
    @NamedQuery(name = "Parpoz.findByPazNum1", query = "SELECT p FROM Parpoz p WHERE p.pazNum1 = :pazNum1"),
    @NamedQuery(name = "Parpoz.findByPazNum2", query = "SELECT p FROM Parpoz p WHERE p.pazNum2 = :pazNum2"),
    @NamedQuery(name = "Parpoz.findByPazNum3", query = "SELECT p FROM Parpoz p WHERE p.pazNum3 = :pazNum3"),
    @NamedQuery(name = "Parpoz.findByPazNum4", query = "SELECT p FROM Parpoz p WHERE p.pazNum4 = :pazNum4"),
    @NamedQuery(name = "Parpoz.findByPazChar1", query = "SELECT p FROM Parpoz p WHERE p.pazChar1 = :pazChar1"),
    @NamedQuery(name = "Parpoz.findByPazChar2", query = "SELECT p FROM Parpoz p WHERE p.pazChar2 = :pazChar2"),
    @NamedQuery(name = "Parpoz.findByPazTyp", query = "SELECT p FROM Parpoz p WHERE p.pazTyp = :pazTyp")})
public class Parpoz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "paz_serial", nullable = false)
    private Integer pazSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "paz_ilosc", nullable = false, precision = 13, scale = 6)
    private BigDecimal pazIlosc;
    @Size(max = 16)
    @Column(name = "paz_mag_symbol", length = 16)
    private String pazMagSymbol;
    @Size(max = 64)
    @Column(name = "paz_mag_nazwa", length = 64)
    private String pazMagNazwa;
    @Size(max = 16)
    @Column(name = "paz_mag_sww_ku", length = 16)
    private String pazMagSwwKu;
    @Column(name = "paz_mag_st_vat", precision = 5, scale = 2)
    private BigDecimal pazMagStVat;
    @Column(name = "paz_mag_cena_spr", precision = 13, scale = 4)
    private BigDecimal pazMagCenaSpr;
    @Column(name = "paz_mag_vat_sprzedaz")
    private Character pazMagVatSprzedaz;
    @Size(max = 5)
    @Column(name = "paz_wal_skrot", length = 5)
    private String pazWalSkrot;
    @Column(name = "paz_wal_ilosc")
    private Short pazWalIlosc;
    @Column(name = "paz_wal_kurs", precision = 8, scale = 4)
    private BigDecimal pazWalKurs;
    @Column(name = "paz_wal_dzien")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pazWalDzien;
    @Column(name = "paz_mag_cena_spr_o", precision = 13, scale = 4)
    private BigDecimal pazMagCenaSprO;
    @Column(name = "paz_mag_typ")
    private Character pazMagTyp;
    @Column(name = "paz_rabat", precision = 5, scale = 2)
    private BigDecimal pazRabat;
    @Size(max = 32)
    @Column(name = "paz_kod_kres", length = 32)
    private String pazKodKres;
    @Size(max = 64)
    @Column(name = "paz_vchar_1", length = 64)
    private String pazVchar1;
    @Column(name = "paz_num_1", precision = 17, scale = 6)
    private BigDecimal pazNum1;
    @Column(name = "paz_num_2", precision = 17, scale = 6)
    private BigDecimal pazNum2;
    @Column(name = "paz_num_3", precision = 17, scale = 6)
    private BigDecimal pazNum3;
    @Column(name = "paz_num_4", precision = 17, scale = 6)
    private BigDecimal pazNum4;
    @Column(name = "paz_char_1")
    private Character pazChar1;
    @Column(name = "paz_char_2")
    private Character pazChar2;
    @Column(name = "paz_typ")
    private Character pazTyp;
    @OneToMany(mappedBy = "dspPazSerial")
    private List<DaneStatP> daneStatPList;
    @JoinColumn(name = "paz_mag_jed_serial", referencedColumnName = "jed_serial")
    @ManyToOne
    private Jednostka pazMagJedSerial;
    @JoinColumn(name = "paz_mag_serial", referencedColumnName = "mag_serial")
    @ManyToOne
    private Magazyn pazMagSerial;
    @JoinColumn(name = "paz_par_serial", referencedColumnName = "par_serial", nullable = false)
    @ManyToOne(optional = false)
    private Paragon pazParSerial;

    public Parpoz() {
    }

    public Parpoz(Integer pazSerial) {
        this.pazSerial = pazSerial;
    }

    public Parpoz(Integer pazSerial, BigDecimal pazIlosc) {
        this.pazSerial = pazSerial;
        this.pazIlosc = pazIlosc;
    }

    public Integer getPazSerial() {
        return pazSerial;
    }

    public void setPazSerial(Integer pazSerial) {
        this.pazSerial = pazSerial;
    }

    public BigDecimal getPazIlosc() {
        return pazIlosc;
    }

    public void setPazIlosc(BigDecimal pazIlosc) {
        this.pazIlosc = pazIlosc;
    }

    public String getPazMagSymbol() {
        return pazMagSymbol;
    }

    public void setPazMagSymbol(String pazMagSymbol) {
        this.pazMagSymbol = pazMagSymbol;
    }

    public String getPazMagNazwa() {
        return pazMagNazwa;
    }

    public void setPazMagNazwa(String pazMagNazwa) {
        this.pazMagNazwa = pazMagNazwa;
    }

    public String getPazMagSwwKu() {
        return pazMagSwwKu;
    }

    public void setPazMagSwwKu(String pazMagSwwKu) {
        this.pazMagSwwKu = pazMagSwwKu;
    }

    public BigDecimal getPazMagStVat() {
        return pazMagStVat;
    }

    public void setPazMagStVat(BigDecimal pazMagStVat) {
        this.pazMagStVat = pazMagStVat;
    }

    public BigDecimal getPazMagCenaSpr() {
        return pazMagCenaSpr;
    }

    public void setPazMagCenaSpr(BigDecimal pazMagCenaSpr) {
        this.pazMagCenaSpr = pazMagCenaSpr;
    }

    public Character getPazMagVatSprzedaz() {
        return pazMagVatSprzedaz;
    }

    public void setPazMagVatSprzedaz(Character pazMagVatSprzedaz) {
        this.pazMagVatSprzedaz = pazMagVatSprzedaz;
    }

    public String getPazWalSkrot() {
        return pazWalSkrot;
    }

    public void setPazWalSkrot(String pazWalSkrot) {
        this.pazWalSkrot = pazWalSkrot;
    }

    public Short getPazWalIlosc() {
        return pazWalIlosc;
    }

    public void setPazWalIlosc(Short pazWalIlosc) {
        this.pazWalIlosc = pazWalIlosc;
    }

    public BigDecimal getPazWalKurs() {
        return pazWalKurs;
    }

    public void setPazWalKurs(BigDecimal pazWalKurs) {
        this.pazWalKurs = pazWalKurs;
    }

    public Date getPazWalDzien() {
        return pazWalDzien;
    }

    public void setPazWalDzien(Date pazWalDzien) {
        this.pazWalDzien = pazWalDzien;
    }

    public BigDecimal getPazMagCenaSprO() {
        return pazMagCenaSprO;
    }

    public void setPazMagCenaSprO(BigDecimal pazMagCenaSprO) {
        this.pazMagCenaSprO = pazMagCenaSprO;
    }

    public Character getPazMagTyp() {
        return pazMagTyp;
    }

    public void setPazMagTyp(Character pazMagTyp) {
        this.pazMagTyp = pazMagTyp;
    }

    public BigDecimal getPazRabat() {
        return pazRabat;
    }

    public void setPazRabat(BigDecimal pazRabat) {
        this.pazRabat = pazRabat;
    }

    public String getPazKodKres() {
        return pazKodKres;
    }

    public void setPazKodKres(String pazKodKres) {
        this.pazKodKres = pazKodKres;
    }

    public String getPazVchar1() {
        return pazVchar1;
    }

    public void setPazVchar1(String pazVchar1) {
        this.pazVchar1 = pazVchar1;
    }

    public BigDecimal getPazNum1() {
        return pazNum1;
    }

    public void setPazNum1(BigDecimal pazNum1) {
        this.pazNum1 = pazNum1;
    }

    public BigDecimal getPazNum2() {
        return pazNum2;
    }

    public void setPazNum2(BigDecimal pazNum2) {
        this.pazNum2 = pazNum2;
    }

    public BigDecimal getPazNum3() {
        return pazNum3;
    }

    public void setPazNum3(BigDecimal pazNum3) {
        this.pazNum3 = pazNum3;
    }

    public BigDecimal getPazNum4() {
        return pazNum4;
    }

    public void setPazNum4(BigDecimal pazNum4) {
        this.pazNum4 = pazNum4;
    }

    public Character getPazChar1() {
        return pazChar1;
    }

    public void setPazChar1(Character pazChar1) {
        this.pazChar1 = pazChar1;
    }

    public Character getPazChar2() {
        return pazChar2;
    }

    public void setPazChar2(Character pazChar2) {
        this.pazChar2 = pazChar2;
    }

    public Character getPazTyp() {
        return pazTyp;
    }

    public void setPazTyp(Character pazTyp) {
        this.pazTyp = pazTyp;
    }

    @XmlTransient
    public List<DaneStatP> getDaneStatPList() {
        return daneStatPList;
    }

    public void setDaneStatPList(List<DaneStatP> daneStatPList) {
        this.daneStatPList = daneStatPList;
    }

    public Jednostka getPazMagJedSerial() {
        return pazMagJedSerial;
    }

    public void setPazMagJedSerial(Jednostka pazMagJedSerial) {
        this.pazMagJedSerial = pazMagJedSerial;
    }

    public Magazyn getPazMagSerial() {
        return pazMagSerial;
    }

    public void setPazMagSerial(Magazyn pazMagSerial) {
        this.pazMagSerial = pazMagSerial;
    }

    public Paragon getPazParSerial() {
        return pazParSerial;
    }

    public void setPazParSerial(Paragon pazParSerial) {
        this.pazParSerial = pazParSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pazSerial != null ? pazSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parpoz)) {
            return false;
        }
        Parpoz other = (Parpoz) object;
        if ((this.pazSerial == null && other.pazSerial != null) || (this.pazSerial != null && !this.pazSerial.equals(other.pazSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Parpoz[ pazSerial=" + pazSerial + " ]";
    }
    
}
