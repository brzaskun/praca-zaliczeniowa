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
@Table(name = "pozycja", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pozycja.findAll", query = "SELECT p FROM Pozycja p"),
    @NamedQuery(name = "Pozycja.findByPozSerial", query = "SELECT p FROM Pozycja p WHERE p.pozSerial = :pozSerial"),
    @NamedQuery(name = "Pozycja.findByPozIlosc", query = "SELECT p FROM Pozycja p WHERE p.pozIlosc = :pozIlosc"),
    @NamedQuery(name = "Pozycja.findByPozMagSymbol", query = "SELECT p FROM Pozycja p WHERE p.pozMagSymbol = :pozMagSymbol"),
    @NamedQuery(name = "Pozycja.findByPozMagNazwa", query = "SELECT p FROM Pozycja p WHERE p.pozMagNazwa = :pozMagNazwa"),
    @NamedQuery(name = "Pozycja.findByPozMagSwwKu", query = "SELECT p FROM Pozycja p WHERE p.pozMagSwwKu = :pozMagSwwKu"),
    @NamedQuery(name = "Pozycja.findByPozMagStVat", query = "SELECT p FROM Pozycja p WHERE p.pozMagStVat = :pozMagStVat"),
    @NamedQuery(name = "Pozycja.findByPozMagCenaSpr", query = "SELECT p FROM Pozycja p WHERE p.pozMagCenaSpr = :pozMagCenaSpr"),
    @NamedQuery(name = "Pozycja.findByPozMagVatSprzedaz", query = "SELECT p FROM Pozycja p WHERE p.pozMagVatSprzedaz = :pozMagVatSprzedaz"),
    @NamedQuery(name = "Pozycja.findByPozWalSkrot", query = "SELECT p FROM Pozycja p WHERE p.pozWalSkrot = :pozWalSkrot"),
    @NamedQuery(name = "Pozycja.findByPozWalIlosc", query = "SELECT p FROM Pozycja p WHERE p.pozWalIlosc = :pozWalIlosc"),
    @NamedQuery(name = "Pozycja.findByPozWalKurs", query = "SELECT p FROM Pozycja p WHERE p.pozWalKurs = :pozWalKurs"),
    @NamedQuery(name = "Pozycja.findByPozWalDzien", query = "SELECT p FROM Pozycja p WHERE p.pozWalDzien = :pozWalDzien"),
    @NamedQuery(name = "Pozycja.findByPozMagCenaSprO", query = "SELECT p FROM Pozycja p WHERE p.pozMagCenaSprO = :pozMagCenaSprO"),
    @NamedQuery(name = "Pozycja.findByPozMagTyp", query = "SELECT p FROM Pozycja p WHERE p.pozMagTyp = :pozMagTyp"),
    @NamedQuery(name = "Pozycja.findByPozRabat", query = "SELECT p FROM Pozycja p WHERE p.pozRabat = :pozRabat"),
    @NamedQuery(name = "Pozycja.findByPozKodKres", query = "SELECT p FROM Pozycja p WHERE p.pozKodKres = :pozKodKres"),
    @NamedQuery(name = "Pozycja.findByPozVchar1", query = "SELECT p FROM Pozycja p WHERE p.pozVchar1 = :pozVchar1"),
    @NamedQuery(name = "Pozycja.findByPozNum1", query = "SELECT p FROM Pozycja p WHERE p.pozNum1 = :pozNum1"),
    @NamedQuery(name = "Pozycja.findByPozNum2", query = "SELECT p FROM Pozycja p WHERE p.pozNum2 = :pozNum2"),
    @NamedQuery(name = "Pozycja.findByPozNum3", query = "SELECT p FROM Pozycja p WHERE p.pozNum3 = :pozNum3"),
    @NamedQuery(name = "Pozycja.findByPozNum4", query = "SELECT p FROM Pozycja p WHERE p.pozNum4 = :pozNum4"),
    @NamedQuery(name = "Pozycja.findByPozChar1", query = "SELECT p FROM Pozycja p WHERE p.pozChar1 = :pozChar1"),
    @NamedQuery(name = "Pozycja.findByPozChar2", query = "SELECT p FROM Pozycja p WHERE p.pozChar2 = :pozChar2")})
public class Pozycja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "poz_serial", nullable = false)
    private Integer pozSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "poz_ilosc", nullable = false, precision = 13, scale = 6)
    private BigDecimal pozIlosc;
    @Size(max = 16)
    @Column(name = "poz_mag_symbol", length = 16)
    private String pozMagSymbol;
    @Size(max = 64)
    @Column(name = "poz_mag_nazwa", length = 64)
    private String pozMagNazwa;
    @Size(max = 16)
    @Column(name = "poz_mag_sww_ku", length = 16)
    private String pozMagSwwKu;
    @Column(name = "poz_mag_st_vat", precision = 5, scale = 2)
    private BigDecimal pozMagStVat;
    @Column(name = "poz_mag_cena_spr", precision = 13, scale = 4)
    private BigDecimal pozMagCenaSpr;
    @Column(name = "poz_mag_vat_sprzedaz")
    private Character pozMagVatSprzedaz;
    @Size(max = 5)
    @Column(name = "poz_wal_skrot", length = 5)
    private String pozWalSkrot;
    @Column(name = "poz_wal_ilosc")
    private Short pozWalIlosc;
    @Column(name = "poz_wal_kurs", precision = 8, scale = 4)
    private BigDecimal pozWalKurs;
    @Column(name = "poz_wal_dzien")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pozWalDzien;
    @Column(name = "poz_mag_cena_spr_o", precision = 13, scale = 4)
    private BigDecimal pozMagCenaSprO;
    @Column(name = "poz_mag_typ")
    private Character pozMagTyp;
    @Column(name = "poz_rabat", precision = 5, scale = 2)
    private BigDecimal pozRabat;
    @Size(max = 16)
    @Column(name = "poz_kod_kres", length = 16)
    private String pozKodKres;
    @Size(max = 64)
    @Column(name = "poz_vchar_1", length = 64)
    private String pozVchar1;
    @Column(name = "poz_num_1", precision = 17, scale = 6)
    private BigDecimal pozNum1;
    @Column(name = "poz_num_2", precision = 17, scale = 6)
    private BigDecimal pozNum2;
    @Column(name = "poz_num_3", precision = 17, scale = 6)
    private BigDecimal pozNum3;
    @Column(name = "poz_num_4", precision = 17, scale = 6)
    private BigDecimal pozNum4;
    @Column(name = "poz_char_1")
    private Character pozChar1;
    @Column(name = "poz_char_2")
    private Character pozChar2;
    @OneToMany(mappedBy = "dspPozSerial")
    private List<DaneStatP> daneStatPList;
    @JoinColumn(name = "poz_fak_serial", referencedColumnName = "fak_serial", nullable = false)
    @ManyToOne(optional = false)
    private Fakrach pozFakSerial;
    @JoinColumn(name = "poz_mag_jed_serial", referencedColumnName = "jed_serial")
    @ManyToOne
    private Jednostka pozMagJedSerial;
    @JoinColumn(name = "poz_mag_serial", referencedColumnName = "mag_serial")
    @ManyToOne
    private Magazyn pozMagSerial;

    public Pozycja() {
    }

    public Pozycja(Integer pozSerial) {
        this.pozSerial = pozSerial;
    }

    public Pozycja(Integer pozSerial, BigDecimal pozIlosc) {
        this.pozSerial = pozSerial;
        this.pozIlosc = pozIlosc;
    }

    public Integer getPozSerial() {
        return pozSerial;
    }

    public void setPozSerial(Integer pozSerial) {
        this.pozSerial = pozSerial;
    }

    public BigDecimal getPozIlosc() {
        return pozIlosc;
    }

    public void setPozIlosc(BigDecimal pozIlosc) {
        this.pozIlosc = pozIlosc;
    }

    public String getPozMagSymbol() {
        return pozMagSymbol;
    }

    public void setPozMagSymbol(String pozMagSymbol) {
        this.pozMagSymbol = pozMagSymbol;
    }

    public String getPozMagNazwa() {
        return pozMagNazwa;
    }

    public void setPozMagNazwa(String pozMagNazwa) {
        this.pozMagNazwa = pozMagNazwa;
    }

    public String getPozMagSwwKu() {
        return pozMagSwwKu;
    }

    public void setPozMagSwwKu(String pozMagSwwKu) {
        this.pozMagSwwKu = pozMagSwwKu;
    }

    public BigDecimal getPozMagStVat() {
        return pozMagStVat;
    }

    public void setPozMagStVat(BigDecimal pozMagStVat) {
        this.pozMagStVat = pozMagStVat;
    }

    public BigDecimal getPozMagCenaSpr() {
        return pozMagCenaSpr;
    }

    public void setPozMagCenaSpr(BigDecimal pozMagCenaSpr) {
        this.pozMagCenaSpr = pozMagCenaSpr;
    }

    public Character getPozMagVatSprzedaz() {
        return pozMagVatSprzedaz;
    }

    public void setPozMagVatSprzedaz(Character pozMagVatSprzedaz) {
        this.pozMagVatSprzedaz = pozMagVatSprzedaz;
    }

    public String getPozWalSkrot() {
        return pozWalSkrot;
    }

    public void setPozWalSkrot(String pozWalSkrot) {
        this.pozWalSkrot = pozWalSkrot;
    }

    public Short getPozWalIlosc() {
        return pozWalIlosc;
    }

    public void setPozWalIlosc(Short pozWalIlosc) {
        this.pozWalIlosc = pozWalIlosc;
    }

    public BigDecimal getPozWalKurs() {
        return pozWalKurs;
    }

    public void setPozWalKurs(BigDecimal pozWalKurs) {
        this.pozWalKurs = pozWalKurs;
    }

    public Date getPozWalDzien() {
        return pozWalDzien;
    }

    public void setPozWalDzien(Date pozWalDzien) {
        this.pozWalDzien = pozWalDzien;
    }

    public BigDecimal getPozMagCenaSprO() {
        return pozMagCenaSprO;
    }

    public void setPozMagCenaSprO(BigDecimal pozMagCenaSprO) {
        this.pozMagCenaSprO = pozMagCenaSprO;
    }

    public Character getPozMagTyp() {
        return pozMagTyp;
    }

    public void setPozMagTyp(Character pozMagTyp) {
        this.pozMagTyp = pozMagTyp;
    }

    public BigDecimal getPozRabat() {
        return pozRabat;
    }

    public void setPozRabat(BigDecimal pozRabat) {
        this.pozRabat = pozRabat;
    }

    public String getPozKodKres() {
        return pozKodKres;
    }

    public void setPozKodKres(String pozKodKres) {
        this.pozKodKres = pozKodKres;
    }

    public String getPozVchar1() {
        return pozVchar1;
    }

    public void setPozVchar1(String pozVchar1) {
        this.pozVchar1 = pozVchar1;
    }

    public BigDecimal getPozNum1() {
        return pozNum1;
    }

    public void setPozNum1(BigDecimal pozNum1) {
        this.pozNum1 = pozNum1;
    }

    public BigDecimal getPozNum2() {
        return pozNum2;
    }

    public void setPozNum2(BigDecimal pozNum2) {
        this.pozNum2 = pozNum2;
    }

    public BigDecimal getPozNum3() {
        return pozNum3;
    }

    public void setPozNum3(BigDecimal pozNum3) {
        this.pozNum3 = pozNum3;
    }

    public BigDecimal getPozNum4() {
        return pozNum4;
    }

    public void setPozNum4(BigDecimal pozNum4) {
        this.pozNum4 = pozNum4;
    }

    public Character getPozChar1() {
        return pozChar1;
    }

    public void setPozChar1(Character pozChar1) {
        this.pozChar1 = pozChar1;
    }

    public Character getPozChar2() {
        return pozChar2;
    }

    public void setPozChar2(Character pozChar2) {
        this.pozChar2 = pozChar2;
    }

    @XmlTransient
    public List<DaneStatP> getDaneStatPList() {
        return daneStatPList;
    }

    public void setDaneStatPList(List<DaneStatP> daneStatPList) {
        this.daneStatPList = daneStatPList;
    }

    public Fakrach getPozFakSerial() {
        return pozFakSerial;
    }

    public void setPozFakSerial(Fakrach pozFakSerial) {
        this.pozFakSerial = pozFakSerial;
    }

    public Jednostka getPozMagJedSerial() {
        return pozMagJedSerial;
    }

    public void setPozMagJedSerial(Jednostka pozMagJedSerial) {
        this.pozMagJedSerial = pozMagJedSerial;
    }

    public Magazyn getPozMagSerial() {
        return pozMagSerial;
    }

    public void setPozMagSerial(Magazyn pozMagSerial) {
        this.pozMagSerial = pozMagSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pozSerial != null ? pozSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pozycja)) {
            return false;
        }
        Pozycja other = (Pozycja) object;
        if ((this.pozSerial == null && other.pozSerial != null) || (this.pozSerial != null && !this.pozSerial.equals(other.pozSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Pozycja[ pozSerial=" + pozSerial + " ]";
    }
    
}
