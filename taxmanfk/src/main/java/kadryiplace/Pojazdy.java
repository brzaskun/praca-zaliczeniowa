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
@Table(name = "pojazdy", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pojazdy.findAll", query = "SELECT p FROM Pojazdy p"),
    @NamedQuery(name = "Pojazdy.findByPojSerial", query = "SELECT p FROM Pojazdy p WHERE p.pojSerial = :pojSerial"),
    @NamedQuery(name = "Pojazdy.findByPojFirSerial", query = "SELECT p FROM Pojazdy p WHERE p.pojFirSerial = :pojFirSerial"),
    @NamedQuery(name = "Pojazdy.findByPojNazwa", query = "SELECT p FROM Pojazdy p WHERE p.pojNazwa = :pojNazwa"),
    @NamedQuery(name = "Pojazdy.findByPojPojemn", query = "SELECT p FROM Pojazdy p WHERE p.pojPojemn = :pojPojemn"),
    @NamedQuery(name = "Pojazdy.findByPojNrRej", query = "SELECT p FROM Pojazdy p WHERE p.pojNrRej = :pojNrRej"),
    @NamedQuery(name = "Pojazdy.findByPojStawka", query = "SELECT p FROM Pojazdy p WHERE p.pojStawka = :pojStawka"),
    @NamedQuery(name = "Pojazdy.findByPojChar1", query = "SELECT p FROM Pojazdy p WHERE p.pojChar1 = :pojChar1"),
    @NamedQuery(name = "Pojazdy.findByPojChar2", query = "SELECT p FROM Pojazdy p WHERE p.pojChar2 = :pojChar2"),
    @NamedQuery(name = "Pojazdy.findByPojChar3", query = "SELECT p FROM Pojazdy p WHERE p.pojChar3 = :pojChar3"),
    @NamedQuery(name = "Pojazdy.findByPojChar4", query = "SELECT p FROM Pojazdy p WHERE p.pojChar4 = :pojChar4"),
    @NamedQuery(name = "Pojazdy.findByPojNum1", query = "SELECT p FROM Pojazdy p WHERE p.pojNum1 = :pojNum1"),
    @NamedQuery(name = "Pojazdy.findByPojNum2", query = "SELECT p FROM Pojazdy p WHERE p.pojNum2 = :pojNum2"),
    @NamedQuery(name = "Pojazdy.findByPojInt1", query = "SELECT p FROM Pojazdy p WHERE p.pojInt1 = :pojInt1"),
    @NamedQuery(name = "Pojazdy.findByPojInt2", query = "SELECT p FROM Pojazdy p WHERE p.pojInt2 = :pojInt2"),
    @NamedQuery(name = "Pojazdy.findByPojDate1", query = "SELECT p FROM Pojazdy p WHERE p.pojDate1 = :pojDate1"),
    @NamedQuery(name = "Pojazdy.findByPojDate2", query = "SELECT p FROM Pojazdy p WHERE p.pojDate2 = :pojDate2"),
    @NamedQuery(name = "Pojazdy.findByPojVchar1", query = "SELECT p FROM Pojazdy p WHERE p.pojVchar1 = :pojVchar1"),
    @NamedQuery(name = "Pojazdy.findByPojVchar2", query = "SELECT p FROM Pojazdy p WHERE p.pojVchar2 = :pojVchar2"),
    @NamedQuery(name = "Pojazdy.findByPojStawkaC", query = "SELECT p FROM Pojazdy p WHERE p.pojStawkaC = :pojStawkaC")})
public class Pojazdy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "poj_serial", nullable = false)
    private Integer pojSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "poj_fir_serial", nullable = false)
    private int pojFirSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "poj_nazwa", nullable = false, length = 32)
    private String pojNazwa;
    @Column(name = "poj_pojemn")
    private Short pojPojemn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "poj_nr_rej", nullable = false, length = 16)
    private String pojNrRej;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "poj_stawka", nullable = false, precision = 13, scale = 2)
    private BigDecimal pojStawka;
    @Column(name = "poj_char_1")
    private Character pojChar1;
    @Column(name = "poj_char_2")
    private Character pojChar2;
    @Column(name = "poj_char_3")
    private Character pojChar3;
    @Column(name = "poj_char_4")
    private Character pojChar4;
    @Column(name = "poj_num_1", precision = 17, scale = 6)
    private BigDecimal pojNum1;
    @Column(name = "poj_num_2", precision = 17, scale = 6)
    private BigDecimal pojNum2;
    @Column(name = "poj_int_1")
    private Integer pojInt1;
    @Column(name = "poj_int_2")
    private Integer pojInt2;
    @Column(name = "poj_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pojDate1;
    @Column(name = "poj_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pojDate2;
    @Size(max = 64)
    @Column(name = "poj_vchar_1", length = 64)
    private String pojVchar1;
    @Size(max = 64)
    @Column(name = "poj_vchar_2", length = 64)
    private String pojVchar2;
    @Size(max = 2)
    @Column(name = "poj_stawka_c", length = 2)
    private String pojStawkaC;
    @JoinColumn(name = "poj_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba pojOsoSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "epzPojSerial")
    private List<EppZest> eppZestList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eppPojSerial")
    private List<Przebieg> przebiegList;

    public Pojazdy() {
    }

    public Pojazdy(Integer pojSerial) {
        this.pojSerial = pojSerial;
    }

    public Pojazdy(Integer pojSerial, int pojFirSerial, String pojNazwa, String pojNrRej, BigDecimal pojStawka) {
        this.pojSerial = pojSerial;
        this.pojFirSerial = pojFirSerial;
        this.pojNazwa = pojNazwa;
        this.pojNrRej = pojNrRej;
        this.pojStawka = pojStawka;
    }

    public Integer getPojSerial() {
        return pojSerial;
    }

    public void setPojSerial(Integer pojSerial) {
        this.pojSerial = pojSerial;
    }

    public int getPojFirSerial() {
        return pojFirSerial;
    }

    public void setPojFirSerial(int pojFirSerial) {
        this.pojFirSerial = pojFirSerial;
    }

    public String getPojNazwa() {
        return pojNazwa;
    }

    public void setPojNazwa(String pojNazwa) {
        this.pojNazwa = pojNazwa;
    }

    public Short getPojPojemn() {
        return pojPojemn;
    }

    public void setPojPojemn(Short pojPojemn) {
        this.pojPojemn = pojPojemn;
    }

    public String getPojNrRej() {
        return pojNrRej;
    }

    public void setPojNrRej(String pojNrRej) {
        this.pojNrRej = pojNrRej;
    }

    public BigDecimal getPojStawka() {
        return pojStawka;
    }

    public void setPojStawka(BigDecimal pojStawka) {
        this.pojStawka = pojStawka;
    }

    public Character getPojChar1() {
        return pojChar1;
    }

    public void setPojChar1(Character pojChar1) {
        this.pojChar1 = pojChar1;
    }

    public Character getPojChar2() {
        return pojChar2;
    }

    public void setPojChar2(Character pojChar2) {
        this.pojChar2 = pojChar2;
    }

    public Character getPojChar3() {
        return pojChar3;
    }

    public void setPojChar3(Character pojChar3) {
        this.pojChar3 = pojChar3;
    }

    public Character getPojChar4() {
        return pojChar4;
    }

    public void setPojChar4(Character pojChar4) {
        this.pojChar4 = pojChar4;
    }

    public BigDecimal getPojNum1() {
        return pojNum1;
    }

    public void setPojNum1(BigDecimal pojNum1) {
        this.pojNum1 = pojNum1;
    }

    public BigDecimal getPojNum2() {
        return pojNum2;
    }

    public void setPojNum2(BigDecimal pojNum2) {
        this.pojNum2 = pojNum2;
    }

    public Integer getPojInt1() {
        return pojInt1;
    }

    public void setPojInt1(Integer pojInt1) {
        this.pojInt1 = pojInt1;
    }

    public Integer getPojInt2() {
        return pojInt2;
    }

    public void setPojInt2(Integer pojInt2) {
        this.pojInt2 = pojInt2;
    }

    public Date getPojDate1() {
        return pojDate1;
    }

    public void setPojDate1(Date pojDate1) {
        this.pojDate1 = pojDate1;
    }

    public Date getPojDate2() {
        return pojDate2;
    }

    public void setPojDate2(Date pojDate2) {
        this.pojDate2 = pojDate2;
    }

    public String getPojVchar1() {
        return pojVchar1;
    }

    public void setPojVchar1(String pojVchar1) {
        this.pojVchar1 = pojVchar1;
    }

    public String getPojVchar2() {
        return pojVchar2;
    }

    public void setPojVchar2(String pojVchar2) {
        this.pojVchar2 = pojVchar2;
    }

    public String getPojStawkaC() {
        return pojStawkaC;
    }

    public void setPojStawkaC(String pojStawkaC) {
        this.pojStawkaC = pojStawkaC;
    }

    public Osoba getPojOsoSerial() {
        return pojOsoSerial;
    }

    public void setPojOsoSerial(Osoba pojOsoSerial) {
        this.pojOsoSerial = pojOsoSerial;
    }

    @XmlTransient
    public List<EppZest> getEppZestList() {
        return eppZestList;
    }

    public void setEppZestList(List<EppZest> eppZestList) {
        this.eppZestList = eppZestList;
    }

    @XmlTransient
    public List<Przebieg> getPrzebiegList() {
        return przebiegList;
    }

    public void setPrzebiegList(List<Przebieg> przebiegList) {
        this.przebiegList = przebiegList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pojSerial != null ? pojSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pojazdy)) {
            return false;
        }
        Pojazdy other = (Pojazdy) object;
        if ((this.pojSerial == null && other.pojSerial != null) || (this.pojSerial != null && !this.pojSerial.equals(other.pojSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Pojazdy[ pojSerial=" + pojSerial + " ]";
    }
    
}
