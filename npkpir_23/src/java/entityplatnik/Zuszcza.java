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
@Table(name = "ZUSZCZA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zuszcza.findAll", query = "SELECT z FROM Zuszcza z"),
    @NamedQuery(name = "Zuszcza.findByIdDokument", query = "SELECT z FROM Zuszcza z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zuszcza.findByIdPlatnik", query = "SELECT z FROM Zuszcza z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zuszcza.findByIdUbezpieczony", query = "SELECT z FROM Zuszcza z WHERE z.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "Zuszcza.findByI1Datanadania", query = "SELECT z FROM Zuszcza z WHERE z.i1Datanadania = :i1Datanadania"),
    @NamedQuery(name = "Zuszcza.findByI2Nalepkar", query = "SELECT z FROM Zuszcza z WHERE z.i2Nalepkar = :i2Nalepkar"),
    @NamedQuery(name = "Zuszcza.findByIi1Nip", query = "SELECT z FROM Zuszcza z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zuszcza.findByIi2Regon", query = "SELECT z FROM Zuszcza z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zuszcza.findByIi3Pesel", query = "SELECT z FROM Zuszcza z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zuszcza.findByIi4Rodzdok", query = "SELECT z FROM Zuszcza z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zuszcza.findByIi5Serianrdok", query = "SELECT z FROM Zuszcza z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zuszcza.findByIi6Nazwaskr", query = "SELECT z FROM Zuszcza z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zuszcza.findByIi7Nazwisko", query = "SELECT z FROM Zuszcza z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zuszcza.findByIi8Imiepierw", query = "SELECT z FROM Zuszcza z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zuszcza.findByIi9Dataurodz", query = "SELECT z FROM Zuszcza z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zuszcza.findByIii1Pesel", query = "SELECT z FROM Zuszcza z WHERE z.iii1Pesel = :iii1Pesel"),
    @NamedQuery(name = "Zuszcza.findByIii2Nip", query = "SELECT z FROM Zuszcza z WHERE z.iii2Nip = :iii2Nip"),
    @NamedQuery(name = "Zuszcza.findByIii3Rodzdok", query = "SELECT z FROM Zuszcza z WHERE z.iii3Rodzdok = :iii3Rodzdok"),
    @NamedQuery(name = "Zuszcza.findByIii4Serianrdok", query = "SELECT z FROM Zuszcza z WHERE z.iii4Serianrdok = :iii4Serianrdok"),
    @NamedQuery(name = "Zuszcza.findByIii5Nazwisko", query = "SELECT z FROM Zuszcza z WHERE z.iii5Nazwisko = :iii5Nazwisko"),
    @NamedQuery(name = "Zuszcza.findByIii6Imiepierw", query = "SELECT z FROM Zuszcza z WHERE z.iii6Imiepierw = :iii6Imiepierw"),
    @NamedQuery(name = "Zuszcza.findByIii7Dataurodz", query = "SELECT z FROM Zuszcza z WHERE z.iii7Dataurodz = :iii7Dataurodz"),
    @NamedQuery(name = "Zuszcza.findByViii1Datawypel", query = "SELECT z FROM Zuszcza z WHERE z.viii1Datawypel = :viii1Datawypel"),
    @NamedQuery(name = "Zuszcza.findByStatuswr", query = "SELECT z FROM Zuszcza z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zuszcza.findByStatuspt", query = "SELECT z FROM Zuszcza z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zuszcza.findByInserttmp", query = "SELECT z FROM Zuszcza z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zuszcza.findBySeria", query = "SELECT z FROM Zuszcza z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zuszcza.findByLPozycji", query = "SELECT z FROM Zuszcza z WHERE z.lPozycji = :lPozycji")})
public class Zuszcza implements Serializable {

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
    @Column(name = "I_1_DATANADANIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i1Datanadania;
    @Size(max = 20)
    @Column(name = "I_2_NALEPKAR", length = 20)
    private String i2Nalepkar;
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
    @Column(name = "VIII_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date viii1Datawypel;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "SERIA")
    private Integer seria;
    @Column(name = "L_POZYCJI")
    private Integer lPozycji;

    public Zuszcza() {
    }

    public Zuszcza(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zuszcza(Integer idDokument, int idPlatnik, int idUbezpieczony) {
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

    public Date getI1Datanadania() {
        return i1Datanadania;
    }

    public void setI1Datanadania(Date i1Datanadania) {
        this.i1Datanadania = i1Datanadania;
    }

    public String getI2Nalepkar() {
        return i2Nalepkar;
    }

    public void setI2Nalepkar(String i2Nalepkar) {
        this.i2Nalepkar = i2Nalepkar;
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

    public Date getViii1Datawypel() {
        return viii1Datawypel;
    }

    public void setViii1Datawypel(Date viii1Datawypel) {
        this.viii1Datawypel = viii1Datawypel;
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

    public Integer getLPozycji() {
        return lPozycji;
    }

    public void setLPozycji(Integer lPozycji) {
        this.lPozycji = lPozycji;
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
        if (!(object instanceof Zuszcza)) {
            return false;
        }
        Zuszcza other = (Zuszcza) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zuszcza[ idDokument=" + idDokument + " ]";
    }
    
}
