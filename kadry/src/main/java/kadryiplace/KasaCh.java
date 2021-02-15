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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kasa_ch", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KasaCh.findAll", query = "SELECT k FROM KasaCh k"),
    @NamedQuery(name = "KasaCh.findByKchSerial", query = "SELECT k FROM KasaCh k WHERE k.kchSerial = :kchSerial"),
    @NamedQuery(name = "KasaCh.findByKchKod", query = "SELECT k FROM KasaCh k WHERE k.kchKod = :kchKod"),
    @NamedQuery(name = "KasaCh.findByKchNazwa", query = "SELECT k FROM KasaCh k WHERE k.kchNazwa = :kchNazwa"),
    @NamedQuery(name = "KasaCh.findByKchKonto", query = "SELECT k FROM KasaCh k WHERE k.kchKonto = :kchKonto"),
    @NamedQuery(name = "KasaCh.findByKchChar1", query = "SELECT k FROM KasaCh k WHERE k.kchChar1 = :kchChar1"),
    @NamedQuery(name = "KasaCh.findByKchChar2", query = "SELECT k FROM KasaCh k WHERE k.kchChar2 = :kchChar2"),
    @NamedQuery(name = "KasaCh.findByKchChar3", query = "SELECT k FROM KasaCh k WHERE k.kchChar3 = :kchChar3"),
    @NamedQuery(name = "KasaCh.findByKchChar4", query = "SELECT k FROM KasaCh k WHERE k.kchChar4 = :kchChar4"),
    @NamedQuery(name = "KasaCh.findByKchInt1", query = "SELECT k FROM KasaCh k WHERE k.kchInt1 = :kchInt1"),
    @NamedQuery(name = "KasaCh.findByKchInt2", query = "SELECT k FROM KasaCh k WHERE k.kchInt2 = :kchInt2"),
    @NamedQuery(name = "KasaCh.findByKchNum1", query = "SELECT k FROM KasaCh k WHERE k.kchNum1 = :kchNum1"),
    @NamedQuery(name = "KasaCh.findByKchNum2", query = "SELECT k FROM KasaCh k WHERE k.kchNum2 = :kchNum2"),
    @NamedQuery(name = "KasaCh.findByKchData1", query = "SELECT k FROM KasaCh k WHERE k.kchData1 = :kchData1"),
    @NamedQuery(name = "KasaCh.findByKchData2", query = "SELECT k FROM KasaCh k WHERE k.kchData2 = :kchData2"),
    @NamedQuery(name = "KasaCh.findByKchVchar1", query = "SELECT k FROM KasaCh k WHERE k.kchVchar1 = :kchVchar1"),
    @NamedQuery(name = "KasaCh.findByKchVchar2", query = "SELECT k FROM KasaCh k WHERE k.kchVchar2 = :kchVchar2"),
    @NamedQuery(name = "KasaCh.findByKchOpis", query = "SELECT k FROM KasaCh k WHERE k.kchOpis = :kchOpis"),
    @NamedQuery(name = "KasaCh.findByKchRodzaj", query = "SELECT k FROM KasaCh k WHERE k.kchRodzaj = :kchRodzaj")})
public class KasaCh implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "kch_serial", nullable = false)
    private Integer kchSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "kch_kod", nullable = false, length = 16)
    private String kchKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "kch_nazwa", nullable = false, length = 32)
    private String kchNazwa;
    @Size(max = 64)
    @Column(name = "kch_konto", length = 64)
    private String kchKonto;
    @Column(name = "kch_char_1")
    private Character kchChar1;
    @Column(name = "kch_char_2")
    private Character kchChar2;
    @Column(name = "kch_char_3")
    private Character kchChar3;
    @Column(name = "kch_char_4")
    private Character kchChar4;
    @Column(name = "kch_int_1")
    private Integer kchInt1;
    @Column(name = "kch_int_2")
    private Integer kchInt2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kch_num_1", precision = 17, scale = 6)
    private BigDecimal kchNum1;
    @Column(name = "kch_num_2", precision = 17, scale = 6)
    private BigDecimal kchNum2;
    @Column(name = "kch_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kchData1;
    @Column(name = "kch_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kchData2;
    @Size(max = 64)
    @Column(name = "kch_vchar_1", length = 64)
    private String kchVchar1;
    @Size(max = 64)
    @Column(name = "kch_vchar_2", length = 64)
    private String kchVchar2;
    @Size(max = 64)
    @Column(name = "kch_opis", length = 64)
    private String kchOpis;
    @Column(name = "kch_rodzaj")
    private Character kchRodzaj;
    @JoinColumn(name = "kch_ban_serial", referencedColumnName = "ban_serial")
    @ManyToOne
    private Bank kchBanSerial;

    public KasaCh() {
    }

    public KasaCh(Integer kchSerial) {
        this.kchSerial = kchSerial;
    }

    public KasaCh(Integer kchSerial, String kchKod, String kchNazwa) {
        this.kchSerial = kchSerial;
        this.kchKod = kchKod;
        this.kchNazwa = kchNazwa;
    }

    public Integer getKchSerial() {
        return kchSerial;
    }

    public void setKchSerial(Integer kchSerial) {
        this.kchSerial = kchSerial;
    }

    public String getKchKod() {
        return kchKod;
    }

    public void setKchKod(String kchKod) {
        this.kchKod = kchKod;
    }

    public String getKchNazwa() {
        return kchNazwa;
    }

    public void setKchNazwa(String kchNazwa) {
        this.kchNazwa = kchNazwa;
    }

    public String getKchKonto() {
        return kchKonto;
    }

    public void setKchKonto(String kchKonto) {
        this.kchKonto = kchKonto;
    }

    public Character getKchChar1() {
        return kchChar1;
    }

    public void setKchChar1(Character kchChar1) {
        this.kchChar1 = kchChar1;
    }

    public Character getKchChar2() {
        return kchChar2;
    }

    public void setKchChar2(Character kchChar2) {
        this.kchChar2 = kchChar2;
    }

    public Character getKchChar3() {
        return kchChar3;
    }

    public void setKchChar3(Character kchChar3) {
        this.kchChar3 = kchChar3;
    }

    public Character getKchChar4() {
        return kchChar4;
    }

    public void setKchChar4(Character kchChar4) {
        this.kchChar4 = kchChar4;
    }

    public Integer getKchInt1() {
        return kchInt1;
    }

    public void setKchInt1(Integer kchInt1) {
        this.kchInt1 = kchInt1;
    }

    public Integer getKchInt2() {
        return kchInt2;
    }

    public void setKchInt2(Integer kchInt2) {
        this.kchInt2 = kchInt2;
    }

    public BigDecimal getKchNum1() {
        return kchNum1;
    }

    public void setKchNum1(BigDecimal kchNum1) {
        this.kchNum1 = kchNum1;
    }

    public BigDecimal getKchNum2() {
        return kchNum2;
    }

    public void setKchNum2(BigDecimal kchNum2) {
        this.kchNum2 = kchNum2;
    }

    public Date getKchData1() {
        return kchData1;
    }

    public void setKchData1(Date kchData1) {
        this.kchData1 = kchData1;
    }

    public Date getKchData2() {
        return kchData2;
    }

    public void setKchData2(Date kchData2) {
        this.kchData2 = kchData2;
    }

    public String getKchVchar1() {
        return kchVchar1;
    }

    public void setKchVchar1(String kchVchar1) {
        this.kchVchar1 = kchVchar1;
    }

    public String getKchVchar2() {
        return kchVchar2;
    }

    public void setKchVchar2(String kchVchar2) {
        this.kchVchar2 = kchVchar2;
    }

    public String getKchOpis() {
        return kchOpis;
    }

    public void setKchOpis(String kchOpis) {
        this.kchOpis = kchOpis;
    }

    public Character getKchRodzaj() {
        return kchRodzaj;
    }

    public void setKchRodzaj(Character kchRodzaj) {
        this.kchRodzaj = kchRodzaj;
    }

    public Bank getKchBanSerial() {
        return kchBanSerial;
    }

    public void setKchBanSerial(Bank kchBanSerial) {
        this.kchBanSerial = kchBanSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kchSerial != null ? kchSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KasaCh)) {
            return false;
        }
        KasaCh other = (KasaCh) object;
        if ((this.kchSerial == null && other.kchSerial != null) || (this.kchSerial != null && !this.kchSerial.equals(other.kchSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.KasaCh[ kchSerial=" + kchSerial + " ]";
    }
    
}
