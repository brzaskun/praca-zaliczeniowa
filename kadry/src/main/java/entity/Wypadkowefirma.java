/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "wypadkowefirma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wypadkowefirma.findAll", query = "SELECT w FROM Wypadkowefirma w"),
    @NamedQuery(name = "Wypadkowefirma.findById", query = "SELECT w FROM Wypadkowefirma w WHERE w.id = :id"),
    @NamedQuery(name = "Wypadkowefirma.findByFirma", query = "SELECT w FROM Wypadkowefirma w WHERE w.firma = :firma"),
    @NamedQuery(name = "Wypadkowefirma.findByDataod", query = "SELECT w FROM Wypadkowefirma w WHERE w.dataod = :dataod"),
    @NamedQuery(name = "Wypadkowefirma.findByDatado", query = "SELECT w FROM Wypadkowefirma w WHERE w.datado = :datado"),
    @NamedQuery(name = "Wypadkowefirma.findByProcent", query = "SELECT w FROM Wypadkowefirma w WHERE w.procent = :procent")})
public class Wypadkowefirma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "dataod")
    private String dataod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "datado")
    private String datado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "procent")
    private double procent;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FirmaKadry firma;

    public Wypadkowefirma() {
    }

    public Wypadkowefirma(Integer id) {
        this.id = id;
    }

    public Wypadkowefirma(Integer id, String dataod, String datado, double procent) {
        this.id = id;
        this.dataod = dataod;
        this.datado = datado;
        this.procent = procent;
    }

     public boolean czynalezydookresu(String rok, String mc) {
        boolean zwrot = false;
        String dataod = this.dataod;
        String datado = this.datado;
        if (datado==null||datado.equals("")) {
            datado = "2050-12-31";
        }
        if (Data.czyjestpomiedzy(dataod, datado, rok, mc)) {
            zwrot = true;
        }
        //String dataod ()
        return zwrot;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public double getProcent() {
        return procent;
    }

    public void setProcent(double procent) {
        this.procent = procent;
    }

    public FirmaKadry getFirma() {
        return firma;
    }

    public void setFirma(FirmaKadry firma) {
        this.firma = firma;
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
        if (!(object instanceof Wypadkowefirma)) {
            return false;
        }
        Wypadkowefirma other = (Wypadkowefirma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Wypadkowefirma[ id=" + id + " ]";
    }
    
}
