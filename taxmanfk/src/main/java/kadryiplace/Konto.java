/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
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
@Table(name = "konto", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Konto.findAll", query = "SELECT k FROM Konto k"),
    @NamedQuery(name = "Konto.findByKntSerial", query = "SELECT k FROM Konto k WHERE k.kntSerial = :kntSerial"),
    @NamedQuery(name = "Konto.findByKntNumer", query = "SELECT k FROM Konto k WHERE k.kntNumer = :kntNumer"),
    @NamedQuery(name = "Konto.findByKntInstytTyp", query = "SELECT k FROM Konto k WHERE k.kntInstytTyp = :kntInstytTyp"),
    @NamedQuery(name = "Konto.findByKntInstytSerial", query = "SELECT k FROM Konto k WHERE k.kntInstytSerial = :kntInstytSerial"),
    @NamedQuery(name = "Konto.findByKntTyp", query = "SELECT k FROM Konto k WHERE k.kntTyp = :kntTyp"),
    @NamedQuery(name = "Konto.findByKntDataOd", query = "SELECT k FROM Konto k WHERE k.kntDataOd = :kntDataOd"),
    @NamedQuery(name = "Konto.findByKntDataDo", query = "SELECT k FROM Konto k WHERE k.kntDataDo = :kntDataDo"),
    @NamedQuery(name = "Konto.findByKntStatus", query = "SELECT k FROM Konto k WHERE k.kntStatus = :kntStatus"),
    @NamedQuery(name = "Konto.findByKntChar1", query = "SELECT k FROM Konto k WHERE k.kntChar1 = :kntChar1"),
    @NamedQuery(name = "Konto.findByKntChar2", query = "SELECT k FROM Konto k WHERE k.kntChar2 = :kntChar2"),
    @NamedQuery(name = "Konto.findByKntVchar1", query = "SELECT k FROM Konto k WHERE k.kntVchar1 = :kntVchar1")})
public class Konto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "knt_serial", nullable = false)
    private Integer kntSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "knt_numer", nullable = false, length = 64)
    private String kntNumer;
    @Column(name = "knt_instyt_typ")
    private Character kntInstytTyp;
    @Column(name = "knt_instyt_serial")
    private Integer kntInstytSerial;
    @Column(name = "knt_typ")
    private Character kntTyp;
    @Column(name = "knt_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kntDataOd;
    @Column(name = "knt_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kntDataDo;
    @Column(name = "knt_status")
    private Character kntStatus;
    @Column(name = "knt_char_1")
    private Character kntChar1;
    @Column(name = "knt_char_2")
    private Character kntChar2;
    @Size(max = 64)
    @Column(name = "knt_vchar_1", length = 64)
    private String kntVchar1;
    @JoinColumn(name = "knt_ban_serial", referencedColumnName = "ban_serial", nullable = false)
    @ManyToOne(optional = false)
    private Bank kntBanSerial;
    @JoinColumn(name = "knt_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma kntFirSerial;
    @JoinColumn(name = "knt_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba kntOsoSerial;

    public Konto() {
    }

    public Konto(Integer kntSerial) {
        this.kntSerial = kntSerial;
    }

    public Konto(Integer kntSerial, String kntNumer) {
        this.kntSerial = kntSerial;
        this.kntNumer = kntNumer;
    }

    public Integer getKntSerial() {
        return kntSerial;
    }

    public void setKntSerial(Integer kntSerial) {
        this.kntSerial = kntSerial;
    }

    public String getKntNumer() {
        return kntNumer;
    }

    public void setKntNumer(String kntNumer) {
        this.kntNumer = kntNumer;
    }

    public Character getKntInstytTyp() {
        return kntInstytTyp;
    }

    public void setKntInstytTyp(Character kntInstytTyp) {
        this.kntInstytTyp = kntInstytTyp;
    }

    public Integer getKntInstytSerial() {
        return kntInstytSerial;
    }

    public void setKntInstytSerial(Integer kntInstytSerial) {
        this.kntInstytSerial = kntInstytSerial;
    }

    public Character getKntTyp() {
        return kntTyp;
    }

    public void setKntTyp(Character kntTyp) {
        this.kntTyp = kntTyp;
    }

    public Date getKntDataOd() {
        return kntDataOd;
    }

    public void setKntDataOd(Date kntDataOd) {
        this.kntDataOd = kntDataOd;
    }

    public Date getKntDataDo() {
        return kntDataDo;
    }

    public void setKntDataDo(Date kntDataDo) {
        this.kntDataDo = kntDataDo;
    }

    public Character getKntStatus() {
        return kntStatus;
    }

    public void setKntStatus(Character kntStatus) {
        this.kntStatus = kntStatus;
    }

    public Character getKntChar1() {
        return kntChar1;
    }

    public void setKntChar1(Character kntChar1) {
        this.kntChar1 = kntChar1;
    }

    public Character getKntChar2() {
        return kntChar2;
    }

    public void setKntChar2(Character kntChar2) {
        this.kntChar2 = kntChar2;
    }

    public String getKntVchar1() {
        return kntVchar1;
    }

    public void setKntVchar1(String kntVchar1) {
        this.kntVchar1 = kntVchar1;
    }

    public Bank getKntBanSerial() {
        return kntBanSerial;
    }

    public void setKntBanSerial(Bank kntBanSerial) {
        this.kntBanSerial = kntBanSerial;
    }

    public Firma getKntFirSerial() {
        return kntFirSerial;
    }

    public void setKntFirSerial(Firma kntFirSerial) {
        this.kntFirSerial = kntFirSerial;
    }

    public Osoba getKntOsoSerial() {
        return kntOsoSerial;
    }

    public void setKntOsoSerial(Osoba kntOsoSerial) {
        this.kntOsoSerial = kntOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kntSerial != null ? kntSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Konto)) {
            return false;
        }
        Konto other = (Konto) object;
        if ((this.kntSerial == null && other.kntSerial != null) || (this.kntSerial != null && !this.kntSerial.equals(other.kntSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Konto[ kntSerial=" + kntSerial + " ]";
    }
    
}
