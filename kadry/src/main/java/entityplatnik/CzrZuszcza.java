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
@Table(name = "CZR_ZUSZCZA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CzrZuszcza.findAll", query = "SELECT c FROM CzrZuszcza c"),
    @NamedQuery(name = "CzrZuszcza.findByIdDokument", query = "SELECT c FROM CzrZuszcza c WHERE c.idDokument = :idDokument"),
    @NamedQuery(name = "CzrZuszcza.findByIdPlatnik", query = "SELECT c FROM CzrZuszcza c WHERE c.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "CzrZuszcza.findByIdUbezpieczony", query = "SELECT c FROM CzrZuszcza c WHERE c.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "CzrZuszcza.findByIdDokNad", query = "SELECT c FROM CzrZuszcza c WHERE c.idDokNad = :idDokNad"),
    @NamedQuery(name = "CzrZuszcza.findByIvA1Zglczlwera", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA1Zglczlwera = :ivA1Zglczlwera"),
    @NamedQuery(name = "CzrZuszcza.findByIvA2Datauzuprzd", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA2Datauzuprzd = :ivA2Datauzuprzd"),
    @NamedQuery(name = "CzrZuszcza.findByIvA3Pesel", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA3Pesel = :ivA3Pesel"),
    @NamedQuery(name = "CzrZuszcza.findByIvA4Nip", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA4Nip = :ivA4Nip"),
    @NamedQuery(name = "CzrZuszcza.findByIvA5Rodzdok", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA5Rodzdok = :ivA5Rodzdok"),
    @NamedQuery(name = "CzrZuszcza.findByIvA6Serianrdok", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA6Serianrdok = :ivA6Serianrdok"),
    @NamedQuery(name = "CzrZuszcza.findByIvA7Nazwisko", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA7Nazwisko = :ivA7Nazwisko"),
    @NamedQuery(name = "CzrZuszcza.findByIvA8Imiepierw", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA8Imiepierw = :ivA8Imiepierw"),
    @NamedQuery(name = "CzrZuszcza.findByIvA9Dataurodz", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA9Dataurodz = :ivA9Dataurodz"),
    @NamedQuery(name = "CzrZuszcza.findByIvA10Kodstpokr", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA10Kodstpokr = :ivA10Kodstpokr"),
    @NamedQuery(name = "CzrZuszcza.findByIvA11Nautrzosub", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA11Nautrzosub = :ivA11Nautrzosub"),
    @NamedQuery(name = "CzrZuszcza.findByIvA12Pogospzub", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA12Pogospzub = :ivA12Pogospzub"),
    @NamedQuery(name = "CzrZuszcza.findByIvA13Stniep", query = "SELECT c FROM CzrZuszcza c WHERE c.ivA13Stniep = :ivA13Stniep"),
    @NamedQuery(name = "CzrZuszcza.findByStatuswr", query = "SELECT c FROM CzrZuszcza c WHERE c.statuswr = :statuswr"),
    @NamedQuery(name = "CzrZuszcza.findByStatuspt", query = "SELECT c FROM CzrZuszcza c WHERE c.statuspt = :statuspt"),
    @NamedQuery(name = "CzrZuszcza.findByInserttmp", query = "SELECT c FROM CzrZuszcza c WHERE c.inserttmp = :inserttmp")})
public class CzrZuszcza implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOK_NAD", nullable = false)
    private int idDokNad;
    @Column(name = "IV_A_1_ZGLCZLWERA")
    private Character ivA1Zglczlwera;
    @Column(name = "IV_A_2_DATAUZUPRZD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ivA2Datauzuprzd;
    @Size(max = 11)
    @Column(name = "IV_A_3_PESEL", length = 11)
    private String ivA3Pesel;
    @Size(max = 10)
    @Column(name = "IV_A_4_NIP", length = 10)
    private String ivA4Nip;
    @Column(name = "IV_A_5_RODZDOK")
    private Character ivA5Rodzdok;
    @Size(max = 9)
    @Column(name = "IV_A_6_SERIANRDOK", length = 9)
    private String ivA6Serianrdok;
    @Size(max = 31)
    @Column(name = "IV_A_7_NAZWISKO", length = 31)
    private String ivA7Nazwisko;
    @Size(max = 22)
    @Column(name = "IV_A_8_IMIEPIERW", length = 22)
    private String ivA8Imiepierw;
    @Column(name = "IV_A_9_DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ivA9Dataurodz;
    @Size(max = 2)
    @Column(name = "IV_A_10_KODSTPOKR", length = 2)
    private String ivA10Kodstpokr;
    @Column(name = "IV_A_11_NAUTRZOSUB")
    private Character ivA11Nautrzosub;
    @Column(name = "IV_A_12_POGOSPZUB")
    private Character ivA12Pogospzub;
    @Column(name = "IV_A_13_STNIEP")
    private Character ivA13Stniep;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public CzrZuszcza() {
    }

    public CzrZuszcza(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public CzrZuszcza(Integer idDokument, int idPlatnik, int idUbezpieczony, int idDokNad) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
        this.idUbezpieczony = idUbezpieczony;
        this.idDokNad = idDokNad;
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

    public int getIdDokNad() {
        return idDokNad;
    }

    public void setIdDokNad(int idDokNad) {
        this.idDokNad = idDokNad;
    }

    public Character getIvA1Zglczlwera() {
        return ivA1Zglczlwera;
    }

    public void setIvA1Zglczlwera(Character ivA1Zglczlwera) {
        this.ivA1Zglczlwera = ivA1Zglczlwera;
    }

    public Date getIvA2Datauzuprzd() {
        return ivA2Datauzuprzd;
    }

    public void setIvA2Datauzuprzd(Date ivA2Datauzuprzd) {
        this.ivA2Datauzuprzd = ivA2Datauzuprzd;
    }

    public String getIvA3Pesel() {
        return ivA3Pesel;
    }

    public void setIvA3Pesel(String ivA3Pesel) {
        this.ivA3Pesel = ivA3Pesel;
    }

    public String getIvA4Nip() {
        return ivA4Nip;
    }

    public void setIvA4Nip(String ivA4Nip) {
        this.ivA4Nip = ivA4Nip;
    }

    public Character getIvA5Rodzdok() {
        return ivA5Rodzdok;
    }

    public void setIvA5Rodzdok(Character ivA5Rodzdok) {
        this.ivA5Rodzdok = ivA5Rodzdok;
    }

    public String getIvA6Serianrdok() {
        return ivA6Serianrdok;
    }

    public void setIvA6Serianrdok(String ivA6Serianrdok) {
        this.ivA6Serianrdok = ivA6Serianrdok;
    }

    public String getIvA7Nazwisko() {
        return ivA7Nazwisko;
    }

    public void setIvA7Nazwisko(String ivA7Nazwisko) {
        this.ivA7Nazwisko = ivA7Nazwisko;
    }

    public String getIvA8Imiepierw() {
        return ivA8Imiepierw;
    }

    public void setIvA8Imiepierw(String ivA8Imiepierw) {
        this.ivA8Imiepierw = ivA8Imiepierw;
    }

    public Date getIvA9Dataurodz() {
        return ivA9Dataurodz;
    }

    public void setIvA9Dataurodz(Date ivA9Dataurodz) {
        this.ivA9Dataurodz = ivA9Dataurodz;
    }

    public String getIvA10Kodstpokr() {
        return ivA10Kodstpokr;
    }

    public void setIvA10Kodstpokr(String ivA10Kodstpokr) {
        this.ivA10Kodstpokr = ivA10Kodstpokr;
    }

    public Character getIvA11Nautrzosub() {
        return ivA11Nautrzosub;
    }

    public void setIvA11Nautrzosub(Character ivA11Nautrzosub) {
        this.ivA11Nautrzosub = ivA11Nautrzosub;
    }

    public Character getIvA12Pogospzub() {
        return ivA12Pogospzub;
    }

    public void setIvA12Pogospzub(Character ivA12Pogospzub) {
        this.ivA12Pogospzub = ivA12Pogospzub;
    }

    public Character getIvA13Stniep() {
        return ivA13Stniep;
    }

    public void setIvA13Stniep(Character ivA13Stniep) {
        this.ivA13Stniep = ivA13Stniep;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDokument != null ? idDokument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CzrZuszcza)) {
            return false;
        }
        CzrZuszcza other = (CzrZuszcza) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.CzrZuszcza[ idDokument=" + idDokument + " ]";
    }
    
}
