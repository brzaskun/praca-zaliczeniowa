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
@Table(name = "PRZESYLKA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Przesylka.findAll", query = "SELECT p FROM Przesylka p"),
    @NamedQuery(name = "Przesylka.findById", query = "SELECT p FROM Przesylka p WHERE p.id = :id"),
    @NamedQuery(name = "Przesylka.findByIdRodzic", query = "SELECT p FROM Przesylka p WHERE p.idRodzic = :idRodzic"),
    @NamedQuery(name = "Przesylka.findByIdPotomek", query = "SELECT p FROM Przesylka p WHERE p.idPotomek = :idPotomek"),
    @NamedQuery(name = "Przesylka.findByPoziom", query = "SELECT p FROM Przesylka p WHERE p.poziom = :poziom"),
    @NamedQuery(name = "Przesylka.findByIdPlatnik", query = "SELECT p FROM Przesylka p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Przesylka.findByPlatnik", query = "SELECT p FROM Przesylka p WHERE p.platnik = :platnik"),
    @NamedQuery(name = "Przesylka.findByIdUzytkownik", query = "SELECT p FROM Przesylka p WHERE p.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Przesylka.findByRodzaj", query = "SELECT p FROM Przesylka p WHERE p.rodzaj = :rodzaj"),
    @NamedQuery(name = "Przesylka.findByTyp", query = "SELECT p FROM Przesylka p WHERE p.typ = :typ"),
    @NamedQuery(name = "Przesylka.findByNadawca", query = "SELECT p FROM Przesylka p WHERE p.nadawca = :nadawca"),
    @NamedQuery(name = "Przesylka.findByOdbiorca", query = "SELECT p FROM Przesylka p WHERE p.odbiorca = :odbiorca"),
    @NamedQuery(name = "Przesylka.findByIdZawartosc", query = "SELECT p FROM Przesylka p WHERE p.idZawartosc = :idZawartosc"),
    @NamedQuery(name = "Przesylka.findByNazwaZawartosc", query = "SELECT p FROM Przesylka p WHERE p.nazwaZawartosc = :nazwaZawartosc"),
    @NamedQuery(name = "Przesylka.findByDotyczy", query = "SELECT p FROM Przesylka p WHERE p.dotyczy = :dotyczy"),
    @NamedQuery(name = "Przesylka.findByKodBledu", query = "SELECT p FROM Przesylka p WHERE p.kodBledu = :kodBledu"),
    @NamedQuery(name = "Przesylka.findByStatus", query = "SELECT p FROM Przesylka p WHERE p.status = :status"),
    @NamedQuery(name = "Przesylka.findByDataUtw", query = "SELECT p FROM Przesylka p WHERE p.dataUtw = :dataUtw"),
    @NamedQuery(name = "Przesylka.findByDataWysOdb", query = "SELECT p FROM Przesylka p WHERE p.dataWysOdb = :dataWysOdb"),
    @NamedQuery(name = "Przesylka.findByIdPrzesylki", query = "SELECT p FROM Przesylka p WHERE p.idPrzesylki = :idPrzesylki"),
    @NamedQuery(name = "Przesylka.findByIdDoZus", query = "SELECT p FROM Przesylka p WHERE p.idDoZus = :idDoZus"),
    @NamedQuery(name = "Przesylka.findByIdZZus", query = "SELECT p FROM Przesylka p WHERE p.idZZus = :idZZus"),
    @NamedQuery(name = "Przesylka.findByAdreshttp", query = "SELECT p FROM Przesylka p WHERE p.adreshttp = :adreshttp"),
    @NamedQuery(name = "Przesylka.findByKanal", query = "SELECT p FROM Przesylka p WHERE p.kanal = :kanal"),
    @NamedQuery(name = "Przesylka.findBySkrot", query = "SELECT p FROM Przesylka p WHERE p.skrot = :skrot"),
    @NamedQuery(name = "Przesylka.findByFormat", query = "SELECT p FROM Przesylka p WHERE p.format = :format"),
    @NamedQuery(name = "Przesylka.findByWielkosc", query = "SELECT p FROM Przesylka p WHERE p.wielkosc = :wielkosc"),
    @NamedQuery(name = "Przesylka.findBySkrotDime", query = "SELECT p FROM Przesylka p WHERE p.skrotDime = :skrotDime"),
    @NamedQuery(name = "Przesylka.findByUpowaznienie", query = "SELECT p FROM Przesylka p WHERE p.upowaznienie = :upowaznienie"),
    @NamedQuery(name = "Przesylka.findByIdPlatnikUpow", query = "SELECT p FROM Przesylka p WHERE p.idPlatnikUpow = :idPlatnikUpow"),
    @NamedQuery(name = "Przesylka.findByInfoProducent", query = "SELECT p FROM Przesylka p WHERE p.infoProducent = :infoProducent"),
    @NamedQuery(name = "Przesylka.findByInfoProgram", query = "SELECT p FROM Przesylka p WHERE p.infoProgram = :infoProgram"),
    @NamedQuery(name = "Przesylka.findByInfoWersja", query = "SELECT p FROM Przesylka p WHERE p.infoWersja = :infoWersja"),
    @NamedQuery(name = "Przesylka.findByCertRodzaj", query = "SELECT p FROM Przesylka p WHERE p.certRodzaj = :certRodzaj"),
    @NamedQuery(name = "Przesylka.findByCertNumser", query = "SELECT p FROM Przesylka p WHERE p.certNumser = :certNumser"),
    @NamedQuery(name = "Przesylka.findByCertWystawca", query = "SELECT p FROM Przesylka p WHERE p.certWystawca = :certWystawca"),
    @NamedQuery(name = "Przesylka.findBySposob", query = "SELECT p FROM Przesylka p WHERE p.sposob = :sposob"),
    @NamedQuery(name = "Przesylka.findByInserttmp", query = "SELECT p FROM Przesylka p WHERE p.inserttmp = :inserttmp")})
public class Przesylka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_RODZIC")
    private Integer idRodzic;
    @Column(name = "ID_POTOMEK")
    private Integer idPotomek;
    @Column(name = "POZIOM")
    private Integer poziom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Column(name = "PLATNIK")
    private Integer platnik;
    @Column(name = "ID_UZYTKOWNIK")
    private Integer idUzytkownik;
    @Column(name = "RODZAJ")
    private Character rodzaj;
    @Column(name = "TYP")
    private Character typ;
    @Size(max = 255)
    @Column(name = "NADAWCA", length = 255)
    private String nadawca;
    @Size(max = 255)
    @Column(name = "ODBIORCA", length = 255)
    private String odbiorca;
    @Column(name = "ID_ZAWARTOSC")
    private Integer idZawartosc;
    @Size(max = 255)
    @Column(name = "NAZWA_ZAWARTOSC", length = 255)
    private String nazwaZawartosc;
    @Size(max = 255)
    @Column(name = "DOTYCZY", length = 255)
    private String dotyczy;
    @Column(name = "KOD_BLEDU")
    private Integer kodBledu;
    @Size(max = 255)
    @Column(name = "STATUS", length = 255)
    private String status;
    @Column(name = "DATA_UTW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUtw;
    @Column(name = "DATA_WYS_ODB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataWysOdb;
    @Size(max = 40)
    @Column(name = "ID_PRZESYLKI", length = 40)
    private String idPrzesylki;
    @Size(max = 40)
    @Column(name = "ID_DO_ZUS", length = 40)
    private String idDoZus;
    @Size(max = 40)
    @Column(name = "ID_Z_ZUS", length = 40)
    private String idZZus;
    @Size(max = 50)
    @Column(name = "ADRESHTTP", length = 50)
    private String adreshttp;
    @Column(name = "KANAL")
    private Character kanal;
    @Size(max = 128)
    @Column(name = "SKROT", length = 128)
    private String skrot;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "SC_DO_PRZES", length = 2147483647)
    private String scDoPrzes;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "SC_DO_CMS", length = 2147483647)
    private String scDoCms;
    @Column(name = "FORMAT")
    private Integer format;
    @Column(name = "WIELKOSC")
    private Integer wielkosc;
    @Size(max = 128)
    @Column(name = "SKROT_DIME", length = 128)
    private String skrotDime;
    @Column(name = "UPOWAZNIENIE")
    private Character upowaznienie;
    @Column(name = "ID_PLATNIK_UPOW")
    private Integer idPlatnikUpow;
    @Size(max = 64)
    @Column(name = "INFO_PRODUCENT", length = 64)
    private String infoProducent;
    @Size(max = 64)
    @Column(name = "INFO_PROGRAM", length = 64)
    private String infoProgram;
    @Size(max = 32)
    @Column(name = "INFO_WERSJA", length = 32)
    private String infoWersja;
    @Column(name = "CERT_RODZAJ")
    private Character certRodzaj;
    @Size(max = 40)
    @Column(name = "CERT_NUMSER", length = 40)
    private String certNumser;
    @Size(max = 255)
    @Column(name = "CERT_WYSTAWCA", length = 255)
    private String certWystawca;
    @Column(name = "SPOSOB")
    private Character sposob;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Przesylka() {
    }

    public Przesylka(Integer id) {
        this.id = id;
    }

    public Przesylka(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdRodzic() {
        return idRodzic;
    }

    public void setIdRodzic(Integer idRodzic) {
        this.idRodzic = idRodzic;
    }

    public Integer getIdPotomek() {
        return idPotomek;
    }

    public void setIdPotomek(Integer idPotomek) {
        this.idPotomek = idPotomek;
    }

    public Integer getPoziom() {
        return poziom;
    }

    public void setPoziom(Integer poziom) {
        this.poziom = poziom;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getPlatnik() {
        return platnik;
    }

    public void setPlatnik(Integer platnik) {
        this.platnik = platnik;
    }

    public Integer getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(Integer idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public Character getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(Character rodzaj) {
        this.rodzaj = rodzaj;
    }

    public Character getTyp() {
        return typ;
    }

    public void setTyp(Character typ) {
        this.typ = typ;
    }

    public String getNadawca() {
        return nadawca;
    }

    public void setNadawca(String nadawca) {
        this.nadawca = nadawca;
    }

    public String getOdbiorca() {
        return odbiorca;
    }

    public void setOdbiorca(String odbiorca) {
        this.odbiorca = odbiorca;
    }

    public Integer getIdZawartosc() {
        return idZawartosc;
    }

    public void setIdZawartosc(Integer idZawartosc) {
        this.idZawartosc = idZawartosc;
    }

    public String getNazwaZawartosc() {
        return nazwaZawartosc;
    }

    public void setNazwaZawartosc(String nazwaZawartosc) {
        this.nazwaZawartosc = nazwaZawartosc;
    }

    public String getDotyczy() {
        return dotyczy;
    }

    public void setDotyczy(String dotyczy) {
        this.dotyczy = dotyczy;
    }

    public Integer getKodBledu() {
        return kodBledu;
    }

    public void setKodBledu(Integer kodBledu) {
        this.kodBledu = kodBledu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataUtw() {
        return dataUtw;
    }

    public void setDataUtw(Date dataUtw) {
        this.dataUtw = dataUtw;
    }

    public Date getDataWysOdb() {
        return dataWysOdb;
    }

    public void setDataWysOdb(Date dataWysOdb) {
        this.dataWysOdb = dataWysOdb;
    }

    public String getIdPrzesylki() {
        return idPrzesylki;
    }

    public void setIdPrzesylki(String idPrzesylki) {
        this.idPrzesylki = idPrzesylki;
    }

    public String getIdDoZus() {
        return idDoZus;
    }

    public void setIdDoZus(String idDoZus) {
        this.idDoZus = idDoZus;
    }

    public String getIdZZus() {
        return idZZus;
    }

    public void setIdZZus(String idZZus) {
        this.idZZus = idZZus;
    }

    public String getAdreshttp() {
        return adreshttp;
    }

    public void setAdreshttp(String adreshttp) {
        this.adreshttp = adreshttp;
    }

    public Character getKanal() {
        return kanal;
    }

    public void setKanal(Character kanal) {
        this.kanal = kanal;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
    }

    public String getScDoPrzes() {
        return scDoPrzes;
    }

    public void setScDoPrzes(String scDoPrzes) {
        this.scDoPrzes = scDoPrzes;
    }

    public String getScDoCms() {
        return scDoCms;
    }

    public void setScDoCms(String scDoCms) {
        this.scDoCms = scDoCms;
    }

    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    public Integer getWielkosc() {
        return wielkosc;
    }

    public void setWielkosc(Integer wielkosc) {
        this.wielkosc = wielkosc;
    }

    public String getSkrotDime() {
        return skrotDime;
    }

    public void setSkrotDime(String skrotDime) {
        this.skrotDime = skrotDime;
    }

    public Character getUpowaznienie() {
        return upowaznienie;
    }

    public void setUpowaznienie(Character upowaznienie) {
        this.upowaznienie = upowaznienie;
    }

    public Integer getIdPlatnikUpow() {
        return idPlatnikUpow;
    }

    public void setIdPlatnikUpow(Integer idPlatnikUpow) {
        this.idPlatnikUpow = idPlatnikUpow;
    }

    public String getInfoProducent() {
        return infoProducent;
    }

    public void setInfoProducent(String infoProducent) {
        this.infoProducent = infoProducent;
    }

    public String getInfoProgram() {
        return infoProgram;
    }

    public void setInfoProgram(String infoProgram) {
        this.infoProgram = infoProgram;
    }

    public String getInfoWersja() {
        return infoWersja;
    }

    public void setInfoWersja(String infoWersja) {
        this.infoWersja = infoWersja;
    }

    public Character getCertRodzaj() {
        return certRodzaj;
    }

    public void setCertRodzaj(Character certRodzaj) {
        this.certRodzaj = certRodzaj;
    }

    public String getCertNumser() {
        return certNumser;
    }

    public void setCertNumser(String certNumser) {
        this.certNumser = certNumser;
    }

    public String getCertWystawca() {
        return certWystawca;
    }

    public void setCertWystawca(String certWystawca) {
        this.certWystawca = certWystawca;
    }

    public Character getSposob() {
        return sposob;
    }

    public void setSposob(Character sposob) {
        this.sposob = sposob;
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
        if (!(object instanceof Przesylka)) {
            return false;
        }
        Przesylka other = (Przesylka) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Przesylka[ id=" + id + " ]";
    }
    
}
