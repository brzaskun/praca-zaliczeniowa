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
@Table(name = "ZUSZIUA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusziua.findAll", query = "SELECT z FROM Zusziua z"),
    @NamedQuery(name = "Zusziua.findByIdDokument", query = "SELECT z FROM Zusziua z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zusziua.findByIdPlatnik", query = "SELECT z FROM Zusziua z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zusziua.findByIdUbezpieczony", query = "SELECT z FROM Zusziua z WHERE z.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "Zusziua.findByI1Zglzmdaidosub", query = "SELECT z FROM Zusziua z WHERE z.i1Zglzmdaidosub = :i1Zglzmdaidosub"),
    @NamedQuery(name = "Zusziua.findByI2Datanadania", query = "SELECT z FROM Zusziua z WHERE z.i2Datanadania = :i2Datanadania"),
    @NamedQuery(name = "Zusziua.findByI3Nalepkar", query = "SELECT z FROM Zusziua z WHERE z.i3Nalepkar = :i3Nalepkar"),
    @NamedQuery(name = "Zusziua.findByIi1Nip", query = "SELECT z FROM Zusziua z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zusziua.findByIi2Regon", query = "SELECT z FROM Zusziua z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zusziua.findByIi3Pesel", query = "SELECT z FROM Zusziua z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zusziua.findByIi4Rodzdok", query = "SELECT z FROM Zusziua z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zusziua.findByIi5Serianrdok", query = "SELECT z FROM Zusziua z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zusziua.findByIi6Nazwaskr", query = "SELECT z FROM Zusziua z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zusziua.findByIi7Nazwisko", query = "SELECT z FROM Zusziua z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zusziua.findByIi8Imiepierw", query = "SELECT z FROM Zusziua z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zusziua.findByIi9Dataurodz", query = "SELECT z FROM Zusziua z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zusziua.findByIii1Pesel", query = "SELECT z FROM Zusziua z WHERE z.iii1Pesel = :iii1Pesel"),
    @NamedQuery(name = "Zusziua.findByIii2Nip", query = "SELECT z FROM Zusziua z WHERE z.iii2Nip = :iii2Nip"),
    @NamedQuery(name = "Zusziua.findByIii3Rodzdok", query = "SELECT z FROM Zusziua z WHERE z.iii3Rodzdok = :iii3Rodzdok"),
    @NamedQuery(name = "Zusziua.findByIii4Serianrdok", query = "SELECT z FROM Zusziua z WHERE z.iii4Serianrdok = :iii4Serianrdok"),
    @NamedQuery(name = "Zusziua.findByIii5Nazwisko", query = "SELECT z FROM Zusziua z WHERE z.iii5Nazwisko = :iii5Nazwisko"),
    @NamedQuery(name = "Zusziua.findByIii6Imiepierw", query = "SELECT z FROM Zusziua z WHERE z.iii6Imiepierw = :iii6Imiepierw"),
    @NamedQuery(name = "Zusziua.findByIii7Dataurodz", query = "SELECT z FROM Zusziua z WHERE z.iii7Dataurodz = :iii7Dataurodz"),
    @NamedQuery(name = "Zusziua.findByIv1Pesel", query = "SELECT z FROM Zusziua z WHERE z.iv1Pesel = :iv1Pesel"),
    @NamedQuery(name = "Zusziua.findByIv2Nip", query = "SELECT z FROM Zusziua z WHERE z.iv2Nip = :iv2Nip"),
    @NamedQuery(name = "Zusziua.findByIv3Rodzdok", query = "SELECT z FROM Zusziua z WHERE z.iv3Rodzdok = :iv3Rodzdok"),
    @NamedQuery(name = "Zusziua.findByIv4Serianrdok", query = "SELECT z FROM Zusziua z WHERE z.iv4Serianrdok = :iv4Serianrdok"),
    @NamedQuery(name = "Zusziua.findByIv5Nazwisko", query = "SELECT z FROM Zusziua z WHERE z.iv5Nazwisko = :iv5Nazwisko"),
    @NamedQuery(name = "Zusziua.findByIv6Imiepierw", query = "SELECT z FROM Zusziua z WHERE z.iv6Imiepierw = :iv6Imiepierw"),
    @NamedQuery(name = "Zusziua.findByIv7Dataurodz", query = "SELECT z FROM Zusziua z WHERE z.iv7Dataurodz = :iv7Dataurodz"),
    @NamedQuery(name = "Zusziua.findByV1Datawypel", query = "SELECT z FROM Zusziua z WHERE z.v1Datawypel = :v1Datawypel"),
    @NamedQuery(name = "Zusziua.findByStatuswr", query = "SELECT z FROM Zusziua z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zusziua.findByStatuspt", query = "SELECT z FROM Zusziua z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zusziua.findByInserttmp", query = "SELECT z FROM Zusziua z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zusziua.findBySeria", query = "SELECT z FROM Zusziua z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zusziua.findByStatuszus", query = "SELECT z FROM Zusziua z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zusziua.findByStatusKontroli", query = "SELECT z FROM Zusziua z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zusziua.findByIdPlZusStatus", query = "SELECT z FROM Zusziua z WHERE z.idPlZusStatus = :idPlZusStatus"),
    @NamedQuery(name = "Zusziua.findByIdUbZusStatus", query = "SELECT z FROM Zusziua z WHERE z.idUbZusStatus = :idUbZusStatus")})
public class Zusziua implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
    @Column(name = "I_1_ZGLZMDAIDOSUB")
    private Character i1Zglzmdaidosub;
    @Column(name = "I_2_DATANADANIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i2Datanadania;
    @Size(max = 20)
    @Column(name = "I_3_NALEPKAR", length = 20)
    private String i3Nalepkar;
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
    @Size(max = 11)
    @Column(name = "III_1_PESEL", length = 11)
    private String iii1Pesel;
    @Size(max = 10)
    @Column(name = "III_2_NIP", length = 10)
    private String iii2Nip;
    @Column(name = "III_3_RODZDOK")
    private Character iii3Rodzdok;
    @Size(max = 9)
    @Column(name = "III_4_SERIANRDOK", length = 9)
    private String iii4Serianrdok;
    @Size(max = 31)
    @Column(name = "III_5_NAZWISKO", length = 31)
    private String iii5Nazwisko;
    @Size(max = 22)
    @Column(name = "III_6_IMIEPIERW", length = 22)
    private String iii6Imiepierw;
    @Column(name = "III_7_DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iii7Dataurodz;
    @Size(max = 11)
    @Column(name = "IV_1_PESEL", length = 11)
    private String iv1Pesel;
    @Size(max = 10)
    @Column(name = "IV_2_NIP", length = 10)
    private String iv2Nip;
    @Column(name = "IV_3_RODZDOK")
    private Character iv3Rodzdok;
    @Size(max = 9)
    @Column(name = "IV_4_SERIANRDOK", length = 9)
    private String iv4Serianrdok;
    @Size(max = 31)
    @Column(name = "IV_5_NAZWISKO", length = 31)
    private String iv5Nazwisko;
    @Size(max = 22)
    @Column(name = "IV_6_IMIEPIERW", length = 22)
    private String iv6Imiepierw;
    @Column(name = "IV_7_DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv7Dataurodz;
    @Column(name = "V_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date v1Datawypel;
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
    @Column(name = "ID_UB_ZUS_STATUS")
    private Character idUbZusStatus;

    public Zusziua() {
    }

    public Zusziua(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zusziua(Integer idDokument, int idPlatnik, int idUbezpieczony) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
        this.idUbezpieczony = idUbezpieczony;
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

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public Character getI1Zglzmdaidosub() {
        return i1Zglzmdaidosub;
    }

    public void setI1Zglzmdaidosub(Character i1Zglzmdaidosub) {
        this.i1Zglzmdaidosub = i1Zglzmdaidosub;
    }

    public Date getI2Datanadania() {
        return i2Datanadania;
    }

    public void setI2Datanadania(Date i2Datanadania) {
        this.i2Datanadania = i2Datanadania;
    }

    public String getI3Nalepkar() {
        return i3Nalepkar;
    }

    public void setI3Nalepkar(String i3Nalepkar) {
        this.i3Nalepkar = i3Nalepkar;
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

    public String getIii1Pesel() {
        return iii1Pesel;
    }

    public void setIii1Pesel(String iii1Pesel) {
        this.iii1Pesel = iii1Pesel;
    }

    public String getIii2Nip() {
        return iii2Nip;
    }

    public void setIii2Nip(String iii2Nip) {
        this.iii2Nip = iii2Nip;
    }

    public Character getIii3Rodzdok() {
        return iii3Rodzdok;
    }

    public void setIii3Rodzdok(Character iii3Rodzdok) {
        this.iii3Rodzdok = iii3Rodzdok;
    }

    public String getIii4Serianrdok() {
        return iii4Serianrdok;
    }

    public void setIii4Serianrdok(String iii4Serianrdok) {
        this.iii4Serianrdok = iii4Serianrdok;
    }

    public String getIii5Nazwisko() {
        return iii5Nazwisko;
    }

    public void setIii5Nazwisko(String iii5Nazwisko) {
        this.iii5Nazwisko = iii5Nazwisko;
    }

    public String getIii6Imiepierw() {
        return iii6Imiepierw;
    }

    public void setIii6Imiepierw(String iii6Imiepierw) {
        this.iii6Imiepierw = iii6Imiepierw;
    }

    public Date getIii7Dataurodz() {
        return iii7Dataurodz;
    }

    public void setIii7Dataurodz(Date iii7Dataurodz) {
        this.iii7Dataurodz = iii7Dataurodz;
    }

    public String getIv1Pesel() {
        return iv1Pesel;
    }

    public void setIv1Pesel(String iv1Pesel) {
        this.iv1Pesel = iv1Pesel;
    }

    public String getIv2Nip() {
        return iv2Nip;
    }

    public void setIv2Nip(String iv2Nip) {
        this.iv2Nip = iv2Nip;
    }

    public Character getIv3Rodzdok() {
        return iv3Rodzdok;
    }

    public void setIv3Rodzdok(Character iv3Rodzdok) {
        this.iv3Rodzdok = iv3Rodzdok;
    }

    public String getIv4Serianrdok() {
        return iv4Serianrdok;
    }

    public void setIv4Serianrdok(String iv4Serianrdok) {
        this.iv4Serianrdok = iv4Serianrdok;
    }

    public String getIv5Nazwisko() {
        return iv5Nazwisko;
    }

    public void setIv5Nazwisko(String iv5Nazwisko) {
        this.iv5Nazwisko = iv5Nazwisko;
    }

    public String getIv6Imiepierw() {
        return iv6Imiepierw;
    }

    public void setIv6Imiepierw(String iv6Imiepierw) {
        this.iv6Imiepierw = iv6Imiepierw;
    }

    public Date getIv7Dataurodz() {
        return iv7Dataurodz;
    }

    public void setIv7Dataurodz(Date iv7Dataurodz) {
        this.iv7Dataurodz = iv7Dataurodz;
    }

    public Date getV1Datawypel() {
        return v1Datawypel;
    }

    public void setV1Datawypel(Date v1Datawypel) {
        this.v1Datawypel = v1Datawypel;
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

    public Character getIdUbZusStatus() {
        return idUbZusStatus;
    }

    public void setIdUbZusStatus(Character idUbZusStatus) {
        this.idUbZusStatus = idUbZusStatus;
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
        if (!(object instanceof Zusziua)) {
            return false;
        }
        Zusziua other = (Zusziua) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zusziua[ idDokument=" + idDokument + " ]";
    }
    
}
