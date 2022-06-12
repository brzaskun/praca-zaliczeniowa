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
@Table(name = "waluta", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Waluta.findAll", query = "SELECT w FROM Waluta w"),
    @NamedQuery(name = "Waluta.findByWalSerial", query = "SELECT w FROM Waluta w WHERE w.walSerial = :walSerial"),
    @NamedQuery(name = "Waluta.findByWalSkrot", query = "SELECT w FROM Waluta w WHERE w.walSkrot = :walSkrot"),
    @NamedQuery(name = "Waluta.findByWalNazwa", query = "SELECT w FROM Waluta w WHERE w.walNazwa = :walNazwa"),
    @NamedQuery(name = "Waluta.findByWalIlosc", query = "SELECT w FROM Waluta w WHERE w.walIlosc = :walIlosc"),
    @NamedQuery(name = "Waluta.findByWalKurs", query = "SELECT w FROM Waluta w WHERE w.walKurs = :walKurs"),
    @NamedQuery(name = "Waluta.findByWalDzien", query = "SELECT w FROM Waluta w WHERE w.walDzien = :walDzien"),
    @NamedQuery(name = "Waluta.findByWalData1", query = "SELECT w FROM Waluta w WHERE w.walData1 = :walData1"),
    @NamedQuery(name = "Waluta.findByWalData2", query = "SELECT w FROM Waluta w WHERE w.walData2 = :walData2"),
    @NamedQuery(name = "Waluta.findByWalVchar1", query = "SELECT w FROM Waluta w WHERE w.walVchar1 = :walVchar1"),
    @NamedQuery(name = "Waluta.findByWalNum1", query = "SELECT w FROM Waluta w WHERE w.walNum1 = :walNum1"),
    @NamedQuery(name = "Waluta.findByWalNum2", query = "SELECT w FROM Waluta w WHERE w.walNum2 = :walNum2")})
public class Waluta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "wal_serial", nullable = false)
    private Integer walSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "wal_skrot", nullable = false, length = 5)
    private String walSkrot;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "wal_nazwa", nullable = false, length = 32)
    private String walNazwa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wal_ilosc", nullable = false)
    private short walIlosc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "wal_kurs", nullable = false, precision = 8, scale = 4)
    private BigDecimal walKurs;
    @Column(name = "wal_dzien")
    @Temporal(TemporalType.TIMESTAMP)
    private Date walDzien;
    @Column(name = "wal_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date walData1;
    @Column(name = "wal_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date walData2;
    @Size(max = 32)
    @Column(name = "wal_vchar_1", length = 32)
    private String walVchar1;
    @Column(name = "wal_num_1", precision = 17, scale = 6)
    private BigDecimal walNum1;
    @Column(name = "wal_num_2", precision = 17, scale = 6)
    private BigDecimal walNum2;
    @OneToMany(mappedBy = "magZakWalSerial")
    private List<Magazyn> magazynList;
    @OneToMany(mappedBy = "magSprWalSerial")
    private List<Magazyn> magazynList1;

    public Waluta() {
    }

    public Waluta(Integer walSerial) {
        this.walSerial = walSerial;
    }

    public Waluta(Integer walSerial, String walSkrot, String walNazwa, short walIlosc, BigDecimal walKurs) {
        this.walSerial = walSerial;
        this.walSkrot = walSkrot;
        this.walNazwa = walNazwa;
        this.walIlosc = walIlosc;
        this.walKurs = walKurs;
    }

    public Integer getWalSerial() {
        return walSerial;
    }

    public void setWalSerial(Integer walSerial) {
        this.walSerial = walSerial;
    }

    public String getWalSkrot() {
        return walSkrot;
    }

    public void setWalSkrot(String walSkrot) {
        this.walSkrot = walSkrot;
    }

    public String getWalNazwa() {
        return walNazwa;
    }

    public void setWalNazwa(String walNazwa) {
        this.walNazwa = walNazwa;
    }

    public short getWalIlosc() {
        return walIlosc;
    }

    public void setWalIlosc(short walIlosc) {
        this.walIlosc = walIlosc;
    }

    public BigDecimal getWalKurs() {
        return walKurs;
    }

    public void setWalKurs(BigDecimal walKurs) {
        this.walKurs = walKurs;
    }

    public Date getWalDzien() {
        return walDzien;
    }

    public void setWalDzien(Date walDzien) {
        this.walDzien = walDzien;
    }

    public Date getWalData1() {
        return walData1;
    }

    public void setWalData1(Date walData1) {
        this.walData1 = walData1;
    }

    public Date getWalData2() {
        return walData2;
    }

    public void setWalData2(Date walData2) {
        this.walData2 = walData2;
    }

    public String getWalVchar1() {
        return walVchar1;
    }

    public void setWalVchar1(String walVchar1) {
        this.walVchar1 = walVchar1;
    }

    public BigDecimal getWalNum1() {
        return walNum1;
    }

    public void setWalNum1(BigDecimal walNum1) {
        this.walNum1 = walNum1;
    }

    public BigDecimal getWalNum2() {
        return walNum2;
    }

    public void setWalNum2(BigDecimal walNum2) {
        this.walNum2 = walNum2;
    }

    @XmlTransient
    public List<Magazyn> getMagazynList() {
        return magazynList;
    }

    public void setMagazynList(List<Magazyn> magazynList) {
        this.magazynList = magazynList;
    }

    @XmlTransient
    public List<Magazyn> getMagazynList1() {
        return magazynList1;
    }

    public void setMagazynList1(List<Magazyn> magazynList1) {
        this.magazynList1 = magazynList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (walSerial != null ? walSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Waluta)) {
            return false;
        }
        Waluta other = (Waluta) object;
        if ((this.walSerial == null && other.walSerial != null) || (this.walSerial != null && !this.walSerial.equals(other.walSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Waluta[ walSerial=" + walSerial + " ]";
    }
    
}
