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
@Table(name = "ZAMOWIENIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zamowienie.findAll", query = "SELECT z FROM Zamowienie z"),
    @NamedQuery(name = "Zamowienie.findById", query = "SELECT z FROM Zamowienie z WHERE z.id = :id"),
    @NamedQuery(name = "Zamowienie.findByIdPlatnik", query = "SELECT z FROM Zamowienie z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zamowienie.findByNip", query = "SELECT z FROM Zamowienie z WHERE z.nip = :nip"),
    @NamedQuery(name = "Zamowienie.findByRegon", query = "SELECT z FROM Zamowienie z WHERE z.regon = :regon"),
    @NamedQuery(name = "Zamowienie.findByPesel", query = "SELECT z FROM Zamowienie z WHERE z.pesel = :pesel"),
    @NamedQuery(name = "Zamowienie.findByRodzdok", query = "SELECT z FROM Zamowienie z WHERE z.rodzdok = :rodzdok"),
    @NamedQuery(name = "Zamowienie.findBySerianrdok", query = "SELECT z FROM Zamowienie z WHERE z.serianrdok = :serianrdok"),
    @NamedQuery(name = "Zamowienie.findByNazwaskr", query = "SELECT z FROM Zamowienie z WHERE z.nazwaskr = :nazwaskr"),
    @NamedQuery(name = "Zamowienie.findByNazwisko", query = "SELECT z FROM Zamowienie z WHERE z.nazwisko = :nazwisko"),
    @NamedQuery(name = "Zamowienie.findByImiepierw", query = "SELECT z FROM Zamowienie z WHERE z.imiepierw = :imiepierw"),
    @NamedQuery(name = "Zamowienie.findByDataurodz", query = "SELECT z FROM Zamowienie z WHERE z.dataurodz = :dataurodz"),
    @NamedQuery(name = "Zamowienie.findByOkrRozl", query = "SELECT z FROM Zamowienie z WHERE z.okrRozl = :okrRozl"),
    @NamedQuery(name = "Zamowienie.findByStatuspt", query = "SELECT z FROM Zamowienie z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zamowienie.findByNumer", query = "SELECT z FROM Zamowienie z WHERE z.numer = :numer"),
    @NamedQuery(name = "Zamowienie.findByTyp", query = "SELECT z FROM Zamowienie z WHERE z.typ = :typ"),
    @NamedQuery(name = "Zamowienie.findByDataUtw", query = "SELECT z FROM Zamowienie z WHERE z.dataUtw = :dataUtw"),
    @NamedQuery(name = "Zamowienie.findByDataWys", query = "SELECT z FROM Zamowienie z WHERE z.dataWys = :dataWys"),
    @NamedQuery(name = "Zamowienie.findByKanal", query = "SELECT z FROM Zamowienie z WHERE z.kanal = :kanal"),
    @NamedQuery(name = "Zamowienie.findByIdPrzesylka", query = "SELECT z FROM Zamowienie z WHERE z.idPrzesylka = :idPrzesylka"),
    @NamedQuery(name = "Zamowienie.findBySkrot", query = "SELECT z FROM Zamowienie z WHERE z.skrot = :skrot"),
    @NamedQuery(name = "Zamowienie.findByIdPotw", query = "SELECT z FROM Zamowienie z WHERE z.idPotw = :idPotw"),
    @NamedQuery(name = "Zamowienie.findByDataPotw", query = "SELECT z FROM Zamowienie z WHERE z.dataPotw = :dataPotw"),
    @NamedQuery(name = "Zamowienie.findByInserttmp", query = "SELECT z FROM Zamowienie z WHERE z.inserttmp = :inserttmp")})
public class Zamowienie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Size(max = 10)
    @Column(name = "NIP", length = 10)
    private String nip;
    @Size(max = 14)
    @Column(name = "REGON", length = 14)
    private String regon;
    @Size(max = 11)
    @Column(name = "PESEL", length = 11)
    private String pesel;
    @Column(name = "RODZDOK")
    private Character rodzdok;
    @Size(max = 9)
    @Column(name = "SERIANRDOK", length = 9)
    private String serianrdok;
    @Size(max = 31)
    @Column(name = "NAZWASKR", length = 31)
    private String nazwaskr;
    @Size(max = 31)
    @Column(name = "NAZWISKO", length = 31)
    private String nazwisko;
    @Size(max = 22)
    @Column(name = "IMIEPIERW", length = 22)
    private String imiepierw;
    @Column(name = "DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataurodz;
    @Size(max = 6)
    @Column(name = "OKR_ROZL", length = 6)
    private String okrRozl;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "NUMER")
    private Integer numer;
    @Column(name = "TYP")
    private Character typ;
    @Column(name = "DATA_UTW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUtw;
    @Column(name = "DATA_WYS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataWys;
    @Column(name = "KANAL")
    private Character kanal;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "UWAGI", length = 2147483647)
    private String uwagi;
    @Column(name = "ID_PRZESYLKA")
    private Integer idPrzesylka;
    @Size(max = 40)
    @Column(name = "SKROT", length = 40)
    private String skrot;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "NAZWA_PLIKU", length = 2147483647)
    private String nazwaPliku;
    @Size(max = 35)
    @Column(name = "ID_POTW", length = 35)
    private String idPotw;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "STATUSPT_AT", length = 2147483647)
    private String statusptAt;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "POTWIERDZENIE", length = 2147483647)
    private String potwierdzenie;
    @Column(name = "DATA_POTW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPotw;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Zamowienie() {
    }

    public Zamowienie(Integer id) {
        this.id = id;
    }

    public Zamowienie(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
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

    public String getNazwaskr() {
        return nazwaskr;
    }

    public void setNazwaskr(String nazwaskr) {
        this.nazwaskr = nazwaskr;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImiepierw() {
        return imiepierw;
    }

    public void setImiepierw(String imiepierw) {
        this.imiepierw = imiepierw;
    }

    public Date getDataurodz() {
        return dataurodz;
    }

    public void setDataurodz(Date dataurodz) {
        this.dataurodz = dataurodz;
    }

    public String getOkrRozl() {
        return okrRozl;
    }

    public void setOkrRozl(String okrRozl) {
        this.okrRozl = okrRozl;
    }

    public Character getStatuspt() {
        return statuspt;
    }

    public void setStatuspt(Character statuspt) {
        this.statuspt = statuspt;
    }

    public Integer getNumer() {
        return numer;
    }

    public void setNumer(Integer numer) {
        this.numer = numer;
    }

    public Character getTyp() {
        return typ;
    }

    public void setTyp(Character typ) {
        this.typ = typ;
    }

    public Date getDataUtw() {
        return dataUtw;
    }

    public void setDataUtw(Date dataUtw) {
        this.dataUtw = dataUtw;
    }

    public Date getDataWys() {
        return dataWys;
    }

    public void setDataWys(Date dataWys) {
        this.dataWys = dataWys;
    }

    public Character getKanal() {
        return kanal;
    }

    public void setKanal(Character kanal) {
        this.kanal = kanal;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public Integer getIdPrzesylka() {
        return idPrzesylka;
    }

    public void setIdPrzesylka(Integer idPrzesylka) {
        this.idPrzesylka = idPrzesylka;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
    }

    public String getNazwaPliku() {
        return nazwaPliku;
    }

    public void setNazwaPliku(String nazwaPliku) {
        this.nazwaPliku = nazwaPliku;
    }

    public String getIdPotw() {
        return idPotw;
    }

    public void setIdPotw(String idPotw) {
        this.idPotw = idPotw;
    }

    public String getStatusptAt() {
        return statusptAt;
    }

    public void setStatusptAt(String statusptAt) {
        this.statusptAt = statusptAt;
    }

    public String getPotwierdzenie() {
        return potwierdzenie;
    }

    public void setPotwierdzenie(String potwierdzenie) {
        this.potwierdzenie = potwierdzenie;
    }

    public Date getDataPotw() {
        return dataPotw;
    }

    public void setDataPotw(Date dataPotw) {
        this.dataPotw = dataPotw;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zamowienie)) {
            return false;
        }
        Zamowienie other = (Zamowienie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zamowienie[ id=" + id + " ]";
    }
    
}
