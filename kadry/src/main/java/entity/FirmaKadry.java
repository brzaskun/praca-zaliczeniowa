/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "firma", uniqueConstraints = {
    @UniqueConstraint(columnNames={"nip"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FirmaKadry.findAll", query = "SELECT f FROM FirmaKadry f"),
    @NamedQuery(name = "FirmaKadry.findById", query = "SELECT f FROM FirmaKadry f WHERE f.id = :id"),
    @NamedQuery(name = "FirmaKadry.findByNazwa", query = "SELECT f FROM FirmaKadry f WHERE f.nazwa = :nazwa"),
    @NamedQuery(name = "FirmaKadry.findByNip", query = "SELECT f FROM FirmaKadry f WHERE f.nip = :nip")})
public class FirmaKadry implements Serializable {

    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 255)
    @Column(name = "nip",nullable = false)
    private String nip;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Uz> uzList;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Definicjalistaplac> definicjalistaplacList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Angaz> angazList;
    @OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
    private List<Kalendarzwzor> kalendarzWzorList;
    @Size(max = 128)
    @Column(name = "email")
    private String email;
    @Size(max = 128)
    @Column(name = "telefon")
    private String telefon;

    public FirmaKadry() {
    }

    public FirmaKadry(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<Angaz> getAngazList() {
        return angazList;
    }

    public void setAngazList(List<Angaz> angazList) {
        this.angazList = angazList;
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
        if (!(object instanceof FirmaKadry)) {
            return false;
        }
        FirmaKadry other = (FirmaKadry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FirmaKadry{" + "nazwa=" + nazwa + ", nip=" + nip + '}';
    }

    


    @XmlTransient
    public List<Kalendarzwzor> getKalendarzWzorList() {
        return kalendarzWzorList;
    }

    public void setKalendarzWzorList(List<Kalendarzwzor> kalendarzWzorList) {
        this.kalendarzWzorList = kalendarzWzorList;
    }


    @XmlTransient
    public List<Definicjalistaplac> getDefinicjalistaplacList() {
        return definicjalistaplacList;
    }

    public void setDefinicjalistaplacList(List<Definicjalistaplac> definicjalistaplacList) {
        this.definicjalistaplacList = definicjalistaplacList;
    }


    @XmlTransient
    public List<Uz> getUzList() {
        return uzList;
    }

    public void setUzList(List<Uz> uzList) {
        this.uzList = uzList;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    
}
