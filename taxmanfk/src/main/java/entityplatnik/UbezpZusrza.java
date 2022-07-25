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
@Table(name = "UBEZP_ZUSRZA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpZusrza.findAll", query = "SELECT u FROM UbezpZusrza u"),
    @NamedQuery(name = "UbezpZusrza.findByIdDokument", query = "SELECT u FROM UbezpZusrza u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpZusrza.findByIdPlatnik", query = "SELECT u FROM UbezpZusrza u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpZusrza.findByIdDokNad", query = "SELECT u FROM UbezpZusrza u WHERE u.idDokNad = :idDokNad"),
    @NamedQuery(name = "UbezpZusrza.findByIdUbezpieczony", query = "SELECT u FROM UbezpZusrza u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpZusrza.findByIiiA1Nazwisko", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiA1Nazwisko = :iiiA1Nazwisko"),
    @NamedQuery(name = "UbezpZusrza.findByIiiA2Imiepierw", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiA2Imiepierw = :iiiA2Imiepierw"),
    @NamedQuery(name = "UbezpZusrza.findByIiiA3Typid", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiA3Typid = :iiiA3Typid"),
    @NamedQuery(name = "UbezpZusrza.findByIiiA4Identyfik", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiA4Identyfik = :iiiA4Identyfik"),
    @NamedQuery(name = "UbezpZusrza.findByIiiB11kodtytub", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiB11kodtytub = :iiiB11kodtytub"),
    @NamedQuery(name = "UbezpZusrza.findByIiiB12prdoem", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiB12prdoem = :iiiB12prdoem"),
    @NamedQuery(name = "UbezpZusrza.findByIiiB13stniep", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiB13stniep = :iiiB13stniep"),
    @NamedQuery(name = "UbezpZusrza.findByIiiB2Podstwymsk", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiB2Podstwymsk = :iiiB2Podstwymsk"),
    @NamedQuery(name = "UbezpZusrza.findByIiiB3Kwskladki", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiB3Kwskladki = :iiiB3Kwskladki"),
    @NamedQuery(name = "UbezpZusrza.findByStatuswr", query = "SELECT u FROM UbezpZusrza u WHERE u.statuswr = :statuswr"),
    @NamedQuery(name = "UbezpZusrza.findByStatuspt", query = "SELECT u FROM UbezpZusrza u WHERE u.statuspt = :statuspt"),
    @NamedQuery(name = "UbezpZusrza.findByStatusws", query = "SELECT u FROM UbezpZusrza u WHERE u.statusws = :statusws"),
    @NamedQuery(name = "UbezpZusrza.findByInserttmp", query = "SELECT u FROM UbezpZusrza u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpZusrza.findByNrsciezwyl", query = "SELECT u FROM UbezpZusrza u WHERE u.nrsciezwyl = :nrsciezwyl"),
    @NamedQuery(name = "UbezpZusrza.findByIiiB3KwsklzpR", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiB3KwsklzpR = :iiiB3KwsklzpR"),
    @NamedQuery(name = "UbezpZusrza.findByIiiB4KwsklzbpR", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiB4KwsklzbpR = :iiiB4KwsklzbpR"),
    @NamedQuery(name = "UbezpZusrza.findByIiiB5KwsklzuR", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiB5KwsklzuR = :iiiB5KwsklzuR"),
    @NamedQuery(name = "UbezpZusrza.findByIiiB6KwsklzfkR", query = "SELECT u FROM UbezpZusrza u WHERE u.iiiB6KwsklzfkR = :iiiB6KwsklzfkR"),
    @NamedQuery(name = "UbezpZusrza.findByStatuszus", query = "SELECT u FROM UbezpZusrza u WHERE u.statuszus = :statuszus"),
    @NamedQuery(name = "UbezpZusrza.findByStatusKontroli", query = "SELECT u FROM UbezpZusrza u WHERE u.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "UbezpZusrza.findByIdUbZusStatus", query = "SELECT u FROM UbezpZusrza u WHERE u.idUbZusStatus = :idUbZusStatus"),
    @NamedQuery(name = "UbezpZusrza.findByNrBlok", query = "SELECT u FROM UbezpZusrza u WHERE u.nrBlok = :nrBlok")})
public class UbezpZusrza implements Serializable {

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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_B_2_PODSTWYMSK", precision = 8, scale = 2)
    private BigDecimal iiiB2Podstwymsk;
    @Column(name = "III_B_3_KWSKLADKI", precision = 7, scale = 2)
    private BigDecimal iiiB3Kwskladki;
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
    @Column(name = "NRSCIEZWYL")
    private Integer nrsciezwyl;
    @Column(name = "III_B_3_KWSKLZP_R", precision = 7, scale = 2)
    private BigDecimal iiiB3KwsklzpR;
    @Column(name = "III_B_4_KWSKLZBP_R", precision = 7, scale = 2)
    private BigDecimal iiiB4KwsklzbpR;
    @Column(name = "III_B_5_KWSKLZU_R", precision = 7, scale = 2)
    private BigDecimal iiiB5KwsklzuR;
    @Column(name = "III_B_6_KWSKLZFK_R", precision = 7, scale = 2)
    private BigDecimal iiiB6KwsklzfkR;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "ID_UB_ZUS_STATUS")
    private Character idUbZusStatus;
    @Column(name = "NR_BLOK")
    private Integer nrBlok;

    public UbezpZusrza() {
    }

    public UbezpZusrza(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public UbezpZusrza(Integer idDokument, int idPlatnik, int idDokNad, int idUbezpieczony, int statusws) {
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

    public BigDecimal getIiiB2Podstwymsk() {
        return iiiB2Podstwymsk;
    }

    public void setIiiB2Podstwymsk(BigDecimal iiiB2Podstwymsk) {
        this.iiiB2Podstwymsk = iiiB2Podstwymsk;
    }

    public BigDecimal getIiiB3Kwskladki() {
        return iiiB3Kwskladki;
    }

    public void setIiiB3Kwskladki(BigDecimal iiiB3Kwskladki) {
        this.iiiB3Kwskladki = iiiB3Kwskladki;
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

    public Integer getNrsciezwyl() {
        return nrsciezwyl;
    }

    public void setNrsciezwyl(Integer nrsciezwyl) {
        this.nrsciezwyl = nrsciezwyl;
    }

    public BigDecimal getIiiB3KwsklzpR() {
        return iiiB3KwsklzpR;
    }

    public void setIiiB3KwsklzpR(BigDecimal iiiB3KwsklzpR) {
        this.iiiB3KwsklzpR = iiiB3KwsklzpR;
    }

    public BigDecimal getIiiB4KwsklzbpR() {
        return iiiB4KwsklzbpR;
    }

    public void setIiiB4KwsklzbpR(BigDecimal iiiB4KwsklzbpR) {
        this.iiiB4KwsklzbpR = iiiB4KwsklzbpR;
    }

    public BigDecimal getIiiB5KwsklzuR() {
        return iiiB5KwsklzuR;
    }

    public void setIiiB5KwsklzuR(BigDecimal iiiB5KwsklzuR) {
        this.iiiB5KwsklzuR = iiiB5KwsklzuR;
    }

    public BigDecimal getIiiB6KwsklzfkR() {
        return iiiB6KwsklzfkR;
    }

    public void setIiiB6KwsklzfkR(BigDecimal iiiB6KwsklzfkR) {
        this.iiiB6KwsklzfkR = iiiB6KwsklzfkR;
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
        if (!(object instanceof UbezpZusrza)) {
            return false;
        }
        UbezpZusrza other = (UbezpZusrza) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpZusrza[ idDokument=" + idDokument + " ]";
    }
    
}
