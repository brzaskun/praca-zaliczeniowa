/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "mobile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mobile.findAll", query = "SELECT m FROM Mobile m"),
    @NamedQuery(name = "Mobile.findById", query = "SELECT m FROM Mobile m WHERE m.id = :id"),
    @NamedQuery(name = "Mobile.findByNip", query = "SELECT m FROM Mobile m WHERE m.nip = :nip"),
    @NamedQuery(name = "Mobile.findByPin", query = "SELECT m FROM Mobile m WHERE m.pin = :pin"),
    @NamedQuery(name = "Mobile.findByNipfirmy", query = "SELECT m FROM Mobile m WHERE m.nipfirmy = :nipfirmy"),
    @NamedQuery(name = "Mobile.findByRok", query = "SELECT m FROM Mobile m WHERE m.rok = :rok"),
    @NamedQuery(name = "Mobile.findByMc", query = "SELECT m FROM Mobile m WHERE m.mc = :mc"),
    @NamedQuery(name = "Mobile.findByRodzaj", query = "SELECT m FROM Mobile m WHERE m.rodzaj = :rodzaj"),
    @NamedQuery(name = "Mobile.findByZaplacone", query = "SELECT m FROM Mobile m WHERE m.zaplacone = :zaplacone"),
    @NamedQuery(name = "Mobile.findByKorekta", query = "SELECT m FROM Mobile m WHERE m.korekta = :korekta"),
    @NamedQuery(name = "Mobile.findByTermin", query = "SELECT m FROM Mobile m WHERE m.termin = :termin"),
    @NamedQuery(name = "Mobile.findByKwota", query = "SELECT m FROM Mobile m WHERE m.kwota = :kwota")})
public class Mobile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "nip")
    private String nip;
    @Size(max = 45)
    @Column(name = "pin")
    private String pin;
    @Size(max = 45)
    @Column(name = "nipfirmy")
    private String nipfirmy;
    @Size(max = 45)
    @Column(name = "rok")
    private String rok;
    @Size(max = 45)
    @Column(name = "mc")
    private String mc;
    @Column(name = "rodzaj")
    private Integer rodzaj;
    @Column(name = "zaplacone")
    @Temporal(TemporalType.DATE)
    private Date zaplacone;
    @Column(name = "korekta")
    private Boolean korekta;
    @Size(max = 45)
    @Column(name = "termin")
    private String termin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private Double kwota;

    public Mobile() {
    }

    public Mobile(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNipfirmy() {
        return nipfirmy;
    }

    public void setNipfirmy(String nipfirmy) {
        this.nipfirmy = nipfirmy;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Integer getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(Integer rodzaj) {
        this.rodzaj = rodzaj;
    }

    public Date getZaplacone() {
        return zaplacone;
    }

    public void setZaplacone(Date zaplacone) {
        this.zaplacone = zaplacone;
    }

    public Boolean getKorekta() {
        return korekta;
    }

    public void setKorekta(Boolean korekta) {
        this.korekta = korekta;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public Double getKwota() {
        return kwota;
    }

    public void setKwota(Double kwota) {
        this.kwota = kwota;
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
        if (!(object instanceof Mobile)) {
            return false;
        }
        Mobile other = (Mobile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Mobile[ id=" + id + " ]";
    }
    
}
