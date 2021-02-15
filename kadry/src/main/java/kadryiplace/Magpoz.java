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
@Table(name = "magpoz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Magpoz.findAll", query = "SELECT m FROM Magpoz m"),
    @NamedQuery(name = "Magpoz.findByMpzSerial", query = "SELECT m FROM Magpoz m WHERE m.mpzSerial = :mpzSerial"),
    @NamedQuery(name = "Magpoz.findByMpzIlosc", query = "SELECT m FROM Magpoz m WHERE m.mpzIlosc = :mpzIlosc"),
    @NamedQuery(name = "Magpoz.findByMpzMagSymbol", query = "SELECT m FROM Magpoz m WHERE m.mpzMagSymbol = :mpzMagSymbol"),
    @NamedQuery(name = "Magpoz.findByMpzMagNazwa", query = "SELECT m FROM Magpoz m WHERE m.mpzMagNazwa = :mpzMagNazwa"),
    @NamedQuery(name = "Magpoz.findByMpzMagSwwKu", query = "SELECT m FROM Magpoz m WHERE m.mpzMagSwwKu = :mpzMagSwwKu"),
    @NamedQuery(name = "Magpoz.findByMpzMagStVat", query = "SELECT m FROM Magpoz m WHERE m.mpzMagStVat = :mpzMagStVat"),
    @NamedQuery(name = "Magpoz.findByMpzMagCena", query = "SELECT m FROM Magpoz m WHERE m.mpzMagCena = :mpzMagCena"),
    @NamedQuery(name = "Magpoz.findByMpzMagVat", query = "SELECT m FROM Magpoz m WHERE m.mpzMagVat = :mpzMagVat"),
    @NamedQuery(name = "Magpoz.findByMpzWalSkrot", query = "SELECT m FROM Magpoz m WHERE m.mpzWalSkrot = :mpzWalSkrot"),
    @NamedQuery(name = "Magpoz.findByMpzWalIlosc", query = "SELECT m FROM Magpoz m WHERE m.mpzWalIlosc = :mpzWalIlosc"),
    @NamedQuery(name = "Magpoz.findByMpzWalKurs", query = "SELECT m FROM Magpoz m WHERE m.mpzWalKurs = :mpzWalKurs"),
    @NamedQuery(name = "Magpoz.findByMpzWalDzien", query = "SELECT m FROM Magpoz m WHERE m.mpzWalDzien = :mpzWalDzien"),
    @NamedQuery(name = "Magpoz.findByMpzMagCenaO", query = "SELECT m FROM Magpoz m WHERE m.mpzMagCenaO = :mpzMagCenaO"),
    @NamedQuery(name = "Magpoz.findByMpzRabat", query = "SELECT m FROM Magpoz m WHERE m.mpzRabat = :mpzRabat"),
    @NamedQuery(name = "Magpoz.findByMpzKodKres", query = "SELECT m FROM Magpoz m WHERE m.mpzKodKres = :mpzKodKres"),
    @NamedQuery(name = "Magpoz.findByMpzVchar1", query = "SELECT m FROM Magpoz m WHERE m.mpzVchar1 = :mpzVchar1"),
    @NamedQuery(name = "Magpoz.findByMpzNum1", query = "SELECT m FROM Magpoz m WHERE m.mpzNum1 = :mpzNum1"),
    @NamedQuery(name = "Magpoz.findByMpzNum2", query = "SELECT m FROM Magpoz m WHERE m.mpzNum2 = :mpzNum2"),
    @NamedQuery(name = "Magpoz.findByMpzNum3", query = "SELECT m FROM Magpoz m WHERE m.mpzNum3 = :mpzNum3"),
    @NamedQuery(name = "Magpoz.findByMpzNum4", query = "SELECT m FROM Magpoz m WHERE m.mpzNum4 = :mpzNum4"),
    @NamedQuery(name = "Magpoz.findByMpzChar1", query = "SELECT m FROM Magpoz m WHERE m.mpzChar1 = :mpzChar1"),
    @NamedQuery(name = "Magpoz.findByMpzChar2", query = "SELECT m FROM Magpoz m WHERE m.mpzChar2 = :mpzChar2")})
public class Magpoz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mpz_serial", nullable = false)
    private Integer mpzSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "mpz_ilosc", nullable = false, precision = 13, scale = 6)
    private BigDecimal mpzIlosc;
    @Size(max = 16)
    @Column(name = "mpz_mag_symbol", length = 16)
    private String mpzMagSymbol;
    @Size(max = 64)
    @Column(name = "mpz_mag_nazwa", length = 64)
    private String mpzMagNazwa;
    @Size(max = 16)
    @Column(name = "mpz_mag_sww_ku", length = 16)
    private String mpzMagSwwKu;
    @Column(name = "mpz_mag_st_vat", precision = 5, scale = 2)
    private BigDecimal mpzMagStVat;
    @Column(name = "mpz_mag_cena", precision = 13, scale = 4)
    private BigDecimal mpzMagCena;
    @Column(name = "mpz_mag_vat")
    private Character mpzMagVat;
    @Size(max = 5)
    @Column(name = "mpz_wal_skrot", length = 5)
    private String mpzWalSkrot;
    @Column(name = "mpz_wal_ilosc")
    private Short mpzWalIlosc;
    @Column(name = "mpz_wal_kurs", precision = 8, scale = 4)
    private BigDecimal mpzWalKurs;
    @Column(name = "mpz_wal_dzien")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mpzWalDzien;
    @Column(name = "mpz_mag_cena_o", precision = 13, scale = 4)
    private BigDecimal mpzMagCenaO;
    @Column(name = "mpz_rabat", precision = 5, scale = 2)
    private BigDecimal mpzRabat;
    @Size(max = 32)
    @Column(name = "mpz_kod_kres", length = 32)
    private String mpzKodKres;
    @Size(max = 64)
    @Column(name = "mpz_vchar_1", length = 64)
    private String mpzVchar1;
    @Column(name = "mpz_num_1", precision = 17, scale = 6)
    private BigDecimal mpzNum1;
    @Column(name = "mpz_num_2", precision = 17, scale = 6)
    private BigDecimal mpzNum2;
    @Column(name = "mpz_num_3", precision = 17, scale = 6)
    private BigDecimal mpzNum3;
    @Column(name = "mpz_num_4", precision = 17, scale = 6)
    private BigDecimal mpzNum4;
    @Column(name = "mpz_char_1")
    private Character mpzChar1;
    @Column(name = "mpz_char_2")
    private Character mpzChar2;
    @OneToMany(mappedBy = "dspMpzSerial")
    private List<DaneStatP> daneStatPList;
    @JoinColumn(name = "mpz_mag_jed_serial", referencedColumnName = "jed_serial")
    @ManyToOne
    private Jednostka mpzMagJedSerial;
    @JoinColumn(name = "mpz_mag_serial", referencedColumnName = "mag_serial")
    @ManyToOne
    private Magazyn mpzMagSerial;
    @JoinColumn(name = "mpz_mdo_serial", referencedColumnName = "mdo_serial", nullable = false)
    @ManyToOne(optional = false)
    private Magdok mpzMdoSerial;

    public Magpoz() {
    }

    public Magpoz(Integer mpzSerial) {
        this.mpzSerial = mpzSerial;
    }

    public Magpoz(Integer mpzSerial, BigDecimal mpzIlosc) {
        this.mpzSerial = mpzSerial;
        this.mpzIlosc = mpzIlosc;
    }

    public Integer getMpzSerial() {
        return mpzSerial;
    }

    public void setMpzSerial(Integer mpzSerial) {
        this.mpzSerial = mpzSerial;
    }

    public BigDecimal getMpzIlosc() {
        return mpzIlosc;
    }

    public void setMpzIlosc(BigDecimal mpzIlosc) {
        this.mpzIlosc = mpzIlosc;
    }

    public String getMpzMagSymbol() {
        return mpzMagSymbol;
    }

    public void setMpzMagSymbol(String mpzMagSymbol) {
        this.mpzMagSymbol = mpzMagSymbol;
    }

    public String getMpzMagNazwa() {
        return mpzMagNazwa;
    }

    public void setMpzMagNazwa(String mpzMagNazwa) {
        this.mpzMagNazwa = mpzMagNazwa;
    }

    public String getMpzMagSwwKu() {
        return mpzMagSwwKu;
    }

    public void setMpzMagSwwKu(String mpzMagSwwKu) {
        this.mpzMagSwwKu = mpzMagSwwKu;
    }

    public BigDecimal getMpzMagStVat() {
        return mpzMagStVat;
    }

    public void setMpzMagStVat(BigDecimal mpzMagStVat) {
        this.mpzMagStVat = mpzMagStVat;
    }

    public BigDecimal getMpzMagCena() {
        return mpzMagCena;
    }

    public void setMpzMagCena(BigDecimal mpzMagCena) {
        this.mpzMagCena = mpzMagCena;
    }

    public Character getMpzMagVat() {
        return mpzMagVat;
    }

    public void setMpzMagVat(Character mpzMagVat) {
        this.mpzMagVat = mpzMagVat;
    }

    public String getMpzWalSkrot() {
        return mpzWalSkrot;
    }

    public void setMpzWalSkrot(String mpzWalSkrot) {
        this.mpzWalSkrot = mpzWalSkrot;
    }

    public Short getMpzWalIlosc() {
        return mpzWalIlosc;
    }

    public void setMpzWalIlosc(Short mpzWalIlosc) {
        this.mpzWalIlosc = mpzWalIlosc;
    }

    public BigDecimal getMpzWalKurs() {
        return mpzWalKurs;
    }

    public void setMpzWalKurs(BigDecimal mpzWalKurs) {
        this.mpzWalKurs = mpzWalKurs;
    }

    public Date getMpzWalDzien() {
        return mpzWalDzien;
    }

    public void setMpzWalDzien(Date mpzWalDzien) {
        this.mpzWalDzien = mpzWalDzien;
    }

    public BigDecimal getMpzMagCenaO() {
        return mpzMagCenaO;
    }

    public void setMpzMagCenaO(BigDecimal mpzMagCenaO) {
        this.mpzMagCenaO = mpzMagCenaO;
    }

    public BigDecimal getMpzRabat() {
        return mpzRabat;
    }

    public void setMpzRabat(BigDecimal mpzRabat) {
        this.mpzRabat = mpzRabat;
    }

    public String getMpzKodKres() {
        return mpzKodKres;
    }

    public void setMpzKodKres(String mpzKodKres) {
        this.mpzKodKres = mpzKodKres;
    }

    public String getMpzVchar1() {
        return mpzVchar1;
    }

    public void setMpzVchar1(String mpzVchar1) {
        this.mpzVchar1 = mpzVchar1;
    }

    public BigDecimal getMpzNum1() {
        return mpzNum1;
    }

    public void setMpzNum1(BigDecimal mpzNum1) {
        this.mpzNum1 = mpzNum1;
    }

    public BigDecimal getMpzNum2() {
        return mpzNum2;
    }

    public void setMpzNum2(BigDecimal mpzNum2) {
        this.mpzNum2 = mpzNum2;
    }

    public BigDecimal getMpzNum3() {
        return mpzNum3;
    }

    public void setMpzNum3(BigDecimal mpzNum3) {
        this.mpzNum3 = mpzNum3;
    }

    public BigDecimal getMpzNum4() {
        return mpzNum4;
    }

    public void setMpzNum4(BigDecimal mpzNum4) {
        this.mpzNum4 = mpzNum4;
    }

    public Character getMpzChar1() {
        return mpzChar1;
    }

    public void setMpzChar1(Character mpzChar1) {
        this.mpzChar1 = mpzChar1;
    }

    public Character getMpzChar2() {
        return mpzChar2;
    }

    public void setMpzChar2(Character mpzChar2) {
        this.mpzChar2 = mpzChar2;
    }

    @XmlTransient
    public List<DaneStatP> getDaneStatPList() {
        return daneStatPList;
    }

    public void setDaneStatPList(List<DaneStatP> daneStatPList) {
        this.daneStatPList = daneStatPList;
    }

    public Jednostka getMpzMagJedSerial() {
        return mpzMagJedSerial;
    }

    public void setMpzMagJedSerial(Jednostka mpzMagJedSerial) {
        this.mpzMagJedSerial = mpzMagJedSerial;
    }

    public Magazyn getMpzMagSerial() {
        return mpzMagSerial;
    }

    public void setMpzMagSerial(Magazyn mpzMagSerial) {
        this.mpzMagSerial = mpzMagSerial;
    }

    public Magdok getMpzMdoSerial() {
        return mpzMdoSerial;
    }

    public void setMpzMdoSerial(Magdok mpzMdoSerial) {
        this.mpzMdoSerial = mpzMdoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mpzSerial != null ? mpzSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Magpoz)) {
            return false;
        }
        Magpoz other = (Magpoz) object;
        if ((this.mpzSerial == null && other.mpzSerial != null) || (this.mpzSerial != null && !this.mpzSerial.equals(other.mpzSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Magpoz[ mpzSerial=" + mpzSerial + " ]";
    }
    
}
