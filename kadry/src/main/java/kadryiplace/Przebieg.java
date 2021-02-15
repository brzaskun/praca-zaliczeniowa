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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "przebieg", catalog = "kadryiplace", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"epp_poj_serial", "epp_rok_serial", "epp_lp"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Przebieg.findAll", query = "SELECT p FROM Przebieg p"),
    @NamedQuery(name = "Przebieg.findByEppSerial", query = "SELECT p FROM Przebieg p WHERE p.eppSerial = :eppSerial"),
    @NamedQuery(name = "Przebieg.findByEppData", query = "SELECT p FROM Przebieg p WHERE p.eppData = :eppData"),
    @NamedQuery(name = "Przebieg.findByEppOpis", query = "SELECT p FROM Przebieg p WHERE p.eppOpis = :eppOpis"),
    @NamedQuery(name = "Przebieg.findByEppCel", query = "SELECT p FROM Przebieg p WHERE p.eppCel = :eppCel"),
    @NamedQuery(name = "Przebieg.findByEppIlosc", query = "SELECT p FROM Przebieg p WHERE p.eppIlosc = :eppIlosc"),
    @NamedQuery(name = "Przebieg.findByEppStawka", query = "SELECT p FROM Przebieg p WHERE p.eppStawka = :eppStawka"),
    @NamedQuery(name = "Przebieg.findByEppUwagi", query = "SELECT p FROM Przebieg p WHERE p.eppUwagi = :eppUwagi"),
    @NamedQuery(name = "Przebieg.findByEppSkreslony", query = "SELECT p FROM Przebieg p WHERE p.eppSkreslony = :eppSkreslony"),
    @NamedQuery(name = "Przebieg.findByEppLp", query = "SELECT p FROM Przebieg p WHERE p.eppLp = :eppLp"),
    @NamedQuery(name = "Przebieg.findByEppChar1", query = "SELECT p FROM Przebieg p WHERE p.eppChar1 = :eppChar1"),
    @NamedQuery(name = "Przebieg.findByEppChar2", query = "SELECT p FROM Przebieg p WHERE p.eppChar2 = :eppChar2"),
    @NamedQuery(name = "Przebieg.findByEppVchar1", query = "SELECT p FROM Przebieg p WHERE p.eppVchar1 = :eppVchar1"),
    @NamedQuery(name = "Przebieg.findByEppData1", query = "SELECT p FROM Przebieg p WHERE p.eppData1 = :eppData1"),
    @NamedQuery(name = "Przebieg.findByEppData2", query = "SELECT p FROM Przebieg p WHERE p.eppData2 = :eppData2"),
    @NamedQuery(name = "Przebieg.findByEppNum1", query = "SELECT p FROM Przebieg p WHERE p.eppNum1 = :eppNum1"),
    @NamedQuery(name = "Przebieg.findByEppNum2", query = "SELECT p FROM Przebieg p WHERE p.eppNum2 = :eppNum2"),
    @NamedQuery(name = "Przebieg.findByEppInt1", query = "SELECT p FROM Przebieg p WHERE p.eppInt1 = :eppInt1")})
public class Przebieg implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "epp_serial", nullable = false)
    private Integer eppSerial;
    @Column(name = "epp_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eppData;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "epp_opis", nullable = false, length = 64)
    private String eppOpis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "epp_cel", nullable = false, length = 64)
    private String eppCel;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "epp_ilosc", nullable = false, precision = 10, scale = 4)
    private BigDecimal eppIlosc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "epp_stawka", nullable = false, precision = 12, scale = 6)
    private BigDecimal eppStawka;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "epp_uwagi", nullable = false, length = 64)
    private String eppUwagi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "epp_skreslony", nullable = false)
    private Character eppSkreslony;
    @Basic(optional = false)
    @NotNull
    @Column(name = "epp_lp", nullable = false)
    private int eppLp;
    @Column(name = "epp_char_1")
    private Character eppChar1;
    @Column(name = "epp_char_2")
    private Character eppChar2;
    @Size(max = 64)
    @Column(name = "epp_vchar_1", length = 64)
    private String eppVchar1;
    @Column(name = "epp_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eppData1;
    @Column(name = "epp_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eppData2;
    @Column(name = "epp_num_1", precision = 17, scale = 6)
    private BigDecimal eppNum1;
    @Column(name = "epp_num_2", precision = 17, scale = 6)
    private BigDecimal eppNum2;
    @Column(name = "epp_int_1")
    private Integer eppInt1;
    @OneToMany(mappedBy = "dsrEppSerial")
    private List<DaneStatR> daneStatRList;
    @JoinColumn(name = "epp_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres eppOkrSerial;
    @JoinColumn(name = "epp_poj_serial", referencedColumnName = "poj_serial", nullable = false)
    @ManyToOne(optional = false)
    private Pojazdy eppPojSerial;
    @JoinColumn(name = "epp_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok eppRokSerial;

    public Przebieg() {
    }

    public Przebieg(Integer eppSerial) {
        this.eppSerial = eppSerial;
    }

    public Przebieg(Integer eppSerial, String eppOpis, String eppCel, BigDecimal eppIlosc, BigDecimal eppStawka, String eppUwagi, Character eppSkreslony, int eppLp) {
        this.eppSerial = eppSerial;
        this.eppOpis = eppOpis;
        this.eppCel = eppCel;
        this.eppIlosc = eppIlosc;
        this.eppStawka = eppStawka;
        this.eppUwagi = eppUwagi;
        this.eppSkreslony = eppSkreslony;
        this.eppLp = eppLp;
    }

    public Integer getEppSerial() {
        return eppSerial;
    }

    public void setEppSerial(Integer eppSerial) {
        this.eppSerial = eppSerial;
    }

    public Date getEppData() {
        return eppData;
    }

    public void setEppData(Date eppData) {
        this.eppData = eppData;
    }

    public String getEppOpis() {
        return eppOpis;
    }

    public void setEppOpis(String eppOpis) {
        this.eppOpis = eppOpis;
    }

    public String getEppCel() {
        return eppCel;
    }

    public void setEppCel(String eppCel) {
        this.eppCel = eppCel;
    }

    public BigDecimal getEppIlosc() {
        return eppIlosc;
    }

    public void setEppIlosc(BigDecimal eppIlosc) {
        this.eppIlosc = eppIlosc;
    }

    public BigDecimal getEppStawka() {
        return eppStawka;
    }

    public void setEppStawka(BigDecimal eppStawka) {
        this.eppStawka = eppStawka;
    }

    public String getEppUwagi() {
        return eppUwagi;
    }

    public void setEppUwagi(String eppUwagi) {
        this.eppUwagi = eppUwagi;
    }

    public Character getEppSkreslony() {
        return eppSkreslony;
    }

    public void setEppSkreslony(Character eppSkreslony) {
        this.eppSkreslony = eppSkreslony;
    }

    public int getEppLp() {
        return eppLp;
    }

    public void setEppLp(int eppLp) {
        this.eppLp = eppLp;
    }

    public Character getEppChar1() {
        return eppChar1;
    }

    public void setEppChar1(Character eppChar1) {
        this.eppChar1 = eppChar1;
    }

    public Character getEppChar2() {
        return eppChar2;
    }

    public void setEppChar2(Character eppChar2) {
        this.eppChar2 = eppChar2;
    }

    public String getEppVchar1() {
        return eppVchar1;
    }

    public void setEppVchar1(String eppVchar1) {
        this.eppVchar1 = eppVchar1;
    }

    public Date getEppData1() {
        return eppData1;
    }

    public void setEppData1(Date eppData1) {
        this.eppData1 = eppData1;
    }

    public Date getEppData2() {
        return eppData2;
    }

    public void setEppData2(Date eppData2) {
        this.eppData2 = eppData2;
    }

    public BigDecimal getEppNum1() {
        return eppNum1;
    }

    public void setEppNum1(BigDecimal eppNum1) {
        this.eppNum1 = eppNum1;
    }

    public BigDecimal getEppNum2() {
        return eppNum2;
    }

    public void setEppNum2(BigDecimal eppNum2) {
        this.eppNum2 = eppNum2;
    }

    public Integer getEppInt1() {
        return eppInt1;
    }

    public void setEppInt1(Integer eppInt1) {
        this.eppInt1 = eppInt1;
    }

    @XmlTransient
    public List<DaneStatR> getDaneStatRList() {
        return daneStatRList;
    }

    public void setDaneStatRList(List<DaneStatR> daneStatRList) {
        this.daneStatRList = daneStatRList;
    }

    public Okres getEppOkrSerial() {
        return eppOkrSerial;
    }

    public void setEppOkrSerial(Okres eppOkrSerial) {
        this.eppOkrSerial = eppOkrSerial;
    }

    public Pojazdy getEppPojSerial() {
        return eppPojSerial;
    }

    public void setEppPojSerial(Pojazdy eppPojSerial) {
        this.eppPojSerial = eppPojSerial;
    }

    public Rok getEppRokSerial() {
        return eppRokSerial;
    }

    public void setEppRokSerial(Rok eppRokSerial) {
        this.eppRokSerial = eppRokSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eppSerial != null ? eppSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Przebieg)) {
            return false;
        }
        Przebieg other = (Przebieg) object;
        if ((this.eppSerial == null && other.eppSerial != null) || (this.eppSerial != null && !this.eppSerial.equals(other.eppSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Przebieg[ eppSerial=" + eppSerial + " ]";
    }
    
}
