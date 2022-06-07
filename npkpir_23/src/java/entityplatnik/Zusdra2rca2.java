/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "ZUSDRA2RCA2")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusdra2rca2.findAll", query = "SELECT z FROM Zusdra2rca2 z"),
    @NamedQuery(name = "Zusdra2rca2.findByIdDokument", query = "SELECT z FROM Zusdra2rca2 z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zusdra2rca2.findByIdPlatnik", query = "SELECT z FROM Zusdra2rca2 z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zusdra2rca2.findByI1Identyfikator", query = "SELECT z FROM Zusdra2rca2 z WHERE z.i1Identyfikator = :i1Identyfikator"),
    @NamedQuery(name = "Zusdra2rca2.findByI2Rok", query = "SELECT z FROM Zusdra2rca2 z WHERE z.i2Rok = :i2Rok"),
    @NamedQuery(name = "Zusdra2rca2.findByI3Datanadania", query = "SELECT z FROM Zusdra2rca2 z WHERE z.i3Datanadania = :i3Datanadania"),
    @NamedQuery(name = "Zusdra2rca2.findByI4Nalepkar", query = "SELECT z FROM Zusdra2rca2 z WHERE z.i4Nalepkar = :i4Nalepkar"),
    @NamedQuery(name = "Zusdra2rca2.findByI5Znakinrdecpok", query = "SELECT z FROM Zusdra2rca2 z WHERE z.i5Znakinrdecpok = :i5Znakinrdecpok"),
    @NamedQuery(name = "Zusdra2rca2.findByIi1Nip", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zusdra2rca2.findByIi2Regon", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zusdra2rca2.findByIi3Pesel", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zusdra2rca2.findByIi4Rodzdok", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zusdra2rca2.findByIi5Serianrdok", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zusdra2rca2.findByIi6Nazwaskr", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zusdra2rca2.findByIi7Nazwisko", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zusdra2rca2.findByIi8Imiepierw", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zusdra2rca2.findByIi9Dataurodz", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zusdra2rca2.findByIii1Przychod", query = "SELECT z FROM Zusdra2rca2 z WHERE z.iii1Przychod = :iii1Przychod"),
    @NamedQuery(name = "Zusdra2rca2.findByIii2Podstawa", query = "SELECT z FROM Zusdra2rca2 z WHERE z.iii2Podstawa = :iii2Podstawa"),
    @NamedQuery(name = "Zusdra2rca2.findByIv1Datawypeln", query = "SELECT z FROM Zusdra2rca2 z WHERE z.iv1Datawypeln = :iv1Datawypeln"),
    @NamedQuery(name = "Zusdra2rca2.findByTypDok", query = "SELECT z FROM Zusdra2rca2 z WHERE z.typDok = :typDok"),
    @NamedQuery(name = "Zusdra2rca2.findByStatuswr", query = "SELECT z FROM Zusdra2rca2 z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zusdra2rca2.findByStatuspt", query = "SELECT z FROM Zusdra2rca2 z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zusdra2rca2.findByInserttmp", query = "SELECT z FROM Zusdra2rca2 z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zusdra2rca2.findBySeria", query = "SELECT z FROM Zusdra2rca2 z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zusdra2rca2.findByStatuszus", query = "SELECT z FROM Zusdra2rca2 z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zusdra2rca2.findByStatusKontroli", query = "SELECT z FROM Zusdra2rca2 z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zusdra2rca2.findByIdPlZusStatus", query = "SELECT z FROM Zusdra2rca2 z WHERE z.idPlZusStatus = :idPlZusStatus"),
    @NamedQuery(name = "Zusdra2rca2.findByIiiDochod", query = "SELECT z FROM Zusdra2rca2 z WHERE z.iiiDochod = :iiiDochod"),
    @NamedQuery(name = "Zusdra2rca2.findByIvPrzychodKarta", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ivPrzychodKarta = :ivPrzychodKarta"),
    @NamedQuery(name = "Zusdra2rca2.findByIvDochodKarta", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ivDochodKarta = :ivDochodKarta"),
    @NamedQuery(name = "Zusdra2rca2.findByIvPrzychodRyczalt", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ivPrzychodRyczalt = :ivPrzychodRyczalt"),
    @NamedQuery(name = "Zusdra2rca2.findByIvDochodRyczalt", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ivDochodRyczalt = :ivDochodRyczalt"),
    @NamedQuery(name = "Zusdra2rca2.findByIvPrzychodOgolne", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ivPrzychodOgolne = :ivPrzychodOgolne"),
    @NamedQuery(name = "Zusdra2rca2.findByIvDochodOgolne", query = "SELECT z FROM Zusdra2rca2 z WHERE z.ivDochodOgolne = :ivDochodOgolne")})
public class Zusdra2rca2 implements Serializable {

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
    @Size(max = 2)
    @Column(name = "I_1_IDENTYFIKATOR", length = 2)
    private String i1Identyfikator;
    @Size(max = 4)
    @Column(name = "I_2_ROK", length = 4)
    private String i2Rok;
    @Column(name = "I_3_DATANADANIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i3Datanadania;
    @Size(max = 20)
    @Column(name = "I_4_NALEPKAR", length = 20)
    private String i4Nalepkar;
    @Size(max = 12)
    @Column(name = "I_5_ZNAKINRDECPOK", length = 12)
    private String i5Znakinrdecpok;
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_1_PRZYCHOD", precision = 10, scale = 2)
    private BigDecimal iii1Przychod;
    @Column(name = "III_2_PODSTAWA", precision = 10, scale = 2)
    private BigDecimal iii2Podstawa;
    @Column(name = "IV_1_DATAWYPELN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iv1Datawypeln;
    @Size(max = 3)
    @Column(name = "TYP_DOK", length = 3)
    private String typDok;
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
    @Column(name = "III_DOCHOD", precision = 10, scale = 2)
    private BigDecimal iiiDochod;
    @Column(name = "IV_PRZYCHOD_KARTA", precision = 10, scale = 2)
    private BigDecimal ivPrzychodKarta;
    @Column(name = "IV_DOCHOD_KARTA", precision = 10, scale = 2)
    private BigDecimal ivDochodKarta;
    @Column(name = "IV_PRZYCHOD_RYCZALT", precision = 10, scale = 2)
    private BigDecimal ivPrzychodRyczalt;
    @Column(name = "IV_DOCHOD_RYCZALT", precision = 10, scale = 2)
    private BigDecimal ivDochodRyczalt;
    @Column(name = "IV_PRZYCHOD_OGOLNE", precision = 10, scale = 2)
    private BigDecimal ivPrzychodOgolne;
    @Column(name = "IV_DOCHOD_OGOLNE", precision = 10, scale = 2)
    private BigDecimal ivDochodOgolne;

    public Zusdra2rca2() {
    }

    public Zusdra2rca2(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zusdra2rca2(Integer idDokument, int idPlatnik) {
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

    public String getI1Identyfikator() {
        return i1Identyfikator;
    }

    public void setI1Identyfikator(String i1Identyfikator) {
        this.i1Identyfikator = i1Identyfikator;
    }

    public String getI2Rok() {
        return i2Rok;
    }

    public void setI2Rok(String i2Rok) {
        this.i2Rok = i2Rok;
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

    public String getI5Znakinrdecpok() {
        return i5Znakinrdecpok;
    }

    public void setI5Znakinrdecpok(String i5Znakinrdecpok) {
        this.i5Znakinrdecpok = i5Znakinrdecpok;
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

    public BigDecimal getIii1Przychod() {
        return iii1Przychod;
    }

    public void setIii1Przychod(BigDecimal iii1Przychod) {
        this.iii1Przychod = iii1Przychod;
    }

    public BigDecimal getIii2Podstawa() {
        return iii2Podstawa;
    }

    public void setIii2Podstawa(BigDecimal iii2Podstawa) {
        this.iii2Podstawa = iii2Podstawa;
    }

    public Date getIv1Datawypeln() {
        return iv1Datawypeln;
    }

    public void setIv1Datawypeln(Date iv1Datawypeln) {
        this.iv1Datawypeln = iv1Datawypeln;
    }

    public String getTypDok() {
        return typDok;
    }

    public void setTypDok(String typDok) {
        this.typDok = typDok;
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

    public BigDecimal getIiiDochod() {
        return iiiDochod;
    }

    public void setIiiDochod(BigDecimal iiiDochod) {
        this.iiiDochod = iiiDochod;
    }

    public BigDecimal getIvPrzychodKarta() {
        return ivPrzychodKarta;
    }

    public void setIvPrzychodKarta(BigDecimal ivPrzychodKarta) {
        this.ivPrzychodKarta = ivPrzychodKarta;
    }

    public BigDecimal getIvDochodKarta() {
        return ivDochodKarta;
    }

    public void setIvDochodKarta(BigDecimal ivDochodKarta) {
        this.ivDochodKarta = ivDochodKarta;
    }

    public BigDecimal getIvPrzychodRyczalt() {
        return ivPrzychodRyczalt;
    }

    public void setIvPrzychodRyczalt(BigDecimal ivPrzychodRyczalt) {
        this.ivPrzychodRyczalt = ivPrzychodRyczalt;
    }

    public BigDecimal getIvDochodRyczalt() {
        return ivDochodRyczalt;
    }

    public void setIvDochodRyczalt(BigDecimal ivDochodRyczalt) {
        this.ivDochodRyczalt = ivDochodRyczalt;
    }

    public BigDecimal getIvPrzychodOgolne() {
        return ivPrzychodOgolne;
    }

    public void setIvPrzychodOgolne(BigDecimal ivPrzychodOgolne) {
        this.ivPrzychodOgolne = ivPrzychodOgolne;
    }

    public BigDecimal getIvDochodOgolne() {
        return ivDochodOgolne;
    }

    public void setIvDochodOgolne(BigDecimal ivDochodOgolne) {
        this.ivDochodOgolne = ivDochodOgolne;
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
        if (!(object instanceof Zusdra2rca2)) {
            return false;
        }
        Zusdra2rca2 other = (Zusdra2rca2) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zusdra2rca2[ idDokument=" + idDokument + " ]";
    }
    
}
