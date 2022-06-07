/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "UBEZP_ZUSRGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpZusrga.findAll", query = "SELECT u FROM UbezpZusrga u"),
    @NamedQuery(name = "UbezpZusrga.findByIdDokument", query = "SELECT u FROM UbezpZusrga u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpZusrga.findByIdPlatnik", query = "SELECT u FROM UbezpZusrga u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpZusrga.findByIdDokNad", query = "SELECT u FROM UbezpZusrga u WHERE u.idDokNad = :idDokNad"),
    @NamedQuery(name = "UbezpZusrga.findByIdUbezpieczony", query = "SELECT u FROM UbezpZusrga u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpZusrga.findByIiiA1Nazwisko", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiA1Nazwisko = :iiiA1Nazwisko"),
    @NamedQuery(name = "UbezpZusrga.findByIiiA2Imiepierw", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiA2Imiepierw = :iiiA2Imiepierw"),
    @NamedQuery(name = "UbezpZusrga.findByIiiA3Typid", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiA3Typid = :iiiA3Typid"),
    @NamedQuery(name = "UbezpZusrga.findByIiiA4Identyfik", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiA4Identyfik = :iiiA4Identyfik"),
    @NamedQuery(name = "UbezpZusrga.findByIiiB11kodtytub", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiB11kodtytub = :iiiB11kodtytub"),
    @NamedQuery(name = "UbezpZusrga.findByIiiB12prdoem", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiB12prdoem = :iiiB12prdoem"),
    @NamedQuery(name = "UbezpZusrga.findByIiiB13stniep", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiB13stniep = :iiiB13stniep"),
    @NamedQuery(name = "UbezpZusrga.findByIiiB2Kodswprzer", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiB2Kodswprzer = :iiiB2Kodswprzer"),
    @NamedQuery(name = "UbezpZusrga.findByIiiB3OkrodRsa", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiB3OkrodRsa = :iiiB3OkrodRsa"),
    @NamedQuery(name = "UbezpZusrga.findByIiiB4OkrdoRsa", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiB4OkrdoRsa = :iiiB4OkrdoRsa"),
    @NamedQuery(name = "UbezpZusrga.findByIiiB5Ldnizasilk", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiB5Ldnizasilk = :iiiB5Ldnizasilk"),
    @NamedQuery(name = "UbezpZusrga.findByIiiB6Kodchoroby", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiB6Kodchoroby = :iiiB6Kodchoroby"),
    @NamedQuery(name = "UbezpZusrga.findByIiiB7Kwotasw", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiB7Kwotasw = :iiiB7Kwotasw"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC1Kodokrpgor", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC1Kodokrpgor = :iiiC1Kodokrpgor"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC2Okrprgorod", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC2Okrprgorod = :iiiC2Okrprgorod"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC3Okrprgordo", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC3Okrprgordo = :iiiC3Okrprgordo"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC4Koddrratow", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC4Koddrratow = :iiiC4Koddrratow"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC5Okrdrratod", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC5Okrdrratod = :iiiC5Okrdrratod"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC6Okrdrratdo", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC6Okrdrratdo = :iiiC6Okrdrratdo"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC7Ldniobpr", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC7Ldniobpr = :iiiC7Ldniobpr"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC8Dniprzepr", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC8Dniprzepr = :iiiC8Dniprzepr"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC9Ldnizjazd", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC9Ldnizjazd = :iiiC9Ldnizjazd"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC10Koddnizja", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC10Koddnizja = :iiiC10Koddnizja"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC11Kodunusp", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC11Kodunusp = :iiiC11Kodunusp"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC12Okurnobod", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC12Okurnobod = :iiiC12Okurnobod"),
    @NamedQuery(name = "UbezpZusrga.findByIiiC13Okrurlndo", query = "SELECT u FROM UbezpZusrga u WHERE u.iiiC13Okrurlndo = :iiiC13Okrurlndo"),
    @NamedQuery(name = "UbezpZusrga.findByStatuswr", query = "SELECT u FROM UbezpZusrga u WHERE u.statuswr = :statuswr"),
    @NamedQuery(name = "UbezpZusrga.findByStatuspt", query = "SELECT u FROM UbezpZusrga u WHERE u.statuspt = :statuspt"),
    @NamedQuery(name = "UbezpZusrga.findByStatusws", query = "SELECT u FROM UbezpZusrga u WHERE u.statusws = :statusws"),
    @NamedQuery(name = "UbezpZusrga.findByInserttmp", query = "SELECT u FROM UbezpZusrga u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpZusrga.findByAlgorytm", query = "SELECT u FROM UbezpZusrga u WHERE u.algorytm = :algorytm")})
public class UbezpZusrga implements Serializable {

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
    @Size(max = 31)
    @Column(name = "III_A_1_NAZWISKO", length = 31)
    private String iiiA1Nazwisko;
    @Size(max = 22)
    @Column(name = "III_A_2_IMIEPIERW", length = 22)
    private String iiiA2Imiepierw;
    @Column(name = "III_A_3_TYPID")
    private Character iiiA3Typid;
    @Size(max = 11)
    @Column(name = "III_A_4_IDENTYFIK", length = 11)
    private String iiiA4Identyfik;
    @Size(max = 4)
    @Column(name = "III_B_1_1KODTYTUB", length = 4)
    private String iiiB11kodtytub;
    @Column(name = "III_B_1_2PRDOEM")
    private Character iiiB12prdoem;
    @Column(name = "III_B_1_3STNIEP")
    private Character iiiB13stniep;
    @Size(max = 3)
    @Column(name = "III_B_2_KODSWPRZER", length = 3)
    private String iiiB2Kodswprzer;
    @Size(max = 4)
    @Column(name = "III_B_3_OKROD_RSA", length = 4)
    private String iiiB3OkrodRsa;
    @Size(max = 4)
    @Column(name = "III_B_4_OKRDO_RSA", length = 4)
    private String iiiB4OkrdoRsa;
    @Size(max = 2)
    @Column(name = "III_B_5_LDNIZASILK", length = 2)
    private String iiiB5Ldnizasilk;
    @Size(max = 2)
    @Column(name = "III_B_6_KODCHOROBY", length = 2)
    private String iiiB6Kodchoroby;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_B_7_KWOTASW", precision = 7, scale = 2)
    private BigDecimal iiiB7Kwotasw;
    @Size(max = 2)
    @Column(name = "III_C_1_KODOKRPGOR", length = 2)
    private String iiiC1Kodokrpgor;
    @Size(max = 4)
    @Column(name = "III_C_2_OKRPRGOROD", length = 4)
    private String iiiC2Okrprgorod;
    @Size(max = 4)
    @Column(name = "III_C_3_OKRPRGORDO", length = 4)
    private String iiiC3Okrprgordo;
    @Column(name = "III_C_4_KODDRRATOW")
    private Character iiiC4Koddrratow;
    @Size(max = 4)
    @Column(name = "III_C_5_OKRDRRATOD", length = 4)
    private String iiiC5Okrdrratod;
    @Size(max = 4)
    @Column(name = "III_C_6_OKRDRRATDO", length = 4)
    private String iiiC6Okrdrratdo;
    @Size(max = 2)
    @Column(name = "III_C_7_LDNIOBPR", length = 2)
    private String iiiC7Ldniobpr;
    @Size(max = 2)
    @Column(name = "III_C_8_DNIPRZEPR", length = 2)
    private String iiiC8Dniprzepr;
    @Size(max = 2)
    @Column(name = "III_C_9_LDNIZJAZD", length = 2)
    private String iiiC9Ldnizjazd;
    @Column(name = "III_C_10_KODDNIZJA")
    private Character iiiC10Koddnizja;
    @Column(name = "III_C_11_KODUNUSP")
    private Character iiiC11Kodunusp;
    @Size(max = 4)
    @Column(name = "III_C_12_OKURNOBOD", length = 4)
    private String iiiC12Okurnobod;
    @Size(max = 4)
    @Column(name = "III_C_13_OKRURLNDO", length = 4)
    private String iiiC13Okrurlndo;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUSWS", nullable = false)
    private int statusws;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Size(max = 3)
    @Column(name = "ALGORYTM", length = 3)
    private String algorytm;

    public UbezpZusrga() {
    }

    public UbezpZusrga(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public UbezpZusrga(Integer idDokument, int idPlatnik, int idDokNad, int idUbezpieczony, int statusws) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
        this.idDokNad = idDokNad;
        this.idUbezpieczony = idUbezpieczony;
        this.statusws = statusws;
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

    public String getIiiA1Nazwisko() {
        return iiiA1Nazwisko;
    }

    public void setIiiA1Nazwisko(String iiiA1Nazwisko) {
        this.iiiA1Nazwisko = iiiA1Nazwisko;
    }

    public String getIiiA2Imiepierw() {
        return iiiA2Imiepierw;
    }

    public void setIiiA2Imiepierw(String iiiA2Imiepierw) {
        this.iiiA2Imiepierw = iiiA2Imiepierw;
    }

    public Character getIiiA3Typid() {
        return iiiA3Typid;
    }

    public void setIiiA3Typid(Character iiiA3Typid) {
        this.iiiA3Typid = iiiA3Typid;
    }

    public String getIiiA4Identyfik() {
        return iiiA4Identyfik;
    }

    public void setIiiA4Identyfik(String iiiA4Identyfik) {
        this.iiiA4Identyfik = iiiA4Identyfik;
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

    public String getIiiB2Kodswprzer() {
        return iiiB2Kodswprzer;
    }

    public void setIiiB2Kodswprzer(String iiiB2Kodswprzer) {
        this.iiiB2Kodswprzer = iiiB2Kodswprzer;
    }

    public String getIiiB3OkrodRsa() {
        return iiiB3OkrodRsa;
    }

    public void setIiiB3OkrodRsa(String iiiB3OkrodRsa) {
        this.iiiB3OkrodRsa = iiiB3OkrodRsa;
    }

    public String getIiiB4OkrdoRsa() {
        return iiiB4OkrdoRsa;
    }

    public void setIiiB4OkrdoRsa(String iiiB4OkrdoRsa) {
        this.iiiB4OkrdoRsa = iiiB4OkrdoRsa;
    }

    public String getIiiB5Ldnizasilk() {
        return iiiB5Ldnizasilk;
    }

    public void setIiiB5Ldnizasilk(String iiiB5Ldnizasilk) {
        this.iiiB5Ldnizasilk = iiiB5Ldnizasilk;
    }

    public String getIiiB6Kodchoroby() {
        return iiiB6Kodchoroby;
    }

    public void setIiiB6Kodchoroby(String iiiB6Kodchoroby) {
        this.iiiB6Kodchoroby = iiiB6Kodchoroby;
    }

    public BigDecimal getIiiB7Kwotasw() {
        return iiiB7Kwotasw;
    }

    public void setIiiB7Kwotasw(BigDecimal iiiB7Kwotasw) {
        this.iiiB7Kwotasw = iiiB7Kwotasw;
    }

    public String getIiiC1Kodokrpgor() {
        return iiiC1Kodokrpgor;
    }

    public void setIiiC1Kodokrpgor(String iiiC1Kodokrpgor) {
        this.iiiC1Kodokrpgor = iiiC1Kodokrpgor;
    }

    public String getIiiC2Okrprgorod() {
        return iiiC2Okrprgorod;
    }

    public void setIiiC2Okrprgorod(String iiiC2Okrprgorod) {
        this.iiiC2Okrprgorod = iiiC2Okrprgorod;
    }

    public String getIiiC3Okrprgordo() {
        return iiiC3Okrprgordo;
    }

    public void setIiiC3Okrprgordo(String iiiC3Okrprgordo) {
        this.iiiC3Okrprgordo = iiiC3Okrprgordo;
    }

    public Character getIiiC4Koddrratow() {
        return iiiC4Koddrratow;
    }

    public void setIiiC4Koddrratow(Character iiiC4Koddrratow) {
        this.iiiC4Koddrratow = iiiC4Koddrratow;
    }

    public String getIiiC5Okrdrratod() {
        return iiiC5Okrdrratod;
    }

    public void setIiiC5Okrdrratod(String iiiC5Okrdrratod) {
        this.iiiC5Okrdrratod = iiiC5Okrdrratod;
    }

    public String getIiiC6Okrdrratdo() {
        return iiiC6Okrdrratdo;
    }

    public void setIiiC6Okrdrratdo(String iiiC6Okrdrratdo) {
        this.iiiC6Okrdrratdo = iiiC6Okrdrratdo;
    }

    public String getIiiC7Ldniobpr() {
        return iiiC7Ldniobpr;
    }

    public void setIiiC7Ldniobpr(String iiiC7Ldniobpr) {
        this.iiiC7Ldniobpr = iiiC7Ldniobpr;
    }

    public String getIiiC8Dniprzepr() {
        return iiiC8Dniprzepr;
    }

    public void setIiiC8Dniprzepr(String iiiC8Dniprzepr) {
        this.iiiC8Dniprzepr = iiiC8Dniprzepr;
    }

    public String getIiiC9Ldnizjazd() {
        return iiiC9Ldnizjazd;
    }

    public void setIiiC9Ldnizjazd(String iiiC9Ldnizjazd) {
        this.iiiC9Ldnizjazd = iiiC9Ldnizjazd;
    }

    public Character getIiiC10Koddnizja() {
        return iiiC10Koddnizja;
    }

    public void setIiiC10Koddnizja(Character iiiC10Koddnizja) {
        this.iiiC10Koddnizja = iiiC10Koddnizja;
    }

    public Character getIiiC11Kodunusp() {
        return iiiC11Kodunusp;
    }

    public void setIiiC11Kodunusp(Character iiiC11Kodunusp) {
        this.iiiC11Kodunusp = iiiC11Kodunusp;
    }

    public String getIiiC12Okurnobod() {
        return iiiC12Okurnobod;
    }

    public void setIiiC12Okurnobod(String iiiC12Okurnobod) {
        this.iiiC12Okurnobod = iiiC12Okurnobod;
    }

    public String getIiiC13Okrurlndo() {
        return iiiC13Okrurlndo;
    }

    public void setIiiC13Okrurlndo(String iiiC13Okrurlndo) {
        this.iiiC13Okrurlndo = iiiC13Okrurlndo;
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

    public int getStatusws() {
        return statusws;
    }

    public void setStatusws(int statusws) {
        this.statusws = statusws;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public String getAlgorytm() {
        return algorytm;
    }

    public void setAlgorytm(String algorytm) {
        this.algorytm = algorytm;
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
        if (!(object instanceof UbezpZusrga)) {
            return false;
        }
        UbezpZusrga other = (UbezpZusrga) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpZusrga[ idDokument=" + idDokument + " ]";
    }
    
}
