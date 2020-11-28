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
@Table(name = "naliczeniepotracenie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Naliczeniepotracenie.findAll", query = "SELECT n FROM Naliczeniepotracenie n"),
    @NamedQuery(name = "Naliczeniepotracenie.findById", query = "SELECT n FROM Naliczeniepotracenie n WHERE n.id = :id"),
    @NamedQuery(name = "Naliczeniepotracenie.findByKwota", query = "SELECT n FROM Naliczeniepotracenie n WHERE n.kwota = :kwota"),
    @NamedQuery(name = "Naliczeniepotracenie.findByRok", query = "SELECT n FROM Naliczeniepotracenie n WHERE n.rok = :rok"),
    @NamedQuery(name = "Naliczeniepotracenie.findByMc", query = "SELECT n FROM Naliczeniepotracenie n WHERE n.mc = :mc")})
public class Naliczeniepotracenie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kwota")
    private double kwota;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "rok")
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "mc")
    private String mc;
    @JoinColumn(name = "definicjalistaplac", referencedColumnName = "id")
    @ManyToOne
    private Definicjalistaplac definicjalistaplac;
    @JoinColumn(name = "skladnikpotracenia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Skladnikpotracenia skladnikpotracenia;

    public Naliczeniepotracenie() {
    }

    public Naliczeniepotracenie(Integer id) {
        this.id = id;
    }

    public Naliczeniepotracenie(Integer id, double kwota, String rok, String mc) {
        this.id = id;
        this.kwota = kwota;
        this.rok = rok;
        this.mc = mc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
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

    public Definicjalistaplac getDefinicjalistaplac() {
        return definicjalistaplac;
    }

    public void setDefinicjalistaplac(Definicjalistaplac definicjalistaplac) {
        this.definicjalistaplac = definicjalistaplac;
    }

    public Skladnikpotracenia getSkladnikpotracenia() {
        return skladnikpotracenia;
    }

    public void setSkladnikpotracenia(Skladnikpotracenia skladnikpotracenia) {
        this.skladnikpotracenia = skladnikpotracenia;
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
        if (!(object instanceof Naliczeniepotracenie)) {
            return false;
        }
        Naliczeniepotracenie other = (Naliczeniepotracenie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Naliczeniepotracenie[ id=" + id + " ]";
    }
    
}
