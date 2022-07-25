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
@Table(name = "UBEZP_ZUSRSA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpZusrsa.findAll", query = "SELECT u FROM UbezpZusrsa u"),
    @NamedQuery(name = "UbezpZusrsa.findByIdDokument", query = "SELECT u FROM UbezpZusrsa u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpZusrsa.findByIdPlatnik", query = "SELECT u FROM UbezpZusrsa u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpZusrsa.findByIdDokNad", query = "SELECT u FROM UbezpZusrsa u WHERE u.idDokNad = :idDokNad"),
    @NamedQuery(name = "UbezpZusrsa.findByIdUbezpieczony", query = "SELECT u FROM UbezpZusrsa u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiA1Nazwisko", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiA1Nazwisko = :iiiA1Nazwisko"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiA2Imiepierw", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiA2Imiepierw = :iiiA2Imiepierw"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiA3Typid", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiA3Typid = :iiiA3Typid"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiA4Identyfik", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiA4Identyfik = :iiiA4Identyfik"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiB11kodtytub", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiB11kodtytub = :iiiB11kodtytub"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiB12prdoem", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiB12prdoem = :iiiB12prdoem"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiB13stniep", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiB13stniep = :iiiB13stniep"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiB2Kodswprzer", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiB2Kodswprzer = :iiiB2Kodswprzer"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiB3OkrodRsa", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiB3OkrodRsa = :iiiB3OkrodRsa"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiB4OkrdoRsa", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiB4OkrdoRsa = :iiiB4OkrdoRsa"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiB5Ldnizasilk", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiB5Ldnizasilk = :iiiB5Ldnizasilk"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiB6Kodchoroby", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiB6Kodchoroby = :iiiB6Kodchoroby"),
    @NamedQuery(name = "UbezpZusrsa.findByIiiB7Kwotasw", query = "SELECT u FROM UbezpZusrsa u WHERE u.iiiB7Kwotasw = :iiiB7Kwotasw"),
    @NamedQuery(name = "UbezpZusrsa.findByStatuswr", query = "SELECT u FROM UbezpZusrsa u WHERE u.statuswr = :statuswr"),
    @NamedQuery(name = "UbezpZusrsa.findByStatuspt", query = "SELECT u FROM UbezpZusrsa u WHERE u.statuspt = :statuspt"),
    @NamedQuery(name = "UbezpZusrsa.findByStatusws", query = "SELECT u FROM UbezpZusrsa u WHERE u.statusws = :statusws"),
    @NamedQuery(name = "UbezpZusrsa.findByInserttmp", query = "SELECT u FROM UbezpZusrsa u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpZusrsa.findByAlgorytm", query = "SELECT u FROM UbezpZusrsa u WHERE u.algorytm = :algorytm"),
    @NamedQuery(name = "UbezpZusrsa.findByStatuszus", query = "SELECT u FROM UbezpZusrsa u WHERE u.statuszus = :statuszus"),
    @NamedQuery(name = "UbezpZusrsa.findByStatusKontroli", query = "SELECT u FROM UbezpZusrsa u WHERE u.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "UbezpZusrsa.findByIdUbZusStatus", query = "SELECT u FROM UbezpZusrsa u WHERE u.idUbZusStatus = :idUbZusStatus"),
    @NamedQuery(name = "UbezpZusrsa.findByNrBlok", query = "SELECT u FROM UbezpZusrsa u WHERE u.nrBlok = :nrBlok")})
public class UbezpZusrsa implements Serializable {

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
    @Column(name = "III_B_3_OKROD_RSA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB3OkrodRsa;
    @Column(name = "III_B_4_OKRDO_RSA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiB4OkrdoRsa;
    @Size(max = 3)
    @Column(name = "III_B_5_LDNIZASILK", length = 3)
    private String iiiB5Ldnizasilk;
    @Size(max = 2)
    @Column(name = "III_B_6_KODCHOROBY", length = 2)
    private String iiiB6Kodchoroby;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_B_7_KWOTASW", precision = 7, scale = 2)
    private BigDecimal iiiB7Kwotasw;
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
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_UB_ZUS_STATUS")
    private Character idUbZusStatus;
    @Column(name = "NR_BLOK")
    private Integer nrBlok;

    public UbezpZusrsa() {
    }

    public UbezpZusrsa(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public UbezpZusrsa(Integer idDokument, int idPlatnik, int idDokNad, int idUbezpieczony, int statusws) {
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

    public Date getIiiB3OkrodRsa() {
        return iiiB3OkrodRsa;
    }

    public void setIiiB3OkrodRsa(Date iiiB3OkrodRsa) {
        this.iiiB3OkrodRsa = iiiB3OkrodRsa;
    }

    public Date getIiiB4OkrdoRsa() {
        return iiiB4OkrdoRsa;
    }

    public void setIiiB4OkrdoRsa(Date iiiB4OkrdoRsa) {
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
        if (!(object instanceof UbezpZusrsa)) {
            return false;
        }
        UbezpZusrsa other = (UbezpZusrsa) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpZusrsa[ idDokument=" + idDokument + " ]";
    }
    
}
