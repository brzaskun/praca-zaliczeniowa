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
@Table(name = "CRL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Crl.findAll", query = "SELECT c FROM Crl c"),
    @NamedQuery(name = "Crl.findById", query = "SELECT c FROM Crl c WHERE c.id = :id"),
    @NamedQuery(name = "Crl.findByIdPlatnik", query = "SELECT c FROM Crl c WHERE c.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Crl.findByWlascicielId", query = "SELECT c FROM Crl c WHERE c.wlascicielId = :wlascicielId"),
    @NamedQuery(name = "Crl.findByWlasciciel", query = "SELECT c FROM Crl c WHERE c.wlasciciel = :wlasciciel"),
    @NamedQuery(name = "Crl.findByRodzaj", query = "SELECT c FROM Crl c WHERE c.rodzaj = :rodzaj"),
    @NamedQuery(name = "Crl.findByNumer", query = "SELECT c FROM Crl c WHERE c.numer = :numer"),
    @NamedQuery(name = "Crl.findBySciezka", query = "SELECT c FROM Crl c WHERE c.sciezka = :sciezka"),
    @NamedQuery(name = "Crl.findByTyp", query = "SELECT c FROM Crl c WHERE c.typ = :typ"),
    @NamedQuery(name = "Crl.findByData", query = "SELECT c FROM Crl c WHERE c.data = :data"),
    @NamedQuery(name = "Crl.findByDataNast", query = "SELECT c FROM Crl c WHERE c.dataNast = :dataNast")})
public class Crl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Size(max = 55)
    @Column(name = "WLASCICIEL_ID", length = 55)
    private String wlascicielId;
    @Size(max = 255)
    @Column(name = "WLASCICIEL", length = 255)
    private String wlasciciel;
    @Column(name = "RODZAJ")
    private Character rodzaj;
    @Column(name = "NUMER")
    private Integer numer;
    @Size(max = 255)
    @Column(name = "SCIEZKA", length = 255)
    private String sciezka;
    @Size(max = 20)
    @Column(name = "TYP", length = 20)
    private String typ;
    @Column(name = "DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "DATA_NAST")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNast;

    public Crl() {
    }

    public Crl(Integer id) {
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

    public Character getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(Character rodzaj) {
        this.rodzaj = rodzaj;
    }

    public Integer getNumer() {
        return numer;
    }

    public void setNumer(Integer numer) {
        this.numer = numer;
    }

    public String getSciezka() {
        return sciezka;
    }

    public void setSciezka(String sciezka) {
        this.sciezka = sciezka;
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

    public Date getDataNast() {
        return dataNast;
    }

    public void setDataNast(Date dataNast) {
        this.dataNast = dataNast;
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
        if (!(object instanceof Crl)) {
            return false;
        }
        Crl other = (Crl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Crl[ id=" + id + " ]";
    }
    
}
