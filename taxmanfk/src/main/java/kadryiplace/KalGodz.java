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
@Table(name = "kal_godz", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KalGodz.findAll", query = "SELECT k FROM KalGodz k"),
    @NamedQuery(name = "KalGodz.findByKagSerial", query = "SELECT k FROM KalGodz k WHERE k.kagSerial = :kagSerial"),
    @NamedQuery(name = "KalGodz.findByKagTyp", query = "SELECT k FROM KalGodz k WHERE k.kagTyp = :kagTyp"),
    @NamedQuery(name = "KalGodz.findByKagData", query = "SELECT k FROM KalGodz k WHERE k.kagData = :kagData"),
    @NamedQuery(name = "KalGodz.findByKagLiczba", query = "SELECT k FROM KalGodz k WHERE k.kagLiczba = :kagLiczba"),
    @NamedQuery(name = "KalGodz.findByKagGodzOd", query = "SELECT k FROM KalGodz k WHERE k.kagGodzOd = :kagGodzOd"),
    @NamedQuery(name = "KalGodz.findByKagGodzDo", query = "SELECT k FROM KalGodz k WHERE k.kagGodzDo = :kagGodzDo"),
    @NamedQuery(name = "KalGodz.findByKagData1", query = "SELECT k FROM KalGodz k WHERE k.kagData1 = :kagData1"),
    @NamedQuery(name = "KalGodz.findByKagData2", query = "SELECT k FROM KalGodz k WHERE k.kagData2 = :kagData2"),
    @NamedQuery(name = "KalGodz.findByKagNum2", query = "SELECT k FROM KalGodz k WHERE k.kagNum2 = :kagNum2"),
    @NamedQuery(name = "KalGodz.findByKagInt1", query = "SELECT k FROM KalGodz k WHERE k.kagInt1 = :kagInt1"),
    @NamedQuery(name = "KalGodz.findByKagInt2", query = "SELECT k FROM KalGodz k WHERE k.kagInt2 = :kagInt2"),
    @NamedQuery(name = "KalGodz.findByKagChar1", query = "SELECT k FROM KalGodz k WHERE k.kagChar1 = :kagChar1"),
    @NamedQuery(name = "KalGodz.findByKagChar2", query = "SELECT k FROM KalGodz k WHERE k.kagChar2 = :kagChar2"),
    @NamedQuery(name = "KalGodz.findByKagChar3", query = "SELECT k FROM KalGodz k WHERE k.kagChar3 = :kagChar3"),
    @NamedQuery(name = "KalGodz.findByKagChar4", query = "SELECT k FROM KalGodz k WHERE k.kagChar4 = :kagChar4"),
    @NamedQuery(name = "KalGodz.findByKagVchar1", query = "SELECT k FROM KalGodz k WHERE k.kagVchar1 = :kagVchar1")})
public class KalGodz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "kag_serial", nullable = false)
    private Integer kagSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kag_typ", nullable = false)
    private Character kagTyp;
    @Column(name = "kag_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kagData;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kag_liczba", precision = 17, scale = 6)
    private BigDecimal kagLiczba;
    @Column(name = "kag_godz_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kagGodzOd;
    @Column(name = "kag_godz_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kagGodzDo;
    @Column(name = "kag_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kagData1;
    @Column(name = "kag_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kagData2;
    @Column(name = "kag_num_2", precision = 17, scale = 6)
    private BigDecimal kagNum2;
    @Column(name = "kag_int_1")
    private Integer kagInt1;
    @Column(name = "kag_int_2")
    private Integer kagInt2;
    @Column(name = "kag_char_1")
    private Character kagChar1;
    @Column(name = "kag_char_2")
    private Character kagChar2;
    @Column(name = "kag_char_3")
    private Character kagChar3;
    @Column(name = "kag_char_4")
    private Character kagChar4;
    @Size(max = 16)
    @Column(name = "kag_vchar_1", length = 16)
    private String kagVchar1;
    @JoinColumn(name = "kag_kld_serial", referencedColumnName = "kld_serial")
    @ManyToOne
    private Kalendarz kagKldSerial;
    @JoinColumn(name = "kag_okr_serial", referencedColumnName = "okr_serial")
    @ManyToOne
    private Okres kagOkrSerial;

    public KalGodz() {
    }

    public KalGodz(Integer kagSerial) {
        this.kagSerial = kagSerial;
    }

    public KalGodz(Integer kagSerial, Character kagTyp) {
        this.kagSerial = kagSerial;
        this.kagTyp = kagTyp;
    }

    public Integer getKagSerial() {
        return kagSerial;
    }

    public void setKagSerial(Integer kagSerial) {
        this.kagSerial = kagSerial;
    }

    public Character getKagTyp() {
        return kagTyp;
    }

    public void setKagTyp(Character kagTyp) {
        this.kagTyp = kagTyp;
    }

    public Date getKagData() {
        return kagData;
    }

    public void setKagData(Date kagData) {
        this.kagData = kagData;
    }

    public BigDecimal getKagLiczba() {
        return kagLiczba;
    }

    public void setKagLiczba(BigDecimal kagLiczba) {
        this.kagLiczba = kagLiczba;
    }

    public Date getKagGodzOd() {
        return kagGodzOd;
    }

    public void setKagGodzOd(Date kagGodzOd) {
        this.kagGodzOd = kagGodzOd;
    }

    public Date getKagGodzDo() {
        return kagGodzDo;
    }

    public void setKagGodzDo(Date kagGodzDo) {
        this.kagGodzDo = kagGodzDo;
    }

    public Date getKagData1() {
        return kagData1;
    }

    public void setKagData1(Date kagData1) {
        this.kagData1 = kagData1;
    }

    public Date getKagData2() {
        return kagData2;
    }

    public void setKagData2(Date kagData2) {
        this.kagData2 = kagData2;
    }

    public BigDecimal getKagNum2() {
        return kagNum2;
    }

    public void setKagNum2(BigDecimal kagNum2) {
        this.kagNum2 = kagNum2;
    }

    public Integer getKagInt1() {
        return kagInt1;
    }

    public void setKagInt1(Integer kagInt1) {
        this.kagInt1 = kagInt1;
    }

    public Integer getKagInt2() {
        return kagInt2;
    }

    public void setKagInt2(Integer kagInt2) {
        this.kagInt2 = kagInt2;
    }

    public Character getKagChar1() {
        return kagChar1;
    }

    public void setKagChar1(Character kagChar1) {
        this.kagChar1 = kagChar1;
    }

    public Character getKagChar2() {
        return kagChar2;
    }

    public void setKagChar2(Character kagChar2) {
        this.kagChar2 = kagChar2;
    }

    public Character getKagChar3() {
        return kagChar3;
    }

    public void setKagChar3(Character kagChar3) {
        this.kagChar3 = kagChar3;
    }

    public Character getKagChar4() {
        return kagChar4;
    }

    public void setKagChar4(Character kagChar4) {
        this.kagChar4 = kagChar4;
    }

    public String getKagVchar1() {
        return kagVchar1;
    }

    public void setKagVchar1(String kagVchar1) {
        this.kagVchar1 = kagVchar1;
    }

    public Kalendarz getKagKldSerial() {
        return kagKldSerial;
    }

    public void setKagKldSerial(Kalendarz kagKldSerial) {
        this.kagKldSerial = kagKldSerial;
    }

    public Okres getKagOkrSerial() {
        return kagOkrSerial;
    }

    public void setKagOkrSerial(Okres kagOkrSerial) {
        this.kagOkrSerial = kagOkrSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kagSerial != null ? kagSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KalGodz)) {
            return false;
        }
        KalGodz other = (KalGodz) object;
        if ((this.kagSerial == null && other.kagSerial != null) || (this.kagSerial != null && !this.kagSerial.equals(other.kagSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.KalGodz[ kagSerial=" + kagSerial + " ]";
    }
    
}
