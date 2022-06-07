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
@Table(name = "WIADOMOSC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wiadomosc.findAll", query = "SELECT w FROM Wiadomosc w"),
    @NamedQuery(name = "Wiadomosc.findById", query = "SELECT w FROM Wiadomosc w WHERE w.id = :id"),
    @NamedQuery(name = "Wiadomosc.findByIdPlatnik", query = "SELECT w FROM Wiadomosc w WHERE w.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Wiadomosc.findByIdUzytkownik", query = "SELECT w FROM Wiadomosc w WHERE w.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Wiadomosc.findByTyp", query = "SELECT w FROM Wiadomosc w WHERE w.typ = :typ"),
    @NamedQuery(name = "Wiadomosc.findByData", query = "SELECT w FROM Wiadomosc w WHERE w.data = :data"),
    @NamedQuery(name = "Wiadomosc.findByWiadRod", query = "SELECT w FROM Wiadomosc w WHERE w.wiadRod = :wiadRod"),
    @NamedQuery(name = "Wiadomosc.findByWiadTyp", query = "SELECT w FROM Wiadomosc w WHERE w.wiadTyp = :wiadTyp"),
    @NamedQuery(name = "Wiadomosc.findByTransakcjaId", query = "SELECT w FROM Wiadomosc w WHERE w.transakcjaId = :transakcjaId"),
    @NamedQuery(name = "Wiadomosc.findByWiadIdSkoj", query = "SELECT w FROM Wiadomosc w WHERE w.wiadIdSkoj = :wiadIdSkoj"),
    @NamedQuery(name = "Wiadomosc.findByRodzaj", query = "SELECT w FROM Wiadomosc w WHERE w.rodzaj = :rodzaj"),
    @NamedQuery(name = "Wiadomosc.findByKod", query = "SELECT w FROM Wiadomosc w WHERE w.kod = :kod"),
    @NamedQuery(name = "Wiadomosc.findByGridOpis", query = "SELECT w FROM Wiadomosc w WHERE w.gridOpis = :gridOpis"),
    @NamedQuery(name = "Wiadomosc.findByStatus", query = "SELECT w FROM Wiadomosc w WHERE w.status = :status"),
    @NamedQuery(name = "Wiadomosc.findByPrzyjmujacyId", query = "SELECT w FROM Wiadomosc w WHERE w.przyjmujacyId = :przyjmujacyId"),
    @NamedQuery(name = "Wiadomosc.findByPlatnikId", query = "SELECT w FROM Wiadomosc w WHERE w.platnikId = :platnikId"),
    @NamedQuery(name = "Wiadomosc.findByInserttmp", query = "SELECT w FROM Wiadomosc w WHERE w.inserttmp = :inserttmp")})
public class Wiadomosc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_UZYTKOWNIK")
    private Integer idUzytkownik;
    @Size(max = 3)
    @Column(name = "TYP", length = 3)
    private String typ;
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "WIAD_ROD")
    private Character wiadRod;
    @Column(name = "WIAD_TYP")
    private Character wiadTyp;
    @Column(name = "TRANSAKCJA_ID")
    private Integer transakcjaId;
    @Column(name = "WIAD_ID_SKOJ")
    private Integer wiadIdSkoj;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "PLIK", length = 2147483647)
    private String plik;
    @Column(name = "RODZAJ")
    private Character rodzaj;
    @Size(max = 3)
    @Column(name = "KOD", length = 3)
    private String kod;
    @Size(max = 255)
    @Column(name = "GRID_OPIS", length = 255)
    private String gridOpis;
    @Size(max = 10)
    @Column(name = "STATUS", length = 10)
    private String status;
    @Size(max = 8)
    @Column(name = "PRZYJMUJACY_ID", length = 8)
    private String przyjmujacyId;
    @Size(max = 55)
    @Column(name = "PLATNIK_ID", length = 55)
    private String platnikId;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Wiadomosc() {
    }

    public Wiadomosc(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(Integer idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Character getWiadRod() {
        return wiadRod;
    }

    public void setWiadRod(Character wiadRod) {
        this.wiadRod = wiadRod;
    }

    public Character getWiadTyp() {
        return wiadTyp;
    }

    public void setWiadTyp(Character wiadTyp) {
        this.wiadTyp = wiadTyp;
    }

    public Integer getTransakcjaId() {
        return transakcjaId;
    }

    public void setTransakcjaId(Integer transakcjaId) {
        this.transakcjaId = transakcjaId;
    }

    public Integer getWiadIdSkoj() {
        return wiadIdSkoj;
    }

    public void setWiadIdSkoj(Integer wiadIdSkoj) {
        this.wiadIdSkoj = wiadIdSkoj;
    }

    public String getPlik() {
        return plik;
    }

    public void setPlik(String plik) {
        this.plik = plik;
    }

    public Character getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(Character rodzaj) {
        this.rodzaj = rodzaj;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getGridOpis() {
        return gridOpis;
    }

    public void setGridOpis(String gridOpis) {
        this.gridOpis = gridOpis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrzyjmujacyId() {
        return przyjmujacyId;
    }

    public void setPrzyjmujacyId(String przyjmujacyId) {
        this.przyjmujacyId = przyjmujacyId;
    }

    public String getPlatnikId() {
        return platnikId;
    }

    public void setPlatnikId(String platnikId) {
        this.platnikId = platnikId;
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
        if (!(object instanceof Wiadomosc)) {
            return false;
        }
        Wiadomosc other = (Wiadomosc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Wiadomosc[ id=" + id + " ]";
    }
    
}
