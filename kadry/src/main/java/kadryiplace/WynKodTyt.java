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
@Table(name = "wyn_kod_tyt", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WynKodTyt.findAll", query = "SELECT w FROM WynKodTyt w"),
    @NamedQuery(name = "WynKodTyt.findByWktSerial", query = "SELECT w FROM WynKodTyt w WHERE w.wktSerial = :wktSerial"),
    @NamedQuery(name = "WynKodTyt.findByWktKod", query = "SELECT w FROM WynKodTyt w WHERE w.wktKod = :wktKod"),
    @NamedQuery(name = "WynKodTyt.findByWktOpis", query = "SELECT w FROM WynKodTyt w WHERE w.wktOpis = :wktOpis"),
    @NamedQuery(name = "WynKodTyt.findByWktUmZlec", query = "SELECT w FROM WynKodTyt w WHERE w.wktUmZlec = :wktUmZlec"),
    @NamedQuery(name = "WynKodTyt.findByWktSystem", query = "SELECT w FROM WynKodTyt w WHERE w.wktSystem = :wktSystem"),
    @NamedQuery(name = "WynKodTyt.findByWktDod1", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod1 = :wktDod1"),
    @NamedQuery(name = "WynKodTyt.findByWktDod2", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod2 = :wktDod2"),
    @NamedQuery(name = "WynKodTyt.findByWktDod3", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod3 = :wktDod3"),
    @NamedQuery(name = "WynKodTyt.findByWktDod4", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod4 = :wktDod4"),
    @NamedQuery(name = "WynKodTyt.findByWktDod5", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod5 = :wktDod5"),
    @NamedQuery(name = "WynKodTyt.findByWktDod6", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod6 = :wktDod6"),
    @NamedQuery(name = "WynKodTyt.findByWktDod7", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod7 = :wktDod7"),
    @NamedQuery(name = "WynKodTyt.findByWktDod8", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod8 = :wktDod8"),
    @NamedQuery(name = "WynKodTyt.findByWktData1", query = "SELECT w FROM WynKodTyt w WHERE w.wktData1 = :wktData1"),
    @NamedQuery(name = "WynKodTyt.findByWktData2", query = "SELECT w FROM WynKodTyt w WHERE w.wktData2 = :wktData2"),
    @NamedQuery(name = "WynKodTyt.findByWktNum1", query = "SELECT w FROM WynKodTyt w WHERE w.wktNum1 = :wktNum1"),
    @NamedQuery(name = "WynKodTyt.findByWktInt1", query = "SELECT w FROM WynKodTyt w WHERE w.wktInt1 = :wktInt1"),
    @NamedQuery(name = "WynKodTyt.findByWktInt2", query = "SELECT w FROM WynKodTyt w WHERE w.wktInt2 = :wktInt2"),
    @NamedQuery(name = "WynKodTyt.findByWktInt3", query = "SELECT w FROM WynKodTyt w WHERE w.wktInt3 = :wktInt3"),
    @NamedQuery(name = "WynKodTyt.findByWktInt4", query = "SELECT w FROM WynKodTyt w WHERE w.wktInt4 = :wktInt4"),
    @NamedQuery(name = "WynKodTyt.findByWktDod9", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod9 = :wktDod9"),
    @NamedQuery(name = "WynKodTyt.findByWktDod10", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod10 = :wktDod10"),
    @NamedQuery(name = "WynKodTyt.findByWktDod11", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod11 = :wktDod11"),
    @NamedQuery(name = "WynKodTyt.findByWktDod12", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod12 = :wktDod12"),
    @NamedQuery(name = "WynKodTyt.findByWktDod13", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod13 = :wktDod13"),
    @NamedQuery(name = "WynKodTyt.findByWktDod14", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod14 = :wktDod14"),
    @NamedQuery(name = "WynKodTyt.findByWktDod15", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod15 = :wktDod15"),
    @NamedQuery(name = "WynKodTyt.findByWktDod16", query = "SELECT w FROM WynKodTyt w WHERE w.wktDod16 = :wktDod16")})
public class WynKodTyt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wkt_serial", nullable = false)
    private Integer wktSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "wkt_kod", nullable = false, length = 8)
    private String wktKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "wkt_opis", nullable = false, length = 300)
    private String wktOpis;
    @Column(name = "wkt_um_zlec")
    private Character wktUmZlec;
    @Column(name = "wkt_system")
    private Character wktSystem;
    @Column(name = "wkt_dod_1")
    private Character wktDod1;
    @Column(name = "wkt_dod_2")
    private Character wktDod2;
    @Column(name = "wkt_dod_3")
    private Character wktDod3;
    @Column(name = "wkt_dod_4")
    private Character wktDod4;
    @Column(name = "wkt_dod_5")
    private Character wktDod5;
    @Column(name = "wkt_dod_6")
    private Character wktDod6;
    @Column(name = "wkt_dod_7")
    private Character wktDod7;
    @Column(name = "wkt_dod_8")
    private Character wktDod8;
    @Column(name = "wkt_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wktData1;
    @Column(name = "wkt_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wktData2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wkt_num_1", precision = 17, scale = 6)
    private BigDecimal wktNum1;
    @Column(name = "wkt_int_1")
    private Integer wktInt1;
    @Column(name = "wkt_int_2")
    private Integer wktInt2;
    @Column(name = "wkt_int_3")
    private Integer wktInt3;
    @Column(name = "wkt_int_4")
    private Integer wktInt4;
    @Column(name = "wkt_dod_9")
    private Character wktDod9;
    @Column(name = "wkt_dod_10")
    private Character wktDod10;
    @Column(name = "wkt_dod_11")
    private Character wktDod11;
    @Column(name = "wkt_dod_12")
    private Character wktDod12;
    @Column(name = "wkt_dod_13")
    private Character wktDod13;
    @Column(name = "wkt_dod_14")
    private Character wktDod14;
    @Column(name = "wkt_dod_15")
    private Character wktDod15;
    @Column(name = "wkt_dod_16")
    private Character wktDod16;
    @OneToMany(mappedBy = "ozlWktSerial")
    private List<OsobaZlec> osobaZlecList;
    @OneToMany(mappedBy = "osdWktSerial")
    private List<OsobaDet> osobaDetList;

    public WynKodTyt() {
    }

    public WynKodTyt(Integer wktSerial) {
        this.wktSerial = wktSerial;
    }

    public WynKodTyt(Integer wktSerial, String wktKod, String wktOpis) {
        this.wktSerial = wktSerial;
        this.wktKod = wktKod;
        this.wktOpis = wktOpis;
    }

    public Integer getWktSerial() {
        return wktSerial;
    }

    public void setWktSerial(Integer wktSerial) {
        this.wktSerial = wktSerial;
    }

    public String getWktKod() {
        return wktKod;
    }

    public void setWktKod(String wktKod) {
        this.wktKod = wktKod;
    }

    public String getWktOpis() {
        return wktOpis;
    }

    public void setWktOpis(String wktOpis) {
        this.wktOpis = wktOpis;
    }

    public Character getWktUmZlec() {
        return wktUmZlec;
    }

    public void setWktUmZlec(Character wktUmZlec) {
        this.wktUmZlec = wktUmZlec;
    }

    public Character getWktSystem() {
        return wktSystem;
    }

    public void setWktSystem(Character wktSystem) {
        this.wktSystem = wktSystem;
    }

    public Character getWktDod1() {
        return wktDod1;
    }

    public void setWktDod1(Character wktDod1) {
        this.wktDod1 = wktDod1;
    }

    public Character getWktDod2() {
        return wktDod2;
    }

    public void setWktDod2(Character wktDod2) {
        this.wktDod2 = wktDod2;
    }

    public Character getWktDod3() {
        return wktDod3;
    }

    public void setWktDod3(Character wktDod3) {
        this.wktDod3 = wktDod3;
    }

    public Character getWktDod4() {
        return wktDod4;
    }

    public void setWktDod4(Character wktDod4) {
        this.wktDod4 = wktDod4;
    }

    public Character getWktDod5() {
        return wktDod5;
    }

    public void setWktDod5(Character wktDod5) {
        this.wktDod5 = wktDod5;
    }

    public Character getWktDod6() {
        return wktDod6;
    }

    public void setWktDod6(Character wktDod6) {
        this.wktDod6 = wktDod6;
    }

    public Character getWktDod7() {
        return wktDod7;
    }

    public void setWktDod7(Character wktDod7) {
        this.wktDod7 = wktDod7;
    }

    public Character getWktDod8() {
        return wktDod8;
    }

    public void setWktDod8(Character wktDod8) {
        this.wktDod8 = wktDod8;
    }

    public Date getWktData1() {
        return wktData1;
    }

    public void setWktData1(Date wktData1) {
        this.wktData1 = wktData1;
    }

    public Date getWktData2() {
        return wktData2;
    }

    public void setWktData2(Date wktData2) {
        this.wktData2 = wktData2;
    }

    public BigDecimal getWktNum1() {
        return wktNum1;
    }

    public void setWktNum1(BigDecimal wktNum1) {
        this.wktNum1 = wktNum1;
    }

    public Integer getWktInt1() {
        return wktInt1;
    }

    public void setWktInt1(Integer wktInt1) {
        this.wktInt1 = wktInt1;
    }

    public Integer getWktInt2() {
        return wktInt2;
    }

    public void setWktInt2(Integer wktInt2) {
        this.wktInt2 = wktInt2;
    }

    public Integer getWktInt3() {
        return wktInt3;
    }

    public void setWktInt3(Integer wktInt3) {
        this.wktInt3 = wktInt3;
    }

    public Integer getWktInt4() {
        return wktInt4;
    }

    public void setWktInt4(Integer wktInt4) {
        this.wktInt4 = wktInt4;
    }

    public Character getWktDod9() {
        return wktDod9;
    }

    public void setWktDod9(Character wktDod9) {
        this.wktDod9 = wktDod9;
    }

    public Character getWktDod10() {
        return wktDod10;
    }

    public void setWktDod10(Character wktDod10) {
        this.wktDod10 = wktDod10;
    }

    public Character getWktDod11() {
        return wktDod11;
    }

    public void setWktDod11(Character wktDod11) {
        this.wktDod11 = wktDod11;
    }

    public Character getWktDod12() {
        return wktDod12;
    }

    public void setWktDod12(Character wktDod12) {
        this.wktDod12 = wktDod12;
    }

    public Character getWktDod13() {
        return wktDod13;
    }

    public void setWktDod13(Character wktDod13) {
        this.wktDod13 = wktDod13;
    }

    public Character getWktDod14() {
        return wktDod14;
    }

    public void setWktDod14(Character wktDod14) {
        this.wktDod14 = wktDod14;
    }

    public Character getWktDod15() {
        return wktDod15;
    }

    public void setWktDod15(Character wktDod15) {
        this.wktDod15 = wktDod15;
    }

    public Character getWktDod16() {
        return wktDod16;
    }

    public void setWktDod16(Character wktDod16) {
        this.wktDod16 = wktDod16;
    }

    @XmlTransient
    public List<OsobaZlec> getOsobaZlecList() {
        return osobaZlecList;
    }

    public void setOsobaZlecList(List<OsobaZlec> osobaZlecList) {
        this.osobaZlecList = osobaZlecList;
    }

    @XmlTransient
    public List<OsobaDet> getOsobaDetList() {
        return osobaDetList;
    }

    public void setOsobaDetList(List<OsobaDet> osobaDetList) {
        this.osobaDetList = osobaDetList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wktSerial != null ? wktSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WynKodTyt)) {
            return false;
        }
        WynKodTyt other = (WynKodTyt) object;
        if ((this.wktSerial == null && other.wktSerial != null) || (this.wktSerial != null && !this.wktSerial.equals(other.wktSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.WynKodTyt[ wktSerial=" + wktSerial + " ]";
    }
    
}
