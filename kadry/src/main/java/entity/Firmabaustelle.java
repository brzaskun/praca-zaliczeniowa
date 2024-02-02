/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firmabaustelle.findAll", query = "SELECT f FROM Firmabaustelle f"),
    @NamedQuery(name = "Firmabaustelle.findById", query = "SELECT f FROM Firmabaustelle f WHERE f.id = :id"),
    @NamedQuery(name = "Firmabaustelle.findByFirmakadry", query = "SELECT f FROM Firmabaustelle f WHERE f.firmakadry = :firmakadry"),
    @NamedQuery(name = "Firmabaustelle.findByKraj", query = "SELECT f FROM Firmabaustelle f WHERE f.kraj = :kraj"),
    @NamedQuery(name = "Firmabaustelle.findByMiasto", query = "SELECT f FROM Firmabaustelle f WHERE f.miasto = :miasto"),
    @NamedQuery(name = "Firmabaustelle.findByDataod", query = "SELECT f FROM Firmabaustelle f WHERE f.dataod = :dataod"),
    @NamedQuery(name = "Firmabaustelle.findByDatado", query = "SELECT f FROM Firmabaustelle f WHERE f.datado = :datado"),
    @NamedQuery(name = "Firmabaustelle.findByRok", query = "SELECT f FROM Firmabaustelle f WHERE f.rok = :rok"),
    @NamedQuery(name = "Firmabaustelle.findByRokFirma", query = "SELECT f FROM Firmabaustelle f WHERE f.rok = :rok AND  f.firmakadry = :firma"),
    @NamedQuery(name = "Firmabaustelle.findByNrkolejny", query = "SELECT f FROM Firmabaustelle f WHERE f.nrkolejny = :nrkolejny")})
public class Firmabaustelle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @ManyToOne(optional=false) 
    @JoinColumn(name="firmakadry", nullable=false, updatable=false)
    private FirmaKadry firmakadry;
    @Column(length = 128)
    private String kraj;
    @Column(length = 128)
    private String miasto;
    @Column(length = 10)
    private String dataod;
    @Column(length = 10)
    private String datado;
    @Basic(optional = false)
    @Column(nullable = false, length = 4)
    private String rok;
    @Column(length = 45)
    private String nrkolejny;

    public Firmabaustelle() {
    }

    public Firmabaustelle(Integer id) {
        this.id = id;
    }

    public Firmabaustelle(Integer id, FirmaKadry firmakadry, String rok) {
        this.id = id;
        this.firmakadry = firmakadry;
        this.rok = rok;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FirmaKadry getFirmakadry() {
        return firmakadry;
    }

    public void setFirmakadry(FirmaKadry firmakadry) {
        this.firmakadry = firmakadry;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getNrkolejny() {
        return nrkolejny;
    }

    public void setNrkolejny(String nrkolejny) {
        this.nrkolejny = nrkolejny;
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
        if (!(object instanceof Firmabaustelle)) {
            return false;
        }
        Firmabaustelle other = (Firmabaustelle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Firmabaustelle[ id=" + id + " ]";
    }
    
    
    public String toStringnieobecnosc() {
        return this.kraj+" "+this.miasto+" "+this.dataod;
    }
    
}
