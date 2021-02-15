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
import javax.persistence.CascadeType;
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
@Table(name = "sc_kamp", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScKamp.findAll", query = "SELECT s FROM ScKamp s"),
    @NamedQuery(name = "ScKamp.findBySckSerial", query = "SELECT s FROM ScKamp s WHERE s.sckSerial = :sckSerial"),
    @NamedQuery(name = "ScKamp.findBySckTyp", query = "SELECT s FROM ScKamp s WHERE s.sckTyp = :sckTyp"),
    @NamedQuery(name = "ScKamp.findBySckOpis1", query = "SELECT s FROM ScKamp s WHERE s.sckOpis1 = :sckOpis1"),
    @NamedQuery(name = "ScKamp.findBySckOpis2", query = "SELECT s FROM ScKamp s WHERE s.sckOpis2 = :sckOpis2"),
    @NamedQuery(name = "ScKamp.findBySckOpis3", query = "SELECT s FROM ScKamp s WHERE s.sckOpis3 = :sckOpis3"),
    @NamedQuery(name = "ScKamp.findBySckOpis4", query = "SELECT s FROM ScKamp s WHERE s.sckOpis4 = :sckOpis4"),
    @NamedQuery(name = "ScKamp.findBySckOpis5", query = "SELECT s FROM ScKamp s WHERE s.sckOpis5 = :sckOpis5"),
    @NamedQuery(name = "ScKamp.findBySckOpis6", query = "SELECT s FROM ScKamp s WHERE s.sckOpis6 = :sckOpis6"),
    @NamedQuery(name = "ScKamp.findBySckOpis7", query = "SELECT s FROM ScKamp s WHERE s.sckOpis7 = :sckOpis7"),
    @NamedQuery(name = "ScKamp.findBySckOpis8", query = "SELECT s FROM ScKamp s WHERE s.sckOpis8 = :sckOpis8"),
    @NamedQuery(name = "ScKamp.findBySckOpis9", query = "SELECT s FROM ScKamp s WHERE s.sckOpis9 = :sckOpis9"),
    @NamedQuery(name = "ScKamp.findBySckOpis10", query = "SELECT s FROM ScKamp s WHERE s.sckOpis10 = :sckOpis10"),
    @NamedQuery(name = "ScKamp.findBySckDataOd", query = "SELECT s FROM ScKamp s WHERE s.sckDataOd = :sckDataOd"),
    @NamedQuery(name = "ScKamp.findBySckDataDo", query = "SELECT s FROM ScKamp s WHERE s.sckDataDo = :sckDataDo"),
    @NamedQuery(name = "ScKamp.findBySckChar1", query = "SELECT s FROM ScKamp s WHERE s.sckChar1 = :sckChar1"),
    @NamedQuery(name = "ScKamp.findBySckChar2", query = "SELECT s FROM ScKamp s WHERE s.sckChar2 = :sckChar2"),
    @NamedQuery(name = "ScKamp.findBySckChar3", query = "SELECT s FROM ScKamp s WHERE s.sckChar3 = :sckChar3"),
    @NamedQuery(name = "ScKamp.findBySckChar4", query = "SELECT s FROM ScKamp s WHERE s.sckChar4 = :sckChar4"),
    @NamedQuery(name = "ScKamp.findBySckChar5", query = "SELECT s FROM ScKamp s WHERE s.sckChar5 = :sckChar5"),
    @NamedQuery(name = "ScKamp.findBySckChar6", query = "SELECT s FROM ScKamp s WHERE s.sckChar6 = :sckChar6"),
    @NamedQuery(name = "ScKamp.findBySckChar7", query = "SELECT s FROM ScKamp s WHERE s.sckChar7 = :sckChar7"),
    @NamedQuery(name = "ScKamp.findBySckChar8", query = "SELECT s FROM ScKamp s WHERE s.sckChar8 = :sckChar8"),
    @NamedQuery(name = "ScKamp.findBySckChar9", query = "SELECT s FROM ScKamp s WHERE s.sckChar9 = :sckChar9"),
    @NamedQuery(name = "ScKamp.findBySckChar10", query = "SELECT s FROM ScKamp s WHERE s.sckChar10 = :sckChar10"),
    @NamedQuery(name = "ScKamp.findBySckNum1", query = "SELECT s FROM ScKamp s WHERE s.sckNum1 = :sckNum1"),
    @NamedQuery(name = "ScKamp.findBySckInt1", query = "SELECT s FROM ScKamp s WHERE s.sckInt1 = :sckInt1"),
    @NamedQuery(name = "ScKamp.findBySckData1", query = "SELECT s FROM ScKamp s WHERE s.sckData1 = :sckData1")})
public class ScKamp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sck_serial", nullable = false)
    private Integer sckSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sck_typ", nullable = false)
    private Character sckTyp;
    @Size(max = 254)
    @Column(name = "sck_opis_1", length = 254)
    private String sckOpis1;
    @Size(max = 254)
    @Column(name = "sck_opis_2", length = 254)
    private String sckOpis2;
    @Size(max = 254)
    @Column(name = "sck_opis_3", length = 254)
    private String sckOpis3;
    @Size(max = 254)
    @Column(name = "sck_opis_4", length = 254)
    private String sckOpis4;
    @Size(max = 254)
    @Column(name = "sck_opis_5", length = 254)
    private String sckOpis5;
    @Size(max = 254)
    @Column(name = "sck_opis_6", length = 254)
    private String sckOpis6;
    @Size(max = 254)
    @Column(name = "sck_opis_7", length = 254)
    private String sckOpis7;
    @Size(max = 254)
    @Column(name = "sck_opis_8", length = 254)
    private String sckOpis8;
    @Size(max = 254)
    @Column(name = "sck_opis_9", length = 254)
    private String sckOpis9;
    @Size(max = 254)
    @Column(name = "sck_opis_10", length = 254)
    private String sckOpis10;
    @Column(name = "sck_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sckDataOd;
    @Column(name = "sck_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sckDataDo;
    @Column(name = "sck_char_1")
    private Character sckChar1;
    @Column(name = "sck_char_2")
    private Character sckChar2;
    @Column(name = "sck_char_3")
    private Character sckChar3;
    @Column(name = "sck_char_4")
    private Character sckChar4;
    @Column(name = "sck_char_5")
    private Character sckChar5;
    @Column(name = "sck_char_6")
    private Character sckChar6;
    @Column(name = "sck_char_7")
    private Character sckChar7;
    @Column(name = "sck_char_8")
    private Character sckChar8;
    @Column(name = "sck_char_9")
    private Character sckChar9;
    @Column(name = "sck_char_10")
    private Character sckChar10;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "sck_num_1", precision = 17, scale = 6)
    private BigDecimal sckNum1;
    @Column(name = "sck_int_1")
    private Integer sckInt1;
    @Column(name = "sck_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sckData1;
    @JoinColumn(name = "sck_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma sckFirSerial;
    @OneToMany(mappedBy = "dscSckSerial")
    private List<DaneStatSc> daneStatScList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scpSckSerial")
    private List<ScPromo> scPromoList;

    public ScKamp() {
    }

    public ScKamp(Integer sckSerial) {
        this.sckSerial = sckSerial;
    }

    public ScKamp(Integer sckSerial, Character sckTyp) {
        this.sckSerial = sckSerial;
        this.sckTyp = sckTyp;
    }

    public Integer getSckSerial() {
        return sckSerial;
    }

    public void setSckSerial(Integer sckSerial) {
        this.sckSerial = sckSerial;
    }

    public Character getSckTyp() {
        return sckTyp;
    }

    public void setSckTyp(Character sckTyp) {
        this.sckTyp = sckTyp;
    }

    public String getSckOpis1() {
        return sckOpis1;
    }

    public void setSckOpis1(String sckOpis1) {
        this.sckOpis1 = sckOpis1;
    }

    public String getSckOpis2() {
        return sckOpis2;
    }

    public void setSckOpis2(String sckOpis2) {
        this.sckOpis2 = sckOpis2;
    }

    public String getSckOpis3() {
        return sckOpis3;
    }

    public void setSckOpis3(String sckOpis3) {
        this.sckOpis3 = sckOpis3;
    }

    public String getSckOpis4() {
        return sckOpis4;
    }

    public void setSckOpis4(String sckOpis4) {
        this.sckOpis4 = sckOpis4;
    }

    public String getSckOpis5() {
        return sckOpis5;
    }

    public void setSckOpis5(String sckOpis5) {
        this.sckOpis5 = sckOpis5;
    }

    public String getSckOpis6() {
        return sckOpis6;
    }

    public void setSckOpis6(String sckOpis6) {
        this.sckOpis6 = sckOpis6;
    }

    public String getSckOpis7() {
        return sckOpis7;
    }

    public void setSckOpis7(String sckOpis7) {
        this.sckOpis7 = sckOpis7;
    }

    public String getSckOpis8() {
        return sckOpis8;
    }

    public void setSckOpis8(String sckOpis8) {
        this.sckOpis8 = sckOpis8;
    }

    public String getSckOpis9() {
        return sckOpis9;
    }

    public void setSckOpis9(String sckOpis9) {
        this.sckOpis9 = sckOpis9;
    }

    public String getSckOpis10() {
        return sckOpis10;
    }

    public void setSckOpis10(String sckOpis10) {
        this.sckOpis10 = sckOpis10;
    }

    public Date getSckDataOd() {
        return sckDataOd;
    }

    public void setSckDataOd(Date sckDataOd) {
        this.sckDataOd = sckDataOd;
    }

    public Date getSckDataDo() {
        return sckDataDo;
    }

    public void setSckDataDo(Date sckDataDo) {
        this.sckDataDo = sckDataDo;
    }

    public Character getSckChar1() {
        return sckChar1;
    }

    public void setSckChar1(Character sckChar1) {
        this.sckChar1 = sckChar1;
    }

    public Character getSckChar2() {
        return sckChar2;
    }

    public void setSckChar2(Character sckChar2) {
        this.sckChar2 = sckChar2;
    }

    public Character getSckChar3() {
        return sckChar3;
    }

    public void setSckChar3(Character sckChar3) {
        this.sckChar3 = sckChar3;
    }

    public Character getSckChar4() {
        return sckChar4;
    }

    public void setSckChar4(Character sckChar4) {
        this.sckChar4 = sckChar4;
    }

    public Character getSckChar5() {
        return sckChar5;
    }

    public void setSckChar5(Character sckChar5) {
        this.sckChar5 = sckChar5;
    }

    public Character getSckChar6() {
        return sckChar6;
    }

    public void setSckChar6(Character sckChar6) {
        this.sckChar6 = sckChar6;
    }

    public Character getSckChar7() {
        return sckChar7;
    }

    public void setSckChar7(Character sckChar7) {
        this.sckChar7 = sckChar7;
    }

    public Character getSckChar8() {
        return sckChar8;
    }

    public void setSckChar8(Character sckChar8) {
        this.sckChar8 = sckChar8;
    }

    public Character getSckChar9() {
        return sckChar9;
    }

    public void setSckChar9(Character sckChar9) {
        this.sckChar9 = sckChar9;
    }

    public Character getSckChar10() {
        return sckChar10;
    }

    public void setSckChar10(Character sckChar10) {
        this.sckChar10 = sckChar10;
    }

    public BigDecimal getSckNum1() {
        return sckNum1;
    }

    public void setSckNum1(BigDecimal sckNum1) {
        this.sckNum1 = sckNum1;
    }

    public Integer getSckInt1() {
        return sckInt1;
    }

    public void setSckInt1(Integer sckInt1) {
        this.sckInt1 = sckInt1;
    }

    public Date getSckData1() {
        return sckData1;
    }

    public void setSckData1(Date sckData1) {
        this.sckData1 = sckData1;
    }

    public Firma getSckFirSerial() {
        return sckFirSerial;
    }

    public void setSckFirSerial(Firma sckFirSerial) {
        this.sckFirSerial = sckFirSerial;
    }

    @XmlTransient
    public List<DaneStatSc> getDaneStatScList() {
        return daneStatScList;
    }

    public void setDaneStatScList(List<DaneStatSc> daneStatScList) {
        this.daneStatScList = daneStatScList;
    }

    @XmlTransient
    public List<ScPromo> getScPromoList() {
        return scPromoList;
    }

    public void setScPromoList(List<ScPromo> scPromoList) {
        this.scPromoList = scPromoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sckSerial != null ? sckSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScKamp)) {
            return false;
        }
        ScKamp other = (ScKamp) object;
        if ((this.sckSerial == null && other.sckSerial != null) || (this.sckSerial != null && !this.sckSerial.equals(other.sckSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.ScKamp[ sckSerial=" + sckSerial + " ]";
    }
    
}
