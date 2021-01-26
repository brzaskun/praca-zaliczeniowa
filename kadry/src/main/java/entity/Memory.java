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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "memory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memory.findAll", query = "SELECT m FROM Memory m"),
    @NamedQuery(name = "Memory.findById", query = "SELECT m FROM Memory m WHERE m.id = :id"),
    @NamedQuery(name = "Memory.findByUzer", query = "SELECT m FROM Memory m WHERE m.uzer = :uzer"),
    @NamedQuery(name = "Memory.findByRok", query = "SELECT m FROM Memory m WHERE m.rok = :rok"),
    @NamedQuery(name = "Memory.findByMc", query = "SELECT m FROM Memory m WHERE m.mc = :mc")})
public class Memory implements Serializable {

    @ManyToOne
    @JoinColumn(name = "uzer", referencedColumnName = "id")
    private Uz uzer;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne
    private Angaz angaz;
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Firma firma;
    @JoinColumn(name = "pracownik", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pracownik pracownik;
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne
    private Umowa umowa;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Memory() {
    }

    public Memory(Integer id) {
        this.id = id;
    }

    public Memory(Integer id, Uz uzer) {
        this.id = id;
        this.uzer = uzer;
    }

    public Memory(Firma firma, Pracownik pracownik) {
        this.firma = firma;
        this.pracownik = pracownik;
    }

    public Memory(Uz uzer, Firma firma, Pracownik pracownik, String rokWpisu, String miesiacWpisu) {
        this.uzer = uzer;
        this.firma = firma;
        this.pracownik = pracownik;
        this.rok = rokWpisu;
        this.mc = miesiacWpisu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Memory)) {
            return false;
        }
        Memory other = (Memory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Memory[ id=" + id + " ]";
    }

    public Uz getUzer() {
        return uzer;
    }

    public void setUzer(Uz uzer) {
        this.uzer = uzer;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }
    
}
