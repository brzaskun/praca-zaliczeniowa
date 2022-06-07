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
@Table(name = "ZUSRIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusria.findAll", query = "SELECT z FROM Zusria z"),
    @NamedQuery(name = "Zusria.findByIdDokument", query = "SELECT z FROM Zusria z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zusria.findByIdPlatnik", query = "SELECT z FROM Zusria z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zusria.findByIdUbezpieczony", query = "SELECT z FROM Zusria z WHERE z.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "Zusria.findByI1RodzajDokumentu", query = "SELECT z FROM Zusria z WHERE z.i1RodzajDokumentu = :i1RodzajDokumentu"),
    @NamedQuery(name = "Zusria.findByIi1Nip", query = "SELECT z FROM Zusria z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zusria.findByIi2Regon", query = "SELECT z FROM Zusria z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zusria.findByIi3Pesel", query = "SELECT z FROM Zusria z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zusria.findByIi4Rodzdok", query = "SELECT z FROM Zusria z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zusria.findByIi5Serianrdok", query = "SELECT z FROM Zusria z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zusria.findByIi6Nazwaskr", query = "SELECT z FROM Zusria z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zusria.findByIi7Nazwisko", query = "SELECT z FROM Zusria z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zusria.findByIi8Imiepierw", query = "SELECT z FROM Zusria z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zusria.findByIi9Dataurodz", query = "SELECT z FROM Zusria z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zusria.findByIii1Nazwisko", query = "SELECT z FROM Zusria z WHERE z.iii1Nazwisko = :iii1Nazwisko"),
    @NamedQuery(name = "Zusria.findByIii2Imiepierw", query = "SELECT z FROM Zusria z WHERE z.iii2Imiepierw = :iii2Imiepierw"),
    @NamedQuery(name = "Zusria.findByIii3Typid", query = "SELECT z FROM Zusria z WHERE z.iii3Typid = :iii3Typid"),
    @NamedQuery(name = "Zusria.findByIii4Identyfik", query = "SELECT z FROM Zusria z WHERE z.iii4Identyfik = :iii4Identyfik"),
    @NamedQuery(name = "Zusria.findByIv1Datarozwyg", query = "SELECT z FROM Zusria z WHERE z.iv1Datarozwyg = :iv1Datarozwyg"),
    @NamedQuery(name = "Zusria.findByIv2Kodrozwyg", query = "SELECT z FROM Zusria z WHERE z.iv2Kodrozwyg = :iv2Kodrozwyg"),
    @NamedQuery(name = "Zusria.findByIv3Kodpodprawrozwy", query = "SELECT z FROM Zusria z WHERE z.iv3Kodpodprawrozwy = :iv3Kodpodprawrozwy"),
    @NamedQuery(name = "Zusria.findByIv4Podprawrozwyg", query = "SELECT z FROM Zusria z WHERE z.iv4Podprawrozwyg = :iv4Podprawrozwyg"),
    @NamedQuery(name = "Zusria.findByIv5Stronarozwyg", query = "SELECT z FROM Zusria z WHERE z.iv5Stronarozwyg = :iv5Stronarozwyg"),
    @NamedQuery(name = "Zusria.findByX1StatusPodmiotu", query = "SELECT z FROM Zusria z WHERE z.x1StatusPodmiotu = :x1StatusPodmiotu"),
    @NamedQuery(name = "Zusria.findByXii1Datawypel", query = "SELECT z FROM Zusria z WHERE z.xii1Datawypel = :xii1Datawypel"),
    @NamedQuery(name = "Zusria.findByStatuswr", query = "SELECT z FROM Zusria z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zusria.findByStatuspt", query = "SELECT z FROM Zusria z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zusria.findByInserttmp", query = "SELECT z FROM Zusria z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zusria.findByZnKor", query = "SELECT z FROM Zusria z WHERE z.znKor = :znKor"),
    @NamedQuery(name = "Zusria.findByZnKonw", query = "SELECT z FROM Zusria z WHERE z.znKonw = :znKonw"),
    @NamedQuery(name = "Zusria.findBySeria", query = "SELECT z FROM Zusria z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zusria.findByLPozycji", query = "SELECT z FROM Zusria z WHERE z.lPozycji = :lPozycji"),
    @NamedQuery(name = "Zusria.findByStatuszus", query = "SELECT z FROM Zusria z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zusria.findByStatusKontroli", query = "SELECT z FROM Zusria z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zusria.findByIdPlZusStatus", query = "SELECT z FROM Zusria z WHERE z.idPlZusStatus = :idPlZusStatus"),
    @NamedQuery(name = "Zusria.findByIdUbZusStatus", query = "SELECT z FROM Zusria z WHERE z.idUbZusStatus = :idUbZusStatus")})
public class Zusria implements Serializable {

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
    @Column(name = "I_1_RODZAJ_DOKUMENTU")
    private Character i1RodzajDokumentu;
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
    @Size(max = 31)
    @Column(name = "III_1_NAZWISKO", length = 31)
    private String iii1Nazwisko;
    @Size(max = 22)
    @Column(name = "III_2_IMIEPIERW", length = 22)
    private String iii2Imiepierw;
    @Column(name = "III_3_TYPID")
    private Character iii3Typid;
    @Size(max = 11)
    @Column(name = "III_4_IDENTYFIK", length = 11)
    private String iii4Identyfik;
    @Column(name = "IV_1_DATAROZWYG")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv1Datarozwyg;
    @Size(max = 3)
    @Column(name = "IV_2_KODROZWYG", length = 3)
    private String iv2Kodrozwyg;
    @Size(max = 3)
    @Column(name = "IV_3_KODPODPRAWROZWY", length = 3)
    private String iv3Kodpodprawrozwy;
    @Size(max = 180)
    @Column(name = "IV_4_PODPRAWROZWYG", length = 180)
    private String iv4Podprawrozwyg;
    @Column(name = "IV_5_STRONAROZWYG")
    private Short iv5Stronarozwyg;
    @Column(name = "X_1_STATUS_PODMIOTU")
    private Character x1StatusPodmiotu;
    @Column(name = "XII_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date xii1Datawypel;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ZN_KOR")
    private Character znKor;
    @Column(name = "ZN_KONW")
    private Character znKonw;
    @Column(name = "SERIA")
    private Integer seria;
    @Column(name = "L_POZYCJI")
    private Integer lPozycji;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_PL_ZUS_STATUS")
    private Character idPlZusStatus;
    @Column(name = "ID_UB_ZUS_STATUS")
    private Character idUbZusStatus;

    public Zusria() {
    }

    public Zusria(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zusria(Integer idDokument, int idPlatnik, int idUbezpieczony) {
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

    public Character getI1RodzajDokumentu() {
        return i1RodzajDokumentu;
    }

    public void setI1RodzajDokumentu(Character i1RodzajDokumentu) {
        this.i1RodzajDokumentu = i1RodzajDokumentu;
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

    public String getIii1Nazwisko() {
        return iii1Nazwisko;
    }

    public void setIii1Nazwisko(String iii1Nazwisko) {
        this.iii1Nazwisko = iii1Nazwisko;
    }

    public String getIii2Imiepierw() {
        return iii2Imiepierw;
    }

    public void setIii2Imiepierw(String iii2Imiepierw) {
        this.iii2Imiepierw = iii2Imiepierw;
    }

    public Character getIii3Typid() {
        return iii3Typid;
    }

    public void setIii3Typid(Character iii3Typid) {
        this.iii3Typid = iii3Typid;
    }

    public String getIii4Identyfik() {
        return iii4Identyfik;
    }

    public void setIii4Identyfik(String iii4Identyfik) {
        this.iii4Identyfik = iii4Identyfik;
    }

    public Date getIv1Datarozwyg() {
        return iv1Datarozwyg;
    }

    public void setIv1Datarozwyg(Date iv1Datarozwyg) {
        this.iv1Datarozwyg = iv1Datarozwyg;
    }

    public String getIv2Kodrozwyg() {
        return iv2Kodrozwyg;
    }

    public void setIv2Kodrozwyg(String iv2Kodrozwyg) {
        this.iv2Kodrozwyg = iv2Kodrozwyg;
    }

    public String getIv3Kodpodprawrozwy() {
        return iv3Kodpodprawrozwy;
    }

    public void setIv3Kodpodprawrozwy(String iv3Kodpodprawrozwy) {
        this.iv3Kodpodprawrozwy = iv3Kodpodprawrozwy;
    }

    public String getIv4Podprawrozwyg() {
        return iv4Podprawrozwyg;
    }

    public void setIv4Podprawrozwyg(String iv4Podprawrozwyg) {
        this.iv4Podprawrozwyg = iv4Podprawrozwyg;
    }

    public Short getIv5Stronarozwyg() {
        return iv5Stronarozwyg;
    }

    public void setIv5Stronarozwyg(Short iv5Stronarozwyg) {
        this.iv5Stronarozwyg = iv5Stronarozwyg;
    }

    public Character getX1StatusPodmiotu() {
        return x1StatusPodmiotu;
    }

    public void setX1StatusPodmiotu(Character x1StatusPodmiotu) {
        this.x1StatusPodmiotu = x1StatusPodmiotu;
    }

    public Date getXii1Datawypel() {
        return xii1Datawypel;
    }

    public void setXii1Datawypel(Date xii1Datawypel) {
        this.xii1Datawypel = xii1Datawypel;
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

    public Character getZnKor() {
        return znKor;
    }

    public void setZnKor(Character znKor) {
        this.znKor = znKor;
    }

    public Character getZnKonw() {
        return znKonw;
    }

    public void setZnKonw(Character znKonw) {
        this.znKonw = znKonw;
    }

    public Integer getSeria() {
        return seria;
    }

    public void setSeria(Integer seria) {
        this.seria = seria;
    }

    public Integer getLPozycji() {
        return lPozycji;
    }

    public void setLPozycji(Integer lPozycji) {
        this.lPozycji = lPozycji;
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
        if (!(object instanceof Zusria)) {
            return false;
        }
        Zusria other = (Zusria) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zusria[ idDokument=" + idDokument + " ]";
    }
    
}
