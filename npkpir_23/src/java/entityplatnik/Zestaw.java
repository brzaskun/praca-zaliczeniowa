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
@Table(name = "ZESTAW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zestaw.findAll", query = "SELECT z FROM Zestaw z"),
    @NamedQuery(name = "Zestaw.findById", query = "SELECT z FROM Zestaw z WHERE z.id = :id"),
    @NamedQuery(name = "Zestaw.findByIdZesNad", query = "SELECT z FROM Zestaw z WHERE z.idZesNad = :idZesNad"),
    @NamedQuery(name = "Zestaw.findByIdPlatnik", query = "SELECT z FROM Zestaw z WHERE z.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Zestaw.findByNumer", query = "SELECT z FROM Zestaw z WHERE z.numer = :numer"),
    @NamedQuery(name = "Zestaw.findByNazwa", query = "SELECT z FROM Zestaw z WHERE z.nazwa = :nazwa"),
    @NamedQuery(name = "Zestaw.findByDataWys", query = "SELECT z FROM Zestaw z WHERE z.dataWys = :dataWys"),
    @NamedQuery(name = "Zestaw.findByDataPotw", query = "SELECT z FROM Zestaw z WHERE z.dataPotw = :dataPotw"),
    @NamedQuery(name = "Zestaw.findByKanal", query = "SELECT z FROM Zestaw z WHERE z.kanal = :kanal"),
    @NamedQuery(name = "Zestaw.findByStatuspt", query = "SELECT z FROM Zestaw z WHERE z.statuspt = :statuspt"),
    @NamedQuery(name = "Zestaw.findByStatuswr", query = "SELECT z FROM Zestaw z WHERE z.statuswr = :statuswr"),
    @NamedQuery(name = "Zestaw.findByDataUtwZest", query = "SELECT z FROM Zestaw z WHERE z.dataUtwZest = :dataUtwZest"),
    @NamedQuery(name = "Zestaw.findByDataIdPotw", query = "SELECT z FROM Zestaw z WHERE z.dataIdPotw = :dataIdPotw"),
    @NamedQuery(name = "Zestaw.findByIdPotw", query = "SELECT z FROM Zestaw z WHERE z.idPotw = :idPotw"),
    @NamedQuery(name = "Zestaw.findByPlikPotw", query = "SELECT z FROM Zestaw z WHERE z.plikPotw = :plikPotw"),
    @NamedQuery(name = "Zestaw.findByPotwJednZus", query = "SELECT z FROM Zestaw z WHERE z.potwJednZus = :potwJednZus"),
    @NamedQuery(name = "Zestaw.findByDataNad", query = "SELECT z FROM Zestaw z WHERE z.dataNad = :dataNad"),
    @NamedQuery(name = "Zestaw.findByDataRej", query = "SELECT z FROM Zestaw z WHERE z.dataRej = :dataRej"),
    @NamedQuery(name = "Zestaw.findByAdreshttp", query = "SELECT z FROM Zestaw z WHERE z.adreshttp = :adreshttp"),
    @NamedQuery(name = "Zestaw.findByIdPlik", query = "SELECT z FROM Zestaw z WHERE z.idPlik = :idPlik"),
    @NamedQuery(name = "Zestaw.findByHash", query = "SELECT z FROM Zestaw z WHERE z.hash = :hash"),
    @NamedQuery(name = "Zestaw.findByIdAt", query = "SELECT z FROM Zestaw z WHERE z.idAt = :idAt"),
    @NamedQuery(name = "Zestaw.findBySkrot", query = "SELECT z FROM Zestaw z WHERE z.skrot = :skrot"),
    @NamedQuery(name = "Zestaw.findByIdPrzesylka", query = "SELECT z FROM Zestaw z WHERE z.idPrzesylka = :idPrzesylka"),
    @NamedQuery(name = "Zestaw.findBySposob", query = "SELECT z FROM Zestaw z WHERE z.sposob = :sposob"),
    @NamedQuery(name = "Zestaw.findByInserttmp", query = "SELECT z FROM Zestaw z WHERE z.inserttmp = :inserttmp"),
    @NamedQuery(name = "Zestaw.findByStatuszus", query = "SELECT z FROM Zestaw z WHERE z.statuszus = :statuszus"),
    @NamedQuery(name = "Zestaw.findByStatusKontroli", query = "SELECT z FROM Zestaw z WHERE z.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "Zestaw.findByStatuswrzus", query = "SELECT z FROM Zestaw z WHERE z.statuswrzus = :statuswrzus")})
public class Zestaw implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_ZES_NAD")
    private Integer idZesNad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Column(name = "NUMER")
    private Integer numer;
    @Size(max = 255)
    @Column(name = "NAZWA", length = 255)
    private String nazwa;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "NAZWA_PLIKU_KSIMAIL", length = 2147483647)
    private String nazwaPlikuKsimail;
    @Column(name = "DATA_WYS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataWys;
    @Column(name = "DATA_POTW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPotw;
    @Column(name = "KANAL")
    private Character kanal;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "POTWIERDZENIE", length = 2147483647)
    private String potwierdzenie;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "UWAGI", length = 2147483647)
    private String uwagi;
    @Column(name = "DATA_UTW_ZEST")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUtwZest;
    @Column(name = "DATA_ID_POTW")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataIdPotw;
    @Size(max = 35)
    @Column(name = "ID_POTW", length = 35)
    private String idPotw;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "SC_DO_POTW", length = 2147483647)
    private String scDoPotw;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "SC_DO_OST", length = 2147483647)
    private String scDoOst;
    @Column(name = "PLIK_POTW")
    private Character plikPotw;
    @Size(max = 10)
    @Column(name = "POTW_JEDN_ZUS", length = 10)
    private String potwJednZus;
    @Column(name = "DATA_NAD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNad;
    @Column(name = "DATA_REJ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRej;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "STATUSPT_AT", length = 2147483647)
    private String statusptAt;
    @Size(max = 50)
    @Column(name = "ADRESHTTP", length = 50)
    private String adreshttp;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "TRESC", length = 2147483647)
    private String tresc;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "PRZYG_OPIS", length = 2147483647)
    private String przygOpis;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "RODZAJ_BL", length = 2147483647)
    private String rodzajBl;
    @Column(name = "ID_PLIK")
    private Integer idPlik;
    @Column(name = "HASH")
    private Integer hash;
    @Size(max = 128)
    @Column(name = "ID_AT", length = 128)
    private String idAt;
    @Size(max = 128)
    @Column(name = "SKROT", length = 128)
    private String skrot;
    @Column(name = "ID_PRZESYLKA")
    private Integer idPrzesylka;
    @Column(name = "SPOSOB")
    private Character sposob;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "STATUSZUS")
    private Character statuszus;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "STATUSWRZUS")
    private Character statuswrzus;

    public Zestaw() {
    }

    public Zestaw(Integer id) {
        this.id = id;
    }

    public Zestaw(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdZesNad() {
        return idZesNad;
    }

    public void setIdZesNad(Integer idZesNad) {
        this.idZesNad = idZesNad;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getNumer() {
        return numer;
    }

    public void setNumer(Integer numer) {
        this.numer = numer;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwaPlikuKsimail() {
        return nazwaPlikuKsimail;
    }

    public void setNazwaPlikuKsimail(String nazwaPlikuKsimail) {
        this.nazwaPlikuKsimail = nazwaPlikuKsimail;
    }

    public Date getDataWys() {
        return dataWys;
    }

    public void setDataWys(Date dataWys) {
        this.dataWys = dataWys;
    }

    public Date getDataPotw() {
        return dataPotw;
    }

    public void setDataPotw(Date dataPotw) {
        this.dataPotw = dataPotw;
    }

    public Character getKanal() {
        return kanal;
    }

    public void setKanal(Character kanal) {
        this.kanal = kanal;
    }

    public String getPotwierdzenie() {
        return potwierdzenie;
    }

    public void setPotwierdzenie(String potwierdzenie) {
        this.potwierdzenie = potwierdzenie;
    }

    public Character getStatuspt() {
        return statuspt;
    }

    public void setStatuspt(Character statuspt) {
        this.statuspt = statuspt;
    }

    public Character getStatuswr() {
        return statuswr;
    }

    public void setStatuswr(Character statuswr) {
        this.statuswr = statuswr;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public Date getDataUtwZest() {
        return dataUtwZest;
    }

    public void setDataUtwZest(Date dataUtwZest) {
        this.dataUtwZest = dataUtwZest;
    }

    public Date getDataIdPotw() {
        return dataIdPotw;
    }

    public void setDataIdPotw(Date dataIdPotw) {
        this.dataIdPotw = dataIdPotw;
    }

    public String getIdPotw() {
        return idPotw;
    }

    public void setIdPotw(String idPotw) {
        this.idPotw = idPotw;
    }

    public String getScDoPotw() {
        return scDoPotw;
    }

    public void setScDoPotw(String scDoPotw) {
        this.scDoPotw = scDoPotw;
    }

    public String getScDoOst() {
        return scDoOst;
    }

    public void setScDoOst(String scDoOst) {
        this.scDoOst = scDoOst;
    }

    public Character getPlikPotw() {
        return plikPotw;
    }

    public void setPlikPotw(Character plikPotw) {
        this.plikPotw = plikPotw;
    }

    public String getPotwJednZus() {
        return potwJednZus;
    }

    public void setPotwJednZus(String potwJednZus) {
        this.potwJednZus = potwJednZus;
    }

    public Date getDataNad() {
        return dataNad;
    }

    public void setDataNad(Date dataNad) {
        this.dataNad = dataNad;
    }

    public Date getDataRej() {
        return dataRej;
    }

    public void setDataRej(Date dataRej) {
        this.dataRej = dataRej;
    }

    public String getStatusptAt() {
        return statusptAt;
    }

    public void setStatusptAt(String statusptAt) {
        this.statusptAt = statusptAt;
    }

    public String getAdreshttp() {
        return adreshttp;
    }

    public void setAdreshttp(String adreshttp) {
        this.adreshttp = adreshttp;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getPrzygOpis() {
        return przygOpis;
    }

    public void setPrzygOpis(String przygOpis) {
        this.przygOpis = przygOpis;
    }

    public String getRodzajBl() {
        return rodzajBl;
    }

    public void setRodzajBl(String rodzajBl) {
        this.rodzajBl = rodzajBl;
    }

    public Integer getIdPlik() {
        return idPlik;
    }

    public void setIdPlik(Integer idPlik) {
        this.idPlik = idPlik;
    }

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }

    public String getIdAt() {
        return idAt;
    }

    public void setIdAt(String idAt) {
        this.idAt = idAt;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
    }

    public Integer getIdPrzesylka() {
        return idPrzesylka;
    }

    public void setIdPrzesylka(Integer idPrzesylka) {
        this.idPrzesylka = idPrzesylka;
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

    public Character getStatuswrzus() {
        return statuswrzus;
    }

    public void setStatuswrzus(Character statuswrzus) {
        this.statuswrzus = statuswrzus;
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
        if (!(object instanceof Zestaw)) {
            return false;
        }
        Zestaw other = (Zestaw) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Zestaw[ id=" + id + " ]";
    }
    
}
