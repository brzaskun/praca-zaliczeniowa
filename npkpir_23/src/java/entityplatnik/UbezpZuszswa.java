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
@Table(name = "UBEZP_ZUSZSWA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpZuszswa.findAll", query = "SELECT u FROM UbezpZuszswa u"),
    @NamedQuery(name = "UbezpZuszswa.findByIdDokument", query = "SELECT u FROM UbezpZuszswa u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpZuszswa.findByIdPlatnik", query = "SELECT u FROM UbezpZuszswa u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpZuszswa.findByIdDokNad", query = "SELECT u FROM UbezpZuszswa u WHERE u.idDokNad = :idDokNad"),
    @NamedQuery(name = "UbezpZuszswa.findByIdUbezpieczony", query = "SELECT u FROM UbezpZuszswa u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiA1Zgldaprszw", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiA1Zgldaprszw = :iiiA1Zgldaprszw"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiA2Zglkordaprszw", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiA2Zglkordaprszw = :iiiA2Zglkordaprszw"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiA3Pesel", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiA3Pesel = :iiiA3Pesel"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiA4Nip", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiA4Nip = :iiiA4Nip"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiA5Rodzdok", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiA5Rodzdok = :iiiA5Rodzdok"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiA6Serianrdok", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiA6Serianrdok = :iiiA6Serianrdok"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiA7Nazwisko", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiA7Nazwisko = :iiiA7Nazwisko"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiA8Imiepierw", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiA8Imiepierw = :iiiA8Imiepierw"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiA9Dataurodz", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiA9Dataurodz = :iiiA9Dataurodz"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB11kodtytub", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB11kodtytub = :iiiB11kodtytub"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB12prdoem", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB12prdoem = :iiiB12prdoem"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB13stniep", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB13stniep = :iiiB13stniep"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB2Kodprszw", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB2Kodprszw = :iiiB2Kodprszw"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB3Kodprszwod", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB3Kodprszwod = :iiiB3Kodprszwod"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB4Kodprszwdo", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB4Kodprszwdo = :iiiB4Kodprszwdo"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB51wymczprl", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB51wymczprl = :iiiB51wymczprl"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB52wymczprm", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB52wymczprm = :iiiB52wymczprm"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB6Kodprszw", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB6Kodprszw = :iiiB6Kodprszw"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB7Kodprszwod", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB7Kodprszwod = :iiiB7Kodprszwod"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB8Kodprszwdo", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB8Kodprszwdo = :iiiB8Kodprszwdo"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB91wymczprl", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB91wymczprl = :iiiB91wymczprl"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB92wymczprm", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB92wymczprm = :iiiB92wymczprm"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB10Kodprszw", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB10Kodprszw = :iiiB10Kodprszw"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB11Kodprszwod", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB11Kodprszwod = :iiiB11Kodprszwod"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB12Kodprszwdo", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB12Kodprszwdo = :iiiB12Kodprszwdo"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB131wymczprl", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB131wymczprl = :iiiB131wymczprl"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB132wymczprm", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB132wymczprm = :iiiB132wymczprm"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB14Kodprszw", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB14Kodprszw = :iiiB14Kodprszw"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB15Kodprszwod", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB15Kodprszwod = :iiiB15Kodprszwod"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB16Kodprszwdo", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB16Kodprszwdo = :iiiB16Kodprszwdo"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB171wymczprl", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB171wymczprl = :iiiB171wymczprl"),
    @NamedQuery(name = "UbezpZuszswa.findByIiiB172wymczprm", query = "SELECT u FROM UbezpZuszswa u WHERE u.iiiB172wymczprm = :iiiB172wymczprm"),
    @NamedQuery(name = "UbezpZuszswa.findByStatuswr", query = "SELECT u FROM UbezpZuszswa u WHERE u.statuswr = :statuswr"),
    @NamedQuery(name = "UbezpZuszswa.findByStatuspt", query = "SELECT u FROM UbezpZuszswa u WHERE u.statuspt = :statuspt"),
    @NamedQuery(name = "UbezpZuszswa.findByInserttmp", query = "SELECT u FROM UbezpZuszswa u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpZuszswa.findByStatusKontroli", query = "SELECT u FROM UbezpZuszswa u WHERE u.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "UbezpZuszswa.findByIdUbZusStatus", query = "SELECT u FROM UbezpZuszswa u WHERE u.idUbZusStatus = :idUbZusStatus"),
    @NamedQuery(name = "UbezpZuszswa.findByNrBlok", query = "SELECT u FROM UbezpZuszswa u WHERE u.nrBlok = :nrBlok")})
public class UbezpZuszswa implements Serializable {

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
    @Column(name = "ID_DOK_NAD", nullable = false)
    private int idDokNad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
    @Column(name = "III_A_1_ZGLDAPRSZW")
    private Character iiiA1Zgldaprszw;
    @Column(name = "III_A_2_ZGLKORDAPRSZW")
    private Character iiiA2Zglkordaprszw;
    @Size(max = 11)
    @Column(name = "III_A_3_PESEL", length = 11)
    private String iiiA3Pesel;
    @Size(max = 10)
    @Column(name = "III_A_4_NIP", length = 10)
    private String iiiA4Nip;
    @Column(name = "III_A_5_RODZDOK")
    private Character iiiA5Rodzdok;
    @Size(max = 9)
    @Column(name = "III_A_6_SERIANRDOK", length = 9)
    private String iiiA6Serianrdok;
    @Size(max = 31)
    @Column(name = "III_A_7_NAZWISKO", length = 31)
    private String iiiA7Nazwisko;
    @Size(max = 22)
    @Column(name = "III_A_8_IMIEPIERW", length = 22)
    private String iiiA8Imiepierw;
    @Column(name = "III_A_9_DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiA9Dataurodz;
    @Size(max = 4)
    @Column(name = "III_B_1_1KODTYTUB", length = 4)
    private String iiiB11kodtytub;
    @Column(name = "III_B_1_2PRDOEM")
    private Character iiiB12prdoem;
    @Column(name = "III_B_1_3STNIEP")
    private Character iiiB13stniep;
    @Size(max = 3)
    @Column(name = "III_B_2_KODPRSZW", length = 3)
    private String iiiB2Kodprszw;
    @Column(name = "III_B_3_KODPRSZWOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB3Kodprszwod;
    @Column(name = "III_B_4_KODPRSZWDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB4Kodprszwdo;
    @Size(max = 3)
    @Column(name = "III_B_5_1WYMCZPRL", length = 3)
    private String iiiB51wymczprl;
    @Size(max = 3)
    @Column(name = "III_B_5_2WYMCZPRM", length = 3)
    private String iiiB52wymczprm;
    @Size(max = 3)
    @Column(name = "III_B_6_KODPRSZW", length = 3)
    private String iiiB6Kodprszw;
    @Column(name = "III_B_7_KODPRSZWOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB7Kodprszwod;
    @Column(name = "III_B_8_KODPRSZWDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB8Kodprszwdo;
    @Size(max = 3)
    @Column(name = "III_B_9_1WYMCZPRL", length = 3)
    private String iiiB91wymczprl;
    @Size(max = 3)
    @Column(name = "III_B_9_2WYMCZPRM", length = 3)
    private String iiiB92wymczprm;
    @Size(max = 3)
    @Column(name = "III_B_10_KODPRSZW", length = 3)
    private String iiiB10Kodprszw;
    @Column(name = "III_B_11_KODPRSZWOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB11Kodprszwod;
    @Column(name = "III_B_12_KODPRSZWDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB12Kodprszwdo;
    @Size(max = 3)
    @Column(name = "III_B_13_1WYMCZPRL", length = 3)
    private String iiiB131wymczprl;
    @Size(max = 3)
    @Column(name = "III_B_13_2WYMCZPRM", length = 3)
    private String iiiB132wymczprm;
    @Size(max = 3)
    @Column(name = "III_B_14_KODPRSZW", length = 3)
    private String iiiB14Kodprszw;
    @Column(name = "III_B_15_KODPRSZWOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB15Kodprszwod;
    @Column(name = "III_B_16_KODPRSZWDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB16Kodprszwdo;
    @Size(max = 3)
    @Column(name = "III_B_17_1WYMCZPRL", length = 3)
    private String iiiB171wymczprl;
    @Size(max = 3)
    @Column(name = "III_B_17_2WYMCZPRM", length = 3)
    private String iiiB172wymczprm;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_UB_ZUS_STATUS")
    private Character idUbZusStatus;
    @Column(name = "NR_BLOK")
    private Integer nrBlok;

    public UbezpZuszswa() {
    }

    public UbezpZuszswa(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public UbezpZuszswa(Integer idDokument, int idPlatnik, int idDokNad, int idUbezpieczony) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
        this.idDokNad = idDokNad;
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

    public int getIdDokNad() {
        return idDokNad;
    }

    public void setIdDokNad(int idDokNad) {
        this.idDokNad = idDokNad;
    }

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public Character getIiiA1Zgldaprszw() {
        return iiiA1Zgldaprszw;
    }

    public void setIiiA1Zgldaprszw(Character iiiA1Zgldaprszw) {
        this.iiiA1Zgldaprszw = iiiA1Zgldaprszw;
    }

    public Character getIiiA2Zglkordaprszw() {
        return iiiA2Zglkordaprszw;
    }

    public void setIiiA2Zglkordaprszw(Character iiiA2Zglkordaprszw) {
        this.iiiA2Zglkordaprszw = iiiA2Zglkordaprszw;
    }

    public String getIiiA3Pesel() {
        return iiiA3Pesel;
    }

    public void setIiiA3Pesel(String iiiA3Pesel) {
        this.iiiA3Pesel = iiiA3Pesel;
    }

    public String getIiiA4Nip() {
        return iiiA4Nip;
    }

    public void setIiiA4Nip(String iiiA4Nip) {
        this.iiiA4Nip = iiiA4Nip;
    }

    public Character getIiiA5Rodzdok() {
        return iiiA5Rodzdok;
    }

    public void setIiiA5Rodzdok(Character iiiA5Rodzdok) {
        this.iiiA5Rodzdok = iiiA5Rodzdok;
    }

    public String getIiiA6Serianrdok() {
        return iiiA6Serianrdok;
    }

    public void setIiiA6Serianrdok(String iiiA6Serianrdok) {
        this.iiiA6Serianrdok = iiiA6Serianrdok;
    }

    public String getIiiA7Nazwisko() {
        return iiiA7Nazwisko;
    }

    public void setIiiA7Nazwisko(String iiiA7Nazwisko) {
        this.iiiA7Nazwisko = iiiA7Nazwisko;
    }

    public String getIiiA8Imiepierw() {
        return iiiA8Imiepierw;
    }

    public void setIiiA8Imiepierw(String iiiA8Imiepierw) {
        this.iiiA8Imiepierw = iiiA8Imiepierw;
    }

    public Date getIiiA9Dataurodz() {
        return iiiA9Dataurodz;
    }

    public void setIiiA9Dataurodz(Date iiiA9Dataurodz) {
        this.iiiA9Dataurodz = iiiA9Dataurodz;
    }

    public String getIiiB11kodtytub() {
        return iiiB11kodtytub;
    }

    public void setIiiB11kodtytub(String iiiB11kodtytub) {
        this.iiiB11kodtytub = iiiB11kodtytub;
    }

    public Character getIiiB12prdoem() {
        return iiiB12prdoem;
    }

    public void setIiiB12prdoem(Character iiiB12prdoem) {
        this.iiiB12prdoem = iiiB12prdoem;
    }

    public Character getIiiB13stniep() {
        return iiiB13stniep;
    }

    public void setIiiB13stniep(Character iiiB13stniep) {
        this.iiiB13stniep = iiiB13stniep;
    }

    public String getIiiB2Kodprszw() {
        return iiiB2Kodprszw;
    }

    public void setIiiB2Kodprszw(String iiiB2Kodprszw) {
        this.iiiB2Kodprszw = iiiB2Kodprszw;
    }

    public Date getIiiB3Kodprszwod() {
        return iiiB3Kodprszwod;
    }

    public void setIiiB3Kodprszwod(Date iiiB3Kodprszwod) {
        this.iiiB3Kodprszwod = iiiB3Kodprszwod;
    }

    public Date getIiiB4Kodprszwdo() {
        return iiiB4Kodprszwdo;
    }

    public void setIiiB4Kodprszwdo(Date iiiB4Kodprszwdo) {
        this.iiiB4Kodprszwdo = iiiB4Kodprszwdo;
    }

    public String getIiiB51wymczprl() {
        return iiiB51wymczprl;
    }

    public void setIiiB51wymczprl(String iiiB51wymczprl) {
        this.iiiB51wymczprl = iiiB51wymczprl;
    }

    public String getIiiB52wymczprm() {
        return iiiB52wymczprm;
    }

    public void setIiiB52wymczprm(String iiiB52wymczprm) {
        this.iiiB52wymczprm = iiiB52wymczprm;
    }

    public String getIiiB6Kodprszw() {
        return iiiB6Kodprszw;
    }

    public void setIiiB6Kodprszw(String iiiB6Kodprszw) {
        this.iiiB6Kodprszw = iiiB6Kodprszw;
    }

    public Date getIiiB7Kodprszwod() {
        return iiiB7Kodprszwod;
    }

    public void setIiiB7Kodprszwod(Date iiiB7Kodprszwod) {
        this.iiiB7Kodprszwod = iiiB7Kodprszwod;
    }

    public Date getIiiB8Kodprszwdo() {
        return iiiB8Kodprszwdo;
    }

    public void setIiiB8Kodprszwdo(Date iiiB8Kodprszwdo) {
        this.iiiB8Kodprszwdo = iiiB8Kodprszwdo;
    }

    public String getIiiB91wymczprl() {
        return iiiB91wymczprl;
    }

    public void setIiiB91wymczprl(String iiiB91wymczprl) {
        this.iiiB91wymczprl = iiiB91wymczprl;
    }

    public String getIiiB92wymczprm() {
        return iiiB92wymczprm;
    }

    public void setIiiB92wymczprm(String iiiB92wymczprm) {
        this.iiiB92wymczprm = iiiB92wymczprm;
    }

    public String getIiiB10Kodprszw() {
        return iiiB10Kodprszw;
    }

    public void setIiiB10Kodprszw(String iiiB10Kodprszw) {
        this.iiiB10Kodprszw = iiiB10Kodprszw;
    }

    public Date getIiiB11Kodprszwod() {
        return iiiB11Kodprszwod;
    }

    public void setIiiB11Kodprszwod(Date iiiB11Kodprszwod) {
        this.iiiB11Kodprszwod = iiiB11Kodprszwod;
    }

    public Date getIiiB12Kodprszwdo() {
        return iiiB12Kodprszwdo;
    }

    public void setIiiB12Kodprszwdo(Date iiiB12Kodprszwdo) {
        this.iiiB12Kodprszwdo = iiiB12Kodprszwdo;
    }

    public String getIiiB131wymczprl() {
        return iiiB131wymczprl;
    }

    public void setIiiB131wymczprl(String iiiB131wymczprl) {
        this.iiiB131wymczprl = iiiB131wymczprl;
    }

    public String getIiiB132wymczprm() {
        return iiiB132wymczprm;
    }

    public void setIiiB132wymczprm(String iiiB132wymczprm) {
        this.iiiB132wymczprm = iiiB132wymczprm;
    }

    public String getIiiB14Kodprszw() {
        return iiiB14Kodprszw;
    }

    public void setIiiB14Kodprszw(String iiiB14Kodprszw) {
        this.iiiB14Kodprszw = iiiB14Kodprszw;
    }

    public Date getIiiB15Kodprszwod() {
        return iiiB15Kodprszwod;
    }

    public void setIiiB15Kodprszwod(Date iiiB15Kodprszwod) {
        this.iiiB15Kodprszwod = iiiB15Kodprszwod;
    }

    public Date getIiiB16Kodprszwdo() {
        return iiiB16Kodprszwdo;
    }

    public void setIiiB16Kodprszwdo(Date iiiB16Kodprszwdo) {
        this.iiiB16Kodprszwdo = iiiB16Kodprszwdo;
    }

    public String getIiiB171wymczprl() {
        return iiiB171wymczprl;
    }

    public void setIiiB171wymczprl(String iiiB171wymczprl) {
        this.iiiB171wymczprl = iiiB171wymczprl;
    }

    public String getIiiB172wymczprm() {
        return iiiB172wymczprm;
    }

    public void setIiiB172wymczprm(String iiiB172wymczprm) {
        this.iiiB172wymczprm = iiiB172wymczprm;
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

    public Character getStatusKontroli() {
        return statusKontroli;
    }

    public void setStatusKontroli(Character statusKontroli) {
        this.statusKontroli = statusKontroli;
    }

    public Character getIdUbZusStatus() {
        return idUbZusStatus;
    }

    public void setIdUbZusStatus(Character idUbZusStatus) {
        this.idUbZusStatus = idUbZusStatus;
    }

    public Integer getNrBlok() {
        return nrBlok;
    }

    public void setNrBlok(Integer nrBlok) {
        this.nrBlok = nrBlok;
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
        if (!(object instanceof UbezpZuszswa)) {
            return false;
        }
        UbezpZuszswa other = (UbezpZuszswa) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpZuszswa[ idDokument=" + idDokument + " ]";
    }
    
}
