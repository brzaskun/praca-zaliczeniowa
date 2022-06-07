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
import javax.persistence.Lob;
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
@Table(name = "DOKUMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokument.findAll", query = "SELECT d FROM Dokument d"),
    @NamedQuery(name = "Dokument.findByIdDokument", query = "SELECT d FROM Dokument d WHERE d.idDokument = :idDokument"),
    @NamedQuery(name = "Dokument.findByIdDokNad", query = "SELECT d FROM Dokument d WHERE d.idDokNad = :idDokNad"),
    @NamedQuery(name = "Dokument.findByIdPlatnik", query = "SELECT d FROM Dokument d WHERE d.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Dokument.findByIdUbezpieczony", query = "SELECT d FROM Dokument d WHERE d.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "Dokument.findByTyp", query = "SELECT d FROM Dokument d WHERE d.typ = :typ"),
    @NamedQuery(name = "Dokument.findByAtrybutI", query = "SELECT d FROM Dokument d WHERE d.atrybutI = :atrybutI"),
    @NamedQuery(name = "Dokument.findByAtrybutIi", query = "SELECT d FROM Dokument d WHERE d.atrybutIi = :atrybutIi"),
    @NamedQuery(name = "Dokument.findByNumer", query = "SELECT d FROM Dokument d WHERE d.numer = :numer"),
    @NamedQuery(name = "Dokument.findByImiepierw", query = "SELECT d FROM Dokument d WHERE d.imiepierw = :imiepierw"),
    @NamedQuery(name = "Dokument.findByNazwisko", query = "SELECT d FROM Dokument d WHERE d.nazwisko = :nazwisko"),
    @NamedQuery(name = "Dokument.findByPesel", query = "SELECT d FROM Dokument d WHERE d.pesel = :pesel"),
    @NamedQuery(name = "Dokument.findByNip", query = "SELECT d FROM Dokument d WHERE d.nip = :nip"),
    @NamedQuery(name = "Dokument.findByRodzdok", query = "SELECT d FROM Dokument d WHERE d.rodzdok = :rodzdok"),
    @NamedQuery(name = "Dokument.findBySerianrdok", query = "SELECT d FROM Dokument d WHERE d.serianrdok = :serianrdok"),
    @NamedQuery(name = "Dokument.findByIdraps", query = "SELECT d FROM Dokument d WHERE d.idraps = :idraps"),
    @NamedQuery(name = "Dokument.findByOkrrozl", query = "SELECT d FROM Dokument d WHERE d.okrrozl = :okrrozl"),
    @NamedQuery(name = "Dokument.findByWersjaWer", query = "SELECT d FROM Dokument d WHERE d.wersjaWer = :wersjaWer"),
    @NamedQuery(name = "Dokument.findByStatuswr", query = "SELECT d FROM Dokument d WHERE d.statuswr = :statuswr"),
    @NamedQuery(name = "Dokument.findByStatuspt", query = "SELECT d FROM Dokument d WHERE d.statuspt = :statuspt"),
    @NamedQuery(name = "Dokument.findByData", query = "SELECT d FROM Dokument d WHERE d.data = :data"),
    @NamedQuery(name = "Dokument.findByInserttmp", query = "SELECT d FROM Dokument d WHERE d.inserttmp = :inserttmp"),
    @NamedQuery(name = "Dokument.findByIdTechDok", query = "SELECT d FROM Dokument d WHERE d.idTechDok = :idTechDok"),
    @NamedQuery(name = "Dokument.findByIdImportDok", query = "SELECT d FROM Dokument d WHERE d.idImportDok = :idImportDok"),
    @NamedQuery(name = "Dokument.findByStatusptold", query = "SELECT d FROM Dokument d WHERE d.statusptold = :statusptold"),
    @NamedQuery(name = "Dokument.findBySeria", query = "SELECT d FROM Dokument d WHERE d.seria = :seria"),
    @NamedQuery(name = "Dokument.findByLPozycji", query = "SELECT d FROM Dokument d WHERE d.lPozycji = :lPozycji"),
    @NamedQuery(name = "Dokument.findByStatuszus", query = "SELECT d FROM Dokument d WHERE d.statuszus = :statuszus"),
    @NamedQuery(name = "Dokument.findByStatusKontroli", query = "SELECT d FROM Dokument d WHERE d.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Dokument.findByNrBlok", query = "SELECT d FROM Dokument d WHERE d.nrBlok = :nrBlok"),
    @NamedQuery(name = "Dokument.findByStatuswrzus", query = "SELECT d FROM Dokument d WHERE d.statuswrzus = :statuswrzus"),
    @NamedQuery(name = "Dokument.findByIdOddzial", query = "SELECT d FROM Dokument d WHERE d.idOddzial = :idOddzial"),
    @NamedQuery(name = "Dokument.findByTypBloku", query = "SELECT d FROM Dokument d WHERE d.typBloku = :typBloku")})
public class Dokument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOKUMENT", nullable = false)
    private Integer idDokument;
    @Column(name = "ID_DOK_NAD")
    private Integer idDokNad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Column(name = "ID_UBEZPIECZONY")
    private Integer idUbezpieczony;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TYP", nullable = false)
    private int typ;
    @Column(name = "ATRYBUT_I")
    private Integer atrybutI;
    @Column(name = "ATRYBUT_II")
    private Integer atrybutIi;
    @Column(name = "NUMER")
    private Integer numer;
    @Size(max = 22)
    @Column(name = "IMIEPIERW", length = 22)
    private String imiepierw;
    @Size(max = 31)
    @Column(name = "NAZWISKO", length = 31)
    private String nazwisko;
    @Size(max = 11)
    @Column(name = "PESEL", length = 11)
    private String pesel;
    @Size(max = 10)
    @Column(name = "NIP", length = 10)
    private String nip;
    @Column(name = "RODZDOK")
    private Character rodzdok;
    @Size(max = 9)
    @Column(name = "SERIANRDOK", length = 9)
    private String serianrdok;
    @Size(max = 2)
    @Column(name = "IDRAPS", length = 2)
    private String idraps;
    @Size(max = 6)
    @Column(name = "OKRROZL", length = 6)
    private String okrrozl;
    @Column(name = "WERSJA_WER")
    private Integer wersjaWer;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_TECH_DOK")
    private Integer idTechDok;
    @Column(name = "ID_IMPORT_DOK")
    private Integer idImportDok;
    @Column(name = "STATUSPTOLD")
    private Character statusptold;
    @Column(name = "SERIA")
    private Integer seria;
    @Column(name = "L_POZYCJI")
    private Integer lPozycji;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "KOREKTA_KOM", length = 2147483647)
    private String korektaKom;
    @Column(name = "NR_BLOK")
    private Integer nrBlok;
    @Column(name = "STATUSWRZUS")
    private Character statuswrzus;
    @Column(name = "ID_ODDZIAL")
    private Integer idOddzial;
    @Column(name = "TYP_BLOKU")
    private Integer typBloku;

    public Dokument() {
    }

    public Dokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Dokument(Integer idDokument, int idPlatnik, int typ) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
        this.typ = typ;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Integer getIdDokNad() {
        return idDokNad;
    }

    public void setIdDokNad(Integer idDokNad) {
        this.idDokNad = idDokNad;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(Integer idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    public Integer getAtrybutI() {
        return atrybutI;
    }

    public void setAtrybutI(Integer atrybutI) {
        this.atrybutI = atrybutI;
    }

    public Integer getAtrybutIi() {
        return atrybutIi;
    }

    public void setAtrybutIi(Integer atrybutIi) {
        this.atrybutIi = atrybutIi;
    }

    public Integer getNumer() {
        return numer;
    }

    public void setNumer(Integer numer) {
        this.numer = numer;
    }

    public String getImiepierw() {
        return imiepierw;
    }

    public void setImiepierw(String imiepierw) {
        this.imiepierw = imiepierw;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Character getRodzdok() {
        return rodzdok;
    }

    public void setRodzdok(Character rodzdok) {
        this.rodzdok = rodzdok;
    }

    public String getSerianrdok() {
        return serianrdok;
    }

    public void setSerianrdok(String serianrdok) {
        this.serianrdok = serianrdok;
    }

    public String getIdraps() {
        return idraps;
    }

    public void setIdraps(String idraps) {
        this.idraps = idraps;
    }

    public String getOkrrozl() {
        return okrrozl;
    }

    public void setOkrrozl(String okrrozl) {
        this.okrrozl = okrrozl;
    }

    public Integer getWersjaWer() {
        return wersjaWer;
    }

    public void setWersjaWer(Integer wersjaWer) {
        this.wersjaWer = wersjaWer;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getIdTechDok() {
        return idTechDok;
    }

    public void setIdTechDok(Integer idTechDok) {
        this.idTechDok = idTechDok;
    }

    public Integer getIdImportDok() {
        return idImportDok;
    }

    public void setIdImportDok(Integer idImportDok) {
        this.idImportDok = idImportDok;
    }

    public Character getStatusptold() {
        return statusptold;
    }

    public void setStatusptold(Character statusptold) {
        this.statusptold = statusptold;
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

    public String getKorektaKom() {
        return korektaKom;
    }

    public void setKorektaKom(String korektaKom) {
        this.korektaKom = korektaKom;
    }

    public Integer getNrBlok() {
        return nrBlok;
    }

    public void setNrBlok(Integer nrBlok) {
        this.nrBlok = nrBlok;
    }

    public Character getStatuswrzus() {
        return statuswrzus;
    }

    public void setStatuswrzus(Character statuswrzus) {
        this.statuswrzus = statuswrzus;
    }

    public Integer getIdOddzial() {
        return idOddzial;
    }

    public void setIdOddzial(Integer idOddzial) {
        this.idOddzial = idOddzial;
    }

    public Integer getTypBloku() {
        return typBloku;
    }

    public void setTypBloku(Integer typBloku) {
        this.typBloku = typBloku;
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
        if (!(object instanceof Dokument)) {
            return false;
        }
        Dokument other = (Dokument) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Dokument[ idDokument=" + idDokument + " ]";
    }
    
}
