/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "UBEZP_INDSCHEMAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpIndschemat.findAll", query = "SELECT u FROM UbezpIndschemat u"),
    @NamedQuery(name = "UbezpIndschemat.findById", query = "SELECT u FROM UbezpIndschemat u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpIndschemat.findByNumerSchematu", query = "SELECT u FROM UbezpIndschemat u WHERE u.numerSchematu = :numerSchematu"),
    @NamedQuery(name = "UbezpIndschemat.findByRodzaj", query = "SELECT u FROM UbezpIndschemat u WHERE u.rodzaj = :rodzaj"),
    @NamedQuery(name = "UbezpIndschemat.findByEmerPodleganie", query = "SELECT u FROM UbezpIndschemat u WHERE u.emerPodleganie = :emerPodleganie"),
    @NamedQuery(name = "UbezpIndschemat.findByEmerZr1", query = "SELECT u FROM UbezpIndschemat u WHERE u.emerZr1 = :emerZr1"),
    @NamedQuery(name = "UbezpIndschemat.findByEmerProc1", query = "SELECT u FROM UbezpIndschemat u WHERE u.emerProc1 = :emerProc1"),
    @NamedQuery(name = "UbezpIndschemat.findByEmerZr2", query = "SELECT u FROM UbezpIndschemat u WHERE u.emerZr2 = :emerZr2"),
    @NamedQuery(name = "UbezpIndschemat.findByEmerProc2", query = "SELECT u FROM UbezpIndschemat u WHERE u.emerProc2 = :emerProc2"),
    @NamedQuery(name = "UbezpIndschemat.findByRentPodleganie", query = "SELECT u FROM UbezpIndschemat u WHERE u.rentPodleganie = :rentPodleganie"),
    @NamedQuery(name = "UbezpIndschemat.findByRentZr1", query = "SELECT u FROM UbezpIndschemat u WHERE u.rentZr1 = :rentZr1"),
    @NamedQuery(name = "UbezpIndschemat.findByRentProc1", query = "SELECT u FROM UbezpIndschemat u WHERE u.rentProc1 = :rentProc1"),
    @NamedQuery(name = "UbezpIndschemat.findByRentZr2", query = "SELECT u FROM UbezpIndschemat u WHERE u.rentZr2 = :rentZr2"),
    @NamedQuery(name = "UbezpIndschemat.findByRentProc2", query = "SELECT u FROM UbezpIndschemat u WHERE u.rentProc2 = :rentProc2"),
    @NamedQuery(name = "UbezpIndschemat.findByChorPodleganie", query = "SELECT u FROM UbezpIndschemat u WHERE u.chorPodleganie = :chorPodleganie"),
    @NamedQuery(name = "UbezpIndschemat.findByChorZr1", query = "SELECT u FROM UbezpIndschemat u WHERE u.chorZr1 = :chorZr1"),
    @NamedQuery(name = "UbezpIndschemat.findByWypPodleganie", query = "SELECT u FROM UbezpIndschemat u WHERE u.wypPodleganie = :wypPodleganie"),
    @NamedQuery(name = "UbezpIndschemat.findByWypZr1", query = "SELECT u FROM UbezpIndschemat u WHERE u.wypZr1 = :wypZr1"),
    @NamedQuery(name = "UbezpIndschemat.findByWypProc1", query = "SELECT u FROM UbezpIndschemat u WHERE u.wypProc1 = :wypProc1"),
    @NamedQuery(name = "UbezpIndschemat.findByWypZr2", query = "SELECT u FROM UbezpIndschemat u WHERE u.wypZr2 = :wypZr2"),
    @NamedQuery(name = "UbezpIndschemat.findByWypProc2", query = "SELECT u FROM UbezpIndschemat u WHERE u.wypProc2 = :wypProc2"),
    @NamedQuery(name = "UbezpIndschemat.findByZdrowPodleganie", query = "SELECT u FROM UbezpIndschemat u WHERE u.zdrowPodleganie = :zdrowPodleganie"),
    @NamedQuery(name = "UbezpIndschemat.findByZdrowZr1", query = "SELECT u FROM UbezpIndschemat u WHERE u.zdrowZr1 = :zdrowZr1"),
    @NamedQuery(name = "UbezpIndschemat.findByInserttmp", query = "SELECT u FROM UbezpIndschemat u WHERE u.inserttmp = :inserttmp")})
public class UbezpIndschemat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "NUMER_SCHEMATU")
    private Integer numerSchematu;
    @Column(name = "RODZAJ")
    private Character rodzaj;
    @Column(name = "EMER_PODLEGANIE")
    private Character emerPodleganie;
    @Size(max = 2)
    @Column(name = "EMER_ZR1", length = 2)
    private String emerZr1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "EMER_PROC1", precision = 5, scale = 4)
    private BigDecimal emerProc1;
    @Size(max = 2)
    @Column(name = "EMER_ZR2", length = 2)
    private String emerZr2;
    @Column(name = "EMER_PROC2", precision = 5, scale = 4)
    private BigDecimal emerProc2;
    @Column(name = "RENT_PODLEGANIE")
    private Character rentPodleganie;
    @Size(max = 2)
    @Column(name = "RENT_ZR1", length = 2)
    private String rentZr1;
    @Column(name = "RENT_PROC1", precision = 5, scale = 4)
    private BigDecimal rentProc1;
    @Size(max = 2)
    @Column(name = "RENT_ZR2", length = 2)
    private String rentZr2;
    @Column(name = "RENT_PROC2", precision = 5, scale = 4)
    private BigDecimal rentProc2;
    @Column(name = "CHOR_PODLEGANIE")
    private Character chorPodleganie;
    @Size(max = 2)
    @Column(name = "CHOR_ZR1", length = 2)
    private String chorZr1;
    @Column(name = "WYP_PODLEGANIE")
    private Character wypPodleganie;
    @Size(max = 2)
    @Column(name = "WYP_ZR1", length = 2)
    private String wypZr1;
    @Column(name = "WYP_PROC1", precision = 5, scale = 4)
    private BigDecimal wypProc1;
    @Size(max = 2)
    @Column(name = "WYP_ZR2", length = 2)
    private String wypZr2;
    @Column(name = "WYP_PROC2", precision = 5, scale = 4)
    private BigDecimal wypProc2;
    @Column(name = "ZDROW_PODLEGANIE")
    private Character zdrowPodleganie;
    @Size(max = 2)
    @Column(name = "ZDROW_ZR1", length = 2)
    private String zdrowZr1;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public UbezpIndschemat() {
    }

    public UbezpIndschemat(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumerSchematu() {
        return numerSchematu;
    }

    public void setNumerSchematu(Integer numerSchematu) {
        this.numerSchematu = numerSchematu;
    }

    public Character getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(Character rodzaj) {
        this.rodzaj = rodzaj;
    }

    public Character getEmerPodleganie() {
        return emerPodleganie;
    }

    public void setEmerPodleganie(Character emerPodleganie) {
        this.emerPodleganie = emerPodleganie;
    }

    public String getEmerZr1() {
        return emerZr1;
    }

    public void setEmerZr1(String emerZr1) {
        this.emerZr1 = emerZr1;
    }

    public BigDecimal getEmerProc1() {
        return emerProc1;
    }

    public void setEmerProc1(BigDecimal emerProc1) {
        this.emerProc1 = emerProc1;
    }

    public String getEmerZr2() {
        return emerZr2;
    }

    public void setEmerZr2(String emerZr2) {
        this.emerZr2 = emerZr2;
    }

    public BigDecimal getEmerProc2() {
        return emerProc2;
    }

    public void setEmerProc2(BigDecimal emerProc2) {
        this.emerProc2 = emerProc2;
    }

    public Character getRentPodleganie() {
        return rentPodleganie;
    }

    public void setRentPodleganie(Character rentPodleganie) {
        this.rentPodleganie = rentPodleganie;
    }

    public String getRentZr1() {
        return rentZr1;
    }

    public void setRentZr1(String rentZr1) {
        this.rentZr1 = rentZr1;
    }

    public BigDecimal getRentProc1() {
        return rentProc1;
    }

    public void setRentProc1(BigDecimal rentProc1) {
        this.rentProc1 = rentProc1;
    }

    public String getRentZr2() {
        return rentZr2;
    }

    public void setRentZr2(String rentZr2) {
        this.rentZr2 = rentZr2;
    }

    public BigDecimal getRentProc2() {
        return rentProc2;
    }

    public void setRentProc2(BigDecimal rentProc2) {
        this.rentProc2 = rentProc2;
    }

    public Character getChorPodleganie() {
        return chorPodleganie;
    }

    public void setChorPodleganie(Character chorPodleganie) {
        this.chorPodleganie = chorPodleganie;
    }

    public String getChorZr1() {
        return chorZr1;
    }

    public void setChorZr1(String chorZr1) {
        this.chorZr1 = chorZr1;
    }

    public Character getWypPodleganie() {
        return wypPodleganie;
    }

    public void setWypPodleganie(Character wypPodleganie) {
        this.wypPodleganie = wypPodleganie;
    }

    public String getWypZr1() {
        return wypZr1;
    }

    public void setWypZr1(String wypZr1) {
        this.wypZr1 = wypZr1;
    }

    public BigDecimal getWypProc1() {
        return wypProc1;
    }

    public void setWypProc1(BigDecimal wypProc1) {
        this.wypProc1 = wypProc1;
    }

    public String getWypZr2() {
        return wypZr2;
    }

    public void setWypZr2(String wypZr2) {
        this.wypZr2 = wypZr2;
    }

    public BigDecimal getWypProc2() {
        return wypProc2;
    }

    public void setWypProc2(BigDecimal wypProc2) {
        this.wypProc2 = wypProc2;
    }

    public Character getZdrowPodleganie() {
        return zdrowPodleganie;
    }

    public void setZdrowPodleganie(Character zdrowPodleganie) {
        this.zdrowPodleganie = zdrowPodleganie;
    }

    public String getZdrowZr1() {
        return zdrowZr1;
    }

    public void setZdrowZr1(String zdrowZr1) {
        this.zdrowZr1 = zdrowZr1;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UbezpIndschemat)) {
            return false;
        }
        UbezpIndschemat other = (UbezpIndschemat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpIndschemat[ id=" + id + " ]";
    }
    
}
