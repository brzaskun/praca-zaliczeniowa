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
@Table(name = "ZUSZIPA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zuszipa.findAll", query = "SELECT z FROM Zuszipa z"),
    @NamedQuery(name = "Zuszipa.findByIdDokument", query = "SELECT z FROM Zuszipa z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zuszipa.findByIdPlatnik", query = "SELECT z FROM Zuszipa z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zuszipa.findByI1Zglzmdaosprawn", query = "SELECT z FROM Zuszipa z WHERE z.i1Zglzmdaosprawn = :i1Zglzmdaosprawn"),
    @NamedQuery(name = "Zuszipa.findByI2Zglzmdaosfizy", query = "SELECT z FROM Zuszipa z WHERE z.i2Zglzmdaosfizy = :i2Zglzmdaosfizy"),
    @NamedQuery(name = "Zuszipa.findByI3Datanadania", query = "SELECT z FROM Zuszipa z WHERE z.i3Datanadania = :i3Datanadania"),
    @NamedQuery(name = "Zuszipa.findByI4Nalepkar", query = "SELECT z FROM Zuszipa z WHERE z.i4Nalepkar = :i4Nalepkar"),
    @NamedQuery(name = "Zuszipa.findByIi1Nip", query = "SELECT z FROM Zuszipa z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zuszipa.findByIi2Regon", query = "SELECT z FROM Zuszipa z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zuszipa.findByIi3Pesel", query = "SELECT z FROM Zuszipa z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zuszipa.findByIi4Rodzdok", query = "SELECT z FROM Zuszipa z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zuszipa.findByIi5Serianrdok", query = "SELECT z FROM Zuszipa z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zuszipa.findByIi6Nazwaskr", query = "SELECT z FROM Zuszipa z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zuszipa.findByIi7Nazwisko", query = "SELECT z FROM Zuszipa z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zuszipa.findByIi8Imiepierw", query = "SELECT z FROM Zuszipa z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zuszipa.findByIi9Dataurodz", query = "SELECT z FROM Zuszipa z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zuszipa.findByIii1Nip", query = "SELECT z FROM Zuszipa z WHERE z.iii1Nip = :iii1Nip"),
    @NamedQuery(name = "Zuszipa.findByIii2Regon", query = "SELECT z FROM Zuszipa z WHERE z.iii2Regon = :iii2Regon"),
    @NamedQuery(name = "Zuszipa.findByIii3Pesel", query = "SELECT z FROM Zuszipa z WHERE z.iii3Pesel = :iii3Pesel"),
    @NamedQuery(name = "Zuszipa.findByIii4Rodzdok", query = "SELECT z FROM Zuszipa z WHERE z.iii4Rodzdok = :iii4Rodzdok"),
    @NamedQuery(name = "Zuszipa.findByIii5Serianrdok", query = "SELECT z FROM Zuszipa z WHERE z.iii5Serianrdok = :iii5Serianrdok"),
    @NamedQuery(name = "Zuszipa.findByIii6Nazwaskr", query = "SELECT z FROM Zuszipa z WHERE z.iii6Nazwaskr = :iii6Nazwaskr"),
    @NamedQuery(name = "Zuszipa.findByIii7Nazwisko", query = "SELECT z FROM Zuszipa z WHERE z.iii7Nazwisko = :iii7Nazwisko"),
    @NamedQuery(name = "Zuszipa.findByIii8Imiepierw", query = "SELECT z FROM Zuszipa z WHERE z.iii8Imiepierw = :iii8Imiepierw"),
    @NamedQuery(name = "Zuszipa.findByIii9Dataurodz", query = "SELECT z FROM Zuszipa z WHERE z.iii9Dataurodz = :iii9Dataurodz"),
    @NamedQuery(name = "Zuszipa.findByIv1Datawypel", query = "SELECT z FROM Zuszipa z WHERE z.iv1Datawypel = :iv1Datawypel"),
    @NamedQuery(name = "Zuszipa.findByStatuswr", query = "SELECT z FROM Zuszipa z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zuszipa.findByStatuspt", query = "SELECT z FROM Zuszipa z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zuszipa.findByInserttmp", query = "SELECT z FROM Zuszipa z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zuszipa.findBySeria", query = "SELECT z FROM Zuszipa z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zuszipa.findByStatuszus", query = "SELECT z FROM Zuszipa z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zuszipa.findByStatusKontroli", query = "SELECT z FROM Zuszipa z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zuszipa.findByIdPlZusStatus", query = "SELECT z FROM Zuszipa z WHERE z.idPlZusStatus = :idPlZusStatus")})
public class Zuszipa implements Serializable {

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
    @Column(name = "I_1_ZGLZMDAOSPRAWN")
    private Character i1Zglzmdaosprawn;
    @Column(name = "I_2_ZGLZMDAOSFIZY")
    private Character i2Zglzmdaosfizy;
    @Column(name = "I_3_DATANADANIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i3Datanadania;
    @Size(max = 20)
    @Column(name = "I_4_NALEPKAR", length = 20)
    private String i4Nalepkar;
    @Size(max = 10)
    @Column(name = "II_1_NIP", length = 10)
    private String ii1Nip;
    @Size(max = 14)
    @Column(name = "II_2_REGON", length = 14)
    private String ii2Regon;
    @Size(max = 11)
    @Column(name = "II_3_PESEL", length = 11)
    private String ii3Pesel;
    @Column(name = "II_4_RODZDOK")
    private Character ii4Rodzdok;
    @Size(max = 9)
    @Column(name = "II_5_SERIANRDOK", length = 9)
    private String ii5Serianrdok;
    @Size(max = 31)
    @Column(name = "II_6_NAZWASKR", length = 31)
    private String ii6Nazwaskr;
    @Size(max = 31)
    @Column(name = "II_7_NAZWISKO", length = 31)
    private String ii7Nazwisko;
    @Size(max = 22)
    @Column(name = "II_8_IMIEPIERW", length = 22)
    private String ii8Imiepierw;
    @Column(name = "II_9_DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ii9Dataurodz;
    @Size(max = 10)
    @Column(name = "III_1_NIP", length = 10)
    private String iii1Nip;
    @Size(max = 14)
    @Column(name = "III_2_REGON", length = 14)
    private String iii2Regon;
    @Size(max = 11)
    @Column(name = "III_3_PESEL", length = 11)
    private String iii3Pesel;
    @Column(name = "III_4_RODZDOK")
    private Character iii4Rodzdok;
    @Size(max = 9)
    @Column(name = "III_5_SERIANRDOK", length = 9)
    private String iii5Serianrdok;
    @Size(max = 31)
    @Column(name = "III_6_NAZWASKR", length = 31)
    private String iii6Nazwaskr;
    @Size(max = 31)
    @Column(name = "III_7_NAZWISKO", length = 31)
    private String iii7Nazwisko;
    @Size(max = 22)
    @Column(name = "III_8_IMIEPIERW", length = 22)
    private String iii8Imiepierw;
    @Column(name = "III_9_DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iii9Dataurodz;
    @Column(name = "IV_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv1Datawypel;
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

    public Zuszipa() {
    }

    public Zuszipa(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zuszipa(Integer idDokument, int idPlatnik) {
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

    public Character getI1Zglzmdaosprawn() {
        return i1Zglzmdaosprawn;
    }

    public void setI1Zglzmdaosprawn(Character i1Zglzmdaosprawn) {
        this.i1Zglzmdaosprawn = i1Zglzmdaosprawn;
    }

    public Character getI2Zglzmdaosfizy() {
        return i2Zglzmdaosfizy;
    }

    public void setI2Zglzmdaosfizy(Character i2Zglzmdaosfizy) {
        this.i2Zglzmdaosfizy = i2Zglzmdaosfizy;
    }

    public Date getI3Datanadania() {
        return i3Datanadania;
    }

    public void setI3Datanadania(Date i3Datanadania) {
        this.i3Datanadania = i3Datanadania;
    }

    public String getI4Nalepkar() {
        return i4Nalepkar;
    }

    public void setI4Nalepkar(String i4Nalepkar) {
        this.i4Nalepkar = i4Nalepkar;
    }

    public String getIi1Nip() {
        return ii1Nip;
    }

    public void setIi1Nip(String ii1Nip) {
        this.ii1Nip = ii1Nip;
    }

    public String getIi2Regon() {
        return ii2Regon;
    }

    public void setIi2Regon(String ii2Regon) {
        this.ii2Regon = ii2Regon;
    }

    public String getIi3Pesel() {
        return ii3Pesel;
    }

    public void setIi3Pesel(String ii3Pesel) {
        this.ii3Pesel = ii3Pesel;
    }

    public Character getIi4Rodzdok() {
        return ii4Rodzdok;
    }

    public void setIi4Rodzdok(Character ii4Rodzdok) {
        this.ii4Rodzdok = ii4Rodzdok;
    }

    public String getIi5Serianrdok() {
        return ii5Serianrdok;
    }

    public void setIi5Serianrdok(String ii5Serianrdok) {
        this.ii5Serianrdok = ii5Serianrdok;
    }

    public String getIi6Nazwaskr() {
        return ii6Nazwaskr;
    }

    public void setIi6Nazwaskr(String ii6Nazwaskr) {
        this.ii6Nazwaskr = ii6Nazwaskr;
    }

    public String getIi7Nazwisko() {
        return ii7Nazwisko;
    }

    public void setIi7Nazwisko(String ii7Nazwisko) {
        this.ii7Nazwisko = ii7Nazwisko;
    }

    public String getIi8Imiepierw() {
        return ii8Imiepierw;
    }

    public void setIi8Imiepierw(String ii8Imiepierw) {
        this.ii8Imiepierw = ii8Imiepierw;
    }

    public Date getIi9Dataurodz() {
        return ii9Dataurodz;
    }

    public void setIi9Dataurodz(Date ii9Dataurodz) {
        this.ii9Dataurodz = ii9Dataurodz;
    }

    public String getIii1Nip() {
        return iii1Nip;
    }

    public void setIii1Nip(String iii1Nip) {
        this.iii1Nip = iii1Nip;
    }

    public String getIii2Regon() {
        return iii2Regon;
    }

    public void setIii2Regon(String iii2Regon) {
        this.iii2Regon = iii2Regon;
    }

    public String getIii3Pesel() {
        return iii3Pesel;
    }

    public void setIii3Pesel(String iii3Pesel) {
        this.iii3Pesel = iii3Pesel;
    }

    public Character getIii4Rodzdok() {
        return iii4Rodzdok;
    }

    public void setIii4Rodzdok(Character iii4Rodzdok) {
        this.iii4Rodzdok = iii4Rodzdok;
    }

    public String getIii5Serianrdok() {
        return iii5Serianrdok;
    }

    public void setIii5Serianrdok(String iii5Serianrdok) {
        this.iii5Serianrdok = iii5Serianrdok;
    }

    public String getIii6Nazwaskr() {
        return iii6Nazwaskr;
    }

    public void setIii6Nazwaskr(String iii6Nazwaskr) {
        this.iii6Nazwaskr = iii6Nazwaskr;
    }

    public String getIii7Nazwisko() {
        return iii7Nazwisko;
    }

    public void setIii7Nazwisko(String iii7Nazwisko) {
        this.iii7Nazwisko = iii7Nazwisko;
    }

    public String getIii8Imiepierw() {
        return iii8Imiepierw;
    }

    public void setIii8Imiepierw(String iii8Imiepierw) {
        this.iii8Imiepierw = iii8Imiepierw;
    }

    public Date getIii9Dataurodz() {
        return iii9Dataurodz;
    }

    public void setIii9Dataurodz(Date iii9Dataurodz) {
        this.iii9Dataurodz = iii9Dataurodz;
    }

    public Date getIv1Datawypel() {
        return iv1Datawypel;
    }

    public void setIv1Datawypel(Date iv1Datawypel) {
        this.iv1Datawypel = iv1Datawypel;
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
        if (!(object instanceof Zuszipa)) {
            return false;
        }
        Zuszipa other = (Zuszipa) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zuszipa[ idDokument=" + idDokument + " ]";
    }
    
}
