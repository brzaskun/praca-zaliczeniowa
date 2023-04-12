/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "staz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Staz.findAll", query = "SELECT s FROM Staz s"),
    @NamedQuery(name = "Staz.findById", query = "SELECT s FROM Staz s WHERE s.id = :id"),
    @NamedQuery(name = "Staz.findByDataod", query = "SELECT s FROM Staz s WHERE s.dataod = :dataod"),
    @NamedQuery(name = "Staz.findByDatado", query = "SELECT s FROM Staz s WHERE s.datado = :datado"),
    @NamedQuery(name = "Staz.findByLata", query = "SELECT s FROM Staz s WHERE s.lata = :lata"),
    @NamedQuery(name = "Staz.findByMiesiace", query = "SELECT s FROM Staz s WHERE s.miesiace = :miesiace"),
    @NamedQuery(name = "Staz.findByDni", query = "SELECT s FROM Staz s WHERE s.dni = :dni"),
    @NamedQuery(name = "Staz.findByUwagi", query = "SELECT s FROM Staz s WHERE s.uwagi = :uwagi"),
    @NamedQuery(name = "Staz.findByAngaz", query = "SELECT s FROM Staz s WHERE s.angaz = :angaz"),
    @NamedQuery(name = "Staz.findBySlownikszkolazatrhistoria", query = "SELECT s FROM Staz s WHERE s.slownikszkolazatrhistoria = :slownikszkolazatrhistoria")})
public class Staz implements Serializable {

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
    @Column(name = "lata")
    private Integer lata;
    @Column(name = "miesiace")
    private Integer miesiace;
    @Column(name = "dni")
    private Integer dni;
    @Column(name = "uwagi")
    private Integer uwagi;
    @NotNull
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne()
    private Angaz angaz;
    @NotNull
    @JoinColumn(name = "slownikszkolazatrhistoria", referencedColumnName = "id")
    @ManyToOne()
    private Slownikszkolazatrhistoria slownikszkolazatrhistoria;

    public Staz() {
    }

    public Staz(Integer id) {
        this.id = id;
    }

    public Staz(Integer id, String dataod, String datado, Angaz angaz, Slownikszkolazatrhistoria slownikszkolazatrhistoria) {
        this.id = id;
        this.dataod = dataod;
        this.datado = datado;
        this.angaz = angaz;
        this.slownikszkolazatrhistoria = slownikszkolazatrhistoria;
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

    public Integer getLata() {
        return lata;
    }

    public void setLata(Integer lata) {
        this.lata = lata;
    }

    public Integer getMiesiace() {
        return miesiace;
    }

    public void setMiesiace(Integer miesiace) {
        this.miesiace = miesiace;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Integer getUwagi() {
        return uwagi;
    }

    public void setUwagi(Integer uwagi) {
        this.uwagi = uwagi;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    public Slownikszkolazatrhistoria getSlownikszkolazatrhistoria() {
        return slownikszkolazatrhistoria;
    }

    public void setSlownikszkolazatrhistoria(Slownikszkolazatrhistoria slownikszkolazatrhistoria) {
        this.slownikszkolazatrhistoria = slownikszkolazatrhistoria;
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
        if (!(object instanceof Staz)) {
            return false;
        }
        Staz other = (Staz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Staz[ id=" + id + " ]";
    }
    
}
