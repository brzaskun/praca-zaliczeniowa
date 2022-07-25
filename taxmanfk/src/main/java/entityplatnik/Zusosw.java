/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "ZUSOSW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusosw.findAll", query = "SELECT z FROM Zusosw z"),
    @NamedQuery(name = "Zusosw.findByIdDokument", query = "SELECT z FROM Zusosw z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zusosw.findByIdPlatnik", query = "SELECT z FROM Zusosw z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zusosw.findByI1Nip", query = "SELECT z FROM Zusosw z WHERE z.i1Nip = :i1Nip"),
    @NamedQuery(name = "Zusosw.findByI2Regon", query = "SELECT z FROM Zusosw z WHERE z.i2Regon = :i2Regon"),
    @NamedQuery(name = "Zusosw.findByI3Pesel", query = "SELECT z FROM Zusosw z WHERE z.i3Pesel = :i3Pesel"),
    @NamedQuery(name = "Zusosw.findByI4Rodzdok", query = "SELECT z FROM Zusosw z WHERE z.i4Rodzdok = :i4Rodzdok"),
    @NamedQuery(name = "Zusosw.findByI5Serianrdok", query = "SELECT z FROM Zusosw z WHERE z.i5Serianrdok = :i5Serianrdok"),
    @NamedQuery(name = "Zusosw.findByI6Nazwaskr", query = "SELECT z FROM Zusosw z WHERE z.i6Nazwaskr = :i6Nazwaskr"),
    @NamedQuery(name = "Zusosw.findByI7Nazwisko", query = "SELECT z FROM Zusosw z WHERE z.i7Nazwisko = :i7Nazwisko"),
    @NamedQuery(name = "Zusosw.findByI8Imiepierw", query = "SELECT z FROM Zusosw z WHERE z.i8Imiepierw = :i8Imiepierw"),
    @NamedQuery(name = "Zusosw.findByI9Dataurodz", query = "SELECT z FROM Zusosw z WHERE z.i9Dataurodz = :i9Dataurodz"),
    @NamedQuery(name = "Zusosw.findByIi1Raportytak", query = "SELECT z FROM Zusosw z WHERE z.ii1Raportytak = :ii1Raportytak"),
    @NamedQuery(name = "Zusosw.findByIi2Raportynie", query = "SELECT z FROM Zusosw z WHERE z.ii2Raportynie = :ii2Raportynie"),
    @NamedQuery(name = "Zusosw.findByIii1Datawypel", query = "SELECT z FROM Zusosw z WHERE z.iii1Datawypel = :iii1Datawypel"),
    @NamedQuery(name = "Zusosw.findByStatuswr", query = "SELECT z FROM Zusosw z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zusosw.findByStatuspt", query = "SELECT z FROM Zusosw z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zusosw.findByInserttmp", query = "SELECT z FROM Zusosw z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zusosw.findBySeria", query = "SELECT z FROM Zusosw z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zusosw.findByStatuszus", query = "SELECT z FROM Zusosw z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zusosw.findByStatusKontroli", query = "SELECT z FROM Zusosw z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zusosw.findByIdPlZusStatus", query = "SELECT z FROM Zusosw z WHERE z.idPlZusStatus = :idPlZusStatus")})
public class Zusosw implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOKUMENT", nullable = false)
    private Integer idDokument;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Size(max = 10)
    @Column(name = "I_1_NIP", length = 10)
    private String i1Nip;
    @Size(max = 14)
    @Column(name = "I_2_REGON", length = 14)
    private String i2Regon;
    @Size(max = 11)
    @Column(name = "I_3_PESEL", length = 11)
    private String i3Pesel;
    @Column(name = "I_4_RODZDOK")
    private Character i4Rodzdok;
    @Size(max = 9)
    @Column(name = "I_5_SERIANRDOK", length = 9)
    private String i5Serianrdok;
    @Size(max = 31)
    @Column(name = "I_6_NAZWASKR", length = 31)
    private String i6Nazwaskr;
    @Size(max = 31)
    @Column(name = "I_7_NAZWISKO", length = 31)
    private String i7Nazwisko;
    @Size(max = 22)
    @Column(name = "I_8_IMIEPIERW", length = 22)
    private String i8Imiepierw;
    @Column(name = "I_9_DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i9Dataurodz;
    @Column(name = "II_1_RAPORTYTAK")
    private Character ii1Raportytak;
    @Column(name = "II_2_RAPORTYNIE")
    private Character ii2Raportynie;
    @Column(name = "III_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iii1Datawypel;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "SERIA")
    private Integer seria;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_PL_ZUS_STATUS")
    private Character idPlZusStatus;

    public Zusosw() {
    }

    public Zusosw(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zusosw(Integer idDokument, int idPlatnik) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public String getI1Nip() {
        return i1Nip;
    }

    public void setI1Nip(String i1Nip) {
        this.i1Nip = i1Nip;
    }

    public String getI2Regon() {
        return i2Regon;
    }

    public void setI2Regon(String i2Regon) {
        this.i2Regon = i2Regon;
    }

    public String getI3Pesel() {
        return i3Pesel;
    }

    public void setI3Pesel(String i3Pesel) {
        this.i3Pesel = i3Pesel;
    }

    public Character getI4Rodzdok() {
        return i4Rodzdok;
    }

    public void setI4Rodzdok(Character i4Rodzdok) {
        this.i4Rodzdok = i4Rodzdok;
    }

    public String getI5Serianrdok() {
        return i5Serianrdok;
    }

    public void setI5Serianrdok(String i5Serianrdok) {
        this.i5Serianrdok = i5Serianrdok;
    }

    public String getI6Nazwaskr() {
        return i6Nazwaskr;
    }

    public void setI6Nazwaskr(String i6Nazwaskr) {
        this.i6Nazwaskr = i6Nazwaskr;
    }

    public String getI7Nazwisko() {
        return i7Nazwisko;
    }

    public void setI7Nazwisko(String i7Nazwisko) {
        this.i7Nazwisko = i7Nazwisko;
    }

    public String getI8Imiepierw() {
        return i8Imiepierw;
    }

    public void setI8Imiepierw(String i8Imiepierw) {
        this.i8Imiepierw = i8Imiepierw;
    }

    public Date getI9Dataurodz() {
        return i9Dataurodz;
    }

    public void setI9Dataurodz(Date i9Dataurodz) {
        this.i9Dataurodz = i9Dataurodz;
    }

    public Character getIi1Raportytak() {
        return ii1Raportytak;
    }

    public void setIi1Raportytak(Character ii1Raportytak) {
        this.ii1Raportytak = ii1Raportytak;
    }

    public Character getIi2Raportynie() {
        return ii2Raportynie;
    }

    public void setIi2Raportynie(Character ii2Raportynie) {
        this.ii2Raportynie = ii2Raportynie;
    }

    public Date getIii1Datawypel() {
        return iii1Datawypel;
    }

    public void setIii1Datawypel(Date iii1Datawypel) {
        this.iii1Datawypel = iii1Datawypel;
    }

    public Character getStatuswr() {
        return statuswr;
    }

    public void setStatuswr(Character statuswr) {
        this.statuswr = statuswr;
    }

    public Character getStatuspt() {
        return statuspt;
    }

    public void setStatuspt(Character statuspt) {
        this.statuspt = statuspt;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getSeria() {
        return seria;
    }

    public void setSeria(Integer seria) {
        this.seria = seria;
    }

    public Character getStatuszus() {
        return statuszus;
    }

    public void setStatuszus(Character statuszus) {
        this.statuszus = statuszus;
    }

    public Character getStatusKontroli() {
        return statusKontroli;
    }

    public void setStatusKontroli(Character statusKontroli) {
        this.statusKontroli = statusKontroli;
    }

    public Character getIdPlZusStatus() {
        return idPlZusStatus;
    }

    public void setIdPlZusStatus(Character idPlZusStatus) {
        this.idPlZusStatus = idPlZusStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDokument != null ? idDokument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zusosw)) {
            return false;
        }
        Zusosw other = (Zusosw) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zusosw[ idDokument=" + idDokument + " ]";
    }
    
}
