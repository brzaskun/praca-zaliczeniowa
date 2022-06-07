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
@Table(name = "ZUSIWA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusiwa.findAll", query = "SELECT z FROM Zusiwa z"),
    @NamedQuery(name = "Zusiwa.findByIdDokument", query = "SELECT z FROM Zusiwa z WHERE z.idDokument = :idDokument"),
    @NamedQuery(name = "Zusiwa.findByIdPlatnik", query = "SELECT z FROM Zusiwa z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zusiwa.findByI11idinfor", query = "SELECT z FROM Zusiwa z WHERE z.i11idinfor = :i11idinfor"),
    @NamedQuery(name = "Zusiwa.findByI12okrinfor", query = "SELECT z FROM Zusiwa z WHERE z.i12okrinfor = :i12okrinfor"),
    @NamedQuery(name = "Zusiwa.findByI2Kodjednter", query = "SELECT z FROM Zusiwa z WHERE z.i2Kodjednter = :i2Kodjednter"),
    @NamedQuery(name = "Zusiwa.findByI3Datanadania", query = "SELECT z FROM Zusiwa z WHERE z.i3Datanadania = :i3Datanadania"),
    @NamedQuery(name = "Zusiwa.findByI4Nalepkar", query = "SELECT z FROM Zusiwa z WHERE z.i4Nalepkar = :i4Nalepkar"),
    @NamedQuery(name = "Zusiwa.findByI5Datawpl", query = "SELECT z FROM Zusiwa z WHERE z.i5Datawpl = :i5Datawpl"),
    @NamedQuery(name = "Zusiwa.findByI6Znakinrdecpok", query = "SELECT z FROM Zusiwa z WHERE z.i6Znakinrdecpok = :i6Znakinrdecpok"),
    @NamedQuery(name = "Zusiwa.findByIi1Nip", query = "SELECT z FROM Zusiwa z WHERE z.ii1Nip = :ii1Nip"),
    @NamedQuery(name = "Zusiwa.findByIi2Regon", query = "SELECT z FROM Zusiwa z WHERE z.ii2Regon = :ii2Regon"),
    @NamedQuery(name = "Zusiwa.findByIi3Pesel", query = "SELECT z FROM Zusiwa z WHERE z.ii3Pesel = :ii3Pesel"),
    @NamedQuery(name = "Zusiwa.findByIi4Rodzdok", query = "SELECT z FROM Zusiwa z WHERE z.ii4Rodzdok = :ii4Rodzdok"),
    @NamedQuery(name = "Zusiwa.findByIi5Serianrdok", query = "SELECT z FROM Zusiwa z WHERE z.ii5Serianrdok = :ii5Serianrdok"),
    @NamedQuery(name = "Zusiwa.findByIi6Nazwaskr", query = "SELECT z FROM Zusiwa z WHERE z.ii6Nazwaskr = :ii6Nazwaskr"),
    @NamedQuery(name = "Zusiwa.findByIi7Nazwisko", query = "SELECT z FROM Zusiwa z WHERE z.ii7Nazwisko = :ii7Nazwisko"),
    @NamedQuery(name = "Zusiwa.findByIi8Imiepierw", query = "SELECT z FROM Zusiwa z WHERE z.ii8Imiepierw = :ii8Imiepierw"),
    @NamedQuery(name = "Zusiwa.findByIi9Dataurodz", query = "SELECT z FROM Zusiwa z WHERE z.ii9Dataurodz = :ii9Dataurodz"),
    @NamedQuery(name = "Zusiwa.findByIii1Lubezp", query = "SELECT z FROM Zusiwa z WHERE z.iii1Lubezp = :iii1Lubezp"),
    @NamedQuery(name = "Zusiwa.findByIv1Rdzialpkd", query = "SELECT z FROM Zusiwa z WHERE z.iv1Rdzialpkd = :iv1Rdzialpkd"),
    @NamedQuery(name = "Zusiwa.findByIv2Lpszkwyp", query = "SELECT z FROM Zusiwa z WHERE z.iv2Lpszkwyp = :iv2Lpszkwyp"),
    @NamedQuery(name = "Zusiwa.findByIv3Lpszwypsmc", query = "SELECT z FROM Zusiwa z WHERE z.iv3Lpszwypsmc = :iv3Lpszwypsmc"),
    @NamedQuery(name = "Zusiwa.findByIv4Lztrwarzag", query = "SELECT z FROM Zusiwa z WHERE z.iv4Lztrwarzag = :iv4Lztrwarzag"),
    @NamedQuery(name = "Zusiwa.findByV1Kodpocztowy", query = "SELECT z FROM Zusiwa z WHERE z.v1Kodpocztowy = :v1Kodpocztowy"),
    @NamedQuery(name = "Zusiwa.findByV2Miejscowosc", query = "SELECT z FROM Zusiwa z WHERE z.v2Miejscowosc = :v2Miejscowosc"),
    @NamedQuery(name = "Zusiwa.findByV3Ulica", query = "SELECT z FROM Zusiwa z WHERE z.v3Ulica = :v3Ulica"),
    @NamedQuery(name = "Zusiwa.findByV5Numerlokalu", query = "SELECT z FROM Zusiwa z WHERE z.v5Numerlokalu = :v5Numerlokalu"),
    @NamedQuery(name = "Zusiwa.findByV4Numerdomu", query = "SELECT z FROM Zusiwa z WHERE z.v4Numerdomu = :v4Numerdomu"),
    @NamedQuery(name = "Zusiwa.findByV6Teldoteletr", query = "SELECT z FROM Zusiwa z WHERE z.v6Teldoteletr = :v6Teldoteletr"),
    @NamedQuery(name = "Zusiwa.findByV7Skrpocztowa", query = "SELECT z FROM Zusiwa z WHERE z.v7Skrpocztowa = :v7Skrpocztowa"),
    @NamedQuery(name = "Zusiwa.findByV8Telefon", query = "SELECT z FROM Zusiwa z WHERE z.v8Telefon = :v8Telefon"),
    @NamedQuery(name = "Zusiwa.findByV9Faks", query = "SELECT z FROM Zusiwa z WHERE z.v9Faks = :v9Faks"),
    @NamedQuery(name = "Zusiwa.findByV10Adrpocztyel", query = "SELECT z FROM Zusiwa z WHERE z.v10Adrpocztyel = :v10Adrpocztyel"),
    @NamedQuery(name = "Zusiwa.findByVi1Datawypel", query = "SELECT z FROM Zusiwa z WHERE z.vi1Datawypel = :vi1Datawypel"),
    @NamedQuery(name = "Zusiwa.findByStatuswr", query = "SELECT z FROM Zusiwa z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zusiwa.findByStatuspt", query = "SELECT z FROM Zusiwa z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zusiwa.findByInserttmp", query = "SELECT z FROM Zusiwa z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zusiwa.findBySeria", query = "SELECT z FROM Zusiwa z WHERE z.seria = :seria"),
    @NamedQuery(name = "Zusiwa.findByStatuszus", query = "SELECT z FROM Zusiwa z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zusiwa.findByStatusKontroli", query = "SELECT z FROM Zusiwa z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zusiwa.findByIdPlZusStatus", query = "SELECT z FROM Zusiwa z WHERE z.idPlZusStatus = :idPlZusStatus")})
public class Zusiwa implements Serializable {

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
    @Column(name = "I_1_1IDINFOR", length = 2)
    private String i11idinfor;
    @Size(max = 4)
    @Column(name = "I_1_2OKRINFOR", length = 4)
    private String i12okrinfor;
    @Size(max = 6)
    @Column(name = "I_2_KODJEDNTER", length = 6)
    private String i2Kodjednter;
    @Column(name = "I_3_DATANADANIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i3Datanadania;
    @Size(max = 20)
    @Column(name = "I_4_NALEPKAR", length = 20)
    private String i4Nalepkar;
    @Column(name = "I_5_DATAWPL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date i5Datawpl;
    @Size(max = 12)
    @Column(name = "I_6_ZNAKINRDECPOK", length = 12)
    private String i6Znakinrdecpok;
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
    @Column(name = "III_1_LUBEZP")
    private Integer iii1Lubezp;
    @Size(max = 5)
    @Column(name = "IV_1_RDZIALPKD", length = 5)
    private String iv1Rdzialpkd;
    @Column(name = "IV_2_LPSZKWYP")
    private Integer iv2Lpszkwyp;
    @Column(name = "IV_3_LPSZWYPSMC")
    private Integer iv3Lpszwypsmc;
    @Column(name = "IV_4_LZTRWARZAG")
    private Integer iv4Lztrwarzag;
    @Size(max = 5)
    @Column(name = "V_1_KODPOCZTOWY", length = 5)
    private String v1Kodpocztowy;
    @Size(max = 26)
    @Column(name = "V_2_MIEJSCOWOSC", length = 26)
    private String v2Miejscowosc;
    @Size(max = 30)
    @Column(name = "V_3_ULICA", length = 30)
    private String v3Ulica;
    @Size(max = 7)
    @Column(name = "V_5_NUMERLOKALU", length = 7)
    private String v5Numerlokalu;
    @Size(max = 7)
    @Column(name = "V_4_NUMERDOMU", length = 7)
    private String v4Numerdomu;
    @Size(max = 12)
    @Column(name = "V_6_TELDOTELETR", length = 12)
    private String v6Teldoteletr;
    @Size(max = 5)
    @Column(name = "V_7_SKRPOCZTOWA", length = 5)
    private String v7Skrpocztowa;
    @Size(max = 12)
    @Column(name = "V_8_TELEFON", length = 12)
    private String v8Telefon;
    @Size(max = 12)
    @Column(name = "V_9_FAKS", length = 12)
    private String v9Faks;
    @Size(max = 30)
    @Column(name = "V_10_ADRPOCZTYEL", length = 30)
    private String v10Adrpocztyel;
    @Column(name = "VI_1_DATAWYPEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vi1Datawypel;
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

    public Zusiwa() {
    }

    public Zusiwa(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Zusiwa(Integer idDokument, int idPlatnik) {
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

    public String getI11idinfor() {
        return i11idinfor;
    }

    public void setI11idinfor(String i11idinfor) {
        this.i11idinfor = i11idinfor;
    }

    public String getI12okrinfor() {
        return i12okrinfor;
    }

    public void setI12okrinfor(String i12okrinfor) {
        this.i12okrinfor = i12okrinfor;
    }

    public String getI2Kodjednter() {
        return i2Kodjednter;
    }

    public void setI2Kodjednter(String i2Kodjednter) {
        this.i2Kodjednter = i2Kodjednter;
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

    public Date getI5Datawpl() {
        return i5Datawpl;
    }

    public void setI5Datawpl(Date i5Datawpl) {
        this.i5Datawpl = i5Datawpl;
    }

    public String getI6Znakinrdecpok() {
        return i6Znakinrdecpok;
    }

    public void setI6Znakinrdecpok(String i6Znakinrdecpok) {
        this.i6Znakinrdecpok = i6Znakinrdecpok;
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

    public Integer getIii1Lubezp() {
        return iii1Lubezp;
    }

    public void setIii1Lubezp(Integer iii1Lubezp) {
        this.iii1Lubezp = iii1Lubezp;
    }

    public String getIv1Rdzialpkd() {
        return iv1Rdzialpkd;
    }

    public void setIv1Rdzialpkd(String iv1Rdzialpkd) {
        this.iv1Rdzialpkd = iv1Rdzialpkd;
    }

    public Integer getIv2Lpszkwyp() {
        return iv2Lpszkwyp;
    }

    public void setIv2Lpszkwyp(Integer iv2Lpszkwyp) {
        this.iv2Lpszkwyp = iv2Lpszkwyp;
    }

    public Integer getIv3Lpszwypsmc() {
        return iv3Lpszwypsmc;
    }

    public void setIv3Lpszwypsmc(Integer iv3Lpszwypsmc) {
        this.iv3Lpszwypsmc = iv3Lpszwypsmc;
    }

    public Integer getIv4Lztrwarzag() {
        return iv4Lztrwarzag;
    }

    public void setIv4Lztrwarzag(Integer iv4Lztrwarzag) {
        this.iv4Lztrwarzag = iv4Lztrwarzag;
    }

    public String getV1Kodpocztowy() {
        return v1Kodpocztowy;
    }

    public void setV1Kodpocztowy(String v1Kodpocztowy) {
        this.v1Kodpocztowy = v1Kodpocztowy;
    }

    public String getV2Miejscowosc() {
        return v2Miejscowosc;
    }

    public void setV2Miejscowosc(String v2Miejscowosc) {
        this.v2Miejscowosc = v2Miejscowosc;
    }

    public String getV3Ulica() {
        return v3Ulica;
    }

    public void setV3Ulica(String v3Ulica) {
        this.v3Ulica = v3Ulica;
    }

    public String getV5Numerlokalu() {
        return v5Numerlokalu;
    }

    public void setV5Numerlokalu(String v5Numerlokalu) {
        this.v5Numerlokalu = v5Numerlokalu;
    }

    public String getV4Numerdomu() {
        return v4Numerdomu;
    }

    public void setV4Numerdomu(String v4Numerdomu) {
        this.v4Numerdomu = v4Numerdomu;
    }

    public String getV6Teldoteletr() {
        return v6Teldoteletr;
    }

    public void setV6Teldoteletr(String v6Teldoteletr) {
        this.v6Teldoteletr = v6Teldoteletr;
    }

    public String getV7Skrpocztowa() {
        return v7Skrpocztowa;
    }

    public void setV7Skrpocztowa(String v7Skrpocztowa) {
        this.v7Skrpocztowa = v7Skrpocztowa;
    }

    public String getV8Telefon() {
        return v8Telefon;
    }

    public void setV8Telefon(String v8Telefon) {
        this.v8Telefon = v8Telefon;
    }

    public String getV9Faks() {
        return v9Faks;
    }

    public void setV9Faks(String v9Faks) {
        this.v9Faks = v9Faks;
    }

    public String getV10Adrpocztyel() {
        return v10Adrpocztyel;
    }

    public void setV10Adrpocztyel(String v10Adrpocztyel) {
        this.v10Adrpocztyel = v10Adrpocztyel;
    }

    public Date getVi1Datawypel() {
        return vi1Datawypel;
    }

    public void setVi1Datawypel(Date vi1Datawypel) {
        this.vi1Datawypel = vi1Datawypel;
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
        if (!(object instanceof Zusiwa)) {
            return false;
        }
        Zusiwa other = (Zusiwa) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zusiwa[ idDokument=" + idDokument + " ]";
    }
    
}
