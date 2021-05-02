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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podatki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Podatki.findAll", query = "SELECT p FROM Podatki p"),
    @NamedQuery(name = "Podatki.findById", query = "SELECT p FROM Podatki p WHERE p.id = :id"),
    @NamedQuery(name = "Podatki.findByRok", query = "SELECT p FROM Podatki p WHERE p.rok = :rok"),
    @NamedQuery(name = "Podatki.findByRokUmowa", query = "SELECT p FROM Podatki p WHERE p.rok = :rok AND p.rodzajumowy = :rodzajumowy"),
    @NamedQuery(name = "Podatki.findByStawka", query = "SELECT p FROM Podatki p WHERE p.stawka = :stawka"),
    @NamedQuery(name = "Podatki.findByKwotawolnaod", query = "SELECT p FROM Podatki p WHERE p.kwotawolnaod = :kwotawolnaod"),
    @NamedQuery(name = "Podatki.findByKwotawolnado", query = "SELECT p FROM Podatki p WHERE p.kwotawolnado = :kwotawolnado"),
    @NamedQuery(name = "Podatki.findByWolnarok", query = "SELECT p FROM Podatki p WHERE p.wolnarok = :wolnarok"),
    @NamedQuery(name = "Podatki.findByWolnamc", query = "SELECT p FROM Podatki p WHERE p.wolnamc = :wolnamc"),
    @NamedQuery(name = "Podatki.findByKup", query = "SELECT p FROM Podatki p WHERE p.kup = :kup"),
    @NamedQuery(name = "Podatki.findByKuppodwyzszone", query = "SELECT p FROM Podatki p WHERE p.kuppodwyzszone = :kuppodwyzszone"),
    @NamedQuery(name = "Podatki.findByRodzajumowy", query = "SELECT p FROM Podatki p WHERE p.rodzajumowy = :rodzajumowy")})
public class Podatki implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "rok")
    private String rok;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "stawka")
    private double stawka;
    @Column(name = "kwotawolnaod")
    private double kwotawolnaod;
    @Column(name = "kwotawolnado")
    private double kwotawolnado;
    @Column(name = "wolnarok")
    private double wolnarok;
    @Column(name = "wolnamc")
    private double wolnamc;
    @Column(name = "kup")
    private double kup;
    @Column(name = "kuppodwyzszone")
    private double kuppodwyzszone;
    @Size(max = 45)
    @Column(name = "rodzajumowy")
    private String rodzajumowy;

    public Podatki() {
    }

    public Podatki(Integer id) {
        this.id = id;
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

    public double getStawka() {
        return stawka;
    }

    public void setStawka(double stawka) {
        this.stawka = stawka;
    }

    public double getKwotawolnaod() {
        return kwotawolnaod;
    }

    public void setKwotawolnaod(double kwotawolnaod) {
        this.kwotawolnaod = kwotawolnaod;
    }

    public double getKwotawolnado() {
        return kwotawolnado;
    }

    public void setKwotawolnado(double kwotawolnado) {
        this.kwotawolnado = kwotawolnado;
    }

    public double getWolnarok() {
        return wolnarok;
    }

    public void setWolnarok(double wolnarok) {
        this.wolnarok = wolnarok;
    }

    public double getWolnamc() {
        return wolnamc;
    }

    public void setWolnamc(double wolnamc) {
        this.wolnamc = wolnamc;
    }

    public double getKup() {
        return kup;
    }

    public void setKup(double kup) {
        this.kup = kup;
    }

    public double getKuppodwyzszone() {
        return kuppodwyzszone;
    }

    public void setKuppodwyzszone(double kuppodwyzszone) {
        this.kuppodwyzszone = kuppodwyzszone;
    }

    public String getRodzajumowy() {
        return rodzajumowy;
    }

    public void setRodzajumowy(String rodzajumowy) {
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
        if (!(object instanceof Podatki)) {
            return false;
        }
        Podatki other = (Podatki) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Podatki[ id=" + id + " ]";
    }
    
}
