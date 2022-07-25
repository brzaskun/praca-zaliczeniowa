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
@Table(name = "KOMUNIKAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Komunikat.findAll", query = "SELECT k FROM Komunikat k"),
    @NamedQuery(name = "Komunikat.findByIdKomunikat", query = "SELECT k FROM Komunikat k WHERE k.idKomunikat = :idKomunikat"),
    @NamedQuery(name = "Komunikat.findByIdPlatnik", query = "SELECT k FROM Komunikat k WHERE k.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Komunikat.findByIdBiuro", query = "SELECT k FROM Komunikat k WHERE k.idBiuro = :idBiuro"),
    @NamedQuery(name = "Komunikat.findByIdKomZus", query = "SELECT k FROM Komunikat k WHERE k.idKomZus = :idKomZus"),
    @NamedQuery(name = "Komunikat.findByDataPublikacji", query = "SELECT k FROM Komunikat k WHERE k.dataPublikacji = :dataPublikacji"),
    @NamedQuery(name = "Komunikat.findByDataWaznosci", query = "SELECT k FROM Komunikat k WHERE k.dataWaznosci = :dataWaznosci"),
    @NamedQuery(name = "Komunikat.findByDataRejestracji", query = "SELECT k FROM Komunikat k WHERE k.dataRejestracji = :dataRejestracji"),
    @NamedQuery(name = "Komunikat.findByTytul", query = "SELECT k FROM Komunikat k WHERE k.tytul = :tytul"),
    @NamedQuery(name = "Komunikat.findByLiczbaZal", query = "SELECT k FROM Komunikat k WHERE k.liczbaZal = :liczbaZal"),
    @NamedQuery(name = "Komunikat.findByStatus", query = "SELECT k FROM Komunikat k WHERE k.status = :status"),
    @NamedQuery(name = "Komunikat.findByStatCzyt", query = "SELECT k FROM Komunikat k WHERE k.statCzyt = :statCzyt"),
    @NamedQuery(name = "Komunikat.findByInserttmp", query = "SELECT k FROM Komunikat k WHERE k.inserttmp = :inserttmp")})
public class Komunikat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_KOMUNIKAT", nullable = false)
    private Integer idKomunikat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Column(name = "ID_BIURO")
    private Integer idBiuro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ID_KOM_ZUS", nullable = false, length = 50)
    private String idKomZus;
    @Column(name = "DATA_PUBLIKACJI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPublikacji;
    @Column(name = "DATA_WAZNOSCI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataWaznosci;
    @Column(name = "DATA_REJESTRACJI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRejestracji;
    @Size(max = 255)
    @Column(name = "TYTUL", length = 255)
    private String tytul;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "TRESC", length = 2147483647)
    private String tresc;
    @Column(name = "LICZBA_ZAL")
    private Short liczbaZal;
    @Column(name = "STATUS")
    private Short status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STAT_CZYT", nullable = false)
    private short statCzyt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Komunikat() {
    }

    public Komunikat(Integer idKomunikat) {
        this.idKomunikat = idKomunikat;
    }

    public Komunikat(Integer idKomunikat, int idPlatnik, String idKomZus, short statCzyt) {
        this.idKomunikat = idKomunikat;
        this.idPlatnik = idPlatnik;
        this.idKomZus = idKomZus;
        this.statCzyt = statCzyt;
    }

    public Integer getIdKomunikat() {
        return idKomunikat;
    }

    public void setIdKomunikat(Integer idKomunikat) {
        this.idKomunikat = idKomunikat;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdBiuro() {
        return idBiuro;
    }

    public void setIdBiuro(Integer idBiuro) {
        this.idBiuro = idBiuro;
    }

    public String getIdKomZus() {
        return idKomZus;
    }

    public void setIdKomZus(String idKomZus) {
        this.idKomZus = idKomZus;
    }

    public Date getDataPublikacji() {
        return dataPublikacji;
    }

    public void setDataPublikacji(Date dataPublikacji) {
        this.dataPublikacji = dataPublikacji;
    }

    public Date getDataWaznosci() {
        return dataWaznosci;
    }

    public void setDataWaznosci(Date dataWaznosci) {
        this.dataWaznosci = dataWaznosci;
    }

    public Date getDataRejestracji() {
        return dataRejestracji;
    }

    public void setDataRejestracji(Date dataRejestracji) {
        this.dataRejestracji = dataRejestracji;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public Short getLiczbaZal() {
        return liczbaZal;
    }

    public void setLiczbaZal(Short liczbaZal) {
        this.liczbaZal = liczbaZal;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public short getStatCzyt() {
        return statCzyt;
    }

    public void setStatCzyt(short statCzyt) {
        this.statCzyt = statCzyt;
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
        hash += (idKomunikat != null ? idKomunikat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Komunikat)) {
            return false;
        }
        Komunikat other = (Komunikat) object;
        if ((this.idKomunikat == null && other.idKomunikat != null) || (this.idKomunikat != null && !this.idKomunikat.equals(other.idKomunikat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Komunikat[ idKomunikat=" + idKomunikat + " ]";
    }
    
}
