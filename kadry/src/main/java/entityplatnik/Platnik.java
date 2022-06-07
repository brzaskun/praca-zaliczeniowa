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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "PLATNIK", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"NAZWASKR"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Platnik.findAll", query = "SELECT p FROM Platnik p"),
    @NamedQuery(name = "Platnik.findById", query = "SELECT p FROM Platnik p WHERE p.id = :id"),
    @NamedQuery(name = "Platnik.findByNazwaskr", query = "SELECT p FROM Platnik p WHERE p.nazwaskr = :nazwaskr"),
    @NamedQuery(name = "Platnik.findByStatuswr", query = "SELECT p FROM Platnik p WHERE p.statuswr = :statuswr"),
    @NamedQuery(name = "Platnik.findByStatuspt", query = "SELECT p FROM Platnik p WHERE p.statuspt = :statuspt"),
    @NamedQuery(name = "Platnik.findByIdbiurarach", query = "SELECT p FROM Platnik p WHERE p.idbiurarach = :idbiurarach"),
    @NamedQuery(name = "Platnik.findByDataod", query = "SELECT p FROM Platnik p WHERE p.dataod = :dataod"),
    @NamedQuery(name = "Platnik.findByDatado", query = "SELECT p FROM Platnik p WHERE p.datado = :datado"),
    @NamedQuery(name = "Platnik.findByIdDokZgl", query = "SELECT p FROM Platnik p WHERE p.idDokZgl = :idDokZgl"),
    @NamedQuery(name = "Platnik.findByIdDokWyr", query = "SELECT p FROM Platnik p WHERE p.idDokWyr = :idDokWyr"),
    @NamedQuery(name = "Platnik.findByIdUzytkownik", query = "SELECT p FROM Platnik p WHERE p.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Platnik.findByZkprchr", query = "SELECT p FROM Platnik p WHERE p.zkprchr = :zkprchr"),
    @NamedQuery(name = "Platnik.findByDataUtw", query = "SELECT p FROM Platnik p WHERE p.dataUtw = :dataUtw"),
    @NamedQuery(name = "Platnik.findByDatardzial", query = "SELECT p FROM Platnik p WHERE p.datardzial = :datardzial"),
    @NamedQuery(name = "Platnik.findByInserttmp", query = "SELECT p FROM Platnik p WHERE p.inserttmp = :inserttmp"),
    @NamedQuery(name = "Platnik.findByIdPlZus", query = "SELECT p FROM Platnik p WHERE p.idPlZus = :idPlZus"),
    @NamedQuery(name = "Platnik.findByCechaPlatnika", query = "SELECT p FROM Platnik p WHERE p.cechaPlatnika = :cechaPlatnika"),
    @NamedQuery(name = "Platnik.findByDokZglosz", query = "SELECT p FROM Platnik p WHERE p.dokZglosz = :dokZglosz"),
    @NamedQuery(name = "Platnik.findByNazwaSkrBRach", query = "SELECT p FROM Platnik p WHERE p.nazwaSkrBRach = :nazwaSkrBRach"),
    @NamedQuery(name = "Platnik.findByNipBRach", query = "SELECT p FROM Platnik p WHERE p.nipBRach = :nipBRach"),
    @NamedQuery(name = "Platnik.findByRegonBRach", query = "SELECT p FROM Platnik p WHERE p.regonBRach = :regonBRach"),
    @NamedQuery(name = "Platnik.findByStatusAktywnosci", query = "SELECT p FROM Platnik p WHERE p.statusAktywnosci = :statusAktywnosci"),
    @NamedQuery(name = "Platnik.findByStatusPotwWZus", query = "SELECT p FROM Platnik p WHERE p.statusPotwWZus = :statusPotwWZus"),
    @NamedQuery(name = "Platnik.findByZnacznikCzasu", query = "SELECT p FROM Platnik p WHERE p.znacznikCzasu = :znacznikCzasu"),
    @NamedQuery(name = "Platnik.findByDataPotwWZus", query = "SELECT p FROM Platnik p WHERE p.dataPotwWZus = :dataPotwWZus"),
    @NamedQuery(name = "Platnik.findByStatusWerAut", query = "SELECT p FROM Platnik p WHERE p.statusWerAut = :statusWerAut"),
    @NamedQuery(name = "Platnik.findBySkrotDanych", query = "SELECT p FROM Platnik p WHERE p.skrotDanych = :skrotDanych"),
    @NamedQuery(name = "Platnik.findByNrDzial", query = "SELECT p FROM Platnik p WHERE p.nrDzial = :nrDzial"),
    @NamedQuery(name = "Platnik.findByLiczbaUbezp", query = "SELECT p FROM Platnik p WHERE p.liczbaUbezp = :liczbaUbezp")})
public class Platnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Size(max = 31)
    @Column(name = "NAZWASKR", length = 31)
    private String nazwaskr;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "IDBIURARACH")
    private Integer idbiurarach;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "DATADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datado;
    @Column(name = "ID_DOK_ZGL")
    private Integer idDokZgl;
    @Column(name = "ID_DOK_WYR")
    private Integer idDokWyr;
    @Column(name = "ID_UZYTKOWNIK")
    private Integer idUzytkownik;
    @Column(name = "ZKPRCHR")
    private Character zkprchr;
    @Column(name = "DATA_UTW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUtw;
    @Column(name = "DATARDZIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datardzial;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "CECHA_PLATNIKA")
    private Character cechaPlatnika;
    @Size(max = 5)
    @Column(name = "DOK_ZGLOSZ", length = 5)
    private String dokZglosz;
    @Size(max = 31)
    @Column(name = "NAZWA_SKR_B_RACH", length = 31)
    private String nazwaSkrBRach;
    @Size(max = 10)
    @Column(name = "NIP_B_RACH", length = 10)
    private String nipBRach;
    @Size(max = 14)
    @Column(name = "REGON_B_RACH", length = 14)
    private String regonBRach;
    @Column(name = "STATUS_AKTYWNOSCI")
    private Character statusAktywnosci;
    @Column(name = "STATUS_POTW_W_ZUS")
    private Character statusPotwWZus;
    @Column(name = "ZNACZNIK_CZASU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date znacznikCzasu;
    @Column(name = "DATA_POTW_W_ZUS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPotwWZus;
    @Column(name = "STATUS_WER_AUT")
    private Character statusWerAut;
    @Size(max = 40)
    @Column(name = "SKROT_DANYCH", length = 40)
    private String skrotDanych;
    @Column(name = "NR_DZIAL")
    private Short nrDzial;
    @Column(name = "LICZBA_UBEZP")
    private Integer liczbaUbezp;

    public Platnik() {
    }

    public Platnik(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwaskr() {
        return nazwaskr;
    }

    public void setNazwaskr(String nazwaskr) {
        this.nazwaskr = nazwaskr;
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

    public Integer getIdbiurarach() {
        return idbiurarach;
    }

    public void setIdbiurarach(Integer idbiurarach) {
        this.idbiurarach = idbiurarach;
    }

    public Date getDataod() {
        return dataod;
    }

    public void setDataod(Date dataod) {
        this.dataod = dataod;
    }

    public Date getDatado() {
        return datado;
    }

    public void setDatado(Date datado) {
        this.datado = datado;
    }

    public Integer getIdDokZgl() {
        return idDokZgl;
    }

    public void setIdDokZgl(Integer idDokZgl) {
        this.idDokZgl = idDokZgl;
    }

    public Integer getIdDokWyr() {
        return idDokWyr;
    }

    public void setIdDokWyr(Integer idDokWyr) {
        this.idDokWyr = idDokWyr;
    }

    public Integer getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(Integer idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public Character getZkprchr() {
        return zkprchr;
    }

    public void setZkprchr(Character zkprchr) {
        this.zkprchr = zkprchr;
    }

    public Date getDataUtw() {
        return dataUtw;
    }

    public void setDataUtw(Date dataUtw) {
        this.dataUtw = dataUtw;
    }

    public Date getDatardzial() {
        return datardzial;
    }

    public void setDatardzial(Date datardzial) {
        this.datardzial = datardzial;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Character getCechaPlatnika() {
        return cechaPlatnika;
    }

    public void setCechaPlatnika(Character cechaPlatnika) {
        this.cechaPlatnika = cechaPlatnika;
    }

    public String getDokZglosz() {
        return dokZglosz;
    }

    public void setDokZglosz(String dokZglosz) {
        this.dokZglosz = dokZglosz;
    }

    public String getNazwaSkrBRach() {
        return nazwaSkrBRach;
    }

    public void setNazwaSkrBRach(String nazwaSkrBRach) {
        this.nazwaSkrBRach = nazwaSkrBRach;
    }

    public String getNipBRach() {
        return nipBRach;
    }

    public void setNipBRach(String nipBRach) {
        this.nipBRach = nipBRach;
    }

    public String getRegonBRach() {
        return regonBRach;
    }

    public void setRegonBRach(String regonBRach) {
        this.regonBRach = regonBRach;
    }

    public Character getStatusAktywnosci() {
        return statusAktywnosci;
    }

    public void setStatusAktywnosci(Character statusAktywnosci) {
        this.statusAktywnosci = statusAktywnosci;
    }

    public Character getStatusPotwWZus() {
        return statusPotwWZus;
    }

    public void setStatusPotwWZus(Character statusPotwWZus) {
        this.statusPotwWZus = statusPotwWZus;
    }

    public Date getZnacznikCzasu() {
        return znacznikCzasu;
    }

    public void setZnacznikCzasu(Date znacznikCzasu) {
        this.znacznikCzasu = znacznikCzasu;
    }

    public Date getDataPotwWZus() {
        return dataPotwWZus;
    }

    public void setDataPotwWZus(Date dataPotwWZus) {
        this.dataPotwWZus = dataPotwWZus;
    }

    public Character getStatusWerAut() {
        return statusWerAut;
    }

    public void setStatusWerAut(Character statusWerAut) {
        this.statusWerAut = statusWerAut;
    }

    public String getSkrotDanych() {
        return skrotDanych;
    }

    public void setSkrotDanych(String skrotDanych) {
        this.skrotDanych = skrotDanych;
    }

    public Short getNrDzial() {
        return nrDzial;
    }

    public void setNrDzial(Short nrDzial) {
        this.nrDzial = nrDzial;
    }

    public Integer getLiczbaUbezp() {
        return liczbaUbezp;
    }

    public void setLiczbaUbezp(Integer liczbaUbezp) {
        this.liczbaUbezp = liczbaUbezp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Platnik)) {
            return false;
        }
        Platnik other = (Platnik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Platnik[ id=" + id + " ]";
    }
    
}
