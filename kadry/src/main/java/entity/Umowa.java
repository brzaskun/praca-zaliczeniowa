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
@Table(name = "umowa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Umowa.findAll", query = "SELECT u FROM Umowa u"),
    @NamedQuery(name = "Umowa.findById", query = "SELECT u FROM Umowa u WHERE u.id = :id"),
    @NamedQuery(name = "Umowa.findByDataod", query = "SELECT u FROM Umowa u WHERE u.dataod = :dataod"),
    @NamedQuery(name = "Umowa.findByDatado", query = "SELECT u FROM Umowa u WHERE u.datado = :datado"),
    @NamedQuery(name = "Umowa.findByDatazawarcia", query = "SELECT u FROM Umowa u WHERE u.datazawarcia = :datazawarcia")})
public class Umowa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "dataod")
    private String dataod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "datado")
    private String datado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "datazawarcia")
    private String datazawarcia;
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Angaz angaz;
    @JoinColumn(name = "rodzajumowy", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rodzajumowy rodzajumowy;

    public Umowa() {
    }

    public Umowa(Integer id) {
        this.id = id;
    }

    public Umowa(Integer id, String dataod, String datado, String datazawarcia) {
        this.id = id;
        this.dataod = dataod;
        this.datado = datado;
        this.datazawarcia = datazawarcia;
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

    public String getDatazawarcia() {
        return datazawarcia;
    }

    public void setDatazawarcia(String datazawarcia) {
        this.datazawarcia = datazawarcia;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    public Rodzajumowy getRodzajumowy() {
        return rodzajumowy;
    }

    public void setRodzajumowy(Rodzajumowy rodzajumowy) {
        this.rodzajumowy = rodzajumowy;
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
        if (!(object instanceof Umowa)) {
            return false;
        }
        Umowa other = (Umowa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Umowa[ id=" + id + " ]";
    }
    
}
