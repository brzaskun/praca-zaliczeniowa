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
@Table(name = "nieobecnoscwykorzystanie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nieobecnoscwykorzystanie.findAll", query = "SELECT u FROM Nieobecnoscwykorzystanie u"),
    @NamedQuery(name = "Nieobecnoscwykorzystanie.findById", query = "SELECT u FROM Nieobecnoscwykorzystanie u WHERE u.id = :id"),
    @NamedQuery(name = "Nieobecnoscwykorzystanie.findByDni", query = "SELECT u FROM Nieobecnoscwykorzystanie u WHERE u.dni = :dni"),
    @NamedQuery(name = "Nieobecnoscwykorzystanie.findByGodziny", query = "SELECT u FROM Nieobecnoscwykorzystanie u WHERE u.godziny = :godziny"),
    @NamedQuery(name = "Nieobecnoscwykorzystanie.findByMc", query = "SELECT u FROM Nieobecnoscwykorzystanie u WHERE u.mc = :mc"),
    @NamedQuery(name = "Nieobecnoscwykorzystanie.findByData", query = "SELECT u FROM Nieobecnoscwykorzystanie u WHERE u.data = :data"),
    @NamedQuery(name = "Nieobecnoscwykorzystanie.findByEtat1", query = "SELECT u FROM Nieobecnoscwykorzystanie u WHERE u.etat1 = :etat1"),
    @NamedQuery(name = "Nieobecnoscwykorzystanie.findByEtat2", query = "SELECT u FROM Nieobecnoscwykorzystanie u WHERE u.etat2 = :etat2")})
public class Nieobecnoscwykorzystanie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dni")
    private double dni;
    @Column(name = "godziny")
    private int godziny;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "mc")
    private String mc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "data")
    private String data;
    @Column(name = "etat1")
    private Integer etat1;
    @Column(name = "etat2")
    private Integer etat2;
    @Column(name = "opis")
    private String opis;
    @Column(name = "kod")
    private String kod;
    @JoinColumn(name = "urlopprezentacja", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Nieobecnoscprezentacja urlopprezentacja;

    public Nieobecnoscwykorzystanie() {
    }

    public Nieobecnoscwykorzystanie(Integer id) {
        this.id = id;
    }

    public Nieobecnoscwykorzystanie(Integer id, String mc, String data) {
        this.id = id;
        this.mc = mc;
        this.data = data;
    }

    public Nieobecnoscwykorzystanie(String podsumowanie, int i) {
        this.data = podsumowanie;
        this.godziny = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getDni() {
        return dni;
    }

    public void setDni(double dni) {
        this.dni = dni;
    }

    public int getGodziny() {
        return godziny;
    }

    public void setGodziny(int godziny) {
        this.godziny = godziny;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

   
    public Integer getEtat1() {
        return etat1;
    }

    public void setEtat1(Integer etat1) {
        this.etat1 = etat1;
    }

    public Integer getEtat2() {
        return etat2;
    }

    public void setEtat2(Integer etat2) {
        this.etat2 = etat2;
    }

    public Nieobecnoscprezentacja getUrlopprezentacja() {
        return urlopprezentacja;
    }

    public void setUrlopprezentacja(Nieobecnoscprezentacja urlopprezentacja) {
        this.urlopprezentacja = urlopprezentacja;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
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
        if (!(object instanceof Nieobecnoscwykorzystanie)) {
            return false;
        }
        Nieobecnoscwykorzystanie other = (Nieobecnoscwykorzystanie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Nieobecnoscwykorzystanie[ id=" + id + " ]";
    }
    
}
