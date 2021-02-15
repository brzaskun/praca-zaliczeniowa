/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "vat", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vat.findAll", query = "SELECT v FROM Vat v"),
    @NamedQuery(name = "Vat.findByVatSerial", query = "SELECT v FROM Vat v WHERE v.vatSerial = :vatSerial"),
    @NamedQuery(name = "Vat.findByVatOpis", query = "SELECT v FROM Vat v WHERE v.vatOpis = :vatOpis"),
    @NamedQuery(name = "Vat.findByVatProcent", query = "SELECT v FROM Vat v WHERE v.vatProcent = :vatProcent"),
    @NamedQuery(name = "Vat.findByVatKod", query = "SELECT v FROM Vat v WHERE v.vatKod = :vatKod"),
    @NamedQuery(name = "Vat.findByVatNum1", query = "SELECT v FROM Vat v WHERE v.vatNum1 = :vatNum1"),
    @NamedQuery(name = "Vat.findByVatNum2", query = "SELECT v FROM Vat v WHERE v.vatNum2 = :vatNum2")})
public class Vat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "vat_serial", nullable = false)
    private Integer vatSerial;
    @Size(max = 32)
    @Column(name = "vat_opis", length = 32)
    private String vatOpis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vat_procent", precision = 5, scale = 2)
    private BigDecimal vatProcent;
    @Size(max = 2)
    @Column(name = "vat_kod", length = 2)
    private String vatKod;
    @Column(name = "vat_num_1", precision = 17, scale = 6)
    private BigDecimal vatNum1;
    @Column(name = "vat_num_2", precision = 17, scale = 6)
    private BigDecimal vatNum2;
    @JoinColumn(name = "vat_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma vatFirSerial;

    public Vat() {
    }

    public Vat(Integer vatSerial) {
        this.vatSerial = vatSerial;
    }

    public Integer getVatSerial() {
        return vatSerial;
    }

    public void setVatSerial(Integer vatSerial) {
        this.vatSerial = vatSerial;
    }

    public String getVatOpis() {
        return vatOpis;
    }

    public void setVatOpis(String vatOpis) {
        this.vatOpis = vatOpis;
    }

    public BigDecimal getVatProcent() {
        return vatProcent;
    }

    public void setVatProcent(BigDecimal vatProcent) {
        this.vatProcent = vatProcent;
    }

    public String getVatKod() {
        return vatKod;
    }

    public void setVatKod(String vatKod) {
        this.vatKod = vatKod;
    }

    public BigDecimal getVatNum1() {
        return vatNum1;
    }

    public void setVatNum1(BigDecimal vatNum1) {
        this.vatNum1 = vatNum1;
    }

    public BigDecimal getVatNum2() {
        return vatNum2;
    }

    public void setVatNum2(BigDecimal vatNum2) {
        this.vatNum2 = vatNum2;
    }

    public Firma getVatFirSerial() {
        return vatFirSerial;
    }

    public void setVatFirSerial(Firma vatFirSerial) {
        this.vatFirSerial = vatFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vatSerial != null ? vatSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vat)) {
            return false;
        }
        Vat other = (Vat) object;
        if ((this.vatSerial == null && other.vatSerial != null) || (this.vatSerial != null && !this.vatSerial.equals(other.vatSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Vat[ vatSerial=" + vatSerial + " ]";
    }
    
}
