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
@Table(name = "CERTYFIKAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Certyfikat.findAll", query = "SELECT c FROM Certyfikat c"),
    @NamedQuery(name = "Certyfikat.findById", query = "SELECT c FROM Certyfikat c WHERE c.id = :id"),
    @NamedQuery(name = "Certyfikat.findByIdPlatnik", query = "SELECT c FROM Certyfikat c WHERE c.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Certyfikat.findByIdUzytkownik", query = "SELECT c FROM Certyfikat c WHERE c.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Certyfikat.findByCertTyp", query = "SELECT c FROM Certyfikat c WHERE c.certTyp = :certTyp"),
    @NamedQuery(name = "Certyfikat.findByNumerSer", query = "SELECT c FROM Certyfikat c WHERE c.numerSer = :numerSer"),
    @NamedQuery(name = "Certyfikat.findByNazwaWydawcy", query = "SELECT c FROM Certyfikat c WHERE c.nazwaWydawcy = :nazwaWydawcy"),
    @NamedQuery(name = "Certyfikat.findByWlascicielId", query = "SELECT c FROM Certyfikat c WHERE c.wlascicielId = :wlascicielId"),
    @NamedQuery(name = "Certyfikat.findByWlasciciel", query = "SELECT c FROM Certyfikat c WHERE c.wlasciciel = :wlasciciel"),
    @NamedQuery(name = "Certyfikat.findByNumerSerWyd", query = "SELECT c FROM Certyfikat c WHERE c.numerSerWyd = :numerSerWyd"),
    @NamedQuery(name = "Certyfikat.findByPrzeznaczenie", query = "SELECT c FROM Certyfikat c WHERE c.przeznaczenie = :przeznaczenie"),
    @NamedQuery(name = "Certyfikat.findByDtCertOd", query = "SELECT c FROM Certyfikat c WHERE c.dtCertOd = :dtCertOd"),
    @NamedQuery(name = "Certyfikat.findByDtCertDo", query = "SELECT c FROM Certyfikat c WHERE c.dtCertDo = :dtCertDo"),
    @NamedQuery(name = "Certyfikat.findByDtKluczaOd", query = "SELECT c FROM Certyfikat c WHERE c.dtKluczaOd = :dtKluczaOd"),
    @NamedQuery(name = "Certyfikat.findByDtKluczaDo", query = "SELECT c FROM Certyfikat c WHERE c.dtKluczaDo = :dtKluczaDo"),
    @NamedQuery(name = "Certyfikat.findByInserttmp", query = "SELECT c FROM Certyfikat c WHERE c.inserttmp = :inserttmp")})
public class Certyfikat implements Serializable {

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
    @Column(name = "CERT_TYP")
    private Integer certTyp;
    @Size(max = 40)
    @Column(name = "NUMER_SER", length = 40)
    private String numerSer;
    @Size(max = 255)
    @Column(name = "NAZWA_WYDAWCY", length = 255)
    private String nazwaWydawcy;
    @Size(max = 55)
    @Column(name = "WLASCICIEL_ID", length = 55)
    private String wlascicielId;
    @Size(max = 255)
    @Column(name = "WLASCICIEL", length = 255)
    private String wlasciciel;
    @Column(name = "NUMER_SER_WYD")
    private Integer numerSerWyd;
    @Column(name = "PRZEZNACZENIE")
    private Short przeznaczenie;
    @Column(name = "DT_CERT_OD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCertOd;
    @Column(name = "DT_CERT_DO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCertDo;
    @Column(name = "DT_KLUCZA_OD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtKluczaOd;
    @Column(name = "DT_KLUCZA_DO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtKluczaDo;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "CERTYFIKAT", length = 2147483647)
    private String certyfikat;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Certyfikat() {
    }

    public Certyfikat(Integer id) {
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

    public Integer getCertTyp() {
        return certTyp;
    }

    public void setCertTyp(Integer certTyp) {
        this.certTyp = certTyp;
    }

    public String getNumerSer() {
        return numerSer;
    }

    public void setNumerSer(String numerSer) {
        this.numerSer = numerSer;
    }

    public String getNazwaWydawcy() {
        return nazwaWydawcy;
    }

    public void setNazwaWydawcy(String nazwaWydawcy) {
        this.nazwaWydawcy = nazwaWydawcy;
    }

    public String getWlascicielId() {
        return wlascicielId;
    }

    public void setWlascicielId(String wlascicielId) {
        this.wlascicielId = wlascicielId;
    }

    public String getWlasciciel() {
        return wlasciciel;
    }

    public void setWlasciciel(String wlasciciel) {
        this.wlasciciel = wlasciciel;
    }

    public Integer getNumerSerWyd() {
        return numerSerWyd;
    }

    public void setNumerSerWyd(Integer numerSerWyd) {
        this.numerSerWyd = numerSerWyd;
    }

    public Short getPrzeznaczenie() {
        return przeznaczenie;
    }

    public void setPrzeznaczenie(Short przeznaczenie) {
        this.przeznaczenie = przeznaczenie;
    }

    public Date getDtCertOd() {
        return dtCertOd;
    }

    public void setDtCertOd(Date dtCertOd) {
        this.dtCertOd = dtCertOd;
    }

    public Date getDtCertDo() {
        return dtCertDo;
    }

    public void setDtCertDo(Date dtCertDo) {
        this.dtCertDo = dtCertDo;
    }

    public Date getDtKluczaOd() {
        return dtKluczaOd;
    }

    public void setDtKluczaOd(Date dtKluczaOd) {
        this.dtKluczaOd = dtKluczaOd;
    }

    public Date getDtKluczaDo() {
        return dtKluczaDo;
    }

    public void setDtKluczaDo(Date dtKluczaDo) {
        this.dtKluczaDo = dtKluczaDo;
    }

    public String getCertyfikat() {
        return certyfikat;
    }

    public void setCertyfikat(String certyfikat) {
        this.certyfikat = certyfikat;
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
        if (!(object instanceof Certyfikat)) {
            return false;
        }
        Certyfikat other = (Certyfikat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Certyfikat[ id=" + id + " ]";
    }
    
}
