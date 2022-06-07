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
@Table(name = "ZUSZBA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zuszba.findAll", query = "SELECT z FROM Zuszba z"),
    @NamedQuery(name = "Zuszba.findByIdDokument", query = "SELECT z FROM Zuszba z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zuszba.findByIdPlatnik", query = "SELECT z FROM Zuszba z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zuszba.findByI1Datanadania", query = "SELECT z FROM Zuszba z WHERE z.i1Datanadania = :i1Datanadania"),
    @NamedQuery(name = "Zuszba.findByI2Nalepkar", query = "SELECT z FROM Zuszba z WHERE z.i2Nalepkar = :i2Nalepkar"),
    @NamedQuery(name = "Zuszba.findByIi1Nip", query = "SELECT z FROM Zuszba z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zuszba.findByIi2Regon", query = "SELECT z FROM Zuszba z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zuszba.findByIi3Pesel", query = "SELECT z FROM Zuszba z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zuszba.findByIi4Rodzdok", query = "SELECT z FROM Zuszba z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zuszba.findByIi5Serianrdok", query = "SELECT z FROM Zuszba z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zuszba.findByIi6Nazwaskr", query = "SELECT z FROM Zuszba z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zuszba.findByIi7Nazwisko", query = "SELECT z FROM Zuszba z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zuszba.findByIi8Imiepierw", query = "SELECT z FROM Zuszba z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zuszba.findByIi9Dataurodz", query = "SELECT z FROM Zuszba z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zuszba.findByIx1Datawypel", query = "SELECT z FROM Zuszba z WHERE z.ix1Datawypel = :ix1Datawypel"),
    @NamedQuery(name = "Zuszba.findByStatuswr", query = "SELECT z FROM Zuszba z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zuszba.findByStatuspt", query = "SELECT z FROM Zuszba z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zuszba.findByInserttmp", query = "SELECT z FROM Zuszba z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zuszba.findBySeria", query = "SELECT z FROM Zuszba z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zuszba.findByLPozycji", query = "SELECT z FROM Zuszba z WHERE z.lPozycji = :lPozycji"),
    @NamedQuery(name = "Zuszba.findByStatuszus", query = "SELECT z FROM Zuszba z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zuszba.findByStatusKontroli", query = "SELECT z FROM Zuszba z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zuszba.findByIdPlZusStatus", query = "SELECT z FROM Zuszba z WHERE z.idPlZusStatus = :idPlZusStatus")})
public class Zuszba implements Serializable {

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
    @Column(name = "IX_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ix1Datawypel;
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
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_PL_ZUS_STATUS")
    private Character idPlZusStatus;

    public Zuszba() {
    }

    public Zuszba(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zuszba(Integer idDokument, int idPlatnik) {
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

    public Date getIx1Datawypel() {
        return ix1Datawypel;
    }

    public void setIx1Datawypel(Date ix1Datawypel) {
        this.ix1Datawypel = ix1Datawypel;
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
        if (!(object instanceof Zuszba)) {
            return false;
        }
        Zuszba other = (Zuszba) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zuszba[ idDokument=" + idDokument + " ]";
    }
    
}
