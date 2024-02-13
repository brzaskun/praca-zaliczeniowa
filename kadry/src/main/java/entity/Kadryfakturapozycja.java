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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kadryfakturapozycja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kadryfakturapozycja.findAll", query = "SELECT k FROM Kadryfakturapozycja k"),
    @NamedQuery(name = "Kadryfakturapozycja.findById", query = "SELECT k FROM Kadryfakturapozycja k WHERE k.id = :id"),
    @NamedQuery(name = "Kadryfakturapozycja.findByRok", query = "SELECT k FROM Kadryfakturapozycja k WHERE k.rok = :rok"),
    @NamedQuery(name = "Kadryfakturapozycja.findByCena", query = "SELECT k FROM Kadryfakturapozycja k WHERE k.cena = :cena")})
public class Kadryfakturapozycja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cena")
    private double cena;
    @JoinColumn(name = "opisuslugi", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Fakturaopisuslugi opisuslugi;
    @JoinColumn(name = "firmakadry", referencedColumnName = "id")
    @OneToOne(optional = false)
    private FirmaKadry firmakadry;
    @JoinColumn(name = "uz", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Uz uz;
    @JoinColumn(name = "waluta", referencedColumnName = "idwaluty")
    @ManyToOne(optional = false)
    private Waluty waluta;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datadodania")
    private Date datadodania;
    @Column(name = "rok")
    private String rok;

    public Kadryfakturapozycja() {
    }

    public Kadryfakturapozycja(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Fakturaopisuslugi getOpisuslugi() {
        return opisuslugi;
    }

    public void setOpisuslugi(Fakturaopisuslugi opisuslugi) {
        this.opisuslugi = opisuslugi;
    }

    public FirmaKadry getFirmakadry() {
        return firmakadry;
    }

    public void setFirmakadry(FirmaKadry firmakadry) {
        this.firmakadry = firmakadry;
    }

    public Uz getUz() {
        return uz;
    }

    public void setUz(Uz uz) {
        this.uz = uz;
    }

    public Waluty getWaluta() {
        return waluta;
    }

    public void setWaluta(Waluty waluta) {
        this.waluta = waluta;
    }

    public Date getDatadodania() {
        return datadodania;
    }

    public void setDatadodania(Date datadodania) {
        this.datadodania = datadodania;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
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
        if (!(object instanceof Kadryfakturapozycja)) {
            return false;
        }
        Kadryfakturapozycja other = (Kadryfakturapozycja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kadryfakturapozycja{" + "cena=" + cena + ", opisuslugi=" + opisuslugi + ", firmakadry=" + firmakadry.getNazwa() + ", uz=" + uz.getImieNazwisko() + ", waluta=" + waluta + ", datadodania=" + datadodania + '}';
    }

    
}
