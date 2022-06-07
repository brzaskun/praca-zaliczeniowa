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
@Table(name = "DOKUMENT_PLATNICZY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DokumentPlatniczy.findAll", query = "SELECT d FROM DokumentPlatniczy d"),
    @NamedQuery(name = "DokumentPlatniczy.findById", query = "SELECT d FROM DokumentPlatniczy d WHERE d.id = :id"),
    @NamedQuery(name = "DokumentPlatniczy.findByIdDokument", query = "SELECT d FROM DokumentPlatniczy d WHERE d.idDokument = :idDokument"),
    @NamedQuery(name = "DokumentPlatniczy.findByIdPlatnik", query = "SELECT d FROM DokumentPlatniczy d WHERE d.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "DokumentPlatniczy.findByNaltytskl", query = "SELECT d FROM DokumentPlatniczy d WHERE d.naltytskl = :naltytskl"),
    @NamedQuery(name = "DokumentPlatniczy.findByNrrachunkuzus", query = "SELECT d FROM DokumentPlatniczy d WHERE d.nrrachunkuzus = :nrrachunkuzus"),
    @NamedQuery(name = "DokumentPlatniczy.findByTypdok", query = "SELECT d FROM DokumentPlatniczy d WHERE d.typdok = :typdok"),
    @NamedQuery(name = "DokumentPlatniczy.findByTypwplaty", query = "SELECT d FROM DokumentPlatniczy d WHERE d.typwplaty = :typwplaty"),
    @NamedQuery(name = "DokumentPlatniczy.findByKwota", query = "SELECT d FROM DokumentPlatniczy d WHERE d.kwota = :kwota"),
    @NamedQuery(name = "DokumentPlatniczy.findByNrrachzleceniodawcy", query = "SELECT d FROM DokumentPlatniczy d WHERE d.nrrachzleceniodawcy = :nrrachzleceniodawcy"),
    @NamedQuery(name = "DokumentPlatniczy.findByNazwaskr", query = "SELECT d FROM DokumentPlatniczy d WHERE d.nazwaskr = :nazwaskr"),
    @NamedQuery(name = "DokumentPlatniczy.findByNip", query = "SELECT d FROM DokumentPlatniczy d WHERE d.nip = :nip"),
    @NamedQuery(name = "DokumentPlatniczy.findByTypid", query = "SELECT d FROM DokumentPlatniczy d WHERE d.typid = :typid"),
    @NamedQuery(name = "DokumentPlatniczy.findByNrdecyzji", query = "SELECT d FROM DokumentPlatniczy d WHERE d.nrdecyzji = :nrdecyzji"),
    @NamedQuery(name = "DokumentPlatniczy.findByIdentyfik", query = "SELECT d FROM DokumentPlatniczy d WHERE d.identyfik = :identyfik"),
    @NamedQuery(name = "DokumentPlatniczy.findByIddekls", query = "SELECT d FROM DokumentPlatniczy d WHERE d.iddekls = :iddekls"),
    @NamedQuery(name = "DokumentPlatniczy.findByOkresdeklar", query = "SELECT d FROM DokumentPlatniczy d WHERE d.okresdeklar = :okresdeklar"),
    @NamedQuery(name = "DokumentPlatniczy.findByData", query = "SELECT d FROM DokumentPlatniczy d WHERE d.data = :data"),
    @NamedQuery(name = "DokumentPlatniczy.findByOddzialnadawca", query = "SELECT d FROM DokumentPlatniczy d WHERE d.oddzialnadawca = :oddzialnadawca"),
    @NamedQuery(name = "DokumentPlatniczy.findByOddzialadresat", query = "SELECT d FROM DokumentPlatniczy d WHERE d.oddzialadresat = :oddzialadresat"),
    @NamedQuery(name = "DokumentPlatniczy.findByWydrukowany", query = "SELECT d FROM DokumentPlatniczy d WHERE d.wydrukowany = :wydrukowany"),
    @NamedQuery(name = "DokumentPlatniczy.findByIdUzytkownik", query = "SELECT d FROM DokumentPlatniczy d WHERE d.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "DokumentPlatniczy.findByStatuswr", query = "SELECT d FROM DokumentPlatniczy d WHERE d.statuswr = :statuswr"),
    @NamedQuery(name = "DokumentPlatniczy.findByWersjaWer", query = "SELECT d FROM DokumentPlatniczy d WHERE d.wersjaWer = :wersjaWer"),
    @NamedQuery(name = "DokumentPlatniczy.findByInserttmp", query = "SELECT d FROM DokumentPlatniczy d WHERE d.inserttmp = :inserttmp"),
    @NamedQuery(name = "DokumentPlatniczy.findBySeria", query = "SELECT d FROM DokumentPlatniczy d WHERE d.seria = :seria"),
    @NamedQuery(name = "DokumentPlatniczy.findByZakres", query = "SELECT d FROM DokumentPlatniczy d WHERE d.zakres = :zakres")})
public class DokumentPlatniczy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_DOKUMENT")
    private Integer idDokument;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Column(name = "NALTYTSKL")
    private Character naltytskl;
    @Size(max = 26)
    @Column(name = "NRRACHUNKUZUS", length = 26)
    private String nrrachunkuzus;
    @Column(name = "TYPDOK")
    private Character typdok;
    @Column(name = "TYPWPLATY")
    private Character typwplaty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "KWOTA", precision = 12, scale = 2)
    private BigDecimal kwota;
    @Size(max = 36)
    @Column(name = "NRRACHZLECENIODAWCY", length = 36)
    private String nrrachzleceniodawcy;
    @Size(max = 54)
    @Column(name = "NAZWASKR", length = 54)
    private String nazwaskr;
    @Size(max = 10)
    @Column(name = "NIP", length = 10)
    private String nip;
    @Size(max = 1)
    @Column(name = "TYPID", length = 1)
    private String typid;
    @Size(max = 15)
    @Column(name = "NRDECYZJI", length = 15)
    private String nrdecyzji;
    @Size(max = 14)
    @Column(name = "IDENTYFIK", length = 14)
    private String identyfik;
    @Size(max = 2)
    @Column(name = "IDDEKLS", length = 2)
    private String iddekls;
    @Size(max = 6)
    @Column(name = "OKRESDEKLAR", length = 6)
    private String okresdeklar;
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Size(max = 30)
    @Column(name = "ODDZIALNADAWCA", length = 30)
    private String oddzialnadawca;
    @Size(max = 30)
    @Column(name = "ODDZIALADRESAT", length = 30)
    private String oddzialadresat;
    @Column(name = "WYDRUKOWANY")
    private Character wydrukowany;
    @Column(name = "ID_UZYTKOWNIK")
    private Integer idUzytkownik;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "WERSJA_WER")
    private Integer wersjaWer;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "SERIA")
    private Integer seria;
    @Column(name = "ZAKRES")
    private Integer zakres;

    public DokumentPlatniczy() {
    }

    public DokumentPlatniczy(Integer id) {
        this.id = id;
    }

    public DokumentPlatniczy(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Character getNaltytskl() {
        return naltytskl;
    }

    public void setNaltytskl(Character naltytskl) {
        this.naltytskl = naltytskl;
    }

    public String getNrrachunkuzus() {
        return nrrachunkuzus;
    }

    public void setNrrachunkuzus(String nrrachunkuzus) {
        this.nrrachunkuzus = nrrachunkuzus;
    }

    public Character getTypdok() {
        return typdok;
    }

    public void setTypdok(Character typdok) {
        this.typdok = typdok;
    }

    public Character getTypwplaty() {
        return typwplaty;
    }

    public void setTypwplaty(Character typwplaty) {
        this.typwplaty = typwplaty;
    }

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }

    public String getNrrachzleceniodawcy() {
        return nrrachzleceniodawcy;
    }

    public void setNrrachzleceniodawcy(String nrrachzleceniodawcy) {
        this.nrrachzleceniodawcy = nrrachzleceniodawcy;
    }

    public String getNazwaskr() {
        return nazwaskr;
    }

    public void setNazwaskr(String nazwaskr) {
        this.nazwaskr = nazwaskr;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getTypid() {
        return typid;
    }

    public void setTypid(String typid) {
        this.typid = typid;
    }

    public String getNrdecyzji() {
        return nrdecyzji;
    }

    public void setNrdecyzji(String nrdecyzji) {
        this.nrdecyzji = nrdecyzji;
    }

    public String getIdentyfik() {
        return identyfik;
    }

    public void setIdentyfik(String identyfik) {
        this.identyfik = identyfik;
    }

    public String getIddekls() {
        return iddekls;
    }

    public void setIddekls(String iddekls) {
        this.iddekls = iddekls;
    }

    public String getOkresdeklar() {
        return okresdeklar;
    }

    public void setOkresdeklar(String okresdeklar) {
        this.okresdeklar = okresdeklar;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getOddzialnadawca() {
        return oddzialnadawca;
    }

    public void setOddzialnadawca(String oddzialnadawca) {
        this.oddzialnadawca = oddzialnadawca;
    }

    public String getOddzialadresat() {
        return oddzialadresat;
    }

    public void setOddzialadresat(String oddzialadresat) {
        this.oddzialadresat = oddzialadresat;
    }

    public Character getWydrukowany() {
        return wydrukowany;
    }

    public void setWydrukowany(Character wydrukowany) {
        this.wydrukowany = wydrukowany;
    }

    public Integer getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(Integer idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public Character getStatuswr() {
        return statuswr;
    }

    public void setStatuswr(Character statuswr) {
        this.statuswr = statuswr;
    }

    public Integer getWersjaWer() {
        return wersjaWer;
    }

    public void setWersjaWer(Integer wersjaWer) {
        this.wersjaWer = wersjaWer;
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

    public Integer getZakres() {
        return zakres;
    }

    public void setZakres(Integer zakres) {
        this.zakres = zakres;
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
        if (!(object instanceof DokumentPlatniczy)) {
            return false;
        }
        DokumentPlatniczy other = (DokumentPlatniczy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.DokumentPlatniczy[ id=" + id + " ]";
    }
    
}
