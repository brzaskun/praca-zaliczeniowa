/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "nieobecnoscprezentacja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nieobecnoscprezentacja.findAll", query = "SELECT u FROM Nieobecnoscprezentacja u"),
    @NamedQuery(name = "Nieobecnoscprezentacja.findById", query = "SELECT u FROM Nieobecnoscprezentacja u WHERE u.id = :id"),
    @NamedQuery(name = "Nieobecnoscprezentacja.findByDoprzeniesienia", query = "SELECT u FROM Nieobecnoscprezentacja u WHERE u.doprzeniesienia = :doprzeniesienia")})
public class Nieobecnoscprezentacja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "rok", nullable = false)
    private String rok;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "bilansotwarciagodziny")
    private int bilansotwarciagodziny;
    @Column(name = "bilansotwarciadni")
    private int bilansotwarciadni;
    @Column(name = "wymiarokresbiezacygodziny")
    private int wymiarokresbiezacygodziny;
    @Column(name = "wymiarokresbiezacydni")
    private int wymiarokresbiezacydni;
    @Column(name = "wymiargeneralnydni")
    private int wymiargeneralnydni;
    @Column(name = "doprzeniesienia")
    private int doprzeniesienia;
    @Column(name = "doprzeniesieniadni")
    private int doprzeniesieniadni;
    @Column(name = "doswiadectwagodziny")
    private int doswiadectwagodziny;
     @Column(name = "doswiadectwadni")
    private int doswiadectwadni;
    @Column(name = "wykorzystanierokbiezacy")
    private int wykorzystanierokbiezacy;
    @Column(name = "wykorzystanierokbiezacydni")
    private int wykorzystanierokbiezacydni;
    @Column(name = "wykorzystanierokbiezacyekwiwalent")
    private int wykorzystanierokbiezacyekwiwalent;
    @NotNull
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne()
    private Angaz angaz;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "urlopprezentacja")
    private List<Nieobecnoscwykorzystanie> nieobecnoscwykorzystanieList;

   

    public Nieobecnoscprezentacja() {
    }

    public Nieobecnoscprezentacja(Integer id) {
        this.id = id;
    }

    public Nieobecnoscprezentacja(Angaz angaz, String rok) {
        this.angaz = angaz;
        this.rok = rok;
        this.nieobecnoscwykorzystanieList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public int getDoprzeniesieniadni() {
        return doprzeniesieniadni;
    }

    public void setDoprzeniesieniadni(int doprzeniesieniadni) {
        this.doprzeniesieniadni = doprzeniesieniadni;
    }

    public int getDoswiadectwagodziny() {
        return doswiadectwagodziny;
    }

    public void setDoswiadectwagodziny(int doswiadectwagodziny) {
        this.doswiadectwagodziny = doswiadectwagodziny;
    }

    public int getDoswiadectwadni() {
        return doswiadectwadni;
    }

    public void setDoswiadectwadni(int doswiadectwadni) {
        this.doswiadectwadni = doswiadectwadni;
    }

    public int getWymiargeneralnydni() {
        return wymiargeneralnydni;
    }

    public void setWymiargeneralnydni(int wymiargeneralnydni) {
        this.wymiargeneralnydni = wymiargeneralnydni;
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
        if (!(object instanceof Nieobecnoscprezentacja)) {
            return false;
        }
        Nieobecnoscprezentacja other = (Nieobecnoscprezentacja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.Nieobecnoscprezentacja[ id=" + id + " ]";
    }

    public int getBilansotwarciagodziny() {
        return bilansotwarciagodziny;
    }

    public void setBilansotwarciagodziny(int bilansotwarciagodziny) {
        this.bilansotwarciagodziny = bilansotwarciagodziny;
    }

    public int getBilansotwarciadni() {
        return bilansotwarciadni;
    }

    public void setBilansotwarciadni(int bilansotwarciadni) {
        this.bilansotwarciadni = bilansotwarciadni;
    }

    public int getWymiarokresbiezacygodziny() {
        return wymiarokresbiezacygodziny;
    }

    public void setWymiarokresbiezacygodziny(int wymiarokresbiezacygodziny) {
        this.wymiarokresbiezacygodziny = wymiarokresbiezacygodziny;
    }

    public int getDoprzeniesienia() {
        return doprzeniesienia;
    }

    public void setDoprzeniesienia(int doprzeniesienia) {
        this.doprzeniesienia = doprzeniesienia;
    }

    public int getWykorzystanierokbiezacy() {
        return wykorzystanierokbiezacy;
    }

    public void setWykorzystanierokbiezacy(int wykorzystanierokbiezacy) {
        this.wykorzystanierokbiezacy = wykorzystanierokbiezacy;
    }

    public int getWykorzystanierokbiezacyekwiwalent() {
        return wykorzystanierokbiezacyekwiwalent;
    }

    public void setWykorzystanierokbiezacyekwiwalent(int wykorzystanierokbiezacyekwiwalent) {
        this.wykorzystanierokbiezacyekwiwalent = wykorzystanierokbiezacyekwiwalent;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    public int getWymiarokresbiezacydni() {
        return wymiarokresbiezacydni;
    }

    public void setWymiarokresbiezacydni(int wymiarokresbiezacydni) {
        this.wymiarokresbiezacydni = wymiarokresbiezacydni;
    }

    public int getWykorzystanierokbiezacydni() {
        return wykorzystanierokbiezacydni;
    }

    public void setWykorzystanierokbiezacydni(int wykorzystanierokbiezacydni) {
        this.wykorzystanierokbiezacydni = wykorzystanierokbiezacydni;
    }

   

    @XmlTransient
    public List<Nieobecnoscwykorzystanie> getNieobecnoscwykorzystanieList() {
        return nieobecnoscwykorzystanieList;
    }

    public void setNieobecnoscwykorzystanieList(List<Nieobecnoscwykorzystanie> nieobecnoscwykorzystanieList) {
        this.nieobecnoscwykorzystanieList = nieobecnoscwykorzystanieList;
    }
    
}
