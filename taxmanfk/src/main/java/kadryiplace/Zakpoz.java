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
@Table(name = "zakpoz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zakpoz.findAll", query = "SELECT z FROM Zakpoz z"),
    @NamedQuery(name = "Zakpoz.findByZpzSerial", query = "SELECT z FROM Zakpoz z WHERE z.zpzSerial = :zpzSerial"),
    @NamedQuery(name = "Zakpoz.findByZpzIlosc", query = "SELECT z FROM Zakpoz z WHERE z.zpzIlosc = :zpzIlosc"),
    @NamedQuery(name = "Zakpoz.findByZpzMagSymbol", query = "SELECT z FROM Zakpoz z WHERE z.zpzMagSymbol = :zpzMagSymbol"),
    @NamedQuery(name = "Zakpoz.findByZpzMagNazwa", query = "SELECT z FROM Zakpoz z WHERE z.zpzMagNazwa = :zpzMagNazwa"),
    @NamedQuery(name = "Zakpoz.findByZpzMagSwwKu", query = "SELECT z FROM Zakpoz z WHERE z.zpzMagSwwKu = :zpzMagSwwKu"),
    @NamedQuery(name = "Zakpoz.findByZpzMagStVat", query = "SELECT z FROM Zakpoz z WHERE z.zpzMagStVat = :zpzMagStVat"),
    @NamedQuery(name = "Zakpoz.findByZpzMagCenaZak", query = "SELECT z FROM Zakpoz z WHERE z.zpzMagCenaZak = :zpzMagCenaZak"),
    @NamedQuery(name = "Zakpoz.findByZpzMagZwolZVat", query = "SELECT z FROM Zakpoz z WHERE z.zpzMagZwolZVat = :zpzMagZwolZVat"),
    @NamedQuery(name = "Zakpoz.findByZpzVchar1", query = "SELECT z FROM Zakpoz z WHERE z.zpzVchar1 = :zpzVchar1"),
    @NamedQuery(name = "Zakpoz.findByZpzNum1", query = "SELECT z FROM Zakpoz z WHERE z.zpzNum1 = :zpzNum1"),
    @NamedQuery(name = "Zakpoz.findByZpzNum2", query = "SELECT z FROM Zakpoz z WHERE z.zpzNum2 = :zpzNum2"),
    @NamedQuery(name = "Zakpoz.findByZpzNum3", query = "SELECT z FROM Zakpoz z WHERE z.zpzNum3 = :zpzNum3"),
    @NamedQuery(name = "Zakpoz.findByZpzNum4", query = "SELECT z FROM Zakpoz z WHERE z.zpzNum4 = :zpzNum4"),
    @NamedQuery(name = "Zakpoz.findByZpzChar1", query = "SELECT z FROM Zakpoz z WHERE z.zpzChar1 = :zpzChar1"),
    @NamedQuery(name = "Zakpoz.findByZpzChar2", query = "SELECT z FROM Zakpoz z WHERE z.zpzChar2 = :zpzChar2"),
    @NamedQuery(name = "Zakpoz.findByZpzMagCenaO", query = "SELECT z FROM Zakpoz z WHERE z.zpzMagCenaO = :zpzMagCenaO"),
    @NamedQuery(name = "Zakpoz.findByZpzRabat", query = "SELECT z FROM Zakpoz z WHERE z.zpzRabat = :zpzRabat")})
public class Zakpoz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "zpz_serial", nullable = false)
    private Integer zpzSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "zpz_ilosc", nullable = false, precision = 13, scale = 6)
    private BigDecimal zpzIlosc;
    @Size(max = 16)
    @Column(name = "zpz_mag_symbol", length = 16)
    private String zpzMagSymbol;
    @Size(max = 64)
    @Column(name = "zpz_mag_nazwa", length = 64)
    private String zpzMagNazwa;
    @Size(max = 16)
    @Column(name = "zpz_mag_sww_ku", length = 16)
    private String zpzMagSwwKu;
    @Column(name = "zpz_mag_st_vat", precision = 5, scale = 2)
    private BigDecimal zpzMagStVat;
    @Column(name = "zpz_mag_cena_zak", precision = 13, scale = 4)
    private BigDecimal zpzMagCenaZak;
    @Column(name = "zpz_mag_zwol_z_vat")
    private Character zpzMagZwolZVat;
    @Size(max = 64)
    @Column(name = "zpz_vchar_1", length = 64)
    private String zpzVchar1;
    @Column(name = "zpz_num_1", precision = 17, scale = 6)
    private BigDecimal zpzNum1;
    @Column(name = "zpz_num_2", precision = 17, scale = 6)
    private BigDecimal zpzNum2;
    @Column(name = "zpz_num_3", precision = 17, scale = 6)
    private BigDecimal zpzNum3;
    @Column(name = "zpz_num_4", precision = 17, scale = 6)
    private BigDecimal zpzNum4;
    @Column(name = "zpz_char_1")
    private Character zpzChar1;
    @Column(name = "zpz_char_2")
    private Character zpzChar2;
    @Column(name = "zpz_mag_cena_o", precision = 13, scale = 4)
    private BigDecimal zpzMagCenaO;
    @Column(name = "zpz_rabat", precision = 5, scale = 2)
    private BigDecimal zpzRabat;
    @JoinColumn(name = "zpz_mag_jed_serial", referencedColumnName = "jed_serial")
    @ManyToOne
    private Jednostka zpzMagJedSerial;
    @JoinColumn(name = "zpz_mag_serial", referencedColumnName = "mag_serial", nullable = false)
    @ManyToOne(optional = false)
    private Magazyn zpzMagSerial;
    @JoinColumn(name = "zpz_zdo_serial", referencedColumnName = "zdo_serial", nullable = false)
    @ManyToOne(optional = false)
    private Zakdok zpzZdoSerial;
    @OneToMany(mappedBy = "dspZpzSerial")
    private List<DaneStatP> daneStatPList;

    public Zakpoz() {
    }

    public Zakpoz(Integer zpzSerial) {
        this.zpzSerial = zpzSerial;
    }

    public Zakpoz(Integer zpzSerial, BigDecimal zpzIlosc) {
        this.zpzSerial = zpzSerial;
        this.zpzIlosc = zpzIlosc;
    }

    public Integer getZpzSerial() {
        return zpzSerial;
    }

    public void setZpzSerial(Integer zpzSerial) {
        this.zpzSerial = zpzSerial;
    }

    public BigDecimal getZpzIlosc() {
        return zpzIlosc;
    }

    public void setZpzIlosc(BigDecimal zpzIlosc) {
        this.zpzIlosc = zpzIlosc;
    }

    public String getZpzMagSymbol() {
        return zpzMagSymbol;
    }

    public void setZpzMagSymbol(String zpzMagSymbol) {
        this.zpzMagSymbol = zpzMagSymbol;
    }

    public String getZpzMagNazwa() {
        return zpzMagNazwa;
    }

    public void setZpzMagNazwa(String zpzMagNazwa) {
        this.zpzMagNazwa = zpzMagNazwa;
    }

    public String getZpzMagSwwKu() {
        return zpzMagSwwKu;
    }

    public void setZpzMagSwwKu(String zpzMagSwwKu) {
        this.zpzMagSwwKu = zpzMagSwwKu;
    }

    public BigDecimal getZpzMagStVat() {
        return zpzMagStVat;
    }

    public void setZpzMagStVat(BigDecimal zpzMagStVat) {
        this.zpzMagStVat = zpzMagStVat;
    }

    public BigDecimal getZpzMagCenaZak() {
        return zpzMagCenaZak;
    }

    public void setZpzMagCenaZak(BigDecimal zpzMagCenaZak) {
        this.zpzMagCenaZak = zpzMagCenaZak;
    }

    public Character getZpzMagZwolZVat() {
        return zpzMagZwolZVat;
    }

    public void setZpzMagZwolZVat(Character zpzMagZwolZVat) {
        this.zpzMagZwolZVat = zpzMagZwolZVat;
    }

    public String getZpzVchar1() {
        return zpzVchar1;
    }

    public void setZpzVchar1(String zpzVchar1) {
        this.zpzVchar1 = zpzVchar1;
    }

    public BigDecimal getZpzNum1() {
        return zpzNum1;
    }

    public void setZpzNum1(BigDecimal zpzNum1) {
        this.zpzNum1 = zpzNum1;
    }

    public BigDecimal getZpzNum2() {
        return zpzNum2;
    }

    public void setZpzNum2(BigDecimal zpzNum2) {
        this.zpzNum2 = zpzNum2;
    }

    public BigDecimal getZpzNum3() {
        return zpzNum3;
    }

    public void setZpzNum3(BigDecimal zpzNum3) {
        this.zpzNum3 = zpzNum3;
    }

    public BigDecimal getZpzNum4() {
        return zpzNum4;
    }

    public void setZpzNum4(BigDecimal zpzNum4) {
        this.zpzNum4 = zpzNum4;
    }

    public Character getZpzChar1() {
        return zpzChar1;
    }

    public void setZpzChar1(Character zpzChar1) {
        this.zpzChar1 = zpzChar1;
    }

    public Character getZpzChar2() {
        return zpzChar2;
    }

    public void setZpzChar2(Character zpzChar2) {
        this.zpzChar2 = zpzChar2;
    }

    public BigDecimal getZpzMagCenaO() {
        return zpzMagCenaO;
    }

    public void setZpzMagCenaO(BigDecimal zpzMagCenaO) {
        this.zpzMagCenaO = zpzMagCenaO;
    }

    public BigDecimal getZpzRabat() {
        return zpzRabat;
    }

    public void setZpzRabat(BigDecimal zpzRabat) {
        this.zpzRabat = zpzRabat;
    }

    public Jednostka getZpzMagJedSerial() {
        return zpzMagJedSerial;
    }

    public void setZpzMagJedSerial(Jednostka zpzMagJedSerial) {
        this.zpzMagJedSerial = zpzMagJedSerial;
    }

    public Magazyn getZpzMagSerial() {
        return zpzMagSerial;
    }

    public void setZpzMagSerial(Magazyn zpzMagSerial) {
        this.zpzMagSerial = zpzMagSerial;
    }

    public Zakdok getZpzZdoSerial() {
        return zpzZdoSerial;
    }

    public void setZpzZdoSerial(Zakdok zpzZdoSerial) {
        this.zpzZdoSerial = zpzZdoSerial;
    }

    @XmlTransient
    public List<DaneStatP> getDaneStatPList() {
        return daneStatPList;
    }

    public void setDaneStatPList(List<DaneStatP> daneStatPList) {
        this.daneStatPList = daneStatPList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zpzSerial != null ? zpzSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zakpoz)) {
            return false;
        }
        Zakpoz other = (Zakpoz) object;
        if ((this.zpzSerial == null && other.zpzSerial != null) || (this.zpzSerial != null && !this.zpzSerial.equals(other.zpzSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Zakpoz[ zpzSerial=" + zpzSerial + " ]";
    }
    
}
