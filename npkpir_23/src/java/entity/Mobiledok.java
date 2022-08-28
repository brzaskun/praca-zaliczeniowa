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
@Table(name = "mobiledok")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mobiledok.findAll", query = "SELECT m FROM Mobiledok m"),
    @NamedQuery(name = "Mobiledok.findById", query = "SELECT m FROM Mobiledok m WHERE m.id = :id"),
    @NamedQuery(name = "Mobiledok.findByNip", query = "SELECT m FROM Mobiledok m WHERE m.nip = :nip"),
    @NamedQuery(name = "Mobiledok.findByData", query = "SELECT m FROM Mobiledok m WHERE m.data = :data"),
    @NamedQuery(name = "Mobiledok.findByRozszerzenie", query = "SELECT m FROM Mobiledok m WHERE m.rozszerzenie = :rozszerzenie")})
public class Mobiledok implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "nip")
    private String nip;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Lob
    @Column(name = "plik")
    private byte[] plik;
    @Size(max = 4)
    @Column(name = "rozszerzenie")
    private String rozszerzenie;

    public Mobiledok() {
    }

    public Mobiledok(Integer id) {
        this.id = id;
    }

    public Mobiledok(Integer id, String nip) {
        this.id = id;
        this.nip = nip;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public byte[] getPlik() {
        return plik;
    }

    public void setPlik(byte[] plik) {
        this.plik = plik;
    }

    public String getRozszerzenie() {
        return rozszerzenie;
    }

    public void setRozszerzenie(String rozszerzenie) {
        this.rozszerzenie = rozszerzenie;
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
        if (!(object instanceof Mobiledok)) {
            return false;
        }
        Mobiledok other = (Mobiledok) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Mobiledok[ id=" + id + " ]";
    }
    
}
