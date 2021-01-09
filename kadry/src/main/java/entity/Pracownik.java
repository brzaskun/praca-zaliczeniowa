/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumy.Memory;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "pracownik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pracownik.findAll", query = "SELECT p FROM Pracownik p"),
    @NamedQuery(name = "Pracownik.findById", query = "SELECT p FROM Pracownik p WHERE p.id = :id"),
    @NamedQuery(name = "Pracownik.findByImie", query = "SELECT p FROM Pracownik p WHERE p.imie = :imie"),
    @NamedQuery(name = "Pracownik.findByNazwisko", query = "SELECT p FROM Pracownik p WHERE p.nazwisko = :nazwisko")})
public class Pracownik implements Serializable {

    @Size(max = 255)
    @Column(name = "imie")
    private String imie;
    @Size(max = 255)
    @Column(name = "nazwisko")
    private String nazwisko;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pracownik")
    private List<Memory> memoryList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "pracownik")
    private List<Angaz> angazList;

    public Pracownik() {
    }

    public Pracownik(int id) {
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
        if (!(object instanceof Pracownik)) {
            return false;
        }
        Pracownik other = (Pracownik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pracownik[ id=" + id + " ]";
    }
    public String getNazwiskoImie() {
        return nazwisko+" "+imie;
    }
    public String getCzyjetsangaz(){
        String zwrot = "";
        if (this.angazList!=null&& !this.angazList.isEmpty()) {
            zwrot = "✔";
        }
        return zwrot;
    }

  

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }
    
    public void setNazwisko(String nazwisko){
        this.nazwisko = nazwisko;
    }

    @XmlTransient
    public List<Memory> getMemoryList() {
        return memoryList;
    }

    public void setMemoryList(List<Memory> memoryList) {
        this.memoryList = memoryList;
    }
}
