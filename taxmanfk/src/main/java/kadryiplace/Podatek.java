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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podatek", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Podatek.findAll", query = "SELECT p FROM Podatek p"),
    @NamedQuery(name = "Podatek.findByPodSerial", query = "SELECT p FROM Podatek p WHERE p.podSerial = :podSerial"),
    @NamedQuery(name = "Podatek.findByPodRok", query = "SELECT p FROM Podatek p WHERE p.podRok = :podRok"),
    @NamedQuery(name = "Podatek.findByPodZalStawka", query = "SELECT p FROM Podatek p WHERE p.podZalStawka = :podZalStawka"),
    @NamedQuery(name = "Podatek.findByPodZalWolna", query = "SELECT p FROM Podatek p WHERE p.podZalWolna = :podZalWolna"),
    @NamedQuery(name = "Podatek.findByPodKwotaOd", query = "SELECT p FROM Podatek p WHERE p.podKwotaOd = :podKwotaOd"),
    @NamedQuery(name = "Podatek.findByPodKwotaDo", query = "SELECT p FROM Podatek p WHERE p.podKwotaDo = :podKwotaDo"),
    @NamedQuery(name = "Podatek.findByPodKolejnosc", query = "SELECT p FROM Podatek p WHERE p.podKolejnosc = :podKolejnosc"),
    @NamedQuery(name = "Podatek.findByPodOstatnia", query = "SELECT p FROM Podatek p WHERE p.podOstatnia = :podOstatnia"),
    @NamedQuery(name = "Podatek.findByPodWolnaRok", query = "SELECT p FROM Podatek p WHERE p.podWolnaRok = :podWolnaRok"),
    @NamedQuery(name = "Podatek.findByPodRodzaj", query = "SELECT p FROM Podatek p WHERE p.podRodzaj = :podRodzaj"),
    @NamedQuery(name = "Podatek.findByPodKup", query = "SELECT p FROM Podatek p WHERE p.podKup = :podKup"),
    @NamedQuery(name = "Podatek.findByPodTylZusOpod", query = "SELECT p FROM Podatek p WHERE p.podTylZusOpod = :podTylZusOpod"),
    @NamedQuery(name = "Podatek.findByPodChar1", query = "SELECT p FROM Podatek p WHERE p.podChar1 = :podChar1"),
    @NamedQuery(name = "Podatek.findByPodChar2", query = "SELECT p FROM Podatek p WHERE p.podChar2 = :podChar2"),
    @NamedQuery(name = "Podatek.findByPodChar3", query = "SELECT p FROM Podatek p WHERE p.podChar3 = :podChar3"),
    @NamedQuery(name = "Podatek.findByPodChar4", query = "SELECT p FROM Podatek p WHERE p.podChar4 = :podChar4"),
    @NamedQuery(name = "Podatek.findByPodChar5", query = "SELECT p FROM Podatek p WHERE p.podChar5 = :podChar5"),
    @NamedQuery(name = "Podatek.findByPodChar6", query = "SELECT p FROM Podatek p WHERE p.podChar6 = :podChar6"),
    @NamedQuery(name = "Podatek.findByPodChar7", query = "SELECT p FROM Podatek p WHERE p.podChar7 = :podChar7"),
    @NamedQuery(name = "Podatek.findByPodChar8", query = "SELECT p FROM Podatek p WHERE p.podChar8 = :podChar8"),
    @NamedQuery(name = "Podatek.findByPodNum1", query = "SELECT p FROM Podatek p WHERE p.podNum1 = :podNum1"),
    @NamedQuery(name = "Podatek.findByPodNum2", query = "SELECT p FROM Podatek p WHERE p.podNum2 = :podNum2"),
    @NamedQuery(name = "Podatek.findByPodData1", query = "SELECT p FROM Podatek p WHERE p.podData1 = :podData1"),
    @NamedQuery(name = "Podatek.findByPodData2", query = "SELECT p FROM Podatek p WHERE p.podData2 = :podData2")})
public class Podatek implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pod_serial", nullable = false)
    private Integer podSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pod_rok", nullable = false)
    private short podRok;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "pod_zal_stawka", nullable = false, precision = 5, scale = 2)
    private BigDecimal podZalStawka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pod_zal_wolna", nullable = false, precision = 13, scale = 2)
    private BigDecimal podZalWolna;
    @Column(name = "pod_kwota_od", precision = 13, scale = 2)
    private BigDecimal podKwotaOd;
    @Column(name = "pod_kwota_do", precision = 13, scale = 2)
    private BigDecimal podKwotaDo;
    @Column(name = "pod_kolejnosc")
    private Short podKolejnosc;
    @Column(name = "pod_ostatnia")
    private Character podOstatnia;
    @Column(name = "pod_wolna_rok", precision = 13, scale = 2)
    private BigDecimal podWolnaRok;
    @Column(name = "pod_rodzaj")
    private Character podRodzaj;
    @Column(name = "pod_kup", precision = 13, scale = 2)
    private BigDecimal podKup;
    @Column(name = "pod_tyl_zus_opod")
    private Character podTylZusOpod;
    @Column(name = "pod_char_1")
    private Character podChar1;
    @Column(name = "pod_char_2")
    private Character podChar2;
    @Column(name = "pod_char_3")
    private Character podChar3;
    @Column(name = "pod_char_4")
    private Character podChar4;
    @Column(name = "pod_char_5")
    private Character podChar5;
    @Column(name = "pod_char_6")
    private Character podChar6;
    @Column(name = "pod_char_7")
    private Character podChar7;
    @Column(name = "pod_char_8")
    private Character podChar8;
    @Column(name = "pod_num_1", precision = 17, scale = 6)
    private BigDecimal podNum1;
    @Column(name = "pod_num_2", precision = 17, scale = 6)
    private BigDecimal podNum2;
    @Column(name = "pod_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date podData1;
    @Column(name = "pod_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date podData2;

    public Podatek() {
    }

    public Podatek(Integer podSerial) {
        this.podSerial = podSerial;
    }

    public Podatek(Integer podSerial, short podRok, BigDecimal podZalStawka, BigDecimal podZalWolna) {
        this.podSerial = podSerial;
        this.podRok = podRok;
        this.podZalStawka = podZalStawka;
        this.podZalWolna = podZalWolna;
    }

    public Integer getPodSerial() {
        return podSerial;
    }

    public void setPodSerial(Integer podSerial) {
        this.podSerial = podSerial;
    }

    public short getPodRok() {
        return podRok;
    }

    public void setPodRok(short podRok) {
        this.podRok = podRok;
    }

    public BigDecimal getPodZalStawka() {
        return podZalStawka;
    }

    public void setPodZalStawka(BigDecimal podZalStawka) {
        this.podZalStawka = podZalStawka;
    }

    public BigDecimal getPodZalWolna() {
        return podZalWolna;
    }

    public void setPodZalWolna(BigDecimal podZalWolna) {
        this.podZalWolna = podZalWolna;
    }

    public BigDecimal getPodKwotaOd() {
        return podKwotaOd;
    }

    public void setPodKwotaOd(BigDecimal podKwotaOd) {
        this.podKwotaOd = podKwotaOd;
    }

    public BigDecimal getPodKwotaDo() {
        return podKwotaDo;
    }

    public void setPodKwotaDo(BigDecimal podKwotaDo) {
        this.podKwotaDo = podKwotaDo;
    }

    public Short getPodKolejnosc() {
        return podKolejnosc;
    }

    public void setPodKolejnosc(Short podKolejnosc) {
        this.podKolejnosc = podKolejnosc;
    }

    public Character getPodOstatnia() {
        return podOstatnia;
    }

    public void setPodOstatnia(Character podOstatnia) {
        this.podOstatnia = podOstatnia;
    }

    public BigDecimal getPodWolnaRok() {
        return podWolnaRok;
    }

    public void setPodWolnaRok(BigDecimal podWolnaRok) {
        this.podWolnaRok = podWolnaRok;
    }

    public Character getPodRodzaj() {
        return podRodzaj;
    }

    public void setPodRodzaj(Character podRodzaj) {
        this.podRodzaj = podRodzaj;
    }

    public BigDecimal getPodKup() {
        return podKup;
    }

    public void setPodKup(BigDecimal podKup) {
        this.podKup = podKup;
    }

    public Character getPodTylZusOpod() {
        return podTylZusOpod;
    }

    public void setPodTylZusOpod(Character podTylZusOpod) {
        this.podTylZusOpod = podTylZusOpod;
    }

    public Character getPodChar1() {
        return podChar1;
    }

    public void setPodChar1(Character podChar1) {
        this.podChar1 = podChar1;
    }

    public Character getPodChar2() {
        return podChar2;
    }

    public void setPodChar2(Character podChar2) {
        this.podChar2 = podChar2;
    }

    public Character getPodChar3() {
        return podChar3;
    }

    public void setPodChar3(Character podChar3) {
        this.podChar3 = podChar3;
    }

    public Character getPodChar4() {
        return podChar4;
    }

    public void setPodChar4(Character podChar4) {
        this.podChar4 = podChar4;
    }

    public Character getPodChar5() {
        return podChar5;
    }

    public void setPodChar5(Character podChar5) {
        this.podChar5 = podChar5;
    }

    public Character getPodChar6() {
        return podChar6;
    }

    public void setPodChar6(Character podChar6) {
        this.podChar6 = podChar6;
    }

    public Character getPodChar7() {
        return podChar7;
    }

    public void setPodChar7(Character podChar7) {
        this.podChar7 = podChar7;
    }

    public Character getPodChar8() {
        return podChar8;
    }

    public void setPodChar8(Character podChar8) {
        this.podChar8 = podChar8;
    }

    public BigDecimal getPodNum1() {
        return podNum1;
    }

    public void setPodNum1(BigDecimal podNum1) {
        this.podNum1 = podNum1;
    }

    public BigDecimal getPodNum2() {
        return podNum2;
    }

    public void setPodNum2(BigDecimal podNum2) {
        this.podNum2 = podNum2;
    }

    public Date getPodData1() {
        return podData1;
    }

    public void setPodData1(Date podData1) {
        this.podData1 = podData1;
    }

    public Date getPodData2() {
        return podData2;
    }

    public void setPodData2(Date podData2) {
        this.podData2 = podData2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (podSerial != null ? podSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Podatek)) {
            return false;
        }
        Podatek other = (Podatek) object;
        if ((this.podSerial == null && other.podSerial != null) || (this.podSerial != null && !this.podSerial.equals(other.podSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Podatek[ podSerial=" + podSerial + " ]";
    }
    
}
