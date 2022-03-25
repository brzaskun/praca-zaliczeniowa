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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "grupyupowaznien", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"firma", "nazwagrupy"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupyupowaznien.findAll", query = "SELECT g FROM Grupyupowaznien g"),
    @NamedQuery(name = "Grupyupowaznien.findById", query = "SELECT g FROM Grupyupowaznien g WHERE g.id = :id"),
    @NamedQuery(name = "Grupyupowaznien.findByFirma", query = "SELECT g FROM Grupyupowaznien g WHERE g.firma = :firma"),
    @NamedQuery(name = "Grupyupowaznien.findByNazwagrupy", query = "SELECT g FROM Grupyupowaznien g WHERE g.nazwagrupy = :nazwagrupy"),
    @NamedQuery(name = "Grupyupowaznien.findByFirmaId", query = "SELECT g FROM Grupyupowaznien g WHERE g.firmaId = :firmaId")})
public class Grupyupowaznien implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "firma", nullable = false, length = 256)
    private String firma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "nazwagrupy", nullable = false, length = 120)
    private String nazwagrupy;
    @Column(name = "firma_id")
    private Integer firmaId;

    public Grupyupowaznien() {
    }

    public Grupyupowaznien(Integer id) {
        this.id = id;
    }

    public Grupyupowaznien(Integer id, String firma, String nazwagrupy) {
        this.id = id;
        this.firma = firma;
        this.nazwagrupy = nazwagrupy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getNazwagrupy() {
        return nazwagrupy;
    }

    public void setNazwagrupy(String nazwagrupy) {
        this.nazwagrupy = nazwagrupy;
    }

    public Integer getFirmaId() {
        return firmaId;
    }

    public void setFirmaId(Integer firmaId) {
        this.firmaId = firmaId;
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
        if (!(object instanceof Grupyupowaznien)) {
            return false;
        }
        Grupyupowaznien other = (Grupyupowaznien) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Grupyupowaznien{" + "firma=" + firma + ", nazwagrupy=" + nazwagrupy + ", firmaId=" + firmaId + '}';
    }

    
    
}
