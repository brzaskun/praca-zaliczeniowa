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
import javax.persistence.Lob;
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
@Table(name = "dokumenty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokumenty.findAll", query = "SELECT d FROM Dokumenty d"),
    @NamedQuery(name = "Dokumenty.findById", query = "SELECT d FROM Dokumenty d WHERE d.id = :id"),
    @NamedQuery(name = "Dokumenty.findByData", query = "SELECT d FROM Dokumenty d WHERE d.data = :data"),
    @NamedQuery(name = "Dokumenty.findByAngaz", query = "SELECT d FROM Dokumenty d WHERE d.angaz = :angaz"),
    @NamedQuery(name = "Dokumenty.findByFirma", query = "SELECT d FROM Dokumenty d WHERE d.angaz.firma = :firma"),
    @NamedQuery(name = "Dokumenty.findByAneks", query = "SELECT d FROM Dokumenty d WHERE d.aneks = :aneks")})
public class Dokumenty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "dokument")
    private byte[] dokument;
    @Size(max = 10)
    @Column(name = "data")
    private String data;
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Angaz angaz;
    @Column(name = "aneks")
    private boolean aneks;

    public Dokumenty() {
    }

    public Dokumenty(Integer id) {
        this.id = id;
    }

    public Dokumenty(Integer id, byte[] dokument) {
        this.id = id;
        this.dokument = dokument;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getDokument() {
        return dokument;
    }

    public void setDokument(byte[] dokument) {
        this.dokument = dokument;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isAneks() {
        return aneks;
    }

    public void setAneks(boolean aneks) {
        this.aneks = aneks;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dokumenty)) {
            return false;
        }
        Dokumenty other = (Dokumenty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Dokumenty[ id=" + id + " ]";
    }
    
}
