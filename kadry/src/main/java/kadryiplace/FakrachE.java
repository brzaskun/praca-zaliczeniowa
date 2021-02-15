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
@Table(name = "fakrach_e", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FakrachE.findAll", query = "SELECT f FROM FakrachE f"),
    @NamedQuery(name = "FakrachE.findByFreSerial", query = "SELECT f FROM FakrachE f WHERE f.freSerial = :freSerial"),
    @NamedQuery(name = "FakrachE.findByFreSysSerial", query = "SELECT f FROM FakrachE f WHERE f.freSysSerial = :freSysSerial"),
    @NamedQuery(name = "FakrachE.findByFreChar1", query = "SELECT f FROM FakrachE f WHERE f.freChar1 = :freChar1"),
    @NamedQuery(name = "FakrachE.findByFreVchar1", query = "SELECT f FROM FakrachE f WHERE f.freVchar1 = :freVchar1"),
    @NamedQuery(name = "FakrachE.findByFreVchar2", query = "SELECT f FROM FakrachE f WHERE f.freVchar2 = :freVchar2"),
    @NamedQuery(name = "FakrachE.findByFreNumeric1", query = "SELECT f FROM FakrachE f WHERE f.freNumeric1 = :freNumeric1"),
    @NamedQuery(name = "FakrachE.findByFreDate1", query = "SELECT f FROM FakrachE f WHERE f.freDate1 = :freDate1"),
    @NamedQuery(name = "FakrachE.findByFreInt1", query = "SELECT f FROM FakrachE f WHERE f.freInt1 = :freInt1"),
    @NamedQuery(name = "FakrachE.findByFreChar2", query = "SELECT f FROM FakrachE f WHERE f.freChar2 = :freChar2")})
public class FakrachE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "fre_serial", nullable = false)
    private Integer freSerial;
    @Column(name = "fre_sys_serial")
    private Integer freSysSerial;
    @Column(name = "fre_char_1")
    private Character freChar1;
    @Size(max = 64)
    @Column(name = "fre_vchar_1", length = 64)
    private String freVchar1;
    @Size(max = 64)
    @Column(name = "fre_vchar_2", length = 64)
    private String freVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fre_numeric_1", precision = 17, scale = 6)
    private BigDecimal freNumeric1;
    @Column(name = "fre_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date freDate1;
    @Column(name = "fre_int_1")
    private Integer freInt1;
    @Column(name = "fre_char_2")
    private Character freChar2;
    @JoinColumn(name = "fre_fak_serial", referencedColumnName = "fak_serial")
    @ManyToOne
    private Fakrach freFakSerial;

    public FakrachE() {
    }

    public FakrachE(Integer freSerial) {
        this.freSerial = freSerial;
    }

    public Integer getFreSerial() {
        return freSerial;
    }

    public void setFreSerial(Integer freSerial) {
        this.freSerial = freSerial;
    }

    public Integer getFreSysSerial() {
        return freSysSerial;
    }

    public void setFreSysSerial(Integer freSysSerial) {
        this.freSysSerial = freSysSerial;
    }

    public Character getFreChar1() {
        return freChar1;
    }

    public void setFreChar1(Character freChar1) {
        this.freChar1 = freChar1;
    }

    public String getFreVchar1() {
        return freVchar1;
    }

    public void setFreVchar1(String freVchar1) {
        this.freVchar1 = freVchar1;
    }

    public String getFreVchar2() {
        return freVchar2;
    }

    public void setFreVchar2(String freVchar2) {
        this.freVchar2 = freVchar2;
    }

    public BigDecimal getFreNumeric1() {
        return freNumeric1;
    }

    public void setFreNumeric1(BigDecimal freNumeric1) {
        this.freNumeric1 = freNumeric1;
    }

    public Date getFreDate1() {
        return freDate1;
    }

    public void setFreDate1(Date freDate1) {
        this.freDate1 = freDate1;
    }

    public Integer getFreInt1() {
        return freInt1;
    }

    public void setFreInt1(Integer freInt1) {
        this.freInt1 = freInt1;
    }

    public Character getFreChar2() {
        return freChar2;
    }

    public void setFreChar2(Character freChar2) {
        this.freChar2 = freChar2;
    }

    public Fakrach getFreFakSerial() {
        return freFakSerial;
    }

    public void setFreFakSerial(Fakrach freFakSerial) {
        this.freFakSerial = freFakSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (freSerial != null ? freSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FakrachE)) {
            return false;
        }
        FakrachE other = (FakrachE) object;
        if ((this.freSerial == null && other.freSerial != null) || (this.freSerial != null && !this.freSerial.equals(other.freSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.FakrachE[ freSerial=" + freSerial + " ]";
    }
    
}
