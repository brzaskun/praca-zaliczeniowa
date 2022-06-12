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
@Table(name = "wymiar_hist", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WymiarHist.findAll", query = "SELECT w FROM WymiarHist w"),
    @NamedQuery(name = "WymiarHist.findByWehSerial", query = "SELECT w FROM WymiarHist w WHERE w.wehSerial = :wehSerial"),
    @NamedQuery(name = "WymiarHist.findByWehDataOd", query = "SELECT w FROM WymiarHist w WHERE w.wehDataOd = :wehDataOd"),
    @NamedQuery(name = "WymiarHist.findByWehDataDo", query = "SELECT w FROM WymiarHist w WHERE w.wehDataDo = :wehDataDo"),
    @NamedQuery(name = "WymiarHist.findByWehEtat1", query = "SELECT w FROM WymiarHist w WHERE w.wehEtat1 = :wehEtat1"),
    @NamedQuery(name = "WymiarHist.findByWehEtat2", query = "SELECT w FROM WymiarHist w WHERE w.wehEtat2 = :wehEtat2"),
    @NamedQuery(name = "WymiarHist.findByWehStatus", query = "SELECT w FROM WymiarHist w WHERE w.wehStatus = :wehStatus"),
    @NamedQuery(name = "WymiarHist.findByWehEtatD", query = "SELECT w FROM WymiarHist w WHERE w.wehEtatD = :wehEtatD"),
    @NamedQuery(name = "WymiarHist.findByWehTyp", query = "SELECT w FROM WymiarHist w WHERE w.wehTyp = :wehTyp"),
    @NamedQuery(name = "WymiarHist.findByWehChar1", query = "SELECT w FROM WymiarHist w WHERE w.wehChar1 = :wehChar1"),
    @NamedQuery(name = "WymiarHist.findByWehChar2", query = "SELECT w FROM WymiarHist w WHERE w.wehChar2 = :wehChar2"),
    @NamedQuery(name = "WymiarHist.findByWehVchar1", query = "SELECT w FROM WymiarHist w WHERE w.wehVchar1 = :wehVchar1"),
    @NamedQuery(name = "WymiarHist.findByWehVchar2", query = "SELECT w FROM WymiarHist w WHERE w.wehVchar2 = :wehVchar2"),
    @NamedQuery(name = "WymiarHist.findByWehChar3", query = "SELECT w FROM WymiarHist w WHERE w.wehChar3 = :wehChar3"),
    @NamedQuery(name = "WymiarHist.findByWehChar4", query = "SELECT w FROM WymiarHist w WHERE w.wehChar4 = :wehChar4"),
    @NamedQuery(name = "WymiarHist.findByWehNum1", query = "SELECT w FROM WymiarHist w WHERE w.wehNum1 = :wehNum1")})
public class WymiarHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "weh_serial", nullable = false)
    private Integer wehSerial;
    @Column(name = "weh_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wehDataOd;
    @Column(name = "weh_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wehDataDo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weh_etat_1", nullable = false)
    private short wehEtat1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weh_etat_2", nullable = false)
    private short wehEtat2;
    @Column(name = "weh_status")
    private Character wehStatus;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weh_etat_d", precision = 17, scale = 6)
    private BigDecimal wehEtatD;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weh_typ", nullable = false)
    private Character wehTyp;
    @Column(name = "weh_char_1")
    private Character wehChar1;
    @Column(name = "weh_char_2")
    private Character wehChar2;
    @Size(max = 64)
    @Column(name = "weh_vchar_1", length = 64)
    private String wehVchar1;
    @Size(max = 64)
    @Column(name = "weh_vchar_2", length = 64)
    private String wehVchar2;
    @Column(name = "weh_char_3")
    private Character wehChar3;
    @Column(name = "weh_char_4")
    private Character wehChar4;
    @Column(name = "weh_num_1", precision = 17, scale = 6)
    private BigDecimal wehNum1;
    @JoinColumn(name = "weh_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba wehOsoSerial;

    public WymiarHist() {
    }

    public WymiarHist(Integer wehSerial) {
        this.wehSerial = wehSerial;
    }

    public WymiarHist(Integer wehSerial, short wehEtat1, short wehEtat2, Character wehTyp) {
        this.wehSerial = wehSerial;
        this.wehEtat1 = wehEtat1;
        this.wehEtat2 = wehEtat2;
        this.wehTyp = wehTyp;
    }

    public Integer getWehSerial() {
        return wehSerial;
    }

    public void setWehSerial(Integer wehSerial) {
        this.wehSerial = wehSerial;
    }

    public Date getWehDataOd() {
        return wehDataOd;
    }

    public void setWehDataOd(Date wehDataOd) {
        this.wehDataOd = wehDataOd;
    }

    public Date getWehDataDo() {
        return wehDataDo;
    }

    public void setWehDataDo(Date wehDataDo) {
        this.wehDataDo = wehDataDo;
    }

    public short getWehEtat1() {
        return wehEtat1;
    }

    public void setWehEtat1(short wehEtat1) {
        this.wehEtat1 = wehEtat1;
    }

    public short getWehEtat2() {
        return wehEtat2;
    }

    public void setWehEtat2(short wehEtat2) {
        this.wehEtat2 = wehEtat2;
    }

    public Character getWehStatus() {
        return wehStatus;
    }

    public void setWehStatus(Character wehStatus) {
        this.wehStatus = wehStatus;
    }

    public BigDecimal getWehEtatD() {
        return wehEtatD;
    }

    public void setWehEtatD(BigDecimal wehEtatD) {
        this.wehEtatD = wehEtatD;
    }

    public Character getWehTyp() {
        return wehTyp;
    }

    public void setWehTyp(Character wehTyp) {
        this.wehTyp = wehTyp;
    }

    public Character getWehChar1() {
        return wehChar1;
    }

    public void setWehChar1(Character wehChar1) {
        this.wehChar1 = wehChar1;
    }

    public Character getWehChar2() {
        return wehChar2;
    }

    public void setWehChar2(Character wehChar2) {
        this.wehChar2 = wehChar2;
    }

    public String getWehVchar1() {
        return wehVchar1;
    }

    public void setWehVchar1(String wehVchar1) {
        this.wehVchar1 = wehVchar1;
    }

    public String getWehVchar2() {
        return wehVchar2;
    }

    public void setWehVchar2(String wehVchar2) {
        this.wehVchar2 = wehVchar2;
    }

    public Character getWehChar3() {
        return wehChar3;
    }

    public void setWehChar3(Character wehChar3) {
        this.wehChar3 = wehChar3;
    }

    public Character getWehChar4() {
        return wehChar4;
    }

    public void setWehChar4(Character wehChar4) {
        this.wehChar4 = wehChar4;
    }

    public BigDecimal getWehNum1() {
        return wehNum1;
    }

    public void setWehNum1(BigDecimal wehNum1) {
        this.wehNum1 = wehNum1;
    }

    public Osoba getWehOsoSerial() {
        return wehOsoSerial;
    }

    public void setWehOsoSerial(Osoba wehOsoSerial) {
        this.wehOsoSerial = wehOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wehSerial != null ? wehSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WymiarHist)) {
            return false;
        }
        WymiarHist other = (WymiarHist) object;
        if ((this.wehSerial == null && other.wehSerial != null) || (this.wehSerial != null && !this.wehSerial.equals(other.wehSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.WymiarHist[ wehSerial=" + wehSerial + " ]";
    }
    
}
