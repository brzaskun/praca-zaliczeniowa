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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "ubezpieczenie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ubezpieczenie.findAll", query = "SELECT u FROM Ubezpieczenie u"),
    @NamedQuery(name = "Ubezpieczenie.findById", query = "SELECT u FROM Ubezpieczenie u WHERE u.id = :id"),
    @NamedQuery(name = "Ubezpieczenie.findByKodubezpieczenia", query = "SELECT u FROM Ubezpieczenie u WHERE u.kodubezpieczenia = :kodubezpieczenia"),
    @NamedQuery(name = "Ubezpieczenie.findByKodzawodu", query = "SELECT u FROM Ubezpieczenie u WHERE u.kodzawodu = :kodzawodu"),
    @NamedQuery(name = "Ubezpieczenie.findByNfz", query = "SELECT u FROM Ubezpieczenie u WHERE u.nfz = :nfz"),
    @NamedQuery(name = "Ubezpieczenie.findByDatanfz", query = "SELECT u FROM Ubezpieczenie u WHERE u.datanfz = :datanfz"),
    @NamedQuery(name = "Ubezpieczenie.findByDataspoleczne", query = "SELECT u FROM Ubezpieczenie u WHERE u.dataspoleczne = :dataspoleczne"),
    @NamedQuery(name = "Ubezpieczenie.findByDatazdrowotne", query = "SELECT u FROM Ubezpieczenie u WHERE u.datazdrowotne = :datazdrowotne"),
    @NamedQuery(name = "Ubezpieczenie.findByEmerytalne", query = "SELECT u FROM Ubezpieczenie u WHERE u.emerytalne = :emerytalne"),
    @NamedQuery(name = "Ubezpieczenie.findByRentowe", query = "SELECT u FROM Ubezpieczenie u WHERE u.rentowe = :rentowe"),
    @NamedQuery(name = "Ubezpieczenie.findByChorobowe", query = "SELECT u FROM Ubezpieczenie u WHERE u.chorobowe = :chorobowe"),
    @NamedQuery(name = "Ubezpieczenie.findByChorobowedobrowolne", query = "SELECT u FROM Ubezpieczenie u WHERE u.chorobowedobrowolne = :chorobowedobrowolne"),
    @NamedQuery(name = "Ubezpieczenie.findByWypadkowe", query = "SELECT u FROM Ubezpieczenie u WHERE u.wypadkowe = :wypadkowe"),
    @NamedQuery(name = "Ubezpieczenie.findByZdrowotne", query = "SELECT u FROM Ubezpieczenie u WHERE u.zdrowotne = :zdrowotne"),
    @NamedQuery(name = "Ubezpieczenie.findByNieliczFP", query = "SELECT u FROM Ubezpieczenie u WHERE u.nieliczFP = :nieliczFP"),
    @NamedQuery(name = "Ubezpieczenie.findByNieliczFGSP", query = "SELECT u FROM Ubezpieczenie u WHERE u.nieliczFGSP = :nieliczFGSP")})
public class Ubezpieczenie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "kodubezpieczenia")
    private String kodubezpieczenia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "kodzawodu")
    private String kodzawodu;
    @Size(max = 90)
    @Column(name = "nfz")
    private String nfz;
    @Size(max = 45)
    @Column(name = "datanfz")
    private String datanfz;
    @Size(max = 45)
    @Column(name = "dataspoleczne")
    private String dataspoleczne;
    @Size(max = 45)
    @Column(name = "datazdrowotne")
    private String datazdrowotne;
    @Column(name = "emerytalne")
    private Boolean emerytalne;
    @Column(name = "rentowe")
    private Boolean rentowe;
    @Column(name = "chorobowe")
    private Boolean chorobowe;
    @Column(name = "chorobowedobrowolne")
    private Boolean chorobowedobrowolne;
    @Column(name = "wypadkowe")
    private Boolean wypadkowe;
    @Column(name = "zdrowotne")
    private Boolean zdrowotne;
    @Column(name = "nieliczFP")
    private Boolean nieliczFP;
    @Column(name = "nieliczFGSP")
    private Boolean nieliczFGSP;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ubezpieczenie")
    private List<Angaz> angazList;

    public Ubezpieczenie() {
        this.kodubezpieczenia = "1110";
        this.kodzawodu = "1585";
        this.nfz = "16R";
        this.datanfz = "2020-01-01";
        this.dataspoleczne = "2020-01-01";
        this.datazdrowotne = "2020-01-01";
        this.emerytalne = true;
        this.rentowe = true;
        this.chorobowe = true;
        this.wypadkowe = true;
        this.zdrowotne = true;
    }

    public Ubezpieczenie(Integer id) {
        this.id = id;
    }

    public Ubezpieczenie(Integer id, String kodubezpieczenia, String kodzawodu) {
        this.id = id;
        this.kodubezpieczenia = kodubezpieczenia;
        this.kodzawodu = kodzawodu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKodubezpieczenia() {
        return kodubezpieczenia;
    }

    public void setKodubezpieczenia(String kodubezpieczenia) {
        this.kodubezpieczenia = kodubezpieczenia;
    }

    public String getKodzawodu() {
        return kodzawodu;
    }

    public void setKodzawodu(String kodzawodu) {
        this.kodzawodu = kodzawodu;
    }

    public String getNfz() {
        return nfz;
    }

    public void setNfz(String nfz) {
        this.nfz = nfz;
    }

    public String getDatanfz() {
        return datanfz;
    }

    public void setDatanfz(String datanfz) {
        this.datanfz = datanfz;
    }

    public String getDataspoleczne() {
        return dataspoleczne;
    }

    public void setDataspoleczne(String dataspoleczne) {
        this.dataspoleczne = dataspoleczne;
    }

    public String getDatazdrowotne() {
        return datazdrowotne;
    }

    public void setDatazdrowotne(String datazdrowotne) {
        this.datazdrowotne = datazdrowotne;
    }

    public Boolean getEmerytalne() {
        return emerytalne;
    }

    public void setEmerytalne(Boolean emerytalne) {
        this.emerytalne = emerytalne;
    }

    public Boolean getRentowe() {
        return rentowe;
    }

    public void setRentowe(Boolean rentowe) {
        this.rentowe = rentowe;
    }

    public Boolean getChorobowe() {
        return chorobowe;
    }

    public void setChorobowe(Boolean chorobowe) {
        this.chorobowe = chorobowe;
    }

    public Boolean getChorobowedobrowolne() {
        return chorobowedobrowolne;
    }

    public void setChorobowedobrowolne(Boolean chorobowedobrowolne) {
        this.chorobowedobrowolne = chorobowedobrowolne;
    }

    public Boolean getWypadkowe() {
        return wypadkowe;
    }

    public void setWypadkowe(Boolean wypadkowe) {
        this.wypadkowe = wypadkowe;
    }

    public Boolean getZdrowotne() {
        return zdrowotne;
    }

    public void setZdrowotne(Boolean zdrowotne) {
        this.zdrowotne = zdrowotne;
    }

    public Boolean getNieliczFP() {
        return nieliczFP;
    }

    public void setNieliczFP(Boolean nieliczFP) {
        this.nieliczFP = nieliczFP;
    }

    public Boolean getNieliczFGSP() {
        return nieliczFGSP;
    }

    public void setNieliczFGSP(Boolean nieliczFGSP) {
        this.nieliczFGSP = nieliczFGSP;
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
        if (!(object instanceof Ubezpieczenie)) {
            return false;
        }
        Ubezpieczenie other = (Ubezpieczenie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ubezpieczenie[ id=" + id + " ]";
    }
    
}
